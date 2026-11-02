# 🧠 Volatility - Guía rápida de análisis forense de memoria

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Framework de análisis forense de memoria
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

---

## 📋 Índice

- [¿Qué es Volatility?](#-qué-es-volatility)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Identificación de la imagen de memoria](#-identificación-de-la-imagen-de-memoria)
- [Análisis de procesos](#-análisis-de-procesos)
- [Análisis de red](#-análisis-de-red)
- [Análisis del registro](#-análisis-del-registro)
- [Análisis de malware](#-análisis-de-malware)
- [Análisis de usuarios y credenciales](#-análisis-de-usuarios-y-credenciales)
- [Análisis de archivos y DLL](#-análisis-de-archivos-y-dll)
- [Análisis de la línea temporal](#-análisis-de-la-línea-temporal)
- [Volatility 3](#-volatility-3)

---

## 🎯 ¿Qué es Volatility?

**Volatility** es un framework de análisis forense de memoria de código abierto que permite examinar volcados de RAM. Resulta fundamental para:

- 🔍 **Respuesta a incidentes** - Identificar malware activo
- 🦠 **Análisis de malware** - Extraer procesos ocultos
- 🔐 **Recuperación de credenciales** - Extraer contraseñas y hashes
- 📊 **Análisis temporal** - Reconstruir los acontecimientos
- 🕵️ **Búsqueda de amenazas** - Encontrar indicadores de compromiso

### Formatos de memoria compatibles

| Formato | Descripción |
|--------|-------------|
| Raw (.raw, .mem) | Volcado de memoria sin procesar |
| EWF (.E01) | Formato EnCase |
| AFF | Advanced Forensics Format (formato forense avanzado) |
| Volcado de fallo | Volcados de fallo de Windows |
| VMware (.vmem) | Instantáneas de VMware |
| VirtualBox (.sav) | Instantáneas de VirtualBox |
| HPAK (.hpak) | Formato FDPro |
| LiME (.lime) | Linux Memory Extractor |

---

## 🚀 Instalación

### Volatility 2 (heredado)

```bash
# Clonar el repositorio
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Instalar las dependencias
pip install pycrypto distorm3 yara-python pillow openpyxl

# Ejecutar
python vol.py -h
```

> **Versión heredada:** Volatility 2 está obsoleto y su repositorio está archivado; el bloque anterior utiliza dependencias y Python de su época. Se conserva para el análisis de laboratorios antiguos. Véase el [anuncio de Volatility Foundation](https://volatilityfoundation.org/announcing-the-official-parity-release-of-volatility-3/).

### Volatility 3 (actual)

```bash
# Instalar mediante pip
pip3 install volatility3

# O clonar el repositorio
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Ejecutar
python3 vol.py -h
vol -h  # Si se ha instalado mediante pip
```

### Descargar tablas de símbolos (Vol3)

```bash
# Símbolos de Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Símbolos de Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Símbolos de Mac
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Extraer en volatility3/symbols/
```

---

> **Símbolos y formatos:** la compatibilidad depende de la versión, los plugins y las capas de lectura disponibles. Los símbolos de Linux y macOS deben corresponder al kernel del volcado; descargar un ZIP genérico no garantiza esa coincidencia. Véase la [guía oficial de símbolos](https://volatility3.readthedocs.io/en/latest/symbol-tables.html).

## 💻 Uso básico

### Sintaxis de Volatility 2

```bash
# Sintaxis general
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Ejemplo
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Sintaxis de Volatility 3

```bash
# Sintaxis general (detección automática del perfil, según el original)
vol -f <memory_dump> <plugin>

# Ejemplo
vol -f memory.raw windows.pslist
```

### Opciones habituales

```bash
# Volatility 2
-f FILE          # Archivo del volcado de memoria
--profile=PROFILE # Perfil del sistema operativo
-h               # Ayuda
--info           # Listar los plugins disponibles

# Volatility 3
-f FILE          # Archivo del volcado de memoria
-o DIR           # Directorio de salida
-r RENDERER      # Formato de salida (pretty, json, csv)
-p PLUGIN_DIR    # Plugins adicionales
-h               # Ayuda
```

---

## 🔍 Identificación de la imagen de memoria

### Identificar el perfil (Vol2)

```bash
# Obtener los perfiles sugeridos
python vol.py -f memory.raw imageinfo

# Información más detallada
python vol.py -f memory.raw kdbgscan
```

### Identificar el sistema operativo (Vol3)

```bash
# Detección automática (Vol3 no requiere seleccionar un perfil manualmente)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Perfiles habituales (Vol2)

| OS | Perfil |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-análisis-de-procesos"></a>
## ⚙️ Análisis de procesos

### Listar los procesos

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Vista de árbol
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Procesos ocultos

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Detalles de los procesos

```bash
# Argumentos de la línea de comandos
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Variables de entorno
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Handles (identificadores de recursos abiertos) del proceso
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# DLL cargadas
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Detectar procesos ocultos

```bash
# Comparar pslist y psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Detección de process hollowing (sustitución del contenido de un proceso)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Volcar un proceso

```bash
# Volcar el ejecutable
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Volcar la memoria
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

## 🌐 Análisis de red

### Conexiones de red

```bash
# Conexiones activas (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Campos de la salida

| Campo | Descripción |
|-------|-------------|
| Offset | Desplazamiento en memoria |
| Proto | TCP/UDP |
| LocalAddr | IP y puerto locales |
| ForeignAddr | IP y puerto remotos |
| State | Estado de la conexión |
| PID | ID del proceso |
| Owner | Nombre del proceso |

### Artefactos de red

```bash
# Sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

## 📝 Análisis del registro

### Listar los hives del registro

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Volcar el registro

```bash
# Mostrar una clave concreta
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Volcar un hive a un archivo
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Claves importantes del registro

```bash
# Claves de inicio automático
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Servicios
printkey -K "SYSTEM\CurrentControlSet\Services"

# Cuentas de usuario
printkey -K "SAM\Domains\Account\Users"

# Archivos recientes
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (programas ejecutados)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

## 🦠 Análisis de malware

### Malfind (código inyectado)

```bash
# Buscar código inyectado
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Volcar a archivos
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### Hooks de la SSDT

```bash
# Tabla de descriptores de servicios del sistema
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Comparar con un sistema limpio
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Análisis de IDT/GDT

```bash
# Tabla de descriptores de interrupciones
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Tabla global de descriptores
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Análisis de controladores

```bash
# Listar los controladores
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Lista de módulos
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Módulos descargados
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Hooks IRP de controladores
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Búsqueda mediante YARA

```bash
# Buscar mediante reglas YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

> **Corrección de YARA (Vol2):** el original invierte las opciones. `-Y`/`--yara-rules` recibe una cadena o patrón y `-y`/`--yara-file` un archivo de reglas. Los comandos originales se conservan. Véase la [referencia oficial](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference-Mal).

### Hooks de API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

## 🔐 Análisis de usuarios y credenciales

### Información de usuarios

```bash
# Obtener los SID de los usuarios
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Último usuario que inició sesión
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Hashes de contraseñas

```bash
# Volcar los hashes SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Secretos LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Credenciales en caché
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Integración con Mimikatz

```bash
# Extraer credenciales (plugin mimikatz)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (integrado)
vol -f memory.raw windows.lsadump
```

### Portapapeles

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

## 📁 Análisis de archivos y DLL

### Búsqueda de archivos

```bash
# Buscar archivos en memoria
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filtrar archivos concretos
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Volcar archivos

```bash
# Volcar un archivo por su desplazamiento
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Volcar todos los archivos
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

> **Tipo de dirección:** `dumpfiles -Q` de Vol2 utiliza un desplazamiento físico; `--virtaddr` de Vol3 espera una dirección virtual. No intercambies el mismo valor sin comprobar su procedencia. Véase la [referencia de Volatility 2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference).

### Análisis de DLL

```bash
# Listar las DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Proceso concreto
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Volcar una DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Cadenas de texto en memoria

```bash
# Extraer cadenas de texto según el original
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Cadenas de texto de un proceso concreto
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

> **Corrección de strings:** el plugin de Vol2 consume un archivo de cadenas con desplazamientos previamente generado y lo relaciona con procesos; `-s strings.txt` no extrae por sí solo las cadenas del volcado. Véase la [referencia oficial](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference).

## 📅 Análisis de la línea temporal

### Creación de una línea temporal

```bash
# Crear una línea temporal
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### Análisis de la MFT

```bash
# Tabla maestra de archivos
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

## 🔄 Volatility 3

### Diferencias principales

| Función | Vol2 | Vol3 |
|---------|------|------|
| Perfil | Manual (`--profile=`) | Detección automática |
| Nombres de plugins | `pslist` | `windows.pslist` |
| Rendimiento | Más lento | Más rápido |
| Python | Python 2.7 | Python 3 |
| Salida | Solo texto | JSON, CSV, etc. |

> **Comparación histórica:** Vol2 también dispone de formatos de salida distintos de texto. En Vol3, las rutas de plugins y los sistemas soportados pueden cambiar; confirma los nombres con la ayuda instalada. Plugins como `hollowfind` y `mimikatz` pueden requerir componentes externos, y `windows.lsadump` no es una implementación completa de Mimikatz. Las diferencias de velocidad dependen del caso.

### Referencia de plugins de Vol3

```bash
# Procesos
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Red
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registro
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Malware
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Credenciales
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Archivos
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Plugins de Linux (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

## 📊 Referencia rápida

### Comandos más utilizados

| Finalidad | Comando de Vol2 | Comando de Vol3 |
|---------|--------------|--------------|
| Identificar la imagen | `imageinfo` | `windows.info` |
| Listar los procesos | `pslist` | `windows.pslist` |
| Árbol de procesos | `pstree` | `windows.pstree` |
| Procesos ocultos | `psscan` | `windows.psscan` |
| Red | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Volcar un proceso | `procdump` | `windows.pslist --dump` |
| Buscar malware | `malfind` | `windows.malfind` |
| Registro | `hivelist` | `windows.registry.hivelist` |
| Hashes | `hashdump` | `windows.hashdump` |
| Archivos | `filescan` | `windows.filescan` |
| Cadenas de texto | `strings` | - |

### Flujo de investigación

```
1. imageinfo/windows.info    → Identificar el sistema operativo
2. pslist/pstree/psscan      → Listar los procesos
3. cmdline                    → Revisar las líneas de comandos
4. netscan                    → Conexiones de red
5. malfind                    → Buscar código inyectado
6. dlllist                    → Revisar las DLL cargadas
7. filescan                   → Buscar archivos de interés
8. hashdump                   → Extraer credenciales
9. timeliner                  → Crear una línea temporal
```

---

## 📚 Recursos

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Volatility Wiki](https://github.com/volatilityfoundation/volatility/wiki)
- [Guía rápida de análisis forense de memoria](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Guías rápidas relacionadas
- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [Escalada de privilegios en Windows](../../Windows-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🧠 ¡Domina el análisis forense de memoria!</b><br>
  <i>La memoria no miente: extrae las evidencias de la RAM</i>
</p>
