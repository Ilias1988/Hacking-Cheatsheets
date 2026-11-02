# 🐛 GDB: guía rápida del depurador GNU

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones del original:** Se conservan comandos, prompts, registros y direcciones. Los ejemplos mezclan x86 y x86-64; los desplazamientos de pila y las convenciones no son universales. `gdb attach <PID>` no es la forma habitual de adjuntarse: se distingue la opción de inicio `-p` del comando interno `attach`. Cuando `x` omite el tamaño de unidad, puede reutilizar el de una inspección anterior ([manual de GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)). En la sección de búsqueda del desplazamiento, generar un patrón no hace que se envíe al programa: la primera entrada mostrada contiene letras A; `print(cyclic(...))` en Python 3 tampoco garantiza emitir bytes sin representación textual. Debe mantenerse coherente el tamaño de subsecuencia entre generación y búsqueda ([GEF](https://hugsy.github.io/gef/commands/pattern/)). Los comandos de GEF/PEDA/pwndbg dependen de la extensión y versión instaladas. Las redirecciones `<(...)` requieren un shell compatible. Los puntos por línea y algunos argumentos necesitan símbolos de depuración.


```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    GNU Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

## 📋 Índice

- [Qué es GDB](#qué-es-gdb)
- [Instalación y configuración](#instalación-y-configuración)
- [Uso básico](#uso-básico)
- [Puntos de interrupción](#puntos-de-interrupción)
- [Control de la ejecución](#control-de-la-ejecución)
- [Inspección de memoria](#inspección-de-memoria)
- [Registros](#registros)
- [Análisis de la pila](#análisis-de-la-pila)
- [GEF/PEDA/pwndbg](#gefpedapwndbg)
- [Desarrollo de exploits](#desarrollo-de-exploits)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-gdb"></a>

## 🎯 Qué es GDB

**GDB** (GNU Debugger) es el depurador estándar de Linux. Es esencial para:

- 🐛 **Depuración**: encontrar y corregir errores
- 🔍 **Ingeniería inversa**: comprender el comportamiento de los binarios
- 💥 **Desarrollo de exploits**: desbordamientos de búfer y ROP
- 📊 **Análisis de binarios**: examinar el flujo de ejecución
- 🧪 **Retos CTF**: resolver retos de ingeniería inversa y pwn

### Arquitecturas admitidas

| Arquitectura | Notas |
|--------------|-------|
| x86 | Intel de 32 bits |
| x86-64 | Intel/AMD de 64 bits |
| ARM | ARM de 32 bits |
| ARM64 | ARM de 64 bits |
| MIPS | Procesadores MIPS |
| PowerPC | Procesadores PPC |

---

<a id="instalación-y-configuración"></a>

## 🚀 Instalación y configuración

### Instalar GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (usar lldb como alternativa, o bien)
brew install gdb
```

### Instalar GEF (GDB Enhanced Features)

```bash
# GEF: la opción más recomendada según el original
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# O manualmente
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Instalar PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Instalar pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Verificar la instalación

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="uso-básico"></a>

## 💻 Uso básico

### Iniciar GDB

```bash
# Depurar un ejecutable
gdb ./program

# Depurar con argumentos
gdb --args ./program arg1 arg2

# Adjuntarse a un proceso en ejecución
gdb -p <PID>
gdb attach <PID>

# Modo silencioso (sin cabecera de presentación)
gdb -q ./program

# Ejecutar comandos desde un archivo
gdb -x commands.gdb ./program
```

### Comandos básicos

| Comando | Abreviatura | Descripción |
|---------|-------|-------------|
| `run` | `r` | Iniciar el programa |
| `run args` | `r args` | Ejecutar con argumentos |
| `continue` | `c` | Continuar la ejecución |
| `quit` | `q` | Salir de GDB |
| `help` | `h` | Mostrar la ayuda |
| `help <cmd>` | `h <cmd>` | Ayuda de un comando |

### Comandos de archivos

```bash
# Cargar un ejecutable
(gdb) file ./program

# Cargar símbolos
(gdb) symbol-file ./program.debug

# Cargar un volcado de memoria (core dump)
(gdb) core-file core

# Establecer el directorio de trabajo
(gdb) cd /path/to/dir

# Establecer los argumentos
(gdb) set args arg1 arg2
```

---

<a id="puntos-de-interrupción"></a>

## 🔴 Puntos de interrupción

### Establecer puntos de interrupción

```bash
# Detenerse en una función
(gdb) break main
(gdb) b main

# Detenerse en una dirección
(gdb) break *0x08048456
(gdb) b *0x08048456

# Detenerse en una línea
(gdb) break file.c:25
(gdb) b 25

# Detenerse a un desplazamiento respecto a una función
(gdb) break *main+48

# Punto de interrupción temporal (se elimina automáticamente)
(gdb) tbreak main
```

### Puntos de interrupción condicionales

```bash
# Detenerse cuando se cumpla la condición
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Añadir una condición a un punto de interrupción existente
(gdb) condition 1 $eax == 0

# Eliminar la condición
(gdb) condition 1
```

### Gestionar puntos de interrupción

```bash
# Listar los puntos de interrupción
(gdb) info breakpoints
(gdb) info b

# Eliminar un punto de interrupción
(gdb) delete 1
(gdb) d 1

# Eliminar todos los puntos de interrupción
(gdb) delete
(gdb) d

# Desactivar un punto de interrupción
(gdb) disable 1
(gdb) dis 1

# Activar un punto de interrupción
(gdb) enable 1
(gdb) en 1

# Desactivar todos
(gdb) disable

# Activar todos
(gdb) enable
```

### Watchpoints (puntos de interrupción de datos)

```bash
# Vigilar cambios en una variable
(gdb) watch variable
(gdb) watch *0x08048456

# Vigilar lecturas
(gdb) rwatch variable

# Vigilar lecturas o escrituras
(gdb) awatch variable

# Vigilar una expresión
(gdb) watch ($eax > 10)
```

### Catchpoints (puntos de captura de eventos)

```bash
# Capturar llamadas al sistema
(gdb) catch syscall

# Capturar una llamada al sistema específica
(gdb) catch syscall write

# Capturar señales
(gdb) catch signal SIGSEGV

# Capturar excepciones
(gdb) catch throw
(gdb) catch catch
```

---

<a id="control-de-la-ejecución"></a>

## ▶️ Control de la ejecución

### Ejecución paso a paso

```bash
# Avanzar una instrucción (entrando en las llamadas)
(gdb) stepi
(gdb) si

# Avanzar una línea (entrando en las llamadas)
(gdb) step
(gdb) s

# Avanzar sin entrar en las llamadas
(gdb) nexti
(gdb) ni

# Avanzar una línea sin entrar en las llamadas
(gdb) next
(gdb) n

# Continuar hasta el retorno
(gdb) finish
(gdb) fin

# Continuar hasta una dirección
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Ejecución

```bash
# Iniciar o reiniciar el programa
(gdb) run
(gdb) r

# Continuar después del punto de interrupción
(gdb) continue
(gdb) c

# Ejecutar hasta el siguiente punto de interrupción
(gdb) continue

# Omitir N puntos de interrupción
(gdb) continue 5

# Saltar a una dirección (¡peligroso!)
(gdb) jump *0x08048456
```

### Control de procesos

```bash
# Finalizar el proceso actual
(gdb) kill

# Desadjuntarse del proceso
(gdb) detach

# Adjuntarse al proceso
(gdb) attach <PID>

# Seguir un fork (padre o hijo)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="inspección-de-memoria"></a>

## 🔍 Inspección de memoria

### Comando print

```bash
# Mostrar una variable
(gdb) print variable
(gdb) p variable

# Mostrar en hexadecimal
(gdb) print/x variable
(gdb) p/x $eax

# Mostrar en binario
(gdb) p/t variable

# Mostrar en decimal
(gdb) p/d variable

# Mostrar como carácter
(gdb) p/c $al

# Mostrar como cadena
(gdb) p/s pointer

# Mostrar un array
(gdb) p *array@10     # 10 elementos
```

### Comando de inspección (x)

```bash
# Sintaxis: x/NFU address
# N = cantidad, F = formato, U = tamaño de unidad

# Formatos: x(hexadecimal), d(decimal), u(sin signo), o(octal), 
#          t(binario), c(carácter), s(cadena), i(instrucción)
# Unidades: b(byte), h(media palabra=2), w(palabra=4), g(palabra gigante=8)

# Inspeccionar la memoria
(gdb) x/10x $esp           # 10 palabras en hexadecimal desde ESP
(gdb) x/20wx 0x08048456    # 20 palabras en hexadecimal
(gdb) x/s 0x08048456       # Cadena en la dirección
(gdb) x/10i $eip           # 10 instrucciones desde EIP
(gdb) x/10gx $rsp          # 10 palabras gigantes (8 bytes) desde RSP

# Patrones habituales
(gdb) x/100x $esp          # Inspeccionar la pila
(gdb) x/20i $eip           # Desensamblar
(gdb) x/s $eax             # Puntero a cadena
```

### Regiones de memoria

```bash
# Mostrar el mapa de memoria
(gdb) info proc mappings

# Mostrar las secciones
(gdb) maintenance info sections

# Mostrar los archivos
(gdb) info files
```

### Desensamblado

```bash
# Desensamblar una función
(gdb) disassemble main
(gdb) disas main

# Desensamblar la función actual
(gdb) disassemble
(gdb) disas

# Desensamblar un rango
(gdb) disas 0x08048456, 0x08048500

# Desensamblar con código fuente (si está disponible)
(gdb) disas /m main

# Establecer la sintaxis
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

<a id="registros"></a>

## 📊 Registros

### Ver los registros

```bash
# Todos los registros
(gdb) info registers
(gdb) i r

# Registro específico
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Todos los registros, incluidos FP/SSE
(gdb) info all-registers
```

### Modificar registros

```bash
# Establecer el valor de un registro
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Establecer las banderas
(gdb) set $eflags = 0x246
```

### Referencia de registros x86

| Registro | 64 bits | 32 bits | 16 bits | 8 bits | Finalidad |
|----------|--------|--------|--------|-------|---------|
| Acumulador | RAX | EAX | AX | AL/AH | Valor de retorno |
| Base | RBX | EBX | BX | BL/BH | General |
| Contador | RCX | ECX | CX | CL/CH | Contador de bucle |
| Datos | RDX | EDX | DX | DL/DH | Entrada/salida y multiplicación |
| Índice de origen | RSI | ESI | SI | SIL | Origen de la cadena |
| Índice de destino | RDI | EDI | DI | DIL | Destino de la cadena |
| Puntero de base | RBP | EBP | BP | BPL | Marco de pila |
| Puntero de pila | RSP | ESP | SP | SPL | Cima de la pila |
| Instrucción | RIP | EIP | IP | - | Siguiente instrucción |

### Registros adicionales de x64

```
R8-R15 : Registros de propósito general
R8D-R15D : Partes bajas de 32 bits
R8W-R15W : Partes bajas de 16 bits
R8B-R15B : Partes bajas de 8 bits
```

---

<a id="análisis-de-la-pila"></a>

## 📚 Análisis de la pila

### Ver la pila

```bash
# Traza de llamadas de la pila
(gdb) backtrace
(gdb) bt

# Traza completa con variables locales
(gdb) backtrace full
(gdb) bt full

# Mostrar el marco
(gdb) frame
(gdb) f

# Seleccionar el marco
(gdb) frame 2
(gdb) f 2

# Mostrar el contenido de la pila
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Marco de pila

```bash
# Mostrar información del marco
(gdb) info frame
(gdb) i f

# Mostrar las variables locales
(gdb) info locals

# Mostrar los argumentos
(gdb) info args

# Subir o bajar entre marcos
(gdb) up
(gdb) down
```

### Comandos de pila

```bash
# Inspeccionar la dirección de retorno
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Inspeccionar el EBP guardado
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Mostrar la disposición de la pila
(gdb) x/20wx $esp-20  # Antes y después
```

---

<a id="gefpedapwndbg"></a>

## 🎨 GEF/PEDA/pwndbg

### Comandos de GEF

```bash
# Visualización del contexto (automática)
gef➤ context

# Checksec (mecanismos de seguridad)
gef➤ checksec

# Buscar un patrón
gef➤ pattern create 200
gef➤ pattern search $rsp

# Buscar una cadena o un valor
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Análisis del heap (memoria dinámica)
gef➤ heap chunks
gef➤ heap bins

# Gadgets ROP
gef➤ ropgadget

# Asistente de cadenas de formato
gef➤ format-string-helper

# VMMap (mapa de memoria)
gef➤ vmmap

# Cadena de desreferencias
gef➤ dereference $rsp 20

# Telescope (desreferencia inteligente)
gef➤ telescope $rsp 20

# Información ELF
gef➤ elf-info

# Valor del canario de pila
gef➤ canary

# Información de PIE/ASLR
gef➤ pie
gef➤ aslr
```

### Comandos de PEDA

```bash
# Comprobación de seguridad
peda➤ checksec

# Buscar una cadena
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Crear y buscar patrones
peda➤ pattern create 200
peda➤ pattern offset $eip

# Gadgets ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Contexto
peda➤ context all

# Ensamblado
peda➤ assemble
```

### Comandos de pwndbg

```bash
# Contexto
pwndbg> context

# Checksec
pwndbg> checksec

# Buscar
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Patrón cíclico
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Heap (memoria dinámica)
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="desarrollo-de-exploits"></a>

## 💥 Desarrollo de exploits

### Encontrar el desplazamiento

```bash
# Método 1: patrón (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Método 2: patrón (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Fundamentos de los desbordamientos de búfer

```bash
# Comprobar ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=desactivado, 1=parcial, 2=completo

# Desactivar ASLR (para pruebas)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Comprobar las protecciones del binario
gef➤ checksec

# Encontrar la ubicación de la dirección de retorno
(gdb) x/20wx $esp     # Antes del fallo
(gdb) i f             # Información del marco
```

### Encontrar gadgets

```bash
# Buscar "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Encontrar la dirección de system()
(gdb) p system
(gdb) p &system

# Encontrar un gadget pop rdi (64 bits)
gef➤ ropgadget --grep "pop rdi"

# Encontrar pop; ret (32 bits)
gef➤ ropgadget --grep "pop"
```

### Escribir memoria

```bash
# Establecer un valor de memoria
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Escribir una cadena
(gdb) set {char[10]}0x08048456 = "AAAA"

# Llamar a una función
(gdb) call system("/bin/sh")
```

### Útil para exploits

```bash
# Seguir los procesos hijo
(gdb) set follow-fork-mode child

# Desactivar ASLR en GDB
(gdb) set disable-randomization on

# Mostrar las bibliotecas compartidas
(gdb) info sharedlibrary

# Encontrar la dirección base de libc
gef➤ vmmap libc

# Encontrar PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `r` | Ejecutar el programa |
| `c` | Continuar |
| `b *addr` | Establecer un punto de interrupción |
| `si` | Avanzar una instrucción |
| `ni` | Siguiente instrucción |
| `fin` | Terminar la función |
| `x/Nx addr` | Inspeccionar la memoria |
| `p $reg` | Mostrar un registro |
| `i r` | Información de registros |
| `bt` | Traza de llamadas |
| `disas` | Desensamblar |
| `q` | Salir |

### Inspección de memoria

| Comando | Descripción |
|---------|-------------|
| `x/10x $esp` | 10 palabras en hexadecimal desde ESP |
| `x/s addr` | Cadena en la dirección |
| `x/i $eip` | Instrucción en EIP |
| `x/20wx addr` | 20 palabras en hexadecimal |
| `x/10gx $rsp` | 10 palabras de 8 bytes desde RSP |

### Depuración rápida

```bash
# Iniciar y detenerse en main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Inspeccionar el fallo
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Flujo de desarrollo de exploits

```
1. checksec           → Comprobar protecciones
2. pattern create     → Crear un patrón
3. ejecutar con patrón → Provocar un fallo en el programa
4. pattern search     → Encontrar el desplazamiento
5. buscar gadgets     → Gadgets ROP
6. construir payload  → Crear el exploit
7. probar             → Depurar con GDB
```

---

## 📚 Recursos

- [Manual oficial de GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [Documentación de GEF](https://hugsy.github.io/gef/)
- [Documentación de pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Guías rápidas relacionadas
- [Ghidra](../../Ghidra/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [Elevación de privilegios en Linux](../../Linux-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🐛 ¡Domina la depuración en Linux!</b><br>
  <i>GDB: la herramienta esencial para la ingeniería inversa en Linux</i>
</p>
