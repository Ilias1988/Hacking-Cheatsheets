# 🕷️ Nikto - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web Sunucu Tarayıcısı - 2001'den Beri
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Güvenlik testleri için açık kaynaklı web sunucusu zafiyet tarayıcısı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Hedef Belirleme](#-hedef-belirleme)
- [Port & SSL Seçenekleri](#-port--ssl-seçenekleri)
- [Tarama Ayarları (Tuning)](#-tarama-ayarları-tuning)
- [Kimlik Doğrulama (Authentication)](#-kimlik-doğrulama-authentication)
- [Proxy & Atlatma (Evasion)](#-proxy--atlatma-evasion)
- [Çıktı & Raporlama](#-çıktı--raporlama)
- [Veritabanı Güncellemeleri](#-veritabanı-güncellemeleri)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Nikto**, web sunucularına karşı kapsamlı güvenlik denetimleri ve zafiyet taramaları gerçekleştiren açık kaynaklı bir araçtır:

### Nikto Neleri Kontrol Eder?

| Kategori | Açıklama |
|----------|----------|
| **Tehlikeli Dosyalar** | Geride bırakılmış yedekler, kurulum ve konfigürasyon dosyaları |
| **Eski Sürümler** | Güncelliğini yitirmiş ve bilinen açıkları olan sunucu yazılımları |
| **Sürüme Özgü Sorunlar** | Belirli sunucu sürümlerini etkileyen güvenlik açıkları |
| **Sunucu Yapılandırması** | Hatalı veya güvensiz sunucu ayarları |
| **CGI Betikleri** | Güvensiz veya potansiyel olarak tehlikeli CGI betikleri |
| **SSL/TLS Durumu** | Geçersiz veya süresi dolmuş SSL sertifikaları |
| **HTTP Başlıkları** | Eksik veya güvensiz güvenlik başlıkları (HSTS, CSP vb.) |
| **HTTP Metotları** | PUT, DELETE, TRACE gibi tehlikeli HTTP metotları |

### Veritabanı İstatistikleri

```
✅ 6.700+ potansiyel olarak tehlikeli dosya ve CGI testi
✅ 1.250+ eski sunucu sürümü tespiti
✅ 270+ sürüme özgü zafiyet denetimi
✅ 1.000+ sunucu konfigürasyon hatası kontrolü
```

---

## 📥 Kurulum

### Kali Linux (Önceden Kuruludur)
```bash
# Nikto'yu güncelleme
sudo apt update && sudo apt install nikto

# Test veritabanını güncelleme
nikto -update
```

### Ubuntu / Debian
```bash
sudo apt install nikto
```

### GitHub Üzerinden
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Kurulumu Doğrulama
```bash
nikto -Version
nikto -h          # Yardım menüsü
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
nikto -h <hedef> [seçenekler]
```

### Hızlı Başlangıç
```bash
# Standart tarama
nikto -h http://target.com

# Port belirterek tarama
nikto -h target.com -p 80

# HTTPS taraması
nikto -h https://target.com

# IP adresi ile tarama
nikto -h 192.168.1.1
```

---

## 🎯 Hedef Belirleme

### Tekil Hedef
```bash
# Alan adına göre
nikto -h target.com

# IP adresine göre
nikto -h 192.168.1.1

# Protokol ile birlikte
nikto -h http://target.com
nikto -h https://target.com
```

### Çoklu Hedef Taraması
```bash
# Dosyadan hedefleri okuma
nikto -h targets.txt

# targets.txt içeriği:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Belirli Bir Dizin veya Yolu Tarama
```bash
# Belirli bir uygulamayı tarama
nikto -h http://target.com/app

# Belirli bir dizini tarama
nikto -h http://target.com/admin/
```

---

## 🔌 Port & SSL Seçenekleri

### Port Seçenekleri
```bash
# Tek port
nikto -h target.com -p 80

# Birden çok port
nikto -h target.com -p 80,443,8080

# Port aralığı
nikto -h target.com -p 80-90

# Yaygın web portları
nikto -h target.com -p 80,443,8000,8080,8443
```

### SSL / TLS Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-ssl` | SSL/HTTPS modunu zorla |
| `-nossl` | SSL'i devre dışı bırak (yalnızca HTTP) |
| `-maxtime` | Maksimum tarama süresi |

```bash
# SSL zorlama
nikto -h target.com -ssl

# SSL kapatma (yalnızca düz HTTP)
nikto -h target.com -nossl

# Standart dışı portta SSL çalıştırma
nikto -h target.com -p 8443 -ssl
```

### Sanal Sunucular (Virtual Hosts)
```bash
# Sanal sunucu belirterek tarama
nikto -h 192.168.1.1 -vhost target.com

# Yük dengeleyicilerin arkasında veya aynı IP'de çoklu web sitesi barındırıldığında kullanılır
```

---

## 🎚️ Tarama Ayarları (Tuning)

### İnce Ayar Kodları (-Tuning)

| Kod | Açıklama |
|-----|----------|
| `0` | Dosya Yükleme (File Upload) |
| `1` | İlgi Çekici Dosyalar / Loglarda Görülenler |
| `2` | Hatalı Yapılandırma / Varsayılan Dosyalar |
| `3` | Bilgi İfşası (Information Disclosure) |
| `4` | Enjeksiyon (XSS / Script / HTML) |
| `5` | Uzaktan Dosya Erişimi - Web Kökü İçinde |
| `6` | Hizmet Reddi (Denial of Service - DoS) |
| `7` | Uzaktan Dosya Erişimi - Sunucu Genelinde |
| `8` | Komut Çalıştırma / Uzak Kabuk |
| `9` | SQL Enjeksiyonu |
| `a` | Kimlik Doğrulama Atlatma (Auth Bypass) |
| `b` | Yazılım / Sürüm Tespiti |
| `c` | Uzaktan Kaynak Dahil Etme (RFI) |
| `d` | Web Servisleri |
| `e` | Yönetici Konsolları (Admin Panel) |
| `x` | Hariç Tutma (Ters Seçim) |

### Tuning Kullanımı
```bash
# Yalnızca SQL Enjeksiyonu testlerini çalıştır
nikto -h target.com -Tuning 9

# XSS ve SQL Enjeksiyonu testlerini birlikte çalıştır
nikto -h target.com -Tuning 49

# Birden fazla kategoriyi seç
nikto -h target.com -Tuning 1234

# DoS testlerini hariç tut (Canlı sistemlerde önerilir)
nikto -h target.com -Tuning x6
```

### Eklentiler (Plugins)
```bash
# Mevcut eklentileri listele
nikto -list-plugins

# Belirli bir eklentiyi çalıştır
nikto -h target.com -Plugins apacheusers

# Birden çok eklenti çalıştır
nikto -h target.com -Plugins "apacheusers;robots"

# Belirli bir eklentiyi hariç tut
nikto -h target.com -Plugins "@@ALL;-robots"
```

---

## 🔐 Kimlik Doğrulama (Authentication)

### Basic Authentication
```bash
# Kullanıcı adı ve parola ile
nikto -h target.com -id admin:password
```

### NTLM Authentication
```bash
# Windows NTLM doğrulaması ile
nikto -h target.com -id admin:password:domain
```

### Çerez Tabanlı Kimlik Doğrulama
```bash
# Çerez dizesi göndererek
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Dosyadan çerez okuyarak
nikto -h target.com -cookies cookies.txt
```

---

## 🛡️ Proxy & Atlatma (Evasion)

### Proxy Desteği
```bash
# HTTP proxy kullanımı
nikto -h target.com -useproxy http://127.0.0.1:8080

# Kimlik doğrulamalı proxy
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Burp Suite üzerinden tarama yapma
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### IDS/WAF Atlatma Teknikleri (-evasion)

| Kod | Teknik |
|-----|--------|
| `1` | Rastgele URI kodlaması (non-UTF8) |
| `2` | Dizin kendi kendine referans (/./) |
| `3` | Erken URL sonlandırma |
| `4` | Başa uzun rastgele dize ekleme |
| `5` | Sahte parametre ekleme |
| `6` | İstek ayırıcı olarak TAB karakteri kullanımı |
| `7` | URL harf durumunu değiştirme (Büyük/Küçük harf) |
| `8` | Windows dizin ayırıcısı (\) kullanımı |
| `A` | Satır başı karakteri (0x0d) kullanımı |
| `B` | İkili değer (0x0b) kullanımı |

```bash
# Tek bir atlatma tekniği
nikto -h target.com -evasion 1

# Birden çok atlatma tekniği kombinasyonu
nikto -h target.com -evasion 1234
```

### İstek Zamanlaması
```bash
# İstekler arasında bekleme süresi (saniye)
nikto -h target.com -Pause 2

# Maksimum tarama süresi sınırı (saniye)
nikto -h target.com -maxtime 3600

# İstek zaman aşımı
nikto -h target.com -timeout 10
```

---

## 📄 Çıktı & Raporlama

### Çıktı Formatları

| Format | Seçenek |
|--------|---------|
| HTML | `-Format htm` |
| XML | `-Format xml` |
| CSV | `-Format csv` |
| Metin (TXT) | `-Format txt` |
| JSON | `-Format json` |

### Rapor Kaydetme Örnekleri
```bash
# HTML rapor oluşturma
nikto -h target.com -o report.html -Format htm

# JSON raporu
nikto -h target.com -o report.json -Format json

# CSV raporu
nikto -h target.com -o report.csv -Format csv
```

### Ekran Gösterim Seçenekleri (-Display)
```bash
nikto -h target.com -Display V    # Ayrıntılı mod
nikto -h target.com -Display D    # Hata ayıklama (Debug)
nikto -h target.com -Display E    # HTTP hatalarını göster
nikto -h target.com -Display P    # İlerleme durumunu göster
```

---

## 🔄 Veritabanı Güncellemeleri

```bash
# Nikto kural ve zafiyet veritabanını güncelle
nikto -update
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Temel Web Sunucu Taraması
```bash
nikto -h http://target.com
```

### Örnek 2: Kapsamlı HTTPS Taraması ve HTML Rapor
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Örnek 3: Çoklu Port Taraması
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Örnek 4: Kimlik Doğrulamalı Yönetim Paneli Denetimi
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Örnek 5: Gizli Tarama (IDS Atlatma + Gecikme)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Örnek 6: Burp Suite Proxy Üzerinden İnceleme
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Örnek 7: Sanal Konak (VHost) Testi
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| Temel tarama | `nikto -h target.com` |
| HTTPS taraması | `nikto -h target.com -ssl` |
| Çoklu port | `nikto -h target.com -p 80,443,8080` |
| Kimlik doğrulamalı | `nikto -h target.com -id user:pass` |
| HTML raporu | `nikto -h target.com -o report.html -Format htm` |
| IDS atlatma | `nikto -h target.com -evasion 123` |
| Proxy kullanımı | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Veritabanı güncelle | `nikto -update` |

### Sık Kullanılan Seçenekler

| Parametre | Açıklama |
|-----------|----------|
| `-h` | Hedef ana makine veya URL |
| `-p` | Taranacak port veya port listesi |
| `-ssl` | SSL/HTTPS modunu zorla |
| `-id` | Kimlik doğrulama bilgisi |
| `-o` | Rapor dosya adı |
| `-Format` | Çıktı formatı (htm, xml, json, csv) |
| `-Tuning` | Tarama kategorileri filtresi |
| `-evasion` | IDS/WAF atlatma tekniği |
| `-useproxy` | Proxy adresi |
| `-vhost` | Sanal konak başlığı |
| `-Pause` | İstekler arası bekleme süresi |
| `-timeout` | İstek zaman aşımı süresi |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **Üretim Ortamlarında DoS Testlerini Hariç Tutun**
   ```bash
   nikto -h target.com -Tuning x6
   ```

2. **Nmap ile Birlikte Kullanın**
   - Önce `nmap -sV -p 80,443 target.com` ile açık web portlarını tespit edin, ardından doğrudan o portlara Nikto yönlendirin.

3. **Veritabanını Güncel Tutun**
   - En güncel açıkların tespiti için periyodik olarak `nikto -update` komutunu çalıştırın.

---

## ⚠️ Yasal Uyarı

> **UYARI:** Nikto, sunucu loglarında belirgin izler bırakan agresif bir web tarayıcısıdır. Yalnızca **yazılı izin alınmış ve yetkilendirilmiş sistemlerde** kullanılmalıdır. Yetkisiz taramalar yasalara aykırıdır.

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto Dokümantasyonu](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### İlgili Kılavuzlar
- [Nmap](../../Nmap/translations/README.tr.md)
- [Gobuster](../../Gobuster/translations/README.tr.md)
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)

---

<p align="center">
  <b>🕷️ Web Sunucu Güvenliğinde Uzmanlaşın!</b><br>
  <i>Zafiyetleri saldırganlardan önce tespit edin</i>
</p>
