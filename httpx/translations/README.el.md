# 🌐 httpx — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Εργαλείο δοκιμών και συλλογής πληροφοριών HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Εργαλείο δοκιμών HTTP της ProjectDiscovery</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Εδώ το **httpx** είναι το εργαλείο της **ProjectDiscovery**, γραμμένο σε Go, διαφορετικό από τη βιβλιοθήκη HTTPX της Python. Ελέγχει υπηρεσίες HTTP/HTTPS και συλλέγει κωδικούς απόκρισης, τίτλους, headers και ενδείξεις τεχνολογιών.

**Βασικοί όροι:** *probe* = δοκιμαστικό αίτημα, *status code* = κωδικός απόκρισης HTTP, *header* = κεφαλίδα, *redirect* = ανακατεύθυνση, *fingerprint* = ένδειξη ταυτότητας τεχνολογίας, *headless browser* = browser χωρίς εμφανές παράθυρο.

Ξεκινήστε με λίγους εγκεκριμένους hosts και διαβάστε την έξοδο πριν τη διοχετεύσετε αλλού. Οι ενδείξεις αναγνώρισης και οι αποκρίσεις HTTP δεν επιβεβαιώνουν μόνες τους ευπάθεια.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Δοκιμές πρόσβασης και αναζήτηση υπηρεσιών](#section-04)
- [Εξαγωγή πληροφοριών](#section-05)
- [Εξαίρεση και επιλογή αποκρίσεων](#section-06)
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

**Το httpx της ProjectDiscovery** στέλνει δοκιμαστικά αιτήματα HTTP για να καταγράψει προσβάσιμες υπηρεσίες και πληροφορίες των αποκρίσεών τους.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Γρήγορο** | Εργαλείο Go με πολλές παράλληλες εργασίες |
| **Πολλαπλοί έλεγχοι HTTP** | Κωδικός κατάστασης, τίτλος, τεχνολογίες και κεφαλίδες |
| **Αναγνώριση τεχνολογιών** | Αναγνώριση βάσει μοτίβων τεχνολογιών |
| **Στιγμιότυπο σελίδας** | Στιγμιότυπα μέσω browser χωρίς γραφικό περιβάλλον |
| **Αλυσίδα εντολών** | Συνδυασμός με subfinder και nuclei |
| **Έξοδος JSON** | Υποστήριξη δομημένων αποτελεσμάτων |

### Σύγκριση httpx με άλλα εργαλεία

| Χαρακτηριστικό | httpx | curl | wget |
| --------- | ------- | ------ | ------ |
| **Ταχύτητα** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Παράλληλες μεταφορές** | ✅ Ναι | ✅ Με parallel | ❌ Όχι κατά το πρωτότυπο |
| **Αναγνώριση τεχνολογιών** | ✅ Ναι | ❌ Όχι | ❌ Όχι |
| **Στιγμιότυπα οθόνης** | ✅ Ναι | ❌ Όχι | ❌ Όχι |
| **Αλυσίδα εντολών** | ✅ Ναι | ⚠️ Χειροκίνητα | ⚠️ Χειροκίνητα |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Ποιο πρόγραμμα καλείτε:** Το εκτελέσιμο μπορεί να ονομάζεται διαφορετικά, π.χ. httpx-toolkit σε ορισμένες διανομές. Ελέγξτε ότι χρησιμοποιείτε το έργο της ProjectDiscovery. Οι λήψεις με v1.x.x είναι placeholders. Δείτε το [επίσημο httpx](https://github.com/projectdiscovery/httpx).


### Εγκατάσταση μέσω Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Λήψη έτοιμου εκτελέσιμου
```bash
# Λήψη από τις εκδόσεις του έργου
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Επιβεβαίωση εγκατάστασης
```bash
httpx -version
httpx -h
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | Ένα URL στόχου |
| `-l` | Αρχείο λίστας URL |
| `-sc` | Εμφάνιση κωδικού κατάστασης |
| `-title` | Εμφάνιση τίτλου σελίδας |
| `-td` | Αναγνώριση τεχνολογιών |
| `-o` | Αρχείο εξόδου |
| `-silent` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |
| `-json` | Έξοδος JSON |

### Πρώτη γνωριμία
```bash
# Δοκιμή ενός URL
httpx -u https://example.com

# Δοκιμές στόχων από αρχείο
httpx -l urls.txt

# Ανάγνωση από την κανονική είσοδο προηγούμενης εντολής
cat hosts.txt | httpx

# Συνδυασμός με Subfinder
subfinder -d example.com -silent | httpx
```

---

<a id="section-04"></a>

## 🔍 Δοκιμές πρόσβασης και αναζήτηση υπηρεσιών

### Βασικές δοκιμές πρόσβασης HTTP

```bash
# Δοκιμές πρόσβασης HTTP και HTTPS
cat hosts.txt | httpx

# Συμπερίληψη κωδικού κατάστασης
cat hosts.txt | httpx -sc

# Δοκιμές σε συγκεκριμένες θύρες
cat hosts.txt | httpx -p 80,443,8080,8443

# Δοκιμές HTTP στις συγκεκριμένες θύρες της λίστας
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Σάρωση θυρών

```bash
# Συγκεκριμένες θύρες
httpx -l hosts.txt -p 80,443,8080,8443

# Εύρος θυρών
httpx -l hosts.txt -p 80-100

# Συνήθεις θύρες web servers
httpx -l hosts.txt -p http:80,https:443
```

### Αναγνώριση πρωτοκόλλου

> **Διόρθωση:** Το -no-fallback ζητά εμφάνιση αποτελεσμάτων και HTTP και HTTPS όπου είναι διαθέσιμα· δεν ταυτίζεται με απενεργοποίηση αλλαγής πρωτοκόλλου. Το -no-fallback-scheme έχει άλλη σημασία. Το HTTP probing δεν αντικαθιστά πλήρη σάρωση θυρών.


```bash
# Δοκιμές πρόσβασης HTTP και HTTPS
cat hosts.txt | httpx

# Ακολούθηση ανακατευθύνσεων
cat hosts.txt | httpx -follow-redirects

# Μέγιστος αριθμός ανακατευθύνσεων
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Εμφάνιση και των δύο πρωτοκόλλων όπου υποστηρίζεται το -no-fallback
cat hosts.txt | httpx -no-fallback
```

---

<a id="section-05"></a>

## 📊 Εξαγωγή πληροφοριών

> **Συμβατότητα:** Παραδείγματα όπως -response-header, -include-response-header με λίστα ονομάτων, -jsonl και επιλογές TLS/screenshots χρειάζονται έλεγχο ανά έκδοση. Αποθηκευμένες αποκρίσεις και screenshots μπορεί να περιέχουν δεδομένα εφαρμογής ή συνεδρίας. Το fingerprint είναι ένδειξη, όχι βεβαιότητα.


### Κωδικοί κατάστασης και τίτλοι

```bash
# Κωδικός κατάστασης HTTP
httpx -l urls.txt -sc

# Τίτλος σελίδας
httpx -l urls.txt -title

# Docker και Kubernetes
httpx -l urls.txt -sc -title

# Μήκος περιεχομένου απόκρισης
httpx -l urls.txt -cl

# Διακομιστής ιστού
httpx -l urls.txt -server

# Συνδυασμός βασικών πληροφοριών
httpx -l urls.txt -sc -title -cl -server
```

### Αναγνώριση τεχνολογιών

```bash
# Αναγνώριση τεχνολογιών
httpx -l urls.txt -td

# Αναγνώριση τεχνολογιών με την πλήρη ονομασία της επιλογής, όχι ενεργοποίηση verbose
httpx -l urls.txt -tech-detect

# Με επιπλέον πληροφορίες
httpx -l urls.txt -sc -title -td
```

### Κεφαλίδες και απόκριση

```bash
# Κεφαλίδες απόκρισης
httpx -l urls.txt -response-header

# Συγκεκριμένη κεφαλίδα· ελέγξτε τη σύνταξη και το πεδίο εφαρμογής της επιλογής
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hash σώματος απόκρισης
httpx -l urls.txt -hash md5

# Προεπισκόπηση σώματος απόκρισης
httpx -l urls.txt -bp
```

### Πληροφορίες IP και DNS

```bash
# Διεύθυνση IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Αναγνώριση δικτύου διανομής περιεχομένου (CDN)
httpx -l urls.txt -cdn

# Πληροφορίες ASN
httpx -l urls.txt -asn

# Συνδυασμός πληροφοριών IP και DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

### Πληροφορίες SSL/TLS

```bash
# Δοκιμές ονομάτων που προκύπτουν από πιστοποιητικά TLS
httpx -l urls.txt -tls-probe

# Συλλογή πληροφοριών TLS
httpx -l urls.txt -tls-grab

# Πληροφορίες πιστοποιητικού
httpx -l urls.txt -tlsgrab
```

### Hash εικονιδίου ιστοτόπου (Favicon)

```bash
# Υπολογισμός hash εικονιδίου για συσχέτιση υποδομών
httpx -l urls.txt -favicon
```

### Λήψη στιγμιοτύπου σελίδας

```bash
# Λήψη στιγμιότυπων
httpx -l urls.txt -screenshot

# Στιγμιότυπα με κατάλογο αποθήκευσης
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Επιλογές Headless
httpx -l urls.txt -screenshot -system-chrome
```

---

<a id="section-06"></a>

## 🎯 Εξαίρεση και επιλογή αποκρίσεων

### Φιλτράρισμα βάσει κωδικού κατάστασης HTTP

```bash
# Συμπερίληψη κωδικών κατάστασης
httpx -l urls.txt -mc 200,301,302

# Εξαίρεση κωδικών κατάστασης
httpx -l urls.txt -fc 404,403,500

# Επιλογή όλων και εξαίρεση συγκεκριμένων τιμών, όπου η επιλογή υποστηρίζεται
httpx -l urls.txt -mc all -fc 404
```

### Φιλτράρισμα βάσει μεγέθους

```bash
# Συμπερίληψη βάσει μήκους περιεχομένου
httpx -l urls.txt -ml 1234

# Εξαίρεση συγκεκριμένων μηκών περιεχομένου
httpx -l urls.txt -fl 0

# Συμπερίληψη βάσει πλήθους γραμμών
httpx -l urls.txt -mlc 100

# Εξαίρεση βάσει πλήθους γραμμών
httpx -l urls.txt -flc 50
```

### Φιλτράρισμα βάσει περιεχομένου

```bash
# Συμπερίληψη βάσει κανονικής έκφρασης σώματος απόκρισης
httpx -l urls.txt -mr "admin|login"

# Εξαίρεση βάσει κανονικής έκφρασης σώματος απόκρισης
httpx -l urls.txt -fr "error|not found"

# Αναζήτηση συμβολοσειράς στο μέρος απόκρισης που ελέγχει η επιλογή
httpx -l urls.txt -ms "dashboard"
```

### Φιλτράρισμα βάσει τεχνολογίας

> **Ελέγξτε τις επιλογές:** Τα -mt/-ft δεν είναι εγγυημένα διαθέσιμα. Το -ms αναζητά συμβολοσειρά στο σώμα, όχι ειδικά στον Server header· δεν επιβεβαιώνει ότι ο server είναι Nginx.


```bash
# Παράδειγμα επιλογής τεχνολογίας· ελέγξτε την υποστήριξη της επιλογής
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Παράδειγμα φίλτρου τεχνολογίας· ελέγξτε την υποστήριξη της επιλογής
httpx -l urls.txt -td -ft "Apache"
```

---

<a id="section-07"></a>

## 📤 Επιλογές αποτελεσμάτων

> **Silent και μορφές:** Με πρόσθετες πληροφορίες, silent δεν σημαίνει καθαρά URLs. Ελέγξτε τη μορφή που απαιτεί το επόμενο εργαλείο και αν υποστηρίζονται οι προτεινόμενες επιλογές CSV/JSON, resume και custom output.


### Μορφές αποτελεσμάτων

```bash
# Απλό κείμενο
httpx -l urls.txt -o results.txt

# Έξοδος JSON
httpx -l urls.txt -json -o results.json

# Έξοδος JSON Lines
httpx -l urls.txt -jsonl -o results.jsonl

# Έξοδος CSV
httpx -l urls.txt -csv -o results.csv
```

### Πεδία αποτελεσμάτων

```bash
# Αποθήκευση αποκρίσεων
httpx -l urls.txt -sr -srd ./responses/

# Αποθήκευση στιγμιότυπων
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Προσαρμοσμένες πληροφορίες εξόδου
httpx -l urls.txt -o output.txt -sc -title -td
```

### Περιορισμένη και αναλυτική έξοδος

```bash
# Περιορισμένη έξοδος· πρόσθετες επιλογές μπορεί να προσθέτουν πληροφορίες πέρα από URL
httpx -l urls.txt -silent

# Αναλυτική έξοδος
httpx -l urls.txt -v

# Διαγνωστική έξοδος
httpx -l urls.txt -debug

# Χωρίς χρώματα στην έξοδο
httpx -l urls.txt -nc
```

---

<a id="section-08"></a>

## ⚡ Προχωρημένες δυνατότητες

### Παράλληλες εργασίες και περιορισμός ρυθμού

```bash
# Ορισμός παράλληλων εργασιών, με προεπιλογή 50 κατά το πρωτότυπο
httpx -l urls.txt -threads 100

# Όριο ρυθμού αιτημάτων
httpx -l urls.txt -rate-limit 100

# Χρονικό όριο
httpx -l urls.txt -timeout 10

# Επαναλήψεις σε αποτυχία
httpx -l urls.txt -retries 3
```

### Χρήση ενδιάμεσου διακομιστή (proxy)

```bash
# Proxy HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# Ενδιάμεσος διακομιστής SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Προσαρμοσμένες κεφαλίδες HTTP

```bash
# Προσαρμοσμένη κεφαλίδα
httpx -l urls.txt -H "Authorization: Bearer token123"

# Πολλαπλές κεφαλίδες HTTP
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Μέθοδοι αιτημάτων

> **HEAD και GET:** Η εφαρμογή μπορεί να απαντά διαφορετικά στις δύο μεθόδους. Το 200 ή ένας τίτλος admin δεν αποδεικνύει πρόσβαση ή ευπάθεια. Η συνοπτική σύγκριση με curl παραπάνω χρειάζεται διευκρίνιση: το curl διαθέτει επίσης παράλληλες μεταφορές. Δείτε το [parallel του curl](https://curl.se/docs/manpage.html#--parallel).


```bash
# Αίτημα HEAD· η συμπεριφορά μπορεί να διαφέρει από GET
httpx -l urls.txt -method HEAD

# Συγκεκριμένη μέθοδος HTTP
httpx -l urls.txt -method OPTIONS

# Αίτημα POST με σώμα δεδομένων
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Συνέχιση εκτέλεσης και διαχείριση προόδου

```bash
# Παράδειγμα συνέχισης προηγούμενης εκτέλεσης
httpx -l urls.txt -resume

# Παράδειγμα διαχείρισης προόδου· ελέγξτε την υποστήριξη της επιλογής
httpx -l urls.txt -resume -resume-file progress.txt
```

---

<a id="section-09"></a>

## 🔄 Ροή εργασίας Bug Bounty

### Ολοκληρωμένη αλυσίδα συλλογής πληροφοριών

```bash
# Βήμα 1: Αναζήτηση υποτομέων
subfinder -d target.com -o subs.txt

# Βήμα 2: Δοκιμές πρόσβασης με httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Βήμα 3: Έλεγχος ενδείξεων ευπαθειών
nuclei -l live.txt -severity critical,high

# Παράδειγμα μίας γραμμής
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Αναλυτική συλλογή πληροφοριών

```bash
# Συλλογή πολλών πληροφοριών απόκρισης
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Στιγμιότυπα προσβάσιμων υπηρεσιών

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Αναζήτηση διαδρομών ενδιαφέροντος

```bash
# Αναζήτηση ενδείξεων σελίδων διαχείρισης
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Αναζήτηση διαδρομών API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Έλεγχος συγκεκριμένων διαδρομών
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Έλεγχοι βάσει τεχνολογίας

```bash
# Αναζήτηση ενδείξεων WordPress· απαιτεί συμβατή επιλογή φίλτρου
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Αναζήτηση ενδείξεων Nginx· ελέγξτε πού εφαρμόζεται το matcher
cat hosts.txt | httpx -server -ms "nginx"
```

---

<a id="section-10"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Βασική δοκιμή HTTP
```bash
echo "https://example.com" | httpx -sc -title
```

### Παράδειγμα 2: Συλλογή πληροφοριών από υποτομείς
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Παράδειγμα 3: Στιγμιότυπα υπηρεσιών
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Παράδειγμα 4: Επιλογή προσβάσιμων υπηρεσιών
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Παράδειγμα 5: Αναγνώριση τεχνολογιών
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Παράδειγμα 6: Μέσω proxy Burp
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Παράδειγμα 7: Συγκεκριμένες θύρες
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Παράδειγμα 8: Επιλογή μόνο αποκρίσεων 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Παράδειγμα 9: Έξοδος JSON για επεξεργασία
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Παράδειγμα 10: Ολοκληρωμένη αλυσίδα εντολών
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Βασική δοκιμή HTTP | `httpx -u url` |
| Από αρχείο | `httpx -l urls.txt` |
| Κωδικός κατάστασης HTTP | `httpx -l urls.txt -sc` |
| Τίτλος σελίδας | `httpx -l urls.txt -title` |
| Αναγνώριση τεχνολογιών | `httpx -l urls.txt -td` |
| Διεύθυνση IP | `httpx -l urls.txt -ip` |
| Στιγμιότυπο σελίδας | `httpx -l urls.txt -screenshot` |
| Έξοδος JSON | `httpx -l urls.txt -json -o out.json` |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | Ένα URL |
| `-l` | Αρχείο λίστας URL |
| `-sc` | Κωδικός κατάστασης HTTP |
| `-title` | Τίτλος σελίδας |
| `-td` | Αναγνώριση τεχνολογιών |
| `-ip` | Διεύθυνση IP |
| `-cdn` | Αναγνώριση δικτύου διανομής περιεχομένου (CDN) |
| `-server` | Διακομιστής ιστού |
| `-mc` | Συμπερίληψη κωδικών |
| `-fc` | Εξαίρεση κωδικών |
| `-json` | Έξοδος JSON |
| `-silent` | Περιορισμένη έξοδος χωρίς πρόσθετα μηνύματα |

### Συνήθεις αλυσίδες εντολών

| Αλυσίδα εντολών | Εντολή |
| ---------- | --------- |
| Προσβάσιμες υπηρεσίες | `subfinder -d target.com \| httpx -silent` |
| Με πρόσθετες πληροφορίες | `subfinder -d target.com \| httpx -sc -title -td` |
| Διοχέτευση στο nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

<a id="section-12"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

1. **Συνδυασμός με Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Συλλέξτε σχετικές πληροφορίες από πολλές ενδείξεις**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Στιγμιότυπα για αρχική αξιολόγηση**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Έξοδος JSON για επεξεργασία**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Συμβουλές απόδοσης

```bash
# Παράδειγμα αυξημένου ρυθμού
httpx -l hosts.txt -threads 100 -timeout 5

# Περιορισμένος ρυθμός και παραλληλία
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

<a id="section-13"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Το httpx στέλνει αιτήματα HTTP. Χρησιμοποιήστε το μόνο σε εξουσιοδοτημένους στόχους.
> 
> - ✅ Χρήση σε προγράμματα για τα οποία έχετε άδεια ελέγχου
> - ✅ Στόχοι Bug Bounty εντός του επιτρεπόμενου πεδίου
> - ✅ Δικά σας domains
> - ❌ Μην στέλνετε δοκιμαστικά αιτήματα σε μη εξουσιοδοτημένους στόχους
> - ❌ Τηρείτε τα όρια ρυθμού
> 
> **Μη εξουσιοδοτημένα δοκιμαστικά αιτήματα μπορεί να είναι παράνομα.**

---

<a id="section-14"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο httpx στο GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Συνοδευτικά έγγραφα και στοιχεία](https://docs.projectdiscovery.io/tools/httpx/)

### Σχετικοί οδηγοί
- [Subfinder](../../Subfinder/translations/README.el.md)
- [Nuclei](../../Nuclei/translations/README.el.md)
- [ffuf](../../ffuf/translations/README.el.md)

---

<p align="center">
  <b>🌐 Καταγράψτε τις προσβάσιμες υπηρεσίες HTTP!</b><br>
  <i>Πληροφορίες αποκρίσεων για οργανωμένη διερεύνηση</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
