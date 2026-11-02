<a id="-phase-2-discovery--enumeration"></a>
# 🔍 المرحلة 2: الاكتشاف وحصر المعلومات (Discovery / Enumeration)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](02-Enumeration.tr.md) • [🇬🇷 Ελληνικά](02-Enumeration.el.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**الهدف:** جمع معلومات عن النظام المخترق والشبكة للتخطيط للخطوات التالية.

---

<a id="-windows-enumeration"></a>
## 🖥️ حصر معلومات Windows

> **ملاحظة تنسيق:** بعض كتل الأصل تجمع أوامر CMD وPowerShell وتعليقات تبدأ بعلامة `#`. هذه العلامة ليست تعليقاً في CMD؛ المقصود شرح الأسطر، ويجب تمييز الصدفة المناسبة لكل أمر.


<a id="system-information"></a>
### معلومات النظام
```cmd
# معلومات النظام الأساسية
systeminfo
hostname
whoami /all

# إصدار نظام التشغيل
wmic os get caption,version,buildnumber

# المعمارية
echo %PROCESSOR_ARCHITECTURE%

# متغيرات البيئة
set
```

<a id="user--group-enumeration"></a>
### حصر المستخدمين والمجموعات
```cmd
# صلاحيات المستخدم الحالي
whoami /priv
whoami /groups

# جميع المستخدمين المحليين
net user
net user administrator

# جميع المجموعات المحلية
net localgroup
net localgroup Administrators

# مستخدمو المجال إذا كان الجهاز منضماً إليه
net user /domain
net group /domain
net group "Domain Admins" /domain
```

<a id="network-information"></a>
### معلومات الشبكة
```cmd
# إعدادات IP
ipconfig /all

# جدول التوجيه
route print

# جدول ARP
arp -a

# الاتصالات النشطة
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# ذاكرة DNS المؤقتة
ipconfig /displaydns

# المشاركات
net share
net view \\localhost
```

<a id="process--services"></a>
### العمليات والخدمات
```cmd
# العمليات قيد التشغيل
tasklist
tasklist /SVC
wmic process list brief

# الخدمات
sc query
net start
wmic service list brief

# المهام المجدولة
schtasks /query /fo LIST /v
```

<a id="installed-software"></a>
### البرمجيات المثبتة
```cmd
# البرامج المثبتة
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# الإصلاحات العاجلة والتصحيحات
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="credentials--sensitive-files"></a>
### بيانات الاعتماد والملفات الحساسة
```cmd
# بيانات الاعتماد المحفوظة
cmdkey /list

# كلمات مرور WiFi
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# البحث عن كلمات مرور داخل الملفات
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# ملفات التثبيت غير التفاعلي Unattend
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

<a id="-linux-enumeration"></a>
## 🐧 حصر معلومات Linux

<a id="system-information-1"></a>
### معلومات النظام
```bash
# المعلومات الأساسية
hostname
uname -a
cat /etc/os-release
cat /proc/version

# المعمارية
arch

# إصدار النواة
uname -r
```

<a id="user--group-enumeration-1"></a>
### حصر المستخدمين والمجموعات
```bash
# المستخدم الحالي
whoami
id
groups

# جميع المستخدمين
cat /etc/passwd
cat /etc/shadow  # إذا كان قابلاً للقراءة
cat /etc/group

# المستخدمون المسجلون حالياً
w
who
last

# صلاحيات sudo
sudo -l
cat /etc/sudoers
```

<a id="network-information-1"></a>
### معلومات الشبكة
```bash
# إعدادات IP
ip a
ifconfig

# التوجيه
ip route
route -n

# ARP
arp -a
ip neigh

# الاتصالات النشطة
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

<a id="process--services-1"></a>
### العمليات والخدمات
```bash
# العمليات قيد التشغيل
ps aux
ps auxwww
top

# الخدمات
systemctl list-units --type=service
service --status-all

# مهام Cron
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

<a id="installed-software-1"></a>
### البرمجيات المثبتة
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# ملفات SUID التنفيذية (مسارات محتملة لتصعيد الصلاحيات)
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

<a id="credentials--sensitive-files-1"></a>
### بيانات الاعتماد والملفات الحساسة
```bash
# مفاتيح SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# ملفات سجل الأوامر
cat ~/.bash_history
cat ~/.zsh_history

# ملفات إعداد تحتوي على كلمات مرور
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# ملفات تستحق المراجعة
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

<a id="-active-directory-enumeration"></a>
## 🏢 حصر معلومات Active Directory

<a id="powerview"></a>
### PowerView
```powershell
# استيراد الوحدة
Import-Module .\PowerView.ps1

# الحصول على معلومات المجال
Get-Domain
Get-DomainController

# عرض المستخدمين
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# عرض المجموعات
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# عرض أجهزة الحاسوب
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# عرض كائنات نهج المجموعة GPO
Get-DomainGPO
Get-DomainGPOLocalGroup
```

<a id="bloodhound"></a>
### BloodHound
```bash
# جمع البيانات باستخدام SharpHound
.\SharpHound.exe -c All

# أو باستخدام PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# جامع بيانات Python من Linux
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

<a id="ldap-enumeration"></a>
### حصر معلومات LDAP
```bash
# من Linux باستخدام ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# المستخدمون
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# باستخدام بيانات اعتماد
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

<a id="kerberos-enumeration"></a>
### حصر معلومات Kerberos
```bash
# حصر المستخدمين باستخدام Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# مستخدمون قابلون لهجمات AS-REP Roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# حسابات قابلة لهجمات Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

<a id="-network-enumeration"></a>
## 📡 حصر معلومات الشبكة

<a id="host-discovery"></a>
### اكتشاف الأجهزة
```bash
# اكتشاف الأجهزة باستطلاع Ping
nmap -sn 192.168.1.0/24

# فحص ARP داخل الشبكة المحلية
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

<a id="port-scanning"></a>
### فحص المنافذ
```bash
# فحص سريع
nmap -F 192.168.1.10

# فحص جميع المنافذ
nmap -p- 192.168.1.10

# تحديد إصدارات الخدمات
nmap -sV -sC 192.168.1.10

# فحص UDP
nmap -sU --top-ports 100 192.168.1.10
```

<a id="smb-enumeration"></a>
### حصر معلومات SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# مشاركات SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# باستخدام بيانات اعتماد
smbclient //192.168.1.10/share -U user
```

<a id="snmp-enumeration"></a>
### حصر معلومات SNMP
```bash
# سلاسل Community الافتراضية
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# الحصر باستخدام onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

<a id="-automated-enumeration-tools"></a>
## 🛠️ أدوات الحصر الآلي

<a id="windows"></a>
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

<a id="linux"></a>
### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# أداة LSE (Linux Smart Enumeration) للحصر الذكي
./lse.sh -l 2
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="windows-quick-enum"></a>
### حصر سريع لمعلومات Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

<a id="linux-quick-enum"></a>
### حصر سريع لمعلومات Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [Nmap](../../Nmap/README.md)
- [BloodHound](../../BloodHound/README.md)
- [PowerView](../../PowerView/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)

---

**المرحلة السابقة:** [← 01 - الوصول الأولي](./01-Initial-Access.ar.md)

**المرحلة التالية:** [03 - تصعيد الصلاحيات →](./03-Privilege-Escalation.ar.md)
