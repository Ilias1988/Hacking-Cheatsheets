# 🌐 Shodan — Aide-mémoire complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   Le moteur de recherche des appareils connectés à Internet
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Moteur de recherche des appareils, serveurs et infrastructures connectés à Internet</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Premiers pas](#-premiers-pas)
- [Filtres de base](#-filtres-de-base)
- [Filtres réseau](#-filtres-réseau)
- [Filtres SSL/TLS](#-filtres-ssltls)
- [Filtres HTTP](#-filtres-http)
- [Services vulnérables](#-services-vulnérables)
- [IoT et ICS/SCADA](#-iot-et-icsscada)
- [Bases de données](#-bases-de-données)
- [Webcams et médias](#-webcams-et-médias)
- [Services cloud](#-services-cloud)
- [Requêtes pour le bug bounty](#-requêtes-pour-le-bug-bounty)
- [Interface en ligne de commande Shodan](#-interface-en-ligne-de-commande-shodan)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Shodan** est un moteur de recherche qui permet de trouver des types précis d’ordinateurs, de serveurs et d’appareils de l’Internet des objets (IoT) connectés à Internet, à l’aide de différents filtres.

### Que peut-on trouver ?

| Catégorie | Exemples |
|----------|----------|
| **Serveurs** | Serveurs web, FTP, SSH, bases de données |
| **Appareils IoT** | Caméras, routeurs, appareils connectés |
| **ICS/SCADA** | Systèmes de contrôle industriels |
| **Cloud** | Instances AWS, Azure et GCP |
| **Vulnérabilités** | Services exposés, identifiants par défaut |

### Adresses de Shodan

| Service | URL |
|---------|-----|
| **Recherche web** | https://www.shodan.io |
| **Cartes** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Images** | https://images.shodan.io |

---

## 🚀 Premiers pas

### Créer un compte

1. Ouvrir https://www.shodan.io
2. Créer un compte gratuit
3. Récupérer la clé API dans les paramètres du compte

### Formules d’abonnement

| Formule | Prix | Fonctionnalités |
|------|-------|----------|
| **Gratuit** | $0 | Recherche de base, résultats limités |
| **Membership** | $49 | Davantage de résultats, téléchargements, interface en ligne de commande |
| **Small Business** | $299/mo | Davantage de crédits, surveillance |
| **Corporate** | $899/mo | Accès illimité |

> **Précision :** les tarifs et limites du tableau sont historiques et conservés du texte anglais ; « accès illimité » est une simplification. Les offres et quotas actuels doivent être vérifiés dans la [présentation officielle des formules](https://book.shodan.io/getting-started/platform/).

### Clé API

```bash
# Récupérer la clé sur : https://account.shodan.io
# Exporter pour l’interface en ligne de commande
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

> **Précision :** une bannière (*banner*) est l’enregistrement d’un service observé par Shodan. La recherche porte sur cet index, dont les données peuvent dater, et non sur un test instantané de la cible ([syntaxe officielle](https://book.shodan.io/getting-started/query-syntax/)).

## 🔧 Filtres de base

### Recherche textuelle

```
# Recherche simple
apache
nginx
Microsoft-IIS

# Recherche par produit
product:apache
product:nginx
product:openssh
```

### Pays et ville

```
# Pays (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Grèce

# Ville
city:Athens
city:"New York"
city:London
city:Tokyo

# Combiner
country:US city:Miami
country:GR city:Thessaloniki
```

### Organisation et fournisseur d’accès

```
# Organisation
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# Fournisseur d’accès à Internet (ISP)
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Nom d’hôte et domaine

```
# Le nom d’hôte contient
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Domaine précis
ssl.cert.subject.cn:target.com
```

### Système d’exploitation

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Filtres réseau

### Port

```
# Port précis
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

# Plusieurs ports
port:22,80,443
```

### IP et réseau

```
# IP précise
ip:8.8.8.8

# Plage IP (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

> **Précision :** les plages privées `192.168.0.0/24`, `10.0.0.0/8` et `172.16.0.0/12` ci-dessus illustrent la notation CIDR ; elles ne représentent pas des réseaux routables sur l’Internet public ([RFC 1918](https://www.rfc-editor.org/rfc/rfc1918.html#section-3)).

### Localisation géographique

```
# Coordonnées géographiques
geo:37.9838,23.7275             # Coordonnées d’Athènes
geo:40.7128,-74.0060,10         # New York avec un rayon

# Région ou État
region:California
region:"New York"
```

---

## 🔐 Filtres SSL/TLS

### Informations du certificat

```
# Sujet du certificat
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Émetteur du certificat
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Certificats autosignés
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

> **Précision :** `ssl.cert.issuer.cn:ssl.cert.subject.cn` cherche une valeur littérale ; cette syntaxe ne compare pas deux champs et ne démontre donc pas qu’un certificat est autosigné ([syntaxe officielle](https://book.shodan.io/getting-started/query-syntax/)).

### Versions SSL

```
# Versions SSL (vulnérables)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Versions sécurisées
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Expiration du certificat

```
# Certificats expirés
ssl.cert.expired:true

# Certificats expirant bientôt
ssl.cert.expires:2024
```

> **Précision :** `ssl.cert.expires:2024` est un exemple daté, pas une sélection dynamique des certificats expirant bientôt. Ce filtre n’est pas répertorié dans la [référence actuelle](https://www.shodan.io/search/filters) ; l’exemple original est conservé.

### Vulnérabilités SSL

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 Filtres HTTP

### Statut et en-têtes HTTP

```
# Code de statut HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Titre HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Contenu HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Technologies web

```
# En-tête du serveur
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Versions précises
http.server:"Apache/2.4.49"     # Vulnérable
http.server:"nginx/1.18"

# Empreinte de la favicon
http.favicon.hash:116323821     # Favicon spécifique
http.favicon.hash:-1137975059   # Jenkins
```

### Composants

```
# Frameworks web
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Technologies
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-services-vulnérables"></a>
## ⚠️ Services vulnérables

### CVE connues

```
# Recherche par CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Plusieurs CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

> **Précision :** les CVE peuvent être déduites de métadonnées sans avoir été confirmées ; Shodan distingue les résultats vérifiés des résultats non vérifiés. Un port, un titre de page ou une chaîne de bannière ne prouve pas à lui seul l’absence d’authentification ou la validité d’un mot de passe par défaut ([évaluation officielle des vulnérabilités](https://help.shodan.io/mastery/vulnerability-assessment)).

### Identifiants par défaut

```
# Mots de passe par défaut
"default password"
"admin" "password"
http.title:"Login" "admin"

# Identifiants par défaut spécifiques
"admin:admin"
"root:root"
"admin:123456"
```

### Services ouverts ou exposés

```
# FTP anonyme
port:21 "Anonymous access granted"

# Telnet ouvert
port:23 -authentication

# MongoDB ouvert
port:27017 "MongoDB Server Information"

# Redis ouvert
port:6379 -authentication

# Elasticsearch ouvert
port:9200 "cluster_name"

# Memcached ouvert
port:11211 "STAT"
```

### Interfaces d’administration exposées

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (sans authentification)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT et ICS/SCADA

### Systèmes de contrôle industriels

```
# Systèmes industriels en général (ICS)
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

### Systèmes SCADA

```
# SCADA
tag:scada

# Systèmes SCADA spécifiques
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### Appareils IoT

```
# Appareils IoT en général
tag:iot

# Routeurs
"router" port:80
http.title:"Router"

# Imprimantes
port:9100
"printer"
http.title:"HP"

# Maison connectée
"smart home"
"IoT Gateway"
```

### Équipements réseau

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

## 💾 Bases de données

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

## 📹 Webcams et médias

### Caméras IP

```
# Webcams génériques
webcam
"webcam" country:US

# Marques de caméras
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Flux RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

### Systèmes DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Diffusion multimédia

```
# Diffusion avec VLC
"VLC media player"

# Serveurs multimédias
product:plex
"Plex Media Server"
```

### Interfaces de caméras spécifiques

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

<a id="-services-cloud"></a>
## ☁️ Services cloud

### Amazon AWS

```
# AWS en général
org:"Amazon"
asn:AS16509

# Buckets S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Services AWS
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

### Interfaces cloud exposées

```
# Tableau de bord Kubernetes
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

## 🐛 Requêtes pour le bug bounty

### Reconnaissance de l’organisation

```
# Organisation cible
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Recherche d’ASN
asn:AS12345
net:1.2.3.0/24
```

### Trouver les services exposés

```
# Tous les services de la cible
org:"Target" port:22,80,443,3306,5432,27017

# Interfaces d’administration
org:"Target" http.title:"admin"

# Pages de connexion
org:"Target" http.title:"login"
```

### Découverte de sous-domaines

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Services vulnérables

```
# Rechercher les CVE
org:"Target" vuln:CVE-2021-44228

# Identifiants par défaut
org:"Target" "default password"

# Bases de données ouvertes
org:"Target" port:27017,6379,9200
```

### Requête complète pour le bug bounty

```
# Reconnaissance complète
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Avec des filtres
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

> **Précision :** les exemples utilisant `||`, des parenthèses ou `vuln:` sans valeur sont conservés, mais ne doivent pas être supposés valides. La documentation décrit des filtres avec valeur, combinés pour restreindre les résultats, et des valeurs séparées par des virgules pour une alternative ; `vuln` et `tag` dépendent en outre de l’offre ([syntaxe](https://book.shodan.io/getting-started/query-syntax/), [filtres](https://www.shodan.io/search/filters)).

## 💻 Interface en ligne de commande Shodan

### Installation

```bash
# Installer avec pip
pip install shodan

# Initialiser avec la clé API
shodan init YOUR_API_KEY
```

> **Précision :** l’export de `SHODAN_API_KEY` présenté plus haut ne remplace pas l’initialisation documentée de ce client avec `shodan init YOUR_API_KEY` ([installation officielle](https://help.shodan.io/command-line-interface/0-installation)).

### Commandes de base

```bash
# Informations du compte
shodan info

# Rechercher
shodan search "apache"

# Rechercher avec des filtres
shodan search "port:22 country:US"

# Compter les résultats
shodan count "nginx"

# Télécharger les résultats
shodan download results.json.gz "apache"

# Analyser les résultats
shodan parse results.json.gz

# Obtenir les informations de l’hôte
shodan host 8.8.8.8
```

### Commandes avancées

```bash
# Informations du domaine
shodan domain target.com

# Scanner (nécessite des crédits)
shodan scan submit 1.2.3.4

# Honeyscore (détecter les pots de miel, ou honeypots)
shodan honeyscore 1.2.3.4

# Informations de mon IP
shodan myip

# Statistiques
shodan stats "apache" --facets country,org
```

> **Précision :** `shodan scan submit 1.2.3.4` demande un scan actif ; il se distingue d’une consultation des résultats déjà indexés et consomme des crédits de scan ([documentation API](https://book.shodan.io/developer-apis/shodan-api/)).

### Utilisation de l’API (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Rechercher
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Recherche d’un hôte
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Référence rapide

### Filtres essentiels

| Filtre | Description | Exemple |
|--------|-------------|---------|
| `port:` | Numéro de port | `port:22` |
| `country:` | Code pays | `country:US` |
| `city:` | Nom de ville | `city:London` |
| `org:` | Organisation | `org:"Google"` |
| `hostname:` | Nom d’hôte | `hostname:google.com` |
| `net:` | Plage IP/CIDR | `net:10.0.0.0/8` |
| `os:` | Système d’exploitation | `os:"Windows"` |
| `product:` | Nom du produit | `product:nginx` |
| `version:` | Version | `version:"2.4.49"` |
| `vuln:` | Identifiant CVE | `vuln:CVE-2021-44228` |

### Filtres HTTP

| Filtre | Description | Exemple |
|--------|-------------|---------|
| `http.title:` | Titre de la page | `http.title:"Admin"` |
| `http.status:` | Statut HTTP | `http.status:200` |
| `http.server:` | En-tête du serveur | `http.server:nginx` |
| `http.html:` | Contenu HTML | `http.html:"login"` |
| `http.component:` | Technologie | `http.component:"WordPress"` |

### Filtres SSL

| Filtre | Description | Exemple |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | Nom commun (CN) du certificat | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Nom commun (CN) de l’émetteur | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Version SSL | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Certificat expiré | `ssl.cert.expired:true` |

### 10 requêtes Shodan à connaître

| # | Requête | Objectif |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH aux États-Unis |
| 2 | `port:3389 country:US` | RDP aux États-Unis |
| 3 | `port:27017 -authentication` | MongoDB ouvert |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Tableaux de bord |
| 6 | `"default password"` | Identifiants par défaut |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | Caméras IP |
| 9 | `product:apache vuln:` | Apache vulnérable |
| 10 | `ssl.cert.expired:true` | Certificats expirés |

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Commencer par la recherche de certificats**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Vérifier tous les ports**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Rechercher les services exposés**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Rechercher les vulnérabilités**
   ```
   org:"Target" vuln:
   ```

### Combiner les filtres

```
# Requêtes complexes
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

> **ATTENTION :** Shodan affiche des informations accessibles publiquement. Toutefois :
> 
> - ✅ Utiliser Shodan pour une reconnaissance autorisée
> - ✅ Se limiter aux cibles comprises dans le périmètre du programme de bug bounty
> - ✅ Signaler les vulnérabilités de manière responsable
> - ❌ Ne jamais exploiter les vulnérabilités découvertes
> - ❌ Ne pas accéder à des systèmes sans autorisation
> - ✅ Respecter la vie privée et la législation
> 
> **Effectuer des scans sans autorisation est illégal !**

---

## 📚 Ressources

### Ressources officielles
- [Shodan](https://www.shodan.io)
- [Aide Shodan](https://help.shodan.io)
- [Interface en ligne de commande Shodan](https://cli.shodan.io)
- [API Shodan](https://developer.shodan.io)

### Aide-mémoire associés
- [Google Dorking](../../Google-Dorking/translations/README.fr.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)

---

<p align="center">
  <b>🌐 Découvrez la face cachée d’Internet !</b><br>
  <i>Maîtrisez Shodan pour la reconnaissance</i>
</p>
