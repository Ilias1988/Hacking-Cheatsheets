# ⚡ Hashcat - Complete Cheatsheet

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    World's Fastest Password Recovery
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 The world's fastest and most advanced password recovery utility</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Attack Modes](#-attack-modes)
- [Hash Modes](#-hash-modes)
- [Mask Attack](#-mask-attack)
- [Rules](#-rules)
- [Session Management](#-session-management)
- [GPU Optimization](#-gpu-optimization)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Hashcat** is the world's fastest and most advanced password recovery utility, supporting over 350 hash types and utilizing GPU acceleration.

### Key Features

| Feature | Description |
|---------|-------------|
| **350+ Hash Types** | MD5, SHA, NTLM, bcrypt, WPA, and more |
| **GPU Accelerated** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Multi-GPU** | Distributed cracking support |
| **5 Attack Modes** | Dictionary, Brute-force, Hybrid, etc. |
| **Powerful Rules** | Advanced word mangling |
| **Cross-Platform** | Linux, Windows, macOS |

### Hashcat vs John the Ripper

| Feature | Hashcat | John |
|---------|---------|------|
| **Speed** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **GPU Support** | Excellent | Good |
| **Hash Types** | 350+ | 300+ |
| **Auto-Detect** | ❌ No | ✅ Yes |
| **Ease of Use** | Moderate | Easier |
| **Best For** | GPU cracking | CPU cracking |

---

## 📥 Installation

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# With OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Download from official site
https://hashcat.net/hashcat/

# Extract and run from folder
hashcat.exe -h
```

### From Source
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Verify Installation
```bash
hashcat --version
hashcat -I                    # Show OpenCL devices
hashcat -b                    # Run benchmark
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-m` | Hash type (mode) |
| `-a` | Attack mode |
| `-o` | Output file |
| `-r` | Rules file |
| `--show` | Show cracked passwords |
| `--status` | Enable status screen |
| `-w` | Workload profile (1-4) |
| `--force` | Ignore warnings |
| `-O` | Optimized kernels |

### Quick Start
```bash
# Basic dictionary attack
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Show cracked hashes
hashcat -m 0 hash.txt --show

# Check example hashes
hashcat -m 0 --example-hashes
```

---

## 🎮 Attack Modes

### Overview

| Mode | Name | Description |
|------|------|-------------|
| `-a 0` | Dictionary | Wordlist attack |
| `-a 1` | Combination | Combine two wordlists |
| `-a 3` | Brute-Force | Mask-based attack |
| `-a 6` | Hybrid Dict+Mask | Wordlist + mask |
| `-a 7` | Hybrid Mask+Dict | Mask + wordlist |
| `-a 9` | Association | Word associations |

### Dictionary Attack (-a 0)

```bash
# Basic dictionary attack
hashcat -m 0 -a 0 hash.txt wordlist.txt

# With rules
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Multiple wordlists
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Combination Attack (-a 1)

Combines words from two wordlists.

```bash
# Combine two wordlists
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# word1word2 combinations
```

### Brute-Force/Mask Attack (-a 3)

```bash
# All lowercase, 6 characters
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# All digits, 4-8 characters
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Custom charset
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Hybrid Attacks (-a 6, -a 7)

```bash
# Dictionary + Mask (append)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Mask + Dictionary (prepend)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Hash Modes

### Most Common Hash Modes

| Mode | Hash Type | Example |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Complex format |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Web Applications

| Mode | Hash Type |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Databases

| Mode | Hash Type |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Documents & Archives

| Mode | Hash Type |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Cryptocurrency

| Mode | Hash Type |
|------|-----------|
| 11300 | Bitcoin wallet |
| 12700 | Blockchain wallet |
| 16600 | Electrum wallet |
| 15200 | Ethereum Keystore |

### List/Search Modes
```bash
# List all hash modes
hashcat --help | grep -i "hash modes"

# Search for specific hash
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Show example hash format
hashcat -m 1000 --example-hashes
```

---

## 🎭 Mask Attack

### Charset Placeholders

| Placeholder | Characters |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Custom Charsets

```bash
# Define custom charset
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Multiple custom charsets
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Special characters
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Increment Mode

```bash
# Try lengths 1-8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Specify min/max
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Common Mask Patterns

```bash
# 8 lowercase letters
?l?l?l?l?l?l?l?l

# Password + 4 digits
password?d?d?d?d

# Capital + lowercase + digits
?u?l?l?l?l?l?d?d

# Winter2024 pattern
?u?l?l?l?l?l?d?d?d?d

# Phone number
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Rules

### Built-in Rule Files

```bash
# Location
ls /usr/share/hashcat/rules/

# Popular rules
best64.rule           # Best 64 rules
rockyou-30000.rule    # Top 30000 rules
d3ad0ne.rule          # D3ad0ne rules
dive.rule             # Dive rules
generated.rule        # Generated rules
```

### Using Rules

```bash
# Single rule file
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Multiple rule files
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Generate rules
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Common Rule Operations

| Rule | Description | Example |
|------|-------------|---------|
| `:` | Do nothing | password |
| `l` | Lowercase all | PASSWORD → password |
| `u` | Uppercase all | password → PASSWORD |
| `c` | Capitalize | password → Password |
| `C` | Lowercase first, rest upper | password → pASSWORD |
| `t` | Toggle case | password → PASSWORD |
| `r` | Reverse | password → drowssap |
| `d` | Duplicate | pass → passpass |
| `$X` | Append X | pass → passX |
| `^X` | Prepend X | pass → Xpass |
| `sXY` | Replace X with Y | password → pa$$word |

---

## 💾 Session Management

### Create Session
```bash
# Named session
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Session files stored in ~/.hashcat/sessions/
```

### Pause & Resume
```bash
# Pause: Press 'p' during cracking
# Resume: Press 'r' during cracking

# Or restore from command line
hashcat --session=mysession --restore
```

### Status Commands

During cracking:
- `s` - Show status
- `p` - Pause
- `r` - Resume
- `b` - Bypass (skip current attack)
- `c` - Checkpoint
- `q` - Quit

### Output Options
```bash
# Output to file
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Output format
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Show cracked
hashcat -m 0 hash.txt --show

# Show remaining (uncracked)
hashcat -m 0 hash.txt --left
```

---

## 🚀 GPU Optimization

### Workload Profiles

| Profile | Description |
|---------|-------------|
| `-w 1` | Low (default, interactive) |
| `-w 2` | Medium |
| `-w 3` | High (may freeze desktop) |
| `-w 4` | Nightmare (dedicated cracking) |

### Optimize Commands
```bash
# High performance
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Optimized kernels (faster, limited password length)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Force device
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Disable CPU, use only GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Device Options
```bash
# List devices
hashcat -I

# Select specific device
hashcat -d 1 ...

# Device types
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Benchmark
```bash
# Full benchmark
hashcat -b

# Specific hash type
hashcat -b -m 0

# Benchmark without OpenCL optimization
hashcat -b --benchmark-all
```

---

## 🎬 Real-World Examples

### Example 1: MD5 Dictionary Attack
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Example 2: NTLM with Rules
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Example 3: WPA/WPA2 Handshake
```bash
# Convert to hashcat format first
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Crack
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Example 4: SHA256 Brute Force (6 chars)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Example 5: Office 2013 Document
```bash
# Extract hash with office2john
office2john document.docx > office.hash

# Crack
hashcat -m 9600 office.hash wordlist.txt
```

### Example 6: Linux Shadow Password
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Example 7: WordPress Hash
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Example 8: Hybrid Attack
```bash
# Company name + 4 digits
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Example 9: Windows Domain Hashes
```bash
# NTLM hashes from secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Example 10: Bitcoin Wallet
```bash
# Extract with bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| MD5 dictionary | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| NTLM brute force | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 with rules | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Show cracked | `hashcat -m 0 hash.txt --show` |
| Benchmark | `hashcat -b` |
| List devices | `hashcat -I` |
| Restore session | `hashcat --session=name --restore` |

### Common Hash Modes

| Mode | Type |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Attack Modes

| Mode | Type |
|------|------|
| -a 0 | Dictionary |
| -a 1 | Combination |
| -a 3 | Brute-force |
| -a 6 | Hybrid (dict+mask) |
| -a 7 | Hybrid (mask+dict) |

---

## 💡 Tips & Best Practices

### Efficiency Tips

1. **Start with Dictionary + Rules**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Use Optimized Kernels**
   ```bash
   hashcat -O ...
   ```

3. **Target Common Patterns**
   ```bash
   # Season+Year: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Performance Tips

```bash
# Maximum performance (dedicated system)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Multiple GPUs
hashcat -d 1,2 ...
```

### Workflow

```
1. Identify hash type
2. Try dictionary attack with rules
3. Try targeted masks (patterns)
4. Try full brute force (if feasible)
5. Try hybrid attacks
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Hashcat is a powerful password cracking tool that should only be used for **authorized purposes**.
> 
> - ✅ Audit your own systems
> - ✅ Security assessments with permission
> - ✅ Recovery of your own passwords
> - ❌ Never crack passwords without authorization
> - ❌ Unauthorized access is illegal
> 
> **Password cracking without authorization is illegal and may result in criminal charges.**

---

## 📚 Resources

### Official Resources
- [Hashcat Official](https://hashcat.net/hashcat/)
- [Hashcat Wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Hashcat Forum](https://hashcat.net/forum/)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Related Cheatsheets
- [John the Ripper](../John-The-Ripper/README.md)
- [Hydra](../Hydra/README.md)
- [Nmap](../Nmap/README.md)

---

<p align="center">
  <b>⚡ Master GPU Password Cracking!</b><br>
  <i>The world's fastest password recovery</i>
</p>
