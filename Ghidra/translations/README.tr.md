[🇬🇧 English](../../README.md)

# 🔍 Ghidra - Tersine Mühendislik Başvuru Kılavuzu

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              NSA Tersine Mühendislik Paketi
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

## 📋 İçindekiler

- [Ghidra Nedir](#-ghidra-nedir)
- [Kurulum](#-kurulum)
- [Arayüze Genel Bakış](#-arayüze-genel-bakış)
- [Proje Oluşturma](#-proje-oluşturma)
- [Temel Analiz](#-temel-analiz)
- [Gezinme (Navigation)](#-gezinme-navigation)
- [Decompiler (Tersine Çevirici)](#-decompiler-tersine-çevirici)
- [Veri Türleri & Yapılar (Structures)](#-veri-türleri--yapılar-structures)
- [Yama ve Değişiklik Yapma (Patching)](#-yama-ve-değişiklik-yapma-patching)
- [Betik Desteği (Scripting)](#-betik-desteği-scripting)
- [Klavye Kısayolları](#-klavye-kısayolları)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 Ghidra Nedir

**Ghidra**, ABD Ulusal Güvenlik Ajansı (NSA) tarafından geliştirilmiş, ücretsiz ve açık kaynaklı bir yazılım tersine mühendislik (SRE) çerçevesidir:

- 🔍 **Ayrıştırıcı (Disassembler)** - Makine kodunu assembly talimatlarına dönüştürür
- 🔄 **Decompiler (Geri Derleyici)** - İkili dosyadan C benzeri kaynak kod (pseudocode) üretir
- 📊 **Otomatik Analiz** - Fonksiyonları, veri türlerini ve referansları analiz eder
- 🔧 **Yama Desteği (Patching)** - İkili dosya talimatlarını düzenler
- 🐍 **Betik Desteği** - Python ve Java ile analiz otomasyonu sağlar
- 👥 **Ekip Çalışması** - Ortak sunucu üzerinden çok kullanıcılı iş birliği sunar

### Desteklenen Mimariler

| Mimari | Çeşitler |
|--------|----------|
| **x86** | 16 / 32 / 64-bit |
| **ARM** | ARM32, ARM64 (AArch64), Thumb |
| **MIPS** | 32 / 64-bit |
| **PowerPC** | 32 / 64-bit |
| **SPARC** | 32 / 64-bit |
| **RISC-V** | 32 / 64-bit |
| **AVR** | 8-bit mikrodenetleyiciler |
| **6502** | 8-bit klasik işlemciler |

### Desteklenen Dosya Biçimleri

| Format | Açıklama |
|--------|----------|
| ELF | Linux çalıştırılabilir dosyaları |
| PE / COFF | Windows çalıştırılabilir dosyaları (EXE, DLL) |
| Mach-O | macOS / iOS çalıştırılabilir dosyaları |
| Ham İkili (Raw) | Bellenim (Firmware), ROM dökümleri |
| APK / DEX | Android uygulamaları ve bayt kodları |
| ve çok daha fazlası... |

---

## 🚀 Kurulum

### İndirme

```bash
# Resmi web sitesi
https://ghidra-sre.org/

# GitHub sürümleri
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Sistem Gereksinimleri

| Gereksinim | Asgari Sürüm / Miktar |
|------------|------------------------|
| **JDK** | Java 17+ (64-bit) |
| **RAM** | En az 4 GB (8 GB ve üzeri önerilir) |
| **Disk** | Kurulum için en az 1 GB boş alan |

### Linux / macOS Kurulumu

```bash
# JDK 17+ kurulumu
sudo apt install openjdk-17-jdk  # Debian / Ubuntu / Kali
brew install openjdk@17          # macOS

# Ghidra arşivini açma
unzip ghidra_*.zip
cd ghidra_*

# Başlatma
./ghidraRun
```

### Windows Kurulumu

```batch
:: Adoptium veya Oracle üzerinden JDK 17+ kurun
:: ghidra_*.zip arşivini klasöre çıkartın
:: ghidraRun.bat dosyasını çalıştırın
```

---

## 🖥️ Arayüze Genel Bakış

### Temel Pencereler

| Pencere | Açıklama |
|---------|----------|
| **Program Trees** | Proje dosya yapısı ve bellek bölümleri |
| **Symbol Tree** | Fonksiyonlar, etiketler, sınıflar ve import/export listesi |
| **Data Type Manager** | Tanımlı veri türleri ve yapılar (C types, struct, enum) |
| **Listing** | Assembly (ayrıştırılmış kod) görünümü |
| **Decompiler** | C tarzı derlenmiş kaynak kod görünümü |
| **Console** | Çıktı ve betik log ekranı |
| **Defined Strings** | Programdaki sabit metinler (strings) |
| **Bookmarks** | Kaydedilen yer imleri |

### Listing Penceresi (Disassembly)

```
Adres      | Baytlar        | Assembly Talimatı     | Yorumlar
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Fonksiyon başlangıcı
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Yığın çerçevesi
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Proje Oluşturma

### Yeni Proje Açma

```
1. File → New Project
2. Proje türünü seçin:
   - Non-Shared Project (Yerel tek kullanıcı)
   - Shared Project (Ekip çalışması sunucusu)
3. Proje dizinini belirleyin
4. Proje adını girin
5. Finish butonuna tıklayın
```

### İkili Dosyayı İçe Aktarma (Import)

```
1. File → Import File (Kısayol: I)
2. İncelenecek dosyayı seçin
3. Dosya formatını doğrulayın (genellikle otomatik algılanır)
4. Mimari ve derleyici ayarlarını onaylayın:
   - Language / Processor
   - Compiler spec
5. OK butonuna basın
6. "Analyze?" sorusuna → Yes (Evet) deyin
```

### Analiz Seçenekleri

| Seçenek | Açıklama |
|---------|----------|
| ASCII Strings | Metin dizgilerini otomatik bul |
| Decompiler | Geri derleme motorunu etkinleştir |
| Stack Analysis | Yığın çerçevesi ve yerel değişken analizi |
| Reference Analysis | Çapraz referansları (XREF) çöz |
| Function ID | Bilinen standart kütüphane fonksiyonlarını eşle |
| Call Convention | Fonksiyon çağırma standartlarını tespit et |

---

## 🔬 Temel Analiz

### Otomatik Analiz (Auto Analysis)

```
Analysis → Auto Analyze (Kısayol: A)
- Seçilen tüm analiz modüllerini sırayla çalıştırır
- Büyük ikili dosyalarda biraz zaman alabilir
- İlerleme durumu sağ alttaki durum çubuğundan izlenebilir
```

### Tek Seferlik Analizciler (One-Shot)

```
Analysis → One Shot → [İstenen Analizci]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Fonksiyon Tanımlama

```
# Tüm fonksiyonları otomatik tara
Analysis → Auto Analyze → "Non-returning Functions" seçeneğini aç

# Manuel fonksiyon oluşturma
Adrese sağ tıklayın → Create Function (Kısayol: F)

# Tanımsız kod bloklarını çözme
Sağ tıklayın → Disassemble (Kısayol: D)
```

---

## 🧭 Gezinme (Navigation)

### Adrese veya Sembole Gitme (Go To)

```
# Kısayol: G tuşuna basın → Hedefi yazın → Enter

# Örnek hedefler:
0x00401000      # Onaltılık bellek adresi
main            # Fonksiyon ismi
entry           # Program giriş noktası
LAB_00401234    # Ghidra etiket ismi
```

### Gezinme Kısayolları

| Kısayol | İşlem |
|---------|-------|
| `G` | Belirtilen adrese git (Go to) |
| `Alt+←` | Bir önceki konuma dön (Geri) |
| `Alt+→` | İleri konuma git |
| `Ctrl+Shift+E` | Program giriş noktasına (Entry Point) git |

### Çapraz Referanslar (XREFs)

```
# Geçerli konuma yapılan referansları inceleme
Sağ tıklayın → References → Show References To

# Bir fonksiyonun nereden çağrıldığını bulma
Fonksiyonu seçin → Sağ tıklayın → References → Show References To

# Kısayol ile arama
Ctrl+Shift+F → Referansları bul
```

### Arama Menüsü

```
# Arama seçenekleri
Search → Memory (S)           # Bellekte değer arama
Search → For Strings          # Metin dizgilerini listeleme
Search → For Direct References # Adrese olan doğrudan referansları bulma
Search → Program Text         # Assembly kodunda metin arama

# Fonksiyon arama
Symbol Tree penceresindeki Filter kutusuna fonksiyon adını yazın
```

---

## 💻 Decompiler (Tersine Çevirici)

### Decompiler Penceresini Açma

```
Window → Decompiler
# Veya sekmelerden Decompiler sekmesine tıklayın
# Listing'de seçilen fonksiyon otomatik olarak C koduna dönüştürülür
```

### Geri Derlenmiş Kod Örneği

```c
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Parolayı girin: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "gizli123") == 0) {
    puts("Erişim onaylandı!");
  }
  else {
    puts("Erişim reddedildi!");
  }
  return 0;
}
```

### Geri Derleme Kalitesini Artırma

```
# Değişkenleri yeniden adlandırma
Değişkene sağ tıklayın → Rename Variable (Kısayol: L)

# Veri türünü değiştirme
Değişkene sağ tıklayın → Retype Variable (Kısayol: Ctrl+L)

# Yorum ekleme
; tuşuna basın → Satır sonu yorumu (EOL)
Ctrl+; → Blok başlık yorumu (Plate comment)

# Fonksiyon imzasını düzenleme
Fonksiyon adına sağ tıklayın → Edit Function Signature
```

### Decompiler İpuçları

| Eylem | Yöntem |
|-------|--------|
| Değişken yeniden adlandırma | `L` veya sağ tık |
| Tür değiştirme | `Ctrl+L` |
| Yapı (struct) otomatik oluşturma | Sağ tık → Auto Create Structure |
| Değişken bölme / birleştirme | Sağ tık → Split / Merge |
| İmzayı sabitleme (commit) | `P` |

---

## 📊 Veri Türleri & Yapılar (Structures)

### Yapı (Structure) Oluşturma

```
# 1. Yöntem: Data Type Manager üzerinden
Data Type Manager → Sağ tıklayın → New → Structure

# 2. Yöntem: Decompiler içinden otomatik
Decompiler → Değişkene sağ tık → Auto Create Structure
```

### C Benzeri Yapı Tanımı

```c
// Örnek yapı tanımı
struct Oyuncu {
    char isim[32];      // ofset 0x00
    int can;            // ofset 0x20
    int puan;           // ofset 0x24
    float pozisyon[3];  // ofset 0x28
};
```

### Yapıyı Koda Uygulama

```
# Listing görünümünde
Adresi seçin → Sağ tıklayın → Data → Choose Data Type

# Veya kısayol
T tuşuna basın → Yapıyı seçin

# Decompiler görünümünde
Değişkene sağ tıklayın → Retype Variable → Yapı adını girin
```

### Diziler (Arrays)

```
# Dizi tanımlama
Başlangıç adresini seçin → Sağ tıklayın → Data → Create Array
Eleman sayısını girin
```

### Numaralandırmalar (Enums)

```
# Enum oluşturma
Data Type Manager → Sağ tıklayın → New → Enum

# Değerleri tanımlama
enum Durum {
    PASIF = 0,
    AKTIF = 1,
    DURAKLATILDI = 2
};
```

---

## 🔧 Yama ve Değişiklik Yapma (Patching)

### Talimat Yamalama (Patch Instruction)

```
# 1. Yöntem: Klavye ile
Değiştirilecek talimatı seçin → Ctrl+Shift+G
Yeni assembly talimatını yazın → Enter'a basın

# 2. Yöntem: Menüden
Sağ tıklayın → Patch Instruction
```

### Veri / Bayt Yamalama

```
# Onaltılık baytları değiştirme
Sağ tıklayın → Patch Data
Yeni hex baytlarını yazın

# Kısayol
Baytları seçin → Ctrl+Shift+G
```

### Sık Kullanılan Yama Kalıpları

| Orijinal Talimat | Yamalanan Talimat | Kullanım Amacı |
|------------------|-------------------|----------------|
| `JE` | `JMP` | Koşulsuz olarak her zaman atla |
| `JNE` | `JMP` | Koşulsuz olarak her zaman atla |
| `JE` | `NOP` | Atlamayı iptal et, devam et |
| `CALL` | `NOP NOP NOP NOP NOP` | Fonksiyon çağrısını atla |
| `74` | `75` | JE talimatını JNE yap |
| `75` | `74` | JNE talimatını JE yap |
| `74` | `EB` | JE yerine kısa JMP koy |

### Yamalanan İkiliyi Dışa Aktarma (Export)

```
File → Export Program
Biçim: Binary
Kayıt hedefini belirleyin ve kaydedin
```

### NOP Alanı Oluşturma (NOP Sled)

```
Alanı seçin → Sağ tıklayın → Clear Code Block
Ardından: Seçin → Sağ tıklayın → Patch → Fill with NOPs
```

---

## 🐍 Betik Desteği (Scripting)

### Script Manager

```
Window → Script Manager
# Veya: Tools → Script Manager
```

### Betik Çalıştırma

```
Script Manager → Betiği seçin → Yeşil "Play" butonuna basın
# Veya betiğe çift tıklayın
```

### Python Betik Örneği (Ghidra Python)

```python
# Örnek: Tüm fonksiyonları listeleme
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = ileriye doğru

for func in funcs:
    print(f"Fonksiyon: {func.getName()} Adres: {func.getEntryPoint()}")
```

### Sık Kullanılan Betik Değişkenleri ve Metotları

```python
# Geçerli imleç adresi
currentAddress

# Geçerli program nesnesi
currentProgram

# Fonksiyon yöneticisi
fm = currentProgram.getFunctionManager()

# Listing nesnesi (disassembly)
listing = currentProgram.getListing()

# Belirtilen adresteki talimat
instr = listing.getInstructionAt(addr)

# Adresteki baytları okuma
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Etiket oluşturma
createLabel(addr, "benim_etiketim", True)

# Satır sonu yorumu ekleme
setEOLComment(addr, "Benim yorumum")

# Referansları alma
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Yararlı Dahili Betikler

| Betik Adı | Amacı |
|-----------|-------|
| FindCrypt | Kriptografik sabitleri ve algoritmaları bulma |
| FunctionID | Standart kütüphane fonksiyonlarını eşleme |
| ExportFunctionInfoScript | Fonksiyon listesini dışa aktarma |
| FindStrings | Dize referanslarını arama |
| SearchMemory | Bellekte desen arama |

---

## ⌨️ Klavye Kısayolları

### Gezinme

| Kısayol | Eylem |
|---------|-------|
| `G` | Adrese git (Go to address) |
| `Alt+←` | Geri git |
| `Alt+→` | İleri git |
| `Ctrl+Shift+E` | Giriş noktasına git (Entry point) |
| `Ctrl+E` | Export tablosunu aç |
| `N` | Bir sonraki tanımsız alana git |

### Analiz ve Tanımlama

| Kısayol | Eylem |
|---------|-------|
| `F` | Fonksiyon oluştur (Create function) |
| `D` | Ayrıştır (Disassemble) |
| `C` | Tanımı temizle (Undefine) |
| `P` | İşaretçi oluştur (Pointer) |
| `T` | Veri türü ata (Data type) |
| `A` | Otomatik analizi başlat |

### Düzenleme

| Kısayol | Eylem |
|---------|-------|
| `L` | Yeniden adlandır (Etiket veya değişken) |
| `;` | Satır sonu yorumu ekle (EOL) |
| `Ctrl+;` | Başlık yorumu ekle (Plate comment) |
| `Ctrl+L` | Değişken türünü değiştir |
| `Ctrl+Shift+G` | Talimatı yamala (Patch) |

### Arama

| Kısayol | Eylem |
|---------|-------|
| `Ctrl+Shift+F` | Referansları bul (Find references) |
| `Ctrl+F` | Metin ara |
| `S` | Bellekte ara |
| `Ctrl+B` | Bayt ara |

### Pencereler

| Kısayol | Eylem |
|---------|-------|
| `Ctrl+D` | Decompiler penceresini aç/kapat |
| `Ctrl+T` | Data Type Manager penceresini aç/kapat |
| `Ctrl+S` | Symbol Tree penceresini aç/kapat |

---

## 📊 Hızlı Referans

### Analiz İş Akışı

```
1. İkiliyi içe aktar  → File → Import
2. Otomatik analiz    → Analysis → Auto Analyze (varsayılanları onayla)
3. main/entry bul     → G tuşuna bas → main veya entry yaz
4. Fonksiyonları tara → Symbol Tree → Functions
5. Dizgileri incele   → Search → For Strings
6. Akışı takip et     → Çağrıları ve atlamaları izle
7. Sembolleri adlandır→ L tuşu ile anlaşılır isimler ver
8. Notlar ekle        → ; tuşu ile yorum ekle
```

### Sık Kullanılan Görevler

| Görev | Yöntem |
|-------|--------|
| main() fonksiyonunu bul | `G` → `main` veya Symbol Tree'de ara |
| Dizgileri listele | Search → For Strings |
| Şifreleme algoritmalarını bul | FindCrypt betiğini çalıştır |
| İçe aktarılan fonksiyonları gör | Symbol Tree → Imports |
| Çapraz referansları bul | Sembolü seç → `Ctrl+Shift+F` |
| Manuel fonksiyon tanımla | Adresi seç → `F` |
| C kodunu görüntüle | Window → Decompiler |

### Fonksiyon Çağırma Kuralları (Calling Conventions)

| Kural | Dönüş | Argüman Aktarımı | Yığın Temizliği |
|-------|-------|------------------|-----------------|
| **cdecl** | EAX | Yığın (Sağdan Sola) | Çağıran (Caller) |
| **stdcall** | EAX | Yığın (Sağdan Sola) | Çağrılan (Callee) |
| **fastcall** | EAX | ECX, EDX, kalan yığında | Çağrılan (Callee) |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Çağıran (Caller) |
| **x64 Linux**| RAX | RDI, RSI, RDX, RCX, R8, R9 | Çağıran (Caller) |

---

## 📚 Kaynaklar

- [Ghidra Resmi Sitesi](https://ghidra-sre.org/)
- [Ghidra GitHub Deposu](https://github.com/NationalSecurityAgency/ghidra)
- [Ghidra API Belgeleri](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra Kaynakları](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### İlgili Başvuru Kılavuzları
- [Binwalk](../../Binwalk/README.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)
- [Volatility](../../Volatility/README.md)

---

<p align="center">
  <b>🔍 Tersine Mühendislikte Uzmanlaşın!</b><br>
  <i>Ghidra - NSA'in güvenlik topluluğuna en güçlü hediyesi</i>
</p>
