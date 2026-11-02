<a id="-autopsy---digital-forensics-platform-cheatsheet"></a>
# 🔍 ورقة مرجعية لمنصة الأدلة الجنائية الرقمية Autopsy

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Digital Forensics Platform
```

يشير الرسم أعلاه إلى منصة Autopsy للأدلة الجنائية الرقمية، وقد حُفظ بتنسيقه الأصلي.

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Autopsy؟](#-what-is-autopsy)
- [التثبيت](#-installation)
- [إنشاء قضية](#-creating-a-case)
- [إضافة مصادر البيانات](#-adding-data-sources)
- [وحدات المعالجة الأولية (Ingest Modules)](#-ingest-modules)
- [ميزات التحليل](#-analysis-features)
- [تحليل الملفات](#-file-analysis)
- [تحليل الخط الزمني](#-timeline-analysis)
- [البحث بالكلمات المفتاحية (Keyword Search)](#-keyword-search)
- [إعداد التقارير](#-reporting)
- [أدوات سطر الأوامر (Sleuth Kit)](#-cli-tools-sleuth-kit)

---

<a id="-what-is-autopsy"></a>
## 🎯 ما هو Autopsy؟

**Autopsy** منصة مجانية ومفتوحة المصدر للأدلة الجنائية الرقمية، مبنية على The Sleuth Kit (TSK). وهي توفر:

- 🖥️ **واجهة رسومية (GUI)** - تحليلًا جنائيًا رقميًا سهل الاستخدام
- 💾 **تحليل الأقراص** - فحص الأقراص الصلبة ووسائط USB وصور الأقراص
- 📁 **استعادة الملفات** - استعادة الملفات المحذوفة
- 📊 **تحليل الخط الزمني** - إعادة بناء نشاط المستخدم
- 🔍 **Keyword Search** - البحث في جميع البيانات
- 📧 **تحليل البريد الإلكتروني** - تحليل قواعد بيانات البريد الإلكتروني
- 🌐 **آثار الويب** - سجل المتصفح والإشارات المرجعية
- 📱 **الأدلة الجنائية للأجهزة المحمولة** - تحليل بيانات Android/iOS

<a id="supported-evidence-types"></a>
### أنواع الأدلة المدعومة

| النوع | الصيغ |
|------|---------|
| **صور الأقراص** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk** | أقراص فعلية/منطقية |
| **ملفات الأجهزة الافتراضية** | VMDK, VHD, VHDX |
| **Logical Files** | مجلدات وملفات |
| **Unallocated Space** | بيانات مستعادة بالتنقيب (Carving) |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="windows"></a>
### Windows

```bash
# نزّل من الموقع الرسمي
https://www.autopsy.com/download/

# شغّل برنامج التثبيت
autopsy-x.x.x-64bit.msi

# المجلد الافتراضي
C:\Program Files\Autopsy-x.x.x\
```

<a id="linux-kali"></a>
### Linux (Kali)

```bash
# التثبيت باستخدام apt
sudo apt update
sudo apt install autopsy

# التشغيل
autopsy
```

<a id="requirements"></a>
### المتطلبات

| المكوّن | الحد الأدنى | الموصى به |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | متعدد الأنوية | 4 أنوية أو أكثر |
| **التخزين** | SSD | NVMe SSD |
| **OS** | Windows 10/11, Linux | Windows 10/11 |

---

<a id="-creating-a-case"></a>
## 📁 إنشاء قضية

<a id="new-case-wizard"></a>
### معالج إنشاء قضية جديدة

1. **File → New Case (قضية جديدة)**
2. أدخل **Case Name** (مثل "Investigation_2024")
3. أدخل **Base Directory** (المجلد الذي تُحفظ فيه ملفات القضية)
4. اختر **Case Type**:
   - Single-user (لمستخدم واحد، محلي)
   - Multi-user (لعدة مستخدمين، تعاوني)
5. أدخل **Case Number** (اختياري)
6. أدخل معلومات **Examiner**
7. انقر على **Finish**

<a id="case-structure"></a>
### بنية ملفات القضية

```
CaseName/
├── autopsy.db           # Case database (SQLite)
├── Reports/             # Generated reports
├── Export/              # Exported files
├── ModuleOutput/        # Module results
└── [HostName]/          # Per-host data
    ├── [DataSource]/    # Evidence files
    └── Reports/
```

توضح البنية أعلاه ملفات قاعدة القضية، والتقارير Reports، والملفات المصدّرة Export، ونتائج الوحدات ModuleOutput، والبيانات الخاصة بكل مضيف ومصدر. حُفظت أسماء الملفات والمجلدات والتعليقات داخل الرسم كما في الأصل.

<a id="case-properties"></a>
### خصائص القضية

| الحقل | الوصف |
|-------|-------------|
| Case Name | معرّف التحقيق |
| Case Number | الرقم المرجعي |
| Examiner | اسم المحلّل |
| Base Directory | مكان حفظ القضية |
| Case Type | لمستخدم واحد/عدة مستخدمين |

---

<a id="-adding-data-sources"></a>
## 📀 إضافة مصادر البيانات

<a id="data-source-types"></a>
### أنواع مصادر البيانات

| النوع | الوصف |
|------|-------------|
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | تحليل قرص فعلي |
| **Logical Files** | استيراد مجلد/ملف |
| **Unallocated Space** | صورة خام للمساحة الحرة |

<a id="adding-disk-image"></a>
### إضافة صورة قرص

1. **Add Data Source** (شريط الأدوات)
2. اختر **Disk Image or VM File**
3. استعرض الملفات وحدّد ملف صورة القرص
4. اختر **Time Zone**
5. اضبط **وحدات المعالجة الأولية (Ingest Modules)** (القسم التالي)
6. انقر على **Finish**

<a id="adding-local-disk"></a>
### إضافة قرص محلي

```
⚠️ تحذير: استخدم مانع كتابة أو وضع القراءة فقط!
```

1. **Add Data Source**
2. اختر **Local Disk**
3. اختر القرص من القائمة
4. اضبط وحدات المعالجة الأولية
5. **Finish**

<a id="supported-image-formats"></a>
### صيغ صور الأقراص المدعومة

| الصيغة | الامتداد | الوصف |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | نسخة مطابقة بتًّا ببت |
| EnCase | .E01, .Ex01 | صيغة Expert Witness |
| AFF | .aff | صيغة الأدلة الجنائية المتقدمة (Advanced Forensic Format) |
| VMDK | .vmdk | قرص VMware |
| VHD/VHDX | .vhd, .vhdx | قرص Hyper-V |

---

<a id="-ingest-modules"></a>
## ⚙️ وحدات المعالجة الأولية (Ingest Modules)

<a id="what-are-ingest-modules"></a>
### ما هي وحدات المعالجة الأولية؟

وحدات تحليل مؤتمتة تعمل على مصادر البيانات لاستخراج الآثار الرقمية (Artifacts).

<a id="core-modules"></a>
### الوحدات الأساسية

| الوحدة | الوصف |
|--------|-------------|
| **Recent Activity** | سجل المتصفح والتنزيلات وملفات تعريف الارتباط |
| **Hash Lookup** | مقارنة قيم MD5/SHA1 بقواعد بيانات معروفة |
| **File Type Identification** | تحديد نوع الملف اعتمادًا على توقيعه |
| **Keyword Search** | فهرسة المحتوى والبحث فيه |
| **Email Parser** | تحليل PST وMBOX وEML |
| **Extension Mismatch** | اكتشاف الملفات التي غُيّرت أسماؤها |
| **Embedded File Extractor** | الاستخراج من الأرشيفات والمستندات |
| **EXIF Parser** | استخراج البيانات الوصفية للصور |
| **Encryption Detection** | العثور على الملفات المشفّرة |
| **Interesting Files** | العثور على ملفات جديرة بالمراجعة |
| **PhotoRec Carver** | استعادة الملفات بالتنقيب (File Carving) |
| **Virtual Machine Extractor** | استخراج ملفات الأجهزة الافتراضية |

<a id="hash-databases"></a>
### قواعد بيانات البصمات (Hashes)

```
# أضف قواعد بيانات للبصمات المعروفة
Tools → Options → Hash Database

# الأنواع:
- Known (NSRL) - تجاهل الملفات المعروفة السليمة
- Known Bad - أصدر تنبيهًا عند مطابقة بصمات برمجيات خبيثة
```

<a id="nsrl-national-software-reference-library"></a>
### NSRL (National Software Reference Library)

```bash
# نزّل NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# الاستيراد إلى Autopsy
Tools → Options → Hash Database → Import Database
```

<a id="module-configuration"></a>
### إعداد الوحدات

```
# فعّل الوحدات أو عطّلها لكل قضية
انقر بزر الفأرة الأيمن على مصدر البيانات → Run Ingest Modules

# اضبط إعدادات الوحدة
انقر على رمز الترس بجوار اسم الوحدة
```

---

<a id="-analysis-features"></a>
## 🔬 ميزات التحليل

<a id="tree-view-left-panel"></a>
### العرض الشجري (اللوحة اليسرى)

```
Data Sources
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Custom Tags]
```

في الشجرة أعلاه تعرض Data Sources مصادر الأدلة، وتعرض Views طرق تصفح الملفات، بينما تجمع Results الآثار المستخرجة ونتائج المطابقة، وتحتوي Tags على الوسوم. حُفظت أسماء عناصر الواجهة والشجرة كما في الأصل.

<a id="quick-analysis-areas"></a>
### مجالات التحليل السريع

| الفئة | ما الذي تبحث عنه؟ |
|----------|-----------------|
| **Deleted Files** | ملفات مستعادة وملفات حذفها المستخدم |
| **Web History** | المواقع التي زارها المستخدم وعمليات البحث |
| **Downloads** | الملفات المنزّلة |
| **Recent Documents** | الملفات التي فُتحت حديثًا |
| **USB Devices** | الأجهزة المتصلة |
| **Email** | المراسلات |
| **Images** | صور ولقطات شاشة |
| **Archives** | محتويات ZIP وRAR |

---

<a id="-file-analysis"></a>
## 📄 تحليل الملفات

<a id="viewing-files"></a>
### عرض الملفات

```
# Content Viewer (لوحة عرض المحتوى السفلية)
- Hex        : عرض خام بالنظام الست عشري
- Text       : المحتوى النصي
- Application: عرض المحتوى بعد معالجته
- Strings    : السلاسل النصية المستخرجة
- Metadata   : البيانات الوصفية للملف
- Results    : نتائج التحليل
- Indexed Text: نص قابل للبحث
```

<a id="file-properties"></a>
### خصائص الملفات

| الخاصية | الوصف |
|----------|-------------|
| Name | اسم الملف |
| Location | المسار الكامل |
| Size | حجم الملف |
| Created | وقت الإنشاء |
| Modified | وقت آخر تعديل |
| Accessed | وقت آخر وصول |
| MD5/SHA1/SHA256 | قيم البصمات |
| Known | الحالة في NSRL |
| MIME Type | النوع المكتشف |

<a id="extracting-files"></a>
### استخراج الملفات

```
# استخرج ملفًا واحدًا
انقر بزر الفأرة الأيمن → Extract File(s)

# استخرج عدة ملفات
حدّد الملفات → انقر بزر الفأرة الأيمن → Extract

# استخرج إلى موقع محدّد
Actions → Extract to...
```

<a id="deleted-file-recovery"></a>
### استعادة الملفات المحذوفة

```
Views → Deleted Files

# يعرض:
- ملفات موجودة في $Recycle.Bin
- ملفات موسومة بالمحذوفة في MFT
- ملفات مستعادة بالتنقيب (PhotoRec)
```

---

<a id="-timeline-analysis"></a>
## 📊 تحليل الخط الزمني

<a id="timeline-view"></a>
### عرض الخط الزمني

```
# افتح Timeline
Tools → Timeline

# أو
Results → Timeline
```

<a id="timeline-features"></a>
### ميزات الخط الزمني

| الميزة | الوصف |
|---------|-------------|
| **Counts View** | تكرار الأحداث بمرور الوقت |
| **Details View** | أحداث منفردة |
| **List View** | قائمة أحداث في جدول |
| **Filters** | تصفية بحسب النوع والتاريخ وغيرهما |

<a id="event-types"></a>
### أنواع الأحداث

| النوع | اللون | الوصف |
|------|-------|-------------|
| File Modified | أزرق | تغيّر محتوى الملف |
| File Accessed | أخضر | فُتح الملف |
| File Created | أصفر | أُنشئ الملف |
| File Changed | أحمر | تغيّرت البيانات الوصفية |
| Web Activity | بنفسجي | أحداث المتصفح |

<a id="timeline-filters"></a>
### مرشّحات الخط الزمني

```
# صفِّ بحسب النطاق الزمني
Start Date: 2024-01-01
End Date: 2024-12-31

# صفِّ بحسب النوع
☑ File System
☑ Web Activity
☐ Registry (إذا جرى تحليلها)

# صفِّ بحسب مصدر البيانات
☑ Disk Image 1
☐ Disk Image 2
```

<a id="timeline-export"></a>
### تصدير الخط الزمني

```
# صدّر الخط الزمني
File → Export → Timeline (CSV/TSV)

# استخدمه مع أدوات خارجية
- Excel
- plaso/log2timeline
- timesketch
```

---

<a id="-keyword-search"></a>
## 🔍 البحث بالكلمات المفتاحية (Keyword Search)

<a id="creating-keyword-lists"></a>
### إنشاء قوائم كلمات مفتاحية

```
# Tools → Options → Keyword Search

# أضف قائمة جديدة
1. انقر على "New List"
2. سمِّ القائمة
3. أضف الكلمات المفتاحية:
   - Literal (مطابقة حرفية)
   - Regex (نمط)
   - Substring (جزء من سلسلة نصية)
```

<a id="common-search-keywords"></a>
### كلمات مفتاحية شائعة للبحث

```
# بيانات مالية
credit card
bank account
swift
iban
bitcoin
wallet

# بيانات الاعتماد
password
login
username
secret
key

# معلومات حساسة
confidential
secret
classified
internal only

# الشبكة
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

<a id="regex-examples"></a>
### أمثلة تعابير نمطية (Regex)

| الغرض | Regex |
|---------|-------|
| Email | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| عنوان IP | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| رقم هاتف (الولايات المتحدة) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| بطاقة ائتمان | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

<a id="running-search"></a>
### إجراء البحث

```
# بحث مباشر (Ad-hoc)
Keyword Search (أعلى اليمين) → أدخل مصطلح البحث → Search

# استخدام قوائم الكلمات المفتاحية
انقر بزر الفأرة الأيمن على مصدر البيانات → Run Ingest Modules → Keyword Search
```

---

<a id="-reporting"></a>
## 📝 إعداد التقارير

<a id="generate-report"></a>
### إنشاء تقرير

```
# Generate Report
Tools → Generate Report

# أو
انقر بزر الفأرة الأيمن case → Generate Report
```

<a id="report-types"></a>
### أنواع التقارير

| النوع | الوصف |
|------|-------------|
| **HTML Report** | تقرير ويب يتضمن الصور |
| **Excel Report** | بصيغة جدول بيانات |
| **Text Report** | نص عادي |
| **TSK Body File** | ملف Body للخط الزمني |
| **Files - FS** | تقرير نظام الملفات |
| **Portable Case** | جزء من القضية قابل للمشاركة |

<a id="html-report-options"></a>
### خيارات تقرير HTML

```
# حدّد البيانات التي سيشملها التقرير
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# الخيارات
☑ Include thumbnails
☑ Include metadata
```

<a id="portable-case"></a>
### Portable Case (قضية محمولة)

```
# أنشئ قضية محمولة للمشاركة
Generate Report → Portable Case

# يتضمن:
- الأدلة المحدّدة
- نتائج التحليل
- يمكن فتحه في Autopsy
```

---

<a id="-cli-tools-sleuth-kit"></a>
## 💻 أدوات سطر الأوامر (Sleuth Kit)

<a id="sleuth-kit-commands"></a>
### أوامر Sleuth Kit

يستخدم Autopsy أدوات The Sleuth Kit (TSK) في الخلفية. تتوفر أوامر سطر الأوامر التالية:

<a id="image-information"></a>
### معلومات صورة القرص

```bash
# احصل على معلومات صورة القرص
img_stat image.E01
img_stat image.dd

# احصل على معلومات نظام الملفات
fsstat -o 2048 image.dd
```

<a id="file-listing"></a>
### عرض قائمة الملفات

```bash
# اعرض الملفات
fls -r -o 2048 image.dd

# اعرض الملفات المحذوفة
fls -r -d -o 2048 image.dd

# الخيارات:
# -r    تكراري عبر المجلدات
# -d    الملفات المحذوفة فقط
# -o    الإزاحة (بداية القسم)
```

<a id="file-recovery"></a>
### استعادة الملفات

```bash
# احصل على الملف باستخدام رقم inode
icat -o 2048 image.dd 12345 > recovered_file

# احصل على محتوى الملف
icat -r -o 2048 image.dd 12345 > file.txt
```

<a id="timeline"></a>
### الخط الزمني

```bash
# أنشئ ملف Body
fls -r -m "/" -o 2048 image.dd > body.txt

# أنشئ خطًا زمنيًا
mactime -b body.txt -d > timeline.csv
```

<a id="hash-calculation"></a>
### حساب البصمات

```bash
# احسب MD5
md5sum image.dd

# احسب SHA256
sha256sum image.dd

# تحقق من صورة القرص
sha256sum -c image.dd.sha256
```

<a id="common-tsk-tools"></a>
### أدوات TSK شائعة

| الأداة | الغرض |
|------|---------|
| `img_stat` | معلومات صورة القرص |
| `mmls` | جدول الأقسام |
| `fsstat` | معلومات نظام الملفات |
| `fls` | قائمة الملفات |
| `icat` | استخراج ملف باستخدام رقم inode |
| `istat` | معلومات inode |
| `blkls` | استخراج المساحة غير المخصّصة |
| `srch_strings` | العثور على السلاسل النصية |
| `tsk_recover` | استعادة جميع الملفات |
| `mactime` | إنشاء خط زمني |

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="investigation-workflow"></a>
### سير عمل التحقيق

```
1. Create Case          → New case with details
2. Add Data Source      → Disk image or drive
3. Run Ingest Modules   → Automated analysis
4. Review Results       → Check extracted content
5. Keyword Search       → Search for terms
6. Timeline Analysis    → Reconstruct events
7. Tag Evidence         → Mark important items
8. Generate Report      → Create final report
```

مراحل سير العمل أعلاه: إنشاء القضية، وإضافة مصدر البيانات، وتشغيل المعالجة، ومراجعة النتائج، والبحث بالكلمات المفتاحية، وتحليل الخط الزمني، ووسم الأدلة، ثم إنشاء التقرير. حُفظ مخطط الخطوات الأصلي بتنسيقه.

<a id="keyboard-shortcuts"></a>
### اختصارات لوحة المفاتيح

| الاختصار | الإجراء |
|----------|--------|
| Ctrl+N | New Case (قضية جديدة) |
| Ctrl+O | Open Case (فتح قضية) |
| Ctrl+F | البحث (داخل الجدول) |
| Ctrl+G | إنشاء تقرير |
| F5 | تحديث العرض |

<a id="common-locations-to-check"></a>
### مواقع شائعة للفحص

| Windows | المسار |
|---------|------|
| ملفات حديثة | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Downloads | `Users\*\Downloads` |
| سجل المتصفح | `Users\*\AppData\Local\[Browser]\User Data` |
| ملفات مؤقتة | `Users\*\AppData\Local\Temp` |
| سلة المحذوفات | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| سجلات الأحداث | `Windows\System32\winevt\Logs` |

---

<a id="-resources"></a>
## 📚 المراجع

- [الموقع الرسمي لـ Autopsy](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [توثيق Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [تدريب الاستجابة للحوادث والتحليل الجنائي الرقمي](https://www.dfir.training/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Volatility](../../Volatility/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)
- [Wireshark](../../Wireshark/translations/README.ar.md)

---



> **ملاحظات الإصدارات:** واجهة القضايا والوحدات هنا تصف Autopsy الحديث؛ أما حزمة `autopsy` في Kali المذكورة في أمر apt فهي Autopsy Forensic Browser القديم (2.24 في صفحة Kali)، ولا تطابق واجهة 4.x. توصي وثائق Autopsy 4.23.0 بحد أدنى 16GB من RAM، بخلاف قيمة 8 GB في جدول المصدر. وتحتاج القضايا متعددة المستخدمين إلى خدمات شبكة وإعداد إضافي. راجع [حزمة Kali](https://www.kali.org/tools/autopsy/) و[التثبيت الرسمي](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/installation_page.html).
>
> أسماء القوائم والاختصارات والوحدات وبعض خيارات العرض تتغير بين الإصدارات؛ حُفظت أسماء المصدر لتسهيل مطابقتها. تعرض الوثائق الحديثة إعدادات البصمات باسم Hash Sets، وتوضح أن المطابقة تعتمد على MD5 مع حساب SHA-256 أيضًا، لا على كل خوارزميات الجدول بالطريقة نفسها. وجود ملف في NSRL يعني أنه معروف، ولا يثبت أنه سليم أو غير مهم للتحقيق. راجع [Hash Lookup](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/hash_db_page.html) و[دليل المستخدم](https://sleuthkit.org/autopsy/docs/user-docs/latest/).
>
> في أوامر TSK، تمثل `-o 2048` إزاحة بداية نظام الملفات بالقطاعات، وليست قيمة ثابتة لكل صورة أو إزاحة بالبايتات. الخيار `-r` في icat يفعّل أساليب استعادة الملفات المحذوفة؛ كما أن وصف tsk_recover في الجدول مبسّط: يستعيد افتراضيًا الملفات غير المخصّصة، وتحتاج استعادة جميع الملفات إلى الخيار المناسب. راجع أدلة [fls](https://www.sleuthkit.org/sleuthkit/man/fls.html) و[icat](https://www.sleuthkit.org/sleuthkit/man/icat.html) و[tsk_recover](https://www.sleuthkit.org/sleuthkit/man/tsk_recover.html).
>
> قوائم الكلمات المفتاحية أمثلة بحث حرفية حُفظت بالإنجليزية، وليست ترجمتها العربية هي قيمة البحث. والتعابير النمطية أمثلة أولية: نمط IPv4 لا يقيّد كل جزء إلى 0–255، ومطابقة أرقام البطاقات لا تثبت صحتها. كما أن ألوان الأحداث ومسارات العرض أمثلة من المصدر، ولا تُعدّ وحدها تفسيرًا جنائيًا؛ يوضح [دليل Timeline](https://sleuthkit.org/autopsy/docs/user-docs/4.23.0/timeline_page.html) أنواع الأحداث وأوضاع العرض والمنطقة الزمنية.

<p align="center">
  <b>🔍 أتقن الأدلة الجنائية الرقمية!</b><br>
  <i>Autopsy — رفيق المحقّق الرقمي</i>
</p>
