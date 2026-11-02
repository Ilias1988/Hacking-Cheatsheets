<a id="-hardware-hacking-cheatsheet"></a>
# 🔧 ورقة مرجعية لاختبار اختراق العتاد (Hardware Hacking)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardware-Hacking.tr.md) • [🇬🇷 Ελληνικά](Hardware-Hacking.el.md)

---

<a id="-uart-serial-console"></a>
## 🔌 واجهة UART ووحدة التحكم التسلسلية (Serial Console)

<a id="identify-uart-pins"></a>
### تحديد أطراف UART

> **توضيح كهربائي:** جهد التغذية VCC وجهد إشارات TX/RX ليسا بالضرورة متساويين؛ فبعض كبلات USB-UART توفر تغذية 5V وإشارات منطقية 3.3V. لذلك لا تعمم القيم الواردة على كل لوحة، وتحقق من مواصفات الجهاز والمحول. [مثال موثق من Adafruit](https://www.adafruit.com/product/4364).
```
TX (Transmit) - إرسال البيانات من الجهاز
RX (Receive) - استقبال البيانات إلى الجهاز
GND - الأرضي
VCC - التغذية الكهربائية (3.3V/5V)
```

<a id="tools"></a>
### الأدوات
- **المقياس المتعدد (Multimeter)** — تحديد GND وVCC
- **المحلل المنطقي (Logic Analyzer)** — تحديد TX/RX ومعدل البود
- **محول USB-UART** — مثل FT232 وCP2102 وCH340

<a id="connection"></a>
### الاتصال
```bash
# استخدام screen في Linux
screen /dev/ttyUSB0 115200

# استخدام minicom في Linux
minicom -D /dev/ttyUSB0 -b 115200

# picocom
picocom -b 115200 /dev/ttyUSB0
```

<a id="common-baud-rates"></a>
### معدلات البود الشائعة (Baud Rates)
```
9600, 19200, 38400, 57600, 115200
```

---

<a id="-jtag-debug-interface"></a>
## 🔧 واجهة تنقيح الأخطاء JTAG

<a id="pins"></a>
### الأطراف (Pins)
```
TDI - دخل بيانات الاختبار (Test Data In)
TDO - خرج بيانات الاختبار (Test Data Out)
TCK - ساعة الاختبار (Test Clock)
TMS - اختيار وضع الاختبار (Test Mode Select)
TRST - إعادة ضبط الاختبار، اختياري (Test Reset)
```

<a id="tools-1"></a>
### الأدوات
```bash
# OpenOCD
openocd -f interface/jlink.cfg -f target/stm32f1x.cfg

# JTAGulator
# التعرف التلقائي على ترتيب أطراف JTAG
```

---

<a id="-spi-flash-memory"></a>
## 📟 واجهة SPI لذاكرة الفلاش

<a id="pins-1"></a>
### الأطراف (Pins)
```
MOSI - خرج المتحكم الرئيسي إلى دخل الجهاز التابع (Master Out Slave In)
MISO - دخل المتحكم الرئيسي من خرج الجهاز التابع (Master In Slave Out)
CLK  - إشارة الساعة (Clock)
CS   - اختيار الشريحة (Chip Select)
```

<a id="dump-flash"></a>
### استخراج نسخة من ذاكرة الفلاش
```bash
# flashrom
flashrom -p ch341a_spi -r dump.bin

# Bus Pirate
flashrom -p buspirate_spi:dev=/dev/ttyUSB0 -r dump.bin
```

---

<a id="-i2c-eeprom"></a>
## 📡 واجهة I2C لذاكرة EEPROM

<a id="pins-2"></a>
### الأطراف (Pins)
```
SDA - خط البيانات التسلسلية (Serial Data)
SCL - خط الساعة التسلسلية (Serial Clock)
```

<a id="dump-eeprom"></a>
### استخراج نسخة من EEPROM
```bash
# i2ctools
i2cdetect -y 1
i2cdump -y 1 0x50
```

---

<a id="-hardware-tools"></a>
## 🛠️ أدوات العتاد

| الأداة | الاستخدام |
|------|-----|
| **Bus Pirate** | اختبار بروتوكولات متعددة |
| **FT232** | التحويل من USB إلى UART |
| **المحلل المنطقي (Logic Analyzer)** | تحليل الإشارات |
| **المقياس المتعدد (Multimeter)** | قياس الجهد واختبار الاستمرارية |
| **JTAGulator** | اكتشاف أطراف JTAG |
| **CH341A** | مبرمج ذاكرة فلاش SPI |

---

<a id="-hardware-hacking-checklist"></a>
## 📋 قائمة تحقق لاختبار اختراق العتاد

```markdown
□ الفحص البصري للشرائح ونقاط الاختبار
□ تحديد موصلات تنقيح الأخطاء
□ تحديد UART باستخدام مقياس متعدد
□ التقاط الإشارات باستخدام محلل منطقي
□ الاتصال والحصول على واجهة أوامر (Shell)
□ استخراج نسخة من ذاكرة الفلاش
□ تحليل البرنامج الثابت المستخرج
```

---

**العودة إلى قسم إنترنت الأشياء:** [📡 اختبار اختراق إنترنت الأشياء](./README.ar.md)
