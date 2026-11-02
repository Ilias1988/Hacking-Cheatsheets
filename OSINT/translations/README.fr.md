# 🔍 Fiche pratique OSINT (renseignement de sources ouvertes)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **Open Source Intelligence (OSINT)** — Collecte et analyse d’informations accessibles au public, ou renseignement de sources ouvertes.

---

## 📑 Table des matières

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Recherche de personnes** | Rechercher des informations sur des personnes | [📄 Consulter](./People-Search.fr.md) |
| **OSINT sur les adresses électroniques** | Reconnaissance et vérification des adresses | [📄 Consulter](./Email-OSINT.fr.md) |
| **Réseaux sociaux** | Investigations sur les réseaux sociaux | [📄 Consulter](./Social-Media-OSINT.fr.md) |
| **Domaines et adresses IP** | Reconnaissance sur les domaines et adresses IP | [📄 Consulter](./Domain-IP-OSINT.fr.md) |
| **OSINT sur les images** | Recherche inversée et analyse d’images | [📄 Consulter](./Image-OSINT.fr.md) |

---

## 🎯 Méthodologie OSINT

```
┌─────────────────────────────────────────────────────────────┐
│                    OSINT LIFECYCLE                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. REQUIREMENTS    →  Define intelligence needs            │
│         ↓                                                   │
│  2. COLLECTION      →  Gather raw data from sources         │
│         ↓                                                   │
│  3. PROCESSING      →  Convert data to usable format        │
│         ↓                                                   │
│  4. ANALYSIS        →  Evaluate and correlate findings      │
│         ↓                                                   │
│  5. DISSEMINATION   →  Report and present intelligence      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Outils OSINT essentiels

### Moteurs de recherche
| Outil | URL | Utilité |
|------|-----|---------|
| **Google** | https://google.com | Recherche générale |
| **Bing** | bing.com | Moteur de recherche alternatif |
| **DuckDuckGo** | duckduckgo.com | Axé sur la confidentialité |
| **Yandex** | yandex.com | Moteur russe (utile pour les images) |
| **Baidu** | baidu.com | Moteur de recherche chinois |

### Recherche de personnes
| Outil | URL | Utilité |
|------|-----|---------|
| **Pipl** | pipl.com | Recherche approfondie de personnes |
| **Spokeo** | spokeo.com | Recherche de personnes aux États-Unis |
| **WhitePages** | whitepages.com | Recherche de numéros de téléphone et d’adresses |
| **ThatsThem** | thatsthem.com | Recherche gratuite de personnes |
| **BeenVerified** | beenverified.com | Vérification des antécédents |

### Outils pour les adresses électroniques
| Outil | URL | Utilité |
|------|-----|---------|
| **Hunter.io** | hunter.io | Recherche d’adresses professionnelles |
| **Have I Been Pwned** | haveibeenpwned.com | Recherche de fuites de données |
| **EmailRep** | emailrep.io | Réputation des adresses électroniques |
| **Phonebook.cz** | phonebook.cz | Recherche d’adresses électroniques et de domaines |
| **h8mail** | GitHub | Recherche d’identifiants dans les fuites de données |

### Réseaux sociaux
| Outil | URL | Utilité |
|------|-----|---------|
| **Sherlock** | GitHub | Recherche de noms d’utilisateur |
| **Social Searcher** | social-searcher.com | Recherche sur les réseaux sociaux |
| **Namechk** | namechk.com | Disponibilité des noms d’utilisateur |
| **KnowEm** | knowem.com | Vérification des noms d’utilisateur |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | API indépendante de données X (Twitter) : recherche, export d’abonnés, suivi et MCP. |

### Outils pour les domaines et adresses IP
| Outil | URL | Utilité |
|------|-----|---------|
| **Shodan** | shodan.io | Recherche d’appareils |
| **Censys** | censys.io | Analyse des services exposés sur Internet |
| **SecurityTrails** | securitytrails.com | Historique DNS |
| **ViewDNS** | viewdns.info | Outils DNS |
| **BuiltWith** | builtwith.com | Identification des technologies |

---

## 🔥 Commandes de référence rapide

### theHarvester
```bash
# Collecte d’adresses électroniques et de sous-domaines
theHarvester -d target.com -l 500 -b all

# Sources précises
theHarvester -d target.com -b google,linkedin,twitter

# Enregistrer dans un fichier
theHarvester -d target.com -b all -f results.html
```

### Sherlock (recherche par nom d’utilisateur)

> **Note de version :** les appels `python3 sherlock` ou `sherlock.py` reflètent les exemples historiques du document source. Vérifier la [documentation officielle d’installation](https://sherlockproject.xyz/installation) pour la version utilisée.
```bash
# Rechercher sur toutes les plateformes
python3 sherlock.py username

# Sites précis
python3 sherlock.py username --site twitter instagram facebook

# Enregistrer la sortie dans un fichier
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Types de transformations :
- Personne vers adresse électronique
- Adresse électronique vers domaine
- Domaine vers adresse IP
- Adresse IP vers emplacement
- Profils sur les réseaux sociaux
```

### Recon-ng
```bash
# Démarrer recon-ng
recon-ng

# Créer un espace de travail
workspaces create target_name

# Charger les modules
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Définir la cible
options set SOURCE target.com

# Exécuter le module
run
```

---

## 📊 Catégories d’OSINT

### OSINT passif
```
Sans interaction directe avec la cible :
- Moteurs de recherche
- Réseaux sociaux
- Registres publics
- Archive.org
- Données WHOIS
```

### OSINT actif
```
Avec une certaine interaction avec la cible :
- Analyse des ports
- Requêtes DNS
- Exploration de sites web
- Vérification des adresses électroniques
```

---

## 🔒 Sécurité opérationnelle (OPSEC)

### Bonnes pratiques
```
✓ Utiliser un VPN / Tor
✓ Créer des comptes de couverture (sock puppets)
✓ Utiliser des machines virtuelles
✓ Ne pas utiliser ses comptes personnels
✓ Effacer régulièrement les cookies et le cache
✓ Utiliser des profils de navigateur séparés
✓ Tout documenter
```

### Comptes de couverture (sock puppets)
```
- Utiliser des identités générées
- Garder un personnage cohérent entre les plateformes
- Utiliser des comptes anciens (perçus comme plus fiables)
- Fournir des informations de profil crédibles
- Ne pas relier ces comptes à son identité réelle
```

---

## 📋 Liste de contrôle OSINT

```markdown
## Cible : [Name/Company/Domain]

### Personnes
□ Variantes du nom complet
□ Numéros de téléphone
□ Adresses électroniques
□ Adresses physiques
□ Profils sur les réseaux sociaux
□ Parcours professionnel
□ Parcours de formation
□ Photos / images

### Entreprise
□ Enregistrement du domaine (WHOIS)
□ Technologies utilisées
□ Liste des employés
□ Format des adresses électroniques
□ Présence sur les réseaux sociaux
□ Actualités / communiqués de presse
□ Informations financières
□ Implantations physiques

### Informations techniques
□ Adresses IP
□ Sous-domaines
□ Ports / services ouverts
□ Enregistrements DNS
□ Certificats SSL
□ Données historiques
```

---

## 🔗 Répertoires et méthodes OSINT

### OSINT Framework
```
https://osintframework.com/
- Organisation par catégorie
- Liens vers des centaines d’outils
- Arborescence visuelle
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Outils de Michael Bazzell
- Outils de recherche personnalisés
- Processus d’investigation
```

---

## 📚 Guides connexes dans ce dépôt

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**Retour à l’accueil :** [🔴 Hacking Cheatsheets](../../README.md)
