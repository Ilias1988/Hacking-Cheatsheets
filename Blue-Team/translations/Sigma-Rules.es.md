# 📝 Guía rápida de reglas Sigma

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

> **Aclaración de versión:** `sigmac` está obsoleto; el ecosistema actual utiliza `sigma-cli` con pySigma y sus backends. Se conservan los comandos originales. Véase [SigmaHQ](https://github.com/SigmaHQ/sigma/wiki). El filtro IP del ejemplo RDP contempla `172.16.`, no todo el rango privado `172.16.0.0/12`. Las reglas requieren validación y ajuste de falsos positivos.


```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** es un formato genérico y abierto de firmas para sistemas SIEM que permite definir reglas de detección independientes de la plataforma.

---

## 📖 Estructura de una regla Sigma

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

## 🎯 Ejemplos de reglas de detección

### Ejecución de Mimikatz
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

### Comando codificado de PowerShell
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

### Creación de tareas programadas sospechosas
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

### Acceso a la memoria de LSASS
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

### Ejecución de PSExec
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

### Canalizaciones con nombre de Cobalt Strike
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

### RDP desde Internet
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

| Modificador | Descripción | Ejemplo |
|----------|-------------|---------|
| `contains` | El valor contiene la cadena | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | El valor comienza por | `Image\|startswith: 'C:\Temp'` |
| `endswith` | El valor termina en | `Image\|endswith: '.exe'` |
| `all` | Deben coincidir todos los valores | `contains\|all:` |
| `base64` | Valor codificado en Base64 | `CommandLine\|base64:` |
| `re` | Expresión regular | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Herramientas de Sigma

### sigmac (Convertir a SIEM)
```bash
# Convertir a Splunk
sigmac -t splunk rule.yml

# Convertir a Elastic
sigmac -t es-qs rule.yml

# Convertir a QRadar
sigmac -t qradar rule.yml

# Convertir varias reglas
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Validar la regla
sigma check rule.yml

# Convertir la regla
sigma convert -t splunk rule.yml
```

---

## 📊 Referencia rápida

### Fuentes de registros

| Categoría | Producto | Descripción |
|----------|---------|-------------|
| `process_creation` | windows | Eventos de creación de procesos |
| `network_connection` | windows | Conexiones de red |
| `file_event` | windows | Operaciones de archivos |
| `registry_event` | windows | Modificaciones del Registro |
| `process_access` | windows | Eventos de acceso a procesos |
| `pipe_created` | windows | Creación de canalizaciones con nombre |

### Etiquetas habituales (MITRE ATT&CK)

| Etiqueta | Descripción |
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
- [Repositorio de reglas Sigma](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Guías rápidas relacionadas

- [Detección con SIEM](./SIEM-Detection.es.md)
- [Reglas YARA](./YARA-Rules.es.md)
- [Análisis de registros](./Log-Analysis.es.md)

---

**Anterior:** [← Búsqueda proactiva de amenazas](./Threat-Hunting.es.md)

**Siguiente:** [Reglas YARA →](./YARA-Rules.es.md)
