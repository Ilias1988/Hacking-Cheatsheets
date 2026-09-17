# 📡 tcpdump - Complete Cheatsheet

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Command-Line Packet Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 The classic command-line packet analyzer</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Basic Syntax](#-basic-syntax)
- [Common Options](#-common-options)
- [BPF Filters](#-bpf-filters)
- [Protocol Filters](#-protocol-filters)
- [Output Options](#-output-options)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**tcpdump** is a powerful command-line packet analyzer available on most Unix-like operating systems. It uses the libpcap library and Berkeley Packet Filter (BPF) syntax.

### Key Features

| Feature | Description |
|---------|-------------|
| **Lightweight** | No GUI, minimal resources |
| **BPF Filters** | Powerful packet filtering |
| **pcap Compatible** | Read/write pcap files |
| **Scriptable** | Perfect for automation |
| **Universal** | Available on all Unix systems |

### tcpdump vs Wireshark

| Feature | tcpdump | Wireshark |
|---------|---------|-----------|
| **Interface** | CLI | GUI |
| **Resources** | Minimal | Higher |
| **Remote** | Native SSH | Requires setup |
| **Scripting** | Easy | Complex |
| **Analysis** | Basic | Advanced |

---

## 📥 Installation

### Linux (Pre-installed on most)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (Pre-installed)
```bash
tcpdump --version
```

### Verify Installation
```bash
tcpdump --version
tcpdump -D              # List interfaces
```

---

## ⌨️ Basic Syntax

### General Syntax
```bash
tcpdump [options] [filter expression]
```

### Basic Commands
```bash
# Capture on default interface
sudo tcpdump

# Capture on specific interface
sudo tcpdump -i eth0

# Capture limited packets
sudo tcpdump -c 100

# Capture with verbose output
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

## ⚙️ Common Options

### Display Options

| Option | Description |
|--------|-------------|
| `-i` | Interface to capture on |
| `-c` | Number of packets to capture |
| `-n` | Don't resolve hostnames |
| `-nn` | Don't resolve hostnames or ports |
| `-v` | Verbose output |
| `-vv` | More verbose |
| `-vvv` | Maximum verbosity |
| `-q` | Quiet/less output |
| `-t` | Don't print timestamp |
| `-tt` | Print Unix timestamp |
| `-ttt` | Print delta (time difference) |
| `-tttt` | Print date and time |

### Capture Options

| Option | Description |
|--------|-------------|
| `-w` | Write to pcap file |
| `-r` | Read from pcap file |
| `-s` | Snapshot length (bytes) |
| `-s0` | Capture full packets |
| `-D` | List available interfaces |

### Output Options

| Option | Description |
|--------|-------------|
| `-A` | Print ASCII |
| `-X` | Print hex and ASCII |
| `-XX` | Print hex and ASCII with headers |
| `-e` | Print link-level header |
| `-l` | Line buffered output |

### Common Combinations
```bash
# No DNS resolution, verbose, full packets
sudo tcpdump -nnvvs0 -i eth0

# Save to file
sudo tcpdump -i eth0 -w capture.pcap

# Read from file
tcpdump -r capture.pcap

# ASCII output
sudo tcpdump -A -i eth0

# Hex + ASCII
sudo tcpdump -X -i eth0
```

---

## 🎣 BPF Filters

### Host Filters

```bash
# Traffic to/from specific host
tcpdump host 192.168.1.1

# Source host only
tcpdump src host 192.168.1.1

# Destination host only
tcpdump dst host 192.168.1.1

# Multiple hosts
tcpdump host 192.168.1.1 or host 192.168.1.2

# Exclude host
tcpdump not host 192.168.1.1

# Network/subnet
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Port Filters

```bash
# Specific port
tcpdump port 80
tcpdump port 443

# Source port
tcpdump src port 443

# Destination port
tcpdump dst port 22

# Port range
tcpdump portrange 1-1024

# Multiple ports
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Protocol Filters

```bash
# By protocol
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protocol and port
tcpdump tcp port 80
tcpdump udp port 53
```

### Logical Operators

| Operator | Alternative | Description |
|----------|-------------|-------------|
| `and` | `&&` | Logical AND |
| `or` | `\|\|` | Logical OR |
| `not` | `!` | Logical NOT |
| `()` | | Grouping |

### Combined Filters

```bash
# Host AND port
tcpdump host 192.168.1.1 and port 80

# Host AND protocol
tcpdump host 192.168.1.1 and tcp

# Complex filter
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Exclude specific traffic
tcpdump not port 22 and not port 53

# Traffic between two hosts
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Protocol Filters

### TCP Flags

```bash
# SYN packets
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# SYN-ACK packets
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# FIN packets
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# RST packets
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# PSH packets
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# ACK only
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### TCP Flag Reference

| Flag | Hex | Dec | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### HTTP Traffic

```bash
# HTTP requests (port 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# GET requests
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# POST requests
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Simple HTTP capture
tcpdump -A -s0 'tcp port 80'
```

### DNS Traffic

```bash
# All DNS
tcpdump port 53
tcpdump udp port 53

# DNS queries only
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# DNS responses only
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### ICMP Traffic

```bash
# All ICMP
tcpdump icmp

# ICMP echo request (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# ICMP echo reply
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP unreachable
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### ARP Traffic

```bash
# All ARP
tcpdump arp

# ARP requests
tcpdump 'arp[6:2] == 1'

# ARP replies
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Output Options

### Write to File

```bash
# Write to pcap file
tcpdump -w capture.pcap

# Write with rotation (100 files, 1MB each)
tcpdump -w capture.pcap -W 100 -C 1

# Write with timestamp in filename
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Capture and display
tcpdump -w capture.pcap | tee
```

### Read from File

```bash
# Read pcap file
tcpdump -r capture.pcap

# Read with filters
tcpdump -r capture.pcap 'host 192.168.1.1'

# Count packets
tcpdump -r capture.pcap | wc -l

# Read with verbose
tcpdump -nnvvXSs0 -r capture.pcap
```

### Display Formats

```bash
# ASCII content
tcpdump -A -i eth0

# Hex and ASCII
tcpdump -X -i eth0

# Just hex
tcpdump -x -i eth0

# With ethernet headers
tcpdump -XX -i eth0

# Print absolute sequence numbers
tcpdump -S -i eth0
```

---

## 🎬 Real-World Examples

### Example 1: Capture Web Traffic
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Example 2: Capture DNS Queries
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Example 3: Capture Traffic to Specific Host
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Example 4: Find SSH Connections
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Example 5: Capture ICMP (Ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Example 6: Detect Port Scan (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Example 7: Capture Only Incoming Traffic
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Example 8: Monitor Specific Conversation
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Example 9: Capture FTP Credentials
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Example 10: Save for Wireshark Analysis
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Then open capture.pcap in Wireshark
```

### Example 11: Remote Capture via SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Example 12: Capture Excluding SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Quick Reference

### Essential Commands

| Task | Command |
|------|---------|
| List interfaces | `tcpdump -D` |
| Basic capture | `tcpdump -i eth0` |
| No DNS resolution | `tcpdump -nn` |
| Capture N packets | `tcpdump -c 100` |
| Write to file | `tcpdump -w file.pcap` |
| Read from file | `tcpdump -r file.pcap` |
| Verbose output | `tcpdump -v` |
| ASCII output | `tcpdump -A` |
| Hex output | `tcpdump -X` |
| Full packet | `tcpdump -s0` |

### Common Filters

| Filter | Description |
|--------|-------------|
| `host 1.2.3.4` | Traffic to/from host |
| `port 80` | Traffic on port 80 |
| `tcp` | TCP traffic only |
| `udp` | UDP traffic only |
| `icmp` | ICMP traffic only |
| `net 192.168.1.0/24` | Traffic to/from subnet |
| `src host 1.2.3.4` | Source is host |
| `dst port 443` | Destination port 443 |

### Filter Combinations

| Example | Description |
|---------|-------------|
| `host A and port 80` | Host A, port 80 |
| `port 80 or port 443` | Port 80 or 443 |
| `not port 22` | Exclude SSH |
| `tcp and port 80` | TCP port 80 |

---

## 💡 Tips & Best Practices

### Performance Tips

```bash
# Don't resolve DNS (faster)
tcpdump -nn

# Limit packet capture size
tcpdump -s 96   # Headers only

# Use BPF filters to reduce load
tcpdump 'host 192.168.1.1'  # Filter at kernel level
```

### Security Tips

```bash
# Run as non-root (if configured)
# Or use minimal capture time

# Drop privileges after capturing
tcpdump -Z user
```

### Useful Aliases

```bash
# Add to ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

## ⚠️ Legal Disclaimer

> **WARNING:** Network packet capture should only be used for **authorized purposes**.
> 
> - ✅ Capture on networks you own
> - ✅ Security testing with permission
> - ✅ Troubleshooting authorized systems
> - ❌ Never capture on unauthorized networks
> - ❌ Capturing others' traffic may be illegal
> 
> **Unauthorized network monitoring is illegal.**

---

## 📚 Resources

### Official Resources
- [tcpdump Manual](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [tcpdump Official Site](https://www.tcpdump.org/)
- [BPF Filter Syntax](https://biot.com/capstats/bpf.html)

### Related Cheatsheets
- [Wireshark](../Wireshark/README.md)
- [Nmap](../Nmap/README.md)
- [Metasploit](../Metasploit/README.md)

---

<p align="center">
  <b>📡 Master Command-Line Packet Analysis!</b><br>
  <i>The lightweight alternative to Wireshark</i>
</p>
