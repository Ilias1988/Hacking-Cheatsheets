# 🐙 GitHub-Dorking – vollständiges Cheatsheet

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Suche nach geheimen Werten">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Offengelegte geheime Werte, API-Schlüssel und Zugangsdaten in GitHub-Repositories finden</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [GitHub-Suchsyntax](#-github-suchsyntax)
- [API-Schlüssel und Tokens](#-api-schlüssel-und-tokens)
- [Datenbankzugangsdaten](#-datenbankzugangsdaten)
- [Private Schlüssel](#-private-schlüssel)
- [Konfigurationsdateien](#-konfigurationsdateien)
- [Geheime Cloud-Zugangsdaten](#-geheime-cloud-zugangsdaten)
- [Passwörter und Zugangsdaten](#-passwörter-und-zugangsdaten)
- [Bug-Bounty-Ziele](#-bug-bounty-ziele)
- [Fortgeschrittene Techniken](#-fortgeschrittene-techniken)
- [Automatisierungswerkzeuge](#-automatisierungswerkzeuge)
- [Schnellübersicht](#-schnellübersicht)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**GitHub-Dorking** bezeichnet den gezielten Einsatz der erweiterten GitHub-Suche, um offengelegte geheime Werte, Zugangsdaten und sensible Informationen in öffentlichen Repositories zu finden. Ein Dork ist dabei eine gezielt formulierte Suchabfrage.

### Was lässt sich finden?

| Kategorie | Beispiele |
|----------|----------|
| **API-Schlüssel** | AWS, Google, Stripe, Twilio |
| **Tokens** | OAuth, JWT, persönliche Zugriffstokens |
| **Zugangsdaten** | Datenbankpasswörter, SMTP, SSH |
| **Private Schlüssel** | RSA, SSH, PEM-Dateien |
| **Konfigurationsdateien** | .env, config.yml, settings.py |

### Warum das wichtig ist

- Entwickler nehmen geheime Werte häufig versehentlich in Commits auf
- Im Git-Verlauf bleiben gelöschte geheime Werte erhalten
- Öffentliche Repositories werden indexiert und können durchsucht werden
- Häufige Quelle für Bug-Bounty-Funde

---

## 🔧 GitHub-Suchsyntax

### Grundlegende Such-URL

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Suchqualifikatoren

| Qualifikator | Beschreibung | Beispiel |
|-----------|-------------|---------|
| `in:file` | Im Dateiinhalt suchen | `password in:file` |
| `in:path` | Im Dateipfad suchen | `config in:path` |
| `filename:` | Nach Dateinamen suchen | `filename:.env` |
| `extension:` | Nach Dateiendung suchen | `extension:json` |
| `language:` | Nach Programmiersprache suchen | `language:python` |
| `user:` | Repositories eines Nutzers durchsuchen | `user:target` |
| `org:` | Repositories einer Organisation durchsuchen | `org:company` |
| `repo:` | Bestimmtes Repository durchsuchen | `repo:user/repo` |
| `path:` | Im Pfad suchen | `path:config/` |
| `size:` | Dateigröße | `size:>1000` |

### Boolesche Operatoren

```
# AND (Leerzeichen)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Exakte Zeichenfolge
"api_key"

# Kombinieren
org:target "api_key" NOT test
```

---

## 🔑 API-Schlüssel und Tokens

### AWS-Schlüssel

```
# AWS-Zugriffsschlüssel-ID
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Geheimer AWS-Schlüssel
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Kombiniert
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# GCP-API-Schlüssel
"AIza"
"AIzaSy"

# GCP-Dienstkonto
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Stripe-Schlüssel
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Kombiniert
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# Twilio-SID und -Token
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### GitHub-Tokens

```
# Persönliche Zugriffstokens
ghp_
gho_
ghu_
ghs_
ghr_

# GitHub-Tokenmuster
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Slack-Tokens
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Slack-Webhook
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Weitere API-Schlüssel

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

## 💾 Datenbankzugangsdaten

### MySQL

```
# MySQL-Verbindungszeichenfolgen
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Kombiniert
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
# MongoDB-Verbindung
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Mit Zugangsdaten
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

### Allgemeine Datenbanksuche

```
# Verbindungszeichenfolgen
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Zugangsdaten
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Private Schlüssel

### SSH-Schlüssel

```
# Private SSH-Schlüssel
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Dateien
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Kombiniert
filename:id_rsa -example -sample
```

### PGP-/GPG-Schlüssel

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### SSL-/TLS-Zertifikate

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a name="-konfigurationsdateien"></a>

## ⚙️ Konfigurationsdateien

### Umgebungsdateien

```
# .env-Dateien
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Mit geheimen Werten
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Konfigurationsdateien

```
# Verschiedene Konfigurationsdateien
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
# Docker-Konfigurationen
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# K8s-Secrets
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

<a name="-geheime-cloud-zugangsdaten"></a>

## ☁️ Geheime Cloud-Zugangsdaten

### AWS

```
# AWS-Zugangsdaten-Datei
filename:credentials aws_access_key_id
filename:.aws/credentials

# AWS-Konfiguration
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# S3-Buckets
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

## 🔓 Passwörter und Zugangsdaten

### Fest im Code hinterlegte Passwörter

```
# Passwortvariablen
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Mit Werten
password="
password='
"password": "
'password': '
```

### SMTP-Zugangsdaten

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Geheime OAuth-Werte

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Geheime JWT-Werte

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Administratorzugangsdaten

```
# Admin
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Bug-Bounty-Ziele

### Zielorganisation

```
# Bestimmte Organisation durchsuchen
org:target-company

# Repositories eines Nutzers
user:target-username

# Bestimmtes Repository
repo:target/repository

# Mit geheimen Werten kombiniert
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Suche anhand einer Domain

```
# Zieldomain
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Kombiniert
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### E-Mail-Adressen von Mitarbeitenden

```
# Repositories von Mitarbeitenden finden
"@target.com"

# E-Mail-Adressen von Entwicklern
"@target.com" password
"@target.com" config
```

### Interne URLs

```
# Interne Dienste
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Dork-Vorlage für Bug Bounty

```
# Umfassende Bestandsaufnahme
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

## 🔬 Fortgeschrittene Techniken

### Suche im Git-Verlauf

```
# Commits prüfen
# (Werkzeuge wie TruffleHog oder git-secrets verwenden)

# Kürzlich gepusht
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Gists durchsuchen

```
# Gists durchsuchen
https://gist.github.com/search?q=password+target

# Gist-Muster
gist password
gist api_key
```

### Codeausschnitte

```
# Im Code suchen
in:file password
in:path config

# Große Dateien (können mehr Daten enthalten)
size:>10000 password
```

### Regex-Muster (mit Werkzeugen)

```
# AWS-Schlüsselmuster
AKIA[0-9A-Z]{16}

# GitHub-Token
ghp_[a-zA-Z0-9]{36}

# Slack-Token
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Privater Schlüssel
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a name="-automatisierungswerkzeuge"></a>

## 🛠️ Automatisierungswerkzeuge

### TruffleHog

```bash
# Installation
pip install truffleHog

# Repository prüfen
trufflehog git https://github.com/target/repo

# Organisation prüfen
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# Installation
brew install gitleaks

# Repository prüfen
gitleaks detect -s /path/to/repo

# Über eine URL prüfen
gitleaks detect --source https://github.com/target/repo

# JSON ausgeben
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Installation
brew install git-secrets

# Muster hinzufügen
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Repository prüfen
git secrets --scan
```

### Werkzeuge zur GitHub-Suche

```bash
# GitHub-Dork-CLI
# Verschiedene Community-Werkzeuge verfügbar

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# GitHub-Überwachung in Echtzeit
# Überwacht in Echtzeit auf geheime Werte
shhgit --search-query "org:target"
```

---

## 📊 Schnellübersicht

### Wichtige Dorks

| Zweck | Dork |
|---------|------|
| AWS-Schlüssel | `org:target AKIA` |
| Passwörter | `org:target password filename:.env` |
| API-Schlüssel | `org:target api_key` |
| Private Schlüssel | `org:target "BEGIN RSA PRIVATE KEY"` |
| Konfigurationsdateien | `org:target filename:config.json` |
| Datenbankzugangsdaten | `org:target "mongodb://"` |
| Tokens | `org:target token secret` |
| E-Mail-Adressen | `"@target.com" password` |

### Dateimuster

| Muster | Dork |
|---------|------|
| `.env` | `filename:.env` |
| JSON-Konfiguration | `filename:config.json` |
| Einstellungen | `filename:settings.py` |
| Zugangsdaten | `filename:credentials` |
| Geheime Werte | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### 15 wichtige GitHub-Dorks

| # | Dork | Zweck |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Datenbankpasswörter |
| 2 | `filename:.env AWS_` | AWS-Zugangsdaten |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Private Schlüssel |
| 4 | `filename:config.json password` | Passwörter in Konfigurationen |
| 5 | `AKIA` | AWS-Zugriffsschlüssel |
| 6 | `sk_live` | Stripe-Schlüssel |
| 7 | `"mongodb+srv://"` | MongoDB-URIs |
| 8 | `filename:credentials aws` | AWS-Zugangsdaten-Datei |
| 9 | `ghp_` | GitHub-Tokens |
| 10 | `xoxb-` | Slack-Tokens |
| 11 | `org:target password` | Organisationspasswörter |
| 12 | `filename:.npmrc _auth` | NPM-Tokens |
| 13 | `extension:sql password` | SQL-Dumps |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | API-Schlüssel |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Bug-Bounty-Tipps

1. **Organisation durchsuchen**
   ```
   org:target-company
   ```

2. **Repositories von Mitarbeitenden prüfen**
   ```
   "@target.com"
   ```

3. **Nach internen URLs suchen**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Git-Verlauf durchsuchen**
   - TruffleHog oder GitLeaks verwenden

5. **Gists prüfen**
   - Entwickler speichern dort häufig Codeausschnitte

### Verantwortungsvolle Offenlegung

- Funde dem Sicherheitsteam **melden**
- Mit gefundenen Zugangsdaten **nicht** auf Systeme zugreifen
- Zugangsdaten **nicht** öffentlich teilen
- Funde nachvollziehbar **dokumentieren**

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** GitHub-Dorking macht öffentlich zugängliche Informationen auffindbar. Dabei gilt:
> 
> - ✅ Öffentliche Repositories durchsuchen
> - ✅ Funde an Bug-Bounty-Programme melden
> - ✅ Verantwortungsvolle Offenlegung
> - ❌ Gefundene Zugangsdaten niemals verwenden
> - ❌ Nicht auf Systeme zugreifen
> - ❌ Geheime Werte nicht speichern oder teilen
> 
> **Die Nutzung gefundener Zugangsdaten ist illegal!**

---

## 📚 Ressourcen

### Werkzeuge
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Referenzen
- [GitHub-Suchdokumentation](https://docs.github.com/en/search-github)
- [Muster für Secret Scanning](https://docs.github.com/en/code-security/secret-scanning)

### Verwandte Cheatsheets
- [Google Dorking](../../Google-Dorking/translations/README.de.md)
- [Shodan](../../Shodan/translations/README.de.md)
- [Subfinder](../../Subfinder/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Suchsyntax:** Die Vorlage mischt ältere Qualifikatoren und heutige Code Search. Die aktuelle Codesuche verwendet unter anderem path und content; filename, extension, in:file, in:path und size aus der Tabelle sind nicht die aktuelle dokumentierte Qualifikatorenliste. Zum Ausschließen dient NOT; Regex-Muster benötigen Schrägstriche statt bloßer Anführungszeichen. Die Originalabfragen bleiben unverändert und sind nicht für jede Suchoberfläche gleich auszuwerten. [GitHub: aktuelle Codesuchsyntax](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).
- **Suchumfang und Verlauf:** Code Search erfordert eine Anmeldung und durchsucht derzeit den Standardbranch, nicht den gesamten Commit-Verlauf. Nicht jeder Inhalt wird indexiert; ein leeres Ergebnis ist kein Vollständigkeitsnachweis. Die pushed-Beispiele sind kein Ersatz für eine Analyse historischer Commits. Websuche und REST-Codesuche haben außerdem unterschiedliche Grenzen und Syntaxkontexte. [GitHub: Codesuche und Grenzen](https://docs.github.com/en/search-github/github-code-search/about-github-code-search), [GitHub: REST-Suche](https://docs.github.com/en/rest/search/search#search-code).
- **Ein Treffer ist nicht automatisch ein Geheimnis:** Öffentlich vorgesehene Firebase-API-Schlüssel und veröffentlichbare Stripe-Schlüssel mit pk-Präfix sind nicht mit geheimen Serverzugangsdaten gleichzusetzen. Entscheidend sind Schlüsseltyp, Berechtigungen und Konfiguration. Auch öffentliche Zertifikate sind keine privaten Schlüssel; die gezeigten Präfixe und Regex-Muster sind weder vollständig noch ein Nachweis gültiger Zugangsdaten. [Firebase: API-Schlüssel](https://firebase.google.com/docs/projects/api-keys), [Stripe: Schlüsseltypen](https://docs.stripe.com/keys).
- **Werkzeugversionen und aktive Verifikation:** Der TruffleHog-Abschnitt mischt alte Python-Installation und neuere CLI-Ideen. Die aktuelle Version ist in Go geschrieben; der git-Modus erwartet ein Repository, keine Organisationsseite. Für Organisationen dokumentiert das Projekt einen eigenen GitHub-Modus. Verifizierte Treffer entstehen durch Tests gegen Dienst-APIs und damit durch aktive Zugriffe. Die gezeigten Befehle sind keine rein passive Suche. [TruffleHog: offizielle Anleitung und Verifikation](https://github.com/trufflesecurity/trufflehog).
- **Gitleaks und weitere CLI-Beispiele:** detect und protect sind seit Gitleaks 8.19.0 veraltet; die dokumentierten Modi git und dir arbeiten mit lokalen Quellen. Eine GitHub-URL als source ist deshalb kein allgemein gültiger Ersatz für einen lokalen Pfad. Optionen anderer Werkzeuge hängen ebenfalls von der konkreten Version ab. Die ursprünglichen Befehle bleiben als historische Beispiele erhalten. [Gitleaks: Befehle](https://github.com/gitleaks/gitleaks#commands).
- **Berechtigung:** Der pauschale Rechtssatz der Vorlage ersetzt keine Prüfung des konkreten Auftrags. Öffentliche Sichtbarkeit von Informationen erteilt keine Zugriffserlaubnis für die damit verbundenen Systeme. Auch GitHub verlangt die Einhaltung seiner Nutzungsregeln und anwendbaren Vorschriften. [GitHub: Acceptable Use Policies](https://docs.github.com/en/site-policy/acceptable-use-policies/github-acceptable-use-policies).

---

<p align="center">
  <b>🐙 Geheime Werte vor Angreifern entdecken!</b><br>
  <i>GitHub-Dorking für Bug Bounty sicher beherrschen</i>
</p>
