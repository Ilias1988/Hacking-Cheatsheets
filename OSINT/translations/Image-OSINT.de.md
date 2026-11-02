# 🖼️ Bild-OSINT

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Umgekehrte Bildersuche

| Dienst | URL | Besonders geeignet für |
|---------|-----|----------|
| **Google Images** | images.google.com | Allgemeine Suche |
| **Yandex** | yandex.com/images | **Gesichter** (laut Original am besten) |
| **TinEye** | tineye.com | Exakte Treffer |
| **Bing Images** | bing.com/images | Alternative |
| **PimEyes** | pimeyes.com | Gesichtserkennung |

---

## 📷 Metadaten extrahieren (EXIF)

### ExifTool
```bash
# Alle Metadaten anzeigen
exiftool image.jpg

# GPS-Koordinaten
exiftool -gps* image.jpg

# Kamerainformationen
exiftool -make -model -datetime image.jpg

# Metadaten entfernen
exiftool -all= image.jpg
```

### Online-Werkzeuge
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Browserbasiertes Werkzeug eines Drittanbieters zur Prüfung von EXIF-/IPTC-/XMP-Daten. Mit nicht vertraulichen Dateien testen.

> Metadaten lassen sich verändern oder entfernen; sie sollten durch andere Belege bestätigt werden.

---

## 🔎 Bildanalyse

### Forensische Analyse
```
Funktionen von FotoForensics.com:
- ELA (Error Level Analysis; Analyse von Kompressionsunterschieden)
- Metadaten extrahieren
- Verborgene Daten erkennen
- Kompression analysieren
```

### Standort aus Bildern bestimmen
```
1. GPS-Daten aus EXIF extrahieren
2. Nach markanten Orten oder Objekten im Bild suchen
3. Mit Straßenansichten vergleichen
4. Texte an Gebäuden und auf Schildern prüfen
```

---

## 📋 Checkliste für Bild-OSINT

```markdown
□ Umgekehrte Bildersuche (Google, Yandex)
□ EXIF-Metadaten extrahieren
□ GPS-Koordinaten prüfen
□ Suche mit Gesichtserkennung
□ Auf Manipulationen untersuchen
□ Bildtext suchen beziehungsweise OCR verwenden
□ Bildverlauf prüfen (TinEye)
```

---

**Zurück zu OSINT:** [🔍 OSINT-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Einordnung der Beispiele:** Aussagen wie „am besten für Gesichter“ sind Wertungen der englischen Vorlage, kein hier durchgeführter Vergleich. Ähnliche Gesichter oder Bildtreffer belegen keine Identität. Fehlende GPS-Daten schließen eine Aufnahme am vermuteten Ort nicht aus; vorhandene Metadaten können verändert sein. ELA zeigt Kompressionsunterschiede und ist allein kein Beweis einer Manipulation. Der Befehl mit `-all=` verändert Metadaten der Datei; er ist kein reiner Anzeigeaufruf.
