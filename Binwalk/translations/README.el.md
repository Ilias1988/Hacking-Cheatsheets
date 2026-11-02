# 🔧 Binwalk — Οδηγός ανάλυσης firmware

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Εργαλείο ανάλυσης και εξαγωγής firmware
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## Πριν ξεκινήσετε

Το **Binwalk** αναζητά αναγνωρίσιμες υπογραφές μέσα σε δυαδικά αρχεία, όπως αποτυπώματα **firmware** (ενσωματωμένο λογισμικό συσκευής). Μπορεί να εντοπίσει πιθανές δομές, συμπιεσμένα τμήματα και συστήματα αρχείων και να χρησιμοποιήσει υποστηριζόμενους μηχανισμούς εξαγωγής.

**Βασικοί όροι:** *signature/magic bytes* = χαρακτηριστική ακολουθία bytes, *offset* = αρχική θέση μέσα στο αρχείο, *carving* = εξαγωγή τμήματος βάσει υπογραφής και ορίων, *extractor* = μηχανισμός εξαγωγής, *entropy* = μέτρο στατιστικής αβεβαιότητας. Η υψηλή εντροπία δεν αποδεικνύει κρυπτογράφηση.

Ο οδηγός συνδυάζει κυρίως επιλογές παλιότερου Binwalk με παραδείγματα που μπορεί να διαφέρουν σε νεότερες εκδόσεις. Ελέγξτε πρώτα ποια γενιά έχετε. Διατηρήστε την αρχική εικόνα και εξετάστε αντίγραφο σε ελεγχόμενο περιβάλλον, χωρίς να εκτελείτε άγνωστα εξαγόμενα αρχεία.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το Binwalk](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [💻 Βασική χρήση](#section-03)
- [🔍 Αναζήτηση υπογραφών αρχείων](#section-04)
- [📦 Εξαγωγή περιεχομένου](#section-05)
- [📊 Ανάλυση εντροπίας](#section-06)
- [💾 Ανάλυση συστήματος αρχείων](#section-07)
- [🎯 Ασκήσεις CTF](#section-08)
- [⚙️ Προχωρημένες επιλογές](#section-09)
- [📊 Γρήγορη αναφορά](#section-10)
- [📚 Πηγές](#section-11)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Binwalk

Το **Binwalk** αναλύει δυαδικά αρχεία και αποτυπώματα firmware, αναγνωρίζει υπογραφές και υποστηρίζει εξαγωγή ενσωματωμένων δεδομένων. Χρησιμοποιείται για:

- 🔧 **Ανάλυση firmware** — αναγνώριση ενσωματωμένων αρχείων και κώδικα
- 🔓 **Έλεγχοι IoT** — ανάλυση αποτυπωμάτων firmware router ή κάμερας
- 🎯 **Ασκήσεις CTF** — αναζήτηση κρυμμένων δεδομένων
- 🔬 **Ψηφιακή διερεύνηση** — ανάλυση δυαδικών αρχείων
- 🔍 **Αντίστροφη μηχανική** — κατανόηση δομών αρχείων

### Τι αναγνωρίζει το Binwalk

| Κατηγορία | Παραδείγματα |
|----------|----------|
| **Συμπιεσμένα δεδομένα** | gzip, bzip2, lzma, xz, zlib |
| **Συμπιεσμένα αρχεία** | tar, zip, rar, 7z, cpio |
| **Συστήματα αρχείων** | squashfs, cramfs, jffs2, ext, ubifs |
| **Εκτελέσιμα αρχεία** | ELF, PE, ARM, MIPS |
| **Εικόνες** | JPEG, PNG, GIF, BMP |
| **Κρυπτογραφία (Crypto)** | OpenSSL και πιστοποιητικά |
| **Φορτωτές εκκίνησης** | U-Boot, ARM headers |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

> **Διαφορετικές γενιές εργαλείου:** Το πρωτότυπο περιέχει εγκατάσταση με Python setup.py και επιλογές παλιότερου Binwalk. Το Binwalk v3 έχει ξαναγραφτεί σε Rust και αλλάζει εγκατάσταση, flags και μηχανισμούς εξαγωγής. Μην συνδυάζετε τυχαία οδηγίες διαφορετικών γενεών. Αφετηρία: [επίσημο Binwalk](https://github.com/ReFirmLabs/binwalk).


### Linux (Kali και Ubuntu)

```bash
# Εγκατάσταση Binwalk
sudo apt update
sudo apt install binwalk

# Ενδεικτικές εξαρτήσεις εξαγωγής της παλαιότερης γενιάς
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Εναλλακτικό παράδειγμα εγκατάστασης από πηγαίο κώδικα
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Εγκατάσταση sasquatch για συγκεκριμένες παραλλαγές SquashFS
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Επιβεβαίωση εγκατάστασης

```bash
binwalk --help
binwalk -h
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

### Βασική σύνταξη

```bash
# Ανάλυση αρχείου και εμφάνιση υπογραφών
binwalk firmware.bin

# Εξαγωγή αρχείων
binwalk -e firmware.bin

# Αναδρομική εξαγωγή
binwalk -eM firmware.bin
```

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
|--------|-------------|
| `-e, --extract` | Εξαγωγή αναγνωρισμένων τύπων αρχείων |
| `-M, --matryoshka` | Αναδρομική ανάλυση εξαγόμενων αρχείων |
| `-r, --rm` | Διαγραφή ενδιάμεσων εξαγόμενων αρχείων μετά την αποσυμπίεση |
| `-q, --quiet` | Περιορισμένη έξοδος |
| `-v, --verbose` | Αναλυτική έξοδος |
| `-d, --depth` | Βάθος αναδρομής, με τιμή 8 κατά το πρωτότυπο |
| `-C, --directory` | Φάκελος εξόδου |
| `-f, --log` | Αποθήκευση καταγραφής σε αρχείο |

---

<a id="section-04"></a>

## 🔍 Αναζήτηση υπογραφών αρχείων

> **Υπογραφή δεν σημαίνει πλήρες έγκυρο αρχείο:** Μικρές ακολουθίες bytes μπορεί να εμφανίζονται τυχαία ή σε διαφορετικό περιεχόμενο. Ελέγξτε όρια, μέγεθος και αν το εξαγόμενο αρχείο αναλύεται σωστά. Το σκέτο -R firmware.bin του πρωτοτύπου δεν είναι ισοδύναμο με γενική ανάλυση: το -R χρειάζεται τιμή αναζήτησης στην αντίστοιχη παλιά έκδοση.


### Βασική ανάλυση

```bash
# Αναζήτηση υπογραφών
binwalk firmware.bin

# Παράδειγμα εξόδου:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Συγκεκριμένοι τύποι υπογραφών

```bash
# Επιλογή ορισμένων τύπων υπογραφών
binwalk -A firmware.bin          # Υπογραφές εντολών μηχανής ή εκτελέσιμου κώδικα
binwalk -R firmware.bin          # Το -R αναζητά raw bytes και απαιτεί αντίστοιχη τιμή· το δείγμα είναι ελλιπές
binwalk -B firmware.bin          # Τυπικές υπογραφές, σύμφωνα με την παλιά έκδοση

# Αναζήτηση υπογραφών εντολών μηχανής
binwalk -A firmware.bin

# Αναζήτηση ακατέργαστης συμβολοσειράς ή bytes
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Φίλτρα συμπερίληψης και εξαίρεσης

```bash
# Συμπερίληψη συγκεκριμένων τύπων
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Εξαίρεση τύπων
binwalk --exclude="jpeg" firmware.bin

# Εμφάνιση συγκεκριμένων υπογραφών
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Προσαρμοσμένες υπογραφές

```bash
# Χρήση προσαρμοσμένου αρχείου υπογραφών
binwalk -m custom_signatures.txt firmware.bin

# Παράδειγμα μορφής αρχείου υπογραφών:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

<a id="section-05"></a>

## 📦 Εξαγωγή περιεχομένου

> **Εξαγωγή και όρια:** Δεν εξάγονται αυτόματα όλα όσα αναγνωρίζονται. Μπορεί να χρειάζονται συμβατοί εξωτερικοί extractors. Το dd με skip χωρίς count αντιγράφει μέχρι το τέλος του αρχείου και μπορεί να συμπεριλάβει επιπλέον τμήματα. Η αναδρομή μπορεί να παράγει μεγάλο όγκο δεδομένων· τα εξαγόμενα αρχεία παραμένουν μη έμπιστα.


### Βασική εξαγωγή

```bash
# Προσπάθεια εξαγωγής αναγνωρισμένων αρχείων
binwalk -e firmware.bin
# Ενδεικτικός φάκελος εξόδου της παλιάς έκδοσης: _firmware.bin.extracted/

# Εξαγωγή σε συγκεκριμένο φάκελο
binwalk -e -C /output/dir firmware.bin

# Εξαγωγή με αναδρομή (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Επιλογές εξαγωγής

```bash
# Διαγραφή ενδιάμεσων εξαγόμενων αρχείων
binwalk -er firmware.bin

# Περιορισμός βάθους αναδρομής
binwalk -eM --depth=3 firmware.bin

# Εξαγωγή με περιορισμένα μηνύματα
binwalk -eq firmware.bin

# Εξαγωγή με αναλυτικά μηνύματα
binwalk -ev firmware.bin
```

### Χειροκίνητη εξαγωγή με dd

```bash
# Όταν δεν υποστηρίζεται αυτόματη εξαγωγή
# Εύρεση αρχικής θέσης από την ανάλυση Binwalk
binwalk firmware.bin
# Παράδειγμα: SquashFS στη θέση 1572892

# Εξαγωγή με dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Εναλλακτικά, με όριο μήκους όταν είναι γνωστό
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Εξαγωγή συγκεκριμένων τύπων βάσει υπογραφών

```bash
# Εξαγωγή συγκεκριμένων τύπων αρχείων
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Πολλοί τύποι
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Μορφή: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

<a id="section-06"></a>

## 📊 Ανάλυση εντροπίας

> **Στατιστική ένδειξη:** Τα εύρη του πρωτοτύπου είναι ενδεικτικά, όχι καθολικός ταξινομητής. Εντροπία επηρεάζεται από μέγεθος παραθύρου, κανονικοποίηση και περιεχόμενο. Συμπίεση και κρυπτογράφηση μπορούν να δίνουν παρόμοιες υψηλές τιμές· η ακριβής διάγνωση χρειάζεται πρόσθετα στοιχεία.


### Τι είναι η εντροπία;

Η εντροπία μετρά τη στατιστική αβεβαιότητα των δεδομένων:
- **Υψηλή εντροπία (0.9–1.0)** — ενδέχεται να σχετίζεται με συμπιεσμένα ή κρυπτογραφημένα δεδομένα
- **Μεσαία εντροπία (0.5–0.8)** — ενδεικτικό μοτίβο, όχι διάγνωση εκτελέσιμου κώδικα
- **Χαμηλή εντροπία (0.0–0.4)** — ενδέχεται να αφορά επαναλαμβανόμενα δεδομένα ή κείμενο

### Παραγωγή γραφήματος εντροπίας

```bash
# Υπολογισμός εντροπίας
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Αποθήκευση γραφήματος εντροπίας
binwalk -E --save firmware.bin
# Ενδεικτικό όνομα εξόδου: firmware.bin.png

# Με υπόμνημα
binwalk -EJ firmware.bin
```

### Επιλογές εντροπίας

```bash
# Ορισμός μεγέθους τμήματος ανάλυσης
binwalk -E --block=1024 firmware.bin

# Εμφάνιση τιμών χωρίς γράφημα
binwalk -E --nplot firmware.bin

# Αναζήτηση απότομων μεταβολών εντροπίας
binwalk -E --edge firmware.bin
```

### Ερμηνεία εντροπίας

```
Υψηλή σχετικά σταθερή τιμή (~1.0) → Πιθανή κρυπτογράφηση ή συμπίεση, χωρίς οριστική διάγνωση
Μεταβαλλόμενο μοτίβο (~0.7) → Πιθανή συμπίεση ή μικτά δεδομένα
Κυματοειδές μοτίβο (~0.5) → Διάφορα είδη κώδικα ή δεδομένων
Χαμηλή σταθερή τιμή (~0.2) → Πιθανώς επαναλαμβανόμενα δεδομένα ή κείμενο
Απότομη πτώση στο 0 → Πιθανή επανάληψη ίδιου byte, π.χ. μηδενική συμπλήρωση
```

---

<a id="section-07"></a>

## 💾 Ανάλυση συστήματος αρχείων

### Συνήθη συστήματα αρχείων firmware

| Σύστημα αρχείων | Περιγραφή |
|-------------|-------------|
| **SquashFS** | Συμπιεσμένο σύστημα μόνο για ανάγνωση, συνηθισμένο σε firmware |
| **JFFS2** | Σύστημα αρχείων flash με καταγραφή αλλαγών |
| **UBIFS** | Σύστημα αρχείων για μεγαλύτερες μνήμες flash |
| **CramFS** | Συμπιεσμένο σύστημα αρχείων ROM |
| **YAFFS** | Σύστημα αρχείων flash YAFFS |
| **ext2/3/4** | Οικογένεια συστημάτων αρχείων Linux |

### Εξαγωγή SquashFS

```bash
# Εξαγωγή με Binwalk
binwalk -e firmware.bin

# Χειροκίνητη εξαγωγή με unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Προσάρτηση όπου υποστηρίζεται
sudo mount -t squashfs squashfs.bin /mnt
```

### Εξαγωγή JFFS2

```bash
# Χρήση jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Εναλλακτικά, μέσω Binwalk
binwalk -e firmware.bin
```

### Εξαγωγή UBIFS

```bash
# Χρήση ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Ανάλυση εξαγόμενων αρχείων

```bash
# Εξέταση δεδομένων μετά την εξαγωγή
cd _firmware.bin.extracted/

# Αναζήτηση αρχείων ενδιαφέροντος
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Αναζήτηση ενδείξεων κωδικών ή μυστικών
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Αναζήτηση εκτελέσιμων
find . -type f -executable
file */bin/*
```

---

<a id="section-08"></a>

## 🎯 Ασκήσεις CTF

### Συνήθη σενάρια CTF

#### Κρυμμένο αρχείο μέσα σε εικόνα

```bash
# Ανάλυση εικόνας για ενσωματωμένα δεδομένα
binwalk image.png
binwalk image.jpg

# Εξαγωγή αναγνωρισμένων ενσωματωμένων αρχείων
binwalk -e image.png

# Παράλληλη εξέταση συμβολοσειρών
strings image.png | head -50
```

#### Αρχεία με πολλά επίπεδα συμπίεσης

```bash
# Αναδρομική εξαγωγή (matryoshka)
binwalk -eM challenge.bin

# Πολλά επίπεδα συμπίεσης
binwalk -eM --depth=10 nested.bin
```

#### Αναζήτηση κρυμμένου κειμένου

```bash
# Συνδυασμός με strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Τμήματα με πιθανή κρυπτογράφηση

```bash
# Η ανάλυση εντροπίας δίνει ενδείξεις· δεν αποδεικνύει κρυπτογράφηση
binwalk -E challenge.bin

# Υψηλή εντροπία μπορεί να αφορά κρυπτογράφηση, συμπίεση ή άλλα δεδομένα
# Εξέταση στοιχείων της άσκησης για κωδικό ή κλειδί
```

### Ροή εργασίας CTF

```bash
# 1. Βασική ανάλυση
binwalk challenge.bin

# 2. Εξέταση εντροπίας
binwalk -E challenge.bin

# 3. Προσπάθεια αναδρομικής εξαγωγής
binwalk -eM challenge.bin

# 4. Αναζήτηση στα εξαγόμενα αρχεία
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Εξέταση ενδείξεων ενσωματωμένων δεδομένων
binwalk -A challenge.bin    # Υπογραφές εντολών μηχανής
xxd challenge.bin | head    # Δεκαεξαδική προβολή
```

### Αναζήτηση ενδείξεων στεγανογραφίας

```bash
# Αναζήτηση υπογραφών μέσα σε εικόνες
binwalk -B suspicious.jpg

# Έλεγχος ενδείξεων πρόσθετων δεδομένων
binwalk suspicious.png

# Εξαγωγή όπου αναγνωρίζεται υποστηριζόμενος τύπος
binwalk -e suspicious.jpg

# Σύγκριση μεγέθους με το αναμενόμενο
ls -la suspicious.jpg
```

---

<a id="section-09"></a>

## ⚙️ Προχωρημένες επιλογές

> **Έλεγχος επιλογών ανά έκδοση:** Τα -W/--hexdump, --diff, --threads, --disable-plugin, --csv, --json και επιλογές γραφήματος δεν είναι εγγυημένα διαθέσιμα μαζί ή με την ίδια σύνταξη. Χρησιμοποιήστε τη βοήθεια της πραγματικής έκδοσης. Τα firmware.bin και οι αριθμητικές θέσεις είναι ενδεικτικά.


### Δεκαεξαδική προβολή

```bash
# Δεκαεξαδική προβολή σε συγκεκριμένες θέσεις
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Δεκαεξαδική προβολή από συγκεκριμένη θέση
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Επιλογές αρχικής θέσης και μήκους

```bash
# Έναρξη από συγκεκριμένη θέση
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Ανάλυση μόνο συγκεκριμένου μήκους
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Επιλογές απόδοσης

```bash
# Παράδειγμα παραλληλισμού· ελέγξτε επιλογές έκδοσης
binwalk --threads=4 firmware.bin

# Παράδειγμα απενεργοποίησης plugin· ελέγξτε αν υποστηρίζεται
binwalk --disable-plugin=lzma firmware.bin
```

### Μορφές αποτελεσμάτων

```bash
# Έξοδος CSV
binwalk --csv firmware.bin > results.csv

# Έξοδος JSON
binwalk --json firmware.bin > results.json

# Αποθήκευση καταγραφής σε αρχείο
binwalk -f scan.log firmware.bin
```

### Σύγκριση δύο αρχείων

```bash
# Σύγκριση δύο εκδόσεων firmware
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Δείχνει διαφορές σε δεκαεξαδική μορφή, όπου υποστηρίζεται
```

---

<a id="section-10"></a>

## 📊 Γρήγορη αναφορά

### Συχνά χρησιμοποιούμενες εντολές

| Σκοπός | Εντολή |
|---------|---------|
| Ανάλυση αρχείου | `binwalk firmware.bin` |
| Εξαγωγή αναγνωρισμένων δεδομένων | `binwalk -e firmware.bin` |
| Αναδρομική εξαγωγή | `binwalk -eM firmware.bin` |
| Ανάλυση εντροπίας | `binwalk -E firmware.bin` |
| Αναζήτηση υπογραφών εντολών μηχανής | `binwalk -A firmware.bin` |
| Αναζήτηση συμβολοσειράς | `binwalk -R "pattern" file` |
| Εξαγωγή σε φάκελο | `binwalk -e -C outdir/ file` |
| Εξαγωγή και διαγραφή ενδιάμεσων αρχείων | `binwalk -er firmware.bin` |

### Συνήθεις υπογραφές αρχείων

| Υπογραφή | Περιγραφή |
|-----------|-------------|
| `\x1f\x8b\x08` | Συμπίεση gzip |
| `BZh` | Συμπίεση bzip2 |
| `\xfd7zXZ` | Συμπίεση xz |
| `hsqs` | SquashFS με σειρά bytes little endian |
| `sqsh` | SquashFS με σειρά bytes big endian |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | Εικόνα PNG |
| `\xff\xd8\xff` | Εικόνα JPEG |
| `PK\x03\x04` | Συμπιεσμένο αρχείο ZIP |

### Ροή εργασίας εξαγωγής

```
1. binwalk firmware.bin       → Αναγνώριση υπογραφών περιεχομένου
2. binwalk -E firmware.bin    → Εξέταση εντροπίας για πιθανή συμπίεση ή κρυπτογράφηση
3. binwalk -e firmware.bin    → Εξαγωγή
4. cd _firmware.bin.extracted → Εξερεύνηση εξαγόμενων δεδομένων
5. find . -name "*" -type f   → Καταγραφή αρχείων
6. grep -r "password" .       → Αναζήτηση ενδείξεων μυστικών
```

### Χειροκίνητη εξαγωγή

```bash
# Εύρεση θέσης από το Binwalk
binwalk firmware.bin
# SquashFS στη θέση 0x180000, δηλαδή 1572864 δεκαδικά

# Εξαγωγή με dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Εξαγωγή SquashFS
unsquashfs fs.squashfs
```

### Χρήσιμες θέσεις αρχείων firmware

| Διαδρομή | Περιεχόμενο |
|------|----------|
| `/etc/passwd` | Λογαριασμοί χρηστών |
| `/etc/shadow` | Αποτυπώματα κωδικών πρόσβασης |
| `/etc/config/` | Ρυθμίσεις συσκευής |
| `/etc/init.d/` | Scripts εκκίνησης |
| `/usr/bin/` | Εκτελέσιμα χρηστών |
| `/www/` | Διεπαφή web |
| `/etc/ssl/` | Πιστοποιητικά SSL/TLS |

---

<a id="section-11"></a>

## 📚 Πηγές

- [Κώδικας Binwalk στο GitHub](https://github.com/ReFirmLabs/binwalk)
- [Wiki του Binwalk](https://github.com/ReFirmLabs/binwalk/wiki)
- [Εισαγωγικό υλικό ανάλυσης firmware](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [Πόροι OWASP για ασφάλεια IoT](https://owasp.org/www-project-iot-security/)

### Σχετικοί οδηγοί
- [ExifTool](../../ExifTool/translations/README.el.md)
- [Volatility](../../Volatility/translations/README.el.md)
- [Autopsy](../../Autopsy/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)

---

<p align="center">
  <b>🔧 Εξασκηθείτε στην ανάλυση firmware!</b><br>
  <i>Binwalk — αναγνωρίστε και εξετάστε ενσωματωμένα δεδομένα</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
