<a id="-sigma-rules-cheatsheet"></a>
# 📝 مرجع قواعد Sigma

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** تنسيق عام ومفتوح لتوقيعات الكشف في أنظمة SIEM، يتيح كتابة قواعد مستقلة عن المنصة.

---

<a id="-sigma-rule-structure"></a>
## 📖 بنية قاعدة Sigma

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

<a id="-detection-rule-examples"></a>
## 🎯 أمثلة على قواعد الكشف

<a id="mimikatz-execution"></a>
### تنفيذ Mimikatz
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

<a id="powershell-encoded-command"></a>
### أمر PowerShell مرمّز
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

<a id="suspicious-scheduled-task-creation"></a>
### إنشاء مهمة مجدولة مشبوهة
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

<a id="lsass-memory-access"></a>
### الوصول إلى ذاكرة LSASS
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

<a id="psexec-execution"></a>
### تنفيذ PSExec
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

<a id="cobalt-strike-named-pipes"></a>
### الأنابيب المسماة في Cobalt Strike
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

<a id="rdp-from-internet"></a>
### اتصالات RDP من الإنترنت
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

<a id="-field-modifiers"></a>
## 🔧 معدّلات الحقول

| المعدّل | الوصف | مثال |
|----------|-------------|---------|
| `contains` | القيمة تحتوي على سلسلة نصية | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | القيمة تبدأ بـ | `Image\|startswith: 'C:\Temp'` |
| `endswith` | القيمة تنتهي بـ | `Image\|endswith: '.exe'` |
| `all` | يجب مطابقة جميع القيم | `contains\|all:` |
| `base64` | قيمة مرمّزة بـ Base64 | `CommandLine\|base64:` |
| `re` | تعبير نمطي (Regex) | `CommandLine\|re: '.*password.*'` |

---

<a id="-sigma-tools"></a>
## 🛠️ أدوات Sigma

<a id="sigmac-convert-to-siem"></a>
### sigmac (التحويل إلى صيغة SIEM)
```bash
# التحويل إلى Splunk
sigmac -t splunk rule.yml

# التحويل إلى Elastic
sigmac -t es-qs rule.yml

# التحويل إلى QRadar
sigmac -t qradar rule.yml

# تحويل مجموعة قواعد
sigmac -t splunk -r rules/ -o output/
```

<a id="sigma-cli"></a>
### sigma-cli
```bash
# التحقق من صحة القاعدة
sigma check rule.yml

# تحويل القاعدة
sigma convert -t splunk rule.yml
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="log-sources"></a>
### مصادر السجلات

| الفئة | المنتج | الوصف |
|----------|---------|-------------|
| `process_creation` | windows | أحداث إنشاء العمليات |
| `network_connection` | windows | اتصالات الشبكة |
| `file_event` | windows | عمليات الملفات |
| `registry_event` | windows | تعديلات السجل |
| `process_access` | windows | أحداث الوصول إلى العمليات |
| `pipe_created` | windows | إنشاء أنبوب مسمّى |

<a id="common-tags-mitre-attck"></a>
### وسوم شائعة (MITRE ATT&CK)

| الوسم | الوصف |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

<a id="-resources"></a>
## 📚 الموارد

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [قواعد Sigma Repository](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [الكشف باستخدام SIEM](SIEM-Detection.ar.md)
- [قواعد YARA](YARA-Rules.ar.md)
- [تحليل السجلات](Log-Analysis.ar.md)

---

**السابق:** [← صيد التهديدات](Threat-Hunting.ar.md)

**التالي:** [قواعد YARA →](YARA-Rules.ar.md)

> **توضيح للأدوات والأمثلة:** يحتفظ الدليل بأوامر `sigmac` الواردة في الأصل. يركّز توثيق Sigma على `sigma-cli` ومحولات pySigma؛ يلزم اختيار الواجهة الخلفية وخط معالجة الحقول الملائمين. القيم المفصولة بـ `|` في نموذج البنية تعرض بدائل توضيحية، وليست مجموعة قيم تُستخدم جميعها حرفيًا في قاعدة نهائية. [دليل Sigma الرسمي](https://sigmahq.io/docs/guide/getting-started.html).
