# 📈 Guia de referência de deteção com SIEM

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> **Notas de precisão:** SIEM significa gestão de informação e eventos de segurança. Os nomes de índices e campos são exemplos e dependem da ingestão e normalização. Os eventos 4625 não contêm a palavra-passe tentada: as consultas de password spraying identificam padrões compatíveis, não comprovam reutilização da mesma palavra-passe. A consulta com `transaction` não impõe, por si só, a ordem falha→sucesso. O executável habitual do Word é `WINWORD.EXE`; o filtro `*word.exe` do original pode não o abranger. Em Kibana, KQL filtra documentos; agregação e correlação requerem funções adicionais, e caracteres especiais podem necessitar de escape ([Elastic](https://www.elastic.co/docs/reference/query-languages/kql)). Comentários iniciados por `#` nos blocos SPL/KQL são explicações editoriais, não devem ser assumidos como sintaxe válida da linguagem.

---

## 🔶 Consultas Splunk (SPL)

### Autenticação e acesso

#### Deteção de força bruta
```spl
# Várias falhas de início de sessão a partir do mesmo IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Falhas de início de sessão seguidas de sucesso (comprometimento de credenciais)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Deteção de password spraying
```spl
# Tentativa da mesma palavra-passe em várias contas
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Vários utilizadores a partir do mesmo IP num curto período
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Inícios de sessão administrativos bem-sucedidos
```spl
# Inícios de sessão de contas de administrador
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Inícios de sessão fora do horário de trabalho
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Processos e execução

#### Criação de processos suspeitos
```spl
# Comandos PowerShell codificados
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Relações suspeitas entre processos pai e filho
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Execução de LOLBAS (binários e scripts legítimos utilizados abusivamente)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Deteção de Mimikatz
```spl
# Palavras-chave de Mimikatz no processo
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Acesso ao LSASS (evento 10 do Sysmon)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Mecanismos de persistência

#### Criação de tarefas agendadas
```spl
# Novas tarefas agendadas
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Nomes suspeitos de tarefas agendadas
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Instalação de serviços
```spl
# Novos serviços instalados
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Caminhos de serviço suspeitos
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Modificações do registo
```spl
# Modificações de chaves Run (eventos 12–14 do Sysmon)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Movimento lateral

#### Atividade RDP
```spl
# Ligações RDP (tipo 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP para vários hosts a partir de uma única origem
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Deteção de PSExec/WMI
```spl
# Atividade semelhante à do PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Execução remota através de WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Exfiltração de dados

#### Transferências de grandes volumes de dados
```spl
# Volume elevado de tráfego de saída
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Exfiltração por DNS (consultas longas)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## 🟢 Elasticsearch/Kibana (KQL & Lucene)

### Autenticação e acesso

#### Deteção de força bruta
```kql
# Falhas de início de sessão no Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Várias falhas de início de sessão (agregar)
event.code: 4625
# Depois, utilizar a visualização para agregar por source.ip

# Início de sessão bem-sucedido após falhas
event.code: (4625 OR 4624)
```

#### Password spraying (tentativa de uma palavra-passe em várias contas)
```kql
# Falhas de autenticação para vários utilizadores a partir de um único IP
event.code: 4625 AND source.ip: *
# Agregar por source.ip e contar valores distintos de user.name
```

### Processos e execução

#### Processos suspeitos
```kql
# PowerShell codificado
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office a iniciar uma shell
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Deteção de Mimikatz
```kql
# Processo com palavras-chave de mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Acesso ao LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Persistência

#### Tarefas agendadas
```kql
# Criação de tarefas
event.code: 4698

# Nomes de tarefa suspeitos
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Serviços
```kql
# Instalação de serviços
event.code: 7045

# Caminhos de serviço suspeitos
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Movimento lateral

#### RDP
```kql
# Inícios de sessão RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP de uma origem invulgar
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Serviço PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Agregações úteis (Elasticsearch DSL)
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

## 📊 Deteção por MITRE ATT&CK

### Acesso inicial (TA0001)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Phishing | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Contas válidas | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Execução (TA0002)
| Técnica | Consulta Splunk |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Execução de scripts | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Persistência (TA0003)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Tarefa agendada | `index=windows EventCode=4698` |
| Chaves Run do registo | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Serviço | `index=windows EventCode=7045` |

### Escalada de privilégios (TA0004)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Manipulação de tokens | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Contorno do UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Evasão de defesas (TA0005)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Limpeza de registos | `index=windows EventCode=1102` |
| Disfarce (masquerading) | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Acesso a credenciais (TA0006)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Dump do LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Extração de credenciais (credential dumping) | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Movimento lateral (TA0008)
| Técnica | Consulta Splunk |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Exfiltração (TA0010)
| Técnica | Consulta Splunk |
|-----------|--------------|
| Túnel DNS | `index=dns \| eval len=len(query) \| where len>50` |
| Web | `index=proxy bytes_out>10000000` |

---

## 🔗 Guias de referência relacionados

- [Análise de registos](Log-Analysis.pt.md)
- [Regras Sigma](Sigma-Rules.pt.md)
- [Threat hunting (procura proativa de ameaças)](Threat-Hunting.pt.md)

---

**Anterior:** [← Análise de registos](Log-Analysis.pt.md)

**Seguinte:** [Threat hunting (procura proativa de ameaças) →](Threat-Hunting.pt.md)
