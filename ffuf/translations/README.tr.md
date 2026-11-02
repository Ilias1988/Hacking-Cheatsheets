# 🚀 ffuf - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool (Daha Hızlı Fuzz Et)
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Go dili ile geliştirilmiş en hızlı ve esnek web fuzzing aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Dizin ve Dosya Fuzzing](#-dizin-ve-dosya-fuzzing)
- [Alt Alan Adı (Subdomain) & VHost Fuzzing](#-alt-alan-adı-subdomain--vhost-fuzzing)
- [Parametre Fuzzing (GET)](#-parametre-fuzzing-get)
- [POST Verisi Fuzzing](#-post-verisi-fuzzing)
- [Filtreleme & Eşleştirme (Filtering & Matching)](#-filtreleme--eşleştirme-filtering--matching)
- [Gelişmiş Özellikler](#-gelişmiş-özellikler)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**ffuf** (Fuzz Faster U Fool), Go dili ile yazılmış son derece hızlı ve modüler bir web fuzzer'dır. Bug bounty avcıları ve sızma testi uzmanları tarafından gizli dizin tespiti, sanal sunucu (VHost) keşfi ve parametre madenciliği için en çok tercih edilen araçtır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Yüksek Hız** | Go mimarisi sayesinde olağanüstü istek gönderme hızı |
| **Tam Esneklik** | HTTP isteğinin herhangi bir noktasına (URL, başlık, gövde) kelime enjekte edebilme |
| **Gelişmiş Filtreleme** | Boyut, satır, kelime sayısı, durum kodu ve regex ile filtreleme |
| **Özyineleme (Recursion)** | Keşfedilen dizinlerin içine otomatik olarak inebilme |
| **Çoklu Çıktı Formatı** | JSON, CSV, HTML ve dosya desteği |

### ffuf vs Diğer Araçlar Karşılaştırması

| Özellik | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Hız** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Esneklik** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST Fuzzing** | ✅ Evet | ❌ Hayır | ❌ Hayır |
| **Özyineleme** | ✅ Evet | ✅ Evet | ✅ Evet |
| **Filtre Seçenekleri** | Çok Zengin | Sınırlı | Sınırlı |

---

## 📥 Kurulum

### Go ile Kurulum
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt update && sudo apt install ffuf
```

### İkili Dosya Olarak İndirme (Releases)
```bash
# GitHub Releases sayfasından:
# https://github.com/ffuf/ffuf/releases

wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Kurulumu Doğrulama
```bash
ffuf -V
ffuf -h
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
ffuf [seçenekler] -u <URL/FUZZ> -w <kelime_listesi>
```

### `FUZZ` Anahtar Kelimesi

`FUZZ` kelimesi, kelime listesindeki girdilerin isteğin tam olarak neresine yerleştirileceğini belirtir:

```bash
# URL yolunda (Dizin / Dosya arama)
ffuf -u https://target.com/FUZZ -w wordlist.txt

# Alt alan adında (Subdomain keşfi)
ffuf -u https://FUZZ.target.com -w wordlist.txt

# GET parametre değerinde
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# POST gövdesinde
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Temel Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Hedef URL (`FUZZ` anahtar sözcüğü ile birlikte) |
| `-w` | Kelime listesi dosya yolu |
| `-o` | Sonuçların yazılacağı dosya |
| `-of` | Çıktı formatı (`json`, `csv`, `html`) |
| `-t` | İş parçacığı sayısı (varsayılan: 40) |
| `-p` | İstekler arası gecikme (saniye) |
| `-r` | Yönlendirmeleri takip et (Follow redirects) |
| `-v` | Ayrıntılı mod |
| `-s` | Sessiz mod (yalnızca bulunan sonuçları basar) |

---

## 📁 Dizin ve Dosya Fuzzing

### Temel Dizin Kaba Kuvveti

```bash
# Standart dizin taraması
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Dosya uzantıları ekleyerek
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Farklı web teknolojileri için uzantılar
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Özyinelemeli (Recursive) Tarama

```bash
# Özyinelemeyi etkinleştir (Bulunan dizinleri otomatik tara)
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Özyineleme derinliğini sınırla (Örn: 2 seviye)
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Özyineleme stratejisi belirleme
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Durum Koduna Göre Eşleme / Filtreleme

```bash
# Yalnızca HTTP 200 yanıtlarını göster
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Belirli birden çok durum kodunu yakala
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# 404 yanıtlarını gizle
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Tüm yanıtları eşleştir, yalnızca 404'ü filtrele
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

---

## 🌐 Alt Alan Adı (Subdomain) & VHost Fuzzing

### Sanal Sunucu (VHost) Keşfi

```bash
# Host başlığını manipüle ederek VHost arama
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Yanıt boyutuna göre filtreleme (Varsayılan sayfa boyutunu hariç tutar)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# SecLists listesi ile VHost keşfi
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Doğrudan DNS Alt Alan Adı Keşfi

```bash
# URL içinde subdomain arama
ffuf -u https://FUZZ.target.com -w subdomains.txt

# DNS yönlendirmelerini takip ederek
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

---

## 🔍 Parametre Fuzzing (GET)

### GET Parametre Adı Keşfi

```bash
# Gizli parametre isimlerini bulma
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Parametre değerini deneme
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Çoklu liste ile parametre ve değer eşleme (W1 ve W2)
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Burp Parametre Listesi ile Arama

```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 POST Verisi Fuzzing

### Form Verisi Fuzzing

```bash
# URL-encoded POST isteği
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# Content-Type başlığı belirterek
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### JSON Formatında POST Fuzzing

```bash
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Çoklu Liste ile Kimlik Bilgisi Doldurma (Credential Stuffing)

```bash
# İki liste: USER ve PASS
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Clusterbomb modu (Tüm olası kombinasyonlar)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtreleme & Eşleştirme (Filtering & Matching)

### Filtre Seçenekleri (-f...)

| Parametre | Açıklama | Örnek |
|-----------|----------|-------|
| `-fc` | Belirtilen durum kodlarını filtrele | `-fc 404,403` |
| `-fs` | Belirtilen yanıt boyutlarını (bayt) filtrele | `-fs 1234` |
| `-fw` | Belirtilen kelime sayısını filtrele | `-fw 100` |
| `-fl` | Belirtilen satır sayısını filtrele | `-fl 50` |
| `-ft` | Yanıt süresine (ms) göre filtrele | `-ft 5000` |
| `-fr` | Regex eşleşmesine göre filtrele | `-fr "error"` |

### Eşleştirme Seçenekleri (-m...)

| Parametre | Açıklama | Örnek |
|-----------|----------|-------|
| `-mc` | Yalnızca belirtilen durum kodlarını göster | `-mc 200,301` |
| `-ms` | Yalnızca belirtilen yanıt boyutunu göster | `-ms 1234` |
| `-mw` | Yalnızca belirtilen kelime sayısını göster | `-mw 100` |
| `-ml` | Yalnızca belirtilen satır sayısını göster | `-ml 50` |
| `-mt` | Yanıt süresine göre eşleştir | `-mt 5000` |
| `-mr` | Yanıtta regex metni geçenleri göster | `-mr "success"` |

### Otomatik Kalibrasyon (-ac)

```bash
# Yanıt anormalliklerini ve sahte 200/404 sayfalarını otomatik olarak tespit edip filtreler:
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Belirli bir rastgele anahtar dize ile kalibre et
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Gelişmiş Özellikler

### Hız Sınırlaması ve Gizlilik

```bash
# Saniyedeki istek sayısı (rate limit)
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# İstekler arasına sabit gecikme koyma (saniye)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Rastgele dalgalı gecikme (Jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### İş Parçacığı (Threads)

```bash
# İş parçacığı sayısı (varsayılan: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Sessiz ve yavaş tarama
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Başlıklar ve Çerezler

```bash
# Özel HTTP başlığı
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Yetkilendirme ve IP başlıkları
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Çerez gönderme
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy Kullanımı

```bash
# Burp Suite proxy üzerinden yönlendirme
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Rapor Çıktısı

```bash
# JSON çıktısı
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# HTML raporu
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Tüm formatlarda kaydet
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Temel Dizin Taraması
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Örnek 2: PHP Dosyaları ve Yedekleri
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Örnek 3: Sanal Sunucu / Alt Alan Adı Keşfi
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Örnek 4: REST API Uç Noktaları
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Örnek 5: Oturum Kaba Kuvveti (Login Brute Force)
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Örnek 6: Tam Kapsamlı Bug Bounty Taraması
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| Dizin taraması | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Uzantılar ile | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Subdomain taraması | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST fuzzing | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| 404 filtreleme | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Boyut filtreleme | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Otomatik kalibrasyon | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Çıktı kaydetme | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **Her Zaman Otomatik Kalibrasyon (`-ac`) ile Başlayın**
   - Sahte 200 veya özel hata sayfalarının sonuçları kirletmesini önler.

2. **Kapsamlı Uzantı Listeleri Belirleyin**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **WAF Korumasını Aşmak İçin İstek Hızını Ayarlayın**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

---

## ⚠️ Yasal Uyarı

> **UYARI:** ffuf, son derece yüksek hacimli istek üretebilen bir fuzzing aracıdır. Yalnızca **yazılı izin alınmış ve yetkili olduğunuz sistemlerde** kullanılmalıdır. Hedef sunucularda hizmet kesintisine (DoS) yol açmamaya özen gösterin.

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [ffuf GitHub Deposu](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Kelime Listeleri
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

### İlgili Kılavuzlar
- [Nuclei](../../Nuclei/translations/README.tr.md)
- [Gobuster](../../Gobuster/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)

---

<p align="center">
  <b>🚀 Daha Hızlı Fuzz Edin, Daha Fazla Açık Keşfedin!</b><br>
  <i>Bug bounty avcılarının vazgeçilmez fuzzer aracı</i>
</p>
