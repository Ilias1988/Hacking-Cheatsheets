# 📝 Spickzettel zu Sigma-Regeln

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](./Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** ist ein allgemeines, offenes Signaturformat für SIEM-Systeme. Es ermöglicht plattformunabhängige Regeln zur Erkennung verdächtiger Ereignisse.

---

> **Versions- und Beispielhinweise:** `sigmac` ist der ältere Konverter; das aktuelle Sigma-Ökosystem verwendet `sigma-cli` auf Basis von pySigma. Backend und Verarbeitungspipeline müssen zum Ziel-SIEM passen. Die mit `|` getrennten Status- und Schweregradwerte im Strukturbeispiel zeigen Alternativen; in einer echten Regel ist jeweils ein gültiger Wert zu wählen. [Sigma-Dokumentation](https://sigmahq.io/docs/guide/about).

> **Erkennungsgrenzen:** `status: stable` ist hier Bestandteil des Originalbeispiels und keine Validierungszusage. Der interne IP-Filter der RDP-Regel deckt mit `172.16.` nicht das vollständige private Netz `172.16.0.0/12` und auch nicht IPv6 ab. Regeln und Ausnahmen sind vor einem Einsatz an die eigene Umgebung anzupassen.

## 📖 Aufbau einer Sigma-Regel

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

## 🎯 Beispiele für Erkennungsregeln

### Mimikatz-Ausführung
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

### Kodierter PowerShell-Befehl
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

### Verdächtige Erstellung einer geplanten Aufgabe
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

### Zugriff auf den LSASS-Speicher
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

### PSExec-Ausführung
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

### Named Pipes von Cobalt Strike
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

### RDP aus dem Internet
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

---

## 🔧 Feldmodifikatoren

| Modifikator | Beschreibung | Beispiel |
|----------|-------------|---------|
| `contains` | Wert enthält die Zeichenfolge | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Wert beginnt mit | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Wert endet mit | `Image\|endswith: '.exe'` |
| `all` | Alle Werte müssen zutreffen | `contains\|all:` |
| `base64` | Base64-kodierter Wert | `CommandLine\|base64:` |
| `re` | Regulärer Ausdruck | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Sigma-Werkzeuge

### sigmac (in SIEM-Abfragen umwandeln)
```bash
# In Splunk-Abfragen umwandeln
sigmac -t splunk rule.yml

# In Elastic-Abfragen umwandeln
sigmac -t es-qs rule.yml

# In QRadar-Abfragen umwandeln
sigmac -t qradar rule.yml

# Mehrere Regeln umwandeln
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Regel prüfen
sigma check rule.yml

# Regel umwandeln
sigma convert -t splunk rule.yml
```

---

## 📊 Kurzreferenz

### Protokollquellen

| Kategorie | Produkt | Beschreibung |
|----------|---------|-------------|
| `process_creation` | windows | Ereignisse zur Prozesserstellung |
| `network_connection` | windows | Netzwerkverbindungen |
| `file_event` | windows | Dateioperationen |
| `registry_event` | windows | Registry-Änderungen |
| `process_access` | windows | Ereignisse zu Prozesszugriffen |
| `pipe_created` | windows | Erstellung von Named Pipes |

### Häufige Tags (MITRE ATT&CK)

| Tag | Beschreibung |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 Weiterführende Quellen

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Sigma-Regelrepository](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Verwandte Spickzettel

- [SIEM-Erkennung](./SIEM-Detection.de.md)
- [YARA-Regeln](./YARA-Rules.de.md)
- [Protokollanalyse](./Log-Analysis.de.md)

---

**Zurück:** [← Threat Hunting (proaktive Bedrohungssuche)](./Threat-Hunting.de.md)

**Weiter:** [YARA-Regeln →](./YARA-Rules.de.md)
