# 🎯 Nuclei — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Fast Template-Based Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 Un scanner de vulnérabilités de référence, fondé sur des modèles, pour le bug bounty</b>
</p>

---

> **Repères de version :** nombres de modèles, chemins, tags et fichiers d’archives sont ceux de la source. `v3.x.x` et les CVE abrégées sont des emplacements illustratifs. `-ut` met à jour les modèles ; l’option de mise à jour du moteur est distincte. La sortie `-json` est historique : l’aide officielle documente notamment `-jsonl` et des options d’export séparées. Voir l’[aide officielle de Nuclei](https://github.com/projectdiscovery/nuclei#command-line-flags). Les commandes restent inchangées.
>
> **Modèles personnalisés :** l’exemple « Vérifier un en-tête » répète la clé YAML `matchers`. Une [association YAML exige des clés uniques](https://yaml.org/spec/1.2/), et les conditions doivent être réunies dans une structure cohérente ; l’extrait source est conservé pour fidélité. Le test de statut 200 seul de l’exemple « Détecter une page précise » ne prouve pas la présence d’un panneau d’administration. Voir le [guide officiel des matchers](https://github.com/projectdiscovery/nuclei-templates/blob/main/TEMPLATE-CREATION-GUIDE.md#matcher-best-practices). Les clés et valeurs YAML, même descriptives, restent en anglais comme dans le code source.
>
> **Interprétation :** un résultat de modèle doit être vérifié dans son contexte. Débit réduit, proxy et exclusions d’étiquettes ne garantissent ni discrétion ni absence d’effet. Le dessin ASCII conserve son texte source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Catégories de modèles](#-catégories-de-modèles)
- [Analyse des cibles](#-analyse-des-cibles)
- [Sélection des modèles](#-sélection-des-modèles)
- [Options de sortie](#-options-de-sortie)
- [Fonctionnalités avancées](#-fonctionnalités-avancées)
- [Modèles personnalisés](#-modèles-personnalisés)
- [Déroulement d’une recherche en bug bounty](#-déroulement-dune-recherche-en-bug-bounty)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Nuclei** est un scanner rapide de vulnérabilités fondé sur des modèles, développé par ProjectDiscovery. Il est largement utilisé en bug bounty et en recherche en sécurité.

### Principales fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Plus de 9000 modèles selon la source** | CVE, panneaux, erreurs de configuration, prises de contrôle |
| **Rapide et efficace** | Analyses parallèles avec limitation du débit |
| **Fondé sur des modèles** | Modèles YAML faciles à personnaliser |
| **Porté par la communauté** | Modèles régulièrement mis à jour |
| **Plusieurs protocoles** | HTTP, DNS, TCP, SSL, navigateur headless |
| **Intégration simple** | Fonctionne avec d’autres outils (httpx, subfinder) |

### Types de modèles

| Type | Description | Nombre |
| ------ | ------------- | ------- |
| **CVE** | Vulnérabilités connues | 2000+ |
| **Panneaux exposés** | Panneaux d’administration et tableaux de bord | 500+ |
| **Erreurs de configuration** | Erreurs de configuration de sécurité | 300+ |
| **Identifiants par défaut** | Identifiants par défaut | 200+ |
| **Prises de contrôle** | Prise de contrôle de sous-domaines | 100+ |
| **Technologies** | Détection des technologies | 1000+ |
| **Expositions de données** | Exposition de fichiers sensibles | 500+ |

---

## 📥 Installation

### Avec Go (recommandé)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Téléchargement du binaire
```bash
# Télécharger depuis les versions publiées
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Mise à jour des modèles
```bash
# Mettre à jour Nuclei et les modèles (libellé source ; voir la précision)
nuclei -ut

# Mettre à jour uniquement les modèles
nuclei -update-templates
```

### Vérifier l’installation
```bash
nuclei -version
nuclei -tl              # Lister les modèles
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
nuclei [options] -target <url>
```

### Options essentielles

| Option | Description |
| -------- | ------------- |
| `-u` | URL d’une cible unique |
| `-l` | Fichier contenant une liste d’URL |
| `-t` | Modèle ou modèles à exécuter |
| `-tags` | Modèles portant certaines étiquettes |
| `-severity` | Filtrer selon la gravité |
| `-o` | Fichier de sortie |
| `-silent` | Mode silencieux |
| `-v` | Sortie détaillée |

### Prise en main rapide
```bash
# Analyser une cible
nuclei -u https://example.com

# Analyser une liste de cibles
nuclei -l urls.txt

# Analyser avec des modèles précis
nuclei -u https://example.com -t cves/

# Analyser selon les étiquettes
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Catégories de modèles

### Modèles CVE

```bash
# Tous les modèles CVE
nuclei -u target.com -t cves/

# Une année de CVE précise
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# CVE critiques uniquement
nuclei -u target.com -t cves/ -severity critical

# Une CVE précise
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Panneaux exposés

```bash
# Détection de tous les panneaux
nuclei -u target.com -t exposed-panels/

# Panneaux précis
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Erreurs de configuration

```bash
# Toutes les erreurs de configuration
nuclei -u target.com -t misconfiguration/

# Types précis
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Identifiants par défaut

```bash
# Tous les identifiants par défaut
nuclei -u target.com -t default-logins/

# Fournisseurs précis
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Prise de contrôle de sous-domaines

```bash
# Tous les tests de prise de contrôle
nuclei -u target.com -t takeovers/

# Fournisseurs précis
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Détection des technologies

```bash
# Détection des technologies
nuclei -u target.com -t technologies/

# Détection des CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Expositions de données

```bash
# Exposition de fichiers sensibles
nuclei -u target.com -t exposures/

# Expositions précises
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Analyse des cibles

### Cible unique

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Plusieurs cibles

```bash
# Depuis un fichier
nuclei -l targets.txt

# Depuis l’entrée standard (pipeline)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Plusieurs URL sur la ligne de commande
nuclei -u https://site1.com -u https://site2.com
```

### Avec d’autres outils

```bash
# Avec subfinder (sous-domaines)
subfinder -d example.com | nuclei

# Avec httpx (hôtes actifs)
subfinder -d example.com | httpx | nuclei

# Avec katana (URL explorées)
katana -u https://example.com | nuclei

# Pipeline complet de reconnaissance
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-sélection-des-modèles"></a>
## 🏷️ Sélection des modèles

### Par chemin de modèle

```bash
# Un seul modèle
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Répertoire de modèles
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Plusieurs répertoires
nuclei -u target.com -t cves/ -t exposed-panels/

# Chemin de modèles personnalisés
nuclei -u target.com -t /path/to/custom/templates/
```

### Par étiquette (tag)

```bash
# Une seule étiquette
nuclei -u target.com -tags cve

# Plusieurs étiquettes (OU)
nuclei -u target.com -tags cve,rce,sqli

# Étiquettes courantes
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Par gravité

```bash
# Un seul niveau de gravité
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Plusieurs niveaux de gravité
nuclei -u target.com -severity critical,high

# Exclure des niveaux de gravité
nuclei -u target.com -exclude-severity info,low
```

### Exclure des modèles

```bash
# Exclure des modèles précis
nuclei -u target.com -exclude-templates cves/2020/

# Exclure selon les étiquettes
nuclei -u target.com -exclude-tags dos,fuzz

# Exclure selon l’identifiant
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Options de sortie

### Formats de sortie

```bash
# Sortie standard
nuclei -u target.com -o results.txt

# Sortie JSON
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Filtrage de la sortie

```bash
# Mode silencieux (résultats uniquement)
nuclei -u target.com -silent

# Sans couleur
nuclei -u target.com -nc

# Sortie détaillée
nuclei -u target.com -v

# Débogage
nuclei -u target.com -debug
```

### Options d’export

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Rapport Markdown
nuclei -u target.com -me reports/
```

---

## ⚡ Fonctionnalités avancées

### Limitation du débit

```bash
# Requêtes par seconde
nuclei -u target.com -rate-limit 100

# Nombre de cibles traitées simultanément par modèle
nuclei -u target.com -bulk-size 25

# Parallélisme
nuclei -u target.com -c 50

# Parallélisme des modèles
nuclei -u target.com -rl 100 -c 50
```

### Prise en charge des proxys

```bash
# Proxy HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Proxy Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Authentification

```bash
# Authentification par en-tête
nuclei -u target.com -H "Authorization: Bearer token123"

# Authentification par cookie
nuclei -u target.com -H "Cookie: session=abc123"

# Plusieurs en-têtes
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Intégration d’Interactsh

```bash
# Utiliser Interactsh pour les tests hors bande (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# Serveur Interactsh personnalisé
nuclei -u target.com -iserver https://my-interactsh.com
```

### Mode de navigateur sans interface graphique (headless)

```bash
# Activer le navigateur sans interface graphique
nuclei -u target.com -headless

# Modèles headless uniquement
nuclei -u target.com -t headless/
```

### Enchaînements de modèles (workflows)

```bash
# Exécuter un enchaînement de modèles
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Lister les enchaînements de modèles
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Modèles personnalisés

### Structure d’un modèle

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Exemples de modèles

#### Détecter une page précise
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Vérifier un en-tête
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Valider un modèle

```bash
# Valider la syntaxe du modèle
nuclei -validate -t my-template.yaml

# Tester le modèle
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Déroulement d’une recherche en bug bounty

### Pipeline complet de reconnaissance

```bash
# Étape 1 : énumération des sous-domaines
subfinder -d target.com -o subs.txt

# Étape 2 : identifier les hôtes actifs
cat subs.txt | httpx -o live.txt

# Étape 3 : lancer Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Commande en une ligne
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Analyse par ordre de priorité

```bash
# Phase 1 : gravité critique uniquement (rapide)
nuclei -l targets.txt -severity critical -o critical.txt

# Phase 2 : gravité élevée
nuclei -l targets.txt -severity high -o high.txt

# Phase 3 : CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# Phase 4 : analyse complète
nuclei -l targets.txt -o all.txt
```

### Analyse ciblée

```bash
# Cibles WordPress
nuclei -l wp-sites.txt -tags wordpress

# Points de terminaison d’API
nuclei -l apis.txt -tags api

# Pages de connexion
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Exemples pratiques

### Exemple 1 : analyse complète
```bash
nuclei -u https://target.com -o results.txt
```

### Exemple 2 : CVE critiques uniquement
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Exemple 3 : analyse rapide en bug bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Exemple 4 : recherche de prises de contrôle de sous-domaines
```bash
cat subs.txt | nuclei -t takeovers/
```

### Exemple 5 : détection des technologies
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Exemple 6 : avec un proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Exemple 7 : analyse authentifiée
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Exemple 8 : analyse avec débit limité
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Exemple 9 : sortie JSON pour traitement
```bash
nuclei -u https://target.com -json -o results.json
```

### Exemple 10 : pipeline complet
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
| ------ | --------- |
| Analyse de base | `nuclei -u target.com` |
| Avec des modèles | `nuclei -u target.com -t cves/` |
| Selon les étiquettes | `nuclei -u target.com -tags cve,rce` |
| Selon la gravité | `nuclei -u target.com -severity critical` |
| Depuis une liste | `nuclei -l urls.txt` |
| Mettre à jour les modèles | `nuclei -ut` |
| Sortie silencieuse | `nuclei -u target.com -silent` |
| Sortie JSON | `nuclei -u target.com -json -o out.json` |

### Étiquettes courantes

| Étiquette | Description |
| ----- | ------------- |
| `cve` | Vulnérabilités CVE |
| `rce` | Exécution de code à distance |
| `sqli` | Injection SQL |
| `xss` | Injection de scripts intersites (XSS) |
| `ssrf` | Falsification de requêtes côté serveur (SSRF) |
| `lfi` | Inclusion de fichiers locaux (LFI) |
| `panel` | Panneaux d’administration |
| `takeover` | Prise de contrôle de sous-domaines |

### Niveaux de gravité

| Gravité | Couleur | Priorité |
| ---------- | ------- | ---------- |
| `critical` | 🔴 Rouge | Immédiate |
| `high` | 🟠 Orange | Élevée |
| `medium` | 🟡 Jaune | Modérée |
| `low` | 🟢 Vert | Faible |
| `info` | 🔵 Bleu | Information |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

```bash
# Réglages proposés pour les analyses étendues
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Mode silencieux pour les pipelines
nuclei -l targets.txt -silent -o results.txt
```

### Conseils pour le bug bounty

1. **Commencer par les gravités critique et élevée**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Rechercher les constats rapides à vérifier**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Utiliser des modèles à jour**
   ```bash
   nuclei -ut  # Toujours mettre à jour avant la recherche
   ```

4. **Limiter le débit pour préserver la stabilité**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Conseils présentés comme discrets dans la source

```bash
# Débit faible et parallélisme réduit
nuclei -l targets.txt -rl 10 -c 2

# Par un proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Nuclei est un scanner de vulnérabilités puissant, réservé aux **tests autorisés**.
> 
> - ✅ Tester les programmes pour lesquels vous avez une autorisation
> - ✅ Utiliser dans le cadre de programmes de bug bounty autorisés
> - ✅ Utiliser sur vos propres systèmes
> - ❌ Ne jamais analyser sans autorisation
> - ❌ Ne pas dépasser les limites de débit ni sortir du périmètre
> 
> **Les analyses non autorisées sont illégales et contraires à l’éthique.**

---

## 📚 Ressources

### Ressources officielles
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Modèles Nuclei](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/nuclei/)

### Ressources sur les modèles
- [Modèles de la communauté](https://github.com/projectdiscovery/nuclei-templates)
- [Guide des modèles](https://docs.projectdiscovery.io/templates/)

### Fiches connexes
- [Nmap](../../Nmap/translations/README.fr.md)
- [Burp Suite](../../Burp-Suite/translations/README.fr.md)
- [SQLMap](../../SQLMap/translations/README.fr.md)

---

<p align="center">
  <b>🎯 Maîtrisez l’analyse des vulnérabilités !</b><br>
  <i>Un allié pour la recherche en bug bounty</i>
</p>
