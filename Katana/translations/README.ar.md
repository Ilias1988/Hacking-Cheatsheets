<a id="-katana---next-gen-web-crawler"></a>
# ⚔️ Katana — زاحف ويب من الجيل الجديد

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Next-Generation Web Crawling Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هو Katana؟](#-what-is-katana)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [خيارات الزحف](#-crawling-options)
- [وضع المتصفح دون واجهة (Headless)](#-headless-mode)
- [المخرجات والتصفية](#-output--filtering)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-katana"></a>
## 🎯 ما هو Katana؟

**Katana** إطار عمل من الجيل الجديد للزحف إلى مواقع الويب، تطوره ProjectDiscovery:

- ⚡ **سريع** - مبني بلغة Go ويدعم تزامناً واسعاً
- 🔍 **عرض الصفحات التي تعتمد على JavaScript** - دعم متصفح دون واجهة
- 📡 **الويب الحديث** - يتعامل مع تطبيقات الصفحة الواحدة (SPA) والمحتوى الديناميكي
- 🎯 **موجّه لبرامج مكافآت الثغرات** - مصمم للاختبار الأمني

<a id="why-katana"></a>
### لماذا Katana؟

| الميزة | الفائدة |
|---------|---------|
| متصفح Chrome دون واجهة | الزحف إلى تطبيقات تعتمد بكثافة على JavaScript |
| ترويسات مخصصة | الزحف مع المصادقة |
| التحكم في النطاق | حصر الزحف ضمن النطاق |
| ملء النماذج | اكتشاف نقاط نهاية إضافية |

---

<a id="-installation"></a>
## 🚀 التثبيت

```bash
# التثبيت باستخدام Go، موصى به في الأصل
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# التحقق
katana -version
```

---

<a id="-basic-usage"></a>
## 📖 الاستخدام الأساسي

<a id="single-target"></a>
### هدف واحد

```bash
# زحف أساسي
katana -u https://example.com

# حفظ المخرجات
katana -u https://example.com -o urls.txt

# الوضع الصامت
katana -u https://example.com -silent
```

<a id="multiple-targets"></a>
### أهداف متعددة

```bash
# من الإدخال القياسي
echo "https://example.com" | katana

# عدة عناوين URL
cat targets.txt | katana

# من ملف
katana -list targets.txt
```

<a id="crawl-depth"></a>
### عمق الزحف

```bash
# ضبط العمق، الافتراضي 3
katana -u https://example.com -d 5

# عمق 1، صفحة الهدف فقط وفق تعليق الأصل
katana -u https://example.com -d 1
```

---

<a id="-crawling-options"></a>
## 🕷️ خيارات الزحف

<a id="scope-control"></a>
### التحكم في النطاق

```bash
# الزحف ضمن النطاق فقط، النطاق نفسه وفق الأصل
katana -u https://example.com -fs dn

# خيارات النطاق:
# dn - اسم النطاق
# rdn - النطاق الجذري
# fqdn - اسم النطاق المؤهل بالكامل

# النطاق المحدد بحقل
katana -u https://example.com -fs rdn
```

<a id="crawling-strategy"></a>
### استراتيجية الزحف

> **تصحيح للخيارات:** توثق [مساعدة Katana الرسمية](https://docs.projectdiscovery.io/tools/katana/usage) الاستراتيجية عبر `-s` أو `-strategy`. أما `-cs` فهو `-crawl-scope` لتعبير نطاق الزحف؛ لذلك لا تعبر أوامر الأصل هنا عن اختيار الاستراتيجية كما يزعم شرحها. حُفظت الأوامر والجدول دون تغيير.


```bash
# العمق أولاً، الافتراضي
katana -u https://example.com -cs depth-first

# العرض أولاً
katana -u https://example.com -cs breadth-first
```

<a id="concurrency"></a>
### التزامن

```bash
# الطلبات المتزامنة، الافتراضي 10
katana -u https://example.com -c 50

# تحديد المعدل بالطلبات في الثانية
katana -u https://example.com -rl 100

# معالجة الأهداف بالتوازي
katana -u https://example.com -p 10
```

<a id="crawl-duration"></a>
### مدة الزحف

```bash
# ضبط مهلة الطلب بالثواني
katana -u https://example.com -timeout 30

# أقصى مدة للزحف
katana -u https://example.com -ct 300
```

<a id="automatic-form-filling"></a>
### الملء التلقائي للنماذج

> **ملاحظة توافق:** مثال `-fv` مأخوذ من الأصل. توثق [مساعدة Katana](https://docs.projectdiscovery.io/tools/katana/usage) ملف إعداد النماذج عبر `-form-config`؛ طابق صياغة القيم مع الإصدار المثبّت قبل الاعتماد على المثال.


```bash
# تفعيل ملء النماذج
katana -u https://example.com -aff

# باستخدام قيم مخصصة
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

<a id="-headless-mode"></a>
## 🌐 وضع المتصفح دون واجهة (Headless)

<a id="enable-headless-browser"></a>
### تفعيل المتصفح دون واجهة

```bash
# تفعيل Chrome دون واجهة
katana -u https://example.com -headless

# متصفح دون واجهة مع تحديد العمق
katana -u https://example.com -headless -d 3
```

<a id="headless-options"></a>
### خيارات المتصفح دون واجهة

> **توضيح:** `-system-chrome` يستخدم Chrome المثبّت محلياً؛ لا يحدد مساراً مخصصاً بحد ذاته. توثق [المساعدة الرسمية](https://docs.projectdiscovery.io/tools/katana/usage) خيار مسار منفصلاً. راجع نسخة الأداة أيضاً عند قراءة مثال `-screenshot`.


```bash
# مسار Chrome مخصص وفق تعليق الأصل
katana -u https://example.com -headless -system-chrome

# تعطيل عزل المتصفح لمستخدم root وفق تعليق الأصل
katana -u https://example.com -headless -no-sandbox

# التقاط صور الصفحات
katana -u https://example.com -headless -screenshot
```

<a id="javascript-crawling"></a>
### الزحف إلى محتوى JavaScript

> **تصحيح للمهلة:** `-timeout 5` مهلة انتظار للطلب مقدارها 5 ثوانٍ، وليس انتظار JavaScript بالمللي ثانية كما يقول التعليق. [التوثيق الرسمي](https://docs.projectdiscovery.io/tools/katana/usage).


```bash
# انتظار تحميل JavaScript بالمللي ثانية وفق تعليق الأصل
katana -u https://example.com -headless -timeout 5

# اكتشاف نقاط نهاية JavaScript والزحف إليها
katana -u https://example.com -js-crawl
```

---

<a id="-output--filtering"></a>
## 📤 المخرجات والتصفية

<a id="output-formats"></a>
### صيغ المخرجات

```bash
# المخرجات العادية إلى ملف
katana -u https://example.com -o results.txt

# مخرجات JSON
katana -u https://example.com -jsonl -o results.json

# دون ألوان
katana -u https://example.com -nc
```

<a id="display-fields"></a>
### حقول العرض

> **ملاحظة:** قائمة حقول `-f` والبيانات المتاحة في JSONL قد تختلف؛ لا تفترض أن الحقول `method,status,body,response,header` في تعليق الأصل حقول عرض مدعومة بهذه الصياغة. [المرجع الرسمي](https://docs.projectdiscovery.io/tools/katana/usage).


```bash
# عرض حقول محددة
katana -u https://example.com -f url,method,status

# الحقول المتاحة وفق الأصل:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

<a id="filter-by-extension"></a>
### التصفية بحسب الامتداد

```bash
# مطابقة امتدادات
katana -u https://example.com -em js,php,asp

# استبعاد امتدادات
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

<a id="filter-by-status-code"></a>
### التصفية بحسب رمز الحالة

> **تصحيح:** في [الخيارات الرسمية](https://docs.projectdiscovery.io/tools/katana/usage)، يعني `-fc` ملف إعداد النماذج، وليس تصفية رمز الحالة؛ وتوجد شروط مطابقة وتصفية DSL. لا تُنقل دلالات أعلام أدوات أخرى إلى Katana؛ أمثلة الأصل هنا تحتاج مراجعة الإصدار.


```bash
# مطابقة رمز الحالة
katana -u https://example.com -mc 200,301,302

# استبعاد رمز الحالة
katana -u https://example.com -fc 404,500
```

<a id="custom-headers"></a>
### ترويسات مخصصة

```bash
# إضافة ترويسة
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# عدة ترويسات
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)

```bash
# وكيل HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# للاستخدام مع Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="basic-commands"></a>
### الأوامر الأساسية

| الأمر | الوصف |
|---------|-------------|
| `katana -u URL` | الزحف إلى عنوان URL واحد |
| `katana -list file.txt` | الزحف إلى أهداف من ملف |
| `katana -u URL -d 5` | ضبط العمق إلى 5 |
| `katana -u URL -headless` | تفعيل المتصفح دون واجهة |
| `katana -u URL -o out.txt` | حفظ النتائج |

<a id="scope-options"></a>
### خيارات النطاق

| الخيار | الوصف |
|------|-------------|
| `-fs dn` | النطاق نفسه |
| `-fs rdn` | النطاق الجذري |
| `-fs fqdn` | النطاق المؤهل بالكامل |
| `-cs depth-first` | زحف بالعمق أولاً |
| `-cs breadth-first` | زحف بالعرض أولاً |

<a id="performance-flags"></a>
### خيارات الأداء

| الخيار | الوصف |
|------|-------------|
| `-c 50` | التزامن |
| `-p 10` | معالجة الأهداف بالتوازي |
| `-rl 100` | تحديد معدل الطلبات |
| `-timeout 30` | مهلة الطلب |

<a id="filtering-flags"></a>
### خيارات التصفية

| الخيار | الوصف |
|------|-------------|
| `-em js,php` | مطابقة امتدادات |
| `-ef png,jpg` | استبعاد امتدادات |
| `-mc 200` | مطابقة رموز الحالة |
| `-fc 404` | استبعاد رموز الحالة |

---

<a id="-bug-bounty-workflows"></a>
## 🎯 مسارات عمل لبرامج مكافآت الثغرات

<a id="basic-crawl"></a>
### زحف أساسي

```bash
# زحف سريع
katana -u https://target.com -d 3 -o urls.txt
```

<a id="comprehensive-crawl"></a>
### زحف شامل

```bash
# زحف عميق مع JavaScript
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

<a id="parameter-discovery"></a>
### اكتشاف المعاملات

```bash
# العثور على عناوين URL التي تتضمن معاملات
katana -u https://target.com -d 3 | grep "?" | sort -u

# استخدام grep لمعاملات محددة
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

<a id="combined-with-other-tools"></a>
### الدمج مع أدوات أخرى

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# سلسلة أوامر كاملة
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

<a id="authenticated-crawling"></a>
### الزحف مع المصادقة

```bash
# باستخدام ملف تعريف ارتباط للجلسة
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

<a id="-resources"></a>
## 📚 المراجع

- [مستودع Katana على GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [منهجية برامج مكافآت الثغرات](../../Bug-Bounty-Methodology/translations/README.ar.md)
- [httpx](../../httpx/translations/README.ar.md)
- [Nuclei](../../Nuclei/translations/README.ar.md)
- [GAU](../../GAU/translations/README.ar.md)

---

<p align="center">
  <b>⚔️ أتقن الزحف إلى مواقع الويب!</b><br>
  <i>Katana — زاحف ويب من الجيل الجديد</i>
</p>
