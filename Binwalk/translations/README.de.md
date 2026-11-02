# 🔧 Binwalk – Cheatsheet zur Firmware-Analyse

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Werkzeug zur Firmware-Analyse und -Extraktion
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Binwalk?](#-was-ist-binwalk)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Signatursuche](#-signatursuche)
- [Extraktion](#-extraktion)
- [Entropieanalyse](#-entropieanalyse)
- [Dateisystemanalyse](#-dateisystemanalyse)
- [CTF-Aufgaben](#-ctf-aufgaben)
- [Erweiterte Optionen](#-erweiterte-optionen)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Binwalk?

**Binwalk** ist ein schnelles, leicht bedienbares Werkzeug zur Analyse, zum Reverse Engineering (Untersuchen des Aufbaus) und zum Extrahieren von Daten aus Firmware-Abbildern. Es eignet sich insbesondere für:

- 🔧 **Firmware-Analyse** – Eingebettete Dateien und Code erkennen
- 🔓 **IoT-Hacking** – Router- und Kamera-Firmware extrahieren
- 🎯 **CTF-Aufgaben** – Versteckte Daten finden
- 🔬 **Forensik** – Binärdateien untersuchen
- 🔍 **Reverse Engineering** – Dateistrukturen verstehen

### Was Binwalk erkennen kann

| Kategorie | Beispiele |
| ---------- | ---------- |
| **Komprimierte Daten** | gzip, bzip2, lzma, xz, zlib |
| **Archive** | tar, zip, rar, 7z, cpio |
| **Dateisysteme** | squashfs, cramfs, jffs2, ext, ubifs |
| **Ausführbare Dateien** | ELF, PE, ARM, MIPS |
| **Bilder** | JPEG, PNG, GIF, BMP |
| **Kryptografie** | OpenSSL, Zertifikate |
| **Bootloader** | U-Boot, ARM-Header |

---

## 🚀 Installation

### Linux (Kali/Ubuntu)

```bash
# Binwalk installieren
sudo apt update
sudo apt install binwalk

# Alle Extraktionswerkzeuge installieren (empfohlen)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Alternativ eine umfassende Installation
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# sasquatch für SquashFS installieren
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Installation überprüfen

```bash
binwalk --help
binwalk -h
```

---

## 💻 Grundlegende Verwendung

### Grundlegende Syntax

```bash
# Datei untersuchen (Signaturen anzeigen)
binwalk firmware.bin

# Dateien extrahieren
binwalk -e firmware.bin

# Rekursive Extraktion
binwalk -eM firmware.bin
```

### Häufige Optionen

| Option | Beschreibung |
| -------- | ------------- |
| `-e, --extract` | Bekannte Dateitypen extrahieren |
| `-M, --matryoshka` | Extrahierte Dateien rekursiv untersuchen |
| `-r, --rm` | Ausgeschnittene Dateien nach der Extraktion löschen |
| `-q, --quiet` | Normale Ausgabe unterdrücken |
| `-v, --verbose` | Ausführliche Ausgabe |
| `-d, --depth` | Rekursionstiefe (Standard: 8) |
| `-C, --directory` | Ausgabeverzeichnis |
| `-f, --log` | Ergebnisse in eine Datei protokollieren |

---

## 🔍 Signatursuche

### Einfache Suche

```bash
# Nach Signaturen suchen
binwalk firmware.bin

# Ausgabebeispiel:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Bestimmte Signaturtypen

```bash
# Nur bestimmte Signaturen anzeigen
binwalk -A firmware.bin          # Opcodes/ausführbarer Code
binwalk -R firmware.bin          # Rohbytes (Signaturdatei)
binwalk -B firmware.bin          # Standardsignaturen (Standard)

# Alle Opcodes anzeigen
binwalk -A firmware.bin

# Nach rohen Zeichenketten/Hexwerten suchen
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Ein- und Ausschlussfilter

```bash
# Nur bestimmte Typen berücksichtigen
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Typen ausschließen
binwalk --exclude="jpeg" firmware.bin

# Nur bestimmte Signaturen anzeigen
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Eigene Signaturen

```bash
# Eigene Signaturdatei verwenden
binwalk -m custom_signatures.txt firmware.bin

# Format der Signaturdatei:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Extraktion

### Einfache Extraktion

```bash
# Alle erkannten Dateien extrahieren
binwalk -e firmware.bin
# Erstellt: _firmware.bin.extracted/

# In ein bestimmtes Verzeichnis extrahieren
binwalk -e -C /output/dir firmware.bin

# Rekursiv extrahieren (Matroschka-Prinzip)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Extraktionsoptionen

```bash
# Nach der Extraktion löschen (aufräumen)
binwalk -er firmware.bin

# Rekursionstiefe begrenzen
binwalk -eM --depth=3 firmware.bin

# Extraktion ohne normale Ausgabe
binwalk -eq firmware.bin

# Extraktion mit ausführlicher Ausgabe
binwalk -ev firmware.bin
```

### Manuelle Extraktion (dd)

```bash
# Wenn Binwalk nicht automatisch extrahieren kann
# Offset aus der Binwalk-Suche ermitteln
binwalk firmware.bin
# Beispiel: SquashFS bei Offset 1572892

# Mit dd ausschneiden
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Alternativ mit count (wenn die Größe bekannt ist)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Bestimmte Dateitypen ausschneiden

```bash
# Nur bestimmte Dateitypen extrahieren
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Mehrere Typen
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Format: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Entropieanalyse

### Was ist Entropie?

Entropie beschreibt, wie zufällig Daten verteilt sind:
- **Hohe Entropie (0.9-1.0)** = Verschlüsselte oder komprimierte Daten
- **Mittlere Entropie (0.5-0.8)** = Ausführbarer Code
- **Niedrige Entropie (0.0-0.4)** = Klartext oder sich wiederholende Daten

### Entropiediagramm erstellen

```bash
# Entropie berechnen
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Entropiediagramm speichern
binwalk -E --save firmware.bin
# Erstellt: firmware.bin.png

# Mit Legende
binwalk -EJ firmware.bin
```

### Entropieoptionen

```bash
# Blockgröße festlegen
binwalk -E --block=1024 firmware.bin

# Entropiewerte anzeigen (ohne Diagramm)
binwalk -E --nplot firmware.bin

# Steigende und fallende Flanken erkennen
binwalk -E --edge firmware.bin
```

### Entropie interpretieren

```
Hohe, flache Linie (~1.0)  → Verschlüsselte Daten
Spitzenmuster (~0.7)       → Komprimierte Daten  
Wellenförmige Linie (~0.5) → Code/Daten
Niedrige, flache Linie (~0.2) → Klartext
Plötzlicher Abfall auf 0  → Auffüllung mit Nullbytes
```

---

## 💾 Dateisystemanalyse

### Verbreitete Firmware-Dateisysteme

| Dateisystem | Beschreibung |
| ------------- | ------------- |
| **SquashFS** | Komprimiert und nur lesbar (besonders verbreitet) |
| **JFFS2** | Flash-Dateisystem mit Journal |
| **UBIFS** | Gegenüber JFFS2 besser für große Flash-Speicher geeignet |
| **CramFS** | Komprimiertes ROM-Dateisystem |
| **YAFFS** | Yet Another Flash File System |
| **ext2/3/4** | Verbreitete Linux-Dateisysteme |

### SquashFS extrahieren

```bash
# Mit Binwalk extrahieren
binwalk -e firmware.bin

# Manuell mit unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Einhängen (falls unterstützt)
sudo mount -t squashfs squashfs.bin /mnt
```

### JFFS2 extrahieren

```bash
# jefferson verwenden
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Alternativ mit Binwalk
binwalk -e firmware.bin
```

### UBIFS extrahieren

```bash
# ubireader verwenden
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Extrahierte Dateien untersuchen

```bash
# Nach der Extraktion die Dateien erkunden
cd _firmware.bin.extracted/

# Interessante Dateien finden
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Nach Passwörtern und geheimen Werten suchen
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Ausführbare Dateien finden
find . -type f -executable
file */bin/*
```

---

## 🎯 CTF-Aufgaben

### Häufige CTF-Szenarien

#### Versteckte Datei in einem Bild

```bash
# Bild auf versteckte Daten untersuchen
binwalk image.png
binwalk image.jpg

# Versteckte Dateien extrahieren
binwalk -e image.png

# Auch lesbare Zeichenketten prüfen
strings image.png | head -50
```

#### Verschachtelte Archive

```bash
# Rekursive Extraktion (Matroschka-Prinzip)
binwalk -eM challenge.bin

# Mehrere Kompressionsschichten
binwalk -eM --depth=10 nested.bin
```

#### Versteckten Text finden

```bash
# Mit strings kombinieren
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Verschlüsselte Bereiche

```bash
# Entropieanalyse zeigt Verschlüsselung auf
binwalk -E challenge.bin

# Bei hoher Entropie vermutlich verschlüsselt
# Häufige Passwörter prüfen oder den Schlüssel an anderer Stelle suchen
```

### CTF-Ablauf

```bash
# 1. Einfache Suche
binwalk challenge.bin

# 2. Entropie prüfen
binwalk -E challenge.bin

# 3. Alles extrahieren
binwalk -eM challenge.bin

# 4. Extrahierte Dateien durchsuchen
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Auf versteckte Daten prüfen
binwalk -A challenge.bin    # Opcodes
xxd challenge.bin | head    # Hexdump
```

### Steganografie erkennen

```bash
# Bilder auf versteckte Daten untersuchen
binwalk -B suspicious.jpg

# Auf angehängte Daten prüfen
binwalk suspicious.png

# Gefundene Daten extrahieren
binwalk -e suspicious.jpg

# Dateigröße mit der erwarteten Größe vergleichen
ls -la suspicious.jpg
```

---

<a name="-erweiterte-optionen"></a>

## ⚙️ Erweiterte Optionen

### Hexdump-Ausgabe

```bash
# Hexdump an Offsets anzeigen
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Hexdump ab einem bestimmten Offset
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Offset-Optionen

```bash
# Bei einem Offset beginnen
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Nur die angegebene Länge untersuchen
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Leistungsoptionen

```bash
# Mehrere Prozesse verwenden
binwalk --threads=4 firmware.bin

# Bestimmte Plugins deaktivieren
binwalk --disable-plugin=lzma firmware.bin
```

### Ausgabeformate

```bash
# CSV-Ausgabe
binwalk --csv firmware.bin > results.csv

# JSON-Ausgabe  
binwalk --json firmware.bin > results.json

# In eine Datei protokollieren
binwalk -f scan.log firmware.bin
```

### Zwei Dateien vergleichen

```bash
# Zwei Firmware-Versionen vergleichen
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Zeigt Unterschiede in Hexdarstellung
```

---

## 📊 Schnellübersicht

### Häufig verwendete Befehle

| Zweck | Befehl |
| --------- | --------- |
| Datei untersuchen | `binwalk firmware.bin` |
| Alles extrahieren | `binwalk -e firmware.bin` |
| Rekursiv extrahieren | `binwalk -eM firmware.bin` |
| Entropieanalyse | `binwalk -E firmware.bin` |
| Nach Opcodes suchen | `binwalk -A firmware.bin` |
| Zeichenkette suchen | `binwalk -R "pattern" file` |
| In ein Verzeichnis extrahieren | `binwalk -e -C outdir/ file` |
| Extrahieren und löschen | `binwalk -er firmware.bin` |

### Verbreitete Dateisignaturen

| Signatur | Beschreibung |
| ----------- | ------------- |
| `\x1f\x8b\x08` | gzip-komprimiert |
| `BZh` | bzip2-komprimiert |
| `\xfd7zXZ` | xz-komprimiert |
| `hsqs` | SquashFS (Little Endian) |
| `sqsh` | SquashFS (Big Endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | PNG-Bild |
| `\xff\xd8\xff` | JPEG-Bild |
| `PK\x03\x04` | ZIP-Archiv |

### Extraktionsablauf

```
1. binwalk firmware.bin       → Inhalt identifizieren
2. binwalk -E firmware.bin    → Auf Verschlüsselung prüfen
3. binwalk -e firmware.bin    → Extrahieren
4. cd _firmware.bin.extracted → Verzeichnis erkunden
5. find . -name "*" -type f   → Alle Dateien auflisten
6. grep -r "password" .       → Geheime Werte finden
```

### Manuelle Extraktion

```bash
# Offset mit Binwalk ermitteln
binwalk firmware.bin
# SquashFS bei 0x180000 (1572864 dezimal)

# Mit dd ausschneiden
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# SquashFS extrahieren
unsquashfs fs.squashfs
```

### Nützliche Dateipfade in Firmware

| Pfad | Inhalt |
| ------ | ---------- |
| `/etc/passwd` | Benutzerkonten |
| `/etc/shadow` | Passworthashes |
| `/etc/config/` | Gerätekonfiguration |
| `/etc/init.d/` | Startskripte |
| `/usr/bin/` | Benutzerprogramme |
| `/www/` | Weboberfläche |
| `/etc/ssl/` | SSL-Zertifikate |

---

## 📚 Ressourcen

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalk Wiki](https://github.com/ReFirmLabs/binwalk/wiki)
- [Firmware Analysis 101](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [OWASP IoT Firmware Analysis](https://owasp.org/www-project-iot-security/)

### Verwandte Cheatsheets
- [ExifTool](../../ExifTool/translations/README.de.md)
- [Volatility](../../Volatility/translations/README.de.md)
- [Autopsy](../../Autopsy/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Optionen:** Die Vorlage mischt vor allem Binwalk-2-Beispiele mit anderen beziehungsweise unvollständigen Optionen. Binwalk 3 wurde in Rust neu geschrieben; die gezeigte Python-Installation aus dem aktuellen Hauptbranch passt dazu nicht. Kurzoptionen, Ausgabeordner und Protokollformate unterscheiden sich: Der aktuelle Parser verwendet unter anderem ein JSON-Log und ein eigenes PNG-Argument; die Threads-Option bezeichnet Threads, nicht mehrere Prozesse. Die ursprünglichen Befehle und Verzeichnisnamen bleiben als historische Beispiele erhalten. [Binwalk: aktuelles Projekt](https://github.com/ReFirmLabs/binwalk), [Binwalk 3: CLI-Definition](https://github.com/ReFirmLabs/binwalk/blob/master/src/cliparser.rs).
- **Signaturen und Extraktion:** In Binwalk 2 erwartet -R eine Bytefolge; die alleinstehende Zeile mit firmware.bin ist keine vollständige Suche in einer Signaturdatei. Eigene Magic-Dateien gehören zur gesonderten Magic-Option. Ein Signaturtreffer allein garantiert weder eine vollständige Datei noch eine erfolgreiche Extraktion. Carving bedeutet, einen Datenbereich auszuschneiden; weitere Entpacker hängen von Dateityp und Installation ab. [Binwalk 2.3.4: Signaturoptionen](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/signature.py), [Extraktionsmodul](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/extractor.py).
- **Entropie:** Die Zahlen und Kurvenformen sind vereinfachte Faustregeln, kein Beweis für Verschlüsselung, Kompression oder Steganografie. In Binwalk 2 speichert -J das Diagramm; es schaltet keine Legende ein. Eine eigene --edge-Option ist dort nicht definiert. Die gezeigten Beispielaufrufe bleiben unverändert. [Binwalk 2.3.4: Entropiemodul und Optionen](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/entropy.py).
- **Hexvergleich und Ausgabe:** -W bezeichnet in Binwalk 2 einen Hexdump beziehungsweise Vergleich eines oder mehrerer Dateien; --diff ist dort kein zusätzlicher definierter Schalter. Die CSV-Option betrifft die Logausgabe, nicht automatisch umgeleitetes Standardausgabeformat. Die gezeigten --json- und --disable-plugin-Aufrufe sind nicht allgemein mit beiden Generationen kompatibel; verfügbare Optionen müssen zur konkreten Version passen. [Binwalk 2.3.4: Hexvergleich](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/hexdiff.py), [allgemeine Optionen](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/general.py), [Binwalk 3: CLI](https://github.com/ReFirmLabs/binwalk/blob/master/src/cliparser.rs).
- **Magic-Werte und Offsets:** UBI# ist die Kennung des UBI-Erase-Counter-Headers, keine eindeutige UBIFS-Knotenkennung. Auch andere kurze Magic-Werte sind nur Erkennungsmerkmale. Die unterschiedlichen SquashFS-Dateinamen und Offsets stammen aus getrennten schematischen Beispielen und bilden keinen durchgehenden Ablauf. Ein dd-Aufruf ohne Größenbegrenzung kopiert ab dem Offset bis zum Dateiende. [Linux: UBI-Medienformat](https://github.com/torvalds/linux/blob/master/drivers/mtd/ubi/ubi-media.h), [GNU dd](https://www.gnu.org/s/coreutils/manual/html_node/dd-invocation.html).

---

<p align="center">
  <b>🔧 Firmware-Analyse beherrschen!</b><br>
  <i>Binwalk – verborgene Inhalte von Firmware entpacken</i>
</p>
