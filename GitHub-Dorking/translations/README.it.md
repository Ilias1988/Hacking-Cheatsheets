# 🐙 GitHub Dorking - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <b>🔐 Individua segreti, chiavi API e credenziali esposti nei repository GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sulle versioni:** l’originale mescola ricerca legacy, Code Search e qualificatori per repository. La sintassi non è intercambiabile: nel Code Search attuale percorsi, contenuti, regex e operatori booleani seguono la [documentazione ufficiale](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax); `pushed:` riguarda la ricerca di repository e non scansiona la cronologia del codice. Le stringhe regex tra virgolette non diventano automaticamente regex. Gli esempi dei token sono parziali e non provano validità o segretezza di una chiave. Anche i comandi degli strumenti sono storici: `pip install truffleHog` non identifica l’attuale distribuzione Go di [TruffleHog](https://github.com/trufflesecurity/trufflehog), e sintassi/sorgenti locali o remote di [Gitleaks](https://github.com/gitleaks/gitleaks) dipendono dalla release. Tutti gli esempi restano invariati; le affermazioni legali originali non valutano il singolo caso.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Sintassi di ricerca GitHub](#-sintassi-di-ricerca-github)
- [Chiavi API e token](#-chiavi-api-e-token)
- [Credenziali dei database](#-credenziali-dei-database)
- [Chiavi private](#-chiavi-private)
- [File di configurazione](#-file-di-configurazione)
- [Segreti cloud](#-segreti-cloud)
- [Password e credenziali](#-password-e-credenziali)
- [Bersagli di bug bounty](#-bersagli-di-bug-bounty)
- [Tecniche avanzate](#-tecniche-avanzate)
- [Strumenti di automazione](#-strumenti-di-automazione)
- [Riferimento rapido](#-riferimento-rapido)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

Il **GitHub Dorking** usa la ricerca avanzata di GitHub per individuare segreti, credenziali e informazioni sensibili esposti nei repository pubblici.

### Che cosa puoi trovare?

| Categoria | Esempi |
|----------|----------|
| **Chiavi API** | AWS, Google, Stripe, Twilio |
| **Token** | OAuth, JWT e token di accesso personali (Personal Access Token) |
| **Credenziali** | Password dei database, SMTP e SSH |
| **Chiavi private** | RSA, SSH e file PEM |
| **File di configurazione** | .env, config.yml, settings.py |

### Perché è importante

- Gli sviluppatori talvolta includono involontariamente segreti nei commit
- La cronologia Git conserva anche i segreti eliminati dai file
- I repository pubblici sono indicizzati e ricercabili
- È una fonte importante di segnalazioni nei programmi di bug bounty

---

## 🔧 Sintassi di ricerca GitHub

### URL di ricerca di base

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Qualificatori di ricerca

| Qualificatore | Descrizione | Esempio |
|-----------|-------------|---------|
| `in:file` | Ricerca nel contenuto dei file | `password in:file` |
| `in:path` | Ricerca nel percorso dei file | `config in:path` |
| `filename:` | Ricerca per nome del file | `filename:.env` |
| `extension:` | Ricerca per estensione | `extension:json` |
| `language:` | Ricerca per linguaggio | `language:python` |
| `user:` | Ricerca nei repository di un utente | `user:target` |
| `org:` | Ricerca nei repository di un'organizzazione | `org:company` |
| `repo:` | Ricerca in un repository specifico | `repo:user/repo` |
| `path:` | Ricerca nel percorso | `path:config/` |
| `size:` | Dimensione del file | `size:>1000` |

### Operatori booleani

```
# AND (spazio)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Frase esatta
"api_key"

# Combina
org:target "api_key" NOT test
```

---

## 🔑 Chiavi API e token

### Chiavi AWS

```
# Identificativo della chiave di accesso AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Chiave segreta AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Combinazioni
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# Chiave API GCP
"AIza"
"AIzaSy"

# Account di servizio GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Chiavi Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Combinazioni
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# SID e token Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### Token GitHub

```
# Token di accesso personali
ghp_
gho_
ghu_
ghs_
ghr_

# Schemi dei token GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Token Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Webhook Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Altre chiavi API

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

## 💾 Credenziali dei database

### MySQL

```
# Stringhe di connessione MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Combinazioni
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
# Connessione MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Con credenziali
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

### Database generico

```
# Stringhe di connessione
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Credenziali
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Chiavi private

### Chiavi SSH

```
# Chiavi SSH private
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# File
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Combinazioni
filename:id_rsa -example -sample
```

### Chiavi PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### Certificati SSL/TLS

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

## ⚙ File di configurazione

### File delle variabili d'ambiente

```
# File .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Con segreti
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### File di configurazione

```
# Vari file di configurazione
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
# Configurazioni Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Segreti K8s
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

## ☁ Segreti cloud

### AWS

```
# File delle credenziali AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Configurazione AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Bucket S3
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

## 🔓 Password e credenziali

### Password incorporate nel codice

```
# Variabili delle password
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Con valori
password="
password='
"password": "
'password': '
```

### Credenziali SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Segreti OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Segreti JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Credenziali di amministrazione

```
# Amministrazione
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Bersagli di bug bounty

### Organizzazione bersaglio

```
# Ricerca in un'organizzazione specifica
org:target-company

# Repository dell'utente
user:target-username

# Repository specifico
repo:target/repository

# Combinazioni con segreti
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Ricerca basata sul dominio

```
# Dominio bersaglio
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Combinazioni
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Email dei dipendenti

```
# Individua i repository dei dipendenti
"@target.com"

# Email degli sviluppatori
"@target.com" password
"@target.com" config
```

### URL interni

```
# Servizi interni
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Modello di dork per il bug bounty

```
# Ricognizione completa
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

## 🔬 Tecniche avanzate

### Ricerca nella cronologia Git

```
# Controlla i commit
# (Usa strumenti come TruffleHog e git-secrets)

# Push recenti
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Ricerca nei Gist

```
# Cerca nei Gist
https://gist.github.com/search?q=password+target

# Schemi per i Gist
gist password
gist api_key
```

### Frammenti di codice

```
# Ricerca nel codice
in:file password
in:path config

# File grandi (possono contenere più dati)
size:>10000 password
```

### Schemi regex (con strumenti)

```
# Schema delle chiavi AWS
AKIA[0-9A-Z]{16}

# Token GitHub
ghp_[a-zA-Z0-9]{36}

# Token Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Chiave privata
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

## 🛠 Strumenti di automazione

### TruffleHog

```bash
# Installa
pip install truffleHog

# Analizza il repository
trufflehog git https://github.com/target/repo

# Analizza l'organizzazione
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# Installa
brew install gitleaks

# Analizza il repository
gitleaks detect -s /path/to/repo

# Analizza a partire dall'URL
gitleaks detect --source https://github.com/target/repo

# Output JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Installa
brew install git-secrets

# Aggiungi gli schemi
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Analizza il repository
git secrets --scan
```

### Strumenti di ricerca GitHub

```bash
# CLI per i dork GitHub
# Sono disponibili vari strumenti della comunità

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Monitoraggio GitHub in tempo reale
# Monitora la presenza di segreti in tempo reale
shhgit --search-query "org:target"
```

---

## 📊 Riferimento rapido

### Dork essenziali

| Scopo | Dork |
|---------|------|
| Chiavi AWS | `org:target AKIA` |
| Password | `org:target password filename:.env` |
| Chiavi API | `org:target api_key` |
| Chiavi private | `org:target "BEGIN RSA PRIVATE KEY"` |
| File di configurazione | `org:target filename:config.json` |
| Credenziali dei database | `org:target "mongodb://"` |
| Token | `org:target token secret` |
| Email | `"@target.com" password` |

### Schemi dei file

| Schema | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Configurazione JSON | `filename:config.json` |
| Impostazioni | `filename:settings.py` |
| Credenziali | `filename:credentials` |
| Segreti | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### 15 dork principali per GitHub

| # | Dork | Scopo |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Password dei database |
| 2 | `filename:.env AWS_` | Credenziali AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Chiavi private |
| 4 | `filename:config.json password` | Password nelle configurazioni |
| 5 | `AKIA` | Chiavi di accesso AWS |
| 6 | `sk_live` | Chiavi Stripe |
| 7 | `"mongodb+srv://"` | MongoDB URIs |
| 8 | `filename:credentials aws` | File delle credenziali AWS |
| 9 | `ghp_` | Token GitHub |
| 10 | `xoxb-` | Token Slack |
| 11 | `org:target password` | Password dell'organizzazione |
| 12 | `filename:.npmrc _auth` | Token NPM |
| 13 | `extension:sql password` | Dump SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Chiavi API |

---

## 💡 Consigli e buone pratiche

### Consigli per il bug bounty

1. **Cerca nell'organizzazione**
   ```
   org:target-company
   ```

2. **Controlla i repository dei dipendenti**
   ```
   "@target.com"
   ```

3. **Cerca gli URL interni**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Cerca nella cronologia Git**
   - Usa TruffleHog o GitLeaks

5. **Controlla i Gist**
   - Gli sviluppatori vi conservano spesso frammenti di codice

### Divulgazione responsabile

- **Segnala** i risultati al team di sicurezza
- **Non** accedere ai sistemi con le credenziali trovate
- **Non** condividere pubblicamente le credenziali
- **Documenta** adeguatamente i risultati

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** il GitHub Dorking rivela informazioni pubblicamente accessibili. Tuttavia:
> 
> - ✅ Cerca nei repository pubblici
> - ✅ Invia segnalazioni ai programmi di bug bounty
> - ✅ Adotta una divulgazione responsabile
> - ❌ Non usare mai le credenziali trovate
> - ❌ Non accedere ai sistemi
> - ❌ Non conservare o condividere segreti
> 
> **Usare le credenziali trovate è illegale!**

---

## 📚 Risorse

### Strumenti
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Riferimenti
- [Documentazione di ricerca GitHub](https://docs.github.com/en/search-github)
- [Schemi di secret scanning](https://docs.github.com/en/code-security/secret-scanning)

### Schede correlate
- [Google Dorking](../../Google-Dorking/translations/README.it.md)
- [Shodan](../../Shodan/translations/README.it.md)
- [Subfinder](../../Subfinder/translations/README.it.md)

---

<p align="center">
  <b>🐙 Trova i segreti prima degli attaccanti!</b><br>
  <i>Padroneggia il GitHub Dorking per il bug bounty</i>
</p>
