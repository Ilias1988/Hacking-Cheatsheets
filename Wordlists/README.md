# 📚 Wordlists - Complete Reference Guide

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Complete reference for penetration testing wordlists</b>
</p>

---

## 📋 Table of Contents

- [Password Wordlists](#-password-wordlists)
- [Directory/File Wordlists](#-directoryfile-wordlists)
- [Subdomain Wordlists](#-subdomain-wordlists)
- [Username Wordlists](#-username-wordlists)
- [Web Fuzzing Wordlists](#-web-fuzzing-wordlists)
- [SecLists Collection](#-seclists-collection)
- [Kali Default Wordlists](#-kali-default-wordlists)
- [Custom Wordlist Tools](#-custom-wordlist-tools)
- [Quick Download](#-quick-download)
- [Tips & Best Practices](#-tips--best-practices)

---

## 🔐 Password Wordlists

### Most Popular

| Wordlist | Size | Lines | Source | Use Case |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Password cracking |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Common passwords |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Large password list |
| **common-passwords-win.txt** | - | - | SecLists | Windows passwords |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Top 1M passwords |

### Password Lists by Type

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Specialized Password Lists

| Wordlist | Purpose | Location |
|----------|---------|----------|
| **wifi-passwords.txt** | WiFi cracking | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Statistical analysis | SecLists/Passwords/ |
| **seasons-only.txt** | Seasonal patterns | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Banned passwords | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Default creds | SecLists/Passwords/Default-Credentials/ |

### Kali Locations

```bash
# RockYou (decompress first)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Directory/File Wordlists

### Directory Brute-forcing

| Wordlist | Size | Lines | Source | Use Case |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Quick scan |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Small enum |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Standard enum |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Complete enum |
| **big.txt** | 210 KB | 20K | SecLists | Common dirs |

### RAFT Series (Highly Recommended!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K dirs
├── raft-large-files.txt            # 37K files
├── raft-large-words.txt            # 120K words
├── raft-medium-directories.txt     # 30K dirs
├── raft-medium-files.txt           # 17K files
├── raft-medium-words.txt           # 63K words
├── raft-small-directories.txt      # 20K dirs
├── raft-small-files.txt            # 11K files
└── raft-small-words.txt            # 43K words
```

### File Extension Lists

| Wordlist | Purpose |
|----------|---------|
| **web-extensions.txt** | Common web extensions |
| **backup-extensions.txt** | Backup files (.bak, .old) |
| **raft-large-extensions.txt** | Large extension list |

### Specialized Discovery

| Wordlist | Purpose | Lines |
|----------|---------|-------|
| **apache.txt** | Apache-specific | - |
| **nginx.txt** | Nginx-specific | - |
| **iis.txt** | IIS-specific | - |
| **api-endpoints.txt** | API discovery | - |
| **graphql.txt** | GraphQL endpoints | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Common Locations

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# SecLists Discovery
/usr/share/seclists/Discovery/Web-Content/

# Quick downloads
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

## 🌐 Subdomain Wordlists

### Popular Subdomain Lists

| Wordlist | Lines | Source | Use Case |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Quick enum |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Standard enum |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Thorough enum |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Fierce compatible |
| **combined_subdomains.txt** | 648K | SecLists | Combined list |

### Subdomain Lists Location

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Best for bug bounty!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Bug Bounty Recommended

```bash
# Jason Haddix methodology
dns-Jhaddix.txt              # 2M+ subdomains

# Combined for thoroughness
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

## 👤 Username Wordlists

### Common Username Lists

| Wordlist | Lines | Purpose |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Quick check |
| **xato-net-10-million-usernames.txt** | 10M | Large list |
| **cirt-default-usernames.txt** | - | Default users |
| **names.txt** | - | Common names |
| **familynames-usa-top1000.txt** | 1K | Surnames |

### Username Lists Location

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Default Credentials

| Service | Wordlist |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

## 💉 Web Fuzzing Wordlists

### SQL Injection

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (Cross-Site Scripting)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (Local/Remote File Inclusion)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Command Injection

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (Server-Side Template Injection)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Fuzzing Collections

| Wordlist | Purpose |
|----------|---------|
| **special-chars.txt** | Special characters |
| **Unicode.txt** | Unicode fuzzing |
| **big-list-of-naughty-strings.txt** | Edge cases |
| **fuzz-Bo0oM.txt** | General fuzzing |
| **burp-parameter-names.txt** | Parameter names |

---

## 📦 SecLists Collection

### About SecLists

**SecLists** is the pentester's companion - a collection of multiple types of lists used during security assessments.

### Installation

```bash
# Kali Linux (pre-installed)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Git Clone
git clone https://github.com/danielmiessler/SecLists.git

# Quick download (specific lists)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### SecLists Structure

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Subdomain wordlists
│   ├── Web-Content/            # Directory/file wordlists
│   └── Infrastructure/         # Services/ports
├── Fuzzing/
│   ├── SQLi/                   # SQL injection payloads
│   ├── XSS/                    # XSS payloads
│   ├── LFI/                    # LFI/RFI payloads
│   └── command-injection*.txt  # Command injection
├── Passwords/
│   ├── Common-Credentials/     # Password lists
│   ├── Leaked-Databases/       # rockyou, etc.
│   └── Default-Credentials/    # Default passwords
├── Usernames/
│   ├── Names/                  # Name lists
│   └── top-usernames*.txt      # Username lists
├── Payloads/
│   └── Anti-Antivirus/         # AV bypass
└── Pattern-Matching/
    └── Hunting/                # Regex patterns
```

### GitHub Repository

🔗 https://github.com/danielmiessler/SecLists

---

## 🐧 Kali Default Wordlists

### Wordlists Location

```bash
# Main wordlists directory
/usr/share/wordlists/

# Contents
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Decompress with gunzip
├── dirb/                   # DIRB wordlists
├── dirbuster/              # DirBuster wordlists
├── fasttrack.txt           # Fast track list
├── fern-wifi/              # WiFi wordlists
├── metasploit/             # MSF wordlists
├── nmap.lst                # Nmap scripts list
├── wfuzz/                  # wFuzz wordlists
└── seclists -> /usr/share/seclists/
```

### DirBuster Lists

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### DIRB Lists

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K entries
├── common.txt               # 4.6K entries
├── small.txt                # 959 entries
├── catala.txt               # Catalan
├── euskera.txt              # Basque
├── extensions_common.txt    # Extensions
├── indexes.txt              # Index files
├── mutations_common.txt     # Mutations
├── spanish.txt              # Spanish
├── stress/                  # Stress tests
└── vulns/                   # Vulnerability checks
```

---

## 🛠️ Custom Wordlist Tools

### CeWL (Custom Word List generator)

```bash
# Installation
sudo apt install cewl

# Basic usage - crawl website
cewl https://target.com -w wordlist.txt

# With depth and minimum word length
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Include emails
cewl https://target.com -e -w wordlist.txt

# Options
-d <depth>      # Depth to spider (default: 2)
-m <length>     # Minimum word length (default: 3)
-w <file>       # Output file
-e              # Include email addresses
--lowercase     # Convert to lowercase
-c              # Count word occurrences
```

### Crunch (Wordlist generator)

```bash
# Installation
sudo apt install crunch

# Basic syntax
crunch <min-len> <max-len> [charset] -o <output>

# Generate 4-6 character passwords
crunch 4 6 -o wordlist.txt

# With specific characters
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# With pattern
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = lowercase, , = uppercase, % = number, ^ = special

# Custom charset
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Pipe to hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (Common User Passwords Profiler)

```bash
# Installation
git clone https://github.com/Mebus/cupp.git
cd cupp

# Interactive mode - profile-based
python3 cupp.py -i

# Questions asked:
# - First name, last name, nickname
# - Birthdate
# - Partner's info
# - Pet's name
# - Company name
# - Keywords

# Download common passwords
python3 cupp.py -l

# Use existing wordlist with profiling
python3 cupp.py -w wordlist.txt
```

### Mentalist (GUI Wordlist Generator)

```bash
# GUI tool for complex wordlist generation
# Supports rules, case modifications, substitutions
# Download from: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Simple custom wordlist generator
import itertools

# Generate combinations
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Hashcat Rules

```bash
# Use rules to mutate wordlist
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Popular rules
/usr/share/hashcat/rules/
├── best64.rule         # Best 64 rules
├── rockyou-30000.rule  # RockYou based
├── d3ad0ne.rule        # d3ad0ne's rules
├── dive.rule           # DIVE rules
└── toggles1-5.rule     # Toggle case
```

---

## ⬇️ Quick Download

### One-Liner Downloads

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists (full)
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Common passwords (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Directory list medium
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Subdomain list
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Jhaddix DNS (bug bounty)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Clone Repositories

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Assetnote Wordlists
git clone https://github.com/assetnote/wordlists.git
```

---

## 💡 Tips & Best Practices

### Choosing the Right Wordlist

| Task | Recommended Wordlist |
|------|---------------------|
| **Quick Dir Scan** | common.txt, raft-small-directories.txt |
| **Thorough Dir Scan** | directory-list-2.3-medium.txt |
| **Complete Dir Scan** | directory-list-2.3-big.txt |
| **Subdomain Enum** | subdomains-top1million-20000.txt |
| **Bug Bounty DNS** | dns-Jhaddix.txt |
| **Password Cracking** | rockyou.txt |
| **Quick Password** | top-10000.txt |

### Combining Wordlists

```bash
# Combine and deduplicate
cat list1.txt list2.txt | sort -u > combined.txt

# Add prefix to all lines
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Add suffix
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Both
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Wordlist Optimization

```bash
# Sort and remove duplicates
sort -u wordlist.txt -o wordlist.txt

# Remove empty lines
sed -i '/^$/d' wordlist.txt

# Remove lines shorter than 4 chars
awk 'length >= 4' wordlist.txt > filtered.txt

# Convert to lowercase
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Count lines
wc -l wordlist.txt
```

### Performance Tips

1. **Start Small** - Use smaller lists first, then expand
2. **Use Patterns** - If you know format, use specific patterns
3. **Combine Tools** - Generate custom + use standard lists
4. **Targeted Lists** - Create target-specific lists with CeWL

---

## 📊 Quick Reference

### Top Wordlists by Category

| Category | Best Choice | Location |
|----------|-------------|----------|
| **Passwords** | rockyou.txt | SecLists/Passwords/ |
| **Directories** | directory-list-2.3-medium.txt | DirBuster |
| **Files** | raft-large-files.txt | SecLists |
| **Subdomains** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Usernames** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Size Guide

| Size | Lines | Use Case |
|------|-------|----------|
| **Small** | < 5K | Quick tests |
| **Medium** | 5K - 100K | Standard enum |
| **Large** | 100K - 1M | Thorough scan |
| **Huge** | > 1M | Complete coverage |

---

## 📚 Resources

### Wordlist Repositories
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Tools
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Related Cheatsheets
- [Hydra](../Hydra/README.md)
- [John the Ripper](../John-The-Ripper/README.md)
- [Hashcat](../Hashcat/README.md)
- [Gobuster](../Gobuster/README.md)
- [ffuf](../ffuf/README.md)

---

<p align="center">
  <b>📚 The Right Wordlist Makes All the Difference!</b><br>
  <i>Choose wisely, combine smartly</i>
</p>
