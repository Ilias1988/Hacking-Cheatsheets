# 📡 IoT Hacking Cheatsheet

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 Table of Contents

| Topic | Description | Guide |
|-------|-------------|-------|
| **Firmware Analysis** | Extraction, reverse engineering | [📄 View](./Firmware-Analysis.md) |
| **Hardware Hacking** | UART, JTAG, SPI, I2C | [📄 View](./Hardware-Hacking.md) |

---

## 🎯 IoT Attack Surface

```
┌─────────────────────────────────────────────────────────────┐
│                    IoT ATTACK SURFACE                        │
├─────────────────────────────────────────────────────────────┤
│  📡 WIRELESS      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 HARDWARE      →  UART, JTAG, SPI, debug ports           │
│  💾 FIRMWARE      →  Binwalk, extraction, RE                │
│  🌐 NETWORK       →  Web interface, APIs, protocols         │
│  📱 MOBILE APP    →  Companion apps, BLE communication      │
│  ☁️ CLOUD         →  APIs, authentication, data storage     │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 Quick Reference

### Firmware
```bash
# Extract firmware
binwalk -e firmware.bin

# Find file systems
binwalk firmware.bin | grep -i filesystem

# Mount squashfs
unsquashfs squashfs-root
```

### Hardware
```bash
# UART connection
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Essential Tools

| Category | Tools |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Hardware** | Bus Pirate, Logic Analyzer, Multimeter |
| **Wireless** | HackRF, RTL-SDR, Ubertooth |
| **Network** | Wireshark, Nmap, Shodan |

---

## 🔍 Shodan IoT Searches

```bash
# Webcams
webcam country:US

# Industrial control
port:502 modbus

# MQTT brokers
port:1883

# Default credentials
default password
```

---

## 📋 IoT Pentest Checklist

```markdown
□ Information gathering (Shodan, OSINT)
□ Firmware acquisition & analysis
□ Hardware inspection (debug ports)
□ Network traffic analysis
□ Web/API testing
□ Mobile app analysis
□ Wireless protocol analysis
□ Cloud backend testing
```

---

**Back to Main:** [🔴 Hacking Cheatsheets](../README.md)
