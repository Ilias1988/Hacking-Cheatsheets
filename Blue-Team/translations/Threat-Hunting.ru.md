# Справочник по поиску угроз (Threat Hunting)

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

**Подписи к схеме:** гипотеза (предположение) → сбор (данные) → анализ (поиск угроз) → реагирование (действие) → документирование (обучение).

## Процесс поиска угроз

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HYPOTHESIS│────▶│ 2. COLLECT  │────▶│ 3. ANALYZE  │────▶│ 4. RESPOND  │
│   (Theory)   │     │   (Data)    │     │   (Hunt)    │     │   (Action)  │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOCUMENT │◀─────────────────────────┘
                          │   (Learn)   │
                          └─────────────┘
```

---

## Примеры гипотез

| Категория | Гипотеза |
|----------|------------|
| **Первоначальный доступ** | Атакующие могут использовать фишинг для доставки вредоносного ПО |
| **Закрепление** | Атакующие могут создавать задания планировщика для закрепления |
| **Учётные данные** | Атакующие могут выгружать LSASS для сбора учётных данных |
| **Перемещение по сети** | Атакующие могут использовать RDP для перемещения по сети |
| **Вывод данных** | Атакующие могут использовать DNS-туннели для вывода данных |

---

## Методы поиска угроз

### Поиск механизмов закрепления

#### Ключи Run в реестре Windows
```powershell
# Запросить все ключи Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Задания планировщика
```powershell
# Вывести все задания планировщика
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Искать подозрительные задания (не Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Службы
```powershell
# Неподписанные службы
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Поиск перемещения по сети

#### Соединения RDP
```powershell
# Сеансы RDP (событие 4624, тип 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Сетевые соединения
```powershell
# Текущие соединения
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# С именами процессов
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Поиск доступа к учётным данным

#### Доступ к LSASS
```powershell
# Событие Sysmon 10 — доступ к LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Поиск выполнения команд

#### Кодированный PowerShell
```powershell
# PowerShell с кодированием
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Подозрительные связи родительских и дочерних процессов
```powershell
# Office запускает cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## Поиск угроз в Linux

### Закрепление
```bash
# Задания cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Авторизованные ключи SSH
find / -name "authorized_keys" 2>/dev/null

# Сценарии запуска
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Исполняемые файлы SUID
find / -perm -4000 -type f 2>/dev/null
```

### Сеть
```bash
# Прослушиваемые порты
ss -tulnp
netstat -tulnp

# Установленные соединения
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Необычные DNS-запросы
tcpdump -i any port 53 -w dns.pcap
```

### Процессы
```bash
# Все процессы с полной командной строкой
ps auxwww

# Дерево процессов
pstree -p

# Скрытые процессы
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Недавно изменённые исполняемые файлы
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## Поиск по индикаторам компрометации (IOC)

### Поиск по хешам
```powershell
# Вычислить хеши
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Поиск известного вредоносного хеша
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Поиск по IP-адресам и доменам
```powershell
# Кеш DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Соединение с известным вредоносным IP
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## Инструменты

| Инструмент | Назначение |
|------|---------|
| **Velociraptor** | Масштабный поиск угроз на конечных устройствах |
| **OSQuery** | Запросы к конечным устройствам на основе SQL |
| **KAPE** | Сбор данных для первичной оценки |
| **Chainsaw** | Быстрый анализ журналов Windows |
| **DeepBlueCLI** | Поиск угроз с помощью PowerShell |

---

## Краткий контрольный список поиска угроз

```markdown
□ Проверить места автозапуска (реестр, службы, задания)
□ Изучить недавнее создание процессов (событие 4688, Sysmon 1)
□ Проанализировать сетевые соединения
□ Искать кодированные команды
□ Искать обращения к LSASS
□ Проверить необычные связи родительских и дочерних процессов
□ Изучить журналы PowerShell
□ Искать признаки перемещения по сети
```

---

## Связанные справочники

- [Анализ журналов](Log-Analysis.ru.md)
- [Обнаружение с помощью SIEM](SIEM-Detection.ru.md)
- [Правила Sigma](Sigma-Rules.ru.md)

---

**Предыдущий раздел:** [← Обнаружение с помощью SIEM](SIEM-Detection.ru.md)

**Далее:** [Правила Sigma →](Sigma-Rules.ru.md)
