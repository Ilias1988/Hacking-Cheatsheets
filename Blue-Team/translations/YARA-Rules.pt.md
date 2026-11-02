# 🔬 Guia de referência de regras YARA

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** é uma ferramenta para identificar e classificar malware com base em padrões textuais ou binários.

> **Notas de versão:** Os metadados e cadeias literais dos exemplos foram preservados em inglês. Na [CLI documentada do YARA](https://yara.readthedocs.io/en/stable/commandline.html), `-p` define o número de threads, não seleciona um processo; o PID é o alvo posicional. `-t` filtra por tag; o tempo limite usa `-a`/`--timeout`. Por isso, os dois exemplos correspondentes do original não executam a função descrita. O bloco de operadores mostra condições alternativas, que devem ser avaliadas separadamente. As regras são heurísticas de exemplo, não deteções universais.

---

## 📖 Estrutura de uma regra YARA

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
        $string2 = { 4D 5A 90 00 }  // hexadecimal
        $regex1 = /password[0-9]+/i  // expressão regular
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Exemplos de regras

### Deteção de Mimikatz
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

### Deteção de webshells (shells acessíveis pela web)
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

### Downloader em PowerShell
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

### Deteção de ransomware
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

### Ficheiro PE com importações suspeitas
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

## 🔧 Modificadores de cadeias de caracteres

| Modificador | Descrição |
|----------|-------------|
| `ascii` | Cadeias de caracteres ASCII |
| `wide` | UTF-16 (cadeias wide do Windows) |
| `nocase` | Sem distinção entre maiúsculas e minúsculas |
| `fullword` | Corresponder apenas à palavra completa |
| `base64` | Cadeias codificadas em Base64 |
| `xor` | Cadeias codificadas com XOR |

### Exemplos
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = curinga (wildcard)
```

---

## 📊 Operadores de condições

| Operador | Descrição |
|----------|-------------|
| `and` | E lógico |
| `or` | OU lógico |
| `not` | NÃO lógico |
| `any of` | Qualquer uma das cadeias |
| `all of` | Todas as cadeias |
| `X of` | X cadeias de caracteres |

### Exemplos
```yara
condition:
    all of them                    // Todas as cadeias
    any of ($s*)                   // Qualquer identificador de cadeia que comece por $s
    2 of ($s1, $s2, $s3)          // 2 destas 3
    #s1 > 5                        // $s1 aparece mais de 5 vezes
    @s1 < 100                      // $s1 num deslocamento inferior a 100
    filesize < 100KB               // Condição sobre o tamanho do ficheiro
    uint16(0) == 0x5A4D            // Cabeçalho MZ (ficheiro PE)
```

---

## 🛠️ Comandos YARA

```bash
# Analisar um ficheiro
yara rules.yar file.exe

# Analisar um diretório
yara -r rules.yar /path/to/scan/

# Analisar com vários ficheiros de regras
yara rule1.yar rule2.yar file.exe

# Mostrar as cadeias correspondentes
yara -s rules.yar file.exe

# Mostrar os metadados
yara -m rules.yar file.exe

# Analisar a memória de um processo
yara -p rules.yar <pid>

# Definir o tempo limite
yara -t 60 rules.yar file.exe

# Compilar as regras
yarac rules.yar compiled.yarc
```

---

## 📚 Recursos

- [Documentação do YARA](https://yara.readthedocs.io/)
- [Repositório de regras YARA](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Guias de referência relacionados

- [Regras Sigma](Sigma-Rules.pt.md)
- Ainda não disponível no original
- [Threat hunting (procura proativa de ameaças)](Threat-Hunting.pt.md)

---

**Anterior:** [← Regras Sigma](Sigma-Rules.pt.md)

**Voltar à visão geral:** [🛡️ Blue Team](README.pt.md)
