<a id="-cloud-security-cheatsheets"></a>
# ☁️ أوراق مرجعية لأمن السحابة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 ██████╗██╗      ██████╗ ██╗   ██╗██████╗     ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██║     ██║     ██║   ██║██║   ██║██║  ██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║     ██║     ██║   ██║██║   ██║██║  ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
 ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝     ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

<a id="-what-is-cloud-security-testing"></a>
## 🎯 ما هو اختبار أمن السحابة؟

يشمل اختبار أمن السحابة تحديد الثغرات في البنية التحتية السحابية، ومنها:
- 🪣 **التخزين (Storage)** — أخطاء إعداد S3 وBlob وGCS
- 🔑 **إدارة الهويات والوصول (IAM)** — عيوب إدارة الهوية وصلاحيات الوصول
- 🖥️ **الحوسبة (Compute)** — ثغرات EC2 والأجهزة الافتراضية والخدمات دون خوادم (Serverless)
- 🌐 **الشبكات (Network)** — الشبكات الافتراضية الخاصة VPC ومجموعات الأمان وجدران الحماية
- 🔐 **الأسرار (Secrets)** — بيانات اعتماد ومفاتيح مكشوفة

---

<a id="-cloud-security-guides"></a>
## 📖 أدلة أمن السحابة

| مزود السحابة | الوصف | الدليل |
|----------------|-------------|-------|
| **AWS** | اختبار اختراق Amazon Web Services | [📄 عرض](./AWS-Pentesting.ar.md) |
| **Azure** | اختبار اختراق Microsoft Azure | [📄 عرض](./Azure-Pentesting.ar.md) |
| **GCP** | اختبار اختراق Google Cloud Platform | [📄 عرض](./GCP-Pentesting.ar.md) |

---

<a id="-essential-cloud-security-tools"></a>
## 🛠️ أدوات أساسية لأمن السحابة

| الأداة | الغرض | المنصة |
|------|---------|----------|
| **ScoutSuite** | تدقيق أمني لبيئات سحابية متعددة | جميعها |
| **Prowler** | تقييم أمن AWS | AWS |
| **CloudSploit** | ماسح أمني للسحابة | جميعها |
| **Pacu** | إطار عمل لاستغلال AWS | AWS |
| **CloudBrute** | استكشاف البيئة السحابية | جميعها |
| **S3Scanner** | استكشاف حاويات S3 | AWS |
| **AzureHound** | تحديد مسارات الهجوم في Azure AD | Azure |
| **gcp_enum** | استكشاف GCP | GCP |

<a id="quick-tool-install"></a>
### تثبيت سريع للأدوات
```bash
# أداة ScoutSuite لبيئات سحابية متعددة
pip install scoutsuite
scout aws

# أداة Prowler لمنصة AWS
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# أداة Pacu لاستغلال AWS
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# أداة CloudBrute للاستكشاف
go install github.com/0xsha/CloudBrute@latest
```

---

<a id="-common-cloud-vulnerabilities"></a>
## 📊 ثغرات سحابية شائعة

| الثغرة | الوصف | الأثر |
|---------------|-------------|--------|
| **حاويات S3/Blob عامة** | تخزين متاح للعامة | تسرب البيانات |
| **صلاحيات IAM مفرطة** | صلاحيات `*` | اختراق الحساب بالكامل |
| **بيانات وصفية مكشوفة** | إمكانية الوصول إلى `169.254.169.254` | سرقة بيانات الاعتماد |
| **بيانات اعتماد مضمنة في الشيفرة** | مفاتيح ضمن الشيفرة أو ملفات الإعداد | الاستيلاء على الحساب |
| **SSRF نحو البيانات الوصفية** | الوصول إلى البيانات الوصفية السحابية | سرقة بيانات الاعتماد |
| **دوال غير آمنة** | أخطاء إعداد Lambda/Functions | تنفيذ شيفرة |

---

<a id="-quick-reference-cloud-metadata-endpoints"></a>
## 🔗 مرجع سريع: نقاط نهاية البيانات الوصفية السحابية

| المزود | نقطة نهاية البيانات الوصفية |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

<a id="-resources"></a>
## 📚 الموارد

- [مشروع OWASP لأمن السحابة](https://owasp.org/www-project-cloud-security/)
- [أفضل ممارسات أمن AWS](https://docs.aws.amazon.com/security/)
- [توثيق أمن Azure](https://docs.microsoft.com/en-us/azure/security/)
- [أمن GCP](https://cloud.google.com/security)
- [مرجع HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ اختبر أمن السحابة بمسؤولية!</b>
</p>
