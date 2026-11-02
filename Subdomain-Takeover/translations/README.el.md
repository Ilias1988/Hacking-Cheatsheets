# 🎯 Subdomain Takeover — Ανάληψη εγκαταλειμμένων υποτομέων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## Πριν ξεκινήσετε

Το **subdomain takeover** αφορά την πιθανή ανάληψη της υπηρεσίας στην οποία εξακολουθεί να δείχνει ένα έμπιστο όνομα. Η διάγνωση απαιτεί δύο διαφορετικά στοιχεία: εγκαταλειμμένη σύνδεση και πραγματική δυνατότητα ενός τρίτου να συνδέσει το όνομα στον δικό του πόρο.

**Βασικοί όροι:** *CNAME* = εγγραφή DNS που συνδέει ένα όνομα με άλλο, *dangling record* = εγγραφή που παραπέμπει σε καταργημένο πόρο, *fingerprint* = χαρακτηριστικό μήνυμα υπηρεσίας, *custom domain* = προσαρμοσμένο όνομα domain, *PoC* = περιορισμένη απόδειξη προβλήματος.

Ξεκινήστε με συλλογή και διασταύρωση ενδείξεων. Τα παραδείγματα δημιουργίας πόρων αλλάζουν εξωτερική κατάσταση· αποτελούν εργαστηριακά σενάρια και δεν πρέπει να χρησιμοποιούνται για πραγματική ανάληψη χωρίς τη σχετική άδεια του προγράμματος.

## Περιεχόμενα

- [🎯 Τι είναι το subdomain takeover](#section-01)
- [🔍 Μεθοδολογία αναγνώρισης](#section-02)
- [🛠️ Αυτοματοποιημένα εργαλεία](#section-03)
- [📋 Υπηρεσίες και ενδεικτικά αποτυπώματα](#section-04)
- [🔥 Βήματα επίδειξης σε εγκεκριμένο περιβάλλον](#section-05)
- [📊 Γρήγορη αναφορά](#section-06)
- [🛠️ Σύνοψη εργαλείων](#section-07)
- [📚 Πηγές](#section-08)

---

<a id="section-01"></a>

## 🎯 Τι είναι το subdomain takeover

Το **subdomain takeover** μπορεί να συμβεί όταν ένας υποτομέας συνεχίζει να δείχνει σε καταργημένο εξωτερικό πόρο και ο πάροχος επιτρέπει σε τρίτο να αναλάβει τη σχετική σύνδεση. Μια εγκαταλειμμένη εγγραφή CNAME δεν αρκεί από μόνη της.

### Επίπτωση
- 🔴 **Phishing** — δυνατότητα φιλοξενίας παραπλανητικού περιεχομένου
- 🔴 **Έκθεση cookies** — ανάλογα με Domain, Path, HttpOnly και πραγματική χρήση
- 🔴 **Επίδραση σε λογαριασμούς** — όταν επηρεάζονται επιτρεπόμενοι προορισμοί OAuth
- 🔴 **Βλάβη φήμης** — κακόβουλο περιεχόμενο σε έμπιστο όνομα

---

<a id="section-02"></a>

## 🔍 Μεθοδολογία αναγνώρισης

### Βήμα 1: Αναζήτηση υποτομέων
```bash
# Συνδυασμός εργαλείων για ευρύτερη κάλυψη
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Συνένωση και αφαίρεση διπλοτύπων
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Βήμα 2: Εύρεση εγγραφών CNAME
```bash
# Εξέταση εγγραφών DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Εναλλακτικά, χρήση dnsrecon
dnsrecon -d target.com -t crt
```

### Βήμα 3: Έλεγχος εγκαταλειμμένων εγγραφών CNAME
```bash
# Χειροκίνητος έλεγχος
dig CNAME subdomain.target.com +short
# Ενδεικτική απόκριση: something.cloudfront.net
# Έπειτα ελέγξτε αν ο σχετικός πόρος είναι πράγματι διαθέσιμος για ανάληψη

# httpx για κωδικούς απόκρισης
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

<a id="section-03"></a>

## 🛠️ Αυτοματοποιημένα εργαλεία

### Subjack
```bash
# Εγκατάσταση
go install github.com/haccer/subjack@latest

# Έλεγχος
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Με αποτυπώματα αναγνώρισης
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei με πρότυπα ελέγχων takeover
```bash
# Χρήση προτύπων Nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Επιλεγμένοι έλεγχοι takeover
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Δείτε το αποθετήριο GitHub για αποτυπώματα αναγνώρισης
# https://github.com/EdOverflow/can-i-take-over-xyz

# Χρήση αποτυπωμάτων χειροκίνητα ή μέσω εργαλείων
```

---

<a id="section-04"></a>

## 📋 Υπηρεσίες και ενδεικτικά αποτυπώματα

> **Ιστορικά παραδείγματα, όχι κατάλογος επιβεβαιωμένων ευπαθειών:** Τα μηνύματα σφάλματος διατηρούνται ακριβώς για αναγνώριση, αλλά δεν αρκούν για takeover. Έλεγχοι κυριότητας, δεσμευμένα ονόματα και πολιτικές παρόχων μπορεί να αποτρέπουν την ανάληψη. Δείτε ενδεικτικά την [επαλήθευση domain GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages) και τους [περιορισμούς εναλλακτικών ονομάτων CloudFront](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html).


### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Αποτύπωμα: "NoSuchBucket"
Αποτύπωμα: "The specified bucket does not exist"

Ανάληψη κατά το πρωτότυπο: δημιουργία bucket με ίδιο όνομα, εφόσον ο πάροχος το επιτρέπει
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Αποτύπωμα: "Bad Request"
Αποτύπωμα: "ERROR: The request could not be satisfied"

Ανάληψη κατά το πρωτότυπο: σύνδεση ίδιου εναλλακτικού domain σε διανομή CloudFront· απαιτεί έλεγχο των περιορισμών του παρόχου
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Αποτύπωμα: "There isn't a GitHub Pages site here"

Περιγραφή ανάληψης του πρωτοτύπου:
1. Δημιουργία repo username.github.io
2. Προσθήκη αρχείου CNAME με τον υποτομέα
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Αποτύπωμα: "No such app"
Αποτύπωμα: "There's nothing here, yet"

Ανάληψη κατά το πρωτότυπο: δημιουργία εφαρμογής Heroku με ίδιο όνομα
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Αποτύπωμα: "404 - Web Site not found"

Ανάληψη κατά το πρωτότυπο: δημιουργία πόρου με ίδιο όνομα
```

### Shopify
```
CNAME: shops.myshopify.com

Αποτύπωμα: "Sorry, this shop is currently unavailable"
Αποτύπωμα: "Only one step left"

Ανάληψη κατά το πρωτότυπο: δημιουργία καταστήματος Shopify και σύνδεση domain
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Αποτύπωμα: "Do you want to register *.wordpress.com?"

Ανάληψη κατά το πρωτότυπο: καταχώριση υποτομέα στο WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Αποτύπωμα: "Help Center Closed"
Αποτύπωμα: "This help center no longer exists"

Η ανάληψη δεν είναι πάντα δυνατή· εξαρτάται από τη ρύθμιση και τους ελέγχους Zendesk
```

### Fastly
```
CNAME: xxx.fastly.net

Αποτύπωμα: "Fastly error: unknown domain"

Ανάληψη κατά το πρωτότυπο: σύνδεση domain σε υπηρεσία Fastly
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Αποτύπωμα: "The gods are wise but do not know of the site"
Αποτύπωμα: "404 error unknown site!"

Ανάληψη κατά το πρωτότυπο: προσθήκη ιστοτόπου στο Pantheon
```

---

<a id="section-05"></a>

## 🔥 Βήματα επίδειξης σε εγκεκριμένο περιβάλλον

> **Οι ακολουθίες δεν είναι πλήρης επιβεβαίωση:** Τα παραδείγματα GitHub/S3 παραλείπουν προϋποθέσεις παρόχου, ρυθμίσεις domain, τύπο endpoint και πρόσβαση. Το NoSuchBucket δεν αποδεικνύει ότι μπορείτε να καταχωρίσετε το όνομα. Η απλή μεταφόρτωση αρχείου δεν εξασφαλίζει ότι θα εξυπηρετείται από τον υποτομέα.


### Παράδειγμα ανάληψης GitHub Pages

```bash
# 1. Εντοπισμός υποτομέα με ενδείξεις πιθανής ανάληψης
dig CNAME blog.target.com
# Ενδεικτική απόκριση: target.github.io, χωρίς αντίστοιχο repo

# 2. Παράδειγμα δημιουργίας αποθετηρίου GitHub
# Ενδεικτικό όνομα: yourusername/target ή org/target

# 3. Ενεργοποίηση GitHub Pages στις ρυθμίσεις του repo

# 4. Προσθήκη αρχείου CNAME με περιεχόμενο:
blog.target.com

# 5. Αναμονή για ενημέρωση DNS και υπηρεσίας

# 6. Επαλήθευση της συγκεκριμένης σύνδεσης
curl https://blog.target.com
```

### Παράδειγμα ανάληψης ονόματος S3

```bash
# 1. Έλεγχος ενδείξεων· το μήνυμα σφάλματος δεν αρκεί για επιβεβαίωση
dig CNAME assets.target.com
# Ενδεικτική απόκριση: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Ενδεικτική απόκριση: NoSuchBucket

# 2. Παράδειγμα δημιουργίας bucket S3 στην αντίστοιχη περιοχή
aws s3 mb s3://assets-target --region us-east-1

# 3. Ρύθμιση bucket
# Παράδειγμα ρυθμίσεων φιλοξενίας ιστοσελίδας ή πρόσβασης

# 4. Μεταφόρτωση περιεχομένου
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Επαλήθευση
curl http://assets.target.com
```

---

<a id="section-06"></a>

## 📊 Γρήγορη αναφορά

### Αναγνώριση υποψήφιων υπηρεσιών

| Υπηρεσία | Μοτίβο CNAME | Αποτύπωμα αναγνώρισης |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Λίστα ελέγχου πιθανής ανάληψης

```markdown
- [ ] Αναζήτηση υποτομέων εντός συμφωνημένων ορίων
- [ ] Εντοπισμός εγγραφών CNAME
- [ ] Έλεγχος εγκαταλειμμένων εγγραφών
- [ ] Σύγκριση αποτυπωμάτων αναγνώρισης
- [ ] Διασταύρωση δυνατότητας ανάληψης του πόρου
- [ ] Δοκιμή ανάληψης μόνο εφόσον επιτρέπεται ρητά
- [ ] Τεκμηρίωση και αναφορά
```

---

<a id="section-07"></a>

## 🛠️ Σύνοψη εργαλείων

| Εργαλείο | Σκοπός |
|------|---------|
| **subjack** | Αυτοματοποιημένη αναζήτηση ενδείξεων takeover |
| **nuclei** | Έλεγχοι βάσει προτύπων |
| **can-i-take-over-xyz** | Πηγή αποτυπωμάτων αναγνώρισης |
| **dnsrecon** | Συλλογή πληροφοριών DNS |
| **subfinder** | Αναζήτηση υποτομέων |
| **httpx** | Δοκιμαστικά αιτήματα HTTP |

---

<a id="section-08"></a>

## 📚 Πηγές

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Πρότυπα takeover του Nuclei](https://github.com/projectdiscovery/nuclei-templates/tree/main/http/takeovers)

---

<p align="center">
  <b>🎯 Ελέγξτε τις εγκαταλειμμένες εγγραφές!</b><br>
  <i>Μόνο για εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
