# ⚡ OWASP ZAP - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy - Ücretsiz Web Güvenlik Test Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 Dünyanın en çok kullanılan ücretsiz ve açık kaynaklı web uygulama güvenlik tarayıcısı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Arayüze Genel Bakış](#-arayüze-genel-bakış)
- [Proxy Yapılandırması](#-proxy-yapılandırması)
- [Manuel Testler (Intercept / Request Editor)](#-manuel-testler-intercept--request-editor)
- [Örümcek & Tarayıcı (Spider & Crawler)](#-örümcek--tarayıcı-spider--crawler)
- [Aktif Tarama (Active Scan)](#-aktif-tarama-active-scan)
- [Pasif Tarama (Passive Scan)](#-pasif-tarama-passive-scan)
- [Bağlamlar & Oturumlar (Contexts & Sessions)](#-bağlamlar--oturumlar-contexts--sessions)
- [Fuzzer (Bulanık Test)](#-fuzzer-bulanık-test)
- [API & Otomasyon](#-api--otomasyon)
- [Komut Satırı ve Docker](#-komut-satırı-ve-docker)
- [Eklentiler (Add-ons)](#-eklentiler-add-ons)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**OWASP ZAP** (Zed Attack Proxy), OWASP vakfı tarafından geliştirilen ve bakımı sürdürülen, web uygulamalarındaki güvenlik açıklarını tespit etmek için tasarlanmış tamamen ücretsiz ve açık kaynaklı bir sızma testi platformudur.

### Neden OWASP ZAP?

| Özellik | Açıklama |
|---------|----------|
| **Ücretsiz & Açık Kaynak** | Lisans maliyeti yoktur, küresel topluluk tarafından desteklenir |
| **Kullanıcı Dostu** | Yeni başlayanlardan kıdemli araştırmacılara kadar uygun arayüz |
| **Otomatik Zafiyet Taraması** | Spider (Klasik ve AJAX), Aktif ve Pasif tarama yetenekleri |
| **Manuel Testler** | Araya giren proxy (Intercept), istek düzenleyici, fuzzer |
| **Kapsamlı REST API** | CI/CD süreçleri ve otomatik güvenlik boru hatları (pipeline) için tam API |
| **Çoklu Platform** | Windows, Linux, macOS ve Docker üzerinde çalışır |
| **Genişletilebilir Pazar Yeri** | 100'den fazla eklenti (add-on) ile kolayca genişletilebilir |

### OWASP ZAP vs Burp Suite Karşılaştırması

| Özellik | OWASP ZAP | Burp Suite (Community / Pro) |
|---------|-----------|-------------------------------|
| **Fiyat** | Tamamen Ücretsiz | Community ücretsiz / Pro $449+/yıl |
| **Açık Kaynak** | ✅ Evet | ❌ Hayır |
| **Otomatik Aktif Tarama** | ✅ Ücretsiz ve Sınırsız | Yalnızca Pro sürümde |
| **Tam API Erişimi** | ✅ Ücretsiz | Yalnızca Pro sürümde |
| **Topluluk ve Geliştirici** | OWASP Vakfı | PortSwigger |

---

## 📥 Kurulum

### Windows
```bash
# Resmi siteden kurulum:
https://www.zaproxy.org/download/

# Chocolatey ile:
choco install owasp-zap
```

### Linux (Kali / Ubuntu)
```bash
# Kali Linux (önceden kuruludur)
zaproxy

# Ubuntu / Debian
sudo apt update && sudo apt install zaproxy

# Snap ile
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew ile
brew install --cask owasp-zap
```

### Docker ile Çalıştırma
```bash
# Kararlı imajı indirin
docker pull ghcr.io/zaproxy/zaproxy:stable

# ZAP'ı arka planda (daemon modunda) başlatın
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080
```

---

## 🖥️ Arayüze Genel Bakış

### Ana Paneller

| Panel | Açıklama |
|-------|----------|
| **Siteler Ağacı (Sites Tree)** | Ziyaret edilen tüm alan adlarının ve dizinlerin hiyerarşik görünümü |
| **Geçmiş (History)** | Proxy üzerinden geçen tüm HTTP istek ve yanıtlarının kronolojik listesi |
| **Uyarılar (Alerts)** | Tespit edilen güvenlik zafiyetleri ve bulgular |
| **Aktif Tarama (Active Scan)** | Sürmekte olan aktif tarama testlerinin durumu ve yüzdesi |
| **Örümcek (Spider)** | Uygulama üzerinde keşfedilen URL listesi |
| **Arama (Search)** | Tüm istek, yanıt ve başlıklarda arama yapma |
| **İstek / Yanıt Detayı** | Seçili HTTP paketinin ham başlık ve gövde görünümü |

### Çalışma Modları (Modes)

| Mod | Açıklama |
|-----|----------|
| **Safe (Güvenli)** | Hiçbir tehlikeli veya değiştirici istek gönderilmez |
| **Protected (Korumalı)** | Yalnızca kapsam (Scope/Context) içine alınan hedefler taranabilir |
| **Standard (Standart)** | Varsayılan çalışma modu; tam tarama yetkisi vardır |
| **ATTACK (Saldırı)** | Kapsamdaki yeni URL'ler keşfedildiği anda otomatik olarak agresif taranır |

---

## 🔌 Proxy Yapılandırması

### Varsayılan Dinleme Portu
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Firefox Proxy Ayarı
```
1. Ayarlar → Ağ Ayarları → Manuel vekil sunucu yapılandırması
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. "Bu vekil sunucuyu HTTPS için de kullan" kutucuğunu işaretleyin
(Alternatif olarak FoxyProxy eklentisini kullanabilirsiniz)
```

### ZAP Kök CA Sertifikasını Yükleme
```
1. Tarayıcınızdan http://zap adresine gidin
2. "Download ZAP Root CA Certificate" bağlantısına tıklayın
3. İndirilen sertifikayı tarayıcınıza içe aktarın:
   - Firefox: Ayarlar → Gizlilik ve Güvenlik → Sertifikaları Görüntüle → İçe Aktar (Web sitelerini tanımlamak için güven)
   - Chrome: Ayarlar → Güvenlik → Sertifikaları Yönet
```

---

## 🔧 Manuel Testler (Intercept / Request Editor)

### İstekleri Yakalama (Break / Intercept)

```
1. Araç çubuğundaki yeşil "Break" (🛑) düğmesine basın
2. Tarayıcıda bir sayfayı açın veya form gönderin
3. ZAP isteği durduracak ve düzenleme ekranında gösterecektir
4. Parametreleri veya başlıkları dilediğiniz gibi değiştirin
5. "Submit and Continue" düğmesine basarak isteği sunucuya iletin
```

### İstek Düzenleyici (Request Editor / Resend)

```
Geçmişteki herhangi bir isteğe sağ tıklayın → Open/Resend with Request Editor
- İstek metodunu (GET, POST, PUT) değiştirin
- Başlıkları ekleyin veya silin
- Gönderip yanıtı doğrudan inceleyin
```

### Kodlama / Çözme / Özetleme (Encode/Decode/Hash)
```
Tools → Encode/Decode/Hash (veya Ctrl+E)
- URL Encode / Decode
- Base64 Encode / Decode
- HTML Entity Encode / Decode
- MD5, SHA1, SHA256 Hash hesaplama
```

---

## 🕷️ Örümcek & Tarayıcı (Spider & Crawler)

### Standart Örümcek (Traditional Spider)

```
Hedefe sağ tık → Spider → Spider...
- Form alanlarını otomatik doldurup gönderir
- HTML ve robots.txt bağlantılarını takip eder
```

### AJAX Örümceği (Modern JS Uygulamaları İçin)

React, Vue, Angular ve yoğun JavaScript içeren Single Page Applications (SPA) için gerçek bir tarayıcı motoru (Headless Chrome/Firefox) çalıştırarak DOM olaylarını tetikler:

```
Hedefe sağ tık → AJAX Spider → AJAX Spider...
Tarayıcı: Firefox veya Chrome seçin
```

---

## ⚡ Aktif Tarama (Active Scan)

Aktif tarama, hedefe bilinen zafiyet test yüklerini (SQL Injection, XSS, Path Traversal, SSRF vb.) göndererek yanıtları analiz eder.

### Aktif Taramayı Başlatma

```
Hedefe sağ tık → Attack → Active Scan...
- Context seçimi
- Kullanıcı seçimi (Yetkili kullanıcı olarak taramak için)
- İlke (Scan Policy) belirleme
```

### Tarama İlkeleri (Scan Policies)

| Kategori | Testler |
|----------|---------|
| **Injection** | SQL Injection, OS Komut Enjeksiyonu, LDAP |
| **Client Browser** | XSS, DOM-based XSS, CSP zayıflıkları |
| **Server Security** | Dosya dahil etme (LFI/RFI), açık dizinler |
| **Information** | Bilgi ifşası, kaynak kod sızıntıları |
| **Miscellaneous** | Açık yönlendirme (Open Redirect), CORS |

---

## 👁️ Pasif Tarama (Passive Scan)

Pasif tarama, ZAP proxy'si üzerinden akan trafiği **hiçbir ek istek göndermeden** arka planda otomatik olarak inceler:
- Eksik güvenlik başlıkları (X-Frame-Options, Content-Security-Policy)
- Güvensiz çerezler (HttpOnly veya Secure bayrağı eksik olanlar)
- Yanıtlarda sızan özel IP adresleri, iç hata mesajları ve yığın izleri (stack traces)

---

## 🎯 Bağlamlar & Oturumlar (Contexts & Sessions)

Test edilen hedef uygulamayı organize etmek ve diğer sitelerden izole etmek için **Context** yapısı kullanılır.

### Context Ayarları

```
Sites panelinde hedefe sağ tık → Include in Context → New Context
- Include in Context: Taranacak URL kalıbı (örn: https://target.com.*)
- Authentication: Form-based, HTTP Basic veya JSON authentication
- Users: Test edilecek kullanıcı hesapları
```

---

## 🎲 Fuzzer (Bulanık Test)

İstek içerisindeki herhangi bir parametreyi otomatik olarak payload listesi ile denemek için kullanılır:

```
1. İstek üzerinde test edilecek değeri seçip fareyle vurgulayın
2. Sağ tık → Fuzz...
3. "Add..." düğmesine basıp Payload tipini belirleyin (FuzzDB, SecLists veya özel kelime listesi)
4. "Start Fuzzer" düğmesine tıklayın
5. Dönen yanıtları boyut ve durum koduna göre inceleyin
```

---

## 🤖 API & Otomasyon

### ZAP REST API Erişimi

```bash
# API anahtarı ile ZAP sürümünü sorgulama
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"

# Belirli bir URL için spider başlatma
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Bulunan zafiyet uyarılarını çekme
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# HTML raporu dışa aktarma
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Python ile ZAP Otomasyonu

```python
from zapv2 import ZAPv2

# ZAP proxy'sine bağlan
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Hedefi spider ile tara
print('Spider başlatılıyor...')
zap.spider.scan(url='http://target.com')

# Aktif tarama başlat
print('Aktif tarama başlatılıyor...')
zap.ascan.scan(url='http://target.com')

# Zafiyetleri listele
alerts = zap.core.alerts()
for alert in alerts:
    print(f"[{alert['risk']}] {alert['name']}")
```

---

## ⌨️ Komut Satırı ve Docker

### Otomatik Docker Taramaları

```bash
# Temel Pasif Güvenlik Taraması (Baseline Scan)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Tam Kapsamlı Aktif Tarama (Full Scan)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# REST API Taraması (OpenAPI / Swagger Tanımı ile)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 📊 Hızlı Başvuru

### Temel İşlemler Tablosu

| Görev | İşlem Yolu |
|-------|------------|
| Proxy'yi başlatma | ZAP açıldığında 8080 portunda otomatik başlar |
| Örümcek taraması | Hedefe sağ tık → Spider |
| Aktif zafiyet taraması | Hedefe sağ tık → Active Scan |
| İsteği durdurma (Break) | Üstteki yeşil Break (🛑) butonuna basın |
| Fuzzing | İsteğe sağ tık → Fuzz... |
| HTML raporu alma | Report → Generate Report |

### Klavye Kısayolları

| Kısayol | İşlem |
|---------|-------|
| `Ctrl+Alt+S` | Örümcek penceresi |
| `Ctrl+Alt+A` | Aktif Tarama penceresi |
| `Ctrl+M` | Manuel İstek Editörü |
| `Ctrl+B` | Break (Yakala/Bırak) geçişi |
| `Ctrl+R` | İsteği Yeniden Gönder |

---

## 💡 İpuçları ve En İyi Uygulamalar

1. **Önce Kapsamı (Scope / Context) Tanımlayın**
   - Üçüncü taraf kaynakların veya CDN'lerin gereksiz yere taranmasını önleyin.

2. **Modern Web Sitelerinde AJAX Spider Kullanın**
   - Tek sayfa uygulamalarında (SPA) klasik spider tüm yolları keşfedemez.

3. **Otomatize Taramadan Önce Elle Gezinin**
   - Uygulamanın tüm özelliklerini tarayıcı ile gezerek ZAP geçmişini besleyin, ardından taramayı başlatın.

---

## ⚠️ Yasal Uyarı

> **UYARI:** OWASP ZAP, hedef sunuculara çok sayıda saldırı yükü gönderen aktif bir güvenlik aracıdır. Yalnızca **yazılı izin alınmış sistemlerde ve yetkili olduğunuz test ortamlarında** kullanılmalıdır. Yetkisiz zafiyet taramaları suç teşkil edebilir.

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [OWASP ZAP Resmi Web Sitesi](https://www.zaproxy.org/)
- [ZAP Dokümantasyonu](https://www.zaproxy.org/docs/)
- [ZAP GitHub Deposu](https://github.com/zaproxy/zaproxy)
- [ZAP API Dokümantasyonu](https://www.zaproxy.org/docs/api/)

### İlgili Kılavuzlar
- [Burp Suite](../../Burp-Suite/translations/README.tr.md)
- [Nikto](../../Nikto/translations/README.tr.md)
- [SQLMap](../../SQLMap/translations/README.tr.md)

---

<p align="center">
  <b>⚡ Web Uygulama Güvenlik Testlerinde Uzmanlaşın!</b><br>
  <i>Topluluk destekli, özgür ve güçlü güvenlik tarayıcısı</i>
</p>
