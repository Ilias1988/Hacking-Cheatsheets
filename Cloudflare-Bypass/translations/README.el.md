# ☁️ Cloudflare — Έλεγχος έκθεσης αρχικού server

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

## Πριν ξεκινήσετε

Το **Cloudflare** μπορεί να παρεμβάλλεται μεταξύ επισκέπτη και αρχικού server ως reverse proxy/CDN και να εφαρμόζει ελέγχους ασφάλειας. Ο οδηγός συγκεντρώνει πηγές για αναζήτηση πιθανής έκθεσης του αρχικού server και παραδείγματα ελέγχων ρυθμίσεων.

**Βασικοί όροι:** *origin IP* = διεύθυνση αρχικού server, *DNS history* = παλιότερες αντιστοιχίσεις ονομάτων, *favicon hash* = αποτύπωμα του εικονιδίου ιστοτόπου, *TLS/SNI* = μηχανισμοί κρυπτογράφησης και επιλογής ονόματος κατά τη σύνδεση, *challenge* = πρόκληση ελέγχου browser.

Ιστορικές IP, κοινά πιστοποιητικά ή ίδιο περιεχόμενο δίνουν υποψήφιες συσχετίσεις, όχι βέβαιη ταυτότητα ή άδεια σύνδεσης. Κάθε ενεργό βήμα χρειάζεται στόχο εντός των συμφωνημένων ορίων.

## Περιεχόμενα

- [🎯 Αναγνώριση ενδείξεων Cloudflare](#section-01)
- [🌐 Αναζήτηση IP του αρχικού server](#section-02)
- [🔥 Απευθείας σύνδεση](#section-03)
- [🛡️ Έλεγχοι ρυθμίσεων Cloudflare WAF](#section-04)
- [🛠️ Εργαλεία](#section-05)
- [📊 Γρήγορη αναφορά](#section-06)
- [📚 Πηγές](#section-07)

---

<a id="section-01"></a>

## 🎯 Αναγνώριση ενδείξεων Cloudflare

> **Ενδεικτικοί κατάλογοι:** Τα εύρη και οι κεφαλίδες του πρωτοτύπου δεν είναι πλήρης τρέχουσα απογραφή. Η εγγραφή 104.16.0.0/12 στην πρώτη λίστα είναι ευρύτερη από τα επιμέρους εύρη της τελικής λίστας. Χρησιμοποιήστε την [επίσημη τεκμηρίωση Cloudflare](https://developers.cloudflare.com/fundamentals/concepts/cloudflare-ip-addresses/) και τον επίσημο κατάλογο IP.


### Κεφαλίδες HTTP
```bash
curl -I https://target.com

# Αναζητήστε ενδείξεις όπως:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Ενδεικτικά εύρη IP Cloudflare από το πρωτότυπο:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

<a id="section-02"></a>

## 🌐 Αναζήτηση IP του αρχικού server

### 1. Εργαλεία ιστορικού DNS

| Υπηρεσία | Διεύθυνση ιστού (URL) |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Εργαλείο στο GitHub |

```bash
# Χρήση CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. Αναζήτηση υποτομέων
```bash
# Ορισμένοι υποτομείς μπορεί να αποκαλύπτουν σχετικές IP
subfinder -d target.com | httpx -ip

# Ενδεικτικά ονόματα υποτομέων για διασταύρωση:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. Αναζήτηση πιστοποιητικών SSL/TLS
```bash
# Αναζήτηση στο Censys.io
# Query: parsed.names: target.com AND NOT ip: 104.*

# Δημόσιες καταγραφές πιστοποιητικών
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Αναζήτηση στο Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. Ανάλυση email
```bash
# Παράδειγμα αποστολής email σε ανύπαρκτη διεύθυνση
# Εξέταση κεφαλίδων μηνύματος μη παράδοσης

# Εναλλακτικά, εγγραφή σε ενημερωτικά μηνύματα
# Εξέταση κεφαλίδων Received για πιθανή IP αρχικού server

# Εξέταση κεφαλίδων όπως:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

### 5. Αποτύπωμα favicon για Shodan
```python
import mmh3
import requests
import codecs

# Υπολογισμός αποτυπώματος favicon
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Αναζήτηση στο Shodan: http.favicon.hash:{hash}
```

```bash
# Εργαλείο γραμμής εντολών Shodan
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. Ιστορικό WHOIS
```bash
# Εξέταση ιστορικού WHOIS για παλιές συσχετίσεις IP
# whoxy.com
# domaintools.com
```

### 7. Πηγαίος κώδικας ιστοσελίδας
```bash
# Αναζήτηση σταθερά γραμμένων IP σε:
# - Αρχεία JavaScript
# - Σχόλια HTML
# - Σημεία εισόδου API
# - Μηνύματα σφάλματος

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Εξέταση αρχείων JavaScript
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. Εγγραφές MX
```bash
dig MX target.com

# Ο server email μπορεί να μοιράζεται IP με τον αρχικό server· χρειάζεται διασταύρωση
nslookup mail.target.com
```

### 9. Εγγραφές SPF
```bash
dig TXT target.com | grep spf

# Η εγγραφή SPF ενδέχεται να περιέχει σχετική IP
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

<a id="section-03"></a>

## 🔥 Απευθείας σύνδεση

> **Host, SNI και πιστοποιητικό:** Η αλλαγή του HTTP Host header δεν αλλάζει από μόνη της το SNI κατά τη σύνδεση TLS σε IP. Το -k παρακάμπτει την επαλήθευση πιστοποιητικού και μπορεί να κρύβει λάθος αντιστοίχιση. Ίδιο περιεχόμενο δεν αποτελεί οριστική επιβεβαίωση. Η αλλαγή /etc/hosts απαιτεί κατάλληλα δικαιώματα και παραμένει μέχρι να αναιρεθεί.


### Έλεγχος υποψήφιας IP
```bash
# Αφού βρεθεί υποψήφια IP αρχικού server:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Εναλλακτικό παράδειγμα αλλαγής του /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Κατόπιν δοκιμή με το όνομα domain
curl https://target.com
```

### Διασταύρωση υποψήφιου αρχικού server
```bash
# Σύγκριση περιεχομένου· ίδια απόκριση δεν αποδεικνύει αποκλειστική ταυτότητα
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Εξέταση πιστοποιητικού SSL/TLS
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

<a id="section-04"></a>

## 🛡️ Έλεγχοι ρυθμίσεων Cloudflare WAF

> **Δεν εγγυώνται παράκαμψη:** User-Agent, προσομοίωση TLS, αυτοματισμός browser και δηλωμένες IP δεν εξασφαλίζουν παράκαμψη WAF, bot protection ή Turnstile. Η συμπεριφορά εξαρτάται από ρυθμίσεις και έκδοση· οι παλιές υπηρεσίες και τα εργαλεία μπορεί να μην είναι διαθέσιμα.


### Έλεγχος εφαρμογής ορίων ρυθμού
```http
# Παραλλαγές κεφαλίδων· δεν εγγυώνται αλλαγή της πραγματικής ταυτότητας πελάτη
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

### Έλεγχος προστασίας από αυτοματισμούς
```bash
# Χρήση User-Agent browser
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Χρήση curl-impersonate
curl_chrome116 https://target.com
```

### Έλεγχος προκλήσεων Turnstile και browser
```bash
# Χρήση αυτοματισμού browser
# - Puppeteer + stealth plugin
# - Playwright
# - undetected-chromedriver

# FlareSolverr μέσω Docker
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

<a id="section-05"></a>

## 🛠️ Εργαλεία

| Εργαλείο | Σκοπός |
|------|---------|
| **CloudFlair** | Αναζήτηση υποψήφιου αρχικού server μέσω Censys |
| **CrimeFlare** | Βάση υποψήφιων IP αρχικών servers |
| **Bypass-Firewalls-by-DNS-History** | Έλεγχος ιστορικού DNS |
| **FlareSolverr** | Αυτοματοποιημένη διαχείριση προκλήσεων browser |
| **curl-impersonate** | Προσομοίωση αποτυπώματος TLS browser |
| **CloudBunny** | Εργαλείο ελέγχων έκθεσης πίσω από Cloudflare |

### Χρήση CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Ορισμός στοιχείων πρόσβασης API Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

<a id="section-06"></a>

## 📊 Γρήγορη αναφορά

### Λίστα ελέγχου αρχικού server
```markdown
- [ ] Ιστορικό DNS (SecurityTrails, ViewDNS)
- [ ] Υποτομείς (mail, ftp, cpanel, dev)
- [ ] Πιστοποιητικά SSL/TLS (Censys, crt.sh)
- [ ] Shodan (αποτύπωμα favicon, SSL/TLS)
- [ ] Κεφαλίδες email
- [ ] Εγγραφές MX/SPF
- [ ] Ανάλυση πηγαίου κώδικα
- [ ] Ιστορικό WHOIS
- [ ] Σύγκριση με επίσημα εύρη IP Cloudflare
```

### Εύρη IP Cloudflare
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

<a id="section-07"></a>

## 📚 Πηγές

- [Επίσημα εύρη IP Cloudflare](https://www.cloudflare.com/ips/)
- [Εργαλείο CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Διασταυρώστε τον αρχικό server!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
