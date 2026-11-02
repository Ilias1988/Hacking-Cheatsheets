# 🔧 Spickzettel für Hardware-Hacking

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

## 🔌 UART (serielle Konsole)

### UART-Pins bestimmen
```
TX (Transmit) – Daten vom Gerät senden
RX (Receive) – Daten am Gerät empfangen
GND – Masse
VCC – Versorgungsspannung (3.3V/5V)
```

### Werkzeuge
- **Multimeter** - GND und VCC bestimmen
- **Logikanalysator** - TX/RX und Baudrate ermitteln
- **USB-UART** - FT232, CP2102, CH340

### Verbindung
```bash
# Linux screen
screen /dev/ttyUSB0 115200

# Linux minicom
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

### Häufige Baudraten
```
9600, 19200, 38400, 57600, 115200
```

---

## 🔧 JTAG (Debug-Schnittstelle)

### Pins
```
TDI – Test Data In (Testdaten-Eingang)
TDO – Test Data Out (Testdaten-Ausgang)
TCK – Test Clock (Testtakt)
TMS – Test Mode Select (Auswahl des Testmodus)
TRST – Test Reset (optionales Rücksetzsignal)
```

### Werkzeuge
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# JTAG-Pinbelegung automatisch ermitteln
```

---

## 📟 SPI (Flash-Speicher)

### Pins
```
MOSI – Master Out Slave In (Daten zum Peripheriegerät)
MISO – Master In Slave Out (Daten vom Peripheriegerät)
CLK  – Takt
CS   – Chipauswahl
```

### Flash-Speicher auslesen
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

## 📡 I2C (EEPROM)

### Pins
```
SDA – Serielle Daten
SCL – Serieller Takt
```

### EEPROM auslesen
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

## 🛠️ Hardware-Werkzeuge

| Werkzeug | Verwendung |
|------|-----|
| **Bus Pirate** | Arbeiten mit mehreren Hardwareprotokollen |
| **FT232** | USB-zu-UART-Adapter |
| **Logikanalysator** | Signalanalyse |
| **Multimeter** | Spannungs- und Durchgangsmessung |
| **JTAGulator** | JTAG-Pins erkennen |
| **CH341A** | Programmiergerät für SPI-Flash |

---

## 📋 Checkliste für Hardware-Hacking

```markdown
□ Sichtprüfung (Chips, Testpunkte)
□ Anschlüsse für Debug-Schnittstellen bestimmen
□ UART mit einem Multimeter lokalisieren
□ Signale mit einem Logikanalysator aufzeichnen
□ Verbindung herstellen und Shell-Zugriff prüfen
□ Flash-Speicher auslesen
□ Extrahierte Firmware analysieren
```

---

**Zurück zur IoT-Übersicht:** [📡 IoT Hacking](./README.de.md)


---

## Hinweise zur deutschen Ausgabe

> **Geräteabhängige Angaben:** Die VCC-Zeile nennt Beispielspannungen; sie ist keine allgemeine Freigabe, ein Gerät mit 3.3V oder 5V zu versorgen. Versorgungsspannung, Logikpegel, Pinbelegung und geeigneter Adapter richten sich nach der konkreten Hardware. Eine erkannte UART-Schnittstelle garantiert weder eine interaktive Shell noch Administratorrechte. Die OpenOCD-Dateien gelten nur für den im Beispiel bezeichneten Adapter und Zielcontroller. SPI- und I2C-Speicher müssen anhand des Bauteiltyps zugeordnet werden.

> **I2C-Kontext:** Busnummer `1` und Adresse `0x50` sind Beispielwerte. `i2cdetect` untersucht den Bus aktiv; es ist keine rein passive Anzeige und kann Geräte beeinflussen. Der gezeigte `i2cdump`-Aufruf ist keine universelle Methode für sämtliche EEPROM-Typen. Die Auswahl des passenden Adapters und Protokolls bleibt erforderlich. [Linux-I2C-Schnittstelle](https://www.kernel.org/doc/html/latest/i2c/dev-interface.html), [i2c-tools beim Hardwarehersteller NXP](https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/All-Boards-I2C-tools/ta-p/1117101).
