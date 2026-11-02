[🇬🇧 English](../../README.md)

# 🔧 Binwalk - Bellenim (Firmware) Analiz Başvuru Kılavuzu

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
            Bellenim Analizi ve Veri Çıkarma Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 İçindekiler

- [Binwalk Nedir](#-binwalk-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [İmza Taraması (Signature Scanning)](#-i̇mza-taraması-signature-scanning)
- [Dosya Çıkarma (Extraction)](#-dosya-çıkarma-extraction)
- [Entropi Analizi (Entropy Analysis)](#-entropi-analizi-entropy-analysis)
- [Dosya Sistemi Analizi](#-dosya-sistemi-analizi)
- [CTF Görevleri & Senaryoları](#-ctf-görevleri--senaryoları)
- [Gelişmiş Seçenekler](#-gelişmiş-seçenekler)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 Binwalk Nedir

**Binwalk**, bellenim (firmware) imajlarını analiz etmek, tersine mühendislik uygulamak ve gömülü dosyaları çıkarmak için tasarlanmış hızlı ve pratik bir araçtır:

- 🔧 **Bellenim Analizi** - Gömülü dosya sistemlerini, çekirdekleri ve kodları tanımlama
- 🔓 **IoT Güvenliği** - Yönlendirici (router), IP kamera ve akıllı cihaz yazılımlarını açma
- 🎯 **CTF Yarışmaları** - Resim veya ikili dosyaların içine gizlenmiş gizli verileri bulma
- 🔬 **Adli Bilişim (Forensics)** - İkili dosya yapılarını ve gizlenmiş bölümleri inceleme
- 🔍 **Tersine Mühendislik** - Dosya formatlarını ve sıkıştırma katmanlarını deşifre etme

### Binwalk'un Algılayabildiği Yapılar

| Kategori | Örnekler |
|----------|----------|
| **Sıkıştırılmış Veri** | gzip, bzip2, lzma, xz, zlib |
| **Arşivler** | tar, zip, rar, 7z, cpio |
| **Dosya Sistemleri** | squashfs, cramfs, jffs2, ext2/3/4, ubifs |
| **Yürütülebilir Dosyalar**| ELF, PE, ARM, MIPS makine kodları |
| **Görsel Dosyaları** | JPEG, PNG, GIF, BMP |
| **Kripto Varlıkları** | OpenSSL sertifikaları, özel anahtarlar |
| **Önyükleyiciler (Boot)** | U-Boot başlıkları, ARM önyükleme yapıları |

---

## 🚀 Kurulum

### Linux (Kali / Ubuntu)

```bash
# Binwalk kurulumu
sudo apt update
sudo apt install binwalk

# Tüm çıkarma araçlarını yükleme (önerilir)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Veya depodan kaynak kodla kurulum
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Özel SquashFS türleri için sasquatch kurulumu
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Kurulum Doğrulama

```bash
binwalk --help
binwalk -h
```

---

## 💻 Temel Kullanım

### Temel Sözdizimi

```bash
# Dosyayı tara (imzaları ve konumları göster)
binwalk firmware.bin

# Algılanan tüm dosyaları otomatik çıkart
binwalk -e firmware.bin

# Özyinelemeli (recursive) iç içe çıkarma
binwalk -eM firmware.bin
```

### Sık Kullanılan Seçenekler

| Parametre | Açıklama |
|-----------|----------|
| `-e, --extract` | Bilinen dosya türlerini otomatik çıkart |
| `-M, --matryoshka` | Çıkarılan dosyaların içine girerek özyinelemeli tara |
| `-r, --rm` | Çıkarma bittikten sonra ham parçaları temizle |
| `-q, --quiet` | Sessiz mod (yalnızca sonuçları ver) |
| `-v, --verbose` | Ayrıntılı çıktı |
| `-d, --depth` | Özyineleme derinlik sınırı (varsayılan: 8) |
| `-C, --directory` | Çıkarma hedef klasörü |
| `-f, --log` | Sonuçları dosyaya günlükle |

---

## 🔍 İmza Taraması (Signature Scanning)

### Standart Tarama

```bash
# İmzaları tara
binwalk firmware.bin

# Çıktı örneği:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Belirli İmza Türlerini Tarama

```bash
# Yalnızca çalıştırılabilir CPU opkodlarını tara
binwalk -A firmware.bin

# Ham bayt veya metin ara
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Dahil Etme ve Hariç Tutma Filtreleri

```bash
# Yalnızca belirli türleri dahil et
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Belirli türleri taramadan hariç tut
binwalk --exclude="jpeg" firmware.bin

# Yalnızca belirli bir imzayı ara (-y)
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Özel İmzalar Kullanma

```bash
# Özel imza dosyası belirterek tarama yapma
binwalk -m custom_signatures.txt firmware.bin

# İmza dosyası biçimi örneği:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Dosya Çıkarma (Extraction)

### Temel Çıkarma

```bash
# Tanınan tüm dosyaları çıkar
binwalk -e firmware.bin
# Oluşan klasör: _firmware.bin.extracted/

# Özel bir klasöre çıkarma
binwalk -e -C /output/dir firmware.bin

# Matruşka modu (iç içe arşivleri sonuna kadar aç)
binwalk -eM firmware.bin
```

### Çıkarma Seçenekleri

```bash
# Çıkarma sonrası ara dosyaları temizleme
binwalk -er firmware.bin

# Özyineleme derinliğini sınırlandırma (ör. 3 seviye)
binwalk -eM --depth=3 firmware.bin

# Sessiz modda çıkarma
binwalk -eq firmware.bin

# Detaylı log ile çıkarma
binwalk -ev firmware.bin
```

### Manuel Çıkarma (dd ile)

```bash
# Binwalk otomatik çıkaramazsa ofset değerini kullanarak dd ile alın
binwalk firmware.bin
# Örnek: 1572892 ofsetinde Squashfs tespit edildi

# dd ile o ofsetten sonrasını ayıklayın:
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Boyutu biliniyorsa count ile sınırlayın:
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Belirli Dosya Türlerini Ayıklama (-D)

```bash
# Yalnızca belirli dosya türlerini çıkart
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Birden fazla tür belirtme
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Çıkarılan dosya üzerinde özel komut çalıştırma (type:ext:command)
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Entropi Analizi (Entropy Analysis)

### Entropi Nedir?

Entropi, verideki rastgelelik derecesini (0.0 ile 1.0 arasında) ölçer:
- **Yüksek Entropi (0.9 - 1.0)** = Şifrelenmiş veya sıkıştırılmış veri
- **Orta Entropi (0.5 - 0.8)** = Çalıştırılabilir makine kodu
- **Düşük Entropi (0.0 - 0.4)** = Düz metin, sıfırlarla doldurulmuş veya tekrarlayan veri

### Entropi Grafiği Oluşturma

```bash
# Terminalde entropi hesaplama
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Entropi grafiğini görsel resim (PNG) olarak kaydetme
binwalk -E --save firmware.bin
# Çıktı: firmware.bin.png

# Açıklama etiketleri (legend) ile birlikte kaydetme
binwalk -EJ firmware.bin
```

### Entropi Grafiğini Yorumlama

```
Yüksek ve düz çizgi (~1.0)   → Şifrelenmiş veri bloğu
Testere dişli dalgalar (~0.7)→ Sıkıştırılmış arşiv/dosya sistemi
Orta düzey dalgalı (~0.5)    → Program kodu ve ikili mantık
Düşük ve düz çizgi (~0.2)    → Düz metin veya konfigürasyon
Aniden 0'a düşüş             → Null padding (sıfır dolgusu)
```

---

## 💾 Dosya Sistemi Analizi

### Yaygın Bellenim Dosya Sistemleri

| Dosya Sistemi | Açıklama |
|---------------|----------|
| **SquashFS** | Yalnızca okunabilir sıkıştırılmış sistem (en yaygın) |
| **JFFS2** | Flash bellekler için günlükleme tabanlı dosya sistemi |
| **UBIFS** | Büyük flash yongalar için optimize edilmiş modern sistem |
| **CramFS** | Sıkıştırılmış salt okunur ROM dosya sistemi |
| **YAFFS** | NAND flash yongalar için tasarlanmış sistem |
| **ext2/3/4** | Standart Linux dosya sistemleri |

### SquashFS Çıkarma ve İnceleme

```bash
# Binwalk ile doğrudan çıkarma
binwalk -e firmware.bin

# unsquashfs aracı ile manuel çıkarma
unsquashfs squashfs-root.bin
ls squashfs-root/

# Salt okunur olarak bağlama (Mount)
sudo mount -t squashfs squashfs.bin /mnt
```

### JFFS2 Çıkarma

```bash
# Jefferson aracı ile açma
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Veya doğrudan binwalk ile
binwalk -e firmware.bin
```

### UBIFS Çıkarma

```bash
# ubi_reader aracı ile
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Çıkarılan Dosyaları İnceleme

```bash
cd _firmware.bin.extracted/

# Kritik sistem dosyalarını bulma
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Parola ve API anahtarı arama
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Çalıştırılabilir ikili dosyaları listeleme
find . -type f -executable
file */bin/*
```

---

## 🎯 CTF Görevleri & Senaryoları

### Resim İçine Gizlenmiş Dosyalar (Steganografi)

```bash
# Resim dosyasında gizlenmiş arşivleri tara
binwalk image.png
binwalk image.jpg

# Gizli verileri otomatik çıkart
binwalk -e image.png

# strings ile kontrol et
strings image.png | head -50
```

### İç İçe Geçmiş Arşivler (Stacked Archives)

```bash
# Matruşka moduyla iç içe geçmiş tüm katmanları aç
binwalk -eM challenge.bin

# 10 katman derinliğe kadar in
binwalk -eM --depth=10 nested.bin
```

### Bayrak (Flag) Arama İş Akışı

```bash
# 1. Temel imza taraması
binwalk challenge.bin

# 2. Entropi kontrolü (şifreli alan var mı?)
binwalk -E challenge.bin

# 3. Tüm katmanları çıkart
binwalk -eM challenge.bin

# 4. Çıkarılan dosyalar içinde arama yap
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag
```

---

## ⚙️ Gelişmiş Seçenekler

### Hex Dökümü Görüntüleme

```bash
# İmzaların bulunduğu ofsetlerde hex dökümünü göster
binwalk -W firmware.bin

# Belirli bir ofset aralığında hex dökümü
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Ofset ve Boyut Kısıtlama

```bash
# Belirli bir ofsetten itibaren tara
binwalk -o 1024 firmware.bin

# Yalnızca belirli bir bayt uzunluğunu tara
binwalk -l 10000 firmware.bin
```

### Çıktı Biçimleri

```bash
# CSV olarak kaydet
binwalk --csv firmware.bin > results.csv

# JSON formatında çıktı al
binwalk --json firmware.bin > results.json

# Log dosyasına yaz
binwalk -f scan.log firmware.bin
```

### İki Bellenim Dosyasını Karşılaştırma (Diff)

```bash
# İki sürüm arasındaki bayt ve yapısal farkları göster
binwalk -W --diff firmware_v1.bin firmware_v2.bin
```

---

## 📊 Hızlı Referans

### En Çok Kullanılan Komutlar

| Amaç | Komut |
|------|-------|
| Dosyayı tara | `binwalk firmware.bin` |
| Tümünü çıkart | `binwalk -e firmware.bin` |
| Özyinelemeli matruşka çıkarma | `binwalk -eM firmware.bin` |
| Entropi grafiği | `binwalk -E firmware.bin` |
| Opkod taraması | `binwalk -A firmware.bin` |
| Özel dizge ara | `binwalk -R "pattern" file` |
| Özel klasöre çıkart | `binwalk -e -C outdir/ file` |
| Çıkar ve geçicileri sil | `binwalk -er firmware.bin` |

### Sık Karşılaşılan Dosya İmzaları (Magic Bytes)

| İmza | Açıklama |
|------|----------|
| `\x1f\x8b\x08` | gzip sıkıştırılmış veri |
| `BZh` | bzip2 sıkıştırılmış veri |
| `\xfd7zXZ` | xz sıkıştırılmış veri |
| `hsqs` | SquashFS (Little Endian) |
| `sqsh` | SquashFS (Big Endian) |
| `\x85\x19` | JFFS2 dosya sistemi |
| `UBI#` | UBIFS dosya sistemi |
| `\x89PNG` | PNG görsel dosyası |
| `\xff\xd8\xff` | JPEG görsel dosyası |
| `PK\x03\x04` | ZIP arşivi |

### Kritik Bellenim Dosya Yolları

| Dizin / Dosya | İçerik |
|---------------|--------|
| `/etc/passwd` | Sistem kullanıcı hesapları |
| `/etc/shadow` | Parola hash'leri |
| `/etc/config/` | Cihaz yapılandırma dosyaları |
| `/etc/init.d/` | Başlangıç servis betikleri |
| `/usr/bin/` | Kullanıcı komutları ve ikilileri |
| `/www/` | Cihazın web yönetim paneli dosyaları |
| `/etc/ssl/` | SSL sertifikaları ve özel anahtarlar |

---

## 📚 Kaynaklar

- [Binwalk GitHub Deposu](https://github.com/ReFirmLabs/binwalk)
- [Binwalk Wiki Belgeleri](https://github.com/ReFirmLabs/binwalk/wiki)
- [OWASP IoT Bellenim Güvenliği](https://owasp.org/www-project-iot-security/)

### İlgili Başvuru Kılavuzları
- [ExifTool](../../ExifTool/README.md)
- [Volatility](../../Volatility/README.md)
- [Autopsy](../../Autopsy/README.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)

---

<p align="center">
  <b>🔧 Bellenim Analizinde Uzmanlaşın!</b><br>
  <i>Binwalk - Bellenimlerin sırlarını açığa çıkaran vazgeçilmez araç</i>
</p>
