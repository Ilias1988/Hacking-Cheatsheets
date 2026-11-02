# 🚀 Gobuster — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Αναζήτηση καταλόγων, υποτομέων DNS και VHosts με λίστες λέξεων
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Εργαλείο αναζήτησης καταλόγων, υποτομέων DNS και εικονικών ιστοτόπων, γραμμένο σε Go</b>
</p>

---

## Πριν ξεκινήσετε

Το **Gobuster** δοκιμάζει υποψήφιες τιμές από μια **wordlist**, δηλαδή λίστα λέξεων σε αρχείο. Το *brute force* εδώ συνήθως σημαίνει διαδοχική δοκιμή ονομάτων και διαδρομών, όχι αυτόματα δοκιμή κωδικών πρόσβασης.

Οι βασικές λειτουργίες έχουν διαφορετικό σκοπό: **dir** αναζητά διαδρομές και αρχεία ενός ιστοτόπου, **dns** αναζητά υποτομείς μέσω DNS, **vhost** ελέγχει διαφορετικά ονόματα ιστοτόπων στον ίδιο web server, και **fuzz** αντικαθιστά το σημείο **FUZZ** με τιμές από τη λίστα. Ένας VHost δεν χρειάζεται απαραίτητα δημόσια εγγραφή DNS. Αυτές οι αναζητήσεις στέλνουν αιτήματα· δεν αποτελούν παθητική παρατήρηση.

**Όροι:** *thread* = παράλληλη εργασία, *status code* = κωδικός απόκρισης HTTP, όπως 200 ή 404, *header* = κεφαλίδα αιτήματος ή απόκρισης, *cookie* = δεδομένο που συχνά συνδέει αιτήματα με μια συνεδρία χρήστη. Οι διαδρομές λιστών, τα domains και τα στοιχεία συνεδρίας είναι παραδείγματα. Οι εντολές διατηρούνται από το πρωτότυπο και οι διαφορές εκδόσεων επισημαίνονται παρακάτω.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Επισκόπηση λειτουργιών](#section-03)
- [Αναζήτηση καταλόγων και αρχείων (dir)](#section-04)
- [Αναζήτηση υποτομέων DNS (dns)](#section-05)
- [Αναζήτηση εικονικών ιστοτόπων (vhost)](#section-06)
- [Δοκιμές διαφορετικών εισόδων (fuzz)](#section-07)
- [Λίστες λέξεων (wordlists)](#section-08)
- [Ταυτοποίηση και κεφαλίδες HTTP](#section-09)
- [Ρύθμιση απόδοσης](#section-10)
- [Παραδείγματα σεναρίων ελέγχου](#section-11)
- [Γρήγορη αναφορά](#section-12)
- [Συμβουλές και καλές πρακτικές](#section-13)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-14)
- [Πηγές](#section-15)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το Gobuster** είναι εργαλείο γραμμένο σε Go που δοκιμάζει πολλές υποψήφιες τιμές από λίστες λέξεων, με παράλληλες εργασίες, για:
- Αναζήτηση καταλόγων και αρχείων σε web servers
- Εντοπισμός υποτομέων μέσω DNS
- Εντοπισμός εικονικών ιστοτόπων (VHosts)
- Δοκιμές εναλλακτικών τιμών μέσα σε URL

### Χαρακτηριστικά του Gobuster

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Γρήγορο** | Γραμμένο σε Go, με πολλές παράλληλες εργασίες |
| **Ευέλικτο** | Διαφορετικές λειτουργίες ανάλογα με τον έλεγχο |
| **Χωρίς αναδρομική αναζήτηση** | Περιορισμός της αναζήτησης στο επιλεγμένο επίπεδο |
| **Σταθερό** | Διαχείριση σφαλμάτων κατά την εκτέλεση |
| **Ενεργή ανάπτυξη** | Τακτικές ενημερώσεις |

### Σύγκριση με άλλα εργαλεία

> **Πώς διαβάζεται η σύγκριση:** Τα αστέρια του πρωτοτύπου είναι ενδεικτικά, όχι μετρήσεις ταχύτητας. Η καταχώριση «No» για αναδρομική αναζήτηση στο ffuf είναι παρωχημένη: το ffuf υποστηρίζει recursion. Δείτε την [επίσημη τεκμηρίωση ffuf](https://github.com/ffuf/ffuf).


| Εργαλείο | Ταχύτητα | Αναδρομική αναζήτηση | Γλώσσα προγραμματισμού |
| ------ | ------- | ----------- | ---------- |
| **Gobuster** | ⭐⭐⭐⭐⭐ | Όχι | Go |
| Dirb | ⭐⭐ | Ναι | C |
| Dirbuster | ⭐⭐⭐ | Ναι | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Ναι | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Ναι | Rust |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Συμβατότητα εκδόσεων:** Το πρωτότυπο συνδυάζει παραδείγματα διαφορετικών εκδόσεων. Ελέγξτε την τοπική βοήθεια κάθε λειτουργίας πριν χρησιμοποιήσετε επιλογές όπως `-d`, `-s`, `-b`, `--insecuressl` ή `--no-tls-validation`. Για παράδειγμα, η τρέχουσα επίσημη τεκμηρίωση DNS δείχνει `-do` για το domain. Τα ονόματα αρχείων λήψης και οι απαιτήσεις Go χρειάζονται επίσης έλεγχο στη [σελίδα Gobuster](https://github.com/OJ/gobuster).


### Kali Linux (συνήθως προεγκατεστημένο)
```bash
# Ενημέρωση Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### Εγκατάσταση από τον πηγαίο κώδικα με Go
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Εγκατάσταση μέσω Snap
```bash
sudo snap install gobuster
```

### Εγκατάσταση από έκδοση του GitHub
```bash
# Παράδειγμα λήψης έκδοσης· ελέγξτε το όνομα αρχείου
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Επιβεβαίωση εγκατάστασης
```bash
gobuster version
gobuster -h
```

---

<a id="section-03"></a>

## 📊 Επισκόπηση λειτουργιών

### Διαθέσιμες λειτουργίες

| Λειτουργία | Περιγραφή | Χρήση |
| ------ | ------------- | ---------- |
| `dir` | Αναζήτηση καταλόγων και αρχείων | Εντοπισμός μη συνδεδεμένων καταλόγων και αρχείων |
| `dns` | Αναζήτηση υποτομέων DNS | Εντοπισμός υποτομέων |
| `vhost` | Αναζήτηση εικονικών ιστοτόπων | Εντοπισμός εικονικών ιστοτόπων |
| `fuzz` | Δοκιμές εναλλακτικών τιμών μέσα σε URL | Δοκιμές διαφορετικών τιμών σε παραμέτρους ή διαδρομές |
| `s3` | Αναζήτηση χώρων αποθήκευσης S3 (buckets) | Εντοπισμός buckets στο AWS S3 |
| `gcs` | Αποθήκευση στο Google Cloud Storage | Εντοπισμός buckets στο GCS |
| `tftp` | Αναζήτηση αρχείων μέσω TFTP | Εντοπισμός αρχείων TFTP |

### Βασική σύνταξη
```bash
gobuster [mode] [options]

# Παραδείγματα
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="section-04"></a>

## 📁 Αναζήτηση καταλόγων και αρχείων (dir)

### Βασική σύνταξη
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | URL στόχου |
| `-w` | Διαδρομή αρχείου λίστας λέξεων |
| `-t` | Παράλληλες εργασίες (threads, προεπιλογή: 10) |
| `-x` | Επεκτάσεις αρχείων προς αναζήτηση |
| `-s` | Κωδικοί κατάστασης HTTP που περιλαμβάνονται στα αποτελέσματα |
| `-b` | Κωδικοί κατάστασης HTTP που εξαιρούνται |
| `-o` | Αρχείο εξόδου |
| `-k` | Παράλειψη επαλήθευσης πιστοποιητικού TLS |
| `-r` | Ακολούθηση ανακατευθύνσεων |
| `-a` | Τιμή κεφαλίδας User-Agent |
| `-c` | Cookies συνεδρίας |
| `-H` | Προσαρμοσμένες κεφαλίδες HTTP |

### Βασική αναζήτηση καταλόγων
```bash
# Απλή αναζήτηση καταλόγων
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Με περισσότερες παράλληλες εργασίες
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Αναζήτηση ανά επέκταση αρχείου
```bash
# Αναζήτηση συγκεκριμένων επεκτάσεων αρχείων
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Πολλαπλές επεκτάσεις αρχείων
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Αντίγραφα ασφαλείας
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Φιλτράρισμα βάσει κωδικών κατάστασης HTTP

> **Φίλτρα και πραγματικά ευρήματα:** Το `--no-status`, όπου υποστηρίζεται, κρύβει την εμφάνιση του κωδικού κατάστασης· δεν σημαίνει «εμφάνισε και τα 404». Αποκρίσεις 200, 301 ή 403 δεν αποδεικνύουν από μόνες τους ότι βρέθηκε ενδιαφέρων πόρος. Συγκρίνετε περιεχόμενο και μήκος με μια ανύπαρκτη διαδρομή, γιατί ένας server μπορεί να απαντά παρόμοια σε όλες τις τιμές.

```bash
# Εμφάνιση μόνο συγκεκριμένων κωδικών κατάστασης HTTP
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Εξαίρεση κωδικών κατάστασης HTTP
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Απόκρυψη κωδικών κατάστασης από την εμφάνιση· δεν αλλάζει το φίλτρο 404
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Επιλογές αποτελεσμάτων
```bash
# Αποθήκευση σε αρχείο
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Αναλυτική έξοδος
gobuster dir -u http://target.com -w wordlist.txt -v

# Απόκρυψη ένδειξης προόδου
gobuster dir -u http://target.com -w wordlist.txt -q

# Εκτεταμένη έξοδος με πλήρη URL
gobuster dir -u http://target.com -w wordlist.txt -e
```

### Επιλογές SSL/TLS

> **Τι κάνει το -k:** Παραλείπει την επαλήθευση του πιστοποιητικού TLS. Η σύνδεση εξακολουθεί να είναι HTTPS, αλλά η ταυτότητα του server δεν επαληθεύεται με τον συνήθη τρόπο. Η πρόσθετη επιλογή `--no-tls-validation` του πρωτοτύπου δεν υποστηρίζεται απαραίτητα από την έκδοσή σας.

```bash
# Παράλειψη επαλήθευσης πιστοποιητικού TLS
gobuster dir -u https://target.com -w wordlist.txt -k

# Παράλειψη επαλήθευσης πιστοποιητικού με επιλογή του πρωτοτύπου
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Παραδείγματα αναζήτησης καταλόγων
```bash
# Τυπική αναζήτηση καταλόγων ιστοτόπου
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# Εφαρμογή PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Εντοπισμός διαδρομών API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Αναζήτηση αντιγράφων ασφαλείας
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="section-05"></a>

## 🌐 Αναζήτηση υποτομέων DNS (dns)

> **Απαντήσεις wildcard:** Αν πολλά ανύπαρκτα ονόματα επιλύονται στην ίδια διεύθυνση, μπορεί να υπάρχει εγγραφή wildcard. Αυτό δεν αποδεικνύει ότι κάθε υποψήφιος υποτομέας αντιστοιχεί σε διαφορετικό σύστημα. Το `8.8.8.8:53` του παραδείγματος είναι ένας resolver με θύρα 53.


### Βασική σύνταξη
```bash
gobuster dns -d <domain> -w <wordlist>
```

### Επιλογές DNS

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-d` | Όνομα τομέα στόχου |
| `-w` | Διαδρομή αρχείου λίστας λέξεων |
| `-t` | Παράλληλες εργασίες (threads) |
| `-r` | Προσαρμοσμένος διακομιστής επίλυσης DNS |
| `-i` | Εμφάνιση διευθύνσεων IP |
| `-c` | Εμφάνιση εγγραφών CNAME |
| `--wildcard` | Συνέχιση αναζήτησης όταν εντοπίζονται απαντήσεις wildcard |
| `--timeout` | Χρονικό όριο ερωτήματος DNS |

### Βασική αναζήτηση υποτομέων DNS
```bash
# Απλή αναζήτηση υποτομέων
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Εμφάνιση IP
gobuster dns -d target.com -w subdomains.txt -i

# Εμφάνιση εγγραφών CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

### Επιλογή διακομιστή επίλυσης DNS
```bash
# Χρήση συγκεκριμένου διακομιστή DNS
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Ένας DNS resolver με ρητή θύρα, όχι πολλοί resolvers
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### Παραδείγματα DNS
```bash
# Εκτενής αναζήτηση υποτομέων
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Σύντομη αναζήτηση υποτομέων
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Με αποθήκευση αποτελεσμάτων
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="section-06"></a>

## 🏠 Αναζήτηση εικονικών ιστοτόπων (vhost)

> **DNS και VHost είναι διαφορετικοί έλεγχοι:** Το VHost αφορά την επιλογή ιστοτόπου μέσω του ονόματος που στέλνεται στον web server. Ελέγξτε αν η λίστα περιέχει πλήρη ονόματα ή μόνο προθέματα· αυτό επηρεάζει τη χρήση `--append-domain`. Πανομοιότυπες αποκρίσεις για όλα τα ονόματα χρειάζονται διερεύνηση.


### Βασική σύνταξη
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### Επιλογές VHost

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | URL στόχου |
| `-w` | Διαδρομή αρχείου λίστας λέξεων |
| `-t` | Παράλληλες εργασίες (threads) |
| `--append-domain` | Προσθήκη του domain στο τέλος κάθε εγγραφής της λίστας |
| `--domain` | Domain για τη δημιουργία ονομάτων VHost |
| `-r` | Ακολούθηση ανακατευθύνσεων |

### Βασική αναζήτηση εικονικών ιστοτόπων
```bash
# Απλή αναζήτηση εικονικών ιστοτόπων
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Με προσθήκη domain στις εγγραφές της λίστας
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### Παραδείγματα VHost
```bash
# Τυπική αναζήτηση εικονικών ιστοτόπων
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Συστήματα περιβάλλοντος ανάπτυξης
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Με προσαρμοσμένο domain
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="section-07"></a>

## 🔧 Δοκιμές διαφορετικών εισόδων (fuzz)

### Βασική σύνταξη
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Επιλογές fuzzing

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-u` | URL με το σημείο αντικατάστασης FUZZ |
| `-w` | Διαδρομή αρχείου λίστας λέξεων |
| `-t` | Παράλληλες εργασίες (threads) |
| `-b` | Εξαίρεση κωδικών κατάστασης HTTP |
| `--exclude-length` | Εξαίρεση βάσει μήκους απόκρισης |

### Παραδείγματα fuzzing
```bash
# Δοκιμές διαφορετικών τιμών παραμέτρου
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Δοκιμές διαφορετικών διαδρομών
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Εξαίρεση συγκεκριμένων μηκών απόκρισης
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="section-08"></a>

## 📚 Λίστες λέξεων (wordlists)

### Τοποθεσίες SecLists στο Kali Linux

```bash
# Αναζήτηση καταλόγων και αρχείων
/usr/share/seclists/Discovery/Web-Content/

# Συνήθεις λίστες ονομάτων καταλόγων
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS και υποτομείς
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Συνήθεις προεγκατεστημένες λίστες λέξεων

```bash
# Λίστες λέξεων Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Λίστες λέξεων Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Επιλογή κατάλληλης λίστας λέξεων

| Χρήση | Λίστα λέξεων |
| ---------- | ---------- |
| Σύντομη σάρωση | `common.txt` |
| Τυπική σάρωση | `directory-list-2.3-medium.txt` |
| Εκτενής σάρωση | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Υποτομείς | `subdomains-top1million-5000.txt` |
| Αρχεία | `raft-large-files.txt` |

---

<a id="section-09"></a>

## 🔐 Ταυτοποίηση και κεφαλίδες HTTP

### Βασική ταυτοποίηση HTTP (Basic Authentication)
```bash
# Βασική ταυτοποίηση HTTP
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Ταυτοποίηση μέσω cookies
```bash
# Ένα cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Πολλαπλά cookies
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# Cookie συνεδρίας PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Προσαρμοσμένες κεφαλίδες HTTP
```bash
# Μία κεφαλίδα HTTP
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Πολλαπλές κεφαλίδες HTTP
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# Κλειδί API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# Προσαρμοσμένη κεφαλίδα User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Χρήση ενδιάμεσου διακομιστή (proxy)
```bash
# Proxy HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# Proxy SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Proxy του Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="section-10"></a>

## ⚡ Ρύθμιση απόδοσης

### Ρύθμιση παράλληλων εργασιών
```bash
# Προεπιλογή: 10 παράλληλες εργασίες
gobuster dir -u http://target.com -w wordlist.txt

# Αύξηση παράλληλων εργασιών σε κατάλληλο δίκτυο
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Μείωση παράλληλων εργασιών σε αργό ή ασταθές δίκτυο
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Ρυθμίσεις χρονικών ορίων
```bash
# Αύξηση χρονικού ορίου
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Χρονικό όριο ερωτήματος DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Καθυστέρηση μεταξύ αιτημάτων
```bash
# Προσθήκη καθυστέρησης για περιορισμό ρυθμού
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Μειωμένος ρυθμός· δεν εγγυάται παράκαμψη WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="section-11"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Τυπική αναζήτηση περιεχομένου ιστοτόπου
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Παράδειγμα 2: Εντοπισμός διαδρομών API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Παράδειγμα 3: Σάρωση με ταυτοποίηση
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Παράδειγμα 4: Αναζήτηση υποτομέων
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Παράδειγμα 5: Εντοπισμός εικονικών ιστοτόπων στο HackTheBox
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Παράδειγμα 6: Μέσω του proxy του Burp Suite
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Παράδειγμα 7: Αναζήτηση αντιγράφων ασφαλείας
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Παράδειγμα 8: Αναγνώριση συστήματος διαχείρισης περιεχομένου (CMS)
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="section-12"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Βασική αναζήτηση καταλόγων | `gobuster dir -u URL -w wordlist.txt` |
| Με επεκτάσεις αρχείων | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Αναζήτηση υποτομέων | `gobuster dns -d domain.com -w wordlist.txt` |
| Αναζήτηση εικονικών ιστοτόπων | `gobuster vhost -u URL -w wordlist.txt` |
| Με αποθήκευση αποτελεσμάτων | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Παράλειψη επαλήθευσης πιστοποιητικού TLS | `gobuster dir -u URL -w wordlist.txt -k` |
| Με cookies συνεδρίας | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Μέσω ενδιάμεσου διακομιστή (proxy) | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Συνηθισμένες επιλογές

| Επιλογή | Σύντομη μορφή | Περιγραφή |
| -------- | ------- | ------------- |
| `--url` | `-u` | URL στόχου |
| `--wordlist` | `-w` | Αρχείο λίστας λέξεων |
| `--threads` | `-t` | Αριθμός παράλληλων εργασιών |
| `--extensions` | `-x` | Επεκτάσεις αρχείων |
| `--status-codes` | `-s` | Κωδικοί κατάστασης HTTP που περιλαμβάνονται στα αποτελέσματα |
| `--cookies` | `-c` | Cookies προς αποστολή |
| `--output` | `-o` | Αρχείο εξόδου |
| `--insecuressl` | `-k` | Παράλειψη επαλήθευσης πιστοποιητικού TLS |
| `--verbose` | `-v` | Αναλυτική έξοδος |
| `--quiet` | `-q` | Περιορισμένη έξοδος |

---

<a id="section-13"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές αποδοτικής χρήσης

1. **Ξεκινήστε με μικρή λίστα και επεκτείνετε την αναζήτηση**
   ```bash
   # Αρχικά σύντομη σάρωση
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Στη συνέχεια μεγαλύτερη λίστα λέξεων
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Επιλέξτε επεκτάσεις ανάλογα με την εφαρμογή**
   ```bash
   # Ιστότοπος PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # Ιστότοπος ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Συνδυάστε το με άλλα εργαλεία**
   ```bash
   # Ροή εργασίας Nmap → Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Ρυθμίσεις ρυθμού και αιτημάτων

> **Περιορισμός των παραδειγμάτων:** Καθυστέρηση, αλλαγή User-Agent ή χρήση proxy δεν εγγυώνται ανωνυμία ή αποφυγή καταγραφής. Οι τιμές threads και timeout είναι ενδεικτικές και χρειάζονται προσαρμογή στο συμφωνημένο πεδίο και στον επιτρεπόμενο ρυθμό ελέγχου.


```bash
# Αργή σάρωση με καθυστέρηση
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Προσαρμοσμένη κεφαλίδα User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Παράδειγμα μέσω Tor· δεν εγγυάται ανωνυμία
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

<a id="section-14"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το Gobuster μόνο για **εξουσιοδοτημένους ελέγχους**.
> 
> - ✅ Έλεγχοι σε συστήματα που σας ανήκουν
> - ✅ Έλεγχοι με ρητή γραπτή άδεια
> - ✅ Χρήση σε ασκήσεις CTF και εργαστήρια
> - ❌ Μη σαρώνετε χωρίς εξουσιοδότηση
> - ❌ Η μη εξουσιοδοτημένη αναζήτηση πληροφοριών μπορεί να είναι παράνομη
> 
> **Εξασφαλίζετε άδεια πριν από τη σάρωση οποιουδήποτε συστήματος.**

---

<a id="section-15"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο Gobuster στο GitHub](https://github.com/OJ/gobuster)
- [Τεκμηρίωση Gobuster](https://github.com/OJ/gobuster/wiki)

### Λίστες λέξεων (wordlists)
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Σχετικοί οδηγοί
- [Nmap](../../Nmap/translations/README.el.md)
- [Burp Suite](../../Burp-Suite/translations/README.el.md)
- [SQLMap](../../SQLMap/translations/README.el.md)

---

<p align="center">
  <b>🚀 Εξασκηθείτε στην αναζήτηση περιεχομένου ιστοτόπων!</b><br>
  <i>Ανακαλύψτε διαδρομές και ονόματα που χρειάζονται περαιτέρω έλεγχο</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
