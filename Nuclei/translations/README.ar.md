<a id="-nuclei---complete-cheatsheet"></a>
# 🎯 Nuclei — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Fast Template-Based Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Templates">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 فاحص ثغرات متقدم قائم على القوالب للباحثين ضمن برامج مكافآت الثغرات</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [فئات القوالب](#-template-categories)
- [فحص الأهداف](#-scanning-targets)
- [اختيار القوالب](#-template-selection)
- [خيارات المخرجات](#-output-options)
- [الميزات المتقدمة](#-advanced-features)
- [القوالب المخصصة](#-custom-templates)
- [سير عمل برامج مكافآت الثغرات](#-bug-bounty-workflow)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Nuclei** فاحص ثغرات سريع يعتمد على القوالب، تطوره ProjectDiscovery، ويستخدمه باحثو برامج مكافآت الثغرات والباحثون الأمنيون.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **أكثر من 9000 قالب وفق الأصل** | ثغرات CVE ولوحات إدارة وإعدادات خاطئة واحتمالات استيلاء |
| **سريع وفعال** | فحص متوازٍ مع تحديد معدل الطلبات |
| **قائم على القوالب** | قوالب YAML سهلة التخصيص |
| **يشارك المجتمع في تطويره** | قوالب تُحدّث باستمرار |
| **بروتوكولات متعددة** | HTTP وDNS وTCP وSSL ومتصفح دون واجهة (Headless) |
| **سهل التكامل** | يتكامل مع أدوات مثل httpx وsubfinder |

<a id="template-types"></a>
### أنواع القوالب

| النوع | الوصف | العدد |
|------|-------------|-------|
| **CVE** | ثغرات معروفة | 2000+ |
| **لوحات الإدارة المكشوفة** | لوحات الإدارة ولوحات المعلومات | 500+ |
| **إعدادات خاطئة** | إعدادات أمنية خاطئة | 300+ |
| **بيانات الدخول الافتراضية** | بيانات اعتماد افتراضية | 200+ |
| **احتمالات الاستيلاء** | الاستيلاء على نطاق فرعي | 100+ |
| **التقنيات** | تحديد التقنيات | 1000+ |
| **انكشاف الملفات والبيانات** | انكشاف ملفات حساسة | 500+ |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="using-go-recommended"></a>
### باستخدام Go (موصى به في الأصل)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

<a id="binary-download"></a>
### تنزيل الملف التنفيذي
```bash
# التنزيل من صفحة الإصدارات
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install nuclei
```

<a id="update-templates"></a>
### تحديث القوالب

> **تصحيح:** الخياران `-ut` و`-update-templates` اسمان لتحديث القوالب، وليس تحديث محرك Nuclei معاً كما يوحي تعليق الأصل. يفصل [التوثيق الرسمي](https://github.com/projectdiscovery/nuclei) بينهما وبين تحديث المحرك عبر `-update`. أُبقيت الأوامر كما وردت.

```bash
# تحديث Nuclei والقوالب وفق تعليق الأصل
nuclei -ut

# تحديث القوالب فقط
nuclei -update-templates
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
nuclei -version
nuclei -tl              # عرض القوالب
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
nuclei [options] -target <url>
```

<a id="essential-options"></a>
### الخيارات الضرورية

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL لهدف واحد |
| `-l` | ملف يتضمن قائمة عناوين URL |
| `-t` | القوالب المطلوب تشغيلها |
| `-tags` | قوالب ذات وسوم محددة |
| `-severity` | التصفية بحسب الخطورة |
| `-o` | ملف المخرجات |
| `-silent` | الوضع الصامت |
| `-v` | مخرجات مفصلة |

<a id="quick-start"></a>
### بداية سريعة
```bash
# فحص هدف واحد
nuclei -u https://example.com

# فحص قائمة أهداف
nuclei -l urls.txt

# الفحص بقوالب محددة
nuclei -u https://example.com -t cves/

# الفحص بحسب الوسوم
nuclei -u https://example.com -tags cve,rce
```

---

<a id="-template-categories"></a>
## 📂 فئات القوالب

> **ملاحظة إصدار:** أعداد القوالب في الجداول والشارات من الأصل وليست إحصاءً حالياً. ينظم [مستودع القوالب الرسمي](https://github.com/projectdiscovery/nuclei-templates) الملفات ضمن بروتوكولات مثل `http/`؛ تحقّق من مسارات أمثلة `cves/` وغيرها حسب نسخة القوالب. قيم مثل `v3.x.x` و`CVE-2023-XXXXX.yaml` حقول توضيحية وليست أسماء تنزيل أو قوالب مضمونة.


<a id="cve-templates"></a>
### قوالب CVE

```bash
# جميع قوالب CVE
nuclei -u target.com -t cves/

# سنة CVE محددة
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# ثغرات CVE الحرجة فقط
nuclei -u target.com -t cves/ -severity critical

# معرّف CVE محدد
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

<a id="exposed-panels"></a>
### لوحات الإدارة المكشوفة

```bash
# جميع قوالب اكتشاف اللوحات
nuclei -u target.com -t exposed-panels/

# لوحات محددة
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

<a id="misconfigurations"></a>
### إعدادات خاطئة

```bash
# جميع قوالب الإعدادات الخاطئة
nuclei -u target.com -t misconfiguration/

# أنواع محددة
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

<a id="default-logins"></a>
### بيانات الدخول الافتراضية

```bash
# جميع قوالب بيانات الاعتماد الافتراضية
nuclei -u target.com -t default-logins/

# جهات مصنّعة محددة
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

<a id="subdomain-takeover"></a>
### الاستيلاء على النطاقات الفرعية

```bash
# جميع فحوصات احتمال الاستيلاء
nuclei -u target.com -t takeovers/

# مزودون محددون
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

<a id="technology-detection"></a>
### تحديد التقنيات

```bash
# تحديد التقنيات
nuclei -u target.com -t technologies/

# تحديد نظام إدارة المحتوى CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

<a id="exposures"></a>
### انكشاف الملفات والبيانات

```bash
# انكشاف ملفات حساسة
nuclei -u target.com -t exposures/

# انكشافات محددة
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

<a id="-scanning-targets"></a>
## 🎯 فحص الأهداف

<a id="single-target"></a>
### هدف واحد

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

<a id="multiple-targets"></a>
### أهداف متعددة

```bash
# من ملف
nuclei -l targets.txt

# من الإدخال القياسي ضمن سلسلة أوامر
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# عدة عناوين URL في سطر الأوامر
nuclei -u https://site1.com -u https://site2.com
```

<a id="with-other-tools"></a>
### التكامل مع أدوات أخرى

```bash
# مع subfinder للنطاقات الفرعية
subfinder -d example.com | nuclei

# مع httpx للأجهزة المستجيبة
subfinder -d example.com | httpx | nuclei

# مع katana لعناوين URL الناتجة عن الزحف
katana -u https://example.com | nuclei

# سلسلة استطلاع كاملة
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-template-selection"></a>
## 🏷️ اختيار القوالب

<a id="by-template-path"></a>
### حسب مسار القالب

```bash
# قالب واحد
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# مجلد قوالب
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# عدة مجلدات
nuclei -u target.com -t cves/ -t exposed-panels/

# مسار قوالب مخصص
nuclei -u target.com -t /path/to/custom/templates/
```

<a id="by-tags"></a>
### حسب الوسوم

```bash
# وسم واحد
nuclei -u target.com -tags cve

# عدة وسوم تربط بينها OR
nuclei -u target.com -tags cve,rce,sqli

# وسوم شائعة
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

<a id="by-severity"></a>
### حسب الخطورة

```bash
# مستوى خطورة واحد
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# عدة مستويات خطورة
nuclei -u target.com -severity critical,high

# استبعاد مستويات خطورة
nuclei -u target.com -exclude-severity info,low
```

<a id="exclude-templates"></a>
### استبعاد القوالب

```bash
# استبعاد قوالب محددة
nuclei -u target.com -exclude-templates cves/2020/

# الاستبعاد بحسب الوسوم
nuclei -u target.com -exclude-tags dos,fuzz

# الاستبعاد بحسب المعرف
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

<a id="-output-options"></a>
## 📤 خيارات المخرجات

<a id="output-formats"></a>
### صيغ المخرجات

> **ملاحظة توافق:** يوضح [المصدر الرسمي الحالي](https://github.com/projectdiscovery/nuclei) أن `-jsonl` يكتب JSON Lines، و`-json-export` يصدّر JSON. احتُفظ بأمثلة `-json` من الأصل؛ طابق خيارات إصدارك ولا تعتمد على امتداد الملف وحده لتحديد بنيته.


```bash
# مخرجات عادية
nuclei -u target.com -o results.txt

# مخرجات JSON
nuclei -u target.com -json -o results.json

# مخرجات JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# مخرجات Markdown
nuclei -u target.com -markdown-export report/
```

<a id="output-filtering"></a>
### تصفية المخرجات

```bash
# الوضع الصامت لعرض النتائج فقط
nuclei -u target.com -silent

# دون ألوان
nuclei -u target.com -nc

# مخرجات مفصلة
nuclei -u target.com -v

# التنقيح
nuclei -u target.com -debug
```

<a id="export-options"></a>
### خيارات التصدير

```bash
# صيغة SARIF للاستخدام مع GitHub
nuclei -u target.com -sarif-export results.sarif

# تقرير Markdown
nuclei -u target.com -me reports/
```

---

<a id="-advanced-features"></a>
## ⚡ الميزات المتقدمة

<a id="rate-limiting"></a>
### تحديد معدل الطلبات

```bash
# عدد الطلبات في الثانية
nuclei -u target.com -rate-limit 100

# عدد الأهداف في الدفعة
nuclei -u target.com -bulk-size 25

# التزامن
nuclei -u target.com -c 50

# تزامن القوالب
nuclei -u target.com -rl 100 -c 50
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)

```bash
# وكيل HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# وكيل SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# وكيل Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

<a id="authentication"></a>
### المصادقة

```bash
# المصادقة باستخدام ترويسة
nuclei -u target.com -H "Authorization: Bearer token123"

# المصادقة باستخدام ملف تعريف ارتباط
nuclei -u target.com -H "Cookie: session=abc123"

# عدة ترويسات
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

<a id="interactsh-integration"></a>
### التكامل مع Interactsh

```bash
# استخدام Interactsh للاختبار خارج القناة (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# خادم Interactsh مخصص
nuclei -u target.com -iserver https://my-interactsh.com
```

<a id="headless-mode"></a>
### وضع المتصفح دون واجهة (Headless)

```bash
# تفعيل المتصفح دون واجهة
nuclei -u target.com -headless

# قوالب المتصفح دون واجهة فقط
nuclei -u target.com -t headless/
```

<a id="workflows"></a>
### مسارات العمل (Workflows)

```bash
# تشغيل مسار عمل
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# عرض مسارات العمل
ls ~/.local/nuclei-templates/workflows/
```

---

<a id="-custom-templates"></a>
## 📝 القوالب المخصصة

<a id="template-structure"></a>
### بنية القالب

> **شرح البنية:** `id` معرّف القالب، و`info` بيانات وصفية تشمل الاسم والمؤلف والخطورة والوسوم. يحدد `http` الطلبات، و`path` المسارات، و`matchers` شروط مطابقة النتائج. حُفظت كل مفاتيح YAML وقيمها النصية كما في الأصل، بما فيها الأسماء والأوصاف، لأنها جزء من المثال التقني.


```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

<a id="template-examples"></a>
### أمثلة القوالب

<a id="detect-specific-page"></a>
#### اكتشاف صفحة محددة

> **ملاحظة تحقق:** يعتمد المثال على رمز HTTP 200 فقط؛ قد يكون الرد صفحة عامة أو صفحة خطأ ذات حالة 200. لا يثبت ذلك وحده وجود لوحة إدارة أو ثغرة، بل يولّد نتيجة تستلزم المراجعة.

```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

<a id="check-header"></a>
#### فحص ترويسة

> **تصحيح بنيوي:** يكرر المثال المفتاح `matchers` مرتين داخل الخريطة نفسها، بينما تشترط [مواصفة YAML](https://yaml.org/spec/1.2.2/) تفرّد المفاتيح. لا تفترض أن شرطي المطابقة سيعملان معاً أو أن القالب صالح كما هو. حُفظ المثال دون إصلاح تنفيذي.

```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

<a id="validate-template"></a>
### التحقق من صحة القالب

```bash
# التحقق من صياغة القالب
nuclei -validate -t my-template.yaml

# اختبار القالب
nuclei -u target.com -t my-template.yaml -debug
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 سير عمل برامج مكافآت الثغرات

<a id="complete-recon-pipeline"></a>
### سلسلة استطلاع كاملة

```bash
# الخطوة 1: حصر النطاقات الفرعية
subfinder -d target.com -o subs.txt

# الخطوة 2: تحديد الأجهزة المستجيبة
cat subs.txt | httpx -o live.txt

# الخطوة 3: تشغيل Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# سطر واحد
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

<a id="priority-scanning"></a>
### الفحص بحسب الأولوية

```bash
# المرحلة 1: الخطورة الحرجة فقط لفحص سريع
nuclei -l targets.txt -severity critical -o critical.txt

# المرحلة 2: الخطورة العالية
nuclei -l targets.txt -severity high -o high.txt

# المرحلة 3: ثغرات CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# المرحلة 4: فحص كامل
nuclei -l targets.txt -o all.txt
```

<a id="targeted-scanning"></a>
### الفحص الموجّه

```bash
# أهداف WordPress
nuclei -l wp-sites.txt -tags wordpress

# نقاط نهاية API
nuclei -l apis.txt -tags api

# صفحات تسجيل الدخول
nuclei -l logins.txt -tags login,default-login
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-full-scan"></a>
### المثال 1: فحص كامل
```bash
nuclei -u https://target.com -o results.txt
```

<a id="example-2-critical-cves-only"></a>
### المثال 2: ثغرات CVE الحرجة فقط

> **توضيح:** يستخدم الأمر `-severity critical,high`؛ لذلك يشمل الخطورة العالية أيضاً رغم عنوان «الحرجة فقط» في الأصل.

```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

<a id="example-3-bug-bounty-quick-scan"></a>
### المثال 3: فحص سريع لبرامج مكافآت الثغرات
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

<a id="example-4-subdomain-takeover-check"></a>
### المثال 4: فحص احتمال الاستيلاء على نطاق فرعي
```bash
cat subs.txt | nuclei -t takeovers/
```

<a id="example-5-technology-detection"></a>
### المثال 5: تحديد التقنيات
```bash
nuclei -u https://target.com -t technologies/ -silent
```

<a id="example-6-with-proxy-burp"></a>
### المثال 6: باستخدام وكيل Burp
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

<a id="example-7-authenticated-scan"></a>
### المثال 7: فحص مع المصادقة
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

<a id="example-8-rate-limited-scan"></a>
### المثال 8: فحص بمعدل طلبات محدد
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

<a id="example-9-json-output-for-processing"></a>
### المثال 9: مخرجات JSON للمعالجة
```bash
nuclei -u https://target.com -json -o results.json
```

<a id="example-10-complete-pipeline"></a>
### المثال 10: سلسلة خطوات كاملة
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| المهمة | الأمر |
|------|---------|
| فحص أساسي | `nuclei -u target.com` |
| باستخدام قوالب | `nuclei -u target.com -t cves/` |
| حسب الوسوم | `nuclei -u target.com -tags cve,rce` |
| حسب الخطورة | `nuclei -u target.com -severity critical` |
| من قائمة | `nuclei -l urls.txt` |
| تحديث القوالب | `nuclei -ut` |
| مخرجات صامتة | `nuclei -u target.com -silent` |
| مخرجات JSON | `nuclei -u target.com -json -o out.json` |

<a id="common-tags"></a>
### وسوم شائعة

| الوسم | الوصف |
|-----|-------------|
| `cve` | ثغرات CVE |
| `rce` | تنفيذ التعليمات البرمجية عن بُعد |
| `sqli` | حقن SQL |
| `xss` | البرمجة النصية عبر المواقع |
| `ssrf` | تزوير الطلبات من جانب الخادم |
| `lfi` | تضمين الملفات المحلية |
| `panel` | لوحات الإدارة |
| `takeover` | الاستيلاء على نطاق فرعي |

<a id="severity-levels"></a>
### مستويات الخطورة

| الخطورة | اللون | الأولوية |
|----------|-------|----------|
| `critical` | 🔴 أحمر | فوري |
| `high` | 🟠 برتقالي | عالية |
| `medium` | 🟡 أصفر | متوسطة |
| `low` | 🟢 أخضر | منخفضة |
| `info` | 🔵 أزرق | معلوماتية |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح الأداء

```bash
# إعدادات مثلى للفحوصات الكبيرة وفق وصف الأصل
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# الوضع الصامت لسلاسل الأوامر
nuclei -l targets.txt -silent -o results.txt
```

<a id="bug-bounty-tips"></a>
### نصائح لبرامج مكافآت الثغرات

1. **البدء بالخطورة الحرجة والعالية**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **البحث عن نتائج سريعة**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **استخدام قوالب محدثة**
   ```bash
   nuclei -ut  # تحديث القوالب قبل البحث دائماً وفق نصيحة الأصل
   ```

4. **تحديد معدل الطلبات لتحسين الاستقرار**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

<a id="stealth-tips"></a>
### نصائح تقليل الظهور

```bash
# بطيء وقليل الضوضاء
nuclei -l targets.txt -rl 10 -c 2

# عبر وكيل
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** Nuclei فاحص ثغرات قوي ينبغي استخدامه فقط في **الاختبار المصرح به**.
> 
> - ✅ استخدمه على البرامج التي لديك إذن باختبارها
> - ✅ استخدمه ضمن برامج مكافآت ثغرات مصرح بها
> - ✅ استخدمه على أنظمتك الخاصة
> - ❌ لا تفحص دون تفويض
> - ❌ التزم بحدود معدل الطلبات ونطاق الاختبار
> 
> **الفحص دون تصريح مخالف للقانون وأخلاقيات العمل.**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-resources"></a>
### المراجع الرسمية
- [مستودع Nuclei على GitHub](https://github.com/projectdiscovery/nuclei)
- [قوالب Nuclei](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [التوثيق](https://docs.projectdiscovery.io/tools/nuclei/)

<a id="template-resources"></a>
### مراجع القوالب
- [قوالب المجتمع](https://github.com/projectdiscovery/nuclei-templates)
- [دليل القوالب](https://docs.projectdiscovery.io/templates/)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [Nmap](../../Nmap/translations/README.ar.md)
- [Burp Suite](../../Burp-Suite/translations/README.ar.md)
- [SQLMap](../../SQLMap/translations/README.ar.md)

---

<p align="center">
  <b>🎯 أتقن فحص الثغرات!</b><br>
  <i>رفيق الباحث عن الثغرات ضمن برامج المكافآت</i>
</p>
