# 📝 Sigma-regler – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Sigma-Rules.md) • [🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** er et generelt og åpent signaturformat for SIEM-systemer. Formatet gjør det mulig å skrive plattformuavhengige deteksjonsregler.

---

## 📖 Oppbygning av en Sigma-regel

```yaml
title: Rule Title
id: unique-uuid
status: experimental|test|stable
description: Description of what this rule detects
author: Your Name
date: 2024/01/15
modified: 2024/01/20
references:
    - https://attack.mitre.org/techniques/T1059/
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        FieldName: 'value'
    condition: selection
falsepositives:
    - Legitimate admin activity
level: high|medium|low|critical
tags:
    - attack.execution
    - attack.t1059
```

---

## 🎯 Eksempler på deteksjonsregler

### Kjøring av Mimikatz
```yaml
title: Mimikatz Command Line Detection
id: a642964e-5618-4fa6-bf2b-a98c6dba52df
status: stable
description: Detects Mimikatz execution via command line arguments
author: Security Team
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        CommandLine|contains:
            - 'sekurlsa::'
            - 'kerberos::'
            - 'lsadump::'
            - 'crypto::'
            - 'dpapi::'
            - 'privilege::debug'
    condition: selection
level: critical
tags:
    - attack.credential_access
    - attack.t1003
```

### Kodet PowerShell-kommando
```yaml
title: PowerShell Encoded Command Execution
id: fb603c23-7a2c-4c89-b8e7-2d6fa33c9b2e
status: stable
description: Detects execution of PowerShell with encoded command
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        Image|endswith: '\powershell.exe'
        CommandLine|contains:
            - '-enc'
            - '-EncodedCommand'
            - '-e '
            - 'FromBase64String'
    condition: selection
level: high
tags:
    - attack.execution
    - attack.t1059.001
```

### Opprettelse av mistenkelig planlagt oppgave
```yaml
title: Suspicious Scheduled Task Created
id: 7a4e6b3c-9f1c-4d8a-b2e5-8c3f6a9d2e1b
status: stable
description: Detects creation of scheduled tasks via command line
logsource:
    category: process_creation
    product: windows
detection:
    selection:
        CommandLine|contains|all:
            - 'schtasks'
            - '/create'
    suspicious_paths:
        CommandLine|contains:
            - '\AppData\'
            - '\Temp\'
            - '\ProgramData\'
    condition: selection and suspicious_paths
level: medium
tags:
    - attack.persistence
    - attack.t1053.005
```

### Tilgang til LSASS-minne
```yaml
title: LSASS Memory Access
id: 32d0d3e2-e58d-4d41-926b-18b520b2b32d
status: stable
description: Detects process accessing LSASS memory
logsource:
    category: process_access
    product: windows
detection:
    selection:
        TargetImage|endswith: '\lsass.exe'
        GrantedAccess|contains:
            - '0x1010'
            - '0x1410'
            - '0x1438'
            - '0x143a'
    filter:
        SourceImage|endswith:
            - '\wmiprvse.exe'
            - '\svchost.exe'
    condition: selection and not filter
level: critical
tags:
    - attack.credential_access
    - attack.t1003.001
```

### Kjøring av PSExec
```yaml
title: PsExec Service Installation
id: c2f8c4e2-8e5a-4c9b-b3d6-7f2a1c5e8d9b
status: stable
description: Detects PsExec service installation
logsource:
    product: windows
    service: system
detection:
    selection:
        EventID: 7045
        ServiceName: 'PSEXESVC'
    condition: selection
level: high
tags:
    - attack.lateral_movement
    - attack.t1021.002
```

### Navngitte rør (named pipes) fra Cobalt Strike
```yaml
title: Cobalt Strike Named Pipe
id: d5af9c28-4e94-4e6a-b4e7-8c3d2f1e5a9b
status: stable
description: Detects Cobalt Strike default named pipes
logsource:
    category: pipe_created
    product: windows
detection:
    selection:
        PipeName|startswith:
            - '\MSSE-'
            - '\msagent_'
            - '\postex_'
            - '\status_'
    condition: selection
level: critical
tags:
    - attack.command_and_control
    - attack.t1071
```

### RDP fra Internett
```yaml
title: RDP Connection from External IP
id: 8b3f9a1c-5d2e-4f8b-a7c6-2e1d9b4a5c3d
status: stable
description: Detects RDP connections from external IP addresses
logsource:
    product: windows
    service: security
detection:
    selection:
        EventID: 4624
        LogonType: 10
    filter_internal:
        IpAddress|startswith:
            - '10.'
            - '172.16.'
            - '192.168.'
            - '127.'
    condition: selection and not filter_internal
level: high
tags:
    - attack.initial_access
    - attack.t1133
```

> **Presisering:** Eksempelets filter dekker bare `172.16.` innenfor det private området `172.16.0.0/12`, ikke hele området til og med `172.31.255.255`. Det behandler heller ikke alle andre interne eller lokale adresseområder. Treff er derfor utgangspunkt for undersøkelser.

---

## 🔧 Feltmodifikatorer

| Modifikator | Beskrivelse | Eksempel |
|----------|-------------|---------|
| `contains` | Verdien inneholder en streng | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Verdien begynner med | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Verdien slutter med | `Image\|endswith: '.exe'` |
| `all` | Alle verdier må samsvare | `contains\|all:` |
| `base64` | Base64-kodet verdi | `CommandLine\|base64:` |
| `re` | Regulært uttrykk | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Sigma-verktøy

### sigmac (konvertering til SIEM)
```bash
# Konverter til Splunk
sigmac -t splunk rule.yml

# Konverter til Elastic
sigmac -t es-qs rule.yml

# Konverter til QRadar
sigmac -t qradar rule.yml

# Konverter flere regler
sigmac -t splunk -r rules/ -o output/
```

> **Versjonsmerknad:** `sigmac` tilhører den eldre verktøykjeden og vedlikeholdes ikke aktivt. Nye arbeidsflyter bruker pySigma og Sigma CLI. De opprinnelige kommandoene er beholdt som historiske eksempler. [SigmaHQs vedlikeholdsstatus](https://github.com/SigmaHQ/legacy-sigmatools).

### sigma-cli
```bash
# Valider regelen
sigma check rule.yml

# Konverter regelen
sigma convert -t splunk rule.yml
```

> **Presisering:** Konvertering forutsetter at riktig backend og eventuelt en behandlingspipeline er tilgjengelig for loggkilden. Feltnavn og deteksjon må tilpasses miljøet. [Sigma CLI-dokumentasjon](https://github.com/SigmaHQ/sigma-cli).

---

## 📊 Hurtigreferanse

### Loggkilder

| Kategori | Produkt | Beskrivelse |
|----------|---------|-------------|
| `process_creation` | windows | Hendelser for prosessopprettelse |
| `network_connection` | windows | Nettverkstilkoblinger |
| `file_event` | windows | Filoperasjoner |
| `registry_event` | windows | Registerendringer |
| `process_access` | windows | Hendelser for tilgang til prosesser |
| `pipe_created` | windows | Opprettelse av navngitte rør |

### Vanlige tagger (MITRE ATT&CK)

| Tagg | Beskrivelse |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 Ressurser

- [Sigma på GitHub](https://github.com/SigmaHQ/sigma)
- [Sigma-regelsamling](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Relaterte hurtigreferanser

- [SIEM-deteksjon](SIEM-Detection.nb.md)
- [YARA-regler](YARA-Rules.nb.md)
- [Logganalyse](Log-Analysis.nb.md)

---

**Forrige:** [← Trusseljakt](Threat-Hunting.nb.md)

**Neste:** [YARA-regler →](YARA-Rules.nb.md)
