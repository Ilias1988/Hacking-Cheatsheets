# ⚡ OWASP ZAP — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy - Free Security Testing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 Un scanner libre de référence pour la sécurité des applications web</b>
</p>

---

> **Repères de version :** le nom « OWASP ZAP », l’association à OWASP, les comparaisons commerciales et les prix décrivent le document source. ZAP a [quitté OWASP et changé de nom en 2023](https://www.zaproxy.org/blog/2023-08-01-zap-is-joining-the-software-security-project/) ; les menus et extensions peuvent aussi varier selon la version. Le montant `$449+` est conservé comme exemple historique, pas comme tarif actuel. Le texte du dessin ASCII est conservé.
>
> **Portée des exemples :** les règles passives examinent le trafic sans créer elles-mêmes de requêtes supplémentaires. Le [script Baseline](https://www.zaproxy.org/docs/docker/baseline-scan/) lance néanmoins un spider, puis attend l’analyse passive : « passif » ne signifie donc pas absence de trafic. Pour conserver les rapports Docker sur l’hôte, la documentation demande un montage de `/zap/wrk/`, absent des exemples simplifiés ci-dessous.
>
> **Automatisation :** les appels d’exploration et d’analyse de l’API sont asynchrones ; le petit exemple Python ne comporte pas l’attente des étapes ni de la fin des analyses. Consultez la [documentation officielle de l’API](https://www.zaproxy.org/docs/api/) avant de traiter cet extrait comme un processus complet. Les URL, commandes et valeurs YAML sont conservées à l’identique.


## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Vue d’ensemble de l’interface](#-vue-densemble-de-linterface)
- [Configuration du proxy](#-configuration-du-proxy)
- [Tests manuels](#-tests-manuels)
- [Exploration et robots d’indexation](#-exploration-et-robots-dindexation)
- [Analyse active](#-analyse-active)
- [Analyse passive](#-analyse-passive)
- [Contextes et sessions](#-contextes-et-sessions)
- [Outil de fuzzing (Fuzzer)](#-outil-de-fuzzing-fuzzer)
- [API et automatisation](#-api-et-automatisation)
- [Ligne de commande](#-ligne-de-commande)
- [Extensions](#-extensions)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**OWASP ZAP** (Zed Attack Proxy) est un outil libre et gratuit de test d’intrusion, historiquement associé à OWASP. Il permet de rechercher des vulnérabilités de sécurité dans les applications web.

### Pourquoi ZAP ?

| Fonctionnalité | Description |
|---------|-------------|
| **Libre et gratuit** | Aucun coût de licence, développement communautaire |
| **Facile à utiliser** | Accessible aux débutants, avec des fonctionnalités avancées |
| **Analyses automatisées** | Exploration, analyses actives et passives |
| **Tests manuels** | Proxy d’interception et outil de fuzzing |
| **Prise en charge des API** | API REST complète pour l’automatisation |
| **Multiplateforme** | Windows, Linux, macOS, Docker |
| **Extensible** | Place de marché proposant plus de 100 extensions selon la source |

### ZAP et Burp Suite

| Fonctionnalité | ZAP | Burp Suite |
|---------|-----|------------|
| **Prix** | Gratuit | $449+/an |
| **Code source ouvert** | ✅ Oui | ❌ Non |
| **Analyses automatisées** | ✅ Complètes | Édition Pro uniquement |
| **Accès à l’API** | ✅ Gratuit | Édition Pro uniquement |
| **Développement actif** | ✅ Oui | ✅ Oui |
| **Communauté** | OWASP | PortSwigger |

---

## 📥 Installation

### Windows
```bash
# Télécharger depuis le site officiel
https://www.zaproxy.org/download/

# Ou utiliser Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (préinstallé)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Télécharger le fichier DMG depuis le site officiel
```

### Docker
```bash
# Télécharger la dernière image
docker pull ghcr.io/zaproxy/zaproxy:stable

# Démarrer ZAP en mode démon
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Démarrer avec une interface graphique (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Vérifier l’installation
```bash
# Démarrer ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-vue-densemble-de-linterface"></a>
## 🖥️ Vue d’ensemble de l’interface

### Panneaux principaux

| Panneau | Description |
|-------|-------------|
| **Sites Tree** | Arborescence des sites visités |
| **History** | Ensemble des requêtes et réponses |
| **Alerts** | Vulnérabilités signalées |
| **Active Scan** | Progression de l’analyse en cours |
| **Spider** | URL explorées |
| **Search** | Recherche dans l’ensemble des données |
| **Request/Response** | Détails des requêtes et réponses |

### Icônes de la barre d’outils

| Icône | Fonction |
|------|----------|
| 🌐 | Analyse depuis Quick Start |
| 🔍 | Exploration avec Spider |
| ⚡ | Analyse active |
| 🛑 | Pause et interception (Break) |
| 📊 | Rapports |
| ⚙️ | Options |

### Modes

| Mode | Description |
|------|-------------|
| **Safe** | Désactive les opérations considérées comme dangereuses |
| **Protected** | Limite les analyses aux cibles dans le périmètre |
| **Standard** | Analyses complètes, mode présenté comme celui par défaut |
| **ATTACK** | Analyses agressives |

---

## 🔌 Configuration du proxy

### Configuration du navigateur

**Proxy ZAP par défaut :**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Configuration de Firefox
```
1. Ouvrir Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Cocher "Also use this proxy for HTTPS"
4. Ou utiliser l’extension FoxyProxy
```

### Importer le certificat ZAP
```
1. Dans le navigateur, ouvrir : http://zap
2. Cliquer sur "Download ZAP Root CA Certificate"
3. Importer le certificat dans le navigateur :
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Configurer les options du proxy ZAP
```
Tools → Options → Local Proxies

Paramètres :
- Address: localhost
- Port: 8080
- Behind NAT : activer si nécessaire
- Remove unsupported encodings : activer
```

### Proxy en amont (chaîne de proxys)
```
Tools → Options → Network → Connection

Utiliser une chaîne de proxys :
- Address: corporate-proxy.com
- Port: 8080
- Configurer l’authentification si nécessaire
```

---

## 🔧 Tests manuels

### Interception des requêtes (Break)

```
1. Cliquer sur le bouton vert "Break" (🛑) dans la barre d’outils
2. Parcourir l’application
3. Modifier la requête interceptée
4. Cliquer sur "Submit and Continue" ou "Drop"

Options d’interception :
- Interrompre toutes les requêtes
- Interrompre toutes les réponses
- Interrompre selon un motif d’URL précis
```

### Éditeur de requêtes

```
Faire un clic droit sur la requête → Open/Resend with Request Editor

Fonctionnalités :
- Modifier n’importe quelle partie de la requête
- Ajouter ou supprimer des en-têtes
- Changer la méthode HTTP
- Envoyer la requête et examiner la réponse
```

### Requête manuelle
```
Tools → Manual Request Editor

Ou : Ctrl+M

Créer des requêtes personnalisées de toutes pièces
```

### Encodage, décodage et empreintes
```
Tools → Encode/Decode/Hash

Fonctions prises en charge :
- Encodage et décodage d’URL
- Encodage et décodage Base64
- Encodage et décodage HTML
- Encodage et décodage hexadécimal
- Calcul d’empreintes MD5, SHA1 et SHA256
```

### Fonction de recherche
```
Onglet Search → saisir le motif recherché

Rechercher dans :
- Les URL
- Les requêtes
- Les réponses
- Les en-têtes
- L’ensemble des données
```

---

<a id="-exploration-et-robots-dindexation"></a>
## 🕷️ Exploration et robots d’indexation

### Robot d’exploration classique (Spider)

```
Faire un clic droit sur la cible → Spider → Spider...

Ou : Ctrl+Alt+S

Options :
- Profondeur maximale d’exploration
- Nombre maximal d’éléments enfants
- Durée maximale
- Traitement des paramètres
```

### Options du robot classique
```
Tools → Options → Spider

Paramètres :
- Max Depth : 5-10 (valeur par défaut indiquée : 5)
- Max Children: 10
- Max Duration : 0 (sans limite)
- Thread Count: 5
- Handle forms : Yes (traiter les formulaires)
- Post forms : Yes (envoyer les formulaires)
```

### Robot d’exploration AJAX (AJAX Spider)

```
Pour les applications utilisant beaucoup JavaScript :

Faire un clic droit sur la cible → AJAX Spider → AJAX Spider...

Nécessite un navigateur :
- Firefox (recommandé)
- Chrome
- HtmlUnit (sans interface graphique)
```

### Options du robot AJAX
```
Tools → Options → AJAX Spider

Paramètres :
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States : 0 (sans limite)
- Max Duration : 60 minutes
- Cliquer sur les éléments par défaut
- Cliquer sur les éléments possédant notamment un attribut onclick
```

---

## ⚡ Analyse active

### Démarrer une analyse active

```
Faire un clic droit sur la cible → Active Scan...

Ou : Ctrl+Alt+A

Configurer :
- Le point de départ (Starting Point)
- Le contexte (facultatif)
- L’utilisateur pour l’authentification
- La politique d’analyse
- Les technologies
```

### Politiques d’analyse

```
Analyze → Scan Policy Manager

Créer ou modifier des politiques :
- Les tests à exécuter
- Le seuil de détection (Threshold)
- L’intensité des tests (Strength)
```

### Catégories de politiques

| Catégorie | Tests |
|----------|-------|
| **Injection** | SQL, commandes système, LDAP |
| **Client Browser** | XSS et DOM XSS |
| **Server Security** | Inclusion de fichiers distants |
| **Information** | Traversée de chemins et divulgation d’informations |
| **Miscellaneous** | Redirections contrôlées par l’utilisateur |

### Intensité des analyses

| Niveau | Description |
|-------|-------------|
| **Low** | Peu de requêtes, rapide |
| **Medium** | Compromis équilibré |
| **High** | Nombreuses requêtes, analyse approfondie |
| **Insane** | Nombre maximal de tests, lent |

### Sélection des technologies
```
Faire un clic droit sur la cible → Technology → Select...

Filtrer les analyses selon les technologies :
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-analyse-passive"></a>
## 👁️ Analyse passive

### Qu’est-ce que l’analyse passive ?

Elle examine le trafic sans envoyer de requêtes supplémentaires.

```
Automatique : analyse normalement le trafic passant par le proxy

Peut repérer :
- Des en-têtes de sécurité absents
- Des divulgations d’informations
- Des problèmes liés aux cookies
- Des faiblesses SSL/TLS selon les règles disponibles
- La divulgation d’adresses IP privées
```

### Règles d’analyse passive

```
Analyze → Scan Policy Manager → Passive Scan

Exemples de règles, dont les noms d’interface sont conservés :
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

### Configurer l’analyse passive
```
Tools → Options → Passive Scan

Paramètres :
- Scan only in scope : recommandé
- Max alerts per rule: 10
- Tags : activer
```

---

## 🎯 Contextes et sessions

### Que sont les contextes ?

Ce sont des regroupements logiques d’URL liées, destinés à organiser les tests.

### Créer un contexte
```
Faire un clic droit dans Sites → Include in Context → New Context

Ou : File → New Context
```

### Paramètres des contextes

| Paramètre | Utilité |
|---------|---------|
| **Include in Context** | URL à inclure |
| **Exclude from Context** | URL à exclure |
| **Technology** | Technologies du serveur |
| **Authentication** | Méthode de connexion |
| **Session Management** | Gestion des cookies et des jetons |
| **Users** | Comptes utilisateur de test |

### Types d’authentification

| Type | Cas d’usage |
|------|----------|
| **Form-based** | Formulaires de connexion |
| **HTTP Basic** | Authentification Basic |
| **JSON-based** | Authentification d’API |
| **Script-based** | Authentification personnalisée |

### Authentification par formulaire
```
1. Enregistrer la requête de connexion dans ZAP
2. Faire un clic droit → Include in Context
3. Ouvrir Context → Authentication → Form-based
4. Renseigner Login URL
5. Renseigner Login Request POST Data
6. Configurer les paramètres du nom d’utilisateur et du mot de passe
7. Définir les indicateurs de connexion et de déconnexion
```

### Gestion des sessions
```
Context → Session Management

Types :
- Cookie-based (le plus courant)
- HTTP Auth
- Script-based
```

### Utilisateurs
```
Context → Users → Add

Ajouter des utilisateurs de test :
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Outil de fuzzing (Fuzzer)

### Démarrer le fuzzer
```
1. Sélectionner une requête dans History
2. Faire un clic droit → Attack → Fuzz...
3. Sélectionner l’emplacement à tester
4. Ajouter les charges utiles (payloads)
5. Démarrer
```

### Ajouter un emplacement de fuzzing
```
1. Dans la fenêtre Fuzzer, sélectionner le texte à tester
2. Cliquer sur "Add..."
3. Sélectionner le type de charge utile
```

### Types de charges utiles

| Type | Description |
|------|-------------|
| **Strings** | Liste personnalisée de chaînes |
| **File** | Chargement depuis un fichier |
| **File Fuzzers** | Listes de mots intégrées |
| **Numbers** | Suites de nombres |
| **Regex** | Génération à partir d’expressions régulières |
| **Script** | Script personnalisé |

### Fuzzers intégrés
```
Add → File Fuzzers

Catégories :
- jbrofuzz (XSS, SQL, etc.)
- dirbuster (répertoires)
- fuzzdb (ensemble étendu)
```

### Options du fuzzer
```
Options :
- Nombre de threads simultanés
- Délai entre les requêtes
- Suivre les redirections
- Afficher tous les résultats ou les filtrer
```

### Exemple de fuzzing d’un paramètre
```
1. Capturer : GET /page?id=1
2. Faire un clic droit → Fuzz
3. Sélectionner "1"
4. Ajouter des charges utiles d’injection SQL
5. Démarrer le fuzzer
6. Analyser les résultats selon la taille et le code de la réponse
```

---

## 🤖 API et automatisation

### Activer l’API
```
Tools → Options → API

Paramètres :
- Enabled : Yes (activée)
- API Key : générée automatiquement ou personnalisée
- Permitted addresses: localhost
```

### Accès à l’API
```bash
# Racine de l’API
http://localhost:8080/JSON/

# Avec une clé d’API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Obtenir la version
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Points de terminaison courants de l’API

| Point de terminaison | Utilité |
|----------|---------|
| `/core/view/alerts/` | Obtenir toutes les alertes |
| `/core/view/hosts/` | Obtenir tous les hôtes |
| `/spider/action/scan/` | Démarrer l’exploration |
| `/ascan/action/scan/` | Démarrer une analyse active |
| `/core/view/urls/` | Obtenir toutes les URL |
| `/core/action/shutdown/` | Arrêter ZAP |

### Exemples d’API

```bash
# Explorer une URL
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Vérifier l’état de l’exploration
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Démarrer une analyse active
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Obtenir les alertes
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Générer un rapport HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Client Python de l’API
```python
from zapv2 import ZAPv2

# Se connecter à ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Analyse active
zap.ascan.scan(url='http://target.com')

# Obtenir les alertes
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-ligne-de-commande"></a>
## ⌨️ Ligne de commande

### Options de base de la ligne de commande
```bash
# Démarrer ZAP en mode démon
zap.sh -daemon

# Démarrer sur un port précis
zap.sh -daemon -port 8090

# Analyse rapide
zap.sh -quickurl http://target.com -quickout report.html

# Analyse de référence (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Framework d’automatisation
```bash
# Exécuter un plan d’analyse automatisée
zap.sh -cmd -autorun automation.yaml
```

### Exemple YAML d’automatisation
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Analyses avec Docker

```bash
# Analyse de référence (règles passives uniquement, avec exploration)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Analyse complète (active)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# Analyse d’API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Extensions

### Installer des extensions
```
Help → Check for Updates → Marketplace

Ou : Tools → Add-ons → Marketplace
```

### Extensions essentielles

| Extension | Description |
|--------|-------------|
| **Active Scan Rules** | Règles d’analyse active supplémentaires |
| **Passive Scan Rules** | Règles passives supplémentaires |
| **FuzzDB** | Charges utiles pour le fuzzing |
| **Retire.js** | Détection de bibliothèques JavaScript vulnérables |
| **AJAX Spider** | Robot d’exploration prenant en charge JavaScript |
| **Selenium** | Automatisation des navigateurs |
| **Wappalyzer** | Détection des technologies |
| **Access Control** | Tests du contrôle d’accès |
| **Directory List** | Listes de mots pour les répertoires |

### Gestion des extensions
```
# Lister les extensions installées
Tools → Add-ons → Installed

# Tout mettre à jour
Help → Check for Updates → Update All
```

---

## 🎬 Exemples pratiques

### Exemple 1 : analyse web rapide
```
1. Saisir l’URL dans l’onglet Quick Start
2. Cliquer sur "Attack"
3. ZAP lance automatiquement l’exploration puis l’analyse
4. Examiner les alertes une fois l’opération terminée
```

### Exemple 2 : tests manuels et automatisés
```
1. Configurer le navigateur pour utiliser le proxy ZAP
2. Parcourir manuellement l’application et toutes ses fonctionnalités
3. Faire un clic droit sur la cible dans Sites → Spider
4. Attendre la fin de l’exploration
5. Faire un clic droit sur la cible → Active Scan
6. Examiner les alertes
7. Générer un rapport
```

### Exemple 3 : analyse authentifiée
```
1. Créer un contexte pour la cible
2. Configurer l’authentification par formulaire
3. Ajouter les identifiants d’un utilisateur de test
4. Configurer le mode utilisateur forcé
5. Explorer l’application en tant qu’utilisateur authentifié
6. Effectuer une analyse active
```

### Exemple 4 : test d’API
```
1. Importer la définition de l’API (OpenAPI/Swagger)
2. Ouvrir File → Import → Import OpenAPI definition
3. Explorer l’API
4. Effectuer une analyse active
```

### Exemple 5 : intégration CI/CD
```bash
# Pipeline Jenkins
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Référence rapide

### Actions essentielles

| Tâche | Méthode |
|------|--------|
| Démarrer le proxy | ZAP démarre automatiquement sur le port 8080 |
| Explorer | Clic droit → Spider |
| Analyse active | Clic droit → Active Scan |
| Intercepter | Cliquer sur le bouton Break (🛑) |
| Effectuer du fuzzing | Clic droit sur la requête → Fuzz |
| Générer un rapport | Report → Generate Report |

### Raccourcis clavier

| Raccourci | Action |
|----------|--------|
| `Ctrl+Alt+S` | Fenêtre Spider |
| `Ctrl+Alt+A` | Fenêtre Active Scan |
| `Ctrl+M` | Requête manuelle |
| `Ctrl+B` | Activer ou désactiver l’interception |
| `Ctrl+F` | Rechercher |
| `Ctrl+R` | Renvoyer la requête |

### Modes d’analyse

| Mode | Utilisation |
|------|----------|
| **Safe** | Pour éviter les opérations susceptibles de modifier l’application |
| **Protected** | Pour tester uniquement un périmètre précis |
| **Standard** | Tests habituels |
| **ATTACK** | Tests agressifs, avec autorisation |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

1. **Limiter le périmètre**
   ```
   Définir le contexte et analyser uniquement les éléments dans le périmètre
   ```

2. **Utiliser AJAX Spider pour les applications monopages (SPA)**
   ```
   Les applications modernes peuvent nécessiter AJAX Spider en complément du robot classique
   ```

3. **Configurer les technologies**
   ```
   Sélectionner uniquement les technologies pertinentes pour réduire la durée de l’analyse
   ```

### Conseils pour améliorer la précision

1. **Configurer d’abord l’authentification**
   ```
   Configurer l’authentification pour améliorer la couverture
   ```

2. **Explorer manuellement avant d’automatiser**
   ```
   Parcourir d’abord l’application manuellement pour découvrir toutes ses fonctionnalités
   ```

3. **Examiner toutes les alertes**
   ```
   Rechercher les faux positifs et confirmer les résultats valides
   ```

### Déroulement des tests

```
1. Configurer le proxy
2. Explorer manuellement, avec authentification
3. Explorer avec les robots classique et AJAX
4. Examiner les résultats de l’analyse passive
5. Effectuer une analyse active
6. Effectuer des tests manuels : fuzzing et cas limites
7. Générer un rapport
```

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** OWASP ZAP est un outil de test de sécurité réservé aux **tests autorisés**.
> 
> - ✅ Tester les applications dont vous êtes propriétaire
> - ✅ Tester avec une autorisation écrite explicite
> - ✅ Utiliser des environnements de test ou de préproduction
> - ❌ Ne jamais analyser la production sans autorisation
> - ❌ Ne jamais analyser sans autorisation des systèmes appartenant à des tiers
> 
> **Les tests de sécurité non autorisés sont illégaux et peuvent entraîner des poursuites pénales.**

---

## 📚 Ressources

### Ressources officielles
- [Site officiel de ZAP](https://www.zaproxy.org/)
- [Documentation ZAP](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [Documentation de l’API ZAP](https://www.zaproxy.org/docs/api/)

### Ressources pour apprendre
- [Guide de test OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Vidéos ZAP Deep Dive](https://www.zaproxy.org/docs/videos/)

### Fiches connexes
- [Burp Suite](../../Burp-Suite/translations/README.fr.md)
- [Nikto](../../Nikto/translations/README.fr.md)
- [SQLMap](../../SQLMap/translations/README.fr.md)

---

<p align="center">
  <b>⚡ Maîtrisez les tests de sécurité des applications web !</b><br>
  <i>Libre, puissant et porté par sa communauté</i>
</p>
