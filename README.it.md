# 🔴 Hacking Cheatsheets

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
  <b>📚 Una raccolta completa di cheatsheets di penetration testing per professionisti della sicurezza</b>
</p>

<p align="center">
  <a href="#-cheatsheets">Cheatsheets</a> •
  <a href="#-per-iniziare">Per iniziare</a> •
  <a href="#-contribuire">Contribuire</a> •
  <a href="#-licenza">Licenza</a>
</p>

> ⚠️ Disclaimer: Molti link ai file all'interno della repo ancora non funzionano, poiché le cheatsheets sono in fase di traduzione. Torna presto per la versione completa!

---

## 🎯 Info sul progetto

**Hacking Cheatsheets** è una raccolta curata di guide di riferimento rapido per strumenti di penetration testing ed ethical hacking. Ogni cheatsheet fornisce:

- ✅ **Spiegazioni chiare** sulle funzionalità degli strumenti
- ✅ **Sintassi dei comandi** con esempi pratici
- ✅ **Scenari reali** e casi d'uso
- ✅ **Tabelle di riferimento rapido** per una consultazione veloce
- ✅ **Consigli & best practice** da pentester esperti

---

## 🎯 Metodologia d'attacco (Kill Chain)

> **NOVITÀ!** Guida completa passo-passo per il penetration testing basata sul framework MITRE ATT&CK.

| Fase | Descrizione | Guida |
|-------|-------------|-------|
| **1. Accesso iniziale** | Exploit, phishing, credenziali | [📄 Visualizza](./Attack-Methodology/translations/01-Initial-Access.it.md) |
| **2. Enumerazione** | Discovery di sistemi e reti | [📄 Visualizza](./Attack-Methodology/translations/02-Enumeration.it.md) |
| **3. Privilege Escalation** | Ottenere accesso root/SYSTEM | [📄 Visualizza](./Attack-Methodology/translations/03-Privilege-Escalation.it.md) |
| **4. Movimento Laterale** | Muoversi attraverso la rete | [📄 Visualizza](./Attack-Methodology/translations/04-Lateral-Movement.it.md) |
| **5. Persistenza** | Mantenere l'accesso | [📄 Visualizza](./Attack-Methodology/translations/05-Persistence.it.md) |
| **6. Defense Evasion** | Bypassare AV/EDR/AMSI | [📄 Visualizza](./Attack-Methodology/translations/06-Defense-Evasion.it.md) |
| **7. Azioni sugli obiettivi** | Data exfiltration e impatto | [📄 Visualizza](./Attack-Methodology/translations/07-Actions-Objectives.it.md) |

👉 **[Panoramica completa della Kill Chain](./Attack-Methodology/translations/README.it.md)**

---

## 🛡️ Blue Team (Defensive Security)

> **NOVITÀ!** Guide complete sulla sicurezza defensiva per analisti SOC e incident responder.

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Incident Response** | Ciclo di vita IR, contenimento, procedure | [📄 Visualizza](./Blue-Team/translations/Incident-Response.it.md) |
| **Log Analysis** | Analisi log Windows/Linux & Event ID | [📄 Visualizza](./Blue-Team/translations/Log-Analysis.it.md) |
| **SIEM Detection** | Query Splunk/ELK & dashboard | [📄 Visualizza](./Blue-Team/translations/SIEM-Detection.it.md) |
| **Threat Hunting** | Tecniche di hunting proattivo | [📄 Visualizza](./Blue-Team/translations/Threat-Hunting.it.md) |
| **Hardening** | Checklist di hardening Windows/Linux | [📄 Visualizza](./Blue-Team/translations/Hardening.it.md) |
| **Sigma Rules** | Regole di detection platform-agnostic | [📄 Visualizza](./Blue-Team/translations/Sigma-Rules.it.md) |
| **YARA Rules** | Pattern di detection malware & IOC | [📄 Visualizza](./Blue-Team/translations/YARA-Rules.it.md) |

👉 **[Panoramica completa Blue Team](./Blue-Team/translations/README.it.md)**

---

## ☁️ Cloud Security

> **NOVITÀ!** Guide al cloud pentesting per AWS, Azure e GCP.

| Provider | Descrizione | Guida |
|----------|-------------|-------|
| **AWS** | S3, IAM, Lambda, EC2, IMDS | [📄 Visualizza](./Cloud-Security/translations/AWS-Pentesting.it.md) |
| **Azure** | Azure AD, Blob Storage, VM, Key Vault | [📄 Visualizza](./Cloud-Security/translations/Azure-Pentesting.it.md) |
| **GCP** | GCS, IAM, Compute, Cloud Functions | [📄 Visualizza](./Cloud-Security/translations/GCP-Pentesting.it.md) |

👉 **[Panoramica completa sulla Cloud Security](./Cloud-Security/translations/README.it.md)**

---

## 📱 Mobile Security

> **NOVITÀ!** Guide al mobile app pentesting per Android e iOS.

| Piattaforma | Descrizione | Guida |
|----------|-------------|-------|
| **Android** | Analisi APK, Frida, bypass root detection | [📄 Visualizza](./Mobile-Security/translations/Android-Pentesting.it.md) |
| **iOS** | Analisi IPA, jailbreak, Objection, keychain | [📄 Visualizza](./Mobile-Security/translations/iOS-Pentesting.it.md) |

👉 **[Panoramica completa sulla Mobile Security](./Mobile-Security/translations/README.it.md)**

---

## 🐳 Container Security

> **NOVITÀ!** Guide al pentesting di Docker & Kubernetes.

| Piattaforma | Descrizione | Guida |
|----------|-------------|-------|
| **Docker** | Container escape, analisi immagini, daemon exploitation | [📄 Visualizza](./Container-Security/translations/Docker-Pentesting.it.md) |
| **Kubernetes** | Bypass RBAC, pod escape, secrets extraction | [📄 Visualizza](./Container-Security/translations/Kubernetes-Pentesting.it.md) |

👉 **[Panoramica completa sulla Container Security](./Container-Security/translations/README.it.md)**

---

## 🎭 Social Engineering

> **NOVITÀ!** Tecniche di social engineering, campagne di phishing e guide al pretexting.

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Phishing** | Email phishing, GoPhish, Evilginx2, vishing, smishing | [📄 Visualizza](./Social-Engineering/translations/Phishing.it.md) |
| **Pretexting** | Personas, scenari, manipolazione psicologica | [📄 Visualizza](./Social-Engineering/translations/Pretexting.it.md) |

👉 **[Panoramica completa sulla Social Engineering](./Social-Engineering/translations/README.it.md)**

---

## 📝 Reporting Templates

> **NOVITÀ!** Template di report professionali per pentester e bug bounty hunter.

| Template | Descrizione | Guida |
|----------|-------------|-------|
| **Report di Pentest** | Struttura completa di un report di penetration test | [📄 Visualizza](./Reporting/translations/Pentest-Report-Template.it.md) |
| **Report di Bug Bounty** | Template per l'invio di report su HackerOne/Bugcrowd | [📄 Visualizza](./Reporting/translations/Bug-Bounty-Report-Template.it.md) |
| **Executive Summary** | Riassunto non tecnico per il management | [📄 Visualizza](./Reporting/translations/Executive-Summary-Template.it.md) |

---

## 🔍 OSINT (Open Source Intelligence)

> **NOVITÀ!** Metodologia OSINT completa e guide agli strumenti.

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **People Search** | Trovare individui online, ricerca telefono/indirizzo | [📄 Visualizza](./OSINT/translations/People-Search.it.md) |
| **OSINT di Email** | Scoperta email,controllo dei data breach, verifica | [📄 Visualizza](./OSINT/translations/Email-OSINT.it.md) |
| **Social Media** | Ricerca username, OSINT specifico per piattaforma | [📄 Visualizza](./OSINT/translations/Social-Media-OSINT.it.md) |
| **Dominio & IP** | WHOIS, DNS, sottodomini, ricognizione IP | [📄 Visualizza](./OSINT/translations/Domain-IP-OSINT.it.md) |
| **OSINT di Immagini** | Reverse image search, metadati EXIF | [📄 Visualizza](./OSINT/translations/Image-OSINT.it.md) |

👉 **[Panoramica completa sulla OSINT](./OSINT/translations/README.it.md)**

---

## 🌐 Pentesting di rete

> **NOVITÀ!** Guide complete al penetration testing di rete.

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Scansione delle porte** | Nmap, Masscan, RustScan | [📄 Visualizza](./Network-Pentesting/translations/Port-Scanning.it.md) |
| **Enumerazione della rete** | SMB, SNMP, NFS, LDAP, DNS | [📄 Visualizza](./Network-Pentesting/translations/Network-Enumeration.it.md) |
| **Attacchi MITM** | ARP spoofing, DNS spoofing, SSL strip | [📄 Visualizza](./Network-Pentesting/translations/MITM-Attacks.it.md) |
| **Exploitation di Servizi** | FTP, SSH, SMB, RDP, database | [📄 Visualizza](./Network-Pentesting/translations/Service-Exploitation.it.md) |

👉 **[Panoramica completa sul Pentesting di rete](./Network-Pentesting/translations/README.it.md)**

---

## 🏁 Cheatsheets su CTF

> **NOVITÀ!** Guide complete per competizioni CTF su HackTheBox, TryHackMe, PicoCTF.

| Categoria | Descrizione | Guida |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, Auth bypass | [📄 Visualizza](./CTF/translations/Web-CTF.it.md) |
| **Crypto** | RSA, AES, hash, encoding, XOR | [📄 Visualizza](./CTF/translations/Crypto-CTF.it.md) |
| **Ingegneria inversa** | Ghidra, IDA, GDB, patching | [📄 Visualizza](./CTF/translations/Reverse-Engineering-CTF.it.md) |
| **Forensics** | Steganografia, memoria, disco, PCAP | [📄 Visualizza](./CTF/translations/Forensics-CTF.it.md) |
| **Pwn/Binary** | Buffer overflow, ROP, shellcode | [📄 Visualizza](./CTF/translations/Pwn-CTF.it.md) |

👉 **[Panoramica completa sui CTF](./CTF/translations/README.it.md)**

---

## 📡 Hacking di dispositivi IoT

> **NOVITÀ!** Hacking di dispositivi IoT, firmware analysis e guide all'hardware hacking.

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Analisi del Firmware** | Binwalk, estrazione, RE, secrets | [📄 Visualizza](./IoT-Hacking/translations/Firmware-Analysis.it.md) |
| **Hardware Hacking** | UART, JTAG, SPI, I2C, porte di debug | [📄 Visualizza](./IoT-Hacking/translations/Hardware-Hacking.it.md) |

👉 **[Panoramica completa sull'IoT Hacking](./IoT-Hacking/translations/README.it.md)**

---

## 📖 Cheatsheets Programmate

### 🔴 Framework di Exploitation

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Metasploit** | Il framework di penetration testing più usato al mondo | [📄 Visualizza](./Metasploit/translations/README.it.md) |
| **Meterpreter** | Payload avanzato di post-exploitation | [📄 Visualizza](./Metasploit/translations/Meterpreter.it.md) |
| **Mimikatz** | Strumento per l'estrazione di credenziali Windows | [📄 Visualizza](./Mimikatz/translations/README.it.md) |
| **PowerShell** | Scripting Windows per il pentesting | [📄 Visualizza](./PowerShell/translations/README.it.md) |
| **Comandi di Linux** | Linux & Bash per il pentesting | [📄 Visualizza](./Linux-Commands/translations/README.it.md) |

### 🔍 Reconnaissance e scansione

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Nmap** | Network discovery e security auditing | [📄 Visualizza](./Nmap/translations/README.it.md) |
| **Gobuster** | Brute-forcing di Directory/DNS/VHost | [📄 Visualizza](./Gobuster/translations/README.it.md) |
| **Nikto** | SCanner di web server | [📄 Visualizza](./Nikto/translations/README.it.md) |

### 🌐 Testing delle Applicazioni Web

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **SQLMap** | Strumento di automazione per la SQL injection | [📄 Visualizza](./SQLMap/translations/README.it.md) |
| **Burp Suite** | Piattaforma di test per la sicurezza delle applicazioni web application | [📄 Visualizza](./Burp-Suite/translations/README.it.md) |
| **OWASP ZAP** | Scanner di sicurezza web app gratuito | [📄 Visualizza](./OWASP-ZAP/translations/README.it.md) |

### 🔓 Password Cracking

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Hydra** | Veloce network login cracker | [📄 Visualizza](./Hydra/translations/README.it.md) |
| **John the Ripper** | Leggendario password cracker | [📄 Visualizza](./John-The-Ripper/translations/README.it.md) |
| **Hashcat** | Il GPU password cracker più veloce al mondo | [📄 Visualizza](./Hashcat/translations/README.it.md) |

### 📡 Analisi di rete

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Wireshark** | Analizzatore di protocolli di rete | [📄 Visualizza](./Wireshark/translations/README.it.md) |
| **tcpdump** | Analizzatore di pacchetti da riga di comando | [📄 Visualizza](./tcpdump/translations/README.it.md) |

### 🐛 Bug Bounty

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **⭐ BB Methodology** | Guida completa al bug bounty hunting | [📄 Visualizza](./Bug-Bounty-Methodology/translations/README.it.md) |
| **Nuclei** | Vulnerability scanner basato su template | [📄 Visualizza](./Nuclei/translations/README.it.md) |
| **ffuf** | Veloce web fuzzer | [📄 Visualizza](./ffuf/translations/README.it.md) |
| **Subfinder** | Discovery di sottodomini | [📄 Visualizza](./Subfinder/translations/README.it.md) |
| **httpx** | HTTP probe & toolkit | [📄 Visualizza](./httpx/translations/README.it.md) |
| **Amass** | Mapping approfondito della superficie di attacco | [📄 Visualizza](./Amass/translations/README.it.md) |
| **GAU** | Prendi tutti gli URL dagli archivi | [📄 Visualizza](./GAU/translations/README.it.md) |
| **Katana** | Web crawler di nuova generazione | [📄 Visualizza](./Katana/translations/README.it.md) |
| **Arjun** | Discovery di parametri nascosti | [📄 Visualizza](./Arjun/translations/README.it.md) |
| **Dalfox** | Scanner di vulnerabilità XSS | [📄 Visualizza](./Dalfox/translations/README.it.md) |

### 💉 Collezione di Payloads

| Vulnerabilità | Descrizione | Cheatsheet |
|---------------|-------------|------------|
| **XSS** | Payload per Cross-Site Scripting | [📄 Visualizza](./Payloads/translations/XSS.it.md) |
| **SQLi** | Payload per SQL Injection | [📄 Visualizza](./Payloads/translations/SQLi.it.md) |
| **LFI** | Payload per Local File Inclusion | [📄 Visualizza](./Payloads/translations/LFI.it.md) |
| **SSTI** | Server-Side Template Injection | [📄 Visualizza](./Payloads/translations/SSTI.it.md) |
| **Command Injection** | Payload per OS command injection | [📄 Visualizza](./Payloads/translations/Command-Injection.it.md) |
| **NoSQL Injection** | Payload per MongoDB, CouchDB, Redis | [📄 Visualizza](./Payloads/translations/NoSQL-Injection.it.md) |
| **Deserialization** | Payload per Java, PHP, Python, .NET | [📄 Visualizza](./Payloads/translations/Deserialization.it.md) |
| **Attacchi WebSocket** | CSWSH, injection, hijacking | [📄 Visualizza](./Payloads/translations/WebSocket-Attacks.it.md) |
| **GraphQL Injection** | Introspection, IDOR, injection | [📄 Visualizza](./Payloads/translations/GraphQL-Injection.it.md) |

### 🔴 Vulnerabilità Web

| Vulnerabilità | Descrizione | Cheatsheet |
|---------------|-------------|------------|
| **Sicurezza delle API** | Guida al testing di REST/GraphQL/JWT | [📄 Visualizza](./API-Security/translations/README.it.md) |
| **IDOR** | Insecure Direct Object Reference | [📄 Visualizza](./IDOR/translations/README.it.md) |
| **SSRF** | Server-Side Request Forgery | [📄 Visualizza](./SSRF/translations/README.it.md) |
| **XXE** | XML External Entity Injection | [📄 Visualizza](./XXE/translations/README.it.md) |
| **Race Conditions** | Attacchi di timing & concorrenza | [📄 Visualizza](./Race-Conditions/translations/README.it.md) |
| **Auth Bypass** | Tecniche di bypass dell'autenticazione | [📄 Visualizza](./Auth-Bypass/translations/README.it.md) |
| **CORS** | Misconfigurazioni Cross-Origin | [📄 Visualizza](./CORS/translations/README.it.md) |
| **Open Redirect** | Vulnerabilità redirect URL | [📄 Visualizza](./Open-Redirect/translations/README.it.md) |

### 🛡️ Tecniche Avanzate di Attacco

| Argomento | Descrizione | Cheatsheet |
|-------|-------------|------------|
| **WAF Bypass** | Discovery IP di origine & evasione WAF | [📄 Visualizza](./WAF-Bypass/translations/README.it.md) |
| **Cloudflare Bypass** | Trovare l'IP di origine dietro Cloudflare | [📄 Visualizza](./Cloudflare-Bypass/translations/README.it.md) |
| **Subdomain Takeover** | Exploitation di CNAME dangling | [📄 Visualizza](./Subdomain-Takeover/translations/README.it.md) |
| **Cache Poisoning** | Web cache poisoning & deception | [📄 Visualizza](./Cache-Poisoning/translations/README.it.md) |
| **HTTP Smuggling** | Request smuggling (CL.TE/TE.CL) | [📄 Visualizza](./HTTP-Request-Smuggling/translations/README.it.md) |
| **Prototype Pollution** | Attacchi JavaScript prototype | [📄 Visualizza](./Prototype-Pollution/translations/README.it.md) |

### 🔎 Dorking & OSINT

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Google Dorking** | Tecniche di ricerca avanzata su Google | [📄 Visualizza](./Google-Dorking/translations/README.it.md) |
| **Shodan** | Motore di ricerca per IoT e dispositivi | [📄 Visualizza](./Shodan/translations/README.it.md) |
| **GitHub Dorking** | Ricerca di informazioni sensibili nei repository | [📄 Visualizza](./GitHub-Dorking/translations/README.it.md) |

### 🔝 Privilege Escalation

| Argomento | Descrizione | Cheatsheet |
|-------|-------------|------------|
| **Linux PrivEsc** | Tecniche di privilege escalation su Linux | [📄 Visualizza](./Linux-PrivEsc/translations/README.it.md) |
| **Windows PrivEsc** | Tecniche di privilege escalation su Windows | [📄 Visualizza](./Windows-PrivEsc/translations/README.it.md) |

### 🔬 Digital Forensics

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Volatility** | Framework di memory forensics | [📄 Visualizza](./Volatility/translations/README.it.md) |
| **Autopsy** | Piattaforma di digital forensics (GUI) | [📄 Visualizza](./Autopsy/translations/README.it.md) |
| **ExifTool** | Estrazione e analisi di metadati | [📄 Visualizza](./ExifTool/translations/README.it.md) |
| **Binwalk** | Analisi ed estrazione di firmware | [📄 Visualizza](./Binwalk/translations/README.it.md) |

### 🔄 Ingegneria Inversa

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Ghidra** | Suite di reverse engineering della NSA | [📄 Visualizza](./Ghidra/translations/README.it.md) |
| **GDB** | Debugger GNU (debugging Linux) | [📄 Visualizza](./GDB/translations/README.it.md) |
| **x64dbg** | Debugger Windows x64/x32 | [📄 Visualizza](./x64dbg/translations/README.it.md) |

### 📶 WiFi Hacking

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **Aircrack-ng** | Suite per WiFi hacking (WPA/WPA2) | [📄 Visualizza](./Aircrack-ng/translations/README.it.md) |
| **Wifite** | Auditor WiFi automatizzato | [📄 Visualizza](./Wifite/translations/README.it.md) |
| **Bettercap** | Framework per attacchi di rete (MITM/WiFi) | [📄 Visualizza](./Bettercap/translations/README.it.md) |

### 🏢 Active Directory

| Strumento | Descrizione | Cheatsheet |
|------|-------------|------------|
| **⭐ AD Methodology** | Guida all'attacco passo-passo | [📄 Visualizza](./AD-Attack-Methodology/translations/README.it.md) |
| **BloodHound** | Visualizzazione dei percorsi di attacco AD | [📄 Visualizza](./BloodHound/translations/README.it.md) |
| **Impacket** | Toolkit di attacco AD in Python | [📄 Visualizza](./Impacket/translations/README.it.md) |
| **CrackMapExec** | Il coltellino svizzero per AD | [📄 Visualizza](./CrackMapExec/translations/README.it.md) |
| **Rubeus** | Toolkit per l'abuso di Kerberos | [📄 Visualizza](./Rubeus/translations/README.it.md) |
| **PowerView** | Enumerazione AD via PowerShell | [📄 Visualizza](./PowerView/translations/README.it.md) |
| **Responder** | Avvelenamento LLMNR/NBT-NS | [📄 Visualizza](./Responder/translations/README.it.md) |
| **Evil-WinRM** | Shell WinRM per pentester | [📄 Visualizza](./Evil-WinRM/translations/README.it.md) |
| **Kerbrute** | Enum utenti e spraying Kerberos | [📄 Visualizza](./Kerbrute/translations/README.it.md) |

### 📚 Risorse

| Risorsa | Descrizione | Cheatsheet |
|----------|-------------|------------|
| **Wordlists** | Guida di riferimento completa alle wordlist | [📄 Visualizza](./Wordlists/translations/README.it.md) |
| **Kali Linux Tools** | Oltre 600 strumenti divisi per categoria | [📄 Visualizza](./Kali-Linux-Tools/translations/README.it.md) |

---

## 🚀 Per iniziare

### Clonare il Repository

```bash
git clone [https://github.com/Ilias1988/Hacking-Cheatsheets.git](https://github.com/Ilias1988/Hacking-Cheatsheets.git)
cd Hacking-Cheatsheets

```

### Sfogliare le Cheatsheets

Naviga in qualsiasi cartella degli strumenti e apri il file README.md:

```bash
# Visualizza la cheatsheet di Metasploit
cat Metasploit/README.md

# Oppure aprila nel tuo editor preferito
code Metasploit/

```

### Accesso Offline

Tutte le cheatsheet sono in formato Markdown, il che le rende:

* 📱 **Mobile-friendly** - Leggibili su qualsiasi dispositivo
* 🔌 **Accessibili offline** - Nessuna connessione internet richiesta
* 🖨️ **Stampabili** - Per creare copie fisiche
* 🔍 **Ricercabili** - Usa grep o lo strumento di ricerca del tuo editor

---

## 📂 Struttura del Repoitory

```
Hacking-Cheatsheets/
│
├── README.md                # Versione originale (ENG) - Indice principale
├── README.it.md             # Questo file - Indice principale
├── LICENSE                  # Licenza MIT
├── CONTRIBUTING.md          # Linee guida per contribuire
├── CONTRIBUTING.it.md       # Linee guida per contribuire (Italiano)
├── .gitignore               # Regole Git ignore
│
├── Metasploit/              # Metasploit Framework
│   ├── README.md            # Guida completa msfconsole
│   └── Meterpreter.md       # Cheatsheet Meterpreter
│
├── Nmap/                    # Network Scanner
│   └── README.md            # Guida completa Nmap
│
├── Gobuster/                # Enumerazione Directory/DNS/VHost
│   └── README.md            # Guida completa Gobuster
│
├── Nikto/                   # Web Server Scanner
│   └── README.md            # Guida completa Nikto
│
├── SQLMap/                  # Tool SQL Injection
│   └── README.md            # Guida completa SQLMap
│
├── Burp-Suite/              # Web Application Testing
│   └── README.md            # Guida completa Burp Suite
│
├── OWASP-ZAP/               # OWASP Zed Attack Proxy
│   └── README.md            # Guida completa ZAP
│
├── Hydra/                   # Network Login Cracker
│   └── README.md            # Guida completa Hydra
│
├── John-The-Ripper/         # Password Cracker
│   └── README.md            # Guida completa John
│
├── Hashcat/                 # GPU Password Cracker
│   └── README.md            # Guida completa Hashcat
│
├── Wireshark/               # Network Protocol Analyzer
│   └── README.md            # Guida completa Wireshark
│
├── tcpdump/                 # Analizzatore di pacchetti da riga di comando
│   └── README.md            # Guida completa tcpdump
│
├── Nuclei/                  # Bug Bounty Scanner
│   └── README.md            # Guida completa Nuclei
│
├── ffuf/                    # Web Fuzzer
│   └── README.md            # Guida completa ffuf
│
├── Subfinder/               # Discovery Sottodomini
│   └── README.md            # Guida completa Subfinder
│
├── httpx/                   # HTTP Probe & Toolkit
│   └── README.md            # Guida completa httpx
│
├── Google-Dorking/          # Google Search Hacking
│   └── README.md            # Guida completa Google Dorking
│
├── Shodan/                  # Motore di ricerca IoT
│   └── README.md            # Guida completa Shodan
│
├── GitHub-Dorking/          # Secret Hunting
│   └── README.md            # Guida completa GitHub Dorking
│
└── ...
```

---

## 🤝 Contribuire

I contributi sono benvenuti! Si prega di leggere le nostre [Linee guida per i contributi](CONTRIBUTING.it.md) prima di inviare una pull request.

### Modi per contribuire

* 📝 **Aggiungi nuove cheatsheet** per strumenti non ancora trattati
* 🔧 **Migliora le cheatsheet esistenti** con esempi migliori
* 🐛 **Segnala problemi** o suggerisci miglioramenti
* 🌐 **Traduci** le cheatsheet in altre lingue
* ⭐ **Metti una stella a questo repo** per mostrare il tuo supporto!

---

## ⚠️ Legal Disclaimer

> **IMPORTANTE:** Queste cheatsheet sono destinate esclusivamente a **scopo educativo** e per **test di sicurezza autorizzati**.
> * ✅ Utilizzale su sistemi di tua proprietà
> * ✅ Utilizzale con esplicito permesso scritto
> * ✅ Utilizzale in incarichi legali di penetration testing
> * ❌ Non utilizzarle mai per accessi non autorizzati
> * ❌ Non utilizzarle mai per scopi malevoli
> 
> 
> **L'accesso non autorizzato ai sistemi informatici è illegale.** Gli autori non sono responsabili per qualsiasi uso improprio di queste informazioni.

---

## 📜 Licenza

Questo progetto è rilasciato sotto la Licenza MIT - consulta il file [LICENZA](LICENSE.it.md) per i dettagli.

---

## 🌟 Mostra il tuo supporto

Se trovi utili queste cheatsheet, considera di:

* ⭐ **Mettere una stella** a questo repository
* 🍴 **Fare il fork** per contribuire
* 📢 **Condividerlo** con altri professionisti della sicurezza
* 💬 **Fornire feedback** per miglioramenti

---

## 📬 Contatti

* **GitHub Issues** - Per bug report e richieste di funzionalità
* **Pull Requests** - Per i contributi

---

<p align="center">
<b>Buon Hacking! 🔴</b>




<i>Ricorda: Hackera responsabilmente, hackera eticamente!</i>
</p>

---

<p align="center">
Fatto con il ❤️ per la community della cybersicurezza
</p>
