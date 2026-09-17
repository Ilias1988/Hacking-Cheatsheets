# 📷 ExifTool - Metadata Extraction Cheatsheet

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Metadata Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## 📋 Table of Contents

- [What is ExifTool](#-what-is-exiftool)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Reading Metadata](#-reading-metadata)
- [Writing Metadata](#-writing-metadata)
- [Removing Metadata](#-removing-metadata)
- [File Types](#-file-types)
- [OSINT & Forensics](#-osint--forensics)
- [Batch Processing](#-batch-processing)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is ExifTool

**ExifTool** is a powerful command-line tool for reading, writing, and manipulating metadata in files. Essential for:

- 🔍 **OSINT** - Extract location, device info from images
- 🔬 **Forensics** - Analyze document metadata
- 🔐 **Privacy** - Remove sensitive metadata
- 📸 **Photography** - Manage photo metadata
- 📄 **Documents** - Analyze PDF, Office file metadata

### Why Metadata Matters

| Use Case | Example |
|----------|---------|
| **Geolocation** | GPS coordinates in photos |
| **Device Info** | Camera model, serial number |
| **Timestamps** | When file was created/modified |
| **Author** | Who created the document |
| **Software** | Which app created the file |
| **Edit History** | Modification timestamps |

---

## 🚀 Installation

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Or
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Download from official site
https://exiftool.org/

# Extract exiftool(-k).exe
# Rename to exiftool.exe
# Add to PATH or use directly
```

### Verify Installation

```bash
exiftool -ver
# Output: 12.xx
```

---

## 💻 Basic Usage

### Basic Syntax

```bash
# Read metadata
exiftool [OPTIONS] FILE

# Write metadata
exiftool -TAG=VALUE FILE

# Multiple files
exiftool *.jpg
exiftool -r directory/
```

### Common Options

| Option | Description |
|--------|-------------|
| `-r` | Recursive (process subdirectories) |
| `-v` | Verbose output |
| `-n` | No print conversion (raw values) |
| `-s` | Short tag names |
| `-G` | Show group name for each tag |
| `-j` | JSON output |
| `-csv` | CSV output |
| `-overwrite_original` | Don't create backup |

---

## 📖 Reading Metadata

### View All Metadata

```bash
# All metadata
exiftool image.jpg

# Verbose (detailed)
exiftool -v image.jpg

# Very verbose
exiftool -v3 image.jpg
```

### Specific Tags

```bash
# Single tag
exiftool -GPSLatitude image.jpg

# Multiple tags
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Exclude tags
exiftool --MakerNotes image.jpg
```

### Group-Based Reading

```bash
# Show all EXIF tags
exiftool -EXIF:all image.jpg

# Show all IPTC tags
exiftool -IPTC:all image.jpg

# Show all XMP tags
exiftool -XMP:all image.jpg

# Show all GPS tags
exiftool -GPS:all image.jpg

# Show with group names
exiftool -G image.jpg
```

### Common Metadata Groups

| Group | Description |
|-------|-------------|
| EXIF | Camera/image data |
| IPTC | News/press metadata |
| XMP | Adobe extensible metadata |
| GPS | Location data |
| MakerNotes | Camera-specific data |
| File | Basic file info |
| Composite | Computed values |

### Output Formats

```bash
# JSON output
exiftool -j image.jpg

# CSV output
exiftool -csv image.jpg

# HTML output
exiftool -htmlDump image.jpg > output.html

# Tab-separated
exiftool -t image.jpg

# Custom format
exiftool -p '$filename: $make $model' image.jpg
```

---

## ✏️ Writing Metadata

### Write Single Tag

```bash
# Set author
exiftool -Author="John Doe" image.jpg

# Set copyright
exiftool -Copyright="© 2024 Company" image.jpg

# Set comment
exiftool -Comment="My photo" image.jpg
```

### Write Multiple Tags

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Write GPS Coordinates

```bash
# Set GPS coordinates
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# With references
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# From Google Maps URL format
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Write DateTime

```bash
# Set date/time
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Set all dates
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Shift dates
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Add 1 year
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Subtract 7 days
```

### Copy Metadata Between Files

```bash
# Copy all metadata
exiftool -TagsFromFile source.jpg destination.jpg

# Copy specific tags
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Copy to multiple files
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

## 🗑️ Removing Metadata

### Remove All Metadata

```bash
# Remove all metadata (keeps file usable)
exiftool -all= image.jpg

# Remove without backup
exiftool -overwrite_original -all= image.jpg

# Process multiple files
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Remove Specific Metadata

```bash
# Remove GPS data only
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Remove EXIF only
exiftool -EXIF:all= image.jpg

# Remove comments
exiftool -Comment= image.jpg

# Remove author info
exiftool -Author= -Creator= -Artist= image.jpg
```

### Remove from Specific File Types

```bash
# Images
exiftool -all= *.jpg *.png *.gif

# PDFs
exiftool -all= document.pdf

# Office files
exiftool -all= *.docx *.xlsx *.pptx

# Videos
exiftool -all= video.mp4
```

### Sanitize for Privacy

```bash
# Remove all identifying info
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

## 📁 File Types

### Supported File Types (400+)

| Category | Formats |
|----------|---------|
| **Images** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **RAW Photos** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Video** | MP4, MOV, AVI, MKV, WebM |
| **Audio** | MP3, WAV, FLAC, M4A, OGG |
| **Documents** | PDF, DOCX, XLSX, PPTX |
| **Archives** | ZIP (limited) |

### File Type Specific

```bash
# PDF metadata
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Video metadata
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Audio metadata
exiftool -Artist -Album -Title -Duration audio.mp3

# Office documents
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

## 🔍 OSINT & Forensics

### Extract GPS Location

```bash
# Show GPS coordinates
exiftool -GPS:all image.jpg

# Human-readable GPS
exiftool -GPSPosition image.jpg

# Decimal degrees
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Google Maps URL
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Device Identification

```bash
# Camera info
exiftool -Make -Model -SerialNumber image.jpg

# Software used
exiftool -Software -CreatorTool image.jpg

# Lens info (if available)
exiftool -Lens -LensModel -FocalLength image.jpg

# Detailed camera info
exiftool -MakerNotes:all image.jpg
```

### Timeline Analysis

```bash
# All dates/times
exiftool -time:all image.jpg

# File system times
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# EXIF times
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Find date discrepancies
exiftool -a -G1 -s -time:all image.jpg
```

### Document Forensics

```bash
# PDF analysis
exiftool -a -G1 document.pdf

# Office file history
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Find author info
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Image Authenticity

```bash
# Check for editing software
exiftool -Software -HistorySoftwareAgent image.jpg

# Check thumbnail vs image
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Check for modifications
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Useful OSINT Queries

```bash
# Extract all identifying info
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Check for steganography tools
exiftool -Software image.jpg | grep -i "steg\|hide"

# Extract embedded thumbnail
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Find social media artifacts
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

## ⚙️ Batch Processing

### Process Multiple Files

```bash
# All JPGs in current directory
exiftool *.jpg

# Recursive
exiftool -r directory/

# Specific extensions
exiftool -ext jpg -ext png directory/
```

### Batch Remove Metadata

```bash
# Remove from all images
exiftool -all= -overwrite_original -r directory/

# Only specific types
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Batch Rename Files

```bash
# Rename by date
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Rename by camera model
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Rename with counter
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Batch Copy Metadata

```bash
# Copy GPS from one file to all
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Export to CSV

```bash
# Export metadata to CSV
exiftool -csv *.jpg > metadata.csv

# Specific fields
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Conditional Processing

```bash
# Only files with GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Only files from specific camera
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Files modified after date
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

## 📊 Quick Reference

### Most Used Commands

| Purpose | Command |
|---------|---------|
| View all metadata | `exiftool image.jpg` |
| View GPS | `exiftool -GPS:all image.jpg` |
| Remove all metadata | `exiftool -all= image.jpg` |
| Remove GPS | `exiftool -GPS:all= image.jpg` |
| JSON output | `exiftool -j image.jpg` |
| Recursive | `exiftool -r directory/` |
| Camera info | `exiftool -Make -Model image.jpg` |
| Timestamps | `exiftool -time:all image.jpg` |

### Common Tags

| Tag | Description |
|-----|-------------|
| `-Make` | Camera manufacturer |
| `-Model` | Camera model |
| `-DateTimeOriginal` | When photo was taken |
| `-GPSLatitude` | GPS latitude |
| `-GPSLongitude` | GPS longitude |
| `-Author` | File author |
| `-Copyright` | Copyright info |
| `-Software` | Editing software |
| `-ImageWidth` | Image width |
| `-ImageHeight` | Image height |

### Privacy Checklist

```bash
# 1. Check what metadata exists
exiftool -a -G1 image.jpg

# 2. Check for GPS
exiftool -GPS:all image.jpg

# 3. Check for identifying info
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Remove everything
exiftool -all= -overwrite_original image.jpg

# 5. Verify removal
exiftool image.jpg
```

### Forensic Checklist

```bash
# 1. Preserve original (make copy first!)
cp image.jpg image_original.jpg

# 2. Full metadata dump
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Extract GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Timeline
exiftool -time:all -G1 image.jpg

# 5. Device info
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Check for tampering
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 Resources

- [ExifTool Official](https://exiftool.org/)
- [ExifTool Documentation](https://exiftool.org/TagNames/)
- [ExifTool Forum](https://exiftool.org/forum/)
- [Supported File Types](https://exiftool.org/#supported)

### Related Cheatsheets
- [Autopsy](../Autopsy/README.md)
- [Volatility](../Volatility/README.md)
- [Linux Commands](../Linux-Commands/README.md)

---

<p align="center">
  <b>📷 Master Metadata Analysis!</b><br>
  <i>ExifTool - Every file tells a story</i>
</p>
