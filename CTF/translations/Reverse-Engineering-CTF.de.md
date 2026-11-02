# 🔄 Spickzettel für Reverse-Engineering-CTFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Erste Analyse

```bash
# Dateityp
file binary

# Zeichenfolgen
strings binary | grep -i flag
strings -n 8 binary

# ELF-Informationen
readelf -h binary

# Schutzmechanismen prüfen
checksec binary
```

---

## 🛠️ Disassembler

### Ghidra (kostenlos)
```
1. File → Import File
2. Analyze → Auto Analyze
3. Die Funktion main() suchen
4. Bei Passwortprüfungen nach strcmp/memcmp suchen
5. F5 zum Dekompilieren
```

### IDA Free
```
1. Programmdatei öffnen
2. F5 für Pseudocode (Hex-Rays)
3. X zum Finden von Querverweisen
4. N zum Umbenennen von Variablen
```

### Radare2
```bash
r2 binary
aaa          # Analysieren
afl          # Funktionen auflisten
pdf @main    # main disassemblieren
VV           # Visueller Modus
```

---

## 🐛 Debugging mit GDB

```bash
# Starten
gdb ./binary

# Haltepunkte
b main
b *0x401234

# Ausführen
r
r arg1 arg2

# Schrittweise ausführen
n           # Nächste Zeile
s           # In den Aufruf hineingehen
c           # Fortsetzen

# Untersuchen
x/s $rdi    # Zeichenfolge
x/10x $rsp  # 10 hexadezimale Werte vom Stack
info reg    # Register

# Flag suchen
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# GEF installieren
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Befehle
vmmap
heap
telescope $rsp
```

---

## 🐍 Binärdateien mit Python verändern

```python
# Binärdatei lesen
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Bytes verändern
data[0x1234] = 0x90  # NOP

# Schreiben
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Häufige Muster

| Prüfung | Umgehung |
|-------|--------|
| `strcmp(input, "password")` | "password" auslesen |
| `if (x == 0x1337)` | Wert 0x1337 ermitteln |
| Anti-debug | ptrace-Prüfung verändern |
| Verschleierte Zeichenfolgen | Dynamische Analyse |

---

## 📋 Checkliste für Reverse Engineering

```markdown
□ Befehle file und strings verwenden
□ Auf Debug-Symbole prüfen
□ In Ghidra/IDA öffnen
□ Funktion main suchen
□ Nach Vergleichen von Zeichenfolgen suchen
□ Benutzereingaben nachverfolgen
□ Bei Bedarf Anti-Debug-Prüfungen verändern
□ Dynamische Analyse mit GDB
```

---

**Zurück zu CTF:** [🏁 CTF-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Werkzeugkontext:** Menüpfade und Tasten hängen von Version, Edition und Belegung ab. In Ghidra folgt das Decompiler-Fenster der ausgewählten Funktion; die Angabe F5 ist keine universelle Tastenzusage. IDA-Pseudocode setzt einen für die Zielarchitektur verfügbaren Decompiler voraus. Die englischen Menübezeichnungen bleiben als Originalreferenz erhalten. [Ghidra: Decompiler-Fenster](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/Decompiler/src/main/help/help/topics/DecompilePlugin/DecompilerWindow.html).

> **Grenzen der Beispiele:** GDB-, Radare2- und Erweiterungsbefehle werden in der jeweiligen Konsole eingegeben. Die Register `$rdi` und `$rsp` beziehen sich auf x86-64; Suchbereiche und Adressen sind Beispiele und müssen tatsächlich gemappt sein. Der Python-Patch setzt eine ausreichend große Datei und einen passenden Dateioffset voraus; ein Dateioffset ist nicht automatisch eine virtuelle Adresse. Die neu geschriebene Datei übernimmt nicht automatisch alle Dateirechte. Eine Funktion muss nach Optimierung oder entfernten Symbolen nicht mehr als `main` benannt sein.
