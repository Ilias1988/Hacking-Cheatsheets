<a id="-forensics-ctf-cheatsheet"></a>
# 🔬 مرجع تحديات الأدلة الجنائية الرقمية في CTF

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

---

<a id="-file-analysis"></a>
## 📁 تحليل الملفات

```bash
# نوع الملف
file unknown_file

# عرض بايتات الملف بالنظام الست عشري
xxd file | head
hexdump -C file | head

# السلاسل النصية
strings -n 6 file
strings -e l file    # ترتيب البايتات من الأقل أهمية أولًا (Little-endian)

# استخراج الملفات المضمّنة اعتمادًا على بنيتها (File Carving)
binwalk file
binwalk -e file      # استخراج
foremost file
```

---

<a id="-steganography"></a>
## 🖼️ إخفاء المعلومات (Steganography)

<a id="images"></a>
### الصور

```bash
# البيانات الوصفية
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # جميع الطرق

# تحليل البت الأقل أهمية (LSB)
stegsolve.jar        # أداة بواجهة رسومية

# السلاسل النصية داخل الصورة
strings image.png | grep -i flag
```

<a id="audio"></a>
### الصوت
```bash
# المخطط الطيفي باستخدام Audacity
افتح الملف ثم اختر Analyze → Plot Spectrum

# Sonic Visualiser
# البحث عن بيانات مخفية في الترددات
```

---

<a id="-memory-forensics"></a>
## 🧠 التحليل الجنائي للذاكرة

<a id="volatility"></a>
### Volatility
```bash
# تحديد ملف تعريف الذاكرة (Profile)
volatility -f dump.raw imageinfo

# قائمة العمليات
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# سجل الأوامر
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# استخراج ذاكرة عملية
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# الملفات
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

<a id="-disk-forensics"></a>
## 💽 التحليل الجنائي للأقراص

```bash
# ربط صورة القرص
mount -o loop image.dd /mnt/disk

# معلومات نظام الملفات
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

<a id="-network-forensics-pcap"></a>
## 📊 التحليل الجنائي للشبكات (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# استخراج الملفات
tcpflow -r capture.pcap
```

---

<a id="-forensics-checklist"></a>
## 📋 قائمة الأدلة الجنائية الرقمية

```markdown
□ فحص نوع الملف باستخدام الأمر file
□ البحث في السلاسل النصية عن العلم (Flag)
□ فحص البيانات الوصفية باستخدام exiftool
□ استخدام Binwalk للبحث عن الملفات المضمّنة
□ أدوات كشف المعلومات المخفية
□ تحليل الذاكرة باستخدام Volatility
□ تحليل PCAP باستخدام Wireshark
```

---

**العودة إلى CTF:** [🏁 نظرة عامة على CTF](README.ar.md)

> **ملاحظة الإصدارات:** أوامر `imageinfo` و`--profile=Win7SP1x64` في الأصل تخص Volatility 2؛ لا يستخدم Volatility 3 ملفات Profiles بالطريقة نفسها، بل يعتمد على جداول الرموز. [مرجع الانتقال الرسمي](https://volatility3.readthedocs.io/en/latest/vol2to3.html).

> **تصحيح بشأن الصوت:** المسار `Analyze → Plot Spectrum` يعرض التردد مقابل السعة، وليس مخططًا طيفيًا زمنيًا (Spectrogram). لعرض تغير الترددات عبر الزمن تُستخدم Spectrogram View. [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html)، [Spectrogram View](https://manual.audacityteam.org/man/spectrogram_view.html).
