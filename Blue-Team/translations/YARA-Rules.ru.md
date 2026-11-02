# Справочник по правилам YARA

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** — инструмент обнаружения и классификации вредоносного ПО по текстовым или двоичным шаблонам.

---

## Структура правила YARA

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
        $string2 = { 4D 5A 90 00 }  // шестнадцатеричные байты
        $regex1 = /password[0-9]+/i  // регулярное выражение
        
    condition:
        $string1 or $string2
}
```

---

## Примеры правил

### Обнаружение Mimikatz
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

### Обнаружение веб-шеллов
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

### Загрузчик PowerShell
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

### Обнаружение программ-вымогателей
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

### PE-файл с подозрительными импортами
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

## Модификаторы строк

| Модификатор | Описание |
|----------|-------------|
| `ascii` | Строки ASCII |
| `wide` | UTF-16 (широкие строки Windows) |
| `nocase` | Без учёта регистра |
| `fullword` | Совпадение только целого слова |
| `base64` | Строки в кодировке Base64 |
| `xor` | Строки, закодированные XOR |

### Примеры
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = подстановочный байт
```

---

## Операторы условий

| Оператор | Описание |
|----------|-------------|
| `and` | Логическое И |
| `or` | Логическое ИЛИ |
| `not` | Логическое НЕ |
| `any of` | Любая из строк |
| `all of` | Все строки |
| `X of` | X строк |

### Примеры
```yara
condition:
    all of them                    // Все строки
    any of ($s*)                   // Любая строка с идентификатором, начинающимся на $s
    2 of ($s1, $s2, $s3)          // 2 из этих 3
    #s1 > 5                        // $s1 встречается более 5 раз
    @s1 < 100                      // $s1 по смещению менее 100
    filesize < 100KB               // Условие по размеру файла
    uint16(0) == 0x5A4D            // Заголовок MZ (PE-файл)
```

---

## Команды YARA

```bash
# Проверить файл
yara rules.yar file.exe

# Проверить каталог
yara -r rules.yar /path/to/scan/

# Проверить с несколькими файлами правил
yara rule1.yar rule2.yar file.exe

# Показать совпавшие строки
yara -s rules.yar file.exe

# Показать метаданные
yara -m rules.yar file.exe

# Проверить память процесса
yara -p rules.yar <pid>

# Установить тайм-аут
yara -t 60 rules.yar file.exe

# Скомпилировать правила
yarac rules.yar compiled.yarc
```

---

## Ресурсы

- [Документация YARA](https://yara.readthedocs.io/)
- [Репозиторий правил YARA](https://github.com/Yara-Rules/rules)
- [Поиск угроз VirusTotal](https://www.virustotal.com/gui/hunting-overview)

---

## Связанные справочники

- [Правила Sigma](Sigma-Rules.ru.md)
- Анализ вредоносного ПО (исходный файл отсутствует; см. примечание в обзоре)
- [Поиск угроз](Threat-Hunting.ru.md)

---

**Предыдущий раздел:** [← Правила Sigma](Sigma-Rules.ru.md)

**К обзору:** [🛡️ Blue Team](README.ru.md)
