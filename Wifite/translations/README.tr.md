[🇬🇧 English](../../README.md)

# 📶 Wifite - Otomatik WiFi Güvenlik Denetim Aracı Başvuru Kılavuzu

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Otomatik WiFi Denetim Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 İçindekiler

- [Wifite Nedir](#-wifite-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Hedef Seçimi](#-hedef-seçimi)
- [Saldırı Seçenekleri](#-saldırı-seçenekleri)
- [WPS Saldırıları](#-wps-saldırıları)
- [PMKID Saldırıları](#-pmkid-saldırıları)
- [Gelişmiş Seçenekler](#-gelişmiş-seçenekler)
- [Parola Kırma Seçenekleri](#-parola-kırma-seçenekleri)
- [Örnek Senaryolar](#-örnek-senaryolar)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 Wifite Nedir

**Wifite** (Wifite2), kablosuz ağ güvenlik denetimini tamamen otomatikleştiren bir araçtır:

- 🤖 **Otomatik Saldırılar** - Karmaşık komutları elle yazma ihtiyacını ortadan kaldırır
- 🎯 **Çoklu Hedef Desteği** - Birden fazla ağı sırayla denetleme imkanı
- 🔓 **WPA / WPA2 / WEP / WPS** - Tüm kablosuz şifreleme türlerini destekler
- 💾 **İlerlemeyi Kaydetme** - Yarıda kalan saldırıları kaldığı yerden devam ettirir
- 🔄 **PMKID Yakalama** - İstemcisiz modern WPA/WPA2 saldırıları

### Wifite ve Wifite2 Karşılaştırması

| Özellik | Orijinal Wifite | Wifite2 |
|---------|-----------------|---------|
| Güncellenme Durumu | Hayır | Evet |
| Python Desteği | Python 2 | Python 3 |
| PMKID Desteği | Yok | Var |
| Kod Mimarisi | Düzensiz | Temiz ve yeniden yazılmış |
| Kullanım | Önerilmez | **Şiddetle Önerilir** |

### Öne Çıkan Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Tam Otomasyon** | Hedefi seç ve saldırıyı tek tıkla başlat |
| **Akıllı Hedefleme** | Ağları sinyal gücüne göre akıllıca sıralar |
| **Kapsamlı Saldırı Yelpazesi** | WPA, WEP, WPS ve PMKID desteği |
| **Kırma Entegrasyonu** | Hashcat ve Aircrack-ng ile yerel entegrasyon |
| **Oturum Devamlılığı** | Kesintiye uğrayan oturumları korur |

---

## 🚀 Kurulum

### Kali Linux (Önceden Yüklü)

```bash
# Kali Linux'ta varsayılan olarak yüklüdür
wifite --help

# En son sürüme güncelleme
sudo apt update && sudo apt install wifite
```

### GitHub Üzerinden Wifite2 Kurulumu

```bash
# Depoyu klonlayın
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Doğrudan çalıştırın
sudo python3 wifite.py

# Veya sisteme kurun
sudo python3 setup.py install
```

### Bağımlılıklar

```bash
# Zorunlu paketler
sudo apt install aircrack-ng

# Tüm özelliklerden faydalanmak için önerilen araçlar
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Kurulumu Doğrulama

```bash
wifite --help
wifite --version
```

---

## 💻 Temel Kullanım

### Hızlı Başlangıç

```bash
# Wifite'ı doğrudan çalıştırın (arayüzü otomatik tespit eder)
sudo wifite

# Belirli bir kablosuz arayüzü seçerek başlatma
sudo wifite -i wlan0
```

### Temel İş Akışı

```
1. sudo wifite komutunu çalıştırın
2. Ağların taranıp listelenmesini bekleyin
3. Taramayı durdurmak için Ctrl+C tuşlarına basın
4. Hedef ağın numarasını seçin
5. Wifite otomatik olarak saldırıyı gerçekleştirir
6. Yakalanan el sıkışmalar kelime listesiyle kırılır
```

### Etkileşimli Arayüz

```bash
sudo wifite

# Çıktı ekranı örneği:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Hedef numarasını girin veya tüm ağlar için Ctrl+C basın
```

---

## 🎯 Hedef Seçimi

### Numaraya Göre Seçim

```bash
# Taramadan sonra hedef numaralarını girin
# Örneğin: 1. ve 3. hedefleri seçme
1, 3

# Aralık belirterek seçme
1-5

# Tüm ağları seçme
all
```

### ESSID (Ağ Adı) Filtreleme

```bash
# Belirli bir ağ adına göre hedefleme
sudo wifite --essid "TargetNetwork"

# Birden fazla ESSID belirtme
sudo wifite --essid "Network1" --essid "Network2"
```

### BSSID (MAC Adresi) Filtreleme

```bash
# Belirli bir MAC adresine kilitlenme
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Şifreleme Türüne Göre Filtreleme

```bash
# Yalnızca WPA/WPA2 ağlarını tara
sudo wifite --wpa

# Yalnızca WEP ağlarını tara
sudo wifite --wep

# Yalnızca WPS açık ağları hedefle
sudo wifite --wps
```

### Kanal ve Frekans Filtreleme

```bash
# Belirli bir kanalı dinle
sudo wifite -c 6

# Yalnızca 5GHz frekansını tara
sudo wifite --5ghz

# Yalnızca 2.4GHz frekansını tara
sudo wifite --2ghz
```

### Sinyal Gücüne Göre Filtreleme

```bash
# Minimum sinyal gücü sınırı (dBm cinsinden, 0'a yakın olan daha güçlüdür)
sudo wifite --power -50
```

---

## ⚔️ Saldırı Seçenekleri

### WPA/WPA2 Ayarları

```bash
# Deauth zaman aşımı süresini belirleme (saniye)
sudo wifite --wpa-deauth-timeout 30

# Gönderilecek deauth paketi sayısı
sudo wifite --num-deauths 5

# Handshake yakalamayı atlayıp yalnızca PMKID dene
sudo wifite --pmkid
```

### Deauthentication (Bağlantı Kesme) Seçenekleri

```bash
# Gönderilecek deauth paket adedi
sudo wifite --num-deauths 10

# Sürekli bağlantı kesme modu
sudo wifite --num-deauths 0
```

### Saldırı Türlerini Hariç Tutma

```bash
# WPA saldırılarını atla
sudo wifite --no-wpa

# WEP saldırılarını atla
sudo wifite --no-wep

# WPS saldırılarını atla
sudo wifite --no-wps

# PMKID saldırılarını atla
sudo wifite --no-pmkid
```

### NetworkManager Süreçlerini Kapatma

```bash
# Çakışan ağ süreçlerini otomatik sonlandır
sudo wifite --kill

# Süreçleri sonlandırma
sudo wifite --no-kill
```

---

## 🔓 WPS Saldırıları

### WPS Modunu Etkinleştirme

```bash
# Yalnızca WPS destekli ağları tara
sudo wifite --wps

# Yalnızca WPS saldırılarını çalıştır
sudo wifite --wps-only
```

### Kullanılacak WPS Aracını Belirleme

```bash
# Reaver kullan (varsayılan)
sudo wifite --wps --reaver

# Bully kullan
sudo wifite --wps --bully
```

### Pixie-Dust Saldırısı

```bash
# Pixie-Dust çevrimdışı WPS saldırısını etkinleştir
sudo wifite --wps --pixie

# Pixie-Dust zaman aşımı süresi
sudo wifite --wps --pixie-timeout 300
```

### WPS Zaman Aşımı Parametreleri

```bash
# Her bir PIN denemesi için zaman aşımı süresi (saniye)
sudo wifite --wps-time 120

# Maksimum WPS hata/yeniden deneme limiti
sudo wifite --wps-fails 10

# Genel WPS saldırısı zaman aşımı süresi
sudo wifite --wps-timeout 300
```

---

## 🔐 PMKID Saldırıları

### PMKID Nedir?

PMKID (Pairwise Master Key Identifier) saldırısı:
- ✅ AP'den doğrudan PMKID verisini çeker
- ✅ Ağda bağlı bir istemciye (kullanıcıya) ihtiyaç duymaz
- ✅ Deauth (bağlantı koparma) saldırısı gerektirmez
- ✅ Çoğu modern WPA2 yönlendiricide başarıyla çalışır

### PMKID Komut Seçenekleri

```bash
# Yalnızca PMKID saldırısı gerçekleştir (el sıkışma beklemeden)
sudo wifite --pmkid

# PMKID deneme süresi limiti
sudo wifite --pmkid-timeout 120
```

### PMKID Çalışma Mantığı

```
1. AP'ye ilişkilendirme isteği (association request) gönderilir
2. AP, yanıt çerçevesindeki EAPOL paketine PMKID bilgisini ekler
3. PMKID havadan yakalanır
4. Hashcat ile çevrimdışı sözlük saldırısıyla şifre çözülür
```

---

## ⚙️ Gelişmiş Seçenekler

### Arayüz Belirtme

```bash
# Ağ kartını belirt
sudo wifite -i wlan0

# Monitör modundaki arayüzü belirt
sudo wifite -i wlan0mon
```

### Çıktı ve Günlük Seçenekleri

```bash
# Ayrıntılı çıktı (Verbose)
sudo wifite -v
sudo wifite --verbose

# Çok ayrıntılı çıktı
sudo wifite -vv

# Sessiz mod (Quiet)
sudo wifite --quiet
```

### Zamanlama Parametreleri

```bash
# Tarama süresi (saniye)
sudo wifite --scan-time 30

# Hedef başına maksimum saldırı süresi
sudo wifite --attack-timeout 300
```

### Kırılan Parolaların Yönetimi

```bash
# Kırılan parolalar varsayılan olarak şu konuma kaydedilir:
# ~/.config/wifite/cracked.txt

# Kırılan ağları görüntüleme
cat ~/.config/wifite/cracked.txt

# Parola dosyasını özel bir yola yönlendirme
sudo wifite --cracked cracked_passwords.txt
```

### Atlatma ve Filtreleme Seçenekleri

```bash
# Parola kırmayı atla (yalnızca paket yakala)
sudo wifite --skip-crack

# Daha önce parolası kırılmış ağları atla
sudo wifite --skip-cracked
```

### Rastgele MAC Adresi Kullanımı

```bash
# Rastgele MAC adresi ata (kimlik gizleme)
sudo wifite --random-mac

# MAC adresini değiştirme
sudo wifite --no-random-mac
```

---

## 🔓 Parola Kırma Seçenekleri

### Sözlük Saldırısı (Wordlist)

```bash
# Özel kelime listesi belirtme
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Birden fazla kelime listesi kullanma
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Hashcat Entegrasyonu

```bash
# GPU gücüyle kırmak için Hashcat kullan
sudo wifite --hashcat

# Hashcat dosya yolunu belirt
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Aircrack-ng Seçenekleri

```bash
# Aircrack-ng ile kırma (varsayılan)
sudo wifite --aircrack

# Aircrack-ng dosya yolunu belirt
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Yalnızca Kırma Modu (Crack Only)

```bash
# Önceden yakalanmış el sıkışma dosyalarını kırma
sudo wifite --crack

# Yakalama dosyası ve kelime listesi belirterek kırma
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Örnek Senaryolar

### Temel WiFi Güvenlik Denetimi

```bash
# Tüm ağları tara ve seçilenleri otomatik denetle
sudo wifite

# Rockyou listesiyle otomatik denetim
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Belirli Bir Ağı Hedefleme

```bash
# İsme göre hedefleme
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# MAC adresine göre hedefleme
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Yalnızca WPA2 ve PMKID Saldırısı

```bash
# Yalnızca PMKID desteğiyle WPA2 ağlarına saldır
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### WPS Saldırısı (Pixie-Dust)

```bash
# WPS destekli ağlara Pixie-Dust uygula
sudo wifite --wps --pixie

# Bully ile doğrudan WPS kaba kuvveti
sudo wifite --wps-only --bully
```

### Sinyali Güçlü Olan Ağları Hedefleme

```bash
# Yalnızca -50 dBm üzeri güçlü sinyalli ağları hedefle
sudo wifite --power -50 --wpa
```

### 5GHz Ağları Denetleme

```bash
# Yalnızca 5GHz bandındaki ağları tara
sudo wifite --5ghz --wpa
```

### Hızlı Denetim (Yalnızca Yakalama)

```bash
# El sıkışmaları topla, kırma işlemini sonraya bırak
sudo wifite --skip-crack
```

### Kesilen Saldırıyı Devam Ettirme

```bash
# Wifite ilerlemeyi otomatik saklar, tekrar çalıştırmanız yeterlidir
sudo wifite

# Daha önce kırılanları tekrar denemeden devam et
sudo wifite --skip-cracked
```

### Gizli (Stealth) Mod

```bash
# Minimum deauth göndererek sessizce çalış
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Hızlı Referans

### Temel Komutlar

| Amaç | Komut |
|------|-------|
| Temel tarama ve denetim | `sudo wifite` |
| İsme göre hedefleme | `sudo wifite --essid "AgAdi"` |
| MAC adresine göre hedefleme | `sudo wifite --bssid MAC` |
| Yalnızca WPA/WPA2 | `sudo wifite --wpa` |
| Yalnızca WPS | `sudo wifite --wps-only` |
| PMKID saldırısı | `sudo wifite --pmkid` |
| Özel kelime listesi | `sudo wifite --dict wordlist.txt` |
| Kırmayı atla | `sudo wifite --skip-crack` |

### Saldırı Türleri

| Saldırı | Parametre | Hedef |
|---------|-----------|-------|
| WPA Handshake | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS açık ağlar |
| WPS Kaba Kuvvet | `--wps-only` | WPS açık ağlar |
| WEP | `--wep` | WEP şifreli ağlar |

### Popüler Kelime Listeleri

| Dosya Yolu | Açıklama |
|------------|----------|
| `/usr/share/wordlists/rockyou.txt` | En yaygın parola listesi |
| `/usr/share/wordlists/fasttrack.txt` | Hızlı kontroller için kısa liste |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | WiFi odaklı özel SecLists listeleri |

### Yakalanan Dosyaların Konumu

```
~/.config/wifite/
├── cracked.txt          # Kırılmış parolalar ve ağlar
├── hs/                  # Yakalanan 4-way handshake dosyaları
│   └── handshake_*.cap
└── pmkid/               # Yakalanan PMKID hash dosyaları
    └── pmkid_*.22000
```

### Parametreler Tablosu

| Parametre | Açıklama |
|-----------|----------|
| `-i` | Ağ arayüzü |
| `-c` | Kanal numarası |
| `-v` | Ayrıntılı log |
| `--wpa` | Sadece WPA/WPA2 ağları |
| `--wep` | Sadece WEP ağları |
| `--wps` | WPS denemelerini aç |
| `--pmkid` | PMKID saldırı modu |
| `--dict` | Sözlük listesi yolu |
| `--kill` | Çakışan süreçleri öldür |
| `--power` | Asgari sinyal gücü eşiği |
| `--5ghz` | 5GHz frekans bandı |
| `--2ghz` | 2.4GHz frekans bandı |

### Sorun Giderme

```bash
# Ağ kartı bulunamadı uyarısı
sudo airmon-ng check kill
sudo wifite -i wlan0

# Hiçbir ağ tespit edilemiyorsa
# - Adaptörün monitör modunu desteklediğinden emin olun
# - Farklı bantları deneyin: --5ghz veya --2ghz
# - Hedef ağlara daha yakın bir konuma geçin

# Handshake yakalanamıyorsa
# - Deauth paket sayısını artırın: --num-deauths 10
# - Bekleme süresini uzatın: --wpa-deauth-timeout 60
# - Ağda aktif istemci bulunmasını bekleyin

# Parola kırma başarısız olduysa
# - Daha kapsamlı bir kelime listesi kullanın
# - GPU gücünden faydalanmak için Hashcat parametresini ekleyin: --hashcat
```

---

## 🔄 Wifite ve Manuel Aircrack-ng Karşılaştırması

| Görev | Wifite | Aircrack-ng Paketi |
|-------|--------|---------------------|
| Monitör modu | Otomatik | `airmon-ng start wlan0` |
| Ağ taraması | Otomatik | `airodump-ng wlan0mon` |
| Hedef seçimi | Etkileşimli menü | Manuel filtreleme |
| Bağlantı kesme | Otomatik | `aireplay-ng -0 ...` |
| Paket yakalama | Otomatik | `airodump-ng -w ...` |
| Parola kırma | Otomatik | `aircrack-ng -w ...` |
| **Zahmet** | **Çok Kolay** | Manuel adımlar |

---

## ⚠️ Yasal Uyarı

```
⚠️ UYARI: Wifite aracını yalnızca SAHİBİ OLDUĞUNUZ veya güvenlik testi 
gerçekleştirmek için AÇIK YAZILI İZİN ALDIĞINIZ ağlarda kullanın.

✅ Yasal ve izinli sızma testleri
✅ Kurumsal güvenlik denetimleri (yetki belgesiyle)
✅ Eğitim ve laboratuvar çalışmaları

❌ Yetkisiz kablosuz ağlara erişmek YASADIŞIDIR
❌ Kötü niyetli ve izinsiz saldırılarda kesinlikle kullanılamaz
```

---

## 📚 Kaynaklar

- [Wifite2 GitHub Deposu](https://github.com/derv82/wifite2)
- [Wifite Wiki Belgeleri](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng Resmi Sitesi](https://www.aircrack-ng.org/)

### İlgili Başvuru Kılavuzları
- [Aircrack-ng](../../Aircrack-ng/translations/README.tr.md)
- [Hashcat](../../Hashcat/translations/README.tr.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)

---

<p align="center">
  <b>📶 Otomatik WiFi Güvenlik Denetimi!</b><br>
  <i>Wifite - Kablosuz ağ güvenliğinde pratik ve güçlü otomasyon</i>
</p>
