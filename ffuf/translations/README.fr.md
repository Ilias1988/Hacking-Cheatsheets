# 🚀 ffuf — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Un outil rapide de fuzzing web écrit en Go</b>
</p>

---

> **Précisions sur les options :** `-r` suit les redirections ; il ne configure pas un résolveur. `-se` arrête sur des erreurs parasites, tandis que `-sf` vise un taux supérieur à 95 % de réponses 403. `-ft` et `-mt` concernent le délai jusqu’au premier octet et attendent une comparaison avec `<` ou `>` : les formes sources `5000` sont conservées. `-k` ne figure pas dans l’aide officielle consultée. Voir l’[aide officielle de ffuf](https://github.com/ffuf/ffuf#usage).
>
> **Versions et interprétation :** les archives `v2.x.x` sont des emplacements réservés. Les comparaisons avec d’autres outils reflètent la source, pas un classement universel actuel. Modifier l’en-tête `Host` recherche des hôtes virtuels sur le serveur joint ; cela ne constitue pas une énumération DNS pure. Avec plusieurs listes, `clusterbomb` produit toutes les combinaisons plutôt que de préserver des paires d’identifiants. Un faible débit ne garantit pas un contournement de WAF ni l’absence de détection. Les commandes et le dessin ASCII restent ceux de la source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Fuzzing des répertoires](#-fuzzing-des-répertoires)
- [Fuzzing des sous-domaines](#-fuzzing-des-sous-domaines)
- [Fuzzing des paramètres](#-fuzzing-des-paramètres)
- [Fuzzing des données POST](#-fuzzing-des-données-post)
- [Filtrage et sélection des réponses](#-filtrage-et-sélection-des-réponses)
- [Fonctionnalités avancées](#-fonctionnalités-avancées)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**ffuf** (Fuzz Faster U Fool) est un outil rapide de fuzzing web, écrit en Go. Il permet notamment de rechercher des répertoires par force brute, de découvrir des hôtes virtuels et de tester des paramètres dans les recherches de bug bounty.

### Principales fonctionnalités

|Fonctionnalité|Description|
|---------|-------------|
|**Vitesse**|Très rapide, développé en Go|
|**Flexible**|Tester n’importe quelle partie d’une requête HTTP|
|**Filtrage**|Plusieurs options de filtrage et de sélection|
|**Récursivité**|Prise en charge de l’exploration récursive|
|**Sortie**|Plusieurs formats de sortie|
|**Simple**|Syntaxe simple et intuitive|

### ffuf et les autres outils

|Fonctionnalité|ffuf|Gobuster|dirb|
|---------|------|----------|------|
|**Vitesse**|⭐⭐⭐⭐⭐|⭐⭐⭐⭐|⭐⭐|
|**Souplesse**|⭐⭐⭐⭐⭐|⭐⭐⭐|⭐⭐|
|**Fuzzing POST**|✅ Oui|❌ Non|❌ Non|
|**Récursivité**|✅ Oui|✅ Oui|✅ Oui|
|**Filtres**|Nombreux|Limités|Limités|

---

## 📥 Installation

### Avec Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Téléchargement d’un binaire
```bash
# Télécharger depuis les versions publiées
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Vérifier l’installation
```bash
ffuf -V
ffuf -h
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### Le mot-clé FUZZ

Le mot-clé **FUZZ** indique à ffuf où insérer les entrées de la liste de mots.

```bash
# Dans le chemin de l’URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# Dans le sous-domaine
ffuf -u https://FUZZ.target.com -w wordlist.txt

# Dans la valeur d’un paramètre
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# Dans les données POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Options essentielles

|Option|Description|
|--------|-------------|
|`-u`|URL cible contenant le mot-clé FUZZ|
|`-w`|Chemin de la liste de mots|
|`-o`|Fichier de sortie|
|`-of`|Format de sortie (json, csv, html)|
|`-t`|Nombre de threads (valeur par défaut indiquée : 40)|
|`-p`|Délai entre les requêtes|
|`-r`|Suivre les redirections|
|`-v`|Sortie détaillée|
|`-s`|Mode silencieux|

---

## 📁 Fuzzing des répertoires

### Recherche élémentaire de répertoires par force brute

```bash
# Fuzzing élémentaire des répertoires
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Avec des extensions de fichiers
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Plusieurs extensions
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Exploration récursive

```bash
# Activer la récursivité
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Profondeur de récursion
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Récursivité avec une stratégie
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filtrer selon le code de statut

```bash
# Retenir uniquement les réponses 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Retenir plusieurs codes
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Exclure les réponses 404 (libellé source sur le comportement par défaut)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Tout retenir, puis exclure les réponses 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Listes de mots courantes

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

## 🌐 Fuzzing des sous-domaines

### Découverte des hôtes virtuels

```bash
# Fuzzing élémentaire des sous-domaines
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filtrer selon la taille de réponse pour écarter la page par défaut
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Avec une liste de mots précise
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Fuzzing des sous-domaines DNS

```bash
# Sous-domaine dans l’URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Avec un résolveur (libellé source ; voir la précision sur -r)
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Listes de mots courantes pour les sous-domaines

```bash
# Listes DNS de SecLists
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

## 🔍 Fuzzing des paramètres

### Fuzzing des paramètres GET

```bash
# Tester le nom du paramètre
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Tester la valeur du paramètre
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Plusieurs paramètres
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Découverte des paramètres

```bash
# Rechercher des paramètres cachés
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 Fuzzing des données POST

### Fuzzing POST élémentaire

```bash
# POST contenant des données de formulaire
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST avec un type de contenu
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing des données JSON en POST

```bash
# Données JSON en POST
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Plusieurs listes de mots (credential stuffing dans la source)

```bash
# Tester les noms d’utilisateur et les mots de passe
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Mode clusterbomb : toutes les combinaisons
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtrage et sélection des réponses

### Options d’exclusion

|Option|Description|Exemple|
|--------|-------------|---------|
|`-fc`|Exclure selon le code de statut|`-fc 404,403`|
|`-fs`|Exclure selon la taille de réponse|`-fs 1234`|
|`-fw`|Exclure selon le nombre de mots|`-fw 100`|
|`-fl`|Exclure selon le nombre de lignes|`-fl 50`|
|`-ft`|Exclure selon le temps (ms)|`-ft 5000`|
|`-fr`|Exclure selon une expression régulière|`-fr "error"`|

### Options de sélection

|Option|Description|Exemple|
|--------|-------------|---------|
|`-mc`|Retenir les codes de statut|`-mc 200,301`|
|`-ms`|Retenir selon la taille de réponse|`-ms 1234`|
|`-mw`|Retenir selon le nombre de mots|`-mw 100`|
|`-ml`|Retenir selon le nombre de lignes|`-ml 50`|
|`-mt`|Retenir selon le temps (ms)|`-mt 5000`|
|`-mr`|Retenir selon une expression régulière|`-mr "success"`|

### Exemples de filtrage

```bash
# Exclure les réponses 404 et 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filtrer selon la taille pour écarter la page par défaut
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filtrer selon le nombre de mots
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Tout retenir, puis exclure des réponses précises
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Étalonnage automatique

```bash
# Étalonner automatiquement les filtres
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Étalonnage avec une chaîne précise
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Fonctionnalités avancées

### Limitation du débit

```bash
# Requêtes par seconde
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Délai entre les requêtes
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Délai aléatoire (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Threads

```bash
# Définir le nombre de threads (valeur par défaut indiquée : 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Réduire le parallélisme pour limiter le trafic
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### En-têtes

```bash
# En-tête personnalisé
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Plusieurs en-têtes
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# Proxy HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Sortie

```bash
# Sortie JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Sortie CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Sortie HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Tous les formats
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Délais d’attente et nouvelles tentatives

```bash
# Délai d’attente d’une requête
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Arrêter en cas de 403 (libellé source ; voir la précision sur -se)
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignorer les erreurs SSL
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

## 🎬 Exemples pratiques

### Exemple 1 : recherche élémentaire de répertoires
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Exemple 2 : rechercher des fichiers PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Exemple 3 : énumération des sous-domaines
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Exemple 4 : découverte des points de terminaison d’API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Exemple 5 : découverte des paramètres
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Exemple 6 : force brute sur la connexion
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Exemple 7 : avec un proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Exemple 8 : exploration récursive
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Exemple 9 : analyse avec débit limité
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Exemple 10 : analyse complète de bug bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Référence rapide

### Commandes essentielles

|Tâche|Commande|
|------|---------|
|Recherche de répertoires|`ffuf -u URL/FUZZ -w wordlist.txt`|
|Avec des extensions|`ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html`|
|Recherche de sous-domaines|`ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt`|
|Fuzzing POST|`ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt`|
|Exclure les réponses 404|`ffuf -u URL/FUZZ -w wordlist.txt -fc 404`|
|Filtrer selon la taille|`ffuf -u URL/FUZZ -w wordlist.txt -fs 1234`|
|Étalonner automatiquement|`ffuf -u URL/FUZZ -w wordlist.txt -ac`|
|Enregistrer la sortie|`ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json`|

### Options courantes

|Option|Description|
|--------|-------------|
|`-u`|URL contenant FUZZ|
|`-w`|Liste de mots|
|`-e`|Extensions|
|`-t`|Threads|
|`-mc`|Codes retenus|
|`-fc`|Codes exclus|
|`-fs`|Taille exclue|
|`-ac`|Étalonner automatiquement|
|`-r`|Suivre les redirections|
|`-o`|Fichier de sortie|

---

## 💡 Conseils et bonnes pratiques

### Conseils pour le bug bounty

1. **Commencer par un étalonnage automatique**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Utiliser plusieurs extensions**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Essayer les extensions courantes de sauvegarde**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Limiter le débit pour le contournement d’un WAF (libellé source)**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Conseils de performance

```bash
# Débit élevé, dans le cas présenté sans WAF
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Débit faible et espacement des requêtes
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** ffuf est un outil puissant de fuzzing réservé aux **tests autorisés**.
> 
> - ✅ L’utiliser sur les programmes pour lesquels vous disposez d’une autorisation
> - ✅ L’utiliser dans des programmes de bug bounty autorisés
> - ✅ Respecter les limites de débit et le périmètre
> - ❌ Ne jamais effectuer de fuzzing sans autorisation
> - ❌ Ne pas provoquer de déni de service
> 
> **Les analyses sans autorisation sont illégales.**

---

## 📚 Ressources

### Ressources officielles
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Listes de mots
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

### Fiches connexes
- [Nuclei](../../Nuclei/translations/README.fr.md)
- [Gobuster](../../Gobuster/translations/README.fr.md)
- [Nmap](../../Nmap/translations/README.fr.md)

---

<p align="center">
  <b>🚀 Testez plus vite, découvrez davantage !</b><br>
  <i>Un outil de choix pour la recherche en bug bounty</i>
</p>
