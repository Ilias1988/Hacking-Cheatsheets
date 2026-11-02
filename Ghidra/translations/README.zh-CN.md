<a id="-ghidra---reverse-engineering-cheatsheet"></a>
# 🔍 Ghidra 逆向工程速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
             NSA 逆向工程套件              
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [什么是 Ghidra](#-what-is-ghidra)
- [安装](#-installation)
- [界面概览](#-interface-overview)
- [创建项目](#-creating-a-project)
- [分析基础](#-analysis-basics)
- [导航](#-navigation)
- [反编译器（Decompiler）](#-decompiler)
- [数据类型与结构体](#-data-types--structures)
- [打补丁与修改](#-patching--modifying)
- [脚本编写](#-scripting)
- [键盘快捷键](#-keyboard-shortcuts)
- [速查](#-quick-reference)

---

<a id="-what-is-ghidra"></a>
## 🎯 什么是 Ghidra

**Ghidra** 是 NSA 开发的免费开源软件逆向工程（SRE）套件，提供以下功能：

- 🔍 **反汇编器** - 将二进制代码转换为汇编代码
- 🔄 **反编译器（Decompiler）** - 生成类似 C 的伪代码
- 📊 **分析** - 自动化代码分析
- 🔧 **打补丁** - 修改二进制文件
- 🐍 **脚本编写** - 使用 Python/Java 实现自动化
- 👥 **协作** - 多用户支持

<a id="supported-architectures"></a>
### 支持的架构

| 架构 | 变体 |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

<a id="supported-formats"></a>
### 支持的格式

| 格式 | 说明 |
|--------|-------------|
| ELF | Linux 可执行文件 |
| PE/COFF | Windows 可执行文件 |
| Mach-O | macOS 可执行文件 |
| 原始二进制文件（Raw Binary） | 固件、ROM |
| APK/DEX | Android 应用 |
| 以及更多格式…… |

---

<a id="-installation"></a>
## 🚀 安装

<a id="download"></a>
### 下载

```bash
# 从官方网站下载
https://ghidra-sre.org/

# 或从 GitHub Releases 下载
https://github.com/NationalSecurityAgency/ghidra/releases
```

<a id="requirements"></a>
### 要求

| 要求 | 版本 |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | 最低 4GB，推荐 8GB+ |
| **磁盘空间** | 安装需要 1GB+ |

<a id="linuxmacos"></a>
### Linux/macOS

```bash
# 安装 JDK 17+（版本要求见译注）
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# 解压 Ghidra
unzip ghidra_*.zip
cd ghidra_*

# 运行
./ghidraRun
```

<a id="windows"></a>
### Windows

```batch
:: 从 Oracle 或 Adoptium 安装 JDK 17+（版本要求见译注）
:: 解压 ghidra_*.zip
:: 运行 ghidraRun.bat
```

---

<a id="-interface-overview"></a>
## 🖥️ 界面概览

<a id="main-windows"></a>
### 主要窗口

| 窗口 | 说明 |
|--------|-------------|
| **Program Trees（程序树）** | 项目文件结构 |
| **Symbol Tree（符号树）** | 函数、标签、类 |
| **Data Type Manager（数据类型管理器）** | 已定义的数据类型 |
| **Listing（代码列表）** | 反汇编视图 |
| **反编译器（Decompiler）** | C 伪代码 |
| **Console（控制台）** | 输出／脚本 |
| **Defined Strings（已定义字符串）** | 发现的字符串 |
| **Bookmarks（书签）** | 保存的位置 |

<a id="listing-window-disassembly"></a>
### Listing 窗口（反汇编）

```
地址       |字节            |反汇编                 |注释     
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | 函数开始      
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | 栈帧       
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

<a id="-creating-a-project"></a>
## 📁 创建项目

<a id="new-project"></a>
### 新建项目

```
1. File → New Project（文件→新建项目）
2. 选择：
   - Non-Shared Project（非共享项目，本地）
   - Shared Project（共享项目，协作）
3. 选择目录
4. 输入项目名称
5. 点击 Finish（完成）
```

<a id="import-binary"></a>
### 导入二进制文件

```
1. File → Import File (I)（文件→导入文件）
2. 选择二进制程序／文件
3. 选择格式（通常会自动识别）
4. 设置选项：
   - Language/Processor（语言／处理器）
   - Compiler spec（编译器规范）
5. 点击 OK（确定）
6. Analyze? → Yes（是否分析→是，推荐）
```

<a id="analysis-options"></a>
### 分析选项

| 选项 | 说明 |
|--------|-------------|
| ASCII Strings（ASCII 字符串） | 查找字符串 |
| 反编译器（Decompiler） | 启用反编译 |
| Stack Analysis（栈分析） | 栈帧分析 |
| Reference Analysis（引用分析） | 查找交叉引用 |
| Function ID（函数识别） | 库函数匹配 |
| Call Convention（调用约定） | 识别调用约定 |

---

<a id="-analysis-basics"></a>
## 🔬 分析基础

<a id="auto-analysis"></a>
### 自动分析

```
Analysis → Auto Analyze (A)（分析→自动分析）
- 运行所有已选分析器
- 大型二进制程序可能需要较长时间
- 进度显示在任务监视器中
```

<a id="one-shot-analyzers"></a>
### 单次分析器

```
Analysis → One Shot → [Analyzer]（分析→单次分析→指定分析器）
- ASCII Strings（ASCII 字符串）
- Decompiler Parameter ID（反编译器参数识别）
- Function ID（函数识别）
- Stack Analysis（栈分析）
```

<a id="function-identification"></a>
### 函数识别

```
# 查找所有函数
Analysis → Auto Analyze → 启用 "Non-returning Functions"（不返回的函数）

# 手动创建函数
右键点击地址 → Create Function (F)（创建函数）

# 未定义的代码
右键点击 → Disassemble (D)（反汇编）
```

---

<a id="-navigation"></a>
## 🧭 导航

<a id="go-to-address"></a>
### 跳转到地址

```
# 键盘快捷键
G → 输入地址 → OK（确定）

# 示例：
0x00401000      # 十六进制地址
main            # 函数名称
entry           # 入口点
LAB_00401234    # 标签
```

<a id="navigation-shortcuts"></a>
### 导航快捷键

| 快捷键 | 操作 |
|----------|--------|
| `G` | 跳转到地址 |
| `Ctrl+G` | 跳转到地址 |
| `Alt+←` | 后退 |
| `Alt+→` | 前进 |
| `Ctrl+Shift+E` | 跳转到入口点 |

<a id="cross-references-xrefs"></a>
### 交叉引用（XREF）

```
# 查看指向当前位置的引用
右键点击 → References → Show References To（引用→显示指向此处的引用）

# 查找函数被调用的位置
选择函数 → 右键点击 → References → Show References To（引用→显示指向此处的引用）

# 键盘操作
Ctrl+Shift+F → 查找引用
```

<a id="search-functions"></a>
### 搜索功能

```
# 搜索
Search → Memory (S)           # 搜索内存
Search → For Strings          # 查找字符串
Search → For Direct References # 查找指向地址的引用
Search → Program Text         # 搜索反汇编内容

# 函数搜索
Symbol Tree → Filter（符号树→过滤）→ 输入函数名称
```

---

<a id="-decompiler"></a>
## 💻 反编译器（Decompiler）

<a id="open-decompiler"></a>
### 打开反编译器

```
Window → Decompiler（窗口→反编译器）
# 或点击 Decompiler 标签页
# 自动反编译所选函数
```

<a id="reading-decompiled-code"></a>
### 阅读反编译代码

```c
// 反编译输出示例
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

<a id="improve-decompilation"></a>
### 改善反编译结果

```
# 重命名变量
右键点击变量 → Rename Variable (L)（重命名变量）

# 更改数据类型
右键点击变量 → Retype Variable (Ctrl+L)（重新指定变量类型）

# 添加注释
; → Set EOL comment（设置行尾注释）
Ctrl+; → Set plate comment（设置顶部注释）

# 设置函数签名
右键点击函数 → Edit Function Signature（编辑函数签名）
```

<a id="decompiler-tricks"></a>
### 反编译器使用技巧

| 操作 | 方式 |
|--------|-----|
| 重命名变量 | `L` 或右键点击 |
| 更改类型 | `Ctrl+L` |
| 创建结构体 | 右键点击 → Auto Create Structure（自动创建结构体） |
| 拆分／合并变量 | 右键点击 → Split/Merge（拆分／合并） |
| 提交函数签名 | `P` |

---

<a id="-data-types--structures"></a>
## 📊 数据类型与结构体

<a id="create-structure"></a>
### 创建结构体

```
# 方法 1：从 Data Type Manager 创建
Data Type Manager → 右键点击 → New → Structure（新建→结构体）

# 方法 2：从反编译器自动创建
Decompiler → 右键点击变量 → Auto Create Structure（自动创建结构体）
```

<a id="define-structure"></a>
### 定义结构体

```c
// 结构体示例
struct Player {
    char name[32];      // 偏移 0x00
    int health;         // 偏移 0x20
    int score;          // 偏移 0x24
    float position[3];  // 偏移 0x28
};
```

<a id="apply-structure"></a>
### 应用结构体

```
# 在 Listing 视图中
选择地址 → 右键点击 → Data → Choose Data Type（数据→选择数据类型）

# 或者
按 T → 选择结构体

# 在 Decompiler 中
右键点击变量 → Retype Variable（重新指定类型）→ 选择结构体
```

<a id="arrays"></a>
### 数组

```
# 创建数组
选择起始地址 → 右键点击 → Data → Create Array（数据→创建数组）
输入元素数量
```

<a id="enums"></a>
### 枚举

```
# 创建枚举
Data Type Manager → 右键点击 → New → Enum（新建→枚举）

# 定义值
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

<a id="-patching--modifying"></a>
## 🔧 打补丁与修改

<a id="patch-instruction"></a>
### 修改指令

```
# 方法 1：使用键盘
选择指令 → Ctrl+Shift+G
输入新指令 → 按 Enter

# 方法 2：使用菜单
右键点击 → Patch Instruction（修改指令）
```

<a id="patch-data"></a>
### 修改数据

```
# 修改字节
右键点击 → Patch Data（修改数据）
输入十六进制字节

# 或者
选择字节 → Ctrl+Shift+G
```

<a id="common-patches"></a>
### 常见补丁

| 原始内容 | 修改后 | 用途 |
|----------|---------|---------|
| `JE` | `JMP` | 始终跳转 |
| `JNE` | `JMP` | 始终跳转 |
| `JE` | `NOP` | 不跳转 |
| `CALL` | `NOP NOP NOP NOP NOP` | 跳过调用 |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP 短跳转 |

<a id="export-patched-binary"></a>
### 导出修改后的二进制文件

```
File → Export Program（文件→导出程序）
Format: Binary（格式：二进制）
选择目标位置
```

<a id="nop-sled"></a>
### NOP 滑道

```
# 填充 NOP
选择范围 → 右键点击 → Clear Code Block（清除代码块）
然后：选择 → 右键点击 → Patch → Fill with NOPs（补丁→填充 NOP）
```

---

<a id="-scripting"></a>
## 🐍 脚本编写

<a id="script-manager"></a>
### 脚本管理器（Script Manager）

```
Window → Script Manager（窗口→脚本管理器）
# 或者：Tools → Script Manager（工具→脚本管理器）
```

<a id="run-script"></a>
### 运行脚本

```
Script Manager → 选择脚本 → Run（绿色播放按钮）
# 或者双击脚本
```

<a id="python-scripting-ghidra-python"></a>
### Python 脚本（Ghidra Python）

```python
# 示例：列出所有函数
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = 正向遍历

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

<a id="common-script-operations"></a>
### 常见脚本操作

```python
# 获取当前地址
currentAddress

# 获取当前程序
currentProgram

# 获取函数管理器
fm = currentProgram.getFunctionManager()

# 获取 Listing（反汇编列表）
listing = currentProgram.getListing()

# 获取地址处的指令
instr = listing.getInstructionAt(addr)

# 获取地址处的字节
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# 创建标签
createLabel(addr, "my_label", True)

# 添加注释
setEOLComment(addr, "My comment")

# 获取引用
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

<a id="useful-built-in-scripts"></a>
### 实用内置脚本

| 脚本 | 用途 |
|--------|---------|
| FindCrypt | 查找密码算法常量 |
| FunctionID | 匹配库函数 |
| ExportFunctionInfoScript | 导出函数列表 |
| FindStrings | 查找字符串引用 |
| SearchMemory | 搜索模式 |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ 键盘快捷键

<a id="navigation"></a>
### 导航

| 快捷键 | 操作 |
|----------|--------|
| `G` | 跳转到地址 |
| `Alt+←` | 后退 |
| `Alt+→` | 前进 |
| `Ctrl+Shift+E` | 入口点 |
| `Ctrl+E` | 导出表格 |
| `N` | 下一个未定义位置 |

<a id="analysis"></a>
### 分析

| 快捷键 | 操作 |
|----------|--------|
| `F` | 创建函数 |
| `D` | 反汇编 |
| `C` | 清除定义 |
| `P` | 创建指针 |
| `T` | 应用数据类型 |
| `A` | 自动分析 |

<a id="editing"></a>
### 编辑

| 快捷键 | 操作 |
|----------|--------|
| `L` | 重命名（标签／变量） |
| `;` | 设置行尾注释 |
| `Ctrl+;` | 设置顶部注释（plate comment） |
| `Ctrl+L` | 重新指定变量类型 |
| `Ctrl+Shift+G` | 修改指令 |

<a id="search"></a>
### 搜索

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+Shift+F` | 查找引用 |
| `Ctrl+F` | 搜索文本 |
| `S` | 搜索内存 |
| `Ctrl+B` | 搜索字节 |

<a id="windows-1"></a>
### 窗口

| 快捷键 | 操作 |
|----------|--------|
| `Ctrl+D` | 切换反编译器显示 |
| `Ctrl+T` | 切换数据类型管理器显示 |
| `Ctrl+S` | 符号树 |

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="analysis-workflow"></a>
### 分析流程

```
1. 导入二进制文件  → File → Import（文件→导入）
2. 自动分析        → Analysis → Auto Analyze（分析→自动分析，接受默认设置）
3. 查找入口／main  → G → main 或 entry
4. 查看函数        → Symbol Tree → Functions（符号树→函数）
5. 检查字符串      → Search → For Strings（搜索→字符串）
6. 跟踪执行流程    → 跟随调用／跳转
7. 重命名符号      → 在函数／变量上按 L
8. 记录发现        → 添加注释（;）
```

<a id="common-tasks"></a>
### 常见任务

| 任务 | 方式 |
|------|-----|
| 查找 main() | `G` → `main` 或在 Symbol Tree（符号树）搜索 |
| 查看字符串 | Search → For Strings（搜索→字符串） |
| 查找密码算法 | 运行 FindCrypt 脚本 |
| 检查导入项 | Symbol Tree → Imports（符号树→导入项） |
| 查找交叉引用 | 选择目标 → Ctrl+Shift+F |
| 创建函数 | 选择地址 → `F` |
| 反编译 | Window → Decompiler（窗口→反编译器） |

<a id="reversing-checklist"></a>
### 逆向分析清单

```
☐ 识别文件类型（PE、ELF 等）
☐ 运行自动分析
☐ 查找入口点／main
☐ 检查字符串中的线索
☐ 识别值得关注的函数
☐ 梳理程序流程
☐ 分析可疑函数
☐ 通过注释记录发现
☐ 重命名函数／变量
☐ 按需创建结构体
```

<a id="function-calling-conventions"></a>
### 函数调用约定

| 约定 | 返回值 | 参数 | 调用者清理 |
|------------|--------|------|----------------|
| **cdecl** | EAX | 栈（R→L） | 调用者 |
| **stdcall** | EAX | 栈（R→L） | 被调用者 |
| **fastcall** | EAX | ECX, EDX, Stack（栈） | 被调用者 |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | 调用者 |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | 调用者 |

---

<a id="-resources"></a>
## 📚 参考资料

- [Ghidra 官方网站](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Ghidra 文档](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

<a id="related-cheatsheets"></a>
### 相关速查表
- [Binwalk](../../Binwalk/translations/README.zh-CN.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)
- [Volatility](../../Volatility/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔍 掌握逆向工程！</b><br>
  <i>Ghidra：NSA 为安全社区提供的工具</i>
</p>

> 译注：JDK 要求取决于 Ghidra 版本，原文 17+ 不是通用保证；[官方仓库当前主分支 README](https://github.com/NationalSecurityAgency/ghidra)列出 JDK 25，应以所下载版本的 Getting Started 文档为准。Python 示例使用 f-string，需要兼容 Python 3 的运行环境；[官方 PyGhidra 文档](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/src/main/py/README.md)介绍了 CPython 3 支持。addr、length 等示例变量仍需由脚本上下文提供。

> 译注：[Ghidra 官方入门指南](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html)说明快捷键可按工具配置修改，菜单也受焦点与插件影响；本页按原文保留，不能将同一按键在不同视图中的含义混用。指南还指出 Raw Bytes／二进制导出通常不保留原文件布局，不能直接等同于生成可运行的原格式 PE／ELF。补丁须考虑实际指令长度与编码，调用约定表也仅概括常见整数／指针情形；原始字节和代码示例均保留原样。
