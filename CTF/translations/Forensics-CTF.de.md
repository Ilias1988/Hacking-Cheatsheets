# 🔬 Spickzettel für Forensik-CTFs

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

## 📁 Dateianalyse

```bash
# Dateityp
file unknown_file

# Hexadezimale Darstellung
xxd file | head
hexdump -C file | head

# Zeichenfolgen
strings -n 6 file
strings -e l file    # Little-Endian-Reihenfolge

# Dateien anhand ihrer Struktur wiederherstellen (File Carving)
binwalk file
binwalk -e file      # Extrahieren
foremost file
```

---

## 🖼️ Steganografie

### Bilder

```bash
# Metadaten
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Alle Methoden

# LSB-Analyse (niederwertigste Bits)
stegsolve.jar        # Werkzeug mit grafischer Oberfläche

# Zeichenfolgen im Bild
strings image.png | grep -i flag
```

### Audio
```bash
# Spektrogramm (Audacity)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Nach verborgenen Daten in den Frequenzanteilen suchen
```

---

## 🧠 Arbeitsspeicherforensik

### Volatility
```bash
# Profil bestimmen
volatility -f dump.raw imageinfo

# Prozessliste
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Befehlsverlauf
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Prozessspeicher sichern
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Dateien
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Datenträgerforensik

```bash
# Datenträgerabbild einbinden
mount -o loop image.dd /mnt/disk

# Informationen zum Dateisystem
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Netzwerkforensik (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Dateien extrahieren
tcpflow -r capture.pcap
```

---

## 📋 Checkliste für Forensik-CTFs

```markdown
□ Dateityp prüfen (Befehl file)
□ Mit strings nach der Flag suchen
□ Metadaten prüfen (exiftool)
□ Mit Binwalk nach eingebetteten Dateien suchen
□ Steganografiewerkzeuge verwenden
□ Arbeitsspeicher analysieren (Volatility)
□ PCAP-Datei analysieren (Wireshark)
```

---

**Zurück zu CTF:** [🏁 CTF-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Versionshinweis:** Die Aufrufe mit `imageinfo` und `--profile` gehören zu Volatility 2. Volatility 3 verwendet Symboltabellen statt dieser Profile und besitzt eine andere Plugin-/CLI-Struktur. Die Zeilen sind unveränderte historische Referenzen. [Volatility: Änderungen von Version 2 zu 3](https://github.com/volatilityfoundation/volatility3/blob/develop/doc/source/vol2to3.rst).

> **Korrektur zur Audioanalyse:** `Analyze → Plot Spectrum` zeigt ein Frequenzspektrum der Auswahl, kein Spektrogramm mit zeitlichem Verlauf. Der Menüpfad ist eine Bedienungsanweisung und kein Bash-Befehl; die englischen Menübezeichnungen bleiben als Originalreferenz erhalten. [Audacity: Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html).

> **Weitere Voraussetzungen:** Der gezeigte Mount-Aufruf erzwingt keinen schreibgeschützten Zugriff. Partitionsabbilder können zusätzlich einen Offset benötigen. `stegsolve.jar` ist ein Dateiname, nicht auf jedem System ein direkt ausführbarer Befehl. Extraktionsfunktionen und Ausgabefelder hängen von Werkzeugversion und Dateiformat ab. Die Beispiele wurden nicht ausgeführt.
