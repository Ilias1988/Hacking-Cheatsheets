<a id="-wifite---automated-wifi-auditor-cheatsheet"></a>
# 📶 ورقة مرجعية لأداة تدقيق WiFi المؤتمتة Wifite

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Automated WiFi Hacking Tool
```

يشير الرسم أعلاه إلى أداة Wifite لتدقيق WiFi، وقد حُفظ بتنسيقه الأصلي. يشير ESSID إلى اسم الشبكة، وBSSID إلى معرّف نقطة الوصول، وغالبًا يكون عنوان MAC. أما Handshake فهي المصافحة التي تتبادل فيها الأطراف رسائل إنشاء الاتصال.

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Wifite؟](#-what-is-wifite)
- [التثبيت](#-installation)
- [الاستخدام الأساسي](#-basic-usage)
- [اختيار الأهداف](#-target-selection)
- [خيارات الهجوم](#-attack-options)
- [هجمات WPS](#-wps-attacks)
- [هجمات PMKID](#-pmkid-attacks)
- [خيارات متقدمة](#-advanced-options)
- [خيارات كسر كلمات المرور](#-cracking-options)
- [أمثلة](#-examples)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-wifite"></a>
## 🎯 ما هو Wifite؟

**Wifite** ‏(Wifite2) أداة مؤتمتة لتدقيق الشبكات اللاسلكية، وتوفر:

- 🤖 **أتمتة الهجمات** - تشغيل الخطوات دون كتابة الأوامر يدويًا
- 🎯 **أهداف متعددة** - اختبار عدة شبكات
- 🔓 **WPA/WPA2/WEP/WPS** - جميع أنواع التشفير
- 💾 **حفظ التقدّم** - استئناف الهجمات المتوقفة
- 🔄 **التقاط PMKID** - هجمات حديثة دون الحاجة إلى عميل متصل

<a id="wifite-vs-wifite2"></a>
### مقارنة Wifite مع Wifite2

| الميزة | Wifite (الأصلي) | Wifite2 |
|---------|-------------------|---------|
| قيد الصيانة | لا | نعم |
| Python | Python 2 | Python 3 |
| PMKID | لا | نعم |
| الشيفرة | غير منظّمة | إعادة كتابة منظّمة |
| الاستخدام | لا تستخدمه | **موصى به** |

<a id="features"></a>
### الميزات

| الميزة | الوصف |
|---------|-------------|
| **مؤتمت** | تدقيق WiFi بخطوات تفاعلية بسيطة |
| **اختيار ذكي للأهداف** | اختيار الأهداف بحسب قوة الإشارة |
| **جميع الهجمات** | WPA, WEP, WPS, PMKID |
| **كسر كلمات المرور** | التكامل مع Hashcat/aircrack |
| **قابل للاستئناف** | متابعة الجلسات المتوقفة |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبّت مسبقًا)

```bash
# مثبّت مسبقًا في Kali
wifite --help

# التحديث إلى أحدث إصدار
sudo apt update && sudo apt install wifite
```

<a id="install-wifite2-from-github"></a>
### تثبيت Wifite2 من GitHub

```bash
# استنسخ المستودع
git clone https://github.com/derv82/wifite2.git
cd wifite2

# التشغيل مباشرة
sudo python3 wifite.py

# أو التثبيت
sudo python3 setup.py install
```

<a id="dependencies"></a>
### الاعتماديات

```bash
# مطلوب
sudo apt install aircrack-ng

# موصى به لتوفير جميع الوظائف
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

<a id="verify-installation"></a>
### التحقق من التثبيت

```bash
wifite --help
wifite --version
```

---

<a id="-basic-usage"></a>
## 💻 الاستخدام الأساسي

<a id="quick-start"></a>
### بداية سريعة

```bash
# شغّل wifite مباشرة (سيكتشف واجهة الشبكة تلقائيًا)
sudo wifite

# حدّد واجهة الشبكة
sudo wifite -i wlan0
```

<a id="basic-workflow"></a>
### سير العمل الأساسي

```
1. شغّل wifite
2. انتظر ظهور الشبكات
3. اضغط Ctrl+C لإيقاف المسح
4. اختر الأهداف بأرقامها
5. يبدأ Wifite الهجمات تلقائيًا
6. اكسر كلمات المرور من المصافحات الملتقطة
```

<a id="interactive-mode"></a>
### الوضع التفاعلي

```bash
# شغّل wifite
sudo wifite

# يعرض الإخراج ما يلي:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# أدخل أرقام الأهداف أو استخدم Ctrl+C لاختيار جميع الأهداف الظاهرة
```

---

<a id="-target-selection"></a>
## 🎯 اختيار الأهداف

<a id="select-by-number"></a>
### الاختيار برقم الهدف

```bash
# بعد المسح، اختر الأهداف بأرقامها
# مثال: اختر الهدفين 1 و3
1, 3

# اختر نطاقًا من الأرقام
1-5

# اختر جميع الأهداف
all
```

<a id="filter-by-essid"></a>
### التصفية بحسب ESSID

```bash
# استهدف اسم شبكة محدّدًا
sudo wifite --essid "TargetNetwork"

# عدة أسماء ESSID
sudo wifite --essid "Network1" --essid "Network2"
```

<a id="filter-by-bssid"></a>
### التصفية بحسب BSSID

```bash
# استهدف عنوان MAC محدّدًا
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

<a id="filter-by-encryption"></a>
### التصفية بحسب نوع التشفير

```bash
# شبكات WPA فقط
sudo wifite --wpa

# شبكات WEP فقط
sudo wifite --wep

# الشبكات التي يكون WPS مفعّلًا فيها فقط
sudo wifite --wps
```

<a id="filter-by-channel"></a>
### التصفية بحسب القناة

```bash
# قناة محدّدة
sudo wifite -c 6

# نطاق 5GHz فقط
sudo wifite --5ghz

# نطاق 2.4GHz فقط
sudo wifite --2ghz
```

<a id="filter-by-signal"></a>
### التصفية بحسب قوة الإشارة

```bash
# الحد الأدنى لقوة الإشارة (dBm؛ كلما اقتربت من 0 كانت أقوى)
sudo wifite --power -50
```

---

<a id="-attack-options"></a>
## ⚔️ خيارات الهجوم

<a id="wpawpa2-options"></a>
### خيارات WPA/WPA2

```bash
# اضبط مهلة إلغاء المصادقة
sudo wifite --wpa-deauth-timeout 30

# عدد عمليات إلغاء المصادقة المطلوب إرسالها
sudo wifite --num-deauths 5

# تجاوز التقاط المصافحة واستخدم PMKID فقط
sudo wifite --pmkid
```

<a id="deauthentication-options"></a>
### خيارات إلغاء المصادقة (Deauthentication)

```bash
# عدد حزم إلغاء المصادقة
sudo wifite --num-deauths 10

# إلغاء مصادقة مستمر
sudo wifite --num-deauths 0
```

<a id="attack-order"></a>
### ترتيب الهجمات

```bash
# تجاوز هجمات WPA
sudo wifite --no-wpa

# تجاوز هجمات WEP
sudo wifite --no-wep

# تجاوز هجمات WPS
sudo wifite --no-wps

# تجاوز هجمات PMKID
sudo wifite --no-pmkid
```

<a id="kill-networkmanager"></a>
### إيقاف NetworkManager

```bash
# أوقف العمليات المتعارضة تلقائيًا
sudo wifite --kill

# لا توقف العمليات
sudo wifite --no-kill
```

---

<a id="-wps-attacks"></a>
## 🔓 هجمات WPS

<a id="enable-wps"></a>
### تفعيل WPS

```bash
# اختبر فقط الشبكات التي يكون WPS مفعّلًا فيها
sudo wifite --wps

# وضع WPS فقط
sudo wifite --wps-only
```

<a id="wps-tools"></a>
### أدوات WPS

```bash
# استخدم Reaver (الافتراضي)
sudo wifite --wps --reaver

# استخدم Bully
sudo wifite --wps --bully
```

<a id="pixie-dust-attack"></a>
### هجوم Pixie-Dust

```bash
# فعّل Pixie-Dust (هجوم WPS خارج الاتصال)
sudo wifite --wps --pixie

# مهلة Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

<a id="wps-options"></a>
### خيارات WPS

```bash
# مهلة WPS لكل محاولة PIN
sudo wifite --wps-time 120

# الحد الأقصى لمحاولات WPS
sudo wifite --wps-fails 10

# مهلة WPS الإجمالية
sudo wifite --wps-timeout 300
```

---

<a id="-pmkid-attacks"></a>
## 🔐 هجمات PMKID

<a id="what-is-pmkid"></a>
### ما هو PMKID؟

هجوم PMKID (معرّف المفتاح الرئيسي الزوجي، Pairwise Master Key Identifier):
- ✅ يلتقط PMK مباشرة من نقطة الوصول (AP)
- ✅ لا يحتاج إلى عميل متصل
- ✅ لا يتطلب إلغاء المصادقة
- ✅ يعمل على معظم شبكات WPA2

<a id="pmkid-options"></a>
### خيارات PMKID

```bash
# هجمات PMKID فقط (دون مصافحة)
sudo wifite --pmkid

# مهلة PMKID
sudo wifite --pmkid-timeout 120
```

<a id="how-pmkid-works"></a>
### كيف يعمل PMKID

```
1. أرسل طلب ارتباط (Association Request) إلى نقطة الوصول AP
2. ترد نقطة الوصول AP بقيمة PMKID ضمن إطار EAPOL
3. التقط PMKID
4. اكسر كلمة المرور خارج الاتصال باستخدام hashcat
```

---

<a id="-advanced-options"></a>
## ⚙️ خيارات متقدمة

<a id="interface-options"></a>
### خيارات واجهة الشبكة

```bash
# حدّد واجهة الشبكة
sudo wifite -i wlan0

# حدّد واجهة في وضع المراقبة
sudo wifite -i wlan0mon
```

<a id="output-options"></a>
### خيارات الإخراج

```bash
# إخراج مفصّل
sudo wifite -v
sudo wifite --verbose

# إخراج أكثر تفصيلًا
sudo wifite -vv

# وضع الإخراج الهادئ
sudo wifite --quiet
```

<a id="timing-options"></a>
### خيارات التوقيت

```bash
# مدة المسح بالثواني
sudo wifite --scan-time 30

# المهلة الإجمالية لكل هدف
sudo wifite --attack-timeout 300
```

<a id="cracked-passwords"></a>
### كلمات المرور التي كُسرت

```bash
# تُحفظ كلمات المرور التي كُسرت في:
# ~/.config/wifite/cracked.txt

# اعرض الشبكات التي كُسرت كلمات مرورها
cat ~/.config/wifite/cracked.txt

# أو حدّد ملفًا مخصّصًا
sudo wifite --cracked cracked_passwords.txt
```

<a id="skip-options"></a>
### خيارات التجاوز

```bash
# تجاوز كسر كلمات المرور (الالتقاط فقط)
sudo wifite --skip-crack

# تجاوز الأهداف التي كُسرت كلمات مرورها سابقًا
sudo wifite --skip-cracked
```

<a id="random-mac"></a>
### عنوان MAC عشوائي

```bash
# غيّر عنوان MAC إلى عنوان عشوائي
sudo wifite --random-mac

# لا تغيّر عنوان MAC
sudo wifite --no-random-mac
```

---

<a id="-cracking-options"></a>
## 🔓 خيارات كسر كلمات المرور

<a id="dictionary-attack"></a>
### هجوم القاموس

```bash
# حدّد قائمة كلمات
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# عدة قوائم كلمات
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

<a id="hashcat-integration"></a>
### التكامل مع Hashcat

```bash
# استخدم hashcat لكسر كلمات المرور
sudo wifite --hashcat

# موقع hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

<a id="aircrack-ng-options"></a>
### خيارات Aircrack-ng

```bash
# استخدم aircrack-ng (الافتراضي)
sudo wifite --aircrack

# مسار aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

<a id="crack-only-mode"></a>
### وضع كسر كلمات المرور فقط

```bash
# اكسر كلمات المرور باستخدام ملفات مصافحة موجودة
sudo wifite --crack

# حدّد ملف الالتقاط
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

<a id="-examples"></a>
## 📋 أمثلة

<a id="basic-wifi-audit"></a>
### تدقيق WiFi أساسي

```bash
# امسح جميع الشبكات وابدأ اختبارها
sudo wifite

# اختبر باستخدام قائمة كلمات مخصّصة
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

<a id="target-specific-network"></a>
### استهداف شبكة محدّدة

```bash
# الاستهداف بالاسم
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# الاستهداف بعنوان MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

<a id="wpa2-only-attack"></a>
### هجوم على WPA2 فقط

```bash
# شبكات WPA2 ذات PMKID فقط
sudo wifite --wpa --pmkid --dict rockyou.txt
```

<a id="wps-attack"></a>
### هجوم WPS

```bash
# WPS باستخدام Pixie-Dust
sudo wifite --wps --pixie

# تخمين WPS بالقوة الغاشمة فقط
sudo wifite --wps-only --bully
```

<a id="strong-signal-only"></a>
### إشارة قوية فقط

```bash
# اختبر الشبكات ذات الإشارة القوية فقط
sudo wifite --power -50 --wpa
```

<a id="5ghz-networks"></a>
### شبكات 5GHz

```bash
# اختبر نطاق 5GHz فقط
sudo wifite --5ghz --wpa
```

<a id="quick-audit-no-cracking"></a>
### تدقيق سريع (دون كسر كلمات المرور)

```bash
# التقط المصافحات فقط، واكسر كلمات المرور لاحقًا
sudo wifite --skip-crack
```

<a id="resume-attack"></a>
### استئناف الهجوم

```bash
# يحفظ Wifite التقدّم تلقائيًا
# شغّله مرة أخرى للاستئناف
sudo wifite

# تجاوز ما كُسر سابقًا
sudo wifite --skip-cracked
```

<a id="verbose-mode"></a>
### وضع الإخراج المفصّل

```bash
# اعرض جميع التفاصيل
sudo wifite -vv --dict rockyou.txt
```

<a id="stealth-mode"></a>
### الوضع الخفي

```bash
# عدد قليل من عمليات إلغاء المصادقة وإخراج هادئ
sudo wifite --num-deauths 1 --quiet
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### أوامر أساسية

| الغرض | الأمر |
|---------|---------|
| مسح أساسي | `sudo wifite` |
| الاستهداف باسم الشبكة | `sudo wifite --essid "Name"` |
| الاستهداف بعنوان MAC | `sudo wifite --bssid MAC` |
| WPA فقط | `sudo wifite --wpa` |
| WPS فقط | `sudo wifite --wps-only` |
| هجوم PMKID | `sudo wifite --pmkid` |
| باستخدام قائمة كلمات | `sudo wifite --dict wordlist.txt` |
| تجاوز كسر كلمات المرور | `sudo wifite --skip-crack` |

<a id="attack-types"></a>
### أنواع الهجمات

| الهجوم | الخيار | الهدف |
|--------|------|--------|
| مصافحة WPA | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS مفعّل |
| تخمين WPS بالقوة الغاشمة | `--wps-only` | WPS مفعّل |
| WEP | `--wep` | WEP (قديم) |

<a id="common-wordlists"></a>
### قوائم كلمات شائعة

| المسار | الوصف |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | كلمات المرور الأكثر شيوعًا |
| `/usr/share/wordlists/fasttrack.txt` | فحص سريع |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | مخصّصة لـ WiFi |

<a id="captured-files"></a>
### الملفات الملتقطة

```
~/.config/wifite/
├── cracked.txt          # Cracked passwords
├── hs/                  # Handshake captures
│   └── handshake_*.cap
└── pmkid/               # PMKID captures
    └── pmkid_*.22000
```

يوضح الرسم أعلاه مجلد المثال، وملف كلمات المرور cracked.txt، ومجلد المصافحات hs، ومجلد PMKID. حُفظت الأسماء والامتدادات والتعليقات الأصلية؛ وقد يختلف مكان الحفظ الفعلي بحسب الإصدار والإعداد.

<a id="flags-quick-reference"></a>
### مرجع سريع للخيارات

| الخيار | الوصف |
|------|-------------|
| `-i` | واجهة الشبكة |
| `-c` | القناة |
| `-v` | إخراج مفصّل |
| `--wpa` | WPA/WPA2 فقط |
| `--wep` | WEP فقط |
| `--wps` | WPS مفعّل |
| `--pmkid` | هجوم PMKID |
| `--dict` | قائمة كلمات |
| `--kill` | إيقاف العمليات |
| `--power` | الحد الأدنى لقوة الإشارة |
| `--5ghz` | نطاق 5GHz |
| `--2ghz` | نطاق 2.4GHz |

<a id="troubleshooting"></a>
### استكشاف المشكلات وحلها

```bash
# لم يُعثر على واجهة الشبكة
sudo airmon-ng check kill
sudo wifite -i wlan0

# لم يُعثر على شبكات
# - تحقق من دعم المحوّل لوضع المراقبة
# - جرّب نطاقات مختلفة: --5ghz أو --2ghz
# - اقترب من الأهداف

# لم تُلتقط المصافحة
# - زد عدد عمليات إلغاء المصادقة: --num-deauths 10
# - انتظر مدة أطول: --wpa-deauth-timeout 60
# - جرّب عملاء مختلفين

# فشل كسر كلمة المرور
# - استخدم قائمة كلمات أفضل
# - جرّب hashcat باستخدام GPU: --hashcat
```

---

<a id="-wifite-vs-manual-aircrack-ng"></a>
## 🔄 مقارنة Wifite مع الاستخدام اليدوي لـ Aircrack-ng

| المهمة | Wifite | حزمة Aircrack-ng |
|------|--------|-------------------|
| وضع المراقبة (Monitor Mode) | تلقائي | `airmon-ng start wlan0` |
| المسح | تلقائي | `airodump-ng wlan0mon` |
| الهدف | تفاعلي | تصفية يدوية |
| إلغاء المصادقة | تلقائي | `aireplay-ng -0 ...` |
| الالتقاط | تلقائي | `airodump-ng -w ...` |
| كسر كلمة المرور | تلقائي | `aircrack-ng -w ...` |
| **الجهد** | **سهل** | يدوي |

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

```
⚠️ تحذير: استخدم Wifite فقط على شبكات تملكها أو تملك
إذنًا كتابيًا صريحًا باختبارها.

✅ اختبارات اختراق مصرّح بها
✅ تدقيق أمني (بإذن)
✅ أغراض تعليمية (بيئة مختبر)

❌ الوصول غير المصرّح به إلى الشبكات غير قانوني
❌ لا تستخدم الأداة مطلقًا لأغراض خبيثة
```

---

<a id="-resources"></a>
## 📚 المراجع

- [مستودع Wifite2 على GitHub](https://github.com/derv82/wifite2)
- [ويكي Wifite](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Aircrack-ng](../../Aircrack-ng/translations/README.ar.md)
- [Hashcat](../../Hashcat/translations/README.ar.md)
- [أوامر Linux](../../Linux-Commands/translations/README.ar.md)

---



> **توضيحات المصطلحات:** WPS آلية إعداد وليست نوع تشفير؛ وPMKID معرّف مشتق من PMK، وليس المفتاح PMK نفسه. عبارة «التقاط PMK مباشرة» في المصدر غير دقيقة: يُلتقط المعرّف وبيانات التبادل للتحقق من تخمينات كلمة المرور خارج الاتصال. لا تعمل الطريقة على كل نقطة وصول WPA2، وغياب عميل متصل لا يعني أن الالتقاط سلبي أو بلا إرسال. راجع [الشرح الأصلي من Hashcat](https://hashcat.net/forum/thread-7717.html).
>
> **اختلاف الإصدارات:** حُفظت أوامر المصدر دون تعديل. يحيل مستودع derv82 حاليًا إلى إصدار Wifite2 الذي يصونه kimocoder؛ وقد تختلف الأعلام بين الفروع والحزم. مثلًا لا يُعرّف محلّل الخيارات المشار إليه `--scan-time`، و`--cracked` يعرض النتائج السابقة ولا يحدد ملف إخراج، و`--crack` يعرض أوامر التعامل مع مصافحة محفوظة. كما يعتمد الاستئناف على الإصدار وآلية حفظ الجلسة، ولا تعني إعادة التشغيل وحدها استكمال كل جلسة تلقائيًا. لذلك لا يُفترض قبول كل الأعلام والأمثلة في هذا الدليل. راجع [المستودع الأصلي](https://github.com/derv82/wifite2) و[واجهة حزمة Kali](https://www.kali.org/tools/wifite/) و[محلّل خيارات المشروع](https://github.com/kimocoder/wifite2/blob/master/wifite/args.py).
>
> يعمل `--wps` كمرشّح للشبكات التي تدعم WPS، ولا يفعّل WPS على الموجّه. وتعني خيارات النطاق تضمين القنوات المدعومة بحسب الإصدار، لا ضمان أن كل مثال يستبعد النطاق الآخر. تكرار `--essid` أو `--dict` في محلّل يستخدم action=store لا ينشئ قائمة تلقائيًا، وقد تستبدل القيمة الأخيرة السابقة. ومسار `~/.config/wifite/` ومخطط الملفات أمثلة من المصدر وليسا مسارًا افتراضيًا ثابتًا لكل إصدار؛ يستخدم [إعداد الفرع الأصلي](https://github.com/derv82/wifite2/blob/master/wifite/config.py) أسماء نسبية مثل hs وcracked.txt.
>
> لا تعني تسمية «الوضع الخفي» عدم الاكتشاف: الإخراج الهادئ وعدد أقل من عمليات إلغاء المصادقة لا يحوّلان التدقيق النشط إلى تدقيق سلبي. ويظل التقاط المصافحة أو PMKID منفصلًا عن نجاح استعادة كلمة المرور؛ تعتمد النتيجة على صحة البيانات وكلمة المرور وقائمة الكلمات. تُقرأ هذه الملاحظات مع قيود الإصدار وأذونات الاختبار الواردة في المصدر.

<p align="center">
  <b>📶 تدقيق WiFi مؤتمت!</b><br>
  <i>Wifite — حدّد وانقر واختبر (بصورة أخلاقية!)</i>
</p>
