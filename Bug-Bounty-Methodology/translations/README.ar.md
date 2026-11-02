<a id="-bug-bounty-methodology"></a>
# 🐛 منهجية البحث عن الثغرات ضمن برامج المكافآت (Bug Bounty)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Complete Bug Bounty Hunting Methodology
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

<a id="-attack-flow-overview"></a>
## 🗺️ نظرة عامة على سير الهجوم

> **دليل المخطط:** تتتابع المراحل الست: حصر النطاقات الفرعية، ثم اكتشاف ASN وعناوين IP، ثم تحديد الأجهزة المستجيبة، ثم جمع عناوين URL، ثم فحص مؤشرات الثغرات، وأخيراً التعمق في المجلدات والمعاملات وانكشاف Git. حُفظ المخطط الإنجليزي ثابت العرض، وتشرح الأقسام العربية كل مرحلة.


```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      BUG BOUNTY HUNTING METHODOLOGY                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  PHASE 1: Subdomain Enum    PHASE 2: ASN & IP        PHASE 3: Live Hosts   │
│  ├── Subfinder              ├── ASN Mapping          ├── HTTPX Probing     │
│  ├── Amass                  ├── IP Harvesting        ├── Port Discovery    │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  PHASE 4: URL Collection    PHASE 5: Vuln Hunting    PHASE 6: Deep Dive    │
│  ├── Katana/Hakrawler       ├── XSS Testing          ├── Directory Brute   │
│  ├── GAU                    ├── LFI/RFI              ├── Parameter Fuzzing │
│  └── Parameter Extract      ├── SSRF/CORS            └── Git Exposure      │
│                             └── Open Redirect                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

1. [المرحلة 1: حصر النطاقات الفرعية](#-phase-1-subdomain-enumeration)
2. [المرحلة 2: اكتشاف ASN وعناوين IP](#-phase-2-asn--ip-discovery)
3. [المرحلة 3: اكتشاف الأجهزة المستجيبة](#-phase-3-live-host-discovery)
4. [المرحلة 4: جمع عناوين URL](#-phase-4-url-collection)
5. [المرحلة 5: البحث عن الثغرات](#-phase-5-vulnerability-hunting)
6. [المرحلة 6: اكتشاف المجلدات والملفات](#-phase-6-directory--file-discovery)
7. [مرجع سريع](#-quick-reference)
8. [تثبيت الأدوات](#-tool-installation)

---

<a id="-phase-1-subdomain-enumeration"></a>
## 🔍 المرحلة 1: حصر النطاقات الفرعية

> **الهدف:** العثور على أكبر عدد ممكن من النطاقات الفرعية؛ اتساع سطح الهجوم يزيد فرص اكتشاف الثغرات.

<a id="automated-enumeration-tools"></a>
### أدوات الحصر الآلي

<a id="subfinder"></a>
#### Subfinder

> **تصحيح للخيارات:** وفق [المصدر الرسمي لـ Subfinder](https://github.com/projectdiscovery/subfinder)، يقيّد `-recursive` الاستخدام بالمصادر القادرة على التعامل مع النطاقات الفرعية تكرارياً؛ ليس ضماناً لزحف تلقائي غير محدود داخل كل نطاق. وقد تتطلب بعض مصادر `-all` مفاتيح API أو اشتراكات.

```bash
# اكتشاف سريع للنطاقات الفرعية من مصادر متعددة
subfinder -d example.com -all -recursive -o subfinder.txt

# شرح الخيارات:
# -all: استخدام جميع المصادر المجانية والمدفوعة
# -recursive: حصر تكراري للنطاقات الفرعية وفق شرح الأصل
# -o: ملف المخرجات
```
> 💡 **نصيحة:** Subfinder سريع وموثوق؛ استخدم `-all` للحصول على نتائج أوسع وفق نصيحة الأصل.

<a id="assetfinder"></a>
#### Assetfinder
```bash
# العثور على مجالات ونطاقات فرعية مرتبطة بالهدف
assetfinder --subs-only example.com > assetfinder.txt

# سريع وبسيط للاستطلاع الأولي
```
> 💡 **نصيحة:** Assetfinder سريع لكنه أقل شمولاً؛ استخدمه إلى جانب أدوات أخرى.

<a id="sublist3r"></a>
#### Sublist3r
```bash
# حصر النطاقات الفرعية باستخدام استخبارات المصادر المفتوحة OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: تحديد محركات البحث
# -v: مخرجات مفصلة
```

<a id="amass"></a>
#### Amass
```bash
# رسم خريطة معمقة لسطح الهجوم
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# الوضع السلبي دون اتصال مباشر بالهدف وفق وصف الأصل
# استخدام -active لنتائج أوسع مع حركة أكثر وضوحاً وفق الأصل
```
> ⚠️ **تنبيه:** قد يولد الوضع النشط في Amass حركة ملحوظة؛ ابدأ بالوضع السلبي وفق نصيحة الأصل.

<a id="public-sources"></a>
### المصادر العامة

<a id="certificate-transparency-crtsh"></a>
#### شفافية الشهادات (Certificate Transparency) عبر crt.sh
```bash
# العثور على النطاقات الفرعية من سجلات شهادات SSL
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **لماذا crt.sh؟** سجلات الشهادات العامة مصدر مجاني لمعلومات النطاقات الفرعية.

<a id="wayback-machine"></a>
#### Wayback Machine
```bash
# اكتشاف نطاقات فرعية تاريخية من أرشيفات الويب
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **نصيحة عملية:** قد تبقى نطاقات فرعية قديمة منسية قيد الاستخدام، فتكون نقاطاً تستحق المراجعة.

<a id="subdomain-processing"></a>
### معالجة النطاقات الفرعية

<a id="merge--deduplicate-results"></a>
#### دمج النتائج وإزالة التكرار
```bash
# دمج ملفات النطاقات الفرعية وإزالة التكرارات
cat *.txt | sort -u > final_subdomains.txt

# عدّ النتائج
wc -l final_subdomains.txt
```

<a id="ffuf-subdomain-bruteforce"></a>
#### حصر النطاقات الفرعية بالقوة الغاشمة باستخدام FFUF
```bash
# اكتشاف النطاقات الفرعية بالقوة الغاشمة باستخدام قوائم الكلمات
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: مطابقة رموز حالة محددة فقط
# قائمة كلمات مقترحة: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

<a id="subdomain-permutation-with-alterx"></a>
#### توليد تبديلات النطاقات الفرعية باستخدام Alterx
```bash
# توليد تبديلات النطاقات الفرعية وحل أسمائها
subfinder -d example.com | alterx | dnsx

# إثراء النطاق بأنماط شائعة
echo example.com | alterx -enrich | dnsx

# استخدام قائمة كلمات لتوليد التبديلات
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **مثال:** إذا وجدت `dev.example.com`، يمكن لـ alterx توليد صيغ مثل `dev1` و`dev2` و`dev-api` وغيرها.

---

<a id="-phase-2-asn--ip-discovery"></a>
## 🌐 المرحلة 2: اكتشاف ASN وعناوين IP

> **الهدف:** العثور على نطاقات عناوين IP والبنية التحتية المرتبطة بالمؤسسة المستهدفة.

<a id="asn-mapping"></a>
### ربط البنية التحتية برقم النظام المستقل (ASN)

```bash
# اكتشاف عناوين IP المرتبطة برقم ASN الخاص بالنطاق وفق وصف الأصل
asnmap -d example.com | dnsx -silent -resp-only

# اكتشاف الأصول باسم المؤسسة
amass intel -org "organization_name"

# اكتشاف الأصول ضمن نطاق عناوين IP
amass intel -active -cidr 159.69.129.82/32

# اكتشاف الأصول برقم ASN
amass intel -active -asn [asnno]
```
> 💡 **لماذا ASN؟** قد تملك المؤسسات نطاقات كاملة من عناوين IP؛ يساعد ذلك على اكتشاف خوادم غير ظاهرة.

<a id="ip-harvesting"></a>
### جمع عناوين IP

<a id="virustotal"></a>
#### VirusTotal
```bash
# استخراج عناوين IP من VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="alienvault-otx"></a>
#### AlienVault OTX
```bash
# جمع عناوين IP من AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="urlscanio"></a>
#### URLScan.io
```bash
# استخراج عناوين IP من URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="shodan-ssl-search"></a>
#### البحث في شهادات SSL باستخدام Shodan
```bash
# العثور على عناوين IP بالبحث في شهادات SSL عبر Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **نصيحة عملية:** يساعد البحث في شهادات SSL عبر Shodan على العثور على خوادم تستخدم شهادة الهدف، حتى الخوادم غير الظاهرة.

---

<a id="-phase-3-live-host-discovery"></a>
## ✅ المرحلة 3: اكتشاف الأجهزة المستجيبة

> **الهدف:** تصفية النطاقات الفرعية لتحديد الأجهزة المستجيبة والقابلة للوصول فقط.

<a id="http-probing-with-httpx"></a>
### اختبار الاستجابة عبر HTTP باستخدام HTTPX

```bash
# اختبار استجابة أساسي على عدة منافذ
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# اختبار مفصل يعرض رموز الحالة والعناوين والتقنيات
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# الخيارات:
# -sc: رمز الحالة
# -title: عنوان الصفحة
# -server: ترويسة الخادم
# -td: تحديد التقنيات
```

<a id="visual-recon-with-aquatone"></a>
### الاستطلاع البصري باستخدام Aquatone

```bash
# التقاط صور للأجهزة المستجيبة
cat hosts.txt | aquatone

# منافذ مخصصة
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# نطاق منافذ موسّع لفحص أشمل
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **لماذا لقطات الشاشة؟** التعرف بسرعة على التطبيقات المهمة ولوحات تسجيل الدخول وصفحات الخطأ.

---

<a id="-phase-4-url-collection"></a>
## 🔗 المرحلة 4: جمع عناوين URL

> **الهدف:** جمع أكبر عدد ممكن من عناوين URL لاختبار المعاملات والبحث عن الثغرات.

<a id="active-crawling"></a>
### الزحف النشط

<a id="katana"></a>
#### Katana
```bash
# زاحف ويب سريع لاكتشاف عناوين URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: عمق زحف من مستويين
# سريع وفعال لتطبيقات الويب الحديثة
```

<a id="hakrawler"></a>
#### Hakrawler
```bash
# زاحف ويب بسيط وسريع
cat urls.txt | hakrawler -u > crawled_urls.txt
```

<a id="passive-url-collection"></a>
### جمع عناوين URL بصورة سلبية

<a id="gau-get-all-urls"></a>
#### أداة GAU (Get All URLs)
```bash
# جمع عناوين URL المعروفة من AlienVault OTX وWayback Machine وCommon Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# جمع عناوين ذات رمز حالة 200 وإزالة التكرار
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **لماذا GAU؟** يعثر على عناوين URL من بيانات تاريخية قد تكشف نقاط نهاية غير ظاهرة.

<a id="urlfinder"></a>
#### URLFinder
```bash
# العثور على عناوين URL من مصادر متعددة
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

<a id="parameter-extraction"></a>
### استخراج المعاملات

```bash
# استخراج عناوين URL التي تحتوي معاملات مفيدة للاختبار
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# مطابقة أنماط المعاملات
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# نمط SQLi في GF: عناوين URL مرشحة لفحص حقن SQL
cat allurls.txt | gf sqli

# نمط XSS في GF
cat allurls.txt | gf xss

# نمط LFI في GF
cat allurls.txt | gf lfi
```
> 💡 **أنماط GF:** ثبّت `gf` وحمّل الأنماط من github.com/1ndianl33t/Gf-Patterns وفق إرشاد الأصل.

---

<a id="-phase-5-vulnerability-hunting"></a>
## 🔓 المرحلة 5: البحث عن الثغرات

<a id="-xss-discovery"></a>
### 🔴 اكتشاف البرمجة النصية عبر المواقع (XSS)

<a id="reflected-xss-testing"></a>
#### اختبار XSS المنعكسة

> **تصحيح لوصف السلسلة:** الأوامر التالية تجمع عناوين URL وترشحها وتزيل التكرار؛ لا تحقن حمولة XSS ولا تؤكد تنفيذ JavaScript. مخرجاتها مرشحات للتحقق، وليست ثغرات XSS مثبتة.

```bash
# سلسلة شاملة لاختبار XSS المنعكسة وفق وصف الأصل
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

<a id="blind-xss-testing"></a>
#### اختبار XSS العمياء
```bash
# اختبار XSS العمياء بحمولات مخصصة
# استخدام خدمات مثل XSS Hunter أو Burp Collaborator
# إدراج الحمولة في حقول الإدخال والترويسات والمعاملات وفق نطاق الاختبار
```
> 💡 **XSS العمياء:** قد تُنفذ الحمولات لاحقاً عند عرض المسؤول للبيانات؛ استخدم خوادم استقبال الإشارات وفق إرشاد الأصل.

<a id="-lfi-discovery"></a>
### 🔴 اكتشاف تضمين الملفات المحلية (LFI)

```bash
# اختبار LFI باستخدام FFUF والبحث عن محتوى ملف passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# اختبار LFI باستخدام curl والمعالجة المتوازية
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# اختبار LFI باستخدام HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **نصيحة LFI:** جرّب `/etc/passwd` و`....//....//etc/passwd` وصيغاً مرمّزة وفق إرشاد الأصل.

<a id="-open-redirect-discovery"></a>
### 🔴 اكتشاف إعادة التوجيه المفتوحة (Open Redirect)

> **ملاحظة تحقق:** مطابقة النص `evil.com` بعد اتباع التحويلات لا تثبت وحدها إعادة توجيه مفتوحة؛ قد يكون النص منعكساً داخل المحتوى. يلزم فحص سلسلة الاستجابات والوجهة الفعلية لتأكيد السلوك.


```bash
# العثور على معاملات إعادة التوجيه
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# نمط إعادة التوجيه في GF
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# اختبار معاملات إعادة التوجيه
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# سلسلة كاملة لاختبار إعادة التوجيه المفتوحة
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

<a id="-ssrf-discovery"></a>
### 🔴 اكتشاف تزوير الطلبات من جانب الخادم (SSRF)

> **ملاحظة AWS:** مثال البيانات الوصفية المباشر يعكس أسلوب IMDSv1. توضح [وثائق AWS](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html) أن IMDSv2 يستخدم رمز جلسة، وأن تقييد المثيل به يجعل طلبات IMDSv1 تفشل. عنوان البيانات الوصفية وحده لا يضمن الوصول أو كشف أسرار.


```bash
# العثور على معاملات مرشحة لفحص SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# العثور على أنماط API وWebhook
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# فحص SSRF باستخدام Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# اختبار SSRF أساسي نحو localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# اختبار SSRF نحو بيانات AWS الوصفية
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **أثر SSRF:** قد تتيح الوصول إلى خدمات داخلية وبيانات وصفية سحابية وغيرها.

<a id="-cors-testing"></a>
### 🔴 اختبار مشاركة الموارد عبر الأصول (CORS)

> **تصحيح مهم:** الجمع بين `Access-Control-Allow-Origin: *` وبيانات الاعتماد لا يثبت ثغرة حرجة تلقائياً؛ توضح [Mozilla](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials) أن المتصفح يمنع مشاركة الاستجابة مع الطلبات المعتمدة على بيانات اعتماد عند استخدام `*`. تُقيّم الخطورة وفق استغلال مثبت والبيانات القابلة للقراءة.


```bash
# اختبار إعداد CORS بأصل طلب مخصص
curl -H "Origin: http://evil.com" -I https://example.com/api/

# تحليل CORS مفصل
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# فحص CORS آلي باستخدام Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **ثغرة CORS:** وجود `Access-Control-Allow-Origin: *` مع بيانات الاعتماد يعني خطورة حرجة وفق ادعاء الأصل؛ راجع التصحيح أعلاه.

<a id="nuclei-vulnerability-scanning"></a>
### فحص الثغرات باستخدام Nuclei

```bash
# هدف واحد
nuclei -u https://example.com -bs 50 -c 30

# عدة أهداف
nuclei -l live_domains.txt -bs 50 -c 30

# درجات الخطورة الحرجة والعالية فقط
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: حجم الدفعة
# -c: التزامن
```

---

<a id="-phase-6-directory--file-discovery"></a>
## 📂 المرحلة 6: اكتشاف المجلدات والملفات

<a id="sensitive-file-discovery"></a>
### اكتشاف الملفات الحساسة

```bash
# امتدادات ملفات حساسة أساسية
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# تعبير نمطي موسع للملفات الحساسة
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# استعلام Google متقدم للملفات
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

<a id="directory-bruteforcing"></a>
### اكتشاف المجلدات بالقوة الغاشمة

<a id="dirsearch"></a>
#### Dirsearch
```bash
# اكتشاف المجلدات الأساسي
dirsearch -u https://example.com --full-url --deep-recursive -r

# توسيع الفحص بعدة امتدادات
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

<a id="ffuf-directory-discovery"></a>
#### اكتشاف المجلدات باستخدام FFUF
```bash
# اكتشاف شامل للمجلدات بالقوة الغاشمة باستخدام FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

<a id="git-exposure-detection"></a>
### اكتشاف انكشاف مستودعات Git

```bash
# اكتشاف مجلدات .git المكشوفة
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# فحص .git/config
curl -s https://example.com/.git/config

# استخراج مستودع Git إذا كان مكشوفاً
git-dumper https://example.com/.git/ output/
```
> ⚠️ **انكشاف Git:** قد يكشف الشيفرة المصدرية وبيانات الاعتماد والأسرار.

<a id="hidden-parameter-discovery-with-arjun"></a>
### اكتشاف المعاملات المخفية باستخدام Arjun

```bash
# اكتشاف المعاملات بصورة سلبية
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# اكتشاف المعاملات نشطاً باستخدام قائمة كلمات
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

<a id="subdomain-takeover"></a>
### الاستيلاء على النطاقات الفرعية (Subdomain Takeover)

```bash
# اكتشاف آلي لاحتمال الاستيلاء على نطاق فرعي
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **مؤشرات الاستيلاء:** سجلات CNAME تشير إلى موارد غير محجوزة في خدمات مثل S3 وHeroku وغيرها

<a id="wordpress-testing"></a>
### اختبار WordPress

```bash
# فحص أمني شامل لـ WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: جميع القوالب
# -e ap: جميع الإضافات
# -e u: المستخدمون
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="recon-pipeline"></a>
### سلسلة خطوات الاستطلاع

```bash
# 1. حصر النطاقات الفرعية
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. اكتشاف الأجهزة المستجيبة
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. جمع عناوين URL
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. عناوين URL التي تحتوي معاملات
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. فحص الثغرات
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

<a id="essential-one-liners"></a>
### أوامر ضرورية في سطر واحد

| الغرض | الأمر |
|---------|---------|
| العثور على النطاقات الفرعية | `subfinder -d target.com -silent` |
| التحقق من الأجهزة المستجيبة | `cat subs.txt \| httpx -silent` |
| جمع عناوين URL | `echo target.com \| gau` |
| العثور على المعاملات | `cat urls.txt \| grep '='` |
| اختبار إعادة التوجيه | `cat urls.txt \| gf redirect` |
| فحص Nuclei | `nuclei -l hosts.txt -s critical,high` |

<a id="file-extensions-to-hunt"></a>
### امتدادات الملفات التي تستحق البحث

| النوع | الامتدادات |
|------|------------|
| **الإعدادات** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **النسخ الاحتياطية** | `.bak, .old, .backup, .zip, .tar.gz` |
| **الشيفرة المصدرية** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **البيانات** | `.sql, .db, .sqlite, .json, .xml` |
| **المستندات** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-tool-installation"></a>
## 🛠️ تثبيت الأدوات

> **ملاحظة توافق:** هذا تجميع لأمثلة أدوات متعددة الإصدارات. قد تختلف خياراتها ومسارات القوالب ومصادر البيانات، كما أن ملفات مثل `payloads/lfi.txt` وقوائم الإدخال ليست منشأة تلقائياً بهذه الصفحة. أُبقيت أوامر الأصل كما هي؛ راجع مساعدة إصدار الأداة قبل تطبيق المثال في المختبر.


<a id="essential-tools"></a>
### الأدوات الضرورية

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

<a id="xss--redirect-tools"></a>
### أدوات XSS وإعادة التوجيه

```bash
# أداة OR لاختبار إعادة التوجيه المفتوحة
go install -v github.com/h6nt3r/or@latest

# أداة RC لاختبار XSS المنعكسة
go install -v github.com/h6nt3r/rc@latest

# أداة CXSS لفحص XSS بحسب السياق
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# أداة GF لمطابقة الأنماط باستخدام grep
go install github.com/tomnomnom/gf@latest
```

<a id="utility-tools"></a>
### أدوات مساعدة

```bash
# أداة URO لإزالة تكرار عناوين URL
pip3 install uro

# أداة Arjun لاكتشاف المعاملات
pip3 install arjun

# أداة Subzy لاكتشاف احتمال الاستيلاء
go install -v github.com/PentestPad/subzy@latest
```

---

<a id="-bug-bounty-tips"></a>
## 🎯 نصائح لبرامج مكافآت الثغرات

1. **أتمت العمل مع التحقق اليدوي** - تحدد الأدوات مؤشرات مرشحة، وأنت تؤكد الثغرات
2. **راجع robots.txt وsitemap.xml** - قد يكشفان مسارات غير ظاهرة
3. **راجع ملفات JavaScript** - نقاط نهاية API وأسرار وبيانات اعتماد مضمّنة في الشيفرة
4. **اختبر جميع المعاملات** - بما فيها المخفية باستخدام Arjun
5. **اربط الثغرات عند وجود مسار مثبت** - SSRF مع XSS قد يزيد الأثر وفق سياق الاستغلال
6. **جودة التقرير مهمة** - إثبات مفهوم واضح وأثر وخطوات إعادة إنتاج
7. **تحلَّ بالصبر** - اكتشاف ثغرات مهمة يحتاج إلى وقت

---

<a id="-related-cheatsheets"></a>
## 📚 أدلة مرجعية ذات صلة

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [البحث المتقدم في Google](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.ar.md)

---

<p align="center">
  <b>🐛 بحث موفق عن الثغرات!</b><br>
  <i>برامج مكافآت الثغرات — المثابرة قد تؤتي ثمارها</i>
</p>
