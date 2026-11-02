# 🔧 x64dbg — Windows 调试器速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
              Windows x64/x32 调试器
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 目录

- [什么是 x64dbg](#-what-is-x64dbg)
- [安装](#-installation)
- [界面概览](#-interface-overview)
- [基本用法](#-basic-usage)
- [断点](#-breakpoints)
- [单步与执行](#-stepping--execution)
- [内存操作](#-memory-operations)
- [寄存器](#-registers)
- [栈分析](#-stack-analysis)
- [补丁修改](#-patching)
- [脚本](#-scripting)
- [键盘快捷键](#-keyboard-shortcuts)
- [快速参考](#-quick-reference)

---

<a id="-what-is-x64dbg"></a>

## 🎯 什么是 x64dbg

**x64dbg** 是适用于 Windows 的开源 x64/x32 调试器，延续了 OllyDbg 的设计理念，并提供现代化功能：

- 🔍 **用户态调试** — 调试 Windows 应用程序
- 🦠 **恶意软件分析** — 分析可疑可执行文件
- 💥 **漏洞利用开发** — 研究 Windows 漏洞利用
- 🔧 **逆向工程** — 理解二进制程序行为
- 🧩 **插件支持** — 通过插件扩展功能

### 功能

| 功能 | 说明 |
|---------|-------------|
| **GUI** | 现代化、可自定义的界面 |
| **x64 支持** | 完整的 64 位调试支持 |
| **脚本** | 内置脚本语言 |
| **插件** | 丰富的插件生态 |
| **符号** | 支持 PDB 符号 |
| **开源** | 免费且持续开发 |

---

<a id="-installation"></a>

## 🚀 安装

### 下载

```
# 官方网站
https://x64dbg.com/

# GitHub 发布版本
https://github.com/x64dbg/x64dbg/releases

# 下载：snapshot_YYYY-MM-DD_HH-MM.zip
```

### 设置

```
1. 将 ZIP 解压到固定位置（例如 C:\x64dbg\）
2. 运行 x96dbg.exe（两种调试器的启动器）
   - x32dbg.exe → 32 位调试
   - x64dbg.exe → 64 位调试
3. （可选）添加到 PATH 或创建快捷方式
```

### 推荐插件

| 插件 | 用途 |
|--------|---------|
| **ScyllaHide** | 对抗反调试检测 |
| **SwissArmyKnife** | 多种实用工具 |
| **x64dbgpy** | Python 脚本支持 |
| **TitanHide** | 内核层反调试相关功能 |
| **OllyDumpEx** | 内存导出 |
| **Multiline Ultimate Assembler** | 多行补丁编辑 |

### 安装插件

```
1. 下载插件（DLL 文件）
2. 复制到：x64dbg\release\x32\plugins\（32-bit）
           x64dbg\release\x64\plugins\（64-bit）
3. 重新启动 x64dbg
```

---

<a id="-interface-overview"></a>

## 🖥️ 界面概览

### 主要窗口

| 窗口 | 说明 | 快捷键 |
|--------|-------------|----------|
| **CPU** | 反汇编视图 | Alt+C |
| **Log** | 调试输出 | Alt+L |
| **Memory Map** | 内存区域 | Alt+M |
| **Call Stack** | 调用栈回溯 | Alt+K |
| **Breakpoints** | 所有断点 | Alt+B |
| **Symbols** | 已加载符号 | Alt+E |
| **Threads** | 线程列表 | Alt+T |
| **Handles** | 句柄列表 | Alt+H |

### CPU 窗口面板

```
┌──────────────────────────┬──────────────────────────┐
│  Disassembly（反汇编）   │   Registers（寄存器）    │
│                          │                          │
├──────────────────────────┼──────────────────────────┤
│ Hex Dump（十六进制转储） │       Stack（栈）        │
│                          │                          │
└──────────────────────────┴──────────────────────────┘
```

### 状态栏

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

<a id="-basic-usage"></a>

## 💻 基本用法

### 开始调试

```
# 打开可执行文件
File → Open（打开，F3）

# 附加到进程
File → Attach（附加，Alt+A）

# 打开并运行
Debug → Run（运行，F9）

# 重新开始调试
Debug → Restart（重新启动，Ctrl+F2）

# 停止调试
Debug → Stop（停止，Alt+F2）
```

### 命令栏

```
# 在底部命令栏输入命令

# 转到指定地址
g 0x401000
goto 0x401000

# 转到表达式对应位置
g GetProcAddress
g kernel32.LoadLibraryA

# 搜索字符串
/s "password"

# 搜索十六进制字节
/x 90 90 90 90

# 搜索字节模式
/p "8B ?? 89"
```

> **译注：**本页保留了部分不适用或混用其他调试器语法的原始示例。x64dbg 的 [`g`／`r`](https://help.x64dbg.com/en/latest/commands/debug-control/run.html)是继续执行的别名，`g addr`会设置一次性断点后运行，并非只移动视图；查看反汇编可用 [`disasm`](https://help.x64dbg.com/en/latest/commands/gui/disasm.html)，赋值可用 [`mov`／`set`](https://help.x64dbg.com/en/latest/commands/general-purpose/mov.html)。`/s`、`/x`、`/p` 也不能当作通用 x64dbg 内置搜索命令，具体语法见 [官方命令目录](https://help.x64dbg.com/en/latest/commands/index.html)。原代码保持不变。

### 导航

```
# 转到指定地址
Ctrl+G → 输入地址

# 转到入口点
Debug → Entry Point（入口点）

# 在反汇编视图中跟随
右键 → Follow in Disassembler（在反汇编视图中跟随）

# 后退
-（减号键）

# 前进
+（加号键）
```

---

<a id="-breakpoints"></a>

## 🔴 断点

### 软件断点（INT3）

```
# 切换当前地址的断点
F2

# 通过命令设置断点
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# 设置条件断点
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# 在模块加载时中断
bp $module(kernel32)
```

> **译注：**[`bp`](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html)的第二个参数是断点名称，不是条件，第三个参数也不是命中次数。条件应通过 [SetBreakpointCondition](https://help.x64dbg.com/en/latest/commands/conditional-breakpoint-control/SetBreakpointCondition.html)或 Edit breakpoint 界面设置；次数可用 [命中计数器表达式](https://help.x64dbg.com/en/latest/introduction/ConditionalBreakpoint.html)。因此本页相应原始 `bp` 示例不能按其注释理解；模块加载事件使用 [`bpdll`](https://help.x64dbg.com/en/latest/commands/breakpoint-control/LibrarianSetBreakpoint.html)一类事件断点。

### 硬件断点

```
# 设置硬件断点（最多 4 个）
右键 → Breakpoint → Hardware → Execute（执行）
右键 → Breakpoint → Hardware → Access（访问）
右键 → Breakpoint → Hardware → Write（写入）

# 通过命令
bph 0x401000, x, 1    # 执行
bph 0x401000, r, 4    # 读取（4 字节）
bph 0x401000, w, 4    # 写入（4 字节）

# 大小：1、2、4 或 8 字节
```

> **译注：**上面硬件断点的 `r` 表示读写访问，而非只读；8 字节大小仅适用于 x64，地址还需按大小对齐，见 [bph 文档](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html)。下方 [bpm](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetMemoryBPX.html)基于 GUARD_PAGE，作用于所在内存区域，不是只监视单个地址。原示例保留。

### 内存断点

```
# 在内存访问时中断
在转储视图中右键 → Breakpoint → Memory → Access（访问）
在转储视图中右键 → Breakpoint → Memory → Write（写入）

# 通过命令
bpm 0x401000, r       # 读取
bpm 0x401000, w       # 写入
bpm 0x401000, x       # 执行
```

### 条件断点

```
# 条件为真时中断
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# 按调用次数中断
bp 0x401000, "", 5    # 第 5 次命中时中断

# 记录日志而不中断
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### 管理断点

```
# 列出所有断点
Breakpoints 窗口（断点，Alt+B）

# 删除断点
bc 0x401000          # 删除指定断点
bc                   # 删除所有断点

# 启用／禁用
bd 0x401000          # 禁用
be 0x401000          # 启用

# 禁用所有断点
bd *

# 启用所有断点
be *
```

---

<a id="-stepping--execution"></a>

## ▶️ 单步与执行

### 单步执行

| 操作 | 快捷键 | 说明 |
|--------|----------|-------------|
| Step Into（单步进入） | F7 | 执行一条指令 |
| Step Over（单步步过） | F8 | 步过函数调用 |
| Step Out（步出） | Ctrl+F9 | 运行至返回 |
| Run to Selection（运行到选中位置） | F4 | 运行到光标位置 |
| Run to User Code（运行到用户代码） | Alt+F9 | 离开系统代码 |

### 运行

| 操作 | 快捷键 | 说明 |
|--------|----------|-------------|
| Run（运行） | F9 | 继续执行 |
| Run Skipping（跳过运行） | Ctrl+F7 | 跳过当前指令 |
| Pause（暂停） | F12 | 中断执行 |
| Restart（重新启动） | Ctrl+F2 | 重新开始调试 |
| Close（关闭） | Alt+F2 | 停止调试 |

> **译注：**原文把 Ctrl+F7 同时列为“Run Skipping”和“Trace Into”，两处说明有冲突；菜单和快捷键也可能因版本或自定义设置不同而变化，应以实际界面为准。

### 跟踪

```
# 跟踪进入（记录所有指令）
Debug → Trace Into（跟踪进入，Ctrl+F7）

# 跟踪步过
Debug → Trace Over（跟踪步过，Ctrl+F8）

# 记录跟踪
Debug → Trace Record（记录跟踪）

# 停止跟踪
Debug → Stop Trace（停止跟踪）
```

### 跳过指令

```
# 跳过当前指令而不执行
右键 → Set New Origin Here（将当前位置设为新执行起点）

# 或者
Ctrl+Shift+O
```

---

<a id="-memory-operations"></a>

## 🔍 内存操作

### 内存转储视图

```
# 在转储视图中跟随地址
右键 → Follow in Dump → Address/Selection（地址／选中内容）

# 转到转储视图中的指定地址
Ctrl+G（在转储窗口中）

# 更改转储显示类型
右键 → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]（显示格式）
```

### 内存映射

```
# 打开 Memory Map（内存映射）
View → Memory Map（内存映射，Alt+M）

# 显示所有内存区域：
- Address（地址）
- Size（大小）
- Section（区段）
- Type（类型：Image/Private/Mapped）
- Protection（保护：Execute/Read/Write）
- Module name（模块名称）
```

### 搜索内存

```
# 在命令栏搜索
/s "string"           # ASCII 字符串
/s L"string"          # Unicode 字符串
/x 90 90 90           # 十六进制字节
/p "8B ?? 89"         # 含通配符的模式

# 查找引用
右键 → Find references to → Selected Address（所选地址的引用）

# 查找字符串
右键 → Search for → All Strings（所有字符串）
```

### 内存保护

```
# 查看保护属性
Memory Map → Protection 列（保护属性）

# 常见保护属性
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### 导出内存

```
# 将内存导出到文件
在 Memory Map 中右键 → Dump Memory to File（导出内存到文件）

# 通过插件导出
Plugins → OllyDumpEx → Dump（导出）
```

---

<a id="-registers"></a>

## 📊 寄存器

### 寄存器面板

```
# 通用寄存器（x64）
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# 通用寄存器（x32）
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# 段寄存器
CS, DS, SS, ES, FS, GS

# 标志位（EFLAGS/RFLAGS）
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### 修改寄存器

```
# 双击寄存器进行编辑
# 或右键选择 Modify Value（修改值）

# 通过命令栏
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # 设置零标志
```

### 重要标志位

| 标志位 | 名称 | 置位条件／含义 |
|------|------|----------|
| ZF | 零标志 | 结果为零 |
| CF | 进位标志 | 无符号溢出 |
| SF | 符号标志 | 结果为负 |
| OF | 溢出标志 | 有符号溢出 |
| DF | 方向标志 | 字符串操作方向 |
| TF | 陷阱标志 | 单步执行 |

### 切换标志位

```
# 单击寄存器面板中的标志字母
# 或使用命令：
r zf=!zf         # 切换零标志
r cf=0           # 清除进位标志
r sf=1           # 设置符号标志
```

---

<a id="-stack-analysis"></a>

## 📚 栈分析

### 栈面板

```
# 显示以下栈内容：
- Address（地址）
- Value（值）
- Comments（注释，若有）

# 右键菜单选项：
- Follow DWORD in Disassembler（在反汇编视图中跟随 DWORD）
- Follow DWORD in Dump（在转储视图中跟随 DWORD）
- Modify（修改）
```

### 调用栈

```
# 打开 Call Stack（调用栈）
View → Call Stack（调用栈，Alt+K）

# 显示：
- Return Address（返回地址）
- From（调用方）
- To（被调用方）
- Size（大小）
- Comment（注释）
```

### 栈操作

```
# 转到栈地址
右键 → Follow DWORD in Stack（在栈中跟随 DWORD）

# 修改栈中的值
右键 → Modify → Edit（编辑）

# 模拟 Push/Pop
r esp=esp-4      # 入栈（32-bit）
r esp=esp+4      # 出栈（32-bit）
r rsp=rsp-8      # 入栈（64-bit）
r rsp=rsp+8      # 出栈（64-bit）
```

> **译注：**上方只调整 `esp`／`rsp` 的表达式并不等同于完整 PUSH／POP：它没有同时完成栈内存或目标寄存器的数据传送。参见 [push 命令](https://help.x64dbg.com/en/latest/commands/general-purpose/push.html)。原演示保留。

### 栈布局（32 位 stdcall）

```
[ESP]       → 返回地址
[ESP+4]     → Arg1（参数）
[ESP+8]     → Arg2（参数）
[ESP+C]     → Arg3（参数）
...

执行 PUSH EBP; MOV EBP, ESP 后：
[EBP]       → 保存的 EBP
[EBP+4]     → 返回地址
[EBP+8]     → Arg1（参数）
[EBP-4]     → Local1（局部变量）
```

---

<a id="-patching"></a>

## 🔧 补丁修改

### 汇编指令

```
# 右键单击指令 → Assemble（汇编，Space）
# 输入新指令

# 示例：
NOP                    # 空操作
JMP 0x401020           # 跳转
MOV EAX, 0             # 清零 EAX
XOR EAX, EAX           # 清零 EAX（更短）
RET                    # 返回
```

### 填充 NOP

```
# 选中范围 → 右键 → Binary → Fill with NOPs（填充 NOP）

# 或选中后按 Space：
# 输入多个 NOP
```

### 二进制编辑

```
# 直接编辑字节
右键 → Binary → Edit（编辑）

# 填充指定值
右键 → Binary → Fill（填充）

# 复制／粘贴二进制数据
右键 → Binary → Copy（复制）
右键 → Binary → Paste（粘贴）
```

### 常用补丁

| 修改前 | 修改后 | 用途 |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | 反转条件 |
| `75 XX` (JNE) | `74 XX` (JE) | 反转条件 |
| `74 XX` (JE) | `EB XX` (JMP) | 始终跳转 |
| `74 XX` (JE) | `90 90` (NOP) | 不再跳转 |
| `0F 84` (JE) | `0F 85` (JNE) | 反转条件（近跳转） |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | 跳过调用 |

### 保存补丁

```
# 方法 1：Patch File（补丁文件）
File → Patch File（补丁文件）

# 方法 2：导出补丁
右键 → Copy → Selection（选中内容）
# 粘贴到十六进制编辑器

# 方法 3：插件
Plugins → Export modified executable（导出修改后的可执行文件）
```

---

<a id="-scripting"></a>

## 📝 脚本

### 命令语法

```
# 变量
$var = 0x401000

# 比较
cmp eax, 0

# 条件分支
je label
jne label

# 标签
label:

# 注释
; 这是一条注释
```

### 基础脚本

```
; 查找 WinMain 的脚本
mov $addr, 0x401000        ; 起始地址
loop:
  find $addr, "8B EC"       ; 查找 PUSH EBP; MOV EBP, ESP
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

> **译注：**这段脚本是有缺陷的原始示意：[`find`](https://help.x64dbg.com/en/latest/commands/searching/find.html)返回匹配的虚拟地址，`add $addr, $result`会把两个地址相加；`8B EC`也仅对应 MOV EBP, ESP，并不包含 PUSH EBP，且不能唯一识别 WinMain。原代码与标签保留。另外，[官方命令语法](https://help.x64dbg.com/en/latest/commands/index.html)使用分号分隔命令，文中的分号注释不应直接视为可执行脚本语法。

### 脚本命令

| 命令 | 说明 |
|---------|-------------|
| `msg "text"` | 显示消息 |
| `log "text"` | 输出到日志控制台 |
| `find addr, "pattern"` | 搜索字节模式 |
| `bp addr` | 设置断点 |
| `bc addr` | 清除断点 |
| `sti` | 单步进入 |
| `sto` | 单步步过 |
| `run` | 运行 |
| `ret` | 返回／停止 |

### 运行脚本

```
# 加载并运行脚本
Scripts → Run Script（运行脚本）

# 或通过命令栏
scr "scriptfile.txt"
```

---

<a id="-keyboard-shortcuts"></a>

## ⌨️ 键盘快捷键

### 常规

| 快捷键 | 操作 |
|----------|--------|
| F3 | 打开文件 |
| Alt+A | 附加到进程 |
| Ctrl+F2 | 重新启动 |
| Alt+F2 | 停止调试 |
| Ctrl+G | 转到指定地址 |
| Ctrl+F | 查找 |

### 调试

| 快捷键 | 操作 |
|----------|--------|
| F2 | 切换断点 |
| F4 | 运行到选中位置 |
| F7 | 单步进入 |
| F8 | 单步步过 |
| F9 | 运行 |
| F12 | 暂停 |
| Ctrl+F9 | 运行至返回 |

### 导航

| 快捷键 | 操作 |
|----------|--------|
| - | 后退 |
| + | 前进 |
| * | 转到 EIP/RIP |
| Enter | 跟随调用／跳转 |
| Escape | 后退 |

### 视图

| 快捷键 | 操作 |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log（日志） |
| Alt+M | Memory Map（内存映射） |
| Alt+K | Call Stack（调用栈） |
| Alt+B | Breakpoints（断点） |
| Alt+E | Symbols（符号） |
| Alt+T | Threads（线程） |

### 编辑

| 快捷键 | 操作 |
|----------|--------|
| Space | 汇编 |
| ; | 添加注释 |
| : | 添加标签 |
| N | 重命名 |

---

<a id="-quick-reference"></a>

## 📊 快速参考

### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `bp addr` | 设置断点 |
| `bc addr` | 清除断点 |
| `bph addr, x` | 硬件断点 |
| `g addr` | 转到指定地址 |
| `r reg=val` | 设置寄存器 |
| `sti` | 单步进入 |
| `sto` | 单步步过 |
| `run` | 运行 |

### 常见字节模式

| 字节 | 指令 |
|-------|-------------|
| `90` | NOP |
| `CC` | INT3（断点） |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

### 分析流程

```
1. 加载可执行文件                   → F3
2. 在入口点设置断点                 → 入口点处按 F2
3. 运行到断点                       → F9
4. 单步执行代码                     → F7/F8
5. 查看寄存器／内存                 → 使用面板
6. 设置其他断点
7. 继续分析
8. 按需修改补丁                     → Space
9. 保存修改后的文件
```

### 绕过反调试

```
# 安装 ScyllaHide 插件
Plugins → ScyllaHide → Options → Select profile（选择配置）

# 手动补丁：
- 用 NOP 替换 IsDebuggerPresent 调用
- 修改 PEB.BeingDebugged
- 正确处理异常
```

---

## 📚 参考资料

- [x64dbg 官网](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [x64dbg 文档](https://help.x64dbg.com/)
- [x64dbg 插件](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### 相关速查表
- [Ghidra](../../Ghidra/README.md)
- [GDB](../../GDB/README.md)
- [Windows 权限提升](../../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🔧 掌握 Windows 调试！</b><br>
  <i>x64dbg — 延续 OllyDbg 理念的现代调试器</i>
</p>
