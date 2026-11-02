# 💾 Hurtigreferanse for fastvareanalyse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Innhenting av fastvare

```bash
# Last ned fra leverandørens nettsted
# Fang opp en trådløs oppdatering (OTA) via MITM
# Les ut fra enheten (UART, SPI, JTAG)
# Hent ut fra mobilappen
```

---

## 🔍 Innledende analyse

> **Presisering:** Høy entropi kan være et tegn på komprimering eller kryptering, men er ikke alene bevis på kryptering.


```bash
# Filtype
file firmware.bin

# Tekststrenger
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropi (tegn på komprimering eller kryptering)
binwalk -E firmware.bin
```

---

## 📦 Utpakking med Binwalk

> **Versjonsmerknad:** Eksemplene er bevart fra originalen. Binwalk finnes i ulike hovedversjoner, og prosjektets nåværende utgave er omskrevet i Rust. Kontroller flaggene for versjonen du bruker; se [Binwalks egen dokumentasjon](https://github.com/ReFirmLabs/binwalk).


```bash
# Undersøk fastvaren
binwalk firmware.bin

# Pakk ut alt
binwalk -e firmware.bin

# Pakk ut til en katalog
binwalk -e -C output firmware.bin

# Rekursiv utpakking
binwalk -eM firmware.bin

# Bestemte signaturer
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Filsystemer

### SquashFS

> **Rettelse:** `unsquashfs` pakker ut en SquashFS-avbildning; det monterer ikke et filsystem. `squashfs-root` er normalt utdatakatalogen, ikke avbildningen som skal leses. Den opprinnelige kommandoen er bevart; den forutsetter at argumentet faktisk er en avbildningsfil. Se [verktøyets man-side](https://manpages.debian.org/testing/squashfs-tools/unsquashfs.1.en.html).

```bash
# Pakk ut
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Pakk på nytt
mksquashfs output modified.squashfs -comp xz
```

### JFFS2

> **Presisering:** Eksemplet forutsetter et egnet Linux-testmiljø med modulene, rettighetene, størrelsen og enheten `/dev/mtdblock0` som vises. `dd` skriver faktisk til den angitte enheten; oppsettet er ikke universelt.

```bash
# Monter
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Pakk ut med ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Hemmeligheter og påloggingsopplysninger

```bash
# Søk etter påloggingsopplysninger
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Finn SSH-nøkler
find . -name "id_rsa*"
find . -name "*.pem"

# Finn konfigurasjonsfiler
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Analyse av binærfiler

```bash
# Finn ELF-binærfiler
find . -type f -exec file {} \; | grep ELF

# Analyser med Ghidra
ghidraRun

# Finn hardkodede strenger
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **Binwalk** | Utpakking |
| **firmware-mod-kit** | Endre fastvare |
| **EMBA** | Automatisert analyse |
| **Ghidra** | Omvendt utvikling av binærfiler |
| **firmwalker** | Søk etter hemmeligheter |

---

## 📋 Sjekkliste for fastvareanalyse

```markdown
□ Identifiser filtypen
□ Undersøk entropien (kryptert?)
□ Pakk ut med binwalk
□ Monter filsystemet
□ Søk etter påloggingsopplysninger
□ Analyser binærfiler
□ Se etter bakdører
□ Se etter feilsøkingsgrensesnitt
```

---

**Tilbake til IoT:** [📡 IoT-hacking](./README.nb.md)
