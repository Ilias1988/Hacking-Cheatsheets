# Hacking Cheatsheets

> **Last verified:** 2026-09-17  
> **Checked against:** Repository structure, automated validators, and the sources named in each verified guide

[![Documentation quality](https://github.com/Ilias1988/Hacking-Cheatsheets/actions/workflows/docs-quality.yml/badge.svg)](https://github.com/Ilias1988/Hacking-Cheatsheets/actions/workflows/docs-quality.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Language: English](https://img.shields.io/badge/Language-English-blue.svg)](README.md)
[![Italian index](https://img.shields.io/badge/Italian-Index-008C45.svg)](README.it.md)

A verification-first field reference for penetration testing, web application
security, Active Directory, cloud, networking, OSINT, defensive analysis, CTFs,
and professional reporting.

This repository is built for fast lookup during authorized labs and security
assessments. It is not a substitute for scope, judgment, local `--help` output,
or the official documentation for the exact version in use.

## Trust Model

Every canonical guide has an explicit status:

| Label | Meaning |
|---|---|
| ✅ **Verified** | The complete guide was checked on the stated date against named primary or maintained upstream sources. |
| 🟡 **Review pending** | Historical content is retained, but version-sensitive commands and claims still require source verification. |
| 🗃️ **Legacy** | The tool or workflow is archived/retired and kept only for older environments. |

Current baseline: **30 verified**, **118 review-pending**, and **0 unclassified**
canonical documents. Generate fresh figures with:

```bash
python scripts/content_health.py . --require-status
```

Read the [verification policy](./docs/VERIFICATION.md) before treating a command
as current. A working link is not proof that a technical claim is correct.

## Start Here

| Goal | Recommended guide | Status |
|---|---|---|
| Plan and run an engagement | [Professional Pentesting Workflow](./Professional-Pentesting/README.md) | ✅ |
| Define safe operating boundaries | [Safe and Authorized Use](./docs/SAFE_USE.md) | ✅ |
| Test a web application or API | [API Security](./API-Security/README.md) · [Web Authentication](./Web-Authentication/README.md) | ✅ |
| Assess Active Directory | [AD attack overview](./AD-Attack-Methodology/README.md) | 🟡 |
| Analyze identity attack paths | [BloodHound CE](./BloodHound/README.md) | ✅ |
| Build findings and deliverables | [Reporting Guide](./Reporting/README.md) | ✅ |
| Contribute or refresh content | [Maintenance Guide](./docs/MAINTENANCE.md) | ✅ |

Suggested assessment loop:

```text
Confirm scope → Map the attack surface → Form a hypothesis
→ Use the safest validation → Capture reproducible evidence
→ Explain business impact → Recommend a fix → Clean up → Retest
```

## Source-Verified Guides

### Professional Practice and Web Security

| Guide | Coverage |
|---|---|
| [Professional Pentesting](./Professional-Pentesting/README.md) | Pre-engagement, evidence, severity, cleanup, and retesting |
| [API Security](./API-Security/README.md) | REST, GraphQL, authorization, resource limits, gateways, and business flows |
| [Web Authentication](./Web-Authentication/README.md) | OAuth 2.0, OIDC, SAML, JWT, WebAuthn, and sessions |
| [File Upload](./File-Upload/README.md) | Validation, storage, processing, access control, and safe proof |
| [Path Traversal](./Path-Traversal/README.md) | Discovery, controlled verification, evidence, and remediation |
| [Business Logic](./Business-Logic/README.md) | State transitions, replay, concurrency, roles, and abuse cases |
| [Payloads Index](./Payloads/README.md) | Safe payload handling and links to focused libraries |
| [Reporting](./Reporting/README.md) | Finding quality, CVSS v4, remediation, and retest outcomes |

### Active Directory and Privilege Escalation

| Guide | Coverage |
|---|---|
| [NetExec](./NetExec/README.md) | Current `nxc` installation, enumeration, evidence, and CME migration |
| [BloodHound CE](./BloodHound/README.md) | BH-CLI installation, collectors, ingest, analysis, and data handling |
| [AD CS and Certipy](./AD-Attack-Methodology/AD-CS-Certipy.md) | CA/template review, controlled ESC1 validation, and remediation |
| [Kerberos Delegation](./AD-Attack-Methodology/Kerberos-Delegation.md) | Unconstrained, constrained, and resource-based delegation |
| [NTLM Relay and Coercion](./AD-Attack-Methodology/NTLM-Relay-and-Coercion.md) | Preconditions, safe workflow, evidence, and hardening |
| [LinPEAS](./LinPEAS/README.md) | Controlled Linux enumeration and result triage |
| [WinPEAS](./WinPEAS/README.md) | Controlled Windows enumeration and result triage |

### Defensive Security and OSINT

| Guide | Coverage |
|---|---|
| [Malware Analysis](./Blue-Team/Malware-Analysis.md) | Isolated triage, static/dynamic analysis, behavior, and reporting |
| [Network Defense](./Blue-Team/Network-Defense.md) | Segmentation, telemetry, detection validation, and response |
| [Social Media OSINT](./OSINT/Social-Media-OSINT.md) | Current tools, platform limits, evidence, and privacy handling |

### Reconnaissance and Scanning

| Guide | Smoke-tested version | Coverage |
|---|---|---|
| [Nuclei](./Nuclei/README.md) | `v3.11.1` | Reviewed templates, output formats, rate controls, and validation |
| [httpx](./httpx/README.md) | `v1.12.0` | Probes, match/filter, output, screenshots, TLS, and safe concurrency |
| [Subfinder](./Subfinder/README.md) | `v2.16.0` | Sources, output, active resolution, provider keys, and rate limits |
| [ffuf](./ffuf/README.md) | `v2.3.0` | Content, vhosts, request data, filters, recursion, rate controls, and evidence |
| [Nmap](./Nmap/README.md) | `v7.991` | Discovery, TCP/UDP, services, NSE, timing, output, scope, and evidence |
| [SQLMap](./SQLMap/README.md) | `1.10.9.12#dev` | Controlled detection, request scoping, evidence, minimal enumeration, and data safety |

## Complete Catalog

The catalog below includes useful historical material. Open each guide and check
its status banner before use.

<details>
<summary><strong>Methodologies, platforms, and defensive security</strong></summary>

| Area | Guides |
|---|---|
| Methodology | [Attack Methodology](./Attack-Methodology/README.md) 🟡 · [AD Attack Methodology](./AD-Attack-Methodology/README.md) 🟡 · [Bug Bounty Methodology](./Bug-Bounty-Methodology/README.md) 🟡 |
| Blue Team | [Overview](./Blue-Team/README.md) 🟡 · [Incident Response](./Blue-Team/Incident-Response.md) 🟡 · [Log Analysis](./Blue-Team/Log-Analysis.md) 🟡 · [SIEM](./Blue-Team/SIEM-Detection.md) 🟡 · [Threat Hunting](./Blue-Team/Threat-Hunting.md) 🟡 · [Hardening](./Blue-Team/Hardening.md) 🟡 · [Sigma](./Blue-Team/Sigma-Rules.md) 🟡 · [YARA](./Blue-Team/YARA-Rules.md) 🟡 |
| Cloud | [Overview](./Cloud-Security/README.md) 🟡 · [AWS](./Cloud-Security/AWS-Pentesting.md) 🟡 · [Azure/Entra](./Cloud-Security/Azure-Pentesting.md) 🟡 · [GCP](./Cloud-Security/GCP-Pentesting.md) 🟡 |
| Containers | [Overview](./Container-Security/README.md) 🟡 · [Docker](./Container-Security/Docker-Pentesting.md) 🟡 · [Kubernetes](./Container-Security/Kubernetes-Pentesting.md) 🟡 |
| Mobile | [Overview](./Mobile-Security/README.md) 🟡 · [Android](./Mobile-Security/Android-Pentesting.md) 🟡 · [iOS](./Mobile-Security/iOS-Pentesting.md) 🟡 |
| Network | [Overview](./Network-Pentesting/README.md) 🟡 · [Port Scanning](./Network-Pentesting/Port-Scanning.md) 🟡 · [Enumeration](./Network-Pentesting/Network-Enumeration.md) 🟡 · [MITM](./Network-Pentesting/MITM-Attacks.md) 🟡 · [Service Exploitation](./Network-Pentesting/Service-Exploitation.md) 🟡 |
| OSINT | [Overview](./OSINT/README.md) 🟡 · [People](./OSINT/People-Search.md) 🟡 · [Email](./OSINT/Email-OSINT.md) 🟡 · [Domain/IP](./OSINT/Domain-IP-OSINT.md) 🟡 · [Images](./OSINT/Image-OSINT.md) 🟡 · [Social Media](./OSINT/Social-Media-OSINT.md) ✅ |
| CTF | [Overview](./CTF/README.md) 🟡 · [Web](./CTF/Web-CTF.md) 🟡 · [Pwn](./CTF/Pwn-CTF.md) 🟡 · [Crypto](./CTF/Crypto-CTF.md) 🟡 · [Forensics](./CTF/Forensics-CTF.md) 🟡 · [Reverse Engineering](./CTF/Reverse-Engineering-CTF.md) 🟡 |
| IoT | [Overview](./IoT-Hacking/README.md) 🟡 · [Firmware](./IoT-Hacking/Firmware-Analysis.md) 🟡 · [Hardware](./IoT-Hacking/Hardware-Hacking.md) 🟡 |
| Social Engineering | [Overview](./Social-Engineering/README.md) 🟡 · [Phishing](./Social-Engineering/Phishing.md) 🟡 · [Pretexting](./Social-Engineering/Pretexting.md) 🟡 |

</details>

<details>
<summary><strong>Web application testing</strong></summary>

| Topic | Status | Topic | Status |
|---|---|---|---|
| [API Security](./API-Security/README.md) | ✅ | [Web Authentication](./Web-Authentication/README.md) | ✅ |
| [File Upload](./File-Upload/README.md) | ✅ | [Path Traversal](./Path-Traversal/README.md) | ✅ |
| [Business Logic](./Business-Logic/README.md) | ✅ | [Authentication Bypass](./Auth-Bypass/README.md) | 🟡 |
| [IDOR](./IDOR/README.md) | 🟡 | [CORS](./CORS/README.md) | 🟡 |
| [SSRF](./SSRF/README.md) | 🟡 | [XXE](./XXE/README.md) | 🟡 |
| [HTTP Request Smuggling](./HTTP-Request-Smuggling/README.md) | 🟡 | [Cache Poisoning](./Cache-Poisoning/README.md) | 🟡 |
| [Prototype Pollution](./Prototype-Pollution/README.md) | 🟡 | [Race Conditions](./Race-Conditions/README.md) | 🟡 |
| [Open Redirect](./Open-Redirect/README.md) | 🟡 | [Subdomain Takeover](./Subdomain-Takeover/README.md) | 🟡 |
| [WAF Bypass](./WAF-Bypass/README.md) | 🟡 | [Cloudflare Bypass](./Cloudflare-Bypass/README.md) | 🟡 |
| [Payload Library](./Payloads/README.md) | ✅ | [SQL Injection Payloads](./Payloads/SQLi.md) | 🟡 |
| [XSS Payloads](./Payloads/XSS.md) | 🟡 | [SSTI Payloads](./Payloads/SSTI.md) | 🟡 |
| [Command Injection](./Payloads/Command-Injection.md) | 🟡 | [Deserialization](./Payloads/Deserialization.md) | 🟡 |
| [NoSQL Injection](./Payloads/NoSQL-Injection.md) | 🟡 | [GraphQL Injection](./Payloads/GraphQL-Injection.md) | 🟡 |
| [LFI](./Payloads/LFI.md) | 🟡 | [WebSocket Attacks](./Payloads/WebSocket-Attacks.md) | 🟡 |

</details>

<details>
<summary><strong>Reconnaissance and web tooling</strong></summary>

| Recon and discovery | Web testing |
|---|---|
| [Amass](./Amass/README.md) 🟡 | [Burp Suite](./Burp-Suite/README.md) 🟡 |
| [Arjun](./Arjun/README.md) 🟡 | [Dalfox](./Dalfox/README.md) 🟡 |
| [ffuf](./ffuf/README.md) ✅ | [Nikto](./Nikto/README.md) 🟡 |
| [GAU](./GAU/README.md) 🟡 | [OWASP ZAP](./OWASP-ZAP/README.md) 🟡 |
| [Gobuster](./Gobuster/README.md) 🟡 | [SQLMap](./SQLMap/README.md) ✅ |
| [Google Dorking](./Google-Dorking/README.md) 🟡 | [Nuclei](./Nuclei/README.md) ✅ |
| [GitHub Dorking](./GitHub-Dorking/README.md) 🟡 | [Katana](./Katana/README.md) 🟡 |
| [httpx](./httpx/README.md) ✅ | [Wordlists](./Wordlists/README.md) 🟡 |
| [Nmap](./Nmap/README.md) ✅ | [Kali Linux Tools](./Kali-Linux-Tools/README.md) 🟡 |
| [Shodan](./Shodan/README.md) 🟡 | [Subfinder](./Subfinder/README.md) ✅ |

</details>

<details>
<summary><strong>Active Directory, Windows, Linux, and network tools</strong></summary>

| Area | Guides |
|---|---|
| AD and identity | [NetExec](./NetExec/README.md) ✅ · [BloodHound CE](./BloodHound/README.md) ✅ · [Impacket](./Impacket/README.md) 🟡 · [Kerbrute](./Kerbrute/README.md) 🟡 · [PowerView](./PowerView/README.md) 🟡 · [Rubeus](./Rubeus/README.md) 🟡 · [Responder](./Responder/README.md) 🟡 · [CrackMapExec](./CrackMapExec/README.md) 🗃️ |
| Windows | [Windows PrivEsc](./Windows-PrivEsc/README.md) 🟡 · [WinPEAS](./WinPEAS/README.md) ✅ · [Evil-WinRM](./Evil-WinRM/README.md) 🟡 · [Mimikatz](./Mimikatz/README.md) 🟡 · [PowerShell](./PowerShell/README.md) 🟡 |
| Linux | [Linux Commands](./Linux-Commands/README.md) 🟡 · [Linux PrivEsc](./Linux-PrivEsc/README.md) 🟡 · [LinPEAS](./LinPEAS/README.md) ✅ |
| Network and wireless | [Aircrack-ng](./Aircrack-ng/README.md) 🟡 · [Bettercap](./Bettercap/README.md) 🟡 · [Hydra](./Hydra/README.md) 🟡 · [tcpdump](./tcpdump/README.md) 🟡 · [Wireshark](./Wireshark/README.md) 🟡 · [Wifite](./Wifite/README.md) 🟡 |
| Passwords and exploitation | [Hashcat](./Hashcat/README.md) 🟡 · [John the Ripper](./John-The-Ripper/README.md) 🟡 · [Metasploit](./Metasploit/README.md) 🟡 · [Meterpreter](./Metasploit/Meterpreter.md) 🟡 |

</details>

<details>
<summary><strong>Forensics, malware, and reverse engineering</strong></summary>

| Guide | Status | Guide | Status |
|---|---|---|---|
| [Malware Analysis](./Blue-Team/Malware-Analysis.md) | ✅ | [Autopsy](./Autopsy/README.md) | 🟡 |
| [Binwalk](./Binwalk/README.md) | 🟡 | [ExifTool](./ExifTool/README.md) | 🟡 |
| [GDB](./GDB/README.md) | 🟡 | [Ghidra](./Ghidra/README.md) | 🟡 |
| [Volatility](./Volatility/README.md) | 🟡 | [x64dbg](./x64dbg/README.md) | 🟡 |

</details>

## Reporting and Project Quality

| Resource | Purpose | Status |
|---|---|---|
| [Reporting Guide](./Reporting/README.md) | Evidence standard, severity, remediation, and retest | ✅ |
| [Pentest Report Template](./Reporting/Pentest-Report-Template.md) | Full technical assessment structure | 🟡 |
| [Bug Bounty Template](./Reporting/Bug-Bounty-Report-Template.md) | Reproducible vulnerability submission | 🟡 |
| [Executive Summary Template](./Reporting/Executive-Summary-Template.md) | Management-focused summary | 🟡 |
| [2026 Repository Review](./docs/REVIEW-2026-09-17.md) | Completed work and prioritized review queue | ✅ |
| [Maintenance Guide](./docs/MAINTENANCE.md) | Source, version, translation, and lifecycle rules | ✅ |
| [Tool Testing](./docs/TOOL-TESTING.md) | CLI smoke-test levels, automation, and image cleanup | ✅ |

Quality gates run on pull requests and the default branch:

```bash
python scripts/validate_docs.py .
python scripts/content_health.py . --require-status
python -m unittest discover -s tests -v
npx markdownlint-cli2 "**/*.md"
```

GitHub Actions also checks external links. CI distinguishes structure and link
health from technical source verification; both are required for a guide to be
marked verified.

## Quick Start

```bash
git clone https://github.com/Ilias1988/Hacking-Cheatsheets.git
cd Hacking-Cheatsheets
python scripts/validate_docs.py .
python scripts/content_health.py . --require-status
```

All guides are Markdown and work offline after cloning. Search locally with:

```bash
rg -n "Kerberos|OAuth|file upload|incident response" -g "*.md"
```

## Repository Layout

```text
Hacking-Cheatsheets/
├── README.md / README.it.md       # Navigation and language indexes
├── Professional-Pentesting/      # Engagement workflow
├── Attack-Methodology/           # End-to-end methodology
├── AD-Attack-Methodology/        # Active Directory paths and controls
├── Blue-Team/                    # Detection, response, and defense
├── Cloud-Security/               # AWS, Azure/Entra, and GCP
├── Payloads/                     # Focused payload references
├── Reporting/                    # Reporting guidance and templates
├── <tool-or-topic>/README.md     # Focused cheatsheet
├── docs/                         # Trust, safety, review, and maintenance
├── scripts/                      # Dependency-free quality checks
├── tests/                        # Validator tests
└── .github/                      # CI and contribution templates
```

English documents are canonical. Italian navigation and available translations
start at [README.it.md](./README.it.md); missing translations intentionally fall
back to the verified or status-labeled English page.

## Contributing

Contributions are welcome. Before opening a pull request:

1. Read [CONTRIBUTING.md](./CONTRIBUTING.md) and the [Maintenance Guide](./docs/MAINTENANCE.md).
2. Prefer official documentation, standards, release notes, and maintained upstream repositories.
3. State whether commands were source-checked or actually lab-tested; do not blur the two.
4. Use placeholders for targets, credentials, tokens, tenants, and customer data.
5. Run the quality commands shown above.

Use the issue templates for inaccurate content and new guide proposals. Report a
repository security vulnerability through [SECURITY.md](./SECURITY.md).

## Legal and Ethical Use

Use this material only on systems you own or are explicitly authorized to test.
Follow the written scope, rate limits, stop conditions, data-handling rules, and
applicable law. Stop at the minimum evidence needed, avoid unrelated data, and
clean up every state-changing action.

See [Safe and Authorized Use](./docs/SAFE_USE.md) for the operational checklist.
The authors and contributors are not responsible for unauthorized or malicious use.

## License

Released under the [MIT License](./LICENSE).

---

Maintained by [Ilias1988](https://github.com/Ilias1988). Issues and pull requests
are the preferred channels for corrections, sources, and improvements.
