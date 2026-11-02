# 🌐 Shodan — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   Μηχανή αναζήτησης συσκευών συνδεδεμένων στο Διαδίκτυο
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Αναζήτηση καταγεγραμμένων υπηρεσιών και υποδομών Διαδικτύου</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **Shodan** αναζητά πληροφορίες που έχει συλλέξει για υπηρεσίες στο Διαδίκτυο. Το **banner** είναι μια καταγραφή απόκρισης υπηρεσίας, το **filter** περιορίζει πεδία της καταγραφής και το **hostname** είναι όνομα που σχετίζεται με έναν host. **ASN** και **CIDR** περιγράφουν οργανώσεις δικτύων και εύρη IP.

Η αναζήτηση στο ευρετήριο διαφέρει από την υποβολή νέας ενεργής σάρωσης. Εξετάστε ημερομηνία καταγραφής, πραγματική συσχέτιση με τον οργανισμό και δικαιώματα του λογαριασμού σας. Ένας τίτλος, μια θύρα ή ένα CVE στην έξοδο αποτελεί ένδειξη για διερεύνηση, όχι αυτόματη επιβεβαίωση. Οι αγγλικές συμβολοσειρές ερωτημάτων και οι εντολές διατηρούνται αυτούσιες.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Πρώτα βήματα](#section-02)
- [Βασικά φίλτρα](#section-03)
- [Φίλτρα δικτύου](#section-04)
- [Φίλτρα SSL/TLS](#section-05)
- [Φίλτρα HTTP](#section-06)
- [Ενδείξεις πιθανώς ευάλωτων υπηρεσιών](#section-07)
- [IoT και βιομηχανικά συστήματα ICS/SCADA](#section-08)
- [Βάσεις δεδομένων](#section-09)
- [Κάμερες και πολυμέσα](#section-10)
- [Υπηρεσίες νέφους (Cloud)](#section-11)
- [Ερωτήματα Bug Bounty](#section-12)
- [Γραμμή εντολών Shodan](#section-13)
- [Γρήγορη αναφορά](#section-14)
- [Συμβουλές και καλές πρακτικές](#section-15)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-16)
- [Πηγές](#section-17)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

Το **Shodan** αναζητά καταγεγραμμένες πληροφορίες υπηρεσιών υπολογιστών, servers και συσκευών IoT με χρήση φίλτρων.

### Τι μπορείτε να εντοπίσετε;

| Κατηγορία | Παραδείγματα |
| ---------- | ---------- |
| **Servers — εξυπηρετητές** | Web servers, FTP, SSH και βάσεις δεδομένων |
| **Συσκευές IoT** | Κάμερες, δρομολογητές και έξυπνες συσκευές |
| **ICS/SCADA** | Συστήματα βιομηχανικού ελέγχου |
| **Υπηρεσίες νέφους (Cloud)** | Πόροι υποδομών AWS, Azure και GCP |
| **Πιθανές αδυναμίες** | Εκτεθειμένες υπηρεσίες και ενδείξεις προεπιλεγμένων στοιχείων |

### Διευθύνσεις υπηρεσιών Shodan

| Υπηρεσία | Διεύθυνση ιστού (URL) |
| --------- | ----- |
| **Αναζήτηση web** | https://www.shodan.io |
| **Χάρτες** | https://maps.shodan.io |
| **Αναζήτηση δημοσιευμένων exploits** | https://exploits.shodan.io |
| **Εικόνες** | https://images.shodan.io |

---

<a id="section-02"></a>

## 🚀 Πρώτα βήματα

### Δημιουργία λογαριασμού

1. Επισκεφθείτε το https://www.shodan.io
2. Δημιουργήστε λογαριασμό σύμφωνα με τις διαθέσιμες επιλογές
3. Βρείτε το κλειδί API στις ρυθμίσεις λογαριασμού

### Ενδεικτικά πακέτα του πρωτοτύπου

> **Ιστορικός πίνακας:** Οι τιμές και οι παροχές διατηρούνται ως στοιχεία του αγγλικού πρωτοτύπου. Δεν αποτελούν σημερινό τιμοκατάλογο. Διαθεσιμότητα φίλτρων, credits, λήψεις και όρια API εξαρτώνται από τον λογαριασμό· η περιγραφή «απεριόριστη πρόσβαση» χρειάζεται έλεγχο στους πραγματικούς όρους.


| Πακέτο | Κόστος | Δυνατότητες |
| ------ | ------- | ---------- |
| **Δωρεάν** | $0 | Βασική αναζήτηση με περιορισμένα αποτελέσματα κατά το πρωτότυπο |
| **Membership** | $49 | Περισσότερα αποτελέσματα, λήψεις και CLI κατά το πρωτότυπο |
| **Small Business** | $299/mo | Περισσότερα credits και παρακολούθηση κατά το πρωτότυπο |
| **Corporate** | $899/mo | Περιγραφή πρωτοτύπου· τα πραγματικά όρια χρειάζονται επιβεβαίωση |

### Κλειδί API

```bash
# Λήψη από: https://account.shodan.io
# Εξαγωγή μεταβλητής περιβάλλοντος· ελέγξτε πώς τη χρησιμοποιεί ο client
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="section-03"></a>

## 🔧 Βασικά φίλτρα

### Αναζήτηση κειμένου

```
# Απλή αναζήτηση
apache
nginx
Microsoft-IIS

# Αναζήτηση προϊόντος
product:apache
product:nginx
product:openssh
```

### Χώρα και πόλη

```
# Χώρα με κωδικό ISO 3166-1 alpha-2
country:US
country:DE
country:RU
country:CN
country:GR                      # Ελλάδα

# Πόλη
city:Athens
city:"New York"
city:London
city:Tokyo

# Συνδυασμός
country:US city:Miami
country:GR city:Thessaloniki
```

### Οργανισμός και πάροχος πρόσβασης

```
# Οργανισμός
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Όνομα host και domain

```
# Το hostname περιλαμβάνει
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Συγκεκριμένο domain στο πεδίο πιστοποιητικού
ssl.cert.subject.cn:target.com
```

### Λειτουργικό σύστημα

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="section-04"></a>

## 🌐 Φίλτρα δικτύου

### Θύρα

```
# Συγκεκριμένη θύρα
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# Πολλαπλές θύρες
port:22,80,443
```

### IP και δίκτυο

> **Εμβέλεια διευθύνσεων:** Τα ιδιωτικά εύρη του παραδείγματος δεν αντιστοιχούν σε δημόσια σαρώσιμες διευθύνσεις. ASN, ISP και cloud provider μπορεί να καλύπτουν πολλούς πελάτες και δεν ορίζουν το scope ενός οργανισμού.


```
# Συγκεκριμένη IP
ip:8.8.8.8

# Εύρος IP με CIDR
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Γεωγραφική τοποθεσία

```
# Γεωγραφικές συντεταγμένες
geo:37.9838,23.7275             # Συντεταγμένες Αθήνας
geo:40.7128,-74.0060,10         # Νέα Υόρκη με ακτίνα

# Περιοχή ή πολιτεία
region:California
region:"New York"
```

---

<a id="section-05"></a>

## 🔐 Φίλτρα SSL/TLS

### Πληροφορίες πιστοποιητικού

> **Δεν γίνεται σύγκριση πεδίων έτσι:** Το αρχικό ερώτημα issuer.cn:subject.cn αναζητά κυριολεκτική τιμή, δεν συγκρίνει αυτόματα εκδότη και θέμα. Δεν αποτελεί αξιόπιστο φίλτρο αυτοϋπογραφής. Ελέγξτε τα υποστηριζόμενα πεδία στην [αναφορά φίλτρων Shodan](https://www.shodan.io/search/filters).


```
# Θέμα πιστοποιητικού
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Εκδότης πιστοποιητικού
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Παράδειγμα αυτοϋπογραφής του πρωτοτύπου· δείτε σημείωση
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### Εκδόσεις SSL/TLS

```
# Παλιές εκδόσεις SSL/TLS προς αξιολόγηση
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Νεότερες εκδόσεις TLS· δεν εγγυώνται συνολική ασφάλεια
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Λήξη πιστοποιητικού

> **Σταθερό έτος και διαθεσιμότητα:** Το 2024 είναι ιστορικό παράδειγμα και δεν σημαίνει «λήγει σύντομα». Η ύπαρξη πεδίου στα δεδομένα API δεν εγγυάται ότι υποστηρίζεται και ως φίλτρο αναζήτησης με την ίδια ονομασία.


```
# Ληγμένα πιστοποιητικά
ssl.cert.expired:true

# Παράδειγμα ημερομηνίας λήξης· το σταθερό έτος δεν σημαίνει σύντομα
ssl.cert.expires:2024
```

### Ενδείξεις αδυναμιών SSL/TLS

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="section-06"></a>

## 🌍 Φίλτρα HTTP

### Κωδικοί απόκρισης και κεφαλίδες HTTP

```
# Κωδικός απόκρισης HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Τίτλος σελίδας HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Περιεχόμενο HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Τεχνολογίες web

```
# Κεφαλίδα Server
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Συγκεκριμένες εκδόσεις
http.server:"Apache/2.4.49"     # Ένδειξη έκδοσης που χρειάζεται αξιολόγηση
http.server:"nginx/1.18"

# Hash εικονιδίου ιστοτόπου (Favicon)
http.favicon.hash:116323821     # Συγκεκριμένο favicon
http.favicon.hash:-1137975059   # Jenkins
```

### Συστατικά λογισμικού

```
# Frameworks web
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Τεχνολογίες
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="section-07"></a>

## ⚠️ Ενδείξεις πιθανώς ευάλωτων υπηρεσιών

> **Αξιολόγηση αποτελέσματος:** Η απουσία της λέξης authentication δεν αποδεικνύει έλλειψη ταυτοποίησης. Μια θύρα, έκδοση ή ετικέτα CVE χρειάζεται εξέταση του banner και του χρόνου συλλογής. Δείτε πώς το Shodan [οργανώνει και αναζητά banners](https://help.shodan.io/the-basics/search-query-fundamentals).


### Γνωστές ευπάθειες με αναγνωριστικό CVE

```
# Αναζήτηση με CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Πολλαπλά CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

### Προεπιλεγμένα στοιχεία σύνδεσης

```
# Αναφορές προεπιλεγμένων κωδικών
"default password"
"admin" "password"
http.title:"Login" "admin"

# Συγκεκριμένα προεπιλεγμένα ζεύγη
"admin:admin"
"root:root"
"admin:123456"
```

### Ανοιχτές ή εκτεθειμένες υπηρεσίες

```
# Ενδείξεις ανώνυμου FTP
port:21 "Anonymous access granted"

# Ενδείξεις έκθεσης Telnet
port:23 -authentication

# Ενδείξεις έκθεσης MongoDB
port:27017 "MongoDB Server Information"

# Ενδείξεις έκθεσης Redis
port:6379 -authentication

# Ενδείξεις έκθεσης Elasticsearch
port:9200 "cluster_name"

# Ενδείξεις έκθεσης Memcached
port:11211 "STAT"
```

### Εκτεθειμένες σελίδες διαχείρισης

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Ένδειξη Jenkins· η απουσία ταυτοποίησης χρειάζεται επιβεβαίωση
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="section-08"></a>

## 🏭 IoT και βιομηχανικά συστήματα ICS/SCADA

### Συστήματα βιομηχανικού ελέγχου

```
# Γενική κατηγορία βιομηχανικών συστημάτων
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### Συστήματα SCADA

```
# SCADA
tag:scada

# Συγκεκριμένες αναφορές SCADA
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### Συσκευές IoT

```
# Γενική κατηγορία IoT
tag:iot

# Δρομολογητές
"router" port:80
http.title:"Router"

# Εκτυπωτές
port:9100
"printer"
http.title:"HP"

# Έξυπνο σπίτι
"smart home"
"IoT Gateway"
```

### Συσκευές δικτύου

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="section-09"></a>

## 💾 Βάσεις δεδομένων

### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="section-10"></a>

## 📹 Κάμερες και πολυμέσα

### Δικτυακές κάμερες

```
# Γενικές αναφορές καμερών
webcam
"webcam" country:US

# Κατασκευαστές καμερών
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Ροές RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

### Συστήματα καταγραφής DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Ροές πολυμέσων

```
# Ροές VLC
"VLC media player"

# Servers πολυμέσων
product:plex
"Plex Media Server"
```

### Συγκεκριμένες διεπαφές καμερών

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="section-11"></a>

## ☁️ Υπηρεσίες νέφους (Cloud)

### Amazon AWS

```
# Γενικά στοιχεία AWS
org:"Amazon"
asn:AS16509

# Κάδοι αποθήκευσης S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Υπηρεσίες AWS
http.title:"AWS"
```

### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

### Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

### Εκτεθειμένες σελίδες διαχείρισης cloud

```
# Kubernetes Dashboard
http.title:"Kubernetes Dashboard"

# Εγκατάσταση μέσω Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="section-12"></a>

## 🐛 Ερωτήματα Bug Bounty

### Συλλογή πληροφοριών οργανισμού

```
# Οργανισμός στόχος
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Αναζήτηση ASN
asn:AS12345
net:1.2.3.0/24
```

### Αναζήτηση εκτεθειμένων υπηρεσιών

```
# Επιλεγμένες θύρες για τον στόχο
org:"Target" port:22,80,443,3306,5432,27017

# Σελίδες διαχείρισης
org:"Target" http.title:"admin"

# Σελίδες σύνδεσης
org:"Target" http.title:"login"
```

### Αναζήτηση υποτομέων

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Ενδείξεις πιθανώς ευάλωτων υπηρεσιών

> **Αξιολόγηση αποτελέσματος:** Η απουσία της λέξης authentication δεν αποδεικνύει έλλειψη ταυτοποίησης. Μια θύρα, έκδοση ή ετικέτα CVE χρειάζεται εξέταση του banner και του χρόνου συλλογής. Δείτε πώς το Shodan [οργανώνει και αναζητά banners](https://help.shodan.io/the-basics/search-query-fundamentals).


```
# Έλεγχος ενδείξεων CVE
org:"Target" vuln:CVE-2021-44228

# Ενδείξεις προεπιλεγμένων στοιχείων
org:"Target" "default password"

# Ενδείξεις έκθεσης βάσεων δεδομένων
org:"Target" port:27017,6379,9200
```

### Συνδυαστικό ερώτημα Bug Bounty

> **Σύνθετη σύνταξη:** Μη μεταφέρετε αυτόματα OR, διπλές κάθετες και παρενθέσεις από άλλη μηχανή αναζήτησης στο Shodan. Ελέγξτε τη σημασία του πραγματικού ερωτήματος και των συνδυασμών φίλτρων στην επίσημη τεκμηρίωση. Τα αρχικά παραδείγματα δεν αποτελούν εγγυημένα έγκυρη καθολική σύνταξη.


```
# Συνδυαστική συλλογή πληροφοριών
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Με φίλτρα
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="section-13"></a>

## 💻 Γραμμή εντολών Shodan

> **Αναζήτηση και σάρωση διαφέρουν:** Η υποβολή scan ζητά νέα ενεργή συλλογή. Το shodan init ρυθμίζει το κλειδί του CLI· μια απλή μεταβλητή περιβάλλοντος δεν υποκαθιστά κάθε ρύθμιση. Εντολές και δικαιώματα χρειάζονται έλεγχο ανά έκδοση και λογαριασμό. Βλ. [επίσημο Shodan CLI](https://help.shodan.io/command-line-interface/0-installation).


### Εγκατάσταση

```bash
# Εγκατάσταση μέσω pip
pip install shodan

# Αρχικοποίηση με κλειδί API
shodan init YOUR_API_KEY
```

### Βασικές εντολές

```bash
# Στοιχεία λογαριασμού
shodan info

# Search — αναζήτηση
shodan search "apache"

# Αναζήτηση με φίλτρα
shodan search "port:22 country:US"

# Καταμέτρηση αποτελεσμάτων
shodan count "nginx"

# Λήψη αποτελεσμάτων
shodan download results.json.gz "apache"

# Ανάλυση αποθηκευμένων αποτελεσμάτων
shodan parse results.json.gz

# Πληροφορίες host
shodan host 8.8.8.8
```

### Προχωρημένες επιλογές γραμμής εντολών

```bash
# Πληροφορίες domain
shodan domain target.com

# Υποβολή ενεργής σάρωσης· απαιτούνται προϋποθέσεις και credits
shodan scan submit 1.2.3.4

# Εκτίμηση honeypot· δεν αποτελεί βέβαιη ανίχνευση
shodan honeyscore 1.2.3.4

# Πληροφορίες δικής σας IP
shodan myip

# Στατιστικά
shodan stats "apache" --facets country,org
```

### Χρήση API με Python

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Search — αναζήτηση
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Αναζήτηση host
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="section-14"></a>

## 📊 Γρήγορη αναφορά

### Βασικά φίλτρα αναφοράς

| Φίλτρο | Περιγραφή | Παράδειγμα |
| -------- | ------------- | --------- |
| `port:` | Αριθμός θύρας | `port:22` |
| `country:` | Κωδικός χώρας | `country:US` |
| `city:` | Όνομα πόλης | `city:London` |
| `org:` | Οργανισμός | `org:"Google"` |
| `hostname:` | Όνομα host | `hostname:google.com` |
| `net:` | Εύρος IP με γραφή CIDR | `net:10.0.0.0/8` |
| `os:` | Λειτουργικό σύστημα | `os:"Windows"` |
| `product:` | Όνομα προϊόντος | `product:nginx` |
| `version:` | Έκδοση | `version:"2.4.49"` |
| `vuln:` | Αναγνωριστικό CVE | `vuln:CVE-2021-44228` |

### Φίλτρα HTTP

| Φίλτρο | Περιγραφή | Παράδειγμα |
| -------- | ------------- | --------- |
| `http.title:` | Τίτλος σελίδας | `http.title:"Admin"` |
| `http.status:` | Κωδικός απόκρισης HTTP | `http.status:200` |
| `http.server:` | Κεφαλίδα Server | `http.server:nginx` |
| `http.html:` | Περιεχόμενο HTML | `http.html:"login"` |
| `http.component:` | Technology — τεχνολογία | `http.component:"WordPress"` |

### Φίλτρα SSL/TLS

| Φίλτρο | Περιγραφή | Παράδειγμα |
| -------- | ------------- | --------- |
| `ssl.cert.subject.cn:` | Πεδίο CN θέματος πιστοποιητικού | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Πεδίο CN εκδότη | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Έκδοση SSL/TLS | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Ληγμένο πιστοποιητικό | `ssl.cert.expired:true` |

### Δέκα ενδεικτικά ερωτήματα Shodan

| # | Ερώτημα | Σκοπός |
| --- | ------- | --------- |
| 1 | `port:22 country:US` | SSH σε διευθύνσεις που αποδίδονται στις ΗΠΑ |
| 2 | `port:3389 country:US` | RDP σε διευθύνσεις που αποδίδονται στις ΗΠΑ |
| 3 | `port:27017 -authentication` | Ενδείξεις έκθεσης MongoDB |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Πίνακες ελέγχου |
| 6 | `"default password"` | Ενδείξεις προεπιλεγμένων στοιχείων |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | Δικτυακές κάμερες |
| 9 | `product:apache vuln:` | Ενδείξεις προς διερεύνηση για Apache |
| 10 | `ssl.cert.expired:true` | Ληγμένα πιστοποιητικά |

---

<a id="section-15"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

1. **Ξεκινήστε από στοιχεία πιστοποιητικού**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Ελέγξτε τις επιλεγμένες θύρες**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Αναζητήστε εκτεθειμένες υπηρεσίες**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Ελέγξτε ενδείξεις ευπαθειών**
   ```
   org:"Target" vuln:
   ```

### Συνδυάστε φίλτρα

```
# Σύνθετα ερωτήματα
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

<a id="section-16"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Το Shodan εμφανίζει καταγεγραμμένες πληροφορίες έκθεσης. Η πρόσβαση σε κάποια υπηρεσία απαιτεί ξεχωριστή εξουσιοδότηση.
> 
> - ✅ Χρήση για εγκεκριμένη συλλογή πληροφοριών
> - ✅ Χρήση για στόχους που περιλαμβάνονται στο scope
> - ✅ Υπεύθυνη αναφορά ευρημάτων
> - ❌ Όχι εκμετάλλευση εκτός εγκεκριμένου ελέγχου
> - ❌ Όχι πρόσβαση σε συστήματα χωρίς άδεια
> - ❌ Σεβασμός ιδιωτικότητας και εφαρμοστέων κανόνων
> 
> **Πραγματοποιείτε ενεργή σάρωση μόνο με την απαραίτητη εξουσιοδότηση.**

---

<a id="section-17"></a>

## 📚 Πηγές

### Επίσημες πηγές
- [Shodan](https://www.shodan.io)
- [Shodan Help](https://help.shodan.io)
- [Γραμμή εντολών Shodan](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Σχετικοί οδηγοί
- [Εξειδικευμένες αναζητήσεις Google (Google Dorking)](../../Google-Dorking/translations/README.el.md)
- [Εξειδικευμένες αναζητήσεις GitHub (GitHub Dorking)](../../GitHub-Dorking/translations/README.el.md)
- [Nmap](../../Nmap/translations/README.el.md)

---

<p align="center">
  <b>🌐 Εξετάστε τις πληροφορίες έκθεσης υπηρεσιών</b><br>
  <i>Συλλογή πληροφοριών με Shodan</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
