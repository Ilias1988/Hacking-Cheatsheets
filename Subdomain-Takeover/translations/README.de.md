# 🎯 Cheatsheet zur Subdomain-Übernahme

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## 🎯 Was ist eine Subdomain-Übernahme?

Eine **Subdomain-Übernahme** (Subdomain Takeover) liegt vor, wenn eine Subdomain über einen CNAME-Eintrag auf eine nicht mehr vorhandene Ressource eines Dienstes verweist und ein Angreifer die zugehörige Ressource erneut registrieren kann, um eigene schädliche Inhalte unter dieser Subdomain bereitzustellen.

### Auswirkungen
- 🔴 **Phishing** – Überzeugende Phishing-Seiten bereitstellen
- 🔴 **Cookie-Diebstahl** – Cookies der übergeordneten Domain stehlen
- 🔴 **Kontoübernahme** – OAuth-Callbacks abfangen
- 🔴 **Rufschädigung** – Schädliche Inhalte bereitstellen

---

## 🔍 Vorgehen zur Erkennung

### Schritt 1: Subdomains ermitteln
```bash
# Mehrere Werkzeuge für eine möglichst umfassende Erfassung verwenden
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Ergebnisse zusammenführen und Duplikate entfernen
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Schritt 2: CNAME-Einträge finden
```bash
# DNS-Einträge prüfen
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Alternativ dnsrecon verwenden
dnsrecon -d target.com -t crt
```

### Schritt 3: Verwaiste CNAME-Einträge prüfen
```bash
# Manuelle Prüfung
dig CNAME subdomain.target.com +short
# Rückgabe: something.cloudfront.net
# Anschließend prüfen, ob die Ressource des Dienstes noch nicht vergeben ist

# httpx zum Ermitteln von Statuscodes verwenden
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Automatisierte Werkzeuge

### Subjack
```bash
# Installation
go install github.com/haccer/subjack@latest

# Prüfung
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Mit Fingerprints (Erkennungsmerkmalen)
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (Takeover-Templates)
```bash
# Nuclei-Templates verwenden
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Gezielte Prüfungen auf Subdomain-Übernahmen
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Im GitHub-Repository nach Fingerprints suchen
# https://github.com/EdOverflow/can-i-take-over-xyz

# Die Fingerprints manuell oder mit automatisierten Werkzeugen verwenden
```

---

## 📋 Anfällige Dienste und Fingerprints

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Fingerprint: "NoSuchBucket"
Fingerprint: "The specified bucket does not exist"

Übernahme: Bucket mit demselben Namen erstellen
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Fingerprint: "Bad Request"
Fingerprint: "ERROR: The request could not be satisfied"

Übernahme: CloudFront-Distribution mit derselben alternativen Domain erstellen
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Fingerprint: "There isn't a GitHub Pages site here"

Übernahme: 
1. Repository username.github.io erstellen
2. CNAME-Datei mit der Ziel-Subdomain hinzufügen
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Fingerprint: "No such app"
Fingerprint: "There's nothing here, yet"

Übernahme: Heroku-App mit demselben Namen erstellen
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Fingerprint: "404 - Web Site not found"

Übernahme: Ressource mit demselben Namen erstellen
```

### Shopify
```
CNAME: shops.myshopify.com

Fingerprint: "Sorry, this shop is currently unavailable"
Fingerprint: "Only one step left"

Übernahme: Shopify-Shop erstellen und eigene Domain hinzufügen
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Fingerprint: "Do you want to register *.wordpress.com?"

Übernahme: Subdomain bei WordPress.com registrieren
```

### Zendesk
```
CNAME: xxx.zendesk.com

Fingerprint: "Help Center Closed"
Fingerprint: "This help center no longer exists"

Übernahme: Nicht immer möglich – hängt von der Zendesk-Konfiguration ab
```

### Fastly
```
CNAME: xxx.fastly.net

Fingerprint: "Fastly error: unknown domain"

Übernahme: Domain zum Fastly-Dienst hinzufügen
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Fingerprint: "The gods are wise but do not know of the site"
Fingerprint: "404 error unknown site!"

Übernahme: Website bei Pantheon hinzufügen
```

---

## 🔥 Schritte zur Ausnutzung

### Beispiel: GitHub-Pages-Übernahme

```bash
# 1. Anfällige Subdomain finden
dig CNAME blog.target.com
# Rückgabe: target.github.io (das Repository existiert jedoch nicht)

# 2. GitHub-Repository erstellen
# Erstellen: yourusername/target (oder org/target)

# 3. GitHub Pages in den Repository-Einstellungen aktivieren

# 4. CNAME-Datei mit folgendem Inhalt hinzufügen:
blog.target.com

# 5. Auf die Verbreitung der DNS-Änderungen warten

# 6. Übernahme überprüfen
curl https://blog.target.com
```

### Beispiel: S3-Übernahme

```bash
# 1. Schwachstelle bestätigen
dig CNAME assets.target.com
# Rückgabe: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Rückgabe: NoSuchBucket

# 2. S3-Bucket erstellen (gleiche Region)
aws s3 mb s3://assets-target --region us-east-1

# 3. Bucket konfigurieren
# Website-Hosting oder öffentlichen Zugriff einrichten

# 4. Inhalte hochladen
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Überprüfen
curl http://assets.target.com
```

---

## 📊 Schnellübersicht

### Erkennung anfälliger Dienste

| Dienst | CNAME-Muster | Fingerprint |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Checkliste zur Subdomain-Übernahme

```markdown
- [ ] Alle Subdomains ermitteln
- [ ] CNAME-Einträge identifizieren
- [ ] Auf verwaiste Einträge prüfen
- [ ] Fingerprints abgleichen
- [ ] Prüfen, ob die Ressource des Dienstes noch nicht vergeben ist
- [ ] Übernahme versuchen (im autorisierten Rahmen)
- [ ] Dokumentieren und melden
```

---

## 🛠️ Werkzeugübersicht

| Werkzeug | Zweck |
|------|---------|
| **subjack** | Automatisierte Erkennung möglicher Übernahmen |
| **nuclei** | Prüfungen anhand von Templates |
| **can-i-take-over-xyz** | Nachschlagewerk für Fingerprints |
| **dnsrecon** | DNS-Erfassung |
| **subfinder** | Ermittlung von Subdomains |
| **httpx** | Prüfung der HTTP-Erreichbarkeit |

---

## 📚 Ressourcen

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei-Takeover-Templates](https://github.com/projectdiscovery/nuclei-templates/tree/main/http/takeovers)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Nachweis und Auswirkungen:** Ein verwaister DNS-Eintrag oder eine passende Fehlermeldung ist zunächst ein Verdacht, kein Beweis für eine übernehmbare Ressource. Die tatsächliche Registrierbarkeit und Domainbindung müssen zum jeweiligen Dienst passen. Neben CNAMEs können auch andere DNS-Eintragstypen betroffen sein. Cookie-Diebstahl setzt passende Domain-, Pfad- und Sicherheitsattribute voraus; Host-only-Cookies der Hauptdomain werden nicht automatisch an Subdomains gesendet. OAuth-Auswirkungen hängen vom konkreten Ablauf ab. [OWASP: Subdomain Takeover](https://owasp.github.io/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/02-Configuration_and_Deployment_Management/10-Subdomain_Takeover), [RFC 6265, Abschnitt 4.1.2.3](https://www.rfc-editor.org/rfc/rfc6265.html#section-4.1.2.3).
- **Anbieterprüfungen und historische Fingerprints:** Die Liste ist keine Zusage heutiger Übernehmbarkeit. CloudFront verlangt beim Hinzufügen alternativer Domains ein gültiges, passendes TLS-Zertifikat. GitHub-Domainverifizierung schützt die verifizierte Domain und deren unmittelbare Subdomains; ein fehlendes Repository oder eine CNAME-Datei allein reichen nicht als Nachweis. Azure App Service bietet unter anderem TXT-basierte Domainbindung. [AWS: CloudFront-CNAME-Anforderungen](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html), [GitHub: Domainverifizierung](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages), [Microsoft: Schutz vor Subdomain-Übernahmen](https://learn.microsoft.com/en-us/azure/app-service/reference-dangling-subdomain-prevention).
- **Heroku:** Seit Juni 2023 erhalten neue Apps zusätzliche Kennungen in ihren Standard-Domainnamen. Eine App mit demselben Namen neu anzulegen stellt daher nicht automatisch dasselbe DNS-Ziel wieder her. [Heroku: Custom Domain Names](https://devcenter.heroku.com/articles/custom-domains).
- **S3-Beispiel:** Die Namen im Original passen für den direkten CNAME-Zugriff nicht zusammen: Beim Aufruf von assets.target.com wertet S3 den HTTP-Hostnamen aus, während das Beispiel einen Bucket namens assets-target anlegt. Bei dieser CNAME-Methode muss der Bucketname dem angefragten Hostnamen entsprechen. Auch Region, Endpoint und Zugriffs-/Website-Konfiguration müssen passen; NoSuchBucket allein bestätigt keine erfolgreiche Übernahme. Die ursprünglichen Befehle bleiben zur Nachvollziehbarkeit unverändert. [AWS: S3 Virtual Hosting](https://docs.aws.amazon.com/AmazonS3/latest/userguide/VirtualHosting.html).
- **DNSRecon:** Der Modus -t crt sucht über crt.sh nach Subdomains und Hosts; er ersetzt keine direkte Prüfung der aktuellen CNAME-Einträge. [DNSRecon: offizielle CLI-Definition](https://github.com/darkoperator/dnsrecon/blob/master/dnsrecon/cli.py).
- **Template-Pfade:** Die gezeigten Nuclei-Pfade stammen aus einer älteren Struktur. Das aktuelle offizielle Repository führt dienstspezifische HTTP-Takeover-Templates unter http/takeovers; der im Beispiel genannte allgemeine Dateiname ist dort nicht vorhanden. Der Ressourcenlink verweist auf das aktuelle Verzeichnis, die ursprünglichen Beispielbefehle bleiben erhalten. [ProjectDiscovery: HTTP-Takeover-Templates](https://github.com/projectdiscovery/nuclei-templates/tree/main/http/takeovers).

---

<p align="center">
  <b>🎯 Subdomain-Übernahmen aufdecken!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
