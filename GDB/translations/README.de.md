# 🐛 GDB – Cheatsheet zum GNU Debugger

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

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

## 📋 Inhaltsverzeichnis

- [Was ist GDB?](#-was-ist-gdb)
- [Installation und Einrichtung](#-installation-und-einrichtung)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Breakpoints](#-breakpoints-haltepunkte)
- [Ausführung steuern](#-ausführung-steuern)
- [Speicher untersuchen](#-speicher-untersuchen)
- [Register](#-register)
- [Stack-Analyse](#-stack-analyse)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Exploit-Entwicklung](#-exploit-entwicklung)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist GDB?

**GDB** (GNU Debugger) ist ein verbreiteter Debugger unter Linux. Er eignet sich insbesondere für:

- 🐛 **Debugging** – Fehler finden und beheben
- 🔍 **Reverse Engineering** (Analyse des Programmaufbaus) – Das Verhalten von Binärprogrammen verstehen
- 💥 **Exploit-Entwicklung** – Pufferüberläufe und Return-Oriented Programming (ROP)
- 📊 **Binäranalyse** – Den Ausführungsablauf untersuchen
- 🧪 **CTF-Aufgaben** – Aufgaben zu Reverse Engineering und Binärausnutzung lösen

### Unterstützte Architekturen

| Architektur | Hinweise |
| -------------- | ------- |
| x86 | 32-Bit-Intel |
| x86-64 | 64-Bit-Intel/AMD |
| ARM | 32-Bit-ARM |
| ARM64 | 64-Bit-ARM |
| MIPS | MIPS-Prozessoren |
| PowerPC | PPC-Prozessoren |

---

## 🚀 Installation und Einrichtung

### GDB installieren

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (alternativ lldb verwenden)
brew install gdb
```

### GEF installieren (GDB Enhanced Features)

```bash
# GEF – besonders empfohlen
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Alternativ manuell
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### PEDA installieren

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### pwndbg installieren

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Installation prüfen

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Grundlegende Verwendung

### GDB starten

```bash
# Ausführbare Datei untersuchen
gdb ./program

# Mit Argumenten untersuchen
gdb --args ./program arg1 arg2

# An einen laufenden Prozess anhängen
gdb -p <PID>
gdb attach <PID>

# Ohne Startbanner
gdb -q ./program

# Befehle aus einer Datei ausführen
gdb -x commands.gdb ./program
```

### Grundlegende Befehle

| Befehl | Kurzform | Beschreibung |
| --------- | ------- | ------------- |
| `run` | `r` | Programm starten |
| `run args` | `r args` | Mit Argumenten starten |
| `continue` | `c` | Ausführung fortsetzen |
| `quit` | `q` | GDB beenden |
| `help` | `h` | Hilfe anzeigen |
| `help <cmd>` | `h <cmd>` | Hilfe zu einem Befehl |

### Dateibefehle

```bash
# Ausführbare Datei laden
(gdb) file ./program

# Symbole laden
(gdb) symbol-file ./program.debug

# Core-Dump laden
(gdb) core-file core

# Arbeitsverzeichnis setzen
(gdb) cd /path/to/dir

# Argumente setzen
(gdb) set args arg1 arg2
```

---

## 🔴 Breakpoints (Haltepunkte)

### Breakpoints setzen

```bash
# Bei einer Funktion anhalten
(gdb) break main
(gdb) b main

# An einer Adresse anhalten
(gdb) break *0x08048456
(gdb) b *0x08048456

# An einer Quellcodezeile anhalten
(gdb) break file.c:25
(gdb) b 25

# An einem Offset ab Funktionsbeginn anhalten
(gdb) break *main+48

# Temporärer Breakpoint (wird automatisch gelöscht)
(gdb) tbreak main
```

### Bedingte Breakpoints

```bash
# Anhalten, wenn die Bedingung wahr ist
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Vorhandenen Breakpoint um eine Bedingung ergänzen
(gdb) condition 1 $eax == 0

# Bedingung entfernen
(gdb) condition 1
```

### Breakpoints verwalten

```bash
# Breakpoints auflisten
(gdb) info breakpoints
(gdb) info b

# Breakpoint löschen
(gdb) delete 1
(gdb) d 1

# Alle Breakpoints löschen
(gdb) delete
(gdb) d

# Breakpoint deaktivieren
(gdb) disable 1
(gdb) dis 1

# Breakpoint aktivieren
(gdb) enable 1
(gdb) en 1

# Alle deaktivieren
(gdb) disable

# Alle aktivieren
(gdb) enable
```

### Watchpoints (Haltepunkte für Datenzugriffe)

```bash
# Variable auf Änderungen überwachen
(gdb) watch variable
(gdb) watch *0x08048456

# Lesezugriffe überwachen
(gdb) rwatch variable

# Lese- und Schreibzugriffe überwachen
(gdb) awatch variable

# Ausdruck überwachen
(gdb) watch ($eax > 10)
```

### Catchpoints (Ereignisse abfangen)

```bash
# Systemaufrufe abfangen
(gdb) catch syscall

# Bestimmten Systemaufruf abfangen
(gdb) catch syscall write

# Signale abfangen
(gdb) catch signal SIGSEGV

# Ausnahmen abfangen
(gdb) catch throw
(gdb) catch catch
```

---

<a name="-ausführung-steuern"></a>

## ▶️ Ausführung steuern

### Schrittweise ausführen

```bash
# Eine Instruktion ausführen (in Aufrufe hinein)
(gdb) stepi
(gdb) si

# Eine Quellcodezeile ausführen (in Aufrufe hinein)
(gdb) step
(gdb) s

# Instruktion ausführen, ohne in Aufrufe hineinzugehen
(gdb) nexti
(gdb) ni

# Quellcodezeile ausführen, Aufrufe überspringen
(gdb) next
(gdb) n

# Bis zur Rückkehr ausführen
(gdb) finish
(gdb) fin

# Bis zu einer Adresse ausführen
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Programmausführung

```bash
# Programm starten/neu starten
(gdb) run
(gdb) r

# Nach einem Breakpoint fortsetzen
(gdb) continue
(gdb) c

# Bis zum nächsten Breakpoint ausführen
(gdb) continue

# N Breakpoint-Treffer überspringen
(gdb) continue 5

# Zu einer Adresse springen (gefährlich!)
(gdb) jump *0x08048456
```

### Prozesssteuerung

```bash
# Aktuellen Prozess beenden
(gdb) kill

# Vom Prozess lösen
(gdb) detach

# An einen Prozess anhängen
(gdb) attach <PID>

# Nach fork folgen (Eltern-/Kindprozess)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Speicher untersuchen

### Print-Befehl

```bash
# Variable ausgeben
(gdb) print variable
(gdb) p variable

# Hexadezimal ausgeben
(gdb) print/x variable
(gdb) p/x $eax

# Binär ausgeben
(gdb) p/t variable

# Dezimal ausgeben
(gdb) p/d variable

# Als Zeichen ausgeben
(gdb) p/c $al

# Als Zeichenkette ausgeben
(gdb) p/s pointer

# Array ausgeben
(gdb) p *array@10     # 10 Elemente
```

### Examine-Befehl (x)

```bash
# Syntax: x/NFU address
# N = Anzahl, F = Format, U = Einheitengröße

# Formate: x(hexadezimal), d(dezimal), u(vorzeichenlos), o(oktal), 
#          t(binär), c(Zeichen), s(Zeichenkette), i(Instruktion)
# Einheiten: b(Byte), h(Halbwort=2), w(Wort=4), g(Giant-Wort=8)

# Speicher untersuchen
(gdb) x/10x $esp           # 10 Wörter ab ESP hexadezimal anzeigen
(gdb) x/20wx 0x08048456    # 20 Wörter hexadezimal anzeigen
(gdb) x/s 0x08048456       # Zeichenkette an der Adresse
(gdb) x/10i $eip           # 10 Instruktionen ab EIP
(gdb) x/10gx $rsp          # 10 Giant-Wörter (je 8 Bytes) ab RSP

# Häufige Muster
(gdb) x/100x $esp          # Stack untersuchen
(gdb) x/20i $eip           # Disassemblieren
(gdb) x/s $eax             # Zeichenkettenpointer
```

### Speicherbereiche

```bash
# Speicherbelegung anzeigen
(gdb) info proc mappings

# Sektionen anzeigen
(gdb) maintenance info sections

# Dateien anzeigen
(gdb) info files
```

### Disassemblierung

```bash
# Funktion disassemblieren
(gdb) disassemble main
(gdb) disas main

# Aktuelle Funktion disassemblieren
(gdb) disassemble
(gdb) disas

# Adressbereich disassemblieren
(gdb) disas 0x08048456, 0x08048500

# Mit Quellcode disassemblieren (falls verfügbar)
(gdb) disas /m main

# Syntax festlegen
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Register

### Register anzeigen

```bash
# Alle Register
(gdb) info registers
(gdb) i r

# Bestimmtes Register
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Alle Register einschließlich FP/SSE
(gdb) info all-registers
```

### Register ändern

```bash
# Registerwert setzen
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Flags setzen
(gdb) set $eflags = 0x246
```

### x86-Registerreferenz

| Register | 64-Bit | 32-Bit | 16-Bit | 8-Bit | Zweck |
| ---------- | -------- | -------- | -------- | ------- | --------- |
| Akkumulator | RAX | EAX | AX | AL/AH | Rückgabewert |
| Basis | RBX | EBX | BX | BL/BH | Allgemein verwendbar |
| Zähler | RCX | ECX | CX | CL/CH | Schleifenzähler |
| Daten | RDX | EDX | DX | DL/DH | Ein-/Ausgabe, Multiplikation |
| Quellindex | RSI | ESI | SI | SIL | Quelle bei Zeichenkettenoperationen |
| Zielindex | RDI | EDI | DI | DIL | Ziel bei Zeichenkettenoperationen |
| Basispointer | RBP | EBP | BP | BPL | Stack-Frame |
| Stackpointer | RSP | ESP | SP | SPL | Stackspitze |
| Instruktionszeiger | RIP | EIP | IP | - | Nächste Instruktion |

### Zusätzliche x64-Register

```
R8-R15 : Allgemein verwendbare Register
R8D-R15D : Untere 32-Bit-Anteile
R8W-R15W : Untere 16-Bit-Anteile
R8B-R15B : Untere 8-Bit-Anteile
```

---

## 📚 Stack-Analyse

### Stack anzeigen

```bash
# Aufrufkette des Stacks
(gdb) backtrace
(gdb) bt

# Vollständige Aufrufkette mit lokalen Variablen
(gdb) backtrace full
(gdb) bt full

# Stack-Frame anzeigen
(gdb) frame
(gdb) f

# Stack-Frame auswählen
(gdb) frame 2
(gdb) f 2

# Stackinhalt anzeigen
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Stack-Frame

```bash
# Informationen zum Stack-Frame anzeigen
(gdb) info frame
(gdb) i f

# Lokale Variablen anzeigen
(gdb) info locals

# Argumente anzeigen
(gdb) info args

# In der Aufrufkette auf-/absteigen
(gdb) up
(gdb) down
```

### Stack-Befehle

```bash
# Rücksprungadresse untersuchen
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Gespeicherten EBP untersuchen
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Stackaufbau anzeigen
(gdb) x/20wx $esp-20  # Bereich davor und danach
```

---

## 🎨 GEF/PEDA/pwndbg

### GEF-Befehle

```bash
# Kontextanzeige (automatisch)
gef➤ context

# Checksec (Schutzmechanismen)
gef➤ checksec

# Muster suchen
gef➤ pattern create 200
gef➤ pattern search $rsp

# Zeichenkette/Wert finden
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Heap analysieren
gef➤ heap chunks
gef➤ heap bins

# ROP-Gadgets
gef➤ ropgadget

# Hilfe für Format-String-Analysen
gef➤ format-string-helper

# VMMap (Speicherbelegung)
gef➤ vmmap

# Pointerkette dereferenzieren
gef➤ dereference $rsp 20

# Telescope (aufbereitete Dereferenzierung)
gef➤ telescope $rsp 20

# ELF-Informationen
gef➤ elf-info

# Canary-Wert
gef➤ canary

# PIE-/ASLR-Informationen
gef➤ pie
gef➤ aslr
```

### PEDA-Befehle

```bash
# Schutzmechanismen prüfen
peda➤ checksec

# Zeichenkette finden
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Muster erzeugen/suchen
peda➤ pattern create 200
peda➤ pattern offset $eip

# ROP-Gadgets
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Kontext
peda➤ context all

# Assemblieren
peda➤ assemble
```

### pwndbg-Befehle

```bash
# Kontext
pwndbg> context

# Checksec
pwndbg> checksec

# Suche
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Zyklisches Muster
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Heap
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

## 💥 Exploit-Entwicklung

### Offset ermitteln

```bash
# Methode 1: Muster (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Methode 2: Muster (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Grundlagen von Pufferüberläufen

```bash
# ASLR prüfen
$ cat /proc/sys/kernel/randomize_va_space
# 0=aus, 1=teilweise, 2=vollständig

# ASLR deaktivieren (für Tests)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Schutzmechanismen des Binärprogramms prüfen
gef➤ checksec

# Position der Rücksprungadresse finden
(gdb) x/20wx $esp     # Vor dem Absturz
(gdb) i f             # Informationen zum Stack-Frame
```

### Gadgets finden

```bash
# "/bin/sh" finden
gef➤ search-pattern "/bin/sh"

# Adresse von system() finden
(gdb) p system
(gdb) p &system

# pop-rdi-Gadget finden (64-Bit)
gef➤ ropgadget --grep "pop rdi"

# pop; ret finden (32-Bit)
gef➤ ropgadget --grep "pop"
```

### Speicher schreiben

```bash
# Speicherwert setzen
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Zeichenkette schreiben
(gdb) set {char[10]}0x08048456 = "AAAA"

# Funktion aufrufen
(gdb) call system("/bin/sh")
```

### Nützlich für Exploits

```bash
# Kindprozessen folgen
(gdb) set follow-fork-mode child

# ASLR in GDB deaktivieren
(gdb) set disable-randomization on

# Geladene Bibliotheken anzeigen
(gdb) info sharedlibrary

# Basisadresse von libc finden
gef➤ vmmap libc

# PLT/GOT finden
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
| --------- | ------------- |
| `r` | Programm starten |
| `c` | Fortsetzen |
| `b *addr` | Breakpoint setzen |
| `si` | Eine Instruktion ausführen |
| `ni` | Instruktion ausführen, Aufrufe überspringen |
| `fin` | Bis zur Funktionsrückkehr ausführen |
| `x/Nx addr` | Speicher untersuchen |
| `p $reg` | Register ausgeben |
| `i r` | Registerinformationen |
| `bt` | Aufrufkette |
| `disas` | Disassemblieren |
| `q` | Beenden |

### Speicheruntersuchung

| Befehl | Beschreibung |
| --------- | ------------- |
| `x/10x $esp` | 10 Wörter ab ESP hexadezimal anzeigen |
| `x/s addr` | Zeichenkette an der Adresse |
| `x/i $eip` | Instruktion an EIP |
| `x/20wx addr` | 20 Wörter hexadezimal anzeigen |
| `x/10gx $rsp` | 10 Qwords ab RSP |

### Schnelles Debugging

```bash
# Starten und bei main anhalten
$ gdb -q ./program
(gdb) break main
(gdb) run

# Absturz untersuchen
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Ablauf der Exploit-Entwicklung

```
1. checksec           → Schutzmechanismen prüfen
2. pattern create     → Muster erzeugen
3. Mit Muster starten → Programm zum Absturz bringen
4. pattern search     → Offset finden
5. Gadgets finden     → ROP-Gadgets
6. Payload erstellen  → Exploit erstellen
7. Testen             → Mit GDB untersuchen
```

---

## 📚 Ressourcen

- [Offizielles GDB-Handbuch](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF-Dokumentation](https://hugsy.github.io/gef/)
- [pwndbg-Dokumentation](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Verwandte Cheatsheets
- [Ghidra](../../Ghidra/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)
- [Linux-Rechteausweitung](../../Linux-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Aufruf und Umgebung:** Die Vorlage mischt Shell-Aufrufe mit Eingaben am Debugger-Prompt; Promptzeichen sind keine Bestandteile der Befehle. Die Shell-Zeile gdb attach ist nicht die dokumentierte Attach-Syntax: attach ist ein interner GDB-Befehl, während die bereits gezeigte Prozessoption zum Startaufruf gehört. Ein Attach benötigt zudem passende Berechtigungen. Erweiterungen sind keine GDB-Bordmittel und ihre Befehle können je nach Version oder Zusatzpaket abweichen. [GNU GDB: Attach](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Attach.html), [GEF: Befehlsreferenz und Erweiterungen](https://hugsy.github.io/gef/).
- **Fortsetzen und Speicherformat:** Die Zahl bei continue bezeichnet den Ignore-Count für den Breakpoint an der aktuellen Haltestelle, nicht eine beliebige Anzahl verschiedener Breakpoints. Bei x ohne ausdrücklich angegebene Einheit wird die zuletzt gewählte Einheit weiterverwendet; „10 Wörter“ ist daher nur mit passender Voreinstellung richtig. Die unterschiedlichen Schreibreihenfolgen von Format und Einheit sind zulässig. [GNU GDB: Ausführung fortsetzen](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Continuing-and-Stepping.html), [GNU GDB: Speicher untersuchen](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html).
- **Architektur und Stack:** Register- und Adressbeispiele mischen 32- und 64-Bit-x86. Feste Rücksprungpositionen relativ zu EBP/RBP setzen einen passenden Stack-Frame voraus; Optimierungen und andere Architekturen können diesen Aufbau verändern. Quellcodezeilen, Variablen und Funktionsnamen benötigen geeignete Debug-Informationen. Die Adressen sind Beispielwerte, keine universellen Ziele. [GNU GDB: Stack-Frames](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Frames.html), [GNU GDB: Register](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Registers.html).
- **Pattern-Beispiele:** Im ersten Ablauf wird trotz erzeugtem zyklischem Muster eine Folge identischer A-Zeichen eingegeben; daraus lässt sich kein eindeutiger Pattern-Offset bestimmen. Der zweite Ablauf erzeugt nicht die anschließend referenzierte Eingabedatei; außerdem zeigt print bei Python-Bytes eine Darstellung des Bytes-Objekts. Alphabet, Teilsequenzlänge und Byte-Reihenfolge müssen bei Erzeugung und Suche zusammenpassen. Die Prozesssubstitution benötigt eine passende Shell. Die Beispiele mischen zudem GEF-/pwndbg-/pwntools-Kontexte und bleiben als ursprüngliche Fragmente erhalten. [GEF: Pattern](https://hugsy.github.io/gef/commands/pattern/), [pwntools: zyklische Muster](https://docs.pwntools.com/en/stable/util/cyclic.html).
- **ASLR und Zustandsänderungen:** Die Kernel-Einstellung im Beispiel betrifft das System, die GDB-Einstellung dagegen den Start des untersuchten Programms auf unterstützten Zielen. GDB deaktiviert diese Randomisierung dort standardmäßig; beim Anhängen an einen bereits laufenden Prozess entsteht dadurch kein neuer Adressraum. Änderungen an Registern und Speicher sowie Funktionsaufrufe verändern den untersuchten Prozess. Ein Schutzmerkmalscheck allein beweist keine Ausnutzbarkeit. [GNU GDB: Programmstart und Randomisierung](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Starting.html), [GNU GDB: Aufruf von Programmfunktionen](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Calling.html).

---

<p align="center">
  <b>🐛 Linux-Debugging beherrschen!</b><br>
  <i>GDB – ein wichtiges Werkzeug für Reverse Engineering unter Linux</i>
</p>
