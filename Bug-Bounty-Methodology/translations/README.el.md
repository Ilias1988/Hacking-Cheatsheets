# 🐛 Μεθοδολογία Bug Bounty

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Οργανωμένη μεθοδολογία ελέγχων Bug Bounty
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## Πριν ξεκινήσετε

Το **bug bounty** είναι η αναζήτηση και τεκμηριωμένη αναφορά ευπαθειών μέσα στους κανόνες ενός προγράμματος. Η αμοιβή εξαρτάται από το πρόγραμμα και το εύρημα· δεν είναι εγγυημένη. Ο οδηγός οργανώνει τη δουλειά σε ανακάλυψη υποτομέων, συσχέτιση υποδομών, έλεγχο υπηρεσιών web, συλλογή URLs και στοχευμένη διερεύνηση.

**Βασικοί όροι:** *scope* = επιτρεπόμενοι στόχοι και όρια ελέγχου, *asset* = στοιχείο υποδομής, *recon* = συλλογή πληροφοριών, *endpoint* = συγκεκριμένο σημείο εφαρμογής, *parameter* = παράμετρος εισόδου, *PoC* = ελάχιστη απόδειξη προβλήματος. Η παθητική συλλογή από τρίτες πηγές διαφέρει από τα αιτήματα που στέλνετε στον στόχο.

Ακολουθήστε τις φάσεις με μικρά, ελεγμένα σύνολα δεδομένων. Πριν περάσετε μια λίστα στο επόμενο εργαλείο, επιβεβαιώστε scope και μορφή εισόδου. Τα domains, οι εταιρείες, οι IP και τα tokens των παραδειγμάτων δεν συνιστούν άδεια ελέγχου. Οι εντολές διατηρούνται από το πρωτότυπο, με διευκρινίσεις όπου χρειάζεται.

<a id="section-01"></a>

## 🗺️ Επισκόπηση ροής ελέγχου

```
ΜΕΘΟΔΟΛΟΓΙΑ BUG BOUNTY

ΦΑΣΗ 1: Αναζήτηση υποτομέων
├── Subfinder / Amass
└── crt.sh / Wayback

ΦΑΣΗ 2: ASN και IP
├── Χαρτογράφηση ASN και συλλογή IP
└── Shodan

ΦΑΣΗ 3: Προσβάσιμες υπηρεσίες
├── HTTPX: δοκιμές HTTP και αναζήτηση θυρών
└── Aquatone: στιγμιότυπα

ΦΑΣΗ 4: Συλλογή URL
├── Katana/Hakrawler / GAU
└── Εξαγωγή παραμέτρων

ΦΑΣΗ 5: Διερεύνηση πιθανών ευπαθειών
├── XSS / LFI/RFI / SSRF/CORS
└── Open Redirect

ΦΑΣΗ 6: Αναλυτική εξερεύνηση
├── Δοκιμές καταλόγων και παραμέτρων
└── Έκθεση δεδομένων Git
```

---

## 📋 Περιεχόμενα

- [Επισκόπηση ροής ελέγχου](#section-01)
- [Φάση 1: Αναζήτηση υποτομέων](#section-02)
- [Φάση 2: Αναζήτηση ASN και IP](#section-03)
- [Φάση 3: Εντοπισμός προσβάσιμων υπηρεσιών](#section-04)
- [Φάση 4: Συλλογή URL](#section-05)
- [Φάση 5: Έλεγχος πιθανών ευπαθειών](#section-06)
- [Φάση 6: Αναζήτηση καταλόγων και αρχείων](#section-07)
- [Γρήγορη αναφορά](#section-08)
- [Εγκατάσταση εργαλείων](#section-09)
- [Συμβουλές για Bug Bounty](#section-10)
- [Σχετικοί οδηγοί](#section-11)

---

<a id="section-02"></a>

## 🔍 Φάση 1: Αναζήτηση υποτομέων

> **Πηγές και αρχεία:** Το recursive του Subfinder επιλέγει πηγές που υποστηρίζουν αναζήτηση βαθύτερων ονομάτων· δεν επανεισάγει συνεχώς κάθε εύρημα. Στη συγχώνευση επιλέξτε συγκεκριμένα αρχεία: το γενικό μοτίβο όλων των .txt μπορεί να αναμείξει άσχετα δεδομένα ή προηγούμενη έξοδο.


> **Στόχος:** Καταγράψτε υποτομείς που σχετίζονται με τον στόχο και ελέγξτε ποιοι επιτρέπεται να εξεταστούν. Περισσότερα ονόματα δεν εγγυώνται περισσότερες ευπάθειες.

### Εργαλεία αυτοματοποιημένης συλλογής πληροφοριών

#### Subfinder
```bash
# Αναζήτηση υποτομέων από πολλές πηγές
subfinder -d example.com -all -recursive -o subfinder.txt

# Επεξήγηση επιλογών:
# -all: Χρήση όλων των διαθέσιμων πηγών, εφόσον έχουν ρυθμιστεί τα απαιτούμενα κλειδιά
# -recursive: Χρήση πηγών που υποστηρίζουν αναδρομικά ονόματα
# -o: Αρχείο αποτελεσμάτων
```
> 💡 **Παρατήρηση:** Το `-all` χρησιμοποιεί περισσότερες διαθέσιμες πηγές. Δεν εγγυάται πλήρη καταγραφή και ορισμένες πηγές χρειάζονται διαπιστευτήρια.

#### Assetfinder
```bash
# Αναζήτηση domains και υποτομέων που φαίνεται να σχετίζονται με τον στόχο
assetfinder --subs-only example.com > assetfinder.txt

# Απλή αρχική συλλογή πληροφοριών
```
> 💡 **Παρατήρηση:** Συνδυάστε τα αποτελέσματα του Assetfinder με άλλες πηγές και αφαιρέστε διπλότυπα.

#### Sublist3r
```bash
# Αναζήτηση υποτομέων από ανοικτές πηγές
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: Επιλογή μηχανών αναζήτησης
# -v: Αναλυτική έξοδος
```

#### Amass
```bash
# Χαρτογράφηση συσχετίσεων υποδομών
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Η παθητική λειτουργία αντλεί από τρίτες πηγές· ελέγξτε τις πρόσθετες ενεργές επιλογές
# Το -active προσθέτει ενεργούς ελέγχους που παράγουν δικτυακή κίνηση
```
> **Παρατήρηση:** Οι ενεργοί έλεγχοι του Amass παράγουν δικτυακή κίνηση. Ξεκινήστε με συλλογή από πηγές και προχωρήστε σύμφωνα με τους κανόνες του προγράμματος.

### Δημόσιες πηγές

#### Καταγραφές δημοσίων πιστοποιητικών (crt.sh)
```bash
# Αναζήτηση υποτομέων από αρχεία δημοσίων πιστοποιητικών
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Γιατί crt.sh;** Τα δημόσια αρχεία πιστοποιητικών μπορούν να δώσουν ονόματα που σχετίζονται με έναν στόχο.

#### Wayback Machine
```bash
# Αναζήτηση ιστορικών ονομάτων σε αρχεία web
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Παρατήρηση:** Ιστορικά ονόματα μπορεί να παραμένουν ενεργά. Χρειάζονται επαλήθευση πριν από οποιοδήποτε συμπέρασμα.

### Επεξεργασία υποτομέων

#### Συνένωση και αφαίρεση διπλοτύπων
```bash
# Συνένωση αρχείων υποτομέων και αφαίρεση διπλοτύπων
cat *.txt | sort -u > final_subdomains.txt

# Καταμέτρηση γραμμών αποτελεσμάτων
wc -l final_subdomains.txt
```

#### Δοκιμές ονομάτων υποτομέων με ffuf
```bash
# Δοκιμές ονομάτων σε URL μέσω HTTP, όχι καθαρό DNS enumeration
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: Συμπερίληψη συγκεκριμένων κωδικών κατάστασης
# Ενδεικτική λίστα: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Παραγωγή παραλλαγών υποτομέων με Alterx
```bash
# Παραγωγή παραλλαγών ονομάτων και επίλυση DNS
subfinder -d example.com | alterx | dnsx

# Εμπλουτισμός υποψήφιων ονομάτων με συνήθη μοτίβα
echo example.com | alterx -enrich | dnsx

# Χρήση λίστας για παραγωγή παραλλαγών ονομάτων
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Παράδειγμα:** Από το `dev.example.com`, το alterx μπορεί να παράγει παραλλαγές όπως `dev1`, `dev2` και `dev-api`.

---

<a id="section-03"></a>

## 🌐 Φάση 2: Αναζήτηση ASN και IP

> **Συσχέτιση και scope:** ASN, πιστοποιητικό, εύρος cloud ή κοινή IP μπορεί να αφορά πολλούς οργανισμούς. Διασταυρώστε το scope πριν από ενεργό έλεγχο. Εκδόσεις APIs, σελιδοποίηση, όρια και απαιτήσεις πρόσβασης των παραδειγμάτων μπορεί να έχουν αλλάξει.


> **Στόχος:** Αναζητήστε πιθανά σχετικά εύρη IP και υποδομές του οργανισμού, χωρίς να θεωρείτε τη συσχέτιση απόδειξη ιδιοκτησίας ή εξουσιοδότησης.

### Χαρτογράφηση ASN

```bash
# Αναζήτηση IP που φαίνεται να συνδέονται με το ASN του domain
asnmap -d example.com | dnsx -silent -resp-only

# Αναζήτηση υποδομών βάσει ονόματος οργανισμού
amass intel -org "organization_name"

# Αναζήτηση υποδομών σε εύρος IP
amass intel -active -cidr 159.69.129.82/32

# Αναζήτηση υποδομών βάσει αριθμού ASN
amass intel -active -asn [asnno]
```
> 💡 **Γιατί ASN;** Η συσχέτιση δικτύων βοηθά στη χαρτογράφηση υποδομών. Ένα εύρος ASN δεν ανήκει απαραίτητα αποκλειστικά στον στόχο ούτε βρίσκεται αυτομάτως εντός scope.

### Συλλογή διευθύνσεων IP

#### VirusTotal
```bash
# Εξαγωγή IP από αποτελέσματα VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Συλλογή IP από AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Εξαγωγή IP από αποτελέσματα URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Αναζήτηση πιστοποιητικών στο Shodan
```bash
# Αναζήτηση IP μέσω στοιχείων πιστοποιητικών στο Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Παρατήρηση:** Η αναζήτηση πιστοποιητικών στο Shodan μπορεί να δώσει επιπλέον συσχετίσεις υποδομών, που απαιτούν επιβεβαίωση.

---

<a id="section-04"></a>

## ✅ Φάση 3: Εντοπισμός προσβάσιμων υπηρεσιών

> **Μορφή εξόδου:** Το httpx με τίτλους ή status codes δεν παράγει καθαρή λίστα URLs, ακόμη και με silent. Ελέγξτε τι περιμένει το επόμενο εργαλείο. Αποτυχία απόκρισης στις επιλεγμένες θύρες δεν αποδεικνύει ότι ο host δεν λειτουργεί.


> **Στόχος:** Εντοπίστε ποιες επιτρεπόμενες υπηρεσίες είναι προσβάσιμες με τις συγκεκριμένες δοκιμές.

### Δοκιμές πρόσβασης HTTP με httpx

```bash
# Βασικές δοκιμές HTTP σε πολλές θύρες
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Δοκιμές με κωδικούς κατάστασης, τίτλους και τεχνολογίες
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Επιλογές:
# -sc: Κωδικός κατάστασης HTTP
# -title: Τίτλος σελίδας
# -server: Κεφαλίδα Server
# -td: Αναγνώριση τεχνολογιών
```

### Οπτική αναγνώριση με Aquatone

```bash
# Λήψη στιγμιότυπων προσβάσιμων υπηρεσιών
cat hosts.txt | aquatone

# Συγκεκριμένες θύρες
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Εκτενέστερη ενδεικτική λίστα θυρών
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Γιατί στιγμιότυπα;** Βοηθούν στην αρχική αναγνώριση εφαρμογών, σελίδων σύνδεσης και μηνυμάτων σφάλματος.

---

<a id="section-05"></a>

## 🔗 Φάση 4: Συλλογή URL

> **Στόχος:** Συλλέξτε URL για οργανωμένη διερεύνηση παραμέτρων και πιθανών ευπαθειών.

### Ενεργή εξερεύνηση ιστοτόπου

#### Katana
```bash
# Εξερεύνηση ιστοτόπου για ανακάλυψη URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: Βάθος εξερεύνησης δύο επιπέδων
# Εργαλείο εξερεύνησης εφαρμογών web
```

#### Hakrawler
```bash
# Απλό εργαλείο εξερεύνησης ιστοτόπων
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### Συλλογή γνωστών URL από τρίτες πηγές

#### GAU (Get All URLs)
```bash
# Συλλογή γνωστών URL από AlienVault OTX, Wayback Machine και Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Επιλογή γνωστών εγγραφών με status 200 και αφαίρεση διπλοτύπων
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Γιατί GAU;** Συλλέγει ιστορικά URL που μπορεί να αποκαλύπτουν διαδρομές προς περαιτέρω διερεύνηση.

#### URLFinder
```bash
# Συλλογή URL από διάφορες πηγές
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Εξαγωγή παραμέτρων

```bash
# Επιλογή URL που φαίνεται να περιέχουν παραμέτρους
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Αντιστοίχιση μοτίβου παραμέτρων
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# Μοτίβο GF για URL προς διερεύνηση SQL Injection
cat allurls.txt | gf sqli

# Μοτίβο GF για υποψήφιο XSS
cat allurls.txt | gf xss

# Μοτίβο GF για υποψήφια LFI
cat allurls.txt | gf lfi
```
> 💡 **Μοτίβα GF:** Το `gf` χρησιμοποιεί συλλογές μοτίβων, όπως εκείνες του github.com/1ndianl33t/Gf-Patterns. Η αντιστοίχιση μοτίβου δεν επιβεβαιώνει ευπάθεια.

---

<a id="section-06"></a>

## 🔓 Φάση 5: Έλεγχος πιθανών ευπαθειών

### 🔴 Αναζήτηση πιθανών σημείων XSS

#### Έλεγχοι ανακλώμενου XSS

> **Συλλογή υποψηφίων:** Το συγκεκριμένο pipeline συλλέγει URLs με παραμέτρους· δεν εισάγει payload ούτε επιβεβαιώνει εκτέλεση JavaScript. Τα φίλτρα GF αναγνωρίζουν μοτίβα για προτεραιοποίηση, όχι επιβεβαιωμένα XSS, SQL injection ή LFI.

```bash
# Αλυσίδα συλλογής υποψήφιων URL για XSS· δεν επιβεβαιώνει εκτέλεση JavaScript
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Έλεγχοι τυφλού XSS (Blind XSS)

> **Callbacks και τεκμηρίωση:** Μια εξωτερική επικοινωνία χρειάζεται συσχέτιση με το αίτημα και το περιβάλλον εκτέλεσης. Χρησιμοποιήστε ελεγχόμενη υπηρεσία και ελάχιστα επιτρεπόμενα δεδομένα. Μελετήστε τα δείγματα αποστολής cookie σε δικό σας εργαστήριο με συνθετικά δεδομένα.

```bash
# Παραδείγματα δοκιμαστικών δεδομένων Blind XSS
# Παράδειγμα χρήσης υπηρεσιών όπως XSS Hunter ή Burp Collaborator
# Τοποθέτηση δοκιμαστικών δεδομένων στα επιτρεπόμενα πεδία, κεφαλίδες και παραμέτρους
```
> 💡 **Blind XSS:** Δοκιμαστικά δεδομένα μπορεί να εκτελεστούν αργότερα, σε διαφορετικό περιβάλλον προβολής. Οι εξωτερικές αλληλεπιδράσεις πρέπει να αντιστοιχίζονται με συγκεκριμένη δοκιμή και την επιτρεπόμενη έκταση του ελέγχου.

### 🔴 Αναζήτηση πιθανής συμπερίληψης τοπικών αρχείων (LFI)

```bash
# Παράδειγμα LFI με ffuf και αναζήτηση μοτίβου αρχείου passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Παράδειγμα παράλληλων αιτημάτων curl για ενδείξεις LFI
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# Παράδειγμα του πρωτοτύπου με httpx για ενδείξεις LFI
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **Παρατήρηση LFI:** Τα `/etc/passwd`, `....//....//etc/passwd` και οι κωδικοποιημένες παραλλαγές είναι παραδείγματα του πρωτοτύπου, με αποτελέσματα που εξαρτώνται από την εφαρμογή και το λειτουργικό.

### 🔴 Αναζήτηση ανοικτών ανακατευθύνσεων

> **Το regex στο σώμα δεν αρκεί:** Η εύρεση evil.com στο τελικό περιεχόμενο μετά από ανακατευθύνσεις δεν αποδεικνύει open redirect. Εξετάστε αλυσίδα αποκρίσεων, Location, ελεγχόμενη παράμετρο και προορισμό.


```bash
# Αναζήτηση παραμέτρων σχετικών με ανακατεύθυνση
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Μοτίβο GF για υποψήφιες ανακατευθύνσεις
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Δοκιμές παραμέτρων ανακατεύθυνσης
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Αλυσίδα συλλογής URL και ενδείξεων ανακατεύθυνσης
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 Έλεγχοι πλαστογράφησης αιτημάτων από τον server (SSRF)

> **Προϋποθέσεις:** Τα παραδείγματα localhost και metadata δεν λειτουργούν παντού. Έλεγχοι δικτύου, tokens και εκδόσεις της υπηρεσίας αλλάζουν το αποτέλεσμα. Κρατήστε την απόδειξη περιορισμένη και εγκεκριμένη· η αντανάκλαση URL δεν αποδεικνύει αίτημα από τον server.


```bash
# Αναζήτηση ονομάτων παραμέτρων προς διερεύνηση SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Αναζήτηση ονομάτων που παραπέμπουν σε API ή webhooks
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Έλεγχος προτύπων SSRF με Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Παράδειγμα ελέγχου SSRF προς το localhost του server
curl "https://example.com/page?url=http://127.0.0.1:80/"

# Παράδειγμα SSRF προς υπηρεσία metadata AWS· η πρόσβαση δεν είναι εγγυημένη
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> **Επίπτωση SSRF:** Μπορεί να επιτρέψει αιτήματα από τον server προς άλλες υπηρεσίες. Η πραγματική πρόσβαση εξαρτάται από φίλτρα, δρομολόγηση και μηχανισμούς ταυτοποίησης.

### 🔴 Έλεγχοι πολιτικής CORS

> **Δεν είναι αυτόματα κρίσιμο εύρημα:** Το wildcard στο Access-Control-Allow-Origin δεν επιτρέπει credentialed ανάγνωση από browser. Εξετάστε πραγματικό origin, credentials και ευαισθησία δεδομένων. Δείτε τον [κανόνα CORS και credentials του Fetch](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials).


```bash
# Έλεγχος κεφαλίδων CORS με συγκεκριμένο Origin
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Εμφάνιση σχετικών κεφαλίδων CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Αυτοματοποιημένοι έλεγχοι CORS με Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> **Διόρθωση CORS:** Ο συνδυασμός `Access-Control-Allow-Origin: *` με credentials δεν αποδεικνύει κρίσιμη ευπάθεια· οι browsers δεν επιτρέπουν ανάγνωση credentialed απόκρισης με αυτό το wildcard. Χρειάζεται τεκμηρίωση της πραγματικής συμπεριφοράς και επίπτωσης.

### Έλεγχοι ευπαθειών με Nuclei

```bash
# Ένας στόχος
nuclei -u https://example.com -bs 50 -c 30

# Πολλαπλοί στόχοι
nuclei -l live_domains.txt -bs 50 -c 30

# Επιλογή κρίσιμης και υψηλής σοβαρότητας
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: Πλήθος στόχων ανά ομάδα παράλληλης επεξεργασίας
# -c: Παράλληλες εργασίες
```

---

<a id="section-07"></a>

## 📂 Φάση 6: Αναζήτηση καταλόγων και αρχείων

### Αναζήτηση εκτεθειμένων αρχείων

```bash
# Ενδεικτικές επεκτάσεις αρχείων προς διερεύνηση
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Εκτενέστερη κανονική έκφραση επεκτάσεων αρχείων
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Παράδειγμα ερωτήματος Google για αρχεία
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Δοκιμές ονομάτων καταλόγων

#### Dirsearch
```bash
# Βασική αναζήτηση καταλόγων
dirsearch -u https://example.com --full-url --deep-recursive -r

# Αναζήτηση με πολλές επεκτάσεις
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Αναζήτηση καταλόγων με ffuf
```bash
# Συνδυασμός επιλογών αναζήτησης καταλόγων ffuf
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Έλεγχος έκθεσης δεδομένων Git

```bash
# Αναζήτηση πιθανώς εκτεθειμένων καταλόγων .git
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Έλεγχος πρόσβασης στο .git/config
curl -s https://example.com/.git/config

# Παράδειγμα εξαγωγής εκτεθειμένου αποθετηρίου· απαιτεί αντίστοιχη άδεια
git-dumper https://example.com/.git/ output/
```
> **Έκθεση Git:** Μπορεί να αποκαλύπτει κώδικα και ευαίσθητα δεδομένα. Ακολουθήστε τα όρια συλλογής τεκμηρίων του προγράμματος.

### Αναζήτηση μη εμφανών παραμέτρων με Arjun

```bash
# Παράδειγμα συλλογής υποψήφιων παραμέτρων από πηγές
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Ενεργή αναζήτηση παραμέτρων με λίστα λέξεων
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Έλεγχος πιθανής ανάληψης υποτομέα (Subdomain Takeover)

> **Απαιτείται επιβεβαίωση:** Ένα CNAME ή μήνυμα ανύπαρκτης υπηρεσίας δεν αποδεικνύει δυνατότητα ανάληψης του υποτομέα. Η κατοχύρωση πόρου για επαλήθευση πρέπει να επιτρέπεται από το πρόγραμμα.


```bash
# Αυτοματοποιημένη αναζήτηση ενδείξεων takeover
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Ένδειξη πιθανής ανάληψης:** CNAME προς μη δεσμευμένη εξωτερική υπηρεσία μπορεί να χρειάζεται έλεγχο. Δεν αποδεικνύει από μόνο του ότι είναι δυνατή η ανάληψη.

### Έλεγχοι WordPress

```bash
# Συνδυασμός επιλογών ελέγχου WordPress· δεν εγγυάται πλήρη κάλυψη
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: Όλα τα θέματα WordPress
# -e ap: Όλα τα πρόσθετα WordPress
# -e u: Χρήστες
```

---

<a id="section-08"></a>

## 📊 Γρήγορη αναφορά

### Αλυσίδα συλλογής πληροφοριών

> **Επιμέρους παραδείγματα:** Τα ονόματα αρχείων στις ενότητες δεν αποτελούν μία έτοιμη συνεπή ακολουθία. Ελέγξτε ποιο αρχείο δημιουργείται και καταναλώνεται, τη μορφή και το scope του. Οι ετικέτες σοβαρότητας scanner δεν υποκαθιστούν την επιβεβαίωση αντίκτυπου.


```bash
# 1. Αναζήτηση υποτομέων
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Εντοπισμός προσβάσιμων υπηρεσιών
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Συλλογή URL
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URL με παραμέτρους
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Έλεγχοι ενδείξεων ευπαθειών
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Σύντομες αλυσίδες εντολών

| Σκοπός | Εντολή |
| --------- | --------- |
| Αναζήτηση υποτομέων | `subfinder -d target.com -silent` |
| Έλεγχος προσβάσιμων υπηρεσιών | `cat subs.txt \| httpx -silent` |
| Συλλογή γνωστών URL | `echo target.com \| gau` |
| Αναζήτηση παραμέτρων | `cat urls.txt \| grep '='` |
| Έλεγχος ανακατευθύνσεων | `cat urls.txt \| gf redirect` |
| Έλεγχος με Nuclei | `nuclei -l hosts.txt -s critical,high` |

### Επεκτάσεις αρχείων προς διερεύνηση

| Τύπος | Επεκτάσεις αρχείων |
| ------ | ------------ |
| **Αρχεία ρυθμίσεων** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Αντίγραφα ασφαλείας** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Πηγαίος κώδικας** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Δεδομένα** | `.sql, .db, .sqlite, .json, .xml` |
| **Έγγραφα** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="section-09"></a>

## 🛠️ Εγκατάσταση εργαλείων

### Βασικά εργαλεία

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# httpx
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# ffuf
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### Εργαλεία XSS και ανακατευθύνσεων

```bash
# OR, εργαλείο ελέγχου ανοικτών ανακατευθύνσεων
go install -v github.com/h6nt3r/or@latest

# RC, εργαλείο ελέγχου ανακλώμενου XSS
go install -v github.com/h6nt3r/rc@latest

# CXSS, εργαλείο ελέγχων XSS ανά πλαίσιο
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF, επιλογή γραμμών βάσει μοτίβων
go install github.com/tomnomnom/gf@latest
```

### Βοηθητικά εργαλεία

```bash
# URO, αφαίρεση παρόμοιων URL
pip3 install uro

# Arjun, αναζήτηση παραμέτρων
pip3 install arjun

# Subzy, αναζήτηση ενδείξεων takeover
go install -v github.com/PentestPad/subzy@latest
```

---

<a id="section-10"></a>

## 🎯 Συμβουλές για Bug Bounty

1. **Αυτοματοποιήστε και επιβεβαιώστε χειροκίνητα** — τα εργαλεία δίνουν υποψήφια ευρήματα, εσείς τεκμηριώνετε αν ισχύουν
2. **Εξετάστε robots.txt και sitemap.xml** — μπορεί να περιέχουν χρήσιμες διαδρομές
3. **Εξετάστε αρχεία JavaScript** — αναζητήστε διαδρομές API και πιθανές ακούσιες εκθέσεις δεδομένων
4. **Εξετάστε τις σχετικές παραμέτρους** — ακόμη και μη εμφανείς, με εργαλεία όπως το Arjun
5. **Εξετάστε αλληλεπιδράσεις ευπαθειών** — η μεγαλύτερη επίπτωση πρέπει να αποδεικνύεται, δεν προκύπτει αυτόματα από έναν συνδυασμό
6. **Η ποιότητα αναφοράς μετρά** — σαφές PoC, πραγματική επίπτωση και βήματα αναπαραγωγής
7. **Δουλέψτε με υπομονή** — η κατανόηση της εφαρμογής απαιτεί χρόνο

---

<a id="section-11"></a>

## 📚 Σχετικοί οδηγοί

- [Nuclei](../../Nuclei/translations/README.el.md)
- [ffuf](../../ffuf/translations/README.el.md)
- [Subfinder](../../Subfinder/translations/README.el.md)
- [httpx](../../httpx/translations/README.el.md)
- [Εξειδικευμένες αναζητήσεις Google (Google Dorking)](../../Google-Dorking/translations/README.el.md)
- [Burp Suite](../../Burp-Suite/translations/README.el.md)

---

<p align="center">
  <b>🐛 Καλή εξάσκηση στους ελέγχους Bug Bounty!</b><br>
  <i>Μεθοδικός έλεγχος και τεκμηριωμένα ευρήματα</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
