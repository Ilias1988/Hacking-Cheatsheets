# 🔍 Google Dorking — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 Προχωρημένες αναζητήσεις Google για ελέγχους ασφάλειας</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Google Dorking** είναι στοχευμένη αναζήτηση με **operators** (τελεστές), όπως περιορισμός domain ή τύπου αρχείου. Το **query/dork** είναι ολόκληρο το ερώτημα αναζήτησης. **Index** είναι το ευρετήριο της μηχανής και **cached/archived copy** ένα παλιότερο αντίγραφο περιεχομένου· δεν είναι το ίδιο με την τρέχουσα σελίδα.

Τα ερωτήματα παραμένουν στα αγγλικά επειδή οι τελεστές και οι λέξεις που αναζητούν είναι μέρος της σύνταξης. Η επεξήγηση μετά το σύμβολο σχολίου δεν αποτελεί μέρος του ερωτήματος. Τα αποτελέσματα δεν είναι πλήρης κατάλογος υποδομών ούτε απόδειξη ευπάθειας. Τα domains είναι ενδεικτικά· ελέγξτε το scope πριν ανοίξετε ή ελέγξετε ενεργά οποιοδήποτε εύρημα.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Βασικοί τελεστές](#section-02)
- [Προχωρημένοι τελεστές](#section-03)
- [Συνδυασμός τελεστών](#section-04)
- [Αναζήτηση αρχείων](#section-05)
- [Εμφάνιση περιεχομένων καταλόγων](#section-06)
- [Σελίδες διαχείρισης](#section-07)
- [Βάσεις δεδομένων και SQL](#section-08)
- [Αρχεία ρυθμίσεων](#section-09)
- [Στοιχεία πρόσβασης και μυστικά](#section-10)
- [Ενδείξεις πιθανώς ευάλωτων servers](#section-11)
- [Ερωτήματα Bug Bounty](#section-12)
- [Παραδείγματα ανά CMS](#section-13)
- [Γρήγορη αναφορά](#section-14)
- [Εργαλεία και αυτοματοποίηση](#section-15)
- [Συμβουλές και καλές πρακτικές](#section-16)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-17)
- [Πηγές](#section-18)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

Το **Google Dorking** χρησιμοποιεί τελεστές αναζήτησης για τον εντοπισμό δημόσια ευρετηριασμένων πληροφοριών, μεταξύ των οποίων μπορεί να υπάρχουν ακούσιες εκθέσεις δεδομένων.

### Τι μπορείτε να εντοπίσετε;

| Κατηγορία | Παραδείγματα |
| ---------- | ---------- |
| **Ευαίσθητα αρχεία** | Αντίγραφα ασφαλείας, ρυθμίσεις, καταγραφές και βάσεις δεδομένων |
| **Εκτεθειμένες σελίδες διαχείρισης** | Σελίδες διαχείρισης, πίνακες ελέγχου και phpMyAdmin |
| **Στοιχεία πρόσβασης** | Κωδικοί πρόσβασης, κλειδιά API και tokens |
| **Πιθανές αδυναμίες** | Ενδείξεις SQL injection και εκτεθειμένων καταλόγων |
| **Προσωπικά δεδομένα** | Email, τηλέφωνα και έγγραφα |

### Διευθύνσεις μηχανών αναζήτησης

| Μηχανή | Διεύθυνση ιστού (URL) |
| -------- | ----- |
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

<a id="section-02"></a>

## 🔧 Βασικοί τελεστές

> **Σύνταξη και κάλυψη:** Μην αντιγράφετε μαζί με το ερώτημα τα επεξηγηματικά σχόλια. Η αναζήτηση site δεν εμφανίζει απαραίτητα όλα τα ευρετηριασμένα URLs· τα μοτίβα με αστερίσκο δεν αποτελούν πλήρη καταγραφή DNS. Βλ. [οδηγό site της Google](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site).


### Τελεστής site

Περιορισμός αναζήτησης σε συγκεκριμένο domain:

```
site:example.com                     # Μόνο το example.com
site:*.example.com                   # Αναζήτηση σχετικών υποτομέων· δεν εγγυάται πληρότητα
site:example.com -www                # Εξαίρεση του όρου www
site:.gov                            # Ιστότοποι με κατάληξη gov
site:.edu                            # Ιστότοποι με κατάληξη edu
```

### Τελεστής inurl

Αναζήτηση μέσα στο URL:

```
inurl:admin                          # Το admin μέσα στο URL
inurl:login                          # Σελίδες σύνδεσης
inurl:dashboard                      # Πίνακες ελέγχου
inurl:config                         # Αρχεία ρυθμίσεων
inurl:backup                         # Αντίγραφα ασφαλείας
```

### Τελεστής intitle

Αναζήτηση στον τίτλο σελίδας:

```
intitle:"index of"                   # Εμφάνιση περιεχομένων καταλόγων
intitle:admin                        # Σελίδες διαχείρισης
intitle:login                        # Σελίδες σύνδεσης
intitle:"dashboard"                  # Πίνακες ελέγχου
intitle:"control panel"              # Πίνακες ελέγχου
```

### Τελεστής intext

Αναζήτηση στο περιεχόμενο σελίδας:

```
intext:"password"                    # Το password στο κείμενο
intext:"username"                    # Το username στο κείμενο
intext:"api_key"                     # Κλειδιά API
intext:"secret"                      # Μυστικά
```

### Τελεστής filetype

Αναζήτηση συγκεκριμένων τύπων αρχείων:

```
filetype:pdf                         # Αρχεία PDF
filetype:doc                         # Έγγραφα Word
filetype:xls                         # Αρχεία Excel
filetype:sql                         # Αρχεία SQL
filetype:log                         # Αρχεία καταγραφής
filetype:bak                         # Αντίγραφα ασφαλείας
filetype:env                         # Αρχεία μεταβλητών περιβάλλοντος
```

---

<a id="section-03"></a>

## ⚡ Προχωρημένοι τελεστές

### Παραλλαγές all

```
allinurl:admin login                 # Όλες οι λέξεις μέσα στο URL
allintitle:admin panel               # Όλες οι λέξεις στον τίτλο
allintext:password username          # Όλες οι λέξεις στο κείμενο
```

### Ιστορικές επιλογές cache και link

> **Ιστορικά παραδείγματα:** Η Google έχει καταργήσει τη λειτουργία του cache operator. Τα cache/link/related του πρωτοτύπου δεν πρέπει να θεωρούνται όλα διαθέσιμα σήμερα. Δείτε την [επίσημη καταγραφή αλλαγών](https://developers.google.com/search/updates).


```
cache:example.com                    # Παλιό αντίγραφο cache — ιστορικό παράδειγμα
link:example.com                     # Ιστορική επιλογή αναζήτησης εισερχόμενων συνδέσμων
related:example.com                  # Ιστορική επιλογή σχετικών ιστοτόπων
```

### Χρονικό διάστημα

> **Ημερομηνίες αποτελεσμάτων:** Τα before/after αφορούν τον τρόπο που η Google αποδίδει τη χρονική πληροφορία του εγγράφου, όχι εγγυημένη ημερομηνία δημοσίευσης ή δημιουργίας αρχείου. Το παλιό daterange του πρωτοτύπου χρειάζεται επιβεβαίωση. Βλ. [τρέχοντες τελεστές αναζήτησης](https://support.google.com/websearch/answer/2466433).


```
after:2023-01-01                     # Μετά την ημερομηνία
before:2024-01-01                    # Πριν την ημερομηνία
daterange:2458850-2459215            # Ιουλιανό εύρος ημερομηνιών του πρωτοτύπου
```

### Χαρακτήρες μπαλαντέρ και OR

```
admin * panel                        # Χαρακτήρας μπαλαντέρ
admin OR login                       # Οποιοσδήποτε από τους όρους
"admin panel"                        # Ακριβής φράση
-inurl:https                         # Εξαίρεση του όρου https στο URL· δεν επαληθεύει πρωτόκολλο
```

### Εύρος αριθμών

```
inurl:id= 1..1000                    # Εύρος αριθμών
price $100..$500                     # Εύρος τιμών
```

---

<a id="section-04"></a>

## 🔗 Συνδυασμός τελεστών

### Βασικοί συνδυασμοί

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Προχωρημένοι συνδυασμοί

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Συνδυασμοί για Bug Bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

<a id="section-05"></a>

## 📁 Αναζήτηση αρχείων

### Ευαίσθητα έγγραφα

```
# Έγγραφα Office
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Αρχεία κειμένου
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Αντίγραφα ασφαλείας

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Αρχεία βάσεων δεδομένων

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Αρχεία καταγραφής

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Αρχεία ρυθμίσεων

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Πηγαίος κώδικας

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

<a id="section-06"></a>

## 📂 Εμφάνιση περιεχομένων καταλόγων

### Σελίδες Index Of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Συγκεκριμένοι κατάλογοι

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Τύποι αρχείων μέσα σε καταλόγους

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

<a id="section-07"></a>

## 🔐 Σελίδες διαχείρισης

### Γενικές διαδρομές διαχείρισης

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel και φιλοξενία

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Διαχείριση βάσεων δεδομένων

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Πίνακες ελέγχου

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

<a id="section-08"></a>

## 💾 Βάσεις δεδομένων και SQL

### Αρχεία SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### Σφάλματα SQL

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### Ενδείξεις προς διερεύνηση για SQL Injection

> **Υποψήφια σημεία:** Η ύπαρξη παραμέτρου id/page ή ενός μηνύματος σφάλματος δεν επιβεβαιώνει SQL Injection. Η αξιολόγηση απαιτεί ξεχωριστό εξουσιοδοτημένο έλεγχο.


```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Εξαγωγές βάσεων δεδομένων

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="section-09"></a>

## ⚙️ Αρχεία ρυθμίσεων

### Ρυθμίσεις web server

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Ρυθμίσεις εφαρμογής

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Ρυθμίσεις Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS και υποδομές cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Πρόθεμα κλειδιού AWS
site:target.com "credentials" filetype:json
```

---

<a id="section-10"></a>

## 🔑 Στοιχεία πρόσβασης και μυστικά

### Κωδικοί πρόσβασης

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Συνδυασμοί χρήστη και κωδικού

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### Κλειδιά API και tokens

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### Διευθύνσεις email

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Ιδιωτικά κλειδιά

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a id="section-11"></a>

## ⚠️ Ενδείξεις πιθανώς ευάλωτων servers

> **Τίτλος και έκδοση δεν αρκούν:** Προεπιλεγμένες σελίδες, dashboards και διαδρομές login δεν είναι από μόνα τους ευπάθειες. Ελέγξτε αν πρόκειται για αναμενόμενη δημόσια λειτουργία και ποια ακριβώς δεδομένα εκτίθενται.


### Προσβάσιμα περιεχόμενα καταλόγων

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Προεπιλεγμένες σελίδες

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Σελίδες σφαλμάτων

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Εκτεθειμένες υπηρεσίες

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

<a id="section-12"></a>

## 🐛 Ερωτήματα Bug Bounty

### Αρχικά σημεία αναζήτησης

```
# Ευαίσθητα αρχεία
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Σελίδες διαχείρισης
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Αρχεία ρυθμίσεων
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Στοιχεία πρόσβασης
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Αναζήτηση υποτομέων

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### Σημεία API

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Περιβάλλοντα ανάπτυξης και δοκιμών

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Σελίδες ταυτοποίησης

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Μεταφόρτωση αρχείων

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

<a id="section-13"></a>

## 📱 Παραδείγματα ανά CMS

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

<a id="section-14"></a>

## 📊 Γρήγορη αναφορά

### Βασικοί τελεστές αναφοράς

| Τελεστής | Λειτουργία | Παράδειγμα |
| ---------- | ---------- | --------- |
| `site:` | Περιορισμός σε domain | `site:target.com` |
| `inurl:` | Αναζήτηση μέσα στο URL | `inurl:admin` |
| `intitle:` | Αναζήτηση στον τίτλο | `intitle:login` |
| `intext:` | Αναζήτηση στο περιεχόμενο | `intext:password` |
| `filetype:` | Τύπος ή επέκταση αρχείου | `filetype:sql` |
| `ext:` | Τύπος ή επέκταση αρχείου | `ext:pdf` |
| `cache:` | Παλιό αντίγραφο cache — ιστορικό παράδειγμα | `cache:target.com` |
| `"..."` | Ακριβής φράση | `"admin panel"` |
| `-` | Εξαίρεση | `-site:www` |
| `OR` / `\|` | Οποιοσδήποτε από τους όρους | `admin OR login` |
| `*` | Χαρακτήρας μπαλαντέρ | `admin * panel` |

### Δέκα ενδεικτικά ερωτήματα Bug Bounty

| # | Ερώτημα αναζήτησης | Σκοπός |
| --- | ------ | --------- |
| 1 | `site:target.com filetype:sql` | Αρχεία βάσεων δεδομένων |
| 2 | `site:target.com inurl:admin` | Περιοχές διαχείρισης |
| 3 | `site:target.com filetype:log` | Αρχεία καταγραφής |
| 4 | `site:target.com filetype:env` | Αρχεία μεταβλητών περιβάλλοντος |
| 5 | `site:target.com intext:password filetype:txt` | Κωδικοί πρόσβασης |
| 6 | `intitle:"index of" site:target.com` | Εμφάνιση περιεχομένων καταλόγων |
| 7 | `site:target.com inurl:api` | Σημεία API |
| 8 | `site:target.com filetype:bak` | Αντίγραφα ασφαλείας |
| 9 | `site:*.target.com -www` | Υποτομείς |
| 10 | `site:target.com "api_key"` | Κλειδιά API |

---

<a id="section-15"></a>

## 🛠️ Εργαλεία και αυτοματοποίηση

### Εργαλεία Google Dorking

| Εργαλείο | Περιγραφή | Διεύθυνση ιστού (URL) |
| ------ | ------------- | ----- |
| **Google Hacking Database** | Συλλογή ερωτημάτων του Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Δημιουργία ερωτημάτων | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Αναζήτηση μέσω Google | GitHub |
| **GooDork** | Εργαλείο αναζητήσεων σε Python | GitHub |
| **GooFuzz** | Αναζήτηση παραλλαγών μέσω Google | GitHub |

### Ιστότοποι σχετικών εργαλείων

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Παράδειγμα script αυτοματοποίησης

> **Το script παράγει URLs:** Δεν εκτελεί τις ίδιες τις αναζητήσεις. Η αντικατάσταση κενών με συν δεν αποτελεί πλήρη κωδικοποίηση URL για αυθαίρετους ειδικούς χαρακτήρες. Τα ορίσματα και η μορφή του παραδείγματος παραμένουν όπως στο πρωτότυπο.


```bash
#!/bin/bash
# Απλό παράδειγμα δημιουργίας URLs αναζήτησης Google

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

<a id="section-16"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

> **Ανενεργό ιστορικό παράδειγμα:** Το παρακάτω cache query διατηρείται για αντιστοίχιση με το αγγλικό κείμενο και δεν αναμένεται να παρέχει αντίγραφο Google σήμερα.


1. **Ξεκινήστε γενικά και περιορίστε σταδιακά**
   ```
   site:target.com                    # Αρχικά
   site:target.com filetype:sql       # Στη συνέχεια πιο συγκεκριμένα
   ```

2. **Χρησιμοποιήστε διαφορετικές μηχανές αναζήτησης**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Ιστορικό παράδειγμα ελέγχου cache**
   ```
   cache:target.com/admin
   ```

4. **Αναζητήστε υποτομείς**
   ```
   site:*.target.com -www
   ```

5. **Περιορίστε τον ρυθμό αναζητήσεων**
   - Η Google μπορεί να περιορίσει υπερβολικά αιτήματα

### Περιορισμοί υπηρεσίας και ρυθμός αναζητήσεων

> **Όρια υπηρεσίας:** Διαφορετικές IP, VPN ή browsers δεν εγγυώνται αφάνεια ή άρση περιορισμών. Ακολουθήστε τους κανόνες και τα όρια της υπηρεσίας αναζήτησης· τα σχόλια του πρωτοτύπου δεν συνιστούν εγγύηση αποτελέσματος.


```
# Αλλαγή IP/VPN κατά το πρωτότυπο· δεν εξασφαλίζει αφάνεια
# Προσθήκη καθυστερήσεων μεταξύ αναζητήσεων
# Παραλλαγή μοτίβων αναζήτησης
# Διαφορετικοί browsers κατά το πρωτότυπο· δεν αναιρούν όρια υπηρεσίας
```

---

<a id="section-17"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Η εμφάνιση αποτελέσματος σε μηχανή αναζήτησης δεν παρέχει άδεια πρόσβασης σε δεδομένα ή συστήματα. Ακολουθείτε το εγκεκριμένο scope και τους κανόνες του προγράμματος.
> 
> - ✅ Χρήση για εξουσιοδοτημένους ελέγχους ασφάλειας
> - ✅ Χρήση μέσα στους κανόνες προγραμμάτων Bug Bounty
> - ✅ Υπεύθυνη αναφορά ευρημάτων
> - ❌ Όχι πρόσβαση σε δεδομένα χωρίς άδεια
> - ❌ Όχι λήψη ή αποθήκευση ευαίσθητων δεδομένων πέρα από την εγκεκριμένη τεκμηρίωση
> - ❌ Όχι εκμετάλλευση ευρημάτων εκτός εγκεκριμένου ελέγχου
> 
> **Εξασφαλίστε εξουσιοδότηση πριν από ενεργό έλεγχο.**

---

<a id="section-18"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Τεκμηρίωση τελεστών Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Βιβλία και οδηγοί
- "Google Hacking for Penetration Testers", του Johnny Long
- Οδηγός ελέγχων ασφάλειας OWASP

### Σχετικοί οδηγοί
- [Shodan](../../Shodan/translations/README.el.md)
- [Εξειδικευμένες αναζητήσεις GitHub (GitHub Dorking)](../../GitHub-Dorking/translations/README.el.md)
- [Subfinder](../../Subfinder/translations/README.el.md)

---

<p align="center">
  <b>🔍 Κατανοήστε τι έχει ευρετηριαστεί δημόσια</b><br>
  <i>Στοχευμένες αναζητήσεις με έλεγχο αποτελεσμάτων</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
