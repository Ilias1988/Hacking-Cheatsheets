<a id="-gdb---gnu-debugger-cheatsheet"></a>
# 🐛 GDB - مرجع سريع لمصحّح GNU

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    GNU Debugger
```

> يكتب الرسم النصي اسم GNU Debugger بالإنجليزية؛ GDB مصحّح أخطاء يتيح تتبّع تنفيذ البرنامج وفحص حالته.

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو GDB](#-what-is-gdb)
- [التثبيت والإعداد](#-installation--setup)
- [الاستخدام الأساسي](#-basic-usage)
- [نقاط التوقف (Breakpoints)](#-breakpoints)
- [التحكم في التنفيذ](#-execution-control)
- [فحص الذاكرة](#-examining-memory)
- [المسجّلات (Registers)](#-registers)
- [تحليل المكدس (Stack)](#-stack-analysis)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [تطوير استغلال الثغرات](#-exploit-development)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-gdb"></a>
## 🎯 ما هو GDB

**GDB**، وهو مصحّح GNU، أداة تصحيح أخطاء شائعة في Linux، وتُستخدم في:

- 🐛 **تصحيح الأخطاء** - العثور على الأخطاء وإصلاحها
- 🔍 **الهندسة العكسية** - فهم سلوك الملفات التنفيذية
- 💥 **تطوير استغلال الثغرات** - تجاوز سعة المخزن المؤقت والبرمجة الموجّهة بالعودة (ROP)
- 📊 **تحليل الملفات التنفيذية** - فحص مسار التنفيذ
- 🧪 **تحديات CTF** - حل تحديات الهندسة العكسية واستغلال البرامج (RE/pwn)

<a id="supported-architectures"></a>
### المعماريات المدعومة

| المعمارية | ملاحظات |
|--------------|-------|
| x86 | Intel ‏32-bit |
| x86-64 | Intel/AMD ‏64-bit |
| ARM | ARM ‏32-bit |
| ARM64 | ARM ‏64-bit |
| MIPS | معالجات MIPS |
| PowerPC | معالجات PPC |

---

<a id="-installation--setup"></a>
## 🚀 التثبيت والإعداد

<a id="install-gdb"></a>
### تثبيت GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (استخدام lldb بديلًا، أو)
brew install gdb
```

<a id="install-gef-gdb-enhanced-features"></a>
### تثبيت GEF (ميزات GDB المحسّنة)

```bash
# GEF - الخيار الذي يفضّله المصدر
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# أو التثبيت يدويًا
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

<a id="install-peda"></a>
### تثبيت PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

<a id="install-pwndbg"></a>
### تثبيت pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

<a id="verify-installation"></a>
### التحقق من التثبيت

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="starting-gdb"></a>
### بدء GDB

```bash
# تصحيح ملف تنفيذي
gdb ./program

# التصحيح مع وسائط
gdb --args ./program arg1 arg2

# الارتباط بعملية قيد التشغيل
gdb -p <PID>
gdb attach <PID>

# الوضع الهادئ (دون مقدمة)
gdb -q ./program

# تنفيذ أوامر من ملف
gdb -x commands.gdb ./program
```

> **تصحيح لصيغة التشغيل:** السطر `gdb attach <PID>` منقول كما ورد، لكنه ليس صيغة استدعاء لأمر attach من صدفة النظام؛ يعامل GDB الوسيط الأول غير الخيار كاسم ملف تنفيذي. تختلف هذه الحالة عن خيار `-p` وعن أمر `attach` داخل جلسة GDB، وكلاهما ظاهر في أمثلة الصفحة. [دليل تشغيل GDB الرسمي](https://www.sourceware.org/gdb/current/onlinedocs/gdb.html/Invoking-GDB.html).

<a id="basic-commands"></a>
### الأوامر الأساسية

| الأمر | الاختصار | الوصف |
|---------|-------|-------------|
| `run` | `r` | بدء البرنامج |
| `run args` | `r args` | التشغيل مع وسائط |
| `continue` | `c` | متابعة التنفيذ |
| `quit` | `q` | الخروج من GDB |
| `help` | `h` | عرض المساعدة |
| `help <cmd>` | `h <cmd>` | مساعدة خاصة بأمر |

<a id="file-commands"></a>
### أوامر الملفات

```bash
# تحميل ملف تنفيذي
(gdb) file ./program

# تحميل الرموز
(gdb) symbol-file ./program.debug

# تحميل تفريغ الذاكرة (Core dump)
(gdb) core-file core

# تعيين مجلد العمل
(gdb) cd /path/to/dir

# تعيين الوسائط
(gdb) set args arg1 arg2
```

---

<a id="-breakpoints"></a>
## 🔴 نقاط التوقف (Breakpoints)

<a id="setting-breakpoints"></a>
### تعيين نقاط التوقف

```bash
# التوقف عند دالة
(gdb) break main
(gdb) b main

# التوقف عند عنوان
(gdb) break *0x08048456
(gdb) b *0x08048456

# التوقف عند سطر
(gdb) break file.c:25
(gdb) b 25

# التوقف عند إزاحة من بداية الدالة
(gdb) break *main+48

# نقطة توقف مؤقتة (تُحذف تلقائيًا)
(gdb) tbreak main
```

<a id="conditional-breakpoints"></a>
### نقاط توقف شرطية

```bash
# التوقف عندما يتحقق الشرط
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# إضافة شرط إلى نقطة توقف موجودة
(gdb) condition 1 $eax == 0

# إزالة الشرط
(gdb) condition 1
```

<a id="managing-breakpoints"></a>
### إدارة نقاط التوقف

```bash
# سرد نقاط التوقف
(gdb) info breakpoints
(gdb) info b

# حذف نقطة توقف
(gdb) delete 1
(gdb) d 1

# حذف جميع نقاط التوقف
(gdb) delete
(gdb) d

# تعطيل نقطة توقف
(gdb) disable 1
(gdb) dis 1

# تفعيل نقطة توقف
(gdb) enable 1
(gdb) en 1

# تعطيل الكل
(gdb) disable

# تفعيل الكل
(gdb) enable
```

<a id="watchpoints-data-breakpoints"></a>
### نقاط مراقبة البيانات (Watchpoints)

```bash
# مراقبة تغيّرات المتغير
(gdb) watch variable
(gdb) watch *0x08048456

# مراقبة عمليات القراءة
(gdb) rwatch variable

# مراقبة القراءة أو الكتابة
(gdb) awatch variable

# مراقبة تعبير
(gdb) watch ($eax > 10)
```

<a id="catchpoints"></a>
### نقاط التقاط الأحداث (Catchpoints)

```bash
# التقاط استدعاءات النظام
(gdb) catch syscall

# التقاط استدعاء نظام محدد
(gdb) catch syscall write

# التقاط الإشارات
(gdb) catch signal SIGSEGV

# التقاط الاستثناءات
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-execution-control"></a>
## ▶️ التحكم في التنفيذ

<a id="stepping"></a>
### التنفيذ خطوة بخطوة

```bash
# تنفيذ تعليمة واحدة مع الدخول في الاستدعاءات
(gdb) stepi
(gdb) si

# تنفيذ سطر واحد مع الدخول في الاستدعاءات
(gdb) step
(gdb) s

# تنفيذ التعليمة مع تجاوز الاستدعاءات دون الدخول فيها
(gdb) nexti
(gdb) ni

# تنفيذ سطر مع تجاوز الاستدعاءات
(gdb) next
(gdb) n

# المتابعة حتى العودة من الدالة
(gdb) finish
(gdb) fin

# المتابعة حتى عنوان محدد
(gdb) until *0x08048456
(gdb) u *0x08048456
```

<a id="running"></a>
### التشغيل

```bash
# بدء البرنامج أو إعادة تشغيله
(gdb) run
(gdb) r

# المتابعة بعد نقطة توقف
(gdb) continue
(gdb) c

# التشغيل حتى نقطة التوقف التالية
(gdb) continue

# تخطي N نقاط توقف
(gdb) continue 5

# القفز إلى عنوان (إجراء خطِر!)
(gdb) jump *0x08048456
```

> **توضيح للعدّاد:** العدد بعد `continue` يضبط مرات تجاهل نقطة التوقف في موضع التوقف الحالي؛ لا يعني تخطي العدد نفسه من نقاط توقف مختلفة في البرنامج. يُعتد بهذا الوسيط عندما يكون التوقف بسبب نقطة توقف. [دليل المتابعة والتنفيذ خطوة بخطوة](https://sourceware.org/gdb/current/onlinedocs/gdb/Continuing-and-Stepping.html).

<a id="process-control"></a>
### التحكم في العملية

```bash
# إنهاء العملية الحالية
(gdb) kill

# فصل الارتباط بالعملية
(gdb) detach

# الارتباط بعملية
(gdb) attach <PID>

# متابعة العملية الأم أو الابنة بعد fork
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="-examining-memory"></a>
## 🔍 فحص الذاكرة

<a id="print-command"></a>
### الأمر print

```bash
# عرض متغير
(gdb) print variable
(gdb) p variable

# العرض بالنظام الست عشري
(gdb) print/x variable
(gdb) p/x $eax

# العرض بالنظام الثنائي
(gdb) p/t variable

# العرض بالنظام العشري
(gdb) p/d variable

# العرض كمحرف
(gdb) p/c $al

# العرض كسلسلة نصية
(gdb) p/s pointer

# عرض مصفوفة
(gdb) p *array@10     # 10 عناصر
```

<a id="examine-command-x"></a>
### أمر فحص الذاكرة (x)

```bash
# Syntax: x/NFU address
# N = count, F = format, U = unit size

# Formats: x(hex), d(decimal), u(unsigned), o(octal), 
#          t(binary), c(char), s(string), i(instruction)
# Units: b(byte), h(half=2), w(word=4), g(giant=8)

# فحص الذاكرة
(gdb) x/10x $esp           # 10 كلمات بالنظام الست عشري بدءًا من ESP
(gdb) x/20wx 0x08048456    # 20 كلمة بالنظام الست عشري
(gdb) x/s 0x08048456       # السلسلة عند العنوان
(gdb) x/10i $eip           # 10 تعليمات بدءًا من EIP
(gdb) x/10gx $rsp          # 10 وحدات كبيرة (8 بايتات) بدءًا من RSP

# أنماط شائعة
(gdb) x/100x $esp          # فحص المكدس
(gdb) x/20i $eip           # تفكيك التعليمات
(gdb) x/s $eax             # مؤشر إلى سلسلة نصية
```

> **قراءة صيغة x:** يحدّد N عدد الوحدات، وF تنسيق العرض، وU حجم الوحدة. تمثّل b بايتًا، وh بايتين، وw أربعة بايتات، وg ثمانية بايتات. يحتفظ GDB بحجم الوحدة الذي استُخدم سابقًا؛ لذلك لا تضمن صيغة مثل `x/10x` عرض كلمات من أربعة بايتات في كل سياق، رغم الوصف المختصر في المصدر. [دليل فحص الذاكرة](https://sourceware.org/gdb/current/onlinedocs/gdb/Memory.html).

<a id="memory-regions"></a>
### مناطق الذاكرة

```bash
# عرض خريطة الذاكرة
(gdb) info proc mappings

# عرض الأقسام
(gdb) maintenance info sections

# عرض الملفات
(gdb) info files
```

<a id="disassembly"></a>
### تفكيك التعليمات إلى Assembly

```bash
# تفكيك تعليمات دالة
(gdb) disassemble main
(gdb) disas main

# تفكيك تعليمات الدالة الحالية
(gdb) disassemble
(gdb) disas

# تفكيك تعليمات نطاق محدد
(gdb) disas 0x08048456, 0x08048500

# تفكيك التعليمات مع الشيفرة المصدرية إن توفرت
(gdb) disas /m main

# تعيين صيغة عرض التعليمات
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

<a id="-registers"></a>
## 📊 المسجّلات (Registers)

<a id="view-registers"></a>
### عرض المسجّلات

```bash
# جميع المسجّلات
(gdb) info registers
(gdb) i r

# مسجّل محدد
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# جميع المسجّلات بما فيها FP/SSE
(gdb) info all-registers
```

<a id="modify-registers"></a>
### تعديل المسجّلات

```bash
# تعيين قيمة المسجّل
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# تعيين الرايات (Flags)
(gdb) set $eflags = 0x246
```

<a id="x86-registers-reference"></a>
### مرجع مسجّلات x86

| المسجّل | 64-bit | 32-bit | 16-bit | 8-bit | الغرض |
|----------|--------|--------|--------|-------|---------|
| المجمّع (Accumulator) | RAX | EAX | AX | AL/AH | قيمة الإرجاع |
| الأساس (Base) | RBX | EBX | BX | BL/BH | استخدام عام |
| العدّاد (Counter) | RCX | ECX | CX | CL/CH | عدّاد الحلقة |
| البيانات (Data) | RDX | EDX | DX | DL/DH | إدخال وإخراج وضرب |
| مؤشر المصدر | RSI | ESI | SI | SIL | مصدر السلسلة |
| مؤشر الوجهة | RDI | EDI | DI | DIL | وجهة السلسلة |
| مؤشر قاعدة الإطار | RBP | EBP | BP | BPL | إطار المكدس |
| مؤشر المكدس | RSP | ESP | SP | SPL | قمة المكدس |
| التعليمة | RIP | EIP | IP | - | التعليمة التالية |

<a id="x64-additional-registers"></a>
### مسجّلات x64 الإضافية

```
R8-R15 : مسجّلات للاستخدام العام
R8D-R15D : الأجزاء الدنيا بحجم 32-bit
R8W-R15W : الأجزاء الدنيا بحجم 16-bit
R8B-R15B : الأجزاء الدنيا بحجم 8-bit
```

---

<a id="-stack-analysis"></a>
## 📚 تحليل المكدس (Stack)

<a id="view-stack"></a>
### عرض المكدس

```bash
# تتبّع مكدس الاستدعاءات
(gdb) backtrace
(gdb) bt

# تتبّع كامل مع المتغيرات المحلية
(gdb) backtrace full
(gdb) bt full

# عرض الإطار
(gdb) frame
(gdb) f

# اختيار إطار
(gdb) frame 2
(gdb) f 2

# عرض محتويات المكدس
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

<a id="stack-frame"></a>
### إطار المكدس

```bash
# عرض معلومات الإطار
(gdb) info frame
(gdb) i f

# عرض المتغيرات المحلية
(gdb) info locals

# عرض الوسائط
(gdb) info args

# التنقل صعودًا أو نزولًا بين الإطارات
(gdb) up
(gdb) down
```

<a id="stack-commands"></a>
### أوامر المكدس

```bash
# فحص عنوان العودة
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# فحص قيمة EBP المحفوظة
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# عرض تخطيط المكدس
(gdb) x/20wx $esp-20  # قبل الموضع وبعده
```

---

<a id="-gefpedapwndbg"></a>
## 🎨 GEF/PEDA/pwndbg

<a id="gef-commands"></a>
### أوامر GEF

```bash
# عرض سياق التنفيذ تلقائيًا
gef➤ context

# Checksec (ميزات الحماية)
gef➤ checksec

# البحث عن النمط
gef➤ pattern create 200
gef➤ pattern search $rsp

# العثور على سلسلة أو قيمة
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# تحليل الكومة (Heap)
gef➤ heap chunks
gef➤ heap bins

# مقاطع تعليمات ROP
gef➤ ropgadget

# مساعد سلاسل التنسيق (Format string)
gef➤ format-string-helper

# VMMap (خريطة الذاكرة)
gef➤ vmmap

# تتبّع سلسلة المؤشرات
gef➤ dereference $rsp 20

# Telescope (تتبّع ذكي للمؤشرات)
gef➤ telescope $rsp 20

# معلومات ELF
gef➤ elf-info

# قيمة حارس المكدس (Canary)
gef➤ canary

# معلومات PIE/ASLR
gef➤ pie
gef➤ aslr
```

<a id="peda-commands"></a>
### أوامر PEDA

```bash
# فحص ميزات الحماية
peda➤ checksec

# العثور على سلسلة
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# إنشاء النمط والبحث عنه
peda➤ pattern create 200
peda➤ pattern offset $eip

# مقاطع تعليمات ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# شيفرة Shellcode
peda➤ shellcode generate x86/linux exec

# السياق
peda➤ context all

# تعليمات Assembly
peda➤ assemble
```

<a id="pwndbg-commands"></a>
### أوامر pwndbg

```bash
# السياق
pwndbg> context

# فحص ميزات الحماية
pwndbg> checksec

# البحث
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# نمط دوري (Cyclic)
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# الكومة (Heap)
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="-exploit-development"></a>
## 💥 تطوير استغلال الثغرات

<a id="finding-offset"></a>
### تحديد الإزاحة (Offset)

```bash
# الطريقة 1: النمط باستخدام GEF
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# الطريقة 2: النمط باستخدام pwntools
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

> **ملاحظة على اكتمال المثال:** ينشئ GEF نمطًا دوريًا ذا مقاطع مميزة، لكن السطر الأول للتشغيل هنا يمرّر حروف A متكررة بدل النمط المنشأ. ويعرض مثال pwntools النمط دون أن تتضمن الخطوات المعروضة إنشاء input.txt. كما تختلف أسماء بعض أوامر الإضافات وأسماؤها المستعارة بحسب الإضافة والإصدار؛ فلا تُعد جميعها أوامر GDB أصلية. أُبقيت الأسطر كما في المصدر. [توثيق pattern في GEF](https://hugsy.github.io/gef/commands/pattern/).

<a id="buffer-overflow-basics"></a>
### أساسيات تجاوز سعة المخزن المؤقت

```bash
# التحقق من عشوائية تخطيط العناوين (ASLR)
$ cat /proc/sys/kernel/randomize_va_space
# 0=off, 1=partial, 2=full

# تعطيل ASLR للاختبار
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# فحص وسائل حماية الملف التنفيذي
gef➤ checksec

# تحديد موضع عنوان العودة
(gdb) x/20wx $esp     # قبل التعطّل
(gdb) i f             # معلومات الإطار
```

<a id="finding-gadgets"></a>
### العثور على مقاطع التعليمات (Gadgets)

```bash
# العثور على "/bin/sh"
gef➤ search-pattern "/bin/sh"

# العثور على عنوان system()
(gdb) p system
(gdb) p &system

# العثور على مقطع pop rdi في 64-bit
gef➤ ropgadget --grep "pop rdi"

# العثور على pop; ret في 32-bit
gef➤ ropgadget --grep "pop"
```

<a id="write-memory"></a>
### الكتابة إلى الذاكرة

```bash
# تعيين قيمة في الذاكرة
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# كتابة سلسلة نصية
(gdb) set {char[10]}0x08048456 = "AAAA"

# استدعاء دالة
(gdb) call system("/bin/sh")
```

<a id="useful-for-exploits"></a>
### إعدادات مفيدة لتطوير الاستغلال

```bash
# متابعة العمليات الابنة
(gdb) set follow-fork-mode child

# تعطيل ASLR داخل GDB
(gdb) set disable-randomization on

# عرض المكتبات المشتركة
(gdb) info sharedlibrary

# تحديد عنوان أساس libc
gef➤ vmmap libc

# العثور على PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| الأمر | الوصف |
|---------|-------------|
| `r` | تشغيل البرنامج |
| `c` | المتابعة |
| `b *addr` | تعيين نقطة توقف |
| `si` | تنفيذ تعليمة مع الدخول في الاستدعاءات |
| `ni` | تنفيذ التعليمة التالية مع تجاوز الاستدعاءات |
| `fin` | إكمال الدالة |
| `x/Nx addr` | فحص الذاكرة |
| `p $reg` | عرض المسجّل |
| `i r` | معلومات المسجّلات |
| `bt` | تتبّع مكدس الاستدعاءات |
| `disas` | تفكيك التعليمات |
| `q` | الخروج |

<a id="memory-examination"></a>
### فحص الذاكرة

| الأمر | الوصف |
|---------|-------------|
| `x/10x $esp` | 10 كلمات بالنظام الست عشري بدءًا من ESP |
| `x/s addr` | السلسلة عند العنوان |
| `x/i $eip` | التعليمة عند EIP |
| `x/20wx addr` | 20 كلمة بالنظام الست عشري |
| `x/10gx $rsp` | 10 كلمات من 8 بايتات بدءًا من RSP |

<a id="quick-debugging"></a>
### تصحيح سريع للأخطاء

```bash
# البدء والتوقف عند main
$ gdb -q ./program
(gdb) break main
(gdb) run

# فحص التعطّل
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

<a id="exploit-dev-workflow"></a>
### سير عمل تطوير الاستغلال

```
1. checksec           → Check protections
2. pattern create     → Create pattern
3. run with pattern   → Crash program
4. pattern search     → Find offset
5. find gadgets       → ROP gadgets
6. build payload      → Create exploit
7. test               → Debug with GDB
```

> يوجز المخطط سير العمل بالترتيب: فحص وسائل الحماية، إنشاء نمط، تشغيل البرنامج بالنمط حتى التعطّل، البحث عن الإزاحة، تحديد مقاطع ROP، بناء حمولة إثبات الاستغلال، ثم اختبارها وتصحيحها باستخدام GDB.

---

<a id="-resources"></a>
## 📚 مصادر

- [دليل GDB الرسمي](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [توثيق GEF](https://hugsy.github.io/gef/)
- [توثيق pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Ghidra](../../Ghidra/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)
- [تصعيد الصلاحيات في Linux](../../Linux-PrivEsc/translations/README.ar.md)

---

<p align="center">
  <b>🐛 إتقان تصحيح الأخطاء في Linux!</b><br>
  <i>GDB - أداة أساسية للهندسة العكسية في Linux</i>
</p>
