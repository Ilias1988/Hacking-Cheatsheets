# 🔬 YARA-regler – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../YARA-Rules.md) • [🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** er et verktøy for å identifisere og klassifisere skadevare ved hjelp av tekstmønstre eller binære mønstre.

---

## 📖 Oppbygning av en YARA-regel

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
        $string2 = { 4D 5A 90 00 }  // heksadesimalt
        $regex1 = /password[0-9]+/i  // regulært uttrykk
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Regeleksempler

### Mimikatz-deteksjon
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

### Deteksjon av webshell
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

### PowerShell-nedlaster
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

### Deteksjon av løsepengevirus
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

### PE-fil med mistenkelige importer
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

> **Presisering:** Eksemplene bruker strenger og enkle mønstre som indikatorer. For eksempel søker det siste mønsteret etter funksjonsnavn som tekst; det analyserer ikke PE-importtabellen. Treff må vurderes i sammenheng.

---

## 🔧 Strengmodifikatorer

| Modifikator | Beskrivelse |
|----------|-------------|
| `ascii` | ASCII-strenger |
| `wide` | UTF-16 (Windows-strenger med brede tegn) |
| `nocase` | Skiller ikke mellom store og små bokstaver |
| `fullword` | Treff bare som helt ord |
| `base64` | Base64-kodede strenger |
| `xor` | XOR-kodede strenger |

### Eksempler
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = jokertegn
```

---

## 📊 Betingelsesoperatorer

| Operator | Beskrivelse |
|----------|-------------|
| `and` | Logisk OG |
| `or` | Logisk ELLER |
| `not` | Logisk IKKE |
| `any of` | Minst én av strengene |
| `all of` | Alle strengene |
| `X of` | X antall strenger |

### Eksempler
```yara
condition:
    all of them                    // Alle strenger
    any of ($s*)                   // En hvilken som helst streng som begynner med $s
    2 of ($s1, $s2, $s3)          // 2 av disse 3
    #s1 > 5                        // $s1 forekommer mer enn 5 ganger
    @s1 < 100                      // $s1 ved en posisjon mindre enn 100
    filesize < 100KB               // Betingelse for filstørrelse
    uint16(0) == 0x5A4D            // MZ-hode (PE-fil)
```

---

## 🛠️ YARA-kommandoer

```bash
# Skann en fil
yara rules.yar file.exe

# Skann en katalog
yara -r rules.yar /path/to/scan/

# Skann med flere regelfiler
yara rule1.yar rule2.yar file.exe

# Vis strenger som ga treff
yara -s rules.yar file.exe

# Vis metadata
yara -m rules.yar file.exe

# Skann prosessminne (originalens syntaks; se presisering nedenfor)
yara -p rules.yar <pid>

# Angi tidsavbrudd
yara -t 60 rules.yar file.exe

# Kompiler regler
yarac rules.yar compiled.yarc
```

> **Presisering av originalen:** `-p` angir antall tråder, ikke prosessmodus. For prosessminne brukes prosess-ID som mål: `yara rules.yar <pid>`. Den opprinnelige kommandolinjen ovenfor er beholdt for sporbarhet. [YARAs kommandolinjedokumentasjon](https://yara.readthedocs.io/en/stable/commandline.html).

---

## 📚 Ressurser

- [YARA-dokumentasjon](https://yara.readthedocs.io/)
- [YARA-regelsamling](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Relaterte hurtigreferanser

- [Sigma-regler](Sigma-Rules.nb.md)
- Skadevareanalyse – kildefilen `Malware-Analysis.md` finnes ikke i denne kopien.
- [Trusseljakt](Threat-Hunting.nb.md)

---

**Forrige:** [← Sigma-regler](Sigma-Rules.nb.md)

**Tilbake til oversikten:** [🛡️ Blue Team](README.nb.md)
