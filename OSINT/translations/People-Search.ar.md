<a id="-people-search-osint"></a>
# 👤 استخبارات المصادر المفتوحة للبحث عن الأشخاص

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

<a id="-people-search-engines"></a>
## 🔍 محركات البحث عن الأشخاص

<a id="free-services"></a>
### خدمات مجانية
| الأداة | URL | التغطية |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | الولايات المتحدة |
| **FastPeopleSearch** | fastpeoplesearch.com | الولايات المتحدة |
| **TruePeopleSearch** | truepeoplesearch.com | الولايات المتحدة |
| **Webmii** | webmii.com | عالمي |
| **PeekYou** | peekyou.com | الولايات المتحدة |
| **Pipl** | pipl.com | عالمي |

<a id="paid-services"></a>
### خدمات مدفوعة
| الأداة | URL | الخصائص |
|------|-----|----------|
| **Spokeo** | spokeo.com | بحث موسّع ومنصات تواصل اجتماعي |
| **BeenVerified** | beenverified.com | التحقق من الخلفية |
| **Intelius** | intelius.com | السجلات الجنائية |
| **الولايات المتحدة بحث** | ussearch.com | سجلات المحاكم |
| **Radaris** | radaris.com | سجلات العقارات |

---

<a id="-google-dorking-for-people"></a>
## 🔎 استخدام معاملات بحث Google للبحث عن الأشخاص

<a id="basic-searches"></a>
### عمليات بحث أساسية
```
# البحث بالاسم
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# الاسم والموقع
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# الاسم والشركة
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# الاسم والبريد الإلكتروني
"John Smith" "@gmail.com"
"John Smith" email
```

<a id="professional-info"></a>
### معلومات مهنية
```
# البحث عن السيرة الذاتية
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# بحث مخصص لـ LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# الملفات المهنية
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

<a id="social-media-discovery"></a>
### اكتشاف حسابات التواصل الاجتماعي
```
# البحث عن جميع الملفات الشخصية
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# البحث باسم المستخدم
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

<a id="-command-line-tools"></a>
## 🛠️ أدوات سطر الأوامر

<a id="sherlock-username-search"></a>
### Sherlock (البحث باسم المستخدم)
```bash
# تثبيت
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# البحث باسم المستخدم عبر المنصات
python3 sherlock username

# أسماء مستخدمين متعددة
python3 sherlock user1 user2 user3

# مواقع محددة فقط
python3 sherlock username --site twitter instagram facebook

# تصدير النتائج
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

<a id="maigret-enhanced-username-search"></a>
### Maigret (بحث موسّع باسم المستخدم)
```bash
# تثبيت
pip3 install maigret

# بحث
maigret username

# باستخدام Tor
maigret username --tor

# إخراج بصيغة JSON
maigret username --json results.json
```

<a id="holehe-email-registration-check"></a>
### holehe (فحص التسجيل بالبريد الإلكتروني)
```bash
# تثبيت
pip3 install holehe

# فحص البريد الإلكتروني عبر الخدمات
holehe email@example.com

# عرض المواقع التي يوجد فيها تسجيل فقط
holehe email@example.com --only-used
```

---

<a id="-email-to-person"></a>
## 📧 الانتقال من البريد الإلكتروني إلى الشخص

<a id="find-person-from-email"></a>
### البحث عن شخص انطلاقًا من بريده الإلكتروني
```
1. البحث عن البريد الإلكتروني في Google: "email@example.com"
2. الفحص في HIBP: haveibeenpwned.com
3. البحث في قواعد بيانات التسريبات
4. فحص التسجيل في منصات التواصل الاجتماعي
5. خدمات البحث العكسي بالبريد الإلكتروني
```

<a id="email-format-discovery"></a>
### اكتشاف صيغة البريد الإلكتروني
```bash
# صيغ شائعة للبريد الإلكتروني
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# البحث عن الصيغة باستخدام Hunter.io
https://hunter.io/email-finder
```

---

<a id="-phone-number-osint"></a>
## 📱 استخبارات المصادر المفتوحة لأرقام الهواتف

<a id="phone-lookup-services"></a>
### خدمات الاستعلام عن أرقام الهواتف
| الخدمة | URL | النوع |
|---------|-----|------|
| **TrueCaller** | truecaller.com | تحديد هوية المتصل |
| **Sync.me** | sync.me | تحديد هوية المتصل |
| **NumLookup** | numlookup.com | استعلام مجاني |
| **SpyDialer** | spydialer.com | استعلام عكسي |
| **CallerID Test** | calleridtest.com | معلومات شركة الاتصالات |

<a id="phone-number-google-dorks"></a>
### معاملات بحث Google لأرقام الهواتف
```
# بحث أساسي
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# البحث عن معلومات مرتبطة
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

<a id="osint-on-phone-numbers"></a>
### جمع معلومات مفتوحة المصدر عن أرقام الهواتف
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # واجهة ويب

# باستخدام واجهات API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

<a id="-address--property-records"></a>
## 🏠 العناوين وسجلات العقارات

<a id="property-search"></a>
### البحث عن العقارات
| الخدمة | URL | التغطية |
|---------|-----|----------|
| **Zillow** | zillow.com | الولايات المتحدة |
| **Redfin** | redfin.com | الولايات المتحدة |
| **مكتب تقييم العقارات بالمقاطعة** | [يختلف حسب المنطقة] | سجلات محلية |
| **Whitepages** | whitepages.com | الولايات المتحدة |

<a id="public-records"></a>
### السجلات العامة
```
# سجلات المحاكم
PACER (pacer.gov) - المحاكم الفيدرالية
مواقع محاكم الولايات - محاكم الولايات

# سجلات الناخبين
قواعد تسجيل الناخبين (تختلف حسب الولاية)

# تسجيل الشركات
مواقع أمانات شؤون الولايات
```

---

<a id="-social-network-analysis"></a>
## 👥 تحليل شبكات التواصل الاجتماعي

<a id="linkedin-osint"></a>
### استخبارات LinkedIn
```
# البحث عن الملف الشخصي
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# البحث عن الروابط بين الأشخاص
فحص "People Also Viewed"
فحص العلاقات المشتركة
قائمة موظفي الشركة
```

<a id="facebook-osint"></a>
### استخبارات Facebook
```
# روابط الملفات الشخصية
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# بحث Graph (محدود حاليًا وفق الأصل)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

<a id="tools-for-social-analysis"></a>
### أدوات تحليل منصات التواصل
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

<a id="-photoface-search"></a>
## 📸 البحث بالصور والوجوه

<a id="reverse-image-search"></a>
### البحث العكسي عن الصور
| الخدمة | URL | الاستخدام الأنسب |
|---------|-----|----------|
| **Google Images** | images.google.com | بحث عام |
| **TinEye** | tineye.com | مطابقات تامة |
| **Yandex** | yandex.com/images | الأفضل للوجوه وفق الأصل |
| **Bing Images** | bing.com/images | بديل جيد |
| **PimEyes** | pimeyes.com | التعرّف على الوجوه |

<a id="face-search"></a>
### البحث عن الوجوه
```
1. Yandex Images - الأفضل للبحث عن الوجوه وفق الأصل
2. PimEyes - تعرّف على الوجوه مقابل اشتراك
3. Social Catfish - بحث عكسي عن الصور المرتبطة بمنصات التواصل
```

---

<a id="-people-search-checklist"></a>
## 📋 قائمة البحث عن الأشخاص

```markdown
## الهدف: [الاسم الكامل]

### المعلومات الأساسية
□ الاسم القانوني الكامل
□ تاريخ الميلاد
□ العنوان الحالي
□ أرقام الهواتف
□ عناوين البريد الإلكتروني

### الحضور على الإنترنت
□ ملف LinkedIn
□ ملف Facebook
□ حساب Twitter/X
□ Instagram
□ TikTok
□ منصات تواصل أخرى
□ موقع شخصي أو مدونة

### المعلومات المهنية
□ جهة العمل الحالية
□ المسمى الوظيفي
□ الخبرات المهنية
□ التعليم
□ التراخيص المهنية
□ المنشورات وبراءات الاختراع

### السجلات
□ سجلات العقارات
□ سجلات المحاكم
□ تسجيل الشركات
□ تسجيل الناخبين
□ سجلات الزواج والطلاق

### العلاقات
□ أفراد العائلة
□ المعارف
□ شركاء الأعمال
□ العلاقات الاجتماعية
```

---

<a id="-legal--ethical-considerations"></a>
## ⚠️ اعتبارات قانونية وأخلاقية

```
✓ استخدم المعلومات المتاحة للعامة فقط
✓ احترم قوانين الخصوصية (GDPR وCCPA)
✓ لا تنتحل الهوية ولا تخدع الآخرين
✓ وثّق منهجيتك
✓ استخدم المعلومات لأغراض مشروعة فقط
✗ لا تلاحق الأشخاص ولا تضايقهم
✗ لا تدخل إلى الحسابات الخاصة
✗ لا تنشر النتائج للعامة دون موافقة
```

---

**العودة إلى OSINT:** [🔍 نظرة عامة على OSINT](README.ar.md)

> **ملاحظة الإصدارات:** احتُفظ بأوامر الأدوات وأمثلة الاستعلامات الأصلية، بما فيها النصوص المقتبسة وأسماء الأشخاص الافتراضية. مستودع [Twint الأصلي](https://github.com/twintproject/twint) مؤرشف منذ 30 مارس 2023؛ لذا تُقرأ أمثلته هنا بوصفها أمثلة تاريخية، ولا يُفترض أنها تعمل مع X الحالي. يعرض [توثيق Sherlock](https://sherlockproject.xyz/installation) طريقة الحزمة وأمر التشغيل الحاليين. أوصاف الخدمات المجانية والمدفوعة وتغطيتها الجغرافية هي كما وردت في الأصل.
