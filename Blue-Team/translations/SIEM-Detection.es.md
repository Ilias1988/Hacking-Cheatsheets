# 📈 Guía rápida de detección con SIEM

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

> **Nota sobre las consultas:** Los ejemplos requieren los índices, campos y registros adecuados; los umbrales necesitan ajuste al entorno. Las líneas que empiezan por `#` son comentarios explicativos y no deben suponerse sintaxis válida de SPL o KQL. Una búsqueda de eventos 4625 no revela la contraseña probada ni demuestra por sí sola password spraying.


```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🔶 Consultas de Splunk (SPL)

### Autenticación y acceso

#### Detección de fuerza bruta
```spl
# Varios inicios de sesión fallidos desde la misma IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Inicios de sesión fallidos seguidos de uno correcto (compromiso de credenciales)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Detección de password spraying
```spl
# Prueba de la misma contraseña en varias cuentas
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Varios usuarios desde una sola IP en poco tiempo
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Inicios de sesión correctos de administradores
```spl
# Inicios de sesión de cuentas de administrador
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Inicios de sesión fuera del horario laboral
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Procesos y ejecución

#### Creación de procesos sospechosos
```spl
# Comandos codificados de PowerShell
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Relaciones sospechosas entre procesos padre e hijo
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Ejecución de LOLBAS
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Detección de Mimikatz
```spl
# Palabras clave de Mimikatz en el proceso
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Acceso a LSASS (evento 10 de Sysmon)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Mecanismos de persistencia

#### Creación de tareas programadas
```spl
# Nuevas tareas programadas
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Nombres sospechosos de tareas programadas
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Instalación de servicios
```spl
# Nuevos servicios instalados
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Rutas sospechosas de servicios
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Modificaciones del Registro
```spl
# Modificaciones de claves Run (eventos 12-14 de Sysmon)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Movimiento lateral

#### Actividad RDP
```spl
# Conexiones RDP (tipo 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP hacia varios hosts desde un único origen
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Detección de PSExec/WMI
```spl
# Actividad similar a PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Ejecución remota mediante WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Exfiltración de datos

#### Transferencias de grandes volúmenes de datos
```spl
# Volumen elevado de tráfico saliente
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Exfiltración mediante DNS (consultas largas)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Autenticación y acceso

#### Detección de fuerza bruta
```kql
# Inicios de sesión fallidos en Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Varios inicios de sesión fallidos (agregar)
event.code: 4625
# A continuación, usar una visualización para agregar por source.ip

# Inicio de sesión correcto después de fallos
event.code: (4625 OR 4624)
```

#### Password spraying (prueba de pocas contraseñas en muchas cuentas)
```kql
# Autenticación fallida desde una sola IP para varios usuarios
event.code: 4625 AND source.ip: *
# Agregar por source.ip y contar los valores únicos de user.name
```

### Procesos y ejecución

#### Procesos sospechosos
```kql
# PowerShell codificado
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office inicia un intérprete de comandos
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Detección de Mimikatz
```kql
# Proceso con palabras clave de mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Acceso a LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistencia

#### Tareas programadas
```kql
# Creación de tareas
event.code: 4698

# Nombres sospechosos de tareas
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Servicios
```kql
# Instalación de servicios
event.code: 7045

# Rutas sospechosas de servicios
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Movimiento lateral

#### RDP
```kql
# Inicios de sesión RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP desde un origen inusual
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Servicio PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Agregaciones útiles (DSL de Elasticsearch)
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

## 📊 Detección según MITRE ATT&CK

### Acceso inicial (TA0001)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Cuentas válidas | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Ejecución (TA0002)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Ejecución de scripts | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistencia (TA0003)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Tarea programada | `index=windows EventCode=4698` |
| Claves Run del Registro | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Servicio | `index=windows EventCode=7045` |

### Elevación de privilegios (TA0004)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Manipulación de tokens | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Evasión de UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Evasión de defensas (TA0005)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Borrado de registros | `index=windows EventCode=1102` |
| Suplantación mediante apariencia legítima (masquerading) | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Acceso a credenciales (TA0006)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Volcado de LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Volcado de credenciales | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Movimiento lateral (TA0008)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltración (TA0010)
| Técnica | Consulta de Splunk |
|-----------|--------------|
| Túnel DNS | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Guías rápidas relacionadas

- [Análisis de registros](./Log-Analysis.es.md)
- [Reglas Sigma](./Sigma-Rules.es.md)
- [Búsqueda proactiva de amenazas](./Threat-Hunting.es.md)

---

**Anterior:** [← Análisis de registros](./Log-Analysis.es.md)

**Siguiente:** [Búsqueda proactiva de amenazas →](./Threat-Hunting.es.md)
