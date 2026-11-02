# 📈 Spickzettel zur SIEM-Erkennung

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](./SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

> **Hinweise zu den Abfragen:** SIEM (Security Information and Event Management) verknüpft und durchsucht Sicherheitsereignisse. Indexnamen, Feldnamen und Zeitschwellen sind Beispiele und müssen zum Datenmodell passen. Die als KQL markierten Beispiele gehören zur Kibana Query Language, nicht zur Kusto Query Language. Führende `#`-Zeilen erläutern die Abfragen; sie sind nicht in jedem Abfrageeditor gültige Syntax. Die Password-Spraying-Abfrage weist viele Konten pro Quelle nach, jedoch nicht die Wiederverwendung desselben Kennworts. Das Brute-Force-Beispiel mit `transaction` garantiert keine zeitliche Reihenfolge von Fehlversuch und Erfolg. `word.exe` im Original ist für Microsoft Word üblicherweise `WINWORD.EXE`; die Originalabfrage bleibt unverändert. Die Muster sind Ausgangspunkte für Erkennungen und können Fehlalarme sowie Erkennungslücken enthalten.

## 🔶 Splunk-Abfragen (SPL)

### Authentifizierung und Zugriff

#### Brute-Force-Erkennung
```spl
# Mehrere fehlgeschlagene Anmeldungen von derselben IP-Adresse
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Fehlgeschlagene Anmeldungen mit anschließendem Erfolg (mögliche kompromittierte Anmeldedaten)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Password Spraying erkennen
```spl
# Dasselbe Kennwort wird bei mehreren Konten ausprobiert
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Mehrere Benutzer von einer IP-Adresse in kurzer Zeit
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Erfolgreiche Administratoranmeldungen
```spl
# Anmeldungen mit Administratorkonten
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Anmeldungen außerhalb der Geschäftszeiten
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Prozesse und Ausführung

#### Verdächtige Prozesserstellung
```spl
# Kodierte PowerShell-Befehle
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Verdächtige Beziehungen zwischen Eltern- und Kindprozessen
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Ausführung von LOLBAS (missbrauchbaren legitimen Windows-Programmen)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Mimikatz erkennen
```spl
# Mimikatz-Schlüsselwörter in der Prozessbefehlszeile
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Zugriff auf LSASS (Sysmon-Ereignis 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Persistenzmechanismen

#### Erstellung geplanter Aufgaben
```spl
# Neue geplante Aufgaben
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Verdächtige Namen geplanter Aufgaben
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Dienstinstallation
```spl
# Neu installierte Dienste
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Verdächtige Dienstpfade
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Registry-Änderungen
```spl
# Änderungen an Run-Schlüsseln (Sysmon-Ereignisse 12–14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Lateral Movement (Seitwärtsbewegung im Netzwerk)

#### RDP-Aktivität
```spl
# RDP-Verbindungen (Typ 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP zu mehreren Hosts von einer Quelle
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### PSExec/WMI erkennen
```spl
# PSExec-ähnliche Aktivität
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Remoteausführung über WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Datenexfiltration

#### Große Datenübertragungen
```spl
# Hoher ausgehender Datenverkehr
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# DNS-Exfiltration (lange Abfragen)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Authentifizierung und Zugriff

#### Brute-Force-Erkennung
```kql
# Fehlgeschlagene Windows-Anmeldungen
event.code: 4625 AND winlog.event_data.LogonType: 3

# Mehrere fehlgeschlagene Anmeldungen (aggregieren)
event.code: 4625
# Anschließend mit einer Visualisierung nach source.ip aggregieren

# Erfolgreiche Anmeldung nach Fehlversuchen
event.code: (4625 OR 4624)
```

#### Password Spraying (Kennwortversuche über viele Konten)
```kql
# Fehlgeschlagene Authentifizierung von einer IP-Adresse für mehrere Benutzer
event.code: 4625 AND source.ip: *
# Nach source.ip aggregieren und unterschiedliche user.name-Werte zählen
```

### Prozesse und Ausführung

#### Verdächtige Prozesse
```kql
# Kodierte PowerShell-Befehle
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office startet eine Shell
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Mimikatz erkennen
```kql
# Prozess mit Mimikatz-Schlüsselwörtern
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Zugriff auf LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistenz (dauerhafter Zugriff)

#### Geplante Aufgaben
```kql
# Aufgabenerstellung
event.code: 4698

# Verdächtige Aufgabennamen
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Dienste
```kql
# Dienstinstallation
event.code: 7045

# Verdächtige Dienstpfade
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Lateral Movement (Seitwärtsbewegung im Netzwerk)

#### RDP
```kql
# RDP-Anmeldungen
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP von einer ungewöhnlichen Quelle
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# PsExec-Dienst
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Nützliche Aggregationen (Elasticsearch DSL)
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

## 📊 Erkennung nach MITRE ATT&CK

### Erstzugriff (TA0001)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Gültige Konten | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Ausführung (TA0002)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Skriptausführung | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistenz (dauerhafter Zugriff) (TA0003)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| Geplante Aufgabe | `index=windows EventCode=4698` |
| Registry Run | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Dienst | `index=windows EventCode=7045` |

### Rechteausweitung (TA0004)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| Token-Manipulation | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| UAC-Umgehung | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Umgehung von Schutzmaßnahmen (TA0005)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| Protokolle löschen | `index=windows EventCode=1102` |
| Tarnung als legitimes Programm | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Zugriff auf Anmeldedaten (TA0006)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| LSASS-Speicherabbild | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Auslesen von Anmeldedaten | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Lateral Movement (Seitwärtsbewegung im Netzwerk) (TA0008)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltration (TA0010)
| Technik | Splunk-Abfrage |
|-----------|--------------|
| DNS-Tunnel | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Verwandte Spickzettel

- [Protokollanalyse](./Log-Analysis.de.md)
- [Sigma-Regeln](./Sigma-Rules.de.md)
- [Threat Hunting (proaktive Bedrohungssuche)](./Threat-Hunting.de.md)

---

**Zurück:** [← Protokollanalyse](./Log-Analysis.de.md)

**Weiter:** [Threat Hunting (proaktive Bedrohungssuche) →](./Threat-Hunting.de.md)
