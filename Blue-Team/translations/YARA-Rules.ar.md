<a id="-yara-rules-cheatsheet"></a>
# 🔬 مرجع قواعد YARA

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** أداة للتعرّف على البرمجيات الخبيثة وتصنيفها اعتمادًا على أنماط نصية أو ثنائية.

---

<a id="-yara-rule-structure"></a>
## 📖 بنية قاعدة YARA

```yara
rule RuleName : tag1 tag2
{
    meta:
        author = "Your Name"
        description = "Description of the rule"
        date = "2024-01-15"
        reference = "https://example.com"
        hash = "abc123..."
        
    strings:
        $string1 = "malware string"
        $string2 = { 4D 5A 90 00 }  // قيم سداسية عشرية
        $regex1 = /password[0-9]+/i  // تعبير نمطي
        
    condition:
        $string1 or $string2
}
```

---

<a id="-rule-examples"></a>
## 🎯 أمثلة على القواعد

<a id="mimikatz-detection"></a>
### اكتشاف Mimikatz
```yara
rule Mimikatz
{
    meta:
        author = "Security Team"
        description = "Detects Mimikatz tool"
        
    strings:
        $s1 = "sekurlsa::" ascii wide
        $s2 = "kerberos::" ascii wide
        $s3 = "lsadump::" ascii wide
        $s4 = "privilege::debug" ascii wide
        $s5 = "mimikatz" ascii wide nocase
        $s6 = "gentilkiwi" ascii wide
        
    condition:
        uint16(0) == 0x5A4D and 3 of ($s*)
}
```

<a id="cobalt-strike-beacon"></a>
### زرعة Beacon من Cobalt Strike
```yara
rule CobaltStrike_Beacon
{
    meta:
        description = "Detects Cobalt Strike Beacon"
        
    strings:
        $s1 = "%02d/%02d/%02d %02d:%02d:%02d" ascii
        $s2 = "beacon.dll" ascii
        $s3 = "ReflectiveLoader" ascii
        $pipe1 = "\\\\.\\pipe\\msagent_" ascii
        $pipe2 = "\\\\.\\pipe\\MSSE-" ascii
        
    condition:
        uint16(0) == 0x5A4D and (2 of ($s*) or any of ($pipe*))
}
```

<a id="webshell-detection"></a>
### اكتشاف Webshell، وهي صدفة أوامر عبر الويب
```yara
rule Webshell_Generic
{
    meta:
        description = "Detects generic webshells"
        
    strings:
        $php1 = "<?php" ascii nocase
        $asp1 = "<%@ " ascii nocase
        
        $eval1 = "eval(" ascii nocase
        $eval2 = "assert(" ascii nocase
        $exec1 = "system(" ascii nocase
        $exec2 = "shell_exec(" ascii nocase
        $exec3 = "passthru(" ascii nocase
        $exec4 = "exec(" ascii nocase
        
        $b64 = "base64_decode" ascii nocase
        $input = "$_REQUEST" ascii nocase
        $input2 = "$_POST" ascii nocase
        $input3 = "$_GET" ascii nocase
        
    condition:
        ($php1 or $asp1) and 
        any of ($eval*) and 
        ($b64 or any of ($input*))
}
```

<a id="powershell-downloader"></a>
### أداة تنزيل باستخدام PowerShell
```yara
rule PowerShell_Downloader
{
    meta:
        description = "Detects PowerShell download cradles"
        
    strings:
        $ps = "powershell" ascii nocase
        
        $dl1 = "DownloadString" ascii nocase
        $dl2 = "DownloadFile" ascii nocase
        $dl3 = "DownloadData" ascii nocase
        $dl4 = "Net.WebClient" ascii nocase
        $dl5 = "Invoke-WebRequest" ascii nocase
        $dl6 = "wget" ascii nocase
        $dl7 = "curl" ascii nocase
        $dl8 = "IWR" ascii nocase
        
        $exec1 = "IEX" ascii nocase
        $exec2 = "Invoke-Expression" ascii nocase
        $exec3 = "-enc" ascii nocase
        $exec4 = "-EncodedCommand" ascii nocase
        
    condition:
        $ps and (any of ($dl*)) and (any of ($exec*))
}
```

<a id="ransomware-detection"></a>
### اكتشاف برمجيات الفدية
```yara
rule Ransomware_Generic
{
    meta:
        description = "Detects generic ransomware patterns"
        
    strings:
        $note1 = "your files have been encrypted" ascii wide nocase
        $note2 = "bitcoin" ascii wide nocase
        $note3 = "decrypt" ascii wide nocase
        $note4 = "ransom" ascii wide nocase
        $note5 = ".onion" ascii wide
        
        $ext1 = ".encrypted" ascii wide
        $ext2 = ".locked" ascii wide
        $ext3 = ".crypto" ascii wide
        
        $api1 = "CryptEncrypt" ascii
        $api2 = "CryptGenKey" ascii
        $api3 = "CryptAcquireContext" ascii
        
    condition:
        uint16(0) == 0x5A4D and
        (2 of ($note*) or any of ($ext*)) and
        2 of ($api*)
}
```

<a id="pe-file-with-suspicious-imports"></a>
### ملف PE يستورد دوال مشبوهة
```yara
rule Suspicious_PE_Imports
{
    meta:
        description = "PE with suspicious imports"
        
    strings:
        $import1 = "VirtualAllocEx" ascii
        $import2 = "WriteProcessMemory" ascii
        $import3 = "CreateRemoteThread" ascii
        $import4 = "NtUnmapViewOfSection" ascii
        $import5 = "SetThreadContext" ascii
        
    condition:
        uint16(0) == 0x5A4D and 3 of them
}
```

---

<a id="-string-modifiers"></a>
## 🔧 معدّلات السلاسل النصية

| المعدّل | الوصف |
|----------|-------------|
| `ascii` | سلاسل ASCII |
| `wide` | UTF-16 (سلاسل Windows العريضة) |
| `nocase` | دون تمييز حالة الأحرف |
| `fullword` | المطابقة بوصفها كلمة كاملة فقط |
| `base64` | سلاسل مرمّزة بـ Base64 |
| `xor` | سلاسل مرمّزة باستخدام XOR |

<a id="examples"></a>
### أمثلة
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? محرف بديل
```

---

<a id="-condition-operators"></a>
## 📊 عوامل الشروط

| العامل | الوصف |
|----------|-------------|
| `and` | و منطقي |
| `or` | أو منطقي |
| `not` | نفي منطقي |
| `any of` | أي سلسلة من السلاسل |
| `all of` | جميع السلاسل |
| `X of` | عدد X من السلاسل |

<a id="examples-1"></a>
### أمثلة
```yara
condition:
    all of them                    // جميع السلاسل
    any of ($s*)                   // أي سلسلة يبدأ اسمها بـ $s
    2 of ($s1, $s2, $s3)          // اثنتان من هذه السلاسل الثلاث
    #s1 > 5                        // تظهر $s1 أكثر من 5 مرات
    @s1 < 100                      // تظهر $s1 عند إزاحة أقل من 100
    filesize < 100KB               // شرط حجم الملف
    uint16(0) == 0x5A4D            // ترويسة MZ (ملف PE)
```

---

<a id="-yara-commands"></a>
## 🛠️ أوامر YARA

```bash
# فحص ملف
yara rules.yar file.exe

# فحص مجلد
yara -r rules.yar /path/to/scan/

# الفحص باستخدام ملفات قواعد متعددة
yara rule1.yar rule2.yar file.exe

# عرض السلاسل المطابقة
yara -s rules.yar file.exe

# عرض البيانات الوصفية
yara -m rules.yar file.exe

# فحص ذاكرة عملية
yara -p rules.yar <pid>

# ضبط المهلة
yara -t 60 rules.yar file.exe

# تجميع القواعد
yarac rules.yar compiled.yarc
```

---

<a id="-resources"></a>
## 📚 الموارد

- [وثائق YARA](https://yara.readthedocs.io/)
- [قواعد YARA Repository](https://github.com/Yara-Rules/rules)
- [صيد التهديدات عبر VirusTotal](https://www.virustotal.com/gui/hunting-overview)

---

<a id="-related-cheatsheets"></a>
## 🔗 مراجع ذات صلة

- [قواعد Sigma](Sigma-Rules.ar.md)
- تحليل البرمجيات الخبيثة — الدليل غير متاح في هذا المستودع (غير متاح في النسخة الأصلية)
- [صيد التهديدات](Threat-Hunting.ar.md)

---

**السابق:** [← قواعد Sigma](Sigma-Rules.ar.md)

**العودة إلى النظرة العامة:** [🛡️ Blue Team](README.ar.md)

> **تصحيح لأمثلة سطر الأوامر:** أُبقيت الأوامر الأصلية للمقارنة. الخيار `-p` يحدد عدد خيوط الفحص وليس تفعيل فحص الذاكرة؛ لفحص عملية يُمرر PID بوصفه الهدف. الخيار `-t` يرشّح القواعد حسب الوسم، أما مهلة الفحص فخيارها `-a` أو `--timeout`. أسطر أمثلة الشروط تعرض بدائل مستقلة، فلا تُجمع كما هي في شرط واحد. [توثيق YARA الرسمي](https://yara.readthedocs.io/en/stable/commandline.html).
