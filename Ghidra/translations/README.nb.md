# 🔍 Ghidra – hurtigreferanse for omvendt utvikling

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              NSAs verktøysamling for omvendt utvikling
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Gratis">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Ghidra](#-hva-er-ghidra)
- [Installasjon](#-installasjon)
- [Oversikt over grensesnittet](#-oversikt-over-grensesnittet)
- [Opprette et prosjekt](#-opprette-et-prosjekt)
- [Grunnleggende analyse](#-grunnleggende-analyse)
- [Navigasjon](#-navigasjon)
- [Dekompilator](#-dekompilator)
- [Datatyper og strukturer](#-datatyper-og-strukturer)
- [Endring av binærkode](#-endring-av-binærkode)
- [Skripting](#-skripting)
- [Tastatursnarveier](#-tastatursnarveier)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Ghidra

> **Versjon og tolkning:** Installasjonseksemplene med JDK 17 er eldre; bruk JDK-versjonen som den valgte utgaven krever ([prosjektets installasjonskrav](https://github.com/NationalSecurityAgency/ghidra#install)). Python-eksemplet med f-streng krever Python 3, for eksempel [PyGhidra](https://raw.githubusercontent.com/NationalSecurityAgency/ghidra/master/Ghidra/Features/PyGhidra/README.md), og fungerer ikke uendret i eldre Jython 2.7. `currentProgram` og hjelpefunksjonene forutsetter en Ghidra-skriptkontekst. [Binary-eksportøren](https://ghidra.re/ghidra_docs/api/ghidra/app/util/exporter/BinaryExporter.html) eksporterer minneblokker som rå byte; resultatet er ikke automatisk en komplett kjørbar PE-/ELF-fil. Menyer, analysemoduler, skript og hurtigtaster avhenger av versjon, installerte utvidelser og aktivt vindu; blant annet er `P` oppført for ulike handlinger. Non-returning-analyse finner ikke generelt alle funksjoner. Oppryddingskolonnen gjelder hvem som rydder stakken; kallkonvensjonstabellen er forenklet for heltallsargumenter og klassiske x86-/x64-ABI-er. Instruksjonsendringer og fem NOP-byte forutsetter riktig instruksjonslengde og arkitektur. Originaleksemplene er bevart.

**Ghidra** er en gratis verktøysamling med åpen kildekode for omvendt utvikling av programvare (SRE), utviklet av NSA. Den tilbyr:

- 🔍 **Disassembler** – omgjør binærkode til assembly
- 🔄 **Dekompilator** – lager C-lignende pseudokode
- 📊 **Analyse** – automatisert kodeanalyse
- 🔧 **Endring av binærkode (patching)** – endre binærfiler
- 🐍 **Skripting** – automatisering med Python/Java
- 👥 **Samarbeid** – støtte for flere brukere

### Støttede arkitekturer

| Arkitektur | Varianter |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Støttede formater

| Format | Beskrivelse |
|--------|-------------|
| ELF | Kjørbare Linux-filer |
| PE/COFF | Kjørbare Windows-filer |
| Mach-O | Kjørbare macOS-filer |
| Raw Binary | Fastvare, ROM |
| APK/DEX | Android-apper |
| og mange flere ... |

---

## 🚀 Installasjon

### Last ned

```bash
# Last ned fra det offisielle nettstedet
https://ghidra-sre.org/

# Eller GitHub-utgivelser
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Krav

| Krav | Versjon |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | Minimum 4GB, 8GB+ anbefales |
| **Disk** | 1GB+ til installasjonen |

### Linux/macOS

```bash
# Installer JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Pakk ut Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Kjør
./ghidraRun
```

### Windows

```batch
:: Installer JDK 17+ fra Oracle eller Adoptium
:: Pakk ut ghidra_*.zip
:: Kjør ghidraRun.bat
```

---

<a id="-oversikt-over-grensesnittet"></a>
## 🖥️ Oversikt over grensesnittet

### Hovedvinduer

| Vindu | Beskrivelse |
|--------|-------------|
| **Program Trees** | Filstruktur i prosjektet |
| **Symbol Tree** | Funksjoner, etiketter og klasser |
| **Data Type Manager** | Definerte datatyper |
| **Listing** | Disassemblert kode |
| **Decompiler** | C-pseudokode |
| **Console** | Utdata og skripting |
| **Defined Strings** | Strenger som er funnet |
| **Bookmarks** | Lagrede plasseringer |

### Listing-vinduet (disassemblering)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Function start
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Stack frame
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Opprette et prosjekt

### Nytt prosjekt

```
1. Velg File → New Project
2. Velg:
   - Non-Shared Project (lokalt)
   - Shared Project (samarbeid)
3. Velg katalog
4. Skriv inn prosjektnavn
5. Klikk på Finish
```

### Importer en binærfil

```
1. Velg File → Import File (I)
2. Velg binærfil eller annen fil
3. Velg format (gjenkjennes vanligvis automatisk)
4. Angi valg:
   - Language/Processor
   - Compiler spec
5. Klikk på OK
6. Analyze? → Yes (anbefales)
```

### Analysevalg

| Valg | Beskrivelse |
|--------|-------------|
| ASCII Strings | Finn strenger |
| Decompiler | Aktiver dekompilering |
| Stack Analysis | Analyse av stakkrammer |
| Reference Analysis | Finn kryssreferanser |
| Function ID | Gjenkjenn bibliotekfunksjoner |
| Call Convention | Identifiser kallkonvensjoner |

---

## 🔬 Grunnleggende analyse

### Automatisk analyse

```
Analysis → Auto Analyze (A)
- Kjører alle valgte analysemoduler
- Kan ta tid for store binærfiler
- Fremdrift vises i oppgaveoversikten
```

### Engangsanalyser

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Identifisering av funksjoner

```
# Finn alle funksjoner
Analysis → Auto Analyze → Enable "Non-returning Functions"

# Opprett en funksjon manuelt
Høyreklikk på adressen → Create Function (F)

# Kode som ikke er definert
Høyreklikk → Disassemble (D)
```

---

## 🧭 Navigasjon

### Gå til adresse

```
# Hurtigtast
G → skriv inn adresse → OK

# Eksempler:
0x00401000      # Heksadesimal adresse
main            # Funksjonsnavn
entry           # Startpunkt
LAB_00401234    # Etikett
```

### Hurtigtaster for navigasjon

| Hurtigtast | Handling |
|----------|--------|
| `G` | Gå til adresse |
| `Ctrl+G` | Gå til adresse |
| `Alt+←` | Gå tilbake |
| `Alt+→` | Gå fremover |
| `Ctrl+Shift+E` | Gå til startpunktet |

### Kryssreferanser (XREF-er)

```
# Vis referanser til gjeldende plassering
Høyreklikk → References → Show References To

# Finn hvor funksjonen blir kalt
Velg funksjonen → høyreklikk → References → Show References To

# Tastatur
Ctrl+Shift+F → finn referanser
```

### Søkefunksjoner

```
# Søk
Search → Memory (S)           # Søk i minnet
Search → For Strings          # Finn strenger
Search → For Direct References # Finn referanser til adressen
Search → Program Text         # Søk i disassemblert kode

# Søk etter funksjon
Symbol Tree → Filter → skriv funksjonsnavnet
```

---

## 💻 Dekompilator

### Åpne dekompilatoren

```
Window → Decompiler
# Eller klikk på Decompiler-fanen
# Dekompilerer den valgte funksjonen automatisk
```

### Lese dekompilert kode

```c
// Eksempel på dekompilert kode
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

### Forbedre dekompileringen

```
# Gi variabler nye navn
Høyreklikk på variabelen → Rename Variable (L)

# Endre datatype
Høyreklikk på variabelen → Retype Variable (Ctrl+L)

# Legg til kommentarer
; → Set EOL comment (kommentar på slutten av linjen)
Ctrl+; → Set plate comment (kommentarblokk)

# Angi funksjonssignatur
Høyreklikk på funksjonen → Edit Function Signature
```

### Tips for dekompilatoren

| Handling | Fremgangsmåte |
|--------|-----|
| Gi variabelen nytt navn | `L` eller høyreklikk |
| Endre type | `Ctrl+L` |
| Opprett en struct | Høyreklikk → Auto Create Structure |
| Del eller slå sammen variabler | Høyreklikk → Split/Merge |
| Bruk signaturen | `P` |

---

## 📊 Datatyper og strukturer

### Opprette en struktur

```
# Metode 1: Fra Data Type Manager
Data Type Manager → høyreklikk → New → Structure

# Metode 2: Opprett automatisk fra dekompilatoren
Decompiler → høyreklikk på variabelen → Auto Create Structure
```

### Definere en struktur

```c
// Eksempel på struktur
struct Player {
    char name[32];      // offset 0x00
    int health;         // offset 0x20
    int score;          // offset 0x24
    float position[3];  // offset 0x28
};
```

### Bruke en struktur

```
# I Listing-visningen
Velg adresse → høyreklikk → Data → Choose Data Type

# Eller
Trykk T → velg struktur

# I Decompiler
Høyreklikk på variabelen → Retype Variable → velg strukturtypen
```

### Tabeller (arrays)

```
# Opprett en tabell (array)
Velg startadresse → høyreklikk → Data → Create Array
Skriv inn antall elementer
```

### Enumerasjoner (enums)

```
# Opprett en enum
Data Type Manager → høyreklikk → New → Enum

# Definer verdier
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Endring av binærkode

### Endre en instruksjon

```
# Metode 1: Tastatur
Velg instruksjonen → Ctrl+Shift+G
Skriv den nye instruksjonen → trykk Enter

# Metode 2: Meny
Høyreklikk → Patch Instruction
```

### Endre data

```
# Endre byte
Høyreklikk → Patch Data
Skriv heksadesimale byte

# Eller
Velg byte → Ctrl+Shift+G
```

### Vanlige endringer

| Opprinnelig | Endret | Formål |
|----------|---------|---------|
| `JE` | `JMP` | Hopp alltid |
| `JNE` | `JMP` | Hopp alltid |
| `JE` | `NOP` | Hopp aldri |
| `CALL` | `NOP NOP NOP NOP NOP` | Hopp over kallet |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Eksportere en endret binærfil

```
File → Export Program
Format: Binary
Velg målplassering
```

### NOP-sekvens

```
# Fyll med NOP-instruksjoner
Velg område → høyreklikk → Clear Code Block
Deretter: velg området → høyreklikk → Patch → Fill with NOPs
```

---

## 🐍 Skripting

### Skriptbehandling

```
Window → Script Manager
# Eller: Tools → Script Manager
```

### Kjøre et skript

```
Script Manager → velg skript → Run (grønn avspillingsknapp)
# Eller dobbeltklikk på skriptet
```

### Python-skripting (Ghidra Python)

```python
# Eksempel: List alle funksjoner
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = fremover

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Vanlige skriptoperasjoner

```python
# Hent gjeldende adresse
currentAddress

# Hent gjeldende program
currentProgram

# Hent funksjonsbehandleren
fm = currentProgram.getFunctionManager()

# Hent Listing (disassemblering)
listing = currentProgram.getListing()

# Hent instruksjonen på adressen
instr = listing.getInstructionAt(addr)

# Hent byte på adressen
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Opprett etikett
createLabel(addr, "my_label", True)

# Legg til kommentar
setEOLComment(addr, "My comment")

# Hent referanser
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Nyttige innebygde skript

| Skript | Formål |
|--------|---------|
| FindCrypt | Finn kryptografiske konstanter |
| FunctionID | Gjenkjenn bibliotekfunksjoner |
| ExportFunctionInfoScript | Eksporter funksjonslisten |
| FindStrings | Finn strengreferanser |
| SearchMemory | Søk etter mønstre |

---

<a id="-tastatursnarveier"></a>
## ⌨️ Tastatursnarveier

### Navigasjon

| Hurtigtast | Handling |
|----------|--------|
| `G` | Gå til adresse |
| `Alt+←` | Tilbake |
| `Alt+→` | Fremover |
| `Ctrl+Shift+E` | Startpunkt |
| `Ctrl+E` | Eksporter tabellen |
| `N` | Neste udefinerte område |

### Analyse

| Hurtigtast | Handling |
|----------|--------|
| `F` | Opprett funksjon |
| `D` | Disassembler |
| `C` | Fjern definisjonen |
| `P` | Opprett peker |
| `T` | Bruk datatype |
| `A` | Analyser automatisk |

### Redigering

| Hurtigtast | Handling |
|----------|--------|
| `L` | Gi etiketten eller variabelen nytt navn |
| `;` | Angi kommentar ved linjeslutt |
| `Ctrl+;` | Angi plate-kommentar |
| `Ctrl+L` | Endre variabelens type |
| `Ctrl+Shift+G` | Endre instruksjon |

### Søk

| Hurtigtast | Handling |
|----------|--------|
| `Ctrl+Shift+F` | Finn referanser |
| `Ctrl+F` | Søk etter tekst |
| `S` | Søk i minnet |
| `Ctrl+B` | Søk etter byte |

### Vinduer

| Hurtigtast | Handling |
|----------|--------|
| `Ctrl+D` | Vis eller skjul dekompilatoren |
| `Ctrl+T` | Vis eller skjul datatypebehandleren |
| `Ctrl+S` | Symboltre |

---

## 📊 Hurtigreferanse

### Arbeidsflyt for analyse

```
1. Importer binærfilen → File → Import
2. Analyser automatisk → Analysis → Auto Analyze (godta standardvalg)
3. Finn entry/main → G → main eller entry
4. Undersøk funksjoner → Symbol Tree → Functions
5. Undersøk strenger → Search → For Strings
6. Følg kjøringen → følg kall og hopp
7. Gi symboler navn → L på funksjoner og variabler
8. Dokumenter → legg til kommentarer (;)
```

### Vanlige oppgaver

| Oppgave | Fremgangsmåte |
|------|-----|
| Finn main() | `G` → `main` eller søk i Symbol Tree |
| Vis strenger | Search → For Strings |
| Finn kryptografi | Kjør FindCrypt-skriptet |
| Undersøk importer | Symbol Tree → Imports |
| Finn XREF-er | Velg → Ctrl+Shift+F |
| Opprett funksjon | Velg adresse → `F` |
| Dekompiler | Window → Decompiler |

### Sjekkliste for omvendt utvikling

```
☐ Identifiser filtypen (PE, ELF osv.)
☐ Kjør automatisk analyse
☐ Finn startpunktet / main
☐ Se etter hint i strenger
☐ Identifiser interessante funksjoner
☐ Kartlegg programflyten
☐ Analyser mistenkelige funksjoner
☐ Dokumenter funn med kommentarer
☐ Gi funksjoner og variabler navn
☐ Opprett strukturer ved behov
```

### Kallkonvensjoner for funksjoner

| Konvensjon | Returverdi | Argumenter | Opprydding hos kalleren |
|------------|--------|------|----------------|
| **cdecl** | EAX | Stakk (R→L) | Kaller |
| **stdcall** | EAX | Stakk (R→L) | Kalt funksjon |
| **fastcall** | EAX | ECX, EDX, Stack | Kalt funksjon |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Kaller |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Kaller |

---

## 📚 Ressurser

- [Ghidras offisielle nettsted](https://ghidra-sre.org/)
- [Ghidra på GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Ghidra-dokumentasjon](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Relaterte hurtigreferanser
- [Binwalk](../../Binwalk/translations/README.nb.md)
- [Linux Commands](../../Linux-Commands/translations/README.nb.md)
- [Volatility](../../Volatility/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Lær omvendt utvikling!</b><br>
  <i>Ghidra – NSAs bidrag til sikkerhetsmiljøet</i>
</p>
