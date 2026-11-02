# 🔄 Hurtigreferanse for omvendt utvikling i CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Innledende analyse

```bash
# Filtype
file binary

# Tekststrenger
strings binary | grep -i flag
strings -n 8 binary

# Opplysninger om ELF-filen
readelf -h binary

# Kontroller sikkerhetsmekanismer
checksec binary
```

---

## 🛠️ Disassemblere

### Ghidra (gratis)

> **Rettelse:** F5-linjen nedenfor er originalens angivelse, ikke en generell Ghidra-instruksjon. Ghidras Decompiler-vindu åpnes fra Window → Decompile og følger den valgte funksjonen automatisk; se [Ghidras offisielle hjelp](https://raw.githubusercontent.com/NationalSecurityAgency/ghidra/master/Ghidra/Features/Decompiler/src/main/help/help/topics/DecompilePlugin/DecompilerWindow.html). Menynavn, snarveier, GDB-tillegg og IDA-funksjoner avhenger av versjon/oppsett.
```
1. File → Import File
2. Analyze → Auto Analyze
3. Finn funksjonen main()
4. Se etter strcmp/memcmp som brukes til passordkontroll
5. F5 for dekompilering (originalens angivelse; se presiseringen)
```

### IDA Free
```
1. Åpne binærfilen
2. F5 for pseudokode (Hex-Rays)
3. X for å finne kryssreferanser
4. N for å gi variabler nye navn
```

### Radare2
```bash
r2 binary
aaa          # Analyser
afl          # List funksjoner
pdf @main    # Disassembler main
VV           # Visuell modus
```

---

## 🐛 Feilsøking med GDB

```bash
# Start
gdb ./binary

# Stoppunkter
b main
b *0x401234

# Kjør
r
r arg1 arg2

# Stegvis kjøring
n           # Neste linje
s           # Gå inn i funksjonen
c           # Fortsett

# Undersøk
x/s $rdi    # Streng
x/10x $rsp  # 10 heksadesimale verdier fra stakken
info reg    # Registre

# Finn flagget
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Installer GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Kommandoer
vmmap
heap
telescope $rsp
```

---

## 🐍 Endring av binærkode med Python

```python
# Les binærfilen
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Endre byte
data[0x1234] = 0x90  # NOP

# Skriv
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Vanlige mønstre

| Kontroll | Omgåelse |
|-------|--------|
| `strcmp(input, "password")` | Hent ut "password" |
| `if (x == 0x1337)` | Finn verdien 0x1337 |
| Anti-debug | Endre ptrace-kontrollen |
| Tilslørte strenger | Dynamisk analyse |

---

## 📋 Sjekkliste for omvendt utvikling

```markdown
□ Kjør file/strings-kommandoene
□ Se etter feilsøkingssymboler
□ Åpne i Ghidra/IDA
□ Finn main-funksjonen
□ Se etter strengsammenligninger
□ Følg brukerdata gjennom programmet
□ Endre anti-debug-kontroller ved behov
□ Utfør dynamisk analyse med GDB
```

---

**Tilbake til CTF:** [🏁 CTF-oversikt](./README.nb.md)
