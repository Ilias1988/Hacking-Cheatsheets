# 🧠 Volatility — 内存取证速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                               内存取证框架
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 目录

- [什么是 Volatility](#-what-is-volatility)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [镜像识别](#-image-identification)
- [进程分析](#-process-analysis)
- [网络分析](#-network-analysis)
- [注册表分析](#-registry-analysis)
- [恶意软件分析](#-malware-analysis)
- [用户与凭据分析](#-user--credential-analysis)
- [文件与 DLL 分析](#-file--dll-analysis)
- [时间线分析](#-timeline-analysis)
- [Volatility 3](#-volatility-3)

---

<a id="-what-is-volatility"></a>

## 🎯 什么是 Volatility

**Volatility** 是用于分析 RAM 转储的开源内存取证框架，主要用于：

- 🔍 **事件响应** — 识别活动中的恶意软件
- 🦠 **恶意软件分析** — 提取隐藏进程
- 🔐 **凭据恢复** — 提取密码／哈希
- 📊 **时间线分析** — 重建事件经过
- 🕵️ **威胁狩猎** — 查找入侵指标（IOC）

### 支持的内存镜像格式

| 格式 | 说明 |
|--------|-------------|
| Raw (.raw, .mem) | 原始内存转储 |
| EWF (.E01) | EnCase 格式 |
| AFF | 高级取证格式（Advanced Forensics Format） |
| 崩溃转储 | Windows 崩溃转储 |
| VMware (.vmem) | VMware 快照 |
| VirtualBox (.sav) | VirtualBox 快照 |
| HPAK (.hpak) | FDPro 格式 |
| LiME (.lime) | Linux Memory Extractor |

---

<a id="-installation"></a>

## 🚀 安装

> **译注：**以下命令跨越 Volatility 2 与 3，依赖项、插件和镜像支持并不通用。Volatility 2 的仓库已归档；原安装示例保留供历史环境参考。Volatility 3 当前的安装与依赖说明见 [官方仓库](https://github.com/volatilityfoundation/volatility3)。

### Volatility 2（旧版）

```bash
# 克隆仓库
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# 安装依赖
pip install pycrypto distorm3 yara-python pillow openpyxl

# 运行
python vol.py -h
```

### Volatility 3（当前版本）

```bash
# 通过 pip 安装
pip3 install volatility3

# 或克隆仓库
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# 运行
python3 vol.py -h
vol -h  # 如果通过 pip 安装
```

### 下载符号表（Vol3）

```bash
# Windows 符号
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Linux 符号
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Mac 符号
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# 解压到 volatility3/symbols/
```

---

<a id="-basic-usage"></a>

## 💻 基本用法

> **译注：**Volatility 3 使用符号表与自动配置机制，“自动检测配置档案”只是简化说法。Windows 可自动获取缺失的匹配符号；Linux/macOS 通常仍需匹配目标内核的符号，Linux banner 必须精确匹配，通用符号包并不能覆盖所有镜像。符号 ZIP 包也可直接放入符号目录，无须一律解压。参见 [符号表文档](https://volatility3.readthedocs.io/en/stable/symbol-tables.html)。

### Volatility 2 语法

```bash
# 通用语法
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# 示例
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Volatility 3 语法

```bash
# 通用语法（自动检测配置档案）
vol -f <memory_dump> <plugin>

# 示例
vol -f memory.raw windows.pslist
```

### 常用选项

```bash
# Volatility 2
-f FILE          # 内存转储文件
--profile=PROFILE # 操作系统配置档案
-h               # 帮助
--info           # 列出可用插件

# Volatility 3
-f FILE          # 内存转储文件
-o DIR           # 输出目录
-r RENDERER      # 输出格式（pretty, json, csv）
-p PLUGIN_DIR    # 附加插件
-h               # 帮助
```

---

<a id="-image-identification"></a>

## 🔍 镜像识别

### 识别配置档案（Profile，Vol2）

```bash
# 获取建议的配置档案
python vol.py -f memory.raw imageinfo

# 更详细的信息
python vol.py -f memory.raw kdbgscan
```

### 识别操作系统（Vol3）

```bash
# 自动检测（Vol3 不需要手动指定配置档案）
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

> **译注：**上面的 `linux.info` 和 `mac.info` 不能视为所有 Volatility 3 安装中均提供的插件；应核对相应版本的 [Linux](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.linux.html)与 [Mac](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.mac.html)插件列表。原命令保留。

### 常见配置档案（Vol2）

| 操作系统 | 配置档案 |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-process-analysis"></a>

## ⚙️ 进程分析

### 列出进程

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # 树状视图
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # 隐藏进程

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### 进程详情

```bash
# 命令行参数
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# 环境变量
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# 进程句柄
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# 已加载的 DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### 检测隐藏进程

```bash
# 比较 pslist 与 psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# 检测进程镂空（Process hollowing）
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

> **译注：**[`psscan`](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#psscan) 可发现未链接或已终止进程的残留，命中不等于进程仍活动或必然恶意；应结合其他视图分析。[hollowfind](https://github.com/monnappa22/HollowFind)是第三方插件，不能假定标准安装自带。原示例保留。

### 导出进程

```bash
# 导出可执行文件
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# 导出内存
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

<a id="-network-analysis"></a>

## 🌐 网络分析

### 网络连接

```bash
# 活动连接（Vol2）
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### 输出字段

| 字段 | 说明 |
|-------|-------------|
| Offset | 内存偏移量 |
| Proto | TCP/UDP |
| LocalAddr | 本地 IP:端口 |
| ForeignAddr | 远程 IP:端口 |
| State | 连接状态 |
| PID | 进程 ID |
| Owner | 进程名称 |

### 网络痕迹

```bash
# 套接字
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

<a id="-registry-analysis"></a>

## 📝 注册表分析

### 列出注册表配置单元（Hive）

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### 导出注册表

```bash
# 输出指定注册表项
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# 将配置单元导出到文件
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### 重要注册表项

```bash
# 自动运行注册表项
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# 服务
printkey -K "SYSTEM\CurrentControlSet\Services"

# 用户账户
printkey -K "SAM\Domains\Account\Users"

# 最近使用的文件
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist（已执行的程序）

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

<a id="-malware-analysis"></a>

## 🦠 恶意软件分析

### Malfind（注入代码）

```bash
# 查找注入代码
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# 导出到文件
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### SSDT 挂钩

```bash
# 系统服务描述符表（SSDT）
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# 与干净系统比较
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### IDT/GDT 分析

```bash
# 中断描述符表（IDT）
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# 全局描述符表（GDT）
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### 驱动程序分析

```bash
# 列出驱动程序
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# 模块列表
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# 已卸载的模块
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# 驱动程序 IRP 挂钩
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### YARA 扫描

```bash
# 使用 YARA 规则扫描
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

> **译注：**上面 Volatility 2 的 YARA 参数示例将用途写反：`-Y` 接受字符串／规则表达式，`-y` 接受规则文件。原命令保留供对照，参见 [官方插件源码](https://github.com/volatilityfoundation/volatility/blob/master/volatility/plugins/malware/malfind.py)。

### API 挂钩

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

<a id="-user--credential-analysis"></a>

## 🔐 用户与凭据分析

### 用户信息

```bash
# 获取用户 SID
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# 最后登录的用户
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### 密码哈希

```bash
# 导出 SAM 哈希
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# LSA 机密
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# 缓存凭据
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Mimikatz 集成

```bash
# 提取凭据（mimikatz 插件）
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3（内置）
vol -f memory.raw windows.lsadump
```

> **译注：**`windows.lsadump` 的功能是导出 LSA 机密，不等同于完整 Mimikatz 集成或通用的 LSASS 明文凭据提取。较新版本将其放在 `windows.registry.lsadump`，旧名称属于弃用兼容入口，参见 [旧入口](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.lsadump.html)与 [当前插件](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.registry.lsadump.html)。

### 剪贴板

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

<a id="-file--dll-analysis"></a>

## 📁 文件与 DLL 分析

### 文件扫描

```bash
# 查找内存中的文件
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# 筛选指定文件
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### 导出文件

> **译注：**下方两条按偏移导出的命令并非地址层面的直接替换：Vol2 `-Q` 接收物理偏移，Vol3 `--virtaddr` 接收虚拟地址，不能把同一个 `<OFFSET>` 不加区分地复用。参见 [Vol2 参数](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#dumpfiles)与 [Vol3 参数定义](https://volatility3.readthedocs.io/en/stable/_modules/volatility3/plugins/windows/dumpfiles.html)。

```bash
# 按偏移量导出文件
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# 导出所有文件
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### DLL 分析

```bash
# 列出 DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# 指定进程
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# 导出 DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### 内存字符串

```bash
# 提取字符串
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# 指定进程的字符串
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

<a id="-timeline-analysis"></a>

## 📅 时间线分析

> **译注：**上面的 `strings -s strings.txt` 用于把已有字符串及其物理偏移映射回进程，不是直接从镜像生成字符串文件；输入需先准备为规定的偏移格式。参见 [Volatility 2 命令参考](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#strings)。

### 创建时间线

```bash
# 创建时间线
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### MFT 分析

```bash
# 主文件表（MFT）
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

<a id="-volatility-3"></a>

## 🔄 Volatility 3

### 主要差异

| 特性 | Vol2 | Vol3 |
|---------|------|------|
| 配置档案 | 手动指定（`--profile=`） | 自动检测 |
| 插件命名 | `pslist` | `windows.pslist` |
| 性能 | 较慢 | 较快 |
| Python | Python 2.7 | Python 3 |
| 输出 | 仅文本 | JSON、CSV 等 |

> **译注：**表中“Vol2 仅文本输出”不准确：Volatility 2 也有其他渲染格式，具体依插件而定，见 [官方输出格式说明](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage#choosing-an-output-format)。插件名也随版本调整，例如较新的 [malfind](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.malware.malfind.html)位于 `windows.malware.malfind`；原参考命令保留。

### Vol3 插件参考

```bash
# 进程
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# 网络
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# 注册表
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# 恶意软件
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# 凭据
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# 文件
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Linux 插件（Vol3）

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 快速参考

### 常用命令

| 用途 | Vol2 命令 | Vol3 命令 |
|---------|--------------|--------------|
| 识别镜像 | `imageinfo` | `windows.info` |
| 列出进程 | `pslist` | `windows.pslist` |
| 进程树 | `pstree` | `windows.pstree` |
| 隐藏进程 | `psscan` | `windows.psscan` |
| 网络 | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| 导出进程 | `procdump` | `windows.pslist --dump` |
| 查找恶意软件 | `malfind` | `windows.malfind` |
| 注册表 | `hivelist` | `windows.registry.hivelist` |
| 哈希 | `hashdump` | `windows.hashdump` |
| 文件 | `filescan` | `windows.filescan` |
| 字符串 | `strings` | - |

### 调查流程

```
1. imageinfo/windows.info     → 识别操作系统
2. pslist/pstree/psscan       → 列出进程
3. cmdline                    → 检查命令行
4. netscan                    → 网络连接
5. malfind                    → 查找注入代码
6. dlllist                    → 检查已加载的 DLL
7. filescan                   → 查找值得关注的文件
8. hashdump                   → 提取凭据
9. timeliner                  → 创建时间线
```

---

## 📚 参考资料

- [Volatility 基金会](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatility 维基](https://github.com/volatilityfoundation/volatility/wiki)
- [内存取证速查表](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### 相关速查表
- [Mimikatz](../../Mimikatz/README.md)
- [Linux 命令](../../Linux-Commands/README.md)
- [Windows 权限提升](../../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🧠 掌握内存取证！</b><br>
  <i>内存不会说谎——从 RAM 中提取真相</i>
</p>
