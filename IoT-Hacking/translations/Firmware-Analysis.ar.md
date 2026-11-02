<a id="-firmware-analysis-cheatsheet"></a>
# 💾 ورقة مرجعية لتحليل البرامج الثابتة (Firmware)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

<a id="-firmware-acquisition"></a>
## 📥 الحصول على البرنامج الثابت

```bash
# التنزيل من موقع الشركة المصنعة
# التقاط تحديث عبر الهواء (OTA) بواسطة اعتراض الاتصال (MITM)
# استخراج نسخة من الجهاز عبر UART أو SPI أو JTAG
# الاستخراج من تطبيق الهاتف
```

---

<a id="-initial-analysis"></a>
## 🔍 التحليل الأولي

```bash
# نوع الملف
file firmware.bin

# السلاسل النصية
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# الإنتروبيا (Entropy) لاستكشاف الضغط أو التشفير
binwalk -E firmware.bin
```

---

<a id="-binwalk-extraction"></a>
## 📦 الاستخراج باستخدام Binwalk

```bash
# فحص البرنامج الثابت
binwalk firmware.bin

# استخراج جميع المكونات
binwalk -e firmware.bin

# الاستخراج إلى مجلد محدد
binwalk -e -C output firmware.bin

# الاستخراج المتكرر للمكونات المتداخلة
binwalk -eM firmware.bin

# تواقيع محددة
binwalk -D 'elf:elf' firmware.bin
```

---

<a id="-file-systems"></a>
## 🗂️ أنظمة الملفات

<a id="squashfs"></a>
### SquashFS

> **توضيح للمثال:** يستقبل `unsquashfs` ملف صورة لنظام SquashFS ويستخرج محتوياته، ولا يركّب مجلدًا. إذا كان `squashfs-root` اسم المجلد الناتج عن استخراج سابق فلن يكون مدخلًا مناسبًا لهذا الأمر؛ أُبقي السطر كما ورد في الأصل. [توثيق squashfs-tools الرسمي](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

```bash
# الاستخراج
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# إعادة الحزم
mksquashfs output modified.squashfs -comp xz
```

<a id="jffs2"></a>
### JFFS2
```bash
# التركيب (Mount)
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

<a id="ubifs"></a>
### UBIFS
```bash
# الاستخراج باستخدام ubi_reader
ubireader_extract_files image.ubi
```

---

<a id="-secrets--credentials"></a>
## 🔐 الأسرار وبيانات الاعتماد

```bash
# البحث عن بيانات الاعتماد
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# البحث عن مفاتيح SSH
find . -name "id_rsa*"
find . -name "*.pem"

# البحث عن ملفات الإعداد
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

<a id="-binary-analysis"></a>
## 🔄 تحليل الملفات الثنائية

```bash
# البحث عن ملفات تنفيذية بتنسيق ELF
find . -type f -exec file {} \; | grep ELF

# التحليل باستخدام Ghidra
ghidraRun

# البحث عن سلاسل نصية مضمنة في الشيفرة
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **Binwalk** | استخراج المكونات |
| **firmware-mod-kit** | تعديل البرامج الثابتة |
| **EMBA** | تحليل آلي |
| **Ghidra** | الهندسة العكسية للملفات الثنائية |
| **firmwalker** | البحث عن الأسرار |

---

<a id="-firmware-analysis-checklist"></a>
## 📋 قائمة تحقق لتحليل البرامج الثابتة

```markdown
□ تحديد نوع الملف
□ فحص الإنتروبيا؛ هل قد تكون البيانات مشفرة؟
□ الاستخراج باستخدام binwalk
□ تركيب نظام الملفات
□ البحث عن بيانات الاعتماد
□ تحليل الملفات الثنائية
□ البحث عن أبواب خلفية (Backdoors)
□ البحث عن واجهات تنقيح الأخطاء (Debug Interfaces)
```

---

**العودة إلى قسم إنترنت الأشياء:** [📡 اختبار اختراق إنترنت الأشياء](./README.ar.md)
