# 🐙 GitHub Dorking — Πλήρης οδηγός αναφοράς

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗ ██╗████████╗██╗  ██╗██╗   ██╗██████╗ 
  ██╔════╝ ██║╚══██╔══╝██║  ██║██║   ██║██╔══██╗
  ██║  ███╗██║   ██║   ███████║██║   ██║██████╔╝
  ██║   ██║██║   ██║   ██╔══██║██║   ██║██╔══██╗
  ╚██████╔╝██║   ██║   ██║  ██║╚██████╔╝██████╔╝
   ╚═════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ 
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/GitHub-Dorking-red?style=for-the-badge" alt="GitHub Dorking">
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Secret Hunting">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Εντοπισμός ακούσια δημοσιευμένων μυστικών και στοιχείων πρόσβασης σε repos</b><br>
<a href="../README.md">🇬🇧 English</a> • <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a>
</p>

---

## Πριν ξεκινήσετε

Το **GitHub Dorking** αναζητά μοτίβα σε κώδικα και αρχεία repos. **Qualifier** είναι ένας περιορισμός όπως repo ή org, **token** ένα διακριτικό πρόσβασης και **secret** μια τιμή που πρέπει να παραμένει μυστική. Η **έκθεση μυστικού** διαφέρει από ένα παράδειγμα, ένα δημόσιο αναγνωριστικό ή ένα ήδη ανακλημένο κλειδί.

Περιορίστε την έρευνα σε εγκεκριμένους οργανισμούς και repos. Μην χρησιμοποιείτε τα στοιχεία που βρίσκετε για σύνδεση ή κλήσεις υπηρεσιών. Η αναζήτηση κώδικα, το ιστορικό Git και τα Gists είναι διαφορετικές πηγές. Ο οδηγός περιέχει και παλιότερη σύνταξη· οι διευκρινίσεις παρακάτω βοηθούν να επιλέξετε τη σωστή λειτουργία χωρίς να αλλάζουν τα αρχικά ερωτήματα.

## 📋 Περιεχόμενα

- [Εισαγωγή](#section-01)
- [Σύνταξη αναζήτησης GitHub](#section-02)
- [Κλειδιά API και tokens](#section-03)
- [Στοιχεία πρόσβασης βάσεων δεδομένων](#section-04)
- [Ιδιωτικά κλειδιά](#section-05)
- [Αρχεία ρυθμίσεων](#section-06)
- [Μυστικά υποδομών cloud](#section-07)
- [Κωδικοί και στοιχεία πρόσβασης](#section-08)
- [Στόχοι Bug Bounty](#section-09)
- [Προχωρημένες τεχνικές](#section-10)
- [Εργαλεία αυτοματοποίησης](#section-11)
- [Γρήγορη αναφορά](#section-12)
- [Συμβουλές και καλές πρακτικές](#section-13)
- [Πλαίσιο εξουσιοδοτημένης χρήσης](#section-14)
- [Πηγές](#section-15)

---

<a id="section-01"></a>

## 🎯 Εισαγωγή

Το **GitHub Dorking** χρησιμοποιεί την αναζήτηση GitHub για τον εντοπισμό δημοσιευμένων μυστικών, στοιχείων πρόσβασης και άλλων πληροφοριών σε repos.

### Τι μπορείτε να εντοπίσετε;

| Κατηγορία | Παραδείγματα |
| ---------- | ---------- |
| **Κλειδιά API** | AWS, Google, Stripe, Twilio |
| **Tokens — διακριτικά πρόσβασης** | OAuth, JWT και προσωπικά διακριτικά πρόσβασης (PAT) |
| **Στοιχεία πρόσβασης** | Κωδικοί βάσεων δεδομένων και στοιχεία SMTP/SSH |
| **Ιδιωτικά κλειδιά** | Κλειδιά RSA/SSH και αρχεία PEM |
| **Αρχεία ρυθμίσεων** | .env, config.yml, settings.py |

### Γιατί έχει σημασία

- Μυστικά μπορεί να δημοσιευθούν κατά λάθος σε commits
- Μυστικά μπορεί να παραμένουν στο ιστορικό μετά τη διαγραφή τους από αρχείο
- Δημόσιοι κώδικες μπορεί να ευρετηριαστούν και να αναζητηθούν
- Πιθανή πηγή ευρημάτων Bug Bounty μέσα στους κανόνες του προγράμματος

---

<a id="section-02"></a>

## 🔧 Σύνταξη αναζήτησης GitHub

> **Παλιά και νέα αναζήτηση:** Το πρωτότυπο συνδυάζει legacy qualifiers και σημερινό Code Search. Δεν υποστηρίζονται απαραίτητα filename, extension, in ή size με τον ίδιο τρόπο. Το σύγχρονο Code Search χρησιμοποιεί μεταξύ άλλων path/content και λογικό NOT. Βλ. [επίσημη σύνταξη](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).


### Βασικό URL αναζήτησης

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Περιορισμοί αναζήτησης — qualifiers

| Περιορισμός | Περιγραφή | Παράδειγμα |
| ----------- | ------------- | --------- |
| `in:file` | Αναζήτηση στο περιεχόμενο αρχείου | `password in:file` |
| `in:path` | Αναζήτηση στη διαδρομή αρχείου | `config in:path` |
| `filename:` | Αναζήτηση βάσει ονόματος αρχείου | `filename:.env` |
| `extension:` | Αναζήτηση βάσει επέκτασης | `extension:json` |
| `language:` | Αναζήτηση βάσει γλώσσας | `language:python` |
| `user:` | Αναζήτηση στα repos χρήστη | `user:target` |
| `org:` | Αναζήτηση στα repos οργανισμού | `org:company` |
| `repo:` | Αναζήτηση σε συγκεκριμένο repo | `repo:user/repo` |
| `path:` | Αναζήτηση στη διαδρομή | `path:config/` |
| `size:` | Μέγεθος αρχείου | `size:>1000` |

### Λογικοί τελεστές

```
# Λογικό AND με κενό μεταξύ όρων
password database

# OR
password OR secret

# Ιστορικό παράδειγμα αποκλεισμού με μείον· ελέγξτε σύνταξη Code Search
password -test -example

# Ακριβής φράση
"api_key"

# Συνδυασμός
org:target "api_key" NOT test
```

---

<a id="section-03"></a>

## 🔑 Κλειδιά API και tokens

> **Μοτίβο δεν σημαίνει ενεργό μυστικό:** Προθέματα και ονόματα μεταβλητών μπορεί να είναι παραδείγματα ή δημόσια αναγνωριστικά. Ένα publishable key, SID ή client identifier δεν έχει πάντα την ίδια ευαισθησία με ένα secret. Τα παλιά μοτίβα δεν καλύπτουν κάθε σημερινό τύπο token.


### Κλειδιά AWS

```
# Αναγνωριστικό κλειδιού πρόσβασης AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Μυστικό κλειδί AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Συνδυασμός
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# Κλειδί API GCP
"AIza"
"AIzaSy"

# Λογαριασμός υπηρεσίας GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Κλειδιά Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Συνδυασμός
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# SID και token Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### Tokens GitHub

```
# Προσωπικά διακριτικά πρόσβασης
ghp_
gho_
ghu_
ghs_
ghr_

# Μοτίβα tokens GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Tokens Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Webhook Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Άλλα κλειδιά API

```
# SendGrid
"SG."
"SENDGRID_API_KEY"

# Mailgun
"key-"
"MAILGUN_API_KEY"

# Mailchimp
"[0-9a-f]{32}-us[0-9]{2}"

# Square
"sq0atp-"
"sq0csp-"

# PayPal
"PAYPAL"

# Heroku
"HEROKU_API_KEY"
"[h|H]eroku.*[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"
```

---

<a id="section-04"></a>

## 💾 Στοιχεία πρόσβασης βάσεων δεδομένων

### MySQL

```
# Συμβολοσειρές σύνδεσης MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Συνδυασμός
filename:.env MYSQL_PASSWORD
filename:config.php "mysql" "password"
"mysqli_connect" password
```

### PostgreSQL

```
# PostgreSQL
"postgres://"
"postgresql://"
"POSTGRES_PASSWORD"
"PG_PASSWORD"
filename:.env POSTGRES
```

### MongoDB

```
# Σύνδεση MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Με στοιχεία σύνδεσης
"mongodb://.*:.*@"
filename:.env MONGO
```

### Redis

```
# Redis
"redis://"
"REDIS_PASSWORD"
"REDIS_URL"
```

### SQLite

```
# SQLite
filename:*.sqlite
filename:*.db
extension:sqlite
```

### Γενικά μοτίβα βάσεων δεδομένων

```
# Συμβολοσειρές σύνδεσης
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Στοιχεία πρόσβασης
"db_password"
"db_pass"
"database_password"
```

---

<a id="section-05"></a>

## 🔐 Ιδιωτικά κλειδιά

### Κλειδιά SSH

```
# Ιδιωτικά κλειδιά SSH
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Αρχεία
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Συνδυασμός
filename:id_rsa -example -sample
```

### Κλειδιά PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### Πιστοποιητικά SSL/TLS

> **Πιστοποιητικό και ιδιωτικό κλειδί διαφέρουν:** Το δημόσιο πιστοποιητικό δεν είναι από μόνο του μυστικό. Εξετάστε τι περιέχει πραγματικά το αρχείο, χωρίς να θεωρείτε κάθε αρχείο PEM/CRT διαρροή ιδιωτικού κλειδιού.


```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="section-06"></a>

## ⚙️ Αρχεία ρυθμίσεων

### Αρχεία μεταβλητών περιβάλλοντος

```
# Αρχεία .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Με μυστικά
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Αρχεία ρυθμίσεων

```
# Διάφορα αρχεία ρυθμίσεων
filename:config.json
filename:config.yml
filename:config.yaml
filename:settings.py
filename:settings.json
filename:application.properties
filename:application.yml
filename:secrets.yml
filename:credentials.json
filename:credentials.xml
```

### Docker

```
# Ρυθμίσεις Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Αντικείμενα Secret Kubernetes
filename:*.yaml "kind: Secret"
filename:secrets.yaml
"kubernetes" "secret"
```

### CI/CD

```
# Jenkins
filename:Jenkinsfile password
filename:jenkins.xml

# Travis CI
filename:.travis.yml

# GitHub Actions
filename:*.yml "secrets"
path:.github/workflows

# GitLab CI
filename:.gitlab-ci.yml
```

---

<a id="section-07"></a>

## ☁️ Μυστικά υποδομών cloud

### AWS

```
# Αρχείο στοιχείων πρόσβασης AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Ρυθμίσεις AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Κάδοι αποθήκευσης S3
"s3.amazonaws.com"
"s3-"
".s3.amazonaws.com"
```

### Azure

```
# Azure
"AZURE_CLIENT_SECRET"
"AZURE_STORAGE_CONNECTION_STRING"
"AccountKey="
"SharedAccessSignature="

# Azure DevOps
"azure" "secret"
"azure" "token"
```

### Google Cloud

```
# GCP
"GOOGLE_APPLICATION_CREDENTIALS"
"GOOGLE_API_KEY"
"GOOGLE_CLOUD_PROJECT"
filename:credentials.json "private_key"
```

### DigitalOcean

```
"DIGITALOCEAN_ACCESS_TOKEN"
"DO_AUTH_TOKEN"
"digitalocean" "token"
```

### Heroku

```
"HEROKU_API_KEY"
"HEROKU_APP_NAME"
filename:.netrc heroku
```

---

<a id="section-08"></a>

## 🔓 Κωδικοί και στοιχεία πρόσβασης

### Κωδικοί ενσωματωμένοι στον πηγαίο κώδικα

```
# Μεταβλητές κωδικών
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Με τιμές
password="
password='
"password": "
'password': '
```

### Στοιχεία SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Μυστικά OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Μυστικά υπογραφής JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Στοιχεία διαχείρισης

```
# Διαχειριστής
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

<a id="section-09"></a>

## 🎯 Στόχοι Bug Bounty

### Οργανισμός στόχος

```
# Αναζήτηση σε συγκεκριμένο οργανισμό
org:target-company

# Repos χρήστη
user:target-username

# Συγκεκριμένο repo
repo:target/repository

# Συνδυασμός με όρους μυστικών
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Αναζήτηση βάσει domain

```
# Domain στόχος
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Συνδυασμός
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Διευθύνσεις email εργαζομένων

```
# Αναζήτηση σχετικών repos εργαζομένων
"@target.com"

# Email προγραμματιστών
"@target.com" password
"@target.com" config
```

### Εσωτερικά URLs

```
# Εσωτερικές υπηρεσίες
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Υπόδειγμα ερωτημάτων Bug Bounty

```
# Συνδυαστική συλλογή πληροφοριών
org:TARGET filename:.env
org:TARGET filename:config.json password
org:TARGET extension:sql
org:TARGET "api_key"
org:TARGET "secret_key"
org:TARGET "password"
org:TARGET "token"
org:TARGET "AWS_ACCESS"
org:TARGET internal
"@TARGET.com" password
"TARGET.com" api_key
```

---

<a id="section-10"></a>

## 🔬 Προχωρημένες τεχνικές

### Έλεγχος ιστορικού Git

> **Το Code Search δεν είναι ιστορικό Git:** Η αναζήτηση κώδικα δεν ισοδυναμεί με σάρωση όλων των commits. Το pushed είναι περιορισμός άλλου είδους αναζήτησης και όχι γενικό φίλτρο κάθε αρχείου. Το ιστορικό χρειάζεται χωριστή εξέταση στο εγκεκριμένο repo.


```
# Έλεγχος commits
# Χρήση κατάλληλα ρυθμισμένων εργαλείων όπως TruffleHog και git-secrets

# Πρόσφατη ενημέρωση κατά το πρωτότυπο· ο qualifier δεν είναι γενικός Code Search
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Αναζήτηση Gists

```
# Αναζήτηση Gists
https://gist.github.com/search?q=password+target

# Ενδεικτικοί όροι Gists
gist password
gist api_key
```

### Αποσπάσματα κώδικα

```
# Αναζήτηση στον κώδικα
in:file password
in:path config

# Μεγάλα αρχεία κατά το πρωτότυπο· ελέγξτε υποστήριξη qualifier
size:>10000 password
```

### Κανονικές εκφράσεις για συμβατά εργαλεία

> **Regex ή κυριολεκτικό κείμενο:** Οι εκφράσεις της ενότητας προορίζονται για συμβατά εργαλεία. Στο Code Search η regex σύνταξη χρειάζεται τους κατάλληλους οριοθέτες και έχει περιορισμούς. Απλά εισαγωγικά γύρω από ένα μοτίβο δεν το μετατρέπουν αυτομάτως σε regex.


```
# Μοτίβο κλειδιού AWS
AKIA[0-9A-Z]{16}

# Token GitHub
ghp_[a-zA-Z0-9]{36}

# Token Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Ιδιωτικό κλειδί
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="section-11"></a>

## 🛠️ Εργαλεία αυτοματοποίησης

### TruffleHog

> **Έκδοση και επαλήθευση:** Η εγκατάσταση pip του πρωτοτύπου δεν ταυτίζεται απαραίτητα με το σημερινό CLI. Η υποεντολή git δεν δέχεται URL οργανισμού ως να ήταν ένα repo. Το TruffleHog μπορεί να επαληθεύει ευρήματα με αιτήματα σε παρόχους· αυτό χρειάζεται ξεχωριστή εξουσιοδότηση. Βλ. επιλογές πηγών και απενεργοποίησης verification στο [επίσημο έργο](https://github.com/trufflesecurity/trufflehog).


```bash
# Παράδειγμα εγκατάστασης
pip install truffleHog

# Έλεγχος repo
trufflehog git https://github.com/target/repo

# Παράδειγμα ελέγχου οργανισμού· χρειάζεται σωστή υποεντολή
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

> **Τοπική πηγή και έκδοση:** Οι παλιές detect/protect έχουν αποσυρθεί από την κύρια βοήθεια νεότερων εκδόσεων. Το source δεν σημαίνει αυθαίρετα URL απομακρυσμένου repo. Ελέγξτε αποδεκτές εισόδους και λειτουργίες git/dir/stdin στο [επίσημο Gitleaks](https://github.com/gitleaks/gitleaks).


```bash
# Παράδειγμα εγκατάστασης
brew install gitleaks

# Έλεγχος repo
gitleaks detect -s /path/to/repo

# Παράδειγμα URL του πρωτοτύπου· ελέγξτε αποδεκτή μορφή πηγής
gitleaks detect --source https://github.com/target/repo

# Έξοδος JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

> **Εισαγωγικά shell:** Το δεύτερο αρχικό regex περιέχει μονό εισαγωγικό μέσα σε ήδη μονά εισαγωγικά Bash. Το backslash δεν το διαφεύγει σε αυτό το πλαίσιο· η εντολή χρειάζεται διόρθωση πριν χρησιμοποιηθεί. Το regex διατηρείται εδώ αυτούσιο.


```bash
# Παράδειγμα εγκατάστασης
brew install git-secrets

# Προσθήκη μοτίβων
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Έλεγχος repo
git secrets --scan
```

### Εργαλεία αναζήτησης GitHub

```bash
# GitHub Dork CLI
# Διατίθενται διάφορα εργαλεία κοινότητας

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Παρακολούθηση δημοσιεύσεων GitHub
# Παρακολούθηση πιθανών δημοσιεύσεων μυστικών
shhgit --search-query "org:target"
```

---

<a id="section-12"></a>

## 📊 Γρήγορη αναφορά

### Βασικά ερωτήματα αναφοράς

| Σκοπός | Ερώτημα αναζήτησης |
| --------- | ------ |
| Κλειδιά AWS | `org:target AKIA` |
| Κωδικοί πρόσβασης | `org:target password filename:.env` |
| Κλειδιά API | `org:target api_key` |
| Ιδιωτικά κλειδιά | `org:target "BEGIN RSA PRIVATE KEY"` |
| Αρχεία ρυθμίσεων | `org:target filename:config.json` |
| Στοιχεία βάσης δεδομένων | `org:target "mongodb://"` |
| Tokens — διακριτικά πρόσβασης | `org:target token secret` |
| Διευθύνσεις email | `"@target.com" password` |

### Μοτίβα αρχείων

| Μοτίβο | Ερώτημα αναζήτησης |
| --------- | ------ |
| `.env` | `filename:.env` |
| Ρυθμίσεις JSON | `filename:config.json` |
| Ρυθμίσεις | `filename:settings.py` |
| Στοιχεία πρόσβασης | `filename:credentials` |
| Μυστικά | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### Δεκαπέντε ενδεικτικά ερωτήματα GitHub

| # | Ερώτημα αναζήτησης | Σκοπός |
| --- | ------ | --------- |
| 1 | `filename:.env DB_PASSWORD` | Κωδικοί βάσεων δεδομένων |
| 2 | `filename:.env AWS_` | Στοιχεία AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Ιδιωτικά κλειδιά |
| 4 | `filename:config.json password` | Κωδικοί σε ρυθμίσεις |
| 5 | `AKIA` | Κλειδιά πρόσβασης AWS |
| 6 | `sk_live` | Κλειδιά Stripe |
| 7 | `"mongodb+srv://"` | Διευθύνσεις σύνδεσης MongoDB |
| 8 | `filename:credentials aws` | Αρχείο στοιχείων AWS |
| 9 | `ghp_` | Tokens GitHub |
| 10 | `xoxb-` | Tokens Slack |
| 11 | `org:target password` | Κωδικοί οργανισμού |
| 12 | `filename:.npmrc _auth` | Tokens NPM |
| 13 | `extension:sql password` | Εξαγωγές SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Κλειδιά API |

---

<a id="section-13"></a>

## 💡 Συμβουλές και καλές πρακτικές

### Συμβουλές για Bug Bounty

1. **Αναζητήστε στον οργανισμό**
   ```
   org:target-company
   ```

2. **Εξετάστε σχετικά repos εργαζομένων εντός scope**
   ```
   "@target.com"
   ```

3. **Αναζητήστε αναφορές εσωτερικών URLs**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Εξετάστε το ιστορικό Git**
   - Χρησιμοποιήστε κατάλληλα ρυθμισμένο TruffleHog ή Gitleaks

5. **Εξετάστε Gists**
   - Μπορεί να περιέχουν αποθηκευμένα αποσπάσματα κώδικα

### Υπεύθυνη γνωστοποίηση ευρημάτων

- **Αναφέρετε** τα ευρήματα στην ομάδα ασφάλειας.
- **Μη χρησιμοποιείτε** στοιχεία πρόσβασης που βρήκατε για σύνδεση σε συστήματα.
- **Μη δημοσιεύετε** στοιχεία πρόσβασης.
- **Τεκμηριώστε** το εύρημα με τα ελάχιστα απαραίτητα στοιχεία.

---

<a id="section-14"></a>

## ⚠️ Πλαίσιο εξουσιοδοτημένης χρήσης

> **ΠΡΟΣΟΧΗ:** Η αναζήτηση μπορεί να εμφανίσει δημόσια αναρτημένα μυστικά. Η δημοσίευσή τους δεν επιτρέπει τη χρήση τους.
> 
> - ✅ Αναζήτηση σε δημόσια repos
> - ✅ Αναφορά στο κατάλληλο πρόγραμμα Bug Bounty
> - ✅ Υπεύθυνη γνωστοποίηση
> - ❌ Όχι χρήση στοιχείων πρόσβασης που βρέθηκαν
> - ❌ Όχι πρόσβαση σε συστήματα εκτός scope
> - ❌ Όχι αποθήκευση ή κοινοποίηση πραγματικών μυστικών
> 
> **Μη χρησιμοποιείτε στοιχεία πρόσβασης που εντοπίσατε χωρίς ρητή εξουσιοδότηση.**

---

<a id="section-15"></a>

## 📚 Πηγές

### Εργαλεία
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Παραπομπές
- [Τεκμηρίωση αναζήτησης GitHub](https://docs.github.com/en/search-github)
- [Μοτίβα ανίχνευσης μυστικών](https://docs.github.com/en/code-security/secret-scanning)

### Σχετικοί οδηγοί
- [Εξειδικευμένες αναζητήσεις Google (Google Dorking)](../../Google-Dorking/translations/README.el.md)
- [Shodan](../../Shodan/translations/README.el.md)
- [Subfinder](../../Subfinder/translations/README.el.md)

---

<p align="center">
  <b>🐙 Εντοπίστε ακούσιες δημοσιεύσεις μυστικών</b><br>
  <i>Αναζήτηση και τεκμηρίωση μέσα στους κανόνες του προγράμματος</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
