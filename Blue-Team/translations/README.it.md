# 🛡️ Blue Team Cheatsheets

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

## 🎯 Cos'è il Blue Team?

**Blue Team** indica il team di sicurezza difensiva responsabile di:
- 🔍 **Rilevare** minacce e attacchi
- 🛡️ **Difendere** sistemi e reti
- 🚨 **Rispondere** agli incidenti di sicurezza
- 📊 **Monitorare** attività sospette
- 🔒 **Rafforzare** l'infrastruttura
---

## 📊 Ciclo di Difesa

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        CICLO DI DIFESA                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. PREPARA   │───▶│ 2. RILEVA    │───▶│ 3. ANALIZZA           │        │
│   │  (Hardening) │    │  (Monitor)   │    │  (Investigate)       │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. RISPONDI           │          │
│          │                               │  (Containment)       │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. MIGLIORA  │◀───│ 6. IMPARA    │◀───│ 5. RECUPERA          │        │
│   │              │    │  (Report)    │    │  (Restore)           │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guide Blue Team

### 🚨 Incident Response & Rilevamento

| Argomento | Descrizione | Guida |
|-----------|-------------|-------|
| **Incident Response** | Procedure IR, playbook, containment | [📄 Visualizza](./Incident-Response.it.md) |
| **Log Analysis** | Analisi log Windows/Linux & Event ID | [📄 Visualizza](./Log-Analysis.it.md) |
| **SIEM Detection** | Query Splunk/ELK & dashboard | [📄 Visualizza](./SIEM-Detection.it.md) |
| **Threat Hunting** | Tecniche di hunting proattivo | [📄 Visualizza](./Threat-Hunting.it.md) |

### 🔬 Analisi & Difesa

| Argomento | Descrizione | Guida |
|-----------|-------------|-------|
| **Malware Analysis** | Tecniche di analisi statica/dinamica | [📄 Visualizza](./Malware-Analysis.it.md) |
| **Network Defense** | IDS/IPS, regole firewall, sicurezza rete | [📄 Visualizza](./Network-Defense.it.md) |
| **Hardening** | Checklist hardening Windows/Linux | [📄 Visualizza](./Hardening.it.md) |

### 📝 Detection Rules

| Argomento | Descrizione | Guida |
|-----------|-------------|-------|
| **Sigma Rules** | Regole di detection agnostiche | [📄 Visualizza](./Sigma-Rules.it.md) |
| **YARA Rules** | Detection malware & IOC | [📄 Visualizza](./YARA-Rules.it.md) |

---

## 🔗 Red Team vs Blue Team

| Aspetto | Red Team (Offensivo) | Blue Team (Difensivo) |
|---------|----------------------|-----------------------|
| **Fine** | Trovare vulnerabilità | Proteggere i sistemi |
| **Approccio** | Simulazione di attacco | Difesa & rilevamento |
| **Tools** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Output** | Vulnerabilità trovate | Incidenti rilevati |
| **Focus** | Intrusione | Difesa |

---

## 📚 Riferimento rapido

### Event ID Windows essenziali

| Event ID | Descrizione |
|----------|-------------|
| 4624 | Logon riuscito |
| 4625 | Logon fallito |
| 4648 | Logon con credenziali esplicite |
| 4672 | Logon admin (privilegi speciali) |
| 4688 | Creazione processo |
| 4698 | Creazione task programmata |
| 4720 | Creazione account utente |
| 7045 | Installazione servizio |

### Log Linux essenziali

| Log | Percorso |
|-----|----------|
| Auth | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Messages | `/var/log/messages` |
| Secure | `/var/log/secure` |
| Audit | `/var/log/audit/audit.log` |

---

## 🛠️ Strumenti Blue Team essenziali

| Categoria | Strumenti |
|-----------|-----------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Forensics** | Volatility, Autopsy, FTK |
| **Malware** | VirusTotal, Any.run, Cuckoo |
| **Network** | Wireshark, tcpdump, Zeek |

---

## 📚 Risorse

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Sigma Rules Repository](https://github.com/SigmaHQ/sigma)
- [YARA Rules Repository](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Difendi in anticipo!</b><br>
  <i>La miglior difesa è quella proattiva!</i>
</p>
