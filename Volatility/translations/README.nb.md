# 🧠 Volatility – hurtigreferanse for minneetterforskning

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Rammeverk for minneetterforskning
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

> **Presisering om versjoner og funn:** Eksemplene blander Volatility 2 og 3. Volatility 3 bruker symboltabeller, ikke Volatility 2-profiler; automatisk identifikasjon forutsetter passende symboler. Tilleggsnavn og valg kan variere mellom utgaver, og enkelte eksempler krever eksterne tillegg. Sammenligningstabellen er en forenkling: Volatility 2 har også andre utdataformater enn ren tekst. `windows.lsadump` er ikke en full erstatning for Mimikatz. Se [offisiell migreringsveiledning](https://volatility3.readthedocs.io/en/latest/vol2to3.html). I YARA-eksemplene er fil- og strengvalgene i originalen byttet om: dokumentasjonen skiller mellom `--yara-file` for en regelfil og `--yara-rules` for regeltekst. Kommandoene er bevart; se [YARA-referansen](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference-Mal#yarascan). Et mistenkelig prosessnavn, en nettverksforbindelse eller et `malfind`-funn er en indikator som må undersøkes videre, ikke alene bevis på skadevare.


## 📋 Innholdsfortegnelse

- [Hva er Volatility](#-hva-er-volatility)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Identifikasjon av minneavbildning](#-identifikasjon-av-minneavbildning)
- [Prosessanalyse](#-prosessanalyse)
- [Nettverksanalyse](#-nettverksanalyse)
- [Analyse av Windows-registeret](#-analyse-av-windows-registeret)
- [Skadevareanalyse](#-skadevareanalyse)
- [Analyse av brukere og påloggingsopplysninger](#-analyse-av-brukere-og-påloggingsopplysninger)
- [Analyse av filer og DLL-er](#-analyse-av-filer-og-dll-er)
- [Tidslinjeanalyse](#-tidslinjeanalyse)
- [Volatility 3](#-volatility-3)

---

## 🎯 Hva er Volatility

**Volatility** er et rammeverk med åpen kildekode for analyse av RAM-avbildninger. Det brukes blant annet til:

- 🔍 **Hendelseshåndtering** – identifiser aktiv skadevare
- 🦠 **Skadevareanalyse** – hent ut skjulte prosesser
- 🔐 **Gjenoppretting av påloggingsopplysninger** – hent ut passord og hasher
- 📊 **Tidslinjeanalyse** – rekonstruer hendelser
- 🕵️ **Trusseljakt** – finn kompromitteringsindikatorer

### Støttede minneformater

| Format | Beskrivelse |
|--------|-------------|
| Raw (.raw, .mem) | Rå minneavbildning |
| EWF (.E01) | EnCase-format |
| AFF | Advanced Forensics Format |
| Krasjavbildning | Krasjavbildninger fra Windows |
| VMware (.vmem) | Øyeblikksbilder fra VMware |
| VirtualBox (.sav) | Øyeblikksbilder fra VirtualBox |
| HPAK (.hpak) | FDPro-format |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Installasjon

### Volatility 2 (eldre versjon)

```bash
# Klon repoet
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Installer avhengigheter
pip install pycrypto distorm3 yara-python pillow openpyxl

# Kjør
python vol.py -h
```

### Volatility 3 (nåværende hovedversjon)

```bash
# Installer med pip
pip3 install volatility3

# Eller klon repoet
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Kjør
python3 vol.py -h
vol -h  # Hvis installert med pip
```

### Last ned symboltabeller (Vol3)

```bash
# Windows-symboler
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Linux-symboler
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Mac-symboler
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Pakk ut til volatility3/symbols/
```

---

## 💻 Grunnleggende bruk

### Syntaks i Volatility 2

```bash
# Generell syntaks
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Eksempel
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Syntaks i Volatility 3

```bash
# Generell syntaks (automatisk identifikasjon; se presiseringen)
vol -f <memory_dump> <plugin>

# Eksempel
vol -f memory.raw windows.pslist
```

### Vanlige alternativer

```bash
# Volatility 2
-f FILE          # Fil med minneavbildning
--profile=PROFILE # Operativsystemprofil
-h               # Hjelp
--info           # List tilgjengelige programtillegg

# Volatility 3
-f FILE          # Fil med minneavbildning
-o DIR           # Utdatamappe
-r RENDERER      # Utdataformat (pretty, json, csv)
-p PLUGIN_DIR    # Ekstra programtillegg
-h               # Hjelp
```

---

## 🔍 Identifikasjon av minneavbildning

### Identifiser profil (Vol2)

```bash
# Hent forslag til profiler
python vol.py -f memory.raw imageinfo

# Mer detaljerte opplysninger
python vol.py -f memory.raw kdbgscan
```

### Identifiser operativsystem (Vol3)

```bash
# Automatisk identifikasjon (Vol3 bruker ikke manuell profil)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Vanlige profiler (Vol2)

| Operativsystem | Profil |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-prosessanalyse"></a>
## ⚙️ Prosessanalyse

### List prosesser

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Trevisning
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Skjulte prosesser

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Prosessdetaljer

```bash
# Kommandolinjeargumenter
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Miljøvariabler
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Prosesshåndtak
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# Innlastede DLL-er
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Finn skjulte prosesser

```bash
# Sammenlign pslist og psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Gjenkjenning av process hollowing
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Hent ut prosessdata

```bash
# Hent ut kjørbar fil
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Hent ut minne
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Nettverksanalyse

### Nettverksforbindelser

```bash
# Aktive forbindelser (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Utdatafelter

| Felt | Beskrivelse |
|-------|-------------|
| Offset | Posisjon i minnet |
| Proto | TCP/UDP |
| LocalAddr | Lokal IP:port |
| ForeignAddr | Ekstern IP:port |
| State | Forbindelsestilstand |
| PID | Prosess-ID |
| Owner | Prosessnavn |

### Nettverksartefakter

```bash
# Nettverkssokler
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Analyse av Windows-registeret

### List registerdatabaser (hives)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Hent ut registeret

```bash
# Vis en bestemt registernøkkel
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Lagre registerdatabase i fil
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Viktige registernøkler

```bash
# Nøkler for automatisk oppstart
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Tjenester
printkey -K "SYSTEM\CurrentControlSet\Services"

# Brukerkontoer
printkey -K "SAM\Domains\Account\Users"

# Nylig brukte filer
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (kjørte programmer)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

## 🦠 Skadevareanalyse

### Malfind (injisert kode)

```bash
# Finn injisert kode
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Lagre i filer
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### SSDT-hooks

```bash
# System Service Descriptor Table (tabell over systemtjenester)
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Sammenlign med et rent system
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Analyse av IDT/GDT

```bash
# Interrupt Descriptor Table (tabell over avbruddsdeskriptorer)
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Global Descriptor Table (global deskriptortabell)
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Driveranalyse

```bash
# List drivere
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Modulliste
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Avlastede moduler
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# IRP-hooks i drivere
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### YARA-skanning

```bash
# Skann med YARA-regler
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### API-hooks

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 Analyse av brukere og påloggingsopplysninger

### Brukeropplysninger

```bash
# Hent brukernes SID-er
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Sist påloggede bruker
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Passordhasher

```bash
# Hent ut SAM-hasher
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# LSA-hemmeligheter
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Mellomlagrede påloggingsopplysninger
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Integrasjon med Mimikatz

```bash
# Hent ut påloggingsopplysninger (mimikatz-tillegg)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (innebygd)
vol -f memory.raw windows.lsadump
```

### Utklippstavle

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Analyse av filer og DLL-er

### Filsøk

```bash
# Finn filer i minnet
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filtrer etter bestemte filer
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Hent ut filer

```bash
# Hent ut fil fra angitt posisjon
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Hent ut alle filer
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### DLL-analyse

```bash
# List DLL-er
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Bestemt prosess
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Hent ut DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Tekststrenger i minnet

```bash
# Hent ut tekststrenger
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Prosesspesifikke tekststrenger
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

## 📅 Tidslinjeanalyse

### Opprettelse av tidslinje

```bash
# Opprett tidslinje
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### MFT-analyse

```bash
# Master File Table (MFT, hovedfiltabell)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Viktige forskjeller

| Funksjon | Vol2 | Vol3 |
|---------|------|------|
| Profil | Manuell (`--profile=`) | Automatisk identifikasjon |
| Navngivning av programtillegg | `pslist` | `windows.pslist` |
| Ytelse | Langsommere | Raskere |
| Python | Python 2.7 | Python 3 |
| Utdata | Bare tekst | JSON, CSV osv. |

### Oversikt over Vol3-tillegg

```bash
# Prosess
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Nettverk
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Windows-registeret
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Skadevare
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Påloggingsopplysninger
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Filer
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Linux-tillegg (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Hurtigreferanse

### Mest brukte kommandoer

| Formål | Vol2-kommando | Vol3-kommando |
|---------|--------------|--------------|
| Identifiser avbildningen | `imageinfo` | `windows.info` |
| List prosesser | `pslist` | `windows.pslist` |
| Prosesstre | `pstree` | `windows.pstree` |
| Skjulte prosesser | `psscan` | `windows.psscan` |
| Nettverk | `netscan` | `windows.netscan` |
| DLL-er | `dlllist` | `windows.dlllist` |
| Hent ut prosessdata | `procdump` | `windows.pslist --dump` |
| Finn skadevare | `malfind` | `windows.malfind` |
| Windows-registeret | `hivelist` | `windows.registry.hivelist` |
| Hasher | `hashdump` | `windows.hashdump` |
| Filer | `filescan` | `windows.filescan` |
| Tekststrenger | `strings` | - |

### Arbeidsflyt for undersøkelsen

```
1. imageinfo/windows.info    → Identifiser operativsystemet
2. pslist/pstree/psscan      → List prosesser
3. cmdline                    → Undersøk kommandolinjer
4. netscan                    → Nettverksforbindelser
5. malfind                    → Finn injisert kode
6. dlllist                    → Undersøk innlastede DLL-er
7. filescan                   → Finn interessante filer
8. hashdump                   → Hent ut påloggingsopplysninger
9. timeliner                  → Opprett tidslinje
```

---

## 📚 Ressurser

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility 3 på GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatilitys wiki](https://github.com/volatilityfoundation/volatility/wiki)
- [Hurtigreferanse for minneetterforskning](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Relaterte hurtigreferanser
- [Mimikatz](../../Mimikatz/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🧠 Lær minneetterforskning!</b><br>
  <i>Undersøk sporene i RAM og vurder dem i sammenheng</i>
</p>
