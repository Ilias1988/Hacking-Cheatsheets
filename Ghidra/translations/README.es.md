# 🔍 Ghidra: guía de consulta de ingeniería inversa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              Suite de ingeniería inversa de la NSA
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Gratuito">
</p>

---

## 📋 Índice

- [Qué es Ghidra](#-qué-es-ghidra)
- [Instalación](#-instalación)
- [Descripción de la interfaz](#-descripción-de-la-interfaz)
- [Creación de un proyecto](#-creación-de-un-proyecto)
- [Fundamentos del análisis](#-fundamentos-del-análisis)
- [Navegación](#-navegación)
- [Descompilador](#-descompilador)
- [Tipos de datos y estructuras](#-tipos-de-datos-y-estructuras)
- [Parches y modificaciones](#-parches-y-modificaciones)
- [Automatización con scripts](#-automatización-con-scripts)
- [Atajos de teclado](#-atajos-de-teclado)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es Ghidra

**Ghidra** es una suite gratuita y de código abierto para ingeniería inversa de software (SRE), desarrollada por la NSA. Ofrece:

- 🔍 **Desensamblador** - Convierte código binario en lenguaje ensamblador
- 🔄 **Descompilador** - Genera pseudocódigo similar a C
- 📊 **Análisis** - Análisis automatizado del código
- 🔧 **Aplicación de parches** - Modifica archivos binarios
- 🐍 **Automatización con scripts** - Automatización con Python y Java
- 👥 **Colaboración** - Compatibilidad con varios usuarios

### Arquitecturas compatibles

| Arquitectura | Variantes |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Formatos compatibles

| Formato | Descripción |
|--------|-------------|
| ELF | Ejecutables de Linux |
| PE/COFF | Ejecutables de Windows |
| Mach-O | Ejecutables de macOS |
| Raw Binary | Firmware, ROM |
| APK/DEX | Aplicaciones de Android |
| y muchos más... |

---

## 🚀 Instalación

### Descarga

```bash
# Descargar del sitio oficial
https://ghidra-sre.org/

# O de las versiones publicadas en GitHub
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Requisitos

> **Compatibilidad:** Las órdenes con JDK 17 del original corresponden a versiones anteriores. La documentación de la rama de desarrollo consultada, para Ghidra 12.2, requiere JDK 25 de 64 bits. Utiliza los requisitos del archivo GettingStarted de tu distribución concreta; se conservan las órdenes originales. Fuente: [guía oficial de inicio](https://github.com/NationalSecurityAgency/ghidra/blob/master/GhidraDocs/GettingStarted.md).

| Requisito | Versión |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | 4 GB como mínimo; 8 GB o más recomendados |
| **Disco** | 1 GB o más para la instalación |

### Linux/macOS

```bash
# Instalar JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Extraer Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Ejecutar
./ghidraRun
```

### Windows

```batch
:: Instalar JDK 17+ de Oracle o Adoptium
:: Extraer ghidra_*.zip
:: Ejecutar ghidraRun.bat
```

---

<a id="-descripción-de-la-interfaz"></a>
## 🖥️ Descripción de la interfaz

> **Nota de interfaz:** Los nombres de ventanas, analizadores, controles y rutas de menú se conservan en inglés para localizarlos. Algunos menús, acciones y atajos del original dependen de la versión, del contexto y de la configuración de la herramienta. Los atajos se pueden personalizar; consulta la [guía oficial de introducción](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html).

### Ventanas principales

| Ventana | Descripción |
|--------|-------------|
| **Program Trees** | Estructura de archivos del proyecto |
| **Symbol Tree** | Funciones, etiquetas y clases |
| **Data Type Manager** | Tipos de datos definidos |
| **Listing** | Vista del desensamblado |
| **Decompiler** | Pseudocódigo en C |
| **Console** | Salida y ejecución de scripts |
| **Defined Strings** | Cadenas encontradas |
| **Bookmarks** | Ubicaciones guardadas |

### Ventana Listing (desensamblado)

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Inicio de la función
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Marco de pila
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

## 📁 Creación de un proyecto

### Proyecto nuevo

```
1. File → New Project
2. Elige:
   - Non-Shared Project (local, sin compartir)
   - Shared Project (colaboración)
3. Selecciona el directorio
4. Introduce el nombre del proyecto
5. Haz clic en Finish
```

### Importación de un binario

```
1. File → Import File (I)
2. Selecciona el binario o archivo
3. Elige el formato (normalmente se detecta automáticamente)
4. Configura las opciones:
   - Language/Processor
   - Compiler spec
5. Haz clic en OK
6. Analyze? → Yes (recommended)
```

### Opciones de análisis

| Opción | Descripción |
|--------|-------------|
| ASCII Strings | Buscar cadenas |
| Decompiler | Activar la descompilación |
| Stack Analysis | Análisis del marco de pila |
| Reference Analysis | Buscar referencias cruzadas |
| Function ID | Identificar funciones de bibliotecas |
| Call Convention | Identificar las convenciones de llamada |

---

## 🔬 Fundamentos del análisis

### Análisis automático

```
Analysis → Auto Analyze (A)
- Ejecuta todos los analizadores seleccionados
- Puede tardar en analizar binarios grandes
- El progreso se muestra en el monitor de tareas
```

### Analizadores de ejecución puntual

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Identificación de funciones

> **Aclaración:** El analizador Non-returning Functions identifica funciones que no retornan; no es una opción general para localizar todas las funciones. La creación de funciones también depende del desensamblado y del análisis del flujo.

```
# Buscar todas las funciones
Analysis → Auto Analyze → Enable "Non-returning Functions"

# Creación manual de una función
Right-click address → Create Function (F)

# Código sin definir
Right-click → Disassemble (D)
```

---

## 🧭 Navegación

### Ir a una dirección

```
# Atajo de teclado
G → Enter address → OK

# Ejemplos:
0x00401000      # Dirección hexadecimal
main            # Nombre de la función
entry           # Punto de entrada
LAB_00401234    # Etiqueta
```

### Atajos de navegación

| Atajo | Acción |
|----------|--------|
| `G` | Ir a una dirección |
| `Ctrl+G` | Ir a una dirección |
| `Alt+←` | Volver a la ubicación anterior |
| `Alt+→` | Avanzar a la ubicación siguiente |
| `Ctrl+Shift+E` | Ir al punto de entrada |

### Referencias cruzadas (XREF)

```
# Ver las referencias a la ubicación actual
Right-click → References → Show References To

# Buscar dónde se llama a la función
Select function → Right-click → References → Show References To

# Teclado
Ctrl+Shift+F → Find references
```

### Funciones de búsqueda

```
# Búsqueda
Search → Memory (S)           # Buscar en la memoria
Search → For Strings          # Buscar cadenas
Search → For Direct References # Buscar referencias a la dirección
Search → Program Text         # Buscar en el desensamblado

# Búsqueda de funciones
Symbol Tree → Filter → Type function name
```

---

## 💻 Descompilador

### Abrir el descompilador

```
Window → Decompiler
# O haz clic en la pestaña Decompiler
# Descompila automáticamente la función seleccionada
```

### Lectura del código descompilado

```c
// Ejemplo de salida del descompilador
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

### Mejorar la descompilación

```
# Cambiar el nombre de las variables
Right-click variable → Rename Variable (L)

# Cambiar el tipo de datos
Right-click variable → Retype Variable (Ctrl+L)

# Añadir comentarios
; → Set EOL comment
Ctrl+; → Set plate comment

# Establecer la firma de la función
Right-click function → Edit Function Signature
```

### Trucos para el descompilador

| Acción | Cómo |
|--------|-----|
| Cambiar el nombre de una variable | `L` o clic derecho |
| Cambiar el tipo | `Ctrl+L` |
| Crear una estructura | Right-click → Auto Create Structure |
| Dividir o combinar variables | Right-click → Split/Merge |
| Confirmar la firma | `P` |

---

## 📊 Tipos de datos y estructuras

### Creación de una estructura

```
# Método 1: desde Data Type Manager
Data Type Manager → Right-click → New → Structure

# Método 2: creación automática desde el descompilador
Decompiler → Right-click variable → Auto Create Structure
```

### Definición de una estructura

```c
// Ejemplo de estructura
struct Player {
    char name[32];      // Desplazamiento 0x00
    int health;         // Desplazamiento 0x20
    int score;          // Desplazamiento 0x24
    float position[3];  // Desplazamiento 0x28
};
```

### Aplicar una estructura

```
# En la vista Listing
Select address → Right-click → Data → Choose Data Type

# O bien
Press T → Select structure

# En Decompiler
Right-click variable → Retype Variable → Select struct
```

### Arrays (matrices)

```
# Crear un array
Select start address → Right-click → Data → Create Array
Introduce el número de elementos
```

### Enumeraciones (enums)

```
# Crear una enumeración
Data Type Manager → Right-click → New → Enum

# Definir valores
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

## 🔧 Parches y modificaciones

### Modificar una instrucción

```
# Método 1: mediante el teclado
Select instruction → Ctrl+Shift+G
Enter new instruction → Press Enter

# Método 2: mediante el menú
Right-click → Patch Instruction
```

### Modificar datos

```
# Cambiar bytes
Right-click → Patch Data
Introduce los bytes en hexadecimal

# O bien
Select bytes → Ctrl+Shift+G
```

### Parches habituales

> **Aclaración:** Los cambios de opcodes mostrados son ejemplos de x86. La longitud de la instrucción, el desplazamiento relativo y los límites del código importan; sustituir un salto condicional por NOP puede requerir varios bytes. Se mantienen los valores del original.

| Original | Modificado | Finalidad |
|----------|---------|---------|
| `JE` | `JMP` | Saltar siempre |
| `JNE` | `JMP` | Saltar siempre |
| `JE` | `NOP` | No saltar nunca |
| `CALL` | `NOP NOP NOP NOP NOP` | Omitir la llamada |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Exportar el binario modificado

> **Aclaración:** La exportación Binary genera bytes en bruto y no garantiza reconstruir un ejecutable PE/ELF equivalente al original. Comprueba el exportador disponible y la correspondencia entre direcciones de memoria y desplazamientos del archivo.

```
File → Export Program
Format: Binary
Selecciona el destino
```

### Secuencia de instrucciones NOP (NOP sled)

```
# Rellenar con NOP
Select range → Right-click → Clear Code Block
Then: Select → Right-click → Patch → Fill with NOPs
```

---

## 🐍 Automatización con scripts

### Administrador de scripts

```
Window → Script Manager
# Or: Tools → Script Manager
```

### Ejecutar un script

```
Script Manager → Select script → Run (green play button)
# O haz doble clic en el script
```

### Scripts de Python (Ghidra Python)

> **Compatibilidad:** El ejemplo contiene una f-string de Python 3, incompatible con el intérprete Jython 2.7 tradicional. PyGhidra permite ejecutar scripts con CPython 3; el contexto de ejecución debe proporcionar las variables de Ghidra utilizadas. Se conserva el código original. Fuente: [módulo oficial PyGhidra](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/README.md).

```python
# Ejemplo: enumerar todas las funciones
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = recorrido hacia delante

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Operaciones habituales en scripts

```python
# Obtener la dirección actual
currentAddress

# Obtener el programa actual
currentProgram

# Obtener el administrador de funciones
fm = currentProgram.getFunctionManager()

# Obtener el listado desensamblado
listing = currentProgram.getListing()

# Obtener la instrucción situada en una dirección
instr = listing.getInstructionAt(addr)

# Obtener los bytes situados en una dirección
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Crear una etiqueta
createLabel(addr, "my_label", True)

# Añadir un comentario
setEOLComment(addr, "My comment")

# Obtener referencias
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Scripts integrados útiles

| Script | Finalidad |
|--------|---------|
| FindCrypt | Buscar constantes criptográficas |
| FunctionID | Identificar funciones de bibliotecas |
| ExportFunctionInfoScript | Exportar la lista de funciones |
| FindStrings | Buscar referencias a cadenas |
| SearchMemory | Buscar patrones |

---

<a id="-atajos-de-teclado"></a>
## ⌨️ Atajos de teclado

### Navegación

| Atajo | Acción |
|----------|--------|
| `G` | Ir a una dirección |
| `Alt+←` | Atrás |
| `Alt+→` | Adelante |
| `Ctrl+Shift+E` | Punto de entrada |
| `Ctrl+E` | Exportar la tabla |
| `N` | Siguiente elemento sin definir |

### Análisis

| Atajo | Acción |
|----------|--------|
| `F` | Crear una función |
| `D` | Desensamblar |
| `C` | Borrar la definición |
| `P` | Crear un puntero |
| `T` | Aplicar un tipo de datos |
| `A` | Analizar automáticamente |

### Edición

| Atajo | Acción |
|----------|--------|
| `L` | Cambiar el nombre de una etiqueta o variable |
| `;` | Establecer un comentario al final de línea (EOL) |
| `Ctrl+;` | Establecer un comentario de cabecera (plate comment) |
| `Ctrl+L` | Cambiar el tipo de una variable |
| `Ctrl+Shift+G` | Modificar una instrucción |

### Búsqueda

| Atajo | Acción |
|----------|--------|
| `Ctrl+Shift+F` | Buscar referencias |
| `Ctrl+F` | Buscar texto |
| `S` | Buscar en la memoria |
| `Ctrl+B` | Buscar bytes |

### Ventanas

| Atajo | Acción |
|----------|--------|
| `Ctrl+D` | Mostrar u ocultar el descompilador |
| `Ctrl+T` | Mostrar u ocultar el administrador de tipos de datos |
| `Ctrl+S` | Árbol de símbolos |

---

## 📊 Referencia rápida

### Flujo de trabajo del análisis

```
1. Importar un binario → File → Import
2. Análisis automático → Analysis → Auto Analyze (aceptar las opciones predeterminadas)
3. Buscar entry/main  → G → main or entry
4. Revisar funciones  → Symbol Tree → Functions
5. Examinar cadenas   → Search → For Strings
6. Seguir la ejecución → Seguir llamadas y saltos
7. Renombrar símbolos → L en funciones o variables
8. Documentar         → Añadir comentarios (;)
```

### Tareas habituales

| Tarea | Cómo |
|------|-----|
| Buscar main() | `G` → `main` o buscar en Symbol Tree |
| Ver cadenas | Search → For Strings |
| Buscar criptografía | Ejecutar el script FindCrypt |
| Consultar las importaciones | Symbol Tree → Imports |
| Buscar referencias cruzadas | Select → Ctrl+Shift+F |
| Crear una función | Select address → `F` |
| Descompilar | Window → Decompiler |

### Lista de comprobación de ingeniería inversa

```
☐ Identificar el tipo de archivo (PE, ELF, etc.)
☐ Ejecutar el análisis automático
☐ Buscar el punto de entrada o main
☐ Revisar las cadenas en busca de pistas
☐ Identificar funciones de interés
☐ Esquematizar el flujo del programa
☐ Analizar las funciones sospechosas
☐ Documentar los hallazgos con comentarios
☐ Cambiar los nombres de funciones y variables
☐ Crear estructuras cuando sea necesario
```

### Convenciones de llamada a funciones

> **Aclaración:** La tabla resume casos habituales de x86/x64 para argumentos enteros o punteros; los valores de coma flotante, agregados, argumentos adicionales y funciones variádicas siguen reglas específicas de cada ABI. No es una descripción completa de todas las variantes.

| Convención | Retorno | Argumentos | Limpieza de la pila |
|------------|--------|------|----------------|
| **cdecl** | EAX | Pila (R→L) | Llamador |
| **stdcall** | EAX | Pila (R→L) | Función llamada |
| **fastcall** | EAX | ECX, EDX, pila | Función llamada |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Llamador |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Llamador |

---

## 📚 Recursos

- [Sitio oficial de Ghidra](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Documentación de Ghidra](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Guías de consulta relacionadas
- [Binwalk](../../Binwalk/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [Volatility](../../Volatility/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Domina la ingeniería inversa!</b><br>
  <i>Ghidra: la contribución de la NSA a la comunidad de seguridad</i>
</p>
