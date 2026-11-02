# 🌐 Shodan – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   Søkemotoren for enheter som er koblet til Internett
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Søkemotor for Internett-tilkoblede enheter, servere og infrastruktur</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [🎯 Innledning](#-innledning)
- [🚀 Komme i gang](#-komme-i-gang)
- [🔧 Grunnleggende filtre](#-grunnleggende-filtre)
- [🌐 Nettverksfiltre](#-nettverksfiltre)
- [🔐 SSL-/TLS-filtre](#-ssl-tls-filtre)
- [🌍 HTTP-filtre](#-http-filtre)
- [⚠️ Sårbare tjenester](#-sårbare-tjenester)
- [🏭 IoT og ICS/SCADA](#-iot-og-icsscada)
- [💾 Databaser](#-databaser)
- [📹 Nettkameraer og medier](#-nettkameraer-og-medier)
- [☁️ Skytjenester](#-skytjenester)
- [🐛 Spørringer for bug bounty](#-spørringer-for-bug-bounty)
- [💻 Shodan fra kommandolinjen](#-shodan-fra-kommandolinjen)
- [📊 Hurtigreferanse](#-hurtigreferanse)
- [📚 Ressurser](#-ressurser)

---

> **Begreper og presisering:** Shodan søker i innsamlede tjenesteopplysninger (bannere), som kan være eldre enn dagens tilstand. Et CVE-, port-, standardpassord- eller tittel-treff bekrefter ikke alene utnyttbarhet, autentiseringsfri tilgang, eierskap eller testomfang. Priser, abonnementer, kreditter og tilgjengelige filtre er originalens historiske eksempler. Spørringer med private adresseområder gir ikke generell innsyn i private nettverk. Filteret som setter issuer til teksten `ssl.cert.subject.cn` sammenligner ikke automatisk to sertifikatfelt. Originalens `||`-kombinasjoner, tomme `vuln:`-verdier og datofelt må kontrolleres mot søkemotorens dokumenterte syntaks; de er bevart uten reparasjon. Se [grunnsyntaks](https://help.shodan.io/the-basics/search-query-fundamentals) og [Shodans forklaring av kombinasjoner](https://blog.shodan.io/search-engine-improvements/). Et sikkert TLS-versjonsnavn alene beviser ikke sikker konfigurasjon.

## 🎯 Innledning

**Shodan** er en søkemotor der du kan finne bestemte typer datamaskiner, servere og IoT-enheter på Internett ved hjelp av ulike filtre.

### Hva kan du finne?

| Kategori | Eksempler |
|----------|----------|
| **Servere** | Webservere, FTP, SSH og databaser |
| **IoT-enheter** | Kameraer, rutere og smartenheter |
| **ICS/SCADA** | Industrielle kontrollsystemer |
| **Sky** | Instanser i AWS, Azure og GCP |
| **Sårbarheter** | Eksponerte tjenester og standard pålogging |

### Shodans URL-er

| Tjeneste | URL |
|---------|-----|
| **Websøk** | https://www.shodan.io |
| **Kart** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Bilder** | https://images.shodan.io |

---

## 🚀 Komme i gang

### Opprett en konto

1. Gå til https://www.shodan.io
2. Opprett en gratis konto
3. Hent API-nøkkelen fra kontoinnstillingene

### Abonnementsnivåer

| Nivå | Pris | Funksjoner |
|------|-------|----------|
| **Gratis** | $0 | Grunnleggende søk og begrensede resultater |
| **Membership** | $49 | Flere resultater, nedlastinger og CLI |
| **Small Business** | $299/mo | Flere kreditter og overvåking |
| **Corporate** | $899/mo | Ubegrenset tilgang |

### API-nøkkel

```bash
# Hentes fra: https://account.shodan.io
# Eksporter til bruk fra kommandolinjen
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

## 🔧 Grunnleggende filtre

### Tekstsøk

```
# Enkelt søk
apache
nginx
Microsoft-IIS

# Produktsøk
product:apache
product:nginx
product:openssh
```

### Land og by

```
# Land (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Hellas

# By
city:Athens
city:"New York"
city:London
city:Tokyo

# Kombiner
country:US city:Miami
country:GR city:Thessaloniki
```

### Organisasjon og Internett-leverandør

```
# Organisasjon
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Vertsnavn og domene

```
# Vertsnavnet inneholder
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Bestemt domene
ssl.cert.subject.cn:target.com
```

### Operativsystem

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Nettverksfiltre

### Port

```
# Bestemt port
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

# Flere porter
port:22,80,443
```

### IP-adresse og nettverk

```
# Bestemt IP-adresse
ip:8.8.8.8

# IP-område (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Geografisk plassering

```
# Geografiske koordinater
geo:37.9838,23.7275             # Koordinater for Athen
geo:40.7128,-74.0060,10         # NYC med radius

# Region eller delstat
region:California
region:"New York"
```

---

## 🔐 SSL-/TLS-filtre

### Sertifikatopplysninger

```
# Sertifikatets innehaver
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Sertifikatutsteder
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Selvsignerte sertifikater
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### SSL-versjoner

```
# SSL-versjoner (sårbare)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Sikre versjoner
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Utløp for sertifikater

```
# Utløpte sertifikater
ssl.cert.expired:true

# Sertifikater som snart utløper
ssl.cert.expires:2024
```

### SSL-sårbarheter

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 HTTP-filtre

### HTTP-status og headere

```
# HTTP-statuskode
http.status:200
http.status:401
http.status:403
http.status:500

# HTTP-tittel
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# HTML-innhold
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Webteknologi

```
# Server-header
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Bestemte versjoner
http.server:"Apache/2.4.49"     # Sårbar
http.server:"nginx/1.18"

# Favicon-hash
http.favicon.hash:116323821     # Bestemt favicon
http.favicon.hash:-1137975059   # Jenkins
```

### Komponenter

```
# Webrammeverk
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Teknologier
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-sårbare-tjenester"></a>
## ⚠️ Sårbare tjenester

### Kjente CVE-er

```
# Søk etter CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Flere CVE-er
vuln:CVE-2021-44228,CVE-2021-45046
```

### Standard påloggingsopplysninger

```
# Standardpassord
"default password"
"admin" "password"
http.title:"Login" "admin"

# Bestemte standardverdier
"admin:admin"
"root:root"
"admin:123456"
```

### Åpne og eksponerte tjenester

```
# Anonym FTP
port:21 "Anonymous access granted"

# Åpen Telnet
port:23 -authentication

# Åpen MongoDB
port:27017 "MongoDB Server Information"

# Åpen Redis
port:6379 -authentication

# Åpen Elasticsearch
port:9200 "cluster_name"

# Åpen Memcached
port:11211 "STAT"
```

### Eksponerte paneler

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (uten autentisering)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT og ICS/SCADA

### Industrielle kontrollsystemer

```
# ICS generelt
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

### SCADA-systemer

```
# SCADA
tag:scada

# Bestemte SCADA-systemer
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### IoT-enheter

```
# IoT generelt
tag:iot

# Rutere
"router" port:80
http.title:"Router"

# Skrivere
port:9100
"printer"
http.title:"HP"

# Smarthus
"smart home"
"IoT Gateway"
```

### Nettverksenheter

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

## 💾 Databaser

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

## 📹 Nettkameraer og medier

### IP-kameraer

```
# Generelle nettkameraer
webcam
"webcam" country:US

# Kameramerker
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# RTSP-strømmer
port:554 has_screenshot:true
"rtsp" port:554
```

### DVR-/NVR-systemer

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Strømming

```
# Strømming med VLC
"VLC media player"

# Medieservere
product:plex
"Plex Media Server"
```

### Bestemte kameragrensesnitt

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

<a id="-skytjenester"></a>
## ☁️ Skytjenester

### Amazon AWS

```
# AWS generelt
org:"Amazon"
asn:AS16509

# S3-bøtter
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# AWS-tjenester
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

### Eksponerte skypaneler

```
# Kubernetes-dashbord
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

## 🐛 Spørringer for bug bounty

### Kartlegging av organisasjoner

```
# Målorganisasjon
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# ASN-oppslag
asn:AS12345
net:1.2.3.0/24
```

### Finne eksponerte tjenester

```
# Alle tjenester for målet
org:"Target" port:22,80,443,3306,5432,27017

# Administrasjonspaneler
org:"Target" http.title:"admin"

# Påloggingssider
org:"Target" http.title:"login"
```

### Oppdagelse av underdomener

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Sårbare tjenester

```
# Se etter CVE-er
org:"Target" vuln:CVE-2021-44228

# Standard pålogging
org:"Target" "default password"

# Åpne databaser
org:"Target" port:27017,6379,9200
```

### Sammensatt spørring for bug bounty

```
# Full kartlegging
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Med filtre
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

## 💻 Shodan fra kommandolinjen

### Installasjon

```bash
# Installer med pip
pip install shodan

# Initialiser med API-nøkkel
shodan init YOUR_API_KEY
```

### Grunnleggende kommandoer

```bash
# Kontoopplysninger
shodan info

# Søk
shodan search "apache"

# Søk med filtre
shodan search "port:22 country:US"

# Tell resultater
shodan count "nginx"

# Last ned resultater
shodan download results.json.gz "apache"

# Les resultater
shodan parse results.json.gz

# Hent vertsopplysninger
shodan host 8.8.8.8
```

### Avansert kommandolinjebruk

```bash
# Domeneopplysninger
shodan domain target.com

# Skann (krever kreditter)
shodan scan submit 1.2.3.4

# Honeyscore (identifiserer mulige honningfeller)
shodan honeyscore 1.2.3.4

# Opplysninger om egen IP-adresse
shodan myip

# Statistikk
shodan stats "apache" --facets country,org
```

### API-bruk (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Søk
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Vertsoppslag
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Hurtigreferanse

### Viktige filtre

| Filter | Beskrivelse | Eksempel |
|--------|-------------|---------|
| `port:` | Portnummer | `port:22` |
| `country:` | Landskode | `country:US` |
| `city:` | Bynavn | `city:London` |
| `org:` | Organisasjon | `org:"Google"` |
| `hostname:` | Vertsnavn | `hostname:google.com` |
| `net:` | IP-område/CIDR | `net:10.0.0.0/8` |
| `os:` | Operativsystem | `os:"Windows"` |
| `product:` | Produktnavn | `product:nginx` |
| `version:` | Versjon | `version:"2.4.49"` |
| `vuln:` | CVE-ID | `vuln:CVE-2021-44228` |

### HTTP-filtre

| Filter | Beskrivelse | Eksempel |
|--------|-------------|---------|
| `http.title:` | Sidetittel | `http.title:"Admin"` |
| `http.status:` | HTTP-status | `http.status:200` |
| `http.server:` | Server-header | `http.server:nginx` |
| `http.html:` | HTML-innhold | `http.html:"login"` |
| `http.component:` | Teknologi | `http.component:"WordPress"` |

### SSL-filtre

| Filter | Beskrivelse | Eksempel |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | Sertifikatets CN | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Utstederens CN | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | SSL-versjon | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Utløpt sertifikat | `ssl.cert.expired:true` |

### Ti Shodan-spørringer

| # | Spørring | Formål |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH i USA |
| 2 | `port:3389 country:US` | RDP i USA |
| 3 | `port:27017 -authentication` | Åpen MongoDB |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Dashbord |
| 6 | `"default password"` | Standard pålogging |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | IP-kameraer |
| 9 | `product:apache vuln:` | Sårbar Apache |
| 10 | `ssl.cert.expired:true` | Utløpte sertifikater |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Begynn med sertifikatsøk**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Undersøk alle portene**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Se etter eksponerte tjenester**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Se etter sårbarheter**
   ```
   org:"Target" vuln:
   ```

### Kombiner filtre

```
# Sammensatte spørringer
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** Shodan viser offentlig tilgjengelige opplysninger. Likevel gjelder følgende:
>
> - ✅ Bruk til autorisert rekognosering
> - ✅ Bruk mot bug bounty-mål innenfor avtalt omfang
> - ✅ Rapporter sårbarheter på en ansvarlig måte
> - ❌ Ikke utnytt sårbarhetene du finner
> - ❌ Ikke få tilgang til systemer uten tillatelse
> - ❌ Respekter personvern og lovverk
>
> **Skanning uten tillatelse er ulovlig!**

---

## 📚 Ressurser

### Offisielle ressurser
- [Shodan](https://www.shodan.io)
- [Shodan-hjelp](https://help.shodan.io)
- [Shodan CLI](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Relaterte hurtigreferanser
- [Google Dorking](../../Google-Dorking/translations/README.nb.md)
- [GitHub-dorking](../../GitHub-Dorking/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)

---

<p align="center">
  <b>🌐 Undersøk den mindre synlige delen av Internett!</b><br>
  <i>Lær Shodan til rekognosering</i>
</p>
