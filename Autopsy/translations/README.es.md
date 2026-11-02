# 🔍 Autopsy: guía de consulta de la plataforma de análisis forense digital

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Plataforma de análisis forense digital
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

## 📋 Índice

- [Qué es Autopsy](#-qué-es-autopsy)
- [Instalación](#-instalación)
- [Creación de un caso](#-creación-de-un-caso)
- [Añadir fuentes de datos](#-añadir-fuentes-de-datos)
- [Módulos de ingesta](#-módulos-de-ingesta)
- [Funciones de análisis](#-funciones-de-análisis)
- [Análisis de archivos](#-análisis-de-archivos)
- [Análisis de la línea temporal](#-análisis-de-la-línea-temporal)
- [Búsqueda por palabras clave](#-búsqueda-por-palabras-clave)
- [Informes](#-informes)
- [Herramientas de línea de comandos (Sleuth Kit)](#-herramientas-de-línea-de-comandos-sleuth-kit)

---

## 🎯 Qué es Autopsy

**Autopsy** es una plataforma gratuita de análisis forense digital y de código abierto basada en The Sleuth Kit (TSK). Ofrece:

- 🖥️ **Interfaz gráfica (GUI)** - Análisis forense fácil de utilizar
- 💾 **Análisis de discos** - Examina discos duros, dispositivos USB e imágenes
- 📁 **Recuperación de archivos** - Recupera archivos eliminados
- 📊 **Análisis de la línea temporal** - Reconstruye la actividad del usuario
- 🔍 **Búsqueda por palabras clave** - Busca en todos los datos
- 📧 **Análisis de correo electrónico** - Procesa bases de datos de correo electrónico
- 🌐 **Artefactos web** - Historial del navegador y marcadores
- 📱 **Análisis forense de dispositivos móviles** - Análisis de Android e iOS

### Tipos de evidencia compatibles

> **Compatibilidad:** Los formatos y módulos disponibles dependen de la versión, de las bibliotecas de imágenes y del tipo de adquisición. Las cifras de hardware y la estructura del caso que aparecen después son orientativas. Los nombres de controles, pestañas y rutas de menú se conservan en inglés para localizarlos en la interfaz; compruébalos en la [guía oficial de la versión instalada](https://sleuthkit.org/autopsy/docs/user-docs/latest/).

| Tipo | Formatos |
|------|---------|
| **Imágenes de disco** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk** | Unidades físicas y lógicas |
| **Archivos de máquinas virtuales** | VMDK, VHD, VHDX |
| **Logical Files** | Carpetas y archivos |
| **Unallocated Space** | Datos recuperados mediante carving (recuperación por firmas) |

---

## 🚀 Instalación

### Windows

```bash
# Descargar del sitio oficial
https://www.autopsy.com/download/

# Ejecutar el instalador
autopsy-x.x.x-64bit.msi

# Ubicación predeterminada
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

> **Compatibilidad:** El paquete `autopsy` de Kali corresponde a la interfaz web histórica Autopsy 2.x, no a la GUI moderna 4.x descrita en esta guía. Sus funciones y menús son distintos. Véanse la [ficha oficial de Kali](https://www.kali.org/tools/autopsy/) y la [documentación oficial de Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/).

```bash
# Instalar mediante apt
sudo apt update
sudo apt install autopsy

# Ejecutar
autopsy
```

### Requisitos

| Componente | Mínimo | Recomendado |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Varios núcleos | 4 o más núcleos |
| **Almacenamiento** | SSD | NVMe SSD |
| **Sistema operativo** | Windows 10/11, Linux | Windows 10/11 |

---

## 📁 Creación de un caso

### Asistente para crear un caso

1. **File → New Case**
2. Introduce **Case Name** (por ejemplo, "Investigation_2024")
3. Introduce **Base Directory** (ubicación donde se guardan los archivos del caso)
4. Selecciona **Case Type**:
   - Single-user (un solo usuario, local)
   - Multi-user (varios usuarios, colaborativo)
5. Introduce **Case Number** (opcional)
6. Introduce la información del analista en **Examiner**
7. Haz clic en **Finish**

### Estructura del caso

```
CaseName/
├── autopsy.db           # Base de datos del caso (SQLite)
├── Reports/             # Informes generados
├── Export/              # Archivos exportados
├── ModuleOutput/        # Resultados de los módulos
└── [HostName]/          # Datos de cada equipo
    ├── [DataSource]/    # Archivos de evidencia
    └── Reports/
```

### Propiedades del caso

| Campo | Descripción |
|-------|-------------|
| Case Name | Identificador de la investigación |
| Case Number | Número de referencia |
| Examiner | Nombre del analista |
| Base Directory | Ubicación de almacenamiento del caso |
| Case Type | Un solo usuario o varios usuarios |

---

## 📀 Añadir fuentes de datos

### Tipos de fuentes de datos

| Tipo | Descripción |
|------|-------------|
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | Análisis de una unidad física |
| **Logical Files** | Importación de carpetas o archivos |
| **Unallocated Space** | Imagen en bruto del espacio libre |

### Añadir una imagen de disco

1. **Add Data Source** (barra de herramientas)
2. Selecciona **Disk Image or VM File**
3. Busca el archivo de imagen
4. Selecciona **Time Zone**
5. Configura **Ingest Modules** (sección siguiente)
6. Haz clic en **Finish**

### Añadir un disco local

```
⚠️ ADVERTENCIA: ¡Utiliza un bloqueador de escritura o el modo de solo lectura!
```

1. **Add Data Source**
2. Selecciona **Local Disk**
3. Elige un disco de la lista
4. Configura módulos de ingesta
5. **Finish**

### Formatos de imagen compatibles

| Formato | Extensión | Descripción |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | Copia bit a bit |
| EnCase | .E01, .Ex01 | Formato Expert Witness |
| AFF | .aff | Advanced Forensic Format |
| VMDK | .vmdk | Disco de VMware |
| VHD/VHDX | .vhd, .vhdx | Disco de Hyper-V |

---

<a id="-módulos-de-ingesta"></a>
## ⚙️ Módulos de ingesta

### ¿Qué son los módulos de ingesta?

Son módulos de análisis automatizado que se ejecutan sobre las fuentes de datos para extraer artefactos.

### Módulos principales

| Módulo | Descripción |
|--------|-------------|
| **Recent Activity** | Historial del navegador, descargas y cookies |
| **Hash Lookup** | Comprobación de MD5/SHA1 frente a bases de datos conocidas |
| **File Type Identification** | Identifica tipos de archivo por su firma |
| **Keyword Search** | Indexa el contenido y permite buscar en él |
| **Email Parser** | Procesa PST, MBOX y EML |
| **Extension Mismatch** | Detecta archivos cuya extensión se ha cambiado |
| **Embedded File Extractor** | Extrae archivos de contenedores comprimidos y documentos |
| **EXIF Parser** | Extrae metadatos de imágenes |
| **Encryption Detection** | Busca archivos cifrados |
| **Interesting Files** | Busca archivos relevantes |
| **PhotoRec Carver** | Recuperación de archivos por firmas (file carving) |
| **Virtual Machine Extractor** | Extrae archivos de máquinas virtuales |

### Bases de datos de hashes

```
# Añadir bases de datos de hashes conocidos
Tools → Options → Hash Database

# Tipos:
- Known (NSRL) - Ignorar archivos conocidos y legítimos
- Known Bad - Alertar sobre hashes de malware
```

### NSRL (National Software Reference Library)

```bash
# Descargar NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Importar en Autopsy
Tools → Options → Hash Database → Import Database
```

### Configuración de los módulos

```
# Activar o desactivar módulos para cada caso
Right-click data source → Run Ingest Modules

# Configurar las opciones del módulo
Haz clic en el icono del engranaje situado junto al nombre del módulo
```

---

## 🔬 Funciones de análisis

### Vista en árbol (panel izquierdo)

```
Data Sources
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Custom Tags]
```

### Áreas de análisis rápido

| Categoría | Qué buscar |
|----------|-----------------|
| **Deleted Files** | Archivos recuperados y eliminaciones realizadas por el usuario |
| **Web History** | Sitios visitados y búsquedas |
| **Downloads** | Archivos descargados |
| **Recent Documents** | Archivos abiertos recientemente |
| **USB Devices** | Dispositivos conectados |
| **Email** | Comunicaciones |
| **Images** | Fotos y capturas de pantalla |
| **Archives** | Contenido de ZIP y RAR |

---

## 📄 Análisis de archivos

### Visualización de archivos

```
# Content Viewer (visor de contenido, panel inferior)
- Hex        : Vista hexadecimal en bruto
- Text       : Contenido de texto
- Application: Vista renderizada
- Strings    : Cadenas extraídas
- Metadata   : Metadatos del archivo
- Results    : Resultados del análisis
- Indexed Text: Texto en el que se pueden realizar búsquedas
```

### Propiedades de los archivos

| Propiedad | Descripción |
|----------|-------------|
| Name | Nombre del archivo |
| Location | Ruta completa |
| Size | Tamaño del archivo |
| Created | Fecha y hora de creación |
| Modified | Última modificación |
| Accessed | Último acceso |
| MD5/SHA1/SHA256 | Valores de hash |
| Known | Estado según NSRL |
| MIME Type | Tipo detectado |

### Extracción de archivos

```
# Extraer un archivo
Right-click → Extract File(s)

# Extraer varios archivos
Select files → Right-click → Extract

# Extraer en una ubicación concreta
Actions → Extract to...
```

### Recuperación de archivos eliminados

```
Views → Deleted Files

# Muestra:
- Archivos en $Recycle.Bin
- Archivos marcados como eliminados en la MFT
- Archivos recuperados por firmas (PhotoRec)
```

---

## 📊 Análisis de la línea temporal

### Vista de línea temporal

```
# Abrir la línea temporal
Tools → Timeline

# O bien
Results → Timeline
```

### Funciones de la línea temporal

| Función | Descripción |
|---------|-------------|
| **Counts View** | Frecuencia de eventos a lo largo del tiempo |
| **Details View** | Eventos individuales |
| **List View** | Lista de eventos en forma de tabla |
| **Filters** | Filtra por tipo, fecha, etc. |

### Tipos de eventos

| Tipo | Color | Descripción |
|------|-------|-------------|
| File Modified | Azul | Cambio del contenido del archivo |
| File Accessed | Verde | Apertura de un archivo |
| File Created | Amarillo | Creación de un archivo |
| File Changed | Rojo | Cambio de metadatos |
| Web Activity | Morado | Eventos del navegador |

### Filtros de la línea temporal

```
# Filtrar por intervalo de fechas
Start Date: 2024-01-01
End Date: 2024-12-31

# Filtrar por tipo
☑ File System
☑ Web Activity
☐ Registry (if parsed)

# Filtrar por fuente de datos
☑ Disk Image 1
☐ Disk Image 2
```

### Exportación de la línea temporal

```
# Exportar la línea temporal
File → Export → Timeline (CSV/TSV)

# Utilizar con herramientas externas
- Excel
- plaso/log2timeline
- timesketch
```

---

## 🔍 Búsqueda por palabras clave

### Creación de listas de palabras clave

```
# Tools → Options → Keyword Search

# Añadir una lista nueva
1. Haz clic en "New List"
2. Asigna un nombre a la lista
3. Añade palabras clave:
   - Literal (coincidencia exacta)
   - Regex (patrón)
   - Substring (subcadena)
```

### Palabras clave habituales para las búsquedas

```
# Información financiera
credit card
bank account
swift
iban
bitcoin
wallet

# Credenciales
password
login
username
secret
key

# Información sensible
confidential
secret
classified
internal only

# Red
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Ejemplos de expresiones regulares

> **Aclaración:** Son patrones de búsqueda aproximados, no validadores. El patrón IPv4 admite octetos mayores que 255; el de tarjetas no comprueba el algoritmo de Luhn ni todas las longitudes posibles. Las palabras clave de los ejemplos se mantienen en inglés porque son los términos que se buscan.

| Finalidad | Regex |
|---------|-------|
| Correo electrónico | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| Dirección IP | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Teléfono de Estados Unidos | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Tarjeta de crédito | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| Número de la Seguridad Social de EE. UU. (SSN) | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Ejecución de búsquedas

```
# Búsqueda puntual
Keyword Search (top-right) → Enter term → Search

# Utilizar listas de palabras clave
Right-click data source → Run Ingest Modules → Keyword Search
```

---

## 📝 Informes

### Generar un informe

```
# Generar un informe
Tools → Generate Report

# O bien
Right-click case → Generate Report
```

### Tipos de informes

| Tipo | Descripción |
|------|-------------|
| **HTML Report** | Basado en web, incluye imágenes |
| **Excel Report** | Formato de hoja de cálculo |
| **Text Report** | Texto sin formato |
| **TSK Body File** | Archivo body para una línea temporal |
| **Files - FS** | Informe del sistema de archivos |
| **Portable Case** | Subconjunto que se puede compartir |

### Opciones del informe HTML

```
# Seleccionar los datos que se incluirán
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Opciones
☑ Include thumbnails
☑ Include metadata
```

### Caso portátil

```
# Crear un caso portátil para compartirlo
Generate Report → Portable Case

# Incluye:
- Evidencias seleccionadas
- Resultados del análisis
- Se puede abrir en Autopsy
```

---

## 💻 Herramientas de línea de comandos (Sleuth Kit)

### Comandos de Sleuth Kit

Autopsy utiliza The Sleuth Kit (TSK) como motor de análisis. Estos son algunos comandos disponibles en su interfaz de línea de comandos (CLI):

### Información de la imagen

> **Aclaración:** `-o 2048` es un ejemplo del desplazamiento inicial del sistema de archivos, expresado en sectores, no en bytes; hay que determinarlo para cada imagen. Véase el [manual oficial de fls](https://www.sleuthkit.org/sleuthkit/man/fls.html).

```bash
# Consultar la información de la imagen
img_stat image.E01
img_stat image.dd

# Consultar la información del sistema de archivos
fsstat -o 2048 image.dd
```

### Listado de archivos

```bash
# Enumerar archivos
fls -r -o 2048 image.dd

# Enumerar archivos eliminados
fls -r -d -o 2048 image.dd

# Opciones:
# -r    Recursivo
# -d    Solo archivos eliminados
# -o    Desplazamiento (inicio de la partición)
```

### Recuperación de archivos

> **Aclaración:** En `icat`, `-r` activa técnicas de recuperación para archivos eliminados; no significa recursividad. Véase el [manual oficial de icat](https://www.sleuthkit.org/sleuthkit/man/icat.html).

```bash
# Obtener un archivo por su inodo
icat -o 2048 image.dd 12345 > recovered_file

# Obtener el contenido de un archivo
icat -r -o 2048 image.dd 12345 > file.txt
```

### Línea temporal

```bash
# Crear un archivo body
fls -r -m "/" -o 2048 image.dd > body.txt

# Crear una línea temporal
mactime -b body.txt -d > timeline.csv
```

### Cálculo de hashes

```bash
# Calcular MD5
md5sum image.dd

# Calcular SHA256
sha256sum image.dd

# Verificar la imagen
sha256sum -c image.dd.sha256
```

### Herramientas habituales de TSK

| Herramienta | Finalidad |
|------|---------|
| `img_stat` | Información de la imagen |
| `mmls` | Tabla de particiones |
| `fsstat` | Información del sistema de archivos |
| `fls` | Listado de archivos |
| `icat` | Extraer un archivo por su inodo |
| `istat` | Información del inodo |
| `blkls` | Extraer el espacio no asignado |
| `srch_strings` | Buscar cadenas |
| `tsk_recover` | Recuperar todos los archivos |
| `mactime` | Crear una línea temporal |

---

## 📊 Referencia rápida

### Flujo de trabajo de una investigación

```
1. Create Case          → Crear un caso con sus datos
2. Add Data Source      → Añadir una imagen de disco o una unidad
3. Run Ingest Modules   → Ejecutar el análisis automatizado
4. Review Results       → Revisar el contenido extraído
5. Keyword Search       → Buscar términos
6. Timeline Analysis    → Reconstruir los eventos
7. Tag Evidence         → Marcar los elementos importantes
8. Generate Report      → Crear el informe final
```

### Atajos de teclado

| Atajo | Acción |
|----------|--------|
| Ctrl+N | Nuevo caso (New Case) |
| Ctrl+O | Abrir caso (Open Case) |
| Ctrl+F | Buscar en la tabla (Find) |
| Ctrl+G | Generar un informe (Generate Report) |
| F5 | Actualizar (Refresh) |

### Ubicaciones habituales que conviene revisar

| Windows | Ruta |
|---------|------|
| Archivos recientes | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Descargas | `Users\*\Downloads` |
| Historial del navegador | `Users\*\AppData\Local\[Browser]\User Data` |
| Archivos temporales | `Users\*\AppData\Local\Temp` |
| Papelera de reciclaje | `$Recycle.Bin` |
| Prefetch (precarga) | `Windows\Prefetch` |
| Registros de eventos | `Windows\System32\winevt\Logs` |

---

## 📚 Recursos

- [Sitio oficial de Autopsy](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Documentación de Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [Formación en análisis forense y respuesta a incidentes (DFIR)](https://www.dfir.training/)

### Guías de consulta relacionadas
- [Volatility](../../Volatility/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [Wireshark](../../Wireshark/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Domina el análisis forense digital!</b><br>
  <i>Autopsy: el mejor aliado del investigador digital</i>
</p>
