# 🔍 Autopsy — aide-mémoire de la plateforme d’investigation numérique

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Plateforme d’investigation numérique
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 Table des matières

- [Qu’est-ce qu’Autopsy ?](#-quest-ce-quautopsy-)
- [Installation](#-installation)
- [Créer un dossier d’enquête](#-créer-un-dossier-denquête)
- [Ajouter des sources de données](#-ajouter-des-sources-de-données)
- [Modules d’analyse initiale (ingest modules)](#-modules-danalyse-initiale-ingest-modules)
- [Fonctions d’analyse](#-fonctions-danalyse)
- [Analyse des fichiers](#-analyse-des-fichiers)
- [Analyse chronologique](#-analyse-chronologique)
- [Recherche par mots-clés](#-recherche-par-mots-clés)
- [Rapports](#-rapports)
- [Outils en ligne de commande (Sleuth Kit)](#-outils-en-ligne-de-commande-sleuth-kit)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce qu’Autopsy ?

**Autopsy** est une plateforme gratuite et open source d’investigation numérique (*digital forensics*) fondée sur The Sleuth Kit (TSK). Elle propose :

- 🖥️ **Interface graphique (GUI)** — Analyse numérique accessible
- 💾 **Analyse de disques** — Examiner des disques durs, supports USB et images
- 📁 **Récupération de fichiers** — Retrouver des fichiers supprimés
- 📊 **Analyse chronologique** — Reconstituer l’activité des utilisateurs
- 🔍 **Recherche par mots-clés** — Rechercher dans l’ensemble des données
- 📧 **Analyse des e-mails** — Interpréter les bases de messagerie
- 🌐 **Traces web** — Historique de navigation, favoris
- 📱 **Investigation mobile** — Analyse Android/iOS

### Types de supports d’enquête pris en charge

| Type | Formats |
|------|---------|
| **Images de disque** | E01, AFF, brut (dd), VHD, VMDK |
| **Disque local** | Disques physiques ou logiques |
| **Fichiers de machines virtuelles** | VMDK, VHD, VHDX |
| **Fichiers logiques** | Dossiers, fichiers |
| **Espace non alloué** | Données récupérées par signatures (carving) |

---

## 🚀 Installation

### Windows

```bash
# Télécharger depuis le site officiel
https://www.autopsy.com/download/

# Lancer le programme d’installation
autopsy-x.x.x-64bit.msi

# Emplacement par défaut
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

> **Différence de génération :** le paquet Kali `autopsy` installe le navigateur historique Autopsy 2, pas l’interface moderne décrite ici. Les commandes originales sont conservées ([fiche Kali](https://www.kali.org/tools/autopsy/)).

```bash
# Installer avec apt
sudo apt update
sudo apt install autopsy

# Lancer
autopsy
```

### Configuration requise

> **Précision :** formats et modules dépendent de la version et des composants installés. Le tableau matériel reprend l’original ; la documentation actuelle recommande un minimum de 16 GB de RAM et décrit des distributions Windows ainsi que Linux/macOS ([installation officielle](https://sleuthkit.org/autopsy/docs/user-docs/latest/installation_page.html)).

| Composant | Minimum | Recommandé |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Multicœur | 4+ cœurs |
| **Stockage** | SSD | NVMe SSD |
| **Système** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Créer un dossier d’enquête

### Assistant de création d’un dossier

> **Précision :** menus et raccourcis sont indicatifs selon la version ; la documentation actuelle utilise notamment Case → Create New Case. La structure ci-dessous est schématique ; le mode multi-utilisateur nécessite des services supplémentaires et le stockage n’est pas toujours un simple `autopsy.db` local ([dossiers Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/cases_page.html)).

1. Choisir **File → New Case** (Fichier → Nouveau dossier)
2. Saisir le nom du dossier, **Case Name** (par exemple, "Investigation_2024")
3. Saisir **Base Directory**, le répertoire où stocker les fichiers du dossier
4. Choisir le type de dossier, **Case Type** :
   - Single-user : un seul utilisateur, en local
   - Multi-user : plusieurs utilisateurs, en collaboration
5. Saisir le numéro de référence, **Case Number** (facultatif)
6. Renseigner les informations sur l’analyste, **Examiner**
7. Cliquer sur **Finish** (Terminer)

### Structure du dossier

```
CaseName/
├── autopsy.db           # Base de données du dossier (SQLite)
├── Reports/             # Rapports générés
├── Export/              # Fichiers exportés
├── ModuleOutput/        # Résultats des modules
└── [HostName]/          # Données par hôte
    ├── [DataSource]/    # Fichiers des éléments d’enquête
    └── Reports/
```

### Propriétés du dossier

| Champ | Description |
|-------|-------------|
| Case Name (nom du dossier) | Identifiant de l’enquête |
| Case Number (numéro du dossier) | Numéro de référence |
| Examiner (analyste) | Nom de l’analyste |
| Base Directory (répertoire de base) | Emplacement de stockage du dossier |
| Case Type (type de dossier) | Un seul ou plusieurs utilisateurs |

---

## 📀 Ajouter des sources de données

### Types de sources de données

| Type | Description |
|------|-------------|
| **Disk Image or VM File** (image de disque ou fichier de VM) | E01, brut, VMDK, VHD |
| **Local Disk** (disque local) | Analyse d’un disque physique |
| **Logical Files** (fichiers logiques) | Importer des dossiers ou fichiers |
| **Unallocated Space** (espace non alloué) | Image brute de l’espace libre |

### Ajouter une image de disque

1. Choisir **Add Data Source** (Ajouter une source de données) dans la barre d’outils
2. Sélectionner **Disk Image or VM File** (Image de disque ou fichier de VM)
3. Parcourir les fichiers pour sélectionner l’image
4. Choisir le fuseau horaire, **Time Zone**
5. Configurer les **Ingest Modules**, modules d’analyse initiale décrits dans la section suivante
6. Cliquer sur **Finish** (Terminer)

### Ajouter un disque local

```
⚠️ ATTENTION : utiliser un bloqueur d’écriture ou un mode lecture seule !
```

1. Choisir **Add Data Source** (Ajouter une source de données)
2. Sélectionner **Local Disk** (Disque local)
3. Choisir le disque dans la liste
4. Configurer les modules d’analyse initiale
5. Cliquer sur **Finish** (Terminer)

### Formats d’images pris en charge

| Format | Extension | Description |
|--------|-----------|-------------|
| Brut | .raw, .dd, .img | Copie bit à bit |
| EnCase | .E01, .Ex01 | Format Expert Witness |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | Disque VMware |
| VHD/VHDX | .vhd, .vhdx | Disque Hyper-V |

---

<a id="-modules-danalyse-initiale-ingest-modules"></a>
## ⚙️ Modules d’analyse initiale (ingest modules)

### Que sont les modules d’analyse initiale ?

Ces modules analysent automatiquement les sources de données pour en extraire des traces numériques (*artifacts*).

### Modules principaux

| Module | Description |
|--------|-------------|
| **Recent Activity** (activité récente) | Historique de navigation, téléchargements, cookies |
| **Hash Lookup** (comparaison d’empreintes) | Recherche MD5/SHA1 dans des bases connues |
| **File Type Identification** | Identifier les types de fichiers par leur signature |
| **Keyword Search** | Indexer le contenu et y rechercher des mots-clés |
| **Email Parser** | Interpréter les fichiers PST, MBOX et EML |
| **Extension Mismatch** | Repérer les fichiers dont l’extension ne correspond pas au type |
| **Embedded File Extractor** | Extraire les fichiers contenus dans des archives ou documents |
| **EXIF Parser** | Extraire les métadonnées des images |
| **Encryption Detection** | Repérer les fichiers chiffrés |
| **Interesting Files** | Repérer les fichiers dignes d’intérêt |
| **PhotoRec Carver** | Récupérer des fichiers par leurs signatures |
| **Virtual Machine Extractor** | Extraire les fichiers des machines virtuelles |

### Bases d’empreintes

> **Précision :** une correspondance NSRL signifie « fichier connu », pas nécessairement inoffensif ou sans intérêt pour l’enquête. Le module actuel documente la comparaison MD5 ; le tableau MD5/SHA1 ci-dessus est hérité de l’original ([Hash Lookup](https://sleuthkit.org/autopsy/docs/user-docs/latest/hash_db_page.html), [NIST](https://www.nist.gov/itl/csd/secure-systems-and-applications/national-software-reference-library-nsrl)).

```
# Ajouter des bases d’empreintes connues
Tools → Options → Hash Database

# Types :
- Known (NSRL) : ignorer les fichiers connus considérés comme sans intérêt
- Known Bad : signaler les empreintes de logiciels malveillants
```

### NSRL (National Software Reference Library)

```bash
# Télécharger la NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Importer dans Autopsy
Tools → Options → Hash Database → Import Database
```

### Configuration des modules

```
# Activer ou désactiver les modules pour chaque dossier
Clic droit sur la source de données → Run Ingest Modules (Lancer les modules d’analyse)

# Configurer les paramètres du module
Cliquer sur l’icône en forme d’engrenage à côté du nom du module
```

---

## 🔬 Fonctions d’analyse

### Arborescence (panneau de gauche)

Les libellés anglais de l’arbre sont conservés pour les retrouver dans l’interface : Data Sources (sources de données), Views (vues), File Types (types de fichiers), By Extension (par extension), By MIME Type (par type MIME), Deleted Files (fichiers supprimés), File Size (taille), Results (résultats), Extracted Content (contenu extrait), Web History (historique web), Web Downloads (téléchargements), Web Cookies (cookies), Recent Documents (documents récents), Keyword Hits (mots-clés trouvés), HashSet Hits (empreintes trouvées), E-mail Messages (messages), Interesting Items (éléments intéressants), Accounts (comptes), Tags (étiquettes), Follow Up (à suivre) et Notable Item (élément notable). Les crochets indiquent des noms d’exemple : Image Name (nom de l’image), Folders (dossiers), Custom Tags (étiquettes personnalisées).

```
Data Sources
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Custom Tags]
```

### Zones à examiner en priorité

| Catégorie | Éléments à rechercher |
|----------|-----------------|
| **Deleted Files** (fichiers supprimés) | Fichiers récupérés, suppressions par les utilisateurs |
| **Web History** (historique web) | Sites visités, recherches |
| **Downloads** (téléchargements) | Fichiers téléchargés |
| **Recent Documents** (documents récents) | Fichiers ouverts récemment |
| **USB Devices** (périphériques USB) | Périphériques connectés |
| **Email** (messagerie) | Communications |
| **Images** | Photos, captures d’écran |
| **Archives** | Contenu ZIP et RAR |

---

## 📄 Analyse des fichiers

### Consulter les fichiers

```
# Afficheur de contenu (panneau du bas)
- Hex         : Vue hexadécimale brute
- Text        : Contenu textuel
- Application : Affichage du contenu avec son rendu
- Strings     : Chaînes de caractères extraites
- Metadata    : Métadonnées du fichier
- Results     : Résultats d’analyse
- Indexed Text : Texte indexé interrogeable
```

### Propriétés des fichiers

| Propriété | Description |
|----------|-------------|
| Name (nom) | Nom du fichier |
| Location (emplacement) | Chemin complet |
| Size (taille) | Taille du fichier |
| Created (création) | Date et heure de création |
| Modified (modification) | Dernière modification |
| Accessed (accès) | Dernier accès |
| MD5/SHA1/SHA256 | Valeurs des empreintes |
| Known (connu) | Statut NSRL |
| MIME Type (type MIME) | Type détecté |

### Extraire des fichiers

```
# Extraire un fichier
Clic droit → Extract File(s) (Extraire les fichiers)

# Extraire plusieurs fichiers
Sélectionner les fichiers → Clic droit → Extract (Extraire)

# Extraire vers un emplacement précis
Actions → Extract to... (Extraire vers...)
```

### Récupérer des fichiers supprimés

> **Précision :** une entrée supprimée ou une signature repérée ne garantit pas un fichier récupérable intégralement ; le contenu dépend des données encore présentes. Le carving recherche des signatures indépendamment des noms et répertoires ([manuel TSK](https://www.sleuthkit.org/sleuthkit/man/fls.html)).

```
Views → Deleted Files

# Affiche :
- Fichiers dans $Recycle.Bin
- Fichiers signalés comme supprimés dans la MFT
- Fichiers récupérés par signatures (PhotoRec)
```

---

## 📊 Analyse chronologique

### Vue chronologique (Timeline)

```
# Ouvrir la chronologie
Tools → Timeline

# Ou
Results → Timeline
```

### Fonctions de la chronologie

| Fonction | Description |
|---------|-------------|
| **Counts View** (comptage) | Fréquence des événements au fil du temps |
| **Details View** (détails) | Événements individuels |
| **List View** (liste) | Liste d’événements sous forme de tableau |
| **Filters** (filtres) | Filtrer par type, date, etc. |

### Types d’événements

| Type | Couleur | Description |
|------|-------|-------------|
| File Modified (modification) | Bleu | Contenu du fichier modifié |
| File Accessed (accès) | Vert | Fichier ouvert |
| File Created (création) | Jaune | Fichier créé |
| File Changed (changement) | Rouge | Métadonnées modifiées |
| Web Activity (activité web) | Violet | Événements du navigateur |

### Filtres de la chronologie

> **Précision :** les dates 2024 ci-dessous sont des exemples fixes. Les horodatages doivent être interprétés avec le fuseau choisi et les caractéristiques du système de fichiers ; les vues et couleurs sont indicatives selon la version.

```
# Filtrer par plage de dates
Start Date (date de début) : 2024-01-01
End Date (date de fin) : 2024-12-31

# Filtrer par type
☑ File System (système de fichiers)
☑ Web Activity (activité web)
☐ Registry (Registre, si analysé)

# Filtrer par source de données
☑ Disk Image 1 (image de disque 1)
☐ Disk Image 2 (image de disque 2)
```

### Exporter la chronologie

```
# Exporter la chronologie
File → Export → Timeline (CSV/TSV)

# Utiliser avec des outils externes
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Recherche par mots-clés

### Créer des listes de mots-clés

```
# Tools → Options → Keyword Search

# Ajouter une liste
1. Cliquer sur "New List" (Nouvelle liste)
2. Donner un nom à la liste
3. Ajouter les mots-clés :
   - Literal : correspondance exacte
   - Regex : expression régulière
   - Substring : sous-chaîne
```

### Mots-clés courants

> **Lecture des exemples :** les termes de recherche anglais restent inchangés, car ce sont les valeurs à rechercher. Ils concernent les finances, les identifiants, la confidentialité et le réseau. Les regex sont des motifs de repérage, pas des validateurs : le motif IP accepte aussi des nombres supérieurs à 255 et les autres motifs peuvent produire des faux positifs.

```
# Données financières
credit card
bank account
swift
iban
bitcoin
wallet

# Identifiants
password
login
username
secret
key

# Données sensibles
confidential
secret
classified
internal only

# Réseau
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Exemples d’expressions régulières

| Objectif | Expression régulière |
|---------|-------|
| Adresse e-mail | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| Adresse IP | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Téléphone (États-Unis) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Carte bancaire | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| Numéro de sécurité sociale américain (SSN) | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Lancer une recherche

```
# Recherche ponctuelle
Keyword Search (en haut à droite) → Saisir le terme → Search (Rechercher)

# Utiliser des listes de mots-clés
Clic droit sur la source → Run Ingest Modules → Keyword Search
```

---

## 📝 Rapports

### Générer un rapport

```
# Générer un rapport
Tools → Generate Report

# Ou
Clic droit sur le dossier → Generate Report (Générer un rapport)
```

### Types de rapports

| Type | Description |
|------|-------------|
| **HTML Report** | Rapport web incluant les images |
| **Excel Report** | Format tableur |
| **Text Report** | Texte brut |
| **TSK Body File** | Fichier de données pour construire une chronologie |
| **Files - FS** | Rapport sur le système de fichiers |
| **Portable Case** | Sous-ensemble partageable du dossier |

### Options du rapport HTML

```
# Sélectionner les données à inclure
☑ Tagged Results (résultats étiquetés)
☑ Interesting Items (éléments intéressants)
☑ Hashset Hits (correspondances d’empreintes)
☑ Keyword Hits (correspondances de mots-clés)
☑ Recent Activity (Web) (activité web récente)
☑ Accounts (comptes)
☑ File Types (types de fichiers)

# Options
☑ Include thumbnails (inclure les miniatures)
☑ Include metadata (inclure les métadonnées)
```

### Dossier portable (Portable Case)

```
# Créer un dossier portable à partager
Generate Report → Portable Case

# Contient :
- Éléments sélectionnés
- Résultats d’analyse
- Ouverture possible dans Autopsy
```

---

## 💻 Outils en ligne de commande (Sleuth Kit)

### Commandes de Sleuth Kit

Autopsy utilise The Sleuth Kit (TSK) comme moteur d’analyse. Les commandes suivantes s’utilisent en ligne de commande (CLI) :

### Informations sur l’image

> **Précision TSK :** `-o` indique un décalage en secteurs, pas en octets ; `2048` doit correspondre à l’image examinée. L’option `icat -r` active des techniques de récupération pour les fichiers supprimés, au-delà de la simple lecture suggérée par le commentaire original ([fls](https://www.sleuthkit.org/sleuthkit/man/fls.html), [icat](https://www.sleuthkit.org/sleuthkit/man/icat.html)). La ligne « récupérer tous les fichiers » de `tsk_recover` est aussi simplifiée : son comportement par défaut ne récupère que les fichiers non alloués ([manuel](https://www.sleuthkit.org/sleuthkit/man/tsk_recover.html)).

```bash
# Obtenir les informations sur l’image
img_stat image.E01
img_stat image.dd

# Obtenir les informations sur le système de fichiers
fsstat -o 2048 image.dd
```

### Lister les fichiers

```bash
# Lister les fichiers
fls -r -o 2048 image.dd

# Lister les fichiers supprimés
fls -r -d -o 2048 image.dd

# Options :
# -r    Récursif
# -d    Fichiers supprimés uniquement
# -o    Décalage (début de la partition)
```

### Récupération de fichiers

```bash
# Récupérer le fichier à partir de son inode
icat -o 2048 image.dd 12345 > recovered_file

# Récupérer le contenu du fichier
icat -r -o 2048 image.dd 12345 > file.txt
```

### Chronologie

```bash
# Créer le fichier body
fls -r -m "/" -o 2048 image.dd > body.txt

# Créer une chronologie
mactime -b body.txt -d > timeline.csv
```

### Calcul d’empreintes

```bash
# Calculer le MD5
md5sum image.dd

# Calculer le SHA256
sha256sum image.dd

# Vérifier l’image
sha256sum -c image.dd.sha256
```

### Outils TSK courants

| Outil | Fonction |
|------|---------|
| `img_stat` | Informations sur l’image |
| `mmls` | Table des partitions |
| `fsstat` | Informations sur le système de fichiers |
| `fls` | Liste des fichiers |
| `icat` | Extraire un fichier à partir de son inode |
| `istat` | Informations sur un inode |
| `blkls` | Extraire l’espace non alloué |
| `srch_strings` | Rechercher des chaînes de caractères |
| `tsk_recover` | Récupérer tous les fichiers |
| `mactime` | Créer une chronologie |

---

## 📊 Référence rapide

### Déroulement d’une investigation

```
1. Create Case          → Créer un dossier avec ses informations
2. Add Data Source      → Ajouter une image de disque ou un disque
3. Run Ingest Modules   → Lancer l’analyse automatisée
4. Review Results       → Examiner le contenu extrait
5. Keyword Search       → Rechercher des termes
6. Timeline Analysis    → Reconstituer les événements
7. Tag Evidence         → Étiqueter les éléments importants
8. Generate Report      → Créer le rapport final
```

### Raccourcis clavier

| Raccourci | Action |
|----------|--------|
| Ctrl+N | Nouveau dossier |
| Ctrl+O | Ouvrir un dossier |
| Ctrl+F | Rechercher dans un tableau |
| Ctrl+G | Générer un rapport |
| F5 | Actualiser |

### Emplacements courants à examiner

| Windows | Chemin |
|---------|------|
| Fichiers récents | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Téléchargements | `Users\*\Downloads` |
| Historique du navigateur | `Users\*\AppData\Local\[Browser]\User Data` |
| Fichiers temporaires | `Users\*\AppData\Local\Temp` |
| Corbeille | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Journaux d’événements | `Windows\System32\winevt\Logs` |

---

## 📚 Ressources

- [Site officiel d’Autopsy](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Documentation d’Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

### Aide-mémoire associés
- [Volatility](../../Volatility/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)
- [Wireshark](../../Wireshark/translations/README.fr.md)

---

<p align="center">
  <b>🔍 Maîtrisez l’investigation numérique !</b><br>
  <i>Autopsy — le meilleur allié de l’analyste numérique</i>
</p>
