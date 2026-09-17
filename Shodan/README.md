# 🌐 Shodan - Complete Cheatsheet

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   The Search Engine for Internet-Connected Devices
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Search engine for Internet-connected devices, servers, and infrastructure</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Getting Started](#-getting-started)
- [Basic Filters](#-basic-filters)
- [Network Filters](#-network-filters)
- [SSL/TLS Filters](#-ssltls-filters)
- [HTTP Filters](#-http-filters)
- [Vulnerable Services](#-vulnerable-services)
- [IoT & ICS/SCADA](#-iot--icsscada)
- [Databases](#-databases)
- [Webcams & Media](#-webcams--media)
- [Cloud Services](#-cloud-services)
- [Bug Bounty Queries](#-bug-bounty-queries)
- [Shodan CLI](#-shodan-cli)
- [Quick Reference](#-quick-reference)
- [Resources](#-resources)

---

## 🎯 Introduction

**Shodan** is a search engine that lets users find specific types of computers, servers, and IoT devices connected to the internet using various filters.

### What Can You Find?

| Category | Examples |
|----------|----------|
| **Servers** | Web servers, FTP, SSH, databases |
| **IoT Devices** | Cameras, routers, smart devices |
| **ICS/SCADA** | Industrial control systems |
| **Cloud** | AWS, Azure, GCP instances |
| **Vulnerabilities** | Exposed services, default creds |

### Shodan URLs

| Service | URL |
|---------|-----|
| **Web Search** | https://www.shodan.io |
| **Maps** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Images** | https://images.shodan.io |

---

## 🚀 Getting Started

### Create Account

1. Go to https://www.shodan.io
2. Create free account
3. Get API key from account settings

### Membership Tiers

| Tier | Price | Features |
|------|-------|----------|
| **Free** | $0 | Basic search, limited results |
| **Membership** | $49 | More results, downloads, CLI |
| **Small Business** | $299/mo | More credits, monitoring |
| **Corporate** | $899/mo | Unlimited access |

### API Key

```bash
# Get from: https://account.shodan.io
# Export for CLI
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

## 🔧 Basic Filters

### Text Search

```
# Simple search
apache
nginx
Microsoft-IIS

# Product search
product:apache
product:nginx
product:openssh
```

### Country & City

```
# Country (ISO 3166-1 alpha-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Greece

# City
city:Athens
city:"New York"
city:London
city:Tokyo

# Combine
country:US city:Miami
country:GR city:Thessaloniki
```

### Organization & ISP

```
# Organization
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Hostname & Domain

```
# Hostname contains
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Specific domain
ssl.cert.subject.cn:target.com
```

### Operating System

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Network Filters

### Port

```
# Specific port
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

# Multiple ports
port:22,80,443
```

### IP & Network

```
# Specific IP
ip:8.8.8.8

# IP range (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Geo Location

```
# Geographic coordinates
geo:37.9838,23.7275             # Athens coordinates
geo:40.7128,-74.0060,10         # NYC with radius

# Region/State
region:California
region:"New York"
```

---

## 🔐 SSL/TLS Filters

### Certificate Information

```
# Certificate subject
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Certificate issuer
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Self-signed certificates
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### SSL Versions

```
# SSL versions (vulnerable)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Secure versions
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Certificate Expiry

```
# Expired certificates
ssl.cert.expired:true

# Certificates expiring soon
ssl.cert.expires:2024
```

### SSL Vulnerabilities

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 HTTP Filters

### HTTP Status & Headers

```
# HTTP status code
http.status:200
http.status:401
http.status:403
http.status:500

# HTTP title
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# HTML content
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Web Technologies

```
# Server header
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Specific versions
http.server:"Apache/2.4.49"     # Vulnerable
http.server:"nginx/1.18"

# Favicon hash
http.favicon.hash:116323821     # Specific favicon
http.favicon.hash:-1137975059   # Jenkins
```

### Components

```
# Web frameworks
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

## ⚠️ Vulnerable Services

### Known CVEs

```
# Search by CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Multiple CVEs
vuln:CVE-2021-44228,CVE-2021-45046
```

### Default Credentials

```
# Default passwords
"default password"
"admin" "password"
http.title:"Login" "admin"

# Specific defaults
"admin:admin"
"root:root"
"admin:123456"
```

### Open/Exposed Services

```
# Anonymous FTP
port:21 "Anonymous access granted"

# Open Telnet
port:23 -authentication

# Open MongoDB
port:27017 "MongoDB Server Information"

# Open Redis
port:6379 -authentication

# Open Elasticsearch
port:9200 "cluster_name"

# Open Memcached
port:11211 "STAT"
```

### Exposed Panels

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (unauthenticated)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT & ICS/SCADA

### Industrial Control Systems

```
# General ICS
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

### SCADA Systems

```
# SCADA
tag:scada

# Specific SCADA
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### IoT Devices

```
# General IoT
tag:iot

# Routers
"router" port:80
http.title:"Router"

# Printers
port:9100
"printer"
http.title:"HP"

# Smart Home
"smart home"
"IoT Gateway"
```

### Network Devices

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

## 💾 Databases

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

## 📹 Webcams & Media

### IP Cameras

```
# Generic webcams
webcam
"webcam" country:US

# Camera brands
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# RTSP streams
port:554 has_screenshot:true
"rtsp" port:554
```

### DVR/NVR Systems

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Streaming

```
# VLC streaming
"VLC media player"

# Media servers
product:plex
"Plex Media Server"
```

### Specific Camera Interfaces

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

## ☁️ Cloud Services

### Amazon AWS

```
# General AWS
org:"Amazon"
asn:AS16509

# S3 Buckets
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# AWS services
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

### Exposed Cloud Panels

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

## 🐛 Bug Bounty Queries

### Organization Recon

```
# Target organization
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# ASN lookup
asn:AS12345
net:1.2.3.0/24
```

### Find Exposed Services

```
# All services for target
org:"Target" port:22,80,443,3306,5432,27017

# Admin panels
org:"Target" http.title:"admin"

# Login pages
org:"Target" http.title:"login"
```

### Subdomain Discovery

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Vulnerable Services

```
# Check for CVEs
org:"Target" vuln:CVE-2021-44228

# Default creds
org:"Target" "default password"

# Open databases
org:"Target" port:27017,6379,9200
```

### Complete Bug Bounty Query

```
# Full recon
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# With filters
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

## 💻 Shodan CLI

### Installation

```bash
# Install via pip
pip install shodan

# Initialize with API key
shodan init YOUR_API_KEY
```

### Basic Commands

```bash
# Account info
shodan info

# Search
shodan search "apache"

# Search with filters
shodan search "port:22 country:US"

# Count results
shodan count "nginx"

# Download results
shodan download results.json.gz "apache"

# Parse results
shodan parse results.json.gz

# Get host info
shodan host 8.8.8.8
```

### Advanced CLI

```bash
# Domain info
shodan domain target.com

# Scan (requires credits)
shodan scan submit 1.2.3.4

# Honeyscore (detect honeypots)
shodan honeyscore 1.2.3.4

# My IP info
shodan myip

# Stats
shodan stats "apache" --facets country,org
```

### API Usage (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Search
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Host lookup
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Quick Reference

### Essential Filters

| Filter | Description | Example |
|--------|-------------|---------|
| `port:` | Port number | `port:22` |
| `country:` | Country code | `country:US` |
| `city:` | City name | `city:London` |
| `org:` | Organization | `org:"Google"` |
| `hostname:` | Hostname | `hostname:google.com` |
| `net:` | IP range/CIDR | `net:10.0.0.0/8` |
| `os:` | Operating system | `os:"Windows"` |
| `product:` | Product name | `product:nginx` |
| `version:` | Version | `version:"2.4.49"` |
| `vuln:` | CVE ID | `vuln:CVE-2021-44228` |

### HTTP Filters

| Filter | Description | Example |
|--------|-------------|---------|
| `http.title:` | Page title | `http.title:"Admin"` |
| `http.status:` | HTTP status | `http.status:200` |
| `http.server:` | Server header | `http.server:nginx` |
| `http.html:` | HTML content | `http.html:"login"` |
| `http.component:` | Technology | `http.component:"WordPress"` |

### SSL Filters

| Filter | Description | Example |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | Certificate CN | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Issuer CN | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | SSL version | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Expired cert | `ssl.cert.expired:true` |

### Top 10 Shodan Queries

| # | Query | Purpose |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH in US |
| 2 | `port:3389 country:US` | RDP in US |
| 3 | `port:27017 -authentication` | Open MongoDB |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Dashboards |
| 6 | `"default password"` | Default creds |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | IP cameras |
| 9 | `product:apache vuln:` | Vulnerable Apache |
| 10 | `ssl.cert.expired:true` | Expired certs |

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Start with Cert Search**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Check All Ports**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Look for Exposed Services**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Check for Vulns**
   ```
   org:"Target" vuln:
   ```

### Combine Filters

```
# Complex queries
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Shodan shows publicly accessible information. However:
> 
> - ✅ Use for authorized reconnaissance
> - ✅ Use for bug bounty targets in scope
> - ✅ Report vulnerabilities responsibly
> - ❌ Never exploit found vulnerabilities
> - ❌ Don't access systems without permission
> - ❌ Respect privacy and laws
> 
> **Scanning without authorization is illegal!**

---

## 📚 Resources

### Official Resources
- [Shodan](https://www.shodan.io)
- [Shodan Help](https://help.shodan.io)
- [Shodan CLI](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Related Cheatsheets
- [Google Dorking](../Google-Dorking/README.md)
- [GitHub Dorking](../GitHub-Dorking/README.md)
- [Nmap](../Nmap/README.md)

---

<p align="center">
  <b>🌐 See the Hidden Internet!</b><br>
  <i>Master Shodan for reconnaissance</i>
</p>
