# ⚔️ Katana - Next-Gen Web Crawler

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Next-Generation Web Crawling Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Table of Contents

- [What is Katana](#-what-is-katana)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Crawling Options](#-crawling-options)
- [Headless Mode](#-headless-mode)
- [Output & Filtering](#-output--filtering)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Katana

**Katana** is a next-generation web crawling framework by ProjectDiscovery:

- ⚡ **Fast** - Go-based, highly concurrent
- 🔍 **JavaScript Rendering** - Headless browser support
- 📡 **Modern Web** - Handles SPAs, dynamic content
- 🎯 **Bug Bounty Focused** - Built for security testing

### Why Katana?

| Feature | Benefit |
|---------|---------|
| Headless Chrome | Crawl JS-heavy apps |
| Custom Headers | Authenticated crawling |
| Scope Control | Stay in scope |
| Form Filling | Discover more endpoints |

---

## 🚀 Installation

```bash
# Go install (recommended)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Verify
katana -version
```

---

## 📖 Basic Usage

### Single Target

```bash
# Basic crawl
katana -u https://example.com

# Save output
katana -u https://example.com -o urls.txt

# Silent mode
katana -u https://example.com -silent
```

### Multiple Targets

```bash
# From stdin
echo "https://example.com" | katana

# Multiple URLs
cat targets.txt | katana

# From file
katana -list targets.txt
```

### Crawl Depth

```bash
# Set depth (default: 3)
katana -u https://example.com -d 5

# Depth 1 (only target page)
katana -u https://example.com -d 1
```

---

## 🕷️ Crawling Options

### Scope Control

```bash
# Crawl only in scope (same domain)
katana -u https://example.com -fs dn

# Scope options:
# dn - domain name
# rdn - root domain
# fqdn - full qualified domain name

# Field scope
katana -u https://example.com -fs rdn
```

### Crawling Strategy

```bash
# Depth-first (default)
katana -u https://example.com -cs depth-first

# Breadth-first
katana -u https://example.com -cs breadth-first
```

### Concurrency

```bash
# Concurrent requests (default: 10)
katana -u https://example.com -c 50

# Rate limit (requests per second)
katana -u https://example.com -rl 100

# Parallelism
katana -u https://example.com -p 10
```

### Crawl Duration

```bash
# Set timeout (seconds)
katana -u https://example.com -timeout 30

# Max crawl time
katana -u https://example.com -ct 300
```

### Automatic Form Filling

```bash
# Enable form filling
katana -u https://example.com -aff

# With custom values
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Headless Mode

### Enable Headless Browser

```bash
# Enable headless Chrome
katana -u https://example.com -headless

# Headless with depth
katana -u https://example.com -headless -d 3
```

### Headless Options

```bash
# Custom Chrome path
katana -u https://example.com -headless -system-chrome

# No sandbox (for root)
katana -u https://example.com -headless -no-sandbox

# Screenshot pages
katana -u https://example.com -headless -screenshot
```

### JavaScript Crawling

```bash
# Wait for JS to load (ms)
katana -u https://example.com -headless -timeout 5

# Crawl JS endpoints
katana -u https://example.com -js-crawl
```

---

## 📤 Output & Filtering

### Output Formats

```bash
# Standard output to file
katana -u https://example.com -o results.txt

# JSON output
katana -u https://example.com -jsonl -o results.json

# No color
katana -u https://example.com -nc
```

### Display Fields

```bash
# Show specific fields
katana -u https://example.com -f url,method,status

# Available fields:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filter by Extension

```bash
# Match extensions
katana -u https://example.com -em js,php,asp

# Exclude extensions
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filter by Status Code

```bash
# Match status code
katana -u https://example.com -mc 200,301,302

# Filter status code
katana -u https://example.com -fc 404,500
```

### Custom Headers

```bash
# Add header
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Multiple headers
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Proxy Support

```bash
# HTTP proxy
katana -u https://example.com -proxy http://127.0.0.1:8080

# For Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Quick Reference

### Basic Commands

| Command | Description |
|---------|-------------|
| `katana -u URL` | Crawl single URL |
| `katana -list file.txt` | Crawl from file |
| `katana -u URL -d 5` | Set depth to 5 |
| `katana -u URL -headless` | Enable headless |
| `katana -u URL -o out.txt` | Save results |

### Scope Options

| Flag | Description |
|------|-------------|
| `-fs dn` | Same domain |
| `-fs rdn` | Root domain |
| `-fs fqdn` | Full domain |
| `-cs depth-first` | Depth-first crawl |
| `-cs breadth-first` | Breadth-first crawl |

### Performance Flags

| Flag | Description |
|------|-------------|
| `-c 50` | Concurrency |
| `-p 10` | Parallelism |
| `-rl 100` | Rate limit |
| `-timeout 30` | Request timeout |

### Filtering Flags

| Flag | Description |
|------|-------------|
| `-em js,php` | Match extensions |
| `-ef png,jpg` | Filter extensions |
| `-mc 200` | Match status codes |
| `-fc 404` | Filter status codes |

---

## 🎯 Bug Bounty Workflows

### Basic Crawl

```bash
# Quick crawl
katana -u https://target.com -d 3 -o urls.txt
```

### Comprehensive Crawl

```bash
# Deep crawl with JS
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Parameter Discovery

```bash
# Find URLs with parameters
katana -u https://target.com -d 3 | grep "?" | sort -u

# With grep for specific params
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Combined with Other Tools

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Full pipeline
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Authenticated Crawling

```bash
# With session cookie
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Resources

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Related Cheatsheets
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/README.md)
- [httpx](../httpx/README.md)
- [Nuclei](../Nuclei/README.md)
- [GAU](../GAU/README.md)

---

<p align="center">
  <b>⚔️ Crawl Everything!</b><br>
  <i>Katana - Next-Gen Web Crawler</i>
</p>
