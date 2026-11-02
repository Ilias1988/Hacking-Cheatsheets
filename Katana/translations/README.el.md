# ⚔️ Katana — Εργαλείο εξερεύνησης ιστοτόπων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Εργαλείο εξερεύνησης ιστοτόπων
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Katana** είναι **web crawler**: επισκέπτεται σελίδες και ανακαλύπτει συνδέσμους και σημεία εισόδου. Αναλύει επίσης JavaScript και υποστηρίζει headless browser για δυναμικές εφαρμογές.

**Βασικοί όροι:** *crawl depth* = βάθος ακολούθησης συνδέσμων, *crawl scope* = επιτρεπόμενα όρια περιήγησης, *concurrency* = ταυτόχρονες εργασίες, *rate limit* = όριο ρυθμού αιτημάτων, *authenticated crawl* = περιήγηση με συνεδρία χρήστη.

Ορίστε στενά όρια και ελέγξτε πού οδηγούν οι σύνδεσμοι. Η αυτόματη συμπλήρωση φορμών και η περιήγηση με ενεργή συνεδρία μπορεί να προκαλέσουν ενέργειες στην εφαρμογή.

## 📋 Περιεχόμενα

- [Τι είναι το Katana](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Επιλογές εξερεύνησης](#section-04)
- [Λειτουργία browser χωρίς γραφικό περιβάλλον (Headless)](#section-05)
- [Αποτελέσματα και φίλτρα](#section-06)
- [Γρήγορη αναφορά](#section-07)
- [Ροές εργασίας Bug Bounty](#section-08)
- [Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Katana

**Το Katana** είναι εργαλείο της ProjectDiscovery για εξερεύνηση ιστοτόπων και ανακάλυψη συνδέσμων:

- ⚡ **Παράλληλη εκτέλεση** — εργαλείο γραμμένο σε Go
- 🔍 **Εκτέλεση JavaScript** — υποστήριξη browser χωρίς γραφικό περιβάλλον
- 📡 **Δυναμικό web** — υποστήριξη εφαρμογών μίας σελίδας και δυναμικού περιεχομένου
- 🎯 **Εστίαση στο Bug Bounty** — σχεδιασμένο για ροές ελέγχων ασφάλειας

### Δυνατότητες Katana

| Χαρακτηριστικό | Χρήση και όφελος |
| --------- | --------- |
| Chrome χωρίς γραφικό περιβάλλον | Εξερεύνηση εφαρμογών με JavaScript |
| Προσαρμοσμένες κεφαλίδες HTTP | Εξερεύνηση με ταυτοποίηση |
| Καθορισμός ορίων εξερεύνησης | Παραμονή στο επιτρεπόμενο πεδίο |
| Συμπλήρωση φορμών | Αναζήτηση περισσότερων διαδρομών |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

```bash
# Εγκατάσταση μέσω Go
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Εγκατάσταση μέσω Docker
docker pull projectdiscovery/katana

# Επιβεβαίωση εγκατάστασης
katana -version
```

---

<a id="section-03"></a>

## 📖 Βασική χρήση

### Ένας στόχος

```bash
# Βασική εξερεύνηση
katana -u https://example.com

# Αποθήκευση αποτελεσμάτων
katana -u https://example.com -o urls.txt

# Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα
katana -u https://example.com -silent
```

### Πολλαπλοί στόχοι

```bash
# Ανάγνωση από την κανονική είσοδο
echo "https://example.com" | katana

# Πολλαπλά URL
cat targets.txt | katana

# Από αρχείο
katana -list targets.txt
```

### Βάθος εξερεύνησης

```bash
# Ορισμός βάθους, με ενδεικτική προεπιλογή 3 του πρωτοτύπου
katana -u https://example.com -d 5

# Βάθος 1· η ακριβής κάλυψη εξαρτάται από το εργαλείο και τη στρατηγική
katana -u https://example.com -d 1
```

---

<a id="section-04"></a>

## 🕷️ Επιλογές εξερεύνησης

### Καθορισμός ορίων εξερεύνησης

> **Πραγματικά όρια:** Το field scope μπορεί να περιλαμβάνει περισσότερους υποτομείς από όσους επιτρέπει το πρόγραμμα. Ελέγξτε σημασία πεδίων και ακριβή επιτρεπόμενα όρια.


```bash
# Παράδειγμα περιορισμού ονομάτων· ελέγξτε την ακριβή σημασία του field scope
katana -u https://example.com -fs dn

# Επιλογές ορίων εξερεύνησης:
# dn - όνομα domain κατά τη συγκεκριμένη έκδοση
# rdn - βασικό domain
# fqdn - πλήρες όνομα domain

# Επιλογή πεδίου ονομάτων για περιορισμό εξερεύνησης
katana -u https://example.com -fs rdn
```

### Στρατηγική εξερεύνησης

> **Λανθασμένη επιλογή:** Το -cs αφορά regex του crawl scope, όχι στρατηγική. Για depth-first/breadth-first χρειάζεται η επιλογή strategy της έκδοσής σας. Οι αρχικές εντολές απαιτούν προσαρμογή. Δείτε τις [επίσημες επιλογές Katana](https://github.com/projectdiscovery/katana).


```bash
# Εξερεύνηση πρώτα σε βάθος· ελέγξτε τη σωστή επιλογή στρατηγικής
katana -u https://example.com -cs depth-first

# Εξερεύνηση πρώτα ανά επίπεδο
katana -u https://example.com -cs breadth-first
```

### Αριθμός ταυτόχρονων εργασιών

```bash
# Παράλληλα αιτήματα, με προεπιλογή 10 κατά το πρωτότυπο
katana -u https://example.com -c 50

# Όριο αιτημάτων ανά δευτερόλεπτο
katana -u https://example.com -rl 100

# Παράλληλη επεξεργασία στόχων ή εργασιών
katana -u https://example.com -p 10
```

### Διάρκεια εξερεύνησης

```bash
# Ορισμός χρονικού ορίου σε δευτερόλεπτα
katana -u https://example.com -timeout 30

# Μέγιστη διάρκεια εξερεύνησης
katana -u https://example.com -ct 300
```

### Αυτόματη συμπλήρωση φορμών

```bash
# Ενεργοποίηση συμπλήρωσης φορμών
katana -u https://example.com -aff

# Με δικές σας τιμές
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

<a id="section-05"></a>

## 🌐 Λειτουργία browser χωρίς γραφικό περιβάλλον (Headless)

### Ενεργοποίηση browser χωρίς γραφικό περιβάλλον

```bash
# Ενεργοποίηση Chrome χωρίς γραφικό περιβάλλον
katana -u https://example.com -headless

# Browser με καθορισμένο βάθος εξερεύνησης
katana -u https://example.com -headless -d 3
```

### Επιλογές Headless

> **Browser:** Το system-chrome επιλέγει εγκατεστημένο Chrome, δεν αποτελεί γενικά όρισμα διαδρομής. Το no-sandbox απενεργοποιεί την απομόνωση του browser και δεν είναι γενική σύσταση. Screenshot και custom form επιλογές χρειάζονται έλεγχο συμβατότητας.


```bash
# Χρήση εγκατεστημένου Chrome με -system-chrome, όχι καθορισμός διαδρομής
katana -u https://example.com -headless -system-chrome

# Παράδειγμα απενεργοποίησης απομόνωσης browser· δεν είναι γενική σύσταση
katana -u https://example.com -headless -no-sandbox

# Παράδειγμα στιγμιότυπων· ελέγξτε αν υποστηρίζεται η συγκεκριμένη επιλογή
katana -u https://example.com -headless -screenshot
```

### Εξερεύνηση συνδέσμων JavaScript

> **Χρόνοι:** Το timeout αφορά χρονικό όριο αιτήματος σε δευτερόλεπτα, όχι γενικά αναμονή JavaScript σε milliseconds. Το crawl duration περιορίζει τη συνολική περιήγηση. Πρόκειται για διαφορετικές επιλογές.


```bash
# Το -timeout του παραδείγματος ορίζει χρονικό όριο αιτήματος σε δευτερόλεπτα, όχι ειδική αναμονή JavaScript σε ms
katana -u https://example.com -headless -timeout 5

# Συλλογή διαδρομών από JavaScript
katana -u https://example.com -js-crawl
```

---

<a id="section-06"></a>

## 📤 Αποτελέσματα και φίλτρα

> **Πεδία και φίλτρα:** Η έκδοση μπορεί να μην υποστηρίζει όλα τα πεδία του πίνακα ή τις επιλογές -mc/-fc. Ελέγξτε διαθέσιμα πεδία και εκφράσεις φίλτρων πριν χρησιμοποιήσετε τα δείγματα σε pipeline.


### Μορφές αποτελεσμάτων

```bash
# Αποθήκευση συνήθους εξόδου σε αρχείο
katana -u https://example.com -o results.txt

# Έξοδος JSON
katana -u https://example.com -jsonl -o results.json

# Χωρίς χρώματα στην έξοδο
katana -u https://example.com -nc
```

### Πεδία αποτελεσμάτων

```bash
# Εμφάνιση συγκεκριμένων πεδίων
katana -u https://example.com -f url,method,status

# Διαθέσιμα πεδία κατά το πρωτότυπο:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Φιλτράρισμα βάσει επέκτασης

```bash
# Συμπερίληψη επεκτάσεων
katana -u https://example.com -em js,php,asp

# Εξαίρεση επεκτάσεων
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Φιλτράρισμα βάσει κωδικού κατάστασης HTTP

```bash
# Συμπερίληψη κωδικού κατάστασης
katana -u https://example.com -mc 200,301,302

# Εξαίρεση κωδικού κατάστασης
katana -u https://example.com -fc 404,500
```

### Προσαρμοσμένες κεφαλίδες HTTP

```bash
# Προσθήκη κεφαλίδας HTTP
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Πολλαπλές κεφαλίδες HTTP
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Χρήση ενδιάμεσου διακομιστή (proxy)

```bash
# Proxy HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# Χρήση με Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `katana -u URL` | Εξερεύνηση ενός URL |
| `katana -list file.txt` | Εξερεύνηση από αρχείο στόχων |
| `katana -u URL -d 5` | Ορισμός βάθους 5 |
| `katana -u URL -headless` | Ενεργοποίηση Headless |
| `katana -u URL -o out.txt` | Αποθήκευση αποτελεσμάτων |

### Επιλογές ορίων εξερεύνησης

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-fs dn` | Ονόματα εντός του αντίστοιχου πεδίου domain |
| `-fs rdn` | Βασικό domain |
| `-fs fqdn` | Πλήρες όνομα domain |
| `-cs depth-first` | Εξερεύνηση πρώτα σε βάθος |
| `-cs breadth-first` | Εξερεύνηση πρώτα ανά επίπεδο |

### Επιλογές απόδοσης

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-c 50` | Αριθμός ταυτόχρονων εργασιών |
| `-p 10` | Παράλληλη επεξεργασία στόχων ή εργασιών |
| `-rl 100` | Όριο ρυθμού αιτημάτων |
| `-timeout 30` | Χρονικό όριο αιτήματος |

### Επιλογές φίλτρων

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-em js,php` | Συμπερίληψη επεκτάσεων |
| `-ef png,jpg` | Εξαίρεση επεκτάσεων |
| `-mc 200` | Συμπερίληψη κωδικών κατάστασης |
| `-fc 404` | Εξαίρεση κωδικών κατάστασης |

---

<a id="section-08"></a>

## 🎯 Ροές εργασίας Bug Bounty

### Βασική εξερεύνηση

```bash
# Σύντομη εξερεύνηση
katana -u https://target.com -d 3 -o urls.txt
```

### Εκτενής εξερεύνηση

```bash
# Εξερεύνηση μεγαλύτερου βάθους με browser
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Αναζήτηση παραμέτρων

```bash
# Αναζήτηση URL με παραμέτρους
katana -u https://target.com -d 3 | grep "?" | sort -u

# Με grep για συγκεκριμένα ονόματα παραμέτρων
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Συνδυασμός με άλλα εργαλεία

```bash
# Συνδυασμός Katana και httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Συνδυασμός Katana και Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Ολοκληρωμένη αλυσίδα εντολών
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Εξερεύνηση με ταυτοποίηση

```bash
# Με cookie συνεδρίας
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Αποθετήριο Katana στο GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Σχετικοί οδηγοί
- [Μεθοδολογία Bug Bounty](../../Bug-Bounty-Methodology/translations/README.el.md)
- [httpx](../../httpx/translations/README.el.md)
- [Nuclei](../../Nuclei/translations/README.el.md)
- [GAU](../../GAU/translations/README.el.md)

---

<p align="center">
  <b>⚔️ Εξερευνήστε τις επιτρεπόμενες διαδρομές!</b><br>
  <i>Katana — εξερεύνηση ιστοτόπων</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
