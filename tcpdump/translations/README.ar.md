<a id="-tcpdump---complete-cheatsheet"></a>
# 📡 tcpdump — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Command-Line Packet Analyzer
```

يصف الشعار tcpdump بأنها أداة لتحليل الحزم عبر سطر الأوامر.

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 الأداة التقليدية لتحليل الحزم عبر سطر الأوامر</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [خيارات شائعة](#-common-options)
- [مرشحات BPF](#-bpf-filters)
- [مرشحات البروتوكولات](#-protocol-filters)
- [خيارات المخرجات](#-output-options)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**tcpdump** محلل قوي للحزم يعمل عبر سطر الأوامر، ويتوفر على معظم الأنظمة الشبيهة بـ Unix. يستخدم مكتبة libpcap وصيغة مرشح حزم بيركلي (Berkeley Packet Filter أو BPF).

<a id="key-features"></a>
### الميزات الأساسية

|الميزة|الوصف|
|---------|-------------|
|**خفيف**|دون واجهة رسومية وباستهلاك قليل للموارد|
|**مرشحات BPF**|ترشيح قوي للحزم|
|**متوافق مع pcap**|قراءة ملفات pcap وكتابتها|
|**قابل للاستخدام في البرامج النصية**|مناسب للأتمتة|
|**واسع التوفر**|متوفر على أنظمة Unix بحسب الأصل|

<a id="tcpdump-vs-wireshark"></a>
### مقارنة tcpdump مع Wireshark

|الميزة|tcpdump|Wireshark|
|---------|---------|-----------|
|**الواجهة**|CLI|GUI|
|**الموارد**|قليل|أعلى|
|**العمل عن بُعد**|عبر SSH مباشرة|يتطلب إعدادًا|
|**البرمجة النصية**|سهلة|أكثر تعقيدًا بحسب المقارنة|
|**التحليل**|أساسي|متقدم|

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="linux-pre-installed-on-most"></a>
### نظام Linux حيث تكون الأداة مثبتة في كثير من التوزيعات
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

<a id="macos-pre-installed"></a>
### نظام macOS حيث تكون الأداة مثبتة مسبقًا
```bash
tcpdump --version
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
tcpdump --version
tcpdump -D              # عرض واجهات الشبكة
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
tcpdump [options] [filter expression]
```

<a id="basic-commands"></a>
### الأوامر الأساسية
```bash
# الالتقاط من واجهة الشبكة الافتراضية
sudo tcpdump

# الالتقاط من واجهة شبكة محددة
sudo tcpdump -i eth0

# التقاط عدد محدد من الحزم
sudo tcpdump -c 100

# الالتقاط مع مخرجات تفصيلية
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-common-options"></a>
## ⚙️ خيارات شائعة

<a id="display-options"></a>
### خيارات العرض

|الخيار|الوصف|
|--------|-------------|
|`-i`|واجهة الشبكة المراد الالتقاط منها|
|`-c`|عدد الحزم المطلوب التقاطها|
|`-n`|عدم تحويل العناوين إلى أسماء مضيفين|
|`-nn`|عدم تحويل العناوين أو المنافذ إلى أسماء|
|`-v`|مخرجات تفصيلية|
|`-vv`|تفصيل أكبر|
|`-vvv`|أقصى مستوى من التفصيل|
|`-q`|مخرجات مختصرة|
|`-t`|عدم عرض الطابع الزمني|
|`-tt`|عرض طابع زمني بصيغة Unix|
|`-ttt`|عرض فرق التوقيت|
|`-tttt`|عرض التاريخ والوقت|

<a id="capture-options"></a>
### خيارات الالتقاط

|الخيار|الوصف|
|--------|-------------|
|`-w`|الكتابة إلى ملف pcap|
|`-r`|القراءة من ملف pcap|
|`-s`|طول الجزء الملتقط من الحزمة بالبايت (Snapshot Length)|
|`-s0`|التقاط الحزم بالطول الافتراضي الأقصى|
|`-D`|عرض واجهات الشبكة المتاحة|

<a id="output-options"></a>
### خيارات المخرجات

|الخيار|الوصف|
|--------|-------------|
|`-A`|العرض بتنسيق ASCII|
|`-X`|عرض ست عشري مع ASCII|
|`-XX`|عرض ست عشري مع ASCII وترويسات طبقة الربط|
|`-e`|عرض ترويسة طبقة الربط|
|`-l`|تخزين المخرجات مؤقتًا على مستوى السطر|

<a id="common-combinations"></a>
### تركيبات خيارات شائعة
```bash
# دون تحويل الأسماء، مع عرض تفصيلي وطول التقاط افتراضي كامل
sudo tcpdump -nnvvs0 -i eth0

# الحفظ في ملف
sudo tcpdump -i eth0 -w capture.pcap

# القراءة من ملف
tcpdump -r capture.pcap

# مخرجات بتنسيق ASCII
sudo tcpdump -A -i eth0

# عرض ست عشري مع ASCII
sudo tcpdump -X -i eth0
```

---

<a id="-bpf-filters"></a>
## 🎣 مرشحات BPF

<a id="host-filters"></a>
### مرشحات المضيفين

```bash
# حركة المرور من مضيف محدد أو إليه
tcpdump host 192.168.1.1

# تحديد مضيف المصدر فقط
tcpdump src host 192.168.1.1

# تحديد مضيف الوجهة فقط
tcpdump dst host 192.168.1.1

# مضيفون متعددون
tcpdump host 192.168.1.1 or host 192.168.1.2

# استبعاد مضيف
tcpdump not host 192.168.1.1

# شبكة أو شبكة فرعية
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

<a id="port-filters"></a>
### مرشحات المنافذ

```bash
# منفذ محدد
tcpdump port 80
tcpdump port 443

# منفذ المصدر
tcpdump src port 443

# منفذ الوجهة
tcpdump dst port 22

# نطاق منافذ
tcpdump portrange 1-1024

# منافذ متعددة
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

<a id="protocol-filters"></a>
### مرشحات البروتوكولات

```bash
# بحسب البروتوكول
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# البروتوكول والمنفذ
tcpdump tcp port 80
tcpdump udp port 53
```

<a id="logical-operators"></a>
### المعاملات المنطقية

|المعامل|البديل|الوصف|
|----------|-------------|-------------|
|`and`|`&&`|الجمع المنطقي AND|
|`or`|`\|\|`|الاختيار المنطقي OR|
|`not`|`!`|النفي المنطقي NOT|
|`()`||تجميع الشروط|

<a id="combined-filters"></a>
### مرشحات مركبة

```bash
# المضيف والمنفذ معًا
tcpdump host 192.168.1.1 and port 80

# المضيف والبروتوكول معًا
tcpdump host 192.168.1.1 and tcp

# مرشح مركب
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# استبعاد حركة مرور محددة
tcpdump not port 22 and not port 53

# حركة المرور بين مضيفين
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

<a id="-protocol-filters"></a>
## 📡 مرشحات البروتوكولات

<a id="tcp-flags"></a>
### أعلام TCP ‏(Flags)

```bash
# حزم تحمل العلم SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# حزم تحمل العلمين SYN وACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# حزم تحمل العلم FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# حزم تحمل العلم RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# حزم تحمل العلم PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# العلم ACK فقط
tcpdump 'tcp[tcpflags] == tcp-ack'
```

<a id="tcp-flag-reference"></a>
### مرجع أعلام TCP

|العلم|ست عشري|عشري|tcpdump|
|------|-----|-----|---------|
|FIN|0x01|1|tcp-fin|
|SYN|0x02|2|tcp-syn|
|RST|0x04|4|tcp-rst|
|PSH|0x08|8|tcp-push|
|ACK|0x10|16|tcp-ack|
|URG|0x20|32|tcp-urg|

<a id="http-traffic"></a>
### حركة HTTP

> **توضيح للمرشحات:** يختار أول مثال حزم IPv4/TCP ذات حمولة على المنفذ 80، وليس طلبات HTTP وحدها. تفحص أمثلة GET وPOST بايتات الحزمة، ولا تعيد تجميع الطلبات الممتدة عبر حزم متعددة. [مرجع مرشحات libpcap الرسمي](https://github.com/the-tcpdump-group/libpcap/blob/master/pcap-filter.manmisc.in).

```bash
# حزم ذات حمولة على المنفذ 80 بحسب وصف الأصل؛ راجع التوضيح
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# طلبات GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# طلبات POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# التقاط HTTP بسيط
tcpdump -A -s0 'tcp port 80'
```

<a id="dns-traffic"></a>
### حركة DNS

```bash
# حركة DNS على المنفذ 53
tcpdump port 53
tcpdump udp port 53

# استعلامات DNS فقط
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# استجابات DNS فقط
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

<a id="icmp-traffic"></a>
### حركة ICMP

```bash
# جميع حزم ICMP
tcpdump icmp

# طلب صدى ICMP المستخدم في ping
tcpdump 'icmp[icmptype] == icmp-echo'

# رد صدى ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# رسائل تعذر الوصول في ICMP
tcpdump 'icmp[icmptype] == icmp-unreach'
```

<a id="arp-traffic"></a>
### حركة ARP

```bash
# جميع حزم ARP
tcpdump arp

# طلبات ARP
tcpdump 'arp[6:2] == 1'

# ردود ARP
tcpdump 'arp[6:2] == 2'
```

---

<a id="-output-options"></a>
## 💾 خيارات المخرجات

<a id="write-to-file"></a>
### الكتابة إلى ملف

> **تصحيح:** يكتب `-w` الحزم الخام إلى الملف بدل عرض تحليلها؛ لذلك لا يجعل `tcpdump -w capture.pcap | tee` الالتقاط ظاهرًا على الشاشة كما يذكر الأصل. أُبقي الأمر دون تعديل. [دليل tcpdump الرسمي](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

```bash
# الكتابة إلى ملف pcap
tcpdump -w capture.pcap

# الكتابة مع تدوير 100 ملف بحجم يقارب 1MB لكل منها
tcpdump -w capture.pcap -W 100 -C 1

# الكتابة مع تضمين الطابع الزمني في اسم الملف
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# الالتقاط والعرض بحسب وصف الأصل؛ راجع التصحيح
tcpdump -w capture.pcap | tee
```

<a id="read-from-file"></a>
### القراءة من ملف

الأمر الذي يستخدم `wc -l` يعد أسطر النص المعروض؛ قد لا يساوي عدد الحزم إذا امتدت مخرجات حزمة واحدة عبر عدة أسطر.

```bash
# قراءة ملف pcap
tcpdump -r capture.pcap

# القراءة باستخدام مرشحات
tcpdump -r capture.pcap 'host 192.168.1.1'

# عد الحزم بحسب الأصل؛ الأمر يعد أسطر العرض
tcpdump -r capture.pcap | wc -l

# القراءة مع عرض تفصيلي
tcpdump -nnvvXSs0 -r capture.pcap
```

<a id="display-formats"></a>
### تنسيقات العرض

```bash
# المحتوى بتنسيق ASCII
tcpdump -A -i eth0

# عرض ست عشري مع ASCII
tcpdump -X -i eth0

# عرض ست عشري فقط
tcpdump -x -i eth0

# مع ترويسات Ethernet
tcpdump -XX -i eth0

# عرض أرقام التسلسل المطلقة
tcpdump -S -i eth0
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-capture-web-traffic"></a>
### المثال 1: التقاط حركة الويب
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

<a id="example-2-capture-dns-queries"></a>
### المثال 2: التقاط استعلامات DNS بحسب الأصل

المرشح `udp port 53` يختار الاستعلامات والاستجابات معًا؛ لحصر الاستعلامات، راجع مثال فحص علم الاستجابة QR في قسم DNS أعلاه.
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

<a id="example-3-capture-traffic-to-specific-host"></a>
### المثال 3: التقاط حركة مضيف محدد
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

<a id="example-4-find-ssh-connections"></a>
### المثال 4: العثور على اتصالات SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

<a id="example-5-capture-icmp-ping"></a>
### المثال 5: التقاط ICMP المستخدم في ping
```bash
sudo tcpdump -i eth0 icmp
```

<a id="example-6-detect-port-scan-syn"></a>
### المثال 6: رصد حزم SYN التي قد ترتبط بفحص المنافذ
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

<a id="example-7-capture-only-incoming-traffic"></a>
### المثال 7: التقاط الحركة الواردة فقط بحسب الأصل

> **تصحيح للاقتباس:** تمنع علامات الاقتباس المفردة توسعة `$(hostname -I | cut -d" " -f1)` في Bash؛ يمرر المثال النص حرفيًا إلى مرشح tcpdump بدل عنوان IP محسوب. أُبقي السطر كما في الأصل. [دليل اقتباس Bash الرسمي](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html).
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

<a id="example-8-monitor-specific-conversation"></a>
### المثال 8: مراقبة اتصال محدد
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

<a id="example-9-capture-ftp-credentials"></a>
### المثال 9: التقاط بيانات اعتماد FTP غير المشفر
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

<a id="example-10-save-for-wireshark-analysis"></a>
### المثال 10: الحفظ للتحليل في Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# ثم افتح capture.pcap في Wireshark
```

<a id="example-11-remote-capture-via-ssh"></a>
### المثال 11: الالتقاط عن بُعد عبر SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

<a id="example-12-capture-excluding-ssh"></a>
### المثال 12: الالتقاط مع استبعاد SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

|المهمة|الأمر|
|------|---------|
|عرض الواجهات|`tcpdump -D`|
|التقاط أساسي|`tcpdump -i eth0`|
|دون تحويل الأسماء|`tcpdump -nn`|
|التقاط عدد محدد من الحزم|`tcpdump -c 100`|
|الكتابة إلى ملف|`tcpdump -w file.pcap`|
|القراءة من ملف|`tcpdump -r file.pcap`|
|مخرجات تفصيلية|`tcpdump -v`|
|عرض ASCII|`tcpdump -A`|
|عرض ست عشري مع ASCII|`tcpdump -X`|
|حزمة بالطول الافتراضي الأقصى|`tcpdump -s0`|

<a id="common-filters"></a>
### مرشحات شائعة

|المرشح|الوصف|
|--------|-------------|
|`host 1.2.3.4`|حركة المرور من المضيف أو إليه|
|`port 80`|حركة المرور على المنفذ 80|
|`tcp`|حركة TCP فقط|
|`udp`|حركة UDP فقط|
|`icmp`|حركة ICMP فقط|
|`net 192.168.1.0/24`|حركة المرور من الشبكة الفرعية أو إليها|
|`src host 1.2.3.4`|المضيف المحدد هو المصدر|
|`dst port 443`|منفذ الوجهة 443|

<a id="filter-combinations"></a>
### تركيبات المرشحات

|المثال|الوصف|
|---------|-------------|
|`host A and port 80`|المضيف A والمنفذ 80|
|`port 80 or port 443`|المنفذ 80 أو 443|
|`not port 22`|استبعاد SSH|
|`tcp and port 80`|منفذ TCP رقم 80|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح للأداء

> **توضيح:** يلتقط `-s 96` أول 96 بايت من كل حزمة، ولا يعني تلقائيًا «الترويسات فقط» لأن أطوال الترويسات تختلف. وكذلك `-s0` يعيد طول الالتقاط الافتراضي الكبير، وليس طولًا غير محدود. [مرجع tcpdump الرسمي](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

```bash
# عدم إجراء استعلامات DNS لتسريع العرض
tcpdump -nn

# تحديد طول الجزء الملتقط من كل حزمة
tcpdump -s 96   # الترويسات فقط بحسب وصف الأصل؛ راجع التوضيح

# استخدام مرشحات BPF لتقليل الحمل
tcpdump 'host 192.168.1.1'  # الترشيح على مستوى النواة
```

<a id="security-tips"></a>
### نصائح أمنية

> **تصحيح للتوقيت:** يخفض `-Z user` الصلاحيات بعد فتح جهاز الالتقاط أو ملف الإدخال وقبل فتح ملفات الإخراج، وليس بعد انتهاء الالتقاط. [دليل tcpdump الموزع مع Ubuntu](https://manpages.ubuntu.com/manpages/tenacious/man8/tcpdump.8.html).

```bash
# التشغيل دون صلاحيات root إذا كان النظام معدًا لذلك
# أو تقليل مدة الالتقاط إلى الحد اللازم

# خفض الصلاحيات بعد الالتقاط بحسب وصف الأصل؛ راجع التوضيح
tcpdump -Z user
```

<a id="useful-aliases"></a>
### أسماء مستعارة مفيدة للأوامر (Aliases)

```bash
# الإضافة إلى ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** ينبغي استخدام التقاط حزم الشبكة فقط **لأغراض مصرح بها**.
> 
> - ✅ التقط الحزم على الشبكات التي تملكها
> - ✅ نفذ الاختبارات الأمنية بتفويض
> - ✅ استخدمه لاستكشاف أعطال الأنظمة المصرح لك بالعمل عليها
> - ❌ لا تلتقط الحزم على شبكات غير مصرح لك بها
> - ❌ قد يخالف التقاط حركة الآخرين القانون
> 
> **بحسب تنبيه الأصل، مراقبة الشبكات دون تفويض مخالفة للقانون.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [دليل tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [الموقع الرسمي لـ tcpdump](https://www.tcpdump.org/)
- [صيغة مرشحات BPF](https://biot.com/capstats/bpf.html)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Wireshark](../../Wireshark/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)

---

<p align="center">
  <b>📡 أتقن تحليل الحزم عبر سطر الأوامر!</b><br>
  <i>بديل خفيف لأداة Wireshark</i>
</p>
