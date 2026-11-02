[🇬🇧 English](../../README.md) | [🇮🇹 Italiano](README.it.md)

# 🌐 Amass - Saldırı Yüzeyi Haritalama (Attack Surface Mapping)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN">
</p>

---

## 📋 İçindekiler

- [Amass Nedir?](#-amass-nedir)
- [Kurulum](#-kurulum)
- [Numaralandırma Modu (Enum)](#-numaralandırma-modu-enum)
- [İstihbarat Modu (Intel)](#-istihbarat-modu-intel)
- [DNS Modu](#-dns-modu)
- [Yapılandırma](#-yapılandırma)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Bug Bounty İş Akışı](#-bug-bounty-iş-akışı)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Amass Nedir?

**Amass**, ağ haritalama ve harici dijital varlık keşfi için geliştirilmiş bir OWASP projesidir:

- 🔍 **Alt Alan Adı Numaralandırma** - Alandaki en kapsamlı keşif aracı
- 🏢 **ASN Keşfi** - Kuruluşun IP ve ağ bloklarını tespit etme
- 📡 **DNS İstihbaratı** - Hem pasif hem aktif keşif teknikleri
- 🗺️ **Saldırı Yüzeyi Haritalama** - Altyapıyı ve ilişkileri görselleştirme

### Neden Amass?

| Özellik | Avantaj |
|---------|---------|
| 55'ten Fazla Veri Kaynağı | En kapsamlı ve eksiksiz sonuçlar |
| Pasif ve Aktif Mod | Gürültü ve fark edilebilirlik seviyesini seçebilme |
| ASN/Kuruluş İstihbaratı | Gizli kalmış altyapıyı açığa çıkarma |
| Grafik Veritabanı | Varlıklar arası ilişkileri haritalandırma |

---

## 🚀 Kurulum

```bash
# Go ile kurulum
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Doğrulama
amass -version
```

---

## 🔍 Numaralandırma Modu (Enum)

### Temel Alt Alan Adı Keşfi

```bash
# Pasif numaralandırma (hedefle doğrudan temas kurulmaz)
amass enum -passive -d example.com

# Aktif numaralandırma (DNS kaba kuvveti dahil)
amass enum -active -d example.com

# Kelime listesiyle kaba kuvvet (brute force)
amass enum -active -brute -d example.com -w wordlist.txt
```

### Çıktı Seçenekleri

```bash
# Dosyaya kaydetme
amass enum -passive -d example.com -o subdomains.txt

# JSON çıktısı
amass enum -passive -d example.com -json output.json

# Belirli bir dizine kaydetme
amass enum -passive -d example.com -dir ./amass_output/
```

### Çoklu Hedefler

```bash
# Birden fazla alan adı
amass enum -passive -d example.com -d target.com

# Dosyadan okuma
amass enum -passive -df domains.txt -o results.txt
```

### Gelişmiş Numaralandırma

```bash
# Zaman aşımı belirleme (dakika cinsinden)
amass enum -passive -d example.com -timeout 30

# Belirli kaynaklarla sınırlama
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Belirli kaynakları hariç tutma
amass enum -passive -d example.com -exclude Google,Bing

# Veri kaynaklarını gösterme
amass enum -passive -d example.com -src
```

### Özyinelemeli (Recursive) Numaralandırma

```bash
# Özyinelemeli aramayı etkinleştirme
amass enum -passive -d example.com -r

# Özyineleme derinliğini belirleme
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 İstihbarat Modu (Intel)

> Intel modu; kuruluşlarla ilişkili kök alan adlarını, ASN'leri ve CIDR IP bloklarını keşfeder.

### Kuruluş Varlıklarını Bulma

```bash
# Kuruluş adına göre arama
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Ters WHOIS (Reverse WHOIS) araması
amass intel -whois -d example.com
```

### ASN Keşfi

```bash
# Alan adı için ASN bulma
amass intel -d example.com

# ASN numarasına göre numaralandırma
amass intel -asn 13335
amass intel -active -asn 13335

# Birden fazla ASN
amass intel -asn 13335,15169
```

### CIDR Blokları

```bash
# CIDR bloğundaki varlıkları bulma
amass intel -active -cidr 192.168.1.0/24

# Birden fazla CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP Aralığı

```bash
# IP adres aralığı tarama
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 DNS Modu

### DNS Sorguları

```bash
# Temel DNS sorgulaması
amass dns -d example.com

# Belirli kayıt türleri
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Alt Alan Adlarını Çözümleme

```bash
# Keşfedilen alt alan adlarını çözümleme
amass dns -df subdomains.txt
```

---

## ⚙️ Yapılandırma

### Yapılandırma Dosyası Konumu

```bash
# Varsayılan konum
~/.config/amass/config.yaml

# Özel yapılandırma dosyası belirtme
amass enum -config ./my_config.yaml -d example.com
```

### Örnek Yapılandırma Dosyası

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### API Anahtarı Gerektiren Veri Kaynakları

| Kaynak | Ücretsiz Katman | API Gerekli mi? |
|--------|-----------------|-----------------|
| Crt.sh | ✅ | Hayır |
| SecurityTrails | ✅ | Evet |
| Shodan | ✅ | Evet |
| VirusTotal | ✅ | Evet |
| Censys | ✅ | Evet |
| PassiveTotal | Sınırlı | Evet |
| BinaryEdge | Sınırlı | Evet |

---

## 📊 Hızlı Başvuru

### Numaralandırma Komutları

| Komut | Açıklama |
|-------|----------|
| `amass enum -passive -d domain.com` | Pasif alt alan adı keşfi |
| `amass enum -active -d domain.com` | Aktif DNS keşfi |
| `amass enum -active -brute -d domain.com` | Alt alan adlarına kaba kuvvet |
| `amass enum -d domain.com -o out.txt` | Çıktıyı dosyaya kaydet |
| `amass enum -d domain.com -json out.json` | JSON formatında kaydet |

### İstihbarat (Intel) Komutları

| Komut | Açıklama |
|-------|----------|
| `amass intel -org "Company"` | Şirket adına göre varlıkları bul |
| `amass intel -asn 12345` | ASN'ye göre varlıkları numaralandır |
| `amass intel -cidr 10.0.0.0/8` | CIDR bloğunu numaralandır |
| `amass intel -whois -d domain.com` | Ters WHOIS sorgusu |

### Temel Parametreler

| Parametre | Açıklama |
|-----------|----------|
| `-d` | Hedef alan adı |
| `-df` | Dosyadan alan adları |
| `-o` | Çıktı dosyası |
| `-passive` | Yalnızca pasif mod (DNS yok) |
| `-active` | Aktif DNS'i etkinleştir |
| `-brute` | DNS kaba kuvvetini aç |
| `-src` | Veri kaynağını göster |
| `-timeout` | Dakika cinsinden zaman aşımı |

---

## 🎯 Bug Bounty İş Akışı

```bash
# 1. Pasif numaralandırma (güvenli ve gürültüsüz)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Kelime listesi ile aktif kaba kuvvet
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Kuruluş varlıklarını keşfetme
amass intel -org "Target Inc" -o org_domains.txt

# 4. Sonuçları birleştirme ve tekilleştirme
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Canlı sunucuları tespit etme
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Kaynaklar

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass Kullanıcı Kılavuzu](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### İlgili Başvuru Kılavuzları
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [Subfinder](../../Subfinder/translations/README.tr.md)
- [httpx](../../httpx/translations/README.tr.md)

---

<p align="center">
  <b>🌐 Saldırı Yüzeyini Haritalayın!</b><br>
  <i>Amass - En kapsamlı alt alan adı ve istihbarat aracı</i>
</p>
