# 🔍 Subfinder — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Fast Subdomain Discovery Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Outil rapide d’énumération passive des sous-domaines, développé par ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Repères de version :** les listes de sources, quotas gratuits, exemples d’archives `v2.x.x` et comparaisons reflètent le document anglais. Disponibilité et clés d’API dépendent des fournisseurs. Le dessin ASCII reste celui de la source.
>
> **Lecture des options :** selon l’[aide officielle](https://docs.projectdiscovery.io/opensource/subfinder/usage), `-recursive` sélectionne des sources capables de traiter récursivement les sous-domaines ; il ne déclenche pas à lui seul une exploration active récursive. `-t` règle la résolution concurrente en mode actif, et `-nW` sélectionne les sous-domaines actifs. `-oJ` produit du JSONL, `-oD` désigne un répertoire de sortie avec `-dL`, pas un format CSV. `-cs` est documenté pour la sortie JSON. `-oJL` et `-exclude-subdomains` ne figurent pas dans cette aide consultée. Les lignes originales sont conservées ; leur compatibilité doit être vérifiée.
>
> **Pipelines :** httpx, dnsx, nmap et nuclei peuvent contacter les cibles. Une sortie enrichie de titres ou de codes HTTP n’est pas une simple liste d’URL ; la transmission directe d’un tel résultat dépend des formats acceptés par l’étape suivante. Les résultats d’outils ne prouvent pas à eux seuls une vulnérabilité.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Découverte de sous-domaines](#-découverte-de-sous-domaines)
- [Sources de données](#-sources-de-données)
- [Configuration des API](#-configuration-des-api)
- [Options de sortie](#-options-de-sortie)
- [Fonctionnalités avancées](#-fonctionnalités-avancées)
- [Déroulement d’une reconnaissance en bug bounty](#-déroulement-dune-reconnaissance-en-bug-bounty)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Subfinder** est un outil rapide d’énumération passive des sous-domaines. Il s’appuie sur plusieurs sources, notamment des moteurs de recherche, des sites de partage de texte et des plateformes de renseignement.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Rapide** | Écrit en Go, avec un fort parallélisme |
| **Passif** | Sans interaction directe avec la cible dans son fonctionnement passif |
| **Plusieurs sources** | Plus de 40 sources selon le document |
| **Intégration des API** | Prise en charge de sources avec accès par API |
| **Adapté aux pipelines** | S’intègre avec d’autres outils |
| **Sortie JSON** | Prise en charge d’une sortie structurée |

### Subfinder et les autres outils

| Fonctionnalité | Subfinder | Amass | Assetfinder |
| --------- | ----------- | ------- | ------------- |
| **Vitesse** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Sources** | 40+ | 50+ | 10+ |
| **Analyse active** | ❌ Non | ✅ Oui | ❌ Non |
| **Prise en charge des API** | ✅ Oui | ✅ Oui | ❌ Non |
| **Facilité d’utilisation** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Installation

### Avec Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

### Téléchargement du binaire
```bash
# Télécharger depuis les versions publiées
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Vérifier l’installation
```bash
subfinder -version
subfinder -h
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
subfinder [options] -d <domain>
```

### Options essentielles

| Option | Description |
| -------- | ------------- |
| `-d` | Domaine cible |
| `-dL` | Fichier contenant une liste de domaines |
| `-o` | Fichier de sortie |
| `-oJ` | Sortie JSON |
| `-silent` | Mode silencieux : sous-domaines uniquement |
| `-v` | Sortie détaillée |
| `-t` | Nombre de tâches parallèles |
| `-all` | Utiliser toutes les sources |

### Prise en main rapide
```bash
# Énumération simple
subfinder -d example.com

# Sortie silencieuse (liste sans messages supplémentaires)
subfinder -d example.com -silent

# Enregistrer dans un fichier
subfinder -d example.com -o subdomains.txt

# Plusieurs domaines
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Découverte de sous-domaines

### Un seul domaine

```bash
# Énumération simple
subfinder -d target.com

# Sortie détaillée
subfinder -d target.com -v

# Mode silencieux : sous-domaines uniquement
subfinder -d target.com -silent

# Afficher les sources
subfinder -d target.com -cs
```

### Plusieurs domaines

```bash
# Depuis un fichier
subfinder -dL domains.txt

# Écrire dans un fichier
subfinder -dL domains.txt -o all_subdomains.txt

# Mode silencieux pour un pipeline
subfinder -dL domains.txt -silent | httpx
```

### Énumération récursive

```bash
# Activer le mode récursif (interprétation source ; voir la précision)
subfinder -d target.com -recursive

# Mode récursif avec toutes les sources
subfinder -d target.com -recursive -all
```

---

## 📊 Sources de données

### Sources gratuites (par défaut)

Le document source présente les services suivants comme des sources utilisées par défaut, sans clé d’API :
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Sources nécessitant une clé d’API

Les services suivants nécessitent des clés d’API pour exploiter leurs données :
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Gestion des sources

```bash
# Lister toutes les sources
subfinder -ls

# Utiliser uniquement certaines sources
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Exclure certaines sources
subfinder -d target.com -es shodan,securitytrails

# Utiliser toutes les sources, y compris les plus lentes
subfinder -d target.com -all

# Afficher les sources ayant trouvé chaque sous-domaine
subfinder -d target.com -cs
```

---

## 🔑 Configuration des API

### Emplacement du fichier de configuration

```bash
# Emplacement par défaut
~/.config/subfinder/provider-config.yaml

# Ou indiquer un autre fichier
subfinder -d target.com -pc /path/to/config.yaml
```

### Modèle de fichier de configuration

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### Obtenir des clés d’API

| Source | Offre gratuite | URL |
| -------- | ----------- | ----- |
| **VirusTotal** | ✅ Oui | https://www.virustotal.com |
| **SecurityTrails** | ✅ Oui, avec limites | https://securitytrails.com |
| **Shodan** | ✅ Oui, avec limites | https://shodan.io |
| **Censys** | ✅ Oui | https://censys.io |
| **Chaos** | ✅ Gratuit, avec limites | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Gratuit | https://github.com/settings/tokens |

### Configuration gratuite recommandée

```yaml
# Configuration minimale recommandée dans le document source pour le bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Options de sortie

### Formats de sortie

```bash
# Texte brut (par défaut)
subfinder -d target.com -o subs.txt

# Sortie JSON
subfinder -d target.com -oJ -o subs.json

# JSON Lines
subfinder -d target.com -oJL -o subs.jsonl

# Format CSV (libellé source ; voir la précision)
subfinder -d target.com -oD -o subs.csv
```

### Filtrage des résultats

```bash
# Mode silencieux : sortie sans messages supplémentaires
subfinder -d target.com -silent

# Afficher uniquement les nouveaux sous-domaines (exemple source ; voir la précision)
subfinder -d target.com -exclude-subdomains known.txt

# Désactiver les couleurs
subfinder -d target.com -nc
```

### Enchaîner les résultats dans un pipeline

```bash
# Transmettre la sortie à httpx
subfinder -d target.com -silent | httpx -silent

# Transmettre la sortie à nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Enregistrer et traiter
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Fonctionnalités avancées

### Parallélisme et performances

```bash
# Régler le parallélisme (valeur par défaut indiquée : 10)
subfinder -d target.com -t 50

# Limiter le débit
subfinder -d target.com -rate-limit 100

# Délai d’attente
subfinder -d target.com -timeout 30
```

### Sélection et exclusion des résultats

```bash
# Retenir certains motifs
subfinder -d target.com -match "api,dev,stage"

# Exclure certains motifs
subfinder -d target.com -filter "www,mail"
```

### Options des résolveurs

```bash
# Utiliser des résolveurs personnalisés
subfinder -d target.com -rL resolvers.txt

# Résoudre les sous-domaines
subfinder -d target.com -nW
```

### Prise en charge des proxys

```bash
# Proxy HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS (exemple source ; prise en charge à vérifier selon la version)
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Déroulement d’une reconnaissance en bug bounty

### Pipeline complet de reconnaissance

```bash
# Étape 1 : énumération des sous-domaines
subfinder -d target.com -all -o subs.txt

# Étape 2 : rechercher les hôtes actifs
cat subs.txt | httpx -o live.txt

# Étape 3 : analyser avec nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Commande en une ligne
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Traitement de plusieurs domaines

```bash
# Énumérer tous les domaines du programme
subfinder -dL scope.txt -all -o all-subs.txt

# Traiter les résultats
cat all-subs.txt | httpx -silent | nuclei
```

### Surveillance continue

```bash
# Enregistrer un état de référence
subfinder -d target.com -silent > baseline.txt

# Rechercher ensuite les nouveaux sous-domaines
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

### Intégration avec d’autres outils

```bash
# Avec httpx, pour les hôtes actifs
subfinder -d target.com -silent | httpx

# Avec ffuf, pour le fuzzing des répertoires
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Avec nmap, pour l’analyse des ports
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Pipeline complet
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Exemples pratiques

### Exemple 1 : énumération simple
```bash
subfinder -d hackerone.com -silent
```

### Exemple 2 : toutes les sources
```bash
subfinder -d target.com -all -v
```

### Exemple 3 : enregistrer les résultats
```bash
subfinder -d target.com -o target-subs.txt
```

### Exemple 4 : plusieurs domaines
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Exemple 5 : afficher les sources
```bash
subfinder -d target.com -cs
```

### Exemple 6 : sortie JSON
```bash
subfinder -d target.com -oJ -o results.json
```

### Exemple 7 : avec un proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Exemple 8 : performances élevées
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Exemple 9 : pipeline complet pour le bug bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Exemple 10 : découverte récursive
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
| ------ | --------- |
| Énumération simple | `subfinder -d target.com` |
| Sortie silencieuse | `subfinder -d target.com -silent` |
| Enregistrer dans un fichier | `subfinder -d target.com -o subs.txt` |
| Toutes les sources | `subfinder -d target.com -all` |
| Afficher les sources | `subfinder -d target.com -cs` |
| Plusieurs domaines | `subfinder -dL domains.txt` |
| Sortie JSON | `subfinder -d target.com -oJ` |
| Lister les sources | `subfinder -ls` |

### Options courantes

| Option | Description |
| -------- | ------------- |
| `-d` | Domaine cible |
| `-dL` | Fichier de liste de domaines |
| `-o` | Fichier de sortie |
| `-oJ` | Sortie JSON |
| `-silent` | Mode silencieux |
| `-all` | Utiliser toutes les sources |
| `-cs` | Afficher les sources de chaque sous-domaine |
| `-t` | Tâches parallèles |
| `-recursive` | Énumération récursive |

### Pipelines courants

| Pipeline | Commande |
| ---------- | --------- |
| Hôtes actifs | `subfinder -d target.com -silent \| httpx` |
| Reconnaissance complète | `subfinder -d target.com \| httpx \| nuclei` |
| Analyse des ports | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Configurer les clés d’API**
   ```bash
   # Obtenir davantage de résultats grâce aux API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Utiliser toutes les sources pour élargir les résultats**
   ```bash
   subfinder -d target.com -all
   ```

3. **Combiner avec d’autres outils**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Surveiller les nouveaux sous-domaines**
   ```bash
   # Recherche quotidienne de nouveaux actifs
   subfinder -d target.com -silent > today.txt
   ```

### Conseils de performance

```bash
# Parallélisme élevé
subfinder -d target.com -t 100 -all

# Délai d’attente pour les sources lentes
subfinder -d target.com -timeout 60
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** la collecte passive de Subfinder n’interagit pas directement avec les cibles. Les modes de résolution active et les outils qui suivent dans le pipeline peuvent toutefois le faire.
> 
> - ✅ Utiliser l’outil dans les programmes autorisés
> - ✅ Respecter le périmètre des programmes de bug bounty
> - ✅ Utiliser ses propres domaines
> - ❌ Ne pas énumérer de cibles sans autorisation
> - ❌ Ne pas dépasser le périmètre du programme
> 
> **Toujours s’assurer de disposer d’une autorisation.**

---

## 📚 Ressources

### Ressources officielles
- [Dépôt GitHub de Subfinder](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/subfinder/)

### Fiches connexes
- [Nuclei](../../Nuclei/translations/README.fr.md)
- [ffuf](../../ffuf/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)

---

<p align="center">
  <b>🔍 Découvrez les sous-domaines !</b><br>
  <i>La première étape de la reconnaissance en bug bounty</i>
</p>
