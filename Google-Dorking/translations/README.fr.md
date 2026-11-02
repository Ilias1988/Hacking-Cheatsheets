# 🔍 Google Dorking — aide-mémoire complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Collecte d’informations">
</p>

<p align="center">
  <b>🎯 Techniques de recherche avancée sur Google pour la recherche en sécurité</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Opérateurs de base](#-opérateurs-de-base)
- [Opérateurs avancés](#-opérateurs-avancés)
- [Combiner les opérateurs](#-combiner-les-opérateurs)
- [Découverte de fichiers](#-découverte-de-fichiers)
- [Listage de répertoires](#-listage-de-répertoires)
- [Interfaces d’administration](#-interfaces-dadministration)
- [Bases de données et SQL](#-bases-de-données-et-sql)
- [Fichiers de configuration](#-fichiers-de-configuration)
- [Identifiants et secrets](#-identifiants-et-secrets)
- [Serveurs potentiellement vulnérables](#-serveurs-potentiellement-vulnérables)
- [Requêtes ciblées pour le bug bounty](#-requêtes-ciblées-pour-le-bug-bounty)
- [Recherches propres aux CMS](#-recherches-propres-aux-cms)
- [Référence rapide](#-référence-rapide)
- [Outils et automatisation](#-outils-et-automatisation)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Introduction

Le **Google Dorking**, aussi appelé *Google Hacking*, consiste à utiliser des opérateurs de recherche avancés pour repérer des informations sensibles exposées sur Internet. Un *dork* est une requête de recherche ciblée.

### Que peut-on trouver ?

| Catégorie | Exemples |
|----------|----------|
| **Fichiers sensibles** | Sauvegardes, configurations, journaux, bases de données |
| **Interfaces exposées** | Interfaces d’administration, tableaux de bord, phpMyAdmin |
| **Identifiants** | Mots de passe, clés API, jetons |
| **Vulnérabilités** | Injection SQL, répertoires accessibles |
| **Données personnelles** | Adresses e-mail, numéros de téléphone, documents |

### Adresses des moteurs de recherche

| Moteur | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Opérateurs de base

### Opérateur site

> **Précision :** `site:` fournit des résultats indexés, sans inventaire exhaustif des pages ou sous-domaines. Les variantes avec `*` ne garantissent donc pas « tous les sous-domaines » ([documentation Google](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site)).

Limiter la recherche à un domaine précis :

```
site:example.com                     # Uniquement example.com
site:*.example.com                   # Tous les sous-domaines
site:example.com -www                # Exclure www
site:.gov                            # Sites gouvernementaux
site:.edu                            # Sites éducatifs
```

### Opérateur inurl

Rechercher dans l’URL :

```
inurl:admin                          # admin dans l’URL
inurl:login                          # Pages de connexion
inurl:dashboard                      # Tableaux de bord
inurl:config                         # Fichiers de configuration
inurl:backup                         # Fichiers de sauvegarde
```

### Opérateur intitle

Rechercher dans le titre de la page :

```
intitle:"index of"                   # Listages de répertoires
intitle:admin                        # Pages d’administration
intitle:login                        # Pages de connexion
intitle:"dashboard"                  # Tableaux de bord
intitle:"control panel"              # Panneaux de contrôle
```

### Opérateur intext

Rechercher dans le contenu de la page :

```
intext:"password"                    # Mot de passe dans le texte
intext:"username"                    # Nom d’utilisateur dans le texte
intext:"api_key"                     # Clés API
intext:"secret"                      # Secrets
```

### Opérateur filetype

> **Précision :** `filetype:` peut s’appuyer sur le type de contenu HTTP ou sur l’extension, et reste limité aux contenus indexés. Trouver une page PHP ne signifie pas que son code source côté serveur est exposé ([documentation Google](https://developers.google.com/search/docs/monitor-debug/search-operators)).

Rechercher des types de fichiers précis :

```
filetype:pdf                         # Fichiers PDF
filetype:doc                         # Documents Word
filetype:xls                         # Fichiers Excel
filetype:sql                         # Fichiers SQL
filetype:log                         # Fichiers journaux
filetype:bak                         # Fichiers de sauvegarde
filetype:env                         # Fichiers d’environnement
```

---

## ⚡ Opérateurs avancés

### Variantes all

```
allinurl:admin login                 # Tous les mots dans l’URL
allintitle:admin panel               # Tous les mots dans le titre
allintext:password username          # Tous les mots dans le texte
```

### Cache et liens

> **Exemples historiques :** `cache:` ne fonctionne plus dans Google Search ; Google l’a confirmé lors du retrait de sa documentation en septembre 2024 ([journal officiel](https://developers.google.com/search/updates#cache-docs)). Les autres opérateurs anciens, notamment `link:` et `related:`, sont conservés comme dans l’original ; leur prise en charge ne doit pas être supposée. Les opérateurs dépendent des limites d’indexation et de récupération du moteur ([Google](https://developers.google.com/search/docs/monitor-debug/search-operators)).

```
cache:example.com                    # Version en cache (historique)
link:example.com                     # Pages contenant un lien vers le site
related:example.com                  # Sites associés
```

### Plage de dates

```
after:2023-01-01                     # Après cette date
before:2024-01-01                    # Avant cette date
daterange:2458850-2459215            # Plage de dates juliennes
```

### Jokers et OR

```
admin * panel                        # Joker
admin OR login                       # L’un ou l’autre terme
"admin panel"                        # Expression exacte
-inurl:https                         # Exclure HTTPS
```

### Plage de nombres

```
inurl:id= 1..1000                    # Plage de nombres
price $100..$500                     # Plage de prix
```

---

## 🔗 Combiner les opérateurs

### Combinaisons de base

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Combinaisons avancées

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Combinaisons pour le bug bounty

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Découverte de fichiers

### Documents sensibles

```
# Documents bureautiques
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Fichiers texte
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Fichiers de sauvegarde

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Fichiers de bases de données

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Fichiers journaux

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Fichiers de configuration

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Code source

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

## 📂 Listage de répertoires

### Index de répertoire (index of)

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Répertoires précis

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Types de fichiers dans les répertoires

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Interfaces d’administration

### Administration générale

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel et hébergement

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Administration de bases de données

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Panneaux de contrôle

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Bases de données et SQL

### Fichiers SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### Erreurs SQL

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### Indices d’injection SQL

> **Précision :** un nom de paramètre, une page d’erreur ou une interface exposée est une piste de reconnaissance ; ce résultat seul ne démontre pas une vulnérabilité.

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Exports de bases de données (dumps)

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-fichiers-de-configuration"></a>
## ⚙️ Fichiers de configuration

### Configuration du serveur web

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Configuration de l’application

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Configuration Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS et cloud

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Préfixe de clé AWS
site:target.com "credentials" filetype:json
```

---

## 🔑 Identifiants et secrets

### Mots de passe

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Paires identifiant et mot de passe

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### Clés API et jetons

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### Adresses e-mail

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Clés privées

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a id="-serveurs-potentiellement-vulnérables"></a>
## ⚠️ Serveurs potentiellement vulnérables

### Répertoires accessibles

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Pages par défaut

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Pages d’erreur

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Services exposés

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

## 🐛 Requêtes ciblées pour le bug bounty

### Pistes rapides

```
# Fichiers sensibles
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Interfaces d’administration
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Fichiers de configuration
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Identifiants
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Découverte de sous-domaines

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### Points d’accès API (endpoints)

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Développement et préproduction

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Pages d’authentification

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Téléversement de fichiers

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 Recherches propres aux CMS

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

## 📊 Référence rapide

### Opérateurs essentiels

| Opérateur | Fonction | Exemple |
|----------|----------|---------|
| `site:` | Domaine précis | `site:target.com` |
| `inurl:` | Recherche dans l’URL | `inurl:admin` |
| `intitle:` | Recherche dans le titre | `intitle:login` |
| `intext:` | Recherche dans le contenu | `intext:password` |
| `filetype:` | Extension de fichier | `filetype:sql` |
| `ext:` | Extension de fichier | `ext:pdf` |
| `cache:` | Version en cache (historique) | `cache:target.com` |
| `"..."` | Expression exacte | `"admin panel"` |
| `-` | Exclusion | `-site:www` |
| `OR` / `\|` | L’un ou l’autre terme | `admin OR login` |
| `*` | Joker | `admin * panel` |

### Dix requêtes pour le bug bounty

| # | Requête ciblée | Objectif |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Fichiers de bases de données |
| 2 | `site:target.com inurl:admin` | Interfaces d’administration |
| 3 | `site:target.com filetype:log` | Fichiers journaux |
| 4 | `site:target.com filetype:env` | Fichiers d’environnement |
| 5 | `site:target.com intext:password filetype:txt` | Mots de passe |
| 6 | `intitle:"index of" site:target.com` | Listage de répertoire |
| 7 | `site:target.com inurl:api` | Points d’accès API |
| 8 | `site:target.com filetype:bak` | Fichiers de sauvegarde |
| 9 | `site:*.target.com -www` | Sous-domaines |
| 10 | `site:target.com "api_key"` | Clés API |

---

<a id="-outils-et-automatisation"></a>
## 🛠️ Outils et automatisation

### Outils de Google Dorking

| Outil | Description | URL |
|------|-------------|-----|
| **Google Hacking Database** | Collection de requêtes d’Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Générateur de requêtes ciblées | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Recherche passive par requêtes Google | GitHub |
| **GooDork** | Outil de requêtes ciblées en Python | GitHub |
| **GooFuzz** | Fuzzing (variation des entrées) avec Google | GitHub |

### Sites consacrés aux requêtes ciblées

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Exemple de script d’automatisation

> **Précision :** ce script affiche des requêtes et des liens ; il n’envoie pas de recherche à Google. Le remplacement des espaces par `+` n’est pas un encodage complet des caractères d’une URL.

```bash
#!/bin/bash
# Automatisation simple de requêtes Google

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Commencer large, puis affiner**
   ```
   site:target.com                    # D’abord
   site:target.com filetype:sql       # Puis affiner
   ```

2. **Utiliser plusieurs moteurs de recherche**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Consulter les pages en cache (exemple historique)**
   - L’opérateur ci-dessous est conservé à titre historique ; voir la précision sur son retrait plus haut.
   ```
   cache:target.com/admin
   ```

4. **Rechercher des sous-domaines**
   ```
   site:*.target.com -www
   ```

5. **Limiter la fréquence des recherches**
   - Google peut bloquer un nombre excessif de requêtes

### Éviter la détection

```
# Utiliser différentes adresses IP ou différents VPN
# Ajouter des délais entre les recherches
# Varier les modèles de recherche
# Utiliser différents navigateurs
```

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** le texte original présente le Google Dorking comme légal en soi, mais **l’accès** à des données exposées sans autorisation peut être illégal.
> 
> - ✅ Utiliser ces techniques pour des tests de sécurité autorisés
> - ✅ Les utiliser dans le cadre de programmes de bug bounty
> - ✅ Signaler les vulnérabilités de manière responsable
> - ❌ Ne jamais accéder à des données sans autorisation
> - ❌ Ne jamais télécharger ni conserver de données sensibles
> - ❌ Ne pas exploiter les vulnérabilités découvertes
> 
> **Toujours obtenir une autorisation appropriée avant les tests !**

---

## 📚 Ressources

### Ressources officielles
- [Opérateurs de recherche Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Livres et guides
- « Google Hacking for Penetration Testers », de Johnny Long
- Guide de test OWASP (OWASP Testing Guide)

### Aide-mémoire associés
- [Shodan](../../Shodan/translations/README.fr.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.fr.md)
- [Subfinder](../../Subfinder/translations/README.fr.md)

---

<p align="center">
  <b>🔍 Trouvez ce que les autres dissimulent !</b><br>
  <i>Maîtrisez l’art du Google Dorking</i>
</p>
