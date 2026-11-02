# 🔓 Hydra — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    The Fast Network Login Cracker
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 Un outil de référence pour les tests parallèles d’authentification réseau</b>
</p>

> **Précisions sur les options :** les exemples originaux sont conservés. `-t` règle les connexions parallèles par cible, pas le nombre total d’essais ; réduire ce nombre ne garantit pas l’absence de verrouillage. `-w` est le délai d’attente d’une réponse, tandis que `-W` concerne l’attente entre connexions d’une tâche. `-T` règle le parallélisme global avec plusieurs cibles, pas un délai de connexion. `-R` restaure une session interrompue au lieu d’en créer une sauvegarde. `-V` affiche chaque paire essayée ; ce n’est pas une vérification autonome de la version. Voir [l’aide dans le code officiel](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).
>
> **Exemples et modules :** `sshkey` attend des chemins de clés privées PEM non chiffrées via `-p`/`-P`, pas des clés générées par `-x` ; voir le [module officiel](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c). L’invocation `ssh ftp mysql` ne constitue pas une sélection valide de plusieurs services. Le module LDAP disponible et les bibliothèques de protocoles dépendent de la compilation. Les noms HTTP de la liste et les exemples ne sont pas tous cohérents entre eux : utilisez l’aide du module installé. Pour `-x`, `a`, `A` et `1` désignent des ensembles ; `!` est un caractère littéral, pas une classe universelle de caractères spéciaux. Voir le [générateur officiel](https://github.com/vanhauser-thc/thc-hydra/blob/master/bfg.c). Les valeurs d’exemple sont celles du document source. Le dessin ASCII garde son texte original.
>
> **Interprétation :** un proxy ou un faible parallélisme ne garantit pas la discrétion. Les adresses, chaînes de réussite/échec, chemins de listes de mots et identifiants illustrent des cas particuliers ; leur présence ne garantit pas qu’ils correspondent à une application donnée.

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Protocoles pris en charge](#-protocoles-pris-en-charge)
- [Tests d’authentification SSH](#-tests-dauthentification-ssh)
- [Tests d’authentification FTP](#-tests-dauthentification-ftp)
- [Tests des formulaires HTTP](#-tests-des-formulaires-http)
- [Tests des bases de données](#-tests-des-bases-de-données)
- [Tests des services de bureau distant](#-tests-des-services-de-bureau-distant)
- [Autres services](#-autres-services)
- [Listes de mots et mots de passe](#-listes-de-mots-et-mots-de-passe)
- [Options avancées](#-options-avancées)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Hydra** (THC-Hydra) est un outil rapide et flexible de test de mots de passe en ligne. Il prend en charge de nombreux protocoles et exécute plusieurs tâches en parallèle.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Plus de 50 protocoles** | SSH, FTP, HTTP, RDP, SMB et d’autres |
| **Parallélisé** | Plusieurs connexions simultanées |
| **Flexible** | En-têtes personnalisés, cookies et proxy |
| **Reprise** | Enregistrer et reprendre des sessions de test |
| **Multiplateforme** | Linux, Windows, macOS |

### Types de tests

| Type | Description |
| ------ | ------------- |
| **Dictionnaire** | Essayer les mots de passe d’une liste |
| **Force brute** | Générer toutes les combinaisons |
| **Password spraying** | Un seul mot de passe essayé sur plusieurs comptes |
| **Réutilisation d’identifiants divulgués (credential stuffing)** | Paires nom d’utilisateur:mot de passe |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Depuis les sources
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Vérifier l’installation
```bash
hydra -h
hydra -V
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
hydra [options] target service
```

### Options courantes

| Option | Description |
| -------- | ------------- |
| `-l` | Un seul nom d’utilisateur |
| `-L` | Liste de noms d’utilisateur |
| `-p` | Un seul mot de passe |
| `-P` | Liste de mots de passe |
| `-C` | Fichier séparé par des deux-points (user:pass) |
| `-t` | Tâches parallèles (valeur par défaut indiquée : 16) |
| `-s` | Numéro de port |
| `-o` | Fichier de sortie |
| `-f` | Arrêter à la première paire valide |
| `-V` | Afficher chaque tentative (libellé source : mode détaillé) |
| `-vV` | Sortie très détaillée |
| `-d` | Mode débogage |

### Prise en main rapide
```bash
# Un utilisateur et une liste de mots de passe
hydra -l admin -P passwords.txt target ssh

# Une liste d’utilisateurs et un seul mot de passe
hydra -L users.txt -p password123 target ssh

# Les deux listes
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Protocoles pris en charge

### Liste complète des protocoles

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 Tests d’authentification SSH

### Test SSH élémentaire
```bash
# Un seul utilisateur
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Plusieurs utilisateurs
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Port particulier
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH avec options
```bash
# Limiter les tentatives pour éviter un verrouillage (libellé source ; voir la précision)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Arrêter après la première réussite
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Afficher chaque tentative
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Authentification SSH par clé
```bash
# Utiliser une clé privée pour vérifier des clés faibles (exemple source ; voir la précision)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 Tests d’authentification FTP

### Test FTP élémentaire
```bash
# Force brute FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Vérification de l’accès FTP anonyme
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Plusieurs utilisateurs
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP sur SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 Tests des formulaires HTTP

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Format :
# "/path:params:failure_string"
```

### HTTP-POST-FORM (le plus courant)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Syntaxe des tests de formulaire

```
"/page:POST_params:Failure_string"

Où :
- ^USER^ = emplacement du nom d’utilisateur
- ^PASS^ = emplacement du mot de passe
- Failure_string = texte affiché lors d’un échec de connexion
```

### Détection fondée sur la réussite

```bash
# Utiliser S= pour une chaîne de réussite plutôt que d’échec
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Avec des cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### Authentification HTTP Basic

```bash
# Authentification HTTP Basic
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### Proxy HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-tests-des-bases-de-données"></a>
## 🗄️ Tests des bases de données

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Port particulier
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Test d’authentification Oracle avec SID
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Listener Oracle
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-tests-des-services-de-bureau-distant"></a>
## 🖥️ Tests des services de bureau distant

### RDP (bureau distant Windows)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Limiter les tâches parallèles (RDP est lent)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (mot de passe uniquement)
hydra -P passwords.txt vnc://192.168.1.1

# Version particulière de VNC (libellé source ; le port change ici)
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Autres services

### SMB (partages Windows)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# Authentification SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Énumération SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Listes de mots et mots de passe

### Listes de mots courantes

```bash
# Emplacements sur Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Mots de passe SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Génération de mots de passe (-x)

```bash
# Générer des mots de passe : min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4 à 6 lettres minuscules
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4 à 6 lettres majuscules
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4 à 6 chiffres
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Mélange

# Options du jeu de caractères :
# a = minuscules    A = majuscules
# 1 = chiffres      ! = caractères spéciaux (libellé source ; voir la précision)
```

### Fichier séparé par des deux-points (-C)

```bash
# Format user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Personnalisation des noms et mots de passe

```bash
# Mots de passe vides
hydra -l admin -e n 192.168.1.1 ssh

# Essayer le nom d’utilisateur comme mot de passe
hydra -l admin -e s 192.168.1.1 ssh

# Essayer le nom d’utilisateur inversé comme mot de passe
hydra -l admin -e r 192.168.1.1 ssh

# Toutes les vérifications particulières
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Options avancées

### Sortie et journalisation

```bash
# Enregistrer les résultats dans un fichier
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Sortie JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Reprendre les tests

```bash
# Enregistrer la session pour la reprendre (libellé source ; -R restaure une session)
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Reprendre un test interrompu
hydra -R
```

### Prise en charge des proxys

```bash
# Proxy HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Proxy SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Réglage des performances

```bash
# Tâches parallèles (valeur par défaut indiquée : 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Délai entre les tentatives (libellé source ; -w attend une réponse)
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Délai de connexion (libellé source ; -T règle le parallélisme global)
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Plusieurs cibles

```bash
# Depuis un fichier
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Exemples pratiques

### Exemple 1 : force brute SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Exemple 2 : formulaire de connexion web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Exemple 3 : connexion WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Exemple 4 : plusieurs services
```bash
# Tester plusieurs services sur un hôte (syntaxe source à ne pas considérer comme valide)
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Exemple 5 : password spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Exemple 6 : avec proxy ou Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Exemple 7 : HTTP Basic sur une interface d’administration
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Exemple 8 : test RDP (prudence)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
| ------ | --------- |
| Test SSH | `hydra -l user -P pass.txt ssh://target` |
| Test FTP | `hydra -l user -P pass.txt ftp://target` |
| Formulaire HTTP POST | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Test RDP | `hydra -l user -P pass.txt rdp://target` |
| Enregistrer la sortie | `hydra ... -o results.txt` |
| Reprendre un test | `hydra -R` |

### Options courantes

| Option | Description |
| -------- | ------------- |
| `-l/-L` | Nom d’utilisateur ou liste |
| `-p/-P` | Mot de passe ou liste |
| `-C` | Fichier user:pass |
| `-t` | Tâches parallèles |
| `-s` | Port |
| `-f` | Arrêter après une réussite |
| `-V` | Affichage détaillé |
| `-o` | Fichier de sortie |
| `-x` | Générer des mots de passe |
| `-e nsr` | Essayer un mot de passe vide, identique au nom ou inversé |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

1. **Limiter les tâches parallèles pour les services sensibles**
   ```bash
   # RDP et SMB : limiter les tâches parallèles
   hydra -t 1 ...
   
   # SSH : parallélisme modéré
   hydra -t 4 ...
   
   # Formulaires web : parallélisme éventuellement plus élevé
   hydra -t 16 ...
   ```

2. **Utiliser des listes de mots ciblées**
   ```bash
   # Choix plus ciblés que rockyou.txt :
   # - Les 1000 mots de passe les plus courants
   # - Les identifiants par défaut
   # - Des mots de passe propres à l’entreprise
   ```

3. **Vérifier d’abord les règles de verrouillage**
   ```bash
   # Commencer par une petite liste de mots
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Conseils présentés comme favorisant la discrétion

```bash
# Ajouter des délais (libellé source ; -w est un délai d’attente de réponse)
hydra -l admin -P passwords.txt -w 30 target ssh

# Limiter les tâches parallèles
hydra -l admin -P passwords.txt -t 2 target ssh

# Passer par un proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Hydra est un outil puissant qui doit être réservé aux **tests autorisés**.
> 
> - ✅ Tester les systèmes dont vous êtes propriétaire
> - ✅ Tester avec une autorisation écrite explicite
> - ✅ Utiliser dans le cadre de tests d’intrusion autorisés
> - ❌ Ne jamais effectuer de test sans autorisation
> - ❌ La force brute sans autorisation est illégale
> 
> **Les tentatives d’accès non autorisé sont illégales et peuvent entraîner des poursuites pénales.**

---

## 📚 Ressources

### Ressources officielles
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Manuel Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Listes de mots
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Identifiants par défaut](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Fiches connexes
- [Nmap](../../Nmap/translations/README.fr.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)
- [SQLMap](../../SQLMap/translations/README.fr.md)

---

<p align="center">
  <b>🔓 Maîtrisez les tests d’authentification réseau !</b><br>
  <i>Obtenez toujours une autorisation préalable</i>
</p>
