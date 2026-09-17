# 🔧 x64dbg - Windows Debugger Cheatsheet

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Windows x64/x32 Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 Table of Contents

- [What is x64dbg](#-what-is-x64dbg)
- [Installation](#-installation)
- [Interface Overview](#-interface-overview)
- [Basic Usage](#-basic-usage)
- [Breakpoints](#-breakpoints)
- [Stepping & Execution](#-stepping--execution)
- [Memory Operations](#-memory-operations)
- [Registers](#-registers)
- [Stack Analysis](#-stack-analysis)
- [Patching](#-patching)
- [Scripting](#-scripting)
- [Keyboard Shortcuts](#-keyboard-shortcuts)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is x64dbg

**x64dbg** is an open-source x64/x32 debugger for Windows. It's the spiritual successor to OllyDbg with modern features:

- 🔍 **User-mode Debugging** - Debug Windows applications
- 🦠 **Malware Analysis** - Analyze suspicious executables
- 💥 **Exploit Development** - Windows exploit research
- 🔧 **Reverse Engineering** - Understand binary behavior
- 🧩 **Plugin Support** - Extensible with plugins

### Features

| Feature | Description |
|---------|-------------|
| **GUI** | Modern, customizable interface |
| **x64 Support** | Full 64-bit debugging |
| **Scripting** | Built-in scripting language |
| **Plugins** | Extensive plugin ecosystem |
| **Symbols** | PDB symbol support |
| **Open Source** | Free and actively developed |

---

## 🚀 Installation

### Download

```
# Official website
https://x64dbg.com/

# GitHub releases
https://github.com/x64dbg/x64dbg/releases

# Download: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Setup

```
1. Extract ZIP to permanent location (e.g., C:\x64dbg\)
2. Run x96dbg.exe (launcher for both)
   - x32dbg.exe → 32-bit debugging
   - x64dbg.exe → 64-bit debugging
3. (Optional) Add to PATH or create shortcuts
```

### Recommended Plugins

| Plugin | Purpose |
|--------|---------|
| **ScyllaHide** | Anti-anti-debug |
| **SwissArmyKnife** | Various utilities |
| **x64dbgpy** | Python scripting |
| **TitanHide** | Kernel anti-debug |
| **OllyDumpEx** | Memory dumping |
| **Multiline Ultimate Assembler** | Multi-line patching |

### Plugin Installation

```
1. Download plugin (DLL file)
2. Copy to: x64dbg\release\x32\plugins\ (32-bit)
           x64dbg\release\x64\plugins\ (64-bit)
3. Restart x64dbg
```

---

## 🖥️ Interface Overview

### Main Windows

| Window | Description | Shortcut |
|--------|-------------|----------|
| **CPU** | Disassembly view | Alt+C |
| **Log** | Debug output | Alt+L |
| **Memory Map** | Memory regions | Alt+M |
| **Call Stack** | Stack trace | Alt+K |
| **Breakpoints** | All breakpoints | Alt+B |
| **Symbols** | Loaded symbols | Alt+E |
| **Threads** | Thread list | Alt+T |
| **Handles** | Handle list | Alt+H |

### CPU Window Panels

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Status Bar

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

## 💻 Basic Usage

### Starting Debugging

```
# Open executable
File → Open (F3)

# Attach to process
File → Attach (Alt+A)

# Open and run
Debug → Run (F9)

# Restart debugging
Debug → Restart (Ctrl+F2)

# Stop debugging
Debug → Stop (Alt+F2)
```

### Command Bar

```
# Enter commands in the command bar at bottom

# Go to address
g 0x401000
goto 0x401000

# Go to expression
g GetProcAddress
g kernel32.LoadLibraryA

# Search string
/s "password"

# Search hex
/x 90 90 90 90

# Search pattern
/p "8B ?? 89"
```

### Navigation

```
# Go to address
Ctrl+G → Enter address

# Go to entry point
Debug → Entry Point

# Follow in disassembler
Right-click → Follow in Disassembler

# Go back
- (minus key)

# Go forward
+ (plus key)
```

---

## 🔴 Breakpoints

### Software Breakpoints (INT3)

```
# Toggle breakpoint at current address
F2

# Set breakpoint via command
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Set conditional breakpoint
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Break on module load
bp $module(kernel32)
```

### Hardware Breakpoints

```
# Set hardware breakpoint (limited to 4)
Right-click → Breakpoint → Hardware → Execute
Right-click → Breakpoint → Hardware → Access
Right-click → Breakpoint → Hardware → Write

# Via command
bph 0x401000, x, 1    # Execute
bph 0x401000, r, 4    # Read (4 bytes)
bph 0x401000, w, 4    # Write (4 bytes)

# Sizes: 1, 2, 4, or 8 bytes
```

### Memory Breakpoints

```
# Break on memory access
Right-click in dump → Breakpoint → Memory → Access
Right-click in dump → Breakpoint → Memory → Write

# Via command
bpm 0x401000, r       # Read
bpm 0x401000, w       # Write
bpm 0x401000, x       # Execute
```

### Conditional Breakpoints

```
# Break if condition is true
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Break on call count
bp 0x401000, "", 5    # Break on 5th hit

# Log without breaking
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Managing Breakpoints

```
# List all breakpoints
Breakpoints window (Alt+B)

# Delete breakpoint
bc 0x401000          # Delete specific
bc                   # Delete all

# Enable/Disable
bd 0x401000          # Disable
be 0x401000          # Enable

# Disable all
bd *

# Enable all
be *
```

---

## ▶️ Stepping & Execution

### Stepping

| Action | Shortcut | Description |
|--------|----------|-------------|
| Step Into | F7 | Execute one instruction |
| Step Over | F8 | Step over calls |
| Step Out | Ctrl+F9 | Run until return |
| Run to Selection | F4 | Run to cursor |
| Run to User Code | Alt+F9 | Exit system code |

### Running

| Action | Shortcut | Description |
|--------|----------|-------------|
| Run | F9 | Continue execution |
| Run Skipping | Ctrl+F7 | Skip current instruction |
| Pause | F12 | Break execution |
| Restart | Ctrl+F2 | Restart debugging |
| Close | Alt+F2 | Stop debugging |

### Trace

```
# Trace into (log all instructions)
Debug → Trace Into (Ctrl+F7)

# Trace over
Debug → Trace Over (Ctrl+F8)

# Trace record
Debug → Trace Record

# Stop trace
Debug → Stop Trace
```

### Skip Instruction

```
# Skip current instruction without executing
Right-click → Set New Origin Here

# Or
Ctrl+Shift+O
```

---

## 🔍 Memory Operations

### Memory Dump

```
# Follow address in dump
Right-click → Follow in Dump → Address/Selection

# Go to address in dump
Ctrl+G (in dump window)

# Change dump type
Right-click → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Memory Map

```
# Open Memory Map
View → Memory Map (Alt+M)

# Shows all memory regions:
- Address
- Size
- Section
- Type (Image/Private/Mapped)
- Protection (Execute/Read/Write)
- Module name
```

### Search Memory

```
# Search in command bar
/s "string"           # ASCII string
/s L"string"          # Unicode string
/x 90 90 90           # Hex bytes
/p "8B ?? 89"         # Pattern with wildcards

# Find references
Right-click → Find references to → Selected Address

# Find strings
Right-click → Search for → All Strings
```

### Memory Protection

```
# View protection
Memory Map → Protection column

# Common protections
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Dump Memory

```
# Dump memory to file
Right-click in Memory Map → Dump Memory to File

# Dump with plugins
Plugins → OllyDumpEx → Dump
```

---

## 📊 Registers

### Register Panel

```
# General Purpose (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# General Purpose (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Segment Registers
CS, DS, SS, ES, FS, GS

# Flags (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Modify Registers

```
# Double-click register to edit
# Or right-click → Modify Value

# Via command bar
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Set Zero Flag
```

### Important Flags

| Flag | Name | Set When |
|------|------|----------|
| ZF | Zero Flag | Result is zero |
| CF | Carry Flag | Unsigned overflow |
| SF | Sign Flag | Result is negative |
| OF | Overflow Flag | Signed overflow |
| DF | Direction Flag | String direction |
| TF | Trap Flag | Single step |

### Toggle Flags

```
# Click on flag letter in registers panel
# Or use commands:
r zf=!zf         # Toggle Zero Flag
r cf=0           # Clear Carry Flag
r sf=1           # Set Sign Flag
```

---

## 📚 Stack Analysis

### Stack Panel

```
# Shows stack contents with:
- Address
- Value
- Comments (if available)

# Right-click options:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify
```

### Call Stack

```
# Open Call Stack
View → Call Stack (Alt+K)

# Shows:
- Return Address
- From (caller)
- To (callee)
- Size
- Comment
```

### Stack Operations

```
# Go to stack address
Right-click → Follow DWORD in Stack

# Modify stack value
Right-click → Modify → Edit

# Push/Pop simulation
r esp=esp-4      # Push (32-bit)
r esp=esp+4      # Pop (32-bit)
r rsp=rsp-8      # Push (64-bit)
r rsp=rsp+8      # Pop (64-bit)
```

### Stack Layout (32-bit stdcall)

```
[ESP]      → Return Address
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

After PUSH EBP; MOV EBP, ESP:
[EBP]      → Saved EBP
[EBP+4]    → Return Address
[EBP+8]    → Arg1
[EBP-4]    → Local1
```

---

## 🔧 Patching

### Assemble Instructions

```
# Right-click instruction → Assemble (Space)
# Enter new instruction(s)

# Examples:
NOP                    # No operation
JMP 0x401020           # Jump
MOV EAX, 0             # Clear EAX
XOR EAX, EAX           # Clear EAX (smaller)
RET                    # Return
```

### Fill with NOPs

```
# Select range → Right-click → Binary → Fill with NOPs

# Or select and press Space:
# Enter multiple NOPs
```

### Binary Edit

```
# Edit bytes directly
Right-click → Binary → Edit

# Fill with value
Right-click → Binary → Fill

# Copy/Paste binary
Right-click → Binary → Copy
Right-click → Binary → Paste
```

### Common Patches

| Before | After | Purpose |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Invert condition |
| `75 XX` (JNE) | `74 XX` (JE) | Invert condition |
| `74 XX` (JE) | `EB XX` (JMP) | Always jump |
| `74 XX` (JE) | `90 90` (NOP) | Never jump |
| `0F 84` (JE) | `0F 85` (JNE) | Invert (near) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Skip call |

### Save Patches

```
# Method 1: Patch File
File → Patch File

# Method 2: Export patches
Right-click → Copy → Selection
# Paste in hex editor

# Method 3: Plugin
Plugins → Export modified executable
```

---

## 📝 Scripting

### Command Syntax

```
# Variables
$var = 0x401000

# Comparison
cmp eax, 0

# Conditional
je label
jne label

# Labels
label:

# Comments
; This is a comment
```

### Basic Script

```
; Script to find WinMain
mov $addr, 0x401000        ; Start address
loop:
  find $addr, "8B EC"       ; Find PUSH EBP; MOV EBP, ESP
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

### Script Commands

| Command | Description |
|---------|-------------|
| `msg "text"` | Display message |
| `log "text"` | Log to console |
| `find addr, "pattern"` | Search pattern |
| `bp addr` | Set breakpoint |
| `bc addr` | Clear breakpoint |
| `sti` | Step into |
| `sto` | Step over |
| `run` | Run |
| `ret` | Return/stop |

### Run Script

```
# Load and run script
Scripts → Run Script

# Or command bar
scr "scriptfile.txt"
```

---

## ⌨️ Keyboard Shortcuts

### General

| Shortcut | Action |
|----------|--------|
| F3 | Open file |
| Alt+A | Attach to process |
| Ctrl+F2 | Restart |
| Alt+F2 | Stop debugging |
| Ctrl+G | Go to address |
| Ctrl+F | Find |

### Debugging

| Shortcut | Action |
|----------|--------|
| F2 | Toggle breakpoint |
| F4 | Run to selection |
| F7 | Step into |
| F8 | Step over |
| F9 | Run |
| F12 | Pause |
| Ctrl+F9 | Run until return |

### Navigation

| Shortcut | Action |
|----------|--------|
| - | Go back |
| + | Go forward |
| * | Go to EIP/RIP |
| Enter | Follow call/jump |
| Escape | Go back |

### Views

| Shortcut | Action |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Breakpoints |
| Alt+E | Symbols |
| Alt+T | Threads |

### Editing

| Shortcut | Action |
|----------|--------|
| Space | Assemble |
| ; | Add comment |
| : | Add label |
| N | Rename |

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `bp addr` | Set breakpoint |
| `bc addr` | Clear breakpoint |
| `bph addr, x` | Hardware breakpoint |
| `g addr` | Go to address |
| `r reg=val` | Set register |
| `sti` | Step into |
| `sto` | Step over |
| `run` | Run |

### Common Patterns

| Bytes | Instruction |
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

### Analysis Workflow

```
1. Load executable           → F3
2. Set breakpoint at entry   → F2 at entry
3. Run to breakpoint         → F9
4. Step through code         → F7/F8
5. Examine registers/memory  → Panels
6. Set additional breakpoints
7. Continue analysis
8. Patch if needed           → Space
9. Save patched file
```

### Anti-Debug Bypass

```
# Install ScyllaHide plugin
Plugins → ScyllaHide → Options → Select profile

# Manual patches:
- NOP IsDebuggerPresent calls
- Patch PEB.BeingDebugged
- Handle exceptions properly
```

---

## 📚 Resources

- [x64dbg Official](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [x64dbg Documentation](https://help.x64dbg.com/)
- [x64dbg Plugins](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Related Cheatsheets
- [Ghidra](../Ghidra/README.md)
- [GDB](../GDB/README.md)
- [Windows PrivEsc](../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🔧 Master Windows Debugging!</b><br>
  <i>x64dbg - The modern OllyDbg successor</i>
</p>
