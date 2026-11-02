# 🔍 Autopsy – Cheatsheet zur digitalen Forensik

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Plattform für digitale Forensik
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Autopsy?](#-was-ist-autopsy)
- [Installation](#-installation)
- [Einen Fall anlegen](#-einen-fall-anlegen)
- [Datenquellen hinzufügen](#-datenquellen-hinzufügen)
- [Ingest-Module](#-ingest-module)
- [Analysefunktionen](#-analysefunktionen)
- [Dateianalyse](#-dateianalyse)
- [Zeitachsenanalyse](#-zeitachsenanalyse)
- [Stichwortsuche](#-stichwortsuche)
- [Berichte erstellen](#-berichte-erstellen)
- [Kommandozeilenwerkzeuge (Sleuth Kit)](#-kommandozeilenwerkzeuge-sleuth-kit)

---

## 🎯 Was ist Autopsy?

**Autopsy** ist eine kostenlose, quelloffene Plattform für digitale Forensik auf Grundlage von The Sleuth Kit (TSK). Sie bietet:

- 🖥️ **Grafische Oberfläche** – Bedienungsfreundliche forensische Analyse
- 💾 **Datenträgeranalyse** – Festplatten, USB-Datenträger und Abbilder untersuchen
- 📁 **Dateiwiederherstellung** – Gelöschte Dateien wiederherstellen
- 📊 **Zeitachsenanalyse** – Benutzeraktivitäten rekonstruieren
- 🔍 **Stichwortsuche** – Datenbestände nach Begriffen durchsuchen
- 📧 **E-Mail-Analyse** – E-Mail-Datenbanken auswerten
- 🌐 **Webartefakte** – Browserverlauf und Lesezeichen untersuchen
- 📱 **Mobilgeräteforensik** – Android-/iOS-Daten analysieren

### Unterstützte Beweismitteltypen

| Typ | Formate |
| ------ | --------- |
| **Datenträgerabbilder** | E01, AFF, Raw (dd), VHD, VMDK |
| **Lokaler Datenträger** | Physische/logische Laufwerke |
| **VM-Dateien** | VMDK, VHD, VHDX |
| **Logische Dateien** | Ordner, Dateien |
| **Nicht zugewiesener Speicherbereich** | Per File Carving rekonstruierte Daten |

---

## 🚀 Installation

### Windows

```bash
# Von der offiziellen Website herunterladen
https://www.autopsy.com/download/

# Installationsprogramm starten
autopsy-x.x.x-64bit.msi

# Standardspeicherort
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

```bash
# Über apt installieren
sudo apt update
sudo apt install autopsy

# Starten
autopsy
```

### Anforderungen

| Komponente | Minimum | Empfehlung |
| ----------- | --------- | ------------- |
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Mehrkernprozessor | 4+ Kerne |
| **Speicher** | SSD | NVMe SSD |
| **Betriebssystem** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Einen Fall anlegen

### Assistent für neue Fälle

1. **File → New Case** auswählen
2. **Case Name** eingeben (z. B. "Investigation_2024")
3. **Base Directory** angeben (Speicherort für die Falldateien)
4. **Case Type** auswählen:
   - Single-user (lokale Bearbeitung)
   - Multi-user (gemeinsame Bearbeitung)
5. Optional **Case Number** eingeben
6. Angaben unter **Examiner** eintragen
7. Auf **Finish** klicken

### Fallstruktur

```
CaseName/
├── autopsy.db           # Falldatenbank (SQLite)
├── Reports/             # Erzeugte Berichte
├── Export/              # Extrahierte Dateien
├── ModuleOutput/        # Modulergebnisse
└── [HostName]/          # Daten je Host
    ├── [DataSource]/    # Beweismitteldateien
    └── Reports/
```

### Falleigenschaften

| Feld | Beschreibung |
| ------- | ------------- |
| Case Name | Bezeichnung der Untersuchung |
| Case Number | Akten- oder Referenznummer |
| Examiner | Name der untersuchenden Person |
| Base Directory | Speicherort des Falls |
| Case Type | Single/Multi-user |

---

## 📀 Datenquellen hinzufügen

### Arten von Datenquellen

| Typ | Beschreibung |
| ------ | ------------- |
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | Analyse eines physischen Datenträgers |
| **Logical Files** | Ordner/Dateien importieren |
| **Unallocated Space** | Rohabbild nicht zugewiesenen Speicherplatzes |

### Datenträgerabbild hinzufügen

1. **Add Data Source** in der Werkzeugleiste auswählen
2. **Disk Image or VM File** auswählen
3. Die Abbilddatei auswählen
4. **Time Zone** auswählen
5. **Ingest Modules** konfigurieren (nächster Abschnitt)
6. Auf **Finish** klicken

### Lokalen Datenträger hinzufügen

```
⚠️ WARNUNG: Schreibblocker oder schreibgeschützten Modus verwenden!
```

1. **Add Data Source** auswählen
2. **Local Disk** auswählen
3. Datenträger aus der Liste auswählen
4. Ingest-Module konfigurieren
5. **Finish** auswählen

### Unterstützte Abbildformate

| Format | Dateiendung | Beschreibung |
| -------- | ----------- | ------------- |
| Raw | .raw, .dd, .img | Bitgetreue Kopie |
| EnCase | .E01, .Ex01 | Expert-Witness-Format |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | VMware-Datenträger |
| VHD/VHDX | .vhd, .vhdx | Hyper-V-Datenträger |

---

<a name="-ingest-module"></a>

## ⚙️ Ingest-Module

### Was sind Ingest-Module?

Ingest-Module sind automatisierte Analysemodule, die Datenquellen verarbeiten und daraus Artefakte extrahieren.

### Zentrale Module

| Modul | Beschreibung |
| -------- | ------------- |
| **Recent Activity** | Browserverlauf, Downloads, Cookies |
| **Hash Lookup** | MD5-/SHA1-Abgleich mit bekannten Datenbanken |
| **File Type Identification** | Dateitypen anhand ihrer Signatur erkennen |
| **Keyword Search** | Inhalte indexieren und durchsuchen |
| **Email Parser** | PST, MBOX und EML auswerten |
| **Extension Mismatch** | Unpassende Dateiendungen erkennen |
| **Embedded File Extractor** | Dateien aus Archiven/Dokumenten extrahieren |
| **EXIF Parser** | Bildmetadaten extrahieren |
| **Encryption Detection** | Verschlüsselte Dateien finden |
| **Interesting Files** | Auffällige Dateien finden |
| **PhotoRec Carver** | Dateien anhand ihrer Struktur rekonstruieren (File Carving) |
| **Virtual Machine Extractor** | VM-Dateien extrahieren |

### Hash-Datenbanken

```
# Bekannte Hash-Datenbanken hinzufügen
Tools → Options → Hash Database

# Arten:
- Known (NSRL) - Bekannte unauffällige Dateien ausblenden
- Known Bad - Auf bekannte Malware-Hashes hinweisen
```

### NSRL (National Software Reference Library)

```bash
# NSRL herunterladen
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# In Autopsy importieren
Tools → Options → Hash Database → Import Database
```

### Module konfigurieren

```
# Module für den jeweiligen Fall aktivieren/deaktivieren
Rechtsklick auf die Datenquelle → Run Ingest Modules

# Moduleinstellungen konfigurieren
Auf das Zahnradsymbol neben dem Modulnamen klicken
```

---

## 🔬 Analysefunktionen

### Baumansicht (linker Bereich)

```
Data Sources
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Custom Tags]
```

### Wichtige Analysebereiche

| Kategorie | Worauf achten? |
| ---------- | ----------------- |
| **Deleted Files** | Wiederhergestellte Dateien, Löschungen durch Nutzer |
| **Web History** | Besuchte Websites, Suchanfragen |
| **Downloads** | Heruntergeladene Dateien |
| **Recent Documents** | Zuletzt geöffnete Dateien |
| **USB Devices** | Angeschlossene Geräte |
| **Email** | Kommunikation |
| **Images** | Fotos, Bildschirmaufnahmen |
| **Archives** | ZIP-/RAR-Inhalte |

---

## 📄 Dateianalyse

### Dateien anzeigen

```
# Content Viewer (unterer Bereich)
- Hex        : Rohe Hexadezimalansicht
- Text       : Textinhalt
- Application: Aufbereitete Darstellung
- Strings    : Extrahierte Zeichenketten
- Metadata   : Dateimetadaten
- Results    : Analyseergebnisse
- Indexed Text: Durchsuchbarer Text
```

### Dateieigenschaften

| Eigenschaft | Beschreibung |
| ---------- | ------------- |
| Name | Dateiname |
| Location | Vollständiger Pfad |
| Size | Dateigröße |
| Created | Erstellungszeit |
| Modified | Letzte Änderung |
| Accessed | Letzter Zugriff |
| MD5/SHA1/SHA256 | Hashwerte |
| Known | NSRL-Status |
| MIME Type | Erkannter Typ |

### Dateien extrahieren

```
# Einzelne Datei extrahieren
Rechtsklick → Extract File(s)

# Mehrere Dateien extrahieren
Dateien auswählen → Rechtsklick → Extract

# An einen bestimmten Speicherort extrahieren
Actions → Extract to...
```

### Gelöschte Dateien wiederherstellen

```
Views → Deleted Files

# Zeigt:
- Dateien in $Recycle.Bin
- In der MFT als gelöscht markierte Dateien
- Per File Carving rekonstruierte Dateien (PhotoRec)
```

---

## 📊 Zeitachsenanalyse

### Zeitachsenansicht

```
# Zeitachse öffnen
Tools → Timeline

# Alternativ
Results → Timeline
```

### Funktionen der Zeitachse

| Funktion | Beschreibung |
| --------- | ------------- |
| **Counts View** | Ereignishäufigkeit im Zeitverlauf |
| **Details View** | Einzelne Ereignisse |
| **List View** | Tabellarische Ereignisliste |
| **Filters** | Nach Typ, Datum usw. filtern |

### Ereignistypen

| Typ | Farbe | Beschreibung |
| ------ | ------- | ------------- |
| File Modified | Blau | Dateiinhalt geändert |
| File Accessed | Grün | Datei geöffnet |
| File Created | Gelb | Datei erstellt |
| File Changed | Rot | Metadaten geändert |
| Web Activity | Violett | Browserereignisse |

### Zeitachsenfilter

```
# Nach Zeitraum filtern
Start Date: 2024-01-01
End Date: 2024-12-31

# Nach Typ filtern
☑ File System
☑ Web Activity
☐ Registry (if parsed)

# Nach Datenquelle filtern
☑ Disk Image 1
☐ Disk Image 2
```

### Zeitachse exportieren

```
# Zeitachse exportieren
File → Export → Timeline (CSV/TSV)

# Mit externen Werkzeugen verwenden
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Stichwortsuche

### Stichwortlisten anlegen

```
# Tools → Options → Keyword Search

# Neue Liste hinzufügen
1. Auf "New List" klicken
2. Die Liste benennen
3. Suchbegriffe hinzufügen:
   - Literal (exakte Übereinstimmung)
   - Regex (regulärer Ausdruck)
   - Substring (Teilzeichenkette)
```

### Häufige Suchbegriffe

```
# Finanzen
credit card
bank account
swift
iban
bitcoin
wallet

# Zugangsdaten
password
login
username
secret
key

# Vertrauliches
confidential
secret
classified
internal only

# Netzwerk
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Regex-Beispiele

| Zweck | Regex |
| --------- | ------- |
| E-Mail | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP-Adresse | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Telefon (USA) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Kreditkarte | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Suche ausführen

```
# Spontane Suche
Keyword Search (oben rechts) → Suchbegriff eingeben → Search

# Stichwortlisten verwenden
Rechtsklick auf die Datenquelle → Run Ingest Modules → Keyword Search
```

---

## 📝 Berichte erstellen

### Bericht erzeugen

```
# Bericht erzeugen
Tools → Generate Report

# Alternativ
Rechtsklick auf den Fall → Generate Report
```

### Berichtsarten

| Typ | Beschreibung |
| ------ | ------------- |
| **HTML Report** | Webansicht einschließlich Bildern |
| **Excel Report** | Tabellenformat |
| **Text Report** | Einfacher Text |
| **TSK Body File** | Body-Datei für die Zeitachse |
| **Files - FS** | Dateisystembericht |
| **Portable Case** | Weitergebbare Teilmenge des Falls |

### Optionen für HTML-Berichte

```
# Einzubeziehende Daten auswählen
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Optionen
☑ Include thumbnails
☑ Include metadata
```

### Portabler Fall

```
# Portablen Fall zur Weitergabe erstellen
Generate Report → Portable Case

# Enthält:
- Ausgewählte Beweismittel
- Analyseergebnisse
- Kann in Autopsy geöffnet werden
```

---

## 💻 Kommandozeilenwerkzeuge (Sleuth Kit)

### Sleuth-Kit-Befehle

Autopsy verwendet The Sleuth Kit (TSK) als Backend. Folgende Kommandozeilenbefehle stehen zur Verfügung:

### Informationen zum Abbild

```bash
# Informationen zum Abbild ermitteln
img_stat image.E01
img_stat image.dd

# Dateisysteminformationen ermitteln
fsstat -o 2048 image.dd
```

### Dateien auflisten

```bash
# Dateien auflisten
fls -r -o 2048 image.dd

# Gelöschte Dateien auflisten
fls -r -d -o 2048 image.dd

# Optionen:
# -r    Rekursiv
# -d    Nur gelöschte Dateien
# -o    Offset (Partitionsanfang)
```

### Dateien wiederherstellen

```bash
# Datei anhand des Inodes ausgeben
icat -o 2048 image.dd 12345 > recovered_file

# Dateiinhalt ausgeben
icat -r -o 2048 image.dd 12345 > file.txt
```

### Zeitachse

```bash
# Body-Datei erstellen
fls -r -m "/" -o 2048 image.dd > body.txt

# Zeitachse erstellen
mactime -b body.txt -d > timeline.csv
```

### Hashwerte berechnen

```bash
# MD5 berechnen
md5sum image.dd

# SHA256 berechnen
sha256sum image.dd

# Abbild überprüfen
sha256sum -c image.dd.sha256
```

### Häufige TSK-Werkzeuge

| Werkzeug | Zweck |
| ------ | --------- |
| `img_stat` | Informationen zum Abbild |
| `mmls` | Partitionstabelle |
| `fsstat` | Dateisysteminformationen |
| `fls` | Dateien auflisten |
| `icat` | Datei anhand des Inodes extrahieren |
| `istat` | Inode-Informationen |
| `blkls` | Nicht zugewiesene Speicherbereiche extrahieren |
| `srch_strings` | Zeichenketten finden |
| `tsk_recover` | Alle Dateien wiederherstellen |
| `mactime` | Zeitachse erstellen |

---

## 📊 Schnellübersicht

### Untersuchungsablauf

```
1. Create Case          → Neuen Fall mit Angaben anlegen
2. Add Data Source      → Datenträgerabbild oder Laufwerk hinzufügen
3. Run Ingest Modules   → Automatisierte Analyse
4. Review Results       → Extrahierte Inhalte prüfen
5. Keyword Search       → Nach Begriffen suchen
6. Timeline Analysis    → Ereignisse rekonstruieren
7. Tag Evidence         → Wichtige Elemente markieren
8. Generate Report      → Abschlussbericht erstellen
```

### Tastenkürzel

| Tastenkürzel | Aktion |
| ---------- | -------- |
| Ctrl+N | Neuer Fall |
| Ctrl+O | Fall öffnen |
| Ctrl+F | In der Tabelle suchen |
| Ctrl+G | Bericht erzeugen |
| F5 | Aktualisieren |

### Häufig zu prüfende Speicherorte

| Windows | Pfad |
| --------- | ------ |
| Zuletzt verwendete Dateien | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Downloads | `Users\*\Downloads` |
| Browserverlauf | `Users\*\AppData\Local\[Browser]\User Data` |
| Temporäre Dateien | `Users\*\AppData\Local\Temp` |
| Papierkorb | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Ereignisprotokolle | `Windows\System32\winevt\Logs` |

---

## 📚 Ressourcen

- [Autopsy: offizielle Website](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Autopsy-Dokumentation](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR-Schulungen](https://www.dfir.training/)

### Verwandte Cheatsheets
- [Volatility](../../Volatility/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)
- [Wireshark](../../Wireshark/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Installation:** Das Kali-Paket aus dem apt-Beispiel ist der ältere Autopsy Forensic Browser 2.24 und nicht die moderne Desktop-Oberfläche 4.x. Die aktuelle Desktop-Dokumentation empfiehlt mindestens 16 GB RAM; die Tabelle ist daher keine verbindliche aktuelle Mindestanforderung. Die ursprünglichen Installationsbeispiele bleiben erhalten. [Kali: Autopsy-Paket](https://www.kali.org/tools/autopsy/), [Autopsy: Installation](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/installation_page.html).
- **Fälle und Datenquellen:** Die Verzeichnisgrafik ist schematisch. Ein Fall verweist normalerweise auf seine Datenquellen, statt die vollständigen Beweismittel automatisch in den Fallordner zu kopieren. Multi-user erfordert zusätzlich konfigurierte Dienste; es ist nicht nur eine Auswahl im Assistenten. Format- und Mobilgeräteunterstützung hängen von Version, Modulen und bereitgestellter Datenquelle ab; die Tabellen sind keine Garantie für jede Datei mit diesen Endungen. [Autopsy: Fälle](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/cases_page.html), [Autopsy: Datenquellen](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/ds_page.html).
- **Oberfläche und Hashbewertung:** Menüpfade, Modulnamen, Tastenkürzel und Farben sind versionsabhängige Orientierungshilfen. Die aktuelle Oberfläche verwendet beispielsweise Hash Sets und Picture Analyzer. NSRL bedeutet „bekannt“, nicht automatisch „harmlos“; die Bedeutung eines Treffers hängt von der Untersuchung ab. Die aktuelle Hash-Lookup-Dokumentation beschreibt MD5-Abgleiche und zusätzlich berechnete SHA-256-Werte, keine pauschale SHA1-Abgleichsgarantie. [Autopsy: Benutzerhandbuch](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/), [Autopsy: Hash Lookup](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/hash_db_page.html).
- **Befunde und Zeitachse:** Ein Zeitstempel oder Suchtreffer beweist nicht für sich eine konkrete Benutzerhandlung. Zeitbasis, Dateisystem und Ingest-Ergebnisse sind mitzuberücksichtigen; während Ingest noch läuft, ist die Zeitachse unvollständig. Die Regexes sind Suchmuster, keine vollständigen Validatoren: Das IPv4-Muster akzeptiert beispielsweise auch Zahlen über 255. Auch das Finden einer gelöschten Datei garantiert nicht deren vollständige Wiederherstellung. [Autopsy: Timeline](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/timeline_page.html), [TSK: fls](https://www.sleuthkit.org/sleuthkit/man/fls.html).
- **TSK-Beispiele:** Der Offset bei -o wird in Sektoren angegeben; 2048 ist ein Beispielwert, keine universelle Partitionsposition. Bei icat aktiviert -r Wiederherstellungstechniken für gelöschte Dateien. tsk_recover exportiert standardmäßig nur nicht zugewiesene Dateien; für alle Dateien beschreibt das Handbuch die Option -e. Die TSK-Befehle laufen getrennt von den GUI-Schritten. Eine Prüfsumme bestätigt nur die Übereinstimmung mit einem vertrauenswürdigen Vergleichswert, nicht allein Herkunft oder Vollständigkeit eines Beweismittels. [TSK: icat](https://www.sleuthkit.org/sleuthkit/man/icat.html), [TSK: fls](https://www.sleuthkit.org/sleuthkit/man/fls.html), [TSK: tsk_recover](https://www.sleuthkit.org/sleuthkit/man/tsk_recover.html).

---

<p align="center">
  <b>🔍 Digitale Forensik beherrschen!</b><br>
  <i>Autopsy – ein hilfreicher Begleiter bei digitalen Untersuchungen</i>
</p>
