<a id="-reverse-engineering-ctf-cheatsheet"></a>
# 🔄 مرجع تحديات الهندسة العكسية في CTF

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

<a id="-initial-analysis"></a>
## 🔍 التحليل الأولي

```bash
# نوع الملف
file binary

# السلاسل النصية
strings binary | grep -i flag
strings -n 8 binary

# ELF info
readelf -h binary

# فحص وسائل الحماية
checksec binary
```

---

<a id="-disassemblers"></a>
## 🛠️ أدوات تفكيك الشيفرة

<a id="ghidra-free"></a>
### Ghidra (مجاني)
```
1. File → Import File (لاستيراد الملف)
2. Analyze → Auto Analyze (للتحليل التلقائي)
3. البحث عن الدالة main()
4. البحث عن strcmp/memcmp التي قد تُستخدم لفحص كلمة المرور
5. استخدام F5 لفك الترجمة وفق المثال الأصلي
```

<a id="ida-free"></a>
### IDA Free
```
1. فتح الملف التنفيذي
2. استخدام F5 لعرض الشيفرة الشبيهة بالمصدر (Hex-Rays)
3. استخدام X للعثور على المراجع المتقاطعة
4. استخدام N لإعادة تسمية المتغيرات
```

<a id="radare2"></a>
### Radare2
```bash
r2 binary
aaa          # تحليل
afl          # سرد الدوال
pdf @main    # تفكيك الدالة main
VV           # الوضع المرئي
```

---

<a id="-gdb-debugging"></a>
## 🐛 تنقيح البرامج باستخدام GDB

```bash
# بدء التشغيل
gdb ./binary

# نقاط التوقف
b main
b *0x401234

# تشغيل
r
r arg1 arg2

# التقدم خطوة
n           # السطر التالي
s           # الدخول في الاستدعاء
c           # متابعة التنفيذ

# الفحص
x/s $rdi    # سلسلة نصية
x/10x $rsp  # عرض 10 قيم ست عشرية من المكدس
info reg    # السجلات

# البحث عن العلم
find 0x0, 0xffffffff, "flag"
```

<a id="gefpeda"></a>
### GEF/PEDA
```bash
# تثبيت GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Commands
vmmap
heap
telescope $rsp
```

---

<a id="-python-patching"></a>
## 🐍 تعديل بايتات البرامج باستخدام Python

```python
# قراءة الملف التنفيذي
with open("binary", "rb") as f:
    data = bytearray(f.read())

# تعديل البايتات
data[0x1234] = 0x90  # NOP

# كتابة
with open("patched", "wb") as f:
    f.write(data)
```

---

<a id="-common-patterns"></a>
## 📊 أنماط شائعة

| الفحص | التجاوز |
|-------|--------|
| `strcmp(input, "password")` | استخراج "password" |
| `if (x == 0x1337)` | العثور على القيمة 0x1337 |
| مقاومة التنقيح | تعديل الشيفرة المرتبطة بـ ptrace |
| سلاسل نصية مموّهة | تحليل ديناميكي |

---

<a id="-re-checklist"></a>
## 📋 قائمة الهندسة العكسية

```markdown
□ تشغيل الأمرين file وstrings
□ فحص رموز التنقيح
□ فتح الملف في Ghidra أو IDA
□ العثور على الدالة main
□ البحث عن مقارنات السلاسل النصية
□ تتبّع مدخلات المستخدم
□ تعديل آليات مقاومة التنقيح عند الحاجة
□ تحليل ديناميكي باستخدام GDB
```

---

**العودة إلى CTF:** [🏁 نظرة عامة على CTF](README.ar.md)

> **تصحيح لواجهة Ghidra:** يشير الأصل إلى F5 لفك الترجمة. يوضح دليل Ghidra فتح نافذة Decompiler عبر `Window → Decompiler` أو رمزها في CodeBrowser؛ احتُفظ باختصار الأصل للمقارنة، ولا يُفترض أنه الاختصار الافتراضي في كل أداة. [دليل Ghidra الرسمي](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html). تفكيك الشيفرة (Disassembly) يعرض تعليمات الآلة بصيغة Assembly، أما فك الترجمة (Decompilation) فينتج شيفرة تقريبية أعلى مستوى.
