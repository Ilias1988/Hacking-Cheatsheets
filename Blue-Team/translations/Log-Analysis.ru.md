# Справочник по анализу журналов

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## Журналы событий Windows

### Расположение журналов
```
# Журнал безопасности
C:\Windows\System32\winevt\Logs\Security.evtx

# Системный журнал
C:\Windows\System32\winevt\Logs\System.evtx

# Журнал приложений
C:\Windows\System32\winevt\Logs\Application.evtx

# Журналы PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon, если установлен
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Важные идентификаторы событий безопасности

#### События аутентификации
| ID события | Описание | Значение для анализа |
|----------|-------------|------------|
| **4624** | Успешный вход | Отслеживание доступа пользователей |
| **4625** | Неудачный вход | Выявление перебора паролей |
| **4634** | Выход | Отслеживание сеансов |
| **4647** | Выход по инициативе пользователя | Отслеживание сеансов |
| **4648** | Вход с явно указанными учётными данными | Перемещение по сети |
| **4672** | Назначены специальные привилегии | Действия администратора |
| **4768** | Запрос Kerberos TGT | Аутентификация AD |
| **4769** | Служебный билет Kerberos | Доступ к службам |
| **4771** | Ошибка предварительной аутентификации Kerberos | Password spraying — перебор одного пароля для многих учётных записей |
| **4776** | Аутентификация NTLM | Проверка учётных данных |

#### Типы входа (для 4624/4625)
| Тип | Описание |
|------|-------------|
| 2 | Интерактивный (локальный) |
| 3 | Сетевой |
| 4 | Пакетный |
| 5 | Служба |
| 7 | Разблокировка |
| 8 | Сетевой с открытым паролем |
| 9 | Новые учётные данные |
| 10 | Удалённый интерактивный (RDP) |
| 11 | Интерактивный с кешированными данными |

#### Управление учётными записями
| ID события | Описание |
|----------|-------------|
| **4720** | Создана учётная запись пользователя |
| **4722** | Учётная запись пользователя включена |
| **4723** | Попытка смены пароля |
| **4724** | Сброс пароля |
| **4725** | Учётная запись пользователя отключена |
| **4726** | Учётная запись пользователя удалена |
| **4728** | Участник добавлен в группу безопасности |
| **4732** | Участник добавлен в локальную группу |
| **4756** | Участник добавлен в универсальную группу |

#### События процессов и служб
| ID события | Описание |
|----------|-------------|
| **4688** | Создание процесса |
| **4689** | Завершение процесса |
| **7045** | Установка службы |
| **7036** | Изменение состояния службы |
| **4697** | Установка службы (журнал безопасности) |

#### Задания планировщика
| ID события | Описание |
|----------|-------------|
| **4698** | Создано задание планировщика |
| **4699** | Удалено задание планировщика |
| **4700** | Включено задание планировщика |
| **4702** | Обновлено задание планировщика |

### Команды запросов PowerShell
```powershell
# Получить записи журнала безопасности
Get-WinEvent -LogName Security -MaxEvents 100

# Неудачные входы (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Успешные входы (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Создание нового процесса (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Установка служб (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Поиск по временному диапазону
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Команды Wevtutil
```cmd
# Запрос к журналу безопасности
wevtutil qe Security /c:50 /f:text

# Запрос конкретного ID события
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Экспортировать журнал
wevtutil epl Security C:\evidence\security.evtx

# Очистить журнал (использовать с осторожностью)
wevtutil cl Security
```

---

## Анализ журналов Linux

### Расположение журналов
```bash
# Аутентификация
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# Система
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Ядро
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Аудит
/var/log/audit/audit.log

# Веб-серверы
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Основные команды анализа журналов

#### Анализ аутентификации
```bash
# Неудачные попытки входа по SSH
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Успешные входы по SSH
grep "Accepted" /var/log/auth.log

# Перебор паролей SSH (число попыток по IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Повышение привилегий пользователя (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Попытки su
grep "su:" /var/log/auth.log
```

#### Анализ системы
```bash
# Время загрузки и выключения
last -x | grep shutdown
last -x | grep reboot

# Пользователи, вошедшие в систему
who
w

# История входов
last
lastlog

# Неудачные попытки входа
lastb
```

#### Анализ процессов и служб
```bash
# Выполнение заданий cron
grep CRON /var/log/syslog

# Запуск и остановка служб
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Сообщения ядра
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Полезные шаблоны Grep/Awk
```bash
# Извлечь IP-адреса из журналов
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Подсчитать число появлений каждого IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Поиск по времени
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Поиск в нескольких файлах
grep -r "error" /var/log/

# Наблюдение в реальном времени
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Все журналы
journalctl

# Режим непрерывного наблюдения
journalctl -f

# Начиная с указанного времени
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# По юниту или службе
journalctl -u sshd
journalctl -u nginx

# По приоритету
journalctl -p err       # Errors only
journalctl -p warning   # Warnings and above

# Сообщения ядра
journalctl -k

# Вывод JSON
journalctl -o json
```

---

## Журналы веб-серверов

### Формат журнала Apache
```
# Комбинированный формат журнала (Combined Log Format)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Поля:
# IP - идентификатор - пользователь - [время] "метод путь протокол" статус размер "referer" "user-agent"
```

### Формат журнала Nginx
```
# Формат по умолчанию похож на Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Анализ веб-журналов
```bash
# Самые запрашиваемые URL
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Самые частые IP-адреса
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Коды статуса HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Ошибки 404
grep ' 404 ' /var/log/apache2/access.log

# POST-запросы (возможные атаки)
grep '"POST' /var/log/apache2/access.log

# Попытки SQL-инъекции
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Попытки обхода каталогов (path traversal)
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Запросы по часам
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## Анализ журналов Sysmon

### Основные идентификаторы событий Sysmon
| ID события | Описание |
|----------|-------------|
| 1 | Создание процесса |
| 2 | Изменено время создания файла |
| 3 | Сетевое соединение |
| 5 | Процесс завершён |
| 6 | Загружен драйвер |
| 7 | Загружен образ (DLL) |
| 8 | CreateRemoteThread — создание удалённого потока |
| 10 | ProcessAccess — доступ к процессу |
| 11 | FileCreate — создание файла |
| 12-14 | События реестра |
| 15 | FileCreateStreamHash — создание именованного потока данных |
| 17-18 | События каналов (pipes) |
| 22 | DNS-запрос |

### Запросы PowerShell к Sysmon
```powershell
# Создание процесса (событие 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Сетевые соединения (событие 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# DNS-запросы (событие 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## Краткий справочник

### Подозрительные признаки для поиска

| Категория | Признак |
|----------|---------|
| **Перебор паролей** | Много событий 4625 с одного IP |
| **Password spraying** | События 4625 для многих пользователей с одного IP |
| **Перемещение по сети** | 4624 типа 3 + 4648 |
| **Закрепление** | 4698 (новое задание планировщика) |
| **Повышение привилегий** | 4672 у неадминистративных учётных записей |
| **Выгрузка учётных данных** | Доступ к lsass.exe |

### Инструменты анализа журналов
| Инструмент | Применение |
|------|-----|
| **grep/awk/sed** | Разбор журналов Linux |
| **PowerShell** | Анализ журналов Windows |
| **Splunk** | Корпоративная SIEM |
| **ELK Stack** | SIEM с открытым исходным кодом |
| **LogParser** | SQL-запросы к журналам |
| **Chainsaw** | Быстрый анализ журналов Windows |

---

## Связанные справочники

- [Реагирование на инциденты](Incident-Response.ru.md)
- [Обнаружение с помощью SIEM](SIEM-Detection.ru.md)
- [Правила Sigma](Sigma-Rules.ru.md)

---

**Предыдущий раздел:** [← Реагирование на инциденты](Incident-Response.ru.md)

**Далее:** [Обнаружение с помощью SIEM →](SIEM-Detection.ru.md)
