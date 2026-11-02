# 🔧 x64dbg – hurtigreferanse for feilsøking i Windows

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Feilsøker for Windows x64/x32
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Skadevare">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er x64dbg](#-hva-er-x64dbg)
- [Installasjon](#-installasjon)
- [Oversikt over grensesnittet](#-oversikt-over-grensesnittet)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Stoppunkter (breakpoints)](#-stoppunkter-breakpoints)
- [Stegvis kjøring og utføring](#-stegvis-kjøring-og-utføring)
- [Minneoperasjoner](#-minneoperasjoner)
- [Registre](#-registre)
- [Stakkanalyse](#-stakkanalyse)
- [Endring av binærkode](#-endring-av-binærkode)
- [Skripting](#-skripting)
- [Tastatursnarveier](#-tastatursnarveier)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er x64dbg

> **Presiseringer:** Menyverdier og hurtigtaster er versjons- og kontekstavhengige; originalen gir motstridende beskrivelser av Ctrl+F7. [`g`](https://help.x64dbg.com/en/latest/commands/debug-control/run.html) er et alias for kjøring; en adresse angir et midlertidig stoppunkt, ikke bare navigasjon. I [`bp`](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html) er andre argument navn og tredje argument type; uttrykkene og tallet 5 i originalen setter derfor ikke dokumenterte betingelser eller treffantall. For [`bph`](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html) betyr `r` lesing og skriving, og 8-byte-størrelsen gjelder bare x64; adressen må være justert. [`bpm`](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetMemoryBPX.html) overvåker hele minneområdet. Endring av ESP/RSP alene utfører ikke en full PUSH/POP. I WinMain-eksemplet tilsvarer `8B EC` bare MOV EBP, ESP; [`find`](https://help.x64dbg.com/en/latest/commands/searching/find.html) gir en virtuell adresse i `$result`, ikke en relativ forskyvning. Løkken identifiserer derfor ikke sikkert WinMain. Endringstabellene forutsetter riktig arkitektur og instruksjonslengde; tillegg krever kompatible utgaver. Originaleksemplene er bevart.

**x64dbg** er en feilsøker med åpen kildekode for Windows x64/x32. Den viderefører mye av OllyDbg-tradisjonen med moderne funksjoner:

- 🔍 **Feilsøking i brukermodus** – feilsøk Windows-applikasjoner
- 🦠 **Skadevareanalyse** – analyser mistenkelige kjørbare filer
- 💥 **Utvikling av exploits** – undersøk Windows-exploits
- 🔧 **Omvendt utvikling** – forstå binærfilers oppførsel
- 🧩 **Støtte for tillegg** – utvid funksjonene med plugins

### Egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **GUI** | Moderne grensesnitt som kan tilpasses |
| **x64-støtte** | Full 64-biters feilsøking |
| **Skripting** | Innebygd skriptspråk |
| **Tillegg** | Omfattende utvalg av tillegg |
| **Symboler** | Støtte for PDB-symboler |
| **Åpen kildekode** | Gratis og under aktiv utvikling |

---

## 🚀 Installasjon

### Last ned

```
# Offisielt nettsted
https://x64dbg.com/

# GitHub-utgivelser
https://github.com/x64dbg/x64dbg/releases

# Last ned: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Oppsett

```
1. Pakk ut ZIP-filen til en fast plassering (f.eks. C:\x64dbg\)
2. Kjør x96dbg.exe (starter begge variantene)
   - x32dbg.exe → 32-biters feilsøking
   - x64dbg.exe → 64-biters feilsøking
3. Legg eventuelt til i PATH eller opprett snarveier
```

### Anbefalte tillegg

| Tillegg | Formål |
|--------|---------|
| **ScyllaHide** | Mottiltak mot anti-debugging |
| **SwissArmyKnife** | Ulike hjelpeverktøy |
| **x64dbgpy** | Python-skripting |
| **TitanHide** | Anti-debug-mottiltak i kjernen |
| **OllyDumpEx** | Uthenting av minne |
| **Multiline Ultimate Assembler** | Endring av flere instruksjonslinjer |

### Installere tillegg

```
1. Last ned tillegget (DLL-fil)
2. Kopier til: x64dbg\release\x32\plugins\ (32-bit)
           x64dbg\release\x64\plugins\ (64-bit)
3. Start x64dbg på nytt
```

---

<a id="-oversikt-over-grensesnittet"></a>
## 🖥️ Oversikt over grensesnittet

### Hovedvinduer

| Vindu | Beskrivelse | Hurtigtast |
|--------|-------------|----------|
| **CPU** | Disassemblert kode | Alt+C |
| **Log** | Feilsøkingsutdata | Alt+L |
| **Memory Map** | Minneområder | Alt+M |
| **Call Stack** | Stakktilbakesporing | Alt+K |
| **Breakpoints** | Alle stoppunkter | Alt+B |
| **Symboler** | Innlastede symboler | Alt+E |
| **Threads** | Trådliste | Alt+T |
| **Handles** | Handle-liste | Alt+H |

### Paneler i CPU-vinduet

Disassembly viser disassemblert kode, Registers viser registre, Hex Dump viser minneinnhold, og Stack viser stakken.

```
┌─────────────────┬──────────────────┐
│ Disassembly     │ Registers        │
│                 │                  │
├─────────────────┼──────────────────┤
│ Hex Dump        │ Stack            │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Statuslinje

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
Modul/adresse     Flagg     Instruksjonspeker   Status
```

---

## 💻 Grunnleggende bruk

### Starte feilsøking

```
# Åpne en kjørbar fil
File → Open (F3)

# Koble til prosess
File → Attach (Alt+A)

# Åpne og kjør
Debug → Run (F9)

# Start feilsøkingen på nytt
Debug → Restart (Ctrl+F2)

# Stopp feilsøkingen
Debug → Stop (Alt+F2)
```

### Kommandolinjen

```
# Skriv kommandoer i kommandolinjen nederst

# Gå til adresse
g 0x401000
goto 0x401000

# Gå til uttrykk
g GetProcAddress
g kernel32.LoadLibraryA

# Søk etter streng
/s "password"

# Søk etter heksadesimale byte
/x 90 90 90 90

# Søk etter mønster
/p "8B ?? 89"
```

### Navigasjon

```
# Gå til adresse
Ctrl+G → skriv inn adressen

# Gå til startpunktet
Debug → Entry Point

# Følg i disassembleren
Høyreklikk → Follow in Disassembler

# Gå tilbake
- (minustasten)

# Gå fremover
+ (plusstasten)
```

---

## 🔴 Stoppunkter (breakpoints)

### Programvarestoppunkter (INT3)

```
# Slå stoppunkt på gjeldende adresse av eller på
F2

# Sett stoppunkt med en kommando
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Sett et betinget stoppunkt
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Stopp når en modul lastes inn
bp $module(kernel32)
```

### Maskinvarestoppunkter

```
# Sett et maskinvarestoppunkt (begrenset til 4)
Høyreklikk → Breakpoint → Hardware → Execute
Høyreklikk → Breakpoint → Hardware → Access
Høyreklikk → Breakpoint → Hardware → Write

# Med kommando
bph 0x401000, x, 1    # Kjøring
bph 0x401000, r, 4    # Lesing (4 byte)
bph 0x401000, w, 4    # Skriving (4 byte)

# Størrelser: 1, 2, 4 eller 8 byte
```

### Minnestoppunkter

```
# Stopp ved minnetilgang
Høyreklikk i minnevisningen → Breakpoint → Memory → Access
Høyreklikk i minnevisningen → Breakpoint → Memory → Write

# Med kommando
bpm 0x401000, r       # Lesing
bpm 0x401000, w       # Skriving
bpm 0x401000, x       # Kjøring
```

### Betingede stoppunkter

```
# Stopp hvis betingelsen er sann
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Stopp ved et bestemt antall treff
bp 0x401000, "", 5    # Stopp ved det 5. treffet

# Logg uten å stoppe
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Administrere stoppunkter

```
# List alle stoppunkter
Breakpoints-vinduet (Alt+B)

# Slett stoppunkt
bc 0x401000          # Slett det angitte
bc                   # Slett alle

# Aktiver/deaktiver
bd 0x401000          # Deaktiver
be 0x401000          # Aktiver

# Deaktiver alle
bd *

# Aktiver alle
be *
```

---

<a id="-stegvis-kjøring-og-utføring"></a>
## ▶️ Stegvis kjøring og utføring

### Stegvis kjøring

| Handling | Hurtigtast | Beskrivelse |
|--------|----------|-------------|
| Step Into | F7 | Utfør én instruksjon |
| Step Over | F8 | Gå over kall |
| Step Out | Ctrl+F9 | Kjør til retur |
| Run to Selection | F4 | Kjør til markøren |
| Run to User Code | Alt+F9 | Gå ut av systemkode |

### Kjøring

| Handling | Hurtigtast | Beskrivelse |
|--------|----------|-------------|
| Kjør | F9 | Fortsett kjøringen |
| Run Skipping | Ctrl+F7 | Hopp over gjeldende instruksjon |
| Sett på pause | F12 | Stopp kjøringen midlertidig |
| Start på nytt | Ctrl+F2 | Start feilsøkingen på nytt |
| Lukk | Alt+F2 | Stopp feilsøkingen |

### Sporing

```
# Spor inn i kall (logg alle instruksjoner)
Debug → Trace Into (Ctrl+F7)

# Spor over kall
Debug → Trace Over (Ctrl+F8)

# Sporingsopptak
Debug → Trace Record

# Stopp sporingen
Debug → Stop Trace
```

### Hoppe over en instruksjon

```
# Hopp over gjeldende instruksjon uten å utføre den
Høyreklikk → Set New Origin Here

# Eller
Ctrl+Shift+O
```

---

## 🔍 Minneoperasjoner

### Minnevisning (dump)

```
# Følg adressen i minnevisningen
Høyreklikk → Follow in Dump → Address/Selection

# Gå til adressen i minnevisningen
Ctrl+G (i minnevisningen)

# Endre visningstypen
Høyreklikk → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Minnekart

```
# Åpne Memory Map
View → Memory Map (Alt+M)

# Viser alle minneområder:
- Address (adresse)
- Size (størrelse)
- Section (seksjon)
- Type (Image/Private/Mapped: image, privat eller tilordnet)
- Protection (Execute/Read/Write: kjøring, lesing eller skriving)
- Module name (modulnavn)
```

### Søke i minnet

```
# Søk i kommandolinjen
/s "string"           # ASCII-streng
/s L"string"          # Unicode-streng
/x 90 90 90           # Heksadesimale byte
/p "8B ?? 89"         # Mønster med jokertegn

# Finn referanser
Høyreklikk → Find references to → Selected Address

# Finn strenger
Høyreklikk → Search for → All Strings
```

### Minnebeskyttelse

```
# Vis minnebeskyttelsen
Memory Map → kolonnen Protection

# Vanlige beskyttelsesverdier
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Hente ut minne

```
# Lagre minnet til en fil
Høyreklikk i Memory Map → Dump Memory to File

# Hent ut minne med tillegg
Plugins → OllyDumpEx → Dump
```

---

## 📊 Registre

### Registerpanelet

```
# Generelle registre (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Generelle registre (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Segmentregistre
CS, DS, SS, ES, FS, GS

# Flagg (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Endre registre

```
# Dobbeltklikk på registeret for å endre det
# Eller høyreklikk → Modify Value

# Via kommandolinjen
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Sett Zero Flag
```

### Viktige flagg

| Flagg | Navn | Settes når |
|------|------|----------|
| ZF | Zero Flag | Resultatet er null |
| CF | Carry Flag | Overløp uten fortegn |
| SF | Sign Flag | Resultatet er negativt |
| OF | Overflow Flag | Overløp med fortegn |
| DF | Direction Flag | Retning for strengoperasjoner |
| TF | Trap Flag | Enkeltsteg |

### Slå flagg av og på

```
# Klikk på flaggbokstaven i registerpanelet
# Eller bruk kommandoer:
r zf=!zf         # Bytt tilstand på Zero Flag
r cf=0           # Nullstill Carry Flag
r sf=1           # Sett Sign Flag
```

---

## 📚 Stakkanalyse

### Stakkpanelet

```
# Viser stakkinnholdet med:
- Address (adresse)
- Value (verdi)
- Comments (kommentarer, hvis tilgjengelig)

# Valg ved høyreklikk:
- Follow DWORD in Disassembler (følg DWORD i disassembleren)
- Follow DWORD in Dump (følg DWORD i minnevisningen)
- Modify (endre)
```

### Kallstakk

```
# Åpne Call Stack
View → Call Stack (Alt+K)

# Viser:
- Return Address (returadresse)
- From (kaller)
- To (kalt funksjon)
- Size (størrelse)
- Comment (kommentar)
```

### Stakkoperasjoner

```
# Gå til en adresse på stakken
Høyreklikk → Follow DWORD in Stack

# Endre en stakkverdi
Høyreklikk → Modify → Edit

# Simulering av push/pop
r esp=esp-4      # Push (32-bit): justerer bare stakkpekeren her
r esp=esp+4      # Pop (32-bit): justerer bare stakkpekeren her
r rsp=rsp-8      # Push (64-bit): justerer bare stakkpekeren her
r rsp=rsp+8      # Pop (64-bit): justerer bare stakkpekeren her
```

### Stakkoppsett (32-bit stdcall)

```
[ESP]      → Returadresse
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

Etter PUSH EBP; MOV EBP, ESP:
[EBP]      → Lagret EBP
[EBP+4]    → Returadresse
[EBP+8]    → Arg1
[EBP-4]    → Local1 (lokal variabel)
```

---

## 🔧 Endring av binærkode

### Assemblere instruksjoner

```
# Høyreklikk på instruksjonen → Assemble (Space)
# Skriv inn nye instruksjoner

# Eksempler:
NOP                    # Ingen operasjon
JMP 0x401020           # Hopp
MOV EAX, 0             # Nullstill EAX
XOR EAX, EAX           # Nullstill EAX (kortere)
RET                    # Returner fra funksjonen
```

### Fyll med NOP-instruksjoner

```
# Velg område → høyreklikk → Binary → Fill with NOPs

# Eller velg området og trykk Space:
# Skriv flere NOP-instruksjoner
```

### Redigere byte

```
# Rediger byte direkte
Høyreklikk → Binary → Edit

# Fyll med en verdi
Høyreklikk → Binary → Fill

# Kopier eller lim inn binærdata
Høyreklikk → Binary → Copy
Høyreklikk → Binary → Paste
```

### Vanlige endringer

| Før | Etter | Formål |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Snu betingelsen |
| `75 XX` (JNE) | `74 XX` (JE) | Snu betingelsen |
| `74 XX` (JE) | `EB XX` (JMP) | Hopp alltid |
| `74 XX` (JE) | `90 90` (NOP) | Hopp aldri |
| `0F 84` (JE) | `0F 85` (JNE) | Snu betingelsen (near) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Hopp over kallet |

### Lagre endringer

```
# Metode 1: Patch File
File → Patch File

# Metode 2: Eksporter endringer
Høyreklikk → Copy → Selection
# Lim inn i en hex-editor

# Metode 3: Tillegg
Plugins → Export modified executable (eksporter den endrede programfilen)
```

---

## 📝 Skripting

### Kommandosyntaks

```
# Variabler
$var = 0x401000

# Sammenligning
cmp eax, 0

# Betingelse
je label
jne label

# Etiketter
label:

# Kommentarer
; Dette er en kommentar
```

### Grunnleggende skript

```
; Skript for å finne WinMain
mov $addr, 0x401000        ; Startadresse
loop:
  find $addr, "8B EC"       ; Finn PUSH EBP; MOV EBP, ESP
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

### Skriptkommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `msg "text"` | Vis melding |
| `log "text"` | Logg til konsollen |
| `find addr, "pattern"` | Søk etter mønster |
| `bp addr` | Sett stoppunkt |
| `bc addr` | Fjern stoppunkt |
| `sti` | Gå inn |
| `sto` | Gå over |
| `run` | Kjør |
| `ret` | Returner/stopp |

### Kjøre et skript

```
# Last inn og kjør skript
Scripts → Run Script

# Eller kommandolinjen
scr "scriptfile.txt"
```

---

<a id="-tastatursnarveier"></a>
## ⌨️ Tastatursnarveier

### Generelt

| Hurtigtast | Handling |
|----------|--------|
| F3 | Åpne fil |
| Alt+A | Koble til prosess |
| Ctrl+F2 | Start på nytt |
| Alt+F2 | Stopp feilsøkingen |
| Ctrl+G | Gå til adresse |
| Ctrl+F | Finn |

### Feilsøking

| Hurtigtast | Handling |
|----------|--------|
| F2 | Slå stoppunkt av eller på |
| F4 | Kjør til det valgte |
| F7 | Gå inn |
| F8 | Gå over |
| F9 | Kjør |
| F12 | Sett på pause |
| Ctrl+F9 | Kjør til retur |

### Navigasjon

| Hurtigtast | Handling |
|----------|--------|
| - | Gå tilbake |
| + | Gå fremover |
| * | Gå til EIP/RIP |
| Enter | Følg kall eller hopp |
| Escape | Gå tilbake |

### Visninger

| Hurtigtast | Handling |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Minnekart |
| Alt+K | Kallstakk |
| Alt+B | Stoppunkter (breakpoints) |
| Alt+E | Symbols |
| Alt+T | Tråder |

### Redigering

| Hurtigtast | Handling |
|----------|--------|
| Space | Assembler |
| ; | Legg til kommentar |
| : | Legg til etikett |
| N | Gi nytt navn |

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `bp addr` | Sett stoppunkt |
| `bc addr` | Fjern stoppunkt |
| `bph addr, x` | Maskinvarebasert stoppunkt |
| `g addr` | Gå til adresse |
| `r reg=val` | Angi registerverdi |
| `sti` | Gå inn |
| `sto` | Gå over |
| `run` | Kjør |

### Vanlige mønstre

| Byte | Instruksjon |
|-------|-------------|
| `90` | NOP |
| `CC` | INT3 (stoppunkt) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

### Arbeidsflyt for analyse

```
1. Last inn kjørbar fil → F3
2. Sett stoppunkt ved startpunktet → F2 ved startpunktet
3. Kjør til stoppunktet → F9
4. Gå stegvis gjennom koden → F7/F8
5. Undersøk registre og minne → panelene
6. Sett flere stoppunkter
7. Fortsett analysen
8. Endre kode ved behov → Space
9. Lagre den endrede filen
```

### Omgåelse av anti-debugging

```
# Installer ScyllaHide-tillegget
Plugins → ScyllaHide → Options → Select profile

# Manuelle endringer:
- Erstatt IsDebuggerPresent-kall med NOP
- Endre PEB.BeingDebugged
- Håndter unntak riktig
```

---

## 📚 Ressurser

- [x64dbgs offisielle nettsted](https://x64dbg.com/)
- [x64dbg på GitHub](https://github.com/x64dbg/x64dbg)
- [x64dbg-dokumentasjon](https://help.x64dbg.com/)
- [x64dbg-tillegg](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Relaterte hurtigreferanser
- [Ghidra](../../Ghidra/translations/README.nb.md)
- [GDB](../../GDB/translations/README.nb.md)
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🔧 Lær feilsøking i Windows!</b><br>
  <i>x64dbg – en moderne etterfølger til OllyDbg</i>
</p>
