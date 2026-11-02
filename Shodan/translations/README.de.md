# 🌐 Shodan – Vollständiges Cheatsheet

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   Die Suchmaschine für Geräte mit Internetverbindung
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Suchmaschine für internetverbundene Geräte, Server und Infrastruktur</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Erste Schritte](#-erste-schritte)
- [Grundlegende Filter](#-grundlegende-filter)
- [Netzwerkfilter](#-netzwerkfilter)
- [SSL-/TLS-Filter](#-ssl-tls-filter)
- [HTTP-Filter](#-http-filter)
- [Anfällige Dienste](#-anfällige-dienste)
- [IoT und ICS/SCADA](#-iot-und-icsscada)
- [Datenbanken](#-datenbanken)
- [Webcams und Medien](#-webcams-und-medien)
- [Cloud-Dienste](#-cloud-dienste)
- [Bug-Bounty-Suchanfragen](#-bug-bounty-suchanfragen)
- [Shodan-CLI](#-shodan-cli)
- [Schnellübersicht](#-schnellübersicht)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Shodan** ist eine Suchmaschine, mit der sich über verschiedene Filter bestimmte Arten von Computern, Servern und IoT-Geräten mit Internetverbindung finden lassen.

### Was lässt sich finden?

| Kategorie | Beispiele |
|----------|----------|
| **Server** | Webserver, FTP, SSH, Datenbanken |
| **IoT-Geräte** | Kameras, Router, vernetzte Geräte |
| **ICS/SCADA** | Industrielle Steuerungssysteme |
| **Cloud** | AWS-, Azure- und GCP-Instanzen |
| **Schwachstellen** | Exponierte Dienste, Standardzugangsdaten |

### Shodan-Adressen

| Dienst | URL |
|---------|-----|
| **Websuche** | https://www.shodan.io |
| **Karten** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Bilder** | https://images.shodan.io |

---

## 🚀 Erste Schritte

### Konto erstellen

1. https://www.shodan.io aufrufen
2. Ein kostenloses Konto erstellen
3. Den API-Schlüssel in den Kontoeinstellungen abrufen

### Mitgliedschaftsstufen

| Stufe | Preis | Funktionen |
|------|-------|----------|
| **Free** | $0 | Grundlegende Suche, begrenzte Ergebnisse |
| **Membership** | $49 | Mehr Ergebnisse, Downloads, CLI |
| **Small Business** | $299/mo | Mehr Credits, Überwachung |
| **Corporate** | $899/mo | Unbegrenzter Zugriff |

### API-Schlüssel

```bash
# Abrufen unter: https://account.shodan.io
# Für die CLI exportieren
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

## 🔧 Grundlegende Filter

### Textsuche

```
# Einfache Suche
apache
nginx
Microsoft-IIS

# Produktsuche
product:apache
product:nginx
product:openssh
```

### Land und Stadt

```
# Land (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Griechenland

# Stadt
city:Athens
city:"New York"
city:London
city:Tokyo

# Kombinieren
country:US city:Miami
country:GR city:Thessaloniki
```

### Organisation und Internetanbieter

```
# Organisation
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Hostname und Domain

```
# Hostname enthält
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Bestimmte Domain
ssl.cert.subject.cn:target.com
```

### Betriebssystem

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Netzwerkfilter

### Port

```
# Bestimmter Port
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# Mehrere Ports
port:22,80,443
```

### IP-Adresse und Netzwerk

```
# Bestimmte IP-Adresse
ip:8.8.8.8

# IP-Adressbereich (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Geografische Position

```
# Geografische Koordinaten
geo:37.9838,23.7275             # Koordinaten von Athen
geo:40.7128,-74.0060,10         # New York mit Radius

# Region/Bundesstaat
region:California
region:"New York"
```

---

## 🔐 SSL-/TLS-Filter

### Zertifikatsinformationen

```
# Zertifikatsinhaber
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Zertifikatsaussteller
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Selbstsignierte Zertifikate
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### SSL-Versionen

```
# SSL-Versionen (anfällig)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Sichere Versionen
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Ablauf von Zertifikaten

```
# Abgelaufene Zertifikate
ssl.cert.expired:true

# Demnächst ablaufende Zertifikate
ssl.cert.expires:2024
```

### SSL-Schwachstellen

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 HTTP-Filter

### HTTP-Status und Header

```
# HTTP-Statuscode
http.status:200
http.status:401
http.status:403
http.status:500

# HTTP-Seitentitel
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# HTML-Inhalt
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Webtechnologien

```
# Server-Header
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Bestimmte Versionen
http.server:"Apache/2.4.49"     # Anfällig
http.server:"nginx/1.18"

# Favicon hash
http.favicon.hash:116323821     # Bestimmtes Favicon
http.favicon.hash:-1137975059   # Jenkins
```

### Komponenten

```
# Webframeworks
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Technologien
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a name="-anfällige-dienste"></a>

## ⚠️ Anfällige Dienste

### Bekannte CVEs

```
# Nach CVE suchen
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Mehrere CVEs
vuln:CVE-2021-44228,CVE-2021-45046
```

### Standardzugangsdaten

```
# Standardpasswörter
"default password"
"admin" "password"
http.title:"Login" "admin"

# Bestimmte Standardzugangsdaten
"admin:admin"
"root:root"
"admin:123456"
```

### Offene oder exponierte Dienste

```
# Anonymes FTP
port:21 "Anonymous access granted"

# Offenes Telnet
port:23 -authentication

# Offenes MongoDB
port:27017 "MongoDB Server Information"

# Offenes Redis
port:6379 -authentication

# Offenes Elasticsearch
port:9200 "cluster_name"

# Offenes Memcached
port:11211 "STAT"
```

### Exponierte Verwaltungsoberflächen

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (ohne Authentifizierung)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT und ICS/SCADA

### Industrielle Steuerungssysteme

```
# Allgemeine ICS-Suche
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### SCADA-Systeme

```
# SCADA
tag:scada

# Bestimmte SCADA-Systeme
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### IoT-Geräte

```
# Allgemeine IoT-Suche
tag:iot

# Router
"router" port:80
http.title:"Router"

# Drucker
port:9100
"printer"
http.title:"HP"

# Smart Home
"smart home"
"IoT Gateway"
```

### Netzwerkgeräte

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

## 💾 Datenbanken

### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

## 📹 Webcams und Medien

### IP-Kameras

```
# Allgemeine Webcams
webcam
"webcam" country:US

# Kameramarken
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# RTSP-Streams
port:554 has_screenshot:true
"rtsp" port:554
```

### DVR-/NVR-Systeme

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Streaming

```
# VLC-Streaming
"VLC media player"

# Medienserver
product:plex
"Plex Media Server"
```

### Bestimmte Kameraoberflächen

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a name="-cloud-dienste"></a>

## ☁️ Cloud-Dienste

### Amazon AWS

```
# Allgemeine AWS-Suche
org:"Amazon"
asn:AS16509

# S3-Buckets
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# AWS-Dienste
http.title:"AWS"
```

### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

### Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

### Exponierte Cloud-Verwaltungsoberflächen

```
# Kubernetes Dashboard
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

## 🐛 Bug-Bounty-Suchanfragen

### Organisationen untersuchen

```
# Zielorganisation
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# ASN nachschlagen
asn:AS12345
net:1.2.3.0/24
```

### Exponierte Dienste finden

```
# Alle Dienste des Ziels
org:"Target" port:22,80,443,3306,5432,27017

# Administrationsoberflächen
org:"Target" http.title:"admin"

# Anmeldeseiten
org:"Target" http.title:"login"
```

### Subdomains ermitteln

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Anfällige Dienste

```
# Auf CVEs prüfen
org:"Target" vuln:CVE-2021-44228

# Standardzugangsdaten
org:"Target" "default password"

# Offene Datenbanken
org:"Target" port:27017,6379,9200
```

### Vollständige Bug-Bounty-Suchanfrage

```
# Umfassende Informationssammlung
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Mit Filtern
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

## 💻 Shodan-CLI

### Installation

```bash
# Über pip installieren
pip install shodan

# Mit API-Schlüssel initialisieren
shodan init YOUR_API_KEY
```

### Grundlegende Befehle

```bash
# Kontoinformationen
shodan info

# Suche
shodan search "apache"

# Suche mit Filtern
shodan search "port:22 country:US"

# Ergebnisse zählen
shodan count "nginx"

# Ergebnisse herunterladen
shodan download results.json.gz "apache"

# Ergebnisse auswerten
shodan parse results.json.gz

# Hostinformationen abrufen
shodan host 8.8.8.8
```

### Erweiterte CLI-Befehle

```bash
# Domaininformationen
shodan domain target.com

# Scan (verbraucht Credits)
shodan scan submit 1.2.3.4

# Honeyscore (Honeypots erkennen)
shodan honeyscore 1.2.3.4

# Informationen zur eigenen IP-Adresse
shodan myip

# Statistiken
shodan stats "apache" --facets country,org
```

### API-Verwendung mit Python

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Suche
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Host nachschlagen
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Schnellübersicht

### Wichtige Filter

| Filter | Beschreibung | Beispiel |
|--------|-------------|---------|
| `port:` | Portnummer | `port:22` |
| `country:` | Ländercode | `country:US` |
| `city:` | Stadtname | `city:London` |
| `org:` | Organisation | `org:"Google"` |
| `hostname:` | Hostname | `hostname:google.com` |
| `net:` | IP-Adressbereich/CIDR | `net:10.0.0.0/8` |
| `os:` | Betriebssystem | `os:"Windows"` |
| `product:` | Produktname | `product:nginx` |
| `version:` | Version | `version:"2.4.49"` |
| `vuln:` | CVE-Kennung | `vuln:CVE-2021-44228` |

### HTTP-Filter

| Filter | Beschreibung | Beispiel |
|--------|-------------|---------|
| `http.title:` | Seitentitel | `http.title:"Admin"` |
| `http.status:` | HTTP-Status | `http.status:200` |
| `http.server:` | Server-Header | `http.server:nginx` |
| `http.html:` | HTML-Inhalt | `http.html:"login"` |
| `http.component:` | Technologie | `http.component:"WordPress"` |

### SSL-Filter

| Filter | Beschreibung | Beispiel |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | CN des Zertifikats | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | CN des Ausstellers | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | SSL-Version | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Abgelaufenes Zertifikat | `ssl.cert.expired:true` |

### Zehn häufige Shodan-Suchanfragen

| # | Suchanfrage | Zweck |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH in den USA |
| 2 | `port:3389 country:US` | RDP in den USA |
| 3 | `port:27017 -authentication` | Offenes MongoDB |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Dashboards |
| 6 | `"default password"` | Standardzugangsdaten |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | IP-Kameras |
| 9 | `product:apache vuln:` | Anfälliger Apache |
| 10 | `ssl.cert.expired:true` | Abgelaufene Zertifikate |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Bug-Bounty-Tipps

1. **Mit der Zertifikatsuche beginnen**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Alle Ports prüfen**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Nach exponierten Diensten suchen**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Auf Schwachstellen prüfen**
   ```
   org:"Target" vuln:
   ```

### Filter kombinieren

```
# Komplexe Suchanfragen
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ Rechtliche Hinweise

> **WARNUNG:** Shodan zeigt öffentlich zugängliche Informationen an. Dennoch gilt:
> 
> - ✅ Für autorisierte Informationssammlung verwenden
> - ✅ Für Bug-Bounty-Ziele innerhalb des freigegebenen Umfangs verwenden
> - ✅ Schwachstellen verantwortungsvoll melden
> - ❌ Gefundene Schwachstellen nicht ausnutzen
> - ❌ Ohne Erlaubnis nicht auf Systeme zugreifen
> - ✅ Privatsphäre und Gesetze respektieren
> 
> **Scannen ohne Genehmigung ist illegal!**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Shodan](https://www.shodan.io)
- [Shodan Help](https://help.shodan.io)
- [Shodan CLI](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Verwandte Cheatsheets
- [Google Dorking](../../Google-Dorking/translations/README.de.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Preise, Kontingente und Schlüssel:** Die Preistabelle ist ein historischer Stand, kein aktuelles Angebot; insbesondere bedeutet „unbegrenzt“ keine pauschale Aufhebung aller Limits. Filter und Credits hängen vom Tarif ab, unter anderem sind vuln und tag eingeschränkt. Die CLI-Anleitung dokumentiert die Initialisierung mit shodan init; die Exportzeile allein ersetzt diesen dokumentierten Schritt nicht. [Shodan: Tarife](https://account.shodan.io/billing), [Credits](https://help.shodan.io/the-basics/credit-types-explained), [CLI-Installation](https://help.shodan.io/command-line-interface/0-installation).
- **Suchsyntax:** Mehrere Suchbegriffe werden mit AND verknüpft; kommagetrennte Werte eines Filters bilden Alternativen. Die ||- und Klammerbeispiele sind daher keine verlässliche allgemeine OR-Syntax. Ein Filter ohne Wert ist keine vollständige CVE-Suche. Die Abfrage für selbstsignierte Zertifikate vergleicht keine zwei Felder miteinander, sondern übergibt den rechten Text als Suchwert. Die aktuellen verfügbaren Filter sind maßgeblich; die Vorlage enthält etwa ssl.cert.expires und http.server, die nicht in der aktuellen Referenz aufgeführt sind. Der Jahreswert 2024 bedeutet außerdem nicht mehr „demnächst“. [Shodan: Suchsyntax](https://book.shodan.io/getting-started/query-syntax/), [Suchmaschinenverhalten](https://blog.shodan.io/search-engine-improvements/), [Filterreferenz](https://www.shodan.io/search/filters).
- **Indizien statt Beweis:** Ein offener Port, ein Produkttitel, ein Favicon oder das Fehlen des Wortes authentication beweisen weder ungeschützten Zugriff noch gültige Standardpasswörter. Shodan unterscheidet verifizierte von aus Metadaten abgeleiteten CVE-Hinweisen; letztere können falsch positiv sein. Auch eine alte TLS-Version oder ein Zertifikatsname allein beweist keine konkrete ausnutzbare Schwachstelle oder Zugehörigkeit zum freigegebenen Ziel. [Shodan: Schwachstellenbewertung](https://help.shodan.io/mastery/vulnerability-assessment), [Suchgrundlagen und Banner](https://help.shodan.io/the-basics/search-query-fundamentals).
- **Netzbereiche und Reichweite:** Die gezeigten privaten CIDR-Bereiche sind keine öffentlich routbaren Zielnetze der normalen Internet-Suche. Ebenso prüft die Portliste unter „Alle Ports prüfen“ nur die aufgeführten Ports. Bereits erfasste Banner zu durchsuchen und einen neuen Scan über scan submit auszulösen sind unterschiedliche Vorgänge. [RFC 1918](https://www.rfc-editor.org/rfc/rfc1918.html), [Shodan: API und Scan-Aufträge](https://developer.shodan.io/api).
- **Hinweise der Vorlage:** Das widersprüchliche Verbotssymbol vor dem Respektieren von Privatsphäre und Gesetzen wurde zu einem Häkchen korrigiert. Der pauschale Rechtssatz ist eine vereinfachte Warnung des Originals und keine Bewertung der im konkreten Fall geltenden Rechtslage. Öffentlich sichtbare Treffer erweitern keine vorhandene Testfreigabe.

---

<p align="center">
  <b>🌐 Das verborgene Internet entdecken!</b><br>
  <i>Shodan für die Informationssammlung beherrschen</i>
</p>
