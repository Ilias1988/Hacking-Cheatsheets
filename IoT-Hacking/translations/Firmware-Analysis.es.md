# 💾 Guía rápida de análisis de firmware

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

> **Aclaración de los ejemplos:** Firmware es el software integrado del dispositivo; OTA significa actualización inalámbrica y MITM, intermediación en la comunicación. La entropía elevada puede indicar tanto compresión como cifrado y no permite distinguirlos por sí sola. `unsquashfs` requiere una imagen de entrada, aunque el primer ejemplo usa el nombre habitual de un directorio de salida. Las opciones de Binwalk dependen de su versión.


---

## 📥 Adquisición del firmware

```bash
# Descargar desde el sitio web del fabricante
# Capturar una actualización OTA (mediante MITM)
# Obtener un volcado del dispositivo (UART, SPI, JTAG)
# Extraer from mobile app
```

---

## 🔍 Análisis inicial

```bash
# Tipo de archivo
file firmware.bin

# Cadenas
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropía (detectar compresión o cifrado)
binwalk -E firmware.bin
```

---

## 📦 Extracción con Binwalk

```bash
# Analizar el firmware
binwalk firmware.bin

# Extraer all
binwalk -e firmware.bin

# Extraer to directory
binwalk -e -C output firmware.bin

# Extracción recursiva
binwalk -eM firmware.bin

# Firmas específicas
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Sistemas de archivos

### SquashFS
```bash
# Extraer
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Volver a empaquetar
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# Montar
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Extraer with ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Secretos y credenciales

```bash
# Buscar credenciales
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Buscar claves SSH
find . -name "id_rsa*"
find . -name "*.pem"

# Buscar archivos de configuración
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Análisis de binarios

```bash
# Buscar binarios ELF
find . -type f -exec file {} \; | grep ELF

# Analizar con Ghidra
ghidraRun

# Buscar cadenas incrustadas
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **Binwalk** | Extracción |
| **firmware-mod-kit** | Modificar el firmware |
| **EMBA** | Análisis automatizado |
| **Ghidra** | Ingeniería inversa de binarios |
| **firmwalker** | Buscar secretos |

---

## 📋 Lista de comprobación de análisis de firmware

```markdown
□ Identificar el tipo de archivo
□ Comprobar la entropía (¿está cifrado?)
□ Extraer con binwalk
□ Montar el sistema de archivos
□ Buscar credenciales
□ Analizar los binarios
□ Buscar puertas traseras
□ Buscar interfaces de depuración
```

---

**Volver a IoT:** [📡 Pruebas de seguridad de IoT](./README.es.md)
