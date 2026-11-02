# 🔬 Spickzettel zu YARA-Regeln

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](./YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** ist ein Werkzeug zum Erkennen und Klassifizieren von Malware anhand von Text- oder Binärmustern.

---

> **Korrektur zum CLI-Beispiel:** `-p` gibt bei YARA die Anzahl der Threads an, nicht die Untersuchung von Prozessspeicher. Dafür wird die Prozess-ID als Ziel angegeben, etwa `yara rules.yar <pid>`. Die Originalzeile bleibt zur Nachvollziehbarkeit erhalten. Mehrere Quellregeldateien werden unterstützt; bei vorkompilierten Regeln gelten andere Vorgaben. [YARA-Befehlszeile](https://yara.readthedocs.io/en/stable/commandline.html).

> **Grenzen der Regelbeispiele:** Die erste Vorlage definiert `$regex1`, verwendet die Kennung aber nicht in der Bedingung; auch die Webshell-Regel enthält unbenutzte `$exec*`-Strings. Solche nicht referenzierten Strings können zu Kompilierfehlern führen. Der MZ-Wert allein beweist keine gültige PE-Struktur. Die als „Imports“ bezeichnete Regel sucht API-Namen als Zeichenfolgen, ohne die Importtabelle zu parsen. Alle Regeln sind Lernbeispiele mit möglichen Fehlalarmen und Erkennungslücken. Metadaten und Suchmuster im Code bleiben als wörtliche Werte erhalten. [YARA-Regelaufbau](https://yara.readthedocs.io/en/stable/writingrules.html).

## 📖 Aufbau einer YARA-Regel

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
        $string2 = { 4D 5A 90 00 }  // Hexadezimalmuster
        $regex1 = /password[0-9]+/i  // Regulärer Ausdruck
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Regelbeispiele

### Mimikatz erkennen
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

### Webshells erkennen
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

### PowerShell-Downloader
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

### Ransomware erkennen
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

### PE-Datei mit verdächtigen Imports
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

## 🔧 Zeichenfolgenmodifikatoren

| Modifikator | Beschreibung |
|----------|-------------|
| `ascii` | ASCII-Zeichenfolgen |
| `wide` | UTF-16 (Windows-Wide-Zeichenfolgen) |
| `nocase` | Groß- und Kleinschreibung ignorieren |
| `fullword` | Nur vollständige Wörter erfassen |
| `base64` | Base64-kodierte Zeichenfolgen |
| `xor` | XOR-kodierte Zeichenfolgen |

### Beispiele
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = Platzhalter
```

---

## 📊 Operatoren für Bedingungen

| Operator | Beschreibung |
|----------|-------------|
| `and` | Logisches UND |
| `or` | Logisches ODER |
| `not` | Logisches NICHT |
| `any of` | Mindestens eine der Zeichenfolgen |
| `all of` | Alle Zeichenfolgen |
| `X of` | X Zeichenfolgen |

### Beispiele
```yara
condition:
    all of them                    // Alle Zeichenfolgen
    any of ($s*)                   // Mindestens eine Zeichenfolge mit einer Kennung, die mit $s beginnt
    2 of ($s1, $s2, $s3)          // 2 dieser 3 Zeichenfolgen
    #s1 > 5                        // $s1 kommt häufiger als 5-mal vor
    @s1 < 100                      // $s1 liegt an einem Offset unter 100
    filesize < 100KB               // Bedingung für die Dateigröße
    uint16(0) == 0x5A4D            // MZ-Header (PE-Datei)
```

---

## 🛠️ YARA-Befehle

```bash
# Datei untersuchen
yara rules.yar file.exe

# Verzeichnis untersuchen
yara -r rules.yar /path/to/scan/

# Mit mehreren Regeldateien untersuchen
yara rule1.yar rule2.yar file.exe

# Übereinstimmende Zeichenfolgen anzeigen
yara -s rules.yar file.exe

# Metadaten anzeigen
yara -m rules.yar file.exe

# Prozessspeicher untersuchen
yara -p rules.yar <pid>

# Zeitlimit festlegen
yara -t 60 rules.yar file.exe

# Regeln kompilieren
yarac rules.yar compiled.yarc
```

---

## 📚 Weiterführende Quellen

- [YARA-Dokumentation](https://yara.readthedocs.io/)
- [YARA-Regelrepository](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Verwandte Spickzettel

- [Sigma-Regeln](./Sigma-Rules.de.md)
- Malware-Analyse (im englischen Original verlinkt; Quelldatei fehlt)
- [Threat Hunting (proaktive Bedrohungssuche)](./Threat-Hunting.de.md)

---

**Zurück:** [← Sigma-Regeln](./Sigma-Rules.de.md)

**Zurück zur Übersicht:** [🛡️ Blue Team](./README.de.md)
