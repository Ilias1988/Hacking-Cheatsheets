# 📷 ExifTool – hurtigreferanse for metadataanalyse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Verktøy for uthenting av metadata
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

> **Presisering om lesing og skriving:** Uttrekk av metadata leser kilden, mens tildelinger som `-TAG=VALUE`, fjerning og kopiering av metadata skriver til filer. ExifTool bevarer normalt en sikkerhetskopi med endelsen `_original`; `-overwrite_original` endrer denne oppførselen. Lese- og skrivestøtte varierer mellom formater og tagger, så ikke alle leseeksempler har et tilsvarende skriveeksempel. Office-eksemplet med `-all=` gir ikke generell støtte for å fjerne metadata fra DOCX/XLSX/PPTX; kontroller formatets skrivestøtte i [formatoversikten](https://github.com/exiftool/exiftool/blob/master/html/index.html). Se også [offisiell dokumentasjon](https://github.com/exiftool/exiftool/blob/master/html/exiftool_pod.html). `-all=` er ingen garanti for sikker sletting av all informasjon. PDF-endringer lagres inkrementelt og kan etterlate tidligere metadata som kan gjenopprettes; se [offisiell PDF-dokumentasjon](https://github.com/exiftool/exiftool/blob/master/html/TagNames/PDF.html). Metadata kan være mangelfulle eller endret og beviser ikke alene opphav, sted eller tidspunkt.


## 📋 Innholdsfortegnelse

- [Hva er ExifTool](#-hva-er-exiftool)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Lese metadata](#-lese-metadata)
- [Skrive metadata](#-skrive-metadata)
- [Fjerne metadata](#-fjerne-metadata)
- [Filtyper](#-filtyper)
- [OSINT og digital etterforskning](#-osint-og-digital-etterforskning)
- [Satsvis behandling](#-satsvis-behandling)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er ExifTool

**ExifTool** er et kraftig kommandolinjeverktøy for å lese, skrive og endre metadata i filer. Det brukes blant annet til:

- 🔍 **OSINT** – hent ut sted og enhetsopplysninger fra bilder
- 🔬 **Digital etterforskning** – analyser dokumentmetadata
- 🔐 **Personvern** – fjern sensitive metadata
- 📸 **Fotografi** – administrer bildemetadata
- 📄 **Dokumenter** – analyser metadata i PDF- og Office-filer

### Hvorfor metadata er viktige

| Bruksområde | Eksempel |
|----------|---------|
| **Geografisk stedfesting** | GPS-koordinater i bilder |
| **Enhetsopplysninger** | Kameramodell og serienummer |
| **Tidsstempler** | Når filen ble opprettet eller endret |
| **Forfatter** | Hvem som opprettet dokumentet |
| **Programvare** | Hvilken app som opprettet filen |
| **Endringshistorikk** | Tidsstempler for endringer |

---

## 🚀 Installasjon

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Eller
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Last ned fra det offisielle nettstedet
https://exiftool.org/

# Pakk ut exiftool(-k).exe
# Gi filen navnet exiftool.exe
# Legg til i PATH eller bruk direkte
```

### Kontroller installasjonen

```bash
exiftool -ver
# Utdata: 12.xx
```

---

## 💻 Grunnleggende bruk

### Grunnleggende syntaks

```bash
# Les metadata
exiftool [OPTIONS] FILE

# Skriv metadata
exiftool -TAG=VALUE FILE

# Flere filer
exiftool *.jpg
exiftool -r directory/
```

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-r` | Rekursivt (behandle undermapper) |
| `-v` | Detaljert utdata |
| `-n` | Ingen visningskonvertering (råverdier) |
| `-s` | Korte taggnavn |
| `-G` | Vis gruppenavn for hver tagg |
| `-j` | JSON-utdata |
| `-csv` | CSV-utdata |
| `-overwrite_original` | Ikke opprett sikkerhetskopi |

---

## 📖 Lese metadata

### Vis alle metadata

```bash
# Alle metadata
exiftool image.jpg

# Detaljerte utdata
exiftool -v image.jpg

# Svært detaljert utdata
exiftool -v3 image.jpg
```

### Bestemte tagger

```bash
# Én tagg
exiftool -GPSLatitude image.jpg

# Flere tagger
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Utelat tagger
exiftool --MakerNotes image.jpg
```

### Lesing etter grupper

```bash
# Vis alle EXIF-tagger
exiftool -EXIF:all image.jpg

# Vis alle IPTC-tagger
exiftool -IPTC:all image.jpg

# Vis alle XMP-tagger
exiftool -XMP:all image.jpg

# Vis alle GPS-tagger
exiftool -GPS:all image.jpg

# Vis med gruppenavn
exiftool -G image.jpg
```

### Vanlige metadatagrupper

| Gruppe | Beskrivelse |
|-------|-------------|
| EXIF | Kamera- og bildedata |
| IPTC | Metadata for nyheter og presse |
| XMP | Adobes utvidbare metadata |
| GPS | Stedsdata |
| MakerNotes | Kameraspesifikke data |
| File | Grunnleggende filopplysninger |
| Composite | Beregnede verdier |

### Utdataformater

```bash
# JSON-utdata
exiftool -j image.jpg

# CSV-utdata
exiftool -csv image.jpg

# HTML-utdata
exiftool -htmlDump image.jpg > output.html

# Tabulatorseparert
exiftool -t image.jpg

# Egendefinert format
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="-skrive-metadata"></a>
## ✏️ Skrive metadata

### Skriv én tagg

```bash
# Angi forfatter
exiftool -Author="John Doe" image.jpg

# Angi opphavsrett
exiftool -Copyright="© 2024 Company" image.jpg

# Angi kommentar
exiftool -Comment="My photo" image.jpg
```

### Skriv flere tagger

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Skriv GPS-koordinater

```bash
# Angi GPS-koordinater
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Med retningsreferanser
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# Fra Google Maps-URL-format
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Skriv dato og klokkeslett

```bash
# Angi dato og klokkeslett
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Angi alle datoer
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Forskyv datoer
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Legg til 1 år
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Trekk fra 7 dager
```

### Kopier metadata mellom filer

```bash
# Kopier alle metadata
exiftool -TagsFromFile source.jpg destination.jpg

# Kopier bestemte tagger
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Kopier til flere filer
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="-fjerne-metadata"></a>
## 🗑️ Fjerne metadata

### Fjern alle metadata

```bash
# Fjern alle metadata (filen kan fortsatt brukes; se presiseringen)
exiftool -all= image.jpg

# Fjern uten sikkerhetskopi
exiftool -overwrite_original -all= image.jpg

# Behandle flere filer
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Fjern bestemte metadata

```bash
# Fjern bare GPS-data
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Fjern bare EXIF
exiftool -EXIF:all= image.jpg

# Fjern kommentarer
exiftool -Comment= image.jpg

# Fjern forfatteropplysninger
exiftool -Author= -Creator= -Artist= image.jpg
```

### Fjern fra bestemte filtyper

```bash
# Bilder
exiftool -all= *.jpg *.png *.gif

# PDF-er
exiftool -all= document.pdf

# Office-filer
exiftool -all= *.docx *.xlsx *.pptx

# Videoer
exiftool -all= video.mp4
```

### Fjern identifiserende opplysninger av personvernhensyn

```bash
# Fjern alle identifiserende opplysninger
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 Filtyper

### Støttede filtyper (400+)

| Kategori | Formater |
|----------|---------|
| **Bilder** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **RAW-bilder** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Video** | MP4, MOV, AVI, MKV, WebM |
| **Lyd** | MP3, WAV, FLAC, M4A, OGG |
| **Dokumenter** | PDF, DOCX, XLSX, PPTX |
| **Arkiver** | ZIP (begrenset) |

### Eksempler for bestemte filtyper

```bash
# PDF-metadata
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Videometadata
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Lydmetadata
exiftool -Artist -Album -Title -Duration audio.mp3

# Office-dokumenter
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT og digital etterforskning

### Hent ut GPS-posisjon

```bash
# Vis GPS-koordinater
exiftool -GPS:all image.jpg

# GPS i lesbart format
exiftool -GPSPosition image.jpg

# Desimalgrader
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Google Maps-URL
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Identifikasjon av enhet

```bash
# Kameraopplysninger
exiftool -Make -Model -SerialNumber image.jpg

# Brukt programvare
exiftool -Software -CreatorTool image.jpg

# Objektivopplysninger (hvis tilgjengelig)
exiftool -Lens -LensModel -FocalLength image.jpg

# Detaljerte kameraopplysninger
exiftool -MakerNotes:all image.jpg
```

### Tidslinjeanalyse

```bash
# Alle datoer og klokkeslett
exiftool -time:all image.jpg

# Filsystemets tidsstempler
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# EXIF-tidsstempler
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Finn avvik mellom datoer
exiftool -a -G1 -s -time:all image.jpg
```

### Dokumentetterforskning

```bash
# PDF-analyse
exiftool -a -G1 document.pdf

# Historikk i Office-filer
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Finn forfatteropplysninger
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Bildets ekthet

```bash
# Se etter redigeringsprogramvare
exiftool -Software -HistorySoftwareAgent image.jpg

# Sammenlign miniatyrbilde og bilde
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Se etter endringer
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Nyttige OSINT-spørringer

```bash
# Hent ut alle identifiserende opplysninger
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Se etter steganografiverktøy
exiftool -Software image.jpg | grep -i "steg\|hide"

# Hent ut innebygd miniatyrbilde
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Finn spor fra sosiale medier
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="-satsvis-behandling"></a>
## ⚙️ Satsvis behandling

### Behandle flere filer

```bash
# Alle JPG-filer i gjeldende mappe
exiftool *.jpg

# Rekursivt
exiftool -r directory/

# Bestemte filendelser
exiftool -ext jpg -ext png directory/
```

### Fjern metadata satsvis

```bash
# Fjern fra alle bilder
exiftool -all= -overwrite_original -r directory/

# Bare bestemte typer
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Gi filer nye navn satsvis

```bash
# Gi navn etter dato
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Gi navn etter kameramodell
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Gi navn med teller
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Kopier metadata satsvis

```bash
# Kopier GPS fra én fil til alle
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Eksporter til CSV

```bash
# Eksporter metadata til CSV
exiftool -csv *.jpg > metadata.csv

# Bestemte felter
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Betinget behandling

```bash
# Bare filer med GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Bare filer fra et bestemt kamera
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Filer endret etter en dato
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Hurtigreferanse

### Mest brukte kommandoer

| Formål | Kommando |
|---------|---------|
| Vis alle metadata | `exiftool image.jpg` |
| Vis GPS | `exiftool -GPS:all image.jpg` |
| Fjern alle metadata | `exiftool -all= image.jpg` |
| Fjern GPS | `exiftool -GPS:all= image.jpg` |
| JSON-utdata | `exiftool -j image.jpg` |
| Rekursivt | `exiftool -r directory/` |
| Kameraopplysninger | `exiftool -Make -Model image.jpg` |
| Tidsstempler | `exiftool -time:all image.jpg` |

### Vanlige tagger

| Tagg | Beskrivelse |
|-----|-------------|
| `-Make` | Kameraprodusent |
| `-Model` | Kameramodell |
| `-DateTimeOriginal` | Når bildet ble tatt |
| `-GPSLatitude` | GPS-breddegrad |
| `-GPSLongitude` | GPS-lengdegrad |
| `-Author` | Filens forfatter |
| `-Copyright` | Opphavsrettsopplysninger |
| `-Software` | Redigeringsprogramvare |
| `-ImageWidth` | Bildebredde |
| `-ImageHeight` | Bildehøyde |

### Sjekkliste for personvern

```bash
# 1. Undersøk hvilke metadata som finnes
exiftool -a -G1 image.jpg

# 2. Se etter GPS
exiftool -GPS:all image.jpg

# 3. Se etter identifiserende opplysninger
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Fjern alt
exiftool -all= -overwrite_original image.jpg

# 5. Kontroller fjerningen
exiftool image.jpg
```

### Sjekkliste for digital etterforskning

```bash
# 1. Bevar originalen (lag en kopi først!)
cp image.jpg image_original.jpg

# 2. Full uthenting av metadata
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Hent ut GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Tidslinje
exiftool -time:all -G1 image.jpg

# 5. Enhetsopplysninger
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Se etter manipulering
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Ressurser

- [ExifTools offisielle nettsted](https://exiftool.org/)
- [ExifTool-dokumentasjon](https://exiftool.org/TagNames/)
- [ExifTool-forum](https://exiftool.org/forum/)
- [Støttede filtyper](https://exiftool.org/#supported)

### Relaterte hurtigreferanser
- [Autopsy](../../Autopsy/translations/README.nb.md)
- [Volatility](../../Volatility/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)

---

<p align="center">
  <b>📷 Lær metadataanalyse!</b><br>
  <i>ExifTool – hver fil kan inneholde interessante spor</i>
</p>
