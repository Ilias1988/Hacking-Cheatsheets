# 🔧 x64dbg – Cheatsheet zum Windows-Debugging

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Debugger für Windows x64/x32
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist x64dbg?](#-was-ist-x64dbg)
- [Installation](#-installation)
- [Oberfläche im Überblick](#-oberfläche-im-überblick)
- [Grundlegende Verwendung](#-grundlegende-verwendung)
- [Haltepunkte](#-haltepunkte)
- [Einzelschritte und Ausführung](#-einzelschritte-und-ausführung)
- [Speicheroperationen](#-speicheroperationen)
- [Register](#-register)
- [Stack-Analyse](#-stack-analyse)
- [Patches erstellen](#-patches-erstellen)
- [Skripting](#-skripting)
- [Tastenkürzel](#-tastenkürzel)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist x64dbg?

**x64dbg** ist ein quelloffener Debugger für 64- und 32-Bit-Windows-Anwendungen. Er knüpft an OllyDbg an und bietet moderne Funktionen:

- 🔍 **User-Mode-Debugging** – Windows-Anwendungen auf Benutzerebene untersuchen
- 🦠 **Malware-Analyse** – Verdächtige ausführbare Dateien untersuchen
- 💥 **Exploit-Entwicklung** – Windows-Schwachstellen erforschen
- 🔧 **Reverse Engineering** (Analyse vorhandener Programme) – Das Verhalten von Binärdateien verstehen
- 🧩 **Plugin-Unterstützung** – Durch Plugins erweiterbar

### Funktionen

| Funktion | Beschreibung |
| --------- | ------------- |
| **GUI** | Moderne, anpassbare Oberfläche |
| **x64-Unterstützung** | Vollständiges 64-Bit-Debugging |
| **Skripting** | Integrierte Skriptsprache |
| **Plugins** | Umfangreiche Plugin-Auswahl |
| **Symbols** | Unterstützung für PDB-Symbole |
| **Open Source** | Kostenlos und aktiv weiterentwickelt |

---

## 🚀 Installation

### Download

```
# Offizielle Website
https://x64dbg.com/

# GitHub-Veröffentlichungen
https://github.com/x64dbg/x64dbg/releases

# Download: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Einrichtung

```
1. ZIP-Datei an einen dauerhaften Speicherort entpacken (z. B. C:\x64dbg\)
2. x96dbg.exe starten (Startprogramm für beide Varianten)
   - x32dbg.exe → 32-Bit-Debugging
   - x64dbg.exe → 64-Bit-Debugging
3. Optional zu PATH hinzufügen oder Verknüpfungen erstellen
```

### Empfohlene Plugins

| Plugin | Zweck |
| -------- | --------- |
| **ScyllaHide** | Anti-Debug-Prüfungen umgehen |
| **SwissArmyKnife** | Verschiedene Hilfsfunktionen |
| **x64dbgpy** | Skripting mit Python |
| **TitanHide** | Anti-Debug-Abwehr auf Kernel-Ebene |
| **OllyDumpEx** | Speicherabbilder erstellen |
| **Multiline Ultimate Assembler** | Patches über mehrere Zeilen |

### Plugins installieren

```
1. Plugin herunterladen (DLL-Datei)
2. Kopieren nach: x64dbg\release\x32\plugins\ (32-bit)
           x64dbg\release\x64\plugins\ (64-bit)
3. x64dbg neu starten
```

---

<a name="-oberfläche-im-überblick"></a>

## 🖥️ Oberfläche im Überblick

### Hauptfenster

| Fenster | Beschreibung | Tastenkürzel |
| -------- | ------------- | ---------- |
| **CPU** | Disassemblierungsansicht | Alt+C |
| **Log** | Debug-Ausgabe | Alt+L |
| **Memory Map** | Speicherbereiche | Alt+M |
| **Call Stack** | Aufrufverlauf | Alt+K |
| **Breakpoints** | Alle Haltepunkte | Alt+B |
| **Symbols** | Geladene Symbole | Alt+E |
| **Threads** | Thread-Liste | Alt+T |
| **Handles** | Handle-Liste | Alt+H |

### Bereiche des CPU-Fensters

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Statusleiste

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

## 💻 Grundlegende Verwendung

### Debugging starten

```
# Ausführbare Datei öffnen
File → Open (F3)

# An einen Prozess anhängen
File → Attach (Alt+A)

# Öffnen und ausführen
Debug → Run (F9)

# Debugging neu starten
Debug → Restart (Ctrl+F2)

# Debugging beenden
Debug → Stop (Alt+F2)
```

### Befehlszeile

```
# Befehle in die Befehlszeile am unteren Rand eingeben

# Zu einer Adresse wechseln
g 0x401000
goto 0x401000

# Zu einem Ausdruck wechseln
g GetProcAddress
g kernel32.LoadLibraryA

# Zeichenkette suchen
/s "password"

# Hexbytes suchen
/x 90 90 90 90

# Muster suchen
/p "8B ?? 89"
```

### Navigation

```
# Zu einer Adresse wechseln
Ctrl+G → Adresse eingeben

# Zum Programmeinstieg wechseln
Debug → Entry Point

# Im Disassembler verfolgen
Rechtsklick → Follow in Disassembler

# Zurück
- (Minustaste)

# Vorwärts
+ (Plustaste)
```

---

## 🔴 Haltepunkte

### Software-Haltepunkte (INT3)

```
# Haltepunkt an der aktuellen Adresse umschalten
F2

# Haltepunkt per Befehl setzen
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Bedingten Haltepunkt setzen
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Beim Laden eines Moduls anhalten
bp $module(kernel32)
```

### Hardware-Haltepunkte

```
# Hardware-Haltepunkt setzen (auf 4 begrenzt)
Rechtsklick → Breakpoint → Hardware → Execute
Rechtsklick → Breakpoint → Hardware → Access
Rechtsklick → Breakpoint → Hardware → Write

# Per Befehl
bph 0x401000, x, 1    # Ausführen
bph 0x401000, r, 4    # Lesen (4 Bytes)
bph 0x401000, w, 4    # Schreiben (4 Bytes)

# Größen: 1, 2, 4 oder 8 Bytes
```

### Speicher-Haltepunkte

```
# Bei Speicherzugriff anhalten
Rechtsklick in die Speicheransicht → Breakpoint → Memory → Access
Rechtsklick in die Speicheransicht → Breakpoint → Memory → Write

# Per Befehl
bpm 0x401000, r       # Lesen
bpm 0x401000, w       # Schreiben
bpm 0x401000, x       # Ausführen
```

### Bedingte Haltepunkte

```
# Anhalten, wenn die Bedingung wahr ist
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Bei einer bestimmten Trefferzahl anhalten
bp 0x401000, "", 5    # Beim 5. Treffer anhalten

# Protokollieren, ohne anzuhalten
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Haltepunkte verwalten

```
# Alle Haltepunkte auflisten
Breakpoints window (Alt+B)

# Haltepunkt löschen
bc 0x401000          # Bestimmten Haltepunkt löschen
bc                   # Alle löschen

# Aktivieren/deaktivieren
bd 0x401000          # Deaktivieren
be 0x401000          # Aktivieren

# Alle deaktivieren
bd *

# Alle aktivieren
be *
```

---

<a name="-einzelschritte-und-ausführung"></a>

## ▶️ Einzelschritte und Ausführung

### Einzelschritte

| Aktion | Tastenkürzel | Beschreibung |
| -------- | ---------- | ------------- |
| Step Into | F7 | Eine Anweisung ausführen |
| Step Over | F8 | Funktionsaufrufe überspringen |
| Step Out | Ctrl+F9 | Bis zur Rückkehr ausführen |
| Run to Selection | F4 | Bis zum Cursor ausführen |
| Run to User Code | Alt+F9 | Systemcode verlassen |

### Ausführung

| Aktion | Tastenkürzel | Beschreibung |
| -------- | ---------- | ------------- |
| Run | F9 | Ausführung fortsetzen |
| Run Skipping | Ctrl+F7 | Aktuelle Anweisung überspringen |
| Pause | F12 | Ausführung anhalten |
| Restart | Ctrl+F2 | Debugging neu starten |
| Close | Alt+F2 | Debugging beenden |

### Tracing

```
# Trace Into (alle Anweisungen protokollieren)
Debug → Trace Into (Ctrl+F7)

# Trace Over
Debug → Trace Over (Ctrl+F8)

# Trace aufzeichnen
Debug → Trace Record

# Trace beenden
Debug → Stop Trace
```

### Anweisung überspringen

```
# Aktuelle Anweisung überspringen, ohne sie auszuführen
Rechtsklick → Set New Origin Here

# Alternativ
Ctrl+Shift+O
```

---

## 🔍 Speicheroperationen

### Speicheransicht

```
# Adresse in der Speicheransicht verfolgen
Rechtsklick → Follow in Dump → Address/Selection

# Zu einer Adresse in der Speicheransicht wechseln
Ctrl+G (in der Speicheransicht)

# Darstellung der Speicheransicht ändern
Rechtsklick → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Speicherbelegung

```
# Memory Map öffnen
View → Memory Map (Alt+M)

# Zeigt alle Speicherbereiche:
- Address
- Size
- Section
- Type (Image/Private/Mapped)
- Protection (Execute/Read/Write)
- Module name
```

### Speicher durchsuchen

```
# In der Befehlszeile suchen
/s "string"           # ASCII-Zeichenkette
/s L"string"          # Unicode-Zeichenkette
/x 90 90 90           # Hexbytes
/p "8B ?? 89"         # Muster mit Platzhaltern

# Querverweise finden
Rechtsklick → Find references to → Selected Address

# Zeichenketten finden
Rechtsklick → Search for → All Strings
```

### Speicherschutz

```
# Schutz anzeigen
Memory Map → Protection column

# Häufige Schutzattribute
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Speicher exportieren

```
# Speicher in eine Datei exportieren
Rechtsklick in Memory Map → Dump Memory to File

# Mit Plugins exportieren
Plugins → OllyDumpEx → Dump
```

---

## 📊 Register

### Registerbereich

```
# Allgemeine Register (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Allgemeine Register (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Segmentregister
CS, DS, SS, ES, FS, GS

# Flags (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Register ändern

```
# Zum Bearbeiten auf das Register doppelklicken
# Alternativ Rechtsklick → Modify Value

# Über die Befehlszeile
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Zero Flag setzen
```

### Wichtige Flags

| Flag | Name | Gesetzt bei |
| ------ | ------ | ---------- |
| ZF | Zero Flag | Ergebnis ist null |
| CF | Carry Flag | Überlauf ohne Vorzeichen |
| SF | Sign Flag | Ergebnis ist negativ |
| OF | Overflow Flag | Überlauf mit Vorzeichen |
| DF | Direction Flag | Verarbeitungsrichtung von Zeichenketten |
| TF | Trap Flag | Einzelschritt |

### Flags umschalten

```
# Im Registerbereich auf den Buchstaben des Flags klicken
# Alternativ Befehle verwenden:
r zf=!zf         # Zero Flag umschalten
r cf=0           # Carry Flag löschen
r sf=1           # Sign Flag setzen
```

---

## 📚 Stack-Analyse

### Stack-Bereich

```
# Zeigt den Stack-Inhalt mit:
- Address
- Value
- Comments (if available)

# Optionen per Rechtsklick:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify
```

### Aufrufliste

```
# Call Stack öffnen
View → Call Stack (Alt+K)

# Zeigt:
- Return Address
- From (caller)
- To (callee)
- Size
- Comment
```

### Stack-Operationen

```
# Zu einer Stack-Adresse wechseln
Rechtsklick → Follow DWORD in Stack

# Stack-Wert ändern
Rechtsklick → Modify → Edit

# Push/Pop simulieren
r esp=esp-4      # Push (32-bit)
r esp=esp+4      # Pop (32-bit)
r rsp=rsp-8      # Push (64-bit)
r rsp=rsp+8      # Pop (64-bit)
```

### Stack-Aufbau (32-bit stdcall)

```
[ESP]      → Rücksprungadresse
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

Nach PUSH EBP; MOV EBP, ESP:
[EBP]      → Gesichertes EBP
[EBP+4]    → Rücksprungadresse
[EBP+8]    → Arg1
[EBP-4]    → Local1
```

---

## 🔧 Patches erstellen

### Anweisungen assemblieren

```
# Rechtsklick auf eine Anweisung → Assemble (Space)
# Neue Anweisung(en) eingeben

# Beispiele:
NOP                    # Keine Operation
JMP 0x401020           # Sprung
MOV EAX, 0             # EAX auf null setzen
XOR EAX, EAX           # EAX auf null setzen (kürzer)
RET                    # Rückkehr
```

### Mit NOPs auffüllen

```
# Bereich auswählen → Rechtsklick → Binary → Fill with NOPs

# Alternativ auswählen und Space drücken:
# Mehrere NOPs eingeben
```

### Binärdaten bearbeiten

```
# Bytes direkt bearbeiten
Rechtsklick → Binary → Edit

# Mit einem Wert auffüllen
Rechtsklick → Binary → Fill

# Binärdaten kopieren/einfügen
Rechtsklick → Binary → Copy
Rechtsklick → Binary → Paste
```

### Häufige Patches

| Vorher | Nachher | Zweck |
| -------- | ------- | --------- |
| `74 XX` (JE) | `75 XX` (JNE) | Bedingung umkehren |
| `75 XX` (JNE) | `74 XX` (JE) | Bedingung umkehren |
| `74 XX` (JE) | `EB XX` (JMP) | Immer springen |
| `74 XX` (JE) | `90 90` (NOP) | Nie springen |
| `0F 84` (JE) | `0F 85` (JNE) | Bedingung umkehren (Near-Sprung) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Aufruf überspringen |

### Patches speichern

```
# Methode 1: Patch File
File → Patch File

# Methode 2: Patches exportieren
Rechtsklick → Copy → Selection
# In einen Hexeditor einfügen

# Methode 3: Plugin
Plugins → Export modified executable
```

---

## 📝 Skripting

### Befehlssyntax

```
# Variablen
$var = 0x401000

# Vergleich
cmp eax, 0

# Bedingter Sprung
je label
jne label

# Labels
label:

# Kommentare
; Dies ist ein Kommentar
```

### Einfaches Skript

```
; Skript zum Finden von WinMain
mov $addr, 0x401000        ; Startadresse
loop:
  find $addr, "8B EC"       ; PUSH EBP; MOV EBP, ESP finden
  cmp $result, 0
  je notfound
  msg "Found at: {$result}"
  add $addr, $result
  add $addr, 1
  jmp loop
notfound:
  msg "Pattern not found"
ret
```

### Skriptbefehle

| Befehl | Beschreibung |
| --------- | ------------- |
| `msg "text"` | Meldung anzeigen |
| `log "text"` | In der Konsole protokollieren |
| `find addr, "pattern"` | Muster suchen |
| `bp addr` | Haltepunkt setzen |
| `bc addr` | Haltepunkt löschen |
| `sti` | Einzelschritt hinein |
| `sto` | Einzelschritt über Aufruf |
| `run` | Ausführen |
| `ret` | Rückkehr/beenden |

### Skript ausführen

```
# Skript laden und ausführen
Scripts → Run Script

# Alternativ über die Befehlszeile
scr "scriptfile.txt"
```

---

<a name="-tastenkürzel"></a>

## ⌨️ Tastenkürzel

### Allgemein

| Tastenkürzel | Aktion |
| ---------- | -------- |
| F3 | Datei öffnen |
| Alt+A | An einen Prozess anhängen |
| Ctrl+F2 | Neu starten |
| Alt+F2 | Debugging beenden |
| Ctrl+G | Zu einer Adresse wechseln |
| Ctrl+F | Suchen |

### Debugging

| Tastenkürzel | Aktion |
| ---------- | -------- |
| F2 | Haltepunkt umschalten |
| F4 | Bis zur Auswahl ausführen |
| F7 | Einzelschritt hinein |
| F8 | Einzelschritt über Aufruf |
| F9 | Ausführen |
| F12 | Anhalten |
| Ctrl+F9 | Bis zur Rückkehr ausführen |

### Navigation

| Tastenkürzel | Aktion |
| ---------- | -------- |
| - | Zurück |
| + | Vorwärts |
| * | Zu EIP/RIP wechseln |
| Enter | Aufruf/Sprung verfolgen |
| Escape | Zurück |

### Ansichten

| Tastenkürzel | Aktion |
| ---------- | -------- |
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Breakpoints |
| Alt+E | Symbols |
| Alt+T | Threads |

### Bearbeitung

| Tastenkürzel | Aktion |
| ---------- | -------- |
| Space | Assemblieren |
| ; | Kommentar hinzufügen |
| : | Label hinzufügen |
| N | Umbenennen |

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
| --------- | ------------- |
| `bp addr` | Haltepunkt setzen |
| `bc addr` | Haltepunkt löschen |
| `bph addr, x` | Hardware-Haltepunkt |
| `g addr` | Zu einer Adresse wechseln |
| `r reg=val` | Register setzen |
| `sti` | Einzelschritt hinein |
| `sto` | Einzelschritt über Aufruf |
| `run` | Ausführen |

### Häufige Bytefolgen

| Bytes | Anweisung |
| ------- | ------------- |
| `90` | NOP |
| `CC` | INT3 (breakpoint) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

### Analyseablauf

```
1. Ausführbare Datei laden   → F3
2. Haltepunkt am Einstieg setzen → F2 am Einstieg
3. Bis zum Haltepunkt ausführen → F9
4. Code in Einzelschritten durchgehen → F7/F8
5. Register/Speicher untersuchen → Ansichtsbereiche
6. Weitere Haltepunkte setzen
7. Analyse fortsetzen
8. Bei Bedarf patchen        → Space
9. Gepatchte Datei speichern
```

### Anti-Debug-Prüfungen umgehen

```
# ScyllaHide-Plugin installieren
Plugins → ScyllaHide → Options → Select profile

# Manuelle Patches:
- IsDebuggerPresent-Aufrufe durch NOPs ersetzen
- PEB.BeingDebugged verändern
- Ausnahmen passend behandeln
```

---

## 📚 Ressourcen

- [x64dbg Official](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [x64dbg Documentation](https://help.x64dbg.com/)
- [x64dbg Plugins](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Verwandte Cheatsheets
- [Ghidra](../../Ghidra/translations/README.de.md)
- [GDB](../../GDB/translations/README.de.md)
- [Windows PrivEsc](../../Windows-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Befehlssemantik:** Die Vorlage vermischt Navigation und Ausführung. g und r sind Aliasse von run; mit einer Adresse wird vor dem Fortsetzen ein einmaliger Haltepunkt gesetzt. Sie sind daher keine bloßen Ansichtswechsel beziehungsweise Registerzuweisungen. Die Suchformen /s, /x und /p sowie scr sind nicht die Befehle der aktuellen dokumentierten Such-/Skriptlisten. Register und Flags haben eigene Ausdrucksregeln; Flags werden dort mit Unterstrich benannt. Die Zahlen werden standardmäßig hexadezimal ausgewertet. Die Originalzeilen bleiben unverändert. [x64dbg: run](https://help.x64dbg.com/en/latest/commands/debug-control/run.html), [Werte und Flags](https://help.x64dbg.com/en/latest/introduction/Values.html), [Suchbefehle](https://help.x64dbg.com/en/latest/commands/searching/index.html), [Skriptbefehle](https://help.x64dbg.com/en/latest/commands/script/index.html).
- **Haltepunkte:** Das zweite bp-Argument ist ein Name, keine Bedingung; das dritte legt den Typ fest, nicht die Trefferzahl. Bedingungen, Trefferzähler und Protokollierung werden gesondert konfiguriert. bplog allein unterdrückt das Anhalten nicht. DLL-Ladeereignisse sind von einem gewöhnlichen Adresshaltepunkt zu unterscheiden. Bei Hardware-Haltepunkten bedeutet r Lesen und Schreiben; Größen und Ausrichtung sind architekturabhängig. Ein bpm-Haltepunkt betrifft den gesamten Speicherbereich der Adresse; der Typ als zweites Argument ist eine dokumentierte Kurzform mit eigener Wiederherstellungssemantik. [x64dbg: SetBPX](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html), [bedingte Haltepunkte](https://help.x64dbg.com/en/latest/introduction/ConditionalBreakpoint.html), [DLL-Ereignisse](https://help.x64dbg.com/en/latest/commands/breakpoint-control/LibrarianSetBreakpoint.html), [Hardware-Haltepunkte](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html), [Speicher-Haltepunkte](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetMemoryBPX.html).
- **Beispielskript:** find liefert eine absolute virtuelle Adresse in $result und sucht nur im betreffenden Speicherbereich. Das anschließende Addieren dieses Ergebnisses zum bisherigen Startwert ist daher kein konsistenter Suchfortschritt. Das Muster 8B EC steht in der eigenen Byte-Tabelle nur für MOV EBP, ESP; es enthält nicht das dort mit 55 angegebene PUSH EBP und identifiziert WinMain nicht eindeutig. Die mit # markierten Erläuterungen sind keine vollständige Skriptsyntax; im dokumentierten Befehlsformat trennt ein Semikolon mehrere Befehle. Die Skriptzeilen und Meldungen bleiben unverändert. [x64dbg: Befehlssyntax](https://help.x64dbg.com/en/latest/commands/), [x64dbg: find und Ergebnis](https://help.x64dbg.com/en/latest/commands/searching/find.html).
- **Architektur und Patches:** Adressen, Registerbreiten und der gezeigte stdcall-Stack sind schematische Beispiele. Das Verschieben von ESP/RSP allein bildet keine vollständige PUSH-/POP-Operation nach, weil kein Wert geschrieben beziehungsweise geladen wird. Auch ein CALL wird nicht allgemein durch fünf NOPs ersetzt: Dafür müssen Kodierung und tatsächliche Instruktionslänge passen. Die Register- und Patch-Beispiele sind keine architekturunabhängige Anleitung. [x64dbg: Werte und Registerbreiten](https://help.x64dbg.com/en/latest/introduction/Values.html), [x64dbg: Assemblieren](https://help.x64dbg.com/en/latest/commands/misc/asm.html).
- **Oberfläche und Erweiterungen:** Menüs und Tastenkürzel können mit Version und Konfiguration abweichen; die Vorlage nennt Ctrl+F7 widersprüchlich für Tracing und Überspringen. Die dokumentierte skip-Funktion überspringt Anweisungen, während andere Ausführungsbefehle andere Wirkungen haben. Plugin-Verfügbarkeit, Architektur und Treibervoraussetzungen müssen zum konkreten System passen; ein Plugin oder ein einzelner Patch garantiert keine vollständige Umgehung aller Anti-Debug-Prüfungen. [x64dbg: skip](https://help.x64dbg.com/en/latest/commands/debug-control/skip.html), [x64dbg: Plugins](https://github.com/x64dbg/x64dbg/wiki/Plugins).

---

<p align="center">
  <b>🔧 Windows-Debugging beherrschen!</b><br>
  <i>x64dbg – der moderne Nachfolger von OllyDbg</i>
</p>
