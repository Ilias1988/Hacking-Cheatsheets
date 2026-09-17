# 🔍 Ghidra - Reverse Engineering Cheatsheet

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              NSA Reverse Engineering Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

## 📋 Table of Contents

- [What is Ghidra](#-what-is-ghidra)
- [Installation](#-installation)
- [Interface Overview](#-interface-overview)
- [Creating a Project](#-creating-a-project)
- [Analysis Basics](#-analysis-basics)
- [Navigation](#-navigation)
- [Decompiler](#-decompiler)
- [Data Types & Structures](#-data-types--structures)
- [Patching & Modifying](#-patching--modifying)
- [Scripting](#-scripting)
- [Keyboard Shortcuts](#-keyboard-shortcuts)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Ghidra

**Ghidra** is a free, open-source software reverse engineering (SRE) suite developed by the NSA. It provides:

- 🔍 **Disassembler** - Convert binary to assembly
- 🔄 **Decompiler** - Generate C-like pseudocode
- 📊 **Analysis** - Automated code analysis
- 🔧 **Patching** - Modify binaries
- 🐍 **Scripting** - Python/Java automation
- 👥 **Collaboration** - Multi-user support

### Supported Architectures

| Architecture | Variants |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Supported Formats

| Format | Description |
|--------|-------------|
| ELF | Linux executables |
| PE/COFF | Windows executables |
| Mach-O | macOS executables |
| Raw Binary | Firmware, ROM |
| APK/DEX | Android apps |
| and many more... |

---

## 🚀 Installation

### Download

```bash
# Download from official site
https://ghidra-sre.org/

# Or GitHub releases
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Requirements

| Requirement | Version |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | 4GB minimum, 8GB+ recommended |
| **Disk** | 1GB+ for installation |

### Linux/macOS

```bash
# Install JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Extract Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Run
./ghidraRun
```

### Windows

```batch
:: Install JDK 17+ from Oracle or Adoptium
:: Extract ghidra_*.zip
:: Run ghidraRun.bat
```

---

## 🖥️ Interface Overview

### Main Windows

| Window | Description |
|--------|-------------|
| **Program Trees** | Project file structure |
| **Symbol Tree** | Functions, labels, classes |
| **Data Type Manager** | Defined data types |
| **Listing** | Disassembly view |
| **Decompiler** | C pseudocode |
| **Console** | Output/scripting |
| **Defined Strings** | Found strings |
| **Bookmarks** | Saved locations |

### Listing Window (Disassembly)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Function start
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Stack frame
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Creating a Project

### New Project

```
1. File → New Project
2. Choose:
   - Non-Shared Project (local)
   - Shared Project (collaboration)
3. Select directory
4. Enter project name
5. Click Finish
```

### Import Binary

```
1. File → Import File (I)
2. Select binary/file
3. Choose format (usually auto-detected)
4. Set options:
   - Language/Processor
   - Compiler spec
5. Click OK
6. Analyze? → Yes (recommended)
```

### Analysis Options

| Option | Description |
|--------|-------------|
| ASCII Strings | Find strings |
| Decompiler | Enable decompilation |
| Stack Analysis | Stack frame analysis |
| Reference Analysis | Find cross-references |
| Function ID | Library function matching |
| Call Convention | Identify calling conventions |

---

## 🔬 Analysis Basics

### Auto Analysis

```
Analysis → Auto Analyze (A)
- Runs all selected analyzers
- Can take time for large binaries
- Progress shown in task monitor
```

### One-Shot Analyzers

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Function Identification

```
# Find all functions
Analysis → Auto Analyze → Enable "Non-returning Functions"

# Manual function creation
Right-click address → Create Function (F)

# Undefined code
Right-click → Disassemble (D)
```

---

## 🧭 Navigation

### Go To Address

```
# Keyboard shortcut
G → Enter address → OK

# Examples:
0x00401000      # Hex address
main            # Function name
entry           # Entry point
LAB_00401234    # Label
```

### Navigation Shortcuts

| Shortcut | Action |
|----------|--------|
| `G` | Go to address |
| `Ctrl+G` | Go to address |
| `Alt+←` | Navigate back |
| `Alt+→` | Navigate forward |
| `Ctrl+Shift+E` | Go to entry point |

### Cross-References (XREFs)

```
# View references to current location
Right-click → References → Show References To

# Find where function is called
Select function → Right-click → References → Show References To

# Keyboard
Ctrl+Shift+F → Find references
```

### Search Functions

```
# Search
Search → Memory (S)           # Search memory
Search → For Strings          # Find strings
Search → For Direct References # Find refs to address
Search → Program Text         # Search disassembly

# Function search
Symbol Tree → Filter → Type function name
```

---

## 💻 Decompiler

### Open Decompiler

```
Window → Decompiler
# Or click Decompiler tab
# Auto-decompiles selected function
```

### Reading Decompiled Code

```c
// Example decompiled output
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

### Improve Decompilation

```
# Rename variables
Right-click variable → Rename Variable (L)

# Change data type
Right-click variable → Retype Variable (Ctrl+L)

# Add comments
; → Set EOL comment
Ctrl+; → Set plate comment

# Set function signature
Right-click function → Edit Function Signature
```

### Decompiler Tricks

| Action | How |
|--------|-----|
| Rename variable | `L` or right-click |
| Change type | `Ctrl+L` |
| Create struct | Right-click → Auto Create Structure |
| Split/Merge variables | Right-click → Split/Merge |
| Commit signature | `P` |

---

## 📊 Data Types & Structures

### Create Structure

```
# Method 1: From Data Type Manager
Data Type Manager → Right-click → New → Structure

# Method 2: Auto-create from decompiler
Decompiler → Right-click variable → Auto Create Structure
```

### Define Structure

```c
// Example structure
struct Player {
    char name[32];      // offset 0x00
    int health;         // offset 0x20
    int score;          // offset 0x24
    float position[3];  // offset 0x28
};
```

### Apply Structure

```
# In Listing view
Select address → Right-click → Data → Choose Data Type

# Or
Press T → Select structure

# In Decompiler
Right-click variable → Retype Variable → Select struct
```

### Arrays

```
# Create array
Select start address → Right-click → Data → Create Array
Enter number of elements
```

### Enums

```
# Create enum
Data Type Manager → Right-click → New → Enum

# Define values
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Patching & Modifying

### Patch Instruction

```
# Method 1: Keyboard
Select instruction → Ctrl+Shift+G
Enter new instruction → Press Enter

# Method 2: Menu
Right-click → Patch Instruction
```

### Patch Data

```
# Change bytes
Right-click → Patch Data
Enter hex bytes

# Or
Select bytes → Ctrl+Shift+G
```

### Common Patches

| Original | Patched | Purpose |
|----------|---------|---------|
| `JE` | `JMP` | Always jump |
| `JNE` | `JMP` | Always jump |
| `JE` | `NOP` | Never jump |
| `CALL` | `NOP NOP NOP NOP NOP` | Skip call |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Export Patched Binary

```
File → Export Program
Format: Binary
Select destination
```

### NOP Sled

```
# Fill with NOPs
Select range → Right-click → Clear Code Block
Then: Select → Right-click → Patch → Fill with NOPs
```

---

## 🐍 Scripting

### Script Manager

```
Window → Script Manager
# Or: Tools → Script Manager
```

### Run Script

```
Script Manager → Select script → Run (green play button)
# Or double-click script
```

### Python Scripting (Ghidra Python)

```python
# Example: List all functions
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = forward

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Common Script Operations

```python
# Get current address
currentAddress

# Get current program
currentProgram

# Get function manager
fm = currentProgram.getFunctionManager()

# Get listing (disassembly)
listing = currentProgram.getListing()

# Get instruction at address
instr = listing.getInstructionAt(addr)

# Get bytes at address
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Create label
createLabel(addr, "my_label", True)

# Add comment
setEOLComment(addr, "My comment")

# Get references
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Useful Built-in Scripts

| Script | Purpose |
|--------|---------|
| FindCrypt | Find crypto constants |
| FunctionID | Match library functions |
| ExportFunctionInfoScript | Export function list |
| FindStrings | Find string references |
| SearchMemory | Search for patterns |

---

## ⌨️ Keyboard Shortcuts

### Navigation

| Shortcut | Action |
|----------|--------|
| `G` | Go to address |
| `Alt+←` | Back |
| `Alt+→` | Forward |
| `Ctrl+Shift+E` | Entry point |
| `Ctrl+E` | Export table |
| `N` | Next undefined |

### Analysis

| Shortcut | Action |
|----------|--------|
| `F` | Create function |
| `D` | Disassemble |
| `C` | Clear (undefine) |
| `P` | Create pointer |
| `T` | Apply data type |
| `A` | Auto-analyze |

### Editing

| Shortcut | Action |
|----------|--------|
| `L` | Rename (label/variable) |
| `;` | Set EOL comment |
| `Ctrl+;` | Set plate comment |
| `Ctrl+L` | Retype variable |
| `Ctrl+Shift+G` | Patch instruction |

### Search

| Shortcut | Action |
|----------|--------|
| `Ctrl+Shift+F` | Find references |
| `Ctrl+F` | Search text |
| `S` | Search memory |
| `Ctrl+B` | Search bytes |

### Windows

| Shortcut | Action |
|----------|--------|
| `Ctrl+D` | Toggle decompiler |
| `Ctrl+T` | Toggle data type manager |
| `Ctrl+S` | Symbol tree |

---

## 📊 Quick Reference

### Analysis Workflow

```
1. Import binary      → File → Import
2. Auto-analyze       → Analysis → Auto Analyze (accept defaults)
3. Find entry/main    → G → main or entry
4. Review functions   → Symbol Tree → Functions
5. Examine strings    → Search → For Strings
6. Trace execution    → Follow calls/jumps
7. Rename symbols     → L on functions/variables
8. Document           → Add comments (;)
```

### Common Tasks

| Task | How |
|------|-----|
| Find main() | `G` → `main` or search Symbol Tree |
| View strings | Search → For Strings |
| Find crypto | Run FindCrypt script |
| Check imports | Symbol Tree → Imports |
| Find XREFs | Select → Ctrl+Shift+F |
| Create function | Select address → `F` |
| Decompile | Window → Decompiler |

### Reversing Checklist

```
☐ Identify file type (PE, ELF, etc.)
☐ Run auto-analysis
☐ Find entry point / main
☐ Check strings for hints
☐ Identify interesting functions
☐ Map out program flow
☐ Analyze suspicious functions
☐ Document findings with comments
☐ Rename functions/variables
☐ Create structures as needed
```

### Function Calling Conventions

| Convention | Return | Args | Caller Cleanup |
|------------|--------|------|----------------|
| **cdecl** | EAX | Stack (R→L) | Caller |
| **stdcall** | EAX | Stack (R→L) | Callee |
| **fastcall** | EAX | ECX, EDX, Stack | Callee |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Caller |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Caller |

---

## 📚 Resources

- [Ghidra Official](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Ghidra Docs](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Related Cheatsheets
- [Binwalk](../Binwalk/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [Volatility](../Volatility/README.md)

---

<p align="center">
  <b>🔍 Master Reverse Engineering!</b><br>
  <i>Ghidra - NSA's gift to the security community</i>
</p>
