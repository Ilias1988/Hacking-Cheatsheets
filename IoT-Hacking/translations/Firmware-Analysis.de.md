# 💾 Spickzettel zur Firmware-Analyse

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Firmware beschaffen

```bash
# Von der Website des Herstellers herunterladen
# OTA-Update aufzeichnen (MITM)
# Speicherinhalt vom Gerät auslesen (UART, SPI, JTAG)
# Aus der mobilen App extrahieren
```

---

## 🔍 Erste Analyse

```bash
# Dateityp
file firmware.bin

# Zeichenfolgen
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropie (Hinweise auf Kompression oder Verschlüsselung)
binwalk -E firmware.bin
```

---

## 📦 Extraktion mit Binwalk

```bash
# Firmware untersuchen
binwalk firmware.bin

# Alles extrahieren
binwalk -e firmware.bin

# In ein Verzeichnis extrahieren
binwalk -e -C output firmware.bin

# Rekursive Extraktion
binwalk -eM firmware.bin

# Bestimmte Signaturen
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Dateisysteme

### SquashFS
```bash
# Extrahieren
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Neu packen
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# Einbinden
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Mit ubi_reader extrahieren
ubireader_extract_files image.ubi
```

---

## 🔐 Secrets und Zugangsdaten

```bash
# Nach Zugangsdaten suchen
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# SSH-Schlüssel suchen
find . -name "id_rsa*"
find . -name "*.pem"

# Konfigurationsdateien suchen
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Analyse von Binärdateien

```bash
# ELF-Binärdateien suchen
find . -type f -exec file {} \; | grep ELF

# Mit Ghidra analysieren
ghidraRun

# Fest im Programm hinterlegte Zeichenfolgen suchen
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **Binwalk** | Extraktion |
| **firmware-mod-kit** | Firmware verändern |
| **EMBA** | Automatisierte Analyse |
| **Ghidra** | Reverse Engineering von Binärdateien |
| **firmwalker** | Nach Secrets suchen |

---

## 📋 Checkliste zur Firmware-Analyse

```markdown
□ Dateityp bestimmen
□ Entropie prüfen (verschlüsselt?)
□ Mit binwalk extrahieren
□ Dateisystem einbinden
□ Nach Zugangsdaten suchen
□ Binärdateien analysieren
□ Auf Hintertüren prüfen
□ Nach Debug-Schnittstellen suchen
```

---

**Zurück zur IoT-Übersicht:** [📡 IoT Hacking](./README.de.md)


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Extraktion:** Die Binwalk-Optionen entsprechen teilweise der älteren v2-Befehlszeile. Binwalk v3 wurde in Rust neu geschrieben; Optionen und externe Extraktionswerkzeuge müssen zur verwendeten Version passen. Hohe Entropie kann sowohl Kompression als auch Verschlüsselung anzeigen und unterscheidet die beiden nicht zuverlässig. Ein Signaturtreffer ist ein Analysehinweis, kein Beweis für ein vollständig extrahierbares Dateisystem. [Binwalk-Projekt](https://github.com/ReFirmLabs/binwalk), [Binwalk-v2-Handbuch](https://manpages.debian.org/unstable/binwalk/binwalk.1.en.html).

> **Dateisysteme und Pfade:** `unsquashfs` erwartet ein SquashFS-Abbild oder ein entsprechendes Blockgerät; `squashfs-root` ist normalerweise der Ausgabeordner. Es extrahiert Dateien ohne Mount-Vorgang. Die JFFS2-Beispiele setzen geeignete Kernelmodule und ein korrekt zugeordnetes MTD-Gerät voraus; `/dev/mtdblock0` bezeichnet nicht auf jedem System den vorgesehenen RAM-Bereich. Verzeichnisse wie `filesystem/` und die relativen Pfade `etc/shadow` setzen den passenden Arbeitsordner voraus. Der Zugriff auf solche Dateien beweist nicht, dass darin Klartextpasswörter stehen. [Unsquashfs](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).
