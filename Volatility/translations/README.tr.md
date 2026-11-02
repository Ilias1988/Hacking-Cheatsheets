[🇬🇧 English](../../README.md)

# 🧠 Volatility - Bellek Adli Bilişimi Başvuru Kılavuzu

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                    Bellek Adli Bilişimi Çerçevesi
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 İçindekiler

- [Volatility Nedir](#-volatility-nedir)
- [Kurulum](#-kurulum)
- [Temel Kullanım](#-temel-kullanım)
- [İmaj Tanımlama (Image Identification)](#-i̇maj-tanımlama-image-identification)
- [Süreç Analizi (Process Analysis)](#-süreç-analizi-process-analysis)
- [Ağ Analizi (Network Analysis)](#-ağ-analizi-network-analysis)
- [Kayıt Defteri Analizi (Registry Analysis)](#-kayıt-defteri-analizi-registry-analysis)
- [Zararlı Yazılım Analizi (Malware Analysis)](#-zararlı-yazılım-analizi-malware-analysis)
- [Kullanıcı & Kimlik Bilgisi Analizi](#-kullanıcı--kimlik-bilgisi-analizi)
- [Dosya & DLL Analizi](#-dosya--dll-analizi)
- [Zaman Çizelgesi Analizi (Timeline)](#-zaman-çizelgesi-analizi-timeline)
- [Volatility 3 Rehberi](#-volatility-3-rehberi)

---

## 🎯 Volatility Nedir

**Volatility**, RAM (uçucu bellek) dökümlerini analiz etmek için geliştirilmiş açık kaynaklı bellek adli bilişimi çatısıdır:

- 🔍 **Olay Müdahalesi (Incident Response)** - Bellekte çalışan canlı tehditleri ve zararlıları tespit etme
- 🦠 **Zararlı Yazılım Analizi** - Gizlenmiş süreçleri, enjekte edilmiş kodları ve hook'ları açığa çıkarma
- 🔐 **Kimlik Bilgisi Kurtarma** - RAM'de saklanan açık parolaları, hash'leri ve biletleri çekme
- 📊 **Zaman Çizelgesi Analizi** - Bellek olaylarını kronolojik sıraya koyarak saldırı anını canlandırma
- 🕵️ **Tehdit Avcılığı (Threat Hunting)** - Ağ bağlantıları ve sistem kalıntıları üzerinden tehdit arama

### Desteklenen Bellek Döküm Biçimleri

| Format | Açıklama |
|--------|----------|
| Ham (.raw, .mem, .dd) | Standart ham bellek dökümü |
| EWF (.E01) | Expert Witness adli imaj formatı |
| AFF | Gelişmiş adli bilişim formatı |
| Çökme Dökümleri (Crash dump) | Windows sistem hata dökümleri |
| VMware (.vmem) | VMware sanal makine anlık görüntüleri |
| VirtualBox (.sav) | VirtualBox anlık görüntüleri |
| HPAK (.hpak) | FastDump Pro formatı |
| LiME (.lime) | Linux Memory Extractor ile alınan dökümler |

---

## 🚀 Kurulum

### Volatility 2 (Eski Sürüm - Python 2)

```bash
# Depoyu klonlayın
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Bağımlılıkları kurun
pip install pycrypto distorm3 yara-python pillow openpyxl

# Çalıştırın
python vol.py -h
```

### Volatility 3 (Güncel Sürüm - Python 3)

```bash
# Pip üzerinden doğrudan kurulum
pip3 install volatility3

# Veya depoyu klonlayarak
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Çalıştırma
python3 vol.py -h
vol -h  # Pip ile kurulduysa
```

### Sembol Tablolarını İndirme (Volatility 3)

```bash
# Windows sembolleri
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Linux sembolleri
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Mac sembolleri
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# İndirilen arşivleri volatility3/symbols/ klasörüne çıkartın
```

---

## 💻 Temel Kullanım

### Volatility 2 Sözdizimi

```bash
# Temel komut yapısı
python vol.py -f <bellek_dokumu> --profile=<profil> <eklenti>

# Örnek:
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Volatility 3 Sözdizimi

```bash
# Temel sözdizimi (işletim sistemini ve profili otomatik algılar)
vol -f <bellek_dokumu> <eklenti>

# Örnek:
vol -f memory.raw windows.pslist
```

### Sık Kullanılan Seçenekler

```bash
# Volatility 2
-f DOSYA          # Bellek döküm dosyası
--profile=PROFIL  # İşletim sistemi profili
-h                # Yardım
--info            # Mevcut eklentileri listele

# Volatility 3
-f DOSYA          # Bellek döküm dosyası
-o DIZIN          # Çıktı kayıt klasörü
-r FORMAT         # Çıktı formatı (pretty, json, csv)
-p EKLENTI_DIZINI # Harici eklenti klasörü
```

---

## 🔍 İmaj Tanımlama (Image Identification)

### Profil Tespiti (Volatility 2)

```bash
# Bellek dökümü için önerilen profilleri göster
python vol.py -f memory.raw imageinfo

# Çekirdek hata ayıklayıcı bloğu üzerinden daha ayrıntılı tespit
python vol.py -f memory.raw kdbgscan
```

### İşletim Sistemi Tespiti (Volatility 3)

```bash
# Windows sistem bilgisi
vol -f memory.raw windows.info

# Linux sistem bilgisi
vol -f memory.lime linux.info

# macOS sistem bilgisi
vol -f memory.raw mac.info
```

---

## ⚙️ Süreç Analizi (Process Analysis)

### Süreçleri Listeleme

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Ağaç görünümü
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Gizli/silinmiş süreçleri bul

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Süreç Detayları ve Argümanlar

```bash
# Komut satırı argümanları (cmdline)
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Ortam değişkenleri
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Sürecin açık tuttuğu tanıtıcılar (Handles - Mutex, dosya, anahtar)
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# Süreç tarafından yüklenen DLL kütüphaneleri
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Gizlenmiş Süreçleri Tespit Etme (Rootkit)

```bash
# pslist ile psscan sonuçlarını çapraz karşılaştır
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Process Hollowing (süreç içi kod boşaltma) tespiti
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Süreç ve Bellek Dökümü Alma (Dump)

```bash
# Yürütülebilir EXE dosyasını bellekten çıkar
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Sürecin tüm bellek alanını dök
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Ağ Analizi (Network Analysis)

### Ağ Bağlantıları

```bash
# Aktif ve geçmiş ağ bağlantıları (Volatility 2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista ve üzeri
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Volatility 3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

---

## 📝 Kayıt Defteri Analizi (Registry Analysis)

### Kovanları (Hives) Listeleme

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Volatility 3
vol -f memory.raw windows.registry.hivelist
```

### Kayıt Defteri Anahtarlarını İnceleme

```bash
# Belirli bir anahtarın içeriğini yazdırma
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Kovanı dosyaya kaydetme
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Kritik Kayıt Defteri Anahtarları

```bash
# Otomatik başlayan programlar (Autorun)
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Servisler
printkey -K "SYSTEM\CurrentControlSet\Services"

# Kullanıcı hesapları
printkey -K "SAM\Domains\Account\Users"

# Son açılan belgeler
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### UserAssist (Çalıştırılan Program İstatistikleri)

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Volatility 3
vol -f memory.raw windows.registry.userassist
```

---

## 🦠 Zararlı Yazılım Analizi (Malware Analysis)

### Malfind (Enjekte Edilmiş Kod Bulma)

```bash
# Belleğe enjekte edilmiş kodları tara
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Enjekte edilen kod parçalarını dosyaya çıkar
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### SSDT ve Sürücü Kancalamaları (Hooks)

```bash
# Sistem Servis Tanımlayıcı Tablosunu (SSDT) tara
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Şüpheli çekirdek kancalarını filtrele
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"

# Sürücü IRP kancalamalarını incele
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Sürücü Taraması (Driverscan)

```bash
# Yüklü çekirdek sürücülerini listele
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Bellekten kaldırılmış (Unloaded) sürücüleri listele
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules
```

### YARA Taraması

```bash
# YARA kural dosyası ile tüm belleği tara
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "aranacak_dizge"

# Volatility 3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

---

## 🔐 Kullanıcı & Kimlik Bilgisi Analizi

### Kullanıcı Bilgileri

```bash
# Kullanıcı SID numaralarını al
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# En son oturum açan kullanıcı
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Parola Hash Değerlerini Çıkarma

```bash
# SAM veritabanındaki kullanıcı parola hash'lerini dök
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# LSA sırlarını (servis parolaları vb.) çıkar
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump
vol -f memory.raw windows.lsadump

# Önbelleğe alınmış etki alanı kimlik bilgileri (DCC/DCC2)
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Pano (Clipboard) Verileri

```bash
# Oturum anında panoda kopyalanmış metni görüntüle
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Dosya & DLL Analizi

### Bellekteki Dosyaları Tarama (Filescan)

```bash
# Bellekte açık olan veya önbelleğe alınan dosyaları listele
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Belirli dosya türlerini filtreleme
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Dosyaları Bellekten Dışarı Aktarma (Dumpfiles)

```bash
# Ofset adresine göre dosyayı bellekten çıkar
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFSET>
```

### DLL Analizi

```bash
# Süreçlere yüklenen DLL kütüphanelerini listele
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Belirli bir DLL dosyasını bellekten çıkar
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

---

## 📅 Zaman Çizelgesi Analizi (Timeline)

### Zaman Çizelgesi Üretme

```bash
# Volatility 2 ile body formatında timeline üret
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Volatility 3 ile doğrudan oluşturma
vol -f memory.raw timeliner.Timeliner
```

### MFT (Master File Table) Analizi

```bash
# Bellekte kalan MFT kayıtlarını ayrıştır
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3 Rehberi

### Volatility 2 ve Volatility 3 Farkları

| Özellik | Volatility 2 | Volatility 3 |
|---------|--------------|--------------|
| Profil Belirleme | Manuel (`--profile=`) | Otomatik Algılama |
| Eklenti İsimleri | `pslist` | `windows.pslist` |
| Hız & Performans | Daha yavaş | Çok daha hızlı |
| Python Sürümü | Python 2.7 | Modern Python 3 |
| Çıktı Biçimleri | Yalnızca metin | JSON, CSV, tablo |

### Volatility 3 Temel Eklenti Tablosu

```bash
# Süreçler
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Ağ
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Kayıt Defteri
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Zararlı Yazılım
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Kimlik Bilgileri
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump

# Dosyalar
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

---

## 📊 Hızlı Referans

### Olay İnceleme İş Akışı

```
1. windows.info / imageinfo  → İşletim sistemini ve mimariyi doğrula
2. pslist / pstree / psscan  → Şüpheli süreçleri ve hiyerarşiyi incele
3. cmdline                   → Süreçlerin komut satırı parametrelerini kontrol et
4. netscan                   → Dış sunuculara yapılan C2 bağlantılarını tespit et
5. malfind                   → Bellek içine kod enjekte edilmiş süreçleri yakala
6. dlllist / handles         → Sürecin yüklü modüllerini ve mutex tanıtıcılarını bul
7. filescan / dumpfiles      → Şüpheli dosyaları bellekten çıkar
8. hashdump / lsadump        → Ele geçirilen kimlik bilgilerini raporla
9. timeliner                 → Olay örgüsünün kronolojik zaman çizelgesini çıkar
```

---

## 📚 Kaynaklar

- [Volatility Foundation Resmi Sitesi](https://www.volatilityfoundation.org/)
- [Volatility 3 GitHub Deposu](https://github.com/volatilityfoundation/volatility3)
- [SANS Bellek Adli Bilişimi Referans Kartı](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### İlgili Başvuru Kılavuzları
- [Mimikatz](../../Mimikatz/translations/README.tr.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)
- [Windows PrivEsc (Yetki Yükseltme)](../../Windows-PrivEsc/translations/README.tr.md)

---

<p align="center">
  <b>🧠 Bellek Adli Bilişiminde Uzmanlaşın!</b><br>
  <i>Bellek asla yalan söylemez - Gerçeği RAM'den ortaya çıkarın</i>
</p>
