# 📷 ExifTool - Scheda di riferimento per l'estrazione dei metadati

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Strumento per estrarre metadati
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

> **Nota sugli esempi:** leggere un formato non implica poterlo scrivere: il supporto varia per formato e tag, quindi gli esempi di rimozione per Office/video non sono universali. `-all=` non garantisce di eliminare qualsiasi informazione dal file; nei PDF le modifiche sono incrementali e i vecchi metadati possono restare recuperabili. `-overwrite_original` evita il backup, non rende la cancellazione sicura. Anche data, software e coordinate possono essere modificati e non provano da soli autenticità o posizione; l’URL Maps originale non applica automaticamente una conversione in gradi decimali. Il valore `12.xx` e i conteggi dei formati sono riferimenti illustrativi. Comandi e valori restano invariati: vedi la [documentazione ufficiale](https://exiftool.org/exiftool_pod.html) e le [note sui PDF](https://exiftool.org/TagNames/PDF.html).

---

## 📋 Indice

- [Che cos'è ExifTool](#-che-cosè-exiftool)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Lettura dei metadati](#-lettura-dei-metadati)
- [Scrittura dei metadati](#-scrittura-dei-metadati)
- [Rimozione dei metadati](#-rimozione-dei-metadati)
- [Tipi di file](#-tipi-di-file)
- [OSINT e analisi forense](#-osint-e-analisi-forense)
- [Elaborazione in blocco](#-elaborazione-in-blocco)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è ExifTool

**ExifTool** è un potente strumento a riga di comando per leggere, scrivere e modificare i metadati dei file. È essenziale per:

- 🔍 **OSINT** - Estrarre posizione e informazioni sul dispositivo dalle immagini
- 🔬 **Analisi forense** - Esaminare i metadati dei documenti
- 🔐 **Privacy** - Rimuovere metadati sensibili
- 📸 **Fotografia** - Gestire i metadati delle foto
- 📄 **Documenti** - Analizzare i metadati di file PDF e Office

### Perché i metadati sono importanti

| Caso d'uso | Esempio |
|----------|---------|
| **Geolocalizzazione** | Coordinate GPS nelle foto |
| **Informazioni sul dispositivo** | Modello della fotocamera e numero di serie |
| **Riferimenti temporali** | Quando il file è stato creato o modificato |
| **Autore** | Chi ha creato il documento |
| **Software** | Quale applicazione ha creato il file |
| **Cronologia delle modifiche** | Date e ore delle modifiche |

---

## 🚀 Installazione

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Oppure
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Scarica dal sito ufficiale
https://exiftool.org/

# Estrai exiftool(-k).exe
# Rinomina in exiftool.exe
# Aggiungi al PATH oppure usa direttamente
```

### Verifica l'installazione

```bash
exiftool -ver
# Output: 12.xx
```

---

## 💻 Utilizzo di base

### Sintassi di base

```bash
# Leggi i metadati
exiftool [OPTIONS] FILE

# Scrivi i metadati
exiftool -TAG=VALUE FILE

# Più file
exiftool *.jpg
exiftool -r directory/
```

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-r` | Ricorsivo (elabora le sottodirectory) |
| `-v` | Output dettagliato |
| `-n` | Senza conversione per la visualizzazione (valori grezzi) |
| `-s` | Nomi brevi dei tag |
| `-G` | Mostra il nome del gruppo per ogni tag |
| `-j` | Output JSON |
| `-csv` | Output CSV |
| `-overwrite_original` | Non creare una copia di backup |

---

## 📖 Lettura dei metadati

### Visualizza tutti i metadati

```bash
# Tutti i metadati
exiftool image.jpg

# Dettagliato
exiftool -v image.jpg

# Molto dettagliato
exiftool -v3 image.jpg
```

### Tag specifici

```bash
# Singolo tag
exiftool -GPSLatitude image.jpg

# Più tag
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Escludi tag
exiftool --MakerNotes image.jpg
```

### Lettura per gruppo

```bash
# Mostra tutti i tag EXIF
exiftool -EXIF:all image.jpg

# Mostra tutti i tag IPTC
exiftool -IPTC:all image.jpg

# Mostra tutti i tag XMP
exiftool -XMP:all image.jpg

# Mostra tutti i tag GPS
exiftool -GPS:all image.jpg

# Mostra i nomi dei gruppi
exiftool -G image.jpg
```

### Gruppi di metadati comuni

| Gruppo | Descrizione |
|-------|-------------|
| EXIF | Dati della fotocamera o dell'immagine |
| IPTC | Metadati giornalistici e di stampa |
| XMP | Metadati estensibili Adobe |
| GPS | Dati di posizione |
| MakerNotes | Dati specifici della fotocamera |
| File | Informazioni di base sul file |
| Composite | Valori calcolati |

### Formati di output

```bash
# Output JSON
exiftool -j image.jpg

# Output CSV
exiftool -csv image.jpg

# Output HTML
exiftool -htmlDump image.jpg > output.html

# Separati da tabulazioni
exiftool -t image.jpg

# Formato personalizzato
exiftool -p '$filename: $make $model' image.jpg
```

---

## ✏ Scrittura dei metadati

### Scrivi un singolo tag

```bash
# Imposta l'autore
exiftool -Author="John Doe" image.jpg

# Imposta il copyright
exiftool -Copyright="© 2024 Company" image.jpg

# Imposta il commento
exiftool -Comment="My photo" image.jpg
```

### Scrivi più tag

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Scrivi le coordinate GPS

```bash
# Imposta le coordinate GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Con riferimenti geografici
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# Dal formato URL di Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Scrivi data e ora

```bash
# Imposta data e ora
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Imposta tutte le date
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Sposta le date
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Aggiungi 1 anno
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Sottrai 7 giorni
```

### Copia i metadati tra file

```bash
# Copia tutti i metadati
exiftool -TagsFromFile source.jpg destination.jpg

# Copia tag specifici
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Copia su più file
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

## 🗑 Rimozione dei metadati

### Rimuovi tutti i metadati

```bash
# Rimuovi tutti i metadati (mantiene il file utilizzabile)
exiftool -all= image.jpg

# Rimuovi senza backup
exiftool -overwrite_original -all= image.jpg

# Elabora più file
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Rimuovi metadati specifici

```bash
# Rimuovi solo i dati GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Rimuovi solo i metadati EXIF
exiftool -EXIF:all= image.jpg

# Rimuovi i commenti
exiftool -Comment= image.jpg

# Rimuovi le informazioni sull'autore
exiftool -Author= -Creator= -Artist= image.jpg
```

### Rimuovi da tipi di file specifici

```bash
# Immagini
exiftool -all= *.jpg *.png *.gif

# PDF
exiftool -all= document.pdf

# File Office
exiftool -all= *.docx *.xlsx *.pptx

# Video
exiftool -all= video.mp4
```

### Rimozione dei dati per la privacy

```bash
# Rimuovi tutte le informazioni identificative
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 Tipi di file

### Tipi di file supportati (oltre 400)

| Categoria | Formati |
|----------|---------|
| **Immagini** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Foto RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Video** | MP4, MOV, AVI, MKV, WebM |
| **Audio** | MP3, WAV, FLAC, M4A, OGG |
| **Documenti** | PDF, DOCX, XLSX, PPTX |
| **Archivi** | ZIP (supporto limitato) |

### Operazioni per tipo di file

```bash
# Metadati PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Metadati video
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Metadati audio
exiftool -Artist -Album -Title -Duration audio.mp3

# Documenti Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT e analisi forense

### Estrai la posizione GPS

```bash
# Mostra le coordinate GPS
exiftool -GPS:all image.jpg

# GPS in formato leggibile
exiftool -GPSPosition image.jpg

# Gradi decimali
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# URL di Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Identificazione del dispositivo

```bash
# Informazioni sulla fotocamera
exiftool -Make -Model -SerialNumber image.jpg

# Software utilizzato
exiftool -Software -CreatorTool image.jpg

# Informazioni sull'obiettivo (se disponibili)
exiftool -Lens -LensModel -FocalLength image.jpg

# Informazioni dettagliate sulla fotocamera
exiftool -MakerNotes:all image.jpg
```

### Analisi temporale

```bash
# Tutte le date e le ore
exiftool -time:all image.jpg

# Date e ore del file system
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Date e ore EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Trova discrepanze temporali
exiftool -a -G1 -s -time:all image.jpg
```

### Analisi forense dei documenti

```bash
# Analisi PDF
exiftool -a -G1 document.pdf

# Cronologia dei file Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Trova informazioni sull'autore
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Autenticità delle immagini

```bash
# Verifica la presenza di software di modifica
exiftool -Software -HistorySoftwareAgent image.jpg

# Confronta la miniatura con l'immagine
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Verifica le modifiche
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Query OSINT utili

```bash
# Estrai tutte le informazioni identificative
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Verifica la presenza di strumenti di steganografia
exiftool -Software image.jpg | grep -i "steg\|hide"

# Estrai la miniatura incorporata
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Trova artefatti dei social media
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

## ⚙ Elaborazione in blocco

### Elabora più file

```bash
# Tutti i JPG nella directory corrente
exiftool *.jpg

# Ricorsivo
exiftool -r directory/

# Estensioni specifiche
exiftool -ext jpg -ext png directory/
```

### Rimozione dei metadati in blocco

```bash
# Rimuovi da tutte le immagini
exiftool -all= -overwrite_original -r directory/

# Solo tipi specifici
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Rinomina i file in blocco

```bash
# Rinomina in base alla data
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Rinomina in base al modello della fotocamera
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Rinomina con un contatore
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Copia dei metadati in blocco

```bash
# Copia il GPS da un file a tutti gli altri
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Esporta in CSV

```bash
# Esporta i metadati in CSV
exiftool -csv *.jpg > metadata.csv

# Campi specifici
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Elaborazione condizionale

```bash
# Solo file con dati GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Solo file di una fotocamera specifica
exiftool -if '$Make eq "Canon"' -filename *.jpg

# File modificati dopo una data
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Riferimento rapido

### Comandi più utilizzati

| Scopo | Comando |
|---------|---------|
| Visualizza tutti i metadati | `exiftool image.jpg` |
| Visualizza il GPS | `exiftool -GPS:all image.jpg` |
| Rimuovi tutti i metadati | `exiftool -all= image.jpg` |
| Rimuovi il GPS | `exiftool -GPS:all= image.jpg` |
| Output JSON | `exiftool -j image.jpg` |
| Ricorsivo | `exiftool -r directory/` |
| Informazioni sulla fotocamera | `exiftool -Make -Model image.jpg` |
| Date e ore | `exiftool -time:all image.jpg` |

### Tag comuni

| Tag | Descrizione |
|-----|-------------|
| `-Make` | Produttore della fotocamera |
| `-Model` | Modello della fotocamera |
| `-DateTimeOriginal` | Quando è stata scattata la foto |
| `-GPSLatitude` | Latitudine GPS |
| `-GPSLongitude` | Longitudine GPS |
| `-Author` | Autore del file |
| `-Copyright` | Informazioni sul copyright |
| `-Software` | Software di modifica |
| `-ImageWidth` | Larghezza dell'immagine |
| `-ImageHeight` | Altezza dell'immagine |

### Checklist per la privacy

```bash
# 1. Verifica quali metadati esistono
exiftool -a -G1 image.jpg

# 2. Verifica la presenza di dati GPS
exiftool -GPS:all image.jpg

# 3. Verifica le informazioni identificative
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Rimuovi tutto
exiftool -all= -overwrite_original image.jpg

# 5. Verifica la rimozione
exiftool image.jpg
```

### Checklist forense

```bash
# 1. Conserva l'originale (prima crea una copia!)
cp image.jpg image_original.jpg

# 2. Estrai tutti i metadati
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Estrai il GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Timeline
exiftool -time:all -G1 image.jpg

# 5. Informazioni sul dispositivo
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Verifica eventuali manomissioni
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Risorse

- [ExifTool ufficiale](https://exiftool.org/)
- [Documentazione ExifTool](https://exiftool.org/TagNames/)
- [Forum ExifTool](https://exiftool.org/forum/)
- [Tipi di file supportati](https://exiftool.org/#supported)

### Schede correlate
- [Autopsy](../../Autopsy/translations/README.it.md)
- [Volatility](../../Volatility/translations/README.it.md)
- [Comandi Linux](../../Linux-Commands/translations/README.it.md)

---

<p align="center">
  <b>📷 Padroneggia l'analisi dei metadati!</b><br>
  <i>ExifTool - Ogni file racconta una storia</i>
</p>
