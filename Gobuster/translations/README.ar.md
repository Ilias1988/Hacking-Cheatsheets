<a id="-gobuster---complete-cheatsheet"></a>
# 🚀 Gobuster — المرجع الشامل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Directory/DNS/VHost Brute-Forcing Tool
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 أداة سريعة مكتوبة بلغة Go لاستطلاع المجلدات وDNS والمضيفين الافتراضيين (VHost)</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [التثبيت](#-installation)
- [نظرة عامة على الأوضاع](#-modes-overview)
- [وضع المجلدات (dir)](#-directory-mode-dir)
- [وضع DNS (dns)](#-dns-mode-dns)
- [وضع المضيفين الافتراضيين (vhost)](#-vhost-mode-vhost)
- [وضع تجربة المدخلات (fuzz)](#-fuzz-mode-fuzz)
- [قوائم الكلمات](#-wordlists)
- [المصادقة والترويسات](#-authentication--headers)
- [ضبط الأداء](#-performance-tuning)
- [أمثلة عملية](#-real-world-examples)
- [مرجع سريع](#-quick-reference)
- [نصائح وأفضل الممارسات](#-tips--best-practices)
- [الموارد](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**Gobuster** أداة سريعة متعددة خيوط التنفيذ، مكتوبة بلغة Go لتجربة القيم المرشحة على نحو مكثّف من أجل:
- استطلاع المجلدات والملفات على خوادم الويب
- اكتشاف النطاقات الفرعية عبر DNS
- اكتشاف المضيفين الافتراضيين (VHost)
- تجربة مدخلات متنوعة في عناوين URL

<a id="why-gobuster"></a>
### لماذا Gobuster؟

| الميزة | الوصف |
|---------|-------------|
| **سريعة** | مكتوبة بلغة Go وتدعم تزامنًا مرتفعًا |
| **مرنة** | أوضاع متعددة لمهام مختلفة |
| **دون بحث تكراري داخل المجلدات المكتشفة** | نتائج واضحة قليلة التشويش |
| **مستقرة** | تتعامل مع الأخطاء بصورة منظّمة |
| **تطوير مستمر** | تحديثات منتظمة |

<a id="comparison-with-other-tools"></a>
### مقارنة بأدوات أخرى

| الأداة | السرعة | البحث التكراري | اللغة |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | لا | Go |
| Dirb | ⭐⭐ | نعم | C |
| Dirbuster | ⭐⭐⭐ | نعم | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | لا | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | نعم | Rust |

---

<a id="-installation"></a>
## 📥 التثبيت

<a id="kali-linux-pre-installed"></a>
### Kali Linux (مثبتة مسبقًا وفق الأصل)
```bash
# تحديث Gobuster
sudo apt update && sudo apt install gobuster
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install gobuster
```

<a id="from-source-go"></a>
### من الشيفرة المصدرية باستخدام Go
```bash
go install github.com/OJ/gobuster/v3@latest
```

<a id="using-snap"></a>
### باستخدام Snap
```bash
sudo snap install gobuster
```

<a id="from-github-releases"></a>
### من إصدارات GitHub
```bash
# تنزيل أحدث إصدار
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

<a id="verify-installation"></a>
### التحقق من التثبيت
```bash
gobuster version
gobuster -h
```

---

<a id="-modes-overview"></a>
## 📊 نظرة عامة على الأوضاع

<a id="available-modes"></a>
### الأوضاع المتاحة

| Mode | الوصف | حالة الاستخدام |
|------|-------------|----------|
| `dir` | استطلاع المجلدات والملفات | البحث عن المجلدات والملفات غير الظاهرة |
| `dns` | استطلاع النطاقات الفرعية عبر DNS | البحث عن النطاقات الفرعية |
| `vhost` | استطلاع المضيفين الافتراضيين | البحث عن المضيفين الافتراضيين |
| `fuzz` | تجربة مدخلات متنوعة في عناوين URL | تجربة قيم متنوعة للمعاملات والمسارات |
| `s3` | استطلاع حاويات S3 | البحث عن حاويات AWS S3 |
| `gcs` | Google Cloud Storage | البحث عن حاويات GCS |
| `tftp` | استطلاع TFTP | البحث عن ملفات TFTP |

<a id="basic-syntax"></a>
### الصياغة الأساسية
```bash
gobuster [mode] [options]

# أمثلة
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="-directory-mode-dir"></a>
## 📁 وضع المجلدات (dir)

<a id="basic-syntax-1"></a>
### الصياغة الأساسية
```bash
gobuster dir -u <URL> -w <wordlist>
```

<a id="essential-options"></a>
### الخيارات الأساسية

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL المستهدف |
| `-w` | مسار قائمة الكلمات |
| `-t` | خيوط التنفيذ (الافتراضي: 10) |
| `-x` | امتدادات الملفات المطلوب البحث عنها |
| `-s` | رموز الحالة المقبولة |
| `-b` | رموز الحالة المستبعدة (قائمة الحظر) |
| `-o` | ملف النتائج |
| `-k` | تجاوز التحقق من شهادة TLS |
| `-r` | اتباع عمليات إعادة التوجيه |
| `-a` | قيمة ترويسة User-Agent |
| `-c` | ملفات الارتباط |
| `-H` | ترويسات مخصصة |

<a id="basic-directory-scan"></a>
### فحص أساسي للمجلدات
```bash
# فحص بسيط للمجلدات
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# باستخدام عدد أكبر من خيوط التنفيذ
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

<a id="file-extension-search"></a>
### البحث حسب امتدادات الملفات
```bash
# البحث عن امتدادات محددة
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# امتدادات متعددة
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# ملفات النسخ الاحتياطية
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

<a id="status-code-filtering"></a>
### التصفية حسب رمز الحالة
```bash
# عرض رموز حالة محددة فقط
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# استبعاد رموز حالة
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# عرض الجميع بما في ذلك 404 وفق وصف الأصل
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

<a id="output-options"></a>
### خيارات الإخراج
```bash
# الحفظ في ملف
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# إخراج تفصيلي
gobuster dir -u http://target.com -w wordlist.txt -v

# دون عرض التقدم وفق وصف الأصل
gobuster dir -u http://target.com -w wordlist.txt -q

# الوضع الموسّع (عناوين URL كاملة)
gobuster dir -u http://target.com -w wordlist.txt -e
```

<a id="ssltls-options"></a>
### خيارات SSL/TLS
```bash
# تجاوز التحقق من شهادة TLS
gobuster dir -u https://target.com -w wordlist.txt -k

# تجاوز التحقق من الشهادة باستخدام الخيارات المطوّلة
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

<a id="directory-mode-examples"></a>
### أمثلة وضع المجلدات
```bash
# فحص اعتيادي لمجلدات الويب
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# تطبيق PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# اكتشاف نقاط اتصال API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# البحث عن ملفات النسخ الاحتياطية
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="-dns-mode-dns"></a>
## 🌐 وضع DNS (dns)

<a id="basic-syntax-2"></a>
### الصياغة الأساسية
```bash
gobuster dns -d <domain> -w <wordlist>
```

<a id="dns-options"></a>
### خيارات DNS

| الخيار | الوصف |
|--------|-------------|
| `-d` | النطاق المستهدف |
| `-w` | مسار قائمة الكلمات |
| `-t` | خيوط التنفيذ |
| `-r` | محلّل DNS مخصص |
| `-i` | عرض عناوين IP |
| `-c` | عرض سجلات CNAME |
| `--wildcard` | فرض معالجة سجلات wildcard |
| `--timeout` | مهلة DNS |

<a id="basic-dns-enumeration"></a>
### استطلاع DNS الأساسي
```bash
# فحص بسيط للنطاقات الفرعية
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# عرض عناوين IP
gobuster dns -d target.com -w subdomains.txt -i

# عرض سجلات CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

<a id="custom-dns-resolver"></a>
### محلّل DNS مخصص
```bash
# استخدام خادم DNS محدد
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# محلّلات متعددة وفق وصف الأصل
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

<a id="dns-examples"></a>
### أمثلة DNS
```bash
# فحص شامل للنطاقات الفرعية
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# فحص سريع للنطاقات الفرعية
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# مع حفظ النتائج
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="-vhost-mode-vhost"></a>
## 🏠 وضع المضيفين الافتراضيين (vhost)

<a id="basic-syntax-3"></a>
### الصياغة الأساسية
```bash
gobuster vhost -u <URL> -w <wordlist>
```

<a id="vhost-options"></a>
### خيارات VHost

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL المستهدف |
| `-w` | مسار قائمة الكلمات |
| `-t` | خيوط التنفيذ |
| `--append-domain` | إلحاق النطاق بعناصر قائمة الكلمات |
| `--domain` | نطاق المضيف الافتراضي |
| `-r` | اتباع عمليات إعادة التوجيه |

<a id="basic-vhost-enumeration"></a>
### استطلاع VHost الأساسي
```bash
# فحص بسيط للمضيفين الافتراضيين
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# مع إلحاق النطاق
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

<a id="vhost-examples"></a>
### أمثلة VHost
```bash
# اكتشاف اعتيادي للمضيفين الافتراضيين
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# مضيفو بيئات التطوير
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# باستخدام نطاق مخصص
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="-fuzz-mode-fuzz"></a>
## 🔧 وضع تجربة المدخلات (fuzz)

<a id="basic-syntax-4"></a>
### الصياغة الأساسية
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

<a id="fuzz-options"></a>
### خيارات Fuzz

| الخيار | الوصف |
|--------|-------------|
| `-u` | عنوان URL يتضمن الكلمة FUZZ |
| `-w` | مسار قائمة الكلمات |
| `-t` | خيوط التنفيذ |
| `-b` | رموز الحالة المطلوب حظرها |
| `--exclude-length` | الاستبعاد حسب طول الاستجابة |

<a id="fuzz-examples"></a>
### أمثلة Fuzz
```bash
# تجربة قيم متنوعة للمعامل
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# تجربة مسارات متنوعة
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# استبعاد أطوال محددة
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="-wordlists"></a>
## 📚 قوائم الكلمات

<a id="seclists-locations-kali-linux"></a>
### مواضع SecLists على Kali Linux

```bash
# استطلاع المجلدات والملفات
/usr/share/seclists/Discovery/Web-Content/

# قوائم مجلدات شائعة
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS والنطاقات الفرعية
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

<a id="default-wordlists"></a>
### قوائم الكلمات الافتراضية

```bash
# قوائم كلمات Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# قوائم كلمات Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

<a id="wordlist-recommendations"></a>
### اقتراحات قوائم الكلمات

| حالة الاستخدام | قائمة الكلمات |
|----------|----------|
| فحص سريع | `common.txt` |
| اعتيادي | `directory-list-2.3-medium.txt` |
| شامل | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| النطاقات الفرعية | `subdomains-top1million-5000.txt` |
| الملفات | `raft-large-files.txt` |

---

<a id="-authentication--headers"></a>
## 🔐 المصادقة والترويسات

<a id="basic-authentication"></a>
### المصادقة الأساسية
```bash
# مصادقة HTTP الأساسية
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

<a id="cookie-authentication"></a>
### المصادقة بملفات الارتباط
```bash
# ملف ارتباط واحد
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# ملفات ارتباط متعددة
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# جلسة PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

<a id="custom-headers"></a>
### ترويسات مخصصة
```bash
# ترويسة واحدة
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# ترويسات متعددة
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# مفتاح API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

<a id="user-agent"></a>
### User-Agent
```bash
# قيمة User-Agent مخصصة
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

<a id="proxy-support"></a>
### دعم الوكيل (Proxy)
```bash
# وكيل HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# وكيل SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# وكيل Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="-performance-tuning"></a>
## ⚡ ضبط الأداء

<a id="thread-management"></a>
### إدارة خيوط التنفيذ
```bash
# الافتراضي: 10 خيوط تنفيذ
gobuster dir -u http://target.com -w wordlist.txt

# زيادة خيوط التنفيذ لشبكة سريعة
gobuster dir -u http://target.com -w wordlist.txt -t 100

# تقليل الخيوط عند البطء أو عدم الاستقرار
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

<a id="timeout-settings"></a>
### إعدادات المهلة
```bash
# زيادة المهلة
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# مهلة DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

<a id="delay-between-requests"></a>
### التأخير بين الطلبات
```bash
# إضافة تأخير للحد من معدل الطلبات
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# الإبطاء لمحاولة تفادي WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="-real-world-examples"></a>
## 🎬 أمثلة عملية

<a id="example-1-standard-web-enumeration"></a>
### المثال 1: استطلاع اعتيادي للويب
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

<a id="example-2-api-endpoint-discovery"></a>
### المثال 2: اكتشاف نقاط اتصال API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

<a id="example-3-authenticated-scan"></a>
### المثال 3: فحص مع مصادقة
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

<a id="example-4-subdomain-enumeration"></a>
### المثال 4: استطلاع النطاقات الفرعية
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

<a id="example-5-vhost-discovery-hackthebox"></a>
### المثال 5: اكتشاف VHost على HackTheBox
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

<a id="example-6-through-burp-proxy"></a>
### المثال 6: عبر وكيل Burp
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

<a id="example-7-backup-file-search"></a>
### المثال 7: البحث عن ملفات النسخ الاحتياطية
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

<a id="example-8-cms-detection"></a>
### المثال 8: اكتشاف نظام إدارة المحتوى (CMS)
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### الأوامر الأساسية

| المهمة | Command |
|------|---------|
| فحص أساسي للمجلدات | `gobuster dir -u URL -w wordlist.txt` |
| مع تحديد الامتدادات | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| فحص النطاقات الفرعية | `gobuster dns -d domain.com -w wordlist.txt` |
| فحص المضيفين الافتراضيين | `gobuster vhost -u URL -w wordlist.txt` |
| مع حفظ النتائج | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| تجاوز أخطاء التحقق من شهادة TLS | `gobuster dir -u URL -w wordlist.txt -k` |
| باستخدام ملفات الارتباط | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| عبر وكيل | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الاختصار | الوصف |
|--------|-------|-------------|
| `--url` | `-u` | عنوان URL المستهدف |
| `--wordlist` | `-w` | ملف قائمة الكلمات |
| `--threads` | `-t` | عدد خيوط التنفيذ |
| `--extensions` | `-x` | امتدادات الملفات |
| `--status-codes` | `-s` | رموز الحالة المقبولة |
| `--cookies` | `-c` | ملفات الارتباط المراد إرسالها |
| `--output` | `-o` | ملف النتائج |
| `--insecuressl` | `-k` | تجاوز التحقق من شهادة TLS |
| `--verbose` | `-v` | إخراج تفصيلي |
| `--quiet` | `-q` | الوضع الهادئ |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وأفضل الممارسات

<a id="efficiency-tips"></a>
### نصائح لزيادة الكفاءة

1. **ابدأ بقائمة صغيرة ثم وسّع الفحص**
   ```bash
   # فحص سريع أولًا
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # ثم استخدام قائمة كلمات أكبر
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **امتدادات تناسب تقنية الهدف**
   ```bash
   # موقع PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # موقع ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **التكامل مع أدوات أخرى**
   ```bash
   # مسار عمل Nmap ثم Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

<a id="avoiding-detection"></a>
### تقليل احتمال الاكتشاف

```bash
# فحص بطيء مع تأخير
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# قيمة User-Agent مخصصة
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# عبر Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تنبيه:** Gobuster أداة قوية للاستطلاع، ويجب استخدامها فقط في **اختبارات مأذون بها**.
> 
> - ✅ اختبر الأنظمة التي تملكها
> - ✅ اختبر بعد الحصول على إذن كتابي صريح
> - ✅ استخدم الأداة في تحديات CTF والمختبرات
> - ❌ لا تفحص أي نظام دون إذن
> - ❌ قد يكون الاستطلاع غير المأذون به مخالفًا للقانون
> 
> **احصل دائمًا على إذن قبل فحص أي نظام.**

---

<a id="-resources"></a>
## 📚 الموارد

<a id="official-resources"></a>
### الموارد الرسمية
- [مستودع Gobuster على GitHub](https://github.com/OJ/gobuster)
- [ويكي Gobuster](https://github.com/OJ/gobuster/wiki)

<a id="wordlists"></a>
### قوائم الكلمات
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

<a id="related-cheatsheets"></a>
### مراجع ذات صلة
- [Nmap](../../Nmap/translations/README.ar.md)
- [Burp Suite](../../Burp-Suite/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🚀 أتقن استطلاع الويب!</b><br>
  <i>اكتشف ما لا يراه الآخرون</i>
</p>

> **مصطلحات:** الاستطلاع (Enumeration) يجمع أسماء الموارد المحتملة من خلال تجربة عناصر قائمة كلمات (Wordlist). يشير VHost إلى مضيف افتراضي تشترك خدمته في خادم ويب مع مضيفين آخرين. تصنيفات السرعة بالنجوم في المقارنة من الأصل وليست نتائج قياس أُجري لهذه الترجمة. حُفظ الرسم النصي بعنوانه الإنجليزي؛ وهو يصف أداة لتخمين المجلدات وأسماء DNS والمضيفين الافتراضيين.

> **تصحيح للتصفية والإخراج:** الخيار `--no-status` يخفي رموز الحالة في الإخراج ولا يجعل نتائج 404 مقبولة. كما أن قائمة الحظر الافتراضية قد تتعارض مع `-s` ما لم تُفرّغ وفق الإصدار. [شيفرة وضع dir الرسمية](https://github.com/OJ/gobuster/blob/master/cli/dir/dir.go). الخيار `-q` يخفي اللافتة والمعلومات الزائدة؛ أما إخفاء التقدم فله `--no-progress`. والاسم المطوّل الموافق لـ `-k` في الشيفرة المشار إليها هو `--no-tls-validation`، وليس `--insecuressl` الوارد في جدول الأصل؛ الجمع بين `-k` والاسم المطوّل لا يضيف حماية أو سلوكًا مختلفًا. [تعريف الخيارات الرسمي](https://github.com/OJ/gobuster/blob/master/cli/options.go).

> **ملاحظة الإصدارات والأمثلة:** أزيل `verbose` واستُبدل بـ `debug` في سجل تغييرات Gobuster 3.7، وتغيّر اسم `show-cname` إلى `check-cname`. لذلك تُراجع خيارات النسخة المستخدمة قبل تطبيق أمثلة الأصل. كما يحتاج إلحاق النطاق بقائمة أسماء قصيرة في وضع VHost إلى مراعاة `--append-domain`. مثال «محلّلات متعددة» في الأصل يحدد خادمًا واحدًا مع منفذه: `8.8.8.8:53`. [توثيق Gobuster الرسمي وسجل التغييرات](https://github.com/OJ/gobuster).

> **تصحيح للمقارنة:** يذكر الجدول أن Ffuf لا يدعم البحث التكراري؛ لكن ffuf يدعم `-recursion` و`-recursion-depth` وفق [توثيق مشروع ffuf](https://github.com/ffuf/ffuf). أُبقيت قيمة الجدول الأصلية مع هذا التصحيح.
