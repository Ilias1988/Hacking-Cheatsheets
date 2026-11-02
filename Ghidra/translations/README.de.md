# 🔍 Ghidra – Cheatsheet für Reverse Engineering

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              Reverse-Engineering-Werkzeugsammlung der NSA
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Ghidra?](#-was-ist-ghidra)
- [Installation](#-installation)
- [Überblick über die Oberfläche](#-überblick-über-die-oberfläche)
- [Ein Projekt anlegen](#-ein-projekt-anlegen)
- [Grundlagen der Analyse](#-grundlagen-der-analyse)
- [Navigation](#-navigation)
- [Decompiler](#-decompiler)
- [Datentypen und Strukturen](#-datentypen-und-strukturen)
- [Patchen und Ändern](#-patchen-und-ändern)
- [Skripte](#-skripte)
- [Tastenkürzel](#-tastenkürzel)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Ghidra?

**Ghidra** ist eine kostenlose, quelloffene Werkzeugsammlung für Software Reverse Engineering (SRE), die von der NSA entwickelt wurde. Reverse Engineering bedeutet hier, Aufbau und Verhalten kompilierter Software zu untersuchen. Ghidra bietet:

- 🔍 **Disassembler** – Binärcode in Assembleranweisungen umwandeln
- 🔄 **Decompiler** – C-ähnlichen Pseudocode erzeugen
- 📊 **Analyse** – Code automatisiert untersuchen
- 🔧 **Patchen** – Binärdateien gezielt verändern
- 🐍 **Skripte** – Abläufe mit Python oder Java automatisieren
- 👥 **Zusammenarbeit** – Gemeinsame Bearbeitung unterstützen

### Unterstützte Architekturen

| Architektur | Varianten |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Unterstützte Formate

| Format | Beschreibung |
|--------|-------------|
| ELF | Ausführbare Linux-Dateien |
| PE/COFF | Ausführbare Windows-Dateien |
| Mach-O | Ausführbare macOS-Dateien |
| Raw Binary | Firmware, ROM |
| APK/DEX | Android-Anwendungen |
| und viele weitere … | — |

---

## 🚀 Installation

### Download

```bash
# Von der offiziellen Website herunterladen
https://ghidra-sre.org/

# Alternativ die GitHub-Releases verwenden
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Anforderungen

| Anforderung | Version |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | Mindestens 4GB, empfohlen 8GB+ |
| **Speicher** | 1GB+ für die Installation |

### Linux/macOS

```bash
# JDK 17+ installieren
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Ghidra entpacken
unzip ghidra_*.zip
cd ghidra_*

# Starten
./ghidraRun
```

### Windows

```batch
:: JDK 17+ von Oracle oder Adoptium installieren
:: ghidra_*.zip entpacken
:: ghidraRun.bat starten
```

---

<a name="-überblick-über-die-oberfläche"></a>

## 🖥️ Überblick über die Oberfläche

### Wichtige Fenster

| Fenster | Beschreibung |
|--------|-------------|
| **Program Trees** | Dateistruktur des Projekts |
| **Symbol Tree** | Funktionen, Labels und Klassen |
| **Data Type Manager** | Definierte Datentypen |
| **Listing** | Disassemblierte Anweisungen |
| **Decompiler** | C-Pseudocode |
| **Console** | Ausgabe und Skripte |
| **Defined Strings** | Gefundene Zeichenketten |
| **Bookmarks** | Gespeicherte Positionen |

### Listing-Fenster (Disassemblierung)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Funktionsanfang
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Stackframe
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Ein Projekt anlegen

### Neues Projekt

```
1. File → New Project auswählen
2. Auswählen:
   - Non-Shared Project (lokal)
   - Shared Project (gemeinsame Bearbeitung)
3. Verzeichnis auswählen
4. Projektnamen eingeben
5. Auf Finish klicken
```

### Binärdatei importieren

```
1. File → Import File (I) auswählen
2. Binärdatei/Datei auswählen
3. Format auswählen (wird meist automatisch erkannt)
4. Optionen festlegen:
   - Language/Processor
   - Compiler spec
5. Auf OK klicken
6. Analyze? → Yes auswählen (empfohlen)
```

### Analyseoptionen

| Option | Beschreibung |
|--------|-------------|
| ASCII Strings | Zeichenketten finden |
| Decompiler | Dekompilierung aktivieren |
| Stack Analysis | Stackframes untersuchen |
| Reference Analysis | Querverweise finden |
| Function ID | Bibliotheksfunktionen zuordnen |
| Call Convention | Aufrufkonventionen erkennen |

---

## 🔬 Grundlagen der Analyse

### Automatische Analyse

```
Analysis → Auto Analyze (A)
- Führt alle ausgewählten Analysefunktionen aus
- Kann bei großen Binärdateien längere Zeit dauern
- Der Fortschritt erscheint im Aufgabenmonitor
```

### Einmalige Analysefunktionen

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Funktionen erkennen

```
# Alle Funktionen finden
Analysis → Auto Analyze → Enable "Non-returning Functions"

# Funktion manuell anlegen
Rechtsklick auf die Adresse → Create Function (F)

# Undefinierter Code
Rechtsklick → Disassemble (D)
```

---

## 🧭 Navigation

### Zu einer Adresse springen

```
# Tastenkürzel
G → Adresse eingeben → OK

# Beispiele:
0x00401000      # Hexadezimale Adresse
main            # Funktionsname
entry           # Einsprungpunkt
LAB_00401234    # Label
```

### Tastenkürzel zur Navigation

| Tastenkürzel | Aktion |
|----------|--------|
| `G` | Zu einer Adresse springen |
| `Ctrl+G` | Zu einer Adresse springen |
| `Alt+←` | Zur vorherigen Position |
| `Alt+→` | Zur nächsten Position |
| `Ctrl+Shift+E` | Zum Einsprungpunkt |

### Querverweise (XREFs)

```
# Verweise auf die aktuelle Position anzeigen
Rechtsklick → References → Show References To

# Aufrufstellen einer Funktion finden
Funktion auswählen → Rechtsklick → References → Show References To

# Tastatur
Ctrl+Shift+F → Verweise finden
```

### Suchfunktionen

```
# Suche
Search → Memory (S)           # Speicher durchsuchen
Search → For Strings          # Zeichenketten finden
Search → For Direct References # Verweise auf eine Adresse finden
Search → Program Text         # Disassemblierung durchsuchen

# Funktionssuche
Symbol Tree → Filter → Funktionsnamen eingeben
```

---

## 💻 Decompiler

### Decompiler öffnen

```
Window → Decompiler
# Alternativ die Registerkarte Decompiler auswählen
# Dekompiliert die ausgewählte Funktion automatisch
```

### Dekompilierten Code lesen

```c
// Beispiel einer dekompilierten Ausgabe
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

### Dekompilierung verbessern

```
# Variablen umbenennen
Rechtsklick auf die Variable → Rename Variable (L)

# Datentyp ändern
Rechtsklick auf die Variable → Retype Variable (Ctrl+L)

# Kommentare hinzufügen
; → Kommentar am Zeilenende setzen
Ctrl+; → Plate-Kommentar setzen

# Funktionssignatur festlegen
Rechtsklick auf die Funktion → Edit Function Signature
```

### Decompiler-Tipps

| Aktion | Vorgehen |
|--------|-----|
| Variable umbenennen | `L` oder Rechtsklick |
| Typ ändern | `Ctrl+L` |
| Struktur anlegen | Rechtsklick → Auto Create Structure |
| Variablen trennen/zusammenführen | Rechtsklick → Split/Merge |
| Signatur übernehmen | `P` |

---

## 📊 Datentypen und Strukturen

### Struktur anlegen

```
# Methode 1: Über den Data Type Manager
Data Type Manager → Rechtsklick → New → Structure

# Methode 2: Automatisch aus dem Decompiler erstellen
Decompiler → Rechtsklick auf die Variable → Auto Create Structure
```

### Struktur definieren

```c
// Beispielstruktur
struct Player {
    char name[32];      // Offset 0x00
    int health;         // Offset 0x20
    int score;          // Offset 0x24
    float position[3];  // Offset 0x28
};
```

### Struktur anwenden

```
# In der Listing-Ansicht
Adresse auswählen → Rechtsklick → Data → Choose Data Type

# Alternativ
T drücken → Struktur auswählen

# Im Decompiler
Rechtsklick auf die Variable → Retype Variable → Struktur auswählen
```

### Arrays

```
# Array anlegen
Startadresse auswählen → Rechtsklick → Data → Create Array
Anzahl der Elemente eingeben
```

### Aufzählungstypen (Enums)

```
# Aufzählungstyp anlegen
Data Type Manager → Rechtsklick → New → Enum

# Werte definieren
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Patchen und Ändern

### Instruktion patchen

```
# Methode 1: Tastatur
Instruktion auswählen → Ctrl+Shift+G
Neue Instruktion eingeben → Enter drücken

# Methode 2: Menü
Rechtsklick → Patch Instruction
```

### Daten patchen

```
# Bytes ändern
Rechtsklick → Patch Data
Bytes als Hexwerte eingeben

# Alternativ
Bytes auswählen → Ctrl+Shift+G
```

### Häufige Patches

| Original | Gepatcht | Zweck |
|----------|---------|---------|
| `JE` | `JMP` | Immer springen |
| `JNE` | `JMP` | Immer springen |
| `JE` | `NOP` | Sprung auslassen |
| `CALL` | `NOP NOP NOP NOP NOP` | Aufruf überspringen |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Gepatchte Binärdatei exportieren

```
File → Export Program
Format: Binary
Ziel auswählen
```

### NOP-Sled

```
# Mit NOPs auffüllen
Bereich auswählen → Rechtsklick → Clear Code Block
Anschließend: Auswählen → Rechtsklick → Patch → Fill with NOPs
```

---

## 🐍 Skripte

### Script Manager

```
Window → Script Manager
# Alternativ: Tools → Script Manager
```

### Skript ausführen

```
Script Manager → Skript auswählen → Run (grüne Startschaltfläche)
# Alternativ das Skript doppelt anklicken
```

### Python-Skripte (Ghidra Python)

```python
# Beispiel: Alle Funktionen auflisten
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = vorwärts

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Häufige Skriptoperationen

```python
# Aktuelle Adresse abrufen
currentAddress

# Aktuelles Programm abrufen
currentProgram

# Funktionsverwaltung abrufen
fm = currentProgram.getFunctionManager()

# Listing (Disassemblierung) abrufen
listing = currentProgram.getListing()

# Instruktion an einer Adresse abrufen
instr = listing.getInstructionAt(addr)

# Bytes an einer Adresse abrufen
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Label anlegen
createLabel(addr, "my_label", True)

# Kommentar hinzufügen
setEOLComment(addr, "My comment")

# Verweise abrufen
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Nützliche mitgelieferte Skripte

| Skript | Zweck |
|--------|---------|
| FindCrypt | Kryptografische Konstanten finden |
| FunctionID | Bibliotheksfunktionen zuordnen |
| ExportFunctionInfoScript | Funktionsliste exportieren |
| FindStrings | Verweise auf Zeichenketten finden |
| SearchMemory | Nach Mustern suchen |

---

<a name="-tastenkürzel"></a>

## ⌨️ Tastenkürzel

### Navigation

| Tastenkürzel | Aktion |
|----------|--------|
| `G` | Zu einer Adresse springen |
| `Alt+←` | Zurück |
| `Alt+→` | Vorwärts |
| `Ctrl+Shift+E` | Einsprungpunkt |
| `Ctrl+E` | Tabelle exportieren |
| `N` | Zur nächsten undefinierten Position |

### Analyse

| Tastenkürzel | Aktion |
|----------|--------|
| `F` | Funktion anlegen |
| `D` | Disassemblieren |
| `C` | Definition entfernen |
| `P` | Zeiger anlegen |
| `T` | Datentyp anwenden |
| `A` | Automatische Analyse |

### Bearbeiten

| Tastenkürzel | Aktion |
|----------|--------|
| `L` | Label/Variable umbenennen |
| `;` | Kommentar am Zeilenende setzen |
| `Ctrl+;` | Plate-Kommentar setzen |
| `Ctrl+L` | Variablentyp ändern |
| `Ctrl+Shift+G` | Instruktion patchen |

### Suche

| Tastenkürzel | Aktion |
|----------|--------|
| `Ctrl+Shift+F` | Verweise finden |
| `Ctrl+F` | Text suchen |
| `S` | Speicher durchsuchen |
| `Ctrl+B` | Bytes suchen |

### Fenster

| Tastenkürzel | Aktion |
|----------|--------|
| `Ctrl+D` | Decompiler ein-/ausblenden |
| `Ctrl+T` | Data Type Manager ein-/ausblenden |
| `Ctrl+S` | Symbol Tree |

---

## 📊 Schnellübersicht

### Analyseablauf

```
1. Binärdatei importieren → File → Import
2. Automatisch analysieren → Analysis → Auto Analyze (Voreinstellungen übernehmen)
3. Einsprungpunkt/main finden → G → main oder entry
4. Funktionen prüfen → Symbol Tree → Functions
5. Zeichenketten untersuchen → Search → For Strings
6. Programmablauf verfolgen → Aufrufen und Sprüngen folgen
7. Symbole umbenennen → L bei Funktionen/Variablen
8. Dokumentieren → Kommentare hinzufügen (;)
```

### Häufige Aufgaben

| Aufgabe | Vorgehen |
|------|-----|
| main() finden | `G` → `main` oder im Symbol Tree suchen |
| Zeichenketten anzeigen | Search → For Strings |
| Kryptografie finden | FindCrypt-Skript ausführen |
| Imports prüfen | Symbol Tree → Imports |
| Querverweise finden | Auswählen → Ctrl+Shift+F |
| Funktion anlegen | Adresse auswählen → `F` |
| Dekompilieren | Window → Decompiler |

### Checkliste für Reverse Engineering

```
☐ Dateityp bestimmen (PE, ELF usw.)
☐ Automatische Analyse ausführen
☐ Einsprungpunkt / main finden
☐ Zeichenketten auf Hinweise prüfen
☐ Interessante Funktionen bestimmen
☐ Programmablauf erfassen
☐ Auffällige Funktionen analysieren
☐ Ergebnisse mit Kommentaren dokumentieren
☐ Funktionen/Variablen umbenennen
☐ Bei Bedarf Strukturen anlegen
```

### Funktionsaufrufkonventionen

| Konvention | Rückgabe | Argumente | Stackbereinigung |
|------------|--------|------|----------------|
| **cdecl** | EAX | Stack (R→L) | Aufrufer |
| **stdcall** | EAX | Stack (R→L) | Aufgerufene Funktion |
| **fastcall** | EAX | ECX, EDX, Stack | Aufgerufene Funktion |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Aufrufer |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Aufrufer |

---

## 📚 Ressourcen

- [Offizielle Ghidra-Website](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Ghidra-Dokumentation](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Verwandte Cheatsheets
- [Binwalk](../../Binwalk/translations/README.de.md)
- [Linux-Befehle](../../Linux-Commands/translations/README.de.md)
- [Volatility](../../Volatility/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Version und Python-Laufzeit:** JDK 17 ist eine historische Anforderung. Die aktuelle offizielle Projektanleitung nennt JDK 25 in 64 Bit; maßgeblich ist die zur verwendeten Ghidra-Version gehörende Anleitung. Der Python-Block mit f-String passt nicht zu älterem Jython auf Python-2-Basis. PyGhidra bietet dagegen natives CPython 3; Laufzeit und Startmodus müssen zum Skript passen. Die Originalbefehle bleiben erhalten. [Ghidra: Installation](https://github.com/NationalSecurityAgency/ghidra#install), [PyGhidra](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/README.md), [Ghidra: ältere Jython-Skripte](https://ghidra.re/ghidra_docs/GhidraClass/Intermediate/Scripting.html).
- **Analyse und Oberfläche:** Der Analyzer für nicht zurückkehrende Funktionen ist keine allgemeine Suche nach allen Funktionen. Automatische Erkennung und C-ähnliche Dekompilierung bleiben Analyseergebnisse, nicht der ursprüngliche Quellcode. Menüpfade, Tastenkürzel und verfügbare Skripte hängen von Version, aktivem Fenster und Erweiterungen ab; die Liste ist keine Zusage, dass jeder genannte Skriptname mitgeliefert wird. [Ghidra: Einführung und Analyse](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html), [Ghidra: nicht zurückkehrende Funktionen](https://ghidra.re/ghidra_docs/GhidraClass/Advanced/improvingDisassemblyAndDecompilation.pdf).
- **Patchen und Export:** Das Entfernen einer Code-Definition ist nicht dasselbe wie das Überschreiben der Bytes mit NOPs. Die fünf NOPs im CALL-Beispiel setzen eine passende Instruktionslänge und Architektur voraus. Auch ein Export im Format Binary ist nicht automatisch ein weiterhin lauffähiger PE-/ELF-Container; Auswahl, Speicherblöcke und Exportformat sind entscheidend. Die schematischen Schritte und Bytes bleiben unverändert. [Ghidra: Einführung und Code-Markup](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html), [Ghidra: Exportformate](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/Base/src/main/help/help/topics/ExporterPlugin/exporter.htm).
- **Aufrufkonventionen:** Die Tabelle ist eine vereinfachte Übersicht für typische Ganzzahl-/Zeigerargumente. Typ, Architektur und ABI beeinflussen Übergabe und Rückgabe; unter Windows x64 werden Gleitkommaargumente beispielsweise über XMM-Register übergeben, und der Aufrufer reserviert zusätzlichen Stackplatz. Die Registerliste ist daher keine vollständige ABI-Beschreibung. [Microsoft: x64-Aufrufkonvention](https://learn.microsoft.com/en-us/cpp/build/x64-calling-convention).

---

<p align="center">
  <b>🔍 Reverse Engineering beherrschen!</b><br>
  <i>Ghidra – ein Beitrag der NSA für die Sicherheitsgemeinschaft</i>
</p>
