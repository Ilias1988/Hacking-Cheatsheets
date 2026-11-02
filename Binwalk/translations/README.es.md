# 🔧 Binwalk - Guía rápida de análisis de firmware

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Herramienta de análisis y extracción de firmware
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 Índice

- [¿Qué es Binwalk?](#-qué-es-binwalk)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Búsqueda de firmas](#-búsqueda-de-firmas)
- [Extracción](#-extracción)
- [Análisis de entropía](#-análisis-de-entropía)
- [Análisis de sistemas de archivos](#-análisis-de-sistemas-de-archivos)
- [Retos CTF](#-retos-ctf)
- [Opciones avanzadas](#-opciones-avanzadas)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es Binwalk?

**Binwalk** es una herramienta rápida y fácil de usar para analizar imágenes de firmware, realizar ingeniería inversa y extraer datos. Resulta fundamental para:

- 🔧 **Análisis de firmware** - Identificar archivos y código integrados
- 🔓 **Hacking de IoT** - Extraer firmware de routers y cámaras
- 🎯 **Retos CTF** - Encontrar datos ocultos
- 🔬 **Análisis forense** - Examinar archivos binarios
- 🔍 **Ingeniería inversa** - Comprender las estructuras de archivos

### Qué puede detectar Binwalk

| Categoría | Ejemplos |
|----------|----------|
| **Datos comprimidos** | gzip, bzip2, lzma, xz, zlib |
| **Archivos contenedores** | tar, zip, rar, 7z, cpio |
| **Sistemas de archivos** | squashfs, cramfs, jffs2, ext, ubifs |
| **Ejecutables** | ELF, PE, ARM, MIPS |
| **Imágenes** | JPEG, PNG, GIF, BMP |
| **Criptografía** | OpenSSL y certificados |
| **Gestores de arranque** | U-Boot y cabeceras ARM |

---

> **Compatibilidad:** esta guía conserva ejemplos centrados en Binwalk 2 y opciones de distintas versiones. Binwalk 3 fue reescrito en Rust; instalar el repositorio actual con `python3 setup.py install` no sigue su sistema de compilación. Comprueba los flags disponibles en tu versión antes de reutilizarlos. Véase el [repositorio oficial](https://github.com/ReFirmLabs/binwalk).

## 🚀 Instalación

### Linux (Kali/Ubuntu)

```bash
# Instalar Binwalk
sudo apt update
sudo apt install binwalk

# Instalar todos los extractores (recomendado)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# O realizar una instalación completa
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Instalar sasquatch para squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Comprobar la instalación

```bash
binwalk --help
binwalk -h
```

---

## 💻 Uso básico

### Sintaxis básica

```bash
# Analizar un archivo (mostrar las firmas)
binwalk firmware.bin

# Extraer archivos
binwalk -e firmware.bin

# Extracción recursiva
binwalk -eM firmware.bin
```

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-e, --extract` | Extraer tipos de archivo conocidos |
| `-M, --matryoshka` | Analizar recursivamente los archivos extraídos |
| `-r, --rm` | Eliminar los archivos recortados después de extraerlos |
| `-q, --quiet` | Modo silencioso |
| `-v, --verbose` | Salida detallada |
| `-d, --depth` | Profundidad de recursión (predeterminada: 8) |
| `-C, --directory` | Directorio de salida |
| `-f, --log` | Guardar el registro en un archivo |

---

## 🔍 Búsqueda de firmas

### Análisis básico

```bash
# Buscar firmas
binwalk firmware.bin

# Ejemplo de salida:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Tipos concretos de firmas

```bash
# Mostrar solo determinadas firmas
binwalk -A firmware.bin          # Opcodes y código ejecutable
binwalk -R firmware.bin          # Bytes sin procesar (archivo de firmas, según el original)
binwalk -B firmware.bin          # Firmas estándar (predeterminado)

# Mostrar todos los opcodes
binwalk -A firmware.bin

# Buscar una cadena o bytes hexadecimales
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

> **Corrección de `-R`:** en la sintaxis histórica, esta opción requiere el patrón de bytes o texto que se desea buscar. `binwalk -R firmware.bin` toma ese argumento como patrón y no constituye por sí solo el análisis del archivo que describe el comentario. Los ejemplos posteriores sí proporcionan patrón y archivo.

### Filtros de inclusión y exclusión

```bash
# Incluir únicamente tipos concretos
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Excluir tipos
binwalk --exclude="jpeg" firmware.bin

# Mostrar solo firmas concretas
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Firmas personalizadas

```bash
# Utilizar un archivo de firmas personalizado
binwalk -m custom_signatures.txt firmware.bin

# Formato del archivo de firmas:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Extracción

### Extracción básica

```bash
# Extraer todos los archivos reconocidos
binwalk -e firmware.bin
# Crea: _firmware.bin.extracted/

# Extraer a un directorio concreto
binwalk -e -C /output/dir firmware.bin

# Extraer recursivamente (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Opciones de extracción

```bash
# Eliminar después de la extracción (limpieza)
binwalk -er firmware.bin

# Limitar la profundidad de recursión
binwalk -eM --depth=3 firmware.bin

# Extracción silenciosa
binwalk -eq firmware.bin

# Extracción detallada
binwalk -ev firmware.bin
```

### Extracción manual (dd)

```bash
# Cuando Binwalk no puede extraer automáticamente
# Obtener el desplazamiento mediante el análisis de Binwalk
binwalk firmware.bin
# Ejemplo: Squashfs en el desplazamiento 1572892

# Extraer mediante dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# O con count (si se conoce el tamaño)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Recorte de tipos concretos (carving)

```bash
# Extraer únicamente tipos de archivo concretos
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Varios tipos
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Formato: tipo:extensión:comando
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Análisis de entropía

### ¿Qué es la entropía?

La entropía mide la aleatoriedad de los datos:
- **Entropía alta (0.9-1.0)** = Datos cifrados o comprimidos
- **Entropía media (0.5-0.8)** = Código ejecutable
- **Entropía baja (0.0-0.4)** = Texto sin formato o datos repetitivos

> **Interpretación:** los rangos anteriores son orientativos. Una entropía alta también puede indicar compresión o datos aleatorios y no demuestra cifrado; la forma de la gráfica depende del tamaño de bloque y del contenido. Las etiquetas de la tabla gráfica siguiente son hipótesis de análisis.

### Generar un gráfico de entropía

```bash
# Calcular la entropía
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Guardar el gráfico de entropía
binwalk -E --save firmware.bin
# Crea: firmware.bin.png

# Con leyenda
binwalk -EJ firmware.bin
```

### Opciones de entropía

```bash
# Establecer el tamaño del bloque
binwalk -E --block=1024 firmware.bin

# Mostrar los valores de entropía (sin gráfico)
binwalk -E --nplot firmware.bin

# Detectar transiciones ascendentes y descendentes
binwalk -E --edge firmware.bin
```

### Interpretación de la entropía

```
Línea alta y plana (~1.0) → Datos cifrados
Patrón con picos (~0.7)   → Datos comprimidos
Línea ondulada (~0.5)     → Código o datos
Línea baja y plana (~0.2) → Texto sin formato
Caída repentina a 0       → Relleno con bytes nulos
```

---

## 💾 Análisis de sistemas de archivos

### Sistemas de archivos habituales en firmware

| Sistema de archivos | Descripción |
|-------------|-------------|
| **SquashFS** | Comprimido y de solo lectura (el más habitual) |
| **JFFS2** | Sistema de archivos flash con journaling |
| **UBIFS** | Alternativa a JFFS2 para memorias flash grandes |
| **CramFS** | Sistema de archivos ROM comprimido |
| **YAFFS** | Yet Another Flash File System (otro sistema de archivos flash) |
| **ext2/3/4** | Sistema de archivos estándar de Linux |

### Extraer SquashFS

```bash
# Extraer con Binwalk
binwalk -e firmware.bin

# Extraer manualmente con unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Montar (si es compatible)
sudo mount -t squashfs squashfs.bin /mnt
```

### Extraer JFFS2

```bash
# Con jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# O con Binwalk
binwalk -e firmware.bin
```

### Extraer UBIFS

```bash
# Con ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analizar los archivos extraídos

```bash
# Después de extraer, explorar el contenido
cd _firmware.bin.extracted/

# Buscar archivos de interés
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Buscar contraseñas y secretos
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Buscar ejecutables
find . -type f -executable
file */bin/*
```

---

## 🎯 Retos CTF

### Escenarios habituales de CTF

#### Archivo oculto en una imagen

```bash
# Analizar la imagen para buscar datos ocultos
binwalk image.png
binwalk image.jpg

# Extraer archivos ocultos
binwalk -e image.png

# Revisar también las cadenas de texto
strings image.png | head -50
```

#### Archivos contenedores anidados

```bash
# Extracción recursiva (matryoshka)
binwalk -eM challenge.bin

# Varias capas de compresión
binwalk -eM --depth=10 nested.bin
```

#### Buscar texto oculto

```bash
# Combinar con strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Secciones cifradas

```bash
# El análisis de entropía revela posibles secciones cifradas
binwalk -E challenge.bin

# Una entropía alta puede indicar cifrado
# Probar contraseñas habituales o buscar la clave en otra parte
```

### Flujo de trabajo de CTF

```bash
# 1. Análisis básico
binwalk challenge.bin

# 2. Comprobar la entropía
binwalk -E challenge.bin

# 3. Extraer todo
binwalk -eM challenge.bin

# 4. Buscar en los archivos extraídos
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Comprobar si hay datos ocultos
binwalk -A challenge.bin    # Opcodes
xxd challenge.bin | head    # Volcado hexadecimal
```

### Detección de esteganografía

```bash
# Buscar datos ocultos en imágenes
binwalk -B suspicious.jpg

# Comprobar si hay datos añadidos al final
binwalk suspicious.png

# Extraerlos si se encuentran
binwalk -e suspicious.jpg

# Comparar el tamaño del archivo con el esperado
ls -la suspicious.jpg
```

---

<a id="-opciones-avanzadas"></a>
## ⚙️ Opciones avanzadas

### Salida hexadecimal (hexdump)

```bash
# Mostrar el volcado hexadecimal en los desplazamientos
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Volcado hexadecimal de un desplazamiento concreto
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Opciones de desplazamiento

```bash
# Comenzar en un desplazamiento
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Analizar únicamente una longitud concreta
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Opciones de rendimiento

```bash
# Utilizar varios procesos
binwalk --threads=4 firmware.bin

# Desactivar determinados plugins
binwalk --disable-plugin=lzma firmware.bin
```

### Formatos de salida

```bash
# Salida CSV
binwalk --csv firmware.bin > results.csv

# Salida JSON
binwalk --json firmware.bin > results.json

# Guardar el registro en un archivo
binwalk -f scan.log firmware.bin
```

### Comparar dos archivos

```bash
# Comparar dos versiones de firmware
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Mostrar las diferencias en hexadecimal
```

---

## 📊 Referencia rápida

### Comandos más utilizados

| Finalidad | Comando |
|---------|---------|
| Analizar un archivo | `binwalk firmware.bin` |
| Extraer todo | `binwalk -e firmware.bin` |
| Extraer recursivamente | `binwalk -eM firmware.bin` |
| Análisis de entropía | `binwalk -E firmware.bin` |
| Búsqueda de opcodes | `binwalk -A firmware.bin` |
| Buscar una cadena | `binwalk -R "pattern" file` |
| Extraer a un directorio | `binwalk -e -C outdir/ file` |
| Extraer y eliminar | `binwalk -er firmware.bin` |

### Firmas de archivo habituales

| Firma | Descripción |
|-----------|-------------|
| `\x1f\x8b\x08` | gzip comprimido |
| `BZh` | bzip2 comprimido |
| `\xfd7zXZ` | xz comprimido |
| `hsqs` | SquashFS (little endian) |
| `sqsh` | SquashFS (big endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | Imagen PNG |
| `\xff\xd8\xff` | Imagen JPEG |
| `PK\x03\x04` | Archivo ZIP |

> **Precisión de firmas:** `UBI#` identifica una cabecera del contenedor UBI, que puede albergar volúmenes UBIFS; no es por sí sola la firma de un sistema de archivos UBIFS. Una coincidencia de firma también puede ser accidental y requiere validar la estructura encontrada.

### Flujo de extracción

```
1. binwalk firmware.bin       → Identificar el contenido
2. binwalk -E firmware.bin    → Comprobar si hay indicios de cifrado
3. binwalk -e firmware.bin    → Extraer
4. cd _firmware.bin.extracted → Explorar
5. find . -name "*" -type f   → Listar todos los archivos
6. grep -r "password" .       → Buscar secretos
```

### Extracción manual

```bash
# Obtener el desplazamiento con Binwalk
binwalk firmware.bin
# Squashfs en 0x180000 (1572864 en decimal)

# Extraer con dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Extraer squashfs
unsquashfs fs.squashfs
```

### Rutas de interés en firmware

| Ruta | Contenido |
|------|----------|
| `/etc/passwd` | Cuentas de usuario |
| `/etc/shadow` | Hashes de contraseñas |
| `/etc/config/` | Configuración del dispositivo |
| `/etc/init.d/` | Scripts de inicio |
| `/usr/bin/` | Binarios de usuario |
| `/www/` | Interfaz web |
| `/etc/ssl/` | Certificados SSL |

---

## 📚 Recursos

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalk Wiki](https://github.com/ReFirmLabs/binwalk/wiki)
- [Introducción al análisis de firmware](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [Análisis de firmware IoT de OWASP](https://owasp.org/www-project-iot-security/)

### Guías rápidas relacionadas
- [ExifTool](../../ExifTool/translations/README.es.md)
- [Volatility](../../Volatility/translations/README.es.md)
- [Autopsy](../../Autopsy/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)

---

<p align="center">
  <b>🔧 ¡Domina el análisis de firmware!</b><br>
  <i>Binwalk - Descubre los secretos del firmware</i>
</p>
