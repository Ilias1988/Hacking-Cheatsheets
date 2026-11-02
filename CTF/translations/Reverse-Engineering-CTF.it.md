# 🔄 Scheda di riferimento per le CTF di reverse engineering

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Reverse-Engineering-CTF.md) • [🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Analisi iniziale

```bash
# Tipo di file
file binary

# Stringhe
strings binary | grep -i flag
strings -n 8 binary

# Informazioni sul formato ELF
readelf -h binary

# Verifica le protezioni
checksec binary
```

---

## 🛠️ Disassemblatori

### Ghidra (gratuito)
```
1. File → Import File
2. Analyze → Auto Analyze
3. Cerca la funzione main()
4. Cerca strcmp/memcmp usati nei controlli della password
5. Premi F5 per decompilare
```

### IDA Free
```
1. Apri il binario
2. Premi F5 per lo pseudocodice (Hex-Rays)
3. Premi X per trovare i riferimenti incrociati
4. Premi N per rinominare le variabili
```

### Radare2
```bash
r2 binary
aaa          # Analizza
afl          # Elenca le funzioni
pdf @main    # Disassembla main
VV           # Modalità visuale
```

---

> **Nota sugli ambienti:** i tasti e la disponibilità del decompilatore dipendono dall'edizione e dall'architettura. I registri `$rdi` e `$rsp` degli esempi GDB si riferiscono a x86-64; gli esempi non sono universali per ogni binario.

## 🐛 Debugging con GDB

```bash
# Avvia
gdb ./binary

# Punti di interruzione (breakpoint)
b main
b *0x401234

# Esegui
r
r arg1 arg2

# Esecuzione passo passo
n           # Riga successiva
s           # Entra nella funzione
c           # Continua

# Esamina
x/s $rdi    # Stringa
x/10x $rsp  # 10 valori esadecimali dallo stack
info reg    # Registri

# Cerca la flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Installa GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Comandi
vmmap
heap
telescope $rsp
```

---

## 🐍 Modifica dei binari con Python (patching)

```python
# Leggi il binario
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Modifica i byte
data[0x1234] = 0x90  # NOP

# Scrivi
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Schemi comuni

| Controllo | Aggiramento |
|-------|--------|
| `strcmp(input, "password")` | Estrai "password" |
| `if (x == 0x1337)` | Individua il valore 0x1337 |
| Anti-debugging | Modifica il controllo ptrace |
| Stringhe offuscate | Analisi dinamica |

---

## 📋 Checklist di reverse engineering

```markdown
□ Esegui i comandi file e strings
□ Verifica la presenza di simboli di debug
□ Apri il binario in Ghidra/IDA
□ Individua la funzione main
□ Cerca confronti tra stringhe
□ Segui il percorso dell'input dell'utente
□ Modifica i controlli anti-debugging se necessario
□ Esegui l'analisi dinamica con GDB
```

---

**Torna alle CTF:** [🏁 Panoramica CTF](./README.it.md)
