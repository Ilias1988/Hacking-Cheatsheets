# 🐛 GDB - Scheda di riferimento per GNU Debugger

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

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

> **Nota sugli esempi:** indirizzi, registri e disposizione dello stack dipendono da architettura, ABI, ottimizzazione e presenza dei frame pointer. I comandi delle estensioni GEF/PEDA/pwndbg dipendono dalla versione e non sono tutti comandi GDB nativi. `gdb attach <PID>` conserva una forma inesatta dell’originale: `attach` è un comando interno al debugger, come indica il [manuale ufficiale](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Attach.html). Negli esempi sugli offset, produrre 200 caratteri `A` non equivale a generare una sequenza ciclica; `print(cyclic(200))` in Python 3 mostra la rappresentazione di un oggetto bytes, non necessariamente i byte grezzi previsti. Le dimensioni omesse nel comando `x` possono dipendere dallo stato precedente; i valori e i frammenti originali restano invariati.

---

## 📋 Indice

- [Che cos'è GDB](#-che-cosè-gdb)
- [Installazione e configurazione](#-installazione-e-configurazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Punti di interruzione (breakpoint)](#-punti-di-interruzione-breakpoint)
- [Controllo dell'esecuzione](#-controllo-dellesecuzione)
- [Esame della memoria](#-esame-della-memoria)
- [Registri](#-registri)
- [Analisi dello stack](#-analisi-dello-stack)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Sviluppo di exploit](#-sviluppo-di-exploit)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è GDB

**GDB** (GNU Debugger) è il debugger standard di Linux. È fondamentale per:

- 🐛 **Debugging** - Individuare e correggere errori
- 🔍 **Reverse engineering** - Comprendere il comportamento dei binari
- 💥 **Sviluppo di exploit** - Buffer overflow e ROP
- 📊 **Analisi dei binari** - Esaminare il flusso di esecuzione
- 🧪 **Sfide CTF** - Risolvere sfide di reverse engineering e pwn

### Architetture supportate

| Architettura | Note |
|--------------|-------|
| x86 | 32-bit Intel |
| x86-64 | 64-bit Intel/AMD |
| ARM | 32-bit ARM |
| ARM64 | 64-bit ARM |
| MIPS | Processori MIPS |
| PowerPC | Processori PPC |

---

## 🚀 Installazione e configurazione

### Installazione di GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (usa invece lldb, oppure)
brew install gdb
```

### Installazione di GEF (GDB Enhanced Features)

```bash
# GEF - La scelta consigliata nell'originale
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Oppure manualmente
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Installazione di PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Installazione di pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Verifica dell'installazione

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Utilizzo di base

### Avvio di GDB

```bash
# Avvia il debugging dell'eseguibile
gdb ./program

# Avvia il debugging con argomenti
gdb --args ./program arg1 arg2

# Collegati a un processo in esecuzione
gdb -p <PID>
gdb attach <PID>

# Modalità silenziosa (senza banner)
gdb -q ./program

# Esegui i comandi da un file
gdb -x commands.gdb ./program
```

### Comandi di base

| Comando | Forma breve | Descrizione |
|---------|-------|-------------|
| `run` | `r` | Avvia il programma |
| `run args` | `r args` | Esegui con argomenti |
| `continue` | `c` | Continua l'esecuzione |
| `quit` | `q` | Esci da GDB |
| `help` | `h` | Mostra la guida |
| `help <cmd>` | `h <cmd>` | Guida sul comando |

### Comandi per i file

```bash
# Carica l'eseguibile
(gdb) file ./program

# Carica i simboli
(gdb) symbol-file ./program.debug

# Carica il core dump
(gdb) core-file core

# Imposta la directory di lavoro
(gdb) cd /path/to/dir

# Imposta gli argomenti
(gdb) set args arg1 arg2
```

---

## 🔴 Punti di interruzione (breakpoint)

### Impostazione dei breakpoint

```bash
# Interrompi nella funzione
(gdb) break main
(gdb) b main

# Interrompi all'indirizzo
(gdb) break *0x08048456
(gdb) b *0x08048456

# Interrompi alla riga
(gdb) break file.c:25
(gdb) b 25

# Interrompi all'offset rispetto alla funzione
(gdb) break *main+48

# Breakpoint temporaneo (eliminazione automatica)
(gdb) tbreak main
```

### Breakpoint condizionali

```bash
# Interrompi quando la condizione è vera
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Aggiungi una condizione al breakpoint esistente
(gdb) condition 1 $eax == 0

# Rimuovi la condizione
(gdb) condition 1
```

### Gestione dei breakpoint

```bash
# Elenca i breakpoint
(gdb) info breakpoints
(gdb) info b

# Elimina il breakpoint
(gdb) delete 1
(gdb) d 1

# Elimina tutti i breakpoint
(gdb) delete
(gdb) d

# Disabilita il breakpoint
(gdb) disable 1
(gdb) dis 1

# Abilita il breakpoint
(gdb) enable 1
(gdb) en 1

# Disabilita tutti
(gdb) disable

# Abilita tutti
(gdb) enable
```

### Watchpoint (breakpoint sui dati)

```bash
# Sorveglia le modifiche alla variabile
(gdb) watch variable
(gdb) watch *0x08048456

# Sorveglia le letture
(gdb) rwatch variable

# Sorveglia letture e scritture
(gdb) awatch variable

# Sorveglia l'espressione
(gdb) watch ($eax > 10)
```

### Punti di intercettazione (catchpoint)

```bash
# Intercetta le chiamate di sistema
(gdb) catch syscall

# Intercetta la chiamata di sistema specificata
(gdb) catch syscall write

# Intercetta i segnali
(gdb) catch signal SIGSEGV

# Intercetta le eccezioni
(gdb) catch throw
(gdb) catch catch
```

---

## ▶ Controllo dell'esecuzione

### Esecuzione passo passo

```bash
# Esegui un'istruzione entrando nelle chiamate
(gdb) stepi
(gdb) si

# Esegui una riga entrando nelle chiamate
(gdb) step
(gdb) s

# Esegui un'istruzione senza entrare nelle chiamate
(gdb) nexti
(gdb) ni

# Esegui una riga senza entrare nelle chiamate
(gdb) next
(gdb) n

# Continua fino al ritorno della funzione
(gdb) finish
(gdb) fin

# Continua fino all'indirizzo
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Esecuzione

```bash
# Avvia o riavvia il programma
(gdb) run
(gdb) r

# Continua dopo il breakpoint
(gdb) continue
(gdb) c

# Esegui fino al prossimo breakpoint
(gdb) continue

# Ignora N interruzioni
(gdb) continue 5

# Salta all'indirizzo (operazione delicata!)
(gdb) jump *0x08048456
```

### Controllo del processo

```bash
# Termina il processo corrente
(gdb) kill

# Scollegati dal processo
(gdb) detach

# Collegati al processo
(gdb) attach <PID>

# Segui la fork (processo padre o figlio)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Esame della memoria

### Comando print

```bash
# Mostra la variabile
(gdb) print variable
(gdb) p variable

# Mostra in esadecimale
(gdb) print/x variable
(gdb) p/x $eax

# Mostra in binario
(gdb) p/t variable

# Mostra in decimale
(gdb) p/d variable

# Mostra come carattere
(gdb) p/c $al

# Mostra come stringa
(gdb) p/s pointer

# Mostra l'array
(gdb) p *array@10     # 10 elementi
```

### Comando di esame della memoria (x)

```bash
# Sintassi: x/NFU address
# N = numero, F = formato, U = dimensione dell'unità

# Formati: x(esadecimale), d(decimale), u(senza segno), o(ottale), 
#          t(binario), c(carattere), s(stringa), i(istruzione)
# Unità: b(byte), h(half=2), w(word=4), g(giant=8)

# Esamina la memoria
(gdb) x/10x $esp           # 10 word esadecimali a partire da ESP
(gdb) x/20wx 0x08048456    # 20 word in esadecimale
(gdb) x/s 0x08048456       # Stringa all'indirizzo
(gdb) x/10i $eip           # 10 istruzioni a partire da EIP
(gdb) x/10gx $rsp          # 10 unità giant (8 byte) a partire da RSP

# Sequenze comuni
(gdb) x/100x $esp          # Esamina lo stack
(gdb) x/20i $eip           # Disassembla
(gdb) x/s $eax             # Puntatore a una stringa
```

### Regioni di memoria

```bash
# Mostra la mappa della memoria
(gdb) info proc mappings

# Mostra le sezioni
(gdb) maintenance info sections

# Mostra i file
(gdb) info files
```

### Disassemblato

```bash
# Disassembla la funzione
(gdb) disassemble main
(gdb) disas main

# Disassembla la funzione corrente
(gdb) disassemble
(gdb) disas

# Disassembla l'intervallo
(gdb) disas 0x08048456, 0x08048500

# Disassembla mostrando il sorgente (se disponibile)
(gdb) disas /m main

# Imposta la sintassi
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Registri

### Visualizzazione dei registri

```bash
# Tutti i registri
(gdb) info registers
(gdb) i r

# Registro specifico
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Tutti i registri, compresi FP/SSE
(gdb) info all-registers
```

### Modifica dei registri

```bash
# Imposta il valore del registro
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Imposta i flag
(gdb) set $eflags = 0x246
```

### Riferimento dei registri x86

| Registro | 64-bit | 32-bit | 16-bit | 8-bit | Scopo |
|----------|--------|--------|--------|-------|---------|
| Accumulatore | RAX | EAX | AX | AL/AH | Valore restituito |
| Base | RBX | EBX | BX | BL/BH | Generale |
| Contatore | RCX | ECX | CX | CL/CH | Contatore del ciclo |
| Dati | RDX | EDX | DX | DL/DH | I/O e moltiplicazione |
| Indice sorgente | RSI | ESI | SI | SIL | Sorgente della stringa |
| Indice destinazione | RDI | EDI | DI | DIL | Destinazione della stringa |
| Puntatore al frame | RBP | EBP | BP | BPL | Frame dello stack |
| Puntatore allo stack | RSP | ESP | SP | SPL | Cima dello stack |
| Istruzione | RIP | EIP | IP | - | Prossima istruzione |

### Registri aggiuntivi x64

```
R8-R15 : Registri di uso generale
R8D-R15D : Parti basse a 32 bit
R8W-R15W : Parti basse a 16 bit
R8B-R15B : Parti basse a 8 bit
```

---

## 📚 Analisi dello stack

### Visualizzazione dello stack

```bash
# Traccia delle chiamate sullo stack (backtrace)
(gdb) backtrace
(gdb) bt

# Backtrace completo con variabili locali
(gdb) backtrace full
(gdb) bt full

# Mostra il frame
(gdb) frame
(gdb) f

# Seleziona il frame
(gdb) frame 2
(gdb) f 2

# Mostra il contenuto dello stack
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Frame dello stack

```bash
# Mostra le informazioni sul frame
(gdb) info frame
(gdb) i f

# Mostra le variabili locali
(gdb) info locals

# Mostra gli argomenti
(gdb) info args

# Spostati tra i frame verso l'alto o il basso
(gdb) up
(gdb) down
```

### Comandi per lo stack

```bash
# Esamina l'indirizzo di ritorno
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Esamina l'EBP salvato
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Mostra la disposizione dello stack
(gdb) x/20wx $esp-20  # Prima e dopo
```

---

## 🎨 GEF/PEDA/pwndbg

### Comandi GEF

```bash
# Visualizzazione automatica del contesto
gef➤ context

# Checksec (funzionalità di sicurezza)
gef➤ checksec

# Cerca una sequenza
gef➤ pattern create 200
gef➤ pattern search $rsp

# Trova una stringa o un valore
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Analisi dell'heap
gef➤ heap chunks
gef➤ heap bins

# Gadget ROP
gef➤ ropgadget

# Strumento di supporto per le format string
gef➤ format-string-helper

# VMMap (mappa della memoria)
gef➤ vmmap

# Catena di dereferenziazioni
gef➤ dereference $rsp 20

# Telescope (dereferenziazione intelligente)
gef➤ telescope $rsp 20

# Informazioni ELF
gef➤ elf-info

# Valore del canary
gef➤ canary

# Informazioni PIE/ASLR
gef➤ pie
gef➤ aslr
```

### Comandi PEDA

```bash
# Verifica delle protezioni
peda➤ checksec

# Trova una stringa
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Creazione e ricerca di una sequenza
peda➤ pattern create 200
peda➤ pattern offset $eip

# Gadget ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Contesto
peda➤ context all

# Assembly
peda➤ assemble
```

### Comandi pwndbg

```bash
# Contesto
pwndbg> context

# Checksec
pwndbg> checksec

# Ricerca
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Sequenza ciclica
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

## 💥 Sviluppo di exploit

### Individuazione dell'offset

```bash
# Metodo 1: sequenza (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Metodo 2: sequenza (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Fondamenti del buffer overflow

```bash
# Verifica l'ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=disattivata, 1=parziale, 2=completa

# Disabilita l'ASLR (per i test)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Verifica le protezioni del binario
gef➤ checksec

# Individua la posizione dell'indirizzo di ritorno
(gdb) x/20wx $esp     # Prima del crash
(gdb) i f             # Informazioni sul frame
```

### Individuazione dei gadget

```bash
# Trova "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Trova l'indirizzo di system()
(gdb) p system
(gdb) p &system

# Trova il gadget pop rdi (64 bit)
gef➤ ropgadget --grep "pop rdi"

# Trova pop; ret (32 bit)
gef➤ ropgadget --grep "pop"
```

### Scrittura in memoria

```bash
# Imposta il valore in memoria
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Scrivi una stringa
(gdb) set {char[10]}0x08048456 = "AAAA"

# Chiama la funzione
(gdb) call system("/bin/sh")
```

### Funzioni utili per gli exploit

```bash
# Segui i processi figli
(gdb) set follow-fork-mode child

# Disabilita l'ASLR in GDB
(gdb) set disable-randomization on

# Mostra le librerie condivise
(gdb) info sharedlibrary

# Trova l'indirizzo base di libc
gef➤ vmmap libc

# Trova PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `r` | Esegui il programma |
| `c` | Continua |
| `b *addr` | Imposta un breakpoint |
| `si` | Esegui un'istruzione |
| `ni` | Prossima istruzione |
| `fin` | Completa la funzione |
| `x/Nx addr` | Esamina la memoria |
| `p $reg` | Mostra il registro |
| `i r` | Informazioni sui registri |
| `bt` | Backtrace |
| `disas` | Disassembla |
| `q` | Esci |

### Esame della memoria

| Comando | Descrizione |
|---------|-------------|
| `x/10x $esp` | 10 word esadecimali a partire da ESP |
| `x/s addr` | Stringa all'indirizzo |
| `x/i $eip` | Istruzione all'indirizzo EIP |
| `x/20wx addr` | 20 word in esadecimale |
| `x/10gx $rsp` | 10 qword a partire da RSP |

### Debugging rapido

```bash
# Avvia e interrompi in main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Esamina il crash
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Procedura di sviluppo di un exploit

```
1. checksec                 → Verifica le protezioni
2. pattern create           → Crea la sequenza
3. run (con la sequenza)    → Provoca il crash del programma
4. pattern search           → Trova l'offset
5. Trova i gadget           → Gadget ROP
6. Costruisci il payload    → Crea l'exploit
7. test                     → Esegui il debugging con GDB
```

---

## 📚 Risorse

- [Manuale ufficiale GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [Documentazione GEF](https://hugsy.github.io/gef/)
- [Documentazione pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Schede correlate
- [Ghidra](../../Ghidra/translations/README.it.md)
- [Comandi Linux](../../Linux-Commands/translations/README.it.md)
- [Escalation dei privilegi su Linux](../../Linux-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🐛 Padroneggia il debugging su Linux!</b><br>
  <i>GDB - Lo strumento essenziale per il reverse engineering su Linux</i>
</p>
