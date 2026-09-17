# 🔍 Autopsy - Digital Forensics Platform Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Digital Forensics Platform
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 Table of Contents

- [What is Autopsy](#-what-is-autopsy)
- [Installation](#-installation)
- [Creating a Case](#-creating-a-case)
- [Adding Data Sources](#-adding-data-sources)
- [Ingest Modules](#-ingest-modules)
- [Analysis Features](#-analysis-features)
- [File Analysis](#-file-analysis)
- [Timeline Analysis](#-timeline-analysis)
- [Keyword Search](#-keyword-search)
- [Reporting](#-reporting)
- [CLI Tools (Sleuth Kit)](#-cli-tools-sleuth-kit)

---

## 🎯 What is Autopsy

**Autopsy** is a free, open-source digital forensics platform built on The Sleuth Kit (TSK). It provides:

- 🖥️ **GUI Interface** - User-friendly forensic analysis
- 💾 **Disk Analysis** - Examine hard drives, USB, images
- 📁 **File Recovery** - Recover deleted files
- 📊 **Timeline Analysis** - Reconstruct user activity
- 🔍 **Keyword Search** - Search across all data
- 📧 **Email Analysis** - Parse email databases
- 🌐 **Web Artifacts** - Browser history, bookmarks
- 📱 **Mobile Forensics** - Android/iOS analysis

### Supported Evidence Types

| Type | Formats |
|------|---------|
| **Disk Images** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk** | Physical/Logical drives |
| **VM Files** | VMDK, VHD, VHDX |
| **Logical Files** | Folders, files |
| **Unallocated Space** | Carved data |

---

## 🚀 Installation

### Windows

```bash
# Download from official site
https://www.autopsy.com/download/

# Run installer
autopsy-x.x.x-64bit.msi

# Default location
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

```bash
# Install via apt
sudo apt update
sudo apt install autopsy

# Run
autopsy
```

### Requirements

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Multi-core | 4+ cores |
| **Storage** | SSD | NVMe SSD |
| **OS** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Creating a Case

### New Case Wizard

1. **File → New Case**
2. Enter **Case Name** (e.g., "Investigation_2024")
3. Enter **Base Directory** (where case files stored)
4. Select **Case Type**:
   - Single-user (local)
   - Multi-user (collaborative)
5. Enter **Case Number** (optional)
6. Enter **Examiner** information
7. Click **Finish**

### Case Structure

```
CaseName/
├── autopsy.db           # Case database (SQLite)
├── Reports/             # Generated reports
├── Export/              # Exported files
├── ModuleOutput/        # Module results
└── [HostName]/          # Per-host data
    ├── [DataSource]/    # Evidence files
    └── Reports/
```

### Case Properties

| Field | Description |
|-------|-------------|
| Case Name | Investigation identifier |
| Case Number | Reference number |
| Examiner | Analyst name |
| Base Directory | Case storage location |
| Case Type | Single/Multi-user |

---

## 📀 Adding Data Sources

### Data Source Types

| Type | Description |
|------|-------------|
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | Physical drive analysis |
| **Logical Files** | Folder/file import |
| **Unallocated Space** | Raw image of free space |

### Adding Disk Image

1. **Add Data Source** (toolbar)
2. Select **Disk Image or VM File**
3. Browse to image file
4. Select **Time Zone**
5. Configure **Ingest Modules** (next section)
6. Click **Finish**

### Adding Local Disk

```
⚠️ WARNING: Use write-blocker or read-only mode!
```

1. **Add Data Source**
2. Select **Local Disk**
3. Choose disk from list
4. Configure ingest modules
5. **Finish**

### Supported Image Formats

| Format | Extension | Description |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | Bit-for-bit copy |
| EnCase | .E01, .Ex01 | Expert Witness format |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | VMware disk |
| VHD/VHDX | .vhd, .vhdx | Hyper-V disk |

---

## ⚙️ Ingest Modules

### What are Ingest Modules?

Automated analysis modules that run on data sources to extract artifacts.

### Core Modules

| Module | Description |
|--------|-------------|
| **Recent Activity** | Browser history, downloads, cookies |
| **Hash Lookup** | MD5/SHA1 lookup against known databases |
| **File Type Identification** | Identify file types by signature |
| **Keyword Search** | Index and search content |
| **Email Parser** | Parse PST, MBOX, EML |
| **Extension Mismatch** | Detect renamed files |
| **Embedded File Extractor** | Extract from archives/docs |
| **EXIF Parser** | Extract image metadata |
| **Encryption Detection** | Find encrypted files |
| **Interesting Files** | Find notable files |
| **PhotoRec Carver** | File carving |
| **Virtual Machine Extractor** | Extract VM files |

### Hash Databases

```
# Add known hash databases
Tools → Options → Hash Database

# Types:
- Known (NSRL) - Ignore known good files
- Known Bad - Alert on malware hashes
```

### NSRL (National Software Reference Library)

```bash
# Download NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Import in Autopsy
Tools → Options → Hash Database → Import Database
```

### Module Configuration

```
# Enable/Disable modules per case
Right-click data source → Run Ingest Modules

# Configure module settings
Click gear icon next to module name
```

---

## 🔬 Analysis Features

### Tree View (Left Panel)

```
Data Sources
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Custom Tags]
```

### Quick Analysis Areas

| Category | What to Look For |
|----------|-----------------|
| **Deleted Files** | Recovered files, user deletions |
| **Web History** | Visited sites, searches |
| **Downloads** | Downloaded files |
| **Recent Documents** | Recently opened files |
| **USB Devices** | Connected devices |
| **Email** | Communications |
| **Images** | Photos, screenshots |
| **Archives** | ZIP, RAR contents |

---

## 📄 File Analysis

### Viewing Files

```
# Content Viewer (Bottom Panel)
- Hex        : Raw hex view
- Text       : Text content
- Application: Rendered view
- Strings    : Extracted strings
- Metadata   : File metadata
- Results    : Analysis results
- Indexed Text: Searchable text
```

### File Properties

| Property | Description |
|----------|-------------|
| Name | File name |
| Location | Full path |
| Size | File size |
| Created | Creation time |
| Modified | Last modified |
| Accessed | Last accessed |
| MD5/SHA1/SHA256 | Hash values |
| Known | NSRL status |
| MIME Type | Detected type |

### Extracting Files

```
# Extract single file
Right-click → Extract File(s)

# Extract multiple
Select files → Right-click → Extract

# Extract to specific location
Actions → Extract to...
```

### Deleted File Recovery

```
Views → Deleted Files

# Shows:
- Files in $Recycle.Bin
- Files marked deleted in MFT
- Carved files (PhotoRec)
```

---

## 📊 Timeline Analysis

### Timeline View

```
# Access Timeline
Tools → Timeline

# Or
Results → Timeline
```

### Timeline Features

| Feature | Description |
|---------|-------------|
| **Counts View** | Event frequency over time |
| **Details View** | Individual events |
| **List View** | Tabular event list |
| **Filters** | Filter by type, date, etc |

### Event Types

| Type | Color | Description |
|------|-------|-------------|
| File Modified | Blue | File content changed |
| File Accessed | Green | File opened |
| File Created | Yellow | File created |
| File Changed | Red | Metadata changed |
| Web Activity | Purple | Browser events |

### Timeline Filters

```
# Filter by date range
Start Date: 2024-01-01
End Date: 2024-12-31

# Filter by type
☑ File System
☑ Web Activity
☐ Registry (if parsed)

# Filter by data source
☑ Disk Image 1
☐ Disk Image 2
```

### Timeline Export

```
# Export timeline
File → Export → Timeline (CSV/TSV)

# Use with external tools
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Keyword Search

### Creating Keyword Lists

```
# Tools → Options → Keyword Search

# Add new list
1. Click "New List"
2. Name the list
3. Add keywords:
   - Literal (exact match)
   - Regex (pattern)
   - Substring
```

### Common Search Keywords

```
# Financial
credit card
bank account
swift
iban
bitcoin
wallet

# Credentials
password
login
username
secret
key

# Sensitive
confidential
secret
classified
internal only

# Network
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Regex Examples

| Purpose | Regex |
|---------|-------|
| Email | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP Address | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Phone (US) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Credit Card | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Running Search

```
# Ad-hoc search
Keyword Search (top-right) → Enter term → Search

# Using keyword lists
Right-click data source → Run Ingest Modules → Keyword Search
```

---

## 📝 Reporting

### Generate Report

```
# Generate Report
Tools → Generate Report

# Or
Right-click case → Generate Report
```

### Report Types

| Type | Description |
|------|-------------|
| **HTML Report** | Web-based, includes images |
| **Excel Report** | Spreadsheet format |
| **Text Report** | Plain text |
| **TSK Body File** | Timeline body file |
| **Files - FS** | File system report |
| **Portable Case** | Shareable subset |

### HTML Report Options

```
# Select data to include
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Options
☑ Include thumbnails
☑ Include metadata
```

### Portable Case

```
# Create portable case for sharing
Generate Report → Portable Case

# Includes:
- Selected evidence
- Analysis results
- Can be opened in Autopsy
```

---

## 💻 CLI Tools (Sleuth Kit)

### Sleuth Kit Commands

Autopsy uses The Sleuth Kit (TSK) backend. CLI commands available:

### Image Information

```bash
# Get image info
img_stat image.E01
img_stat image.dd

# Get filesystem info
fsstat -o 2048 image.dd
```

### File Listing

```bash
# List files
fls -r -o 2048 image.dd

# List deleted files
fls -r -d -o 2048 image.dd

# Options:
# -r    Recursive
# -d    Deleted files only
# -o    Offset (partition start)
```

### File Recovery

```bash
# Get file by inode
icat -o 2048 image.dd 12345 > recovered_file

# Get file content
icat -r -o 2048 image.dd 12345 > file.txt
```

### Timeline

```bash
# Create body file
fls -r -m "/" -o 2048 image.dd > body.txt

# Create timeline
mactime -b body.txt -d > timeline.csv
```

### Hash Calculation

```bash
# Calculate MD5
md5sum image.dd

# Calculate SHA256
sha256sum image.dd

# Verify image
sha256sum -c image.dd.sha256
```

### Common TSK Tools

| Tool | Purpose |
|------|---------|
| `img_stat` | Image information |
| `mmls` | Partition table |
| `fsstat` | File system info |
| `fls` | File listing |
| `icat` | Extract file by inode |
| `istat` | Inode information |
| `blkls` | Extract unallocated |
| `srch_strings` | Find strings |
| `tsk_recover` | Recover all files |
| `mactime` | Create timeline |

---

## 📊 Quick Reference

### Investigation Workflow

```
1. Create Case          → New case with details
2. Add Data Source      → Disk image or drive
3. Run Ingest Modules   → Automated analysis
4. Review Results       → Check extracted content
5. Keyword Search       → Search for terms
6. Timeline Analysis    → Reconstruct events
7. Tag Evidence         → Mark important items
8. Generate Report      → Create final report
```

### Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| Ctrl+N | New Case |
| Ctrl+O | Open Case |
| Ctrl+F | Find (in table) |
| Ctrl+G | Generate Report |
| F5 | Refresh |

### Common Locations to Check

| Windows | Path |
|---------|------|
| Recent Files | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Downloads | `Users\*\Downloads` |
| Browser History | `Users\*\AppData\Local\[Browser]\User Data` |
| Temp Files | `Users\*\AppData\Local\Temp` |
| Recycle Bin | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Event Logs | `Windows\System32\winevt\Logs` |

---

## 📚 Resources

- [Autopsy Official](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Autopsy Documentation](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

### Related Cheatsheets
- [Volatility](../Volatility/README.md)
- [Linux Commands](../Linux-Commands/README.md)
- [Wireshark](../Wireshark/README.md)

---

<p align="center">
  <b>🔍 Master Digital Forensics!</b><br>
  <i>Autopsy - The digital investigator's best friend</i>
</p>
