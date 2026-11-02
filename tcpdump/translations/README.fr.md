# 📡 tcpdump - fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Command-Line Packet Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 L’analyseur de paquets classique en ligne de commande</b>
</p>

---

> **Précisions de lecture :** les commandes et filtres sont conservés. `-w` écrit des paquets bruts dans le fichier indiqué ; le pipeline `-w capture.pcap | tee` ne produit donc pas en même temps leur affichage décodé. `-Z` abandonne les privilèges après l’ouverture de la source de capture, et non après la fin de la capture. `-s0` utilise la longueur maximale prévue par la version ; une limite fixe de 96 octets ne garantit pas de ne conserver que les en-têtes. Voir le [manuel officiel](https://www.tcpdump.org/manpages/tcpdump.1.html).
>
> **Limites des exemples :** dans l’exemple « trafic entrant », les apostrophes simples empêchent l’expansion de `$(hostname -I | cut -d" " -f1)` par le shell : cette commande ne fournit pas automatiquement l’adresse locale au filtre. Les tests HTTP fondés sur `ip[...]` visent IPv4 et des octets présents dans les paquets capturés ; ils ne reconstituent pas tous les flux HTTP et ne déchiffrent pas HTTPS. Le filtre DNS UDP ne couvre pas DNS sur TCP ni les transports chiffrés. `wc -l` compte des lignes de sortie, pas universellement les paquets. Le texte du dessin ASCII est conservé.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Options courantes](#-options-courantes)
- [Filtres BPF](#-filtres-bpf)
- [Filtres par protocole](#-filtres-par-protocole)
- [Options de sortie](#-options-de-sortie)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**tcpdump** est un analyseur de paquets en ligne de commande disponible sur la plupart des systèmes de type Unix. Il utilise la bibliothèque libpcap et la syntaxe Berkeley Packet Filter (BPF).

### Principales fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Léger** | Sans interface graphique, ressources limitées |
| **Filtres BPF** | Filtrage puissant des paquets |
| **Compatible pcap** | Lire et écrire des fichiers pcap |
| **Utilisable dans des scripts** | Adapté à l’automatisation |
| **Largement disponible** | Présent sur les systèmes Unix selon le document source |

### tcpdump et Wireshark

| Fonctionnalité | tcpdump | Wireshark |
|---------|---------|-----------|
| **Interface** | Ligne de commande (CLI) | Interface graphique (GUI) |
| **Ressources** | Faibles | Plus importantes |
| **À distance** | Via SSH | Configuration nécessaire |
| **Scripts** | Simples | Plus complexes selon la source |
| **Analyse** | Élémentaire | Avancée |

---

## 📥 Installation

### Linux (préinstallé sur de nombreux systèmes)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (préinstallé)
```bash
tcpdump --version
```

### Vérifier l’installation
```bash
tcpdump --version
tcpdump -D              # Lister les interfaces
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
tcpdump [options] [filter expression]
```

### Commandes de base
```bash
# Capturer sur l’interface par défaut
sudo tcpdump

# Capturer sur une interface précise
sudo tcpdump -i eth0

# Limiter le nombre de paquets capturés
sudo tcpdump -c 100

# Capturer avec une sortie détaillée
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-options-courantes"></a>
## ⚙️ Options courantes

### Options d’affichage

| Option | Description |
|--------|-------------|
| `-i` | Interface sur laquelle capturer |
| `-c` | Nombre de paquets à capturer |
| `-n` | Ne pas résoudre les noms d’hôtes |
| `-nn` | Ne résoudre ni les noms d’hôtes ni les noms de services des ports |
| `-v` | Sortie détaillée |
| `-vv` | Sortie plus détaillée |
| `-vvv` | Niveau de détail maximal |
| `-q` | Sortie réduite |
| `-t` | Ne pas afficher l’horodatage |
| `-tt` | Afficher l’horodatage Unix |
| `-ttt` | Afficher l’écart de temps |
| `-tttt` | Afficher la date et l’heure |

### Options de capture

| Option | Description |
|--------|-------------|
| `-w` | Écrire dans un fichier pcap |
| `-r` | Lire un fichier pcap |
| `-s` | Longueur capturée par paquet, en octets (snaplen) |
| `-s0` | Utiliser la longueur maximale prévue par la version |
| `-D` | Lister les interfaces disponibles |

### Options de sortie

| Option | Description |
|--------|-------------|
| `-A` | Afficher le contenu ASCII |
| `-X` | Afficher l’hexadécimal et l’ASCII |
| `-XX` | Afficher l’hexadécimal et l’ASCII avec l’en-tête de liaison |
| `-e` | Afficher l’en-tête de la couche liaison |
| `-l` | Tamponner la sortie ligne par ligne |

### Combinaisons courantes
```bash
# Sans résolution de noms, sortie détaillée, longueur maximale
sudo tcpdump -nnvvs0 -i eth0

# Enregistrer dans un fichier
sudo tcpdump -i eth0 -w capture.pcap

# Lire un fichier
tcpdump -r capture.pcap

# Sortie ASCII
sudo tcpdump -A -i eth0

# Hexadécimal et ASCII
sudo tcpdump -X -i eth0
```

---

## 🎣 Filtres BPF

### Filtres par hôte

```bash
# Trafic depuis ou vers un hôte précis
tcpdump host 192.168.1.1

# Hôte source uniquement
tcpdump src host 192.168.1.1

# Hôte de destination uniquement
tcpdump dst host 192.168.1.1

# Plusieurs hôtes
tcpdump host 192.168.1.1 or host 192.168.1.2

# Exclure un hôte
tcpdump not host 192.168.1.1

# Réseau ou sous-réseau
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Filtres par port

```bash
# Port précis
tcpdump port 80
tcpdump port 443

# Port source
tcpdump src port 443

# Port de destination
tcpdump dst port 22

# Plage de ports
tcpdump portrange 1-1024

# Plusieurs ports
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Filtres par protocole

```bash
# Par protocole
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protocole et port
tcpdump tcp port 80
tcpdump udp port 53
```

### Opérateurs logiques

| Opérateur | Autre notation | Description |
|----------|-------------|-------------|
| `and` | `&&` | ET logique |
| `or` | `\|\|` | OU logique |
| `not` | `!` | NON logique |
| `()` | | Regroupement |

### Filtres combinés

```bash
# Hôte ET port
tcpdump host 192.168.1.1 and port 80

# Hôte ET protocole
tcpdump host 192.168.1.1 and tcp

# Filtre composé
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Exclure certains trafics
tcpdump not port 22 and not port 53

# Trafic entre deux hôtes
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Filtres par protocole

### Indicateurs TCP

```bash
# Paquets SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# Paquets SYN-ACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# Paquets FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# Paquets RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# Paquets PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# ACK seul
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Référence des indicateurs TCP

| Indicateur | Hex | Déc | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### Trafic HTTP

```bash
# Requêtes HTTP (port 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# Requêtes GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# Requêtes POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Capture HTTP simple
tcpdump -A -s0 'tcp port 80'
```

### Trafic DNS

```bash
# Tout le trafic DNS
tcpdump port 53
tcpdump udp port 53

# Requêtes DNS uniquement
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Réponses DNS uniquement
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### Trafic ICMP

```bash
# Tout le trafic ICMP
tcpdump icmp

# Requête d’écho ICMP (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# Réponse d’écho ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# Message ICMP de destination inaccessible
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### Trafic ARP

```bash
# Tout le trafic ARP
tcpdump arp

# Requêtes ARP
tcpdump 'arp[6:2] == 1'

# Réponses ARP
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Options de sortie

### Écrire dans un fichier

```bash
# Écrire dans un fichier pcap
tcpdump -w capture.pcap

# Écrire avec rotation (100 fichiers, 1 Mo chacun)
tcpdump -w capture.pcap -W 100 -C 1

# Inclure l’horodatage dans le nom du fichier
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Capturer et afficher (libellé source ; voir la précision)
tcpdump -w capture.pcap | tee
```

### Lire un fichier

```bash
# Lire un fichier pcap
tcpdump -r capture.pcap

# Lire avec des filtres
tcpdump -r capture.pcap 'host 192.168.1.1'

# Compter les paquets (via les lignes affichées dans cet exemple)
tcpdump -r capture.pcap | wc -l

# Lire avec une sortie détaillée
tcpdump -nnvvXSs0 -r capture.pcap
```

### Formats d’affichage

```bash
# Contenu ASCII
tcpdump -A -i eth0

# Hexadécimal et ASCII
tcpdump -X -i eth0

# Hexadécimal uniquement
tcpdump -x -i eth0

# Avec les en-têtes Ethernet
tcpdump -XX -i eth0

# Afficher les numéros de séquence absolus
tcpdump -S -i eth0
```

---

## 🎬 Exemples pratiques

### Exemple 1 : capturer le trafic web
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Exemple 2 : capturer les requêtes DNS
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Exemple 3 : capturer le trafic vers un hôte précis
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Exemple 4 : repérer les connexions SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Exemple 5 : capturer ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Exemple 6 : repérer une analyse de ports (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Exemple 7 : capturer uniquement le trafic entrant
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Exemple 8 : observer une conversation précise
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Exemple 9 : capturer des identifiants FTP
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Exemple 10 : enregistrer pour analyser avec Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Ouvrir ensuite capture.pcap dans Wireshark
```

### Exemple 11 : capture distante via SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Exemple 12 : capturer en excluant SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
|------|---------|
| Lister les interfaces | `tcpdump -D` |
| Capture élémentaire | `tcpdump -i eth0` |
| Sans résolution DNS | `tcpdump -nn` |
| Capturer N paquets | `tcpdump -c 100` |
| Écrire dans un fichier | `tcpdump -w file.pcap` |
| Lire un fichier | `tcpdump -r file.pcap` |
| Sortie détaillée | `tcpdump -v` |
| Sortie ASCII | `tcpdump -A` |
| Sortie hexadécimale | `tcpdump -X` |
| Paquet complet | `tcpdump -s0` |

### Filtres courants

| Filtre | Description |
|--------|-------------|
| `host 1.2.3.4` | Trafic depuis ou vers l’hôte |
| `port 80` | Trafic sur le port 80 |
| `tcp` | Trafic TCP uniquement |
| `udp` | Trafic UDP uniquement |
| `icmp` | Trafic ICMP uniquement |
| `net 192.168.1.0/24` | Trafic depuis ou vers le sous-réseau |
| `src host 1.2.3.4` | L’hôte est la source |
| `dst port 443` | Port de destination 443 |

### Combinaisons de filtres

| Exemple | Description |
|---------|-------------|
| `host A and port 80` | Hôte A, port 80 |
| `port 80 or port 443` | Port 80 ou 443 |
| `not port 22` | Exclure SSH |
| `tcp and port 80` | Port TCP 80 |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performances

```bash
# Ne pas résoudre les noms (plus rapide)
tcpdump -nn

# Limiter la longueur capturée par paquet
tcpdump -s 96   # En-têtes uniquement selon le document source

# Utiliser des filtres BPF pour réduire la charge
tcpdump 'host 192.168.1.1'  # Filtrer au niveau du noyau
```

### Conseils de sécurité

```bash
# Exécuter sans être root si la configuration le permet
# Ou limiter la durée de capture

# Abandonner les privilèges après l’ouverture de la capture (voir la précision)
tcpdump -Z user
```

### Alias utiles

```bash
# Ajouter à ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** la capture de paquets doit être réservée aux **usages autorisés**.
> 
> - ✅ Capturer sur les réseaux dont vous êtes propriétaire
> - ✅ Effectuer des tests de sécurité avec autorisation
> - ✅ Dépanner des systèmes avec autorisation
> - ❌ Ne jamais capturer sur des réseaux sans autorisation
> - ❌ Capturer le trafic d’autres personnes peut être illégal
> 
> **La surveillance réseau non autorisée est illégale.**

---

## 📚 Ressources

### Ressources officielles
- [Manuel tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Site officiel de tcpdump](https://www.tcpdump.org/)
- [Syntaxe des filtres BPF](https://biot.com/capstats/bpf.html)

### Fiches connexes
- [Wireshark](../../Wireshark/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)
- [Metasploit](../../Metasploit/translations/README.fr.md)

---

<p align="center">
  <b>📡 Maîtrisez l’analyse des paquets en ligne de commande !</b><br>
  <i>L’alternative légère à Wireshark</i>
</p>
