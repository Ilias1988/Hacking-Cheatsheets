<a id="-incident-response-cheatsheet"></a>
# 🚨 مرجع الاستجابة للحوادث

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

<a id="-incident-response-lifecycle-nist"></a>
## 📊 دورة حياة الاستجابة للحوادث (NIST)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.PREPARATION│────▶│ 2.DETECTION  │────▶│3.CONTAINMENT │────▶│4.ERADICATION │
│              │     │ & ANALYSIS   │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.LESSONS    │◀────│ 5.RECOVERY   │◀──────────────┘
                 │   LEARNED    │     │              │
                 └──────────────┘     └──────────────┘
```

---

<a id="1-preparation"></a>
## 1️⃣ الاستعداد

<a id="documentation-checklist"></a>
### قائمة الوثائق المطلوبة
```markdown
- [ ] توثيق خطة الاستجابة للحوادث
- [ ] قائمة جهات اتصال داخلية وخارجية
- [ ] خطة التواصل
- [ ] جرد الأصول
- [ ] مخططات الشبكة
- [ ] الإعدادات المرجعية
- [ ] التحقق من النسخ الاحتياطية
```

<a id="ir-team-contacts-template"></a>
### نموذج جهات اتصال فريق الاستجابة للحوادث
```
قائد فريق الاستجابة:          [الاسم] - [الهاتف] - [البريد الإلكتروني]
أمن تقنية المعلومات:      [الاسم] - [الهاتف] - [البريد الإلكتروني]
الشؤون القانونية:            [الاسم] - [الهاتف] - [البريد الإلكتروني]
التواصل:   [الاسم] - [الهاتف] - [البريد الإلكتروني]
الموارد البشرية:               [الاسم] - [الهاتف] - [البريد الإلكتروني]
خبراء الأدلة الرقمية الخارجيون: [الشركة] - [الهاتف]
جهات إنفاذ القانون:  [الجهة] - [الهاتف]
```

<a id="essential-ir-tools"></a>
### أدوات أساسية للاستجابة للحوادث
```bash
# جمع الأدلة الجنائية الرقمية
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# تحليل الشبكة
- Wireshark
- tcpdump
- Zeek

# تحليل السجلات
- Splunk/ELK
- grep/awk/sed
```

---

<a id="2-detection--analysis"></a>
## 2️⃣ الاكتشاف والتحليل

<a id="initial-triage-questions"></a>
### أسئلة الفرز الأولي
```markdown
1. ما نوع هذا الحادث؟
   - [ ] إصابة ببرمجية خبيثة
   - [ ] وصول غير مصرح به
   - [ ] خرق بيانات
   - [ ] حجب الخدمة
   - [ ] تهديد داخلي
   - [ ] التصيد

2. متى اكتُشف؟
3. كيف اكتُشف؟
4. ما الأنظمة المتأثرة؟
5. ما البيانات التي قد تتأثر؟
6. هل ما زال نشطًا؟
```

<a id="windows-quick-triage"></a>
### الفرز السريع على Windows
```cmd
# جلسات المستخدمين الحالية
query user
qwinsta

# الاتصالات النشطة
netstat -ano | findstr ESTABLISHED

# العمليات قيد التشغيل
tasklist /v
wmic process get processid,parentprocessid,commandline

# تعديلات الملفات الأخيرة
forfiles /P C:\ /S /D +01/01/2024

# المهام المجدولة
schtasks /query /fo LIST /v

# الخدمات
sc query state=all
wmic service get name,startmode,state

# مفاتيح Run في السجل
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# سجل أوامر PowerShell الأخير
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

<a id="linux-quick-triage"></a>
### الفرز السريع على Linux
```bash
# المستخدمون المسجلون حاليًا
w
who
last -a

# الاتصالات النشطة
ss -tulnp
netstat -tulnp
lsof -i

# العمليات قيد التشغيل
ps auxwww
pstree -p

# تعديلات الملفات الأخيرة
find / -mtime -1 -type f 2>/dev/null

# مهام Cron
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# برامج بدء التشغيل النصية
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# سجل الأوامر
cat ~/.bash_history
cat /root/.bash_history
```

<a id="network-analysis"></a>
### تحليل الشبكة
```bash
# التقاط حركة الشبكة
tcpdump -i eth0 -w capture.pcap

# التصفية حسب مضيف محدد
tcpdump -i eth0 host 192.168.1.100

# حركة HTTP
tcpdump -i eth0 port 80 or port 443

# استعلامات DNS
tcpdump -i eth0 port 53
```

---

<a id="3-containment"></a>
## 3️⃣ الاحتواء

<a id="short-term-containment"></a>
### الاحتواء قصير الأمد
```bash
# عزل الشبكة على Windows
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# عزل الشبكة على Linux
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# تعطيل الحساب المخترق
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# إنهاء العملية الخبيثة
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

<a id="long-term-containment"></a>
### الاحتواء طويل الأمد
```bash
# تقسيم الشبكة
# تطبيق قواعد جدار الحماية لعزل شبكة VLAN المتأثرة
# تحديث قوائم التحكم في الوصول على المحولات والموجّهات

# حظر عناوين IP والنطاقات الخبيثة في جدار الحماية
# الإضافة إلى قائمة الحظر في الوكيل
# تحديث DNS sinkhole لإعادة توجيه النطاقات الخبيثة

# تعطيل الخدمات المتأثرة مؤقتًا
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

<a id="evidence-preservation"></a>
### حفظ الأدلة
```bash
# تفريغ الذاكرة على Windows باستخدام WinPMEM أو DumpIt
winpmem_mini_x64.exe memory.raw

# تفريغ الذاكرة على Linux
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# صورة القرص
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# حساب البصمة للتحقق من السلامة
sha256sum /evidence/disk.img > disk.img.sha256

# جمع البيانات سريعة الزوال أولًا وفق ترتيب تطايرها:
# 1. الذاكرة
# 2. حالة الشبكة
# 3. العمليات قيد التشغيل
# 4. القرص
```

---

<a id="4-eradication"></a>
## 4️⃣ الاستئصال

<a id="malware-removal"></a>
### إزالة البرمجيات الخبيثة
```bash
# تحديد آليات الاستمرارية وإزالتها

# Windows - السجل
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows - المهام المجدولة
schtasks /delete /tn "MalwareTask" /f

# Windows - الخدمات
sc delete "MalwareService"

# Linux - مهام Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

<a id="password-reset"></a>
### إعادة تعيين كلمات المرور
```bash
# فرض إعادة تعيين كلمات المرور لجميع الحسابات المخترقة
# إعادة تعيين كلمات مرور حسابات الخدمات
# استبدال مفاتيح API ورموز الوصول
# إبطال الجلسات

# Windows - فرض تغيير كلمة المرور
net user username /logonpasswordchg:yes

# إعادة ضبط تذاكر Kerberos عند اختراق AD
# - إعادة تعيين كلمة مرور krbtgt مرتين
# - إعادة تعيين كلمات مرور جميع حسابات المسؤولين
```

<a id="patch--update"></a>
### الترقيع والتحديث
```bash
# تطبيق الرقع الأمنية الناقصة
# تحديث توقيعات مكافحة الفيروسات
# تصحيح الإعدادات الخاطئة التي سمحت بالهجوم
```

---

<a id="5-recovery"></a>
## 5️⃣ التعافي

<a id="system-restoration"></a>
### استعادة الأنظمة
```bash
# الاستعادة من نسخة احتياطية سليمة
# إعادة بناء الأنظمة المخترقة
# التحقق من السلامة قبل إعادة الاتصال

# الاستعادة التدريجية:
1. استعادة الأنظمة الحرجة أولًا
2. مراقبة مؤشرات تكرار الإصابة
3. استعادة الأنظمة الإضافية
4. العودة إلى التشغيل المعتاد
```

<a id="monitoring-post-recovery"></a>
### المراقبة بعد التعافي
```bash
# زيادة تفصيل السجلات
# نشر وسائل مراقبة إضافية
# مراقبة عودة المهاجم

# المؤشرات الأساسية التي تجب مراقبتها:
- مؤشرات الاختراق نفسها (عناوين IP والنطاقات والبصمات)
- تكتيكات وتقنيات وإجراءات مشابهة (TTPs)
- محاولات الوصول إلى الحسابات نفسها
- الاتصال ببنية القيادة والتحكم (C2)
```

---

<a id="6-lessons-learned"></a>
## 6️⃣ الدروس المستفادة

<a id="post-incident-report-template"></a>
### نموذج تقرير ما بعد الحادث
```markdown
## ملخص الحادث
- معرّف الحادث:
- تاريخ الاكتشاف:
- تاريخ الاحتواء:
- تاريخ المعالجة:
- نوع الحادث:
- الخطورة:

## التسلسل الزمني
| التاريخ/الوقت | الحدث |
|-----------|-------|
| YYYY-MM-DD HH:MM | الاكتشاف الأولي |
| ... | ... |

## تقييم الأثر
- الأنظمة المتأثرة:
- البيانات المخترقة:
- الأثر على الأعمال:
- الأثر المالي:

## تحليل السبب الجذري
- كيف تمكّن المهاجم من الوصول؟
- ما الثغرات التي استُغلّت؟
- ما الضوابط التي أخفقت؟

## الإجراءات المتخذة
1. الاكتشاف
2. الاحتواء
3. الاستئصال
4. التعافي

## التوصيات
1. تحسينات فورية
2. تحسينات أمنية طويلة الأمد
3. تغييرات السياسات

## الدروس المستفادة
- ما الذي نجح؟
- ما الذي يمكن تحسينه؟
- ما الاحتياجات التدريبية التي حُدّدت؟
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="severity-classification"></a>
### تصنيف الخطورة

| المستوى | الوصف | زمن الاستجابة |
|-------|-------------|---------------|
| **حرج** | اختراق نشط وتسريب بيانات | فوري |
| **عالٍ** | اختراق مؤكّد | أقل من ساعة |
| **متوسط** | اختراق محتمل | أقل من 4 ساعات |
| **منخفض** | نشاط مشبوه | أقل من 24 ساعة |

<a id="common-iocs-to-collect"></a>
### مؤشرات الاختراق الشائعة المطلوب جمعها

| نوع مؤشر الاختراق | أمثلة |
|----------|----------|
| **البصمة** | MD5, SHA1, SHA256 |
| **IP** | عناوين IP للمصدر والوجهة |
| **النطاق** | نطاقات القيادة والتحكم |
| **URL** | روابط خبيثة |
| **البريد الإلكتروني** | مرسل التصيد |
| **الملف** | اسم ملف البرمجية الخبيثة |
| **السجل** | مفاتيح الاستمرارية |

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [تحليل السجلات](Log-Analysis.ar.md)
- [الكشف باستخدام SIEM](SIEM-Detection.ar.md)
- [Volatility (الأدلة الجنائية الرقمية)](../../Volatility/README.md)

---

**التالي:** [تحليل السجلات →](Log-Analysis.ar.md)

> **توضيح للرسم والأمثلة:** المراحل هي الاستعداد، ثم الاكتشاف والتحليل، فالاحتواء والاستئصال والتعافي والدروس المستفادة. تعليقات `#` في كتلة `cmd` توضيحية؛ يستخدم CMD التعليمة `rem` للتعليقات. أمثلة Windows وLinux في الكتل المختلطة تخص بيئات مختلفة، والتاريخ `01/01/2024` مثال ثابت يجب مراجعته وفق نطاق التحقيق. [توثيق rem](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/rem).
