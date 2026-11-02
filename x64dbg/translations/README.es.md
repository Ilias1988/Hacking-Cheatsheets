# 🔧 x64dbg - Guía rápida del depurador de Windows

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Windows x64/x32 Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 Índice

- [¿Qué es x64dbg?](#-qué-es-x64dbg)
- [Instalación](#-instalación)
- [Descripción de la interfaz](#-descripción-de-la-interfaz)
- [Uso básico](#-uso-básico)
- [Puntos de interrupción](#-puntos-de-interrupción)
- [Ejecución paso a paso y continua](#-ejecución-paso-a-paso-y-continua)
- [Operaciones con memoria](#-operaciones-con-memoria)
- [Registros](#-registros)
- [Análisis de la pila](#-análisis-de-la-pila)
- [Aplicación de parches](#-aplicación-de-parches)
- [Scripting](#-scripting)
- [Atajos de teclado](#-atajos-de-teclado)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es x64dbg?

**x64dbg** es un depurador de código abierto para Windows x64/x32. Es el sucesor espiritual de OllyDbg e incorpora funciones modernas:

- 🔍 **Depuración en modo usuario** - Depurar aplicaciones de Windows
- 🦠 **Análisis de malware** - Examinar ejecutables sospechosos
- 💥 **Desarrollo de exploits** - Investigar exploits de Windows
- 🔧 **Ingeniería inversa** - Comprender el comportamiento de los binarios
- 🧩 **Compatibilidad con plugins** - Ampliar sus funciones mediante plugins

### Funciones

| Función | Descripción |
|---------|-------------|
| **GUI** | Interfaz moderna y personalizable |
| **Compatibilidad con x64** | Depuración completa de 64 bits |
| **Scripting** | Lenguaje de scripting integrado |
| **Plugins** | Amplio ecosistema de plugins |
| **Symbols** | Compatible con símbolos PDB |
| **Código abierto** | Gratuito y en desarrollo activo |

---

## 🚀 Instalación

### Descarga

```
# Sitio web oficial
https://x64dbg.com/

# Versiones publicadas en GitHub
https://github.com/x64dbg/x64dbg/releases

# Descargar: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Configuración

```
1. Extraer el ZIP en una ubicación permanente (p. ej., C:\x64dbg\)
2. Ejecutar x96dbg.exe (iniciador para ambas arquitecturas)
   - x32dbg.exe → Depuración de 32 bits
   - x64dbg.exe → Depuración de 64 bits
3. (Opcional) Añadir a PATH o crear accesos directos
```

### Plugins recomendados

| Plugin | Finalidad |
|--------|---------|
| **ScyllaHide** | Contramedidas frente a la detección del depurador |
| **SwissArmyKnife** | Utilidades variadas |
| **x64dbgpy** | Scripting en Python |
| **TitanHide** | Ocultación del depurador mediante un componente del kernel |
| **OllyDumpEx** | Volcado de memoria |
| **Multiline Ultimate Assembler** | Parches con varias instrucciones |

### Instalación de plugins

```
1. Descargar el plugin (archivo DLL)
2. Copiar en: x64dbg\release\x32\plugins\ (32 bits)
           x64dbg\release\x64\plugins\ (64 bits)
3. Reiniciar x64dbg
```

---

> **Interfaz:** las ventanas, acciones y rutas de menús se conservan en inglés para localizarlas en x64dbg. `Right-click` indica clic derecho; `Follow` significa seguir una dirección y `Dump` es una vista o un volcado de memoria. Los atajos pueden variar según versión y configuración.

<a id="-descripción-de-la-interfaz"></a>
## 🖥️ Descripción de la interfaz

### Ventanas principales

| Ventana | Descripción | Atajo |
|--------|-------------|----------|
| **CPU** | Vista de desensamblado | Alt+C |
| **Log** | Salida de depuración | Alt+L |
| **Memory Map** | Regiones de memoria | Alt+M |
| **Call Stack** | Traza de la pila de llamadas | Alt+K |
| **Breakpoints** | Todos los puntos de interrupción | Alt+B |
| **Symbols** | Símbolos cargados | Alt+E |
| **Threads** | Lista de hilos | Alt+T |
| **Handles** | Lista de handles (identificadores de recursos) | Alt+H |

### Paneles de la ventana CPU

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Barra de estado

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

## 💻 Uso básico

### Iniciar la depuración

```
# Abrir un ejecutable
File → Open (F3)

# Adjuntarse a un proceso
File → Attach (Alt+A)

# Abrir y ejecutar
Debug → Run (F9)

# Reiniciar la depuración
Debug → Restart (Ctrl+F2)

# Detener la depuración
Debug → Stop (Alt+F2)
```

### Barra de comandos

```
# Introducir los comandos en la barra inferior

# Ir a una dirección
g 0x401000
goto 0x401000

# Ir a una expresión
g GetProcAddress
g kernel32.LoadLibraryA

# Buscar una cadena
/s "password"

# Buscar bytes hexadecimales
/x 90 90 90 90

# Buscar un patrón
/p "8B ?? 89"
```

> **Corrección de comandos:** `g` y `r` son alias de `run`, no comandos generales para navegar sin ejecutar ni para asignar registros como sugieren algunos ejemplos. Con una dirección, `run` coloca un breakpoint de un solo uso antes de continuar. Se conserva el original; consulta la [referencia oficial](https://help.x64dbg.com/en/latest/commands/debug-control/run.html).

### Navegación

```
# Ir a una dirección
Ctrl+G → Enter address

# Ir al punto de entrada
Debug → Entry Point

# Seguir en el desensamblador
Right-click → Follow in Disassembler

# Volver atrás
- (minus key)

# Avanzar
+ (plus key)
```

---

## 🔴 Puntos de interrupción

### Puntos de interrupción por software (INT3)

```
# Alternar un punto de interrupción en la dirección actual
F2

# Establecer un punto de interrupción mediante un comando
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Establecer un punto de interrupción condicional según el original
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Interrumpir al cargar un módulo
bp $module(kernel32)
```

> **Corrección de `bp`:** su segundo argumento es el nombre del breakpoint, no una condición. `"eax==0"` no configura por sí solo un breakpoint condicional y el tercer argumento no es un contador de eventos. Véase la [documentación de SetBPX](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html).

### Puntos de interrupción por hardware

```
# Establecer un breakpoint por hardware (límite de 4)
Right-click → Breakpoint → Hardware → Execute
Right-click → Breakpoint → Hardware → Access
Right-click → Breakpoint → Hardware → Write

# Mediante un comando
bph 0x401000, x, 1    # Ejecución
bph 0x401000, r, 4    # Lectura (4 bytes)
bph 0x401000, w, 4    # Escritura (4 bytes)

# Tamaños: 1, 2, 4 u 8 bytes
```

### Puntos de interrupción de memoria

```
# Interrumpir al acceder a memoria
Right-click in dump → Breakpoint → Memory → Access
Right-click in dump → Breakpoint → Memory → Write

# Mediante un comando
bpm 0x401000, r       # Lectura
bpm 0x401000, w       # Escritura
bpm 0x401000, x       # Ejecución
```

### Puntos de interrupción condicionales

```
# Interrumpir si la condición es verdadera según el original
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Interrumpir según el número de llamadas
bp 0x401000, "", 5    # Interrumpir en el quinto evento según el original

# Registrar sin interrumpir
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Gestión de puntos de interrupción

```
# Listar todos los puntos de interrupción
Breakpoints window (Alt+B)

# Eliminar un punto de interrupción
bc 0x401000          # Eliminar uno concreto
bc                   # Eliminar todos

# Activar o desactivar
bd 0x401000          # Desactivar
be 0x401000          # Activar

# Desactivar todos
bd *

# Activar todos
be *
```

---

<a id="-ejecución-paso-a-paso-y-continua"></a>
## ▶️ Ejecución paso a paso y continua

> **Acciones de la interfaz:** `Step Into` entra en las llamadas; `Step Over` las ejecuta sin recorrerlas; `Step Out` continúa hasta el retorno. `Run` ejecuta, `Pause` pausa, `Restart` reinicia, `Find` busca y `Assemble` ensambla. Se mantienen sus nombres literales en las tablas.

### Ejecución paso a paso

| Acción | Atajo | Descripción |
|--------|----------|-------------|
| Step Into | F7 | Ejecutar una instrucción |
| Step Over | F8 | Pasar por encima de las llamadas |
| Step Out | Ctrl+F9 | Ejecutar hasta el retorno |
| Run to Selection | F4 | Ejecutar hasta el cursor |
| Run to User Code | Alt+F9 | Salir del código del sistema |

### Ejecución continua

| Acción | Atajo | Descripción |
|--------|----------|-------------|
| Run | F9 | Continuar la ejecución |
| Run Skipping | Ctrl+F7 | Omitir la instrucción actual |
| Pause | F12 | Interrumpir la ejecución |
| Restart | Ctrl+F2 | Reiniciar la depuración |
| Close | Alt+F2 | Detener la depuración |

### Trazado

```
# Trazar entrando en llamadas (registrar todas las instrucciones)
Debug → Trace Into (Ctrl+F7)

# Trazar pasando por encima de llamadas
Debug → Trace Over (Ctrl+F8)

# Registrar una traza
Debug → Trace Record

# Detener el trazado
Debug → Stop Trace
```

### Omitir una instrucción

```
# Omitir la instrucción actual sin ejecutarla
Right-click → Set New Origin Here

# O bien
Ctrl+Shift+O
```

---

## 🔍 Operaciones con memoria

### Volcado de memoria

```
# Seguir una dirección en el volcado
Right-click → Follow in Dump → Address/Selection

# Ir a una dirección del volcado
Ctrl+G (in dump window)

# Cambiar la visualización del volcado
Right-click → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Memory Map

```
# Abrir Memory Map
View → Memory Map (Alt+M)

# Muestra todas las regiones de memoria:
- Address
- Size
- Section
- Type (Image/Private/Mapped)
- Protection (Execute/Read/Write)
- Module name
```

### Buscar en memoria

```
# Buscar desde la barra de comandos
/s "string"           # Cadena ASCII
/s L"string"          # Cadena Unicode
/x 90 90 90           # Bytes hexadecimales
/p "8B ?? 89"         # Patrón con comodines

# Buscar referencias
Right-click → Find references to → Selected Address

# Buscar cadenas
Right-click → Search for → All Strings
```

### Protección de memoria

```
# Ver la protección
Memory Map → Protection column

# Protecciones habituales
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Volcar memoria

```
# Volcar la memoria a un archivo
Right-click in Memory Map → Dump Memory to File

# Volcar mediante plugins
Plugins → OllyDumpEx → Dump
```

---

## 📊 Registros

### Panel de registros

```
# Propósito general (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Propósito general (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Registros de segmento
CS, DS, SS, ES, FS, GS

# Flags (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Modificar registros

```
# Hacer doble clic en un registro para editarlo
# Or right-click → Modify Value

# Mediante la barra de comandos
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Activar Zero Flag
```

### Flags importantes

| Flag | Nombre | Cuándo se activa |
|------|------|----------|
| ZF | Zero Flag | El resultado es cero |
| CF | Carry Flag | Desbordamiento sin signo |
| SF | Sign Flag | El resultado es negativo |
| OF | Overflow Flag | Desbordamiento con signo |
| DF | Direction Flag | Dirección de las operaciones con cadenas |
| TF | Trap Flag | Ejecución de un solo paso |

### Alternar flags

```
# Pulsar la letra del flag en el panel de registros
# O utilizar comandos:
r zf=!zf         # Alternar Zero Flag
r cf=0           # Desactivar Carry Flag
r sf=1           # Activar Sign Flag
```

---

## 📚 Análisis de la pila

### Panel de la pila

```
# Muestra el contenido de la pila con:
- Address
- Value
- Comments (if available)

# Opciones del menú de clic derecho:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify
```

### Call Stack

```
# Abrir Call Stack
View → Call Stack (Alt+K)

# Muestra:
- Return Address
- From (caller)
- To (callee)
- Size
- Comment
```

### Operaciones con la pila

```
# Ir a una dirección de la pila
Right-click → Follow DWORD in Stack

# Modificar un valor de la pila
Right-click → Modify → Edit

# Simulación de push/pop
r esp=esp-4      # Push (32 bits)
r esp=esp+4      # Pop (32 bits)
r rsp=rsp-8      # Push (64 bits)
r rsp=rsp+8      # Pop (64 bits)
```

> **Pila:** modificar ESP/RSP solo cambia el puntero; no reproduce todos los efectos de PUSH o POP, que también escribe o lee el valor correspondiente.

### Distribución de la pila (stdcall de 32 bits)

```
[ESP]      → Return Address
[ESP+4]    → Arg1
[ESP+8]    → Arg2
[ESP+C]    → Arg3
...

Después de PUSH EBP; MOV EBP, ESP:
[EBP]      → Saved EBP
[EBP+4]    → Return Address
[EBP+8]    → Arg1
[EBP-4]    → Local1
```

---

## 🔧 Aplicación de parches

### Ensamblar instrucciones

```
# Right-click instruction → Assemble (Space)
# Introducir las nuevas instrucciones

# Ejemplos:
NOP                    # Sin operación
JMP 0x401020           # Salto
MOV EAX, 0             # Poner EAX a cero
XOR EAX, EAX           # Poner EAX a cero (más compacto)
RET                    # Retorno
```

### Rellenar con NOP

```
# Select range → Right-click → Binary → Fill with NOPs

# O seleccionar y pulsar Space:
# Introducir varios NOP
```

### Edición binaria

```
# Editar los bytes directamente
Right-click → Binary → Edit

# Rellenar con un valor
Right-click → Binary → Fill

# Copiar o pegar datos binarios
Right-click → Binary → Copy
Right-click → Binary → Paste
```

### Parches habituales

| Antes | Después | Finalidad |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Invertir la condición |
| `75 XX` (JNE) | `74 XX` (JE) | Invertir la condición |
| `74 XX` (JE) | `EB XX` (JMP) | Saltar siempre |
| `74 XX` (JE) | `90 90` (NOP) | No saltar |
| `0F 84` (JE) | `0F 85` (JNE) | Invertir un salto cercano |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Omitir la llamada |

> **Longitud de instrucciones:** reemplazar una llamada por cinco NOP solo es correcto si ocupa cinco bytes. Comprueba el tamaño real y los límites de cada instrucción.

### Guardar los parches

```
# Método 1: Patch File
File → Patch File

# Método 2: exportar parches
Right-click → Copy → Selection
# Pegar en un editor hexadecimal

# Método 3: plugin
Plugins → Export modified executable
```

---

## 📝 Scripting

### Sintaxis de comandos

```
# Variables
$var = 0x401000

# Comparación
cmp eax, 0

# Condicional
je label
jne label

# Etiquetas
label:

# Comentarios
; Esto es un comentario
```

### Script básico

```
; Script para buscar WinMain, según el original
mov $addr, 0x401000        ; Dirección inicial
loop:
  find $addr, "8B EC"       ; Buscar PUSH EBP; MOV EBP, ESP según el original
  cmp $result, 0
  je notfound
  msg "Found at: {$result}"
  add $addr, $result
  add $addr, 1
  jmp loop
notfound:
  msg "Pattern not found"
ret
```

> **Precisión del script:** `8B EC` corresponde a `MOV EBP, ESP`, no incluye `PUSH EBP` (`55`) y puede aparecer en muchas funciones. El ejemplo no identifica WinMain de forma fiable; revisa también cómo utiliza la dirección devuelta por `find` para avanzar en el bucle.

### Comandos de scripting

| Comando | Descripción |
|---------|-------------|
| `msg "text"` | Mostrar un mensaje |
| `log "text"` | Escribir en la consola |
| `find addr, "pattern"` | Buscar un patrón |
| `bp addr` | Establecer un punto de interrupción |
| `bc addr` | Eliminar un punto de interrupción |
| `sti` | Step into |
| `sto` | Step over |
| `run` | Run |
| `ret` | Retornar o detenerse |

### Ejecutar un script

```
# Cargar y ejecutar un script
Scripts → Run Script

# O utilizar la barra de comandos
scr "scriptfile.txt"
```

---

<a id="-atajos-de-teclado"></a>
## ⌨️ Atajos de teclado

### General

| Atajo | Acción |
|----------|--------|
| F3 | Abrir un archivo |
| Alt+A | Adjuntarse a un proceso |
| Ctrl+F2 | Restart |
| Alt+F2 | Detener la depuración |
| Ctrl+G | Ir a una dirección |
| Ctrl+F | Find |

### Depuración

| Atajo | Acción |
|----------|--------|
| F2 | Alternar un punto de interrupción |
| F4 | Ejecutar hasta la selección |
| F7 | Step into |
| F8 | Step over |
| F9 | Run |
| F12 | Pause |
| Ctrl+F9 | Ejecutar hasta el retorno |

### Navegación

| Atajo | Acción |
|----------|--------|
| - | Volver atrás |
| + | Avanzar |
| * | Ir a EIP/RIP |
| Enter | Seguir una llamada o un salto |
| Escape | Volver atrás |

### Vistas

| Atajo | Acción |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log |
| Alt+M | Memory Map |
| Alt+K | Call Stack |
| Alt+B | Breakpoints |
| Alt+E | Symbols |
| Alt+T | Threads |

### Edición

| Atajo | Acción |
|----------|--------|
| Space | Assemble |
| ; | Añadir un comentario |
| : | Añadir una etiqueta |
| N | Cambiar el nombre |

---

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `bp addr` | Establecer un punto de interrupción |
| `bc addr` | Eliminar un punto de interrupción |
| `bph addr, x` | Punto de interrupción por hardware |
| `g addr` | Ir a una dirección |
| `r reg=val` | Establecer un registro |
| `sti` | Step into |
| `sto` | Step over |
| `run` | Run |

### Patrones habituales

| Bytes | Instrucción |
|-------|-------------|
| `90` | NOP |
| `CC` | INT3 (breakpoint) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

### Flujo de análisis

```
1. Cargar el ejecutable       → F3
2. Añadir un breakpoint       → F2 en la entrada
3. Ejecutar hasta el breakpoint → F9
4. Recorrer el código         → F7/F8
5. Examinar registros y memoria → Paneles
6. Añadir puntos de interrupción
7. Continuar el análisis
8. Aplicar parches si procede → Space
9. Guardar el archivo modificado
```

### Elusión de mecanismos antidepuración

```
# Instalar el plugin ScyllaHide
Plugins → ScyllaHide → Options → Select profile

# Parches manuales:
- Sustituir llamadas a IsDebuggerPresent por NOP
- Modificar PEB.BeingDebugged
- Gestionar las excepciones adecuadamente
```

---

## 📚 Recursos

- [Sitio oficial de x64dbg](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [Documentación de x64dbg](https://help.x64dbg.com/)
- [Plugins de x64dbg](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Guías rápidas relacionadas
- [Ghidra](../../Ghidra/translations/README.es.md)
- [GDB](../../GDB/translations/README.es.md)
- [Escalada de privilegios en Windows](../../Windows-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🔧 ¡Domina la depuración de Windows!</b><br>
  <i>x64dbg - El sucesor moderno de OllyDbg</i>
</p>
