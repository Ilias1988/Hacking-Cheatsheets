<a id="-amass---attack-surface-mapping"></a>
# 🌐 Amass — رسم خريطة سطح الهجوم

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         In-depth Attack Surface Mapping
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما Amass؟](#-what-is-amass)
- [التثبيت](#-installation)
- [وضع الاستطلاع (Enumeration)](#-enumeration-mode)
- [وضع جمع المعلومات (Intel)](#-intel-mode)
- [وضع DNS](#-dns-mode)
- [الإعداد](#-configuration)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-amass"></a>
## 🎯 ما Amass؟

**Amass** مشروع تابع لـ OWASP لرسم خرائط الشبكات واكتشاف الأصول الخارجية:

- 🔍 **استطلاع النطاقات الفرعية** - الأداة الأشمل وفق وصف الأصل
- 🏢 **اكتشاف أرقام الأنظمة المستقلة (ASN)** - العثور على نطاقات عناوين IP الخاصة بالمؤسسة
- 📡 **استخبارات DNS** - استطلاع سلبي ونشط
- 🗺️ **رسم خريطة سطح الهجوم** - عرض البنية التحتية بصريًا

<a id="why-amass"></a>
### لماذا Amass؟

| الميزة | الفائدة |
|---------|---------|
| أكثر من 55 مصدر بيانات | نتائج واسعة التغطية وفق الأصل |
| سلبي ونشط | اختيار مستوى النشاط الظاهر |
| معلومات ASN والمؤسسات | اكتشاف بنية تحتية غير ظاهرة |
| قاعدة بيانات بيانية | تمثيل العلاقات بصريًا |

---

<a id="-installation"></a>
## 🚀 التثبيت

```bash
# التثبيت باستخدام Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# التحقق
amass -version
```

---

<a id="-enumeration-mode"></a>
## 🔍 وضع الاستطلاع (Enumeration)

<a id="basic-subdomain-enumeration"></a>
### استطلاع أساسي للنطاقات الفرعية

```bash
# استطلاع سلبي دون اتصال مباشر بالهدف وفق وصف الأصل
amass enum -passive -d example.com

# استطلاع نشط، يصفه الأصل بأنه تخمين DNS
amass enum -active -d example.com

# تخمين باستخدام قائمة كلمات
amass enum -active -brute -d example.com -w wordlist.txt
```

<a id="output-options"></a>
### خيارات الإخراج

```bash
# الحفظ في ملف
amass enum -passive -d example.com -o subdomains.txt

# إخراج بصيغة JSON
amass enum -passive -d example.com -json output.json

# الحفظ في مجلد
amass enum -passive -d example.com -dir ./amass_output/
```

<a id="multiple-targets"></a>
### أهداف متعددة

```bash
# نطاقات متعددة
amass enum -passive -d example.com -d target.com

# من ملف
amass enum -passive -df domains.txt -o results.txt
```

<a id="advanced-enumeration"></a>
### استطلاع متقدم

```bash
# تحديد المهلة بالدقائق
amass enum -passive -d example.com -timeout 30

# تحديد المصادر المستخدمة
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# استبعاد مصادر
amass enum -passive -d example.com -exclude Google,Bing

# عرض مصادر البيانات
amass enum -passive -d example.com -src
```

<a id="recursive-enumeration"></a>
### استطلاع تكراري

```bash
# تفعيل البحث التكراري وفق وصف الأصل
amass enum -passive -d example.com -r

# تحديد عمق التكرار وفق وصف الأصل
amass enum -passive -d example.com -max-depth 3
```

---

<a id="-intel-mode"></a>
## 🏢 وضع جمع المعلومات (Intel)

> يكتشف وضع Intel النطاقات الجذرية وأرقام ASN ونطاقات CIDR المرتبطة بالمؤسسات.

<a id="find-organization-assets"></a>
### اكتشاف أصول المؤسسة

```bash
# حسب اسم المؤسسة
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# بحث WHOIS عكسي
amass intel -whois -d example.com
```

<a id="asn-discovery"></a>
### اكتشاف أرقام الأنظمة المستقلة (ASN)

```bash
# العثور على ASN المرتبط بالنطاق
amass intel -d example.com

# الاستطلاع حسب ASN
amass intel -asn 13335
amass intel -active -asn 13335

# أرقام ASN متعددة
amass intel -asn 13335,15169
```

<a id="cidr-range"></a>
### نطاق CIDR

```bash
# البحث عن أصول ضمن نطاق CIDR
amass intel -active -cidr 192.168.1.0/24

# نطاقات CIDR متعددة
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

<a id="ip-range"></a>
### نطاق عناوين IP

```bash
# مدى عناوين IP
amass intel -active -ip 192.168.1.1-254
```

---

<a id="-dns-mode"></a>
## 📡 وضع DNS

<a id="dns-queries"></a>
### استعلامات DNS

```bash
# استعلام DNS أساسي
amass dns -d example.com

# أنواع سجلات محددة
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

<a id="resolve-subdomains"></a>
### حل أسماء النطاقات الفرعية

```bash
# حل أسماء النطاقات الفرعية المكتشفة
amass dns -df subdomains.txt
```

---

<a id="-configuration"></a>
## ⚙️ الإعداد

<a id="config-file-location"></a>
### موضع ملف الإعداد

```bash
# الموضع الافتراضي
~/.config/amass/config.yaml

# تحديد ملف إعداد مخصص
amass enum -config ./my_config.yaml -d example.com
```

<a id="sample-config-file"></a>
### مثال ملف إعداد

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

<a id="data-sources-with-api-keys"></a>
### مصادر بيانات تستخدم مفاتيح API

| المصدر | الخطة المجانية | يتطلب API |
|--------|-----------|--------------|
| Crt.sh | ✅ | لا |
| SecurityTrails | ✅ | نعم |
| Shodan | ✅ | نعم |
| VirusTotal | ✅ | نعم |
| Censys | ✅ | نعم |
| PassiveTotal | محدودة | نعم |
| BinaryEdge | محدودة | نعم |

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="enumeration-commands"></a>
### أوامر الاستطلاع

| الأمر | الوصف |
|---------|-------------|
| `amass enum -passive -d domain.com` | استطلاع سلبي للنطاقات الفرعية |
| `amass enum -active -d domain.com` | استطلاع DNS نشط |
| `amass enum -active -brute -d domain.com` | تخمين أسماء النطاقات الفرعية |
| `amass enum -d domain.com -o out.txt` | الحفظ في ملف |
| `amass enum -d domain.com -json out.json` | إخراج بصيغة JSON |

<a id="intel-commands"></a>
### أوامر Intel

| الأمر | الوصف |
|---------|-------------|
| `amass intel -org "Company"` | البحث عن الأصول حسب اسم المؤسسة |
| `amass intel -asn 12345` | الاستطلاع حسب ASN |
| `amass intel -cidr 10.0.0.0/8` | استطلاع نطاق CIDR |
| `amass intel -whois -d domain.com` | بحث WHOIS عكسي |

<a id="essential-flags"></a>
### الخيارات الأساسية

| الخيار | الوصف |
|------|-------------|
| `-d` | النطاق المستهدف |
| `-df` | قراءة النطاقات من ملف |
| `-o` | ملف النتائج |
| `-passive` | سلبي فقط دون استعلامات DNS وفق الأصل |
| `-active` | تفعيل DNS النشط وفق الأصل |
| `-brute` | تخمين أسماء DNS |
| `-src` | عرض مصدر البيانات |
| `-timeout` | المهلة بالدقائق |

---

<a id="-bug-bounty-workflow"></a>
## 🎯 مسار عمل البحث عن الثغرات ضمن برامج المكافآت

```bash
# 1. استطلاع سلبي (يصفه الأصل بأنه آمن)
amass enum -passive -d target.com -o passive_subs.txt

# 2. استطلاع نشط مع التخمين
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. اكتشاف أصول المؤسسة
amass intel -org "Target Inc" -o org_domains.txt

# 4. دمج النتائج
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. حل أسماء المضيفين المتاحين وفق وصف الأصل
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

<a id="-resources"></a>
## 📚 الموارد

- [مستودع OWASP Amass على GitHub](https://github.com/owasp-amass/amass)
- [دليل مستخدم Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [منهجية البحث عن الثغرات ضمن برامج المكافآت](../../Bug-Bounty-Methodology/translations/README.ar.md)
- [Subfinder](../../Subfinder/translations/README.ar.md)
- [httpx](../../httpx/translations/README.ar.md)

---

<p align="center">
  <b>🌐 ارسم خريطة سطح الهجوم!</b><br>
  <i>Amass — أداة واسعة التغطية للنطاقات الفرعية</i>
</p>

> **مصطلحات:** سطح الهجوم هو مجموعة الأصول والخدمات التي قد يصل إليها مهاجم. ASN رقم نظام مستقل في توجيه الإنترنت، وCIDR صياغة تصف نطاقات عناوين IP. الاستطلاع السلبي يعتمد على مصادر بيانات، بينما يضيف النشط تفاعلات مع البنية المستهدفة. حُفظ الرسم الإنجليزي؛ وعنوانه يعني «رسم متعمّق لخريطة سطح الهجوم». أعداد المصادر وخططها المجانية في الجدول منقولة من الأصل وليست مراجعة محدثة للخدمات.

> **ملاحظة توافق محددة:** جرى التحقق من شيفرة Amass `v4.2.0` لأن أمثلة الأصل تشير إلى v4. في هذا الإصدار، `-r` يقبل عناوين محلّلات DNS وليس مفتاحًا لتفعيل التكرار، و`-max-depth` يحدّ عدد تسميات النطاق أثناء التخمين. كذلك يفصل الإصدار بين `-active` لمحاولات نقل مناطق DNS وجمع أسماء الشهادات، و`-brute` للتخمين؛ فلا يتطابق الوصفان في بعض تعليقات الأصل. وُسم `-passive` بأنه مهمل في هذا الإصدار لأن السلوك السلبي هو الافتراضي. [تعريفات خيارات enum الرسمية للإصدار المثبّت](https://github.com/owasp-amass/amass/blob/v4.2.0/cmd/amass/enum.go).

> **تصحيح لوضع DNS:** لا يوفّر مشغّل `v4.2.0` أمرًا فرعيًا باسم `dns`؛ يعرض `enum` و`intel`. لذلك أُبقي قسم `amass dns` من الأصل بوصفه مثالًا يحتاج إلى مراجعة توافق، ولا يُفترض أنه يعمل في هذه النسخة. [المشغّل الرسمي](https://github.com/owasp-amass/amass/blob/v4.2.0/cmd/amass/main.go). ملف YAML وأسماء مصادره وقيمه محفوظة للمقارنة، ويجب مطابقة مخطط الإعداد مع الإصدار المستخدم.
