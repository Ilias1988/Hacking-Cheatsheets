# 🎭 Sosyal Mühendislik Kopya Kağıdı (Social Engineering Cheatsheets)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Sosyal Mühendislik Nedir?

Sosyal mühendislik; insanları gizli bilgileri ifşa etmeleri veya bilgi güvenliğini tehlikeye atacak eylemlerde bulunmaları için manipüle etme sanatıdır. Teknik güvenlik açıklarından ziyade insan psikolojisindeki zaafları istismar eder.

**Temel İlkeler:**
- 🎭 **Otorite (Authority)** - İnsanlar yetkili figürlere güvenme eğilimindedir
- ⏰ **Aciliyet (Urgency)** - Zaman baskısı eleştirel düşünmeyi azaltır
- 🤝 **Karşılıklılık (Reciprocity)** - İnsanlar kendilerine yapılan bir iyiliğe karşılık verme ihtiyacı hisseder
- 👥 **Sosyal Kanıt (Social Proof)** - İnsanlar çoğunluğun davranışını takip eder
- 😊 **Sempatiklik (Liking)** - İnsanlar sevdikleri veya benimsedikleri kişilerin taleplerini yerine getirir
- 📉 **Kıtlık (Scarcity)** - Sınırlı erişilebilirlik bir şeye duyulan isteği artırır

---

## 📖 Sosyal Mühendislik Kılavuzları

| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Oltalama (Phishing)** | E-posta, SMS ve sesli oltalama teknikleri ve araçları | [📄 Görüntüle](./Phishing.tr.md) |
| **Senaryo Kurma (Pretexting)** | İnandırıcı senaryolar ve kimlikler oluşturma | [📄 Görüntüle](./Pretexting.tr.md) |

---

## 🛠️ Temel Sosyal Mühendislik Araçları

| Araç | Amacı | Türü |
|------|-------|------|
| **GoPhish** | Oltalama kampanyası çatısı | Phishing |
| **SET (Social Engineering Toolkit)** | Kapsamlı sosyal mühendislik çatısı | Çok Amaçlı |
| **Evilginx2** | Ortadaki adam (MITM) oltalaması (2FA atlatma) | Phishing |
| **King Phisher** | Oltalama kampanya platformu | Phishing |
| **Modlishka** | Ters vekil (reverse proxy) oltalaması | Phishing |
| **theHarvester** | E-posta ve alan adı toplama | Keşif (Recon) |
| **LinkedIn Araçları** | Çalışan ve organizasyon numaralandırması | OSINT |
| **Maltego** | OSINT ve ilişki haritalama | Keşif (Recon) |

### Hızlı Araç Kurulumu
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Sosyal Mühendislik Saldırı Türleri

| Saldırı Türü | Açıklama | Vektör |
|--------------|----------|--------|
| **Oltalama (Phishing)** | Kimlik bilgilerini çalmak için yanıltıcı e-postalar | E-posta |
| **Hedefli Oltalama (Spear Phishing)** | Belirli bireylere veya kurumlara yönelik özelleştirilmiş oltalama | E-posta |
| **Balina Avı (Whaling)** | Üst düzey yöneticileri (C-level) hedef alan oltalama | E-posta |
| **Sesli Oltalama (Vishing)** | Sesli arama veya telefon tabanlı sosyal mühendislik | Telefon |
| **SMS Oltalaması (Smishing)** | SMS tabanlı oltalama mesajları | SMS |
| **Senaryo Kurma (Pretexting)** | Bilgi çekmek için kurgusal hikayeler oluşturma | Herhangi biri |
| **Yemleme (Baiting)** | Virüslü medya (USB vb.) bırakarak kurbanı çekme | Fiziksel |
| **Karşılıklılık (Quid Pro Quo)** | Bilgi karşılığında bir hizmet veya avantaj sunma | Herhangi biri |
| **Kuyruk Takibi (Tailgating)** | Yetkili bir kişinin arkasından güvenli alana sızma | Fiziksel |
| **Su Başı Saldırısı (Watering Hole)** | Hedeflerin sık ziyaret ettiği web sitelerini ele geçirme | Web |

---

## 🔗 Hızlı Başvuru: Oltalama Göstergeleri

### E-postalardaki Şüpheli Belirtiler
```
□ Gönderen e-posta adresinin şirket alan adıyla uyuşmaması
□ Genel hitaplar kullanılması ("Değerli Müşterimiz")
□ Yapay aciliyet hissi veya tehdit içeren ifadeler
□ Yazım ve dilbilgisi hataları
□ Şüpheli bağlantılar (üzerine gelerek hedefi kontrol edin)
□ Beklenmedik dosya ekleri (.exe, .scr, makrolu ofis belgeleri)
□ Hassas kimlik bilgilerinin talep edilmesi
□ Gerçek olamayacak kadar iyi teklifler
```

### URL Analizi
```bash
# Alan adı yaşını kontrol etme
whois suspicious-domain.com

# İtibar kontrolü
https://www.virustotal.com
https://urlscan.io

# Kısaltılmış bağlantıları açma ve analiz etme
https://checkshorturl.com
```

---

## 📚 Kaynaklar

- [OWASP Social Engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK - Initial Access](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Sosyal Mühendislik Testlerini Yalnızca Yetkili ve İzinli Kapsamlarda Gerçekleştirin!</b>
</p>
