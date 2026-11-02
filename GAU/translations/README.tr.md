[🇬🇧 English](../../README.md)

# 🔗 GAU - Get All URLs

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Fast URL Harvesting
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 İçindekiler

- [GAU Nedir?](#-gau-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Sağlayıcılar (Providers)](#-sağlayıcılar-providers)
- [Filtreleme](#-filtreleme)
- [Entegrasyon](#-entegrasyon)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Bug Bounty İş Akışı](#-bug-bounty-iş-akışı)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 GAU Nedir?

**GAU** (Get All URLs), birden çok genel ve açık istihbarat kaynağından bilinen URL adreslerini toplayan pasif bir URL keşif aracıdır:

- 🔍 **Wayback Machine** - Geçmişe dönük arşivlenmiş URL'ler
- 🌐 **Common Crawl** - Web tarama veri tabanı
- 📡 **AlienVault OTX** - Tehdit istihbaratı kaynaklı URL'ler
- 🔒 **URLScan.io** - Taranmış web sitesi URL'leri

### Neden GAU?

| Avantaj | Açıklama |
|---------|----------|
| **Pasif Keşif** | Hedefle doğrudan temas kurulmaz, iz bırakmaz |
| **Geçmiş Veriler** | Eski, unutulmuş veya gizli kalmış uç noktaları bulur |
| **Hızlı** | Paralel veri çekme mimarisi |
| **Çoklu Kaynak** | Kapsamlı ve geniş sonuç kümesi |

---

## 🚀 Kurulum

```bash
# Go ile kurulum (önerilen)
go install github.com/lc/gau/v2/cmd/gau@latest

# Kaynak koddan derleme
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Doğrulama
gau --version
```

---

## 📖 Temel Kullanım

### Tek Alan Adı

```bash
# Temel kullanım
gau example.com

# Çıktıyı dosyaya kaydetme
gau example.com -o urls.txt

# Banner olmadan çalıştırma
gau --subs example.com
```

### Çoklu Alan Adı

```bash
# Standart girdiden (stdin)
echo "example.com" | gau

# Çoklu alan adı listesinden
cat domains.txt | gau

# Dosyayı girdi olarak verme
gau --threads 5 < domains.txt
```

### Alt Alan Adlarını Dahil Etme

```bash
# Sonuçlara alt alan adlarını da dahil et
gau --subs example.com
```

### İş Parçacığı (Threading)

```bash
# İş parçacığı sayısı (varsayılan: 1)
gau --threads 5 example.com

# İstek sınırlarına takılmamak için ayarlama
gau --threads 2 example.com
```

---

## 🌐 Sağlayıcılar (Providers)

### Kullanılabilir Sağlayıcılar

| Sağlayıcı | Açıklama | Varsayılan |
|-----------|----------|------------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Sağlayıcı Seçimi

```bash
# Yalnızca belirli sağlayıcıları kullanma
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Belirli bir sağlayıcıyı hariç tutma
gau --blacklist commoncrawl example.com
```

### Sağlayıcı Yapılandırması

```bash
# Yapılandırma dosyası konumu
~/.gau.toml

# Örnek yapılandırma
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filtreleme

### Durum Koduna Göre Filtreleme

```bash
# Yalnızca HTTP 200 dönmüş olan URL'ler
gau --mc 200 example.com

# Birden fazla durum kodu eşleştirme
gau --mc 200,301,302 example.com

# Belirli durum kodlarını filtreleme (hariç tutma)
gau --fc 404,500 example.com
```

### Uzantıya Göre Filtreleme

```bash
# Uzantı eşleştirme
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Belirtilen uzantıları hariç tutma
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### MIME Türüne Göre Filtreleme

```bash
# İçerik türüne göre filtreleme
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Çıktı Formatı

```bash
# JSON formatında çıktı
gau --json example.com

# Ayrıntılı mod
gau --verbose example.com
```

---

## 🔗 Entegrasyon

### httpx ile Kullanım

```bash
# Canlı URL'leri kontrol etme
gau example.com | httpx -silent

# Durum kodları ve sayfa başlıkları ile
gau example.com | httpx -sc -title
```

### grep / Parametre Ayıklama ile Kullanım

```bash
# Parametre içeren URL'leri bulma
gau example.com | grep "="

# Belirli kalıpları arama
gau example.com | grep -E "\.php\?|\.asp\?"

# GF desenleri ile zafiyet arama
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### urldedupe ile Kullanım

```bash
# Yinelenenleri temizleme
gau example.com | urldedupe

# Alternatif: sort -u
gau example.com | sort -u
```

### uro ile Kullanım

```bash
# Akıllı URL tekilleştirme (deduplication)
gau example.com | uro
```

### Eksiksiz Keşif Zinciri (Pipeline)

```bash
# Kapsamlı URL toplama zinciri
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `gau domain.com` | Alan adına ait URL'leri toplar |
| `gau --subs domain.com` | Alt alan adlarını da dahil eder |
| `gau -o urls.txt domain.com` | Çıktıyı dosyaya kaydeder |
| `gau --threads 5 domain.com` | Çoklu iş parçacığıyla çalışır |

### Filtreleme Seçenekleri

| Parametre | Açıklama |
|-----------|----------|
| `--mc 200` | Belirtilen durum kodunu eşleştirir |
| `--fc 404` | Belirtilen durum kodunu filtreler |
| `--mt "regex"` | Regex eşleştirmesi yapar |
| `--ft "regex"` | Regex eşleşenleri filtreler |
| `--json` | JSON formatında çıktı verir |

### Sağlayıcı Seçenekleri

| Parametre | Açıklama |
|-----------|----------|
| `--providers wayback,otx` | Belirli sağlayıcıları kullanır |
| `--blacklist commoncrawl` | Belirtilen sağlayıcıyı hariç tutar |

### Faydalı Tek Satırlık Komutlar (One-Liners)

```bash
# Tüm JavaScript dosyalarını bulma
gau example.com | grep -E "\.js$" | sort -u

# API uç noktalarını bulma
gau example.com | grep -iE "/api/|/v[0-9]/"

# Parametre içeren URL'leri bulma
gau example.com | grep "?" | grep "=" | sort -u

# Olası SQLi noktalarını bulma
gau example.com | gf sqli

# Olası XSS noktalarını bulma
gau example.com | gf xss

# Parametre içeren canlı URL'ler
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Bug Bounty İş Akışı

```bash
# 1. Tüm URL'leri toplayın
gau --subs --threads 5 target.com > all_urls.txt

# 2. İlginç URL'leri filtreleyin ve tekilleştirin
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Canlı olanları test edin
cat params.txt | httpx -silent > live_params.txt

# 4. Potansiyel zafiyet kategorilerini ayıklayın
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Otomatik test gerçekleştirin
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Kaynaklar

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### İlgili Başvuru Kılavuzları
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [Subfinder](../../Subfinder/translations/README.tr.md)
- [httpx](../../httpx/translations/README.tr.md)

---

<p align="center">
  <b>🔗 Tüm URL'leri Toplayın!</b><br>
  <i>GAU - Pasif URL Keşfi</i>
</p>
