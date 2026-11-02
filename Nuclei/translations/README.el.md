# 🎯 Nuclei — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Έλεγχοι ευπαθειών βάσει προτύπων
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Έλεγχοι βάσει προτύπων για ροές εργασίας Bug Bounty</b>
</p>

---

## Πριν ξεκινήσετε

Το **Nuclei** εκτελεί ελέγχους που περιγράφονται σε **templates** (πρότυπα). Ένα template ορίζει αιτήματα και συνθήκες που θεωρούνται αποτέλεσμα. Χρειάζεται εξέταση αιτήματος, απόκρισης και πραγματικού αντίκτυπου πριν επιβεβαιώσετε ευπάθεια.

**Βασικοί όροι:** *matcher* = συνθήκη αντιστοίχισης, *extractor* = κανόνας εξαγωγής τιμών, *tag* = ετικέτα επιλογής, *severity* = δηλωμένη σοβαρότητα, *workflow* = ακολουθία ελέγχων, *OOB callback* = επικοινωνία προς εξωτερική υπηρεσία παρατήρησης.

Ξεκινήστε με λίγα, επιλεγμένα templates σε εργαστήριο ή εγκεκριμένο στόχο. Εξετάστε τι κάνουν πριν τα εκτελέσετε. Διαδρομές και επιλογές του πρωτοτύπου μπορεί να χρειάζονται προσαρμογή.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Κατηγορίες προτύπων](#section-04)
- [Έλεγχος στόχων](#section-05)
- [Επιλογή προτύπων](#section-06)
- [Επιλογές αποτελεσμάτων](#section-07)
- [Προχωρημένες δυνατότητες](#section-08)
- [Προσαρμοσμένα πρότυπα](#section-09)
- [Ροή εργασίας Bug Bounty](#section-10)
- [Παραδείγματα σεναρίων ελέγχου](#section-11)
- [Γρήγορη αναφορά](#section-12)
- [Συμβουλές και καλές πρακτικές](#section-13)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-14)
- [Πηγές](#section-15)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

> **Αριθμοί του πρωτοτύπου:** Τα πλήθη templates και οι συγκρίσεις χαρακτηριστικών παρακάτω είναι ενδεικτικά στοιχεία του αγγλικού οδηγού, όχι απογραφή ή μέτρηση της εγκατεστημένης έκδοσης.


**Το Nuclei** είναι εργαλείο της ProjectDiscovery που εκτελεί ελέγχους με βάση πρότυπα. Κάθε πρότυπο περιγράφει αιτήματα και συνθήκες αναγνώρισης ενός πιθανού ευρήματος.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **9000+ Πρότυπα** | Γνωστές ευπάθειες, σελίδες διαχείρισης, λανθασμένες ρυθμίσεις και takeover |
| **Παράλληλη και αποδοτική εκτέλεση** | Παράλληλοι έλεγχοι με περιορισμό ρυθμού |
| **Βασισμένο σε πρότυπα (templates)** | Πρότυπα YAML που περιγράφουν τους ελέγχους |
| **Ανάπτυξη με συμμετοχή της κοινότητας** | Πρότυπα που ενημερώνονται συχνά |
| **Πολλαπλά πρωτόκολλα** | HTTP, DNS, TCP, SSL/TLS και λειτουργίες browser χωρίς γραφικό περιβάλλον |
| **Εύκολη ενσωμάτωση σε ροές εργασίας** | Συνεργασία με εργαλεία όπως httpx και subfinder |

### Τύποι προτύπων

| Τύπος | Περιγραφή | Πλήθος |
| ------ | ------------- | ------- |
| **CVE** | Γνωστές ευπάθειες | 2000+ |
| **Εκτεθειμένες σελίδες διαχείρισης** | Περιοχές διαχείρισης και πίνακες ελέγχου | 500+ |
| **Λανθασμένες ρυθμίσεις** | Λανθασμένες ρυθμίσεις ασφάλειας | 300+ |
| **Προεπιλεγμένα στοιχεία σύνδεσης** | Προεπιλεγμένα στοιχεία σύνδεσης | 200+ |
| **Πιθανές αναλήψεις υποτομέων** | Πιθανή ανάληψη υποτομέα | 100+ |
| **Τεχνολογίες** | Αναγνώριση τεχνολογιών | 1000+ |
| **Εκτεθειμένα δεδομένα** | Έκθεση ευαίσθητων αρχείων | 500+ |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Έκδοση και ενημέρωση:** Το -ut ενημερώνει templates, όχι το εκτελέσιμο Nuclei. Τα ονόματα με v3.x.x είναι placeholders. Επιβεβαιώστε επιλογές και θέση templates από το [επίσημο Nuclei](https://github.com/projectdiscovery/nuclei).


### Εγκατάσταση μέσω Go
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Λήψη έτοιμου εκτελέσιμου
```bash
# Λήψη από τις εκδόσεις του έργου
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Ενημέρωση προτύπων
```bash
# Ενημέρωση προτύπων με -ut, όχι του ίδιου του εκτελέσιμου nuclei
nuclei -ut

# Ενημέρωση προτύπων
nuclei -update-templates
```

### Επιβεβαίωση εγκατάστασης
```bash
nuclei -version
nuclei -tl              # Προβολή προτύπων
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
nuclei [options] -target <url>
```

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | Ένα URL στόχου |
| `-l` | Αρχείο λίστας URL |
| `-t` | Πρότυπα προς εκτέλεση |
| `-tags` | Πρότυπα με συγκεκριμένες ετικέτες |
| `-severity` | Επιλογή βάσει σοβαρότητας |
| `-o` | Αρχείο εξόδου |
| `-silent` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |
| `-v` | Αναλυτική έξοδος |

### Πρώτη γνωριμία
```bash
# Έλεγχος ενός στόχου
nuclei -u https://example.com

# Έλεγχος λίστας στόχων
nuclei -l urls.txt

# Έλεγχος με συγκεκριμένα πρότυπα
nuclei -u https://example.com -t cves/

# Επιλογή ελέγχων βάσει ετικετών
nuclei -u https://example.com -tags cve,rce
```

---

<a id="section-04"></a>

## 📂 Κατηγορίες προτύπων

> **Διαδρομές του πρωτοτύπου:** Κατηγορίες όπως cves ή exposed-panels μπορεί να έχουν μεταφερθεί σε υποφακέλους, π.χ. http. Ελέγξτε το εγκατεστημένο δέντρο και τα IDs. Οι αριθμοί templates δεν είναι απογραφή της σημερινής έκδοσης.


### Πρότυπα ελέγχου γνωστών ευπαθειών CVE

```bash
# Όλα τα επιλεγμένα πρότυπα CVE
nuclei -u target.com -t cves/

# Πρότυπα CVE συγκεκριμένου έτους
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Πρότυπα CVE με ένδειξη κρίσιμης σοβαρότητας
nuclei -u target.com -t cves/ -severity critical

# Συγκεκριμένο αναγνωριστικό CVE
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Εκτεθειμένες σελίδες διαχείρισης

```bash
# Αναζήτηση σελίδων διαχείρισης
nuclei -u target.com -t exposed-panels/

# Συγκεκριμένοι τύποι σελίδων διαχείρισης
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Λανθασμένες ρυθμίσεις

```bash
# Έλεγχοι λανθασμένων ρυθμίσεων
nuclei -u target.com -t misconfiguration/

# Συγκεκριμένες κατηγορίες
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Προεπιλεγμένα στοιχεία σύνδεσης

```bash
# Έλεγχοι προεπιλεγμένων στοιχείων σύνδεσης
nuclei -u target.com -t default-logins/

# Συγκεκριμένοι κατασκευαστές
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Έλεγχος πιθανής ανάληψης υποτομέα (Subdomain Takeover)

```bash
# Έλεγχοι πιθανής ανάληψης υποτομέων
nuclei -u target.com -t takeovers/

# Συγκεκριμένοι πάροχοι
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Αναγνώριση τεχνολογιών

```bash
# Αναγνώριση τεχνολογιών
nuclei -u target.com -t technologies/

# Αναγνώριση συστήματος διαχείρισης περιεχομένου
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Εκτεθειμένα δεδομένα

```bash
# Έκθεση ευαίσθητων αρχείων
nuclei -u target.com -t exposures/

# Συγκεκριμένες κατηγορίες έκθεσης δεδομένων
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

<a id="section-05"></a>

## 🎯 Έλεγχος στόχων

### Ένας στόχος

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Πολλαπλοί στόχοι

```bash
# Από αρχείο
nuclei -l targets.txt

# Ανάγνωση από την κανονική είσοδο προηγούμενης εντολής
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Πολλά URL στην ίδια γραμμή εντολής
nuclei -u https://site1.com -u https://site2.com
```

### Συνδυασμός με άλλα εργαλεία

```bash
# Συνδυασμός με Subfinder για ονόματα υποτομέων
subfinder -d example.com | nuclei

# Συνδυασμός με httpx για προσβάσιμες υπηρεσίες
subfinder -d example.com | httpx | nuclei

# Συνδυασμός με Katana για URL εξερεύνησης
katana -u https://example.com | nuclei

# Ολοκληρωμένη αλυσίδα συλλογής πληροφοριών
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="section-06"></a>

## 🏷️ Επιλογή προτύπων

### Με βάση τη διαδρομή προτύπου

```bash
# Ένα πρότυπο
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Κατάλογος προτύπων
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Πολλαπλοί κατάλογοι
nuclei -u target.com -t cves/ -t exposed-panels/

# Προσαρμοσμένη διαδρομή προτύπων
nuclei -u target.com -t /path/to/custom/templates/
```

### Με βάση ετικέτες

```bash
# Μία ετικέτα
nuclei -u target.com -tags cve

# Πολλές ετικέτες, με συνθήκη Ή
nuclei -u target.com -tags cve,rce,sqli

# Συνήθεις ετικέτες
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Με βάση σοβαρότητα

```bash
# Ένα επίπεδο σοβαρότητας
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Πολλά επίπεδα σοβαρότητας
nuclei -u target.com -severity critical,high

# Εξαίρεση επιπέδων σοβαρότητας
nuclei -u target.com -exclude-severity info,low
```

### Εξαίρεση προτύπων

```bash
# Εξαίρεση συγκεκριμένων προτύπων
nuclei -u target.com -exclude-templates cves/2020/

# Εξαίρεση βάσει ετικετών
nuclei -u target.com -exclude-tags dos,fuzz

# Εξαίρεση βάσει αναγνωριστικού
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

<a id="section-07"></a>

## 📤 Επιλογές αποτελεσμάτων

### Μορφές αποτελεσμάτων

```bash
# Συνήθης έξοδος κειμένου
nuclei -u target.com -o results.txt

# Έξοδος JSON
nuclei -u target.com -json -o results.json

# Έξοδος JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Έξοδος Markdown
nuclei -u target.com -markdown-export report/
```

### Φιλτράρισμα εξόδου

```bash
# Περιορισμένη έξοδος αποτελεσμάτων
nuclei -u target.com -silent

# Χωρίς χρώματα στην έξοδο
nuclei -u target.com -nc

# Αναλυτική έξοδος
nuclei -u target.com -v

# Διαγνωστική έξοδος
nuclei -u target.com -debug
```

### Επιλογές εξαγωγής

```bash
# Εξαγωγή SARIF για εργαλεία αξιολόγησης
nuclei -u target.com -sarif-export results.sarif

# Αναφορά Markdown
nuclei -u target.com -me reports/
```

---

<a id="section-08"></a>

## ⚡ Προχωρημένες δυνατότητες

> **Επιλογή ελέγχων:** Headless, default-login και workflows μπορεί να κάνουν περισσότερα από ανάγνωση σελίδας. Εξετάστε αιτήματα και επιτρεπόμενες τεχνικές. Ένα OOB callback χρειάζεται ερμηνεία. Επιλογές JSON/JSONL και εξαγωγής αναφορών εξαρτώνται από έκδοση.


### Περιορισμός ρυθμού

```bash
# Αιτήματα ανά δευτερόλεπτο
nuclei -u target.com -rate-limit 100

# Μέγεθος ομάδας στόχων
nuclei -u target.com -bulk-size 25

# Αριθμός ταυτόχρονων εργασιών
nuclei -u target.com -c 50

# Παράλληλη επεξεργασία προτύπων
nuclei -u target.com -rl 100 -c 50
```

### Χρήση ενδιάμεσου διακομιστή (proxy)

```bash
# Proxy HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# Ενδιάμεσος διακομιστής SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Proxy του Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Ταυτοποίηση

```bash
# Ταυτοποίηση μέσω κεφαλίδας HTTP
nuclei -u target.com -H "Authorization: Bearer token123"

# Ταυτοποίηση μέσω cookies
nuclei -u target.com -H "Cookie: session=abc123"

# Πολλαπλές κεφαλίδες HTTP
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Έλεγχοι εξωτερικών αλληλεπιδράσεων με Interactsh

```bash
# Χρήση Interactsh για έλεγχο εξωτερικών αλληλεπιδράσεων
nuclei -u target.com -interactsh-server https://interact.sh

# Δικός σας διακομιστής Interactsh
nuclei -u target.com -iserver https://my-interactsh.com
```

### Λειτουργία browser χωρίς γραφικό περιβάλλον (Headless)

```bash
# Ενεργοποίηση browser χωρίς γραφικό περιβάλλον
nuclei -u target.com -headless

# Πρότυπα που απαιτούν browser χωρίς γραφικό περιβάλλον
nuclei -u target.com -t headless/
```

### Ροές ελέγχων

```bash
# Εκτέλεση ροής ελέγχων
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Προβολή αρχείων ροών ελέγχου
ls ~/.local/nuclei-templates/workflows/
```

---

<a id="section-09"></a>

## 📝 Προσαρμοσμένα πρότυπα

### Δομή προτύπου

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Παραδείγματα προτύπων

#### Αναγνώριση συγκεκριμένης σελίδας

> **Το 200 δεν αρκεί:** Το παράδειγμα ελέγχει επιτυχή απόκριση, όχι ότι το περιεχόμενο είναι admin panel ή υπάρχει ευπάθεια. Χρειάζεται κριτήριο περιεχομένου και έλεγχος γενικών σελίδων απάντησης.

```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Έλεγχος κεφαλίδας

> **Το YAML χρειάζεται διόρθωση:** Έχει δύο κλειδιά matchers στο ίδιο αντικείμενο. Ο parser μπορεί να το απορρίψει ή να χαθεί ο ένας ορισμός. Χρειάζεται ενιαία σωστή δομή και επικύρωση πριν χρησιμοποιηθεί. Το δείγμα διατηρείται αυτούσιο για πιστότητα στο πρωτότυπο.

```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Έλεγχος εγκυρότητας προτύπου

```bash
# Έλεγχος σύνταξης προτύπου
nuclei -validate -t my-template.yaml

# Εργαστηριακή δοκιμή προτύπου
nuclei -u target.com -t my-template.yaml -debug
```

---

<a id="section-10"></a>

## 🔄 Ροή εργασίας Bug Bounty

### Ολοκληρωμένη αλυσίδα συλλογής πληροφοριών

```bash
# Βήμα 1: Αναζήτηση υποτομέων
subfinder -d target.com -o subs.txt

# Βήμα 2: Δοκιμές προσβασιμότητας HTTP, όχι απλώς επίλυση DNS
cat subs.txt | httpx -o live.txt

# Βήμα 3: Εκτέλεση Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Παράδειγμα μίας γραμμής
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Έλεγχοι με σειρά προτεραιότητας

```bash
# Φάση 1: Επιλογή κρίσιμης σοβαρότητας
nuclei -l targets.txt -severity critical -o critical.txt

# Φάση 2: Υψηλή σοβαρότητα
nuclei -l targets.txt -severity high -o high.txt

# Φάση 3: Πρότυπα CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# Φάση 4: Ευρύτερο σύνολο ελέγχων
nuclei -l targets.txt -o all.txt
```

### Στοχευμένοι έλεγχοι

```bash
# Στόχοι WordPress
nuclei -l wp-sites.txt -tags wordpress

# Διαδρομές API
nuclei -l apis.txt -tags api

# Σελίδες σύνδεσης
nuclei -l logins.txt -tags login,default-login
```

---

<a id="section-11"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Εκτενής έλεγχος
```bash
nuclei -u https://target.com -o results.txt
```

### Παράδειγμα 2: Επιλογή ελέγχων CVE υψηλής σοβαρότητας
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Παράδειγμα 3: Σύντομος έλεγχος Bug Bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Παράδειγμα 4: Έλεγχος πιθανής ανάληψης υποτομέα
```bash
cat subs.txt | nuclei -t takeovers/
```

### Παράδειγμα 5: Αναγνώριση τεχνολογιών
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Παράδειγμα 6: Μέσω proxy Burp
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Παράδειγμα 7: Σάρωση με ταυτοποίηση
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Παράδειγμα 8: Έλεγχος με περιορισμό ρυθμού
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Παράδειγμα 9: Έξοδος JSON για επεξεργασία
```bash
nuclei -u https://target.com -json -o results.json
```

### Παράδειγμα 10: Ολοκληρωμένη αλυσίδα εντολών
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

<a id="section-12"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Βασική σάρωση | `nuclei -u target.com` |
| Με πρότυπα | `nuclei -u target.com -t cves/` |
| Με ετικέτες | `nuclei -u target.com -tags cve,rce` |
| Με σοβαρότητα | `nuclei -u target.com -severity critical` |
| Από λίστα | `nuclei -l urls.txt` |
| Ενημέρωση προτύπων | `nuclei -ut` |
| Περιορισμένη έξοδος | `nuclei -u target.com -silent` |
| Έξοδος JSON | `nuclei -u target.com -json -o out.json` |

### Συνήθεις ετικέτες

| Ετικέτα | Περιγραφή |
| ----- | ------------- |
| `cve` | Γνωστές ευπάθειες με αναγνωριστικό CVE |
| `rce` | Απομακρυσμένη εκτέλεση κώδικα |
| `sqli` | Έγχυση SQL |
| `xss` | Εκτέλεση μη έμπιστου περιεχομένου στον browser (XSS) |
| `ssrf` | Πλαστογράφηση αιτημάτων από τον server (SSRF) |
| `lfi` | Συμπερίληψη τοπικού αρχείου (LFI) |
| `panel` | Περιοχές διαχείρισης |
| `takeover` | Πιθανή ανάληψη υποτομέα |

### Επίπεδα σοβαρότητας

| Σοβαρότητα | Χρώμα | Προτεραιότητα |
| ---------- | ------- | ---------- |
| `critical` | 🔴 Κόκκινο | Άμεση αξιολόγηση |
| `high` | 🟠 Πορτοκαλί | Υψηλή |
| `medium` | 🟡 Κίτρινο | Μέση |
| `low` | 🟢 Πράσινο | Χαμηλή |
| `info` | 🔵 Μπλε | Πληροφοριακή |

---

<a id="section-13"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές απόδοσης

```bash
# Ενδεικτικές ρυθμίσεις μεγάλου φόρτου, όχι καθολικά βέλτιστες
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Περιορισμένη έξοδος για διοχέτευση σε επόμενη εντολή
nuclei -l targets.txt -silent -o results.txt
```

### Συμβουλές για Bug Bounty

1. **Ξεκινήστε από τα ευρήματα κρίσιμης ή υψηλής σοβαρότητας**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Εξετάστε απλούς ελέγχους με σαφή τεκμηρίωση**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Χρησιμοποιήστε ενημερωμένα πρότυπα**
   ```bash
   nuclei -ut  # Ενημέρωση προτύπων πριν από τον έλεγχο
   ```

4. **Περιορίστε τον ρυθμό για σταθερή λειτουργία**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Παραδείγματα μεταβολής ιχνών του πρωτοτύπου

```bash
# Περιορισμός ρυθμού και παραλληλίας
nuclei -l targets.txt -rl 10 -c 2

# Μέσω ενδιάμεσου διακομιστή (proxy)
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

<a id="section-14"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το Nuclei μόνο για **εξουσιοδοτημένους ελέγχους**.
> 
> - ✅ Χρήση σε προγράμματα για τα οποία έχετε άδεια ελέγχου
> - ✅ Χρήση σε εξουσιοδοτημένα προγράμματα Bug Bounty
> - ✅ Χρήση στα δικά σας συστήματα
> - ❌ Μη σαρώνετε χωρίς εξουσιοδότηση
> - ❌ Τηρείτε τα όρια ρυθμού και το επιτρεπόμενο πεδίο
> 
> **Η μη εξουσιοδοτημένη σάρωση μπορεί να επιφέρει νομικές κυρώσεις.**

---

<a id="section-15"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο Nuclei στο GitHub](https://github.com/projectdiscovery/nuclei)
- [Πρότυπα Nuclei](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Συνοδευτικά έγγραφα και στοιχεία](https://docs.projectdiscovery.io/tools/nuclei/)

### Πηγές προτύπων
- [Πρότυπα της κοινότητας](https://github.com/projectdiscovery/nuclei-templates)
- [Οδηγός δημιουργίας προτύπων](https://docs.projectdiscovery.io/templates/)

### Σχετικοί οδηγοί
- [Nmap](../../Nmap/translations/README.el.md)
- [Burp Suite](../../Burp-Suite/translations/README.el.md)
- [SQLMap](../../SQLMap/translations/README.el.md)

---

<p align="center">
  <b>🎯 Εξασκηθείτε στους ελέγχους με πρότυπα!</b><br>
  <i>Συνδυάστε αυτοματοποίηση και χειροκίνητη επιβεβαίωση</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
