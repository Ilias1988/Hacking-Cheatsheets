# 🧠 Volatility — Οδηγός ψηφιακής εξέτασης μνήμης

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Πλαίσιο ψηφιακής εξέτασης μνήμης
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## Πριν ξεκινήσετε

Το **Volatility** εξετάζει ένα αποθηκευμένο **memory dump**, δηλαδή αποτύπωμα RAM. Αναζητά δομές λειτουργικού συστήματος, διεργασίες, συνδέσεις, αρχεία και άλλα στοιχεία που ήταν διαθέσιμα κατά τη λήψη. Δεν καταγράφει τη μνήμη από μόνο του και δεν μπορεί να ανακτήσει δεδομένα που απουσιάζουν από το αποτύπωμα.

**Βασικοί όροι:** *plugin* = πρόσθετη λειτουργία ανάλυσης, *PID* = αναγνωριστικό διεργασίας, *offset* = θέση μέσα σε δεδομένα ή χώρο διευθύνσεων, *profile* = περιγραφή δομών λειτουργικού στο Volatility 2, *symbol table* = πίνακας συμβόλων που βοηθά στην ερμηνεία δομών στο Volatility 3, *artifact* = στοιχείο προς διερεύνηση.

Ξεχωρίστε τις γενιές 2 και 3 πριν επιλέξετε εντολές. Κρατήστε το αρχικό αποτύπωμα, αποτύπωμα κατακερματισμού (hash) και στοιχεία προέλευσης· εργαστείτε σε αντίγραφο. Τα παραδείγματα διατηρούνται από το πρωτότυπο και δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το Volatility](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [💻 Βασική χρήση](#section-03)
- [🔍 Αναγνώριση αποτυπώματος μνήμης](#section-04)
- [⚙️ Ανάλυση διεργασιών](#section-05)
- [🌐 Ανάλυση δικτύου](#section-06)
- [📝 Ανάλυση μητρώου Windows](#section-07)
- [🦠 Ανάλυση κακόβουλου λογισμικού](#section-08)
- [🔐 Ανάλυση χρηστών και στοιχείων πρόσβασης](#section-09)
- [📁 Ανάλυση αρχείων και βιβλιοθηκών DLL](#section-10)
- [📅 Ανάλυση χρονογραμμής](#section-11)
- [🔄 Volatility 3](#section-12)
- [📊 Γρήγορη αναφορά](#section-13)
- [📚 Πηγές](#section-14)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Volatility

Το **Volatility** είναι πλαίσιο ανοικτού κώδικα για ψηφιακή εξέταση αποτυπωμάτων RAM. Χρησιμοποιείται για:

- 🔍 **Αντιμετώπιση περιστατικών** — αναζήτηση ενδείξεων κακόβουλης δραστηριότητας
- 🦠 **Ανάλυση κακόβουλου λογισμικού** — διερεύνηση διεργασιών και κώδικα
- 🔐 **Ανάκτηση στοιχείων πρόσβασης** — εξέταση διαθέσιμων διαπιστευτηρίων ή αποτυπωμάτων
- 📊 **Ανάλυση χρονογραμμής** — ανασύνθεση συμβάντων
- 🕵️ **Διερεύνηση απειλών** — αναζήτηση ενδείξεων παραβίασης

### Μορφές αποτυπωμάτων μνήμης του πρωτοτύπου

> **Η κατάληξη δεν αρκεί:** Υποστήριξη container, λειτουργικού και συγκεκριμένων δομών διαφέρει ανά έκδοση. Ένα αρχείο .E01 ή snapshot δεν είναι κατ’ ανάγκη αποτύπωμα RAM. Χρειάζεται γνωστή διαδικασία απόκτησης και επαρκές περιεχόμενο για την επιλεγμένη ανάλυση.


| Μορφή | Περιγραφή |
|--------|-------------|
| Ακατέργαστο αποτύπωμα (.raw, .mem) | Ακατέργαστο αποτύπωμα μνήμης |
| EWF (.E01) | Μορφή EnCase |
| AFF | Μορφή Advanced Forensics Format |
| Αποτύπωμα κατάρρευσης συστήματος | Αποτυπώματα κατάρρευσης Windows |
| VMware (.vmem) | Στιγμιότυπα VMware |
| VirtualBox (.sav) | Στιγμιότυπα VirtualBox |
| HPAK (.hpak) | Μορφή FDPro |
| LiME (.lime) | Linux Memory Extractor |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Δύο διαφορετικές γενιές:** Το Volatility 2 βασίζεται στην παλιά οικογένεια Python 2 και τα προφίλ του δεν χρησιμοποιούνται με τον ίδιο τρόπο στο Volatility 3. Μην αναμειγνύετε εξαρτήσεις, flags και ονόματα plugins. Η εντολή python του συστήματός σας μπορεί να σημαίνει Python 3. Δείτε το [επίσημο Volatility 3](https://github.com/volatilityfoundation/volatility3).


### Volatility 2 — Παλαιότερη γενιά

```bash
# Λήψη αποθετηρίου
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Εγκατάσταση εξαρτήσεων
pip install pycrypto distorm3 yara-python pillow openpyxl

# Εκτέλεση
python vol.py -h
```

### Volatility 3 — Νεότερη γενιά

```bash
# Εγκατάσταση μέσω pip
pip3 install volatility3

# Εναλλακτικά, λήψη αποθετηρίου
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Εκτέλεση
python3 vol.py -h
vol -h  # Εφόσον η εγκατάσταση μέσω pip παρέχει το αντίστοιχο εκτελέσιμο
```

### Λήψη πινάκων συμβόλων για Volatility 3

> **Σύμβολα ανά σύστημα:** Τα έτοιμα ZIP δεν εγγυώνται κατάλληλα σύμβολα για κάθε kernel, ιδίως Linux/macOS. Η επίσημη οδηγία προβλέπει τοποθέτηση των συγκεκριμένων ZIP αυτούσιων, με τα αρχικά ονόματα, στον φάκελο symbols· δεν απαιτεί την αποσυμπίεση που αναφέρει το πρωτότυπο. Χρειάζονται σύμβολα που αντιστοιχούν στο εξεταζόμενο σύστημα. Η αυτόματη αναγνώριση δεν καταργεί αυτές τις προϋποθέσεις.


```bash
# Σύμβολα Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Σύμβολα Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Σύμβολα macOS
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Τοποθέτηση των επίσημων ZIP στον volatility3/symbols/ σύμφωνα με την τεκμηρίωση, χωρίς υποχρεωτική αποσυμπίεση
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

### Σύνταξη Volatility 2

```bash
# Γενική σύνταξη
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Παράδειγμα
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Σύνταξη Volatility 3

```bash
# Γενική σύνταξη· το Volatility 3 χρησιμοποιεί layers και σύμβολα αντί προφίλ Volatility 2
vol -f <memory_dump> <plugin>

# Παράδειγμα
vol -f memory.raw windows.pslist
```

### Συνηθισμένες επιλογές

```bash
# Volatility 2
-f FILE          # Αρχείο αποτυπώματος μνήμης
--profile=PROFILE # Προφίλ λειτουργικού συστήματος
-h               # Βοήθεια
--info           # Κατάλογος διαθέσιμων προσθέτων

# Volatility 3
-f FILE          # Αρχείο αποτυπώματος μνήμης
-o DIR           # Φάκελος εξόδου
-r RENDERER      # Μορφή εξόδου (pretty, json, csv)
-p PLUGIN_DIR    # Πρόσθετα από επιπλέον φάκελο
-h               # Βοήθεια
```

---

<a id="section-04"></a>

## 🔍 Αναγνώριση αποτυπώματος μνήμης

### Αναγνώριση προφίλ στο Volatility 2

```bash
# Εύρεση προτεινόμενων προφίλ
python vol.py -f memory.raw imageinfo

# Αναλυτικότερες πληροφορίες
python vol.py -f memory.raw kdbgscan
```

### Πληροφορίες λειτουργικού στο Volatility 3

> **Ονόματα plugins:** Τα windows.info, linux.info ή mac.info του πρωτοτύπου δεν πρέπει να θεωρούνται διαθέσιμα σε κάθε έκδοση. Ελέγξτε τη λίστα plugins της εγκατάστασης και τις ειδικές απαιτήσεις του λειτουργικού. Το όνομα αρχείου memory.raw δεν προσδιορίζει από μόνο του το λειτουργικό.


```bash
# Το Volatility 3 δεν χρησιμοποιεί το χειροκίνητο προφίλ του Volatility 2· απαιτεί κατάλληλα σύμβολα
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Ενδεικτικά προφίλ Volatility 2

| Περιβάλλον | Προφίλ |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="section-05"></a>

## ⚙️ Ανάλυση διεργασιών

### Καταγραφή διεργασιών

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Δενδροειδής προβολή
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Σάρωση δομών διεργασιών· περιλαμβάνει και υπολείμματα

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Στοιχεία διεργασιών

```bash
# Ορίσματα γραμμής εντολών
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Μεταβλητές περιβάλλοντος
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Handles της διεργασίας, δηλαδή αναφορές σε αντικείμενα συστήματος
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# Φορτωμένες βιβλιοθήκες DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Αναζήτηση ενδείξεων κρυμμένων διεργασιών

> **Διαφορές χρειάζονται ερμηνεία:** Η σάρωση μνήμης μπορεί να εντοπίσει και υπολείμματα τερματισμένων διεργασιών. Διαφορά από pslist ή αποτέλεσμα malfind δεν επιβεβαιώνει αυτόματα malware. Το hollowfind μπορεί να απαιτεί ξεχωριστό πρόσθετο.


```bash
# Σύγκριση pslist και psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Αναζήτηση ενδείξεων αντικατάστασης κώδικα διεργασίας (hollowing)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Εξαγωγή δεδομένων διεργασίας

```bash
# Εξαγωγή εκτελέσιμου
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Εξαγωγή μνήμης διεργασίας
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

<a id="section-06"></a>

## 🌐 Ανάλυση δικτύου

### Συνδέσεις δικτύου

```bash
# Ενδείξεις δικτυακών συνδέσεων στο Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Από Windows Vista και μετά, όπου υποστηρίζεται
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Πεδία αποτελεσμάτων

| Πεδίο | Περιγραφή |
|-------|-------------|
| Offset — θέση μέσα στα δεδομένα | Θέση μέσα στη μνήμη |
| Proto | TCP/UDP |
| LocalAddr — τοπική διεύθυνση | Τοπική IP και θύρα |
| ForeignAddr — απομακρυσμένη διεύθυνση | Απομακρυσμένη IP και θύρα |
| Κατάσταση | Κατάσταση σύνδεσης |
| PID | Αναγνωριστικό διεργασίας |
| Υπεύθυνος | Όνομα διεργασίας |

### Ευρήματα δικτυακής δραστηριότητας

```bash
# Δικτυακά sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

<a id="section-07"></a>

## 📝 Ανάλυση μητρώου Windows

### Καταγραφή δομών hive του μητρώου

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Εξαγωγή μητρώου

```bash
# Εμφάνιση συγκεκριμένου κλειδιού μητρώου
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Εξαγωγή δομής hive σε αρχείο
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Σημαντικά κλειδιά μητρώου

```bash
# Κλειδιά αυτόματης εκκίνησης
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Υπηρεσίες
printkey -K "SYSTEM\CurrentControlSet\Services"

# Λογαριασμοί χρηστών
printkey -K "SAM\Domains\Account\Users"

# Πρόσφατα αρχεία
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### UserAssist — ενδείξεις εκτέλεσης προγραμμάτων

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

<a id="section-08"></a>

## 🦠 Ανάλυση κακόβουλου λογισμικού

### Malfind — ενδείξεις εισαγωγής κώδικα

```bash
# Αναζήτηση ενδείξεων εισαγωγής κώδικα
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Εξαγωγή σε αρχεία
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### Παρεμβολές στον πίνακα SSDT

```bash
# System Service Descriptor Table — πίνακας υπηρεσιών συστήματος
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Σύγκριση με κατάλληλο καθαρό σύστημα αναφοράς
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Ανάλυση πινάκων IDT/GDT

```bash
# Interrupt Descriptor Table — πίνακας περιγραφέων διακοπών
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Global Descriptor Table — καθολικός πίνακας περιγραφέων
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Ανάλυση οδηγών συσκευών

```bash
# Καταγραφή οδηγών συσκευών
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Κατάλογος φορτωμένων modules
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Modules που αποφορτώθηκαν
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Παρεμβολές σε χειριστές IRP οδηγών συσκευών
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Έλεγχος με κανόνες YARA

> **Έλεγχος flags Volatility 2:** Το -y αφορά κανόνα ή έκφραση αναζήτησης, ενώ το -Y αρχείο κανόνων στην αντίστοιχη έκδοση· οι τιμές του πρωτοτύπου χρειάζονται επιβεβαίωση. Μια απλή συμβολοσειρά δεν αποτελεί πάντα έγκυρο πλήρες αρχείο YARA. Στο Volatility 3 ελέγξτε το διαθέσιμο όνομα plugin και τις επιλογές του.


```bash
# Έλεγχος με κανόνες YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### Παρεμβολές σε λειτουργίες API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

<a id="section-09"></a>

## 🔐 Ανάλυση χρηστών και στοιχείων πρόσβασης

### Πληροφορίες χρηστών

```bash
# Εύρεση αναγνωριστικών ασφάλειας SID χρηστών
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Ενδείξεις τελευταίου συνδεδεμένου χρήστη
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Αποτυπώματα κωδικών πρόσβασης

```bash
# Εξαγωγή αποτυπωμάτων κωδικών από το SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Μυστικά LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Προσωρινά αποθηκευμένα στοιχεία πρόσβασης
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Παράδειγμα ενσωμάτωσης Mimikatz

> **Δεν είναι ταυτόσημες λειτουργίες:** Το πρόσθετο mimikatz του Volatility 2 δεν είναι κατ’ ανάγκη προεγκατεστημένο. Το lsadump του Volatility 3 δεν αποτελεί πλήρη ενσωμάτωση όλων των λειτουργιών Mimikatz. Τα διαθέσιμα στοιχεία εξαρτώνται από έκδοση Windows, ρυθμίσεις και πληρότητα αποτυπώματος.


```bash
# Εξαγωγή στοιχείων πρόσβασης μέσω πρόσθετου mimikatz
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Παράδειγμα πρόσθετου lsadump του Volatility 3· δεν ταυτίζεται με το Mimikatz
vol -f memory.raw windows.lsadump
```

### Πρόχειρο συστήματος

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

<a id="section-10"></a>

## 📁 Ανάλυση αρχείων και βιβλιοθηκών DLL

### Αναζήτηση δομών αρχείων

```bash
# Αναζήτηση δομών αρχείων στη μνήμη
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Φιλτράρισμα συγκεκριμένων αρχείων
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Εξαγωγή αρχείων

> **Διευθύνσεις και απαιτούμενα ορίσματα:** Φυσικό offset, εικονική διεύθυνση και offset συγκεκριμένης δομής δεν είναι εναλλάξιμα. Οι εντολές εξαγωγής χρειάζονται τα ορίσματα που ζητά το αντίστοιχο plugin. Τα αποσπασματικά printkey παραπάνω δεν είναι πλήρεις αυτόνομες εντολές.


```bash
# Εξαγωγή αρχείου βάσει θέσης μνήμης
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Παράδειγμα εξαγωγής· απαιτούνται σωστά ορίσματα επιλογής αρχείων
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### Ανάλυση βιβλιοθηκών DLL

```bash
# Καταγραφή βιβλιοθηκών DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Συγκεκριμένη διεργασία
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Εξαγωγή βιβλιοθήκης DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Συμβολοσειρές μνήμης

> **Απαιτείται αρχείο εισόδου:** Το strings plugin του Volatility 2 συσχετίζει συμβολοσειρές και θέσεις από ήδη παραγόμενο αρχείο με χώρους διεργασιών· δεν αντικαθιστά γενικά την αρχική εξαγωγή strings. Το strings.txt πρέπει να έχει την απαιτούμενη μορφή.


```bash
# Αντιστοίχιση ήδη εξαγμένων συμβολοσειρών σε διεργασίες
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Αντιστοίχιση συμβολοσειρών συγκεκριμένης διεργασίας
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

<a id="section-11"></a>

## 📅 Ανάλυση χρονογραμμής

### Δημιουργία χρονογραμμής

```bash
# Δημιουργία χρονογραμμής
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### Ανάλυση Master File Table (MFT)

```bash
# Master File Table — βασικός πίνακας αρχείων NTFS
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

<a id="section-12"></a>

## 🔄 Volatility 3

> **Συνοπτική σύγκριση:** Η ταχύτητα δεν είναι σταθερή ιδιότητα κάθε εργασίας. Το Volatility 2 επίσης διέθετε διαφορετικές μορφές εξόδου ανά λειτουργία. Ορισμένα plugins έχουν μετακινηθεί σε namespaces όπως windows.malware ή windows.registry· ελέγξτε τη βοήθεια της έκδοσης αντί να θεωρείτε όλα τα παρακάτω ονόματα έγκυρα. Δείτε ενδεικτικά το [τρέχον malfind](https://volatility3.readthedocs.io/en/latest/volatility3.plugins.windows.malware.malfind.html).


### Βασικές διαφορές

| Χαρακτηριστικό | Vol2 | Vol3 |
|---------|------|------|
| Προφίλ | Χειροκίνητα (`--profile=`) | Αναγνώριση μέσω διαθέσιμων δεδομένων και συμβόλων |
| Ονομασία προσθέτων | `pslist` | `windows.pslist` |
| Απόδοση | Πιο αργό, σύμφωνα με το πρωτότυπο | Πιο γρήγορο, σύμφωνα με το πρωτότυπο |
| Python | Python 2.7 | Python 3 |
| Αποθήκευση αποτελεσμάτων | Πολλαπλές μορφές ανά plugin· όχι μόνο κείμενο | JSON, CSV και άλλες μορφές |

### Αναφορά προσθέτων Volatility 3

```bash
# Διεργασίες
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Δίκτυο
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Μητρώο Windows
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Ενδείξεις κακόβουλου κώδικα
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Στοιχεία πρόσβασης
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Αρχεία
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Πρόσθετα Linux για Volatility 3

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

<a id="section-13"></a>

## 📊 Γρήγορη αναφορά

### Συχνά χρησιμοποιούμενες εντολές

| Σκοπός | Εντολή Volatility 2 | Εντολή Volatility 3 |
|---------|--------------|--------------|
| Αναγνώριση αποτυπώματος | `imageinfo` | `windows.info` |
| Καταγραφή διεργασιών | `pslist` | `windows.pslist` |
| Δέντρο διεργασιών | `pstree` | `windows.pstree` |
| Αναζήτηση διεργασιών με σάρωση μνήμης | `psscan` | `windows.psscan` |
| Δίκτυο | `netscan` | `windows.netscan` |
| Βιβλιοθήκες DLL | `dlllist` | `windows.dlllist` |
| Εξαγωγή διεργασίας | `procdump` | `windows.pslist --dump` |
| Αναζήτηση ενδείξεων κακόβουλου κώδικα | `malfind` | `windows.malfind` |
| Μητρώο Windows (Registry) | `hivelist` | `windows.registry.hivelist` |
| Αποτυπώματα | `hashdump` | `windows.hashdump` |
| Αρχεία | `filescan` | `windows.filescan` |
| Strings — συμβολοσειρές | `strings` | - |

### Ροή εργασίας διερεύνησης

```
1. imageinfo/windows.info    → Πληροφορίες λειτουργικού συστήματος
2. pslist/pstree/psscan      → Καταγραφή διεργασιών
3. cmdline                    → Εξέταση γραμμών εντολών
4. netscan                    → Ενδείξεις δικτυακών συνδέσεων
5. malfind                    → Αναζήτηση ενδείξεων εισαγωγής κώδικα
6. dlllist                    → Εξέταση φορτωμένων DLL
7. filescan                   → Αναζήτηση αρχείων ενδιαφέροντος
8. hashdump                   → Εξαγωγή διαθέσιμων στοιχείων πρόσβασης
9. timeliner                  → Δημιουργία χρονογραμμής
```

---

<a id="section-14"></a>

## 📚 Πηγές

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatility Wiki](https://github.com/volatilityfoundation/volatility/wiki)
- [Οδηγός ψηφιακής εξέτασης μνήμης](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Σχετικοί οδηγοί
- [Mimikatz](../../Mimikatz/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)
- [Κλιμάκωση προνομίων Windows](../../Windows-PrivEsc/translations/README.el.md)

---

<p align="center">
  <b>🧠 Εξασκηθείτε στην ανάλυση μνήμης!</b><br>
  <i>Συσχετίστε τα δεδομένα μνήμης με την υπόλοιπη έρευνα</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
