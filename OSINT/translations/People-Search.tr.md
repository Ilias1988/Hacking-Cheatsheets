[🇬🇧 English](../People-Search.md)

# 👤 Kişi Arama OSINT (People Search OSINT)

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 Kişi Arama Motorları

### Ücretsiz Servisler
| Araç | URL | Kapsam |
|------|-----|--------|
| **ThatsThem** | thatsthem.com | ABD |
| **FastPeopleSearch** | fastpeoplesearch.com | ABD |
| **TruePeopleSearch** | truepeoplesearch.com | ABD |
| **Webmii** | webmii.com | Küresel |
| **PeekYou** | peekyou.com | ABD |
| **Pipl** | pipl.com | Küresel |

### Ücretli Servisler
| Araç | URL | Özellikler |
|------|-----|------------|
| **Spokeo** | spokeo.com | Derin arama, sosyal medya |
| **BeenVerified** | beenverified.com | Geçmiş ve sicil kontrolleri |
| **Intelius** | intelius.com | Adli sicil kayıtları |
| **US Search** | ussearch.com | Mahkeme kayıtları |
| **Radaris** | radaris.com | Mülk ve tapu kayıtları |

---

## 🔎 Kişi Tespiti İçin Google Dorking

### Temel Aramalar
```
# İsim araması
"Ahmet Yılmaz" site:linkedin.com
"Ahmet Yılmaz" site:facebook.com
"Ahmet Yılmaz" site:twitter.com

# İsim + Şehir / Konum
"Ahmet Yılmaz" "İstanbul"
"Ahmet Yılmaz" "Ankara" OR "İzmir"

# İsim + Şirket / Kurum
"Ahmet Yılmaz" "ASELSAN"
"Ahmet Yılmaz" site:sirketadi.com

# İsim + E-posta
"Ahmet Yılmaz" "@gmail.com"
"Ahmet Yılmaz" e-posta
```

### Mesleki ve Profesyonel Bilgiler
```
# Özgeçmiş / CV araması
"Ahmet Yılmaz" filetype:pdf resume OR cv
"Ahmet Yılmaz" filetype:doc özgeçmiş
intitle:"curriculum vitae" "Ahmet Yılmaz"

# LinkedIn aramaları
site:linkedin.com/in "Ahmet Yılmaz"
site:linkedin.com "Ahmet Yılmaz" "Yazılım Mühendisi"

# Profesyonel profil dizinleri
site:about.me "Ahmet Yılmaz"
site:crunchbase.com "Ahmet Yılmaz"
```

### Sosyal Medya Keşfi
```
# Tüm sosyal ağ profillerini bulma
"Ahmet Yılmaz" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Belirli kullanıcı adı (username) araması
"ahmetyilmaz123" site:twitter.com
"ahmetyilmaz123" site:reddit.com
"ahmetyilmaz123" site:github.com
```

---

## 🛠️ Komut Satırı Araçları

### Sherlock (Kullanıcı Adı Taraması)
```bash
# Kurulum
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Platformlar genelinde kullanıcı adı arama
python3 sherlock username

# Birden fazla kullanıcı adı
python3 sherlock user1 user2 user3

# Yalnızca belirli platformlarda arama
python3 sherlock username --site twitter instagram facebook

# Sonuçları dışa aktarma
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (Gelişmiş Kullanıcı Adı Taraması)
```bash
# Kurulum
pip3 install maigret

# Tarama
maigret username

# Tor ağı üzerinden arama
maigret username --tor

# JSON formatında çıktı
maigret username --json results.json
```

### holehe (E-posta Platform Kaydı Tespiti)
```bash
# Kurulum
pip3 install holehe

# E-postanın hangi servislerde kayıtlı olduğunu kontrol etme
holehe email@example.com

# Yalnızca kayıtlı olan platformları listele
holehe email@example.com --only-used
```

---

## 📧 E-postadan Kişiye Ulaşma

### E-postadan Kimlik Tespiti
```
1. Google'da tam eşleşme arayın: "email@example.com"
2. Veri sızıntılarını kontrol edin: haveibeenpwned.com
3. Sızıntı veritabanlarında arama yapın
4. Sosyal medya kayıtlarını kontrol edin (holehe)
5. Ters e-posta sorgulama araçlarını kullanın
```

### E-posta Formatı Tespiti
```bash
# Yaygın e-posta desenleri
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Hunter.io e-posta desen bulucu
https://hunter.io/email-finder
```

---

## 📱 Telefon Numarası OSINT

### Telefon Sorgulama Servisleri
| Servis | URL | Tür |
|--------|-----|-----|
| **TrueCaller** | truecaller.com | Arayan kimliği |
| **Sync.me** | sync.me | Arayan kimliği |
| **NumLookup** | numlookup.com | Ücretsiz sorgu |
| **SpyDialer** | spydialer.com | Ters arama |
| **CallerID Test** | calleridtest.com | Operatör bilgisi |

### Telefon Numarası İçin Google Dork'ları
```
# Temel aramalar
"0555-123-4567"
"+90 555 123 4567"
"05551234567"

# İlişkili hesapları ve kayıtları bulma
"0555-123-4567" site:facebook.com
"0555-123-4567" "@"
"0555-123-4567" "email"
```

### Telefon Numarası İçin OSINT Araçları
```bash
# phoneinfoga kurulumu ve tarama
pip3 install phoneinfoga
phoneinfoga scan -n "+905551234567"
phoneinfoga serve -p 8080  # Web arayüzü

# API kullanarak doğrulama
curl "https://api.numlookupapi.com/v1/validate/905551234567?apikey=YOUR_KEY"
```

---

## 🏠 Adres ve Mülk Kayıtları

### Mülk ve Adres Arama
| Servis | URL | Kapsam |
|--------|-----|--------|
| **Zillow** | zillow.com | ABD |
| **Redfin** | redfin.com | ABD |
| **Tapu ve Kadastro Portalları** | [ülkeye göre değişir] | Yerel kayıtlar |
| **Whitepages** | whitepages.com | ABD |

### Kamu Kayıtları
```
# Mahkeme kayıtları
Ulusal adalet portalları, e-Devlet kayıtları, federal mahkemeler

# Seçmen kayıtları
Seçmen listeleri ve veritabanları (yasal çerçeveye göre)

# Ticaret sicil kayıtları
Ticaret Sicil Gazetesi, Oda kayıtları (TOBB / MERSİS vb.)
```

---

## 👥 Sosyal Ağ Analizi

### LinkedIn OSINT
```
# Profil araması
site:linkedin.com/in "Ahmet Yılmaz"
site:linkedin.com/in "Ahmet Yılmaz" "Şirket Adı"

# Bağlantıları inceleme
"Bunu Görüntüleyenler Şunları da Görüntüledi" alanını kontrol edin
Ortak bağlantıları listeleyin
Şirket çalışanları listesini inceleyin
```

### Facebook OSINT
```
# Profil arama URL'leri
facebook.com/search/people/?q=ahmet%20yilmaz
facebook.com/search/pages/?q=ahmet%20yilmaz
```

### Sosyal Ağ Analiz Araçları
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter/X takipçi analizi)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Fotoğraf ve Yüz Arama

### Tersine Görsel Arama
| Servis | URL | En İyi Alanı |
|--------|-----|--------------|
| **Google Images** | images.google.com | Genel aramalar |
| **TinEye** | tineye.com | Birebir eşleşmeler |
| **Yandex** | yandex.com/images | Yüz tespiti için en iyisi |
| **Bing Images** | bing.com/images | Alternatif genel arama |
| **PimEyes** | pimeyes.com | Biyometrik yüz tanıma |

### Yüz Arama Metodolojisi
```
1. Yandex Görsel Arama - İnternetteki yüz benzerliklerini en iyi yakalayan ücretsiz motordur
2. PimEyes - Gelişmiş yapay zeka destekli yüz tanıma motoru
3. Social Catfish - Sosyal medya profillerinde görsel arama
```

---

## 📋 Kişi Arama Kontrol Listesi

```markdown
## Hedef: [Tam Adı Soyadı]

### Temel Bilgiler
□ Resmi adı ve soyadı varyasyonları
□ Doğum tarihi / Yaş
□ Güncel ve geçmiş adresler
□ Telefon numaraları
□ E-posta adresleri

### Çevrim İçi Varlık
□ LinkedIn profili
□ Facebook profili
□ Twitter/X hesabı
□ Instagram hesabı
□ TikTok hesabı
□ Diğer sosyal platformlar
□ Kişisel web sitesi / blog

### Mesleki Bilgiler
□ Güncel çalıştığı şirket/kurum
□ Unvanı ve görevi
□ Çalışma geçmişi
□ Eğitim ve sertifikalar
□ Mesleki lisanslar
□ Yayınlar ve patentler

### Resmi Kayıtlar
□ Şirket ve ortaklık kayıtları
□ Ticaret sicili
□ Açık mahkeme ve bülten kayıtları
□ Dernek ve oda üyelikleri

### Bağlantılar
□ Aile üyeleri
□ İş arkadaşları ve ortakları
□ Sosyal çevre bağlantıları
```

---

## ⚠️ Yasal ve Etik Hususlar

```
✓ Yalnızca herkese açık (publicly available) bilgileri kullanın
✓ Kişisel verilerin korunması kanunlarına (KVKK, GDPR, CCPA) titizlikle uyun
✓ Asla kimlik hırsızlığı veya aldatma (impersonation) yapmayın
✓ Metodolojinizi ve kaynaklarınızı eksiksiz belgeleyin
✓ Yalnızca meşru ve yasal amaçlar doğrultusunda araştırma yapın
✗ Taciz, izinsiz takip (stalking) veya tehdit amaçlı kullanmayın
✗ Özel, şifreli veya kapalı hesaplara yetkisiz erişim sağlamayın
✗ Elde edilen bulguları rıza olmaksızın kamuya açık paylaşmayın
```

---

**OSINT Ana Sayfasına Dön:** [🔍 OSINT Genel Bakış](./README.tr.md)
