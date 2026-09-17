# 🌐 httpx - Complete Cheatsheet

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Fast HTTP Probe & Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Fast and multi-purpose HTTP toolkit by ProjectDiscovery</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Probing & Discovery](#-probing--discovery)
- [Information Extraction](#-information-extraction)
- [Filtering & Matching](#-filtering--matching)
- [Output Options](#-output-options)
- [Advanced Features](#-advanced-features)
- [Bug Bounty Workflow](#-bug-bounty-workflow)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**httpx** is a fast and multi-purpose HTTP toolkit that allows running multiple probes using the retryablehttp library. It's designed for bug bounty hunters and security researchers.

### Key Features

| Feature | Description |
|---------|-------------|
| **Fast** | Highly concurrent Go-based tool |
| **Multi-Probe** | Status code, title, tech, headers |
| **Tech Detection** | Wappalyzer-based detection |
| **Screenshot** | Headless browser screenshots |
| **Pipeline** | Works with subfinder, nuclei |
| **JSON Output** | Structured output support |

### httpx vs Other Tools

| Feature | httpx | curl | wget |
|---------|-------|------|------|
| **Speed** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Concurrent** | ✅ Yes | ❌ No | ❌ No |
| **Tech Detection** | ✅ Yes | ❌ No | ❌ No |
| **Screenshots** | ✅ Yes | ❌ No | ❌ No |
| **Pipeline** | ✅ Yes | ⚠️ Manual | ⚠️ Manual |

---

## 📥 Installation

### Using Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Binary Download
```bash
# Download from releases
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Verify Installation
```bash
httpx -version
httpx -h
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-u` | Single target URL |
| `-l` | File with list of URLs |
| `-sc` | Show status code |
| `-title` | Show page title |
| `-td` | Technology detection |
| `-o` | Output file |
| `-silent` | Silent mode |
| `-json` | JSON output |

### Quick Start
```bash
# Probe single URL
httpx -u https://example.com

# Probe from file
httpx -l urls.txt

# From stdin (pipeline)
cat hosts.txt | httpx

# With subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 Probing & Discovery

### Basic Probing

```bash
# Probe hosts for HTTP/HTTPS
cat hosts.txt | httpx

# Include status code
cat hosts.txt | httpx -sc

# Probe specific ports
cat hosts.txt | httpx -p 80,443,8080,8443

# Probe all common ports
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Port Scanning

```bash
# Custom ports
httpx -l hosts.txt -p 80,443,8080,8443

# Port range
httpx -l hosts.txt -p 80-100

# Common web ports
httpx -l hosts.txt -p http:80,https:443
```

### Protocol Detection

```bash
# HTTP/HTTPS probing
cat hosts.txt | httpx

# Follow redirects
cat hosts.txt | httpx -follow-redirects

# Max redirects
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# No fallback
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Information Extraction

### Status Codes & Titles

```bash
# Status code
httpx -l urls.txt -sc

# Page title
httpx -l urls.txt -title

# Both
httpx -l urls.txt -sc -title

# Content length
httpx -l urls.txt -cl

# Web server
httpx -l urls.txt -server

# All basic info
httpx -l urls.txt -sc -title -cl -server
```

### Technology Detection

```bash
# Detect technologies
httpx -l urls.txt -td

# Tech detection (verbose)
httpx -l urls.txt -tech-detect

# With other info
httpx -l urls.txt -sc -title -td
```

### Headers & Response

```bash
# Response headers
httpx -l urls.txt -response-header

# Specific header
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Response body hash
httpx -l urls.txt -hash md5

# Body preview
httpx -l urls.txt -bp
```

### IP & DNS Information

```bash
# IP address
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# CDN detection
httpx -l urls.txt -cdn

# ASN info
httpx -l urls.txt -asn

# All DNS info
httpx -l urls.txt -ip -cname -cdn -asn
```

### SSL/TLS Information

```bash
# TLS probe
httpx -l urls.txt -tls-probe

# TLS grab
httpx -l urls.txt -tls-grab

# Certificate info
httpx -l urls.txt -tlsgrab
```

### Favicon Hash

```bash
# Get favicon hash (useful for Shodan searches)
httpx -l urls.txt -favicon
```

### Screenshot Capture

```bash
# Take screenshots
httpx -l urls.txt -screenshot

# Screenshot with output directory
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Headless options
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtering & Matching

### Filter by Status Code

```bash
# Match status codes
httpx -l urls.txt -mc 200,301,302

# Filter status codes
httpx -l urls.txt -fc 404,403,500

# Match all, filter specific
httpx -l urls.txt -mc all -fc 404
```

### Filter by Size

```bash
# Match content length
httpx -l urls.txt -ml 1234

# Filter content length
httpx -l urls.txt -fl 0

# Match lines
httpx -l urls.txt -mlc 100

# Filter lines
httpx -l urls.txt -flc 50
```

### Filter by Content

```bash
# Match response body (regex)
httpx -l urls.txt -mr "admin|login"

# Filter response body (regex)
httpx -l urls.txt -fr "error|not found"

# Match string
httpx -l urls.txt -ms "dashboard"
```

### Filter by Technology

```bash
# Match technology
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Filter technology
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Output Options

### Output Formats

```bash
# Plain text
httpx -l urls.txt -o results.txt

# JSON output
httpx -l urls.txt -json -o results.json

# JSON lines
httpx -l urls.txt -jsonl -o results.jsonl

# CSV output
httpx -l urls.txt -csv -o results.csv
```

### Output Fields

```bash
# Store response
httpx -l urls.txt -sr -srd ./responses/

# Store screenshots
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Custom output
httpx -l urls.txt -o output.txt -sc -title -td
```

### Silent & Verbose

```bash
# Silent (URLs only)
httpx -l urls.txt -silent

# Verbose
httpx -l urls.txt -v

# Debug
httpx -l urls.txt -debug

# No color
httpx -l urls.txt -nc
```

---

## ⚡ Advanced Features

### Threading & Rate Limiting

```bash
# Set threads (default: 50)
httpx -l urls.txt -threads 100

# Rate limit
httpx -l urls.txt -rate-limit 100

# Timeout
httpx -l urls.txt -timeout 10

# Retries
httpx -l urls.txt -retries 3
```

### Proxy Support

```bash
# HTTP proxy
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS proxy
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Custom Headers

```bash
# Custom header
httpx -l urls.txt -H "Authorization: Bearer token123"

# Multiple headers
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Request Methods

```bash
# HEAD request (faster)
httpx -l urls.txt -method HEAD

# Custom method
httpx -l urls.txt -method OPTIONS

# POST with body
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Resume & Pagination

```bash
# Resume from last position
httpx -l urls.txt -resume

# Store progress
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Bug Bounty Workflow

### Complete Recon Pipeline

```bash
# Step 1: Subdomain enumeration
subfinder -d target.com -o subs.txt

# Step 2: Probe live hosts with httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Step 3: Vulnerability scan
nuclei -l live.txt -severity critical,high

# One-liner
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Detailed Reconnaissance

```bash
# Full info gathering
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Screenshot All Live Hosts

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Find Interesting Endpoints

```bash
# Find admin panels
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Find API endpoints
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Check specific paths
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Technology-Specific Hunting

```bash
# Find WordPress sites
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Find Nginx servers
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Real-World Examples

### Example 1: Basic Probe
```bash
echo "https://example.com" | httpx -sc -title
```

### Example 2: Full Recon from Subdomains
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Example 3: Screenshot All Hosts
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Example 4: Find Live Hosts Only
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Example 5: Technology Detection
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Example 6: With Proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Example 7: Custom Ports
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Example 8: Filter 200 Only
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Example 9: JSON Output for Processing
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Example 10: Complete Pipeline
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic probe | `httpx -u url` |
| From file | `httpx -l urls.txt` |
| Status code | `httpx -l urls.txt -sc` |
| Page title | `httpx -l urls.txt -title` |
| Tech detect | `httpx -l urls.txt -td` |
| IP address | `httpx -l urls.txt -ip` |
| Screenshot | `httpx -l urls.txt -screenshot` |
| JSON output | `httpx -l urls.txt -json -o out.json` |

### Common Options

| Option | Description |
|--------|-------------|
| `-u` | Single URL |
| `-l` | URL list file |
| `-sc` | Status code |
| `-title` | Page title |
| `-td` | Technology detect |
| `-ip` | IP address |
| `-cdn` | CDN detection |
| `-server` | Web server |
| `-mc` | Match codes |
| `-fc` | Filter codes |
| `-json` | JSON output |
| `-silent` | Silent mode |

### Common Pipelines

| Pipeline | Command |
|----------|---------|
| Live hosts | `subfinder -d target.com \| httpx -silent` |
| With info | `subfinder -d target.com \| httpx -sc -title -td` |
| To nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Use with Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Get Full Picture**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Screenshot for Triage**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **JSON for Processing**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Performance Tips

```bash
# High speed
httpx -l hosts.txt -threads 100 -timeout 5

# Low and slow
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** httpx makes HTTP requests to targets. Only use on authorized systems.
> 
> - ✅ Use on programs you have permission to test
> - ✅ Bug bounty targets in scope
> - ✅ Your own domains
> - ❌ Never probe unauthorized targets
> - ❌ Respect rate limits
> 
> **Unauthorized probing may be illegal.**

---

## 📚 Resources

### Official Resources
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/httpx/)

### Related Cheatsheets
- [Subfinder](../Subfinder/README.md)
- [Nuclei](../Nuclei/README.md)
- [ffuf](../ffuf/README.md)

---

<p align="center">
  <b>🌐 Probe Everything!</b><br>
  <i>The essential HTTP toolkit for bug bounty</i>
</p>
