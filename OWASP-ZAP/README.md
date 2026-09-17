# ⚡ OWASP ZAP - Complete Cheatsheet

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy - Free Security Testing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 The world's most widely used free web security scanner</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Interface Overview](#-interface-overview)
- [Proxy Configuration](#-proxy-configuration)
- [Manual Testing](#-manual-testing)
- [Spider & Crawler](#-spider--crawler)
- [Active Scanning](#-active-scanning)
- [Passive Scanning](#-passive-scanning)
- [Contexts & Sessions](#-contexts--sessions)
- [Fuzzer](#-fuzzer)
- [API & Automation](#-api--automation)
- [Command Line](#-command-line)
- [Add-ons](#-add-ons)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**OWASP ZAP** (Zed Attack Proxy) is a free, open-source penetration testing tool maintained by OWASP. It's designed to find security vulnerabilities in web applications.

### Why ZAP?

| Feature | Description |
|---------|-------------|
| **Free & Open Source** | No licensing costs, community-driven |
| **Easy to Use** | Beginner-friendly with powerful features |
| **Automated Scanning** | Spider, Active/Passive scanning |
| **Manual Testing** | Intercepting proxy, fuzzer |
| **API Support** | Full REST API for automation |
| **Cross-Platform** | Windows, Linux, macOS, Docker |
| **Extensible** | Marketplace with 100+ add-ons |

### ZAP vs Burp Suite

| Feature | ZAP | Burp Suite |
|---------|-----|------------|
| **Price** | Free | $449+/year |
| **Open Source** | ✅ Yes | ❌ No |
| **Automated Scanning** | ✅ Full | Pro only |
| **API Access** | ✅ Free | Pro only |
| **Active Development** | ✅ Yes | ✅ Yes |
| **Community** | OWASP | PortSwigger |

---

## 📥 Installation

### Windows
```bash
# Download from official site
https://www.zaproxy.org/download/

# Or using Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (pre-installed)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Download DMG from official site
```

### Docker
```bash
# Pull latest image
docker pull ghcr.io/zaproxy/zaproxy:stable

# Run ZAP in daemon mode
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Run with GUI (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Verify Installation
```bash
# Start ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

## 🖥️ Interface Overview

### Main Panels

| Panel | Description |
|-------|-------------|
| **Sites Tree** | Hierarchical view of visited sites |
| **History** | All requests/responses |
| **Alerts** | Found vulnerabilities |
| **Active Scan** | Current scan progress |
| **Spider** | Crawled URLs |
| **Search** | Search across all data |
| **Request/Response** | View details |

### Toolbar Icons

| Icon | Function |
|------|----------|
| 🌐 | Quick Start scan |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Break/Intercept |
| 📊 | Reports |
| ⚙️ | Options |

### Modes

| Mode | Description |
|------|-------------|
| **Safe** | No dangerous operations |
| **Protected** | Only scan in-scope targets |
| **Standard** | Full scanning (default) |
| **ATTACK** | Aggressive scanning |

---

## 🔌 Proxy Configuration

### Browser Configuration

**Default ZAP Proxy:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Firefox Setup
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Check "Also use this proxy for HTTPS"
4. Or use FoxyProxy extension
```

### Import ZAP Certificate
```
1. In browser, go to: http://zap
2. Click "Download ZAP Root CA Certificate"
3. Import to browser:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Configure ZAP Proxy Options
```
Tools → Options → Local Proxies

Settings:
- Address: localhost
- Port: 8080
- Behind NAT: Enable if needed
- Remove unsupported encodings: Enable
```

### Upstream Proxy (Proxy Chain)
```
Tools → Options → Network → Connection

Use proxy chain:
- Address: corporate-proxy.com
- Port: 8080
- Authentication if required
```

---

## 🔧 Manual Testing

### Intercepting Requests (Break)

```
1. Click green "Break" button (🛑) in toolbar
2. Browse the application
3. Modify intercepted request
4. Click "Submit and Continue" or "Drop"

Break Options:
- Break on all requests
- Break on all responses
- Break on specific URL pattern
```

### Request Editor

```
Right-click request → Open/Resend with Request Editor

Features:
- Modify any part of request
- Add/remove headers
- Change HTTP method
- Send and view response
```

### Manual Request
```
Tools → Manual Request Editor

Or: Ctrl+M

Create custom requests from scratch
```

### Encode/Decode/Hash
```
Tools → Encode/Decode/Hash

Supports:
- URL encode/decode
- Base64 encode/decode
- HTML encode/decode
- Hex encode/decode
- MD5, SHA1, SHA256 hashing
```

### Search Functionality
```
Search tab → Enter pattern

Search across:
- URLs
- Requests
- Responses
- Headers
- All
```

---

## 🕷️ Spider & Crawler

### Standard Spider

```
Right-click target → Spider → Spider...

Or: Ctrl+Alt+S

Options:
- Max depth to crawl
- Max children
- Max duration
- Handle parameters
```

### Spider Options
```
Tools → Options → Spider

Settings:
- Max Depth: 5-10 (default: 5)
- Max Children: 10
- Max Duration: 0 (unlimited)
- Thread Count: 5
- Handle forms: Yes
- Post forms: Yes
```

### AJAX Spider

```
For JavaScript-heavy applications:

Right-click target → AJAX Spider → AJAX Spider...

Requires browser:
- Firefox (recommended)
- Chrome
- HtmlUnit (headless)
```

### AJAX Spider Options
```
Tools → Options → AJAX Spider

Settings:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (unlimited)
- Max Duration: 60 minutes
- Click default elements
- Click elements with: onclick, etc.
```

---

## ⚡ Active Scanning

### Start Active Scan

```
Right-click target → Active Scan...

Or: Ctrl+Alt+A

Configure:
- Starting Point
- Context (optional)
- User (for authentication)
- Policy
- Technology
```

### Scan Policies

```
Analyze → Scan Policy Manager

Create/Modify policies:
- Which tests to run
- Threshold (how suspicious)
- Strength (how aggressive)
```

### Policy Categories

| Category | Tests |
|----------|-------|
| **Injection** | SQL, OS Command, LDAP |
| **Client Browser** | XSS, DOM XSS |
| **Server Security** | Remote file inclusion |
| **Information** | Path traversal, disclosure |
| **Miscellaneous** | User-controlled redirects |

### Scan Strength

| Level | Description |
|-------|-------------|
| **Low** | Few requests, fast |
| **Medium** | Balanced |
| **High** | Many requests, thorough |
| **Insane** | Maximum tests (slow) |

### Technology Selection
```
Right-click target → Technology → Select...

Filter scans by technology:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

## 👁️ Passive Scanning

### What is Passive Scanning?

Analyzes traffic without sending additional requests.

```
Automatic - always running on proxied traffic

Finds:
- Security headers missing
- Information disclosure
- Cookie issues
- Weak SSL/TLS
- Private IP disclosure
```

### Passive Scan Rules

```
Analyze → Scan Policy Manager → Passive Scan

Rules include:
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

### Configure Passive Scanning
```
Tools → Options → Passive Scan

Settings:
- Scan only in scope: Recommended
- Max alerts per rule: 10
- Tags: Enable
```

---

## 🎯 Contexts & Sessions

### What are Contexts?

Logical groupings of related URLs for organized testing.

### Create Context
```
Right-click Sites → Include in Context → New Context

Or: File → New Context
```

### Context Settings

| Setting | Purpose |
|---------|---------|
| **Include in Context** | URLs to include |
| **Exclude from Context** | URLs to exclude |
| **Technology** | Server technology |
| **Authentication** | Login method |
| **Session Management** | Cookie/token handling |
| **Users** | Test user accounts |

### Authentication Types

| Type | Use Case |
|------|----------|
| **Form-based** | Login forms |
| **HTTP Basic** | Basic auth |
| **JSON-based** | API authentication |
| **Script-based** | Custom auth |

### Form-Based Authentication
```
1. Record login request in ZAP
2. Right-click → Include in Context
3. Context → Authentication → Form-based
4. Set Login URL
5. Set Login Request POST Data
6. Configure username/password parameters
7. Set logged-in/out indicators
```

### Session Management
```
Context → Session Management

Types:
- Cookie-based (most common)
- HTTP Auth
- Script-based
```

### Users
```
Context → Users → Add

Add test users:
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Fuzzer

### Start Fuzzer
```
1. Select request in History
2. Right-click → Attack → Fuzz...
3. Highlight fuzz location
4. Add payloads
5. Start
```

### Add Fuzz Location
```
1. In Fuzzer dialog, highlight text to fuzz
2. Click "Add..."
3. Select payload type
```

### Payload Types

| Type | Description |
|------|-------------|
| **Strings** | Custom string list |
| **File** | Load from file |
| **File Fuzzers** | Built-in wordlists |
| **Numbers** | Number sequences |
| **Regex** | Generated from regex |
| **Script** | Custom script |

### Built-in Fuzzers
```
Add → File Fuzzers

Categories:
- jbrofuzz (XSS, SQL, etc.)
- dirbuster (directories)
- fuzzdb (comprehensive)
```

### Fuzzer Options
```
Options:
- Concurrent threads
- Delay between requests
- Follow redirects
- Show all results or filtered
```

### Fuzzer Example: Parameter Fuzzing
```
1. Capture: GET /page?id=1
2. Right-click → Fuzz
3. Highlight "1" 
4. Add SQL injection payloads
5. Start fuzzer
6. Analyze results by response length/code
```

---

## 🤖 API & Automation

### Enable API
```
Tools → Options → API

Settings:
- Enabled: Yes
- API Key: (auto-generated or custom)
- Permitted addresses: localhost
```

### API Access
```bash
# API Root
http://localhost:8080/JSON/

# With API key
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Get version
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Common API Endpoints

| Endpoint | Purpose |
|----------|---------|
| `/core/view/alerts/` | Get all alerts |
| `/core/view/hosts/` | Get all hosts |
| `/spider/action/scan/` | Start spider |
| `/ascan/action/scan/` | Start active scan |
| `/core/view/urls/` | Get all URLs |
| `/core/action/shutdown/` | Shutdown ZAP |

### API Examples

```bash
# Spider a URL
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Check spider status
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Start active scan
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Get alerts
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Generate HTML report
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Python API Client
```python
from zapv2 import ZAPv2

# Connect to ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Active Scan
zap.ascan.scan(url='http://target.com')

# Get alerts
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

## ⌨️ Command Line

### Basic Command Line Options
```bash
# Start ZAP in daemon mode
zap.sh -daemon

# Start on specific port
zap.sh -daemon -port 8090

# Quick scan
zap.sh -quickurl http://target.com -quickout report.html

# Baseline scan (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Automation Framework
```bash
# Run automation scan
zap.sh -cmd -autorun automation.yaml
```

### Automation YAML Example
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Docker Scans

```bash
# Baseline Scan (passive only)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Full Scan (active)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# API Scan
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Add-ons

### Install Add-ons
```
Help → Check for Updates → Marketplace

Or: Tools → Add-ons → Marketplace
```

### Essential Add-ons

| Add-on | Description |
|--------|-------------|
| **Active Scan Rules** | Additional scan rules |
| **Passive Scan Rules** | Additional passive rules |
| **FuzzDB** | Fuzzing payloads |
| **Retire.js** | Detect vulnerable JS libraries |
| **AJAX Spider** | JavaScript-aware crawler |
| **Selenium** | Browser automation |
| **Wappalyzer** | Technology detection |
| **Access Control** | Access control testing |
| **Directory List** | Directory wordlists |

### Add-on Management
```
# List installed
Tools → Add-ons → Installed

# Update all
Help → Check for Updates → Update All
```

---

## 🎬 Real-World Examples

### Example 1: Quick Web Scan
```
1. Enter URL in Quick Start tab
2. Click "Attack"
3. ZAP will spider and scan automatically
4. Review alerts when complete
```

### Example 2: Manual + Automated Testing
```
1. Configure browser proxy to ZAP
2. Browse application manually (explore all features)
3. Right-click target in Sites → Spider
4. Wait for spider to complete
5. Right-click target → Active Scan
6. Review alerts
7. Generate report
```

### Example 3: Authenticated Scan
```
1. Create Context for target
2. Set up Form-based Authentication
3. Add test user credentials
4. Configure forced user mode
5. Spider as authenticated user
6. Active Scan
```

### Example 4: API Testing
```
1. Import API definition (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Spider the API
4. Active Scan
```

### Example 5: CI/CD Integration
```bash
# Jenkins Pipeline
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Quick Reference

### Essential Actions

| Task | Method |
|------|--------|
| Start proxy | ZAP auto-starts on port 8080 |
| Spider | Right-click → Spider |
| Active Scan | Right-click → Active Scan |
| Intercept | Click Break button (🛑) |
| Fuzz | Right-click request → Fuzz |
| Generate Report | Report → Generate Report |

### Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+Alt+S` | Spider dialog |
| `Ctrl+Alt+A` | Active Scan dialog |
| `Ctrl+M` | Manual Request |
| `Ctrl+B` | Toggle Break |
| `Ctrl+F` | Find |
| `Ctrl+R` | Resend Request |

### Scan Modes

| Mode | Use When |
|------|----------|
| **Safe** | Don't want to make any changes |
| **Protected** | Testing specific scope only |
| **Standard** | Normal testing |
| **ATTACK** | Aggressive testing (with permission!) |

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Limit Scope**
   ```
   Define context and only scan in-scope items
   ```

2. **Use AJAX Spider for SPAs**
   ```
   Modern apps need AJAX Spider, not just standard
   ```

3. **Configure Technology**
   ```
   Select only relevant technologies to reduce scan time
   ```

### Accuracy Tips

1. **Authenticate First**
   ```
   Set up authentication for deeper coverage
   ```

2. **Manual Explore Before Automated**
   ```
   Manually browse the app first to discover all features
   ```

3. **Review All Alerts**
   ```
   Check for false positives, verify true positives
   ```

### Workflow

```
1. Configure proxy
2. Manual exploration (with authentication)
3. Spider (standard + AJAX)
4. Passive scan review
5. Active scan
6. Manual testing (fuzzing, edge cases)
7. Generate report
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** OWASP ZAP is a security testing tool that should only be used for **authorized testing**.
> 
> - ✅ Test applications you own
> - ✅ Test with explicit written permission
> - ✅ Use in testing/staging environments
> - ❌ Never scan production without authorization
> - ❌ Never scan systems you don't own
> 
> **Unauthorized security testing is illegal and may result in criminal charges.**

---

## 📚 Resources

### Official Resources
- [OWASP ZAP Website](https://www.zaproxy.org/)
- [ZAP Documentation](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [ZAP API Docs](https://www.zaproxy.org/docs/api/)

### Learning Resources
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [ZAP Deep Dive Videos](https://www.zaproxy.org/docs/videos/)

### Related Cheatsheets
- [Burp Suite](../Burp-Suite/README.md)
- [Nikto](../Nikto/README.md)
- [SQLMap](../SQLMap/README.md)

---

<p align="center">
  <b>⚡ Master Web Application Security Testing!</b><br>
  <i>Free, powerful, and community-driven</i>
</p>
