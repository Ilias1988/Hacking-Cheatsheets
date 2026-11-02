# 📝 Sigma 规则速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

> **译注：** `sigmac` 属于已停止积极维护的旧工具链，参见 [SigmaHQ 旧工具说明](https://github.com/SigmaHQ/legacy-sigmatools)。现代 `sigma-cli` 转换需要对应后端，通常还需要处理管道以映射字段，参见 [官方 CLI 文档](https://github.com/SigmaHQ/sigma-cli)。原文的结构模板使用 `experimental|test|stable` 等值表示备选项，并非可直接部署的完整规则。RDP 示例只排除了 `172.16.`，没有覆盖整个 `172.16.0.0/12` 私有地址范围。

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** 是一种用于安全信息和事件管理（SIEM）系统的通用开放签名格式，可编写与平台无关的检测规则。

---

## 📖 Sigma 规则结构

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

## 🎯 检测规则示例

### Mimikatz 执行
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

### PowerShell 编码命令
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

### 创建可疑计划任务
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

### LSASS 内存访问
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

### PSExec 执行
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

### Cobalt Strike 命名管道
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

### 来自互联网的 RDP 连接
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

## 🔧 字段修饰符

| 修饰符 | 说明 | 示例 |
|----------|-------------|---------|
| `contains` | 值包含指定字符串 | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | 值以指定内容开头 | `Image\|startswith: 'C:\Temp'` |
| `endswith` | 值以指定内容结尾 | `Image\|endswith: '.exe'` |
| `all` | 所有值均须匹配 | `contains\|all:` |
| `base64` | 经过 Base64 编码的值 | `CommandLine\|base64:` |
| `re` | 正则表达式 | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Sigma 工具

### sigmac（转换为 SIEM 查询）
```bash
# 转换为 Splunk 查询
sigmac -t splunk rule.yml

# 转换为 Elastic 查询
sigmac -t es-qs rule.yml

# 转换为 QRadar 查询
sigmac -t qradar rule.yml

# 批量转换多条规则
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# 验证规则
sigma check rule.yml

# 转换规则
sigma convert -t splunk rule.yml
```

---

## 📊 快速参考

### 日志来源

| 类别 | 产品 | 说明 |
|----------|---------|-------------|
| `process_creation` | windows | 进程创建事件 |
| `network_connection` | windows | 网络连接 |
| `file_event` | windows | 文件操作 |
| `registry_event` | windows | 注册表修改 |
| `process_access` | windows | 进程访问事件 |
| `pipe_created` | windows | 创建命名管道 |

### 常用标签（MITRE ATT&CK）

| Tag | 说明 |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 参考资源

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Sigma 规则库](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 相关速查表

- [SIEM 检测](./SIEM-Detection.zh-CN.md)
- [YARA 规则](./YARA-Rules.zh-CN.md)
- [日志分析](./Log-Analysis.zh-CN.md)

---

**上一篇:** [← 威胁狩猎](./Threat-Hunting.zh-CN.md)

**下一篇:** [YARA 规则 →](./YARA-Rules.zh-CN.md)
