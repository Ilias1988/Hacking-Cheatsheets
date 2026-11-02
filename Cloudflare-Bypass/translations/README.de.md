# ☁️ Cheatsheet: Cloudflare-Schutz umgehen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Cloudflare erkennen

### Header
```bash
curl -I https://target.com

# Auf folgende Header achten:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Cloudflare-IP-Bereiche:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

## 🌐 IP-Adresse des Ursprungsservers finden

### 1. Werkzeuge für historische DNS-Daten

| Dienst | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Werkzeug auf GitHub |

```bash
# CloudFlair verwenden
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Subdomains ermitteln
```bash
# Viele Subdomains können den Ursprungsserver offenlegen
subfinder -d target.com | httpx -ip

# Häufige Subdomains mit der IP-Adresse des Ursprungsservers:
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

### 3. Nach SSL-Zertifikaten suchen
```bash
# Suche auf Censys.io
# Query: parsed.names: target.com AND NOT ip: 104.*

# Certificate Transparency (öffentlich protokollierte Zertifikate)
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Mit Shodan suchen
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. E-Mails analysieren
```bash
# Eine E-Mail an eine nicht vorhandene Adresse senden
# Header der Unzustellbarkeitsmeldung prüfen

# Alternativ für Newsletter registrieren
# Received:-Header auf die IP-Adresse des Ursprungsservers prüfen

# In den Headern nachsehen:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Favicon-Hash (Shodan)
```python
import mmh3
import requests
import codecs

# Favicon-Hash berechnen
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Auf Shodan suchen: http.favicon.hash:{hash}
```

```bash
# Shodan CLI
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Historische WHOIS-Daten
```bash
# Historische WHOIS-Daten auf frühere IP-Adressen prüfen
# whoxy.com
# domaintools.com
```

### 7. Quelltext der Website
```bash
# Nach fest eingetragenen IP-Adressen suchen in:
# - JavaScript-Dateien
# - HTML-Kommentaren
# - API-Endpunkten
# - Fehlermeldungen

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# JavaScript-Dateien prüfen
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. MX-Einträge
```bash
dig MX target.com

# Mailserver und Ursprungsserver können dieselbe IP-Adresse verwenden
nslookup mail.target.com
```

### 9. SPF-Einträge
```bash
dig TXT target.com | grep spf

# SPF kann die IP-Adresse des Ursprungsservers enthalten
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

## 🔥 Direkte Verbindung

### Gefundene IP-Adresse prüfen
```bash
# Sobald eine mögliche IP-Adresse des Ursprungsservers bekannt ist:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Alternativ /etc/hosts bearbeiten
echo "ORIGIN_IP target.com" >> /etc/hosts

# Danach wie gewohnt aufrufen
curl https://target.com
```

### Ursprungsserver überprüfen
```bash
# Prüfen, ob derselbe Inhalt zurückgegeben wird
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# SSL-Zertifikat prüfen
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

## 🛡️ Cloudflare-WAF umgehen

### Rate Limiting umgehen
```http
# Die Werte dieser Header variieren
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Bot-Schutz umgehen
```bash
# User-Agent eines echten Browsers verwenden
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# curl-impersonate verwenden
curl_chrome116 https://target.com
```

### Turnstile und Challenges umgehen
```bash
# Browserautomatisierung verwenden
# - Puppeteer + Stealth-Plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr (Docker)
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **CloudFlair** | Ursprungsserver über Censys finden |
| **CrimeFlare** | Datenbank mit IP-Adressen von Ursprungsservern |
| **Bypass-Firewalls-by-DNS-History** | Historische DNS-Daten prüfen |
| **FlareSolverr** | Browser-Challenges lösen |
| **curl-impersonate** | TLS-Fingerabdruck eines Browsers nachbilden |
| **CloudBunny** | Werkzeug zum Umgehen von Cloudflare |

### CloudFlair verwenden
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Censys-API-Zugangsdaten setzen
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

## 📊 Schnellübersicht

### Checkliste zur IP-Adresse des Ursprungsservers
```markdown
- [ ] Historische DNS-Daten (SecurityTrails, ViewDNS)
- [ ] Subdomains (mail, ftp, cpanel, dev)
- [ ] SSL-Zertifikate (Censys, crt.sh)
- [ ] Shodan (favicon hash, SSL)
- [ ] E-Mail-Header
- [ ] MX-/SPF-Einträge
- [ ] Quelltextanalyse
- [ ] Historische WHOIS-Daten
- [ ] IP-Bereiche vergleichen (außerhalb der Cloudflare-Bereiche)
```

### Cloudflare-IP-Bereiche
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

## 📚 Weiterführende Quellen

- [Cloudflare-IP-Bereiche](https://www.cloudflare.com/ips/)
- [CloudFlair-Werkzeug](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

## 📝 Hinweise zur Einordnung der Originalbeispiele

- **IP-Bereiche:** Der frühe DNS-Block enthält noch den historischen Bereich 104.16.0.0/12. Cloudflare ersetzte ihn 2021 durch 104.16.0.0/13 und 104.24.0.0/14; beide stehen bereits in der späteren Liste. Die Vorlage nennt nur IPv4. Für aktuelle IPv4- und IPv6-Bereiche ist die [offizielle Cloudflare-Liste](https://www.cloudflare.com/ips/) maßgeblich.
- **Indizien und Nachweis:** Alte DNS-Daten, E-Mail-Header, Zertifikate oder gleiche Favicons liefern Kandidaten, keinen sicheren Nachweis des aktuellen Web-Ursprungsservers. Auch die Inhaltsähnlichkeit allein beweist keine Umgehung. Eine direkte Verbindung setzt voraus, dass der Dienst tatsächlich erreichbar ist; Cloudflare beschreibt Netzwerkbeschränkungen, Authenticated Origin Pulls und Tunnel als Schutzmaßnahmen. [Cloudflare: Ursprungsserver schützen](https://developers.cloudflare.com/fundamentals/security/protect-your-origin-server/)
- **HTTPS und SNI:** Ein HTTP-Host-Header ersetzt nicht den TLS-Servernamen. Beim Aufruf einer IP-Adresse können deshalb andere virtuelle Hosts oder TLS-Fehler auftreten. Die Option -k schaltet die Zertifikatsprüfung ab; sie korrigiert weder den Servernamen noch belegt sie die Identität des Servers. Die Beispiele bleiben unverändert. [curl: Verbindungsziel und TLS](https://curl.se/docs/manpage.html#--connect-to), [curl: --insecure](https://curl.se/docs/manpage.html#-k)
- **Header und Schutzmechanismen:** Beliebig gesetzte IP-Header garantieren keine Umgehung von Cloudflare-Rate-Limits. Entscheidend ist, welchen vertrauenswürdigen Proxy beziehungsweise welche Information die konkrete Anwendung auswertet. Cloudflare setzt beziehungsweise ergänzt Header beim Weiterleiten zum Ursprung; eine unsichere Vertrauenskette am Server ist gesondert zu prüfen. [Cloudflare: HTTP-Header](https://developers.cloudflare.com/fundamentals/reference/http-headers/)
- **Historische Werkzeuge und Challenges:** Suchsyntax, Zugangsdaten und verfügbare Versionen hängen vom jeweiligen Dienst ab. CloudFlair weist darauf hin, dass kostenlose Censys-Konten seit Ende 2024 keinen benötigten API-Zugriff mehr bieten. Browserautomatisierung und ein User-Agent sind keine garantierte Turnstile-Umgehung; die Anwendung muss Tokens serverseitig prüfen. FlareSolverr dokumentiert zudem Grenzen seiner CAPTCHA-Unterstützung. [CloudFlair](https://github.com/christophetd/CloudFlair), [Cloudflare: Turnstile-Validierung](https://developers.cloudflare.com/turnstile/get-started/server-side-validation/), [FlareSolverr: CAPTCHA Solver](https://github.com/FlareSolverr/FlareSolverr#captcha-solvers)

---

<p align="center">
  <b>☁️ Den Ursprungsserver finden!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
