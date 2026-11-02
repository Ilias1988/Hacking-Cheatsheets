<a id="-wireshark---complete-cheatsheet"></a>
# 🦈 Wireshark — المرجع الشامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           The Network Protocol Analyzer
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 محلّل بروتوكولات الشبكة الشهير عالميًا</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [نظرة عامة على الواجهة](#-interface-overview)
- [أساسيات التقاط الحزم](#-capture-basics)
- [مرشّحات العرض](#-display-filters)
- [مرشّحات الالتقاط](#-capture-filters)
- [تحليل البروتوكولات](#-protocol-analysis)
- [تتبّع تدفقات البيانات](#-following-streams)
- [الإحصاءات والتحليل](#-statistics--analysis)
- [tshark (CLI)](#-tshark-cli)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [اختصارات لوحة المفاتيح](#-keyboard-shortcuts)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Wireshark** محلّل حزم مجاني مفتوح المصدر، يُستخدم لاستكشاف مشكلات الشبكات وتحليلها وتطوير البرمجيات وتدقيق الأمن.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **فحص متعمّق** | دعم مئات البروتوكولات |
| **التقاط مباشر** | التقاط الحزم في الوقت الفعلي |
| **تحليل دون اتصال** | قراءة ملفات الالتقاط (pcap وpcapng) |
| **مرشّحات قوية** | مرشّحات العرض والالتقاط |
| **متعدد المنصات** | Windows, Linux, macOS |
| **GUI & CLI** | Wireshark بواجهة رسومية وtshark بسطر الأوامر |

<a id="use-cases"></a>
### حالات الاستخدام

| حالة الاستخدام | الوصف |
|----------|-------------|
| **استكشاف مشكلات الشبكة** | تشخيص مشكلات الاتصال |
| **التحليل الأمني** | كشف الحركة الخبيثة |
| **تطوير البروتوكولات** | اختبار بروتوكولات جديدة |
| **الأدلة الجنائية الرقمية** | تحليل الحركة الملتقطة |
| **التعليم** | تعلّم الشبكات |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="windows"></a>
### Windows
```bash
# التنزيل من الموقع الرسمي
https://www.wireshark.org/download.html

# أو باستخدام Chocolatey
choco install wireshark
```

<a id="linux-kaliubuntu"></a>
### Linux (Kali/Ubuntu)
```bash
# Kali (مثبّت مسبقًا وفق الأصل)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# السماح بالتقاط الحزم دون استخدام root
sudo usermod -aG wireshark $USER
# سجّل الخروج ثم ادخل مجددًا
```

<a id="macos"></a>
### macOS
```bash
brew install --cask wireshark
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
wireshark --version
tshark --version
```

---

<a id="-interface-overview"></a>
## 🖥️ نظرة عامة على الواجهة

<a id="main-window-areas"></a>
### مناطق النافذة الرئيسية

| المنطقة | الوصف |
|------|-------------|
| **Menu Bar (شريط القوائم)** | File, Edit, View, Go, Capture, Analyze |
| **Main Toolbar (شريط الأدوات الرئيسي)** | أزرار الوصول السريع |
| **Filter Bar (شريط المرشّحات)** | إدخال مرشّحات العرض |
| **Packet List (قائمة الحزم)** | جميع الحزم الملتقطة |
| **Packet Details (تفاصيل الحزمة)** | طبقات البروتوكول للحزمة المحددة |
| **Packet Bytes (بايتات الحزمة)** | البيانات الخام بالنظام الست عشري وASCII |

<a id="packet-list-columns-default"></a>
### أعمدة قائمة الحزم الافتراضية

| العمود | الوصف |
|--------|-------------|
| No. | رقم الحزمة |
| Time | الطابع الزمني |
| Source | عنوان IP/MAC للمصدر |
| Destination | عنوان IP/MAC للوجهة |
| Protocol | بروتوكول أعلى طبقة |
| Length | حجم الحزمة بالبايت |
| Info | معلومات خاصة بالبروتوكول |

---

<a id="-capture-basics"></a>
## 📡 أساسيات التقاط الحزم

<a id="start-capture"></a>
### بدء الالتقاط

```
1. حدّد واجهة الشبكة من شاشة الترحيب
2. انقر "Start Capture"، وهو رمز زعنفة القرش الزرقاء
3. أو افتح Capture → Start

طريقة سريعة: انقر مرتين على اسم الواجهة
```

<a id="stop-capture"></a>
### إيقاف الالتقاط

```
انقر المربع الأحمر في شريط الأدوات
أو افتح Capture → Stop
أو اضغط Ctrl+E
```

<a id="save-capture"></a>
### حفظ الالتقاط

```
File → Save As
الصيغ: pcapng افتراضيًا وpcap وغيرها
```

<a id="open-capture-file"></a>
### فتح ملف التقاط

```
File → Open
أو اضغط Ctrl+O
أو استخدم: wireshark file.pcap
```

---

<a id="-display-filters"></a>
## 🔍 مرشّحات العرض

<a id="filter-syntax"></a>
### صياغة المرشّح

تستخدم مرشّحات العرض **صياغة مقارنة الحقول**:

```
field operator value
```

<a id="comparison-operators"></a>
### عوامل المقارنة

| العامل | الوصف | المثال |
|----------|-------------|---------|
| `==` أو `eq` | يساوي | `ip.addr == 192.168.1.1` |
| `!=` أو `ne` | غير مساوٍ | `ip.addr != 10.0.0.1` |
| `>` أو `gt` | أكبر من | `frame.len > 100` |
| `<` أو `lt` | أصغر من | `tcp.port < 1024` |
| `>=` أو `ge` | أكبر من أو يساوي | `frame.len >= 64` |
| `<=` أو `le` | أصغر من أو يساوي | `tcp.port <= 1024` |
| `contains` | يحتوي على سلسلة نصية | `http contains "password"` |
| `matches` | مطابقة تعبير نمطي | `http.host matches ".*\.com"` |

<a id="logical-operators"></a>
### العوامل المنطقية

| العامل | الوصف | المثال |
|----------|-------------|---------|
| `and` أو `&&` | و منطقي | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` أو `\|\|` | أو منطقي | `tcp.port == 80 or tcp.port == 443` |
| `not` أو `!` | نفي منطقي | `not arp` |
| `()` | التجميع | `(ip.src == 10.0.0.1) and (tcp or udp)` |

<a id="ip-filters"></a>
### مرشّحات عناوين IP

```bash
# عنوان IP محدد، سواء للمصدر أو الوجهة
ip.addr == 192.168.1.1

# عنوان IP للمصدر فقط
ip.src == 192.168.1.1

# عنوان IP للوجهة فقط
ip.dst == 192.168.1.1

# نطاق عناوين IP (شبكة فرعية)
ip.addr == 192.168.1.0/24

# استبعاد عنوان IP
ip.addr != 192.168.1.1

# بين مضيفين
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

<a id="port-filters"></a>
### مرشّحات المنافذ

```bash
# منفذ محدد، سواء للمصدر أو الوجهة
tcp.port == 80
udp.port == 53

# منفذ المصدر فقط
tcp.srcport == 443

# منفذ الوجهة فقط
tcp.dstport == 22

# نطاق منافذ
tcp.port >= 1 and tcp.port <= 1024

# منافذ متعددة
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

<a id="protocol-filters"></a>
### مرشّحات البروتوكولات

```bash
# حسب اسم البروتوكول
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# خاص بـ HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# خاص بـ DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # سجل A
dns.flags.response == 1    # استجابات DNS

# خاص بـ TCP
tcp.flags.syn == 1         # حزم SYN
tcp.flags.fin == 1         # حزم FIN
tcp.flags.reset == 1       # حزم RST
tcp.analysis.retransmission  # إعادات الإرسال
tcp.stream == 5            # تدفق محدد
```

<a id="content-filters"></a>
### مرشّحات المحتوى

```bash
# يحتوي على سلسلة نصية
http contains "password"
tcp contains "login"
frame contains "secret"

# محتوى HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# مطابقة دون تمييز حالة الأحرف
http.host matches "(?i)example"
```

<a id="common-filter-examples"></a>
### أمثلة شائعة للمرشّحات

```bash
# حركة HTTP فقط
http

# حركة HTTPS وفق وصف الأصل
tls

# استعلامات DNS
dns.flags.response == 0

# اتصالات أُعيد ضبطها (RST)
tcp.flags.reset == 1

# حزم كبيرة
frame.len > 1000

# عنوان MAC محدد
eth.addr == aa:bb:cc:dd:ee:ff

# حركة البث العام
eth.dst == ff:ff:ff:ff:ff:ff

# حركة غير محلية
!(ip.addr == 192.168.0.0/16)

# المصافحة الثلاثية في TCP وفق وصف الأصل
tcp.flags.syn == 1 or tcp.flags.ack == 1

# طلبات HTTP POST التي تتضمن بيانات
http.request.method == "POST" and http.file_data
```

---

<a id="-capture-filters"></a>
## 🎣 مرشّحات الالتقاط

<a id="bpf-syntax-berkeley-packet-filter"></a>
### صياغة BPF، أي Berkeley Packet Filter

تستخدم مرشّحات الالتقاط **صياغة BPF**، وهي تختلف عن صياغة مرشّحات العرض.

<a id="basic-syntax"></a>
### الصياغة الأساسية

```bash
# المضيف
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# الشبكة
net 192.168.1.0/24
src net 10.0.0.0/8

# المنفذ
port 80
src port 443
dst port 22
portrange 1-1024

# Protocol
tcp
udp
icmp
arp
```

<a id="combined-filters"></a>
### مرشّحات مركّبة

```bash
# المضيف والمنفذ
host 192.168.1.1 and port 80

# محادثة محددة
host 192.168.1.1 and host 192.168.1.2

# استبعاد حركة
not port 22
not host 10.0.0.1

# مرشّح مركّب
tcp and (port 80 or port 443) and host 192.168.1.1
```

<a id="common-capture-filters"></a>
### مرشّحات التقاط شائعة

| الغرض | المرشّح |
|---------|--------|
| مضيف محدد | `host 192.168.1.1` |
| حركة الويب | `port 80 or port 443` |
| DNS فقط | `port 53` |
| SSH فقط | `port 22` |
| استبعاد البث العام | `not broadcast` |
| شبكة فرعية محددة | `net 192.168.1.0/24` |

---

<a id="-protocol-analysis"></a>
## 🔬 تحليل البروتوكولات

<a id="tcp-analysis"></a>
### تحليل TCP

```bash
# أعلام TCP
tcp.flags.syn == 1 and tcp.flags.ack == 0    # SYN فقط
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# مشكلات TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# تدفق TCP
tcp.stream == 0
```

<a id="http-analysis"></a>
### تحليل HTTP

```bash
# طلبات HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# استجابات HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # الأخطاء

# محتوى HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

<a id="dns-analysis"></a>
### تحليل DNS

```bash
# استعلامات DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # سجل A
dns.qry.type == 28     # سجل AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# استجابات DNS
dns.flags.response == 1
dns.flags.rcode != 0   # الأخطاء
```

<a id="tlsssl-analysis"></a>
### تحليل TLS/SSL

```bash
# حركة TLS
tls

# مصافحة TLS
tls.handshake
tls.handshake.type == 1     # Client Hello (رسالة ترحيب العميل)
tls.handshake.type == 2     # Server Hello (رسالة ترحيب الخادم)

# الشهادات
tls.handshake.certificate

# إصدار TLS
tls.record.version == 0x0303  # TLS 1.2
```

<a id="arp-analysis"></a>
### تحليل ARP

```bash
# جميع حزم ARP
arp

# طلبات ARP
arp.opcode == 1

# ردود ARP
arp.opcode == 2

# حزم Gratuitous ARP (قد تشير إلى انتحال)
arp.isgratuitous == 1
```

---

<a id="-following-streams"></a>
## 🔗 تتبّع تدفقات البيانات

<a id="follow-tcp-stream"></a>
### تتبّع تدفق TCP

```
انقر بزر الفأرة الأيمن على الحزمة ثم اختر Follow → TCP Stream

أو افتح Analyze → Follow → TCP Stream
```

<a id="follow-http-stream"></a>
### تتبّع تدفق HTTP

```
انقر بزر الفأرة الأيمن على حزمة HTTP ثم اختر Follow → HTTP Stream
```

<a id="follow-udp-stream"></a>
### تتبّع تدفق UDP

```
انقر بزر الفأرة الأيمن على حزمة UDP ثم اختر Follow → UDP Stream
```

<a id="stream-window"></a>
### نافذة التدفق

- تعرض المحادثة كاملة
- تستخدم ألوانًا مختلفة للعميل والخادم
- الحفظ كنص أو بيانات خام
- أزرار تصفية حسب الاتجاه

---

<a id="-statistics--analysis"></a>
## 📊 الإحصاءات والتحليل

<a id="protocol-hierarchy"></a>
### التسلسل الهرمي للبروتوكولات

```
Statistics → Protocol Hierarchy

يعرض توزيع البروتوكولات الملتقطة
```

<a id="conversations"></a>
### المحادثات

```
Statistics → Conversations

عرض جميع المحادثات حسب:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

<a id="endpoints"></a>
### نقاط النهاية

```
Statistics → Endpoints

سرد جميع نقاط النهاية الفريدة
```

<a id="io-graph"></a>
### IO Graph

```
Statistics → IO Graph

عرض حركة البيانات على مدى الزمن
```

<a id="expert-information"></a>
### معلومات التشخيص (Expert Information)

```
Analyze → Expert Information

يعرض التنبيهات والأخطاء والملاحظات:
- إعادات إرسال TCP
- حزم مشوّهة البنية
- الأخطاء
```

<a id="http-statistics"></a>
### إحصاءات HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

<a id="-tshark-cli"></a>
## 💻 tshark (CLI)

<a id="basic-usage"></a>
### الاستخدام الأساسي

```bash
# التقاط الحزم
tshark -i eth0

# قراءة ملف التقاط
tshark -r capture.pcap

# الحفظ في ملف
tshark -i eth0 -w output.pcap
```

<a id="apply-filters"></a>
### تطبيق المرشّحات

```bash
# مرشّح الالتقاط
tshark -i eth0 -f "port 80"

# مرشّح العرض
tshark -r capture.pcap -Y "http"
```

<a id="output-options"></a>
### خيارات الإخراج

```bash
# عرض حقول محددة
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# إخراج بصيغة JSON
tshark -r capture.pcap -T json

# عرض الإحصاءات
tshark -r capture.pcap -q -z io,stat,1
```

<a id="common-tshark-examples"></a>
### أمثلة شائعة على tshark

```bash
# التقاط طلبات HTTP مباشرة
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# استخراج استعلامات DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# عدّ الحزم حسب عنوان IP
tshark -r capture.pcap -q -z ip_hosts,tree

# إحصاءات HTTP
tshark -r capture.pcap -q -z http,tree
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-capture-http-traffic"></a>
### المثال 1: التقاط حركة HTTP
```bash
# مرشّح الالتقاط: port 80
# مرشّح العرض: http
# تتبّع تدفق HTTP لعرض الطلبات والاستجابات كاملة
```

<a id="example-2-find-credentials"></a>
### المثال 2: البحث عن بيانات الاعتماد
```bash
# مرشّح العرض
http.request.method == "POST" and http contains "password"

# أو لحركة FTP
ftp contains "PASS"
```

<a id="example-3-detect-port-scan"></a>
### المثال 3: اكتشاف فحص المنافذ
```bash
# حزم SYN متعددة إلى منافذ مختلفة
tcp.flags.syn == 1 and tcp.flags.ack == 0

# البحث عن وجهات متعددة من مصدر واحد
Statistics → Conversations → TCP
```

<a id="example-4-dns-exfiltration"></a>
### المثال 4: تسريب البيانات عبر DNS
```bash
# استعلامات DNS كبيرة
dns and frame.len > 512

# سجلات TXT غير معتادة
dns.qry.type == 16
```

<a id="example-5-extract-files"></a>
### المثال 5: استخراج الملفات
```
File → Export Objects → HTTP

حدّد الملفات المراد تصديرها
```

<a id="example-6-analyze-tls-handshake"></a>
### المثال 6: تحليل مصافحة TLS
```bash
tls.handshake

# فحص Client Hello بحثًا عن SNI
tls.handshake.type == 1
```

<a id="example-7-find-cleartext-passwords"></a>
### المثال 7: البحث عن كلمات مرور بنص صريح
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-display-filters"></a>
### مرشّحات العرض الأساسية

| الغرض | المرشّح |
|---------|--------|
| حركة HTTP | `http` |
| حركة DNS | `dns` |
| عنوان IP محدد | `ip.addr == 192.168.1.1` |
| منفذ محدد | `tcp.port == 80` |
| مشكلات TCP | `tcp.analysis.flags` |
| أخطاء HTTP | `http.response.code >= 400` |
| يحتوي على سلسلة نصية | `frame contains "text"` |
| استبعاد بروتوكول | `not arp` |

<a id="common-capture-filters-1"></a>
### مرشّحات التقاط شائعة

| الغرض | المرشّح |
|---------|--------|
| مضيف محدد | `host 192.168.1.1` |
| حركة الويب | `port 80 or port 443` |
| DNS فقط | `port 53` |
| استبعاد البث العام | `not broadcast` |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ اختصارات لوحة المفاتيح

| الاختصار | الإجراء |
|----------|--------|
| `Ctrl+E` | بدء الالتقاط وإيقافه |
| `Ctrl+K` | خيارات الالتقاط |
| `Ctrl+O` | فتح ملف |
| `Ctrl+S` | حفظ ملف |
| `Ctrl+F` | البحث عن حزمة |
| `Ctrl+G` | الانتقال إلى حزمة |
| `Ctrl+→` | الحزمة التالية في المحادثة |
| `Ctrl+←` | الحزمة السابقة في المحادثة |
| `Ctrl+.` | الحزمة المعلّمة التالية |
| `Ctrl+M` | تعليم الحزمة أو إزالة تعليمها |
| `Ctrl+Shift+M` | تعليم جميع الحزم المعروضة |
| `Space` | توسيع شجرة البروتوكول أو طيّها |
| `Tab` | اللوحة التالية |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="capture-tips"></a>
### نصائح الالتقاط

1. **استخدم مرشّحات الالتقاط**
   ```
   قلّل حجم الملف بالتصفية أثناء الالتقاط
   ```

2. **المخزن الدائري لعمليات الالتقاط الطويلة**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **الالتقاط عن بُعد**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

<a id="analysis-tips"></a>
### نصائح التحليل

1. **قواعد الألوان**
   ```
   View → Coloring Rules
   خصّص ألوان البروتوكولات
   ```

2. **عرض الوقت**
   ```
   View → Time Display Format
   جرّب "Seconds Since Beginning of Capture"
   ```

3. **تحويل الأسماء والعناوين**
   ```
   View → Name Resolution
   فعّل الميزة أو عطّلها حسب الحاجة
   ```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تنبيه:** يجب التقاط حزم الشبكة فقط في **أغراض مأذون بها**.
> 
> - ✅ التقط الحزم على الشبكات التي تملكها
> - ✅ اختبر الأمن بعد الحصول على إذن
> - ✅ شخّص مشكلات الأنظمة المأذون لك بفحصها
> - ❌ لا تلتقط الحزم على شبكات دون إذن
> - ❌ قد يكون التقاط حركة الآخرين مخالفًا للقانون
> 
> **المراقبة غير المأذون بها للشبكات مخالفة للقانون في معظم الولايات القضائية، وفق التنبيه الوارد في الأصل.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### الموارد الرسمية
- [الموقع الرسمي لـ Wireshark](https://www.wireshark.org/)
- [دليل مستخدم Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [مرجع مرشّحات العرض](https://www.wireshark.org/docs/dfref/)
- [ويكي Wireshark](https://wiki.wireshark.org/)

<a id="learning-resources"></a>
### موارد التعلم
- [دليل Wireshark التعليمي](https://www.wireshark.org/docs/wsug_html/)
- [عينات التقاط](https://wiki.wireshark.org/SampleCaptures)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [Nmap](../../Nmap/translations/README.ar.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)
- [Hydra](../../Hydra/translations/README.ar.md)

---

<p align="center">
  <b>🦈 أتقن تحليل الشبكات!</b><br>
  <i>شاهد ما يجري عبر الشبكة</i>
</p>

> **مصطلحات:** الالتقاط (Capture) يجمع الحزم من واجهة الشبكة، ومرشّح الالتقاط يحدد ما يُجمع؛ أما مرشّح العرض فيحدد ما يظهر من الحزم الملتقطة. التدفق (Stream) يجمع بيانات محادثة مترابطة، وpcap/pcapng صيغ ملفات الالتقاط. عنوان الرسم الإنجليزي يعني «محلّل بروتوكولات الشبكة»، وقد حُفظ الرسم كاملًا للحفاظ على محاذاته. يظل المخطط `field operator value` صياغة تقنية: حقل ثم عامل مقارنة ثم قيمة.

> **تصحيح بشأن TCP:** المرشّح الموسوم بالمصافحة الثلاثية في الأصل، `tcp.flags.syn == 1 or tcp.flags.ack == 1`، يعرض أي حزمة تحمل SYN أوACK، ومنها حزم بيانات كثيرة؛ فهو لا يعزل المصافحة وحدها. وكذلك فإن ظهور RST يعني إعادة ضبط الاتصال ولا يشرح سببه وحده. [مرجع حقول TCP الرسمي](https://www.wireshark.org/docs/dfref/t/tcp.html). تُقرأ أمثلة كشف الفحص وتسريب DNS بوصفها مؤشرات تحتاج إلى تحليل المحادثة، لا أدلة قاطعة بمفردها.

> **تصحيح بشأن TLS:** لا يثبت المرشّح `tls.record.version == 0x0303` استخدام TLS 1.2 وحده؛ تستخدم سجلات TLS 1.3 أيضًا هذه القيمة القديمة للتوافق. يُراجع الإصدار المتفاوض عليه في المصافحة. كذلك يعرض `tls` حركة TLS عمومًا، وليس HTTPS فقط. [تعريف طبقة السجلات في RFC 8446](https://www.rfc-editor.org/rfc/rfc8446.html#section-5.1).

> **توضيح لصلاحيات الالتقاط:** قد لا تكفي إضافة المستخدم إلى مجموعة wireshark وحدها؛ يعتمد الالتقاط دون root على إعداد امتيازات dumpcap وطريقة تغليف التوزيعة. [مرجع صلاحيات الالتقاط من Wireshark](https://wiki.wireshark.org/CaptureSetup/CapturePrivileges). جميع أسماء القوائم والحقول والمرشّحات في الأمثلة محفوظة بالإنجليزية لتوافق واجهة الأداة؛ تختلف صياغة مرشّحات العرض عن BPF كما يوضح [دليل مرشّحات العرض](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html).
