# 👤 OSINT : recherche de personnes

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 Moteurs de recherche de personnes

### Services gratuits

> **Précision :** la classification gratuit/payant et les couvertures sont celles du document source ; elles peuvent évoluer selon le service. Une concordance de nom ou de visage ne suffit pas à établir une identité.
| Outil | URL | Couverture |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | États-Unis |
| **FastPeopleSearch** | fastpeoplesearch.com | États-Unis |
| **TruePeopleSearch** | truepeoplesearch.com | États-Unis |
| **Webmii** | webmii.com | Monde |
| **PeekYou** | peekyou.com | États-Unis |
| **Pipl** | pipl.com | Monde |

### Services payants
| Outil | URL | Fonctions |
|------|-----|----------|
| **Spokeo** | spokeo.com | Recherche approfondie, réseaux sociaux |
| **BeenVerified** | beenverified.com | Vérification des antécédents |
| **Intelius** | intelius.com | Antécédents judiciaires |
| **US Search** | ussearch.com | Archives judiciaires |
| **Radaris** | radaris.com | Registres immobiliers |

---

## 🔎 Google Dorking pour la recherche de personnes

### Recherches de base
```
# Recherche par nom
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Nom + lieu
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Nom + entreprise
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Nom + adresse électronique
"John Smith" "@gmail.com"
"John Smith" email
```

### Informations professionnelles
```
# Recherche de CV
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Recherche spécifique à LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Profils professionnels
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Découverte de profils sur les réseaux sociaux
```
# Rechercher tous les profils
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Recherche par nom d’utilisateur
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Outils en ligne de commande

### Sherlock (recherche par nom d’utilisateur)

> **Note de version :** les appels `python3 sherlock` ou `sherlock.py` reflètent les exemples historiques du document source. Vérifier la [documentation officielle d’installation](https://sherlockproject.xyz/installation) pour la version utilisée.
```bash
# Installation
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Rechercher le nom d’utilisateur sur plusieurs plateformes
python3 sherlock username

# Plusieurs noms d’utilisateur
python3 sherlock user1 user2 user3

# Uniquement certains sites
python3 sherlock username --site twitter instagram facebook

# Exporter les résultats
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (recherche avancée par nom d’utilisateur)
```bash
# Installation
pip3 install maigret

# Recherche
maigret username

# Avec Tor
maigret username --tor

# Sortie JSON
maigret username --json results.json
```

### holehe (vérification des inscriptions avec une adresse électronique)
```bash
# Installation
pip3 install holehe

# Vérifier l’adresse électronique sur plusieurs services
holehe email@example.com

# Afficher uniquement les sites où l’adresse est inscrite
holehe email@example.com --only-used
```

---

## 📧 Retrouver une personne à partir de son adresse électronique

### Rechercher une personne à partir d’une adresse électronique
```
1. Rechercher l’adresse dans Google : "email@example.com"
2. Consulter HIBP : haveibeenpwned.com
3. Rechercher dans les bases de fuites de données
4. Vérifier les inscriptions sur les réseaux sociaux
5. Consulter les services de recherche inversée d’adresses électroniques
```

### Découverte des formats d’adresses électroniques
```bash
# Formats courants d’adresses électroniques
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Recherche de formats avec Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT sur les numéros de téléphone

### Services de recherche de numéros de téléphone
| Service | URL | Type |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Identification de l’appelant |
| **Sync.me** | sync.me | Identification de l’appelant |
| **NumLookup** | numlookup.com | Recherche gratuite |
| **SpyDialer** | spydialer.com | Recherche inversée |
| **CallerID Test** | calleridtest.com | Informations sur l’opérateur |

### Google Dorks pour les numéros de téléphone
```
# Recherche de base
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Rechercher les informations associées
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### Recherche OSINT sur les numéros de téléphone
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Interface web

# Avec des API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Adresses et registres immobiliers

### Recherche immobilière
| Service | URL | Couverture |
|---------|-----|----------|
| **Zillow** | zillow.com | États-Unis |
| **Redfin** | redfin.com | États-Unis |
| **County Assessor** | [varies] | Registres locaux |
| **Whitepages** | whitepages.com | États-Unis |

### Registres publics
```
# Archives judiciaires
PACER (pacer.gov) — Tribunaux fédéraux
Sites des tribunaux des États — Juridictions des États

# Listes électorales
Bases d’inscription sur les listes électorales (selon l’État)

# Registres des entreprises
Sites des secrétaires d’État de chaque État
```

---

## 👥 Analyse des réseaux sociaux

### OSINT sur LinkedIn
```
# Recherche de profils
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Rechercher les relations
Consulter la rubrique "People Also Viewed"
Examiner les relations en commun
Consulter la liste des employés de l’entreprise
```

### OSINT sur Facebook
```
# URL des profils
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (fonctionnalités désormais limitées)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Outils d’analyse des réseaux sociaux

> **Note historique :** les exemples Twint sont conservés, mais le [dépôt officiel](https://github.com/twintproject/twint) est archivé. Leur fonctionnement avec X actuel n’est pas garanti.
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Recherche de photos et de visages

### Recherche inversée d’images
| Service | URL | Usage privilégié |
|---------|-----|----------|
| **Google Images** | images.google.com | Recherche générale |
| **TinEye** | tineye.com | Correspondances exactes |
| **Yandex** | yandex.com/images | Visages (préférence du document source) |
| **Bing Images** | bing.com/images | Autre solution utile |
| **PimEyes** | pimeyes.com | Reconnaissance faciale |

### Recherche de visages
```
1. Yandex Images — Présenté dans le document source comme adapté à la recherche de visages
2. PimEyes — Reconnaissance faciale payante
3. Social Catfish — Recherche inversée d’images pour les réseaux sociaux
```

---

## 📋 Liste de contrôle de la recherche de personnes

```markdown
## Cible : [Full Name]

### Informations de base
□ Nom légal complet
□ Date de naissance
□ Adresse actuelle
□ Numéros de téléphone
□ Adresses électroniques

### Présence en ligne
□ Profil LinkedIn
□ Profil Facebook
□ Compte Twitter/X
□ Instagram
□ TikTok
□ Autres réseaux sociaux
□ Site personnel / blog

### Vie professionnelle
□ Employeur actuel
□ Intitulé du poste
□ Parcours professionnel
□ Formation
□ Agréments professionnels
□ Publications / brevets

### Registres
□ Registres immobiliers
□ Archives judiciaires
□ Registres des entreprises
□ Inscription sur les listes électorales
□ Actes de mariage / divorce

### Relations
□ Membres de la famille
□ Relations
□ Partenaires commerciaux
□ Relations sociales
```

---

## ⚠️ Considérations juridiques et éthiques

```
✓ Utiliser uniquement des informations accessibles au public
✓ Respecter les lois sur la vie privée (RGPD, CCPA)
✓ Ne pas usurper d’identité ni tromper autrui
✓ Documenter sa méthodologie
✓ Réserver ces recherches à des usages légitimes
✗ Ne pas traquer ni harceler
✗ Ne pas accéder aux comptes privés
✗ Ne pas diffuser publiquement les résultats sans consentement
```

---

**Retour à l’OSINT :** [🔍 Vue d’ensemble de l’OSINT](./README.fr.md)
