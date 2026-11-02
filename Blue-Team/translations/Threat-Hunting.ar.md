<a id="-threat-hunting-cheatsheet"></a>
# 🎯 مرجع صيد التهديدات

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-threat-hunting-process"></a>
## 🎯 عملية صيد التهديدات

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HYPOTHESIS│────▶│ 2. COLLECT  │────▶│ 3. ANALYZE  │────▶│ 4. RESPOND  │
│   (Theory)   │     │   (Data)    │     │   (Hunt)    │     │   (Action)  │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOCUMENT │◀─────────────────────────┘
                          │   (Learn)   │
                          └─────────────┘
```

---

<a id="-hypothesis-examples"></a>
## 📋 أمثلة على الفرضيات

| الفئة | الفرضية |
|----------|------------|
| **الوصول الأولي** | قد يستخدم المهاجمون التصيد لإيصال البرمجيات الخبيثة |
| **الاستمرارية** | قد ينشئ المهاجمون مهام مجدولة للحفاظ على الوصول |
| **بيانات الاعتماد** | قد يفرّغ المهاجمون ذاكرة LSASS لجمع بيانات الاعتماد |
| **الحركة الجانبية** | قد يستخدم المهاجمون RDP للحركة الجانبية |
| **تسريب البيانات** | قد يستخدم المهاجمون أنفاق DNS لتسريب البيانات |

---

<a id="-hunting-techniques"></a>
## 🔍 تقنيات صيد التهديدات

<a id="hunt-for-persistence"></a>
### البحث عن آليات الاستمرارية

<a id="windows-registry-run-keys"></a>
#### مفاتيح Run في سجل Windows
```powershell
# الاستعلام عن جميع مفاتيح Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

<a id="scheduled-tasks"></a>
#### المهام المجدولة
```powershell
# سرد جميع المهام المجدولة
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# البحث عن مهام مشبوهة لا تتبع Microsoft
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

<a id="services"></a>
#### الخدمات
```powershell
# الخدمات غير الموقّعة
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

<a id="hunt-for-lateral-movement"></a>
### البحث عن الحركة الجانبية

<a id="rdp-connections"></a>
#### اتصالات RDP
```powershell
# جلسات RDP (الحدث 4624، النوع 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

<a id="network-connections"></a>
#### اتصالات الشبكة
```powershell
# الاتصالات الحالية
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# مع أسماء العمليات
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

<a id="hunt-for-credential-access"></a>
### البحث عن الوصول إلى بيانات الاعتماد

<a id="lsass-access"></a>
#### الوصول إلى LSASS
```powershell
# حدث Sysmon رقم 10: الوصول إلى LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

<a id="hunt-for-execution"></a>
### البحث عن التنفيذ

<a id="encoded-powershell"></a>
#### PowerShell مرمّز
```powershell
# PowerShell مع ترميز الأوامر
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

<a id="suspicious-parent-child"></a>
#### علاقات مشبوهة بين العمليات الأم والتابعة
```powershell
# إنشاء Office لعمليات cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

<a id="-linux-threat-hunting"></a>
## 🐧 صيد التهديدات على Linux

<a id="persistence"></a>
### الاستمرارية
```bash
# مهام Cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# مفاتيح SSH المصرّح بها
find / -name "authorized_keys" 2>/dev/null

# برامج بدء التشغيل النصية
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# ملفات تنفيذية تحمل SUID
find / -perm -4000 -type f 2>/dev/null
```

<a id="network"></a>
### الشبكة
```bash
# منافذ الاستماع
ss -tulnp
netstat -tulnp

# الاتصالات القائمة
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# نشاط DNS غير معتاد
tcpdump -i any port 53 -w dns.pcap
```

<a id="processes"></a>
### العمليات
```bash
# جميع العمليات مع سطر الأوامر الكامل
ps auxwww

# شجرة العمليات
pstree -p

# العمليات المخفية
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# الملفات التنفيذية المعدلة حديثًا
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

<a id="-ioc-hunting"></a>
## 📊 البحث عن مؤشرات الاختراق (IOC)

<a id="hash-hunting"></a>
### البحث باستخدام البصمات
```powershell
# حساب البصمات
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# البحث عن بصمة خبيثة معروفة
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

<a id="ipdomain-hunting"></a>
### البحث حسب عنوان IP أو النطاق
```powershell
# ذاكرة DNS المؤقتة
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# اتصال بعنوان IP خبيث معروف
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **Velociraptor** | صيد التهديدات عبر عدد كبير من الأجهزة الطرفية |
| **OSQuery** | استعلامات على الأجهزة الطرفية بلغة SQL |
| **KAPE** | جمع بيانات الفرز الأولي |
| **Chainsaw** | تحليل سريع لسجلات Windows |
| **DeepBlueCLI** | صيد التهديدات باستخدام PowerShell |

---

<a id="-quick-hunt-checklist"></a>
## 📋 قائمة مختصرة لصيد التهديدات

```markdown
□ فحص مواضع التشغيل التلقائي (السجل والخدمات والمهام)
□ مراجعة العمليات المنشأة حديثًا (الحدث 4688 وSysmon 1)
□ تحليل اتصالات الشبكة
□ البحث عن أوامر مرمّزة
□ البحث عن الوصول إلى LSASS
□ فحص العلاقات غير المعتادة بين العمليات الأم والتابعة
□ مراجعة سجلات PowerShell
□ البحث عن مؤشرات الحركة الجانبية
```

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [تحليل السجلات](Log-Analysis.ar.md)
- [الكشف باستخدام SIEM](SIEM-Detection.ar.md)
- [قواعد Sigma](Sigma-Rules.ar.md)

---

**السابق:** [← الكشف باستخدام SIEM](SIEM-Detection.ar.md)

**التالي:** [قواعد Sigma →](Sigma-Rules.ar.md)

> **شرح الرسم:** تبدأ العملية بفرضية، ثم جمع البيانات وتحليلها بحثًا عن التهديد، فالاستجابة والتوثيق واستخلاص الدروس. مؤشرات الاختراق (IOCs) تشمل البصمات والعناوين والنطاقات؛ وTTPs تعني التكتيكات والتقنيات والإجراءات.
