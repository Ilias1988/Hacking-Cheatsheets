[🇬🇧 English](../../README.md)

# 📷 ExifTool - Meta Veri (Metadata) Analiz Başvuru Kılavuzu

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                     Meta Veri Çıkarma Aracı
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## 📋 İçindekiler

- [ExifTool Nedir](#-exiftool-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [Meta Verileri Okuma](#-meta-verileri-okuma)
- [Meta Veri Yazma ve Güncelleme](#-meta-veri-yazma-ve-güncelleme)
- [Meta Verileri Temizleme / Silme](#-meta-verileri-temizleme--silme)
- [Desteklenen Dosya Biçimleri](#-desteklenen-dosya-biçimleri)
- [OSINT ve Adli Bilişim (Forensics)](#-osint-ve-adli-bilişim-forensics)
- [Toplu İşlemler (Batch Processing)](#-toplu-i̇şlemler-batch-processing)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 ExifTool Nedir

**ExifTool**, çok çeşitli dosya türlerinde meta verileri (metadata) okumak, yazmak ve düzenlemek için geliştirilmiş güçlü bir komut satırı aracıdır:

- 🔍 **Açık Kaynak İstihbaratı (OSINT)** - Fotoğraflardan coğrafi konum (GPS) ve cihaz bilgisi çıkarma
- 🔬 **Dijital Adli Bilişim** - Belge, görsel ve video dosyalarının oluşturulma ve düzenlenme geçmişini inceleme
- 🔐 **Gizlilik ve Güvenlik** - Paylaşılmadan önce dosyalardaki hassas meta verileri temizleme
- 📸 **Fotoğrafçılık** - EXIF, IPTC ve XMP etiketlerini yönetme
- 📄 **Belge Analizi** - PDF ve Office belgelerinin yazar, yazılım ve revizyon bilgilerini ortaya çıkarma

### Meta Veri Neden Önemlidir?

| Bilgi Türü | Örnek |
|------------|-------|
| **Coğrafi Konum** | Fotoğrafın çekildiği yerin GPS enlem/boylam koordinatları |
| **Cihaz Bilgisi** | Kamera/telefon marka, model ve seri numarası |
| **Zaman Damgaları** | Dosyanın tam olarak ne zaman çekildiği, kaydedildiği ve düzenlendiği |
| **Yazar / Kullanıcı** | Belgeyi oluşturan ve en son düzenleyen kullanıcı adı |
| **Yazılım Bilgisi** | Hangi programla (Photoshop, Word vb.) üretildiği veya düzenlendiği |
| **Düzenleme Geçmişi**| Değiştirilme zamanları, revizyon numaraları |

---

## 🚀 Kurulum

### Linux (Debian / Ubuntu / Kali)

```bash
# ExifTool kurulumu
sudo apt install exiftool

# Veya Perl kütüphanesi olarak
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew ile kurulum
brew install exiftool
```

### Windows

```bash
# Resmi web sitesinden indirin
https://exiftool.org/

# exiftool(-k).exe dosyasını exiftool.exe olarak yeniden adlandırın
# PATH ortam değişkenine ekleyin veya doğrudan terminalden çalıştırın
```

### Kurulum Doğrulama

```bash
exiftool -ver
# Çıktı: 12.xx
```

---

## 💻 Temel Kullanım

### Temel Sözdizimi

```bash
# Meta verileri okuma
exiftool [SEÇENEKLER] DOSYA

# Meta veri yazma
exiftool -ETİKET=DEĞER DOSYA

# Birden fazla dosya veya dizin
exiftool *.jpg
exiftool -r dizin/
```

### Sık Kullanılan Seçenekler

| Parametre | Açıklama |
|-----------|----------|
| `-r` | Dizinleri özyinelemeli (recursive) tara |
| `-v` | Ayrıntılı çıktı |
| `-n` | Ham sayısal değerleri göster (dönüştürme yapmadan) |
| `-s` | Kısa etiket isimlerini göster |
| `-G` | Her etiket için ait olduğu grubu göster |
| `-j` | JSON formatında çıktı al |
| `-csv` | CSV formatında tablo dökümü |
| `-overwrite_original` | Yedek (`_original`) dosyası oluşturmadan doğrudan üzerine yaz |

---

## 📖 Meta Verileri Okuma

### Tüm Meta Verileri Görüntüleme

```bash
# Tüm meta verileri listele
exiftool image.jpg

# Detaylı çıktı
exiftool -v image.jpg

# Çok ayrıntılı çıktı
exiftool -v3 image.jpg
```

### Belirli Etiketleri Okuma

```bash
# Tek bir etiket okuma
exiftool -GPSLatitude image.jpg

# Birden fazla etiket seçme
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Belirli etiketleri hariç tutma (iki tire ile)
exiftool --MakerNotes image.jpg
```

### Grup Tabanlı Okuma

```bash
# Tüm EXIF etiketlerini göster
exiftool -EXIF:all image.jpg

# Tüm IPTC etiketlerini göster
exiftool -IPTC:all image.jpg

# Tüm XMP etiketlerini göster
exiftool -XMP:all image.jpg

# Yalnızca konum (GPS) etiketlerini göster
exiftool -GPS:all image.jpg

# Grup adlarıyla birlikte listele
exiftool -G image.jpg
```

### Çıktı Formatları

```bash
# JSON formatında döküm
exiftool -j image.jpg

# CSV formatında döküm
exiftool -csv image.jpg

# HTML tablosu olarak kaydetme
exiftool -htmlDump image.jpg > output.html

# Sekmeyle ayrılmış (tab-separated) çıktı
exiftool -t image.jpg

# Özel format şablonu
exiftool -p '$filename: $make $model' image.jpg
```

---

## ✏️ Meta Veri Yazma ve Güncelleme

### Tek Etiket Güncelleme

```bash
# Yazar bilgisi atama
exiftool -Author="Ahmet Yilmaz" image.jpg

# Telif hakkı bilgisi ekleme
exiftool -Copyright="© 2024 Kurum" image.jpg

# Açıklama / yorum ekleme
exiftool -Comment="Ozel Fotograf" image.jpg
```

### Birden Fazla Etiket Yazma

```bash
exiftool -Author="Ahmet Yilmaz" -Copyright="© 2024" -Keywords="doga,manzara" image.jpg
```

### GPS Konum Bilgisi Yazma

```bash
# Koordinat atama
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Yön referanslarıyla birlikte atama
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# Google Maps formatında koordinat girme
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Tarih ve Saat Bilgisi Güncelleme

```bash
# Orijinal çekim tarihini ayarlama
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Dosyadaki tüm tarih alanlarını tek seferde ayarlama
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Tarihleri ileri/geri kaydırma
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # 1 yıl ileri al
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # 7 gün geri al
```

### Dosyalar Arasında Meta Veri Kopyalama

```bash
# Kaynak dosyadaki tüm meta verileri hedef dosyaya kopyala
exiftool -TagsFromFile source.jpg destination.jpg

# Yalnızca belirli etiket gruplarını kopyala
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Bir klasördeki tüm dosyalara kopyala
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

## 🗑️ Meta Verileri Temizleme / Silme

### Tüm Meta Verileri Silme

```bash
# Tüm meta verileri temizle (orijinal dosya yedeği kalır)
exiftool -all= image.jpg

# Yedek oluşturmadan doğrudan dosya üzerinden sil
exiftool -overwrite_original -all= image.jpg

# Bir klasördeki tüm resimleri temizle
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Belirli Meta Verileri Silme

```bash
# Yalnızca GPS konum verilerini sil
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Yalnızca EXIF verilerini sil
exiftool -EXIF:all= image.jpg

# Yorumları sil
exiftool -Comment= image.jpg

# Yazar ve oluşturucu bilgilerini sil
exiftool -Author= -Creator= -Artist= image.jpg
```

### Belirli Dosya Türlerini Temizleme

```bash
# Resimler
exiftool -all= *.jpg *.png *.gif

# PDF belgeleri
exiftool -all= document.pdf

# Office belgeleri (Word, Excel, PowerPoint)
exiftool -all= *.docx *.xlsx *.pptx

# Video dosyaları
exiftool -all= video.mp4
```

### Gizlilik Odaklı Tam Temizlik

```bash
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 Desteklenen Dosya Biçimleri

### Desteklenen Dosya Türleri (400'den Fazla)

| Kategori | Biçimler |
|----------|----------|
| **Resimler** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **RAW Fotoğraflar**| CR2, NEF, ARW, DNG, ORF, RW2 |
| **Video** | MP4, MOV, AVI, MKV, WebM |
| **Ses** | MP3, WAV, FLAC, M4A, OGG |
| **Belgeler** | PDF, DOCX, XLSX, PPTX |
| **Arşivler** | ZIP (sınırlı meta veri) |

### Dosya Türüne Özel Sorgular

```bash
# PDF meta verileri
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Video meta verileri
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Ses meta verileri
exiftool -Artist -Album -Title -Duration audio.mp3

# Office belgeleri
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT ve Adli Bilişim (Forensics)

### GPS Konum Bilgilerini Çıkarma

```bash
# Tüm GPS etiketlerini görüntüleme
exiftool -GPS:all image.jpg

# Okunabilir GPS konumu
exiftool -GPSPosition image.jpg

# Ondalık derece (Decimal degrees) formatında çıktı alma
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Doğrudan açılabilir Google Maps bağlantısı üretme
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Cihaz ve Donanım Tespiti

```bash
# Kamera üretici, model ve seri numarası
exiftool -Make -Model -SerialNumber image.jpg

# Kullanılan yazılım ve işletim sistemi
exiftool -Software -CreatorTool image.jpg

# Lens detayları
exiftool -Lens -LensModel -FocalLength image.jpg

# Kamera üreticisine özel ayrıntılı bilgiler
exiftool -MakerNotes:all image.jpg
```

### Zaman Çizelgesi (Timeline) Analizi

```bash
# Dosyadaki tüm tarih/saat etiketleri
exiftool -time:all image.jpg

# Dosya sistemi tarihleri
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# EXIF tarihleri
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Tarih tutarsızlıklarını yakalama (sahtecilik tespiti)
exiftool -a -G1 -s -time:all image.jpg
```

### Belge Adli Bilişimi

```bash
# PDF analizi
exiftool -a -G1 document.pdf

# Word revizyon ve düzenleme süresi
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Belge sahipleri ve kurum bilgisi
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Görsel Orijinallik Denetimi

```bash
# Düzenleme yazılımı izlerini arama
exiftool -Software -HistorySoftwareAgent image.jpg

# Gömülü küçük resmi (Thumbnail) ayıklama
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Çekim ve değişiklik tarihlerini karşılaştırma
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

---

## ⚙️ Toplu İşlemler (Batch Processing)

### Birden Fazla Dosyayı İşleme

```bash
# Geçerli dizindeki tüm JPG dosyaları
exiftool *.jpg

# Alt dizinleri de kapsayacak şekilde (Recursive)
exiftool -r directory/

# Belirli uzantıları filtreleme
exiftool -ext jpg -ext png directory/
```

### Toplu Meta Veri Temizleme

```bash
# Tüm resimlerin meta verilerini temizleme
exiftool -all= -overwrite_original -r directory/

# Yalnızca JPG ve PNG dosyalarını temizleme
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Dosyaları Meta Veriye Göre Yeniden Adlandırma

```bash
# Çekim tarihine göre yeniden adlandırma (YYYYAAGG_SSDDSS)
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Kamera modeline göre adlandırma
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Sayaç ekleyerek adlandırma
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Meta Verileri CSV Olarak Dışa Aktarma

```bash
# Tüm meta verileri CSV dosyasına aktarma
exiftool -csv *.jpg > metadata.csv

# Belirli alanları seçerek CSV tablosu oluşturma
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Koşullu İşlemler (-if)

```bash
# Yalnızca GPS bilgisi içeren dosyaları göster
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Yalnızca Canon marka makinelerle çekilenleri listele
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Belirli bir tarihten sonra değiştirilen dosyaları göster
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Hızlı Referans

### En Sık Kullanılan Komutlar

| Amaç | Komut |
|------|-------|
| Tüm meta verileri gör | `exiftool image.jpg` |
| GPS konumunu gör | `exiftool -GPS:all image.jpg` |
| Tüm meta verileri sil | `exiftool -all= image.jpg` |
| Yalnızca GPS bilgisini sil | `exiftool -GPS:all= image.jpg` |
| JSON formatında çıktı | `exiftool -j image.jpg` |
| Özyinelemeli tara | `exiftool -r dizin/` |
| Kamera bilgilerini gör | `exiftool -Make -Model image.jpg` |
| Zaman damgalarını gör | `exiftool -time:all image.jpg` |

### Sık Kullanılan Etiketler

| Etiket | Açıklama |
|--------|----------|
| `-Make` | Kamera / telefon üreticisi |
| `-Model` | Cihaz model adı |
| `-DateTimeOriginal` | Fotoğrafın çekildiği asıl an |
| `-GPSLatitude` | GPS enlem değeri |
| `-GPSLongitude` | GPS boylam değeri |
| `-Author` | Dosya yazarı |
| `-Copyright` | Telif hakkı bilgisi |
| `-Software` | Düzenleme veya üretim yazılımı |
| `-ImageWidth` | Resim genişliği (piksel) |
| `-ImageHeight` | Resim yüksekliği (piksel) |

### Gizlilik Kontrol Listesi

```bash
# 1. Mevcut meta verileri kontrol edin
exiftool -a -G1 image.jpg

# 2. GPS koordinatlarını kontrol edin
exiftool -GPS:all image.jpg

# 3. Kişisel ve cihaz kimlik bilgilerini kontrol edin
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Tüm meta verileri kalıcı olarak silin
exiftool -all= -overwrite_original image.jpg

# 5. Silinmeyi doğrulayın
exiftool image.jpg
```

---

## 📚 Kaynaklar

- [ExifTool Resmi Web Sitesi](https://exiftool.org/)
- [ExifTool Etiket İsimleri Kılavuzu](https://exiftool.org/TagNames/)
- [ExifTool Desteklenen Biçimler](https://exiftool.org/#supported)

### İlgili Başvuru Kılavuzları
- [Autopsy](../../Autopsy/README.md)
- [Volatility](../../Volatility/README.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)

---

<p align="center">
  <b>📷 Meta Veri Analizinde Uzmanlaşın!</b><br>
  <i>ExifTool - Her dosyanın anlatacak bir hikayesi vardır</i>
</p>
