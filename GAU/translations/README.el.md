# 🔗 GAU — Συλλογή γνωστών URL

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Συλλογή γνωστών URL από πολλές πηγές
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **GAU (Get All URLs)** συλλέγει γνωστά URLs από ιστορικά αρχεία και εξωτερικούς παρόχους. Βοηθά στην ανακάλυψη παλιών διαδρομών, JavaScript και URLs με παραμέτρους. Δεν βρίσκει απαραίτητα κάθε URL μιας εφαρμογής.

**Βασικοί όροι:** *archive* = ιστορικό αρχείο, *provider* = πηγή δεδομένων, *query string* = παράμετροι μετά το ερωτηματικό του URL, *MIME type* = τύπος περιεχομένου, *deduplication* = αφαίρεση διπλοτύπων.

Ένα ιστορικό URL μπορεί να έχει καταργηθεί, να έχει αλλάξει ιδιοκτήτη ή να είναι εκτός scope. Η μεταγενέστερη χρήση httpx, crawler ή scanner είναι ξεχωριστό ενεργό βήμα.

## 📋 Περιεχόμενα

- [Τι είναι το GAU](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Πάροχοι δεδομένων](#section-04)
- [Φιλτράρισμα](#section-05)
- [Συνδυασμός εργαλείων](#section-06)
- [Γρήγορη αναφορά](#section-07)
- [Ροή εργασίας Bug Bounty](#section-08)
- [Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το GAU

**Το GAU** (Get All URLs) συλλέγει γνωστά URL από πολλές πηγές:

- 🔍 **Wayback Machine** — ιστορικά URL
- 🌐 **Common Crawl** — δεδομένα εξερεύνησης web
- 📡 **AlienVault OTX** — γνωστά URL από πηγή πληροφοριών ασφάλειας
- 🔒 **URLScan.io** — URL που έχουν καταγραφεί σε ελέγχους

### Δυνατότητες GAU

| Χρήση και όφελος | Περιγραφή |
| --------- | ------------- |
| **Συλλογή πληροφοριών από τρίτες πηγές** | Συλλογή από παρόχους χωρίς άμεση επικοινωνία με τον στόχο στο συγκεκριμένο βήμα |
| **Ιστορικά δεδομένα** | Αναζήτηση παλιών ή μη εμφανών διαδρομών |
| **Γρήγορο** | Παράλληλη συλλογή αποτελεσμάτων |
| **Πολλαπλές πηγές** | Αποτελέσματα από πολλές πηγές |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

```bash
# Εγκατάσταση μέσω Go
go install github.com/lc/gau/v2/cmd/gau@latest

# Εγκατάσταση από πηγαίο κώδικα
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Επιβεβαίωση εγκατάστασης
gau --version
```

---

<a id="section-03"></a>

## 📖 Βασική χρήση

### Ένα domain

```bash
# Βασική χρήση
gau example.com

# Με αρχείο αποτελεσμάτων
gau example.com -o urls.txt

# Το --subs του παραδείγματος συμπεριλαμβάνει υποτομείς· δεν είναι επιλογή απόκρυψης banner
gau --subs example.com
```

### Πολλαπλά domains

```bash
# Ανάγνωση από την κανονική είσοδο
echo "example.com" | gau

# Πολλαπλά domains
cat domains.txt | gau

# Από αρχείο
gau --threads 5 < domains.txt
```

### Συμπερίληψη υποτομέων

```bash
# Συμπερίληψη υποτομέων στα αποτελέσματα
gau --subs example.com
```

### Παράλληλες εργασίες

```bash
# Ορισμός παράλληλων εργασιών, με προεπιλογή 1 κατά το πρωτότυπο
gau --threads 5 example.com

# Προσαρμογή στα όρια ρυθμού
gau --threads 2 example.com
```

---

<a id="section-04"></a>

## 🌐 Πάροχοι δεδομένων

> **Επιλογή παρόχων:** Το blacklist αποκλείει επεκτάσεις αρχείων, όχι πάροχο. Το providers επιλέγει πηγές. Διαθεσιμότητα, όρια και ρυθμίσεις αλλάζουν ανά έκδοση και υπηρεσία. Δείτε το [επίσημο GAU](https://github.com/lc/gau).


### Διαθέσιμοι πάροχοι

| Πάροχος | Περιγραφή | Προεπιλογή του πρωτοτύπου |
| ---------- | ------------- | --------- |
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Επιλογή παρόχων

```bash
# Χρήση μόνο συγκεκριμένων παρόχων
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Παράδειγμα με --blacklist· αφορά επεκτάσεις αρχείων, όχι εξαίρεση παρόχου GAU
gau --blacklist commoncrawl example.com
```

### Ρυθμίσεις παρόχων

```bash
# Θέση αρχείου ρυθμίσεων
~/.gau.toml

# Παράδειγμα ρυθμίσεων
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

<a id="section-05"></a>

## 🔍 Φιλτράρισμα

> **MIME και επέκταση:** Τα -mt/-ft αφορούν MIME types, όχι αυθαίρετα regex επεκτάσεων. Τα σχετικά αρχικά παραδείγματα δεν εφαρμόζουν όσα υπονοούν οι τίτλοι τους. Το -subs περιλαμβάνει υποτομείς, δεν είναι silent. Ιστορικοί κωδικοί παρόχων δεν εγγυώνται σημερινή απόκριση.


### Φιλτράρισμα βάσει κωδικού κατάστασης HTTP

```bash
# Επιλογή εγγραφών παρόχων με σχετικό status· όχι νέος έλεγχος διαθεσιμότητας στόχου
gau --mc 200 example.com

# Πολλαπλοί κωδικοί κατάστασης
gau --mc 200,301,302 example.com

# Εξαίρεση συγκεκριμένων κωδικών κατάστασης
gau --fc 404,500 example.com
```

### Φιλτράρισμα βάσει επέκτασης

```bash
# Το -mt αφορά MIME types, όχι επεκτάσεις· το αρχικό παράδειγμα χρειάζεται διόρθωση
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Το -ft αφορά MIME types· για επεκτάσεις ελέγξτε το blacklist
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Φιλτράρισμα βάσει τύπου περιεχομένου MIME

```bash
# Φιλτράρισμα βάσει τύπου περιεχομένου
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Μορφή αποτελεσμάτων

```bash
# Έξοδος JSON
gau --json example.com

# Αναλυτική εμφάνιση δοκιμών
gau --verbose example.com
```

---

<a id="section-06"></a>

## 🔗 Συνδυασμός εργαλείων

> **Επόμενα βήματα:** Ελέγξτε scope πριν από ενεργά αιτήματα. Η αφαίρεση διπλοτύπων μπορεί να συγχωνεύει διαφορετικές εισόδους. Παράμετρος URL και απόκριση 200 είναι υποψήφιο σημείο, όχι επιβεβαίωση XSS ή SQL injection.


### Με httpx

```bash
# Έλεγχος προσβασιμότητας URL
gau example.com | httpx -silent

# Με κωδικούς κατάστασης
gau example.com | httpx -sc -title
```

### Με grep και εξαγωγή παραμέτρων

```bash
# Αναζήτηση URL με παραμέτρους
gau example.com | grep "="

# Αναζήτηση συγκεκριμένων μοτίβων
gau example.com | grep -E "\.php\?|\.asp\?"

# GF Patterns
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Με urldedupe

```bash
# Αφαίρεση διπλοτύπων
gau example.com | urldedupe

# Εναλλακτικά: sort -u
gau example.com | sort -u
```

### Με uro

```bash
# Αφαίρεση παρόμοιων URL σύμφωνα με τη λογική του εργαλείου
gau example.com | uro
```

### Ολοκληρωμένη αλυσίδα εντολών

```bash
# Αλυσίδα συλλογής και επεξεργασίας URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `gau domain.com` | Συλλογή γνωστών URL ενός domain |
| `gau --subs domain.com` | Συμπερίληψη υποτομέων |
| `gau -o urls.txt domain.com` | Αποθήκευση σε αρχείο |
| `gau --threads 5 domain.com` | Παράλληλες εργασίες |

### Επιλογές φιλτραρίσματος

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `--mc 200` | Συμπερίληψη κωδικού κατάστασης |
| `--fc 404` | Εξαίρεση κωδικού κατάστασης |
| `--mt "regex"` | Συμπερίληψη βάσει κανονικής έκφρασης, όπου υποστηρίζεται από την επιλογή |
| `--ft "regex"` | Εξαίρεση βάσει κανονικής έκφρασης, όπου υποστηρίζεται από την επιλογή |
| `--json` | Έξοδος JSON |

### Επιλογές παρόχων

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `--providers wayback,otx` | Χρήση συγκεκριμένων παρόχων |
| `--blacklist commoncrawl` | Παράδειγμα του πρωτοτύπου· ελέγξτε τη σημασία της επιλογής |

### Χρήσιμες σύντομες εντολές

```bash
# Επιλογή URL που τελειώνουν σε .js· όχι λήψη κάθε αρχείου JavaScript
gau example.com | grep -E "\.js$" | sort -u

# Αναζήτηση διαδρομών API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Αναζήτηση παραμέτρων
gau example.com | grep "?" | grep "=" | sort -u

# Επιλογή υποψήφιων URL για έλεγχο SQL Injection
gau example.com | gf sqli

# Επιλογή υποψήφιων URL για έλεγχο XSS
gau example.com | gf xss

# Προσβάσιμα URL που περιέχουν παραμέτρους
gau example.com | grep "=" | httpx -silent
```

---

<a id="section-08"></a>

## 🎯 Ροή εργασίας Bug Bounty

```bash
# 1. Συλλογή διαθέσιμων γνωστών URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. Επιλογή URL προς διερεύνηση
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Έλεγχος τρέχουσας πρόσβασης
cat params.txt | httpx -silent > live_params.txt

# 4. Επιλογή υποψήφιων URL βάσει μοτίβων· δεν επιβεβαιώνει ευπάθειες
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Παράδειγμα δοκιμής που χρειάζεται επιβεβαίωση αποτελέσματος
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Αποθετήριο GAU στο GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Σχετικοί οδηγοί
- [Μεθοδολογία Bug Bounty](../../Bug-Bounty-Methodology/translations/README.el.md)
- [Subfinder](../../Subfinder/translations/README.el.md)
- [httpx](../../httpx/translations/README.el.md)

---

<p align="center">
  <b>🔗 Συλλέξτε γνωστά URL από πολλές πηγές!</b><br>
  <i>GAU — συλλογή γνωστών URL</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
