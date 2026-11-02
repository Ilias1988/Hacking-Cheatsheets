# 🔍 Subfinder — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Εργαλείο αναζήτησης υποτομέων
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Αναζήτηση υποτομέων από τρίτες πηγές με Subfinder</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Subfinder** συλλέγει υποτομείς από εξωτερικές πηγές, όπως καταγραφές πιστοποιητικών και APIs. Ένας υποτομέας έχει μορφή app.example.com. Δεν εγγυάται ότι κάθε όνομα υπάρχει ακόμη, εξυπηρετεί ιστοσελίδα ή επιτρέπεται να ελεγχθεί.

**Βασικοί όροι:** *passive enumeration* = συλλογή ονομάτων από τρίτες πηγές, *provider* = πάροχος δεδομένων, *resolver* = υπηρεσία επίλυσης DNS, *API key* = κλειδί πρόσβασης σε υπηρεσία, *JSONL* = μία εγγραφή JSON ανά γραμμή.

Η συλλογή ονομάτων, η ενεργή επίλυση DNS και ο έλεγχος HTTP είναι διαφορετικά βήματα. Κρατήστε χωριστές τις λίστες και ελέγξτε ποια βήματα επικοινωνούν άμεσα με την υποδομή.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Αναζήτηση υποτομέων](#section-04)
- [Πηγές δεδομένων](#section-05)
- [Ρύθμιση API](#section-06)
- [Επιλογές αποτελεσμάτων](#section-07)
- [Προχωρημένες δυνατότητες](#section-08)
- [Ροή εργασίας Bug Bounty](#section-09)
- [Παραδείγματα σεναρίων ελέγχου](#section-10)
- [Γρήγορη αναφορά](#section-11)
- [Συμβουλές και καλές πρακτικές](#section-12)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το Subfinder** αναζητά ονόματα υποτομέων σε τρίτες πηγές, όπως υπηρεσίες δεδομένων και πλατφόρμες πληροφοριών ασφάλειας.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Γρήγορο** | Γραμμένο σε Go, με πολλές παράλληλες εργασίες |
| **Passive — παθητική ανάλυση** | Συλλογή από τρίτες πηγές, εκτός αν ενεργοποιηθεί πρόσθετος ενεργός έλεγχος |
| **Πολλαπλές πηγές** | 40+ πηγές δεδομένων |
| **Ενσωμάτωση API** | Υποστήριξη πηγών που απαιτούν διαπιστευτήρια API |
| **Κατάλληλο για αλυσίδες εντολών** | Συνεργασία με άλλα εργαλεία |
| **Έξοδος JSON** | Υποστήριξη δομημένων αποτελεσμάτων |

### Σύγκριση Subfinder με άλλα εργαλεία

| Χαρακτηριστικό | Subfinder | Amass | Assetfinder |
| --------- | ----------- | ------- | ------------- |
| **Ταχύτητα** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Πηγές** | 40+ | 50+ | 10+ |
| **Ενεργή αλληλεπίδραση** | ⚠️ Προαιρετική ενεργή επίλυση DNS | ✅ Ναι | ❌ Όχι κατά το πρωτότυπο |
| **Υποστήριξη API** | ✅ Ναι | ✅ Ναι | ❌ Όχι |
| **Ευκολία χρήσης** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

### Εγκατάσταση μέσω Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Λήψη έτοιμου εκτελέσιμου
```bash
# Λήψη από τις εκδόσεις του έργου
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Επιβεβαίωση εγκατάστασης
```bash
subfinder -version
subfinder -h
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
subfinder [options] -d <domain>
```

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-d` | Όνομα τομέα στόχου |
| `-dL` | Αρχείο λίστας domains |
| `-o` | Αρχείο εξόδου |
| `-oJ` | Έξοδος JSON |
| `-silent` | Περιορισμένη έξοδος με ονόματα υποτομέων |
| `-v` | Αναλυτική έξοδος |
| `-t` | Αριθμός παράλληλων εργασιών |
| `-all` | Χρήση όλων των διαθέσιμων πηγών |

### Πρώτη γνωριμία
```bash
# Βασική συλλογή πληροφοριών
subfinder -d example.com

# Έξοδος λίστας χωρίς πρόσθετα μηνύματα
subfinder -d example.com -silent

# Αποθήκευση σε αρχείο
subfinder -d example.com -o subdomains.txt

# Πολλαπλά domains
subfinder -dL domains.txt -o all-subs.txt
```

---

<a id="section-04"></a>

## 🔍 Αναζήτηση υποτομέων

### Ένα domain

```bash
# Βασική σάρωση
subfinder -d target.com

# Αναλυτική έξοδος
subfinder -d target.com -v

# Περιορισμένη έξοδος με ονόματα υποτομέων
subfinder -d target.com -silent

# Εμφάνιση πηγών
subfinder -d target.com -cs
```

### Πολλαπλά domains

```bash
# Από αρχείο
subfinder -dL domains.txt

# Αποθήκευση σε αρχείο
subfinder -dL domains.txt -o all_subdomains.txt

# Περιορισμένη έξοδος για αλυσίδες εντολών
subfinder -dL domains.txt -silent | httpx
```

### Συλλογή από πηγές που υποστηρίζουν αναδρομικά ονόματα

> **Σημασία recursive:** Το -recursive επιλέγει πηγές που δέχονται αναζήτηση βαθύτερων υποτομέων. Δεν ανατροφοδοτεί αυτόματα κάθε αποτέλεσμα σε νέα αναζήτηση χωρίς όριο. Δείτε το [επίσημο Subfinder](https://github.com/projectdiscovery/subfinder).


```bash
# Επιλογή πηγών με υποστήριξη αναδρομικών ερωτημάτων, όχι αυτόματη εξαντλητική αναδρομή
subfinder -d target.com -recursive

# Πηγές με αναδρομική υποστήριξη και επιλογή -all
subfinder -d target.com -recursive -all
```

---

<a id="section-05"></a>

## 📊 Πηγές δεδομένων

### Πηγές χωρίς κλειδί API που αναφέρει το πρωτότυπο

Το πρωτότυπο παραθέτει τις ακόλουθες πηγές χωρίς κλειδί API. Η διαθεσιμότητα και οι προεπιλογές αλλάζουν:
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Πηγές με απαιτήσεις API

Οι παρακάτω πηγές μπορεί να απαιτούν κλειδιά ή διαφορετικά δικαιώματα API. Ελέγξτε την τρέχουσα υποστήριξη:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Διαχείριση πηγών

```bash
# Προβολή όλων των διαθέσιμων πηγών
subfinder -ls

# Χρήση μόνο συγκεκριμένων πηγών
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Εξαίρεση συγκεκριμένων πηγών
subfinder -d target.com -es shodan,securitytrails

# Χρήση όλων των διαθέσιμων πηγών, ακόμη και βραδύτερων
subfinder -d target.com -all

# Εμφάνιση πηγής εύρεσης κάθε υποτομέα
subfinder -d target.com -cs
```

---

<a id="section-06"></a>

## 🔑 Ρύθμιση API

### Θέση αρχείου ρυθμίσεων

```bash
# Συνήθης θέση ρυθμίσεων κατά το πρωτότυπο
~/.config/subfinder/provider-config.yaml

# Εναλλακτικά καθορίστε δικό σας αρχείο
subfinder -d target.com -pc /path/to/config.yaml
```

### Παράδειγμα αρχείου ρυθμίσεων

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### Απόκτηση κλειδιών API

| Source — προέλευση | Δωρεάν πρόσβαση κατά το πρωτότυπο | Διεύθυνση ιστού (URL) |
| -------- | ----------- | ----- |
| **VirusTotal** | ✅ Ναι | https://www.virustotal.com |
| **SecurityTrails** | ✅ Ναι, με περιορισμούς κατά το πρωτότυπο | https://securitytrails.com |
| **Shodan** | ✅ Ναι, με περιορισμούς κατά το πρωτότυπο | https://shodan.io |
| **Censys** | ✅ Ναι | https://censys.io |
| **Chaos** | ✅ Δωρεάν με περιορισμούς κατά το πρωτότυπο | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Δωρεάν | https://github.com/settings/tokens |

### Ενδεικτική ρύθμιση πηγών του πρωτοτύπου

```yaml
# Ενδεικτικό σύνολο πηγών του πρωτοτύπου, όχι απαραίτητη προϋπόθεση
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

<a id="section-07"></a>

## 📤 Επιλογές αποτελεσμάτων

### Μορφές αποτελεσμάτων

> **Διόρθωση:** Το -oD ορίζει φάκελο εξόδου, όχι CSV. Επιλογές όπως -oJL ή -exclude-subdomains χρειάζονται επιβεβαίωση στη βοήθεια της εγκατεστημένης έκδοσης.


```bash
# Απλό κείμενο, προεπιλογή του πρωτοτύπου
subfinder -d target.com -o subs.txt

# Έξοδος JSON
subfinder -d target.com -oJ -o subs.json

# Έξοδος JSON Lines
subfinder -d target.com -oJL -o subs.jsonl

# Φάκελος εξόδου μέσω -oD· δεν είναι μορφή CSV
subfinder -d target.com -oD -o subs.csv
```

### Φιλτράρισμα εξόδου

```bash
# Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα
subfinder -d target.com -silent

# Παράδειγμα εξαίρεσης γνωστών υποτομέων· ελέγξτε τη διαθέσιμη επιλογή
subfinder -d target.com -exclude-subdomains known.txt

# Χωρίς χρώματα στην έξοδο
subfinder -d target.com -nc
```

### Διοχέτευση εξόδου σε άλλες εντολές

> **Μορφή δεδομένων:** Domains, καθαρά URLs, πρόσθετες στήλες και JSON διαφέρουν. Το silent δεν αφαιρεί άλλες ρητά επιλεγμένες πληροφορίες του httpx. Τα δείγματα API keys είναι placeholders· πραγματικά μυστικά δεν αποθηκεύονται σε δημόσιο repo.


```bash
# Διοχέτευση στο httpx
subfinder -d target.com -silent | httpx -silent

# Διοχέτευση στο nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Αποθήκευση και επεξεργασία
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

<a id="section-08"></a>

## ⚡ Προχωρημένες δυνατότητες

### Παράλληλες εργασίες και απόδοση

> **Διαφορετικά όρια:** Το -t αφορά ταυτόχρονη ενεργή επίλυση στην αντίστοιχη λειτουργία και δεν επιταχύνει αυτόματα όλες τις παθητικές πηγές. Το -all δεν εξασφαλίζει πληρότητα· απαιτήσεις API, όρια και διαθεσιμότητα διαφέρουν ανά πάροχο.


```bash
# Ορισμός παράλληλων εργασιών, με προεπιλογή 10 κατά το πρωτότυπο
subfinder -d target.com -t 50

# Περιορισμός ρυθμού
subfinder -d target.com -rate-limit 100

# Χρονικό όριο
subfinder -d target.com -timeout 30
```

### Συμπερίληψη και εξαίρεση αποτελεσμάτων

```bash
# Συμπερίληψη συγκεκριμένων μοτίβων
subfinder -d target.com -match "api,dev,stage"

# Εξαίρεση μοτίβων
subfinder -d target.com -filter "www,mail"
```

### Επιλογές επίλυσης DNS

```bash
# Χρήση συγκεκριμένων διακομιστών επίλυσης DNS
subfinder -d target.com -rL resolvers.txt

# Επίλυση ονομάτων υποτομέων
subfinder -d target.com -nW
```

### Χρήση ενδιάμεσου διακομιστή (proxy)

```bash
# Proxy HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# Ενδιάμεσος διακομιστής SOCKS
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

<a id="section-09"></a>

## 🔄 Ροή εργασίας Bug Bounty

### Ολοκληρωμένη αλυσίδα συλλογής πληροφοριών

```bash
# Βήμα 1: Αναζήτηση υποτομέων
subfinder -d target.com -all -o subs.txt

# Βήμα 2: Έλεγχος προσβάσιμων υπηρεσιών
cat subs.txt | httpx -o live.txt

# Βήμα 3: Έλεγχος με Nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Παράδειγμα μίας γραμμής
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Ροή εργασίας για πολλά domains

```bash
# Αναζήτηση για τα domains του εγκεκριμένου πεδίου
subfinder -dL scope.txt -all -o all-subs.txt

# Επεξεργασία αποτελεσμάτων
cat all-subs.txt | httpx -silent | nuclei
```

### Επαναλαμβανόμενη παρακολούθηση

> **Baseline:** Κανονικοποιήστε και ταξινομήστε σταθερά τα αποτελέσματα πριν συγκρίνετε συλλογές. Οι εντολές εκτελούν μία συλλογή· δεν δημιουργούν αυτόματα καθημερινό προγραμματισμό.


```bash
# Αποθήκευση αρχικής καταγραφής αναφοράς
subfinder -d target.com -silent > baseline.txt

# Μεταγενέστερη καταγραφή και σύγκριση υποτομέων
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Συνδυασμός με άλλα εργαλεία

```bash
# Συνδυασμός με httpx για προσβάσιμες υπηρεσίες
subfinder -d target.com -silent | httpx

# Συνδυασμός με ffuf για αναζήτηση διαδρομών
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Συνδυασμός με Nmap για σάρωση θυρών
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Συνδυασμός εργαλείων σε αλυσίδα
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

<a id="section-10"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Βασική αναζήτηση υποτομέων
```bash
subfinder -d hackerone.com -silent
```

### Παράδειγμα 2: Με όλες τις διαθέσιμες πηγές
```bash
subfinder -d target.com -all -v
```

### Παράδειγμα 3: Αποθήκευση αποτελεσμάτων
```bash
subfinder -d target.com -o target-subs.txt
```

### Παράδειγμα 4: Πολλαπλά domains
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Παράδειγμα 5: Εμφάνιση πηγών
```bash
subfinder -d target.com -cs
```

### Παράδειγμα 6: Έξοδος JSON
```bash
subfinder -d target.com -oJ -o results.json
```

### Παράδειγμα 7: Με proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Παράδειγμα 8: Παράδειγμα αυξημένης παραλληλίας
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Παράδειγμα 9: Αλυσίδα εργαλείων Bug Bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Παράδειγμα 10: Πηγές αναδρομικής αναζήτησης
```bash
subfinder -d target.com -recursive -all -silent
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Βασική σάρωση | `subfinder -d target.com` |
| Περιορισμένη έξοδος | `subfinder -d target.com -silent` |
| Αποθήκευση σε αρχείο | `subfinder -d target.com -o subs.txt` |
| Όλες οι διαθέσιμες πηγές | `subfinder -d target.com -all` |
| Εμφάνιση πηγών | `subfinder -d target.com -cs` |
| Πολλαπλά domains | `subfinder -dL domains.txt` |
| Έξοδος JSON | `subfinder -d target.com -oJ` |
| Προβολή πηγών | `subfinder -ls` |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-d` | Όνομα τομέα στόχου |
| `-dL` | Αρχείο λίστας domains |
| `-o` | Αρχείο εξόδου |
| `-oJ` | Έξοδος JSON |
| `-silent` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |
| `-all` | Χρήση όλων των διαθέσιμων πηγών |
| `-cs` | Εμφάνιση πηγής εύρεσης κάθε υποτομέα |
| `-t` | Παράλληλες εργασίες (threads) |
| `-recursive` | Χρήση πηγών με αναδρομική υποστήριξη |

### Συνήθεις αλυσίδες εντολών

| Αλυσίδα εντολών | Εντολή |
| ---------- | --------- |
| Προσβάσιμες υπηρεσίες | `subfinder -d target.com -silent \| httpx` |
| Εκτενής συλλογή πληροφοριών | `subfinder -d target.com \| httpx \| nuclei` |
| Σάρωση θυρών | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

<a id="section-12"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

1. **Ρυθμίστε κλειδιά API**
   ```bash
   # Προσθήκη κλειδιών για τις επιλεγμένες πηγές API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Χρησιμοποιήστε περισσότερες πηγές για ευρύτερη κάλυψη**
   ```bash
   subfinder -d target.com -all
   ```

3. **Συνδυάστε το με άλλα εργαλεία**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Παρακολουθήστε νέα ονόματα υποτομέων**
   ```bash
   # Παράδειγμα καταγραφής σημερινών αποτελεσμάτων· δεν δημιουργεί μόνο του περιοδικό έλεγχο
   subfinder -d target.com -silent > today.txt
   ```

### Συμβουλές απόδοσης

```bash
# Αυξημένος αριθμός παράλληλων εργασιών
subfinder -d target.com -t 100 -all

# Χρονικό όριο για πηγές που αποκρίνονται αργά
subfinder -d target.com -timeout 60
```

---

<a id="section-13"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Η βασική συλλογή του Subfinder γίνεται από τρίτες πηγές. Πρόσθετες ενεργές επιλογές ή επόμενα εργαλεία της αλυσίδας μπορεί να επικοινωνούν με στόχους.
> 
> - ✅ Χρήση σε προγράμματα με εξουσιοδότηση
> - ✅ Στόχοι Bug Bounty εντός του επιτρεπόμενου πεδίου
> - ✅ Δικά σας domains
> - ❌ Μην συλλέγετε πληροφορίες στόχων κατά παράβαση των κανόνων εξουσιοδότησης
> - ❌ Τηρείτε το επιτρεπόμενο πεδίο του προγράμματος
> 
> **Εξασφαλίστε την απαιτούμενη εξουσιοδότηση.**

---

<a id="section-14"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο Subfinder στο GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Συνοδευτικά έγγραφα και στοιχεία](https://docs.projectdiscovery.io/tools/subfinder/)

### Σχετικοί οδηγοί
- [Nuclei](../../Nuclei/translations/README.el.md)
- [ffuf](../../ffuf/translations/README.el.md)
- [Nmap](../../Nmap/translations/README.el.md)

---

<p align="center">
  <b>🔍 Αναζητήστε ονόματα υποτομέων!</b><br>
  <i>Αφετηρία συλλογής πληροφοριών για Bug Bounty</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
