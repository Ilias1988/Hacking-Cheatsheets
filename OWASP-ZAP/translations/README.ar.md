<a id="-owasp-zap---complete-cheatsheet"></a>
# ⚡ OWASP ZAP — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy - Free Security Testing Tool
```

يعرف الشعار ZAP باسم Zed Attack Proxy، وهي أداة مجانية لاختبار الأمن.

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 من أشهر ماسحات أمن الويب المجانية</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [نظرة عامة على الواجهة](#-interface-overview)
- [إعداد الوكيل (Proxy)](#-proxy-configuration)
- [الاختبار اليدوي](#-manual-testing)
- [استكشاف الصفحات والزحف (Spider وCrawler)](#-spider--crawler)
- [الفحص النشط (Active Scanning)](#-active-scanning)
- [الفحص السلبي (Passive Scanning)](#-passive-scanning)
- [السياقات والجلسات (Contexts وSessions)](#-contexts--sessions)
- [أداة الاختبار بمدخلات متنوعة (Fuzzer)](#-fuzzer)
- [واجهة API والأتمتة](#-api--automation)
- [سطر الأوامر](#-command-line)
- [الإضافات (Add-ons)](#-add-ons)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

> **توضيح تاريخي:** الاسم OWASP ZAP موروث من الأصل؛ لم يعد ZAP مشروعًا تابعًا لـ OWASP منذ عام 2023. [توضيح المشروع الرسمي](https://www.zaproxy.org/docs/nowaspzap/).

**OWASP ZAP** ‏(Zed Attack Proxy) أداة مجانية ومفتوحة المصدر لاختبار الاختراق، صممت لاكتشاف الثغرات الأمنية في تطبيقات الويب. ينسبها الأصل إلى OWASP؛ انظر توضيح التسمية أدناه.

<a id="why-zap"></a>
### لماذا ZAP؟

|الميزة|الوصف|
|---------|-------------|
|**مجاني ومفتوح المصدر**|دون رسوم ترخيص ويطوره المجتمع|
|**سهل الاستخدام**|مناسب للمبتدئين مع ميزات قوية|
|**فحص آلي**|زحف وفحص نشط وسلبي|
|**اختبار يدوي**|وكيل اعتراض وأداة Fuzzer|
|**دعم API**|واجهة REST API للأتمتة|
|**متعدد المنصات**|Windows, Linux, macOS, Docker|
|**قابل للتوسعة**|متجر يتضمن أكثر من 100 إضافة بحسب الأصل|

<a id="zap-vs-burp-suite"></a>
### مقارنة ZAP مع Burp Suite

> **ملاحظة:** يحفظ الجدول مقارنة الأصل؛ السعر `$449+/year` ومزايا الإصدارات ليست معلومات آنية. راجع [صفحة PortSwigger الرسمية](https://portswigger.net/burp/pro) للأسعار والميزات الحالية.

|الميزة|ZAP|Burp Suite|
|---------|-----|------------|
|**السعر**|مجاني|$449+ سنويًا بحسب الأصل|
|**مفتوح المصدر**|✅ نعم|❌ لا|
|**فحص آلي**|✅ كامل|الإصدار Pro فقط بحسب الأصل|
|**الوصول إلى API**|✅ مجاني|الإصدار Pro فقط بحسب الأصل|
|**تطوير مستمر**|✅ نعم|✅ نعم|
|**المجتمع**|OWASP|PortSwigger|

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="windows"></a>
### Windows
```bash
# التنزيل من الموقع الرسمي
https://www.zaproxy.org/download/

# أو باستخدام Chocolatey
choco install owasp-zap
```

<a id="linux-kaliubuntu"></a>
### نظام Linux ‏(Kali/Ubuntu)
```bash
# نظام Kali حيث تكون الأداة مثبتة مسبقًا
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

<a id="macos"></a>
### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# تنزيل ملف DMG من الموقع الرسمي
```

<a id="docker"></a>
### Docker
```bash
# جلب صورة الحاوية ذات الوسم المحدد
docker pull ghcr.io/zaproxy/zaproxy:stable

# تشغيل ZAP في وضع الخدمة (Daemon)
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# التشغيل بواجهة رسومية في Linux
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
# تشغيل ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-interface-overview"></a>
## 🖥️ نظرة عامة على الواجهة

<a id="main-panels"></a>
### لوحات الواجهة الرئيسية

|اللوحة|الوصف|
|-------|-------------|
|**Sites Tree**|عرض هرمي للمواقع التي جرت زيارتها|
|**History**|جميع الطلبات والاستجابات|
|**Alerts**|الثغرات المكتشفة|
|**Active Scan**|تقدم الفحص الحالي|
|**Spider**|عناوين URL التي زارها الزاحف|
|**Search**|البحث في جميع البيانات|
|**Request/Response**|عرض التفاصيل|

<a id="toolbar-icons"></a>
### أيقونات شريط الأدوات

|الأيقونة|الوظيفة|
|------|----------|
|🌐|فحص Quick Start|
|🔍|Spider|
|⚡|Active Scan|
|🛑|اعتراض باستخدام Break|
|📊|التقارير|
|⚙️|الخيارات|

<a id="modes"></a>
### الأوضاع

|الوضع|الوصف|
|------|-------------|
|**Safe**|منع العمليات الخطرة|
|**Protected**|حصر الفحص في الأهداف ضمن النطاق|
|**Standard**|الفحص الكامل، وهو الوضع الافتراضي بحسب الأصل|
|**ATTACK**|فحص مكثف|

---

<a id="-proxy-configuration"></a>
## 🔌 إعداد الوكيل (Proxy)

<a id="browser-configuration"></a>
### إعداد المتصفح

**وكيل ZAP الافتراضي:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

<a id="firefox-setup"></a>
### إعداد Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. حدد "Also use this proxy for HTTPS"
4. أو استخدم إضافة FoxyProxy
```

<a id="import-zap-certificate"></a>
### استيراد شهادة ZAP
```
1. في المتصفح انتقل إلى: http://zap
2. انقر "Download ZAP Root CA Certificate"
3. استورد الشهادة إلى المتصفح:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

<a id="configure-zap-proxy-options"></a>
### إعداد خيارات وكيل ZAP

> **توافق الواجهة:** مسار `Local Proxies` في الأصل قديم؛ يوثق ZAP حاليًا `Options → Network → Local Servers/Proxies`. قد تختلف أسماء القوائم باختلاف الإصدار والإضافات. [دليل إعداد الوكيل](https://www.zaproxy.org/docs/desktop/start/proxies/).
```
Tools → Options → Local Proxies

الإعدادات:
- Address: localhost
- Port: 8080
- Behind NAT: فعّل عند الحاجة
- Remove unsupported encodings: Enable — تفعيل إزالة الترميزات غير المدعومة
```

<a id="upstream-proxy-proxy-chain"></a>
### الوكيل الأعلى ضمن سلسلة الوكلاء (Proxy Chain)
```
Tools → Options → Network → Connection

استخدم سلسلة الوكلاء (Use proxy chain):
- Address: corporate-proxy.com
- Port: 8080
- المصادقة إذا كانت مطلوبة
```

---

<a id="-manual-testing"></a>
## 🔧 الاختبار اليدوي

<a id="intercepting-requests-break"></a>
### اعتراض الطلبات باستخدام Break

```
1. انقر زر "Break" الأخضر (🛑) في شريط الأدوات
2. تصفح التطبيق
3. عدّل الطلب المعترض
4. انقر "Submit and Continue" أو "Drop"

خيارات Break:
- اعتراض جميع الطلبات
- اعتراض جميع الاستجابات
- اعتراض نمط URL محدد
```

<a id="request-editor"></a>
### محرر الطلبات (Request Editor)

```
انقر الطلب بزر الفأرة الأيمن → Open/Resend with Request Editor

الميزات:
- تعديل أي جزء من الطلب
- إضافة الترويسات أو حذفها
- تغيير أسلوب HTTP
- الإرسال وعرض الاستجابة
```

<a id="manual-request"></a>
### طلب يدوي
```
Tools → Manual Request Editor

أو: Ctrl+M

إنشاء طلبات مخصصة من البداية
```

<a id="encodedecodehash"></a>
### الترميز وفك الترميز والتجزئة (Encode/Decode/Hash)
```
Tools → Encode/Decode/Hash

يدعم:
- ترميز URL وفك ترميزه
- ترميز Base64 وفك ترميزه
- ترميز HTML وفك ترميزه
- الترميز الست عشري وفك ترميزه
- التجزئة باستخدام MD5 وSHA1 وSHA256
```

<a id="search-functionality"></a>
### وظيفة البحث
```
في تبويب Search أدخل النمط المطلوب

البحث ضمن:
- URLs
- الطلبات
- الاستجابات
- الترويسات
- الجميع
```

---

<a id="-spider--crawler"></a>
## 🕷️ استكشاف الصفحات والزحف (Spider وCrawler)

<a id="standard-spider"></a>
### الزاحف التقليدي Standard Spider

```
انقر الهدف بزر الفأرة الأيمن → Spider → Spider...

أو: Ctrl+Alt+S

الخيارات:
- أقصى عمق للزحف
- أقصى عدد من العناصر التابعة
- أقصى مدة
- معالجة المعلمات
```

<a id="spider-options"></a>
### خيارات Spider
```
Tools → Options → Spider

الإعدادات:
- Max Depth: 5-10 (القيمة الافتراضية: 5)
- Max Children: 10
- Max Duration: 0 (دون حد)
- Thread Count: 5 — عدد مسارات التنفيذ
- Handle forms: Yes — معالجة النماذج
- Post forms: Yes — إرسال النماذج
```

<a id="ajax-spider"></a>
### الزاحف AJAX Spider

```
للتطبيقات التي تعتمد بكثافة على JavaScript:

انقر الهدف بزر الفأرة الأيمن → AJAX Spider → AJAX Spider...

يتطلب متصفحًا:
- Firefox، موصى به
- Chrome
- HtmlUnit دون واجهة رسومية (Headless)
```

<a id="ajax-spider-options"></a>
### خيارات AJAX Spider
```
Tools → Options → AJAX Spider

الإعدادات:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (دون حد)
- Max Duration: 60 دقيقة
- Click default elements — النقر على العناصر الافتراضية
- Click elements with: onclick, etc. — النقر على عناصر لها معالجات أحداث مثل onclick
```

---

<a id="-active-scanning"></a>
## ⚡ الفحص النشط (Active Scanning)

<a id="start-active-scan"></a>
### بدء الفحص النشط

```
انقر الهدف بزر الفأرة الأيمن → Active Scan...

أو: Ctrl+Alt+A

الإعداد:
- Starting Point — نقطة البداية
- Context، اختياري
- User، للمصادقة
- Policy
- Technology
```

<a id="scan-policies"></a>
### سياسات الفحص

```
Analyze → Scan Policy Manager

إنشاء السياسات أو تعديلها:
- تحديد الاختبارات المطلوب تشغيلها
- Threshold: عتبة اعتبار السلوك مثيرًا للاشتباه
- Strength: شدة الاختبار
```

<a id="policy-categories"></a>
### فئات السياسات

|الفئة|الاختبارات|
|----------|-------|
|**Injection**|حقن SQL وأوامر نظام التشغيل وLDAP|
|**Client Browser**|ثغرات XSS وDOM XSS|
|**Server Security**|تضمين الملفات عن بُعد|
|**Information**|اجتياز المسارات وكشف المعلومات|
|**Miscellaneous**|إعادة توجيه يتحكم فيها المستخدم|

<a id="scan-strength"></a>
### شدة الفحص

|المستوى|الوصف|
|-------|-------------|
|**Low**|طلبات قليلة وفحص سريع|
|**Medium**|متوازن|
|**High**|طلبات كثيرة وفحص مفصل|
|**Insane**|أقصى عدد من الاختبارات مع بطء أكبر|

<a id="technology-selection"></a>
### اختيار التقنيات
```
انقر الهدف بزر الفأرة الأيمن → Technology → Select...

تصفية الفحوص بحسب التقنية:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-passive-scanning"></a>
## 👁️ الفحص السلبي (Passive Scanning)

<a id="what-is-passive-scanning"></a>
### ما هو الفحص السلبي؟

يحلل حركة المرور دون إرسال طلبات إضافية من أجل قواعد الفحص السلبي نفسها.

```
تلقائي: يحلل حركة المرور عبر الوكيل أثناء تفعيله

يكتشف:
- غياب ترويسات الأمان
- كشف المعلومات
- مشكلات ملفات تعريف الارتباط
- ضعف SSL/TLS بحسب وصف الأصل
- كشف عناوين IP الخاصة
```

<a id="passive-scan-rules"></a>
### قواعد الفحص السلبي

```
Analyze → Scan Policy Manager → Passive Scan

تتضمن القواعد التالية بأسمائها في الواجهة:
- Application Error Disclosure — كشف أخطاء التطبيق
- Cookie Without Secure Flag — ملف تعريف ارتباط دون السمة Secure
- Cross-Domain JavaScript — JavaScript من نطاق آخر
- Information Disclosure — كشف المعلومات
- Private IP Disclosure — كشف عنوان IP خاص
- X-Frame-Options Header Missing — غياب ترويسة X-Frame-Options
```

<a id="configure-passive-scanning"></a>
### إعداد الفحص السلبي
```
Tools → Options → Passive Scan

الإعدادات:
- Scan only in scope: موصى به
- Max alerts per rule: 10
- Tags: Enable
```

---

<a id="-contexts--sessions"></a>
## 🎯 السياقات والجلسات (Contexts وSessions)

<a id="what-are-contexts"></a>
### ما هي السياقات؟

مجموعات منطقية لعناوين URL المترابطة، تساعد على تنظيم الاختبار.

<a id="create-context"></a>
### إنشاء سياق (Context)
```
انقر Sites بزر الفأرة الأيمن → Include in Context → New Context

أو: File → New Context
```

<a id="context-settings"></a>
### إعدادات السياق

|الإعداد|الغرض|
|---------|---------|
|**Include in Context**|عناوين URL المطلوب تضمينها|
|**Exclude from Context**|عناوين URL المطلوب استبعادها|
|**Technology**|تقنيات الخادم|
|**Authentication**|طريقة تسجيل الدخول|
|**Session Management**|التعامل مع ملفات تعريف الارتباط ورموز الوصول|
|**Users**|حسابات مستخدمي الاختبار|

<a id="authentication-types"></a>
### أنواع المصادقة

|النوع|الاستخدام|
|------|----------|
|**Form-based**|نماذج تسجيل الدخول|
|**HTTP Basic**|المصادقة الأساسية|
|**JSON-based**|مصادقة API|
|**Script-based**|مصادقة مخصصة|

<a id="form-based-authentication"></a>
### المصادقة المعتمدة على النماذج
```
1. سجل طلب تسجيل الدخول في ZAP
2. انقر بزر الفأرة الأيمن → Include in Context
3. Context → Authentication → Form-based
4. حدد Login URL
5. حدد Login Request POST Data
6. اضبط معلمات اسم المستخدم وكلمة المرور
7. حدد مؤشرات حالة تسجيل الدخول والخروج
```

<a id="session-management"></a>
### إدارة الجلسات
```
Context → Session Management

الأنواع:
- Cookie-based، الأكثر شيوعًا
- HTTP Auth
- Script-based
```

<a id="users"></a>
### المستخدمون
```
Context → Users → Add

أضف مستخدمي الاختبار:
- Name: admin
- Credentials: admin/password123
```

---

<a id="-fuzzer"></a>
## 🎲 أداة الاختبار بمدخلات متنوعة (Fuzzer)

<a id="start-fuzzer"></a>
### بدء Fuzzer
```
1. حدد الطلب في History
2. انقر بزر الفأرة الأيمن → Attack → Fuzz...
3. حدد موضع إدخال قيم Fuzzing
4. أضف المدخلات (Payloads)
5. ابدأ
```

<a id="add-fuzz-location"></a>
### إضافة موضع للاختبار بمدخلات متنوعة
```
1. في نافذة Fuzzer حدد النص المراد اختباره
2. انقر "Add..."
3. حدد نوع المدخلات
```

<a id="payload-types"></a>
### أنواع المدخلات (Payloads)

|النوع|الوصف|
|------|-------------|
|**Strings**|قائمة سلاسل نصية مخصصة|
|**File**|التحميل من ملف|
|**File Fuzzers**|قوائم كلمات مدمجة|
|**Numbers**|تسلسلات رقمية|
|**Regex**|توليد المدخلات من تعبير نمطي Regex|
|**Script**|برنامج نصي مخصص|

<a id="built-in-fuzzers"></a>
### مصادر مدخلات Fuzzing المدمجة
```
Add → File Fuzzers

الفئات:
- jbrofuzz لاختبارات XSS وSQL وغيرها
- dirbuster لأسماء المجلدات
- fuzzdb، مجموعة شاملة
```

<a id="fuzzer-options"></a>
### خيارات Fuzzer
```
الخيارات:
- عدد مسارات التنفيذ المتزامنة
- التأخير بين الطلبات
- اتباع إعادة التوجيه
- عرض جميع النتائج أو تصفيتها
```

<a id="fuzzer-example-parameter-fuzzing"></a>
### مثال Fuzzer: اختبار معلمة بمدخلات متنوعة
```
1. التقط: GET /page?id=1
2. انقر بزر الفأرة الأيمن → Fuzz
3. حدد "1" 
4. أضف مدخلات حقن SQL
5. شغل Fuzzer
6. حلل النتائج بحسب طول الاستجابة ورمز حالتها
```

---

<a id="-api--automation"></a>
## 🤖 واجهة API والأتمتة

<a id="enable-api"></a>
### تفعيل واجهة API
```
Tools → Options → API

الإعدادات:
- Enabled: Yes
- API Key: (مولد تلقائيًا أو مخصص)
- Permitted addresses: localhost — العناوين المسموح لها
```

<a id="api-access"></a>
### الوصول إلى API
```bash
# المسار الجذري لـ API
http://localhost:8080/JSON/

# باستخدام مفتاح API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# عرض الإصدار
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

<a id="common-api-endpoints"></a>
### نقاط نهاية API الشائعة

|نقطة النهاية|الغرض|
|----------|---------|
|`/core/view/alerts/`|الحصول على جميع التنبيهات|
|`/core/view/hosts/`|الحصول على جميع المضيفين|
|`/spider/action/scan/`|بدء Spider|
|`/ascan/action/scan/`|بدء الفحص النشط|
|`/core/view/urls/`|الحصول على جميع عناوين URL|
|`/core/action/shutdown/`|إيقاف ZAP|

<a id="api-examples"></a>
### أمثلة API

```bash
# استكشاف عنوان URL باستخدام Spider
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# فحص حالة Spider
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# بدء الفحص النشط
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# الحصول على التنبيهات
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# إنشاء تقرير HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

<a id="python-api-client"></a>
### عميل API بلغة Python

> **توضيح للمثال:** يعرض الكود استدعاءات بدء الزحف والفحص ثم جلب التنبيهات، دون انتظار اكتمالهما؛ لذلك قد تكون التنبيهات المسترجعة مبكرة وغير نهائية. أُبقي المثال كما في الأصل.
```python
from zapv2 import ZAPv2

# الاتصال بـ ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# الفحص النشط Active Scan
zap.ascan.scan(url='http://target.com')

# الحصول على التنبيهات
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-command-line"></a>
## ⌨️ سطر الأوامر

<a id="basic-command-line-options"></a>
### خيارات سطر الأوامر الأساسية
```bash
# تشغيل ZAP في وضع الخدمة (Daemon)
zap.sh -daemon

# التشغيل على منفذ محدد
zap.sh -daemon -port 8090

# فحص سريع
zap.sh -quickurl http://target.com -quickout report.html

# فحص Baseline باستخدام Docker
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

<a id="automation-framework"></a>
### إطار الأتمتة (Automation Framework)
```bash
# تشغيل فحص الأتمتة
zap.sh -cmd -autorun automation.yaml
```

<a id="automation-yaml-example"></a>
### مثال أتمتة بتنسيق YAML
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

<a id="docker-scans"></a>
### الفحوص باستخدام Docker

> **توضيح:** يشغل Baseline زاحف Spider ويرسل طلبات للاستكشاف ثم ينتظر الفحص السلبي؛ كلمة passive لا تعني انعدام حركة الشبكة. كذلك تتطلب ملفات التقارير مشاركة مجلد مع `/zap/wrk/` لحفظها على المضيف؛ أمثلة الأصل هنا لا تضيف هذا الربط. [توثيق Baseline الرسمي](https://www.zaproxy.org/docs/docker/baseline-scan/).

```bash
# فحص Baseline بقواعد فحص سلبية؛ راجع التوضيح
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# فحص كامل نشط
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# فحص API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

<a id="-add-ons"></a>
## 🧩 الإضافات (Add-ons)

<a id="install-add-ons"></a>
### تثبيت الإضافات
```
Help → Check for Updates → Marketplace

أو: Tools → Add-ons → Marketplace
```

<a id="essential-add-ons"></a>
### إضافات أساسية

|الإضافة|الوصف|
|--------|-------------|
|**Active Scan Rules**|قواعد فحص إضافية|
|**Passive Scan Rules**|قواعد فحص سلبي إضافية|
|**FuzzDB**|مدخلات لاختبارات Fuzzing|
|**Retire.js**|اكتشاف مكتبات JavaScript ذات الثغرات|
|**AJAX Spider**|زاحف يدعم JavaScript|
|**Selenium**|أتمتة المتصفح|
|**Wappalyzer**|اكتشاف التقنيات|
|**Access Control**|اختبار التحكم بالوصول|
|**Directory List**|قوائم كلمات لأسماء المجلدات|

<a id="add-on-management"></a>
### إدارة الإضافات
```
# عرض الإضافات المثبتة
Tools → Add-ons → Installed

# تحديث الجميع
Help → Check for Updates → Update All
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-quick-web-scan"></a>
### المثال 1: فحص ويب سريع
```
1. أدخل URL في تبويب Quick Start
2. انقر "Attack"
3. سيجري ZAP الزحف والفحص تلقائيًا
4. راجع التنبيهات بعد الاكتمال
```

<a id="example-2-manual--automated-testing"></a>
### المثال 2: اختبار يدوي وآلي
```
1. اضبط وكيل المتصفح على ZAP
2. تصفح التطبيق يدويًا واستكشف جميع الميزات
3. انقر الهدف بزر الفأرة الأيمن في Sites → Spider
4. انتظر اكتمال Spider
5. انقر الهدف بزر الفأرة الأيمن → Active Scan
6. راجع التنبيهات
7. أنشئ تقريرًا
```

<a id="example-3-authenticated-scan"></a>
### المثال 3: فحص مع المصادقة
```
1. أنشئ Context للهدف
2. اضبط Form-based Authentication
3. أضف بيانات اعتماد مستخدم الاختبار
4. اضبط وضع المستخدم الإجباري (Forced User Mode)
5. شغل Spider بهوية مستخدم مسجل الدخول
6. شغل Active Scan
```

<a id="example-4-api-testing"></a>
### المثال 4: اختبار API
```
1. استورد تعريف API بتنسيق OpenAPI/Swagger
2. File → Import → Import OpenAPI definition
3. استكشف API باستخدام Spider
4. شغل Active Scan
```

<a id="example-5-cicd-integration"></a>
### المثال 5: التكامل مع CI/CD
```bash
# مسار عمل Jenkins
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-actions"></a>
### الإجراءات الأساسية

|المهمة|الطريقة|
|------|--------|
|بدء الوكيل|يبدأ وكيل ZAP تلقائيًا على المنفذ 8080 بحسب الإعداد|
|Spider|Right-click → Spider|
|Active Scan|Right-click → Active Scan|
|اعتراض|النقر على زر Break ‏(🛑)|
|Fuzz|Right-click request → Fuzz|
|إنشاء تقرير|Report → Generate Report|

<a id="keyboard-shortcuts"></a>
### اختصارات لوحة المفاتيح

|الاختصار|الإجراء|
|----------|--------|
|`Ctrl+Alt+S`|نافذة Spider|
|`Ctrl+Alt+A`|نافذة Active Scan|
|`Ctrl+M`|Manual Request|
|`Ctrl+B`|تبديل حالة Break|
|`Ctrl+F`|بحث|
|`Ctrl+R`|إعادة إرسال الطلب|

<a id="scan-modes"></a>
### أوضاع الفحص

|الوضع|متى يستخدم|
|------|----------|
|**Safe**|عند الرغبة في تجنب العمليات التي تغير الهدف|
|**Protected**|عند اختبار نطاق محدد فقط|
|**Standard**|للاختبار المعتاد|
|**ATTACK**|للاختبار المكثف بتفويض صريح|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح للأداء

1. **تحديد النطاق**
   ```
   حدد سياقًا وافحص العناصر الواقعة ضمن النطاق فقط
   ```

2. **استخدام AJAX Spider لتطبيقات الصفحة الواحدة SPAs**
   ```
   قد تحتاج التطبيقات الحديثة إلى AJAX Spider بالإضافة إلى الزاحف التقليدي
   ```

3. **إعداد التقنيات**
   ```
   حدد التقنيات ذات الصلة فقط لتقليل مدة الفحص
   ```

<a id="accuracy-tips"></a>
### نصائح للدقة

1. **إعداد المصادقة أولًا**
   ```
   اضبط المصادقة لتغطية أعمق
   ```

2. **الاستكشاف اليدوي قبل الأتمتة**
   ```
   تصفح التطبيق يدويًا أولًا لاكتشاف جميع الميزات
   ```

3. **مراجعة جميع التنبيهات**
   ```
   راجع الإنذارات الكاذبة وتحقق من النتائج الصحيحة
   ```

<a id="workflow"></a>
### تسلسل العمل

```
1. اضبط الوكيل
2. استكشاف يدوي مع المصادقة
3. زحف تقليدي وباستخدام AJAX
4. مراجعة نتائج الفحص السلبي
5. فحص نشط
6. اختبار يدوي يشمل Fuzzing والحالات الحدية
7. أنشئ تقريرًا
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** ZAP أداة لاختبار الأمن، وينبغي استخدامها فقط في **اختبارات مصرح بها**.
> 
> - ✅ اختبر التطبيقات التي تملكها
> - ✅ اختبر بتفويض مكتوب وصريح
> - ✅ استخدم بيئات الاختبار أو التجهيز (Staging)
> - ❌ لا تفحص بيئات الإنتاج دون تفويض
> - ❌ لا تفحص أنظمة لا تملكها بحسب تنبيه الأصل
> 
> **بحسب تنبيه الأصل، قد يخالف الاختبار الأمني دون تفويض القانون ويؤدي إلى اتهامات جنائية.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [موقع ZAP الرسمي](https://www.zaproxy.org/)
- [توثيق ZAP](https://www.zaproxy.org/docs/)
- [مستودع ZAP على GitHub](https://github.com/zaproxy/zaproxy)
- [توثيق API الخاصة بـ ZAP](https://www.zaproxy.org/docs/api/)

<a id="learning-resources"></a>
### مصادر التعلم
- [دليل OWASP للاختبار](https://owasp.org/www-project-web-security-testing-guide/)
- [سلسلة ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [فيديوهات متعمقة عن ZAP](https://www.zaproxy.org/docs/videos/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [أداة Burp Suite](../../Burp-Suite/translations/README.ar.md)
- [Nikto](../../Nikto/translations/README.ar.md)
- [SQLMap](../../SQLMap/translations/README.ar.md)

---

<p align="center">
  <b>⚡ أتقن اختبار أمن تطبيقات الويب!</b><br>
  <i>مجاني وقوي ويطوره المجتمع</i>
</p>
