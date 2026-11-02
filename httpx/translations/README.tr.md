[🇬🇧 English](../../README.md)

# 🌐 httpx - Kapsamlı Başvuru Kılavuzu

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Fast HTTP Probe & Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ ProjectDiscovery tarafından geliştirilen hızlı ve çok amaçlı HTTP araç seti</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Sondaj ve Keşif](#-sondaj-ve-keşif)
- [Bilgi Çıkarımı](#-bilgi-çıkarımı)
- [Filtreleme ve Eşleştirme](#-filtreleme-ve-eşleştirme)
- [Çıktı Seçenekleri](#-çıktı-seçenekleri)
- [Gelişmiş Özellikler](#-gelişmiş-özellikler)
- [Bug Bounty İş Akışı](#-bug-bounty-iş-akışı)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Pratikler](#-ipuçları-ve-en-iyi-pratikler)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**httpx**, retryablehttp kütüphanesini kullanarak birden çok yoklama ve HTTP sondajı gerçekleştirebilen son derece hızlı ve çok amaçlı bir HTTP araç setidir. Bug bounty avcıları ve güvenlik araştırmacıları için özel olarak tasarlanmıştır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Hızlı** | Go tabanlı, yüksek eşzamanlılık (concurrency) |
| **Çoklu Sondaj** | Durum kodu, başlık, teknoloji, yanıt başlıkları |
| **Teknoloji Tespiti** | Wappalyzer tabanlı teknoloji tanıma |
| **Ekran Görüntüsü** | Headless tarayıcı ile ekran görüntüsü alma |
| **Boru Hattı (Pipeline)** | subfinder, nuclei vb. araçlarla kusursuz entegrasyon |
| **JSON Çıktısı** | Yapılandırılmış veri çıktısı desteği |

### httpx ve Diğer Araçların Karşılaştırması

| Özellik | httpx | curl | wget |
|---------|-------|------|------|
| **Hız** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Eşzamanlılık** | ✅ Evet | ❌ Hayır | ❌ Hayır |
| **Teknoloji Tespiti** | ✅ Evet | ❌ Hayır | ❌ Hayır |
| **Ekran Görüntüsü** | ✅ Evet | ❌ Hayır | ❌ Hayır |
| **Boru Hattı Uyumu** | ✅ Evet | ⚠️ Manuel | ⚠️ Manuel |

---

## 📥 Kurulum

### Go ile Kurulum
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### İkili (Binary) İndirme
```bash
# Sürümler sayfasından indirin
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Kurulumu Doğrulama
```bash
httpx -version
httpx -h
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Temel Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Tek bir hedef URL |
| `-l` | URL listesini içeren dosya |
| `-sc` | HTTP durum kodunu göster (status code) |
| `-title` | Sayfa başlığını göster (page title) |
| `-td` | Teknoloji tespiti yap (technology detection) |
| `-o` | Çıktı dosyası |
| `-silent` | Sessiz mod |
| `-json` | JSON formatında çıktı |

### Hızlı Başlangıç
```bash
# Tek bir URL'yi yoklama
httpx -u https://example.com

# Dosyadan liste yoklama
httpx -l urls.txt

# Standart girdiden (stdin/boru hattı) okuma
cat hosts.txt | httpx

# Subfinder ile birlikte kullanım
subfinder -d example.com -silent | httpx
```

---

## 🔍 Sondaj ve Keşif

### Temel Sondaj (Probing)

```bash
# Sunucuları HTTP/HTTPS için yoklama
cat hosts.txt | httpx

# Durum kodunu dahil etme
cat hosts.txt | httpx -sc

# Belirli portları yoklama
cat hosts.txt | httpx -p 80,443,8080,8443

# Tüm yaygın web portlarını yoklama
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Port Taraması

```bash
# Özel portlar
httpx -l hosts.txt -p 80,443,8080,8443

# Port aralığı
httpx -l hosts.txt -p 80-100

# Yaygın web port protokolleri
httpx -l hosts.txt -p http:80,https:443
```

### Protokol Tespiti

```bash
# HTTP/HTTPS yoklaması
cat hosts.txt | httpx

# Yönlendirmeleri (redirects) takip etme
cat hosts.txt | httpx -follow-redirects

# Maksimum yönlendirme sınırı
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Fallback yapma (yedek protokole geçme)
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Bilgi Çıkarımı

### Durum Kodları ve Sayfa Başlıkları

```bash
# Durum kodu
httpx -l urls.txt -sc

# Sayfa başlığı
httpx -l urls.txt -title

# Her ikisi birlikte
httpx -l urls.txt -sc -title

# İçerik uzunluğu (Content Length)
httpx -l urls.txt -cl

# Web sunucusu bilgisi (Server header)
httpx -l urls.txt -server

# Tüm temel bilgiler bir arada
httpx -l urls.txt -sc -title -cl -server
```

### Teknoloji Tespiti

```bash
# Kullanılan teknolojileri tespit etme
httpx -l urls.txt -td

# Teknoloji tespiti (ayrıntılı)
httpx -l urls.txt -tech-detect

# Diğer bilgilerle birlikte
httpx -l urls.txt -sc -title -td
```

### Başlıklar ve Yanıt İçeriği

```bash
# Yanıt başlıkları (Response headers)
httpx -l urls.txt -response-header

# Belirli başlıkları dahil etme
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Yanıt gövdesinin karma (hash) değeri
httpx -l urls.txt -hash md5

# Yanıt gövdesi önizlemesi (Body preview)
httpx -l urls.txt -bp
```

### IP ve DNS Bilgileri

```bash
# IP adresi
httpx -l urls.txt -ip

# CNAME kaydı
httpx -l urls.txt -cname

# CDN tespiti
httpx -l urls.txt -cdn

# ASN bilgisi
httpx -l urls.txt -asn

# Tüm DNS/Ağ bilgileri
httpx -l urls.txt -ip -cname -cdn -asn
```

### SSL/TLS Bilgileri

```bash
# TLS sondajı
httpx -l urls.txt -tls-probe

# TLS yakalama
httpx -l urls.txt -tls-grab

# Sertifika bilgileri
httpx -l urls.txt -tlsgrab
```

### Favicon Hash Değeri

```bash
# Favicon hash'ini alma (Shodan aramalarında çok etkilidir)
httpx -l urls.txt -favicon
```

### Ekran Görüntüsü Alma

```bash
# Ekran görüntüleri alma
httpx -l urls.txt -screenshot

# Ekran görüntülerini belirli bir dizine kaydetme
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Headless tarayıcı seçenekleri (sistemdeki Chrome ile)
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtreleme ve Eşleştirme

### Durum Koduna Göre Filtreleme

```bash
# Belirli durum kodlarını eşleştirme
httpx -l urls.txt -mc 200,301,302

# Belirli durum kodlarını filtreleme (hariç tutma)
httpx -l urls.txt -fc 404,403,500

# Tümünü eşleştir, yalnızca 404'leri filtrele
httpx -l urls.txt -mc all -fc 404
```

### Yanıt Boyutuna Göre Filtreleme

```bash
# Belirli içerik uzunluğunu eşleştirme
httpx -l urls.txt -ml 1234

# 0 baytlık boş yanıtları filtreleme
httpx -l urls.txt -fl 0

# Satır sayısına göre eşleştirme
httpx -l urls.txt -mlc 100

# Satır sayısına göre filtreleme
httpx -l urls.txt -flc 50
```

### İçeriğe Göre Filtreleme

```bash
# Yanıt gövdesinde regex eşleştirme
httpx -l urls.txt -mr "admin|login"

# Yanıt gövdesinde regex filtreleme
httpx -l urls.txt -fr "error|not found"

# Dize eşleştirme
httpx -l urls.txt -ms "dashboard"
```

### Teknolojiye Göre Filtreleme

```bash
# Teknolojiye göre eşleştirme
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Belirli bir teknolojiyi hariç tutma
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Çıktı Seçenekleri

### Çıktı Formatları

```bash
# Düz metin
httpx -l urls.txt -o results.txt

# JSON çıktısı
httpx -l urls.txt -json -o results.json

# JSON lines çıktısı
httpx -l urls.txt -jsonl -o results.jsonl

# CSV formatı
httpx -l urls.txt -csv -o results.csv
```

### Çıktı Alanları

```bash
# Yanıtları diske kaydetme
httpx -l urls.txt -sr -srd ./responses/

# Ekran görüntülerini kaydetme
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Özel çıktı alanları
httpx -l urls.txt -o output.txt -sc -title -td
```

### Sessiz ve Ayrıntılı Modlar

```bash
# Sessiz mod (yalnızca URL'ler)
httpx -l urls.txt -silent

# Ayrıntılı mod
httpx -l urls.txt -v

# Hata ayıklama (debug) modu
httpx -l urls.txt -debug

# Renksiz çıktı
httpx -l urls.txt -nc
```

---

## ⚡ Gelişmiş Özellikler

### İş Parçacığı ve Hız Sınırlaması

```bash
# İş parçacığı sayısı (varsayılan: 50)
httpx -l urls.txt -threads 100

# İstek oranı sınırlaması (rate limit)
httpx -l urls.txt -rate-limit 100

# Zaman aşımı süresi (saniye)
httpx -l urls.txt -timeout 10

# Tekrar deneme sayısı
httpx -l urls.txt -retries 3
```

### Proxy Desteği

```bash
# HTTP proxy
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS proxy
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite üzerinden geçirme
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Özel HTTP Başlıkları (Headers)

```bash
# Özel başlık ekleme
httpx -l urls.txt -H "Authorization: Bearer token123"

# Birden fazla başlık ekleme
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# Özel User-Agent tanımlama
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### İstek Metotları

```bash
# HEAD isteği (daha hızlıdır)
httpx -l urls.txt -method HEAD

# Özel istek metodu
httpx -l urls.txt -method OPTIONS

# Gövdeli POST isteği
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Kaldığı Yerden Devam Etme (Resume)

```bash
# Son pozisyondan devam etme
httpx -l urls.txt -resume

# İlerlemeyi dosyaya kaydetme
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Bug Bounty İş Akışı

### Eksiksiz Keşif Boru Hattı

```bash
# Adım 1: Alt alan adı numaralandırması
subfinder -d target.com -o subs.txt

# Adım 2: httpx ile canlı sunucuları tespit etme
cat subs.txt | httpx -sc -title -td -o live.txt

# Adım 3: Zafiyet taraması
nuclei -l live.txt -severity critical,high

# Tek satırda zincirleme
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Kapsamlı Keşif

```bash
# Tüm bilgileri toplama
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Tüm Canlı Sunucuların Ekran Görüntüsünü Alma

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### İlginç Uç Noktaları Tespit Etme

```bash
# Yönetim panellerini bulma
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# API uç noktalarını bulma
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Belirli dosya/yolları kontrol etme
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Teknolojiye Özel Hedef Avlama

```bash
# WordPress sitelerini bulma
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Nginx sunucularını bulma
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Temel Sondaj
```bash
echo "https://example.com" | httpx -sc -title
```

### Örnek 2: Alt Alan Adlarından Tam Kapsamlı Keşif
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Örnek 3: Tüm Sunucuların Ekran Görüntüsünü Alma
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Örnek 4: Yalnızca Canlı Sunucuları Ayıklama
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Örnek 5: Teknoloji Tespiti ve JSON Çıktısı
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Örnek 6: Proxy (Burp) ile Çalıştırma
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Örnek 7: Özel Portları Yoklama
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Örnek 8: Yalnızca HTTP 200 Dönenleri Filtreleme
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Örnek 9: İşleme İçin JSON Çıktısı Alma
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Örnek 10: Eksiksiz Boru Hattı Zinciri
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar

| Görev | Komut |
|-------|-------|
| Temel sondaj | `httpx -u url` |
| Dosyadan okuma | `httpx -l urls.txt` |
| Durum kodu | `httpx -l urls.txt -sc` |
| Sayfa başlığı | `httpx -l urls.txt -title` |
| Teknoloji tespiti | `httpx -l urls.txt -td` |
| IP adresi | `httpx -l urls.txt -ip` |
| Ekran görüntüsü | `httpx -l urls.txt -screenshot` |
| JSON çıktısı | `httpx -l urls.txt -json -o out.json` |

### Sık Kullanılan Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Tekil URL |
| `-l` | URL liste dosyası |
| `-sc` | Durum kodu |
| `-title` | Sayfa başlığı |
| `-td` | Teknoloji tespiti |
| `-ip` | IP adresi |
| `-cdn` | CDN tespiti |
| `-server` | Web sunucu bilgisi |
| `-mc` | Eşleştirilecek kodlar |
| `-fc` | Filtrelenecek kodlar |
| `-json` | JSON çıktısı |
| `-silent` | Sessiz mod |

### Yaygın Boru Hatları

| Boru Hattı | Komut |
|------------|-------|
| Canlı sunucular | `subfinder -d target.com \| httpx -silent` |
| Bilgi toplamalı | `subfinder -d target.com \| httpx -sc -title -td` |
| Nuclei'ye aktarma | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 İpuçları ve En İyi Pratikler

### Bug Bounty İpuçları

1. **Subfinder ile Birlikte Kullanın**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Büyük Resmi Görün**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Önceliklendirme (Triage) İçin Ekran Görüntüsü Alın**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Veri İşleme İçin JSON Kullanın**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Performans İpuçları

```bash
# Yüksek hız
httpx -l hosts.txt -threads 100 -timeout 5

# Yavaş ve gizli (stealth)
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** httpx hedeflere doğrudan HTTP istekleri gönderir. Yalnızca izin verilen sistemlerde kullanın.
> 
> - ✅ Test izniniz olan programlarda kullanın
> - ✅ Kapsam dahilindeki (in-scope) hedeflerde çalışın
> - ✅ Kendi alan adlarınız üzerinde test yapın
> - ❌ İzniniz olmayan hedefleri asla taramayın
> - ❌ İstek oranı limitlerine (rate limit) saygı gösterin
> 
> **Yetkisiz ağ sondajı yasa dışı olabilir.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Dokümantasyon](https://docs.projectdiscovery.io/tools/httpx/)

### İlgili Başvuru Kılavuzları
- [Subfinder](../../Subfinder/README.md)
- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)

---

<p align="center">
  <b>🌐 Her Şeyi Sondajlayın!</b><br>
  <i>Bug bounty için vazgeçilmez HTTP araç seti</i>
</p>
