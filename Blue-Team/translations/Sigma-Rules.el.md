# 📝 Κανόνες ανίχνευσης Sigma

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Sigma-Rules.md) • [🇮🇹 Italiano](Sigma-Rules.it.md) • [🇹🇷 Türkçe](Sigma-Rules.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
███████╗██╗ ██████╗ ███╗   ███╗ █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
██╔════╝██║██╔════╝ ████╗ ████║██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
███████╗██║██║  ███╗██╔████╔██║███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
╚════██║██║██║   ██║██║╚██╔╝██║██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
███████║██║╚██████╔╝██║ ╚═╝ ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
╚══════╝╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

Η **Sigma** είναι ανοικτή μορφή περιγραφής κανόνων ανίχνευσης σε καταγραφές, με δυνατότητα μετατροπής για διαφορετικά συστήματα SIEM.

---

## Πριν ξεκινήσετε

Οι **κανόνες Sigma** περιγράφουν μοτίβα ανίχνευσης σε καταγραφές, με δομή **YAML** ανεξάρτητη από έναν συγκεκριμένο προμηθευτή SIEM. Για να χρησιμοποιηθούν χρειάζονται σωστή πηγή δεδομένων και μετατροπή στη γλώσσα του συστήματος προορισμού.

**Ανάγνωση της δομής:** Το `logsource` δηλώνει την πηγή συμβάντων. Το `detection` περιέχει τα κριτήρια και το `condition` τον τρόπο συνδυασμού τους. Το `falsepositives` περιγράφει νόμιμες δραστηριότητες που μπορεί να ταιριάζουν. Το `level` εκφράζει τη σοβαρότητα που αποδίδει ο συντάκτης. Τα `title`, `description` και `author` είναι μεταδεδομένα. Τα ονόματα πεδίων, τα μοτίβα, οι αγγλικές περιγραφές μέσα στα παραδείγματα YAML και οι ετικέτες διατηρούνται για αντιστοιχία με την πηγή.

Το `status: stable` που εμφανίζεται σε ένα εκπαιδευτικό παράδειγμα δεν αποδεικνύει ότι έχει αξιολογηθεί στον οργανισμό σας. Το φίλτρο αποκλείει δεδομένα, ενώ ένας τροποποιητής όπως `contains` αλλάζει τον τρόπο σύγκρισης. Η [επίσημη τεκμηρίωση Sigma](https://sigmahq.io/docs/guide/getting-started.html) εξηγεί τη σύγχρονη μετατροπή με sigma-cli, backends και pipelines.

## Περιεχόμενα

- [📖 Δομή κανόνα Sigma](#section-01)
- [🎯 Παραδείγματα κανόνων ανίχνευσης](#section-02)
- [🔧 Τροποποιητές σύγκρισης πεδίων (Modifiers)](#section-03)
- [🛠️ Εργαλεία Sigma](#section-04)
- [📊 Γρήγορη αναφορά](#section-05)
- [📚 Πηγές](#section-06)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-07)

<a id="section-01"></a>

## 📖 Δομή κανόνα Sigma

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

<a id="section-02"></a>

## 🎯 Παραδείγματα κανόνων ανίχνευσης

### Εκτέλεση Mimikatz
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

### Κωδικοποιημένη εντολή PowerShell
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

### Δημιουργία ύποπτης προγραμματισμένης εργασίας
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

### Πρόσβαση στη μνήμη LSASS
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

### Εκτέλεση PSExec
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

### Ονομασμένοι δίαυλοι επικοινωνίας του Cobalt Strike (Named Pipes)
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

### Πρόσβαση RDP από εξωτερικές διευθύνσεις

Το φίλτρο του παραδείγματος δεν καλύπτει όλα τα ιδιωτικά δίκτυα: περιλαμβάνει μόνο `172.16.` από το ευρύτερο εύρος `172.16.0.0/12`. Συνεπώς, το αποτέλεσμα δεν ταυτίζεται κατ’ ανάγκη με πρόσβαση από το διαδίκτυο.

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

<a id="section-03"></a>

## 🔧 Τροποποιητές σύγκρισης πεδίων (Modifiers)

| Τροποποιητής | Περιγραφή | Παράδειγμα |
| ---------- | ------------- | --------- |
| `contains` | Η τιμή περιέχει τη συμβολοσειρά | `CommandLine\|contains: 'mimikatz'` |
| `startswith` | Η τιμή αρχίζει με τη συμβολοσειρά | `Image\|startswith: 'C:\Temp'` |
| `endswith` | Η τιμή τελειώνει με τη συμβολοσειρά | `Image\|endswith: '.exe'` |
| `all` | Πρέπει να ταιριάζουν όλες οι τιμές | `contains\|all:` |
| `base64` | Τιμή κωδικοποιημένη σε Base64 | `CommandLine\|base64:` |
| `re` | Κανονική έκφραση (Regular Expression) | `CommandLine\|re: '.*password.*'` |

---

<a id="section-04"></a>

## 🛠️ Εργαλεία Sigma

### sigmac — παλαιότερο εργαλείο μετατροπής για SIEM

Οι εντολές sigmac διατηρούνται ως παλαιότερα παραδείγματα του πρωτοτύπου. Για νέο περιβάλλον ακολουθήστε την επίσημη τεκμηρίωση sigma-cli και τις απαιτήσεις του backend προορισμού.

```bash
# Μετατροπή σε ερώτημα Splunk
sigmac -t splunk rule.yml

# Μετατροπή για Elastic
sigmac -t es-qs rule.yml

# Μετατροπή για QRadar
sigmac -t qradar rule.yml

# Μαζική μετατροπή κανόνων
sigmac -t splunk -r rules/ -o output/
```

### sigma-cli
```bash
# Έλεγχος κανόνα
sigma check rule.yml

# Μετατροπή κανόνα
sigma convert -t splunk rule.yml
```

---

<a id="section-05"></a>

## 📊 Γρήγορη αναφορά

### Πηγές καταγραφών

| Κατηγορία | Προϊόν | Περιγραφή |
| ---------- | --------- | ------------- |
| `process_creation` | windows | Συμβάντα δημιουργίας διεργασιών |
| `network_connection` | windows | Συνδέσεις δικτύου |
| `file_event` | windows | Ενέργειες αρχείων |
| `registry_event` | windows | Μεταβολές μητρώου Windows |
| `process_access` | windows | Συμβάντα πρόσβασης σε διεργασίες |
| `pipe_created` | windows | Δημιουργία ονομασμένου διαύλου επικοινωνίας |

### Συνηθισμένες ετικέτες αναφοράς στο MITRE ATT&CK

| Ετικέτα | Περιγραφή |
| ----- | ------------- |
| `attack.initial_access` | TA0001 |
| `attack.execution` | TA0002 |
| `attack.persistence` | TA0003 |
| `attack.privilege_escalation` | TA0004 |
| `attack.defense_evasion` | TA0005 |
| `attack.credential_access` | TA0006 |
| `attack.lateral_movement` | TA0008 |

---

<a id="section-06"></a>

## 📚 Πηγές

- [Sigma GitHub](https://github.com/SigmaHQ/sigma)
- [Sigma Rules Repository](https://github.com/SigmaHQ/sigma/tree/master/rules)
- [MITRE ATT&CK](https://attack.mitre.org/)

---

<a id="section-07"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Ανίχνευση με SIEM](SIEM-Detection.el.md)
- [Κανόνες YARA](YARA-Rules.el.md)
- [Ανάλυση καταγραφών](Log-Analysis.el.md)

---

**Προηγούμενο θέμα:** [← Ενίσχυση ασφάλειας](Hardening.el.md)

**Επόμενο θέμα:** [Κανόνες YARA →](YARA-Rules.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
