# 🌐 Amass — Χαρτογράφηση επιφάνειας έκθεσης

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         Χαρτογράφηση επιφάνειας έκθεσης
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.it.md">🇮🇹 Versione Italiana</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **OWASP Amass** χαρτογραφεί την **attack surface** (επιφάνεια επίθεσης): ονόματα, διευθύνσεις και σχέσεις υποδομών. Συνδυάζει πηγές δεδομένων και, ανάλογα με λειτουργία και έκδοση, ενεργές διαδικασίες ανακάλυψης.

**Βασικοί όροι:** *enumeration* = συστηματική καταγραφή, *ASN* = αριθμός αυτόνομου συστήματος δικτύου, *CIDR* = γραφή εύρους IP, *WHOIS* = πληροφορίες καταχώρισης, *brute force DNS* = δοκιμή υποψήφιων ονομάτων μέσω DNS.

Το πρωτότυπο περιέχει εντολές παλαιότερων εκδόσεων. Επιβεβαιώστε την έκδοση και τη βοήθεια του εγκατεστημένου εργαλείου. Μια σχέση με οργανισμό ή ASN δεν προσθέτει αυτόματα στόχο στο scope.

## 📋 Περιεχόμενα

- [Τι είναι το Amass](#section-01)
- [Εγκατάσταση](#section-02)
- [Λειτουργία συλλογής πληροφοριών](#section-03)
- [Λειτουργία αναζήτησης οργανωτικών υποδομών (Intel)](#section-04)
- [Λειτουργία DNS](#section-05)
- [Ρυθμίσεις](#section-06)
- [Γρήγορη αναφορά](#section-07)
- [Ροή εργασίας Bug Bounty](#section-08)
- [Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Amass

**Το Amass** είναι εργαλείο χαρτογράφησης δικτύων και αναζήτησης εξωτερικών υποδομών:

- 🔍 **Αναζήτηση υποτομέων** — συνδυασμός πολλών πηγών
- 🏢 **Αναζήτηση ASN** — εύρεση πιθανών σχετικών εύρων IP
- 📡 **Πληροφορίες DNS** — συλλογή από πηγές και ενεργοί έλεγχοι
- 🗺️ **Χαρτογράφηση επιφάνειας έκθεσης** — απεικόνιση υποδομών

### Δυνατότητες Amass

| Χαρακτηριστικό | Χρήση και όφελος |
| --------- | --------- |
| 55+ Πηγές δεδομένων | Ευρεία κάλυψη σύμφωνα με το πρωτότυπο |
| Συλλογή από πηγές και ενεργοί έλεγχοι | Επιλογή τρόπου αλληλεπίδρασης και ρυθμού |
| Πληροφορίες ASN και οργανισμών | Αναζήτηση σχετικών υποδομών |
| Βάση δεδομένων σχέσεων | Απεικόνιση σχέσεων μεταξύ υποδομών |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Διαφορετικές γενιές CLI:** Το πρωτότυπο συνδυάζει εγκατάσταση v4 και παραδείγματα άλλων εκδόσεων. Υποεντολές intel/dns, επιλογές enum, JSON και ρυθμίσεις χρειάζονται έλεγχο. Αφετηρία είναι το [επίσημο OWASP Amass](https://github.com/owasp-amass/amass).


```bash
# Εγκατάσταση μέσω Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Εγκατάσταση στο Kali Linux
sudo apt install amass

# Εγκατάσταση μέσω Docker
docker pull caffix/amass

# Επιβεβαίωση εγκατάστασης
amass -version
```

---

<a id="section-03"></a>

## 🔍 Λειτουργία συλλογής πληροφοριών

> **Διαφορετικές λειτουργίες:** Active δεν σημαίνει αυτόματα DNS brute force. Το -r μπορεί να αφορά resolvers και δεν είναι γενικός διακόπτης αναδρομής. Τα παραδείγματα recursion χρειάζονται έλεγχο της συγκεκριμένης έκδοσης.


### Βασική αναζήτηση υποτομέων

```bash
# Συλλογή από τρίτες πηγές χωρίς άμεσο ενεργό έλεγχο στόχου στο συγκεκριμένο βήμα
amass enum -passive -d example.com

# Ενεργοί έλεγχοι· το -active δεν ενεργοποιεί μόνο του κάθε δοκιμή brute force
amass enum -active -d example.com

# Διαδοχικές δοκιμές βάσει λίστας λέξεων
amass enum -active -brute -d example.com -w wordlist.txt
```

### Επιλογές αποτελεσμάτων

```bash
# Αποθήκευση σε αρχείο
amass enum -passive -d example.com -o subdomains.txt

# Έξοδος JSON
amass enum -passive -d example.com -json output.json

# Αποθήκευση σε κατάλογο
amass enum -passive -d example.com -dir ./amass_output/
```

### Πολλαπλοί στόχοι

```bash
# Πολλαπλά domains
amass enum -passive -d example.com -d target.com

# Από αρχείο
amass enum -passive -df domains.txt -o results.txt
```

### Προχωρημένη συλλογή πληροφοριών

```bash
# Ορισμός χρονικού ορίου σε λεπτά
amass enum -passive -d example.com -timeout 30

# Περιορισμός επιλεγμένων πηγών
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Εξαίρεση πηγών
amass enum -passive -d example.com -exclude Google,Bing

# Εμφάνιση πηγών δεδομένων
amass enum -passive -d example.com -src
```

### Συλλογή από πηγές που υποστηρίζουν αναδρομικά ονόματα

```bash
# Παράδειγμα του πρωτοτύπου· ελέγξτε την πραγματική σημασία του -r στο Amass
amass enum -passive -d example.com -r

# Παράδειγμα βάθους αναδρομής· ελέγξτε τη διαθεσιμότητα της επιλογής
amass enum -passive -d example.com -max-depth 3
```

---

<a id="section-04"></a>

## 🏢 Λειτουργία αναζήτησης οργανωτικών υποδομών (Intel)

> **Συσχετίσεις:** Οργανισμός, ASN και εύρος IP είναι στοιχεία προς διασταύρωση. Σε κοινόχρηστη φιλοξενία και cloud δεν αποδεικνύουν αποκλειστική ιδιοκτησία ή άδεια ελέγχου.


> Η λειτουργία Intel αναζητά βασικά domains, ASN και εύρη CIDR που φαίνεται να συνδέονται με οργανισμούς.

### Αναζήτηση υποδομών οργανισμού

```bash
# Με βάση όνομα οργανισμού
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Αντίστροφη αναζήτηση στοιχείων WHOIS
amass intel -whois -d example.com
```

### Αναζήτηση ASN

```bash
# Αναζήτηση πληροφοριών ASN για domain
amass intel -d example.com

# Αναζήτηση βάσει ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Πολλαπλοί αριθμοί ASN
amass intel -asn 13335,15169
```

### Εύρος διευθύνσεων CIDR

```bash
# Αναζήτηση υποδομών σε εύρος CIDR
amass intel -active -cidr 192.168.1.0/24

# Πολλαπλά εύρη CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### Εύρος IP

```bash
# Εύρος διευθύνσεων IP
amass intel -active -ip 192.168.1.1-254
```

---

<a id="section-05"></a>

## 📡 Λειτουργία DNS

### Ερωτήματα DNS

```bash
# Βασικό ερώτημα DNS
amass dns -d example.com

# Συγκεκριμένοι τύποι εγγραφών DNS
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Επίλυση ονομάτων υποτομέων

```bash
# Επίλυση ονομάτων που βρέθηκαν
amass dns -df subdomains.txt
```

---

<a id="section-06"></a>

## ⚙️ Ρυθμίσεις

### Θέση αρχείου ρυθμίσεων

```bash
# Συνήθης θέση ρυθμίσεων κατά το πρωτότυπο
~/.config/amass/config.yaml

# Καθορισμός δικού σας αρχείου ρυθμίσεων
amass enum -config ./my_config.yaml -d example.com
```

### Παράδειγμα αρχείου ρυθμίσεων

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Πηγές δεδομένων με κλειδιά API

| Source — προέλευση | Δωρεάν πρόσβαση κατά το πρωτότυπο | Απαίτηση API κατά το πρωτότυπο |
| -------- | ----------- | -------------- |
| Crt.sh | ✅ | Όχι |
| SecurityTrails | ✅ | Ναι |
| Shodan | ✅ | Ναι |
| VirusTotal | ✅ | Ναι |
| Censys | ✅ | Ναι |
| PassiveTotal | Περιορισμένες δυνατότητες σύμφωνα με το πρωτότυπο | Ναι |
| BinaryEdge | Περιορισμένες δυνατότητες σύμφωνα με το πρωτότυπο | Ναι |

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Εντολές συλλογής πληροφοριών

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `amass enum -passive -d domain.com` | Αναζήτηση υποτομέων από τρίτες πηγές |
| `amass enum -active -d domain.com` | Ενεργοί έλεγχοι DNS |
| `amass enum -active -brute -d domain.com` | Δοκιμές υποψήφιων ονομάτων υποτομέων |
| `amass enum -d domain.com -o out.txt` | Αποθήκευση σε αρχείο |
| `amass enum -d domain.com -json out.json` | Έξοδος JSON |

### Εντολές αναζήτησης υποδομών

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `amass intel -org "Company"` | Αναζήτηση υποδομών βάσει ονόματος οργανισμού |
| `amass intel -asn 12345` | Αναζήτηση βάσει ASN |
| `amass intel -cidr 10.0.0.0/8` | Αναζήτηση εντός εύρους CIDR |
| `amass intel -whois -d domain.com` | Αντίστροφη αναζήτηση στοιχείων WHOIS |

### Βασικές επιλογές

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-d` | Όνομα τομέα στόχου |
| `-df` | Domains από αρχείο |
| `-o` | Αρχείο εξόδου |
| `-passive` | Συλλογή από πηγές χωρίς άμεση ενεργή επίλυση DNS του στόχου |
| `-active` | Ενεργοποίηση πρόσθετων ενεργών ελέγχων |
| `-brute` | Δοκιμές ονομάτων DNS βάσει λίστας |
| `-src` | Εμφάνιση πηγής δεδομένων |
| `-timeout` | Χρονικό όριο σε λεπτά κατά το συγκεκριμένο εργαλείο |

---

<a id="section-08"></a>

## 🎯 Ροή εργασίας Bug Bounty

```bash
# 1. Συλλογή από τρίτες πηγές· τηρείτε τους κανόνες του προγράμματος
amass enum -passive -d target.com -o passive_subs.txt

# 2. Ενεργοί έλεγχοι με δοκιμές ονομάτων από λίστα
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Αναζήτηση υποδομών οργανισμού
amass intel -org "Target Inc" -o org_domains.txt

# 4. Συνένωση αποτελεσμάτων
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Δοκιμές προσβασιμότητας HTTP, όχι απλώς επίλυση DNS
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Αποθετήριο Amass στο GitHub](https://github.com/owasp-amass/amass)
- [Οδηγός χρήσης Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Σχετικοί οδηγοί
- [Μεθοδολογία Bug Bounty](../../Bug-Bounty-Methodology/translations/README.el.md)
- [Subfinder](../../Subfinder/translations/README.el.md)
- [httpx](../../httpx/translations/README.el.md)

---

<p align="center">
  <b>🌐 Χαρτογραφήστε την επιφάνεια έκθεσης!</b><br>
  <i>Amass — συσχέτιση υποδομών και ονομάτων</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
