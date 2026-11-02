<a id="-ghidra---reverse-engineering-cheatsheet"></a>
# 🔍 ورقة مرجعية للهندسة العكسية باستخدام Ghidra

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              NSA Reverse Engineering Suite
```

يشير الرسم أعلاه إلى حزمة Ghidra للهندسة العكسية، وقد حُفظ بتنسيقه الأصلي. تساعد الهندسة العكسية على فهم سلوك البرنامج من ملفه الثنائي؛ وAssembly تمثيل لتعليمات المعالج، أما الشيفرة الشبهية فهي تقريب يسهل قراءته وليست المصدر الأصلي بالضرورة.

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Ghidra؟](#-what-is-ghidra)
- [التثبيت](#-installation)
- [نظرة على الواجهة](#-interface-overview)
- [إنشاء مشروع](#-creating-a-project)
- [أساسيات التحليل](#-analysis-basics)
- [التنقّل](#-navigation)
- [محوّل الشيفرة إلى صيغة عالية المستوى (Decompiler)](#-decompiler)
- [أنواع البيانات والبُنى](#-data-types--structures)
- [الترقيع والتعديل](#-patching--modifying)
- [كتابة السكربتات](#-scripting)
- [اختصارات لوحة المفاتيح](#-keyboard-shortcuts)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-ghidra"></a>
## 🎯 ما هو Ghidra؟

**Ghidra** حزمة مجانية ومفتوحة المصدر للهندسة العكسية للبرمجيات (SRE)، طوّرتها وكالة NSA. وهي توفر:

- 🔍 **محوّل الشيفرة إلى Assembly ‏(Disassembler)** - تحويل الملف الثنائي إلى تعليمات Assembly
- 🔄 **محوّل الشيفرة إلى صيغة عالية المستوى (Decompiler)** - توليد شيفرة شبهية شبيهة بلغة C
- 📊 **التحليل** - تحليلًا مؤتمتًا للشيفرة
- 🔧 **الترقيع (Patching)** - تعديل الملفات الثنائية
- 🐍 **كتابة السكربتات** - الأتمتة باستخدام Python/Java
- 👥 **التعاون** - دعم عدة مستخدمين

<a id="supported-architectures"></a>
### المعماريات المدعومة

| المعمارية | الأنواع |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

<a id="supported-formats"></a>
### الصيغ المدعومة

| الصيغة | الوصف |
|--------|-------------|
| ELF | ملفات Linux التنفيذية |
| PE/COFF | ملفات Windows التنفيذية |
| Mach-O | ملفات macOS التنفيذية |
| ملف ثنائي خام (Raw Binary) | برمجيات ثابتة وROM |
| APK/DEX | تطبيقات Android |
| وغيرها من الصيغ... | — |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="download"></a>
### التنزيل

```bash
# نزّل من الموقع الرسمي
https://ghidra-sre.org/

# أو من إصدارات GitHub
https://github.com/NationalSecurityAgency/ghidra/releases
```

<a id="requirements"></a>
### المتطلبات

| المتطلب | الإصدار |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | 4GB حد أدنى، ويُوصى بـ 8GB أو أكثر |
| **مساحة القرص** | 1GB أو أكثر للتثبيت |

<a id="linuxmacos"></a>
### Linux/macOS

```bash
# ثبّت JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# فك ضغط Ghidra
unzip ghidra_*.zip
cd ghidra_*

# التشغيل
./ghidraRun
```

<a id="windows"></a>
### Windows

```batch
:: ثبّت JDK 17+ من Oracle أو Adoptium
:: فك ضغط ghidra_*.zip
:: شغّل ghidraRun.bat
```

---

<a id="-interface-overview"></a>
## 🖥️ نظرة على الواجهة

<a id="main-windows"></a>
### النوافذ الرئيسية

| النافذة | الوصف |
|--------|-------------|
| **Program Trees** | بنية ملفات المشروع |
| **Symbol Tree** | الدوال والتسميات والفئات |
| **Data Type Manager** | أنواع البيانات المعرّفة |
| **Listing** | عرض تعليمات Assembly |
| **Decompiler** | شيفرة شبهية بلغة C |
| **Console** | الإخراج وكتابة السكربتات |
| **Defined Strings** | السلاسل النصية المكتشفة |
| **Bookmarks** | مواقع محفوظة |

<a id="listing-window-disassembly"></a>
### نافذة Listing (تفكيك الشيفرة)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Function start
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Stack frame
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

يعرض الجدول أعلاه العنوان Address، والبايتات Bytes، وتعليمات Disassembly، ثم التعليقات Comments. يشير Function start إلى بداية الدالة، وStack frame إلى إطار المكدس. حُفظت العناوين والتعليمات والتنسيق الأصلي.

---

<a id="-creating-a-project"></a>
## 📁 إنشاء مشروع

<a id="new-project"></a>
### مشروع جديد (New Project)

```
1. File → New Project
2. اختر:
   - Non-Shared Project (محلي)
   - Shared Project (للتعاون)
3. حدّد المجلد
4. أدخل اسم المشروع
5. انقر على Finish
```

<a id="import-binary"></a>
### استيراد ملف ثنائي

```
1. File → Import File (I)
2. حدّد الملف الثنائي أو الملف
3. اختر الصيغة (تُكتشف تلقائيًا عادةً)
4. اضبط الخيارات:
   - Language/Processor
   - Compiler spec
5. انقر على OK
6. Analyze? → Yes (موصى به)
```

<a id="analysis-options"></a>
### خيارات التحليل

| الخيار | الوصف |
|--------|-------------|
| ASCII Strings | العثور على السلاسل النصية |
| Decompiler | تفعيل التحويل إلى شيفرة عالية المستوى |
| Stack Analysis | تحليل إطار المكدس |
| Reference Analysis | العثور على الإحالات المرجعية المتقاطعة |
| Function ID | مطابقة دوال المكتبات |
| Call Convention | تحديد اصطلاحات استدعاء الدوال |

---

<a id="-analysis-basics"></a>
## 🔬 أساسيات التحليل

<a id="auto-analysis"></a>
### التحليل التلقائي

```
Analysis → Auto Analyze (A)
- يشغّل جميع المحلّلات المحدّدة
- قد يستغرق وقتًا مع الملفات الثنائية الكبيرة
- يظهر التقدّم في مراقب المهام
```

<a id="one-shot-analyzers"></a>
### محلّلات تُشغَّل لمرة واحدة (One-Shot)

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

<a id="function-identification"></a>
### تحديد الدوال

```
# اعثر على جميع الدوال
Analysis → Auto Analyze → فعّل "Non-returning Functions"

# إنشاء دالة يدويًا
انقر بزر الفأرة الأيمن على العنوان → Create Function (F)

# شيفرة غير معرّفة
انقر بزر الفأرة الأيمن → Disassemble (D)
```

---

<a id="-navigation"></a>
## 🧭 التنقّل

<a id="go-to-address"></a>
### الانتقال إلى عنوان

```
# اختصار لوحة المفاتيح
G → أدخل العنوان → OK

# أمثلة:
0x00401000      # عنوان بالنظام الست عشري
main            # اسم دالة
entry           # نقطة الدخول
LAB_00401234    # تسمية
```

<a id="navigation-shortcuts"></a>
### اختصارات التنقّل

| الاختصار | الإجراء |
|----------|--------|
| `G` | الانتقال إلى عنوان |
| `Ctrl+G` | الانتقال إلى عنوان |
| `Alt+←` | الرجوع إلى الموقع السابق |
| `Alt+→` | الانتقال إلى الموقع التالي |
| `Ctrl+Shift+E` | الانتقال إلى نقطة الدخول |

<a id="cross-references-xrefs"></a>
### الإحالات المرجعية المتقاطعة (XREFs)

```
# اعرض الإحالات المرجعية إلى الموضع الحالي
انقر بزر الفأرة الأيمن → References → Show References To

# اعثر على مواضع استدعاء الدالة
حدّد الدالة → انقر بزر الفأرة الأيمن → References → Show References To

# لوحة المفاتيح
Ctrl+Shift+F → اعثر على الإحالات المرجعية
```

<a id="search-functions"></a>
### البحث عن الدوال

```
# البحث
Search → Memory (S)           # ابحث في الذاكرة
Search → For Strings          # اعثر على السلاسل النصية
Search → For Direct References # اعثر على الإحالات المرجعية إلى العنوان
Search → Program Text         # ابحث في تعليمات Assembly

# البحث عن الدوال
Symbol Tree → Filter → اكتب اسم الدالة
```

---

<a id="-decompiler"></a>
## 💻 محوّل الشيفرة إلى صيغة عالية المستوى (Decompiler)

<a id="open-decompiler"></a>
### فتح نافذة Decompiler

```
Window → Decompiler
# أو انقر على تبويب Decompiler
# يسترجع تلقائيًا الشيفرة عالية المستوى للدالة المحدّدة
```

<a id="reading-decompiled-code"></a>
### قراءة الشيفرة المسترجعة

```c
// مثال على الشيفرة المسترجعة
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

<a id="improve-decompilation"></a>
### تحسين الشيفرة المسترجعة

```
# أعد تسمية المتغيرات
انقر بزر الفأرة الأيمن على المتغير → Rename Variable (L)

# غيّر نوع البيانات
انقر بزر الفأرة الأيمن على المتغير → Retype Variable (Ctrl+L)

# أضف تعليقات
; → Set EOL comment
Ctrl+; → Set plate comment

# اضبط توقيع الدالة
انقر بزر الفأرة الأيمن على الدالة → Edit Function Signature
```

<a id="decompiler-tricks"></a>
### أساليب مفيدة في Decompiler

| الإجراء | الطريقة |
|--------|-----|
| إعادة تسمية متغير | `L` أو النقر بزر الفأرة الأيمن |
| تغيير النوع | `Ctrl+L` |
| إنشاء بنية | انقر بزر الفأرة الأيمن → Auto Create Structure |
| تقسيم المتغيرات أو دمجها | انقر بزر الفأرة الأيمن → Split/Merge |
| تثبيت توقيع الدالة | `P` |

---

<a id="-data-types--structures"></a>
## 📊 أنواع البيانات والبُنى

<a id="create-structure"></a>
### إنشاء بنية

```
# الطريقة 1: من Data Type Manager
Data Type Manager → انقر بزر الفأرة الأيمن → New → Structure

# الطريقة 2: الإنشاء التلقائي من Decompiler
Decompiler → انقر بزر الفأرة الأيمن على المتغير → Auto Create Structure
```

<a id="define-structure"></a>
### تعريف بنية

```c
// مثال على بنية
struct Player {
    char name[32];      // الإزاحة 0x00
    int health;         // الإزاحة 0x20
    int score;          // الإزاحة 0x24
    float position[3];  // الإزاحة 0x28
};
```

<a id="apply-structure"></a>
### تطبيق بنية

```
# في عرض Listing
حدّد العنوان → انقر بزر الفأرة الأيمن → Data → Choose Data Type

# أو
اضغط T → اختر البنية

# في Decompiler
انقر بزر الفأرة الأيمن على المتغير → Retype Variable → اختر البنية
```

<a id="arrays"></a>
### المصفوفات

```
# أنشئ مصفوفة
حدّد عنوان البداية → انقر بزر الفأرة الأيمن → Data → Create Array
أدخل عدد العناصر
```

<a id="enums"></a>
### التعدادات (Enums)

```
# أنشئ تعدادًا
Data Type Manager → انقر بزر الفأرة الأيمن → New → Enum

# عرّف القيم
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

<a id="-patching--modifying"></a>
## 🔧 الترقيع والتعديل

<a id="patch-instruction"></a>
### ترقيع تعليمة

```
# الطريقة 1: لوحة المفاتيح
حدّد التعليمة → Ctrl+Shift+G
أدخل التعليمة الجديدة → اضغط Enter

# الطريقة 2: القائمة
انقر بزر الفأرة الأيمن → Patch Instruction
```

<a id="patch-data"></a>
### ترقيع البيانات

```
# غيّر البايتات
انقر بزر الفأرة الأيمن → Patch Data
أدخل البايتات بالنظام الست عشري

# أو
حدّد البايتات → Ctrl+Shift+G
```

<a id="common-patches"></a>
### ترقيعات شائعة

| الأصل | بعد الترقيع | الغرض |
|----------|---------|---------|
| `JE` | `JMP` | القفز دائمًا |
| `JNE` | `JMP` | القفز دائمًا |
| `JE` | `NOP` | عدم القفز مطلقًا |
| `CALL` | `NOP NOP NOP NOP NOP` | تجاوز الاستدعاء |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

<a id="export-patched-binary"></a>
### تصدير الملف الثنائي المعدّل

```
File → Export Program
Format: Binary
حدّد الوجهة
```

<a id="nop-sled"></a>
### سلسلة تعليمات NOP

```
# املأ بتعليمات NOP
حدّد النطاق → انقر بزر الفأرة الأيمن → Clear Code Block
ثم: Select → انقر بزر الفأرة الأيمن → Patch → Fill with NOPs
```

---

<a id="-scripting"></a>
## 🐍 كتابة السكربتات

<a id="script-manager"></a>
### مدير السكربتات (Script Manager)

```
Window → Script Manager
# أو: Tools → Script Manager
```

<a id="run-script"></a>
### تشغيل سكربت

```
Script Manager → حدّد السكربت → Run (زر التشغيل الأخضر)
# أو انقر مرتين على السكربت
```

<a id="python-scripting-ghidra-python"></a>
### سكربتات Python في Ghidra

```python
# مثال: عرض جميع الدوال
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = التقدّم إلى الأمام

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

<a id="common-script-operations"></a>
### عمليات شائعة في السكربتات

```python
# احصل على العنوان الحالي
currentAddress

# احصل على البرنامج الحالي
currentProgram

# احصل على مدير الدوال
fm = currentProgram.getFunctionManager()

# احصل على عرض Listing (تعليمات Assembly)
listing = currentProgram.getListing()

# احصل على التعليمة عند العنوان
instr = listing.getInstructionAt(addr)

# احصل على البايتات عند العنوان
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# أنشئ تسمية
createLabel(addr, "my_label", True)

# أضف تعليقًا
setEOLComment(addr, "My comment")

# احصل على الإحالات المرجعية
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

<a id="useful-built-in-scripts"></a>
### سكربتات مدمجة مفيدة

| السكربت | الغرض |
|--------|---------|
| FindCrypt | العثور على ثوابت خوارزميات التشفير |
| FunctionID | مطابقة دوال المكتبات |
| ExportFunctionInfoScript | تصدير قائمة الدوال |
| FindStrings | العثور على إحالات إلى سلاسل نصية |
| SearchMemory | البحث عن أنماط |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ اختصارات لوحة المفاتيح

<a id="navigation"></a>
### التنقّل

| الاختصار | الإجراء |
|----------|--------|
| `G` | الانتقال إلى عنوان |
| `Alt+←` | رجوع |
| `Alt+→` | تقدّم |
| `Ctrl+Shift+E` | نقطة الدخول |
| `Ctrl+E` | تصدير الجدول |
| `N` | الانتقال إلى الموضع التالي غير المعرّف |

<a id="analysis"></a>
### التحليل

| الاختصار | الإجراء |
|----------|--------|
| `F` | إنشاء دالة |
| `D` | تفكيك الشيفرة |
| `C` | إزالة التعريف |
| `P` | إنشاء مؤشر |
| `T` | تطبيق نوع بيانات |
| `A` | التحليل التلقائي |

<a id="editing"></a>
### التحرير

| الاختصار | الإجراء |
|----------|--------|
| `L` | إعادة تسمية تسمية أو متغير |
| `;` | إضافة تعليق نهاية سطر (EOL) |
| `Ctrl+;` | إضافة تعليق Plate |
| `Ctrl+L` | تغيير نوع المتغير |
| `Ctrl+Shift+G` | ترقيع تعليمة |

<a id="search"></a>
### البحث

| الاختصار | الإجراء |
|----------|--------|
| `Ctrl+Shift+F` | العثور على الإحالات المرجعية |
| `Ctrl+F` | البحث في النص |
| `S` | البحث في الذاكرة |
| `Ctrl+B` | البحث عن بايتات |

<a id="windows-1"></a>
### نوافذ الواجهة

| الاختصار | الإجراء |
|----------|--------|
| `Ctrl+D` | إظهار نافذة Decompiler أو إخفاؤها |
| `Ctrl+T` | إظهار مدير أنواع البيانات أو إخفاؤه |
| `Ctrl+S` | شجرة الرموز |

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="analysis-workflow"></a>
### سير عمل التحليل

```
1. Import binary      → File → Import
2. Auto-analyze       → Analysis → Auto Analyze (accept defaults)
3. Find entry/main    → G → main or entry
4. Review functions   → Symbol Tree → Functions
5. Examine strings    → Search → For Strings
6. Trace execution    → Follow calls/jumps
7. Rename symbols     → L on functions/variables
8. Document           → Add comments (;)
```

مراحل المخطط أعلاه: استيراد الملف الثنائي، ثم التحليل التلقائي، والعثور على نقطة الدخول أو main، ومراجعة الدوال والسلاسل النصية، وتتبع الاستدعاءات والقفزات، وإعادة تسمية الرموز، وتوثيق الاستنتاجات بالتعليقات. حُفظ المخطط الأصلي بتنسيقه.

<a id="common-tasks"></a>
### مهام شائعة

| المهمة | الطريقة |
|------|-----|
| العثور على main() | `G` → `main` أو البحث في Symbol Tree |
| عرض السلاسل النصية | Search → For Strings |
| العثور على شيفرة التشفير | تشغيل سكربت FindCrypt |
| فحص الاستيرادات | Symbol Tree → Imports |
| العثور على XREFs | Select → Ctrl+Shift+F |
| إنشاء دالة | حدّد العنوان → `F` |
| تحويل الشيفرة إلى صيغة عالية المستوى | Window → Decompiler |

<a id="reversing-checklist"></a>
### قائمة التحقق للهندسة العكسية

```
☐ حدّد نوع الملف (PE وELF وغيرهما)
☐ شغّل التحليل التلقائي
☐ اعثر على نقطة الدخول / main
☐ افحص السلاسل النصية بحثًا عن تلميحات
☐ حدّد الدوال الجديرة بالمراجعة
☐ ارسم تدفّق البرنامج
☐ حلّل الدوال المشبوهة
☐ وثّق النتائج بالتعليقات
☐ أعد تسمية الدوال والمتغيرات
☐ أنشئ البُنى عند الحاجة
```

<a id="function-calling-conventions"></a>
### اصطلاحات استدعاء الدوال

| الاصطلاح | قيمة الإرجاع | المعاملات | تنظيف المكدس |
|------------|--------|------|----------------|
| **cdecl** | EAX | المكدس (R→L) | الدالة المستدعية |
| **stdcall** | EAX | المكدس (R→L) | الدالة المستدعاة |
| **fastcall** | EAX | ECX, EDX, Stack | الدالة المستدعاة |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | الدالة المستدعية |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | الدالة المستدعية |

---

<a id="-resources"></a>
## 📚 المراجع

- [الموقع الرسمي لـ Ghidra](https://ghidra-sre.org/)
- [مستودع Ghidra على GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [توثيق Ghidra](https://ghidra.re/ghidra_docs/api/)
- [قائمة Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Binwalk](../../Binwalk/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)
- [Volatility](../../Volatility/translations/README.ar.md)

---



> **ملاحظات الإصدارات والسكربتات:** حُفظت متطلبات `17+` وأوامر JDK 17 كما في المصدر، لكنها لا تصلح لكل إصدار؛ تعرض تعليمات المشروع الحالية JDK 25 ‏64-bit، ويجب الرجوع إلى دليل الإصدار المستخدم. مثال Python ذي السلسلة f-string يحتاج إلى بيئة Python 3 مناسبة؛ يوفّر PyGhidra تشغيل CPython 3، ولا ينبغي افتراض توافق المثال مع مفسّر Jython القديم. راجع [تعليمات المشروع](https://github.com/NationalSecurityAgency/ghidra/blob/master/README.md) و[PyGhidra](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/README.md).
>
> أسماء الإجراءات والسكربتات والاختصارات تعتمد على الإصدار والأداة والملحقات وإعدادات المستخدم. تختلف بعض الإجراءات بين Listing وDecompiler؛ يشرح [دليل Ghidra الرسمي](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html) قوائم السياق وتخصيص الاختصارات باستخدام F4. كما أن خيار Non-returning Functions لا يعني العثور على جميع الدوال؛ التحليل التلقائي مجموعة محلّلات وقد يحتاج إلى تصحيح يدوي. حُفظت أسماء المصدر لتسهيل المطابقة مع الواجهة.
>
> تصدير Binary ينتج بايتات كتل الذاكرة، ولا يضمن إعادة بناء ملف PE/ELF قابل للتشغيل مع ترويساته. يختلف عن تصدير الملف الأصلي وفق الصيغة والخيارات المتاحة. راجع [BinaryExporter](https://ghidra.re/ghidra_docs/api/ghidra/app/util/exporter/BinaryExporter.html) و[OriginalFileExporter](https://ghidra.re/ghidra_docs/api/ghidra/app/util/exporter/OriginalFileExporter.html). كذلك فجدول الترقيعات أمثلة تعليمية: تختلف أطوال تعليمات القفز والاستدعاء وإزاحاتها؛ لا يضمن استبدال اختصار Assembly وحده بقاء حدود التعليمات صحيحة.
>
> جدول اصطلاحات الاستدعاء مختصر ويركّز على حالات شائعة للمعاملات الصحيحة والمؤشرات؛ تختلف القيم العائمة والبُنى وإرجاع القيم بحسب ABI والمترجم. في Windows x64 مثلًا تُستخدم سجلات XMM للمعاملات العائمة وتُحجز مساحة Shadow Space؛ راجع [اصطلاح Windows x64](https://learn.microsoft.com/en-us/cpp/build/x64-calling-convention). لم تُعدَّل السجلات أو القيم الواردة في أمثلة المصدر.

<p align="center">
  <b>🔍 أتقن الهندسة العكسية!</b><br>
  <i>Ghidra — هدية NSA إلى مجتمع الأمن</i>
</p>
