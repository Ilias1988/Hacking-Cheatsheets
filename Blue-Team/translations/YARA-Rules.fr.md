# 🔬 Aide-mémoire : règles YARA

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** permet d'identifier et de classer des logiciels malveillants à partir de motifs textuels ou binaires.

> **Précisions de traduction :** dans la [documentation YARA](https://yara.readthedocs.io/en/stable/commandline.html), `-p` fixe le nombre de threads, et le PID d’un processus est une cible positionnelle : l’exemple `yara -p rules.yar <pid>` est donc conservé mais incorrect. De même, `-t` sélectionne une étiquette ; le délai maximal se règle avec `-a` ou `--timeout`, contrairement à l’exemple original. Les règles sont illustratives ; les chaînes non utilisées et les capacités de détection doivent être vérifiées avec la version de YARA retenue. Le guide `Malware-Analysis.md` référencé est absent du dépôt source.

---

## 📖 Structure d'une règle YARA

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
        $string2 = { 4D 5A 90 00 }  // octets hexadécimaux
        $regex1 = /password[0-9]+/i  // expression régulière
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Exemples de règles

### Détection de Mimikatz
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

### Détection de webshells
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

### Programme de téléchargement PowerShell
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

### Détection de rançongiciels
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

### Fichier PE avec des imports suspects
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

## 🔧 Modificateurs de chaînes

| Modificateur | Description |
|----------|-------------|
| `ascii` | Chaînes ASCII |
| `wide` | UTF-16 (chaînes larges Windows) |
| `nocase` | Insensible à la casse |
| `fullword` | Correspondance sur un mot entier uniquement |
| `base64` | Chaînes encodées en Base64 |
| `xor` | Chaînes encodées par XOR |

### Exemples
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = octet quelconque
```

---

## 📊 Opérateurs de condition

| Opérateur | Description |
|----------|-------------|
| `and` | ET logique |
| `or` | OU logique |
| `not` | NON logique |
| `any of` | Au moins une des chaînes |
| `all of` | Toutes les chaînes |
| `X of` | X chaînes |

### Exemples
```yara
condition:
    all of them                    // Toutes les chaînes
    any of ($s*)                   // Toute chaîne dont l'identifiant commence par $s
    2 of ($s1, $s2, $s3)          // Deux de ces trois chaînes
    #s1 > 5                        // $s1 apparaît plus de 5 fois
    @s1 < 100                      // $s1 à une position inférieure à 100
    filesize < 100KB               // Condition sur la taille du fichier
    uint16(0) == 0x5A4D            // En-tête MZ (fichier PE)
```

---

## 🛠️ Commandes YARA

```bash
# Analyser un fichier
yara rules.yar file.exe

# Analyser un répertoire
yara -r rules.yar /path/to/scan/

# Analyser avec plusieurs fichiers de règles
yara rule1.yar rule2.yar file.exe

# Afficher les chaînes correspondantes
yara -s rules.yar file.exe

# Afficher les métadonnées
yara -m rules.yar file.exe

# Analyser la mémoire d'un processus
yara -p rules.yar <pid>

# Définir un délai maximal
yara -t 60 rules.yar file.exe

# Compiler les règles
yarac rules.yar compiled.yarc
```

---

## 📚 Ressources

- [Documentation YARA](https://yara.readthedocs.io/)
- [Dépôt de règles YARA](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Aide-mémoire associés

- [Règles Sigma](./Sigma-Rules.fr.md)
- Analyse de logiciels malveillants — guide pas encore disponible
- [Recherche proactive de menaces](./Threat-Hunting.fr.md)

---

**Précédent:** [← Règles Sigma](./Sigma-Rules.fr.md)

**Retour à la vue d'ensemble:** [🛡️ Blue Team](./README.fr.md)
