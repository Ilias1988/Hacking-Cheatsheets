# 🔓 Hydra — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    Εργαλείο δοκιμών σύνδεσης σε υπηρεσίες δικτύου
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 Εργαλείο παράλληλων δοκιμών σύνδεσης σε υπηρεσίες δικτύου</b>
</p>

---

## Πριν ξεκινήσετε

Το **Hydra** δοκιμάζει ονόματα χρήστη και κωδικούς απευθείας σε μια υπηρεσία, όπως SSH ή μια φόρμα σύνδεσης. Αυτό είναι **online password testing**: κάθε προσπάθεια επικοινωνεί με τον server και μπορεί να καταγραφεί, να αποτύχει λόγω φίλτρων ή να κλειδώσει λογαριασμό. Διαφέρει από John και Hashcat, που ελέγχουν κυρίως τοπικά δεδομένα χωρίς να επαναλαμβάνουν συνδέσεις στον server.

**Όροι:** *wordlist* = αρχείο υποψήφιων λέξεων ή κωδικών, *credential pair* = ζεύγος ονόματος χρήστη και κωδικού, *task/thread* = παράλληλη εργασία, *lockout* = κλείδωμα λογαριασμού, *password spray* = δοκιμή ίδιου κωδικού σε πολλούς λογαριασμούς. Μικρός αριθμός παράλληλων εργασιών δεν ισοδυναμεί με μικρό συνολικό αριθμό προσπαθειών.

Διαβάστε πρώτα τον τρόπο επιλογής χρήστη, λίστας και υπηρεσίας και έπειτα τις ειδικές παραμέτρους του πρωτοκόλλου. Οι εντολές διατηρούνται όπως στο αγγλικό πρωτότυπο· οι σημειώσεις επισημαίνουν παραδείγματα που χρειάζονται διόρθωση πριν από χρήση στο εγκεκριμένο εργαστήριο.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική σύνταξη](#section-03)
- [Υποστηριζόμενα πρωτόκολλα](#section-04)
- [Δοκιμές σύνδεσης SSH](#section-05)
- [Δοκιμές σύνδεσης FTP](#section-06)
- [Δοκιμές σύνδεσης μέσω φορμών HTTP](#section-07)
- [Δοκιμές σύνδεσης σε βάσεις δεδομένων](#section-08)
- [Δοκιμές απομακρυσμένης επιφάνειας εργασίας](#section-09)
- [Άλλες υπηρεσίες](#section-10)
- [Λίστες λέξεων και κωδικοί πρόσβασης](#section-11)
- [Προχωρημένες επιλογές](#section-12)
- [Παραδείγματα σεναρίων ελέγχου](#section-13)
- [Γρήγορη αναφορά](#section-14)
- [Συμβουλές και καλές πρακτικές](#section-15)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-16)
- [Πηγές](#section-17)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

**Το Hydra** (THC-Hydra) εκτελεί δοκιμές στοιχείων σύνδεσης σε υπηρεσίες δικτύου. Υποστηρίζει πολλά πρωτόκολλα και μπορεί να διατηρεί παράλληλες συνδέσεις.

### Βασικά χαρακτηριστικά

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Πολλά πρωτόκολλα** | SSH, FTP, HTTP, RDP, SMB και άλλα |
| **Παράλληλη εκτέλεση** | Πολλαπλές ταυτόχρονες συνδέσεις |
| **Ευέλικτο** | Προσαρμοσμένες κεφαλίδες HTTP, cookies και proxy |
| **Συνέχιση** | Αποθήκευση και επαναφορά συνεδρίας ελέγχου |
| **Πολλαπλές πλατφόρμες** | Linux, Windows και macOS, ανάλογα με την έκδοση |

### Τύποι δοκιμών κωδικών πρόσβασης

| Τύπος | Περιγραφή |
| ------ | ------------- |
| **Λίστα λέξεων (Dictionary)** | Δοκιμή κωδικών από λίστα λέξεων |
| **Δοκιμή συνδυασμών (Brute Force)** | Παραγωγή όλων των συνδυασμών του επιλεγμένου συνόλου |
| **Ίδιος κωδικός σε πολλούς χρήστες (Password Spray)** | Ένας κωδικός πρόσβασης για πολλούς χρήστες |
| **Δοκιμή έτοιμων ζευγών στοιχείων σύνδεσης (Credential Stuffing)** | Ζεύγη ονόματος χρήστη και κωδικού |

---

<a id="section-02"></a>

## 📥 Εγκατάσταση

> **Έλεγχος εγκατάστασης:** Η βοήθεια του εγκατεστημένου Hydra δείχνει τις διαθέσιμες υπηρεσίες και επιλογές. Το `-V` εμφανίζει τα ζεύγη κάθε προσπάθειας· δεν είναι επιλογή εμφάνισης έκδοσης. Η διαθέσιμη υποστήριξη εξαρτάται και από τις βιβλιοθήκες με τις οποίες μεταγλωττίστηκε το εργαλείο. Δείτε το [επίσημο αποθετήριο Hydra](https://github.com/vanhauser-thc/thc-hydra).


### Kali Linux (συνήθως προεγκατεστημένο)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Εγκατάσταση από πηγαίο κώδικα
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS μέσω Homebrew
```bash
brew install hydra
```

### Επιβεβαίωση εγκατάστασης
```bash
hydra -h
hydra -V
```

---

<a id="section-03"></a>

## ⌨️ Βασική σύνταξη

### Γενική σύνταξη
```bash
hydra [options] target service
```

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-l` | Ένα όνομα χρήστη |
| `-L` | Λίστα ονομάτων χρήστη |
| `-p` | Ένας κωδικός πρόσβασης |
| `-P` | Λίστα κωδικών πρόσβασης |
| `-C` | Αρχείο ζευγών user:pass με διαχωριστικό άνω και κάτω τελεία |
| `-t` | Παράλληλες εργασίες (προεπιλογή του πρωτοτύπου: 16) |
| `-s` | Αριθμός θύρας |
| `-o` | Αρχείο εξόδου |
| `-f` | Διακοπή στο πρώτο έγκυρο ζεύγος ανά στόχο |
| `-V` | Αναλυτική εμφάνιση δοκιμών |
| `-vV` | Πολύ αναλυτική έξοδος |
| `-d` | Λειτουργία αποσφαλμάτωσης |

### Πρώτη γνωριμία
```bash
# Ένας χρήστης και λίστα κωδικών
hydra -l admin -P passwords.txt target ssh

# Λίστα χρηστών και ένας κωδικός
hydra -L users.txt -p password123 target ssh

# Λίστα χρηστών και λίστα κωδικών
hydra -L users.txt -P passwords.txt target ssh
```

---

<a id="section-04"></a>

## 📡 Υποστηριζόμενα πρωτόκολλα

### Λίστα πρωτοκόλλων του πρωτοτύπου

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

<a id="section-05"></a>

## 🔐 Δοκιμές σύνδεσης SSH

### Βασικές δοκιμές SSH
```bash
# Ένας χρήστης
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Πολλαπλοί χρήστες
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Συγκεκριμένη θύρα
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH με πρόσθετες επιλογές
```bash
# Περιορισμός παράλληλων εργασιών· δεν περιορίζει συνολικά τις προσπάθειες ή τα κλειδώματα
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Διακοπή στην πρώτη επιτυχία ανά στόχο
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Αναλυτική έξοδος
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Ταυτοποίηση SSH με κλειδί

> **Το παράδειγμα χρειάζεται διόρθωση:** Το module sshkey χρησιμοποιεί αρχεία ιδιωτικών κλειδιών για ταυτοποίηση. Η παραγωγή συμβολοσειρών με `-x` δεν αποτελεί γενικό έλεγχο «αδύναμων κλειδιών» ούτε ανάκτηση συνθηματικού ενός κλειδιού. Δείτε τον [κώδικα του module sshkey](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c).

```bash
# Παράδειγμα sshkey του πρωτοτύπου· δεν είναι γενική δοκιμή ισχύος ιδιωτικών κλειδιών
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

<a id="section-06"></a>

## 📂 Δοκιμές σύνδεσης FTP

### Βασικές δοκιμές FTP
```bash
# Δοκιμή στοιχείων σύνδεσης FTP από λίστα
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Έλεγχος ανώνυμης σύνδεσης FTP
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Πολλαπλοί χρήστες
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS, δηλαδή FTP μέσω SSL/TLS
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

<a id="section-07"></a>

## 🌐 Δοκιμές σύνδεσης μέσω φορμών HTTP

> **Αναγνώριση πραγματικής σύνδεσης:** Επιβεβαιώστε το πραγματικό αίτημα, τις παραμέτρους και τις αποκρίσεις επιτυχίας/αποτυχίας. Αλλαγή μηνύματος, ανακατεύθυνση, ληγμένο cookie, token CSRF ή πρόσθετο βήμα ταυτοποίησης μπορεί να ακυρώσει την ένδειξη του παραδείγματος. Η σύνταξη κεφαλίδων με άνω και κάτω τελεία εξαρτάται από τους κανόνες διαφυγής του module· το παράδειγμα Cookie δεν είναι γενική έτοιμη ρύθμιση.


### Φόρμα HTTP GET (HTTP-GET-FORM)

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Μορφή:
# "/path:params:failure_string"
```

### Φόρμα HTTP POST (HTTP-POST-FORM)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Σύνταξη ελέγχου φόρμας

```
"/page:POST_params:Failure_string"

Όπου:
- ^USER^ = Σημείο εισαγωγής ονόματος χρήστη
- ^PASS^ = Σημείο εισαγωγής κωδικού πρόσβασης
- Failure_string = Κείμενο που εμφανίζεται σε αποτυχημένη σύνδεση
```

### Αναγνώριση βάσει ένδειξης επιτυχίας

```bash
# Χρήση S= για ένδειξη επιτυχίας αντί αποτυχίας
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Με cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### Βασική ταυτοποίηση HTTP (Basic Auth)

```bash
# Βασική ταυτοποίηση HTTP
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### Proxy HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="section-08"></a>

## 🗄️ Δοκιμές σύνδεσης σε βάσεις δεδομένων

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Συγκεκριμένη θύρα
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Δοκιμές σύνδεσης Oracle με SID
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Δοκιμές Oracle listener
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="section-09"></a>

## 🖥️ Δοκιμές απομακρυσμένης επιφάνειας εργασίας

### RDP, απομακρυσμένη επιφάνεια εργασίας Windows

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Περιορισμός παράλληλων εργασιών RDP
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC με κωδικό χωρίς όνομα χρήστη
hydra -P passwords.txt vnc://192.168.1.1

# Επιλογή συγκεκριμένης θύρας VNC, όχι έκδοσης πρωτοκόλλου
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

<a id="section-10"></a>

## 🔧 Άλλες υπηρεσίες

### SMB, κοινόχρηστοι πόροι Windows

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# Ταυτοποίηση SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Αναζήτηση πληροφοριών SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

<a id="section-11"></a>

## 📚 Λίστες λέξεων και κωδικοί πρόσβασης

### Συνήθεις λίστες λέξεων

```bash
# Ενδεικτικές τοποθεσίες στο Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Λίστες κωδικών SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Παραγωγή υποψήφιων κωδικών (-x)

> **Σύνολο χαρακτήρων:** Τα `a`, `A` και `1` έχουν ειδική σημασία στην παραγωγή υποψήφιων τιμών. Το `!` δεν σημαίνει αυτόματα «όλοι οι ειδικοί χαρακτήρες». Ελέγξτε την ειδική βοήθεια της εγκατεστημένης έκδοσης για την επιλογή `-x`.


```bash
# Παραγωγή κωδικών: min:max:charset, ελάχιστο και μέγιστο μήκος και σύνολο χαρακτήρων
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 πεζά γράμματα
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 κεφαλαία γράμματα
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 ψηφία
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Συνδυασμός τύπων στόχων

# Επιλογές συνόλου χαρακτήρων:
# a = πεζά γράμματα    A = κεφαλαία γράμματα
# 1 = ψηφία      ! = κυριολεκτικός χαρακτήρας, όχι κατηγορία όλων των συμβόλων
```

### Αρχείο ζευγών με διαχωριστικό : (-C)

```bash
# Μορφή user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Προσαρμογή δοκιμών ονόματος χρήστη και κωδικού

```bash
# Κενοί κωδικοί
hydra -l admin -e n 192.168.1.1 ssh

# Δοκιμή ονόματος χρήστη ως κωδικού
hydra -l admin -e s 192.168.1.1 ssh

# Δοκιμή ανεστραμμένου ονόματος χρήστη ως κωδικού
hydra -l admin -e r 192.168.1.1 ssh

# Όλοι οι ειδικοί έλεγχοι κενού, ίδιου ή ανεστραμμένου ονόματος
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

<a id="section-12"></a>

## ⚡ Προχωρημένες επιλογές

### Αποτελέσματα και καταγραφή

```bash
# Αποθήκευση αποτελεσμάτων σε αρχείο
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Εξαγωγή αποτελεσμάτων σε JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Συνέχιση διακοπείσας συνεδρίας

> **Αποθήκευση και επαναφορά:** Το `-o` αποθηκεύει ευρήματα, ενώ το `-R` επαναφέρει προηγούμενη διακοπείσα συνεδρία. Η πρώτη γραμμή του πρωτοτύπου δεν είναι εντολή δημιουργίας νέας συνεδρίας με αποθήκευση προόδου.


```bash
# Παράδειγμα του πρωτοτύπου με -R· αφορά επαναφορά, όχι αποθήκευση νέας συνεδρίας
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Συνέχιση διακοπείσας συνεδρίας
hydra -R
```

### Χρήση ενδιάμεσου διακομιστή (proxy)

```bash
# Proxy HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Διαμεσολαβητής συνδέσεων SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Ρύθμιση απόδοσης

> **Διόρθωση χρονισμού:** Το `-w` είναι αναμονή απόκρισης, το `-W` αφορά χρόνο μεταξύ συνδέσεων ανά εργασία, το `-t` παράλληλες εργασίες ανά στόχο και το `-T` συνολικές παράλληλες εργασίες για πολλούς στόχους. Συνεπώς τα σχόλια «Wait time between attempts» και «Connection timeout» του πρωτοτύπου είναι λανθασμένα. Δείτε την [επίσημη βοήθεια στον κώδικα Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).


```bash
# Παράλληλες εργασίες (προεπιλογή του πρωτοτύπου: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Μέγιστη αναμονή απόκρισης με -w, όχι παύση μεταξύ προσπαθειών
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Παράδειγμα με -T· αφορά συνολικές παράλληλες εργασίες, όχι timeout
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Πολλαπλοί στόχοι

```bash
# Από αρχείο
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

<a id="section-13"></a>

## 🎬 Παραδείγματα σεναρίων ελέγχου

### Παράδειγμα 1: Δοκιμές κωδικών SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Παράδειγμα 2: Φόρμα σύνδεσης web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Παράδειγμα 3: Σύνδεση WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Παράδειγμα 4: Παράδειγμα πολλαπλών υπηρεσιών του πρωτοτύπου

> **Μη έγκυρη γενική σύνταξη:** Η προσθήκη `ssh ftp mysql` στο τέλος δεν επιλέγει τρεις υπηρεσίες σε μία κανονική εκτέλεση Hydra. Το παράδειγμα διατηρείται ως έχει, με αυτή τη διευκρίνιση.

```bash
# Παράδειγμα πολλαπλών υπηρεσιών του πρωτοτύπου· δεν είναι έγκυρη γενική σύνταξη
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Παράδειγμα 5: Ίδιος κωδικός για πολλούς χρήστες
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Παράδειγμα 6: Με proxy ή Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Παράδειγμα 7: Βασική ταυτοποίηση HTTP σε περιοχή διαχείρισης
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Παράδειγμα 8: Δοκιμές σύνδεσης RDP
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

<a id="section-14"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εργασία | Εντολή |
| ------ | --------- |
| Δοκιμές σύνδεσης SSH | `hydra -l user -P pass.txt ssh://target` |
| Δοκιμές σύνδεσης FTP | `hydra -l user -P pass.txt ftp://target` |
| Έλεγχος φόρμας HTTP POST | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Δοκιμές σύνδεσης RDP | `hydra -l user -P pass.txt rdp://target` |
| Αποθήκευση αποτελεσμάτων | `hydra ... -o results.txt` |
| Συνέχιση συνεδρίας | `hydra -R` |

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-l/-L` | Όνομα χρήστη ή λίστα ονομάτων |
| `-p/-P` | Κωδικός ή λίστα κωδικών |
| `-C` | Αρχείο ζευγών user:pass |
| `-t` | Παράλληλες εργασίες (threads) |
| `-s` | Θύρα |
| `-f` | Διακοπή σε επιτυχία ανά στόχο |
| `-V` | Αναλυτική έξοδος |
| `-o` | Αρχείο εξόδου |
| `-x` | Παραγωγή υποψήφιων κωδικών |
| `-e nsr` | Δοκιμή κενού κωδικού, ονόματος χρήστη ή ανεστραμμένου ονόματος |

---

<a id="section-15"></a>

## 💡 Συμβουλές και καλές πρακτικές

> **Ρυθμός και κλειδώματα:** Λιγότερες παράλληλες εργασίες δεν εγγυώνται αποφυγή κλειδώματος. Τα παραδείγματα `-w` στην ενότητα Stealth δεν εισάγουν παύση μεταξύ προσπαθειών. Proxy, Tor και μικρός αριθμός tasks δεν εγγυώνται ανωνυμία ή μη καταγραφή.


### Συμβουλές απόδοσης

1. **Περιορισμός παράλληλων εργασιών για ευαίσθητες υπηρεσίες**
   ```bash
   # RDP και SMB: μικρός αριθμός παράλληλων εργασιών
   hydra -t 1 ...
   
   # SSH: μέτριος αριθμός παράλληλων εργασιών
   hydra -t 4 ...
   
   # Φόρμες web: προσαρμογή μόνο εντός του επιτρεπόμενου ρυθμού
   hydra -t 16 ...
   ```

2. **Χρήση λιστών σχετικών με το εγκεκριμένο πεδίο ελέγχου**
   ```bash
   # Ενδεικτικές μικρότερες ή σχετικότερες λίστες:
   # - Λίστα 1000 συνηθισμένων κωδικών
   # - Αναζήτηση των όρων του παραδείγματος — δεν αποδεικνύει πρόσβαση
   # - Λίστα σχετική με τον οργανισμό του εγκεκριμένου ελέγχου
   ```

3. **Πρώτα έλεγχος πολιτικής κλειδώματος λογαριασμών**
   ```bash
   # Αρχικά έλεγχος με μικρή λίστα, μέσα στα συμφωνημένα όρια προσπαθειών
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Παραδείγματα μεταβολής ιχνών του πρωτοτύπου

```bash
# Παράδειγμα με -w· είναι όριο αναμονής απόκρισης, όχι καθυστέρηση μεταξύ προσπαθειών
hydra -l admin -P passwords.txt -w 30 target ssh

# Μικρός αριθμός παράλληλων εργασιών
hydra -l admin -P passwords.txt -t 2 target ssh

# Μέσω ενδιάμεσου διακομιστή (proxy)
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

<a id="section-16"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Χρησιμοποιείτε το Hydra μόνο για **εξουσιοδοτημένους ελέγχους**.
> 
> - ✅ Έλεγχοι σε συστήματα που σας ανήκουν
> - ✅ Έλεγχοι με ρητή γραπτή άδεια
> - ✅ Χρήση σε εξουσιοδοτημένους ελέγχους διείσδυσης
> - ❌ Μην εκτελείτε δοκιμές πρόσβασης χωρίς εξουσιοδότηση
> - ❌ Οι δοκιμές brute force χωρίς άδεια μπορεί να είναι παράνομες
> 
> **Μη εξουσιοδοτημένες απόπειρες πρόσβασης μπορεί να επιφέρουν νομικές κυρώσεις.**

---

<a id="section-17"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Αποθετήριο THC-Hydra στο GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Εγχειρίδιο Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Λίστες λέξεων (wordlists)
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Προεπιλεγμένα στοιχεία σύνδεσης](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Σχετικοί οδηγοί
- [Nmap](../../Nmap/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)
- [SQLMap](../../SQLMap/translations/README.el.md)

---

<p align="center">
  <b>🔓 Εξασκηθείτε στους ελέγχους σύνδεσης δικτύου!</b><br>
  <i>Εξασφαλίστε πρώτα εξουσιοδότηση για τον έλεγχο</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
