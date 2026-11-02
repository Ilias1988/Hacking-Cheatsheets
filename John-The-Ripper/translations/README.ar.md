<a id="-john-the-ripper---complete-cheatsheet"></a>
# 🔨 John the Ripper — المرجع الشامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  The Password Cracker     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 الأداة الشهيرة لتدقيق أمن كلمات المرور واستعادتها</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصياغة الأساسية](#-basic-syntax)
- [أوضاع كسر كلمات المرور](#-cracking-modes)
- [استخراج البصمات](#-hash-extraction)
- [أنواع البصمات الشائعة](#-common-hash-types)
- [كسر كلمات مرور Linux](#-linux-password-cracking)
- [كسر كلمات مرور Windows](#-windows-password-cracking)
- [كسر كلمات مرور الملفات](#-file-password-cracking)
- [قواعد تحويل الكلمات (Rules & Mangling)](#-rules--mangling)
- [إدارة الجلسات](#-session-management)
- [ضبط الأداء](#-performance-tuning)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**John the Ripper**، ويُختصر إلى John أو JtR، أداة مجانية مفتوحة المصدر لتدقيق أمن كلمات المرور واستعادتها، مصممة لاكتشاف كلمات المرور الضعيفة واستعادة الكلمات المقابلة لبصماتها.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **دعم صيغ متعددة** | يدعم مئات أنواع البصمات |
| **أوضاع متعددة** | أوضاع Single وWordlist وIncremental |
| **قواعد مخصصة** | قواعد قوية لتحويل الكلمات |
| **الاكتشاف الآلي** | اكتشاف آلي لصيغة البصمة |
| **متعدد المنصات** | Linux, Windows, macOS |
| **دعم المعالج الرسومي** | تسريع OpenCL في Jumbo |

<a id="john-vs-hashcat"></a>
### John مقابل Hashcat

| الميزة | John | Hashcat |
|---------|------|---------|
| **الاستخدام الأنسب** | الكسر باستخدام المعالج المركزي | الكسر باستخدام المعالج الرسومي |
| **سهولة الاستخدام** | أسهل | أكثر تعقيدًا |
| **الاكتشاف الآلي** | ✅ نعم | ❌ لا |
| **القواعد** | قوية | قوية جدًا |
| **أنواع البصمات** | كثيرة | أكثر |
| **السرعة على المعالج الرسومي** | جيدة | أفضل |

<a id="versions"></a>
### النسخ

| النسخة | الوصف |
|---------|-------------|
| **John (Core)** | النسخة الأساسية |
| **John Jumbo** | نسخة محسّنة من المجتمع (موصى بها في الأصل) |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبّت مسبقًا وفق الأصل)
```bash
john --version
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
# النسخة القياسية
sudo apt install john

# نسخة Jumbo (موصى بها)
sudo apt install john-jumbo
```

<a id="from-source-jumbo"></a>
### من الشيفرة المصدرية لنسخة Jumbo
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install john-jumbo
```

<a id="windows"></a>
### Windows
```bash
# التنزيل من الموقع الرسمي
https://www.openwall.com/john/

# أو استخدام Chocolatey
choco install john
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
john --version
john --list=formats | wc -l    # عدّ الصيغ المدعومة وفق وصف الأصل
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصياغة الأساسية

<a id="general-syntax"></a>
### الصياغة العامة
```bash
john [options] [password_file]
```

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الوصف |
|--------|-------------|
| `--wordlist=` | مسار قائمة الكلمات |
| `--format=` | تحديد صيغة البصمة |
| `--rules` | تفعيل قواعد تحويل الكلمات |
| `--show` | عرض كلمات المرور المستعادة |
| `--single` | وضع Single Crack |
| `--incremental` | وضع التخمين الشامل |
| `--session=` | تسمية الجلسة |
| `--restore` | استعادة جلسة سابقة |
| `--pot=` | ملف pot مخصص |

<a id="quick-start"></a>
### بداية سريعة
```bash
# الاكتشاف الآلي ثم الكسر
john hashes.txt

# باستخدام قائمة كلمات
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# عرض كلمات المرور المستعادة
john --show hashes.txt
```

---

<a id="-cracking-modes"></a>
## 🎮 أوضاع كسر كلمات المرور

<a id="single-crack-mode"></a>
### وضع Single Crack

يستخدم أسماء تسجيل الدخول ومعلومات GECOS، مثل بيانات وصف الحساب، لتوليد كلمات مرور مرشحة.

```bash
# وضع Single (يجرّب تنويعات اسم المستخدم؛ يصفه الأصل بأنه الأسرع)
john --single hashes.txt

# مع تحديد الصيغة
john --single --format=sha512crypt hashes.txt
```

<a id="wordlist-mode"></a>
### وضع قائمة الكلمات (Wordlist)

يستخدم كلمات من قاموس مع إمكانية تطبيق قواعد تحويل عليها.

```bash
# هجوم أساسي بقائمة كلمات
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# قائمة كلمات مع قواعد تحويل
john --wordlist=wordlist.txt --rules hashes.txt

# مجموعة قواعد محددة
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

<a id="incremental-mode-brute-force"></a>
### الوضع التزايدي (Incremental) للتخمين الشامل

يجرّب تركيبات المحارف الممكنة ضمن إعدادات الوضع المحددة.

```bash
# الوضع التزايدي الافتراضي (جميع المحارف وفق الأصل)
john --incremental hashes.txt

# مجموعة محارف محددة
john --incremental=Digits hashes.txt      # أرقام فقط
john --incremental=Lower hashes.txt       # أحرف صغيرة فقط
john --incremental=Upper hashes.txt       # أحرف كبيرة فقط
john --incremental=Alpha hashes.txt       # أحرف فقط
john --incremental=Alnum hashes.txt       # أحرف وأرقام
john --incremental=LowerNum hashes.txt    # أحرف صغيرة وأرقام
```

<a id="mask-mode"></a>
### وضع القناع (Mask)

تخمين شامل يعتمد على نمط محدد.

```bash
# هجوم بالقناع، حيث تشير ? إلى الرموز النائبة
john --mask='?l?l?l?l?d?d' hashes.txt

# الرموز النائبة:
# ?l = أحرف صغيرة    ?u = أحرف كبيرة
# ?d = أرقام       ?s = محارف خاصة
# ?a = الجميع          ?h = ست عشري بأحرف صغيرة
```

<a id="external-mode"></a>
### الوضع الخارجي (External)

خوارزميات مخصصة لتوليد المرشحات أو ترشيحها.

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="-hash-extraction"></a>
## 🔍 استخراج البصمات

<a id="linux-passwords"></a>
### كلمات مرور Linux

```bash
# دمج ملفي passwd وshadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# كسر البصمات في الملف المدمج
john unshadowed.txt
```

<a id="windows-hashes"></a>
### بصمات Windows

```bash
# من ملف SAM باستخدام pwdump أو secretsdump أو غيرهما
# Format: user:id:LM_hash:NTLM_hash:::

# باستخدام hashdump في Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# الحفظ في ملف ثم الكسر
john --format=nt hashes.txt
```

<a id="file-passwords-2john-tools"></a>
### كلمات مرور الملفات باستخدام أدوات 2john

```bash
# ملفات ZIP
zip2john protected.zip > zip_hash.txt

# ملفات RAR
rar2john protected.rar > rar_hash.txt

# ملفات PDF
pdf2john protected.pdf > pdf_hash.txt

# مستندات Office
office2john document.docx > office_hash.txt

# مفاتيح SSH
ssh2john id_rsa > ssh_hash.txt

# ملفات 7zip
7z2john archive.7z > 7z_hash.txt

# قواعد بيانات KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="-common-hash-types"></a>
## 🔢 أنواع البصمات الشائعة

<a id="hash-format-table"></a>
### جدول صيغ البصمات

| نوع البصمة | خيار الصيغة | المثال |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

<a id="list-all-formats"></a>
### سرد جميع الصيغ
```bash
# سرد جميع الصيغ المدعومة
john --list=formats

# البحث عن صيغة محددة
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# عدّ الصيغ وفق وصف الأصل
john --list=formats | wc -l
```

<a id="identify-hash-type"></a>
### تحديد نوع البصمة
```bash
# استخدام hash-identifier
hash-identifier

# أو hashid
hashid -m '$6$salt$hash...'
```

---

<a id="-linux-password-cracking"></a>
## 🐧 كسر كلمات مرور Linux

<a id="prepare-hashes"></a>
### تجهيز البصمات
```bash
# دمج passwd وshadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# أو استخراج البصمة وحدها
sudo cat /etc/shadow | grep username
```

<a id="hash-format-examples"></a>
### أمثلة صيغ البصمات
```
# /etc/shadow format:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (الأشيع في Linux الحديث وفق الأصل)
# $y$ = yescrypt (الأحدث وفق الأصل)
```

<a id="crack-linux-hashes"></a>
### كسر بصمات Linux
```bash
# الاكتشاف الآلي (موصى به)
john linux_hashes.txt

# تحديد الصيغة
john --format=sha512crypt linux_hashes.txt

# باستخدام قائمة كلمات
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# عرض النتائج
john --show linux_hashes.txt
```

---

<a id="-windows-password-cracking"></a>
## 🪟 كسر كلمات مرور Windows

<a id="hash-types"></a>
### أنواع البصمات

| النوع | الوصف | الصيغة |
|------|-------------|--------|
| **LM** | قديم وضعيف | `--format=lm` |
| **NTLM** | إصدارات Windows الحديثة | `--format=nt` |
| **NTLMv2** | مصادقة الشبكة | `--format=netntlmv2` |

<a id="crack-ntlm-hashes"></a>
### كسر بصمات NTLM
```bash
# صيغة بصمة NTLM:
# user:rid:lm_hash:ntlm_hash:::

# كسر NTLM
john --format=nt windows_hashes.txt

# باستخدام قائمة كلمات
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# باستخدام قواعد تحويل
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

<a id="extract-windows-hashes"></a>
### استخراج بصمات Windows
```bash
# باستخدام Metasploit
meterpreter> hashdump

# باستخدام secretsdump من Impacket
secretsdump.py -sam sam -system system LOCAL

# باستخدام pwdump
pwdump7.exe
```

---

<a id="-file-password-cracking"></a>
## 📄 كسر كلمات مرور الملفات

<a id="zip-files"></a>
### ملفات ZIP
```bash
# استخراج البصمة
zip2john protected.zip > zip.hash

# كسر البصمة
john --wordlist=rockyou.txt zip.hash

# عرض كلمة المرور
john --show zip.hash
```

<a id="rar-files"></a>
### ملفات RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

<a id="pdf-files"></a>
### ملفات PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

<a id="office-documents"></a>
### مستندات Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

<a id="ssh-private-keys"></a>
### مفاتيح SSH الخاصة
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

<a id="keepass-database"></a>
### قاعدة بيانات KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-rules--mangling"></a>
## ⚙️ قواعد تحويل الكلمات (Rules & Mangling)

<a id="enable-rules"></a>
### تفعيل القواعد
```bash
# القواعد الافتراضية
john --wordlist=wordlist.txt --rules hashes.txt

# مجموعة قواعد محددة
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

<a id="list-available-rules"></a>
### سرد القواعد المتاحة
```bash
john --list=rules
```

<a id="common-rule-operations"></a>
### عمليات القواعد الشائعة

| القاعدة | الوصف | المثال |
|------|-------------|---------|
| `:` | ترك الكلمة دون تغيير | password → password |
| `l` | تحويل جميع الأحرف إلى صغيرة | PassWord → password |
| `u` | تحويل جميع الأحرف إلى كبيرة | password → PASSWORD |
| `c` | جعل الحرف الأول كبيرًا | password → Password |
| `r` | عكس الترتيب | password → drowssap |
| `d` | تكرار الكلمة | pass → passpass |
| `$X` | إلحاق المحرف X بالنهاية | pass → passX |
| `^X` | إضافة المحرف X في البداية | pass → Xpass |
| `sXY` | استبدال X بـ Y | password → pa$$word |

<a id="custom-rules"></a>
### قواعد مخصصة
```bash
# الإضافة إلى john.conf ضمن [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="-session-management"></a>
## 💾 إدارة الجلسات

<a id="create-named-session"></a>
### إنشاء جلسة مسمّاة
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

<a id="restore-session"></a>
### استعادة جلسة
```bash
# استعادة جلسة انقطعت
john --restore=mycrack

# استعادة الجلسة الافتراضية
john --restore
```

<a id="check-status"></a>
### فحص الحالة
```bash
# اضغط أي مفتاح أثناء الكسر لعرض الحالة
# أو افحص ملف .rec
cat ~/.john/mycrack.rec
```

<a id="pot-file"></a>
### ملف النتائج المحفوظة (Pot File)
```bash
# ملف pot الافتراضي
~/.john/john.pot

# ملف pot مخصص
john --pot=custom.pot hashes.txt

# عرض جميع كلمات المرور المستعادة
john --show --pot=custom.pot hashes.txt
```

---

<a id="-performance-tuning"></a>
## 🚀 ضبط الأداء

<a id="fork-multi-process"></a>
### التشغيل بعمليات متعددة باستخدام Fork
```bash
# استخدام عدة أنوية للمعالج المركزي
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

<a id="opencl-gpu"></a>
### OpenCL (GPU)
```bash
# سرد أجهزة OpenCL
john --list=opencl-devices

# استخدام المعالج الرسومي
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

<a id="limit-candidates"></a>
### تقييد الكلمات المرشحة
```bash
# تقييد طول كلمة المرور
john --min-length=6 --max-length=12 --incremental hashes.txt
```

<a id="memory-usage"></a>
### استخدام الذاكرة
```bash
# تقليل استخدام الذاكرة
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-linux-shadow-file"></a>
### المثال 1: ملف shadow في Linux
```bash
# دمج الملفات
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# الكسر باستخدام rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# عرض النتائج
john --show hashes.txt
```

<a id="example-2-windows-ntlm"></a>
### المثال 2: بصمات NTLM في Windows
```bash
# من مخرجات hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# كسر البصمة
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

<a id="example-3-zip-file-password"></a>
### المثال 3: كلمة مرور ملف ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

<a id="example-4-ssh-key-passphrase"></a>
### المثال 4: عبارة مرور مفتاح SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

<a id="example-5-md5-hashes"></a>
### المثال 5: بصمات MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

<a id="example-6-web-application-hashes"></a>
### المثال 6: بصمات تطبيقات الويب
```bash
# بصمة bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

<a id="example-7-with-rules"></a>
### المثال 7: استخدام القواعد
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

<a id="example-8-incremental-attack"></a>
### المثال 8: هجوم تزايدي
```bash
# تجربة جميع التركيبات بطول 6-8 من الأحرف الصغيرة والأرقام
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| المهمة | الأمر |
|------|---------|
| كسر أساسي | `john hashes.txt` |
| هجوم بقائمة كلمات | `john --wordlist=rockyou.txt hashes.txt` |
| باستخدام قواعد تحويل | `john --wordlist=words.txt --rules hashes.txt` |
| عرض الكلمات المستعادة | `john --show hashes.txt` |
| صيغة محددة | `john --format=raw-md5 hashes.txt` |
| الوضع التزايدي | `john --incremental hashes.txt` |
| استعادة جلسة | `john --restore=session` |
| سرد الصيغ | `john --list=formats` |

<a id="2john-tools"></a>
### أدوات 2john

| الأداة | الغرض |
|------|---------|
| `unshadow` | Linux passwd/shadow |
| `zip2john` | ملفات ZIP |
| `rar2john` | ملفات RAR |
| `pdf2john` | ملفات PDF |
| `office2john` | مستندات Office |
| `ssh2john` | مفاتيح SSH |
| `keepass2john` | قواعد بيانات KeePass |

<a id="common-formats"></a>
### الصيغ الشائعة

| Hash | خيار الصيغة |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="efficiency-tips"></a>
### نصائح لزيادة الكفاءة

1. **جرّب وضع Single أولًا**
   ```bash
   john --single hashes.txt
   ```

2. **استخدم قوائم كلمات تناسب الهدف**
   ```bash
   # اسم الشركة والمدينة وغيرهما
   john --wordlist=targeted.txt hashes.txt
   ```

3. **ابدأ بقوائم كلمات صغيرة**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # ثم جرّب قائمة أكبر
   ```

4. **اجمع بين الأوضاع**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

<a id="performance-tips"></a>
### نصائح لتحسين الأداء

```bash
# استخدام عدة أنوية
john --fork=4 hashes.txt

# تسريع بالمعالج الرسومي في Jumbo
john --format=sha512crypt-opencl hashes.txt
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تنبيه:** John the Ripper أداة قوية لكسر كلمات المرور، ويجب استخدامها فقط في **أغراض مأذون بها**.
> 
> - ✅ دقّق كلمات المرور الخاصة بك
> - ✅ اختبر بإذن صريح
> - ✅ أجرِ التقييمات الأمنية بعد الحصول على إذن
> - ❌ لا تكسر كلمات مرور دون إذن
> - ❌ الوصول غير المأذون به مخالف للقانون
> 
> **كسر كلمات المرور دون إذن مخالف للقانون وقد يؤدي إلى اتهامات جنائية، وفق التنبيه الوارد في الأصل.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### الموارد الرسمية
- [الموقع الرسمي لـ John the Ripper](https://www.openwall.com/john/)
- [مستودع John Jumbo على GitHub](https://github.com/openwall/john)
- [ويكي John](https://openwall.info/wiki/john)

<a id="wordlists"></a>
### قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [Hydra](../../Hydra/translations/README.ar.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)

---

<p align="center">
  <b>🔨 أتقن كسر كلمات المرور!</b><br>
  <i>استخدم مهارات كسر كلمات المرور بمسؤولية</i>
</p>

> **مصطلحات:** البصمة (Hash) ناتج دالة أحادية الاتجاه؛ تتولّد الكلمات المرشحة وتُقارن نتائجها بالبصمة، ولا تُفك البصمة كما يُفك تشفير نص. قائمة الكلمات (Wordlist) مصدر المرشحات، والقواعد (Rules) تحوّلها، والقناع (Mask) يحدد شكلها. يحفظ ملف pot نتائج الكلمات المستعادة. عنوان الرسم الإنجليزي يعني «أداة كسر كلمات المرور» وقد حُفظت محاذاته.

> **تنبيه للأمثلة المختصرة:** يتضمن الأصل بصمات تنتهي بـ `...` وبصمات مقطوعة في بعض أمثلة hashdump وNTLM؛ هي أمثلة بنيوية وليست جميعها مدخلات مكتملة قابلة للكسر كما هي. كذلك فإن `john --list=formats | wc -l` يعدّ أسطر العرض، ولا يضمن عدّ كل صيغة إن جُمعت عدة أسماء في السطر نفسه. تختلف الأوامر والصيغ وأسماء أدوات 2john بحسب بناء Core أوJumbo وطريقة تغليف التوزيعة. [توثيق خيارات Jumbo](https://github.com/openwall/john/blob/bleeding-jumbo/doc/OPTIONS).

> **تصحيح لقواعد التحويل:** تنفّذ `$X` و`^X` إضافة محرف واحد. لذلك لا تعبّر الأسطر `$123` و`^2023` في مثال الأصل عن إضافة السلسلتين 123 و2023 بأكملهما. يجب بناء تسلسل أوأمر سلاسل صالح وفق الصيغة الموثقة. حُفظت القواعد الأصلية للمقارنة. [مرجع قواعد Openwall](https://www.openwall.com/john/doc/RULES.shtml).

> **توضيح للمقارنة:** يملك Hashcat الخيار `--identify` لعرض الخوارزميات المدعومة المحتملة للبصمات المدخلة، خلاف الانطباع الذي تعطيه خانة «لا» في مقارنة الاكتشاف الآلي. لا تعني هذه الإمكانية اختيارًا يقينيًا لصيغة ملتبسة، وتصنيفات السرعة والسهولة في الجدول من الأصل وليست اختبار أداء لهذه الترجمة. [توثيق Hashcat الرسمي](https://hashcat.net/wiki/doku.php?id=hashcat).
