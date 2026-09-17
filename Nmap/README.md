# 🔍 Nmap - Complete Cheatsheet

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - The Network Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 The most powerful network scanning and discovery tool</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Target Specification](#-target-specification)
- [Host Discovery](#-host-discovery)
- [Port Scanning Techniques](#-port-scanning-techniques)
- [Port Specification](#-port-specification)
- [Service & Version Detection](#-service--version-detection)
- [OS Detection](#-os-detection)
- [Timing & Performance](#-timing--performance)
- [NSE Scripts](#-nse-scripts)
- [Output Formats](#-output-formats)
- [Firewall/IDS Evasion](#-firewallids-evasion)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Nmap** (Network Mapper) is a free and open-source utility for network discovery and security auditing. It's the industry standard for network scanning.

### Key Features

| Feature | Description |
|---------|-------------|
| **Host Discovery** | Find live hosts on a network |
| **Port Scanning** | Identify open ports and services |
| **Version Detection** | Determine service versions |
| **OS Detection** | Identify operating systems |
| **NSE Scripts** | Vulnerability scanning, exploitation |
| **Output Formats** | XML, grepable, normal output |

### Port States

| State | Description |
|-------|-------------|
| `open` | Application is accepting connections |
| `closed` | Port is accessible but no application listening |
| `filtered` | Nmap cannot determine if open (firewall) |
| `unfiltered` | Port is accessible but state unknown |
| `open|filtered` | Cannot determine if open or filtered |
| `closed|filtered` | Cannot determine if closed or filtered |

---

## 📥 Installation

### Kali Linux (Pre-installed)
```bash
# Update Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Download from https://nmap.org/download.html
# Or use Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Verify Installation
```bash
nmap --version
nmap -h          # Help
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
nmap [Scan Type] [Options] {target}
```

### Quick Start Examples
```bash
# Basic scan
nmap 192.168.1.1

# Scan multiple hosts
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Scan a range
nmap 192.168.1.1-254

# Scan entire subnet
nmap 192.168.1.0/24

# Scan from file
nmap -iL targets.txt
```

---

## 🎯 Target Specification

### Single Targets
```bash
# IP address
nmap 192.168.1.1

# Hostname
nmap scanme.nmap.org

# Domain
nmap example.com
```

### Multiple Targets
```bash
# Space-separated
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Range
nmap 192.168.1.1-100

# CIDR notation
nmap 192.168.1.0/24

# Wildcard
nmap 192.168.1.*

# Octet range
nmap 192.168.0-255.1-254

# Mixed
nmap 192.168.1.0/24 10.0.0.1
```

### Input from File
```bash
# Read from file
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Exclude Targets
```bash
# Exclude single host
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Exclude from file
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Random Targets
```bash
# Scan random hosts (for research)
nmap -iR 100
```

---

## 🔎 Host Discovery

### Discovery Options

| Option | Description |
|--------|-------------|
| `-sL` | List scan - only list targets |
| `-sn` | Ping scan - no port scan |
| `-Pn` | Skip host discovery (treat all hosts as online) |
| `-PS` | TCP SYN ping |
| `-PA` | TCP ACK ping |
| `-PU` | UDP ping |
| `-PE` | ICMP echo ping |
| `-PP` | ICMP timestamp ping |
| `-PM` | ICMP netmask ping |
| `-PO` | IP protocol ping |
| `-PR` | ARP ping (local network) |

### Ping Scan (No Port Scan)
```bash
# Discover live hosts only
nmap -sn 192.168.1.0/24

# Ping scan with host list
nmap -sn -iL hosts.txt
```

### Skip Host Discovery
```bash
# Scan all hosts (don't ping first)
nmap -Pn 192.168.1.1

# Useful when:
# - Hosts block ICMP
# - Scanning through firewall
```

### TCP SYN/ACK Ping
```bash
# TCP SYN ping on port 80
nmap -PS80 192.168.1.1

# TCP ACK ping on port 80
nmap -PA80 192.168.1.1

# Multiple ports
nmap -PS22,80,443 192.168.1.1
```

### UDP Ping
```bash
# UDP ping
nmap -PU53 192.168.1.1

# Multiple ports
nmap -PU53,161 192.168.1.1
```

### ICMP Ping
```bash
# ICMP echo
nmap -PE 192.168.1.1

# ICMP timestamp
nmap -PP 192.168.1.1

# ICMP netmask
nmap -PM 192.168.1.1
```

### ARP Ping (Local Network)
```bash
# ARP scan (fastest on local network)
nmap -PR 192.168.1.0/24

# ARP scan only
nmap -sn -PR 192.168.1.0/24
```

### Disable DNS Resolution
```bash
# No DNS resolution (faster)
nmap -n 192.168.1.1

# Always resolve DNS
nmap -R 192.168.1.1
```

---

## 🔌 Port Scanning Techniques

### Scan Types Overview

| Option | Name | Description |
|--------|------|-------------|
| `-sS` | SYN Scan | Stealth scan (default, requires root) |
| `-sT` | TCP Connect | Full TCP connection (no root needed) |
| `-sU` | UDP Scan | Scan UDP ports |
| `-sA` | ACK Scan | Map firewall rules |
| `-sW` | Window Scan | Similar to ACK, uses window field |
| `-sM` | Maimon Scan | FIN/ACK probe |
| `-sN` | NULL Scan | No flags set |
| `-sF` | FIN Scan | FIN flag set |
| `-sX` | Xmas Scan | FIN, PSH, URG flags |

### SYN Stealth Scan (Default)
```bash
# Most common scan (requires root)
sudo nmap -sS 192.168.1.1

# Never completes TCP handshake
# Fast and stealthy
```

### TCP Connect Scan
```bash
# Full TCP connection
nmap -sT 192.168.1.1

# No root required
# More detectable but always works
```

### UDP Scan
```bash
# Scan UDP ports
sudo nmap -sU 192.168.1.1

# UDP scan is SLOW
# Combine with version detection
sudo nmap -sU -sV 192.168.1.1

# Common UDP ports
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Combined TCP/UDP
```bash
# Scan both TCP and UDP
sudo nmap -sS -sU 192.168.1.1

# Specific ports
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### NULL, FIN, Xmas Scans
```bash
# NULL scan (no flags)
sudo nmap -sN 192.168.1.1

# FIN scan (FIN flag only)
sudo nmap -sF 192.168.1.1

# Xmas scan (FIN, PSH, URG)
sudo nmap -sX 192.168.1.1

# Good for:
# - Bypassing stateless firewalls
# - Doesn't work on Windows
```

### ACK Scan
```bash
# Map firewall rules
sudo nmap -sA 192.168.1.1

# Determine:
# - Which ports are filtered
# - Firewall rules
# Returns: unfiltered or filtered
```

### Window Scan
```bash
# Similar to ACK scan
sudo nmap -sW 192.168.1.1

# Uses TCP window size to determine state
```

### Idle/Zombie Scan
```bash
# Ultimate stealth - use zombie host
sudo nmap -sI zombie_host 192.168.1.1

# Your IP never touches target
# Requires suitable zombie
```

---

## 🎚️ Port Specification

### Port Options

| Option | Description |
|--------|-------------|
| `-p <port>` | Scan specific port(s) |
| `-p-` | Scan all 65535 ports |
| `-p 1-1000` | Scan port range |
| `--top-ports <n>` | Scan top N ports |
| `-F` | Fast scan (top 100 ports) |
| `-r` | Scan ports sequentially |

### Specific Ports
```bash
# Single port
nmap -p 80 192.168.1.1

# Multiple ports
nmap -p 80,443,22 192.168.1.1

# Port range
nmap -p 1-1000 192.168.1.1

# All ports
nmap -p- 192.168.1.1

# Mixed
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Port by Protocol
```bash
# TCP ports
nmap -p T:80,443 192.168.1.1

# UDP ports
nmap -p U:53,161 192.168.1.1

# Both
nmap -p T:80,443,U:53 192.168.1.1
```

### Top Ports
```bash
# Top 100 ports (fast)
nmap -F 192.168.1.1

# Top 10 ports
nmap --top-ports 10 192.168.1.1

# Top 1000 ports (default)
nmap --top-ports 1000 192.168.1.1
```

### Common Ports Reference

| Port | Service |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Service & Version Detection

### Version Detection Options

| Option | Description |
|--------|-------------|
| `-sV` | Version detection |
| `--version-intensity <0-9>` | Probe intensity |
| `--version-light` | Light version scan (intensity 2) |
| `--version-all` | Try all probes (intensity 9) |
| `--version-trace` | Debug version scan |

### Basic Version Detection
```bash
# Detect service versions
nmap -sV 192.168.1.1

# Common output:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Version Intensity
```bash
# Light scan (faster)
nmap -sV --version-light 192.168.1.1

# All probes (thorough)
nmap -sV --version-all 192.168.1.1

# Custom intensity (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 OS Detection

### OS Detection Options

| Option | Description |
|--------|-------------|
| `-O` | Enable OS detection |
| `--osscan-limit` | Only scan promising hosts |
| `--osscan-guess` | Guess OS more aggressively |
| `--max-os-tries` | Maximum OS detection tries |

### Basic OS Detection
```bash
# Detect OS (requires root)
sudo nmap -O 192.168.1.1

# Output example:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Aggressive OS Guessing
```bash
# More aggressive guessing
sudo nmap -O --osscan-guess 192.168.1.1
```

### Aggressive Scan (-A)
```bash
# Enable OS detection, version, scripts, traceroute
sudo nmap -A 192.168.1.1

# Equivalent to: -O -sV -sC --traceroute
```

---

## ⏱️ Timing & Performance

### Timing Templates

| Option | Name | Description |
|--------|------|-------------|
| `-T0` | Paranoid | IDS evasion, very slow |
| `-T1` | Sneaky | IDS evasion, slow |
| `-T2` | Polite | Less bandwidth, slower |
| `-T3` | Normal | Default timing |
| `-T4` | Aggressive | Fast, reliable network |
| `-T5` | Insane | Very fast, may miss ports |

### Usage
```bash
# Slow and stealthy
nmap -T1 192.168.1.1

# Normal (default)
nmap -T3 192.168.1.1

# Fast
nmap -T4 192.168.1.1

# Very fast (local network)
nmap -T5 192.168.1.1
```

### Fine-Grained Timing

| Option | Description |
|--------|-------------|
| `--min-rate <n>` | Minimum packets per second |
| `--max-rate <n>` | Maximum packets per second |
| `--min-parallelism <n>` | Minimum parallel probes |
| `--max-parallelism <n>` | Maximum parallel probes |
| `--min-hostgroup <n>` | Minimum hosts in parallel |
| `--max-hostgroup <n>` | Maximum hosts in parallel |
| `--host-timeout <time>` | Give up on host after time |
| `--scan-delay <time>` | Delay between probes |
| `--max-retries <n>` | Maximum probe retries |

```bash
# Send at least 1000 packets/sec
nmap --min-rate 1000 192.168.1.0/24

# Maximum 100 packets/sec (slow down)
nmap --max-rate 100 192.168.1.1

# Timeout host after 30 minutes
nmap --host-timeout 30m 192.168.1.0/24

# Delay between probes
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 NSE Scripts

### Script Categories

| Category | Description |
|----------|-------------|
| `auth` | Authentication bypass |
| `broadcast` | Discover hosts via broadcast |
| `brute` | Brute force attacks |
| `default` | Default scripts (-sC) |
| `discovery` | Service discovery |
| `dos` | Denial of service |
| `exploit` | Exploit vulnerabilities |
| `external` | Query external services |
| `fuzzer` | Fuzzing |
| `intrusive` | May crash services |
| `malware` | Malware detection |
| `safe` | Safe scripts |
| `version` | Version detection |
| `vuln` | Vulnerability detection |

### Script Options

| Option | Description |
|--------|-------------|
| `-sC` | Default scripts |
| `--script <scripts>` | Run specific scripts |
| `--script-args <args>` | Script arguments |
| `--script-updatedb` | Update script database |
| `--script-help <script>` | Script help |

### Default Scripts
```bash
# Run default scripts
nmap -sC 192.168.1.1

# With version detection (recommended)
nmap -sV -sC 192.168.1.1
```

### Run Specific Scripts
```bash
# Single script
nmap --script http-title 192.168.1.1

# Multiple scripts
nmap --script http-title,http-headers 192.168.1.1

# Category
nmap --script vuln 192.168.1.1

# Wildcard
nmap --script "http-*" 192.168.1.1

# Combine categories
nmap --script "vuln and safe" 192.168.1.1

# Exclude scripts
nmap --script "not intrusive" 192.168.1.1
```

### Popular Scripts

#### Vulnerability Scanning
```bash
# All vulnerability scripts
nmap --script vuln 192.168.1.1

# Specific CVE check
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### HTTP Scripts
```bash
# HTTP enumeration
nmap --script http-enum 192.168.1.1

# HTTP headers
nmap --script http-headers 192.168.1.1

# HTTP methods
nmap --script http-methods 192.168.1.1

# HTTP title
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### SMB Scripts
```bash
# SMB enumeration
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# SMB vulnerabilities
nmap --script smb-vuln* 192.168.1.1
```

#### SSH Scripts
```bash
# SSH authentication methods
nmap --script ssh-auth-methods 192.168.1.1

# SSH host key
nmap --script ssh-hostkey 192.168.1.1

# SSH brute force
nmap --script ssh-brute 192.168.1.1
```

#### DNS Scripts
```bash
# DNS brute force
nmap --script dns-brute example.com

# DNS zone transfer
nmap --script dns-zone-transfer example.com
```

#### MySQL/Database Scripts
```bash
# MySQL info
nmap --script mysql-info 192.168.1.1

# MySQL enum
nmap --script mysql-enum 192.168.1.1

# MySQL brute
nmap --script mysql-brute 192.168.1.1
```

### Script Arguments
```bash
# Brute force with wordlist
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# HTTP auth brute force
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Output Formats

### Output Options

| Option | Description |
|--------|-------------|
| `-oN <file>` | Normal output |
| `-oX <file>` | XML output |
| `-oG <file>` | Grepable output |
| `-oA <basename>` | All formats |
| `-oS <file>` | Script kiddie output |

### Save Output
```bash
# Normal output
nmap -oN scan.txt 192.168.1.1

# XML output (for tools)
nmap -oX scan.xml 192.168.1.1

# Grepable output
nmap -oG scan.gnmap 192.168.1.1

# All formats at once
nmap -oA scan 192.168.1.1
# Creates: scan.nmap, scan.xml, scan.gnmap
```

### Verbosity & Debugging

| Option | Description |
|--------|-------------|
| `-v` | Increase verbosity |
| `-vv` | More verbose |
| `-d` | Debug mode |
| `-dd` | More debugging |
| `--reason` | Show reason for port state |
| `--open` | Only show open ports |
| `--packet-trace` | Show all packets |

```bash
# Verbose output
nmap -v 192.168.1.1

# Very verbose
nmap -vv 192.168.1.1

# Show only open ports
nmap --open 192.168.1.1

# Show reason
nmap --reason 192.168.1.1
```

---

## 🛡️ Firewall/IDS Evasion

### Evasion Techniques

| Option | Description |
|--------|-------------|
| `-f` | Fragment packets |
| `--mtu <size>` | Custom MTU size |
| `-D <decoys>` | Use decoys |
| `-S <IP>` | Spoof source IP |
| `--source-port <port>` | Spoof source port |
| `--data-length <n>` | Append random data |
| `--randomize-hosts` | Random host order |
| `--spoof-mac <mac>` | Spoof MAC address |
| `--badsum` | Send bad checksums |

### Packet Fragmentation
```bash
# Fragment packets
nmap -f 192.168.1.1

# Custom MTU
nmap --mtu 16 192.168.1.1
```

### Decoys
```bash
# Use decoys
nmap -D decoy1,decoy2,ME 192.168.1.1

# Random decoys
nmap -D RND:5 192.168.1.1
```

### Source IP/Port Spoofing
```bash
# Spoof source IP
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Spoof source port (common allowed ports)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### MAC Spoofing
```bash
# Random MAC
nmap --spoof-mac 0 192.168.1.1

# Specific vendor
nmap --spoof-mac Apple 192.168.1.1

# Specific MAC
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Data Padding
```bash
# Add random data to packets
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Real-World Examples

### Example 1: Quick Network Sweep
```bash
# Find live hosts
nmap -sn 192.168.1.0/24
```

### Example 2: Basic Port Scan
```bash
# Scan with version detection
nmap -sV 192.168.1.1
```

### Example 3: Comprehensive Scan
```bash
# Full scan with OS and scripts
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Example 4: Aggressive Full Scan
```bash
# All ports, aggressive
sudo nmap -A -T4 -p- 192.168.1.1
```

### Example 5: Vulnerability Scan
```bash
# Check for vulnerabilities
nmap --script vuln 192.168.1.1
```

### Example 6: Stealth Scan
```bash
# Stealthy scan
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Example 7: Web Server Scan
```bash
# Comprehensive web scan
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Example 8: SMB Enumeration
```bash
# Full SMB enumeration
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Example 9: Full Subnet Scan
```bash
# Scan entire subnet
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Example 10: UDP Services
```bash
# Common UDP services
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Quick Reference

### Essential Commands

| Command | Description |
|---------|-------------|
| `nmap <target>` | Basic scan |
| `nmap -sn <target>` | Ping scan only |
| `nmap -Pn <target>` | Skip host discovery |
| `nmap -sS <target>` | SYN stealth scan |
| `nmap -sV <target>` | Version detection |
| `nmap -O <target>` | OS detection |
| `nmap -A <target>` | Aggressive scan |
| `nmap -sC <target>` | Default scripts |
| `nmap -p- <target>` | All ports |
| `nmap --script vuln <target>` | Vulnerability scan |

### Speed vs Stealth

| Need | Options |
|------|---------|
| **Fast** | `-T4 --min-rate 1000` |
| **Stealthy** | `-T1 -f --data-length 25` |
| **Balanced** | `-T3` (default) |

### Common Scans

```bash
# Fast scan
nmap -F 192.168.1.1

# Full TCP scan
nmap -sT -p- 192.168.1.1

# Service scan
nmap -sV 192.168.1.1

# Vulnerability scan
nmap --script vuln 192.168.1.1

# Everything
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Tips & Best Practices

### Performance Tips

1. **Use SYN Scan (Default)**
   ```bash
   # Requires root but fastest
   sudo nmap -sS 192.168.1.1
   ```

2. **Limit Port Range Initially**
   ```bash
   # Start with top ports
   nmap --top-ports 100 192.168.1.1
   ```

3. **Increase Speed for Local Networks**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Accuracy Tips

1. **Multiple Scan Types**
   ```bash
   # TCP and UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Increase Retries for Unreliable Networks**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Legal Considerations

> ⚠️ **IMPORTANT:** Only scan networks you own or have permission to scan.

- ✅ Your own networks
- ✅ Lab environments
- ✅ Authorized penetration tests
- ❌ Random internet hosts
- ❌ Networks without permission

---

## ⚠️ Legal Disclaimer

> **WARNING:** Nmap is a powerful tool that should only be used for **authorized scanning**.
> 
> - ✅ Scan networks you own
> - ✅ Scan with explicit written permission
> - ✅ Use in authorized penetration tests
> - ❌ Never scan networks without authorization
> - ❌ Unauthorized scanning may be illegal
> 
> **Unauthorized network scanning is illegal in many jurisdictions.**

---

## 📚 Resources

### Official Resources
- [Nmap Official Website](https://nmap.org/)
- [Nmap Reference Guide](https://nmap.org/book/man.html)
- [NSE Script Documentation](https://nmap.org/nsedoc/)

### Learning Resources
- [Nmap Network Scanning Book](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Related Cheatsheets
- [Metasploit Framework](../Metasploit/README.md)
- [SQLMap](../SQLMap/README.md)
- [Burp Suite](../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 Master Network Reconnaissance!</b><br>
  <i>Know your network before attackers do</i>
</p>
