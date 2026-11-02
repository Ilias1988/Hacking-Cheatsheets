[🇬🇧 English](../Email-OSINT.md)

# 📧 E-posta OSINT Başvuru Kılavuzu

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 E-posta Keşif Araçları

| Araç | URL | Amaç |
|------|-----|------|
| **Hunter.io** | hunter.io | Kurumsal e-postaları bulma |
| **Phonebook.cz** | phonebook.cz | Ücretsiz e-posta ve alan adı araması |
| **Snov.io** | snov.io | E-posta bulucu ve doğrulayıcı |
| **Clearbit Connect** | clearbit.com | E-posta zenginleştirme |
| **RocketReach** | rocketreach.co | İletişim bilgisi bulucu |
| **Voila Norbert** | voilanorbert.com | E-posta sorgulama |

---

## 🛠️ E-posta Numaralandırma Komutları

### theHarvester
```bash
# E-posta toplama
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io CLI
```bash
# API sorgusu
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# E-posta doğrulama
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (Veri Sızıntısı Araması)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (Platform Kayıt Kontrolü)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 E-posta Biçim Kalıpları (Format Patterns)

```bash
# Yaygın kurumsal e-posta desenleri
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### E-posta Permütasyon Scripti
```python
# Olası e-posta varyasyonlarını üretme
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Veri İhlali (Breach) Kontrolü

| Servis | URL | Tür |
|--------|-----|-----|
| **Have I Been Pwned** | haveibeenpwned.com | Ücretsiz |
| **DeHashed** | dehashed.com | Ücretli |
| **LeakCheck** | leakcheck.io | Ücretli |
| **Snusbase** | snusbase.com | Ücretli |
| **IntelX** | intelx.io | Freemium |

### HIBP API Kullanımı
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ E-posta Doğrulama

### Doğrulama Yöntemleri
```
1. MX Kaydı Kontrolü - Alan adının geçerli bir posta sunucusu var mı?
2. SMTP Doğrulaması - Posta kutusu gerçekten mevcut mu?
3. Sözdizimi Kontrolü - Geçerli bir e-posta formatında mı?
4. Geçici E-posta Kontrolü - Tek kullanımlık geçici servis mi?
5. Rol Hesabı Kontrolü - Genel rol hesabı mı (info@, admin@ vb.)?
```

### Basit SMTP Doğrulama
```bash
# MX kayıtlarını sorgulama
nslookup -type=mx target.com
dig mx target.com

# Telnet ile doğrudan sunucu kontrolü
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = posta kutusu mevcut, 550 = mevcut değil
```

---

## 📋 E-posta OSINT Kontrol Listesi

```markdown
## Hedef E-posta: [email@domain.com]

□ E-postanın varlığını doğrula (SMTP/Hunter)
□ Veri ihlallerini kontrol et (HIBP)
□ İlişkili platform hesaplarını bul (holehe)
□ Google ve Bing üzerinde arama yap
□ Sosyal medya kayıtlarını kontrol et
□ Herkese açık profilleri incele
□ Paste sitelerini araştır
□ Profesyonel ağlarda ara
```

---

**OSINT Ana Sayfasına Dön:** [🔍 OSINT Genel Bakış](./README.tr.md)
