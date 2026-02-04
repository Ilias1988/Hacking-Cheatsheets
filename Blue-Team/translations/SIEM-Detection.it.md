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

## 🔶 Query Splunk (SPL)

### Autenticazione & Accesso

#### Rilevamento Brute Force
```spl
# Più login falliti dallo stesso IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Login falliti seguiti da successo (compromissione credenziali)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Rilevamento Password Spray
```spl
# Stessa password provata su più account
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Più utenti da singolo IP in breve tempo
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Logon Admin Riusciti
```spl
# Logon account admin
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Logon fuori orario lavorativo
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Processi & Esecuzione

#### Creazione Processo Sospetto
```spl
# PowerShell con comandi codificati
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Relazioni sospette parent-child
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Esecuzione LOLBAS
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Rilevamento Mimikatz
```spl
# Parole chiave mimikatz nel processo
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Accesso LSASS (Sysmon Event 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Meccanismi di Persistenza

#### Creazione Task Pianificato
```spl
# Nuovi task pianificati
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Nomi task pianificati sospetti
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Installazione Servizi
```spl
# Nuovi servizi installati
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Percorsi servizi sospetti
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Modifiche Registro
```spl
# Modifiche chiavi Run (Sysmon Event 12-14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Movimento Laterale

#### Attività RDP
```spl
# Connessioni RDP (Tipo 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP verso più host da una singola sorgente
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Rilevamento PSExec/WMI
```spl
# Attività tipo PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Esecuzione remota WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Esfiltrazione Dati

#### Trasferimenti Dati Grandi
```spl
# Traffico outbound elevato
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Esfiltrazione DNS (query lunghe)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Autenticazione & Accesso

#### Rilevamento Brute Force
```kql
# Login falliti in Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Più login falliti (aggregazione)
event.code: 4625
# Usare visualizzazione per aggregare per source.ip

# Login riuscito dopo fallimenti
event.code: (4625 OR 4624)
```

#### Password Spray
```kql
# Autenticazione fallita da singolo IP verso più utenti
event.code: 4625 AND source.ip: *
# Aggregare per source.ip, contare utenti unici user.name
```

### Processi & Esecuzione

#### Processi Sospetti
```kql
# PowerShell codificato
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office genera shell
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Rilevamento Mimikatz
```kql
# Processo con parole chiave mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Accesso LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistenza

#### Task Pianificati
```kql
# Creazione task
event.code: 4698

# Nomi task sospetti
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Servizi
```kql
# Installazione servizio
event.code: 7045

# Percorsi servizio sospetti
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Movimento Laterale

#### RDP
```kql
# Logon RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP da sorgente insolita
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Servizio PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Aggregazioni Utili (Elasticsearch DSL)
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

## 📊 Rilevamento secondo MITRE ATT&CK

### Initial Access (TA0001)
| Tecnica | Query Splunk |
|---------|-------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Valid Accounts | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Execution (TA0002)
| Tecnica | Query Splunk |
|---------|-------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Scripting | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistence (TA0003)
| Tecnica | Query Splunk |
|---------|-------------|
| Scheduled Task | `index=windows EventCode=4698` |
| Registry Run | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Service | `index=windows EventCode=7045` |

### Privilege Escalation (TA0004)
| Tecnica | Query Splunk |
|---------|-------------|
| Token Manipulation | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| UAC Bypass | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Defense Evasion (TA0005)
| Tecnica | Query Splunk |
|---------|-------------|
| Clear Logs | `index=windows EventCode=1102` |
| Masquerading | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Credential Access (TA0006)
| Tecnica | Query Splunk |
|---------|-------------|
| LSASS Dump | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Credential Dump | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Lateral Movement (TA0008)
| Tecnica | Query Splunk |
|---------|-------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltration (TA0010)
| Tecnica | Query Splunk |
|---------|-------------|
| DNS Tunnel | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Cheatsheet Correlati

- [Analisi Log](./Log-Analysis.it.md)
- [Sigma Rules](./Sigma-Rules.it.md)
- [Threat Hunting](./Threat-Hunting.it.md)

---

**Precedente:** [← Analisi dei Log](./Log-Analysis.it.md)

**Successivo:** [Threat Hunting →](./Threat-Hunting.it.md)
