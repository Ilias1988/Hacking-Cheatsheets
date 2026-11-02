# 🔍 Nmap — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - The Network Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 Un outil majeur d’analyse et de découverte des réseaux</b>
</p>

> **Précisions de lecture :** les termes « discret » et « évasion » reprennent la source ; ils ne garantissent pas l’invisibilité. Une connexion TCP peut être filtrée ou refusée : « toujours fonctionnelle » n’est pas une garantie. Les limitations des analyses NULL/FIN/Xmas dépendent de la pile TCP et du filtrage, pas seulement du nom du système. Les commandes et les sorties d’exemple restent inchangées.
>
> **Découverte et couverture :** `-Pn` considère les cibles comme actives, mais la découverte ARP/Neighbor Discovery reste normalement utilisée sur le réseau Ethernet local. `-PR` seul ne désactive pas l’analyse des ports ; c’est `-sn` qui le fait. Voir la [découverte des hôtes](https://nmap.org/book/man-host-discovery.html). `-A` active la détection du système, des versions, les scripts par défaut et traceroute ; il ne sélectionne ni tous les ports ni un profil de vitesse. Voir le [résumé officiel des options](https://nmap.org/book/man-briefoptions.html).
>
> **Retransmissions et NSE :** l’exemple `--max-retries 3` fixe un plafond de trois retransmissions, inférieur au plafond par défaut de dix ; il ne constitue donc pas une augmentation par rapport au réglage initial. Voir la [temporisation](https://nmap.org/book/man-performance.html). La catégorie `auth` couvre l’authentification et ses contournements ; `safe` décrit une classification, pas une garantie absolue d’absence d’effets. Voir les [catégories NSE](https://nmap.org/book/nse-usage.html). Les exemples `--script "not intrusive"` et `"http-*"` peuvent sélectionner un ensemble large de scripts.

---

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Définition des cibles](#-définition-des-cibles)
- [Découverte des hôtes](#-découverte-des-hôtes)
- [Techniques d’analyse des ports](#-techniques-danalyse-des-ports)
- [Sélection des ports](#-sélection-des-ports)
- [Détection des services et des versions](#-détection-des-services-et-des-versions)
- [Détection du système d’exploitation](#-détection-du-système-dexploitation)
- [Temporisation et performances](#-temporisation-et-performances)
- [Scripts NSE](#-scripts-nse)
- [Formats de sortie](#-formats-de-sortie)
- [Évasion des pare-feu et IDS](#-évasion-des-pare-feu-et-ids)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Nmap** (Network Mapper) est un utilitaire libre et gratuit de découverte réseau et d’audit de sécurité. C’est un outil de référence pour l’analyse des réseaux.

### Principales fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Découverte des hôtes** | Repérer les hôtes actifs sur un réseau |
| **Analyse des ports** | Identifier les ports ouverts et les services |
| **Détection des versions** | Déterminer les versions des services |
| **Détection du système d’exploitation** | Identifier les systèmes d’exploitation |
| **Scripts NSE** | Recherche de vulnérabilités et exploitation |
| **Formats de sortie** | XML, sortie exploitable par grep et sortie normale |

### États des ports

| État | Description |
|-------|-------------|
| `open` | Une application accepte les connexions |
| `closed` | Le port est accessible, mais aucune application n’y écoute |
| `filtered` | Nmap ne peut pas déterminer si le port est ouvert (filtrage) |
| `unfiltered` | Le port est accessible, mais son état reste inconnu |
| `open\|filtered` | Impossible de distinguer ouvert et filtré |
| `closed\|filtered` | Impossible de distinguer fermé et filtré |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
# Mettre Nmap à jour
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Télécharger depuis https://nmap.org/download.html
# Ou utiliser Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Vérifier l’installation
```bash
nmap --version
nmap -h          # Aide
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
nmap [Scan Type] [Options] {target}
```

### Exemples pour débuter
```bash
# Analyse élémentaire
nmap 192.168.1.1

# Analyser plusieurs hôtes
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Analyser une plage
nmap 192.168.1.1-254

# Analyser tout le sous-réseau
nmap 192.168.1.0/24

# Analyser les cibles d’un fichier
nmap -iL targets.txt
```

---

## 🎯 Définition des cibles

### Cibles individuelles
```bash
# Adresse IP
nmap 192.168.1.1

# Nom d’hôte
nmap scanme.nmap.org

# Domaine
nmap example.com
```

### Cibles multiples
```bash
# Adresses séparées par des espaces
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Plage
nmap 192.168.1.1-100

# Notation CIDR
nmap 192.168.1.0/24

# Caractère générique
nmap 192.168.1.*

# Plage d’octets
nmap 192.168.0-255.1-254

# Combinaison de formats
nmap 192.168.1.0/24 10.0.0.1
```

### Entrée depuis un fichier
```bash
# Lire depuis un fichier
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Exclure des cibles
```bash
# Exclure un hôte
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Exclure les cibles d’un fichier
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Cibles aléatoires
```bash
# Analyser des hôtes aléatoires (recherche, selon la source)
nmap -iR 100
```

---

## 🔎 Découverte des hôtes

### Options de découverte

| Option | Description |
|--------|-------------|
| `-sL` | Analyse par liste : énumérer uniquement les cibles |
| `-sn` | Découverte par ping, sans analyse des ports |
| `-Pn` | Ignorer la découverte et considérer tous les hôtes comme actifs |
| `-PS` | Ping TCP SYN |
| `-PA` | Ping TCP ACK |
| `-PU` | Ping UDP |
| `-PE` | Requête d’écho ICMP |
| `-PP` | Requête d’horodatage ICMP |
| `-PM` | Requête de masque réseau ICMP |
| `-PO` | Sonde de protocole IP |
| `-PR` | Ping ARP sur le réseau local |

### Découverte par ping (sans analyse des ports)
```bash
# Découvrir uniquement les hôtes actifs
nmap -sn 192.168.1.0/24

# Découverte par ping à partir d’une liste d’hôtes
nmap -sn -iL hosts.txt
```

### Ignorer la découverte des hôtes
```bash
# Analyser tous les hôtes sans découverte préalable
nmap -Pn 192.168.1.1

# Utile lorsque :
# - Les hôtes bloquent ICMP
# - L’analyse traverse un pare-feu
```

### Ping TCP SYN/ACK
```bash
# Ping TCP SYN sur le port 80
nmap -PS80 192.168.1.1

# Ping TCP ACK sur le port 80
nmap -PA80 192.168.1.1

# Plusieurs ports
nmap -PS22,80,443 192.168.1.1
```

### Ping UDP
```bash
# Ping UDP
nmap -PU53 192.168.1.1

# Plusieurs ports
nmap -PU53,161 192.168.1.1
```

### Ping ICMP
```bash
# Écho ICMP
nmap -PE 192.168.1.1

# Horodatage ICMP
nmap -PP 192.168.1.1

# Masque réseau ICMP
nmap -PM 192.168.1.1
```

### Ping ARP (réseau local)
```bash
# Analyse ARP, très rapide sur le réseau local
nmap -PR 192.168.1.0/24

# Découverte ARP uniquement
nmap -sn -PR 192.168.1.0/24
```

### Désactiver la résolution DNS
```bash
# Sans résolution DNS, pour aller plus vite
nmap -n 192.168.1.1

# Toujours effectuer la résolution DNS
nmap -R 192.168.1.1
```

---

## 🔌 Techniques d’analyse des ports

### Vue d’ensemble des types d’analyse

| Option | Nom | Description |
|--------|------|-------------|
| `-sS` | Analyse SYN | Analyse dite discrète, par défaut avec les privilèges nécessaires |
| `-sT` | Connexion TCP complète | Connexion TCP complète, sans privilèges root nécessaires |
| `-sU` | Analyse UDP | Analyser les ports UDP |
| `-sA` | Analyse ACK | Cartographier les règles du pare-feu |
| `-sW` | Analyse Window | Semblable à ACK, exploite le champ de fenêtre TCP |
| `-sM` | Analyse Maimon | Sonde FIN/ACK |
| `-sN` | Analyse NULL | Aucun drapeau activé |
| `-sF` | Analyse FIN | Drapeau FIN activé |
| `-sX` | Analyse Xmas | Drapeaux FIN, PSH et URG |

### Analyse SYN dite discrète (par défaut)
```bash
# Analyse la plus courante, nécessitant les privilèges root
sudo nmap -sS 192.168.1.1

# Ne termine pas l’établissement de la connexion TCP
# Rapide et dite discrète dans la source
```

### Analyse par connexion TCP complète
```bash
# Connexion TCP complète
nmap -sT 192.168.1.1

# Ne nécessite pas les privilèges root
# Plus visible et présentée comme toujours fonctionnelle dans la source
```

### Analyse UDP
```bash
# Analyser les ports UDP
sudo nmap -sU 192.168.1.1

# L’analyse UDP est LENTE
# Combiner avec la détection des versions
sudo nmap -sU -sV 192.168.1.1

# Ports UDP courants
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Analyse TCP/UDP combinée
```bash
# Analyser TCP et UDP
sudo nmap -sS -sU 192.168.1.1

# Ports précis
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### Analyses NULL, FIN et Xmas
```bash
# Analyse NULL, sans drapeau
sudo nmap -sN 192.168.1.1

# Analyse FIN, seul le drapeau FIN est activé
sudo nmap -sF 192.168.1.1

# Analyse Xmas, drapeaux FIN, PSH et URG
sudo nmap -sX 192.168.1.1

# Utilisations et limites indiquées dans la source :
# - Contournement des pare-feu sans suivi d’état
# - Ne fonctionne pas sur Windows selon la source
```

### Analyse ACK
```bash
# Cartographier les règles du pare-feu
sudo nmap -sA 192.168.1.1

# Déterminer :
# - Quels ports sont filtrés
# - Les règles du pare-feu
# Renvoie : unfiltered ou filtered
```

### Analyse Window
```bash
# Semblable à l’analyse ACK
sudo nmap -sW 192.168.1.1

# Utilise la taille de fenêtre TCP pour estimer l’état
```

### Analyse indirecte par hôte zombie (Idle Scan)
```bash
# Discrétion maximale annoncée dans la source : utiliser un hôte zombie
sudo nmap -sI zombie_host 192.168.1.1

# L’adresse IP de l’analyste ne contacte pas la cible selon la source
# Nécessite un hôte zombie adapté
```

---

<a id="-sélection-des-ports"></a>
## 🎚️ Sélection des ports

### Options de sélection des ports

| Option | Description |
|--------|-------------|
| `-p <port>` | Analyser des ports précis |
| `-p-` | Analyser les 65535 ports |
| `-p 1-1000` | Analyser une plage de ports |
| `--top-ports <n>` | Analyser les N ports les plus fréquents |
| `-F` | Analyse rapide des 100 ports les plus fréquents |
| `-r` | Analyser les ports dans l’ordre |

### Ports précis
```bash
# Un seul port
nmap -p 80 192.168.1.1

# Plusieurs ports
nmap -p 80,443,22 192.168.1.1

# Plage de ports
nmap -p 1-1000 192.168.1.1

# Tous les ports
nmap -p- 192.168.1.1

# Combinaison de formats
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Ports par protocole
```bash
# Ports TCP
nmap -p T:80,443 192.168.1.1

# Ports UDP
nmap -p U:53,161 192.168.1.1

# Les deux protocoles
nmap -p T:80,443,U:53 192.168.1.1
```

### Ports les plus fréquents
```bash
# Les 100 ports les plus fréquents, pour une analyse rapide
nmap -F 192.168.1.1

# Les 10 ports les plus fréquents
nmap --top-ports 10 192.168.1.1

# Les 1000 ports les plus fréquents, par défaut
nmap --top-ports 1000 192.168.1.1
```

### Référence des ports courants

| Port | Service |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Détection des services et des versions

### Options de détection des versions

| Option | Description |
|--------|-------------|
| `-sV` | Détection des versions |
| `--version-intensity <0-9>` | Intensité des sondes |
| `--version-light` | Détection légère des versions, intensité 2 |
| `--version-all` | Essayer toutes les sondes, intensité 9 |
| `--version-trace` | Déboguer la détection des versions |

### Détection élémentaire des versions
```bash
# Détecter les versions des services
nmap -sV 192.168.1.1

# Exemple de sortie :
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensité des sondes de version
```bash
# Analyse légère, plus rapide
nmap -sV --version-light 192.168.1.1

# Toutes les sondes, pour une analyse approfondie
nmap -sV --version-all 192.168.1.1

# Intensité personnalisée de 0 à 9
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 Détection du système d’exploitation

### Options de détection du système

| Option | Description |
|--------|-------------|
| `-O` | Activer la détection du système |
| `--osscan-limit` | Limiter la détection aux hôtes prometteurs |
| `--osscan-guess` | Estimer le système de manière plus poussée |
| `--max-os-tries` | Nombre maximal de tentatives de détection du système |

### Détection élémentaire du système
```bash
# Détecter le système, avec les privilèges root
sudo nmap -O 192.168.1.1

# Exemple de sortie :
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Estimation plus poussée du système
```bash
# Estimation plus poussée
sudo nmap -O --osscan-guess 192.168.1.1
```

### Analyse approfondie (-A)
```bash
# Activer la détection du système, des versions, les scripts et traceroute
sudo nmap -A 192.168.1.1

# Équivalent indiqué : -O -sV -sC --traceroute
```

---

<a id="-temporisation-et-performances"></a>
## ⏱️ Temporisation et performances

### Profils de temporisation

| Option | Nom | Description |
|--------|------|-------------|
| `-T0` | Paranoid | Évasion IDS selon la source, très lent |
| `-T1` | Sneaky | Évasion IDS selon la source, lent |
| `-T2` | Polite | Moins de bande passante, plus lent |
| `-T3` | Normal | Temporisation par défaut |
| `-T4` | Aggressive | Rapide, pour un réseau fiable |
| `-T5` | Insane | Très rapide, peut manquer des ports |

### Utilisation
```bash
# Lent et dit discret dans la source
nmap -T1 192.168.1.1

# Normal, par défaut
nmap -T3 192.168.1.1

# Rapide
nmap -T4 192.168.1.1

# Très rapide, sur réseau local
nmap -T5 192.168.1.1
```

### Réglages précis de temporisation

| Option | Description |
|--------|-------------|
| `--min-rate <n>` | Nombre minimal de paquets par seconde |
| `--max-rate <n>` | Nombre maximal de paquets par seconde |
| `--min-parallelism <n>` | Nombre minimal de sondes simultanées |
| `--max-parallelism <n>` | Nombre maximal de sondes simultanées |
| `--min-hostgroup <n>` | Nombre minimal d’hôtes traités en parallèle |
| `--max-hostgroup <n>` | Nombre maximal d’hôtes traités en parallèle |
| `--host-timeout <time>` | Abandonner un hôte à l’expiration du délai |
| `--scan-delay <time>` | Délai entre les sondes |
| `--max-retries <n>` | Nombre maximal de retransmissions des sondes |

```bash
# Envoyer au moins 1000 paquets par seconde
nmap --min-rate 1000 192.168.1.0/24

# Limiter à 100 paquets par seconde pour ralentir
nmap --max-rate 100 192.168.1.1

# Abandonner l’hôte après 30 minutes
nmap --host-timeout 30m 192.168.1.0/24

# Délai entre les sondes
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 Scripts NSE

### Catégories de scripts

| Catégorie | Description |
|----------|-------------|
| `auth` | Authentification et contournements associés |
| `broadcast` | Découvrir des hôtes par diffusion |
| `brute` | Attaques par force brute |
| `default` | Scripts par défaut (-sC) |
| `discovery` | Découverte des services |
| `dos` | Déni de service |
| `exploit` | Exploiter des vulnérabilités |
| `external` | Interroger des services externes |
| `fuzzer` | Fuzzing, tests par entrées variées |
| `intrusive` | Peut provoquer l’arrêt de services |
| `malware` | Détection de logiciels malveillants |
| `safe` | Scripts classés safe |
| `version` | Détection des versions |
| `vuln` | Détection de vulnérabilités |

### Options des scripts

| Option | Description |
|--------|-------------|
| `-sC` | Scripts par défaut |
| `--script <scripts>` | Exécuter des scripts précis |
| `--script-args <args>` | Arguments des scripts |
| `--script-updatedb` | Mettre à jour l’index des scripts |
| `--script-help <script>` | Aide des scripts |

### Scripts par défaut
```bash
# Exécuter les scripts par défaut
nmap -sC 192.168.1.1

# Avec détection des versions, recommandé
nmap -sV -sC 192.168.1.1
```

### Exécuter des scripts précis
```bash
# Un seul script
nmap --script http-title 192.168.1.1

# Plusieurs scripts
nmap --script http-title,http-headers 192.168.1.1

# Catégorie
nmap --script vuln 192.168.1.1

# Caractère générique
nmap --script "http-*" 192.168.1.1

# Combiner des catégories
nmap --script "vuln and safe" 192.168.1.1

# Exclure des scripts
nmap --script "not intrusive" 192.168.1.1
```

### Scripts courants

#### Recherche de vulnérabilités
```bash
# Tous les scripts de recherche de vulnérabilités
nmap --script vuln 192.168.1.1

# Vérification d’une CVE précise
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### Scripts HTTP
```bash
# Énumération HTTP
nmap --script http-enum 192.168.1.1

# En-têtes HTTP
nmap --script http-headers 192.168.1.1

# Méthodes HTTP
nmap --script http-methods 192.168.1.1

# Titre HTTP
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### Scripts SMB
```bash
# Énumération SMB
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# Vulnérabilités SMB
nmap --script smb-vuln* 192.168.1.1
```

#### Scripts SSH
```bash
# Méthodes d’authentification SSH
nmap --script ssh-auth-methods 192.168.1.1

# Clé d’hôte SSH
nmap --script ssh-hostkey 192.168.1.1

# Force brute SSH
nmap --script ssh-brute 192.168.1.1
```

#### Scripts DNS
```bash
# Force brute DNS
nmap --script dns-brute example.com

# Transfert de zone DNS
nmap --script dns-zone-transfer example.com
```

#### Scripts MySQL et bases de données
```bash
# Informations MySQL
nmap --script mysql-info 192.168.1.1

# Énumération MySQL
nmap --script mysql-enum 192.168.1.1

# Force brute MySQL
nmap --script mysql-brute 192.168.1.1
```

### Arguments des scripts
```bash
# Force brute avec une liste de mots
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Force brute de l’authentification HTTP
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Formats de sortie

### Options de sortie

| Option | Description |
|--------|-------------|
| `-oN <file>` | Sortie normale |
| `-oX <file>` | Sortie XML |
| `-oG <file>` | Sortie exploitable par grep |
| `-oA <basename>` | Tous les formats |
| `-oS <file>` | Sortie fantaisie dite script kiddie |

### Enregistrer la sortie
```bash
# Sortie normale
nmap -oN scan.txt 192.168.1.1

# Sortie XML pour les outils
nmap -oX scan.xml 192.168.1.1

# Sortie exploitable par grep
nmap -oG scan.gnmap 192.168.1.1

# Tous les formats simultanément
nmap -oA scan 192.168.1.1
# Crée : scan.nmap, scan.xml, scan.gnmap
```

### Niveau de détail et débogage

| Option | Description |
|--------|-------------|
| `-v` | Augmenter le niveau de détail |
| `-vv` | Sortie plus détaillée |
| `-d` | Mode de débogage |
| `-dd` | Débogage plus détaillé |
| `--reason` | Afficher la justification de l’état du port |
| `--open` | N’afficher que les ports ouverts |
| `--packet-trace` | Afficher tous les paquets |

```bash
# Sortie détaillée
nmap -v 192.168.1.1

# Sortie très détaillée
nmap -vv 192.168.1.1

# Afficher uniquement les ports ouverts
nmap --open 192.168.1.1

# Afficher la justification
nmap --reason 192.168.1.1
```

---

<a id="-évasion-des-pare-feu-et-ids"></a>
## 🛡️ Évasion des pare-feu et IDS

### Techniques d’évasion

| Option | Description |
|--------|-------------|
| `-f` | Fragmenter les paquets |
| `--mtu <size>` | Taille MTU personnalisée |
| `-D <decoys>` | Utiliser des leurres |
| `-S <IP>` | Usurper l’adresse IP source |
| `--source-port <port>` | Usurper le port source |
| `--data-length <n>` | Ajouter des données aléatoires |
| `--randomize-hosts` | Ordre aléatoire des hôtes |
| `--spoof-mac <mac>` | Usurper l’adresse MAC |
| `--badsum` | Envoyer des sommes de contrôle incorrectes |

### Fragmentation des paquets
```bash
# Fragmenter les paquets
nmap -f 192.168.1.1

# MTU personnalisée
nmap --mtu 16 192.168.1.1
```

### Leurres
```bash
# Utiliser des leurres
nmap -D decoy1,decoy2,ME 192.168.1.1

# Leurres aléatoires
nmap -D RND:5 192.168.1.1
```

### Usurpation de l’adresse IP ou du port source
```bash
# Usurper l’adresse IP source
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Usurper le port source avec des ports souvent autorisés
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### Usurpation de l’adresse MAC
```bash
# Adresse MAC aléatoire
nmap --spoof-mac 0 192.168.1.1

# Fabricant précis
nmap --spoof-mac Apple 192.168.1.1

# Adresse MAC précise
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Ajout de données de remplissage
```bash
# Ajouter des données aléatoires aux paquets
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Exemples pratiques

### Exemple 1 : découverte rapide du réseau
```bash
# Repérer les hôtes actifs
nmap -sn 192.168.1.0/24
```

### Exemple 2 : analyse élémentaire des ports
```bash
# Analyser avec détection des versions
nmap -sV 192.168.1.1
```

### Exemple 3 : analyse complète
```bash
# Analyse complète avec détection du système et scripts
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Exemple 4 : analyse approfondie de tous les ports
```bash
# Tous les ports, analyse approfondie
sudo nmap -A -T4 -p- 192.168.1.1
```

### Exemple 5 : recherche de vulnérabilités
```bash
# Rechercher des vulnérabilités
nmap --script vuln 192.168.1.1
```

### Exemple 6 : analyse dite discrète
```bash
# Analyse dite discrète
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Exemple 7 : analyse d’un serveur web
```bash
# Analyse web complète
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Exemple 8 : énumération SMB
```bash
# Énumération SMB complète
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Exemple 9 : analyse de tout un sous-réseau
```bash
# Analyser tout le sous-réseau
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Exemple 10 : services UDP
```bash
# Services UDP courants
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `nmap <target>` | Analyse élémentaire |
| `nmap -sn <target>` | Découverte par ping uniquement |
| `nmap -Pn <target>` | Ignorer la découverte des hôtes |
| `nmap -sS <target>` | Analyse SYN dite discrète |
| `nmap -sV <target>` | Détection des versions |
| `nmap -O <target>` | Détection du système |
| `nmap -A <target>` | Analyse approfondie |
| `nmap -sC <target>` | Scripts par défaut |
| `nmap -p- <target>` | Tous les ports |
| `nmap --script vuln <target>` | Recherche de vulnérabilités |

### Vitesse et discrétion

| Besoin | Options |
|------|---------|
| **Rapide** | `-T4 --min-rate 1000` |
| **Discret selon la source** | `-T1 -f --data-length 25` |
| **Équilibré** | `-T3` (par défaut) |

### Analyses courantes

```bash
# Analyse rapide
nmap -F 192.168.1.1

# Analyse de tous les ports TCP
nmap -sT -p- 192.168.1.1

# Analyse des services
nmap -sV 192.168.1.1

# Recherche de vulnérabilités
nmap --script vuln 192.168.1.1

# Analyse globale
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

1. **Utiliser l’analyse SYN (par défaut)**
   ```bash
   # Nécessite les privilèges root ; présenté comme le plus rapide
   sudo nmap -sS 192.168.1.1
   ```

2. **Limiter d’abord la plage de ports**
   ```bash
   # Commencer par les ports les plus fréquents
   nmap --top-ports 100 192.168.1.1
   ```

3. **Augmenter la vitesse sur les réseaux locaux**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Conseils pour améliorer la précision

1. **Combiner plusieurs types d’analyse**
   ```bash
   # TCP et UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Augmenter les nouvelles tentatives sur les réseaux peu fiables**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Considérations juridiques

> ⚠️ **IMPORTANT :** analysez uniquement les réseaux qui vous appartiennent ou pour lesquels vous disposez d’une autorisation.

- ✅ Vos propres réseaux
- ✅ Environnements de laboratoire
- ✅ Tests d’intrusion autorisés
- ❌ Hôtes Internet choisis au hasard
- ❌ Réseaux sans autorisation

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Nmap est un outil puissant à réserver aux **analyses autorisées**.
> 
> - ✅ Analyser vos propres réseaux
> - ✅ Disposer d’une autorisation écrite explicite
> - ✅ L’utiliser dans des tests d’intrusion autorisés
> - ❌ Ne jamais analyser de réseau sans autorisation
> - ❌ Une analyse non autorisée peut être illégale
> 
> **Les analyses réseau non autorisées sont illégales dans de nombreuses juridictions.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de Nmap](https://nmap.org/)
- [Guide de référence de Nmap](https://nmap.org/book/man.html)
- [Documentation des scripts NSE](https://nmap.org/nsedoc/)

### Ressources d’apprentissage
- [Livre Nmap Network Scanning](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Fiches connexes
- [Metasploit Framework](../../Metasploit/translations/README.fr.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 Maîtrisez la reconnaissance réseau !</b><br>
  <i>Connaissez votre réseau avant les attaquants</i>
</p>
