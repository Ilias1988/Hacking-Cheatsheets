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

## 🎯 Rilevare Cloudflare

### Header
```bash
curl -I https://target.com

# Cerca:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Range IP Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Trovare l'IP di Origine

### 1. Strumenti di Storico DNS

| Servizio | URL |
|----------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Tool GitHub |

```bash
# Usando CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Scoperta Subdomain
```bash
# Molti subdomain possono esporre l'origine
subfinder -d target.com | httpx -ip

# Subdomain comuni con IP origine:
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

### 3. Ricerca Certificati SSL
```bash
# Ricerca su Censys.io
# Query: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Cerca su Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Analisi Email
```bash
# Invia email a indirizzo inesistente
# Controlla header di bounce-back

# O registrati a newsletter
# Controlla header Received: per IP origine

# Cerca negli header:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (a volte contiene origine)
```

### 5. Hash Favicon (Shodan)
```python
import mmh3
import requests
import codecs

# Calcola hash favicon
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Cerca su Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. WHOIS Storico
```bash
# Controlla WHOIS storico per vecchi IP
# whoxy.com
# domaintools.com
```

### 7. Codice Sorgente Sito
```bash
# Cerca IP hardcoded in:
# - File JavaScript
# - Commenti HTML
# - Endpoint API
# - Messaggi di errore

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Controlla file JS
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. Record MX
```bash
dig MX target.com

# Il mail server potrebbe essere sull'IP di origine
nslookup mail.target.com
```

### 9. Record SPF
```bash
dig TXT target.com | grep spf

# SPF potrebbe includere IP origine
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Connessione Diretta

### Testa l'IP trovato
```bash
# Una volta trovato un IP di origine:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# O modifica /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Poi naviga normalmente
curl https://target.com
```

### Verifica Origine
```bash
# Controlla se il contenuto è lo stesso
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Controlla certificato SSL
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Bypass di Cloudflare WAF

### Bypass del Rate Limiting
```http
# Ruota questi header
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Bypass Bot Protection
```bash
# Usa user-agent reale browser
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Usa curl-impersonate
curl_chrome116 https://target.com
```

### Bypass Turnstile/Challenge
```bash
# Usa automazione browser
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|-----------|-------|
| **CloudFlair** | Trova origine via Censys |
| **CrimeFlare** | Database IP origine |
| **Bypass-Firewalls-by-DNS-History** | Storico DNS |
| **FlareSolverr** | Risolvi challenge browser |
| **curl-impersonate** | Fingerprint TLS browser |
| **CloudBunny** | Tool bypass Cloudflare |

### Uso CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Imposta credenziali API Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Riferimento Rapido

### Checklist IP Origine
```markdown
- [ ] Storico DNS (SecurityTrails, ViewDNS)
- [ ] Subdomain (mail, ftp, cpanel, dev)
- [ ] Certificati SSL (Censys, crt.sh)
- [ ] Shodan (favicon hash, SSL)
- [ ] Header email
- [ ] Record MX/SPF
- [ ] Analisi codice sorgente
- [ ] WHOIS storico
- [ ] Confronto range IP (non CF)
```

### Range IP Cloudflare
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

## 📚 Risorse

- [CloudFlare IP Ranges](https://www.cloudflare.com/ips/)
- [CloudFlair Tool](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Trova l'origine!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
