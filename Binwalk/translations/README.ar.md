<a id="-binwalk---firmware-analysis-cheatsheet"></a>
# 🔧 Binwalk - ورقة مرجعية لتحليل البرامج الثابتة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Firmware Analysis & Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

يوضح الشعار اسم الأداة ووظيفتها في تحليل البرامج الثابتة واستخراج محتوياتها.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Binwalk](#-what-is-binwalk)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [البحث عن التواقيع](#-signature-scanning)
- [الاستخراج](#-extraction)
- [تحليل الإنتروبيا](#-entropy-analysis)
- [تحليل أنظمة الملفات](#-file-system-analysis)
- [تحديات CTF](#-ctf-challenges)
- [خيارات متقدمة](#-advanced-options)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-binwalk"></a>
## 🎯 ما هو Binwalk

**Binwalk** أداة سريعة وسهلة الاستخدام لتحليل صور البرامج الثابتة (Firmware)، وإجراء الهندسة العكسية عليها، واستخراج البيانات منها. تفيد خصوصا في:

- 🔧 **تحليل البرامج الثابتة** - التعرف على الملفات والشيفرات المضمنة
- 🔓 **اختبار أمن إنترنت الأشياء** - استخراج البرامج الثابتة للموجهات والكاميرات
- 🎯 **تحديات CTF** - العثور على البيانات المخفية
- 🔬 **التحليل الجنائي الرقمي** - تحليل الملفات الثنائية
- 🔍 **الهندسة العكسية** - فهم بنية الملفات

<a id="what-binwalk-can-detect"></a>
### ما الذي يستطيع Binwalk اكتشافه

| الفئة | أمثلة |
| ---------- | ---------- |
| **ملفات مضغوطة** | gzip, bzip2, lzma, xz, zlib |
| **أرشيفات** | tar, zip, rar, 7z, cpio |
| **أنظمة ملفات** | squashfs, cramfs, jffs2, ext, ubifs |
| **ملفات قابلة للتنفيذ** | ELF, PE, ARM, MIPS |
| **صور** | JPEG, PNG, GIF, BMP |
| **بيانات تشفير** | OpenSSL، شهادات |
| **محملات إقلاع** | U-Boot، ترويسات ARM |

---

<a id="-installation"></a>
## 🚀 التثبيت

> **تنبيه توافق:** تحتفظ الأمثلة بخيارات الأصل وطريقة التثبيت القديمة باستخدام Python. أعيدت كتابة Binwalk v3 بلغة Rust؛ لذا قد تختلف إجراءات التثبيت والخيارات بين الإصدارات. راجع [المستودع الرسمي](https://github.com/ReFirmLabs/binwalk) ووثائق الإصدار المستخدم.

<a id="linux-kaliubuntu"></a>
### Linux ‏(Kali/Ubuntu)

```bash
# تثبيت binwalk
sudo apt update
sudo apt install binwalk

# تثبيت جميع أدوات الاستخراج (موصى به)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# أو التثبيت الشامل
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# تثبيت sasquatch لدعم squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

<a id="macos"></a>
### macOS

```bash
brew install binwalk
```

<a id="verify-installation"></a>
### التحقق من التثبيت

```bash
binwalk --help
binwalk -h
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="basic-syntax"></a>
### الصيغة الأساسية

```bash
# فحص الملف (عرض التواقيع)
binwalk firmware.bin

# استخراج الملفات
binwalk -e firmware.bin

# استخراج تكراري
binwalk -eM firmware.bin
```

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الوصف |
| -------- | ------------- |
| `-e, --extract` | استخراج أنواع الملفات المعروفة |
| `-M, --matryoshka` | فحص الملفات المستخرجة بصورة تكرارية |
| `-r, --rm` | حذف الملفات المقتطعة بعد الاستخراج |
| `-q, --quiet` | الوضع الصامت |
| `-v, --verbose` | مخرجات تفصيلية |
| `-d, --depth` | عمق التكرار (الافتراضي: 8) |
| `-C, --directory` | مجلد المخرجات |
| `-f, --log` | تسجيل المخرجات في ملف |

---

<a id="-signature-scanning"></a>
## 🔍 البحث عن التواقيع

<a id="basic-scan"></a>
### الفحص الأساسي

```bash
# البحث عن التواقيع
binwalk firmware.bin

# مثال على المخرجات:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

<a id="specific-signature-types"></a>
### أنواع محددة من التواقيع

> **تصحيح للمثال:** يحتاج `-R` في الإصدار v2 إلى تسلسل بايتات للبحث عنه، ثم الملف الهدف؛ لذلك لا يحدد السطر الأصلي `binwalk -R firmware.bin` نمطا وملفا على نحو صحيح. يوضح المثالان اللاحقان الصيغة المقصودة، وملف التواقيع المخصص يستخدم `-m`. راجع [تعريف الخيارات الرسمي في v2.3.4](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/signature.py).

```bash
# عرض تواقيع محددة فقط
binwalk -A firmware.bin          # شيفرات العمليات/الشيفرة القابلة للتنفيذ
binwalk -R firmware.bin          # بايتات خام (ملف التواقيع)
binwalk -B firmware.bin          # التواقيع القياسية (الافتراضي)

# عرض جميع شيفرات العمليات
binwalk -A firmware.bin

# البحث عن سلسلة خام أو بايتات بالنظام الست عشري
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

<a id="includeexclude-filters"></a>
### مرشحات التضمين والاستبعاد

```bash
# تضمين أنواع محددة فقط
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# استبعاد أنواع
binwalk --exclude="jpeg" firmware.bin

# عرض تواقيع محددة فقط
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

<a id="custom-signatures"></a>
### تواقيع مخصصة

```bash
# استخدام ملف تواقيع مخصص
binwalk -m custom_signatures.txt firmware.bin

# تنسيق ملف التواقيع:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

<a id="-extraction"></a>
## 📦 الاستخراج

<a id="basic-extraction"></a>
### الاستخراج الأساسي

```bash
# استخراج جميع الملفات التي تم التعرف عليها
binwalk -e firmware.bin
# ينشئ: _firmware.bin.extracted/

# الاستخراج إلى مجلد محدد
binwalk -e -C /output/dir firmware.bin

# الاستخراج التكراري (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

<a id="extraction-options"></a>
### خيارات الاستخراج

```bash
# الحذف بعد الاستخراج (تنظيف الملفات)
binwalk -er firmware.bin

# تحديد أقصى عمق للتكرار
binwalk -eM --depth=3 firmware.bin

# استخراج صامت
binwalk -eq firmware.bin

# استخراج بمخرجات تفصيلية
binwalk -ev firmware.bin
```

<a id="manual-extraction-dd"></a>
### الاستخراج اليدوي باستخدام dd

```bash
# عندما يعجز binwalk عن الاستخراج التلقائي
# الحصول على الإزاحة من فحص binwalk
binwalk firmware.bin
# مثال: Squashfs عند الإزاحة 1572892

# الاستخراج باستخدام dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# أو تحديد عدد البايتات باستخدام count إذا كان الحجم معروفا
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

<a id="carving-specific-types"></a>
### اقتطاع أنواع محددة من الملفات

```bash
# استخراج أنواع محددة فقط من الملفات
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# أنواع متعددة
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# التنسيق: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

<a id="-entropy-analysis"></a>
## 📊 تحليل الإنتروبيا

<a id="what-is-entropy"></a>
### ما هي الإنتروبيا؟

تقيس الإنتروبيا (Entropy) مدى العشوائية في البيانات:
- **إنتروبيا مرتفعة (0.9-1.0)** = بيانات مشفرة أو مضغوطة
- **إنتروبيا متوسطة (0.5-0.8)** = شيفرة قابلة للتنفيذ
- **إنتروبيا منخفضة (0.0-0.4)** = نص عادي أو بيانات متكررة

<a id="generate-entropy-graph"></a>
### إنشاء رسم للإنتروبيا

> **تصحيح للتعليق:** في v2.3.4 يعني `-J` حفظ الرسم بصيغة PNG، وليس إظهار مفتاح الرسم؛ لذا يحفظ `-EJ` رسم الإنتروبيا. [تعريف الخيار الرسمي](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/entropy.py).

```bash
# حساب الإنتروبيا
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# حفظ رسم الإنتروبيا
binwalk -E --save firmware.bin
# ينشئ: firmware.bin.png

# مع مفتاح للرسم
binwalk -EJ firmware.bin
```

<a id="entropy-options"></a>
### خيارات الإنتروبيا

```bash
# تحديد حجم الكتلة
binwalk -E --block=1024 firmware.bin

# عرض قيم الإنتروبيا دون رسم
binwalk -E --nplot firmware.bin

# اكتشاف الانتقالات الصاعدة والهابطة
binwalk -E --edge firmware.bin
```

<a id="interpreting-entropy"></a>
### تفسير الإنتروبيا

> **توضيح:** القيم والأنماط التالية مؤشرات تقريبية؛ قد ينتج الضغط والتشفير معا إنتروبيا مرتفعة، ولا يثبت شكل الرسم وحده وجود التشفير أو نوع البيانات. ينطبق ذلك أيضا على أمثلة CTF أدناه. يستخدم الأصل مقياسا مطبّعا من 0 إلى 1، بينما تعرض وثائق v3 مقياسا من 0 إلى 8؛ فلا تنقل حدود الأصل بين الإصدارين مباشرة. راجع [شرح الإنتروبيا الرسمي](https://github.com/ReFirmLabs/binwalk/wiki/Generating-Entropy-Graphs).

```
High flat line (~1.0)  → Encrypted data
Spiky pattern (~0.7)   → Compressed data  
Wavy line (~0.5)       → Code/data
Low flat line (~0.2)   → Plain text
Sudden drop to 0       → Null padding
```

يعرض الرسم الأصلي: خطا مرتفعا مستويا لبيانات قد تكون مشفرة، ونمطا متعرجا للضغط، وخطا متموجا للشيفرة أو البيانات، وخطا منخفضا للنص العادي، وهبوطا إلى الصفر للحشو بالبايتات الصفرية. تظل هذه أوصافا إرشادية وليست اختبارات حاسمة.

---

<a id="-file-system-analysis"></a>
## 💾 تحليل أنظمة الملفات

<a id="common-firmware-file-systems"></a>
### أنظمة الملفات الشائعة في البرامج الثابتة

| نظام الملفات | الوصف |
| ------------- | ------------- |
| **SquashFS** | نظام مضغوط للقراءة فقط (الأكثر شيوعا) |
| **JFFS2** | نظام ملفات لذاكرة الفلاش يستخدم سجل تغييرات |
| **UBIFS** | بديل محسّن عن JFFS2 لذاكرة الفلاش كبيرة السعة |
| **CramFS** | نظام ملفات مضغوط لذاكرة ROM |
| **YAFFS** | نظام ملفات آخر لذاكرة الفلاش (Yet Another Flash File System) |
| **ext2/3/4** | نظام ملفات قياسي في Linux |

<a id="extract-squashfs"></a>
### استخراج SquashFS

```bash
# الاستخراج باستخدام Binwalk
binwalk -e firmware.bin

# الاستخراج اليدوي باستخدام unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# تركيب نظام الملفات إذا كان مدعوما
sudo mount -t squashfs squashfs.bin /mnt
```

<a id="extract-jffs2"></a>
### استخراج JFFS2

```bash
# باستخدام jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# أو باستخدام binwalk
binwalk -e firmware.bin
```

<a id="extract-ubifs"></a>
### استخراج UBIFS

```bash
# باستخدام ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

<a id="analyze-extracted-files"></a>
### تحليل الملفات المستخرجة

```bash
# استكشاف الملفات بعد الاستخراج
cd _firmware.bin.extracted/

# العثور على ملفات جديرة بالفحص
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# البحث عن كلمات المرور والأسرار
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# العثور على الملفات القابلة للتنفيذ
find . -type f -executable
file */bin/*
```

---

<a id="-ctf-challenges"></a>
## 🎯 تحديات CTF

<a id="common-ctf-scenarios"></a>
### سيناريوهات CTF الشائعة

<a id="hidden-file-in-image"></a>
#### ملف مخفي داخل صورة

```bash
# فحص الصورة بحثا عن بيانات مخفية
binwalk image.png
binwalk image.jpg

# استخراج الملفات المخفية
binwalk -e image.png

# فحص السلاسل النصية أيضا
strings image.png | head -50
```

<a id="stacked-archives"></a>
#### أرشيفات متداخلة

```bash
# استخراج تكراري (matryoshka)
binwalk -eM challenge.bin

# طبقات متعددة من الضغط
binwalk -eM --depth=10 nested.bin
```

<a id="find-hidden-text"></a>
#### العثور على نص مخفي

```bash
# الجمع مع أداة strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

<a id="encrypted-sections"></a>
#### أجزاء مشفرة

```bash
# تحليل الإنتروبيا يكشف التشفير
binwalk -E challenge.bin

# إذا كانت الإنتروبيا مرتفعة فقد تكون البيانات مشفرة
# تجربة كلمات المرور الشائعة أو البحث عن المفتاح في مكان آخر
```

<a id="ctf-workflow"></a>
### خطوات العمل في CTF

```bash
# 1. الفحص الأساسي
binwalk challenge.bin

# 2. فحص الإنتروبيا
binwalk -E challenge.bin

# 3. استخراج كل المحتويات
binwalk -eM challenge.bin

# 4. البحث في الملفات المستخرجة
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. البحث عن بيانات مخفية
binwalk -A challenge.bin    # شيفرات العمليات
xxd challenge.bin | head    # عرض بالنظام الست عشري
```

<a id="steganography-detection"></a>
### اكتشاف إخفاء البيانات

```bash
# البحث عن بيانات مخفية في الصور
binwalk -B suspicious.jpg

# البحث عن بيانات ملحقة بنهاية الملف
binwalk suspicious.png

# الاستخراج إذا عثر على بيانات
binwalk -e suspicious.jpg

# مقارنة حجم الملف بالحجم المتوقع
ls -la suspicious.jpg
```

---

<a id="-advanced-options"></a>
## ⚙️ خيارات متقدمة

<a id="hexdump-output"></a>
### عرض البيانات بالنظام الست عشري

```bash
# عرض البيانات بالنظام الست عشري مع الإزاحات
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# عرض البيانات عند إزاحة محددة بالنظام الست عشري
binwalk -o 0x1000 -l 512 -W firmware.bin
```

<a id="offset-options"></a>
### خيارات الإزاحة

```bash
# البدء من إزاحة محددة
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# فحص طول محدد فقط
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

<a id="performance-options"></a>
### خيارات الأداء

```bash
# استخدام عمليات متعددة
binwalk --threads=4 firmware.bin

# تعطيل إضافات محددة
binwalk --disable-plugin=lzma firmware.bin
```

<a id="output-formats"></a>
### تنسيقات المخرجات

```bash
# مخرجات CSV
binwalk --csv firmware.bin > results.csv

# مخرجات JSON  
binwalk --json firmware.bin > results.json

# تسجيل المخرجات في ملف
binwalk -f scan.log firmware.bin
```

<a id="diff-two-files"></a>
### مقارنة ملفين

```bash
# مقارنة إصدارين من البرامج الثابتة
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# يعرض الاختلافات بالنظام الست عشري
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="most-used-commands"></a>
### الأوامر الأكثر استخداما

| الغرض | الأمر |
| --------- | --------- |
| فحص ملف | `binwalk firmware.bin` |
| استخراج الكل | `binwalk -e firmware.bin` |
| استخراج تكراري | `binwalk -eM firmware.bin` |
| تحليل الإنتروبيا | `binwalk -E firmware.bin` |
| البحث عن شيفرات العمليات | `binwalk -A firmware.bin` |
| البحث عن سلسلة نصية | `binwalk -R "pattern" file` |
| الاستخراج إلى مجلد | `binwalk -e -C outdir/ file` |
| الاستخراج والحذف | `binwalk -er firmware.bin` |

<a id="common-file-signatures"></a>
### تواقيع الملفات الشائعة

| التوقيع | الوصف |
| ----------- | ------------- |
| `\x1f\x8b\x08` | بيانات مضغوطة باستخدام gzip |
| `BZh` | بيانات مضغوطة باستخدام bzip2 |
| `\xfd7zXZ` | بيانات مضغوطة باستخدام xz |
| `hsqs` | SquashFS (ترتيب البايتات من الأقل أهمية: little endian) |
| `sqsh` | SquashFS (ترتيب البايتات من الأعلى أهمية: big endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | صورة PNG |
| `\xff\xd8\xff` | صورة JPEG |
| `PK\x03\x04` | أرشيف ZIP |

> **تصحيح للتوقيع:** يشير `UBI#` تحديدا إلى ترويسة عداد المسح في طبقة UBI، وليس وحده إلى توقيع نظام UBIFS. أبقيت تسمية الجدول الأصلية للمقارنة؛ راجع [تعريف الترويسة في نواة Linux](https://github.com/torvalds/linux/blob/master/drivers/mtd/ubi/ubi-media.h).

<a id="extraction-workflow"></a>
### خطوات الاستخراج

```
1. binwalk firmware.bin       → التعرف على المحتويات
2. binwalk -E firmware.bin    → البحث عن مؤشرات التشفير
3. binwalk -e firmware.bin    → الاستخراج
4. cd _firmware.bin.extracted → الاستكشاف
5. find . -name "*" -type f   → عرض جميع الملفات
6. grep -r "password" .       → العثور على الأسرار
```

<a id="manual-extraction"></a>
### الاستخراج اليدوي

```bash
# الحصول على الإزاحة من binwalk
binwalk firmware.bin
# Squashfs عند 0x180000 ‏(1572864 بالنظام العشري)

# الاستخراج باستخدام dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# استخراج squashfs
unsquashfs fs.squashfs
```

<a id="useful-file-locations-firmware"></a>
### مسارات ملفات مفيدة في البرامج الثابتة

| المسار | المحتويات |
| ------ | ---------- |
| `/etc/passwd` | حسابات المستخدمين |
| `/etc/shadow` | تجزئات كلمات المرور |
| `/etc/config/` | إعدادات الجهاز |
| `/etc/init.d/` | سكربتات بدء التشغيل |
| `/usr/bin/` | الملفات التنفيذية للمستخدم |
| `/www/` | واجهة الويب |
| `/etc/ssl/` | شهادات SSL |

---

<a id="-resources"></a>
## 📚 المصادر

- [مستودع Binwalk على GitHub](https://github.com/ReFirmLabs/binwalk)
- [ويكي Binwalk](https://github.com/ReFirmLabs/binwalk/wiki)
- [مقدمة في تحليل البرامج الثابتة](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [تحليل البرامج الثابتة لإنترنت الأشياء من OWASP](https://owasp.org/www-project-iot-security/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [ExifTool](../../ExifTool/translations/README.ar.md)
- [Volatility](../../Volatility/translations/README.ar.md)
- [Autopsy](../../Autopsy/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)

---

<p align="center">
  <b>🔧 أتقن تحليل البرامج الثابتة!</b><br>
  <i>Binwalk - استكشف أسرار البرامج الثابتة باستخراج محتوياتها</i>
</p>
