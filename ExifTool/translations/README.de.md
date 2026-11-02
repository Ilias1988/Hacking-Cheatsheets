# 📷 ExifTool – Cheatsheet zur Metadatenextraktion

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Werkzeug zur Metadatenextraktion
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist ExifTool?](#-was-ist-exiftool)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Metadaten lesen](#-metadaten-lesen)
- [Metadaten schreiben](#-metadaten-schreiben)
- [Metadaten entfernen](#-metadaten-entfernen)
- [Dateitypen](#-dateitypen)
- [OSINT und Forensik](#-osint-und-forensik)
- [Stapelverarbeitung](#-stapelverarbeitung)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist ExifTool?

**ExifTool** ist ein vielseitiges Kommandozeilenwerkzeug zum Lesen, Schreiben und Bearbeiten von Metadaten in Dateien. Tags sind dabei benannte Metadatenfelder. Es eignet sich insbesondere für:

- 🔍 **OSINT** (Informationsgewinnung aus offenen Quellen) – Standort- und Geräteangaben aus Bildern auslesen
- 🔬 **Forensik** – Metadaten von Dokumenten analysieren
- 🔐 **Privatsphäre** – Sensible Metadaten entfernen
- 📸 **Fotografie** – Fotometadaten verwalten
- 📄 **Dokumente** – Metadaten von PDF- und Office-Dateien analysieren

### Warum Metadaten wichtig sind

| Anwendungsfall | Beispiel |
|----------|---------|
| **Geolokalisierung** | GPS-Koordinaten in Fotos |
| **Geräteinformationen** | Kameramodell, Seriennummer |
| **Zeitstempel** | Zeitpunkt der Erstellung oder Änderung |
| **Autor** | Ersteller des Dokuments |
| **Software** | Zum Erstellen verwendete Anwendung |
| **Bearbeitungshistorie** | Änderungszeitstempel |

---

## 🚀 Installation

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Alternativ
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Von der offiziellen Website herunterladen
https://exiftool.org/

# exiftool(-k).exe entpacken
# In exiftool.exe umbenennen
# Zu PATH hinzufügen oder direkt aufrufen
```

### Installation prüfen

```bash
exiftool -ver
# Ausgabe: 12.xx
```

---

## 💻 Grundlegende Verwendung

### Grundlegende Syntax

```bash
# Metadaten lesen
exiftool [OPTIONS] FILE

# Metadaten schreiben
exiftool -TAG=VALUE FILE

# Mehrere Dateien
exiftool *.jpg
exiftool -r directory/
```

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-r` | Rekursiv (Unterverzeichnisse verarbeiten) |
| `-v` | Ausführliche Ausgabe |
| `-n` | Ohne Anzeigeumwandlung (Rohwerte) |
| `-s` | Kurze Tag-Namen |
| `-G` | Gruppennamen für jeden Tag anzeigen |
| `-j` | JSON-Ausgabe |
| `-csv` | CSV-Ausgabe |
| `-overwrite_original` | Keine Sicherung erstellen |

---

## 📖 Metadaten lesen

### Alle Metadaten anzeigen

```bash
# Alle Metadaten
exiftool image.jpg

# Ausführliche Ausgabe
exiftool -v image.jpg

# Sehr ausführliche Ausgabe
exiftool -v3 image.jpg
```

### Bestimmte Tags

```bash
# Einzelner Tag
exiftool -GPSLatitude image.jpg

# Mehrere Tags
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Tags ausschließen
exiftool --MakerNotes image.jpg
```

### Nach Gruppen lesen

```bash
# Alle EXIF-Tags anzeigen
exiftool -EXIF:all image.jpg

# Alle IPTC-Tags anzeigen
exiftool -IPTC:all image.jpg

# Alle XMP-Tags anzeigen
exiftool -XMP:all image.jpg

# Alle GPS-Tags anzeigen
exiftool -GPS:all image.jpg

# Mit Gruppennamen anzeigen
exiftool -G image.jpg
```

### Häufige Metadatengruppen

| Gruppe | Beschreibung |
|-------|-------------|
| EXIF | Kamera- und Bilddaten |
| IPTC | Nachrichten- und Pressemetadaten |
| XMP | Erweiterbare Metadaten von Adobe |
| GPS | Standortdaten |
| MakerNotes | Kameraspezifische Daten |
| File | Grundlegende Dateiinformationen |
| Composite | Berechnete Werte |

### Ausgabeformate

```bash
# JSON-Ausgabe
exiftool -j image.jpg

# CSV-Ausgabe
exiftool -csv image.jpg

# HTML-Ausgabe
exiftool -htmlDump image.jpg > output.html

# Durch Tabulatoren getrennt
exiftool -t image.jpg

# Eigenes Ausgabeformat
exiftool -p '$filename: $make $model' image.jpg
```

---

<a name="-metadaten-schreiben"></a>

## ✏️ Metadaten schreiben

### Einzelnen Tag schreiben

```bash
# Autor setzen
exiftool -Author="John Doe" image.jpg

# Urheberrechtsangabe setzen
exiftool -Copyright="© 2024 Company" image.jpg

# Kommentar setzen
exiftool -Comment="My photo" image.jpg
```

### Mehrere Tags schreiben

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### GPS-Koordinaten schreiben

```bash
# GPS-Koordinaten setzen
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Mit Richtungsangaben
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# Aus dem Koordinatenformat einer Google-Maps-URL
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Datum und Uhrzeit schreiben

```bash
# Datum und Uhrzeit setzen
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Alle Datumsangaben setzen
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Datumsangaben verschieben
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Ein Jahr hinzufügen
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Sieben Tage abziehen
```

### Metadaten zwischen Dateien kopieren

```bash
# Alle Metadaten kopieren
exiftool -TagsFromFile source.jpg destination.jpg

# Bestimmte Tags kopieren
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# In mehrere Dateien kopieren
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a name="-metadaten-entfernen"></a>

## 🗑️ Metadaten entfernen

### Alle Metadaten entfernen

```bash
# Alle Metadaten entfernen (Datei bleibt verwendbar)
exiftool -all= image.jpg

# Ohne Sicherung entfernen
exiftool -overwrite_original -all= image.jpg

# Mehrere Dateien verarbeiten
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Bestimmte Metadaten entfernen

```bash
# Nur GPS-Daten entfernen
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Nur EXIF entfernen
exiftool -EXIF:all= image.jpg

# Kommentare entfernen
exiftool -Comment= image.jpg

# Autorenangaben entfernen
exiftool -Author= -Creator= -Artist= image.jpg
```

### Aus bestimmten Dateitypen entfernen

```bash
# Bilder
exiftool -all= *.jpg *.png *.gif

# PDFs
exiftool -all= document.pdf

# Office-Dateien
exiftool -all= *.docx *.xlsx *.pptx

# Videos
exiftool -all= video.mp4
```

### Zum Schutz der Privatsphäre bereinigen

```bash
# Alle identifizierenden Angaben entfernen
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 Dateitypen

### Unterstützte Dateitypen (400+)

| Kategorie | Formate |
|----------|---------|
| **Bilder** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **RAW-Fotos** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Video** | MP4, MOV, AVI, MKV, WebM |
| **Audio** | MP3, WAV, FLAC, M4A, OGG |
| **Dokumente** | PDF, DOCX, XLSX, PPTX |
| **Archive** | ZIP (eingeschränkt) |

### Dateitypspezifische Beispiele

```bash
# PDF-Metadaten
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Video-Metadaten
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Audio-Metadaten
exiftool -Artist -Album -Title -Duration audio.mp3

# Office-Dokumente
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT und Forensik

### GPS-Position auslesen

```bash
# GPS-Koordinaten anzeigen
exiftool -GPS:all image.jpg

# GPS-Angaben in lesbarer Form
exiftool -GPSPosition image.jpg

# Dezimalgrad
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Google-Maps-URL
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Gerät identifizieren

```bash
# Kamerainformationen
exiftool -Make -Model -SerialNumber image.jpg

# Verwendete Software
exiftool -Software -CreatorTool image.jpg

# Objektivinformationen (falls vorhanden)
exiftool -Lens -LensModel -FocalLength image.jpg

# Ausführliche Kamerainformationen
exiftool -MakerNotes:all image.jpg
```

### Zeitachsenanalyse

```bash
# Alle Datums- und Zeitangaben
exiftool -time:all image.jpg

# Zeitstempel des Dateisystems
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# EXIF-Zeitstempel
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Abweichende Datumsangaben finden
exiftool -a -G1 -s -time:all image.jpg
```

### Dokumentenforensik

```bash
# PDF-Analyse
exiftool -a -G1 document.pdf

# Bearbeitungshistorie einer Office-Datei
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Autorenangaben finden
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Echtheit von Bildern prüfen

```bash
# Auf Bearbeitungssoftware prüfen
exiftool -Software -HistorySoftwareAgent image.jpg

# Vorschaubild mit dem Bild vergleichen
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Auf Änderungen prüfen
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Nützliche OSINT-Abfragen

```bash
# Alle identifizierenden Angaben auslesen
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Auf Steganografie-Werkzeuge prüfen
exiftool -Software image.jpg | grep -i "steg\|hide"

# Eingebettetes Vorschaubild extrahieren
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Artefakte sozialer Medien finden
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a name="-stapelverarbeitung"></a>

## ⚙️ Stapelverarbeitung

### Mehrere Dateien verarbeiten

```bash
# Alle JPG-Dateien im aktuellen Verzeichnis
exiftool *.jpg

# Rekursiv
exiftool -r directory/

# Bestimmte Dateiendungen
exiftool -ext jpg -ext png directory/
```

### Metadaten stapelweise entfernen

```bash
# Aus allen Bildern entfernen
exiftool -all= -overwrite_original -r directory/

# Nur bestimmte Dateitypen
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Dateien stapelweise umbenennen

```bash
# Nach Datum umbenennen
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Nach Kameramodell umbenennen
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Mit Zähler umbenennen
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Metadaten stapelweise kopieren

```bash
# GPS-Daten aus einer Datei in alle Dateien kopieren
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Als CSV exportieren

```bash
# Metadaten als CSV exportieren
exiftool -csv *.jpg > metadata.csv

# Bestimmte Felder
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Bedingte Verarbeitung

```bash
# Nur Dateien mit GPS-Angaben
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Nur Dateien einer bestimmten Kamera
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Nach einem Datum geänderte Dateien
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Schnellübersicht

### Häufig verwendete Befehle

| Zweck | Befehl |
|---------|---------|
| Alle Metadaten anzeigen | `exiftool image.jpg` |
| GPS-Angaben anzeigen | `exiftool -GPS:all image.jpg` |
| Alle Metadaten entfernen | `exiftool -all= image.jpg` |
| GPS-Daten entfernen | `exiftool -GPS:all= image.jpg` |
| JSON-Ausgabe | `exiftool -j image.jpg` |
| Rekursiv | `exiftool -r directory/` |
| Kamerainformationen | `exiftool -Make -Model image.jpg` |
| Zeitstempel | `exiftool -time:all image.jpg` |

### Häufige Tags

| Tag | Beschreibung |
|-----|-------------|
| `-Make` | Kamerahersteller |
| `-Model` | Kameramodell |
| `-DateTimeOriginal` | Aufnahmezeitpunkt |
| `-GPSLatitude` | Geografische Breite |
| `-GPSLongitude` | Geografische Länge |
| `-Author` | Dateiautor |
| `-Copyright` | Urheberrechtsangabe |
| `-Software` | Bearbeitungssoftware |
| `-ImageWidth` | Bildbreite |
| `-ImageHeight` | Bildhöhe |

### Checkliste zum Schutz der Privatsphäre

```bash
# 1. Vorhandene Metadaten prüfen
exiftool -a -G1 image.jpg

# 2. Auf GPS-Angaben prüfen
exiftool -GPS:all image.jpg

# 3. Auf identifizierende Angaben prüfen
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Alles entfernen
exiftool -all= -overwrite_original image.jpg

# 5. Entfernung überprüfen
exiftool image.jpg
```

### Forensische Checkliste

```bash
# 1. Original erhalten (zuerst eine Kopie erstellen!)
cp image.jpg image_original.jpg

# 2. Vollständigen Metadatenbericht erstellen
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. GPS-Daten auslesen
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Zeitachse
exiftool -time:all -G1 image.jpg

# 5. Geräteinformationen
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Auf Manipulation prüfen
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Ressourcen

- [Offizielle ExifTool-Website](https://exiftool.org/)
- [ExifTool-Dokumentation](https://exiftool.org/TagNames/)
- [ExifTool-Forum](https://exiftool.org/forum/)
- [Unterstützte Dateitypen](https://exiftool.org/#supported)

### Verwandte Cheatsheets
- [Autopsy](../../Autopsy/translations/README.de.md)
- [Volatility](../../Volatility/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Formatabhängige Funktionen:** Die Formatliste umfasst auch nur lesbare Formate. Insbesondere die gezeigten Office-Dateien sind in der offiziellen Unterstützungstabelle als lesbar ausgewiesen; der Löschaufruf ist damit keine Zusage zur Bearbeitung. Bei TagsFromFile werden nur entsprechend schreibbare Tags übertragen. htmlDump erzeugt einen strukturellen Hexdump als HTML. [ExifTool: Formatunterstützung](https://exiftool.org/#supported), [ExifTool: Befehlsdokumentation](https://exiftool.org/exiftool_pod.html).
- **Löschen und Datenschutz:** all= ist keine formatübergreifende Anonymisierungsgarantie. RAW-Dateien benötigen teilweise Metadaten zur korrekten Darstellung; auch Farbinformationen können relevant sein. Standardmäßig angelegte Sicherungen enthalten die alten Daten weiterhin. Bei PDFs arbeitet ExifTool mit reversiblen inkrementellen Änderungen: Frühere Informationen bleiben im Dateikörper erhalten. Die ursprünglichen Löschbeispiele bleiben unverändert. [ExifTool: sicheres Entfernen](https://exiftool.org/faq.html#Q32), [ExifTool: PDF-Tags und reversible Änderungen](https://exiftool.org/TagNames/PDF.html).
- **GPS und Zeitangaben:** EXIF speichert die Himmelsrichtung in eigenen Ref-Tags; ein negatives Longitude-Beispiel allein ersetzt diese Richtungsangabe nicht allgemein. Der Maps-Ausdruck verwendet ohne angepasste Ausgabeformatierung nicht automatisch Dezimalgrad. AllDates umfasst lediglich DateTimeOriginal, CreateDate und ModifyDate, nicht sämtliche Zeitstempel. Metadaten lassen sich bearbeiten; Zeitstempel oder Softwareangaben allein beweisen daher weder Herkunft noch Bildauthentizität. [ExifTool: GPS-Formate](https://exiftool.org/faq.html#Q14), [ExifTool: AllDates](https://exiftool.org/TagNames/Shortcuts.html).
- **Umbenennen und Shell-Kontext:** Der Zähleraufruf verwendet eine Tag-Umleitung, obwohl rechts kein vorhandener Quell-Tag angegeben ist; er ist kein allgemein gültiges Beispiel für einen festen Dateinamen. Kopierausdrücke und direkte Zuweisungen haben unterschiedliche Syntax. Prozentzeichen werden innerhalb von Datumsformaten beziehungsweise Windows-Batchdateien zusätzlich maskiert; die einfachen Anführungszeichen der Shell-Beispiele sind nicht in jeder Windows-Shell gleich auszuwerten. Die Originalbefehle bleiben erhalten. [ExifTool: Dateinamen und Formatcodes](https://exiftool.org/filename.html), [ExifTool: Aufruf und Zuweisungen](https://exiftool.org/exiftool_pod.html).
- **Windows-Paket und Versionsausgabe:** Aktuelle Windows-Pakete benötigen neben der EXE auch den zugehörigen Ordner exiftool_files. Die Ausgabe 12.xx ist ein historischer Platzhalter, keine geforderte aktuelle Version. [ExifTool: offizielle Installationsanleitung](https://exiftool.org/install.html).

---

<p align="center">
  <b>📷 Metadatenanalyse beherrschen!</b><br>
  <i>ExifTool – jede Datei erzählt eine Geschichte</i>
</p>
