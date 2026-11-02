# 🔬 Guía de referencia de análisis forense para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Análisis de archivos

```bash
# Tipo de archivo
file unknown_file

# Volcado hexadecimal
xxd file | head
hexdump -C file | head

# Cadenas de texto
strings -n 6 file
strings -e l file    # Little-endian

# Recuperación de archivos por sus firmas (file carving)
binwalk file
binwalk -e file      # Extraer
foremost file
```

---

## 🖼️ Esteganografía

### Imágenes

```bash
# Metadatos
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Todos los métodos

# Análisis de bits menos significativos (LSB)
stegsolve.jar        # Herramienta con interfaz gráfica

# Cadenas de texto en la imagen
strings image.png | grep -i flag
```

### Audio

> **Aclaración:** Plot Spectrum muestra un espectro, no un espectrograma temporal. Se conserva el recorrido de menús en inglés del original. Para localizar patrones a lo largo del tiempo se necesita la vista de espectrograma de la herramienta.
```bash
# Espectrograma (Audacity)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Buscar datos ocultos en las frecuencias
```

---

## 🧠 Análisis forense de memoria

### Volatility

> **Versión:** `imageinfo` y `--profile` pertenecen a Volatility 2. Volatility 3 usa tablas de símbolos y una interfaz de plugins distinta; véase la [guía oficial de migración](https://volatility3.readthedocs.io/en/latest/vol2to3.html).
```bash
# Identificar el perfil
volatility -f dump.raw imageinfo

# Lista de procesos
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Historial de comandos
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Volcar el proceso
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Archivos
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Análisis forense de discos

```bash
# Montar la imagen
mount -o loop image.dd /mnt/disk

# Información del sistema de archivos
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Análisis forense de red (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Extraer archivos
tcpflow -r capture.pcap
```

---

## 📋 Lista de comprobación de análisis forense

```markdown
□ Comprobar el tipo de archivo (comando file)
□ Buscar la flag mediante strings
□ Comprobar metadatos (exiftool)
□ Usar Binwalk para buscar archivos incrustados
□ Herramientas de esteganografía
□ Análisis de memoria (Volatility)
□ Análisis de PCAP (Wireshark)
```

---

**Volver a CTF:** [🏁 Resumen de CTF](./README.es.md)
