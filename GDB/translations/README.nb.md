# 🐛 GDB – hurtigreferanse for GNU Debugger

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

## 📋 Innholdsfortegnelse

- [Hva er GDB](#-hva-er-gdb)
- [Installasjon og oppsett](#-installasjon-og-oppsett)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Stoppunkter (breakpoints)](#-stoppunkter-breakpoints)
- [Kontroll over kjøringen](#-kontroll-over-kjøringen)
- [Undersøke minnet](#-undersøke-minnet)
- [Registre](#-registre)
- [Stakkanalyse](#-stakkanalyse)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Utvikling av exploits](#-utvikling-av-exploits)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er GDB

> **Presiseringer:** `gdb attach <PID>` er ikke generell syntaks for tilkobling fra shellet; originalens `gdb -p <PID>` og den interne `attach`-kommandoen er dokumenterte former ([filvalg](https://sourceware.org/gdb/current/onlinedocs/gdb.html/File-Options.html)). `continue 5` ignorerer videre treff på stoppunktet der programmet stoppet, ikke fem vilkårlige stoppunkter ([kjøringskontroll](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Continuing-and-Stepping.html)). `x/10x` gjenbruker siste enhetsstørrelse; det betyr ikke alltid 32-biters ord ([minneundersøkelse](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html)). Stakkforskyvningene med EBP/RBP forutsetter en tilsvarende stakkramme og ABI. GEF-, PEDA- og pwndbg-kommandoer er tilleggsspesifikke og versjonsavhengige. Offset-eksemplene er ufullstendige: en rekke A-tegn er ikke det genererte sykliske mønsteret, og Python 3-utskriften av bytes er ikke automatisk rå mønsterdata i input.txt. Originaleksemplene er bevart.

**GDB** (GNU Debugger) er en standardfeilsøker for Linux. Den er nyttig til:

- 🐛 **Feilsøking** – finn og rett feil
- 🔍 **Omvendt utvikling** – forstå hvordan binærfiler oppfører seg
- 💥 **Utvikling av exploits** – bufferoverløp og ROP
- 📊 **Binæranalyse** – undersøk kjøringsflyten
- 🧪 **CTF-oppgaver** – løs RE-/pwn-oppgaver

### Støttede arkitekturer

| Arkitektur | Merknader |
|--------------|-------|
| x86 | 32-bit Intel |
| x86-64 | 64-bit Intel/AMD |
| ARM | 32-bit ARM |
| ARM64 | 64-bit ARM |
| MIPS | MIPS-prosessorer |
| PowerPC | PPC-prosessorer |

---

## 🚀 Installasjon og oppsett

### Installer GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (bruk eventuelt lldb i stedet)
brew install gdb
```

### Installer GEF (GDB Enhanced Features)

```bash
# GEF – anbefalt i originalen
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Eller manuelt
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Installer PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Installer pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Kontroller installasjonen

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Grunnleggende bruk

### Starte GDB

```bash
# Feilsøk en kjørbar fil
gdb ./program

# Feilsøk med argumenter
gdb --args ./program arg1 arg2

# Koble til en kjørende prosess
gdb -p <PID>
gdb attach <PID>

# Stille modus (uten banner)
gdb -q ./program

# Utfør kommandoer fra en fil
gdb -x commands.gdb ./program
```

### Grunnleggende kommandoer

| Kommando | Kortform | Beskrivelse |
|---------|-------|-------------|
| `run` | `r` | Start programmet |
| `run args` | `r args` | Kjør med argumenter |
| `continue` | `c` | Fortsett kjøringen |
| `quit` | `q` | Avslutt GDB |
| `help` | `h` | Vis hjelp |
| `help <cmd>` | `h <cmd>` | Hjelp til en kommando |

### Filkommandoer

```bash
# Last inn en kjørbar fil
(gdb) file ./program

# Last inn symboler
(gdb) symbol-file ./program.debug

# Last inn en kjernedump
(gdb) core-file core

# Angi arbeidskatalog
(gdb) cd /path/to/dir

# Angi argumenter
(gdb) set args arg1 arg2
```

---

## 🔴 Stoppunkter (breakpoints)

### Sette stoppunkter

```bash
# Stopp ved en funksjon
(gdb) break main
(gdb) b main

# Stopp på en adresse
(gdb) break *0x08048456
(gdb) b *0x08048456

# Stopp på en linje
(gdb) break file.c:25
(gdb) b 25

# Stopp ved en forskyvning fra funksjonen
(gdb) break *main+48

# Midlertidig stoppunkt (slettes automatisk)
(gdb) tbreak main
```

### Betingede stoppunkter

```bash
# Stopp når betingelsen er sann
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Legg en betingelse til et eksisterende stoppunkt
(gdb) condition 1 $eax == 0

# Fjern betingelsen
(gdb) condition 1
```

### Administrere stoppunkter

```bash
# List stoppunkter
(gdb) info breakpoints
(gdb) info b

# Slett stoppunkt
(gdb) delete 1
(gdb) d 1

# Slett alle stoppunkter
(gdb) delete
(gdb) d

# Deaktiver stoppunkt
(gdb) disable 1
(gdb) dis 1

# Aktiver stoppunkt
(gdb) enable 1
(gdb) en 1

# Deaktiver alle
(gdb) disable

# Aktiver alle
(gdb) enable
```

### Overvåkingspunkter (datastoppunkter)

```bash
# Overvåk endringer i en variabel
(gdb) watch variable
(gdb) watch *0x08048456

# Overvåk lesing
(gdb) rwatch variable

# Overvåk lesing eller skriving
(gdb) awatch variable

# Overvåk et uttrykk
(gdb) watch ($eax > 10)
```

### Hendelsesstoppunkter (catchpoints)

```bash
# Stopp ved systemkall
(gdb) catch syscall

# Stopp ved et bestemt systemkall
(gdb) catch syscall write

# Stopp ved signaler
(gdb) catch signal SIGSEGV

# Stopp ved unntak
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-kontroll-over-kjøringen"></a>
## ▶️ Kontroll over kjøringen

### Stegvis kjøring

```bash
# Utfør én instruksjon (gå inn i kall)
(gdb) stepi
(gdb) si

# Utfør én linje (gå inn i kall)
(gdb) step
(gdb) s

# Gå over (ikke gå inn i kall)
(gdb) nexti
(gdb) ni

# Gå over én linje
(gdb) next
(gdb) n

# Fortsett til funksjonen returnerer
(gdb) finish
(gdb) fin

# Fortsett til en adresse
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Kjøring

```bash
# Start eller start programmet på nytt
(gdb) run
(gdb) r

# Fortsett etter et stoppunkt
(gdb) continue
(gdb) c

# Kjør til neste stoppunkt
(gdb) continue

# Hopp over N stoppunktreff
(gdb) continue 5

# Hopp til en adresse (endrer programflyten!)
(gdb) jump *0x08048456
```

### Prosesskontroll

```bash
# Avslutt gjeldende prosess
(gdb) kill

# Koble fra prosessen
(gdb) detach

# Koble til prosess
(gdb) attach <PID>

# Følg fork (foreldre-/barnprosess)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Undersøke minnet

### Print-kommandoen

```bash
# Skriv ut variabelen
(gdb) print variable
(gdb) p variable

# Skriv ut heksadesimalt
(gdb) print/x variable
(gdb) p/x $eax

# Skriv ut binært
(gdb) p/t variable

# Skriv ut desimalt
(gdb) p/d variable

# Skriv ut som tegn
(gdb) p/c $al

# Skriv ut som streng
(gdb) p/s pointer

# Skriv ut en tabell (array)
(gdb) p *array@10     # 10 elementer
```

### Examine-kommandoen (x)

```bash
# Syntaks: x/NFU address
# N = antall, F = format, U = enhetsstørrelse

# Formater: x(heksadesimal), d(desimal), u(uten fortegn), o(oktal), 
#          t(binær), c(tegn), s(streng), i(instruksjon)
# Enheter: b(byte), h(half=2), w(word=4), g(giant=8)

# Undersøk minnet
(gdb) x/10x $esp           # 10 heksadesimale ord fra ESP
(gdb) x/20wx 0x08048456    # 20 ord heksadesimalt
(gdb) x/s 0x08048456       # Streng på adressen
(gdb) x/10i $eip           # 10 instruksjoner fra EIP
(gdb) x/10gx $rsp          # 10 enheter på 8 byte fra RSP

# Vanlige mønstre
(gdb) x/100x $esp          # Undersøk stakken
(gdb) x/20i $eip           # Disassembler
(gdb) x/s $eax             # Strengpeker
```

### Minneområder

```bash
# Vis minnekartet
(gdb) info proc mappings

# Vis seksjoner
(gdb) maintenance info sections

# Vis filer
(gdb) info files
```

### Disassemblering

```bash
# Disassembler en funksjon
(gdb) disassemble main
(gdb) disas main

# Disassembler gjeldende funksjon
(gdb) disassemble
(gdb) disas

# Disassembler et adresseområde
(gdb) disas 0x08048456, 0x08048500

# Disassembler med kildekode (hvis tilgjengelig)
(gdb) disas /m main

# Angi syntaks
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Registre

### Vise registre

```bash
# Alle registre
(gdb) info registers
(gdb) i r

# Bestemt register
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Alle registre, inkludert FP/SSE
(gdb) info all-registers
```

### Endre registre

```bash
# Angi registerverdi
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Angi flagg
(gdb) set $eflags = 0x246
```

### Referanse for x86-registre

| Register | 64-bit | 32-bit | 16-bit | 8-bit | Formål |
|----------|--------|--------|--------|-------|---------|
| Akkumulator | RAX | EAX | AX | AL/AH | Returverdi |
| Basis | RBX | EBX | BX | BL/BH | Generelt |
| Teller | RCX | ECX | CX | CL/CH | Løkketeller |
| Data | RDX | EDX | DX | DL/DH | Inn/ut og multiplikasjon |
| Kildeindeks | RSI | ESI | SI | SIL | Strengkilde |
| Destinasjonsindeks | RDI | EDI | DI | DIL | Strengdestinasjon |
| Basepeker | RBP | EBP | BP | BPL | Stakkramme |
| Stakkpeker | RSP | ESP | SP | SPL | Toppen av stakken |
| Instruksjon | RIP | EIP | IP | - | Neste instruksjon |

### Flere x64-registre

```
R8-R15 : Generelle registre
R8D-R15D : Nedre 32-bitshalvdeler
R8W-R15W : Nedre 16-bitshalvdeler
R8B-R15B : Nedre 8-bitshalvdeler
```

---

## 📚 Stakkanalyse

### Vise stakken

```bash
# Stakktilbakesporing
(gdb) backtrace
(gdb) bt

# Full tilbakesporing med lokale variabler
(gdb) backtrace full
(gdb) bt full

# Vis stakkrammen
(gdb) frame
(gdb) f

# Velg stakkramme
(gdb) frame 2
(gdb) f 2

# Vis innholdet i stakken
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Stakkramme

```bash
# Vis informasjon om stakkrammen
(gdb) info frame
(gdb) i f

# Vis lokale variabler
(gdb) info locals

# Vis argumenter
(gdb) info args

# Flytt opp eller ned mellom stakkrammer
(gdb) up
(gdb) down
```

### Stakkkommandoer

```bash
# Undersøk returadressen
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Undersøk lagret EBP
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Vis stakkoppsettet
(gdb) x/20wx $esp-20  # Før og etter
```

---

## 🎨 GEF/PEDA/pwndbg

### GEF-kommandoer

```bash
# Kontekstvisning (automatisk)
gef➤ context

# Checksec (sikkerhetsmekanismer)
gef➤ checksec

# Søk etter mønster
gef➤ pattern create 200
gef➤ pattern search $rsp

# Finn streng eller verdi
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Analyse av heapen
gef➤ heap chunks
gef➤ heap bins

# ROP-gadgets
gef➤ ropgadget

# Hjelper for formatstrenger
gef➤ format-string-helper

# VMMap (minnekart)
gef➤ vmmap

# Følg en kjede av pekere
gef➤ dereference $rsp 20

# Telescope (utvidet pekeroppslag)
gef➤ telescope $rsp 20

# ELF-informasjon
gef➤ elf-info

# Canary-verdi
gef➤ canary

# PIE-/ASLR-informasjon
gef➤ pie
gef➤ aslr
```

### PEDA-kommandoer

```bash
# Sikkerhetskontroll
peda➤ checksec

# Finn streng
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Opprett eller søk etter mønster
peda➤ pattern create 200
peda➤ pattern offset $eip

# ROP-gadgets
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Kontekst
peda➤ context all

# Assembly
peda➤ assemble
```

### pwndbg-kommandoer

```bash
# Kontekst
pwndbg> context

# Checksec
pwndbg> checksec

# Søk
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Syklisk mønster
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

## 💥 Utvikling av exploits

### Finne forskyvningen (offset)

```bash
# Metode 1: Mønster (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Metode 2: Mønster (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Grunnleggende bufferoverløp

```bash
# Kontroller ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=av, 1=delvis, 2=full

# Deaktiver ASLR (til testing)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Kontroller binærfilens sikkerhetsmekanismer
gef➤ checksec

# Finn plasseringen til returadressen
(gdb) x/20wx $esp     # Før krasjet
(gdb) i f             # Informasjon om stakkrammen
```

### Finne gadgets

```bash
# Finn "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Finn adressen til system()
(gdb) p system
(gdb) p &system

# Finn en pop rdi-gadget (64-bit)
gef➤ ropgadget --grep "pop rdi"

# Finn pop; ret (32-bit)
gef➤ ropgadget --grep "pop"
```

### Skrive til minnet

```bash
# Angi minneverdi
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Skriv en streng
(gdb) set {char[10]}0x08048456 = "AAAA"

# Kall en funksjon
(gdb) call system("/bin/sh")
```

### Nyttig ved exploit-utvikling

```bash
# Følg barnprosesser
(gdb) set follow-fork-mode child

# Deaktiver ASLR i GDB
(gdb) set disable-randomization on

# Vis delte biblioteker
(gdb) info sharedlibrary

# Finn basisadressen til libc
gef➤ vmmap libc

# Finn PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `r` | Kjør programmet |
| `c` | Fortsett |
| `b *addr` | Sett stoppunkt |
| `si` | Gå én instruksjon frem og inn i kall |
| `ni` | Gå én instruksjon frem uten å gå inn i kall |
| `fin` | Fullfør funksjonen |
| `x/Nx addr` | Undersøk minnet |
| `p $reg` | Skriv ut registeret |
| `i r` | Registerinformasjon |
| `bt` | Tilbakesporing |
| `disas` | Disassembler |
| `q` | Avslutt |

### Minneundersøkelse

| Kommando | Beskrivelse |
|---------|-------------|
| `x/10x $esp` | 10 heksadesimale ord fra ESP |
| `x/s addr` | Streng på adressen |
| `x/i $eip` | Instruksjonen ved EIP |
| `x/20wx addr` | 20 ord heksadesimalt |
| `x/10gx $rsp` | 10 qword-enheter fra RSP |

### Rask feilsøking

```bash
# Start og stopp ved main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Undersøk krasjet
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Arbeidsflyt for exploit-utvikling

```
1. checksec           → kontroller sikkerhetsmekanismer
2. pattern create     → opprett mønster
3. Kjør med mønster → få programmet til å krasje
4. pattern search     → finn forskyvningen
5. Finn gadgets → ROP-gadgets
6. Lag payload → opprett exploit
7. Test → feilsøk med GDB
```

---

## 📚 Ressurser

- [Offisiell GDB-manual](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF-dokumentasjon](https://hugsy.github.io/gef/)
- [pwndbg-dokumentasjon](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Relaterte hurtigreferanser
- [Ghidra](../../Ghidra/translations/README.nb.md)
- [Linux Commands](../../Linux-Commands/translations/README.nb.md)
- [Rettighetseskalering i Linux](../../Linux-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🐛 Lær feilsøking i Linux!</b><br>
  <i>GDB – et viktig verktøy for omvendt utvikling i Linux</i>
</p>
