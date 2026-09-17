# 🛡️ Blue Team Cheatsheets

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

## 🎯 What is Blue Team?

**Blue Team** refers to the defensive security team responsible for:
- 🔍 **Detecting** threats and attacks
- 🛡️ **Defending** systems and networks
- 🚨 **Responding** to security incidents
- 📊 **Monitoring** for suspicious activity
- 🔒 **Hardening** infrastructure

---

## 📊 Defense Lifecycle

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        DEFENSE LIFECYCLE                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. PREPARE   │───▶│ 2. DETECT    │───▶│ 3. ANALYZE           │        │
│   │  (Hardening) │    │  (Monitor)   │    │  (Investigate)       │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. RESPOND           │          │
│          │                               │  (Containment)       │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. IMPROVE   │◀───│ 6. LEARN     │◀───│ 5. RECOVER           │        │
│   │              │    │  (Report)    │    │  (Restore)           │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Blue Team Guides

### 🚨 Incident Response & Detection

| Topic | Description | Guide |
|-------|-------------|-------|
| **Incident Response** | IR procedures, playbooks, containment | [📄 View](./Incident-Response.md) |
| **Log Analysis** | Windows/Linux log analysis & Event IDs | [📄 View](./Log-Analysis.md) |
| **SIEM Detection** | Splunk/ELK queries & dashboards | [📄 View](./SIEM-Detection.md) |
| **Threat Hunting** | Proactive hunting techniques | [📄 View](./Threat-Hunting.md) |

### 🔬 Analysis & Defense

| Topic | Description | Guide |
|-------|-------------|-------|
| **Malware Analysis** | Static/dynamic analysis techniques | [📄 View](./Malware-Analysis.md) |
| **Network Defense** | IDS/IPS, firewall rules, network security | [📄 View](./Network-Defense.md) |
| **Hardening** | Windows/Linux hardening checklists | [📄 View](./Hardening.md) |

### 📝 Detection Rules

| Topic | Description | Guide |
|-------|-------------|-------|
| **Sigma Rules** | Platform-agnostic detection rules | [📄 View](./Sigma-Rules.md) |
| **YARA Rules** | Malware & IOC detection | [📄 View](./YARA-Rules.md) |

---

## 🔗 Red Team vs Blue Team

| Aspect | Red Team (Offensive) | Blue Team (Defensive) |
|--------|---------------------|----------------------|
| **Goal** | Find vulnerabilities | Protect systems |
| **Approach** | Attack simulation | Defense & detection |
| **Tools** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Output** | Vulnerabilities found | Incidents detected |
| **Focus** | Breaking in | Keeping out |

---

## 📚 Quick Reference

### Essential Windows Event IDs

| Event ID | Description |
|----------|-------------|
| 4624 | Successful logon |
| 4625 | Failed logon |
| 4648 | Explicit credential logon |
| 4672 | Admin logon (special privileges) |
| 4688 | Process creation |
| 4698 | Scheduled task created |
| 4720 | User account created |
| 7045 | Service installed |

### Essential Linux Logs

| Log | Location |
|-----|----------|
| Auth | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Messages | `/var/log/messages` |
| Secure | `/var/log/secure` |
| Audit | `/var/log/audit/audit.log` |

---

## 🛠️ Essential Blue Team Tools

| Category | Tools |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Forensics** | Volatility, Autopsy, FTK |
| **Malware** | VirusTotal, Any.run, Cuckoo |
| **Network** | Wireshark, tcpdump, Zeek |

---

## 📚 Resources

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Sigma Rules Repository](https://github.com/SigmaHQ/sigma)
- [YARA Rules Repository](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Defend Forward!</b><br>
  <i>The best defense is a proactive one!</i>
</p>
