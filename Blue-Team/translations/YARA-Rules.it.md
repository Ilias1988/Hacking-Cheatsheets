# 🔬 YARA Rules Cheatsheet

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** è uno strumento per identificare e classificare malware basandosi su pattern testuali o binari.

---

## 📖 Struttura di una YARA Rule

```yara
rule NomeRegola : tag1 tag2
{
    meta:
        author = "Il tuo nome"
        description = "Descrizione della regola"
        date = "2024-01-15"
        reference = "https://example.com"
        hash = "abc123..."
        
    strings:
        $string1 = "malware string"
        $string2 = { 4D 5A 90 00 }  // hex
        $regex1 = /password[0-9]+/i  // regex
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Esempi di Regole

### Rilevamento Mimikatz
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

### Rilevamento Webshell
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

### Downloader di PowerShell
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

### Rilevamento Ransomware
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

### PE File con Import Sospetti
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

## 🔧 Modificatori di Stringa

| Modificatore | Descrizione | Nota |
|--------------|-------------|------|
| `ascii` | Stringhe ASCII standard | Default |
| `wide` | Stringhe UTF-16 | Usate in Windows (es. nomi servizi) |
| `nocase` | Case insensitive | Ignora maiuscole/minuscole |
| `fullword` | Match parola intera | Evita match parziali (es. "win" in "windows") |
| `base64` | Stringhe in base64 | Calcola automaticamente i 3 possibili offset |
| `xor` | Stringhe XORed | Cerca con chiavi a singolo byte (0x00-0xFF) |
| `private` | Stringa privata | Non mostra il match nell'output di YARA |

### Esempi
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = wildcard
```

---

## 📊 Operatori di Condizione

| Operatore | Descrizione |
|-----------|-------------|
| `and` | AND logico |
| `or` | OR logico |
| `not` | NOT logico |
| `any of` | Qualsiasi delle stringhe |
| `all of` | Tutte le stringhe |
| `X of` | X numero di stringhe |

### Esempi
```yara
condition:
    all of them                    // Tutte le stringhe
    any of ($s*)                   // Qualsiasi stringa che inizia con $s
    2 of ($s1, $s2, $s3)           // 2 di queste 3
    #s1 > 5                        // $s1 appare più di 5 volte
    @s1 < 100                      // $s1 a offset minore di 100
    filesize < 100KB               // Condizione sulla dimensione del file
    uint16(0) == 0x5A4D            // Identifica file eseguibili Windows (PE)
```

---

## 🛠️ Comandi YARA

```bash
# Scansiona file
yara rules.yar file.exe

# Scansiona directory
yara -r rules.yar /percorso/da/scansionare/

# Scansiona con più file di regole
yara rule1.yar rule2.yar file.exe

# Mostra stringhe corrispondenti
yara -s rules.yar file.exe

# Mostra metadata
yara -m rules.yar file.exe

# Scansiona memoria di processo
yara -p rules.yar <pid>

# Imposta timeout
yara -t 60 rules.yar file.exe

# Compila regole
yarac rules.yar compiled.yarc
```

---

## 📚 Risorse

- [Documentazione YARA](https://yara.readthedocs.io/)
- [YARA Rules Repository](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Cheatsheet Correlate

- [Sigma Rules](./Sigma-Rules.it.md)
- [Malware Analysis](./Malware-Analysis.it.md)
- [Threat Hunting](./Threat-Hunting.it.md)

---

**Precedente:** [← Sigma Rules](./Sigma-Rules.it.md)

**Torna all'Indice:** [🛡️ Blue Team](./README.it.md)
