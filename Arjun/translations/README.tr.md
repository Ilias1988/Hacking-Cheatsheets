[🇬🇧 English](../../README.md) | [🇮🇹 Italiano](README.it.md)

# 🔍 Arjun - Gizli Parametre Keşif Aracı

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP Parameter Discovery Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug-Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 İçindekiler

- [Arjun Nedir?](#-arjun-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Keşif Modları](#-keşif-modları)
- [Gelişmiş Seçenekler](#-gelişmiş-seçenekler)
- [Çıktı Seçenekleri](#-çıktı-seçenekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Bug Bounty İş Akışları](#-bug-bounty-iş-akışları)
- [İpuçları](#-ipuçları)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Arjun Nedir?

**Arjun**, web uygulamalarındaki gizli HTTP parametrelerini keşfeden gelişmiş bir araçtır:

- 🔍 **Parametre Keşfi** - Belgelenmemiş gizli GET ve POST parametrelerini bulur
- 🎯 **JSON Desteği** - JSON gövdesindeki parametreleri tespit eder
- 📡 **Çoklu İstek Metotları** - GET, POST ve JSON modlarını destekler
- ⚡ **Hızlı ve Verimli** - Yüksek eşzamanlı istek mimarisi

### Neden Arjun?

| Özellik | Avantaj |
|---------|---------|
| Gizli Parametreler | Kodda başvurulmayan gizli uç noktaları bulur |
| Çoklu Formatlar | GET, POST, JSON desteği |
| Pasif Mod | JavaScript dosyalarını tarayarak parametre çıkarır |
| Burp Entegrasyonu | Mevcut güvenlik iş akışınıza kolayca dahil edilir |

---

## 🚀 Kurulum

```bash
# pip ile kurulum (önerilen)
pip3 install arjun

# Kaynak koddan derleme
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Doğrulama
arjun --help
```

---

## 📖 Temel Kullanım

### Tek URL

```bash
# Temel parametre keşfi
arjun -u https://example.com/endpoint

# Çıktıyı dosyaya kaydetme
arjun -u https://example.com/endpoint -o results.txt
```

### Çoklu URL

```bash
# Dosyadan okuma
arjun -i urls.txt

# Standart girdiden (stdin) okuma
cat urls.txt | arjun --stdin
```

### HTTP Metodunu Belirleme

```bash
# GET parametreleri (varsayılan)
arjun -u https://example.com/search -m GET

# POST parametreleri
arjun -u https://example.com/api -m POST

# JSON istek gövdesi
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Keşif Modları

### GET Parametreleri

```bash
# GET parametrelerini keşfetme
arjun -u https://example.com/search?known=value -m GET

# Örnek çıktı:
# [param] id
# [param] user
# [param] debug
```

### POST Parametreleri

```bash
# POST form parametrelerini keşfetme
arjun -u https://example.com/login -m POST

# Mevcut veriyle birlikte gönderme
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### JSON Parametreleri

```bash
# JSON gövde parametrelerini keşfetme
arjun -u https://example.com/api/v1/users -m JSON

# Temel JSON verisiyle birlikte gönderme
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Birleşik Keşif

```bash
# Tüm metotları birlikte tarama
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

## ⚙️ Gelişmiş Seçenekler

### Özel Kelime Listesi (Wordlist)

```bash
# Özel kelime listesi kullanma
arjun -u https://example.com -w /path/to/wordlist.txt

# SecLists parametre listesini kullanma
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### İş Parçacığı ve Hız Sınırlaması

```bash
# İş parçacığı sayısı (varsayılan: 2)
arjun -u https://example.com -t 10

# İstek oranı sınırı (saniyedeki istek sayısı)
arjun -u https://example.com --rate-limit 10

# İstekler arası gecikme (saniye)
arjun -u https://example.com --delay 1
```

### Özel HTTP Başlıkları (Headers)

```bash
# Başlık ekleme
arjun -u https://example.com --headers "Authorization: Bearer token"

# Birden fazla başlık ekleme
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Dosyadan başlık okuma
arjun -u https://example.com -H headers.txt
```

### Bilinen Parametreleri Dahil Etme

```bash
# Bilinen parametreleri koruma
arjun -u https://example.com/search --include 'q=test&page=1'

# POST için
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Pasif Mod

```bash
# JavaScript dosyalarından parametre çıkarma
arjun -u https://example.com --passive

# Bu mod, JS dosyalarındaki olası parametre isimlerini analiz eder
```

### Yönlendirmeleri Devre Dışı Bırakma

```bash
# Yönlendirmeleri takip etme
arjun -u https://example.com --disable-redirects
```

### Zaman Aşımı (Timeout)

```bash
# Zaman aşımı süresi belirleme (saniye)
arjun -u https://example.com --timeout 15
```

### Kararlı Mod (Stable Mode)

```bash
# Daha güvenilir sonuçlar için kararlı mod (daha yavaş)
arjun -u https://example.com --stable
```

---

## 📤 Çıktı Seçenekleri

### Çıktı Formatları

```bash
# Düz metin çıktısı (varsayılan)
arjun -u https://example.com -oT results.txt

# JSON formatında çıktı
arjun -u https://example.com -oJ results.json

# Her iki formatta birden kaydetme
arjun -u https://example.com -oT text.txt -oJ json.json
```

### JSON Çıktı Örneği

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Burp Suite'ten İçe Aktarma

```bash
# Burp isteklerini içe aktarma
arjun --import burp_export.xml

# Burp dosyasını işleme
arjun -i burp_requests.txt --burp
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `arjun -u URL` | Parametreleri keşfeder |
| `arjun -u URL -m POST` | POST metodunu kullanır |
| `arjun -u URL -m JSON` | JSON gövdesini tarar |
| `arjun -i file.txt` | Dosyadan çoklu URL tarar |
| `arjun -u URL -oJ out.json` | JSON çıktısı üretir |

### Metot Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-m GET` | GET parametreleri |
| `-m POST` | POST form verileri |
| `-m JSON` | JSON gövde parametreleri |
| `-m GET,POST` | Birden fazla metot |

### Performans Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-t 10` | İş parçacığı sayısı |
| `--rate-limit 10` | Saniyedeki istek sayısı |
| `--delay 1` | İstekler arası gecikme süresi |
| `--timeout 15` | İstek zaman aşımı süresi |

### Keşif Parametreleri

| Parametre | Açıklama |
|-----------|----------|
| `-w wordlist.txt` | Özel kelime listesi |
| `--include 'a=b'` | Mevcut parametreleri dahil et |
| `--passive` | JS dosyalarından pasif çıkar |
| `--stable` | Kararlı ve hassas mod |

---

## 🎯 Bug Bounty İş Akışları

### Temel Keşif

```bash
# Hızlı parametre keşfi
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Özel Kelime Listesi ile Kapsamlı Tarama

```bash
# Kapsamlı keşif
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Kimlik Doğrulamalı (Authenticated) Test

```bash
# Oturum bilgileri ile tarama
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Toplu Uç Nokta Taraması

```bash
# Çoklu uç noktaları tarama
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Veya dosya belirterek
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Boru Hattı (Pipeline) Entegrasyonu

```bash
# Uç noktaları bulup ardından parametreleri keşfetme
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Keşfedilen Parametreleri Test Etme

```bash
# Keşif sonrası zafiyet testleri
# 1. Parametreleri keşfedin
arjun -u https://target.com/search -oJ params.json

# 2. Parametre isimlerini ayıklayın
cat params.json | jq -r '.[].params[].name'

# 3. ffuf ile test edin
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 İpuçları

1. **Önce pasif başlayın** - JavaScript dosyalarını tarayarak başlayın
2. **Kapsamlı kelime listeleri kullanın** - SecLists Burp parametre listeleri önerilir
3. **İstek oranını sınırlayın** - WAF veya güvenlik duvarı tarafından engellenmeyin
4. **Tüm metotları deneyin** - GET, POST ve JSON formatlarını mutlaka kontrol edin
5. **Başlıkları test edin** - X-Custom-Param, X-Debug gibi gizli başlıkları unutmayın
6. **Kimlik doğrulamalı test yapın** - Giriş yapılmış alanlarda daha fazla parametre gizlidir

### Yaygın Gizli Parametreler

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Kaynaklar

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp Parameter Names Listesi](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### İlgili Başvuru Kılavuzları
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [ffuf](../../ffuf/translations/README.tr.md)
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)

---

<p align="center">
  <b>🔍 Gizli Parametreleri Keşfedin!</b><br>
  <i>Arjun - Parametre Avcısı</i>
</p>
