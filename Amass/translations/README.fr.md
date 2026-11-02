# 🌐 Amass — cartographie de la surface d’attaque

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Versions :** la fiche mêle des syntaxes historiques et une installation v4. Sous-commandes, sorties JSON, schéma YAML, sources et offres gratuites varient ; le mode `dns` et ses options ne figurent pas dans le [guide officiel consulté](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md). Les commandes et valeurs de configuration restent inchangées, avec le texte ASCII source.
>
> **Précisions historiques :** dans le [guide officiel v3.23.3](https://github.com/owasp-amass/amass/blob/v3.23.3/doc/user_guide.md), `-r` indique des résolveurs DNS, pas un interrupteur de récursion. `-active` active des méthodes de reconnaissance ; `-brute` est distinct. `-max-depth` concerne les niveaux de force brute. Pour `intel`, `-org` recherche les descriptions d’AS, `-ip` affiche les IP découvertes et `-addr` désigne une plage : les libellés sources simplifient ou confondent ces notions.
>
> **Pipelines :** httpx effectue des requêtes HTTP ; cette étape n’est pas une simple résolution DNS. Les rapprochements avec une organisation constituent des pistes à confirmer, pas une preuve de propriété ou de périmètre autorisé.

## 📋 Table des matières

- [Qu’est-ce qu’Amass](#-quest-ce-quamass)
- [Installation](#-installation)
- [Mode d’énumération](#-mode-dénumération)
- [Mode de renseignement (intel)](#-mode-de-renseignement-intel)
- [Mode DNS](#-mode-dns)
- [Configuration](#-configuration)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce qu’Amass

**Amass** est un projet OWASP de cartographie réseau et de découverte d’actifs externes :

- 🔍 **Énumération des sous-domaines** — Présenté par la source comme un outil particulièrement complet
- 🏢 **Découverte des ASN** — Rechercher les plages IP d’une organisation
- 📡 **Renseignement DNS** — Reconnaissance passive et active
- 🗺️ **Cartographie de la surface d’attaque** — Visualiser l’infrastructure

### Pourquoi Amass ?

| Fonctionnalité | Avantage |
|---------|---------|
| Plus de 55 sources selon le document | Résultats présentés comme très complets |
| Passif et actif | Choisir le niveau d’interaction réseau |
| Renseignement ASN/organisation | Découvrir de l’infrastructure peu visible |
| Base de données en graphe | Visualiser les relations |

---

## 🚀 Installation

```bash
# Installation avec Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Vérifier
amass -version
```

---

## 🔍 Mode d’énumération

### Énumération élémentaire des sous-domaines

```bash
# Énumération passive (sans contact direct avec la cible)
amass enum -passive -d example.com

# Énumération active (force brute DNS selon le libellé source ; voir la précision)
amass enum -active -d example.com

# Force brute avec une liste de mots
amass enum -active -brute -d example.com -w wordlist.txt
```

### Options de sortie

```bash
# Enregistrer dans un fichier
amass enum -passive -d example.com -o subdomains.txt

# Sortie JSON
amass enum -passive -d example.com -json output.json

# Enregistrer dans un répertoire
amass enum -passive -d example.com -dir ./amass_output/
```

### Plusieurs cibles

```bash
# Plusieurs domaines
amass enum -passive -d example.com -d target.com

# Depuis un fichier
amass enum -passive -df domains.txt -o results.txt
```

### Énumération avancée

```bash
# Définir une durée limite (en minutes)
amass enum -passive -d example.com -timeout 30

# Limiter les sources
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Exclure des sources
amass enum -passive -d example.com -exclude Google,Bing

# Afficher les sources de données
amass enum -passive -d example.com -src
```

### Énumération récursive

```bash
# Activer la recherche récursive (libellé source ; voir la précision)
amass enum -passive -d example.com -r

# Définir la profondeur de récursion (selon le mode et la version)
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 Mode de renseignement (intel)

> Le mode intel recherche les domaines racines, ASN et plages CIDR associés aux organisations.

### Rechercher les actifs d’une organisation

```bash
# Par nom d’organisation
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Recherche WHOIS inversée
amass intel -whois -d example.com
```

### Découverte des ASN

```bash
# Rechercher l’ASN d’un domaine (libellé source ; voir la précision)
amass intel -d example.com

# Énumérer à partir d’un ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Plusieurs ASN
amass intel -asn 13335,15169
```

### Plage CIDR

```bash
# Rechercher les actifs d’une plage CIDR
amass intel -active -cidr 192.168.1.0/24

# Plusieurs plages CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### Plage d’adresses IP

```bash
# Plage d’adresses IP
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 Mode DNS

### Requêtes DNS

```bash
# Recherche DNS élémentaire
amass dns -d example.com

# Types d’enregistrements précis
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Résoudre les sous-domaines

```bash
# Résoudre les sous-domaines découverts
amass dns -df subdomains.txt
```

---

<a id="-configuration"></a>
## ⚙️ Configuration

### Emplacement du fichier de configuration

```bash
# Emplacement par défaut indiqué dans la source
~/.config/amass/config.yaml

# Choisir un fichier de configuration personnalisé
amass enum -config ./my_config.yaml -d example.com
```

### Exemple de fichier de configuration

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Sources de données utilisant des clés d’API

| Source | Offre gratuite | API nécessaire |
|--------|-----------|--------------|
| Crt.sh | ✅ | Non |
| SecurityTrails | ✅ | Oui |
| Shodan | ✅ | Oui |
| VirusTotal | ✅ | Oui |
| Censys | ✅ | Oui |
| PassiveTotal | Limitée | Oui |
| BinaryEdge | Limitée | Oui |

---

## 📊 Référence rapide

### Commandes d’énumération

| Commande | Description |
|---------|-------------|
| `amass enum -passive -d domain.com` | Énumération passive des sous-domaines |
| `amass enum -active -d domain.com` | Énumération DNS active |
| `amass enum -active -brute -d domain.com` | Recherche de sous-domaines par force brute |
| `amass enum -d domain.com -o out.txt` | Enregistrer dans un fichier |
| `amass enum -d domain.com -json out.json` | Sortie JSON |

### Commandes de renseignement

| Commande | Description |
|---------|-------------|
| `amass intel -org "Company"` | Rechercher les actifs par nom d’organisation |
| `amass intel -asn 12345` | Énumérer à partir d’un ASN |
| `amass intel -cidr 10.0.0.0/8` | Énumérer une plage CIDR |
| `amass intel -whois -d domain.com` | Recherche WHOIS inversée |

### Options essentielles

| Option | Description |
|------|-------------|
| `-d` | Domaine cible |
| `-df` | Domaines lus dans un fichier |
| `-o` | Fichier de sortie |
| `-passive` | Mode purement passif (sans validation DNS dans ce mode historique) |
| `-active` | Activer les méthodes de reconnaissance actives |
| `-brute` | Recherche DNS par force brute |
| `-src` | Afficher la source des données |
| `-timeout` | Durée limite en minutes |

---

## 🎯 Déroulement d’une reconnaissance en bug bounty

```bash
# 1. Énumération passive (présentée comme sûre dans la source)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Reconnaissance active avec force brute
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Rechercher les actifs de l’organisation
amass intel -org "Target Inc" -o org_domains.txt

# 4. Fusionner les résultats
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Rechercher les hôtes HTTP actifs
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Ressources

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Guide d’utilisation d’Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Fiches connexes
- [Méthodologie bug bounty](../../Bug-Bounty-Methodology/translations/README.fr.md)
- [Subfinder](../../Subfinder/translations/README.fr.md)
- [httpx](../../httpx/translations/README.fr.md)

---

<p align="center">
  <b>🌐 Cartographiez la surface d’attaque !</b><br>
  <i>Amass — Un outil complet d’énumération des sous-domaines</i>
</p>
