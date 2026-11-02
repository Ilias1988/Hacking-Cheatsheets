# 🧠 Volatility – Cheatsheet zur Arbeitsspeicherforensik

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Framework zur Arbeitsspeicherforensik
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Volatility?](#-was-ist-volatility)
- [Installation](#-installation)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Speicherabbild identifizieren](#-speicherabbild-identifizieren)
- [Prozessanalyse](#-prozessanalyse)
- [Netzwerkanalyse](#-netzwerkanalyse)
- [Registry-Analyse](#-registry-analyse)
- [Malware-Analyse](#-malware-analyse)
- [Benutzer und Zugangsdaten analysieren](#-benutzer-und-zugangsdaten-analysieren)
- [Datei- und DLL-Analyse](#-datei--und-dll-analyse)
- [Zeitachsenanalyse](#-zeitachsenanalyse)
- [Volatility 3](#-volatility-3)

---

## 🎯 Was ist Volatility?

**Volatility** ist ein quelloffenes Framework zur Arbeitsspeicherforensik, mit dem sich RAM-Abbilder untersuchen lassen. Es unterstützt insbesondere:

- 🔍 **Incident Response** (Reaktion auf Sicherheitsvorfälle) – Aktive Schadsoftware erkennen
- 🦠 **Malware-Analyse** – Versteckte Prozesse extrahieren
- 🔐 **Wiederherstellung von Zugangsdaten** – Passwörter und Hashes extrahieren
- 📊 **Zeitachsenanalyse** – Ereignisse rekonstruieren
- 🕵️ **Threat Hunting** (gezielte Bedrohungssuche) – Kompromittierungsindikatoren finden

### Unterstützte Speicherformate

| Format | Beschreibung |
|--------|-------------|
| Raw (.raw, .mem) | Rohes Speicherabbild |
| EWF (.E01) | EnCase-Format |
| AFF | Advanced Forensics Format |
| Crash dump | Windows-Absturzabbilder |
| VMware (.vmem) | VMware-Snapshots |
| VirtualBox (.sav) | VirtualBox-Snapshots |
| HPAK (.hpak) | FDPro-Format |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Installation

### Volatility 2 (ältere Version)

```bash
# Repository klonen
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Abhängigkeiten installieren
pip install pycrypto distorm3 yara-python pillow openpyxl

# Starten
python vol.py -h
```

### Volatility 3 (aktuelle Generation)

```bash
# Über pip installieren
pip3 install volatility3

# Alternativ das Repository klonen
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Starten
python3 vol.py -h
vol -h  # Bei Installation über pip
```

### Symboltabellen herunterladen (Vol3)

```bash
# Windows-Symbole
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Linux-Symbole
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Mac-Symbole
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Nach volatility3/symbols/ entpacken
```

---

## 💻 Grundlegende Verwendung

### Syntax von Volatility 2

```bash
# Allgemeine Syntax
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Beispiel
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Syntax von Volatility 3

```bash
# Allgemeine Syntax (Profil wird automatisch erkannt)
vol -f <memory_dump> <plugin>

# Beispiel
vol -f memory.raw windows.pslist
```

### Häufige Optionen

```bash
# Volatility 2
-f FILE          # Datei mit dem Speicherabbild
--profile=PROFILE # Betriebssystemprofil
-h               # Hilfe
--info           # Verfügbare Plugins auflisten

# Volatility 3
-f FILE          # Datei mit dem Speicherabbild
-o DIR           # Ausgabeverzeichnis
-r RENDERER      # Ausgabeformat (pretty, json, csv)
-p PLUGIN_DIR    # Zusätzliche Plugins
-h               # Hilfe
```

---

## 🔍 Speicherabbild identifizieren

### Profil bestimmen (Vol2)

```bash
# Vorgeschlagene Profile ermitteln
python vol.py -f memory.raw imageinfo

# Ausführlichere Informationen
python vol.py -f memory.raw kdbgscan
```

### Betriebssystem bestimmen (Vol3)

```bash
# Automatische Erkennung (Vol3 benötigt kein manuell gewähltes Profil)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Häufige Profile (Vol2)

| Betriebssystem | Profil |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a name="-prozessanalyse"></a>

## ⚙️ Prozessanalyse

### Prozesse auflisten

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Baumansicht
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Versteckte Prozesse

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Prozessdetails

```bash
# Befehlszeilenargumente
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Umgebungsvariablen
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Prozess-Handles
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# Geladene DLLs
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Versteckte Prozesse erkennen

```bash
# pslist und psscan vergleichen
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Process Hollowing (Austausch von Prozesscode) erkennen
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Prozessdaten exportieren

```bash
# Ausführbare Datei exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Prozessspeicher exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Netzwerkanalyse

### Netzwerkverbindungen

```bash
# Aktive Verbindungen (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Ausgabefelder

| Feld | Beschreibung |
|-------|-------------|
| Offset | Position im Speicher |
| Proto | TCP/UDP |
| LocalAddr | Lokale IP-Adresse:Port |
| ForeignAddr | Entfernte IP-Adresse:Port |
| State | Verbindungszustand |
| PID | Prozess-ID |
| Owner | Prozessname |

### Netzwerkartefakte

```bash
# Sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Registry-Analyse

### Registry-Hives auflisten

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Registry-Daten exportieren

```bash
# Bestimmten Schlüssel anzeigen
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Hive in eine Datei exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Wichtige Registry-Schlüssel

```bash
# Autostart-Schlüssel
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Dienste
printkey -K "SYSTEM\CurrentControlSet\Services"

# Benutzerkonten
printkey -K "SAM\Domains\Account\Users"

# Zuletzt verwendete Dateien
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (ausgeführte Programme)

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

## 🦠 Malware-Analyse

### Malfind (eingeschleuster Code)

```bash
# Eingeschleusten Code finden
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# In Dateien exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### SSDT-Hooks

```bash
# System Service Descriptor Table (Systemdiensttabelle)
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Mit einem unveränderten System vergleichen
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### IDT-/GDT-Analyse

```bash
# Interrupt Descriptor Table (Interrupttabelle)
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Global Descriptor Table (globale Deskriptortabelle)
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Treiberanalyse

```bash
# Treiber auflisten
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Modulliste
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Entladene Module
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# IRP-Hooks von Treibern
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### YARA-Prüfung

```bash
# Mit YARA-Regeln prüfen
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### API-Hooks

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 Benutzer und Zugangsdaten analysieren

### Benutzerinformationen

```bash
# Benutzer-SIDs ermitteln
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Zuletzt angemeldeter Benutzer
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Passworthashes

```bash
# SAM-Hashes exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# LSA-Secrets
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Zwischengespeicherte Zugangsdaten
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Mimikatz-Integration

```bash
# Zugangsdaten extrahieren (mimikatz-Plugin)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (integriert)
vol -f memory.raw windows.lsadump
```

### Zwischenablage

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Datei- und DLL-Analyse

### Dateisuche

```bash
# Dateien im Speicher finden
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Bestimmte Dateien herausfiltern
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Dateien exportieren

```bash
# Datei anhand ihres Offsets exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Alle Dateien exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### DLL-Analyse

```bash
# DLLs auflisten
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Bestimmter Prozess
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# DLL exportieren
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Zeichenketten im Speicher

```bash
# Zeichenketten extrahieren
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Prozessbezogene Zeichenketten
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

## 📅 Zeitachsenanalyse

### Zeitachse erstellen

```bash
# Zeitachse erstellen
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### MFT-Analyse

```bash
# Master File Table (zentrale NTFS-Dateitabelle)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Wesentliche Unterschiede

| Merkmal | Vol2 | Vol3 |
|---------|------|------|
| Profil | Manuell (`--profile=`) | Automatische Erkennung |
| Plugin-Namen | `pslist` | `windows.pslist` |
| Leistung | Langsamer | Schneller |
| Python | Python 2.7 | Python 3 |
| Ausgabe | Nur Text | JSON, CSV usw. |

### Plugin-Referenz für Vol3

```bash
# Prozesse
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Netzwerk
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registry
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Schadsoftware
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Zugangsdaten
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Dateien
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Linux-Plugins (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Schnellübersicht

### Häufig verwendete Befehle

| Zweck | Vol2-Befehl | Vol3-Befehl |
|---------|--------------|--------------|
| Speicherabbild identifizieren | `imageinfo` | `windows.info` |
| Prozesse auflisten | `pslist` | `windows.pslist` |
| Prozessbaum | `pstree` | `windows.pstree` |
| Versteckte Prozesse | `psscan` | `windows.psscan` |
| Netzwerk | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Prozessdaten exportieren | `procdump` | `windows.pslist --dump` |
| Schadsoftware finden | `malfind` | `windows.malfind` |
| Registry | `hivelist` | `windows.registry.hivelist` |
| Hashes | `hashdump` | `windows.hashdump` |
| Dateien | `filescan` | `windows.filescan` |
| Zeichenketten | `strings` | - |

### Untersuchungsablauf

```
1. imageinfo/windows.info    → Betriebssystem bestimmen
2. pslist/pstree/psscan      → Prozesse auflisten
3. cmdline                    → Befehlszeilen prüfen
4. netscan                    → Netzwerkverbindungen
5. malfind                    → Eingeschleusten Code finden
6. dlllist                    → Geladene DLLs prüfen
7. filescan                   → Interessante Dateien finden
8. hashdump                   → Zugangsdaten extrahieren
9. timeliner                  → Zeitachse erstellen
```

---

## 📚 Ressourcen

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatility Wiki](https://github.com/volatilityfoundation/volatility/wiki)
- [Cheatsheet zur Arbeitsspeicherforensik](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Verwandte Cheatsheets
- [Mimikatz](../../Mimikatz/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)
- [Windows-Privilegienausweitung](../../Windows-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Symboltabellen:** Die Befehle sind versionsgebundene Vorlagen. Volatility 2 benötigt seine ältere Python-Umgebung; die aktuelle Vol3-Anleitung beschreibt andere Installationsschritte als die historische requirements-Datei. Vol3 verwendet Symboltabellen statt der alten manuell gewählten Profile. Die ZIP-Pakete können unverändert im Symbolverzeichnis liegen; Entpacken ist nicht zwingend erforderlich. Für Linux und macOS sind passende Kernel-Symbole nötig; bei Linux muss der Banner exakt passen. Ein allgemeines Paket deckt nicht jede Kernel-Konfiguration ab. [Vol3: Installation und Symbolpakete](https://github.com/volatilityfoundation/volatility3/blob/develop/README.md), [Vol3: Symboltabellen](https://volatility3.readthedocs.io/en/latest/symbol-tables.html).
- **Plugins und Datenformate:** Die Formattabelle ist keine Zusage, dass jede Datei mit diesen Endungen in jeder Version direkt lesbar ist. Unterstützte Layer, optionale Bibliotheken und das konkrete Abbild sind entscheidend. linux.info und mac.info aus der Vorlage stehen nicht in den aktuellen Plugin-Verzeichnissen. Auch Namen ändern sich: Der ältere windows.malfind-Alias ist als veraltet dokumentiert, während das Plugin inzwischen im Namespace windows.malware liegt. hollowfind ist beispielsweise ein separates Community-Plugin. Zusätzliche Plugins müssen gesondert verfügbar sein; die Beispielnamen sind keine Garantie für eine Standardinstallation. [Vol3: Linux-Plugins](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.linux.html), [Vol3: Mac-Plugins](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.mac.html), [Vol3: alter Malfind-Alias](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.malfind.html), [Community-Plugins](https://github.com/volatilityfoundation/community), [HollowFind: Originalprojekt](https://github.com/monnappa22/HollowFind).
- **YARA und Zeichenketten:** In Vol2 steht -Y für eine direkt angegebene Suchzeichenkette beziehungsweise Regelangabe, -y für eine Regeldatei. Die beiden Beispiele der Vorlage vertauschen diese Rollen. Das strings-Plugin extrahiert außerdem nicht zunächst selbst alle Zeichenketten: Es benötigt eine bereits erzeugte Datei mit Zeichenketten und ihren physischen Offsets und ordnet sie Speicherbereichen zu. Die ursprünglichen Befehle bleiben unverändert. [Vol2: YARA-Optionen im Quellcode](https://github.com/volatilityfoundation/volatility/blob/master/volatility/plugins/malware/malfind.py), [Vol2: strings](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#strings).
- **Befunde und Zugangsdaten:** Ein psscan-Treffer kann auch einen beendeten Prozess betreffen; ein Malfind-Treffer ist ein verdächtiger Speicherbereich und noch kein Malware-Beweis. lsadump liest LSA-Secrets und ist keine vollständige Mimikatz-Entsprechung. Lesbare Artefakte hängen vom Abbild und den verfügbaren Strukturen ab. Der abschließende Slogan ist deshalb keine forensische Garantie: Unvollständige Erfassung, ausgelagerte Seiten, beschädigte Daten und unpassende Symbole können Ergebnisse verfälschen. [Vol2: Prozessanalyse](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference), [Vol3: Malfind](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.malware.malfind.html), [Vol3: Lsadump](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.lsadump.html), [Volatility: Grenzen der Auswertung](https://github.com/volatilityfoundation/volatility).
- **Vergleichstabelle und Kurzformen:** Vol2 unterstützt seit Version 2.5 bei entsprechend angepassten Plugins auch strukturierte Ausgabeformate; „Nur Text“ ist daher zu pauschal. Eine generell höhere Geschwindigkeit von Vol3 lässt sich für jeden Fall ebenfalls nicht aus der Tabelle ableiten. Die alleinstehenden printkey-Zeilen sind Plugin-Argumentfragmente, keine vollständigen Shell-Aufrufe. Ausgabeformat, Architektur und Plugin-Unterstützung müssen zur jeweiligen Version passen. [Vol2: Unified Output](https://github.com/volatilityfoundation/volatility/wiki/Unified-Output), [Vol2: Aufruf und Plugin-Optionen](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage).

---

<p align="center">
  <b>🧠 Arbeitsspeicherforensik beherrschen!</b><br>
  <i>Der Speicher lügt nie – dem RAM die Wahrheit entlocken</i>
</p>
