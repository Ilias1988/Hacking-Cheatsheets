# 🎯 Nuclei - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
   Hızlı Şablon Tabanlı Zafiyet Tarayıcısı
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Bug bounty araştırmacıları ve güvenlik uzmanları için 1 numaralı şablon tabanlı zafiyet tarayıcısı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Şablon Kategorileri](#-şablon-kategorileri)
- [Hedefleri Tarama](#-hedefleri-tarama)
- [Şablon Seçimi](#-şablon-seçimi)
- [Çıktı Seçenekleri](#-çıktı-seçenekleri)
- [Gelişmiş Özellikler](#-gelişmiş-özellikler)
- [Özel Şablon Yazma (Custom Templates)](#-özel-şablon-yazma-custom-templates)
- [Bug Bounty İş Akışı](#-bug-bounty-i̇ş-akışı)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Nuclei**, ProjectDiscovery tarafından geliştirilen, YAML tabanlı şablonlar (templates) kullanarak hedeflere karşı son derece hızlı ve yapılandırılabilir güvenlik taramaları gerçekleştiren modern bir zafiyet tarayıcısıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **9000+ Hazır Şablon** | Bilinen CVE'ler, açık paneller, yapılandırma hataları ve devralmalar |
| **Yüksek Hız ve Verimlilik** | Eşzamanlı iş parçacıkları ve istek hız sınırlaması desteği |
| **YAML Tabanlı Şablonlar** | Okunması, yazılması ve özelleştirilmesi son derece kolay şablon yapısı |
| **Topluluk Odaklı** | Her gün yeni çıkan 0-day ve 1-day zafiyetler için güncellenen şablon havuzu |
| **Çoklu Protokol Desteği** | HTTP, DNS, TCP, SSL/TLS, Headless tarayıcı ve Websocket testleri |
| **Boru Hattı (Pipeline) Entegrasyonu** | subfinder, httpx, katana gibi araçlarla kusursuz boru hattı uyumu |

### Şablon Türleri

| Tür | Açıklama |
|-----|----------|
| **CVE** | Bilinen ve yayınlanmış güvenlik açıkları |
| **Exposed Panels** | Açıkta unutulmuş admin ve yönetim panelleri |
| **Misconfigurations** | Hatalı güvenlik ve sunucu yapılandırmaları |
| **Default Logins** | Varsayılan fabrika çıkış kullanıcı adı ve parolaları |
| **Takeovers** | Sahipsiz DNS kayıtları üzerinden alt alan adı devralma (Subdomain Takeover) |
| **Technologies** | Hedefin kullandığı yazılım, kütüphane ve teknolojilerin tespiti |
| **Exposures** | Hassas dosya, anahtar ve kaynak kod ifşaları |

---

## 📥 Kurulum

### Go ile Kurulum (Önerilen)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### GitHub Sürümleri Üzerinden
```bash
# En son sürümü indirin:
# https://github.com/projectdiscovery/nuclei/releases

wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt update && sudo apt install nuclei
```

### Şablonları Güncelleme
```bash
# Hem Nuclei motorunu hem şablonları güncelle
nuclei -ut

# Yalnızca şablonları güncelle
nuclei -update-templates
```

### Kurulumu Doğrulama
```bash
nuclei -version
nuclei -tl              # Yüklü şablonları listele
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
nuclei [seçenekler] -target <url>
```

### Sık Kullanılan Temel Parametreler

| Seçenek | Açıklama |
|---------|----------|
| `-u` | Tek bir hedef URL |
| `-l` | Hedef URL'leri içeren dosya listesi |
| `-t` | Çalıştırılacak şablon veya şablon dizini |
| `-tags` | Belirli etiketlere sahip şablonları çalıştır (örn: `cve`, `rce`) |
| `-severity` | Zafiyet önem derecesine göre filtrele (`critical`, `high`, vb.) |
| `-o` | Sonuçları dosyaya kaydet |
| `-silent` | Yalnızca bulunan bulguları ekrana bas (sessiz mod) |
| `-v` | Ayrıntılı mod |

### Hızlı Başlangıç

```bash
# Tek bir hedefi tüm varsayılan şablonlarla tara
nuclei -u https://example.com

# Hedef listesini tara
nuclei -l urls.txt

# Yalnızca CVE şablonlarını çalıştır
nuclei -u https://example.com -t cves/

# Etiketlere göre tara (CVE ve RCE açıkları)
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Şablon Kategorileri

### CVE Şablonları
```bash
# Tüm CVE şablonlarını çalıştır
nuclei -u target.com -t cves/

# Belirli bir yılın CVE'lerini çalıştır
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Sadece kritik seviyedeki CVE'leri dene
nuclei -u target.com -t cves/ -severity critical

# Tek bir CVE şablonunu dene
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Açık Paneller (Exposed Panels)
```bash
# Tüm panel tespit şablonları
nuclei -u target.com -t exposed-panels/

# Popüler panelleri arama
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Yapılandırma Hataları (Misconfigurations)
```bash
# Tüm hatalı yapılandırma testleri
nuclei -u target.com -t misconfiguration/

# Belirli türler
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Varsayılan Giriş Bilgileri (Default Logins)
```bash
# Varsayılan kimlik bilgisi testleri
nuclei -u target.com -t default-logins/
nuclei -u target.com -tags tomcat-default
```

### Alt Alan Adı Devralma (Subdomain Takeovers)
```bash
# Tüm devralma kontrolleri
nuclei -u target.com -t takeovers/
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags github-takeover
```

### Hassas Veri İfşası (Exposures)
```bash
# Tüm ifşa testleri
nuclei -u target.com -t exposures/

# Belirli ifşalar
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags git
```

---

## 🎯 Hedefleri Tarama

### Boru Hattı (Pipeline) ile Entegrasyon

```bash
# subfinder ile alt alan adlarını bulup doğrudan nuclei'ye iletme
subfinder -d example.com | nuclei

# httpx ile önce ayakta olan canlı HTTP servislerini doğrula, sonra tara
subfinder -d example.com | httpx | nuclei

# katana ile taranan tüm linkleri nuclei'ye aktarma
katana -u https://example.com | nuclei

# Tam sessiz boru hattı (Sonuç odaklı)
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

## 🏷️ Şablon Seçimi

### Önem Derecesine Göre Filtreleme (-severity)

| Seviye | Öncelik |
|--------|---------|
| `critical` | Acil (Kritik RCE, kimlik doğrulama atlatma) |
| `high` | Yüksek (SQLi, SSRF, LFI) |
| `medium` | Orta (XSS, hassas bilgi ifşası) |
| `low` | Düşük (Güvensiz yapılandırmalar) |
| `info` | Bilgilendirici (Teknoloji tespiti) |

```bash
# Yalnızca kritik ve yüksek seviyeleri çalıştır
nuclei -u target.com -severity critical,high

# Bilgi ve düşük seviyeleri hariç tut
nuclei -u target.com -exclude-severity info,low
```

### Şablonları Hariç Tutma

```bash
# Belirli bir dizini hariç tut
nuclei -u target.com -exclude-templates cves/2020/

# Belirli etiketleri (örn: DoS testlerini) hariç tut
nuclei -u target.com -exclude-tags dos,fuzz
```

---

## 📤 Çıktı Seçenekleri

```bash
# Standart metin dosyasına kaydet
nuclei -u target.com -o results.txt

# JSON formatında kaydet
nuclei -u target.com -json -o results.json

# JSON Lines formatında kaydet
nuclei -u target.com -jsonl -o results.jsonl

# Markdown formatında rapor oluştur
nuclei -u target.com -markdown-export report/
```

---

## ⚡ Gelişmiş Özellikler

### Hız Sınırlaması ve Eşzamanlılık

```bash
# Saniyede maksimum 100 istek gönder
nuclei -u target.com -rate-limit 100

# Eşzamanlı istek iş parçacığı sayısı (-c)
nuclei -u target.com -c 50

# WAF arkasındaki hedefler için yavaş ve dikkatli tarama
nuclei -u target.com -rl 10 -c 5
```

### Proxy Desteği
```bash
# Burp Suite üzerinden yönlendirme
nuclei -u target.com -proxy http://127.0.0.1:8080

# Tor / SOCKS5 proxy kullanımı
nuclei -u target.com -proxy socks5://127.0.0.1:9050
```

### Kimlik Doğrulama Başlıkları
```bash
# Bearer token ile yetkili tarama
nuclei -u target.com -H "Authorization: Bearer token123"

# Çerez ekleme
nuclei -u target.com -H "Cookie: session=abc123"
```

### Headless (Görünmez Tarayıcı) Modu
```bash
# DOM tabanlı XSS ve dinamik sayfalar için gerçek tarayıcı motoru kullan
nuclei -u target.com -headless
```

---

## 📝 Özel Şablon Yazma (Custom Templates)

### Örnek Şablon Yapısı (YAML)

```yaml
id: admin-paneli-tespiti

info:
  name: Özel Yönetici Paneli Tespiti
  author: arastirmaci
  severity: info
  description: Yönetim paneli uç noktalarını denetler
  tags: panel,custom

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: word
        words:
          - "Admin Panel"
          - "Dashboard"
        part: body
      - type: status
        status:
          - 200
```

### Şablon Doğrulama

```bash
# Yazılan şablonun sözdizimini doğrula
nuclei -validate -t my-template.yaml

# Hata ayıklama modunda tek bir hedefte test et
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Bug Bounty İş Akışı

### Eksiksiz Keşif ve Zafiyet Avı Boru Hattı

```bash
# 1. Adım: Alt alan adlarını keşfet
subfinder -d target.com -o subs.txt

# 2. Adım: Canlı HTTP servislerini belirle
cat subs.txt | httpx -o live.txt

# 3. Adım: Kritik ve yüksek seviyeli açıkları tara
nuclei -l live.txt -severity critical,high -o findings.txt

# Tek Satırlık Akış:
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| Temel tarama | `nuclei -u target.com` |
| Şablon dizini belirtme | `nuclei -u target.com -t cves/` |
| Etiketlere göre | `nuclei -u target.com -tags cve,rce` |
| Önem derecesine göre | `nuclei -u target.com -severity critical` |
| Dosyadan liste tarama | `nuclei -l urls.txt` |
| Şablonları güncelleme | `nuclei -ut` |
| Sessiz çıktı | `nuclei -u target.com -silent` |
| JSON raporu | `nuclei -u target.com -json -o out.json` |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **Her Taramadan Önce Şablonları Güncelleyin**
   ```bash
   nuclei -ut
   ```
   *Topluluk tarafından her gün kritik CVE şablonları eklenmektedir.*

2. **Hızlı Başarılar İçin `takeover` ve `exposed-panels` Etiketlerini Deneyin**
   ```bash
   nuclei -l live.txt -tags takeover,exposed-panels
   ```

3. **Büyük Hedeflerde Hız Sınırını Belirleyin**
   - Sunucuları kilitlememek veya WAF tarafından engellenmemek için `-rate-limit 50` veya `-rate-limit 100` parametresini kullanın.

---

## ⚠️ Yasal Uyarı

> **UYARI:** Nuclei, hedef sistemlerde güvenlik açığı doğrulaması yapan aktif bir tarayıcıdır. Yalnızca **yazılı izin alınmış sistemlerde, izinli bug bounty programları kapsamında ve sahibi olduğunuz altyapılarda** kullanılmalıdır. Yetkisiz taramalar yasalara aykırıdır.

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [ProjectDiscovery Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Resmi Nuclei Şablon Deposu](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery Resmi Sitesi](https://projectdiscovery.io/)
- [Nuclei Dokümantasyonu](https://docs.projectdiscovery.io/tools/nuclei/)

### İlgili Kılavuzlar
- [Bug Bounty Metodolojisi](../../Bug-Bounty-Methodology/translations/README.tr.md)
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)

---

<p align="center">
  <b>🎯 Zafiyet Taramasında Ustalaşın!</b><br>
  <i>Bug bounty avcılarının en güçlü müttefiki</i>
</p>
