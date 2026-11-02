# 🔧 Binwalk – hurtigreferanse for fastvareanalyse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Verktøy for analyse og uttrekking av fastvare
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

> **Presisering om hovedversjoner og slutninger:** Mange installasjons- og flaggeksempler her gjelder eldre Binwalk-utgaver. Binwalk v3 er skrevet om i Rust og har andre installasjonsmåter og delvis andre valg; kontroller dokumentasjonen for den utgaven eksemplene skal brukes med. Kildeeksemplene er bevart. Se [prosjektets offisielle dokumentasjon](https://github.com/ReFirmLabs/binwalk). Linjen med `-R firmware.bin` er misvisende: `-R` brukes for et rått søkemønster i eldre utgaver, ikke som en generell signaturfilbryter. Entropitersklene og grafmønstrene er tommelfingerregler; høy entropi kan skyldes både komprimering og kryptering og beviser ingen av delene. Et signaturtreff eller avvikende filstørrelse må bekreftes med andre undersøkelser.


## 📋 Innholdsfortegnelse

- [Hva er Binwalk](#-hva-er-binwalk)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Signatursøk](#-signatursøk)
- [Uttrekking](#-uttrekking)
- [Entropianalyse](#-entropianalyse)
- [Filsystemanalyse](#-filsystemanalyse)
- [CTF-oppgaver](#-ctf-oppgaver)
- [Avanserte alternativer](#-avanserte-alternativer)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Binwalk

**Binwalk** er et raskt og brukervennlig verktøy for analyse, omvendt utvikling og uttrekking av data fra fastvareavbildninger. Det er nyttig til:

- 🔧 **Fastvareanalyse** – Identifisere innebygde filer og kode
- 🔓 **IoT-testing** – Trekke ut fastvare fra rutere og kameraer
- 🎯 **CTF-oppgaver** – Finne skjulte data
- 🔬 **Digital etterforskning** – Analysere binærfiler
- 🔍 **Omvendt utvikling** – Forstå filstrukturer

### Hva Binwalk kan oppdage

| Kategori | Eksempler |
|----------|----------|
| **Komprimerte data** | gzip, bzip2, lzma, xz, zlib |
| **Arkiver** | tar, zip, rar, 7z, cpio |
| **Filsystemer** | squashfs, cramfs, jffs2, ext, ubifs |
| **Kjørbare filer** | ELF, PE, ARM, MIPS |
| **Bilder** | JPEG, PNG, GIF, BMP |
| **Kryptografi** | OpenSSL, sertifikater |
| **Oppstartslastere** | U-Boot, ARM-hoder |

---

## 🚀 Installasjon

### Linux (Kali/Ubuntu)

```bash
# Installer binwalk
sudo apt update
sudo apt install binwalk

# Installer alle uttrekkingsverktøy (anbefalt i originalen)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Alternativt: fullstendig installasjon
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Installer sasquatch for squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Kontroller installasjonen

```bash
binwalk --help
binwalk -h
```

---

## 💻 Grunnleggende bruk

### Grunnleggende syntaks

```bash
# Undersøk en fil (vis signaturer)
binwalk firmware.bin

# Trekk ut filer
binwalk -e firmware.bin

# Rekursiv uttrekking
binwalk -eM firmware.bin
```

### Vanlige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-e, --extract` | Trekk ut kjente filtyper |
| `-M, --matryoshka` | Undersøk uttrukne filer rekursivt |
| `-r, --rm` | Slett utskårne filer etter uttrekking |
| `-q, --quiet` | Stille modus |
| `-v, --verbose` | Detaljert utdata |
| `-d, --depth` | Rekursjonsdybde (standard: 8) |
| `-C, --directory` | Utdatamappe |
| `-f, --log` | Skriv logg til fil |

---

## 🔍 Signatursøk

### Grunnleggende undersøkelse

```bash
# Søk etter signaturer
binwalk firmware.bin

# Eksempel på utdata:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Bestemte signaturtyper

```bash
# Vis bare bestemte signaturer
binwalk -A firmware.bin          # Maskinkodeinstruksjoner (opcodes) / kjørbar kode
binwalk -R firmware.bin          # Rå byte (signaturfil, slik originalen angir; se presiseringen)
binwalk -B firmware.bin          # Standardsignaturer (standardinnstilling)

# Vis alle maskinkodeinstruksjoner
binwalk -A firmware.bin

# Søk etter en rå streng eller heksadesimale byte
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Filtre for inkludering og utelukking

```bash
# Inkluder bare bestemte typer
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Utelukk typer
binwalk --exclude="jpeg" firmware.bin

# Vis bare bestemte signaturer
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Egendefinerte signaturer

```bash
# Bruk en egendefinert signaturfil
binwalk -m custom_signatures.txt firmware.bin

# Format på signaturfilen:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Uttrekking

### Grunnleggende uttrekking

```bash
# Trekk ut alle gjenkjente filer
binwalk -e firmware.bin
# Oppretter: _firmware.bin.extracted/

# Trekk ut til en bestemt mappe
binwalk -e -C /output/dir firmware.bin

# Trekk ut rekursivt (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Alternativer for uttrekking

```bash
# Slett etter uttrekking (opprydding)
binwalk -er firmware.bin

# Begrens rekursjonsdybden
binwalk -eM --depth=3 firmware.bin

# Uttrekking med begrenset utdata
binwalk -eq firmware.bin

# Uttrekking med detaljert utdata
binwalk -ev firmware.bin
```

### Manuell uttrekking (dd)

```bash
# Når binwalk ikke kan trekke ut automatisk
# Finn byteforskyvningen i resultatene fra binwalk
binwalk firmware.bin
# Eksempel: Squashfs ved byteforskyvning 1572892

# Trekk ut med dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Eller med count (hvis størrelsen er kjent)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Utskjæring av bestemte filtyper

```bash
# Trekk ut bare bestemte filtyper
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Flere typer
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Format: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Entropianalyse

### Hva er entropi?

Entropi måler graden av uforutsigbarhet i data:
- **Høy entropi (0.9-1.0)** = Krypterte/komprimerte data
- **Middels entropi (0.5-0.8)** = Kjørbar kode
- **Lav entropi (0.0-0.4)** = Ren tekst eller gjentakende data

### Lag en entropigraf

```bash
# Beregn entropi
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Lagre entropigrafen
binwalk -E --save firmware.bin
# Oppretter: firmware.bin.png

# Med tegnforklaring
binwalk -EJ firmware.bin
```

### Alternativer for entropianalyse

```bash
# Angi blokkstørrelse
binwalk -E --block=1024 firmware.bin

# Vis entropiverdier (uten graf)
binwalk -E --nplot firmware.bin

# Oppdag stigende og fallende overganger
binwalk -E --edge firmware.bin
```

### Tolking av entropi

```
Høy, flat linje (~1.0)  → Krypterte data
Spisst mønster (~0.7)   → Komprimerte data  
Bølgete linje (~0.5)    → Kode/data
Lav, flat linje (~0.2)  → Ren tekst
Brått fall til 0        → Utfylling med nullbyte
```

---

## 💾 Filsystemanalyse

### Vanlige filsystemer i fastvare

| Filsystem | Beskrivelse |
|-------------|-------------|
| **SquashFS** | Komprimert og skrivebeskyttet (vanligst) |
| **JFFS2** | Journalførende filsystem for flashminne |
| **UBIFS** | Videreutvikling for større flashminne enn JFFS2 |
| **CramFS** | Komprimert ROM-filsystem |
| **YAFFS** | Yet Another Flash File System (filsystem for flashminne) |
| **ext2/3/4** | Vanlig Linux-filsystem |

### Trekk ut SquashFS

```bash
# Uttrekking med Binwalk
binwalk -e firmware.bin

# Manuelt med unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Monter (hvis det støttes)
sudo mount -t squashfs squashfs.bin /mnt
```

### Trekk ut JFFS2

```bash
# Med jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Eller med binwalk
binwalk -e firmware.bin
```

### Trekk ut UBIFS

```bash
# Med ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analyser uttrukne filer

```bash
# Utforsk filene etter uttrekking
cd _firmware.bin.extracted/

# Finn interessante filer
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Søk etter passord og hemmeligheter
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Finn kjørbare filer
find . -type f -executable
file */bin/*
```

---

## 🎯 CTF-oppgaver

### Vanlige CTF-scenarioer

#### Skjult fil i et bilde

```bash
# Undersøk et bilde for skjulte data
binwalk image.png
binwalk image.jpg

# Trekk ut skjulte filer
binwalk -e image.png

# Undersøk også tekststrenger
strings image.png | head -50
```

#### Arkiver i flere lag

```bash
# Rekursiv uttrekking (matryoshka)
binwalk -eM challenge.bin

# Flere lag med komprimering
binwalk -eM --depth=10 nested.bin
```

#### Finn skjult tekst

```bash
# Kombiner med strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Krypterte områder

```bash
# Entropianalyse kan gi tegn på kryptering (se presiseringen)
binwalk -E challenge.bin

# Høy entropi kan tyde på kryptering (se presiseringen)
# Prøv vanlige passord eller finn nøkkelen et annet sted
```

### Arbeidsflyt for CTF

```bash
# 1. Grunnleggende undersøkelse
binwalk challenge.bin

# 2. Undersøk entropien
binwalk -E challenge.bin

# 3. Trekk ut alt
binwalk -eM challenge.bin

# 4. Søk i uttrukne filer
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Se etter skjulte data
binwalk -A challenge.bin    # Maskinkodeinstruksjoner
xxd challenge.bin | head    # Heksadesimal visning
```

### Oppdaging av steganografi

```bash
# Se etter skjulte data i bilder
binwalk -B suspicious.jpg

# Se etter data lagt til på slutten
binwalk suspicious.png

# Trekk ut eventuelle funn
binwalk -e suspicious.jpg

# Sammenlign filstørrelsen med forventet størrelse
ls -la suspicious.jpg
```

---

<a id="-avanserte-alternativer"></a>
## ⚙️ Avanserte alternativer

### Heksadesimal utdata

```bash
# Vis heksadesimale data ved byteforskyvninger
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Vis heksadesimale data ved en bestemt byteforskyvning
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Alternativer for byteforskyvning

```bash
# Start ved en byteforskyvning
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Undersøk bare den angitte lengden
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Ytelsesalternativer

```bash
# Bruk flere prosesser
binwalk --threads=4 firmware.bin

# Deaktiver bestemte tillegg
binwalk --disable-plugin=lzma firmware.bin
```

### Utdataformater

```bash
# CSV-utdata
binwalk --csv firmware.bin > results.csv

# JSON-utdata  
binwalk --json firmware.bin > results.json

# Skriv logg til fil
binwalk -f scan.log firmware.bin
```

### Sammenlign to filer

```bash
# Sammenlign to fastvareversjoner
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Viser forskjeller i heksadesimal form
```

---

## 📊 Hurtigreferanse

### Mest brukte kommandoer

| Formål | Kommando |
|---------|---------|
| Undersøk en fil | `binwalk firmware.bin` |
| Trekk ut alt | `binwalk -e firmware.bin` |
| Rekursiv uttrekking | `binwalk -eM firmware.bin` |
| Entropianalyse | `binwalk -E firmware.bin` |
| Søk etter maskinkodeinstruksjoner | `binwalk -A firmware.bin` |
| Søk etter en streng | `binwalk -R "pattern" file` |
| Trekk ut til en mappe | `binwalk -e -C outdir/ file` |
| Trekk ut og slett | `binwalk -er firmware.bin` |

### Vanlige filsignaturer

| Signatur | Beskrivelse |
|-----------|-------------|
| `\x1f\x8b\x08` | gzip-komprimert |
| `BZh` | bzip2-komprimert |
| `\xfd7zXZ` | xz-komprimert |
| `hsqs` | SquashFS (little-endian bytefølge) |
| `sqsh` | SquashFS (big-endian bytefølge) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | PNG-bilde |
| `\xff\xd8\xff` | JPEG-bilde |
| `PK\x03\x04` | ZIP-arkiv |

### Arbeidsflyt for uttrekking

```
1. binwalk firmware.bin       → Identifiser innholdet
2. binwalk -E firmware.bin    → Se etter tegn på kryptering
3. binwalk -e firmware.bin    → Trekk ut
4. cd _firmware.bin.extracted → Utforsk
5. find . -name "*" -type f   → List alle filer
6. grep -r "password" .       → Finn hemmeligheter
```

### Manuell uttrekking

```bash
# Hent byteforskyvningen fra binwalk
binwalk firmware.bin
# Squashfs ved 0x180000 (1572864 desimalt)

# Trekk ut med dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Trekk ut squashfs
unsquashfs fs.squashfs
```

### Nyttige filplasseringer (fastvare)

| Sti | Innhold |
|------|----------|
| `/etc/passwd` | Brukerkontoer |
| `/etc/shadow` | Passordhasher |
| `/etc/config/` | Enhetskonfigurasjon |
| `/etc/init.d/` | Oppstartsskript |
| `/usr/bin/` | Brukerprogrammer |
| `/www/` | Nettgrensesnitt |
| `/etc/ssl/` | SSL-sertifikater |

---

## 📚 Ressurser

- [Binwalk på GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalks wiki](https://github.com/ReFirmLabs/binwalk/wiki)
- [Innføring i fastvareanalyse](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [OWASP – fastvareanalyse for IoT](https://owasp.org/www-project-iot-security/)

### Relaterte hurtigreferanser
- [ExifTool](../../ExifTool/translations/README.nb.md)
- [Volatility](../../Volatility/translations/README.nb.md)
- [Autopsy](../../Autopsy/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)

---

<p align="center">
  <b>🔧 Bli fortrolig med fastvareanalyse!</b><br>
  <i>Binwalk – pakk ut hemmelighetene i fastvaren</i>
</p>
