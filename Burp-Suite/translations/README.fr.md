# 🔶 Burp Suite — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    ____                      _____       _ __
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/
                /_/
    Web Application Security Testing Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 La plateforme de référence pour les tests de sécurité des applications web</b>
</p>

---

> **Repères de version :** les montants et capacités des éditions du tableau sont ceux du document source, pas un tarif garanti à jour. Les anciens menus `Spider`, `Extender` et `Proxy → Options` peuvent différer de votre version ; consultez les [réglages officiels](https://portswigger.net/burp/documentation/desktop/settings). Les libellés anglais de l’interface sont conservés pour pouvoir les retrouver.
>
> **Java et raccourcis :** la mention source `JRE 8+` est historique. La [documentation actuelle du lancement en ligne de commande](https://portswigger.net/burp/documentation/desktop/troubleshooting/launch-from-command-line) demande Java 21 au minimum ; les installateurs natifs comprennent un environnement Java. Les raccourcis dépendent de la plateforme et de la configuration : les tableaux sources attribuent notamment deux fonctions à `Ctrl+Shift+T` ; vérifiez vos réglages plutôt que de considérer ces combinaisons comme universelles.
>
> **Interprétation :** les requêtes et réglages sont des exemples. Un contrôle statistique de Sequencer ne prouve pas à lui seul la sécurité cryptographique du générateur. L’exemple JWT est tronqué et un JWT comporte des segments encodés en Base64URL ; le décoder ne vérifie pas sa signature. Retirer un en-tête côté proxy ou ralentir les requêtes ne garantit ni le contournement d’un contrôle serveur ni l’absence de détection. Le dessin ASCII conserve le texte source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation et préparation](#-installation-et-préparation)
- [Configuration du proxy](#-configuration-du-proxy)
- [Interception du trafic](#-interception-du-trafic)
- [Cible et périmètre](#-cible-et-périmètre)
- [Spider et robot d’exploration](#-spider-et-robot-dexploration)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Extensions](#-extensions)
- [Raccourcis clavier](#-raccourcis-clavier)
- [Déroulement des tests](#-déroulement-des-tests)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Burp Suite** est une plateforme intégrée de test de sécurité des applications web. Développée par PortSwigger, elle constitue un outil de référence pour les tests d’intrusion web.

### Éditions

| Édition | Fonctionnalités | Prix |
|---------|----------|-------|
| **Community** | Proxy de base et outils manuels | Gratuit |
| **Professional** | Scanner, Intruder sans limitation de débit imposée par Community et Collaborator | $449/an |
| **Enterprise** | Intégration CI/CD et fonctions de travail en équipe | Contacter le service commercial |

### Outils principaux

| Outil | Description |
|------|-------------|
| **Proxy** | Intercepter et modifier le trafic HTTP/HTTPS |
| **Spider** | Explorer automatiquement les applications web |
| **Scanner** | Rechercher automatiquement des vulnérabilités |
| **Intruder** | Attaques automatisées personnalisées |
| **Repeater** | Modification manuelle des requêtes |
| **Decoder** | Encoder et décoder des données |
| **Comparer** | Comparer les réponses |
| **Sequencer** | Analyser le caractère aléatoire des jetons |

---

## 📥 Installation et préparation

### Téléchargement

```bash
# Télécharger depuis le site officiel
https://portswigger.net/burp/releases

# Disponible pour :
# - Windows (installateur ou application autonome)
# - macOS (DMG)
# - Linux (script ou JAR)
```

### Configuration requise

| Élément | Minimum | Recommandé |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Disque | 500 MB | 2+ GB |
| Java | JRE 8+ | JRE fourni |
| Affichage | 1280x800 | 1920x1080 |

### Configuration au premier lancement

1. **Choisir le type de projet**
   - Projet temporaire pour les tests
   - Nouveau projet sur disque pour enregistrer le travail
   - Ouvrir un projet existant

2. **Configuration**
   - Utiliser les réglages par défaut de Burp, recommandés pour débuter
   - Charger un fichier de configuration

3. **Allocation de mémoire**
   ```bash
   # Augmenter la mémoire pour les projets volumineux
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

## 🔌 Configuration du proxy

### Configuration du navigateur

#### Configuration manuelle du proxy

| Navigateur | Emplacement des réglages du proxy |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Utilise le proxy du système ou une extension |
| Safari | System Preferences → Network → Proxies |

**Proxy Burp par défaut :**
```
Host: 127.0.0.1
Port: 8080
```

#### Configuration de Firefox
```
1. Ouvrir les préférences de Firefox
2. Rechercher "proxy"
3. Cliquer sur "Settings..."
4. Sélectionner "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. Cocher "Also use this proxy for HTTPS"
7. Cliquer sur OK
```

#### Extension FoxyProxy (recommandée)
```
1. Installer l’extension FoxyProxy
2. Ajouter un proxy :
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. L’activer pendant les tests
```

### Installation du certificat SSL/TLS

```bash
# Étape 1 : une fois le proxy activé, ouvrir :
http://burp

# Étape 2 : cliquer sur "CA Certificate" pour télécharger le certificat

# Étape 3 : importer le certificat :
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# Système : ajouter le certificat aux autorités racines de confiance
```

### Points d’écoute du proxy

```
Proxy → Options → Proxy Listeners

Par défaut : 127.0.0.1:8080

Ajouter des points d’écoute personnalisés :
- Ports distincts selon les tests
- Écoute sur toutes les interfaces (0.0.0.0) pour les tests mobiles
- Redirection vers d’autres hôtes
```

### Proxy invisible pour les clients sans prise en charge explicite du proxy

```
1. Proxy → Options → Proxy Listeners
2. Modifier le point d’écoute → Request handling
3. Activer "Support invisible proxying"
4. Configurer le fichier hosts ou le DNS
```

---

## 🎣 Interception du trafic

### Commandes d’interception

| Bouton | Fonction |
|--------|----------|
| **Intercept is on/off** | Activer ou désactiver l’interception |
| **Forward** | Transmettre la requête au serveur |
| **Drop** | Abandonner la requête |
| **Action** | Menu d’options supplémentaires |

### Options d’interception

```
Proxy → Options → Intercept Client Requests

Exemples de règles :
- Intercepter uniquement les éléments du périmètre
- Intercepter uniquement certains types de fichiers
- Exclure les images et les fichiers statiques
```

### Modification des requêtes

```http
# Requête d’origine
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Requête modifiée pendant l’interception
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Recherche et remplacement

```
Proxy → Options → Match and Replace

Règles courantes :
- Supprimer des en-têtes de sécurité
- Modifier User-Agent
- Ajouter des en-têtes personnalisés
- Remplacer des paramètres de requête
```

| Type | Motif recherché | Remplacement | Objectif |
|------|-------|---------|---------|
| En-tête de requête | `User-Agent: .*` | `User-Agent: Custom` | Modifier l’agent utilisateur |
| Corps de requête | `password=test` | `password=admin` | Tester des identifiants |
| En-tête de réponse | `X-Frame-Options.*` | ` ` | Supprimer l’en-tête |
| Corps de réponse | `disabled` | `enabled` | Activer des boutons |

### Modification des réponses

```
Proxy → Options → Intercept Server Responses

Activer pour :
- Supprimer des en-têtes de sécurité
- Modifier le JavaScript
- Modifier le contenu de la réponse
- Tester les contrôles côté client
```

---

## 🎯 Cible et périmètre

### Ajout au périmètre

```
Méthode 1 : clic droit dans Site map → Add to scope
Méthode 2 : Target → Scope → Add pour un ajout manuel
Méthode 3 : depuis Proxy history → Add to scope
```

### Configuration du périmètre

```
Target → Scope

Inclure dans le périmètre :
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File : motif d’expression régulière

Exemples de motifs :
.*\.target\.com$          # Tous les sous-domaines
^https://target\.com/app  # Chemin précis
```

### Réglages avancés du périmètre

```
Target → Scope → Advanced scope control

Inclure :
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclure :
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Plan du site

```
Target → Site map

Fonctionnalités :
- Afficher tout le contenu découvert
- Filtrer selon le périmètre
- Rechercher et filtrer le contenu
- Mettre en évidence les éléments intéressants
- Comparer des plans de site
```

---

<a id="-spider-et-robot-dexploration"></a>
## 🕷️ Spider et robot d’exploration

### Démarrer l’exploration

```
1. Faire un clic droit sur la cible dans Site map
2. Sélectionner "Spider this host" ou "Spider this branch"
3. Ou : Target → Site map → clic droit → Scan → Crawl
```

### Réglages de l’exploration

```
Spider → Options (ancienne interface), ou
Dashboard → New scan → Crawl

Réglages du robot d’exploration :
- Profondeur maximale des liens
- Durée maximale de l’exploration
- Envoi des formulaires
- Gestion de la connexion
```

### Configuration de l’exploration

| Réglage | Description | Recommandé |
|---------|-------------|-------------|
| **Profondeur des liens** | Profondeur jusqu’à laquelle suivre les liens | 5-10 |
| **Nombre maximal de requêtes** | Limite du nombre total de requêtes | 1000-5000 |
| **Suppression des doublons** | Ignorer les pages similaires | Enabled |
| **Analyse passive** | Analyser pendant l’exploration | Enabled |

### Envoi des formulaires

```
Spider → Options → Application Login

Configurer la connexion pour :
- Un jeu d’identifiants
- Une liste d’identifiants
- La détection de session
```

---

## 🔍 Scanner

### Types d’analyse

| Type | Description | Cas d’usage |
|------|-------------|----------|
| **Passive** | Analyser le trafic du proxy | En permanence |
| **Active** | Envoyer des charges utiles pour rechercher des vulnérabilités | Avec autorisation |
| **Complète** | Analyse approfondie | Lorsque le temps le permet |
| **Personnalisée** | Uniquement certains tests | Tests ciblés |

### Démarrer une analyse

```
Méthode 1 : clic droit → Scan
Méthode 2 : Dashboard → New scan
Méthode 3 : envoyer au Scanner depuis un autre outil
```

### Configuration de l’analyse

```
Dashboard → New scan → Scan configuration

Configurations intégrées :
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Catégories de vulnérabilités

```
Le Scanner recherche notamment :

Injections :
- Injection SQL
- Injection de commandes système
- Injection LDAP
- Injection XML/XPath

Cross-Site Scripting (XSS) :
- XSS réfléchi
- XSS stocké
- XSS fondé sur le DOM

Authentification :
- Défauts d’authentification
- Problèmes de gestion des sessions
- Mots de passe faibles

Autres :
- CSRF
- Traversée de répertoires
- Inclusion de fichiers
- Divulgation d’informations
- Problèmes côté serveur
```

### Résultats de l’analyse

```
Dashboard → Issue activity
Target → Site map → Issues

Pour chaque constat :
- Gravité (High/Medium/Low/Info)
- Niveau de confiance (Certain/Firm/Tentative)
- Description
- Correction
- Preuve (requête/réponse)
```

### Profils d’analyse personnalisés

```
1. Dashboard → New scan
2. Sélectionner "Scan configuration"
3. Cliquer sur "New" ou modifier une configuration existante
4. Enregistrer pour réutilisation

Personnaliser :
- Les vulnérabilités à tester
- L’intensité des charges utiles
- Le débit des requêtes
- La gestion de l’authentification
```

---

## 🔁 Repeater

### Objectif

Tests et modifications manuels de requêtes individuelles.

### Envoyer à Repeater

```
1. Faire un clic droit sur une requête
2. Sélectionner "Send to Repeater"
3. Clavier : Ctrl+R
```

### Utiliser Repeater

```
1. Modifier la requête dans le panneau de gauche
2. Cliquer sur "Send", ou utiliser Ctrl+Space
3. Lire la réponse dans le panneau de droite
4. Comparer les réponses
```

### Fonctionnalités de Repeater

| Fonctionnalité | Description |
|---------|-------------|
| **Plusieurs onglets** | Tester plusieurs requêtes |
| **Historique des requêtes** | Naviguer avec les boutons < > |
| **Suivre les redirections** | Activer ou désactiver le suivi automatique |
| **Traiter les cookies** | Actualiser les cookies automatiquement |
| **Content-Length** | Actualiser automatiquement l’en-tête |

### Exemples de tests

```http
# Tester une injection SQL
GET /user.php?id=1' HTTP/1.1

# Tester un XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Tester un contournement de l’authentification
GET /admin HTTP/1.1
Cookie: role=admin

# Tester un IDOR
GET /api/user/1 HTTP/1.1  # Votre utilisateur
GET /api/user/2 HTTP/1.1  # Autre utilisateur
```

---

## 🔫 Intruder

### Types d’attaque

| Type | Positions | Charges utiles | Cas d’usage |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 jeu, une position à la fois | Test d’un seul paramètre |
| **Battering Ram** | 1+ | 1 jeu, même valeur partout | Même valeur partout |
| **Pitchfork** | 1+ | Plusieurs jeux en parallèle | Paires nom d’utilisateur et mot de passe |
| **Cluster Bomb** | 1+ | Plusieurs jeux, toutes les combinaisons | Force brute complète sur les identifiants |

### Envoyer à Intruder

```
1. Faire un clic droit sur la requête
2. Sélectionner "Send to Intruder"
3. Clavier : Ctrl+I
```

### Définir les positions

```
Intruder → Positions

Marquer les points d’injection avec les symboles § :

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Boutons :
- Add § — Marquer la sélection
- Clear § — Supprimer les marqueurs
- Auto § — Détecter automatiquement
```

### Types de charges utiles

| Type | Description |
|------|-------------|
| **Simple list** | Liste de mots personnalisée |
| **Runtime file** | Charger depuis un fichier |
| **Numbers** | Nombres séquentiels ou aléatoires |
| **Dates** | Suites de dates |
| **Brute forcer** | Combinaisons de caractères |
| **Null payloads** | Charges utiles vides pour tester le débit |
| **Username generator** | Formats courants de noms d’utilisateur |

### Traitement des charges utiles

```
Intruder → Payloads → Payload Processing

Ajouter des règles :
- Ajouter un préfixe ou un suffixe
- Encoder pour une URL
- Encoder en Base64
- Calculer une empreinte (MD5, SHA)
- Rechercher et remplacer
```

### Grep - Match

```
Intruder → Options → Grep - Match

Ajouter les chaînes à repérer dans les réponses :
- "Login successful"
- "Invalid password"
- "error"
- Expression régulière personnalisée

Cela aide à repérer les attaques ayant abouti.
```

### Exemples d’attaques

#### Force brute sur la connexion
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Attack type: Cluster bomb
Payload 1 : liste de noms d’utilisateur
Payload 2 : liste de mots de passe
```

#### Fuzzing des paramètres
```
GET /api/user/§1§ HTTP/1.1

Attack type: Sniper
Payload : nombres 1-1000
```

#### Découverte de contenu
```
GET /§admin§ HTTP/1.1

Attack type: Sniper
Payload : liste de répertoires
Filtre : code de statut != 404
```

### Analyse des résultats

```
Après l’attaque :
- Trier par longueur pour repérer les réponses différentes
- Trier par code de statut
- Filtrer selon les correspondances grep
- Exporter les résultats
```

---

## 🔓 Decoder

### Encodage et décodage

```
Onglet Decoder

Opérations :
- Encodage et décodage d’URL
- Encodage et décodage HTML
- Encodage et décodage Base64
- Encodage et décodage hexadécimal
- Compression et décompression Gzip
- ASCII Hex
- Binaire
```

### Décodage automatique

```
Decoder → Smart decode

Détecte et décode automatiquement :
- Base64
- Encodage d’URL
- Entités HTML
- Encodages combinés
```

### Usages courants

```
# Décoder un jeton JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Choisir Decode as Base64

# Décoder un paramètre d’URL
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ Appliquer URL decode
→ <script>alert(1)</script>

# Encoder une charge utile
<script>alert(1)</script>
→ Appliquer Base64 encode
→ Utiliser dans l’attaque
```

---

## 📊 Comparer

### Objectif

Comparer deux éléments de données : requêtes, réponses ou autres données.

### Utilisation

```
1. Faire un clic droit sur le premier élément → Send to Comparer
2. Faire un clic droit sur le second élément → Send to Comparer
3. Ouvrir l’onglet Comparer
4. Sélectionner les éléments, puis cliquer sur "Words" ou "Bytes"
```

### Modes de comparaison

| Mode | Description |
|------|-------------|
| **Words** | Comparer mot à mot |
| **Bytes** | Comparer octet par octet, avec une correspondance exacte |

### Cas d’usage

```
- Comparer les réponses de connexion réussie et échouée
- Rechercher les différences entre les jetons de session
- Comparer avant et après modification
- Repérer les champs de formulaire masqués
```

---

## 🔢 Sequencer

### Objectif

Analyser la qualité du caractère aléatoire des jetons, notamment les identifiants de session et les jetons CSRF.

### Utilisation

```
1. Capturer une requête qui renvoie un jeton
2. Faire un clic droit → Send to Sequencer
3. Indiquer l’emplacement du jeton
4. Démarrer la capture en direct
5. Analyser les résultats
```

### Indicateurs d’analyse

| Indicateur | Valeur favorable | Description |
|--------|------------|-------------|
| **Qualité globale** | >100 bits | Score de caractère aléatoire |
| **Au niveau des caractères** | Entropie élevée | Caractère aléatoire par caractère |
| **Au niveau des bits** | Entropie élevée | Caractère aléatoire par bit |

### Interprétation

```
Résultats :
- Excellent : le document source présente le jeton comme cryptographiquement aléatoire
- Good : acceptable pour la plupart des usages selon la source
- Poor : potentiellement prévisible
- Very poor : le jeton pourrait être deviné
```

---

## 🧩 Extensions

### BApp Store

```
Extender → BApp Store

Extensions populaires :
```

### Extensions utiles

| Extension | Description |
|-----------|-------------|
| **Autorize** | Tests d’autorisation |
| **Logger++** | Journalisation enrichie |
| **Retire.js** | Détecter les bibliothèques JavaScript vulnérables |
| **Active Scan++** | Vérifications supplémentaires du scanner |
| **AuthMatrix** | Tests de matrices d’autorisation |
| **Turbo Intruder** | Alternative rapide à Intruder |
| **Param Miner** | Découverte de paramètres masqués |
| **JSON Web Tokens** | Analyse et modification de JWT |
| **Hackvertor** | Encodage et décodage avancés |
| **CO2** | Ensemble d’outils utiles |

### Installation des extensions

```
Méthode 1 : BApp Store
1. Extender → BApp Store
2. Rechercher l’extension
3. Cliquer sur Install

Méthode 2 : installation manuelle
1. Télécharger le fichier .jar ou .py
2. Extender → Extensions → Add
3. Choisir le type de fichier et son chemin
```

### Réglages des extensions

```
Extender → Options

- Environnement Python pour les extensions Python
- Réglages de l’environnement Java
- Options de journalisation
```

---

<a id="-raccourcis-clavier"></a>
## ⌨️ Raccourcis clavier

### Raccourcis globaux

| Raccourci | Action |
|----------|--------|
| `Ctrl+I` | Envoyer à Intruder |
| `Ctrl+R` | Envoyer à Repeater |
| `Ctrl+S` | Rechercher |
| `Ctrl+F` | Transmettre dans Proxy |
| `Ctrl+D` | Abandonner dans Proxy |
| `Ctrl+T` | Activer ou désactiver l’interception |
| `Ctrl+Shift+T` | Activer ou désactiver l’interception du proxy |

### Navigation entre les onglets

| Raccourci | Action |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### Dans Repeater

| Raccourci | Action |
|----------|--------|
| `Ctrl+Space` | Envoyer la requête |
| `Ctrl+G` | Accéder à l’historique des requêtes |
| `Ctrl+Plus` | Nouvel onglet |

### Dans l’éditeur

| Raccourci | Action |
|----------|--------|
| `Ctrl+U` | Encoder la sélection pour une URL |
| `Ctrl+Shift+U` | Décoder une URL dans la sélection |
| `Ctrl+B` | Encoder en Base64 |
| `Ctrl+Shift+B` | Décoder le Base64 |

---

## 🎯 Déroulement des tests

### Déroulement standard d’un test d’application web

```
Étape 1 : préparation
├── Configurer le proxy du navigateur
├── Installer le certificat SSL
└── Définir le périmètre

Étape 2 : reconnaissance
├── Parcourir manuellement l’application
├── Lancer Spider ou le robot d’exploration
└── Examiner Site map

Étape 3 : analyse
├── Consulter Proxy history
├── Examiner les résultats de l’analyse passive
└── Déterminer la surface d’attaque

Étape 4 : tests
├── Tester manuellement avec Repeater
├── Tester automatiquement avec Intruder
├── Lancer une analyse active
└── Utiliser des extensions

Étape 5 : rapport
├── Examiner tous les constats
├── Générer un rapport
└── Documenter les preuves
```

### Liste de contrôle des tests

```
Authentification :
□ Force brute sur la connexion
□ Politique de mots de passe
□ Verrouillage des comptes
□ Gestion des sessions
□ Fonction de mémorisation de la connexion

Autorisation :
□ Tests IDOR
□ Élévation de privilèges
□ Accès aux fonctions

Validation des entrées :
□ Injection SQL
□ XSS réfléchi, stocké ou fondé sur le DOM
□ Injection de commandes
□ Téléversement de fichiers
□ XXE

Session :
□ Fixation de session
□ Expiration de session
□ Caractère aléatoire des jetons

Logique métier :
□ Modification des prix
□ Contournement du déroulement prévu
□ Conditions de concurrence
```

---

## 📊 Référence rapide

### Synthèse des outils

| Outil | Usage principal | Raccourci principal |
|------|-------------|--------------|
| Proxy | Intercepter le trafic | Ctrl+T (toggle) |
| Repeater | Tests manuels | Ctrl+R (send to) |
| Intruder | Attaques automatisées | Ctrl+I (send to) |
| Scanner | Rechercher des vulnérabilités | Clic droit → Scan |
| Spider | Découvrir du contenu | Clic droit → Spider |
| Decoder | Encoder et décoder | - |
| Comparer | Comparer les réponses | Send to Comparer |
| Sequencer | Analyser les jetons | Send to Sequencer |

### Référence rapide des types d’attaque Intruder

| Attaque | Quand l’utiliser |
|--------|----------|
| **Sniper** | Tester un paramètre à la fois |
| **Battering Ram** | Même charge utile partout |
| **Pitchfork** | Paires nom d’utilisateur et mot de passe |
| **Cluster Bomb** | Toutes les combinaisons nom d’utilisateur × mot de passe |

### Codes de statut courants

| Code | Signification | Intérêt dans Intruder |
|------|---------|----------------------|
| 200 | OK | Réponse normale |
| 301/302 | Redirection | Peut signaler une réussite |
| 401 | Non authentifié | Identifiants incorrects |
| 403 | Interdit | Accès refusé |
| 404 | Introuvable | Chemin invalide |
| 500 | Erreur du serveur | Vulnérabilité possible |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performances

1. **Limiter le périmètre**
   ```
   Ne garder dans le périmètre que les domaines cibles
   Cela réduit le bruit dans Proxy history
   ```

2. **Désactiver des extensions**
   ```
   Désactiver les extensions inutilisées pour améliorer les performances
   ```

3. **Utiliser des fichiers de projet**
   ```
   Enregistrer régulièrement le travail
   Utiliser des fichiers de projet pour les tests importants
   ```

### Conseils de test

1. **Commencer par l’analyse passive**
   ```
   Laisser le scanner passif repérer les problèmes évidents
   Examiner les résultats avant l’analyse active
   ```

2. **Commencer par Repeater**
   ```
   Les tests manuels permettent d’approfondir l’analyse
   Comprendre l’application avant d’automatiser
   ```

3. **S’organiser avec les onglets**
   ```
   Donner des noms explicites aux onglets Repeater
   Nommer les attaques Intruder
   ```

### Réduction de la visibilité des tests

```
Réglages présentés comme discrets dans la source :

Project options → Connections:
- Limiter le débit des requêtes
- Délais aléatoires
- Limite des connexions simultanées

Intruder → Options:
- Délai entre les requêtes
- Heure de démarrage
```

### Dépannage

| Problème | Solution |
|-------|----------|
| Impossible d’intercepter HTTPS | Installer le certificat de l’autorité de certification |
| Performances lentes | Réduire le périmètre et désactiver des extensions |
| Requêtes manquantes | Vérifier "Intercept Server Responses" |
| Le scanner ne trouve pas de vulnérabilités | Augmenter l’intensité de l’analyse |

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Burp Suite est un outil puissant de test de sécurité qui doit être réservé aux **tests autorisés**.
>
> - ✅ Tester les applications qui vous appartiennent
> - ✅ Tester avec une autorisation écrite explicite
> - ✅ Tester dans des environnements prévus à cet effet
> - ❌ Ne jamais analyser un système de production sans autorisation
> - ❌ Ne jamais utiliser l’outil pour un accès non autorisé
>
> **Les tests de sécurité non autorisés sont illégaux et peuvent entraîner des poursuites pénales.**

---

## 📚 Ressources

### Ressources officielles
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Burp Suite Documentation](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Ressources d’apprentissage
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Fiches connexes
- [SQLMap](../../SQLMap/translations/README.fr.md)
- [Metasploit Framework](../../Metasploit/translations/README.fr.md)

---

<p align="center">
  <b>🔶 Maîtrisez les tests de sécurité des applications web !</b><br>
  <i>Pour apprendre, pratiquez : préparez un laboratoire et entraînez-vous !</i>
</p>
