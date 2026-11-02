# 🔨 John the Ripper — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  Εργαλείο ελέγχου και ανάκτησης κωδικών
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 Εργαλείο ελέγχου αντοχής και ανάκτησης κωδικών</b>
</p>

---

## Πριν ξεκινήσετε

Το **John the Ripper** δοκιμάζει υποψήφιους κωδικούς απέναντι σε **hashes** ή άλλα κατάλληλα δεδομένα ελέγχου. Ένα hash είναι αποτέλεσμα μονόδρομου υπολογισμού· η δοκιμή κωδικών δεν αποτελεί απλή «αποκρυπτογράφησή» του. Το εργαλείο παράγει υποψήφιες τιμές και ελέγχει αν ταιριάζουν.

**Όροι:** *salt* = πρόσθετη τιμή που χρησιμοποιείται στον υπολογισμό hash, *format* = μορφή δεδομένων και αλγόριθμος, *candidate* = υποψήφιος κωδικός, *rule/mangling* = κανόνας παραγωγής παραλλαγών, *mask* = μοτίβο χαρακτήρων, *pot file* = τοπικό αρχείο αποτελεσμάτων που έχουν ανακτηθεί. Τα εργαλεία **2john** εξάγουν από αρχεία τη μορφή που χρειάζεται ο έλεγχος· δεν ανακτούν αυτόματα τον κωδικό.

Η έκδοση **Jumbo** προσθέτει μορφές και δυνατότητες πέρα από τη βασική έκδοση. Ξεκινήστε με αναγνώριση μορφής και προετοιμασία εισόδου, συνεχίστε με κατάλληλη λίστα ή κανόνες και ελέγξτε τα αποτελέσματα. Οι εντολές και τα δείγματα διατηρούνται από το πρωτότυπο· ορισμένα hashes είναι συντομευμένα και δεν αποτελούν έτοιμα δεδομένα δοκιμής.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Τρόποι δοκιμών για ανάκτηση κωδικών](#section-04)
- [Εξαγωγή hashes και δεδομένων ελέγχου](#section-05)
- [Συνήθεις τύποι hashes](#section-06)
- [Έλεγχος hashes κωδικών Linux](#section-07)
- [Έλεγχος hashes κωδικών Windows](#section-08)
- [Ανάκτηση κωδικών αρχείων](#section-09)
- [Κανόνες και παραγωγή παραλλαγών λέξεων](#section-10)
- [Διαχείριση συνεδριών](#section-11)
- [Ρύθμιση απόδοσης](#section-12)
- [Παραδείγματα σεναρίων ελέγχου](#section-13)
- [Γρήγορη αναφορά](#section-14)
- [Συμβουλές και καλές πρακτικές](#section-15)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-16)
- [Πηγές](#section-17)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το John the Ripper** (John/JtR) είναι δωρεάν εργαλείο ανοιχτού κώδικα για έλεγχο αντοχής κωδικών και ανάκτησή τους από hashes ή κατάλληλα δεδομένα ελέγχου.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Πολλές μορφές δεδομένων** | Υποστήριξη πολλών τύπων hashes |
| **Πολλαπλοί τρόποι δοκιμών** | Single, λίστες λέξεων και Incremental |
| **Προσαρμοσμένοι κανόνες μετασχηματισμού** | Κανόνες παραγωγής παραλλαγών λέξεων |
| **Αυτόματη αναγνώριση μορφής** | Αυτόματη εκτίμηση μορφής hash, με πιθανές ασάφειες |
| **Πολλαπλές πλατφόρμες** | Linux, Windows και macOS, ανάλογα με την έκδοση |
| **Υποστήριξη GPU** | Επιτάχυνση μέσω OpenCL στην έκδοση Jumbo |

### Σύγκριση John και Hashcat

> **Ενδεικτική σύγκριση:** Το John Jumbo υποστηρίζει GPU και το Hashcat μπορεί να χρησιμοποιεί και CPU, ανάλογα με backend και format. Το Hashcat διαθέτει επιλογή αναγνώρισης τύπων hash, οπότε το «No» του πρωτοτύπου διορθώνεται εδώ. Η αναγνώριση και στα δύο εργαλεία μπορεί να είναι αμφίσημη. Αστέρια και γενικές συγκρίσεις ταχύτητας δεν αποτελούν μετρήσεις. Δείτε την [τεκμηρίωση Hashcat](https://hashcat.net/wiki/doku.php?id=hashcat).


| Χαρακτηριστικό | John | Hashcat |
| --------- | ------ | --------- |
| **Ενδεικτική χρήση** | Έλεγχοι με επεξεργαστή (CPU) | Έλεγχοι με κάρτα γραφικών (GPU) |
| **Ευκολία χρήσης** | Πιο εύχρηστο κατά το πρωτότυπο | Πιο σύνθετο κατά το πρωτότυπο |
| **Αναγνώριση μορφής** | ✅ Εκτίμηση με πιθανές ασάφειες | ✅ Διαθέσιμη επιλογή αναγνώρισης |
| **Κανόνες μετασχηματισμού** | Ισχυρό σύνολο κανόνων | Εκτεταμένο σύνολο κανόνων |
| **Τύποι hashes** | Πολλοί | Περισσότεροι κατά το πρωτότυπο |
| **Ταχύτητα με GPU** | Καλή κατά το πρωτότυπο | Καλύτερη κατά το πρωτότυπο |

### Εκδόσεις

| Έκδοση | Περιγραφή |
| --------- | ------------- |
| **John (Core)** | Βασική έκδοση |
| **John Jumbo** | Εμπλουτισμένη έκδοση από την κοινότητα (Jumbo) |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Core, Jumbo και πακέτα:** Ονόματα πακέτων όπως `john-jumbo` και επιλογές όπως `--version` δεν είναι διαθέσιμα σε κάθε εγκατάσταση. Τα βοηθητικά 2john μπορεί να είναι scripts με διαφορετικά ονόματα ή καταλήξεις. Επιβεβαιώστε την τοπική βοήθεια και τις οδηγίες [Openwall](https://www.openwall.com/john/). Το `--list=formats | wc -l` μετρά γραμμές, όχι απαραίτητα τον αριθμό μορφών.


### Kali Linux (συνήθως προεγκατεστημένο)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Βασική έκδοση
sudo apt install john

# Έκδοση Jumbo· η διαθεσιμότητα πακέτου εξαρτάται από τη διανομή
sudo apt install john-jumbo
```

### Εγκατάσταση Jumbo από πηγαίο κώδικα
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS μέσω Homebrew
```bash
brew install john-jumbo
```

### Windows
```bash
# Λήψη από τον επίσημο ιστότοπο
https://www.openwall.com/john/

# Εναλλακτικά μέσω Chocolatey
choco install john
```

### Επιβεβαίωση εγκατάστασης
```bash
john --version
john --list=formats | wc -l    # Πρόχειρη καταμέτρηση γραμμών, όχι απαραίτητα πλήθους μορφών
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
john [options] [password_file]
```

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `--wordlist=` | Διαδρομή λίστας λέξεων |
| `--format=` | Καθορισμός μορφής hash |
| `--rules` | Ενεργοποίηση κανόνων παραγωγής παραλλαγών λέξεων |
| `--show` | Εμφάνιση κωδικών που έχουν ανακτηθεί |
| `--single` | Λειτουργία Single |
| `--incremental` | Δοκιμές συνδυασμών χαρακτήρων |
| `--session=` | Ορισμός ονόματος συνεδρίας |
| `--restore` | Επαναφορά προηγούμενης συνεδρίας |
| `--pot=` | Προσαρμοσμένο αρχείο ανακτημένων αποτελεσμάτων (pot file) |

### Πρώτη γνωριμία
```bash
# Αυτόματη εκτίμηση μορφής και δοκιμές ανάκτησης
john hashes.txt

# Με λίστα λέξεων
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Εμφάνιση κωδικών που έχουν ανακτηθεί
john --show hashes.txt
```

---

<a id="section-04"></a>

## 🎮 Τρόποι δοκιμών για ανάκτηση κωδικών

> **Όρια κάθε λειτουργίας:** Το Single αξιοποιεί ονόματα και περιγραφικά στοιχεία, όταν υπάρχουν. Το Incremental περιορίζεται από τη ρύθμιση χαρακτήρων και μήκους· τα ονόματα συνόλων διαφέρουν ανά έκδοση. Ένα external φίλτρο, όπως το παράδειγμα Filter_Alpha, δεν είναι απαραίτητα αυτόνομη γεννήτρια υποψήφιων τιμών. Δείτε τις [επιλογές John](https://www.openwall.com/john/doc/OPTIONS.shtml).


### Λειτουργία Single

Παράγει υποψήφιους κωδικούς από ονόματα χρηστών και τα περιγραφικά στοιχεία GECOS, όταν αυτά υπάρχουν στην είσοδο.

```bash
# Λειτουργία Single, με παραλλαγές ονόματος χρήστη όπου υπάρχουν σχετικά στοιχεία
john --single hashes.txt

# Με καθορισμό μορφής
john --single --format=sha512crypt hashes.txt
```

### Λειτουργία λίστας λέξεων (Wordlist)

Δοκιμάζει λέξεις από λίστα, προαιρετικά με κανόνες μετασχηματισμού.

```bash
# Βασικές δοκιμές από λίστα λέξεων
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Λίστα λέξεων με κανόνες
john --wordlist=wordlist.txt --rules hashes.txt

# Συγκεκριμένο σύνολο κανόνων
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Λειτουργία Incremental, δοκιμές συνδυασμών

Δοκιμάζει συνδυασμούς χαρακτήρων μέσα στα όρια και στο σύνολο της επιλεγμένης ρύθμισης.

```bash
# Προεπιλεγμένη ρύθμιση Incremental· το σύνολο εξαρτάται από τις ρυθμίσεις
john --incremental hashes.txt

# Συγκεκριμένο σύνολο χαρακτήρων
john --incremental=Digits hashes.txt      # Μόνο ψηφία
john --incremental=Lower hashes.txt       # Μόνο πεζά γράμματα
john --incremental=Upper hashes.txt       # Μόνο κεφαλαία γράμματα
john --incremental=Alpha hashes.txt       # Μόνο γράμματα
john --incremental=Alnum hashes.txt       # Γράμματα και ψηφία
john --incremental=LowerNum hashes.txt    # Πεζά γράμματα και ψηφία
```

### Λειτουργία μοτίβου (Mask)

Παράγει υποψήφιους κωδικούς με βάση ένα καθορισμένο μοτίβο.

```bash
# Δοκιμές μοτίβου, με σύμβολα αντικατάστασης που αρχίζουν με ?
john --mask='?l?l?l?l?d?d' hashes.txt

# Σύμβολα αντικατάστασης:
# ?l = πεζά γράμματα    ?u = κεφαλαία γράμματα
# ?d = ψηφία       ?s = ειδικοί χαρακτήρες
# ?a = συνδυασμός βασικών συνόλων    ?h = πεζά δεκαεξαδικά, όπου υποστηρίζεται
```

### Εξωτερική λειτουργία (External)

Προσαρμοσμένα προγράμματα παραγωγής ή φιλτραρίσματος υποψήφιων κωδικών.

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="section-05"></a>

## 🔍 Εξαγωγή hashes και δεδομένων ελέγχου

### Κωδικοί Linux

```bash
# Συνένωση αρχείων passwd και shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Έλεγχος του συνενωμένου αρχείου
john unshadowed.txt
```

### Hashes Windows

```bash
# Από αρχείο SAM με κατάλληλο εργαλείο, όπως pwdump ή secretsdump
# Μορφή: user:id:LM_hash:NTLM_hash:::

# Χρήση hashdump του Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Αποθήκευση σε αρχείο και έλεγχος ανάκτησης
john --format=nt hashes.txt
```

### Κωδικοί αρχείων και εργαλεία 2john

```bash
# Αρχεία ZIP
zip2john protected.zip > zip_hash.txt

# Αρχεία RAR
rar2john protected.rar > rar_hash.txt

# Αρχεία PDF
pdf2john protected.pdf > pdf_hash.txt

# Έγγραφα Office
office2john document.docx > office_hash.txt

# Κλειδιά SSH
ssh2john id_rsa > ssh_hash.txt

# Αρχεία 7-Zip
7z2john archive.7z > 7z_hash.txt

# Βάσεις κωδικών KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="section-06"></a>

## 🔢 Συνήθεις τύποι hashes

### Πίνακας μορφών hash

> **Πλήρη δεδομένα εισόδου:** Συμβολοσειρές με `...` ή κομμένα LM/NT hashes είναι ενδεικτικές και δεν μπορούν να χρησιμοποιηθούν αυτούσιες. Τα NT hashes και οι αποκρίσεις δικτυακής ταυτοποίησης NetNTLMv2 είναι διαφορετικές μορφές. Ο τύπος δεν αναγνωρίζεται με βεβαιότητα μόνο από το μήκος.


| Τύπος hash | Επιλογή μορφής | Παράδειγμα |
| ----------- | ------------- | --------- |
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 (sha512crypt) | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 (sha256crypt) | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 (md5crypt) | `--format=md5crypt` | `$1$salt$hash` |

### Προβολή όλων των μορφών
```bash
# Προβολή υποστηριζόμενων μορφών
john --list=formats

# Αναζήτηση συγκεκριμένης μορφής
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Πρόχειρη καταμέτρηση γραμμών, όχι απαραίτητα μορφών
john --list=formats | wc -l
```

### Αναγνώριση τύπου hash
```bash
# Χρήση εργαλείου hash-identifier
hash-identifier

# Εναλλακτικά το εργαλείο hashid
hashid -m '$6$salt$hash...'
```

---

<a id="section-07"></a>

## 🐧 Έλεγχος hashes κωδικών Linux

### Προετοιμασία hashes
```bash
# Συνένωση στοιχείων από passwd και shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Εναλλακτικά εμφάνιση της σχετικής εγγραφής shadow
sudo cat /etc/shadow | grep username
```

### Παραδείγματα μορφής hash

> **Raw hash και password hashing:** Τα `$1$`, `$5$` και `$6$` αντιστοιχούν σε md5crypt, sha256crypt και sha512crypt, όχι σε απλό raw MD5/SHA. Το `$y$` δηλώνει yescrypt. Η προεπιλογή ενός σύγχρονου Linux εξαρτάται από διανομή και έκδοση, όχι από τη γενίκευση του πρωτοτύπου.

```
# Μορφή εγγραφής /etc/shadow:
# username:$id$salt$hash:...

# $1$ = md5crypt, όχι απλό raw MD5
# $5$ = sha256crypt, όχι απλό raw SHA-256
# $6$ = sha512crypt· η προεπιλογή εξαρτάται από τη διανομή και την έκδοση
# $y$ = yescrypt, όπου υποστηρίζεται
```

### Δοκιμές ανάκτησης κωδικών Linux
```bash
# Αυτόματη εκτίμηση μορφής hash
john linux_hashes.txt

# Καθορισμός μορφής
john --format=sha512crypt linux_hashes.txt

# Με λίστα λέξεων
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Εμφάνιση αποτελεσμάτων
john --show linux_hashes.txt
```

---

<a id="section-08"></a>

## 🪟 Έλεγχος hashes κωδικών Windows

### Τύποι hashes

| Τύπος | Περιγραφή | Μορφή |
| ------ | ------------- | -------- |
| **LM** | Παλιός, αδύναμος μηχανισμός | `--format=lm` |
| **NTLM** | Hash κωδικού Windows, τύπου NT | `--format=nt` |
| **NTLMv2** | Απόκριση δικτυακής ταυτοποίησης | `--format=netntlmv2` |

### Δοκιμές ανάκτησης από NT hashes
```bash
# Μορφή εγγραφής NT hash:
# user:rid:lm_hash:ntlm_hash:::

# Δοκιμές ανάκτησης από NT hashes
john --format=nt windows_hashes.txt

# Με λίστα λέξεων
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Με κανόνες μετασχηματισμού
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Εξαγωγή hashes Windows
```bash
# Χρήση Metasploit
meterpreter> hashdump

# Χρήση secretsdump της συλλογής Impacket
secretsdump.py -sam sam -system system LOCAL

# Χρήση pwdump
pwdump7.exe
```

---

<a id="section-09"></a>

## 📄 Ανάκτηση κωδικών αρχείων

### Αρχεία ZIP
```bash
# Εξαγωγή δεδομένων ελέγχου hash
zip2john protected.zip > zip.hash

# Δοκιμή υποψήφιων τιμών
john --wordlist=rockyou.txt zip.hash

# Εμφάνιση ανακτημένου κωδικού
john --show zip.hash
```

### Αρχεία RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### Αρχεία PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Έγγραφα Office
```bash
# Έγγραφα Word, Excel και PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Ιδιωτικά κλειδιά SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Βάση κωδικών KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="section-10"></a>

## ⚙️ Κανόνες και παραγωγή παραλλαγών λέξεων

### Ενεργοποίηση κανόνων
```bash
# Προεπιλεγμένοι κανόνες
john --wordlist=wordlist.txt --rules hashes.txt

# Συγκεκριμένο σύνολο κανόνων
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Προβολή διαθέσιμων συνόλων κανόνων
```bash
john --list=rules
```

### Συνήθεις πράξεις κανόνων

| Κανόνας | Περιγραφή | Παράδειγμα |
| ------ | ------------- | --------- |
| `:` | Διατήρηση της τιμής χωρίς αλλαγή | password → password |
| `l` | Μετατροπή όλων των γραμμάτων σε πεζά | PassWord → password |
| `u` | Μετατροπή όλων των γραμμάτων σε κεφαλαία | password → PASSWORD |
| `c` | Πρώτο γράμμα κεφαλαίο, τα υπόλοιπα πεζά | password → Password |
| `r` | Αντιστροφή σειράς χαρακτήρων | password → drowssap |
| `d` | Επανάληψη ολόκληρης της λέξης | pass → passpass |
| `$X` | Προσθήκη χαρακτήρα X στο τέλος | pass → passX |
| `^X` | Προσθήκη χαρακτήρα X στην αρχή | pass → Xpass |
| `sXY` | Αντικατάσταση χαρακτήρα X με Y | password → pa$$word |

### Προσαρμοσμένοι κανόνες μετασχηματισμού

> **Προσοχή στη σύνταξη κανόνων:** Τα `$X` και `^X` προσθέτουν έναν χαρακτήρα κάθε φορά. Οι γραμμές `$123` και `^2023` του πρωτοτύπου δεν σημαίνουν απλά «πρόσθεσε ολόκληρο το 123/2023». Το `sXY` απαιτεί συγκεκριμένους χαρακτήρες X και Y· ο πίνακας δείχνει ενδεικτικό αποτέλεσμα, όχι αποτέλεσμα του κυριολεκτικού κανόνα sXY. Δείτε τη [σύνταξη κανόνων John](https://www.openwall.com/john/doc/RULES.shtml).

```bash
# Προσθήκη στο john.conf, ενότητα [List.Rules:Custom]· δείτε τη σημείωση σύνταξης
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="section-11"></a>

## 💾 Διαχείριση συνεδριών

> **Ευαίσθητα αποτελέσματα:** Το pot file μπορεί να περιέχει ανακτημένους κωδικούς και τα αρχεία συνεδρίας πληροφορίες του ελέγχου. Η θέση τους εξαρτάται από εγκατάσταση και ρυθμίσεις. Το .rec είναι αρχείο επαναφοράς· η υποστηριζόμενη προβολή status είναι καταλληλότερη για παρακολούθηση προόδου.


### Δημιουργία συνεδρίας με όνομα
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Επαναφορά συνεδρίας
```bash
# Επαναφορά διακοπείσας συνεδρίας
john --restore=mycrack

# Επαναφορά προεπιλεγμένης συνεδρίας
john --restore
```

### Έλεγχος κατάστασης
```bash
# Πατήστε ένα πλήκτρο κατά τις δοκιμές για εμφάνιση κατάστασης
# Εναλλακτικά εξέταση αρχείου .rec· για πρόοδο προτιμάται η υποστηριζόμενη επιλογή status
cat ~/.john/mycrack.rec
```

### Αρχείο ανακτημένων αποτελεσμάτων (pot file)
```bash
# Συνήθης προεπιλεγμένη θέση pot file
~/.john/john.pot

# Προσαρμοσμένο αρχείο ανακτημένων αποτελεσμάτων (pot file)
john --pot=custom.pot hashes.txt

# Εμφάνιση όλων των ανακτημένων κωδικών
john --show --pot=custom.pot hashes.txt
```

---

<a id="section-12"></a>

## 🚀 Ρύθμιση απόδοσης

### Πολλαπλές διεργασίες με Fork
```bash
# Χρήση πολλών πυρήνων CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### Επιτάχυνση GPU με OpenCL
```bash
# Προβολή συσκευών OpenCL
john --list=opencl-devices

# Χρήση GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Περιορισμός υποψήφιων κωδικών
```bash
# Περιορισμός μήκους υποψήφιων κωδικών
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Χρήση μνήμης
```bash
# Περιορισμός μνήμης προσωρινής φόρτωσης λίστας
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="section-13"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Αρχείο shadow του Linux
```bash
# Συνένωση αρχείων
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Δοκιμές με τη λίστα rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Εμφάνιση αποτελεσμάτων
john --show hashes.txt
```

### Παράδειγμα 2: NT hashes Windows
```bash
# Παράδειγμα εξόδου hashdump· τα hashes του πρωτοτύπου είναι ελλιπή
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Δοκιμή υποψήφιων τιμών
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Παράδειγμα 3: Κωδικός αρχείου ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Παράδειγμα 4: Συνθηματικό ιδιωτικού κλειδιού SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Παράδειγμα 5: Hashes MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Παράδειγμα 6: Hashes εφαρμογών web
```bash
# Παράδειγμα hash bcrypt· η συμβολοσειρά είναι συντομευμένη
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Παράδειγμα 7: Με κανόνες μετασχηματισμού
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Παράδειγμα 8: Δοκιμές Incremental
```bash
# Δοκιμές συνδυασμών 6-8 χαρακτήρων με πεζά και ψηφία
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="section-14"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Βασικός έλεγχος ανάκτησης | `john hashes.txt` |
| Δοκιμές από λίστα λέξεων | `john --wordlist=rockyou.txt hashes.txt` |
| Με κανόνες μετασχηματισμού | `john --wordlist=words.txt --rules hashes.txt` |
| Εμφάνιση ανακτημένων αποτελεσμάτων | `john --show hashes.txt` |
| Συγκεκριμένη μορφή | `john --format=raw-md5 hashes.txt` |
| Incremental, δοκιμές συνδυασμών | `john --incremental hashes.txt` |
| Επαναφορά συνεδρίας | `john --restore=session` |
| Προβολή μορφών | `john --list=formats` |

### 2john — βοηθητικά εργαλεία

| Εργαλείο | Σκοπός |
| ------ | --------- |
| `unshadow` | Συνένωση στοιχείων από passwd και shadow του Linux |
| `zip2john` | Αρχεία ZIP |
| `rar2john` | Αρχεία RAR |
| `pdf2john` | Αρχεία PDF |
| `office2john` | Έγγραφα Office |
| `ssh2john` | Κλειδιά SSH |
| `keepass2john` | Βάσεις κωδικών KeePass |

### Συνήθεις μορφές

| Ψηφιακή σύνοψη αρχείου (Hash) | Επιλογή μορφής |
| ------ | ------------- |
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 (sha512crypt) | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="section-15"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές αποδοτικής χρήσης

1. **Δοκιμάστε πρώτα τη λειτουργία Single όταν υπάρχουν σχετικά στοιχεία**
   ```bash
   john --single hashes.txt
   ```

2. **Χρήση λιστών σχετικών με το εγκεκριμένο πεδίο ελέγχου**
   ```bash
   # Σχετικοί όροι του εγκεκριμένου ελέγχου, όπως εταιρεία ή πόλη
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Ξεκινήστε με μικρές λίστες λέξεων**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Στη συνέχεια δοκιμάστε μεγαλύτερες λίστες
   ```

4. **Συνδυάστε τρόπους δοκιμών**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Συμβουλές απόδοσης

```bash
# Χρήση πολλών πυρήνων
john --fork=4 hashes.txt

# Επιτάχυνση GPU στην έκδοση Jumbo
john --format=sha512crypt-opencl hashes.txt
```

---

<a id="section-16"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το John the Ripper μόνο για **εξουσιοδοτημένους σκοπούς**.
> 
> - ✅ Έλεγχος των δικών σας κωδικών
> - ✅ Έλεγχος με ρητή άδεια
> - ✅ Αξιολογήσεις ασφάλειας με εξουσιοδότηση
> - ❌ Μην επιχειρείτε ανάκτηση κωδικών χωρίς άδεια
> - ❌ Η μη εξουσιοδοτημένη πρόσβαση μπορεί να επιφέρει νομικές κυρώσεις
> 
> **Η ανάκτηση κωδικών χωρίς εξουσιοδότηση μπορεί να επιφέρει νομικές κυρώσεις.**

---

<a id="section-17"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Επίσημος ιστότοπος John the Ripper](https://www.openwall.com/john/)
- [Αποθετήριο John Jumbo στο GitHub](https://github.com/openwall/john)
- [Τεκμηρίωση John](https://openwall.info/wiki/john)

### Λίστες λέξεων (wordlists)
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Σχετικοί οδηγοί
- [Hydra](../../Hydra/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)
- [Nmap](../../Nmap/translations/README.el.md)

---

<p align="center">
  <b>🔨 Εξασκηθείτε στον έλεγχο αντοχής κωδικών!</b><br>
  <i>Χρησιμοποιείτε τα εργαλεία στο εγκεκριμένο πεδίο ελέγχου</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
