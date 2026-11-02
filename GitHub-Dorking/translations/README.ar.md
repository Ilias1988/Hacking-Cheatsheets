<a id="-github-dorking---complete-cheatsheet"></a>
# 🐙 ورقة مرجعية شاملة للبحث المتقدم في GitHub

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗ ██╗████████╗██╗  ██╗██╗   ██╗██████╗ 
  ██╔════╝ ██║╚══██╔══╝██║  ██║██║   ██║██╔══██╗
  ██║  ███╗██║   ██║   ███████║██║   ██║██████╔╝
  ██║   ██║██║   ██║   ██╔══██║██║   ██║██╔══██╗
  ╚██████╔╝██║   ██║   ██║  ██║╚██████╔╝██████╔╝
   ╚═════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ 
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/GitHub-Dorking-red?style=for-the-badge" alt="البحث المتقدم في GitHub">
  <img src="https://img.shields.io/badge/Secret-Hunting-orange?style=for-the-badge" alt="البحث عن الأسرار">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="مكافآت الثغرات">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
</p>

<p align="center">
  <b>🔐 اعثر على الأسرار ومفاتيح API وبيانات الاعتماد المكشوفة في مستودعات GitHub</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

يشير الرسم أعلاه إلى البحث المتقدم في GitHub، وقد حُفظ بتنسيقه الأصلي. تُسمّى استعلامات البحث المتخصصة Dorks؛ أما الأسرار فهي بيانات حساسة مثل مفاتيح المصادقة، والرموز (Tokens) قيم تُستخدم للوصول إلى الخدمات.

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [مقدمة](#-introduction)
- [صياغة البحث في GitHub](#-github-search-syntax)
- [مفاتيح API والرموز (Tokens)](#-api-keys--tokens)
- [بيانات اعتماد قواعد البيانات](#-database-credentials)
- [المفاتيح الخاصة](#-private-keys)
- [ملفات الإعداد](#-configuration-files)
- [أسرار الخدمات السحابية](#-cloud-secrets)
- [كلمات المرور وبيانات الاعتماد](#-passwords--credentials)
- [أهداف برامج مكافآت الثغرات](#-bug-bounty-targets)
- [أساليب متقدمة](#-advanced-techniques)
- [أدوات الأتمتة](#-automation-tools)
- [مرجع سريع](#-quick-reference)
- [المراجع](#-resources)

---

<a id="-introduction"></a>
## 🎯 مقدمة

**البحث المتقدم في GitHub (GitHub Dorking)** هو استخدام إمكانات البحث المتقدم في GitHub للعثور على أسرار وبيانات اعتماد ومعلومات حساسة مكشوفة في المستودعات العامة.

<a id="what-can-you-find"></a>
### ما الذي يمكنك العثور عليه؟

| الفئة | أمثلة |
|----------|----------|
| **مفاتيح API** | AWS, Google, Stripe, Twilio |
| **الرموز (Tokens)** | OAuth وJWT ورموز الوصول الشخصية |
| **بيانات الاعتماد** | كلمات مرور قواعد البيانات، SMTP، SSH |
| **المفاتيح الخاصة** | RSA وSSH وملفات PEM |
| **ملفات الإعداد** | .env, config.yml, settings.py |

<a id="why-this-matters"></a>
### أهمية هذا الموضوع

- قد يضيف المطوّرون أسرارًا إلى سجل المستودع دون قصد
- يحتفظ سجل Git بالأسرار التي حُذفت من الملفات
- تُفهرس المستودعات العامة وتتاح للبحث
- مصدر مهم لاكتشافات برامج مكافآت الثغرات

---

<a id="-github-search-syntax"></a>
## 🔧 صياغة البحث في GitHub

<a id="basic-search-url"></a>
### عنوان URL الأساسي للبحث

```
https://github.com/search?q=YOUR_QUERY&type=code
```

<a id="search-qualifiers"></a>
### محدِّدات البحث (Qualifiers)

| المحدِّد | الوصف | مثال |
|-----------|-------------|---------|
| `in:file` | البحث في محتوى الملف | `password in:file` |
| `in:path` | البحث في مسار الملف | `config in:path` |
| `filename:` | البحث باسم الملف | `filename:.env` |
| `extension:` | البحث بامتداد الملف | `extension:json` |
| `language:` | البحث بلغة البرمجة | `language:python` |
| `user:` | البحث في مستودعات مستخدم | `user:target` |
| `org:` | البحث في مستودعات مؤسسة | `org:company` |
| `repo:` | البحث في مستودع محدّد | `repo:user/repo` |
| `path:` | البحث ضمن مسار | `path:config/` |
| `size:` | حجم الملف | `size:>1000` |

<a id="boolean-operators"></a>
### المعاملات المنطقية

```
# AND (مسافة)
password database

# OR
password OR secret

# NOT (-)
password -test -example

# عبارة مطابقة تمامًا
"api_key"

# دمج الاستعلامات
org:target "api_key" NOT test
```

---

<a id="-api-keys--tokens"></a>
## 🔑 مفاتيح API والرموز (Tokens)

<a id="aws-keys"></a>
### مفاتيح AWS

```
# معرّف مفتاح الوصول إلى AWS
AKIA
AKID
"aws_access_key_id"
"AWS_ACCESS_KEY_ID"

# المفتاح السري في AWS
"aws_secret_access_key"
"AWS_SECRET_ACCESS_KEY"

# استعلامات مركّبة
filename:.env AWS_ACCESS_KEY
org:target AKIA
org:target "aws_secret"
```

<a id="google-cloud"></a>
### سحابة Google

```
# مفتاح API في GCP
"AIza"
"AIzaSy"

# حساب خدمة في GCP
"type": "service_account"
filename:credentials.json "private_key"
"client_secret" extension:json

# Firebase
"apiKey" "authDomain" "databaseURL"
filename:firebase
```

<a id="stripe"></a>
### Stripe

```
# مفاتيح Stripe
"sk_live_"
"rk_live_"
"pk_live_"
sk_live
rk_live

# استعلامات مركّبة
org:target sk_live
filename:.env STRIPE_SECRET
```

<a id="twilio"></a>
### Twilio

```
# معرّف SID ورمز Twilio
"TWILIO_ACCOUNT_SID"
"TWILIO_AUTH_TOKEN"
"twilio" "sid" "token"
```

<a id="github-tokens"></a>
### رموز GitHub

```
# رموز الوصول الشخصية
ghp_
gho_
ghu_
ghs_
ghr_

# أنماط رموز GitHub
"GITHUB_TOKEN"
"github_token"
"gh_token"
filename:.env GITHUB_TOKEN
```

<a id="slack"></a>
### Slack

```
# رموز Slack
"xoxb-"
"xoxp-"
"xoxs-"
"xoxa-"

# خطّاف ويب (Webhook) في Slack
"hooks.slack.com"
"T[A-Z0-9]{8}/B[A-Z0-9]{8}/[a-zA-Z0-9]{24}"
```

<a id="other-api-keys"></a>
### مفاتيح API أخرى

```
# SendGrid
"SG."
"SENDGRID_API_KEY"

# Mailgun
"key-"
"MAILGUN_API_KEY"

# Mailchimp
"[0-9a-f]{32}-us[0-9]{2}"

# Square
"sq0atp-"
"sq0csp-"

# PayPal
"PAYPAL"

# Heroku
"HEROKU_API_KEY"
"[h|H]eroku.*[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"
```

---

<a id="-database-credentials"></a>
## 💾 بيانات اعتماد قواعد البيانات

<a id="mysql"></a>
### MySQL

```
# سلاسل الاتصال بـ MySQL
"mysql://"
"mysql_password"
"MYSQL_PASSWORD"
"DB_PASSWORD"

# استعلامات مركّبة
filename:.env MYSQL_PASSWORD
filename:config.php "mysql" "password"
"mysqli_connect" password
```

<a id="postgresql"></a>
### PostgreSQL

```
# PostgreSQL
"postgres://"
"postgresql://"
"POSTGRES_PASSWORD"
"PG_PASSWORD"
filename:.env POSTGRES
```

<a id="mongodb"></a>
### MongoDB

```
# الاتصال بـ MongoDB
"mongodb://"
"mongodb+srv://"
"MONGO_URI"
"MONGODB_URI"

# مع بيانات اعتماد
"mongodb://.*:.*@"
filename:.env MONGO
```

<a id="redis"></a>
### Redis

```
# Redis
"redis://"
"REDIS_PASSWORD"
"REDIS_URL"
```

<a id="sqlite"></a>
### SQLite

```
# SQLite
filename:*.sqlite
filename:*.db
extension:sqlite
```

<a id="generic-database"></a>
### قواعد البيانات عمومًا

```
# سلاسل الاتصال
"connection_string"
"connectionString"
"DB_CONNECTION"
"DATABASE_URL"

# بيانات الاعتماد
"db_password"
"db_pass"
"database_password"
```

---

<a id="-private-keys"></a>
## 🔐 المفاتيح الخاصة

<a id="ssh-keys"></a>
### مفاتيح SSH

```
# مفاتيح SSH الخاصة
"-----BEGIN RSA PRIVATE KEY-----"
"-----BEGIN DSA PRIVATE KEY-----"
"-----BEGIN EC PRIVATE KEY-----"
"-----BEGIN OPENSSH PRIVATE KEY-----"
"-----BEGIN PRIVATE KEY-----"

# الملفات
filename:id_rsa
filename:id_dsa
filename:id_ed25519
extension:pem
extension:key

# استعلامات مركّبة
filename:id_rsa -example -sample
```

<a id="pgpgpg-keys"></a>
### مفاتيح PGP/GPG

```
"-----BEGIN PGP PRIVATE KEY BLOCK-----"
filename:*.asc "PRIVATE KEY"
```

<a id="ssltls-certificates"></a>
### شهادات SSL/TLS

```
"-----BEGIN CERTIFICATE-----"
filename:*.crt
filename:*.cer
extension:pem "CERTIFICATE"
```

---

<a id="-configuration-files"></a>
## ⚙️ ملفات الإعداد

<a id="environment-files"></a>
### ملفات البيئة

```
# ملفات .env
filename:.env
filename:.env.local
filename:.env.production
filename:.env.development
filename:.env.staging
filename:.env.backup

# مع أسرار
filename:.env DB_PASSWORD
filename:.env API_KEY
filename:.env SECRET
```

<a id="config-files"></a>
### ملفات الإعداد

```
# ملفات إعداد متنوعة
filename:config.json
filename:config.yml
filename:config.yaml
filename:settings.py
filename:settings.json
filename:application.properties
filename:application.yml
filename:secrets.yml
filename:credentials.json
filename:credentials.xml
```

<a id="docker"></a>
### Docker

```
# إعدادات Docker
filename:docker-compose.yml password
filename:Dockerfile ENV
filename:.dockercfg
filename:docker-compose.yml
```

<a id="kubernetes"></a>
### Kubernetes

```
# أسرار Kubernetes
filename:*.yaml "kind: Secret"
filename:secrets.yaml
"kubernetes" "secret"
```

<a id="cicd"></a>
### CI/CD

```
# Jenkins
filename:Jenkinsfile password
filename:jenkins.xml

# Travis CI
filename:.travis.yml

# GitHub Actions
filename:*.yml "secrets"
path:.github/workflows

# GitLab CI
filename:.gitlab-ci.yml
```

---

<a id="-cloud-secrets"></a>
## ☁️ أسرار الخدمات السحابية

<a id="aws"></a>
### AWS

```
# ملف بيانات اعتماد AWS
filename:credentials aws_access_key_id
filename:.aws/credentials

# إعدادات AWS
"aws_access_key_id"
"aws_secret_access_key"
"aws_session_token"

# حاويات S3
"s3.amazonaws.com"
"s3-"
".s3.amazonaws.com"
```

<a id="azure"></a>
### Azure

```
# Azure
"AZURE_CLIENT_SECRET"
"AZURE_STORAGE_CONNECTION_STRING"
"AccountKey="
"SharedAccessSignature="

# Azure DevOps
"azure" "secret"
"azure" "token"
```

<a id="google-cloud-1"></a>
### سحابة Google

```
# GCP
"GOOGLE_APPLICATION_CREDENTIALS"
"GOOGLE_API_KEY"
"GOOGLE_CLOUD_PROJECT"
filename:credentials.json "private_key"
```

<a id="digitalocean"></a>
### DigitalOcean

```
"DIGITALOCEAN_ACCESS_TOKEN"
"DO_AUTH_TOKEN"
"digitalocean" "token"
```

<a id="heroku"></a>
### Heroku

```
"HEROKU_API_KEY"
"HEROKU_APP_NAME"
filename:.netrc heroku
```

---

<a id="-passwords--credentials"></a>
## 🔓 كلمات المرور وبيانات الاعتماد

<a id="hardcoded-passwords"></a>
### كلمات المرور المكتوبة مباشرة في الشيفرة

```
# متغيرات كلمات المرور
password=
passwd=
pass=
pwd=
"password:"
"password ="

# مع قيم
password="
password='
"password": "
'password': '
```

<a id="smtp-credentials"></a>
### بيانات اعتماد SMTP

```
# SMTP
"smtp_password"
"SMTP_PASSWORD"
"mail_password"
"EMAIL_PASSWORD"
"smtp" "password"
```

<a id="oauth-secrets"></a>
### أسرار OAuth

```
# OAuth
"client_secret"
"CLIENT_SECRET"
"oauth_token"
"OAUTH_SECRET"
"refresh_token"
```

<a id="jwt-secrets"></a>
### أسرار JWT

```
# JWT
"JWT_SECRET"
"jwt_secret"
"JWT_KEY"
"secret_key"
"SECRET_KEY"
```

<a id="admin-credentials"></a>
### بيانات اعتماد المسؤول

```
# المسؤول
"admin_password"
"root_password"
"master_password"
"default_password"
```

---

<a id="-bug-bounty-targets"></a>
## 🎯 أهداف برامج مكافآت الثغرات

<a id="target-organization"></a>
### المؤسسة المستهدفة

```
# البحث في مؤسسة محدّدة
org:target-company

# مستودعات المستخدم
user:target-username

# مستودع محدّد
repo:target/repository

# دمج البحث مع الأسرار
org:target-company password
org:target-company api_key
org:target-company "secret"
```

<a id="domain-based-search"></a>
### البحث بناءً على اسم النطاق

```
# النطاق المستهدف
"target.com"
"@target.com"
"api.target.com"
"internal.target.com"

# استعلامات مركّبة
"target.com" password
"target.com" api_key
org:target "@target.com"
```

<a id="employee-emails"></a>
### عناوين البريد الإلكتروني للموظفين

```
# العثور على مستودعات الموظفين
"@target.com"

# عناوين البريد الإلكتروني للمطوّرين
"@target.com" password
"@target.com" config
```

<a id="internal-urls"></a>
### عناوين URL الداخلية

```
# خدمات داخلية
"internal.target.com"
"dev.target.com"
"staging.target.com"
"admin.target.com"
"vpn.target.com"
"jira.target.com"
"confluence.target.com"
```

<a id="bug-bounty-dork-template"></a>
### قالب بحث لبرامج مكافآت الثغرات

```
# استطلاع شامل
org:TARGET filename:.env
org:TARGET filename:config.json password
org:TARGET extension:sql
org:TARGET "api_key"
org:TARGET "secret_key"
org:TARGET "password"
org:TARGET "token"
org:TARGET "AWS_ACCESS"
org:TARGET internal
"@TARGET.com" password
"TARGET.com" api_key
```

---

<a id="-advanced-techniques"></a>
## 🔬 أساليب متقدمة

<a id="git-history-search"></a>
### البحث في سجل Git

```
# افحص الإيداعات (Commits)
# (استخدم أدوات مثل TruffleHog وgit-secrets)

# إيداعات دُفعت حديثًا
pushed:>2024-01-01 password
pushed:>2024-01-01 filename:.env
```

<a id="gist-search"></a>
### البحث في Gists

```
# البحث في Gists
https://gist.github.com/search?q=password+target

# أنماط Gist
gist password
gist api_key
```

<a id="code-snippets"></a>
### مقتطفات الشيفرة

```
# البحث في الشيفرة
in:file password
in:path config

# ملفات كبيرة (قد تحتوي على بيانات أكثر)
size:>10000 password
```

<a id="regex-patterns-with-tools"></a>
### أنماط التعابير النمطية (باستخدام الأدوات)

```
# نمط مفتاح AWS
AKIA[0-9A-Z]{16}

# رمز GitHub
ghp_[a-zA-Z0-9]{36}

# رمز Slack
xox[baprs]-[0-9]{12}-[0-9]{12}-[a-zA-Z0-9]{24}

# مفتاح خاص
-----BEGIN [A-Z]+ PRIVATE KEY-----
```

---

<a id="-automation-tools"></a>
## 🛠️ أدوات الأتمتة

<a id="trufflehog"></a>
### TruffleHog

```bash
# التثبيت
pip install truffleHog

# فحص المستودع
trufflehog git https://github.com/target/repo

# فحص المؤسسة
trufflehog git https://github.com/target --only-verified
```

<a id="gitleaks"></a>
### GitLeaks

```bash
# التثبيت
brew install gitleaks

# فحص المستودع
gitleaks detect -s /path/to/repo

# الفحص من عنوان URL
gitleaks detect --source https://github.com/target/repo

# إخراج بصيغة JSON
gitleaks detect -s repo --report-format json -r results.json
```

<a id="git-secrets"></a>
### git-secrets

```bash
# التثبيت
brew install git-secrets

# إضافة أنماط
git secrets --add 'AKIA[A-Z0-9]{16}'
git secrets --add 'password\s*=\s*["\'][^"\']*["\']'

# فحص المستودع
git secrets --scan
```

<a id="github-search-tools"></a>
### أدوات البحث في GitHub

```bash
# واجهة سطر أوامر للبحث المتقدم في GitHub
# تتوفر أدوات مجتمعية متنوعة

# GitDorker
python3 gitdorker.py -t TOKEN -org target

# gitrob
gitrob target-org
```

<a id="shhgit"></a>
### shhgit

```bash
# مراقبة GitHub في الوقت الفعلي
# تراقب ظهور الأسرار في الوقت الفعلي
shhgit --search-query "org:target"
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-dorks"></a>
### استعلامات بحث أساسية

| الغرض | استعلام البحث (Dork) |
|---------|------|
| مفاتيح AWS | `org:target AKIA` |
| كلمات المرور | `org:target password filename:.env` |
| مفاتيح API | `org:target api_key` |
| المفاتيح الخاصة | `org:target "BEGIN RSA PRIVATE KEY"` |
| ملفات الإعداد | `org:target filename:config.json` |
| بيانات اعتماد قواعد البيانات | `org:target "mongodb://"` |
| الرموز (Tokens) | `org:target token secret` |
| عناوين البريد الإلكتروني | `"@target.com" password` |

<a id="file-patterns"></a>
### أنماط الملفات

| النمط | استعلام البحث (Dork) |
|---------|------|
| `.env` | `filename:.env` |
| إعدادات JSON | `filename:config.json` |
| الإعدادات | `filename:settings.py` |
| بيانات الاعتماد | `filename:credentials` |
| الأسرار | `filename:secrets` |
| Docker | `filename:docker-compose.yml` |

<a id="top-15-github-dorks"></a>
### أبرز 15 استعلام بحث في GitHub

| # | استعلام البحث (Dork) | الغرض |
|---|------|---------|
| 1 | `filename:.env DB_PASSWORD` | كلمات مرور قواعد البيانات |
| 2 | `filename:.env AWS_` | بيانات اعتماد AWS |
| 3 | `"-----BEGIN RSA PRIVATE KEY-----"` | مفاتيح خاصة |
| 4 | `filename:config.json password` | كلمات مرور الإعدادات |
| 5 | `AKIA` | مفاتيح الوصول إلى AWS |
| 6 | `sk_live` | مفاتيح Stripe |
| 7 | `"mongodb+srv://"` | عناوين URI الخاصة بـ MongoDB |
| 8 | `filename:credentials aws` | ملف بيانات اعتماد AWS |
| 9 | `ghp_` | رموز GitHub |
| 10 | `xoxb-` | رموز Slack |
| 11 | `org:target password` | كلمات مرور المؤسسة |
| 12 | `filename:.npmrc _auth` | رموز NPM |
| 13 | `extension:sql password` | نسخ SQL المُصدَّرة |
| 14 | `filename:wp-config.php` | WordPress |
| 15 | `"api_key" extension:json` | مفاتيح API |

---

<a id="-tips--best-practices"></a>
## 💡 نصائح وممارسات موصى بها

<a id="bug-bounty-tips"></a>
### نصائح لبرامج مكافآت الثغرات

1. **ابحث في المؤسسة**
   ```
   org:target-company
   ```

2. **افحص مستودعات الموظفين**
   ```
   "@target.com"
   ```

3. **ابحث عن عناوين URL الداخلية**
   ```
   org:target "internal" OR "dev" OR "staging"
   ```

4. **ابحث في سجل Git**
   - استخدم TruffleHog أو GitLeaks

5. **افحص Gists**
   - كثيرًا ما يخزّن المطوّرون مقتطفات شيفرة هناك

<a id="responsible-disclosure"></a>
### الإفصاح المسؤول

- **أبلغ** فريق الأمن بالنتائج
- **لا** تدخل إلى الأنظمة باستخدام بيانات الاعتماد التي عثرت عليها
- **لا** تنشر بيانات الاعتماد علنًا
- **وثّق** نتائجك بصورة مناسبة

---

<a id="-legal-disclaimer"></a>
## ⚠️ إخلاء مسؤولية قانوني

> **تحذير:** يكشف البحث المتقدم في GitHub معلومات متاحة للعامة. ومع ذلك:
> 
> - ✅ البحث في المستودعات العامة
> - ✅ إبلاغ برامج مكافآت الثغرات
> - ✅ الإفصاح المسؤول
> - ❌ لا تستخدم مطلقًا بيانات الاعتماد التي عثرت عليها
> - ❌ لا تدخل إلى الأنظمة
> - ❌ لا تخزّن الأسرار أو تشاركها
> 
> **استخدام بيانات الاعتماد التي عثرت عليها غير قانوني!**

---

<a id="-resources"></a>
## 📚 المراجع

<a id="tools"></a>
### الأدوات
- [TruffleHog](https://github.com/trufflesecurity/trufflehog)
- [GitLeaks](https://github.com/gitleaks/gitleaks)
- [git-secrets](https://github.com/awslabs/git-secrets)
- [shhgit](https://github.com/eth0izzle/shhgit)

<a id="references"></a>
### المصادر
- [وثائق البحث في GitHub](https://docs.github.com/en/search-github)
- [أنماط فحص الأسرار](https://docs.github.com/en/code-security/secret-scanning)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [البحث المتقدم في Google](../../Google-Dorking/translations/README.ar.md)
- [Shodan](../../Shodan/translations/README.ar.md)
- [Subfinder](../../Subfinder/translations/README.ar.md)

---



> **ملاحظات التوافق:** تجمع الأمثلة الأصلية بين صيغ بحث قديمة وحديثة. يستخدم GitHub Code Search الحالي محدِّدات مثل `content:` و`path:`، والمعامل `NOT`، والتعابير النمطية المحاطة بشرطتين مائلتين؛ لذلك لا تُفترض صلاحية أمثلة `filename:` و`extension:` و`in:file` و`size:` أو الاستبعاد بالشرطة في كل واجهة بحث. النص بين علامتي اقتباس هو بحث حرفي، لا Regex تلقائيًا؛ كما أن صيغ الاقتباس غير المغلقة أمثلة مقتطعة تحتاج إلى مراعاة صياغة البحث. راجع [الصياغة الرسمية](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax).
>
> البحث عن الشيفرة يتطلب تسجيل الدخول، ويقتصر حاليًا على الفرع الافتراضي ولا يفهرس كل الملفات؛ لا يبحث تلقائيًا في الإيداعات القديمة أو الأسرار المحذوفة. و`pushed:` ليس بديلًا عن فحص محتوى سجل Git. قد تتسع الاستعلامات المختلطة مع `OR` خارج النطاق المقصود إذا لم تُضبط الأقواس. راجع [حدود Code Search](https://docs.github.com/en/search-github/github-code-search/about-github-code-search) و[المعاملات المنطقية](https://docs.github.com/en/search-github/github-code-search/understanding-github-code-search-syntax#using-boolean-operations).
>
> أُبقيت أوامر الأدوات كما وردت، لكنها تعتمد على الإصدار: يميّز TruffleHog الحالي بين مصدر `git` لمستودع ومصدر `github` مع `--org` لمؤسسة؛ ولا يُعدّ رابط مؤسسة مستودع Git. كما أن أمثلة التثبيت القديمة عبر pip ليست دليل تثبيت للإصدار الحالي. في Gitleaks أُهمل `detect` منذ v8.19.0، وتستهدف أوضاع `git` و`dir` مسارات محلية، وليس رابط مستودع بدل المسار. قد تجري أدوات التحقق اتصالات للتحقق من صلاحية الأسرار؛ فعبارة `--only-verified` لا تعني فحصًا محليًا فقط. راجع [توثيق TruffleHog](https://github.com/trufflesecurity/trufflehog/blob/main/README.md) و[توثيق Gitleaks](https://github.com/gitleaks/gitleaks/blob/master/README.md).
>
> ليست كل نتيجة سرًّا صالحًا أو تسريبًا: قد تتطابق أمثلة واختبارات وأنماط جزئية، والأنماط المدرجة ليست حصرًا لجميع صيغ الرموز. مفاتيح Stripe ذات البادئة `pk_live_` قابلة للنشر، بخلاف المفاتيح السرية؛ راجع [أنواع مفاتيح Stripe](https://docs.stripe.com/keys). حُفظت جميع بادئات المصدر وعباراته حرفيًا، بما فيها `AKID` وأمثلة الشهادات العامة.

<p align="center">
  <b>🐙 اعثر على الأسرار قبل المهاجمين!</b><br>
  <i>أتقن البحث المتقدم في GitHub لبرامج مكافآت الثغرات</i>
</p>
