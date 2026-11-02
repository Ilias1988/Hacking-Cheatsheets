# 🔍 Arjun — Αναζήτηση μη εμφανών παραμέτρων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         Εργαλείο αναζήτησης παραμέτρων HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.it.md">🇮🇹 Versione Italiana</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Arjun** αναζητά παραμέτρους HTTP που μπορεί να δέχεται μια εφαρμογή χωρίς να τις εμφανίζει εύκολα. Συγκρίνει αποκρίσεις για διαφορετικές υποψήφιες παραμέτρους. Η αποδοχή παραμέτρου δεν αποτελεί από μόνη της ευπάθεια.

**Βασικοί όροι:** *hidden parameter* = μη εμφανής παράμετρος, *GET* = παράμετροι συνήθως στο URL, *POST* = δεδομένα στο σώμα αιτήματος, *JSON body* = σώμα με δομημένα δεδομένα JSON, *baseline* = απόκριση αναφοράς για σύγκριση.

Ξεκινήστε με ένα εγκεκριμένο endpoint και σταθερή απόκριση αναφοράς. Μεταβαλλόμενο περιεχόμενο και tokens μπορούν να μπερδέψουν τη σύγκριση. Ελέγξτε μορφές εισόδου/εξόδου ανά έκδοση.

## 📋 Περιεχόμενα

- [Τι είναι το Arjun](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Τρόποι αναζήτησης παραμέτρων](#section-04)
- [Προχωρημένες επιλογές](#section-05)
- [Επιλογές αποτελεσμάτων](#section-06)
- [Γρήγορη αναφορά](#section-07)
- [Ροές εργασίας Bug Bounty](#section-08)
- [Πρακτικές συμβουλές](#section-09)
- [Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Arjun

**Το Arjun** αναζητά παραμέτρους HTTP που δεν εμφανίζονται εύκολα στην εφαρμογή:

- 🔍 **Αναζήτηση παραμέτρων** — υποψήφιες παράμετροι GET και POST
- 🎯 **Υποστήριξη JSON** — αναζήτηση πεδίων σώματος JSON
- 📡 **Πολλοί τρόποι εισόδου** — GET, POST και JSON
- ⚡ **Παράλληλες εργασίες** — δυνατότητα πολλών ταυτόχρονων αιτημάτων

### Δυνατότητες Arjun

| Χαρακτηριστικό | Χρήση και όφελος |
| --------- | --------- |
| Μη εμφανείς παράμετροι | Αναζήτηση μη εμφανών παραμέτρων σε γνωστά endpoints |
| Μορφές εισόδου | GET, POST, JSON |
| Λειτουργία συλλογής υποψήφιων παραμέτρων από πηγές | Έλεγχος της πηγής δεδομένων· δεν ισοδυναμεί πάντα με ανάλυση JavaScript |
| Εισαγωγή αιτημάτων Burp | Ενσωμάτωση στην υπάρχουσα ροή εργασίας |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

```bash
# Εγκατάσταση μέσω pip
pip3 install arjun

# Εγκατάσταση από πηγαίο κώδικα
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Επιβεβαίωση εγκατάστασης
arjun --help
```

---

<a id="section-03"></a>

## 📖 Βασική χρήση

### Ένα URL

```bash
# Βασική αναζήτηση παραμέτρων
arjun -u https://example.com/endpoint

# Με αρχείο αποτελεσμάτων
arjun -u https://example.com/endpoint -o results.txt
```

### Πολλαπλά URL

```bash
# Από αρχείο
arjun -i urls.txt

# Ανάγνωση από την κανονική είσοδο
cat urls.txt | arjun --stdin
```

### Επιλογή μεθόδου HTTP

```bash
# Παράμετροι GET, προεπιλογή του πρωτοτύπου
arjun -u https://example.com/search -m GET

# Παράμετροι POST
arjun -u https://example.com/api -m POST

# Σώμα JSON
arjun -u https://example.com/api -m JSON
```

---

<a id="section-04"></a>

## 🔎 Τρόποι αναζήτησης παραμέτρων

### Παράμετροι GET

```bash
# Αναζήτηση παραμέτρων GET
arjun -u https://example.com/search?known=value -m GET

# Ενδεικτική έξοδος:
# [param] id
# [param] user
# [param] debug
```

### Παράμετροι POST

```bash
# Αναζήτηση παραμέτρων φόρμας POST
arjun -u https://example.com/login -m POST

# Με ήδη γνωστά δεδομένα
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### Παράμετροι σώματος JSON

```bash
# Αναζήτηση πεδίων σώματος JSON
arjun -u https://example.com/api/v1/users -m JSON

# Με αρχικά δεδομένα JSON
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Συνδυασμός τρόπων αναζήτησης

```bash
# Διευρυμένοι έλεγχοι του εργαλείου
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="section-05"></a>

## ⚙️ Προχωρημένες επιλογές

### Προσαρμοσμένη λίστα λέξεων

```bash
# Χρήση δικής σας λίστας λέξεων
arjun -u https://example.com -w /path/to/wordlist.txt

# Χρήση λίστας ονομάτων παραμέτρων SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Παράλληλες εργασίες και περιορισμός ρυθμού

```bash
# Ορισμός παράλληλων εργασιών, με προεπιλογή 2 κατά το πρωτότυπο
arjun -u https://example.com -t 10

# Όριο αιτημάτων ανά δευτερόλεπτο
arjun -u https://example.com --rate-limit 10

# Καθυστέρηση μεταξύ αιτημάτων σε δευτερόλεπτα
arjun -u https://example.com --delay 1
```

### Προσαρμοσμένες κεφαλίδες HTTP

```bash
# Προσθήκη κεφαλίδας HTTP
arjun -u https://example.com --headers "Authorization: Bearer token"

# Πολλαπλές κεφαλίδες HTTP
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Από αρχείο
arjun -u https://example.com -H headers.txt
```

### Συμπερίληψη γνωστών παραμέτρων

```bash
# Συμπερίληψη γνωστών παραμέτρων
arjun -u https://example.com/search --include 'q=test&page=1'

# Για δεδομένα POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Λειτουργία συλλογής υποψήφιων παραμέτρων από πηγές

> **Διευκρίνιση:** Η παθητική συλλογή αντλεί γνωστά ονόματα παραμέτρων από τρίτες πηγές· δεν είναι γενικός JavaScript crawler. Η υπόλοιπη διαδικασία μπορεί να περιλαμβάνει ενεργά αιτήματα. Δείτε την [επίσημη χρήση Arjun](https://github.com/s0md3v/Arjun/wiki/Usage).


```bash
# Περιγραφή του πρωτοτύπου· ελέγξτε τις πραγματικές πηγές του --passive
arjun -u https://example.com --passive

# Το --passive δεν ισοδυναμεί από μόνο του με γενικό crawler JavaScript
```

### Απενεργοποίηση ακολούθησης ανακατευθύνσεων

```bash
# Χωρίς ακολούθηση ανακατευθύνσεων
arjun -u https://example.com --disable-redirects
```

### Χρονικό όριο

```bash
# Ορισμός χρονικού ορίου σε δευτερόλεπτα
arjun -u https://example.com --timeout 15
```

### Λειτουργία σταθερότερου ρυθμού (Stable)

```bash
# Πιο συντηρητικές ρυθμίσεις, συνήθως βραδύτερα
arjun -u https://example.com --stable
```

---

<a id="section-06"></a>

## 📤 Επιλογές αποτελεσμάτων

> **Σχήματα και επιλογές:** Το δείγμα JSON δεν εγγυάται το σχήμα της έκδοσής σας. Επιλογές -o, stdin/import/Burp, headers από αρχείο και πολλαπλές μέθοδοι χρειάζονται επιβεβαίωση. Επαληθεύστε ότι η διαφορά απόκρισης οφείλεται πράγματι στην παράμετρο.


### Μορφές αποτελεσμάτων

```bash
# Έξοδος απλού κειμένου κατά το πρωτότυπο
arjun -u https://example.com -oT results.txt

# Έξοδος JSON
arjun -u https://example.com -oJ results.json

# Docker και Kubernetes
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Παράδειγμα εξόδου JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Εισαγωγή αιτημάτων Burp

```bash
# Παράδειγμα εισαγωγής αιτημάτων Burp· ελέγξτε τη συμβατή μορφή
arjun --import burp_export.xml

# Επεξεργασία αρχείου Burp· απαιτεί συμβατή μορφή εισόδου
arjun -i burp_requests.txt --burp
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `arjun -u URL` | Αναζήτηση παραμέτρων |
| `arjun -u URL -m POST` | Μέθοδος POST |
| `arjun -u URL -m JSON` | Σώμα JSON |
| `arjun -i file.txt` | Πολλαπλά URL |
| `arjun -u URL -oJ out.json` | Έξοδος JSON |

### Επιλογές μεθόδου

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-m GET` | Παράμετροι GET |
| `-m POST` | Δεδομένα φόρμας POST |
| `-m JSON` | Σώμα JSON |
| `-m GET,POST` | Πολλαπλές μέθοδοι |

### Επιλογές απόδοσης

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-t 10` | Παράλληλες εργασίες (threads) |
| `--rate-limit 10` | Αιτήματα ανά δευτερόλεπτο |
| `--delay 1` | Καθυστέρηση μεταξύ αιτημάτων |
| `--timeout 15` | Χρονικό όριο αιτήματος |

### Επιλογές εντοπισμού συστημάτων

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-w wordlist.txt` | Προσαρμοσμένη λίστα λέξεων |
| `--include 'a=b'` | Συμπερίληψη παραμέτρων |
| `--passive` | Συλλογή υποψήφιων παραμέτρων κατά το παράδειγμα του πρωτοτύπου |
| `--stable` | Λειτουργία με πιο συντηρητικές ρυθμίσεις |

---

<a id="section-08"></a>

## 🎯 Ροές εργασίας Bug Bounty

### Βασική αναζήτηση

```bash
# Αρχική αναζήτηση παραμέτρων
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Με προσαρμοσμένη λίστα λέξεων

```bash
# Εκτενέστερη αναζήτηση
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Έλεγχοι με ταυτοποίηση

```bash
# Με στοιχεία συνεδρίας
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Αναζήτηση σε πολλαπλά endpoints

```bash
# Πολλαπλές διαδρομές
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Εναλλακτικά από αρχείο
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Ενσωμάτωση σε αλυσίδα εντολών

```bash
# Συλλογή διαδρομών και έπειτα αναζήτηση παραμέτρων
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Συνδυασμός Katana και Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Έλεγχος εντοπισμένων παραμέτρων

> **Ελλιπές πέρασμα δεδομένων:** Το jq δεν δημιουργεί το params.txt που διαβάζει μετά το ffuf. Η έκφρασή του εξαρτάται επίσης από το πραγματικό σχήμα JSON. Ελέγξτε και αποθηκεύστε συνειδητά τη λίστα πριν τη χρησιμοποιήσετε.


```bash
# Μετά την αναζήτηση, έλεγχος των υποψήφιων παραμέτρων
# 1. Αναζήτηση παραμέτρων
arjun -u https://target.com/search -oJ params.json

# 2. Εξαγωγή ονομάτων παραμέτρων από συμβατή έξοδο JSON
cat params.json | jq -r '.[].params[].name'

# 3. Έλεγχος με ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

<a id="section-09"></a>

## 💡 Πρακτικές συμβουλές

1. **Ξεκινήστε από διαθέσιμα δεδομένα** — εξετάστε ποιες πηγές χρησιμοποιεί το εργαλείο
2. **Επιλέξτε σχετικές λίστες λέξεων** — η λίστα Burp parameter names είναι ένα παράδειγμα
3. **Περιορίστε τον ρυθμό** — σύμφωνα με τους κανόνες του προγράμματος, χωρίς εγγύηση αποφυγής αποκλεισμού
4. **Εξετάστε τις υποστηριζόμενες μορφές εισόδου** — GET, POST και JSON
5. **Εξετάστε τις σχετικές κεφαλίδες** — όπως X-Custom-Param και X-Debug, όπου έχουν νόημα
6. **Χρησιμοποιήστε την κατάλληλη ταυτοποίηση** — για περιοχές που απαιτούν σύνδεση

### Συνήθη ονόματα μη εμφανών παραμέτρων

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

<a id="section-10"></a>

## 📚 Πηγές

- [Αποθετήριο Arjun στο GitHub](https://github.com/s0md3v/Arjun)
- [Λίστα ονομάτων παραμέτρων για ελέγχους web](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Σχετικοί οδηγοί
- [Μεθοδολογία Bug Bounty](../../Bug-Bounty-Methodology/translations/README.el.md)
- [ffuf](../../ffuf/translations/README.el.md)
- [Burp Suite](../../Burp-Suite/translations/README.el.md)

---

<p align="center">
  <b>🔍 Αναζητήστε μη εμφανείς παραμέτρους!</b><br>
  <i>Arjun — δοκιμές παραμέτρων HTTP</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
