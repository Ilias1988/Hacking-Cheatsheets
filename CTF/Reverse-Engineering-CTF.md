# 🔄 Reverse Engineering CTF Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

---

## 🔍 Initial Analysis

```bash
# File type
file binary

# Strings
strings binary | grep -i flag
strings -n 8 binary

# ELF info
readelf -h binary

# Check protections
checksec binary
```

---

## 🛠️ Disassemblers

### Ghidra (Free)
```
1. File → Import File
2. Analyze → Auto Analyze
3. Look for main() function
4. Check for strcmp/memcmp for password checks
5. F5 to decompile
```

### IDA Free
```
1. Open binary
2. F5 for pseudocode (Hex-Rays)
3. X to find cross-references
4. N to rename variables
```

### Radare2
```bash
r2 binary
aaa          # Analyze
afl          # List functions
pdf @main    # Disassemble main
VV           # Visual mode
```

---

## 🐛 GDB Debugging

```bash
# Start
gdb ./binary

# Breakpoints
b main
b *0x401234

# Run
r
r arg1 arg2

# Step
n           # Next line
s           # Step into
c           # Continue

# Examine
x/s $rdi    # String
x/10x $rsp  # 10 hex from stack
info reg    # Registers

# Find flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Install GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Commands
vmmap
heap
telescope $rsp
```

---

## 🐍 Python Patching

```python
# Read binary
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Patch bytes
data[0x1234] = 0x90  # NOP

# Write
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Common Patterns

| Check | Bypass |
|-------|--------|
| `strcmp(input, "password")` | Extract "password" |
| `if (x == 0x1337)` | Find value 0x1337 |
| Anti-debug | Patch ptrace |
| Obfuscated strings | Dynamic analysis |

---

## 📋 RE Checklist

```markdown
□ Run file/strings commands
□ Check for debug symbols
□ Open in Ghidra/IDA
□ Find main function
□ Look for string comparisons
□ Trace user input
□ Patch anti-debug if needed
□ Dynamic analysis with GDB
```

---

**Back to CTF:** [🏁 CTF Overview](./README.md)
