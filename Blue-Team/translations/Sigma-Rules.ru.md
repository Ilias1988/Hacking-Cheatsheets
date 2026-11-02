# Справочник по правилам Sigma

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** — универсальный открытый формат сигнатур для SIEM, позволяющий создавать правила обнаружения независимо от платформы.

---

## Структура правила Sigma

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

## Примеры правил обнаружения

### Выполнение Mimikatz
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

### Кодированная команда PowerShell
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

### Создание подозрительного задания планировщика
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

### Доступ к памяти LSASS
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

### Выполнение PSExec
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

### Именованные каналы Cobalt Strike
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

### RDP из Интернета
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

## Модификаторы полей

| Модификатор | Описание | Пример |
|----------|-------------|---------|
| `contains` | Значение содержит строку | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Значение начинается с | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Значение заканчивается на | `Image\|endswith: '.exe'` |
| `all` | Должны совпасть все значения | `contains\|all:` |
| `base64` | Значение в кодировке Base64 | `CommandLine\|base64:` |
| `re` | Регулярное выражение | `CommandLine\|re: '.*password.*'` |

---

## Инструменты Sigma

### sigmac (преобразование для SIEM)
```bash
# Преобразовать для Splunk
sigmac -t splunk rule.yml

# Преобразовать для Elastic
sigmac -t es-qs rule.yml

# Преобразовать для QRadar
sigmac -t qradar rule.yml

# Преобразовать несколько правил
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Проверить правило
sigma check rule.yml

# Преобразовать правило
sigma convert -t splunk rule.yml
```

---

## Краткий справочник

### Источники журналов

| Категория | Продукт | Описание |
|----------|---------|-------------|
| `process_creation` | windows | События создания процессов |
| `network_connection` | windows | Сетевые соединения |
| `file_event` | windows | Операции с файлами |
| `registry_event` | windows | Изменения реестра |
| `process_access` | windows | События доступа к процессам |
| `pipe_created` | windows | Создание именованных каналов |

### Типичные теги (MITRE ATT&CK)

| Тег | Описание |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## Ресурсы

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Репозиторий правил Sigma](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## Связанные справочники

- [Обнаружение с помощью SIEM](SIEM-Detection.ru.md)
- [Правила YARA](YARA-Rules.ru.md)
- [Анализ журналов](Log-Analysis.ru.md)

---

**Предыдущий раздел:** [← Поиск угроз](Threat-Hunting.ru.md)

**Далее:** [Правила YARA →](YARA-Rules.ru.md)
