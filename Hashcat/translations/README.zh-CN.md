# ⚡ Hashcat 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本说明：** 本文保留原文模式号、规则、掩码、密码与哈希样例。哈希类型数量、设备后端和安装包会随版本变化；对比表中的“不能自动识别”、5 种攻击模式及默认工作负载等属于旧版描述，当前官方选项中已有 `--identify`。设备类型 `-D 3` 等也需按实际版本核对，参见 [Hashcat 官方选项说明](https://hashcat.net/wiki/doku.php?id=hashcat)。
>
> **参数勘误：** `--outfile-format=2` 表示 `plain`，并非原文注释的 `hash:plain`；`--benchmark-all` 是对全部哈希模式进行基准测试，不表示关闭 OpenCL 优化。`--force` 会忽略警告，不是性能加速开关。原始命令与注释中的技术字面量均保持不变，参见 [官方格式和参数表](https://hashcat.net/wiki/doku.php?id=hashcat)。
>
> **掩码与格式说明：** 4 个 `?d` 的掩码最多覆盖 4 位，设置 `--increment-max=8` 不会自动将其扩展到 8 位，参见 [官方掩码说明](https://hashcat.net/wiki/doku.php?id=mask_attack)。原文字符集 `?s` 的展示也不完整，实际字符集应核对官方列表。模式表中的 `15200` 被原文标为 Ethereum Keystore，但官方列表对应 Blockchain, My Wallet, V2；哈希样例中带 `...` 的内容是截断展示，不能作为完整输入。参见 [官方模式与格式示例](https://hashcat.net/wiki/doku.php?id=example_hashes)。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    全球最快的密码恢复工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 全球最快且最先进的密码恢复工具</b>
</p>

---

## 📋 目录

- [简介](#-introduction)
- [安装](#-installation)
- [基本语法](#-basic-syntax)
- [攻击模式](#-attack-modes)
- [哈希模式](#-hash-modes)
- [掩码攻击](#-mask-attack)
- [规则](#-rules)
- [会话管理](#-session-management)
- [GPU 优化](#-gpu-optimization)
- [实践示例](#-real-world-examples)
- [速查](#-quick-reference)
- [技巧与最佳实践](#-tips--best-practices)
- [参考资源](#-resources)

---

<a id="-introduction"></a>
## 🎯 简介

**Hashcat** 是全球最快且最先进的密码恢复工具，支持超过 350 种哈希类型，并利用 GPU 加速。

### 主要功能

| 功能 | 说明 |
|---------|-------------|
| **350+ 种哈希类型** | MD5、SHA、NTLM、bcrypt、WPA 等 |
| **GPU 加速** | NVIDIA CUDA, AMD OpenCL, Intel |
| **多 GPU** | 支持分布式破解 |
| **5 种攻击模式** | 字典、暴力枚举、混合等 |
| **强大的规则功能** | 高级候选词变换 |
| **跨平台** | Linux, Windows, macOS |

### Hashcat 与 John the Ripper 对比

| 功能 | Hashcat | John |
|---------|---------|------|
| **速度** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **GPU 支持** | 优秀 | 良好 |
| **哈希类型** | 350+ | 300+ |
| **自动识别** | ❌ 否 | ✅ 是 |
| **易用性** | 适中 | 更容易 |
| **更适合** | GPU 破解 | CPU 破解 |

---

<a id="-installation"></a>
## 📥 安装

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# 使用 OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# 从官方网站下载
https://hashcat.net/hashcat/

# 解压后在文件夹中运行
hashcat.exe -h
```

### 从源码构建
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### 验证安装
```bash
hashcat --version
hashcat -I                    # 显示 OpenCL 设备
hashcat -b                    # 运行基准测试
```

---

<a id="-basic-syntax"></a>
## ⌨️ 基本语法

### 通用语法
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-m` | 哈希类型（模式） |
| `-a` | 攻击模式 |
| `-o` | 输出文件 |
| `-r` | 规则文件 |
| `--show` | 显示已恢复的密码 |
| `--status` | 启用状态显示 |
| `-w` | 工作负载配置（1-4） |
| `--force` | 忽略警告 |
| `-O` | 优化内核 |

### 快速开始
```bash
# 基础字典攻击
hashcat -m 0 -a 0 hash.txt wordlist.txt

# 显示已破解的哈希
hashcat -m 0 hash.txt --show

# 查看哈希示例
hashcat -m 0 --example-hashes
```

---

<a id="-attack-modes"></a>
## 🎮 攻击模式

### 概览

| 模式 | 名称 | 说明 |
|------|------|-------------|
| `-a 0` | 字典 | 字典攻击 |
| `-a 1` | 组合 | 组合两个字典 |
| `-a 3` | 暴力枚举 | 基于掩码的攻击 |
| `-a 6` | 混合：字典+掩码 | 字典 + 掩码 |
| `-a 7` | 混合：掩码+字典 | 掩码 + 字典 |
| `-a 9` | 关联 | 词条关联 |

### 字典攻击（-a 0）

```bash
# 基础字典攻击
hashcat -m 0 -a 0 hash.txt wordlist.txt

# 使用规则
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# 多个字典
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### 组合攻击（-a 1）

将两个字典中的词条组合起来。

```bash
# 组合两个字典
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# word1word2 组合
```

### 暴力枚举/掩码攻击（-a 3）

```bash
# 全部为小写字母，6 个字符
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# 全部为数字，4-8 个字符（原文示例的掩码长度有误，见译注）
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# 自定义字符集
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### 混合攻击（-a 6、-a 7）

```bash
# 字典 + 掩码（追加到末尾）
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# 掩码 + 字典（添加到开头）
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

<a id="-hash-modes"></a>
## 🔢 哈希模式

### 最常用的哈希模式

| 模式 | 哈希类型 | 示例 |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | 复杂格式 |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Web 应用

| 模式 | 哈希类型 |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### 数据库

| 模式 | 哈希类型 |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### 文档与压缩包

| 模式 | 哈希类型 |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### 加密货币

| 模式 | 哈希类型 |
|------|-----------|
| 11300 | Bitcoin 钱包 |
| 12700 | Blockchain 钱包 |
| 16600 | Electrum 钱包 |
| 15200 | Ethereum Keystore |

### 列出或搜索模式
```bash
# 列出所有哈希模式
hashcat --help | grep -i "hash modes"

# 搜索指定哈希类型
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# 显示哈希格式示例
hashcat -m 1000 --example-hashes
```

---

<a id="-mask-attack"></a>
## 🎭 掩码攻击

### 字符集占位符

| 占位符 | 字符 |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | ``!"#$%&'()*+,-./:;<=>?@[]^_`{\|}~`` |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### 自定义字符集

```bash
# 定义自定义字符集
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# 多个自定义字符集
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# 特殊字符
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### 递增长度模式

```bash
# 尝试长度 1-8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# 指定最小和最大长度
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### 常见掩码模式

```bash
# 8 个小写字母
?l?l?l?l?l?l?l?l

# Password + 4 位数字
password?d?d?d?d

# 大写字母 + 小写字母 + 数字
?u?l?l?l?l?l?d?d

# Winter2024 模式
?u?l?l?l?l?l?d?d?d?d

# 电话号码
?d?d?d?d?d?d?d?d?d?d
```

---

<a id="-rules"></a>
## 📜 规则

### 内置规则文件

```bash
# 位置
ls /usr/share/hashcat/rules/

# 常用规则
best64.rule           # 常用的 64 条规则
rockyou-30000.rule    # 常用的 30000 条规则
d3ad0ne.rule          # D3ad0ne 规则
dive.rule             # Dive 规则
generated.rule        # 生成的规则
```

### 使用规则

```bash
# 单个规则文件
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# 多个规则文件
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# 生成规则
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### 常见规则操作

| 规则 | 说明 | 示例 |
|------|-------------|---------|
| `:` | 不作变换 | password |
| `l` | 全部转为小写 | PASSWORD → password |
| `u` | 全部转为大写 | password → PASSWORD |
| `c` | 首字母大写 | password → Password |
| `C` | 首字母小写，其余大写 | password → pASSWORD |
| `t` | 切换大小写 | password → PASSWORD |
| `r` | 反转 | password → drowssap |
| `d` | 重复 | pass → passpass |
| `$X` | 末尾追加 X | pass → passX |
| `^X` | 开头添加 X | pass → Xpass |
| `sXY` | 将 X 替换为 Y | password → pa$$word |

---

<a id="-session-management"></a>
## 💾 会话管理

### 创建会话
```bash
# 命名会话
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# 会话文件存放在 ~/.hashcat/sessions/（实际位置依版本和环境而异）
```

### 暂停与恢复
```bash
# 暂停：破解时按 'p'
# 恢复：破解时按 'r'

# 或从命令行恢复
hashcat --session=mysession --restore
```

### 状态控制按键

破解过程中：
- `s` - 显示状态
- `p` - 暂停
- `r` - 恢复
- `b` - 跳过当前攻击
- `c` - 检查点
- `q` - 退出

### 输出选项
```bash
# 输出到文件
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# 输出格式
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# 显示已破解结果
hashcat -m 0 hash.txt --show

# 显示剩余未破解结果
hashcat -m 0 hash.txt --left
```

---

<a id="-gpu-optimization"></a>
## 🚀 GPU 优化

### 工作负载配置

| 配置 | 说明 |
|---------|-------------|
| `-w 1` | 低（原文标为默认、可交互，具体依版本而异） |
| `-w 2` | 中 |
| `-w 3` | 高（可能导致桌面卡顿） |
| `-w 4` | 极高（专用破解环境） |

### 优化命令
```bash
# 高性能
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# 优化内核（更快，但支持的密码长度受限）
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# 指定设备
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# 不使用 CPU，仅使用 GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### 设备选项
```bash
# 列出设备
hashcat -I

# 选择指定设备
hashcat -d 1 ...

# 设备类型
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### 基准测试
```bash
# 完整基准测试
hashcat -b

# 指定哈希类型
hashcat -b -m 0

# 不使用 OpenCL 优化的基准测试（原文描述有误，见译注）
hashcat -b --benchmark-all
```

---

<a id="-real-world-examples"></a>
## 🎬 实践示例

### 示例 1：MD5 字典攻击
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### 示例 2：NTLM 配合规则
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### 示例 3：WPA/WPA2 握手
```bash
# 先转换为 hashcat 格式
hcxpcapngtool -o hash.hc22000 capture.pcapng

# 破解
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### 示例 4：SHA256 暴力枚举（6 个字符）
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### 示例 5：Office 2013 文档
```bash
# 使用 office2john 提取哈希
office2john document.docx > office.hash

# 破解
hashcat -m 9600 office.hash wordlist.txt
```

### 示例 6：Linux Shadow 密码
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### 示例 7：WordPress 哈希
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### 示例 8：混合攻击
```bash
# 公司名称 + 4 位数字
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### 示例 9：Windows 域哈希
```bash
# 来自 secretsdump 的 NTLM 哈希
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### 示例 10：Bitcoin 钱包
```bash
# 使用 bitcoin2john 提取
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用命令

| 任务 | 命令 |
|------|---------|
| MD5 字典攻击 | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| NTLM 暴力枚举 | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 配合规则 | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| 显示已破解结果 | `hashcat -m 0 hash.txt --show` |
| 基准测试 | `hashcat -b` |
| 列出设备 | `hashcat -I` |
| 恢复会话 | `hashcat --session=name --restore` |

### 常见哈希模式

| 模式 | 类型 |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### 攻击模式

| 模式 | 类型 |
|------|------|
| -a 0 | 字典 |
| -a 1 | 组合 |
| -a 3 | 暴力枚举 |
| -a 6 | 混合（字典+掩码） |
| -a 7 | 混合（掩码+字典） |

---

<a id="-tips--best-practices"></a>
## 💡 技巧与最佳实践

### 效率建议

1. **从字典 + 规则开始**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **使用优化内核**
   ```bash
   hashcat -O ...
   ```

3. **针对常见模式**
   ```bash
   # 季节+年份：Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### 性能建议

```bash
# 最大性能（专用系统）
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# 多个 GPU
hashcat -d 1,2 ...
```

### 工作流程

```
1. 识别哈希类型
2. 尝试字典结合规则
3. 尝试针对性掩码（模式）
4. 尝试完整暴力枚举（在可行的情况下）
5. 尝试混合攻击
```

---

## ⚠️ 法律免责声明

> **警告：** Hashcat 是强大的密码破解工具，仅应用于 **获得授权的用途**。
> 
> - ✅ 审计自己拥有的系统
> - ✅ 获得许可后开展安全评估
> - ✅ 恢复自己的密码
> - ❌ 切勿在未获授权时破解密码
> - ❌ 未获授权的访问属于违法行为
> 
> **未获授权的密码破解属于违法行为，可能导致刑事指控。**

---

<a id="-resources"></a>
## 📚 参考资源

### 官方资源
- [Hashcat 官网](https://hashcat.net/hashcat/)
- [Hashcat Wiki](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Hashcat 论坛](https://hashcat.net/forum/)

### 字典
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### 相关速查表
- [John the Ripper](../../John-The-Ripper/README.md)
- [Hydra](../../Hydra/README.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>⚡ 掌握 GPU 密码破解技术！</b><br>
  <i>全球最快的密码恢复工具</i>
</p>
