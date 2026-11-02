# 🔧 x64dbg - Scheda di riferimento per il debugger Windows

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Debugger Windows x64/x32
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

> **Nota sulla sintassi:** menu, scorciatoie e plugin dipendono dalla versione. La tabella originale associa Ctrl+F7 sia a “Run Skipping” sia a “Trace Into”: non sono azioni equivalenti. Nel comando [SetBPX/bp](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html), il secondo argomento è il nome del breakpoint, non una condizione; le condizioni hanno una [funzione documentata separata](https://help.x64dbg.com/en/latest/commands/conditional-breakpoint-control/SetBreakpointCondition.html). Perciò gli esempi `bp` con espressioni e conteggi non vanno interpretati come sintassi generale corretta. Modificare soltanto ESP/RSP non simula completamente push/pop. Il frammento che cerca `8B EC` non identifica univocamente WinMain; numeri, etichette, indirizzi, stringhe e codice sono conservati come nell’originale. I nomi dei controlli e i percorsi dei menu restano in inglese.

---

## 📋 Indice

- [Che cos'è x64dbg](#-che-cosè-x64dbg)
- [Installazione](#-installazione)
- [Panoramica dell'interfaccia](#-panoramica-dellinterfaccia)
- [Utilizzo di base](#-utilizzo-di-base)
- [Punti di interruzione (breakpoint)](#-punti-di-interruzione-breakpoint)
- [Esecuzione passo passo e controllo](#-esecuzione-passo-passo-e-controllo)
- [Operazioni sulla memoria](#-operazioni-sulla-memoria)
- [Registri](#-registri)
- [Analisi dello stack](#-analisi-dello-stack)
- [Applicazione di patch](#-applicazione-di-patch)
- [Scripting](#-scripting)
- [Scorciatoie da tastiera](#-scorciatoie-da-tastiera)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è x64dbg

**x64dbg** è un debugger open source x64/x32 per Windows. Riprende l'approccio di OllyDbg e offre funzionalità moderne:

- 🔍 **Debugging in modalità utente** - Analizza le applicazioni Windows durante l'esecuzione
- 🦠 **Analisi del malware** - Esamina gli eseguibili sospetti
- 💥 **Sviluppo di exploit** - Ricerca sugli exploit per Windows
- 🔧 **Reverse engineering** - Comprendere il comportamento dei binari
- 🧩 **Supporto per i plugin** - Funzionalità estendibili tramite plugin

### Funzionalità

| Funzionalità | Descrizione |
|---------|-------------|
| **GUI** | Interfaccia moderna e personalizzabile |
| **Supporto x64** | Debugging completo a 64 bit |
| **Scripting** | Linguaggio di scripting integrato |
| **Plugins** | Ampio ecosistema di plugin |
| **Symbols** | Supporto per i simboli PDB |
| **Open Source** | Gratuito e sviluppato attivamente |

---

## 🚀 Installazione

### Download

```
# Sito ufficiale
https://x64dbg.com/

# Release GitHub
https://github.com/x64dbg/x64dbg/releases

# Scarica: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Configurazione

```
1. Estrai lo ZIP in una posizione permanente (per esempio C:\x64dbg\)
2. Avvia x96dbg.exe (launcher per entrambe le versioni)
   - x32dbg.exe → Debugging a 32 bit
   - x64dbg.exe → Debugging a 64 bit
3. (Facoltativo) Aggiungi a PATH oppure crea collegamenti
```

### Plugin consigliati

| Plugin | Scopo |
|--------|---------|
| **ScyllaHide** | Contrasto alle tecniche anti-debug |
| **SwissArmyKnife** | Utilità varie |
| **x64dbgpy** | Scripting Python |
| **TitanHide** | Contrasto alle tecniche anti-debug a livello kernel |
| **OllyDumpEx** | Estrazione della memoria |
| **Multiline Ultimate Assembler** | Modifica di più righe |

### Installazione dei plugin

```
1. Scarica il plugin (file DLL)
2. Copia in: x64dbg\release\x32\plugins\ (32 bit)
           x64dbg\release\x64\plugins\ (64 bit)
3. Riavvia x64dbg
```

---

## 🖥 Panoramica dell'interfaccia

### Finestre principali

| Finestra | Descrizione | Scorciatoia |
|--------|-------------|----------|
| **CPU** | Vista del disassemblato | Alt+C |
| **Log** | Output del debugger | Alt+L |
| **Memory Map** | Regioni di memoria | Alt+M |
| **Call Stack** | Traccia delle chiamate nello stack | Alt+K |
| **Breakpoints** | Tutti i breakpoint | Alt+B |
| **Symbols** | Simboli caricati | Alt+E |
| **Threads** | Elenco dei thread | Alt+T |
| **Handles** | Elenco degli handle | Alt+H |

### Pannelli della finestra CPU

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Barra di stato

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

## 💻 Utilizzo di base

### Avvio del debugging

```
# Apri l'eseguibile
File → Open (F3)

# Collegati al processo
File → Attach (Alt+A)

# Apri ed esegui
Debug → Run (F9)

# Riavvia il debugging
Debug → Restart (Ctrl+F2)

# Termina il debugging
Debug → Stop (Alt+F2)
```

### Barra dei comandi

```
# Inserisci i comandi nella barra inferiore

# Vai all'indirizzo
g 0x401000
goto 0x401000

# Vai all'espressione
g GetProcAddress
g kernel32.LoadLibraryA

# Cerca una stringa
/s "password"

# Cerca byte esadecimali
/x 90 90 90 90

# Cerca una sequenza
/p "8B ?? 89"
```

### Navigazione

```
# Vai all'indirizzo
Ctrl+G → Inserisci l'indirizzo

# Vai al punto di ingresso
Debug → Entry Point

# Segui nel disassemblatore
Clic destro → Follow in Disassembler

# Torna indietro
- (tasto meno)

# Vai avanti
+ (tasto più)
```

---

## 🔴 Punti di interruzione (breakpoint)

### Breakpoint software (INT3)

```
# Attiva o disattiva il breakpoint all'indirizzo corrente
F2

# Imposta il breakpoint tramite comando
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Imposta un breakpoint condizionale
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Interrompi al caricamento del modulo
bp $module(kernel32)
```

### Breakpoint hardware

```
# Imposta un breakpoint hardware (massimo 4)
Clic destro → Breakpoint → Hardware → Execute
Clic destro → Breakpoint → Hardware → Access
Clic destro → Breakpoint → Hardware → Write

# Tramite comando
bph 0x401000, x, 1    # Esegui
bph 0x401000, r, 4    # Lettura (4 byte)
bph 0x401000, w, 4    # Scrittura (4 byte)

# Dimensioni: 1, 2, 4 oppure 8 byte
```

### Breakpoint sulla memoria

```
# Interrompi all'accesso alla memoria
Clic destro nel dump → Breakpoint → Memory → Access
Clic destro nel dump → Breakpoint → Memory → Write

# Tramite comando
bpm 0x401000, r       # Lettura
bpm 0x401000, w       # Scrittura
bpm 0x401000, x       # Esegui
```

### Breakpoint condizionali

```
# Interrompi se la condizione è vera
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Interrompi in base al numero di chiamate
bp 0x401000, "", 5    # Interrompi al quinto passaggio

# Registra un messaggio senza interrompere
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Gestione dei breakpoint

```
# Elenca tutti i breakpoint
Finestra Breakpoints (Alt+B)

# Elimina il breakpoint
bc 0x401000          # Elimina quello specificato
bc                   # Elimina tutti

# Abilita o disabilita
bd 0x401000          # Disabilita
be 0x401000          # Abilita

# Disabilita tutti
bd *

# Abilita tutti
be *
```

---

## ▶ Esecuzione passo passo e controllo

### Esecuzione passo passo

| Azione | Scorciatoia | Descrizione |
|--------|----------|-------------|
| Step Into | F7 | Esegui un'istruzione |
| Step Over | F8 | Esegui senza entrare nelle chiamate |
| Step Out | Ctrl+F9 | Esegui fino al ritorno |
| Run to Selection | F4 | Esegui fino al cursore |
| Run to User Code | Alt+F9 | Esci dal codice di sistema |

### Esecuzione

| Azione | Scorciatoia | Descrizione |
|--------|----------|-------------|
| Run | F9 | Continua l'esecuzione |
| Run Skipping | Ctrl+F7 | Salta l'istruzione corrente |
| Pause | F12 | Interrompi l'esecuzione |
| Restart | Ctrl+F2 | Riavvia il debugging |
| Close | Alt+F2 | Termina il debugging |

### Tracciamento

```
# Traccia entrando nelle chiamate (registra tutte le istruzioni)
Debug → Trace Into (Ctrl+F7)

# Traccia senza entrare nelle chiamate
Debug → Trace Over (Ctrl+F8)

# Registrazione della traccia
Debug → Trace Record

# Interrompi il tracciamento
Debug → Stop Trace
```

### Salto di un'istruzione

```
# Salta l'istruzione corrente senza eseguirla
Clic destro → Set New Origin Here

# Oppure
Ctrl+Shift+O
```

---

## 🔍 Operazioni sulla memoria

### Dump della memoria

```
# Segui l'indirizzo nel dump
Clic destro → Follow in Dump → Address/Selection

# Vai all'indirizzo nella finestra del dump
Ctrl+G (nella finestra del dump)

# Cambia il tipo di visualizzazione del dump
Clic destro → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Memory Map

```
# Apri Memory Map
View → Memory Map (Alt+M)

# Mostra tutte le regioni di memoria:
- Indirizzo
- Dimensione
- Sezione
- Tipo (Image/Private/Mapped)
- Protezione (Execute/Read/Write)
- Nome del modulo
```

### Ricerca nella memoria

```
# Cerca nella barra dei comandi
/s "string"           # Stringa ASCII
/s L"string"          # Stringa Unicode
/x 90 90 90           # Byte esadecimali
/p "8B ?? 89"         # Sequenza con caratteri jolly

# Trova i riferimenti
Clic destro → Find references to → Selected Address

# Individua le stringhe
Clic destro → Search for → All Strings
```

### Protezione della memoria

```
# Visualizza la protezione
Memory Map → Colonna Protection

# Protezioni comuni
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Estrazione della memoria

```
# Estrai la memoria in un file
Clic destro in Memory Map → Dump Memory to File

# Estrai tramite plugin
Plugins → OllyDumpEx → Dump
```

---

## 📊 Registri

### Pannello dei registri

```
# Uso generale (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Uso generale (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Registri di segmento
CS, DS, SS, ES, FS, GS

# Flag (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Modifica dei registri

```
# Fai doppio clic sul registro per modificarlo
# Oppure clic destro → Modify Value

# Tramite la barra dei comandi
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Imposta lo Zero Flag
```

### Flag importanti

| Flag | Nome | Condizione di impostazione |
|------|------|----------|
| ZF | Zero Flag | Il risultato è zero |
| CF | Carry Flag | Overflow senza segno |
| SF | Sign Flag | Il risultato è negativo |
| OF | Overflow Flag | Overflow con segno |
| DF | Direction Flag | Direzione delle operazioni sulle stringhe |
| TF | Trap Flag | Esecuzione di un singolo passo |

### Modifica dei flag

```
# Fai clic sulla lettera del flag nel pannello dei registri
# Oppure usa i comandi:
r zf=!zf         # Inverti lo Zero Flag
r cf=0           # Azzera il Carry Flag
r sf=1           # Imposta il Sign Flag
```

---

## 📚 Analisi dello stack

### Pannello dello stack

```
# Mostra il contenuto dello stack con:
- Indirizzo
- Valore
- Commenti (se disponibili)

# Opzioni del clic destro:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify
```

### Call Stack

```
# Apri Call Stack
View → Call Stack (Alt+K)

# Mostra:
- Indirizzo di ritorno
- From (chiamante)
- To (funzione chiamata)
- Dimensione
- Commento
```

### Operazioni sullo stack

```
# Vai all'indirizzo nello stack
Clic destro → Follow DWORD in Stack

# Modifica il valore nello stack
Clic destro → Modify → Edit

# Simulazione di push e pop
r esp=esp-4      # Push (32 bit)
r esp=esp+4      # Pop (32 bit)
r rsp=rsp-8      # Push (64 bit)
r rsp=rsp+8      # Pop (64 bit)
```

### Disposizione dello stack (stdcall a 32 bit)

```
[ESP]      → Indirizzo di ritorno
[ESP+4]    → Argomento 1
[ESP+8]    → Argomento 2
[ESP+C]    → Argomento 3
...

Dopo PUSH EBP; MOV EBP, ESP:
[EBP]      → EBP salvato
[EBP+4]    → Indirizzo di ritorno
[EBP+8]    → Argomento 1
[EBP-4]    → Variabile locale 1
```

---

## 🔧 Applicazione di patch

### Assemblaggio delle istruzioni

```
# Clic destro sull'istruzione → Assemble (Space)
# Inserisci le nuove istruzioni

# Esempi:
NOP                    # Nessuna operazione
JMP 0x401020           # Salto
MOV EAX, 0             # Azzera EAX
XOR EAX, EAX           # Azzera EAX (più compatto)
RET                    # Ritorno
```

### Riempi con NOP

```
# Seleziona l'intervallo → Clic destro → Binary → Fill with NOPs

# Oppure seleziona e premi Space:
# Inserisci più NOP
```

### Modifica binaria

```
# Modifica direttamente i byte
Clic destro → Binary → Edit

# Riempi con un valore
Clic destro → Binary → Fill

# Copia o incolla dati binari
Clic destro → Binary → Copy
Clic destro → Binary → Paste
```

### Patch comuni

| Prima | Dopo | Scopo |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Inverti la condizione |
| `75 XX` (JNE) | `74 XX` (JE) | Inverti la condizione |
| `74 XX` (JE) | `EB XX` (JMP) | Esegui sempre il salto |
| `74 XX` (JE) | `90 90` (NOP) | Non eseguire il salto |
| `0F 84` (JE) | `0F 85` (JNE) | Inverti (salto near) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Salta la chiamata |

### Salvataggio delle patch

```
# Metodo 1: Patch File
File → Patch File

# Metodo 2: esportazione delle patch
Clic destro → Copy → Selection
# Incolla in un editor esadecimale

# Metodo 3: plugin
Plugins → Export modified executable
```

---

## 📝 Scripting

### Sintassi dei comandi

```
# Variabili
$var = 0x401000

# Confronto
cmp eax, 0

# Condizione
je label
jne label

# Etichette
label:

# Commenti
; Questo è un commento
```

### Script di base

```
; Script per individuare WinMain
mov $addr, 0x401000        ; Indirizzo iniziale
loop:
  find $addr, "8B EC"       ; Cerca PUSH EBP; MOV EBP, ESP
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

### Comandi degli script

| Comando | Descrizione |
|---------|-------------|
| `msg "text"` | Mostra un messaggio |
| `log "text"` | Registra nella console |
| `find addr, "pattern"` | Cerca una sequenza |
| `bp addr` | Imposta un breakpoint |
| `bc addr` | Rimuovi il breakpoint |
| `sti` | Entra nella chiamata |
| `sto` | Esegui senza entrare nella chiamata |
| `run` | Run |
| `ret` | Ritorna o termina |

### Esecuzione di uno script

```
# Carica ed esegui lo script
Scripts → Run Script

# Oppure dalla barra dei comandi
scr "scriptfile.txt"
```

---

## ⌨ Scorciatoie da tastiera

### Generale

| Scorciatoia | Azione |
|----------|--------|
| F3 | Apri il file |
| Alt+A | Collegati al processo |
| Ctrl+F2 | Restart |
| Alt+F2 | Termina il debugging |
| Ctrl+G | Vai all'indirizzo |
| Ctrl+F | Cerca |

### Debugging

| Scorciatoia | Azione |
|----------|--------|
| F2 | Attiva o disattiva il breakpoint |
| F4 | Esegui fino alla selezione |
| F7 | Entra nella chiamata |
| F8 | Esegui senza entrare nella chiamata |
| F9 | Run |
| F12 | Pause |
| Ctrl+F9 | Esegui fino al ritorno |

### Navigazione

| Scorciatoia | Azione |
|----------|--------|
| - | Torna indietro |
| + | Vai avanti |
| * | Vai a EIP/RIP |
| Enter | Segui la chiamata o il salto |
| Escape | Torna indietro |

### Viste

| Scorciatoia | Azione |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Breakpoints |
| Alt+E | Symbols |
| Alt+T | Threads |

### Modifica

| Scorciatoia | Azione |
|----------|--------|
| Space | Assembla |
| ; | Aggiungi un commento |
| : | Aggiungi un'etichetta |
| N | Rinomina |

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `bp addr` | Imposta un breakpoint |
| `bc addr` | Rimuovi il breakpoint |
| `bph addr, x` | Breakpoint hardware |
| `g addr` | Vai all'indirizzo |
| `r reg=val` | Imposta il registro |
| `sti` | Entra nella chiamata |
| `sto` | Esegui senza entrare nella chiamata |
| `run` | Run |

### Sequenze comuni

| Byte | Istruzione |
|-------|-------------|
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

### Procedura di analisi

```
1. Carica l'eseguibile        → F3
2. Imposta il breakpoint iniziale → F2 al punto di ingresso
3. Esegui fino al breakpoint → F9
4. Esegui il codice passo passo → F7/F8
5. Esamina registri e memoria → Pannelli
6. Imposta ulteriori breakpoint
7. Continua l'analisi
8. Applica patch se necessario → Space
9. Salva il file modificato
```

### Aggiramento delle tecniche anti-debug

```
# Installa il plugin ScyllaHide
Plugins → ScyllaHide → Options → Seleziona il profilo

# Patch manuali:
- Sostituisci le chiamate a IsDebuggerPresent con NOP
- Modifica PEB.BeingDebugged
- Gestisci correttamente le eccezioni
```

---

## 📚 Risorse

- [Sito ufficiale x64dbg](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [Documentazione x64dbg](https://help.x64dbg.com/)
- [Plugin x64dbg](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Schede correlate
- [Ghidra](../../Ghidra/translations/README.it.md)
- [GDB](../../GDB/translations/README.it.md)
- [Escalation dei privilegi su Windows](../../Windows-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🔧 Padroneggia il debugging su Windows!</b><br>
  <i>x64dbg - Il successore moderno di OllyDbg</i>
</p>
