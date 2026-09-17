# 🔧 Binwalk - Cheatsheet Analisi Firmware

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Firmware Analysis & Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 Indice

- [Cos'è Binwalk](#-cosè-binwalk)
- [Installazione](#-installazione)
- [Uso Base](#-uso-base)
- [Signature Scanning](#-signature-scanning)
- [Estrazione](#-estrazione)
- [Analisi Entropia](#-analisi-entropia)
- [Analisi File System](#-analisi-file-system)
- [Sfide CTF](#-sfide-ctf)
- [Opzioni Avanzate](#-opzioni-avanzate)
- [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è Binwalk

**Binwalk** è uno strumento veloce e semplice per analizzare, fare reverse engineering ed estrarre dati da immagini firmware. Essenziale per:

- 🔧 **Analisi Firmware** - Identifica file/codice embedded
- 🔓 **IoT Hacking** - Estrai firmware router/telecamere
- 🎯 **Sfide CTF** - Trova dati nascosti
- 🔬 **Forensics** - Analizza file binari
- 🔍 **Reverse Engineering** - Comprendi strutture file

### Cosa Rileva Binwalk

| Categoria | Esempi |
|-----------|--------|
| **Compressed** | gzip, bzip2, lzma, xz, zlib |
| **Archives** | tar, zip, rar, 7z, cpio |
| **File Systems** | squashfs, cramfs, jffs2, ext, ubifs |
| **Executables** | ELF, PE, ARM, MIPS |
| **Images** | JPEG, PNG, GIF, BMP |
| **Crypto** | OpenSSL, certificati |
| **Boot Loaders** | U-Boot, header ARM |

---

## 🚀 Installazione

### Linux (Kali/Ubuntu)

```bash
# Installa binwalk
sudo apt update
sudo apt install binwalk

# Installa tutti gli extractor (consigliato)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Oppure installazione completa
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Installa sasquatch per squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Verifica Installazione

```bash
binwalk --help
binwalk -h
```

---

## 💻 Uso Base

### Sintassi Base

```bash
# Scansiona file (mostra firma)
binwalk firmware.bin

# Estrai file
binwalk -e firmware.bin

# Estrazione ricorsiva
binwalk -eM firmware.bin
```

### Opzioni Comuni

| Opzione | Descrizione |
|---------|-------------|
| `-e, --extract` | Estrai tipi dei file noti |
| `-M, --matryoshka` | Scansione ricorsiva file estratti |
| `-r, --rm` | Cancella file carved dopo estrazione |
| `-q, --quiet` | Modalità silenziosa |
| `-v, --verbose` | Output dettagliato |
| `-d, --depth` | Profondità ricorsione (default: 8) |
| `-C, --directory` | Directory output |
| `-f, --log` | Log su file |

---

## 🔍 Signature Scanning

### Scansione Base

```bash
# Scansiona firma
binwalk firmware.bin

# Esempio output:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Tipi Signature Specifici

```bash
# Mostra solo alcune firme
binwalk -A firmware.bin          # Opcode/codice eseguibile
binwalk -R firmware.bin          # Raw bytes (file signature)
binwalk -B firmware.bin          # Signature standard (default)

# Mostra tutti gli opcode
binwalk -A firmware.bin

# Cerca stringa/hex raw
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Filtri Include/Exclude

```bash
# Includi solo tipi specifici
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Escludi tipi
binwalk --exclude="jpeg" firmware.bin

# Mostra solo firme specifiche
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Firme Personalizzate

```bash
# Usa file firma personalizzato
binwalk -m custom_signatures.txt firmware.bin

# Formato file firma:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Estrazione

### Estrazione Base

```bash
# Estrai tutti i file riconosciuti
binwalk -e firmware.bin
# Crea: _firmware.bin.extracted/

# Estrai in directory specifica
binwalk -e -C /output/dir firmware.bin

# Estrazione ricorsiva (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Opzioni Estrazione

```bash
# Cancella dopo estrazione (pulizia)
binwalk -er firmware.bin

# Limita profondità ricorsione
binwalk -eM --depth=3 firmware.bin

# Estrazione silenziosa
binwalk -eq firmware.bin

# Estrazione dettagliata
binwalk -ev firmware.bin
```

### Estrazione Manuale (dd)

```bash
# Se binwalk non estrae automaticamente
# Prendi offset da scan binwalk
binwalk firmware.bin
# Esempio: Squashfs a offset 1572892

# Estrai con dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# O con count (se nota la dimensione)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Carving di Tipi Specifici

```bash
# Estrai solo tipi di file specifici
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Più tipi
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Formato: tipo:estensione:comando
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Analisi Entropia

### Cos'è l'Entropia?

L'entropia misura la casualità dei dati:
- **Alta entropia (0.9-1.0)** = Dati cifrati/compressi
- **Media entropia (0.5-0.8)** = Codice eseguibile
- **Bassa entropia (0.0-0.4)** = Testo, dati ripetuti

### Genera Grafico Entropia

```bash
# Calcola entropia
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Salva grafico entropia
binwalk -E --save firmware.bin
# Crea: firmware.bin.png

# Con legenda
binwalk -EJ firmware.bin
```

### Opzioni Entropia

```bash
# Imposta block size
binwalk -E --block=1024 firmware.bin

# Mostra valori entropia (no grafico)
binwalk -E --nplot firmware.bin

# Rileva edge salita/discesa
binwalk -E --edge firmware.bin
```

### Interpretazione Entropia

```
Linea piatta alta (~1.0)  → Dati cifrati
Pattern a picchi (~0.7)   → Dati compressi  
Linea ondulata (~0.5)     → Codice/dati
Linea piatta bassa (~0.2) → Testo
Caduta improvvisa a 0     → Padding nullo
```

---

## 💾 Analisi File System

### File System Firmware Comuni

| File System | Descrizione |
|-------------|-------------|
| **SquashFS** | Compressed read-only (più comune) |
| **JFFS2** | Journaling flash file system |
| **UBIFS** | JFFS2 migliorato per grandi flash |
| **CramFS** | Compressed ROM file system |
| **YAFFS** | Yet Another Flash File System |
| **ext2/3/4** | Filesystem Linux standard |

### Estrai SquashFS

```bash
# Estrazione con binwalk
binwalk -e firmware.bin

# Manuale con unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Monta (se supportato)
sudo mount -t squashfs squashfs.bin /mnt
```

### Estrai JFFS2

```bash
# Con jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# O con binwalk
binwalk -e firmware.bin
```

### Estrai UBIFS

```bash
# Con ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analizza File Estratti

```bash
# Dopo estrazione, esplora
cd _firmware.bin.extracted/

# Trova file interessanti
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Cerca password/segreti
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Trova eseguibili
find . -type f -executable
file */bin/*
```

---

## 🎯 Sfide CTF

### Scenari CTF Comuni

#### File Nascosto in Immagine

```bash
# Scansiona immagine per dati nascosti
binwalk image.png
binwalk image.jpg

# Estrai file nascosti
binwalk -e image.png

# Controlla anche le stringhe
strings image.png | head -50
```

#### Archivi Annidati

```bash
# Estrazione ricorsiva (matryoshka)
binwalk -eM challenge.bin

# Più livelli di compressione
binwalk -eM --depth=10 nested.bin
```

#### Trova Testo Nascosto

```bash
# Combina con strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Sezioni Cifrate

```bash
# Analisi entropia rivela cifratura
binwalk -E challenge.bin

# Se entropia alta, probabilmente si tratta di file cifrato
# Prova password comuni o cerca chiave altrove
```

### Workflow CTF

```bash
# 1. Scansione base
binwalk challenge.bin

# 2. Controlla entropia
binwalk -E challenge.bin

# 3. Estrai tutto
binwalk -eM challenge.bin

# 4. Cerca nei file estratti
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Cerca dati nascosti
binwalk -A challenge.bin    # Opcode
xxd challenge.bin | head    # Hex dump
```

### Rilevamento Steganografia

```bash
# Scansiona immagini per dati nascosti
binwalk -B suspicious.jpg

# Controlla dati appesi
binwalk suspicious.png

# Estrai se trovato
binwalk -e suspicious.jpg

# Confronta dimensione file vs attesa
ls -la suspicious.jpg
```

---

## ⚙️ Opzioni Avanzate

### Output Hexdump

```bash
# Mostra hexdump agli offset
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Hexdump offset specifico
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Opzioni Offset

```bash
# Inizia da offset
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Scansiona solo una lunghezza
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Opzioni di Performance

```bash
# Usa più processi
binwalk --threads=4 firmware.bin

# Disabilita plugin specifici
binwalk --disable-plugin=lzma firmware.bin
```

### Formati Output

```bash
# Output CSV
binwalk --csv firmware.bin > results.csv

# Output JSON  
binwalk --json firmware.bin > results.json

# Log su file
binwalk -f scan.log firmware.bin
```

### Diff Due File

```bash
# Confronta due versioni firmware
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Mostra differenze in hex
```

---

## 📊 Riferimento Rapido

### Comandi Più Usati

| Scopo | Comando |
|-------|---------|
| Scansiona file | `binwalk firmware.bin` |
| Estrai tutto | `binwalk -e firmware.bin` |
| Estrazione ricorsiva | `binwalk -eM firmware.bin` |
| Analisi entropia | `binwalk -E firmware.bin` |
| Scansione opcode | `binwalk -A firmware.bin` |
| Cerca stringa | `binwalk -R "pattern" file` |
| Estrai in dir | `binwalk -e -C outdir/ file` |
| Estrai & cancella | `binwalk -er firmware.bin` |

### Signature File Comuni

| Signature | Descrizione |
|-----------|-------------|
| `\x1f\x8b\x08` | gzip compresso |
| `BZh` | bzip2 compresso |
| `\xfd7zXZ` | xz compresso |
| `hsqs` | SquashFS (little endian) |
| `sqsh` | SquashFS (big endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | PNG image |
| `\xff\xd8\xff` | JPEG image |
| `PK\x03\x04` | ZIP archive |

### Workflow Estrazione

```
1. binwalk firmware.bin       → Identifica contenuti
2. binwalk -E firmware.bin    → Controlla cifratura
3. binwalk -e firmware.bin    → Estrai
4. cd _firmware.bin.extracted → Esplora
5. find . -name "*" -type f   → Elenca tutti i file
6. grep -r "password" .       → Trova segreti
```

### Estrazione Manuale

```bash
# Prendi offset da binwalk
binwalk firmware.bin
# Squashfs a 0x180000 (1572864 decimale)

# Estrai con dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Estrai squashfs
unsquashfs fs.squashfs
```

### Percorsi Utili (Firmware)

| Path | Contenuto |
|------|-----------|
| `/etc/passwd` | Account utente |
| `/etc/shadow` | Hash password |
| `/etc/config/` | Configurazione dispositivo |
| `/etc/init.d/` | Script di avvio |
| `/usr/bin/` | Binari utente |
| `/www/` | Interfaccia web |
| `/etc/ssl/` | Certificati SSL |

---

## 📚 Risorse

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalk Wiki](https://github.com/ReFirmLabs/binwalk/wiki)
- [Firmware Analysis 101](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [OWASP IoT Firmware Analysis](https://owasp.org/www-project-iot-security/)

### Cheatsheet Correlati
- [ExifTool](../ExifTool/translations/README.it.md)
- [Volatility](../Volatility/translations/README.it.md)
- [Autopsy](../Autopsy/translations/README.it.md)
- [Linux Commands](../Linux-Commands/translations/README.it.md)

---

<p align="center">
  <b>🔧 Padroneggia l'analisi firmware!</b><br>
  <i>Binwalk - Svela i segreti di ogni firmware</i>
</p>
