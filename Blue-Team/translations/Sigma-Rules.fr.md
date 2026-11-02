# 📝 Aide-mémoire : règles Sigma

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Sigma-Rules.tr.md) • [🇬🇷 Ελληνικά](Sigma-Rules.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**Sigma** est un format ouvert et générique de signatures pour les SIEM, permettant de décrire des règles de détection indépendantes de la plateforme.

> **Précisions de traduction :** `sigmac` appartient à l’outillage historique, [déprécié par SigmaHQ](https://github.com/SigmaHQ/sigma/wiki/Converter-Tool-Sigmac-%28deprecated%29). Le [CLI fondé sur pySigma](https://github.com/SigmaHQ/sigma-cli) utilise des backends et des pipelines adaptés au schéma de journaux. Les règles et leurs métadonnées sont conservées comme exemples ; `status: stable` dans ces blocs ne certifie pas leur validation pour votre environnement. Le filtre « RDP depuis Internet » ne couvre notamment que `172.16.` et non toute la plage privée `172.16.0.0/12` ; il ne suffit donc pas à identifier les adresses publiques.

---

## 📖 Structure d'une règle Sigma

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

## 🎯 Exemples de règles de détection

### Exécution de Mimikatz
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

### Commande PowerShell encodée
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

### Création suspecte d'une tâche planifiée
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

### Accès à la mémoire de LSASS
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

### Exécution de PSExec
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

### Tubes nommés de Cobalt Strike
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

### RDP depuis Internet
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

## 🔧 Modificateurs de champs

| Modificateur | Description | Exemple |
|----------|-------------|---------|
| `contains` | La valeur contient la chaîne | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | La valeur commence par | `Image\|startswith: 'C:\Temp'` |
| `endswith` | La valeur se termine par | `Image\|endswith: '.exe'` |
| `all` | Toutes les valeurs doivent correspondre | `contains\|all:` |
| `base64` | Valeur encodée en Base64 | `CommandLine\|base64:` |
| `re` | Expression régulière | `CommandLine\|re: '.*password.*'` |

---

## 🛠️ Outils Sigma

### sigmac (conversion pour un SIEM)
```bash
# Convertir pour Splunk
sigmac -t splunk rule.yml

# Convertir pour Elastic
sigmac -t es-qs rule.yml

# Convertir pour QRadar
sigmac -t qradar rule.yml

# Convertir plusieurs règles
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Valider la règle
sigma check rule.yml

# Convertir la règle
sigma convert -t splunk rule.yml
```

---

## 📊 Référence rapide

### Sources de journaux

| Catégorie | Produit | Description |
|----------|---------|-------------|
| `process_creation` | windows | Événements de création de processus |
| `network_connection` | windows | Connexions réseau |
| `file_event` | windows | Opérations sur les fichiers |
| `registry_event` | windows | Modifications du Registre |
| `process_access` | windows | Événements d'accès aux processus |
| `pipe_created` | windows | Création d'un tube nommé |

### Étiquettes courantes (MITRE ATT&CK)

| Étiquette | Description |
|-----|-------------|
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

## 📚 Ressources

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Dépôt de règles Sigma](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

## 🔗 Aide-mémoire associés

- [Détection avec un SIEM](./SIEM-Detection.fr.md)
- [Règles YARA](./YARA-Rules.fr.md)
- [Analyse des journaux](./Log-Analysis.fr.md)

---

**Précédent:** [← Recherche proactive de menaces](./Threat-Hunting.fr.md)

**Suivant:** [Règles YARA →](./YARA-Rules.fr.md)
