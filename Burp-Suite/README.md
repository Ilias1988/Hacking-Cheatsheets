# 🔶 Burp Suite - Complete Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Web Application Security Testing Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 The industry-standard web application security testing platform</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation & Setup](#-installation--setup)
- [Proxy Configuration](#-proxy-configuration)
- [Intercepting Traffic](#-intercepting-traffic)
- [Target & Scope](#-target--scope)
- [Spider/Crawler](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Extensions](#-extensions)
- [Keyboard Shortcuts](#-keyboard-shortcuts)
- [Testing Workflow](#-testing-workflow)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Burp Suite** is an integrated platform for performing security testing of web applications. Developed by PortSwigger, it is the industry standard for web penetration testing.

### Editions

| Edition | Features | Price |
|---------|----------|-------|
| **Community** | Basic proxy, manual tools | Free |
| **Professional** | Scanner, Intruder (unlimited), Collaborator | $449/year |
| **Enterprise** | CI/CD integration, team features | Contact sales |

### Core Tools

| Tool | Description |
|------|-------------|
| **Proxy** | Intercept and modify HTTP/HTTPS traffic |
| **Spider** | Crawl web applications automatically |
| **Scanner** | Find vulnerabilities automatically |
| **Intruder** | Automated customized attacks |
| **Repeater** | Manual request manipulation |
| **Decoder** | Encode/decode data |
| **Comparer** | Compare responses |
| **Sequencer** | Analyze randomness of tokens |

---

## 📥 Installation & Setup

### Download

```bash
# Download from official site
https://portswigger.net/burp/releases

# Available for:
# - Windows (installer/standalone)
# - macOS (DMG)
# - Linux (script/JAR)
```

### System Requirements

| Requirement | Minimum | Recommended |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Disk | 500 MB | 2+ GB |
| Java | JRE 8+ | Bundled JRE |
| Display | 1280x800 | 1920x1080 |

### First Launch Configuration

1. **Select Project Type**
   - Temporary project (testing)
   - New project on disk (save work)
   - Open existing project

2. **Configuration**
   - Use Burp defaults (recommended for beginners)
   - Load from configuration file

3. **Memory Allocation**
   ```bash
   # Increase memory for large projects
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

## 🔌 Proxy Configuration

### Browser Configuration

#### Manual Proxy Setup

| Browser | Proxy Settings Location |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Uses system proxy or extension |
| Safari | System Preferences → Network → Proxies |

**Default Burp Proxy:**
```
Host: 127.0.0.1
Port: 8080
```

#### Firefox Configuration
```
1. Open Firefox Preferences
2. Search for "proxy"
3. Click "Settings..."
4. Select "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. Check "Also use this proxy for HTTPS"
7. Click OK
```

#### FoxyProxy Extension (Recommended)
```
1. Install FoxyProxy extension
2. Add new proxy:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. Enable when testing
```

### SSL/TLS Certificate Installation

```bash
# Step 1: With proxy enabled, visit:
http://burp

# Step 2: Click "CA Certificate" to download

# Step 3: Import certificate:
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# System: Add to trusted root certificates
```

### Proxy Listeners

```
Proxy → Options → Proxy Listeners

Default: 127.0.0.1:8080

Add custom listeners:
- Different ports for different testing
- Bind to all interfaces (0.0.0.0) for mobile testing
- Redirect to different hosts
```

### Invisible Proxy (For Non-Proxy-Aware Clients)

```
1. Proxy → Options → Proxy Listeners
2. Edit listener → Request handling
3. Enable "Support invisible proxying"
4. Configure hosts file or DNS
```

---

## 🎣 Intercepting Traffic

### Intercept Controls

| Button | Function |
|--------|----------|
| **Intercept is on/off** | Toggle interception |
| **Forward** | Send request to server |
| **Drop** | Discard request |
| **Action** | Additional options menu |

### Intercept Options

```
Proxy → Options → Intercept Client Requests

Rules examples:
- Only intercept in-scope items
- Only intercept specific file types
- Exclude images and static files
```

### Request Modification

```http
# Original Request
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Modified Request (while intercepted)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Match and Replace

```
Proxy → Options → Match and Replace

Common rules:
- Remove security headers
- Modify User-Agent
- Add custom headers
- Replace request parameters
```

| Type | Match | Replace | Purpose |
|------|-------|---------|---------|
| Request header | `User-Agent: .*` | `User-Agent: Custom` | Change UA |
| Request body | `password=test` | `password=admin` | Test creds |
| Response header | `X-Frame-Options.*` | ` ` | Remove header |
| Response body | `disabled` | `enabled` | Enable buttons |

### Response Modification

```
Proxy → Options → Intercept Server Responses

Enable for:
- Remove security headers
- Modify JavaScript
- Change response content
- Test client-side controls
```

---

## 🎯 Target & Scope

### Adding to Scope

```
Method 1: Right-click in Site map → Add to scope
Method 2: Target → Scope → Add (manually)
Method 3: From Proxy history → Add to scope
```

### Scope Configuration

```
Target → Scope

Include in scope:
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern

Example patterns:
.*\.target\.com$          # All subdomains
^https://target\.com/app  # Specific path
```

### Advanced Scope Settings

```
Target → Scope → Advanced scope control

Include:
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclude:
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Site Map

```
Target → Site map

Features:
- View all discovered content
- Filter by scope
- Search/filter content
- Highlight interesting items
- Compare site maps
```

---

## 🕷️ Spider/Crawler

### Starting the Spider

```
1. Right-click target in Site map
2. Select "Spider this host" or "Spider this branch"
3. Or: Target → Site map → Right-click → Scan → Crawl
```

### Spider Settings

```
Spider → Options (legacy) OR
Dashboard → New scan → Crawl

Crawler settings:
- Maximum link depth
- Maximum crawl time
- Forms submission
- Login handling
```

### Crawl Configuration

| Setting | Description | Recommended |
|---------|-------------|-------------|
| **Link depth** | How deep to follow links | 5-10 |
| **Max requests** | Total requests limit | 1000-5000 |
| **Duplicate removal** | Skip similar pages | Enabled |
| **Passive scanning** | Scan while crawling | Enabled |

### Form Submission

```
Spider → Options → Application Login

Configure login for:
- Single credentials
- Credential list
- Session detection
```

---

## 🔍 Scanner

### Scan Types

| Type | Description | Use Case |
|------|-------------|----------|
| **Passive** | Analyze proxy traffic | Always on |
| **Active** | Send payloads to find vulns | With permission |
| **Full** | Comprehensive scan | Time available |
| **Custom** | Specific tests only | Targeted testing |

### Starting a Scan

```
Method 1: Right-click → Scan
Method 2: Dashboard → New scan
Method 3: Send to Scanner from other tools
```

### Scan Configuration

```
Dashboard → New scan → Scan configuration

Built-in configurations:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Vulnerability Categories

```
Scanner checks for:

Injection:
- SQL Injection
- OS Command Injection
- LDAP Injection
- XML/XPath Injection

Cross-Site Scripting:
- Reflected XSS
- Stored XSS
- DOM-based XSS

Authentication:
- Broken authentication
- Session management issues
- Weak passwords

Other:
- CSRF
- Directory traversal
- File inclusion
- Information disclosure
- Server-side issues
```

### Scan Results

```
Dashboard → Issue activity
Target → Site map → Issues

For each issue:
- Severity (High/Medium/Low/Info)
- Confidence (Certain/Firm/Tentative)
- Description
- Remediation
- Evidence (request/response)
```

### Custom Scan Profiles

```
1. Dashboard → New scan
2. Select "Scan configuration"
3. Click "New" or modify existing
4. Save for reuse

Customize:
- Which vulnerabilities to test
- Payload intensity
- Request rate
- Authentication handling
```

---

## 🔁 Repeater

### Purpose

Manual testing and manipulation of individual requests.

### Sending to Repeater

```
1. Right-click any request
2. Select "Send to Repeater"
3. Keyboard: Ctrl+R
```

### Using Repeater

```
1. Modify request in left panel
2. Click "Send" (or Ctrl+Space)
3. View response in right panel
4. Compare responses
```

### Repeater Features

| Feature | Description |
|---------|-------------|
| **Multiple tabs** | Test multiple requests |
| **Request history** | Navigate with < > buttons |
| **Follow redirects** | Toggle auto-follow |
| **Process cookies** | Update cookies automatically |
| **Content-Length** | Auto-update header |

### Testing Examples

```http
# Test for SQL Injection
GET /user.php?id=1' HTTP/1.1

# Test for XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Test authentication bypass
GET /admin HTTP/1.1
Cookie: role=admin

# Test IDOR
GET /api/user/1 HTTP/1.1  # Your user
GET /api/user/2 HTTP/1.1  # Other user
```

---

## 🔫 Intruder

### Attack Types

| Type | Positions | Payloads | Use Case |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 set, one at a time | Single parameter testing |
| **Battering Ram** | 1+ | 1 set, same everywhere | Same value everywhere |
| **Pitchfork** | 1+ | Multiple sets, parallel | Username + password pairs |
| **Cluster Bomb** | 1+ | Multiple sets, all combinations | Full credential bruteforce |

### Sending to Intruder

```
1. Right-click request
2. Select "Send to Intruder"
3. Keyboard: Ctrl+I
```

### Setting Positions

```
Intruder → Positions

Mark injection points with § symbols:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Buttons:
- Add § - Mark selection
- Clear § - Remove markers
- Auto § - Auto-detect
```

### Payload Types

| Type | Description |
|------|-------------|
| **Simple list** | Custom wordlist |
| **Runtime file** | Load from file |
| **Numbers** | Sequential/random numbers |
| **Dates** | Date sequences |
| **Brute forcer** | Character combinations |
| **Null payloads** | Empty payloads (rate testing) |
| **Username generator** | Common username patterns |

### Payload Processing

```
Intruder → Payloads → Payload Processing

Add rules:
- Add prefix/suffix
- URL encode
- Base64 encode
- Hash (MD5, SHA)
- Match/replace
```

### Grep - Match

```
Intruder → Options → Grep - Match

Add strings to flag in responses:
- "Login successful"
- "Invalid password"
- "error"
- Custom regex

Helps identify successful attacks.
```

### Attack Examples

#### Brute Force Login
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type: Cluster bomb
Payload 1: Username list
Payload 2: Password list
```

#### Fuzzing Parameters
```
GET /api/user/§1§ HTTP/1.1

Attack type: Sniper
Payload: Numbers 1-1000
```

#### Content Discovery
```
GET /§admin§ HTTP/1.1

Attack type: Sniper
Payload: Directory wordlist
Filter: Status code != 404
```

### Results Analysis

```
After attack:
- Sort by length (find different responses)
- Sort by status code
- Filter by grep match
- Export results
```

---

## 🔓 Decoder

### Encoding/Decoding

```
Decoder tab

Operations:
- URL encode/decode
- HTML encode/decode
- Base64 encode/decode
- Hex encode/decode
- Gzip compress/decompress
- ASCII Hex
- Binary
```

### Smart Decode

```
Decoder → Smart decode

Automatically detects and decodes:
- Base64
- URL encoding
- HTML entities
- Combined encodings
```

### Common Uses

```
# Decode JWT token
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decode as Base64

# Decode URL parameter
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL decode
→ <script>alert(1)</script>

# Encode payload
<script>alert(1)</script>
→ Base64 encode
→ Use in attack
```

---

## 📊 Comparer

### Purpose

Compare two data items (requests, responses, or any data).

### How to Use

```
1. Right-click item 1 → Send to Comparer
2. Right-click item 2 → Send to Comparer
3. Go to Comparer tab
4. Select items and click "Words" or "Bytes"
```

### Comparison Modes

| Mode | Description |
|------|-------------|
| **Words** | Compare word by word |
| **Bytes** | Compare byte by byte (exact) |

### Use Cases

```
- Compare successful vs failed login responses
- Find differences in session tokens
- Compare before/after modifications
- Identify hidden form fields
```

---

## 🔢 Sequencer

### Purpose

Analyze the quality of randomness in tokens (session IDs, CSRF tokens).

### How to Use

```
1. Capture request that returns token
2. Right-click → Send to Sequencer
3. Configure token location
4. Start live capture
5. Analyze results
```

### Analysis Metrics

| Metric | Good Value | Description |
|--------|------------|-------------|
| **Overall quality** | >100 bits | Randomness score |
| **Character-level** | High entropy | Per-character randomness |
| **Bit-level** | High entropy | Per-bit randomness |

### Interpretation

```
Results:
- Excellent: Token is cryptographically random
- Good: Acceptable for most uses  
- Poor: Potentially predictable
- Very poor: Token may be guessable
```

---

## 🧩 Extensions

### BApp Store

```
Extender → BApp Store

Popular extensions:
```

### Must-Have Extensions

| Extension | Description |
|-----------|-------------|
| **Autorize** | Authorization testing |
| **Logger++** | Enhanced logging |
| **Retire.js** | Detect vulnerable JS libraries |
| **Active Scan++** | Additional scan checks |
| **AuthMatrix** | Authorization matrix testing |
| **Turbo Intruder** | Fast Intruder alternative |
| **Param Miner** | Hidden parameter discovery |
| **JSON Web Tokens** | JWT analysis/manipulation |
| **Hackvertor** | Advanced encoding/decoding |
| **CO2** | Suite of useful tools |

### Installing Extensions

```
Method 1: BApp Store
1. Extender → BApp Store
2. Find extension
3. Click Install

Method 2: Manual
1. Download .jar or .py file
2. Extender → Extensions → Add
3. Select file type and path
```

### Extension Settings

```
Extender → Options

- Python environment (for Python extensions)
- Java environment settings
- Logging options
```

---

## ⌨️ Keyboard Shortcuts

### Global Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+I` | Send to Intruder |
| `Ctrl+R` | Send to Repeater |
| `Ctrl+S` | Search |
| `Ctrl+F` | Forward (Proxy) |
| `Ctrl+D` | Drop (Proxy) |
| `Ctrl+T` | Toggle Intercept |
| `Ctrl+Shift+T` | Toggle Proxy intercept |

### Tab Navigation

| Shortcut | Action |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### In Repeater

| Shortcut | Action |
|----------|--------|
| `Ctrl+Space` | Send request |
| `Ctrl+G` | Go to (request history) |
| `Ctrl+Plus` | New tab |

### In Editor

| Shortcut | Action |
|----------|--------|
| `Ctrl+U` | URL encode selection |
| `Ctrl+Shift+U` | URL decode selection |
| `Ctrl+B` | Base64 encode |
| `Ctrl+Shift+B` | Base64 decode |

---

## 🎯 Testing Workflow

### Standard Web App Test Flow

```
Step 1: Setup
├── Configure browser proxy
├── Install SSL certificate
└── Define scope

Step 2: Reconnaissance
├── Browse application manually
├── Run Spider/Crawler
└── Review Site map

Step 3: Analysis
├── Check Proxy history
├── Review passive scan results
└── Identify attack surface

Step 4: Testing
├── Test with Repeater (manual)
├── Test with Intruder (automated)
├── Run active scan
└── Use extensions

Step 5: Reporting
├── Review all findings
├── Generate report
└── Document evidence
```

### Testing Checklist

```
Authentication:
□ Brute force login
□ Password policy
□ Account lockout
□ Session management
□ Remember me function

Authorization:
□ IDOR testing
□ Privilege escalation
□ Function level access

Input Validation:
□ SQL injection
□ XSS (reflected/stored/DOM)
□ Command injection
□ File upload
□ XXE

Session:
□ Session fixation
□ Session timeout
□ Token randomness

Business Logic:
□ Price manipulation
□ Workflow bypass
□ Race conditions
```

---

## 📊 Quick Reference

### Tool Summary

| Tool | Primary Use | Key Shortcut |
|------|-------------|--------------|
| Proxy | Intercept traffic | Ctrl+T (toggle) |
| Repeater | Manual testing | Ctrl+R (send to) |
| Intruder | Automated attacks | Ctrl+I (send to) |
| Scanner | Find vulnerabilities | Right-click → Scan |
| Spider | Discover content | Right-click → Spider |
| Decoder | Encode/Decode | - |
| Comparer | Diff responses | Send to Comparer |
| Sequencer | Token analysis | Send to Sequencer |

### Intruder Attack Types Quick Reference

| Attack | Use When |
|--------|----------|
| **Sniper** | Testing one parameter at a time |
| **Battering Ram** | Same payload everywhere |
| **Pitchfork** | Username:password pairs |
| **Cluster Bomb** | All username × password combinations |

### Common Status Codes

| Code | Meaning | Intruder Significance |
|------|---------|----------------------|
| 200 | OK | Normal response |
| 301/302 | Redirect | May indicate success |
| 401 | Unauthorized | Bad credentials |
| 403 | Forbidden | Access denied |
| 404 | Not found | Invalid path |
| 500 | Server error | Possible vulnerability |

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Limit Scope**
   ```
   Only keep target domains in scope
   Reduces noise in Proxy history
   ```

2. **Disable Extensions**
   ```
   Disable unused extensions for performance
   ```

3. **Use Project Files**
   ```
   Save work frequently
   Use project files for large tests
   ```

### Testing Tips

1. **Start Passive**
   ```
   Let passive scanner find low-hanging fruit
   Review before active scanning
   ```

2. **Use Repeater First**
   ```
   Manual testing reveals more
   Understand the application before automation
   ```

3. **Organize with Tabs**
   ```
   Name Repeater tabs meaningfully
   Use Intruder attack names
   ```

### Avoiding Detection

```
Settings for stealth:

Project options → Connections:
- Throttle requests
- Random delays
- Concurrent connections limit

Intruder → Options:
- Request delay
- Start time
```

### Troubleshooting

| Issue | Solution |
|-------|----------|
| Can't intercept HTTPS | Install CA certificate |
| Slow performance | Reduce scope, disable extensions |
| Missing requests | Check "Intercept Server Responses" |
| Scanner not finding vulns | Increase scan intensity |

---

## ⚠️ Legal Disclaimer

> **WARNING:** Burp Suite is a powerful security testing tool that should only be used for **authorized testing**.
> 
> - ✅ Test applications you own
> - ✅ Test with explicit written permission
> - ✅ Test in dedicated testing environments
> - ❌ Never scan production systems without authorization
> - ❌ Never use for unauthorized access
> 
> **Unauthorized security testing is illegal and may result in criminal charges.**

---

## 📚 Resources

### Official Resources
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Burp Suite Documentation](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Learning Resources
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Related Cheatsheets
- [SQLMap](../SQLMap/README.md)
- [Metasploit Framework](../Metasploit/README.md)

---

<p align="center">
  <b>🔶 Master Web Application Security Testing!</b><br>
  <i>The best way to learn is by doing - set up a lab and practice!</i>
</p>
