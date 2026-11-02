<a id="-burp-suite---complete-cheatsheet"></a>
# 🔶 Burp Suite — المرجع الشامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Web Application Security Testing Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 منصة اختبار أمن تطبيقات الويب الشائعة في القطاع</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [التثبيت والإعداد](#-installation--setup)
- [إعداد الوكيل (Proxy)](#-proxy-configuration)
- [اعتراض حركة البيانات](#-intercepting-traffic)
- [الهدف ونطاق الاختبار](#-target--scope)
- [Spider/Crawler](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [الإضافات](#-extensions)
- [اختصارات لوحة المفاتيح](#-keyboard-shortcuts)
- [مسار عمل الاختبار](#-testing-workflow)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Burp Suite** منصة متكاملة لاختبار أمن تطبيقات الويب، تطوّرها PortSwigger، وتُستخدم على نطاق واسع في اختبار اختراق الويب.

<a id="editions"></a>
### الإصدارات

| الإصدار | الميزات | السعر |
|---------|----------|-------|
| **Community** | وكيل أساسي وأدوات يدوية | مجاني |
| **Professional** | Scanner وIntruder دون قيود النسخة المجانية وCollaborator | $449/year |
| **Enterprise** | التكامل مع CI/CD وميزات العمل الجماعي | التواصل مع المبيعات |

<a id="core-tools"></a>
### الأدوات الأساسية

| الأداة | الوصف |
|------|-------------|
| **Proxy** | اعتراض حركة HTTP/HTTPS وتعديلها |
| **Spider** | الزحف الآلي إلى تطبيقات الويب |
| **Scanner** | اكتشاف الثغرات آليًا |
| **Intruder** | هجمات آلية قابلة للتخصيص |
| **Repeater** | تعديل الطلبات يدويًا |
| **Decoder** | ترميز البيانات وفك ترميزها |
| **Comparer** | مقارنة الاستجابات |
| **Sequencer** | تحليل عشوائية الرموز |

---

<a id="-installation--setup"></a>
## 📥 التثبيت والإعداد

<a id="download"></a>
### التنزيل

```bash
# التنزيل من الموقع الرسمي
https://portswigger.net/burp/releases

# متوفر للأنظمة التالية:
# - Windows (مثبّت أو إصدار مستقل)
# - macOS (DMG)
# - Linux (برنامج نصي أو JAR)
```

<a id="system-requirements"></a>
### متطلبات النظام

| المتطلب | الحد الأدنى | الموصى به |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| مساحة القرص | 500 MB | 2+ GB |
| Java | JRE 8+ | بيئة JRE المرفقة |
| العرض | 1280x800 | 1920x1080 |

<a id="first-launch-configuration"></a>
### إعداد التشغيل الأول

1. **اختيار نوع المشروع**
   - Temporary project (مشروع مؤقت للاختبار)
   - New project on disk (حفظ العمل على القرص)
   - Open existing project (فتح مشروع موجود)

2. **الإعداد**
   - Use Burp defaults (الإعدادات الافتراضية، موصى بها للمبتدئين)
   - Load from configuration file (تحميل ملف إعداد)

3. **تخصيص الذاكرة**
   ```bash
   # زيادة الذاكرة للمشروعات الكبيرة
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

<a id="-proxy-configuration"></a>
## 🔌 إعداد الوكيل (Proxy)

<a id="browser-configuration"></a>
### إعداد المتصفح

<a id="manual-proxy-setup"></a>
#### إعداد الوكيل يدويًا

| المتصفح | موضع إعدادات الوكيل |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | يستخدم وكيل النظام أو إضافة |
| Safari | System Preferences → Network → Proxies |

**وكيل Burp الافتراضي:**
```
Host: 127.0.0.1
Port: 8080
```

<a id="firefox-configuration"></a>
#### إعداد Firefox
```
1. افتح تفضيلات Firefox
2. ابحث عن "proxy"
3. انقر "Settings..."
4. اختر "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. فعّل "Also use this proxy for HTTPS"
7. انقر OK
```

<a id="foxyproxy-extension-recommended"></a>
#### إضافة FoxyProxy (موصى بها في الأصل)
```
1. ثبّت إضافة FoxyProxy
2. أضف وكيلًا جديدًا:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. فعّل الوكيل أثناء الاختبار
```

<a id="ssltls-certificate-installation"></a>
### تثبيت شهادة SSL/TLS

```bash
# الخطوة 1: بعد تفعيل الوكيل، افتح:
http://burp

# الخطوة 2: انقر "CA Certificate" للتنزيل

# الخطوة 3: استورد الشهادة:
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# على النظام: أضفها إلى شهادات الجذر الموثوقة
```

<a id="proxy-listeners"></a>
### مستمعو الوكيل (Proxy Listeners)

```
Proxy → Options → Proxy Listeners

Default: 127.0.0.1:8080

أضف مستمعين مخصصين:
- منافذ مختلفة لاختبارات مختلفة
- الربط بجميع الواجهات (0.0.0.0) لاختبار الأجهزة المحمولة
- إعادة التوجيه إلى مضيفين مختلفين
```

<a id="invisible-proxy-for-non-proxy-aware-clients"></a>
### الوكيل غير المرئي للعملاء غير المدركين لإعدادات الوكيل

```
1. Proxy → Options → Proxy Listeners
2. عدّل المستمع ثم افتح Request handling
3. فعّل "Support invisible proxying"
4. اضبط ملف hosts أو DNS
```

---

<a id="-intercepting-traffic"></a>
## 🎣 اعتراض حركة البيانات

<a id="intercept-controls"></a>
### أدوات التحكم في الاعتراض

| الزر | الوظيفة |
|--------|----------|
| **Intercept is on/off** | تفعيل الاعتراض أو إيقافه |
| **Forward** | إرسال الطلب إلى الخادم |
| **Drop** | إسقاط الطلب |
| **Action (الإجراء)** | قائمة خيارات إضافية |

<a id="intercept-options"></a>
### خيارات الاعتراض

```
Proxy → Options → Intercept Client Requests

أمثلة على القواعد:
- اعتراض العناصر الواقعة ضمن النطاق فقط
- اعتراض أنواع ملفات محددة فقط
- استبعاد الصور والملفات الثابتة
```

<a id="request-modification"></a>
### تعديل الطلبات

```http
# الطلب الأصلي
GET /page.php?id=1 HTTP/1.1
Host: target.com

# الطلب المعدّل أثناء الاعتراض
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

<a id="match-and-replace"></a>
### المطابقة والاستبدال (Match and Replace)

```
Proxy → Options → Match and Replace

قواعد شائعة:
- إزالة ترويسات الأمان
- تعديل User-Agent
- إضافة ترويسات مخصصة
- استبدال معاملات الطلب
```

| النوع | المطابقة | الاستبدال | الغرض |
|------|-------|---------|---------|
| ترويسة الطلب | `User-Agent: .*` | `User-Agent: Custom` | تغيير User-Agent |
| جسم الطلب | `password=test` | `password=admin` | اختبار بيانات الاعتماد |
| ترويسة الاستجابة | `X-Frame-Options.*` | ` ` | إزالة الترويسة |
| جسم الاستجابة | `disabled` | `enabled` | تفعيل الأزرار |

<a id="response-modification"></a>
### تعديل الاستجابات

```
Proxy → Options → Intercept Server Responses

فعّل ذلك من أجل:
- إزالة ترويسات الأمان
- تعديل JavaScript
- تغيير محتوى الاستجابة
- اختبار الضوابط على جهة العميل
```

---

<a id="-target--scope"></a>
## 🎯 الهدف ونطاق الاختبار

<a id="adding-to-scope"></a>
### إضافة الهدف إلى النطاق

```
الطريقة 1: انقر بزر الفأرة الأيمن داخل Site map ثم اختر Add to scope
الطريقة 2: افتح Target → Scope → Add للإضافة اليدوية
الطريقة 3: من Proxy history اختر Add to scope
```

<a id="scope-configuration"></a>
### إعداد نطاق الاختبار

```
Target → Scope

Include in scope (التضمين في النطاق):
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern

أمثلة على الأنماط:
.*\.target\.com$          # جميع النطاقات الفرعية
^https://target\.com/app  # مسار محدد
```

<a id="advanced-scope-settings"></a>
### إعدادات النطاق المتقدمة

```
Target → Scope → Advanced scope control

Include:
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclude:
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

<a id="site-map"></a>
### خريطة الموقع (Site map)

```
Target → Site map

الميزات:
- عرض جميع المحتويات المكتشفة
- التصفية حسب النطاق
- البحث في المحتوى وتصفيته
- تمييز العناصر المهمة
- مقارنة خرائط المواقع
```

---

<a id="-spidercrawler"></a>
## 🕷️ Spider/Crawler

<a id="starting-the-spider"></a>
### بدء الزحف باستخدام Spider

```
1. انقر بزر الفأرة الأيمن على الهدف في Site map
2. اختر "Spider this host" أو "Spider this branch"
3. أو افتح Target → Site map ثم انقر بزر الفأرة الأيمن واختر Scan → Crawl
```

<a id="spider-settings"></a>
### إعدادات Spider

```
Spider → Options (legacy) OR
Dashboard → New scan → Crawl

إعدادات الزاحف:
- الحد الأقصى لعمق الروابط
- الحد الأقصى لمدة الزحف
- إرسال النماذج
- التعامل مع تسجيل الدخول
```

<a id="crawl-configuration"></a>
### إعداد الزحف

| الإعداد | الوصف | الموصى به |
|---------|-------------|-------------|
| **Link depth** | عمق تتبّع الروابط | 5-10 |
| **Max requests** | الحد الإجمالي للطلبات | 1000-5000 |
| **Duplicate removal** | تجاوز الصفحات المتشابهة | مفعّل |
| **سلبي scanning** | الفحص أثناء الزحف | مفعّل |

<a id="form-submission"></a>
### إرسال النماذج

```
Spider → Options → Application Login

اضبط تسجيل الدخول من أجل:
- مجموعة واحدة من بيانات الاعتماد
- قائمة بيانات اعتماد
- اكتشاف الجلسة
```

---

<a id="-scanner"></a>
## 🔍 Scanner

<a id="scan-types"></a>
### أنواع الفحص

| النوع | الوصف | حالة الاستخدام |
|------|-------------|----------|
| **سلبي** | تحليل حركة الوكيل | مفعّل دائمًا وفق الأصل |
| **نشط** | إرسال حمولات لاكتشاف الثغرات | بعد الحصول على إذن |
| **كامل** | فحص شامل | عند توفر الوقت |
| **مخصص** | اختبارات محددة فقط | اختبار موجّه |

<a id="starting-a-scan"></a>
### بدء فحص

```
الطريقة 1: انقر بزر الفأرة الأيمن ثم اختر Scan
الطريقة 2: افتح Dashboard → New scan
الطريقة 3: أرسل إلى Scanner من الأدوات الأخرى
```

<a id="scan-configuration"></a>
### إعداد الفحص

```
Dashboard → New scan → Scan configuration

إعدادات الفحص المدمجة:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

<a id="vulnerability-categories"></a>
### فئات الثغرات

```
يفحص Scanner الأنواع التالية:

الحقن:
- حقن SQL
- حقن أوامر نظام التشغيل
- حقن LDAP
- حقن XML/XPath

البرمجة النصية عبر المواقع:
- XSS منعكس
- XSS مخزّن
- XSS معتمد على DOM

المصادقة:
- خلل في المصادقة
- مشكلات إدارة الجلسات
- كلمات مرور ضعيفة

أخرى:
- CSRF
- اجتياز المجلدات
- تضمين الملفات
- كشف المعلومات
- مشكلات جهة الخادم
```

<a id="scan-results"></a>
### نتائج الفحص

```
Dashboard → Issue activity
Target → Site map → Issues

لكل مشكلة:
- Severity (High/Medium/Low/Info) — مستوى الخطورة
- Confidence (Certain/Firm/Tentative) — درجة الثقة
- الوصف
- المعالجة
- الأدلة (الطلب والاستجابة)
```

<a id="custom-scan-profiles"></a>
### ملفات فحص مخصصة

```
1. Dashboard → New scan
2. اختر "Scan configuration"
3. انقر "New" أو عدّل إعدادًا موجودًا
4. احفظ الإعداد لإعادة استخدامه

التخصيص:
- الثغرات المطلوب اختبارها
- كثافة الحمولات
- معدل الطلبات
- التعامل مع المصادقة
```

---

<a id="-repeater"></a>
## 🔁 Repeater

<a id="purpose"></a>
### الغرض

اختبار الطلبات الفردية وتعديلها يدويًا.

<a id="sending-to-repeater"></a>
### الإرسال إلى Repeater

```
1. انقر بزر الفأرة الأيمن على أي طلب
2. اختر "Send to Repeater"
3. اختصار لوحة المفاتيح: Ctrl+R
```

<a id="using-repeater"></a>
### استخدام Repeater

```
1. عدّل الطلب في اللوحة اليسرى
2. انقر "Send" أو اضغط Ctrl+Space
3. اعرض الاستجابة في اللوحة اليمنى
4. مقارنة الاستجابات
```

<a id="repeater-features"></a>
### ميزات Repeater

| Feature | الوصف |
|---------|-------------|
| **Multiple tabs** | اختبار طلبات متعددة |
| **Request history** | التنقل باستخدام الزرين < > |
| **Follow redirects** | تفعيل التتبّع الآلي أو إيقافه |
| **Process cookies** | تحديث ملفات الارتباط آليًا |
| **Content-Length** | تحديث الترويسة آليًا |

<a id="testing-examples"></a>
### أمثلة الاختبار

```http
# اختبار حقن SQL
GET /user.php?id=1' HTTP/1.1

# اختبار XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# اختبار تجاوز المصادقة
GET /admin HTTP/1.1
Cookie: role=admin

# اختبار IDOR
GET /api/user/1 HTTP/1.1  # المستخدم الخاص بك
GET /api/user/2 HTTP/1.1  # مستخدم آخر
```

---

<a id="-intruder"></a>
## 🔫 Intruder

<a id="attack-types"></a>
### أنواع الهجوم

| النوع | مواضع الحقن | الحمولات | حالة الاستخدام |
|------|-----------|----------|----------|
| **Sniper** | 1+ | مجموعة واحدة، قيمة في كل مرة | اختبار معامل واحد |
| **Battering Ram** | 1+ | مجموعة واحدة، القيمة نفسها في كل المواضع | القيمة نفسها في جميع المواضع |
| **Pitchfork** | 1+ | مجموعات متعددة بالتوازي | أزواج اسم المستخدم وكلمة المرور |
| **Cluster Bomb** | 1+ | مجموعات متعددة وجميع التركيبات | تجربة جميع تركيبات بيانات الاعتماد |

<a id="sending-to-intruder"></a>
### الإرسال إلى Intruder

```
1. انقر بزر الفأرة الأيمن على الطلب
2. اختر "Send to Intruder"
3. اختصار لوحة المفاتيح: Ctrl+I
```

<a id="setting-positions"></a>
### تحديد مواضع الحقن

```
Intruder → Positions

حدّد نقاط الحقن بالرموز §:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

الأزرار:
- Add § - وضع علامة على التحديد
- Clear § - إزالة العلامات
- Auto § - الاكتشاف الآلي
```

<a id="payload-types"></a>
### أنواع الحمولات

| النوع | الوصف |
|------|-------------|
| **Simple list** | قائمة كلمات مخصصة |
| **Runtime file** | التحميل من ملف |
| **Numbers** | أعداد متتالية أو عشوائية |
| **Dates** | متتاليات تواريخ |
| **Brute forcer** | تركيبات محارف |
| **Null payloads** | حمولات فارغة لاختبار المعدل |
| **Username generator** | أنماط شائعة لأسماء المستخدمين |

<a id="payload-processing"></a>
### معالجة الحمولات

```
Intruder → Payloads → Payload Processing

أضف قواعد:
- إضافة بادئة أو لاحقة
- ترميز URL
- ترميز Base64
- حساب بصمة MD5 أو SHA
- مطابقة واستبدال
```

<a id="grep---match"></a>
### Grep - المطابقة

```
Intruder → Options → Grep - Match

أضف سلاسل لتمييز ظهورها في الاستجابات:
- "Login successful"
- "Invalid password"
- "error"
- تعبير نمطي مخصص

يساعد ذلك في تحديد المحاولات الناجحة.
```

<a id="attack-examples"></a>
### أمثلة الهجمات

<a id="brute-force-login"></a>
#### تخمين بيانات تسجيل الدخول
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type (نوع الهجوم): Cluster bomb
Payload 1 (الحمولة الأولى): قائمة أسماء المستخدمين
Payload 2 (الحمولة الثانية): قائمة كلمات المرور
```

<a id="fuzzing-parameters"></a>
#### تجربة قيم متنوعة للمعاملات
```
GET /api/user/§1§ HTTP/1.1

Attack type (نوع الهجوم): Sniper
Payload (الحمولة): Numbers 1-1000
```

<a id="content-discovery"></a>
#### اكتشاف المحتوى
```
GET /§admin§ HTTP/1.1

Attack type (نوع الهجوم): Sniper
Payload (الحمولة): قائمة كلمات للمجلدات
Filter (التصفية): Status code != 404
```

<a id="results-analysis"></a>
### تحليل النتائج

```
بعد الهجوم:
- الترتيب حسب الطول للعثور على استجابات مختلفة
- الترتيب حسب رمز الحالة
- التصفية حسب نتائج المطابقة
- تصدير النتائج
```

---

<a id="-decoder"></a>
## 🔓 Decoder

<a id="encodingdecoding"></a>
### الترميز وفك الترميز

```
تبويب Decoder

العمليات:
- ترميز URL وفك ترميزه
- ترميز HTML وفك ترميزه
- ترميز Base64 وفك ترميزه
- الترميز الست عشري وفك ترميزه
- الضغط وفك الضغط باستخدام Gzip
- ASCII Hex
- ثنائي
```

<a id="smart-decode"></a>
### Smart Decode

```
Decoder → Smart decode

يكتشف الترميزات التالية ويفكها آليًا:
- Base64
- ترميز URL
- كيانات HTML
- ترميزات مركّبة
```

<a id="common-uses"></a>
### استخدامات شائعة

```
# فك ترميز JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decode as Base64

# فك ترميز معامل URL
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL decode
→ <script>alert(1)</script>

# ترميز الحمولة
<script>alert(1)</script>
→ Base64 encode
→ Use in attack
```

---

<a id="-comparer"></a>
## 📊 Comparer

<a id="purpose-1"></a>
### الغرض

مقارنة عنصرين من البيانات، مثل طلبين أو استجابتين أو أي بيانات أخرى.

<a id="how-to-use"></a>
### طريقة الاستخدام

```
1. انقر بزر الفأرة الأيمن على العنصر 1 ثم اختر Send to Comparer
2. انقر بزر الفأرة الأيمن على العنصر 2 ثم اختر Send to Comparer
3. انتقل إلى تبويب Comparer
4. حدّد العناصر وانقر "Words" أو "Bytes"
```

<a id="comparison-modes"></a>
### أوضاع المقارنة

| Mode | الوصف |
|------|-------------|
| **Words** | المقارنة كلمة بكلمة |
| **Bytes** | المقارنة الدقيقة بايتًا ببايت |

<a id="use-cases"></a>
### حالات الاستخدام

```
- مقارنة استجابات تسجيل الدخول الناجح والفاشل
- البحث عن فروق بين رموز الجلسات
- المقارنة قبل التعديلات وبعدها
- تحديد حقول النماذج المخفية
```

---

<a id="-sequencer"></a>
## 🔢 Sequencer

<a id="purpose-2"></a>
### الغرض

تحليل جودة العشوائية في الرموز، مثل معرّفات الجلسات ورموز CSRF.

<a id="how-to-use-1"></a>
### طريقة الاستخدام

```
1. التقط طلبًا يعيد رمزًا
2. انقر بزر الفأرة الأيمن ثم اختر Send to Sequencer
3. حدّد موضع الرمز
4. ابدأ الالتقاط المباشر
5. حلّل النتائج
```

<a id="analysis-metrics"></a>
### مقاييس التحليل

| المقياس | القيمة الجيدة | الوصف |
|--------|------------|-------------|
| **Overall quality** | >100 bits | درجة العشوائية |
| **Character-level** | إنتروبيا مرتفعة | العشوائية على مستوى المحرف |
| **Bit-level** | إنتروبيا مرتفعة | العشوائية على مستوى البت |

<a id="interpretation"></a>
### تفسير النتائج

```
النتائج:
- Excellent: الرمز عشوائي تشفيريًا وفق وصف الأصل
- Good: مقبول لمعظم الاستخدامات  
- Poor: قد يكون قابلًا للتنبؤ
- Very poor: قد يمكن تخمين الرمز
```

---

<a id="-extensions"></a>
## 🧩 الإضافات

<a id="bapp-store"></a>
### BApp Store

```
Extender → BApp Store

إضافات شائعة:
```

<a id="must-have-extensions"></a>
### إضافات مفيدة

| الإضافة | الوصف |
|-----------|-------------|
| **Autorize** | اختبار صلاحيات الوصول |
| **Logger++** | تسجيل موسّع |
| **Retire.js** | كشف مكتبات JavaScript الضعيفة |
| **نشط Scan++** | فحوص إضافية |
| **AuthMatrix** | اختبار مصفوفة صلاحيات الوصول |
| **Turbo Intruder** | بديل سريع لـ Intruder |
| **Param Miner** | اكتشاف المعاملات المخفية |
| **JSON Web Tokens** | تحليل JWT وتعديله |
| **Hackvertor** | ترميز وفك ترميز متقدمان |
| **CO2** | مجموعة أدوات مفيدة |

<a id="installing-extensions"></a>
### تثبيت الإضافات

```
الطريقة 1: BApp Store
1. Extender → BApp Store
2. ابحث عن الإضافة
3. انقر Install

الطريقة 2: التثبيت اليدوي
1. نزّل ملف .jar أو .py
2. Extender → Extensions → Add
3. حدّد نوع الملف ومساره
```

<a id="extension-settings"></a>
### إعدادات الإضافات

```
Extender → Options

- بيئة Python لإضافات Python
- إعدادات بيئة Java
- خيارات التسجيل
```

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ اختصارات لوحة المفاتيح

<a id="global-shortcuts"></a>
### اختصارات عامة

| الاختصار | Action (الإجراء) |
|----------|--------|
| `Ctrl+I` | Send to Intruder (الإرسال إلى Intruder) |
| `Ctrl+R` | Send to Repeater (الإرسال إلى Repeater) |
| `Ctrl+S` | Search (بحث) |
| `Ctrl+F` | Forward (تمرير الطلب في Proxy) |
| `Ctrl+D` | Drop (إسقاط الطلب في Proxy) |
| `Ctrl+T` | تفعيل الاعتراض أو إيقافه |
| `Ctrl+Shift+T` | تفعيل اعتراض Proxy أو إيقافه |

<a id="tab-navigation"></a>
### التنقل بين التبويبات

| الاختصار | Action (الإجراء) |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

<a id="in-repeater"></a>
### داخل Repeater

| الاختصار | Action (الإجراء) |
|----------|--------|
| `Ctrl+Space` | إرسال الطلب |
| `Ctrl+G` | الانتقال في سجل الطلبات |
| `Ctrl+Plus` | تبويب جديد |

<a id="in-editor"></a>
### داخل المحرر

| الاختصار | Action (الإجراء) |
|----------|--------|
| `Ctrl+U` | ترميز التحديد بصيغة URL |
| `Ctrl+Shift+U` | فك ترميز التحديد من URL |
| `Ctrl+B` | ترميز Base64 |
| `Ctrl+Shift+B` | فك ترميز Base64 |

---

<a id="-testing-workflow"></a>
## 🎯 مسار عمل الاختبار

<a id="standard-web-app-test-flow"></a>
### مسار اعتيادي لاختبار تطبيق ويب

```
Step 1: Setup
├── Configure browser proxy
├── Install SSL certificate
└── Define scope

Step 2: Reconnaissance
├── Browse application manually
├── Run Spider/Crawler
└── Review Site map

Step 3: Analysis
├── Check Proxy history
├── Review passive scan results
└── Identify attack surface

Step 4: Testing
├── Test with Repeater (manual)
├── Test with Intruder (automated)
├── Run active scan
└── Use extensions

Step 5: Reporting
├── Review all findings
├── Generate report
└── Document evidence
```

<a id="testing-checklist"></a>
### قائمة الاختبار

```
المصادقة:
□ تخمين بيانات تسجيل الدخول
□ سياسة كلمات المرور
□ قفل الحساب
□ إدارة الجلسات
□ وظيفة تذكر المستخدم

صلاحيات الوصول:
□ اختبار الوصول المباشر غير الآمن إلى الكائنات (IDOR)
□ رفع الامتيازات
□ التحكم في الوصول على مستوى الوظيفة

التحقق من المدخلات:
□ حقن SQL
□ XSS المنعكس والمخزّن والمعتمد على DOM
□ حقن الأوامر
□ رفع الملفات
□ XXE

الجلسة:
□ تثبيت الجلسة
□ مهلة الجلسة
□ عشوائية الرموز

منطق الأعمال:
□ التلاعب بالأسعار
□ تجاوز مسار العمل
□ حالات التسابق
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="tool-summary"></a>
### ملخص الأدوات

| الأداة | الاستخدام الأساسي | الاختصار الأساسي |
|------|-------------|--------------|
| Proxy | اعتراض الحركة | Ctrl+T (toggle) |
| Repeater | الاختبار اليدوي | Ctrl+R (send to) |
| Intruder | هجمات آلية | Ctrl+I (send to) |
| Scanner | اكتشاف الثغرات | Right-click → Scan |
| Spider | اكتشاف المحتوى | Right-click → Spider |
| Decoder | الترميز وفك الترميز | - |
| Comparer | مقارنة فروق الاستجابات | Send to Comparer |
| Sequencer | تحليل الرموز | Send to Sequencer |

<a id="intruder-attack-types-quick-reference"></a>
### مرجع سريع لأنواع هجمات Intruder

| الهجوم | متى يُستخدم |
|--------|----------|
| **Sniper** | عند اختبار معامل واحد في كل مرة |
| **Battering Ram** | الحمولة نفسها في جميع المواضع |
| **Pitchfork** | أزواج اسم المستخدم وكلمة المرور |
| **Cluster Bomb** | جميع تركيبات اسم المستخدم × كلمة المرور |

<a id="common-status-codes"></a>
### رموز حالة شائعة

| الرمز | المعنى | دلالتها في Intruder |
|------|---------|----------------------|
| 200 | OK | استجابة عادية |
| 301/302 | Redirect | قد تشير إلى نجاح المحاولة |
| 401 | Unauthorized | بيانات اعتماد غير صحيحة وفق تبسيط الأصل |
| 403 | Forbidden | الوصول مرفوض |
| 404 | Not found | مسار غير صحيح |
| 500 | Server error | ثغرة محتملة |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح لتحسين الأداء

1. **تحديد النطاق**
   ```
   أبقِ نطاقات الهدف فقط ضمن النطاق
   يقلّل ذلك التشويش في سجل Proxy
   ```

2. **تعطيل الإضافات**
   ```
   عطّل الإضافات غير المستخدمة لتحسين الأداء
   ```

3. **استخدام ملفات المشروعات**
   ```
   احفظ العمل باستمرار
   استخدم ملفات المشروعات للاختبارات الكبيرة
   ```

<a id="testing-tips"></a>
### نصائح الاختبار

1. **ابدأ بالتحليل السلبي**
   ```
   دع الفحص السلبي يحدد المشكلات السهلة الملاحظة
   راجع النتائج قبل الفحص النشط
   ```

2. **استخدم Repeater أولًا**
   ```
   يكشف الاختبار اليدوي تفاصيل إضافية
   افهم التطبيق قبل أتمتة الاختبار
   ```

3. **التنظيم باستخدام التبويبات**
   ```
   اختر أسماء واضحة لتبويبات Repeater
   استخدم أسماء واضحة لهجمات Intruder
   ```

<a id="avoiding-detection"></a>
### تقليل احتمال الاكتشاف

```
إعدادات لتقليل وضوح النشاط:

Project options → Connections:
- تقييد معدل الطلبات
- تأخيرات عشوائية
- الحد الأقصى للاتصالات المتزامنة

Intruder → Options:
- التأخير بين الطلبات
- وقت البدء
```

<a id="troubleshooting"></a>
### استكشاف المشكلات

| المشكلة | الحل |
|-------|----------|
| تعذّر اعتراض HTTPS | تثبيت شهادة سلطة التصديق (CA) |
| بطء الأداء | تقليل النطاق وتعطيل الإضافات |
| طلبات مفقودة | فحص "Intercept Server Responses" وفق اقتراح الأصل |
| لا يجد Scanner ثغرات | زيادة كثافة الفحص |

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تنبيه:** Burp Suite أداة قوية لاختبار الأمن، ويجب استخدامها فقط في **اختبارات مأذون بها**.
> 
> - ✅ اختبر التطبيقات التي تملكها
> - ✅ اختبر بعد الحصول على إذن كتابي صريح
> - ✅ اختبر في بيئات مخصصة للاختبار
> - ❌ لا تفحص أنظمة الإنتاج دون إذن
> - ❌ لا تستخدم الأداة للوصول غير المأذون به
> 
> **اختبار الأمن غير المأذون به مخالف للقانون وقد يؤدي إلى اتهامات جنائية، وفق التنبيه الوارد في الأصل.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### الموارد الرسمية
- [أكاديمية أمن الويب من PortSwigger](https://portswigger.net/web-security)
- [توثيق Burp Suite](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

<a id="learning-resources"></a>
### موارد التعلم
- [دليل الاختبار من OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [SQLMap](../../SQLMap/translations/README.ar.md)
- [Metasploit Framework](../../Metasploit/translations/README.ar.md)

---

<p align="center">
  <b>🔶 أتقن اختبار أمن تطبيقات الويب!</b><br>
  <i>أفضل طريقة للتعلم هي الممارسة؛ جهّز مختبرًا وتدرّب!</i>
</p>

> **شرح مسار العمل:** يبيّن المخطط الإعداد أولًا، ثم استطلاع التطبيق، وتحليل سطح الهجوم، وإجراء الاختبارات اليدوية والآلية، وأخيرًا مراجعة النتائج وإعداد التقرير وحفظ الأدلة. أُبقي المخطط الإنجليزي للحفاظ على محاذاته، كما أُبقيت أسماء الأزرار والقوائم الإنجليزية لتسهيل العثور عليها في الواجهة.

> **تحديثات الإصدارات:** السعر `$449/year` ومتطلبات الجدول، بما فيها `JRE 8+`، منقولة من الأصل وليست عرض سعر أو مواصفات محدثة. يحدد [توثيق تشغيل Burp](https://portswigger.net/burp/documentation/desktop/troubleshooting/launch-from-command-line) Java 21 حدًا أدنى لتشغيل JAR. ولخيارات الذاكرة، يضع مشغّل Java الخيارات قبل `-jar`، مثل `java -Xmx4g -jar burpsuite_pro.jar`؛ أُبقي ترتيب الأمر الأصلي للمقارنة. [صياغة مشغّل Java الرسمية](https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html#synopsis).

> **توضيح للواجهة:** تحتوي مسارات Spider وExtender وProxy → Options على تسميات من إصدارات سابقة؛ يصف [توثيق الفحص الحالي](https://portswigger.net/burp/documentation/desktop/running-scans) الزحف والفحص من خلال Burp Scanner. كما يسند الأصل `Ctrl+Shift+T` لأكثر من وظيفة؛ راجع التعيينات الفعلية في `Settings → User interface → Hotkeys` بدل افتراض صحة القيمتين معًا. [إعدادات الاختصارات الرسمية](https://portswigger.net/burp/documentation/desktop/settings/ui/hotkeys).

> **توضيح لنتائج Sequencer:** تعطي الاختبارات الإحصائية مؤشرًا إلى جودة العشوائية في عينة الرموز؛ لا ينبغي تفسير كلمة Excellent وحدها على أنها إثبات أن خوارزمية التوليد آمنة تشفيريًا. هذا استنتاج من طبيعة [اختبارات Sequencer الموثّقة](https://portswigger.net/burp/documentation/desktop/tools/sequencer/results/tests). كذلك فإن مثال JWT في Decoder مقتطع؛ يتكون JWT من أجزاء مرمّزة بـ base64url تفصلها نقاط، وفك الترميز لا يثبت صحة التوقيع. [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html#section-3).
