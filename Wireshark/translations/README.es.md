# 🦈 Wireshark - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           El analizador de protocolos de red
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Captura de paquetes">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocolo">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Código abierto">
</p>

<p align="center">
  <b>🌐 El analizador de protocolos de red más popular del mundo</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Resumen de la interfaz](#-resumen-de-la-interfaz)
- [Fundamentos de captura](#-fundamentos-de-captura)
- [Filtros de visualización](#-filtros-de-visualización)
- [Filtros de captura](#-filtros-de-captura)
- [Análisis de protocolos](#-análisis-de-protocolos)
- [Seguimiento de flujos](#-seguimiento-de-flujos)
- [Estadísticas y análisis](#-estadísticas-y-análisis)
- [tshark (CLI)](#-tshark-cli)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Atajos de teclado](#-atajos-de-teclado)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Wireshark** es un analizador de paquetes gratuito y de código abierto para resolver problemas de red, realizar análisis, desarrollar software y auditar la seguridad.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Inspección profunda** | Compatibilidad con cientos de protocolos |
| **Captura en vivo** | Captura de paquetes en tiempo real |
| **Análisis sin conexión** | Leer archivos de captura (pcap, pcapng) |
| **Filtros potentes** | Filtros de visualización y de captura |
| **Multiplataforma** | Windows, Linux, macOS |
| **Interfaz gráfica y línea de comandos** | Wireshark (interfaz gráfica) y tshark (línea de comandos) |

### Casos de uso

| Caso de uso | Descripción |
|----------|-------------|
| **Resolución de problemas de red** | Diagnosticar problemas de conectividad |
| **Análisis de seguridad** | Detectar tráfico malicioso |
| **Desarrollo de protocolos** | Probar protocolos nuevos |
| **Análisis forense** | Analizar el tráfico capturado |
| **Formación** | Aprender redes |

---

## 📥 Instalación

### Windows
```bash
# Descargar desde el sitio oficial
https://www.wireshark.org/download.html

# O mediante Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (preinstalado)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Permitir la captura sin ser root
sudo usermod -aG wireshark $USER
# Cerrar la sesión e iniciarla de nuevo
```

### macOS
```bash
brew install --cask wireshark
```

### Verificar la instalación
```bash
wireshark --version
tshark --version
```

---

<a id="-resumen-de-la-interfaz"></a>
## 🖥️ Resumen de la interfaz

Los recorridos de menús, las etiquetas de columnas y los nombres de opciones se conservan en inglés para que puedan reconocerse en la interfaz. Pueden variar con el idioma o la versión instalada.

### Áreas de la ventana principal

| Área | Descripción |
|------|-------------|
| **Barra de menús (Menu Bar)** | File, Edit, View, Go, Capture, Analyze |
| **Barra principal de herramientas (Main Toolbar)** | Botones de acceso rápido |
| **Barra de filtros (Filter Bar)** | Introducir filtros de visualización |
| **Lista de paquetes (Packet List)** | Todos los paquetes capturados |
| **Detalles del paquete (Packet Details)** | Capas de protocolo del paquete seleccionado |
| **Bytes del paquete (Packet Bytes)** | Datos sin procesar en hexadecimal/ASCII |

### Columnas de la lista de paquetes (predeterminadas)

| Columna | Descripción |
|--------|-------------|
| No. | Número de paquete |
| Time | Marca temporal |
| Source | IP/MAC de origen |
| Destination | IP/MAC de destino |
| Protocol | Protocolo de la capa superior |
| Length | Tamaño del paquete en bytes |
| Info | Información específica del protocolo |

---

## 📡 Fundamentos de captura

### Iniciar la captura

```
1. Seleccionar una interfaz en la pantalla de bienvenida
2. Pulsar "Start Capture" (aleta azul de tiburón)
3. Or: Capture → Start

Acceso rápido: hacer doble clic en el nombre de la interfaz
```

### Detener la captura

```
Pulsar el cuadrado rojo de la barra de herramientas
Or: Capture → Stop
O bien: Ctrl+E
```

### Guardar la captura

```
File → Save As
Formatos: pcapng (predeterminado), pcap, etc.
```

### Abrir un archivo de captura

```
File → Open
O bien: Ctrl+O
O bien: wireshark file.pcap
```

---

## 🔍 Filtros de visualización

### Sintaxis de filtros

Los filtros de visualización usan una **sintaxis de comparación de campos**:

```
field operator value
```

### Operadores de comparación

> **Versiones:** la semántica de `!=` para campos con varios valores cambió en Wireshark 3.6. Las expresiones regulares también requieren distinguir el escape de la cadena del escape de la regex. Véase la [guía oficial de filtros](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html).

| Operador | Descripción | Ejemplo |
|----------|-------------|---------|
| `==` o `eq` | Igual | `ip.addr == 192.168.1.1` |
| `!=` o `ne` | Distinto | `ip.addr != 10.0.0.1` |
| `>` o `gt` | Mayor que | `frame.len > 100` |
| `<` o `lt` | Menor que | `tcp.port < 1024` |
| `>=` o `ge` | Mayor o igual | `frame.len >= 64` |
| `<=` o `le` | Menor o igual | `tcp.port <= 1024` |
| `contains` | Contiene una cadena | `http contains "password"` |
| `matches` | Coincidencia con una expresión regular | `http.host matches ".*\.com"` |

### Operadores lógicos

| Operador | Descripción | Ejemplo |
|----------|-------------|---------|
| `and` o `&&` | AND lógico | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` o `\|\|` | OR lógico | `tcp.port == 80 or tcp.port == 443` |
| `not` o `!` | NOT lógico | `not arp` |
| `()` | Agrupación | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### Filtros IP

```bash
# Dirección IP concreta (origen o destino)
ip.addr == 192.168.1.1

# Solo IP de origen
ip.src == 192.168.1.1

# Solo IP de destino
ip.dst == 192.168.1.1

# Intervalo IP (subred)
ip.addr == 192.168.1.0/24

# Excluir una IP
ip.addr != 192.168.1.1

# Entre dos equipos
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Filtros de puertos

```bash
# Puerto concreto (origen o destino)
tcp.port == 80
udp.port == 53

# Solo puerto de origen
tcp.srcport == 443

# Solo puerto de destino
tcp.dstport == 22

# Intervalo de puertos
tcp.port >= 1 and tcp.port <= 1024

# Varios puertos
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Filtros de protocolo

```bash
# Por nombre de protocolo
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# Específicos de HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Específicos de DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # Registro A
dns.flags.response == 1    # Respuestas DNS

# Específicos de TCP
tcp.flags.syn == 1         # Paquetes SYN
tcp.flags.fin == 1         # Paquetes FIN
tcp.flags.reset == 1       # Paquetes RST
tcp.analysis.retransmission  # Retransmisiones
tcp.stream == 5            # Flujo concreto
```

### Filtros de contenido

```bash
# Contiene una cadena
http contains "password"
tcp contains "login"
frame contains "secret"

# Contenido HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Coincidencia sin distinguir mayúsculas de minúsculas
http.host matches "(?i)example"
```

### Ejemplos habituales de filtros

> **Interpretación:** el filtro de SYN o ACK mostrado como «handshake» también incluye muchos paquetes posteriores de la conexión. Un RST o una consulta DNS grande, por sí solos, no demuestran un fallo, un escaneo o una exfiltración.

```bash
# Solo tráfico HTTP
http

# Tráfico HTTPS
tls

# Consultas DNS
dns.flags.response == 0

# Conexiones fallidas (RST)
tcp.flags.reset == 1

# Paquetes grandes
frame.len > 1000

# Dirección MAC concreta
eth.addr == aa:bb:cc:dd:ee:ff

# Tráfico de difusión
eth.dst == ff:ff:ff:ff:ff:ff

# Tráfico no local
!(ip.addr == 192.168.0.0/16)

# Establecimiento de conexión TCP en tres pasos
tcp.flags.syn == 1 or tcp.flags.ack == 1

# Solicitudes HTTP POST con datos
http.request.method == "POST" and http.file_data
```

---

## 🎣 Filtros de captura

### Sintaxis BPF (Berkeley Packet Filter)

Los filtros de captura usan **sintaxis BPF** (¡diferente de la de los filtros de visualización!).

### Sintaxis básica

```bash
# Equipo
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Red
net 192.168.1.0/24
src net 10.0.0.0/8

# Puerto
port 80
src port 443
dst port 22
portrange 1-1024

# Protocolo
tcp
udp
icmp
arp
```

### Filtros combinados

```bash
# Equipo y puerto
host 192.168.1.1 and port 80

# Conversación concreta
host 192.168.1.1 and host 192.168.1.2

# Excluir tráfico
not port 22
not host 10.0.0.1

# Filtro complejo
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Filtros de captura habituales

| Finalidad | Filtro |
|---------|--------|
| Equipo concreto | `host 192.168.1.1` |
| Tráfico web | `port 80 or port 443` |
| Solo DNS | `port 53` |
| Solo SSH | `port 22` |
| Sin difusión | `not broadcast` |
| Subred concreta | `net 192.168.1.0/24` |

---

## 🔬 Análisis de protocolos

### Análisis de TCP

```bash
# Indicadores TCP
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Solo SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# Problemas de TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# Flujo TCP
tcp.stream == 0
```

### Análisis de HTTP

```bash
# Solicitudes HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# Respuestas HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Errores

# Contenido HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### Análisis de DNS

```bash
# Consultas DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # Registro A
dns.qry.type == 28     # Registro AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# Respuestas DNS
dns.flags.response == 1
dns.flags.rcode != 0   # Errores
```

### Análisis de TLS/SSL

> **Precisión:** `tls.record.version == 0x0303` no identifica de forma exclusiva TLS 1.2: TLS 1.3 mantiene ese valor heredado en la capa de registros. Examina la versión negociada en el handshake; véase [RFC 8446](https://www.rfc-editor.org/rfc/rfc8446.html). El filtro `tls` tampoco se limita a HTTPS.

```bash
# Tráfico TLS
tls

# Negociación TLS
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Certificados
tls.handshake.certificate

# Versión de TLS
tls.record.version == 0x0303  # TLS 1.2
```

### Análisis de ARP

```bash
# Todo ARP
arp

# Solicitudes ARP
arp.opcode == 1

# Respuestas ARP
arp.opcode == 2

# ARP gratuito (posible suplantación)
arp.isgratuitous == 1
```

---

## 🔗 Seguimiento de flujos

### Seguir un flujo TCP

```
Right-click packet → Follow → TCP Stream

Or: Analyze → Follow → TCP Stream
```

### Seguir un flujo HTTP

```
Right-click HTTP packet → Follow → HTTP Stream
```

### Seguir un flujo UDP

```
Right-click UDP packet → Follow → UDP Stream
```

### Ventana del flujo

- Muestra la conversación completa
- Colores para distinguir cliente y servidor
- Guardar como texto o datos sin procesar
- Botones para filtrar por dirección

---

## 📊 Estadísticas y análisis

### Jerarquía de protocolos

```
Statistics → Protocol Hierarchy

Muestra el desglose de los protocolos capturados
```

### Conversaciones

```
Statistics → Conversations

Ver todas las conversaciones por:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Extremos de comunicación

```
Statistics → Endpoints

Listar todos los extremos de comunicación únicos
```

### Gráfico de E/S

```
Statistics → IO Graph

Visualizar el tráfico a lo largo del tiempo
```

### Información experta

```
Analyze → Expert Information

Muestra advertencias, errores y notas:
- Retransmisiones TCP
- Paquetes malformados
- Errores
```

### Estadísticas HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (CLI)

### Uso básico

```bash
# Capturar paquetes
tshark -i eth0

# Leer un archivo de captura
tshark -r capture.pcap

# Escribir en un archivo
tshark -i eth0 -w output.pcap
```

### Aplicar filtros

```bash
# Filtro de captura
tshark -i eth0 -f "port 80"

# Filtro de visualización
tshark -r capture.pcap -Y "http"
```

### Opciones de salida

```bash
# Mostrar campos concretos
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Salida JSON
tshark -r capture.pcap -T json

# Mostrar estadísticas
tshark -r capture.pcap -q -z io,stat,1
```

### Ejemplos habituales de tshark

```bash
# Capturar solicitudes HTTP en vivo
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Extraer consultas DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Contar paquetes por IP
tshark -r capture.pcap -q -z ip_hosts,tree

# Estadísticas HTTP
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Capturar tráfico HTTP
```bash
# Filtro de captura: port 80
# Filtro de visualización: http
# Seguir el flujo HTTP para ver solicitudes y respuestas completas
```

### Ejemplo 2: Encontrar credenciales
```bash
# Filtro de visualización
http.request.method == "POST" and http contains "password"

# O para FTP
ftp contains "PASS"
```

### Ejemplo 3: Detectar un escaneo de puertos
```bash
# Varios SYN dirigidos a puertos diferentes
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Buscar muchos destinos desde un solo origen
Statistics → Conversations → TCP
```

### Ejemplo 4: Exfiltración mediante DNS
```bash
# Consultas DNS grandes
dns and frame.len > 512

# Registros TXT inusuales
dns.qry.type == 16
```

### Ejemplo 5: Extraer archivos
```
File → Export Objects → HTTP

Seleccionar los archivos que se van a exportar
```

### Ejemplo 6: Analizar la negociación TLS
```bash
tls.handshake

# Examinar Client Hello para ver SNI
tls.handshake.type == 1
```

### Ejemplo 7: Encontrar contraseñas en texto claro
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Referencia rápida

### Filtros de visualización esenciales

| Finalidad | Filtro |
|---------|--------|
| Tráfico HTTP | `http` |
| Tráfico DNS | `dns` |
| IP concreta | `ip.addr == 192.168.1.1` |
| Puerto concreto | `tcp.port == 80` |
| Problemas de TCP | `tcp.analysis.flags` |
| Errores HTTP | `http.response.code >= 400` |
| Contiene una cadena | `frame contains "text"` |
| Excluir un protocolo | `not arp` |

### Filtros de captura habituales

| Finalidad | Filtro |
|---------|--------|
| Equipo concreto | `host 192.168.1.1` |
| Tráfico web | `port 80 or port 443` |
| Solo DNS | `port 53` |
| Sin difusión | `not broadcast` |

---

<a id="-atajos-de-teclado"></a>
## ⌨️ Atajos de teclado

> **Atajos:** la tabla original contiene asignaciones que pueden no coincidir con la versión actual. Por ejemplo, la guía actual asigna `Ctrl+→` y `Ctrl+←` a expandir y contraer el árbol de detalles, y `Ctrl+.` al siguiente paquete de la conversación. Consulta la [navegación oficial](https://www.wireshark.org/docs/wsug_html_chunked/ChUseMainWindowSection.html) y los atajos de tu instalación.

| Atajo | Action |
|----------|--------|
| `Ctrl+E` | Iniciar/detener la captura |
| `Ctrl+K` | Opciones de captura |
| `Ctrl+O` | Abrir un archivo |
| `Ctrl+S` | Guardar el archivo |
| `Ctrl+F` | Buscar un paquete |
| `Ctrl+G` | Ir a un paquete |
| `Ctrl+→` | Siguiente paquete de la conversación |
| `Ctrl+←` | Paquete anterior de la conversación |
| `Ctrl+.` | Siguiente paquete marcado |
| `Ctrl+M` | Marcar/desmarcar un paquete |
| `Ctrl+Shift+M` | Marcar todos los paquetes mostrados |
| `Space` | Expandir/contraer el árbol de protocolos |
| `Tab` | Panel siguiente |

---

## 💡 Consejos y buenas prácticas

### Consejos de captura

1. **Usar filtros de captura**
   ```
   Reducir el tamaño del archivo filtrando durante la captura
   ```

2. **Búfer circular para capturas largas**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Capturar de forma remota**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Consejos de análisis

1. **Reglas de colores**
   ```
   View → Coloring Rules
   Personalizar los colores de los protocolos
   ```

2. **Visualización del tiempo**
   ```
   View → Time Display Format
   Probar "Seconds Since Beginning of Capture"
   ```

3. **Resolución de nombres**
   ```
   View → Name Resolution
   Activar/desactivar según sea necesario
   ```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** La captura de paquetes de red debe utilizarse únicamente con **fines autorizados**.
> 
> - ✅ Capturar en redes propias
> - ✅ Realizar pruebas de seguridad con permiso
> - ✅ Resolver problemas en sistemas autorizados
> - ❌ Nunca capturar en redes sin autorización
> - ❌ Capturar tráfico ajeno puede ser ilegal
> 
> **La monitorización de redes sin autorización es ilegal en la mayoría de las jurisdicciones.**

---

## 📚 Recursos

### Recursos oficiales
- [Sitio oficial de Wireshark](https://www.wireshark.org/)
- [Guía de usuario de Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Referencia de filtros de visualización](https://www.wireshark.org/docs/dfref/)
- [Wiki de Wireshark](https://wiki.wireshark.org/)

### Recursos de aprendizaje
- [Tutorial de Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Capturas de ejemplo](https://wiki.wireshark.org/SampleCaptures)

### Guías de referencia relacionadas
- [Nmap](../../Nmap/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)
- [Hydra](../../Hydra/translations/README.es.md)

---

<p align="center">
  <b>🦈 ¡Domina el análisis de redes!</b><br>
  <i>Observa todo lo que circula por la red</i>
</p>
