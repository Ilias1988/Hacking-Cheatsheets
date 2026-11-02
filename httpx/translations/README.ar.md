<a id="-httpx---complete-cheatsheet"></a>
# 🌐 httpx — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Fast HTTP Probe & Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ مجموعة أدوات HTTP سريعة ومتعددة الاستخدامات من ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [اختبار الاستجابة والاكتشاف](#-probing--discovery)
- [استخراج المعلومات](#-information-extraction)
- [التصفية والمطابقة](#-filtering--matching)
- [خيارات المخرجات](#-output-options)
- [الميزات المتقدمة](#-advanced-features)
- [سير عمل برامج مكافآت الثغرات](#-bug-bounty-workflow)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**httpx** مجموعة أدوات HTTP سريعة ومتعددة الاستخدامات، تتيح تنفيذ اختبارات استجابة متعددة باستخدام مكتبة retryablehttp. صُممت للباحثين ضمن برامج مكافآت الثغرات والباحثين الأمنيين.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **سريعة** | أداة مبنية بلغة Go وتدعم تزامناً واسعاً |
| **اختبارات استجابة متعددة** | رمز الحالة والعنوان والتقنيات والترويسات |
| **تحديد التقنيات** | تحديد يعتمد على Wappalyzer |
| **لقطة الشاشة** | لقطات شاشة عبر متصفح دون واجهة |
| **سلسلة الأوامر** | تتكامل مع subfinder وnuclei |
| **مخرجات JSON** | دعم المخرجات المنظمة |

<a id="httpx-vs-other-tools"></a>
### مقارنة httpx بأدوات أخرى

> **تصحيح للمقارنة:** جدول الأصل مبسّط وليس قياس أداء. يدعم curl النقل المتوازي عبر `--parallel` وفق [دليله الرسمي](https://curl.se/docs/manpage.html)، لذا علامة «لا» في صف التزامن ليست حكماً عاماً على قدراته.


| الميزة | httpx | curl | wget |
|---------|-------|------|------|
| **السرعة** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **التزامن** | ✅ نعم | ❌ لا | ❌ لا |
| **تحديد التقنيات** | ✅ نعم | ❌ لا | ❌ لا |
| **لقطات الشاشة** | ✅ نعم | ❌ لا | ❌ لا |
| **سلسلة الأوامر** | ✅ نعم | ⚠️ يدوي | ⚠️ يدوي |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="using-go"></a>
### باستخدام Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install httpx-toolkit
```

<a id="binary-download"></a>
### تنزيل الملف التنفيذي

> **ملاحظة:** `v1.x.x` في رابط الأرشيف قيمة توضيحية، وليس اسم إصدار قابل للتنزيل كما هو. بقية خيارات الأمثلة، ومنها حفظ اللقطات والاستئناف، تحتاج مطابقة مساعدة النسخة المثبتة.

```bash
# التنزيل من صفحة الإصدارات
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
httpx -version
httpx -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

<a id="essential-options"></a>
### الخيارات الضرورية

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL لهدف واحد |
| `-l` | ملف يتضمن قائمة عناوين URL |
| `-sc` | عرض رمز الحالة |
| `-title` | عرض عنوان الصفحة |
| `-td` | تحديد التقنيات |
| `-o` | ملف المخرجات |
| `-silent` | الوضع الصامت |
| `-json` | مخرجات JSON |

<a id="quick-start"></a>
### بداية سريعة
```bash
# اختبار استجابة عنوان URL واحد
httpx -u https://example.com

# اختبار العناوين من ملف
httpx -l urls.txt

# من الإدخال القياسي ضمن سلسلة أوامر
cat hosts.txt | httpx

# مع subfinder
subfinder -d example.com -silent | httpx
```

---

<a id="-probing--discovery"></a>
## 🔍 اختبار الاستجابة والاكتشاف

<a id="basic-probing"></a>
### اختبار الاستجابة الأساسي

```bash
# اختبار استجابة الأجهزة عبر HTTP/HTTPS
cat hosts.txt | httpx

# تضمين رمز الحالة
cat hosts.txt | httpx -sc

# اختبار منافذ محددة
cat hosts.txt | httpx -p 80,443,8080,8443

# اختبار المنافذ الشائعة في المثال
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

<a id="port-scanning"></a>
### فحص المنافذ

```bash
# منافذ مخصصة
httpx -l hosts.txt -p 80,443,8080,8443

# نطاق منافذ
httpx -l hosts.txt -p 80-100

# منافذ ويب شائعة
httpx -l hosts.txt -p http:80,https:443
```

<a id="protocol-detection"></a>
### تحديد البروتوكول

```bash
# اختبار HTTP/HTTPS
cat hosts.txt | httpx

# اتباع عمليات إعادة التوجيه
cat hosts.txt | httpx -follow-redirects

# أقصى عدد لإعادات التوجيه
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# دون رجوع تلقائي وفق تعليق الأصل
cat hosts.txt | httpx -no-fallback
```

---

<a id="-information-extraction"></a>
## 📊 استخراج المعلومات

<a id="status-codes--titles"></a>
### رموز الحالة والعناوين

```bash
# رمز الحالة
httpx -l urls.txt -sc

# عنوان الصفحة
httpx -l urls.txt -title

# كلاهما
httpx -l urls.txt -sc -title

# طول المحتوى
httpx -l urls.txt -cl

# خادم الويب
httpx -l urls.txt -server

# جميع المعلومات الأساسية
httpx -l urls.txt -sc -title -cl -server
```

<a id="technology-detection"></a>
### تحديد التقنيات

```bash
# تحديد التقنيات
httpx -l urls.txt -td

# تحديد التقنيات بمخرجات مفصلة وفق الأصل
httpx -l urls.txt -tech-detect

# مع معلومات إضافية
httpx -l urls.txt -sc -title -td
```

<a id="headers--response"></a>
### الترويسات والاستجابة

> **تصحيح:** يضيف `-include-response-header` الترويسات إلى مخرجات JSON، وهو مفتاح تفعيل وليس قائمة أسماء ترويسات. لذلك لا يحدد النص `Server,X-Powered-By` في مثال الأصل حقولاً بهذا الأسلوب. [المصدر الرسمي](https://github.com/projectdiscovery/httpx).


```bash
# ترويسات الاستجابة
httpx -l urls.txt -response-header

# ترويسة محددة
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# بصمة جسم الاستجابة
httpx -l urls.txt -hash md5

# معاينة جسم الاستجابة
httpx -l urls.txt -bp
```

<a id="ip--dns-information"></a>
### معلومات IP وDNS

```bash
# عنوان IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# تحديد شبكة توصيل المحتوى (CDN)
httpx -l urls.txt -cdn

# معلومات رقم النظام المستقل (ASN)
httpx -l urls.txt -asn

# جميع معلومات DNS في المثال
httpx -l urls.txt -ip -cname -cdn -asn
```

<a id="ssltls-information"></a>
### معلومات SSL/TLS

```bash
# اختبار TLS
httpx -l urls.txt -tls-probe

# جمع معلومات TLS
httpx -l urls.txt -tls-grab

# معلومات الشهادة
httpx -l urls.txt -tlsgrab
```

<a id="favicon-hash"></a>
### بصمة أيقونة الموقع (Favicon)

```bash
# حساب بصمة أيقونة الموقع للاستفادة منها في بحث Shodan
httpx -l urls.txt -favicon
```

<a id="screenshot-capture"></a>
### التقاط صور الشاشة

```bash
# التقاط صور الشاشة
httpx -l urls.txt -screenshot

# لقطات شاشة مع مجلد للمخرجات
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# خيارات المتصفح دون واجهة
httpx -l urls.txt -screenshot -system-chrome
```

---

<a id="-filtering--matching"></a>
## 🎯 التصفية والمطابقة

<a id="filter-by-status-code"></a>
### التصفية بحسب رمز الحالة

```bash
# مطابقة رموز الحالة
httpx -l urls.txt -mc 200,301,302

# استبعاد رموز الحالة
httpx -l urls.txt -fc 404,403,500

# مطابقة الجميع واستبعاد رموز محددة
httpx -l urls.txt -mc all -fc 404
```

<a id="filter-by-size"></a>
### التصفية بحسب الحجم

```bash
# مطابقة طول المحتوى
httpx -l urls.txt -ml 1234

# استبعاد طول محتوى محدد
httpx -l urls.txt -fl 0

# مطابقة عدد الأسطر
httpx -l urls.txt -mlc 100

# استبعاد عدد أسطر محدد
httpx -l urls.txt -flc 50
```

<a id="filter-by-content"></a>
### التصفية بحسب المحتوى

> **تصحيح للعلم:** توثق [مساعدة httpx الرسمية](https://github.com/projectdiscovery/httpx) الخيار `-fe` لاستبعاد الاستجابات بتعبير نمطي، بينما `-fr` يعني اتباع إعادة التوجيه. مثال الأصل يستخدم `-fr` تحت وصف مرشح regex؛ حُفظ دون تعديل تنفيذي.


```bash
# مطابقة جسم الاستجابة بتعبير نمطي
httpx -l urls.txt -mr "admin|login"

# استبعاد جسم الاستجابة بتعبير نمطي وفق تعليق الأصل
httpx -l urls.txt -fr "error|not found"

# مطابقة سلسلة نصية
httpx -l urls.txt -ms "dashboard"
```

<a id="filter-by-technology"></a>
### التصفية بحسب التقنية

> **ملاحظة توافق:** احتُفظ بخياري `-mt` و`-ft` كما في الأصل. طابق أسماء خيارات المطابقة والتصفية مع [مساعدة الإصدار](https://github.com/projectdiscovery/httpx)؛ لا تفترض أن أسماء الخيارات متطابقة بين الأدوات أو الإصدارات.


```bash
# مطابقة تقنية
httpx -l urls.txt -td -mt "WordPress,Nginx"

# استبعاد تقنية
httpx -l urls.txt -td -ft "Apache"
```

---

<a id="-output-options"></a>
## 📤 خيارات المخرجات

<a id="output-formats"></a>
### صيغ المخرجات

> **توضيح تنسيق:** يكتب `-json` كائنات بصيغة JSON Lines، لا مصفوفة JSON واحدة. راجع [المساعدة الرسمية](https://github.com/projectdiscovery/httpx) لمطابقة خيارات `-jsonl` وغيرها في نسختك؛ امتداد `.json` وحده لا يحدد بنية الملف.


```bash
# نص عادي
httpx -l urls.txt -o results.txt

# مخرجات JSON
httpx -l urls.txt -json -o results.json

# مخرجات JSON Lines
httpx -l urls.txt -jsonl -o results.jsonl

# مخرجات CSV
httpx -l urls.txt -csv -o results.csv
```

<a id="output-fields"></a>
### حقول المخرجات

```bash
# حفظ الاستجابة
httpx -l urls.txt -sr -srd ./responses/

# حفظ لقطات الشاشة
httpx -l urls.txt -screenshot -ssd ./screenshots/

# مخرجات مخصصة
httpx -l urls.txt -o output.txt -sc -title -td
```

<a id="silent--verbose"></a>
### المخرجات الصامتة والمفصلة

```bash
# وضع صامت لعناوين URL فقط
httpx -l urls.txt -silent

# مخرجات مفصلة
httpx -l urls.txt -v

# التنقيح
httpx -l urls.txt -debug

# دون ألوان
httpx -l urls.txt -nc
```

---

<a id="-advanced-features"></a>
## ⚡ الميزات المتقدمة

<a id="threading--rate-limiting"></a>
### خيوط التنفيذ وتحديد معدل الطلبات

```bash
# ضبط خيوط التنفيذ، الافتراضي 50 وفق الأصل
httpx -l urls.txt -threads 100

# تحديد معدل الطلبات
httpx -l urls.txt -rate-limit 100

# المهلة
httpx -l urls.txt -timeout 10

# إعادات المحاولة
httpx -l urls.txt -retries 3
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)

```bash
# وكيل HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# وكيل SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

<a id="custom-headers"></a>
### ترويسات مخصصة

```bash
# ترويسة مخصصة
httpx -l urls.txt -H "Authorization: Bearer token123"

# عدة ترويسات
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# ترويسة User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

<a id="request-methods"></a>
### طرق طلب HTTP

> **تصحيح:** في [الخيارات الرسمية](https://github.com/projectdiscovery/httpx)، يعرض `-method` طريقة الطلب، بينما يحدد `-x` الطرق المطلوب اختبارها. لا تُفهم أمثلة `-method HEAD/OPTIONS/POST` في الأصل على أنها صياغة مضمونة للإصدار الحالي.


```bash
# طلب HEAD، أسرع وفق وصف الأصل
httpx -l urls.txt -method HEAD

# طريقة طلب مخصصة
httpx -l urls.txt -method OPTIONS

# طلب POST مع جسم
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

<a id="resume--pagination"></a>
### الاستئناف وتقسيم النتائج إلى صفحات

```bash
# الاستئناف من آخر موضع
httpx -l urls.txt -resume

# حفظ التقدم
httpx -l urls.txt -resume -resume-file progress.txt
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 سير عمل برامج مكافآت الثغرات

<a id="complete-recon-pipeline"></a>
### سلسلة استطلاع كاملة

```bash
# الخطوة 1: حصر النطاقات الفرعية
subfinder -d target.com -o subs.txt

# الخطوة 2: اختبار الأجهزة المستجيبة باستخدام httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# الخطوة 3: فحص الثغرات
nuclei -l live.txt -severity critical,high

# سطر واحد
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

<a id="detailed-reconnaissance"></a>
### استطلاع مفصل

```bash
# جمع المعلومات الشامل
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

<a id="screenshot-all-live-hosts"></a>
### التقاط صور لجميع الأجهزة المستجيبة

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

<a id="find-interesting-endpoints"></a>
### العثور على نقاط نهاية تستحق المراجعة

```bash
# العثور على لوحات الإدارة
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# العثور على نقاط نهاية API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# فحص مسارات محددة
httpx -l hosts.txt -path /robots.txt -mc 200
```

<a id="technology-specific-hunting"></a>
### البحث الموجّه بحسب التقنية

```bash
# العثور على مواقع WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# العثور على خوادم Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-basic-probe"></a>
### المثال 1: اختبار استجابة أساسي
```bash
echo "https://example.com" | httpx -sc -title
```

<a id="example-2-full-recon-from-subdomains"></a>
### المثال 2: استطلاع شامل من النطاقات الفرعية
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

<a id="example-3-screenshot-all-hosts"></a>
### المثال 3: التقاط صور لجميع الأجهزة
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

<a id="example-4-find-live-hosts-only"></a>
### المثال 4: العثور على الأجهزة المستجيبة فقط
```bash
cat subs.txt | httpx -silent -o live.txt
```

<a id="example-5-technology-detection"></a>
### المثال 5: تحديد التقنيات
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

<a id="example-6-with-proxy-burp"></a>
### المثال 6: عبر وكيل Burp
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

<a id="example-7-custom-ports"></a>
### المثال 7: منافذ مخصصة
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

<a id="example-8-filter-200-only"></a>
### المثال 8: نتائج الحالة 200 فقط
```bash
cat subs.txt | httpx -mc 200 -silent
```

<a id="example-9-json-output-for-processing"></a>
### المثال 9: مخرجات JSON للمعالجة
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

<a id="example-10-complete-pipeline"></a>
### المثال 10: سلسلة كاملة
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| المهمة | الأمر |
|------|---------|
| اختبار استجابة أساسي | `httpx -u url` |
| من ملف | `httpx -l urls.txt` |
| رمز الحالة | `httpx -l urls.txt -sc` |
| عنوان الصفحة | `httpx -l urls.txt -title` |
| تحديد التقنيات | `httpx -l urls.txt -td` |
| عنوان IP | `httpx -l urls.txt -ip` |
| لقطة الشاشة | `httpx -l urls.txt -screenshot` |
| مخرجات JSON | `httpx -l urls.txt -json -o out.json` |

<a id="common-options"></a>
### خيارات شائعة

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL واحد |
| `-l` | ملف قائمة عناوين URL |
| `-sc` | رمز الحالة |
| `-title` | عنوان الصفحة |
| `-td` | تحديد التقنيات |
| `-ip` | عنوان IP |
| `-cdn` | تحديد شبكة توصيل المحتوى (CDN) |
| `-server` | خادم الويب |
| `-mc` | مطابقة رموز الحالة |
| `-fc` | استبعاد رموز الحالة |
| `-json` | مخرجات JSON |
| `-silent` | الوضع الصامت |

<a id="common-pipelines"></a>
### سلاسل أوامر شائعة

| سلسلة الأوامر | الأمر |
|----------|---------|
| الأجهزة المستجيبة | `subfinder -d target.com \| httpx -silent` |
| مع معلومات | `subfinder -d target.com \| httpx -sc -title -td` |
| التمرير إلى nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="bug-bounty-tips"></a>
### نصائح لبرامج مكافآت الثغرات

1. **الاستخدام مع Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **جمع صورة شاملة**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **لقطات شاشة للمراجعة الأولية**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **مخرجات JSON للمعالجة**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

<a id="performance-tips"></a>
### نصائح الأداء

```bash
# سرعة مرتفعة
httpx -l hosts.txt -threads 100 -timeout 5

# معدل منخفض وفحص بطيء
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** يرسل httpx طلبات HTTP إلى الأهداف؛ استخدمه على الأنظمة المصرح لك باختبارها فقط.
> 
> - ✅ استخدمه على البرامج التي لديك إذن باختبارها
> - ✅ أهداف برامج مكافآت الثغرات الواقعة ضمن النطاق
> - ✅ نطاقاتك الخاصة
> - ❌ لا تختبر استجابة أهداف غير مصرح بها
> - ❌ التزم بحدود معدل الطلبات
> 
> **قد يكون اختبار استجابة أهداف دون تصريح مخالفاً للقانون.**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-resources"></a>
### المراجع الرسمية
- [مستودع httpx على GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [التوثيق](https://docs.projectdiscovery.io/tools/httpx/)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [Subfinder](../../Subfinder/translations/README.ar.md)
- [Nuclei](../../Nuclei/translations/README.ar.md)
- [ffuf](../../ffuf/translations/README.ar.md)

---

<p align="center">
  <b>🌐 أتقن اختبار استجابة الأهداف!</b><br>
  <i>مجموعة أدوات HTTP أساسية لبرامج مكافآت الثغرات</i>
</p>
