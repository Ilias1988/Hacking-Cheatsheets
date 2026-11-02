[🇬🇧 English](../../README.md)

# 🔍 Google Dorking - Kapsamlı Başvuru Kılavuzu

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 Güvenlik Araştırmacıları İçin İleri Düzey Google Arama Teknikleri</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Temel Operatörler](#-temel-operatörler)
- [Gelişmiş Operatörler](#-gelişmiş-operatörler)
- [Operatörleri Birleştirme](#-operatörleri-birleştirme)
- [Dosya Keşfi](#-dosya-keşfi)
- [Dizin Listeleme (Directory Listing)](#-dizin-listeleme-directory-listing)
- [Yönetici (Admin) Panelleri](#-yönetici-admin-panelleri)
- [Veritabanı & SQL](#-veritabanı--sql)
- [Yapılandırma Dosyaları](#-yapılandırma-dosyaları)
- [Kimlik Bilgileri ve Gizli Anahtarlar](#-kimlik-bilgileri-ve-gizli-anahtarlar)
- [Zafiyetli Sunucular](#-zafiyetli-sunucular)
- [Bug Bounty Dork'ları](#-bug-bounty-dorkları)
- [CMS'e Özel Dork'lar](#-cmse-özel-dorklar)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Araçlar ve Otomasyon](#-araçlar-ve-otomasyon)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Google Dorking** (Google Hacking olarak da bilinir), internete açık unutulmuş hassas verileri, gizli yapılandırma dosyalarını ve güvenlik açıklarını tespit etmek için gelişmiş arama motoru operatörlerini kullanan bir keşif tekniğidir.

### Neler Bulabilirsiniz?

| Kategori | Örnekler |
|----------|----------|
| **Hassas Dosyalar** | Yedekler, yapılandırma dosyaları, log kayıtları, veritabanları |
| **Açık Paneller** | Yönetim (admin) panelleri, gösterge panelleri, phpMyAdmin |
| **Kimlik Bilgileri** | Parolalar, API anahtarları, yetki belirteçleri (tokens) |
| **Zafiyetler** | SQL enjeksiyonu noktaları, açık dizin indeksleri |
| **Kişisel Veriler** | E-postalar, telefon numaraları, kimlik dokümanları |

### Arama Motoru Adresleri

| Arama Motoru | URL |
|--------------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Temel Operatörler

### Site Operatörü (`site:`)

Aramayı belirli bir alan adı veya uzantıyla sınırlandırır:

```
site:example.com                     # Yalnızca example.com
site:*.example.com                   # Tüm alt alan adları
site:example.com -www                # www alt alan adını hariç tut
site:.gov                            # Devlet siteleri
site:.edu                            # Eğitim kurumları
```

### Inurl Operatörü (`inurl:`)

URL içerisinde belirtilen kelimeyi arar:

```
inurl:admin                          # URL'de admin geçenler
inurl:login                          # Giriş sayfaları
inurl:dashboard                      # Gösterge panelleri
inurl:config                         # Yapılandırma dosyaları
inurl:backup                         # Yedek dosyaları
```

### Intitle Operatörü (`intitle:`)

Sayfa başlığında (HTML `<title>`) arar:

```
intitle:"index of"                   # Dizin listeleme sayfaları
intitle:admin                        # Yönetici sayfaları
intitle:login                        # Giriş sayfaları
intitle:"dashboard"                  # Kontrol panelleri
intitle:"control panel"              # Kontrol panelleri
```

### Intext Operatörü (`intext:`)

Sayfa gövdesindeki metin içerisinde arar:

```
intext:"password"                    # Metinde geçen parolalar
intext:"username"                    # Kullanıcı adları
intext:"api_key"                     # API anahtarları
intext:"secret"                      # Gizli dizgiler
```

### Filetype / Ext Operatörü (`filetype:`, `ext:`)

Belirli dosya uzantılarını arar:

```
filetype:pdf                         # PDF belgeleri
filetype:doc                         # Word dokümanları
filetype:xls                         # Excel tabloları
filetype:sql                         # SQL veritabanı dökümleri
filetype:log                         # Log dosyaları
filetype:bak                         # Yedekleme dosyaları
filetype:env                         # Ortam değişkeni (.env) dosyaları
```

---

## ⚡ Gelişmiş Operatörler

### "All" Varyantları

```
allinurl:admin login                 # Belirtilen tüm kelimeler URL'de olmalı
allintitle:admin panel               # Belirtilen tüm kelimeler başlıkta olmalı
allintext:password username          # Belirtilen tüm kelimeler metinde olmalı
```

### Cache & Link

```
cache:example.com                    # Sayfanın Google önbelleğindeki sürümü
link:example.com                     # Hedefe bağlantı veren sayfalar
related:example.com                  # İlgili/benzer web siteleri
```

### Tarih Aralığı Filtreleri

```
after:2023-01-01                     # Belirtilen tarihten sonrakiler
before:2024-01-01                    # Belirtilen tarihten öncekiler
daterange:2458850-2459215            # Julian tarih aralığı
```

### Joker Karakterler & Mantıksal OR

```
admin * panel                        # Araya herhangi bir kelime gelebilir
admin OR login                       # İki terimden herhangi biri
"admin panel"                        # Tam ifade eşleşmesi
-inurl:https                         # HTTPS içerenleri hariç tut
```

### Sayı Aralığı

```
inurl:id= 1..1000                    # Sayı aralığı
price $100..$500                     # Fiyat aralığı
```

---

## 🔗 Operatörleri Birleştirme

### Temel Kombinasyonlar

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### İleri Düzey Kombinasyonlar

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Bug Bounty Kombinasyonları

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Dosya Keşfi

### Hassas Dokümanlar

```
# Ofis Belgeleri
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Metin Dosyaları
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Yedekleme Dosyaları

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Veritabanı Dosyaları

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Log Dosyaları

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Yapılandırma Dosyaları

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Kaynak Kodlar

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

## 📂 Dizin Listeleme (Directory Listing)

### Index Of Sayfaları

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Belirli Hassas Dizinler

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Dizin İçindeki Belirli Dosya Türleri

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Yönetici (Admin) Panelleri

### Genel Yönetici Girişleri

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel & Barındırma Panelleri

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Veritabanı Yönetim Arayüzleri

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Gösterge ve Kontrol Panelleri

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Veritabanı & SQL

### SQL Dosyaları

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### SQL Hata Mesajları

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### SQL Enjeksiyonu Göstergeleri

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Veritabanı Dökümleri

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

## ⚙️ Yapılandırma Dosyaları

### Web Sunucusu Yapılandırmaları

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Uygulama Yapılandırmaları

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Git Yapılandırmaları

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS & Bulut Kimlik Bilgileri

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # AWS Key öneki
site:target.com "credentials" filetype:json
```

---

## 🔑 Kimlik Bilgileri ve Gizli Anahtarlar

### Parolalar

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Kullanıcı Adı ve Parola İkilileri

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### API Anahtarları ve Belirteçler (Tokens)

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### E-posta Adresleri

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Özel Anahtarlar (Private Keys)

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

## ⚠️ Zafiyetli Sunucular

### Açık Dizinler

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Varsayılan Karşılama Sayfaları

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Hata Sayfaları

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Dışa Açık Servisler

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

## 🐛 Bug Bounty Dork'ları

### Hızlı Kazanım (Quick Wins) Dork'ları

```
# Hassas Dosyalar
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Yönetim Panelleri
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Yapılandırma Dosyaları
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Kimlik Bilgileri
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Alt Alan Adı Keşfi

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### API Uç Noktaları

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Geliştirme ve Test (Staging) Ortamları

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Kimlik Doğrulama Sayfaları

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Dosya Yükleme Alanları

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 CMS'e Özel Dork'lar

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

## 📊 Hızlı Başvuru

### Temel Operatörler

| Operatör | İşlev | Örnek |
|----------|-------|-------|
| `site:` | Belirli bir alan adına kısıtla | `site:target.com` |
| `inurl:` | URL içerisinde ara | `inurl:admin` |
| `intitle:` | Başlıkta ara | `intitle:login` |
| `intext:` | Sayfa metninde ara | `intext:password` |
| `filetype:` | Dosya uzantısı ara | `filetype:sql` |
| `ext:` | Dosya uzantısı ara | `ext:pdf` |
| `cache:` | Önbelleğe alınmış kopya | `cache:target.com` |
| `"..."` | Birebir ifade eşleşmesi | `"admin panel"` |
| `-` | Hariç tutma | `-site:www` |
| `OR` / `|` | Mantıksal VEYA | `admin OR login` |
| `*` | Joker karakter | `admin * panel` |

### Bug Bounty İçin En İyi 10 Dork

| # | Dork | Amaç |
|---|------|------|
| 1 | `site:target.com filetype:sql` | Veritabanı dosyaları |
| 2 | `site:target.com inurl:admin` | Yönetici panelleri |
| 3 | `site:target.com filetype:log` | Log kayıtları |
| 4 | `site:target.com filetype:env` | Ortam değişkeni (.env) dosyaları |
| 5 | `site:target.com intext:password filetype:txt` | Parolalar |
| 6 | `intitle:"index of" site:target.com` | Açık dizin listelemeleri |
| 7 | `site:target.com inurl:api` | API uç noktaları |
| 8 | `site:target.com filetype:bak` | Yedek dosyaları |
| 9 | `site:*.target.com -www` | Alt alan adları |
| 10 | `site:target.com "api_key"` | API anahtarları |

---

## 🛠️ Araçlar ve Otomasyon

### Google Dorking Araçları

| Araç | Açıklama | Bağlantı |
|------|----------|----------|
| **Google Hacking Database** | Exploit-DB dork koleksiyonu | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Hızlı dork üretici aracı | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Pasif Google dorking aracı | GitHub |
| **GooDork** | Python dorking scripti | GitHub |
| **GooFuzz** | Google tabanlı fuzzing aracı | GitHub |

### Faydalı Web Siteleri

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Örnek Otomasyon Betiği (Bash)

```bash
#!/bin/bash
# Basit Google Dorking Otomasyon Scripti

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 İpuçları ve En İyi Pratikler

### Bug Bounty İpuçları

1. **Geniş Başlayıp Daraltın**
   ```
   site:target.com                    # Önce genel arama
   site:target.com filetype:sql       # Ardından spesifik arama
   ```

2. **Farklı Arama Motorlarını Deneyin**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Önbelleğe Alınmış Sayfaları İnceleyin**
   ```
   cache:target.com/admin
   ```

4. **Alt Alan Adlarını Ayıklayın**
   ```
   site:*.target.com -www
   ```

5. **Arama Oranını Sınırlayın**
   - Google aşırı isteklerde CAPTCHA veya geçici IP engeli koyabilir.

### Tespiti Engelleme

```
# Farklı IP adresleri / VPN kullanın
# Aramalar arasına rastgele gecikmeler ekleyin
# Arama kalıplarınızı çeşitlendirin
# Farklı tarayıcı profilleri kullanın
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Google Dorking yapmak ve açık aramalar gerçekleştirmek tamamen yasaldır; ancak açığa çıkmış hassas verilere yetkisiz olarak **erişmek, indirmek veya istismar etmek** yasa dışıdır.
> 
> - ✅ Yalnızca izin verilen güvenlik testlerinde kullanın
> - ✅ Bug bounty programları kapsamında çalışın
> - ✅ Tespit edilen açıkları sorumlu açıklama (responsible disclosure) ile bildirin
> - ❌ İzniniz olmayan verilere asla erişmeyin
> - ❌ Hassas verileri indirmeyin ve depolamayın
> - ❌ Bulunan zafiyetleri istismar etmeyin
> 
> **Test yapmadan önce mutlaka yetki ve izin belgeniz olduğundan emin olun!**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Google Arama Operatörleri](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database (GHDB)](https://www.exploit-db.com/google-hacking-database)

### Kitaplar ve Rehberler
- Johnny Long - "Google Hacking for Penetration Testers"
- OWASP Web Security Testing Guide

### İlgili Başvuru Kılavuzları
- [Shodan](../../Shodan/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Subfinder](../../Subfinder/translations/README.tr.md)

---

<p align="center">
  <b>🔍 Başkalarının Sakladıklarını Keşfedin!</b><br>
  <i>Google Dorking sanatında ustalaşın</i>
</p>
