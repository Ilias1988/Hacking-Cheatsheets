[🇬🇧 English](../../README.md)

# 🌐 Shodan - Kapsamlı Başvuru Kılavuzu

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
  <b>🔍 İnternete bağlı cihazlar, sunucular ve altyapılar için küresel arama motoru</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Başlarken](#-başlarken)
- [Temel Filtreler](#-temel-filtreler)
- [Ağ Filtreleri](#-ağ-filtreleri)
- [SSL/TLS Filtreleri](#-ssltls-filtreleri)
- [HTTP Filtreleri](#-http-filtreleri)
- [Zafiyetli Servisler](#-zafiyetli-servisler)
- [IoT ve ICS/SCADA](#-iot-ve-icsscada)
- [Veritabanları](#-veritabanları)
- [Web Kameraları ve Medya](#-web-kameraları-ve-medya)
- [Bulut Servisleri](#-bulut-servisleri)
- [Bug Bounty Sorguları](#-bug-bounty-sorguları)
- [Shodan CLI](#-shodan-cli)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Shodan**, kullanıcıların internete doğrudan bağlı bilgisayarları, sunucuları, IoT cihazlarını, endüstriyel kontrol sistemlerini ve ağ donanımlarını çeşitli filtreler kullanarak bulmasını sağlayan dünyanın ilk ve en popüler arama motorudur.

### Neler Bulabilirsiniz?

| Kategori | Örnekler |
|----------|----------|
| **Sunucular** | Web sunucuları, FTP, SSH, veritabanları |
| **IoT Cihazları** | Kameralar, yönlendiriciler (routers), akıllı ev cihazları |
| **ICS/SCADA** | Endüstriyel kontrol sistemleri, PLC üniteleri |
| **Bulut (Cloud)** | AWS, Azure, GCP bulut örnekleri |
| **Zafiyetler** | Dışa açık servislere ait bilinen açıklar, varsayılan parolalar |

### Shodan Servis Adresleri

| Servis | URL |
|--------|-----|
| **Web Arama** | https://www.shodan.io |
| **Haritalar** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Görseller** | https://images.shodan.io |

---

## 🚀 Başlarken

### Hesap Oluşturma

1. https://www.shodan.io adresine gidin.
2. Ücretsiz hesap oluşturun.
3. Hesap ayarları panelinden API anahtarınızı alın.

### Üyelik Paketleri

| Paket | Ücret | Özellikler |
|-------|-------|------------|
| **Free (Ücretsiz)** | $0 | Temel arama, sınırlı sonuçlar |
| **Membership (Ömür Boyu)** | $49 | Daha fazla sonuç, veri indirme, CLI erişimi |
| **Small Business** | $299/ay | Genişletilmiş sorgu kredisi, varlık izleme |
| **Corporate** | $899/ay | Sınırsız kurumsal erişim |

### API Anahtarı Yapılandırma

```bash
# API anahtarını ortam değişkeni olarak tanımlayın
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

## 🔧 Temel Filtreler

### Metin Araması

```
# Basit arama
apache
nginx
Microsoft-IIS

# Ürün araması
product:apache
product:nginx
product:openssh
```

### Ülke ve Şehir

```
# Ülke kodu (ISO 3166-1 alpha-2)
country:TR                      # Türkiye
country:US                      # Amerika Birleşik Devletleri
country:DE                      # Almanya
country:RU                      # Rusya

# Şehir
city:Istanbul
city:Ankara
city:Izmir
city:"New York"

# Kombine arama
country:TR city:Istanbul
country:US city:Miami
```

### Organizasyon & İSS (ISP)

```
# Organizasyon
org:"Turk Telekom"
org:"Google"
org:"Amazon"
org:"Microsoft"

# İnternet Servis Sağlayıcısı (ISP)
isp:"Turkcell"
isp:"Vodafone"
isp:"Comcast"
```

### Hostname & Alan Adı

```
# Hostname arama
hostname:google.com
hostname:.gov.tr
hostname:.edu.tr
hostname:target.com

# SSL sertifikasında alan adı
ssl.cert.subject.cn:target.com
```

### İşletim Sistemi

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

## 🌐 Ağ Filtreleri

### Port Numarası

```
# Belirli portlar
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

# Birden fazla port
port:22,80,443
```

### IP ve Ağ Blokları

```
# Belirli IP adresi
ip:8.8.8.8

# CIDR IP bloğu
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# Otonom Sistem Numarası (ASN)
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Coğrafi Konum (Geo Location)

```
# Koordinat araması
geo:41.0082,28.9784             # İstanbul koordinatları
geo:40.7128,-74.0060,10         # Yarıçaplı arama (km)

# Bölge / Eyalet
region:California
region:"Marmara"
```

---

## 🔐 SSL/TLS Filtreleri

### Sertifika Bilgileri

```
# Sertifika konusu (Subject Common Name)
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Sertifika yetkilisi (Issuer)
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Kendi imzalı (Self-signed) sertifikalar
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### SSL Protokol Sürümleri

```
# Zafiyetli eski SSL sürümleri
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Güvenli TLS sürümleri
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Sertifika Geçerlilik Süresi

```
# Süresi dolmuş sertifikalar
ssl.cert.expired:true

# Yakında süresi dolacak sertifikalar
ssl.cert.expires:2024
```

### SSL Zafiyetleri

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

## 🌍 HTTP Filtreleri

### HTTP Durumu ve Başlıklar

```
# HTTP durum kodları
http.status:200
http.status:401
http.status:403
http.status:500

# Sayfa başlıkları
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# HTML içeriği
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Web Sunucu Başlıkları ve Teknolojiler

```
# Sunucu başlığı (Server header)
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Belirli zafiyetli sürümler
http.server:"Apache/2.4.49"     # RCE açığı olan sürüm
http.server:"nginx/1.18"

# Favicon Hash Değeri
http.favicon.hash:116323821     # Belirli bir uygulamanın ikonu
http.favicon.hash:-1137975059   # Jenkins
```

### Web Bileşenleri

```
# Web Çatıları (Frameworks)
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Kütüphaneler
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

## ⚠️ Zafiyetli Servisler

### Bilinen CVE Numaraları ile Arama

```
# CVE ile arama
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Birden fazla CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

### Varsayılan Kimlik Bilgileri

```
# Varsayılan şifre metinleri
"default password"
"admin" "password"
http.title:"Login" "admin"

# Bilinen varsayılan ikililer
"admin:admin"
"root:root"
"admin:123456"
```

### Açık ve Korumasız Servisler

```
# Anonim FTP erişimi
port:21 "Anonymous access granted"

# Şifresiz Telnet
port:23 -authentication

# Kimlik doğrulamasız açık MongoDB
port:27017 "MongoDB Server Information"

# Şifresiz açık Redis
port:6379 -authentication

# Açık Elasticsearch
port:9200 "cluster_name"

# Açık Memcached
port:11211 "STAT"
```

### Dışa Açık Yönetim Panelleri

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Şifresiz Jenkins
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

## 🏭 IoT ve ICS/SCADA

### Endüstriyel Kontrol Sistemleri (ICS)

```
# Genel ICS etiketi
tag:ics

# Modbus protokolü
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet (Bina otomasyonu)
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### SCADA Sistemleri

```
# SCADA etiketi
tag:scada

# Spesifik SCADA aramaları
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### IoT Aygıtları

```
# Genel IoT etiketi
tag:iot

# Yönlendiriciler (Routers)
"router" port:80
http.title:"Router"

# Yazıcılar
port:9100
"printer"
http.title:"HP"

# Akıllı Ev Sistemleri
"smart home"
"IoT Gateway"
```

### Ağ Donanımları

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

## 💾 Veritabanları

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

## 📹 Web Kameraları ve Medya

### IP Kameralar

```
# Genel kameralar
webcam
"webcam" country:TR

# Kamera markaları
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# RTSP yayınları
port:554 has_screenshot:true
"rtsp" port:554
```

### DVR / NVR Kayıt Cihazları

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Medya Sunucuları

```
# VLC yayını
"VLC media player"

# Medya sunucuları
product:plex
"Plex Media Server"
```

---

## ☁️ Bulut Servisleri

### Amazon AWS

```
# Genel AWS aramaları
org:"Amazon"
asn:AS16509

# S3 Depoları
"AmazonS3"

# EC2 Örnekleri
"ec2" org:"Amazon"

# AWS Servisleri
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

### Açık Bulut Panelleri

```
# Kubernetes Dashboard
http.title:"Kubernetes Dashboard"

# Docker API
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

## 🐛 Bug Bounty Sorguları

### Organizasyon Keşfi

```
# Hedef organizasyon
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# ASN ve IP aralığı sorgusu
asn:AS12345
net:1.2.3.0/24
```

### Dışa Açık Servisleri Bulma

```
# Hedefe ait tüm açık portlar
org:"Target" port:22,80,443,3306,5432,27017

# Yönetim panelleri
org:"Target" http.title:"admin"

# Giriş sayfaları
org:"Target" http.title:"login"
```

### Alt Alan Adı Keşfi

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Zafiyetli Servisleri Tarama

```
# CVE kontrolü
org:"Target" vuln:CVE-2021-44228

# Varsayılan kimlik bilgileri
org:"Target" "default password"

# Açık veritabanları
org:"Target" port:27017,6379,9200
```

### Kapsamlı Bug Bounty Sorgusu

```
# Tam keşif sorgusu
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Port filtreleriyle birlikte
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

## 💻 Shodan CLI

### Kurulum

```bash
# pip ile kurulum
pip install shodan

# API anahtarı ile başlatma
shodan init YOUR_API_KEY
```

### Temel Komutlar

```bash
# Hesap bilgisi
shodan info

# Arama yapma
shodan search "apache"

# Filtreli arama
shodan search "port:22 country:US"

# Sonuç sayısını öğrenme
shodan count "nginx"

# Sonuçları indirme
shodan download results.json.gz "apache"

# İndirilen sonuçları okuma
shodan parse results.json.gz

# Hedef IP hakkında detaylı bilgi
shodan host 8.8.8.8
```

### Gelişmiş CLI Komutları

```bash
# Alan adı bilgisi
shodan domain target.com

# Tarama başlatma (kredi gerektirir)
shodan scan submit 1.2.3.4

# Bal küpü (honeypot) tespiti
shodan honeyscore 1.2.3.4

# Kendi IP bilginizi alma
shodan myip

# İstatistiksel özet alma
shodan stats "apache" --facets country,org
```

### Python API Kullanımı

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Arama gerçekleştirme
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Sunucu sorgulama
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

## 📊 Hızlı Başvuru

### Temel Filtreler

| Filtre | Açıklama | Örnek |
|--------|----------|-------|
| `port:` | Port numarası | `port:22` |
| `country:` | Ülke kodu | `country:TR` |
| `city:` | Şehir adı | `city:Istanbul` |
| `org:` | Organizasyon adı | `org:"Google"` |
| `hostname:` | Alan / sunucu adı | `hostname:google.com` |
| `net:` | IP aralığı / CIDR | `net:10.0.0.0/8` |
| `os:` | İşletim sistemi | `os:"Windows"` |
| `product:` | Ürün / yazılım adı | `product:nginx` |
| `version:` | Yazılım sürümü | `version:"2.4.49"` |
| `vuln:` | CVE numarası | `vuln:CVE-2021-44228` |

### HTTP Filtreleri

| Filtre | Açıklama | Örnek |
|--------|----------|-------|
| `http.title:` | Sayfa başlığı | `http.title:"Admin"` |
| `http.status:` | HTTP durum kodu | `http.status:200` |
| `http.server:` | Sunucu başlığı | `http.server:nginx` |
| `http.html:` | HTML içeriği | `http.html:"login"` |
| `http.component:` | Kullanılan teknoloji | `http.component:"WordPress"` |

### SSL Filtreleri

| Filtre | Açıklama | Örnek |
|--------|----------|-------|
| `ssl.cert.subject.cn:` | Sertifika konusu (CN) | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Sertifika sağlayıcısı | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | SSL sürümü | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Süresi dolmuş mu | `ssl.cert.expired:true` |

---

## 💡 İpuçları ve En İyi Pratikler

### Bug Bounty İpuçları

1. **Sertifika Aramasıyla Başlayın**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Tüm Portları Tarayın**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Dışa Açık Veritabanlarını Arayın**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **CVE Açıklarını Kontrol Edin**
   ```
   org:"Target" vuln:
   ```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Shodan herkese açık verileri indeksler. Ancak:
> 
> - ✅ Yalnızca yetkiniz olan hedeflerde keşif yapın
> - ✅ Kapsam dahilindeki bug bounty hedeflerinde çalışın
> - ✅ Zafiyetleri sorumlu açıklama ile bildirin
> - ❌ Tespit edilen açıkları asla istismar etmeyin
> - ❌ Sistemlere izinsiz erişim sağlamayın
> - ❌ Gizlilik ve kanunlara titizlikle uyun
> 
> **İzinsiz sistemlere erişmek veya müdahale etmek yasa dışıdır!**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Shodan Resmi Web Sitesi](https://www.shodan.io)
- [Shodan Yardım Dokümantasyonu](https://help.shodan.io)
- [Shodan CLI Dokümantasyonu](https://cli.shodan.io)
- [Shodan Geliştirici API](https://developer.shodan.io)

### İlgili Başvuru Kılavuzları
- [Google Dorking](../../Google-Dorking/translations/README.tr.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)

---

<p align="center">
  <b>🌐 İnternetin Görünmeyen Yüzünü Keşfedin!</b><br>
  <i>İstihbarat ve keşif için Shodan'da ustalaşın</i>
</p>
