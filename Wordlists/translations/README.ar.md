<a id="-wordlists---complete-reference-guide"></a>
# 📚 قوائم الكلمات (Wordlists) - دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

> يعرض الرسم النصي كلمة Wordlists بالإنجليزية؛ وهي قوائم مرشّحات مثل كلمات المرور وأسماء المستخدمين والمسارات التي تستخدمها أدوات الاختبار.

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Wordlists">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 مرجع شامل لقوائم الكلمات المستخدمة في اختبار الاختراق</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [قوائم كلمات المرور](#-password-wordlists)
- [قوائم أسماء المجلدات والملفات](#-directoryfile-wordlists)
- [قوائم النطاقات الفرعية](#-subdomain-wordlists)
- [قوائم أسماء المستخدمين](#-username-wordlists)
- [قوائم اختبار مدخلات الويب (Fuzzing)](#-web-fuzzing-wordlists)
- [مجموعة SecLists](#-seclists-collection)
- [قوائم الكلمات الافتراضية في Kali](#-kali-default-wordlists)
- [أدوات إنشاء قوائم مخصصة](#-custom-wordlist-tools)
- [تنزيل سريع](#-quick-download)
- [نصائح وممارسات موصى بها](#-tips--best-practices)

---

<a id="-password-wordlists"></a>
## 🔐 قوائم كلمات المرور

<a id="most-popular"></a>
### الأكثر شيوعًا

| قائمة الكلمات | الحجم | عدد الأسطر | المصدر | حالة الاستخدام |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | اختبار استعادة كلمات المرور |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | كلمات مرور شائعة |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | قائمة كبيرة لكلمات المرور |
| **common-passwords-win.txt** | - | - | SecLists | كلمات مرور Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | أكثر 1M كلمة مرور شيوعًا |

> **حدود المرجع:** أُبقيت أحجام القوائم وأعداد أسطرها ومساراتها كما في المصدر؛ هذه أرقام وإشارات لنسخ معينة، وقد تختلف مع التحديث أو الضغط وطريقة حساب الحجم. أشجار المجلدات هنا وصفية وليست ضمانًا لوجود كل ملف بالاسم والمسار نفسيهما في إصدار SecLists المثبّت. [مستودع SecLists الرسمي](https://github.com/danielmiessler/SecLists).

<a id="password-lists-by-type"></a>
### قوائم كلمات المرور حسب النوع

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

<a id="specialized-password-lists"></a>
### قوائم كلمات مرور متخصصة

| قائمة الكلمات | الغرض | الموضع |
|----------|---------|----------|
| **wifi-passwords.txt** | اختبار كلمات مرور WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | تحليل إحصائي | SecLists/Passwords/ |
| **seasons-only.txt** | أنماط موسمية | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | كلمات مرور محظورة | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | بيانات اعتماد افتراضية | SecLists/Passwords/Default-Credentials/ |

<a id="kali-locations"></a>
### المواضع في Kali

```bash
# RockYou (فك الضغط أولًا)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

<a id="-directoryfile-wordlists"></a>
## 📂 قوائم أسماء المجلدات والملفات

<a id="directory-brute-forcing"></a>
### تخمين أسماء المجلدات

| قائمة الكلمات | الحجم | عدد الأسطر | المصدر | حالة الاستخدام |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | فحص سريع |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | استطلاع محدود |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | استطلاع اعتيادي |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | استطلاع شامل |
| **big.txt** | 210 KB | 20K | SecLists | مجلدات شائعة |

> **تصحيح للنسبة:** يضع المصدر common.txt تحت DirBuster، بينما تعرض وثائق Kali مثال هذه القائمة ضمن DIRB. قد توجد نسخ أو روابط في مجموعات أخرى؛ بقيت خلايا المصدر والأسماء كما وردت. [مثال DIRB الرسمي في Kali](https://www.kali.org/tools/dirb/).

<a id="raft-series-highly-recommended"></a>
### سلسلة RAFT (يوصي بها المصدر بشدة)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K مجلدًا
├── raft-large-files.txt            # 37K ملفًا
├── raft-large-words.txt            # 120K كلمة
├── raft-medium-directories.txt     # 30K مجلدًا
├── raft-medium-files.txt           # 17K ملفًا
├── raft-medium-words.txt           # 63K كلمة
├── raft-small-directories.txt      # 20K مجلدًا
├── raft-small-files.txt            # 11K ملفًا
└── raft-small-words.txt            # 43K كلمة
```

<a id="file-extension-lists"></a>
### قوائم امتدادات الملفات

| قائمة الكلمات | الغرض |
|----------|---------|
| **web-extensions.txt** | امتدادات ويب شائعة |
| **backup-extensions.txt** | ملفات نسخ احتياطية (.bak, .old) |
| **raft-large-extensions.txt** | قائمة كبيرة للامتدادات |

<a id="specialized-discovery"></a>
### اكتشاف متخصص

| قائمة الكلمات | الغرض | عدد الأسطر |
|----------|---------|-------|
| **apache.txt** | خاصة بـ Apache | - |
| **nginx.txt** | خاصة بـ Nginx | - |
| **iis.txt** | خاصة بـ IIS | - |
| **api-endpoints.txt** | اكتشاف واجهات API | - |
| **graphql.txt** | نقاط نهاية GraphQL | - |
| **swagger.txt** | Swagger/OpenAPI | - |

<a id="common-locations"></a>
### مواضع شائعة

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# قوائم الاكتشاف في SecLists
/usr/share/seclists/Discovery/Web-Content/

# تنزيلات سريعة
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

<a id="-subdomain-wordlists"></a>
## 🌐 قوائم النطاقات الفرعية

<a id="popular-subdomain-lists"></a>
### قوائم نطاقات فرعية شائعة

| قائمة الكلمات | عدد الأسطر | المصدر | حالة الاستخدام |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | استطلاع سريع |
| **subdomains-top1million-20000.txt** | 20K | SecLists | استطلاع اعتيادي |
| **subdomains-top1million-110000.txt** | 110K | SecLists | استطلاع موسّع |
| **dns-Jhaddix.txt** | 2M+ | SecLists | برامج مكافآت الثغرات |
| **fierce-hostlist.txt** | - | SecLists | متوافقة مع Fierce |
| **combined_subdomains.txt** | 648K | SecLists | قائمة مجمّعة |

<a id="subdomain-lists-location"></a>
### مواضع قوائم النطاقات الفرعية

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # الخيار المفضّل في المصدر لبرامج مكافآت الثغرات!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

<a id="bug-bounty-recommended"></a>
### قوائم يوصي بها المصدر لبرامج مكافآت الثغرات

```bash
# منهجية Jason Haddix
dns-Jhaddix.txt              # 2M+ نطاق فرعي

# الدمج لتوسيع التغطية
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

<a id="-username-wordlists"></a>
## 👤 قوائم أسماء المستخدمين

<a id="common-username-lists"></a>
### قوائم أسماء مستخدمين شائعة

| قائمة الكلمات | عدد الأسطر | الغرض |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | اختبار سريع |
| **xato-net-10-million-usernames.txt** | 10M | قائمة كبيرة |
| **cirt-default-usernames.txt** | - | أسماء مستخدمين افتراضية |
| **names.txt** | - | أسماء شائعة |
| **familynames-usa-top1000.txt** | 1K | أسماء عائلات |

<a id="username-lists-location"></a>
### مواضع قوائم أسماء المستخدمين

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

<a id="default-credentials"></a>
### بيانات الاعتماد الافتراضية

| الخدمة | قائمة الكلمات |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

<a id="-web-fuzzing-wordlists"></a>
## 💉 قوائم اختبار مدخلات الويب (Fuzzing)

<a id="sql-injection"></a>
### حقن SQL

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

<a id="xss-cross-site-scripting"></a>
### البرمجة النصية عبر المواقع (XSS)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

<a id="lfirfi-localremote-file-inclusion"></a>
### تضمين الملفات المحلية أو البعيدة (LFI/RFI)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

<a id="command-injection"></a>
### حقن الأوامر

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

<a id="ssti-server-side-template-injection"></a>
### حقن قوالب الخادم (SSTI)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

<a id="fuzzing-collections"></a>
### مجموعات اختبار المدخلات المتنوعة

| قائمة الكلمات | الغرض |
|----------|---------|
| **special-chars.txt** | محارف خاصة |
| **Unicode.txt** | اختبار مدخلات Unicode |
| **big-list-of-naughty-strings.txt** | حالات حدّية |
| **fuzz-Bo0oM.txt** | اختبار عام بمدخلات متنوعة |
| **burp-parameter-names.txt** | أسماء المعاملات |

---

<a id="-seclists-collection"></a>
## 📦 مجموعة SecLists

<a id="about-seclists"></a>
### نبذة عن SecLists

**SecLists** مجموعة تضم أنواعًا متعددة من القوائم المستخدمة في التقييمات الأمنية، وتساعد مختبر الاختراق في مهامه.

<a id="installation"></a>
### التثبيت

```bash
# Kali Linux (مثبّت مسبقًا بحسب المصدر)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# نسخ المستودع باستخدام Git
git clone https://github.com/danielmiessler/SecLists.git

# تنزيل سريع لقوائم محددة
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

> **توضيح للتثبيت:** لا يضمن وجود Kali أن SecLists مثبّت في كل صورة أو بيئة؛ تسرده وثائق Kali ضمن حزم معينة، بينما wordlists حزمة مستقلة. كما يعتمد توفر اسم حزمة apt على مستودعات التوزيعة وإصدارها؛ أمثلة المصدر ليست ضمانًا للتثبيت في أي Ubuntu أو Debian. [حزمة SecLists الرسمية في Kali](https://www.kali.org/tools/seclists/)، [حزمة wordlists](https://www.kali.org/tools/wordlists/).

<a id="seclists-structure"></a>
### بنية SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # قوائم النطاقات الفرعية
│   ├── Web-Content/            # قوائم أسماء المجلدات والملفات
│   └── Infrastructure/         # الخدمات والمنافذ
├── Fuzzing/
│   ├── SQLi/                   # حمولات حقن SQL
│   ├── XSS/                    # حمولات XSS
│   ├── LFI/                    # حمولات LFI/RFI
│   └── command-injection*.txt  # حقن الأوامر
├── Passwords/
│   ├── Common-Credentials/     # قوائم كلمات المرور
│   ├── Leaked-Databases/       # rockyou وغيرها
│   └── Default-Credentials/    # كلمات مرور افتراضية
├── Usernames/
│   ├── Names/                  # قوائم أسماء
│   └── top-usernames*.txt      # قوائم أسماء المستخدمين
├── Payloads/
│   └── Anti-Antivirus/         # تجاوز برامج مكافحة الفيروسات
└── Pattern-Matching/
    └── Hunting/                # أنماط التعابير النمطية
```

<a id="github-repository"></a>
### مستودع GitHub

🔗 https://github.com/danielmiessler/SecLists

---

<a id="-kali-default-wordlists"></a>
## 🐧 قوائم الكلمات الافتراضية في Kali

<a id="wordlists-location"></a>
### مواضع قوائم الكلمات

```bash
# المجلد الرئيسي لقوائم الكلمات
/usr/share/wordlists/

# المحتويات
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # فك الضغط باستخدام gunzip
├── dirb/                   # قوائم DIRB
├── dirbuster/              # قوائم DirBuster
├── fasttrack.txt           # قائمة Fast track
├── fern-wifi/              # قوائم WiFi
├── metasploit/             # قوائم MSF
├── nmap.lst                # قائمة سكربتات Nmap
├── wfuzz/                  # قوائم wFuzz
└── seclists -> /usr/share/seclists/
```

> **تصحيح لوصف nmap.lst:** يربطه مثال Kali الرسمي بملف passwords.lst ضمن بيانات Nmap، أي بقائمة كلمات مرور، لا بقائمة سكربتات كما يذكر تعليق المصدر. احتُفظ باسم الملف وبنية الشجرة وتعليقها مع هذا التوضيح. [توثيق wordlists في Kali](https://www.kali.org/tools/wordlists/).

<a id="dirbuster-lists"></a>
### قوائم DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

<a id="dirb-lists"></a>
### قوائم DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K مدخلًا
├── common.txt               # 4.6K مدخلًا
├── small.txt                # 959 مدخلًا
├── catala.txt               # الكتالونية
├── euskera.txt              # الباسكية
├── extensions_common.txt    # الامتدادات
├── indexes.txt              # ملفات الفهرسة
├── mutations_common.txt     # تحويرات الكلمات
├── spanish.txt              # الإسبانية
├── stress/                  # اختبارات الضغط
└── vulns/                   # اختبارات الثغرات
```

---

<a id="-custom-wordlist-tools"></a>
## 🛠️ أدوات إنشاء قوائم مخصصة

<a id="cewl-custom-word-list-generator"></a>
### CeWL (مولّد قوائم كلمات مخصصة)

```bash
# التثبيت
sudo apt install cewl

# الاستخدام الأساسي - تتبّع صفحات موقع
cewl https://target.com -w wordlist.txt

# تحديد العمق والحد الأدنى لطول الكلمة
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# تضمين عناوين البريد
cewl https://target.com -e -w wordlist.txt

# الخيارات
-d <depth>      # عمق تتبّع الصفحات (الافتراضي: 2)
-m <length>     # الحد الأدنى لطول الكلمة (الافتراضي: 3)
-w <file>       # ملف الخرج
-e              # تضمين عناوين البريد الإلكتروني
--lowercase     # التحويل إلى أحرف صغيرة
-c              # عدّ مرات ظهور الكلمات
```

<a id="crunch-wordlist-generator"></a>
### Crunch (مولّد قوائم كلمات)

```bash
# التثبيت
sudo apt install crunch

# الصيغة الأساسية
crunch <min-len> <max-len> [charset] -o <output>

# توليد كلمات مرور بطول 4-6 محارف
crunch 4 6 -o wordlist.txt

# باستخدام محارف محددة
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# باستخدام نمط
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = أحرف صغيرة, , = أحرف كبيرة, % = أرقام, ^ = محارف خاصة

# مجموعة محارف مخصصة
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# تمرير الخرج إلى Hashcat
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

<a id="cupp-common-user-passwords-profiler"></a>
### CUPP (مولّد مرشّحات كلمات مرور من ملف معلومات المستخدم)

```bash
# التثبيت
git clone https://github.com/Mebus/cupp.git
cd cupp

# وضع تفاعلي يعتمد على معلومات الشخص
python3 cupp.py -i

# الأسئلة المطروحة:
# - الاسم الأول واسم العائلة واللقب
# - تاريخ الميلاد
# - معلومات الشريك
# - اسم الحيوان الأليف
# - اسم الشركة
# - كلمات مفتاحية

# تنزيل كلمات مرور شائعة
python3 cupp.py -l

# استخدام قائمة موجودة مع معلومات الشخص
python3 cupp.py -w wordlist.txt
```

<a id="mentalist-gui-wordlist-generator"></a>
### Mentalist (مولّد قوائم كلمات بواجهة رسومية)

```bash
# أداة رسومية لإنشاء قوائم كلمات مركّبة
# تدعم القواعد وتغيير حالة الأحرف والاستبدالات
# التنزيل من: https://github.com/sc0tfree/mentalist
```

<a id="wordlister-python"></a>
### Wordlister باستخدام Python

```python
#!/usr/bin/env python3
# مولّد بسيط لقائمة كلمات مخصصة
import itertools

# توليد التركيبات
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

<a id="hashcat-rules"></a>
### قواعد Hashcat

```bash
# استخدام القواعد لتحوير قائمة الكلمات
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# قواعد شائعة
/usr/share/hashcat/rules/
├── best64.rule         # قواعد Best 64
├── rockyou-30000.rule  # مبنية على RockYou
├── d3ad0ne.rule        # قواعد d3ad0ne
├── dive.rule           # قواعد DIVE
└── toggles1-5.rule     # تبديل حالة الأحرف
```

---

<a id="-quick-download"></a>
## ⬇️ تنزيل سريع

<a id="one-liner-downloads"></a>
### تنزيلات بأوامر من سطر واحد

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists كاملة
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# كلمات مرور شائعة (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# قائمة مجلدات متوسطة
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# قائمة نطاقات فرعية
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# قائمة Jhaddix DNS لبرامج مكافآت الثغرات
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

<a id="clone-repositories"></a>
### نسخ المستودعات

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# قوائم Assetnote
git clone https://github.com/assetnote/wordlists.git
```

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وممارسات موصى بها

<a id="choosing-the-right-wordlist"></a>
### اختيار قائمة الكلمات المناسبة

| المهمة | قائمة الكلمات التي يوصي بها المصدر |
|------|---------------------|
| **فحص سريع للمجلدات** | common.txt, raft-small-directories.txt |
| **فحص موسّع للمجلدات** | directory-list-2.3-medium.txt |
| **فحص شامل للمجلدات** | directory-list-2.3-big.txt |
| **تعداد النطاقات الفرعية** | subdomains-top1million-20000.txt |
| **استطلاع DNS لبرامج مكافآت الثغرات** | dns-Jhaddix.txt |
| **اختبار استعادة كلمات المرور** | rockyou.txt |
| **اختبار سريع لكلمات المرور** | top-10000.txt |

> الاسم top-10000.txt في هذا الصف غير محدد بمصدر أو مسار؛ يحتفظ به المرجع كما ورد، فلا يُفترض أنه اسم ملف موحّد في جميع المجموعات.

<a id="combining-wordlists"></a>
### دمج قوائم الكلمات

```bash
# الدمج وإزالة التكرار
cat list1.txt list2.txt | sort -u > combined.txt

# إضافة بادئة إلى جميع الأسطر
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# إضافة لاحقة
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# كلا الأسلوبين
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

<a id="wordlist-optimization"></a>
### تحسين قوائم الكلمات

```bash
# الترتيب وإزالة التكرار
sort -u wordlist.txt -o wordlist.txt

# حذف الأسطر الفارغة
sed -i '/^$/d' wordlist.txt

# حذف الأسطر الأقصر من 4 محارف
awk 'length >= 4' wordlist.txt > filtered.txt

# التحويل إلى أحرف صغيرة
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# عدّ الأسطر
wc -l wordlist.txt
```

<a id="performance-tips"></a>
### نصائح للأداء

1. **ابدأ بقائمة صغيرة** - استخدم القوائم الأصغر أولًا ثم وسّعها
2. **استخدم الأنماط** - إذا عرفت الصيغة، فاستخدم أنماطًا محددة
3. **اجمع بين الأدوات** - أنشئ قوائم مخصصة واستخدم القوائم العامة معها
4. **قوائم موجهة للهدف** - أنشئ قوائم خاصة بالهدف باستخدام CeWL

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="top-wordlists-by-category"></a>
### قوائم بارزة حسب الفئة

| الفئة | الخيار الذي يفضّله المصدر | الموضع |
|----------|-------------|----------|
| **كلمات المرور** | rockyou.txt | SecLists/Passwords/ |
| **المجلدات** | directory-list-2.3-medium.txt | DirBuster |
| **الملفات** | raft-large-files.txt | SecLists |
| **النطاقات الفرعية** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **أسماء المستخدمين** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

<a id="size-guide"></a>
### دليل الأحجام

| الحجم | عدد الأسطر | حالة الاستخدام |
|------|-------|----------|
| **صغير** | < 5K | اختبارات سريعة |
| **متوسط** | 5K - 100K | استطلاع اعتيادي |
| **كبير** | 100K - 1M | فحص موسّع |
| **ضخم** | > 1M | تغطية شاملة |

> **حدود التغطية:** عبارة «تغطية شاملة» تصنيف وصفي من المصدر، وليست ضمانًا بأن قائمة كبيرة ستحتوي كل كلمة أو اسم أو مسار محتمل. تتحدد فائدة القائمة بملاءمتها للمهمة والبيئة، لا بعدد أسطرها وحده.

---

<a id="-resources"></a>
## 📚 مصادر

<a id="wordlist-repositories"></a>
### مستودعات قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [قوائم Assetnote](https://github.com/assetnote/wordlists)

<a id="tools"></a>
### أدوات
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [Hydra](../../Hydra/translations/README.ar.md)
- [John the Ripper](../../John-The-Ripper/translations/README.ar.md)
- [Hashcat](../../Hashcat/translations/README.ar.md)
- [Gobuster](../../Gobuster/translations/README.ar.md)
- [ffuf](../../ffuf/translations/README.ar.md)

---

<p align="center">
  <b>📚 اختيار قائمة الكلمات المناسبة يصنع فارقًا!</b><br>
  <i>اختر بعناية وادمج القوائم بذكاء</i>
</p>
