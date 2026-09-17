# 🌐 Amass - Attack Surface Mapping

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN">
</p>

---

## 📋 Table of Contents

- [What is Amass](#-what-is-amass)
- [Installation](#-installation)
- [Enumeration Mode](#-enumeration-mode)
- [Intel Mode](#-intel-mode)
- [DNS Mode](#-dns-mode)
- [Configuration](#-configuration)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Amass

**Amass** is an OWASP project for network mapping and external asset discovery:

- 🔍 **Subdomain Enumeration** - The most comprehensive tool
- 🏢 **ASN Discovery** - Find organization's IP ranges
- 📡 **DNS Intelligence** - Passive & active recon
- 🗺️ **Attack Surface Mapping** - Visualize infrastructure

### Why Amass?

| Feature | Benefit |
|---------|---------|
| 55+ Data Sources | Most comprehensive results |
| Passive & Active | Choose your noise level |
| ASN/Org Intel | Find hidden infrastructure |
| Graph Database | Visualize relationships |

---

## 🚀 Installation

```bash
# Go install
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Verify
amass -version
```

---

## 🔍 Enumeration Mode

### Basic Subdomain Enumeration

```bash
# Passive enumeration (no direct contact with target)
amass enum -passive -d example.com

# Active enumeration (DNS brute forcing)
amass enum -active -d example.com

# Brute force with wordlist
amass enum -active -brute -d example.com -w wordlist.txt
```

### Output Options

```bash
# Save to file
amass enum -passive -d example.com -o subdomains.txt

# JSON output
amass enum -passive -d example.com -json output.json

# Save to directory
amass enum -passive -d example.com -dir ./amass_output/
```

### Multiple Targets

```bash
# Multiple domains
amass enum -passive -d example.com -d target.com

# From file
amass enum -passive -df domains.txt -o results.txt
```

### Advanced Enumeration

```bash
# Set timeout (minutes)
amass enum -passive -d example.com -timeout 30

# Limit sources
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Exclude sources
amass enum -passive -d example.com -exclude Google,Bing

# Show data sources
amass enum -passive -d example.com -src
```

### Recursive Enumeration

```bash
# Enable recursive search
amass enum -passive -d example.com -r

# Set recursion depth
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 Intel Mode

> Intel mode discovers root domains, ASNs, and CIDR ranges associated with organizations.

### Find Organization Assets

```bash
# By organization name
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Reverse WHOIS
amass intel -whois -d example.com
```

### ASN Discovery

```bash
# Find ASN for domain
amass intel -d example.com

# Enumerate by ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Multiple ASNs
amass intel -asn 13335,15169
```

### CIDR Range

```bash
# Find assets in CIDR
amass intel -active -cidr 192.168.1.0/24

# Multiple CIDRs
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP Range

```bash
# IP address range
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 DNS Mode

### DNS Queries

```bash
# Basic DNS lookup
amass dns -d example.com

# Specific record types
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Resolve Subdomains

```bash
# Resolve discovered subdomains
amass dns -df subdomains.txt
```

---

## ⚙️ Configuration

### Config File Location

```bash
# Default location
~/.config/amass/config.yaml

# Specify custom config
amass enum -config ./my_config.yaml -d example.com
```

### Sample Config File

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Data Sources with API Keys

| Source | Free Tier | API Required |
|--------|-----------|--------------|
| Crt.sh | ✅ | No |
| SecurityTrails | ✅ | Yes |
| Shodan | ✅ | Yes |
| VirusTotal | ✅ | Yes |
| Censys | ✅ | Yes |
| PassiveTotal | Limited | Yes |
| BinaryEdge | Limited | Yes |

---

## 📊 Quick Reference

### Enumeration Commands

| Command | Description |
|---------|-------------|
| `amass enum -passive -d domain.com` | Passive subdomain enum |
| `amass enum -active -d domain.com` | Active DNS enum |
| `amass enum -active -brute -d domain.com` | Brute force subdomains |
| `amass enum -d domain.com -o out.txt` | Save to file |
| `amass enum -d domain.com -json out.json` | JSON output |

### Intel Commands

| Command | Description |
|---------|-------------|
| `amass intel -org "Company"` | Find assets by org name |
| `amass intel -asn 12345` | Enumerate by ASN |
| `amass intel -cidr 10.0.0.0/8` | Enumerate CIDR range |
| `amass intel -whois -d domain.com` | Reverse WHOIS |

### Essential Flags

| Flag | Description |
|------|-------------|
| `-d` | Target domain |
| `-df` | Domains from file |
| `-o` | Output file |
| `-passive` | Passive only (no DNS) |
| `-active` | Enable active DNS |
| `-brute` | DNS brute forcing |
| `-src` | Show data source |
| `-timeout` | Timeout in minutes |

---

## 🎯 Bug Bounty Workflow

```bash
# 1. Passive enumeration (safe)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Active with brute force
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Find organization assets
amass intel -org "Target Inc" -o org_domains.txt

# 4. Combine results
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Resolve live hosts
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Resources

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass User Guide](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Related Cheatsheets
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/README.md)
- [Subfinder](../Subfinder/README.md)
- [httpx](../httpx/README.md)

---

<p align="center">
  <b>🌐 Map the Attack Surface!</b><br>
  <i>Amass - The most comprehensive subdomain tool</i>
</p>
