<a id="-hashcat---complete-cheatsheet"></a>
# ⚡ Hashcat — دليل مرجعي شامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    World's Fastest Password Recovery
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 أداة استعادة كلمات المرور الأسرع والأكثر تقدماً في العالم وفق وصف الأصل</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [أنماط الهجوم](#-attack-modes)
- [أنماط البصمات](#-hash-modes)
- [هجوم القناع (Mask Attack)](#-mask-attack)
- [القواعد (Rules)](#-rules)
- [إدارة الجلسات](#-session-management)
- [تحسين أداء GPU](#-gpu-optimization)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Hashcat** أداة لاستعادة كلمات المرور باستخدام تسريع المعالجة الرسومية (GPU). يصفها الأصل بأنها الأسرع والأكثر تقدماً في العالم، مع دعم أكثر من 350 نوعاً من البصمات؛ هذه الأعداد والمقارنات تعكس وصف الأصل وليست إحصاءً محدثاً.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **أكثر من 350 نوعاً من البصمات** | MD5 وSHA وNTLM وbcrypt وWPA وغيرها |
| **مسرّع باستخدام GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **يدعم عدة وحدات GPU** | دعم الكسر الموزّع وفق وصف الأصل |
| **5 أنماط هجوم وفق الأصل** | القاموس والقوة الغاشمة والهجوم الهجين وغيرها |
| **قواعد قوية** | توليد صيغ متقدمة من الكلمات |
| **متعدد المنصات** | Linux, Windows, macOS |

<a id="hashcat-vs-john-the-ripper"></a>
### مقارنة Hashcat مع John the Ripper

> **توضيح الإصدار:** صف الاكتشاف التلقائي في الأصل قديم؛ يدعم Hashcat التعرف على الأنواع المرشحة، وتوثق [المساعدة الرسمية](https://hashcat.net/wiki/doku.php?id=hashcat) الخيار `--identify`. قد تتشابه تنسيقات البصمات، فلا تعد النتيجة إثباتاً وحيداً للخوارزمية. أعداد الأنواع والنجوم ومقارنات الأداء في الجداول وصف مبسّط من الأصل.


| الميزة | Hashcat | John |
|---------|---------|------|
| **السرعة** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **دعم GPU** | ممتاز | جيد |
| **أنواع البصمات** | 350+ | 300+ |
| **الاكتشاف التلقائي** | ❌ لا | ✅ نعم |
| **سهولة الاستخدام** | متوسطة | أسهل |
| **الأنسب لـ** | الكسر باستخدام GPU | الكسر باستخدام CPU |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# مع OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install hashcat
```

<a id="windows"></a>
### Windows
```bash
# التنزيل من الموقع الرسمي
https://hashcat.net/hashcat/

# فك الضغط والتشغيل من المجلد
hashcat.exe -h
```

<a id="from-source"></a>
### من الشيفرة المصدرية
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
hashcat --version
hashcat -I                    # عرض أجهزة OpenCL
hashcat -b                    # تشغيل اختبار الأداء
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

<a id="essential-options"></a>
### الخيارات الضرورية

| الخيار | الوصف |
|--------|-------------|
| `-m` | نوع البصمة أو نمطها |
| `-a` | نمط الهجوم |
| `-o` | ملف المخرجات |
| `-r` | ملف القواعد |
| `--show` | عرض كلمات المرور المستعادة |
| `--status` | تفعيل شاشة الحالة |
| `-w` | ملف عبء العمل (1-4) |
| `--force` | تجاهل التحذيرات |
| `-O` | أنوية معالجة محسّنة |

<a id="quick-start"></a>
### بداية سريعة
```bash
# هجوم قاموس أساسي
hashcat -m 0 -a 0 hash.txt wordlist.txt

# عرض البصمات المستعادة
hashcat -m 0 hash.txt --show

# مراجعة أمثلة البصمات
hashcat -m 0 --example-hashes
```

---

<a id="-attack-modes"></a>
## 🎮 أنماط الهجوم

<a id="overview"></a>
### نظرة عامة

| النمط | الاسم | الوصف |
|------|------|-------------|
| `-a 0` | القاموس | هجوم باستخدام قائمة كلمات |
| `-a 1` | دمج القوائم | دمج قائمتين للكلمات |
| `-a 3` | القوة الغاشمة | هجوم قائم على قناع (Mask) |
| `-a 6` | هجين: قاموس ثم قناع | قائمة كلمات ثم قناع |
| `-a 7` | هجين: قناع ثم قاموس | قناع ثم قائمة كلمات |
| `-a 9` | الربط (Association) | ربط الكلمات بالبصمات |

<a id="dictionary-attack--a-0"></a>
### هجوم القاموس (-a 0)

```bash
# هجوم قاموس أساسي
hashcat -m 0 -a 0 hash.txt wordlist.txt

# مع قواعد
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# عدة قوائم كلمات
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

<a id="combination-attack--a-1"></a>
### هجوم دمج القوائم (-a 1)

يدمج كلمات من قائمتين للكلمات.

```bash
# دمج قائمتين للكلمات
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# تراكيب بالشكل word1word2
```

<a id="brute-forcemask-attack--a-3"></a>
### هجوم القوة الغاشمة أو القناع (-a 3)

> **تصحيح:** مثال الأرقام 4-8 يضع قناعاً من أربع خانات فقط. توضح [وثائق الأقنعة](https://hashcat.net/wiki/doku.php?id=mask_attack) أن `--increment` لا يتجاوز طول القناع نفسه؛ لذا لا يختبر هذا المثال الأطوال حتى 8 كما يدعي التعليق. حُفظ القناع دون تعديل.


```bash
# أحرف صغيرة فقط بطول 6 أحرف
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# أرقام فقط بطول 4-8 أحرف وفق تعليق الأصل
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# مجموعة أحرف مخصصة
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

<a id="hybrid-attacks--a-6--a-7"></a>
### الهجمات الهجينة (-a 6, -a 7)

```bash
# قاموس ثم قناع يضاف في النهاية
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# قناع يضاف في البداية ثم قاموس
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

<a id="-hash-modes"></a>
## 🔢 أنماط البصمات

<a id="most-common-hash-modes"></a>
### أنماط البصمات الأكثر شيوعاً

> **ملاحظة تنسيق:** أمثلة البصمات التي تحتوي `...` مختصرة وليست قيماً صالحة للاستخدام كما هي. وتختلف الصيغ والاستخراج المسبق حسب النوع؛ راجع [أمثلة Hashcat الرسمية](https://hashcat.net/wiki/doku.php?id=example_hashes). أرقام الأنماط وأسماء الخوارزميات هنا محفوظة من الأصل.


| النمط | نوع البصمة | المثال |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | تنسيق مركب |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

<a id="web-applications"></a>
### تطبيقات الويب

| النمط | نوع البصمة |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

<a id="databases"></a>
### قواعد البيانات

| النمط | نوع البصمة |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

<a id="documents--archives"></a>
### المستندات والأرشيفات

| النمط | نوع البصمة |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

<a id="cryptocurrency"></a>
### العملات المشفرة

> **تصحيح للجدول:** النمط 15200 موصوف هنا بأنه Ethereum Keystore، بينما يسجله [مرجع Hashcat الرسمي](https://hashcat.net/wiki/doku.php?id=example_hashes) باسم Blockchain, My Wallet, V2. حُفظت القيمة والتسمية الأصليتان مع هذا التنبيه؛ اختر النمط وفق تنسيق البصمة الفعلي.


| النمط | نوع البصمة |
|------|-----------|
| 11300 | محفظة Bitcoin |
| 12700 | محفظة Blockchain |
| 16600 | محفظة Electrum |
| 15200 | Ethereum Keystore |

<a id="listsearch-modes"></a>
### عرض الأنماط والبحث فيها
```bash
# عرض جميع أنماط البصمات
hashcat --help | grep -i "hash modes"

# البحث عن نوع بصمة محدد
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# عرض مثال لتنسيق البصمة
hashcat -m 1000 --example-hashes
```

---

<a id="-mask-attack"></a>
## 🎭 هجوم القناع (Mask Attack)

<a id="charset-placeholders"></a>
### رموز مجموعات الأحرف

> **ملاحظة:** حُفظت قائمة `?s` كما في الأصل، لكنها مختصرة؛ يضم التعريف الرسمي أيضاً المسافة والشرطة المائلة العكسية. راجع [توثيق مجموعات الأحرف](https://hashcat.net/wiki/doku.php?id=mask_attack) عند بناء القناع، ولا تعتبر نص هذا الصف قائمة كاملة.


| الرمز | الأحرف |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | ``!"#$%&'()*+,-./:;<=>?@[]^_`{\|}~`` |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

<a id="custom-charsets"></a>
### مجموعات أحرف مخصصة

```bash
# تعريف مجموعة أحرف مخصصة
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# عدة مجموعات أحرف مخصصة
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# أحرف خاصة
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

<a id="increment-mode"></a>
### وضع زيادة الطول (Increment)

```bash
# تجربة الأطوال 1-8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# تحديد الحدين الأدنى والأقصى
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

<a id="common-mask-patterns"></a>
### أنماط أقنعة شائعة

```bash
# 8 أحرف صغيرة
?l?l?l?l?l?l?l?l

# كلمة password تليها 4 أرقام
password?d?d?d?d

# حرف كبير ثم أحرف صغيرة ثم أرقام
?u?l?l?l?l?l?d?d

# نمط Winter2024
?u?l?l?l?l?l?d?d?d?d

# رقم هاتف
?d?d?d?d?d?d?d?d?d?d
```

---

<a id="-rules"></a>
## 📜 القواعد (Rules)

<a id="built-in-rule-files"></a>
### ملفات القواعد المرفقة

```bash
# الموقع
ls /usr/share/hashcat/rules/

# قواعد شائعة
best64.rule           # أفضل 64 قاعدة وفق الأصل
rockyou-30000.rule    # أبرز 30000 قاعدة وفق الأصل
d3ad0ne.rule          # قواعد D3ad0ne
dive.rule             # قواعد Dive
generated.rule        # قواعد مولّدة
```

<a id="using-rules"></a>
### استخدام القواعد

```bash
# ملف قواعد واحد
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# عدة ملفات قواعد
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# توليد قواعد
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

<a id="common-rule-operations"></a>
### عمليات القواعد الشائعة

| القاعدة | الوصف | المثال |
|------|-------------|---------|
| `:` | دون تغيير | password |
| `l` | تحويل جميع الأحرف إلى صغيرة | PASSWORD → password |
| `u` | تحويل جميع الأحرف إلى كبيرة | password → PASSWORD |
| `c` | تكبير الحرف الأول | password → Password |
| `C` | تصغير الحرف الأول وتكبير البقية | password → pASSWORD |
| `t` | تبديل حالة الأحرف | password → PASSWORD |
| `r` | عكس الترتيب | password → drowssap |
| `d` | تكرار الكلمة | pass → passpass |
| `$X` | إلحاق X في النهاية | pass → passX |
| `^X` | إضافة X في البداية | pass → Xpass |
| `sXY` | استبدال X بـ Y | password → pa$$word |

---

<a id="-session-management"></a>
## 💾 إدارة الجلسات

<a id="create-session"></a>
### إنشاء جلسة
```bash
# جلسة ذات اسم
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# تُحفظ ملفات الجلسات في ~/.hashcat/sessions/ وفق الأصل
```

<a id="pause--resume"></a>
### الإيقاف المؤقت والاستئناف
```bash
# إيقاف مؤقت: اضغط 'p' أثناء الاستعادة
# استئناف: اضغط 'r' أثناء الاستعادة

# أو استعادة الجلسة من سطر الأوامر
hashcat --session=mysession --restore
```

<a id="status-commands"></a>
### أوامر الحالة

أثناء عملية الاستعادة:
- `s` - عرض الحالة
- `p` - إيقاف مؤقت
- `r` - استئناف
- `b` - تجاوز الهجوم الحالي
- `c` - التوقف عند نقطة استئناف
- `q` - إنهاء

<a id="output-options"></a>
### خيارات المخرجات

> **تصحيح:** قيمة `--outfile-format=2` تعني `plain` فقط، خلافاً لتعليق `hash:plain` في الأصل؛ دمج حقلي البصمة والنص يعتمد قيم الصيغة المناسبة. يوضح ذلك [مصدر المساعدة الرسمي للإصدار 6.2.6](https://github.com/hashcat/hashcat/blob/v6.2.6/src/usage.c).

```bash
# حفظ المخرجات في ملف
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# صيغة المخرجات
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# عرض النتائج المستعادة
hashcat -m 0 hash.txt --show

# عرض البصمات المتبقية غير المستعادة
hashcat -m 0 hash.txt --left
```

---

<a id="-gpu-optimization"></a>
## 🚀 تحسين أداء GPU

<a id="workload-profiles"></a>
### ملفات عبء العمل

> **تصحيح إصدار:** في [المساعدة الرسمية لـ Hashcat 6.2.6](https://github.com/hashcat/hashcat/blob/v6.2.6/src/usage.c)، الملف الافتراضي هو 2، وليس 1 كما في الجدول. خيارات الضبط والأجهزة قد تتغير بين الإصدارات؛ تحقق من مساعدة نسختك. الخيار `--force` يتجاهل التحذيرات ولا يُعد تحسيناً للسرعة بحد ذاته.


| الملف | الوصف |
|---------|-------------|
| `-w 1` | منخفض (الافتراضي والتفاعلي وفق وصف الأصل) |
| `-w 2` | متوسط |
| `-w 3` | مرتفع وقد يجعل سطح المكتب غير مستجيب |
| `-w 4` | Nightmare لجهاز مخصص للاستعادة |

<a id="optimize-commands"></a>
### أوامر تحسين الأداء
```bash
# أداء مرتفع
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# أنوية محسّنة أسرع مع قيود على طول كلمة المرور
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# تحديد جهاز
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# استبعاد CPU واستخدام GPU فقط
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

<a id="device-options"></a>
### خيارات الأجهزة
```bash
# عرض الأجهزة
hashcat -I

# اختيار جهاز محدد
hashcat -d 1 ...

# أنواع الأجهزة
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

<a id="benchmark"></a>
### اختبار الأداء (Benchmark)

> **تصحيح:** `--benchmark-all` يوسّع اختبار الأداء إلى جميع أنماط البصمات، وليس لتعطيل تحسين OpenCL كما يقول تعليق الأصل. [المساعدة الرسمية](https://hashcat.net/wiki/doku.php?id=hashcat).

```bash
# اختبار أداء كامل وفق الأصل
hashcat -b

# نوع بصمة محدد
hashcat -b -m 0

# اختبار أداء دون تحسين OpenCL وفق تعليق الأصل
hashcat -b --benchmark-all
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-md5-dictionary-attack"></a>
### المثال 1: هجوم قاموس على MD5
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

<a id="example-2-ntlm-with-rules"></a>
### المثال 2: NTLM مع قواعد
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

<a id="example-3-wpawpa2-handshake"></a>
### المثال 3: مصافحة WPA/WPA2
```bash
# التحويل أولاً إلى تنسيق Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# استعادة كلمة المرور
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

<a id="example-4-sha256-brute-force-6-chars"></a>
### المثال 4: القوة الغاشمة على SHA256 بطول 6 أحرف
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

<a id="example-5-office-2013-document"></a>
### المثال 5: مستند Office 2013
```bash
# استخراج البصمة باستخدام office2john
office2john document.docx > office.hash

# استعادة كلمة المرور
hashcat -m 9600 office.hash wordlist.txt
```

<a id="example-6-linux-shadow-password"></a>
### المثال 6: كلمة مرور من ملف shadow في Linux
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

<a id="example-7-wordpress-hash"></a>
### المثال 7: بصمة WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

<a id="example-8-hybrid-attack"></a>
### المثال 8: هجوم هجين
```bash
# اسم شركة تتبعه 4 أرقام
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

<a id="example-9-windows-domain-hashes"></a>
### المثال 9: بصمات مجال Windows
```bash
# بصمات NTLM من secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

<a id="example-10-bitcoin-wallet"></a>
### المثال 10: محفظة Bitcoin
```bash
# الاستخراج باستخدام bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الضرورية

| المهمة | الأمر |
|------|---------|
| قاموس لـ MD5 | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| القوة الغاشمة على NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 مع قواعد | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| عرض النتائج المستعادة | `hashcat -m 0 hash.txt --show` |
| اختبار الأداء (Benchmark) | `hashcat -b` |
| عرض الأجهزة | `hashcat -I` |
| استعادة الجلسة | `hashcat --session=name --restore` |

<a id="common-hash-modes"></a>
### أنماط بصمات شائعة

| النمط | النوع |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

<a id="attack-modes"></a>
### أنماط الهجوم

| النمط | النوع |
|------|------|
| -a 0 | القاموس |
| -a 1 | دمج القوائم |
| -a 3 | القوة الغاشمة |
| -a 6 | هجين: قاموس ثم قناع |
| -a 7 | هجين: قناع ثم قاموس |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="efficiency-tips"></a>
### نصائح الكفاءة

1. **البدء بقاموس مع قواعد**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **استخدام أنوية المعالجة المحسّنة**
   ```bash
   hashcat -O ...
   ```

3. **استهداف الأنماط الشائعة**
   ```bash
   # فصل مع سنة: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

<a id="performance-tips"></a>
### نصائح الأداء

```bash
# أقصى أداء على جهاز مخصص وفق الأصل
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# عدة وحدات GPU
hashcat -d 1,2 ...
```

<a id="workflow"></a>
### سير العمل

```
1. تحديد نوع البصمة
2. تجربة هجوم القاموس مع القواعد
3. تجربة أقنعة تستهدف أنماطاً محددة
4. تجربة القوة الغاشمة الكاملة إذا كانت مجدية
5. تجربة الهجمات الهجينة
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** Hashcat أداة قوية لاستعادة كلمات المرور، وينبغي استخدامها فقط في **أغراض مصرح بها**.
> 
> - ✅ دقّق أنظمتك الخاصة
> - ✅ تقييمات أمنية بإذن
> - ✅ استعادة كلمات مرورك الخاصة
> - ❌ لا تكسر كلمات المرور دون تفويض
> - ❌ الوصول غير المصرح به مخالف للقانون
> 
> **كسر كلمات المرور دون تصريح مخالف للقانون وقد يؤدي إلى اتهامات جنائية.**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="official-resources"></a>
### المراجع الرسمية
- [الموقع الرسمي لـ Hashcat](https://hashcat.net/hashcat/)
- [ويكي Hashcat](https://hashcat.net/wiki/)
- [مستودع Hashcat على GitHub](https://github.com/hashcat/hashcat)
- [منتدى Hashcat](https://hashcat.net/forum/)

<a id="wordlists"></a>
### قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

<a id="related-cheatsheets"></a>
### أدلة مرجعية ذات صلة
- [John the Ripper](../../John-The-Ripper/translations/README.ar.md)
- [Hydra](../../Hydra/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)

---

<p align="center">
  <b>⚡ أتقن استعادة كلمات المرور باستخدام GPU!</b><br>
  <i>استعادة كلمات المرور الأسرع في العالم وفق وصف الأصل</i>
</p>
