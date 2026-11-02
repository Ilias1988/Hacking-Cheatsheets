<a id="-arjun---hidden-parameter-discovery"></a>
# 🔍 Arjun — اكتشاف معاملات HTTP المخفية

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP Parameter Discovery Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما Arjun؟](#-what-is-arjun)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [أوضاع الاكتشاف](#-discovery-modes)
- [الخيارات المتقدمة](#-advanced-options)
- [خيارات الإخراج](#-output-options)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-arjun"></a>
## 🎯 ما Arjun؟

يكتشف **Arjun** معاملات HTTP المخفية في تطبيقات الويب:

- 🔍 **اكتشاف المعاملات** - البحث عن معاملات GET/POST المخفية
- 🎯 **دعم JSON** - اكتشاف معاملات JSON
- 📡 **دعم طرق متعددة** - أوضاع GET وPOST وJSON
- ⚡ **سريع** - طلبات بدرجة مرتفعة من التزامن

<a id="why-arjun"></a>
### لماذا Arjun؟

| الميزة | الفائدة |
|---------|---------|
| معاملات مخفية | البحث عن نقاط اتصال غير مشار إليها وفق وصف الأصل |
| صيغ متعددة | GET, POST, JSON |
| الوضع السلبي | فحص ملفات JavaScript وفق الأصل |
| الاستيراد من Burp | التكامل مع مسار العمل القائم |

---

<a id="-installation"></a>
## 🚀 التثبيت

```bash
# التثبيت باستخدام pip (موصى به في الأصل)
pip3 install arjun

# من الشيفرة المصدرية
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# التحقق
arjun --help
```

---

<a id="-basic-usage"></a>
## 📖 الاستخدام الأساسي

<a id="single-url"></a>
### عنوان URL واحد

```bash
# اكتشاف أساسي للمعاملات
arjun -u https://example.com/endpoint

# مع ملف نتائج
arjun -u https://example.com/endpoint -o results.txt
```

<a id="multiple-urls"></a>
### عناوين URL متعددة

```bash
# من ملف
arjun -i urls.txt

# من الإدخال القياسي stdin
cat urls.txt | arjun --stdin
```

<a id="specify-http-method"></a>
### تحديد طريقة HTTP

```bash
# معاملات GET (الافتراضي)
arjun -u https://example.com/search -m GET

# معاملات POST
arjun -u https://example.com/api -m POST

# جسم طلب بصيغة JSON
arjun -u https://example.com/api -m JSON
```

---

<a id="-discovery-modes"></a>
## 🔎 أوضاع الاكتشاف

<a id="get-parameters"></a>
### معاملات GET

```bash
# اكتشاف معاملات GET
arjun -u https://example.com/search?known=value -m GET

# مثال للمخرجات:
# [param] id
# [param] user
# [param] debug
```

<a id="post-parameters"></a>
### معاملات POST

```bash
# اكتشاف معاملات نماذج POST
arjun -u https://example.com/login -m POST

# مع بيانات موجودة
arjun -u https://example.com/login -m POST --include 'username=admin'
```

<a id="json-parameters"></a>
### معاملات JSON

```bash
# اكتشاف معاملات جسم JSON
arjun -u https://example.com/api/v1/users -m JSON

# مع كائن JSON أساسي
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

<a id="combined-discovery"></a>
### اكتشاف يجمع طرقًا متعددة

```bash
# جميع الطرق
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-advanced-options"></a>
## ⚙️ الخيارات المتقدمة

<a id="custom-wordlist"></a>
### قائمة كلمات مخصصة

```bash
# استخدام قائمة كلمات مخصصة
arjun -u https://example.com -w /path/to/wordlist.txt

# استخدام قائمة معاملات من SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

<a id="threading--rate-limiting"></a>
### خيوط التنفيذ وتقييد معدل الطلبات

```bash
# تحديد خيوط التنفيذ (الافتراضي 2 وفق الأصل)
arjun -u https://example.com -t 10

# حد المعدل، بعدد الطلبات في الثانية
arjun -u https://example.com --rate-limit 10

# التأخير بين الطلبات بالثواني
arjun -u https://example.com --delay 1
```

<a id="custom-headers"></a>
### ترويسات مخصصة

```bash
# إضافة ترويسة
arjun -u https://example.com --headers "Authorization: Bearer token"

# ترويسات متعددة
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# من ملف
arjun -u https://example.com -H headers.txt
```

<a id="include-known-parameters"></a>
### تضمين المعاملات المعروفة

```bash
# تضمين معاملات معروفة
arjun -u https://example.com/search --include 'q=test&page=1'

# لطلبات POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

<a id="passive-mode"></a>
### الوضع السلبي

```bash
# استخراج الأسماء من ملفات JavaScript وفق الأصل
arjun -u https://example.com --passive

# يفحص ذلك ملفات JavaScript بحثًا عن أسماء معاملات محتملة وفق الأصل
```

<a id="disable-redirects"></a>
### تعطيل اتباع إعادة التوجيه

```bash
# عدم اتباع عمليات إعادة التوجيه
arjun -u https://example.com --disable-redirects
```

<a id="timeout"></a>
### المهلة

```bash
# تحديد المهلة بالثواني
arjun -u https://example.com --timeout 15
```

<a id="stable-mode"></a>
### الوضع المستقر

```bash
# أكثر استقرارًا وأبطأ
arjun -u https://example.com --stable
```

---

<a id="-output-options"></a>
## 📤 خيارات الإخراج

<a id="output-formats"></a>
### صيغ النتائج

```bash
# إخراج نصي، يصفه الأصل بأنه الافتراضي
arjun -u https://example.com -oT results.txt

# إخراج بصيغة JSON
arjun -u https://example.com -oJ results.json

# كلاهما
arjun -u https://example.com -oT text.txt -oJ json.json
```

<a id="json-output-example"></a>
### مثال نتائج JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

<a id="import-from-burp"></a>
### الاستيراد من Burp

```bash
# استيراد طلبات Burp
arjun --import burp_export.xml

# معالجة ملف Burp
arjun -i burp_requests.txt --burp
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="basic-commands"></a>
### الأوامر الأساسية

| الأمر | الوصف |
|---------|-------------|
| `arjun -u URL` | اكتشاف المعاملات |
| `arjun -u URL -m POST` | طريقة POST |
| `arjun -u URL -m JSON` | جسم طلب بصيغة JSON |
| `arjun -i file.txt` | عناوين URL متعددة |
| `arjun -u URL -oJ out.json` | إخراج بصيغة JSON |

<a id="method-options"></a>
### خيارات الطريقة

| الخيار | الوصف |
|------|-------------|
| `-m GET` | معاملات GET |
| `-m POST` | بيانات نموذج POST |
| `-m JSON` | جسم طلب بصيغة JSON |
| `-m GET,POST` | طرق متعددة |

<a id="performance-options"></a>
### خيارات الأداء

| الخيار | الوصف |
|------|-------------|
| `-t 10` | خيوط التنفيذ |
| `--rate-limit 10` | طلبات في الثانية |
| `--delay 1` | التأخير بين الطلبات |
| `--timeout 15` | مهلة الطلب |

<a id="discovery-options"></a>
### خيارات الاكتشاف

| الخيار | الوصف |
|------|-------------|
| `-w wordlist.txt` | قائمة كلمات مخصصة |
| `--include 'a=b'` | تضمين معاملات |
| `--passive` | الاستخراج من JavaScript وفق الأصل |
| `--stable` | وضع أكثر استقرارًا |

---

<a id="-bug-bounty-workflows"></a>
## 🎯 مسارات العمل في برامج مكافآت الثغرات

<a id="basic-discovery"></a>
### اكتشاف أساسي

```bash
# اكتشاف سريع للمعاملات
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

<a id="with-custom-wordlist"></a>
### باستخدام قائمة كلمات مخصصة

```bash
# اكتشاف موسّع
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

<a id="authenticated-testing"></a>
### اختبار مع مصادقة

```bash
# باستخدام جلسة
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

<a id="mass-discovery"></a>
### اكتشاف على عدة أهداف

```bash
# نقاط اتصال متعددة
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# أو باستخدام ملف
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

<a id="pipeline-integration"></a>
### التكامل مع سلسلة الأدوات

```bash
# البحث عن نقاط الاتصال ثم اكتشاف معاملاتها
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

<a id="testing-discovered-parameters"></a>
### اختبار المعاملات المكتشفة

```bash
# بعد الاكتشاف، اختبر وجود ثغرات
# 1. اكتشف المعاملات
arjun -u https://target.com/search -oJ params.json

# 2. استخرج أسماء المعاملات
cat params.json | jq -r '.[].params[].name'

# 3. اختبر باستخدام ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

<a id="-pro-tips"></a>
## 💡 نصائح عملية

1. **ابدأ بالاستطلاع السلبي** - افحص ملفات JavaScript أولًا
2. **استخدم قوائم كلمات مناسبة** - يوصي الأصل بقائمة أسماء معاملات Burp
3. **قيّد معدل الطلبات** - لتقليل احتمال الحظر
4. **اختبر جميع الطرق** - GET وPOST وJSON
5. **افحص الترويسات** - X-Custom-Param وX-Debug وغيرهما
6. **اختبار مع مصادقة** - اختبر المناطق التي تتطلب تسجيل الدخول

<a id="common-hidden-parameters"></a>
### معاملات مخفية شائعة

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

<a id="-resources"></a>
## 📚 الموارد

- [مستودع Arjun على GitHub](https://github.com/s0md3v/Arjun)
- [قائمة أسماء معاملات Burp](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [منهجية برامج مكافآت الثغرات](../../Bug-Bounty-Methodology/translations/README.ar.md)
- [ffuf](../../ffuf/translations/README.ar.md)
- [Burp Suite](../../Burp-Suite/translations/README.ar.md)

---

<p align="center">
  <b>🔍 اكتشف المعاملات المخفية!</b><br>
  <i>Arjun — الباحث عن المعاملات</i>
</p>

> **مصطلحات:** المعامل (Parameter) اسم وقيمة يستقبل بهما التطبيق مدخلًا؛ ونقطة الاتصال (Endpoint) هي عنوان الوظيفة أوالمورد. اكتشاف معاملات غير ظاهرة لا يعني اكتشاف نقاط اتصال جديدة تلقائيًا، خلاف صياغة إحدى خانات الأصل. يدل عنوان الرسم الإنجليزي على «مجموعة أدوات اكتشاف معاملات HTTP»، وقد حُفظت محاذاته.

> **مراجعة توافق:** في شيفرة المشروع التي جرى التحقق منها، التأخير `-d` والمهلة `-T`، والخيوط الافتراضية 5، و`-o` اسم بديل لإخراج JSON. لا تعرّف هذه الشيفرة الخيارات `--stdin` أو`--import` أو`--burp` أو`-H` أو`--delay` أو`--timeout` المستخدمة في بعض أمثلة الأصل. لذلك أُبقيت الأمثلة للمقارنة، ولا يُفترض توافقها مع كل إصدار. [تعريف الخيارات الرسمي](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py).

> **تصحيح للترويسات والوضع السلبي:** يفصل `--headers` الترويسات بأسطر جديدة، لا بفاصلة منقوطة بين أسماء ترويسات مختلفة. كما يجمع `--passive` أسماء من مصادر مثل Wayback وCommonCrawl وOTX، ثم تُستخدم في عملية الاكتشاف؛ فلا يعني هذا الخيار وحده الاقتصار على قراءة JavaScript دون اختبار الهدف. [دليل الاستخدام الرسمي](https://github.com/s0md3v/Arjun/wiki/Usage).

> **توضيح لصيغة النتائج:** مثال JSON في الأصل يعرض كائنات تحتوي name وreason، لكن الشيفرة التي روجعت تحفظ `params` قائمة أسماء نصية. لذلك لا يُعمّم استعلام `.[].params[].name` على كل إصدار. كذلك لا تنشئ خطوات المثال ملف `params.txt` تلقائيًا قبل ffuf. [بنية النتائج في المصدر](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py). حُفظ JSON والاستعلام الأصليان حرفيًا مع هذا التوضيح.
