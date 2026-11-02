# 🔬 Hurtigreferanse for digital etterforskning i CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Filanalyse

```bash
# Filtype
file unknown_file

# Heksadesimal visning
xxd file | head
hexdump -C file | head

# Tekststrenger
strings -n 6 file
strings -e l file    # Little-endian

# Hent ut innebygde filer (file carving)
binwalk file
binwalk -e file      # Hent ut
foremost file
```

---

## 🖼️ Steganografi (skjulte data)

### Bilder

```bash
# Metadata
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Alle metoder

# Analyse av minst signifikante bit (LSB)
stegsolve.jar        # Verktøy med grafisk grensesnitt

# Tekststrenger i bildet
strings image.png | grep -i flag
```

### Lyd

> **Rettelse:** Audacitys Plot Spectrum viser et frekvensspekter for et utvalg, ikke et spektrogram over tid. Menystien i originalen er bevart. Se [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html) og [Spectrogram View](https://manual.audacityteam.org/man/spectrogram_view.html). `stegsolve.jar` er dessuten et filnavn, ikke nødvendigvis en kjørbar kommando i et shell.
```bash
# Spektrogram (Audacity; se presiseringen)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Se etter skjulte data i frekvensinnholdet
```

---

## 🧠 Minneetterforskning

> **Versjonsmerknad:** `imageinfo` og `--profile=Win7SP1x64` viser Volatility 2-syntaks. Volatility 3 bruker symboltabeller fremfor profiler; se [Volatilitys migreringsdokumentasjon](https://volatility3.readthedocs.io/en/latest/vol2to3.html).

### Volatility
```bash
# Identifiser profil
volatility -f dump.raw imageinfo

# Prosessliste
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Kommandohistorikk
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Hent ut prosessminne
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Filer
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Disketterforskning

```bash
# Monter diskavbildningen
mount -o loop image.dd /mnt/disk

# Opplysninger om filsystemet
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Nettverksetterforskning (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Hent ut filer
tcpflow -r capture.pcap
```

---

## 📋 Sjekkliste for digital etterforskning

```markdown
□ Kontroller filtypen (file-kommandoen)
□ Søk etter flagg blant tekststrengene
□ Kontroller metadata (exiftool)
□ Bruk Binwalk for å finne innebygde filer
□ Prøv steganografiverktøy
□ Analyser minne (Volatility)
□ Analyser PCAP (Wireshark)
```

---

**Tilbake til CTF:** [🏁 CTF-oversikt](./README.nb.md)
