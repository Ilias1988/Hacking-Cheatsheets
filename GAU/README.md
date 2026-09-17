# 🔗 GAU - Get All URLs

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Fast URL Harvesting
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Table of Contents

- [What is GAU](#-what-is-gau)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Providers](#-providers)
- [Filtering](#-filtering)
- [Integration](#-integration)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is GAU

**GAU** (Get All URLs) fetches known URLs from multiple sources:

- 🔍 **Wayback Machine** - Historical URLs
- 🌐 **Common Crawl** - Web crawl data
- 📡 **AlienVault OTX** - Threat intelligence URLs
- 🔒 **URLScan.io** - Scanned URLs

### Why GAU?

| Benefit | Description |
|---------|-------------|
| **Passive Recon** | No direct target contact |
| **Historical Data** | Find old/forgotten endpoints |
| **Fast** | Parallel fetching |
| **Multiple Sources** | Comprehensive results |

---

## 🚀 Installation

```bash
# Go install (recommended)
go install github.com/lc/gau/v2/cmd/gau@latest

# From source
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Verify
gau --version
```

---

## 📖 Basic Usage

### Single Domain

```bash
# Basic usage
gau example.com

# With output file
gau example.com -o urls.txt

# Silent mode (no banner)
gau --subs example.com
```

### Multiple Domains

```bash
# From stdin
echo "example.com" | gau

# Multiple domains
cat domains.txt | gau

# From file
gau --threads 5 < domains.txt
```

### Include Subdomains

```bash
# Include subdomains in results
gau --subs example.com
```

### Threading

```bash
# Set threads (default: 1)
gau --threads 5 example.com

# Adjust for rate limiting
gau --threads 2 example.com
```

---

## 🌐 Providers

### Available Providers

| Provider | Description | Default |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Select Providers

```bash
# Use specific providers only
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Exclude provider
gau --blacklist commoncrawl example.com
```

### Provider Configuration

```bash
# Config file location
~/.gau.toml

# Sample config
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filtering

### Filter by Status Code

```bash
# Only URLs that returned 200
gau --mc 200 example.com

# Multiple status codes
gau --mc 200,301,302 example.com

# Filter OUT specific codes
gau --fc 404,500 example.com
```

### Filter by Extension

```bash
# Match extensions
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Filter OUT extensions
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Filter by MIME Type

```bash
# Filter by content type
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Output Format

```bash
# JSON output
gau --json example.com

# Verbose mode
gau --verbose example.com
```

---

## 🔗 Integration

### With httpx

```bash
# Check live URLs
gau example.com | httpx -silent

# With status codes
gau example.com | httpx -sc -title
```

### With grep/Parameter Extraction

```bash
# Find URLs with parameters
gau example.com | grep "="

# Find specific patterns
gau example.com | grep -E "\.php\?|\.asp\?"

# GF patterns
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### With urldedupe

```bash
# Remove duplicates
gau example.com | urldedupe

# Alternative: sort -u
gau example.com | sort -u
```

### With uro

```bash
# Smart URL deduplication
gau example.com | uro
```

### Full Pipeline

```bash
# Complete URL collection pipeline
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Quick Reference

### Basic Commands

| Command | Description |
|---------|-------------|
| `gau domain.com` | Get URLs for domain |
| `gau --subs domain.com` | Include subdomains |
| `gau -o urls.txt domain.com` | Output to file |
| `gau --threads 5 domain.com` | Multi-threaded |

### Filtering Options

| Flag | Description |
|------|-------------|
| `--mc 200` | Match status code |
| `--fc 404` | Filter status code |
| `--mt "regex"` | Match regex |
| `--ft "regex"` | Filter regex |
| `--json` | JSON output |

### Provider Options

| Flag | Description |
|------|-------------|
| `--providers wayback,otx` | Use specific providers |
| `--blacklist commoncrawl` | Exclude provider |

### Useful One-Liners

```bash
# Get all JS files
gau example.com | grep -E "\.js$" | sort -u

# Find API endpoints
gau example.com | grep -iE "/api/|/v[0-9]/"

# Find parameters
gau example.com | grep "?" | grep "=" | sort -u

# Find potential SQLi
gau example.com | gf sqli

# Find potential XSS
gau example.com | gf xss

# Live URLs with params
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Bug Bounty Workflow

```bash
# 1. Collect all URLs
gau --subs --threads 5 target.com > all_urls.txt

# 2. Filter interesting URLs
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Check live
cat params.txt | httpx -silent > live_params.txt

# 4. Find vulnerabilities
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Test
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Resources

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Related Cheatsheets
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/README.md)
- [Subfinder](../Subfinder/README.md)
- [httpx](../httpx/README.md)

---

<p align="center">
  <b>🔗 Harvest All URLs!</b><br>
  <i>GAU - Passive URL Discovery</i>
</p>
