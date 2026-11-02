# 🌐 OSINT — Έρευνα domain και διευθύνσεων IP

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="../Domain-IP-OSINT.md">🇬🇧 English</a> • <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe</a>
</p>

[🔍 Ελληνική επισκόπηση OSINT](README.el.md)

---

## Πριν ξεκινήσετε

Το **domain** είναι όνομα διαδικτυακού τομέα και το **subdomain** υποτομέας του. Το **DNS** συνδέει ονόματα με διαφορετικούς τύπους εγγραφών: **A** για IPv4, **MX** για email, **TXT** για κείμενο και **NS** για εξυπηρετητές ονομάτων. Το **WHOIS** παρέχει πληροφορίες καταχώρισης, που μπορεί να είναι περιορισμένες ή να διαφέρουν ανά μητρώο.

Το **ASN** είναι ο αριθμός ενός αυτόνομου συστήματος, δηλαδή μιας διοικητικά οργανωμένης ομάδας δικτύων. Η **γεωεντόπιση IP** είναι εκτίμηση περιοχής, όχι αξιόπιστος προσδιορισμός κατοικίας ή προσώπου· δείτε τον [περιορισμό ακρίβειας που εξηγεί η MaxMind](https://dev.maxmind.com/geoip/docs/databases/city-and-country/).

Ξεκινήστε με στοιχεία μητρώων και δημόσιων ευρετηρίων και διαχωρίστε τα από αιτήματα που στέλνετε στην ίδια την υποδομή. Τα δεδομένα ιστορικού ή σάρωσης μπορεί να είναι παλαιότερα από την τρέχουσα κατάσταση. Ένα κοινό IP, shared hosting ή CDN δεν αποδεικνύει κοινό ιδιοκτήτη όλων των domain. Οι ενδεικτικές διευθύνσεις και τα domain του πρωτοτύπου χρειάζονται αντικατάσταση για τον δικό σας έλεγχο.

## Περιεχόμενα

- [🔍 Εργαλεία συλλογής πληροφοριών για domain](#section-01)
- [🛠️ Εντολές](#section-02)
- [🔎 Συλλογή πληροφοριών για IP](#section-03)
- [📋 Λίστα ελέγχου domain](#section-04)

<a id="section-01"></a>

## 🔍 Εργαλεία συλλογής πληροφοριών για domain

| Εργαλείο | URL | Χρήση |
| ------ | ----- | --------- |
| **WHOIS** | who.is | Στοιχεία καταχώρισης domain |
| **SecurityTrails** | securitytrails.com | Ιστορικές εγγραφές DNS |
| **ViewDNS** | viewdns.info | Εργαλεία διερεύνησης DNS |
| **BuiltWith** | builtwith.com | Τεχνολογίες που χρησιμοποιούνται |
| **Wappalyzer** | wappalyzer.com | Εντοπισμός τεχνολογιών |

---

<a id="section-02"></a>

## 🛠️ Εντολές

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Διερεύνηση εγγραφών DNS

Το ANY δεν εγγυάται πλήρη επιστροφή όλων των τύπων εγγραφών· δείτε το [RFC 8482 για περιορισμένες απαντήσεις ANY](https://www.rfc-editor.org/rfc/rfc8482). Η μεταφορά ζώνης AXFR επικοινωνεί με εξυπηρετητή DNS και μπορεί να απορρίπτεται από την πολιτική του. Η επιτυχία ή η αποτυχία ενός αιτήματος δεν αποδεικνύει πλήρη απογραφή της υποδομής.

```bash
# Εγγραφές DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Δοκιμή μεταφοράς ζώνης DNS (zone transfer)
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Εντοπισμός υποτομέων (subdomains)
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

<a id="section-03"></a>

## 🔎 Συλλογή πληροφοριών για IP

| Εργαλείο | URL | Χρήση |
| ------ | ----- | --------- |
| **Shodan** | shodan.io | Αναζήτηση συσκευών και υπηρεσιών |
| **Censys** | censys.io | Δεδομένα σάρωσης υποδομών του διαδικτύου |
| **IPinfo** | ipinfo.io | Εκτίμηση γεωγραφικής θέσης IP |
| **AbuseIPDB** | abuseipdb.com | Ενδείξεις φήμης IP |

### Γραμμή εντολών Shodan
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### Αναζήτηση στοιχείων IP
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

<a id="section-04"></a>

## 📋 Λίστα ελέγχου domain

```markdown
□ Στοιχεία WHOIS
□ Εγγραφές DNS (A, MX, TXT, NS)
□ Υποτομείς (subdomains)
□ Ιστορικό DNS μέσω SecurityTrails
□ Τεχνολογίες που χρησιμοποιούνται
□ Πληροφορίες πιστοποιητικών SSL/TLS
□ Περιοχές διευθύνσεων IP
□ Αναζήτηση domain που συσχετίζονται με IP
□ Πληροφορίες αριθμού αυτόνομου συστήματος (ASN)
```

---

**Επιστροφή:** [🔍 Ελληνική επισκόπηση OSINT](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
