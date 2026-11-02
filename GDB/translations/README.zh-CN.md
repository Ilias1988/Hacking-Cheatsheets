# 🐛 GDB - GNU 调试器速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    GNU 调试器
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

## 📋 目录

- [GDB 是什么](#-what-is-gdb)
- [安装与配置](#-installation--setup)
- [基本用法](#-basic-usage)
- [断点](#-breakpoints)
- [执行控制](#-execution-control)
- [查看内存](#-examining-memory)
- [寄存器](#-registers)
- [调用栈分析](#-stack-analysis)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [漏洞利用开发](#-exploit-development)
- [速查表](#-quick-reference)

---

<a id="-what-is-gdb"></a>
## 🎯 GDB 是什么

**GDB** （GNU Debugger）是 Linux 上的标准调试器，常用于以下场景：

- 🐛 **调试** - 查找并修复错误
- 🔍 **逆向工程** - 理解二进制程序的行为
- 💥 **漏洞利用开发** - 缓冲区溢出、返回导向编程（ROP）
- 📊 **二进制分析** - 查看执行流程
- 🧪 **CTF 题目** - 解决逆向／pwn 题目

### 支持的体系结构

| 体系结构 | 说明 |
|--------------|-------|
| x86 | 32 位 Intel |
| x86-64 | 64 位 Intel/AMD |
| ARM | 32 位 ARM |
| ARM64 | 64 位 ARM |
| MIPS | MIPS 处理器 |
| PowerPC | PPC 处理器 |

---

<a id="-installation--setup"></a>
## 🚀 安装与配置

### 安装 GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS（可改用 lldb，或采用下方方式）
brew install gdb
```

### 安装 GEF（GDB Enhanced Features）

```bash
# GEF - 原文最推荐的扩展
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# 也可以手动配置
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### 安装 PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### 安装 pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### 验证安装

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="-basic-usage"></a>
## 💻 基本用法

### 启动 GDB

```bash
# 调试可执行文件
gdb ./program

# 携带参数调试
gdb --args ./program arg1 arg2

# 附加到正在运行的进程
gdb -p <PID>
gdb attach <PID>

# 安静模式（不显示启动横幅）
gdb -q ./program

# 执行文件中的命令
gdb -x commands.gdb ./program
```

> **附加语法勘误：** 上方 `gdb attach <PID>` 混用了启动命令与交互命令；[官方手册](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Attach.html)中的 `attach <PID>` 是 GDB 内部命令，启动时则使用 `-p`。原文示例保持不变。

### 基本命令

| 命令 | 缩写 | 说明 |
|---------|-------|-------------|
| `run` | `r` | 启动程序 |
| `run args` | `r args` | 携带参数运行 |
| `continue` | `c` | 继续执行 |
| `quit` | `q` | 退出 GDB |
| `help` | `h` | 显示帮助 |
| `help <cmd>` | `h <cmd>` | 查看指定命令的帮助 |

### 文件相关命令

```bash
# 加载可执行文件
(gdb) file ./program

# 加载符号
(gdb) symbol-file ./program.debug

# 加载核心转储文件（core dump）
(gdb) core-file core

# 设置工作目录
(gdb) cd /path/to/dir

# 设置参数
(gdb) set args arg1 arg2
```

---

<a id="-breakpoints"></a>
## 🔴 断点

### 设置断点

```bash
# 在函数处设置断点
(gdb) break main
(gdb) b main

# 在地址处设置断点
(gdb) break *0x08048456
(gdb) b *0x08048456

# 在源代码行设置断点
(gdb) break file.c:25
(gdb) b 25

# 在相对函数起点的偏移处设置断点
(gdb) break *main+48

# 临时断点（触发后自动删除）
(gdb) tbreak main
```

### 条件断点

```bash
# 条件成立时中断
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# 为已有断点添加条件
(gdb) condition 1 $eax == 0

# 移除条件
(gdb) condition 1
```

### 管理断点

```bash
# 列出断点
(gdb) info breakpoints
(gdb) info b

# 删除断点
(gdb) delete 1
(gdb) d 1

# 删除全部断点
(gdb) delete
(gdb) d

# 禁用断点
(gdb) disable 1
(gdb) dis 1

# 启用断点
(gdb) enable 1
(gdb) en 1

# 全部禁用
(gdb) disable

# 全部启用
(gdb) enable
```

### 观察点（数据断点）

```bash
# 监视变量变化
(gdb) watch variable
(gdb) watch *0x08048456

# 监视读取操作
(gdb) rwatch variable

# 监视读取或写入操作
(gdb) awatch variable

# 监视表达式
(gdb) watch ($eax > 10)
```

### 捕获点（Catchpoint）

```bash
# 捕获系统调用
(gdb) catch syscall

# 捕获指定系统调用
(gdb) catch syscall write

# 捕获信号
(gdb) catch signal SIGSEGV

# 捕获异常
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-execution-control"></a>
## ▶️ 执行控制

### 单步执行

```bash
# 单步执行一条指令（进入函数调用）
(gdb) stepi
(gdb) si

# 单步执行一行源码（进入函数调用）
(gdb) step
(gdb) s

# 单步跳过调用（不进入被调用函数）
(gdb) nexti
(gdb) ni

# 按源代码行单步跳过调用
(gdb) next
(gdb) n

# 继续执行至函数返回
(gdb) finish
(gdb) fin

# 继续执行至指定地址
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### 运行

```bash
# 启动／重新启动程序
(gdb) run
(gdb) r

# 从断点处继续执行
(gdb) continue
(gdb) c

# 运行至下一个断点
(gdb) continue

# 跳过 N 次断点（原文概括，参见下方说明）
(gdb) continue 5

# 跳转至指定地址（危险操作！）
(gdb) jump *0x08048456
```

> **continue 说明：** [官方手册](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Continuing-and-Stepping.html)规定，参数是当前停止位置断点的 ignore-count，仅在因断点停止时有意义；`continue 5` 并非忽略接下来任意位置的 `5` 个断点。

### 进程控制

```bash
# 终止当前进程
(gdb) kill

# 从进程分离
(gdb) detach

# 附加到进程
(gdb) attach <PID>

# 跟踪 fork 后的进程（parent/child）
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="-examining-memory"></a>
## 🔍 查看内存

### print 命令

```bash
# 打印变量
(gdb) print variable
(gdb) p variable

# 以十六进制打印
(gdb) print/x variable
(gdb) p/x $eax

# 以二进制打印
(gdb) p/t variable

# 以十进制打印
(gdb) p/d variable

# 按字符打印
(gdb) p/c $al

# 按字符串格式打印
(gdb) p/s pointer

# 打印数组
(gdb) p *array@10     # 10 个元素
```

### 查看内存的 x 命令

```bash
# 语法：x/NFU address
# N = 数量，F = 格式，U = 单位大小

# 格式：x（十六进制）、d（十进制）、u（无符号）、o（八进制）， 
#          t（二进制）、c（字符）、s（字符串）、i（指令）
# 单位：b（字节）、h（half=2）、w（word=4）、g（giant=8）

# 查看内存
(gdb) x/10x $esp           # 从 ESP 开始查看 10 个十六进制字
(gdb) x/20wx 0x08048456    # 以十六进制查看 20 个字
(gdb) x/s 0x08048456       # 指定地址处的字符串
(gdb) x/10i $eip           # 从 EIP 开始查看 10 条指令
(gdb) x/10gx $rsp          # 从 RSP 开始查看 10 个 giant 单位（8 bytes）

# 常见用法
(gdb) x/100x $esp          # 查看栈
(gdb) x/20i $eip           # 反汇编
(gdb) x/s $eax             # 字符串指针
```

> **单位说明：** [官方内存查看文档](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html)说明，省略单位时会沿用此前 `x` 命令设置的大小，初始默认才是 `w`。因此，`x/10x` 不保证始终读取字大小；原文中的 `w` 为 `4` 字节，`g` 为 `8` 字节。

### 内存区域

```bash
# 显示内存映射
(gdb) info proc mappings

# 显示节区
(gdb) maintenance info sections

# 显示文件信息
(gdb) info files
```

### 反汇编

```bash
# 反汇编函数
(gdb) disassemble main
(gdb) disas main

# 反汇编当前函数
(gdb) disassemble
(gdb) disas

# 反汇编指定地址范围
(gdb) disas 0x08048456, 0x08048500

# 结合源码反汇编（如果存在源码）
(gdb) disas /m main

# 设置汇编语法
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

> **反汇编版本说明：** [官方手册](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Machine-Code.html)已将 `/m` 标为弃用，结合源码显示时更推荐 `/s`；`set disassembly-flavor` 也仅用于 Intel x86 系列目标。上方原文命令未改写。

<a id="-registers"></a>
## 📊 寄存器

### 查看寄存器

```bash
# 全部寄存器
(gdb) info registers
(gdb) i r

# 指定寄存器
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# 全部寄存器，包括 FP/SSE
(gdb) info all-registers
```

### 修改寄存器

```bash
# 设置寄存器值
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# 设置标志位
(gdb) set $eflags = 0x246
```

### x86 寄存器参考

| 寄存器类别 | 64 位 | 32 位 | 16 位 | 8 位 | 用途 |
|----------|--------|--------|--------|-------|---------|
| 累加器 | RAX | EAX | AX | AL/AH | 返回值 |
| 基址寄存器 | RBX | EBX | BX | BL/BH | 通用 |
| 计数器 | RCX | ECX | CX | CL/CH | 循环计数 |
| 数据寄存器 | RDX | EDX | DX | DL/DH | 输入／输出、乘法 |
| 源索引 | RSI | ESI | SI | SIL | 字符串源地址 |
| 目标索引 | RDI | EDI | DI | DIL | 字符串目标地址 |
| 基址指针 | RBP | EBP | BP | BPL | 栈帧 |
| 栈指针 | RSP | ESP | SP | SPL | 栈顶 |
| 指令指针 | RIP | EIP | IP | - | 下一条指令 |

### x64 扩展寄存器

```
R8-R15 : 通用寄存器
R8D-R15D : 低 32 位
R8W-R15W : 低 16 位
R8B-R15B : 低 8 位
```

---

<a id="-stack-analysis"></a>
## 📚 调用栈分析

### 查看调用栈

```bash
# 调用栈回溯
(gdb) backtrace
(gdb) bt

# 包含局部变量的完整回溯
(gdb) backtrace full
(gdb) bt full

# 显示栈帧
(gdb) frame
(gdb) f

# 选择栈帧
(gdb) frame 2
(gdb) f 2

# 显示栈内容
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### 栈帧

```bash
# 显示栈帧信息
(gdb) info frame
(gdb) i f

# 显示局部变量
(gdb) info locals

# 显示参数
(gdb) info args

# 向上／向下切换栈帧
(gdb) up
(gdb) down
```

### 栈相关命令

```bash
# 查看返回地址
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# 查看保存的 EBP
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# 显示栈布局
(gdb) x/20wx $esp-20  # 查看前后区域
```

---

<a id="-gefpedapwndbg"></a>
## 🎨 GEF/PEDA/pwndbg

> **扩展说明：** 本节命令分别属于不同扩展，并非全部是 GDB 内置命令；部分功能还随版本或额外插件变化。可对照 [GEF 命令文档](https://hugsy.github.io/gef/)及 [GEF-Extras](https://hugsy.github.io/gef-extras/)核对。原文提示符与命令均保留，不能仅凭 `gef➤` 提示符认定某个扩展命令一定可用。

### GEF 命令

```bash
# 显示上下文（自动）
gef➤ context

# Checksec（安全保护机制）
gef➤ checksec

# 查找模式
gef➤ pattern create 200
gef➤ pattern search $rsp

# 查找字符串／数值
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# 堆分析
gef➤ heap chunks
gef➤ heap bins

# ROP gadgets（可组合的指令片段）
gef➤ ropgadget

# 格式化字符串辅助工具
gef➤ format-string-helper

# VMMap（内存映射）
gef➤ vmmap

# 解引用链
gef➤ dereference $rsp 20

# Telescope（智能解引用）
gef➤ telescope $rsp 20

# ELF 信息
gef➤ elf-info

# 栈保护 Canary 的值
gef➤ canary

# PIE/ASLR 信息
gef➤ pie
gef➤ aslr
```

### PEDA 命令

```bash
# 安全检查
peda➤ checksec

# 查找字符串
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# 创建／搜索模式
peda➤ pattern create 200
peda➤ pattern offset $eip

# ROP gadgets（可组合的指令片段）
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode（机器码载荷）
peda➤ shellcode generate x86/linux exec

# 上下文
peda➤ context all

# 汇编
peda➤ assemble
```

### pwndbg 命令

```bash
# 上下文
pwndbg> context

# Checksec
pwndbg> checksec

# 搜索
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# 循环模式（cyclic pattern）
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# 堆
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="-exploit-development"></a>
## 💥 漏洞利用开发

### 确定偏移量

```bash
# 方法 1：模式（GEF）
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# 方法 2：模式（pwntools）
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

> **模式示例勘误：** 上方方法 1 实际输入重复的 `A`，并未输入前一步生成的唯一循环模式；方法 2 也未展示创建 `input.txt`。按 [GEF](https://hugsy.github.io/gef/commands/pattern/)与 [pwntools 文档](https://docs.pwntools.com/en/stable/util/cyclic.html)，生成与查找必须使用一致的模式参数；Python 直接打印 bytes 对象还可能输出其表示形式。含 `<(...)` 的语法也依赖[启动程序所用的 shell](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Starting.html)，不能假定处处可用。

### 缓冲区溢出基础

```bash
# 检查地址空间布局随机化（ASLR）
$ cat /proc/sys/kernel/randomize_va_space
# 0=关闭，1=部分随机化，2=完全随机化

# 关闭 ASLR（用于测试）
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# 检查二进制文件的保护机制
gef➤ checksec

# 查找返回地址的位置
(gdb) x/20wx $esp     # 崩溃前
(gdb) i f             # 栈帧信息
```

### 查找 gadgets

```bash
# 查找 "/bin/sh"
gef➤ search-pattern "/bin/sh"

# 查找 system() 的地址
(gdb) p system
(gdb) p &system

# 查找 pop rdi gadget（64-bit）
gef➤ ropgadget --grep "pop rdi"

# 查找 pop; ret（32-bit）
gef➤ ropgadget --grep "pop"
```

### 写入内存

```bash
# 设置内存值
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# 写入字符串
(gdb) set {char[10]}0x08048456 = "AAAA"

# 调用函数
(gdb) call system("/bin/sh")
```

### 漏洞利用相关实用命令

```bash
# 跟踪子进程
(gdb) set follow-fork-mode child

# 在 GDB 中关闭 ASLR
(gdb) set disable-randomization on

# 显示共享库
(gdb) info sharedlibrary

# 查找 libc 基址
gef➤ vmmap libc

# 查找 PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="-quick-reference"></a>
## 📊 速查表

> **ASLR 范围说明：** [官方手册](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Starting.html)说明，`set disable-randomization on` 影响 GDB 随后启动的程序，且仅在受支持的目标上实现；它不同于修改系统级随机化设置，也不会追溯改变已附加进程的地址布局。

### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `r` | 运行程序 |
| `c` | 继续执行 |
| `b *addr` | 设置断点 |
| `si` | 单步进入指令 |
| `ni` | 单步跳过指令 |
| `fin` | 运行至函数返回 |
| `x/Nx addr` | 查看内存 |
| `p $reg` | 打印寄存器值 |
| `i r` | 查看寄存器信息 |
| `bt` | 调用栈回溯 |
| `disas` | 反汇编 |
| `q` | 退出 |

### 内存查看

| 命令 | 说明 |
|---------|-------------|
| `x/10x $esp` | 从 ESP 开始查看 10 个十六进制字 |
| `x/s addr` | 指定地址处的字符串 |
| `x/i $eip` | EIP 处的指令 |
| `x/20wx addr` | 以十六进制查看 20 个字 |
| `x/10gx $rsp` | 从 RSP 开始查看 10 个四字（qword） |

### 快速调试

```bash
# 启动程序并在 main 处中断
$ gdb -q ./program
(gdb) break main
(gdb) run

# 检查崩溃现场
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### 漏洞利用开发流程

```
1. checksec           → 检查保护机制
2. pattern create     → 创建模式
3. 使用模式运行       → 使程序崩溃
4. pattern search     → 确定偏移量
5. 查找 gadgets       → ROP 指令片段
6. 构造 payload       → 构造漏洞利用
7. 测试               → 使用 GDB 调试
```

---

## 📚 参考资源

- [GDB 官方手册](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [GEF 文档](https://hugsy.github.io/gef/)
- [pwndbg 文档](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### 相关速查表
- [Ghidra](../../Ghidra/README.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)
- [Linux 权限提升](../../Linux-PrivEsc/translations/README.zh-CN.md)

---

<p align="center">
  <b>🐛 掌握 Linux 调试技巧！</b><br>
  <i>GDB - Linux 逆向分析人员的必备工具</i>
</p>
