<a id="-social-media-osint"></a>
# 📱 استخبارات المصادر المفتوحة لمنصات التواصل

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Social-Media-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-username-search-tools"></a>
## 🔍 أدوات البحث باسم المستخدم

| الأداة | URL | المنصات |
|------|-----|-----------|
| **Sherlock** | github.com/sherlock-project | أكثر من 300 موقع |
| **Maigret** | github.com/soxoj/maigret | أكثر من 2500 موقع |
| **Namechk** | namechk.com | أكثر من 100 موقع |
| **KnowEm** | knowem.com | أكثر من 500 موقع |
| **WhatsMyName** | whatsmyname.app | عبر الويب |

<a id="sherlock"></a>
### Sherlock
```bash
python3 sherlock username
python3 sherlock user1 user2 --output results.txt
```

<a id="maigret"></a>
### Maigret
```bash
maigret username
maigret username --tor --json results.json
```

---

<a id="-platform-specific-osint"></a>
## 📊 جمع المعلومات حسب المنصة

<a id="twitterx"></a>
### Twitter/X
```bash
# Twint
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# رابط الملف الشخصي
twitter.com/username
x.com/username
```

<a id="instagram"></a>
### Instagram
```
# الملف الشخصي: instagram.com/username
# الأدوات: Instaloader, Osintgram

# Instaloader
pip3 install instaloader
instaloader profile username --no-videos
```

<a id="linkedin"></a>
### LinkedIn
```
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company"
```

<a id="facebook"></a>
### Facebook
```
facebook.com/username
facebook.com/search/people/?q=name
# الأداة: fb-friend-list-scraper
```

<a id="tiktok"></a>
### TikTok
```
tiktok.com/@username
# الأداة: TikTok-Scraper
```

<a id="reddit"></a>
### Reddit
```
reddit.com/user/username
# الأداة: BDFR, Pushshift API
```

---

<a id="-quick-commands"></a>
## 🛠️ أوامر سريعة

```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# بحث موحّد
echo "username" | sherlock --print-all
```

---

<a id="-social-media-checklist"></a>
## 📋 قائمة منصات التواصل الاجتماعي

```markdown
□ ملف Twitter/X
□ ملف Instagram  
□ ملف Facebook
□ ملف LinkedIn
□ حساب TikTok
□ حساب Reddit
□ ملف GitHub
□ قناة YouTube
□ خوادم Discord
□ مجموعات Telegram
□ ملفات منصات التعارف
□ ملفات منصات الألعاب
```

---

**العودة إلى OSINT:** [🔍 نظرة عامة على OSINT](README.ar.md)

> **ملاحظة الإصدارات:** [مستودع Twint الأصلي](https://github.com/twintproject/twint) مؤرشف منذ 30 مارس 2023؛ أمثلته محفوظة للمقارنة التاريخية. كما أن أعداد المواقع المدعومة في الجدول منقولة من الأصل وليست أعدادًا جرى التحقق منها لهذه الترجمة. يوضّح [توثيق Sherlock الرسمي](https://sherlockproject.xyz/installation) استخدام حزمة `sherlock-project` وأمر `sherlock`؛ وقد تخص صيغ التشغيل القديمة في الأصل تخطيطات سابقة للمشروع.
