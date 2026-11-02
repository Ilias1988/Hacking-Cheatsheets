# 🔍 Nmap - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - El escáner de red
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconocimiento">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Escaneo de puertos">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="Scripts NSE">
</p>

<p align="center">
  <b>🌐 La herramienta más potente de escaneo y descubrimiento de redes</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Especificación de objetivos](#-especificación-de-objetivos)
- [Descubrimiento de equipos](#-descubrimiento-de-equipos)
- [Técnicas de escaneo de puertos](#-técnicas-de-escaneo-de-puertos)
- [Especificación de puertos](#-especificación-de-puertos)
- [Detección de servicios y versiones](#-detección-de-servicios-y-versiones)
- [Detección del sistema operativo](#-detección-del-sistema-operativo)
- [Temporización y rendimiento](#-temporización-y-rendimiento)
- [Scripts NSE](#-scripts-nse)
- [Formatos de salida](#-formatos-de-salida)
- [Evasión de cortafuegos/IDS](#-evasión-de-cortafuegosids)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

NSE (Nmap Scripting Engine) es el motor de scripts de Nmap; IDS significa sistema de detección de intrusiones. Las sondas son paquetes de prueba que ayudan a inferir el estado de los equipos y servicios.

**Nmap** (Network Mapper) es una utilidad gratuita y de código abierto para descubrir redes y realizar auditorías de seguridad. Es una herramienta de referencia en el sector para el escaneo de redes.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Descubrimiento de equipos** | Encontrar equipos activos en una red |
| **Escaneo de puertos** | Identificar puertos abiertos y servicios |
| **Detección de versiones** | Determinar las versiones de los servicios |
| **Detección del sistema operativo** | Identificar sistemas operativos |
| **Scripts NSE** | Escaneo de vulnerabilidades y explotación |
| **Formatos de salida** | XML, salida apta para grep y salida normal |

### Estados de los puertos

| Estado | Descripción |
|-------|-------------|
| `open` | La aplicación acepta conexiones |
| `closed` | El puerto es accesible, pero no hay una aplicación a la escucha |
| `filtered` | Nmap no puede determinar si está abierto (cortafuegos) |
| `unfiltered` | El puerto es accesible, pero su estado es desconocido |
| `open|filtered` | No se puede determinar si está abierto o filtrado |
| `closed|filtered` | No se puede determinar si está cerrado o filtrado |

---

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
# Actualizar Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Descargar desde https://nmap.org/download.html
# O usar Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Verificar la instalación
```bash
nmap --version
nmap -h          # Ayuda
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
nmap [Scan Type] [Options] {target}
```

### Ejemplos para empezar
```bash
# Escaneo básico
nmap 192.168.1.1

# Escanear varios equipos
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Escanear un intervalo
nmap 192.168.1.1-254

# Escanear toda la subred
nmap 192.168.1.0/24

# Escanear a partir de un archivo
nmap -iL targets.txt
```

---

## 🎯 Especificación de objetivos

### Objetivos individuales
```bash
# Dirección IP
nmap 192.168.1.1

# Nombre del equipo
nmap scanme.nmap.org

# Dominio
nmap example.com
```

### Varios objetivos
```bash
# Separados por espacios
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Intervalo
nmap 192.168.1.1-100

# Notación CIDR
nmap 192.168.1.0/24

# Comodín
nmap 192.168.1.*

# Intervalo de octetos
nmap 192.168.0-255.1-254

# Combinación de formatos
nmap 192.168.1.0/24 10.0.0.1
```

### Entrada desde un archivo
```bash
# Leer desde un archivo
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Excluir objetivos
```bash
# Excluir un equipo
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Excluir a partir de un archivo
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Objetivos aleatorios
```bash
# Escanear equipos aleatorios (para investigación)
nmap -iR 100
```

---

## 🔎 Descubrimiento de equipos

### Opciones de descubrimiento

| Opción | Descripción |
|--------|-------------|
| `-sL` | Escaneo de lista: solo enumerar los objetivos |
| `-sn` | Escaneo de ping: sin escaneo de puertos |
| `-Pn` | Omitir el descubrimiento de equipos (considerarlos todos activos) |
| `-PS` | Ping TCP SYN |
| `-PA` | Ping TCP ACK |
| `-PU` | Ping UDP |
| `-PE` | Ping de eco ICMP |
| `-PP` | Ping de marca temporal ICMP |
| `-PM` | Ping de máscara de red ICMP |
| `-PO` | Ping de protocolo IP |
| `-PR` | Ping ARP (red local) |

### Escaneo de ping (sin escaneo de puertos)
```bash
# Descubrir únicamente equipos activos
nmap -sn 192.168.1.0/24

# Escaneo de ping con una lista de equipos
nmap -sn -iL hosts.txt
```

### Omitir el descubrimiento de equipos
```bash
# Escanear todos los equipos (sin ping previo)
nmap -Pn 192.168.1.1

# Útil cuando:
# - Los equipos bloquean ICMP
# - Se escanea a través de un cortafuegos
```

### Ping TCP SYN/ACK
```bash
# Ping TCP SYN en el puerto 80
nmap -PS80 192.168.1.1

# Ping TCP ACK en el puerto 80
nmap -PA80 192.168.1.1

# Varios puertos
nmap -PS22,80,443 192.168.1.1
```

### Ping UDP
```bash
# Ping UDP
nmap -PU53 192.168.1.1

# Varios puertos
nmap -PU53,161 192.168.1.1
```

### Ping ICMP
```bash
# Eco ICMP
nmap -PE 192.168.1.1

# Marca temporal ICMP
nmap -PP 192.168.1.1

# Máscara de red ICMP
nmap -PM 192.168.1.1
```

### Ping ARP (red local)
```bash
# Escaneo ARP (el más rápido en la red local)
nmap -PR 192.168.1.0/24

# Solo escaneo ARP
nmap -sn -PR 192.168.1.0/24
```

### Desactivar la resolución DNS
```bash
# Sin resolución DNS (más rápido)
nmap -n 192.168.1.1

# Resolver DNS siempre
nmap -R 192.168.1.1
```

---

## 🔌 Técnicas de escaneo de puertos

> **Precisión:** «discreto» no significa indetectable, y el escaneo TCP Connect tampoco garantiza resultados en cualquier red. El SYN predeterminado requiere capacidad para enviar paquetes sin procesar; en caso contrario Nmap usa TCP Connect. Véanse las [técnicas oficiales de escaneo](https://nmap.org/book/man-port-scanning-techniques.html).

### Resumen de tipos de escaneo

| Opción | Nombre | Descripción |
|--------|------|-------------|
| `-sS` | Escaneo SYN | Escaneo discreto (predeterminado, requiere root) |
| `-sT` | Conexión TCP | Conexión TCP completa (no requiere root) |
| `-sU` | Escaneo UDP | Escanear puertos UDP |
| `-sA` | Escaneo ACK | Identificar las reglas del cortafuegos |
| `-sW` | Escaneo de ventana TCP | Similar a ACK; utiliza el campo de ventana |
| `-sM` | Escaneo Maimon | Sonda FIN/ACK |
| `-sN` | Escaneo NULL | Sin indicadores activados |
| `-sF` | Escaneo FIN | Indicador FIN activado |
| `-sX` | Escaneo Xmas | Indicadores FIN, PSH y URG |

### Escaneo SYN discreto (predeterminado)
```bash
# Escaneo más habitual (requiere root)
sudo nmap -sS 192.168.1.1

# No completa el establecimiento de la conexión TCP
# Rápido y discreto
```

### Escaneo mediante conexión TCP
```bash
# Conexión TCP completa
nmap -sT 192.168.1.1

# No requiere root
# Más detectable, pero siempre funciona
```

### Escaneo UDP
```bash
# Escanear puertos UDP
sudo nmap -sU 192.168.1.1

# El escaneo UDP es LENTO
# Combinar con la detección de versiones
sudo nmap -sU -sV 192.168.1.1

# Puertos UDP habituales
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### TCP/UDP combinados
```bash
# Escanear TCP y UDP
sudo nmap -sS -sU 192.168.1.1

# Puertos específicos
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### Escaneos NULL, FIN y Xmas
```bash
# Escaneo NULL (sin indicadores)
sudo nmap -sN 192.168.1.1

# Escaneo FIN (solo el indicador FIN)
sudo nmap -sF 192.168.1.1

# Escaneo Xmas (FIN, PSH y URG)
sudo nmap -sX 192.168.1.1

# Útiles para:
# - Eludir cortafuegos sin estado
# - No funciona en Windows
```

### Escaneo ACK
```bash
# Identificar las reglas del cortafuegos
sudo nmap -sA 192.168.1.1

# Determinar:
# - Qué puertos están filtrados
# - Reglas del cortafuegos
# Devuelve: unfiltered o filtered
```

### Escaneo de ventana TCP
```bash
# Similar al escaneo ACK
sudo nmap -sW 192.168.1.1

# Usa el tamaño de ventana TCP para determinar el estado
```

### Escaneo idle/zombie
```bash
# Máxima discreción: usar un equipo zombie
sudo nmap -sI zombie_host 192.168.1.1

# Tu IP nunca contacta con el objetivo
# Requiere un zombie adecuado
```

---

<a id="-especificación-de-puertos"></a>
## 🎚️ Especificación de puertos

### Opciones de puertos

| Opción | Descripción |
|--------|-------------|
| `-p <port>` | Escanear puertos concretos |
| `-p-` | Escanear los 65535 puertos |
| `-p 1-1000` | Escanear un intervalo de puertos |
| `--top-ports <n>` | Escanear los N puertos más frecuentes |
| `-F` | Escaneo rápido (los 100 puertos más frecuentes) |
| `-r` | Escanear los puertos de forma secuencial |

### Puertos específicos
```bash
# Un puerto
nmap -p 80 192.168.1.1

# Varios puertos
nmap -p 80,443,22 192.168.1.1

# Intervalo de puertos
nmap -p 1-1000 192.168.1.1

# Todos los puertos
nmap -p- 192.168.1.1

# Combinación de formatos
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Puertos por protocolo

> **Aclaración:** `T:` y `U:` seleccionan puertos por protocolo, pero no habilitan el tipo de escaneo. Los ejemplos con puertos UDP necesitan que también esté seleccionado el escaneo UDP, como en la sección de TCP/UDP combinados.
```bash
# Puertos TCP
nmap -p T:80,443 192.168.1.1

# Puertos UDP
nmap -p U:53,161 192.168.1.1

# Ambos
nmap -p T:80,443,U:53 192.168.1.1
```

### Puertos más frecuentes
```bash
# Los 100 puertos más frecuentes (rápido)
nmap -F 192.168.1.1

# Los 10 puertos más frecuentes
nmap --top-ports 10 192.168.1.1

# Los 1000 puertos más frecuentes (predeterminado)
nmap --top-ports 1000 192.168.1.1
```

### Referencia de puertos habituales

| Puerto | Servicio |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Detección de servicios y versiones

### Opciones de detección de versiones

| Opción | Descripción |
|--------|-------------|
| `-sV` | Detección de versiones |
| `--version-intensity <0-9>` | Intensidad de las sondas |
| `--version-light` | Escaneo ligero de versiones (intensidad 2) |
| `--version-all` | Probar todas las sondas (intensidad 9) |
| `--version-trace` | Depurar el escaneo de versiones |

### Detección básica de versiones
```bash
# Detectar las versiones de los servicios
nmap -sV 192.168.1.1

# Salida habitual:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensidad de la detección de versiones
```bash
# Escaneo ligero (más rápido)
nmap -sV --version-light 192.168.1.1

# Todas las sondas (exhaustivo)
nmap -sV --version-all 192.168.1.1

# Intensidad personalizada (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 Detección del sistema operativo

### Opciones de detección del sistema operativo

| Opción | Descripción |
|--------|-------------|
| `-O` | Habilitar la detección del sistema operativo |
| `--osscan-limit` | Escanear únicamente equipos con condiciones favorables |
| `--osscan-guess` | Estimar el sistema operativo de forma más agresiva |
| `--max-os-tries` | Número máximo de intentos de detección del sistema operativo |

### Detección básica del sistema operativo
```bash
# Detectar el sistema operativo (requiere root)
sudo nmap -O 192.168.1.1

# Ejemplo de salida:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Estimación agresiva del sistema operativo
```bash
# Estimación más agresiva
sudo nmap -O --osscan-guess 192.168.1.1
```

### Escaneo agresivo (-A)
```bash
# Habilitar detección de SO y versiones, scripts y traceroute
sudo nmap -A 192.168.1.1

# Equivale a: -O -sV -sC --traceroute
```

---

<a id="-temporización-y-rendimiento"></a>
## ⏱️ Temporización y rendimiento

### Plantillas de temporización

| Opción | Nombre | Descripción |
|--------|------|-------------|
| `-T0` | Paranoico | Evasión de IDS, muy lento |
| `-T1` | Sigiloso | Evasión de IDS, lento |
| `-T2` | Cortés | Menos ancho de banda, más lento |
| `-T3` | Normal | Temporización predeterminada |
| `-T4` | Agresivo | Rápido, para redes fiables |
| `-T5` | Extremo | Muy rápido; puede omitir puertos |

### Uso
```bash
# Lento y discreto
nmap -T1 192.168.1.1

# Normal (predeterminado)
nmap -T3 192.168.1.1

# Rápido
nmap -T4 192.168.1.1

# Muy rápido (red local)
nmap -T5 192.168.1.1
```

### Ajuste preciso de la temporización

| Opción | Descripción |
|--------|-------------|
| `--min-rate <n>` | Mínimo de paquetes por segundo |
| `--max-rate <n>` | Máximo de paquetes por segundo |
| `--min-parallelism <n>` | Mínimo de sondas en paralelo |
| `--max-parallelism <n>` | Máximo de sondas en paralelo |
| `--min-hostgroup <n>` | Mínimo de equipos en paralelo |
| `--max-hostgroup <n>` | Máximo de equipos en paralelo |
| `--host-timeout <time>` | Abandonar el equipo al transcurrir el tiempo indicado |
| `--scan-delay <time>` | Retardo entre sondas |
| `--max-retries <n>` | Máximo de reintentos por sonda |

```bash
# Enviar al menos 1000 paquetes por segundo
nmap --min-rate 1000 192.168.1.0/24

# Máximo de 100 paquetes por segundo (reducir la velocidad)
nmap --max-rate 100 192.168.1.1

# Agotar el tiempo del equipo tras 30 minutos
nmap --host-timeout 30m 192.168.1.0/24

# Retardo entre sondas
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 Scripts NSE

> **Categorías:** `auth` abarca tareas de autenticación y no solo evasiones. `-sC` y los comodines amplios pueden seleccionar scripts intrusivos; una etiqueta no garantiza ausencia de impacto. `--script-updatedb` reconstruye el índice local, no descarga actualizaciones. Véase la [documentación oficial de NSE](https://nmap.org/book/man-nse.html).

### Categorías de scripts

| Categoría | Descripción |
|----------|-------------|
| `auth` | Evasión de autenticación |
| `broadcast` | Descubrir equipos mediante difusión |
| `brute` | Ataques de fuerza bruta |
| `default` | Scripts predeterminados (-sC) |
| `discovery` | Descubrimiento de servicios |
| `dos` | Denegación de servicio |
| `exploit` | Explotar vulnerabilidades |
| `external` | Consultar servicios externos |
| `fuzzer` | Pruebas con entradas variadas (fuzzing) |
| `intrusive` | Puede provocar fallos en servicios |
| `malware` | Detección de malware |
| `safe` | Scripts seguros |
| `version` | Detección de versiones |
| `vuln` | Detección de vulnerabilidades |

### Opciones de scripts

| Opción | Descripción |
|--------|-------------|
| `-sC` | Scripts predeterminados |
| `--script <scripts>` | Ejecutar scripts concretos |
| `--script-args <args>` | Argumentos del script |
| `--script-updatedb` | Actualizar la base de datos de scripts |
| `--script-help <script>` | Ayuda del script |

### Scripts predeterminados
```bash
# Ejecutar los scripts predeterminados
nmap -sC 192.168.1.1

# Con detección de versiones (recomendado)
nmap -sV -sC 192.168.1.1
```

### Ejecutar scripts concretos
```bash
# Un script
nmap --script http-title 192.168.1.1

# Varios scripts
nmap --script http-title,http-headers 192.168.1.1

# Categoría
nmap --script vuln 192.168.1.1

# Comodín
nmap --script "http-*" 192.168.1.1

# Combinar categorías
nmap --script "vuln and safe" 192.168.1.1

# Excluir scripts
nmap --script "not intrusive" 192.168.1.1
```

### Scripts habituales

#### Escaneo de vulnerabilidades
```bash
# Todos los scripts de vulnerabilidades
nmap --script vuln 192.168.1.1

# Comprobación de un CVE concreto
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### Scripts HTTP
```bash
# Enumeración HTTP
nmap --script http-enum 192.168.1.1

# Cabeceras HTTP
nmap --script http-headers 192.168.1.1

# Métodos HTTP
nmap --script http-methods 192.168.1.1

# Título HTTP
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### Scripts SMB
```bash
# Enumeración SMB
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# Vulnerabilidades SMB
nmap --script smb-vuln* 192.168.1.1
```

#### Scripts SSH
```bash
# Métodos de autenticación SSH
nmap --script ssh-auth-methods 192.168.1.1

# Clave de host SSH
nmap --script ssh-hostkey 192.168.1.1

# Fuerza bruta SSH
nmap --script ssh-brute 192.168.1.1
```

#### Scripts DNS
```bash
# Fuerza bruta DNS
nmap --script dns-brute example.com

# Transferencia de zona DNS
nmap --script dns-zone-transfer example.com
```

#### Scripts MySQL y de bases de datos
```bash
# Información de MySQL
nmap --script mysql-info 192.168.1.1

# Enumeración de MySQL
nmap --script mysql-enum 192.168.1.1

# Fuerza bruta MySQL
nmap --script mysql-brute 192.168.1.1
```

### Argumentos de scripts
```bash
# Fuerza bruta con una lista de palabras
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Fuerza bruta de autenticación HTTP
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Formatos de salida

### Opciones de salida

| Opción | Descripción |
|--------|-------------|
| `-oN <file>` | Salida normal |
| `-oX <file>` | Salida XML |
| `-oG <file>` | Salida apta para grep |
| `-oA <basename>` | Todos los formatos |
| `-oS <file>` | Salida estilizada script kiddie |

### Guardar la salida
```bash
# Salida normal
nmap -oN scan.txt 192.168.1.1

# Salida XML (para herramientas)
nmap -oX scan.xml 192.168.1.1

# Salida apta para grep
nmap -oG scan.gnmap 192.168.1.1

# Todos los formatos a la vez
nmap -oA scan 192.168.1.1
# Crea: scan.nmap, scan.xml, scan.gnmap
```

### Nivel de detalle y depuración

| Opción | Descripción |
|--------|-------------|
| `-v` | Aumentar el nivel de detalle |
| `-vv` | Más detallado |
| `-d` | Modo de depuración |
| `-dd` | Más información de depuración |
| `--reason` | Mostrar el motivo del estado del puerto |
| `--open` | Mostrar solo los puertos abiertos |
| `--packet-trace` | Mostrar todos los paquetes |

```bash
# Salida detallada
nmap -v 192.168.1.1

# Muy detallado
nmap -vv 192.168.1.1

# Mostrar solo los puertos abiertos
nmap --open 192.168.1.1

# Mostrar el motivo
nmap --reason 192.168.1.1
```

---

<a id="-evasión-de-cortafuegosids"></a>
## 🛡️ Evasión de cortafuegos/IDS

### Técnicas de evasión

| Opción | Descripción |
|--------|-------------|
| `-f` | Fragmentar paquetes |
| `--mtu <size>` | Tamaño MTU personalizado |
| `-D <decoys>` | Usar señuelos |
| `-S <IP>` | Suplantar la IP de origen |
| `--source-port <port>` | Suplantar el puerto de origen |
| `--data-length <n>` | Añadir datos aleatorios |
| `--randomize-hosts` | Orden aleatorio de equipos |
| `--spoof-mac <mac>` | Suplantar la dirección MAC |
| `--badsum` | Enviar sumas de comprobación incorrectas |

### Fragmentación de paquetes
```bash
# Fragmentar paquetes
nmap -f 192.168.1.1

# MTU personalizado
nmap --mtu 16 192.168.1.1
```

### Señuelos
```bash
# Usar señuelos
nmap -D decoy1,decoy2,ME 192.168.1.1

# Señuelos aleatorios
nmap -D RND:5 192.168.1.1
```

### Suplantación de IP y puerto de origen
```bash
# Suplantar la IP de origen
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Suplantar el puerto de origen (puertos permitidos habituales)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### Suplantación de MAC
```bash
# MAC aleatoria
nmap --spoof-mac 0 192.168.1.1

# Fabricante concreto
nmap --spoof-mac Apple 192.168.1.1

# MAC concreta
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Relleno de datos
```bash
# Añadir datos aleatorios a los paquetes
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Barrido rápido de red
```bash
# Encontrar equipos activos
nmap -sn 192.168.1.0/24
```

### Ejemplo 2: Escaneo básico de puertos
```bash
# Escanear con detección de versiones
nmap -sV 192.168.1.1
```

### Ejemplo 3: Escaneo exhaustivo
```bash
# Escaneo completo con detección de SO y scripts
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Ejemplo 4: Escaneo completo agresivo
```bash
# Todos los puertos, agresivo
sudo nmap -A -T4 -p- 192.168.1.1
```

### Ejemplo 5: Escaneo de vulnerabilidades
```bash
# Buscar vulnerabilidades
nmap --script vuln 192.168.1.1
```

### Ejemplo 6: Escaneo discreto
```bash
# Escaneo discreto
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Ejemplo 7: Escaneo de servidor web
```bash
# Escaneo web exhaustivo
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Ejemplo 8: Enumeración SMB
```bash
# Enumeración SMB completa
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Ejemplo 9: Escaneo completo de subred
```bash
# Escanear toda la subred
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Ejemplo 10: Servicios UDP
```bash
# Servicios UDP habituales
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `nmap <target>` | Escaneo básico |
| `nmap -sn <target>` | Solo escaneo de ping |
| `nmap -Pn <target>` | Omitir el descubrimiento de equipos |
| `nmap -sS <target>` | Escaneo SYN discreto |
| `nmap -sV <target>` | Detección de versiones |
| `nmap -O <target>` | Detección del sistema operativo |
| `nmap -A <target>` | Escaneo agresivo |
| `nmap -sC <target>` | Scripts predeterminados |
| `nmap -p- <target>` | Todos los puertos |
| `nmap --script vuln <target>` | Escaneo de vulnerabilidades |

### Velocidad frente a discreción

| Necesidad | Opciones |
|------|---------|
| **Rápido** | `-T4 --min-rate 1000` |
| **Discreto** | `-T1 -f --data-length 25` |
| **Equilibrado** | `-T3` (predeterminado) |

### Escaneos habituales

```bash
# Escaneo rápido
nmap -F 192.168.1.1

# Escaneo TCP completo
nmap -sT -p- 192.168.1.1

# Escaneo de servicios
nmap -sV 192.168.1.1

# Escaneo de vulnerabilidades
nmap --script vuln 192.168.1.1

# Todo
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Usar el escaneo SYN (predeterminado)**
   ```bash
   # Requiere root, pero es el más rápido
   sudo nmap -sS 192.168.1.1
   ```

2. **Limitar inicialmente el intervalo de puertos**
   ```bash
   # Empezar por los puertos más frecuentes
   nmap --top-ports 100 192.168.1.1
   ```

3. **Aumentar la velocidad en redes locales**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Consejos para mejorar la precisión

> **Reintentos:** `--max-retries 3` fija un máximo; no aumenta necesariamente el valor predeterminado. Las velocidades elevadas y límites bajos pueden reducir la precisión. Véase [Temporización y rendimiento](https://nmap.org/book/man-performance.html).

1. **Varios tipos de escaneo**
   ```bash
   # TCP y UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Aumentar los reintentos en redes poco fiables**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Consideraciones legales

> ⚠️ **IMPORTANTE:** Escanea únicamente redes propias o para las que tengas permiso.

- ✅ Redes propias
- ✅ Entornos de laboratorio
- ✅ Pruebas de penetración autorizadas
- ❌ Equipos aleatorios de Internet
- ❌ Redes sin permiso

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Nmap es una herramienta potente que debe utilizarse únicamente para **escaneos autorizados**.
> 
> - ✅ Escanear redes propias
> - ✅ Escanear con permiso explícito por escrito
> - ✅ Usar en pruebas de penetración autorizadas
> - ❌ Nunca escanear redes sin autorización
> - ❌ El escaneo sin autorización puede ser ilegal
> 
> **El escaneo de redes sin autorización es ilegal en muchas jurisdicciones.**

---

## 📚 Recursos

### Recursos oficiales
- [Sitio web oficial de Nmap](https://nmap.org/)
- [Guía de referencia de Nmap](https://nmap.org/book/man.html)
- [Documentación de scripts NSE](https://nmap.org/nsedoc/)

### Recursos de aprendizaje
- [Libro Nmap Network Scanning](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guías de referencia relacionadas
- [Metasploit Framework](../../Metasploit/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Domina el reconocimiento de redes!</b><br>
  <i>Conoce tu red antes que los atacantes</i>
</p>
