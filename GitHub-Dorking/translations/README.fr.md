# 🐙 GitHub Dorking — aide-mémoire complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/GitHub-Dorking-red?style=for-the-badge" alt="Recherche ciblée sur GitHub">
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="Recherche de secrets">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Programme de bug bounty">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 Repérer les secrets, clés d’API et identifiants exposés dans les dépôts GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Syntaxe de recherche GitHub](#-syntaxe-de-recherche-github)
- [Clés d’API et jetons](#-clés-dapi-et-jetons)
- [Identifiants de bases de données](#-identifiants-de-bases-de-données)
- [Clés privées](#-clés-privées)
- [Fichiers de configuration](#-fichiers-de-configuration)
- [Secrets cloud](#-secrets-cloud)
- [Mots de passe et identifiants](#-mots-de-passe-et-identifiants)
- [Cibles de bug bounty](#-cibles-de-bug-bounty)
- [Techniques avancées](#-techniques-avancées)
- [Outils d’automatisation](#-outils-dautomatisation)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Introduction

Le **GitHub Dorking** consiste à utiliser la recherche avancée de GitHub pour repérer des secrets, des identifiants et des informations sensibles exposés dans des dépôts publics. Un *dork* est une requête de recherche ciblée.

### Que peut-on trouver ?

| Catégorie | Exemples |
|----------|----------|
| **Clés d’API** | AWS, Google, Stripe, Twilio |
| **Jetons** | OAuth, JWT, jetons d’accès personnels (Personal Access Tokens) |
| **Identifiants** | Mots de passe de bases de données, SMTP, SSH |
| **Clés privées** | RSA, SSH, fichiers PEM |
| **Fichiers de configuration** | .env, config.yml, settings.py |

### Pourquoi est-ce important ?

- Les développeurs ajoutent souvent des secrets à des commits par accident
- L’historique Git conserve les secrets supprimés des fichiers
- Les dépôts publics sont indexés et peuvent faire l’objet de recherches
- Une source importante de découvertes dans les programmes de bug bounty

---

## 🔧 Syntaxe de recherche GitHub

### URL de recherche de base

```
https://github.com/search?q=YOUR_QUERY&type=code
```

### Filtres de recherche

> **Précision :** le tableau conserve des filtres de l’ancienne recherche de code (`filename:`, `extension:`, `in:file`, `in:path`, `size:`). La recherche web actuelle utilise notamment `path:` et `content:` ; la syntaxe dépend du moteur ou de l’API. Les exemples originaux restent inchangés. Voir les documentations GitHub [actuelle](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax) et [historique](https://docs.github.com/en/search-github/searching-on-github/searching-code).

| Filtre | Description | Exemple |
|-----------|-------------|---------|
| `in:file` | Rechercher dans le contenu des fichiers | `password in:file` |
| `in:path` | Rechercher dans les chemins de fichiers | `config in:path` |
| `filename:` | Rechercher par nom de fichier | `filename:.env` |
| `extension:` | Rechercher par extension | `extension:json` |
| `language:` | Rechercher par langage | `language:python` |
| `user:` | Rechercher dans les dépôts d’un utilisateur | `user:target` |
| `org:` | Rechercher dans les dépôts d’une organisation | `org:company` |
| `repo:` | Rechercher dans un dépôt précis | `repo:user/repo` |
| `path:` | Rechercher dans un chemin | `path:config/` |
| `size:` | Taille du fichier | `size:>1000` |

### Opérateurs booléens

> **Précision :** la recherche actuelle reconnaît `AND`, `OR` et `NOT` ; le raccourci `-` ci-dessous est hérité. Les expressions régulières doivent être délimitées par des barres obliques ; un motif entre guillemets n’est pas automatiquement une regex. Les combinaisons avec `OR` demandent aussi de vérifier le regroupement des conditions ([syntaxe officielle](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax)).

```
# AND (espace)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# Expression exacte
"api_key"

# Combiner
org:target "api_key" NOT test
```

---

## 🔑 Clés d’API et jetons

### Clés AWS

```
# Identifiant de clé d’accès AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# Clé secrète AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# Combinaisons
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

### Google Cloud

```
# Clé d’API GCP
"AIza"
"AIzaSy"

# Compte de service GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

### Stripe

> **Précision :** `pk_live_` identifie une clé Stripe publiable, contrairement aux clés secrètes ou restreintes. De même, une clé destinée uniquement aux services Firebase peut être publique par conception si ses restrictions sont adaptées ; sa présence ne démontre pas une fuite de secret. Voir [Stripe](https://docs.stripe.com/keys) et [Firebase](https://firebase.google.com/docs/projects/api-keys).

```
# Clés Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# Combinaisons
org:target sk_live
filename:.env STRIPE_SECRET
```

### Twilio

```
# SID et jeton Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

### Jetons GitHub

> **Précision :** la liste ci-dessous regroupe plusieurs familles historiques de jetons, pas seulement des jetons d’accès personnels ; leurs préfixes et formats ne sont pas exhaustifs ([explication officielle de GitHub](https://github.blog/engineering/platform-security/behind-githubs-new-authentication-token-formats/)).

```
# Jetons d’accès personnels (Personal Access Tokens)
ghp_
gho_
ghu_
ghs_
ghr_

# Motifs de jetons GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

### Slack

```
# Jetons Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# Webhook Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

### Autres clés d’API

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

## 💾 Identifiants de bases de données

### MySQL

```
# Chaînes de connexion MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# Combinaisons
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
# Connexion MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# Avec des identifiants
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

### Bases de données : termes génériques

```
# Chaînes de connexion
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# Identifiants
"db_password"
"db_pass"
"database_password"
```

---

## 🔐 Clés privées

### Clés SSH

```
# Clés SSH privées
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# Fichiers
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# Combinaisons
filename:id_rsa -example -sample
```

### Clés PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

### Certificats SSL/TLS

> **Précision :** les motifs ci-dessous recherchent des certificats, qui ne sont pas des clés privées. La présence d’un certificat seul ne démontre pas une fuite de clé. Voir le [format des certificats X.509 dans la RFC 5280](https://www.rfc-editor.org/rfc/rfc5280.html#section-4.1).

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-fichiers-de-configuration"></a>
## ⚙️ Fichiers de configuration

### Fichiers d’environnement

```
# Fichiers .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# Avec des secrets
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

### Fichiers de configuration

```
# Divers fichiers de configuration
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
# Configurations Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

### Kubernetes

```
# Secrets Kubernetes
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

<a id="-secrets-cloud"></a>
## ☁️ Secrets cloud

### AWS

```
# Fichier d’identifiants AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# Configuration AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# Buckets S3
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

## 🔓 Mots de passe et identifiants

### Mots de passe codés en dur

```
# Variables de mots de passe
password=
passwd=
pass=
pwd=
"password:"
"password ="

# Avec des valeurs
password="
password='
"password": "
'password': '
```

### Identifiants SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

### Secrets OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

### Secrets JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

### Identifiants d’administration

```
# Administration
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

## 🎯 Cibles de bug bounty

### Organisation cible

```
# Rechercher dans une organisation précise
org:target-company

# Dépôts d’un utilisateur
user:target-username

# Dépôt précis
repo:target/repository

# Combinaisons avec des secrets
org:target-company password
org:target-company api_key
org:target-company "secret"
```

### Recherche par domaine

```
# Domaine cible
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# Combinaisons
"target.com" password
"target.com" api_key
org:target "@target.com"
```

### Adresses e-mail des employés

```
# Trouver les dépôts des employés
"@target.com"

# Adresses e-mail des développeurs
"@target.com" password
"@target.com" config
```

### URL internes

```
# Services internes
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

### Modèle de dorks pour le bug bounty

```
# Reconnaissance complète
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

## 🔬 Techniques avancées

### Recherche dans l’historique Git

> **Précision :** `pushed:` filtre la date de mise à jour de dépôts ; il ne recherche pas le contenu des anciens commits. L’historique et les secrets supprimés exigent une analyse distincte ([documentation GitHub](https://docs.github.com/en/search-github/searching-on-github/searching-for-repositories#search-by-when-a-repository-was-created-or-last-updated)).

```
# Examiner les commits
# (Utiliser des outils tels que TruffleHog ou git-secrets)

# Dépôts ayant reçu récemment un push
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

### Recherche dans les gists

```
# Rechercher dans les gists
https://gist.github.com/search?q=password+target

# Motifs pour les gists
gist password
gist api_key
```

### Extraits de code

```
# Rechercher dans le code
in:file password
in:path config

# Fichiers volumineux (peuvent contenir davantage de données)
size:>10000 password
```

### Expressions régulières (avec des outils)

```
# Motif de clé AWS
AKIA[0-9A-Z]{16}

# Jeton GitHub
ghp_[a-zA-Z0-9]{36}

# Jeton Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# Clé privée
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-outils-dautomatisation"></a>
## 🛠️ Outils d’automatisation

### TruffleHog

> **Précision :** ces commandes mélangent des générations de TruffleHog. Le projet actuel est un binaire Go ; l’URL d’une organisation n’est pas celle d’un dépôt Git. La documentation distingue les sources `git` et `github`, ainsi que leurs options de résultats. Les commandes historiques sont conservées ici ([projet officiel](https://github.com/trufflesecurity/trufflehog)).

```bash
# Installation
pip install truffleHog

# Analyser un dépôt
trufflehog git https://github.com/target/repo

# Analyser une organisation
trufflehog git https://github.com/target --only-verified
```

### GitLeaks

> **Précision :** `detect` est déprécié depuis Gitleaks 8.19.0 ; le mode Git documenté analyse un dépôt local. Le paramètre `--source` avec une URL ci-dessous ne doit pas être interprété comme un clonage automatique. Vérifier la version et la source attendue ([documentation officielle](https://github.com/gitleaks/gitleaks)).

```bash
# Installation
brew install gitleaks

# Analyser un dépôt
gitleaks detect -s /path/to/repo

# Analyser depuis une URL
gitleaks detect --source https://github.com/target/repo

# Sortie JSON
gitleaks detect -s repo --report-format json -r results.json
```

### git-secrets

```bash
# Installation
brew install git-secrets

# Ajouter des motifs
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# Analyser un dépôt
git secrets --scan
```

### Outils de recherche GitHub

```bash
# Interface en ligne de commande pour les dorks GitHub
# Divers outils communautaires sont disponibles

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

### shhgit

```bash
# Surveillance de GitHub en temps réel
# Recherche de secrets en temps réel
shhgit --search-query "org:target"
```

---

## 📊 Référence rapide

### Dorks essentiels

| Objectif | Dork |
|---------|------|
| Clés AWS | `org:target AKIA` |
| Mots de passe | `org:target password filename:.env` |
| Clés d’API | `org:target api_key` |
| Clés privées | `org:target "BEGIN RSA PRIVATE KEY"` |
| Fichiers de configuration | `org:target filename:config.json` |
| Identifiants de bases de données | `org:target "mongodb://"` |
| Jetons | `org:target token secret` |
| Adresses e-mail | `"@target.com" password` |

### Modèles de fichiers

| Motif | Dork |
|---------|------|
| `.env` | `filename:.env` |
| Configuration JSON | `filename:config.json` |
| Paramètres | `filename:settings.py` |
| Identifiants | `filename:credentials` |
| Secrets | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

### Les 15 principaux dorks GitHub

| # | Dork | Objectif |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | Mots de passe de bases de données |
| 2 | `filename:.env AWS_` | Identifiants AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | Clés privées |
| 4 | `filename:config.json password` | Mots de passe dans les configurations |
| 5 | `AKIA` | Clés d’accès AWS |
| 6 | `sk_live` | Clés Stripe |
| 7 | `"mongodb+srv://"` | URI MongoDB |
| 8 | `filename:credentials aws` | Fichier d’identifiants AWS |
| 9 | `ghp_` | Jetons GitHub |
| 10 | `xoxb-` | Jetons Slack |
| 11 | `org:target password` | Mots de passe de l’organisation |
| 12 | `filename:.npmrc _auth` | Jetons NPM |
| 13 | `extension:sql password` | Exports SQL |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | Clés d’API |

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Rechercher dans l’organisation**
   ```
   org:target-company
   ```

2. **Examiner les dépôts des employés**
   ```
   "@target.com"
   ```

3. **Rechercher des URL internes**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **Examiner l’historique Git**
   - Utiliser TruffleHog ou GitLeaks

5. **Examiner les gists**
   - Les développeurs y stockent souvent des extraits de code

### Divulgation responsable

- **Signaler** les découvertes à l’équipe de sécurité
- **Ne pas** accéder aux systèmes avec les identifiants trouvés
- **Ne pas** partager publiquement les identifiants
- **Documenter** correctement les découvertes

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** le GitHub Dorking révèle des informations accessibles publiquement. Toutefois :
> 
> - ✅ Rechercher dans les dépôts publics
> - ✅ Signaler les découvertes aux programmes de bug bounty
> - ✅ Procéder à une divulgation responsable
> - ❌ Ne jamais utiliser les identifiants trouvés
> - ❌ Ne pas accéder aux systèmes
> - ❌ Ne pas stocker ni partager de secrets
> 
> **Utiliser des identifiants trouvés sans autorisation est illégal !**

---

## 📚 Ressources

### Outils
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

### Références
- [Documentation de la recherche GitHub](https://docs.github.com/en/search-github)
- [Détection de secrets et motifs reconnus](https://docs.github.com/en/code-security/secret-scanning)

### Aide-mémoire associés
- [Google Dorking](../../Google-Dorking/translations/README.fr.md)
- [Shodan](../../Shodan/translations/README.fr.md)
- [Subfinder](../../Subfinder/translations/README.fr.md)

---

<p align="center">
  <b>🐙 Repérez les secrets avant les attaquants !</b><br>
  <i>Maîtrisez le GitHub Dorking pour le bug bounty</i>
</p>
