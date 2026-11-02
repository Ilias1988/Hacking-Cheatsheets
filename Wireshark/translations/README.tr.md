# 🦈 Wireshark - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
          Ağ Protokolü ve Paket Analizcisi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 Dünyanın en popüler ağ protokolü ve paket inceleme aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Arayüze Genel Bakış](#-arayüze-genel-bakış)
- [Paket Yakalama Temelleri](#-paket-yakalama-temelleri)
- [Görüntüleme Filtreleri (Display Filters)](#-görüntüleme-filtreleri-display-filters)
- [Yakalama Filtreleri (Capture Filters)](#-yakalama-filtreleri-capture-filters)
- [Protokol Analizi](#-protokol-analizi)
- [Akışları Takip Etme (Follow Stream)](#-akışları-takip-etme-follow-stream)
- [İstatistikler & Analiz](#-i̇statistikler--analiz)
- [tshark (Komut Satırı Sürümü)](#-tshark-komut-satırı-sürümü)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [Klavye Kısayolları](#-klavye-kısayolları)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Wireshark**, ağ sorunlarını giderme, güvenlik denetimi, adli bilişim analizi ve protokol geliştirme için kullanılan ücretsiz ve açık kaynaklı bir paket analiz aracıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Derinlemesine İnceleme** | Yüzlerce ağ protokolü için tam çözümleme desteği |
| **Canlı Paket Yakalama** | Ağ arayüzünden gerçek zamanlı veri toplama |
| **Çevrimdışı Analiz** | Önceden kaydedilmiş dosyaları inceleme (`pcap`, `pcapng`) |
| **Güçlü Filtreler** | Gelişmiş yakalama (Capture) ve görüntüleme (Display) filtreleri |
| **Çoklu Platform** | Windows, Linux ve macOS |
| **GUI & CLI** | Grafik arayüz (Wireshark) ve komut satırı aracı (tshark) |

---

## 📥 Kurulum

### Windows
```bash
# Resmi siteden kurulum:
https://www.wireshark.org/download.html

# Chocolatey ile:
choco install wireshark
```

### Linux (Kali / Ubuntu)
```bash
# Kali Linux (önceden kuruludur)
wireshark

# Ubuntu / Debian
sudo apt update && sudo apt install wireshark

# Root olmayan kullanıcıya yakalama yetkisi verme:
sudo usermod -aG wireshark $USER
# Oturumu kapatıp yeniden açın
```

### macOS (Homebrew)
```bash
brew install --cask wireshark
```

### Kurulumu Doğrulama
```bash
wireshark --version
tshark --version
```

---

## 🖥️ Arayüze Genel Bakış

### Ana Pencere Bölümleri

| Bölüm | Açıklama |
|-------|----------|
| **Menü Çubuğu (Menu Bar)** | Dosya, Düzen, Görünüm, Yakalama, İstatistikler menüleri |
| **Araç Çubuğu (Main Toolbar)** | Yakalamayı başlatma, durdurma ve kaydetme düğmeleri |
| **Filtre Çubuğu (Filter Bar)** | Görüntüleme filtrelerinin yazıldığı alan |
| **Paket Listesi (Packet List)** | Yakalanan tüm paketlerin özet satırları |
| **Paket Detayları (Packet Details)** | Seçili paketin OSI katmanlarına göre açılır ayrıntıları |
| **Ham Baytlar (Packet Bytes)** | Paketin onaltılık (hex) ve ASCII ham verisi |

### Paket Listesi Sütunları

| Sütun | Açıklama |
|-------|----------|
| No. | Paketin sıra numarası |
| Time | Yakalandığı zaman damgası |
| Source | Kaynak IP veya MAC adresi |
| Destination | Hedef IP veya MAC adresi |
| Protocol | Paketin en üst katmandaki protokolü |
| Length | Paketin bayt cinsinden boyutu |
| Info | Pakete ait özet protokol bilgisi |

---

## 📡 Paket Yakalama Temelleri

### Yakalamayı Başlatma

```
1. Karşılama ekranından dinlenecek ağ arayüzünü seçin (örn: eth0, wlan0)
2. "Start Capture" düğmesine (mavi köpekbalığı yüzgeci) tıklayın
3. Veya menüden: Capture → Start
4. Hızlı yol: Arayüz adına çift tıklayın
```

### Yakalamayı Durdurma

```
Araç çubuğundaki kırmızı kareye tıklayın
Veya: Capture → Stop
Kısayol: Ctrl+E
```

### Yakalamayı Kaydetme

```
File → Save As (pcapng veya pcap formatında kaydedin)
```

### Yakalama Dosyasını Açma

```
File → Open (Ctrl+O)
Veya komut satırından: wireshark dosya.pcap
```

---

## 🔍 Görüntüleme Filtreleri (Display Filters)

### Filtre Sözdizimi

Görüntüleme filtreleri alan karşılaştırma mantığıyla çalışır:

```
alan karşılaştırma_operatörü değer
```

### Karşılaştırma Operatörleri

| Operatör | Karşılığı | Açıklama | Örnek |
|----------|-----------|----------|-------|
| `==` | `eq` | Eşittir | `ip.addr == 192.168.1.1` |
| `!=` | `ne` | Eşit değildir | `ip.addr != 10.0.0.1` |
| `>` | `gt` | Büyüktür | `frame.len > 100` |
| `<` | `lt` | Küçüktür | `tcp.port < 1024` |
| `>=` | `ge` | Büyük veya eşittir | `frame.len >= 64` |
| `<=` | `le` | Küçük veya eşittir | `tcp.port <= 1024` |
| `contains` | - | İçerir (metin) | `http contains "password"` |
| `matches` | - | Düzenli ifade (regex) | `http.host matches ".*\.com"` |

### Mantıksal Operatörler

| Operatör | Karşılığı | Açıklama | Örnek |
|----------|-----------|----------|-------|
| `and` | `&&` | Mantıksal VE | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` | `\|\|` | Mantıksal VEYA | `tcp.port == 80 or tcp.port == 443` |
| `not` | `!` | Mantıksal DEĞİL | `not arp` |
| `()` | - | Öncelik parantezi | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### IP Adresi Filtreleri

```bash
# Kaynak veya hedef IP adresi
ip.addr == 192.168.1.1

# Yalnızca kaynak IP
ip.src == 192.168.1.1

# Yalnızca hedef IP
ip.dst == 192.168.1.1

# Alt ağ (Subnet) filtresi
ip.addr == 192.168.1.0/24

# Belirli bir IP'yi hariç tutma
ip.addr != 192.168.1.1

# İki ana makine arasındaki trafik
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Port Filtreleri

```bash
# Belirli port (kaynak veya hedef)
tcp.port == 80
udp.port == 53

# Sadece kaynak port
tcp.srcport == 443

# Sadece hedef port
tcp.dstport == 22

# Port aralığı
tcp.port >= 1 and tcp.port <= 1024

# Çoklu port seçimi
tcp.port in {80 443 8080}
```

### Protokol Filtreleri

```bash
# Protokol adına göre filtreleme
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# HTTP filtreleri
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# DNS filtreleri
dns.qry.name == "google.com"
dns.qry.type == 1          # A kaydı
dns.flags.response == 1    # DNS yanıtları

# TCP bayrak filtreleri
tcp.flags.syn == 1         # SYN paketleri
tcp.flags.fin == 1         # FIN paketleri
tcp.flags.reset == 1       # RST paketleri
tcp.analysis.retransmission  # Yeniden iletimler (retransmissions)
tcp.stream == 5            # Belirli bir TCP akışı
```

### İçerik ve Dize Filtreleri

```bash
# Paket içinde metin arama
http contains "password"
tcp contains "login"
frame contains "secret"

# HTTP alanlarında arama
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Büyük/küçük harf duyarsız arama
http.host matches "(?i)example"
```

---

## 🎣 Yakalama Filtreleri (Capture Filters)

Yakalama filtreleri doğrudan donanım/paket yakalama motoruna verilir ve **BPF (Berkeley Packet Filter)** sözdizimini kullanır (Görüntüleme filtrelerinden farklıdır!).

### Temel BPF Sözdizimi

```bash
# Ana makine
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Ağ bloğu
net 192.168.1.0/24
src net 10.0.0.0/8

# Port
port 80
src port 443
dst port 22
portrange 1-1024

# Protokol
tcp
udp
icmp
arp
```

### Bileşik Yakalama Filtreleri

```bash
# Belirli ana makine ve port
host 192.168.1.1 and port 80

# İki makine arasındaki konuşma
host 192.168.1.1 and host 192.168.1.2

# Belirli trafiği hariç tutma
not port 22
not host 10.0.0.1

# Karmaşık filtre
tcp and (port 80 or port 443) and host 192.168.1.1
```

---

## 🔬 Protokol Analizi

### TCP Analizi

```bash
# TCP El Sıkışması (Handshake)
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Yalnızca SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # Bağlantıyı kapatma (FIN)
tcp.flags.reset == 1                          # Bağlantıyı sıfırlama (RST)

# Ağ problemleri
tcp.analysis.retransmission                  # Paket kayıpları ve tekrar iletim
tcp.analysis.duplicate_ack                   # Yinelenen onay paketleri
tcp.analysis.zero_window                     # Alıcı arabellek doluluğu
```

### HTTP Analizi

```bash
# HTTP istekleri
http.request
http.request.method == "POST"
http.request.uri contains "/login"

# Hata kodları
http.response.code >= 400

# Gönderilen form veya dosya verisi
http.file_data contains "password"
```

### DNS Analizi

```bash
# DNS sorguları
dns.flags.response == 0
dns.qry.name == "example.com"

# Hata yanıtları (NXDomain vb.)
dns.flags.response == 1 and dns.flags.rcode != 0
```

### TLS / SSL Analizi

```bash
# TLS trafiği
tls

# TLS El Sıkışması
tls.handshake.type == 1     # Client Hello (SNI alanını içerir)
tls.handshake.type == 2     # Server Hello
tls.handshake.certificate  # Gönderilen sertifikalar
```

---

## 🔗 Akışları Takip Etme (Follow Stream)

### TCP Akışını Takip Etme

```
1. Herhangi bir TCP paketine sağ tıklayın
2. Follow → TCP Stream seçeneğini seçin
(Veya menüden: Analyze → Follow → TCP Stream)
```

Bu işlem:
- İki taraf arasındaki tam iletişimi birleştirip metin olarak gösterir
- İstemci isteklerini kırmızı, sunucu yanıtlarını mavi renkle vurgular
- Otomatik olarak `tcp.stream == X` filtresini uygular

---

## 📊 İstatistikler & Analiz

| İstatistik Menüsü | Açıklama |
|-------------------|----------|
| **Protocol Hierarchy** | Yakalanan paketlerin protokol dağılımını gösterir |
| **Conversations** | İki uç nokta arasındaki tüm oturumları listeler (IP, TCP, UDP) |
| **Endpoints** | Ağdaki tüm benzersiz cihaz ve IP listesini çıkarır |
| **IO Graph** | Zaman içindeki trafik yoğunluğu ve bant genişliği grafiğini çizer |
| **Expert Information** | Anormallikleri, paket kayıplarını ve protokol hatalarını raporlar |

---

## 💻 tshark (Komut Satırı Sürümü)

### Temel Kullanım

```bash
# Canlı arayüz dinleme
tshark -i eth0

# pcap dosyasını okuma
tshark -r capture.pcap

# Canlı yakalamayı dosyaya yazma
tshark -i eth0 -w output.pcap
```

### Filtrelerle Kullanım

```bash
# Yakalama filtresi (BPF)
tshark -i eth0 -f "port 80"

# Görüntüleme filtresi (-Y)
tshark -r capture.pcap -Y "http"
```

### Belirli Alanları Çıkarma (-T fields)

```bash
# Kaynak IP, hedef IP ve portları listele
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# DNS sorgu isimlerini çıkar
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Canlı HTTP istek URL'lerini izle
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Şifresiz Parola Avı
```bash
# HTTP veya FTP üzerinden giden şifresiz kimlik bilgileri
http.request.method == "POST" and http contains "password"
# Veya FTP için:
ftp contains "PASS"
```

### Örnek 2: Port Tarama Tespiti
```bash
# Çok sayıda farklı porta gönderilen SYN paketleri
tcp.flags.syn == 1 and tcp.flags.ack == 0
# Ardından Statistics → Conversations → TCP menüsünü inceleyin
```

### Örnek 3: DNS Tünelleme / Veri Kaçırma (Exfiltration)
```bash
# Anormal derecede büyük DNS sorguları
dns and frame.len > 512
# Şüpheli TXT kayıtları
dns.qry.type == 16
```

### Örnek 4: Yakalanan Dosyaları Dışa Aktarma
```
File → Export Objects → HTTP
Listeden istenen dosya seçilip diske kaydedilir
```

---

## 📊 Hızlı Başvuru

### Sık Kullanılan Görüntüleme Filtreleri

| Amaç | Filtre |
|------|--------|
| HTTP trafiği | `http` |
| DNS trafiği | `dns` |
| Belirli IP | `ip.addr == 192.168.1.1` |
| Belirli Port | `tcp.port == 80` |
| TCP Sorunları | `tcp.analysis.flags` |
| HTTP Hata Kodları | `http.response.code >= 400` |
| Metin İçeren | `frame contains "text"` |
| Protokol Hariç Tut | `not arp` |

---

## ⌨️ Klavye Kısayolları

| Kısayol | İşlem |
|---------|-------|
| `Ctrl+E` | Yakalamayı Başlat / Durdur |
| `Ctrl+K` | Yakalama Seçenekleri |
| `Ctrl+O` | Dosya Aç |
| `Ctrl+S` | Dosya Kaydet |
| `Ctrl+F` | Paket İçinde Ara |
| `Ctrl+G` | Belirli Paket Numarasına Git |
| `Ctrl+M` | Paketi İşaretle (Mark/Unmark) |
| `Boşluk (Space)` | Protokol ağacını aç/kapat |
| `Tab` | Sonraki panele geç |

---

## ⚠️ Yasal Uyarı

> **UYARI:** Ağ paketlerini yakalama ve inceleme, yalnızca **yetkili ve izinli ağlarda** gerçekleştirilmelidir.
> 
> - ✅ Kendi ağlarınızda ve laboratuvar ortamlarında test edin
> - ✅ Yazılı izin alınmış güvenlik analizlerinde kullanın
> - ❌ Başkalarına ait ağ trafiğini izinsiz dinlemeyin
> 
> **Yetkisiz ağ dinleme (sniffing) eylemleri yasalarca suç sayılmaktadır.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Wireshark Resmi Sitesi](https://www.wireshark.org/)
- [Wireshark Kullanıcı Kılavuzu](https://www.wireshark.org/docs/wsug_html/)
- [Görüntüleme Filtreleri Referansı](https://www.wireshark.org/docs/dfref/)
- [Örnek Paket Yakalamaları (Sample Captures)](https://wiki.wireshark.org/SampleCaptures)

### İlgili Kılavuzlar
- [Nmap](../../Nmap/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)
- [Hydra](../../Hydra/translations/README.tr.md)

---

<p align="center">
  <b>🦈 Ağ Analizi ve Paket İncelemede Ustalaşın!</b><br>
  <i>Ağ kablosunda akan her şeyi net bir şekilde görün</i>
</p>
