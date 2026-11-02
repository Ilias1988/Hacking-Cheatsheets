# 📚 Wordlists — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Οδηγός επιλογής και προσαρμογής λιστών για ελέγχους ασφάλειας</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Μια **wordlist** είναι λίστα υποψήφιων τιμών: κωδικών, ονομάτων χρήστη, καταλόγων, υποτομέων ή άλλων δοκιμαστικών εισόδων. Συνήθως κάθε γραμμή περιέχει μία τιμή. Το κατάλληλο αρχείο εξαρτάται από το εργαλείο και το συγκεκριμένο ερώτημα· μια λίστα κωδικών δεν αντικαθιστά αυτόματα μια λίστα διαδρομών web.

**Βασικοί όροι:** *charset* = σύνολο επιτρεπόμενων χαρακτήρων, *pattern* = μοτίβο, *rule* = κανόνας μετασχηματισμού, *deduplication* = αφαίρεση διπλοτύπων, *encoding* = κωδικοποίηση χαρακτήρων. Τα K/M στους πίνακες σημαίνουν κατά προσέγγιση χιλιάδες/εκατομμύρια εγγραφών, ενώ KB/MB αφορούν μέγεθος αρχείου.

Ξεκινήστε με μικρό, σχετικό σύνολο και επιβεβαιώστε μορφή, κωδικοποίηση και επιτρεπόμενο ρυθμό δοκιμών. Περισσότερες γραμμές δεν εγγυώνται εύρημα ή πλήρη κάλυψη. Οι αγγλικές διαδρομές, τα ονόματα αρχείων και οι εντολές διατηρούνται από το πρωτότυπο και δεν εκτελούνται εδώ.

## 📋 Περιεχόμενα

- [Λίστες υποψήφιων κωδικών](#section-01)
- [Λίστες καταλόγων και αρχείων](#section-02)
- [Λίστες υποψήφιων υποτομέων](#section-03)
- [Λίστες ονομάτων χρηστών](#section-04)
- [Λίστες δοκιμαστικών εισόδων web](#section-05)
- [Συλλογή SecLists](#section-06)
- [Συνήθεις λίστες της εγκατάστασης Kali](#section-07)
- [Εργαλεία δημιουργίας δικών σας λιστών](#section-08)
- [Σύντομη αναφορά λήψεων](#section-09)
- [Συμβουλές και καλές πρακτικές](#section-10)
- [Γρήγορη αναφορά](#section-11)
- [Πηγές](#section-12)

---

<a id="section-01"></a>

## 🔐 Λίστες υποψήφιων κωδικών

> **Στιγμιότυπο συλλογών:** Τα μεγέθη και τα πλήθη εγγραφών είναι ενδεικτικά στοιχεία του πρωτοτύπου. Έκδοση αρχείου, συμπίεση, κωδικοποίηση και αφαίρεση διπλοτύπων τα αλλάζουν. Ένα όνομα που αναφέρει εκατομμύρια γραμμές δεν εγγυάται ισάριθμες μοναδικές ή κατάλληλες τιμές.


### Συνήθεις επιλογές του πρωτοτύπου

| Λίστα υποψηφίων | Μέγεθος | Γραμμές | Προέλευση | Χρήση |
| ---------- | ------ | ------- | -------- | ---------- |
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Έλεγχος αντοχής κωδικών πρόσβασης |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Συνηθισμένοι κωδικοί |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Μεγάλη λίστα υποψήφιων κωδικών |
| **common-passwords-win.txt** | - | - | SecLists | Υποψήφιοι κωδικοί για σενάρια Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Λίστα ενός εκατομμυρίου υποψήφιων κωδικών |

### Λίστες κωδικών ανά τύπο

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Εξειδικευμένες λίστες κωδικών

| Λίστα υποψηφίων | Σκοπός | Τοποθεσία |
| ---------- | --------- | ---------- |
| **wifi-passwords.txt** | Έλεγχος υποψήφιων κωδικών WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Επιλογή βάσει στατιστικών μοτίβων | SecLists/Passwords/ |
| **seasons-only.txt** | Εποχικά μοτίβα | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Λίστα συχνά απαγορευμένων κωδικών | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Προεπιλεγμένα στοιχεία πρόσβασης | SecLists/Passwords/Default-Credentials/ |

### Θέσεις αρχείων στο Kali

> **Αποσυμπίεση και χώρος:** Το gunzip χωρίς επιλογή διατήρησης αντικαθιστά συνήθως το συμπιεσμένο αρχείο με το αποσυμπιεσμένο. Χρειάζεται αρκετός χώρος και κατάλληλο δικαίωμα εγγραφής στη συγκεκριμένη θέση. Η κατάληξη gz δεν είναι λίστα που διαβάζει κάθε εργαλείο απευθείας.


```bash
# RockYou — πρώτα αποσυμπίεση
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="section-02"></a>

## 📂 Λίστες καταλόγων και αρχείων

> **Επιβεβαιώστε το πραγματικό αρχείο:** DIRB, DirBuster και SecLists είναι διαφορετικές συλλογές. Ίδιο ή παρόμοιο όνομα δεν αποδεικνύει ίδια διαδρομή ή περιεχόμενο. Τα δενδρογράμματα παρακάτω είναι παραδείγματα δομής, όχι εντολές προς αντιγραφή στο shell.

> **Διόρθωση προέλευσης:** Το common.txt με περίπου 4.6K εγγραφές του πίνακα αντιστοιχεί στη συνήθη λίστα DIRB, όπως φαίνεται στην [επίσημη τεκμηρίωση Kali για το DIRB](https://www.kali.org/tools/dirb/).


### Δοκιμή υποψήφιων ονομάτων καταλόγων

| Λίστα υποψηφίων | Μέγεθος | Γραμμές | Προέλευση | Χρήση |
| ---------- | ------ | ------- | -------- | ---------- |
| **common.txt** | 36 KB | 4.6K | DIRB | Σύντομη αρχική αναζήτηση |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Αρχική καταγραφή με μικρή λίστα |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Καταγραφή με μεσαίο εύρος δοκιμών |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Ευρύτερη καταγραφή χωρίς εγγύηση πληρότητας |
| **big.txt** | 210 KB | 20K | SecLists | Συνήθη ονόματα καταλόγων |

### Σειρά RAFT — ενδεικτική επιλογή του πρωτοτύπου

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K ονόματα καταλόγων, κατά το πρωτότυπο
├── raft-large-files.txt            # 37K ονόματα αρχείων, κατά το πρωτότυπο
├── raft-large-words.txt            # 120K λέξεις, κατά το πρωτότυπο
├── raft-medium-directories.txt     # 30K ονόματα καταλόγων, κατά το πρωτότυπο
├── raft-medium-files.txt           # 17K ονόματα αρχείων, κατά το πρωτότυπο
├── raft-medium-words.txt           # 63K λέξεις, κατά το πρωτότυπο
├── raft-small-directories.txt      # 20K ονόματα καταλόγων, κατά το πρωτότυπο
├── raft-small-files.txt            # 11K ονόματα αρχείων, κατά το πρωτότυπο
└── raft-small-words.txt            # 43K λέξεις, κατά το πρωτότυπο
```

### Λίστες επεκτάσεων αρχείων

| Λίστα υποψηφίων | Σκοπός |
| ---------- | --------- |
| **web-extensions.txt** | Συνήθεις επεκτάσεις αρχείων web |
| **backup-extensions.txt** | Αντίγραφα ασφαλείας (.bak, .old) |
| **raft-large-extensions.txt** | Εκτεταμένη λίστα επεκτάσεων |

### Εξειδικευμένη αναζήτηση

| Λίστα υποψηφίων | Σκοπός | Γραμμές |
| ---------- | --------- | ------- |
| **apache.txt** | Ονόματα σχετικά με Apache | - |
| **nginx.txt** | Ονόματα σχετικά με Nginx | - |
| **iis.txt** | Ονόματα σχετικά με IIS | - |
| **api-endpoints.txt** | Αναζήτηση σημείων API | - |
| **graphql.txt** | Σημεία GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Συνήθεις θέσεις αρχείων

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Κατάλογος Discovery του SecLists
/usr/share/seclists/Discovery/Web-Content/

# Ενδεικτικά ονόματα αρχείων για λήψη
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="section-03"></a>

## 🌐 Λίστες υποψήφιων υποτομέων

### Συνήθεις λίστες υποτομέων

| Λίστα υποψηφίων | Γραμμές | Προέλευση | Χρήση |
| ---------- | ------- | -------- | ---------- |
| **subdomains-top1million-5000.txt** | 5K | SecLists | Σύντομη αρχική καταγραφή |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Καταγραφή με μεσαίο εύρος δοκιμών |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Αναλυτικότερη καταγραφή |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Έρευνα Bug Bounty μέσα στο scope |
| **fierce-hostlist.txt** | - | SecLists | Μορφή κατάλληλη για το Fierce, ανά έκδοση |
| **combined_subdomains.txt** | 648K | SecLists | Συνδυασμένη λίστα |

### Θέση λιστών υποτομέων

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Ενδεικτική επιλογή του πρωτοτύπου για Bug Bounty
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Ενδεικτικές επιλογές για Bug Bounty

```bash
# Μεθοδολογία Jason Haddix
dns-Jhaddix.txt              # 2M+ υποψήφια ονόματα, κατά το πρωτότυπο

# Συνδυασμός για ευρύτερο σύνολο υποψηφίων
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="section-04"></a>

## 👤 Λίστες ονομάτων χρηστών

### Συνήθεις λίστες ονομάτων χρηστών

| Λίστα υποψηφίων | Γραμμές | Σκοπός |
| ---------- | ------- | --------- |
| **top-usernames-shortlist.txt** | 17 | Αρχικός σύντομος έλεγχος |
| **xato-net-10-million-usernames.txt** | 10M | Μεγάλη λίστα |
| **cirt-default-usernames.txt** | - | Προεπιλεγμένα ονόματα χρηστών |
| **names.txt** | - | Συνήθη ονόματα |
| **familynames-usa-top1000.txt** | 1K | Επώνυμα |

### Θέση λιστών ονομάτων χρηστών

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Προεπιλεγμένα στοιχεία σύνδεσης

| Υπηρεσία | Λίστα υποψηφίων |
| --------- | ---------- |
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="section-05"></a>

## 💉 Λίστες δοκιμαστικών εισόδων web

### Έγχυση SQL (SQL Injection)

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS — εκτέλεση κώδικα στον browser (Cross-Site Scripting)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI — συμπερίληψη τοπικού ή απομακρυσμένου αρχείου

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Εισαγωγή εντολών λειτουργικού συστήματος

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI — Έγχυση εκφράσεων σε πρότυπα του server

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Συλλογές δοκιμαστικών εισόδων

| Λίστα υποψηφίων | Σκοπός |
| ---------- | --------- |
| **special-chars.txt** | Ειδικοί χαρακτήρες |
| **Unicode.txt** | Δοκιμές χαρακτήρων Unicode |
| **big-list-of-naughty-strings.txt** | Οριακές και ασυνήθιστες είσοδοι |
| **fuzz-Bo0oM.txt** | Γενικές δοκιμές διαφορετικών εισόδων |
| **burp-parameter-names.txt** | Ονόματα παραμέτρων |

---

<a id="section-06"></a>

## 📦 Συλλογή SecLists

> **Διαδρομές, προέλευση και όροι χρήσης:** Ονόματα και φάκελοι αλλάζουν ανά έκδοση. Ελέγξτε το πραγματικό δέντρο και τις συνοδευτικές πληροφορίες πριν χρησιμοποιήσετε ή αναδιανείμετε μια λίστα. Η συλλογή συγκεντρώνει υλικό από πολλές πηγές· δείτε το [επίσημο SecLists](https://github.com/danielmiessler/SecLists). Η εντολή εγκατάστασης πακέτου απαιτεί να διατίθεται το πακέτο στα αποθετήρια της δικής σας διανομής.


### Τι είναι το SecLists

Το **SecLists** συγκεντρώνει διαφορετικούς τύπους λιστών που χρησιμοποιούνται σε ελέγχους ασφάλειας.

### Εγκατάσταση

```bash
# Kali Linux — η προεγκατάσταση εξαρτάται από το προφίλ εγκατάστασης
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Τοπικό αντίγραφο μέσω Git
git clone https://github.com/danielmiessler/SecLists.git

# Λήψη συγκεκριμένης λίστας
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Δομή SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Λίστες υποτομέων
│   ├── Web-Content/            # Λίστες καταλόγων και αρχείων
│   └── Infrastructure/         # Υπηρεσίες και θύρες
├── Fuzzing/
│   ├── SQLi/                   # Δοκιμαστικές είσοδοι SQL injection
│   ├── XSS/                    # Δοκιμαστικές είσοδοι XSS
│   ├── LFI/                    # Δοκιμαστικές είσοδοι LFI/RFI
│   └── command-injection*.txt  # Εισαγωγή εντολών λειτουργικού συστήματος
├── Passwords/
│   ├── Common-Credentials/     # Λίστες υποψήφιων κωδικών
│   ├── Leaked-Databases/       # rockyou και άλλες συλλογές
│   └── Default-Credentials/    # Προεπιλεγμένοι κωδικοί
├── Usernames/
│   ├── Names/                  # Λίστες ονομάτων
│   └── top-usernames*.txt      # Λίστες ονομάτων χρηστών
├── Payloads/
│   └── Anti-Antivirus/         # Υλικό δοκιμών φίλτρων AV· δεν εγγυάται παράκαμψη
└── Pattern-Matching/
    └── Hunting/                # Κανονικές εκφράσεις
```

### Αποθετήριο στο GitHub

🔗 https://github.com/danielmiessler/SecLists

---

<a id="section-07"></a>

## 🐧 Συνήθεις λίστες της εγκατάστασης Kali

> **Δεν είναι ίδια κάθε εγκατάσταση:** Η διαθεσιμότητα εξαρτάται από πακέτα, έκδοση και προφίλ εγκατάστασης. Πολλές εγγραφές του φακέλου wordlists είναι συμβολικοί σύνδεσμοι σε άλλες συλλογές. Το nmap.lst είναι λίστα δεδομένων κωδικών και όχι κατάλογος scripts Nmap. Βλ. [πακέτο wordlists](https://www.kali.org/tools/wordlists/) και [πακέτο seclists](https://www.kali.org/tools/seclists/).


### Θέση λιστών

```bash
# Κύριος φάκελος λιστών
/usr/share/wordlists/

# Περιεχόμενα
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Αποσυμπίεση με gunzip
├── dirb/                   # Λίστες DIRB
├── dirbuster/              # Λίστες DirBuster
├── fasttrack.txt           # Λίστα FastTrack
├── fern-wifi/              # Λίστες WiFi
├── metasploit/             # Λίστες Metasploit Framework
├── nmap.lst                # Λίστα δεδομένων κωδικών Nmap· όχι κατάλογος scripts
├── wfuzz/                  # Λίστες wFuzz
└── seclists -> /usr/share/seclists/
```

### Λίστες DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### Λίστες DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K εγγραφές, κατά το πρωτότυπο
├── common.txt               # 4.6K εγγραφές, κατά το πρωτότυπο
├── small.txt                # 959 εγγραφές, κατά το πρωτότυπο
├── catala.txt               # Καταλανικά
├── euskera.txt              # Βασκικά
├── extensions_common.txt    # Επεκτάσεις αρχείων
├── indexes.txt              # Ονόματα αρχείων index
├── mutations_common.txt     # Μετασχηματισμοί
├── spanish.txt              # Ισπανικά
├── stress/                  # Δοκιμές φόρτου
└── vulns/                   # Έλεγχοι πιθανών αδυναμιών
```

---

<a id="section-08"></a>

## 🛠️ Εργαλεία δημιουργίας δικών σας λιστών

### CeWL — δημιουργία λίστας από περιεχόμενο ιστοτόπου

> **Ενεργή συλλογή:** Το CeWL επισκέπτεται τον ιστότοπο και δεν είναι απλή αναζήτηση σε τοπικό αρχείο. Ελέγξτε scope, βάθος, ταυτοποίηση και ρυθμό. Οι επιλογές συλλογής email και οι θέσεις εξόδου χρειάζονται επιβεβαίωση στη βοήθεια της έκδοσης.


```bash
# Εγκατάσταση
sudo apt install cewl

# Βασική χρήση — ενεργή περιήγηση σε ιστότοπο
cewl https://target.com -w wordlist.txt

# Με βάθος περιήγησης και ελάχιστο μήκος λέξης
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Συμπερίληψη διευθύνσεων email
cewl https://target.com -e -w wordlist.txt

# Επιλογές
-d <depth>      # Βάθος περιήγησης, προεπιλογή 2 κατά το πρωτότυπο
-m <length>     # Ελάχιστο μήκος λέξης, προεπιλογή 3 κατά το πρωτότυπο
-w <file>       # Αρχείο εξόδου
-e              # Συμπερίληψη διευθύνσεων email
--lowercase     # Μετατροπή σε πεζά
-c              # Καταμέτρηση εμφανίσεων λέξεων
```

### Crunch — παραγωγή συνδυασμών χαρακτήρων

> **Οι συνδυασμοί αυξάνονται εκθετικά:** Για σταθερό μήκος n και σύνολο c χαρακτήρων υπάρχουν έως c στη δύναμη n τιμές. Η παραγωγή μεγάλης λίστας μπορεί να απαιτήσει πολύ χρόνο και χώρο, ενώ η διοχέτευση δεν μειώνει το πλήθος των υποψηφίων. Το παράδειγμα Python παρακάτω χρησιμοποιεί επίσης καρτεσιανό γινόμενο.


```bash
# Εγκατάσταση
sudo apt install crunch

# Βασική σύνταξη
crunch <min-len> <max-len> [charset] -o <output>

# Παραγωγή υποψήφιων τιμών μήκους 4–6 χαρακτήρων
crunch 4 6 -o wordlist.txt

# Με συγκεκριμένο σύνολο χαρακτήρων
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Με μοτίβο
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = πεζό, , = κεφαλαίο, % = αριθμός, ^ = ειδικός χαρακτήρας

# Προσαρμοσμένο σύνολο χαρακτήρων
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Διοχέτευση στο hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP — δημιουργία υποψηφίων βάσει προφίλ

```bash
# Εγκατάσταση
git clone https://github.com/Mebus/cupp.git
cd cupp

# Διαδραστική λειτουργία βάσει προφίλ
python3 cupp.py -i

# Ενδεικτικές ερωτήσεις:
# - Όνομα, επώνυμο και ψευδώνυμο
# - Ημερομηνία γέννησης
# - Στοιχεία συντρόφου
# - Όνομα κατοικιδίου
# - Όνομα εταιρείας
# - Λέξεις-κλειδιά

# Λήψη λιστών συνηθισμένων κωδικών
python3 cupp.py -l

# Χρήση υπάρχουσας λίστας με προσαρμογή προφίλ
python3 cupp.py -w wordlist.txt
```

### Mentalist — γραφικό εργαλείο δημιουργίας λιστών

```bash
# Γραφικό εργαλείο σύνθετης δημιουργίας λιστών
# Υποστηρίζει κανόνες, αλλαγές πεζών/κεφαλαίων και αντικαταστάσεις
# Λήψη από: https://github.com/sc0tfree/mentalist
```

### Παράδειγμα δημιουργίας λίστας με Python

```python
#!/usr/bin/env python3
# Απλή δημιουργία λίστας με Python
import itertools

# Παραγωγή συνδυασμών
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Κανόνες μετασχηματισμού Hashcat

```bash
# Χρήση κανόνων για μετασχηματισμό λίστας
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Συνήθεις κανόνες του πρωτοτύπου
/usr/share/hashcat/rules/
├── best64.rule         # Κανόνες best64
├── rockyou-30000.rule  # Βασισμένο στο RockYou
├── d3ad0ne.rule        # Κανόνες d3ad0ne
├── dive.rule           # Κανόνες DIVE
└── toggles1-5.rule     # Αλλαγή πεζών και κεφαλαίων
```

---

<a id="section-09"></a>

## ⬇️ Σύντομη αναφορά λήψεων

### Λήψεις με σύντομες εντολές

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# Λήψη δέντρου SecLists με περιορισμένο ιστορικό
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Λίστα συνηθισμένων κωδικών ενός εκατομμυρίου εγγραφών
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Μεσαία λίστα καταλόγων
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Λίστα υποτομέων
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# Λίστα Jhaddix DNS για Bug Bounty
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Δημιουργία τοπικού αντιγράφου αποθετηρίων

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Λίστες λέξεων Assetnote
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="section-10"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Επιλογή κατάλληλης λίστας

| Εργασία | Ενδεικτική λίστα |
| ------ | --------------------- |
| **Αρχικός σύντομος έλεγχος καταλόγων** | common.txt, raft-small-directories.txt |
| **Αναλυτικότερος έλεγχος καταλόγων** | directory-list-2.3-medium.txt |
| **Ευρύτερος έλεγχος καταλόγων** | directory-list-2.3-big.txt |
| **Καταγραφή υποτομέων** | subdomains-top1million-20000.txt |
| **Έρευνα DNS για Bug Bounty** | dns-Jhaddix.txt |
| **Έλεγχος αντοχής κωδικών πρόσβασης** | rockyou.txt |
| **Μικρή λίστα συνηθισμένων κωδικών** | top-10000.txt |

### Συνδυασμός λιστών

> **Μία γραμμή δεν ισούται πάντα με μία επιθυμητή λέξη:** Το απλό read του shell μπορεί να επηρεάζεται από διαχωριστικά και backslashes. Επιβεβαιώστε το περιεχόμενο πριν και μετά τις μετατροπές, ειδικά σε λίστες που περιέχουν κενά ή ειδικούς χαρακτήρες.


```bash
# Συνένωση και αφαίρεση διπλοτύπων
cat list1.txt list2.txt | sort -u > combined.txt

# Προσθήκη προθέματος σε όλες τις γραμμές
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Προσθήκη επιθέματος
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Και οι δύο μετασχηματισμοί
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Προσαρμογή και καθαρισμός λιστών

> **Μορφή και μετασχηματισμοί:** LF/CRLF, κενές γραμμές, BOM και κωδικοποίηση μπορούν να αλλάξουν τι διαβάζει ένα εργαλείο. Οι εντολές κεφαλαίων/πεζών δεν εγγυώνται πλήρη μετατροπή όλων των χαρακτήρων Unicode. Το sort αλλάζει και τη σειρά προτεραιότητας, ενώ sort και sed -i του παραδείγματος μπορούν να τροποποιήσουν το αρχικό αρχείο· εργαστείτε σε αντίγραφο όταν χρειάζεται να το διατηρήσετε.


```bash
# Ταξινόμηση και αφαίρεση διπλοτύπων
sort -u wordlist.txt -o wordlist.txt

# Αφαίρεση κενών γραμμών
sed -i '/^$/d' wordlist.txt

# Αφαίρεση γραμμών με μήκος μικρότερο από 4, ανά τρόπο μέτρησης του εργαλείου
awk 'length >= 4' wordlist.txt > filtered.txt

# Μετατροπή σε πεζά
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Καταμέτρηση γραμμών
wc -l wordlist.txt
```

### Συμβουλές απόδοσης

1. **Ξεκινήστε με μικρή λίστα** — ελέγξτε πρώτα την απόκριση και επεκτείνετε σταδιακά.
2. **Χρησιμοποιήστε κατάλληλα μοτίβα** — όταν γνωρίζετε τη μορφή, περιορίστε τους υποψηφίους.
3. **Συνδυάστε πηγές** — προσαρμοσμένη λίστα και κατάλληλες έτοιμες συλλογές.
4. **Επιλέξτε σχετικές λέξεις** — το CeWL μπορεί να αντλήσει όρους από εγκεκριμένο ιστότοπο.

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Ενδεικτικές λίστες ανά κατηγορία

| Κατηγορία | Ενδεικτική επιλογή του πρωτοτύπου | Τοποθεσία |
| ---------- | ------------- | ---------- |
| **Κωδικοί πρόσβασης** | rockyou.txt | SecLists/Passwords/ |
| **Κατάλογοι** | directory-list-2.3-medium.txt | DirBuster |
| **Αρχεία** | raft-large-files.txt | SecLists |
| **Υποτομείς** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Ονόματα χρηστών** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Οδηγός μεγέθους

| Μέγεθος | Γραμμές | Χρήση |
| ------ | ------- | ---------- |
| **Μικρή** | < 5K | Σύντομες αρχικές δοκιμές |
| **Μεσαία** | 5K - 100K | Καταγραφή με μεσαίο εύρος δοκιμών |
| **Μεγάλη** | 100K - 1M | Αναλυτικότερος έλεγχος |
| **Πολύ μεγάλη** | > 1M | Ευρύτερη κάλυψη χωρίς εγγύηση πληρότητας |

---

<a id="section-12"></a>

## 📚 Πηγές

### Αποθετήρια λιστών
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Λίστες λέξεων Assetnote](https://github.com/assetnote/wordlists)

### Εργαλεία
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Σχετικοί οδηγοί
- [Hydra](../../Hydra/translations/README.el.md)
- [John the Ripper](../../John-The-Ripper/translations/README.el.md)
- [Hashcat](../../Hashcat/translations/README.el.md)
- [Gobuster](../../Gobuster/translations/README.el.md)
- [ffuf](../../ffuf/translations/README.el.md)

---

<p align="center">
  <b>📚 Επιλέξτε λίστα κατάλληλη για το συγκεκριμένο ερώτημα!</b><br>
  <i>Μικρά αρχικά σύνολα, σωστή μορφή και ελεγχόμενη επέκταση</i>
</p>

**Επιστροφή:** [Κεντρική ελληνική σελίδα](../../README.el.md)
