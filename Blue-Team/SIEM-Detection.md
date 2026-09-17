# 📈 SIEM Detection Cheatsheet

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔶 Splunk Queries (SPL)

### Authentication & Access

#### Brute Force Detection
```spl
# Multiple failed logins from same IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Failed logins followed by success (credential compromise)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Password Spray Detection
```spl
# Same password tried on multiple accounts
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Multiple users from single IP in short time
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Successful Admin Logons
```spl
# Admin account logons
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Logons outside business hours
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Process & Execution

#### Suspicious Process Creation
```spl
# PowerShell encoded commands
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Suspicious parent-child relationships
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# LOLBAS execution
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Mimikatz Detection
```spl
# Mimikatz keywords in process
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# LSASS access (Sysmon Event 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Persistence Mechanisms

#### Scheduled Task Creation
```spl
# New scheduled tasks
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Suspicious scheduled task names
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Service Installation
```spl
# New services installed
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Suspicious service paths
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Registry Modifications
```spl
# Run key modifications (Sysmon Event 12-14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Lateral Movement

#### RDP Activity
```spl
# RDP connections (Type 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP to multiple hosts from single source
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### PSExec/WMI Detection
```spl
# PSExec-like activity
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# WMI remote execution
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Data Exfiltration

#### Large Data Transfers
```spl
# High outbound traffic
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# DNS exfiltration (long queries)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Authentication & Access

#### Brute Force Detection
```kql
# Failed logins in Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Multiple failed logins (aggregate)
event.code: 4625
# Then use visualization to aggregate by source.ip

# Successful login after failures
event.code: (4625 OR 4624)
```

#### Password Spray
```kql
# Failed auth from single IP to multiple users
event.code: 4625 AND source.ip: *
# Aggregate by source.ip, count unique user.name
```

### Process & Execution

#### Suspicious Processes
```kql
# Encoded PowerShell
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office spawning shell
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Mimikatz Detection
```kql
# Process with mimikatz keywords
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# LSASS access
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistence

#### Scheduled Tasks
```kql
# Task creation
event.code: 4698

# Suspicious task names
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Services
```kql
# Service installation
event.code: 7045

# Suspicious service paths
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Lateral Movement

#### RDP
```kql
# RDP logons
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP from unusual source
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# PsExec service
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Useful Aggregations (Elasticsearch DSL)
```json
{
  "aggs": {
    "failed_logins_by_ip": {
      "terms": {
        "field": "source.ip",
        "size": 10
      },
      "aggs": {
        "unique_users": {
          "cardinality": {
            "field": "user.name"
          }
        }
      }
    }
  },
  "query": {
    "bool": {
      "must": [
        { "match": { "event.code": 4625 }}
      ]
    }
  }
}
```

---

## 📊 Detection by MITRE ATT&CK

### Initial Access (TA0001)
| Technique | Splunk Query |
|-----------|--------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Valid Accounts | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Execution (TA0002)
| Technique | Splunk Query |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Scripting | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistence (TA0003)
| Technique | Splunk Query |
|-----------|--------------|
| Scheduled Task | `index=windows EventCode=4698` |
| Registry Run | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Service | `index=windows EventCode=7045` |

### Privilege Escalation (TA0004)
| Technique | Splunk Query |
|-----------|--------------|
| Token Manipulation | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| UAC Bypass | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Defense Evasion (TA0005)
| Technique | Splunk Query |
|-----------|--------------|
| Clear Logs | `index=windows EventCode=1102` |
| Masquerading | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Credential Access (TA0006)
| Technique | Splunk Query |
|-----------|--------------|
| LSASS Dump | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Credential Dump | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Lateral Movement (TA0008)
| Technique | Splunk Query |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltration (TA0010)
| Technique | Splunk Query |
|-----------|--------------|
| DNS Tunnel | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Related Cheatsheets

- [Log Analysis](./Log-Analysis.md)
- [Sigma Rules](./Sigma-Rules.md)
- [Threat Hunting](./Threat-Hunting.md)

---

**Previous:** [← Log Analysis](./Log-Analysis.md)

**Next:** [Threat Hunting →](./Threat-Hunting.md)
