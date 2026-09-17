# 🔨 John the Ripper - Complete Cheatsheet

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  The Password Cracker     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 The legendary password security auditing and recovery tool</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Cracking Modes](#-cracking-modes)
- [Hash Extraction](#-hash-extraction)
- [Common Hash Types](#-common-hash-types)
- [Linux Password Cracking](#-linux-password-cracking)
- [Windows Password Cracking](#-windows-password-cracking)
- [File Password Cracking](#-file-password-cracking)
- [Rules & Mangling](#-rules--mangling)
- [Session Management](#-session-management)
- [Performance Tuning](#-performance-tuning)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**John the Ripper** (John/JtR) is a free, open-source password security auditing and recovery tool. It's designed to detect weak passwords and crack password hashes.

### Key Features

| Feature | Description |
|---------|-------------|
| **Multi-Format** | Supports hundreds of hash types |
| **Multiple Modes** | Single, Wordlist, Incremental |
| **Custom Rules** | Powerful word mangling rules |
| **Auto-Detect** | Automatic hash format detection |
| **Cross-Platform** | Linux, Windows, macOS |
| **GPU Support** | OpenCL acceleration (Jumbo) |

### John vs Hashcat

| Feature | John | Hashcat |
|---------|------|---------|
| **Best For** | CPU cracking | GPU cracking |
| **Ease of Use** | Easier | More complex |
| **Auto-Detect** | ✅ Yes | ❌ No |
| **Rules** | Powerful | Very powerful |
| **Hash Types** | Many | More |
| **Speed (GPU)** | Good | Better |

### Versions

| Version | Description |
|---------|-------------|
| **John (Core)** | Basic version |
| **John Jumbo** | Community enhanced version (recommended) |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Standard version
sudo apt install john

# Jumbo version (recommended)
sudo apt install john-jumbo
```

### From Source (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Download from official site
https://www.openwall.com/john/

# Or use Chocolatey
choco install john
```

### Verify Installation
```bash
john --version
john --list=formats | wc -l    # Count supported formats
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
john [options] [password_file]
```

### Common Options

| Option | Description |
|--------|-------------|
| `--wordlist=` | Path to wordlist |
| `--format=` | Specify hash format |
| `--rules` | Enable word mangling rules |
| `--show` | Show cracked passwords |
| `--single` | Single crack mode |
| `--incremental` | Brute force mode |
| `--session=` | Name the session |
| `--restore` | Restore previous session |
| `--pot=` | Custom pot file |

### Quick Start
```bash
# Auto-detect and crack
john hashes.txt

# With wordlist
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Show cracked passwords
john --show hashes.txt
```

---

## 🎮 Cracking Modes

### Single Crack Mode

Uses login names and GECOS information to generate password guesses.

```bash
# Single mode (fastest, tries username variations)
john --single hashes.txt

# With format specification
john --single --format=sha512crypt hashes.txt
```

### Wordlist Mode

Uses dictionary words with optional rules.

```bash
# Basic wordlist attack
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Wordlist with rules
john --wordlist=wordlist.txt --rules hashes.txt

# Specific ruleset
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Incremental Mode (Brute Force)

Tries all possible character combinations.

```bash
# Default incremental (all characters)
john --incremental hashes.txt

# Specific charset
john --incremental=Digits hashes.txt      # Numbers only
john --incremental=Lower hashes.txt       # Lowercase only
john --incremental=Upper hashes.txt       # Uppercase only
john --incremental=Alpha hashes.txt       # Letters only
john --incremental=Alnum hashes.txt       # Alphanumeric
john --incremental=LowerNum hashes.txt    # Lowercase + numbers
```

### Mask Mode

Pattern-based brute force.

```bash
# Mask attack (? = placeholders)
john --mask='?l?l?l?l?d?d' hashes.txt

# Placeholders:
# ?l = lowercase    ?u = uppercase
# ?d = digits       ?s = special
# ?a = all          ?h = hex lowercase
```

### External Mode

Custom cracking algorithms.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Hash Extraction

### Linux Passwords

```bash
# Combine passwd and shadow files
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Crack the combined file
john unshadowed.txt
```

### Windows Hashes

```bash
# From SAM file (use pwdump, secretsdump, etc.)
# Format: user:id:LM_hash:NTLM_hash:::

# Using Metasploit hashdump
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Save to file and crack
john --format=nt hashes.txt
```

### File Passwords (2john tools)

```bash
# ZIP files
zip2john protected.zip > zip_hash.txt

# RAR files
rar2john protected.rar > rar_hash.txt

# PDF files
pdf2john protected.pdf > pdf_hash.txt

# Office documents
office2john document.docx > office_hash.txt

# SSH keys
ssh2john id_rsa > ssh_hash.txt

# 7zip files
7z2john archive.7z > 7z_hash.txt

# KeePass databases
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Common Hash Types

### Hash Format Table

| Hash Type | Format Flag | Example |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### List All Formats
```bash
# List all supported formats
john --list=formats

# Search for specific format
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Count formats
john --list=formats | wc -l
```

### Identify Hash Type
```bash
# Use hash-identifier
hash-identifier

# Or hashid
hashid -m '$6$salt$hash...'
```

---

## 🐧 Linux Password Cracking

### Prepare Hashes
```bash
# Combine passwd and shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Or extract just the hash
sudo cat /etc/shadow | grep username
```

### Hash Format Examples
```
# /etc/shadow format:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (most common modern Linux)
# $y$ = yescrypt (newest)
```

### Crack Linux Hashes
```bash
# Auto-detect (recommended)
john linux_hashes.txt

# Specify format
john --format=sha512crypt linux_hashes.txt

# With wordlist
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Show results
john --show linux_hashes.txt
```

---

## 🪟 Windows Password Cracking

### Hash Types

| Type | Description | Format |
|------|-------------|--------|
| **LM** | Legacy (weak) | `--format=lm` |
| **NTLM** | Modern Windows | `--format=nt` |
| **NTLMv2** | Network auth | `--format=netntlmv2` |

### Crack NTLM Hashes
```bash
# NTLM hash format:
# user:rid:lm_hash:ntlm_hash:::

# Crack NTLM
john --format=nt windows_hashes.txt

# With wordlist
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# With rules
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Extract Windows Hashes
```bash
# Using Metasploit
meterpreter> hashdump

# Using secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Using pwdump
pwdump7.exe
```

---

## 📄 File Password Cracking

### ZIP Files
```bash
# Extract hash
zip2john protected.zip > zip.hash

# Crack
john --wordlist=rockyou.txt zip.hash

# Show password
john --show zip.hash
```

### RAR Files
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### PDF Files
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Office Documents
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### SSH Private Keys
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### KeePass Database
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

## ⚙️ Rules & Mangling

### Enable Rules
```bash
# Default rules
john --wordlist=wordlist.txt --rules hashes.txt

# Specific ruleset
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### List Available Rules
```bash
john --list=rules
```

### Common Rule Operations

| Rule | Description | Example |
|------|-------------|---------|
| `:` | Do nothing | password → password |
| `l` | Lowercase all | PassWord → password |
| `u` | Uppercase all | password → PASSWORD |
| `c` | Capitalize | password → Password |
| `r` | Reverse | password → drowssap |
| `d` | Duplicate | pass → passpass |
| `$X` | Append char X | pass → passX |
| `^X` | Prepend char X | pass → Xpass |
| `sXY` | Replace X with Y | password → pa$$word |

### Custom Rules
```bash
# Add to john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Session Management

### Create Named Session
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Restore Session
```bash
# Restore interrupted session
john --restore=mycrack

# Restore default session
john --restore
```

### Check Status
```bash
# Press any key during cracking to see status
# Or check .rec file
cat ~/.john/mycrack.rec
```

### Pot File
```bash
# Default pot file
~/.john/john.pot

# Custom pot file
john --pot=custom.pot hashes.txt

# Show all cracked passwords
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Performance Tuning

### Fork (Multi-Process)
```bash
# Use multiple CPU cores
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# List OpenCL devices
john --list=opencl-devices

# Use GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Limit Candidates
```bash
# Limit password length
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Memory Usage
```bash
# Reduce memory usage
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Real-World Examples

### Example 1: Linux Shadow File
```bash
# Combine files
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Crack with rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Show results
john --show hashes.txt
```

### Example 2: Windows NTLM
```bash
# From hashdump output
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Crack
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Example 3: ZIP File Password
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Example 4: SSH Key Passphrase
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Example 5: MD5 Hashes
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Example 6: Web Application Hashes
```bash
# bcrypt hash
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Example 7: With Rules
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Example 8: Incremental Attack
```bash
# Try all 6-8 character lowercase+digits
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic crack | `john hashes.txt` |
| Wordlist attack | `john --wordlist=rockyou.txt hashes.txt` |
| With rules | `john --wordlist=words.txt --rules hashes.txt` |
| Show cracked | `john --show hashes.txt` |
| Specific format | `john --format=raw-md5 hashes.txt` |
| Incremental | `john --incremental hashes.txt` |
| Restore session | `john --restore=session` |
| List formats | `john --list=formats` |

### 2john Tools

| Tool | Purpose |
|------|---------|
| `unshadow` | Linux passwd/shadow |
| `zip2john` | ZIP files |
| `rar2john` | RAR files |
| `pdf2john` | PDF files |
| `office2john` | Office documents |
| `ssh2john` | SSH keys |
| `keepass2john` | KeePass databases |

### Common Formats

| Hash | Format Flag |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 Tips & Best Practices

### Efficiency Tips

1. **Try Single Mode First**
   ```bash
   john --single hashes.txt
   ```

2. **Use Targeted Wordlists**
   ```bash
   # Company name, city, etc.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Start with Small Wordlists**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Then try larger
   ```

4. **Combine Modes**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Performance Tips

```bash
# Use multiple cores
john --fork=4 hashes.txt

# GPU acceleration (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** John the Ripper is a powerful password cracking tool that should only be used for **authorized purposes**.
> 
> - ✅ Audit your own passwords
> - ✅ Test with explicit permission
> - ✅ Security assessments with authorization
> - ❌ Never crack passwords without permission
> - ❌ Unauthorized access is illegal
> 
> **Password cracking without authorization is illegal and may result in criminal charges.**

---

## 📚 Resources

### Official Resources
- [John the Ripper Official](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John Wiki](https://openwall.info/wiki/john)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Related Cheatsheets
- [Hydra](../Hydra/README.md)
- [Metasploit](../Metasploit/README.md)
- [Nmap](../Nmap/README.md)

---

<p align="center">
  <b>🔨 Master Password Cracking!</b><br>
  <i>Always crack responsibly</i>
</p>
