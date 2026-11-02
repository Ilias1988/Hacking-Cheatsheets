# 📡 tcpdump - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
       Komut Satırı Paket Analizcisi
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 Klasik komut satırı ağ paketi yakalama ve analiz aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Sık Kullanılan Seçenekler](#-sık-kullanılan-seçenekler)
- [BPF Filtreleri (Berkeley Packet Filter)](#-bpf-filtreleri-berkeley-packet-filter)
- [Protokol Filtreleri](#-protokol-filtreleri)
- [Çıktı ve Kayıt Seçenekleri](#-çıktı-ve-kayıt-seçenekleri)
- [Gerçek Dünya Örnekleri](#-gerçek-dünya-örnekleri)
- [Hızlı Başvuru](#-hızlı-başvuru)
- [İpuçları ve En İyi Uygulamalar](#-i̇puçları-ve-en-i̇yi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**tcpdump**, Unix ve Linux işletim sistemlerinin çoğunda varsayılan olarak bulunan, ağ sorunlarını giderme ve güvenlik analizi için vazgeçilmez olan hafif ve son derece güçlü bir komut satırı paket analizcisidir. `libpcap` kütüphanesini ve **BPF (Berkeley Packet Filter)** sözdizimini kullanır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Hafif ve Hızlı** | Grafik arayüze ihtiyaç duymaz, minimum sistem kaynağı tüketir |
| **BPF Filtreleme** | Çekirdek (kernel) seviyesinde yüksek performanslı paket filtreleme |
| **pcap Uyumluluğu** | Wireshark ile tam uyumlu pcap dosyalarını okuma ve yazma |
| **Otomasyon ve Betikleme** | Bash betikleri ve CI/CD süreçleri için mükemmel entegrasyon |
| **Evrensel Kullanılabilirlik** | Neredeyse tüm Unix/Linux sunucularda kuruludur |

### tcpdump vs Wireshark Karşılaştırması

| Özellik | tcpdump | Wireshark |
|---------|---------|-----------|
| **Arayüz** | CLI (Komut Satırı) | GUI (Grafiksel) |
| **Kaynak Tüketimi** | Çok Düşük | Daha Yüksek |
| **Uzak Sunucu Erişimi** | Doğal SSH ile anında | X11/VNC veya dosya aktarımı gerektirir |
| **Betik Uyumluluğu** | Çok Kolay | Karmaşık |
| **Derin Analiz** | Temel / Orta | İleri Düzey Görselleştirme |

---

## 📥 Kurulum

### Linux

```bash
# Kali / Debian / Ubuntu
sudo apt update && sudo apt install tcpdump

# CentOS / RHEL
sudo yum install tcpdump

# Arch Linux
sudo pacman -S tcpdump
```

### macOS (Önceden Kuruludur)
```bash
tcpdump --version
```

### Kurulumu Doğrulama
```bash
tcpdump --version
tcpdump -D              # Kullanılabilir ağ arayüzlerini listeler
```

---

## ⌨️ Temel Sözdizimi

### Genel Sözdizimi
```bash
tcpdump [seçenekler] [filtre_ifadesi]
```

### Temel Komutlar
```bash
# Varsayılan arayüzde yakalamayı başlat
sudo tcpdump

# Belirli bir arayüzde dinleme yap (örn: eth0)
sudo tcpdump -i eth0

# Belirli sayıda paket yakalayıp dur
sudo tcpdump -c 100

# Ayrıntılı (verbose) mod
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

## ⚙️ Sık Kullanılan Seçenekler

### Gösterim Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-i` | Dinlenecek ağ arayüzü |
| `-c` | Yakalanacak paket sayısı |
| `-n` | IP adreslerini ana makine isimlerine çözümleme (DNS sorgusu yapma) |
| `-nn` | Hem IP adreslerini hem de port numaralarını isimlere çözümleme |
| `-v` | Ayrıntılı çıktı |
| `-vv` | Daha ayrıntılı çıktı |
| `-vvv` | En üst düzey ayrıntılı çıktı |
| `-q` | Sessiz mod (daha az bilgi gösterir) |
| `-t` | Zaman damgasını yazdırma |
| `-tt` | Unix zaman damgası (epoch) yazdır |
| `-ttt` | Paketler arası geçen süreyi (delta) yazdır |
| `-tttt` | Tarih ve tam saat bilgisini yazdır |

### Paket Yakalama ve Boyut Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-w` | Paketleri pcap dosyasına kaydet |
| `-r` | Önceden kaydedilmiş pcap dosyasını oku |
| `-s` | Paket yakalama boyutu (bayt sınırı) |
| `-s0` | Paketin tamamını kesilmeden yakala (`-s 0`) |
| `-D` | Kullanılabilir arayüzleri listele |

### Veri Format Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-A` | Paket içeriğini ASCII metin olarak yazdır |
| `-X` | Paket içeriğini hem Onaltılık (Hex) hem ASCII olarak yazdır |
| `-XX` | Ethernet başlıkları dahil Hex ve ASCII yazdır |
| `-e` | Bağlantı katmanı (MAC adresi) başlığını göster |
| `-l` | Satır arabellekli çıktı (pipe ile diğer araçlara aktarmak için) |

### Popüler Kombinasyonlar
```bash
# DNS çözümlemesi yapma, en ayrıntılı, tam paket boyutu (Analistler için standart)
sudo tcpdump -nnvvs0 -i eth0

# Canlı trafiği dosyaya kaydet
sudo tcpdump -i eth0 -w capture.pcap

# Kayıtlı dosyayı terminalde incele
tcpdump -r capture.pcap

# Paketlerin içeriğindeki metinleri (ASCII) oku
sudo tcpdump -A -i eth0

# Hex + ASCII olarak incele
sudo tcpdump -X -i eth0
```

---

## 🎣 BPF Filtreleri (Berkeley Packet Filter)

### Ana Makine (Host) Filtreleri

```bash
# Belirli bir IP adresine giden veya gelen trafik
tcpdump host 192.168.1.1

# Yalnızca kaynak IP
tcpdump src host 192.168.1.1

# Yalnızca hedef IP
tcpdump dst host 192.168.1.1

# Birden çok IP
tcpdump host 192.168.1.1 or host 192.168.1.2

# Belirli bir IP'yi hariç tutma
tcpdump not host 192.168.1.1

# Alt ağ (Subnet) trafiği
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Port Filtreleri

```bash
# Belirli port (kaynak veya hedef)
tcpdump port 80
tcpdump port 443

# Kaynak port
tcpdump src port 443

# Hedef port
tcpdump dst port 22

# Port aralığı
tcpdump portrange 1-1024

# Birden çok port
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Protokol Filtreleri

```bash
# Protokole göre
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protokol ve port birleşimi
tcpdump tcp port 80
tcpdump udp port 53
```

### Mantıksal Operatörler

| Operatör | Alternatif | Açıklama |
|----------|------------|----------|
| `and` | `&&` | Mantıksal VE |
| `or` | `\|\|` | Mantıksal VEYA |
| `not` | `!` | Mantıksal DEĞİL |
| `()` | - | Gruplandırma parantezi |

### Bileşik Filtre Örnekleri

```bash
# Belirli ana makine VE port 80
tcpdump host 192.168.1.1 and port 80

# Parantezli karmaşık filtre
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# SSH ve DNS trafiğini hariç tut
tcpdump not port 22 and not port 53

# İki makine arasındaki doğrudan konuşma
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Protokol Filtreleri

### TCP Bayrakları (TCP Flags)

```bash
# SYN paketleri (Port taraması veya yeni bağlantı tespiti)
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# SYN-ACK paketleri
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# FIN paketleri
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# RST paketleri (Bağlantı sıfırlama / reddetme)
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# PSH paketleri (Veri taşıyan paketler)
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Yalnızca ACK paketi
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### TCP Bayrak Referans Tablosu

| Bayrak | Hex | Onluk (Dec) | tcpdump Sabiti |
|--------|-----|-------------|----------------|
| FIN | 0x01 | 1 | `tcp-fin` |
| SYN | 0x02 | 2 | `tcp-syn` |
| RST | 0x04 | 4 | `tcp-rst` |
| PSH | 0x08 | 8 | `tcp-push` |
| ACK | 0x10 | 16 | `tcp-ack` |
| URG | 0x20 | 32 | `tcp-urg` |

### HTTP Trafiği

```bash
# HTTP GET isteklerini filtreleme
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# HTTP POST isteklerini filtreleme
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Basit HTTP paket metin yakalama
tcpdump -A -s0 'tcp port 80'
```

### DNS Trafiği

```bash
# Tüm DNS trafiği
tcpdump port 53
tcpdump udp port 53

# Yalnızca giden DNS sorguları
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Yalnızca gelen DNS yanıtları
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### ICMP (Ping) Trafiği

```bash
# Tüm ICMP paketleri
tcpdump icmp

# Yalnızca Echo Request (Ping isteği)
tcpdump 'icmp[icmptype] == icmp-echo'

# Yalnızca Echo Reply (Ping cevabı)
tcpdump 'icmp[icmptype] == icmp-echoreply'

# Ulaşılamaz (Unreachable) hataları
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### ARP Trafiği

```bash
# Tüm ARP trafiği
tcpdump arp

# ARP İstekleri
tcpdump 'arp[6:2] == 1'

# ARP Yanıtları
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Çıktı ve Kayıt Seçenekleri

### Dosyaya Yazma (-w)

```bash
# Standart pcap kaydı
tcpdump -w capture.pcap

# Döngüsel kayıt (Maksimum 100 dosya, her biri 1MB)
tcpdump -w capture.pcap -W 100 -C 1

# Dosya adına zaman damgası ekleme
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Hem ekrana basıp hem dosyaya yazma
tcpdump -l -i eth0 | tee capture.txt
```

### Dosyadan Okuma (-r)

```bash
# Kayıtlı pcap dosyasını oku
tcpdump -r capture.pcap

# Okurken filtre uygulama
tcpdump -r capture.pcap 'host 192.168.1.1'

# Dosyadaki toplam paket sayısını öğren
tcpdump -r capture.pcap | wc -l
```

---

## 🎬 Gerçek Dünya Örnekleri

### Örnek 1: Web Trafiğini İzleme
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Örnek 2: DNS Sorgularını Yakalama
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Örnek 3: Belirli Bir Makinenin Tüm Trafiği
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Örnek 4: SSH Bağlantılarını Denetleme
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Örnek 5: SYN Port Taraması Tespiti
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Örnek 6: Şifresiz FTP Kimlik Bilgilerini Yakalama
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Örnek 7: Wireshark İçin Uzaktan Canlı Yakalama (SSH Pipe)
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Örnek 8: SSH Oturumunu Hariç Tutarak Dinleme
```bash
# Uzak sunucuda çalışırken kendi SSH oturumunuzun trafiği kirletmesini önleyin:
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Hızlı Başvuru

### Temel Komutlar Tablosu

| Görev | Komut |
|-------|-------|
| Arayüzleri listele | `tcpdump -D` |
| Temel yakalama | `tcpdump -i eth0` |
| DNS çözümlemesi yapma | `tcpdump -nn` |
| N adet paket yakala | `tcpdump -c 100` |
| Dosyaya kaydet | `tcpdump -w file.pcap` |
| Dosyadan oku | `tcpdump -r file.pcap` |
| ASCII gösterim | `tcpdump -A` |
| Hex + ASCII gösterim | `tcpdump -X` |
| Tam paket boyutu | `tcpdump -s0` |

### Sık Kullanılan BPF Filtreleri

| Filtre | Açıklama |
|--------|----------|
| `host 1.2.3.4` | Belirtilen IP ile ilgili tüm trafik |
| `port 80` | Port 80 üzerindeki trafik |
| `tcp` | Yalnızca TCP protokolü |
| `udp` | Yalnızca UDP protokolü |
| `icmp` | Yalnızca ICMP (ping vb.) |
| `net 192.168.1.0/24` | Belirtilen alt ağ trafiği |
| `not port 22` | SSH trafiğini hariç tut |

---

## 💡 İpuçları ve En İyi Uygulamalar

### Performans İpuçları

```bash
# DNS çözümlemesini kapatın (Çok daha hızlıdır ve sistemi yormaz)
tcpdump -nn

# Sadece başlıkları yakalayın (Disk alanından tasarruf sağlar)
tcpdump -s 96

# Filtreleri çekirdek seviyesinde tutun
tcpdump 'host 192.168.1.1'
```

### Yararlı Bash Kısayolları (Aliases)

```bash
# ~/.bashrc dosyasına ekleyebilirsiniz:
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ Yasal Uyarı

> **UYARI:** Ağ trafiğini izleme ve paket analizi işlemleri, yalnızca **yazılı izin alınmış ve yetkili olduğunuz ağlarda** yapılmalıdır. Yetkisiz ağ dinleme eylemleri yasalara aykırıdır ve cezai sorumluluk doğurur.

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [tcpdump Resmi Kılavuzu (Man Page)](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [tcpdump Resmi Web Sitesi](https://www.tcpdump.org/)
- [BPF Filtre Sözdizimi](https://biot.com/capstats/bpf.html)

### İlgili Kılavuzlar
- [Wireshark](../../Wireshark/translations/README.tr.md)
- [Nmap](../../Nmap/translations/README.tr.md)
- [Metasploit](../../Metasploit/translations/README.tr.md)

---

<p align="center">
  <b>📡 Komut Satırı Paket Analizinde Ustalaşın!</b><br>
  <i>Wireshark'ın sunuculardaki hafif ve vazgeçilmez alternatifi</i>
</p>
