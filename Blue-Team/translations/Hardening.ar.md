<a id="-system-hardening-cheatsheet"></a>
# 🔒 مرجع تحصين الأنظمة

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-windows-hardening"></a>
## 🖥️ تحصين Windows

<a id="account-security"></a>
### أمن الحسابات
```powershell
# تعطيل حساب المسؤول المحلي
net user Administrator /active:no

# إعادة تسمية حساب المسؤول
wmic useraccount where name='Administrator' rename 'NewAdminName'

# ضبط سياسة كلمات مرور قوية (GPO أو secpol.msc)
# الحد الأدنى للطول: 14 محرفًا
# التعقيد: مفعّل
# الحد الأقصى للعمر: 60 يومًا

# تعطيل حساب الضيف
net user Guest /active:no

# تقييد استخدام رمز وصول المسؤول (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

<a id="disable-unnecessary-services"></a>
### تعطيل الخدمات غير الضرورية
```powershell
# تعطيل خدمة Remote Registry
sc config RemoteRegistry start= disabled

# تعطيل إدارة Windows عن بُعد عند عدم الحاجة إليها
sc config WinRM start= disabled

# تعطيل SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# تعطيل NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

<a id="firewall-configuration"></a>
### إعداد جدار الحماية
```powershell
# تفعيل جدار حماية Windows
netsh advfirewall set allprofiles state on

# حظر الاتصالات الواردة افتراضيًا
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# تفعيل التسجيل
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

<a id="powershell-security"></a>
### أمن PowerShell
```powershell
# تفعيل وضع اللغة المقيّدة (Constrained Language Mode)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# تفعيل تسجيل كتل البرامج النصية
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# تفعيل تسجيل الوحدات
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

<a id="audit-policy"></a>
### سياسة التدقيق
```powershell
# تفعيل تسجيل سطر الأوامر عند إنشاء العمليات
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# إعداد سياسات التدقيق
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

<a id="windows-defender"></a>
### Windows Defender
```powershell
# تفعيل الحماية في الوقت الفعلي
Set-MpPreference -DisableRealtimeMonitoring $false

# تفعيل الحماية السحابية
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# تفعيل قواعد تقليل سطح الهجوم (ASR)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

<a id="lsass-protection"></a>
### حماية LSASS
```powershell
# تفعيل Credential Guard (يشير الأصل إلى اشتراط TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# تشغيل LSASS بوصفها عملية محمية خفيفة (PPL)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

<a id="-linux-hardening"></a>
## 🐧 تحصين Linux

<a id="user--access-control"></a>
### إدارة المستخدمين والتحكم في الوصول
```bash
# تعطيل تسجيل الدخول بحساب root
sudo passwd -l root

# ضبط تعقيد كلمات المرور (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# مدة صلاحية كلمات المرور (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# تعطيل الحسابات غير المستخدمة
usermod -s /sbin/nologin username
```

<a id="ssh-hardening-etcsshsshd_config"></a>
### تحصين SSH (/etc/ssh/sshd_config)
```bash
# تعطيل تسجيل الدخول بحساب root
PermitRootLogin no

# تعطيل المصادقة بكلمة المرور واستخدام المفاتيح
PasswordAuthentication no
PubkeyAuthentication yes

# تغيير المنفذ الافتراضي
Port 2222

# تقييد المستخدمين المسموح لهم
AllowUsers admin operator

# استخدام الإصدار 2 فقط
Protocol 2

# منع كلمات المرور الفارغة
PermitEmptyPasswords no

# إعدادات المهلة
ClientAliveInterval 300
ClientAliveCountMax 2

# إعادة تشغيل SSH
sudo systemctl restart sshd
```

<a id="firewall-iptablesnftables"></a>
### جدار الحماية (iptables/nftables)
```bash
# سياسة الحظر الافتراضي
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# السماح باتصالات الواجهة المحلية loopback
iptables -A INPUT -i lo -j ACCEPT

# السماح بالاتصالات القائمة
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# السماح باتصالات SSH عبر منفذ مخصص
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# حفظ القواعد
iptables-save > /etc/iptables/rules.v4
```

<a id="file-permissions"></a>
### أذونات الملفات
```bash
# تأمين الملفات الحساسة
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# البحث عن ملفات SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# إزالة بت SUID غير الضروري
chmod u-s /usr/bin/unnecessary_binary
```

<a id="disable-unused-services"></a>
### تعطيل الخدمات غير المستخدمة
```bash
# سرد الخدمات المفعّلة
systemctl list-unit-files --state=enabled

# تعطيل الخدمات غير الضرورية
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# تعطيل IPv6 عند عدم الحاجة إليه
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

<a id="kernel-hardening-etcsysctlconf"></a>
### تحصين النواة (/etc/sysctl.conf)
```bash
# تعطيل تمرير حزم IP
net.ipv4.ip_forward = 0

# تعطيل التوجيه من المصدر
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# تفعيل SYN cookies
net.ipv4.tcp_syncookies = 1

# تعطيل إعادة التوجيه عبر ICMP
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# تفعيل ASLR عبر randomize_va_space (يسميه الأصل exec shield)
kernel.randomize_va_space = 2

# تطبيق التغييرات
sysctl -p
```

<a id="audit-system-auditd"></a>
### نظام التدقيق (auditd)
```bash
# تثبيت auditd
apt install auditd

# أمثلة على القواعد (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# إعادة تشغيل auditd
systemctl restart auditd
```

---

<a id="-quick-hardening-checklist"></a>
## 📊 قائمة مختصرة للتحصين

<a id="windows"></a>
### Windows
```markdown
□ تعطيل الخدمات غير الضرورية (SMBv1 وRemote Registry)
□ تفعيل جدار حماية Windows
□ إعداد سياسة قوية لكلمات المرور
□ تفعيل تسجيل PowerShell
□ إعداد سياسة التدقيق
□ تفعيل Windows Defender
□ تفعيل حماية LSASS
□ تثبيت التحديثات الأمنية
□ إزالة البرامج غير المستخدمة
```

<a id="linux"></a>
### Linux
```markdown
□ تعطيل تسجيل الدخول بحساب root
□ إعداد تحصين SSH
□ تفعيل جدار الحماية (iptables/ufw)
□ ضبط أذونات الملفات المناسبة
□ Disable unused services
□ إعداد تحصين النواة
□ تفعيل auditd
□ تثبيت التحديثات الأمنية
□ إعداد fail2ban
```

---

<a id="-resources"></a>
## 📚 الموارد

- [معايير CIS](https://www.cisecurity.org/cis-benchmarks/)
- [إرشادات NIST](https://www.nist.gov/cyberframework)
- [خطوط الأساس الأمنية من Microsoft](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [الاستجابة للحوادث](Incident-Response.ar.md)
- الدفاع الشبكي — الدليل غير متاح في هذا المستودع (غير متاح في النسخة الأصلية)

---

**السابق:** ← الدفاع الشبكي — الدليل غير متاح في هذا المستودع (غير متاح في النسخة الأصلية)

**العودة إلى النظرة العامة:** [🛡️ Blue Team](README.ar.md)

> **توضيحات تقنية:** يستخدم Windows PowerShell الاسم المختصر `sc` للأمر `Set-Content`؛ المقصود في أمثلة إدارة الخدمات هو برنامج `sc.exe`. كما أن ضبط `ConstrainedLanguage` داخل جلسة واحدة مفيد للتجربة، أما فرض القيود فيعتمد على سياسة تحكم بالتطبيقات أو إعداد جلسة. TPM موصى به لـ Credential Guard وليس شرطًا مطلقًا كما يوحي تعليق الأصل. الإعداد `kernel.randomize_va_space = 2` يفعّل عشوائية فضاء العناوين بما فيها الكومة (ASLR)، وليس ميزة باسم Exec Shield. [اختصار sc](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-5.1#notes)، [أوضاع اللغة](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes#setting-the-language-mode)، [متطلبات Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/#hardware-and-software-requirements)، [توثيق النواة](https://docs.kernel.org/admin-guide/sysctl/kernel.html#randomize-va-space).
