# 📝 Sigma Rules Cheatsheet

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** è un formato di regole generico e aperto per sistemi SIEM, che consente la creazione di regole di rilevamento indipendenti dalla piattaforma.

---

## 📖 Struttura di una Sigma Rule

```yaml
title: Titolo della regola
id: uuid-univoco
status: experimental|test|stable
description: Descrizione di cosa rileva la regola
author: Il tuo nome
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
    - Attività amministrativa legittima
level: high|medium|low|critical
tags:
    - attack.execution
    - attack.t1059
```

---

## 🎯 Esempi di Regole di Rilevamento

### Esecuzione di Mimikatz
```yaml
title: Mimikatz Command Line Detection
id: a642964e-5618-4fa6-bf2b-a98c6dba52df
status: stable
description: Rileva l'esecuzione di Mimikatz tramite argomenti della command line
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

### PowerShell Encoded Command
```yaml
title: PowerShell Encoded Command Execution
id: fb603c23-7a2c-4c89-b8e7-2d6fa33c9b2e
status: stable
description: Rileva l'esecuzione di PowerShell con comando codificato
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

### Creazione di Scheduled Task sospetto
```yaml
title: Suspicious Scheduled Task Created
id: 7a4e6b3c-9f1c-4d8a-b2e5-8c3f6a9d2e1b
status: stable
description: Rileva la creazione di scheduled task tramite command line
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

### Accesso alla memoria di LSASS
```yaml
title: LSASS Memory Access
id: 32d0d3e2-e58d-4d41-926b-18b520b2b32d
status: stable
description: Rileva processi che accedono alla memoria di LSASS
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

### Esecuzione di PSExec
```yaml
title: PsExec Service Installation
id: c2f8c4e2-8e5a-4c9b-b3d6-7f2a1c5e8d9b
status: stable
description: Rileva l'installazione del servizio PsExec
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

### Named Pipes di Cobalt Strike
```yaml
title: Cobalt Strike Named Pipe
id: d5af9c28-4e94-4e6a-b4e7-8c3d2f1e5a9b
status: stable
description: Rileva i named pipe di default di Cobalt Strike
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

### RDP da Internet
```yaml
title: RDP Connection from External IP
id: 8b3f9a1c-5d2e-4f8b-a7c6-2e1d9b4a5c3d
status: stable
description: Rileva connessioni RDP da indirizzi IP esterni
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

---

## 🔧 Modificatori di campo

| Modificatore | Descrizione | Esempio |
|--------------|-------------|---------|
| `contains` | Il valore contiene la stringa | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Il valore inizia con | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Il valore termina con | `Image\|endswith: '.exe'` |
| `all` | Tutti i valori devono corrispondere | `contains\|all:` |
| `base64` | Valore codificato in base64 | `CommandLine\|base64:` |
| `re` | Espressione regolare | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Sigma Tools

### sigmac (Converti per SIEM)
```bash
# Converti per Splunk
sigmac -t splunk rule.yml

# Converti per Elastic
sigmac -t es-qs rule.yml

# Converti per QRadar
sigmac -t qradar rule.yml

# Converti più regole
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Valida la regola
sigma check rule.yml

# Converte la regola
sigma convert -t splunk rule.yml
```

---

## 📊 Riferimento rapido

### Log Sources

| Categoria | Prodotto | Descrizione |
|-----------|----------|-------------|
| `process_creation` | windows | Eventi di creazione processo |
| `network_connection` | windows | Connessioni di rete |
| `file_event` | windows | Operazioni su file |
| `registry_event` | windows | Modifiche al registro |
| `process_access` | windows | Accesso ai processi |
| `pipe_created` | windows | Creazione di named pipe |

### Tag comuni (MITRE ATT&CK)

| Tag | Descrizione |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 Risorse

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Sigma Rules Repository](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Cheatsheet correlati

- [Rilevamento SIEM](./SIEM-Detection.it.md)
- [YARA Rules](./YARA-Rules.it.md)
- [Analisi Log](./Log-Analysis.it.md)

---

**Precedente:** [← Threat Hunting](./Threat-Hunting.it.md)

**Successivo:** [YARA Rules →](./YARA-Rules.it.md)
