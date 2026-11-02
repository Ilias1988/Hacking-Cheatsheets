# 🖼️ OSINT sulle immagini

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota interpretativa:** i giudizi “migliore per i volti” riprendono l’originale e non costituiscono una classifica verificata. Una somiglianza visiva o un’anomalia di compressione, da sola, non dimostra l’identità di una persona né una manipolazione: serve conferma da altre evidenze.

---

## 🔍 Ricerca inversa delle immagini

| Servizio | URL | Ideale per |
|---------|-----|----------|
| **Google Images** | images.google.com | Uso generale |
| **Yandex** | yandex.com/images | **Volti** (la scelta migliore nell'originale) |
| **TinEye** | tineye.com | Corrispondenze esatte |
| **Bing Images** | bing.com/images | Alternativa |
| **PimEyes** | pimeyes.com | Riconoscimento facciale |

---

## 📷 Estrazione dei metadati (EXIF)

### ExifTool
```bash
# Visualizza tutti i metadati
exiftool image.jpg

# Coordinate GPS
exiftool -gps* image.jpg

# Informazioni sulla fotocamera
exiftool -make -model -datetime image.jpg

# Rimuovi i metadati
exiftool -all= image.jpg
```

### Strumenti online
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Strumento di terze parti accessibile dal browser per esaminare metadati EXIF/IPTC/XMP. Provalo con file non sensibili.

> I metadati possono essere modificati o rimossi: confrontali con altre evidenze.

---

## 🔎 Analisi delle immagini

### Analisi forense
```
Funzionalità di FotoForensics.com:
- ELA (Error Level Analysis, analisi dei livelli di errore)
- Estrazione dei metadati
- Rilevamento di dati nascosti
- Analisi della compressione
```

### Geolocalizzazione tramite immagini
```
1. Estrai i dati GPS dai metadati EXIF
2. Cerca punti di riferimento nell'immagine
3. Confronta con le immagini a livello stradale
4. Esamina il testo su edifici e cartelli
```

---

## 📋 Checklist OSINT sulle immagini

```markdown
□ Ricerca inversa delle immagini (Google, Yandex)
□ Estrai i metadati EXIF
□ Verifica le coordinate GPS
□ Ricerca tramite riconoscimento facciale
□ Verifica la presenza di manipolazioni
□ Cerca il testo nell'immagine tramite OCR
□ Verifica lo storico dell'immagine (TinEye)
```

---

**Torna a OSINT:** [🔍 Panoramica OSINT](./README.it.md)
