# 🧠 Volatility - Memory Forensics Cheatsheet

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Memory Forensics Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 Table of Contents

- [What is Volatility](#-what-is-volatility)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Image Identification](#-image-identification)
- [Process Analysis](#-process-analysis)
- [Network Analysis](#-network-analysis)
- [Registry Analysis](#-registry-analysis)
- [Malware Analysis](#-malware-analysis)
- [User & Credential Analysis](#-user--credential-analysis)
- [File & DLL Analysis](#-file--dll-analysis)
- [Timeline Analysis](#-timeline-analysis)
- [Volatility 3](#-volatility-3)

---

## 🎯 What is Volatility

**Volatility** is an open-source memory forensics framework for analyzing RAM dumps. It's essential for:

- 🔍 **Incident Response** - Identify active malware
- 🦠 **Malware Analysis** - Extract hidden processes
- 🔐 **Credential Recovery** - Extract passwords/hashes
- 📊 **Timeline Analysis** - Reconstruct events
- 🕵️ **Threat Hunting** - Find indicators of compromise

### Supported Memory Formats

| Format | Description |
|--------|-------------|
| Raw (.raw, .mem) | Raw memory dump |
| EWF (.E01) | EnCase format |
| AFF | Advanced Forensics Format |
| Crash dump | Windows crash dumps |
| VMware (.vmem) | VMware snapshots |
| VirtualBox (.sav) | VirtualBox snapshots |
| HPAK (.hpak) | FDPro format |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Installation

### Volatility 2 (Legacy)

```bash
# Clone repository
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Install dependencies
pip install pycrypto distorm3 yara-python pillow openpyxl

# Run
python vol.py -h
```

### Volatility 3 (Current)

```bash
# Install via pip
pip3 install volatility3

# Or clone repository
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Run
python3 vol.py -h
vol -h  # If installed via pip
```

### Download Symbol Tables (Vol3)

```bash
# Windows symbols
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Linux symbols
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Mac symbols
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Extract to volatility3/symbols/
```

---

## 💻 Basic Usage

### Volatility 2 Syntax

```bash
# General syntax
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Example
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Volatility 3 Syntax

```bash
# General syntax (auto-detects profile)
vol -f <memory_dump> <plugin>

# Example
vol -f memory.raw windows.pslist
```

### Common Options

```bash
# Volatility 2
-f FILE          # Memory dump file
--profile=PROFILE # OS profile
-h               # Help
--info           # List available plugins

# Volatility 3
-f FILE          # Memory dump file
-o DIR           # Output directory
-r RENDERER      # Output format (pretty, json, csv)
-p PLUGIN_DIR    # Additional plugins
-h               # Help
```

---

## 🔍 Image Identification

### Identify Profile (Vol2)

```bash
# Get suggested profiles
python vol.py -f memory.raw imageinfo

# More detailed info
python vol.py -f memory.raw kdbgscan
```

### Identify OS (Vol3)

```bash
# Auto-detection (Vol3 doesn't need manual profile)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Common Profiles (Vol2)

| OS | Profile |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

## ⚙️ Process Analysis

### List Processes

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Tree view
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Hidden processes

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Process Details

```bash
# Command line arguments
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Environment variables
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Process handles
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# DLLs loaded
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Detect Hidden Processes

```bash
# Compare pslist vs psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Process hollowing detection
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Dump Process

```bash
# Dump executable
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Dump memory
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Network Analysis

### Network Connections

```bash
# Active connections (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Output Fields

| Field | Description |
|-------|-------------|
| Offset | Memory offset |
| Proto | TCP/UDP |
| LocalAddr | Local IP:Port |
| ForeignAddr | Remote IP:Port |
| State | Connection state |
| PID | Process ID |
| Owner | Process name |

### Network Artifacts

```bash
# Sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Registry Analysis

### List Registry Hives

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Dump Registry

```bash
# Print specific key
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Dump hive to file
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Important Registry Keys

```bash
# Autorun keys
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Services
printkey -K "SYSTEM\CurrentControlSet\Services"

# User accounts
printkey -K "SAM\Domains\Account\Users"

# Recent files
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (Executed Programs)

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

## 🦠 Malware Analysis

### Malfind (Injected Code)

```bash
# Find injected code
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Dump to files
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### SSDT Hooks

```bash
# System Service Descriptor Table
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Compare with clean system
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### IDT/GDT Analysis

```bash
# Interrupt Descriptor Table
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Global Descriptor Table
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Driver Analysis

```bash
# List drivers
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Module list
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Unloaded modules
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Driver IRP hooks
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### YARA Scanning

```bash
# Scan with YARA rules
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### API Hooks

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 User & Credential Analysis

### User Information

```bash
# Get user SIDs
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Last logged on user
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Password Hashes

```bash
# Dump SAM hashes
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# LSA secrets
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Cached credentials
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Mimikatz Integration

```bash
# Extract credentials (mimikatz plugin)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (built-in)
vol -f memory.raw windows.lsadump
```

### Clipboard

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 File & DLL Analysis

### File Scan

```bash
# Find files in memory
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filter specific files
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Dump Files

```bash
# Dump file by offset
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Dump all files
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### DLL Analysis

```bash
# List DLLs
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Specific process
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Dump DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Memory Strings

```bash
# Extract strings
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Process-specific strings
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

## 📅 Timeline Analysis

### Timeline Creation

```bash
# Create timeline
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### MFT Analysis

```bash
# Master File Table
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Key Differences

| Feature | Vol2 | Vol3 |
|---------|------|------|
| Profile | Manual (`--profile=`) | Auto-detect |
| Plugin naming | `pslist` | `windows.pslist` |
| Performance | Slower | Faster |
| Python | Python 2.7 | Python 3 |
| Output | Text only | JSON, CSV, etc |

### Vol3 Plugin Reference

```bash
# Process
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Network
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registry
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Malware
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Credentials
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Files
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Linux Plugins (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Quick Reference

### Most Used Commands

| Purpose | Vol2 Command | Vol3 Command |
|---------|--------------|--------------|
| Identify image | `imageinfo` | `windows.info` |
| List processes | `pslist` | `windows.pslist` |
| Process tree | `pstree` | `windows.pstree` |
| Hidden processes | `psscan` | `windows.psscan` |
| Network | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Dump process | `procdump` | `windows.pslist --dump` |
| Find malware | `malfind` | `windows.malfind` |
| Registry | `hivelist` | `windows.registry.hivelist` |
| Hashes | `hashdump` | `windows.hashdump` |
| Files | `filescan` | `windows.filescan` |
| Strings | `strings` | - |

### Investigation Workflow

```
1. imageinfo/windows.info    → Identify OS
2. pslist/pstree/psscan      → List processes
3. cmdline                    → Check command lines
4. netscan                    → Network connections
5. malfind                    → Find injected code
6. dlllist                    → Check loaded DLLs
7. filescan                   → Find interesting files
8. hashdump                   → Extract credentials
9. timeliner                  → Create timeline
```

---

## 📚 Resources

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatility Wiki](https://github.com/volatilityfoundation/volatility/wiki)
- [Memory Forensics Cheatsheet](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Related Cheatsheets
- [Mimikatz](../Mimikatz/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [Windows PrivEsc](../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🧠 Master Memory Forensics!</b><br>
  <i>Memory never lies - extract the truth from RAM</i>
</p>
