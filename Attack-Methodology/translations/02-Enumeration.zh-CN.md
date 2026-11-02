# 🔍 阶段 2：发现与枚举（Discovery / Enumeration）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](./02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**目标：** 收集已入侵系统及其所在网络的信息，为后续步骤制定计划。

> **译注：** 部分代码块的语言标签只是原文排版提示，块内可能混用 cmd、PowerShell 或其他工具控制台语法。自动化工具、收集器及示例文件名需与所用版本对应；存在某项权限或服务，不等于可以直接利用。

---

## 🖥️ Windows 枚举

### 系统信息
```cmd
# 基本系统信息
systeminfo
hostname
whoami /all

# 操作系统版本
wmic os get caption,version,buildnumber

# 体系结构
echo %PROCESSOR_ARCHITECTURE%

# 环境变量
set
```

### 用户与组枚举
```cmd
# 当前用户权限
whoami /priv
whoami /groups

# 所有本地用户
net user
net user administrator

# 所有本地组
net localgroup
net localgroup Administrators

# 域用户（若已加入域）
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### 网络信息
```cmd
# IP 配置
ipconfig /all

# 路由表
route print

# ARP 表
arp -a

# 活动连接
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# DNS 缓存
ipconfig /displaydns

# 共享
net share
net view \\localhost
```

### 进程与服务
```cmd
# 运行中的进程
tasklist
tasklist /SVC
wmic process list brief

# 服务
sc query
net start
wmic service list brief

# 计划任务
schtasks /query /fo LIST /v
```

### 已安装的软件
```cmd
# 已安装的程序
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# 热修复与补丁
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### 凭据与敏感文件
```cmd
# 已保存的凭据
cmdkey /list

# WiFi 密码
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# 在文件中搜索密码
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# 无人值守安装文件
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

## 🐧 Linux 枚举

### 系统信息
```bash
# 基本信息
hostname
uname -a
cat /etc/os-release
cat /proc/version

# 体系结构
arch

# 内核版本
uname -r
```

### 用户与组枚举
```bash
# 当前用户
whoami
id
groups

# 所有用户
cat /etc/passwd
cat /etc/shadow  # if readable
cat /etc/group

# 已登录用户
w
who
last

# Sudo 权限
sudo -l
cat /etc/sudoers
```

### 网络信息
```bash
# IP 配置
ip a
ifconfig

# 路由
ip route
route -n

# ARP
arp -a
ip neigh

# 活动连接
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### 进程与服务
```bash
# 运行中的进程
ps aux
ps auxwww
top

# 服务
systemctl list-units --type=service
service --status-all

# Cron 定时任务
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### 已安装的软件
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# SUID 二进制程序（可能的提权入口！）
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### 凭据与敏感文件
```bash
# SSH 密钥
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# 历史记录文件
cat ~/.bash_history
cat ~/.zsh_history

# 包含密码的配置文件
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# 值得检查的文件
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

## 🏢 Active Directory（活动目录）枚举

### PowerView
```powershell
# 导入模块
Import-Module .\PowerView.ps1

# 获取域信息
Get-Domain
Get-DomainController

# 获取用户
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# 获取组
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# 获取计算机
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# 获取组策略对象（GPO）
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# 使用 SharpHound 收集数据
.\SharpHound.exe -c All

# 或使用 PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Python 收集器（从 Linux 运行）
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### LDAP 枚举
```bash
# 从 Linux 使用 ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# 用户
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# 使用凭据
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Kerberos 枚举
```bash
# 使用 Kerbrute 枚举用户
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# 可进行 AS-REP Roasting 的用户
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# 可进行 Kerberoasting 的账户
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

## 📡 网络枚举

### 主机发现
```bash
# Ping 主机探测
nmap -sn 192.168.1.0/24

# ARP 扫描（本地网络）
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### 端口扫描
```bash
# 快速扫描
nmap -F 192.168.1.10

# 全端口扫描
nmap -p- 192.168.1.10

# 服务版本检测
nmap -sV -sC 192.168.1.10

# UDP 扫描
nmap -sU --top-ports 100 192.168.1.10
```

### SMB 枚举
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# SMB 共享
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# 使用凭据
smbclient //192.168.1.10/share -U user
```

### SNMP 枚举
```bash
# 默认 SNMP 团体字串
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# 使用 onesixtyone 枚举
onesixtyone -c community.txt 192.168.1.10
```

---

## 🛠️ 自动化枚举工具

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

## 📊 快速参考

### Windows 快速枚举
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Linux 快速枚举
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

## 🔗 相关速查表

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**上一阶段：** [← 01 - 初始访问](./01-Initial-Access.zh-CN.md)

**下一阶段：** [03 - 权限提升 →](./03-Privilege-Escalation.zh-CN.md)
