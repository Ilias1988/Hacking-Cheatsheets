<a id="-shodan---complete-cheatsheet"></a>
# 🌐 Shodan — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   The Search Engine for Internet-Connected Devices
```

يصف الشعار Shodan بأنه محرك بحث للأجهزة المتصلة بالإنترنت.

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 محرك بحث للأجهزة والخوادم والبنية التحتية المتصلة بالإنترنت</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [البدء](#-getting-started)
- [المرشحات الأساسية](#-basic-filters)
- [مرشحات الشبكة](#-network-filters)
- [مرشحات SSL/TLS](#-ssltls-filters)
- [مرشحات HTTP](#-http-filters)
- [خدمات قد تتأثر بثغرات](#-vulnerable-services)
- [إنترنت الأشياء وأنظمة ICS/SCADA](#-iot--icsscada)
- [قواعد البيانات](#-databases)
- [الكاميرات والوسائط](#-webcams--media)
- [الخدمات السحابية](#-cloud-services)
- [استعلامات لمكافآت الثغرات](#-bug-bounty-queries)
- [واجهة أوامر Shodan](#-shodan-cli)
- [مرجع سريع](#-quick-reference)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Shodan** محرك بحث يتيح العثور على أنواع محددة من الحواسيب والخوادم وأجهزة إنترنت الأشياء باستخدام مرشحات متنوعة لبيانات الخدمات المفهرسة.

<a id="what-can-you-find"></a>
### ما الذي يمكنك العثور عليه؟

|الفئة|أمثلة|
|----------|----------|
|**الخوادم**|خوادم ويب وFTP وSSH وقواعد بيانات|
|**أجهزة إنترنت الأشياء**|كاميرات وموجهات وأجهزة ذكية|
|**ICS/SCADA**|أنظمة تحكم صناعي|
|**السحابة**|مثيلات AWS وAzure وGCP|
|**مؤشرات ثغرات**|خدمات مكشوفة ونصوص تشير إلى بيانات اعتماد افتراضية|

<a id="shodan-urls"></a>
### روابط خدمات Shodan

|الخدمة|URL|
|---------|-----|
|**البحث على الويب**|https://www.shodan.io|
|**الخرائط**|https://maps.shodan.io|
|**مراجع الاستغلال**|https://exploits.shodan.io|
|**الصور**|https://images.shodan.io|

---

<a id="-getting-started"></a>
## 🚀 البدء

<a id="create-account"></a>
### إنشاء حساب

1. انتقل إلى https://www.shodan.io
2. أنشئ حسابًا مجانيًا
3. احصل على مفتاح API من إعدادات الحساب

<a id="membership-tiers"></a>
### خطط العضوية

> **ملاحظة:** الأسعار والحدود أدناه منقولة من الأصل وليست عرضًا حاليًا أو ضمانًا لوصول غير محدود. كما يدرج مرجع Shodan المرشحين `tag` و`vuln` ضمن الخيارات المقيدة بخطط أعلى. [مرجع المرشحات الرسمي](https://www.shodan.io/search/filters).

|الخطة|السعر بحسب الأصل|الميزات بحسب الأصل|
|------|-------|----------|
|**مجانية**|$0|بحث أساسي ونتائج محدودة|
|**عضوية**|$49|نتائج أكثر وتنزيلات ودعم CLI|
|**شركات صغيرة**|$299/mo|أرصدة أكثر ومراقبة|
|**مؤسسات**|$899/mo|وصول غير محدود بحسب الأصل|

<a id="api-key"></a>
### مفتاح API

```bash
# Get from: https://account.shodan.io
# تصدير المتغير لاستخدامه ضمن بيئة الأوامر بحسب الأصل
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="-basic-filters"></a>
## 🔧 المرشحات الأساسية

<a id="text-search"></a>
### البحث النصي

```
# بحث بسيط
apache
nginx
Microsoft-IIS

# البحث عن منتج
product:apache
product:nginx
product:openssh
```

<a id="country--city"></a>
### البلد والمدينة

```
# البلد برمز ISO 3166-1 alpha-2
country:US
country:DE
country:RU
country:CN
country:GR                      # اليونان

# المدينة
city:Athens
city:"New York"
city:London
city:Tokyo

# دمج المرشحات
country:US city:Miami
country:GR city:Thessaloniki
```

<a id="organization--isp"></a>
### المؤسسة ومزود الإنترنت

```
# المؤسسة
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

<a id="hostname--domain"></a>
### اسم المضيف والنطاق

```
# البحث ضمن اسم المضيف
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# نطاق محدد
ssl.cert.subject.cn:target.com
```

<a id="operating-system"></a>
### نظام التشغيل

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="-network-filters"></a>
## 🌐 مرشحات الشبكة

<a id="port"></a>
### المنفذ

```
# منفذ محدد
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# منافذ متعددة
port:22,80,443
```

<a id="ip--network"></a>
### عنوان IP والشبكة

```
# عنوان IP محدد
ip:8.8.8.8

# نطاق IP بصيغة CIDR
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

<a id="geo-location"></a>
### الموقع الجغرافي

```
# الإحداثيات الجغرافية
geo:37.9838,23.7275             # إحداثيات أثينا
geo:40.7128,-74.0060,10         # مدينة نيويورك مع نصف قطر

# المنطقة أو الولاية
region:California
region:"New York"
```

---

<a id="-ssltls-filters"></a>
## 🔐 مرشحات SSL/TLS

<a id="certificate-information"></a>
### معلومات الشهادة

> **تصحيح:** الصيغة `ssl.cert.issuer.cn:ssl.cert.subject.cn` لا تقارن قيم الحقلين؛ موضع ما بعد النقطتين هو قيمة بحث، لا مرجع حقل آخر. لذلك لا تثبت الأمثلة الشهادات ذاتية التوقيع بهذه الطريقة. [صيغة البحث الرسمية](https://help.shodan.io/the-basics/search-query-fundamentals).

```
# الجهة التي صدرت لها الشهادة
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# الجهة المصدرة للشهادة
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# شهادات موقعة ذاتيًا بحسب الأصل؛ راجع التصحيح
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

<a id="ssl-versions"></a>
### إصدارات SSL/TLS

```
# إصدارات SSL/TLS قديمة أو ضعيفة بحسب الأصل
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# إصدارات أحدث بحسب الأصل
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

<a id="certificate-expiry"></a>
### انتهاء صلاحية الشهادة

السنة `2024` في الأصل ثابتة، ولا تعني «ستنتهي قريبًا» بالنسبة إلى وقت استخدام الدليل. تحقّق أيضًا من دعم مرشح `ssl.cert.expires` في إصدار الخدمة؛ المرجع الحالي يدرج `ssl.cert.expired` ولا يدرج هذا الاسم. [مرجع المرشحات](https://www.shodan.io/search/filters).

```
# شهادات منتهية الصلاحية
ssl.cert.expired:true

# شهادات توشك على الانتهاء بحسب الأصل؛ السنة المعروضة ثابتة
ssl.cert.expires:2024
```

<a id="ssl-vulnerabilities"></a>
### ثغرات SSL/TLS

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="-http-filters"></a>
## 🌍 مرشحات HTTP

<a id="http-status--headers"></a>
### حالة HTTP وترويساته

```
# رمز حالة HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# عنوان صفحة HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# محتوى HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

<a id="web-technologies"></a>
### تقنيات الويب

```
# ترويسة الخادم
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# إصدارات محددة
http.server:"Apache/2.4.49"     # إصدار يصفه الأصل بأنه متأثر؛ يلزم التحقق
http.server:"nginx/1.18"

# تجزئة أيقونة الموقع (Favicon Hash)
http.favicon.hash:116323821     # أيقونة موقع محددة
http.favicon.hash:-1137975059   # Jenkins
```

<a id="components"></a>
### المكونات

```
# أطر عمل الويب
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# التقنيات
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="-vulnerable-services"></a>
## ⚠️ خدمات قد تتأثر بثغرات

> **توضيح للنتائج:** يبحث Shodan في بيانات الخدمة المفهرسة (Banner). وجود كلمات مثل `admin:admin` أو غياب كلمة `authentication` من النص أو ظهور عنوان لوحة لا يثبت صلاحية بيانات الدخول أو غياب المصادقة. تبقى النتائج مؤشرات تحتاج إلى تقييم ضمن التفويض، لا أحكامًا مؤكدة. [شرح البيانات المفهرسة الرسمي](https://help.shodan.io/the-basics/search-query-fundamentals).

<a id="known-cves"></a>
### معرفات CVE معروفة

```
# البحث بمعرف CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# معرفات CVE متعددة
vuln:CVE-2021-44228,CVE-2021-45046
```

<a id="default-credentials"></a>
### بيانات اعتماد افتراضية بحسب النص المفهرس

```
# كلمات مرور افتراضية ضمن النص
"default password"
"admin" "password"
http.title:"Login" "admin"

# قيم افتراضية محددة ضمن النص
"admin:admin"
"root:root"
"admin:123456"
```

<a id="openexposed-services"></a>
### خدمات مكشوفة بحسب مؤشرات البحث

```
# وصول FTP مجهول بحسب النص المفهرس
port:21 "Anonymous access granted"

# خدمة Telnet مكشوفة بحسب الأصل
port:23 -authentication

# خدمة MongoDB مكشوفة بحسب الأصل
port:27017 "MongoDB Server Information"

# خدمة Redis مكشوفة بحسب الأصل
port:6379 -authentication

# خدمة Elasticsearch مكشوفة بحسب الأصل
port:9200 "cluster_name"

# خدمة Memcached مكشوفة بحسب الأصل
port:11211 "STAT"
```

<a id="exposed-panels"></a>
### لوحات إدارة مكشوفة

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# لوحة Jenkins دون مصادقة بحسب الأصل؛ العنوان وحده لا يثبت ذلك
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="-iot--icsscada"></a>
## 🏭 إنترنت الأشياء وأنظمة ICS/SCADA

<a id="industrial-control-systems"></a>
### أنظمة التحكم الصناعي

```
# أنظمة ICS عامة
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

<a id="scada-systems"></a>
### أنظمة التحكم والمراقبة SCADA

```
# SCADA
tag:scada

# أنظمة SCADA محددة
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

<a id="iot-devices"></a>
### أجهزة إنترنت الأشياء

```
# أجهزة إنترنت أشياء عامة
tag:iot

# الموجهات
"router" port:80
http.title:"Router"

# الطابعات
port:9100
"printer"
http.title:"HP"

# المنزل الذكي
"smart home"
"IoT Gateway"
```

<a id="network-devices"></a>
### أجهزة الشبكة

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="-databases"></a>
## 💾 قواعد البيانات

<a id="mysql"></a>
### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

<a id="postgresql"></a>
### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

<a id="mongodb"></a>
### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

<a id="redis"></a>
### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

<a id="elasticsearch"></a>
### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

<a id="couchdb"></a>
### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

<a id="memcached"></a>
### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

<a id="cassandra"></a>
### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="-webcams--media"></a>
## 📹 الكاميرات والوسائط

<a id="ip-cameras"></a>
### كاميرات IP

```
# كاميرات ويب عامة
webcam
"webcam" country:US

# علامات تجارية للكاميرات
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# تدفقات RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

<a id="dvrnvr-systems"></a>
### أنظمة تسجيل الفيديو DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

<a id="streaming"></a>
### البث

```
# البث باستخدام VLC
"VLC media player"

# خوادم الوسائط
product:plex
"Plex Media Server"
```

<a id="specific-camera-interfaces"></a>
### واجهات كاميرات محددة

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="-cloud-services"></a>
## ☁️ الخدمات السحابية

<a id="amazon-aws"></a>
### خدمات Amazon AWS

```
# خدمات AWS عامة
org:"Amazon"
asn:AS16509

# حاويات S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# خدمات AWS
http.title:"AWS"
```

<a id="microsoft-azure"></a>
### خدمات Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

<a id="google-cloud"></a>
### خدمات Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

<a id="digitalocean"></a>
### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

<a id="exposed-cloud-panels"></a>
### لوحات سحابية مكشوفة

```
# لوحة إدارة Kubernetes
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="-bug-bounty-queries"></a>
## 🐛 استعلامات لمكافآت الثغرات

<a id="organization-recon"></a>
### استطلاع المؤسسة

```
# المؤسسة المستهدفة
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# البحث برقم النظام المستقل (ASN)
asn:AS12345
net:1.2.3.0/24
```

<a id="find-exposed-services"></a>
### العثور على خدمات مكشوفة

```
# خدمات الهدف على المنافذ المحددة
org:"Target" port:22,80,443,3306,5432,27017

# لوحات الإدارة
org:"Target" http.title:"admin"

# صفحات تسجيل الدخول
org:"Target" http.title:"login"
```

<a id="subdomain-discovery"></a>
### اكتشاف النطاقات الفرعية

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

<a id="vulnerable-services"></a>
### خدمات قد تتأثر بثغرات

```
# البحث عن مؤشرات CVE
org:"Target" vuln:CVE-2021-44228

# بيانات اعتماد افتراضية ضمن النص
org:"Target" "default password"

# قواعد بيانات مكشوفة بحسب الأصل
org:"Target" port:27017,6379,9200
```

<a id="complete-bug-bounty-query"></a>
### استعلام متكامل لمكافآت الثغرات بحسب الأصل

> **توافق الصيغة:** يستخدم الأصل `||` وأقواس تجميع كما في لغات أخرى. لا يفصل دليل الصيغة الأساسي هذا التركيب؛ أبقيناه حرفيًا، ولا تفترض أنه يعبر عن OR منطقي عام في Shodan دون مراجعة دعم الواجهة المستخدمة. [الدليل الرسمي للصيغة](https://help.shodan.io/the-basics/search-query-fundamentals).

```
# استطلاع متكامل
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# باستخدام مرشحات
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="-shodan-cli"></a>
## 💻 واجهة أوامر Shodan

<a id="installation"></a>
### التثبيت

```bash
# التثبيت باستخدام pip
pip install shodan

# تهيئة الأداة باستخدام مفتاح API
shodan init YOUR_API_KEY
```

<a id="basic-commands"></a>
### الأوامر الأساسية

```bash
# معلومات الحساب
shodan info

# البحث
shodan search "apache"

# البحث باستخدام المرشحات
shodan search "port:22 country:US"

# عد النتائج
shodan count "nginx"

# تنزيل النتائج
shodan download results.json.gz "apache"

# تحليل النتائج
shodan parse results.json.gz

# الحصول على معلومات المضيف
shodan host 8.8.8.8
```

<a id="advanced-cli"></a>
### أوامر متقدمة

```bash
# معلومات النطاق
shodan domain target.com

# إرسال مهمة فحص تتطلب أرصدة
shodan scan submit 1.2.3.4

# درجة Honeyscore لتقدير احتمال كون الجهاز فخًا
shodan honeyscore 1.2.3.4

# معلومات عنوان IP الحالي
shodan myip

# الإحصاءات
shodan stats "apache" --facets country,org
```

<a id="api-usage-python"></a>
### استخدام API بلغة Python

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# البحث
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# البحث عن المضيف
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-filters"></a>
### المرشحات الأساسية

|المرشح|الوصف|المثال|
|--------|-------------|---------|
|`port:`|رقم المنفذ|`port:22`|
|`country:`|رمز البلد|`country:US`|
|`city:`|اسم المدينة|`city:London`|
|`org:`|المؤسسة|`org:"Google"`|
|`hostname:`|اسم المضيف|`hostname:google.com`|
|`net:`|نطاق IP بصيغة CIDR|`net:10.0.0.0/8`|
|`os:`|نظام التشغيل|`os:"Windows"`|
|`product:`|اسم المنتج|`product:nginx`|
|`version:`|الإصدار|`version:"2.4.49"`|
|`vuln:`|معرف CVE|`vuln:CVE-2021-44228`|

<a id="http-filters"></a>
### مرشحات HTTP

|المرشح|الوصف|المثال|
|--------|-------------|---------|
|`http.title:`|عنوان الصفحة|`http.title:"Admin"`|
|`http.status:`|حالة HTTP|`http.status:200`|
|`http.server:`|ترويسة الخادم|`http.server:nginx`|
|`http.html:`|محتوى HTML|`http.html:"login"`|
|`http.component:`|التقنية|`http.component:"WordPress"`|

<a id="ssl-filters"></a>
### مرشحات SSL

|المرشح|الوصف|المثال|
|--------|-------------|---------|
|`ssl.cert.subject.cn:`|الاسم الشائع CN في الشهادة|`ssl.cert.subject.cn:target.com`|
|`ssl.cert.issuer.cn:`|الاسم الشائع للجهة المصدرة|`ssl.cert.issuer.cn:"DigiCert"`|
|`ssl.version:`|إصدار SSL/TLS|`ssl.version:sslv3`|
|`ssl.cert.expired:`|شهادة منتهية الصلاحية|`ssl.cert.expired:true`|

<a id="top-10-shodan-queries"></a>
### عشرة استعلامات Shodan بحسب الأصل

|#|الاستعلام|الغرض|
|---|-------|---------|
|1|`port:22 country:US`|خدمات SSH في الولايات المتحدة|
|2|`port:3389 country:US`|خدمات RDP في الولايات المتحدة|
|3|`port:27017 -authentication`|MongoDB مكشوفة بحسب الأصل|
|4|`vuln:CVE-2021-44228`|Log4Shell|
|5|`http.title:"Dashboard"`|لوحات إدارة|
|6|`"default password"`|نصوص بيانات اعتماد افتراضية|
|7|`port:9200 "elastic"`|Elasticsearch|
|8|`webcam`|كاميرات IP|
|9|`product:apache vuln:`|خوادم Apache يصفها الأصل بأنها متأثرة|
|10|`ssl.cert.expired:true`|شهادات منتهية الصلاحية|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="bug-bounty-tips"></a>
### نصائح لمكافآت الثغرات

1. **ابدأ بالبحث عن الشهادات**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **افحص المنافذ المذكورة**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **ابحث عن الخدمات المكشوفة**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **ابحث عن مؤشرات الثغرات**
   ```
   org:"Target" vuln:
   ```

<a id="combine-filters"></a>
### دمج المرشحات

```
# استعلامات مركبة
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** يعرض Shodan معلومات مفهرسة من خدمات يمكن الوصول إليها علنًا؛ ومع ذلك:
> 
> - ✅ استخدمه في استطلاع مصرح به
> - ✅ التزم بأهداف مكافآت الثغرات الواقعة ضمن النطاق
> - ✅ أبلغ عن الثغرات بمسؤولية
> - ❌ لا تستغل الثغرات المكتشفة بحسب تنبيه الأصل
> - ❌ لا تصل إلى الأنظمة دون إذن
> - ❌ لا تنتهك الخصوصية أو القوانين
> 
> **بحسب تنبيه الأصل، قد يخالف الفحص دون تفويض القانون.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [Shodan](https://www.shodan.io)
- [مركز مساعدة Shodan](https://help.shodan.io)
- [واجهة أوامر Shodan](https://cli.shodan.io)
- [واجهة API الخاصة بـ Shodan](https://developer.shodan.io)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [البحث المتقدم في Google](../../Google-Dorking/translations/README.ar.md)
- [البحث المتقدم في GitHub](../../GitHub-Dorking/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)

---

<p align="center">
  <b>🌐 افهم الخدمات الظاهرة على الإنترنت!</b><br>
  <i>أتقن استخدام Shodan في الاستطلاع</i>
</p>
