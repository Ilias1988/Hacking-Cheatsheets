# 🎯 Προληπτική αναζήτηση απειλών (Threat Hunting)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Threat-Hunting.md) • [🇮🇹 Italiano](Threat-Hunting.it.md) • [🇹🇷 Türkçe](Threat-Hunting.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## Πριν ξεκινήσετε

Η **προληπτική αναζήτηση απειλών (Threat Hunting)** ξεκινά από μια ελέγξιμη υπόθεση και εξετάζει διαθέσιμα δεδομένα για να τη στηρίξει ή να την απορρίψει. Δεν απαιτεί να έχει προηγηθεί ειδοποίηση. Χρειάζεται όμως επαρκής τηλεμετρία, γνωστή κανονική συμπεριφορά και καταγραφή του τι ελέγχθηκε.

Οι **TTPs** είναι τακτικές, τεχνικές και διαδικασίες ενός επιτιθέμενου. Το **IOC hunting** αναζητά συγκεκριμένες ενδείξεις, ενώ η ανάλυση συμπεριφοράς εξετάζει πώς έγιναν οι ενέργειες. Η **γονική διεργασία (parent)** ξεκινά μια **θυγατρική διεργασία (child)**· μια ασυνήθιστη σχέση είναι αφετηρία διερεύνησης, όχι τελικό συμπέρασμα.

Η απουσία αποτελέσματος δεν αποδεικνύει απουσία επίθεσης. Μπορεί να λείπουν οι αναγκαίες καταγραφές ή τα φίλτρα να μην ταιριάζουν στα δεδομένα. Οι δείκτες θέσεων `Properties[...]` του πρωτοτύπου απαιτούν επαλήθευση έναντι του σχήματος συμβάντων.

## Περιεχόμενα

- [🎯 Διαδικασία προληπτικής αναζήτησης απειλών](#section-01)
- [📋 Παραδείγματα ερευνητικών υποθέσεων](#section-02)
- [🔍 Τεχνικές αναζήτησης απειλών](#section-03)
- [🐧 Αναζήτηση απειλών στο Linux](#section-04)
- [📊 Αναζήτηση ενδείξεων παραβίασης (IOC)](#section-05)
- [🛠️ Εργαλεία](#section-06)
- [📋 Σύντομη λίστα ελέγχου αναζήτησης απειλών](#section-07)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-08)

<a id="section-01"></a>

## 🎯 Διαδικασία προληπτικής αναζήτησης απειλών

Διατυπώνουμε υπόθεση → συλλέγουμε δεδομένα → τα αναλύουμε → επιλέγουμε απόκριση → τεκμηριώνουμε αποτελέσματα και μαθήματα.


```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HYPOTHESIS│────▶│ 2. COLLECT  │────▶│ 3. ANALYZE  │────▶│ 4. RESPOND  │
│   (Theory)   │     │   (Data)    │     │   (Hunt)    │     │   (Action)  │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOCUMENT │◀─────────────────────────┘
                          │   (Learn)   │
                          └─────────────┘
```

---

<a id="section-02"></a>

## 📋 Παραδείγματα ερευνητικών υποθέσεων

| Κατηγορία | Υπόθεση προς έλεγχο |
| ---------- | ------------ |
| **Αρχική πρόσβαση** | Ενδέχεται να χρησιμοποιείται ηλεκτρονικό ψάρεμα για παράδοση κακόβουλου λογισμικού |
| **Διατήρηση πρόσβασης** | Ενδέχεται να δημιουργούνται προγραμματισμένες εργασίες για διατήρηση πρόσβασης |
| **Στοιχεία ταυτοποίησης** | Ενδέχεται να εξάγεται η μνήμη LSASS για συλλογή στοιχείων σύνδεσης |
| **Πλευρική μετακίνηση** | Ενδέχεται να χρησιμοποιείται RDP για μετακίνηση μεταξύ συστημάτων |
| **Εξαγωγή δεδομένων** | Ενδέχεται να μεταφέρονται δεδομένα μέσω ερωτημάτων DNS |

---

<a id="section-03"></a>

## 🔍 Τεχνικές αναζήτησης απειλών

### Αναζήτηση μηχανισμών διατήρησης πρόσβασης

#### Κλειδιά αυτόματης εκτέλεσης Run στο μητρώο Windows
```powershell
# Ανάγνωση των επιλεγμένων κλειδιών Run του παραδείγματος
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Προγραμματισμένες εργασίες (Scheduled Tasks)
```powershell
# Καταγραφή μη απενεργοποιημένων προγραμματισμένων εργασιών
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Εργασίες με δηλωμένο συντάκτη διαφορετικό από Microsoft προς διερεύνηση
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Υπηρεσίες

Η εξαίρεση διαδρομών που περιέχουν τη λέξη `Windows` στο παράδειγμα αναζήτησης υπηρεσιών δεν ελέγχει ψηφιακές υπογραφές.

```powershell
# Υπηρεσίες με διαδρομή χωρίς τη λέξη Windows — δεν είναι έλεγχος υπογραφής
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Αναζήτηση ενδείξεων πλευρικής μετακίνησης

#### Συνδέσεις απομακρυσμένης επιφάνειας εργασίας (RDP)
```powershell
# Συνεδρίες RDP — συμβάν 4624, τύπος 10
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Συνδέσεις δικτύου
```powershell
# Τρέχουσες συνδέσεις
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Με τα ονόματα διεργασιών
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Αναζήτηση πρόσβασης σε στοιχεία ταυτοποίησης

#### Πρόσβαση στη διεργασία LSASS
```powershell
# Συμβάν Sysmon 10 — πρόσβαση στη LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Αναζήτηση ύποπτων εκτελέσεων

#### Κωδικοποιημένες εντολές PowerShell
```powershell
# PowerShell με ενδείξεις κωδικοποίησης
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Ασυνήθιστη σχέση γονικής και θυγατρικής διεργασίας
```powershell
# Εφαρμογή Office που εκκινεί cmd ή PowerShell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

<a id="section-04"></a>

## 🐧 Αναζήτηση απειλών στο Linux

### Διατήρηση πρόσβασης
```bash
# Προγραμματισμένες εργασίες cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Εξουσιοδοτημένα δημόσια κλειδιά SSH
find / -name "authorized_keys" 2>/dev/null

# Σενάρια αυτόματης εκκίνησης
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Εκτελέσιμα με ενεργό δικαίωμα SUID
find / -perm -4000 -type f 2>/dev/null
```

### Δίκτυο
```bash
# Θύρες σε αναμονή συνδέσεων
ss -tulnp
netstat -tulnp

# Ενεργές συνδέσεις
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Καταγραφή κίνησης DNS για διερεύνηση
tcpdump -i any port 53 -w dns.pcap
```

### Διεργασίες

Το παράδειγμα που ξεκινά από `ps aux` εξετάζει ήδη ορατές διεργασίες. Δεν αποδεικνύει την ανίχνευση όλων των κρυφών διεργασιών.

```bash
# Όλες οι διεργασίες με πλήρη γραμμή εντολών
ps auxwww

# Δέντρο σχέσεων διεργασιών
pstree -p

# Έλεγχος συνδέσμων εκτελέσιμων ορατών διεργασιών — δεν αποδεικνύει κρυφές διεργασίες
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Εκτελέσιμα που τροποποιήθηκαν πρόσφατα
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

<a id="section-05"></a>

## 📊 Αναζήτηση ενδείξεων παραβίασης (IOC)

### Αναζήτηση βάσει ψηφιακής σύνοψης (Hash)
```powershell
# Υπολογισμός ψηφιακής σύνοψης
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Αναζήτηση γνωστής κακόβουλης ψηφιακής σύνοψης
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Αναζήτηση διευθύνσεων IP και τομέων
```powershell
# Προσωρινά αποθηκευμένες απαντήσεις DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Σύνδεση σε γνωστή κακόβουλη IP
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

<a id="section-06"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
| ------ | --------- |
| **Velociraptor** | Αναζήτηση απειλών σε πολλές συσκευές |
| **OSQuery** | Αναζητήσεις SQL σε πληροφορίες συσκευών |
| **KAPE** | Συλλογή στοιχείων αρχικής αξιολόγησης |
| **Chainsaw** | Γρήγορη ανάλυση καταγραφών Windows |
| **DeepBlueCLI** | Αναζήτηση απειλών με PowerShell |

---

<a id="section-07"></a>

## 📋 Σύντομη λίστα ελέγχου αναζήτησης απειλών

```markdown
□ Έλεγχος αυτόματης εκτέλεσης: μητρώο, υπηρεσίες και εργασίες
□ Έλεγχος πρόσφατης δημιουργίας διεργασιών: συμβάν 4688 και Sysmon 1
□ Ανάλυση συνδέσεων δικτύου
□ Αναζήτηση κωδικοποιημένων εντολών
□ Έλεγχος πρόσβασης στη LSASS
□ Έλεγχος ασυνήθιστων σχέσεων γονικών και θυγατρικών διεργασιών
□ Έλεγχος καταγραφών PowerShell
□ Αναζήτηση ενδείξεων πλευρικής μετακίνησης
```

---

<a id="section-08"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Ανάλυση καταγραφών](Log-Analysis.el.md)
- [Ανίχνευση με SIEM](SIEM-Detection.el.md)
- [Κανόνες Sigma](Sigma-Rules.el.md)

---

**Προηγούμενο θέμα:** [← Ανίχνευση με SIEM](SIEM-Detection.el.md)

**Επόμενο θέμα:** [Ενίσχυση ασφάλειας →](Hardening.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
