# 💾 Fiche pratique d’analyse de firmware

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Acquisition du firmware

```bash
# Télécharger depuis le site du fabricant
# Capturer une mise à jour OTA (over-the-air) par interception MITM
# Extraire depuis l’appareil (UART, SPI, JTAG)
# Extraire depuis l’application mobile
```

---

## 🔍 Analyse initiale

```bash
# Type de fichier
file firmware.bin

# Chaînes de caractères
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropie (repérer une compression ou un chiffrement)
binwalk -E firmware.bin
```

> **Précision :** une entropie élevée peut correspondre à des données compressées comme à des données chiffrées ; ce seul indicateur ne permet pas de les distinguer avec certitude.

---

## 📦 Extraction avec Binwalk

```bash
# Analyser le firmware
binwalk firmware.bin

# Tout extraire
binwalk -e firmware.bin

# Extraire dans un répertoire
binwalk -e -C output firmware.bin

# Extraction récursive
binwalk -eM firmware.bin

# Signatures particulières
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Systèmes de fichiers

### SquashFS
```bash
# Extraire
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Reconstruire l’image
mksquashfs output modified.squashfs -comp xz
```

> **Correction du document source :** `unsquashfs` attend une image SquashFS et en extrait le contenu sans la monter. `squashfs-root` est normalement le nom du répertoire de sortie, pas celui d’une image d’entrée ; la première commande est donc ambiguë si ce nom désigne déjà un répertoire. Les commandes originales sont conservées. Voir le [guide officiel d’Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

### JFFS2
```bash
# Monter
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

> **Prérequis de l’exemple :** la destination de `dd` doit bien correspondre au périphérique MTD de test créé pour cette manipulation. Le numéro `0` du document source ne garantit pas cette correspondance sur une autre machine ; écrire sur une mémoire flash réelle en remplacerait le contenu.

### UBIFS
```bash
# Extraire avec ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Secrets et identifiants

```bash
# Rechercher des identifiants
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Rechercher des clés SSH
find . -name "id_rsa*"
find . -name "*.pem"

# Rechercher des fichiers de configuration
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Analyse des binaires

```bash
# Rechercher les binaires ELF
find . -type f -exec file {} \; | grep ELF

# Analyser avec Ghidra
ghidraRun

# Rechercher des chaînes codées en dur
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Outils

| Outil | Utilité |
|------|---------|
| **Binwalk** | Extraction |
| **firmware-mod-kit** | Modification de firmware |
| **EMBA** | Analyse automatisée |
| **Ghidra** | Rétro-ingénierie des binaires |
| **firmwalker** | Recherche de secrets |

---

## 📋 Liste de contrôle de l’analyse de firmware

```markdown
□ Identifier le type de fichier
□ Examiner l’entropie (chiffrement éventuel)
□ Extraire avec binwalk
□ Monter le système de fichiers
□ Rechercher des identifiants
□ Analyser les binaires
□ Rechercher des portes dérobées (backdoors)
□ Rechercher des interfaces de débogage
```

---

**Retour à la rubrique IoT :** [📡 Sécurité des objets connectés](./README.fr.md)
