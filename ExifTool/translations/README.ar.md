<a id="-exiftool---metadata-extraction-cheatsheet"></a>
# 📷 ExifTool - مرجع سريع لاستخراج البيانات الوصفية

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Metadata Extraction Tool
```

> يكتب الرسم النصي اسم الأداة بالإنجليزية؛ تستخرج ExifTool البيانات الوصفية، أي معلومات تصف الملف أو جهاز إنشائه أو توقيته وموقعه.

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو ExifTool](#-what-is-exiftool)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [قراءة البيانات الوصفية](#-reading-metadata)
- [كتابة البيانات الوصفية](#-writing-metadata)
- [حذف البيانات الوصفية](#-removing-metadata)
- [أنواع الملفات](#-file-types)
- [استخبارات المصادر المفتوحة والتحليل الجنائي الرقمي](#-osint--forensics)
- [معالجة الملفات على دفعات](#-batch-processing)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-exiftool"></a>
## 🎯 ما هو ExifTool

**ExifTool** أداة سطر أوامر لقراءة البيانات الوصفية (Metadata) وكتابتها وتعديلها في الملفات. تُستخدم في المجالات التالية:

- 🔍 **OSINT** - استخراج الموقع ومعلومات الجهاز من الصور
- 🔬 **التحليل الجنائي الرقمي** - تحليل البيانات الوصفية للمستندات
- 🔐 **الخصوصية** - حذف البيانات الوصفية الحساسة
- 📸 **التصوير** - إدارة البيانات الوصفية للصور
- 📄 **المستندات** - تحليل البيانات الوصفية لملفات PDF وOffice

<a id="why-metadata-matters"></a>
### لماذا تهم البيانات الوصفية

| حالة الاستخدام | مثال |
|----------|---------|
| **تحديد الموقع الجغرافي** | إحداثيات GPS داخل الصور |
| **معلومات الجهاز** | طراز الكاميرا ورقمها التسلسلي |
| **الطوابع الزمنية** | وقت إنشاء الملف أو تعديله |
| **المؤلف (Author)** | مَن أنشأ المستند |
| **البرنامج (Software)** | التطبيق الذي أنشأ الملف |
| **سجل التعديلات** | الطوابع الزمنية للتعديلات |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# أو
sudo apt install libimage-exiftool-perl
```

<a id="macos"></a>
### macOS

```bash
# Homebrew
brew install exiftool
```

<a id="windows"></a>
### Windows

```bash
# التنزيل من الموقع الرسمي
https://exiftool.org/

# استخراج exiftool(-k).exe
# إعادة التسمية إلى exiftool.exe
# الإضافة إلى PATH أو الاستخدام مباشرةً
```

<a id="verify-installation"></a>
### التحقق من التثبيت

```bash
exiftool -ver
# Output: 12.xx
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="basic-syntax"></a>
### الصيغة الأساسية

```bash
# قراءة البيانات الوصفية
exiftool [OPTIONS] FILE

# كتابة البيانات الوصفية
exiftool -TAG=VALUE FILE

# عدة ملفات
exiftool *.jpg
exiftool -r directory/
```

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الوصف |
|--------|-------------|
| `-r` | معالجة تكرارية تشمل المجلدات الفرعية |
| `-v` | خرج تفصيلي |
| `-n` | دون تحويل القيم للعرض (قيم خام) |
| `-s` | أسماء مختصرة للوسوم |
| `-G` | عرض اسم المجموعة لكل وسم |
| `-j` | خرج JSON |
| `-csv` | خرج CSV |
| `-overwrite_original` | عدم إنشاء نسخة احتياطية |

---

<a id="-reading-metadata"></a>
## 📖 قراءة البيانات الوصفية

<a id="view-all-metadata"></a>
### عرض جميع البيانات الوصفية

```bash
# جميع البيانات الوصفية
exiftool image.jpg

# خرج مفصّل
exiftool -v image.jpg

# خرج شديد التفصيل
exiftool -v3 image.jpg
```

<a id="specific-tags"></a>
### وسوم محددة

```bash
# وسم واحد
exiftool -GPSLatitude image.jpg

# عدة وسوم
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# استبعاد وسوم
exiftool --MakerNotes image.jpg
```

<a id="group-based-reading"></a>
### القراءة حسب المجموعة

```bash
# عرض جميع وسوم EXIF
exiftool -EXIF:all image.jpg

# عرض جميع وسوم IPTC
exiftool -IPTC:all image.jpg

# عرض جميع وسوم XMP
exiftool -XMP:all image.jpg

# عرض جميع وسوم GPS
exiftool -GPS:all image.jpg

# العرض مع أسماء المجموعات
exiftool -G image.jpg
```

<a id="common-metadata-groups"></a>
### مجموعات البيانات الوصفية الشائعة

| المجموعة | الوصف |
|-------|-------------|
| EXIF | بيانات الكاميرا أو الصورة |
| IPTC | بيانات وصفية للأخبار والصحافة |
| XMP | بيانات Adobe الوصفية القابلة للتوسّع |
| GPS | بيانات الموقع |
| MakerNotes | بيانات خاصة بالكاميرا |
| File | معلومات الملف الأساسية |
| Composite | قيم محسوبة |

<a id="output-formats"></a>
### تنسيقات الخرج

```bash
# خرج JSON
exiftool -j image.jpg

# خرج CSV
exiftool -csv image.jpg

# خرج HTML
exiftool -htmlDump image.jpg > output.html

# فصل القيم بمحرف الجدولة
exiftool -t image.jpg

# تنسيق مخصص
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="-writing-metadata"></a>
## ✏️ كتابة البيانات الوصفية

<a id="write-single-tag"></a>
### كتابة وسم واحد

```bash
# تعيين المؤلف
exiftool -Author="John Doe" image.jpg

# تعيين حقوق النشر
exiftool -Copyright="© 2024 Company" image.jpg

# تعيين تعليق
exiftool -Comment="My photo" image.jpg
```

<a id="write-multiple-tags"></a>
### كتابة عدة وسوم

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

<a id="write-gps-coordinates"></a>
### كتابة إحداثيات GPS

```bash
# تعيين إحداثيات GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# مع وسوم تحديد الجهات
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# باستخدام تنسيق إحداثيات رابط Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

<a id="write-datetime"></a>
### كتابة التاريخ والوقت

```bash
# تعيين التاريخ والوقت
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# تعيين جميع التواريخ
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# إزاحة التواريخ
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # إضافة 1 سنة
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # طرح 7 أيام
```

<a id="copy-metadata-between-files"></a>
### نسخ البيانات الوصفية بين الملفات

```bash
# نسخ جميع البيانات الوصفية
exiftool -TagsFromFile source.jpg destination.jpg

# نسخ وسوم محددة
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# النسخ إلى عدة ملفات
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="-removing-metadata"></a>
## 🗑️ حذف البيانات الوصفية

<a id="remove-all-metadata"></a>
### حذف جميع البيانات الوصفية

```bash
# حذف جميع البيانات الوصفية (مع إبقاء الملف قابلًا للاستخدام)
exiftool -all= image.jpg

# الحذف دون نسخة احتياطية
exiftool -overwrite_original -all= image.jpg

# معالجة عدة ملفات
exiftool -all= *.jpg
exiftool -r -all= directory/
```

> **توضيح للحذف:** لا يضمن الخيار `-all=` حذف كل البيانات الوصفية في كل تنسيق، وبعض البنى ضرورية لعرض الملف أو لا تقبل الحذف. لذلك لا تكفي عبارة «حذف الكل» وحدها لإثبات إزالة جميع المعلومات الحساسة، وقد يؤثر الحذف في معلومات الألوان أو عرض بعض صيغ RAW. [حدود الحذف في توثيق ExifTool](https://exiftool.org/exiftool_pod.html).

<a id="remove-specific-metadata"></a>
### حذف بيانات وصفية محددة

```bash
# حذف بيانات GPS فقط
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# حذف EXIF فقط
exiftool -EXIF:all= image.jpg

# حذف التعليقات
exiftool -Comment= image.jpg

# حذف معلومات المؤلف
exiftool -Author= -Creator= -Artist= image.jpg
```

<a id="remove-from-specific-file-types"></a>
### الحذف من أنواع ملفات محددة

```bash
# الصور
exiftool -all= *.jpg *.png *.gif

# ملفات PDF
exiftool -all= document.pdf

# ملفات Office
exiftool -all= *.docx *.xlsx *.pptx

# ملفات الفيديو
exiftool -all= video.mp4
```

> **تصحيح بحسب نوع الملف:** يعدّل ExifTool ملفات PDF بإضافة تحديثات متتابعة؛ تبقى المعلومات القديمة قابلة للاسترجاع، فلا يُعد هذا حذفًا آمنًا لها. كذلك يسرد التوثيق DOCX وXLSX وPPTX كتنسيقات للقراءة، لا لكتابة البيانات الوصفية؛ لذا لا تعمل أمثلة حذف بيانات Office أعلاه كما توحي صياغة المصدر. احتُفظ بالأوامر كما وردت. [توثيق PDF الرسمي](https://exiftool.org/TagNames/PDF.html)، [جدول التنسيقات الرسمي](https://exiftool.org/ExifTool.pdf).

<a id="sanitize-for-privacy"></a>
### تنقية البيانات لحماية الخصوصية

```bash
# حذف جميع المعلومات التي تكشف الهوية
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="-file-types"></a>
## 📁 أنواع الملفات

<a id="supported-file-types-400"></a>
### أنواع الملفات المدعومة (400+)

| الفئة | التنسيقات |
|----------|---------|
| **الصور** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **صور RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **الفيديو** | MP4, MOV, AVI, MKV, WebM |
| **الصوت** | MP3, WAV, FLAC, M4A, OGG |
| **المستندات** | PDF, DOCX, XLSX, PPTX |
| **الأرشيفات** | ZIP (دعم محدود) |

<a id="file-type-specific"></a>
### الاستخدام حسب نوع الملف

```bash
# البيانات الوصفية لملفات PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# البيانات الوصفية للفيديو
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# البيانات الوصفية للصوت
exiftool -Artist -Album -Title -Duration audio.mp3

# مستندات Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="-osint--forensics"></a>
## 🔍 استخبارات المصادر المفتوحة والتحليل الجنائي الرقمي

<a id="extract-gps-location"></a>
### استخراج موقع GPS

```bash
# عرض إحداثيات GPS
exiftool -GPS:all image.jpg

# بيانات GPS بصيغة مقروءة
exiftool -GPSPosition image.jpg

# درجات عشرية
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# رابط Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

<a id="device-identification"></a>
### تحديد الجهاز

```bash
# معلومات الكاميرا
exiftool -Make -Model -SerialNumber image.jpg

# البرنامج المستخدم
exiftool -Software -CreatorTool image.jpg

# معلومات العدسة (إن توفرت)
exiftool -Lens -LensModel -FocalLength image.jpg

# معلومات تفصيلية عن الكاميرا
exiftool -MakerNotes:all image.jpg
```

<a id="timeline-analysis"></a>
### تحليل التسلسل الزمني

```bash
# جميع التواريخ والأوقات
exiftool -time:all image.jpg

# أوقات نظام الملفات
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# أوقات EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# البحث عن اختلافات التواريخ
exiftool -a -G1 -s -time:all image.jpg
```

<a id="document-forensics"></a>
### التحليل الجنائي الرقمي للمستندات

```bash
# تحليل PDF
exiftool -a -G1 document.pdf

# سجل ملف Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# العثور على معلومات المؤلف
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

<a id="image-authenticity"></a>
### فحص أصالة الصورة

```bash
# التحقق من برنامج التحرير
exiftool -Software -HistorySoftwareAgent image.jpg

# مقارنة الصورة المصغرة بالصورة الأصلية
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# التحقق من التعديلات
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

<a id="useful-osint-queries"></a>
### استعلامات مفيدة لجمع المعلومات من المصادر المفتوحة

```bash
# استخراج جميع المعلومات التي تكشف الهوية
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# التحقق من آثار أدوات إخفاء البيانات (Steganography)
exiftool -Software image.jpg | grep -i "steg\|hide"

# استخراج الصورة المصغرة المضمّنة
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# البحث عن آثار منصات التواصل الاجتماعي
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="-batch-processing"></a>
## ⚙️ معالجة الملفات على دفعات

<a id="process-multiple-files"></a>
### معالجة عدة ملفات

```bash
# جميع ملفات JPG في المجلد الحالي
exiftool *.jpg

# معالجة تكرارية
exiftool -r directory/

# امتدادات محددة
exiftool -ext jpg -ext png directory/
```

<a id="batch-remove-metadata"></a>
### حذف البيانات الوصفية على دفعات

```bash
# الحذف من جميع الصور
exiftool -all= -overwrite_original -r directory/

# أنواع محددة فقط
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

<a id="batch-rename-files"></a>
### إعادة تسمية الملفات على دفعات

```bash
# إعادة التسمية حسب التاريخ
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# إعادة التسمية حسب طراز الكاميرا
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# إعادة التسمية باستخدام عدّاد
exiftool '-filename<photo_%04c.%%e' *.jpg
```

> **ملاحظة على إعادة التسمية:** يميّز ExifTool بين الإسناد الحرفي للوسم والنقل باستخدام `<`. مثال العدّاد في المصدر منقول كما هو، لكنه لا يذكر وسمًا مصدرًا؛ فلا يُفترض أنه صيغة إعادة تسمية مكتملة. كما أن `%c` يعالج أرقام النسخ عند تعارض الأسماء، وتلزم مضاعفة `%` لرموز اسم الملف عند تضمينها في تنسيق تاريخ. [توثيق FileName الرسمي](https://exiftool.org/filename.html).

<a id="batch-copy-metadata"></a>
### نسخ البيانات الوصفية على دفعات

```bash
# نسخ GPS من ملف واحد إلى جميع الملفات
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

<a id="export-to-csv"></a>
### التصدير إلى CSV

```bash
# تصدير البيانات الوصفية إلى CSV
exiftool -csv *.jpg > metadata.csv

# حقول محددة
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

<a id="conditional-processing"></a>
### المعالجة المشروطة

```bash
# الملفات التي تحتوي على GPS فقط
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# ملفات كاميرا محددة فقط
exiftool -if '$Make eq "Canon"' -filename *.jpg

# الملفات المعدّلة بعد تاريخ محدد
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="most-used-commands"></a>
### الأوامر الأكثر استخدامًا

| الغرض | الأمر |
|---------|---------|
| عرض جميع البيانات الوصفية | `exiftool image.jpg` |
| عرض بيانات GPS | `exiftool -GPS:all image.jpg` |
| حذف جميع البيانات الوصفية | `exiftool -all= image.jpg` |
| حذف بيانات GPS | `exiftool -GPS:all= image.jpg` |
| خرج JSON | `exiftool -j image.jpg` |
| معالجة تكرارية | `exiftool -r directory/` |
| معلومات الكاميرا | `exiftool -Make -Model image.jpg` |
| الطوابع الزمنية | `exiftool -time:all image.jpg` |

<a id="common-tags"></a>
### الوسوم الشائعة

| الوسم | الوصف |
|-----|-------------|
| `-Make` | الشركة المصنّعة للكاميرا |
| `-Model` | طراز الكاميرا |
| `-DateTimeOriginal` | وقت التقاط الصورة |
| `-GPSLatitude` | خط عرض GPS |
| `-GPSLongitude` | خط طول GPS |
| `-Author` | مؤلف الملف |
| `-Copyright` | معلومات حقوق النشر |
| `-Software` | برنامج التحرير |
| `-ImageWidth` | عرض الصورة |
| `-ImageHeight` | ارتفاع الصورة |

<a id="privacy-checklist"></a>
### قائمة التحقق للخصوصية

```bash
# 1. فحص البيانات الوصفية الموجودة
exiftool -a -G1 image.jpg

# 2. التحقق من GPS
exiftool -GPS:all image.jpg

# 3. التحقق من المعلومات التي تكشف الهوية
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. حذف كل شيء
exiftool -all= -overwrite_original image.jpg

# 5. التحقق من الحذف
exiftool image.jpg
```

<a id="forensic-checklist"></a>
### قائمة التحقق للتحليل الجنائي الرقمي

```bash
# 1. الحفاظ على الأصل (إنشاء نسخة أولًا!)
cp image.jpg image_original.jpg

# 2. تفريغ كامل للبيانات الوصفية
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. استخراج GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. التسلسل الزمني
exiftool -time:all -G1 image.jpg

# 5. معلومات الجهاز
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. التحقق من العبث
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

<a id="-resources"></a>
## 📚 مصادر

- [موقع ExifTool الرسمي](https://exiftool.org/)
- [توثيق ExifTool](https://exiftool.org/TagNames/)
- [منتدى ExifTool](https://exiftool.org/forum/)
- [أنواع الملفات المدعومة](https://exiftool.org/#supported)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Autopsy](../../Autopsy/translations/README.ar.md)
- [Volatility](../../Volatility/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)

---

<p align="center">
  <b>📷 إتقان تحليل البيانات الوصفية!</b><br>
  <i>ExifTool - لكل ملف قصة</i>
</p>
