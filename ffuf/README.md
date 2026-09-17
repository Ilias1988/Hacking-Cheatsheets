# 🚀 ffuf - Complete Cheatsheet

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ The fastest web fuzzer written in Go</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Directory Fuzzing](#-directory-fuzzing)
- [Subdomain Fuzzing](#-subdomain-fuzzing)
- [Parameter Fuzzing](#-parameter-fuzzing)
- [POST Data Fuzzing](#-post-data-fuzzing)
- [Filtering & Matching](#-filtering--matching)
- [Advanced Features](#-advanced-features)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**ffuf** (Fuzz Faster U Fool) is a fast web fuzzer written in Go. It's the go-to tool for directory brute-forcing, virtual host discovery, and parameter fuzzing in bug bounty hunting.

### Key Features

| Feature | Description |
|---------|-------------|
| **Speed** | Extremely fast (Go-based) |
| **Flexible** | Fuzz any part of HTTP request |
| **Filtering** | Multiple filter/match options |
| **Recursion** | Recursive scanning support |
| **Output** | Multiple output formats |
| **Easy** | Simple and intuitive syntax |

### ffuf vs Other Tools

| Feature | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Speed** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Flexibility** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST fuzzing** | ✅ Yes | ❌ No | ❌ No |
| **Recursion** | ✅ Yes | ✅ Yes | ✅ Yes |
| **Filters** | Many | Limited | Limited |

---

## 📥 Installation

### Using Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Binary Download
```bash
# Download from releases
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Verify Installation
```bash
ffuf -V
ffuf -h
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### The FUZZ Keyword

The **FUZZ** keyword tells ffuf where to inject words from the wordlist.

```bash
# In URL path
ffuf -u https://target.com/FUZZ -w wordlist.txt

# In subdomain
ffuf -u https://FUZZ.target.com -w wordlist.txt

# In parameter value
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# In POST data
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-u` | Target URL with FUZZ keyword |
| `-w` | Wordlist path |
| `-o` | Output file |
| `-of` | Output format (json, csv, html) |
| `-t` | Number of threads (default: 40) |
| `-p` | Delay between requests |
| `-r` | Follow redirects |
| `-v` | Verbose output |
| `-s` | Silent mode |

---

## 📁 Directory Fuzzing

### Basic Directory Brute Force

```bash
# Basic directory fuzzing
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# With file extensions
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Multiple extensions
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Recursive Scanning

```bash
# Enable recursion
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Recursion depth
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Recursion with strategy
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filter by Status Code

```bash
# Match only 200 responses
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Match multiple codes
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Filter out 404s (default behavior change)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Match all, filter 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Popular Wordlists

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

## 🌐 Subdomain Fuzzing

### Virtual Host Discovery

```bash
# Basic subdomain fuzzing
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filter by response size (remove default page)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# With specific wordlist
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### DNS Subdomain Fuzzing

```bash
# Subdomain in URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# With resolver
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Common Subdomain Wordlists

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

## 🔍 Parameter Fuzzing

### GET Parameter Fuzzing

```bash
# Fuzz parameter name
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Fuzz parameter value
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Multiple parameters
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Parameter Discovery

```bash
# Find hidden parameters
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 POST Data Fuzzing

### Basic POST Fuzzing

```bash
# POST with form data
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST with content type
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### JSON POST Fuzzing

```bash
# JSON POST data
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Multiple Wordlists (Credential Stuffing)

```bash
# Username and password fuzzing
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Mode: clusterbomb (all combinations)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtering & Matching

### Filter Options

| Option | Description | Example |
|--------|-------------|---------|
| `-fc` | Filter by status code | `-fc 404,403` |
| `-fs` | Filter by response size | `-fs 1234` |
| `-fw` | Filter by word count | `-fw 100` |
| `-fl` | Filter by line count | `-fl 50` |
| `-ft` | Filter by time (ms) | `-ft 5000` |
| `-fr` | Filter by regex | `-fr "error"` |

### Match Options

| Option | Description | Example |
|--------|-------------|---------|
| `-mc` | Match status codes | `-mc 200,301` |
| `-ms` | Match response size | `-ms 1234` |
| `-mw` | Match word count | `-mw 100` |
| `-ml` | Match line count | `-ml 50` |
| `-mt` | Match time (ms) | `-mt 5000` |
| `-mr` | Match by regex | `-mr "success"` |

### Filter Examples

```bash
# Filter 404 and 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filter by size (remove default page)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filter by words
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Match all, filter specific
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Auto-Calibration

```bash
# Auto-calibrate filters
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Calibration with specific keyword
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Advanced Features

### Rate Limiting

```bash
# Requests per second
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Delay between requests
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Random delay (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Threads

```bash
# Set threads (default: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Reduce for stealth
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Headers

```bash
# Custom header
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Multiple headers
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# HTTP proxy
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Output

```bash
# JSON output
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# CSV output
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# HTML output
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# All formats
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Timeout & Retries

```bash
# Request timeout
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Stop on 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignore SSL errors
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

## 🎬 Real-World Examples

### Example 1: Basic Directory Scan
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Example 2: Find PHP Files
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Example 3: Subdomain Enumeration
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Example 4: API Endpoint Discovery
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Example 5: Parameter Discovery
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Example 6: Login Brute Force
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Example 7: With Proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Example 8: Recursive Scan
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Example 9: Rate Limited Scan
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Example 10: Complete Bug Bounty Scan
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Directory scan | `ffuf -u URL/FUZZ -w wordlist.txt` |
| With extensions | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Subdomain scan | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST fuzzing | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Filter 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Filter by size | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Auto-calibrate | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Save output | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Common Options

| Option | Description |
|--------|-------------|
| `-u` | URL with FUZZ |
| `-w` | Wordlist |
| `-e` | Extensions |
| `-t` | Threads |
| `-mc` | Match codes |
| `-fc` | Filter codes |
| `-fs` | Filter size |
| `-ac` | Auto-calibrate |
| `-r` | Follow redirects |
| `-o` | Output file |

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Always Auto-Calibrate First**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Use Multiple Extensions**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Try Common Backup Extensions**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Rate Limit for WAF Bypass**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Performance Tips

```bash
# High speed (no WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Low and slow (stealth)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** ffuf is a powerful fuzzing tool that should only be used for **authorized testing**.
> 
> - ✅ Use on programs you have permission to test
> - ✅ Use in authorized bug bounty programs
> - ✅ Respect rate limits and scope
> - ❌ Never fuzz without authorization
> - ❌ Don't cause denial of service
> 
> **Unauthorized scanning is illegal.**

---

## 📚 Resources

### Official Resources
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

### Related Cheatsheets
- [Nuclei](../Nuclei/README.md)
- [Gobuster](../Gobuster/README.md)
- [Nmap](../Nmap/README.md)

---

<p align="center">
  <b>🚀 Fuzz Faster, Find More!</b><br>
  <i>The bug bounty hunter's fuzzer of choice</i>
</p>
