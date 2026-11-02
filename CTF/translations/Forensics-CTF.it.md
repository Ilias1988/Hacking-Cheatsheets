# 🔬 Scheda di riferimento per le CTF di analisi forense

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Forensics-CTF.md) • [🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Analisi dei file

```bash
# Tipo di file
file unknown_file

# Visualizzazione esadecimale (hex dump)
xxd file | head
hexdump -C file | head

# Stringhe
strings -n 6 file
strings -e l file    # Little-endian

# Estrazione di file dai dati grezzi (file carving)
binwalk file
binwalk -e file      # Estrai
foremost file
```

---

## 🖼️ Steganografia

### Immagini

```bash
# Metadati
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Tutti i metodi

# Analisi dei bit meno significativi (LSB)
stegsolve.jar        # Strumento con interfaccia grafica

# Stringhe nell'immagine
strings image.png | grep -i flag
```

### Audio
```bash
# Spettrogramma (Audacity)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Cerca dati nascosti nelle frequenze
```

---

> **Nota su Audacity:** il percorso `Analyze → Plot Spectrum` mostra lo spettro della selezione, non lo spettrogramma nel tempo. Per quest'ultimo si usa la [vista Spectrogram](https://manual.audacityteam.org/man/spectrogram_view.html); vedi anche [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html). La voce originale è conservata.

## 🧠 Analisi forense della memoria

> **Nota sulle versioni:** i comandi `imageinfo`, `--profile` e `procdump` qui riportati sono esempi di Volatility 2. Volatility 3 usa tabelle di simboli e una sintassi diversa, senza profili: consulta la [documentazione ufficiale](https://volatility3.readthedocs.io/en/latest/vol2to3.html).

### Volatility
```bash
# Identifica il profilo
volatility -f dump.raw imageinfo

# Elenco dei processi
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Cronologia dei comandi
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Estrai la memoria del processo
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# File
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

> **Nota di integrità:** il comando di montaggio originale non imposta la sola lettura. Per un'analisi forense, lavora su una copia e proteggi l'immagine dalle scritture.

## 💽 Analisi forense dei dischi

```bash
# Monta l'immagine
mount -o loop image.dd /mnt/disk

# Informazioni sul file system
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Analisi forense di rete (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Estrai i file
tcpflow -r capture.pcap
```

---

## 📋 Checklist di analisi forense

```markdown
□ Verifica il tipo di file (comando file)
□ Cerca la flag tra le stringhe
□ Esamina i metadati (exiftool)
□ Usa Binwalk per cercare file incorporati
□ Usa strumenti di steganografia
□ Analizza la memoria (Volatility)
□ Analizza i PCAP (Wireshark)
```

---

**Torna alle CTF:** [🏁 Panoramica CTF](./README.it.md)
