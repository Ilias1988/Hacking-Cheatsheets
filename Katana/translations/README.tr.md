[🇬🇧 English](../../README.md)

# ⚔️ Katana - Yeni Nesil Web Tarayıcısı (Web Crawler)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Next-Generation Web Crawling Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 İçindekiler

- [Katana Nedir?](#-katana-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Tarama Seçenekleri](#-tarama-seçenekleri)
- [Headless Mod](#-headless-mod)
- [Çıktı ve Filtreleme](#-çıktı-ve-filtreleme)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Bug Bounty İş Akışları](#-bug-bounty-iş-akışları)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Katana Nedir?

**Katana**, ProjectDiscovery tarafından geliştirilen yeni nesil bir web tarama (crawling/spidering) çatısıdır:

- ⚡ **Hızlı** - Go tabanlı, yüksek eşzamanlı mimari
- 🔍 **JavaScript Render Desteği** - Headless tarayıcı desteği
- 📡 **Modern Web Mimarisi** - Tek Sayfa Uygulamaları (SPA) ve dinamik içerikleri işleme yeteneği
- 🎯 **Bug Bounty Odaklı** - Güvenlik testleri ve keşif için özel olarak geliştirilmiş

### Neden Katana?

| Özellik | Avantaj |
|---------|---------|
| Headless Chrome | Yoğun JavaScript kullanan uygulamaları tarar |
| Özel HTTP Başlıkları | Kimlik doğrulamalı (authenticated) tarama |
| Kapsam Kontrolü | Kapsam (scope) sınırları içinde kalma |
| Form Doldurma | Daha fazla gizli uç nokta keşfetme |

---

## 🚀 Kurulum

```bash
# Go ile kurulum (önerilen)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Doğrulama
katana -version
```

---

## 📖 Temel Kullanım

### Tek Hedef

```bash
# Temel tarama
katana -u https://example.com

# Çıktıyı kaydetme
katana -u https://example.com -o urls.txt

# Sessiz mod
katana -u https://example.com -silent
```

### Çoklu Hedef

```bash
# Standart girdiden (stdin)
echo "https://example.com" | katana

# Çoklu URL listesi
cat targets.txt | katana

# Dosyadan okuma
katana -list targets.txt
```

### Tarama Derinliği (Crawl Depth)

```bash
# Derinliği belirleme (varsayılan: 3)
katana -u https://example.com -d 5

# Derinlik 1 (yalnızca verilen hedef sayfa)
katana -u https://example.com -d 1
```

---

## 🕷️ Tarama Seçenekleri

### Kapsam (Scope) Kontrolü

```bash
# Yalnızca kapsam dahilinde tara (aynı alan adı)
katana -u https://example.com -fs dn

# Kapsam seçenekleri:
# dn - alan adı (domain name)
# rdn - kök alan adı (root domain)
# fqdn - tam nitelikli alan adı (full qualified domain name)

# Alan adı kapsamı belirleme
katana -u https://example.com -fs rdn
```

### Tarama Stratejisi

```bash
# Önce Derinlik (varsayılan: Depth-first)
katana -u https://example.com -cs depth-first

# Önce Genişlik (Breadth-first)
katana -u https://example.com -cs breadth-first
```

### Eşzamanlılık (Concurrency)

```bash
# Eşzamanlı istek sayısı (varsayılan: 10)
katana -u https://example.com -c 50

# İstek oranı sınırı (saniyedeki istek sayısı)
katana -u https://example.com -rl 100

# Paralellik derecesi
katana -u https://example.com -p 10
```

### Tarama Süresi

```bash
# Zaman aşımı süresi (saniye)
katana -u https://example.com -timeout 30

# Maksimum toplam tarama süresi
katana -u https://example.com -ct 300
```

### Otomatik Form Doldurma

```bash
# Form doldurmayı etkinleştirme
katana -u https://example.com -aff

# Özel değerlerle form doldurma
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Headless Mod

### Headless Tarayıcıyı Etkinleştirme

```bash
# Headless Chrome etkinleştirme
katana -u https://example.com -headless

# Belirli derinlikle headless tarama
katana -u https://example.com -headless -d 3
```

### Headless Seçenekleri

```bash
# Özel Chrome ikili yolu belirtme
katana -u https://example.com -headless -system-chrome

# Korumalı alanı devre dışı bırakma (root kullanıcısı için)
katana -u https://example.com -headless -no-sandbox

# Sayfa ekran görüntülerini yakalama
katana -u https://example.com -headless -screenshot
```

### JavaScript Taraması

```bash
# JS yüklenmesini bekleme süresi (milisaniye)
katana -u https://example.com -headless -timeout 5

# JS uç noktalarını tarama
katana -u https://example.com -js-crawl
```

---

## 📤 Çıktı ve Filtreleme

### Çıktı Formatları

```bash
# Standart çıktıyı dosyaya kaydetme
katana -u https://example.com -o results.txt

# JSON çıktısı
katana -u https://example.com -jsonl -o results.json

# Renksiz çıktı
katana -u https://example.com -nc
```

### Görüntülenecek Alanlar (Fields)

```bash
# Belirli alanları gösterme
katana -u https://example.com -f url,method,status

# Kullanılabilir alanlar:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Uzantıya Göre Filtreleme

```bash
# Belirli uzantıları eşleştirme
katana -u https://example.com -em js,php,asp

# Gereksiz uzantıları hariç tutma
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Durum Koduna Göre Filtreleme

```bash
# Belirli durum kodlarını eşleştirme
katana -u https://example.com -mc 200,301,302

# Belirli durum kodlarını filtreleme
katana -u https://example.com -fc 404,500
```

### Özel HTTP Başlıkları (Headers)

```bash
# Başlık ekleme
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Birden fazla başlık ekleme
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Proxy Desteği

```bash
# HTTP proxy
katana -u https://example.com -proxy http://127.0.0.1:8080

# Burp Suite üzerinden yönlendirme
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `katana -u URL` | Tek bir URL'yi tarar |
| `katana -list file.txt` | Dosyadan URL listesini tarar |
| `katana -u URL -d 5` | Tarama derinliğini 5 yapar |
| `katana -u URL -headless` | Headless tarayıcıyı etkinleştirir |
| `katana -u URL -o out.txt` | Sonuçları dosyaya kaydeder |

### Kapsam Seçenekleri

| Parametre | Açıklama |
|-----------|----------|
| `-fs dn` | Aynı alan adı |
| `-fs rdn` | Kök alan adı |
| `-fs fqdn` | Tam nitelikli alan adı |
| `-cs depth-first` | Önce derinlik araması |
| `-cs breadth-first` | Önce genişlik araması |

### Performans Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-c 50` | Eşzamanlı istek sayısı |
| `-p 10` | Paralellik derecesi |
| `-rl 100` | İstek oranı sınırı |
| `-timeout 30` | İstek zaman aşımı süresi |

### Filtreleme Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-em js,php` | Belirtilen uzantıları eşleştir |
| `-ef png,jpg` | Belirtilen uzantıları hariç tut |
| `-mc 200` | Belirtilen durum kodlarını eşleştir |
| `-fc 404` | Belirtilen durum kodlarını filtrele |

---

## 🎯 Bug Bounty İş Akışları

### Hızlı Temel Tarama

```bash
# Hızlı tarama
katana -u https://target.com -d 3 -o urls.txt
```

### Kapsamlı ve Derinlemesine Tarama

```bash
# JavaScript destekli derin tarama
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Parametre Keşfi

```bash
# Parametre içeren URL'leri bulma
katana -u https://target.com -d 3 | grep "?" | sort -u

# Belirli parametreleri grep ile ayıklama
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Diğer Araçlarla Birlikte Kullanım

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Eksiksiz keşif zinciri
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Kimlik Doğrulamalı (Authenticated) Tarama

```bash
# Oturum çerezi ile tarama
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Kaynaklar

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### İlgili Başvuru Kılavuzları
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [httpx](../../httpx/translations/README.tr.md)
- [Nuclei](../../Nuclei/translations/README.tr.md)
- [GAU](../../GAU/README.md)

---

<p align="center">
  <b>⚔️ Her Şeyi Tarayın!</b><br>
  <i>Katana - Yeni Nesil Web Tarayıcısı</i>
</p>
