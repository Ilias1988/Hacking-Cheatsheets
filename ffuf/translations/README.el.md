# 🚀 ffuf — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Δοκιμές διαφορετικών εισόδων για εφαρμογές web
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Εργαλείο δοκιμών εισόδων web, γραμμένο σε Go</b>
</p>

---

## Πριν ξεκινήσετε

Το **ffuf** δοκιμάζει πολλές τιμές σε επιλεγμένο σημείο αιτήματος HTTP. Το **FUZZ** είναι η θέση αντικατάστασης και η **wordlist** η λίστα υποψήφιων τιμών. Χρησιμοποιείται για διαδρομές, παραμέτρους και virtual hosts.

**Βασικοί όροι:** *fuzzing* = συστηματική δοκιμή εισόδων, *matcher* = επιλογή αποκρίσεων που ενδιαφέρουν, *filter* = απόκρυψη αποκρίσεων βάσει κριτηρίων, *calibration* = προσαρμογή στο συνηθισμένο μοτίβο απάντησης, *recursion* = συνέχιση αναζήτησης σε επόμενα επίπεδα.

Πρώτα δείτε πώς απαντά η εφαρμογή σε ανύπαρκτη διαδρομή. Μετά επιλέξτε μικρή λίστα, φίλτρα και επιτρεπόμενο ρυθμό. Μια διαφορετική απόκριση είναι στοιχείο για διερεύνηση, όχι αυτόματη απόδειξη ευπάθειας.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Δοκιμές διαδρομών καταλόγων](#section-04)
- [Δοκιμές υποτομέων και εικονικών ιστοτόπων](#section-05)
- [Δοκιμές ονομάτων και τιμών παραμέτρων](#section-06)
- [Δοκιμές δεδομένων POST](#section-07)
- [Εξαίρεση και επιλογή αποκρίσεων](#section-08)
- [Προχωρημένες δυνατότητες](#section-09)
- [Παραδείγματα σεναρίων ελέγχου](#section-10)
- [Γρήγορη αναφορά](#section-11)
- [Συμβουλές και καλές πρακτικές](#section-12)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το ffuf** (Fuzz Faster U Fool) είναι εργαλείο Go για δοκιμές πολλών τιμών σε αιτήματα web. Χρησιμοποιείται για αναζήτηση διαδρομών, εικονικών ιστοτόπων και παραμέτρων.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Ταχύτητα** | Εργαλείο γραμμένο σε Go με παράλληλες εργασίες |
| **Ευέλικτο** | Δοκιμές διαφορετικών τιμών σε τμήματα αιτήματος HTTP |
| **Φιλτράρισμα** | Πολλές επιλογές εξαίρεσης και επιλογής αποτελεσμάτων |
| **Αναδρομική αναζήτηση** | Υποστήριξη αναδρομικής αναζήτησης |
| **Αποθήκευση αποτελεσμάτων** | Πολλαπλές μορφές αποτελεσμάτων |
| **Απλή κατά το πρωτότυπο** | Σχετικά απλή σύνταξη εντολών |

### Σύγκριση ffuf με άλλα εργαλεία

> **Ενδεικτική σύγκριση:** Οι δυνατότητες εξαρτώνται από έκδοση και λειτουργία. Το Gobuster υποστηρίζει POST fuzzing στη λειτουργία fuzz, ενώ η λειτουργία dir δεν αποτελεί γενικό αναδρομικό crawler. Οι απόλυτοι χαρακτηρισμοί του πρωτοτύπου χρειάζονται αυτή τη διευκρίνιση. Πηγή: [επίσημο Gobuster](https://github.com/OJ/gobuster).


| Χαρακτηριστικό | ffuf | Gobuster | dirb |
| --------- | ------ | ---------- | ------ |
| **Ταχύτητα** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Ευελιξία** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **Δοκιμές δεδομένων POST** | ✅ Ναι | ✅ Στη λειτουργία fuzz | ❌ Όχι κατά το πρωτότυπο |
| **Αναδρομική αναζήτηση** | ✅ Ναι | ⚠️ Δεν παρέχεται γενική αναδρομή στη λειτουργία dir | ✅ Ναι |
| **Φίλτρα** | Πολλοί | Περιορισμένες δυνατότητες σύμφωνα με το πρωτότυπο | Περιορισμένες δυνατότητες σύμφωνα με το πρωτότυπο |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

### Εγκατάσταση μέσω Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Λήψη έτοιμου εκτελέσιμου
```bash
# Λήψη από τις εκδόσεις του έργου
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Επιβεβαίωση εγκατάστασης
```bash
ffuf -V
ffuf -h
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### Το σημείο αντικατάστασης FUZZ

Η λέξη **FUZZ** δείχνει στο ffuf σε ποιο σημείο θα εισάγει κάθε τιμή της λίστας.

```bash
# Μέσα στη διαδρομή URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# Στο όνομα υποτομέα
ffuf -u https://FUZZ.target.com -w wordlist.txt

# Στην τιμή παραμέτρου
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# Μέσα σε δεδομένα POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | URL στόχου με σημείο αντικατάστασης FUZZ |
| `-w` | Διαδρομή αρχείου λίστας λέξεων |
| `-o` | Αρχείο εξόδου |
| `-of` | Μορφή αποτελεσμάτων, όπως JSON, CSV ή HTML |
| `-t` | Παράλληλες εργασίες, με ενδεικτική προεπιλογή 40 στο πρωτότυπο |
| `-p` | Καθυστέρηση μεταξύ αιτημάτων |
| `-r` | Ακολούθηση ανακατευθύνσεων |
| `-v` | Αναλυτική έξοδος |
| `-s` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |

---

<a id="section-04"></a>

## 📁 Δοκιμές διαδρομών καταλόγων

### Βασικές δοκιμές ονομάτων καταλόγων

```bash
# Βασικές δοκιμές ονομάτων καταλόγων
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Με επεκτάσεις αρχείων
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Πολλαπλές επεκτάσεις αρχείων
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Αναδρομική αναζήτηση

```bash
# Ενεργοποίηση αναδρομικής αναζήτησης
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Βάθος αναδρομικής αναζήτησης
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Αναδρομική αναζήτηση με συγκεκριμένη στρατηγική
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Φιλτράρισμα βάσει κωδικού κατάστασης HTTP

```bash
# Συμπερίληψη μόνο αποκρίσεων 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Συμπερίληψη πολλών κωδικών κατάστασης
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Εξαίρεση 404· οι ενεργές συνθήκες επιλογής εξακολουθούν να ισχύουν
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Αρχική επιλογή όλων των αποκρίσεων και εξαίρεση 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Συνήθεις λίστες λέξεων

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="section-05"></a>

## 🌐 Δοκιμές υποτομέων και εικονικών ιστοτόπων

### Αναζήτηση εικονικών ιστοτόπων (VHosts)

```bash
# Βασικές δοκιμές ονομάτων εικονικών ιστοτόπων
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Εξαίρεση μεγέθους απόκρισης, εφόσον έχει μετρηθεί κατάλληλη τιμή αναφοράς
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Με συγκεκριμένη λίστα λέξεων
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Δοκιμές ονομάτων υποτομέων μέσω URL

> **Το -r ακολουθεί redirects:** Δεν ορίζει DNS resolver. Η αντικατάσταση στον Host header διαφέρει από αντικατάσταση domain στο URL ως προς DNS, TLS/SNI και δρομολόγηση. Δείτε τις [επίσημες επιλογές ffuf](https://github.com/ffuf/ffuf).


```bash
# Όνομα υποτομέα μέσα στο URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Παράδειγμα με -r· στο ffuf σημαίνει ακολούθηση ανακατευθύνσεων, όχι επιλογή DNS resolver
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Συνήθεις λίστες ονομάτων υποτομέων

```bash
# Λίστες ονομάτων DNS από SecLists
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="section-06"></a>

## 🔍 Δοκιμές ονομάτων και τιμών παραμέτρων

### Δοκιμές παραμέτρων GET

```bash
# Δοκιμές διαφορετικών ονομάτων παραμέτρου
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Δοκιμές διαφορετικών τιμών παραμέτρου
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Πολλαπλές παράμετροι
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Αναζήτηση παραμέτρων

```bash
# Αναζήτηση μη εμφανών παραμέτρων
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="section-07"></a>

## 📝 Δοκιμές δεδομένων POST

### Βασικές δοκιμές POST

```bash
# Αίτημα POST με δεδομένα φόρμας
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# Αίτημα POST με καθορισμένο τύπο περιεχομένου
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Δοκιμές POST με JSON

```bash
# Δεδομένα POST σε JSON
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Πολλαπλές λίστες στοιχείων σύνδεσης

> **Συνδυασμοί:** Το clusterbomb δοκιμάζει το καρτεσιανό γινόμενο των λιστών, όχι μόνο αντίστοιχα ζεύγη χρήστη/κωδικού. Αυτό μπορεί να αυξήσει πολύ τα αιτήματα και να επηρεάσει λογαριασμούς.


```bash
# Δοκιμές συνδυασμών ονόματος χρήστη και κωδικού
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Λειτουργία clusterbomb, όλοι οι συνδυασμοί λιστών
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="section-08"></a>

## 🎯 Εξαίρεση και επιλογή αποκρίσεων

> **Μετρήστε τη συνήθη απόκριση:** Το μέγεθος 1234 δεν είναι γενικός κανόνας. Wildcard σελίδες μπορεί να απαντούν 200 για ανύπαρκτους πόρους. Η αφαίρεση 404 δεν σημαίνει αυτόματα αντιστοίχιση όλων των υπόλοιπων status codes· εξετάστε matcher και filter μαζί.


### Επιλογές εξαίρεσης αποτελεσμάτων

| Επιλογή | Περιγραφή | Παράδειγμα |
| -------- | ------------- | --------- |
| `-fc` | Εξαίρεση βάσει κωδικού κατάστασης | `-fc 404,403` |
| `-fs` | Εξαίρεση βάσει μεγέθους απόκρισης | `-fs 1234` |
| `-fw` | Εξαίρεση βάσει πλήθους λέξεων | `-fw 100` |
| `-fl` | Εξαίρεση βάσει πλήθους γραμμών | `-fl 50` |
| `-ft` | Εξαίρεση βάσει χρόνου σε χιλιοστά δευτερολέπτου | `-ft 5000` |
| `-fr` | Εξαίρεση βάσει κανονικής έκφρασης | `-fr "error"` |

### Επιλογές συμπερίληψης αποτελεσμάτων

| Επιλογή | Περιγραφή | Παράδειγμα |
| -------- | ------------- | --------- |
| `-mc` | Συμπερίληψη κωδικών κατάστασης | `-mc 200,301` |
| `-ms` | Συμπερίληψη βάσει μεγέθους απόκρισης | `-ms 1234` |
| `-mw` | Συμπερίληψη βάσει πλήθους λέξεων | `-mw 100` |
| `-ml` | Συμπερίληψη βάσει πλήθους γραμμών | `-ml 50` |
| `-mt` | Συμπερίληψη βάσει χρόνου σε χιλιοστά δευτερολέπτου | `-mt 5000` |
| `-mr` | Συμπερίληψη βάσει κανονικής έκφρασης | `-mr "success"` |

### Παραδείγματα φίλτρων

```bash
# Εξαίρεση αποκρίσεων 404 και 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Εξαίρεση μεγέθους απόκρισης βάσει μέτρησης της αρχικής σελίδας
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Εξαίρεση βάσει πλήθους λέξεων
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Επιλογή όλων και εξαίρεση συγκεκριμένων τιμών, όπου η επιλογή υποστηρίζεται
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Αυτόματη προσαρμογή φίλτρων (Auto-Calibration)

```bash
# Αυτόματη προσαρμογή φίλτρων
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Προσαρμογή φίλτρων με συγκεκριμένη δοκιμαστική τιμή
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="section-09"></a>

## ⚡ Προχωρημένες δυνατότητες

### Περιορισμός ρυθμού

```bash
# Αιτήματα ανά δευτερόλεπτο
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Καθυστέρηση μεταξύ αιτημάτων
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Τυχαία καθυστέρηση μεταξύ αιτημάτων
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Παράλληλες εργασίες (threads)

```bash
# Ορισμός παράλληλων εργασιών, με προεπιλογή 40 κατά το πρωτότυπο
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Μείωση παραλληλίας, χωρίς εγγύηση αποφυγής καταγραφής
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Κεφαλίδες HTTP

```bash
# Προσαρμοσμένη κεφαλίδα
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Πολλαπλές κεφαλίδες HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie συνεδρίας
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# Proxy HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Αποθήκευση αποτελεσμάτων

```bash
# Έξοδος JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Έξοδος CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Έξοδος HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Συνδυασμός κανονικής εξόδου, XML και grepable
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Χρονικά όρια και επαναλήψεις

> **Διόρθωση περιγραφής:** Το -se σταματά σε περιπτώσεις spurious errors, όχι απλώς στο πρώτο 403. Το -sf αφορά κυριαρχία αποκρίσεων 403. Ελέγξτε τη βοήθεια της έκδοσής σας. Λιγότερα threads δεν εγγυώνται αφάνεια ή παράκαμψη WAF.


```bash
# Χρονικό όριο αιτήματος
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Παράδειγμα με -se· δεν σημαίνει γενικά διακοπή στο πρώτο 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Παράλειψη ελέγχου πιστοποιητικού TLS
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="section-10"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Βασική αναζήτηση καταλόγων
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Παράδειγμα 2: Αναζήτηση αρχείων PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Παράδειγμα 3: Αναζήτηση υποτομέων
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Παράδειγμα 4: Εντοπισμός διαδρομών API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Παράδειγμα 5: Αναζήτηση παραμέτρων
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Παράδειγμα 6: Δοκιμές σύνδεσης με λίστα κωδικών
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Παράδειγμα 7: Μέσω proxy Burp
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Παράδειγμα 8: Αναδρομική αναζήτηση
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Παράδειγμα 9: Έλεγχος με περιορισμό ρυθμού
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Παράδειγμα 10: Συνδυασμός επιλογών Bug Bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Αναζήτηση καταλόγων | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Με επεκτάσεις αρχείων | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Αναζήτηση υποτομέων | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| Δοκιμές δεδομένων POST | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Εξαίρεση κωδικού 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Εξαίρεση βάσει μεγέθους | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Αυτόματη προσαρμογή φίλτρων | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Αποθήκευση αποτελεσμάτων | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | URL με σημείο FUZZ |
| `-w` | Λίστα λέξεων |
| `-e` | Επεκτάσεις αρχείων |
| `-t` | Παράλληλες εργασίες (threads) |
| `-mc` | Συμπερίληψη κωδικών |
| `-fc` | Εξαίρεση κωδικών |
| `-fs` | Εξαίρεση μεγέθους |
| `-ac` | Αυτόματη προσαρμογή φίλτρων |
| `-r` | Ακολούθηση ανακατευθύνσεων |
| `-o` | Αρχείο εξόδου |

---

<a id="section-12"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

1. **Εξετάστε πρώτα την αυτόματη προσαρμογή φίλτρων**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Χρησιμοποιήστε σχετικές επεκτάσεις αρχείων**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Δοκιμάστε συνήθεις επεκτάσεις αντιγράφων ασφαλείας**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Περιορισμός ρυθμού, χωρίς εγγύηση παράκαμψης WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Συμβουλές απόδοσης

```bash
# Παράδειγμα αυξημένης παραλληλίας· δεν προϋποθέτει ότι απουσιάζει WAF
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Περιορισμένος ρυθμός, χωρίς εγγύηση αποφυγής καταγραφής
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

<a id="section-13"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το ffuf μόνο για **εξουσιοδοτημένους ελέγχους**.
> 
> - ✅ Χρήση σε προγράμματα για τα οποία έχετε άδεια ελέγχου
> - ✅ Χρήση σε εξουσιοδοτημένα προγράμματα Bug Bounty
> - ✅ Τηρείτε τα όρια ρυθμού και το επιτρεπόμενο πεδίο
> - ❌ Μην εκτελείτε fuzzing χωρίς εξουσιοδότηση
> - ❌ Μην προκαλείτε άρνηση υπηρεσίας
> 
> **Η μη εξουσιοδοτημένη σάρωση μπορεί να επιφέρει νομικές κυρώσεις.**

---

<a id="section-14"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο ffuf στο GitHub](https://github.com/ffuf/ffuf)
- [Τεκμηρίωση ffuf](https://github.com/ffuf/ffuf/wiki)

### Λίστες λέξεων (wordlists)
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Λίστες λέξεων Assetnote](https://wordlists.assetnote.io/)

### Σχετικοί οδηγοί
- [Nuclei](../../Nuclei/translations/README.el.md)
- [Gobuster](../../Gobuster/translations/README.el.md)
- [Nmap](../../Nmap/translations/README.el.md)

---

<p align="center">
  <b>🚀 Οργανώστε τις δοκιμές εισόδων με ffuf!</b><br>
  <i>Συγκρίνετε αποκρίσεις και επιβεβαιώστε τα αποτελέσματα</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
