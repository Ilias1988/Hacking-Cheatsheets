# 🖼️ OSINT de imágenes

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Aclaración:** Las preferencias de buscadores reflejan el criterio del original. Una coincidencia visual o un resultado de ELA no demuestra por sí solo identidad ni manipulación; debe contrastarse con otras evidencias.


---

## 🔍 Búsqueda inversa de imágenes

| Servicio | URL | Más útil para |
|---------|-----|----------|
| **Google Images** | images.google.com | General |
| **Yandex** | yandex.com/images | **Rostros** (preferido del original) |
| **TinEye** | tineye.com | Coincidencias exactas |
| **Bing Images** | bing.com/images | Alternativa |
| **PimEyes** | pimeyes.com | Reconocimiento facial |

---

## 📷 Extracción de metadatos (EXIF)

### ExifTool
```bash
# Ver todos los metadatos
exiftool image.jpg

# Coordenadas GPS
exiftool -gps* image.jpg

# Información de la cámara
exiftool -make -model -datetime image.jpg

# Eliminar metadatos
exiftool -all= image.jpg
```

### Herramientas en línea
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Herramienta de terceros para inspeccionar EXIF/IPTC/XMP desde el navegador. Probar con archivos no sensibles.

> Los metadatos pueden editarse o eliminarse; deben corroborarse con otras evidencias.

---

## 🔎 Análisis de imágenes

### Análisis forense
```
Funciones de FotoForensics.com:
- ELA (análisis del nivel de error)
- Extracción de metadatos
- Detección de datos ocultos
- Análisis de compresión
```

### Geolocalización a partir de imágenes
```
1. Extraer los datos GPS de EXIF
2. Buscar puntos de referencia en la imagen
3. Comparar con vistas a nivel de calle
4. Comprobar el texto de edificios y carteles
```

---

## 📋 Lista de comprobación de OSINT de imágenes

```markdown
□ Búsqueda inversa de imágenes (Google, Yandex)
□ Extraer los metadatos EXIF
□ Comprobar las coordenadas GPS
□ Búsqueda por reconocimiento facial
□ Analizar posibles manipulaciones
□ Buscar el texto de la imagen mediante OCR
□ Comprobar el historial de la imagen (TinEye)
```

---

**Volver a OSINT:** [🔍 Índice de OSINT](./README.es.md)
