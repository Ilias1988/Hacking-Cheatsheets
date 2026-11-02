# 🔍 Nmap - Kapsamlı Başvuru Kılavuzu

[🇬🇧 English](../../README.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - Ağ Tarayıcısı
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Ağ_Tarayıcısı-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Keşif-red?style=for-the-badge" alt="Keşif">
  <img src="https://img.shields.io/badge/Port_Taraması-green?style=for-the-badge" alt="Port Taraması">
  <img src="https://img.shields.io/badge/NSE_Betikleri-purple?style=for-the-badge" alt="NSE Betikleri">
</p>

<p align="center">
  <b>🌐 Dünyanın en güçlü ve yaygın kullanılan ağ tarama ve keşif aracı</b>
</p>

---

## 📋 İçindekiler

- [Giriş](#-giriş)
- [Kurulum](#-kurulum)
- [Temel Sözdizimi](#-temel-sözdizimi)
- [Hedef Belirleme](#-hedef-belirleme)
- [Ana Makine Keşfi (Host Discovery)](#-ana-makine-keşfi-host-discovery)
- [Port Tarama Teknikleri](#-port-tarama-teknikleri)
- [Port Belirleme](#-port-belirleme)
- [Servis ve Versiyon Tespiti](#-servis-ve-versiyon-tespiti)
- [İşletim Sistemi (OS) Tespiti](#-işletim-sistemi-os-tespiti)
- [Zamanlama ve Performans (Timing)](#-zamanlama-ve-performans-timing)
- [NSE Betikleri (Nmap Scripting Engine)](#-nse-betikleri-nmap-scripting-engine)
- [Çıktı Biçimleri (Output Formats)](#-çıktı-biçimleri-output-formats)
- [Güvenlik Duvarı / IDS Atlatma (Evasion)](#-güvenlik-duvarı--ids-atlatma-evasion)
- [Gerçek Dünya Örnek Senaryoları](#-gerçek-dünya-örnek-senaryoları)
- [Hızlı Başvuru Tablosu](#-hızlı-başvuru-tablosu)
- [İpuçları ve En İyi Uygulamalar](#-ipuçları-ve-en-iyi-uygulamalar)
- [Kaynaklar](#-kaynaklar)

---

## 🎯 Giriş

**Nmap** (Network Mapper), ağ keşfi ve güvenlik denetimi için geliştirilmiş ücretsiz ve açık kaynaklı bir yardımcı programdır. Ağ taraması ve haritalandırmasında siber güvenlik endüstrisinin fiili standardıdır.

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Ana Makine Keşfi** | Ağ üzerindeki aktif/canlı cihazları tespit etme |
| **Port Taraması** | Açık portları ve dinleyen servisleri belirleme |
| **Versiyon Tespiti** | Servislerin ve uygulamaların tam sürümlerini öğrenme |
| **OS Tespiti** | Hedef cihazın işletim sistemi ve donanımını saptama |
| **NSE Betikleri** | Gelişmiş zafiyet taraması, keşif ve otomatik istismar |
| **Çıktı Biçimleri** | XML, grepable, metin ve standart raporlama |

### Port Durumları (Port States)

| Durum | Açıklama |
|-------|----------|
| `open` | Uygulama bağlantıları kabul ediyor, port açık |
| `closed` | Port erişilebilir ancak dinleyen bir servis yok |
| `filtered` | Güvenlik duvarı/filtre Nmap'in port durumunu belirlemesini engelliyor |
| `unfiltered` | Porta erişilebiliyor ancak açık veya kapalı olduğu kesinleştirilemedi |
| `open\|filtered` | Portun açık mı yoksa filtrelenmiş mi olduğu ayırt edilemiyor |
| `closed\|filtered` | Portun kapalı mı yoksa filtrelenmiş mi olduğu ayırt edilemiyor |

---

## 📥 Kurulum

### Kali Linux (Ön Yüklü Gelir)
```bash
# Nmap paketini güncelleme
sudo apt update && sudo apt install nmap
```

### Ubuntu / Debian
```bash
sudo apt install nmap
```

### CentOS / RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# https://nmap.org/download.html adresinden yükleyiciyi indirin
# Veya Chocolatey kullanarak:
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Kurulumu Doğrulama
```bash
nmap --version
nmap -h          # Yardım menüsü
```

---

## ⌨️ Temel Sözdizimi

### Genel Komut Yapısı
```bash
nmap [Tarama Türü] [Seçenekler] {hedef}
```

### Hızlı Başlangıç Örnekleri
```bash
# Tek bir ana makineyi temel tarama
nmap 192.168.1.1

# Birden fazla ana makineyi tarama
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Belirli bir IP aralığını tarama
nmap 192.168.1.1-254

# Tüm alt ağı (subnet) tarama
nmap 192.168.1.0/24

# Hedef listesini dosyadan okuyarak tarama
nmap -iL hedefler.txt
```

---

## 🎯 Hedef Belirleme

### Tek Hedefler
```bash
# IP adresi
nmap 192.168.1.1

# Ana makine adı (Hostname)
nmap scanme.nmap.org

# Alan adı (Domain)
nmap example.com
```

### Çoklu Hedefler
```bash
# Boşlukla ayrılmış
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Aralık belirterek
nmap 192.168.1.1-100

# CIDR notasyonu
nmap 192.168.1.0/24

# Joker karakter (Wildcard)
nmap 192.168.1.*

# Oktet aralığı
nmap 192.168.0-255.1-254

# Karışık hedefler
nmap 192.168.1.0/24 10.0.0.1
```

### Dosyadan Hedef Alma
```bash
# Dosyadan okuma
nmap -iL hedefler.txt

# hedefler.txt içeriği:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Hedefleri Hariç Tutma (Exclude)
```bash
# Tek bir ana makineyi hariç tutma
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Dosyadaki hedefleri hariç tutma
nmap 192.168.1.0/24 --excludefile haric_tutulanlar.txt
```

### Rastgele Hedefler
```bash
# Rastgele 100 ana makineyi tara (araştırma amaçlı)
nmap -iR 100
```

---

## 🔎 Ana Makine Keşfi (Host Discovery)

### Keşif Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-sL` | Liste taraması - Yalnızca hedefleri listeler, paket göndermez |
| `-sn` | Ping taraması - Port taraması yapmaz, sadece canlı makineleri bulur |
| `-Pn` | Ana makine keşfini atlar (tüm hedefleri aktif kabul eder) |
| `-PS` | TCP SYN ping gönderir |
| `-PA` | TCP ACK ping gönderir |
| `-PU` | UDP ping gönderir |
| `-PE` | ICMP Echo (yankı) ping |
| `-PP` | ICMP Timestamp (zaman damgası) ping |
| `-PM` | ICMP Netmask (ağ maskesi) ping |
| `-PO` | IP Protokol ping |
| `-PR` | ARP ping (yerel yerel ağ için en hızlısı) |

### Ping Taraması (Port Taraması Olmadan Canlı Cihaz Keşfi)
```bash
# Alt ağdaki canlı cihazları tespit etme
nmap -sn 192.168.1.0/24

# Dosyadan liste ile ping taraması
nmap -sn -iL hosts.txt
```

### Ana Makine Keşfini Atlama (-Pn)
```bash
# Ping göndermeden doğrudan port taramasına geç
nmap -Pn 192.168.1.1

# Kullanım durumları:
# - Hedef ICMP ping paketlerini engelliyorsa
# - Güvenlik duvarı arkası taranıyorsa
```

### TCP SYN/ACK Ping
```bash
# Port 80 üzerine TCP SYN ping
nmap -PS80 192.168.1.1

# Port 80 üzerine TCP ACK ping
nmap -PA80 192.168.1.1

# Çoklu portlar üzerinden ping
nmap -PS22,80,443 192.168.1.1
```

### UDP Ping
```bash
# UDP ping (DNS portu üzerinden)
nmap -PU53 192.168.1.1

# Çoklu UDP portları
nmap -PU53,161 192.168.1.1
```

### ICMP Ping
```bash
# ICMP Echo
nmap -PE 192.168.1.1

# ICMP Zaman damgası
nmap -PP 192.168.1.1

# ICMP Ağ maskesi
nmap -PM 192.168.1.1
```

### ARP Ping (Yerel Ağ)
```bash
# Yerel ağda ARP taraması (en hızlı ve güvenilir keşif)
nmap -PR 192.168.1.0/24

# Sadece ARP ping taraması
nmap -sn -PR 192.168.1.0/24
```

### DNS Çözümlemesini Kapatma / Zorlama
```bash
# DNS çözümlemesi yapma (taramayı ciddi ölçüde hızlandırır)
nmap -n 192.168.1.1

# Her zaman DNS çözümlemesi yap
nmap -R 192.168.1.1
```

---

## 🔌 Port Tarama Teknikleri

### Tarama Türleri Genel Bakış

| Seçenek | Adı | Açıklama |
|---------|-----|----------|
| `-sS` | SYN Taraması | Gizli/Stealth tarama (varsayılan, root yetkisi gerektirir) |
| `-sT` | TCP Connect | Tam TCP el sıkışması (root yetkisi gerektirmez) |
| `-sU` | UDP Taraması | UDP portlarını tarar |
| `-sA` | ACK Taraması | Güvenlik duvarı kurallarını haritalandırır |
| `-sW` | Window Taraması | ACK taramasına benzer, pencere alanını analiz eder |
| `-sM` | Maimon Taraması | FIN/ACK paketleri ile test |
| `-sN` | NULL Taraması | Hiçbir bayrak (flag) ayarlanmaz |
| `-sF` | FIN Taraması | Yalnızca FIN bayrağı ayarlanır |
| `-sX` | Xmas Taraması | FIN, PSH ve URG bayrakları ayarlanır |

### SYN Gizli Tarama (Varsayılan ve En Çok Tercih Edilen)
```bash
# En yaygın tarama (root yetkisi gerektirir)
sudo nmap -sS 192.168.1.1

# TCP el sıkışmasını hiçbir zaman tamamlamaz (RST ile keser)
# Hızlıdır ve loglara daha az takılır
```

### TCP Connect Taraması
```bash
# Tam TCP bağlantısı kurar
nmap -sT 192.168.1.1

# Root yetkisi gerekmez
# Hedef loglarında kolayca görünür ancak her zaman çalışır
```

### UDP Taraması
```bash
# UDP portlarını tarama
sudo nmap -sU 192.168.1.1

# UDP taramaları YAVAŞTIR
# Versiyon tespiti ile birleştirmek önerilir:
sudo nmap -sU -sV 192.168.1.1

# Yalnızca yaygın kritik UDP portlarını tarama
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Birleşik TCP ve UDP Taraması
```bash
# Hem TCP hem UDP portlarını birlikte tara
sudo nmap -sS -sU 192.168.1.1

# Belirli portlar için:
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### NULL, FIN ve Xmas Taramaları
```bash
# NULL taraması (hiç bayrak yok)
sudo nmap -sN 192.168.1.1

# FIN taraması (yalnızca FIN)
sudo nmap -sF 192.168.1.1

# Xmas taraması (FIN, PSH, URG bayrakları açık)
sudo nmap -sX 192.168.1.1

# Kullanım amacı:
# - Durumsuz (stateless) güvenlik duvarlarını atlatmak
# - Windows sistemlerde RFC uyumsuzluğundan dolayı doğru sonuç vermez
```

### ACK Taraması
```bash
# Güvenlik duvarı filtreleme kurallarını saptama
sudo nmap -sA 192.168.1.1

# Çıktı portun açık/kapalı olduğunu değil, "filtered" veya "unfiltered" olduğunu gösterir
```

### Window Taraması
```bash
# TCP Pencere boyutuna göre analiz
sudo nmap -sW 192.168.1.1
```

### Idle / Zombie Taraması
```bash
# En üst düzey gizlilik - üçüncü taraf bir zombi makine üzerinden tarama
sudo nmap -sI zombi_ip 192.168.1.1

# Kendi IP adresiniz hedef sisteme kesinlikle temas etmez
# Uygun IP ID artışına sahip bir zombi cihaz gerektirir
```

---

## 🎚️ Port Belirleme

### Port Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-p <port>` | Belirli port veya portları tara |
| `-p-` | Tüm 65.535 portu eksiksiz tara |
| `-p 1-1000` | Port aralığını tara |
| `--top-ports <n>` | En popüler N portu tara |
| `-F` | Hızlı tarama (en popüler 100 port) |
| `-r` | Portları rastgele değil sıralı tara |

### Belirli Portlar
```bash
# Tek port
nmap -p 80 192.168.1.1

# Çoklu port
nmap -p 80,443,22 192.168.1.1

# Port aralığı
nmap -p 1-1000 192.168.1.1

# Tüm portlar (1-65535)
nmap -p- 192.168.1.1

# Karışık port tanımı
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Protokole Göre Port Belirleme
```bash
# TCP portları
nmap -p T:80,443 192.168.1.1

# UDP portları
nmap -p U:53,161 192.168.1.1

# Birlikte
nmap -p T:80,443,U:53 192.168.1.1
```

### En Popüler Portlar
```bash
# En yaygın 100 port (Hızlı)
nmap -F 192.168.1.1

# En yaygın 10 port
nmap --top-ports 10 192.168.1.1

# En yaygın 1000 port (Nmap varsayılanı)
nmap --top-ports 1000 192.168.1.1
```

### Yaygın Portlar Referans Tablosu

| Port | Servis | Açıklama |
|------|--------|----------|
| 21 | FTP | Dosya Aktarım Protokolü |
| 22 | SSH | Güvenli Kabuk Bağlantısı |
| 23 | Telnet | Şifrelenmemiş Uzak Bağlantı |
| 25 | SMTP | E-Posta İletimi |
| 53 | DNS | Alan Adı Çözümleme |
| 80 | HTTP | Web Sunucusu |
| 110 | POP3 | E-Posta Alma |
| 135 | MSRPC | Microsoft RPC Servisi |
| 139 | NetBIOS | NetBIOS Oturum Servisi |
| 143 | IMAP | E-Posta Alma Protokolü |
| 443 | HTTPS | Güvenli Web Sunucusu (TLS/SSL) |
| 445 | SMB | Dosya Paylaşımı / Active Directory |
| 993 | IMAPS | Güvenli IMAP |
| 995 | POP3S | Güvenli POP3 |
| 1433 | MSSQL | Microsoft SQL Server |
| 1521 | Oracle | Oracle Veritabanı |
| 3306 | MySQL | MySQL / MariaDB Veritabanı |
| 3389 | RDP | Uzak Masaüstü Bağlantısı |
| 5432 | PostgreSQL | PostgreSQL Veritabanı |
| 5900 | VNC | Sanal Ağ Hesaplama (Uzak Masaüstü) |
| 8080 | HTTP-Proxy | Alternatif Web Portu / Proxy |

---

## 🔬 Servis ve Versiyon Tespiti

### Versiyon Tespiti Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-sV` | Servis/versiyon tespitini etkinleştirir |
| `--version-intensity <0-9>` | Prob sorgulama yoğunluğu (0: hafif, 9: en detaylı) |
| `--version-light` | Hafif tarama (yoğunluk 2, daha hızlı) |
| `--version-all` | Tüm probları dener (yoğunluk 9, kapsamlı) |
| `--version-trace` | Hata ayıklama için versiyon izleme çıktıları üretir |

### Temel Versiyon Tespiti
```bash
# Servis versiyonlarını belirleme
nmap -sV 192.168.1.1

# Çıktı örneği:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Versiyon Yoğunluğu (Intensity)
```bash
# Hafif tarama (hızlı)
nmap -sV --version-light 192.168.1.1

# Tüm probları deneme (derinlemesine analiz)
nmap -sV --version-all 192.168.1.1

# Özel yoğunluk değeri (0-9 arası)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 İşletim Sistemi (OS) Tespiti

### OS Tespiti Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-O` | İşletim sistemi tespitini etkinleştirir |
| `--osscan-limit` | Yalnızca uygun port durumuna sahip makinelerde dener |
| `--osscan-guess` | OS tespitinde daha agresif tahmin yürütür |
| `--max-os-tries` | Maksimum deneme sayısı |

### Temel OS Tespiti
```bash
# İşletim sistemini tespit et (root yetkisi gerektirir)
sudo nmap -O 192.168.1.1

# Çıktı örneği:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Agresif OS Tahmini
```bash
# Şüphede kalınan durumlarda agresif tahmin
sudo nmap -O --osscan-guess 192.168.1.1
```

### Kapsamlı Agresif Tarama (-A)
```bash
# OS tespiti, versiyon tespiti, varsayılan betikler ve traceroute birlikte çalışır
sudo nmap -A 192.168.1.1

# Şu komutun eşdeğeridir: -O -sV -sC --traceroute
```

---

## ⏱️ Zamanlama ve Performans (Timing)

### Zamanlama Şablonları (Timing Templates)

| Seçenek | Adı | Açıklama |
|---------|-----|----------|
| `-T0` | Paranoid | IDS atlatma, son derece yavaş (seri gönderim) |
| `-T1` | Sneaky | IDS atlatma, yavaş |
| `-T2` | Polite | Az bant genişliği tüketir, yavaş |
| `-T3` | Normal | Nmap'in varsayılan dengeli modu |
| `-T4` | Aggressive | Hızlı, modern ve kararlı ağlar için idealdir |
| `-T5` | Insane | Çok hızlı, paket kaybı riski vardır, port kaçırabilir |

### Kullanım
```bash
# Yavaş ve gizli
nmap -T1 192.168.1.1

# Normal (varsayılan)
nmap -T3 192.168.1.1

# Hızlı (önerilen)
nmap -T4 192.168.1.1

# Çok hızlı (yerel ağ)
nmap -T5 192.168.1.1
```

### İnce Ayar Zamanlama Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `--min-rate <n>` | Saniye başına minimum gönderilecek paket sayısı |
| `--max-rate <n>` | Saniye başına maksimum gönderilecek paket sayısı |
| `--min-parallelism <n>` | Minimum paralel prob sayısı |
| `--max-parallelism <n>` | Maksimum paralel prob sayısı |
| `--min-hostgroup <n>` | Paralel taranacak minimum makine sayısı |
| `--max-hostgroup <n>` | Paralel taranacak maksimum makine sayısı |
| `--host-timeout <süre>` | Belirtilen sürede yanıt vermeyen hedefi atla |
| `--scan-delay <süre>` | Problar arasına konulacak bekleme süresi |
| `--max-retries <n>` | Paket tekrar deneme üst limiti |

```bash
# Saniyede en az 1000 paket gönder
nmap --min-rate 1000 192.168.1.0/24

# Saniyede en fazla 100 paket gönder (hızı düşür / bant genişliğini koru)
nmap --max-rate 100 192.168.1.1

# 30 dakika içinde bitmezse hedefi geç
nmap --host-timeout 30m 192.168.1.0/24

# Her prob arasına 1 saniye gecikme koy
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 NSE Betikleri (Nmap Scripting Engine)

### Betik Kategorileri

| Kategori | Açıklama |
|----------|----------|
| `auth` | Kimlik doğrulama kontrolleri ve atlatma |
| `broadcast` | Yerel ağda broadcast ile makine keşfi |
| `brute` | Kaba kuvvet parola denemeleri |
| `default` | Temel ve güvenli varsayılan betikler (-sC) |
| `discovery` | Servis ve ağ detay keşfi |
| `dos` | Hizmet dışı bırakma testleri |
| `exploit` | Zafiyetleri doğrudan istismar etme |
| `external` | Harici kaynak ve veritabanlarına sorgu atma |
| `fuzzer` | Girdi testleri ve fuzzing |
| `intrusive` | Servisleri çökertme riski olan agresif betikler |
| `malware` | Zararlı yazılım ve arka kapı tespiti |
| `safe` | Servisleri bozmayan güvenli betikler |
| `version` | Gelişmiş versiyon tespiti |
| `vuln` | Bilinen zafiyetleri ve CVE'leri tarama |

### Betik Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-sC` | Varsayılan (default) betikleri çalıştırır |
| `--script <betikler>` | Belirtilen betik veya kategoriyi çalıştırır |
| `--script-args <argümanlar>` | Betiklere parametre geçirme |
| `--script-updatedb` | NSE veritabanını günceller |
| `--script-help <betik>` | Betik yardım ve kullanım bilgilerini gösterir |

### Varsayılan Betikler
```bash
# Varsayılan betikleri çalıştır
nmap -sC 192.168.1.1

# Versiyon tespiti ile birlikte (en çok kullanılan pratik yöntem)
nmap -sV -sC 192.168.1.1
```

### Özel Betikleri Çalıştırma
```bash
# Tek bir betik
nmap --script http-title 192.168.1.1

# Birden fazla betik
nmap --script http-title,http-headers 192.168.1.1

# Belirli bir kategori
nmap --script vuln 192.168.1.1

# Joker karakter ile eşleşenler
nmap --script "http-*" 192.168.1.1

# Mantıksal operatörler ile kategorileri birleştirme
nmap --script "vuln and safe" 192.168.1.1

# Belirli kategoriyi hariç tutma
nmap --script "not intrusive" 192.168.1.1
```

### Popüler NSE Betikleri

#### Zafiyet Taraması
```bash
# Tüm zafiyet kontrol betikleri
nmap --script vuln 192.168.1.1

# EternalBlue (MS17-010) kontrolü
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed SSL zafiyeti
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock zafiyeti
nmap --script http-shellshock 192.168.1.1
```

#### HTTP / Web Betikleri
```bash
# HTTP dizin ve dosya numaralandırma
nmap --script http-enum 192.168.1.1

# HTTP başlıklarını çekme
nmap --script http-headers 192.168.1.1

# İzin verilen HTTP metotları
nmap --script http-methods 192.168.1.1

# Sayfa başlığını alma
nmap --script http-title 192.168.1.1

# robots.txt dosyasını okuma
nmap --script http-robots.txt 192.168.1.1
```

#### SMB Betikleri
```bash
# Paylaşımları listeleme
nmap --script smb-enum-shares 192.168.1.1

# Kullanıcıları listeleme
nmap --script smb-enum-users 192.168.1.1

# SMB üzerinden işletim sistemi tespiti
nmap --script smb-os-discovery 192.168.1.1

# Bilinen SMB zafiyetlerini kontrol etme
nmap --script smb-vuln* 192.168.1.1
```

#### SSH Betikleri
```bash
# Desteklenen kimlik doğrulama metotları
nmap --script ssh-auth-methods 192.168.1.1

# SSH anahtar parmak izi
nmap --script ssh-hostkey 192.168.1.1

# SSH kaba kuvvet saldırısı
nmap --script ssh-brute 192.168.1.1
```

#### DNS Betikleri
```bash
# DNS alt alan adı kaba kuvveti
nmap --script dns-brute example.com

# DNS Bölge Transferi (Zone Transfer) testi
nmap --script dns-zone-transfer example.com
```

#### MySQL / Veritabanı Betikleri
```bash
# MySQL sunucu bilgisi
nmap --script mysql-info 192.168.1.1

# Veritabanı ve kullanıcı listeleme
nmap --script mysql-enum 192.168.1.1

# MySQL kaba kuvvet
nmap --script mysql-brute 192.168.1.1
```

### Betik Argümanları Kullanımı
```bash
# Özel kelime listesi ile kaba kuvvet
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Özel yol belirterek HTTP kimlik doğrulama testi
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Çıktı Biçimleri (Output Formats)

### Çıktı Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| `-oN <dosya>` | Standart okunabilir metin çıktısı |
| `-oX <dosya>` | XML çıktısı (araçlar ve otomasyon için) |
| `-oG <dosya>` | Grepable çıktı (grep ve awk ile ayrıştırmaya uygun) |
| `-oA <ad>` | Tüm temel formatlarda aynı anda kaydeder (.nmap, .xml, .gnmap) |
| `-oS <dosya>` | Script Kiddie formatında eğlenceli çıktı |

### Çıktıyı Dosyaya Kaydetme
```bash
# Standart metin çıktısı
nmap -oN tarama.txt 192.168.1.1

# XML çıktısı
nmap -oX tarama.xml 192.168.1.1

# Grepable çıktı
nmap -oG tarama.gnmap 192.168.1.1

# Tüm formatlarda aynı anda kaydetme (en çok tavsiye edilen)
nmap -oA tarama_raporu 192.168.1.1
# tarama_raporu.nmap, tarama_raporu.xml ve tarama_raporu.gnmap dosyalarını üretir
```

### Ayrıntı Düzeyi ve Hata Ayıklama (Verbosity)

| Seçenek | Açıklama |
|---------|----------|
| `-v` | Ayrıntı düzeyini artırır |
| `-vv` | Çok daha detaylı çıktı üretir |
| `-d` | Hata ayıklama modunu açar |
| `-dd` | Derin hata ayıklama modu |
| `--reason` | Portun neden o durumda olduğunu gösterir |
| `--open` | Yalnızca açık portları listeler |
| `--packet-trace` | Gönderilen ve alınan tüm paketleri ekrana basar |

```bash
# Ayrıntılı tarama
nmap -v 192.168.1.1

# Çok ayrıntılı tarama
nmap -vv 192.168.1.1

# Yalnızca açık portları göster
nmap --open 192.168.1.1

# Port durum nedenini göster
nmap --reason 192.168.1.1
```

---

## 🛡️ Güvenlik Duvarı / IDS Atlatma (Evasion)

### Atlatma Teknikleri Tablosu

| Seçenek | Açıklama |
|---------|----------|
| `-f` | Paketleri küçük parçalara (fragment) böler |
| `--mtu <boyut>` | Özel MTU boyutu belirler (8'in katı olmalıdır) |
| `-D <yemler>` | Sahte yem IP'leri (Decoys) arasına kendi IP'sini gizler |
| `-S <IP>` | Sahte kaynak IP adresi tanımlar |
| `--source-port <port>` | Sahte kaynak portu tanımlar (ör. 53, 80) |
| `--data-length <n>` | Paketlerin sonuna rastgele bayt verisi ekler |
| `--randomize-hosts` | Hedef makinelerin taranma sırasını karıştırır |
| `--spoof-mac <mac>` | Sahte kaynak MAC adresi kullanır |
| `--badsum` | Hatalı TCP/UDP sağlama toplamı gönderir |

### Paket Parçalama (Fragmentation)
```bash
# Paketleri 8 baytlık parçalara böl
nmap -f 192.168.1.1

# Özel MTU boyutu (16 bayt)
nmap --mtu 16 192.168.1.1
```

### Sahte Yem IP'leri (Decoys)
```bash
# Belirtilen IP'ler ile kendi IP'nizi karıştırın
nmap -D yem1,yem2,ME 192.168.1.1

# Rastgele 5 sahte yem IP üret
nmap -D RND:5 192.168.1.1
```

### Kaynak IP ve Port Sahteciliği (Spoofing)
```bash
# Kaynak IP'yi değiştirme (yanıtları alamazsınız, kör tarama)
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Kaynak portunu güvenli bilinen porta ayarlama (Güvenlik duvarlarını aşabilir)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### MAC Adresi Sahteciliği
```bash
# Rastgele MAC adresi üret
nmap --spoof-mac 0 192.168.1.1

# Belirli bir üretici imzası taklit etme
nmap --spoof-mac Apple 192.168.1.1

# Özel MAC adresi belirleme
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Veri Doldurma (Data Padding)
```bash
# İmzaları yanıltmak için paketlere 25 bayt rastgele veri ekle
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Gerçek Dünya Örnek Senaryoları

### Senaryo 1: Hızlı Ağ Taraması (Canlı Cihaz Keşfi)
```bash
# Alt ağdaki aktif cihazları hızlıca bul
nmap -sn 192.168.1.0/24
```

### Senaryo 2: Temel Port ve Servis Taraması
```bash
# Versiyon tespiti ile popüler 1000 port
nmap -sV 192.168.1.1
```

### Senaryo 3: Kapsamlı Güvenlik Denetimi Taraması
```bash
# SYN, versiyon, OS tespiti ve varsayılan betikler birlikte
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Senaryo 4: Agresif Tam Port Taraması
```bash
# Tüm 65.535 port üzerinde agresif analiz
sudo nmap -A -T4 -p- 192.168.1.1
```

### Senaryo 5: Zafiyet Taraması
```bash
# Hedefteki bilinen açıkları denetle
nmap --script vuln 192.168.1.1
```

### Senaryo 6: Gizli (Stealth) Tarama
```bash
# Tespit edilme olasılığını en aza indiren tarama
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Senaryo 7: Web Sunucusu Analizi
```bash
# Web portlarını ve ilgili tüm HTTP betiklerini çalıştır
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Senaryo 8: SMB Servisi Numaralandırması
```bash
# Windows/Samba SMB açıklarını ve paylaşımlarını analiz et
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Senaryo 9: Tüm Alt Ağın Ayrıntılı Raporlanması
```bash
# Açık portları bul, versiyonları çıkart ve tüm formatlarda kaydet
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA altag_raporu
```

### Senaryo 10: Kritik UDP Servislerinin Taranması
```bash
# En sık zafiyet barındıran UDP portlarını incele
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Hızlı Başvuru Tablosu

### Temel Komutlar

| Komut | Açıklama |
|-------|----------|
| `nmap <hedef>` | Temel varsayılan tarama |
| `nmap -sn <hedef>` | Yalnızca canlı makine ping taraması |
| `nmap -Pn <hedef>` | Ping kontrolünü atla, portları tara |
| `nmap -sS <hedef>` | SYN gizli tarama (root) |
| `nmap -sV <hedef>` | Servis ve sürüm tespiti |
| `nmap -O <hedef>` | İşletim sistemi tespiti |
| `nmap -A <hedef>` | Agresif tarama (OS, Sürüm, Betik, Traceroute) |
| `nmap -sC <hedef>` | Varsayılan NSE betikleri |
| `nmap -p- <hedef>` | Tüm 65.535 portu tarama |
| `nmap --script vuln <hedef>` | Zafiyet taraması çalıştırma |

### Hız ve Gizlilik Dengesi

| İhtiyaç | Tercih Edilen Seçenekler |
|---------|-------------------------|
| **Yüksek Hız** | `-T4 --min-rate 1000` |
| **Maksimum Gizlilik** | `-T1 -f --data-length 25` |
| **Dengeli Standart** | `-T3` (varsayılan) |

### Sıkça Kullanılan Komut Kalıpları
```bash
# Hızlı tarama
nmap -F 192.168.1.1

# Tam TCP taraması
nmap -sT -p- 192.168.1.1

# Detaylı servis taraması
nmap -sV 192.168.1.1

# Zafiyet analizi
nmap --script vuln 192.168.1.1

# Eksiksiz tam tarama
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 İpuçları ve En İyi Uygulamalar

### Performans İpuçları

1. **Her Zaman SYN Taraması Tercih Edin**
   ```bash
   # Root gerektirir ancak TCP Connect'e göre çok daha hızlıdır
   sudo nmap -sS 192.168.1.1
   ```

2. **İlk Aşamada Port Aralığını Daraltın**
   ```bash
   # Hızlı bir ön keşif için popüler portlarla başlayın
   nmap --top-ports 100 192.168.1.1
   ```

3. **Yerel Ağda Hız Ayarlarını Yükseltin**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Doğruluk İpuçları

1. **Farklı Tarama Türlerini Birlikte Deneyin**
   ```bash
   # Hem TCP hem UDP servislerini inceleyin
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Paket Kaybı Olan Ağlarda Tekrar Sayısını Artırın**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

---

## ⚠️ Yasal Uyarı

> **ÖNEMLİ:** Nmap oldukça güçlü bir güvenlik aracıdır ve yalnızca **yazılı izin alınmış sistemlerde** veya **sahibi olduğunuz ağlarda** kullanılmalıdır.
> 
> - ✅ Sahibi olduğunuz kişisel ağlar
> - ✅ İnceleme amaçlı laboratuvar ortamları
> - ✅ Yazılı izin alınmış yetkili sızma testleri
> - ❌ İnternetteki rastgele sistemleri taramak
> - ❌ İzniniz olmayan şirket veya kurum ağları
> 
> **Yetkisiz ağ taramaları birçok yargı bölgesinde yasal suç teşkil edebilir.**

---

## 📚 Kaynaklar

### Resmi Kaynaklar
- [Nmap Resmi Web Sitesi](https://nmap.org/)
- [Nmap Başvuru Kılavuzu (Man Page)](https://nmap.org/book/man.html)
- [NSE Betik Dokümantasyonu](https://nmap.org/nsedoc/)

### Eğitim Kaynakları
- [Nmap Network Scanning Kitabı](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### İlgili Diğer Kılavuzlar
- [Metasploit Framework](../../Metasploit/translations/README.tr.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 Ağ Keşif Sanatında Ustalaşın!</b><br>
  <i>Ağınızı saldırganlardan önce tanıyın ve güvenceye alın</i>
</p>
