# 📷 ExifTool — Οδηγός ανάγνωσης και διαχείρισης μεταδεδομένων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Εργαλείο εξαγωγής μεταδεδομένων
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## Πριν ξεκινήσετε

Το **ExifTool** διαβάζει μεταδεδομένα πολλών τύπων αρχείων και μπορεί να γράφει ή να αφαιρεί ορισμένα πεδία στους υποστηριζόμενους εγγράψιμους τύπους. **Μεταδεδομένα** είναι πληροφορίες για ένα αρχείο, όπως δηλωμένος δημιουργός, ημερομηνία, μοντέλο κάμερας ή θέση GPS.

**Βασικοί όροι:** *tag* = πεδίο μεταδεδομένων, *group* = ομάδα πεδίων, *EXIF/IPTC/XMP* = διαφορετικές οικογένειες μεταδεδομένων, *MakerNotes* = στοιχεία ειδικά για τον κατασκευαστή, *timestamp* = χρονική σήμανση. Τα ονόματα πεδίων και οι επιλογές διατηρούνται στα αγγλικά επειδή αποτελούν σύνταξη του εργαλείου.

Η απλή ανάγνωση διαφέρει από επιλογές εγγραφής, αντιγραφής, αφαίρεσης ή μετονομασίας. Για ψηφιακή εξέταση κρατήστε το πρωτότυπο και δουλέψτε σε αντίγραφο. Οι τιμές μεταδεδομένων μπορούν να αλλάξουν ή να λείπουν, επομένως δεν αποτελούν μόνες τους απόδειξη τοποθεσίας, δημιουργού ή αυθεντικότητας.

## 📋 Περιεχόμενα

- [🎯 Τι είναι το ExifTool](#section-01)
- [🚀 Εγκατάσταση](#section-02)
- [💻 Βασική χρήση](#section-03)
- [📖 Ανάγνωση μεταδεδομένων](#section-04)
- [✏️ Εγγραφή μεταδεδομένων](#section-05)
- [🗑️ Αφαίρεση μεταδεδομένων](#section-06)
- [📁 Τύποι αρχείων](#section-07)
- [🔍 OSINT και ψηφιακή διερεύνηση](#section-08)
- [⚙️ Μαζική επεξεργασία](#section-09)
- [📊 Γρήγορη αναφορά](#section-10)
- [📚 Πηγές](#section-11)

---

<a id="section-01"></a>

## 🎯 Τι είναι το ExifTool

Το **ExifTool** είναι εργαλείο γραμμής εντολών για ανάγνωση, εγγραφή και επεξεργασία μεταδεδομένων αρχείων. Χρησιμοποιείται για:

- 🔍 **OSINT** — εξέταση δηλωμένων στοιχείων τοποθεσίας και συσκευής σε εικόνες
- 🔬 **Ψηφιακή διερεύνηση** — ανάλυση μεταδεδομένων εγγράφων
- 🔐 **Ιδιωτικότητα** — αφαίρεση υποστηριζόμενων ευαίσθητων μεταδεδομένων
- 📸 **Φωτογραφία** — διαχείριση μεταδεδομένων φωτογραφιών
- 📄 **Έγγραφα** — εξέταση μεταδεδομένων PDF και Office

### Γιατί έχουν σημασία τα μεταδεδομένα

| Χρήση | Παράδειγμα |
|----------|---------|
| **Γεωγραφικός εντοπισμός** | Συντεταγμένες GPS σε φωτογραφίες |
| **Στοιχεία συσκευής** | Μοντέλο κάμερας και σειριακός αριθμός |
| **Χρονικές σημάνσεις** | Πότε δηλώνεται δημιουργία ή τροποποίηση αρχείου |
| **Δημιουργός** | Δηλωμένος δημιουργός εγγράφου |
| **Λογισμικό** | Ποια εφαρμογή δηλώνεται ως δημιουργός αρχείου |
| **Ιστορικό επεξεργασίας** | Χρονικές σημάνσεις τροποποιήσεων |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Linux (Kali και Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Εναλλακτικά
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Λήψη από την επίσημη σελίδα
https://exiftool.org/

# Αποσυμπίεση του exiftool(-k).exe
# Μετονομασία σε exiftool.exe
# Προσθήκη στο PATH ή χρήση με πλήρη διαδρομή
```

### Επιβεβαίωση εγκατάστασης

```bash
exiftool -ver
# Ενδεικτική έξοδος του πρωτοτύπου: 12.xx
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

### Βασική σύνταξη

```bash
# Ανάγνωση μεταδεδομένων
exiftool [OPTIONS] FILE

# Εγγραφή μεταδεδομένων
exiftool -TAG=VALUE FILE

# Πολλά αρχεία
exiftool *.jpg
exiftool -r directory/
```

### Συνηθισμένες επιλογές

| Επιλογή | Περιγραφή |
|--------|-------------|
| `-r` | Αναδρομική επεξεργασία υποφακέλων |
| `-v` | Αναλυτική έξοδος |
| `-n` | Χωρίς μετατροπή εμφάνισης, αριθμητικές ή αρχικές τιμές πεδίων |
| `-s` | Σύντομα ονόματα πεδίων |
| `-G` | Εμφάνιση ομάδας κάθε πεδίου |
| `-j` | Έξοδος JSON |
| `-csv` | Έξοδος CSV |
| `-overwrite_original` | Χωρίς δημιουργία του συνήθους αντιγράφου ασφαλείας |

---

<a id="section-04"></a>

## 📖 Ανάγνωση μεταδεδομένων

### Προβολή μεταδεδομένων

```bash
# Προβολή διαθέσιμων μεταδεδομένων
exiftool image.jpg

# Αναλυτική έξοδος
exiftool -v image.jpg

# Ακόμη αναλυτικότερη έξοδος
exiftool -v3 image.jpg
```

### Συγκεκριμένα πεδία

```bash
# Μία ετικέτα
exiftool -GPSLatitude image.jpg

# Πολλά πεδία
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Εξαίρεση πεδίων
exiftool --MakerNotes image.jpg
```

### Ανάγνωση ανά ομάδα πεδίων

```bash
# Εμφάνιση πεδίων EXIF
exiftool -EXIF:all image.jpg

# Εμφάνιση πεδίων IPTC
exiftool -IPTC:all image.jpg

# Εμφάνιση πεδίων XMP
exiftool -XMP:all image.jpg

# Εμφάνιση πεδίων GPS
exiftool -GPS:all image.jpg

# Εμφάνιση με ονόματα ομάδων
exiftool -G image.jpg
```

### Συνήθεις ομάδες μεταδεδομένων

| Ομάδα | Περιγραφή |
|-------|-------------|
| EXIF | Στοιχεία κάμερας και εικόνας |
| IPTC | Μεταδεδομένα ειδήσεων και δημοσιογραφικού περιεχομένου |
| XMP | Επεκτάσιμα μεταδεδομένα XMP |
| GPS | Δεδομένα τοποθεσίας |
| MakerNotes | Στοιχεία ειδικά για την κάμερα |
| Αρχείο | Βασικές πληροφορίες αρχείου |
| Composite | Υπολογιζόμενες τιμές |

### Μορφές αποτελεσμάτων

```bash
# Έξοδος JSON
exiftool -j image.jpg

# Έξοδος CSV
exiftool -csv image.jpg

# Έξοδος HTML
exiftool -htmlDump image.jpg > output.html

# Πεδία χωρισμένα με tab
exiftool -t image.jpg

# Προσαρμοσμένη μορφή
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="section-05"></a>

## ✏️ Εγγραφή μεταδεδομένων

> **Οι εντολές αλλάζουν αρχεία:** Οι αναθέσεις πεδίων, η αντιγραφή tags και οι μετονομασίες πρέπει να γίνονται σε αντίγραφα εργασίας. Συνήθως διατηρείται αρχείο με κατάληξη _original, αλλά το overwrite_original καταργεί αυτή την προστασία. Η δυνατότητα ανάγνωσης ενός format δεν συνεπάγεται ότι κάθε tag είναι εγγράψιμο. Δείτε την [τεκμηρίωση ExifTool](https://exiftool.org/exiftool_pod.html).


### Εγγραφή ενός πεδίου

```bash
# Ορισμός δημιουργού
exiftool -Author="John Doe" image.jpg

# Ορισμός στοιχείων πνευματικών δικαιωμάτων
exiftool -Copyright="© 2024 Company" image.jpg

# Ορισμός σχολίου
exiftool -Comment="My photo" image.jpg
```

### Εγγραφή πολλών πεδίων

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Εγγραφή συντεταγμένων GPS

> **Κατεύθυνση και υπολογισμός:** Το γεωγραφικό πλάτος/μήκος χρειάζεται σωστά GPSLatitudeRef και GPSLongitudeRef όπου τα απαιτεί η μορφή. Μην υποθέτετε ότι ένα πρόσημο ρυθμίζει κάθε σχετικό πεδίο. Σύνδεσμος χάρτη και δεκαδικές τιμές χρειάζονται έλεγχο μορφοποίησης και αναφοράς ημισφαιρίου.


```bash
# Ορισμός συντεταγμένων GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Με ρητή δήλωση βορρά/νότου και ανατολής/δύσης
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# Παράδειγμα συντεταγμένων σε μοίρες, λεπτά και δευτερόλεπτα
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Εγγραφή ημερομηνίας και ώρας

```bash
# Ορισμός ημερομηνίας και ώρας
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Ορισμός της ομάδας AllDates· δεν καλύπτει κάθε ημερομηνία του αρχείου
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Μετατόπιση ημερομηνιών
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Προσθήκη ενός έτους
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Αφαίρεση επτά ημερών
```

### Αντιγραφή μεταδεδομένων μεταξύ αρχείων

```bash
# Αντιγραφή υποστηριζόμενων εγγράψιμων μεταδεδομένων
exiftool -TagsFromFile source.jpg destination.jpg

# Αντιγραφή συγκεκριμένων πεδίων
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Αντιγραφή σε πολλά αρχεία
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="section-06"></a>

## 🗑️ Αφαίρεση μεταδεδομένων

> **Δεν εγγυάται πλήρη ανωνυμοποίηση:** Το -all= αφαιρεί υποστηριζόμενα μεταδεδομένα, όχι αναγνωρίσιμο περιεχόμενο εικόνας ή κάθε πληροφορία κάθε format. Η ενημέρωση PDF είναι προσθετική και παλιότερα μεταδεδομένα μπορεί να παραμένουν ανακτήσιμα. Πολλά Office formats υποστηρίζονται μόνο για ανάγνωση, οπότε τα παραδείγματα αφαίρεσης σε DOCX/XLSX/PPTX δεν είναι γενικά εφαρμόσιμα. Ελέγξτε δυνατότητες ανά μορφή και αποτέλεσμα.


### Αφαίρεση υποστηριζόμενων μεταδεδομένων

```bash
# Αφαίρεση υποστηριζόμενων μεταδεδομένων· ελέγξτε περιορισμούς τύπου αρχείου
exiftool -all= image.jpg

# Αφαίρεση χωρίς το συνήθως δημιουργούμενο αντίγραφο ασφαλείας
exiftool -overwrite_original -all= image.jpg

# Επεξεργασία πολλών αρχείων
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Αφαίρεση συγκεκριμένων μεταδεδομένων

```bash
# Αφαίρεση πεδίων GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Αφαίρεση μόνο EXIF
exiftool -EXIF:all= image.jpg

# Αφαίρεση σχολίων
exiftool -Comment= image.jpg

# Αφαίρεση πεδίων δημιουργού
exiftool -Author= -Creator= -Artist= image.jpg
```

### Αφαίρεση ανά τύπο αρχείου

```bash
# Εικόνες
exiftool -all= *.jpg *.png *.gif

# PDFs
exiftool -all= document.pdf

# Αρχεία Office· η δυνατότητα ανάγνωσης δεν συνεπάγεται εγγραφή
exiftool -all= *.docx *.xlsx *.pptx

# Βίντεο
exiftool -all= video.mp4
```

### Μείωση έκθεσης προσωπικών στοιχείων

```bash
# Παράδειγμα αφαίρεσης αναγνωριστικών πεδίων, χωρίς εγγύηση πλήρους ανωνυμοποίησης
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="section-07"></a>

## 📁 Τύποι αρχείων

### Ενδεικτικοί τύποι αρχείων του πρωτοτύπου

| Κατηγορία | Μορφές |
|----------|---------|
| **Εικόνες** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Φωτογραφίες RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Βίντεο** | MP4, MOV, AVI, MKV, WebM |
| **Ήχος** | MP3, WAV, FLAC, M4A, OGG |
| **Έγγραφα** | PDF, DOCX, XLSX, PPTX |
| **Συμπιεσμένα αρχεία** | ZIP με περιορισμένη υποστήριξη |

### Εντολές ανά τύπο αρχείου

```bash
# Μεταδεδομένα PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Μεταδεδομένα βίντεο
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Μεταδεδομένα ήχου
exiftool -Artist -Album -Title -Duration audio.mp3

# Έγγραφα Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="section-08"></a>

## 🔍 OSINT και ψηφιακή διερεύνηση

### Εξαγωγή θέσης GPS

```bash
# Εμφάνιση συντεταγμένων GPS
exiftool -GPS:all image.jpg

# Μορφοποιημένη θέση GPS
exiftool -GPSPosition image.jpg

# Δεκαδικές μοίρες
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Σύνδεσμος Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Αναγνώριση στοιχείων συσκευής

```bash
# Στοιχεία κάμερας
exiftool -Make -Model -SerialNumber image.jpg

# Δηλωμένο λογισμικό επεξεργασίας
exiftool -Software -CreatorTool image.jpg

# Στοιχεία φακού, εφόσον υπάρχουν
exiftool -Lens -LensModel -FocalLength image.jpg

# Αναλυτικά στοιχεία κάμερας
exiftool -MakerNotes:all image.jpg
```

### Ανάλυση χρονογραμμής

```bash
# Ημερομηνίες και ώρες
exiftool -time:all image.jpg

# Χρονικές σημάνσεις συστήματος αρχείων
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Χρονικές σημάνσεις EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Αναζήτηση αποκλίσεων ημερομηνιών
exiftool -a -G1 -s -time:all image.jpg
```

### Ψηφιακή εξέταση εγγράφων

```bash
# Ανάλυση PDF
exiftool -a -G1 document.pdf

# Διαθέσιμα πεδία ιστορικού αρχείου Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Αναζήτηση πεδίων δημιουργού
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Ενδείξεις αυθεντικότητας εικόνας

> **Ενδείξεις, όχι απόδειξη:** Λογισμικό επεξεργασίας, χρονικές αποκλίσεις ή μικρογραφία διαφορετική από την εικόνα χρειάζονται συσχέτιση με προέλευση και άλλες εξετάσεις. Η απουσία τους δεν αποδεικνύει αυθεντικότητα. Τα μεταδεδομένα μπορούν να έχουν αφαιρεθεί ή τροποποιηθεί.


```bash
# Εξέταση δηλωμένου λογισμικού επεξεργασίας
exiftool -Software -HistorySoftwareAgent image.jpg

# Εξαγωγή μικρογραφίας για σύγκριση με την εικόνα
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Εξέταση χρονικών σημάνσεων για πιθανές αποκλίσεις
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Χρήσιμα ερωτήματα OSINT

```bash
# Εξαγωγή επιλεγμένων αναγνωριστικών πεδίων
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Αναζήτηση συγκεκριμένων λέξεων στο πεδίο Software· δεν αποδεικνύει στεγανογραφία
exiftool -Software image.jpg | grep -i "steg\|hide"

# Εξαγωγή ενσωματωμένης μικρογραφίας
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Αναζήτηση ονομάτων πλατφορμών σε μεταδεδομένα
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="section-09"></a>

## ⚙️ Μαζική επεξεργασία

> **Εύρος αλλαγών:** Αναδρομή, wildcards και overwrite_original μπορούν να επηρεάσουν πολλά αρχεία. Οι εντολές με $, εισαγωγικά και ανακατεύθυνση ακολουθούν συγκεκριμένο shell· μην τις αντιγράφετε χωρίς προσαρμογή σε άλλο περιβάλλον. Το AllDates αφορά επιλεγμένη ομάδα ημερομηνιών, όχι κάθε χρονική σήμανση.


### Επεξεργασία πολλών αρχείων

```bash
# Όλα τα JPG του τρέχοντος φακέλου
exiftool *.jpg

# Αναδρομική επεξεργασία
exiftool -r directory/

# Συγκεκριμένες επεκτάσεις
exiftool -ext jpg -ext png directory/
```

### Μαζική αφαίρεση μεταδεδομένων

```bash
# Αφαίρεση μεταδεδομένων από τις επιλεγμένες εικόνες
exiftool -all= -overwrite_original -r directory/

# Μόνο συγκεκριμένοι τύποι
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Μαζική μετονομασία αρχείων

```bash
# Μετονομασία βάσει ημερομηνίας
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Μετονομασία βάσει μοντέλου κάμερας
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Μετονομασία με μετρητή, σύμφωνα με το πρωτότυπο
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Μαζική αντιγραφή μεταδεδομένων

```bash
# Αντιγραφή GPS από ένα αρχείο στα υπόλοιπα επιλεγμένα
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Εξαγωγή σε CSV

```bash
# Εξαγωγή μεταδεδομένων σε CSV
exiftool -csv *.jpg > metadata.csv

# Συγκεκριμένα πεδία
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Επεξεργασία υπό συνθήκες

```bash
# Μόνο αρχεία με διαθέσιμο πεδίο GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Μόνο αρχεία με τον επιλεγμένο κατασκευαστή κάμερας
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Αρχεία με δηλωμένη τροποποίηση μετά την ημερομηνία
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="section-10"></a>

## 📊 Γρήγορη αναφορά

### Συχνά χρησιμοποιούμενες εντολές

| Σκοπός | Εντολή |
|---------|---------|
| Προβολή μεταδεδομένων | `exiftool image.jpg` |
| Προβολή GPS | `exiftool -GPS:all image.jpg` |
| Αφαίρεση υποστηριζόμενων μεταδεδομένων | `exiftool -all= image.jpg` |
| Αφαίρεση GPS | `exiftool -GPS:all= image.jpg` |
| Έξοδος JSON | `exiftool -j image.jpg` |
| Αναδρομική επεξεργασία | `exiftool -r directory/` |
| Στοιχεία κάμερας | `exiftool -Make -Model image.jpg` |
| Χρονικές σημάνσεις | `exiftool -time:all image.jpg` |

### Συνήθεις ετικέτες

| Ετικέτα | Περιγραφή |
|-----|-------------|
| `-Make` | Κατασκευαστής κάμερας |
| `-Model` | Μοντέλο κάμερας |
| `-DateTimeOriginal` | Δηλωμένος χρόνος λήψης φωτογραφίας |
| `-GPSLatitude` | Γεωγραφικό πλάτος GPS |
| `-GPSLongitude` | Γεωγραφικό μήκος GPS |
| `-Author` | Δημιουργός αρχείου |
| `-Copyright` | Στοιχεία πνευματικών δικαιωμάτων |
| `-Software` | Λογισμικό επεξεργασίας |
| `-ImageWidth` | Πλάτος εικόνας |
| `-ImageHeight` | Ύψος εικόνας |

### Λίστα ελέγχου ιδιωτικότητας

```bash
# 1. Έλεγχος διαθέσιμων μεταδεδομένων
exiftool -a -G1 image.jpg

# 2. Έλεγχος GPS
exiftool -GPS:all image.jpg

# 3. Έλεγχος αναγνωριστικών στοιχείων
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Παράδειγμα αφαίρεσης υποστηριζόμενων μεταδεδομένων σε αντίγραφο
exiftool -all= -overwrite_original image.jpg

# 5. Έλεγχος αποτελέσματος και περιορισμών
exiftool image.jpg
```

### Λίστα ψηφιακής εξέτασης

```bash
# 1. Διατήρηση πρωτοτύπου και δημιουργία αντιγράφου εργασίας
cp image.jpg image_original.jpg

# 2. Αναλυτική αναφορά μεταδεδομένων
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Εξαγωγή GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Χρονογραμμή
exiftool -time:all -G1 image.jpg

# 5. Στοιχεία συσκευής
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Εξέταση ενδείξεων επεξεργασίας
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

<a id="section-11"></a>

## 📚 Πηγές

- [Επίσημη σελίδα ExifTool](https://exiftool.org/)
- [Τεκμηρίωση πεδίων ExifTool](https://exiftool.org/TagNames/)
- [Φόρουμ ExifTool](https://exiftool.org/forum/)
- [Υποστηριζόμενοι τύποι αρχείων](https://exiftool.org/#supported)

### Σχετικοί οδηγοί
- [Autopsy](../../Autopsy/translations/README.el.md)
- [Volatility](../../Volatility/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)

---

<p align="center">
  <b>📷 Εξετάστε προσεκτικά τα μεταδεδομένα!</b><br>
  <i>ExifTool — μεταδεδομένα με προσεκτική ερμηνεία</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
