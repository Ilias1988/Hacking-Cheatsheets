# 📷 ExifTool — aide-mémoire d’extraction des métadonnées

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Outil d’extraction des métadonnées
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Investigation numérique">
</p>

---

## 📋 Table des matières

- [Qu’est-ce qu’ExifTool](#-quest-ce-quexiftool)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Lire les métadonnées](#-lire-les-métadonnées)
- [Écrire des métadonnées](#-écrire-des-métadonnées)
- [Supprimer les métadonnées](#-supprimer-les-métadonnées)
- [Types de fichiers](#-types-de-fichiers)
- [OSINT et investigation numérique](#-osint-et-investigation-numérique)
- [Traitement par lots](#-traitement-par-lots)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce qu’ExifTool

**ExifTool** est un outil en ligne de commande puissant pour lire, écrire et modifier les métadonnées des fichiers. Une balise (*tag*) est un champ de métadonnées nommé. L’OSINT désigne le renseignement de sources ouvertes. ExifTool est essentiel pour :

- 🔍 **OSINT** — Extraire la localisation et les informations sur l’appareil depuis des images
- 🔬 **Investigation numérique** — Analyser les métadonnées des documents
- 🔐 **Vie privée** — Supprimer les métadonnées sensibles
- 📸 **Photographie** — Gérer les métadonnées des photos
- 📄 **Documents** — Analyser les métadonnées des fichiers PDF et Office

### Pourquoi les métadonnées comptent

| Usage | Exemple |
| ---------- | --------- |
| **Géolocalisation** | Coordonnées GPS dans les photos |
| **Informations sur l’appareil** | Modèle d’appareil photo, numéro de série |
| **Horodatages** | Date de création ou de modification du fichier |
| **Auteur** | Personne ayant créé le document |
| **Logiciel** | Application ayant créé le fichier |
| **Historique des modifications** | Horodatages des modifications |

---

## 🚀 Installation

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Ou
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

> **Précision :** les distributions Windows actuelles exigent aussi le dossier `exiftool_files` à côté de l’exécutable. Le `12.xx` affiché plus bas est un exemple historique, pas une version attendue ([installation officielle](https://exiftool.org/install.html#Windows)).

```bash
# Télécharger depuis le site officiel
https://exiftool.org/

# Extraire exiftool(-k).exe
# Renommer en exiftool.exe
# Ajouter au PATH ou utiliser directement
```

### Vérifier l’installation

```bash
exiftool -ver
# Sortie : 12.xx
```

---

## 💻 Utilisation de base

### Syntaxe de base

```bash
# Lire les métadonnées
exiftool [OPTIONS] FILE

# Écrire des métadonnées
exiftool -TAG=VALUE FILE

# Plusieurs fichiers
exiftool *.jpg
exiftool -r directory/
```

### Options courantes

| Option | Description |
| -------- | ------------- |
| `-r` | Récursif (traiter les sous-répertoires) |
| `-v` | Sortie détaillée |
| `-n` | Sans conversion d’affichage (valeurs brutes) |
| `-s` | Noms courts des balises |
| `-G` | Afficher le nom du groupe pour chaque balise |
| `-j` | Sortie JSON |
| `-csv` | Sortie CSV |
| `-overwrite_original` | Ne pas créer de sauvegarde |

---

## 📖 Lire les métadonnées

### Afficher toutes les métadonnées

```bash
# Toutes les métadonnées
exiftool image.jpg

# Sortie détaillée (verbose)
exiftool -v image.jpg

# Sortie très détaillée
exiftool -v3 image.jpg
```

### Balises précises

```bash
# Une seule balise
exiftool -GPSLatitude image.jpg

# Plusieurs balises
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Exclure des balises
exiftool --MakerNotes image.jpg
```

### Lecture par groupe

```bash
# Afficher toutes les balises EXIF
exiftool -EXIF:all image.jpg

# Afficher toutes les balises IPTC
exiftool -IPTC:all image.jpg

# Afficher toutes les balises XMP
exiftool -XMP:all image.jpg

# Afficher toutes les balises GPS
exiftool -GPS:all image.jpg

# Afficher les noms de groupes
exiftool -G image.jpg
```

### Groupes courants de métadonnées

| Groupe | Description |
| ------- | ------------- |
| EXIF | Données de l’appareil photo ou de l’image |
| IPTC | Métadonnées de presse |
| XMP | Métadonnées extensibles Adobe |
| GPS | Données de localisation |
| MakerNotes | Données propres à l’appareil photo |
| File | Informations générales sur le fichier |
| Composite | Valeurs calculées |

### Formats de sortie

> **Précision :** `-htmlDump` produit une représentation hexadécimale HTML destinée à l’analyse bas niveau ; ce n’est pas un simple tableau HTML des balises ([documentation officielle](https://exiftool.org/exiftool_pod.html#htmlDump-OFFSET)).

```bash
# Sortie JSON
exiftool -j image.jpg

# Sortie CSV
exiftool -csv image.jpg

# Sortie HTML
exiftool -htmlDump image.jpg > output.html

# Séparation par tabulations
exiftool -t image.jpg

# Format personnalisé
exiftool -p '$filename: $make $model' image.jpg
```

---

## ✏ Écrire des métadonnées

### Écrire une seule balise

```bash
# Définir l’auteur
exiftool -Author="John Doe" image.jpg

# Définir les droits d’auteur
exiftool -Copyright="© 2024 Company" image.jpg

# Définir le commentaire
exiftool -Comment="My photo" image.jpg
```

### Écrire plusieurs balises

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Écrire des coordonnées GPS

> **Précision :** les références N/S et E/W comptent pour les coordonnées EXIF ; une longitude négative seule ne remplace pas toujours la référence d’hémisphère attendue. Le format et le groupe de destination doivent être vérifiés ([FAQ GPS](https://exiftool.org/faq.html#Q14)).

```bash
# Définir les coordonnées GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Avec les références d’hémisphère
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# À partir du format d’URL Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Écrire la date et l’heure

> **Précision :** `AllDates` est un raccourci pour `DateTimeOriginal`, `CreateDate` et `ModifyDate`, pas pour tous les horodatages du fichier ([documentation officielle](https://exiftool.org/exiftool_pod.html#WRITING-EXAMPLES)).

```bash
# Définir la date et l’heure
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Définir toutes les dates
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Décaler les dates
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Ajouter 1 an
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Soustraire 7 jours
```

### Copier les métadonnées entre fichiers

```bash
# Copier toutes les métadonnées
exiftool -TagsFromFile source.jpg destination.jpg

# Copier des balises précises
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Copier vers plusieurs fichiers
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

## 🗑 Supprimer les métadonnées

### Supprimer toutes les métadonnées

> **Précision :** `-all=` ne garantit pas l’effacement de toute information dans tous les formats ; certaines métadonnées sont nécessaires à leur fonctionnement. Les fichiers de sauvegarde peuvent encore contenir les informations initiales, et `-overwrite_original` évite la création d’une nouvelle sauvegarde, sans renforcer l’effacement dans le fichier ([FAQ officielle](https://exiftool.org/faq.html#Q32)).

```bash
# Supprimer toutes les métadonnées (en gardant le fichier utilisable)
exiftool -all= image.jpg

# Supprimer sans sauvegarde
exiftool -overwrite_original -all= image.jpg

# Traiter plusieurs fichiers
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Supprimer des métadonnées précises

```bash
# Supprimer uniquement les données GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Supprimer uniquement les données EXIF
exiftool -EXIF:all= image.jpg

# Supprimer les commentaires
exiftool -Comment= image.jpg

# Supprimer les informations d’auteur
exiftool -Author= -Creator= -Artist= image.jpg
```

### Suppression selon le type de fichier

> **Précision :** lecture et écriture ne sont pas équivalentes : DOCX, XLSX et PPTX sont en lecture seule dans ExifTool, donc la commande Office ci-dessous ne les nettoie pas ([formats officiels](https://exiftool.org/#supported)). Pour les PDF, les mises à jour sont incrémentales et réversibles : d’anciennes métadonnées restent dans le fichier ([documentation PDF](https://exiftool.org/TagNames/PDF.html)).

```bash
# Images
exiftool -all= *.jpg *.png *.gif

# PDF
exiftool -all= document.pdf

# Fichiers Office
exiftool -all= *.docx *.xlsx *.pptx

# Vidéos
exiftool -all= video.mp4
```

### Nettoyer pour protéger la vie privée

```bash
# Supprimer toutes les informations d’identification
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 Types de fichiers

### Types de fichiers pris en charge (400+)

| Catégorie | Formats |
| ---------- | --------- |
| **Images** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Photos RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Vidéo** | MP4, MOV, AVI, MKV, WebM |
| **Audio** | MP3, WAV, FLAC, M4A, OGG |
| **Documents** | PDF, DOCX, XLSX, PPTX |
| **Archives** | ZIP (prise en charge limitée) |

### Commandes propres aux formats

```bash
# Métadonnées PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Métadonnées vidéo
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Métadonnées audio
exiftool -Artist -Album -Title -Duration audio.mp3

# Documents Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT et investigation numérique

### Extraire la localisation GPS

> **Précision :** l’URL Google Maps ci-dessous réutilise les valeurs affichées ; leur format et l’hémisphère peuvent demander une conversion en coordonnées décimales signées. Le simple affichage ne garantit pas une URL correctement interprétée ([FAQ GPS](https://exiftool.org/faq.html#Q14)).

```bash
# Afficher les coordonnées GPS
exiftool -GPS:all image.jpg

# Coordonnées GPS lisibles
exiftool -GPSPosition image.jpg

# Degrés décimaux
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# URL Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Identifier l’appareil

```bash
# Informations sur l’appareil photo
exiftool -Make -Model -SerialNumber image.jpg

# Logiciel utilisé
exiftool -Software -CreatorTool image.jpg

# Informations sur l’objectif (si disponibles)
exiftool -Lens -LensModel -FocalLength image.jpg

# Informations détaillées sur l’appareil photo
exiftool -MakerNotes:all image.jpg
```

### Analyse chronologique

```bash
# Toutes les dates et heures
exiftool -time:all image.jpg

# Horodatages du système de fichiers
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Horodatages EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Rechercher les incohérences de dates
exiftool -a -G1 -s -time:all image.jpg
```

### Investigation de documents

```bash
# Analyse PDF
exiftool -a -G1 document.pdf

# Historique du fichier Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Rechercher les informations d’auteur
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Authenticité d’une image

> **Précision :** les métadonnées peuvent être modifiées ou absentes. Un logiciel, une date ou une coordonnée est un indice à recouper, pas une preuve d’authenticité ou de localisation ; l’absence de mention d’un outil ne prouve pas non plus l’absence de stéganographie.

```bash
# Rechercher un logiciel de retouche
exiftool -Software -HistorySoftwareAgent image.jpg

# Comparer la miniature à l’image
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Rechercher des modifications
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Requêtes OSINT utiles

```bash
# Extraire toutes les informations d’identification
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Rechercher des outils de stéganographie
exiftool -Software image.jpg | grep -i "steg\|hide"

# Extraire la miniature intégrée
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Rechercher des traces de réseaux sociaux
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

## ⚙ Traitement par lots

### Traiter plusieurs fichiers

```bash
# Tous les JPG du répertoire courant
exiftool *.jpg

# Parcourir les sous-répertoires
exiftool -r directory/

# Extensions précises
exiftool -ext jpg -ext png directory/
```

### Supprimer les métadonnées par lots

```bash
# Supprimer dans toutes les images
exiftool -all= -overwrite_original -r directory/

# Uniquement certains types
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Renommer des fichiers par lots

> **Précision :** les exemples utilisent la syntaxe des shells Unix ; les guillemets dépendent du shell. La ligne avec compteur est conservée de l’original, mais `<` demande une copie depuis une balise ou une expression : le texte `photo_%04c.%%e` n’est pas une balise source garantie valide ([syntaxe officielle](https://exiftool.org/exiftool_pod.html#RENAMING-EXAMPLES)).

```bash
# Renommer selon la date
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Renommer selon le modèle d’appareil photo
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Renommer avec un compteur
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Copier des métadonnées par lots

```bash
# Copier le GPS d’un fichier vers tous les autres
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Exporter en CSV

```bash
# Exporter les métadonnées en CSV
exiftool -csv *.jpg > metadata.csv

# Champs précis
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Traitement conditionnel

```bash
# Uniquement les fichiers avec GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Uniquement les fichiers d’un appareil photo précis
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Fichiers modifiés après une date
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Référence rapide

### Commandes les plus utilisées

| Objectif | Commande |
| --------- | --------- |
| Afficher toutes les métadonnées | `exiftool image.jpg` |
| Afficher le GPS | `exiftool -GPS:all image.jpg` |
| Supprimer toutes les métadonnées | `exiftool -all= image.jpg` |
| Supprimer le GPS | `exiftool -GPS:all= image.jpg` |
| Sortie JSON | `exiftool -j image.jpg` |
| Récursif | `exiftool -r directory/` |
| Informations sur l’appareil photo | `exiftool -Make -Model image.jpg` |
| Horodatages | `exiftool -time:all image.jpg` |

### Balises courantes

| Balise | Description |
| ----- | ------------- |
| `-Make` | Fabricant de l’appareil photo |
| `-Model` | Modèle de l’appareil photo |
| `-DateTimeOriginal` | Date de prise de vue |
| `-GPSLatitude` | Latitude GPS |
| `-GPSLongitude` | Longitude GPS |
| `-Author` | Auteur du fichier |
| `-Copyright` | Informations sur les droits d’auteur |
| `-Software` | Logiciel de retouche |
| `-ImageWidth` | Largeur de l’image |
| `-ImageHeight` | Hauteur de l’image |

### Liste de vérification de confidentialité

```bash
# 1. Examiner les métadonnées présentes
exiftool -a -G1 image.jpg

# 2. Rechercher le GPS
exiftool -GPS:all image.jpg

# 3. Rechercher des informations d’identification
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Tout supprimer
exiftool -all= -overwrite_original image.jpg

# 5. Vérifier la suppression
exiftool image.jpg
```

### Liste de vérification d’investigation

```bash
# 1. Préserver l’original (faire d’abord une copie !)
cp image.jpg image_original.jpg

# 2. Exporter toutes les métadonnées
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Extraire le GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Chronologie
exiftool -time:all -G1 image.jpg

# 5. Informations sur l’appareil
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Rechercher des altérations
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Ressources

- [Site officiel d’ExifTool](https://exiftool.org/)
- [Documentation d’ExifTool](https://exiftool.org/TagNames/)
- [Forum ExifTool](https://exiftool.org/forum/)
- [Types de fichiers pris en charge](https://exiftool.org/#supported)

### Aide-mémoire associés
- [Autopsy](../../Autopsy/translations/README.fr.md)
- [Volatility](../../Volatility/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)

---

<p align="center">
  <b>📷 Maîtrisez l’analyse des métadonnées !</b><br>
  <i>ExifTool — Chaque fichier raconte une histoire</i>
</p>
