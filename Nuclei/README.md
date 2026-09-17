# 🎯 Nuclei - Complete Cheatsheet

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Fast Template-Based Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 The #1 template-based vulnerability scanner for bug bounty hunters</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Template Categories](#-template-categories)
- [Scanning Targets](#-scanning-targets)
- [Template Selection](#-template-selection)
- [Output Options](#-output-options)
- [Advanced Features](#-advanced-features)
- [Custom Templates](#-custom-templates)
- [Bug Bounty Workflow](#-bug-bounty-workflow)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Nuclei** is a fast, template-based vulnerability scanner developed by ProjectDiscovery. It's the go-to tool for bug bounty hunters and security researchers.

### Key Features

| Feature | Description |
|---------|-------------|
| **9000+ Templates** | CVEs, panels, misconfigs, takeovers |
| **Fast & Efficient** | Parallel scanning with rate limiting |
| **Template-Based** | YAML templates for easy customization |
| **Community-Driven** | Constantly updated templates |
| **Multiple Protocols** | HTTP, DNS, TCP, SSL, Headless |
| **Easy Integration** | Works with other tools (httpx, subfinder) |

### Template Types

| Type | Description | Count |
|------|-------------|-------|
| **CVE** | Known vulnerabilities | 2000+ |
| **Exposed Panels** | Admin panels, dashboards | 500+ |
| **Misconfigurations** | Security misconfigs | 300+ |
| **Default Logins** | Default credentials | 200+ |
| **Takeovers** | Subdomain takeover | 100+ |
| **Technologies** | Technology detection | 1000+ |
| **Exposures** | Sensitive file exposure | 500+ |

---

## 📥 Installation

### Using Go (Recommended)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Binary Download
```bash
# Download from releases
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Update Templates
```bash
# Update nuclei and templates
nuclei -ut

# Update only templates
nuclei -update-templates
```

### Verify Installation
```bash
nuclei -version
nuclei -tl              # List templates
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
nuclei [options] -target <url>
```

### Essential Options

| Option | Description |
|--------|-------------|
| `-u` | Single target URL |
| `-l` | File with list of URLs |
| `-t` | Template(s) to run |
| `-tags` | Templates with specific tags |
| `-severity` | Filter by severity |
| `-o` | Output file |
| `-silent` | Silent mode |
| `-v` | Verbose output |

### Quick Start
```bash
# Scan single target
nuclei -u https://example.com

# Scan list of targets
nuclei -l urls.txt

# Scan with specific templates
nuclei -u https://example.com -t cves/

# Scan with tags
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Template Categories

### CVE Templates

```bash
# All CVE templates
nuclei -u target.com -t cves/

# Specific CVE year
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Critical CVEs only
nuclei -u target.com -t cves/ -severity critical

# Specific CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Exposed Panels

```bash
# All panel detection
nuclei -u target.com -t exposed-panels/

# Specific panels
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Misconfigurations

```bash
# All misconfigs
nuclei -u target.com -t misconfiguration/

# Specific types
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Default Logins

```bash
# All default credentials
nuclei -u target.com -t default-logins/

# Specific vendors
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Subdomain Takeover

```bash
# All takeover checks
nuclei -u target.com -t takeovers/

# Specific providers
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Technology Detection

```bash
# Technology detection
nuclei -u target.com -t technologies/

# CMS detection
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Exposures

```bash
# Sensitive file exposure
nuclei -u target.com -t exposures/

# Specific exposures
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Scanning Targets

### Single Target

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Multiple Targets

```bash
# From file
nuclei -l targets.txt

# From stdin (pipeline)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Multiple URLs inline
nuclei -u https://site1.com -u https://site2.com
```

### With Other Tools

```bash
# With subfinder (subdomains)
subfinder -d example.com | nuclei

# With httpx (live hosts)
subfinder -d example.com | httpx | nuclei

# With katana (crawled URLs)
katana -u https://example.com | nuclei

# Complete recon pipeline
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

## 🏷️ Template Selection

### By Template Path

```bash
# Single template
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Directory of templates
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Multiple directories
nuclei -u target.com -t cves/ -t exposed-panels/

# Custom template path
nuclei -u target.com -t /path/to/custom/templates/
```

### By Tags

```bash
# Single tag
nuclei -u target.com -tags cve

# Multiple tags (OR)
nuclei -u target.com -tags cve,rce,sqli

# Common tags
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### By Severity

```bash
# Single severity
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Multiple severities
nuclei -u target.com -severity critical,high

# Exclude severity
nuclei -u target.com -exclude-severity info,low
```

### Exclude Templates

```bash
# Exclude specific templates
nuclei -u target.com -exclude-templates cves/2020/

# Exclude by tags
nuclei -u target.com -exclude-tags dos,fuzz

# Exclude by ID
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Output Options

### Output Formats

```bash
# Standard output
nuclei -u target.com -o results.txt

# JSON output
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Output Filtering

```bash
# Silent mode (only results)
nuclei -u target.com -silent

# No color
nuclei -u target.com -nc

# Verbose
nuclei -u target.com -v

# Debug
nuclei -u target.com -debug
```

### Export Options

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Markdown report
nuclei -u target.com -me reports/
```

---

## ⚡ Advanced Features

### Rate Limiting

```bash
# Requests per second
nuclei -u target.com -rate-limit 100

# Bulk size
nuclei -u target.com -bulk-size 25

# Concurrency
nuclei -u target.com -c 50

# Template concurrency
nuclei -u target.com -rl 100 -c 50
```

### Proxy Support

```bash
# HTTP proxy
nuclei -u target.com -proxy http://127.0.0.1:8080

# SOCKS proxy
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Burp Suite proxy
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Authentication

```bash
# Header authentication
nuclei -u target.com -H "Authorization: Bearer token123"

# Cookie authentication
nuclei -u target.com -H "Cookie: session=abc123"

# Multiple headers
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Interactsh Integration

```bash
# Use interactsh for OOB testing
nuclei -u target.com -interactsh-server https://interact.sh

# Custom interactsh server
nuclei -u target.com -iserver https://my-interactsh.com
```

### Headless Mode

```bash
# Enable headless browser
nuclei -u target.com -headless

# Headless templates only
nuclei -u target.com -t headless/
```

### Workflows

```bash
# Run workflow
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# List workflows
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Custom Templates

### Template Structure

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Template Examples

#### Detect Specific Page
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Check Header
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Validate Template

```bash
# Validate template syntax
nuclei -validate -t my-template.yaml

# Test template
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Bug Bounty Workflow

### Complete Recon Pipeline

```bash
# Step 1: Subdomain enumeration
subfinder -d target.com -o subs.txt

# Step 2: Resolve live hosts
cat subs.txt | httpx -o live.txt

# Step 3: Run nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# One-liner
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Priority Scanning

```bash
# Phase 1: Critical only (fast)
nuclei -l targets.txt -severity critical -o critical.txt

# Phase 2: High severity
nuclei -l targets.txt -severity high -o high.txt

# Phase 3: CVEs
nuclei -l targets.txt -t cves/ -o cves.txt

# Phase 4: Full scan
nuclei -l targets.txt -o all.txt
```

### Targeted Scanning

```bash
# WordPress targets
nuclei -l wp-sites.txt -tags wordpress

# API endpoints
nuclei -l apis.txt -tags api

# Login pages
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Real-World Examples

### Example 1: Full Scan
```bash
nuclei -u https://target.com -o results.txt
```

### Example 2: Critical CVEs Only
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Example 3: Bug Bounty Quick Scan
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Example 4: Subdomain Takeover Check
```bash
cat subs.txt | nuclei -t takeovers/
```

### Example 5: Technology Detection
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Example 6: With Proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Example 7: Authenticated Scan
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Example 8: Rate Limited Scan
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Example 9: JSON Output for Processing
```bash
nuclei -u https://target.com -json -o results.json
```

### Example 10: Complete Pipeline
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic scan | `nuclei -u target.com` |
| With templates | `nuclei -u target.com -t cves/` |
| By tags | `nuclei -u target.com -tags cve,rce` |
| By severity | `nuclei -u target.com -severity critical` |
| From list | `nuclei -l urls.txt` |
| Update templates | `nuclei -ut` |
| Silent output | `nuclei -u target.com -silent` |
| JSON output | `nuclei -u target.com -json -o out.json` |

### Common Tags

| Tag | Description |
|-----|-------------|
| `cve` | CVE vulnerabilities |
| `rce` | Remote code execution |
| `sqli` | SQL injection |
| `xss` | Cross-site scripting |
| `ssrf` | Server-side request forgery |
| `lfi` | Local file inclusion |
| `panel` | Admin panels |
| `takeover` | Subdomain takeover |

### Severity Levels

| Severity | Color | Priority |
|----------|-------|----------|
| `critical` | 🔴 Red | Immediate |
| `high` | 🟠 Orange | High |
| `medium` | 🟡 Yellow | Medium |
| `low` | 🟢 Green | Low |
| `info` | 🔵 Blue | Informational |

---

## 💡 Tips & Best Practices

### Performance Tips

```bash
# Optimal settings for large scans
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Silent for pipelines
nuclei -l targets.txt -silent -o results.txt
```

### Bug Bounty Tips

1. **Start with Critical/High**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Check for Quick Wins**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Use Updated Templates**
   ```bash
   nuclei -ut  # Always update before hunting
   ```

4. **Rate Limit for Stability**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Stealth Tips

```bash
# Slow and quiet
nuclei -l targets.txt -rl 10 -c 2

# Through proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Nuclei is a powerful vulnerability scanner that should only be used for **authorized testing**.
> 
> - ✅ Use on programs you have permission to test
> - ✅ Use in authorized bug bounty programs
> - ✅ Use on your own systems
> - ❌ Never scan without authorization
> - ❌ Respect rate limits and scope
> 
> **Unauthorized scanning is illegal and unethical.**

---

## 📚 Resources

### Official Resources
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Nuclei Templates](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/nuclei/)

### Template Resources
- [Community Templates](https://github.com/projectdiscovery/nuclei-templates)
- [Template Guide](https://docs.projectdiscovery.io/templates/)

### Related Cheatsheets
- [Nmap](../Nmap/README.md)
- [Burp Suite](../Burp-Suite/README.md)
- [SQLMap](../SQLMap/README.md)

---

<p align="center">
  <b>🎯 Master Vulnerability Scanning!</b><br>
  <i>The bug bounty hunter's best friend</i>
</p>
