# 🩸 BloodHound — Οπτικοποίηση διαδρομών πρόσβασης στο Active Directory

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Οπτικοποίηση πιθανών διαδρομών πρόσβασης AD
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## Πριν ξεκινήσετε

Το **BloodHound** απεικονίζει οντότητες και σχέσεις πρόσβασης ως γράφο. Ένας **κόμβος** μπορεί να είναι χρήστης, ομάδα ή υπολογιστής· μια **ακμή** είναι σχέση, όπως συμμετοχή σε ομάδα ή δικαίωμα. Μια διαδρομή στον γράφο προτείνει τι να εξετάσετε, δεν αποδεικνύει αυτόματα εκμεταλλεύσιμη αλυσίδα.

**Βασικοί όροι:** *collector* = συλλέκτης δεδομένων, *SharpHound* = συλλέκτης AD, *principal* = οντότητα ασφάλειας, *Cypher* = γλώσσα ερωτημάτων γράφου, *owned* = επισήμανση οντότητας που ελέγχετε, *session* = ένδειξη συνεδρίας χρήστη σε υπολογιστή.

Το πρωτότυπο αναμειγνύει **BloodHound Legacy** και **Community Edition (CE)**. Διαφέρουν εγκατάσταση, διεπαφή, συλλέκτες και σχήμα δεδομένων. Επιλέξτε συμβατή γενιά πριν ακολουθήσετε βήματα. Τα δεδομένα συλλογής μπορεί να είναι ελλιπή ή παλιά και χρειάζονται διασταύρωση.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το BloodHound](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [📡 Συλλογή μέσω SharpHound](#section-03)
- [🖥️ Χρήση γραφικού περιβάλλοντος BloodHound](#section-04)
- [🔍 Ενσωματωμένα ερωτήματα](#section-05)
- [📝 Προσαρμοσμένα ερωτήματα Cypher](#section-06)
- [🎯 Ανάλυση διαδρομών πρόσβασης](#section-07)
- [🆕 BloodHound CE](#section-08)
- [📊 Γρήγορη αναφορά](#section-09)
- [📚 Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το BloodHound

Το **BloodHound** χρησιμοποιεί γράφους για να απεικονίζει σχέσεις και πιθανές διαδρομές πρόσβασης στο Active Directory. Δείχνει:

- 🔗 **Διαδρομές πρόσβασης** — πιθανές αλυσίδες προς Domain Admins
- 👤 **Σχέσεις χρηστών** — συμμετοχές σε ομάδες και ενδείξεις συνεδριών
- 💻 **Σχέσεις υπολογιστών** — δικαιώματα διαχείρισης και συνεδρίες
- 🔓 **Λανθασμένες ρυθμίσεις** — δικαιώματα που χρειάζονται διερεύνηση
- 🎯 **Στόχοι υψηλής σημασίας** — κρίσιμοι λογαριασμοί και συστήματα

### Επιμέρους εργαλεία

| Συστατικό | Περιγραφή |
|-----------|-------------|
| **BloodHound** | Γραφικό περιβάλλον της παλιάς γενιάς, με Neo4j |
| **SharpHound** | Συλλέκτης δεδομένων, ανά γενιά και διαθέσιμη μορφή |
| **Neo4j** | Βάση δεδομένων γράφου |
| **BloodHound CE** | Community Edition — νεότερη κοινοτική έκδοση, διαφορετική από το Enterprise |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Legacy και CE:** Τα βήματα Neo4j browser, Bolt και desktop εκτελέσιμου αφορούν κυρίως BloodHound Legacy. Το CE είναι εφαρμογή web με διαφορετική εγκατάσταση και διαχείριση. Μην θεωρείτε συμβατά όλα τα πακέτα, collectors και exports. Χρησιμοποιήστε το [επίσημο BloodHound](https://github.com/SpecterOps/BloodHound).


### Kali Linux

```bash
# Εγκατάσταση BloodHound της αντίστοιχης γενιάς
sudo apt update
sudo apt install bloodhound

# Εγκατάσταση Neo4j
sudo apt install neo4j

# Εκκίνηση Neo4j
sudo neo4j start

# Περιβάλλον Neo4j στο http://localhost:7474
# Στοιχεία του ιστορικού παραδείγματος: neo4j / neo4j, με αλλαγή στην πρώτη σύνδεση

# Εκκίνηση BloodHound
bloodhound
```

### Windows

```powershell
# Λήψη από εκδόσεις του επίσημου GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Εγκατάσταση Neo4j Community Edition
https://neo4j.com/download/

# Εκκίνηση Neo4j
# Εκτέλεση της αντίστοιχης υπηρεσίας με τα απαιτούμενα δικαιώματα

# Εκτέλεση BloodHound.exe της παλιάς desktop γενιάς
```

### Παράδειγμα Docker του πρωτοτύπου

```bash
# Παράδειγμα ρύθμισης Neo4j για τη συγκεκριμένη γενιά BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Αναμονή εκκίνησης και μετά εκτέλεση BloodHound
bloodhound
```

---

<a id="section-03"></a>

## 📡 Συλλογή μέσω SharpHound

> **Έκδοση συλλέκτη:** Default δεν ταυτίζεται γενικά με All. Ονόματα μεθόδων, throttle, loop και επιλογές stealth/skipdns εξαρτώνται από την έκδοση. Το DCOnly μειώνει συγκεκριμένες επαφές με endpoints, χωρίς εγγύηση αφάνειας. Συλλέξτε μόνο όσα απαιτεί ο έλεγχος.


### Λήψη SharpHound

```bash
# Από εκδόσεις BloodHound· ελέγξτε συμβατότητα συλλέκτη
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Ενδεικτικά αρχεία:
# - SharpHound.exe — αυτόνομο εκτελέσιμο
# - SharpHound.ps1 — παλαιότερη μορφή PowerShell
```

### Βασική συλλογή

```powershell
# Εκκίνηση SharpHound με την προεπιλεγμένη συλλογή· δεν ισοδυναμεί αναγκαστικά με All
.\SharpHound.exe

# Καθορισμός domain
.\SharpHound.exe -d domain.local

# Καθορισμός εξόδου
.\SharpHound.exe -o C:\Temp\

# Παράδειγμα παλαιότερης μορφής PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Μέθοδοι συλλογής

```powershell
# Μέθοδοι που περιλαμβάνει η επιλογή All στη συγκεκριμένη έκδοση
.\SharpHound.exe -c All

# Επιλεγμένες μέθοδοι
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### Περιγραφή μεθόδων συλλογής

| Μέθοδος | Περιγραφή | Ενδεικτικός όγκος δραστηριότητας |
|--------|-------------|-------|
| `Default` — προεπιλογή του πρωτοτύπου | Ομάδες, τοπικά δικαιώματα, συνεδρίες και σχέσεις εμπιστοσύνης, ανά έκδοση | Χαμηλή |
| `All` — όλες οι υποστηριζόμενες μέθοδοι | Όλες οι μέθοδοι που περιλαμβάνει η συγκεκριμένη επιλογή | Υψηλή |
| Session | Ενδείξεις συνδεδεμένων χρηστών | Μέση |
| LocalGroup | Μέλη τοπικών ομάδων | Μέση |
| ACL | Δικαιώματα ACL | Χαμηλή |
| DCOnly | Συλλογή με εστίαση στους domain controllers | Πολύ χαμηλό, σύμφωνα με το πρωτότυπο |
| `Group` — ομάδες | Συμμετοχές σε ομάδες | Χαμηλή |
| Trusts | Σχέσεις εμπιστοσύνης domains | Χαμηλή |

### Συλλογή με περιορισμό δραστηριότητας

```powershell
# Επιλογές περιορισμού δραστηριότητας· δεν εγγυώνται αφάνεια
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Χωρίς επίλυση διευθύνσεων και θυρών σε ονόματα
.\SharpHound.exe --skipdns

# Καθυστέρηση μεταξύ αιτημάτων
.\SharpHound.exe --throttle 1000
```

### Επαναλαμβανόμενη συλλογή συνεδριών

```powershell
# Συλλογή ενδείξεων συνεδριών σε επαναλήψεις
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Επαναλήψεις με διάστημα αναμονής
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Συλλογή από Linux με bloodhound.py

```bash
# Εγκατάσταση
pip install bloodhound

# Εκτέλεση συλλογής
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Με αποτύπωμα NT
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="section-04"></a>

## 🖥️ Χρήση γραφικού περιβάλλοντος BloodHound

### Εκκίνηση BloodHound

```bash
# 1. Εκκίνηση Neo4j
sudo neo4j start

# 2. Αναμονή Neo4j και έλεγχος του http://localhost:7474

# 3. Εκκίνηση BloodHound
bloodhound

# 4. Σύνδεση
# URL: bolt://localhost:7687
# Username: neo4j
# Κωδικός: ο κωδικός που ορίσατε
```

### Εισαγωγή δεδομένων

```
1. Πατήστε "Upload Data", το εικονίδιο φακέλου, για εισαγωγή
2. Επιλέξτε το συμβατό αρχείο .zip του SharpHound
3. Περιμένετε να ολοκληρωθεί η εισαγωγή
4. Τα εισαγόμενα δεδομένα εμφανίζονται στον γράφο
```

### Επισκόπηση περιβάλλοντος εργασίας

| Περιοχή | Περιγραφή |
|---------|-------------|
| **Search — αναζήτηση** | Αναζήτηση χρηστών, υπολογιστών και ομάδων |
| **Ανάλυση** | Έτοιμα ερωτήματα |
| **Φίλτρα** | Ορατότητα σχέσεων και κόμβων |
| **Γράφος** | Οπτική απεικόνιση |
| **Node Info — στοιχεία κόμβου** | Λεπτομέρειες επιλεγμένου στοιχείου |

### Πλοήγηση

```
- Αριστερό κλικ: επιλογή κόμβου
- Δεξί κλικ: μενού ενεργειών
- Κύλιση: μεγέθυνση ή σμίκρυνση
- Σύρσιμο: μετακίνηση προβολής
- Ctrl+Click: προσθήκη στην επιλογή
```

---

<a id="section-05"></a>

## 🔍 Ενσωματωμένα ερωτήματα

### Πληροφορίες domain

```
- Find all Domain Admins — Αναζήτηση μελών Domain Admins
- Find all Domain Controllers — Αναζήτηση domain controllers
- Find high value targets — Αναζήτηση στόχων υψηλής σημασίας
- Map Domain Trusts — Χαρτογράφηση σχέσεων εμπιστοσύνης domains
```

### Συντομότερες διαδρομές

```
- Shortest Paths to Domain Admins — Συντομότερες διαδρομές προς Domain Admins
- Shortest Path from Owned Principals — Συντομότερη διαδρομή από ελεγχόμενες οντότητες
- Shortest Path to High Value Targets — Συντομότερη διαδρομή προς στόχους υψηλής σημασίας
- Shortest Path to Unconstrained Delegation — Συντομότερη διαδρομή προς μη περιορισμένη ανάθεση
```

### Kerberos

```
- Find Kerberoastable Accounts — Αναζήτηση λογαριασμών με ενδείξεις δυνατότητας Kerberoasting
- Find AS-REP Roastable Users — Αναζήτηση χρηστών χωρίς απαίτηση προταυτοποίησης Kerberos
- Shortest Path from Kerberoastable Users — Συντομότερη διαδρομή από χρήστες με SPN
- Find Principals with Unconstrained Delegation — Αναζήτηση οντοτήτων με μη περιορισμένη ανάθεση
```

### Ανάλυση ACL

```
- Find Principals with DCSync Rights — Αναζήτηση οντοτήτων με δικαιώματα DCSync
- Find Dangerous Rights for Domain Users — Αναζήτηση δικαιωμάτων ενδιαφέροντος για Domain Users
- Find Users with Foreign Domain Group Membership — Αναζήτηση χρηστών που ανήκουν σε ομάδες άλλου domain
```

### Υπολογιστές

```
- Find Computers with Unsupported OS — Αναζήτηση υπολογιστών με παλιότερο μη υποστηριζόμενο λειτουργικό
- Find Computers where Domain Users are Local Admin — Αναζήτηση υπολογιστών όπου Domain Users διαθέτουν τοπικά δικαιώματα διαχειριστή
- Find Computers with Sessions of High Value Users — Αναζήτηση υπολογιστών με ενδείξεις συνεδριών χρηστών υψηλής σημασίας
```

### GPO

```
- Find all GPO Owned by Owned Principals — Αναζήτηση GPO που ανήκουν σε ελεγχόμενες οντότητες
- Find GPO Affecting High Value Targets — Αναζήτηση GPO που επηρεάζουν στόχους υψηλής σημασίας
```

---

<a id="section-06"></a>

## 📝 Προσαρμοσμένα ερωτήματα Cypher

> **Σχήμα και σημασιολογία:** Ιδιότητες όπως owned/highvalue, ονόματα σχέσεων και υποστήριξη Cypher μπορεί να διαφέρουν μεταξύ Legacy και CE. Ερωτήματα με κάθε ακμή ή φίλτρα μόνο SPN δεν επιβεβαιώνουν εκμεταλλεύσιμη διαδρομή. Ελέγξτε την πραγματική σημασία κάθε σχέσης.


### Βασικά ερωτήματα

```cypher
// Καταγραφή χρηστών
MATCH (u:User) RETURN u

// Καταγραφή υπολογιστών
MATCH (c:Computer) RETURN c

// Αναζήτηση μελών Domain Admins
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Αναζήτηση domain controllers
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Ερωτήματα συνεδριών

```cypher
// Αναζήτηση ενδείξεων συνεδριών Domain Admins
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Αναζήτηση χρηστών με ενδείξεις συνεδριών σε πολλούς υπολογιστές
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Ερωτήματα διαδρομών

```cypher
// Συντομότερη διαδρομή προς Domain Admins
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Διαδρομές προς Domain Admins με όριο 10 αποτελεσμάτων
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Διαδρομές από οντότητες που έχουν επισημανθεί ως ελεγχόμενες
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### Ερωτήματα δικαιωμάτων ACL

```cypher
// Χρήστες με ενδείξεις δικαιωμάτων DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Χρήστες με δικαίωμα επαναφοράς κωδικού άλλου χρήστη
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Χρήστες με GenericAll σε λογαριασμούς υπολογιστών
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Ερωτήματα Kerberos

```cypher
// Χρήστες με SPN και διαδρομή προς Domain Admins
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Χρήστες χωρίς απαίτηση προταυτοποίησης Kerberos
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Υπολογιστές με μη περιορισμένη ανάθεση
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Στόχοι υψηλής σημασίας

```cypher
// Επισήμανση στόχων υψηλής σημασίας
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Αναζήτηση διαδρομών προς στόχους υψηλής σημασίας
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="section-07"></a>

## 🎯 Ανάλυση διαδρομών πρόσβασης

### Αναζήτηση διαδρομών πρόσβασης

```
1. Αναζητήστε τον αρχικό κόμβο, π.χ. τον λογαριασμό που ελέγχετε
2. Δεξί κλικ → "Mark as Owned" για επισήμανση ελεγχόμενης οντότητας στην παλιά διεπαφή
3. Εκτελέστε το ερώτημα "Shortest Path from Owned Principals"
4. Εξετάστε τις σχέσεις και τις προϋποθέσεις κάθε βήματος
```

### Συνήθεις σχέσεις του γράφου

> **Σχέση δεν σημαίνει ολοκληρωμένο αποτέλεσμα:** HasSession δεν εγγυάται διαθέσιμα μυστικά, ούτε κάθε AllExtendedRights αντιστοιχεί σε DCSync. Ο αντίκτυπος εξαρτάται από τον τύπο αντικειμένου, την κατεύθυνση, τα δικαιώματα και τους ελέγχους του περιβάλλοντος. Τα δεδομένα συνεδρίας είναι στιγμιότυπα και μπορεί να έχουν αλλάξει.


| Σχέση | Δυνατότητα ή μέθοδος προς διερεύνηση |
|------|---------------|
| **MemberOf** | Συμμετοχή σε ομάδα |
| **AdminTo** | Τοπικά δικαιώματα διαχειριστή |
| **HasSession** | Ένδειξη σχετικής συνεδρίας, όχι εγγύηση πρόσβασης σε διαπιστευτήρια |
| **CanRDP** | Δικαίωμα πρόσβασης RDP |
| **CanPSRemote** | Απομακρυσμένη εκτέλεση PowerShell |
| **ExecuteDCOM** | Εκτέλεση μέσω DCOM |
| **GenericAll** | Πλήρης έλεγχος του σχετικού αντικειμένου |
| **GenericWrite** | Τροποποίηση ιδιοτήτων αντικειμένου |
| **WriteOwner** | Αλλαγή ιδιοκτήτη |
| **WriteDacl** | Τροποποίηση ACL |
| **ForceChangePassword** | Επαναφορά κωδικού |
| **AddMember** | Προσθήκη μέλους σε ομάδα |
| **AllExtendedRights** | Εκτεταμένα δικαιώματα· η ακριβής δυνατότητα εξαρτάται από το αντικείμενο |
| **GetChanges** | Ένα από τα απαιτούμενα δικαιώματα αναπαραγωγής DCSync |
| **GetChangesAll** | Συμπληρωματικό δικαίωμα αναπαραγωγής DCSync |

### Έλεγχοι αξιοποίησης σχέσεων του γράφου

#### GenericAll σε λογαριασμό χρήστη

```powershell
# Επαναφορά κωδικού
net user targetuser NewP@ssw0rd /domain

# Εναλλακτικά, μέσω PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll σε λογαριασμό υπολογιστή

```powershell
# Παράδειγμα ανάθεσης RBCD με τις απαιτούμενες προϋποθέσεις
# Χρήση Rubeus και PowerMad, σύμφωνα με το σενάριο
```

#### ForceChangePassword

```powershell
# Επαναφορά κωδικού
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Προσθήκη χρήστη σε ομάδα
net group "Domain Admins" attackeruser /add /domain

# Εναλλακτικά, μέσω PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Προσθήκη δικαιωμάτων DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="section-08"></a>

## 🆕 BloodHound CE

> **Διόρθωση αρχιτεκτονικής:** Η επίσημη περιγραφή CE χρησιμοποιεί PostgreSQL για δεδομένα εφαρμογής και Neo4j για γράφο· ο ισχυρισμός «PostgreSQL, όχι Neo4j» του πρωτοτύπου είναι ανακριβής. CE σημαίνει Community Edition, διαφορετικό προϊόν από το Enterprise. Η εγκατάσταση, οι θύρες και η αρχική σύνδεση πρέπει να αντιστοιχούν στην έκδοση. Πηγή: [BloodHound CE](https://github.com/SpecterOps/BloodHound).


### Σχετικά με το BloodHound CE

Το **BloodHound Community Edition (CE)** είναι η νεότερη κοινοτική γενιά, με διαφορετική εγκατάσταση και διεπαφή:
- Σύγχρονο περιβάλλον web
- Βάσεις σχεσιακών δεδομένων και γράφου ανά έκδοση και ρύθμιση· δείτε τη διευκρίνιση
- Διεπαφή REST API
- Διαφορετική αρχιτεκτονική και δυνατότητες

### Εγκατάσταση

```bash
# Ιστορικό παράδειγμα εκκίνησης μέσω Docker Compose
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Πρόσβαση του παραδείγματος: http://localhost:8080
# Στοιχεία παραδείγματος: admin και κωδικός από τα logs της εγκατάστασης
```

### Συμβατό SharpHound για CE

```powershell
# Λήψη συλλέκτη συμβατού με τη συγκεκριμένη έκδοση CE
# Από τις αντίστοιχες εκδόσεις BloodHound CE

# Παράδειγμα συλλογής· ελέγξτε τη σύνταξη της έκδοσης
.\SharpHound.exe -c All
```

---

<a id="section-09"></a>

## 📊 Γρήγορη αναφορά

### Εντολές SharpHound

| Εντολή | Περιγραφή |
|---------|-------------|
| `.\SharpHound.exe` | Προεπιλεγμένη συλλογή της έκδοσης |
| `.\SharpHound.exe -c All` | Όλες οι διαθέσιμες μέθοδοι της επιλογής |
| `.\SharpHound.exe -c DCOnly` | Συλλογή DCOnly, χωρίς εγγύηση αφάνειας |
| `.\SharpHound.exe -c Session --loop` | Επαναλαμβανόμενη συλλογή συνεδριών |
| `.\SharpHound.exe -d domain.local` | Καθορισμός domain |
| `.\SharpHound.exe --stealth` | Επιλογή περιορισμού δραστηριότητας, όπου υποστηρίζεται |

### Συνήθη ερωτήματα

| Ερώτημα | Σκοπός |
|-------|---------|
| Αναζήτηση μελών Domain Admins | Εντοπισμός μελών Domain Admins |
| Συντομότερη διαδρομή προς Domain Admins | Αναζήτηση πιθανής διαδρομής πρόσβασης |
| Χρήστες με ενδείξεις SPN | Αναζήτηση λογαριασμών με SPN για έλεγχο |
| Λογαριασμοί χωρίς απαίτηση preauthentication | Χρήστες χωρίς απαίτηση προταυτοποίησης Kerberos |
| Μη περιορισμένη ανάθεση ταυτοποίησης | Έλεγχος διαμόρφωσης ανάθεσης |
| Δικαιώματα DCSync | Αναζήτηση οντοτήτων με δικαιώματα αναπαραγωγής |

### Ροή εργασίας ανάλυσης

```
1. Συλλογή δεδομένων με συμβατό SharpHound
2. Εισαγωγή στο BloodHound
3. Επισήμανση οντοτήτων που ελέγχετε
4. Εκτέλεση του σχετικού ερωτήματος "Shortest Path from Owned"
5. Εξέταση σχέσεων και απαιτούμενων δικαιωμάτων
6. Περιορισμένη επαλήθευση βημάτων που επιτρέπει ο έλεγχος
7. Ενημέρωση επισήμανσης για νέες ελεγχόμενες οντότητες
8. Επανάληψη με βάση τα επαληθευμένα ευρήματα
```

### Βασικά αρχεία

```
Ενδεικτική έξοδος SharpHound:
- *_BloodHound.zip — δεδομένα JSON, ανά έκδοση
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

<a id="section-10"></a>

## 📚 Πηγές

- [Κώδικας BloodHound στο GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [Τεκμηρίωση BloodHound](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Σχετικοί οδηγοί
- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [PowerShell](../../PowerShell/translations/README.el.md)
- [Ανύψωση δικαιωμάτων Windows](../../Windows-PrivEsc/translations/README.el.md)

---

<p align="center">
  <b>🩸 Επαληθεύστε τις διαδρομές πρόσβασης!</b><br>
  <i>BloodHound — σχέσεις, γράφοι και ελεγχόμενη επαλήθευση</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
