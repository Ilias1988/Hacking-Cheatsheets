<a id="-volatility---memory-forensics-cheatsheet"></a>
# 🧠 Volatility — ورقة مرجعية للتحليل الجنائي للذاكرة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Memory Forensics Framework
```

يصف الشعار Volatility بأنه إطار للتحليل الجنائي للذاكرة.

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هو Volatility؟](#-what-is-volatility)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [تحديد خصائص صورة الذاكرة](#-image-identification)
- [تحليل العمليات](#-process-analysis)
- [تحليل الشبكة](#-network-analysis)
- [تحليل سجل Windows ‏(Registry)](#-registry-analysis)
- [تحليل البرمجيات الخبيثة](#-malware-analysis)
- [تحليل المستخدمين وبيانات الاعتماد](#-user--credential-analysis)
- [تحليل الملفات ومكتبات DLL](#-file--dll-analysis)
- [تحليل التسلسل الزمني](#-timeline-analysis)
- [الإصدار Volatility 3](#-volatility-3)

---

<a id="-what-is-volatility"></a>
## 🎯 ما هو Volatility؟

**Volatility** إطار مفتوح المصدر للتحليل الجنائي لصور ذاكرة RAM، ويستخدم في:

- 🔍 **الاستجابة للحوادث** — تحديد مؤشرات البرمجيات الخبيثة النشطة
- 🦠 **تحليل البرمجيات الخبيثة** — البحث عن العمليات المخفية واستخراجها
- 🔐 **استعادة بيانات الاعتماد** — استخراج ما يتوفر من كلمات مرور أو قيم تجزئة
- 📊 **تحليل التسلسل الزمني** — إعادة بناء الأحداث
- 🕵️ **البحث الاستباقي عن التهديدات** — العثور على مؤشرات الاختراق

<a id="supported-memory-formats"></a>
### تنسيقات صور الذاكرة المدعومة بحسب الإصدار

|التنسيق|الوصف|
|--------|-------------|
|Raw (.raw, .mem)|صورة ذاكرة خام|
|EWF (.E01)|تنسيق EnCase|
|AFF|تنسيق AFF للتحليل الجنائي|
|Crash dump|صور تعطل Windows|
|VMware (.vmem)|لقطات VMware|
|VirtualBox (.sav)|لقطات VirtualBox|
|HPAK (.hpak)|تنسيق FDPro|
|LiME (.lime)|تنسيق أداة Linux Memory Extractor|

---

<a id="-installation"></a>
## 🚀 التثبيت

> **توافق الإصدارات:** يفصل الدليل بين Vol2 القديم وVol3؛ أسماء الإضافات وتوفرها وصيغ الذاكرة ومتطلبات الرموز تختلف بحسب الإصدار. بعض إضافات الأصل خارجية أو أعيدت تسميتها؛ لا تعامل القائمة كقائمة مضمونة لكل تثبيت. [فهرس إضافات Vol3 الرسمي](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.html).

<a id="volatility-2-legacy"></a>
### الإصدار القديم Volatility 2

```bash
# نسخ المستودع
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# تثبيت الاعتمادات
pip install pycrypto distorm3 yara-python pillow openpyxl

# التشغيل
python vol.py -h
```

<a id="volatility-3-current"></a>
### الإصدار Volatility 3

```bash
# التثبيت باستخدام pip
pip3 install volatility3

# أو نسخ المستودع
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# التشغيل
python3 vol.py -h
vol -h  # عند التثبيت باستخدام pip
```

<a id="download-symbol-tables-vol3"></a>
### تنزيل جداول الرموز للإصدار Vol3

```bash
# رموز Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# رموز Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# رموز macOS
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# فك الضغط إلى volatility3/symbols/
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="volatility-2-syntax"></a>
### صيغة Volatility 2

```bash
# الصيغة العامة
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# مثال
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

<a id="volatility-3-syntax"></a>
### صيغة Volatility 3

```bash
# الصيغة العامة؛ يصف الأصل معالجة الرموز بأنها اكتشاف تلقائي للملف التعريفي
vol -f <memory_dump> <plugin>

# مثال
vol -f memory.raw windows.pslist
```

<a id="common-options"></a>
### خيارات شائعة

```bash
# Volatility 2
-f FILE          # ملف صورة الذاكرة
--profile=PROFILE # الملف التعريفي لنظام التشغيل (Profile)
-h               # المساعدة
--info           # عرض الإضافات المتاحة

# الإصدار Volatility 3
-f FILE          # ملف صورة الذاكرة
-o DIR           # مجلد المخرجات
-r RENDERER      # تنسيق المخرجات بحسب الأصل: pretty وjson وcsv
-p PLUGIN_DIR    # إضافات أخرى
-h               # المساعدة
```

---

<a id="-image-identification"></a>
## 🔍 تحديد خصائص صورة الذاكرة

<a id="identify-profile-vol2"></a>
### تحديد الملف التعريفي في Vol2

```bash
# الحصول على الملفات التعريفية المقترحة
python vol.py -f memory.raw imageinfo

# معلومات أكثر تفصيلًا
python vol.py -f memory.raw kdbgscan
```

<a id="identify-os-vol3"></a>
### تحديد معلومات النظام في Vol3

> **توضيح:** لا يستخدم Vol3 ملفات Profile بالطريقة نفسها في Vol2، لكنه يحتاج رموزًا وطبقات ذاكرة متوافقة. يشرح الدليل الرسمي جلب رموز Windows تلقائيًا في حالات كثيرة؛ أما `linux.info` و`mac.info` فليسا في فهرس الإضافات القياسي الحالي. أبقينا أمثلة الأصل. [دليل Windows](https://volatility3.readthedocs.io/en/stable/getting-started-windows-tutorial.html)، [فهرس الإضافات](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.html).

```bash
# اكتشاف تلقائي؛ لا يتطلب Vol3 تحديد Profile يدويًا كما في Vol2
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

<a id="common-profiles-vol2"></a>
### ملفات تعريفية شائعة في Vol2

|نظام التشغيل|الملف التعريفي|
|----|---------|
|Windows XP SP2 x86|WinXPSP2x86|
|Windows XP SP3 x86|WinXPSP3x86|
|Windows 7 SP1 x86|Win7SP1x86|
|Windows 7 SP1 x64|Win7SP1x64|
|Windows 10 x64|Win10x64_*|
|Windows Server 2012|Win2012R2x64|

---

<a id="-process-analysis"></a>
## ⚙️ تحليل العمليات

<a id="list-processes"></a>
### عرض العمليات

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # عرض شجري
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # كائنات عمليات قد تكون مخفية أو منتهية

# الإصدار Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

<a id="process-details"></a>
### تفاصيل العمليات

```bash
# وسائط سطر الأوامر
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# متغيرات البيئة
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# مقابض العمليات (Handles)
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# مكتبات DLL المحملة
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

<a id="detect-hidden-processes"></a>
### البحث عن العمليات المخفية

```bash
# مقارنة طرق عرض العمليات مثل pslist وpsscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# الكشف عن تجويف العمليات (Process Hollowing)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

<a id="dump-process"></a>
### استخراج عملية من الذاكرة

```bash
# استخراج الملف التنفيذي
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# استخراج محتوى الذاكرة
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

<a id="-network-analysis"></a>
## 🌐 تحليل الشبكة

<a id="network-connections"></a>
### اتصالات الشبكة

```bash
# آثار اتصالات الشبكة في Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

<a id="output-fields"></a>
### حقول المخرجات

|الحقل|الوصف|
|-------|-------------|
|Offset|إزاحة الذاكرة|
|Proto|TCP/UDP|
|LocalAddr|عنوان IP المحلي والمنفذ|
|ForeignAddr|عنوان IP البعيد والمنفذ|
|State|حالة الاتصال|
|PID|معرف العملية|
|Owner|اسم العملية|

<a id="network-artifacts"></a>
### آثار الشبكة

```bash
# المقابس (Sockets)
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

<a id="-registry-analysis"></a>
## 📝 تحليل سجل Windows ‏(Registry)

<a id="list-registry-hives"></a>
### عرض خلايا سجل Windows ‏(Hives)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

<a id="dump-registry"></a>
### استخراج بيانات السجل

```bash
# عرض مفتاح محدد
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# استخراج خلية السجل إلى ملف
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

<a id="important-registry-keys"></a>
### مفاتيح سجل مهمة

```bash
# مفاتيح التشغيل التلقائي
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# الخدمات
printkey -K "SYSTEM\CurrentControlSet\Services"

# حسابات المستخدمين
printkey -K "SAM\Domains\Account\Users"

# الملفات الحديثة
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

<a id="user-assist-executed-programs"></a>
### آثار تشغيل البرامج في UserAssist

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

<a id="shellbags"></a>
### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

<a id="-malware-analysis"></a>
## 🦠 تحليل البرمجيات الخبيثة

<a id="malfind-injected-code"></a>
### البحث عن شيفرة محقونة باستخدام Malfind

```bash
# البحث عن شيفرة قد تكون محقونة
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# الاستخراج إلى ملفات
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

<a id="ssdt-hooks"></a>
### خطافات جدول SSDT

```bash
# جدول واصفات خدمات النظام (SSDT)
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# المقارنة بنظام نظيف بحسب الأصل؛ المرشح يستبعد اسمين فقط
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

<a id="idtgdt-analysis"></a>
### تحليل جدولي IDT وGDT

```bash
# جدول واصفات المقاطعات (IDT)
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# جدول الواصفات العام (GDT)
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

<a id="driver-analysis"></a>
### تحليل برامج التشغيل

```bash
# عرض برامج التشغيل
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# قائمة الوحدات
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# وحدات أزيل تحميلها
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# خطافات IRP في برامج التشغيل
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

<a id="yara-scanning"></a>
### الفحص بقواعد YARA

```bash
# الفحص باستخدام قواعد YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

<a id="api-hooks"></a>
### خطافات API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

<a id="-user--credential-analysis"></a>
## 🔐 تحليل المستخدمين وبيانات الاعتماد

<a id="user-information"></a>
### معلومات المستخدمين

```bash
# الحصول على معرفات أمان المستخدمين (SIDs)
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# آخر مستخدم سجل الدخول
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

<a id="password-hashes"></a>
### قيم تجزئة كلمات المرور

```bash
# استخراج قيم التجزئة من SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# أسرار LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# بيانات الاعتماد المخزنة مؤقتًا
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

<a id="mimikatz-integration"></a>
### التكامل مع Mimikatz بحسب الأصل

> **تصحيح:** `windows.lsadump` يستخرج أسرار LSA؛ ليس نسخة مدمجة من جميع وظائف Mimikatz، كما أن الاسم نفسه موسوم بالقدم في توثيق Vol3 الحالي. [مرجع الإضافة الرسمي](https://volatility3.readthedocs.io/en/stable/volatility3.plugins.windows.lsadump.html).

```bash
# استخراج بيانات اعتماد باستخدام إضافة mimikatz بحسب الأصل
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# إضافة مدمجة في Vol3 بحسب الأصل
vol -f memory.raw windows.lsadump
```

<a id="clipboard"></a>
### الحافظة

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

<a id="-file--dll-analysis"></a>
## 📁 تحليل الملفات ومكتبات DLL

<a id="file-scan"></a>
### البحث عن الملفات

```bash
# البحث عن الملفات في الذاكرة
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# ترشيح ملفات محددة
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

<a id="dump-files"></a>
### استخراج الملفات

> **توضيح للإزاحات:** يميز Vol3 بين `--virtaddr` للعناوين الافتراضية و`--physaddr` للعناوين المادية لكائن `_FILE_OBJECT`. الخيار `-Q` في Vol2 يستخدم عنوانًا ماديًا؛ لذلك لا تنقل قيمة `<OFFSET>` إلى `--virtaddr` دون التحقق من طبقة العنوان. [مرجع Vol2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#dumpfiles). [تعريف الخيارات الرسمي](https://volatility3.readthedocs.io/en/stable/_modules/volatility3/plugins/windows/dumpfiles.html).

```bash
# استخراج ملف بواسطة الإزاحة
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# استخراج جميع الملفات المتاحة
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

<a id="dll-analysis"></a>
### تحليل مكتبات DLL

```bash
# عرض مكتبات DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# عملية محددة
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# استخراج مكتبة DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

<a id="memory-strings"></a>
### السلاسل النصية في الذاكرة

> **توضيح للخطوة الناقصة:** تتلقى إضافة `strings` في Vol2 ملفًا موجودًا يربط الإزاحات بالسلاسل، ثم تحدد العمليات والعناوين المرتبطة بها؛ لا تنشئ `strings.txt` بنفسها كما قد يوحي تعليق الأصل. [مرجع Vol2 الرسمي](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference#strings).

```bash
# استخراج السلاسل النصية بحسب الأصل؛ الإضافة تربط ملف سلاسل موجودًا بالذاكرة
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# السلاسل النصية الخاصة بعملية محددة
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

<a id="-timeline-analysis"></a>
## 📅 تحليل التسلسل الزمني

<a id="timeline-creation"></a>
### إنشاء تسلسل زمني

```bash
# إنشاء التسلسل الزمني
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

<a id="mft-analysis"></a>
### تحليل جدول الملفات الرئيسي MFT

```bash
# جدول الملفات الرئيسي (MFT)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

<a id="-volatility-3"></a>
## 🔄 الإصدار Volatility 3

<a id="key-differences"></a>
### الفروق الأساسية

> **تصحيح للمقارنة:** وصف مخرجات Vol2 بأنها «نص فقط» تبسيط زائد؛ يتيح إطار المخرجات تنسيقات أخرى بحسب دعم الإضافة والـ renderer. [دليل استخدام Vol2 الرسمي](https://github.com/volatilityfoundation/volatility/wiki/Volatility-Usage#choosing-an-output-format).

|الميزة|Vol2|Vol3|
|---------|------|------|
|الملف التعريفي|تحديد يدوي باستخدام `--profile=`|اكتشاف تلقائي بحسب الأصل|
|تسمية الإضافات|`pslist`|`windows.pslist`|
|الأداء|أبطأ بحسب المقارنة|أسرع بحسب المقارنة|
|Python|Python 2.7|Python 3|
|المخرجات|نص فقط بحسب الأصل؛ راجع التوضيح|JSON وCSV وغيرها|

<a id="vol3-plugin-reference"></a>
### مرجع إضافات Vol3

```bash
# العمليات
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# الشبكة
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# سجل Windows
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# البرمجيات الخبيثة
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# بيانات الاعتماد
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# الملفات
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

<a id="linux-plugins-vol3"></a>
### إضافات Linux في Vol3

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="most-used-commands"></a>
### الأوامر الأكثر استخدامًا

|الغرض|أمر Vol2|أمر Vol3|
|---------|--------------|--------------|
|تحديد خصائص الصورة|`imageinfo`|`windows.info`|
|عرض العمليات|`pslist`|`windows.pslist`|
|شجرة العمليات|`pstree`|`windows.pstree`|
|البحث عن عمليات مخفية|`psscan`|`windows.psscan`|
|الشبكة|`netscan`|`windows.netscan`|
|DLLs|`dlllist`|`windows.dlllist`|
|استخراج عملية|`procdump`|`windows.pslist --dump`|
|البحث عن مؤشرات برمجيات خبيثة|`malfind`|`windows.malfind`|
|السجل|`hivelist`|`windows.registry.hivelist`|
|قيم التجزئة|`hashdump`|`windows.hashdump`|
|الملفات|`filescan`|`windows.filescan`|
|السلاسل النصية|`strings`|-|

<a id="investigation-workflow"></a>
### تسلسل عمل التحقيق

```
1. imageinfo/windows.info    → تحديد نظام التشغيل
2. pslist/pstree/psscan      → عرض العمليات
3. cmdline                    → فحص أسطر الأوامر
4. netscan                    → آثار اتصالات الشبكة
5. malfind                    → البحث عن شيفرة محقونة
6. dlllist                    → فحص مكتبات DLL المحملة
7. filescan                   → العثور على ملفات تستحق المراجعة
8. hashdump                   → استخراج بيانات الاعتماد المتاحة
9. timeliner                  → إنشاء التسلسل الزمني
```

---

<a id="-resources"></a>
## 📚 الموارد

- [مؤسسة Volatility](https://www.volatilityfoundation.org/)
- [مستودع Volatility3 على GitHub](https://github.com/volatilityfoundation/volatility3)
- [ويكي Volatility](https://github.com/volatilityfoundation/volatility/wiki)
- [مرجع التحليل الجنائي للذاكرة](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Mimikatz](../../Mimikatz/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)
- [رفع الصلاحيات في Windows](../../Windows-PrivEsc/translations/README.ar.md)

---

<p align="center">
  <b>🧠 أتقن التحليل الجنائي للذاكرة!</b><br>
  <i>استخرج أدلة الأحداث من ذاكرة RAM</i>
</p>
