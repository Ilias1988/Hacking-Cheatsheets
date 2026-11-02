[🇬🇧 English](../../README.md)

# 🔍 OSINT (Açık Kaynak İstihbaratı) Başvuru Kılavuzu

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **Açık Kaynak İstihbaratı (OSINT)** - Kamuya açık ve yasal kaynaklardan bilgi toplama, işleme ve analiz etme disiplinidir.

---

## 📑 İçindekiler

| Konu | Açıklama | Kılavuz |
|------|----------|---------|
| **Kişi Arama** | Bireyler hakkında açık istihbarat toplama | [📄 Görüntüle](./People-Search.tr.md) |
| **E-posta OSINT** | E-posta keşfi, biçim analizi ve doğrulama | [📄 Görüntüle](./Email-OSINT.tr.md) |
| **Sosyal Medya** | Sosyal ağlarda profil ve kullanıcı adı araştırması | [📄 Görüntüle](./Social-Media-OSINT.tr.md) |
| **Alan Adı & IP** | Alan adı, DNS ve IP adresi istihbaratı | [📄 Görüntüle](./Domain-IP-OSINT.tr.md) |
| **Görsel OSINT** | Tersine görsel arama, EXIF ve konum analizi | [📄 Görüntüle](./Image-OSINT.tr.md) |

---

## 🎯 OSINT Metodolojisi

```
┌─────────────────────────────────────────────────────────────┐
32: │                    OSINT YAŞAM DÖNGÜSÜ                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. GEREKSİNİMLER   →  İstihbarat hedeflerini belirleme     │
│         ↓                                                   │
│  2. TOPLAMA         →  Kaynaklardan ham verileri derleme    │
│         ↓                                                   │
│  3. İŞLEME          →  Verileri kullanılabilir formata dökme│
│         ↓                                                   │
│  4. ANALİZ          →  Bulguları değerlendirme ve bağlama  │
│         ↓                                                   │
│  5. RAPORLAMA       →  İstihbarat raporunu sunma            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Temel OSINT Araçları

### Arama Motorları
| Araç | URL | Amaç |
|------|-----|------|
| **Google** | https://google.com | Genel arama ve dorking |
| **Bing** | bing.com | Alternatif arama motoru |
| **DuckDuckGo** | duckduckgo.com | Gizlilik odaklı arama |
| **Yandex** | yandex.com | Rus arama motoru (görsel aramada çok güçlü) |
| **Baidu** | baidu.com | Çin arama motoru |

### Kişi Arama Servisleri
| Araç | URL | Amaç |
|------|-----|------|
| **Pipl** | pipl.com | Derin kişi araştırması |
| **Spokeo** | spokeo.com | Sosyal medya ve kişi araması |
| **WhitePages** | whitepages.com | Telefon / Adres sorgulama |
| **ThatsThem** | thatsthem.com | Ücretsiz kişi sorgulama |
| **BeenVerified** | beenverified.com | Geçmiş ve sicil kontrolü |

### E-posta Araçları
| Araç | URL | Amaç |
|------|-----|------|
| **Hunter.io** | hunter.io | Kurumsal e-posta desenleri bulma |
| **Have I Been Pwned** | haveibeenpwned.com | Veri ihlali ve sızıntı kontrolü |
| **EmailRep** | emailrep.io | E-posta itibar puanı |
| **Phonebook.cz** | phonebook.cz | E-posta / alan adı arama |
| **h8mail** | GitHub | Sızıntı kimlik bilgisi arama |

### Sosyal Medya
| Araç | URL | Amaç |
|------|-----|------|
| **Sherlock** | GitHub | Platformlar arası kullanıcı adı arama |
| **Social Searcher** | social-searcher.com | Sosyal medya içerik araması |
| **Namechk** | namechk.com | Kullanıcı adı kullanılabilirlik kontrolü |
| **KnowEm** | knowem.com | Kullanıcı adı sorgulama |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | Arama, takipçi dışa aktarma ve izleme için bağımsız X (Twitter) veri API'si |

### Alan Adı / IP Araçları
| Araç | URL | Amaç |
|------|-----|------|
| **Shodan** | shodan.io | Bağlı cihaz ve IoT arama motoru |
| **Censys** | censys.io | İnternet genel tarama motoru |
| **SecurityTrails** | securitytrails.com | Geçmiş DNS ve alan adı kayıtları |
| **ViewDNS** | viewdns.info | DNS sorgulama araçları |
| **BuiltWith** | builtwith.com | Teknoloji profili çıkarma |

---

## 🔥 Hızlı Başvuru Komutları

### theHarvester
```bash
# E-posta ve alt alan adı toplama
theHarvester -d target.com -l 500 -b all

# Belirli kaynakları kullanma
theHarvester -d target.com -b google,linkedin,twitter

# Çıktıyı HTML dosyasına kaydetme
theHarvester -d target.com -b all -f results.html
```

### Sherlock (Kullanıcı Adı Taraması)
```bash
# Tüm platformlarda arama
python3 sherlock.py username

# Belirli sitelerde arama
python3 sherlock.py username --site twitter instagram facebook

# Çıktıyı dosyaya kaydetme
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Transform türleri:
- Kişiden E-postaya (Person to Email)
- E-postadan Alan Adına (Email to Domain)
- Alan Adından IP Adresine (Domain to IP)
- IP Adresinden Konuma (IP to Location)
- Sosyal Medya profilleri (Social Media profiles)
```

### Recon-ng
```bash
# recon-ng başlatma
recon-ng

# Çalışma alanı (workspace) oluşturma
workspaces create hedef_adi

# Modülleri yükleme
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Hedefi belirleme
options set SOURCE target.com

# Modülü çalıştırma
run
```

---

## 📊 OSINT Kategorileri

### Pasif OSINT
```
Hedefle doğrudan temas kurulmaz:
- Arama motorları
- Sosyal medya analizleri
- Kamu ve resmi sicil kayıtları
- Archive.org (Wayback Machine)
- WHOIS veritabanları
```

### Aktif OSINT
```
Hedef sistemlerle doğrudan iletişim kurulur:
- Port taramaları
- DNS sorgulamaları
- Web crawling (tarama)
- SMTP e-posta doğrulaması
```

---

## 🔒 Operasyonel Güvenlik (OPSEC)

### En İyi Pratikler
```
✓ Güvenilir VPN / Tor ağı kullanın
✓ Sahte (sock puppet) araştırma hesapları oluşturun
✓ İzolasyon için sanal makineler (VM) tercih edin
✓ Asla kişisel hesaplarınızı veya gerçek IP'nizi kullanmayın
✓ Çerezleri ve önbelleği düzenli olarak temizleyin
✓ Ayrılmış özel tarayıcı profilleri oluşturun
✓ Tüm adımları ve kanıtları belgeleyin
```

### Sock Puppet (Araştırma Hesabı) İlkeleri
```
- Yapay zeka ile üretilmiş veya jenerik kimlikler kullanın
- Platformlar arasında tutarlı bir kimlik hikayesi kurgulayın
- Hesabın yaşını (hesap açılış tarihini) önceden olgunlaştırın
- İnandırıcı profil detayları ekleyin
- Gerçek kimliğinizle veya kişisel verilerinizle asla bağlantı kurmayın
```

---

## 📋 OSINT Kontrol Listesi

```markdown
## Hedef: [Kişi / Şirket / Alan Adı]

### Bireyler (Kişiler)
□ Tam ad varyasyonları
□ Telefon numaraları
□ E-posta adresleri
□ Fiziksel adresler
□ Sosyal medya profilleri
□ Profesyonel ve mesleki geçmiş
□ Eğitim geçmişi
□ Fotoğraflar ve görseller

### Şirketler / Kurumlar
□ Alan adı tescil bilgileri (WHOIS)
□ Teknoloji yığını
□ Çalışan listesi ve yöneticiler
□ Şirket e-posta formatı
□ Sosyal medya hesapları
□ Basın bültenleri ve haberler
□ Finansal raporlar ve ticaret sicili
□ Fiziksel ofis ve tesis konumları

### Teknik Varlıklar
□ IP adresleri ve blokları
□ Alt alan adları (subdomains)
□ Açık portlar ve çalışan servisler
□ DNS kayıtları
□ SSL/TLS sertifikaları
□ Geçmiş web arşivleri
```

---

## 🔗 OSINT Çatıları (Frameworks)

### OSINT Framework
```
https://osintframework.com/
- Kategorilere göre düzenlenmiş yapı
- Yüzlerce araca doğrudan bağlantı
- Görsel ağaç diyagramı
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Michael Bazzell'in açık kaynak araçları
- Özel arama motorları
- Adli bilişim ve soruşturma iş akışları
```

---

## 📚 Bu Depodaki İlgili Kılavuzlar

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/translations/README.tr.md)
- [Subfinder](../../Subfinder/translations/README.tr.md)

---

**Ana Sayfaya Dön:** [🔴 Hacking Cheatsheets](../../README.tr.md)
