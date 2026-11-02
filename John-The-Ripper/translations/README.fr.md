# 🔨 John the Ripper — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  The Password Cracker     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 L’outil historique d’audit de sécurité et de récupération des mots de passe</b>
</p>

---

> **Versions et portée :** cette fiche conserve les exemples anglais, qui mêlent Core et Jumbo. Les formats, OpenCL, scripts 2john, noms de paquets et options disponibles dépendent de la version et de la distribution. Consultez les [options officielles de Jumbo](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS). Les comparaisons de vitesse et de fonctionnalités avec Hashcat sont celles du document source, pas un classement universel actuel. Le dessin ASCII conserve le texte source.
>
> **Exemples incomplets :** plusieurs empreintes sont tronquées, notamment celles des sorties Windows et de bcrypt ; elles ne constituent pas des données d’entrée complètes. `wc -l` compte les lignes de sortie, pas nécessairement les formats listés. Les chemins des fichiers pot et des scripts 2john varient selon l’installation.
>
> **Règles et mémoire :** dans la [syntaxe officielle des règles](https://www.openwall.com/john/doc/RULES.shtml), `$X` et `^X` manipulent un caractère. Les lignes `$123` et `^2023` ne signifient donc pas simplement ajouter les chaînes « 123 » ou « 2023 » ; elles sont conservées pour fidélité. `--mem-file-size` règle le seuil de préchargement d’une liste en mémoire, pas un plafond général de RAM. La présence de yescrypt et les jeux incrémentaux dépendent aussi de la compilation et de la configuration.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Modes de recherche des mots de passe](#-modes-de-recherche-des-mots-de-passe)
- [Extraction des empreintes](#-extraction-des-empreintes)
- [Types d’empreintes courants](#-types-dempreintes-courants)
- [Recherche des mots de passe Linux](#-recherche-des-mots-de-passe-linux)
- [Recherche des mots de passe Windows](#-recherche-des-mots-de-passe-windows)
- [Recherche des mots de passe de fichiers](#-recherche-des-mots-de-passe-de-fichiers)
- [Règles et transformations](#-règles-et-transformations)
- [Gestion des sessions](#-gestion-des-sessions)
- [Réglage des performances](#-réglage-des-performances)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**John the Ripper** (John/JtR) est un outil libre et gratuit d’audit de sécurité et de récupération des mots de passe. Il permet de repérer les mots de passe faibles et de retrouver des mots de passe à partir de leurs empreintes.

### Principales fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Plusieurs formats** | Prend en charge des centaines de types d’empreintes |
| **Plusieurs modes** | Single, liste de mots, incrémental |
| **Règles personnalisées** | Règles puissantes de transformation des mots |
| **Détection automatique** | Détection automatique du format d’empreinte |
| **Multiplateforme** | Linux, Windows, macOS |
| **Prise en charge des GPU** | Accélération OpenCL (Jumbo) |

### John et Hashcat

| Fonctionnalité | John | Hashcat |
|---------|------|---------|
| **Usage privilégié** | Recherche sur CPU | Recherche sur GPU |
| **Facilité d’utilisation** | Plus simple | Plus complexe |
| **Détection automatique** | ✅ Oui | ❌ Non |
| **Règles** | Puissantes | Très puissantes |
| **Types d’empreintes** | Nombreux | Davantage |
| **Vitesse (GPU)** | Bonne | Meilleure |

### Versions

| Version | Description |
|---------|-------------|
| **John (Core)** | Version de base |
| **John Jumbo** | Version enrichie par la communauté (recommandée) |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Version standard
sudo apt install john

# Version Jumbo (recommandée)
sudo apt install john-jumbo
```

### Depuis les sources (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Télécharger depuis le site officiel
https://www.openwall.com/john/

# Ou utiliser Chocolatey
choco install john
```

### Vérifier l’installation
```bash
john --version
john --list=formats | wc -l    # Compter les formats pris en charge (voir la précision)
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
john [options] [password_file]
```

### Options courantes

| Option | Description |
|--------|-------------|
| `--wordlist=` | Chemin de la liste de mots |
| `--format=` | Indiquer le format d’empreinte |
| `--rules` | Activer les règles de transformation des mots |
| `--show` | Afficher les mots de passe retrouvés |
| `--single` | Mode Single Crack |
| `--incremental` | Mode force brute |
| `--session=` | Nommer la session |
| `--restore` | Restaurer la session précédente |
| `--pot=` | Fichier pot personnalisé |

### Prise en main rapide
```bash
# Détecter automatiquement le format et lancer la recherche
john hashes.txt

# Avec une liste de mots
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Afficher les mots de passe retrouvés
john --show hashes.txt
```

---

## 🎮 Modes de recherche des mots de passe

### Mode Single Crack

Utilise les noms de connexion et les informations GECOS pour générer des mots de passe candidats.

```bash
# Mode Single (présenté comme le plus rapide ; essaie des variantes du nom d’utilisateur)
john --single hashes.txt

# Avec un format explicite
john --single --format=sha512crypt hashes.txt
```

### Mode liste de mots (wordlist)

Utilise les mots d’un dictionnaire, éventuellement transformés par des règles.

```bash
# Recherche simple avec une liste de mots
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Liste de mots avec des règles
john --wordlist=wordlist.txt --rules hashes.txt

# Jeu de règles précis
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Mode incrémental (force brute)

Essaie les combinaisons de caractères définies par la configuration incrémentale.

```bash
# Mode incrémental par défaut (caractères définis par la configuration)
john --incremental hashes.txt

# Jeu de caractères précis
john --incremental=Digits hashes.txt      # Chiffres uniquement
john --incremental=Lower hashes.txt       # Minuscules uniquement
john --incremental=Upper hashes.txt       # Majuscules uniquement
john --incremental=Alpha hashes.txt       # Lettres uniquement
john --incremental=Alnum hashes.txt       # Alphanumérique
john --incremental=LowerNum hashes.txt    # Minuscules et chiffres
```

### Mode masque

Recherche par force brute selon un motif.

```bash
# Recherche par masque (? introduit les symboles de remplacement)
john --mask='?l?l?l?l?d?d' hashes.txt

# Symboles de remplacement :
# ?l = minuscules    ?u = majuscules
# ?d = chiffres      ?s = caractères spéciaux
# ?a = tous         ?h = hexadécimal en minuscules
```

### Mode externe

Algorithmes de recherche personnalisés.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Extraction des empreintes

### Mots de passe Linux

```bash
# Combiner les fichiers passwd et shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Rechercher les mots de passe dans le fichier combiné
john unshadowed.txt
```

### Empreintes Windows

```bash
# Depuis le fichier SAM (avec pwdump, secretsdump, etc.)
# Format : user:id:LM_hash:NTLM_hash:::

# Avec hashdump dans Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Enregistrer dans un fichier et lancer la recherche
john --format=nt hashes.txt
```

### Mots de passe de fichiers (outils 2john)

```bash
# Fichiers ZIP
zip2john protected.zip > zip_hash.txt

# Fichiers RAR
rar2john protected.rar > rar_hash.txt

# Fichiers PDF
pdf2john protected.pdf > pdf_hash.txt

# Documents Office
office2john document.docx > office_hash.txt

# Clés SSH
ssh2john id_rsa > ssh_hash.txt

# Fichiers 7zip
7z2john archive.7z > 7z_hash.txt

# Bases KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Types d’empreintes courants

### Tableau des formats d’empreintes

| Type d’empreinte | Option de format | Exemple |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Lister tous les formats
```bash
# Lister tous les formats pris en charge
john --list=formats

# Rechercher un format précis
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Compter les formats (voir la précision)
john --list=formats | wc -l
```

### Identifier le type d’empreinte
```bash
# Utiliser hash-identifier
hash-identifier

# Ou hashid
hashid -m '$6$salt$hash...'
```

---

## 🐧 Recherche des mots de passe Linux

### Préparer les empreintes
```bash
# Combiner passwd et shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Ou extraire uniquement l’empreinte
sudo cat /etc/shadow | grep username
```

### Exemples de formats d’empreintes
```
# Format de /etc/shadow :
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (présenté dans la source comme courant sous Linux moderne)
# $y$ = yescrypt (présenté comme le plus récent dans la source)
```

### Rechercher les mots de passe Linux
```bash
# Détection automatique (recommandée)
john linux_hashes.txt

# Indiquer le format
john --format=sha512crypt linux_hashes.txt

# Avec une liste de mots
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Afficher les résultats
john --show linux_hashes.txt
```

---

## 🪟 Recherche des mots de passe Windows

### Types d’empreintes

| Type | Description | Format |
|------|-------------|--------|
| **LM** | Ancien format (faible) | `--format=lm` |
| **NTLM** | Windows moderne | `--format=nt` |
| **NTLMv2** | Authentification réseau | `--format=netntlmv2` |

### Rechercher les mots de passe des empreintes NTLM
```bash
# Format des empreintes NTLM :
# user:rid:lm_hash:ntlm_hash:::

# Rechercher les mots de passe des empreintes NTLM
john --format=nt windows_hashes.txt

# Avec une liste de mots
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Avec des règles
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Extraire les empreintes Windows
```bash
# Avec Metasploit
meterpreter> hashdump

# Avec secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Avec pwdump
pwdump7.exe
```

---

## 📄 Recherche des mots de passe de fichiers

### Fichiers ZIP
```bash
# Extraire l’empreinte
zip2john protected.zip > zip.hash

# Lancer la recherche
john --wordlist=rockyou.txt zip.hash

# Afficher le mot de passe
john --show zip.hash
```

### Fichiers RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### Fichiers PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Documents Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Clés privées SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Base KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-règles-et-transformations"></a>
## ⚙️ Règles et transformations

### Activer les règles
```bash
# Règles par défaut
john --wordlist=wordlist.txt --rules hashes.txt

# Jeu de règles précis
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Lister les règles disponibles
```bash
john --list=rules
```

### Opérations courantes des règles

| Règle | Description | Exemple |
|------|-------------|---------|
| `:` | Ne rien modifier | password → password |
| `l` | Tout convertir en minuscules | PassWord → password |
| `u` | Tout convertir en majuscules | password → PASSWORD |
| `c` | Mettre une majuscule initiale | password → Password |
| `r` | Inverser | password → drowssap |
| `d` | Dupliquer | pass → passpass |
| `$X` | Ajouter le caractère X à la fin | pass → passX |
| `^X` | Ajouter le caractère X au début | pass → Xpass |
| `sXY` | Remplacer X par Y | password → pa$$word |

### Règles personnalisées
```bash
# Ajouter à john.conf dans [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Gestion des sessions

### Créer une session nommée
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Restaurer une session
```bash
# Restaurer une session interrompue
john --restore=mycrack

# Restaurer la session par défaut
john --restore
```

### Vérifier l’état
```bash
# Appuyer sur une touche pendant la recherche pour afficher l’état
# Ou examiner le fichier .rec
cat ~/.john/mycrack.rec
```

### Fichier pot
```bash
# Fichier pot par défaut
~/.john/john.pot

# Fichier pot personnalisé
john --pot=custom.pot hashes.txt

# Afficher tous les mots de passe retrouvés
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Réglage des performances

### Fork (plusieurs processus)
```bash
# Utiliser plusieurs cœurs CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# Lister les périphériques OpenCL
john --list=opencl-devices

# Utiliser le GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Limiter les candidats
```bash
# Limiter la longueur des mots de passe
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Utilisation de la mémoire
```bash
# Réduire l’utilisation mémoire (voir la précision)
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Exemples pratiques

### Exemple 1 : fichier shadow Linux
```bash
# Combiner les fichiers
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Effectuer la recherche avec rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Afficher les résultats
john --show hashes.txt
```

### Exemple 2 : NTLM Windows
```bash
# Depuis la sortie de hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Lancer la recherche
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Exemple 3 : mot de passe d’un fichier ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Exemple 4 : phrase secrète d’une clé SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Exemple 5 : empreintes MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Exemple 6 : empreintes d’une application web
```bash
# Empreinte bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Exemple 7 : avec des règles
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Exemple 8 : recherche incrémentale
```bash
# Essayer les combinaisons de 6 à 8 caractères, en minuscules et chiffres
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
|------|---------|
| Recherche simple | `john hashes.txt` |
| Recherche avec une liste de mots | `john --wordlist=rockyou.txt hashes.txt` |
| Avec des règles | `john --wordlist=words.txt --rules hashes.txt` |
| Afficher les résultats | `john --show hashes.txt` |
| Format précis | `john --format=raw-md5 hashes.txt` |
| Incrémental | `john --incremental hashes.txt` |
| Restaurer une session | `john --restore=session` |
| Lister les formats | `john --list=formats` |

### Outils 2john

| Outil | Utilité |
|------|---------|
| `unshadow` | Fichiers passwd/shadow Linux |
| `zip2john` | Fichiers ZIP |
| `rar2john` | Fichiers RAR |
| `pdf2john` | Fichiers PDF |
| `office2john` | Documents Office |
| `ssh2john` | Clés SSH |
| `keepass2john` | Bases KeePass |

### Formats courants

| Empreinte | Option de format |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 Conseils et bonnes pratiques

### Conseils d’efficacité

1. **Essayer d’abord le mode Single**
   ```bash
   john --single hashes.txt
   ```

2. **Utiliser des listes de mots ciblées**
   ```bash
   # Nom de l’entreprise, ville, etc.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Commencer par de petites listes**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Essayer ensuite des listes plus grandes
   ```

4. **Combiner les modes**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Conseils de performance

```bash
# Utiliser plusieurs cœurs
john --fork=4 hashes.txt

# Accélération GPU (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** John the Ripper est un puissant outil de recherche de mots de passe, à utiliser uniquement à des **fins autorisées**.
> 
> - ✅ Auditer vos propres mots de passe
> - ✅ Tester avec une autorisation explicite
> - ✅ Réaliser des évaluations de sécurité autorisées
> - ❌ Ne jamais rechercher les mots de passe d’autrui sans autorisation
> - ❌ L’accès non autorisé est illégal
> 
> **La recherche de mots de passe sans autorisation est illégale et peut entraîner des poursuites pénales.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de John the Ripper](https://www.openwall.com/john/)
- [John Jumbo sur GitHub](https://github.com/openwall/john)
- [Wiki John](https://openwall.info/wiki/john)

### Listes de mots
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Fiches connexes
- [Hydra](../../Hydra/translations/README.fr.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)

---

<p align="center">
  <b>🔨 Maîtrisez l’audit et la récupération des mots de passe !</b><br>
  <i>Travaillez toujours dans le cadre d’une autorisation</i>
</p>
