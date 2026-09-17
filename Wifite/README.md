# 📶 Wifite - Automated WiFi Auditor Cheatsheet

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Automated WiFi Hacking Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 Table of Contents

- [What is Wifite](#-what-is-wifite)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Target Selection](#-target-selection)
- [Attack Options](#-attack-options)
- [WPS Attacks](#-wps-attacks)
- [PMKID Attacks](#-pmkid-attacks)
- [Advanced Options](#-advanced-options)
- [Cracking Options](#-cracking-options)
- [Examples](#-examples)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Wifite

**Wifite** (Wifite2) is an automated wireless auditing tool that:

- 🤖 **Automates attacks** - No manual command typing
- 🎯 **Multi-target** - Attack multiple networks
- 🔓 **WPA/WPA2/WEP/WPS** - All encryption types
- 💾 **Saves progress** - Resume interrupted attacks
- 🔄 **PMKID capture** - Modern clientless attacks

### Wifite vs Wifite2

| Feature | Wifite (Original) | Wifite2 |
|---------|-------------------|---------|
| Maintained | No | Yes |
| Python | Python 2 | Python 3 |
| PMKID | No | Yes |
| Code | Messy | Clean rewrite |
| Use | Don't | **Recommended** |

### Features

| Feature | Description |
|---------|-------------|
| **Automated** | Point and shoot WiFi auditing |
| **Smart targeting** | Targets by signal strength |
| **All attacks** | WPA, WEP, WPS, PMKID |
| **Cracking** | Integrates with Hashcat/aircrack |
| **Resumable** | Continue interrupted sessions |

---

## 🚀 Installation

### Kali Linux (Pre-installed)

```bash
# Already installed in Kali
wifite --help

# Update to latest
sudo apt update && sudo apt install wifite
```

### Install Wifite2 from GitHub

```bash
# Clone repository
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Run directly
sudo python3 wifite.py

# Or install
sudo python3 setup.py install
```

### Dependencies

```bash
# Required
sudo apt install aircrack-ng

# Recommended for full functionality
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Verify Installation

```bash
wifite --help
wifite --version
```

---

## 💻 Basic Usage

### Quick Start

```bash
# Simply run wifite (will auto-detect interface)
sudo wifite

# Specify interface
sudo wifite -i wlan0
```

### Basic Workflow

```
1. Run wifite
2. Wait for networks to appear
3. Press Ctrl+C to stop scanning
4. Select target(s) by number
5. Wifite attacks automatically
6. Crack captured handshakes
```

### Interactive Mode

```bash
# Start wifite
sudo wifite

# Output shows:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Enter target number(s) or Ctrl+C to select all visible
```

---

## 🎯 Target Selection

### Select by Number

```bash
# After scan, select targets by number
# Example: select targets 1 and 3
1, 3

# Select range
1-5

# Select all
all
```

### Filter by ESSID

```bash
# Target specific network name
sudo wifite --essid "TargetNetwork"

# Multiple ESSIDs
sudo wifite --essid "Network1" --essid "Network2"
```

### Filter by BSSID

```bash
# Target specific MAC address
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filter by Encryption

```bash
# Only WPA networks
sudo wifite --wpa

# Only WEP networks
sudo wifite --wep

# Only WPS-enabled
sudo wifite --wps
```

### Filter by Channel

```bash
# Specific channel
sudo wifite -c 6

# 5GHz only
sudo wifite --5ghz

# 2.4GHz only
sudo wifite --2ghz
```

### Filter by Signal

```bash
# Minimum power (dBm, closer to 0 = stronger)
sudo wifite --power -50
```

---

## ⚔️ Attack Options

### WPA/WPA2 Options

```bash
# Set deauth timeout
sudo wifite --wpa-deauth-timeout 30

# Number of deauths to send
sudo wifite --num-deauths 5

# Skip handshake capture, only PMKID
sudo wifite --pmkid
```

### Deauthentication Options

```bash
# Number of deauth packets
sudo wifite --num-deauths 10

# Continuous deauth
sudo wifite --num-deauths 0
```

### Attack Order

```bash
# Skip WPA attacks
sudo wifite --no-wpa

# Skip WEP attacks
sudo wifite --no-wep

# Skip WPS attacks
sudo wifite --no-wps

# Skip PMKID attacks
sudo wifite --no-pmkid
```

### Kill NetworkManager

```bash
# Auto-kill interfering processes
sudo wifite --kill

# Don't kill processes
sudo wifite --no-kill
```

---

## 🔓 WPS Attacks

### Enable WPS

```bash
# Only attack WPS-enabled networks
sudo wifite --wps

# WPS-only mode
sudo wifite --wps-only
```

### WPS Tools

```bash
# Use Reaver (default)
sudo wifite --wps --reaver

# Use Bully
sudo wifite --wps --bully
```

### Pixie-Dust Attack

```bash
# Enable Pixie-Dust (offline WPS attack)
sudo wifite --wps --pixie

# Pixie-Dust timeout
sudo wifite --wps --pixie-timeout 300
```

### WPS Options

```bash
# WPS timeout per PIN attempt
sudo wifite --wps-time 120

# Maximum WPS retries
sudo wifite --wps-fails 10

# WPS timeout (overall)
sudo wifite --wps-timeout 300
```

---

## 🔐 PMKID Attacks

### What is PMKID?

PMKID (Pairwise Master Key Identifier) attack:
- ✅ Captures PMK from AP directly
- ✅ No client needed
- ✅ No deauth required
- ✅ Works on most WPA2 networks

### PMKID Options

```bash
# Only PMKID attacks (no handshake)
sudo wifite --pmkid

# PMKID timeout
sudo wifite --pmkid-timeout 120
```

### How PMKID Works

```
1. Send association request to AP
2. AP responds with PMKID in EAPOL frame
3. Capture PMKID
4. Crack offline with hashcat
```

---

## ⚙️ Advanced Options

### Interface Options

```bash
# Specify interface
sudo wifite -i wlan0

# Specify monitor interface
sudo wifite -i wlan0mon
```

### Output Options

```bash
# Verbose output
sudo wifite -v
sudo wifite --verbose

# Very verbose
sudo wifite -vv

# Quiet mode
sudo wifite --quiet
```

### Timing Options

```bash
# Scan time (seconds)
sudo wifite --scan-time 30

# Overall timeout per target
sudo wifite --attack-timeout 300
```

### Cracked Passwords

```bash
# Cracked passwords are saved to:
# ~/.config/wifite/cracked.txt

# View cracked networks
cat ~/.config/wifite/cracked.txt

# Or specify custom file
sudo wifite --cracked cracked_passwords.txt
```

### Skip Options

```bash
# Skip cracking (capture only)
sudo wifite --skip-crack

# Skip targets already cracked
sudo wifite --skip-cracked
```

### Random MAC

```bash
# Randomize MAC address
sudo wifite --random-mac

# Don't change MAC
sudo wifite --no-random-mac
```

---

## 🔓 Cracking Options

### Dictionary Attack

```bash
# Specify wordlist
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Multiple wordlists
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Hashcat Integration

```bash
# Use hashcat for cracking
sudo wifite --hashcat

# Hashcat location
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Aircrack-ng Options

```bash
# Use aircrack-ng (default)
sudo wifite --aircrack

# Aircrack-ng path
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Crack Only Mode

```bash
# Crack existing handshake files
sudo wifite --crack

# Specify capture file
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Examples

### Basic WiFi Audit

```bash
# Scan and attack all networks
sudo wifite

# Attack with custom wordlist
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Target Specific Network

```bash
# Target by name
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Target by MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### WPA2 Only Attack

```bash
# Only WPA2 networks with PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### WPS Attack

```bash
# WPS with Pixie-Dust
sudo wifite --wps --pixie

# WPS bruteforce only
sudo wifite --wps-only --bully
```

### Strong Signal Only

```bash
# Only attack networks with strong signal
sudo wifite --power -50 --wpa
```

### 5GHz Networks

```bash
# Attack 5GHz band only
sudo wifite --5ghz --wpa
```

### Quick Audit (No Cracking)

```bash
# Capture handshakes only, crack later
sudo wifite --skip-crack
```

### Resume Attack

```bash
# Wifite auto-saves progress
# Just run again to resume
sudo wifite

# Skip already cracked
sudo wifite --skip-cracked
```

### Verbose Mode

```bash
# See all details
sudo wifite -vv --dict rockyou.txt
```

### Stealth Mode

```bash
# Minimal deauths, quiet
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Quick Reference

### Essential Commands

| Purpose | Command |
|---------|---------|
| Basic scan | `sudo wifite` |
| Target by name | `sudo wifite --essid "Name"` |
| Target by MAC | `sudo wifite --bssid MAC` |
| WPA only | `sudo wifite --wpa` |
| WPS only | `sudo wifite --wps-only` |
| PMKID attack | `sudo wifite --pmkid` |
| With wordlist | `sudo wifite --dict wordlist.txt` |
| Skip cracking | `sudo wifite --skip-crack` |

### Attack Types

| Attack | Flag | Target |
|--------|------|--------|
| WPA Handshake | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS enabled |
| WPS Bruteforce | `--wps-only` | WPS enabled |
| WEP | `--wep` | WEP (obsolete) |

### Common Wordlists

| Path | Description |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Most common passwords |
| `/usr/share/wordlists/fasttrack.txt` | Quick check |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | WiFi-specific |

### Captured Files

```
~/.config/wifite/
├── cracked.txt          # Cracked passwords
├── hs/                  # Handshake captures
│   └── handshake_*.cap
└── pmkid/               # PMKID captures
    └── pmkid_*.22000
```

### Flags Quick Reference

| Flag | Description |
|------|-------------|
| `-i` | Interface |
| `-c` | Channel |
| `-v` | Verbose |
| `--wpa` | WPA/WPA2 only |
| `--wep` | WEP only |
| `--wps` | WPS enabled |
| `--pmkid` | PMKID attack |
| `--dict` | Wordlist |
| `--kill` | Kill processes |
| `--power` | Minimum signal |
| `--5ghz` | 5GHz band |
| `--2ghz` | 2.4GHz band |

### Troubleshooting

```bash
# Interface not found
sudo airmon-ng check kill
sudo wifite -i wlan0

# No networks found
# - Check adapter supports monitor mode
# - Try different bands: --5ghz or --2ghz
# - Move closer to targets

# Handshake not captured
# - Increase deauths: --num-deauths 10
# - Wait longer: --wpa-deauth-timeout 60
# - Try different clients

# Cracking fails
# - Use better wordlist
# - Try hashcat with GPU: --hashcat
```

---

## 🔄 Wifite vs Manual Aircrack-ng

| Task | Wifite | Aircrack-ng Suite |
|------|--------|-------------------|
| Monitor mode | Automatic | `airmon-ng start wlan0` |
| Scan | Automatic | `airodump-ng wlan0mon` |
| Target | Interactive | Manual filtering |
| Deauth | Automatic | `aireplay-ng -0 ...` |
| Capture | Automatic | `airodump-ng -w ...` |
| Crack | Automatic | `aircrack-ng -w ...` |
| **Effort** | **Easy** | Manual |

---

## ⚠️ Legal Disclaimer

```
⚠️ WARNING: Only use Wifite on networks you OWN or have 
EXPLICIT WRITTEN PERMISSION to test.

✅ Authorized penetration testing
✅ Security auditing (with permission)
✅ Educational purposes (lab environment)

❌ Unauthorized network access is ILLEGAL
❌ Never use for malicious purposes
```

---

## 📚 Resources

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite Wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Related Cheatsheets
- [Aircrack-ng](../Aircrack-ng/README.md)
- [Hashcat](../Hashcat/README.md)
- [Linux Commands](../Linux-Commands/README.md)

---

<p align="center">
  <b>📶 Automated WiFi Auditing!</b><br>
  <i>Wifite - Point, click, hack (ethically!)</i>
</p>
