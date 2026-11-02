# ☁️ Hurtigreferanse for omgåelse av Cloudflare

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

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

> **Begreper og versjoner:** Opprinnelsesserveren (origin) er serveren bak Cloudflare. IP-områder, verktøy og API-felter i originalen er historiske eksempler; de er ikke en oppdatert autoritativ liste. E-post-, DNS-, ikon- og sertifikattreff er spor som må bekreftes. Host-headeren alene styrer ikke nødvendigvis TLS/SNI, og curl -k opphever sertifikatkontroll, ikke TLS-kryptering. Å finne IP-adressen eller sende klientvalgte IP-headere garanterer ikke omgåelse av WAF, rate limits eller challenges. [Cloudflares origin-beskyttelse](https://developers.cloudflare.com/fundamentals/security/protect-your-origin-server/) kan blokkere slik direkte tilgang. Kommandoene og IP-verdiene er beholdt.


## 🎯 Identifiser Cloudflare

### Headere
```bash
curl -I https://target.com

# Se etter:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# IP-områder for Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 Finn opprinnelsesserverens IP-adresse

### 1. Verktøy for DNS-historikk

| Tjeneste | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Verktøy på GitHub |

```bash
# Bruk av CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Oppdagelse av underdomener
```bash
# Flere underdomener kan eksponere opprinnelsesserveren
subfinder -d target.com | httpx -ip

# Vanlige underdomener som kan vise opprinnelsesserverens IP-adresse:
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

### 3. Søk etter TLS-sertifikater
```bash
# Søk på Censys.io
# Spørring: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Søk i Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. E-postanalyse
```bash
# Send e-post til en adresse som ikke finnes
# Undersøk headere i returmeldingen

# Eller meld deg på nyhetsbrev
# Undersøk Received:-headere for opprinnelsesserverens IP-adresse

# Se i headerne:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Favicon-hash (Shodan)
```python
import mmh3
import requests
import codecs

# Beregn favicon-hash
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Søk i Shodan: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Historiske WHOIS-data
```bash
# Undersøk historiske WHOIS-data for gamle IP-adresser
# whoxy.com
# domaintools.com
```

### 7. Nettstedets kildekode
```bash
# Se etter hardkodede IP-adresser i:
# - JavaScript-filer
# - HTML-kommentarer
# - API-endepunkter
# - Feilmeldinger

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Undersøk JavaScript-filer
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. MX-oppføringer
```bash
dig MX target.com

# E-postserveren kan ha samme IP-adresse som opprinnelsesserveren
nslookup mail.target.com
```

### 9. SPF-oppføringer
```bash
dig TXT target.com | grep spf

# SPF kan inneholde opprinnelsesserverens IP-adresse
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Direkte tilkobling

### Test den funne IP-adressen
```bash
# Når du har en mulig IP-adresse til opprinnelsesserveren:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Eller rediger /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Besøk deretter nettstedet som vanlig
curl https://target.com
```

### Kontroller opprinnelsesserveren
```bash
# Undersøk om innholdet er det samme
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Undersøk TLS-sertifikatet
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Omgåelse av Cloudflares WAF

### Omgåelse av hastighetsbegrensning
```http
# Varier disse headerne
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Omgåelse av botbeskyttelse
```bash
# Bruk User-Agent fra en virkelig nettleser
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Bruk curl-impersonate
curl_chrome116 https://target.com
```

### Omgåelse av Turnstile eller nettleserutfordring
```bash
# Bruk nettleserautomatisering
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **CloudFlair** | Finn opprinnelsesserver via Censys |
| **CrimeFlare** | Database over opprinnelsesserveres IP-adresser |
| **Bypass-Firewalls-by-DNS-History** | Kontroll av DNS-historikk |
| **FlareSolverr** | Verktøy for nettleserutfordringer |
| **curl-impersonate** | TLS-fingeravtrykk fra nettleser |
| **CloudBunny** | Verktøy for Cloudflare-omgåelse |

### Bruk av CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Angi Censys-API-legitimasjon
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Hurtigreferanse

### Sjekkliste for opprinnelsesserverens IP-adresse
```markdown
- [ ] DNS-historikk (SecurityTrails, ViewDNS)
- [ ] Underdomener (mail, ftp, cpanel, dev)
- [ ] TLS-sertifikater (Censys, crt.sh)
- [ ] Shodan (favicon-hash, TLS)
- [ ] E-postheadere
- [ ] MX-/SPF-oppføringer
- [ ] Analyse av kildekode
- [ ] Historiske WHOIS-data
- [ ] Sammenligning av IP-områder (utenfor Cloudflares områder)
```

### Cloudflares IP-områder
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

## 📚 Ressurser

- [CloudFlare IP Ranges](https://www.cloudflare.com/ips/)
- [CloudFlair Tool](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Finn opprinnelsesserveren!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
