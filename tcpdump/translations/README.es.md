# 📡 tcpdump: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Aclaraciones sobre los ejemplos:** Se conservan todos los filtros originales. En el ejemplo 7, las comillas simples impiden que el shell expanda `$(...)`, por lo que ese comando no obtiene la IP como pretende su título. `tcpdump -w capture.pcap | tee` guarda paquetes, pero no genera por sí solo su visualización textual simultánea. `-Z user` reduce privilegios tras abrir el dispositivo de captura, no al terminar de capturar. Véase el [manual oficial en el repositorio de tcpdump](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in). Los filtros de DNS por puerto 53 no incluyen DNS cifrado; `-s 96` limita bytes y puede recortar cabeceras. Ver paquetes SYN por sí solo no confirma una exploración de puertos.


```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Analizador de paquetes de línea de comandos
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 El clásico analizador de paquetes de línea de comandos</b>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Instalación](#instalación)
- [Sintaxis básica](#sintaxis-básica)
- [Opciones habituales](#opciones-habituales)
- [Filtros BPF](#filtros-bpf)
- [Filtros de protocolo](#filtros-de-protocolo)
- [Opciones de salida](#opciones-de-salida)
- [Ejemplos prácticos](#ejemplos-prácticos)
- [Referencia rápida](#referencia-rápida)
- [Consejos y buenas prácticas](#consejos-y-buenas-prácticas)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**tcpdump** es un potente analizador de paquetes de línea de comandos disponible en la mayoría de los sistemas operativos de tipo Unix. Utiliza la biblioteca libpcap y la sintaxis Berkeley Packet Filter (BPF).

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Ligero** | Sin interfaz gráfica y con un consumo mínimo de recursos |
| **Filtros BPF** | Potente filtrado de paquetes |
| **Compatible con pcap** | Lectura y escritura de archivos pcap |
| **Automatizable mediante scripts** | Ideal para automatizar |
| **Universal** | Disponible en los sistemas Unix |

### tcpdump vs Wireshark

| Característica | tcpdump | Wireshark |
|---------|---------|-----------|
| **Interfaz** | CLI | GUI |
| **Recursos** | Mínimos | Mayor consumo |
| **Uso remoto** | Mediante SSH de forma nativa | Requiere configuración |
| **Automatización mediante scripts** | Sencillo | Complejo |
| **Análisis** | Básico | Avanzado |

---

<a id="instalación"></a>

## 📥 Instalación

### Linux (preinstalado en la mayoría)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (preinstalado)
```bash
tcpdump --version
```

### Verificar la instalación
```bash
tcpdump --version
tcpdump -D              # Listar interfaces
```

---

<a id="sintaxis-básica"></a>

## ⌨️ Sintaxis básica

### Sintaxis general
```bash
tcpdump [options] [filter expression]
```

### Comandos básicos
```bash
# Capturar en la interfaz predeterminada
sudo tcpdump

# Capturar en una interfaz específica
sudo tcpdump -i eth0

# Capturar un número limitado de paquetes
sudo tcpdump -c 100

# Capturar con salida detallada
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="opciones-habituales"></a>

## ⚙️ Opciones habituales

### Opciones de visualización

| Opción | Descripción |
|--------|-------------|
| `-i` | Interfaz en la que se realizará la captura |
| `-c` | Número de paquetes que se capturarán |
| `-n` | No resolver nombres de host |
| `-nn` | No resolver nombres de host ni de puertos |
| `-v` | Salida detallada |
| `-vv` | Más detalle |
| `-vvv` | Nivel máximo de detalle |
| `-q` | Salida silenciosa o reducida |
| `-t` | No mostrar la marca de tiempo |
| `-tt` | Mostrar la marca de tiempo Unix |
| `-ttt` | Mostrar la diferencia de tiempo |
| `-tttt` | Mostrar fecha y hora |

### Opciones de captura

| Opción | Descripción |
|--------|-------------|
| `-w` | Escribir en un archivo pcap |
| `-r` | Leer desde un archivo pcap |
| `-s` | Longitud capturada por paquete (bytes) |
| `-s0` | Capturar paquetes completos |
| `-D` | Listar las interfaces disponibles |

### Opciones de salida

| Opción | Descripción |
|--------|-------------|
| `-A` | Mostrar ASCII |
| `-X` | Mostrar hexadecimal y ASCII |
| `-XX` | Mostrar hexadecimal y ASCII con cabeceras |
| `-e` | Mostrar la cabecera de la capa de enlace |
| `-l` | Salida con búfer por líneas |

### Combinaciones habituales
```bash
# Sin resolución DNS, con detalle y paquetes completos
sudo tcpdump -nnvvs0 -i eth0

# Guardar en un archivo
sudo tcpdump -i eth0 -w capture.pcap

# Leer desde un archivo
tcpdump -r capture.pcap

# Salida ASCII
sudo tcpdump -A -i eth0

# Hexadecimal y ASCII
sudo tcpdump -X -i eth0
```

---

<a id="filtros-bpf"></a>

## 🎣 Filtros BPF

### Filtros de host

```bash
# Tráfico hacia o desde un host específico
tcpdump host 192.168.1.1

# Solo el host de origen
tcpdump src host 192.168.1.1

# Solo el host de destino
tcpdump dst host 192.168.1.1

# Varios hosts
tcpdump host 192.168.1.1 or host 192.168.1.2

# Excluir un host
tcpdump not host 192.168.1.1

# Red o subred
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Filtros de puerto

```bash
# Puerto específico
tcpdump port 80
tcpdump port 443

# Puerto de origen
tcpdump src port 443

# Puerto de destino
tcpdump dst port 22

# Rango de puertos
tcpdump portrange 1-1024

# Varios puertos
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Filtros de protocolo

```bash
# Por protocolo
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protocolo y puerto
tcpdump tcp port 80
tcpdump udp port 53
```

### Operadores lógicos

| Operador | Alternativa | Descripción |
|----------|-------------|-------------|
| `and` | `&&` | AND lógico |
| `or` | `\|\|` | OR lógico |
| `not` | `!` | NOT lógico |
| `()` | | Agrupación |

### Filtros combinados

```bash
# Host Y puerto
tcpdump host 192.168.1.1 and port 80

# Host Y protocolo
tcpdump host 192.168.1.1 and tcp

# Filtro complejo
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Excluir tráfico específico
tcpdump not port 22 and not port 53

# Tráfico entre dos hosts
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

<a id="filtros-de-protocolo"></a>

## 📡 Filtros de protocolo

### Banderas TCP

```bash
# Paquetes SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# Paquetes SYN-ACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# Paquetes FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# Paquetes RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# Paquetes PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Solo ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Referencia de banderas TCP

| Bandera | Hexadecimal | Decimal | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### Tráfico HTTP

```bash
# Solicitudes HTTP (puerto 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# Solicitudes GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# Solicitudes POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Captura HTTP sencilla
tcpdump -A -s0 'tcp port 80'
```

### Tráfico DNS

```bash
# Todo el tráfico DNS
tcpdump port 53
tcpdump udp port 53

# Solo consultas DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Solo respuestas DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### Tráfico ICMP

```bash
# Todo el tráfico ICMP
tcpdump icmp

# Solicitud de eco ICMP (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# Respuesta de eco ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# Destino inalcanzable ICMP
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### Tráfico ARP

```bash
# Todo el tráfico ARP
tcpdump arp

# Solicitudes ARP
tcpdump 'arp[6:2] == 1'

# Respuestas ARP
tcpdump 'arp[6:2] == 2'
```

---

<a id="opciones-de-salida"></a>

## 💾 Opciones de salida

### Escribir en un archivo

```bash
# Escribir en un archivo pcap
tcpdump -w capture.pcap

# Escribir con rotación (100 archivos de 1 MB cada uno)
tcpdump -w capture.pcap -W 100 -C 1

# Escribir con una marca de tiempo en el nombre del archivo
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Capturar y mostrar (descripción del original)
tcpdump -w capture.pcap | tee
```

### Leer desde un archivo

```bash
# Leer un archivo pcap
tcpdump -r capture.pcap

# Leer con filtros
tcpdump -r capture.pcap 'host 192.168.1.1'

# Contar paquetes
tcpdump -r capture.pcap | wc -l

# Leer con detalle
tcpdump -nnvvXSs0 -r capture.pcap
```

### Formatos de visualización

```bash
# Contenido ASCII
tcpdump -A -i eth0

# Hexadecimal y ASCII
tcpdump -X -i eth0

# Solo hexadecimal
tcpdump -x -i eth0

# Con cabeceras Ethernet
tcpdump -XX -i eth0

# Mostrar números de secuencia absolutos
tcpdump -S -i eth0
```

---

<a id="ejemplos-prácticos"></a>

## 🎬 Ejemplos prácticos

### Ejemplo 1: capturar tráfico web
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Ejemplo 2: capturar consultas DNS
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Ejemplo 3: capturar tráfico de un host específico
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Ejemplo 4: encontrar conexiones SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Ejemplo 5: capturar ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Ejemplo 6: detectar una exploración de puertos (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Ejemplo 7: capturar solo tráfico entrante
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Ejemplo 8: supervisar una conversación específica
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Ejemplo 9: capturar credenciales FTP
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Ejemplo 10: guardar para analizar en Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Después, abrir capture.pcap en Wireshark
```

### Ejemplo 11: captura remota mediante SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Ejemplo 12: capturar excluyendo SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Listar interfaces | `tcpdump -D` |
| Captura básica | `tcpdump -i eth0` |
| Sin resolución DNS | `tcpdump -nn` |
| Capturar N paquetes | `tcpdump -c 100` |
| Escribir en un archivo | `tcpdump -w file.pcap` |
| Leer desde un archivo | `tcpdump -r file.pcap` |
| Salida detallada | `tcpdump -v` |
| Salida ASCII | `tcpdump -A` |
| Salida hexadecimal | `tcpdump -X` |
| Paquete completo | `tcpdump -s0` |

### Filtros habituales

| Filtro | Descripción |
|--------|-------------|
| `host 1.2.3.4` | Tráfico hacia o desde el host |
| `port 80` | Tráfico en el puerto 80 |
| `tcp` | Solo tráfico TCP |
| `udp` | Solo tráfico UDP |
| `icmp` | Solo tráfico ICMP |
| `net 192.168.1.0/24` | Tráfico hacia o desde la subred |
| `src host 1.2.3.4` | El origen es el host |
| `dst port 443` | Puerto de destino 443 |

### Combinaciones de filtros

| Ejemplo | Descripción |
|---------|-------------|
| `host A and port 80` | Host A, puerto 80 |
| `port 80 or port 443` | Puerto 80 o 443 |
| `not port 22` | Excluir SSH |
| `tcp and port 80` | Puerto TCP 80 |

---

<a id="consejos-y-buenas-prácticas"></a>

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

```bash
# No resolver DNS (más rápido)
tcpdump -nn

# Limitar el tamaño de captura por paquete
tcpdump -s 96   # Solo cabeceras (descripción del original)

# Usar filtros BPF para reducir la carga
tcpdump 'host 192.168.1.1'  # Filtrar a nivel del kernel
```

### Consejos de seguridad

```bash
# Ejecutar sin root (si se ha configurado)
# O minimizar el tiempo de captura

# Reducir los privilegios después de capturar (descripción del original)
tcpdump -Z user
```

### Alias útiles

```bash
# Añadir a ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** La captura de paquetes de red solo debe utilizarse para **fines autorizados**.
> 
> - ✅ Capturar en redes propias
> - ✅ Pruebas de seguridad con permiso
> - ✅ Resolución de problemas en sistemas autorizados
> - ❌ No capturar en redes sin autorización
> - ❌ Capturar el tráfico de terceros puede ser ilegal
> 
> **La supervisión de redes sin autorización es ilegal.**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [Manual de tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Sitio oficial de tcpdump](https://www.tcpdump.org/)
- [Sintaxis de filtros BPF](https://biot.com/capstats/bpf.html)

### Guías rápidas relacionadas
- [Wireshark](../../Wireshark/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)

---

<p align="center">
  <b>📡 ¡Domina el análisis de paquetes desde la línea de comandos!</b><br>
  <i>La alternativa ligera a Wireshark</i>
</p>
