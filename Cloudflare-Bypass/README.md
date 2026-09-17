# ☁️ Cloudflare Bypass Cheatsheet

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🎯 Detect Cloudflare

### Headers
```bash
curl -I https://target.com

# Look for:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Cloudflare IP ranges:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Find Origin IP

### 1. DNS History Tools

| Service | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | GitHub tool |

```bash
# Using CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Subdomain Discovery
```bash
# Many subdomains might expose origin
subfinder -d target.com | httpx -ip

# Common subdomains with origin IP:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. SSL Certificate Search
```bash
# Censys.io search
# Query: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Search Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Email Analysis
```bash
# Send email to non-existent address
# Check bounce-back headers

# Or register for newsletters
# Check Received: headers for origin IP

# Look in headers:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Favicon Hash (Shodan)
```python
import mmh3
import requests
import codecs

# Calculate favicon hash
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Search Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Historical WHOIS
```bash
# Check historical WHOIS for old IPs
# whoxy.com
# domaintools.com
```

### 7. Website Source Code
```bash
# Check for hardcoded IPs in:
# - JavaScript files
# - HTML comments
# - API endpoints
# - Error messages

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Check JS files
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. MX Records
```bash
dig MX target.com

# Mail server might be on same IP as origin
nslookup mail.target.com
```

### 9. SPF Records
```bash
dig TXT target.com | grep spf

# SPF might include origin IP
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Direct Connection

### Test Found IP
```bash
# Once you have potential origin IP:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Or edit /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Then browse normally
curl https://target.com
```

### Verify Origin
```bash
# Check if same content
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Check SSL certificate
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Cloudflare WAF Bypass

### Rate Limiting Bypass
```http
# Rotate these headers
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Bot Protection Bypass
```bash
# Use real browser user-agent
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Use curl-impersonate
curl_chrome116 https://target.com
```

### Turnstile/Challenge Bypass
```bash
# Use browser automation
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **CloudFlair** | Find origin via Censys |
| **CrimeFlare** | Database of origin IPs |
| **Bypass-Firewalls-by-DNS-History** | DNS history check |
| **FlareSolverr** | Browser challenge solver |
| **curl-impersonate** | Browser TLS fingerprint |
| **CloudBunny** | Cloudflare bypass tool |

### CloudFlair Usage
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Set Censys API credentials
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Quick Reference

### Origin IP Checklist
```markdown
- [ ] DNS History (SecurityTrails, ViewDNS)
- [ ] Subdomains (mail, ftp, cpanel, dev)
- [ ] SSL Certificates (Censys, crt.sh)
- [ ] Shodan (favicon hash, SSL)
- [ ] Email headers
- [ ] MX/SPF records
- [ ] Source code analysis
- [ ] Historical WHOIS
- [ ] IP range comparison (not in CF ranges)
```

### Cloudflare IP Ranges
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

## 📚 Resources

- [CloudFlare IP Ranges](https://www.cloudflare.com/ips/)
- [CloudFlair Tool](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Find the Origin!</b><br>
  <i>For authorized testing only!</i>
</p>
