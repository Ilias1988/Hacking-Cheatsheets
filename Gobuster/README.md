# 🚀 Gobuster - Complete Cheatsheet

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Directory/DNS/VHost Brute-Forcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Fast directory, DNS, and VHost enumeration tool written in Go</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Modes Overview](#-modes-overview)
- [Directory Mode (dir)](#-directory-mode-dir)
- [DNS Mode (dns)](#-dns-mode-dns)
- [VHost Mode (vhost)](#-vhost-mode-vhost)
- [Fuzz Mode (fuzz)](#-fuzz-mode-fuzz)
- [Wordlists](#-wordlists)
- [Authentication & Headers](#-authentication--headers)
- [Performance Tuning](#-performance-tuning)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Gobuster** is a fast, multi-threaded brute-force tool written in Go for:
- Directory/File enumeration on web servers
- DNS subdomain discovery
- Virtual Host (VHost) discovery
- URL fuzzing

### Why Gobuster?

| Feature | Description |
|---------|-------------|
| **Fast** | Written in Go, highly concurrent |
| **Flexible** | Multiple modes for different tasks |
| **No Recursion** | Clean results without noise |
| **Stable** | Handles errors gracefully |
| **Active Development** | Regular updates |

### Comparison with Other Tools

| Tool | Speed | Recursion | Language |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | No | Go |
| Dirb | ⭐⭐ | Yes | C |
| Dirbuster | ⭐⭐⭐ | Yes | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | No | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Yes | Rust |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
# Update Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### From Source (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Using Snap
```bash
sudo snap install gobuster
```

### From GitHub Releases
```bash
# Download latest release
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Verify Installation
```bash
gobuster version
gobuster -h
```

---

## 📊 Modes Overview

### Available Modes

| Mode | Description | Use Case |
|------|-------------|----------|
| `dir` | Directory/file enumeration | Find hidden directories/files |
| `dns` | DNS subdomain enumeration | Find subdomains |
| `vhost` | Virtual host enumeration | Find virtual hosts |
| `fuzz` | URL fuzzing | Fuzz parameters/paths |
| `s3` | S3 bucket enumeration | Find AWS S3 buckets |
| `gcs` | Google Cloud Storage | Find GCS buckets |
| `tftp` | TFTP enumeration | Find TFTP files |

### Basic Syntax
```bash
gobuster [mode] [options]

# Examples
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

## 📁 Directory Mode (dir)

### Basic Syntax
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-u` | Target URL |
| `-w` | Wordlist path |
| `-t` | Threads (default: 10) |
| `-x` | File extensions to search |
| `-s` | Positive status codes |
| `-b` | Negative status codes (blacklist) |
| `-o` | Output file |
| `-k` | Skip SSL verification |
| `-r` | Follow redirects |
| `-a` | User-Agent string |
| `-c` | Cookies |
| `-H` | Custom headers |

### Basic Directory Scan
```bash
# Simple directory scan
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# With more threads
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### File Extension Search
```bash
# Search for specific extensions
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Multiple extensions
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Backup files
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Status Code Filtering
```bash
# Only show specific status codes
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Exclude status codes
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Show all (including 404)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Output Options
```bash
# Save to file
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Verbose output
gobuster dir -u http://target.com -w wordlist.txt -v

# No progress
gobuster dir -u http://target.com -w wordlist.txt -q

# Expanded mode (full URLs)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### SSL/TLS Options
```bash
# Skip SSL verification
gobuster dir -u https://target.com -w wordlist.txt -k

# Skip SSL with extended options
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Directory Mode Examples
```bash
# Standard web directory scan
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# PHP application
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# API endpoint discovery
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Find backup files
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

## 🌐 DNS Mode (dns)

### Basic Syntax
```bash
gobuster dns -d <domain> -w <wordlist>
```

### DNS Options

| Option | Description |
|--------|-------------|
| `-d` | Target domain |
| `-w` | Wordlist path |
| `-t` | Threads |
| `-r` | Custom DNS resolver |
| `-i` | Show IP addresses |
| `-c` | Show CNAME records |
| `--wildcard` | Force wildcard processing |
| `--timeout` | DNS timeout |

### Basic DNS Enumeration
```bash
# Simple subdomain scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Show IPs
gobuster dns -d target.com -w subdomains.txt -i

# Show CNAMEs
gobuster dns -d target.com -w subdomains.txt -c
```

### Custom DNS Resolver
```bash
# Use specific DNS server
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Multiple resolvers
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### DNS Examples
```bash
# Comprehensive subdomain scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Quick subdomain scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# With output
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

## 🏠 VHost Mode (vhost)

### Basic Syntax
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### VHost Options

| Option | Description |
|--------|-------------|
| `-u` | Target URL |
| `-w` | Wordlist path |
| `-t` | Threads |
| `--append-domain` | Append domain to wordlist entries |
| `--domain` | Domain for vhost |
| `-r` | Follow redirects |

### Basic VHost Enumeration
```bash
# Simple VHost scan
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# With domain appending
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### VHost Examples
```bash
# Standard VHost discovery
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Development hosts
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# With custom domain
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

## 🔧 Fuzz Mode (fuzz)

### Basic Syntax
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Fuzz Options

| Option | Description |
|--------|-------------|
| `-u` | URL with FUZZ keyword |
| `-w` | Wordlist path |
| `-t` | Threads |
| `-b` | Blacklist status codes |
| `--exclude-length` | Exclude by response length |

### Fuzz Examples
```bash
# Fuzz parameter value
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Fuzz path
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Exclude specific lengths
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

## 📚 Wordlists

### SecLists Locations (Kali Linux)

```bash
# Directory/File enumeration
/usr/share/seclists/Discovery/Web-Content/

# Popular directory lists
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS/Subdomains
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Default Wordlists

```bash
# Dirb wordlists
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Dirbuster wordlists
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Wordlist Recommendations

| Use Case | Wordlist |
|----------|----------|
| Quick scan | `common.txt` |
| Standard | `directory-list-2.3-medium.txt` |
| Thorough | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Subdomains | `subdomains-top1million-5000.txt` |
| Files | `raft-large-files.txt` |

---

## 🔐 Authentication & Headers

### Basic Authentication
```bash
# HTTP Basic Auth
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Cookie Authentication
```bash
# Single cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Multiple cookies
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# PHP Session
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Custom Headers
```bash
# Single header
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Multiple headers
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# API Key
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# Custom User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Proxy Support
```bash
# HTTP Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# SOCKS5 Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Burp Suite Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

## ⚡ Performance Tuning

### Thread Management
```bash
# Default: 10 threads
gobuster dir -u http://target.com -w wordlist.txt

# Increase threads (fast network)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Reduce threads (slow/unstable)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Timeout Settings
```bash
# Increase timeout
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# DNS timeout
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Delay Between Requests
```bash
# Add delay (rate limiting)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Slower for WAF evasion
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

## 🎬 Real-World Examples

### Example 1: Standard Web Enumeration
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Example 2: API Endpoint Discovery
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Example 3: Authenticated Scan
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Example 4: Subdomain Enumeration
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Example 5: VHost Discovery (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Example 6: Through Burp Proxy
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Example 7: Backup File Search
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Example 8: CMS Detection
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic dir scan | `gobuster dir -u URL -w wordlist.txt` |
| With extensions | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Subdomain scan | `gobuster dns -d domain.com -w wordlist.txt` |
| VHost scan | `gobuster vhost -u URL -w wordlist.txt` |
| With output | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Skip SSL errors | `gobuster dir -u URL -w wordlist.txt -k` |
| With cookies | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Through proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Common Options

| Option | Short | Description |
|--------|-------|-------------|
| `--url` | `-u` | Target URL |
| `--wordlist` | `-w` | Wordlist file |
| `--threads` | `-t` | Number of threads |
| `--extensions` | `-x` | File extensions |
| `--status-codes` | `-s` | Positive status codes |
| `--cookies` | `-c` | Cookies to send |
| `--output` | `-o` | Output file |
| `--insecuressl` | `-k` | Skip SSL verification |
| `--verbose` | `-v` | Verbose output |
| `--quiet` | `-q` | Quiet mode |

---

## 💡 Tips & Best Practices

### Efficiency Tips

1. **Start Small, Scale Up**
   ```bash
   # Quick scan first
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Then larger wordlist
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Target-Specific Extensions**
   ```bash
   # PHP site
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # ASP.NET site
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Combine with Other Tools**
   ```bash
   # Nmap → Gobuster workflow
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Avoiding Detection

```bash
# Slow scan with delay
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Custom User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Through Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Gobuster is a powerful enumeration tool that should only be used for **authorized testing**.
> 
> - ✅ Test on systems you own
> - ✅ Test with explicit written permission
> - ✅ Use in CTF challenges and labs
> - ❌ Never scan without authorization
> - ❌ Unauthorized enumeration may be illegal
> 
> **Always get permission before scanning any system.**

---

## 📚 Resources

### Official Resources
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Related Cheatsheets
- [Nmap](../Nmap/README.md)
- [Burp Suite](../Burp-Suite/README.md)
- [SQLMap](../SQLMap/README.md)

---

<p align="center">
  <b>🚀 Master Web Enumeration!</b><br>
  <i>Discover what others can't see</i>
</p>
