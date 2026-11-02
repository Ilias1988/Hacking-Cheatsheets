# 🖼️ Bilde-OSINT

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Omvendt bildesøk

| Tjeneste | URL | Best egnet til |
|---------|-----|----------|
| **Google Images** | images.google.com | Generelle søk |
| **Yandex** | yandex.com/images | **Ansikter** (best, ifølge originalen) |
| **TinEye** | tineye.com | Nøyaktige treff |
| **Bing Images** | bing.com/images | Alternativ |
| **PimEyes** | pimeyes.com | Ansiktsgjenkjenning |

---

## 📷 Uthenting av metadata (EXIF)

> **Presisering:** «Best» i tabellen er originalens vurdering, ikke en dokumentert rangering. Likhet i ansikter eller bilder er et undersøkelsesspor, ikke alene bevis på identitet.

### ExifTool
```bash
# Vis alle metadata
exiftool image.jpg

# GPS-koordinater
exiftool -gps* image.jpg

# Kameraopplysninger
exiftool -make -model -datetime image.jpg

# Fjern metadata
exiftool -all= image.jpg
```

### Nettbaserte verktøy
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Nettleserbasert tredjepartsverktøy for undersøkelse av EXIF/IPTC/XMP. Test med filer som ikke er sensitive.

> Metadata kan endres eller fjernes; kontroller dem mot andre bevis.

---

## 🔎 Bildeanalyse

### Digital bildeetterforskning

> **Presisering:** ELA og andre bildeindikatorer må tolkes i sammenheng med komprimering og bildets historikk; én indikator er ikke alene bevis på manipulering.
```
Funksjoner i FotoForensics.com:
- ELA (Error Level Analysis, analyse av feilnivå)
- Uthenting av metadata
- Deteksjon av skjulte data
- Analyse av komprimering
```

### Geografisk stedfesting fra bilder
```
1. Hent ut GPS-data fra EXIF
2. Søk etter landemerker i bildet
3. Sammenlign med gatebilder
4. Undersøk tekst på bygninger og skilt
```

---

## 📋 Sjekkliste for bilde-OSINT

```markdown
□ Omvendt bildesøk (Google, Yandex)
□ Hent ut EXIF-metadata
□ Kontroller GPS-koordinater
□ Søk med ansiktsgjenkjenning
□ Undersøk mulig manipulering
□ Søk etter bildetekst eller bruk tekstgjenkjenning (OCR)
□ Sjekk bildehistorikken (TinEye)
```

---

**Tilbake til OSINT:** [🔍 OSINT-oversikt](./README.nb.md)
