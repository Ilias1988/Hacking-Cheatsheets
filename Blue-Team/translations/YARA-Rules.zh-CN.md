# 🔬 YARA 规则速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

> **译注：** 原文命令保留以便对照，但其中 `-p` 是线程数量参数，不是进程扫描开关；进程 PID 应作为扫描目标。`-t` 用于选择规则标签，超时参数为 `-a`／`--timeout`，参见 [YARA 官方命令行文档](https://yara.readthedocs.io/en/stable/commandline.html)。条件运算示例列出的是独立写法，不能把整段当成一个有效条件。

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** 是一种基于文本或二进制特征模式识别和分类恶意软件的工具。

---

## 📖 YARA 规则结构

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
        $string2 = { 4D 5A 90 00 }  // 十六进制
        $regex1 = /password[0-9]+/i  // 正则表达式
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 规则示例

### Mimikatz 检测
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

### Cobalt Strike Beacon
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

### Webshell（网页后门）检测
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

### PowerShell 下载器
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

### 勒索软件检测
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

### 包含可疑导入项的 PE 文件
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

## 🔧 字符串修饰符

| 修饰符 | 说明 |
|----------|-------------|
| `ascii` | ASCII 字符串 |
| `wide` | UTF-16（Windows 宽字符串） |
| `nocase` | 不区分大小写 |
| `fullword` | 仅匹配完整单词 |
| `base64` | 经过 Base64 编码的字符串 |
| `xor` | 经过 XOR 编码的字符串 |

### 示例
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = 通配符
```

---

## 📊 条件运算符

| 运算符 | 说明 |
|----------|-------------|
| `and` | 逻辑与 |
| `or` | 逻辑或 |
| `not` | 逻辑非 |
| `any of` | 任意一个字符串 |
| `all of` | 所有字符串 |
| `X of` | X 个字符串 |

### 示例
```yara
condition:
    all of them                    // 所有字符串
    any of ($s*)                   // 标识符以 $s 开头的任意字符串
    2 of ($s1, $s2, $s3)          // 这三个字符串中的任意两个
    #s1 > 5                        // $s1 出现超过 5 次
    @s1 < 100                      // $s1 出现位置的偏移小于 100
    filesize < 100KB               // 文件大小条件
    uint16(0) == 0x5A4D            // MZ 文件头（PE 文件）
```

---

## 🛠️ YARA 命令

```bash
# 扫描文件
yara rules.yar file.exe

# 扫描目录
yara -r rules.yar /path/to/scan/

# 使用多个规则文件扫描
yara rule1.yar rule2.yar file.exe

# 显示匹配的字符串
yara -s rules.yar file.exe

# 显示元数据
yara -m rules.yar file.exe

# 扫描进程内存
yara -p rules.yar <pid>

# 设置超时时间
yara -t 60 rules.yar file.exe

# 编译规则
yarac rules.yar compiled.yarc
```

---

## 📚 参考资源

- [YARA 文档](https://yara.readthedocs.io/)
- [YARA 规则库](https://github.com/Yara-Rules/rules)
- [VirusTotal 狩猎ing](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 相关速查表

- [Sigma 规则](./Sigma-Rules.zh-CN.md)
- 恶意软件分析（原仓库尚未提供 `Malware-Analysis.md`）
- [威胁狩猎](./Threat-Hunting.zh-CN.md)

---

**上一篇:** [← Sigma 规则](./Sigma-Rules.zh-CN.md)

**返回概览:** [🛡️ 蓝队](./README.zh-CN.md)
