# 🔍 Google Dorking - Complete Cheatsheet

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 Advanced Google Search Techniques for Security Researchers</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Basic Operators](#-basic-operators)
- [Advanced Operators](#-advanced-operators)
- [Combining Operators](#-combining-operators)
- [File Discovery](#-file-discovery)
- [Directory Listing](#-directory-listing)
- [Admin Panels](#-admin-panels)
- [Database & SQL](#-database--sql)
- [Configuration Files](#-configuration-files)
- [Credentials & Secrets](#-credentials--secrets)
- [Vulnerable Servers](#-vulnerable-servers)
- [Bug Bounty Dorks](#-bug-bounty-dorks)
- [CMS Specific](#-cms-specific)
- [Quick Reference](#-quick-reference)
- [Tools & Automation](#-tools--automation)
- [Resources](#-resources)

---

## 🎯 Introduction

**Google Dorking** (also known as Google Hacking) is a technique that uses advanced search operators to find sensitive information exposed on the internet.

### What Can You Find?

| Category | Examples |
|----------|----------|
| **Sensitive Files** | Backups, configs, logs, databases |
| **Exposed Panels** | Admin panels, dashboards, phpMyAdmin |
| **Credentials** | Passwords, API keys, tokens |
| **Vulnerabilities** | SQL injection, open directories |
| **Personal Data** | Emails, phone numbers, documents |

### Search Engine URLs

| Engine | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Basic Operators

### Site Operator

Limit search to specific domain:

```
site:example.com                     # Only example.com
site:*.example.com                   # All subdomains
site:example.com -www                # Exclude www
site:.gov                            # Government sites
site:.edu                            # Educational sites
```

### Inurl Operator

Search in URL:

```
inurl:admin                          # admin in URL
inurl:login                          # login pages
inurl:dashboard                      # dashboards
inurl:config                         # config files
inurl:backup                         # backup files
```

### Intitle Operator

Search in page title:

```
intitle:"index of"                   # Directory listings
intitle:admin                        # Admin pages
intitle:login                        # Login pages
intitle:"dashboard"                  # Dashboards
intitle:"control panel"              # Control panels
```

### Intext Operator

Search in page content:

```
intext:"password"                    # Password in text
intext:"username"                    # Username in text
intext:"api_key"                     # API keys
intext:"secret"                      # Secrets
```

### Filetype Operator

Search specific file types:

```
filetype:pdf                         # PDF files
filetype:doc                         # Word documents
filetype:xls                         # Excel files
filetype:sql                         # SQL files
filetype:log                         # Log files
filetype:bak                         # Backup files
filetype:env                         # Environment files
```

---

## ⚡ Advanced Operators

### All Variants

```
allinurl:admin login                 # All words in URL
allintitle:admin panel               # All words in title
allintext:password username          # All words in text
```

### Cache & Link

```
cache:example.com                    # Cached version
link:example.com                     # Pages linking to
related:example.com                  # Related sites
```

### Date Range

```
after:2023-01-01                     # After date
before:2024-01-01                    # Before date
daterange:2458850-2459215            # Julian date range
```

### Wildcards & OR

```
admin * panel                        # Wildcard
admin OR login                       # Either term
"admin panel"                        # Exact phrase
-inurl:https                         # Exclude HTTPS
```

### Number Range

```
inurl:id= 1..1000                    # Number range
price $100..$500                     # Price range
```

---

## 🔗 Combining Operators

### Basic Combinations

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Advanced Combinations

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Bug Bounty Combinations

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 File Discovery

### Sensitive Documents

```
# Office Documents
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Text Files
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Backup Files

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Database Files

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Log Files

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Configuration Files

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Source Code

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

## 📂 Directory Listing

### Index Of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Specific Directories

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### File Types in Directories

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Admin Panels

### Generic Admin

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### CPanel & Hosting

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Database Admin

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Control Panels

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Database & SQL

### SQL Files

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### SQL Errors

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### SQL Injection Indicators

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Database Dumps

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

## ⚙️ Configuration Files

### Web Server Config

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Application Config

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Git Config

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS & Cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # AWS Key prefix
site:target.com "credentials" filetype:json
```

---

## 🔑 Credentials & Secrets

### Passwords

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Username & Password Combos

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### API Keys & Tokens

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### Email Addresses

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Private Keys

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

## ⚠️ Vulnerable Servers

### Open Directories

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Default Pages

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Error Pages

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Exposed Services

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

## 🐛 Bug Bounty Dorks

### Quick Wins

```
# Sensitive Files
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Admin Panels
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Config Files
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Credentials
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Subdomain Discovery

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### API Endpoints

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Development & Staging

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Authentication Pages

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### File Upload

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 CMS Specific

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

## 📊 Quick Reference

### Essential Operators

| Operator | Function | Example |
|----------|----------|---------|
| `site:` | Domain specific | `site:target.com` |
| `inurl:` | Search in URL | `inurl:admin` |
| `intitle:` | Search in title | `intitle:login` |
| `intext:` | Search in content | `intext:password` |
| `filetype:` | File extension | `filetype:sql` |
| `ext:` | File extension | `ext:pdf` |
| `cache:` | Cached version | `cache:target.com` |
| `"..."` | Exact phrase | `"admin panel"` |
| `-` | Exclude | `-site:www` |
| `OR` / `|` | Either term | `admin OR login` |
| `*` | Wildcard | `admin * panel` |

### Top 10 Dorks for Bug Bounty

| # | Dork | Purpose |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Database files |
| 2 | `site:target.com inurl:admin` | Admin panels |
| 3 | `site:target.com filetype:log` | Log files |
| 4 | `site:target.com filetype:env` | Environment files |
| 5 | `site:target.com intext:password filetype:txt` | Passwords |
| 6 | `intitle:"index of" site:target.com` | Directory listing |
| 7 | `site:target.com inurl:api` | API endpoints |
| 8 | `site:target.com filetype:bak` | Backup files |
| 9 | `site:*.target.com -www` | Subdomains |
| 10 | `site:target.com "api_key"` | API keys |

---

## 🛠️ Tools & Automation

### Google Dorking Tools

| Tool | Description | URL |
|------|-------------|-----|
| **Google Hacking Database** | Exploit-DB dork collection | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Dork generator | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Passive Google dorking | GitHub |
| **GooDork** | Python dorking tool | GitHub |
| **GooFuzz** | Fuzzing with Google | GitHub |

### Dorking Websites

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Automation Script Example

```bash
#!/bin/bash
# Simple Google Dork automation

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Start Broad, Go Specific**
   ```
   site:target.com                    # First
   site:target.com filetype:sql       # Then specific
   ```

2. **Use Multiple Search Engines**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Check Cached Pages**
   ```
   cache:target.com/admin
   ```

4. **Look for Subdomains**
   ```
   site:*.target.com -www
   ```

5. **Rate Limit Your Searches**
   - Google may block excessive queries

### Avoid Detection

```
# Use different IPs/VPNs
# Add delays between searches
# Vary your search patterns
# Use different browsers
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Google Dorking itself is legal, but **accessing** exposed data without authorization may be illegal.
> 
> - ✅ Use for authorized security testing
> - ✅ Use for bug bounty programs
> - ✅ Report vulnerabilities responsibly
> - ❌ Never access data without permission
> - ❌ Never download/store sensitive data
> - ❌ Don't exploit found vulnerabilities
> 
> **Always get proper authorization before testing!**

---

## 📚 Resources

### Official Resources
- [Google Search Operators](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Books & Guides
- "Google Hacking for Penetration Testers" by Johnny Long
- OWASP Testing Guide

### Related Cheatsheets
- [Shodan](../Shodan/README.md)
- [GitHub Dorking](../GitHub-Dorking/README.md)
- [Subfinder](../Subfinder/README.md)

---

<p align="center">
  <b>🔍 Find What Others Hide!</b><br>
  <i>Master the art of Google Dorking</i>
</p>
