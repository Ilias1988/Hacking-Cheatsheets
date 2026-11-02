<a id="-x64dbg---windows-debugger-cheatsheet"></a>
# 🔧 x64dbg - ورقة مرجعية لتنقيح برامج Windows

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Windows x64/x32 Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

يوضح الشعار اسم الأداة ووظيفتها كمنقّح لبرامج Windows بنسختي 32 و64 بت. تحتفظ الأمثلة بأسماء القوائم والاختصارات كما تظهر في الواجهة الإنجليزية.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو x64dbg](#-what-is-x64dbg)
- [التثبيت](#-installation)
- [نظرة عامة على الواجهة](#-interface-overview)
- [الاستخدام الأساسي](#-basic-usage)
- [نقاط التوقف](#-breakpoints)
- [التنفيذ خطوة بخطوة والتحكم بالتشغيل](#-stepping--execution)
- [عمليات الذاكرة](#-memory-operations)
- [السجلات](#-registers)
- [تحليل المكدس](#-stack-analysis)
- [تعديل الشيفرة الثنائية](#-patching)
- [كتابة السكربتات](#-scripting)
- [اختصارات لوحة المفاتيح](#-keyboard-shortcuts)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-x64dbg"></a>
## 🎯 ما هو x64dbg

**x64dbg** منقّح مفتوح المصدر لبرامج Windows ذات البنيتين x64 وx32. يواصل نهج OllyDbg مع مزايا حديثة:

- 🔍 **التنقيح في وضع المستخدم (User mode)** - تنقيح تطبيقات Windows
- 🦠 **تحليل البرمجيات الخبيثة** - تحليل الملفات التنفيذية المشبوهة
- 💥 **تطوير الاستغلالات** - البحث في استغلال ثغرات Windows
- 🔧 **الهندسة العكسية** - فهم سلوك البرامج الثنائية
- 🧩 **دعم الإضافات** - توسيع الوظائف بإضافات

<a id="features"></a>
### المزايا

| الميزة | الوصف |
| --------- | ------------- |
| **واجهة رسومية (GUI)** | واجهة حديثة قابلة للتخصيص |
| **دعم x64** | تنقيح كامل لبرامج 64 بت |
| **كتابة السكربتات** | لغة سكربتات مدمجة |
| **الإضافات** | مجموعة واسعة من الإضافات |
| **الرموز** | دعم رموز PDB |
| **مفتوح المصدر** | مجاني ويخضع للتطوير المستمر |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="download"></a>
### التنزيل

```
# الموقع الرسمي
https://x64dbg.com/

# الإصدارات على GitHub
https://github.com/x64dbg/x64dbg/releases

# تنزيل: snapshot_YYYY-MM-DD_HH-MM.zip
```

<a id="setup"></a>
### الإعداد

```
1. استخراج ملف ZIP إلى مسار دائم (مثل C:\x64dbg\)
2. تشغيل x96dbg.exe (مشغّل كلا الإصدارين)
   - x32dbg.exe → تنقيح برامج 32 بت
   - x64dbg.exe → تنقيح برامج 64 بت
3. (اختياري) الإضافة إلى PATH أو إنشاء اختصارات
```

<a id="recommended-plugins"></a>
### إضافات موصى بها

| الإضافة | الغرض |
| -------- | --------- |
| **ScyllaHide** | التصدي لآليات مكافحة التنقيح |
| **SwissArmyKnife** | أدوات مساعدة متنوعة |
| **x64dbgpy** | كتابة سكربتات Python |
| **TitanHide** | إخفاء مؤشرات التنقيح من مستوى النواة |
| **OllyDumpEx** | حفظ تفريغ الذاكرة |
| **Multiline Ultimate Assembler** | تعديل الشيفرة عبر عدة أسطر |

<a id="plugin-installation"></a>
### تثبيت الإضافات

```
1. تنزيل الإضافة (ملف DLL)
2. النسخ إلى: x64dbg\release\x32\plugins\ ‏(32 بت)
           x64dbg\release\x64\plugins\ ‏(64 بت)
3. إعادة تشغيل x64dbg
```

---

<a id="-interface-overview"></a>
## 🖥️ نظرة عامة على الواجهة

<a id="main-windows"></a>
### النوافذ الرئيسية

| النافذة | الوصف | الاختصار |
| -------- | ------------- | ---------- |
| **CPU** | عرض الشيفرة المفككة إلى تعليمات التجميع | Alt+C |
| **Log** | مخرجات التنقيح | Alt+L |
| **Memory Map** | مناطق الذاكرة | Alt+M |
| **Call Stack** | تسلسل الاستدعاءات | Alt+K |
| **Breakpoints** | جميع نقاط التوقف | Alt+B |
| **الرموز** | الرموز المحملة | Alt+E |
| **Threads** | قائمة خيوط التنفيذ | Alt+T |
| **Handles** | قائمة مقابض الموارد | Alt+H |

<a id="cpu-window-panels"></a>
### لوحات نافذة CPU

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

تضم النافذة أربع لوحات: التعليمات المفككة (Disassembly)، والسجلات (Registers)، وعرض الذاكرة بالنظام الست عشري (Hex Dump)، والمكدس (Stack).

<a id="status-bar"></a>
### شريط الحالة

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

يعرض شريط الحالة الوحدة والعنوان، والأعلام، ومؤشر التعليمة EIP/RIP، وحالة التنقيح.

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="starting-debugging"></a>
### بدء التنقيح

```
# فتح ملف تنفيذي
File → Open (F3)

# الإرفاق بعملية
File → Attach (Alt+A)

# الفتح والتشغيل
Debug → Run (F9)

# إعادة بدء التنقيح
Debug → Restart (Ctrl+F2)

# إنهاء التنقيح
Debug → Stop (Alt+F2)
```

<a id="command-bar"></a>
### شريط الأوامر

> **تصحيح للأصل:** الأمر `g` اسم بديل لـ `run`؛ يشغّل البرنامج، ويضع نقطة توقف لمرة واحدة عند العنوان المعطى، ولا يكتفي بتحريك العرض. كذلك `r` اسم بديل للتشغيل، فلا تعتمد أمثلة `r reg=val` أدناه لتعديل السجلات؛ استخدم واجهة تعديل القيم أو صيغة التعيين الموثقة. راجع [أسماء أمر التشغيل](https://help.x64dbg.com/en/latest/commands/debug-control/run.html) و[أمر mov/set](https://help.x64dbg.com/en/latest/commands/general-purpose/mov.html). أبقيت أمثلة الأصل للمقارنة.

```
# إدخال الأوامر في شريط الأوامر أسفل النافذة

# الانتقال إلى عنوان
g 0x401000
goto 0x401000

# الانتقال إلى تعبير
g GetProcAddress
g kernel32.LoadLibraryA

# البحث عن سلسلة نصية
/s "password"

# البحث عن بايتات بالنظام الست عشري
/x 90 90 90 90

# البحث عن نمط
/p "8B ?? 89"
```

<a id="navigation"></a>
### التنقل

```
# الانتقال إلى عنوان
Ctrl+G → إدخال العنوان

# الانتقال إلى نقطة الدخول
Debug → Entry Point

# اتباع العنوان في عارض التعليمات
النقر بزر الفأرة الأيمن → Follow in Disassembler

# العودة
- (مفتاح الطرح)

# التقدم
+ (مفتاح الجمع)
```

---

<a id="-breakpoints"></a>
## 🔴 نقاط التوقف

<a id="software-breakpoints-int3"></a>
### نقاط التوقف البرمجية (INT3)

```
# تبديل نقطة توقف عند العنوان الحالي
F2

# وضع نقطة توقف عبر أمر
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# وضع نقطة توقف شرطية
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# التوقف عند تحميل وحدة
bp $module(kernel32)
```

<a id="hardware-breakpoints"></a>
### نقاط التوقف العتادية

> **تصحيح:** يعني النوع `r` في `bph` القراءة **والكتابة**، وليس القراءة فقط. الحجم `8` متاح على x64، ويجب أن يوافق العنوان محاذاة الحجم. [التوثيق الرسمي](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html).

```
# وضع نقطة توقف عتادية (بحد أقصى 4)
النقر بزر الفأرة الأيمن → Breakpoint → Hardware → Execute
النقر بزر الفأرة الأيمن → Breakpoint → Hardware → Access
النقر بزر الفأرة الأيمن → Breakpoint → Hardware → Write

# عبر أمر
bph 0x401000, x, 1    # تنفيذ
bph 0x401000, r, 4    # قراءة (4 بايتات)
bph 0x401000, w, 4    # كتابة (4 بايتات)

# الأحجام: 1 أو 2 أو 4 أو 8 بايتات
```

<a id="memory-breakpoints"></a>
### نقاط توقف الذاكرة

```
# التوقف عند الوصول إلى الذاكرة
النقر بزر الفأرة الأيمن في عرض الذاكرة → Breakpoint → Memory → Access
النقر بزر الفأرة الأيمن في عرض الذاكرة → Breakpoint → Memory → Write

# عبر أمر
bpm 0x401000, r       # قراءة
bpm 0x401000, w       # كتابة
bpm 0x401000, x       # تنفيذ
```

<a id="conditional-breakpoints"></a>
### نقاط التوقف الشرطية

> **تصحيح للصيغة:** المعامل الثاني لـ `bp` هو اسم نقطة التوقف، وليس شرطها، والمعامل الثالث يحدد نوعها؛ لذلك لا تنشئ أمثلة `bp` التالية شرطا أو عدادا كما توحي تعليقات الأصل. اضبط الشرط والتسجيل من نافذة `Edit breakpoint`؛ يوثق x64dbg عداد الوصول باسم `$breakpointcounter`. راجع [صيغة bp](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html) و[نقاط التوقف الشرطية](https://help.x64dbg.com/en/latest/introduction/ConditionalBreakpoint.html).

```
# التوقف إذا تحقق الشرط
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# التوقف بناء على عدد مرات الوصول
bp 0x401000, "", 5    # التوقف عند الوصول للمرة الخامسة

# تسجيل البيانات دون إيقاف التنفيذ
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

<a id="managing-breakpoints"></a>
### إدارة نقاط التوقف

```
# عرض جميع نقاط التوقف
نافذة Breakpoints ‏(Alt+B)

# حذف نقطة توقف
bc 0x401000          # حذف نقطة محددة
bc                   # حذف الكل

# التفعيل أو التعطيل
bd 0x401000          # تعطيل
be 0x401000          # تفعيل

# تعطيل الكل
bd *

# تفعيل الكل
be *
```

---

<a id="-stepping--execution"></a>
## ▶️ التنفيذ خطوة بخطوة والتحكم بالتشغيل

<a id="stepping"></a>
### التنفيذ خطوة بخطوة

| الإجراء | الاختصار | الوصف |
| -------- | ---------- | ------------- |
| الدخول خطوة بخطوة (Step Into) | F7 | تنفيذ تعليمة واحدة |
| التنفيذ مع تجاوز الاستدعاء (Step Over) | F8 | تجاوز تفاصيل الاستدعاءات أثناء التنفيذ خطوة بخطوة |
| التشغيل حتى العودة (Step Out) | Ctrl+F9 | التشغيل حتى العودة |
| التشغيل حتى التحديد (Run to Selection) | F4 | التشغيل حتى موضع المؤشر |
| التشغيل حتى شيفرة المستخدم (Run to User Code) | Alt+F9 | مغادرة شيفرة النظام |

<a id="running"></a>
### التشغيل

| الإجراء | الاختصار | الوصف |
| -------- | ---------- | ------------- |
| تشغيل (Run) | F9 | متابعة التنفيذ |
| تشغيل مع تجاوز (Run Skipping) | Ctrl+F7 | تجاوز التعليمة الحالية |
| إيقاف مؤقت (Pause) | F12 | إيقاف التنفيذ مؤقتا |
| إعادة البدء | Ctrl+F2 | إعادة بدء التنقيح |
| إنهاء (Close) | Alt+F2 | إنهاء التنقيح |

<a id="trace"></a>
### التتبع

```
# التتبع مع الدخول إلى الاستدعاءات (تسجيل كل التعليمات)
Debug → Trace Into (Ctrl+F7)

# التتبع مع تجاوز تفاصيل الاستدعاءات
Debug → Trace Over (Ctrl+F8)

# تسجيل التتبع
Debug → Trace Record

# إيقاف التتبع
Debug → Stop Trace
```

<a id="skip-instruction"></a>
### تجاوز تعليمة

```
# تجاوز التعليمة الحالية دون تنفيذها
النقر بزر الفأرة الأيمن → Set New Origin Here

# أو
Ctrl+Shift+O
```

---

<a id="-memory-operations"></a>
## 🔍 عمليات الذاكرة

<a id="memory-dump"></a>
### عرض محتويات الذاكرة

```
# اتباع العنوان في عرض الذاكرة
النقر بزر الفأرة الأيمن → Follow in Dump → Address/Selection

# الانتقال إلى عنوان في عرض الذاكرة
Ctrl+G (في نافذة عرض الذاكرة)

# تغيير تنسيق عرض الذاكرة
النقر بزر الفأرة الأيمن → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

<a id="memory-map"></a>
### خريطة الذاكرة

توضح الحقول التالية العنوان والحجم والقسم ونوع الذاكرة (صورة برنامج أو ذاكرة خاصة أو معيّنة)، وصلاحيات الحماية، واسم الوحدة. أبقيت تسميات الحقول كما تظهر في الواجهة.

```
# فتح Memory Map
View → Memory Map (Alt+M)

# يعرض جميع مناطق الذاكرة:
- Address
- Size
- Section
- Type (Image/Private/Mapped)
- Protection (Execute/Read/Write)
- Module name
```

<a id="search-memory"></a>
### البحث في الذاكرة

```
# البحث من شريط الأوامر
/s "string"           # سلسلة ASCII
/s L"string"          # سلسلة Unicode
/x 90 90 90           # بايتات بالنظام الست عشري
/p "8B ?? 89"         # نمط يتضمن محارف بديلة

# العثور على المراجع
النقر بزر الفأرة الأيمن → Find references to → Selected Address

# العثور على السلاسل النصية
النقر بزر الفأرة الأيمن → Search for → All Strings
```

<a id="memory-protection"></a>
### حماية الذاكرة

```
# عرض الحماية
Memory Map → عمود Protection

# قيم الحماية الشائعة
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

<a id="dump-memory"></a>
### حفظ تفريغ الذاكرة

```
# حفظ تفريغ الذاكرة في ملف
النقر بزر الفأرة الأيمن داخل Memory Map → Dump Memory to File

# حفظ التفريغ باستخدام إضافات
Plugins → OllyDumpEx → Dump
```

---

<a id="-registers"></a>
## 📊 السجلات

<a id="register-panel"></a>
### لوحة السجلات

```
# سجلات عامة الاستخدام (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# سجلات عامة الاستخدام (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# سجلات المقاطع
CS, DS, SS, ES, FS, GS

# الأعلام (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

<a id="modify-registers"></a>
### تعديل السجلات

```
# النقر مرتين على السجل لتحريره
# أو النقر بزر الفأرة الأيمن → Modify Value

# عبر شريط الأوامر
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # ضبط علم الصفر
```

<a id="important-flags"></a>
### أعلام مهمة

| العلم | الاسم | متى يضبط |
| ------ | ------ | ---------- |
| ZF | علم الصفر (Zero Flag) | عندما تكون النتيجة صفرا |
| CF | علم الحمل (Carry Flag) | عند تجاوز السعة في العمليات دون إشارة |
| SF | علم الإشارة (Sign Flag) | عندما تكون النتيجة سالبة |
| OF | علم تجاوز السعة (Overflow Flag) | عند تجاوز السعة في العمليات ذات الإشارة |
| DF | علم الاتجاه (Direction Flag) | لتحديد اتجاه معالجة السلاسل |
| TF | علم الفخ (Trap Flag) | للتنفيذ خطوة بخطوة |

<a id="toggle-flags"></a>
### تبديل الأعلام

```
# النقر على حرف العلم في لوحة السجلات
# أو استخدام الأوامر:
r zf=!zf         # عكس حالة علم الصفر
r cf=0           # مسح علم الحمل
r sf=1           # ضبط علم الإشارة
```

---

<a id="-stack-analysis"></a>
## 📚 تحليل المكدس

<a id="stack-panel"></a>
### لوحة المكدس

تسرد اللوحة العنوان والقيمة والتعليق إن وجد. تتيح قائمة السياق اتباع قيمة DWORD في عارض التعليمات أو الذاكرة، أو تعديلها.

```
# يعرض محتويات المكدس مع:
- Address
- Value
- Comments (if available)

# خيارات النقر بزر الفأرة الأيمن:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify
```

<a id="call-stack"></a>
### مكدس الاستدعاءات

تعرض الحقول عنوان العودة، والمستدعي (From)، والدالة المستدعاة (To)، والحجم، والتعليق.

```
# فتح Call Stack
View → Call Stack (Alt+K)

# يعرض:
- Return Address
- From (caller)
- To (callee)
- Size
- Comment
```

<a id="stack-operations"></a>
### عمليات المكدس

```
# الانتقال إلى عنوان في المكدس
النقر بزر الفأرة الأيمن → Follow DWORD in Stack

# تعديل قيمة في المكدس
النقر بزر الفأرة الأيمن → Modify → Edit

# محاكاة Push/Pop
r esp=esp-4      # Push ‏(32 بت)
r esp=esp+4      # Pop ‏(32 بت)
r rsp=rsp-8      # Push ‏(64 بت)
r rsp=rsp+8      # Pop ‏(64 بت)
```

<a id="stack-layout-32-bit-stdcall"></a>
### بنية المكدس (stdcall بنظام 32 بت)

```
[ESP]      → Return Address
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

After PUSH EBP; MOV EBP, ESP:
[EBP]      → Saved EBP
[EBP+4]    → Return Address
[EBP+8]    → Arg1
[EBP-4]    → Local1
```

في المخطط: يشير ESP إلى عنوان العودة، وتليه الوسائط. بعد حفظ EBP وإنشاء إطار المكدس، يحتوي [EBP] على EBP المحفوظ، ويليه عنوان العودة والوسائط، بينما تقع المتغيرات المحلية عند إزاحات سالبة.

---

<a id="-patching"></a>
## 🔧 تعديل الشيفرة الثنائية

<a id="assemble-instructions"></a>
### تجميع التعليمات

```
# النقر بزر الفأرة الأيمن على التعليمة → Assemble ‏(Space)
# إدخال التعليمة أو التعليمات الجديدة

# أمثلة:
NOP                    # لا عملية
JMP 0x401020           # قفزة
MOV EAX, 0             # تصفير EAX
XOR EAX, EAX           # تصفير EAX (أقصر حجما)
RET                    # عودة
```

<a id="fill-with-nops"></a>
### الملء بتعليمات NOP

```
# تحديد نطاق → النقر بزر الفأرة الأيمن → Binary → Fill with NOPs

# أو التحديد والضغط على Space:
# إدخال عدة تعليمات NOP
```

<a id="binary-edit"></a>
### تحرير البيانات الثنائية

```
# تحرير البايتات مباشرة
النقر بزر الفأرة الأيمن → Binary → Edit

# الملء بقيمة
النقر بزر الفأرة الأيمن → Binary → Fill

# نسخ البيانات الثنائية ولصقها
النقر بزر الفأرة الأيمن → Binary → Copy
النقر بزر الفأرة الأيمن → Binary → Paste
```

<a id="common-patches"></a>
### تعديلات شائعة

| قبل | بعد | الغرض |
| -------- | ------- | --------- |
| `74 XX` (JE) | `75 XX` (JNE) | عكس الشرط |
| `75 XX` (JNE) | `74 XX` (JE) | عكس الشرط |
| `74 XX` (JE) | `EB XX` (JMP) | القفز دائما |
| `74 XX` (JE) | `90 90` (NOP) | عدم القفز مطلقا |
| `0F 84` (JE) | `0F 85` (JNE) | عكس الشرط في قفزة قريبة (near) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | تجاوز الاستدعاء |

<a id="save-patches"></a>
### حفظ التعديلات

```
# الطريقة 1: Patch File
File → Patch File

# الطريقة 2: تصدير التعديلات
النقر بزر الفأرة الأيمن → Copy → Selection
# اللصق في محرر ست عشري

# الطريقة 3: استخدام إضافة
Plugins → Export modified executable
```

---

<a id="-scripting"></a>
## 📝 كتابة السكربتات

<a id="command-syntax"></a>
### صيغة الأوامر

```
# المتغيرات
$var = 0x401000

# المقارنة
cmp eax, 0

# الانتقال الشرطي
je label
jne label

# التسميات
label:

# التعليقات
; هذا تعليق
```

<a id="basic-script"></a>
### سكربت أساسي

> **توضيح للمثال:** يعيد `find` عنوانا افتراضيا مطلقا في `$result`، فلا تعد إضافة هذه القيمة إلى عنوان البداية خطوة صحيحة للتقدم إلى التطابق التالي. كما أن مطابقة تمهيد دالة لا تحدد `WinMain` وحدها. أبقي السكربت كما ورد؛ راجع [سلوك find الرسمي](https://help.x64dbg.com/en/latest/commands/searching/find.html).

```
; سكربت للعثور على WinMain
mov $addr, 0x401000        ; عنوان البداية
loop:
  find $addr, "8B EC"       ; Find PUSH EBP; MOV EBP, ESP
  cmp $result, 0
  je notfound
  msg "Found at: {$result}"
  add $addr, $result
  add $addr, 1
  jmp loop
notfound:
  msg "Pattern not found"
ret
```

<a id="script-commands"></a>
### أوامر السكربتات

| الأمر | الوصف |
| --------- | ------------- |
| `msg "text"` | عرض رسالة |
| `log "text"` | تسجيل رسالة في وحدة التحكم |
| `find addr, "pattern"` | البحث عن نمط |
| `bp addr` | وضع نقطة توقف |
| `bc addr` | حذف نقطة توقف |
| `sti` | الدخول خطوة بخطوة (Step Into) |
| `sto` | التنفيذ مع تجاوز الاستدعاء (Step Over) |
| `run` | تشغيل (Run) |
| `ret` | العودة أو التوقف |

<a id="run-script"></a>
### تشغيل سكربت

```
# تحميل السكربت وتشغيله
Scripts → Run Script

# أو عبر شريط الأوامر
scr "scriptfile.txt"
```

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ اختصارات لوحة المفاتيح

> **ملاحظة على الأصل:** يسند الدليل `Ctrl+F7` إلى إجراءين مختلفين في قسمي التشغيل والتتبع؛ تحقق من الاختصار المعين في نسختك قبل الاعتماد على هذه الجداول. أبقيت الاختصارات الأصلية دون تغيير.

<a id="general"></a>
### عام

| الاختصار | الإجراء |
| ---------- | -------- |
| F3 | فتح ملف |
| Alt+A | الإرفاق بعملية |
| Ctrl+F2 | إعادة البدء |
| Alt+F2 | إنهاء التنقيح |
| Ctrl+G | الانتقال إلى عنوان |
| Ctrl+F | بحث |

<a id="debugging"></a>
### التنقيح

| الاختصار | الإجراء |
| ---------- | -------- |
| F2 | تبديل نقطة التوقف |
| F4 | التشغيل حتى التحديد |
| F7 | الدخول خطوة بخطوة (Step Into) |
| F8 | التنفيذ مع تجاوز الاستدعاء (Step Over) |
| F9 | تشغيل (Run) |
| F12 | إيقاف مؤقت (Pause) |
| Ctrl+F9 | التشغيل حتى العودة |

<a id="navigation-1"></a>
### التنقل

| الاختصار | الإجراء |
| ---------- | -------- |
| - | العودة |
| + | التقدم |
| * | الانتقال إلى EIP/RIP |
| Enter | اتباع الاستدعاء أو القفزة |
| Escape | العودة |

<a id="views"></a>
### طرق العرض

| الاختصار | الإجراء |
| ---------- | -------- |
| Alt+C | المعالج (CPU) |
| Alt+L | السجل (Log) |
| Alt+M | خريطة الذاكرة (Memory Map) |
| Alt+K | مكدس الاستدعاءات (Call Stack) |
| Alt+B | نقاط التوقف (Breakpoints) |
| Alt+E | الرموز (Symbols) |
| Alt+T | خيوط التنفيذ (Threads) |

<a id="editing"></a>
### التحرير

| الاختصار | الإجراء |
| ---------- | -------- |
| Space | تجميع تعليمات |
| ; | إضافة تعليق |
| : | إضافة تسمية |
| N | إعادة تسمية |

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### أوامر أساسية

| الأمر | الوصف |
| --------- | ------------- |
| `bp addr` | وضع نقطة توقف |
| `bc addr` | حذف نقطة توقف |
| `bph addr, x` | نقطة توقف عتادية |
| `g addr` | الانتقال إلى عنوان |
| `r reg=val` | تعيين قيمة سجل |
| `sti` | الدخول خطوة بخطوة (Step Into) |
| `sto` | التنفيذ مع تجاوز الاستدعاء (Step Over) |
| `run` | تشغيل (Run) |

<a id="common-patterns"></a>
### أنماط شائعة

| البايتات | التعليمة |
| ------- | ------------- |
| `90` | NOP |
| `CC` | INT3 (breakpoint) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

<a id="analysis-workflow"></a>
### خطوات التحليل

```
1. تحميل الملف التنفيذي       → F3
2. وضع نقطة توقف عند الدخول   → F2 عند نقطة الدخول
3. التشغيل حتى نقطة التوقف    → F9
4. تنفيذ الشيفرة خطوة بخطوة   → F7/F8
5. فحص السجلات والذاكرة       → اللوحات
6. وضع نقاط توقف إضافية
7. متابعة التحليل
8. تعديل الشيفرة عند الحاجة   → Space
9. حفظ الملف المعدل
```

<a id="anti-debug-bypass"></a>
### تجاوز آليات مكافحة التنقيح

```
# تثبيت إضافة ScyllaHide
Plugins → ScyllaHide → Options → Select profile

# تعديلات يدوية:
- استبدال استدعاءات IsDebuggerPresent بتعليمات NOP
- تعديل PEB.BeingDebugged
- التعامل مع الاستثناءات بصورة صحيحة
```

---

<a id="-resources"></a>
## 📚 المصادر

- [موقع x64dbg الرسمي](https://x64dbg.com/)
- [مستودع x64dbg على GitHub](https://github.com/x64dbg/x64dbg)
- [توثيق x64dbg](https://help.x64dbg.com/)
- [إضافات x64dbg](https://github.com/x64dbg/x64dbg/wiki/Plugins)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Ghidra](../../Ghidra/translations/README.ar.md)
- [GDB](../../GDB/translations/README.ar.md)
- [تصعيد الصلاحيات في Windows](../../Windows-PrivEsc/translations/README.ar.md)

---

<p align="center">
  <b>🔧 أتقن تنقيح برامج Windows!</b><br>
  <i>x64dbg - الأداة الحديثة التي تواصل نهج OllyDbg</i>
</p>
