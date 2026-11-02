# 🔨 John the Ripper——完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  密码破解工具
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 经典的密码安全审计与恢复工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [破解模式](#-cracking-modes)
- [哈希提取](#-hash-extraction)
- [常见哈希类型](#-common-hash-types)
- [Linux 密码破解](#-linux-password-cracking)
- [Windows 密码破解](#-windows-password-cracking)
- [文件密码破解](#-file-password-cracking)
- [规则与词形变换](#-rules--mangling)
- [会话管理](#-session-management)
- [性能调整](#-performance-tuning)
- [实际应用示例](#-real-world-examples)
- [快速参考](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**John the Ripper**（John／JtR）是免费的开源密码安全审计与恢复工具，用于检测弱密码并破解密码哈希。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **多格式支持** | 支持数百种哈希类型 |
| **多种模式** | Single、Wordlist、Incremental 模式 |
| **自定义规则** | 强大的词形变换规则 |
| **自动识别** | 自动识别哈希格式 |
| **跨平台** | Linux, Windows, macOS |
| **GPU 支持** | OpenCL 加速（Jumbo） |

### John 与 Hashcat 对比

> **原表说明：** 此表属于概括性比较，实际速度与格式支持取决于算法、硬件及构建。原文将 Hashcat 的自动识别列为“否”，但当前 [Hashcat 官方帮助](https://hashcat.net/wiki/doku.php?id=hashcat) 已提供自动识别相关能力；原表保留作对照。

| 功能 | John | Hashcat |
|---------|------|---------|
| **适用场景** | CPU 破解 | GPU 破解 |
| **易用性** | 较易 | 较复杂 |
| **自动识别** | ✅ 是 | ❌ 否 |
| **规则** | 强大 | 非常强大 |
| **哈希类型** | 多 | 更多 |
| **速度（GPU）** | 良好 | 更好 |

### 版本

| 版本 | 说明 |
|---------|-------------|
| **John (Core)** | 基础版本 |
| **John Jumbo** | 社区增强版本（推荐） |

---

<a id="-installation"></a>
## 📥 安装

> **构建差异：** Core 与 Jumbo 的功能、发行版包名及 2john 脚本入口并不统一；原文 `john-jumbo` 不能视为所有 Debian／Ubuntu 仓库通用包名。请按发行版实际包及所装构建核对，例如 [Kali 的 john 软件包说明](https://www.kali.org/tools/john/)。官方说明无参数运行会显示帮助，Jumbo 还可通过 `--list=help` 查看可列出的能力，参见 [选项文档](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS)。

### Kali Linux（预装）
```bash
john --version
```

### Ubuntu/Debian
```bash
# 标准版本
sudo apt install john

# Jumbo 版本（推荐）
sudo apt install john-jumbo
```

### 从源码安装（Jumbo）
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS（Homebrew）
```bash
brew install john-jumbo
```

### Windows
```bash
# 从官方网站下载
https://www.openwall.com/john/

# 或使用 Chocolatey
choco install john
```

### 验证安装
```bash
john --version
john --list=formats | wc -l    # 统计支持的格式数（原文说法；wc -l 实际统计行数）
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
john [options] [password_file]
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `--wordlist=` | 字典路径 |
| `--format=` | 指定哈希格式 |
| `--rules` | 启用词形变换规则 |
| `--show` | 显示已破解的密码 |
| `--single` | Single 破解模式 |
| `--incremental` | 穷举模式 |
| `--session=` | 为会话命名 |
| `--restore` | 恢复先前的会话 |
| `--pot=` | 自定义 pot 文件 |

### 快速开始
```bash
# 自动识别并破解
john hashes.txt

# 使用字典
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# 显示已破解的密码
john --show hashes.txt
```

---

<a id="-cracking-modes"></a>
## 🎮 破解模式

### Single 破解模式

使用登录名与 GECOS（用户信息字段）生成候选密码。

```bash
# Single 模式（原文称最快，尝试用户名变体）
john --single hashes.txt

# 指定格式
john --single --format=sha512crypt hashes.txt
```

### 字典模式

使用字典中的词条，并可选择应用变换规则。

```bash
# 基本字典攻击
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# 字典加规则
john --wordlist=wordlist.txt --rules hashes.txt

# 指定规则集
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Incremental 模式（穷举）

尝试所配置范围内的字符组合。

```bash
# 默认 Incremental（原文称全部字符，实际取决于配置）
john --incremental hashes.txt

# 指定字符集
john --incremental=Digits hashes.txt      # 仅数字
john --incremental=Lower hashes.txt       # 仅小写字母
john --incremental=Upper hashes.txt       # 仅大写字母
john --incremental=Alpha hashes.txt       # 仅字母
john --incremental=Alnum hashes.txt       # 字母与数字
john --incremental=LowerNum hashes.txt    # 小写字母与数字
```

### 掩码模式

> **字符集说明：** 这里的 `?a` 指可打印 ASCII 字符，并非所有 Unicode 字符；`?h` 是小写十六进制数字。掩码占位符与规则语法中的字符类别需要区分，参见 [Jumbo 掩码文档](https://github.com/openwall/john/blob/bleeding-jumbo/doc/MASK)。所有原始掩码字符串保持不变。

根据指定模式生成候选密码并穷举。

```bash
# 掩码攻击（? 表示占位符）
john --mask='?l?l?l?l?d?d' hashes.txt

# 占位符：
# ?l = 小写字母    ?u = 大写字母
# ?d = 数字       ?s = 特殊字符
# ?a = 全部（可打印 ASCII）          ?h = 小写十六进制
```

### 外部模式

使用自定义破解算法或过滤逻辑。

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="-hash-extraction"></a>
## 🔍 哈希提取

### Linux 密码

```bash
# 合并 passwd 与 shadow 文件
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# 破解合并后的文件
john unshadowed.txt
```

### Windows 哈希

```bash
# 从 SAM 文件提取（使用 pwdump、secretsdump 等）
# 格式：user:id:LM_hash:NTLM_hash:::

# 使用 Metasploit hashdump
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# 保存到文件并破解
john --format=nt hashes.txt
```

### 文件密码（2john 工具）

```bash
# ZIP 文件
zip2john protected.zip > zip_hash.txt

# RAR 文件
rar2john protected.rar > rar_hash.txt

# PDF 文件
pdf2john protected.pdf > pdf_hash.txt

# Office 文档
office2john document.docx > office_hash.txt

# SSH 密钥
ssh2john id_rsa > ssh_hash.txt

# 7zip 文件
7z2john archive.7z > 7z_hash.txt

# KeePass 数据库
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="-common-hash-types"></a>
## 🔢 常见哈希类型

### 哈希格式表

> **样例完整性：** 含 `...` 的哈希是省略展示，不能作为完整有效输入。原文若干 SAM／NTLM 示例中的十六进制字段也被截短；表中的 LM 值仅有 16 个十六进制字符。请使用完整的实际哈希记录，不能补猜缺失字符。此处保留全部原始样例以便核对。

| 哈希类型 | 格式参数 | 示例 |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### 列出全部格式

> **计数纠正：** `wc -l` 统计输出行数。John 的格式列表可能在同一行输出多个逗号分隔名称，因此它不等于格式数量；这种输出布局可见 [Kali 官方使用示例](https://www.kali.org/tools/john/)。自动识别也可能得到多个候选格式，需结合来源判断。
```bash
# 列出全部支持的格式
john --list=formats

# 搜索指定格式
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# 统计格式（此处实际统计输出行数）
john --list=formats | wc -l
```

### 识别哈希类型
```bash
# 使用 hash-identifier
hash-identifier

# 或使用 hashid
hashid -m '$6$salt$hash...'
```

---

<a id="-linux-password-cracking"></a>
## 🐧 Linux 密码破解

### 准备哈希
```bash
# 合并 passwd 与 shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# 或提取所需的哈希条目
sudo cat /etc/shadow | grep username
```

### 哈希格式示例
```
# /etc/shadow 格式：
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512（原文称现代 Linux 最常见，依发行版配置而异）
# $y$ = yescrypt（原文标为最新）
```

### 破解 Linux 哈希
```bash
# 自动识别（推荐）
john linux_hashes.txt

# 指定格式
john --format=sha512crypt linux_hashes.txt

# 使用字典
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# 显示结果
john --show linux_hashes.txt
```

---

<a id="-windows-password-cracking"></a>
## 🪟 Windows 密码破解

### 哈希类型

| 类型 | 说明 | 格式 |
|------|-------------|--------|
| **LM** | 旧式格式（较弱） | `--format=lm` |
| **NTLM** | 现代 Windows | `--format=nt` |
| **NTLMv2** | 网络身份验证 | `--format=netntlmv2` |

### 破解 NTLM 哈希
```bash
# NTLM 哈希格式：
# user:rid:lm_hash:ntlm_hash:::

# 破解 NTLM
john --format=nt windows_hashes.txt

# 使用字典
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# 使用规则
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### 提取 Windows 哈希
```bash
# 使用 Metasploit
meterpreter> hashdump

# 使用 secretsdump（Impacket）
secretsdump.py -sam sam -system system LOCAL

# 使用 pwdump
pwdump7.exe
```

---

<a id="-file-password-cracking"></a>
## 📄 文件密码破解

### ZIP 文件
```bash
# 提取哈希
zip2john protected.zip > zip.hash

# 破解
john --wordlist=rockyou.txt zip.hash

# 显示密码
john --show zip.hash
```

### RAR 文件
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### PDF 文件
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Office 文档
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### SSH 私钥
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### KeePass 数据库
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-rules--mangling"></a>
## ⚙️ 规则与词形变换

### 启用规则
```bash
# 默认规则
john --wordlist=wordlist.txt --rules hashes.txt

# 指定规则集
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### 列出可用规则
```bash
john --list=rules
```

### 常见规则操作

| 规则 | 说明 | 示例 |
|------|-------------|---------|
| `:` | 不修改 | password → password |
| `l` | 全部转为小写 | PassWord → password |
| `u` | 全部转为大写 | password → PASSWORD |
| `c` | 首字母大写 | password → Password |
| `r` | 逆序 | password → drowssap |
| `d` | 重复 | pass → passpass |
| `$X` | 在末尾追加字符 X | pass → passX |
| `^X` | 在开头插入字符 X | pass → Xpass |
| `sXY` | 将 X 替换为 Y | password → pa$$word |

### 自定义规则

> **规则纠正：** `$X` 和 `^X` 每次分别追加、前置一个字符，不能把原文 `$123`、`^2023` 简单理解为追加“123”或前置“2023”。规则按命令序列解析，字符串插入另有语法；参见 [官方规则文档](https://www.openwall.com/john/doc/RULES.shtml)。原始规则保留，未改写或执行。
```bash
# 添加到 john.conf 的 [List.Rules:Custom] 小节
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="-session-management"></a>
## 💾 会话管理

### 创建命名会话
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### 恢复会话
```bash
# 恢复中断的会话
john --restore=mycrack

# 恢复默认会话
john --restore
```

### 查看状态
```bash
# 破解期间按任意键查看状态
# 或检查 .rec 文件
cat ~/.john/mycrack.rec
```

### pot 文件

> **路径说明：** pot 文件保存已恢复的结果。原文 `~/.john/john.pot` 是常见安装布局，官方以 `$JOHN/john.pot` 表示其位置，实际目录取决于安装方式，参见 [官方入门文档](https://www.openwall.com/john/doc/)。命名会话的 `.rec` 文件位置也应按本机配置核对。
```bash
# 默认 pot 文件
~/.john/john.pot

# 自定义 pot 文件
john --pot=custom.pot hashes.txt

# 显示全部已破解的密码
john --show --pot=custom.pot hashes.txt
```

---

<a id="-performance-tuning"></a>
## 🚀 性能调整

### Fork（多进程）
```bash
# 使用多个 CPU 核心
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL（GPU）
```bash
# 列出 OpenCL 设备
john --list=opencl-devices

# 使用 GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### 限制候选密码

> **长度单位：** Jumbo 的 `--min-length`／`--max-length` 限制按字节计算；非 ASCII 字符可能占多个字节，因此原文“字符数”描述不总是准确。参见 [官方选项说明](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS)。
```bash
# 限制密码长度
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### 内存使用

> **参数说明：** `--mem-file-size` 设置字典文件预加载到内存的大小阈值，并不是 John 进程总内存的硬上限。原文将其简写为“降低内存使用”，实际行为应按 [官方选项文档](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS) 理解。
```bash
# 降低内存使用（原文简化说法，参见译注）
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 实际应用示例

### 示例 1：Linux shadow 文件
```bash
# 合并文件
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# 使用 rockyou 破解
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# 显示结果
john --show hashes.txt
```

### 示例 2：Windows NTLM
```bash
# 来自 hashdump 输出
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# 破解
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### 示例 3：ZIP 文件密码
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### 示例 4：SSH 密钥口令
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### 示例 5：MD5 哈希
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### 示例 6：Web 应用哈希
```bash
# bcrypt 哈希
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### 示例 7：使用规则
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### 示例 8：Incremental 攻击
```bash
# 尝试全部 6–8 位小写字母与数字组合
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 常用命令

| 任务 | 命令 |
|------|---------|
| 基本破解 | `john hashes.txt` |
| 字典攻击 | `john --wordlist=rockyou.txt hashes.txt` |
| 使用规则 | `john --wordlist=words.txt --rules hashes.txt` |
| 显示已破解结果 | `john --show hashes.txt` |
| 指定格式 | `john --format=raw-md5 hashes.txt` |
| Incremental 模式 | `john --incremental hashes.txt` |
| 恢复会话 | `john --restore=session` |
| 列出格式 | `john --list=formats` |

### 2john 工具

| 工具 | 用途 |
|------|---------|
| `unshadow` | Linux passwd／shadow 文件 |
| `zip2john` | ZIP 文件 |
| `rar2john` | RAR 文件 |
| `pdf2john` | PDF 文件 |
| `office2john` | Office 文档 |
| `ssh2john` | SSH 密钥 |
| `keepass2john` | KeePass 数据库 |

### 常见格式

| Hash | 格式参数 |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 效率技巧

1. **优先尝试 Single 模式**
   ```bash
   john --single hashes.txt
   ```

2. **使用针对目标的字典**
   ```bash
   # 公司名称、城市等
   john --wordlist=targeted.txt hashes.txt
   ```

3. **先使用小字典**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # 然后尝试更大的字典
   ```

4. **结合多种模式**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### 性能技巧

```bash
# 使用多个核心
john --fork=4 hashes.txt

# GPU 加速（Jumbo）
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ 法律免责声明

> **警告：** John the Ripper 是功能强大的密码破解工具，只能用于**已获授权的用途**。
> 
> - ✅ 审计自己的密码
> - ✅ 取得明确许可后测试
> - ✅ 开展已获授权的安全评估
> - ❌ 未经许可，不得破解密码
> - ❌ 未授权访问属于违法行为
> 
> **未经授权的密码破解属于违法行为，可能导致刑事指控。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [John the Ripper 官方网站](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John Wiki](https://openwall.info/wiki/john)

### 字典
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### 相关速查表
- [Hydra](../../Hydra/translations/README.zh-CN.md)
- [Metasploit](../../Metasploit/translations/README.zh-CN.md)
- [Nmap](../../Nmap/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔨 掌握密码破解！</b><br>
  <i>始终负责任地使用密码破解工具</i>
</p>
