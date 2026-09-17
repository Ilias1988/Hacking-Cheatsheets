# 🐛 Bug Bounty Methodology

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Complete Bug Bounty Hunting Methodology
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Attack Flow Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      BUG BOUNTY HUNTING METHODOLOGY                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  PHASE 1: Subdomain Enum    PHASE 2: ASN & IP        PHASE 3: Live Hosts   │
│  ├── Subfinder              ├── ASN Mapping          ├── HTTPX Probing     │
│  ├── Amass                  ├── IP Harvesting        ├── Port Discovery    │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  PHASE 4: URL Collection    PHASE 5: Vuln Hunting    PHASE 6: Deep Dive    │
│  ├── Katana/Hakrawler       ├── XSS Testing          ├── Directory Brute   │
│  ├── GAU                    ├── LFI/RFI              ├── Parameter Fuzzing │
│  └── Parameter Extract      ├── SSRF/CORS            └── Git Exposure      │
│                             └── Open Redirect                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Table of Contents

1. [Phase 1: Subdomain Enumeration](#-phase-1-subdomain-enumeration)
2. [Phase 2: ASN & IP Discovery](#-phase-2-asn--ip-discovery)
3. [Phase 3: Live Host Discovery](#-phase-3-live-host-discovery)
4. [Phase 4: URL Collection](#-phase-4-url-collection)
5. [Phase 5: Vulnerability Hunting](#-phase-5-vulnerability-hunting)
6. [Phase 6: Directory & File Discovery](#-phase-6-directory--file-discovery)
7. [Quick Reference](#-quick-reference)
8. [Tool Installation](#-tool-installation)

---

## 🔍 Phase 1: Subdomain Enumeration

> **Goal:** Find as many subdomains as possible - the larger the attack surface, the higher the chance of finding vulnerabilities.

### Automated Enumeration Tools

#### Subfinder
```bash
# Fast subdomain discovery using multiple data sources
subfinder -d example.com -all -recursive -o subfinder.txt

# Flags explained:
# -all: Use all sources (free + premium)
# -recursive: Recursive subdomain enumeration
# -o: Output file
```
> 💡 **Tip:** Subfinder is fast and reliable. Use `-all` for comprehensive results.

#### Assetfinder
```bash
# Find domains and subdomains associated with a target
assetfinder --subs-only example.com > assetfinder.txt

# Quick and simple - good for initial recon
```
> 💡 **Tip:** Assetfinder is fast but less comprehensive. Use alongside other tools.

#### Sublist3r
```bash
# Subdomain enumeration using OSINT techniques
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: Specify search engines
# -v: Verbose output
```

#### Amass
```bash
# In-depth attack surface mapping (the powerhouse!)
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Passive mode - doesn't touch the target directly
# Use -active for more results (but noisier)
```
> ⚠️ **Warning:** Active Amass can be noisy. Use passive mode first!

### Public Sources

#### Certificate Transparency (crt.sh)
```bash
# Find subdomains from SSL certificate logs
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Why crt.sh?** SSL certificates are public records - free subdomain intel!

#### Wayback Machine
```bash
# Historical subdomain discovery from web archives
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Pro Tip:** Old subdomains might still exist but be forgotten - easy wins!

### Subdomain Processing

#### Merge & Deduplicate Results
```bash
# Combine all subdomain files and remove duplicates
cat *.txt | sort -u > final_subdomains.txt

# Count your results
wc -l final_subdomains.txt
```

#### FFUF Subdomain Bruteforce
```bash
# Brute force subdomain discovery using wordlists
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: Match only specific status codes
# Wordlist suggestion: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Subdomain Permutation with Alterx
```bash
# Generate subdomain permutations and resolve them
subfinder -d example.com | alterx | dnsx

# Enrich domain with common patterns
echo example.com | alterx -enrich | dnsx

# Use wordlist for permutation
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Example:** If you find `dev.example.com`, alterx generates `dev1`, `dev2`, `dev-api`, etc.

---

## 🌐 Phase 2: ASN & IP Discovery

> **Goal:** Find IP ranges and infrastructure associated with the target organization.

### ASN Mapping

```bash
# Discover IP addresses associated with domain's ASN
asnmap -d example.com | dnsx -silent -resp-only

# Discover assets by organization name
amass intel -org "organization_name"

# Discover assets within IP range
amass intel -active -cidr 159.69.129.82/32

# Discover assets by ASN number
amass intel -active -asn [asnno]
```
> 💡 **Why ASN?** Organizations often own entire IP ranges - find hidden servers!

### IP Harvesting

#### VirusTotal
```bash
# Extract IP addresses from VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Get IP addresses from AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Extract IP addresses from URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Shodan SSL Search
```bash
# Find IP addresses using Shodan SSL certificate search
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Pro Tip:** Shodan SSL search finds servers using the target's SSL cert - even hidden ones!

---

## ✅ Phase 3: Live Host Discovery

> **Goal:** Filter subdomains to find only live, accessible hosts.

### HTTP Probing with HTTPX

```bash
# Basic probing on multiple ports
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Detailed probing with status codes, titles, and tech detection
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Flags:
# -sc: Status code
# -title: Page title
# -server: Server header
# -td: Technology detection
```

### Visual Recon with Aquatone

```bash
# Take screenshots of live hosts
cat hosts.txt | aquatone

# Custom ports
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Extended port range (comprehensive)
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Why Screenshots?** Quickly identify interesting applications, login panels, and error pages.

---

## 🔗 Phase 4: URL Collection

> **Goal:** Gather as many URLs as possible for parameter and vulnerability testing.

### Active Crawling

#### Katana
```bash
# Fast web crawler for URL discovery
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: Depth of 2 levels
# Fast and efficient for modern web apps
```

#### Hakrawler
```bash
# Simple, fast web crawler
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Passive URL Collection

#### GAU (Get All URLs)
```bash
# Fetch known URLs from AlienVault OTX, Wayback Machine, and Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Get URLs with 200 status code and deduplicate
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Why GAU?** Finds URLs from historical data that might reveal hidden endpoints!

#### URLFinder
```bash
# Find URLs from various sources
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Parameter Extraction

```bash
# Extract URLs containing parameters (gold for testing!)
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Parameter pattern matching
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# GF SQLi pattern - URLs potentially vulnerable to SQL injection
cat allurls.txt | gf sqli

# GF XSS pattern
cat allurls.txt | gf xss

# GF LFI pattern
cat allurls.txt | gf lfi
```
> 💡 **GF Patterns:** Install `gf` and download patterns from github.com/1ndianl33t/Gf-Patterns

---

## 🔓 Phase 5: Vulnerability Hunting

### 🔴 XSS Discovery

#### Reflected XSS Testing
```bash
# Comprehensive reflected XSS testing pipeline
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Blind XSS Testing
```bash
# Blind XSS testing with specialized payloads
# Use services like XSS Hunter or Burp Collaborator
# Insert payload in all input fields, headers, and parameters
```
> 💡 **Blind XSS:** Payloads execute later when admin views data - use callback servers!

### 🔴 LFI Discovery

```bash
# LFI testing with FFUF and passwd file detection
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# LFI testing with curl and parallel processing
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# HTTPx LFI test
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **LFI Tip:** Try `/etc/passwd`, `....//....//etc/passwd`, and encoded variants!

### 🔴 Open Redirect Discovery

```bash
# Find redirect parameters
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# GF redirect pattern
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Test redirect parameters
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Full open redirect pipeline
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 SSRF Discovery

```bash
# Find SSRF-prone parameters
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Find API/Webhook patterns
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Nuclei SSRF scan
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Basic SSRF test to localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# Cloud metadata SSRF (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **SSRF Impact:** Can access internal services, cloud metadata, and more!

### 🔴 CORS Testing

```bash
# Test CORS configuration with custom origin
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Detailed CORS analysis
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Automated CORS scanning with Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS Vuln:** If `Access-Control-Allow-Origin: *` with credentials = Critical!

### Nuclei Vulnerability Scanning

```bash
# Single target
nuclei -u https://example.com -bs 50 -c 30

# Multiple targets
nuclei -l live_domains.txt -bs 50 -c 30

# Only critical and high severity
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: Bulk size
# -c: Concurrency
```

---

## 📂 Phase 6: Directory & File Discovery

### Sensitive File Discovery

```bash
# Basic sensitive file extensions
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Extended sensitive file regex
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Google Dork for files
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Directory Bruteforcing

#### Dirsearch
```bash
# Basic directory discovery
dirsearch -u https://example.com --full-url --deep-recursive -r

# Extended with multiple extensions
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### FFUF Directory Discovery
```bash
# Comprehensive FFUF directory bruteforce
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Git Exposure Detection

```bash
# Detect exposed .git directories
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Check for .git/config
curl -s https://example.com/.git/config

# Dump git repo if exposed
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Git Exposure:** Can expose source code, credentials, and secrets!

### Hidden Parameter Discovery with Arjun

```bash
# Passive parameter discovery
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Active parameter discovery with wordlist
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Subdomain Takeover

```bash
# Automated subdomain takeover detection
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Takeover Signs:** CNAME pointing to unclaimed services (S3, Heroku, etc.)

### WordPress Testing

```bash
# Full WordPress security scan
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: All themes
# -e ap: All plugins
# -e u: Users
```

---

## 📊 Quick Reference

### Recon Pipeline

```bash
# 1. Subdomain Enumeration
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Live Host Discovery
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. URL Collection
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. Parameter URLs
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Vulnerability Scanning
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Essential One-Liners

| Purpose | Command |
|---------|---------|
| Find subdomains | `subfinder -d target.com -silent` |
| Check live hosts | `cat subs.txt \| httpx -silent` |
| Get all URLs | `echo target.com \| gau` |
| Find params | `cat urls.txt \| grep '='` |
| Test redirects | `cat urls.txt \| gf redirect` |
| Nuclei scan | `nuclei -l hosts.txt -s critical,high` |

### File Extensions to Hunt

| Type | Extensions |
|------|------------|
| **Config** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Backup** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Source** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Data** | `.sql, .db, .sqlite, .json, .xml` |
| **Docs** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

## 🛠️ Tool Installation

### Essential Tools

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### XSS & Redirect Tools

```bash
# OR (Open Redirect Tester)
go install -v github.com/h6nt3r/or@latest

# RC (Reflected XSS)
go install -v github.com/h6nt3r/rc@latest

# CXSS (Contextual XSS Scanner)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (Grep Patterns)
go install github.com/tomnomnom/gf@latest
```

### Utility Tools

```bash
# URO (URL Deduplication)
pip3 install uro

# Arjun (Parameter Discovery)
pip3 install arjun

# Subzy (Takeover Detection)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Bug Bounty Tips

1. **Automate, but verify manually** - Tools find candidates, you confirm vulns
2. **Check robots.txt and sitemap.xml** - Often reveal hidden paths
3. **Look at JavaScript files** - API endpoints, secrets, hardcoded creds
4. **Test all parameters** - Even hidden ones (use Arjun)
5. **Chain vulnerabilities** - SSRF + XSS = Higher impact
6. **Report quality matters** - Clear PoC, impact, reproduction steps
7. **Be patient** - Good bugs take time to find

---

## 📚 Related Cheatsheets

- [Nuclei](../Nuclei/README.md)
- [ffuf](../ffuf/README.md)
- [Subfinder](../Subfinder/README.md)
- [httpx](../httpx/README.md)
- [Google Dorking](../Google-Dorking/README.md)
- [Burp Suite](../Burp-Suite/README.md)

---

<p align="center">
  <b>🐛 Happy Hunting!</b><br>
  <i>Bug Bounty - Where persistence meets payouts</i>
</p>
