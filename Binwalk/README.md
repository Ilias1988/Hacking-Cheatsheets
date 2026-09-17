# 🔧 Binwalk - Firmware Analysis Cheatsheet

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

## 📋 Table of Contents

- [What is Binwalk](#-what-is-binwalk)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Signature Scanning](#-signature-scanning)
- [Extraction](#-extraction)
- [Entropy Analysis](#-entropy-analysis)
- [File System Analysis](#-file-system-analysis)
- [CTF Challenges](#-ctf-challenges)
- [Advanced Options](#-advanced-options)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Binwalk

**Binwalk** is a fast, easy-to-use tool for analyzing, reverse engineering, and extracting data from firmware images. Essential for:

- 🔧 **Firmware Analysis** - Identify embedded files/code
- 🔓 **IoT Hacking** - Extract router/camera firmware
- 🎯 **CTF Challenges** - Find hidden data
- 🔬 **Forensics** - Analyze binary files
- 🔍 **Reverse Engineering** - Understand file structures

### What Binwalk Can Detect

| Category | Examples |
|----------|----------|
| **Compressed** | gzip, bzip2, lzma, xz, zlib |
| **Archives** | tar, zip, rar, 7z, cpio |
| **File Systems** | squashfs, cramfs, jffs2, ext, ubifs |
| **Executables** | ELF, PE, ARM, MIPS |
| **Images** | JPEG, PNG, GIF, BMP |
| **Crypto** | OpenSSL, certificates |
| **Boot Loaders** | U-Boot, ARM headers |

---

## 🚀 Installation

### Linux (Kali/Ubuntu)

```bash
# Install binwalk
sudo apt update
sudo apt install binwalk

# Install all extractors (recommended)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Or comprehensive install
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Install sasquatch for squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Verify Installation

```bash
binwalk --help
binwalk -h
```

---

## 💻 Basic Usage

### Basic Syntax

```bash
# Scan file (show signatures)
binwalk firmware.bin

# Extract files
binwalk -e firmware.bin

# Recursive extraction
binwalk -eM firmware.bin
```

### Common Options

| Option | Description |
|--------|-------------|
| `-e, --extract` | Extract known file types |
| `-M, --matryoshka` | Recursively scan extracted files |
| `-r, --rm` | Delete carved files after extraction |
| `-q, --quiet` | Quiet mode |
| `-v, --verbose` | Verbose output |
| `-d, --depth` | Recursion depth (default: 8) |
| `-C, --directory` | Output directory |
| `-f, --log` | Log to file |

---

## 🔍 Signature Scanning

### Basic Scan

```bash
# Scan for signatures
binwalk firmware.bin

# Output example:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Specific Signature Types

```bash
# Only show certain signatures
binwalk -A firmware.bin          # Opcodes/executable code
binwalk -R firmware.bin          # Raw bytes (signatures file)
binwalk -B firmware.bin          # Standard signatures (default)

# Show all opcodes
binwalk -A firmware.bin

# Search for raw string/hex
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Include/Exclude Filters

```bash
# Include only specific types
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Exclude types
binwalk --exclude="jpeg" firmware.bin

# Show only specific signatures
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Custom Signatures

```bash
# Use custom signature file
binwalk -m custom_signatures.txt firmware.bin

# Signature file format:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

## 📦 Extraction

### Basic Extraction

```bash
# Extract all recognized files
binwalk -e firmware.bin
# Creates: _firmware.bin.extracted/

# Extract to specific directory
binwalk -e -C /output/dir firmware.bin

# Extract with recursion (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Extraction Options

```bash
# Delete after extraction (cleanup)
binwalk -er firmware.bin

# Limit recursion depth
binwalk -eM --depth=3 firmware.bin

# Quiet extraction
binwalk -eq firmware.bin

# Verbose extraction
binwalk -ev firmware.bin
```

### Manual Extraction (dd)

```bash
# When binwalk can't auto-extract
# Get offset from binwalk scan
binwalk firmware.bin
# Example: Squashfs at offset 1572892

# Extract using dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Or with count (if size known)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Carving Specific Types

```bash
# Extract only specific file types
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Multiple types
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Format: type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

## 📊 Entropy Analysis

### What is Entropy?

Entropy measures randomness in data:
- **High entropy (0.9-1.0)** = Encrypted/compressed data
- **Medium entropy (0.5-0.8)** = Executable code
- **Low entropy (0.0-0.4)** = Plain text, repeating data

### Generate Entropy Graph

```bash
# Calculate entropy
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Save entropy plot
binwalk -E --save firmware.bin
# Creates: firmware.bin.png

# With legend
binwalk -EJ firmware.bin
```

### Entropy Options

```bash
# Set block size
binwalk -E --block=1024 firmware.bin

# Show entropy values (no graph)
binwalk -E --nplot firmware.bin

# Detect rising/falling edges
binwalk -E --edge firmware.bin
```

### Interpreting Entropy

```
High flat line (~1.0)  → Encrypted data
Spiky pattern (~0.7)   → Compressed data  
Wavy line (~0.5)       → Code/data
Low flat line (~0.2)   → Plain text
Sudden drop to 0       → Null padding
```

---

## 💾 File System Analysis

### Common Firmware File Systems

| File System | Description |
|-------------|-------------|
| **SquashFS** | Read-only compressed (most common) |
| **JFFS2** | Journaling flash file system |
| **UBIFS** | Better JFFS2 for large flash |
| **CramFS** | Compressed ROM file system |
| **YAFFS** | Yet Another Flash File System |
| **ext2/3/4** | Standard Linux filesystem |

### Extract SquashFS

```bash
# Binwalk extract
binwalk -e firmware.bin

# Manual with unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Mount (if supported)
sudo mount -t squashfs squashfs.bin /mnt
```

### Extract JFFS2

```bash
# Using jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Or with binwalk
binwalk -e firmware.bin
```

### Extract UBIFS

```bash
# Using ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analyze Extracted Files

```bash
# After extraction, explore
cd _firmware.bin.extracted/

# Find interesting files
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Search for passwords/secrets
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Find executables
find . -type f -executable
file */bin/*
```

---

## 🎯 CTF Challenges

### Common CTF Scenarios

#### Hidden File in Image

```bash
# Scan image for hidden data
binwalk image.png
binwalk image.jpg

# Extract hidden files
binwalk -e image.png

# Check strings too
strings image.png | head -50
```

#### Stacked Archives

```bash
# Recursive extraction (matryoshka)
binwalk -eM challenge.bin

# Multiple layers of compression
binwalk -eM --depth=10 nested.bin
```

#### Find Hidden Text

```bash
# Combine with strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Encrypted Sections

```bash
# Entropy analysis reveals encryption
binwalk -E challenge.bin

# If high entropy found, likely encrypted
# Try common passwords or find key elsewhere
```

### CTF Workflow

```bash
# 1. Basic scan
binwalk challenge.bin

# 2. Check entropy
binwalk -E challenge.bin

# 3. Extract everything
binwalk -eM challenge.bin

# 4. Search extracted files
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Check for hidden data
binwalk -A challenge.bin    # Opcodes
xxd challenge.bin | head    # Hex dump
```

### Steganography Detection

```bash
# Scan for hidden data in images
binwalk -B suspicious.jpg

# Check for appended data
binwalk suspicious.png

# Extract if found
binwalk -e suspicious.jpg

# Compare file size vs expected
ls -la suspicious.jpg
```

---

## ⚙️ Advanced Options

### Hexdump Output

```bash
# Show hexdump at offsets
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Hexdump specific offset
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Offset Options

```bash
# Start at offset
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Scan only length
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Performance Options

```bash
# Use multiple processes
binwalk --threads=4 firmware.bin

# Disable certain plugins
binwalk --disable-plugin=lzma firmware.bin
```

### Output Formats

```bash
# CSV output
binwalk --csv firmware.bin > results.csv

# JSON output  
binwalk --json firmware.bin > results.json

# Log to file
binwalk -f scan.log firmware.bin
```

### Diff Two Files

```bash
# Compare two firmware versions
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Shows differences in hex
```

---

## 📊 Quick Reference

### Most Used Commands

| Purpose | Command |
|---------|---------|
| Scan file | `binwalk firmware.bin` |
| Extract all | `binwalk -e firmware.bin` |
| Recursive extract | `binwalk -eM firmware.bin` |
| Entropy analysis | `binwalk -E firmware.bin` |
| Opcode scan | `binwalk -A firmware.bin` |
| Search string | `binwalk -R "pattern" file` |
| Extract to dir | `binwalk -e -C outdir/ file` |
| Extract & delete | `binwalk -er firmware.bin` |

### Common File Signatures

| Signature | Description |
|-----------|-------------|
| `\x1f\x8b\x08` | gzip compressed |
| `BZh` | bzip2 compressed |
| `\xfd7zXZ` | xz compressed |
| `hsqs` | SquashFS (little endian) |
| `sqsh` | SquashFS (big endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | PNG image |
| `\xff\xd8\xff` | JPEG image |
| `PK\x03\x04` | ZIP archive |

### Extraction Workflow

```
1. binwalk firmware.bin       → Identify contents
2. binwalk -E firmware.bin    → Check for encryption
3. binwalk -e firmware.bin    → Extract
4. cd _firmware.bin.extracted → Explore
5. find . -name "*" -type f   → List all files
6. grep -r "password" .       → Find secrets
```

### Manual Extraction

```bash
# Get offset from binwalk
binwalk firmware.bin
# Squashfs at 0x180000 (1572864 decimal)

# Extract with dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Extract squashfs
unsquashfs fs.squashfs
```

### Useful File Locations (Firmware)

| Path | Contents |
|------|----------|
| `/etc/passwd` | User accounts |
| `/etc/shadow` | Password hashes |
| `/etc/config/` | Device configuration |
| `/etc/init.d/` | Startup scripts |
| `/usr/bin/` | User binaries |
| `/www/` | Web interface |
| `/etc/ssl/` | SSL certificates |

---

## 📚 Resources

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalk Wiki](https://github.com/ReFirmLabs/binwalk/wiki)
- [Firmware Analysis 101](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [OWASP IoT Firmware Analysis](https://owasp.org/www-project-iot-security/)

### Related Cheatsheets
- [ExifTool](../ExifTool/README.md)
- [Volatility](../Volatility/README.md)
- [Autopsy](../Autopsy/README.md)
- [Linux Commands](../Linux-Commands/README.md)

---

<p align="center">
  <b>🔧 Master Firmware Analysis!</b><br>
  <i>Binwalk - Unpack the secrets of any firmware</i>
</p>
