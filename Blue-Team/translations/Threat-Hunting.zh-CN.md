# 🎯 威胁狩猎速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

> **译注：** 原文“未签名服务”示例仅检查路径是否包含 `Windows`，没有验证数字签名；“隐藏进程”示例也只是枚举已有 PID 的可执行文件路径。事件属性的数字索引依赖事件结构，应按实际日志字段核对，查询结果本身不构成恶意行为的结论。

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 威胁狩猎流程

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. 提出假设  │────▶│ 2. 收集     │────▶│ 3. 分析     │────▶│ 4. 响应     │
│   (理论)     │     │   (数据)    │     │   (狩猎)    │     │   (行动)    │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. 记录     │◀─────────────────────────┘
                          │   (学习)    │
                          └─────────────┘
```

---

## 📋 假设示例

| 类别 | 假设 |
|----------|------------|
| **初始访问** | 攻击者可能通过网络钓鱼投递恶意软件 |
| **持久化** | 攻击者可能创建计划任务以实现持久化 |
| **凭据** | 攻击者可能转储 LSASS 以获取凭据 |
| **横向移动** | 攻击者可能使用 RDP 进行横向移动 |
| **数据外传** | 攻击者可能通过 DNS 隧道外传数据 |

---

## 🔍 狩猎技术

### 排查持久化行为

#### Windows 注册表 Run 项
```powershell
# 查询所有 Run 项
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### 计划任务
```powershell
# 列出所有计划任务
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# 查找可疑任务（非 Microsoft）
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### 服务
```powershell
# 未签名服务（原文表述；此查询仅按路径筛选）
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### 排查横向移动

#### RDP 连接
```powershell
# RDP 会话（事件 4624，类型 10）
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### 网络连接
```powershell
# 当前连接
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# 附带进程名称
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### 排查凭据访问

#### LSASS 访问
```powershell
# Sysmon 事件 10 — LSASS 访问
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### 排查执行行为

#### 经过编码的 PowerShell
```powershell
# 带编码内容的 PowerShell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### 可疑父子进程关系
```powershell
# Office 启动 cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Linux 威胁狩猎

### 持久化
```bash
# Cron 定时任务
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# SSH 授权密钥
find / -name "authorized_keys" 2>/dev/null

# 启动脚本
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# 具有 SUID 位的二进制文件
find / -perm -4000 -type f 2>/dev/null
```

### 网络
```bash
# 监听端口
ss -tulnp
netstat -tulnp

# 已建立的连接
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# 异常 DNS
tcpdump -i any port 53 -w dns.pcap
```

### 进程
```bash
# 列出所有进程及完整命令行
ps auxwww

# 进程树
pstree -p

# 隐藏进程（原文表述；此命令列出现有 PID 对应的可执行文件）
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# 最近修改的二进制文件
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 IOC 狩猎

### 哈希值排查
```powershell
# 计算哈希值
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# 搜索已知恶意哈希
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### IP 与域名排查
```powershell
# DNS 缓存
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# 连接到已知恶意 IP
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **Velociraptor** | 大规模终端威胁狩猎 |
| **OSQuery** | 基于 SQL 的终端查询 |
| **KAPE** | 初步研判数据采集 |
| **Chainsaw** | 快速分析 Windows 日志 |
| **DeepBlueCLI** | 使用 PowerShell 开展威胁狩猎 |

---

## 📋 快速狩猎检查清单

```markdown
□ 检查自动运行位置（注册表、服务、任务）
□ 审查最近的进程创建（事件 4688、Sysmon 1）
□ 分析网络连接
□ 查找经过编码的命令
□ 排查 LSASS 访问
□ 检查异常的父子进程关系
□ 审查 PowerShell 日志
□ 查找横向移动指标
```

---

## 🔗 相关速查表

- [日志分析](./Log-Analysis.zh-CN.md)
- [SIEM 检测](./SIEM-Detection.zh-CN.md)
- [Sigma 规则](./Sigma-Rules.zh-CN.md)

---

**上一篇:** [← SIEM 检测](./SIEM-Detection.zh-CN.md)

**下一篇:** [Sigma 规则 →](./Sigma-Rules.zh-CN.md)
