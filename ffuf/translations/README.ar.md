<a id="-ffuf---complete-cheatsheet"></a>
# 🚀 ffuf — المرجع الشامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ أداة سريعة جدًا لتجربة مدخلات الويب، مكتوبة بلغة Go</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [الصياغة الأساسية](#-basic-syntax)
- [تجربة أسماء المجلدات](#-directory-fuzzing)
- [تجربة أسماء النطاقات الفرعية](#-subdomain-fuzzing)
- [تجربة المعاملات](#-parameter-fuzzing)
- [تجربة بيانات POST](#-post-data-fuzzing)
- [الاستبعاد والمطابقة](#-filtering--matching)
- [الميزات المتقدمة](#-advanced-features)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**ffuf**، اختصار Fuzz Faster U Fool، أداة سريعة مكتوبة بلغة Go لتجربة مدخلات الويب (Fuzzing). تُستخدم لتخمين أسماء المجلدات، واكتشاف المضيفين الافتراضيين، وتجربة المعاملات في البحث عن الثغرات ضمن برامج المكافآت.

<a id="key-features"></a>
### الميزات الأساسية

| الميزة | الوصف |
|---------|-------------|
| **السرعة** | سريعة جدًا ومبنية بلغة Go |
| **مرنة** | تجربة قيم متنوعة في أي جزء من طلب HTTP |
| **الاستبعاد** | خيارات متعددة للاستبعاد والمطابقة |
| **البحث التكراري** | دعم البحث التكراري داخل المجلدات |
| **النتائج** | صيغ إخراج متعددة |
| **سهلة** | صياغة بسيطة وواضحة |

<a id="ffuf-vs-other-tools"></a>
### ffuf مقارنة بأدوات أخرى

| الميزة | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **السرعة** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **المرونة** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **تجربة بيانات POST** | ✅ نعم | ❌ لا | ❌ لا |
| **البحث التكراري** | ✅ نعم | ✅ نعم | ✅ نعم |
| **المرشّحات** | كثيرة | محدودة | محدودة |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="using-go"></a>
### باستخدام Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

<a id="kali-linux"></a>
### Kali Linux
```bash
sudo apt install ffuf
```

<a id="binary-download"></a>
### تنزيل الملف التنفيذي
```bash
# التنزيل من صفحة الإصدارات
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
ffuf -V
ffuf -h
```

---

<a id="-basic-syntax"></a>
## ⌨️ الصياغة الأساسية

<a id="general-syntax"></a>
### الصياغة العامة
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

<a id="the-fuzz-keyword"></a>
### الكلمة النائبة FUZZ

تحدد الكلمة النائبة **FUZZ** الموضع الذي سيضع فيه ffuf الكلمات من قائمة الكلمات.

```bash
# داخل مسار URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# داخل النطاق الفرعي
ffuf -u https://FUZZ.target.com -w wordlist.txt

# في قيمة المعامل
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# داخل بيانات POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

<a id="essential-options"></a>
### الخيارات الأساسية

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL المستهدف مع الكلمة FUZZ |
| `-w` | مسار قائمة الكلمات |
| `-o` | ملف النتائج |
| `-of` | صيغة النتائج (json أو csv أو html) |
| `-t` | عدد خيوط التنفيذ (الافتراضي: 40) |
| `-p` | التأخير بين الطلبات |
| `-r` | اتباع عمليات إعادة التوجيه |
| `-v` | إخراج تفصيلي |
| `-s` | الوضع الصامت |

---

<a id="-directory-fuzzing"></a>
## 📁 تجربة أسماء المجلدات

<a id="basic-directory-brute-force"></a>
### تخمين أساسي لأسماء المجلدات

```bash
# تجربة أساسية لأسماء المجلدات
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# مع امتدادات الملفات
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# امتدادات متعددة
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

<a id="recursive-scanning"></a>
### البحث التكراري

```bash
# تفعيل البحث التكراري
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# عمق البحث التكراري
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# البحث التكراري مع استراتيجية محددة
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

<a id="filter-by-status-code"></a>
### التصفية حسب رمز الحالة

```bash
# مطابقة استجابات 200 فقط
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# مطابقة رموز متعددة
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# استبعاد استجابات 404، وفق وصف الأصل لتغيير السلوك الافتراضي
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# مطابقة الجميع ثم استبعاد 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

<a id="popular-wordlists"></a>
### قوائم كلمات شائعة

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="-subdomain-fuzzing"></a>
## 🌐 تجربة أسماء النطاقات الفرعية

<a id="virtual-host-discovery"></a>
### اكتشاف المضيفين الافتراضيين

```bash
# تجربة أساسية لأسماء النطاقات الفرعية
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# الاستبعاد حسب حجم الاستجابة لإزالة الصفحة الافتراضية
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# باستخدام قائمة كلمات محددة
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

<a id="dns-subdomain-fuzzing"></a>
### تجربة النطاقات الفرعية عبر DNS

```bash
# النطاق الفرعي داخل URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# باستخدام محلّل DNS وفق وصف الأصل
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

<a id="common-subdomain-wordlists"></a>
### قوائم شائعة للنطاقات الفرعية

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="-parameter-fuzzing"></a>
## 🔍 تجربة المعاملات

<a id="get-parameter-fuzzing"></a>
### تجربة معاملات GET

```bash
# تجربة أسماء المعاملات
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# تجربة قيم المعامل
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# معاملات متعددة
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

<a id="parameter-discovery"></a>
### اكتشاف المعاملات

```bash
# البحث عن معاملات مخفية
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="-post-data-fuzzing"></a>
## 📝 تجربة بيانات POST

<a id="basic-post-fuzzing"></a>
### تجربة أساسية لبيانات POST

```bash
# طلب POST ببيانات نموذج
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# طلب POST مع تحديد نوع المحتوى
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

<a id="json-post-fuzzing"></a>
### تجربة بيانات POST بصيغة JSON

```bash
# بيانات POST بصيغة JSON
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

<a id="multiple-wordlists-credential-stuffing"></a>
### قوائم كلمات متعددة (Credential Stuffing وفق عنوان الأصل)

```bash
# تجربة أسماء المستخدمين وكلمات المرور
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# الوضع clusterbomb (جميع التركيبات)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="-filtering--matching"></a>
## 🎯 الاستبعاد والمطابقة

<a id="filter-options"></a>
### خيارات الاستبعاد

| الخيار | الوصف | المثال |
|--------|-------------|---------|
| `-fc` | الاستبعاد حسب رمز الحالة | `-fc 404,403` |
| `-fs` | الاستبعاد حسب حجم الاستجابة | `-fs 1234` |
| `-fw` | الاستبعاد حسب عدد الكلمات | `-fw 100` |
| `-fl` | الاستبعاد حسب عدد الأسطر | `-fl 50` |
| `-ft` | الاستبعاد حسب الوقت بالميلي ثانية | `-ft 5000` |
| `-fr` | الاستبعاد بتعبير نمطي | `-fr "error"` |

<a id="match-options"></a>
### خيارات المطابقة

| الخيار | الوصف | المثال |
|--------|-------------|---------|
| `-mc` | مطابقة رموز الحالة | `-mc 200,301` |
| `-ms` | مطابقة حجم الاستجابة | `-ms 1234` |
| `-mw` | مطابقة عدد الكلمات | `-mw 100` |
| `-ml` | مطابقة عدد الأسطر | `-ml 50` |
| `-mt` | مطابقة الوقت بالميلي ثانية | `-mt 5000` |
| `-mr` | المطابقة بتعبير نمطي | `-mr "success"` |

<a id="filter-examples"></a>
### أمثلة الاستبعاد

```bash
# استبعاد 404 و403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# الاستبعاد حسب الحجم لإزالة الصفحة الافتراضية
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# الاستبعاد حسب عدد الكلمات
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# مطابقة الجميع ثم استبعاد المحدد
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

<a id="auto-calibration"></a>
### المعايرة الآلية

```bash
# معايرة مرشّحات الاستبعاد آليًا
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# المعايرة باستخدام كلمة محددة
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="-advanced-features"></a>
## ⚡ الميزات المتقدمة

<a id="rate-limiting"></a>
### تقييد معدل الطلبات

```bash
# عدد الطلبات في الثانية
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# التأخير بين الطلبات
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# تأخير عشوائي (Jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

<a id="threads"></a>
### خيوط التنفيذ

```bash
# تحديد خيوط التنفيذ (الافتراضي: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# تقليل الخيوط لتخفيف وضوح النشاط
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

<a id="headers"></a>
### الترويسات

```bash
# ترويسة مخصصة
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# ترويسات متعددة
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# ملف ارتباط
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

<a id="proxy"></a>
### الوكيل

```bash
# وكيل HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

<a id="output"></a>
### النتائج

```bash
# إخراج بصيغة JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# إخراج بصيغة CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# إخراج بصيغة HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# جميع الصيغ
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

<a id="timeout--retries"></a>
### المهل وإعادة المحاولة

```bash
# مهلة الطلب
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# التوقف عند 403 وفق وصف الأصل
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# تجاوز أخطاء التحقق من شهادة TLS
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-basic-directory-scan"></a>
### المثال 1: فحص أساسي للمجلدات
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

<a id="example-2-find-php-files"></a>
### المثال 2: البحث عن ملفات PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

<a id="example-3-subdomain-enumeration"></a>
### المثال 3: استطلاع النطاقات الفرعية
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

<a id="example-4-api-endpoint-discovery"></a>
### المثال 4: اكتشاف نقاط اتصال API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

<a id="example-5-parameter-discovery"></a>
### المثال 5: اكتشاف المعاملات
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

<a id="example-6-login-brute-force"></a>
### المثال 6: تخمين بيانات تسجيل الدخول
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

<a id="example-7-with-proxy-burp"></a>
### المثال 7: استخدام وكيل Burp
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

<a id="example-8-recursive-scan"></a>
### المثال 8: فحص تكراري
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

<a id="example-9-rate-limited-scan"></a>
### المثال 9: فحص بمعدل طلبات محدد
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

<a id="example-10-complete-bug-bounty-scan"></a>
### المثال 10: فحص متكامل ضمن برنامج مكافآت
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| المهمة | الأمر |
|------|---------|
| فحص المجلدات | `ffuf -u URL/FUZZ -w wordlist.txt` |
| مع امتدادات | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| فحص النطاقات الفرعية | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| تجربة بيانات POST | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| استبعاد 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| الاستبعاد حسب الحجم | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| المعايرة الآلية | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| حفظ النتائج | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL يتضمن FUZZ |
| `-w` | قائمة الكلمات |
| `-e` | الامتدادات |
| `-t` | خيوط التنفيذ |
| `-mc` | رموز المطابقة |
| `-fc` | رموز الاستبعاد |
| `-fs` | حجم الاستجابات المستبعدة |
| `-ac` | المعايرة الآلية |
| `-r` | اتباع عمليات إعادة التوجيه |
| `-o` | ملف النتائج |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="bug-bounty-tips"></a>
### نصائح لبرامج مكافآت الثغرات

1. **ابدأ دائمًا بالمعايرة الآلية وفق نصيحة الأصل**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **استخدم امتدادات متعددة**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **جرّب امتدادات النسخ الاحتياطية الشائعة**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **تقييد المعدل لمحاولة تجاوز WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

<a id="performance-tips"></a>
### نصائح الأداء

```bash
# سرعة مرتفعة عند غياب WAF
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# معدل منخفض وبطيء لتقليل وضوح النشاط
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تنبيه:** ffuf أداة قوية لتجربة المدخلات، ويجب استخدامها فقط في **اختبارات مأذون بها**.
> 
> - ✅ استخدمها في برامج لديك إذن باختبارها
> - ✅ استخدمها ضمن برامج مكافآت ثغرات مأذون بها
> - ✅ احترم حدود معدل الطلبات ونطاق الاختبار
> - ❌ لا تجرّب المدخلات دون إذن
> - ❌ لا تتسبب في حجب الخدمة
> 
> **الفحص غير المأذون به مخالف للقانون، وفق تنبيه الأصل.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### الموارد الرسمية
- [مستودع ffuf على GitHub](https://github.com/ffuf/ffuf)
- [ويكي ffuf](https://github.com/ffuf/ffuf/wiki)

<a id="wordlists"></a>
### قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [قوائم كلمات Assetnote](https://wordlists.assetnote.io/)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [Nuclei](../../Nuclei/translations/README.ar.md)
- [Gobuster](../../Gobuster/translations/README.ar.md)
- [Nmap](../../Nmap/translations/README.ar.md)

---

<p align="center">
  <b>🚀 جرّب المدخلات بسرعة واكتشف المزيد!</b><br>
  <i>أداة تجربة مدخلات يفضّلها باحثو مكافآت الثغرات</i>
</p>

> **مصطلحات:** Fuzzing هو تجربة قيم مرشحة في مواضع محددة من الطلب. تحدد FUZZ موضع الاستبدال، وقائمة الكلمات (Wordlist) مصدر القيم. يستبعد Filter النتائج المطابقة له، بينما يختار Matcher النتائج المقبولة. حُفظ الرسم الإنجليزي وشعاره؛ وتقييمات السرعة بالنجوم ووصف «الأسرع» من الأصل، وليست قياسات لهذه الترجمة.

> **تصحيح الخيارات:** `-r` يتبع إعادة التوجيه ولا يحدد محلّل DNS. و`-se` يوقف العمل عند أخطاء غير اعتيادية، وليس عند أول 403؛ أما `-sf` فيوقفه عندما تتجاوز نسبة 403 مقدار 95%. يتطلب `-ft` و`-mt` شرط مقارنة لزمن أول بايت، مثل `>5000`، فلا تُقرأ القيمة `5000` المجردة في الجدول على أنها الصياغة الموثقة. [توثيق ffuf الرسمي](https://github.com/ffuf/ffuf#usage).

> **توضيح للأمثلة:** تغيير ترويسة Host يختبر المضيفين الافتراضيين عبر HTTP، ويمكنه اكتشافها دون سجلات DNS. أما وضع الاسم داخل URL فيعتمد على إمكانية حله. حجم الاستجابة 1234 مثال يُضبط حسب الصفحة الافتراضية. كذلك فإن وضع `clusterbomb` يجرّب جميع التركيبات بين القوائم، لا أزواجًا متوازية فقط. [أمثلة المشروع وخيارات الأوضاع](https://github.com/ffuf/ffuf). اسم الإصدار `v2.x.x` في رابط التنزيل عنصر نائب، وليس إصدارًا محددًا.

> **تصحيح للمقارنة:** يذكر الجدول أن Gobuster لا يجرّب بيانات POST، لكن سجل تغييراته يوثق دعم تجربة جسم POST والترويسات وبيانات المصادقة منذ 3.3. أُبقي الجدول الأصلي مع هذا التصحيح. [سجل Gobuster الرسمي](https://github.com/OJ/gobuster#33).
