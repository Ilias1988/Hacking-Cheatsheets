<a id="-image-osint"></a>
# 🖼️ استخبارات المصادر المفتوحة للصور

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

<a id="-reverse-image-search"></a>
## 🔍 البحث العكسي عن الصور

| الخدمة | URL | الاستخدام الأنسب |
|---------|-----|----------|
| **Google Images** | images.google.com | بحث عام |
| **Yandex** | yandex.com/images | **الوجوه** (الأفضل وفق تقييم الأصل) |
| **TinEye** | tineye.com | مطابقات تامة |
| **Bing Images** | bing.com/images | بديل |
| **PimEyes** | pimeyes.com | التعرّف على الوجوه |

---

<a id="-metadata-extraction-exif"></a>
## 📷 استخراج البيانات الوصفية (EXIF)

<a id="exiftool"></a>
### ExifTool
```bash
# عرض جميع البيانات الوصفية
exiftool image.jpg

# إحداثيات GPS
exiftool -gps* image.jpg

# معلومات الكاميرا
exiftool -make -model -datetime image.jpg

# إزالة البيانات الوصفية
exiftool -all= image.jpg
```

<a id="online-tools"></a>
### أدوات عبر الإنترنت
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [عارض البيانات الوصفية من Metadata Remover](https://metadataremover.ai/metadata-viewer) — أداة من جهة خارجية لفحص EXIF/IPTC/XMP عبر المتصفح. جرّبها بملفات غير حساسة.

> يمكن تعديل البيانات الوصفية أو إزالتها؛ لذا تحقّق منها بمقارنتها بأدلة أخرى.

---

<a id="-image-analysis"></a>
## 🔎 تحليل الصور

<a id="forensic-analysis"></a>
### التحليل الجنائي الرقمي
```
خصائص FotoForensics.com:
- تحليل مستويات الخطأ (ELA)
- استخراج البيانات الوصفية
- كشف البيانات المخفية
- تحليل الضغط
```

<a id="geolocation-from-images"></a>
### تحديد الموقع الجغرافي من الصور
```
1. استخراج إحداثيات GPS من بيانات EXIF
2. البحث عن المعالم الظاهرة في الصورة
3. المقارنة مع صور الشوارع
4. فحص النصوص على المباني واللافتات
```

---

<a id="-image-osint-checklist"></a>
## 📋 قائمة استخبارات الصور

```markdown
□ البحث العكسي عن الصورة (Google وYandex)
□ استخراج بيانات EXIF الوصفية
□ فحص إحداثيات GPS
□ البحث بالتعرّف على الوجوه
□ تحليل مؤشرات التلاعب
□ البحث عن نص الصورة واستخدام التعرّف الضوئي على المحارف (OCR)
□ فحص تاريخ ظهور الصورة (TinEye)
```

---

**العودة إلى OSINT:** [🔍 نظرة عامة على OSINT](README.ar.md)

> **توضيح تحريري:** تفضيلات محركات البحث عن الوجوه ووصف أحدها بأنه «الأفضل» تقييمات واردة في الأصل، وليست نتيجة اختبار مقارن أُجري لهذه الترجمة. EXIF بيانات وصفية قد تتضمن معلومات الكاميرا ووقت الالتقاط والموقع؛ وELA يعني تحليل مستويات الخطأ. تبقى بيانات الصورة قرائن يجب مقارنتها بأدلة أخرى كما يوضح الأصل.
