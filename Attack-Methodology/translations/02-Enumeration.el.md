# 🔍 Φάση 2: Αναγνώριση και απαρίθμηση (Discovery / Enumeration)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../02-Enumeration.md) • [🇮🇹 Italiano](02-Enumeration.it.md) • [🇹🇷 Türkçe](02-Enumeration.tr.md)

```
  ███████╗███╗   ██╗██╗   ██╗███╗   ███╗███████╗██████╗  █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝████╗  ██║██║   ██║████╗ ████║██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
  █████╗  ██╔██╗ ██║██║   ██║██╔████╔██║█████╗  ██████╔╝███████║   ██║   ██║██║   ██║██╔██╗ ██║
  ██╔══╝  ██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
  ███████╗██║ ╚████║╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Στόχος:** Συλλογή πληροφοριών για το σύστημα και το δίκτυο όπου έχει αποκτηθεί πρόσβαση, ώστε να σχεδιαστούν τα επόμενα βήματα.

---

## Πριν ξεκινήσετε

Η **αναγνώριση (discovery)** δείχνει ποια συστήματα και υπηρεσίες υπάρχουν. Η **απαρίθμηση (enumeration)** συλλέγει περισσότερες λεπτομέρειες: χρήστες, ομάδες, δικαιώματα, διεργασίες, κοινόχρηστους φακέλους και ρυθμίσεις. Σημειώνουμε τι βρήκαμε και ποια δικαιώματα έχει ο λογαριασμός μας, αντί να θεωρούμε κάθε αποτέλεσμα ευπάθεια.

**Βασικοί όροι:** **Διεργασία (process)** είναι ένα πρόγραμμα που εκτελείται. **Υπηρεσία (service)** είναι λειτουργία που συνήθως εκτελείται στο παρασκήνιο. **Τομέας (domain)** στο Active Directory είναι περιβάλλον κεντρικής διαχείρισης χρηστών και συσκευών. Το **DNS** αντιστοιχίζει ονόματα σε διευθύνσεις, το **SMB** χρησιμοποιείται μεταξύ άλλων για κοινόχρηστους πόρους, το **LDAP** για ερωτήματα σε υπηρεσίες καταλόγου και το **Kerberos** για ταυτοποίηση μέσω εισιτηρίων.

Τα αποτελέσματα εντολών που αναζητούν κωδικούς ή ιδιωτικά κλειδιά είναι ευαίσθητα. Ορισμένες εντολές απαιτούν περισσότερα δικαιώματα ή προγράμματα που δεν είναι εγκατεστημένα. Τα `winPEAS` και `linPEAS` συγκεντρώνουν πιθανά σημεία ελέγχου· δεν αποδεικνύουν αυτομάτως εκμεταλλεύσιμη αδυναμία.

## Περιεχόμενα

- [🖥️ Συλλογή πληροφοριών στα Windows](#section-01)
- [🐧 Συλλογή πληροφοριών στο Linux](#section-02)
- [🏢 Συλλογή πληροφοριών στο Active Directory](#section-03)
- [📡 Συλλογή πληροφοριών δικτύου](#section-04)
- [🛠️ Εργαλεία αυτόματης συλλογής πληροφοριών](#section-05)
- [📊 Γρήγορη αναφορά](#section-06)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-07)

<a id="section-01"></a>

## 🖥️ Συλλογή πληροφοριών στα Windows

### Πληροφορίες συστήματος
```cmd
# Βασικές πληροφορίες συστήματος
systeminfo
hostname
whoami /all

# Έκδοση λειτουργικού συστήματος
wmic os get caption,version,buildnumber

# Αρχιτεκτονική επεξεργαστή
echo %PROCESSOR_ARCHITECTURE%

# Μεταβλητές περιβάλλοντος
set
```

### Εντοπισμός χρηστών και ομάδων
```cmd
# Προνόμια του τρέχοντος χρήστη
whoami /priv
whoami /groups

# Όλοι οι τοπικοί χρήστες
net user
net user administrator

# Όλες οι τοπικές ομάδες χρηστών
net localgroup
net localgroup Administrators

# Χρήστες τομέα, εφόσον το σύστημα ανήκει σε τομέα
net user /domain
net group /domain
net group "Domain Admins" /domain
```

### Πληροφορίες δικτύου
```cmd
# Ρυθμίσεις διευθύνσεων IP
ipconfig /all

# Πίνακας δρομολόγησης
route print

# Πίνακας αντιστοίχισης διευθύνσεων ARP
arp -a

# Ενεργές συνδέσεις
netstat -ano
netstat -ano | findstr ESTABLISHED
netstat -ano | findstr LISTENING

# Προσωρινά αποθηκευμένες απαντήσεις DNS
ipconfig /displaydns

# Κοινόχρηστοι πόροι
net share
net view \\localhost
```

### Διεργασίες και υπηρεσίες
```cmd
# Διεργασίες που εκτελούνται
tasklist
tasklist /SVC
wmic process list brief

# Υπηρεσίες
sc query
net start
wmic service list brief

# Προγραμματισμένες εργασίες
schtasks /query /fo LIST /v
```

### Εγκατεστημένο λογισμικό
```cmd
# Εγκατεστημένα προγράμματα
wmic product get name,version
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall

# Εγκατεστημένες διορθώσεις ασφαλείας
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Στοιχεία σύνδεσης και ευαίσθητα αρχεία
```cmd
# Αποθηκευμένα στοιχεία σύνδεσης
cmdkey /list

# Αποθηκευμένοι κωδικοί ασύρματων δικτύων
netsh wlan show profiles
netsh wlan show profile name="SSID" key=clear

# Αναζήτηση κωδικών πρόσβασης σε αρχεία
findstr /si password *.txt *.ini *.config *.xml
dir /s *pass* *cred* *vnc* *.config

# Αρχεία αυτοματοποιημένης εγκατάστασης (Unattend)
dir /s C:\*unattend.xml
dir /s C:\*sysprep.inf
```

---

<a id="section-02"></a>

## 🐧 Συλλογή πληροφοριών στο Linux

### Πληροφορίες συστήματος
```bash
# Βασικές πληροφορίες
hostname
uname -a
cat /etc/os-release
cat /proc/version

# Αρχιτεκτονική επεξεργαστή
arch

# Έκδοση πυρήνα
uname -r
```

### Εντοπισμός χρηστών και ομάδων
```bash
# Τρέχων χρήστης
whoami
id
groups

# Όλοι οι χρήστες
cat /etc/passwd
cat /etc/shadow  # εφόσον επιτρέπεται η ανάγνωση
cat /etc/group

# Συνδεδεμένοι χρήστες
w
who
last

# Επιτρεπόμενες εντολές μέσω sudo
sudo -l
cat /etc/sudoers
```

### Πληροφορίες δικτύου
```bash
# Ρυθμίσεις διευθύνσεων IP
ip a
ifconfig

# Δρομολόγηση
ip route
route -n

# ARP
arp -a
ip neigh

# Ενεργές συνδέσεις
ss -tulnp
netstat -tulnp

# DNS
cat /etc/resolv.conf
```

### Διεργασίες και υπηρεσίες
```bash
# Διεργασίες που εκτελούνται
ps aux
ps auxwww
top

# Υπηρεσίες
systemctl list-units --type=service
service --status-all

# Προγραμματισμένες εργασίες cron
cat /etc/crontab
ls -la /etc/cron.*
crontab -l
```

### Εγκατεστημένο λογισμικό
```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Εκτελέσιμα SUID — πιθανά σημεία ελέγχου κλιμάκωσης προνομίων
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null
```

### Στοιχεία σύνδεσης και ευαίσθητα αρχεία
```bash
# Κλειδιά SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Αρχεία ιστορικού εντολών
cat ~/.bash_history
cat ~/.zsh_history

# Αρχεία ρυθμίσεων που περιέχουν κωδικούς πρόσβασης
grep -r "password" /etc/ 2>/dev/null
grep -r "pass" /home/ 2>/dev/null

# Αρχεία που αξίζει να εξεταστούν
cat /etc/fstab
cat ~/.bashrc
cat ~/.profile
```

---

<a id="section-03"></a>

## 🏢 Συλλογή πληροφοριών στο Active Directory

### PowerView
```powershell
# Φόρτωση της μονάδας κώδικα
Import-Module .\PowerView.ps1

# Ανάκτηση πληροφοριών τομέα
Get-Domain
Get-DomainController

# Ανάκτηση χρηστών
Get-DomainUser
Get-DomainUser -Identity admin
Get-DomainUser | select samaccountname,description

# Ανάκτηση ομάδων
Get-DomainGroup
Get-DomainGroupMember -Identity "Domain Admins"

# Ανάκτηση υπολογιστών
Get-DomainComputer
Get-DomainComputer | select dnshostname,operatingsystem

# Ανάκτηση αντικειμένων πολιτικής ομάδας (GPO)
Get-DomainGPO
Get-DomainGPOLocalGroup
```

### BloodHound
```bash
# Συλλογή δεδομένων με SharpHound
.\SharpHound.exe -c All

# Εναλλακτικά με PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound -CollectionMethod All

# Συλλογή δεδομένων μέσω Python από Linux
bloodhound-python -u user -p 'password' -d domain.local -ns 192.168.1.1 -c All
```

### Συλλογή πληροφοριών μέσω LDAP
```bash
# Από Linux με ldapsearch
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local"

# Χρήστες
ldapsearch -x -H ldap://192.168.1.1 -b "DC=domain,DC=local" "(objectClass=user)"

# Με στοιχεία σύνδεσης
ldapsearch -x -H ldap://192.168.1.1 -D "user@domain.local" -w 'password' -b "DC=domain,DC=local"
```

### Συλλογή πληροφοριών για Kerberos
```bash
# Εντοπισμός χρηστών με Kerbrute
kerbrute userenum -d domain.local users.txt --dc 192.168.1.1

# Χρήστες που ενδέχεται να επιτρέπουν AS-REP Roasting
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt

# Λογαριασμοί υπηρεσιών που ενδέχεται να επιτρέπουν Kerberoasting
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1
```

---

<a id="section-04"></a>

## 📡 Συλλογή πληροφοριών δικτύου

### Εντοπισμός ενεργών συσκευών (Host Discovery)
```bash
# Έλεγχος ενεργών συσκευών στο εύρος διευθύνσεων
nmap -sn 192.168.1.0/24

# Σάρωση ARP στο τοπικό δίκτυο
arp-scan -l

# Netdiscover
netdiscover -r 192.168.1.0/24
```

### Σάρωση θυρών (Port Scanning)
```bash
# Γρήγορη σάρωση
nmap -F 192.168.1.10

# Σάρωση όλων των θυρών
nmap -p- 192.168.1.10

# Εντοπισμός υπηρεσιών και εκδόσεων
nmap -sV -sC 192.168.1.10

# Σάρωση θυρών UDP
nmap -sU --top-ports 100 192.168.1.10
```

### Συλλογή πληροφοριών μέσω SMB
```bash
# Enum4linux
enum4linux -a 192.168.1.10

# Κοινόχρηστοι πόροι SMB
smbclient -L //192.168.1.10 -N
smbmap -H 192.168.1.10
crackmapexec smb 192.168.1.10 --shares

# Με στοιχεία σύνδεσης
smbclient //192.168.1.10/share -U user
```

### Συλλογή πληροφοριών μέσω SNMP
```bash
# Προεπιλεγμένες συμβολοσειρές πρόσβασης SNMP (Community Strings)
snmpwalk -v2c -c public 192.168.1.10
snmpwalk -v2c -c private 192.168.1.10

# Συλλογή πληροφοριών με onesixtyone
onesixtyone -c community.txt 192.168.1.10
```

---

<a id="section-05"></a>

## 🛠️ Εργαλεία αυτόματης συλλογής πληροφοριών

### Windows
```bash
# WinPEAS
.\winPEAS.exe

# Seatbelt
.\Seatbelt.exe -group=all

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --database 2024.xlsx --systeminfo systeminfo.txt
```

### Linux
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# LSE (Linux Smart Enumeration)
./lse.sh -l 2
```

---

<a id="section-06"></a>

## 📊 Γρήγορη αναφορά

### Σύντομος έλεγχος Windows
```cmd
systeminfo
whoami /all
net user
net localgroup Administrators
ipconfig /all
netstat -ano
tasklist /SVC
```

### Σύντομος έλεγχος Linux
```bash
uname -a && id && sudo -l
cat /etc/passwd
ps aux
netstat -tulnp
find / -perm -4000 2>/dev/null
```

---

<a id="section-07"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Nmap](../../Nmap/translations/README.el.md)
- [BloodHound](../../BloodHound/translations/README.el.md)
- [PowerView](../../PowerView/translations/README.el.md)
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 01 — Αρχική πρόσβαση](01-Initial-Access.el.md)

**Επόμενη φάση:** [03 — Κλιμάκωση προνομίων →](03-Privilege-Escalation.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
