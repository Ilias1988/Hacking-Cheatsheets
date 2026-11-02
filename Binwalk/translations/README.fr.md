# 🔧 Binwalk — aide-mémoire d’analyse de firmware

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Outil d’analyse et d’extraction de firmware
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Binwalk ?](#-quest-ce-que-binwalk-)
- [Installation](#-installation)
- [Utilisation de base](#-utilisation-de-base)
- [Recherche de signatures](#-recherche-de-signatures)
- [Extraction](#-extraction)
- [Analyse d’entropie](#-analyse-dentropie)
- [Analyse des systèmes de fichiers](#-analyse-des-systèmes-de-fichiers)
- [Épreuves CTF](#-épreuves-ctf)
- [Options avancées](#-options-avancées)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)


---

## 🎯 Qu’est-ce que Binwalk ?

**Binwalk** est un outil rapide et simple à utiliser pour analyser des images de firmware (micrologiciel), en étudier le fonctionnement par rétro-ingénierie et en extraire des données. Il est essentiel pour :

- 🔧 **Analyse de firmware** — Identifier les fichiers et le code intégrés
- 🔓 **Sécurité offensive IoT** — Extraire le firmware de routeurs ou de caméras
- 🎯 **Épreuves CTF** — Rechercher des données cachées
- 🔬 **Investigation numérique** — Analyser des fichiers binaires
- 🔍 **Rétro-ingénierie** — Comprendre les structures des fichiers

### Ce que Binwalk peut détecter

| Catégorie | Exemples |
|----------|----------|
| **Données compressées** | gzip, bzip2, lzma, xz, zlib |
| **Archives** | tar, zip, rar, 7z, cpio |
| **Systèmes de fichiers** | squashfs, cramfs, jffs2, ext, ubifs |
| **Exécutables** | ELF, PE, ARM, MIPS |
| **Images** | JPEG, PNG, GIF, BMP |
| **Cryptographie** | OpenSSL, certificats |
| **Chargeurs de démarrage** | U-Boot, en-têtes ARM |

---

## 🚀 Installation

> **Version des exemples :** ce guide conserve surtout la syntaxe Python de Binwalk 2. Le projet actuel, Binwalk 3, est réécrit en Rust ; l’installation par `setup.py`, les dépendances et plusieurs options ne lui correspondent pas. Vérifier la génération utilisée avant d’interpréter les exemples ([projet officiel](https://github.com/ReFirmLabs/binwalk)).

### Linux (Kali/Ubuntu)

```bash
# Installer Binwalk
sudo apt update
sudo apt install binwalk

# Installer tous les outils d’extraction (recommandé)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Ou effectuer une installation complète
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Installer sasquatch pour squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Vérifier l’installation

```bash
binwalk --help
binwalk -h
```

---

## 💻 Utilisation de base

### Syntaxe de base

```bash
# Analyser un fichier (afficher les signatures)
binwalk firmware.bin

# Extraire les fichiers
binwalk -e firmware.bin

# Extraction récursive
binwalk -eM firmware.bin
```

### Options courantes

| Option | Description |
|--------|-------------|
| `-e, --extract` | Extraire les types de fichiers reconnus |
| `-M, --matryoshka` | Analyser récursivement les fichiers extraits |
| `-r, --rm` | Supprimer les fichiers découpés après extraction |
| `-q, --quiet` | Mode silencieux |
| `-v, --verbose` | Sortie détaillée |
| `-d, --depth` | Profondeur de récursion (par défaut : 8) |
| `-C, --directory` | Répertoire de sortie |
| `-f, --log` | Enregistrer dans un fichier journal |

---

## 🔍 Recherche de signatures

### Analyse de base

```bash
# Rechercher les signatures
binwalk firmware.bin

# Exemple de sortie :
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Types de signatures précis

> **Correction de l’original :** en version 2, `-R` attend une séquence d’octets, pas un fichier de signatures. La ligne `binwalk -R firmware.bin` traite donc `firmware.bin` comme motif. Les commandes et descriptions historiques restent inchangées ; les fichiers de signatures personnalisés relèvent de `-m` ([code officiel v2](https://raw.githubusercontent.com/ReFirmLabs/binwalk/v2.3.4/src/binwalk/modules/signature.py)).

```bash
# Afficher uniquement certaines signatures
binwalk -A firmware.bin          # Opcodes (codes d’instruction) / code exécutable
binwalk -R firmware.bin          # Octets bruts (fichier de signatures)
binwalk -B firmware.bin          # Signatures standard (par défaut)

# Afficher tous les opcodes
binwalk -A firmware.bin

# Rechercher une chaîne brute ou une séquence hexadécimale
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Filtres d’inclusion et d’exclusion

```bash
# Inclure uniquement certains types
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Exclure des types
binwalk --exclude="jpeg" firmware.bin

# Afficher uniquement certaines signatures
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Signatures personnalisées

```bash
# Utiliser un fichier de signatures personnalisé
binwalk -m custom_signatures.txt firmware.bin

# Format du fichier de signatures :
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Extraction

### Extraction de base

```bash
# Extraire tous les fichiers reconnus
binwalk -e firmware.bin
# Crée : _firmware.bin.extracted/

# Extraire vers un répertoire précis
binwalk -e -C /output/dir firmware.bin

# Extraire récursivement (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Options d’extraction

> **Précision :** détecter une signature ne garantit ni l’intégrité d’un fichier ni son extraction. La version 2 s’appuie sur des règles et des outils externes ; `-r` vise les fichiers découpés après leur traitement, pas la suppression générale des données extraites ([extracteur officiel](https://raw.githubusercontent.com/ReFirmLabs/binwalk/v2.3.4/src/binwalk/modules/extractor.py)).

```bash
# Supprimer après extraction (nettoyage)
binwalk -er firmware.bin

# Limiter la profondeur de récursion
binwalk -eM --depth=3 firmware.bin

# Extraction silencieuse
binwalk -eq firmware.bin

# Extraction détaillée
binwalk -ev firmware.bin
```

### Extraction manuelle (dd)

```bash
# Lorsque Binwalk ne peut pas extraire automatiquement
# Relever le décalage (offset) dans l’analyse Binwalk
binwalk firmware.bin
# Exemple : Squashfs au décalage 1572892

# Extraire avec dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Ou avec count (si la taille est connue)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Extraction par découpage de types précis (carving)

```bash
# Extraire uniquement certains types de fichiers
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Plusieurs types
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Format : type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Analyse d’entropie

### Qu’est-ce que l’entropie ?

> **Précision :** ces seuils et profils sont des heuristiques. Une entropie élevée peut correspondre à de la compression comme à du chiffrement ; elle ne permet pas de les distinguer à elle seule. La version 2 normalise l’entropie entre 0 et 1, tandis que les graphiques de la version 3 utilisent une échelle de 0 à 8 ([analyse officielle](https://github.com/ReFirmLabs/binwalk/wiki/Generating-Entropy-Graphs), [calcul v2](https://raw.githubusercontent.com/ReFirmLabs/binwalk/v2.3.4/src/binwalk/modules/entropy.py)).

L’entropie mesure le caractère aléatoire des données :
- **Entropie élevée (0.9-1.0)** = Données chiffrées ou compressées
- **Entropie moyenne (0.5-0.8)** = Code exécutable
- **Entropie faible (0.0-0.4)** = Texte brut, données répétitives

### Générer un graphique d’entropie

```bash
# Calculer l’entropie
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Enregistrer le graphique d’entropie
binwalk -E --save firmware.bin
# Crée : firmware.bin.png

# Avec légende
binwalk -EJ firmware.bin
```

### Options d’entropie

> **Correction de l’original :** en v2, `-J` enregistre le graphique, au lieu d’ajouter une légende. Le module documente des seuils de transition, sans option `--edge` ; cet exemple doit être lu comme historique et non comme une option garantie ([source v2](https://raw.githubusercontent.com/ReFirmLabs/binwalk/v2.3.4/src/binwalk/modules/entropy.py)).

```bash
# Définir la taille des blocs
binwalk -E --block=1024 firmware.bin

# Afficher les valeurs d’entropie (sans graphique)
binwalk -E --nplot firmware.bin

# Détecter les transitions montantes et descendantes
binwalk -E --edge firmware.bin
```

### Interpréter l’entropie

```
Plateau élevé (~1.0)  → Données chiffrées
Profil avec des pics (~0.7)   → Données compressées
Courbe ondulée (~0.5)       → Code/données
Plateau faible (~0.2)   → Texte brut
Chute soudaine à 0       → Remplissage par des octets nuls
```

---

## 💾 Analyse des systèmes de fichiers

### Systèmes de fichiers courants des firmwares

| Système de fichiers | Description |
|-------------|-------------|
| **SquashFS** | Système compressé en lecture seule (très courant) |
| **JFFS2** | Système de fichiers journalisé pour mémoire flash |
| **UBIFS** | Alternative à JFFS2 adaptée aux grandes mémoires flash |
| **CramFS** | Système de fichiers ROM compressé |
| **YAFFS** | Yet Another Flash File System (système pour mémoire flash) |
| **ext2/3/4** | Systèmes de fichiers Linux classiques |

### Extraire SquashFS

```bash
# Extraction avec Binwalk
binwalk -e firmware.bin

# Extraction manuelle avec unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Monter le système de fichiers (si pris en charge)
sudo mount -t squashfs squashfs.bin /mnt
```

### Extraire JFFS2

```bash
# Avec jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Ou avec Binwalk
binwalk -e firmware.bin
```

### Extraire UBIFS

```bash
# Avec ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analyser les fichiers extraits

```bash
# Explorer après l’extraction
cd _firmware.bin.extracted/

# Rechercher les fichiers intéressants
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Rechercher des mots de passe ou des secrets
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Rechercher les exécutables
find . -type f -executable
file */bin/*
```

---

## 🎯 Épreuves CTF

### Scénarios CTF courants

#### Fichier caché dans une image

```bash
# Analyser l’image pour rechercher des données cachées
binwalk image.png
binwalk image.jpg

# Extraire les fichiers cachés
binwalk -e image.png

# Examiner aussi les chaînes de caractères
strings image.png | head -50
```

#### Archives imbriquées

```bash
# Extraction récursive (matryoshka)
binwalk -eM challenge.bin

# Plusieurs couches de compression
binwalk -eM --depth=10 nested.bin
```

#### Rechercher du texte caché

```bash
# Combiner avec strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Sections chiffrées

```bash
# L’analyse d’entropie révèle le chiffrement
binwalk -E challenge.bin

# Une entropie élevée suggère des données chiffrées
# Essayer des mots de passe courants ou rechercher la clé ailleurs
```

### Déroulement d’une analyse CTF

```bash
# 1. Analyse de base
binwalk challenge.bin

# 2. Examiner l’entropie
binwalk -E challenge.bin

# 3. Tout extraire
binwalk -eM challenge.bin

# 4. Rechercher dans les fichiers extraits
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Rechercher des données cachées
binwalk -A challenge.bin    # Opcodes
xxd challenge.bin | head    # Affichage hexadécimal
```

### Détection de stéganographie

```bash
# Analyser les images pour rechercher des données cachées
binwalk -B suspicious.jpg

# Rechercher des données ajoutées à la fin
binwalk suspicious.png

# Extraire les données trouvées
binwalk -e suspicious.jpg

# Comparer la taille du fichier à la taille attendue
ls -la suspicious.jpg
```

---

<a id="-options-avancées"></a>
## ⚙️ Options avancées

> **Précision sur les versions :** les options avancées mélangent des générations. `-W` est le mode d’affichage/comparaison binaire de la v2 ; `--diff` n’y est pas une option supplémentaire. Le parseur v3 propose des threads et un journal JSON, mais ne reprend pas toutes les options illustrées, notamment `--disable-plugin`, `--csv` et `--json`. Les commandes sont conservées et ne constituent pas une syntaxe universelle ([HexDiff v2](https://raw.githubusercontent.com/ReFirmLabs/binwalk/v2.3.4/src/binwalk/modules/hexdiff.py), [parseur v3](https://raw.githubusercontent.com/ReFirmLabs/binwalk/master/src/cliparser.rs)).

### Affichage hexadécimal (hexdump)

```bash
# Afficher le contenu hexadécimal aux décalages
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Afficher le contenu hexadécimal à un décalage précis
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Options de décalage (offset)

```bash
# Commencer au décalage
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Limiter la longueur analysée
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Options de performance

```bash
# Utiliser plusieurs processus
binwalk --threads=4 firmware.bin

# Désactiver certains plugins
binwalk --disable-plugin=lzma firmware.bin
```

### Formats de sortie

```bash
# Sortie CSV
binwalk --csv firmware.bin > results.csv

# Sortie JSON
binwalk --json firmware.bin > results.json

# Enregistrer dans un fichier journal
binwalk -f scan.log firmware.bin
```

### Comparer deux fichiers

```bash
# Comparer deux versions de firmware
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Affiche les différences en hexadécimal
```

---

## 📊 Référence rapide

### Commandes les plus utilisées

| Objectif | Commande |
|---------|---------|
| Analyser un fichier | `binwalk firmware.bin` |
| Tout extraire | `binwalk -e firmware.bin` |
| Extraction récursive | `binwalk -eM firmware.bin` |
| Analyse d’entropie | `binwalk -E firmware.bin` |
| Recherche d’opcodes | `binwalk -A firmware.bin` |
| Rechercher une chaîne | `binwalk -R "pattern" file` |
| Extraire vers un répertoire | `binwalk -e -C outdir/ file` |
| Extraire et supprimer | `binwalk -er firmware.bin` |

### Signatures de fichiers courantes

> **Précision :** la signature `UBI#` identifie un en-tête de compteur d’effacement UBI ; elle ne prouve pas à elle seule la présence d’un système de fichiers UBIFS. La ligne du tableau reste celle du texte original ([définition du noyau Linux](https://raw.githubusercontent.com/torvalds/linux/master/drivers/mtd/ubi/ubi-media.h)).

| Signature | Description |
|-----------|-------------|
| `\x1f\x8b\x08` | Données compressées gzip |
| `BZh` | Données compressées bzip2 |
| `\xfd7zXZ` | Données compressées xz |
| `hsqs` | SquashFS (petit-boutiste) |
| `sqsh` | SquashFS (gros-boutiste) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | Image PNG |
| `\xff\xd8\xff` | Image JPEG |
| `PK\x03\x04` | Archive ZIP |

### Déroulement d’une extraction

```
1. binwalk firmware.bin       → Identifier le contenu
2. binwalk -E firmware.bin    → Rechercher des indices de chiffrement
3. binwalk -e firmware.bin    → Extraire
4. cd _firmware.bin.extracted → Explorer
5. find . -name "*" -type f   → Lister tous les fichiers
6. grep -r "password" .       → Rechercher des secrets
```

### Extraction manuelle

```bash
# Relever le décalage avec Binwalk
binwalk firmware.bin
# Squashfs à 0x180000 (1572864 en décimal)

# Extraire avec dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Extraire squashfs
unsquashfs fs.squashfs
```

### Emplacements utiles dans un firmware

| Chemin | Contenu |
|------|----------|
| `/etc/passwd` | Comptes utilisateurs |
| `/etc/shadow` | Empreintes de mots de passe |
| `/etc/config/` | Configuration de l’appareil |
| `/etc/init.d/` | Scripts de démarrage |
| `/usr/bin/` | Exécutables utilisateur |
| `/www/` | Interface web |
| `/etc/ssl/` | Certificats SSL |

---

## 📚 Ressources

- [Binwalk sur GitHub](https://github.com/ReFirmLabs/binwalk)
- [Wiki de Binwalk](https://github.com/ReFirmLabs/binwalk/wiki)
- [Introduction à l’analyse de firmware](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [Analyse de firmware IoT — OWASP](https://owasp.org/www-project-iot-security/)

### Aide-mémoire associés
- [ExifTool](../../ExifTool/translations/README.fr.md)
- [Volatility](../../Volatility/translations/README.fr.md)
- [Autopsy](../../Autopsy/translations/README.fr.md)
- [Commandes Linux](../../Linux-Commands/translations/README.fr.md)

---

<p align="center">
  <b>🔧 Maîtrisez l’analyse de firmware !</b><br>
  <i>Binwalk — découvrez les secrets des firmwares</i>
</p>
