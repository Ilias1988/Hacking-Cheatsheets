<a id="-subfinder---complete-cheatsheet"></a>
# 🔍 Subfinder — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Fast Subdomain Discovery Tool
```

يصف الشعار Subfinder بأنها أداة سريعة لاكتشاف النطاقات الفرعية.

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Passive">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 أداة سريعة من ProjectDiscovery لاستكشاف النطاقات الفرعية بطريقة سلبية</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [اكتشاف النطاقات الفرعية](#-subdomain-discovery)
- [مصادر البيانات](#-data-sources)
- [إعداد واجهات API](#-api-configuration)
- [خيارات المخرجات](#-output-options)
- [ميزات متقدمة](#-advanced-features)
- [تسلسل العمل في مكافآت الثغرات](#-bug-bounty-workflow)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Subfinder** أداة سريعة لاستكشاف النطاقات الفرعية بطريقة سلبية، باستخدام مصادر متعددة تشمل محركات البحث ومواقع مشاركة النصوص ومنصات المعلومات الأمنية.

<a id="key-features"></a>
### الميزات الأساسية

|الميزة|الوصف|
|---------|-------------|
|**سريع**|مكتوب بلغة Go مع تزامن مرتفع|
|**استطلاع سلبي**|دون اتصال مباشر بالهدف في النمط السلبي|
|**مصادر متعددة**|أكثر من 40 مصدر بيانات بحسب الأصل|
|**تكامل API**|دعم المصادر التي تتطلب مفاتيح API|
|**مناسب لمسارات العمل**|يعمل مع أدوات أخرى|
|**مخرجات JSON**|دعم المخرجات المهيكلة|

<a id="subfinder-vs-other-tools"></a>
### مقارنة Subfinder بالأدوات الأخرى

|الميزة|Subfinder|Amass|Assetfinder|
|---------|-----------|-------|-------------|
|**السرعة**|⭐⭐⭐⭐⭐|⭐⭐⭐|⭐⭐⭐⭐|
|**المصادر**|40+|50+|10+|
|**الفحص النشط**|❌ لا|✅ نعم|❌ لا|
|**دعم API**|✅ نعم|✅ نعم|❌ لا|
|**سهولة الاستخدام**|⭐⭐⭐⭐⭐|⭐⭐⭐|⭐⭐⭐⭐|

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="using-go"></a>
### باستخدام Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install subfinder
```

<a id="binary-download"></a>
### تنزيل الملف التنفيذي

> **توضيح:** `v2.x.x` و`2.x.x` قيم نائبة في رابط الأصل؛ ليست أسماء إصدار جاهزة للتنزيل. راجع صفحة الإصدارات المذكورة في المثال.
```bash
# التنزيل من صفحة الإصدارات
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

<a id="docker"></a>
### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
subfinder -version
subfinder -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
subfinder [options] -d <domain>
```

<a id="essential-options"></a>
### الخيارات الأساسية

|الخيار|الوصف|
|--------|-------------|
|`-d`|النطاق المستهدف|
|`-dL`|ملف يتضمن قائمة نطاقات|
|`-o`|ملف المخرجات|
|`-oJ`|مخرجات JSONL، أسطر JSON منفصلة|
|`-silent`|وضع هادئ يعرض النطاقات الفرعية فقط|
|`-v`|مخرجات تفصيلية|
|`-t`|عدد العمليات المتزامنة بحسب الأصل؛ راجع التوضيح|
|`-all`|استخدام جميع المصادر|

<a id="quick-start"></a>
### بدء سريع
```bash
# استكشاف أساسي
subfinder -d example.com

# مخرجات هادئة في قائمة دون رسائل إضافية
subfinder -d example.com -silent

# الحفظ في ملف
subfinder -d example.com -o subdomains.txt

# نطاقات متعددة
subfinder -dL domains.txt -o all-subs.txt
```

---

<a id="-subdomain-discovery"></a>
## 🔍 اكتشاف النطاقات الفرعية

<a id="single-domain"></a>
### نطاق واحد

```bash
# استكشاف أساسي
subfinder -d target.com

# مخرجات تفصيلية
subfinder -d target.com -v

# وضع هادئ يعرض النطاقات الفرعية فقط
subfinder -d target.com -silent

# عرض المصادر بحسب وصف الأصل؛ راجع توضيح ‎-cs
subfinder -d target.com -cs
```

<a id="multiple-domains"></a>
### نطاقات متعددة

```bash
# من ملف
subfinder -dL domains.txt

# حفظ المخرجات في ملف
subfinder -dL domains.txt -o all_subdomains.txt

# وضع هادئ لتمرير المخرجات بين الأدوات
subfinder -dL domains.txt -silent | httpx
```

<a id="recursive-enumeration"></a>
### الاستكشاف المتداخل (Recursive Enumeration)

> **تصحيح:** يختار `-recursive` المصادر التي تقبل النطاقات المتداخلة، ولا يصف حلقة زحف تكرارية تلقائية لجميع النتائج. [مرجع الخيارات الرسمي](https://docs.projectdiscovery.io/opensource/subfinder/usage).

```bash
# تفعيل الاستكشاف المتداخل بحسب وصف الأصل؛ راجع التوضيح
subfinder -d target.com -recursive

# الاستكشاف المتداخل مع جميع المصادر بحسب الأصل
subfinder -d target.com -recursive -all
```

---

<a id="-data-sources"></a>
## 📊 مصادر البيانات

قوائم المصادر وأعدادها والخطط المجانية أدناه تعكس الأصل؛ تتغير الإتاحة ومتطلبات المفاتيح والحدود بحسب المزود والإصدار. قالب YAML مثال مرجعي أبقينا مفاتيحه وقيمه كما هي. [مرجع الخيارات وإدارة المصادر](https://docs.projectdiscovery.io/opensource/subfinder/usage).

<a id="free-sources-default"></a>
### مصادر مجانية افتراضية بحسب الأصل

يذكر الأصل المصادر التالية بوصفها افتراضية ولا تتطلب مفتاح API:
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

<a id="premium-sources-api-required"></a>
### مصادر تتطلب مفاتيح API بحسب الأصل

يذكر الأصل أن المصادر التالية تتطلب مفاتيح API لتوسيع النتائج:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

<a id="source-management"></a>
### إدارة المصادر

```bash
# عرض جميع المصادر
subfinder -ls

# استخدام مصادر محددة فقط
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# استبعاد مصادر محددة
subfinder -d target.com -es shodan,securitytrails

# استخدام جميع المصادر، بما فيها البطيئة
subfinder -d target.com -all

# عرض المصادر التي اكتشفت كل نطاق فرعي بحسب الأصل؛ راجع التوضيح
subfinder -d target.com -cs
```

---

<a id="-api-configuration"></a>
## 🔑 إعداد واجهات API

<a id="config-file-location"></a>
### موقع ملف الإعداد

```bash
# الموقع الافتراضي
~/.config/subfinder/provider-config.yaml

# أو تحديد ملف مخصص
subfinder -d target.com -pc /path/to/config.yaml
```

<a id="config-file-template"></a>
### قالب ملف الإعداد

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

<a id="getting-api-keys"></a>
### الحصول على مفاتيح API

|المصدر|الخطة المجانية|URL|
|--------|-----------|-----|
|**VirusTotal**|✅ نعم|https://www.virustotal.com|
|**SecurityTrails**|✅ نعم، بحدود|https://securitytrails.com|
|**Shodan**|✅ نعم، بحدود|https://shodan.io|
|**Censys**|✅ نعم|https://censys.io|
|**Chaos**|✅ مجاني بحدود|https://chaos.projectdiscovery.io|
|**GitHub**|✅ مجاني|https://github.com/settings/tokens|

<a id="recommended-free-setup"></a>
### إعداد مجاني مقترح بحسب الأصل

```yaml
# حد أدنى مقترح لاختبارات مكافآت الثغرات بحسب الأصل
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

<a id="-output-options"></a>
## 📤 خيارات المخرجات

<a id="output-formats"></a>
### تنسيقات المخرجات

> **تصحيح للإصدار:** ينتج `-oJ` تنسيق JSONL؛ و`-oD` مجلد إخراج مع `-dL` وليس CSV. لا يدرج التوثيق الحالي `-oJL`. كما أن `-cs` يجمع المصادر في مخرجات JSON فقط، فلا تكفي أمثلة الأصل التي تستخدمه منفردًا. [مرجع ProjectDiscovery الرسمي](https://docs.projectdiscovery.io/opensource/subfinder/usage).

```bash
# نص عادي، وهو التنسيق الافتراضي
subfinder -d target.com -o subs.txt

# مخرجات JSON بحسب وصف الأصل؛ التنسيق الفعلي JSONL
subfinder -d target.com -oJ -o subs.json

# مخرجات JSON على أسطر منفصلة
subfinder -d target.com -oJL -o subs.jsonl

# تنسيق CSV بحسب الأصل؛ راجع تصحيح ‎-oD
subfinder -d target.com -oD -o subs.csv
```

<a id="output-filtering"></a>
### ترشيح المخرجات

> **توافق:** لا يدرج التوثيق الحالي `-exclude-subdomains`؛ ويوثق `-filter` لاستبعاد نطاقات أو قوائم منها. أُبقي مثال الأصل دون تغيير. [مرجع الخيارات](https://docs.projectdiscovery.io/opensource/subfinder/usage).

```bash
# الوضع الهادئ لمخرجات دون رسائل إضافية
subfinder -d target.com -silent

# عرض النطاقات الجديدة فقط مع استبعاد المعروفة بحسب الأصل
subfinder -d target.com -exclude-subdomains known.txt

# دون ألوان
subfinder -d target.com -nc
```

<a id="piping-output"></a>
### تمرير المخرجات بين الأدوات

```bash
# تمرير المخرجات إلى httpx
subfinder -d target.com -silent | httpx -silent

# تمرير المخرجات إلى nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# الحفظ والمعالجة
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

<a id="-advanced-features"></a>
## ⚡ ميزات متقدمة

<a id="threading--performance"></a>
### التزامن والأداء

> **تصحيح:** يضبط `-t` تزامن حل DNS عند استخدام `-active` فقط؛ لا يرفع تزامن المصادر السلبية عمومًا كما توحي أمثلة الأداء. [مرجع الخيارات](https://docs.projectdiscovery.io/opensource/subfinder/usage).

```bash
# تحديد التزامن، والافتراضي 10 بحسب الأصل؛ راجع توضيح ‎-t
subfinder -d target.com -t 50

# تحديد معدل الطلبات
subfinder -d target.com -rate-limit 100

# مهلة الانتظار
subfinder -d target.com -timeout 30
```

<a id="matching--filtering"></a>
### المطابقة والترشيح

```bash
# مطابقة أنماط محددة
subfinder -d target.com -match "api,dev,stage"

# استبعاد الأنماط المحددة
subfinder -d target.com -filter "www,mail"
```

<a id="resolver-options"></a>
### خيارات محللات DNS ‏(Resolvers)

```bash
# استخدام محللات DNS مخصصة
subfinder -d target.com -rL resolvers.txt

# التحقق من قابلية حل النطاقات الفرعية
subfinder -d target.com -nW
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)

```bash
# وكيل HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# وكيل SOCKS بحسب مثال الأصل
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

<a id="-bug-bounty-workflow"></a>
## 🔄 تسلسل العمل في مكافآت الثغرات

<a id="complete-recon-pipeline"></a>
### مسار استطلاع متكامل

```bash
# الخطوة 1: استكشاف النطاقات الفرعية
subfinder -d target.com -all -o subs.txt

# الخطوة 2: التحقق من خدمات الويب المستجيبة
cat subs.txt | httpx -o live.txt

# الخطوة 3: الفحص باستخدام nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# أمر في سطر واحد
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

<a id="multi-domain-workflow"></a>
### تسلسل العمل لنطاقات متعددة

```bash
# استكشاف جميع نطاقات البرنامج
subfinder -dL scope.txt -all -o all-subs.txt

# معالجة النتائج
cat all-subs.txt | httpx -silent | nuclei
```

<a id="continuous-monitoring"></a>
### المراقبة المستمرة

> **توضيح للمقارنة:** يقارن `diff` أسطر الملفين بترتيبها؛ إذا تغير ترتيب نفس النطاقات فقد يصنفها المثال إضافات. يلزم توحيد الترتيب وإزالة التكرار للحصول على مقارنة مجموعات موثوقة. احتُفظ بالأوامر الأصلية كما هي.

```bash
# حفظ قائمة مرجعية أولية
subfinder -d target.com -silent > baseline.txt

# البحث عن النطاقات الجديدة في وقت لاحق
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

<a id="integration-with-other-tools"></a>
### التكامل مع أدوات أخرى

```bash
# مع httpx لخدمات الويب المستجيبة
subfinder -d target.com -silent | httpx

# مع ffuf لاختبار أسماء المجلدات
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# مع nmap لفحص المنافذ
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# مسار عمل متكامل
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-basic-enumeration"></a>
### المثال 1: استكشاف أساسي
```bash
subfinder -d hackerone.com -silent
```

<a id="example-2-with-all-sources"></a>
### المثال 2: استخدام جميع المصادر
```bash
subfinder -d target.com -all -v
```

<a id="example-3-save-results"></a>
### المثال 3: حفظ النتائج
```bash
subfinder -d target.com -o target-subs.txt
```

<a id="example-4-multiple-domains"></a>
### المثال 4: نطاقات متعددة
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

<a id="example-5-show-sources"></a>
### المثال 5: عرض المصادر بحسب الأصل
```bash
subfinder -d target.com -cs
```

<a id="example-6-json-output"></a>
### المثال 6: مخرجات JSON
```bash
subfinder -d target.com -oJ -o results.json
```

<a id="example-7-with-proxy"></a>
### المثال 7: استخدام وكيل
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

<a id="example-8-high-performance"></a>
### المثال 8: أداء مرتفع بحسب الأصل
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

<a id="example-9-full-bug-bounty-pipeline"></a>
### المثال 9: مسار متكامل لمكافآت الثغرات
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

<a id="example-10-recursive-discovery"></a>
### المثال 10: اكتشاف النطاقات المتداخلة
```bash
subfinder -d target.com -recursive -all -silent
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

|المهمة|الأمر|
|------|---------|
|استكشاف أساسي|`subfinder -d target.com`|
|مخرجات هادئة|`subfinder -d target.com -silent`|
|الحفظ في ملف|`subfinder -d target.com -o subs.txt`|
|جميع المصادر|`subfinder -d target.com -all`|
|عرض المصادر بحسب الأصل|`subfinder -d target.com -cs`|
|نطاقات متعددة|`subfinder -dL domains.txt`|
|مخرجات JSONL، أسطر JSON منفصلة|`subfinder -d target.com -oJ`|
|عرض قائمة المصادر|`subfinder -ls`|

<a id="common-options"></a>
### خيارات شائعة

|الخيار|الوصف|
|--------|-------------|
|`-d`|النطاق المستهدف|
|`-dL`|ملف قائمة النطاقات|
|`-o`|ملف المخرجات|
|`-oJ`|مخرجات JSONL، أسطر JSON منفصلة|
|`-silent`|الوضع الهادئ|
|`-all`|استخدام جميع المصادر|
|`-cs`|إدراج مصادر كل نطاق فرعي في مخرجات JSON|
|`-t`|عمليات متزامنة بحسب الأصل|
|`-recursive`|استخدام المصادر الداعمة للنطاقات المتداخلة|

<a id="common-pipelines"></a>
### مسارات عمل شائعة

|مسار العمل|الأمر|
|----------|---------|
|خدمات ويب مستجيبة|`subfinder -d target.com -silent \| httpx`|
|استطلاع متكامل|`subfinder -d target.com \| httpx \| nuclei`|
|فحص منافذ|`subfinder -d target.com -silent \| dnsx -a \| nmap -iL -`|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="bug-bounty-tips"></a>
### نصائح لمكافآت الثغرات

1. **إعداد مفاتيح API**
   ```bash
   # الحصول على نتائج أوسع باستخدام API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **استخدام جميع المصادر لتوسيع التغطية**
   ```bash
   subfinder -d target.com -all
   ```

3. **التكامل مع أدوات أخرى**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **مراقبة النطاقات الفرعية الجديدة**
   ```bash
   # فحص يومي بحثًا عن أصول جديدة
   subfinder -d target.com -silent > today.txt
   ```

<a id="performance-tips"></a>
### نصائح للأداء

```bash
# عدد تزامن مرتفع بحسب الأصل؛ راجع توضيح ‎-t
subfinder -d target.com -t 100 -all

# مهلة انتظار أطول للمصادر البطيئة
subfinder -d target.com -timeout 60
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** يعمل Subfinder في نمطه السلبي عبر مصادر خارجية، لكن تفعيل خيارات التحقق النشط أو استخدام الأدوات اللاحقة في المسار قد يؤدي إلى اتصالات بالهدف.
> 
> - ✅ استخدمه ضمن برامج مصرح بها
> - ✅ التزم بأهداف مكافآت الثغرات الواقعة ضمن النطاق
> - ✅ استخدمه مع نطاقاتك
> - ❌ لا تستكشف أهدافًا دون تفويض بحسب تنبيه الأصل
> - ❌ احترم نطاق البرنامج ولا تتجاوزه
> 
> **تأكد دائمًا من وجود التفويض.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [مستودع Subfinder على GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [التوثيق](https://docs.projectdiscovery.io/tools/subfinder/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Nuclei](../../Nuclei/translations/README.ar.md)
- [ffuf](../../ffuf/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)

---

<p align="center">
  <b>🔍 وسع اكتشاف النطاقات الفرعية!</b><br>
  <i>خطوة أولى في استطلاع أهداف مكافآت الثغرات</i>
</p>
