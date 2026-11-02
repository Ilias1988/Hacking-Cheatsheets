# 📧 OSINT — Έρευνα διευθύνσεων email

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="../Email-OSINT.md">🇬🇧 English</a> • <a href="Email-OSINT.tr.md">🇹🇷 Türkçe</a>
</p>

[🔍 Ελληνική επισκόπηση OSINT](README.el.md)

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## Πριν ξεκινήσετε

Η έρευνα **email** εξετάζει δημόσιες εμφανίσεις μιας διεύθυνσης, πιθανά μοτίβα εταιρικών email και καταγεγραμμένες διαρροές. Το **breach** είναι περιστατικό παραβίασης ή διαρροής δεδομένων. Το **HIBP** είναι το Have I Been Pwned. Το **API key** είναι κλειδί πρόσβασης σε υπηρεσία και δεν πρέπει να δημοσιεύεται.

Το **DNS** δημοσιεύει πληροφορίες για ένα domain. Οι εγγραφές **MX** περιγράφουν διακομιστές διαχείρισης email, ενώ το **SMTP** είναι πρωτόκολλο μεταφοράς email. Η ύπαρξη MX ή η σωστή σύνταξη μιας διεύθυνσης δεν επιβεβαιώνει συγκεκριμένο γραμματοκιβώτιο. Ο έλεγχος SMTP επικοινωνεί με διακομιστή και συνεπώς έχει διαφορετικό αποτύπωμα από μια απλή αναζήτηση σε δημόσια ευρετήρια.

Τα μοτίβα first.last και οι παραλλαγές του Python παραδείγματος είναι υποθέσεις, όχι επιβεβαιωμένες διευθύνσεις. Εύρημα σε διαρροή δεν αποδεικνύει ότι ο τρέχων κωδικός είναι γνωστός ή ότι ένας λογαριασμός παραμένει παραβιασμένος. Η απουσία ευρήματος δεν αποδεικνύει απουσία διαρροής. Περιορίστε την έρευνα στα δεδομένα που χρειάζεστε και καταγράψτε την προέλευση των αποτελεσμάτων.

## Περιεχόμενα

- [🔍 Εργαλεία εντοπισμού email](#section-01)
- [🛠️ Εντολές διερεύνησης διευθύνσεων email](#section-02)
- [📧 Συνηθισμένα μοτίβα διευθύνσεων email](#section-03)
- [🔐 Έλεγχος καταγεγραμμένων διαρροών](#section-04)
- [✅ Επαλήθευση διευθύνσεων email](#section-05)
- [📋 Λίστα ελέγχου έρευνας email](#section-06)

<a id="section-01"></a>

## 🔍 Εργαλεία εντοπισμού email

| Εργαλείο | URL | Χρήση |
| ------ | ----- | --------- |
| **Hunter.io** | hunter.io | Εύρεση εταιρικών διευθύνσεων email |
| **Phonebook.cz** | phonebook.cz | Αναζήτηση email — χαρακτηρισμός πρωτοτύπου ως δωρεάν |
| **Snov.io** | snov.io | Εντοπισμός διευθύνσεων email |
| **Clearbit Connect** | clearbit.com | Συμπληρωματικά στοιχεία για διεύθυνση email |
| **RocketReach** | rocketreach.co | Εντοπισμός στοιχείων επικοινωνίας |
| **Voila Norbert** | voilanorbert.com | Αναζήτηση διεύθυνσης email |

---

<a id="section-02"></a>

## 🛠️ Εντολές διερεύνησης διευθύνσεων email

### theHarvester
```bash
# Συλλογή διευθύνσεων email
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io — Παραδείγματα αιτημάτων API
```bash
# Αίτημα API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Έλεγχος ενδείξεων εγκυρότητας email
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail — Αναζήτηση σε πηγές διαρροών
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe — Έλεγχος ενδείξεων εγγραφής
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

<a id="section-03"></a>

## 📧 Συνηθισμένα μοτίβα διευθύνσεων email

```bash
# Συνηθισμένα μοτίβα εταιρικών email
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Δημιουργία πιθανών παραλλαγών email
```python
# Δημιουργία πιθανών παραλλαγών email
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

<a id="section-04"></a>

## 🔐 Έλεγχος καταγεγραμμένων διαρροών

Ο χαρακτηρισμός «δωρεάν» αφορά την κατηγοριοποίηση του πρωτοτύπου και δεν ισχύει κατ’ ανάγκη για API ή όλες τις λειτουργίες. Για αναζήτηση email στο API HIBP απαιτείται εξουσιοδοτημένο κλειδί πρόσβασης, σύμφωνα με την [επίσημη τεκμηρίωσή του](https://haveibeenpwned.com/API/v3).


| Υπηρεσία | URL | Τύπος |
| --------- | ----- | ------ |
| **Have I Been Pwned** | haveibeenpwned.com | Δωρεάν πρόσβαση σύμφωνα με το πρωτότυπο |
| **DeHashed** | dehashed.com | Επί πληρωμή σύμφωνα με το πρωτότυπο |
| **LeakCheck** | leakcheck.io | Επί πληρωμή σύμφωνα με το πρωτότυπο |
| **Snusbase** | snusbase.com | Επί πληρωμή σύμφωνα με το πρωτότυπο |
| **IntelX** | intelx.io | Συνδυασμός δωρεάν και επί πληρωμή πρόσβασης |

### API του Have I Been Pwned
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

<a id="section-05"></a>

## ✅ Επαλήθευση διευθύνσεων email

### Μέθοδοι επαλήθευσης και οι περιορισμοί τους
```
1. Έλεγχος MX — Εντοπισμός εγγραφών διαχείρισης email του domain
2. Έλεγχος SMTP — Ενδείξεις αποδοχής παραλήπτη, όχι βεβαιότητα ύπαρξης λογαριασμού
3. Έλεγχος σύνταξης — Εξέταση της μορφής της διεύθυνσης
4. Έλεγχος προσωρινών email — Ένδειξη παρόχου προσωρινών διευθύνσεων
5. Έλεγχος ρόλου — Αναγνώριση γενικών διευθύνσεων όπως info@ ή admin@
```

### Απλό παράδειγμα συνομιλίας SMTP

Το σχόλιο του πρωτοτύπου «250 = υπάρχει, 550 = δεν υπάρχει» είναι υπεραπλούστευση. Οι απαντήσεις μπορεί να επηρεάζονται από πολιτικές αποδοχής ή απόρριψης και δεν επιβεβαιώνουν από μόνες τους την ύπαρξη συγκεκριμένου mailbox. Οι γραμμές HELO, MAIL FROM και RCPT TO ανήκουν στη συνομιλία SMTP μετά τη σύνδεση, όχι στο shell. Δείτε το [RFC 5321 για SMTP](https://www.rfc-editor.org/rfc/rfc5321).

```bash
# Έλεγχος εγγραφών MX
nslookup -type=mx target.com
dig mx target.com

# Παράδειγμα συνομιλίας SMTP μέσω Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# Οι απαντήσεις 250/550 δεν αποδεικνύουν από μόνες τους ύπαρξη ή απουσία mailbox
```

---

<a id="section-06"></a>

## 📋 Λίστα ελέγχου έρευνας email

```markdown
## Email προς έρευνα: [email@domain.com]

□ Εξετάστε ενδείξεις εγκυρότητας email μέσω SMTP ή Hunter
□ Ελέγξτε καταγεγραμμένες διαρροές στο HIBP
□ Αναζητήστε ενδείξεις συσχετισμένων λογαριασμών με holehe
□ Αναζητήστε τη διεύθυνση σε Google και Bing
□ Εξετάστε δημόσιες ενδείξεις εγγραφής σε κοινωνικά δίκτυα
□ Αναζητήστε δημόσια προφίλ
□ Αναζητήστε νόμιμα διαθέσιμες δημόσιες αναρτήσεις κειμένου
□ Εξετάστε επαγγελματικά δίκτυα
```

---

**Επιστροφή:** [🔍 Ελληνική επισκόπηση OSINT](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
