# 📡 Guía rápida de pruebas de seguridad de IoT

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Terminología y precisión:** IoT significa Internet de las cosas; RE es ingeniería inversa. `unsquashfs` extrae una imagen SquashFS: no la monta. El nombre `squashfs-root` suele designar el directorio de salida, por lo que el argumento del ejemplo debe contrastarse con el archivo de imagen real. Se conserva el comando del original.


```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 Índice

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Análisis de firmware** | Extracción e ingeniería inversa | [📄 Ver](./Firmware-Analysis.es.md) |
| **Pruebas de seguridad de hardware** | UART, JTAG, SPI, I2C | [📄 Ver](./Hardware-Hacking.es.md) |

---

## 🎯 Superficie de ataque de IoT

```
┌───────────────────────────────────────────────────────────────┐
│  SUPERFICIE DE ATAQUE DE IoT                                  │
│                                                               │
│  INALÁMBRICO  → WiFi, BLE, Zigbee, Z-Wave, LoRa               │
│  HARDWARE     → UART, JTAG, SPI, puertos de depuración        │
│  FIRMWARE     → Binwalk, extracción e ingeniería inversa      │
│  RED          → Interfaz web, API y protocolos                │
│  APP MÓVIL    → Apps complementarias y comunicación BLE       │
│  NUBE         → API, autenticación y almacenamiento de datos  │
└───────────────────────────────────────────────────────────────┘
```

---

## 🔥 Referencia rápida

### Firmware
```bash
# Extraer firmware
binwalk -e firmware.bin

# Buscar sistemas de archivos
binwalk firmware.bin | grep -i filesystem

# Montar squashfs
unsquashfs squashfs-root
```

### Hardware
```bash
# Conexión UART
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Herramientas esenciales

| Categoría | Herramientas |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Hardware** | Bus Pirate, Analizador lógico, Multímetro |
| **Inalámbrico** | HackRF, RTL-SDR, Ubertooth |
| **Red** | Wireshark, Nmap, Shodan |

---

## 🔍 Búsquedas de IoT en Shodan

```bash
# Webcams
webcam country:US

# Control industrial
port:502 modbus

# Brokers de MQTT
port:1883

# Credenciales predeterminadas
default password
```

---

## 📋 Lista de comprobación para pruebas de IoT

```markdown
□ Recopilación de información (Shodan, OSINT)
□ Adquisición y análisis de firmware
□ Inspección del hardware (puertos de depuración)
□ Análisis del tráfico de red
□ Pruebas de aplicaciones web y API
□ Análisis de aplicaciones móviles
□ Análisis de protocolos inalámbricos
□ Pruebas del backend en la nube
```

---

**Volver al índice principal:** [🔴 Hacking Cheatsheets](../../README.md)
