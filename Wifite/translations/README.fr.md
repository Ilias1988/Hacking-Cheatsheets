# 📶 Wifite — aide-mémoire d’audit WiFi automatisé

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Outil automatisé d’audit WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Wifite ?](#-quest-ce-que-wifite-)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Sélection des cibles](#-sélection-des-cibles)
- [Options d’attaque](#-options-dattaque)
- [Attaques WPS](#-attaques-wps)
- [Attaques PMKID](#-attaques-pmkid)
- [Options avancées](#-options-avancées)
- [Options de recherche du mot de passe](#-options-de-recherche-du-mot-de-passe)
- [Exemples](#-exemples)
- [Référence rapide](#-référence-rapide)
- [Wifite et Aircrack-ng manuel](#-wifite-et-aircrack-ng-manuel)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Wifite ?

**Wifite** (Wifite2) est un outil automatisé d’audit des réseaux sans fil qui :

- 🤖 **Automatise les attaques** — Évite de saisir manuellement chaque commande
- 🎯 **Gère plusieurs cibles** — Attaque plusieurs réseaux
- 🔓 **WPA/WPA2/WEP/WPS** — Couvre les types de protection présentés dans l’original
- 💾 **Enregistre la progression** — Permet de reprendre des attaques interrompues
- 🔄 **Capture des PMKID** — Propose des attaques modernes sans client déjà connecté

> **Précision :** WPS est un mécanisme de configuration, pas un chiffrement. Cette liste ne signifie pas une prise en charge universelle de toutes les protections WiFi ; le matériel doit notamment permettre le mode moniteur et l’injection selon l’opération ([projet officiel](https://github.com/derv82/wifite2)).

### Wifite et Wifite2

| Fonction | Wifite (original) | Wifite2 |
|---------|-------------------|---------|
| Maintenance | Non | Oui |
| Python | Python 2 | Python 3 |
| PMKID | Non | Oui |
| Code | Désorganisé | Réécriture propre |
| Utilisation | À éviter | **Recommandé** |

> **Contexte historique :** ce tableau reprend les appréciations de l’original. Le dépôt derv82 renvoie désormais vers une branche Wifite2 davantage suivie ; version et dépendances varient selon le dépôt ou le paquet. Le nom du script peut aussi différer par sa casse ([README du projet](https://github.com/derv82/wifite2)).

### Fonctionnalités

| Fonction | Description |
|---------|-------------|
| **Automatisation** | Audit WiFi après sélection des cibles |
| **Sélection intelligente** | Sélection selon la puissance du signal |
| **Ensemble des attaques** | WPA, WEP, WPS, PMKID |
| **Recherche du mot de passe (cracking)** | Intégration avec Hashcat/aircrack |
| **Reprise** | Poursuite de sessions interrompues |

---

## 🚀 Installation

### Kali Linux (préinstallé)

```bash
# Déjà installé dans Kali
wifite --help

# Mettre à jour vers la dernière version
sudo apt update && sudo apt install wifite
```

### Installer Wifite2 depuis GitHub

```bash
# Cloner le dépôt
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Lancer directement
sudo python3 wifite.py

# Ou installer
sudo python3 setup.py install
```

### Dépendances

```bash
# Nécessaire
sudo apt install aircrack-ng

# Recommandé pour disposer de toutes les fonctions
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Vérifier l’installation

```bash
wifite --help
wifite --version
```

---

## 💻 Utilisation de base

> **Syntaxe des exemples :** les commandes originales restent inchangées. Certaines options, dont `--quiet`, `--attack-timeout` et `--hashcat-path`, manquent au parseur derv82 ; vérifier la version ou la branche concernée ([arguments officiels](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

### Démarrage rapide

```bash
# Lancer simplement wifite (détection automatique de l’interface)
sudo wifite

# Préciser l’interface
sudo wifite -i wlan0
```

### Déroulement de base

```
1. Lancer wifite
2. Attendre l’apparition des réseaux
3. Appuyer sur Ctrl+C pour arrêter l’analyse
4. Sélectionner les cibles par leur numéro
5. Wifite lance automatiquement les attaques
6. Rechercher les mots de passe des handshakes capturés
```

### Mode interactif

> **Précision :** la sortie ci-dessous est illustrative. Dans le code de sélection, Ctrl+C interrompt la détection puis donne accès au choix des cibles ; il ne signifie pas automatiquement « tout sélectionner » ([sélection officielle](https://github.com/derv82/wifite2/blob/master/wifite/util/scanner.py)).

```bash
# Démarrer wifite
sudo wifite

# La sortie affiche :
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Saisir les numéros des cibles ou Ctrl+C pour sélectionner toutes les cibles visibles
```

---

## 🎯 Sélection des cibles

### Sélectionner par numéro

```bash
# Après l’analyse, sélectionner les cibles par leur numéro
# Exemple : sélectionner les cibles 1 et 3
1, 3

# Sélectionner une plage
1-5

# Tout sélectionner
all
```

### Filtrer par ESSID

Un ESSID est le nom du réseau WiFi ; le BSSID désigne ici l’adresse MAC du point d’accès.

> **Précision :** les options ESSID et dictionnaire du parseur historique stockent une seule valeur ; les répéter ne garantit pas de cumuler plusieurs réseaux ou listes ([arguments](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

```bash
# Cibler un nom de réseau précis
sudo wifite --essid "TargetNetwork"

# Plusieurs ESSID
sudo wifite --essid "Network1" --essid "Network2"
```

### Filtrer par BSSID

```bash
# Cibler une adresse MAC précise
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filtrer par protection

```bash
# Réseaux WPA uniquement
sudo wifite --wpa

# Réseaux WEP uniquement
sudo wifite --wep

# Réseaux avec WPS activé uniquement
sudo wifite --wps
```

### Filtrer par canal

> **Précision :** le `--5ghz` historique ajoute les canaux 5 GHz sans garantir leur sélection exclusive ; l’option 2,4 GHz dépend de la branche ([arguments](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

```bash
# Canal précis
sudo wifite -c 6

# 5 GHz uniquement
sudo wifite --5ghz

# 2,4 GHz uniquement
sudo wifite --2ghz
```

### Filtrer par signal

```bash
# Puissance minimale (dBm : plus la valeur est proche de 0, plus le signal est fort)
sudo wifite --power -50
```

---

<a id="-options-dattaque"></a>
## ⚔️ Options d’attaque

### Options WPA/WPA2

```bash
# Définir le délai de désauthentification
sudo wifite --wpa-deauth-timeout 30

# Nombre de désauthentifications à envoyer
sudo wifite --num-deauths 5

# Ignorer la capture du handshake, PMKID uniquement
sudo wifite --pmkid
```

### Options de désauthentification

La désauthentification (*deauthentication*, abrégée *deauth*) vise à interrompre l’association d’un client au réseau.

> **Précision :** la configuration historique ignore une valeur nulle pour le nombre de désauthentifications ; `--num-deauths 0` ne garantit donc pas une émission continue ([code officiel](https://github.com/derv82/wifite2/blob/master/wifite/config.py)).

```bash
# Nombre de paquets de désauthentification
sudo wifite --num-deauths 10

# Désauthentification continue
sudo wifite --num-deauths 0
```

### Ordre des attaques

```bash
# Ignorer les attaques WPA
sudo wifite --no-wpa

# Ignorer les attaques WEP
sudo wifite --no-wep

# Ignorer les attaques WPS
sudo wifite --no-wps

# Ignorer les attaques PMKID
sudo wifite --no-pmkid
```

### Arrêter NetworkManager

```bash
# Arrêter automatiquement les processus qui interfèrent
sudo wifite --kill

# Ne pas arrêter les processus
sudo wifite --no-kill
```

---

## 🔓 Attaques WPS

### Activer WPS

```bash
# N’attaquer que les réseaux où WPS est activé
sudo wifite --wps

# Mode WPS uniquement
sudo wifite --wps-only
```

### Outils WPS

```bash
# Utiliser Reaver (par défaut)
sudo wifite --wps --reaver

# Utiliser Bully
sudo wifite --wps --bully
```

### Attaque Pixie-Dust

```bash
# Activer Pixie-Dust (attaque WPS hors ligne)
sudo wifite --wps --pixie

# Délai d’attente Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

### Options WPS

> **Correction :** `--wps-time` limite ici l’attente globale de Pixie-Dust, pas chaque essai de PIN. `--wps-only` inclut aussi Pixie-Dust, pas uniquement la force brute ([arguments](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

```bash
# Délai WPS pour chaque tentative de code PIN
sudo wifite --wps-time 120

# Nombre maximal de nouvelles tentatives WPS
sudo wifite --wps-fails 10

# Délai global WPS
sudo wifite --wps-timeout 300
```

---

## 🔐 Attaques PMKID

### Qu’est-ce qu’un PMKID ?

L’attaque PMKID (*Pairwise Master Key Identifier*, identifiant de clé maîtresse par paire) :

- ✅ Capture directement une PMK depuis le point d’accès, selon la formulation de l’original
- ✅ Ne nécessite pas de client déjà connecté
- ✅ Ne nécessite pas de désauthentification
- ✅ Fonctionne sur la plupart des réseaux WPA2, selon l’original

> **Correction :** le PMKID est un identifiant calculé à partir de la PMK, pas la PMK elle-même. Le point d’accès doit prendre en charge l’échange concerné ; la portée « la plupart des réseaux » n’est pas garantie. Sans client signifie sans client ordinaire déjà connecté, pas sans communication radio avec le point d’accès ([explication de Hashcat](https://hashcat.net/forum/thread-7717.html)).

### Options PMKID

```bash
# Attaques PMKID uniquement (sans handshake)
sudo wifite --pmkid

# Délai d’attente PMKID
sudo wifite --pmkid-timeout 120
```

### Fonctionnement du PMKID

```
1. Envoyer une demande d’association au point d’accès
2. Le point d’accès répond avec un PMKID dans une trame EAPOL
3. Capturer le PMKID
4. Rechercher le mot de passe hors ligne avec hashcat
```

---

<a id="-options-avancées"></a>
## ⚙️ Options avancées

### Options d’interface

```bash
# Préciser l’interface
sudo wifite -i wlan0

# Préciser l’interface en mode moniteur
sudo wifite -i wlan0mon
```

### Options de sortie

```bash
# Sortie détaillée
sudo wifite -v
sudo wifite --verbose

# Sortie très détaillée
sudo wifite -vv

# Mode silencieux
sudo wifite --quiet
```

### Options de temporisation

```bash
# Durée d’analyse (secondes)
sudo wifite --scan-time 30

# Délai global pour chaque cible
sudo wifite --attack-timeout 300
```

### Mots de passe retrouvés

> **Précision :** l’arborescence et les chemins sont schématiques. La configuration historique utilise `cracked.txt` et un dossier de handshakes `hs` ; `--cracked` sert à afficher des résultats, pas à choisir un fichier de sortie ([configuration](https://github.com/derv82/wifite2/blob/master/wifite/config.py), [arguments](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

```bash
# Les mots de passe retrouvés sont enregistrés dans :
# ~/.config/wifite/cracked.txt

# Afficher les réseaux dont le mot de passe a été retrouvé
cat ~/.config/wifite/cracked.txt

# Ou préciser un fichier personnalisé
sudo wifite --cracked cracked_passwords.txt
```

### Options d’omission

```bash
# Ignorer la recherche du mot de passe (capture uniquement)
sudo wifite --skip-crack

# Ignorer les cibles dont le mot de passe a déjà été retrouvé
sudo wifite --skip-cracked
```

### Adresse MAC aléatoire

```bash
# Utiliser une adresse MAC aléatoire
sudo wifite --random-mac

# Ne pas modifier l’adresse MAC
sudo wifite --no-random-mac
```

---

## 🔓 Options de recherche du mot de passe

### Attaque par dictionnaire

Une *wordlist* est une liste de mots de passe candidats testés lors de cette recherche.

```bash
# Préciser la liste de mots de passe
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Plusieurs listes de mots de passe
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Intégration de Hashcat

```bash
# Utiliser hashcat pour rechercher le mot de passe
sudo wifite --hashcat

# Emplacement de hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Options Aircrack-ng

```bash
# Utiliser aircrack-ng (par défaut)
sudo wifite --aircrack

# Chemin d’aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Mode de recherche uniquement

> **Précision :** le `--crack` historique affiche des commandes pour les captures existantes ; le mode avec fichier positionnel ci-dessous n’est pas garanti ([code officiel](https://github.com/derv82/wifite2/blob/master/wifite/args.py)).

```bash
# Rechercher les mots de passe à partir de handshakes existants
sudo wifite --crack

# Préciser le fichier de capture
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Exemples

### Audit WiFi de base

```bash
# Analyser et attaquer tous les réseaux
sudo wifite

# Attaquer avec une liste de mots de passe personnalisée
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Cibler un réseau précis

```bash
# Cibler par nom
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Cibler par adresse MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Attaque WPA2 uniquement

```bash
# Réseaux WPA2 avec PMKID uniquement
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### Attaque WPS

```bash
# WPS avec Pixie-Dust
sudo wifite --wps --pixie

# WPS par force brute uniquement
sudo wifite --wps-only --bully
```

### Signal fort uniquement

```bash
# N’attaquer que les réseaux dont le signal est fort
sudo wifite --power -50 --wpa
```

### Réseaux 5 GHz

```bash
# Attaquer uniquement la bande 5 GHz
sudo wifite --5ghz --wpa
```

### Audit rapide sans recherche de mot de passe

```bash
# Capturer uniquement les handshakes, rechercher les mots de passe plus tard
sudo wifite --skip-crack
```

### Reprendre une attaque

> **Précision :** sauvegarder des captures ou des résultats ne garantit pas la reprise exacte de toute la session à son interruption. Les comportements de réutilisation dépendent des versions ; les commandes ci-dessous sont conservées comme dans l’original ([configuration](https://github.com/derv82/wifite2/blob/master/wifite/config.py)).

```bash
# Wifite enregistre automatiquement la progression
# Relancer simplement pour reprendre
sudo wifite

# Ignorer les mots de passe déjà retrouvés
sudo wifite --skip-cracked
```

### Mode détaillé

```bash
# Afficher tous les détails
sudo wifite -vv --dict rockyou.txt
```

### Mode discret

> **Précision :** une sortie silencieuse ou peu de paquets ne rend pas l’activité radio invisible ; le titre et la syntaxe restent ceux de l’original.

```bash
# Peu de désauthentifications, sortie silencieuse
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Référence rapide

### Commandes essentielles

| Objectif | Commande |
|---------|---------|
| Analyse de base | `sudo wifite` |
| Cibler par nom | `sudo wifite --essid "Name"` |
| Cibler par adresse MAC | `sudo wifite --bssid MAC` |
| WPA uniquement | `sudo wifite --wpa` |
| WPS uniquement | `sudo wifite --wps-only` |
| Attaque PMKID | `sudo wifite --pmkid` |
| Avec une liste de mots de passe | `sudo wifite --dict wordlist.txt` |
| Ignorer la recherche du mot de passe | `sudo wifite --skip-crack` |

### Types d’attaques

| Attaque | Option | Cible |
|--------|------|--------|
| Handshake WPA | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS activé |
| WPS par force brute | `--wps-only` | WPS activé |
| WEP | `--wep` | WEP (obsolète) |

### Listes de mots de passe courantes

| Chemin | Description |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Mots de passe très courants |
| `/usr/share/wordlists/fasttrack.txt` | Vérification rapide |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Listes propres au WiFi |

### Fichiers capturés

```
~/.config/wifite/
├── cracked.txt          # Mots de passe retrouvés
├── hs/                  # Captures de handshakes
│   └── handshake_*.cap
└── pmkid/               # Captures de PMKID
    └── pmkid_*.22000
```

### Tableau des options

| Option | Description |
|------|-------------|
| `-i` | Interface |
| `-c` | Canal |
| `-v` | Sortie détaillée |
| `--wpa` | WPA/WPA2 uniquement |
| `--wep` | WEP uniquement |
| `--wps` | WPS activé |
| `--pmkid` | Attaque PMKID |
| `--dict` | Liste de mots de passe |
| `--kill` | Arrêter les processus |
| `--power` | Signal minimal |
| `--5ghz` | Bande 5 GHz |
| `--2ghz` | Bande 2,4 GHz |

### Dépannage

```bash
# Interface introuvable
sudo airmon-ng check kill
sudo wifite -i wlan0

# Aucun réseau trouvé
# - Vérifier que l’adaptateur prend en charge le mode moniteur
# - Essayer d’autres bandes : --5ghz ou --2ghz
# - Se rapprocher des cibles

# Handshake non capturé
# - Augmenter les désauthentifications : --num-deauths 10
# - Attendre davantage : --wpa-deauth-timeout 60
# - Essayer d’autres clients

# Échec de la recherche du mot de passe
# - Utiliser une liste de mots de passe plus adaptée
# - Essayer hashcat avec le GPU : --hashcat
```

---

## 🔄 Wifite et Aircrack-ng manuel

| Tâche | Wifite | Suite Aircrack-ng |
|------|--------|-------------------|
| Mode moniteur | Automatique | `airmon-ng start wlan0` |
| Analyse | Automatique | `airodump-ng wlan0mon` |
| Cible | Interactif | Filtrage manuel |
| Désauthentification | Automatique | `aireplay-ng -0 ...` |
| Capture | Automatique | `airodump-ng -w ...` |
| Recherche du mot de passe | Automatique | `aircrack-ng -w ...` |
| **Effort** | **Simple** | Manuel |

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

```
⚠️ ATTENTION : utiliser Wifite uniquement sur les réseaux dont vous êtes
PROPRIÉTAIRE ou pour lesquels vous disposez d’une AUTORISATION ÉCRITE EXPLICITE.

✅ Tests d’intrusion autorisés
✅ Audits de sécurité avec autorisation
✅ Apprentissage dans un environnement de laboratoire

❌ L’accès non autorisé à un réseau est ILLÉGAL
❌ Ne jamais utiliser cet outil à des fins malveillantes
```

---

## 📚 Ressources

- [Wifite2 sur GitHub](https://github.com/derv82/wifite2)
- [Wiki de Wifite](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Aide-mémoire associés
- [Aircrack-ng](../../Aircrack-ng/translations/README.fr.md)
- [Hashcat](../../Hashcat/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)

---

<p align="center">
  <b>📶 Audit WiFi automatisé !</b><br>
  <i>Wifite — sélectionner, lancer, auditer avec autorisation !</i>
</p>
