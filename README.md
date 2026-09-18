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


## 🌐 Web Resources & Aesthetic Symbols Index
- [SYM 1D438](https://angelic-ribbon-text-78.pages.dev/symbol/sym-1d438/)
- [RIGHT HEAVY BRACKET BOX](https://dark-poetry-symbols-18.pages.dev/symbol/right-heavy-bracket-box/)
- [SYM 1F643](https://scholarly-vintage-symbols-48.pages.dev/symbol/sym-1f643/)
- [SYM 2647](https://ribbon-heart-fonts-86.pages.dev/symbol/sym-2647/)
- [TRENDING](https://coquette-aesthetic-symbols-14.pages.dev/ru/trending/)
- [SYM 1F47D](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f47d/)
- [SYM 1D464](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d464/)
- [SYM 1D477](https://alchemical-symbol-hub-52.pages.dev/symbol/sym-1d477/)
- [SYM 2672](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-2672/)
- [SYM 26BE](https://zen-typography-hub-86.pages.dev/symbol/sym-26be/)
- [SYM 262A](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-262a/)
- [SYM 1F4A9](https://soft-angel-unicode-43.pages.dev/symbol/sym-1f4a9/)
- [SYM 262B](https://zen-typography-hub-86.pages.dev/symbol/sym-262b/)
- [FREEFIRE NAMES](https://kawaii-kaomoji-hub-51.pages.dev/vi/freefire-names/)
- [SYM 1D464](https://matrix-glitch-text-59.pages.dev/symbol/sym-1d464/)
- [SYM 1D47E](https://classic-literature-symbols-64.pages.dev/symbol/sym-1d47e/)
- [SYM 2639](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-2639/)
- [SYM 1F636 200D 1F32B FE0F](https://kawaii-kaomoji-hub-12.pages.dev/symbol/sym-1f636-200d-1f32b-fe0f/)
- [SPARKLE DOT FLARE](https://angelic-bow-symbols-76.pages.dev/symbol/sparkle-dot-flare/)
- [SYM 26F4](https://cyber-clan-tags-38.pages.dev/symbol/sym-26f4/)
- [SYM 2674](https://zen-typography-hub-86.pages.dev/symbol/sym-2674/)
- [SYM 26AA](https://matrix-glitch-text-59.pages.dev/symbol/sym-26aa/)
- [SYM 1D459](https://anime-sparkle-text-14.pages.dev/symbol/sym-1d459/)
- [RIGHT WHITE CORNER BRACKET](https://baroque-text-decor-84.pages.dev/symbol/right-white-corner-bracket/)
- [SYM 1F928](https://gothic-bio-fonts-98.pages.dev/symbol/sym-1f928/)
- [SYM 1F970](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f970/)
- [TAURUS ZODIAC BULL](https://pastel-moe-kaomoji-91.pages.dev/symbol/taurus-zodiac-bull/)
- [SYM 2638](https://zen-spacing-text-68.pages.dev/symbol/sym-2638/)
- [SYM 1D452](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d452/)
- [SYM 2664](https://zen-typography-hub-86.pages.dev/symbol/sym-2664/)
- [SYM 26F4](https://coquette-aesthetic-symbols-96.pages.dev/symbol/sym-26f4/)
- [SYM 26D0](https://gothic-bio-fonts-61.pages.dev/symbol/sym-26d0/)
- [KAOMOJI](https://angelic-bow-symbols-76.pages.dev/pt/kaomoji/)
- [SYM 1F635 200D 1F4AB](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f635-200d-1f4ab/)
- [CUTE BUNNY RABBIT FACE](https://coquette-heart-text-40.pages.dev/symbol/cute-bunny-rabbit-face/)
- [SYM 2632](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2632/)
- [SYM 1F642 200D 2195 FE0F](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1f642-200d-2195-fe0f/)
- [SYM 2621](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-2621/)
- [SYM 260F](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-260f/)
- [SYM 1D4A0](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d4a0/)
- [SYM 1F479](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1f479/)
- [BRACKETS](https://anime-sparkle-text-92.pages.dev/ja/brackets/)
- [SYM 2614](https://zen-spacing-text-68.pages.dev/symbol/sym-2614/)
- [SYM 1D41F](https://zen-typography-hub-86.pages.dev/symbol/sym-1d41f/)
- [SYM 26ED](https://minimal-star-symbols-22.pages.dev/symbol/sym-26ed/)
- [SUPER SHY BLUSHING KAOMOJI](https://vintage-runic-symbols-53.pages.dev/symbol/super-shy-blushing-kaomoji/)
- [BORDERS DIVIDERS](https://coquette-aesthetic-symbols-14.pages.dev/es/borders-dividers/)
- [SYM 1F62F](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f62f/)
- [SYM 2630](https://baroque-crown-unicode-60.pages.dev/symbol/sym-2630/)
- [SYM 1F61C](https://clean-line-emojis-77.pages.dev/symbol/sym-1f61c/)
- [SYM 1F60D](https://neon-hacker-text-25.pages.dev/symbol/sym-1f60d/)
- [SYM 26BA](https://vintage-runic-symbols-53.pages.dev/symbol/sym-26ba/)
- [SYM 1FA75](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1fa75/)
- [SYM 1D421](https://soft-pastel-unicode-78.pages.dev/symbol/sym-1d421/)
- [SYM 1D404](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d404/)
- [SYM 1D469](https://soft-pastel-unicode-78.pages.dev/symbol/sym-1d469/)
- [SYM 1D417](https://soft-pastel-unicode-78.pages.dev/symbol/sym-1d417/)
- [HIGH VOLTAGE LIGHTNING](https://gothic-bio-fonts-98.pages.dev/symbol/high-voltage-lightning/)
- [FREEFIRE NAMES](https://soft-pastel-unicode-78.pages.dev/es/freefire-names/)
- [SYM 26E6](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-26e6/)
- [SYM 26AC](https://cyber-clan-tags-38.pages.dev/symbol/sym-26ac/)
- [SYM 1F610](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f610/)
- [SYM 1D462](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d462/)
- [SYM 1F633](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1f633/)
- [NATURE FLOWERS](https://vintage-runic-symbols-53.pages.dev/ja/nature-flowers/)
- [SYM 1F62D](https://baroque-crown-unicode-60.pages.dev/symbol/sym-1f62d/)
- [BRACKETS](https://angelic-bow-symbols-76.pages.dev/brackets/)
- [NATURE FLOWERS](https://coquette-aesthetic-symbols-14.pages.dev/nature-flowers/)
- [SYM 263A FE0F](https://ballet-core-symbols-11.pages.dev/symbol/sym-263a-fe0f/)
- [SYM 2663](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2663/)
- [SYM 2638](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2638/)
- [SYM 2657](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2657/)
- [SYM 1F92F](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f92f/)
- [SYM 2612](https://pastel-moe-kaomoji-91.pages.dev/symbol/sym-2612/)
- [FOUR POINT STAR SPARKLE](https://coquette-aesthetic-symbols-14.pages.dev/symbol/four-point-star-sparkle/)
- [TRENDING](https://angelic-soft-text-59.pages.dev/pt/trending/)
- [SYM 1D453](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d453/)
- [SYM 1D45F](https://cyber-clan-tags-69.pages.dev/symbol/sym-1d45f/)
- [SYM 1D49C](https://vintage-runic-symbols-53.pages.dev/symbol/sym-1d49c/)
- [STARS](https://coquette-aesthetic-symbols-14.pages.dev/stars/)
- [SYM 26F1](https://kawaii-kaomoji-hub-89.pages.dev/symbol/sym-26f1/)
- [SYM 1F611](https://coquette-aesthetic-symbols-14.pages.dev/symbol/sym-1f611/)
- [SYM 2637](https://vintage-library-rune-80.pages.dev/symbol/sym-2637/)
- [SYM 267D](https://sleek-line-symbols-51.pages.dev/symbol/sym-267d/)
- [SYM 2746](https://pastel-moe-kaomoji-91.pages.dev/symbol/sym-2746/)
- [SYM 1F61E](https://scholarly-script-hub-43.pages.dev/symbol/sym-1f61e/)
- [SYM 1D433](https://pastel-moe-kaomoji-91.pages.dev/symbol/sym-1d433/)
- [SYM 1D45C](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d45c/)
- [SYM 1F921](https://angelic-soft-text-59.pages.dev/symbol/sym-1f921/)
- [SYM 26EB](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-26eb/)
- [LEFT MATHEMATICAL WHITE SQUARE BRACKET](https://gothic-bio-fonts-13.pages.dev/symbol/left-mathematical-white-square-bracket/)
- [SYM 1D4A2](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-1d4a2/)
- [INSTAGRAM BIO](https://ballet-core-symbols-11.pages.dev/instagram-bio/)
- [STARS](https://baroque-curse-text-56.pages.dev/ja/stars/)
- [SYM 1F611](https://balletcore-unicode-67.pages.dev/symbol/sym-1f611/)
- [SYM 2620 FE0F](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2620-fe0f/)
- [SYM 2670](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-2670/)
- [GOTHIC OBSIDIAN SKULL CREST](https://gothic-bio-fonts-13.pages.dev/symbol/gothic-obsidian-skull-crest/)
- [KAOMOJI](https://poetic-scroll-fonts-91.pages.dev/pt/kaomoji/)
- [SYM 1F480](https://baroque-curse-text-56.pages.dev/symbol/sym-1f480/)
- [SYM 1D46A](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d46a/)
- [SYM 2646](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-2646/)
- [SYM 1D485](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d485/)
- [SYM 26CD](https://balletcore-unicode-67.pages.dev/symbol/sym-26cd/)
- [DAGGER BLADE](https://vintage-runic-symbols-53.pages.dev/symbol/dagger-blade/)
- [SYM 1F605](https://gothic-bio-fonts-13.pages.dev/symbol/sym-1f605/)
- [SYM 1D457](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d457/)
- [SYM 2663](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-2663/)
- [ZODIAC CELESTIAL](https://gothic-bio-fonts-13.pages.dev/ru/zodiac-celestial/)
- [SYM 273D](https://baroque-curse-text-56.pages.dev/symbol/sym-273d/)
- [SYM 1D484](https://gothic-bio-fonts-69.pages.dev/symbol/sym-1d484/)
- [SYM 1D47A](https://cyber-clan-tags-38.pages.dev/symbol/sym-1d47a/)
- [SYM 1F638](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1f638/)
- [SYM 1F636 200D 1F32B FE0F](https://mecha-glitch-fonts-82.pages.dev/symbol/sym-1f636-200d-1f32b-fe0f/)
- [TRENDING](https://zen-spacing-text-68.pages.dev/ja/trending/)
- [SYM 1D489](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-1d489/)
- [SYM 2611](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-2611/)
- [SYM 1D401](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-1d401/)
- [SYM 2722](https://angelic-soft-text-59.pages.dev/symbol/sym-2722/)
- [SYM 26ED](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-26ed/)
- [STARS](https://coquette-aesthetic-symbols-14.pages.dev/ru/stars/)
- [SYM 26B1](https://vintage-runic-symbols-53.pages.dev/symbol/sym-26b1/)
- [SYM 1D417](https://soft-pink-fonts-41.pages.dev/symbol/sym-1d417/)
- [SYM 1D43A](https://pastel-moe-kaomoji-91.pages.dev/symbol/sym-1d43a/)
- [PINWHEEL STAR](https://gothic-bio-fonts-13.pages.dev/symbol/pinwheel-star/)
- [SYM 1F916](https://balletcore-unicode-67.pages.dev/symbol/sym-1f916/)
- [SYM 1D40C](https://nordic-minimal-fonts-67.pages.dev/symbol/sym-1d40c/)
- [ARROWS LINES](https://coquette-aesthetic-symbols-14.pages.dev/vi/arrows-lines/)
- [SYM 1F63C](https://gothic-bio-fonts-61.pages.dev/symbol/sym-1f63c/)
- [SYM 26D1](https://pastel-chibi-emotes-23.pages.dev/symbol/sym-26d1/)
