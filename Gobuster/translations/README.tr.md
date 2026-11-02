# 🚀 Gobuster - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
              Dizin / DNS / Sanal Sunucu (VHost) Kaba Kuvvet Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Go dili ile yazılmış yüksek hızlı dizin, DNS ve VHost keşif aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Çalışma Modlarına Genel Bakış](#-çalışma-modlarına-genel-bakış)
- [Dizin Modu (dir)](#-dizin-modu-dir)
- [DNS Modu (dns)](#-dns-modu-dns)
- [Sanal Sunucu Modu (vhost)](#-sanal-sunucu-modu-vhost)
- [Fuzzing Modu (fuzz)](#-fuzzing-modu-fuzz)
- [Kelime Listeleri (Wordlists)](#-kelime-listeleri-wordlists)
- [Kimlik Doğrulama & Başlıklar (Headers)](#-kimlik-doğrulama--başlıklar-headers)
- [Performans Optimizasyonu](#-performans-optimizasyonu)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Gobuster**, Go programlama dili ile yazılmış, son derece hızlı ve çok iş parçacıklı (multi-threaded) bir kaba kuvvet (brute-force) tarayıcısıdır:
- Web sunucularında gizli dizin ve dosyaları keşfetme
- DNS alt alan adlarını (subdomains) tespit etme
- Sanal sunucu (Virtual Host - VHost) keşfi
- URL ve parametre fuzzing işlemleri

### Neden Gobuster?

| Özellik | Açıklama |
|---------|----------|
| **Yüksek Hız** | Go dili sayesinde minimum kaynak tüketimi ve yüksek eşzamanlılık |
| **Esneklik** | Farklı saldırı senaryoları için özelleşmiş modlar |
| **Özyinelemesiz (No Recursion)** | Gereksiz gürültü yaratmadan doğrudan istenen derinliği tarama |
| **Kararlılık** | Ağ hatalarını ve zaman aşımlarını zarifçe yönetir |
| **Aktif Geliştirme** | Topluluk tarafından düzenli güncellenir |

### Diğer Araçlarla Karşılaştırma

| Araç | Hız | Özyineleme (Recursion) | Dil |
|------|-----|-------------------------|-----|
| **Gobuster** | ⭐⭐⭐⭐⭐ | Hayır | Go |
| Dirb | ⭐⭐ | Evet | C |
| Dirbuster | ⭐⭐⭐ | Evet | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Hayır | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Evet | Rust |

---

## 📥 Kurulum

### Kali Linux (Önceden Kuruludur)
```bash
sudo apt update && sudo apt install gobuster
```

### Ubuntu / Debian
```bash
sudo apt install gobuster
```

### Kaynak Koddan Derleme (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Snap ile Kurulum
```bash
sudo snap install gobuster
```

### GitHub Sürümleri Üzerinden
```bash
# En son derlenmiş paketi indirin:
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Kurulumu Doğrulama
```bash
gobuster version
gobuster -h
```

---

## 📊 Çalışma Modlarına Genel Bakış

### Kullanılabilir Modlar

| Mod | Açıklama | Tipik Kullanım Alanı |
|-----|----------|----------------------|
| `dir` | Dizin / dosya keşfi | Gizli web dizinlerini ve kaynaklarını bulma |
| `dns` | DNS alt alan adı keşfi | Subdomain tespiti |
| `vhost` | Virtual host keşfi | Aynı IP üzerindeki sanal konakları bulma |
| `fuzz` | URL fuzzing | Parametre ve özel yolları deneme |
| `s3` | S3 depolama kovaları | Açık AWS S3 kovalarını tespit etme |
| `gcs` | Google Cloud Storage | GCS depolama kovalarını tespit etme |
| `tftp` | TFTP keşfi | TFTP sunucularındaki dosyaları arama |

### Temel Sözdizimi
```bash
gobuster [mod] [seçenekler]

# Örnekler:
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

## 📁 Dizin Modu (dir)

### Temel Sözdizimi
```bash
gobuster dir -u <URL> -w <kelime_listesi>
```

### Temel Seçenekler

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Hedef URL |
| `-w` | Kelime listesi dosya yolu |
| `-t` | Eşzamanlı iş parçacığı sayısı (varsayılan: 10) |
| `-x` | Aranacak dosya uzantıları |
| `-s` | Başarılı kabul edilecek HTTP durum kodları |
| `-b` | Yok sayılacak (kara liste) durum kodları |
| `-o` | Sonuçların kaydedileceği dosya |
| `-k` | SSL sertifika doğrulamasını atla |
| `-r` | Yönlendirmeleri (redirects) takip et |
| `-a` | Özel User-Agent dizesi |
| `-c` | İsteklerle gönderilecek çerezler (cookies) |
| `-H` | Özel HTTP başlıkları (headers) |

### Temel Dizin Taraması
```bash
# Standart dizin taraması
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Yüksek iş parçacığı sayısı ile
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Dosya Uzantısı Arama (-x)
```bash
# Belirli uzantıları arama
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Kapsamlı web uzantıları
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Yedek ve geçici dosyalar
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Durum Kodu Filtreleme (-s ve -b)
```bash
# Yalnızca belirli durum kodlarını listele
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Belirli durum kodlarını hariç tut
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Tüm yanıtları listele (404 dahil)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Çıktı ve Gösterim Seçenekleri
```bash
# Sonuçları dosyaya kaydetme
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Ayrıntılı (verbose) mod
gobuster dir -u http://target.com -w wordlist.txt -v

# İlerleme çubuğunu gizleme (sessiz mod)
gobuster dir -u http://target.com -w wordlist.txt -q

# Genişletilmiş URL gösterimi (tam URL listeleme)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### SSL / TLS Seçenekleri
```bash
# Geçersiz SSL sertifikalarını yok sayma (-k)
gobuster dir -u https://target.com -w wordlist.txt -k
```

### Dizin Modu Örnekleri
```bash
# Standart web dizin keşfi
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# PHP uygulaması taraması
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# API uç noktası keşfi
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Yedek ve sıkıştırılmış dosya avı
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

## 🌐 DNS Modu (dns)

### Temel Sözdizimi
```bash
gobuster dns -d <alan_adı> -w <kelime_listesi>
```

### DNS Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-d` | Hedef alan adı (domain) |
| `-w` | Subdomain kelime listesi |
| `-t` | Eşzamanlı iş parçacığı sayısı |
| `-r` | Özel DNS sunucusu (resolver) |
| `-i` | Çözümlenen IP adreslerini göster |
| `-c` | CNAME kayıtlarını göster |
| `--wildcard` | Joker (wildcard) DNS kayıtlarını ele al |
| `--timeout` | DNS sorgu zaman aşımı süresi |

### Temel Alt Alan Adı Keşfi
```bash
# Standart subdomain taraması
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# IP adreslerini göstererek
gobuster dns -d target.com -w subdomains.txt -i

# CNAME kayıtlarını göstererek
gobuster dns -d target.com -w subdomains.txt -c
```

### Özel DNS Sunucusu Belirleme
```bash
# Belirli bir DNS sunucusunu kullan
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Port belirterek
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

---

## 🏠 Sanal Sunucu Modu (vhost)

### Temel Sözdizimi
```bash
gobuster vhost -u <URL> -w <kelime_listesi>
```

### VHost Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Hedef URL veya IP |
| `-w` | Kelime listesi yolu |
| `-t` | İş parçacığı sayısı |
| `--append-domain` | Kelime listesi girdilerinin ardına ana alan adını ekler |
| `--domain` | Hedef alan adı |
| `-r` | Yönlendirmeleri takip et |

### VHost Keşfi Örnekleri
```bash
# Standart VHost keşfi
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Alan adını otomatik ekleyerek (subdomain.target.com şeklinde dener)
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# IP adresi üzerinden etki alanı belirterek (CTF / HackTheBox senaryoları)
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb --append-domain
```

---

## 🔧 Fuzzing Modu (fuzz)

### Temel Sözdizimi
```bash
gobuster fuzz -u <FUZZ_içeren_URL> -w <kelime_listesi>
```

### Fuzz Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-u` | `FUZZ` anahtar kelimesini içeren hedef URL |
| `-w` | Kelime listesi yolu |
| `-t` | İş parçacığı sayısı |
| `-b` | Kara liste durum kodları |
| `--exclude-length` | Belirtilen yanıt uzunluklarını filtrele |

### Fuzzing Örnekleri
```bash
# Parametre değerini fuzz etme
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Dizin yolunu fuzz etme
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Belirli yanıt boyutlarını filtreleme
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

## 📚 Kelime Listeleri (Wordlists)

### Kali Linux SecLists Konumları

```bash
# Web içerik keşfi listeleri
/usr/share/seclists/Discovery/Web-Content/

# En popüler dizin listeleri:
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS ve Subdomain listeleri:
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Önerilen Liste Eşleşmeleri

| Senaryo | Önerilen Kelime Listesi |
|---------|-------------------------|
| Hızlı Tarama | `common.txt` |
| Standart Tarama | `directory-list-2.3-medium.txt` |
| Derinlemesine Tarama | `directory-list-2.3-big.txt` |
| API Uç Noktaları | `api-endpoints.txt` |
| Alt Alan Adları | `subdomains-top1million-5000.txt` |
| Dosya Arama | `raft-large-files.txt` |

---

## 🔐 Kimlik Doğrulama & Başlıklar (Headers)

### Basic Authentication
```bash
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Çerez (Cookie) Kullanımı
```bash
# Tek çerez ile
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Birden çok çerez ile
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# PHP oturum kimliği ile
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Özel HTTP Başlıkları (-H)
```bash
# Yetkilendirme başlığı (Bearer token)
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# IP yanıltma veya dahili ağ simülasyonu
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# API anahtarı ile
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent Belirleme
```bash
# Özel tarayıcı User-Agent'ı
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Arama motoru botu gibi görünme
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Proxy Kullanımı
```bash
# HTTP Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# SOCKS5 / Tor Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Burp Suite üzerinden yönlendirme (SSL sertifikasını yok sayarak)
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

## ⚡ Performans Optimizasyonu

### İş Parçacığı Yönetimi (-t)
```bash
# Varsayılan: 10 iş parçacığı
gobuster dir -u http://target.com -w wordlist.txt

# Yüksek hızlı ağlar için artırma
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Zayıf veya hassas hedefler için düşürme
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Zaman Aşımı ve Gecikme Ayarları
```bash
# İstek zaman aşımı süresini artırma
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# İstekler arasına gecikme koyma (Hız sınırlaması / Rate-limit atlatma)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# WAF tespitinden kaçınmak için daha yavaş tarama
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Standart Web Dizin Keşfi
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Örnek 2: REST API Uç Noktası Keşfi
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Örnek 3: Oturum Açılmış Yönetici Alanı Taraması
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Örnek 4: Alt Alan Adı Keşfi
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Örnek 5: Sanal Konak Keşfi (HackTheBox / CTF)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Örnek 6: Burp Suite Proxy Üzerinden İnceleme
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Örnek 7: Kritik Yedek Dosyalarını Arama
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| Dizin taraması | `gobuster dir -u URL -w wordlist.txt` |
| Uzantılar ile dizin | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Subdomain taraması | `gobuster dns -d domain.com -w wordlist.txt` |
| VHost taraması | `gobuster vhost -u URL -w wordlist.txt` |
| Sonuçları kaydetme | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| SSL hatalarını yoksay | `gobuster dir -u URL -w wordlist.txt -k` |
| Çerez belirterek | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Proxy kullanarak | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Sık Kullanılan Seçenekler

| Uzun Parametre | Kısa Parametre | Açıklama |
|----------------|----------------|----------|
| `--url` | `-u` | Hedef URL |
| `--wordlist` | `-w` | Kelime listesi dosyası |
| `--threads` | `-t` | İş parçacığı sayısı |
| `--extensions` | `-x` | Dosya uzantıları |
| `--status-codes` | `-s` | İstenen HTTP durum kodları |
| `--cookies` | `-c` | Gönderilecek HTTP çerezleri |
| `--output` | `-o` | Çıktı dosyası |
| `--insecuressl` | `-k` | SSL doğrulamasını atla |
| `--verbose` | `-v` | Ayrıntılı mod |
| `--quiet` | `-q` | Sessiz mod |

---

## 💡 İpuçları ve En İyi Uygulamalar

### Verimlilik İpuçları

1. **Önce Küçük Liste, Sonra Büyük Liste**
   - Hızlı bir genel bakış için `common.txt`, ardından kapsamlı analiz için `directory-list-2.3-medium.txt` kullanın.

2. **Hedefe Özgü Uzantılar Belirleyin**
   - Hedef PHP ise `-x php`, IIS/ASP.NET ise `-x asp,aspx` ekleyin.

3. **Nmap ile Zincirleyin**
   - Önce `nmap -sV -p 80,443 target.com` ile web servislerini tespit edin, ardından Gobuster'ı başlatın.

### Tespit Mekanizmalarından Kaçınma

```bash
# Gecikmeli ve düşük iş parçacıklı tarama
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Gerçekçi User-Agent kullanma
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Gobuster, yalnızca **yetkili ve izinli sızma testlerinde** kullanılması gereken güçlü bir keşif aracıdır.
> 
> - ✅ Sahibi olduğunuz veya yazılı izin aldığınız sistemlerde test edin
> - ✅ CTF müsabakaları ve laboratuvar ortamlarında pratik yapın
> - ❌ İzniniz olmayan hiçbir hedefte kaba kuvvet taraması gerçekleştirmeyin
> 
> **Yetkisiz ağ taramaları ve keşif girişimleri suç teşkil edebilir.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Kelime Listeleri
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### İlgili Kılavuzlar
- [Nmap](../../Nmap/translations/README.tr.md)
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)
- [SQLMap](../../SQLMap/translations/README.tr.md)

---

<p align="center">
  <b>🚀 Web Keşif Tekniklerinde Ustalaşın!</b><br>
  <i>Başkalarının göremediği gizli yolları keşfedin</i>
</p>
