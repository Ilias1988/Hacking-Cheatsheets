# 🔬 CTF — Ψηφιακή διερεύνηση αρχείων και δεδομένων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Forensics-CTF.md) • [🇹🇷 Türkçe](Forensics-CTF.tr.md)

[🏁 Ελληνική επισκόπηση CTF](README.el.md)

---

## Πριν ξεκινήσετε

Το **Forensics** εδώ σημαίνει ψηφιακή διερεύνηση δεδομένων της άσκησης. Η **στεγανογραφία** κρύβει δεδομένα μέσα σε άλλο περιεχόμενο, τα **μεταδεδομένα** περιγράφουν ιδιότητες ενός αρχείου και το **file carving** εντοπίζει ή εξάγει ενσωματωμένα αρχεία από τη δομή τους.

Ένα **memory dump** είναι αποτύπωμα μνήμης, ένα **disk image** αντίγραφο δεδομένων δίσκου και ένα **PCAP** αρχείο καταγραφής δικτυακών πακέτων. Το **LSB** είναι το λιγότερο σημαντικό bit, που εξετάζεται σε ορισμένα σενάρια στεγανογραφίας. Το **spectrogram** δείχνει τη μεταβολή συχνοτήτων στον χρόνο και διαφέρει από ένα διάγραμμα μέσου φάσματος.

Κρατήστε τα αρχικά αρχεία και εργαστείτε σε αντίγραφα. Προσαρμόστε τα εργαλεία στον πραγματικό τύπο δεδομένων, όχι μόνο στην επέκταση του αρχείου. Οι εντολές και οι ενδεικτικές διαδρομές διατηρούνται όπως στο πρωτότυπο. Ένα αποτέλεσμα εξαγωγής ή μια συμβολοσειρά που μοιάζει με flag χρειάζεται επιβεβαίωση με την εκφώνηση.

## Περιεχόμενα

- [📁 Ανάλυση αρχείων](#section-01)
- [🖼️ Στεγανογραφία](#section-02)
- [🧠 Ψηφιακή διερεύνηση μνήμης](#section-03)
- [💽 Ψηφιακή διερεύνηση δίσκου](#section-04)
- [📊 Διερεύνηση δικτυακών καταγραφών (PCAP)](#section-05)
- [📋 Λίστα ελέγχου Forensics CTF](#section-06)

<a id="section-01"></a>

## 📁 Ανάλυση αρχείων

```bash
# Αναγνώριση τύπου αρχείου
file unknown_file

# Προβολή περιεχομένου σε δεκαεξαδική μορφή
xxd file | head
hexdump -C file | head

# Εξαγωγή αναγνώσιμων συμβολοσειρών
strings -n 6 file
strings -e l file    # Συμβολοσειρές 16 bit σε διάταξη little-endian

# Εντοπισμός ή εξαγωγή ενσωματωμένων αρχείων
binwalk file
binwalk -e file      # Εξαγωγή
foremost file
```

---

<a id="section-02"></a>

## 🖼️ Στεγανογραφία

### Εικόνες

Το stegsolve.jar είναι όνομα αρχείου εφαρμογής, όχι πλήρης καθολική εντολή εκκίνησης. Τα εργαλεία στεγανογραφίας υποστηρίζουν συγκεκριμένους τύπους αρχείων και τεχνικές· απουσία ευρήματος από ένα εργαλείο δεν αποκλείει κρυμμένο περιεχόμενο.


```bash
# Μεταδεδομένα
exiftool image.jpg
identify -verbose image.png

# Steghide για το παράδειγμα JPEG
steghide extract -sf image.jpg

# Zsteg για το παράδειγμα PNG
zsteg image.png
zsteg -a image.png   # Διευρυμένοι έλεγχοι του εργαλείου

# Ανάλυση λιγότερο σημαντικών bits (LSB)
stegsolve.jar        # Όνομα αρχείου εργαλείου με γραφικό περιβάλλον — δείτε τη σημείωση

# Αναζήτηση συμβολοσειρών μέσα στο αρχείο εικόνας
strings image.png | grep -i flag
```

### Ήχος

Η διαδρομή Analyze → Plot Spectrum αφορά [διάγραμμα συχνοτήτων και πλάτους](https://manual.audacityteam.org/man/plot_spectrum.html), όχι φασματογράφημα μεταβολής στον χρόνο. Η γραμμή περιγράφει ενέργεια στο γραφικό περιβάλλον και δεν εκτελείται στο shell.

```bash
# Παράδειγμα ανάλυσης φάσματος Audacity — δεν είναι φασματογράφημα χρόνου
Open file → Analyze → Plot Spectrum — Άνοιγμα αρχείου και προβολή φάσματος

# Sonic Visualiser
# Εξέταση συχνοτήτων για ενδείξεις κρυμμένων δεδομένων
```

---

<a id="section-03"></a>

## 🧠 Ψηφιακή διερεύνηση μνήμης

Η σύνταξη imageinfo και --profile ανήκει στα παραδείγματα [Volatility 2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference). Δεν μεταφέρεται αυτούσια σε Volatility 3. Το Win7SP1x64 είναι ενδεικτικό προφίλ που πρέπει να ταιριάζει στο αποτύπωμα μνήμης.


### Volatility
```bash
# Εντοπισμός κατάλληλου προφίλ Volatility 2
volatility -f dump.raw imageinfo

# Κατάλογος και δέντρο διεργασιών
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Ιστορικό εντολών από το αποτύπωμα μνήμης
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Εξαγωγή δεδομένων συγκεκριμένης διεργασίας
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Αναζήτηση στοιχείων αρχείων
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

<a id="section-04"></a>

## 💽 Ψηφιακή διερεύνηση δίσκου

Η εντολή mount του πρωτοτύπου δεν ζητά ρητά προσάρτηση μόνο για ανάγνωση. Μην τη θεωρείτε διαδικασία διατήρησης του πρωτοτύπου· εργαστείτε σε αντίγραφο. Τα εργαλεία μπορεί επίσης να χρειάζονται σωστό offset διαμερίσματος, το οποίο δεν προσδιορίζεται εδώ.


```bash
# Προσάρτηση εικόνας δίσκου — δείτε τη σημείωση για το πρωτότυπο
mount -o loop image.dd /mnt/disk

# Πληροφορίες συστήματος αρχείων
fsstat image.dd

# Autopsy — διαθέσιμη διεπαφή ανά εγκατάσταση
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

<a id="section-05"></a>

## 📊 Διερεύνηση δικτυακών καταγραφών (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Εξαγωγή δεδομένων ροών επικοινωνίας
tcpflow -r capture.pcap
```

---

<a id="section-06"></a>

## 📋 Λίστα ελέγχου Forensics CTF

```markdown
□ Αναγνωρίστε τον τύπο αρχείου με file
□ Αναζητήστε συμβολοσειρές που σχετίζονται με το flag
□ Εξετάστε μεταδεδομένα με exiftool
□ Χρησιμοποιήστε Binwalk για ενδείξεις ενσωματωμένων αρχείων
□ Επιλέξτε εργαλεία στεγανογραφίας ανά τύπο αρχείου
□ Αναλύστε αποτύπωμα μνήμης με κατάλληλη έκδοση Volatility
□ Αναλύστε καταγραφή PCAP με Wireshark
```

---

**Επιστροφή:** [🏁 Ελληνική επισκόπηση CTF](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
