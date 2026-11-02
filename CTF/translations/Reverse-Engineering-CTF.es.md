# 🔄 Guía de referencia de ingeniería inversa para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

---

## 🔍 Análisis inicial

```bash
# Tipo de archivo
file binary

# Cadenas de texto
strings binary | grep -i flag
strings -n 8 binary

# Información de ELF
readelf -h binary

# Comprobar protecciones
checksec binary
```

---

## 🛠️ Desensambladores

### Ghidra (gratuito)

> **Interfaz:** los nombres de menús se conservan en inglés para que coincidan con la aplicación. Los atajos y la disponibilidad del descompilador dependen de la herramienta, versión y configuración; F5 no es un atajo universal de descompilación.
```
1. File → Import File
2. Analyze → Auto Analyze
3. Buscar la función main()
4. Buscar strcmp/memcmp en las comprobaciones de contraseñas
5. F5 para descompilar
```

### IDA Free
```
1. Abrir el binario
2. F5 para ver pseudocódigo (Hex-Rays)
3. X para encontrar referencias cruzadas
4. N para renombrar variables
```

### Radare2
```bash
r2 binary
aaa          # Analizar
afl          # Listar funciones
pdf @main    # Desensamblar main
VV           # Modo visual
```

---

## 🐛 Depuración con GDB

```bash
# Iniciar
gdb ./binary

# Puntos de interrupción
b main
b *0x401234

# Ejecutar
r
r arg1 arg2

# Avanzar paso a paso
n           # Siguiente línea
s           # Entrar en la función
c           # Continuar

# Examinar
x/s $rdi    # Cadena de texto
x/10x $rsp  # 10 valores hexadecimales de la pila
info reg    # Registros

# Buscar la flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Instalar GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Comandos
vmmap
heap
telescope $rsp
```

---

## 🐍 Modificación de binarios con Python

```python
# Leer el binario
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Modificar bytes
data[0x1234] = 0x90  # NOP

# Escribir
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Patrones habituales

| Comprobación | Evasión |
|-------|--------|
| `strcmp(input, "password")` | Extraer "password" |
| `if (x == 0x1337)` | Encontrar el valor 0x1337 |
| Protección contra depuración | Modificar la llamada a ptrace |
| Cadenas ofuscadas | Análisis dinámico |

---

## 📋 Lista de comprobación de ingeniería inversa

```markdown
□ Ejecutar los comandos file y strings
□ Buscar símbolos de depuración
□ Abrir en Ghidra/IDA
□ Buscar la función main
□ Buscar comparaciones de cadenas
□ Seguir la entrada del usuario
□ Modificar la protección contra depuración si es necesario
□ Análisis dinámico con GDB
```

---

**Volver a CTF:** [🏁 Resumen de CTF](./README.es.md)
