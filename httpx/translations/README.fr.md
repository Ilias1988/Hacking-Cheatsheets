# 🌐 httpx — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Fast HTTP Probe & Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Boîte à outils HTTP rapide et polyvalente, développée par ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Outil concerné :** cette fiche décrit [httpx de ProjectDiscovery](https://github.com/projectdiscovery/httpx), pas le [client Python HTTPX](https://www.python-httpx.org/). Les noms d’exécutables peuvent se recouper ; vérifiez le produit installé. Le dessin ASCII et les commandes restent ceux de la source.
>
> **Repères de version :** les archives `v1.x.x` sont illustratives. Dans l’[aide officielle consultée](https://docs.projectdiscovery.io/opensource/httpx/usage), `-method` affiche la méthode et `-x` sélectionne les méthodes à envoyer. `-include-response-header` est une option booléenne d’inclusion des en-têtes dans la sortie JSON, pas une liste d’en-têtes à extraire. Les formes sources `-response-header`, `-tlsgrab`, `-screenshot-output`, `-ssd`, `-jsonl`, `-resume-file`, `-mt` et `-ft` ne figurent pas dans cette aide ; les exemples sont conservés pour fidélité. `-fr` est l’alias de suivi des redirections, pas le filtre regex montré dans la source. `-no-fallback` permet d’afficher les résultats HTTP et HTTPS, plutôt que de se limiter au repli automatique habituel.
>
> **Interprétation :** `-td` et `-tech-detect` désignent la même détection ; le second nom n’active pas à lui seul un mode plus détaillé. `-tls-probe` vérifie les domaines extraits des données TLS, tandis que `-tls-grab` collecte les informations TLS. `-json` produit du JSONL. Une sortie enrichie de titres et de codes n’est pas une simple liste d’URL ; sa réutilisation dépend du format accepté par l’outil suivant. La recherche d’un mot ou d’un titre ne prouve pas à elle seule la présence d’un panneau exploitable.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Vérifications HTTP et découverte](#-vérifications-http-et-découverte)
- [Extraction d’informations](#-extraction-dinformations)
- [Filtrage et sélection des réponses](#-filtrage-et-sélection-des-réponses)
- [Options de sortie](#-options-de-sortie)
- [Fonctionnalités avancées](#-fonctionnalités-avancées)
- [Déroulement d’une reconnaissance en bug bounty](#-déroulement-dune-reconnaissance-en-bug-bounty)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**httpx** est une boîte à outils HTTP rapide et polyvalente qui réalise plusieurs vérifications à l’aide de la bibliothèque retryablehttp. Elle s’adresse notamment aux chercheurs en sécurité et aux participants aux programmes de bug bounty.

### Principales fonctionnalités

| Fonctionnalité | Description |
|---------|-------------|
| **Rapide** | Outil écrit en Go, avec un fort parallélisme |
| **Vérifications multiples** | Code de statut, titre, technologies et en-têtes |
| **Détection des technologies** | Détection fondée sur Wappalyzer |
| **Capture d’écran** | Captures à l’aide d’un navigateur sans interface graphique |
| **Pipelines** | Fonctionne avec subfinder et nuclei |
| **Sortie JSON** | Prise en charge d’une sortie structurée |

### httpx et les autres outils

| Fonctionnalité | httpx | curl | wget |
|---------|-------|------|------|
| **Vitesse** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Parallélisme** | ✅ Oui | ❌ Non | ❌ Non |
| **Détection des technologies** | ✅ Oui | ❌ Non | ❌ Non |
| **Captures d’écran** | ✅ Oui | ❌ Non | ❌ Non |
| **Pipelines** | ✅ Oui | ⚠️ Manuel | ⚠️ Manuel |

---

## 📥 Installation

### Avec Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Téléchargement du binaire
```bash
# Télécharger depuis les versions publiées
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Vérifier l’installation
```bash
httpx -version
httpx -h
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Options essentielles

| Option | Description |
|--------|-------------|
| `-u` | URL d’une cible |
| `-l` | Fichier contenant une liste d’URL |
| `-sc` | Afficher le code de statut |
| `-title` | Afficher le titre de la page |
| `-td` | Détection des technologies |
| `-o` | Fichier de sortie |
| `-silent` | Mode silencieux |
| `-json` | Sortie JSON |

### Prise en main rapide
```bash
# Vérifier une seule URL
httpx -u https://example.com

# Vérifier les cibles d’un fichier
httpx -l urls.txt

# Depuis l’entrée standard, dans un pipeline
cat hosts.txt | httpx

# Avec subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 Vérifications HTTP et découverte

### Vérifications élémentaires

```bash
# Vérifier les hôtes en HTTP/HTTPS
cat hosts.txt | httpx

# Inclure le code de statut
cat hosts.txt | httpx -sc

# Vérifier des ports précis
cat hosts.txt | httpx -p 80,443,8080,8443

# Vérifier les ports courants de la liste
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Vérification des ports

```bash
# Ports personnalisés
httpx -l hosts.txt -p 80,443,8080,8443

# Plage de ports
httpx -l hosts.txt -p 80-100

# Ports web courants
httpx -l hosts.txt -p http:80,https:443
```

### Détection du protocole

```bash
# Vérifications HTTP/HTTPS
cat hosts.txt | httpx

# Suivre les redirections
cat hosts.txt | httpx -follow-redirects

# Nombre maximal de redirections
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Sans repli automatique (libellé source ; voir la précision)
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Extraction d’informations

### Codes de statut et titres

```bash
# Code de statut
httpx -l urls.txt -sc

# Titre de la page
httpx -l urls.txt -title

# Les deux
httpx -l urls.txt -sc -title

# Taille du contenu
httpx -l urls.txt -cl

# Serveur web
httpx -l urls.txt -server

# Toutes les informations de base
httpx -l urls.txt -sc -title -cl -server
```

### Détection des technologies

```bash
# Détecter les technologies
httpx -l urls.txt -td

# Détection des technologies (libellé source « détaillé »)
httpx -l urls.txt -tech-detect

# Avec d’autres informations
httpx -l urls.txt -sc -title -td
```

### En-têtes et réponse

```bash
# En-têtes de réponse
httpx -l urls.txt -response-header

# En-tête précis
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Empreinte du corps de réponse
httpx -l urls.txt -hash md5

# Aperçu du corps
httpx -l urls.txt -bp
```

### Informations IP et DNS

```bash
# Adresse IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Détection de CDN
httpx -l urls.txt -cdn

# Informations ASN
httpx -l urls.txt -asn

# Toutes les informations DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

### Informations SSL/TLS

```bash
# Vérification à partir des noms TLS (libellé source)
httpx -l urls.txt -tls-probe

# Collecte d’informations TLS
httpx -l urls.txt -tls-grab

# Informations du certificat
httpx -l urls.txt -tlsgrab
```

### Empreinte de la favicon

```bash
# Obtenir l’empreinte de la favicon, utile pour les recherches Shodan
httpx -l urls.txt -favicon
```

### Captures d’écran

```bash
# Prendre des captures d’écran
httpx -l urls.txt -screenshot

# Capture avec répertoire de sortie
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Options du navigateur sans interface graphique
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtrage et sélection des réponses

### Filtrer selon le code de statut

```bash
# Retenir les codes de statut indiqués
httpx -l urls.txt -mc 200,301,302

# Exclure les codes de statut indiqués
httpx -l urls.txt -fc 404,403,500

# Tout retenir, sauf certains codes (libellé source)
httpx -l urls.txt -mc all -fc 404
```

### Filtrer selon la taille

```bash
# Retenir une taille de contenu
httpx -l urls.txt -ml 1234

# Exclure une taille de contenu
httpx -l urls.txt -fl 0

# Retenir un nombre de lignes
httpx -l urls.txt -mlc 100

# Exclure un nombre de lignes
httpx -l urls.txt -flc 50
```

### Filtrer selon le contenu

```bash
# Retenir selon une expression régulière dans la réponse
httpx -l urls.txt -mr "admin|login"

# Exclure selon une expression régulière dans la réponse
httpx -l urls.txt -fr "error|not found"

# Retenir une chaîne de caractères
httpx -l urls.txt -ms "dashboard"
```

### Filtrer selon les technologies

```bash
# Retenir une technologie (libellé source ; voir la précision)
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Exclure une technologie (libellé source ; voir la précision)
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Options de sortie

### Formats de sortie

```bash
# Texte brut
httpx -l urls.txt -o results.txt

# Sortie JSON
httpx -l urls.txt -json -o results.json

# Lignes JSON
httpx -l urls.txt -jsonl -o results.jsonl

# Sortie CSV
httpx -l urls.txt -csv -o results.csv
```

### Champs de sortie

```bash
# Enregistrer la réponse
httpx -l urls.txt -sr -srd ./responses/

# Enregistrer les captures d’écran
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Sortie personnalisée
httpx -l urls.txt -o output.txt -sc -title -td
```

### Modes silencieux et détaillé

```bash
# Silencieux, avec les URL seules
httpx -l urls.txt -silent

# Mode détaillé
httpx -l urls.txt -v

# Débogage
httpx -l urls.txt -debug

# Sans couleur
httpx -l urls.txt -nc
```

---

## ⚡ Fonctionnalités avancées

### Parallélisme et limitation du débit

```bash
# Régler le nombre de threads (valeur par défaut indiquée : 50)
httpx -l urls.txt -threads 100

# Limite du débit
httpx -l urls.txt -rate-limit 100

# Délai d’attente
httpx -l urls.txt -timeout 10

# Nouvelles tentatives
httpx -l urls.txt -retries 3
```

### Prise en charge des proxys

```bash
# Proxy HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# Proxy SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### En-têtes personnalisés

```bash
# En-tête personnalisé
httpx -l urls.txt -H "Authorization: Bearer token123"

# Plusieurs en-têtes
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Méthodes de requête

```bash
# Requête HEAD, présentée comme plus rapide dans la source
httpx -l urls.txt -method HEAD

# Méthode personnalisée
httpx -l urls.txt -method OPTIONS

# POST avec corps de requête
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Reprise et pagination

```bash
# Reprendre depuis la dernière position
httpx -l urls.txt -resume

# Enregistrer la progression (libellé source)
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Déroulement d’une reconnaissance en bug bounty

### Pipeline complet de reconnaissance

```bash
# Étape 1 : énumérer les sous-domaines
subfinder -d target.com -o subs.txt

# Étape 2 : vérifier les hôtes actifs avec httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Étape 3 : rechercher des vulnérabilités
nuclei -l live.txt -severity critical,high

# Commande en une ligne
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Reconnaissance détaillée

```bash
# Collecte complète d’informations
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Capturer tous les hôtes actifs

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Rechercher des points de terminaison intéressants

```bash
# Rechercher des panneaux d’administration
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Rechercher des points de terminaison d’API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Vérifier des chemins précis
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Recherche ciblée par technologie

```bash
# Rechercher des sites WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Rechercher des serveurs Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Exemples pratiques

### Exemple 1 : vérification élémentaire
```bash
echo "https://example.com" | httpx -sc -title
```

### Exemple 2 : reconnaissance complète depuis les sous-domaines
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Exemple 3 : capturer tous les hôtes
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Exemple 4 : ne retenir que les hôtes actifs
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Exemple 5 : détection des technologies
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Exemple 6 : avec un proxy Burp
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Exemple 7 : ports personnalisés
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Exemple 8 : ne retenir que le statut 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Exemple 9 : sortie JSON à traiter
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Exemple 10 : pipeline complet
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Commande |
|------|---------|
| Vérification élémentaire | `httpx -u url` |
| Depuis un fichier | `httpx -l urls.txt` |
| Code de statut | `httpx -l urls.txt -sc` |
| Titre de la page | `httpx -l urls.txt -title` |
| Détection des technologies | `httpx -l urls.txt -td` |
| Adresse IP | `httpx -l urls.txt -ip` |
| Capture d’écran | `httpx -l urls.txt -screenshot` |
| Sortie JSON | `httpx -l urls.txt -json -o out.json` |

### Options courantes

| Option | Description |
|--------|-------------|
| `-u` | Une URL |
| `-l` | Fichier contenant la liste d’URL |
| `-sc` | Code de statut |
| `-title` | Titre de la page |
| `-td` | Détection des technologies |
| `-ip` | Adresse IP |
| `-cdn` | Détection de CDN |
| `-server` | Serveur web |
| `-mc` | Codes retenus |
| `-fc` | Codes exclus |
| `-json` | Sortie JSON |
| `-silent` | Mode silencieux |

### Pipelines courants

| Pipeline | Commande |
|----------|---------|
| Hôtes actifs | `subfinder -d target.com \| httpx -silent` |
| Avec informations | `subfinder -d target.com \| httpx -sc -title -td` |
| Vers nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Utiliser avec Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Obtenir une vue complète**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Prendre des captures pour le triage**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **Utiliser JSON pour le traitement**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Conseils de performance

```bash
# Vitesse élevée
httpx -l hosts.txt -threads 100 -timeout 5

# Débit faible et rythme lent
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** httpx envoie des requêtes HTTP aux cibles. Utilisez-le uniquement sur des systèmes pour lesquels vous disposez d’une autorisation.
> 
> - ✅ Utiliser dans les programmes que vous êtes autorisé à tester
> - ✅ Tester les cibles de bug bounty incluses dans le périmètre
> - ✅ Tester vos propres domaines
> - ❌ Ne jamais vérifier de cibles sans autorisation
> - ❌ Ne pas dépasser les limites de débit
> 
> **Les vérifications effectuées sans autorisation peuvent être illégales.**

---

## 📚 Ressources

### Ressources officielles
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentation](https://docs.projectdiscovery.io/tools/httpx/)

### Fiches connexes
- [Subfinder](../../Subfinder/translations/README.fr.md)
- [Nuclei](../../Nuclei/translations/README.fr.md)
- [ffuf](../../ffuf/translations/README.fr.md)

---

<p align="center">
  <b>🌐 Vérifiez la surface HTTP !</b><br>
  <i>La boîte à outils HTTP essentielle pour le bug bounty</i>
</p>
