# 🐉 Kali Linux Tools - Complete Reference (600+ Tools)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    TOOLS REFERENCE GUIDE
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Tools">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Categories">
</p>

---

## 📋 Table of Contents

1. [Information Gathering](#1--information-gathering)
2. [Vulnerability Analysis](#2--vulnerability-analysis)
3. [Web Application Analysis](#3--web-application-analysis)
4. [Database Assessment](#4--database-assessment)
5. [Password Attacks](#5--password-attacks)
6. [Wireless Attacks](#6--wireless-attacks)
7. [Reverse Engineering](#7--reverse-engineering)
8. [Exploitation Tools](#8--exploitation-tools)
9. [Sniffing & Spoofing](#9--sniffing--spoofing)
10. [Post Exploitation](#10--post-exploitation)
11. [Forensics](#11--forensics)
12. [Reporting Tools](#12--reporting-tools)
13. [Social Engineering](#13--social-engineering)
14. [Hardware Hacking](#14--hardware-hacking)

---

## 1. 🔍 Information Gathering

### DNS Analysis

| Tool | Description |
|------|-------------|
| **dnsrecon** | DNS enumeration script |
| **dnsenum** | DNS enumeration tool |
| **fierce** | DNS reconnaissance tool |
| **dnsmap** | Subdomain brute-forcer |
| **dnstracer** | DNS trace tool |
| **dnswalk** | DNS database debugger |

### Host Discovery

| Tool | Description |
|------|-------------|
| **nmap** | Network mapper & port scanner |
| **masscan** | Fast port scanner |
| **hping3** | Network packet generator |
| **fping** | Fast ping sweep |
| **arping** | ARP ping utility |
| **netdiscover** | Active/passive ARP reconnaissance |
| **unicornscan** | Asynchronous network scanner |

### Network & Port Scanners

| Tool | Description |
|------|-------------|
| **nmap** | Most popular network scanner |
| **masscan** | Fastest port scanner |
| **rustscan** | Modern fast scanner |
| **zmap** | Fast single-packet network scanner |
| **unicornscan** | Async TCP/UDP scanner |
| **nbtscan** | NetBIOS name scanner |
| **onesixtyone** | SNMP scanner |

### OSINT Tools

| Tool | Description |
|------|-------------|
| **maltego** | OSINT and graphical link analysis |
| **spiderfoot** | OSINT automation tool |
| **recon-ng** | Web reconnaissance framework |
| **theHarvester** | Email, subdomain, IP harvester |
| **shodan** | IoT search engine CLI |
| **amass** | Attack surface mapping |
| **subfinder** | Subdomain discovery tool |
| **assetfinder** | Find domains and subdomains |
| **waybackurls** | Fetch URLs from Wayback Machine |
| **gau** | Get All URLs |

### Route Analysis

| Tool | Description |
|------|-------------|
| **traceroute** | Network route tracing |
| **mtr** | Network diagnostic tool |
| **netmask** | Network mask tool |
| **tcptraceroute** | TCP traceroute |

### SMB Analysis

| Tool | Description |
|------|-------------|
| **smbclient** | SMB client |
| **smbmap** | SMB share enumeration |
| **enum4linux** | Windows/Samba enumeration |
| **enum4linux-ng** | Next-gen enum4linux |
| **rpcclient** | MS-RPC client |
| **nbtscan** | NetBIOS scanner |

### SMTP Analysis

| Tool | Description |
|------|-------------|
| **smtp-user-enum** | SMTP user enumeration |
| **swaks** | SMTP test tool |

### SNMP Analysis

| Tool | Description |
|------|-------------|
| **snmpwalk** | SNMP data retrieval |
| **snmpcheck** | SNMP device enumerator |
| **onesixtyone** | Fast SNMP scanner |
| **braa** | Mass SNMP scanner |

### SSL Analysis

| Tool | Description |
|------|-------------|
| **sslscan** | SSL/TLS scanner |
| **sslyze** | SSL/TLS analysis tool |
| **testssl.sh** | SSL/TLS testing |

---

## 2. 🔓 Vulnerability Analysis

### Vulnerability Scanners

| Tool | Description |
|------|-------------|
| **nmap** | NSE vulnerability scripts |
| **nikto** | Web server scanner |
| **openvas** | Open vulnerability assessment |
| **legion** | Network penetration testing |
| **lynis** | Security auditing tool |
| **nuclei** | Template-based vuln scanner |

### Fuzzing Tools

| Tool | Description |
|------|-------------|
| **wfuzz** | Web fuzzer |
| **ffuf** | Fast web fuzzer |
| **gobuster** | Directory/DNS brute-forcer |
| **dirb** | Web content scanner |
| **dirbuster** | Directory brute-forcer |
| **feroxbuster** | Recursive content discovery |
| **spike** | Protocol fuzzer |
| **sfuzz** | Simple fuzzer |

### VoIP Tools

| Tool | Description |
|------|-------------|
| **sipvicious** | SIP protocol tools |
| **voiphopper** | VoIP VLAN hopping |

---

## 3. 🌐 Web Application Analysis

### CMS Identification

| Tool | Description |
|------|-------------|
| **wpscan** | WordPress vulnerability scanner |
| **joomscan** | Joomla scanner |
| **droopescan** | Drupal/WordPress/Joomla scanner |
| **cmseek** | CMS detection and exploitation |

### Web Vulnerability Scanners

| Tool | Description |
|------|-------------|
| **nikto** | Web server scanner |
| **burpsuite** | Web app security testing |
| **zaproxy** | OWASP ZAP proxy |
| **wapiti** | Web vulnerability scanner |
| **skipfish** | Web app security scanner |
| **arachni** | Web app security scanner |
| **w3af** | Web app attack framework |
| **vega** | Web security scanner |

### Web Crawlers

| Tool | Description |
|------|-------------|
| **cutycapt** | Webpage screenshot |
| **dirb** | Web content scanner |
| **dirbuster** | Directory brute force |
| **gobuster** | Directory/DNS brute-forcer |
| **feroxbuster** | Fast content discovery |

### Web Application Proxies

| Tool | Description |
|------|-------------|
| **burpsuite** | Web proxy & scanner |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | HTTP proxy |
| **proxychains** | Proxy chains |

### SQL Injection

| Tool | Description |
|------|-------------|
| **sqlmap** | SQL injection automation |
| **sqlninja** | SQL Server injection |
| **bbqsql** | Blind SQL injection |
| **jsql-injection** | Java SQL injection |
| **ghauri** | SQL injection tool |

### XSS Tools

| Tool | Description |
|------|-------------|
| **xsser** | XSS vulnerability tester |
| **xssstrike** | Advanced XSS detection |
| **dalfox** | Parameter analysis for XSS |

---

## 4. 💾 Database Assessment

| Tool | Description |
|------|-------------|
| **sqlmap** | SQL injection automation |
| **sqlninja** | MS SQL injection |
| **jsql-injection** | Java-based SQLi |
| **oscanner** | Oracle scanner |
| **tnscmd10g** | Oracle TNS listener tool |
| **odat** | Oracle database attacking tool |
| **mdbtools** | MS Access tools |
| **sidguesser** | Oracle SID guesser |

---

## 5. 🔑 Password Attacks

### Offline Attacks

| Tool | Description |
|------|-------------|
| **john** | John the Ripper password cracker |
| **hashcat** | GPU password cracker |
| **ophcrack** | Windows password cracker |
| **hashid** | Hash identifier |
| **hash-identifier** | Hash type identifier |
| **chntpw** | Windows password reset |
| **samdump2** | SAM dump utility |
| **crunch** | Wordlist generator |
| **cewl** | Custom wordlist generator |
| **cupp** | User password profiler |
| **rsmangler** | Wordlist mangler |
| **wordlists** | Collection of wordlists |

### Online Attacks

| Tool | Description |
|------|-------------|
| **hydra** | Network login cracker |
| **medusa** | Parallel login brute-forcer |
| **ncrack** | Network auth cracker |
| **patator** | Multi-purpose brute-forcer |
| **thc-pptp-bruter** | PPTP brute-forcer |
| **crowbar** | Brute-force tool |

### Passing the Hash

| Tool | Description |
|------|-------------|
| **mimikatz** | Windows credential extractor |
| **pth-toolkit** | Pass-the-hash toolkit |
| **smbmap** | SMB enumeration |
| **crackmapexec** | Network pentest tool |
| **evil-winrm** | Windows remote management |

---

## 6. 📡 Wireless Attacks

### Bluetooth

| Tool | Description |
|------|-------------|
| **bluelog** | Bluetooth scanner |
| **bluemaho** | Bluetooth security testing |
| **blueranger** | Bluetooth proximity detection |
| **btscanner** | Bluetooth device scanner |
| **redfang** | Hidden Bluetooth scanner |
| **spooftooph** | Bluetooth spoofing |

### WiFi Tools

| Tool | Description |
|------|-------------|
| **aircrack-ng** | WiFi security suite |
| **airmon-ng** | Monitor mode enabler |
| **airodump-ng** | Packet capture |
| **aireplay-ng** | Packet injection |
| **airbase-ng** | Fake AP |
| **wifite** | Automated WiFi auditor |
| **fern-wifi-cracker** | WiFi auditing tool |
| **kismet** | Wireless network detector |
| **reaver** | WPS attack tool |
| **bully** | WPS brute-force |
| **pixiewps** | WPS offline attack |
| **wifiphisher** | WiFi phishing tool |
| **fluxion** | MitM WPA attack |
| **hostapd-wpe** | Wireless credential harvesting |
| **cowpatty** | WPA-PSK cracker |
| **asleap** | LEAP/PPTP password recovery |
| **eapmd5pass** | EAP-MD5 password cracker |
| **mdk3** | Wireless attack tool |
| **mdk4** | Wireless exploitation tool |

### RFID/NFC

| Tool | Description |
|------|-------------|
| **mfoc** | Mifare Classic offline cracker |
| **mfcuk** | Mifare Classic universal toolkit |
| **mfterm** | Terminal for Mifare Classic |
| **libnfc** | NFC library |
| **proxmark3** | RFID research tool |

---

## 7. 🔧 Reverse Engineering

### Debuggers

| Tool | Description |
|------|-------------|
| **gdb** | GNU Debugger |
| **edb-debugger** | Cross-platform debugger |
| **radare2** | Reverse engineering framework |
| **x64dbg** | x64/x32 debugger |
| **ollydbg** | x86 debugger |
| **immunity-debugger** | Python scriptable debugger |

### Disassemblers

| Tool | Description |
|------|-------------|
| **ghidra** | NSA reverse engineering tool |
| **radare2** | Reverse engineering framework |
| **cutter** | Radare2 GUI |
| **rizin** | Reverse engineering framework |
| **objdump** | Object file disassembler |
| **IDA Free** | Interactive disassembler |

### Other RE Tools

| Tool | Description |
|------|-------------|
| **apktool** | Android APK tool |
| **dex2jar** | DEX to JAR converter |
| **jadx** | Java decompiler |
| **jd-gui** | Java decompiler GUI |
| **bytecode-viewer** | Java bytecode viewer |
| **androguard** | Android app analysis |
| **frida** | Dynamic instrumentation |
| **angr** | Binary analysis platform |
| **binwalk** | Firmware analysis tool |

---

## 8. 💣 Exploitation Tools

### Exploitation Frameworks

| Tool | Description |
|------|-------------|
| **metasploit-framework** | Exploitation framework |
| **armitage** | Metasploit GUI |
| **cobalt-strike** | Adversary simulation |
| **beef-xss** | Browser exploitation |
| **routersploit** | Router exploitation |
| **exploitdb** | Exploit database |
| **searchsploit** | Exploit-DB search |

### Shellcode Tools

| Tool | Description |
|------|-------------|
| **msfvenom** | Payload generator |
| **veil** | Payload generation framework |
| **shellnoob** | Shellcode toolkit |

### Network Exploitation

| Tool | Description |
|------|-------------|
| **responder** | LLMNR/NBT-NS poisoner |
| **impacket** | Network protocol toolkit |
| **crackmapexec** | Post-exploitation tool |
| **evil-winrm** | WinRM shell |
| **powershell-empire** | Post-exploitation framework |

---

## 9. 📡 Sniffing & Spoofing

### Network Sniffers

| Tool | Description |
|------|-------------|
| **wireshark** | Network protocol analyzer |
| **tcpdump** | Command-line sniffer |
| **tshark** | Terminal Wireshark |
| **ettercap** | Network sniffer/MitM |
| **dsniff** | Network auditing tools |
| **net-creds** | Credential sniffer |
| **pcredz** | Credential extractor |

### Spoofing Tools

| Tool | Description |
|------|-------------|
| **arpspoof** | ARP spoofing |
| **dnsspoof** | DNS spoofing |
| **macchanger** | MAC address changer |
| **ettercap** | ARP poisoning |
| **bettercap** | Network attack tool |
| **mitmproxy** | HTTP MitM proxy |
| **sslstrip** | HTTPS stripping |

### VoIP Sniffing

| Tool | Description |
|------|-------------|
| **sipvicious** | SIP/VoIP tools |
| **voiphopper** | VoIP VLAN hopping |

---

## 10. 🎯 Post Exploitation

### OS Backdoors

| Tool | Description |
|------|-------------|
| **msfvenom** | Payload generator |
| **veil** | Antivirus evasion |
| **shellter** | Dynamic shellcode injector |
| **weevely** | PHP web shell |

### Tunneling

| Tool | Description |
|------|-------------|
| **sshuttle** | SSH tunnel VPN |
| **chisel** | HTTP tunneling |
| **proxychains** | Proxy chains |
| **socat** | Relay tool |
| **plink** | PuTTY link |
| **stunnel** | TLS tunneling |
| **dns2tcp** | DNS tunneling |
| **iodine** | DNS tunnel |
| **ptunnel** | ICMP tunneling |
| **pwnat** | NAT traversal |
| **udptunnel** | UDP tunneling |

### Windows Post-Exploitation

| Tool | Description |
|------|-------------|
| **mimikatz** | Windows credential dumper |
| **crackmapexec** | Windows/AD pentesting |
| **evil-winrm** | WinRM shell |
| **impacket** | Windows protocols |
| **bloodhound** | Active Directory recon |
| **powershell-empire** | PowerShell post-exploit |
| **rubeus** | Kerberos abuse |
| **kerbrute** | Kerberos brute-force |

### Linux Post-Exploitation

| Tool | Description |
|------|-------------|
| **linpeas** | Linux privilege escalation |
| **linenum** | Linux enumeration |
| **pspy** | Process monitor |
| **linux-exploit-suggester** | Kernel exploit finder |

---

## 11. 🔬 Forensics

### Forensic Frameworks

| Tool | Description |
|------|-------------|
| **autopsy** | Digital forensics platform |
| **sleuthkit** | Forensic toolkit |
| **dff** | Digital forensics framework |

### Forensic Carving

| Tool | Description |
|------|-------------|
| **foremost** | File carving tool |
| **scalpel** | File carver |
| **bulk_extractor** | Info extractor |
| **photorec** | Photo recovery |
| **testdisk** | Data recovery |

### Forensic Imaging

| Tool | Description |
|------|-------------|
| **dc3dd** | Forensic disk imaging |
| **dcfldd** | Enhanced dd |
| **guymager** | Forensic imager GUI |

### Memory Forensics

| Tool | Description |
|------|-------------|
| **volatility** | Memory forensics |
| **volatility3** | Memory analysis |
| **lime** | Linux memory extractor |

### Network Forensics

| Tool | Description |
|------|-------------|
| **wireshark** | Packet analysis |
| **tcpdump** | Packet capture |
| **ngrep** | Network grep |
| **NetworkMiner** | Network forensics |

### PDF Forensics

| Tool | Description |
|------|-------------|
| **pdfid** | PDF scanner |
| **pdf-parser** | PDF analysis |
| **peepdf** | PDF analysis tool |

---

## 12. 📝 Reporting Tools

| Tool | Description |
|------|-------------|
| **cutycapt** | Webpage screenshots |
| **faraday** | Collaborative pentest tool |
| **maltego** | OSINT visualization |
| **pipal** | Password analysis |
| **recordmydesktop** | Screen recorder |
| **cherrytree** | Note-taking app |
| **dradis** | Collaboration platform |
| **metagoofil** | Metadata extraction |
| **eyewitness** | Website screenshot |
| **gowitness** | Website screenshot |

---

## 13. 🎭 Social Engineering

| Tool | Description |
|------|-------------|
| **setoolkit** | Social Engineering Toolkit |
| **gophish** | Phishing framework |
| **king-phisher** | Phishing campaign |
| **evilginx2** | MitM phishing framework |
| **beef-xss** | Browser exploitation |
| **maltego** | OSINT relationships |
| **theHarvester** | Email harvesting |
| **sherlock** | Username OSINT |
| **social-analyzer** | Social media analysis |

---

## 14. 🔧 Hardware Hacking

| Tool | Description |
|------|-------------|
| **arduino** | Hardware platform |
| **binwalk** | Firmware analysis |
| **flashrom** | Flash programmer |
| **openocd** | On-chip debugging |
| **proxmark3** | RFID research |

---

## 📊 Quick Reference - Top 50 Tools

| # | Tool | Category | Use Case |
|---|------|----------|----------|
| 1 | **nmap** | Scanning | Port scanning |
| 2 | **metasploit** | Exploitation | Exploit framework |
| 3 | **burpsuite** | Web | Web testing |
| 4 | **wireshark** | Sniffing | Packet analysis |
| 5 | **john** | Password | Password cracking |
| 6 | **hashcat** | Password | GPU cracking |
| 7 | **hydra** | Password | Online brute-force |
| 8 | **sqlmap** | Web | SQL injection |
| 9 | **aircrack-ng** | Wireless | WiFi cracking |
| 10 | **nikto** | Web | Web scanning |
| 11 | **gobuster** | Web | Directory brute-force |
| 12 | **ffuf** | Web | Web fuzzing |
| 13 | **dirb** | Web | Directory scanning |
| 14 | **wpscan** | Web | WordPress scanning |
| 15 | **enum4linux** | Info | SMB enumeration |
| 16 | **crackmapexec** | Post-Exploit | AD pentesting |
| 17 | **mimikatz** | Post-Exploit | Credential dump |
| 18 | **responder** | Exploitation | LLMNR poisoning |
| 19 | **impacket** | Exploitation | Protocol tools |
| 20 | **bloodhound** | Post-Exploit | AD mapping |
| 21 | **netcat** | Utility | TCP/UDP utility |
| 22 | **socat** | Utility | Data relay |
| 23 | **tcpdump** | Sniffing | Packet capture |
| 24 | **ettercap** | Sniffing | MitM attacks |
| 25 | **bettercap** | Sniffing | Network attacks |
| 26 | **proxychains** | Utility | Proxy chains |
| 27 | **msfvenom** | Exploitation | Payload gen |
| 28 | **searchsploit** | Exploitation | Exploit search |
| 29 | **theHarvester** | OSINT | Email harvesting |
| 30 | **maltego** | OSINT | Link analysis |
| 31 | **recon-ng** | OSINT | Recon framework |
| 32 | **amass** | OSINT | Subdomain enum |
| 33 | **subfinder** | OSINT | Subdomain finder |
| 34 | **nuclei** | Vuln | Template scanner |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Wireless | Auto WiFi audit |
| 37 | **ghidra** | RE | Reverse engineering |
| 38 | **radare2** | RE | Binary analysis |
| 39 | **gdb** | RE | Debugger |
| 40 | **binwalk** | RE | Firmware analysis |
| 41 | **volatility** | Forensics | Memory analysis |
| 42 | **autopsy** | Forensics | Forensic platform |
| 43 | **foremost** | Forensics | File carving |
| 44 | **setoolkit** | Social Eng | SE Toolkit |
| 45 | **beef-xss** | Web | Browser exploit |
| 46 | **linpeas** | PrivEsc | Linux enum |
| 47 | **winpeas** | PrivEsc | Windows enum |
| 48 | **chisel** | Tunneling | HTTP tunnel |
| 49 | **sshuttle** | Tunneling | SSH VPN |
| 50 | **evil-winrm** | Post-Exploit | WinRM shell |

---

## 📚 Resources

- [Kali Linux Tools](https://www.kali.org/tools/)
- [Kali Documentation](https://www.kali.org/docs/)
- [Kali Metapackages](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Master Kali Linux!</b><br>
  <i>600+ tools at your fingertips</i>
</p>
