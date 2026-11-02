# 📝 Guia de referência de regras Sigma

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** é um formato de assinaturas genérico e aberto para sistemas SIEM, que permite criar regras de deteção independentes da plataforma.

> **Notas de versão e precisão:** Os exemplos preservam os valores literais YAML, incluindo título, descrição e autor. Os valores alternativos separados por `|` no primeiro modelo são opções explicativas, não uma regra pronta para validação. Os exemplos com `sigmac` pertencem à ferramenta antiga; o fluxo documentado utiliza [Sigma CLI e pySigma](https://sigmahq.io/docs/guide/getting-started.html), com backend e pipeline adequados ao SIEM. As regras são exemplos didáticos e precisam de afinação. No filtro de RDP, `172.16.` cobre apenas parte de `172.16.0.0/12` e não representa toda a rede privada nem trata IPv6.

---

## 📖 Estrutura de uma regra Sigma

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

## 🎯 Exemplos de regras de deteção

### Execução de Mimikatz
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

### Comando PowerShell codificado
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

### Criação de tarefa agendada suspeita
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

### Acesso à memória do LSASS
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

### Execução de PSExec
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

### Named pipes do Cobalt Strike (canais de comunicação nomeados)
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

### RDP a partir da Internet
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

## 🔧 Modificadores de campos

| Modificador | Descrição | Exemplo |
|----------|-------------|---------|
| `contains` | O valor contém a cadeia de caracteres | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | O valor começa por | `Image\|startswith: 'C:\Temp'` |
| `endswith` | O valor termina em | `Image\|endswith: '.exe'` |
| `all` | Todos os valores têm de corresponder | `contains\|all:` |
| `base64` | Valor codificado em Base64 | `CommandLine\|base64:` |
| `re` | Expressão regular | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Ferramentas Sigma

### sigmac (conversão para SIEM)
```bash
# Converter para Splunk
sigmac -t splunk rule.yml

# Converter para Elastic
sigmac -t es-qs rule.yml

# Converter para QRadar
sigmac -t qradar rule.yml

# Converter várias regras
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Validar a regra
sigma check rule.yml

# Converter a regra
sigma convert -t splunk rule.yml
```

---

## 📊 Referência rápida

### Fontes de registos

| Categoria | Produto | Descrição |
|----------|---------|-------------|
| `process_creation` | windows | Eventos de criação de processos |
| `network_connection` | windows | Ligações de rede |
| `file_event` | windows | Operações sobre ficheiros |
| `registry_event` | windows | Modificações do registo |
| `process_access` | windows | Eventos de acesso a processos |
| `pipe_created` | windows | Criação de named pipes |

### Tags comuns (MITRE ATT&CK)

| Tag | Descrição |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 Recursos

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Repositório de regras Sigma](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Guias de referência relacionados

- [Deteção com SIEM](SIEM-Detection.pt.md)
- [Regras YARA](YARA-Rules.pt.md)
- [Análise de registos](Log-Analysis.pt.md)

---

**Anterior:** [← Threat hunting (procura proativa de ameaças)](Threat-Hunting.pt.md)

**Seguinte:** [Regras YARA →](YARA-Rules.pt.md)
