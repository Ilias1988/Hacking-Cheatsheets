[🇬🇧 English](../../README.md)

# 🔍 Autopsy - Dijital Adli Bilişim Platformu Başvuru Kılavuzu

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Dijital Adli Bilişim Platformu
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 İçindekiler

- [Autopsy Nedir](#-autopsy-nedir)
- [Kurulum](#-kurulum)
- [Vaka (Case) Oluşturma](#-vaka-case-oluşturma)
- [Veri Kaynağı Ekleme](#-veri-kaynağı-ekleme)
- [İçe Aktarma Modülleri (Ingest Modules)](#-i̇çe-aktarma-modülleri-ingest-modules)
- [Analiz Özellikleri](#-analiz-özellikleri)
- [Dosya Analizi](#-dosya-analizi)
- [Zaman Çizelgesi Analizi (Timeline)](#-zaman-çizelgesi-analizi-timeline)
- [Anahtar Kelime Arama (Keyword Search)](#-anahtar-kelime-arama-keyword-search)
- [Raporlama](#-raporlama)
- [Komut Satırı Araçları (The Sleuth Kit - TSK)](#-komut-satırı-araçları-the-sleuth-kit---tsk)

---

## 🎯 Autopsy Nedir

**Autopsy**, The Sleuth Kit (TSK) motoru üzerine inşa edilmiş, açık kaynaklı ve ücretsiz bir dijital adli bilişim (DFIR) analiz platformudur:

- 🖥️ **Grafiksel Kullanıcı Arayüzü (GUI)** - Kullanıcı dostu adli analiz ortamı
- 💾 **Disk ve İmaj Analizi** - Sabit diskler, USB bellekler ve adli imajlar üzerinde inceleme
- 📁 **Silinen Dosyaları Kurtarma** - Silinmiş dosya kayıtlarını ve tahsis edilmemiş alanları kurtarma
- 📊 **Zaman Çizelgesi Analizi (Timeline)** - Kullanıcı ve sistem aktivitelerini kronolojik sıraya koyma
- 🔍 **Anahtar Kelime Arama** - Tüm veri kaynaklarında hızlı metin ve düzenli ifade (Regex) araması
- 📧 **E-posta Analizi** - PST, MBOX ve EML e-posta veritabanlarını ayrıştırma
- 🌐 **Web Kalıntıları (Artifacts)** - Tarayıcı geçmişi, çerezler ve indirme kayıtları
- 📱 **Mobil Adli Bilişim** - Android ve iOS sistemlerinden çıkarılan verileri inceleme

### Desteklenen Kanıt Türleri

| Tür | Biçimler |
|-----|----------|
| **Disk İmajları** | E01, Ex01, AFF, Ham (Raw/dd), VHD, VMDK |
| **Yerel Disk** | Fiziksel sürücüler ve mantıksal bölümler |
| **Sanal Makine Dosyaları** | VMDK (VMware), VHD / VHDX (Hyper-V) |
| **Mantıksal Dosyalar** | Klasör veya dosya listesi olarak içe aktarma |
| **Tahsis Edilmemiş Alan** | Boş alanlardan çıkarılan ham veriler (Unallocated space) |

---

## 🚀 Kurulum

### Windows Kurulumu

```bash
# Resmi web sitesinden indirin
https://www.autopsy.com/download/

# Kurulum dosyasını çalıştırın
autopsy-x.x.x-64bit.msi

# Varsayılan kurulum konumu
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali / Ubuntu)

```bash
# Paket yöneticisi ile kurulum
sudo apt update
sudo apt install autopsy

# Başlatma
autopsy
```

### Sistem Gereksinimleri

| Bileşen | Asgari | Önerilen |
|---------|--------|----------|
| **RAM** | 8 GB | 16 GB veya üzeri |
| **İşlemci** | Çok çekirdekli | 4+ çekirdek |
| **Disk** | SSD | Hızlı NVMe SSD |
| **İşletim Sistemi** | Windows 10/11, Linux | Windows 10/11 (tam özellikler) |

---

## 📁 Vaka (Case) Oluşturma

### Yeni Vaka Sihirbazı

1. **File → New Case** yolunu izleyin
2. **Case Name** (Vaka Adı) girin (ör. "Soruşturma_2024")
3. **Base Directory** (Vaka dosyalarının kaydedileceği ana klasör) seçin
4. **Case Type** belirleyin:
   - Single-user (Tek kullanıcı / yerel)
   - Multi-user (Çok kullanıcılı / merkezi PostgreSQL sunucusu)
5. **Case Number** (Vaka numarası) girin (isteğe bağlı)
6. **Examiner** (İncelemeyi yapan uzman) bilgilerini doldurun
7. **Finish** butonuna basarak vakayı oluşturun

### Vaka Klasör Yapısı

```
VakaAdi/
├── autopsy.db           # Vaka veritabanı (SQLite)
├── Reports/             # Üretilen adli raporlar
├── Export/              # Dışa aktarılan kanıt dosyaları
├── ModuleOutput/        # Çalıştırılan modüllerin çıktıları
└── [HostAdi]/           # İncelenen cihazlara özel veriler
    ├── [VeriKaynagi]/   # Kanıt imajları ve indeksler
    └── Reports/
```

---

## 📀 Veri Kaynağı Ekleme

### Veri Kaynağı Türleri

| Tür | Açıklama |
|-----|----------|
| **Disk Image or VM File** | E01, Raw (dd), VMDK, VHD adli imajları |
| **Local Disk** | Canlı fiziksel sürücü analizi |
| **Logical Files** | Belirli bir klasör veya dosya grubunu inceleme |
| **Unallocated Space** | Boş sektörlerden alınan ham alan imajı |

### Disk İmajı Ekleme

1. Araç çubuğundan **Add Data Source** butonuna tıklayın
2. **Disk Image or VM File** seçeneğini işaretleyin
3. İmaj dosyasının konumunu seçin
4. Sistemin orijinal **Zaman Dilimi** (Time Zone) bilgisini ayarlayın
5. Çalıştırılacak **İçe Aktarma Modülleri**'ni yapılandırın
6. **Finish** butonuna basarak analizi başlatın

```
⚠️ UYARI: Canlı yerel disk eklerken delil bütünlüğünü korumak için 
mutlaka donanımsal yazma engelleyici (write-blocker) veya salt okunur 
bağlantı modu kullanın!
```

---

## ⚙️ İçe Aktarma Modülleri (Ingest Modules)

### İçe Aktarma Modülü Nedir?

Veri kaynağı sisteme eklenirken arka planda otomatik olarak çalışan, sistem kalıntılarını ve delilleri çıkaran analiz modülleridir.

### Temel Modüller

| Modül | Açıklama |
|-------|----------|
| **Recent Activity** | Tarayıcı geçmişi, çerezler, indirmeler ve son açılan belgeler |
| **Hash Lookup** | MD5/SHA1 hash değerlerini bilinen veritabanlarıyla (NSRL) eşleme |
| **File Type Identification** | Dosya türünü uzantısına değil ikili imzasına (magic bytes) göre bulma |
| **Keyword Search** | Verilerin içeriğini indeksleme ve anahtar kelime arama |
| **Email Parser** | PST, MBOX ve EML e-posta dosyalarını ayrıştırma |
| **Extension Mismatch** | Uzantısı kasıtlı olarak değiştirilmiş dosyaları tespit etme |
| **Embedded File Extractor** | Arşivler, Office ve PDF belgeleri içindeki gömülü dosyaları çıkarma |
| **EXIF Parser** | Resimlerin EXIF ve GPS meta verilerini ayrıştırma |
| **Encryption Detection** | Şifrelenmiş veya parola korumalı dosyaları tespit etme |
| **Interesting Files** | Kritik sistem veya kullanıcı dosyalarını otomatik işaretleme |
| **PhotoRec Carver** | Boş alandan silinmiş dosyaları kurtarma (File carving) |

### Hash Veritabanları (NSRL & Kötü Amaçlılar)

```
# Hash veritabanı ekleme
Tools → Options → Hash Database

# Veritabanı türleri:
- Known (NSRL): Bilinen temiz sistem dosyalarını yoksayarak analizi hızlandırır
- Known Bad: Bilinen zararlı yazılım hash'lerinde anında uyarı üretir
```

---

## 🔬 Analiz Özellikleri

### Sol Ağaç Paneli (Tree View)

```
Data Sources (Veri Kaynakları)
├── [İmaj Adı]
│   ├── Volume 1 (Bölüm 1)
│   │   ├── [Klasörler]
│   │   ├── $OrphanFiles (Yetim dosyalar)
│   │   └── $Unalloc (Tahsis edilmemiş boş alan)
│   └── Volume 2
│
Views (Görünümler)
├── File Types (Dosya Türleri)
│   ├── By Extension (Uzantıya göre)
│   └── By MIME Type (MIME türüne göre)
├── Deleted Files (Silinmiş Dosyalar)
├── File Size (Dosya Boyutları)
└── ...
│
Results (Sonuçlar)
├── Extracted Content (Çıkarılan İçerik)
│   ├── Web History (Web Geçmişi)
│   ├── Web Downloads (İndirilen Dosyalar)
│   ├── Recent Documents (Son Belgeler)
│   └── ...
├── Keyword Hits (Anahtar Kelime Eşleşmeleri)
├── HashSet Hits (Zararlı Hash Eşleşmeleri)
├── E-mail Messages (E-postalar)
└── ...
```

---

## 📄 Dosya Analizi

### Alt İçerik Görüntüleyici Panelleri

```
- Hex         : Dosyanın ham onaltılık dökümü
- Text        : Metin içeriği
- Application : PDF, resim veya medya önizlemesi
- Strings     : Çıkarılan ASCII ve Unicode metin dizgileri
- Metadata    : Dosya sistemi ve EXIF meta verileri
- Results     : Modüllerin dosya hakkında ürettiği sonuçlar
- Indexed Text: Arama motoru tarafından indekslenmiş metin
```

### Dosya Dışa Aktarma (Export)

```
# Tek dosyayı dışa aktarma
Dosyaya sağ tıklayın → Extract File(s)

# Birden fazla dosyayı toplu aktarma
Dosyaları seçin → Sağ tıklayın → Extract File(s)
```

### Silinen Dosyaları İnceleme

```
Views → Deleted Files menüsü altında:
- $Recycle.Bin (Geri Dönüşüm Kutusu) içindeki silinmiş dosyalar
- MFT üzerinde silindi olarak işaretlenmiş dosya girişleri
- PhotoRec tarafından kurtarılan başlık tabanlı dosyalar (carved files)
```

---

## 📊 Zaman Çizelgesi Analizi (Timeline)

### Zaman Çizelgesi Aracına Erişim

```
Tools → Timeline
# Veya Results sekmesi altından Timeline butonuna tıklayın
```

### Görünüm Modları

| Görünüm | Açıklama |
|---------|----------|
| **Counts View** | Olayların zaman içindeki yoğunluk çubuk grafiği |
| **Details View** | Belirli bir zaman aralığındaki olay detayları |
| **List View** | Olayların tablo biçiminde kronolojik listesi |

### Olay Türleri ve Renkleri

| Tür | Renk | Anlamı |
|-----|------|--------|
| File Modified | Mavi | Dosya içeriği değiştirildi |
| File Accessed | Yeşil | Dosya açıldı veya okundu |
| File Created | Sarı | Dosya ilk kez oluşturuldu |
| File Changed | Kırmızı | Meta veri veya izinler değiştirildi |
| Web Activity | Mor | Web tarayıcı oturum ve indirme olayı |

---

## 🔍 Anahtar Kelime Arama (Keyword Search)

### Arama Listeleri Oluşturma

```
Tools → Options → Keyword Search
1. "New List" butonuna basın
2. Liste adını girin (ör. "Finansal Veriler")
3. Anahtar kelimeleri ekleyin:
   - Literal (Birebir eşleşme)
   - Regex (Düzenli ifade)
   - Substring (Alt dize)
```

### Adli Bilişim İçin Düzenli İfade (Regex) Örnekleri

| Amaç | Düzenli İfade (Regex) |
|------|------------------------|
| E-posta Adresi | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IPv4 Adresi | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Kredi Kartı | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| URL Bağlantıları | `https?://[^\s]+` |

---

## 📝 Raporlama

### Rapor Üretme

```
Tools → Generate Report
# Veya vaka adına sağ tıklayıp Generate Report seçeneğini kullanın
```

### Rapor Türleri

| Biçim | Açıklama |
|-------|----------|
| **HTML Report** | Tarayıcıda açılabilen, resimli kapsamlı web raporu |
| **Excel Report** | Verilerin tablo halinde analiz edilebileceği elektronik tablo |
| **Text Report** | Basit düz metin formatında rapor |
| **Portable Case** | Autopsy ile doğrudan açılabilen taşınabilir vaka paketi |

---

## 💻 Komut Satırı Araçları (The Sleuth Kit - TSK)

Autopsy, arka planda The Sleuth Kit (TSK) araçlarını kullanır. Terminalden doğrudan çalıştırılabilen komutlar:

### İmaj ve Dosya Sistemi Bilgisi

```bash
# İmaj formatı ve boyutu hakkında bilgi alma
img_stat image.E01
img_stat image.dd

# Bölüm tablosunu listeleme (mmls)
mmls image.dd

# Dosya sistemi detayları (Ofset belirterek)
fsstat -o 2048 image.dd
```

### Dosya Listeleme (fls)

```bash
# Bölümdeki dosyaları özyinelemeli listele
fls -r -o 2048 image.dd

# Yalnızca silinmiş dosyaları listele (-d)
fls -r -d -o 2048 image.dd
```

### Dosya Kurtarma (icat)

```bash
# Inode / MFT numarasına göre dosyayı dışarı aktarma
icat -o 2048 image.dd 12345 > kurtarilan_dosya.bin
```

### Zaman Çizelgesi Oluşturma

```bash
# Body dosyası oluşturma
fls -r -m "/" -o 2048 image.dd > body.txt

# Mactime ile CSV formatında zaman çizelgesi üretme
mactime -b body.txt -d > timeline.csv
```

---

## 📊 Hızlı Referans

### İnceleme İş Akışı

```
1. Vaka Oluştur       → Case Name ve detayları gir
2. Veri Kaynağı Ekle  → Disk imajını veya fiziksel diski bağla
3. Modülleri Çalıştır → Otomatik analiz modüllerini başlat
4. Sonuçları İncele   → Web geçmişi, silinen dosyalar, USB kalıntıları
5. Arama Yap          → İsim, e-posta veya anahtar kelime ara
6. Kronoloji Çıkar    → Timeline ile olay örgüsünü kur
7. Delilleri Etiketle → Tag özelliği ile önemli bulguları işaretle
8. Rapor Oluştur      → HTML/Excel formatında adli rapor üret
```

### Kritik Windows Kalıntı Yolları

| Kalıntı Türü | Dosya Yolu |
|--------------|------------|
| Son Kullanılan Dosyalar | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| İndirilen Dosyalar | `Users\*\Downloads` |
| Tarayıcı Verileri | `Users\*\AppData\Local\[Tarayici]\User Data` |
| Geçici Dosyalar | `Users\*\AppData\Local\Temp` |
| Geri Dönüşüm Kutusu | `$Recycle.Bin` |
| Program Çalıştırma (Prefetch) | `Windows\Prefetch` |
| Olay Günlükleri (Event Logs) | `Windows\System32\winevt\Logs` |

---

## 📚 Kaynaklar

- [Autopsy Resmi Web Sitesi](https://www.autopsy.com/)
- [The Sleuth Kit (TSK)](https://www.sleuthkit.org/)
- [Autopsy Kullanıcı Belgeleri](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Eğitimi ve Kaynakları](https://www.dfir.training/)

### İlgili Başvuru Kılavuzları
- [Volatility](../../Volatility/README.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)
- [Wireshark](../../Wireshark/translations/README.tr.md)

---

<p align="center">
  <b>🔍 Dijital Adli Bilişimde Uzmanlaşın!</b><br>
  <i>Autopsy - Dijital soruşturmacıların en güvenilir yardımcısı</i>
</p>
