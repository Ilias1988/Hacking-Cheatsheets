# 🐛 Bug-Bounty-Methodik

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Vollständige Methodik für die Bug-Bounty-Suche
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Aufklärung">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Schwachstellen">
</p>

---

## 🗺️ Übersicht des Testablaufs

```
┌──────────────────────────────────────────────────────────┐
│ BUG-BOUNTY-METHODIK                                      │
│ PHASE 1: Subdomains erfassen                             │
│ Subfinder; Amass; crt.sh; Wayback                        │
│ PHASE 2: ASN und IP-Adressen                             │
│ ASN-Zuordnung; IP-Sammlung; Shodan                       │
│ PHASE 3: Erreichbare Hosts                               │
│ HTTPX-Prüfung; Ports ermitteln; Aquatone                 │
│ PHASE 4: URLs sammeln                                    │
│ Katana/Hakrawler; GAU; Parameter extrahieren             │
│ PHASE 5: Schwachstellen suchen                           │
│ XSS; LFI/RFI; SSRF/CORS; offene Weiterleitungen          │
│ PHASE 6: Vertiefte Analyse                               │
│ Verzeichnissuche; Parameter-Fuzzing; offengelegtes Git   │
└──────────────────────────────────────────────────────────┘
```

---

## 📋 Inhaltsverzeichnis

1. [Phase 1: Subdomains erfassen](#-phase-1-subdomains-erfassen)
2. [Phase 2: ASN und IP-Adressen ermitteln](#-phase-2-asn-und-ip-adressen-ermitteln)
3. [Phase 3: Erreichbare Hosts ermitteln](#-phase-3-erreichbare-hosts-ermitteln)
4. [Phase 4: URLs sammeln](#-phase-4-urls-sammeln)
5. [Phase 5: Schwachstellen suchen](#-phase-5-schwachstellen-suchen)
6. [Phase 6: Verzeichnisse und Dateien finden](#-phase-6-verzeichnisse-und-dateien-finden)
7. [Kurzreferenz](#-kurzreferenz)
8. [Werkzeuge installieren](#-werkzeuge-installieren)

---

## 🔍 Phase 1: Subdomains erfassen

> **Ziel:** Möglichst viele Subdomains finden. Eine größere Angriffsfläche bietet mehr Ansatzpunkte für die Suche nach Schwachstellen.

### Werkzeuge zur automatisierten Enumeration

#### Subfinder
```bash
# Schnelle Subdomain-Suche über mehrere Datenquellen
subfinder -d example.com -all -recursive -o subfinder.txt

# Erläuterung der Optionen:
# -all: Alle Quellen verwenden (kostenlose und kostenpflichtige)
# -recursive: Rekursive Subdomain-Erfassung laut Vorlage
# -o: Ausgabedatei
```
> 💡 **Tipp:** Subfinder arbeitet laut Vorlage schnell und zuverlässig. `-all` erweitert die abgefragten Quellen.

#### Assetfinder
```bash
# Zugeordnete Domains und Subdomains ermitteln
assetfinder --subs-only example.com > assetfinder.txt

# Schnell und einfach für die erste Aufklärung
```
> 💡 **Tipp:** Assetfinder arbeitet schnell, liefert aber weniger umfassende Ergebnisse. Mit anderen Werkzeugen kombinieren.

#### Sublist3r
```bash
# Subdomains mit OSINT-Verfahren erfassen
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: Suchmaschinen festlegen
# -v: Ausführliche Ausgabe
```

#### Amass
```bash
# Angriffsfläche ausführlich erfassen
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Passiver Modus ohne direkte Zielinteraktion laut Vorlage
# -active für weitere Ergebnisse verwenden (erzeugt mehr Anfragen)
```
> ⚠️ **Hinweis:** Aktives Amass kann viele Anfragen erzeugen. Zuerst den passiven Modus verwenden.

### Öffentliche Quellen

#### Certificate Transparency (öffentliche Zertifikatsprotokolle, crt.sh)
```bash
# Subdomains aus SSL-Zertifikatsprotokollen ermitteln
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Warum crt.sh?** Öffentliche Zertifikatsprotokolle liefern kostenlose Hinweise auf Subdomains.

#### Wayback Machine
```bash
# Historische Subdomains aus Webarchiven ermitteln
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Praxistipp:** Alte Subdomains können weiterhin existieren, obwohl sie nicht mehr gepflegt werden.

### Subdomain-Ergebnisse aufbereiten

#### Ergebnisse zusammenführen und Duplikate entfernen
```bash
# Alle Subdomain-Dateien zusammenführen und Duplikate entfernen
cat *.txt | sort -u > final_subdomains.txt

# Ergebnisse zählen
wc -l final_subdomains.txt
```

#### Subdomains mit FFUF systematisch ausprobieren
```bash
# Subdomains anhand von Wortlisten systematisch ausprobieren
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: Nur bestimmte Statuscodes berücksichtigen
# Wortlistenvorschlag: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Subdomain-Varianten mit Alterx erzeugen
```bash
# Subdomain-Varianten erzeugen und per DNS auflösen
subfinder -d example.com | alterx | dnsx

# Domain um häufige Muster ergänzen
echo example.com | alterx -enrich | dnsx

# Wortliste zur Variantenbildung verwenden
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Beispiel:** Aus `dev.example.com` kann alterx Varianten wie `dev1`, `dev2` oder `dev-api` ableiten.

---

## 🌐 Phase 2: ASN und IP-Adressen ermitteln

> **Ziel:** IP-Adressbereiche und Infrastruktur ermitteln, die der Zielorganisation zugeordnet sind.

### ASN-Zuordnung

```bash
# IP-Adressen mit Bezug zur ASN der Domain ermitteln
asnmap -d example.com | dnsx -silent -resp-only

# Ressourcen anhand des Organisationsnamens ermitteln
amass intel -org "organization_name"

# Ressourcen innerhalb eines IP-Adressbereichs ermitteln
amass intel -active -cidr 159.69.129.82/32

# Ressourcen anhand der ASN ermitteln
amass intel -active -asn [asnno]
```
> 💡 **Warum ASN?** Organisationen können ganze IP-Adressbereiche besitzen. Deren Zuordnung kann auf weitere Server hinweisen.

### IP-Adressen sammeln

#### VirusTotal
```bash
# IP-Adressen aus VirusTotal extrahieren
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# IP-Adressen aus AlienVault Open Threat Exchange abrufen
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# IP-Adressen aus URLScan.io extrahieren
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Shodan-Suche nach SSL-Zertifikaten
```bash
# IP-Adressen über die Shodan-SSL-Zertifikatssuche finden
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Praxistipp:** Die Shodan-Zertifikatssuche kann weitere Server mit Zertifikatsbezug zum Ziel aufzeigen.

---

## ✅ Phase 3: Erreichbare Hosts ermitteln

> **Ziel:** Die Subdomain-Liste auf antwortende, erreichbare Hosts eingrenzen.

### HTTP-Erreichbarkeit mit HTTPX prüfen

```bash
# Erreichbarkeit auf mehreren Ports prüfen
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Prüfung mit Statuscodes, Seitentiteln und Technologieerkennung
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Optionen:
# -sc: Statuscode
# -title: Seitentitel
# -server: Server-Header
# -td: Technologieerkennung
```

### Visuelle Aufklärung mit Aquatone

```bash
# Bildschirmaufnahmen erreichbarer Hosts erstellen
cat hosts.txt | aquatone

# Eigene Ports
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Erweiterte Portauswahl
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Warum Bildschirmaufnahmen?** Interessante Anwendungen, Anmeldeseiten und Fehlerseiten lassen sich damit schnell erkennen.

---

## 🔗 Phase 4: URLs sammeln

> **Ziel:** Möglichst viele URLs für Parameter- und Schwachstellentests sammeln.

### Aktives Crawling

#### Katana
```bash
# Schneller Webcrawler zur URL-Suche
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: Tiefe von zwei Ebenen
# Laut Vorlage schnell und effizient für moderne Webanwendungen
```

#### Hakrawler
```bash
# Einfacher, schneller Webcrawler
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Passive URL-Sammlung

#### GAU (Get All URLs)
```bash
# Bekannte URLs aus AlienVault OTX, Wayback Machine und Common Crawl abrufen
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# URLs mit Statuscode 200 abrufen und Duplikate entfernen
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Warum GAU?** Historische Daten können auf URLs und weniger sichtbare Endpunkte hinweisen.

#### URLFinder
```bash
# URLs aus verschiedenen Quellen ermitteln
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Parameter extrahieren

```bash
# URLs mit Parametern für weitere Tests extrahieren
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Parametermuster abgleichen
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# GF-SQLi-Muster: URLs mit möglichen Ansatzpunkten für SQL-Injection
cat allurls.txt | gf sqli

# GF-XSS-Muster
cat allurls.txt | gf xss

# GF-LFI-Muster
cat allurls.txt | gf lfi
```
> 💡 **GF-Muster:** `gf` installieren und Muster von github.com/1ndianl33t/Gf-Patterns beziehen.

---

## 🔓 Phase 5: Schwachstellen suchen

### 🔴 XSS suchen

#### Reflected XSS prüfen
```bash
# Verarbeitungskette für Reflected-XSS-Tests laut Vorlage
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Blind XSS prüfen
```bash
# Blind XSS mit speziellen Payloads prüfen
# Dienste wie XSS Hunter oder Burp Collaborator verwenden
# Payload in Eingabefelder, Header und Parameter einsetzen
```
> 💡 **Blind XSS:** Die Payload kann erst später ausgeführt werden, etwa wenn ein Administrator Daten ansieht. Ein Callback-Dienst kann dies sichtbar machen.

### 🔴 LFI suchen

```bash
# LFI mit FFUF und Erkennung von passwd-Inhalten prüfen
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# LFI mit curl und paralleler Verarbeitung prüfen
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# LFI-Test mit HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **LFI-Tipp:** Die Vorlage nennt `/etc/passwd`, `....//....//etc/passwd` und kodierte Varianten als Testwerte.

### 🔴 Offene Weiterleitungen suchen

```bash
# Weiterleitungsparameter finden
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# GF-Weiterleitungsmuster
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Weiterleitungsparameter prüfen
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Vollständige Verarbeitungskette für offene Weiterleitungen
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 SSRF suchen

```bash
# Parameter mit möglichen SSRF-Ansatzpunkten finden
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# API- und Webhook-Muster finden
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# SSRF-Scan mit Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Einfacher SSRF-Test gegen localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF auf Cloud-Metadaten (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **SSRF-Auswirkungen:** Je nach Umgebung kann der Zugriff auf interne Dienste, Cloud-Metadaten und weitere Ressourcen möglich sein.

### 🔴 CORS prüfen

```bash
# CORS-Konfiguration mit eigener Origin prüfen
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Ausführliche CORS-Analyse
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Automatisierte CORS-Prüfung mit Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS-Aussage der Vorlage:** `Access-Control-Allow-Origin: *` zusammen mit Zugangsdaten wird als kritisch bezeichnet. Diese pauschale Aussage ist unten korrigiert.

### Schwachstellenscans mit Nuclei

```bash
# Einzelnes Ziel
nuclei -u https://example.com -bs 50 -c 30

# Mehrere Ziele
nuclei -l live_domains.txt -bs 50 -c 30

# Nur kritische Befunde und Befunde mit hohem Schweregrad
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: Größe der Zielgruppenverarbeitung (Bulk Size)
# -c: Parallelität
```

---

## 📂 Phase 6: Verzeichnisse und Dateien finden

### Sensible Dateien finden

```bash
# Grundlegende Dateiendungen für die Suche nach sensiblen Dateien
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Erweiterter regulärer Ausdruck für sensible Dateien
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Google-Suchabfrage für Dateien
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Verzeichnisse systematisch ausprobieren

#### Dirsearch
```bash
# Einfache Verzeichnissuche
dirsearch -u https://example.com --full-url --deep-recursive -r

# Erweiterte Suche mit mehreren Dateiendungen
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Verzeichnisse mit FFUF finden
```bash
# Umfassende Verzeichnissuche mit FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Offengelegte Git-Verzeichnisse erkennen

```bash
# Offengelegte .git-Verzeichnisse erkennen
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Auf .git/config prüfen
curl -s https://example.com/.git/config

# Offengelegtes Git-Repository auslesen
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Offengelegtes Git:** Dabei können Quellcode, Zugangsdaten und Secrets zugänglich werden.

### Verborgene Parameter mit Arjun finden

```bash
# Passive Parametersuche
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Aktive Parametersuche mit Wortliste
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Übernahme von Subdomains (Subdomain Takeover)

```bash
# Mögliche Subdomain-Übernahmen automatisiert erkennen
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Hinweise auf eine mögliche Übernahme:** CNAME-Einträge verweisen auf nicht mehr belegte Dienste wie S3 oder Heroku.

### WordPress prüfen

```bash
# Umfassender WordPress-Sicherheitsscan
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: Alle Themes
# -e ap: Alle Plugins
# -e u: Benutzer
```

---

## 📊 Kurzreferenz

### Aufklärung als Verarbeitungskette

```bash
# 1. Subdomains erfassen
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Erreichbare Hosts ermitteln
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. URLs sammeln
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URLs mit Parametern
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Schwachstellenscans
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Wichtige Einzeiler

| Zweck | Befehl |
|---------|---------|
| Subdomains finden | `subfinder -d target.com -silent` |
| Erreichbare Hosts prüfen | `cat subs.txt \| httpx -silent` |
| URLs sammeln | `echo target.com \| gau` |
| Parameter finden | `cat urls.txt \| grep '='` |
| Weiterleitungen prüfen | `cat urls.txt \| gf redirect` |
| Nuclei-Scan | `nuclei -l hosts.txt -s critical,high` |

### Interessante Dateiendungen

| Art | Dateiendungen |
|------|------------|
| **Konfiguration** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Sicherung** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Quellcode** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Daten** | `.sql, .db, .sqlite, .json, .xml` |
| **Dokumente** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a name="-werkzeuge-installieren"></a>

## 🛠️ Werkzeuge installieren

### Wichtige Werkzeuge

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### Werkzeuge für XSS und Weiterleitungen

```bash
# OR (Werkzeug für offene Weiterleitungen)
go install -v github.com/h6nt3r/or@latest

# RC (Reflected XSS)
go install -v github.com/h6nt3r/rc@latest

# CXSS (kontextbezogener XSS-Scanner)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (Grep-Muster)
go install github.com/tomnomnom/gf@latest
```

### Hilfswerkzeuge

```bash
# URO (URL-Duplikate entfernen)
pip3 install uro

# Arjun (Parametersuche)
pip3 install arjun

# Subzy (Übernahmeerkennung)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Tipps für Bug Bounty

1. **Automatisieren und manuell bestätigen** – Werkzeuge liefern Kandidaten; die Schwachstelle muss bestätigt werden.
2. **robots.txt und sitemap.xml prüfen** – Diese Dateien können weniger sichtbare Pfade enthalten.
3. **JavaScript-Dateien ansehen** – Sie können API-Endpunkte, Secrets und fest hinterlegte Zugangsdaten enthalten.
4. **Alle Parameter prüfen** – Auch verborgene Parameter berücksichtigen, etwa mit Arjun.
5. **Schwachstellen verknüpfen** – SSRF und XSS können sich je nach Kontext in ihrer Wirkung ergänzen.
6. **Gute Berichte sind wichtig** – Klaren PoC, Auswirkungen und Reproduktionsschritte angeben.
7. **Geduld haben** – Aussagekräftige Schwachstellen lassen sich nicht immer schnell finden.

---

## 📚 Verwandte Spickzettel

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [Google Dorking](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.de.md)

---

<p align="center">
  <b>🐛 Viel Erfolg bei der Suche!</b><br>
  <i>Bug Bounty – Ausdauer und sorgfältige Arbeit zählen</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Zuordnung:** *Enumeration* bedeutet systematisches Erfassen. Eine *ASN* kennzeichnet ein autonomes System; *Crawling* verfolgt Links, *Fuzzing* variiert Eingaben. Eine Domain-, ASN-, Zertifikats- oder IP-Zuordnung beweist keine Eigentümerschaft und erweitert nicht automatisch den vereinbarten Prüfumfang. Historische Daten können veraltet sein; eine ausbleibende Antwort beweist nicht, dass ein Host nicht existiert.

> **Korrektur zu CORS:** Die Kombination aus `Access-Control-Allow-Origin: *` und erlaubten Zugangsdaten ist nicht pauschal eine kritische Datenfreigabe: Bei einer Anfrage mit Credentials akzeptiert der Browser den Wildcard-Wert nicht als Freigabe zum Lesen der Antwort. Die tatsächlichen Auswirkungen hängen von Origin-Prüfung, Credentials-Modus und zugänglichen Daten ab. Die vereinfachte Originalaussage ersetzt keinen Nachweis. [WHATWG: Fetch/CORS](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials).

> **Werkzeuge und Syntax:** Subfinders `-recursive` beschränkt die Quellen auf solche, die rekursive Subdomain-Abfragen unterstützen; es ist keine allgemeine Garantie einer vollständigen rekursiven Suche. `-all` ersetzt keine benötigten API-Schlüssel. Werkzeugaufrufe, API-Versionen und Nuclei-Templatepfade sind gegen die eingesetzte Version zu prüfen. Die Verbindung einer ASN-/CIDR-Ausgabe mit einem DNS-Werkzeug setzt kompatible Eingabeformate voraus. [Subfinder: Optionen](https://github.com/projectdiscovery/subfinder).

> **Heuristiken und Verarbeitungsketten:** Die als Reflected-XSS-Test bezeichnete lange Pipeline sammelt und filtert vor allem URLs; sie weist keine ausgeführte XSS-Payload nach. Treffer für Parameter, Dateiendungen, Weiterleitungsziele oder Zeichenfolgen wie `root:x` sind Untersuchungshinweise. Die crt.sh-Regex beschränkt Namensformen; sie bildet weder sämtliche Subdomain-Ebenen noch alle gültigen DNS-Namen ab. Starre awk-Feldpositionen hängen vom Ausgabeformat ab. `cat *.txt` kann auch bereits erzeugte Ergebnisdateien einlesen; abweichende Namen wie `subdomain.txt`, `allurls.txt` und `subdomains.txt` müssen im Arbeitsablauf zugeordnet werden. Einzelne Weiterleitungs- oder CNAME-Treffer belegen keine ausnutzbare Schwachstelle. Alle Befehle, Queries und Payloads bleiben unverändert; es wurde kein Beispiel ausgeführt.
