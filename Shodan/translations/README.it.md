# 🌐 Shodan - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   Il motore di ricerca dei dispositivi connessi a Internet
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Motore di ricerca per dispositivi, server e infrastrutture connessi a Internet</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sugli esempi originali:** prezzi, limiti, crediti e filtri dipendono dal piano e dalla versione; la tabella conserva i valori storici, non un’offerta attuale. I filtri `tag` e `vuln` richiedono piani abilitati secondo il [riferimento ufficiale](https://www.shodan.io/search/filters). Le query cercano dati indicizzati nei banner, non verificano credenziali, vulnerabilità o assenza di autenticazione: anche `-authentication` esclude una parola, non prova che l’accesso sia libero. Il valore `ssl.cert.issuer.cn:ssl.cert.subject.cn` non va interpretato come un confronto fra campi. `2024` è una data fissa, non “in scadenza”; query con `vuln:` vuoto, `||` o filtri storici richiedono verifica. Consulta la [sintassi ufficiale](https://help.shodan.io/the-basics/search-query-fundamentals). La disponibilità di TLS recente non dimostra da sola una configurazione sicura; le formule legali dell’originale sono generali.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Per iniziare](#-per-iniziare)
- [Filtri di base](#-filtri-di-base)
- [Filtri di rete](#-filtri-di-rete)
- [Filtri SSL/TLS](#-filtri-ssltls)
- [Filtri HTTP](#-filtri-http)
- [Servizi vulnerabili](#-servizi-vulnerabili)
- [IoT e ICS/SCADA](#-iot-e-icsscada)
- [Database](#-database)
- [Webcam e contenuti multimediali](#-webcam-e-contenuti-multimediali)
- [Servizi cloud](#-servizi-cloud)
- [Query per il bug bounty](#-query-per-il-bug-bounty)
- [CLI di Shodan](#-cli-di-shodan)
- [Riferimento rapido](#-riferimento-rapido)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Shodan** è un motore di ricerca che permette di trovare specifici tipi di computer, server e dispositivi IoT connessi a Internet tramite vari filtri.

### Che cosa puoi trovare?

| Categoria | Esempi |
|----------|----------|
| **Server** | Server web, FTP, SSH e database |
| **Dispositivi IoT** | Telecamere, router e dispositivi intelligenti |
| **ICS/SCADA** | Sistemi di controllo industriale |
| **Cloud** | Istanze AWS, Azure e GCP |
| **Vulnerabilità** | Servizi esposti e credenziali predefinite |

### URL di Shodan

| Servizio | URL |
|---------|-----|
| **Ricerca web** | https://www.shodan.io |
| **Mappe** | https://maps.shodan.io |
| **Exploit** | https://exploits.shodan.io |
| **Immagini** | https://images.shodan.io |

---

## 🚀 Per iniziare

### Creare un account

1. Apri https://www.shodan.io
2. Crea un account gratuito
3. Recupera la chiave API dalle impostazioni dell'account

### Livelli di abbonamento

| Livello | Prezzo | Funzionalità |
|------|-------|----------|
| **Gratuito** | $0 | Ricerca di base, risultati limitati |
| **Membership** | $49 | Più risultati, download e CLI |
| **Small Business** | $299/mo | Più crediti e monitoraggio |
| **Corporate** | $899/mo | Accesso illimitato |

### Chiave API

```bash
# Recuperala da: https://account.shodan.io
# Esporta per la CLI
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

## 🔧 Filtri di base

### Ricerca testuale

```
# Ricerca semplice
apache
nginx
Microsoft-IIS

# Ricerca per prodotto
product:apache
product:nginx
product:openssh
```

### Paese e città

```
# Paese (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Grecia

# Città
city:Athens
city:"New York"
city:London
city:Tokyo

# Combina
country:US city:Miami
country:GR city:Thessaloniki
```

### Organizzazione e ISP

```
# Organizzazione
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Nome host e dominio

```
# Il nome host contiene
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Dominio specifico
ssl.cert.subject.cn:target.com
```

### Sistema operativo

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Filtri di rete

### Porta

```
# Porta specifica
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

# Più porte
port:22,80,443
```

### IP e rete

```
# Indirizzo IP specifico
ip:8.8.8.8

# Intervallo IP (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Geolocalizzazione

```
# Coordinate geografiche
geo:37.9838,23.7275             # Coordinate di Atene
geo:40.7128,-74.0060,10         # New York con raggio

# Regione o stato
region:California
region:"New York"
```

---

## 🔐 Filtri SSL/TLS

### Informazioni sui certificati

```
# Soggetto del certificato
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Emittente del certificato
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Certificati autofirmati
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### Versioni SSL

```
# Versioni SSL (vulnerabili)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Versioni sicure
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Scadenza dei certificati

```
# Certificati scaduti
ssl.cert.expired:true

# Certificati in scadenza
ssl.cert.expires:2024
```

### Vulnerabilità SSL

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 Filtri HTTP

### Stato HTTP e header

```
# Codice di stato HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Titolo HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Contenuto HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Tecnologie web

```
# Header del server
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Versioni specifiche
http.server:"Apache/2.4.49"     # Vulnerabile
http.server:"nginx/1.18"

# Hash della favicon
http.favicon.hash:116323821     # Favicon specifica
http.favicon.hash:-1137975059   # Jenkins
```

### Componenti

```
# Framework web
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Tecnologie
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

## ⚠ Servizi vulnerabili

### CVE note

```
# Ricerca per CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Più CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

### Credenziali predefinite

```
# Password predefinite
"default password"
"admin" "password"
http.title:"Login" "admin"

# Valori predefiniti specifici
"admin:admin"
"root:root"
"admin:123456"
```

### Servizi aperti o esposti

```
# FTP anonimo
port:21 "Anonymous access granted"

# Telnet aperto
port:23 -authentication

# MongoDB aperto
port:27017 "MongoDB Server Information"

# Redis aperto
port:6379 -authentication

# Elasticsearch aperto
port:9200 "cluster_name"

# Memcached aperto
port:11211 "STAT"
```

### Pannelli esposti

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (senza autenticazione)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT e ICS/SCADA

### Sistemi di controllo industriale

```
# ICS generici
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

### Sistemi SCADA

```
# SCADA
tag:scada

# SCADA specifici
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### Dispositivi IoT

```
# IoT generico
tag:iot

# Router
"router" port:80
http.title:"Router"

# Stampanti
port:9100
"printer"
http.title:"HP"

# Casa intelligente
"smart home"
"IoT Gateway"
```

### Dispositivi di rete

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

## 💾 Database

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

## 📹 Webcam e contenuti multimediali

### Telecamere IP

```
# Webcam generiche
webcam
"webcam" country:US

# Marche di telecamere
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Flussi RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

### Sistemi DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Streaming

```
# Streaming VLC
"VLC media player"

# Server multimediali
product:plex
"Plex Media Server"
```

### Interfacce di telecamere specifiche

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

## ☁ Servizi cloud

### Amazon AWS

```
# AWS generico
org:"Amazon"
asn:AS16509

# Bucket S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Servizi AWS
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

### Pannelli cloud esposti

```
# Dashboard Kubernetes
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

## 🐛 Query per il bug bounty

### Ricognizione sull'organizzazione

```
# Organizzazione bersaglio
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Ricerca ASN
asn:AS12345
net:1.2.3.0/24
```

### Individuare i servizi esposti

```
# Tutti i servizi del bersaglio
org:"Target" port:22,80,443,3306,5432,27017

# Pannelli di amministrazione
org:"Target" http.title:"admin"

# Pagine di accesso
org:"Target" http.title:"login"
```

### Individuazione dei sottodomini

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Servizi vulnerabili

```
# Verifica delle CVE
org:"Target" vuln:CVE-2021-44228

# Credenziali predefinite
org:"Target" "default password"

# Database aperti
org:"Target" port:27017,6379,9200
```

### Query completa per il bug bounty

```
# Ricognizione completa
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Con filtri
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

## 💻 CLI di Shodan

### Installazione

```bash
# Installa tramite pip
pip install shodan

# Inizializza con la chiave API
shodan init YOUR_API_KEY
```

### Comandi di base

```bash
# Informazioni sull'account
shodan info

# Ricerca
shodan search "apache"

# Ricerca con filtri
shodan search "port:22 country:US"

# Conta i risultati
shodan count "nginx"

# Scarica i risultati
shodan download results.json.gz "apache"

# Analizza i risultati
shodan parse results.json.gz

# Recupera le informazioni sull'host
shodan host 8.8.8.8
```

### CLI avanzata

```bash
# Informazioni sul dominio
shodan domain target.com

# Scansione (richiede crediti)
shodan scan submit 1.2.3.4

# Honeyscore (rilevamento degli honeypot)
shodan honeyscore 1.2.3.4

# Informazioni sul mio IP
shodan myip

# Statistiche
shodan stats "apache" --facets country,org
```

### Uso dell'API (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Ricerca
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Ricerca dell'host
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Riferimento rapido

### Filtri essenziali

| Filtro | Descrizione | Esempio |
|--------|-------------|---------|
| `port:` | Numero di porta | `port:22` |
| `country:` | Codice del paese | `country:US` |
| `city:` | Nome della città | `city:London` |
| `org:` | Organizzazione | `org:"Google"` |
| `hostname:` | Nome host | `hostname:google.com` |
| `net:` | Intervallo IP/CIDR | `net:10.0.0.0/8` |
| `os:` | Sistema operativo | `os:"Windows"` |
| `product:` | Nome del prodotto | `product:nginx` |
| `version:` | Versione | `version:"2.4.49"` |
| `vuln:` | Identificativo CVE | `vuln:CVE-2021-44228` |

### Filtri HTTP

| Filtro | Descrizione | Esempio |
|--------|-------------|---------|
| `http.title:` | Titolo della pagina | `http.title:"Admin"` |
| `http.status:` | Stato HTTP | `http.status:200` |
| `http.server:` | Header del server | `http.server:nginx` |
| `http.html:` | Contenuto HTML | `http.html:"login"` |
| `http.component:` | Tecnologia | `http.component:"WordPress"` |

### Filtri SSL

| Filtro | Descrizione | Esempio |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | CN del certificato | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | CN dell'emittente | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Versione SSL | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Certificato scaduto | `ssl.cert.expired:true` |

### 10 query principali per Shodan

| # | Query | Scopo |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH negli Stati Uniti |
| 2 | `port:3389 country:US` | RDP negli Stati Uniti |
| 3 | `port:27017 -authentication` | MongoDB aperto |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Dashboard |
| 6 | `"default password"` | Credenziali predefinite |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | Telecamere IP |
| 9 | `product:apache vuln:` | Apache vulnerabile |
| 10 | `ssl.cert.expired:true` | Certificati scaduti |

---

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Inizia dalla ricerca dei certificati**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Controlla tutte le porte**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Cerca i servizi esposti**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Verifica le vulnerabilità**
   ```
   org:"Target" vuln:
   ```

### Combinare i filtri

```
# Query complesse
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Shodan mostra informazioni pubblicamente accessibili. Tuttavia:
> 
> - ✅ Usalo per ricognizioni autorizzate
> - ✅ Usalo sui bersagli compresi nell'ambito del programma di bug bounty
> - ✅ Segnala le vulnerabilità in modo responsabile
> - ❌ Non sfruttare mai le vulnerabilità individuate
> - ❌ Non accedere ai sistemi senza permesso
> - ❌ Rispetta la privacy e le leggi
> 
> **Eseguire scansioni senza autorizzazione è illegale!**

---

## 📚 Risorse

### Risorse ufficiali
- [Shodan](https://www.shodan.io)
- [Guida di Shodan](https://help.shodan.io)
- [CLI di Shodan](https://cli.shodan.io)
- [API di Shodan](https://developer.shodan.io)

### Schede correlate
- [Google Dorking](../../Google-Dorking/translations/README.it.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)

---

<p align="center">
  <b>🌐 Scopri l'Internet nascosto!</b><br>
  <i>Padroneggia Shodan per la ricognizione</i>
</p>
