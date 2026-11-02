# 💾 Scheda di riferimento per l'analisi del firmware

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Firmware-Analysis.md) • [🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Acquisizione del firmware

```bash
# Scarica dal sito del produttore
# Acquisisci un aggiornamento OTA, via etere (MITM)
# Estrai dalla memoria del dispositivo (UART, SPI, JTAG)
# Estrai dall'app mobile
```

---

## 🔍 Analisi iniziale

```bash
# Tipo di file
file firmware.bin

# Stringhe
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropia (indizi di compressione o cifratura)
binwalk -E firmware.bin
```

> **Nota interpretativa:** un’entropia elevata può essere compatibile sia con compressione sia con cifratura; da sola non distingue con certezza le due condizioni.

---

## 📦 Estrazione con Binwalk

```bash
# Analizza il firmware
binwalk firmware.bin

# Estrai tutto
binwalk -e firmware.bin

# Estrai nella directory indicata
binwalk -e -C output firmware.bin

# Estrazione ricorsiva
binwalk -eM firmware.bin

# Firme specifiche
binwalk -D 'elf:elf' firmware.bin
```

> **Nota sulle versioni:** gli esempi conservano la sintassi dell’originale, tipica di Binwalk 2. Binwalk 3 è una riscrittura in Rust e non tutte le opzioni sono intercambiabili: verifica la guida della versione utilizzata. Vedi il [progetto ufficiale Binwalk](https://github.com/ReFirmLabs/binwalk).

---

## 🗂️ File system

### SquashFS
```bash
# Estrai
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Ricrea l'immagine
mksquashfs output modified.squashfs -comp xz
```

> **Nota sull’originale:** `unsquashfs` richiede un’immagine SquashFS in ingresso; `squashfs-root` indica normalmente la directory di estrazione predefinita, non l’immagine. I comandi originali sono conservati. Vedi la [documentazione di Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

### JFFS2
```bash
# Monta
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Estrai con ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Segreti e credenziali

```bash
# Cerca credenziali
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Trova chiavi SSH
find . -name "id_rsa*"
find . -name "*.pem"

# Trova file di configurazione
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Analisi dei binari

```bash
# Trova binari ELF
find . -type f -exec file {} \; | grep ELF

# Analizza con Ghidra
ghidraRun

# Trova stringhe incorporate nel codice
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|------|---------|
| **Binwalk** | Estrazione |
| **firmware-mod-kit** | Modifica del firmware |
| **EMBA** | Analisi automatizzata |
| **Ghidra** | Reverse engineering dei binari |
| **firmwalker** | Ricerca di segreti |

---

## 📋 Checklist per l'analisi del firmware

```markdown
□ Identifica il tipo di file
□ Esamina l'entropia (possibile cifratura?)
□ Estrai con binwalk
□ Monta il file system
□ Cerca credenziali
□ Analizza i binari
□ Verifica la presenza di backdoor
□ Cerca interfacce di debug
```

---

**Torna a IoT:** [📡 Hacking IoT](./README.it.md)
