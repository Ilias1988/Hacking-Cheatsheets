# 🔍 Autopsy - Cheatsheet Digital Forensics Platform

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Digital Forensics Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 Indice

- [Cos'è Autopsy](#-cosè-autopsy)
- [Installazione](#-installazione)
- [Creazione di un Caso](#-creazione-di-un-caso)
- [Aggiunta Sorgenti Dati](#-aggiunta-sorgenti-dati)
- [Moduli Ingest](#-moduli-ingest)
- [Funzionalità di Analisi](#-funzionalità-di-analisi)
- [Analisi File](#-analisi-file)
- [Analisi Timeline](#-analisi-timeline)
- [Ricerca Parole Chiave](#-ricerca-parole-chiave)
- [Reportistica](#-reportistica)
- [Strumenti CLI (Sleuth Kit)](#-strumenti-cli-sleuth-kit)

---

## 🎯 Cos'è Autopsy

**Autopsy** è una piattaforma di digital forensics gratuita e open-source basata su The Sleuth Kit (TSK). Offre:

- 🖥️ **Interfaccia GUI** - Analisi forense user-friendly
- 💾 **Analisi Disco** - Esamina hard disk, USB, immagini
- 📁 **Recupero File** - Recupera file cancellati
- 📊 **Analisi Timeline** - Ricostruisce attività utente
- 🔍 **Ricerca Parole Chiave** - Cerca su tutti i dati
- 📧 **Analisi Email** - Parsing database email
- 🌐 **Web Artifacts** - Cronologia browser, segnalibri
- 📱 **Mobile Forensics** - Analisi Android/iOS

### Tipi di Evidenza Supportati

| Tipo | Formati |
|------|---------|
| **Immagini Disco** | E01, AFF, Raw (dd), VHD, VMDK |
| **Disco Locale** | Dischi fisici/logici |
| **File VM** | VMDK, VHD, VHDX |
| **File Logici** | Cartelle, file |
| **Spazio Non Allocato** | Dati carved |

---

## 🚀 Installazione

### Windows

```bash
# Scarica dal sito ufficiale
https://www.autopsy.com/download/

# Esegui installer
autopsy-x.x.x-64bit.msi

# Percorso predefinito
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

```bash
# Installa via apt
sudo apt update
sudo apt install autopsy

# Avvia
autopsy
```

### Requisiti

| Componente | Minimo | Raccomandato |
|------------|--------|--------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Multi-core | 4+ core |
| **Storage** | SSD | NVMe SSD |
| **OS** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Creazione di un Caso

### Nuovo Caso (Wizard)

1. **File → New Case**
2. Inserisci **Nome Caso** (es: "Indagine_2024")
3. Inserisci **Cartella Base** (dove salvare i file del caso)
4. Seleziona **Tipo Caso**:
   - Single-user (locale)
   - Multi-user (collaborativo)
5. Inserisci **Numero Caso** (opzionale)
6. Inserisci dati **Examiner**
7. Clicca **Finish**

### Struttura Caso

```
CaseName/
├── autopsy.db           # Database caso (SQLite)
├── Reports/             # Report generati
├── Export/              # File esportati
├── ModuleOutput/        # Risultati moduli
└── [HostName]/          # Dati per host
    ├── [DataSource]/    # File prove
    └── Reports/
```

### Proprietà di un Caso

| Campo | Descrizione |
|-------|-------------|
| Nome Caso | Identificativo indagine |
| Numero Caso | Numero di riferimento |
| Examiner | Nome analista |
| Cartella Base | Percorso salvataggio caso |
| Tipo Caso | Single/Multi-user |

---

## 📀 Aggiunta delle Sorgenti Dati

### Tipi di Sorgente

| Tipo | Descrizione |
|------|-------------|
| **Immagine Disco o File VM** | E01, Raw, VMDK, VHD |
| **Disco Locale** | Analisi disco fisico |
| **File Logici** | Import cartelle/file |
| **Spazio Non Allocato** | Immagine raw dello spazio libero |

### Aggiunta Immagine Disco

1. **Add Data Source** (toolbar)
2. Seleziona **Disk Image or VM File**
3. Sfoglia file immagine
4. Seleziona **Fuso Orario**
5. Configura **Moduli Ingest** (vedi sotto)
6. Clicca **Finish**

### Aggiunta Disco Locale

```
⚠️ ATTENZIONE: Usa write-blocker o modalità sola lettura!
```

1. **Add Data Source**
2. Seleziona **Local Disk**
3. Scegli disco dalla lista
4. Configura moduli ingest
5. **Finish**

### Formati Immagine Supportati

| Formato | Estensione | Descrizione |
|---------|------------|-------------|
| Raw | .raw, .dd, .img | Copia bit-a-bit |
| EnCase | .E01, .Ex01 | Expert Witness format |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | Disco VMware |
| VHD/VHDX | .vhd, .vhdx | Disco Hyper-V |

---

## ⚙️ Moduli Ingest

### Cosa sono i Moduli Ingest?

Moduli di analisi automatica che estraggono artefatti dalle sorgenti dati.

### Moduli Principali

| Modulo | Descrizione |
|--------|-------------|
| **Recent Activity** | Cronologia browser, download, cookie |
| **Hash Lookup** | Lookup MD5/SHA1 su database noti |
| **File Type Identification** | Identifica tipo file da firma |
| **Keyword Search** | Indicizza e cerca contenuti |
| **Email Parser** | Parsing PST, MBOX, EML |
| **Extension Mismatch** | Rileva file rinominati |
| **Embedded File Extractor** | Estrae da archivi/documenti |
| **EXIF Parser** | Estrae metadati immagini |
| **Encryption Detection** | Trova file cifrati |
| **Interesting Files** | Trova file notevoli |
| **PhotoRec Carver** | File carving |
| **Virtual Machine Extractor** | Estrae file VM |

### Database Hash

```
# Aggiungi database hash noti
Tools → Options → Hash Database

# Tipi:
- Known (NSRL) - Ignora file noti buoni
- Known Bad - Allerta su hash malware
```

### NSRL (National Software Reference Library)

```bash
# Scarica NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Importa in Autopsy
Tools → Options → Hash Database → Import Database
```

### Configurazione Moduli

```
# Abilita/Disabilita moduli per caso
Right-click data source → Run Ingest Modules

# Configura impostazioni modulo
Clicca icona ingranaggio accanto al nome modulo
```

---

## 🔬 Funzionalità di Analisi

### Tree View (Pannello Sinistro)

```
Data Sources
├── [Nome Immagine]
│   ├── Volume 1
│   │   ├── [Cartelle]
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

### Aree di Analisi Rapida

| Categoria | Cosa Cercare |
|-----------|--------------|
| **Deleted Files** | File recuperati, cancellazioni utente |
| **Web History** | Siti visitati, ricerche |
| **Downloads** | File scaricati |
| **Recent Documents** | File aperti di recente |
| **USB Devices** | Dispositivi collegati |
| **Email** | Comunicazioni |
| **Images** | Foto, screenshot |
| **Archives** | Contenuti ZIP, RAR |

---

## 📄 Analisi File

### Visualizzazione File

```
# Content Viewer (Pannello Inferiore)
- Hex        : Vista esadecimale raw
- Text       : Contenuto testuale
- Application: Vista renderizzata
- Strings    : Stringhe estratte
- Metadati   : Metadati file
- Results    : Risultati analisi
- Indexed Text: Testo ricercabile
```

### Proprietà File

| Proprietà | Descrizione |
|-----------|-------------|
| Name | Nome file |
| Location | Percorso completo |
| Size | Dimensione file |
| Created | Data creazione |
| Modified | Ultima modifica |
| Accessed | Ultimo accesso |
| MD5/SHA1/SHA256 | Hash |
| Known | Stato NSRL |
| MIME Type | Tipo rilevato |

### Estrazione File

```
# Estrai singolo file
Right-click → Extract File(s)

# Estrai multipli
Seleziona file → Right-click → Extract

# Estrai in posizione specifica
Actions → Extract to...
```

### Recupero File Cancellati

```
Views → Deleted Files

# Mostra:
- File in $Recycle.Bin
- File marcati deleted in MFT
- File carved (PhotoRec)
```

---

## 📊 Analisi Timeline

### Timeline View

```
# Accedi alla Timeline
Tools → Timeline

# Oppure
Results → Timeline
```

### Funzionalità Timeline

| Funzione | Descrizione |
|----------|-------------|
| **Counts View** | Frequenza eventi nel tempo |
| **Details View** | Eventi individuali |
| **List View** | Lista tabellare eventi |
| **Filters** | Filtra per tipo, data, ecc |

### Tipi di Evento

| Tipo | Colore | Descrizione |
|------|--------|-------------|
| File Modified | Blu | Contenuto file cambiato |
| File Accessed | Verde | File aperto |
| File Created | Giallo | File creato |
| File Changed | Rosso | Metadati cambiati |
| Web Activity | Viola | Eventi browser |

### Filtri Timeline

```
# Filtra per intervallo date
Start Date: 2024-01-01
End Date: 2024-12-31

# Filtra per tipo
☑ File System
☑ Web Activity
☐ Registry (se parsato)

# Filtra per sorgente dati
☑ Disk Image 1
☐ Disk Image 2
```

### Esportazione Timeline

```
# Esporta timeline
File → Export → Timeline (CSV/TSV)

# Usa con strumenti esterni
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Ricerca Parole Chiave

### Creazione Liste Parole Chiave

```
# Tools → Options → Keyword Search

# Nuova lista
1. Click "New List"
2. Dai un nome
3. Aggiungi parole chiave:
   - Literal (match esatto)
   - Regex (pattern)
   - Substring
```

### Parole Chiave Comuni

```
# Financial
credit card
bank account
swift
iban
bitcoin
wallet

# Credentials
password
login
username
secret
key

# Sensitive
confidential
secret
classified
internal only

# Network
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Esempi di Regex

| Scopo | Regex |
|-------|-------|
| Email | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP Address | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Telefono (US) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Carta di Credito | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Esecuzione Ricerca

```
# Ricerca ad-hoc
Keyword Search (in alto a destra) → Inserisci termine → Search

# Usando liste parole chiave
Right-click data source → Run Ingest Modules → Keyword Search
```

---

## 📝 Reportistica

### Genera Report

```
# Genera Report
Tools → Generate Report

# Oppure
Tasto destro sul caso → Generate Report
```

### Tipi di Report

| Tipo | Descrizione |
|------|-------------|
| **HTML Report** | Web, include immagini |
| **Excel Report** | Foglio di calcolo |
| **Text Report** | Testo semplice |
| **TSK Body File** | Timeline body file |
| **Files - FS** | Report file system |
| **Portable Case** | Sottoinsieme condivisibile |

### Opzioni Report HTML

```
# Seleziona dati da includere
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Opzioni
☑ Includi thumbnails
☑ Includi metadati
```

### Portable Case

```
# Crea portable case per condivisione
Generate Report → Portable Case

# Include:
- Prova selezionata
- Risultati analisi
- Apribile in Autopsy
```

---

## 💻 Strumenti CLI (Sleuth Kit)

### Comandi Sleuth Kit

Autopsy usa The Sleuth Kit (TSK) come backend. Comandi CLI disponibili:

### Informazioni Immagine

```bash
# Info immagine
img_stat image.E01
img_stat image.dd

# Info filesystem
fsstat -o 2048 image.dd
```

### Lista File

```bash
# Lista file
fls -r -o 2048 image.dd

# Lista file cancellati
fls -r -d -o 2048 image.dd

# Opzioni:
# -r    Ricorsivo
# -d    Solo file cancellati
# -o    Offset (inizio partizione)
```

### Recupero File

```bash
# Estrai file per inode
icat -o 2048 image.dd 12345 > recovered_file

# Estrai contenuto file
icat -r -o 2048 image.dd 12345 > file.txt
```

### Timeline

```bash
# Crea file body
fls -r -m "/" -o 2048 image.dd > body.txt

# Crea timeline
mactime -b body.txt -d > timeline.csv
```

### Calcolo Hash

```bash
# Calcola MD5
md5sum image.dd

# Calcola SHA256
sha256sum image.dd

# Verifica immagine
sha256sum -c image.dd.sha256
```

### Strumenti TSK Comuni

| Tool | Scopo |
|------|-------|
| `img_stat` | Info immagine |
| `mmls` | Tabella partizioni |
| `fsstat` | Info filesystem |
| `fls` | Lista file |
| `icat` | Estrai file per inode |
| `istat` | Info inode |
| `blkls` | Estrai non allocato |
| `srch_strings` | Trova stringhe |
| `tsk_recover` | Recupera tutti i file |
| `mactime` | Crea timeline |

---

## 📊 Riferimento Rapido

### Workflow Investigazione

```
1. Crea Caso           → Nuovo caso con dettagli
2. Aggiungi Sorgente   → Immagine disco o drive
3. Avvia Moduli Ingest → Analisi automatica
4. Rivedi Risultati    → Controlla contenuti estratti
5. Ricerca Parole      → Cerca termini
6. Analisi Timeline    → Ricostruisci eventi
7. Tagga Prova         → Segna elementi importanti
8. Genera Report       → Crea report finale
```

### Scorciatoie Tastiera

| Shortcut | Azione |
|----------|--------|
| Ctrl+N | Nuovo Caso |
| Ctrl+O | Apri Caso |
| Ctrl+F | Trova (in tabella) |
| Ctrl+G | Genera Report |
| F5 | Aggiorna |

### Percorsi Comuni da Controllare

| Windows | Percorso |
|---------|----------|
| Recent Files | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Downloads | `Users\*\Downloads` |
| Browser History | `Users\*\AppData\Local\[Browser]\User Data` |
| Temp Files | `Users\*\AppData\Local\Temp` |
| Recycle Bin | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Event Logs | `Windows\System32\winevt\Logs` |

---

## 📚 Risorse

- [Autopsy Official](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Autopsy Documentazione](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

### Cheatsheet Correlate
- [Volatility](../Volatility/translations/README.it.md)
- [Linux Commands](../Linux-Commands/translations/README.it.md)
- [Wireshark](../Wireshark/translations/README.it.md)

---

<p align="center">
  <b>🔍 Padroneggia la Digital Forensics!</b><br>
  <i>Autopsy - Il miglior amico dell'investigatore digitale</i>
</p>
