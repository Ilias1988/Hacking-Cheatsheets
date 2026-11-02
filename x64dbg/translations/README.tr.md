[🇬🇧 English](../../README.md)

# 🔧 x64dbg - Windows Hata Ayıklayıcı Başvuru Kılavuzu

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Windows x64/x32 Hata Ayıklayıcı
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 İçindekiler

- [x64dbg Nedir](#-x64dbg-nedir)
- [Kurulum](#-kurulum)
- [Arayüze Genel Bakış](#-arayüze-genel-bakış)
- [Temel Kullanım](#-temel-kullanım)
- [Kesme Noktaları (Breakpoints)](#-kesme-noktaları-breakpoints)
- [Adımlama & Yürütme (Stepping & Execution)](#-adımlama--yürütme-stepping--execution)
- [Bellek İşlemleri (Memory Operations)](#-bellek-i̇şlemleri-memory-operations)
- [Yazmaçlar (Registers)](#-yazmaçlar-registers)
- [Yığın Analizi (Stack Analysis)](#-yığın-analizi-stack-analysis)
- [Yama ve Kod Düzenleme (Patching)](#-yama-ve-kod-düzenleme-patching)
- [Betik Desteği (Scripting)](#-betik-desteği-scripting)
- [Klavye Kısayolları](#-klavye-kısayolları)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 x64dbg Nedir

**x64dbg**, Windows platformu için açık kaynaklı x64 ve x32 kullanıcı kipi (user-mode) hata ayıklayıcısıdır. Efsanevi OllyDbg aracının modern halefidir:

- 🔍 **Kullanıcı Kipi Hata Ayıklama** - Windows uygulamalarını ve süreçlerini analiz etme
- 🦠 **Zararlı Yazılım Analizi** - Şüpheli ikili dosyaları kontrollü ortamda inceleme
- 💥 **İstismar Geliştirme** - Windows bellek taşmaları ve güvenlik açıklarını analiz etme
- 🔧 **Tersine Mühendislik** - Derlenmiş kod akışını ve algoritmaları çözme
- 🧩 **Eklenti Desteği** - Zengin eklenti (plugin) ekosistemiyle genişletilebilirlik

### Temel Özellikler

| Özellik | Açıklama |
|---------|----------|
| **Modern GUI** | Tamamen özelleştirilebilir, hızlı kullanıcı arayüzü |
| **Tam 64-bit Desteği** | Hem 32-bit (x32dbg) hem de 64-bit (x64dbg) yerel desteği |
| **Dahili Betik Dili** | Karmaşık hata ayıklama işlemlerini otomatikleştirme |
| **Geniş Eklenti Desteği** | Topluluk tarafından geliştirilen onlarca eklenti |
| **Sembol Desteği** | Microsoft PDB ve kaynak sembollerini çözme |
| **Açık Kaynak** | Tamamen ücretsiz ve aktif geliştirme |

---

## 🚀 Kurulum

### İndirme

```
# Resmi web sitesi
https://x64dbg.com/

# GitHub sürümleri
https://github.com/x64dbg/x64dbg/releases

# İndirilecek dosya: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Kurulum Adımları

```
1. İndirilen ZIP arşivini kalıcı bir klasöre çıkarın (ör. C:\x64dbg\)
2. x96dbg.exe başlatıcısını çalıştırın:
   - x32dbg.exe → 32-bit uygulamalar için
   - x64dbg.exe → 64-bit uygulamalar için
3. (İsteğe bağlı) Masaüstü kısayolları oluşturun veya PATH ortam değişkenine ekleyin
```

### Önerilen Eklentiler

| Eklenti | Kullanım Amacı |
|---------|----------------|
| **ScyllaHide** | Gelişmiş Anti-Debug atlatma çözümü |
| **SwissArmyKnife** | Çeşitli analiz ve tersine mühendislik yardımcıları |
| **x64dbgpy** | Python betik entegrasyonu |
| **TitanHide** | Çekirdek seviyesi (Kernel) anti-debug atlatma |
| **OllyDumpEx** | Süreç bellek dökümü alma (Dumping) |
| **Multiline Ultimate Assembler** | Çok satırlı assembly yamalama aracı |

### Eklenti Kurulumu

```
1. Eklentinin DLL dosyasını indirin
2. İlgili klasöre kopyalayın:
   - 32-bit: x64dbg\release\x32\plugins\
   - 64-bit: x64dbg\release\x64\plugins\
3. x64dbg uygulamasını yeniden başlatın
```

---

## 🖥️ Arayüze Genel Bakış

### Ana Pencereler

| Pencere | Açıklama | Kısayol |
|---------|----------|---------|
| **CPU** | Assembly ayrıştırıcı ve yürütme ekranı | Alt+C |
| **Log** | Hata ayıklama günlüğü ve mesajlar | Alt+L |
| **Memory Map** | Sürecin bellek bölümleri ve izinleri | Alt+M |
| **Call Stack** | Çağrı yığını dökümü | Alt+K |
| **Breakpoints** | Tanımlı tüm kesme noktaları | Alt+B |
| **Symbols** | Yüklenen modül ve API sembolleri | Alt+E |
| **Threads** | Sürece ait iş parçacıkları | Alt+T |
| **Handles** | İşletim sistemi tanıtıcıları (Handles) | Alt+H |

### CPU Penceresi Bölümleri

```
┌─────────────────┬──────────────────┐
│   Assembly      │    Yazmaçlar     │
│  (Disassembly)  │   (Registers)    │
├─────────────────┼──────────────────┤
│   Hex Dökümü    │    Yığın         │
│   (Hex Dump)    │   (Stack)        │
└─────────────────┴──────────────────┘
```

---

## 💻 Temel Kullanım

### Hata Ayıklamaya Başlama

```
# Dosya açma
File → Open (Kısayol: F3)

# Çalışan sürece bağlanma (Attach)
File → Attach (Kısayol: Alt+A)

# Çalıştırma
Debug → Run (Kısayol: F9)

# Baştan başlatma
Debug → Restart (Kısayol: Ctrl+F2)

# Hata ayıklamayı durdurma
Debug → Stop (Kısayol: Alt+F2)
```

### Komut Satırı Çubuğu

```
# Alt kısımdaki komut satırına komut yazabilirsiniz:

# Adrese gitme
g 0x401000
goto 0x401000

# Fonksiyon veya API adına gitme
g GetProcAddress
g kernel32.LoadLibraryA

# Dize (string) arama
/s "password"

# Onaltılık bayt arama
/x 90 90 90 90

# Joker karakterli desen arama
/p "8B ?? 89"
```

### Gezinme

```
# Adrese gitme diyaloğu
Ctrl+G → Adresi girin

# Program giriş noktasına (Entry Point) gitme
Debug → Entry Point

# Ayrıştırıcıda takip etme
Sağ tıklayın → Follow in Disassembler

# Bir önceki konuma dönme
- (eksi tuşu)

# İleri konuma gitme
+ (artı tuşu)
```

---

## 🔴 Kesme Noktaları (Breakpoints)

### Yazılımsal Kesme Noktaları (Software / INT3)

```
# Seçili adrese kesme noktası koyma / kaldırma
F2

# Komut satırından kesme noktası koyma
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Koşullu yazılımsal kesme noktası
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Modül yüklendiğinde durma
bp $module(kernel32)
```

### Donanımsal Kesme Noktaları (Hardware Breakpoints)

```
# Menüden donanımsal kesme noktası koyma (En fazla 4 adet)
Sağ tıklayın → Breakpoint → Hardware → Execute (Çalıştırma)
Sağ tıklayın → Breakpoint → Hardware → Access (Erişim)
Sağ tıklayın → Breakpoint → Hardware → Write (Yazma)

# Komut ile koyma
bph 0x401000, x, 1    # Çalıştırma (Execute)
bph 0x401000, r, 4    # Okuma (4 bayt)
bph 0x401000, w, 4    # Yazma (4 bayt)
```

### Bellek Kesme Noktaları (Memory Breakpoints)

```
# Menüden bellek kesme noktası
Dump penceresinde sağ tıklayın → Breakpoint → Memory → Access
Dump penceresinde sağ tıklayın → Breakpoint → Memory → Write

# Komut satırından
bpm 0x401000, r       # Okuma
bpm 0x401000, w       # Yazma
bpm 0x401000, x       # Çalıştırma
```

### Koşullu Kesme Noktaları

```
# Koşul sağlandığında durdur
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Çağrı sayısına göre durdur (5. vuruşta dur)
bp 0x401000, "", 5

# Durmadan yalnızca log kaydetme (bplog)
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Kesme Noktası Yönetimi

```
# Kesme noktalarını listeleme
Alt+B tuşlarına basın

# Kesme noktasını silme
bc 0x401000          # Belirli adrestekini sil
bc                   # Tümünü sil

# Devre dışı bırakma / Etkinleştirme
bd 0x401000          # Devre dışı bırak
be 0x401000          # Etkinleştir
bd *                 # Hepsini kapat
be *                 # Hepsini aç
```

---

## ▶️ Adımlama & Yürütme (Stepping & Execution)

### Adımlama İşlemleri

| Eylem | Kısayol | Açıklama |
|-------|---------|----------|
| Step Into | F7 | Tek bir talimat yürüt (çağrıların içine gir) |
| Step Over | F8 | Talimatın üzerinden geç (çağrıları doğrudan çalıştır) |
| Step Out | Ctrl+F9 | Fonksiyon bitip dönene kadar yürüt |
| Run to Selection | F4 | İmlecin bulunduğu satıra kadar çalıştır |
| Run to User Code | Alt+F9 | Sistem DLL'lerinden çıkıp kullanıcı koduna dön |

### Yürütme Kontrolleri

| Eylem | Kısayol | Açıklama |
|-------|---------|----------|
| Run | F9 | Normal yürütmeye devam et |
| Run Skipping | Ctrl+F7 | Geçerli talimatı yürütmeden atla |
| Pause | F12 | Programı anında duraklat |
| Restart | Ctrl+F2 | Hata ayıklamayı baştan başlat |
| Close | Alt+F2 | Süreci kapat ve hata ayıklamayı durdur |

### İzleme (Trace)

```
# Talimatların içine girerek izleme kaydı al
Debug → Trace Into (Ctrl+F7)

# Talimatların üzerinden geçerek izleme al
Debug → Trace Over (Ctrl+F8)

# İzleme kaydı başlat / durdur
Debug → Trace Record
Debug → Stop Trace
```

### Talimat Atlatma (New Origin)

```
# Bir talimatı çalıştırmadan EIP/RIP yazmacını o noktaya taşıma
Satıra sağ tıklayın → Set New Origin Here (Kısayol: Ctrl+Shift+O)
```

---

## 🔍 Bellek İşlemleri (Memory Operations)

### Hex Dökümü (Memory Dump)

```
# Adresi Dump penceresinde takip etme
Sağ tıklayın → Follow in Dump → Address / Selection

# Dump penceresinde adrese gitme
Dump penceresindeyken Ctrl+G

# Veri görünüm türünü değiştirme
Sağ tıklayın → Hex → [Byte / Word / Dword / Qword / ASCII / Unicode]
```

### Bellek Haritası (Memory Map)

```
# Bellek haritasını açma
View → Memory Map (Kısayol: Alt+M)

# Sütun bilgileri:
- Adres (Address)
- Boyut (Size)
- Bölüm adı (.text, .data, .rsrc)
- Tür (Image, Private, Mapped)
- Erişim İzinleri (Execute, Read, Write)
- Modül adı
```

### Bellekte Arama

```
# Komut satırından arama
/s "aranan_metin"     # ASCII dizesi
/s L"aranan_metin"    # Unicode dizesi
/x 90 90 90           # Hex bayt dizisi
/p "8B ?? 89"         # Joker karakterli bayt deseni

# Referansları bulma
Sağ tıklayın → Find references to → Selected Address

# Tüm dizgileri listeleme
Sağ tıklayın → Search for → All Strings
```

### Bellek Dökümünü Dosyaya Kaydetme

```
# Bellek bölümünü diske yazma
Memory Map penceresinde bölüme sağ tıklayın → Dump Memory to File

# OllyDumpEx eklentisi ile PE dökümü alma
Plugins → OllyDumpEx → Dump
```

---

## 📊 Yazmaçlar (Registers)

### Yazmaç Paneli

```
# Genel Amaçlı Yazmaçlar (64-bit)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Genel Amaçlı Yazmaçlar (32-bit)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Segment Yazmaçları
CS, DS, SS, ES, FS, GS

# Bayraklar (Flags - EFLAGS / RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Yazmaç Değerlerini Değiştirme

```
# Değiştirmek istediğiniz yazmaca çift tıklayın
# Veya komut satırından atama yapın:
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Zero Flag (Sıfır bayrağını) aktif et
```

### Önemli CPU Bayrakları

| Bayrak | Tam Adı | Tetiklenme Durumu |
|--------|---------|-------------------|
| ZF | Zero Flag | İşlem sonucu 0 olduğunda |
| CF | Carry Flag | İşaretsiz taşma (overflow) olduğunda |
| SF | Sign Flag | İşlem sonucu negatif olduğunda |
| OF | Overflow Flag | İşaretli taşma olduğunda |
| DF | Direction Flag | Dize işlemlerinde yön (artan/azalan) |
| TF | Trap Flag | Tek adım modu (Single step) |

### Bayrak Değerini Tersine Çevirme

```
# Yazmaçlar panelindeki bayrak harfine tıklayın
# Veya komut satırından:
r zf=!zf         # ZF bayrağını tersine çevir
r cf=0           # CF bayrağını temizle
r sf=1           # SF bayrağını 1 yap
```

---

## 📚 Yığın Analizi (Stack Analysis)

### Yığın Paneli (Stack Panel)

```
# Yığın görünümündeki bilgiler:
- Bellek Adresi
- Saklanan Değer (Value)
- Çözümlenen Sembol ve Yorumlar

# Sağ tık seçenekleri:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify (Değeri düzenle)
```

### Çağrı Yığını (Call Stack)

```
# Çağrı yığınını açma
View → Call Stack (Kısayol: Alt+K)

# Görünüm alanları:
- Dönüş Adresi (Return Address)
- Çağıran (Caller)
- Hedef (Callee)
- Boyut ve parametreler
```

### Yığın Düzeni (32-bit stdcall Örneği)

```
[ESP]      → Dönüş Adresi
[ESP+4]    → 1. Parametre (Arg1)
[ESP+8]    → 2. Parametre (Arg2)
[ESP+C]    → 3. Parametre (Arg3)

Fonksiyon başında PUSH EBP; MOV EBP, ESP çalıştıktan sonra:
[EBP]      → Önceki EBP değeri
[EBP+4]    → Dönüş Adresi
[EBP+8]    → 1. Parametre
[EBP-4]    → 1. Yerel değişken
```

---

## 🔧 Yama ve Kod Düzenleme (Patching)

### Assembly Talimatı Derleme (Assemble)

```
# Satırı seçin → Space (Boşluk) tuşuna basın
# Yeni assembly talimatını girin:

NOP                    # İşlem yapma (No-op)
JMP 0x401020           # Doğrudan atla
MOV EAX, 0             # EAX'i sıfırla
XOR EAX, EAX           # EAX'i sıfırla (1 bayt daha kısa)
RET                    # Fonksiyondan dön
```

### NOP ile Doldurma (Fill with NOPs)

```
# Satırları seçin → Sağ tıklayın → Binary → Fill with NOPs
# Veya Space tuşuna basıp arka arkaya NOP yazın
```

### Baytları Doğrudan Düzenleme

```
# Onaltılık baytları elle düzenleme
Sağ tıklayın → Binary → Edit

# Seçili alanı belirli bir bayt ile doldurma
Sağ tıklayın → Binary → Fill
```

### Sık Kullanılan Yamalar

| Önceki Kod | Sonraki Kod | Amaç |
|------------|-------------|------|
| `74 XX` (JE) | `75 XX` (JNE) | Koşulu tersine çevir |
| `75 XX` (JNE) | `74 XX` (JE) | Koşulu tersine çevir |
| `74 XX` (JE) | `EB XX` (JMP) | Koşulsuz her zaman atla |
| `74 XX` (JE) | `90 90` (NOP) | Asla atlama, devam et |
| `0F 84` (JE) | `0F 85` (JNE) | Uzak koşulu tersine çevir |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Fonksiyon çağrısını iptal et |

### Yamaları Kalıcı Olarak Kaydetme

```
# Yöntem: Patch File menüsü
File → Patch File
Yamaların uygulanacağı dosyayı onaylayın ve yeni bir isimle kaydedin
```

---

## 📝 Betik Desteği (Scripting)

### Betik Sözdizimi

```
# Değişken atama
$var = 0x401000

# Karşılaştırma
cmp eax, 0

# Koşullu atlama
je etiket
jne etiket

# Etiket tanımlama
etiket:

# Açıklama satırı
; Bu bir açıklama satırıdır
```

### Örnek Betik

```
; WinMain fonksiyonunu arayan basit betik
mov $addr, 0x401000        ; Başlangıç adresi
dongu:
  find $addr, "8B EC"       ; PUSH EBP; MOV EBP, ESP desenini ara
  cmp $result, 0
  je bulunamadi
  msg "Bulundu: {$result}"
  add $addr, $result
  add $addr, 1
  jmp dongu
bulunamadi:
  msg "Desen bulunamadı"
ret
```

### Sık Kullanılan Betik Komutları

| Komut | Açıklama |
|-------|----------|
| `msg "metin"` | Ekrana mesaj kutusu çıkar |
| `log "metin"` | Log penceresine yazdır |
| `find addr, "pattern"` | Deseni ara |
| `bp addr` | Kesme noktası koy |
| `bc addr` | Kesme noktasını kaldır |
| `sti` | Step into yap |
| `sto` | Step over yap |
| `run` | Çalıştır |
| `ret` | Betiği sonlandır |

---

## ⌨️ Klavye Kısayolları

### Genel Kısayollar

| Kısayol | İşlem |
|---------|-------|
| F3 | Dosya aç |
| Alt+A | Sürece bağlan (Attach) |
| Ctrl+F2 | Hata ayıklamayı baştan başlat |
| Alt+F2 | Hata ayıklamayı sonlandır |
| Ctrl+G | Belirtilen adrese git |
| Ctrl+F | Metin / kod ara |

### Yürütme ve Adımlama Kısayolları

| Kısayol | İşlem |
|---------|-------|
| F2 | Kesme noktası koy / kaldır |
| F4 | İmlece kadar çalıştır (Run to selection) |
| F7 | Step Into (İçeri adımla) |
| F8 | Step Over (Üzerinden adımla) |
| F9 | Run (Çalıştır) |
| F12 | Pause (Duraklat) |
| Ctrl+F9 | Dönüşe kadar çalıştır (Run until return) |

### Görünüm Kısayolları

| Kısayol | Görünüm |
|---------|---------|
| Alt+C | CPU Ekranı |
| Alt+L | Log Ekranı |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Kesme Noktaları |
| Alt+E | Semboller (Symbols) |
| Alt+T | İş Parçacıkları (Threads) |

---

## 📊 Hızlı Referans

### Sık Kullanılan Makine Kodu Kalıpları

| Baytlar | Assembly Talimatı | Anlamı |
|---------|-------------------|--------|
| `90` | NOP | İşlemsiz bayt |
| `CC` | INT3 | Hata ayıklama kesme baytı |
| `C3` | RET | Fonksiyon dönüşü |
| `55` | PUSH EBP | Standart fonksiyon girişi (Prolog) |
| `8B EC` | MOV EBP, ESP | Yığın çerçevesi kurulumu |
| `74 XX` | JE short | Eşitse kısa mesafe atla |
| `75 XX` | JNE short | Eşit değilse kısa mesafe atla |
| `EB XX` | JMP short | Koşulsuz kısa atla |
| `E8 XX XX XX XX` | CALL near | Yakın fonksiyon çağrısı |
| `E9 XX XX XX XX` | JMP near | Yakın koşulsuz atlama |

### Anti-Debug Korumalarını Aşma

```
# ScyllaHide eklentisini kullanın
Plugins → ScyllaHide → Options → Uygun profili seçin

# Manuel olarak kontrol noktalarını yamalayın:
- IsDebuggerPresent çağrılarını NOP ile doldurun
- PEB.BeingDebugged baytını 0 yapın
- İstisnaları (Exceptions) doğru şekilde yönetin
```

---

## 📚 Kaynaklar

- [x64dbg Resmi Sitesi](https://x64dbg.com/)
- [x64dbg GitHub Deposu](https://github.com/x64dbg/x64dbg)
- [x64dbg Yardım Belgeleri](https://help.x64dbg.com/)
- [x64dbg Eklentileri Listesi](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### İlgili Başvuru Kılavuzları
- [Ghidra](../../Ghidra/translations/README.tr.md)
- [GDB](../../GDB/translations/README.tr.md)
- [Windows PrivEsc (Yetki Yükseltme)](../../Windows-PrivEsc/translations/README.tr.md)

---

<p align="center">
  <b>🔧 Windows Hata Ayıklamada Uzmanlaşın!</b><br>
  <i>x64dbg - Modern çağın en güçlü Windows hata ayıklayıcısı</i>
</p>
