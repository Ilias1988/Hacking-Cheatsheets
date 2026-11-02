<a id="-gau---get-all-urls"></a>
# 🔗 GAU — جمع عناوين URL المعروفة (Get All URLs)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Fast URL Harvesting
```

يعرض الشعار اسم GAU ووظيفتها في جمع عناوين URL بسرعة.

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هي GAU؟](#-what-is-gau)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [مزودو البيانات](#-providers)
- [الترشيح](#-filtering)
- [التكامل مع الأدوات](#-integration)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-gau"></a>
## 🎯 ما هي GAU؟

تجلب **GAU** ‏(Get All URLs) عناوين URL المعروفة من مصادر متعددة:

- 🔍 **Wayback Machine** — عناوين URL تاريخية
- 🌐 **Common Crawl** — بيانات الزحف إلى الويب
- 📡 **AlienVault OTX** — عناوين URL من معلومات التهديدات
- 🔒 **URLScan.io** — عناوين URL سبق فحصها

<a id="why-gau"></a>
### لماذا GAU؟

|الفائدة|الوصف|
|---------|-------------|
|**استطلاع سلبي**|دون اتصال مباشر بالهدف أثناء جمع البيانات|
|**بيانات تاريخية**|العثور على نقاط نهاية قديمة أو منسية|
|**سريع**|جلب متوازٍ|
|**مصادر متعددة**|تغطية أوسع للنتائج|

---

<a id="-installation"></a>
## 🚀 التثبيت

```bash
# التثبيت باستخدام Go، وهو الأسلوب المقترح
go install github.com/lc/gau/v2/cmd/gau@latest

# التثبيت من الشيفرة المصدرية
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# التحقق
gau --version
```

---

<a id="-basic-usage"></a>
## 📖 الاستخدام الأساسي

<a id="single-domain"></a>
### نطاق واحد

> **تصحيح:** الخيار `--subs` يضم النطاقات الفرعية إلى النتائج؛ ليس خيار وضع هادئ أو إخفاء الشعار. أُبقي مثال الأصل كما هو. [دليل GAU الرسمي](https://github.com/lc/gau/blob/master/README.md).

```bash
# الاستخدام الأساسي
gau example.com

# مع حفظ المخرجات في ملف
gau example.com -o urls.txt

# الوضع الهادئ دون شعار بحسب الأصل؛ راجع تصحيح ‎--subs
gau --subs example.com
```

<a id="multiple-domains"></a>
### نطاقات متعددة

```bash
# القراءة من الإدخال القياسي (stdin)
echo "example.com" | gau

# نطاقات متعددة
cat domains.txt | gau

# من ملف
gau --threads 5 < domains.txt
```

<a id="include-subdomains"></a>
### تضمين النطاقات الفرعية

```bash
# تضمين النطاقات الفرعية في النتائج
gau --subs example.com
```

<a id="threading"></a>
### التنفيذ المتوازي

```bash
# تحديد عدد العمال المتزامنين، والافتراضي 1 بحسب الأصل
gau --threads 5 example.com

# تعديل التزامن لمراعاة حدود المعدل
gau --threads 2 example.com
```

---

<a id="-providers"></a>
## 🌐 مزودو البيانات

<a id="available-providers"></a>
### المزودون المتاحون

|المزود|الوصف|افتراضي|
|----------|-------------|---------|
|`wayback`|Wayback Machine|✅|
|`commoncrawl`|Common Crawl|✅|
|`otx`|AlienVault OTX|✅|
|`urlscan`|URLScan.io|✅|

<a id="select-providers"></a>
### اختيار المزودين

> **تصحيح:** يحدد `--providers` المزودين المستخدمين، بينما يستبعد `--blacklist` امتدادات ملفات لا مزودي بيانات. لذلك لا يستبعد مثال `--blacklist commoncrawl` مزود Common Crawl كما يدعي الأصل. [مرجع الخيارات الرسمي](https://github.com/lc/gau/blob/master/README.md).

```bash
# استخدام مزودين محددين فقط
gau --providers wayback example.com
gau --providers wayback,otx example.com

# استبعاد مزود بحسب وصف الأصل؛ راجع تصحيح ‎--blacklist
gau --blacklist commoncrawl example.com
```

<a id="provider-configuration"></a>
### إعداد المزودين

```bash
# موقع ملف الإعداد
~/.gau.toml

# مثال لملف الإعداد
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

<a id="-filtering"></a>
## 🔍 الترشيح

<a id="filter-by-status-code"></a>
### الترشيح بحسب رمز الحالة

```bash
# عناوين URL المسجلة برمز الحالة 200 فقط
gau --mc 200 example.com

# رموز حالة متعددة
gau --mc 200,301,302 example.com

# استبعاد رموز حالة محددة
gau --fc 404,500 example.com
```

<a id="filter-by-extension"></a>
### الترشيح بحسب امتداد الملف وفق وصف الأصل

> **تصحيح:** يقبل `--mt` أنواع MIME لمطابقتها، و`--ft` أنواع MIME لاستبعادها؛ لا توثقهما الأداة كمرشحي Regex لامتدادات الملفات. أُبقيت الأمثلة غير الدقيقة أدناه دون تغيير، ويعرض قسم MIME استخدامهما الصحيح. [توثيق GAU](https://github.com/lc/gau/blob/master/README.md).

```bash
# مطابقة امتدادات الملفات بحسب الأصل؛ راجع تصحيح ‎--mt
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# استبعاد امتدادات الملفات بحسب الأصل؛ راجع تصحيح ‎--ft
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

<a id="filter-by-mime-type"></a>
### الترشيح بحسب نوع المحتوى (MIME Type)

```bash
# الترشيح بحسب نوع المحتوى
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

<a id="output-format"></a>
### تنسيق المخرجات

```bash
# مخرجات JSON
gau --json example.com

# الوضع التفصيلي
gau --verbose example.com
```

---

<a id="-integration"></a>
## 🔗 التكامل مع الأدوات

<a id="with-httpx"></a>
### التكامل مع httpx

```bash
# التحقق من عناوين URL المستجيبة
gau example.com | httpx -silent

# مع رموز الحالة
gau example.com | httpx -sc -title
```

<a id="with-grepparameter-extraction"></a>
### التكامل مع grep واستخراج المعلمات

```bash
# العثور على عناوين URL تتضمن معلمات
gau example.com | grep "="

# العثور على أنماط محددة
gau example.com | grep -E "\.php\?|\.asp\?"

# أنماط أداة GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

<a id="with-urldedupe"></a>
### التكامل مع urldedupe

```bash
# إزالة التكرار
gau example.com | urldedupe

# بديل باستخدام sort -u
gau example.com | sort -u
```

<a id="with-uro"></a>
### التكامل مع uro

```bash
# تقليل تكرار عناوين URL بطريقة تراعي بنيتها
gau example.com | uro
```

<a id="full-pipeline"></a>
### مسار عمل متكامل

```bash
# مسار متكامل لجمع عناوين URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="basic-commands"></a>
### الأوامر الأساسية

|الأمر|الوصف|
|---------|-------------|
|`gau domain.com`|جمع عناوين URL للنطاق|
|`gau --subs domain.com`|تضمين النطاقات الفرعية|
|`gau -o urls.txt domain.com`|حفظ المخرجات في ملف|
|`gau --threads 5 domain.com`|تنفيذ بعدة عمال متزامنين|

<a id="filtering-options"></a>
### خيارات الترشيح

|الخيار|الوصف|
|------|-------------|
|`--mc 200`|مطابقة رمز الحالة|
|`--fc 404`|استبعاد رمز الحالة|
|`--mt "regex"`|مطابقة تعبير نمطي بحسب الأصل؛ راجع التوضيح|
|`--ft "regex"`|استبعاد تعبير نمطي بحسب الأصل؛ راجع التوضيح|
|`--json`|مخرجات JSON|

<a id="provider-options"></a>
### خيارات المزودين

|الخيار|الوصف|
|------|-------------|
|`--providers wayback,otx`|استخدام مزودين محددين|
|`--blacklist commoncrawl`|استبعاد مزود بحسب الأصل؛ راجع التوضيح|

<a id="useful-one-liners"></a>
### أوامر مفيدة في سطر واحد

```bash
# العثور على عناوين ملفات JS
gau example.com | grep -E "\.js$" | sort -u

# العثور على نقاط نهاية API
gau example.com | grep -iE "/api/|/v[0-9]/"

# العثور على المعلمات
gau example.com | grep "?" | grep "=" | sort -u

# العثور على عناوين مرشحة لاختبار حقن SQL
gau example.com | gf sqli

# العثور على عناوين مرشحة لاختبار XSS
gau example.com | gf xss

# عناوين URL مستجيبة تتضمن معلمات
gau example.com | grep "=" | httpx -silent
```

---

<a id="-bug-bounty-workflow"></a>
## 🎯 تسلسل العمل في مكافآت الثغرات

> **توضيح لحدود المثال:** تصنف أوامر `gf` عناوين مرشحة بحسب الأنماط، ولا تثبت الثغرات وحدها. كذلك فإن إدراج قيمة XSS ثم تلقي HTTP 200 لا يثبت تنفيذ JavaScript في المتصفح. أسطر المثال الأصلية محفوظة دون تشغيل. [وصف GF الرسمي](https://github.com/tomnomnom/gf)، [دليل OWASP للتحقق من XSS](https://wstg.owasp.org/v4.2/4-Web_Application_Security_Testing/07-Input_Validation_Testing/01-Testing_for_Reflected_Cross_Site_Scripting/).

```bash
# 1. جمع جميع عناوين URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. اختيار عناوين URL اللافتة للاهتمام
cat all_urls.txt | grep "=" | uro > params.txt

# 3. التحقق من الاستجابة
cat params.txt | httpx -silent > live_params.txt

# 4. العثور على مدخلات مرشحة لاختبار الثغرات بحسب الأصل
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. الاختبار بحسب الأصل؛ راجع توضيح التحقق
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

<a id="-resources"></a>
## 📚 الموارد

- [مستودع GAU على GitHub](https://github.com/lc/gau)
- [أنماط أداة GF](https://github.com/1ndianl33t/Gf-Patterns)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [منهجية مكافآت الثغرات](../../Bug-Bounty-Methodology/translations/README.ar.md)
- [Subfinder](../../Subfinder/translations/README.ar.md)
- [httpx](../../httpx/translations/README.ar.md)

---

<p align="center">
  <b>🔗 اجمع عناوين URL المعروفة!</b><br>
  <i>GAU — اكتشاف عناوين URL بطريقة سلبية</i>
</p>
