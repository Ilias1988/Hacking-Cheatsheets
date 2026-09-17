# 🔍 Arjun - Hidden Parameter Discovery

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP Parameter Discovery Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Table of Contents

- [What is Arjun](#-what-is-arjun)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Discovery Modes](#-discovery-modes)
- [Advanced Options](#-advanced-options)
- [Output Options](#-output-options)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Arjun

**Arjun** discovers hidden HTTP parameters on web applications:

- 🔍 **Parameter Discovery** - Find hidden GET/POST params
- 🎯 **JSON Support** - Discover JSON parameters
- 📡 **Multi-Method** - GET, POST, JSON modes
- ⚡ **Fast** - Highly concurrent requests

### Why Arjun?

| Feature | Benefit |
|---------|---------|
| Hidden Parameters | Find unreferenced endpoints |
| Multiple Formats | GET, POST, JSON |
| Passive Mode | Check JS files |
| Import from Burp | Integrate with existing workflow |

---

## 🚀 Installation

```bash
# pip install (recommended)
pip3 install arjun

# From source
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Verify
arjun --help
```

---

## 📖 Basic Usage

### Single URL

```bash
# Basic parameter discovery
arjun -u https://example.com/endpoint

# With output file
arjun -u https://example.com/endpoint -o results.txt
```

### Multiple URLs

```bash
# From file
arjun -i urls.txt

# From stdin
cat urls.txt | arjun --stdin
```

### Specify HTTP Method

```bash
# GET parameters (default)
arjun -u https://example.com/search -m GET

# POST parameters
arjun -u https://example.com/api -m POST

# JSON body
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Discovery Modes

### GET Parameters

```bash
# Discover GET parameters
arjun -u https://example.com/search?known=value -m GET

# Example output:
# [param] id
# [param] user
# [param] debug
```

### POST Parameters

```bash
# Discover POST form parameters
arjun -u https://example.com/login -m POST

# With existing data
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### JSON Parameters

```bash
# Discover JSON body parameters
arjun -u https://example.com/api/v1/users -m JSON

# With base JSON
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Combined Discovery

```bash
# All methods
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

## ⚙️ Advanced Options

### Custom Wordlist

```bash
# Use custom wordlist
arjun -u https://example.com -w /path/to/wordlist.txt

# Use SecLists params
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Threading & Rate Limiting

```bash
# Set threads (default: 2)
arjun -u https://example.com -t 10

# Rate limit (requests per second)
arjun -u https://example.com --rate-limit 10

# Delay between requests (seconds)
arjun -u https://example.com --delay 1
```

### Custom Headers

```bash
# Add header
arjun -u https://example.com --headers "Authorization: Bearer token"

# Multiple headers
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# From file
arjun -u https://example.com -H headers.txt
```

### Include Known Parameters

```bash
# Include known parameters
arjun -u https://example.com/search --include 'q=test&page=1'

# For POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Passive Mode

```bash
# Extract from JavaScript files
arjun -u https://example.com --passive

# This checks JS files for potential parameter names
```

### Disable Redirects

```bash
# Don't follow redirects
arjun -u https://example.com --disable-redirects
```

### Timeout

```bash
# Set timeout (seconds)
arjun -u https://example.com --timeout 15
```

### Stable Mode

```bash
# More reliable (slower)
arjun -u https://example.com --stable
```

---

## 📤 Output Options

### Output Formats

```bash
# Text output (default)
arjun -u https://example.com -oT results.txt

# JSON output
arjun -u https://example.com -oJ results.json

# Both
arjun -u https://example.com -oT text.txt -oJ json.json
```

### JSON Output Example

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Import from Burp

```bash
# Import Burp requests
arjun --import burp_export.xml

# Process Burp file
arjun -i burp_requests.txt --burp
```

---

## 📊 Quick Reference

### Basic Commands

| Command | Description |
|---------|-------------|
| `arjun -u URL` | Discover params |
| `arjun -u URL -m POST` | POST method |
| `arjun -u URL -m JSON` | JSON body |
| `arjun -i file.txt` | Multiple URLs |
| `arjun -u URL -oJ out.json` | JSON output |

### Method Options

| Flag | Description |
|------|-------------|
| `-m GET` | GET parameters |
| `-m POST` | POST form data |
| `-m JSON` | JSON body |
| `-m GET,POST` | Multiple methods |

### Performance Options

| Flag | Description |
|------|-------------|
| `-t 10` | Threads |
| `--rate-limit 10` | Requests/second |
| `--delay 1` | Delay between requests |
| `--timeout 15` | Request timeout |

### Discovery Options

| Flag | Description |
|------|-------------|
| `-w wordlist.txt` | Custom wordlist |
| `--include 'a=b'` | Include params |
| `--passive` | Extract from JS |
| `--stable` | Reliable mode |

---

## 🎯 Bug Bounty Workflows

### Basic Discovery

```bash
# Quick param discovery
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### With Custom Wordlist

```bash
# Comprehensive discovery
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Authenticated Testing

```bash
# With session
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Mass Discovery

```bash
# Multiple endpoints
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Or with file
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Pipeline Integration

```bash
# Find endpoints, then discover params
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Testing Discovered Parameters

```bash
# After discovery, test for vulns
# 1. Discover params
arjun -u https://target.com/search -oJ params.json

# 2. Extract param names
cat params.json | jq -r '.[].params[].name'

# 3. Test with ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Pro Tips

1. **Start passive** - Check JS files first
2. **Use good wordlists** - Burp parameter names recommended
3. **Rate limit** - Don't get blocked
4. **Test all methods** - GET, POST, and JSON
5. **Check headers** - X-Custom-Param, X-Debug, etc.
6. **Authenticated** - Test logged-in areas

### Common Hidden Parameters

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Resources

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp Parameter Names](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Related Cheatsheets
- [Bug Bounty Methodology](../Bug-Bounty-Methodology/README.md)
- [ffuf](../ffuf/README.md)
- [Burp Suite](../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 Discover Hidden Parameters!</b><br>
  <i>Arjun - The Parameter Hunter</i>
</p>
