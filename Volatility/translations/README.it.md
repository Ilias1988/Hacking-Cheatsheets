# 🧠 Volatility - Scheda di riferimento per l'analisi forense della memoria

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Framework per l’analisi forense della memoria
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

> **Nota sulle versioni:** Volatility 2 è una versione storica basata su Python 2 e il suo repository è archiviato. Volatility 3 non usa i profili di Vol2: richiede tabelle dei simboli compatibili, spesso ottenute automaticamente per Windows. Nomi, percorsi dei plugin, formati leggibili e dipendenze variano con la release; plugin come `hollowfind` e `mimikatz` possono essere componenti esterni. `windows.lsadump` non è un sostituto universale di Mimikatz. La tabella che descrive l’output Vol2 come “solo testo” è semplificata: esiste anche l’[output unificato con altri renderer](https://github.com/volatilityfoundation/volatility/wiki/Unified-Output). I comandi originali sono conservati. Consulta il [tutorial ufficiale Vol3](https://volatility3.readthedocs.io/en/latest/getting-started-windows-tutorial.html) e il [riferimento Vol2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference). Una discordanza tra elenchi di processi o un risultato di `malfind` è un indizio da correlare, non una prova automatica di malware; anche un’acquisizione può essere incompleta o alterata.

---

## 📋 Indice

- [Che cos'è Volatility](#-che-cosè-volatility)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Identificazione dell'immagine di memoria](#-identificazione-dellimmagine-di-memoria)
- [Analisi dei processi](#-analisi-dei-processi)
- [Analisi di rete](#-analisi-di-rete)
- [Analisi del registro](#-analisi-del-registro)
- [Analisi del malware](#-analisi-del-malware)
- [Analisi degli utenti e delle credenziali](#-analisi-degli-utenti-e-delle-credenziali)
- [Analisi di file e DLL](#-analisi-di-file-e-dll)
- [Analisi temporale](#-analisi-temporale)
- [Volatility 3](#-volatility-3)

---

## 🎯 Che cos'è Volatility

**Volatility** è un framework open source per l'analisi forense delle acquisizioni della RAM (memory dump). È essenziale per:

- 🔍 **Risposta agli incidenti** - Identificare malware attivo
- 🦠 **Analisi del malware** - Estrarre processi nascosti
- 🔐 **Recupero delle credenziali** - Estrarre password e hash
- 📊 **Analisi temporale** - Ricostruire gli eventi
- 🕵️ **Threat hunting** - Cercare indicatori di compromissione

### Formati delle acquisizioni di memoria supportati

| Formato | Descrizione |
|--------|-------------|
| Raw (.raw, .mem) | Acquisizione della memoria in formato grezzo |
| EWF (.E01) | Formato EnCase |
| AFF | Advanced Forensics Format (formato forense avanzato) |
| Crash dump | Acquisizioni della memoria dopo un arresto anomalo di Windows |
| VMware (.vmem) | Snapshot VMware |
| VirtualBox (.sav) | Snapshot VirtualBox |
| HPAK (.hpak) | Formato FDPro |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Installazione

### Volatility 2 (versione storica)

```bash
# Clona il repository
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Installa le dipendenze
pip install pycrypto distorm3 yara-python pillow openpyxl

# Esegui
python vol.py -h
```

### Volatility 3 (versione attuale)

```bash
# Installa tramite pip
pip3 install volatility3

# Oppure clona il repository
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Esegui
python3 vol.py -h
vol -h  # Se installato tramite pip
```

### Scarica le tabelle dei simboli (Vol3)

```bash
# Simboli Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Simboli Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Simboli Mac
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Estrai in volatility3/symbols/
```

---

## 💻 Utilizzo di base

### Sintassi di Volatility 2

```bash
# Sintassi generale
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Esempio
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Sintassi di Volatility 3

```bash
# Sintassi generale (rileva automaticamente il profilo)
vol -f <memory_dump> <plugin>

# Esempio
vol -f memory.raw windows.pslist
```

### Opzioni comuni

```bash
# Volatility 2
-f FILE          # File di acquisizione della memoria
--profile=PROFILE # Profilo del sistema operativo
-h               # Guida
--info           # Elenca i plugin disponibili

# Volatility 3
-f FILE          # File di acquisizione della memoria
-o DIR           # Directory di output
-r RENDERER      # Formato di output (pretty, json, csv)
-p PLUGIN_DIR    # Plugin aggiuntivi
-h               # Guida
```

---

## 🔍 Identificazione dell'immagine di memoria

### Identifica il profilo (Vol2)

```bash
# Ottieni i profili suggeriti
python vol.py -f memory.raw imageinfo

# Informazioni più dettagliate
python vol.py -f memory.raw kdbgscan
```

### Identifica il sistema operativo (Vol3)

```bash
# Rilevamento automatico (Vol3 non richiede un profilo manuale)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Profili comuni (Vol2)

| Sistema operativo | Profilo |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

## ⚙ Analisi dei processi

### Elenca i processi

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Vista ad albero
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Processi nascosti

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Dettagli dei processi

```bash
# Argomenti della riga di comando
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Variabili d'ambiente
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Handle dei processi
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# DLL caricate
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Individua processi nascosti

```bash
# Confronta pslist e psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Rilevamento del process hollowing (sostituzione del contenuto di un processo)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Estrai un processo

```bash
# Estrai l'eseguibile
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Estrai la memoria
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Analisi di rete

### Connessioni di rete

```bash
# Connessioni attive (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Campi di output

| Campo | Descrizione |
|-------|-------------|
| Offset | Offset nella memoria |
| Proto | TCP/UDP |
| LocalAddr | IP:Porta locali |
| ForeignAddr | IP:Porta remoti |
| State | Stato della connessione |
| PID | Identificativo del processo |
| Owner | Nome del processo |

### Artefatti di rete

```bash
# Socket
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Analisi del registro

### Elenca gli hive del registro (archivi delle chiavi)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Estrai il registro

```bash
# Mostra una chiave specifica
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Estrai l'hive in un file
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Chiavi del registro importanti

```bash
# Chiavi di avvio automatico
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Servizi
printkey -K "SYSTEM\CurrentControlSet\Services"

# Account utente
printkey -K "SAM\Domains\Account\Users"

# File recenti
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (programmi eseguiti)

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

## 🦠 Analisi del malware

### Malfind (codice iniettato)

```bash
# Trova codice iniettato
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Estrai in file
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### Hook della SSDT

```bash
# System Service Descriptor Table (tabella dei descrittori dei servizi di sistema)
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Confronta con un sistema pulito
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Analisi IDT/GDT

```bash
# Interrupt Descriptor Table (tabella dei descrittori degli interrupt)
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Global Descriptor Table (tabella globale dei descrittori)
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Analisi dei driver

```bash
# Elenca i driver
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Elenco dei moduli
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Moduli scaricati dalla memoria
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Hook IRP dei driver
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Scansione YARA

```bash
# Esamina con regole YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### Hook delle API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 Analisi degli utenti e delle credenziali

### Informazioni sugli utenti

```bash
# Ottieni i SID degli utenti
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Ultimo utente che ha effettuato l'accesso
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Hash delle password

```bash
# Estrai gli hash dal SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Segreti LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Credenziali memorizzate nella cache
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Integrazione con Mimikatz

```bash
# Estrai le credenziali (plugin mimikatz)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (integrato)
vol -f memory.raw windows.lsadump
```

### Appunti

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Analisi di file e DLL

### Ricerca dei file

```bash
# Trova file in memoria
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filtra file specifici
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Estrai i file

```bash
# Estrai un file in base all'offset
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Estrai tutti i file
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### Analisi delle DLL

```bash
# Elenca le DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Processo specifico
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Estrai una DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Stringhe nella memoria

```bash
# Estrai le stringhe
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Stringhe di un processo specifico
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

## 📅 Analisi temporale

### Creazione di una timeline

```bash
# Crea una timeline
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### Analisi della MFT

```bash
# Master File Table (tabella principale dei file)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Differenze principali

| Funzionalità | Vol2 | Vol3 |
|---------|------|------|
| Profilo | Manuale (`--profile=`) | Rilevamento automatico |
| Denominazione dei plugin | `pslist` | `windows.pslist` |
| Prestazioni | Più lento | Più veloce |
| Python | Python 2.7 | Python 3 |
| Output | Solo testo | JSON, CSV, ecc. |

### Riferimento dei plugin Vol3

```bash
# Processi
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Rete
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registro
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Malware
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Credenziali
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# File
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Plugin Linux (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Riferimento rapido

### Comandi più utilizzati

| Scopo | Comando Vol2 | Comando Vol3 |
|---------|--------------|--------------|
| Identifica l'immagine di memoria | `imageinfo` | `windows.info` |
| Elenca i processi | `pslist` | `windows.pslist` |
| Albero dei processi | `pstree` | `windows.pstree` |
| Processi nascosti | `psscan` | `windows.psscan` |
| Rete | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Estrai il processo | `procdump` | `windows.pslist --dump` |
| Trova malware | `malfind` | `windows.malfind` |
| Registro | `hivelist` | `windows.registry.hivelist` |
| Hash | `hashdump` | `windows.hashdump` |
| File | `filescan` | `windows.filescan` |
| Stringhe | `strings` | - |

### Procedura d'indagine

```
1. imageinfo/windows.info    → Identifica il sistema operativo
2. pslist/pstree/psscan      → Elenca i processi
3. cmdline                    → Controlla le righe di comando
4. netscan                    → Connessioni di rete
5. malfind                    → Trova codice iniettato
6. dlllist                    → Controlla le DLL caricate
7. filescan                   → Trova file di interesse
8. hashdump                   → Estrai le credenziali
9. timeliner                  → Crea una timeline
```

---

## 📚 Risorse

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 su GitHub](https://github.com/volatilityfoundation/volatility3)
- [Wiki di Volatility](https://github.com/volatilityfoundation/volatility/wiki)
- [Scheda di riferimento per l'analisi forense della memoria](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Schede correlate
- [Mimikatz](../../Mimikatz/translations/README.it.md)
- [Comandi Linux](../../Linux-Commands/translations/README.it.md)
- [Escalation dei privilegi Windows](../../Windows-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🧠 Padroneggia l'analisi forense della memoria!</b><br>
  <i>La memoria non mente: estrai la verità dalla RAM</i>
</p>
