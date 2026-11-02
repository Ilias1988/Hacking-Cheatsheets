# 🔍 Arjun — découverte de paramètres cachés

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         HTTP Parameter Discovery Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

> **Versions et options :** les commandes anglaises sont conservées. Le [parseur officiel consulté](https://github.com/s0md3v/Arjun/blob/master/arjun/__main__.py) documente `-d` pour le délai et `-T` pour le timeout ; les formes sources `--delay`, `--timeout`, `--stdin`, `--import`, `--burp` et `-H` n’y figurent pas. `-i` importe notamment des fichiers texte, requêtes brutes ou exports Burp. `-o` est un alias de la sortie JSON, même si le nom du fichier se termine par `.txt`. Les valeurs par défaut varient selon la version.
>
> **Mode passif et en-têtes :** `--passive` collecte des noms depuis des sources historiques, puis leur présence peut être testée sur les cibles ; il ne désigne pas simplement une lecture de fichiers JavaScript. Les en-têtes multiples sont séparés par des sauts de ligne dans la [documentation officielle](https://github.com/s0md3v/Arjun/wiki/Usage), pas par un point-virgule entre deux noms d’en-têtes. Le mode stable et le débit réduit ne garantissent pas l’absence de blocage.
>
> **Exemple JSON :** la forme illustrative `params` avec objets `name`/`reason` ne correspond pas à la liste de noms produite par le code officiel consulté ; l’extraction `jq` doit donc correspondre au schéma réel. Les chaînes JSON, valeurs d’exemple et dessin ASCII restent ceux du document source. Découvrir un paramètre ne prouve pas une vulnérabilité.

## 📋 Table des matières

- [Qu’est-ce qu’Arjun](#-quest-ce-quarjun)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Modes de découverte](#-modes-de-découverte)
- [Options avancées](#-options-avancées)
- [Options de sortie](#-options-de-sortie)
- [Référence rapide](#-référence-rapide)

---

## 🎯 Qu’est-ce qu’Arjun

**Arjun** recherche les paramètres HTTP cachés des applications web :

- 🔍 **Découverte de paramètres** — Rechercher des paramètres GET/POST cachés
- 🎯 **Prise en charge de JSON** — Découvrir des paramètres JSON
- 📡 **Plusieurs méthodes** — Modes GET, POST et JSON
- ⚡ **Rapide** — Requêtes exécutées en parallèle

### Pourquoi Arjun ?

| Fonctionnalité | Avantage |
|---------|---------|
| Paramètres cachés | Repérer des paramètres non référencés, selon l’objectif de l’outil |
| Plusieurs formats | GET, POST, JSON |
| Mode passif | Examiner des fichiers JavaScript selon la description source |
| Import depuis Burp | S’intégrer au déroulement des tests existant |

---

## 🚀 Installation

```bash
# Installation avec pip (recommandée)
pip3 install arjun

# Depuis les sources
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Vérifier
arjun --help
```

---

## 📖 Utilisation de base

### Une seule URL

```bash
# Découverte élémentaire de paramètres
arjun -u https://example.com/endpoint

# Avec un fichier de sortie
arjun -u https://example.com/endpoint -o results.txt
```

### Plusieurs URL

```bash
# Depuis un fichier
arjun -i urls.txt

# Depuis l’entrée standard
cat urls.txt | arjun --stdin
```

### Choisir la méthode HTTP

```bash
# Paramètres GET (par défaut)
arjun -u https://example.com/search -m GET

# Paramètres POST
arjun -u https://example.com/api -m POST

# Corps JSON
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Modes de découverte

### Paramètres GET

```bash
# Découvrir des paramètres GET
arjun -u https://example.com/search?known=value -m GET

# Exemple de sortie :
# [param] id
# [param] user
# [param] debug
```

### Paramètres POST

```bash
# Découvrir les paramètres d’un formulaire POST
arjun -u https://example.com/login -m POST

# Avec des données existantes
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### Paramètres JSON

```bash
# Découvrir les paramètres d’un corps JSON
arjun -u https://example.com/api/v1/users -m JSON

# Avec un objet JSON de base
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Découverte combinée

```bash
# Toutes les méthodes
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-options-avancées"></a>
## ⚙️ Options avancées

### Liste de mots personnalisée

```bash
# Utiliser une liste de mots personnalisée
arjun -u https://example.com -w /path/to/wordlist.txt

# Utiliser les noms de paramètres de SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Parallélisme et limitation du débit

```bash
# Définir les threads (valeur source par défaut : 2)
arjun -u https://example.com -t 10

# Limite de débit (requêtes par seconde)
arjun -u https://example.com --rate-limit 10

# Délai entre les requêtes (secondes)
arjun -u https://example.com --delay 1
```

### En-têtes personnalisés

```bash
# Ajouter un en-tête
arjun -u https://example.com --headers "Authorization: Bearer token"

# Plusieurs en-têtes
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Depuis un fichier
arjun -u https://example.com -H headers.txt
```

### Inclure des paramètres connus

```bash
# Inclure des paramètres connus
arjun -u https://example.com/search --include 'q=test&page=1'

# Pour POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Mode passif

```bash
# Extraire depuis des fichiers JavaScript (description source ; voir la précision)
arjun -u https://example.com --passive

# Recherche de noms de paramètres dans les fichiers JS selon la source
```

### Désactiver les redirections

```bash
# Ne pas suivre les redirections
arjun -u https://example.com --disable-redirects
```

### Délai d’attente

```bash
# Définir le délai d’attente (secondes)
arjun -u https://example.com --timeout 15
```

### Mode stable

```bash
# Privilégier la stabilité (plus lent)
arjun -u https://example.com --stable
```

---

## 📤 Options de sortie

### Formats de sortie

```bash
# Sortie texte (présentée comme celle par défaut dans la source)
arjun -u https://example.com -oT results.txt

# Sortie JSON
arjun -u https://example.com -oJ results.json

# Les deux formats
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Exemple de sortie JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Import depuis Burp

```bash
# Importer des requêtes Burp
arjun --import burp_export.xml

# Traiter un fichier Burp
arjun -i burp_requests.txt --burp
```

---

## 📊 Référence rapide

### Commandes de base

| Commande | Description |
|---------|-------------|
| `arjun -u URL` | Découvrir des paramètres |
| `arjun -u URL -m POST` | Méthode POST |
| `arjun -u URL -m JSON` | Corps JSON |
| `arjun -i file.txt` | Plusieurs URL |
| `arjun -u URL -oJ out.json` | Sortie JSON |

### Options de méthode

| Option | Description |
|------|-------------|
| `-m GET` | Paramètres GET |
| `-m POST` | Données de formulaire POST |
| `-m JSON` | Corps JSON |
| `-m GET,POST` | Plusieurs méthodes |

### Options de performances

| Option | Description |
|------|-------------|
| `-t 10` | Threads (tâches simultanées) |
| `--rate-limit 10` | Requêtes par seconde |
| `--delay 1` | Délai entre les requêtes |
| `--timeout 15` | Délai d’attente d’une requête |

### Options de découverte

| Option | Description |
|------|-------------|
| `-w wordlist.txt` | Liste de mots personnalisée |
| `--include 'a=b'` | Inclure des paramètres |
| `--passive` | Extraction depuis JS selon la source ; voir la précision |
| `--stable` | Mode privilégiant la stabilité |

---

## 🎯 Déroulements de recherche en bug bounty

### Découverte élémentaire

```bash
# Découverte rapide de paramètres
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Avec une liste de mots personnalisée

```bash
# Découverte approfondie
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Tests authentifiés

```bash
# Avec une session
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Découverte sur plusieurs cibles

```bash
# Plusieurs points de terminaison
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Ou avec un fichier
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Intégration dans un pipeline

```bash
# Rechercher des points de terminaison, puis découvrir leurs paramètres
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Tester les paramètres découverts

```bash
# Après la découverte, rechercher des vulnérabilités
# 1. Découvrir les paramètres
arjun -u https://target.com/search -oJ params.json

# 2. Extraire les noms des paramètres
cat params.json | jq -r '.[].params[].name'

# 3. Tester avec ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Conseils pratiques

1. **Commencer par la collecte passive** — Examiner d’abord les fichiers JS selon le conseil source ; voir les limites du mode passif.
2. **Choisir de bonnes listes** — Les noms de paramètres de Burp sont recommandés dans la source.
3. **Limiter le débit** — Réduire les risques de blocage, sans garantie de les éviter.
4. **Tester plusieurs méthodes** — GET, POST et JSON.
5. **Examiner les en-têtes** — X-Custom-Param, X-Debug, etc.
6. **Tester avec authentification** — Examiner les espaces accessibles après connexion.

### Paramètres cachés courants

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Ressources

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Noms de paramètres Burp](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Fiches connexes
- [Méthodologie de bug bounty](../../Bug-Bounty-Methodology/translations/README.fr.md)
- [ffuf](../../ffuf/translations/README.fr.md)
- [Burp Suite](../../Burp-Suite/translations/README.fr.md)

---

<p align="center">
  <b>🔍 Découvrez les paramètres cachés !</b><br>
  <i>Arjun — À la recherche des paramètres</i>
</p>
