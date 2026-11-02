<a id="-osint-open-source-intelligence-cheatsheet"></a>
# 🔍 مرجع استخبارات المصادر المفتوحة (OSINT)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **استخبارات المصادر المفتوحة (OSINT)** هي جمع المعلومات المتاحة للعامة وتحليلها.

---

<a id="-table-of-contents"></a>
## 📑 المحتويات

| الموضوع | الوصف | الدليل |
|-------|-------------|-------|
| **البحث عن الأشخاص** | البحث عن معلومات حول الأشخاص | [📄 عرض](People-Search.ar.md) |
| **استخبارات البريد الإلكتروني** | استطلاع عناوين البريد الإلكتروني والتحقق منها | [📄 عرض](Email-OSINT.ar.md) |
| **منصات التواصل الاجتماعي** | التحقيق باستخدام منصات التواصل الاجتماعي | [📄 عرض](Social-Media-OSINT.ar.md) |
| **النطاقات وعناوين IP** | استطلاع النطاقات وعناوين IP | [📄 عرض](Domain-IP-OSINT.ar.md) |
| **استخبارات المصادر المفتوحة للصور** | البحث العكسي عن الصور وتحليلها | [📄 عرض](Image-OSINT.ar.md) |

---

<a id="-osint-methodology"></a>
## 🎯 منهجية استخبارات المصادر المفتوحة

```
┌─────────────────────────────────────────────────────────────┐
│                    OSINT LIFECYCLE                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. REQUIREMENTS    →  Define intelligence needs            │
│         ↓                                                   │
│  2. COLLECTION      →  Gather raw data from sources         │
│         ↓                                                   │
│  3. PROCESSING      →  Convert data to usable format        │
│         ↓                                                   │
│  4. ANALYSIS        →  Evaluate and correlate findings      │
│         ↓                                                   │
│  5. DISSEMINATION   →  Report and present intelligence      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

<a id="-essential-osint-tools"></a>
## 🛠️ الأدوات الأساسية لاستخبارات المصادر المفتوحة

<a id="search-engines"></a>
### محركات البحث
| الأداة | URL | الغرض |
|------|-----|---------|
| **Google** | https://google.com | بحث عام |
| **Bing** | bing.com | محرك بحث بديل |
| **DuckDuckGo** | duckduckgo.com | يركّز على الخصوصية |
| **Yandex** | yandex.com | محرك بحث روسي (جيد للصور وفق الأصل) |
| **Baidu** | baidu.com | محرك بحث صيني |

<a id="people-search"></a>
### البحث عن الأشخاص
| الأداة | URL | الغرض |
|------|-----|---------|
| **Pipl** | pipl.com | بحث موسّع عن الأشخاص |
| **Spokeo** | spokeo.com | البحث عن الأشخاص في الولايات المتحدة |
| **WhitePages** | whitepages.com | الاستعلام عن الهاتف أو العنوان |
| **ThatsThem** | thatsthem.com | بحث مجاني عن الأشخاص |
| **BeenVerified** | beenverified.com | التحقق من الخلفية |

<a id="email-tools"></a>
### أدوات البريد الإلكتروني
| الأداة | URL | الغرض |
|------|-----|---------|
| **Hunter.io** | hunter.io | العثور على عناوين البريد الإلكتروني للشركات |
| **Have I Been Pwned** | haveibeenpwned.com | فحص التسريبات |
| **EmailRep** | emailrep.io | سمعة البريد الإلكتروني |
| **Phonebook.cz** | phonebook.cz | البحث عن البريد الإلكتروني والنطاقات |
| **h8mail** | GitHub | البحث عن بيانات الاعتماد في التسريبات |

<a id="social-media"></a>
### منصات التواصل الاجتماعي
| الأداة | URL | الغرض |
|------|-----|---------|
| **Sherlock** | GitHub | البحث باسم المستخدم |
| **Social Searcher** | social-searcher.com | البحث في منصات التواصل |
| **Namechk** | namechk.com | توفر اسم المستخدم |
| **KnowEm** | knowem.com | فحص اسم المستخدم |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | واجهة API مستقلة لبيانات X (Twitter)، للبحث وتصدير المتابعين والمراقبة والتكامل عبر MCP. |

<a id="domainip-tools"></a>
### أدوات النطاقات وعناوين IP
| الأداة | URL | الغرض |
|------|-----|---------|
| **Shodan** | shodan.io | البحث عن الأجهزة |
| **Censys** | censys.io | فحص الإنترنت |
| **SecurityTrails** | securitytrails.com | سجل DNS التاريخي |
| **ViewDNS** | viewdns.info | أدوات DNS |
| **BuiltWith** | builtwith.com | تحديد التقنيات المستخدمة |

---

<a id="-quick-reference-commands"></a>
## 🔥 أوامر مرجعية سريعة

<a id="theharvester"></a>
### theHarvester
```bash
# جمع عناوين البريد الإلكتروني والنطاقات الفرعية
theHarvester -d target.com -l 500 -b all

# مصادر محددة
theHarvester -d target.com -b google,linkedin,twitter

# الحفظ في ملف
theHarvester -d target.com -b all -f results.html
```

<a id="sherlock-username-search"></a>
### Sherlock (البحث باسم المستخدم)
```bash
# البحث في جميع المنصات
python3 sherlock.py username

# مواقع محددة
python3 sherlock.py username --site twitter instagram facebook

# حفظ النتائج في ملف
python3 sherlock.py username --output results.txt
```

<a id="maltego"></a>
### Maltego
```
# أنواع التحويلات (Transforms):
- من شخص إلى بريد إلكتروني
- من بريد إلكتروني إلى نطاق
- من نطاق إلى عنوان IP
- من عنوان IP إلى موقع جغرافي
- ملفات منصات التواصل الاجتماعي
```

<a id="recon-ng"></a>
### Recon-ng
```bash
# بدء recon-ng
recon-ng

# إنشاء مساحة عمل
workspaces create target_name

# تحميل الوحدات
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# تحديد الهدف
options set SOURCE target.com

# تشغيل الوحدة
run
```

---

<a id="-osint-categories"></a>
## 📊 فئات استخبارات المصادر المفتوحة

<a id="passive-osint"></a>
### جمع المعلومات السلبي
```
دون تفاعل مباشر مع الهدف:
- محركات البحث
- منصات التواصل الاجتماعي
- السجلات العامة
- Archive.org
- بيانات WHOIS
```

<a id="active-osint"></a>
### جمع المعلومات النشط
```
يتضمن قدرًا من التفاعل مع الهدف:
- فحص المنافذ
- استعلامات DNS
- الزحف إلى مواقع الويب
- التحقق من البريد الإلكتروني
```

---

<a id="-operational-security-opsec"></a>
## 🔒 الأمن التشغيلي (OPSEC)

<a id="best-practices"></a>
### أفضل الممارسات
```
✓ استخدم VPN أو Tor
✓ أنشئ حسابات بهويات بحثية مستعارة (Sock Puppets)
✓ استخدم أجهزة افتراضية
✓ لا تستخدم حساباتك الشخصية
✓ امسح ملفات الارتباط وذاكرة التخزين المؤقت بانتظام
✓ استخدم ملفات متصفح منفصلة
✓ وثّق جميع الخطوات
```

<a id="sock-puppet-accounts"></a>
### حسابات الهويات البحثية المستعارة
```
- استخدم هويات مولّدة
- حافظ على اتساق الهوية عبر المنصات
- حسابات قديمة (أكثر موثوقية وفق الأصل)
- تفاصيل ملف شخصي قابلة للتصديق
- لا تربطها بهويتك الحقيقية
```

---

<a id="-osint-checklist"></a>
## 📋 قائمة استخبارات المصادر المفتوحة

```markdown
## الهدف: [الاسم/الشركة/النطاق]

### الأشخاص
□ الصيغ المختلفة للاسم الكامل
□ أرقام الهواتف
□ عناوين البريد الإلكتروني
□ العناوين الفعلية
□ ملفات منصات التواصل
□ التاريخ المهني
□ التاريخ التعليمي
□ الصور

### الشركة
□ تسجيل النطاق (WHOIS)
□ التقنيات المستخدمة
□ قائمة الموظفين
□ صيغة البريد الإلكتروني
□ الحضور على منصات التواصل
□ الأخبار والبيانات الصحفية
□ المعلومات المالية
□ المواقع الفعلية

### المعلومات التقنية
□ عناوين IP
□ النطاقات الفرعية
□ المنافذ والخدمات المفتوحة
□ سجلات DNS
□ شهادات SSL
□ البيانات التاريخية
```

---

<a id="-osint-frameworks"></a>
## 🔗 أطر استخبارات المصادر المفتوحة

<a id="osint-framework"></a>
### OSINT Framework
```
https://osintframework.com/
- منظّم بحسب الفئات
- روابط لمئات الأدوات
- بنية شجرية مرئية
```

<a id="inteltechniques"></a>
### IntelTechniques
```
https://inteltechniques.com/tools/
- أدوات Michael Bazzell
- أدوات بحث مخصصة
- مسارات عمل التحقيق
```

---

<a id="-related-guides-in-this-repo"></a>
## 📚 أدلة ذات صلة في هذا المستودع

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**العودة إلى الصفحة الرئيسية:** [🔴 مراجع اختبار الاختراق](../../README.md)

> **شرح دورة العمل:** يبدأ الرسم بتحديد احتياجات المعلومات، ثم جمع البيانات الخام، ومعالجتها إلى صيغة مفيدة، وتحليلها وربط النتائج، وأخيرًا إعداد التقارير وعرض الاستخبارات. أُبقي الرسم الإنجليزي ثابت العرض للحفاظ على محاذاته. OSINT هو استخبارات المصادر المفتوحة، وOPSEC هو الأمن التشغيلي. معاملات البحث المتقدم (Dorks) تضبط نطاق البحث ولا تغيّر صلاحيات الوصول.

> **ملاحظة الإصدارات:** أمثلة تشغيل Sherlock بأسلوب `python3 sherlock.py` محفوظة من الأصل؛ يعرض [توثيق المشروع](https://sherlockproject.xyz/installation) حزمة `sherlock-project` وأمر التشغيل `sherlock`. تصنيفات الخدمات المجانية والمدفوعة وأعداد المواقع في هذه الأدلة منقولة من الأصل وليست مراجعة مستقلة لخطط الخدمات الحالية.
