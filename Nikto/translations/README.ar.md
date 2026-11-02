<a id="-nikto---complete-cheatsheet"></a>
# 🕷️ Nikto — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web Server Scanner - Since 2001
```

يصف الشعار Nikto بأنه ماسح لخوادم الويب بدأ منذ عام 2001.

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 ماسح مفتوح المصدر لخوادم الويب مخصص لاختبارات الأمن</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [تحديد الأهداف](#-target-specification)
- [خيارات المنافذ وSSL](#-port--ssl-options)
- [تخصيص الفحص (Scan Tuning)](#-scan-tuning)
- [المصادقة](#-authentication)
- [الوكيل وتفادي الرصد](#-proxy--evasion)
- [المخرجات والتقارير](#-output--reporting)
- [تحديث قواعد البيانات](#-database-updates)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Nikto** ماسح مفتوح المصدر لخوادم الويب، يجري اختبارات واسعة تشمل الجوانب التالية:

<a id="what-nikto-checks"></a>
### ما الذي يفحصه Nikto؟

|الفئة|الوصف|
|----------|-------------|
|**ملفات خطرة**|ملفات قد تشكل خطرًا، مثل النسخ الاحتياطية|
|**إصدارات قديمة**|برامج خادم قديمة|
|**مشكلات مرتبطة بالإصدار**|ثغرات خاصة بإصدارات معينة|
|**إعدادات الخادم**|أخطاء الإعداد|
|**برامج CGI النصية**|برامج CGI قد تكون خطرة|
|**SSL/TLS**|مشكلات شهادات SSL|
|**الترويسات**|ترويسات HTTP مفقودة أو غير آمنة|
|**أساليب HTTP**|أساليب HTTP قد تكون خطرة|

<a id="database-statistics"></a>
### إحصاءات قاعدة البيانات

الأرقام التالية منقولة من الأصل بوصفها إحصاءات مرجعية، وليست تعدادًا مباشرًا للإصدار المثبت لديك. وهي تشير إلى أكثر من 6,700 ملف أو برنامج CGI قد يكون خطرًا، وأكثر من 1,250 إصدار خادم قديم، وأكثر من 270 مشكلة مرتبطة بالإصدار، وأكثر من 1,000 مشكلة إعداد. أُبقي العرض الرقمي الأصلي أدناه.

```
✅ 6,700+ potentially dangerous files/CGIs
✅ 1,250+ outdated server versions
✅ 270+ version-specific problems
✅ 1,000+ server configuration issues
```

<a id="features"></a>
### الميزات

|الميزة|الوصف|
|---------|-------------|
|**تغطية واسعة**|اختبارات لآلاف الثغرات|
|**يعتمد على الإضافات**|بنية قابلة للتوسعة|
|**تنسيقات متعددة**|مخرجات HTML وXML وCSV وJSON|
|**دعم SSL**|اختبار HTTPS|
|**تفادي IDS**|تقنيات مدمجة لتفادي الرصد|
|**المصادقة**|دعم Basic وDigest وNTLM بحسب الأصل|

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### نظام Kali Linux حيث تكون الأداة مثبتة مسبقًا
```bash
# تحديث Nikto
sudo apt update && sudo apt install nikto

# تحديث قاعدة البيانات
nikto -update
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install nikto
```

<a id="from-github"></a>
### التثبيت من GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install nikto
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
nikto -Version
nikto -h          # المساعدة
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
nikto -h <host> [options]
```

<a id="quick-start"></a>
### بدء سريع
```bash
# فحص أساسي
nikto -h http://target.com

# الفحص مع تحديد المنفذ
nikto -h target.com -p 80

# فحص HTTPS
nikto -h https://target.com

# فحص عنوان IP
nikto -h 192.168.1.1
```

---

<a id="-target-specification"></a>
## 🎯 تحديد الأهداف

<a id="single-target"></a>
### هدف واحد
```bash
# باستخدام اسم المضيف
nikto -h target.com

# باستخدام عنوان IP
nikto -h 192.168.1.1

# مع تحديد البروتوكول
nikto -h http://target.com
nikto -h https://target.com
```

<a id="multiple-targets"></a>
### أهداف متعددة
```bash
# من ملف
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

<a id="target-with-path"></a>
### هدف مع مسار
```bash
# فحص مسار محدد
nikto -h http://target.com/app

# فحص مجلد محدد
nikto -h http://target.com/admin/
```

---

<a id="-port--ssl-options"></a>
## 🔌 خيارات المنافذ وSSL

<a id="port-options"></a>
### خيارات المنافذ
```bash
# منفذ واحد
nikto -h target.com -p 80

# منافذ متعددة
nikto -h target.com -p 80,443,8080

# نطاق منافذ
nikto -h target.com -p 80-90

# منافذ ويب شائعة
nikto -h target.com -p 80,443,8000,8080,8443
```

<a id="ssltls-options"></a>
### خيارات SSL/TLS

|الخيار|الوصف|
|--------|-------------|
|`-ssl`|فرض وضع SSL|
|`-nossl`|تعطيل SSL|
|`-maxtime`|المدة القصوى للفحص|

```bash
# فرض استخدام SSL
nikto -h target.com -ssl

# تعطيل SSL واستخدام HTTP فقط
nikto -h target.com -nossl

# استخدام SSL على منفذ غير قياسي
nikto -h target.com -p 8443 -ssl
```

<a id="virtual-hosts"></a>
### المضيفون الافتراضيون (Virtual Hosts)
```bash
# تحديد مضيف افتراضي
nikto -h 192.168.1.1 -vhost target.com

# يفيد في الحالات التالية:
# - الهدف خلف موزع حمل
# - مواقع متعددة تشترك في عنوان IP واحد
```

---

<a id="-scan-tuning"></a>
## 🎚️ تخصيص الفحص (Scan Tuning)

<a id="tuning-options--tuning"></a>
### خيارات تخصيص الفحص (-Tuning)

|الرمز|الوصف|
|------|-------------|
|`0`|رفع الملفات|
|`1`|ملف لافت للاهتمام أو ظهر في السجلات|
|`2`|خطأ إعداد أو ملف افتراضي|
|`3`|كشف المعلومات|
|`4`|الحقن: XSS أو البرامج النصية أو HTML|
|`5`|جلب ملفات عن بُعد ضمن جذر الويب|
|`6`|حجب الخدمة (DoS)|
|`7`|جلب ملفات عن بُعد من نطاق الخادم كله|
|`8`|تنفيذ أوامر أو الحصول على Shell عن بُعد|
|`9`|حقن SQL|
|`a`|تجاوز المصادقة|
|`b`|تحديد البرامج|
|`c`|تضمين مصدر بعيد|
|`d`|خدمات الويب|
|`e`|لوحة الإدارة|
|`x`|عكس خيارات التخصيص للاستبعاد|

<a id="using-tuning"></a>
### استخدام خيارات التخصيص
```bash
# اختبار حقن SQL فقط
nikto -h target.com -Tuning 9

# اختبار XSS وحقن SQL
nikto -h target.com -Tuning 49

# اختبار أنواع متعددة
nikto -h target.com -Tuning 1234

# استبعاد اختبارات محددة باستخدام البادئة x
nikto -h target.com -Tuning x6    # استبعاد اختبارات حجب الخدمة (DoS)

# جميع الاختبارات باستثناء DoS
nikto -h target.com -Tuning x6
```

<a id="plugins"></a>
### الإضافات (Plugins)
```bash
# عرض الإضافات المتاحة
nikto -list-plugins

# تشغيل إضافة محددة
nikto -h target.com -Plugins apacheusers

# تشغيل إضافات متعددة
nikto -h target.com -Plugins "apacheusers;robots"

# استبعاد إضافات
nikto -h target.com -Plugins "@@ALL;-robots"
```

<a id="common-plugins"></a>
### إضافات شائعة

|الإضافة|الوصف|
|--------|-------------|
|`apacheusers`|استكشاف مستخدمي Apache|
|`cgi`|اختبار برامج CGI النصية|
|`robots`|تحليل robots.txt|
|`headers`|تحليل ترويسات HTTP|
|`httpoptions`|اختبار أساليب HTTP|
|`outdated`|فحص البرامج القديمة|
|`ssl`|تحليل SSL/TLS|
|`dictionary`|هجوم باستخدام قائمة كلمات (Dictionary Attack)|

---

<a id="-authentication"></a>
## 🔐 المصادقة

<a id="basic-authentication"></a>
### المصادقة الأساسية (Basic Authentication)

> **تصحيح:** وضع `admin:password` بين علامتي اقتباس لا يحوله إلى Base64. يقبل الخيار `-id` صيغة `id:pass` أو `id:pass:realm` بحسب التوثيق. [قائمة خيارات Nikto الرسمية](https://github.com/sullo/nikto/wiki/Annotated-Option-List).
```bash
# اسم المستخدم وكلمة المرور
nikto -h target.com -id admin:password

# صيغة Base64 بحسب وصف الأصل؛ راجع التصحيح
nikto -h target.com -id "admin:password"
```

<a id="digest-authentication"></a>
### مصادقة Digest
```bash
# مصادقة Digest مع الاكتشاف التلقائي بحسب الأصل
nikto -h target.com -id admin:password
```

<a id="ntlm-authentication"></a>
### مصادقة NTLM
```bash
# مصادقة NTLM
nikto -h target.com -id admin:password:domain
```

<a id="cookie-based-authentication"></a>
### المصادقة باستخدام ملفات تعريف الارتباط (Cookies)

> **توافق الإصدارات:** أمثلة `-cookies` ليست ضمن قائمة الخيارات الرسمية الحالية. يوثق المشروع إعداد `STATIC-COOKIE` في ملف الإعدادات؛ لا تفترض أن تمرير `cookies.txt` يعمل بهذه الصيغة. أُبقي المثال الأصلي دون تغيير. [متغيرات إعداد Nikto](https://github.com/sullo/nikto/wiki/Config-Variables).
```bash
# إرسال ملفات تعريف الارتباط
nikto -h target.com -cookies "session=abc123; token=xyz789"

# من ملف
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy--evasion"></a>
## 🛡️ الوكيل وتفادي الرصد

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)
```bash
# وكيل HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# وكيل يتطلب المصادقة
nikto -h target.com -useproxy http://user:pass@proxy:8080

# عبر Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

<a id="ids-evasion--evasion"></a>
### تفادي أنظمة كشف التسلل IDS باستخدام ‎-evasion

|الرمز|التقنية|
|------|-----------|
|`1`|ترميز URI عشوائي بغير UTF8|
|`2`|إشارة المجلد إلى نفسه (/./)|
|`3`|إنهاء URL مبكرًا|
|`4`|إضافة سلسلة عشوائية طويلة في البداية|
|`5`|معلمة مزيفة|
|`6`|استخدام TAB فاصلًا في الطلب|
|`7`|تغيير حالة الأحرف في URL|
|`8`|استخدام فاصل مجلدات Windows (\)|
|`A`|استخدام محرف الرجوع إلى أول السطر (0x0d)|
|`B`|استخدام القيمة الثنائية 0x0b|

```bash
# تقنية واحدة لتفادي الرصد
nikto -h target.com -evasion 1

# تقنيات متعددة لتفادي الرصد
nikto -h target.com -evasion 1234

# جميع تقنيات تفادي الرصد بحسب خيارات المثال
nikto -h target.com -evasion 12345678AB
```

<a id="user-agent"></a>
### User-Agent
```bash
# تحديد ترويسة User-Agent مخصصة
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

<a id="request-timing"></a>
### توقيت الطلبات
```bash
# التوقف بين الطلبات بالثواني
nikto -h target.com -Pause 2

# المدة القصوى للفحص بالثواني
nikto -h target.com -maxtime 3600

# مهلة الانتظار لكل طلب
nikto -h target.com -timeout 10
```

---

<a id="-output--reporting"></a>
## 📄 المخرجات والتقارير

<a id="output-formats"></a>
### تنسيقات المخرجات

|الخيار|التنسيق|
|--------|--------|
|`-o file`|حفظ المخرجات في ملف|
|`-Format htm`|تنسيق HTML|
|`-Format xml`|تنسيق XML|
|`-Format csv`|تنسيق CSV|
|`-Format txt`|تنسيق نصي|
|`-Format json`|تنسيق JSON|
|`-Format nbe`|تنسيق NBE بحسب الأصل|
|`-Format sql`|تنسيق SQL|

<a id="save-results"></a>
### حفظ النتائج
```bash
# مخرجات نصية
nikto -h target.com -o report.txt

# تقرير HTML
nikto -h target.com -o report.html -Format htm

# تقرير XML
nikto -h target.com -o report.xml -Format xml

# تقرير JSON
nikto -h target.com -o report.json -Format json

# تقرير CSV
nikto -h target.com -o report.csv -Format csv
```

<a id="verbosity-options"></a>
### خيارات تفصيل المخرجات
```bash
# خيارات العرض
nikto -h target.com -Display V    # عرض تفصيلي
nikto -h target.com -Display D    # تنقيح الأخطاء
nikto -h target.com -Display E    # أخطاء HTTP
nikto -h target.com -Display P    # تقدم الفحص
nikto -h target.com -Display S    # إخفاء عناوين IP من المخرجات
nikto -h target.com -Display 1    # عرض عمليات إعادة التوجيه
nikto -h target.com -Display 2    # عرض ملفات تعريف الارتباط
nikto -h target.com -Display 3    # عرض استجابات 200/OK
nikto -h target.com -Display 4    # عرض عناوين URL التي تتطلب المصادقة

# خيارات عرض متعددة
nikto -h target.com -Display VEP
```

<a id="save-progress"></a>
### حفظ تقدم الفحص بحسب وصف الأصل

> **تصحيح:** يحفظ `-Save` الاستجابات والطلبات المرتبطة بالنتائج في مجلد، وليس حالة فحص قابلة للاستئناف. لا توثق قائمة الخيارات الحالية `-Resume`. أُبقي السطران الأصليان للتوثيق. [توثيق الحفظ وإعادة الطلبات](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-&-Replaying)، [قائمة الخيارات](https://github.com/sullo/nikto/wiki/Annotated-Option-List).
```bash
# حفظ حالة الفحص للاستئناف بحسب وصف الأصل؛ راجع التصحيح
nikto -h target.com -Save scan_state.txt

# استئناف الفحص بحسب وصف الأصل
nikto -Resume scan_state.txt
```

---

<a id="-database-updates"></a>
## 🔄 تحديث قواعد البيانات

> **ملاحظة إصدار:** خيار `-update` قديم؛ يذكر المشروع أن آلية التحديث تغيرت بدءًا من `2.1.6`. روابط `2.1.5` في الأصل أمثلة تاريخية وليست قاعدة تحديث عامة للإصدارات الحديثة. أُبقيت الأوامر كما وردت دون تنفيذها. [دليل التحديث الرسمي](https://github.com/sullo/nikto/wiki/Updating).

<a id="update-nikto-database"></a>
### تحديث قاعدة بيانات Nikto
```bash
# تحديث جميع قواعد البيانات
nikto -update

# تحديث يدوي عند فشل ‎-update بحسب المثال القديم
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

<a id="database-location"></a>
### موقع قاعدة البيانات
```bash
# المواقع الافتراضية
/usr/share/nikto/databases/
/var/lib/nikto/

# ملفات قاعدة البيانات
db_tests         # قاعدة الاختبارات الرئيسية
db_outdated      # إصدارات البرامج القديمة
db_server_msgs   # رسائل الخادم
db_variables     # المتغيرات
db_404_strings   # السلاسل النصية لصفحات 404
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-basic-web-server-scan"></a>
### المثال 1: فحص أساسي لخادم ويب
```bash
nikto -h http://target.com
```

<a id="example-2-comprehensive-https-scan"></a>
### المثال 2: فحص شامل عبر HTTPS
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

<a id="example-3-scan-multiple-ports"></a>
### المثال 3: فحص منافذ متعددة
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

<a id="example-4-authenticated-scan"></a>
### المثال 4: فحص مع المصادقة
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

<a id="example-5-stealth-scan-ids-evasion"></a>
### المثال 5: فحص بتقنيات تفادي IDS بحسب وصف الأصل
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

<a id="example-6-through-burp-proxy"></a>
### المثال 6: الفحص عبر وكيل Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

<a id="example-7-sql-injection-focus"></a>
### المثال 7: التركيز على حقن SQL
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

<a id="example-8-full-audit"></a>
### المثال 8: تدقيق شامل
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

<a id="example-9-virtual-host-testing"></a>
### المثال 9: اختبار مضيف افتراضي
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

<a id="example-10-scan-from-target-file"></a>
### المثال 10: الفحص من ملف أهداف
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

|المهمة|الأمر|
|------|---------|
|فحص أساسي|`nikto -h target.com`|
|فحص HTTPS|`nikto -h target.com -ssl`|
|منافذ متعددة|`nikto -h target.com -p 80,443,8080`|
|مع المصادقة|`nikto -h target.com -id user:pass`|
|تقرير HTML|`nikto -h target.com -o report.html -Format htm`|
|تفادي الرصد|`nikto -h target.com -evasion 123`|
|عبر وكيل|`nikto -h target.com -useproxy http://127.0.0.1:8080`|
|تحديث قاعدة البيانات|`nikto -update`|

<a id="common-options"></a>
### خيارات شائعة

|الخيار|الوصف|
|--------|-------------|
|`-h`|المضيف المستهدف|
|`-p`|المنافذ المراد فحصها|
|`-ssl`|فرض وضع SSL|
|`-id`|بيانات اعتماد المصادقة|
|`-o`|ملف المخرجات|
|`-Format`|تنسيق المخرجات|
|`-Tuning`|تخصيص الفحص|
|`-evasion`|تفادي أنظمة كشف التسلل|
|`-useproxy`|خادم الوكيل|
|`-vhost`|مضيف افتراضي|
|`-Pause`|التأخير بين الاختبارات|
|`-timeout`|مهلة انتظار الطلب|

<a id="tuning-quick-reference"></a>
### مرجع سريع لتخصيص الفحص

|الرمز|الفئة|
|------|----------|
|1-4|معلومات أو أخطاء إعداد|
|4-5|حقن أو جلب ملفات|
|6|حجب الخدمة؛ تجنب اختباره في الإنتاج|
|7-8|وصول عن بُعد|
|9|حقن SQL|
|a|تجاوز المصادقة|
|b|تحديد البرامج|
|x|استبعاد|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح للأداء

1. **ابدأ بفحص أساسي**
   ```bash
   nikto -h target.com
   ```

2. **استبعد اختبارات حجب الخدمة**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **استخدم مهل انتظار مناسبة**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

<a id="accuracy-tips"></a>
### نصائح للدقة

1. **حدّث قاعدة البيانات بانتظام**
   ```bash
   nikto -update
   ```

2. **استخدم وضع المخرجات التفصيلية**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **ادمج النتائج مع أدوات أخرى**
   ```bash
   # استخدام Nmap أولًا ثم Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

<a id="common-issues"></a>
### مشكلات شائعة

|المشكلة|الحل|
|-------|----------|
|بطء الفحص|تقليل `-Pause` وزيادة `-timeout` بحسب سبب البطء|
|أخطاء SSL|استخدام الخيار `-ssl` صراحة|
|مشكلات المصادقة|فحص صيغة `-id`|
|نتائج مفقودة|تحديث قاعدة البيانات وفحص الإضافات|

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** Nikto ماسح لخوادم الويب، وينبغي استخدامه فقط في **اختبارات مصرح بها**.
> 
> - ✅ افحص الخوادم التي تملكها
> - ✅ افحص بتفويض مكتوب وصريح
> - ✅ استخدمه ضمن اختبارات اختراق مصرح بها
> - ❌ لا تفحص دون تفويض
> - ❌ الفحص غير المصرح به مخالف للقانون بحسب تنبيه الأصل
> 
> **قد يولد Nikto قدرًا كبيرًا من حركة الشبكة، وقد يؤدي إلى تنبيهات أمنية.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [مستودع Nikto على GitHub](https://github.com/sullo/nikto)
- [توثيق Nikto](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

<a id="learning-resources"></a>
### مصادر التعلم
- [دليل OWASP للاختبار](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Nmap](../../Nmap/translations/README.ar.md)
- [Gobuster](../../Gobuster/translations/README.ar.md)
- [أداة Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ أتقن فحص خوادم الويب!</b><br>
  <i>اكتشف الثغرات قبل أن يكتشفها المهاجمون</i>
</p>
