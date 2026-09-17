# 🐛 GDB - GNU Debugger Cheatsheet

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

## 📋 Table of Contents

- [What is GDB](#-what-is-gdb)
- [Installation & Setup](#-installation--setup)
- [Basic Usage](#-basic-usage)
- [Breakpoints](#-breakpoints)
- [Execution Control](#-execution-control)
- [Examining Memory](#-examining-memory)
- [Registers](#-registers)
- [Stack Analysis](#-stack-analysis)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Exploit Development](#-exploit-development)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is GDB

**GDB** (GNU Debugger) is the standard debugger for Linux. Essential for:

- 🐛 **Debugging** - Find and fix bugs
- 🔍 **Reverse Engineering** - Understand binary behavior
- 💥 **Exploit Development** - Buffer overflows, ROP
- 📊 **Binary Analysis** - Examine execution flow
- 🧪 **CTF Challenges** - Solve RE/pwn challenges

### Supported Architectures

| Architecture | Notes |
|--------------|-------|
| x86 | 32-bit Intel |
| x86-64 | 64-bit Intel/AMD |
| ARM | 32-bit ARM |
| ARM64 | 64-bit ARM |
| MIPS | MIPS processors |
| PowerPC | PPC processors |

---

## 🚀 Installation & Setup

### Install GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (use lldb instead, or)
brew install gdb
```

### Install GEF (GDB Enhanced Features)

```bash
# GEF - Most recommended
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Or manually
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Install PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Install pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Verify Installation

```bash
gdb --version
gdb -q
(gdb) quit
```

---

## 💻 Basic Usage

### Starting GDB

```bash
# Debug executable
gdb ./program

# Debug with arguments
gdb --args ./program arg1 arg2

# Attach to running process
gdb -p <PID>
gdb attach <PID>

# Quiet mode (no banner)
gdb -q ./program

# Execute commands from file
gdb -x commands.gdb ./program
```

### Basic Commands

| Command | Short | Description |
|---------|-------|-------------|
| `run` | `r` | Start program |
| `run args` | `r args` | Run with arguments |
| `continue` | `c` | Continue execution |
| `quit` | `q` | Exit GDB |
| `help` | `h` | Show help |
| `help <cmd>` | `h <cmd>` | Help on command |

### File Commands

```bash
# Load executable
(gdb) file ./program

# Load symbols
(gdb) symbol-file ./program.debug

# Load core dump
(gdb) core-file core

# Set working directory
(gdb) cd /path/to/dir

# Set arguments
(gdb) set args arg1 arg2
```

---

## 🔴 Breakpoints

### Setting Breakpoints

```bash
# Break at function
(gdb) break main
(gdb) b main

# Break at address
(gdb) break *0x08048456
(gdb) b *0x08048456

# Break at line
(gdb) break file.c:25
(gdb) b 25

# Break at offset from function
(gdb) break *main+48

# Temporary breakpoint (auto-delete)
(gdb) tbreak main
```

### Conditional Breakpoints

```bash
# Break when condition is true
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Add condition to existing breakpoint
(gdb) condition 1 $eax == 0

# Remove condition
(gdb) condition 1
```

### Managing Breakpoints

```bash
# List breakpoints
(gdb) info breakpoints
(gdb) info b

# Delete breakpoint
(gdb) delete 1
(gdb) d 1

# Delete all breakpoints
(gdb) delete
(gdb) d

# Disable breakpoint
(gdb) disable 1
(gdb) dis 1

# Enable breakpoint
(gdb) enable 1
(gdb) en 1

# Disable all
(gdb) disable

# Enable all
(gdb) enable
```

### Watchpoints (Data Breakpoints)

```bash
# Watch variable for changes
(gdb) watch variable
(gdb) watch *0x08048456

# Watch for reads
(gdb) rwatch variable

# Watch for reads or writes
(gdb) awatch variable

# Watch expression
(gdb) watch ($eax > 10)
```

### Catchpoints

```bash
# Catch syscalls
(gdb) catch syscall

# Catch specific syscall
(gdb) catch syscall write

# Catch signals
(gdb) catch signal SIGSEGV

# Catch exceptions
(gdb) catch throw
(gdb) catch catch
```

---

## ▶️ Execution Control

### Stepping

```bash
# Step one instruction (into calls)
(gdb) stepi
(gdb) si

# Step one line (into calls)
(gdb) step
(gdb) s

# Step over (don't enter calls)
(gdb) nexti
(gdb) ni

# Step over line
(gdb) next
(gdb) n

# Continue until return
(gdb) finish
(gdb) fin

# Continue until address
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Running

```bash
# Start/restart program
(gdb) run
(gdb) r

# Continue after breakpoint
(gdb) continue
(gdb) c

# Run until next breakpoint
(gdb) continue

# Skip N breakpoints
(gdb) continue 5

# Jump to address (dangerous!)
(gdb) jump *0x08048456
```

### Process Control

```bash
# Kill current process
(gdb) kill

# Detach from process
(gdb) detach

# Attach to process
(gdb) attach <PID>

# Follow fork (parent/child)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

## 🔍 Examining Memory

### Print Command

```bash
# Print variable
(gdb) print variable
(gdb) p variable

# Print in hex
(gdb) print/x variable
(gdb) p/x $eax

# Print in binary
(gdb) p/t variable

# Print in decimal
(gdb) p/d variable

# Print as character
(gdb) p/c $al

# Print as string
(gdb) p/s pointer

# Print array
(gdb) p *array@10     # 10 elements
```

### Examine Command (x)

```bash
# Syntax: x/NFU address
# N = count, F = format, U = unit size

# Formats: x(hex), d(decimal), u(unsigned), o(octal), 
#          t(binary), c(char), s(string), i(instruction)
# Units: b(byte), h(half=2), w(word=4), g(giant=8)

# Examine memory
(gdb) x/10x $esp           # 10 hex words from ESP
(gdb) x/20wx 0x08048456    # 20 words in hex
(gdb) x/s 0x08048456       # String at address
(gdb) x/10i $eip           # 10 instructions from EIP
(gdb) x/10gx $rsp          # 10 giant (8 bytes) from RSP

# Common patterns
(gdb) x/100x $esp          # Examine stack
(gdb) x/20i $eip           # Disassemble
(gdb) x/s $eax             # String pointer
```

### Memory Regions

```bash
# Show memory map
(gdb) info proc mappings

# Show sections
(gdb) maintenance info sections

# Show files
(gdb) info files
```

### Disassembly

```bash
# Disassemble function
(gdb) disassemble main
(gdb) disas main

# Disassemble current function
(gdb) disassemble
(gdb) disas

# Disassemble range
(gdb) disas 0x08048456, 0x08048500

# Disassemble with source (if available)
(gdb) disas /m main

# Set syntax
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

## 📊 Registers

### View Registers

```bash
# All registers
(gdb) info registers
(gdb) i r

# Specific register
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# All registers including FP/SSE
(gdb) info all-registers
```

### Modify Registers

```bash
# Set register value
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Set flags
(gdb) set $eflags = 0x246
```

### x86 Registers Reference

| Register | 64-bit | 32-bit | 16-bit | 8-bit | Purpose |
|----------|--------|--------|--------|-------|---------|
| Accumulator | RAX | EAX | AX | AL/AH | Return value |
| Base | RBX | EBX | BX | BL/BH | General |
| Counter | RCX | ECX | CX | CL/CH | Loop counter |
| Data | RDX | EDX | DX | DL/DH | I/O, multiply |
| Source Index | RSI | ESI | SI | SIL | String src |
| Dest Index | RDI | EDI | DI | DIL | String dst |
| Base Pointer | RBP | EBP | BP | BPL | Stack frame |
| Stack Pointer | RSP | ESP | SP | SPL | Stack top |
| Instruction | RIP | EIP | IP | - | Next instr |

### x64 Additional Registers

```
R8-R15 : General purpose registers
R8D-R15D : 32-bit lower halves
R8W-R15W : 16-bit lower halves
R8B-R15B : 8-bit lower halves
```

---

## 📚 Stack Analysis

### View Stack

```bash
# Stack backtrace
(gdb) backtrace
(gdb) bt

# Full backtrace with locals
(gdb) backtrace full
(gdb) bt full

# Show frame
(gdb) frame
(gdb) f

# Select frame
(gdb) frame 2
(gdb) f 2

# Show stack contents
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Stack Frame

```bash
# Show frame info
(gdb) info frame
(gdb) i f

# Show locals
(gdb) info locals

# Show arguments
(gdb) info args

# Move up/down frames
(gdb) up
(gdb) down
```

### Stack Commands

```bash
# Examine return address
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Examine saved EBP
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Show stack layout
(gdb) x/20wx $esp-20  # Before and after
```

---

## 🎨 GEF/PEDA/pwndbg

### GEF Commands

```bash
# Context display (automatic)
gef➤ context

# Checksec (security features)
gef➤ checksec

# Search pattern
gef➤ pattern create 200
gef➤ pattern search $rsp

# Find string/value
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Heap analysis
gef➤ heap chunks
gef➤ heap bins

# ROP gadgets
gef➤ ropgadget

# Format string helper
gef➤ format-string-helper

# VMMap (memory map)
gef➤ vmmap

# Dereference chain
gef➤ dereference $rsp 20

# Telescope (smart dereference)
gef➤ telescope $rsp 20

# ELF info
gef➤ elf-info

# Canary value
gef➤ canary

# PIE/ASLR info
gef➤ pie
gef➤ aslr
```

### PEDA Commands

```bash
# Security check
peda➤ checksec

# Find string
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Pattern create/search
peda➤ pattern create 200
peda➤ pattern offset $eip

# ROP gadgets
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Context
peda➤ context all

# Assembly
peda➤ assemble
```

### pwndbg Commands

```bash
# Context
pwndbg> context

# Checksec
pwndbg> checksec

# Search
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Cyclic pattern
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

## 💥 Exploit Development

### Finding Offset

```bash
# Method 1: Pattern (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Method 2: Pattern (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Buffer Overflow Basics

```bash
# Check for ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=off, 1=partial, 2=full

# Disable ASLR (for testing)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Check binary protections
gef➤ checksec

# Find return address location
(gdb) x/20wx $esp     # Before crash
(gdb) i f             # Frame info
```

### Finding Gadgets

```bash
# Find "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Find system() address
(gdb) p system
(gdb) p &system

# Find pop rdi gadget (64-bit)
gef➤ ropgadget --grep "pop rdi"

# Find pop; ret (32-bit)
gef➤ ropgadget --grep "pop"
```

### Write Memory

```bash
# Set memory value
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Write string
(gdb) set {char[10]}0x08048456 = "AAAA"

# Call function
(gdb) call system("/bin/sh")
```

### Useful for Exploits

```bash
# Follow child processes
(gdb) set follow-fork-mode child

# Disable ASLR in GDB
(gdb) set disable-randomization on

# Show shared libraries
(gdb) info sharedlibrary

# Find libc base
gef➤ vmmap libc

# Find PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `r` | Run program |
| `c` | Continue |
| `b *addr` | Set breakpoint |
| `si` | Step instruction |
| `ni` | Next instruction |
| `fin` | Finish function |
| `x/Nx addr` | Examine memory |
| `p $reg` | Print register |
| `i r` | Info registers |
| `bt` | Backtrace |
| `disas` | Disassemble |
| `q` | Quit |

### Memory Examination

| Command | Description |
|---------|-------------|
| `x/10x $esp` | 10 hex words from ESP |
| `x/s addr` | String at address |
| `x/i $eip` | Instruction at EIP |
| `x/20wx addr` | 20 words in hex |
| `x/10gx $rsp` | 10 qwords from RSP |

### Quick Debugging

```bash
# Start and break at main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Examine crash
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Exploit Dev Workflow

```
1. checksec           → Check protections
2. pattern create     → Create pattern
3. run with pattern   → Crash program
4. pattern search     → Find offset
5. find gadgets       → ROP gadgets
6. build payload      → Create exploit
7. test               → Debug with GDB
```

---

## 📚 Resources

- [GDB Official Manual](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF Documentation](https://hugsy.github.io/gef/)
- [pwndbg Documentation](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Related Cheatsheets
- [Ghidra](../Ghidra/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [Linux PrivEsc](../Linux-PrivEsc/README.md)

---

<p align="center">
  <b>🐛 Master Linux Debugging!</b><br>
  <i>GDB - The essential tool for every Linux reverser</i>
</p>
