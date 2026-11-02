# 📊 Ανάλυση καταγραφών (Log Analysis)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Log-Analysis.md) • [🇮🇹 Italiano](Log-Analysis.it.md) • [🇹🇷 Türkçe](Log-Analysis.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## Πριν ξεκινήσετε

Οι **καταγραφές (logs)** αποτυπώνουν ενέργειες και συμβάντα: ποιος συνδέθηκε, τι εκτελέστηκε, πότε και από πού. Ένα **Event ID** προσδιορίζει τύπο συμβάντος μέσα στη συγκεκριμένη πηγή καταγραφής· ο ίδιος αριθμός σε άλλη πηγή δεν έχει απαραίτητα την ίδια σημασία.

Συσχετίζουμε **χρόνο, χρήστη, σύστημα και διεύθυνση προέλευσης**. Ένα αποτυχημένο login μπορεί να είναι λάθος πληκτρολόγησης, ενώ πολλά αποτυχημένα logins απαιτούν διερεύνηση. Ούτε η επιτυχημένη σύνδεση ούτε η πρόσβαση στη LSASS αποδεικνύει από μόνη της επίθεση. Το **Sysmon** είναι πρόσθετο εργαλείο τηλεμετρίας Windows και απαιτεί εγκατάσταση και κατάλληλες ρυθμίσεις.

Οι διαδρομές Linux εξαρτώνται από διανομή και ρυθμίσεις. Οι αναφορές PowerShell σε `Properties[αριθμός]` και οι αναζητήσεις awk σε αριθμημένα πεδία εξαρτώνται από την πραγματική δομή των συμβάντων. Ελέγχουμε τα ονόματα και τις τιμές των πεδίων πριν ερμηνεύσουμε αποτελέσματα. Οι αγγλικές λέξεις μέσα σε αναζητήσεις, όπως `Failed password`, παραμένουν αμετάβλητες επειδή είναι μοτίβα προς αναζήτηση.

## Περιεχόμενα

- [🖥️ Καταγραφές συμβάντων Windows](#section-01)
- [🐧 Ανάλυση καταγραφών Linux](#section-02)
- [🌐 Καταγραφές διακομιστών ιστού](#section-03)
- [🛡️ Ανάλυση καταγραφών Sysmon](#section-04)
- [📊 Γρήγορη αναφορά](#section-05)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-06)

<a id="section-01"></a>

## 🖥️ Καταγραφές συμβάντων Windows

### Τοποθεσίες καταγραφών
```
# Καταγραφή ασφαλείας (Security)
C:\Windows\System32\winevt\Logs\Security.evtx

# Καταγραφή συστήματος (System)
C:\Windows\System32\winevt\Logs\System.evtx

# Καταγραφή εφαρμογών (Application)
C:\Windows\System32\winevt\Logs\Application.evtx

# Καταγραφές PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon, εφόσον είναι εγκατεστημένο
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Σημαντικοί κωδικοί συμβάντων ασφαλείας

#### Συμβάντα ταυτοποίησης
| Κωδικός συμβάντος (Event ID) | Περιγραφή | Χρησιμότητα |
| ---------- | ------------- | ------------ |
| **4624** | Επιτυχημένη σύνδεση | Παρακολούθηση πρόσβασης χρηστών |
| **4625** | Αποτυχημένη σύνδεση | Ενδείξεις επαναλαμβανόμενων δοκιμών κωδικών |
| **4634** | Αποσύνδεση | Παρακολούθηση συνεδριών |
| **4647** | Αποσύνδεση με ενέργεια του χρήστη | Παρακολούθηση συνεδριών |
| **4648** | Απόπειρα σύνδεσης με ρητά δηλωμένα στοιχεία | Ενδείξεις πλευρικής μετακίνησης |
| **4672** | Απόδοση ειδικών προνομίων | Δραστηριότητα με αυξημένα προνόμια |
| **4768** | Αίτημα εισιτηρίου ταυτοποίησης TGT στο Kerberos | Ταυτοποίηση στο Active Directory |
| **4769** | Αίτημα εισιτηρίου υπηρεσίας Kerberos | Πρόσβαση σε υπηρεσία |
| **4771** | Αποτυχία προεπαλήθευσης Kerberos | Ενδείξεις δοκιμών κωδικών σε πολλούς χρήστες |
| **4776** | Έλεγχος στοιχείων σύνδεσης μέσω NTLM | Επαλήθευση στοιχείων σύνδεσης |

#### Τύποι σύνδεσης για τα συμβάντα 4624/4625
| Τύπος | Περιγραφή |
| ------ | ------------- |
| 2 | Διαδραστική σύνδεση στον ίδιο υπολογιστή |
| 3 | Δίκτυο |
| 4 | Μαζική εργασία παρασκηνίου (Batch) |
| 5 | Υπηρεσία |
| 7 | Ξεκλείδωμα συνεδρίας |
| 8 | Τύπος NetworkCleartext — δεν αποδεικνύει μόνος του μη κρυπτογραφημένη μεταφορά στο δίκτυο |
| 9 | Νέα στοιχεία σύνδεσης για εξερχόμενη πρόσβαση |
| 10 | Απομακρυσμένη διαδραστική σύνδεση μέσω RDP |
| 11 | Διαδραστική σύνδεση με προσωρινά αποθηκευμένα στοιχεία |

#### Διαχείριση λογαριασμών
| Κωδικός συμβάντος (Event ID) | Περιγραφή |
| ---------- | ------------- |
| **4720** | Δημιουργία λογαριασμού χρήστη |
| **4722** | Ενεργοποίηση λογαριασμού χρήστη |
| **4723** | Απόπειρα αλλαγής κωδικού |
| **4724** | Επαναφορά κωδικού |
| **4725** | Απενεργοποίηση λογαριασμού |
| **4726** | Διαγραφή λογαριασμού |
| **4728** | Προσθήκη μέλους σε καθολική ομάδα ασφαλείας (Global) |
| **4732** | Προσθήκη μέλους σε τοπική ομάδα ασφαλείας |
| **4756** | Προσθήκη μέλους σε καθολική ομάδα ασφαλείας τύπου Universal |

#### Συμβάντα διεργασιών και υπηρεσιών
| Κωδικός συμβάντος (Event ID) | Περιγραφή |
| ---------- | ------------- |
| **4688** | Δημιουργία διεργασίας |
| **4689** | Τερματισμός διεργασίας |
| **7045** | Εγκατάσταση υπηρεσίας |
| **7036** | Αλλαγή κατάστασης υπηρεσίας |
| **4697** | Εγκατάσταση υπηρεσίας στην καταγραφή Security |

#### Προγραμματισμένες εργασίες (Scheduled Tasks)
| Κωδικός συμβάντος (Event ID) | Περιγραφή |
| ---------- | ------------- |
| **4698** | Δημιουργία προγραμματισμένης εργασίας |
| **4699** | Διαγραφή προγραμματισμένης εργασίας |
| **4700** | Ενεργοποίηση προγραμματισμένης εργασίας |
| **4702** | Ενημέρωση προγραμματισμένης εργασίας |

### Εντολές αναζήτησης συμβάντων με PowerShell
```powershell
# Ανάκτηση καταχωρίσεων ασφαλείας
Get-WinEvent -LogName Security -MaxEvents 100

# Αποτυχημένες συνδέσεις — συμβάν 4625
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Επιτυχημένες συνδέσεις — συμβάν 4624
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Δημιουργία νέας διεργασίας — συμβάν 4688
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Εγκατάσταση υπηρεσίας — συμβάν 7045
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Αναζήτηση με χρονικό εύρος
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Εντολές διαχείρισης καταγραφών με wevtutil

Η `wevtutil cl Security` διαγράφει καταγραφές και δεν είναι εντολή ανάλυσης. Αποφεύγεται κατά τη διαφύλαξη στοιχείων περιστατικού.

```cmd
# Αναζήτηση στην καταγραφή Security
wevtutil qe Security /c:50 /f:text

# Αναζήτηση συγκεκριμένου κωδικού συμβάντος
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Εξαγωγή καταγραφής σε αρχείο
wevtutil epl Security C:\evidence\security.evtx

# Διαγραφή καταγραφής — καταστρέφει διαθέσιμα στοιχεία
wevtutil cl Security
```

---

<a id="section-02"></a>

## 🐧 Ανάλυση καταγραφών Linux

### Τοποθεσίες καταγραφών
```bash
# Ταυτοποίηση
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# Σύστημα
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Πυρήνας
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Έλεγχος συμβάντων (Audit)
/var/log/audit/audit.log

# Διακομιστές ιστού
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Συνήθεις εντολές ανάλυσης καταγραφών

#### Ανάλυση ταυτοποίησης χρηστών
```bash
# Αποτυχημένες απόπειρες SSH
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Επιτυχημένες συνδέσεις SSH
grep "Accepted" /var/log/auth.log

# Πλήθος αποτυχημένων δοκιμών SSH ανά IP
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Χρήση αυξημένων προνομίων μέσω sudo
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Απόπειρες αλλαγής χρήστη με su
grep "su:" /var/log/auth.log
```

#### Ανάλυση δραστηριότητας συστήματος
```bash
# Χρόνοι εκκίνησης και τερματισμού συστήματος
last -x | grep shutdown
last -x | grep reboot

# Χρήστες που είναι συνδεδεμένοι
who
w

# Ιστορικό συνδέσεων
last
lastlog

# Αποτυχημένες απόπειρες σύνδεσης
lastb
```

#### Ανάλυση διεργασιών και υπηρεσιών
```bash
# Εκτελέσεις προγραμματισμένων εργασιών cron
grep CRON /var/log/syslog

# Εκκινήσεις και διακοπές υπηρεσιών
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Μηνύματα πυρήνα
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Χρήσιμα μοτίβα αναζήτησης με grep και awk
```bash
# Εξαγωγή διευθύνσεων που μοιάζουν με IPv4 από καταγραφές
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Καταμέτρηση εμφανίσεων ανά IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Αναζήτηση χρονικού διαστήματος
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Αναζήτηση σε πολλά αρχεία
grep -r "error" /var/log/

# Παρακολούθηση σε πραγματικό χρόνο
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Ανάγνωση του ημερολογίου systemd με journalctl
```bash
# Όλες οι καταγραφές
journalctl

# Συνεχής εμφάνιση νέων συμβάντων
journalctl -f

# Από συγκεκριμένη χρονική στιγμή
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Ανά μονάδα ή υπηρεσία
journalctl -u sshd
journalctl -u nginx

# Ανά προτεραιότητα
journalctl -p err       # μόνο σφάλματα
journalctl -p warning   # προειδοποιήσεις και υψηλότερες προτεραιότητες

# Μηνύματα πυρήνα
journalctl -k

# Έξοδος σε μορφή JSON
journalctl -o json
```

---

<a id="section-03"></a>

## 🌐 Καταγραφές διακομιστών ιστού

### Μορφή καταγραφών Apache
```
# Συνδυασμένη μορφή καταγραφής Apache (Combined Log Format)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Πεδία:
# IP - αναγνωριστικό - χρήστης - [χρόνος] "μέθοδος διαδρομή πρωτόκολλο" κατάσταση μέγεθος "προέλευση συνδέσμου" "φυλλομετρητής"
```

### Μορφή καταγραφών Nginx
```
# Προεπιλεγμένο παράδειγμα μορφής παρόμοιας με Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Ανάλυση καταγραφών ιστού

Ένα αίτημα POST ή μια λέξη όπως `select` δεν αποτελεί απόδειξη επίθεσης. Οι αναζητήσεις επιστρέφουν υποψήφια συμβάντα για έλεγχο του πλήρους αιτήματος και του πλαισίου του.

```bash
# Διευθύνσεις ιστού με τα περισσότερα αιτήματα
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Διευθύνσεις IP με τις περισσότερες εμφανίσεις
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Κωδικοί κατάστασης HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Απαντήσεις HTTP 404
grep ' 404 ' /var/log/apache2/access.log

# Αιτήματα POST προς διερεύνηση — δεν είναι κατ’ ανάγκη επιθέσεις
grep '"POST' /var/log/apache2/access.log

# Αναζήτηση λέξεων που μπορεί να σχετίζονται με έγχυση SQL
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Πιθανές απόπειρες υπέρβασης διαδρομής (Path Traversal)
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Αιτήματα ανά ώρα
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

<a id="section-04"></a>

## 🛡️ Ανάλυση καταγραφών Sysmon

### Βασικοί κωδικοί συμβάντων Sysmon
| Κωδικός συμβάντος (Event ID) | Περιγραφή |
| ---------- | ------------- |
| 1 | Δημιουργία διεργασίας |
| 2 | Αλλαγή της ώρας δημιουργίας αρχείου |
| 3 | Σύνδεση δικτύου |
| 5 | Τερματισμός διεργασίας |
| 6 | Φόρτωση οδηγού συσκευής |
| 7 | Φόρτωση βιβλιοθήκης DLL |
| 8 | Δημιουργία νήματος σε άλλη διεργασία (CreateRemoteThread) |
| 10 | Πρόσβαση σε διεργασία (ProcessAccess) |
| 11 | Δημιουργία αρχείου (FileCreate) |
| 12-14 | Συμβάντα μητρώου Windows |
| 15 | Δημιουργία ροής αρχείου με σύνοψη (FileCreateStreamHash) |
| 17-18 | Συμβάντα διαύλων επικοινωνίας (Pipes) |
| 22 | Ερώτημα DNS |

### Αναζητήσεις Sysmon με PowerShell
```powershell
# Δημιουργία διεργασίας — συμβάν Sysmon 1
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Συνδέσεις δικτύου — συμβάν Sysmon 3
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# Ερωτήματα DNS — συμβάν Sysmon 22
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

### Μοτίβα που χρειάζονται διερεύνηση

| Κατηγορία | Μοτίβο |
| ---------- | --------- |
| **Επαναλαμβανόμενες δοκιμές κωδικών (Brute Force)** | Πολλά συμβάντα 4625 από την ίδια διεύθυνση IP |
| **Δοκιμές κωδικών σε πολλούς χρήστες (Password Spraying)** | Συμβάντα 4625 για πολλούς χρήστες από την ίδια IP |
| **Πλευρική μετακίνηση** | 4624 Type 3 + 4648 |
| **Διατήρηση πρόσβασης** | Συμβάν 4698 — νέα προγραμματισμένη εργασία |
| **Κλιμάκωση προνομίων** | Συμβάν 4672 σε λογαριασμούς όπου δεν αναμένονται ειδικά προνόμια |
| **Εξαγωγή στοιχείων σύνδεσης** | Πρόσβαση στη διεργασία lsass.exe |

### Εργαλεία ανάλυσης καταγραφών
| Εργαλείο | Χρήση |
| ------ | ----- |
| **grep/awk/sed** | Επεξεργασία καταγραφών Linux |
| **PowerShell** | Ανάλυση καταγραφών Windows |
| **Splunk** | Εταιρική πλατφόρμα SIEM |
| **ELK Stack** | Οικοσύστημα ανοικτού κώδικα για συλλογή και ανάλυση συμβάντων |
| **LogParser** | Αναζητήσεις SQL σε καταγραφές |
| **Chainsaw** | Γρήγορη ανάλυση καταγραφών Windows |

---

<a id="section-06"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Αντιμετώπιση περιστατικών](Incident-Response.el.md)
- [Ανίχνευση με SIEM](SIEM-Detection.el.md)
- [Κανόνες Sigma](Sigma-Rules.el.md)

---

**Προηγούμενο θέμα:** [← Αντιμετώπιση περιστατικών](Incident-Response.el.md)

**Επόμενο θέμα:** [Ανίχνευση με SIEM →](SIEM-Detection.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
