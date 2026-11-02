# 🔗 GAU — collecte d’URL avec Get All URLs

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Fast URL Harvesting
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Précisions sur les options :** l’[aide officielle de GAU](https://github.com/lc/gau#usage) définit `--subs` comme l’inclusion des sous-domaines, pas un mode silencieux. `--blacklist` exclut des extensions, pas des fournisseurs. `--mt` et `--ft` sélectionnent ou excluent des types MIME, pas des expressions régulières d’extensions. Les exemples originaux sont conservés.
>
> **Lecture des résultats :** les URL connues et leurs métadonnées ne confirment pas leur état actuel. Les pipelines utilisant httpx contactent les cibles ; les motifs GF et une réponse HTTP 200 ne prouvent pas une vulnérabilité. Les options, fournisseurs et valeurs par défaut dépendent de la version et de la configuration. Le dessin ASCII et les clés/valeurs TOML restent ceux du document source.

## 📋 Table des matières

- [Qu’est-ce que GAU](#-quest-ce-que-gau)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Fournisseurs](#-fournisseurs)
- [Filtrage](#-filtrage)
- [Intégration](#-intégration)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce que GAU

**GAU** (Get All URLs) récupère des URL connues auprès de plusieurs sources :

- 🔍 **Wayback Machine** — URL historiques
- 🌐 **Common Crawl** — Données d’exploration du Web
- 📡 **AlienVault OTX** — URL issues du renseignement sur les menaces
- 🔒 **URLScan.io** — URL analysées

### Pourquoi GAU ?

| Avantage | Description |
|---------|-------------|
| **Reconnaissance passive** | Sans contact direct avec la cible pendant la collecte |
| **Données historiques** | Retrouver des points de terminaison anciens ou oubliés |
| **Rapide** | Collecte parallèle |
| **Plusieurs sources** | Résultats issus de sources complémentaires |

---

## 🚀 Installation

```bash
# Installation avec Go (recommandée)
go install github.com/lc/gau/v2/cmd/gau@latest

# Depuis les sources
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Vérifier
gau --version
```

---

## 📖 Utilisation de base

### Un seul domaine

```bash
# Utilisation de base
gau example.com

# Avec un fichier de sortie
gau example.com -o urls.txt

# Mode silencieux sans bannière (libellé source ; voir la précision)
gau --subs example.com
```

### Plusieurs domaines

```bash
# Depuis l’entrée standard
echo "example.com" | gau

# Plusieurs domaines
cat domains.txt | gau

# Depuis un fichier
gau --threads 5 < domains.txt
```

### Inclure les sous-domaines

```bash
# Inclure les sous-domaines dans les résultats
gau --subs example.com
```

### Parallélisme

```bash
# Régler le nombre de threads (valeur par défaut indiquée : 1)
gau --threads 5 example.com

# Adapter le parallélisme aux limites de débit
gau --threads 2 example.com
```

---

## 🌐 Fournisseurs

### Fournisseurs disponibles

| Fournisseur | Description | Par défaut |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Sélectionner les fournisseurs

```bash
# Utiliser uniquement certains fournisseurs
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Exclure un fournisseur (libellé source ; voir la précision)
gau --blacklist commoncrawl example.com
```

### Configuration des fournisseurs

```bash
# Emplacement du fichier de configuration
~/.gau.toml

# Exemple de configuration
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filtrage

### Filtrer selon le code de statut

```bash
# Conserver les URL associées à un statut 200
gau --mc 200 example.com

# Plusieurs codes de statut
gau --mc 200,301,302 example.com

# Exclure certains codes
gau --fc 404,500 example.com
```

### Filtrer selon l’extension

```bash
# Retenir des extensions (interprétation source ; voir la précision)
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Exclure des extensions (interprétation source ; voir la précision)
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

### Filtrer selon le type MIME

```bash
# Filtrer selon le type de contenu
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Format de sortie

```bash
# Sortie JSON
gau --json example.com

# Mode détaillé
gau --verbose example.com
```

---

## 🔗 Intégration

### Avec httpx

```bash
# Vérifier les URL accessibles
gau example.com | httpx -silent

# Avec les codes de statut
gau example.com | httpx -sc -title
```

### Avec grep et l’extraction de paramètres

```bash
# Rechercher les URL contenant des paramètres
gau example.com | grep "="

# Rechercher des motifs précis
gau example.com | grep -E "\.php\?|\.asp\?"

# Motifs GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Avec urldedupe

```bash
# Supprimer les doublons
gau example.com | urldedupe

# Autre possibilité : sort -u
gau example.com | sort -u
```

### Avec uro

```bash
# Déduplication intelligente des URL
gau example.com | uro
```

### Pipeline complet

```bash
# Pipeline complet de collecte d’URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Référence rapide

### Commandes de base

| Commande | Description |
|---------|-------------|
| `gau domain.com` | Récupérer les URL du domaine |
| `gau --subs domain.com` | Inclure les sous-domaines |
| `gau -o urls.txt domain.com` | Écrire dans un fichier |
| `gau --threads 5 domain.com` | Utiliser plusieurs threads |

### Options de filtrage

| Option | Description |
|------|-------------|
| `--mc 200` | Retenir un code de statut |
| `--fc 404` | Exclure un code de statut |
| `--mt "regex"` | Retenir une regex selon la source (option MIME ; voir la précision) |
| `--ft "regex"` | Exclure une regex selon la source (option MIME ; voir la précision) |
| `--json` | Sortie JSON |

### Options des fournisseurs

| Option | Description |
|------|-------------|
| `--providers wayback,otx` | Utiliser certains fournisseurs |
| `--blacklist commoncrawl` | Exclure un fournisseur selon la source (option d’extensions ; voir la précision) |

### Commandes utiles en une ligne

```bash
# Récupérer tous les fichiers JS
gau example.com | grep -E "\.js$" | sort -u

# Rechercher les points de terminaison d’API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Rechercher des paramètres
gau example.com | grep "?" | grep "=" | sort -u

# Rechercher des candidats pour des tests SQLi
gau example.com | gf sqli

# Rechercher des candidats pour des tests XSS
gau example.com | gf xss

# URL accessibles contenant des paramètres
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Déroulement d’une recherche en bug bounty

```bash
# 1. Collecter toutes les URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. Filtrer les URL intéressantes
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Vérifier leur accessibilité
cat params.txt | httpx -silent > live_params.txt

# 4. Rechercher des pistes de vulnérabilités
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Tester
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

## 📚 Ressources

- [GAU GitHub](https://github.com/lc/gau)
- [GF Patterns](https://github.com/1ndianl33t/Gf-Patterns)

### Fiches connexes
- [Méthodologie de recherche en bug bounty](../../Bug-Bounty-Methodology/translations/README.fr.md)
- [Subfinder](../../Subfinder/translations/README.fr.md)
- [httpx](../../httpx/translations/README.fr.md)

---

<p align="center">
  <b>🔗 Collectez toutes les URL !</b><br>
  <i>GAU — Découverte passive d’URL</i>
</p>
