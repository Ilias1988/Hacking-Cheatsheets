# 🔬 Guía rápida de reglas YARA

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](YARA-Rules.tr.md) • [🇬🇷 Ελληνικά](YARA-Rules.el.md)

> **Corrección del original:** En `yara -p rules.yar <pid>`, `-p` configura el número de hilos; no selecciona el análisis de memoria. El PID puede ser el destino posicional: `yara rules.yar <pid>`. Se conserva la línea original. Véase [documentación de YARA](https://yara.readthedocs.io/en/stable/commandline.html). Las firmas son heurísticas, no identificaciones concluyentes.


```
██╗   ██╗ █████╗ ██████╗  █████╗     ██████╗ ██╗   ██╗██╗     ███████╗███████╗
╚██╗ ██╔╝██╔══██╗██╔══██╗██╔══██╗    ██╔══██╗██║   ██║██║     ██╔════╝██╔════╝
 ╚████╔╝ ███████║██████╔╝███████║    ██████╔╝██║   ██║██║     █████╗  ███████╗
  ╚██╔╝  ██╔══██║██╔══██╗██╔══██║    ██╔══██╗██║   ██║██║     ██╔══╝  ╚════██║
   ██║   ██║  ██║██║  ██║██║  ██║    ██║  ██║╚██████╔╝███████╗███████╗███████║
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚══════╝╚══════╝
```

**YARA** es una herramienta para identificar y clasificar malware a partir de patrones textuales o binarios.

---

## 📖 Estructura de una regla YARA

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
        $regex1 = /password[0-9]+/i  // expresión regular
        
    condition:
        $string1 or $string2
}
```

---

## 🎯 Ejemplos de reglas

### Detección de Mimikatz
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

### Detección de webshells
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

### Descargador de PowerShell
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

### Detección de ransomware
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

### Archivo PE con importaciones sospechosas
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

## 🔧 Modificadores de cadenas

| Modificador | Descripción |
|----------|-------------|
| `ascii` | Cadenas ASCII |
| `wide` | UTF-16 (cadenas anchas de Windows) |
| `nocase` | Sin distinguir mayúsculas y minúsculas |
| `fullword` | Coincidir únicamente con palabras completas |
| `base64` | Cadenas codificadas en Base64 |
| `xor` | Cadenas codificadas mediante XOR |

### Ejemplos
```yara
$s1 = "malware" ascii wide nocase
$s2 = "password" fullword
$s3 = "secret" base64
$s4 = "payload" xor
$hex = { 4D 5A ?? ?? 00 00 }  // ?? = comodín
```

---

## 📊 Operadores de condición

| Operador | Descripción |
|----------|-------------|
| `and` | AND lógico |
| `or` | OR lógico |
| `not` | NOT lógico |
| `any of` | Cualquiera de las cadenas |
| `all of` | Todas las cadenas |
| `X of` | X cadenas |

### Ejemplos
```yara
condition:
    all of them                    // Todas las cadenas
    any of ($s*)                   // Cualquier cadena cuyo identificador comienza por $s
    2 of ($s1, $s2, $s3)          // 2 de estas 3
    #s1 > 5                        // $s1 aparece más de 5 veces
    @s1 < 100                      // $s1 en un desplazamiento menor que 100
    filesize < 100KB               // Condición sobre el tamaño del archivo
    uint16(0) == 0x5A4D            // Cabecera MZ (archivo PE)
```

---

## 🛠️ Comandos de YARA

```bash
# Analizar un archivo
yara rules.yar file.exe

# Analizar un directorio
yara -r rules.yar /path/to/scan/

# Analizar con varios archivos de reglas
yara rule1.yar rule2.yar file.exe

# Mostrar las cadenas que coinciden
yara -s rules.yar file.exe

# Mostrar los metadatos
yara -m rules.yar file.exe

# Analizar la memoria de un proceso
yara -p rules.yar <pid>

# Establecer el tiempo de espera
yara -t 60 rules.yar file.exe

# Compilar las reglas
yarac rules.yar compiled.yarc
```

---

## 📚 Recursos

- [Documentación de YARA](https://yara.readthedocs.io/)
- [Repositorio de reglas YARA](https://github.com/Yara-Rules/rules)
- [VirusTotal Hunting](https://www.virustotal.com/gui/hunting-overview)

---

## 🔗 Guías rápidas relacionadas

- [Reglas Sigma](./Sigma-Rules.es.md)
- Análisis de malware (guía ausente en el original)
- [Búsqueda proactiva de amenazas](./Threat-Hunting.es.md)

---

**Anterior:** [← Reglas Sigma](./Sigma-Rules.es.md)

**Volver al índice:** [🛡️ Blue Team](./README.es.md)
