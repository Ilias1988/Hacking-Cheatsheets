# 🔬 Fiche pratique d’investigation numérique pour les CTF

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Analyse de fichiers

```bash
# Type de fichier
file unknown_file

# Vue hexadécimale
xxd file | head
hexdump -C file | head

# Chaînes de caractères
strings -n 6 file
strings -e l file    # Ordre des octets little-endian

# Récupération de fichiers par signature (carving)
binwalk file
binwalk -e file      # Extraire
foremost file
```

---

## 🖼️ Stéganographie

### Images

```bash
# Métadonnées
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Toutes les méthodes

# Analyse des bits de poids faible (LSB)
stegsolve.jar        # Outil graphique

# Chaînes de caractères dans l’image
strings image.png | grep -i flag
```

### Audio

> **Précision :** le chemin de menu anglais ci-dessous est conservé comme repère d’interface. `Plot Spectrum` affiche un spectre sur la sélection ; pour observer les fréquences au fil du temps, utiliser la [vue Spectrogram d’Audacity](https://manual.audacityteam.org/man/spectrogram_view.html).
```bash
# Spectrogramme (Audacity)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Rechercher des données cachées dans les fréquences
```

---

## 🧠 Analyse de la mémoire

### Volatility

> **Note de version :** les commandes `imageinfo`, `--profile` et `procdump` ci-dessous utilisent la syntaxe de Volatility 2. Volatility 3 ne repose plus sur ces profils ; voir les [différences officielles entre les versions](https://volatility3.readthedocs.io/en/latest/vol2to3.html).
```bash
# Identifier le profil
volatility -f dump.raw imageinfo

# Liste des processus
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Historique des commandes
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Extraire la mémoire d’un processus
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Fichiers
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Analyse des disques

> **Précision :** la commande de montage originale n’impose pas la lecture seule. Sur une image de preuve, choisir un montage et une méthode de travail qui évitent de modifier les données ; l’exemple est conservé sans exécution.

```bash
# Monter l’image
mount -o loop image.dd /mnt/disk

# Informations sur le système de fichiers
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Analyse des captures réseau (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Extraire les fichiers
tcpflow -r capture.pcap
```

---

## 📋 Liste de contrôle d’investigation numérique

```markdown
□ Vérifier le type de fichier avec la commande file
□ Rechercher le flag dans les chaînes de caractères
□ Examiner les métadonnées avec exiftool
□ Rechercher des fichiers incorporés avec Binwalk
□ Utiliser les outils de stéganographie
□ Analyser la mémoire avec Volatility
□ Analyser les captures PCAP avec Wireshark
```

---

**Retour aux CTF :** [🏁 Vue d’ensemble des CTF](./README.fr.md)
