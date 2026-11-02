# 🦈 Wireshark — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           The Network Protocol Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 Un analyseur de référence pour les protocoles réseau</b>
</p>

---

> **Filtres et versions :** filtres de capture BPF et filtres d’affichage sont deux langages distincts ; l’étiquette `bash` des exemples n’indique pas qu’il faut saisir ces expressions dans un shell. Les menus, raccourcis, noms de champs et droits de capture dépendent de la version et de la plateforme. Le groupe `wireshark` ne suffit que si les droits de capture de `dumpcap` ont aussi été configurés. Voir le [guide officiel](https://www.wireshark.org/docs/wsug_html/). Le dessin ASCII conserve son texte source.
>
> **Interprétation des exemples :** le filtre SYN **ou** ACK du document source sélectionne beaucoup de trafic TCP, pas uniquement les trois paquets de l’établissement d’une connexion. Un RST, un ARP gratuit ou une grande trame DNS ne prouve pas à lui seul une attaque ou une exfiltration ; les filtres sont des points de départ pour l’analyse. Le filtre `tls` ne désigne pas exclusivement HTTPS et la lecture du contenu chiffré peut nécessiter des secrets de session.
>
> **Version TLS :** `tls.record.version == 0x0303` correspond au champ de version de l’enregistrement, pas à une preuve suffisante d’une négociation TLS 1.2 : TLS 1.3 conserve aussi une valeur historique `0x0303` dans cette couche. Voir la [référence des champs TLS de Wireshark](https://www.wireshark.org/docs/dfref/t/tls.html) et la [spécification TLS 1.3](https://www.rfc-editor.org/rfc/rfc8446.html#section-5.1). Les filtres et commandes originaux sont conservés.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Vue d’ensemble de l’interface](#-vue-densemble-de-linterface)
- [Notions de base de la capture](#-notions-de-base-de-la-capture)
- [Filtres d’affichage](#-filtres-daffichage)
- [Filtres de capture](#-filtres-de-capture)
- [Analyse des protocoles](#-analyse-des-protocoles)
- [Suivi des flux](#-suivi-des-flux)
- [Statistiques et analyse](#-statistiques-et-analyse)
- [tshark (ligne de commande)](#-tshark-ligne-de-commande)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Raccourcis clavier](#-raccourcis-clavier)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Wireshark** est un analyseur de paquets libre et gratuit, utilisé pour le dépannage réseau, l’analyse, le développement logiciel et l’audit de sécurité.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Inspection approfondie** | Des centaines de protocoles pris en charge |
| **Capture en direct** | Capture des paquets en temps réel |
| **Analyse hors ligne** | Lire les fichiers de capture (pcap, pcapng) |
| **Filtres puissants** | Filtres d’affichage et de capture |
| **Multiplateforme** | Windows, Linux, macOS |
| **Interface graphique et ligne de commande** | Wireshark (interface graphique) et tshark (ligne de commande) |

### Cas d’usage

| Cas d’usage | Description |
| ---------- | ------------- |
| **Dépannage réseau** | Diagnostiquer les problèmes de connectivité |
| **Analyse de sécurité** | Repérer le trafic malveillant |
| **Développement de protocoles** | Tester de nouveaux protocoles |
| **Investigation numérique** | Analyser le trafic capturé |
| **Formation** | Apprendre le fonctionnement des réseaux |

---

## 📥 Installation

### Windows
```bash
# Télécharger depuis le site officiel
https://www.wireshark.org/download.html

# Ou utiliser Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (préinstallé)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Autoriser la capture sans compte root
sudo usermod -aG wireshark $USER
# Se déconnecter puis se reconnecter
```

### macOS
```bash
brew install --cask wireshark
```

### Vérifier l’installation
```bash
wireshark --version
tshark --version
```

---

<a id="-vue-densemble-de-linterface"></a>
## 🖥️ Vue d’ensemble de l’interface

### Zones de la fenêtre principale

| Zone | Description |
| ------ | ------------- |
| **Barre de menus** | File, Edit, View, Go, Capture, Analyze |
| **Barre d’outils principale** | Boutons d’accès rapide |
| **Barre de filtre** | Saisir les filtres d’affichage |
| **Liste des paquets** | Tous les paquets capturés |
| **Détails du paquet** | Couches de protocoles du paquet sélectionné |
| **Octets du paquet** | Données brutes en hexadécimal et ASCII |

### Colonnes de la liste des paquets (par défaut)

| Colonne | Description |
| -------- | ------------- |
| No. | Numéro du paquet |
| Time | Horodatage |
| Source | Adresse IP/MAC source |
| Destination | Adresse IP/MAC de destination |
| Protocol | Protocole de la couche la plus haute |
| Length | Taille du paquet en octets |
| Info | Informations propres au protocole |

---

## 📡 Notions de base de la capture

### Démarrer une capture

```
1. Sélectionner une interface sur l’écran d’accueil
2. Cliquer sur "Start Capture" (aileron de requin bleu)
3. Ou utiliser Capture → Start

Raccourci : double-cliquer sur le nom de l’interface
```

### Arrêter une capture

```
Cliquer sur le carré rouge de la barre d’outils
Ou utiliser Capture → Stop
Ou utiliser Ctrl+E
```

### Enregistrer une capture

```
File → Save As
Formats : pcapng (par défaut), pcap, etc.
```

### Ouvrir un fichier de capture

```
File → Open
Ou utiliser Ctrl+O
Ou lancer : wireshark file.pcap
```

---

## 🔍 Filtres d’affichage

### Syntaxe des filtres

Les filtres d’affichage utilisent une **syntaxe de comparaison des champs** :

```
field operator value
```

### Opérateurs de comparaison

| Opérateur | Description | Exemple |
| ---------- | ------------- | --------- |
| `==` ou `eq` | Égal à | `ip.addr == 192.168.1.1` |
| `!=` ou `ne` | Différent de | `ip.addr != 10.0.0.1` |
| `>` ou `gt` | Supérieur à | `frame.len > 100` |
| `<` ou `lt` | Inférieur à | `tcp.port < 1024` |
| `>=` ou `ge` | Supérieur ou égal à | `frame.len >= 64` |
| `<=` ou `le` | Inférieur ou égal à | `tcp.port <= 1024` |
| `contains` | Contient une chaîne | `http contains "password"` |
| `matches` | Correspondance avec une expression régulière | `http.host matches ".*\.com"` |

### Opérateurs logiques

| Opérateur | Description | Exemple |
| ---------- | ------------- | --------- |
| `and` ou `&&` | ET logique | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` ou `\|\|` | OU logique | `tcp.port == 80 or tcp.port == 443` |
| `not` ou `!` | NON logique | `not arp` |
| `()` | Regroupement | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### Filtres IP

```bash
# Adresse IP précise (source ou destination)
ip.addr == 192.168.1.1

# Adresse IP source uniquement
ip.src == 192.168.1.1

# Adresse IP de destination uniquement
ip.dst == 192.168.1.1

# Plage IP (sous-réseau)
ip.addr == 192.168.1.0/24

# Exclure une adresse IP
ip.addr != 192.168.1.1

# Entre deux hôtes
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Filtres de ports

```bash
# Port précis (source ou destination)
tcp.port == 80
udp.port == 53

# Port source uniquement
tcp.srcport == 443

# Port de destination uniquement
tcp.dstport == 22

# Plage de ports
tcp.port >= 1 and tcp.port <= 1024

# Plusieurs ports
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Filtres de protocoles

```bash
# Par nom de protocole
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# Propre à HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Propre à DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # Enregistrement A
dns.flags.response == 1    # Réponses DNS

# Propre à TCP
tcp.flags.syn == 1         # Paquets SYN
tcp.flags.fin == 1         # Paquets FIN
tcp.flags.reset == 1       # Paquets RST
tcp.analysis.retransmission  # Retransmissions
tcp.stream == 5            # Flux précis
```

### Filtres de contenu

```bash
# Contient une chaîne
http contains "password"
tcp contains "login"
frame contains "secret"

# Contenu HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Correspondance sans distinction de casse
http.host matches "(?i)example"
```

### Exemples de filtres courants

```bash
# Trafic HTTP uniquement
http

# Trafic HTTPS
tls

# Requêtes DNS
dns.flags.response == 0

# Connexions interrompues ou refusées (RST)
tcp.flags.reset == 1

# Paquets volumineux
frame.len > 1000

# Adresse MAC précise
eth.addr == aa:bb:cc:dd:ee:ff

# Trafic de diffusion
eth.dst == ff:ff:ff:ff:ff:ff

# Trafic hors de la plage locale indiquée
!(ip.addr == 192.168.0.0/16)

# Établissement TCP en trois étapes (libellé source ; voir la précision)
tcp.flags.syn == 1 or tcp.flags.ack == 1

# Requêtes HTTP POST contenant des données
http.request.method == "POST" and http.file_data
```

---

## 🎣 Filtres de capture

### Syntaxe BPF (Berkeley Packet Filter)

Les filtres de capture utilisent la **syntaxe BPF**, différente de celle des filtres d’affichage.

### Syntaxe de base

```bash
# Hôte
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Réseau
net 192.168.1.0/24
src net 10.0.0.0/8

# Port
port 80
src port 443
dst port 22
portrange 1-1024

# Protocole
tcp
udp
icmp
arp
```

### Filtres combinés

```bash
# Hôte et port
host 192.168.1.1 and port 80

# Conversation précise
host 192.168.1.1 and host 192.168.1.2

# Exclure du trafic
not port 22
not host 10.0.0.1

# Filtre complexe
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Filtres de capture courants

| Utilité | Filtre |
| --------- | -------- |
| Hôte précis | `host 192.168.1.1` |
| Trafic web | `port 80 or port 443` |
| DNS uniquement | `port 53` |
| SSH uniquement | `port 22` |
| Exclure les diffusions (broadcast) | `not broadcast` |
| Sous-réseau précis | `net 192.168.1.0/24` |

---

## 🔬 Analyse des protocoles

### Analyse TCP

```bash
# Indicateurs TCP
tcp.flags.syn == 1 and tcp.flags.ack == 0    # SYN uniquement
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# Problèmes TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# Flux TCP
tcp.stream == 0
```

### Analyse HTTP

```bash
# Requêtes HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# Réponses HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Erreurs

# Contenu HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### Analyse DNS

```bash
# Requêtes DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # Enregistrement A
dns.qry.type == 28     # Enregistrement AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# Réponses DNS
dns.flags.response == 1
dns.flags.rcode != 0   # Erreurs
```

### Analyse TLS/SSL

```bash
# Trafic TLS
tls

# Négociation TLS
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Certificats
tls.handshake.certificate

# Version TLS
tls.record.version == 0x0303  # TLS 1.2
```

### Analyse ARP

```bash
# Tout le trafic ARP
arp

# Requêtes ARP
arp.opcode == 1

# Réponses ARP
arp.opcode == 2

# ARP gratuit (usurpation possible)
arp.isgratuitous == 1
```

---

## 🔗 Suivi des flux

### Suivre un flux TCP

```
Faire un clic droit sur un paquet → Follow → TCP Stream

Ou utiliser Analyze → Follow → TCP Stream
```

### Suivre un flux HTTP

```
Faire un clic droit sur un paquet HTTP → Follow → HTTP Stream
```

### Suivre un flux UDP

```
Faire un clic droit sur un paquet UDP → Follow → UDP Stream
```

### Fenêtre de suivi du flux

- Affiche l’ensemble de la conversation
- Différencie le client et le serveur par des couleurs
- Permet d’enregistrer le contenu sous forme de texte ou de données brutes
- Propose des boutons de filtrage selon le sens du trafic

---

## 📊 Statistiques et analyse

### Hiérarchie des protocoles

```
Statistics → Protocol Hierarchy

Affiche la répartition des protocoles capturés
```

### Conversations

```
Statistics → Conversations

Afficher toutes les conversations par :
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Points de terminaison

```
Statistics → Endpoints

Lister tous les points de terminaison distincts
```

### Graphique d’entrées/sorties

```
Statistics → IO Graph

Visualiser l’évolution du trafic dans le temps
```

### Informations d’expertise

```
Analyze → Expert Information

Affiche les avertissements, erreurs et remarques :
- Retransmissions TCP
- Paquets mal formés
- Erreurs
```

### Statistiques HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (ligne de commande)

### Utilisation de base

```bash
# Capturer les paquets
tshark -i eth0

# Lire un fichier de capture
tshark -r capture.pcap

# Écrire dans un fichier
tshark -i eth0 -w output.pcap
```

### Appliquer des filtres

```bash
# Filtre de capture
tshark -i eth0 -f "port 80"

# Filtre d’affichage
tshark -r capture.pcap -Y "http"
```

### Options de sortie

```bash
# Afficher certains champs
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Sortie JSON
tshark -r capture.pcap -T json

# Afficher les statistiques
tshark -r capture.pcap -q -z io,stat,1
```

### Exemples tshark courants

```bash
# Capturer et afficher les requêtes HTTP en direct
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Extraire les requêtes DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Compter les paquets par adresse IP
tshark -r capture.pcap -q -z ip_hosts,tree

# Statistiques HTTP
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Exemples pratiques

### Exemple 1 : capturer le trafic HTTP
```bash
# Filtre de capture : port 80
# Filtre d’affichage : http
# Suivre le flux HTTP pour voir les requêtes et réponses complètes
```

### Exemple 2 : rechercher des identifiants
```bash
# Filtre d’affichage
http.request.method == "POST" and http contains "password"

# Ou pour FTP
ftp contains "PASS"
```

### Exemple 3 : repérer une analyse des ports
```bash
# Plusieurs SYN vers différents ports
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Rechercher de nombreuses destinations depuis une même source
Statistics → Conversations → TCP
```

### Exemple 4 : exfiltration par DNS
```bash
# Requêtes DNS volumineuses
dns and frame.len > 512

# Enregistrements TXT inhabituels
dns.qry.type == 16
```

### Exemple 5 : extraire des fichiers
```
File → Export Objects → HTTP

Sélectionner les fichiers à exporter
```

### Exemple 6 : analyser la négociation TLS
```bash
tls.handshake

# Examiner Client Hello pour le SNI
tls.handshake.type == 1
```

### Exemple 7 : rechercher des mots de passe en clair
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Référence rapide

### Filtres d’affichage essentiels

| Utilité | Filtre |
| --------- | -------- |
| Trafic HTTP | `http` |
| Trafic DNS | `dns` |
| Adresse IP précise | `ip.addr == 192.168.1.1` |
| Port précis | `tcp.port == 80` |
| Problèmes TCP | `tcp.analysis.flags` |
| Erreurs HTTP | `http.response.code >= 400` |
| Contient une chaîne | `frame contains "text"` |
| Exclure un protocole | `not arp` |

### Filtres de capture courants

| Utilité | Filtre |
| --------- | -------- |
| Hôte précis | `host 192.168.1.1` |
| Trafic web | `port 80 or port 443` |
| DNS uniquement | `port 53` |
| Exclure les diffusions (broadcast) | `not broadcast` |

---

<a id="-raccourcis-clavier"></a>
## ⌨️ Raccourcis clavier

| Raccourci | Action |
| ---------- | -------- |
| `Ctrl+E` | Démarrer ou arrêter la capture |
| `Ctrl+K` | Options de capture |
| `Ctrl+O` | Ouvrir un fichier |
| `Ctrl+S` | Enregistrer le fichier |
| `Ctrl+F` | Rechercher un paquet |
| `Ctrl+G` | Aller à un paquet |
| `Ctrl+→` | Paquet suivant dans la conversation |
| `Ctrl+←` | Paquet précédent dans la conversation |
| `Ctrl+.` | Paquet marqué suivant |
| `Ctrl+M` | Marquer ou retirer la marque d’un paquet |
| `Ctrl+Shift+M` | Marquer tous les paquets affichés |
| `Space` | Développer ou réduire l’arbre des protocoles |
| `Tab` | Panneau suivant |

---

## 💡 Conseils et bonnes pratiques

### Conseils de capture

1. **Utiliser les filtres de capture**
   ```
   Réduire la taille du fichier en filtrant dès la capture
   ```

2. **Tampon circulaire pour les longues captures**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Capturer à distance**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Conseils d’analyse

1. **Règles de coloration**
   ```
   View → Coloring Rules
   Personnaliser les couleurs selon les protocoles
   ```

2. **Affichage du temps**
   ```
   View → Time Display Format
   Essayer "Seconds Since Beginning of Capture"
   ```

3. **Résolution des noms**
   ```
   View → Name Resolution
   Activer ou désactiver selon les besoins
   ```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** la capture de paquets réseau doit être réservée à des **usages autorisés**.
> 
> - ✅ Capturer sur les réseaux dont vous êtes propriétaire
> - ✅ Effectuer des tests de sécurité avec autorisation
> - ✅ Dépanner des systèmes sur lesquels vous êtes autorisé à intervenir
> - ❌ Ne jamais capturer sur des réseaux sans autorisation
> - ❌ La capture du trafic d’autrui peut être illégale
> 
> **La surveillance non autorisée des réseaux est illégale dans la plupart des juridictions.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de Wireshark](https://www.wireshark.org/)
- [Guide de l’utilisateur Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Référence des filtres d’affichage](https://www.wireshark.org/docs/dfref/)
- [Wiki Wireshark](https://wiki.wireshark.org/)

### Ressources pour apprendre
- [Tutoriel Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Exemples de captures](https://wiki.wireshark.org/SampleCaptures)

### Fiches connexes
- [Nmap](../../Nmap/translations/README.fr.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)
- [Hydra](../../Hydra/translations/README.fr.md)

---

<p align="center">
  <b>🦈 Maîtrisez l’analyse réseau !</b><br>
  <i>Explorez le trafic qui circule sur le réseau</i>
</p>
