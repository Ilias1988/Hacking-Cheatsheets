<a id="-google-dorking---complete-cheatsheet"></a>
# 🔍 البحث المتقدم في Google ‏(Google Dorking) - مرجع شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> يكتب الرسم النصي اسم Google Dorking بالإنجليزية؛ المقصود استخدام معاملات البحث المتقدم لجمع المعلومات المتاحة لمحرك البحث.

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Info Gathering">
</p>

<p align="center">
  <b>🎯 تقنيات البحث المتقدم في Google للباحثين الأمنيين</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [معاملات البحث الأساسية](#-basic-operators)
- [معاملات البحث المتقدمة](#-advanced-operators)
- [الجمع بين معاملات البحث](#-combining-operators)
- [اكتشاف الملفات](#-file-discovery)
- [قوائم المجلدات](#-directory-listing)
- [لوحات الإدارة](#-admin-panels)
- [قواعد البيانات وSQL](#-database--sql)
- [ملفات الإعدادات](#-configuration-files)
- [بيانات الاعتماد والأسرار](#-credentials--secrets)
- [خوادم قد تكون معرضة للثغرات](#-vulnerable-servers)
- [استعلامات لبرامج مكافآت الثغرات](#-bug-bounty-dorks)
- [استعلامات خاصة بأنظمة إدارة المحتوى (CMS)](#-cms-specific)
- [مرجع سريع](#-quick-reference)
- [الأدوات والأتمتة](#-tools--automation)
- [مصادر](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Google Dorking**، ويُعرف أيضًا باسم Google Hacking، أسلوب يستخدم عمليات البحث المتقدمة للعثور على معلومات حساسة مكشوفة على الإنترنت.

<a id="what-can-you-find"></a>
### ما الذي يمكن العثور عليه؟

| الفئة | أمثلة |
|----------|----------|
| **ملفات حساسة** | نسخ احتياطية وإعدادات وسجلات وقواعد بيانات |
| **لوحات مكشوفة** | لوحات إدارة ولوحات متابعة وphpMyAdmin |
| **بيانات الاعتماد** | كلمات مرور ومفاتيح API ورموز وصول |
| **الثغرات** | حقن SQL ومجلدات متاحة للتصفح |
| **بيانات شخصية** | عناوين بريد وأرقام هواتف ومستندات |

<a id="search-engine-urls"></a>
### عناوين محركات البحث

| المحرك | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

<a id="-basic-operators"></a>
## 🔧 معاملات البحث الأساسية

<a id="site-operator"></a>
### المعامل site:

حصر البحث في نطاق محدد:

```
site:example.com                     # ضمن example.com فقط
site:*.example.com                   # جميع النطاقات الفرعية
site:example.com -www                # استبعاد www
site:.gov                            # مواقع حكومية
site:.edu                            # مواقع تعليمية
```

> **حدود النتائج:** يعرض `site:` صفحات مفهرسة من النطاق، وقد يشمل نطاقاته الفرعية، لكنه لا يُرجع بالضرورة كل الصفحات المفهرسة؛ لذا لا تثبت النتائج اكتمال الحصر. كما أن ظهور صفحة خطأ أو لوحة إدارة ليس بمفرده إثباتًا لثغرة. [توثيق Google للمعامل site:](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site).

<a id="inurl-operator"></a>
### المعامل inurl:

البحث في عنوان URL:

```
inurl:admin                          # وجود admin في URL
inurl:login                          # صفحات تسجيل الدخول
inurl:dashboard                      # لوحات المتابعة
inurl:config                         # ملفات الإعدادات
inurl:backup                         # ملفات النسخ الاحتياطية
```

<a id="intitle-operator"></a>
### المعامل intitle:

البحث في عنوان الصفحة:

```
intitle:"index of"                   # قوائم المجلدات
intitle:admin                        # صفحات الإدارة
intitle:login                        # صفحات تسجيل الدخول
intitle:"dashboard"                  # لوحات المتابعة
intitle:"control panel"              # لوحات التحكم
```

<a id="intext-operator"></a>
### المعامل intext:

البحث في محتوى الصفحة:

```
intext:"password"                    # وجود كلمة password في النص
intext:"username"                    # وجود كلمة username في النص
intext:"api_key"                     # مفاتيح API
intext:"secret"                      # أسرار
```

<a id="filetype-operator"></a>
### المعامل filetype:

البحث عن أنواع ملفات محددة:

```
filetype:pdf                         # ملفات PDF
filetype:doc                         # مستندات Word
filetype:xls                         # ملفات Excel
filetype:sql                         # ملفات SQL
filetype:log                         # ملفات السجلات
filetype:bak                         # ملفات النسخ الاحتياطية
filetype:env                         # ملفات البيئة
```

---

<a id="-advanced-operators"></a>
## ⚡ معاملات البحث المتقدمة

<a id="all-variants"></a>
### الصيغ التي تشترط جميع الكلمات

```
allinurl:admin login                 # جميع الكلمات في URL
allintitle:admin panel               # جميع الكلمات في عنوان الصفحة
allintext:password username          # جميع الكلمات في النص
```

<a id="cache--link"></a>
### النسخ المخزنة والروابط

```
cache:example.com                    # نسخة مخزنة مؤقتًا
link:example.com                     # صفحات تشير إلى الموقع
related:example.com                  # مواقع ذات صلة
```

> **ملاحظة تاريخية:** احتُفظ بصيغ المصدر كما هي، لكن Google أعلن أن `cache:` لم يعد يعمل في سبتمبر 2024، وأن `related:` لم يعد مدعومًا في يوليو 2023. لذلك تُقرأ أمثلة النسخ المخزنة والمواقع المشابهة هنا بوصفها أمثلة قديمة، بما فيها صف الجدول والنصيحة الخاصة بـ `cache:` لاحقًا. [سجل تحديثات Google الرسمي](https://developers.google.com/search/updates).

<a id="date-range"></a>
### نطاق زمني

```
after:2023-01-01                     # بعد التاريخ
before:2024-01-01                    # قبل التاريخ
daterange:2458850-2459215            # نطاق التاريخ اليولياني
```

<a id="wildcards--or"></a>
### محارف البدل وعملية OR

```
admin * panel                        # محرف بدل
admin OR login                       # أي من الكلمتين
"admin panel"                        # عبارة مطابقة تمامًا
-inurl:https                         # استبعاد HTTPS
```

<a id="number-range"></a>
### نطاق الأعداد

```
inurl:id= 1..1000                    # نطاق أعداد
price $100..$500                     # نطاق أسعار
```

---

<a id="-combining-operators"></a>
## 🔗 الجمع بين معاملات البحث

<a id="basic-combinations"></a>
### تركيبات أساسية

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

<a id="advanced-combinations"></a>
### تركيبات متقدمة

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

<a id="bug-bounty-combinations"></a>
### تركيبات لبرامج مكافآت الثغرات

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

<a id="-file-discovery"></a>
## 📁 اكتشاف الملفات

<a id="sensitive-documents"></a>
### مستندات حساسة

```
# مستندات Office
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# ملفات نصية
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

<a id="backup-files"></a>
### ملفات النسخ الاحتياطية

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

<a id="database-files"></a>
### ملفات قواعد البيانات

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

<a id="log-files"></a>
### ملفات السجلات

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

<a id="configuration-files"></a>
### ملفات الإعدادات

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

<a id="source-code"></a>
### الشيفرة المصدرية

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

<a id="-directory-listing"></a>
## 📂 قوائم المجلدات

<a id="index-of"></a>
### قوائم Index Of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

<a id="specific-directories"></a>
### مجلدات محددة

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

<a id="file-types-in-directories"></a>
### أنواع الملفات داخل المجلدات

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

<a id="-admin-panels"></a>
## 🔐 لوحات الإدارة

<a id="generic-admin"></a>
### لوحات إدارة عامة

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

<a id="cpanel--hosting"></a>
### لوحات cPanel والاستضافة

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

<a id="database-admin"></a>
### إدارة قواعد البيانات

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

<a id="control-panels"></a>
### لوحات التحكم

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

<a id="-database--sql"></a>
## 💾 قواعد البيانات وSQL

<a id="sql-files"></a>
### ملفات SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

<a id="sql-errors"></a>
### أخطاء SQL

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

<a id="sql-injection-indicators"></a>
### مؤشرات حقن SQL

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

<a id="database-dumps"></a>
### نسخ قواعد البيانات المصدّرة (Dumps)

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-configuration-files"></a>
## ⚙️ ملفات الإعدادات

<a id="web-server-config"></a>
### إعدادات خادم الويب

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

<a id="application-config"></a>
### إعدادات التطبيق

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

<a id="git-config"></a>
### إعدادات Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

<a id="aws--cloud"></a>
### AWS والبيئات السحابية

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # بادئة مفتاح AWS
site:target.com "credentials" filetype:json
```

---

<a id="-credentials--secrets"></a>
## 🔑 بيانات الاعتماد والأسرار

<a id="passwords"></a>
### كلمات المرور

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

<a id="username--password-combos"></a>
### أزواج أسماء المستخدمين وكلمات المرور

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

<a id="api-keys--tokens"></a>
### مفاتيح API ورموز الوصول

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

<a id="email-addresses"></a>
### عناوين البريد الإلكتروني

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

<a id="private-keys"></a>
### المفاتيح الخاصة

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a id="-vulnerable-servers"></a>
## ⚠️ خوادم قد تكون معرضة للثغرات

<a id="open-directories"></a>
### مجلدات متاحة للتصفح

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

<a id="default-pages"></a>
### الصفحات الافتراضية

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

<a id="error-pages"></a>
### صفحات الأخطاء

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

<a id="exposed-services"></a>
### خدمات مكشوفة

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

<a id="-bug-bounty-dorks"></a>
## 🐛 استعلامات لبرامج مكافآت الثغرات

<a id="quick-wins"></a>
### نتائج أولية سريعة

```
# ملفات حساسة
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# لوحات الإدارة
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# ملفات الإعدادات
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# بيانات الاعتماد
site:target.com (intext:password | intext:api_key | intext:secret)
```

<a id="subdomain-discovery"></a>
### اكتشاف النطاقات الفرعية

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

<a id="api-endpoints"></a>
### نقاط نهاية API

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

<a id="development--staging"></a>
### بيئات التطوير والتهيئة (Staging)

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

<a id="authentication-pages"></a>
### صفحات المصادقة

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

<a id="file-upload"></a>
### رفع الملفات

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

<a id="-cms-specific"></a>
## 📱 استعلامات خاصة بأنظمة إدارة المحتوى (CMS)

<a id="wordpress"></a>
### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

<a id="joomla"></a>
### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

<a id="drupal"></a>
### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

<a id="magento"></a>
### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-operators"></a>
### معاملات البحث الأساسية

| المعامل | الوظيفة | مثال |
|----------|----------|---------|
| `site:` | حصر النطاق | `site:target.com` |
| `inurl:` | البحث في URL | `inurl:admin` |
| `intitle:` | البحث في عنوان الصفحة | `intitle:login` |
| `intext:` | البحث في المحتوى | `intext:password` |
| `filetype:` | امتداد الملف | `filetype:sql` |
| `ext:` | امتداد الملف | `ext:pdf` |
| `cache:` | نسخة مخزنة مؤقتًا | `cache:target.com` |
| `"..."` | عبارة مطابقة تمامًا | `"admin panel"` |
| `-` | استبعاد | `-site:www` |
| `OR` / `\|` | أي من الكلمتين | `admin OR login` |
| `*` | محرف بدل | `admin * panel` |

<a id="top-10-dorks-for-bug-bounty"></a>
### 10 استعلامات لبرامج مكافآت الثغرات

| # | استعلام بحث متقدم | الغرض |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | ملفات قواعد البيانات |
| 2 | `site:target.com inurl:admin` | لوحات الإدارة |
| 3 | `site:target.com filetype:log` | ملفات السجلات |
| 4 | `site:target.com filetype:env` | ملفات البيئة |
| 5 | `site:target.com intext:password filetype:txt` | كلمات المرور |
| 6 | `intitle:"index of" site:target.com` | قائمة المجلد |
| 7 | `site:target.com inurl:api` | نقاط نهاية API |
| 8 | `site:target.com filetype:bak` | ملفات النسخ الاحتياطية |
| 9 | `site:*.target.com -www` | النطاقات الفرعية |
| 10 | `site:target.com "api_key"` | مفاتيح API |

---

<a id="-tools--automation"></a>
## 🛠️ الأدوات والأتمتة

<a id="google-dorking-tools"></a>
### أدوات Google Dorking

| الأداة | الوصف | URL |
|------|-------------|-----|
| **Google Hacking Database** | مجموعة استعلامات Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | مولّد استعلامات | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | بحث Google متقدم بأسلوب غير نشط | GitHub |
| **GooDork** | أداة بحث متقدم باستخدام Python | GitHub |
| **GooFuzz** | اختبار مدخلات متنوعة باستخدام Google | GitHub |

<a id="dorking-websites"></a>
### مواقع للبحث المتقدم

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

<a id="automation-script-example"></a>
### مثال سكربت أتمتة

```bash
#!/bin/bash
# أتمتة بسيطة لاستعلامات Google Dork

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وممارسات موصى بها

<a id="bug-bounty-tips"></a>
### نصائح لبرامج مكافآت الثغرات

1. **ابدأ ببحث واسع ثم حدّده**
   ```
   site:target.com                    # أولًا
   site:target.com filetype:sql       # ثم تضييق البحث
   ```

2. **استخدم عدة محركات بحث**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **راجع الصفحات المخزنة مؤقتًا**
   ```
   cache:target.com/admin
   ```

   > المثال السابق تاريخي؛ معامل `cache:` لم يعد يعمل في Google Search، كما توضّح ملاحظة الإصدارات أعلاه.

4. **ابحث عن النطاقات الفرعية**
   ```
   site:*.target.com -www
   ```

5. **حدّد معدل استعلامات البحث**
   - قد يحظر Google كثرة الاستعلامات

<a id="avoid-detection"></a>
### تجنّب الاكتشاف

```
# استخدام عناوين IP أو شبكات VPN مختلفة
# إضافة فواصل زمنية بين عمليات البحث
# تنويع أنماط البحث
# استخدام متصفحات مختلفة
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تحذير:** يذكر المصدر أن Google Dorking في حد ذاته قانوني، لكن **الوصول** إلى البيانات المكشوفة دون تصريح قد يكون مخالفًا للقانون.
> 
> - ✅ استخدمه في اختبارات أمنية مصرّح بها
> - ✅ استخدمه ضمن برامج مكافآت الثغرات
> - ✅ أبلغ عن الثغرات بمسؤولية
> - ❌ لا تصل إلى البيانات دون إذن
> - ❌ لا تنزّل بيانات حساسة ولا تخزّنها
> - ❌ لا تستغل الثغرات التي تعثر عليها
> 
> **احصل دائمًا على التصريح المناسب قبل الاختبار!**

---

<a id="-resources"></a>
## 📚 مصادر

<a id="official-resources"></a>
### مصادر رسمية
- [عمليات البحث في Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

<a id="books--guides"></a>
### كتب وأدلة
- "Google Hacking for Penetration Testers" بقلم Johnny Long
- دليل الاختبار من OWASP

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Shodan](../../Shodan/translations/README.ar.md)
- [البحث المتقدم في GitHub](../../GitHub-Dorking/translations/README.ar.md)
- [Subfinder](../../Subfinder/translations/README.ar.md)

---

<p align="center">
  <b>🔍 اكتشاف المعلومات غير الظاهرة بسهولة!</b><br>
  <i>إتقان البحث المتقدم في Google</i>
</p>
