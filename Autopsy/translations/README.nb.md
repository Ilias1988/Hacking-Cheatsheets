# 🔍 Autopsy – hurtigreferanse for digital etterforskning

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Plattform for digital etterforskning
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

> **Presisering om utgaver og tolkning:** Menystiene og feltnavnene beholdes på engelsk slik at de kan gjenfinnes i programmet. Veiledningen blander den moderne skrivebordsutgaven med den eldre nettleserbaserte utgaven: [Kalis `autopsy`-pakke](https://www.kali.org/tools/autopsy/) er Autopsy Forensic Browser 2.24 og gir ikke skrivebordsgrensesnittet som beskrives videre. Støtte for datakilder og moduler avhenger av utgave og installerte komponenter; særlig omtalen av mobilanalyse er generell. For Sleuth Kit angir `-o` startforskyvning i sektorer, ikke byte; se [den offisielle `fls`-manualen](https://www.sleuthkit.org/sleuthkit/man/fls.html). Et søke- eller hashsett-treff må tolkes sammen med andre spor og dokumentert bevisbehandling.


## 📋 Innholdsfortegnelse

- [Hva er Autopsy](#-hva-er-autopsy)
- [Installasjon](#-installasjon)
- [Opprettelse av en sak](#-opprettelse-av-en-sak)
- [Legge til datakilder](#-legge-til-datakilder)
- [Ingest Modules (analysemoduler)](#-ingest-modules-analysemoduler)
- [Analysefunksjoner](#-analysefunksjoner)
- [Filanalyse](#-filanalyse)
- [Tidslinjeanalyse](#-tidslinjeanalyse)
- [Nøkkelordsøk](#-nøkkelordsøk)
- [Rapportering](#-rapportering)
- [Kommandolinjeverktøy (Sleuth Kit)](#-kommandolinjeverktøy-sleuth-kit)

---

## 🎯 Hva er Autopsy

**Autopsy** er en gratis plattform med åpen kildekode for digital etterforskning, bygget på The Sleuth Kit (TSK). Den tilbyr:

- 🖥️ **Grafisk grensesnitt** – brukervennlig etterforskningsanalyse
- 💾 **Diskanalyse** – undersøk harddisker, USB-enheter og avbildninger
- 📁 **Filgjenoppretting** – gjenopprett slettede filer
- 📊 **Tidslinjeanalyse** – rekonstruer brukeraktivitet
- 🔍 **Nøkkelordsøk** – søk i alle data
- 📧 **E-postanalyse** – tolk e-postdatabaser
- 🌐 **Webartefakter** – nettleserhistorikk og bokmerker
- 📱 **Mobiletterforskning** – analyse av Android/iOS

### Støttede typer bevismateriale

| Type | Formater |
|------|---------|
| **Diskavbildninger** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk** | Fysiske/logiske disker |
| **VM-filer** | VMDK, VHD, VHDX |
| **Logical Files** | Mapper og filer |
| **Unallocated Space** | Uthentede data (file carving) |

---

## 🚀 Installasjon

### Windows

```bash
# Last ned fra det offisielle nettstedet
https://www.autopsy.com/download/

# Kjør installasjonsprogrammet
autopsy-x.x.x-64bit.msi

# Standardplassering
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

```bash
# Installer med apt
sudo apt update
sudo apt install autopsy

# Kjør
autopsy
```

### Systemkrav

| Komponent | Minimum | Anbefalt |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Flere kjerner | 4+ kjerner |
| **Lagring** | SSD | NVMe SSD |
| **OS** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Opprettelse av en sak

### Veiviser for ny sak

1. **File → New Case**
2. Angi **Case Name** (f.eks. "Investigation_2024")
3. Angi **Base Directory** (der saksfilene lagres)
4. Velg **Case Type**:
   - Single-user (lokalt, én bruker)
   - Multi-user (samarbeid med flere brukere)
5. Angi **Case Number** (valgfritt)
6. Angi opplysninger under **Examiner**
7. Klikk på **Finish**

### Sakens katalogstruktur

```
CaseName/
├── autopsy.db           # Saksdatabase (SQLite)
├── Reports/             # Genererte rapporter
├── Export/              # Eksporterte filer
├── ModuleOutput/        # Modulresultater
└── [HostName]/          # Data for hver vert
    ├── [DataSource]/    # Filer med bevismateriale
    └── Reports/
```

### Saksegenskaper

| Felt | Beskrivelse |
|-------|-------------|
| Case Name | Identifikator for undersøkelsen |
| Case Number | Referansenummer |
| Examiner | Analytikerens navn |
| Base Directory | Lagringssted for saken |
| Case Type | Enkeltbruker/flere brukere |

---

## 📀 Legge til datakilder

### Typer datakilder

| Type | Beskrivelse |
|------|-------------|
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | Analyse av fysisk disk |
| **Logical Files** | Import av mapper/filer |
| **Unallocated Space** | Rå avbildning av ledig plass |

### Legge til en diskavbildning

1. **Add Data Source** (verktøylinjen)
2. Velg **Disk Image or VM File**
3. Bla til avbildningsfilen
4. Velg **Time Zone**
5. Konfigurer **Ingest Modules** (neste avsnitt)
6. Klikk på **Finish**

### Legge til en lokal disk

```
⚠️ ADVARSEL: Bruk skriveblokkering eller skrivebeskyttet modus!
```

1. **Add Data Source**
2. Velg **Local Disk**
3. Velg disken fra listen
4. Konfigurer analysemodulene
5. **Finish**

### Støttede avbildningsformater

| Format | Filendelse | Beskrivelse |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | Bit-for-bit-kopi |
| EnCase | .E01, .Ex01 | Expert Witness format |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | VMware-disk |
| VHD/VHDX | .vhd, .vhdx | Hyper-V-disk |

---

<a id="-ingest-modules-analysemoduler"></a>
## ⚙️ Ingest Modules (analysemoduler)

### Hva er ingest-moduler?

Automatiserte analysemoduler som undersøker datakilder og henter ut artefakter, altså spor av aktivitet.

### Kjernemoduler

| Modul | Beskrivelse |
|--------|-------------|
| **Recent Activity** | Nettleserhistorikk, nedlastinger og informasjonskapsler |
| **Hash Lookup** | MD5-/SHA1-oppslag i kjente databaser |
| **File Type Identification** | Identifiser filtyper fra signaturer |
| **Keyword Search** | Indekser og søk i innhold |
| **Email Parser** | Tolk PST, MBOX og EML |
| **Extension Mismatch** | Finn filer med endret navn |
| **Embedded File Extractor** | Hent ut fra arkiver og dokumenter |
| **EXIF Parser** | Hent ut bildemetadata |
| **Encryption Detection** | Finn krypterte filer |
| **Interesting Files** | Finn interessante filer |
| **PhotoRec Carver** | Uthenting av filer basert på datamønstre (file carving) |
| **Virtual Machine Extractor** | Hent ut VM-filer |

### Hashdatabaser

```
# Legg til kjente hashdatabaser
Tools → Options → Hash Database

# Typer:
- Known (NSRL) – utelat kjente gode filer
- Known Bad – varsle om kjente skadevarehasher
```

### NSRL (National Software Reference Library)

```bash
# Last ned NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Importer i Autopsy
Tools → Options → Hash Database → Import Database
```

### Konfigurasjon av moduler

```
# Aktiver/deaktiver moduler for hver sak
Høyreklikk på datakilden → Run Ingest Modules

# Konfigurer modulinnstillingene
Klikk på tannhjulet ved siden av modulnavnet
```

---

## 🔬 Analysefunksjoner

### Trevisning (venstre panel)

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

### Områder for rask analyse

| Kategori | Hva du skal se etter |
|----------|-----------------|
| **Deleted Files** | Gjenopprettede filer og slettinger utført av brukeren |
| **Web History** | Besøkte nettsteder og søk |
| **Downloads** | Nedlastede filer |
| **Recent Documents** | Nylig åpnede filer |
| **USB Devices** | Tilkoblede enheter |
| **Email** | Kommunikasjon |
| **Images** | Fotografier og skjermbilder |
| **Archives** | Innhold i ZIP og RAR |

---

## 📄 Filanalyse

### Visning av filer

```
# Content Viewer (nedre panel)
- Hex        : Rå heksadesimalvisning
- Text       : Tekstinnhold
- Application: Gjengitt visning
- Strings    : Uthentede tekststrenger
- Metadata   : Filmetadata
- Results    : Analyseresultater
- Indexed Text: Søkbar tekst
```

### Filegenskaper

| Egenskap | Beskrivelse |
|----------|-------------|
| Name | Filnavn |
| Location | Fullstendig sti |
| Size | Filstørrelse |
| Created | Opprettelsestidspunkt |
| Modified | Sist endret |
| Accessed | Sist brukt |
| MD5/SHA1/SHA256 | Hashverdier |
| Known | NSRL-status |
| MIME Type | Identifisert type |

### Hente ut filer

```
# Hent ut én fil
Høyreklikk → Extract File(s)

# Hent ut flere filer
Velg filer → høyreklikk → Extract

# Hent ut til et bestemt sted
Actions → Extract to...
```

### Gjenoppretting av slettede filer

```
Views → Deleted Files

# Viser:
- Filer i $Recycle.Bin
- Filer merket som slettet i MFT
- Filer hentet ut med carving (PhotoRec)
```

---

## 📊 Tidslinjeanalyse

### Tidslinjevisning

```
# Åpne tidslinjen
Tools → Timeline

# Eller
Results → Timeline
```

### Tidslinjefunksjoner

| Funksjon | Beskrivelse |
|---------|-------------|
| **Counts View** | Hendelsesfrekvens over tid |
| **Details View** | Enkelthendelser |
| **List View** | Hendelsesliste som tabell |
| **Filters** | Filtrer etter type, dato osv. |

### Hendelsestyper

| Type | Farge | Beskrivelse |
|------|-------|-------------|
| File Modified | Blå | Filinnhold endret |
| File Accessed | Grønn | Fil åpnet |
| File Created | Gul | Fil opprettet |
| File Changed | Rød | Metadata endret |
| Web Activity | Lilla | Nettleserhendelser |

### Tidslinjefiltre

```
# Filtrer etter datointervall
Start Date: 2024-01-01
End Date: 2024-12-31

# Filtrer etter type
☑ File System
☑ Web Activity
☐ Registry (hvis tolket)

# Filtrer etter datakilde
☑ Disk Image 1
☐ Disk Image 2
```

### Eksport av tidslinje

```
# Eksporter tidslinjen
File → Export → Timeline (CSV/TSV)

# Bruk med eksterne verktøy
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Nøkkelordsøk

### Opprettelse av nøkkelordlister

```
# Tools → Options → Keyword Search

# Legg til en ny liste
1. Klikk på "New List"
2. Gi listen et navn
3. Legg til nøkkelord:
   - Literal (nøyaktig samsvar)
   - Regex (mønster)
   - Substring (delstreng)
```

### Vanlige søkeord

```
# Økonomi
credit card
bank account
swift
iban
bitcoin
wallet

# Påloggingsopplysninger
password
login
username
secret
key

# Sensitive opplysninger
confidential
secret
classified
internal only

# Nettverk
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Eksempler på regulære uttrykk

| Formål | Regex |
|---------|-------|
| E-post | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP-adresse | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Telefonnummer (USA) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Kredittkort | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Utføre søk

```
# Ad hoc-søk
Keyword Search (øverst til høyre) → angi søkeord → Search

# Bruk av nøkkelordlister
Høyreklikk på datakilden → Run Ingest Modules → Keyword Search
```

---

## 📝 Rapportering

### Generer rapport

```
# Generer rapport
Tools → Generate Report

# Eller
Høyreklikk på saken → Generate Report
```

### Rapporttyper

| Type | Beskrivelse |
|------|-------------|
| **HTML Report** | Webbasert, med bilder |
| **Excel Report** | Regnearkformat |
| **Text Report** | Ren tekst |
| **TSK Body File** | Tidslinjens body-fil |
| **Files - FS** | Filsystemrapport |
| **Portable Case** | Delbart utdrag |

### Alternativer for HTML-rapport

```
# Velg data som skal tas med
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Alternativer
☑ Include thumbnails
☑ Include metadata
```

### Portable Case (portabel sak)

```
# Opprett en portabel sak for deling
Generate Report → Portable Case

# Inneholder:
- Utvalgt bevismateriale
- Analyseresultater
- Kan åpnes i Autopsy
```

---

## 💻 Kommandolinjeverktøy (Sleuth Kit)

### Sleuth Kit-kommandoer

Autopsy bruker The Sleuth Kit (TSK) som bakgrunnsmotor. Følgende kommandolinjeverktøy er tilgjengelige:

### Opplysninger om avbildningen

```bash
# Hent opplysninger om avbildningen
img_stat image.E01
img_stat image.dd

# Hent filsystemopplysninger
fsstat -o 2048 image.dd
```

### Fillisting

```bash
# List filer
fls -r -o 2048 image.dd

# List slettede filer
fls -r -d -o 2048 image.dd

# Alternativer:
# -r    Rekursivt
# -d    Bare slettede filer
# -o    Forskyvning (partisjonens start)
```

### Filgjenoppretting

```bash
# Hent fil fra inode
icat -o 2048 image.dd 12345 > recovered_file

# Hent filinnhold
icat -r -o 2048 image.dd 12345 > file.txt
```

### Tidslinje

```bash
# Opprett body-fil
fls -r -m "/" -o 2048 image.dd > body.txt

# Opprett tidslinje
mactime -b body.txt -d > timeline.csv
```

### Beregning av hasher

```bash
# Beregn MD5
md5sum image.dd

# Beregn SHA256
sha256sum image.dd

# Kontroller avbildningen
sha256sum -c image.dd.sha256
```

### Vanlige TSK-verktøy

| Verktøy | Formål |
|------|---------|
| `img_stat` | Opplysninger om avbildningen |
| `mmls` | Partisjonstabell |
| `fsstat` | Filsystemopplysninger |
| `fls` | Fillisting |
| `icat` | Hent ut fil fra inode |
| `istat` | Inode-opplysninger |
| `blkls` | Hent ut uallokert plass |
| `srch_strings` | Finn tekststrenger |
| `tsk_recover` | Gjenopprett alle filer |
| `mactime` | Opprett tidslinje |

---

## 📊 Hurtigreferanse

### Arbeidsflyt for undersøkelsen

```
1. Create Case          → Opprett en ny sak med detaljer
2. Add Data Source      → Diskavbildning eller disk
3. Run Ingest Modules   → Automatisert analyse
4. Review Results       → Undersøk uthentet innhold
5. Keyword Search       → Søk etter ord
6. Tidslinjeanalyse     → Rekonstruer hendelser
7. Merk bevismateriale  → Merk viktige elementer
8. Generate Report      → Opprett sluttrapport
```

### Tastatursnarveier

| Snarvei | Handling |
|----------|--------|
| Ctrl+N | New Case (ny sak) |
| Ctrl+O | Open Case (åpne sak) |
| Ctrl+F | Finn (i tabell) |
| Ctrl+G | Generer rapport |
| F5 | Oppdater visningen |

### Vanlige plasseringer å undersøke

| Windows | Sti |
|---------|------|
| Nylig brukte filer | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Nedlastinger | `Users\*\Downloads` |
| Nettleserhistorikk | `Users\*\AppData\Local\[Browser]\User Data` |
| Midlertidige filer | `Users\*\AppData\Local\Temp` |
| Papirkurv | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Hendelseslogger | `Windows\System32\winevt\Logs` |

---

## 📚 Ressurser

- [Autopsys offisielle nettsted](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Autopsy-dokumentasjon](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

### Relaterte hurtigreferanser
- [Volatility](../../Volatility/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)
- [Wireshark](../../Wireshark/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Lær digital etterforskning!</b><br>
  <i>Autopsy – en nyttig medspiller i digital etterforskning</i>
</p>
