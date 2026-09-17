# 🦈 Wireshark - Complete Cheatsheet

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           The Network Protocol Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 The world's most popular network protocol analyzer</b>
</p>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Interface Overview](#-interface-overview)
- [Capture Basics](#-capture-basics)
- [Display Filters](#-display-filters)
- [Capture Filters](#-capture-filters)
- [Protocol Analysis](#-protocol-analysis)
- [Following Streams](#-following-streams)
- [Statistics & Analysis](#-statistics--analysis)
- [tshark (CLI)](#-tshark-cli)
- [Real-World Examples](#-real-world-examples)
- [Quick Reference](#-quick-reference)
- [Keyboard Shortcuts](#-keyboard-shortcuts)
- [Tips & Best Practices](#-tips--best-practices)
- [Resources](#-resources)

---

## 🎯 Introduction

**Wireshark** is a free and open-source packet analyzer used for network troubleshooting, analysis, software development, and security auditing.

### Key Features

| Feature | Description |
|---------|-------------|
| **Deep Inspection** | Hundreds of protocols supported |
| **Live Capture** | Real-time packet capture |
| **Offline Analysis** | Read captured files (pcap, pcapng) |
| **Powerful Filters** | Display and capture filters |
| **Cross-Platform** | Windows, Linux, macOS |
| **GUI & CLI** | Wireshark (GUI) and tshark (CLI) |

### Use Cases

| Use Case | Description |
|----------|-------------|
| **Network Troubleshooting** | Debug connectivity issues |
| **Security Analysis** | Detect malicious traffic |
| **Protocol Development** | Test new protocols |
| **Forensics** | Analyze captured traffic |
| **Education** | Learn networking |

---

## 📥 Installation

### Windows
```bash
# Download from official site
https://www.wireshark.org/download.html

# Or using Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (pre-installed)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Allow non-root capture
sudo usermod -aG wireshark $USER
# Logout and login again
```

### macOS
```bash
brew install --cask wireshark
```

### Verify Installation
```bash
wireshark --version
tshark --version
```

---

## 🖥️ Interface Overview

### Main Window Areas

| Area | Description |
|------|-------------|
| **Menu Bar** | File, Edit, View, Go, Capture, Analyze |
| **Main Toolbar** | Quick access buttons |
| **Filter Bar** | Enter display filters |
| **Packet List** | All captured packets |
| **Packet Details** | Protocol layers of selected packet |
| **Packet Bytes** | Raw hex/ASCII data |

### Packet List Columns (Default)

| Column | Description |
|--------|-------------|
| No. | Packet number |
| Time | Timestamp |
| Source | Source IP/MAC |
| Destination | Destination IP/MAC |
| Protocol | Highest-layer protocol |
| Length | Packet size in bytes |
| Info | Protocol-specific information |

---

## 📡 Capture Basics

### Start Capture

```
1. Select interface from welcome screen
2. Click "Start Capture" (blue shark fin)
3. Or: Capture → Start

Quick: Double-click interface name
```

### Stop Capture

```
Click red square in toolbar
Or: Capture → Stop
Or: Ctrl+E
```

### Save Capture

```
File → Save As
Formats: pcapng (default), pcap, etc.
```

### Open Capture File

```
File → Open
Or: Ctrl+O
Or: wireshark file.pcap
```

---

## 🔍 Display Filters

### Filter Syntax

Display filters use **field comparison syntax**:

```
field operator value
```

### Comparison Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `==` or `eq` | Equal | `ip.addr == 192.168.1.1` |
| `!=` or `ne` | Not equal | `ip.addr != 10.0.0.1` |
| `>` or `gt` | Greater than | `frame.len > 100` |
| `<` or `lt` | Less than | `tcp.port < 1024` |
| `>=` or `ge` | Greater or equal | `frame.len >= 64` |
| `<=` or `le` | Less or equal | `tcp.port <= 1024` |
| `contains` | Contains string | `http contains "password"` |
| `matches` | Regex match | `http.host matches ".*\.com"` |

### Logical Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `and` or `&&` | Logical AND | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` or `\|\|` | Logical OR | `tcp.port == 80 or tcp.port == 443` |
| `not` or `!` | Logical NOT | `not arp` |
| `()` | Grouping | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### IP Filters

```bash
# Specific IP address (src or dst)
ip.addr == 192.168.1.1

# Source IP only
ip.src == 192.168.1.1

# Destination IP only
ip.dst == 192.168.1.1

# IP range (subnet)
ip.addr == 192.168.1.0/24

# Exclude IP
ip.addr != 192.168.1.1

# Between two hosts
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Port Filters

```bash
# Specific port (src or dst)
tcp.port == 80
udp.port == 53

# Source port only
tcp.srcport == 443

# Destination port only
tcp.dstport == 22

# Port range
tcp.port >= 1 and tcp.port <= 1024

# Multiple ports
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Protocol Filters

```bash
# By protocol name
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# HTTP specific
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# DNS specific
dns.qry.name == "google.com"
dns.qry.type == 1          # A record
dns.flags.response == 1    # DNS responses

# TCP specific
tcp.flags.syn == 1         # SYN packets
tcp.flags.fin == 1         # FIN packets
tcp.flags.reset == 1       # RST packets
tcp.analysis.retransmission  # Retransmissions
tcp.stream == 5            # Specific stream
```

### Content Filters

```bash
# Contains string
http contains "password"
tcp contains "login"
frame contains "secret"

# HTTP content
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Case-insensitive match
http.host matches "(?i)example"
```

### Common Filter Examples

```bash
# HTTP traffic only
http

# HTTPS traffic
tls

# DNS queries
dns.flags.response == 0

# Failed connections (RST)
tcp.flags.reset == 1

# Large packets
frame.len > 1000

# Specific MAC address
eth.addr == aa:bb:cc:dd:ee:ff

# Broadcast traffic
eth.dst == ff:ff:ff:ff:ff:ff

# Non-local traffic
!(ip.addr == 192.168.0.0/16)

# TCP 3-way handshake
tcp.flags.syn == 1 or tcp.flags.ack == 1

# HTTP POST requests with data
http.request.method == "POST" and http.file_data
```

---

## 🎣 Capture Filters

### BPF Syntax (Berkeley Packet Filter)

Capture filters use **BPF syntax** (different from display filters!).

### Basic Syntax

```bash
# Host
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Network
net 192.168.1.0/24
src net 10.0.0.0/8

# Port
port 80
src port 443
dst port 22
portrange 1-1024

# Protocol
tcp
udp
icmp
arp
```

### Combined Filters

```bash
# Host and port
host 192.168.1.1 and port 80

# Specific conversation
host 192.168.1.1 and host 192.168.1.2

# Exclude traffic
not port 22
not host 10.0.0.1

# Complex filter
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Common Capture Filters

| Purpose | Filter |
|---------|--------|
| Specific host | `host 192.168.1.1` |
| Web traffic | `port 80 or port 443` |
| DNS only | `port 53` |
| SSH only | `port 22` |
| Not broadcast | `not broadcast` |
| Specific subnet | `net 192.168.1.0/24` |

---

## 🔬 Protocol Analysis

### TCP Analysis

```bash
# TCP flags
tcp.flags.syn == 1 and tcp.flags.ack == 0    # SYN only
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# TCP problems
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# TCP stream
tcp.stream == 0
```

### HTTP Analysis

```bash
# HTTP requests
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# HTTP responses
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Errors

# HTTP content
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### DNS Analysis

```bash
# DNS queries
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # A record
dns.qry.type == 28     # AAAA record
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# DNS responses
dns.flags.response == 1
dns.flags.rcode != 0   # Errors
```

### TLS/SSL Analysis

```bash
# TLS traffic
tls

# TLS handshake
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Certificates
tls.handshake.certificate

# TLS version
tls.record.version == 0x0303  # TLS 1.2
```

### ARP Analysis

```bash
# All ARP
arp

# ARP requests
arp.opcode == 1

# ARP replies
arp.opcode == 2

# Gratuitous ARP (possible spoofing)
arp.isgratuitous == 1
```

---

## 🔗 Following Streams

### Follow TCP Stream

```
Right-click packet → Follow → TCP Stream

Or: Analyze → Follow → TCP Stream
```

### Follow HTTP Stream

```
Right-click HTTP packet → Follow → HTTP Stream
```

### Follow UDP Stream

```
Right-click UDP packet → Follow → UDP Stream
```

### Stream Window

- Shows complete conversation
- Color-coded (client/server)
- Save as text or raw data
- Filter buttons for direction

---

## 📊 Statistics & Analysis

### Protocol Hierarchy

```
Statistics → Protocol Hierarchy

Shows breakdown of protocols captured
```

### Conversations

```
Statistics → Conversations

View all conversations by:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Endpoints

```
Statistics → Endpoints

List all unique endpoints
```

### IO Graph

```
Statistics → IO Graph

Visualize traffic over time
```

### Expert Information

```
Analyze → Expert Information

Shows warnings, errors, notes:
- TCP retransmissions
- Malformed packets
- Errors
```

### HTTP Statistics

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (CLI)

### Basic Usage

```bash
# Capture packets
tshark -i eth0

# Read capture file
tshark -r capture.pcap

# Write to file
tshark -i eth0 -w output.pcap
```

### Apply Filters

```bash
# Capture filter
tshark -i eth0 -f "port 80"

# Display filter
tshark -r capture.pcap -Y "http"
```

### Output Options

```bash
# Show specific fields
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# JSON output
tshark -r capture.pcap -T json

# Show statistics
tshark -r capture.pcap -q -z io,stat,1
```

### Common tshark Examples

```bash
# Live capture HTTP requests
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Extract DNS queries
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Count packets by IP
tshark -r capture.pcap -q -z ip_hosts,tree

# HTTP statistics
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Real-World Examples

### Example 1: Capture HTTP Traffic
```bash
# Capture filter: port 80
# Display filter: http
# Follow HTTP stream to see full requests/responses
```

### Example 2: Find Credentials
```bash
# Display filter
http.request.method == "POST" and http contains "password"

# Or for FTP
ftp contains "PASS"
```

### Example 3: Detect Port Scan
```bash
# Multiple SYN to different ports
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Look for many destinations from one source
Statistics → Conversations → TCP
```

### Example 4: DNS Exfiltration
```bash
# Large DNS queries
dns and frame.len > 512

# Unusual TXT records
dns.qry.type == 16
```

### Example 5: Extract Files
```
File → Export Objects → HTTP

Select files to export
```

### Example 6: Analyze TLS Handshake
```bash
tls.handshake

# See Client Hello for SNI
tls.handshake.type == 1
```

### Example 7: Find Cleartext Passwords
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Quick Reference

### Essential Display Filters

| Purpose | Filter |
|---------|--------|
| HTTP traffic | `http` |
| DNS traffic | `dns` |
| Specific IP | `ip.addr == 192.168.1.1` |
| Specific port | `tcp.port == 80` |
| TCP problems | `tcp.analysis.flags` |
| HTTP errors | `http.response.code >= 400` |
| Contains string | `frame contains "text"` |
| Exclude protocol | `not arp` |

### Common Capture Filters

| Purpose | Filter |
|---------|--------|
| Specific host | `host 192.168.1.1` |
| Web traffic | `port 80 or port 443` |
| DNS only | `port 53` |
| Not broadcast | `not broadcast` |

---

## ⌨️ Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+E` | Start/Stop capture |
| `Ctrl+K` | Capture options |
| `Ctrl+O` | Open file |
| `Ctrl+S` | Save file |
| `Ctrl+F` | Find packet |
| `Ctrl+G` | Go to packet |
| `Ctrl+→` | Next packet in conversation |
| `Ctrl+←` | Previous packet in conversation |
| `Ctrl+.` | Next packet with mark |
| `Ctrl+M` | Mark/Unmark packet |
| `Ctrl+Shift+M` | Mark all displayed |
| `Space` | Toggle protocol tree |
| `Tab` | Next pane |

---

## 💡 Tips & Best Practices

### Capture Tips

1. **Use Capture Filters**
   ```
   Reduce file size by filtering at capture
   ```

2. **Ring Buffer for Long Captures**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Capture Remotely**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Analysis Tips

1. **Color Rules**
   ```
   View → Coloring Rules
   Customize colors for protocols
   ```

2. **Time Display**
   ```
   View → Time Display Format
   Try "Seconds Since Beginning of Capture"
   ```

3. **Name Resolution**
   ```
   View → Name Resolution
   Enable/disable as needed
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
> **Unauthorized network monitoring is illegal in most jurisdictions.**

---

## 📚 Resources

### Official Resources
- [Wireshark Official](https://www.wireshark.org/)
- [Wireshark User Guide](https://www.wireshark.org/docs/wsug_html/)
- [Display Filter Reference](https://www.wireshark.org/docs/dfref/)
- [Wireshark Wiki](https://wiki.wireshark.org/)

### Learning Resources
- [Wireshark Tutorial](https://www.wireshark.org/docs/wsug_html/)
- [Sample Captures](https://wiki.wireshark.org/SampleCaptures)

### Related Cheatsheets
- [Nmap](../Nmap/README.md)
- [Metasploit](../Metasploit/README.md)
- [Hydra](../Hydra/README.md)

---

<p align="center">
  <b>🦈 Master Network Analysis!</b><br>
  <i>See everything on the wire</i>
</p>
