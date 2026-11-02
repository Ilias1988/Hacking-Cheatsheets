# 📚 Listes de mots (wordlists) — guide de référence complet

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Listes de mots">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Essais exhaustifs">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Référence complète des listes de mots pour les tests d’intrusion</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Table des matières

- [Listes de mots de passe](#-listes-de-mots-de-passe)
- [Listes de répertoires et de fichiers](#-listes-de-répertoires-et-de-fichiers)
- [Listes de sous-domaines](#-listes-de-sous-domaines)
- [Listes de noms d’utilisateurs](#-listes-de-noms-dutilisateurs)
- [Listes pour le fuzzing web](#-listes-pour-le-fuzzing-web)
- [Collection SecLists](#-collection-seclists)
- [Listes de mots fournies sous Kali](#-listes-de-mots-fournies-sous-kali)
- [Outils de création de listes personnalisées](#-outils-de-création-de-listes-personnalisées)
- [Téléchargement rapide](#-téléchargement-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🔐 Listes de mots de passe

Une *wordlist* est une liste de mots ou de valeurs candidates. Le *fuzzing* teste de nombreuses entrées pour observer le comportement d’une application.

> **Contexte des inventaires :** tailles, nombres de lignes, noms et arborescences sont repris de l’original à titre indicatif. Les collections évoluent ; certains fichiers peuvent être déplacés, renommés ou absents selon la version. Les chemins et URL historiques restent inchangés ([projet SecLists](https://github.com/danielmiessler/SecLists)).

### Les plus utilisées

| Liste | Taille | Lignes | Source | Utilisation |
| ---------- | ------ | ------- | -------- | ---------- |
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Recherche de mots de passe |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Mots de passe courants |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Grande liste de mots de passe |
| **common-passwords-win.txt** | - | - | SecLists | Mots de passe Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Un million de mots de passe fréquents |

### Listes de mots de passe par type

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Listes de mots de passe spécialisées

| Liste | Objectif | Emplacement |
| ---------- | --------- | ---------- |
| **wifi-passwords.txt** | Recherche de mots de passe Wi-Fi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Analyse statistique | SecLists/Passwords/ |
| **seasons-only.txt** | Motifs saisonniers | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Mots de passe interdits | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Identifiants par défaut | SecLists/Passwords/Default-Credentials/ |

### Emplacements sous Kali

```bash
# RockYou (décompresser d’abord)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Listes de répertoires et de fichiers

### Recherche de répertoires par essais systématiques

> **Précision :** l’attribution de `common.txt` à DirBuster est simplifiée : la liste courante documentée sous Kali appartient à DIRB. Les noms seuls ne garantissent pas un contenu identique entre collections ([documentation DIRB](https://www.kali.org/tools/dirb/)).

| Liste | Taille | Lignes | Source | Utilisation |
| ---------- | ------ | ------- | -------- | ---------- |
| **common.txt** | 36 KB | 4.6K | DirBuster | Analyse rapide |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Énumération limitée |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Énumération standard |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Énumération étendue |
| **big.txt** | 210 KB | 20K | SecLists | Répertoires courants |

### Série RAFT (recommandée dans l’original)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K répertoires
├── raft-large-files.txt            # 37K fichiers
├── raft-large-words.txt            # 120K mots
├── raft-medium-directories.txt     # 30K répertoires
├── raft-medium-files.txt           # 17K fichiers
├── raft-medium-words.txt           # 63K mots
├── raft-small-directories.txt      # 20K répertoires
├── raft-small-files.txt            # 11K fichiers
└── raft-small-words.txt            # 43K mots
```

### Listes d’extensions de fichiers

| Liste | Objectif |
| ---------- | --------- |
| **web-extensions.txt** | Extensions web courantes |
| **backup-extensions.txt** | Fichiers de sauvegarde (.bak, .old) |
| **raft-large-extensions.txt** | Grande liste d’extensions |

### Découverte spécialisée

| Liste | Objectif | Lignes |
| ---------- | --------- | ------- |
| **apache.txt** | Propre à Apache | - |
| **nginx.txt** | Propre à Nginx | - |
| **iis.txt** | Propre à IIS | - |
| **api-endpoints.txt** | Découverte d’API | - |
| **graphql.txt** | Points d’accès GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Emplacements courants

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Découverte avec SecLists
/usr/share/seclists/Discovery/Web-Content/

# Téléchargements rapides
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

## 🌐 Listes de sous-domaines

### Listes de sous-domaines courantes

| Liste | Lignes | Source | Utilisation |
| ---------- | ------- | -------- | ---------- |
| **subdomains-top1million-5000.txt** | 5K | SecLists | Énumération rapide |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Énumération standard |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Énumération approfondie |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Bug bounty |
| **fierce-hostlist.txt** | - | SecLists | Compatible avec Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Liste combinée |

### Emplacement des listes de sous-domaines

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # Recommandé pour le bug bounty dans l’original
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Listes recommandées pour le bug bounty

Un programme de *bug bounty* récompense le signalement de vulnérabilités selon son périmètre et ses règles.

```bash
# Méthodologie de Jason Haddix
dns-Jhaddix.txt              # 2M+ sous-domaines

# Combiner pour approfondir la recherche
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

## 👤 Listes de noms d’utilisateurs

### Listes de noms d’utilisateurs courantes

| Liste | Lignes | Objectif |
| ---------- | ------- | --------- |
| **top-usernames-shortlist.txt** | 17 | Vérification rapide |
| **xato-net-10-million-usernames.txt** | 10M | Grande liste |
| **cirt-default-usernames.txt** | - | Utilisateurs par défaut |
| **names.txt** | - | Noms courants |
| **familynames-usa-top1000.txt** | 1K | Noms de famille |

### Emplacement des listes de noms d’utilisateurs

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Identifiants par défaut

| Service | Liste |
| --------- | ---------- |
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

## 💉 Listes pour le fuzzing web

### Injection SQL

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (injection de script dans le navigateur)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (inclusion de fichiers locaux ou distants)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Injection de commandes

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (injection dans les modèles côté serveur)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Collections pour le fuzzing

| Liste | Objectif |
| ---------- | --------- |
| **special-chars.txt** | Caractères spéciaux |
| **Unicode.txt** | Fuzzing Unicode |
| **big-list-of-naughty-strings.txt** | Cas limites |
| **fuzz-Bo0oM.txt** | Fuzzing général |
| **burp-parameter-names.txt** | Noms de paramètres |

---

## 📦 Collection SecLists

### À propos de SecLists

**SecLists** rassemble plusieurs types de listes utilisées lors des évaluations de sécurité : mots de passe, noms d’utilisateurs, chemins et charges utiles.

### Installation

> **Version des exemples :** la présence de SecLists dépend de l’image et des paquets installés ; la disponibilité de `apt install seclists` dépend aussi des dépôts configurés. Le projet documente notamment l’installation du paquet Kali, sans garantir sa présence sur toute installation Debian/Ubuntu ([installation SecLists](https://github.com/danielmiessler/SecLists)).

```bash
# Kali Linux (préinstallé selon l’original)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Clonage Git
git clone https://github.com/danielmiessler/SecLists.git

# Téléchargement rapide de listes précises
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Structure de SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Listes de sous-domaines
│   ├── Web-Content/            # Listes de répertoires et de fichiers
│   └── Infrastructure/         # Services et ports
├── Fuzzing/
│   ├── SQLi/                   # Charges utiles d’injection SQL
│   ├── XSS/                    # Charges utiles XSS
│   ├── LFI/                    # Charges utiles LFI/RFI
│   └── command-injection*.txt  # Injection de commandes
├── Passwords/
│   ├── Common-Credentials/     # Listes de mots de passe
│   ├── Leaked-Databases/       # rockyou, etc.
│   └── Default-Credentials/    # Mots de passe par défaut
├── Usernames/
│   ├── Names/                  # Listes de noms
│   └── top-usernames*.txt      # Listes de noms d’utilisateurs
├── Payloads/
│   └── Anti-Antivirus/         # Contournement antivirus
└── Pattern-Matching/
    └── Hunting/                # Expressions régulières
```

### Dépôt GitHub

🔗 https://github.com/danielmiessler/SecLists

---

## 🐧 Listes de mots fournies sous Kali

### Emplacement des listes

> **Correction de l’original :** dans l’exemple officiel Kali, `nmap.lst` pointe vers une liste de mots de passe de Nmap, pas une liste de scripts. Les liens et répertoires disponibles dépendent des paquets installés ([paquet wordlists](https://www.kali.org/tools/wordlists/)).

```bash
# Répertoire principal des listes
/usr/share/wordlists/

# Contenu
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Décompresser avec gunzip
├── dirb/                   # Listes DIRB
├── dirbuster/              # Listes DirBuster
├── fasttrack.txt           # Liste Fast Track
├── fern-wifi/              # Listes Wi-Fi
├── metasploit/             # Listes Metasploit
├── nmap.lst                # Liste de scripts Nmap selon l’original
├── wfuzz/                  # Listes wFuzz
└── seclists -> /usr/share/seclists/
```

### Listes DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### Listes DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K entrées
├── common.txt               # 4.6K entrées
├── small.txt                # 959 entrées
├── catala.txt               # Catalan
├── euskera.txt              # Basque
├── extensions_common.txt    # Extensions
├── indexes.txt              # Fichiers d’index
├── mutations_common.txt     # Mutations
├── spanish.txt              # Espagnol
├── stress/                  # Tests de charge
└── vulns/                   # Vérifications de vulnérabilités
```

---

<a id="-outils-de-création-de-listes-personnalisées"></a>
## 🛠️ Outils de création de listes personnalisées

### CeWL (générateur de listes personnalisées)

```bash
# Installation
sudo apt install cewl

# Utilisation de base : parcourir un site web
cewl https://target.com -w wordlist.txt

# Définir la profondeur et la longueur minimale des mots
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Inclure les adresses e-mail
cewl https://target.com -e -w wordlist.txt

# Options
-d <depth>      # Profondeur de parcours (par défaut : 2)
-m <length>     # Longueur minimale des mots (par défaut : 3)
-w <file>       # Fichier de sortie
-e              # Inclure les adresses e-mail
--lowercase     # Convertir en minuscules
-c              # Compter les occurrences des mots
```

### Crunch (générateur de listes de mots)

> **Précision :** la génération exhaustive croît rapidement : pour un alphabet de taille n et une longueur fixe L, elle peut produire n^L candidats. Le pipeline Hashcat ci-dessous impose en outre le mode `0`, qui correspond à MD5 ; ce n’est pas une commande générique pour tout type d’empreinte ([modes Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes)).

```bash
# Installation
sudo apt install crunch

# Syntaxe de base
crunch <min-len> <max-len> [charset] -o <output>

# Générer des mots de passe de 4 à 6 caractères
crunch 4 6 -o wordlist.txt

# Définir les caractères utilisables
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Utiliser un motif
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = minuscule, , = majuscule, % = chiffre, ^ = caractère spécial

# Jeu de caractères personnalisé
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Transmettre la sortie à Hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (profilage de mots de passe courants)

```bash
# Installation
git clone https://github.com/Mebus/cupp.git
cd cupp

# Mode interactif fondé sur un profil
python3 cupp.py -i

# Questions posées :
# - Prénom, nom de famille et surnom
# - Date de naissance
# - Informations sur le partenaire
# - Nom de l’animal de compagnie
# - Nom de l’entreprise
# - Mots-clés

# Télécharger des mots de passe courants
python3 cupp.py -l

# Utiliser une liste existante avec le profilage
python3 cupp.py -w wordlist.txt
```

### Mentalist (générateur de listes avec interface graphique)

```bash
# Outil graphique pour créer des listes complexes
# Prend en charge les règles, changements de casse et substitutions
# Téléchargement : https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Générateur simple de liste personnalisée
import itertools

# Générer les suites de caractères
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Règles Hashcat

```bash
# Appliquer des règles pour transformer une liste
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Règles courantes
/usr/share/hashcat/rules/
├── best64.rule         # Sélection de 64 règles
├── rockyou-30000.rule  # Fondées sur RockYou
├── d3ad0ne.rule        # Règles de d3ad0ne
├── dive.rule           # Règles DIVE
└── toggles1-5.rule     # Changer la casse
```

---

<a id="-téléchargement-rapide"></a>
## ⬇️ Téléchargement rapide

### Téléchargements en une commande

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists complet
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Mots de passe courants (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Liste moyenne de répertoires
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Liste de sous-domaines
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# DNS de Jhaddix pour le bug bounty
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Cloner les dépôts

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Listes Assetnote
git clone https://github.com/assetnote/wordlists.git
```

---

## 💡 Conseils et bonnes pratiques

### Choisir la liste adaptée

| Tâche | Liste recommandée |
| ------ | --------------------- |
| **Analyse rapide des répertoires** | common.txt, raft-small-directories.txt |
| **Analyse approfondie des répertoires** | directory-list-2.3-medium.txt |
| **Analyse étendue des répertoires** | directory-list-2.3-big.txt |
| **Énumération des sous-domaines** | subdomains-top1million-20000.txt |
| **DNS pour le bug bounty** | dns-Jhaddix.txt |
| **Recherche de mots de passe** | rockyou.txt |
| **Vérification rapide de mots de passe** | top-10000.txt |

### Combiner des listes

```bash
# Combiner et dédupliquer
cat list1.txt list2.txt | sort -u > combined.txt

# Ajouter un préfixe à toutes les lignes
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Ajouter un suffixe
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Combiner les deux variantes
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Optimiser les listes

```bash
# Trier et supprimer les doublons
sort -u wordlist.txt -o wordlist.txt

# Supprimer les lignes vides
sed -i '/^$/d' wordlist.txt

# Supprimer les lignes de moins de 4 caractères
awk 'length >= 4' wordlist.txt > filtered.txt

# Convertir en minuscules
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Compter les lignes
wc -l wordlist.txt
```

### Conseils de performance

1. **Commencer petit** — Utiliser d’abord de petites listes, puis élargir la recherche
2. **Utiliser des motifs** — Si le format est connu, définir des motifs adaptés
3. **Combiner les outils** — Associer des listes personnalisées aux listes existantes
4. **Adapter les listes** — Créer avec CeWL des listes propres au contexte de la cible

---

## 📊 Référence rapide

### Listes de référence par catégorie

| Catégorie | Choix de référence | Emplacement |
| ---------- | ------------- | ---------- |
| **Mots de passe** | rockyou.txt | SecLists/Passwords/ |
| **Répertoires** | directory-list-2.3-medium.txt | DirBuster |
| **Fichiers** | raft-large-files.txt | SecLists |
| **Sous-domaines** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Noms d’utilisateurs** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Guide des tailles

> **Précision :** une liste très grande ne garantit pas une couverture complète de tous les noms ou mots de passe possibles. Les appréciations et recommandations du guide décrivent des points de départ, pas un résultat assuré.

| Taille | Lignes | Utilisation |
| ------ | ------- | ---------- |
| **Petite** | < 5K | Tests rapides |
| **Moyenne** | 5K - 100K | Énumération standard |
| **Grande** | 100K - 1M | Analyse approfondie |
| **Très grande** | > 1M | Couverture étendue selon l’original |

---

## 📚 Ressources

### Dépôts de listes de mots
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Assetnote Wordlists](https://github.com/assetnote/wordlists)

### Outils
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Aide-mémoire associés
- [Hydra](../../Hydra/translations/README.fr.md)
- [John the Ripper](../../John-The-Ripper/translations/README.fr.md)
- [Hashcat](../../Hashcat/translations/README.fr.md)
- [Gobuster](../../Gobuster/translations/README.fr.md)
- [ffuf](../../ffuf/translations/README.fr.md)

---

<p align="center">
  <b>📚 Une liste adaptée fait toute la différence !</b><br>
  <i>Choisir avec soin et combiner les listes à bon escient</i>
</p>
