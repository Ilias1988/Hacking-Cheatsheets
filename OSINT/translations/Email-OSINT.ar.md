<a id="-email-osint-cheatsheet"></a>
# 📧 مرجع استخبارات المصادر المفتوحة للبريد الإلكتروني

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

<a id="-email-discovery-tools"></a>
## 🔍 أدوات اكتشاف عناوين البريد الإلكتروني

| الأداة | URL | الغرض |
|------|-----|---------|
| **Hunter.io** | hunter.io | العثور على عناوين البريد الإلكتروني للشركات |
| **Phonebook.cz** | phonebook.cz | بحث مجاني عن عناوين البريد الإلكتروني |
| **Snov.io** | snov.io | اكتشاف عناوين البريد الإلكتروني |
| **Clearbit Connect** | clearbit.com | إثراء معلومات البريد الإلكتروني |
| **RocketReach** | rocketreach.co | البحث عن بيانات التواصل |
| **Voila Norbert** | voilanorbert.com | الاستعلام عن البريد الإلكتروني |

---

<a id="-email-enumeration-commands"></a>
## 🛠️ أوامر استطلاع البريد الإلكتروني

<a id="theharvester"></a>
### theHarvester
```bash
# جمع عناوين البريد الإلكتروني
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

<a id="hunterio-cli"></a>
### Hunter.io CLI
```bash
# استعلام API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# التحقق من البريد الإلكتروني
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

<a id="h8mail-breach-search"></a>
### h8mail (البحث في تسريبات البيانات)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

<a id="holehe-registration-check"></a>
### holehe (فحص التسجيل في الخدمات)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

<a id="-email-format-patterns"></a>
## 📧 أنماط صيغ البريد الإلكتروني

```bash
# صيغ شائعة للبريد المؤسسي
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

<a id="email-permutator"></a>
### توليد الصيغ المحتملة للبريد الإلكتروني
```python
# توليد صيغ متنوعة للبريد الإلكتروني
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

<a id="-breach-checking"></a>
## 🔐 فحص تسريبات البيانات

| الخدمة | URL | النوع |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | مجاني |
| **DeHashed** | dehashed.com | مدفوع |
| **LeakCheck** | leakcheck.io | مدفوع |
| **Snusbase** | snusbase.com | مدفوع |
| **IntelX** | intelx.io | مجاني مع ميزات مدفوعة |

<a id="hibp-api"></a>
### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

<a id="-email-verification"></a>
## ✅ التحقق من البريد الإلكتروني

<a id="verification-methods"></a>
### طرق التحقق
```
1. فحص سجل MX: وجود خادم بريد للنطاق
2. التحقق عبر SMTP: فحص وجود صندوق البريد
3. فحص البنية: التأكد من صحة صيغة العنوان
4. فحص العناوين المؤقتة: التأكد من أنه ليس بريدًا مؤقتًا
5. فحص عناوين الأدوار: التأكد من أنه ليس عنوانًا عامًا (info@ وadmin@)
```

<a id="simple-smtp-check"></a>
### فحص SMTP بسيط
```bash
# فحص سجلات MX
nslookup -type=mx target.com
dig mx target.com

# التحقق باستخدام Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = موجود، و550 = غير موجود وفق تبسيط الأصل
```

---

<a id="-email-osint-checklist"></a>
## 📋 قائمة استخبارات البريد الإلكتروني

```markdown
## البريد الإلكتروني المستهدف: [email@domain.com]

□ التحقق من وجود عنوان البريد (SMTP/Hunter)
□ فحص تسريبات البيانات (HIBP)
□ البحث عن الحسابات المرتبطة (holehe)
□ البحث في Google/Bing
□ فحص التسجيل في منصات التواصل الاجتماعي
□ البحث عن ملفات تعريف عامة
□ البحث في مواقع مشاركة النصوص
□ فحص الشبكات المهنية
```

---

**العودة إلى OSINT:** [🔍 نظرة عامة على OSINT](README.ar.md)

> **تصحيح لتبسيط SMTP:** لا تعني الاستجابة `250` وحدها إثبات وجود صندوق بريد لشخص محدد؛ معناها نجاح الإجراء المطلوب. وقد تعني `550` عدم وجود الصندوق أو رفض الوصول أو الرفض لأسباب تتعلق بالسياسة. لذلك لا يُعد اختبار RCPT وحده إثباتًا قاطعًا. انظر [تعريف رموز الرد في RFC 5321](https://datatracker.ietf.org/doc/html/rfc5321#section-4.2.2). كذلك فإن وجود سجل MX يخص نطاق البريد ولا يثبت وجود عنوان بعينه.
