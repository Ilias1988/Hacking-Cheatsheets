# 📡 Scheda di riferimento per l'hacking IoT

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 Indice

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Analisi del firmware** | Estrazione e reverse engineering (analisi del funzionamento interno) | [📄 Visualizza](./Firmware-Analysis.it.md) |
| **Hacking hardware** | UART, JTAG, SPI, I2C | [📄 Visualizza](./Hardware-Hacking.it.md) |

---

## 🎯 Superficie di attacco IoT

```
┌─────────────────────────────────────────────────────────────┐
│                 SUPERFICIE DI ATTACCO IoT                    │
├─────────────────────────────────────────────────────────────┤
│  📡 WIRELESS      →  WiFi, BLE, Zigbee, Z-Wave, LoRa          │
│  🔧 HARDWARE      →  UART, JTAG, SPI, porte di debug          │
│  💾 FIRMWARE      →  Binwalk, estrazione, reverse engineering│
│  🌐 RETE          →  Interfaccia web, API, protocolli        │
│  📱 APP MOBILE    →  App di supporto, comunicazione BLE      │
│  ☁️ CLOUD         →  API, autenticazione, archiviazione dati │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔥 Riferimento rapido

### Firmware
```bash
# Estrai il firmware
binwalk -e firmware.bin

# Individua i file system
binwalk firmware.bin | grep -i filesystem

# Monta squashfs (etichetta dell'originale: vedi nota)
unsquashfs squashfs-root
```

> **Nota sull’originale:** `unsquashfs` estrae un’immagine SquashFS, non la monta. `squashfs-root` è normalmente la directory di estrazione predefinita: il comando conservato richiede invece che quel nome identifichi un’immagine valida. Vedi la [documentazione di Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

### Hardware
```bash
# Connessione UART
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Strumenti essenziali

| Categoria | Strumenti |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Hardware** | Bus Pirate, analizzatore logico, multimetro |
| **Wireless** | HackRF, RTL-SDR, Ubertooth |
| **Rete** | Wireshark, Nmap, Shodan |

---

## 🔍 Ricerche IoT su Shodan

```bash
# Webcam
webcam country:US

# Controllo industriale
port:502 modbus

# Broker MQTT
port:1883

# Credenziali predefinite
default password
```

> **Nota sul formato:** il blocco precedente contiene query per Shodan, non comandi Bash. I risultati dipendono dai dati indicizzati e non dimostrano da soli la presenza di una vulnerabilità.

---

## 📋 Checklist per il pentesting IoT

```markdown
□ Raccolta di informazioni (Shodan, OSINT)
□ Acquisizione e analisi del firmware
□ Ispezione hardware (porte di debug)
□ Analisi del traffico di rete
□ Test delle interfacce web e delle API
□ Analisi delle app mobili
□ Analisi dei protocolli wireless
□ Test del backend cloud
```

---

**Torna all'indice principale:** [🔴 Hacking Cheatsheets](../../README.it.md)
