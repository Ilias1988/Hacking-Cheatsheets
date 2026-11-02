# 📊 日志分析速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

> **译注：** 表中的事件组合是调查线索，不能单独证明入侵。示例查询依赖审计配置、日志来源和字段结构；7045 是 System 日志中的事件，4697 是 Security 日志中的事件，原文相关路径和查询均按原样保留。

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Windows 事件日志

### 日志位置
```
# 安全日志
C:\Windows\System32\winevt\Logs\Security.evtx

# 系统日志
C:\Windows\System32\winevt\Logs\System.evtx

# 应用程序日志
C:\Windows\System32\winevt\Logs\Application.evtx

# PowerShell 日志
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon（如已安装）
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### 关键安全事件 ID

#### 身份验证事件
| 事件 ID | 说明 | 关注重点 |
|----------|-------------|------------|
| **4624** | 登录成功 | 跟踪用户访问 |
| **4625** | 登录失败 | 检测暴力破解 |
| **4634** | 注销 | 会话跟踪 |
| **4647** | 用户主动注销 | 会话跟踪 |
| **4648** | 使用显式凭据登录 | 横向移动 movement |
| **4672** | 分配特殊权限 | 管理员活动 |
| **4768** | Kerberos TGT 请求 | AD 身份验证 |
| **4769** | Kerberos 服务票据 | 服务访问 |
| **4771** | Kerberos 预身份验证失败 | 密码喷洒 |
| **4776** | NTLM 身份验证 | 凭据验证 |

#### 登录类型（适用于 4624/4625）
| 类型 | 说明 |
|------|-------------|
| 2 | 交互式（本地） |
| 3 | 网络 |
| 4 | 批处理 |
| 5 | 服务 |
| 7 | 解锁 |
| 8 | 网络明文登录 |
| 9 | 新凭据 |
| 10 | 远程交互式（RDP） |
| 11 | 缓存的交互式登录 |

#### 账户管理
| 事件 ID | 说明 |
|----------|-------------|
| **4720** | 创建用户账户 |
| **4722** | 启用用户账户 |
| **4723** | 尝试更改密码 |
| **4724** | 重置密码 |
| **4725** | 禁用用户账户 |
| **4726** | 删除用户账户 |
| **4728** | 向安全组添加成员 |
| **4732** | 向本地组添加成员 |
| **4756** | 向通用组添加成员 |

#### 进程与服务事件
| 事件 ID | 说明 |
|----------|-------------|
| **4688** | 创建进程 |
| **4689** | 终止进程 |
| **7045** | 安装服务 |
| **7036** | 服务状态变更 |
| **4697** | 安装服务（安全日志） |

#### 计划任务
| 事件 ID | 说明 |
|----------|-------------|
| **4698** | 创建计划任务 |
| **4699** | 删除计划任务 |
| **4700** | 启用计划任务 |
| **4702** | 更新计划任务 |

### PowerShell 查询命令
```powershell
# 获取安全日志条目
Get-WinEvent -LogName Security -MaxEvents 100

# 登录失败（4625）
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# 登录成功（4624）
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# 新建进程（4688）
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# 安装服务（7045）
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# 按时间范围搜索
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Wevtutil 命令
```cmd
# 查询安全日志
wevtutil qe Security /c:50 /f:text

# 查询指定事件 ID
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# 导出日志
wevtutil epl Security C:\evidence\security.evtx

# 清除日志（谨慎使用）
wevtutil cl Security
```

---

## 🐧 Linux 日志分析

### 日志位置
```bash
# 身份验证
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# 系统
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# 内核
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# 审计
/var/log/audit/audit.log

# Web 服务器
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### 常用日志分析命令

#### 身份验证分析
```bash
# SSH 登录失败
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# SSH 登录成功
grep "Accepted" /var/log/auth.log

# SSH 暴力破解（按 IP 计数）
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# 用户权限提升（sudo）
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# su 尝试
grep "su:" /var/log/auth.log
```

#### 系统分析
```bash
# 启动与关机时间
last -x | grep shutdown
last -x | grep reboot

# 当前已登录的用户
who
w

# 登录历史
last
lastlog

# 失败的登录尝试
lastb
```

#### 进程与服务分析
```bash
# Cron 任务执行
grep CRON /var/log/syslog

# 服务启动与停止
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# 内核消息
dmesg | tail -100
grep -i error /var/log/kern.log
```

### 实用 grep/awk 模式
```bash
# 从日志提取 IP
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# 按 IP 统计出现次数
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# 按时间搜索
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# 搜索多个文件
grep -r "error" /var/log/

# 实时监控
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### journalctl（systemd）
```bash
# 所有日志
journalctl

# 持续跟踪模式
journalctl -f

# 从指定时间开始
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# 按单元或服务筛选
journalctl -u sshd
journalctl -u nginx

# 按优先级筛选
journalctl -p err       # Errors only
journalctl -p warning   # Warnings and above

# 内核消息
journalctl -k

# JSON 输出
journalctl -o json
```

---

## 🌐 Web 服务器日志

### Apache 日志格式
```
# 组合日志格式
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# 字段：
# IP - ident - user - [timestamp] "method path protocol" status size "referer" "user-agent"
```

### Nginx 日志格式
```
# 默认格式与 Apache 类似
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Web 日志分析
```bash
# 请求最多的 URL
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# 出现最多的 IP
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# HTTP 状态码
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# 404 错误
grep ' 404 ' /var/log/apache2/access.log

# POST 请求（可能的攻击）
grep '"POST' /var/log/apache2/access.log

# SQL 注入尝试
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# 路径遍历尝试
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# 按小时统计请求
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Sysmon 日志分析

### 关键 Sysmon 事件 ID
| 事件 ID | 说明 |
|----------|-------------|
| 1 | 创建进程 |
| 2 | 修改文件创建时间 |
| 3 | 网络连接 |
| 5 | 进程终止 |
| 6 | 加载驱动程序 |
| 7 | 加载映像（DLL） |
| 8 | CreateRemoteThread |
| 10 | ProcessAccess |
| 11 | 文件Create |
| 12-14 | 注册表事件 |
| 15 | 文件CreateStream哈希 |
| 17-18 | 管道事件 |
| 22 | DNS 查询 |

### PowerShell Sysmon 查询
```powershell
# 进程创建（事件 1）
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# 网络连接（事件 3）
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# DNS 查询（事件 22）
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 快速参考

### 需要关注的可疑模式

| 类别 | 模式 |
|----------|---------|
| **暴力破解** | 同一 IP 出现多次 4625 事件 |
| **密码喷洒攻击** | 同一 IP 针对多个用户产生 4625 事件 |
| **横向移动** | 4624（类型 3）+ 4648 |
| **持久化** | 4698（新增计划任务） |
| **权限提升** | 非管理员账户产生 4672 事件 |
| **凭据转储** | 访问 lsass.exe |

### 日志分析工具
| 工具 | 用途 |
|------|-----|
| **grep/awk/sed** | Linux 日志解析 |
| **PowerShell** | Windows 日志分析 |
| **Splunk** | 企业级 SIEM |
| **ELK Stack** | 开源 SIEM |
| **日志Parser** | 使用 SQL 查询日志 |
| **Chainsaw** | 快速分析 Windows 日志 |

---

## 🔗 相关速查表

- [事件响应](./Incident-Response.zh-CN.md)
- [SIEM 检测](./SIEM-Detection.zh-CN.md)
- [Sigma 规则](./Sigma-Rules.zh-CN.md)

---

**上一篇:** [← 事件响应](./Incident-Response.zh-CN.md)

**下一篇:** [SIEM 检测 →](./SIEM-Detection.zh-CN.md)
