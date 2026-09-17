# 🔍 Subfinder - Complete Cheatsheet

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Fast Subdomain Discovery Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Fast passive subdomain enumeration tool by ProjectDiscovery</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Subdomain Discovery](#-subdomain-discovery)
- [Data Sources](#-data-sources)
- [API Configuration](#-api-configuration)
- [Output Options](#-output-options)
- [Advanced Features](#-advanced-features)
- [Bug Bounty Workflow](#-bug-bounty-workflow)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Subfinder** is a fast passive subdomain enumeration tool that discovers subdomains using multiple sources including search engines, paste sites, and intelligence platforms.

### Key Features

| Feature | Description |
|---------|-------------|
| **Fast** | Go-based, highly concurrent |
| **Passive** | No direct target interaction |
| **Multiple Sources** | 40+ data sources |
| **API Integration** | Premium source support |
| **Pipeline Friendly** | Works with other tools |
| **JSON Output** | Structured output support |

### Subfinder vs Other Tools

| Feature | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Speed** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Sources** | 40+ | 50+ | 10+ |
| **Active Scanning** | ❌ No | ✅ Yes | ❌ No |
| **API Support** | ✅ Yes | ✅ Yes | ❌ No |
| **Ease of Use** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Installation

### Using Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Binary Download
```bash
# Download from releases
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Verify Installation
```bash
subfinder -version
subfinder -h
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
subfinder [options] -d <domain>
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-d` | Target domain |
| `-dL` | File with list of domains |
| `-o` | Output file |
| `-oJ` | JSON output |
| `-silent` | Silent mode (only subdomains) |
| `-v` | Verbose output |
| `-t` | Number of threads |
| `-all` | Use all sources |

### Quick Start
```bash
# Basic enumeration
subfinder -d example.com

# Silent output (clean list)
subfinder -d example.com -silent

# Save to file
subfinder -d example.com -o subdomains.txt

# Multiple domains
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Subdomain Discovery

### Single Domain

```bash
# Basic scan
subfinder -d target.com

# Verbose output
subfinder -d target.com -v

# Silent (subdomains only)
subfinder -d target.com -silent

# Show sources
subfinder -d target.com -cs
```

### Multiple Domains

```bash
# From file
subfinder -dL domains.txt

# Output to file
subfinder -dL domains.txt -o all_subdomains.txt

# Silent mode for piping
subfinder -dL domains.txt -silent | httpx
```

### Recursive Enumeration

```bash
# Enable recursive (find sub-subdomains)
subfinder -d target.com -recursive

# Recursive with all sources
subfinder -d target.com -recursive -all
```

---

## 📊 Data Sources

### Free Sources (Default)

Subfinder uses these sources by default (no API key required):
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Premium Sources (API Required)

These sources require API keys for better results:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Source Management

```bash
# List all sources
subfinder -ls

# Use specific sources only
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Exclude specific sources
subfinder -d target.com -es shodan,securitytrails

# Use all sources (including slow ones)
subfinder -d target.com -all

# Show which source found each subdomain
subfinder -d target.com -cs
```

---

## 🔑 API Configuration

### Config File Location

```bash
# Default location
~/.config/subfinder/provider-config.yaml

# Or specify custom
subfinder -d target.com -pc /path/to/config.yaml
```

### Config File Template

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### Getting API Keys

| Source | Free Tier | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Yes | https://www.virustotal.com |
| **SecurityTrails** | ✅ Yes (limited) | https://securitytrails.com |
| **Shodan** | ✅ Yes (limited) | https://shodan.io |
| **Censys** | ✅ Yes | https://censys.io |
| **Chaos** | ✅ Free (limited) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Free | https://github.com/settings/tokens |

### Recommended Free Setup

```yaml
# Minimum recommended for bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Output Options

### Output Formats

```bash
# Plain text (default)
subfinder -d target.com -o subs.txt

# JSON output
subfinder -d target.com -oJ -o subs.json

# JSON lines
subfinder -d target.com -oJL -o subs.jsonl

# CSV format
subfinder -d target.com -oD -o subs.csv
```

### Output Filtering

```bash
# Silent mode (clean output)
subfinder -d target.com -silent

# Show only new subdomains (exclude known)
subfinder -d target.com -exclude-subdomains known.txt

# No color
subfinder -d target.com -nc
```

### Piping Output

```bash
# Pipe to httpx
subfinder -d target.com -silent | httpx -silent

# Pipe to nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Save and process
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Advanced Features

### Threading & Performance

```bash
# Set threads (default: 10)
subfinder -d target.com -t 50

# Rate limiting
subfinder -d target.com -rate-limit 100

# Timeout
subfinder -d target.com -timeout 30
```

### Matching & Filtering

```bash
# Match specific patterns
subfinder -d target.com -match "api,dev,stage"

# Filter patterns
subfinder -d target.com -filter "www,mail"
```

### Resolver Options

```bash
# Use custom resolvers
subfinder -d target.com -rL resolvers.txt

# Resolve subdomains
subfinder -d target.com -nW
```

### Proxy Support

```bash
# HTTP proxy
subfinder -d target.com -proxy http://127.0.0.1:8080

# SOCKS proxy
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Bug Bounty Workflow

### Complete Recon Pipeline

```bash
# Step 1: Subdomain enumeration
subfinder -d target.com -all -o subs.txt

# Step 2: Check live hosts
cat subs.txt | httpx -o live.txt

# Step 3: Scan with nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# One-liner
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Multi-Domain Workflow

```bash
# Enumerate all program domains
subfinder -dL scope.txt -all -o all-subs.txt

# Process results
cat all-subs.txt | httpx -silent | nuclei
```

### Continuous Monitoring

```bash
# Save baseline
subfinder -d target.com -silent > baseline.txt

# Later, find new subdomains
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Integration with Other Tools

```bash
# With httpx (live hosts)
subfinder -d target.com -silent | httpx

# With ffuf (directory fuzzing)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# With nmap (port scanning)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Complete pipeline
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Real-World Examples

### Example 1: Basic Enumeration
```bash
subfinder -d hackerone.com -silent
```

### Example 2: With All Sources
```bash
subfinder -d target.com -all -v
```

### Example 3: Save Results
```bash
subfinder -d target.com -o target-subs.txt
```

### Example 4: Multiple Domains
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Example 5: Show Sources
```bash
subfinder -d target.com -cs
```

### Example 6: JSON Output
```bash
subfinder -d target.com -oJ -o results.json
```

### Example 7: With Proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Example 8: High Performance
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Example 9: Full Bug Bounty Pipeline
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Example 10: Recursive Discovery
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic scan | `subfinder -d target.com` |
| Silent output | `subfinder -d target.com -silent` |
| Save to file | `subfinder -d target.com -o subs.txt` |
| All sources | `subfinder -d target.com -all` |
| Show sources | `subfinder -d target.com -cs` |
| Multiple domains | `subfinder -dL domains.txt` |
| JSON output | `subfinder -d target.com -oJ` |
| List sources | `subfinder -ls` |

### Common Options

| Option | Description |
|--------|-------------|
| `-d` | Target domain |
| `-dL` | Domain list file |
| `-o` | Output file |
| `-oJ` | JSON output |
| `-silent` | Silent mode |
| `-all` | Use all sources |
| `-cs` | Show source for each subdomain |
| `-t` | Threads |
| `-recursive` | Recursive enumeration |

### Common Pipelines

| Pipeline | Command |
|----------|---------|
| Live hosts | `subfinder -d target.com -silent \| httpx` |
| Full recon | `subfinder -d target.com \| httpx \| nuclei` |
| Port scan | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Configure API Keys**
   ```bash
   # Better results with APIs
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Use All Sources for Completeness**
   ```bash
   subfinder -d target.com -all
   ```

3. **Combine with Other Tools**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Monitor New Subdomains**
   ```bash
   # Daily check for new assets
   subfinder -d target.com -silent > today.txt
   ```

### Performance Tips

```bash
# High thread count
subfinder -d target.com -t 100 -all

# Timeout for slow sources
subfinder -d target.com -timeout 60
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Subfinder performs passive reconnaissance and doesn't interact directly with targets. However, subsequent tools in your pipeline may.
> 
> - ✅ Use on authorized programs
> - ✅ Bug bounty targets in scope
> - ✅ Your own domains
> - ❌ Never enumerate unauthorized targets
> - ❌ Respect program scope
> 
> **Always ensure you have authorization.**

---

## 📚 Resources

### Official Resources
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/subfinder/)

### Related Cheatsheets
- [Nuclei](../Nuclei/README.md)
- [ffuf](../ffuf/README.md)
- [Nmap](../Nmap/README.md)

---

<p align="center">
  <b>🔍 Find Every Subdomain!</b><br>
  <i>The first step in bug bounty recon</i>
</p>
