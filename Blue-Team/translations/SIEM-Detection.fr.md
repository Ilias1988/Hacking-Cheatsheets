# 📈 Aide-mémoire : détection avec un SIEM

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Précisions de traduction :** les requêtes sont des exemples à adapter aux index, champs, règles de collecte et fenêtres temporelles du SIEM. Les lignes `#` sont des annotations pédagogiques à séparer des requêtes selon l’interface utilisée. Le comptage d’échecs sur plusieurs comptes peut suggérer du password spraying, mais les événements ne révèlent pas le mot de passe essayé. La requête `transaction` présentée ne garantit pas à elle seule l’ordre « échecs puis réussite ». Les seuils, noms de comptes et filtres réseau simplifiés nécessitent une validation locale.

---

## 🔶 Requêtes Splunk (SPL)

### Authentification et accès

#### Détection des attaques par force brute
```spl
# Plusieurs connexions échouées depuis la même adresse IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Échecs suivis d'une réussite : compromission possible d'identifiants
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Détection du password spraying
```spl
# Même mot de passe essayé sur plusieurs comptes
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Plusieurs utilisateurs depuis la même IP sur une courte période
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Ouvertures de session administrateur réussies
```spl
# Connexions de comptes administrateurs
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Connexions en dehors des heures de travail
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Processus et exécution

#### Création de processus suspects
```spl
# Commandes PowerShell encodées
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Relations parent-enfant suspectes
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Exécution de binaires légitimes détournés (LOLBAS)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Détection de Mimikatz
```spl
# Mots-clés Mimikatz dans le processus
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Accès à LSASS (événement Sysmon 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Mécanismes de persistance

#### Création de tâches planifiées
```spl
# Nouvelles tâches planifiées
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Noms de tâches planifiées suspects
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Installation de services
```spl
# Nouveaux services installés
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Chemins de services suspects
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Modifications du Registre
```spl
# Modifications des clés Run (événements Sysmon 12 à 14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Déplacement latéral

#### Activité RDP
```spl
# Connexions RDP (type 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP vers plusieurs hôtes depuis une même source
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Détection de PSExec/WMI
```spl
# Activité de type PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Exécution distante par WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Exfiltration de données

#### Transferts de données volumineux
```spl
# Volume élevé de trafic sortant
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Exfiltration DNS : requêtes longues
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Authentification et accès

#### Détection des attaques par force brute
```kql
# Connexions échouées sous Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Plusieurs connexions échouées : agrégation
event.code: 4625
# Utiliser ensuite une visualisation pour agréger par source.ip

# Connexion réussie après des échecs
event.code: (4625 OR 4624)
```

#### Pulvérisation de mots de passe (password spraying)
```kql
# Échecs d'authentification depuis une IP vers plusieurs utilisateurs
event.code: 4625 AND source.ip: *
# Agréger par source.ip et compter les valeurs distinctes de user.name
```

### Processus et exécution

#### Processus suspects
```kql
# PowerShell encodé
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office lançant un interpréteur de commandes
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Détection de Mimikatz
```kql
# Processus contenant des mots-clés Mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Accès à LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistance

#### Tâches planifiées
```kql
# Création d'une tâche
event.code: 4698

# Noms de tâches suspects
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Services
```kql
# Installation d'un service
event.code: 7045

# Chemins de services suspects
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Déplacement latéral

#### RDP
```kql
# Connexions RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP depuis une source inhabituelle
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Service PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Agrégations utiles (DSL Elasticsearch)
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

## 📊 Détection selon MITRE ATT&CK

### Accès initial (TA0001)
| Technique | Requête Splunk |
|-----------|--------------|
| Hameçonnage (phishing) | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Comptes valides | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Exécution (TA0002)
| Technique | Requête Splunk |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Scripts | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistance (TA0003)
| Technique | Requête Splunk |
|-----------|--------------|
| Tâche planifiée | `index=windows EventCode=4698` |
| Clés Run du Registre | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Service | `index=windows EventCode=7045` |

### Élévation de privilèges (TA0004)
| Technique | Requête Splunk |
|-----------|--------------|
| Manipulation de jetons | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Contournement de l'UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Contournement des défenses (TA0005)
| Technique | Requête Splunk |
|-----------|--------------|
| Effacement des journaux | `index=windows EventCode=1102` |
| Déguisement (masquerading) | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Accès aux identifiants (TA0006)
| Technique | Requête Splunk |
|-----------|--------------|
| Extraction de la mémoire LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Extraction d'identifiants | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Déplacement latéral (TA0008)
| Technique | Requête Splunk |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltration (TA0010)
| Technique | Requête Splunk |
|-----------|--------------|
| Tunnel DNS | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Aide-mémoire associés

- [Analyse des journaux](./Log-Analysis.fr.md)
- [Règles Sigma](./Sigma-Rules.fr.md)
- [Recherche proactive de menaces](./Threat-Hunting.fr.md)

---

**Précédent:** [← Analyse des journaux](./Log-Analysis.fr.md)

**Suivant:** [Recherche proactive de menaces →](./Threat-Hunting.fr.md)
