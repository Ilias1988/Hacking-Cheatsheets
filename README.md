# 🔴 Hacking Cheatsheets

[🇮🇹 Versione Italiana](README.it.md)

```
    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
    ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
    ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
    ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
    ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
     ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
    ██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
    ██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
    ██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
    ╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
     ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Penetration-Testing-red?style=for-the-badge" alt="Penetration Testing">
  <img src="https://img.shields.io/badge/Ethical-Hacking-orange?style=for-the-badge" alt="Ethical Hacking">
  <img src="https://img.shields.io/badge/Cybersecurity-blue?style=for-the-badge" alt="Cybersecurity">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License">
</p>

<p align="center">
  <b>📚 A comprehensive collection of penetration testing cheatsheets for security professionals</b>
</p>

<p align="center">
  <a href="#-cheatsheets">Cheatsheets</a> •
  <a href="#-quick-start">Quick Start</a> •
  <a href="#-contributing">Contributing</a> •
  <a href="#-license">License</a>
</p>

---

## 🎯 About

**Hacking Cheatsheets** is a curated collection of quick reference guides for penetration testing and ethical hacking tools. Each cheatsheet provides:

- ✅ **Clear explanations** of tool functionality
- ✅ **Command syntax** with practical examples
- ✅ **Real-world scenarios** and use cases
- ✅ **Quick reference tables** for rapid lookup
- ✅ **Tips & best practices** from experienced pentesters

---

## 🎯 Attack Methodology (Kill Chain)

> **NEW!** Complete step-by-step guide for penetration testing based on MITRE ATT&CK framework.

| Phase | Description | Guide |
|-------|-------------|-------|
| **1. Initial Access** | Exploits, phishing, credentials | [📄 View](./Attack-Methodology/01-Initial-Access.md) |
| **2. Enumeration** | System & network discovery | [📄 View](./Attack-Methodology/02-Enumeration.md) |
| **3. Privilege Escalation** | Get root/SYSTEM access | [📄 View](./Attack-Methodology/03-Privilege-Escalation.md) |
| **4. Lateral Movement** | Move across the network | [📄 View](./Attack-Methodology/04-Lateral-Movement.md) |
| **5. Persistence** | Maintain access | [📄 View](./Attack-Methodology/05-Persistence.md) |
| **6. Defense Evasion** | Bypass AV/EDR/AMSI | [📄 View](./Attack-Methodology/06-Defense-Evasion.md) |
| **7. Actions on Objectives** | Data exfiltration & impact | [📄 View](./Attack-Methodology/07-Actions-Objectives.md) |

👉 **[Full Kill Chain Overview](./Attack-Methodology/README.md)**

---

## 🛡️ Blue Team (Defensive Security)

> **NEW!** Complete defensive security guides for SOC analysts and incident responders.

| Topic | Description | Guide |
|-------|-------------|-------|
| **Incident Response** | IR lifecycle, containment, procedures | [📄 View](./Blue-Team/Incident-Response.md) |
| **Log Analysis** | Windows/Linux log analysis & Event IDs | [📄 View](./Blue-Team/Log-Analysis.md) |
| **SIEM Detection** | Splunk/ELK queries & dashboards | [📄 View](./Blue-Team/SIEM-Detection.md) |
| **Threat Hunting** | Proactive hunting techniques | [📄 View](./Blue-Team/Threat-Hunting.md) |
| **Hardening** | Windows/Linux hardening checklists | [📄 View](./Blue-Team/Hardening.md) |
| **Sigma Rules** | Platform-agnostic detection rules | [📄 View](./Blue-Team/Sigma-Rules.md) |
| **YARA Rules** | Malware & IOC detection patterns | [📄 View](./Blue-Team/YARA-Rules.md) |

👉 **[Full Blue Team Overview](./Blue-Team/README.md)**

---

## ☁️ Cloud Security

> **NEW!** Cloud pentesting guides for AWS, Azure, and GCP.

| Provider | Description | Guide |
|----------|-------------|-------|
| **AWS** | S3, IAM, Lambda, EC2, IMDS | [📄 View](./Cloud-Security/AWS-Pentesting.md) |
| **Azure** | Azure AD, Blob Storage, VMs, Key Vault | [📄 View](./Cloud-Security/Azure-Pentesting.md) |
| **GCP** | GCS, IAM, Compute, Cloud Functions | [📄 View](./Cloud-Security/GCP-Pentesting.md) |

👉 **[Full Cloud Security Overview](./Cloud-Security/README.md)**

---

## 📱 Mobile Security

> **NEW!** Mobile app pentesting guides for Android and iOS.

| Platform | Description | Guide |
|----------|-------------|-------|
| **Android** | APK analysis, Frida, root detection bypass | [📄 View](./Mobile-Security/Android-Pentesting.md) |
| **iOS** | IPA analysis, jailbreak, Objection, keychain | [📄 View](./Mobile-Security/iOS-Pentesting.md) |

👉 **[Full Mobile Security Overview](./Mobile-Security/README.md)**

---

## 🐳 Container Security

> **NEW!** Docker & Kubernetes pentesting guides.

| Platform | Description | Guide |
|----------|-------------|-------|
| **Docker** | Container escape, image analysis, daemon exploitation | [📄 View](./Container-Security/Docker-Pentesting.md) |
| **Kubernetes** | RBAC bypass, pod escape, secrets extraction | [📄 View](./Container-Security/Kubernetes-Pentesting.md) |

👉 **[Full Container Security Overview](./Container-Security/README.md)**

---

## 🎭 Social Engineering

> **NEW!** Social engineering techniques, phishing campaigns, and pretexting guides.

| Topic | Description | Guide |
|-------|-------------|-------|
| **Phishing** | Email phishing, GoPhish, Evilginx2, vishing, smishing | [📄 View](./Social-Engineering/Phishing.md) |
| **Pretexting** | Personas, scenarios, psychological manipulation | [📄 View](./Social-Engineering/Pretexting.md) |

👉 **[Full Social Engineering Overview](./Social-Engineering/README.md)**

---

## 📝 Reporting Templates

> **NEW!** Professional report templates for pentesters and bug bounty hunters.

| Template | Description | Guide |
|----------|-------------|-------|
| **Pentest Report** | Full penetration test report structure | [📄 View](./Reporting/Pentest-Report-Template.md) |
| **Bug Bounty Report** | HackerOne/Bugcrowd submission template | [📄 View](./Reporting/Bug-Bounty-Report-Template.md) |
| **Executive Summary** | Non-technical summary for C-level | [📄 View](./Reporting/Executive-Summary-Template.md) |

---

## 🔍 OSINT (Open Source Intelligence)

> **NEW!** Complete OSINT methodology and tool guides.

| Topic | Description | Guide |
|-------|-------------|-------|
| **People Search** | Find individuals online, phone/address lookup | [📄 View](./OSINT/People-Search.md) |
| **Email OSINT** | Email discovery, breach checking, verification | [📄 View](./OSINT/Email-OSINT.md) |
| **Social Media** | Username search, platform-specific OSINT | [📄 View](./OSINT/Social-Media-OSINT.md) |
| **Domain & IP** | WHOIS, DNS, subdomain, IP reconnaissance | [📄 View](./OSINT/Domain-IP-OSINT.md) |
| **Image OSINT** | Reverse image search, EXIF metadata | [📄 View](./OSINT/Image-OSINT.md) |

👉 **[Full OSINT Overview](./OSINT/README.md)**

---

## 🌐 Network Pentesting

> **NEW!** Complete network penetration testing guides.

| Topic | Description | Guide |
|-------|-------------|-------|
| **Port Scanning** | Nmap, Masscan, RustScan | [📄 View](./Network-Pentesting/Port-Scanning.md) |
| **Network Enumeration** | SMB, SNMP, NFS, LDAP, DNS | [📄 View](./Network-Pentesting/Network-Enumeration.md) |
| **MITM Attacks** | ARP spoofing, DNS spoofing, SSL strip | [📄 View](./Network-Pentesting/MITM-Attacks.md) |
| **Service Exploitation** | FTP, SSH, SMB, RDP, databases | [📄 View](./Network-Pentesting/Service-Exploitation.md) |

👉 **[Full Network Pentesting Overview](./Network-Pentesting/README.md)**

---

## 🏁 CTF Cheatsheets

> **NEW!** Complete CTF competition guides for HackTheBox, TryHackMe, PicoCTF.

| Category | Description | Guide |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, Auth bypass | [📄 View](./CTF/Web-CTF.md) |
| **Crypto** | RSA, AES, hashes, encoding, XOR | [📄 View](./CTF/Crypto-CTF.md) |
| **Reverse Engineering** | Ghidra, IDA, GDB, patching | [📄 View](./CTF/Reverse-Engineering-CTF.md) |
| **Forensics** | Steganography, memory, disk, PCAP | [📄 View](./CTF/Forensics-CTF.md) |
| **Pwn/Binary** | Buffer overflow, ROP, shellcode | [📄 View](./CTF/Pwn-CTF.md) |

👉 **[Full CTF Overview](./CTF/README.md)**

---

## 📡 IoT Hacking

> **NEW!** IoT device hacking, firmware analysis, and hardware hacking guides.

| Topic | Description | Guide |
|-------|-------------|-------|
| **Firmware Analysis** | Binwalk, extraction, RE, secrets | [📄 View](./IoT-Hacking/Firmware-Analysis.md) |
| **Hardware Hacking** | UART, JTAG, SPI, I2C, debug ports | [📄 View](./IoT-Hacking/Hardware-Hacking.md) |

👉 **[Full IoT Hacking Overview](./IoT-Hacking/README.md)**

---

## 📖 Cheatsheets

### 🔴 Exploitation Framework

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Metasploit Framework** | The world's most used penetration testing framework | [📄 View](./Metasploit/README.md) |
| **Meterpreter** | Advanced post-exploitation payload | [📄 View](./Metasploit/Meterpreter.md) |
| **Mimikatz** | Windows credential extraction tool | [📄 View](./Mimikatz/README.md) |
| **PowerShell** | Windows scripting for pentesting | [📄 View](./PowerShell/README.md) |
| **Linux Commands** | Linux & Bash for pentesting | [📄 View](./Linux-Commands/README.md) |

### 🔍 Reconnaissance & Scanning

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Nmap** | Network discovery and security auditing | [📄 View](./Nmap/README.md) |
| **Gobuster** | Directory/DNS/VHost brute-forcing | [📄 View](./Gobuster/README.md) |
| **Nikto** | Web server scanner | [📄 View](./Nikto/README.md) |

### 🌐 Web Application Testing

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **SQLMap** | SQL injection automation tool | [📄 View](./SQLMap/README.md) |
| **Burp Suite** | Web application security testing platform | [📄 View](./Burp-Suite/README.md) |
| **OWASP ZAP** | Free web app security scanner | [📄 View](./OWASP-ZAP/README.md) |

### 🔓 Password Cracking

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Hydra** | Fast network login cracker | [📄 View](./Hydra/README.md) |
| **John the Ripper** | Legendary password cracker | [📄 View](./John-The-Ripper/README.md) |
| **Hashcat** | World's fastest GPU password cracker | [📄 View](./Hashcat/README.md) |

### 📡 Network Analysis

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Wireshark** | Network protocol analyzer | [📄 View](./Wireshark/README.md) |
| **tcpdump** | Command-line packet analyzer | [📄 View](./tcpdump/README.md) |

### 🐛 Bug Bounty

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **⭐ BB Methodology** | Complete bug bounty hunting guide | [📄 View](./Bug-Bounty-Methodology/README.md) |
| **Nuclei** | Template-based vulnerability scanner | [📄 View](./Nuclei/README.md) |
| **ffuf** | Fast web fuzzer | [📄 View](./ffuf/README.md) |
| **Subfinder** | Subdomain discovery | [📄 View](./Subfinder/README.md) |
| **httpx** | HTTP probe & toolkit | [📄 View](./httpx/README.md) |
| **Amass** | In-depth attack surface mapping | [📄 View](./Amass/README.md) |
| **GAU** | Get All URLs from archives | [📄 View](./GAU/README.md) |
| **Katana** | Next-gen web crawler | [📄 View](./Katana/README.md) |
| **Arjun** | Hidden parameter discovery | [📄 View](./Arjun/README.md) |
| **Dalfox** | XSS vulnerability scanner | [📄 View](./Dalfox/README.md) |

### 💉 Payloads Collection

| Vulnerability | Description | Cheatsheet |
|---------------|-------------|------------|
| **XSS** | Cross-Site Scripting payloads | [📄 View](./Payloads/XSS.md) |
| **SQLi** | SQL Injection payloads | [📄 View](./Payloads/SQLi.md) |
| **LFI** | Local File Inclusion payloads | [📄 View](./Payloads/LFI.md) |
| **SSTI** | Server-Side Template Injection | [📄 View](./Payloads/SSTI.md) |
| **Command Injection** | OS command injection payloads | [📄 View](./Payloads/Command-Injection.md) |
| **NoSQL Injection** | MongoDB, CouchDB, Redis payloads | [📄 View](./Payloads/NoSQL-Injection.md) |
| **Deserialization** | Java, PHP, Python, .NET payloads | [📄 View](./Payloads/Deserialization.md) |
| **WebSocket Attacks** | CSWSH, injection, hijacking | [📄 View](./Payloads/WebSocket-Attacks.md) |
| **GraphQL Injection** | Introspection, IDOR, injection | [📄 View](./Payloads/GraphQL-Injection.md) |

### 🔴 Web Vulnerabilities

| Vulnerability | Description | Cheatsheet |
|---------------|-------------|------------|
| **API Security** | REST/GraphQL/JWT testing guide | [📄 View](./API-Security/README.md) |
| **IDOR** | Insecure Direct Object Reference | [📄 View](./IDOR/README.md) |
| **SSRF** | Server-Side Request Forgery | [📄 View](./SSRF/README.md) |
| **XXE** | XML External Entity Injection | [📄 View](./XXE/README.md) |
| **Race Conditions** | Timing & concurrency attacks | [📄 View](./Race-Conditions/README.md) |
| **Auth Bypass** | Authentication bypass techniques | [📄 View](./Auth-Bypass/README.md) |
| **CORS** | Cross-Origin misconfigurations | [📄 View](./CORS/README.md) |
| **Open Redirect** | URL redirect vulnerabilities | [📄 View](./Open-Redirect/README.md) |

### 🛡️ Advanced Attack Techniques

| Topic | Description | Cheatsheet |
|-------|-------------|------------|
| **WAF Bypass** | Origin IP discovery & WAF evasion | [📄 View](./WAF-Bypass/README.md) |
| **Cloudflare Bypass** | Find origin IP behind Cloudflare | [📄 View](./Cloudflare-Bypass/README.md) |
| **Subdomain Takeover** | Dangling CNAME exploitation | [📄 View](./Subdomain-Takeover/README.md) |
| **Cache Poisoning** | Web cache poisoning & deception | [📄 View](./Cache-Poisoning/README.md) |
| **HTTP Smuggling** | Request smuggling (CL.TE/TE.CL) | [📄 View](./HTTP-Request-Smuggling/README.md) |
| **Prototype Pollution** | JavaScript prototype attacks | [📄 View](./Prototype-Pollution/README.md) |

### 🔎 Dorking & OSINT

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Google Dorking** | Advanced Google search techniques | [📄 View](./Google-Dorking/README.md) |
| **Shodan** | IoT & device search engine | [📄 View](./Shodan/README.md) |
| **GitHub Dorking** | Secret hunting in repositories | [📄 View](./GitHub-Dorking/README.md) |

### 🔝 Privilege Escalation

| Topic | Description | Cheatsheet |
|-------|-------------|------------|
| **Linux PrivEsc** | Linux privilege escalation techniques | [📄 View](./Linux-PrivEsc/README.md) |
| **Windows PrivEsc** | Windows privilege escalation techniques | [📄 View](./Windows-PrivEsc/README.md) |

### 🔬 Digital Forensics

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Volatility** | Memory forensics framework | [📄 View](./Volatility/README.md) |
| **Autopsy** | Digital forensics platform (GUI) | [📄 View](./Autopsy/README.md) |
| **ExifTool** | Metadata extraction & analysis | [📄 View](./ExifTool/README.md) |
| **Binwalk** | Firmware analysis & extraction | [📄 View](./Binwalk/README.md) |

### 🔄 Reverse Engineering

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Ghidra** | NSA reverse engineering suite | [📄 View](./Ghidra/README.md) |
| **GDB** | GNU Debugger (Linux debugging) | [📄 View](./GDB/README.md) |
| **x64dbg** | Windows x64/x32 debugger | [📄 View](./x64dbg/README.md) |

### 📶 WiFi Hacking

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **Aircrack-ng** | WiFi hacking suite (WPA/WPA2) | [📄 View](./Aircrack-ng/README.md) |
| **Wifite** | Automated WiFi auditor | [📄 View](./Wifite/README.md) |
| **Bettercap** | Network attack framework (MITM/WiFi) | [📄 View](./Bettercap/README.md) |

### 🏢 Active Directory

| Tool | Description | Cheatsheet |
|------|-------------|------------|
| **⭐ AD Methodology** | Step-by-step attack guide | [📄 View](./AD-Attack-Methodology/README.md) |
| **BloodHound** | AD attack path visualization | [📄 View](./BloodHound/README.md) |
| **Impacket** | Python AD attack toolkit | [📄 View](./Impacket/README.md) |
| **CrackMapExec** | AD Swiss Army knife | [📄 View](./CrackMapExec/README.md) |
| **Rubeus** | Kerberos abuse toolkit | [📄 View](./Rubeus/README.md) |
| **PowerView** | PowerShell AD enumeration | [📄 View](./PowerView/README.md) |
| **Responder** | LLMNR/NBT-NS poisoning | [📄 View](./Responder/README.md) |
| **Evil-WinRM** | WinRM shell for pentesters | [📄 View](./Evil-WinRM/README.md) |
| **Kerbrute** | Kerberos user enum & spray | [📄 View](./Kerbrute/README.md) |

### 📚 Resources

| Resource | Description | Cheatsheet |
|----------|-------------|------------|
| **Wordlists** | Complete wordlist reference guide | [📄 View](./Wordlists/README.md) |
| **Kali Linux Tools** | 600+ tools by category | [📄 View](./Kali-Linux-Tools/README.md) |

---

## 🚀 Quick Start

### Clone the Repository

```bash
git clone https://github.com/Ilias1988/Hacking-Cheatsheets.git
cd Hacking-Cheatsheets
```

### Browse Cheatsheets

Navigate to any tool folder and open the README.md file:

```bash
# View Metasploit cheatsheet
cat Metasploit/README.md

# Or open in your favorite editor
code Metasploit/
```

### Offline Access

All cheatsheets are in Markdown format, making them:
- 📱 **Mobile-friendly** - Read on any device
- 🔌 **Offline accessible** - No internet required
- 🖨️ **Printable** - Create physical copies
- 🔍 **Searchable** - Use grep or your editor's search

---

## 📂 Repository Structure

```
Hacking-Cheatsheets/
│
├── README.md                # This file - Main index
├── README.it.md             # Italian translation - Main index
├── LICENSE                  # MIT License
├── CONTRIBUTING.md          # Contribution guidelines
├── CONTRIBUTING.it.md       # Italian contribution guidelines
├── .gitignore               # Git ignore rules
│
├── Metasploit/              # Metasploit Framework
│   ├── README.md            # Complete msfconsole guide
│   └── Meterpreter.md       # Meterpreter cheatsheet
│
├── Nmap/                    # Network Scanner
│   └── README.md            # Complete Nmap guide
│
├── Gobuster/                # Directory/DNS Enumeration
│   └── README.md            # Complete Gobuster guide
│
├── Nikto/                   # Web Server Scanner
│   └── README.md            # Complete Nikto guide
│
├── SQLMap/                  # SQL Injection Tool
│   └── README.md            # Complete SQLMap guide
│
├── Burp-Suite/              # Web Application Testing
│   └── README.md            # Complete Burp Suite guide
│
├── OWASP-ZAP/               # OWASP Zed Attack Proxy
│   └── README.md            # Complete ZAP guide
│
├── Hydra/                   # Network Login Cracker
│   └── README.md            # Complete Hydra guide
│
├── John-The-Ripper/         # Password Cracker
│   └── README.md            # Complete John guide
│
├── Hashcat/                 # GPU Password Cracker
│   └── README.md            # Complete Hashcat guide
│
├── Wireshark/               # Network Protocol Analyzer
│   └── README.md            # Complete Wireshark guide
│
├── tcpdump/                 # Command-Line Packet Analyzer
│   └── README.md            # Complete tcpdump guide
│
├── Nuclei/                  # Bug Bounty Scanner
│   └── README.md            # Complete Nuclei guide
│
├── ffuf/                    # Web Fuzzer
│   └── README.md            # Complete ffuf guide
│
├── Subfinder/               # Subdomain Discovery
│   └── README.md            # Complete Subfinder guide
│
├── httpx/                   # HTTP Probe & Toolkit
│   └── README.md            # Complete httpx guide
│
├── Google-Dorking/          # Google Search Hacking
│   └── README.md            # Complete Google Dorking guide
│
├── Shodan/                  # IoT Search Engine
│   └── README.md            # Complete Shodan guide
│
├── GitHub-Dorking/          # Secret Hunting
│   └── README.md            # Complete GitHub Dorking guide
│
└── ...
```

---

## 🤝 Contributing

Contributions are welcome! Please read our [Contributing Guidelines](CONTRIBUTING.md) before submitting a pull request.

### Ways to Contribute

- 📝 **Add new cheatsheets** for tools not yet covered
- 🔧 **Improve existing cheatsheets** with better examples
- 🐛 **Report issues** or suggest improvements
- 🌐 **Translate** cheatsheets to other languages
- ⭐ **Star this repo** to show your support!

---

## ⚠️ Legal Disclaimer

> **IMPORTANT:** These cheatsheets are intended for **educational purposes** and **authorized security testing only**. 
> 
> - ✅ Use on systems you own
> - ✅ Use with explicit written permission
> - ✅ Use in legal penetration testing engagements
> - ❌ Never use for unauthorized access
> - ❌ Never use for malicious purposes
> 
> **Unauthorized access to computer systems is illegal.** The authors are not responsible for any misuse of this information.

---

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🌟 Show Your Support

If you find these cheatsheets useful, please consider:

- ⭐ **Starring** this repository
- 🍴 **Forking** to contribute
- 📢 **Sharing** with fellow security professionals
- 💬 **Providing feedback** for improvements

---

## 📬 Contact

- **GitHub Issues** - For bug reports and feature requests
- **Pull Requests** - For contributions

---

<p align="center">
  <b>Happy Hacking! 🔴</b><br>
  <i>Remember: Hack responsibly, hack ethically!</i>
</p>

---

<p align="center">
  Made with ❤️ for the cybersecurity community
</p>
