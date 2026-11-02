# ⚔️ Katana — robot d’exploration web de nouvelle génération

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Next-Generation Web Crawling Framework
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Crawler">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Repères de version :** dans l’[aide officielle de Katana](https://docs.projectdiscovery.io/opensource/katana/usage), `-cs` définit des expressions régulières de périmètre ; la stratégie utilise `-s`. Les exemples `-cs depth-first` et `-cs breadth-first` sont conservés, mais leur libellé source confond ces options.
>
> **Autres précisions :** `-timeout` attend des secondes pour les requêtes, pas des millisecondes de chargement JavaScript. `-system-chrome` sélectionne le Chrome installé ; un chemin explicite relève de `-system-chrome-path`. `-fc` désigne un fichier de configuration des formulaires, pas les codes HTTP à exclure. Les formes sources `-fv`, `-screenshot` et `-mc` ne figurent pas dans l’aide consultée ; la liste de champs de `-f` y est également différente. Commandes et valeurs restent inchangées, ainsi que le texte du dessin ASCII. Vérifiez les options de votre version avant d’utiliser ces exemples.

## 📋 Table des matières

- [Qu’est-ce que Katana](#-quest-ce-que-katana)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Options d’exploration](#-options-dexploration)
- [Mode sans interface graphique (headless)](#-mode-sans-interface-graphique-headless)
- [Sortie et filtrage](#-sortie-et-filtrage)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce que Katana

**Katana** est un framework d’exploration web de nouvelle génération, développé par ProjectDiscovery :

- ⚡ **Rapide** — Écrit en Go, avec un fort parallélisme
- 🔍 **Rendu JavaScript** — Prise en charge d’un navigateur sans interface graphique
- 📡 **Web moderne** — Prise en charge des applications monopages (SPA) et du contenu dynamique
- 🎯 **Orienté bug bounty** — Conçu pour les tests de sécurité

### Pourquoi Katana ?

| Fonctionnalité | Avantage |
|---------|---------|
| Chrome sans interface graphique | Explorer les applications utilisant beaucoup JavaScript |
| En-têtes personnalisés | Exploration avec authentification |
| Contrôle du périmètre | Rester dans le périmètre |
| Remplissage des formulaires | Découvrir davantage de points de terminaison |

---

## 🚀 Installation

```bash
# Installation avec Go (recommandée)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Vérifier
katana -version
```

---

## 📖 Utilisation de base

### Cible unique

```bash
# Exploration de base
katana -u https://example.com

# Enregistrer la sortie
katana -u https://example.com -o urls.txt

# Mode silencieux
katana -u https://example.com -silent
```

### Plusieurs cibles

```bash
# Depuis l’entrée standard
echo "https://example.com" | katana

# Plusieurs URL
cat targets.txt | katana

# Depuis un fichier
katana -list targets.txt
```

### Profondeur d’exploration

```bash
# Définir la profondeur (valeur par défaut : 3)
katana -u https://example.com -d 5

# Profondeur 1 (seule la page cible selon le document source)
katana -u https://example.com -d 1
```

---

<a id="-options-dexploration"></a>
## 🕷️ Options d’exploration

### Contrôle du périmètre

```bash
# Explorer uniquement le périmètre (même domaine)
katana -u https://example.com -fs dn

# Options de périmètre :
# dn - nom de domaine
# rdn - domaine racine
# fqdn - nom de domaine pleinement qualifié

# Périmètre défini par un champ
katana -u https://example.com -fs rdn
```

### Stratégie d’exploration

```bash
# En profondeur (par défaut)
katana -u https://example.com -cs depth-first

# En largeur
katana -u https://example.com -cs breadth-first
```

### Concurrence

```bash
# Requêtes simultanées (valeur par défaut : 10)
katana -u https://example.com -c 50

# Limite de débit (requêtes par seconde)
katana -u https://example.com -rl 100

# Parallélisme
katana -u https://example.com -p 10
```

### Durée d’exploration

```bash
# Définir le délai d’attente (secondes)
katana -u https://example.com -timeout 30

# Durée maximale de l’exploration
katana -u https://example.com -ct 300
```

### Remplissage automatique des formulaires

```bash
# Activer le remplissage des formulaires
katana -u https://example.com -aff

# Avec des valeurs personnalisées
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Mode sans interface graphique (headless)

### Activer le navigateur sans interface graphique

```bash
# Activer Chrome sans interface graphique
katana -u https://example.com -headless

# Mode headless avec profondeur définie
katana -u https://example.com -headless -d 3
```

### Options du mode headless

```bash
# Chemin Chrome personnalisé (libellé source ; voir la précision)
katana -u https://example.com -headless -system-chrome

# Sans bac à sable (exemple source pour root)
katana -u https://example.com -headless -no-sandbox

# Capturer les pages (exemple source ; voir la précision)
katana -u https://example.com -headless -screenshot
```

### Exploration JavaScript

```bash
# Attendre le chargement de JavaScript (libellé source en ms ; voir la précision)
katana -u https://example.com -headless -timeout 5

# Explorer les points de terminaison JavaScript
katana -u https://example.com -js-crawl
```

---

## 📤 Sortie et filtrage

### Formats de sortie

```bash
# Sortie standard dans un fichier
katana -u https://example.com -o results.txt

# Sortie JSON
katana -u https://example.com -jsonl -o results.json

# Sans couleur
katana -u https://example.com -nc
```

### Champs affichés

```bash
# Afficher certains champs
katana -u https://example.com -f url,method,status

# Champs disponibles :
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filtrer par extension

```bash
# Retenir les extensions
katana -u https://example.com -em js,php,asp

# Exclure les extensions
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filtrer par code de statut

```bash
# Retenir les codes de statut
katana -u https://example.com -mc 200,301,302

# Exclure les codes de statut
katana -u https://example.com -fc 404,500
```

### En-têtes personnalisés

```bash
# Ajouter un en-tête
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Plusieurs en-têtes
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Prise en charge des proxys

```bash
# Proxy HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# Pour Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Référence rapide

### Commandes de base

| Commande | Description |
|---------|-------------|
| `katana -u URL` | Explorer une URL |
| `katana -list file.txt` | Explorer les URL d’un fichier |
| `katana -u URL -d 5` | Fixer la profondeur à 5 |
| `katana -u URL -headless` | Activer le mode headless |
| `katana -u URL -o out.txt` | Enregistrer les résultats |

### Options de périmètre

| Option | Description |
|------|-------------|
| `-fs dn` | Même domaine |
| `-fs rdn` | Domaine racine |
| `-fs fqdn` | Domaine complet |
| `-cs depth-first` | Exploration en profondeur |
| `-cs breadth-first` | Exploration en largeur |

### Options de performances

| Option | Description |
|------|-------------|
| `-c 50` | Concurrence |
| `-p 10` | Parallélisme |
| `-rl 100` | Limite de débit |
| `-timeout 30` | Délai d’attente d’une requête |

### Options de filtrage

| Option | Description |
|------|-------------|
| `-em js,php` | Retenir les extensions |
| `-ef png,jpg` | Exclure les extensions |
| `-mc 200` | Retenir les codes de statut |
| `-fc 404` | Exclure les codes de statut |

---

## 🎯 Déroulement des recherches en bug bounty

### Exploration élémentaire

```bash
# Exploration rapide
katana -u https://target.com -d 3 -o urls.txt
```

### Exploration approfondie

```bash
# Exploration approfondie avec JavaScript
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Découverte de paramètres

```bash
# Rechercher les URL comportant des paramètres
katana -u https://target.com -d 3 | grep "?" | sort -u

# Avec grep pour certains paramètres
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Combinaison avec d’autres outils

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Pipeline complet
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Exploration avec authentification

```bash
# Avec un cookie de session
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Ressources

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Fiches connexes
- [Méthodologie de recherche en bug bounty](../../Bug-Bounty-Methodology/translations/README.fr.md)
- [httpx](../../httpx/translations/README.fr.md)
- [Nuclei](../../Nuclei/translations/README.fr.md)
- [GAU](../../GAU/translations/README.fr.md)

---

<p align="center">
  <b>⚔️ Explorez le Web !</b><br>
  <i>Katana — robot d’exploration web de nouvelle génération</i>
</p>
