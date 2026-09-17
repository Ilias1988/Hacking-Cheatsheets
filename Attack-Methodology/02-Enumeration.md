# 🔍 Phase 2: Discovery / Enumeration

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Goal:** Gather information about the compromised system and network to plan next steps.

---

## 🖥️ Windows Enumeration

### System Information
```cmd
# Basic system info
systeminfo
hostname
whoami /all

# OS version
wmic os get caption,version,buildnumber

# Architecture
echo %PROCESSOR_ARCHITECTURE%

# Environment variables
set
```

### User & Group Enumeration
```cmd
# Current user privileges
whoami /priv
whoami /groups

# All local users
net user
net user administrator

# All local groups
net localgroup
net localgroup Administrators

# Domain users (if domain joined)
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Network Information
```cmd
# IP configuration
ipconfig /all

# Routing table
route print

# ARP table
arp -a

# Active connections
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# DNS cache
ipconfig /displaydns

# Shares
net share
net view \\localhost
```

### Process & Services
```cmd
# Running processes
tasklist
tasklist /SVC
wmic process list brief

# Services
sc query
net start
wmic service list brief

# Scheduled tasks
schtasks /query /fo LIST /v
```

### Installed Software
```cmd
# Installed programs
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Hotfixes/Patches
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Credentials & Sensitive Files
```cmd
# Saved credentials
cmdkey /list

# WiFi passwords
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Search for passwords in files
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Unattend files
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Linux Enumeration

### System Information
```bash
# Basic info
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Architecture
arch

# Kernel version
uname -r
```

### User & Group Enumeration
```bash
# Current user
whoami
id
groups

# All users
cat /etc/passwd
cat /etc/shadow  # if readable
cat /etc/group

# Logged in users
w
who
last

# Sudo permissions
sudo -l
cat /etc/sudoers
```

### Network Information
```bash
# IP configuration
ip a
ifconfig

# Routing
ip route
route -n

# ARP
arp -a
ip neigh

# Active connections
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Process & Services
```bash
# Running processes
ps aux
ps auxwww
top

# Services
systemctl list-units --type=service
service --status-all

# Cron jobs
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Installed Software
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# SUID binaries (privesc vectors!)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Credentials & Sensitive Files
```bash
# SSH keys
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# History files
cat ~/.bash_history
cat ~/.zsh_history

# Config files with passwords
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Interesting files
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Active Directory Enumeration

### PowerView
```powershell
# Import module
Import-Module .\PowerView.ps1

# Get domain info
Get-Domain
Get-DomainController

# Get users
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Get groups
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Get computers
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Get GPOs
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Collect data with SharpHound
.\SharpHound.exe -c All

# Or with PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Python collector (from Linux)
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### LDAP Enumeration
```bash
# From Linux with ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Users
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# With credentials
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Kerberos Enumeration
```bash
# Enumerate users with Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# AS-REP Roastable users
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Kerberoastable accounts
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 Network Enumeration

### Host Discovery
```bash
# Ping sweep
nmap -sn 192.168.1.0/24

# ARP scan (local network)
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Port Scanning
```bash
# Quick scan
nmap -F 192.168.1.10

# Full port scan
nmap -p- 192.168.1.10

# Service version detection
nmap -sV -sC 192.168.1.10

# UDP scan
nmap -sU --top-ports 100 192.168.1.10
```

### SMB Enumeration
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# SMB shares
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# With credentials
smbclient //192.168.1.10/share -U user
```

### SNMP Enumeration
```bash
# Default community strings
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Enumerate with onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ Automated Enumeration Tools

### Windows
```bash
# WinPEAS
.\winPEAS.exe

# Seatbelt
.\Seatbelt.exe -group=all

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --database 2024.xlsx --systeminfo systeminfo.txt
```

### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# LSE (Linux Smart Enumeration)
./lse.sh -l 2
```

---

## 📊 Quick Reference

### Windows Quick Enum
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Linux Quick Enum
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 Related Cheatsheets

- [Nmap](../Nmap/README.md)
- [BloodHound](../BloodHound/README.md)
- [PowerView](../PowerView/README.md)
- [CrackMapExec](../CrackMapExec/README.md)

---

**Previous Phase:** [← 01 - Initial Access](./01-Initial-Access.md)

**Next Phase:** [03 - Privilege Escalation →](./03-Privilege-Escalation.md)
