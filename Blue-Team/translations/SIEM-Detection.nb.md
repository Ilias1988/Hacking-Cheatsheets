# 📈 SIEM-deteksjon – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../SIEM-Detection.md) • [🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔶 Splunk-spørringer (SPL)

> **Presisering:** Spørringene er eksempler som forutsetter bestemte indekser, feltnavn og logging. `#`-linjene er forklarende kommentarer fra originalen og må ikke uten videre limes inn som SPL. Treff og terskler må valideres i miljøet.

### Autentisering og tilgang

#### Deteksjon av brute force
```spl
# Mange mislykkede pålogginger fra samme IP-adresse
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Mislykkede pålogginger etterfulgt av en vellykket pålogging (kompromittert påloggingsinformasjon)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Deteksjon av passordspraying

> **Presisering:** Logger over mislykket autentisering viser ikke hvilket passord som ble brukt. Spørringene finner mønstre som kan tyde på passordspraying; de beviser ikke at samme passord ble forsøkt.
```spl
# Samme passord forsøkt på flere kontoer
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Flere brukere fra én IP-adresse på kort tid
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Vellykkede administratorpålogginger
```spl
# Pålogginger med administratorkontoer
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Pålogginger utenfor arbeidstiden
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Prosesser og kjøring

#### Opprettelse av mistenkelige prosesser
```spl
# Kodede PowerShell-kommandoer
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Mistenkelige forhold mellom foreldre- og underprosesser
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Kjøring av LOLBAS (legitime systemverktøy misbrukt til angrep)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Mimikatz-deteksjon
```spl
# Mimikatz-nøkkelord i prosessen
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Tilgang til LSASS (Sysmon-hendelse 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Mekanismer for vedvarende tilgang (persistence)

#### Opprettelse av planlagte oppgaver
```spl
# Nye planlagte oppgaver
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Mistenkelige navn på planlagte oppgaver
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Tjenesteinstallasjon
```spl
# Nye tjenester installert
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Mistenkelige tjenestestier
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Registerendringer
```spl
# Endringer i Run-nøkler (Sysmon-hendelse 12–14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Lateral bevegelse

#### RDP-aktivitet
```spl
# RDP-tilkoblinger (type 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP til flere verter fra én kilde
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### PSExec-/WMI-deteksjon
```spl
# PSExec-lignende aktivitet
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Ekstern kjøring med WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Dataeksfiltrering

#### Store dataoverføringer
```spl
# Stor utgående trafikk
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# DNS-eksfiltrering (lange spørringer)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL og Lucene)

> **Presisering:** Originalen grupperer KQL og Lucene sammen. Syntaksen og støtten for jokertegn avhenger av valgt spørringsspråk og innstillinger. Enkeltfiltrene viser kandidater; de korrelerer ikke alene feil etterfulgt av suksess.

### Autentisering og tilgang

#### Deteksjon av brute force
```kql
# Mislykkede pålogginger i Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Mange mislykkede pålogginger (aggreger)
event.code: 4625
# Bruk deretter en visualisering til å aggregere etter source.ip

# Vellykket pålogging etter mislykkede forsøk
event.code: (4625 OR 4624)
```

#### Passordspraying
```kql
# Mislykket autentisering fra én IP-adresse mot flere brukere
event.code: 4625 AND source.ip: *
# Aggreger etter source.ip og tell unike user.name-verdier
```

### Prosesser og kjøring

#### Mistenkelige prosesser
```kql
# Kodet PowerShell
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office starter et kommandoskall
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Mimikatz-deteksjon
```kql
# Prosess med Mimikatz-nøkkelord
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Tilgang til LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Vedvarende tilgang

#### Planlagte oppgaver
```kql
# Opprettelse av oppgave
event.code: 4698

# Mistenkelige oppgavenavn
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Tjenester
```kql
# Tjenesteinstallasjon
event.code: 7045

# Mistenkelige tjenestestier
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Lateral bevegelse

#### RDP
```kql
# RDP-pålogginger
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP fra uvanlig kilde
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# PsExec-tjeneste
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Nyttige aggregeringer (Elasticsearch DSL)
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

## 📊 Deteksjon etter MITRE ATT&CK

### Innledende tilgang (TA0001)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Gyldige kontoer | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Kjøring (TA0002)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Skriptkjøring | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Vedvarende tilgang (TA0003)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| Planlagt oppgave | `index=windows EventCode=4698` |
| Run-nøkkel i registeret | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Tjeneste | `index=windows EventCode=7045` |

### Rettighetseskalering (TA0004)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| Tokenmanipulering | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Omgåelse av UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Omgåelse av forsvar (TA0005)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| Tømming av logger | `index=windows EventCode=1102` |
| Maskering | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Tilgang til påloggingsinformasjon (TA0006)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| LSASS-dump | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Uthenting av påloggingsinformasjon | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Lateral bevegelse (TA0008)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Eksfiltrering (TA0010)
| Teknikk | Splunk-spørring |
|-----------|--------------|
| DNS-tunnel | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Relaterte hurtigreferanser

- [Logganalyse](Log-Analysis.nb.md)
- [Sigma-regler](Sigma-Rules.nb.md)
- [Trusseljakt](Threat-Hunting.nb.md)

---

**Forrige:** [← Logganalyse](Log-Analysis.nb.md)

**Neste:** [Trusseljakt →](Threat-Hunting.nb.md)
