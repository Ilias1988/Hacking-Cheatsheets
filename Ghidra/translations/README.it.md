# 🔍 Ghidra - Scheda di riferimento per il reverse engineering

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              Suite di reverse engineering della NSA
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

> **Nota sulle versioni:** il requisito JDK 17+ e alcuni percorsi, scorciatoie e script sono riferimenti dell’originale, non requisiti universali delle release attuali. Consulta la [guida ufficiale di avvio](https://github.com/NationalSecurityAgency/ghidra/blob/master/GhidraDocs/GettingStarted.md). Lo script con f-string richiede un ambiente Python 3 compatibile; il supporto CPython 3 è descritto nella sezione PyGhidra della guida. `addr` e `length` negli esempi devono essere definiti dal contesto. Il decompilato è una ricostruzione, non il sorgente originale; le convenzioni di chiamata della tabella sono semplificate. Esportare con formato Binary non garantisce di ottenere un eseguibile equivalente al file importato. Nomi dei menu e controlli sono mantenuti in inglese per riconoscerli nell’interfaccia.

---

## 📋 Indice

- [Che cos'è Ghidra](#-che-cosè-ghidra)
- [Installazione](#-installazione)
- [Panoramica dell'interfaccia](#-panoramica-dellinterfaccia)
- [Creazione di un progetto](#-creazione-di-un-progetto)
- [Fondamenti dell'analisi](#-fondamenti-dellanalisi)
- [Navigazione](#-navigazione)
- [Decompiler](#-decompiler)
- [Tipi di dati e strutture](#-tipi-di-dati-e-strutture)
- [Patch e modifiche](#-patch-e-modifiche)
- [Scripting](#-scripting)
- [Scorciatoie da tastiera](#-scorciatoie-da-tastiera)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Ghidra

**Ghidra** è una suite gratuita e open source per il reverse engineering del software (SRE), sviluppata dalla NSA. Offre:

- 🔍 **Disassembler** - Converte il codice binario in assembly
- 🔄 **Decompilatore** - Genera pseudocodice simile al C
- 📊 **Analisi** - Analisi automatizzata del codice
- 🔧 **Patch** - Modifica dei binari
- 🐍 **Scripting** - Automazione con Python/Java
- 👥 **Collaborazione** - Supporto per più utenti

### Architetture supportate

| Architettura | Varianti |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Formati supportati

| Formato | Descrizione |
|--------|-------------|
| ELF | Eseguibili Linux |
| PE/COFF | Eseguibili Windows |
| Mach-O | Eseguibili macOS |
| Binario grezzo | Firmware, ROM |
| APK/DEX | App Android |
| e molti altri... |

---

## 🚀 Installazione

### Download

```bash
# Scarica dal sito ufficiale
https://ghidra-sre.org/

# Oppure dalle release GitHub
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Requisiti

| Requisito | Versione |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | Minimo 4GB, consigliati 8GB+ |
| **Disco** | 1GB+ per l'installazione |

### Linux/macOS

```bash
# Installa JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Estrai Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Esegui
./ghidraRun
```

### Windows

```batch
:: Installa JDK 17+ da Oracle o Adoptium
:: Estrai ghidra_*.zip
:: Avvia ghidraRun.bat
```

---

## 🖥 Panoramica dell'interfaccia

### Finestre principali

| Finestra | Descrizione |
|--------|-------------|
| **Program Trees** | Struttura dei file del progetto |
| **Symbol Tree** | Funzioni, etichette e classi |
| **Data Type Manager** | Tipi di dati definiti |
| **Listing** | Vista del disassemblato |
| **Decompiler** | Pseudocodice C |
| **Console** | Output e scripting |
| **Defined Strings** | Stringhe individuate |
| **Bookmarks** | Posizioni salvate |

### Finestra Listing (disassemblato)

```
Indirizzo  | Byte           | Disassemblato         | Commenti
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Inizio funzione
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Frame dello stack
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Creazione di un progetto

### Nuovo progetto

```
1. File → New Project
2. Scegli:
   - Non-Shared Project (locale)
   - Shared Project (collaborazione)
3. Seleziona la directory
4. Inserisci il nome del progetto
5. Fai clic su Finish
```

### Importazione del binario

```
1. File → Import File (I)
2. Seleziona il binario o il file
3. Scegli il formato (di solito rilevato automaticamente)
4. Imposta le opzioni:
   - Language/Processor
   - Compiler spec
5. Fai clic su OK
6. Analyze? → Yes (consigliato)
```

### Opzioni di analisi

| Opzione | Descrizione |
|--------|-------------|
| ASCII Strings | Individua le stringhe |
| Decompiler | Abilita la decompilazione |
| Stack Analysis | Analisi del frame dello stack |
| Reference Analysis | Individua i riferimenti incrociati |
| Function ID | Riconoscimento delle funzioni di libreria |
| Call Convention | Identifica le convenzioni di chiamata |

---

## 🔬 Fondamenti dell'analisi

### Analisi automatica

```
Analysis → Auto Analyze (A)
- Esegue tutti gli analizzatori selezionati
- Può richiedere tempo per i binari di grandi dimensioni
- L'avanzamento viene mostrato nel monitor delle attività
```

### Analizzatori eseguiti singolarmente

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Identificazione delle funzioni

```
# Individua tutte le funzioni
Analysis → Auto Analyze → Enable "Non-returning Functions"

# Creazione manuale di una funzione
Clic destro sull'indirizzo → Create Function (F)

# Codice non ancora definito
Clic destro → Disassemble (D)
```

---

## 🧭 Navigazione

### Passaggio a un indirizzo

```
# Scorciatoia da tastiera
G → Inserisci l'indirizzo → OK

# Esempi:
0x00401000      # Indirizzo esadecimale
main            # Nome della funzione
entry           # Punto di ingresso
LAB_00401234    # Etichetta
```

### Scorciatoie di navigazione

| Scorciatoia | Azione |
|----------|--------|
| `G` | Vai all'indirizzo |
| `Ctrl+G` | Vai all'indirizzo |
| `Alt+←` | Torna alla posizione precedente |
| `Alt+→` | Vai alla posizione successiva |
| `Ctrl+Shift+E` | Vai al punto di ingresso |

### Riferimenti incrociati (XREF)

```
# Visualizza i riferimenti alla posizione corrente
Clic destro → References → Show References To

# Individua i punti in cui viene chiamata la funzione
Seleziona la funzione → Clic destro → References → Show References To

# Tastiera
Ctrl+Shift+F → Cerca i riferimenti
```

### Ricerca delle funzioni

```
# Ricerca
Search → Memory (S)           # Cerca nella memoria
Search → For Strings          # Individua le stringhe
Search → For Direct References # Individua i riferimenti all'indirizzo
Search → Program Text         # Cerca nel disassemblato

# Ricerca di una funzione
Symbol Tree → Filter → Digita il nome della funzione
```

---

## 💻 Decompiler

### Apertura del decompilatore

```
Window → Decompiler
# Oppure fai clic sulla scheda Decompiler
# Decompila automaticamente la funzione selezionata
```

### Lettura del codice decompilato

```c
// Esempio di output decompilato
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

### Migliorare la decompilazione

```
# Rinomina le variabili
Clic destro sulla variabile → Rename Variable (L)

# Cambia il tipo di dati
Clic destro sulla variabile → Retype Variable (Ctrl+L)

# Aggiungi commenti
; → Imposta un commento a fine riga (EOL)
Ctrl+; → Imposta un commento di intestazione (plate comment)

# Imposta la firma della funzione
Clic destro sulla funzione → Edit Function Signature
```

### Suggerimenti per il decompilatore

| Azione | Come |
|--------|-----|
| Rinomina la variabile | `L` oppure clic destro |
| Cambia il tipo | `Ctrl+L` |
| Crea una struttura | Clic destro → Auto Create Structure |
| Dividi o unisci le variabili | Clic destro → Split/Merge |
| Conferma la firma | `P` |

---

## 📊 Tipi di dati e strutture

### Creazione di una struttura

```
# Metodo 1: da Data Type Manager
Data Type Manager → Clic destro → New → Structure

# Metodo 2: creazione automatica dal decompilatore
Decompiler → Clic destro sulla variabile → Auto Create Structure
```

### Definizione della struttura

```c
// Struttura di esempio
struct Player {
    char name[32];      // offset 0x00
    int health;         // offset 0x20
    int score;          // offset 0x24
    float position[3];  // offset 0x28
};
```

### Applicazione della struttura

```
# Nella vista Listing
Seleziona l'indirizzo → Clic destro → Data → Choose Data Type

# Oppure
Premi T → Seleziona la struttura

# Nel Decompiler
Clic destro sulla variabile → Retype Variable → Seleziona la struttura
```

### Array

```
# Crea un array
Seleziona l'indirizzo iniziale → Clic destro → Data → Create Array
Inserisci il numero di elementi
```

### Enumerazioni

```
# Crea un'enumerazione
Data Type Manager → Clic destro → New → Enum

# Definisci i valori
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Patch e modifiche

### Modifica di un'istruzione (patch)

```
# Metodo 1: tastiera
Seleziona l'istruzione → Ctrl+Shift+G
Inserisci la nuova istruzione → Premi Enter

# Metodo 2: menu
Clic destro → Patch Instruction
```

### Modifica dei dati (patch)

```
# Cambia i byte
Clic destro → Patch Data
Inserisci i byte esadecimali

# Oppure
Seleziona i byte → Ctrl+Shift+G
```

### Patch comuni

| Originale | Modificato | Scopo |
|----------|---------|---------|
| `JE` | `JMP` | Esegui sempre il salto |
| `JNE` | `JMP` | Esegui sempre il salto |
| `JE` | `NOP` | Non eseguire il salto |
| `CALL` | `NOP NOP NOP NOP NOP` | Salta la chiamata |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Esportazione del binario modificato

```
File → Export Program
Format: Binary
Seleziona la destinazione
```

### Sequenza di NOP

```
# Riempi con NOP
Seleziona l'intervallo → Clic destro → Clear Code Block
Poi: Seleziona → Clic destro → Patch → Fill with NOPs
```

---

## 🐍 Scripting

### Gestore degli script

```
Window → Script Manager
# Oppure: Tools → Script Manager
```

### Esecuzione di uno script

```
Script Manager → Seleziona lo script → Run (pulsante verde di esecuzione)
# Oppure fai doppio clic sullo script
```

### Scripting Python (Ghidra Python)

```python
# Esempio: elenca tutte le funzioni
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = ordine crescente

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Operazioni comuni negli script

```python
# Ottieni l'indirizzo corrente
currentAddress

# Ottieni il programma corrente
currentProgram

# Ottieni il gestore delle funzioni
fm = currentProgram.getFunctionManager()

# Ottieni il listing (disassemblato)
listing = currentProgram.getListing()

# Ottieni l'istruzione all'indirizzo
instr = listing.getInstructionAt(addr)

# Ottieni i byte all'indirizzo
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Crea un'etichetta
createLabel(addr, "my_label", True)

# Aggiungi un commento
setEOLComment(addr, "My comment")

# Ottieni i riferimenti
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Script integrati utili

| Script | Scopo |
|--------|---------|
| FindCrypt | Individua le costanti crittografiche |
| FunctionID | Riconosci le funzioni di libreria |
| ExportFunctionInfoScript | Esporta l'elenco delle funzioni |
| FindStrings | Individua i riferimenti alle stringhe |
| SearchMemory | Cerca sequenze (pattern) |

---

## ⌨ Scorciatoie da tastiera

### Navigazione

| Scorciatoia | Azione |
|----------|--------|
| `G` | Vai all'indirizzo |
| `Alt+←` | Indietro |
| `Alt+→` | Avanti |
| `Ctrl+Shift+E` | Punto di ingresso |
| `Ctrl+E` | Esporta la tabella |
| `N` | Prossima area non definita |

### Analisi

| Scorciatoia | Azione |
|----------|--------|
| `F` | Crea una funzione |
| `D` | Disassembla |
| `C` | Rimuovi la definizione |
| `P` | Crea un puntatore |
| `T` | Applica il tipo di dati |
| `A` | Analizza automaticamente |

### Modifica

| Scorciatoia | Azione |
|----------|--------|
| `L` | Rinomina etichetta o variabile |
| `;` | Imposta il commento a fine riga |
| `Ctrl+;` | Imposta un commento di intestazione |
| `Ctrl+L` | Cambia il tipo della variabile |
| `Ctrl+Shift+G` | Modifica l'istruzione |

### Ricerca

| Scorciatoia | Azione |
|----------|--------|
| `Ctrl+Shift+F` | Trova i riferimenti |
| `Ctrl+F` | Cerca testo |
| `S` | Cerca nella memoria |
| `Ctrl+B` | Cerca byte |

### Windows

| Scorciatoia | Azione |
|----------|--------|
| `Ctrl+D` | Mostra o nascondi il decompilatore |
| `Ctrl+T` | Mostra o nascondi il gestore dei tipi di dati |
| `Ctrl+S` | Albero dei simboli |

---

## 📊 Riferimento rapido

### Procedura di analisi

```
1. Importa il binario  → File → Import
2. Analisi automatica → Analysis → Auto Analyze (accetta i valori predefiniti)
3. Trova entry/main   → G → main oppure entry
4. Esamina le funzioni → Symbol Tree → Functions
5. Esamina le stringhe → Search → For Strings
6. Segui l'esecuzione → Segui chiamate e salti
7. Rinomina i simboli → L su funzioni e variabili
8. Documenta          → Aggiungi commenti (;)
```

### Attività comuni

| Attività | Come |
|------|-----|
| Trova main() | `G` → `main` oppure cerca in Symbol Tree |
| Visualizza le stringhe | Search → For Strings |
| Individua il codice crittografico | Esegui lo script FindCrypt |
| Esamina gli import | Symbol Tree → Imports |
| Trova gli XREF | Seleziona → Ctrl+Shift+F |
| Crea una funzione | Seleziona l'indirizzo → `F` |
| Decompila | Window → Decompiler |

### Checklist per il reverse engineering

```
☐ Identifica il tipo di file (PE, ELF, ecc.)
☐ Esegui l'analisi automatica
☐ Trova il punto di ingresso o main
☐ Cerca indizi nelle stringhe
☐ Individua le funzioni interessanti
☐ Ricostruisci il flusso del programma
☐ Analizza le funzioni sospette
☐ Documenta i risultati con commenti
☐ Rinomina funzioni e variabili
☐ Crea le strutture necessarie
```

### Convenzioni di chiamata delle funzioni

| Convenzione | Valore restituito | Argomenti | Pulizia dello stack |
|------------|--------|------|----------------|
| **cdecl** | EAX | Stack (da destra a sinistra) | Chiamante |
| **stdcall** | EAX | Stack (da destra a sinistra) | Funzione chiamata |
| **fastcall** | EAX | ECX, EDX, Stack | Funzione chiamata |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Chiamante |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Chiamante |

---

## 📚 Risorse

- [Sito ufficiale di Ghidra](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Documentazione Ghidra](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Schede correlate
- [Binwalk](../../Binwalk/translations/README.it.md)
- [Comandi Linux](../../Linux-Commands/translations/README.it.md)
- [Volatility](../../Volatility/translations/README.it.md)

---

<p align="center">
  <b>🔍 Padroneggia il reverse engineering!</b><br>
  <i>Ghidra - Il contributo della NSA alla comunità della sicurezza</i>
</p>
