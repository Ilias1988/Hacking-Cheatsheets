# 🐙 GitHub Dorking - Complete Cheatsheet

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
  <b>🔐 Find Exposed Secrets, API Keys, and Credentials in GitHub Repositories</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [GitHub Search Syntax](#-github-search-syntax)
- [API Keys & Tokens](#-api-keys--tokens)
- [Database Credentials](#-database-credentials)
- [Private Keys](#-private-keys)
- [Configuration Files](#-configuration-files)
- [Cloud Secrets](#-cloud-secrets)
- [Passwords & Credentials](#-passwords--credentials)
- [Bug Bounty Targets](#-bug-bounty-targets)
- [Advanced Techniques](#-advanced-techniques)
- [Automation Tools](#-automation-tools)
- [Quick Reference](#-quick-reference)
- [Resources](#-resources)

---

## 🎯 Introduction

**GitHub Dorking** is the technique of using GitHub's advanced search to find exposed secrets, credentials, and sensitive information in public repositories.

### What Can You Find?

| Category | Examples |
|----------|----------|
| **API Keys** | AWS, Google, Stripe, Twilio |
| **Tokens** | OAuth, JWT, Personal Access Tokens |
| **Credentials** | Database passwords, SMTP, SSH |
| **Private Keys** | RSA, SSH, PEM files |
| **Config Files** | .env, config.yml, settings.py |

### Why This Matters

- Developers often accidentally commit secrets
- Git history preserves deleted secrets
- Public repos are indexed and searchable
- Major source of bug bounty findings

---

## 🔧 GitHub Search Syntax

### Basic Search URL

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Search Qualifiers

| Qualifier | Description | Example |
|-----------|-------------|---------|
| `in:file` | Search in file content | `password in:file` |
| `in:path` | Search in file path | `config in:path` |
| `filename:` | Search by filename | `filename:.env` |
| `extension:` | Search by extension | `extension:json` |
| `language:` | Search by language | `language:python` |
| `user:` | Search user's repos | `user:target` |
| `org:` | Search org's repos | `org:company` |
| `repo:` | Search specific repo | `repo:user/repo` |
| `path:` | Search in path | `path:config/` |
| `size:` | File size | `size:>1000` |

### Boolean Operators

```
# AND (space)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Exact phrase
"api_key"

# Combine
org:target "api_key" NOT test
```

---

## 🔑 API Keys & Tokens

### AWS Keys

```
# AWS Access Key ID
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# AWS Secret Key
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Combined
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# GCP API Key
"AIza"
"AIzaSy"

# GCP Service Account
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

```
# Stripe Keys
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Combined
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# Twilio SID & Token
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### GitHub Tokens

```
# Personal Access Tokens
ghp_
gho_
ghu_
ghs_
ghr_

# GitHub Token patterns
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Slack Tokens
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Slack Webhook
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Other API Keys

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

## 💾 Database Credentials

### MySQL

```
# MySQL Connection Strings
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Combined
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
# MongoDB Connection
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# With credentials
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

### Generic Database

```
# Connection strings
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Credentials
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Private Keys

### SSH Keys

```
# Private SSH Keys
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Files
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Combined
filename:id_rsa -example -sample
```

### PGP/GPG Keys

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### SSL/TLS Certificates

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

## ⚙️ Configuration Files

### Environment Files

```
# .env files
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# With secrets
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Config Files

```
# Various config files
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
# Docker configs
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# K8s secrets
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

## ☁️ Cloud Secrets

### AWS

```
# AWS Credentials File
filename:credentials aws_access_key_id
filename:.aws/credentials

# AWS Config
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# S3 Buckets
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

## 🔓 Passwords & Credentials

### Hardcoded Passwords

```
# Password variables
password=
passwd=
pass=
pwd=
"password:"
"password ="

# With values
password="
password='
"password": "
'password': '
```

### SMTP Credentials

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### OAuth Secrets

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### JWT Secrets

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Admin Credentials

```
# Admin
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Bug Bounty Targets

### Target Organization

```
# Search specific org
org:target-company

# User repos
user:target-username

# Specific repo
repo:target/repository

# Combined with secrets
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Domain-Based Search

```
# Target domain
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Combined
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Employee Emails

```
# Find employee repos
"@target.com"

# Developer emails
"@target.com" password
"@target.com" config
```

### Internal URLs

```
# Internal services
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Bug Bounty Dork Template

```
# Complete recon
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

## 🔬 Advanced Techniques

### Git History Search

```
# Check commits
# (Use tools like TruffleHog, git-secrets)

# Recently pushed
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Gist Search

```
# Search Gists
https://gist.github.com/search?q=password+target

# Gist patterns
gist password
gist api_key
```

### Code Snippets

```
# Search in code
in:file password
in:path config

# Large files (might have more data)
size:>10000 password
```

### Regex Patterns (with tools)

```
# AWS Key Pattern
AKIA[0-9A-Z]{16}

# GitHub Token
ghp_[a-zA-Z0-9]{36}

# Slack Token
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Private Key
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

## 🛠️ Automation Tools

### TruffleHog

```bash
# Install
pip install truffleHog

# Scan repo
trufflehog git https://github.com/target/repo

# Scan org
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

```bash
# Install
brew install gitleaks

# Scan repo
gitleaks detect -s /path/to/repo

# Scan from URL
gitleaks detect --source https://github.com/target/repo

# Output JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Install
brew install git-secrets

# Add patterns
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Scan repo
git secrets --scan
```

### GitHub Search Tools

```bash
# GitHub Dork CLI
# Various community tools available

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Real-time GitHub monitoring
# Monitors for secrets in real-time
shhgit --search-query "org:target"
```

---

## 📊 Quick Reference

### Essential Dorks

| Purpose | Dork |
|---------|------|
| AWS Keys | `org:target AKIA` |
| Passwords | `org:target password filename:.env` |
| API Keys | `org:target api_key` |
| Private Keys | `org:target "BEGIN RSA PRIVATE KEY"` |
| Config Files | `org:target filename:config.json` |
| DB Creds | `org:target "mongodb://"` |
| Tokens | `org:target token secret` |
| Emails | `"@target.com" password` |

### File Patterns

| Pattern | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Config JSON | `filename:config.json` |
| Settings | `filename:settings.py` |
| Credentials | `filename:credentials` |
| Secrets | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### Top 15 GitHub Dorks

| # | Dork | Purpose |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | DB passwords |
| 2 | `filename:.env AWS_` | AWS creds |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Private keys |
| 4 | `filename:config.json password` | Config passwords |
| 5 | `AKIA` | AWS Access Keys |
| 6 | `sk_live` | Stripe keys |
| 7 | `"mongodb+srv://"` | MongoDB URIs |
| 8 | `filename:credentials aws` | AWS creds file |
| 9 | `ghp_` | GitHub tokens |
| 10 | `xoxb-` | Slack tokens |
| 11 | `org:target password` | Org passwords |
| 12 | `filename:.npmrc _auth` | NPM tokens |
| 13 | `extension:sql password` | SQL dumps |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | API keys |

---

## 💡 Tips & Best Practices

### Bug Bounty Tips

1. **Search the Organization**
   ```
   org:target-company
   ```

2. **Check Employee Repos**
   ```
   "@target.com"
   ```

3. **Look for Internal URLs**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Search Git History**
   - Use TruffleHog or GitLeaks

5. **Check Gists**
   - Developers often store snippets

### Responsible Disclosure

- **Report** findings to security team
- **Don't** access systems with found credentials
- **Don't** share credentials publicly
- **Document** your findings properly

---

## ⚠️ Legal Disclaimer

> **WARNING:** GitHub Dorking reveals publicly accessible information. However:
> 
> - ✅ Search public repositories
> - ✅ Report to bug bounty programs
> - ✅ Responsible disclosure
> - ❌ Never use found credentials
> - ❌ Don't access systems
> - ❌ Don't store/share secrets
> 
> **Using found credentials is illegal!**

---

## 📚 Resources

### Tools
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### References
- [GitHub Search Docs](https://docs.github.com/en/search-github)
- [Secret Scanning Patterns](https://docs.github.com/en/code-security/secret-scanning)

### Related Cheatsheets
- [Google Dorking](../Google-Dorking/README.md)
- [Shodan](../Shodan/README.md)
- [Subfinder](../Subfinder/README.md)

---

<p align="center">
  <b>🐙 Find Secrets Before Attackers Do!</b><br>
  <i>Master GitHub Dorking for bug bounty</i>
</p>
