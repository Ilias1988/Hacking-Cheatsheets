# 📡 Spickzettel für IoT-Hacking

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📑 Inhaltsverzeichnis

| Thema | Beschreibung | Anleitung |
|-------|-------------|-------|
| **Firmware-Analyse** | Extraktion und Reverse Engineering | [📄 Ansehen](./Firmware-Analysis.de.md) |
| **Hardware-Hacking** | UART, JTAG, SPI, I2C | [📄 Ansehen](./Hardware-Hacking.de.md) |

---

## 🎯 IoT-Angriffsfläche

```
┌─────────────────────────────────────────────────────────┐
│ IoT-ANGRIFFSFLÄCHE                                      │
│ FUNK        → WiFi, BLE, Zigbee, Z-Wave, LoRa           │
│ HARDWARE    → UART, JTAG, SPI, Debug-Schnittstellen     │
│ FIRMWARE    → Binwalk, Extraktion, Reverse Engineering  │
│ NETZWERK    → Weboberfläche, APIs, Protokolle           │
│ MOBILE APP  → Begleit-Apps, BLE-Kommunikation           │
│ CLOUD       → APIs, Authentifizierung, Datenspeicherung │
└─────────────────────────────────────────────────────────┘
```

---

## 🔥 Kurzreferenz

### Firmware
```bash
# Firmware extrahieren
binwalk -e firmware.bin

# Dateisysteme ermitteln
binwalk firmware.bin | grep -i filesystem

# SquashFS einbinden (Bezeichnung der Vorlage)
unsquashfs squashfs-root
```

### Hardware
```bash
# UART-Verbindung
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

## 🛠️ Wichtige Werkzeuge

| Kategorie | Werkzeuge |
|----------|-------|
| **Firmware** | Binwalk, firmware-mod-kit, EMBA |
| **Hardware** | Bus Pirate, Logikanalysator, Multimeter |
| **Funk** | HackRF, RTL-SDR, Ubertooth |
| **Netzwerk** | Wireshark, Nmap, Shodan |

---

## 🔍 Shodan-Suchabfragen für IoT

```bash
# Webcams
webcam country:US

# Industrielle Steuerungen
port:502 modbus

# MQTT-Broker
port:1883

# Standardzugangsdaten
default password
```

---

## 📋 Checkliste für IoT-Penetrationstests

```markdown
□ Informationssammlung (Shodan, OSINT)
□ Firmware beschaffen und analysieren
□ Hardware untersuchen (Debug-Schnittstellen)
□ Netzwerkverkehr analysieren
□ Weboberfläche und APIs prüfen
□ Mobile App analysieren
□ Funkprotokolle analysieren
□ Cloud-Backend prüfen
```

---

**Zurück zur Hauptübersicht:** [🔴 Hacking Cheatsheets](../../README.md)


---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Kontext:** *Firmware* ist die gerätenahe Software. *RE* bedeutet Reverse Engineering, also die Untersuchung des Programmaufbaus. *OTA* bezeichnet eine Aktualisierung über eine Netzwerkverbindung; *MITM* eine Position zwischen Kommunikationspartnern. Die dargestellten Bereiche sind mögliche Angriffsflächen, keine Aussage darüber, welche Schnittstellen ein konkretes Gerät besitzt. Shodan-Zeilen sind Suchabfragen für Shodan, keine ausführbaren Bash-Befehle; Treffer belegen nicht automatisch eine Schwachstelle.

> **Korrektur zur SquashFS-Kurzreferenz:** `unsquashfs` extrahiert ein Dateisystemabbild, statt es einzubinden. `squashfs-root` ist üblicherweise der Name des Ausgabeordners; ein bereits extrahierter Ordner ist kein passendes Eingabeabbild. Der Originalbefehl bleibt unverändert. [Offizielle Unsquashfs-Dokumentation](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).
