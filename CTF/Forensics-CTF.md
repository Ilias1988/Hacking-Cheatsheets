# 🔬 Forensics CTF Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

---

## 📁 File Analysis

```bash
# File type
file unknown_file

# Hex dump
xxd file | head
hexdump -C file | head

# Strings
strings -n 6 file
strings -e l file    # Little-endian

# File carving
binwalk file
binwalk -e file      # Extract
foremost file
```

---

## 🖼️ Steganography

### Images

```bash
# Metadata
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # All methods

# LSB analysis
stegsolve.jar        # GUI tool

# Strings in image
strings image.png | grep -i flag
```

### Audio
```bash
# Spectrogram (Audacity)
Open file → Analyze → Plot Spectrum

# Sonic Visualiser
# Check for hidden data in frequencies
```

---

## 🧠 Memory Forensics

### Volatility
```bash
# Identify profile
volatility -f dump.raw imageinfo

# Process list
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Command history
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Dump process
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Files
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Disk Forensics

```bash
# Mount image
mount -o loop image.dd /mnt/disk

# File system info
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Network Forensics (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Extract files
tcpflow -r capture.pcap
```

---

## 📋 Forensics Checklist

```markdown
□ Check file type (file command)
□ Strings search for flag
□ Check metadata (exiftool)
□ Binwalk for embedded files
□ Steganography tools
□ Memory analysis (Volatility)
□ PCAP analysis (Wireshark)
```

---

**Back to CTF:** [🏁 CTF Overview](./README.md)
