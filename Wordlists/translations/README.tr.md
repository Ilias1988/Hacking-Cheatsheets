[🇬🇧 English](../../README.md)

# 📚 Kelime Listeleri (Wordlists) - Kapsamlı Başvuru Kılavuzu

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Sızma testleri ve güvenlik denetimleri için eksiksiz kelime listesi rehberi</b>
</p>

---

## 📋 İçindekiler

- [Parola Kelime Listeleri](#-parola-kelime-listeleri)
- [Dizin ve Dosya Kelime Listeleri](#-dizin-ve-dosya-kelime-listeleri)
- [Alt Alan Adı Kelime Listeleri](#-alt-alan-adı-kelime-listeleri)
- [Kullanıcı Adı Kelime Listeleri](#-kullanıcı-adı-kelime-listeleri)
- [Web Fuzzing Kelime Listeleri](#-web-fuzzing-kelime-listeleri)
- [SecLists Koleksiyonu](#-seclists-koleksiyonu)
- [Kali Varsayılan Kelime Listeleri](#-kali-varsayılan-kelime-listeleri)
- [Özel Kelime Listesi Üretim Araçları](#-özel-kelime-listesi-üretim-araçları)
- [Hızlı İndirme Komutları](#-hızlı-i̇ndirme-komutları)
- [İpuçları ve En İyi Pratikler](#-ipuçları-ve-en-iyi-pratikler)
- [Hızlı Başvuru](#-hızlı-başvuru)

---

## 🔐 Parola Kelime Listeleri

### En Popüler Parola Listeleri

| Kelime Listesi | Boyut | Satır Sayısı | Kaynak | Kullanım Alanı |
|----------------|-------|--------------|--------|----------------|
| **rockyou.txt** | 139 MB | 14.3M | Kali / SecLists | Parola kırma (hash cracking) |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | En yaygın parolalar |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Büyük kapsamlı parola listesi |
| **common-passwords-win.txt** | - | - | SecLists | Windows parola politikaları |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | İlk 1 milyon parola |

### Türlerine Göre Parola Listeleri

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Özelleşmiş Parola Listeleri

| Kelime Listesi | Amaç | Konum |
|----------------|------|-------|
| **wifi-passwords.txt** | Kablosuz ağ parolaları | `SecLists/Passwords/` |
| **probable-v2-top12000.txt** | İstatistiki parola analizi | `SecLists/Passwords/` |
| **seasons-only.txt** | Mevsim ve yıl kalıpları | `SecLists/Passwords/Honeypot-Captures/` |
| **twitter-banned.txt** | Yasaklanmış yaygın şifreler | `SecLists/Passwords/` |
| **cirt-default-passwords.txt** | Donanım varsayılan şifreleri | `SecLists/Passwords/Default-Credentials/` |

### Kali Linux Dosya Yolları

```bash
# RockYou (önce arşivden çıkarın)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper varsayılan listesi
/usr/share/john/password.lst

# Metasploit kelime listeleri
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Dizin ve Dosya Kelime Listeleri

### Dizin Kaba Kuvveti (Directory Brute-Forcing)

| Kelime Listesi | Boyut | Satır Sayısı | Kaynak | Kullanım Alanı |
|----------------|-------|--------------|--------|----------------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Hızlı genel tarama |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Küçük/orta ölçekli tarama |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Standart ve önerilen tarama |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Kapsamlı derin tarama |
| **big.txt** | 210 KB | 20K | SecLists | Yaygın dizinler |

### RAFT Serisi (Şiddetle Tavsiye Edilir!)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K dizin
├── raft-large-files.txt            # 37K dosya
├── raft-large-words.txt            # 120K kelime
├── raft-medium-directories.txt     # 30K dizin
├── raft-medium-files.txt           # 17K dosya
├── raft-medium-words.txt           # 63K kelime
├── raft-small-directories.txt      # 20K dizin
├── raft-small-files.txt            # 11K dosya
└── raft-small-words.txt            # 43K kelime
```

### Dosya Uzantısı Listeleri

| Kelime Listesi | Amaç |
|----------------|------|
| **web-extensions.txt** | Yaygın web uzantıları |
| **backup-extensions.txt** | Yedek dosyaları (.bak, .old, .zip) |
| **raft-large-extensions.txt** | Geniş uzantı listesi |

### Servise Özel Keşif Listeleri

| Kelime Listesi | Amaç |
|----------------|------|
| **apache.txt** | Apache sunucusuna özel dosyalar |
| **nginx.txt** | Nginx sunucusuna özel yollar |
| **iis.txt** | Microsoft IIS yapılandırmaları |
| **api-endpoints.txt** | REST API uç noktaları |
| **graphql.txt** | GraphQL sorgu ve uç noktaları |
| **swagger.txt** | Swagger ve OpenAPI belgeleri |

---

## 🌐 Alt Alan Adı Kelime Listeleri

### Popüler Alt Alan Adı Listeleri

| Kelime Listesi | Satır Sayısı | Kaynak | Kullanım Alanı |
|----------------|--------------|--------|----------------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Hızlı keşif |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Standart keşif |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Detaylı keşif |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty için en iyisi |
| **fierce-hostlist.txt** | - | SecLists | Fierce aracı uyumlu |
| **combined_subdomains.txt** | 648K | SecLists | Birleştirilmiş küme |

### Alt Alan Adı Listeleri Konumu

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Bug bounty için en popüler liste
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

---

## 👤 Kullanıcı Adı Kelime Listeleri

### Yaygın Kullanıcı Adı Listeleri

| Kelime Listesi | Satır Sayısı | Amaç |
|----------------|--------------|------|
| **top-usernames-shortlist.txt** | 17 | Hızlı kaba kuvvet kontrolü |
| **xato-net-10-million-usernames.txt** | 10M | Büyük kullanıcı adı listesi |
| **cirt-default-usernames.txt** | - | Donanım varsayılan kullanıcıları |
| **names.txt** | - | Yaygın kişi adları |
| **familynames-usa-top1000.txt** | 1K | Yaygın soyadlar |

### Servislere Özel Varsayılan Kimlik Bilgileri

| Servis | Kelime Listesi |
|--------|----------------|
| **FTP** | `ftp-betterdefaultpasslist.txt` |
| **SSH** | `ssh-betterdefaultpasslist.txt` |
| **Tomcat** | `tomcat-betterdefaultpasslist.txt` |
| **MySQL** | `mysql-betterdefaultpasslist.txt` |
| **MSSQL** | `mssql-betterdefaultpasslist.txt` |
| **PostgreSQL** | `postgres-betterdefaultpasslist.txt` |

---

## 💉 Web Fuzzing Kelime Listeleri

### SQL Enjeksiyonu (SQLi)

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (Siteler Arası Betik Çalıştırma)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI / RFI (Dosya Dahil Etme)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Komut Enjeksiyonu

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (Sunucu Taraflı Şablon Enjeksiyonu)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Parametre ve Karakter Fuzzing Listeleri

| Kelime Listesi | Amaç |
|----------------|------|
| **special-chars.txt** | Özel karakterler ve kaçış dizileri |
| **Unicode.txt** | Unicode normalizasyon fuzzing |
| **big-list-of-naughty-strings.txt** | Beklenmeyen sınır uç durumları (edge cases) |
| **fuzz-Bo0oM.txt** | Genel web fuzzing yükleri |
| **burp-parameter-names.txt** | Parametre keşfi |

---

## 📦 SecLists Koleksiyonu

### Kurulum

```bash
# Kali Linux (kurulu değilse)
sudo apt update && sudo apt install seclists

# GitHub üzerinden doğrudan klonlama
git clone https://github.com/danielmiessler/SecLists.git /opt/SecLists
```

### SecLists Dizin Yapısı

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Alt alan adı listeleri
│   ├── Web-Content/            # Dizin ve dosya listeleri
│   └── Infrastructure/         # Servis ve port listeleri
├── Fuzzing/
│   ├── SQLi/                   # SQLi payload listeleri
│   ├── XSS/                    # XSS payload listeleri
│   ├── LFI/                    # LFI/RFI payload listeleri
│   └── command-injection*.txt  # Komut enjeksiyonu
├── Passwords/
│   ├── Common-Credentials/     # Parola listeleri
│   ├── Leaked-Databases/       # rockyou vb. sızıntı listeleri
│   └── Default-Credentials/    # Varsayılan parolalar
├── Usernames/
│   ├── Names/                  # İsim listeleri
│   └── top-usernames*.txt      # Kullanıcı adı listeleri
└── Payloads/
    └── Anti-Antivirus/         # AV atlatma payload'ları
```

---

## 🐧 Kali Varsayılan Kelime Listeleri

```bash
# Ana kelime listeleri dizini
/usr/share/wordlists/

# İçerik
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # gunzip ile açılır
├── dirb/                   # DIRB listeleri
├── dirbuster/              # DirBuster listeleri
├── fasttrack.txt           # Hızlı saldırı listesi
├── fern-wifi/              # WiFi listeleri
├── metasploit/             # Metasploit listeleri
├── nmap.lst                # Nmap NSE script listesi
├── wfuzz/                  # wFuzz fuzzing listeleri
└── seclists -> /usr/share/seclists/
```

---

## 🛠️ Özel Kelime Listesi Üretim Araçları

### CeWL (Web Sayfasından Kelime Çıkarıcı)

```bash
# Kurulum
sudo apt install cewl

# Hedef web sitesini tarayarak kelime listesi oluşturma
cewl https://target.com -w custom_wordlist.txt

# Derinlik ve minimum kelime uzunluğu belirterek
cewl https://target.com -d 3 -m 6 -w custom_wordlist.txt

# E-posta adreslerini de dahil etme
cewl https://target.com -e -w emails_wordlist.txt
```

### Crunch (Karakter Tabanlı Liste Üretici)

```bash
# Kurulum
sudo apt install crunch

# Temel sözdizimi: crunch <min> <max> [karakter_seti] -o <çıktı>
# 4 ile 6 karakter arası tüm kombinasyonlar
crunch 4 6 -o wordlist.txt

# Özel karakter setiyle 8 karakterlik parola listesi
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Belirli bir kalıpla (Pattern) üretme:
# @ = küçük harf, , = büyük harf, % = rakam, ^ = özel karakter
crunch 6 6 -t @@%%^^ -o pattern_list.txt
```

### CUPP (Kişiye Özel Profil Tabanlı Parola Üretici)

```bash
# Kurulum
git clone https://github.com/Mebus/cupp.git
cd cupp

# Etkileşimli profil modu
python3 cupp.py -i
# Sorulan sorular: İsim, soyisim, doğum tarihi, eş bilgisi, evcil hayvan adı vb.
```

### Hashcat Kuralları ile Kelime Listesi Zenginleştirme

```bash
# En iyi kurallarla kelime listesini çeşitlendirme (mutations)
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout > mutated.txt
```

---

## ⬇️ Hızlı İndirme Komutları

```bash
# RockYou indirme
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# En popüler 1 milyon parola
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# DirBuster orta boy liste
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Jason Haddix DNS listesi (Bug bounty için)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

---

## 💡 İpuçları ve En İyi Pratikler

### Göreve Göre Doğru Listeyi Seçme

| Görev | Önerilen Kelime Listesi |
|-------|-------------------------|
| **Hızlı Dizin Taraması** | `common.txt`, `raft-small-directories.txt` |
| **Standart Dizin Taraması** | `directory-list-2.3-medium.txt` |
| **Kapsamlı Dizin Taraması** | `directory-list-2.3-big.txt` |
| **Standart Alt Alan Adı** | `subdomains-top1million-20000.txt` |
| **Bug Bounty Alt Alan Adı** | `dns-Jhaddix.txt` |
| **Parola Kırma** | `rockyou.txt` + Hashcat kuralları |

### Kelime Listelerini Optimize Etme

```bash
# Sıralama ve yinelenenleri kaldırma
sort -u wordlist.txt -o wordlist.txt

# Boş satırları temizleme
sed -i '/^$/d' wordlist.txt

# 4 karakterden kısa satırları silme
awk 'length >= 4' wordlist.txt > filtered.txt

# Küçük harfe dönüştürme
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Satır sayısını öğrenme
wc -l wordlist.txt
```

---

## 📚 Kaynaklar

### Kelime Listesi Depoları
- [SecLists Resmi Deposu](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### İlgili Başvuru Kılavuzları
- [Hydra](../../Hydra/translations/README.tr.md)
- [John The Ripper](../../John-The-Ripper/translations/README.tr.md)
- [Hashcat](../../Hashcat/translations/README.tr.md)
- [ffuf](../../ffuf/translations/README.tr.md)
- [Gobuster](../../Gobuster/translations/README.tr.md)

---

<p align="center">
  <b>📚 Doğru Kelime Listesi Başarının Anahtarıdır!</b><br>
  <i>Akıllıca seçin, kurallarla zenginleştirin</i>
</p>
