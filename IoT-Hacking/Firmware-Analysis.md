# 💾 Firmware Analysis Cheatsheet

---

## 📥 Firmware Acquisition

```bash
# Download from vendor website
# Capture OTA update (MITM)
# Dump from device (UART, SPI, JTAG)
# Extract from mobile app
```

---

## 🔍 Initial Analysis

```bash
# File type
file firmware.bin

# Strings
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropy (detect compression/encryption)
binwalk -E firmware.bin
```

---

## 📦 Binwalk Extraction

```bash
# Scan firmware
binwalk firmware.bin

# Extract all
binwalk -e firmware.bin

# Extract to directory
binwalk -e -C output firmware.bin

# Recursive extraction
binwalk -eM firmware.bin

# Specific signatures
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ File Systems

### SquashFS
```bash
# Extract
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Repack
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# Mount
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Extract with ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Secrets & Credentials

```bash
# Search for credentials
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Find SSH keys
find . -name "id_rsa*"
find . -name "*.pem"

# Find config files
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Binary Analysis

```bash
# Find ELF binaries
find . -type f -exec file {} \; | grep ELF

# Analyze with Ghidra
ghidraRun

# Find hardcoded strings
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Tools

| Tool | Purpose |
|------|---------|
| **Binwalk** | Extraction |
| **firmware-mod-kit** | Modify firmware |
| **EMBA** | Automated analysis |
| **Ghidra** | Binary RE |
| **firmwalker** | Search for secrets |

---

## 📋 Firmware Analysis Checklist

```markdown
□ Identify file type
□ Check entropy (encrypted?)
□ Extract with binwalk
□ Mount filesystem
□ Search for credentials
□ Analyze binaries
□ Check for backdoors
□ Look for debug interfaces
```

---

**Back to IoT:** [📡 IoT Hacking](./README.md)
