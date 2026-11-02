# 🐙 GitHub-dorking – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <b>🔐 Finn eksponerte hemmeligheter, API-nøkler og påloggingsopplysninger i GitHub-repoer</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [🎯 Innledning](#-innledning)
- [🔧 GitHubs søkesyntaks](#-githubs-søkesyntaks)
- [🔑 API-nøkler og tokener](#-api-nøkler-og-tokener)
- [💾 Påloggingsopplysninger for databaser](#-påloggingsopplysninger-for-databaser)
- [🔐 Private nøkler](#-private-nøkler)
- [⚙️ Konfigurasjonsfiler](#-konfigurasjonsfiler)
- [☁️ Hemmeligheter i skyen](#-hemmeligheter-i-skyen)
- [🔓 Passord og påloggingsopplysninger](#-passord-og-påloggingsopplysninger)
- [🎯 Bug bounty-mål](#-bug-bounty-mål)
- [🔬 Avanserte teknikker](#-avanserte-teknikker)
- [🛠️ Automatiseringsverktøy](#-automatiseringsverktøy)
- [📊 Hurtigreferanse](#-hurtigreferanse)
- [📚 Ressurser](#-ressurser)

---

> **Begreper og versjoner:** Et repo er et kodelager; et token er en tilgangsverdi, og hemmelighetsskanning leter etter slike verdier. Originalen blander eldre kodesøkkvalifikatorer, andre søketyper og verktøysyntaks. Moderne GitHub Code Search og det eldre API-søket har ulike regler; se [GitHubs søkesyntaks](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax). Vanlig kodesøk er ikke et fullstendig søk gjennom commit-historikken; `pushed:` gjør ikke et kodesøk til historiesøk. Regex-lignende tekst i anførselstegn er ikke automatisk et regulært uttrykk. Tokenprefikser og generasjonsmønstre endres, og sertifikater eller publiserbare nøkler er ikke nødvendigvis hemmeligheter. Verktøyenes installasjon og CLI-flagg er versjonsavhengige: den viste pip-installasjonen kan gjelde eldre TruffleHog; TruffleHogs viste git-linje med organisasjons-URL skanner ikke automatisk alle organisasjonens repoer, og Gitleaks-eksemplet med en URL er ikke generelt det samme som en lokal sti til et repo. Se [TruffleHog](https://github.com/trufflesecurity/trufflehog) og [Gitleaks](https://github.com/gitleaks/gitleaks). Eksemplene er bevart; ingen påloggingsopplysninger ble søkt opp eller prøvd.

## 🎯 Innledning

**GitHub-dorking** bruker GitHubs avanserte søk til å finne eksponerte hemmeligheter, påloggingsopplysninger og sensitive data i offentlige repoer.

### Hva kan du finne?

| Kategori | Eksempler |
|----------|----------|
| **API-nøkler** | AWS, Google, Stripe, Twilio |
| **Tokener** | OAuth, JWT og personlige tilgangstokener |
| **Påloggingsopplysninger** | Databasepassord, SMTP, SSH |
| **Private nøkler** | RSA, SSH og PEM-filer |
| **Konfigurasjonsfiler** | .env, config.yml, settings.py |

### Hvorfor er dette viktig?

- Utviklere legger noen ganger hemmeligheter inn i commits ved et uhell
- Git-historikken kan bevare slettede hemmeligheter
- Offentlige repoer indekseres og kan søkes i
- En viktig kilde til bug bounty-funn

---

## 🔧 GitHubs søkesyntaks

### Grunnleggende søke-URL

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Søkeavgrensninger

| Avgrensning | Beskrivelse | Eksempel |
|-----------|-------------|---------|
| `in:file` | Søk i filinnhold | `password in:file` |
| `in:path` | Søk i filsti | `config in:path` |
| `filename:` | Søk etter filnavn | `filename:.env` |
| `extension:` | Søk etter filendelse | `extension:json` |
| `language:` | Søk etter språk | `language:python` |
| `user:` | Søk i brukerens repoer | `user:target` |
| `org:` | Søk i organisasjonens repoer | `org:company` |
| `repo:` | Søk i et bestemt repo | `repo:user/repo` |
| `path:` | Søk i sti | `path:config/` |
| `size:` | Filstørrelse | `size:>1000` |

### Boolske operatorer

```
# OG (mellomrom)
password database

# ELLER
password OR secret

# IKKE (-)
password -test -example

# Nøyaktig frase
"api_key"

# Kombiner
org:target "api_key" NOT test
```

---

## 🔑 API-nøkler og tokener

### AWS-nøkler

```
# AWS-tilgangsnøkkel-ID
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Hemmelig AWS-nøkkel
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Kombinert
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# GCP-API-nøkkel
"AIza"
"AIzaSy"

# GCP-tjenestekonto
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Stripe-nøkler
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Kombinert
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# Twilio-SID og token
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### GitHub-tokener

```
# Personlige tilgangstokener
ghp_
gho_
ghu_
ghs_
ghr_

# Mønstre for GitHub-tokener
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Slack-tokener
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Slack-webhook
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Andre API-nøkler

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

## 💾 Påloggingsopplysninger for databaser

### MySQL

```
# MySQL-tilkoblingsstrenger
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Kombinert
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
# MongoDB-tilkobling
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Med påloggingsopplysninger
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

### Generell database

```
# Tilkoblingsstrenger
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Påloggingsopplysninger
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Private nøkler

### SSH-nøkler

```
# Private SSH-nøkler
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Filer
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Kombinert
filename:id_rsa -example -sample
```

### PGP-/GPG-nøkler

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### SSL-/TLS-sertifikater

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-konfigurasjonsfiler"></a>
## ⚙️ Konfigurasjonsfiler

### Filer med miljøvariabler

```
# .env-filer
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Med hemmeligheter
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Konfigurasjonsfiler

```
# Ulike konfigurasjonsfiler
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
# Docker-konfigurasjoner
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# K8s-hemmeligheter
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

<a id="-hemmeligheter-i-skyen"></a>
## ☁️ Hemmeligheter i skyen

### AWS

```
# AWS-fil med påloggingsopplysninger
filename:credentials aws_access_key_id
filename:.aws/credentials

# AWS-konfigurasjon
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# S3-bøtter
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

## 🔓 Passord og påloggingsopplysninger

### Hardkodede passord

```
# Passordvariabler
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Med verdier
password="
password='
"password": "
'password': '
```

### SMTP-påloggingsopplysninger

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### OAuth-hemmeligheter

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### JWT-hemmeligheter

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Administratorpålogging

```
# Administrator
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Bug bounty-mål

### Målorganisasjon

```
# Søk i en bestemt organisasjon
org:target-company

# Brukerrepoer
user:target-username

# Bestemt repo
repo:target/repository

# Kombinert med hemmeligheter
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Domenebasert søk

```
# Måldomene
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Kombinert
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### E-postadresser til ansatte

```
# Finn ansattes repoer
"@target.com"

# Utviklernes e-postadresser
"@target.com" password
"@target.com" config
```

### Interne URL-er

```
# Interne tjenester
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Dorkmal for bug bounty

```
# Full kartlegging
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

## 🔬 Avanserte teknikker

### Søk i Git-historikken

```
# Undersøk commits
# (Bruk verktøy som TruffleHog og git-secrets)

# Nylig pushet
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Søk i Gists

```
# Søk etter Gists
https://gist.github.com/search?q=password+target

# Gist-mønstre
gist password
gist api_key
```

### Kodeutdrag

```
# Søk i kode
in:file password
in:path config

# Store filer (kan inneholde flere opplysninger)
size:>10000 password
```

### Regulære uttrykk med verktøy

```
# Mønster for AWS-nøkkel
AKIA[0-9A-Z]{16}

# GitHub-token
ghp_[a-zA-Z0-9]{36}

# Slack-token
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Privat nøkkel
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-automatiseringsverktøy"></a>
## 🛠️ Automatiseringsverktøy

### TruffleHog

```bash
# Installer
pip install truffleHog

# Skann repo
trufflehog git https://github.com/target/repo

# Skann organisasjon
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# Installer
brew install gitleaks

# Skann repo
gitleaks detect -s /path/to/repo

# Skann fra URL
gitleaks detect --source https://github.com/target/repo

# Utdata i JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Installer
brew install git-secrets

# Legg til mønstre
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Skann repo
git secrets --scan
```

### Verktøy for GitHub-søk

```bash
# GitHub-dorking fra kommandolinjen
# Ulike fellesskapsverktøy finnes

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# GitHub-overvåking i sanntid
# Overvåker hemmeligheter i sanntid
shhgit --search-query "org:target"
```

---

## 📊 Hurtigreferanse

### Viktige dorker

| Formål | Dork |
|---------|------|
| AWS-nøkler | `org:target AKIA` |
| Passord | `org:target password filename:.env` |
| API-nøkler | `org:target api_key` |
| Private nøkler | `org:target "BEGIN RSA PRIVATE KEY"` |
| Konfigurasjonsfiler | `org:target filename:config.json` |
| Databasepålogging | `org:target "mongodb://"` |
| Tokener | `org:target token secret` |
| E-postadresser | `"@target.com" password` |

### Filmønstre

| Mønster | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Konfigurasjon i JSON | `filename:config.json` |
| Innstillinger | `filename:settings.py` |
| Påloggingsopplysninger | `filename:credentials` |
| Hemmeligheter | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### Femten GitHub-dorker

| # | Dork | Formål |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Databasepassord |
| 2 | `filename:.env AWS_` | AWS-pålogging |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Private nøkler |
| 4 | `filename:config.json password` | Konfigurasjonspassord |
| 5 | `AKIA` | AWS-tilgangsnøkler |
| 6 | `sk_live` | Stripe-nøkler |
| 7 | `"mongodb+srv://"` | MongoDB-URI-er |
| 8 | `filename:credentials aws` | AWS-fil med pålogging |
| 9 | `ghp_` | GitHub-tokener |
| 10 | `xoxb-` | Slack-tokener |
| 11 | `org:target password` | Organisasjonspassord |
| 12 | `filename:.npmrc _auth` | NPM-tokener |
| 13 | `extension:sql password` | SQL-uttrekk |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | API-nøkler |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for bug bounty

1. **Søk i organisasjonen**
   ```
   org:target-company
   ```

2. **Undersøk ansattes repoer**
   ```
   "@target.com"
   ```

3. **Se etter interne URL-er**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Søk i Git-historikken**
   - Bruk TruffleHog eller GitLeaks

5. **Undersøk Gists**
   - Utviklere lagrer ofte kodeutdrag der

### Ansvarlig offentliggjøring

- **Rapporter** funn til sikkerhetsteamet
- **Ikke** få tilgang til systemer med påloggingsopplysningene du finner
- **Ikke** del påloggingsopplysninger offentlig
- **Dokumenter** funnene på en ordentlig måte

---

## ⚠️ Juridisk merknad

> **ADVARSEL:** GitHub-dorking viser offentlig tilgjengelige opplysninger. Likevel gjelder følgende:
>
> - ✅ Søk i offentlige repoer
> - ✅ Rapporter til bug bounty-programmer
> - ✅ Ansvarlig offentliggjøring
> - ❌ Bruk aldri påloggingsopplysningene du finner
> - ❌ Ikke få tilgang til systemer
> - ❌ Ikke lagre eller del hemmeligheter
>
> **Bruk av påloggingsopplysninger du finner, kan være ulovlig uten tillatelse!**

---

## 📚 Ressurser

### Verktøy
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Referanser
- [GitHubs søkedokumentasjon](https://docs.github.com/en/search-github)
- [Mønstre for hemmelighetsskanning](https://docs.github.com/en/code-security/secret-scanning)

### Relaterte hurtigreferanser
- [Google Dorking](../../Google-Dorking/translations/README.nb.md)
- [Shodan](../../Shodan/translations/README.nb.md)
- [Subfinder](../../Subfinder/translations/README.nb.md)

---

<p align="center">
  <b>🐙 Finn hemmelighetene før angriperne gjør det!</b><br>
  <i>Lær GitHub-dorking til bug bounty</i>
</p>
