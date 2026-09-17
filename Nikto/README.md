# 🕷️ Nikto - Complete Cheatsheet

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web Server Scanner - Since 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Open-source web server scanner for security testing</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Target Specification](#-target-specification)
- [Port & SSL Options](#-port--ssl-options)
- [Scan Tuning](#-scan-tuning)
- [Authentication](#-authentication)
- [Proxy & Evasion](#-proxy--evasion)
- [Output & Reporting](#-output--reporting)
- [Database Updates](#-database-updates)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Nikto** is an open-source web server scanner that performs comprehensive tests against web servers for multiple items including:

### What Nikto Checks

| Category | Description |
|----------|-------------|
| **Dangerous Files** | Files that may be dangerous (backups, etc.) |
| **Outdated Versions** | Outdated server software |
| **Version Specific** | Version-specific vulnerabilities |
| **Server Configuration** | Misconfigurations |
| **CGI Scripts** | Potentially dangerous CGI scripts |
| **SSL/TLS** | SSL certificate issues |
| **Headers** | Missing/insecure HTTP headers |
| **Methods** | Dangerous HTTP methods |

### Database Statistics

```
✅ 6,700+ potentially dangerous files/CGIs
✅ 1,250+ outdated server versions
✅ 270+ version-specific problems
✅ 1,000+ server configuration issues
```

### Features

| Feature | Description |
|---------|-------------|
| **Comprehensive** | Tests for thousands of vulnerabilities |
| **Plugin-based** | Extensible architecture |
| **Multiple Formats** | HTML, XML, CSV, JSON output |
| **SSL Support** | Full HTTPS testing |
| **IDS Evasion** | Built-in evasion techniques |
| **Authentication** | Basic, Digest, NTLM support |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
# Update Nikto
sudo apt update && sudo apt install nikto

# Update database
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### From GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Verify Installation
```bash
nikto -Version
nikto -h          # Help
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
nikto -h <host> [options]
```

### Quick Start
```bash
# Basic scan
nikto -h http://target.com

# Scan with port
nikto -h target.com -p 80

# Scan HTTPS
nikto -h https://target.com

# Scan IP
nikto -h 192.168.1.1
```

---

## 🎯 Target Specification

### Single Target
```bash
# By hostname
nikto -h target.com

# By IP
nikto -h 192.168.1.1

# With protocol
nikto -h http://target.com
nikto -h https://target.com
```

### Multiple Targets
```bash
# From file
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Target with Path
```bash
# Scan specific path
nikto -h http://target.com/app

# Scan specific directory
nikto -h http://target.com/admin/
```

---

## 🔌 Port & SSL Options

### Port Options
```bash
# Single port
nikto -h target.com -p 80

# Multiple ports
nikto -h target.com -p 80,443,8080

# Port range
nikto -h target.com -p 80-90

# Common web ports
nikto -h target.com -p 80,443,8000,8080,8443
```

### SSL/TLS Options

| Option | Description |
|--------|-------------|
| `-ssl` | Force SSL mode |
| `-nossl` | Disable SSL |
| `-maxtime` | Maximum scan time |

```bash
# Force SSL
nikto -h target.com -ssl

# Disable SSL (HTTP only)
nikto -h target.com -nossl

# SSL on non-standard port
nikto -h target.com -p 8443 -ssl
```

### Virtual Hosts
```bash
# Specify virtual host
nikto -h 192.168.1.1 -vhost target.com

# Useful when:
# - Target behind load balancer
# - Multiple sites on same IP
```

---

## 🎚️ Scan Tuning

### Tuning Options (-Tuning)

| Code | Description |
|------|-------------|
| `0` | File Upload |
| `1` | Interesting File / Seen in logs |
| `2` | Misconfiguration / Default File |
| `3` | Information Disclosure |
| `4` | Injection (XSS/Script/HTML) |
| `5` | Remote File Retrieval - Inside Web Root |
| `6` | Denial of Service |
| `7` | Remote File Retrieval - Server Wide |
| `8` | Command Execution / Remote Shell |
| `9` | SQL Injection |
| `a` | Authentication Bypass |
| `b` | Software Identification |
| `c` | Remote Source Inclusion |
| `d` | WebService |
| `e` | Administrative Console |
| `x` | Reverse Tuning Options |

### Using Tuning
```bash
# Only test for SQL Injection
nikto -h target.com -Tuning 9

# Test for XSS and SQL Injection
nikto -h target.com -Tuning 49

# Test for multiple types
nikto -h target.com -Tuning 1234

# Exclude certain tests (x prefix)
nikto -h target.com -Tuning x6    # Exclude DoS tests

# All except DoS
nikto -h target.com -Tuning x6
```

### Plugins
```bash
# List available plugins
nikto -list-plugins

# Run specific plugin
nikto -h target.com -Plugins apacheusers

# Run multiple plugins
nikto -h target.com -Plugins "apacheusers;robots"

# Exclude plugins
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Common Plugins

| Plugin | Description |
|--------|-------------|
| `apacheusers` | Apache user enumeration |
| `cgi` | CGI script testing |
| `robots` | robots.txt analysis |
| `headers` | HTTP header analysis |
| `httpoptions` | HTTP methods testing |
| `outdated` | Outdated software check |
| `ssl` | SSL/TLS analysis |
| `dictionary` | Dictionary attack |

---

## 🔐 Authentication

### Basic Authentication
```bash
# Username and password
nikto -h target.com -id admin:password

# Base64 format
nikto -h target.com -id "admin:password"
```

### Digest Authentication
```bash
# Digest auth (auto-detected)
nikto -h target.com -id admin:password
```

### NTLM Authentication
```bash
# NTLM auth
nikto -h target.com -id admin:password:domain
```

### Cookie-Based Authentication
```bash
# Send cookies
nikto -h target.com -cookies "session=abc123; token=xyz789"

# From file
nikto -h target.com -cookies cookies.txt
```

---

## 🛡️ Proxy & Evasion

### Proxy Support
```bash
# HTTP proxy
nikto -h target.com -useproxy http://127.0.0.1:8080

# Authenticated proxy
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Through Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### IDS Evasion (-evasion)

| Code | Technique |
|------|-----------|
| `1` | Random URI encoding (non-UTF8) |
| `2` | Directory self-reference (/./) |
| `3` | Premature URL ending |
| `4` | Prepend long random string |
| `5` | Fake parameter |
| `6` | TAB as request spacer |
| `7` | Change the case of the URL |
| `8` | Use Windows directory separator (\) |
| `A` | Use a carriage return (0x0d) |
| `B` | Use binary value 0x0b |

```bash
# Single evasion technique
nikto -h target.com -evasion 1

# Multiple evasion techniques
nikto -h target.com -evasion 1234

# All evasion techniques
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# Custom User-Agent
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Request Timing
```bash
# Pause between requests (seconds)
nikto -h target.com -Pause 2

# Maximum scan time (seconds)
nikto -h target.com -maxtime 3600

# Timeout per request
nikto -h target.com -timeout 10
```

---

## 📄 Output & Reporting

### Output Formats

| Option | Format |
|--------|--------|
| `-o file` | Output to file |
| `-Format htm` | HTML format |
| `-Format xml` | XML format |
| `-Format csv` | CSV format |
| `-Format txt` | Text format |
| `-Format json` | JSON format |
| `-Format nbe` | NBE format |
| `-Format sql` | SQL format |

### Save Results
```bash
# Text output
nikto -h target.com -o report.txt

# HTML report
nikto -h target.com -o report.html -Format htm

# XML report
nikto -h target.com -o report.xml -Format xml

# JSON report
nikto -h target.com -o report.json -Format json

# CSV report
nikto -h target.com -o report.csv -Format csv
```

### Verbosity Options
```bash
# Display options
nikto -h target.com -Display V    # Verbose
nikto -h target.com -Display D    # Debug
nikto -h target.com -Display E    # HTTP errors
nikto -h target.com -Display P    # Progress
nikto -h target.com -Display S    # Scrub IP from output
nikto -h target.com -Display 1    # Show redirects
nikto -h target.com -Display 2    # Show cookies
nikto -h target.com -Display 3    # Show 200/OK responses
nikto -h target.com -Display 4    # Show URLs requiring auth

# Multiple display options
nikto -h target.com -Display VEP
```

### Save Progress
```bash
# Save scan state (can resume later)
nikto -h target.com -Save scan_state.txt

# Resume scan
nikto -Resume scan_state.txt
```

---

## 🔄 Database Updates

### Update Nikto Database
```bash
# Update all databases
nikto -update

# Manual update (if -update fails)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Database Location
```bash
# Default locations
/usr/share/nikto/databases/
/var/lib/nikto/

# Database files
db_tests         # Main test database
db_outdated      # Outdated software versions
db_server_msgs   # Server messages
db_variables     # Variables
db_404_strings   # 404 page strings
```

---

## 🎬 Real-World Examples

### Example 1: Basic Web Server Scan
```bash
nikto -h http://target.com
```

### Example 2: Comprehensive HTTPS Scan
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Example 3: Scan Multiple Ports
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Example 4: Authenticated Scan
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Example 5: Stealth Scan (IDS Evasion)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Example 6: Through Burp Proxy
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Example 7: SQL Injection Focus
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Example 8: Full Audit
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Example 9: Virtual Host Testing
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Example 10: Scan from Target File
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| Basic scan | `nikto -h target.com` |
| HTTPS scan | `nikto -h target.com -ssl` |
| Multiple ports | `nikto -h target.com -p 80,443,8080` |
| With auth | `nikto -h target.com -id user:pass` |
| HTML report | `nikto -h target.com -o report.html -Format htm` |
| Evasion | `nikto -h target.com -evasion 123` |
| Through proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Update DB | `nikto -update` |

### Common Options

| Option | Description |
|--------|-------------|
| `-h` | Target host |
| `-p` | Port(s) to scan |
| `-ssl` | Force SSL mode |
| `-id` | Authentication credentials |
| `-o` | Output file |
| `-Format` | Output format |
| `-Tuning` | Scan tuning |
| `-evasion` | IDS evasion |
| `-useproxy` | Proxy server |
| `-vhost` | Virtual host |
| `-Pause` | Delay between tests |
| `-timeout` | Request timeout |

### Tuning Quick Reference

| Code | Category |
|------|----------|
| 1-4 | Information/Misconfiguration |
| 4-5 | Injection/Retrieval |
| 6 | DoS (avoid in production) |
| 7-8 | Remote access |
| 9 | SQL Injection |
| a | Auth bypass |
| b | Software ID |
| x | Exclude |

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Start with Basic Scan**
   ```bash
   nikto -h target.com
   ```

2. **Exclude DoS Tests**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Use Appropriate Timeouts**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Accuracy Tips

1. **Update Database Regularly**
   ```bash
   nikto -update
   ```

2. **Use Verbose Mode**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Combine with Other Tools**
   ```bash
   # Nmap first, then Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Common Issues

| Issue | Solution |
|-------|----------|
| Slow scan | Reduce `-Pause`, increase `-timeout` |
| SSL errors | Use `-ssl` flag explicitly |
| Auth issues | Check `-id` format |
| Missing results | Update database, check plugins |

---

## ⚠️ Legal Disclaimer

> **WARNING:** Nikto is a web server scanner that should only be used for **authorized testing**.
> 
> - ✅ Scan servers you own
> - ✅ Scan with explicit written permission
> - ✅ Use in authorized penetration tests
> - ❌ Never scan without authorization
> - ❌ Unauthorized scanning is illegal
> 
> **Nikto can generate significant traffic and may trigger security alerts.**

---

## 📚 Resources

### Official Resources
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto Documentation](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Learning Resources
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Related Cheatsheets
- [Nmap](../Nmap/README.md)
- [Gobuster](../Gobuster/README.md)
- [Burp Suite](../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ Master Web Server Scanning!</b><br>
  <i>Find vulnerabilities before attackers do</i>
</p>
