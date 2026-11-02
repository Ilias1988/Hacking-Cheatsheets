[🇬🇧 English](../../README.md)

# 🐛 GDB - GNU Hata Ayıklayıcı Başvuru Kılavuzu

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
     GNU Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

## 📋 İçindekiler

- [GDB Nedir](#-gdb-nedir)
- [Kurulum & Yapılandırma](#-kurulum--yapılandırma)
- [Temel Kullanım](#-temel-kullanım)
- [Kesme Noktaları (Breakpoints)](#-kesme-noktaları-breakpoints)
- [Yürütme Kontrolü (Execution Control)](#-yürütme-kontrolü-execution-control)
- [Bellek İnceleme (Examining Memory)](#-bellek-i̇nceleme-examining-memory)
- [Yazmaçlar (Registers)](#-yazmaçlar-registers)
- [Yığın (Stack) Analizi](#-yığın-stack-analizi)
- [GEF / PEDA / pwndbg Eklentileri](#-gef--peda--pwndbg-eklentileri)
- [İstismar Geliştirme (Exploit Development)](#-i̇stismar-geliştirme-exploit-development)
- [Hızlı Referans](#-hızlı-referans)

---

## 🎯 GDB Nedir

**GDB** (GNU Debugger), Linux sistemleri için endüstri standardı hata ayıklayıcıdır. Şu alanlarda vazgeçilmezdir:

- 🐛 **Hata Ayıklama (Debugging)** - Program hatalarını ve çökmeleri tespit edip giderme
- 🔍 **Tersine Mühendislik** - Derlenmiş ikili dosyaların iç mantığını anlama
- 💥 **İstismar Geliştirme (Exploit Dev)** - Buffer overflow, ROP zincirleri ve bellek manipülasyonu
- 📊 **İkili Dosya Analizi** - Kod akışını ve CPU yazmaçlarını dinamik olarak izleme
- 🧪 **CTF Yarışmaları** - Reverse ve pwn kategorisindeki soruları çözme

### Desteklenen Mimariler

| Mimari | Açıklama |
|--------|----------|
| x86 | 32-bit Intel |
| x86-64 | 64-bit Intel / AMD |
| ARM | 32-bit ARM |
| ARM64 | 64-bit ARM (AArch64) |
| MIPS | MIPS gömülü işlemciler |
| PowerPC | PPC mimarisi |

---

## 🚀 Kurulum & Yapılandırma

### GDB Kurulumu

```bash
# Debian / Ubuntu / Kali Linux
sudo apt install gdb

# Fedora / RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (Homebrew ile veya lldb tercih edilir)
brew install gdb
```

### GEF Kurulumu (GDB Enhanced Features - Şiddetle Önerilir)

```bash
# Otomatik GEF kurulumu
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Veya elle kurulum
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### PEDA Kurulumu

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### pwndbg Kurulumu

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Kurulumu Doğrulama

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Temel Kullanım

### GDB'yi Başlatma

```bash
# Çalıştırılabilir dosyayı debug modunda açma
gdb ./program

# Komut satırı argümanları ile başlatma
gdb --args ./program arg1 arg2

# Çalışan bir sürece bağlanma (Attach)
gdb -p <PID>
gdb attach <PID>

# Sessiz mod (başlangıç banner'ı olmadan)
gdb -q ./program

# Bir komut dosyasından komutları çalıştırarak başlatma
gdb -x commands.gdb ./program
```

### Temel Komutlar

| Komut | Kısayol | Açıklama |
|-------|---------|----------|
| `run` | `r` | Programı baştan başlat |
| `run args` | `r args` | Argümanlar vererek başlat |
| `continue` | `c` | Duraklatılan yürütmeye devam et |
| `quit` | `q` | GDB'den çık |
| `help` | `h` | Yardım menüsünü göster |
| `help <komut>` | `h <komut>` | Belirli komut hakkında detay |

### Dosya Komutları

```bash
# Yeni bir çalıştırılabilir dosya yükleme
(gdb) file ./program

# Hata ayıklama sembollerini yükleme
(gdb) symbol-file ./program.debug

# Çökme dökümünü (Core dump) inceleme
(gdb) core-file core

# Çalışma dizinini değiştirme
(gdb) cd /path/to/dir

# Çalıştırma argümanlarını belirleme
(gdb) set args arg1 arg2
```

---

## 🔴 Kesme Noktaları (Breakpoints)

### Kesme Noktası Belirleme

```bash
# Fonksiyon adına göre durdur
(gdb) break main
(gdb) b main

# Bellek adresine göre durdur (* işareti ile)
(gdb) break *0x08048456
(gdb) b *0x08048456

# Satır numarasına göre durdur
(gdb) break file.c:25
(gdb) b 25

# Fonksiyon başlangıcından ofset kadar sonra durdur
(gdb) break *main+48

# Geçici kesme noktası (bir kere tetiklenince silinir)
(gdb) tbreak main
```

### Koşullu Kesme Noktaları (Conditional)

```bash
# Belirli bir koşul sağlandığında durdur
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Mevcut kesme noktasına koşul ekleme
(gdb) condition 1 $eax == 0

# Kesme noktasındaki koşulu kaldırma
(gdb) condition 1
```

### Kesme Noktalarını Yönetme

```bash
# Tüm kesme noktalarını listele
(gdb) info breakpoints
(gdb) info b

# Belirli bir kesme noktasını sil
(gdb) delete 1
(gdb) d 1

# Bütün kesme noktalarını sil
(gdb) delete
(gdb) d

# Kesme noktasını devre dışı bırak
(gdb) disable 1
(gdb) dis 1

# Devre dışı kesme noktasını etkinleştir
(gdb) enable 1
(gdb) en 1

# Hepsini devre dışı bırak / etkinleştir
(gdb) disable
(gdb) enable
```

### Değer İzleme Noktaları (Watchpoints)

```bash
# Değişkenin veya adresin değeri değiştiğinde dur
(gdb) watch variable
(gdb) watch *0x08048456

# Yalnızca okuma yapıldığında dur (rwatch)
(gdb) rwatch variable

# Okuma veya yazma yapıldığında dur (awatch)
(gdb) awatch variable

# Mantıksal ifadeye göre izle
(gdb) watch ($eax > 10)
```

### Olay Yakalama Noktaları (Catchpoints)

```bash
# Herhangi bir sistem çağrısında dur
(gdb) catch syscall

# Belirli bir sistem çağrısında dur
(gdb) catch syscall write

# Sinyal yakalama (ör. Segmentation fault)
(gdb) catch signal SIGSEGV

# İstisna (exception) fırlatıldığında dur
(gdb) catch throw
(gdb) catch catch
```

---

## ▶️ Yürütme Kontrolü (Execution Control)

### Adım Adım İlerletme (Stepping)

```bash
# Tek bir makine talimatı ilerle (çağrıların içine girer - step into)
(gdb) stepi
(gdb) si

# Kaynak kodda tek bir satır ilerle (çağrıların içine girer)
(gdb) step
(gdb) s

# Talimatı atla (çağrıların içine girmeden üzerinden geç - step over)
(gdb) nexti
(gdb) ni

# Satır üzerinden geç
(gdb) next
(gdb) n

# Fonksiyon bitip dönene kadar çalışmaya devam et
(gdb) finish
(gdb) fin

# Belirtilen adrese kadar çalıştır
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Yürütmeyi Sürdürme

```bash
# Programı başlat
(gdb) run
(gdb) r

# Bir sonraki kesme noktasına kadar devam et
(gdb) continue
(gdb) c

# Belirtilen sayıda kesme noktasını atlayarak devam et
(gdb) continue 5

# Program sayacını doğrudan belirtilen adrese sıçrat
(gdb) jump *0x08048456
```

### Süreç Kontrolü

```bash
# Çalışan süreci sonlandır
(gdb) kill

# Süreçten ayrıl (arka planda çalışmaya devam eder)
(gdb) detach

# Sürece bağlan
(gdb) attach <PID>

# Fork işlemlerinde üst/alt süreci takip etme
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Bellek İnceleme (Examining Memory)

### Print Komutu

```bash
# Değişken değerini yazdır
(gdb) print variable
(gdb) p variable

# Onaltılık (Hex) tabanında yazdır
(gdb) print/x variable
(gdb) p/x $eax

# İkili (Binary) tabanında yazdır
(gdb) p/t variable

# Ondalık (Decimal) tabanında yazdır
(gdb) p/d variable

# Karakter olarak yazdır
(gdb) p/c $al

# Metin dizgisi (string) olarak yazdır
(gdb) p/s pointer

# Dizi elemanlarını toplu yazdır (ör. 10 eleman)
(gdb) p *array@10
```

### Bellek İnceleme Komutu (x)

```bash
# Sözdizimi: x/NFU adres
# N = Adet, F = Format, U = Birim Boyutu

# Formatlar: x (hex), d (decimal), u (unsigned), o (octal), 
#             t (binary), c (char), s (string), i (instruction)
# Birimler: b (byte=1), h (half=2), w (word=4), g (giant=8)

# Bellek dökümü örnekleri:
(gdb) x/10x $esp           # ESP'den itibaren 10 adet hex kelime (word)
(gdb) x/20wx 0x08048456    # 20 adet 4-baytlık hex kelime
(gdb) x/s 0x08048456       # Adresteki null-terminated dizgiyi yazdır
(gdb) x/10i $eip           # EIP'den itibaren 10 makine talimatını göster
(gdb) x/10gx $rsp          # RSP'den itibaren 10 adet 64-bitlik (8 bayt) qword

# Sık kullanılan incelemeler:
(gdb) x/100x $esp          # Yığın (stack) içeriğini dök
(gdb) x/20i $eip           # Talimatları ayrıştır (disassemble)
(gdb) x/s $eax             # EAX'in gösterdiği adresteki dizgiyi oku
```

### Bellek Haritası ve Bölümleri

```bash
# Sürecin bellek haritasını göster
(gdb) info proc mappings

# Bellek bölümlerini listele
(gdb) maintenance info sections

# Dosya ve bölüm detayları
(gdb) info files
```

### Tersine Çevirme / Ayrıştırma (Disassembly)

```bash
# Fonksiyonu ayrıştır
(gdb) disassemble main
(gdb) disas main

# Geçerli fonksiyonu ayrıştır
(gdb) disassemble
(gdb) disas

# Belirli bir adres aralığını ayrıştır
(gdb) disas 0x08048456, 0x08048500

# Kaynak kodla harmanlanmış ayrıştırma
(gdb) disas /m main

# Ayrıştırma sözdizimi seçimi (Intel / AT&T)
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Yazmaçlar (Registers)

### Yazmaçları Görüntüleme

```bash
# Tüm genel amaçlı yazmaçları listele
(gdb) info registers
(gdb) i r

# Belirli bir yazmacı oku
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Kayan nokta ve SSE dahil tüm yazmaçlar
(gdb) info all-registers
```

### Yazmaç Değerlerini Değiştirme

```bash
# Yazmaca değer atama
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Bayrak (Flags) yazmacını değiştirme
(gdb) set $eflags = 0x246
```

### x86 / x64 Yazmaç Tablosu

| Yazmaç Türü | 64-bit | 32-bit | 16-bit | 8-bit | Kullanım Amacı |
|-------------|--------|--------|--------|-------|----------------|
| Accumulator | RAX | EAX | AX | AL/AH | Dönüş değeri, aritmetik |
| Base | RBX | EBX | BX | BL/BH | Genel amaçlı |
| Counter | RCX | ECX | CX | CL/CH | Döngü sayacı |
| Data | RDX | EDX | DX | DL/DH | Girdi/çıktı, çarpma/bölme |
| Source Index | RSI | ESI | SI | SIL | Dizi/katar kaynağı |
| Dest Index | RDI | EDI | DI | DIL | Dizi/katar hedefi |
| Base Pointer | RBP | EBP | BP | BPL | Yığın çerçevesi tabanı |
| Stack Pointer| RSP | ESP | SP | SPL | Yığın tepesi |
| Instruction | RIP | EIP | IP | - | Bir sonraki talimat |

### x64 Ek Genel Amaçlı Yazmaçlar

```
R8-R15   : 64-bit genel amaçlı yazmaçlar
R8D-R15D : 32-bit alt yarıları
R8W-R15W : 16-bit alt yarıları
R8B-R15B : 8-bit en alt baytları
```

---

## 📚 Yığın (Stack) Analizi

### Yığını Görüntüleme

```bash
# Çağrı yığını dökümü (Backtrace)
(gdb) backtrace
(gdb) bt

# Yerel değişkenlerle tam yığın dökümü
(gdb) backtrace full
(gdb) bt full

# Geçerli çerçeve bilgisi
(gdb) frame
(gdb) f

# Belirli bir yığın çerçevesine geçiş yapma
(gdb) frame 2
(gdb) f 2

# Yığındaki değerleri doğrudan inceleme
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Yığın Çerçevesi (Stack Frame)

```bash
# Çerçeve ayrıntıları
(gdb) info frame
(gdb) i f

# Çerçevedeki yerel değişkenler
(gdb) info locals

# Fonksiyona iletilen argümanlar
(gdb) info args

# Çerçeveler arasında yukarı/aşağı gezinme
(gdb) up
(gdb) down
```

### Dönüş Adresi ve Kaydedilen EBP

```bash
# Kaydedilen dönüş adresini (Return Address) inceleme
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Kaydedilen EBP değerini inceleme
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Yığın düzenini kontrol etme
(gdb) x/20wx $esp-20
```

---

## 🎨 GEF / PEDA / pwndbg Eklentileri

### GEF Komutları

```bash
# Bağlam ekranı (otomatik tetiklenir)
gef➤ context

# İkili güvenlik korumalarını kontrol et (ASLR, NX, Canary, PIE)
gef➤ checksec

# Desen (Pattern) oluşturma ve ofset arama
gef➤ pattern create 200
gef➤ pattern search $rsp

# Bellekte dize veya bayt arama
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Heap (öbek) analizi
gef➤ heap chunks
gef➤ heap bins

# ROP parçacıkları (gadgets) bulma
gef➤ ropgadget

# Format string açığı tespit yardımcısı
gef➤ format-string-helper

# Bellek haritası (Memory map)
gef➤ vmmap

# İşaretçi zinciri çözme (Dereference chain)
gef➤ dereference $rsp 20
gef➤ telescope $rsp 20

# ELF dosya başlık bilgileri
gef➤ elf-info

# Stack canary değeri
gef➤ canary

# PIE ve ASLR durumu
gef➤ pie
gef➤ aslr
```

### PEDA Komutları

```bash
# Güvenlik denetimi
peda➤ checksec

# Bellekte arama
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Desen oluşturma ve ofset tespiti
peda➤ pattern create 200
peda➤ pattern offset $eip

# ROP aracı
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode üreteci
peda➤ shellcode generate x86/linux exec

# Bağlam görünümü
peda➤ context all

# Anlık assembly derleme
peda➤ assemble
```

### pwndbg Komutları

```bash
# Bağlam ekranı
pwndbg> context

# Koruma kontrolleri
pwndbg> checksec

# Bellek arama
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Döngüsel desen (cyclic)
pwndbg> cyclic 200
pwndbg> cyclic -l <deger>

# ROP filtreleme
pwndbg> rop --grep "pop rdi"

# Heap durumları
pwndbg> heap
pwndbg> bins
pwndbg> arena

# Global Offset Table / PLT
pwndbg> got
pwndbg> plt
```

---

## 💥 İstismar Geliştirme (Exploit Development)

### Ofset Mesafesini Bulma

```bash
# 1. Yöntem: GEF ile desen üretimi
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# 2. Yöntem: pwntools döngüsel desen
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Bellek Taşması Temelleri (Buffer Overflow)

```bash
# Sistem ASLR durumunu kontrol et
$ cat /proc/sys/kernel/randomize_va_space
# 0 = kapalı, 1 = kısmi, 2 = tam aktif

# Test amacıyla ASLR'ı geçici kapatma
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# İkili korumalarını kontrol et
gef➤ checksec

# Çökme anından önce dönüş adresi konumunu inceleme
(gdb) x/20wx $esp
(gdb) i f
```

### ROP Gadget ve Fonksiyon Adreslerini Bulma

```bash
# Bellekte "/bin/sh" arama
gef➤ search-pattern "/bin/sh"

# system() fonksiyon adresini bulma
(gdb) p system
(gdb) p &system

# 64-bit için "pop rdi; ret" gadget arama
gef➤ ropgadget --grep "pop rdi"

# 32-bit için pop parçacıkları arama
gef➤ ropgadget --grep "pop"
```

### Belleğe Doğrudan Veri Yazma

```bash
# Adrese sayısal değer yazma
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Adrese metin dizgisi yazma
(gdb) set {char[10]}0x08048456 = "AAAA"

# GDB içinden doğrudan fonksiyon çağırma
(gdb) call system("/bin/sh")
```

### İstismar İçin Yararlı Yapılandırmalar

```bash
# Alt süreçleri otomatik takip et
(gdb) set follow-fork-mode child

# GDB içinde bellek adreslerinin rastgeleleşmesini engelle
(gdb) set disable-randomization on

# Paylaşılan dinamik kütüphaneleri listele
(gdb) info sharedlibrary

# libc taban adresini bulma
gef➤ vmmap libc

# PLT ve GOT fonksiyon adresleri
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Hızlı Referans

### En Önemli Komutlar

| Komut | Açıklama |
|-------|----------|
| `r` | Programı çalıştır |
| `c` | Devam et |
| `b *addr` | Belirtilen adrese kesme noktası koy |
| `si` | Makine talimatı içine gir (step into) |
| `ni` | Makine talimatı üzerinden atla (step over) |
| `fin` | Fonksiyonu tamamla ve dön |
| `x/Nx addr` | Bellekten N adet veri oku |
| `p $reg` | Yazmaç değerini yazdır |
| `i r` | Yazmaçların genel listesi |
| `bt` | Çağrı yığını dökümü |
| `disas` | Talimatları ayrıştır |
| `q` | Çıkış yap |

### Bellek İnceleme Formatları

| Komut | Açıklama |
|-------|----------|
| `x/10x $esp` | ESP'den itibaren 10 adet hex kelime |
| `x/s addr` | Adresteki dizeyi yazdır |
| `x/i $eip` | EIP'deki talimatı göster |
| `x/20wx addr` | 20 adet 4-baytlık kelime dökümü |
| `x/10gx $rsp` | RSP'den itibaren 10 adet 8-baytlık qword |

### Hızlı Hata Ayıklama Akışı

```bash
# Programı yükle ve main'de durdur
$ gdb -q ./program
(gdb) break main
(gdb) run

# Çökmeyi incele
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### İstismar Geliştirme İş Akışı

```
1. checksec           → Güvenlik korumalarını tespit et
2. pattern create     → Benzersiz desen üret
3. run with pattern   → Programı girdiye boğarak çökert
4. pattern search     → RIP/EIP ofset mesafesini bul
5. find gadgets       → Uygun ROP parçacıklarını topla
6. build payload      → Exploit kodunu inşa et
7. test               → GDB altında doğrula ve optimize et
```

---

## 📚 Kaynaklar

- [GDB Resmi Kullanım Kılavuzu](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF Belgeleri](https://hugsy.github.io/gef/)
- [pwndbg Belgeleri](https://github.com/pwndbg/pwndbg)
- [PEDA Deposu](https://github.com/longld/peda)

### İlgili Başvuru Kılavuzları
- [Ghidra](../../Ghidra/README.md)
- [Linux Komutları](../../Linux-Commands/translations/README.tr.md)
- [Linux PrivEsc (Yetki Yükseltme)](../../Linux-PrivEsc/translations/README.tr.md)

---

<p align="center">
  <b>🐛 Linux Hata Ayıklamada Uzmanlaşın!</b><br>
  <i>GDB - Her Linux tersine mühendisinin vazgeçilmez aracı</i>
</p>
