# 🖼️ OSINT sur les images

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Recherche inversée d’images

| Service | URL | Usage privilégié |
|---------|-----|----------|
| **Google Images** | images.google.com | Recherche générale |
| **Yandex** | yandex.com/images | **Visages** (préférence du document source) |
| **TinEye** | tineye.com | Correspondances exactes |
| **Bing Images** | bing.com/images | Solution de remplacement |
| **PimEyes** | pimeyes.com | Reconnaissance faciale |

---

## 📷 Extraction des métadonnées (EXIF)

### ExifTool
```bash
# Afficher toutes les métadonnées
exiftool image.jpg

# Coordonnées GPS
exiftool -gps* image.jpg

# Informations sur l’appareil photo
exiftool -make -model -datetime image.jpg

# Supprimer les métadonnées
exiftool -all= image.jpg
```

### Outils en ligne
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Outil tiers d’inspection EXIF/IPTC/XMP dans le navigateur. Faire les essais avec des fichiers non sensibles.

> Les métadonnées peuvent être modifiées ou supprimées ; les recouper avec d’autres éléments de preuve.

---

## 🔎 Analyse d’images

### Analyse forensique
```
Fonctions de FotoForensics.com :
- ELA (Error Level Analysis, analyse des niveaux d’erreur)
- Extraction des métadonnées
- Détection de données cachées
- Analyse de la compression
```

### Géolocalisation à partir d’images
```
1. Extraire les coordonnées GPS des métadonnées EXIF
2. Rechercher les points de repère visibles dans l’image
3. Comparer avec des vues de rue
4. Examiner le texte sur les bâtiments et les panneaux
```

---

## 📋 Liste de contrôle de l’OSINT sur les images

```markdown
□ Effectuer une recherche inversée d’images (Google, Yandex)
□ Extraire les métadonnées EXIF
□ Vérifier les coordonnées GPS
□ Effectuer une recherche par reconnaissance faciale
□ Rechercher des traces de retouche
□ Rechercher le texte de l’image / utiliser la reconnaissance optique de caractères (OCR)
□ Vérifier l’historique de l’image (TinEye)
```

---

**Retour à l’OSINT :** [🔍 Vue d’ensemble de l’OSINT](./README.fr.md)
