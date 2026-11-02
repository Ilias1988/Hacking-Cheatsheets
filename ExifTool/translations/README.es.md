# 📷 ExifTool: guía rápida de extracción de metadatos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones del original:** Los comandos, etiquetas y valores de ejemplo se conservan. Poder leer un formato no implica poder escribirlo o borrar sus metadatos; debe consultarse la compatibilidad de cada formato y etiqueta. `-all=` no garantiza eliminar toda la información de cualquier archivo. En PDF, ExifTool usa actualizaciones reversibles: los datos anteriores pueden permanecer en el documento, por lo que no equivale a una eliminación segura ([documentación oficial](https://exiftool.org/exiftool_pod.html)). Los comandos de borrado de Office o vídeo pueden no admitir las operaciones descritas. Las fechas `2024` y la salida `12.xx` son ejemplos históricos. `AllDates` es un atajo de determinadas etiquetas de fecha, no de cualquier marca temporal existente. Los metadatos pueden alterarse: por sí solos no prueban autoría, ubicación o autenticidad.


```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Herramienta de extracción de metadatos
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## 📋 Índice

- [Qué es ExifTool](#qué-es-exiftool)
- [Instalación](#instalación)
- [Uso básico](#uso-básico)
- [Lectura de metadatos](#lectura-de-metadatos)
- [Escritura de metadatos](#escritura-de-metadatos)
- [Eliminación de metadatos](#eliminación-de-metadatos)
- [Tipos de archivo](#tipos-de-archivo)
- [OSINT y análisis forense](#osint-y-análisis-forense)
- [Procesamiento por lotes](#procesamiento-por-lotes)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-exiftool"></a>

## 🎯 Qué es ExifTool

**ExifTool** es una potente herramienta de línea de comandos para leer, escribir y manipular metadatos de archivos. Es esencial para:

- 🔍 **OSINT**: extraer de las imágenes la ubicación y la información del dispositivo
- 🔬 **Análisis forense**: analizar los metadatos de los documentos
- 🔐 **Privacidad**: eliminar metadatos sensibles
- 📸 **Fotografía**: gestionar los metadatos de las fotografías
- 📄 **Documentos**: analizar los metadatos de archivos PDF y Office

### Por qué importan los metadatos

| Uso | Ejemplo |
|----------|---------|
| **Geolocalización** | Coordenadas GPS en fotografías |
| **Información del dispositivo** | Modelo de cámara y número de serie |
| **Marcas de tiempo** | Cuándo se creó o modificó el archivo |
| **Autor** | Quién creó el documento |
| **Software** | Qué aplicación creó el archivo |
| **Historial de edición** | Marcas de tiempo de las modificaciones |

---

<a id="instalación"></a>

## 🚀 Instalación

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# O bien
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Descargar desde el sitio oficial
https://exiftool.org/

# Extraer exiftool(-k).exe
# Cambiar el nombre a exiftool.exe
# Añadir a PATH o usar directamente
```

### Verificar la instalación

```bash
exiftool -ver
# Salida: 12.xx
```

---

<a id="uso-básico"></a>

## 💻 Uso básico

### Sintaxis básica

```bash
# Leer metadatos
exiftool [OPTIONS] FILE

# Escribir metadatos
exiftool -TAG=VALUE FILE

# Varios archivos
exiftool *.jpg
exiftool -r directory/
```

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-r` | Recursivo (procesar subdirectorios) |
| `-v` | Salida detallada |
| `-n` | Sin conversión de presentación (valores sin procesar) |
| `-s` | Nombres cortos de etiquetas |
| `-G` | Mostrar el nombre del grupo de cada etiqueta |
| `-j` | Salida JSON |
| `-csv` | Salida CSV |
| `-overwrite_original` | No crear una copia de seguridad |

---

<a id="lectura-de-metadatos"></a>

## 📖 Lectura de metadatos

### Ver todos los metadatos

```bash
# Todos los metadatos
exiftool image.jpg

# Salida detallada
exiftool -v image.jpg

# Salida muy detallada
exiftool -v3 image.jpg
```

### Etiquetas específicas

```bash
# Una etiqueta
exiftool -GPSLatitude image.jpg

# Varias etiquetas
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Excluir etiquetas
exiftool --MakerNotes image.jpg
```

### Lectura por grupos

```bash
# Mostrar todas las etiquetas EXIF
exiftool -EXIF:all image.jpg

# Mostrar todas las etiquetas IPTC
exiftool -IPTC:all image.jpg

# Mostrar todas las etiquetas XMP
exiftool -XMP:all image.jpg

# Mostrar todas las etiquetas GPS
exiftool -GPS:all image.jpg

# Mostrar con nombres de grupo
exiftool -G image.jpg
```

### Grupos habituales de metadatos

| Grupo | Descripción |
|-------|-------------|
| EXIF | Datos de la cámara o imagen |
| IPTC | Metadatos de noticias y prensa |
| XMP | Metadatos extensibles de Adobe |
| GPS | Datos de ubicación |
| MakerNotes | Datos específicos de la cámara |
| File | Información básica del archivo |
| Composite | Valores calculados |

### Formatos de salida

```bash
# Salida JSON
exiftool -j image.jpg

# Salida CSV
exiftool -csv image.jpg

# Salida HTML
exiftool -htmlDump image.jpg > output.html

# Separado por tabulaciones
exiftool -t image.jpg

# Formato personalizado
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="escritura-de-metadatos"></a>

## ✏️ Escritura de metadatos

### Escribir una etiqueta

```bash
# Establecer el autor
exiftool -Author="John Doe" image.jpg

# Establecer los derechos de autor
exiftool -Copyright="© 2024 Company" image.jpg

# Establecer un comentario
exiftool -Comment="My photo" image.jpg
```

### Escribir varias etiquetas

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Escribir coordenadas GPS

```bash
# Establecer las coordenadas GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Con referencias
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# A partir del formato de URL de Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Escribir fecha y hora

```bash
# Establecer la fecha y la hora
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Establecer todas las fechas
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Desplazar fechas
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Añadir un año
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Restar siete días
```

### Copiar metadatos entre archivos

```bash
# Copiar todos los metadatos
exiftool -TagsFromFile source.jpg destination.jpg

# Copiar etiquetas específicas
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Copiar a varios archivos
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="eliminación-de-metadatos"></a>

## 🗑️ Eliminación de metadatos

### Eliminar todos los metadatos

```bash
# Eliminar todos los metadatos (conservando la utilidad del archivo, según el original)
exiftool -all= image.jpg

# Eliminar sin copia de seguridad
exiftool -overwrite_original -all= image.jpg

# Procesar varios archivos
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Eliminar metadatos específicos

```bash
# Eliminar solo los datos GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Eliminar solo EXIF
exiftool -EXIF:all= image.jpg

# Eliminar comentarios
exiftool -Comment= image.jpg

# Eliminar la información del autor
exiftool -Author= -Creator= -Artist= image.jpg
```

### Eliminar de tipos de archivo específicos

```bash
# Imágenes
exiftool -all= *.jpg *.png *.gif

# PDF
exiftool -all= document.pdf

# Archivos de Office
exiftool -all= *.docx *.xlsx *.pptx

# Vídeos
exiftool -all= video.mp4
```

### Limpiar para proteger la privacidad

```bash
# Eliminar toda la información identificativa
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="tipos-de-archivo"></a>

## 📁 Tipos de archivo

### Tipos de archivo admitidos (más de 400)

| Categoría | Formatos |
|----------|---------|
| **Imágenes** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Fotografías RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Vídeo** | MP4, MOV, AVI, MKV, WebM |
| **Audio** | MP3, WAV, FLAC, M4A, OGG |
| **Documentos** | PDF, DOCX, XLSX, PPTX |
| **Archivos comprimidos** | ZIP (limitado) |

### Específico de cada tipo de archivo

```bash
# Metadatos de PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Metadatos de vídeo
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Metadatos de audio
exiftool -Artist -Album -Title -Duration audio.mp3

# Documentos de Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="osint-y-análisis-forense"></a>

## 🔍 OSINT y análisis forense

### Extraer la ubicación GPS

```bash
# Mostrar las coordenadas GPS
exiftool -GPS:all image.jpg

# GPS legible para personas
exiftool -GPSPosition image.jpg

# Grados decimales
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# URL de Google Maps
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Identificación del dispositivo

```bash
# Información de la cámara
exiftool -Make -Model -SerialNumber image.jpg

# Software utilizado
exiftool -Software -CreatorTool image.jpg

# Información del objetivo (si está disponible)
exiftool -Lens -LensModel -FocalLength image.jpg

# Información detallada de la cámara
exiftool -MakerNotes:all image.jpg
```

### Análisis de la cronología

```bash
# Todas las fechas y horas
exiftool -time:all image.jpg

# Marcas de tiempo del sistema de archivos
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Marcas de tiempo EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Buscar discrepancias entre fechas
exiftool -a -G1 -s -time:all image.jpg
```

### Análisis forense de documentos

```bash
# Análisis de PDF
exiftool -a -G1 document.pdf

# Historial del archivo de Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Buscar información del autor
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Autenticidad de la imagen

```bash
# Comprobar si aparece software de edición
exiftool -Software -HistorySoftwareAgent image.jpg

# Comparar la miniatura con la imagen
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Buscar modificaciones
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Consultas útiles para OSINT

```bash
# Extraer toda la información identificativa
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Buscar herramientas de esteganografía
exiftool -Software image.jpg | grep -i "steg\|hide"

# Extraer la miniatura incrustada
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Buscar rastros de redes sociales
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="procesamiento-por-lotes"></a>

## ⚙️ Procesamiento por lotes

### Procesar varios archivos

```bash
# Todos los JPG del directorio actual
exiftool *.jpg

# Recursivo
exiftool -r directory/

# Extensiones específicas
exiftool -ext jpg -ext png directory/
```

### Eliminar metadatos por lotes

```bash
# Eliminar de todas las imágenes
exiftool -all= -overwrite_original -r directory/

# Solo tipos específicos
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Cambiar nombres de archivos por lotes

```bash
# Cambiar el nombre según la fecha
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Cambiar el nombre según el modelo de cámara
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Cambiar el nombre con un contador
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Copiar metadatos por lotes

```bash
# Copiar el GPS de un archivo a todos
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Exportar a CSV

```bash
# Exportar metadatos a CSV
exiftool -csv *.jpg > metadata.csv

# Campos específicos
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Procesamiento condicional

```bash
# Solo archivos con GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Solo archivos de una cámara específica
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Archivos modificados después de una fecha
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos más utilizados

| Finalidad | Comando |
|---------|---------|
| Ver todos los metadatos | `exiftool image.jpg` |
| Ver GPS | `exiftool -GPS:all image.jpg` |
| Eliminar todos los metadatos | `exiftool -all= image.jpg` |
| Eliminar GPS | `exiftool -GPS:all= image.jpg` |
| Salida JSON | `exiftool -j image.jpg` |
| Recursivo | `exiftool -r directory/` |
| Información de la cámara | `exiftool -Make -Model image.jpg` |
| Marcas de tiempo | `exiftool -time:all image.jpg` |

### Etiquetas habituales

| Etiqueta | Descripción |
|-----|-------------|
| `-Make` | Fabricante de la cámara |
| `-Model` | Modelo de la cámara |
| `-DateTimeOriginal` | Cuándo se tomó la fotografía |
| `-GPSLatitude` | Latitud GPS |
| `-GPSLongitude` | Longitud GPS |
| `-Author` | Autor del archivo |
| `-Copyright` | Información de derechos de autor |
| `-Software` | Software de edición |
| `-ImageWidth` | Anchura de la imagen |
| `-ImageHeight` | Altura de la imagen |

### Lista de comprobación de privacidad

```bash
# 1. Comprobar qué metadatos existen
exiftool -a -G1 image.jpg

# 2. Comprobar GPS
exiftool -GPS:all image.jpg

# 3. Buscar información identificativa
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Eliminar todo
exiftool -all= -overwrite_original image.jpg

# 5. Verificar la eliminación
exiftool image.jpg
```

### Lista de comprobación forense

```bash
# 1. Conservar el original (¡hacer primero una copia!)
cp image.jpg image_original.jpg

# 2. Volcado completo de metadatos
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Extraer GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Cronología
exiftool -time:all -G1 image.jpg

# 5. Información del dispositivo
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Buscar indicios de manipulación
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Recursos

- [Sitio oficial de ExifTool](https://exiftool.org/)
- [Documentación de ExifTool](https://exiftool.org/TagNames/)
- [Foro de ExifTool](https://exiftool.org/forum/)
- [Tipos de archivo admitidos](https://exiftool.org/#supported)

### Guías rápidas relacionadas
- [Autopsy](../../Autopsy/translations/README.es.md)
- [Volatility](../../Volatility/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)

---

<p align="center">
  <b>📷 ¡Domina el análisis de metadatos!</b><br>
  <i>ExifTool: cada archivo cuenta una historia</i>
</p>
