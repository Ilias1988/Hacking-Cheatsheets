# Справочник по обнаружению с помощью SIEM

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SIEM-Detection.tr.md) • [🇬🇷 Ελληνικά](SIEM-Detection.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

> **Примечание переводчика:** строки с `#` в примерах — пояснения автора: удалите их перед выполнением скопированного запроса. В SPL комментарии заключаются в тройные обратные кавычки ([документация Splunk](https://help.splunk.com/en/splunk-enterprise/search/search-manual/9.3/using-the-search-app/add-comments-to-searches)). Примеры обнаруживают признаки и требуют проверки контекста. В частности, число пользователей и IP-адресов само по себе не доказывает использование одного и того же пароля.

## Запросы Splunk (SPL)

### Аутентификация и доступ

#### Обнаружение перебора паролей
```spl
# Несколько неудачных входов с одного IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# После неудачных входов следует успешный (возможная компрометация учётных данных)
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Обнаружение password spraying
```spl
# Один пароль проверяется для нескольких учётных записей (гипотеза)
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Много пользователей с одного IP за короткое время
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Успешные входы администраторов
```spl
# Входы административных учётных записей
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Входы вне рабочего времени
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Процессы и выполнение

#### Создание подозрительных процессов
```spl
# Кодированные команды PowerShell
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Подозрительные связи родительских и дочерних процессов
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Выполнение LOLBAS — использование штатных средств для атаки
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Обнаружение Mimikatz
```spl
# Ключевые слова Mimikatz в процессе
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Доступ к LSASS (событие Sysmon 10)
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Механизмы закрепления

#### Создание заданий планировщика
```spl
# Новые задания планировщика
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Подозрительные имена заданий планировщика
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Установка службы
```spl
# Установленные новые службы
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Подозрительные пути служб
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Изменения реестра
```spl
# Изменения ключей Run (события Sysmon 12-14)
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Перемещение по сети (lateral movement)

#### Активность RDP
```spl
# Соединения RDP (тип 10)
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# RDP к нескольким узлам из одного источника
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Обнаружение PSExec/WMI
```spl
# Активность, похожая на PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Удалённое выполнение через WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Вывод данных (эксфильтрация)

#### Передача больших объёмов данных
```spl
# Большой объём исходящего трафика
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Вывод данных через DNS (длинные запросы)
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

## Elasticsearch/Kibana (KQL и Lucene)

### Аутентификация и доступ

#### Обнаружение перебора паролей
```kql
# Неудачные входы в Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Несколько неудачных входов (агрегация)
event.code: 4625
# Затем использовать визуализацию для агрегации по source.ip

# Успешный вход после неудач
event.code: (4625 OR 4624)
```

#### Password spraying — перебор одного пароля для многих учётных записей
```kql
# Неудачная аутентификация с одного IP для нескольких пользователей
event.code: 4625 AND source.ip: *
# Агрегировать по source.ip, подсчитать уникальные user.name
```

### Процессы и выполнение

#### Подозрительные процессы
```kql
# Кодированный PowerShell
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# LOLBAS
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Office запускает командную оболочку
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Обнаружение Mimikatz
```kql
# Процесс с ключевыми словами mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Доступ к LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Закрепление

#### Задания планировщика
```kql
# Создание задания
event.code: 4698

# Подозрительные имена заданий
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Службы
```kql
# Установка службы
event.code: 7045

# Подозрительные пути служб
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Перемещение по сети (lateral movement)

#### RDP
```kql
# Входы по RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# RDP из необычного источника
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Служба PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Полезные агрегации (Elasticsearch DSL)
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

## Обнаружение по MITRE ATT&CK

### Первоначальный доступ (TA0001)
| Техника | Запрос Splunk |
|-----------|--------------|
| Фишинг | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Действующие учётные записи | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Выполнение (TA0002)
| Техника | Запрос Splunk |
|-----------|--------------|
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Сценарии | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Закрепление (TA0003)
| Техника | Запрос Splunk |
|-----------|--------------|
| Задание планировщика | `index=windows EventCode=4698` |
| Ключи Run реестра | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Служба | `index=windows EventCode=7045` |

### Повышение привилегий (TA0004)
| Техника | Запрос Splunk |
|-----------|--------------|
| Манипуляции токенами | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Обход UAC | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Уклонение от защиты (TA0005)
| Техника | Запрос Splunk |
|-----------|--------------|
| Очистка журналов | `index=windows EventCode=1102` |
| Маскировка | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Доступ к учётным данным (TA0006)
| Техника | Запрос Splunk |
|-----------|--------------|
| Дамп LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Выгрузка учётных данных | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Перемещение по сети (TA0008)
| Техника | Запрос Splunk |
|-----------|--------------|
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Вывод данных (TA0010)
| Техника | Запрос Splunk |
|-----------|--------------|
| DNS-туннель | `index=dns \| eval len=len(query) \| where len>50` |
| Веб | `index=proxy bytes_out>10000000` |

---

## Связанные справочники

- [Анализ журналов](Log-Analysis.ru.md)
- [Правила Sigma](Sigma-Rules.ru.md)
- [Поиск угроз](Threat-Hunting.ru.md)

---

**Предыдущий раздел:** [← Анализ журналов](Log-Analysis.ru.md)

**Далее:** [Поиск угроз →](Threat-Hunting.ru.md)
