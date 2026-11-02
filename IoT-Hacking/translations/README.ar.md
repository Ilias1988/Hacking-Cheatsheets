<a id="-iot-hacking-cheatsheet"></a>
# 📡 ورقة مرجعية لاختبار اختراق إنترنت الأشياء (IoT)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
██╗ ██████╗ ████████╗    ██╗  ██╗ █████╗  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██║██╔═══██╗╚══██╔══╝    ██║  ██║██╔══██╗██╔════╝██║ ██╔╝██║████╗  ██║██╔════╝ 
██║██║   ██║   ██║       ███████║███████║██║     █████╔╝ ██║██╔██╗ ██║██║  ███╗
██║██║   ██║   ██║       ██╔══██║██╔══██║██║     ██╔═██╗ ██║██║╚██╗██║██║   ██║
██║╚██████╔╝   ██║       ██║  ██║██║  ██║╚██████╗██║  ██╗██║██║ ╚████║╚██████╔╝
╚═╝ ╚═════╝    ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-table-of-contents"></a>
## 📑 جدول المحتويات

| الموضوع | الوصف | الدليل |
|-------|-------------|-------|
| **تحليل البرامج الثابتة** | الاستخراج والهندسة العكسية | [📄 عرض](./Firmware-Analysis.ar.md) |
| **اختبار اختراق العتاد** | UART وJTAG وSPI وI2C | [📄 عرض](./Hardware-Hacking.ar.md) |

---

<a id="-iot-attack-surface"></a>
## 🎯 سطح الهجوم في إنترنت الأشياء (Attack Surface)

يشمل المخطط الاتصالات اللاسلكية مثل WiFi وBLE، وواجهات العتاد مثل UART وJTAG، والبرامج الثابتة وتحليلها، والشبكة وواجهات الويب وAPI، وتطبيق الهاتف المرافق، ثم الخدمات السحابية والمصادقة وتخزين البيانات. أُبقي الرسم الأصلي للحفاظ على محاذاته.

```
┌─────────────────────────────────────────────────────────────┐
│                    IoT ATTACK SURFACE                        │
├─────────────────────────────────────────────────────────────┤
│  📡 WIRELESS      →  WiFi, BLE, Zigbee, Z-Wave, LoRa        │
│  🔧 HARDWARE      →  UART, JTAG, SPI, debug ports           │
│  💾 FIRMWARE      →  Binwalk, extraction, RE                │
│  🌐 NETWORK       →  Web interface, APIs, protocols         │
│  📱 MOBILE APP    →  Companion apps, BLE communication      │
│  ☁️ CLOUD         →  APIs, authentication, data storage     │
└─────────────────────────────────────────────────────────────┘
```

---

<a id="-quick-reference"></a>
## 🔥 مرجع سريع

<a id="firmware"></a>
### البرامج الثابتة

> **توضيح للمثال:** يستقبل `unsquashfs` ملف صورة لنظام SquashFS ويستخرج محتوياته، ولا يركّب مجلدًا. إذا كان `squashfs-root` اسم المجلد الناتج عن استخراج سابق فلن يكون مدخلًا مناسبًا لهذا الأمر؛ أُبقي السطر كما ورد في الأصل. [توثيق squashfs-tools الرسمي](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md).

```bash
# استخراج البرنامج الثابت
binwalk -e firmware.bin

# البحث عن أنظمة الملفات
binwalk firmware.bin | grep -i filesystem

# تركيب SquashFS بحسب وصف الأصل؛ راجع التوضيح أدناه
unsquashfs squashfs-root
```

<a id="hardware"></a>
### العتاد
```bash
# الاتصال عبر UART
screen /dev/ttyUSB0 115200

# Bus Pirate
sudo picocom -b 115200 /dev/ttyUSB0
```

---

<a id="-essential-tools"></a>
## 🛠️ الأدوات الأساسية

| الفئة | الأدوات |
|----------|-------|
| **البرامج الثابتة** | Binwalk, firmware-mod-kit, EMBA |
| **العتاد** | Bus Pirate والمحلل المنطقي والمقياس المتعدد |
| **الاتصالات اللاسلكية** | HackRF, RTL-SDR, Ubertooth |
| **الشبكات** | Wireshark, Nmap, Shodan |

---

<a id="-shodan-iot-searches"></a>
## 🔍 عمليات بحث Shodan عن أجهزة إنترنت الأشياء

```bash
# كاميرات الويب
webcam country:US

# أنظمة التحكم الصناعي
port:502 modbus

# وسطاء MQTT
port:1883

# بيانات الاعتماد الافتراضية
default password
```

---

<a id="-iot-pentest-checklist"></a>
## 📋 قائمة تحقق لاختبار اختراق إنترنت الأشياء

```markdown
□ جمع المعلومات باستخدام Shodan واستخبارات المصادر المفتوحة (OSINT)
□ الحصول على البرنامج الثابت وتحليله
□ فحص العتاد ومنافذ تنقيح الأخطاء
□ تحليل حركة الشبكة
□ اختبار الويب وواجهات API
□ تحليل تطبيق الهاتف
□ تحليل البروتوكولات اللاسلكية
□ اختبار الخدمات الخلفية السحابية
```

---

**العودة إلى الصفحة الرئيسية:** [🔴 أوراق مرجعية لاختبار الاختراق](../../README.md)
