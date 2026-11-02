<a id="-nmap---complete-cheatsheet"></a>
# 🔍 Nmap — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - The Network Scanner
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 أداة متقدمة لفحص الشبكات واكتشاف أجهزتها</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [تحديد الأهداف](#-target-specification)
- [اكتشاف الأجهزة](#-host-discovery)
- [تقنيات فحص المنافذ](#-port-scanning-techniques)
- [تحديد المنافذ](#-port-specification)
- [تحديد الخدمات وإصداراتها](#-service--version-detection)
- [تحديد نظام التشغيل](#-os-detection)
- [التوقيت والأداء](#-timing--performance)
- [سكربتات محرك Nmap (NSE)](#-nse-scripts)
- [صيغ المخرجات](#-output-formats)
- [تفادي جدار الحماية وأنظمة كشف التسلل](#-firewallids-evasion)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Nmap**، اختصار Network Mapper، أداة مجانية ومفتوحة المصدر لاكتشاف الشبكات والتدقيق الأمني، وتُستخدم على نطاق واسع في فحص الشبكات.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **اكتشاف الأجهزة** | العثور على الأجهزة المستجيبة داخل الشبكة |
| **فحص المنافذ** | تحديد المنافذ المفتوحة والخدمات |
| **تحديد الإصدارات** | تحديد إصدارات الخدمات |
| **تحديد نظام التشغيل** | تحديد أنظمة التشغيل |
| **سكربتات محرك Nmap (NSE)** | فحص الثغرات واستغلالها |
| **صيغ المخرجات** | XML وصيغة قابلة للمعالجة بـ grep والصيغة العادية |

<a id="port-states"></a>
### حالات المنافذ

> **توضيح المصطلح:** تعني filtered أن الاستجابة لا تكفي لحسم حالة المنفذ بسبب تصفية الحزم أو عائق شبكي؛ لا تثبت وحدها أن خدمة ما مغلقة. حُفظت أسماء الحالات الإنجليزية لأنها قيم تقنية تظهر في المخرجات.


| الحالة | الوصف |
|-------|-------------|
| `open` | تطبيق يقبل الاتصالات |
| `closed` | المنفذ قابل للوصول ولا يستمع عليه تطبيق |
| `filtered` | لا يستطيع Nmap تحديد ما إذا كان مفتوحاً بسبب التصفية |
| `unfiltered` | المنفذ قابل للوصول لكن حالة انفتاحه غير معروفة |
| `open\|filtered` | يتعذر التمييز بين مفتوح ومصفّى |
| `closed\|filtered` | يتعذر التمييز بين مغلق ومصفّى |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبت مسبقاً)
```bash
# تحديث Nmap
sudo apt update && sudo apt install nmap
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install nmap
```

<a id="centosrhel"></a>
### CentOS/RHEL
```bash
sudo yum install nmap
```

<a id="windows"></a>
### Windows
```bash
# التنزيل من https://nmap.org/download.html
# أو استخدام Chocolatey
choco install nmap
```

<a id="macos"></a>
### macOS
```bash
brew install nmap
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
nmap --version
nmap -h          # المساعدة
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
nmap [Scan Type] [Options] {target}
```

<a id="quick-start-examples"></a>
### أمثلة للبداية السريعة
```bash
# فحص أساسي
nmap 192.168.1.1

# فحص عدة أجهزة
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# فحص نطاق
nmap 192.168.1.1-254

# فحص الشبكة الفرعية كاملة
nmap 192.168.1.0/24

# الفحص من ملف
nmap -iL targets.txt
```

---

<a id="-target-specification"></a>
## 🎯 تحديد الأهداف

<a id="single-targets"></a>
### أهداف منفردة
```bash
# عنوان IP
nmap 192.168.1.1

# اسم المضيف
nmap scanme.nmap.org

# اسم المجال
nmap example.com
```

<a id="multiple-targets"></a>
### أهداف متعددة
```bash
# أهداف مفصولة بمسافات
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# نطاق
nmap 192.168.1.1-100

# صيغة CIDR
nmap 192.168.1.0/24

# حرف بدل
nmap 192.168.1.*

# نطاق لقيمة مقطع Octet
nmap 192.168.0-255.1-254

# تحديد مختلط
nmap 192.168.1.0/24 10.0.0.1
```

<a id="input-from-file"></a>
### الإدخال من ملف
```bash
# القراءة من ملف
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

<a id="exclude-targets"></a>
### استبعاد أهداف
```bash
# استبعاد جهاز واحد
nmap 192.168.1.0/24 --exclude 192.168.1.1

# الاستبعاد من ملف
nmap 192.168.1.0/24 --excludefile exclude.txt
```

<a id="random-targets"></a>
### أهداف عشوائية
```bash
# فحص أجهزة عشوائية لأغراض البحث وفق الأصل
nmap -iR 100
```

---

<a id="-host-discovery"></a>
## 🔎 اكتشاف الأجهزة

<a id="discovery-options"></a>
### خيارات الاكتشاف

| الخيار | الوصف |
|--------|-------------|
| `-sL` | فحص القائمة: عرض الأهداف فقط |
| `-sn` | فحص Ping دون فحص المنافذ |
| `-Pn` | تجاوز اكتشاف الأجهزة واعتبارها جميعاً متصلة |
| `-PS` | استطلاع TCP SYN |
| `-PA` | استطلاع TCP ACK |
| `-PU` | استطلاع UDP |
| `-PE` | استطلاع ICMP Echo |
| `-PP` | استطلاع الطابع الزمني عبر ICMP |
| `-PM` | استطلاع قناع الشبكة عبر ICMP |
| `-PO` | استطلاع بروتوكول IP |
| `-PR` | استطلاع ARP في الشبكة المحلية |

<a id="ping-scan-no-port-scan"></a>
### فحص Ping دون فحص المنافذ
```bash
# اكتشاف الأجهزة المستجيبة فقط
nmap -sn 192.168.1.0/24

# فحص Ping بقائمة أجهزة
nmap -sn -iL hosts.txt
```

<a id="skip-host-discovery"></a>
### تجاوز اكتشاف الأجهزة
```bash
# فحص جميع الأجهزة دون اختبار Ping أولاً
nmap -Pn 192.168.1.1

# مفيد عندما:
# - تحجب الأجهزة ICMP
# - يجري الفحص عبر جدار حماية
```

<a id="tcp-synack-ping"></a>
### استطلاع TCP SYN/ACK
```bash
# استطلاع TCP SYN على المنفذ 80
nmap -PS80 192.168.1.1

# استطلاع TCP ACK على المنفذ 80
nmap -PA80 192.168.1.1

# عدة منافذ
nmap -PS22,80,443 192.168.1.1
```

<a id="udp-ping"></a>
### استطلاع UDP
```bash
# استطلاع UDP
nmap -PU53 192.168.1.1

# عدة منافذ
nmap -PU53,161 192.168.1.1
```

<a id="icmp-ping"></a>
### استطلاع ICMP
```bash
# طلب صدى ICMP
nmap -PE 192.168.1.1

# طابع زمني عبر ICMP
nmap -PP 192.168.1.1

# قناع الشبكة عبر ICMP
nmap -PM 192.168.1.1
```

<a id="arp-ping-local-network"></a>
### استطلاع ARP في الشبكة المحلية
```bash
# فحص ARP، الأسرع محلياً وفق الأصل
nmap -PR 192.168.1.0/24

# فحص ARP فقط
nmap -sn -PR 192.168.1.0/24
```

<a id="disable-dns-resolution"></a>
### تعطيل حل أسماء DNS
```bash
# دون حل أسماء DNS لزيادة السرعة
nmap -n 192.168.1.1

# حل أسماء DNS دائماً
nmap -R 192.168.1.1
```

---

<a id="-port-scanning-techniques"></a>
## 🔌 تقنيات فحص المنافذ

> **توضيح الدقة:** تسميات «خفي» و«يعمل دائماً» في الأصل ليست ضمانات. تعتمد النتائج على ردود الهدف والتصفية والصلاحيات. يوضح [دليل أنواع الفحص الرسمي](https://nmap.org/book/man-port-scanning-techniques.html) أن الفحص الافتراضي ينتقل من SYN إلى Connect عند غياب صلاحيات الحزم الخام، وأن بعض الأنظمة لا تتعامل مع NULL/FIN/Xmas كما تتوقع هذه الفحوصات.


<a id="scan-types-overview"></a>
### نظرة عامة على أنواع الفحص

| الخيار | الاسم | الوصف |
|--------|------|-------------|
| `-sS` | فحص SYN | فحص أقل ظهوراً وفق الأصل؛ الافتراضي ويتطلب root |
| `-sT` | اتصال TCP | اتصال TCP كامل دون حاجة إلى root |
| `-sU` | فحص UDP | فحص منافذ UDP |
| `-sA` | فحص ACK | استكشاف قواعد جدار الحماية |
| `-sW` | فحص نافذة TCP (Window) | مشابه لفحص ACK ويستخدم حقل نافذة TCP |
| `-sM` | Maimon Scan | مسبار FIN/ACK |
| `-sN` | فحص NULL | دون ضبط أعلام TCP |
| `-sF` | فحص FIN | ضبط علم FIN |
| `-sX` | فحص Xmas | ضبط أعلام FIN وPSH وURG |

<a id="syn-stealth-scan-default"></a>
### فحص SYN الأقل ظهوراً وفق الأصل (الافتراضي)
```bash
# الفحص الأكثر شيوعاً ويتطلب root وفق الأصل
sudo nmap -sS 192.168.1.1

# لا يُكمل مصافحة TCP
# سريع وأقل ظهوراً وفق الأصل
```

<a id="tcp-connect-scan"></a>
### فحص اتصال TCP
```bash
# اتصال TCP كامل
nmap -sT 192.168.1.1

# لا يتطلب root
# أسهل رصداً لكنه يعمل دائماً وفق ادعاء الأصل
```

<a id="udp-scan"></a>
### فحص UDP
```bash
# فحص منافذ UDP
sudo nmap -sU 192.168.1.1

# فحص UDP بطيء
# دمجه مع تحديد الإصدارات
sudo nmap -sU -sV 192.168.1.1

# منافذ UDP شائعة
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

<a id="combined-tcpudp"></a>
### فحص TCP وUDP معاً
```bash
# فحص TCP وUDP معاً
sudo nmap -sS -sU 192.168.1.1

# منافذ محددة
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

<a id="null-fin-xmas-scans"></a>
### فحوصات NULL وFIN وXmas
```bash
# فحص NULL دون أعلام
sudo nmap -sN 192.168.1.1

# فحص FIN بضبط علم FIN فقط
sudo nmap -sF 192.168.1.1

# فحص Xmas بأعلام FIN وPSH وURG
sudo nmap -sX 192.168.1.1

# مناسب وفق الأصل لما يلي:
# - تجاوز جدران الحماية عديمة الحالة
# - لا يعمل على Windows وفق الأصل
```

<a id="ack-scan"></a>
### فحص ACK
```bash
# استكشاف قواعد جدار الحماية
sudo nmap -sA 192.168.1.1

# تحديد:
# - المنافذ التي تخضع للتصفية
# - قواعد جدار الحماية
# يعيد الحالة unfiltered أو filtered
```

<a id="window-scan"></a>
### فحص نافذة TCP (Window)
```bash
# مشابه لفحص ACK
sudo nmap -sW 192.168.1.1

# يستخدم حجم نافذة TCP لتحديد الحالة
```

<a id="idlezombie-scan"></a>
### الفحص الخامل عبر جهاز وسيط (Idle/Zombie)
```bash
# تمويه كامل باستخدام جهاز Zombie وفق ادعاء الأصل
sudo nmap -sI zombie_host 192.168.1.1

# لا يصل عنوانك إلى الهدف وفق ادعاء الأصل
# يتطلب جهاز Zombie مناسباً
```

---

<a id="-port-specification"></a>
## 🎚️ تحديد المنافذ

<a id="port-options"></a>
### خيارات المنافذ

| الخيار | الوصف |
|--------|-------------|
| `-p <port>` | فحص منفذ محدد أو عدة منافذ |
| `-p-` | فحص جميع المنافذ البالغ عددها 65535 |
| `-p 1-1000` | فحص نطاق من المنافذ |
| `--top-ports <n>` | فحص أكثر N من المنافذ شيوعاً |
| `-F` | فحص سريع لأكثر 100 منفذ شيوعاً |
| `-r` | فحص المنافذ بترتيب متتابع |

<a id="specific-ports"></a>
### منافذ محددة
```bash
# منفذ واحد
nmap -p 80 192.168.1.1

# عدة منافذ
nmap -p 80,443,22 192.168.1.1

# نطاق منافذ
nmap -p 1-1000 192.168.1.1

# جميع المنافذ
nmap -p- 192.168.1.1

# تحديد مختلط
nmap -p 22,80,443,1000-2000 192.168.1.1
```

<a id="port-by-protocol"></a>
### المنافذ حسب البروتوكول

> **تصحيح لأمثلة الأصل:** البادئتان `T:` و`U:` تحددان قائمة المنافذ ولا تفعّلان نوع الفحص وحدهما. توضح [وثائق Nmap](https://nmap.org/book/man-port-specification.html) أن جمع TCP وUDP يتطلب `-sU` مع نوع فحص TCP. لذلك أمثلة هذا القسم التي تذكر `U:` دون تفعيل UDP ناقصة من حيث اختيار نوع الفحص؛ أُبقيت الأوامر الأصلية دون تعديل.

```bash
# منافذ TCP
nmap -p T:80,443 192.168.1.1

# منافذ UDP
nmap -p U:53,161 192.168.1.1

# كلاهما
nmap -p T:80,443,U:53 192.168.1.1
```

<a id="top-ports"></a>
### المنافذ الأكثر شيوعاً
```bash
# أكثر 100 منفذ شيوعاً، فحص سريع
nmap -F 192.168.1.1

# أكثر 10 منافذ شيوعاً
nmap --top-ports 10 192.168.1.1

# أكثر 1000 منفذ شيوعاً، الافتراضي
nmap --top-ports 1000 192.168.1.1
```

<a id="common-ports-reference"></a>
### مرجع المنافذ الشائعة

| المنفذ | الخدمة |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

<a id="-service--version-detection"></a>
## 🔬 تحديد الخدمات وإصداراتها

<a id="version-detection-options"></a>
### خيارات تحديد الإصدارات

| الخيار | الوصف |
|--------|-------------|
| `-sV` | تحديد الإصدارات |
| `--version-intensity <0-9>` | شدة إرسال المسابير |
| `--version-light` | فحص خفيف للإصدارات بشدة 2 |
| `--version-all` | تجربة جميع المسابير بشدة 9 |
| `--version-trace` | تنقيح فحص الإصدارات |

<a id="basic-version-detection"></a>
### تحديد الإصدارات الأساسي
```bash
# تحديد إصدارات الخدمات
nmap -sV 192.168.1.1

# مخرجات شائعة:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

<a id="version-intensity"></a>
### شدة تحديد الإصدارات
```bash
# فحص خفيف وأسرع
nmap -sV --version-light 192.168.1.1

# جميع المسابير لفحص أعمق
nmap -sV --version-all 192.168.1.1

# شدة مخصصة بين 0-9
nmap -sV --version-intensity 5 192.168.1.1
```

---

<a id="-os-detection"></a>
## 💻 تحديد نظام التشغيل

<a id="os-detection-options"></a>
### خيارات تحديد نظام التشغيل

| الخيار | الوصف |
|--------|-------------|
| `-O` | تفعيل تحديد نظام التشغيل |
| `--osscan-limit` | فحص الأجهزة المرشحة للحصول على نتائج مناسبة فقط |
| `--osscan-guess` | تخمين نظام التشغيل بصورة أكثر توسعاً |
| `--max-os-tries` | أقصى عدد لمحاولات تحديد نظام التشغيل |

<a id="basic-os-detection"></a>
### التحديد الأساسي لنظام التشغيل
```bash
# تحديد نظام التشغيل، يتطلب root وفق الأصل
sudo nmap -O 192.168.1.1

# مثال للمخرجات:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

<a id="aggressive-os-guessing"></a>
### تخمين موسّع لنظام التشغيل
```bash
# تخمين أكثر توسعاً
sudo nmap -O --osscan-guess 192.168.1.1
```

<a id="aggressive-scan--a"></a>
### الفحص الموسّع (-A)
```bash
# تفعيل تحديد النظام والإصدارات والسكربتات وتتبّع المسار
sudo nmap -A 192.168.1.1

# يعادل الخيارات: -O -sV -sC --traceroute
```

---

<a id="-timing--performance"></a>
## ⏱️ التوقيت والأداء

<a id="timing-templates"></a>
### قوالب التوقيت

| الخيار | الاسم | الوصف |
|--------|------|-------------|
| `-T0` | Paranoid | تفادي أنظمة كشف التسلل وفق الأصل، بطيء جداً |
| `-T1` | Sneaky | تفادي أنظمة كشف التسلل وفق الأصل، بطيء |
| `-T2` | Polite | استهلاك أقل لعرض النطاق مع بطء أكبر |
| `-T3` | Normal | التوقيت الافتراضي |
| `-T4` | Aggressive | سريع لشبكة موثوقة |
| `-T5` | Insane | سريع جداً وقد يفوّت منافذ |

<a id="usage"></a>
### الاستخدام
```bash
# بطيء وأقل ظهوراً وفق الأصل
nmap -T1 192.168.1.1

# عادي، الافتراضي
nmap -T3 192.168.1.1

# سريع
nmap -T4 192.168.1.1

# سريع جداً للشبكة المحلية
nmap -T5 192.168.1.1
```

<a id="fine-grained-timing"></a>
### التحكم التفصيلي بالتوقيت

| الخيار | الوصف |
|--------|-------------|
| `--min-rate <n>` | أدنى عدد حزم في الثانية |
| `--max-rate <n>` | أقصى عدد حزم في الثانية |
| `--min-parallelism <n>` | أدنى عدد مسابير متزامنة |
| `--max-parallelism <n>` | أقصى عدد مسابير متزامنة |
| `--min-hostgroup <n>` | أدنى عدد أجهزة تُفحص بالتوازي |
| `--max-hostgroup <n>` | أقصى عدد أجهزة تُفحص بالتوازي |
| `--host-timeout <time>` | التوقف عن فحص الجهاز بعد مهلة |
| `--scan-delay <time>` | التأخير بين المسابير |
| `--max-retries <n>` | أقصى عدد لإعادات إرسال المسبار |

```bash
# إرسال 1000 حزمة في الثانية على الأقل
nmap --min-rate 1000 192.168.1.0/24

# إرسال 100 حزمة في الثانية كحد أقصى لتقليل السرعة
nmap --max-rate 100 192.168.1.1

# التوقف عن فحص الجهاز بعد 30 دقيقة
nmap --host-timeout 30m 192.168.1.0/24

# التأخير بين المسابير
nmap --scan-delay 1s 192.168.1.1
```

---

<a id="-nse-scripts"></a>
## 📜 سكربتات محرك Nmap (NSE)

<a id="script-categories"></a>
### فئات السكربتات

| الفئة | الوصف |
|----------|-------------|
| `auth` | تجاوز المصادقة وفق وصف الأصل |
| `broadcast` | اكتشاف الأجهزة عبر البث العام |
| `brute` | هجمات القوة الغاشمة |
| `default` | السكربتات الافتراضية (-sC) |
| `discovery` | اكتشاف الخدمات |
| `dos` | حجب الخدمة |
| `exploit` | استغلال الثغرات |
| `external` | استعلام خدمات خارجية |
| `fuzzer` | اختبار المدخلات المتغيرة (Fuzzing) |
| `intrusive` | قد تتسبب في تعطل الخدمات |
| `malware` | اكتشاف البرمجيات الضارة |
| `safe` | سكربتات مصنفة safe |
| `version` | تحديد الإصدارات |
| `vuln` | اكتشاف الثغرات |

<a id="script-options"></a>
### خيارات السكربتات

> **توضيح:** يعيد `--script-updatedb` بناء فهرس السكربتات المحلية في `scripts/script.db`، ولا ينزّل سكربتات أحدث من الإنترنت. كما أن `-sC` يستخدم فئة default التي قد تتضمن سكربتات مصنفة intrusive؛ اسم الفئة ليس ضماناً لانعدام الأثر. [دليل NSE الرسمي](https://nmap.org/book/man-nse.html).


| الخيار | الوصف |
|--------|-------------|
| `-sC` | السكربتات الافتراضية |
| `--script <scripts>` | تشغيل سكربتات محددة |
| `--script-args <args>` | معاملات السكربت |
| `--script-updatedb` | تحديث فهرس السكربتات |
| `--script-help <script>` | مساعدة السكربت |

<a id="default-scripts"></a>
### السكربتات الافتراضية
```bash
# تشغيل السكربتات الافتراضية
nmap -sC 192.168.1.1

# مع تحديد الإصدارات، موصى به في الأصل
nmap -sV -sC 192.168.1.1
```

<a id="run-specific-scripts"></a>
### تشغيل سكربتات محددة
```bash
# سكربت واحد
nmap --script http-title 192.168.1.1

# عدة سكربتات
nmap --script http-title,http-headers 192.168.1.1

# الفئة
nmap --script vuln 192.168.1.1

# حرف بدل
nmap --script "http-*" 192.168.1.1

# دمج الفئات
nmap --script "vuln and safe" 192.168.1.1

# استبعاد سكربتات
nmap --script "not intrusive" 192.168.1.1
```

<a id="popular-scripts"></a>
### سكربتات شائعة

<a id="vulnerability-scanning"></a>
#### فحص الثغرات
```bash
# جميع سكربتات فئة الثغرات
nmap --script vuln 192.168.1.1

# فحص CVE محدد
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

<a id="http-scripts"></a>
#### سكربتات HTTP
```bash
# حصر معلومات HTTP
nmap --script http-enum 192.168.1.1

# ترويسات HTTP
nmap --script http-headers 192.168.1.1

# طرق طلب HTTP
nmap --script http-methods 192.168.1.1

# عنوان صفحة HTTP
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

<a id="smb-scripts"></a>
#### سكربتات SMB
```bash
# حصر معلومات SMB
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# ثغرات SMB
nmap --script smb-vuln* 192.168.1.1
```

<a id="ssh-scripts"></a>
#### سكربتات SSH
```bash
# طرق مصادقة SSH
nmap --script ssh-auth-methods 192.168.1.1

# مفتاح مضيف SSH
nmap --script ssh-hostkey 192.168.1.1

# تخمين بيانات SSH بالقوة الغاشمة
nmap --script ssh-brute 192.168.1.1
```

<a id="dns-scripts"></a>
#### سكربتات DNS
```bash
# تخمين أسماء DNS بالقوة الغاشمة
nmap --script dns-brute example.com

# نقل منطقة DNS
nmap --script dns-zone-transfer example.com
```

<a id="mysqldatabase-scripts"></a>
#### سكربتات MySQL وقواعد البيانات
```bash
# معلومات MySQL
nmap --script mysql-info 192.168.1.1

# حصر معلومات MySQL
nmap --script mysql-enum 192.168.1.1

# تخمين بيانات MySQL بالقوة الغاشمة
nmap --script mysql-brute 192.168.1.1
```

<a id="script-arguments"></a>
### معاملات السكربتات
```bash
# القوة الغاشمة باستخدام قائمة كلمات
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# تخمين مصادقة HTTP بالقوة الغاشمة
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

<a id="-output-formats"></a>
## 📄 صيغ المخرجات

<a id="output-options"></a>
### خيارات المخرجات

> **توضيح:** عبارة «جميع الصيغ» مع `-oA` تعني الصيغ الثلاث normal وXML وgrepable، بامتدادات `.nmap` و`.xml` و`.gnmap`؛ ولا تشمل كل أنواع العرض الأخرى. يصف [دليل المخرجات الرسمي](https://nmap.org/book/man-output.html) صيغة grepable بأنها قديمة ويوضح أن XML أقدر على تمثيل خصائص Nmap.


| الخيار | الوصف |
|--------|-------------|
| `-oN <file>` | المخرجات العادية |
| `-oX <file>` | مخرجات XML |
| `-oG <file>` | مخرجات قابلة للمعالجة بـ grep |
| `-oA <basename>` | جميع الصيغ وفق وصف الأصل |
| `-oS <file>` | مخرجات بأسلوب sCRiPt KiDDi3 |

<a id="save-output"></a>
### حفظ المخرجات
```bash
# المخرجات العادية
nmap -oN scan.txt 192.168.1.1

# مخرجات XML للأدوات
nmap -oX scan.xml 192.168.1.1

# مخرجات قابلة للمعالجة بـ grep
nmap -oG scan.gnmap 192.168.1.1

# جميع الصيغ في وقت واحد وفق الأصل
nmap -oA scan 192.168.1.1
# ينشئ: scan.nmap, scan.xml, scan.gnmap
```

<a id="verbosity--debugging"></a>
### تفصيل المخرجات والتنقيح

> **تصحيح:** الخيار `--open` لا يقتصر على الحالة `open`؛ قد يعرض أيضاً `open|filtered` و`unfiltered` بوصفها حالات مفتوحة أو محتملة الانفتاح. راجع [شرح Nmap الرسمي](https://nmap.org/book/man-output.html).


| الخيار | الوصف |
|--------|-------------|
| `-v` | زيادة تفصيل المخرجات |
| `-vv` | تفصيل أكبر |
| `-d` | وضع التنقيح |
| `-dd` | معلومات تنقيح أكثر |
| `--reason` | عرض سبب تصنيف حالة المنفذ |
| `--open` | عرض المنافذ المفتوحة فقط وفق وصف الأصل |
| `--packet-trace` | عرض جميع الحزم |

```bash
# مخرجات مفصلة
nmap -v 192.168.1.1

# مخرجات أكثر تفصيلاً
nmap -vv 192.168.1.1

# عرض المنافذ المفتوحة فقط وفق الأصل
nmap --open 192.168.1.1

# عرض السبب
nmap --reason 192.168.1.1
```

---

<a id="-firewallids-evasion"></a>
## 🛡️ تفادي جدار الحماية وأنظمة كشف التسلل

<a id="evasion-techniques"></a>
### تقنيات تفادي الرصد

| الخيار | الوصف |
|--------|-------------|
| `-f` | تجزئة الحزم |
| `--mtu <size>` | حجم MTU مخصص |
| `-D <decoys>` | استخدام أجهزة تمويه (Decoys) |
| `-S <IP>` | انتحال عنوان IP المصدر |
| `--source-port <port>` | تحديد منفذ مصدر بقصد التمويه |
| `--data-length <n>` | إلحاق بيانات عشوائية |
| `--randomize-hosts` | ترتيب عشوائي للأجهزة |
| `--spoof-mac <mac>` | انتحال عنوان MAC |
| `--badsum` | إرسال قيم تحقق غير صحيحة |

<a id="packet-fragmentation"></a>
### تجزئة الحزم
```bash
# تجزئة الحزم
nmap -f 192.168.1.1

# حجم MTU مخصص
nmap --mtu 16 192.168.1.1
```

<a id="decoys"></a>
### أجهزة التمويه (Decoys)
```bash
# استخدام أجهزة تمويه (Decoys)
nmap -D decoy1,decoy2,ME 192.168.1.1

# أجهزة تمويه عشوائية
nmap -D RND:5 192.168.1.1
```

<a id="source-ipport-spoofing"></a>
### انتحال عنوان IP ومنفذ المصدر
```bash
# انتحال عنوان IP المصدر
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# تحديد منفذ مصدر من المنافذ المسموح بها عادةً
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

<a id="mac-spoofing"></a>
### انتحال عنوان MAC
```bash
# عنوان MAC عشوائي
nmap --spoof-mac 0 192.168.1.1

# شركة مصنّعة محددة
nmap --spoof-mac Apple 192.168.1.1

# عنوان MAC محدد
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

<a id="data-padding"></a>
### حشو البيانات
```bash
# إضافة بيانات عشوائية إلى الحزم
nmap --data-length 25 192.168.1.1
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-quick-network-sweep"></a>
### المثال 1: اكتشاف سريع لأجهزة الشبكة
```bash
# العثور على الأجهزة المستجيبة
nmap -sn 192.168.1.0/24
```

<a id="example-2-basic-port-scan"></a>
### المثال 2: فحص أساسي للمنافذ
```bash
# الفحص مع تحديد الإصدارات
nmap -sV 192.168.1.1
```

<a id="example-3-comprehensive-scan"></a>
### المثال 3: فحص شامل
```bash
# فحص شامل مع تحديد النظام والسكربتات
sudo nmap -sS -sV -O -sC 192.168.1.1
```

<a id="example-4-aggressive-full-scan"></a>
### المثال 4: فحص موسّع لكافة المنافذ
```bash
# جميع المنافذ بفحص موسّع
sudo nmap -A -T4 -p- 192.168.1.1
```

<a id="example-5-vulnerability-scan"></a>
### المثال 5: فحص الثغرات
```bash
# البحث عن الثغرات
nmap --script vuln 192.168.1.1
```

<a id="example-6-stealth-scan"></a>
### المثال 6: فحص أقل ظهوراً وفق الأصل
```bash
# فحص أقل ظهوراً وفق الأصل
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

<a id="example-7-web-server-scan"></a>
### المثال 7: فحص خادم ويب
```bash
# فحص ويب شامل
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

<a id="example-8-smb-enumeration"></a>
### المثال 8: حصر معلومات SMB
```bash
# حصر شامل لمعلومات SMB
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

<a id="example-9-full-subnet-scan"></a>
### المثال 9: فحص الشبكة الفرعية كاملة
```bash
# فحص الشبكة الفرعية كاملة
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

<a id="example-10-udp-services"></a>
### المثال 10: خدمات UDP
```bash
# خدمات UDP شائعة
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| الأمر | الوصف |
|---------|-------------|
| `nmap <target>` | فحص أساسي |
| `nmap -sn <target>` | فحص Ping فقط |
| `nmap -Pn <target>` | تجاوز اكتشاف الأجهزة |
| `nmap -sS <target>` | فحص SYN الأقل ظهوراً وفق الأصل |
| `nmap -sV <target>` | تحديد الإصدارات |
| `nmap -O <target>` | تحديد نظام التشغيل |
| `nmap -A <target>` | فحص موسّع |
| `nmap -sC <target>` | السكربتات الافتراضية |
| `nmap -p- <target>` | جميع المنافذ |
| `nmap --script vuln <target>` | فحص الثغرات |

<a id="speed-vs-stealth"></a>
### السرعة مقابل تقليل الظهور

| الحاجة | الخيارات |
|------|---------|
| **سريع** | `-T4 --min-rate 1000` |
| **أقل ظهوراً وفق الأصل** | `-T1 -f --data-length 25` |
| **متوازن** | `-T3` (الافتراضي) |

<a id="common-scans"></a>
### فحوصات شائعة

```bash
# فحص سريع
nmap -F 192.168.1.1

# فحص TCP كامل
nmap -sT -p- 192.168.1.1

# فحص الخدمات
nmap -sV 192.168.1.1

# فحص الثغرات
nmap --script vuln 192.168.1.1

# جميع الخيارات في المثال
sudo nmap -A -T4 -p- 192.168.1.1
```

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح الأداء

1. **استخدام فحص SYN الافتراضي**
   ```bash
   # يتطلب root لكنه الأسرع وفق الأصل
   sudo nmap -sS 192.168.1.1
   ```

2. **تحديد نطاق المنافذ في البداية**
   ```bash
   # البدء بالمنافذ الأكثر شيوعاً
   nmap --top-ports 100 192.168.1.1
   ```

3. **زيادة السرعة للشبكات المحلية**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

<a id="accuracy-tips"></a>
### نصائح الدقة

1. **أنواع فحص متعددة**
   ```bash
   # بروتوكولا TCP وUDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **زيادة محاولات الإرسال للشبكات غير الموثوقة**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

<a id="legal-considerations"></a>
### اعتبارات قانونية

> ⚠️ **مهم:** افحص فقط الشبكات التي تملكها أو لديك إذن بفحصها.

- ✅ شبكاتك الخاصة
- ✅ بيئات المختبر
- ✅ اختبارات اختراق مصرح بها
- ❌ أجهزة عشوائية على الإنترنت
- ❌ شبكات دون إذن

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** Nmap أداة قوية ينبغي استخدامها فقط في **الفحص المصرح به**.
> 
> - ✅ افحص الشبكات التي تملكها
> - ✅ افحص بإذن كتابي صريح
> - ✅ استخدمها في اختبارات اختراق مصرح بها
> - ❌ لا تفحص شبكات دون تفويض
> - ❌ قد يكون الفحص غير المصرح به مخالفاً للقانون
> 
> **فحص الشبكات دون تصريح مخالف للقانون في العديد من الولايات القضائية.**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-resources"></a>
### المراجع الرسمية
- [الموقع الرسمي لـ Nmap](https://nmap.org/)
- [الدليل المرجعي لـ Nmap](https://nmap.org/book/man.html)
- [توثيق سكربتات NSE](https://nmap.org/nsedoc/)

<a id="learning-resources"></a>
### مراجع التعلم
- [كتاب Nmap لفحص الشبكات](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [Metasploit Framework](../../Metasploit/translations/README.ar.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🔍 أتقن استطلاع الشبكات!</b><br>
  <i>تعرّف على شبكتك قبل المهاجمين</i>
</p>
