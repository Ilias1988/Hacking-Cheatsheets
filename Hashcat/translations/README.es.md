# ⚡ Hashcat - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    Recuperación de contraseñas a máxima velocidad
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Tipos de hash">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Máxima velocidad">
</p>

<p align="center">
  <b>🚀 La utilidad de recuperación de contraseñas más rápida y avanzada del mundo</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Modos de ataque](#-modos-de-ataque)
- [Modos de hash](#-modos-de-hash)
- [Ataque de máscara](#-ataque-de-máscara)
- [Reglas](#-reglas)
- [Gestión de sesiones](#-gestión-de-sesiones)
- [Optimización de GPU](#-optimización-de-gpu)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Hashcat** es una utilidad avanzada de recuperación de contraseñas, presentada como la más rápida del mundo, que admite más de 350 tipos de hash y aprovecha la aceleración por GPU.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Más de 350 tipos de hash** | MD5, SHA, NTLM, bcrypt, WPA y otros |
| **Aceleración por GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Multi-GPU** | Compatibilidad con cracking distribuido |
| **5 modos de ataque** | Diccionario, fuerza bruta, híbrido, etc. |
| **Reglas potentes** | Transformación avanzada de palabras |
| **Multiplataforma** | Linux, Windows, macOS |

> **Terminología:** un ataque de diccionario prueba candidatos de una lista; una máscara describe los caracteres permitidos en cada posición. Las reglas transforman esos candidatos. Las cifras de tipos de hash y modos de ataque son históricas.

### Hashcat frente a John the Ripper

| Función | Hashcat | John |
|---------|---------|------|
| **Velocidad** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Compatibilidad con GPU** | Excelente | Buena |
| **Tipos de hash** | 350+ | 300+ |
| **Detección automática** | ❌ No | ✅ Sí |
| **Facilidad de uso** | Moderada | Más sencilla |
| **Uso más indicado** | Cracking con GPU | Cracking con CPU |

---

> **Actualización de la comparación:** Hashcat dispone de `--identify`; «sin detección automática» simplifica demasiado su capacidad actual. Las diferencias de velocidad y compatibilidad dependen del algoritmo, la versión y el hardware. Véase la [ayuda oficial](https://github.com/hashcat/hashcat/blob/master/docs/hashcat-help.md).

## 📥 Instalación

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Con OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Descargar desde el sitio oficial
https://hashcat.net/hashcat/

# Extraer y ejecutar desde la carpeta
hashcat.exe -h
```

### Desde el código fuente
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Comprobar la instalación
```bash
hashcat --version
hashcat -I                    # Mostrar los dispositivos OpenCL
hashcat -b                    # Ejecutar el benchmark (prueba de rendimiento)
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-m` | Tipo de hash (modo) |
| `-a` | Modo de ataque |
| `-o` | Archivo de salida |
| `-r` | Archivo de reglas |
| `--show` | Mostrar las contraseñas recuperadas |
| `--status` | Activar la pantalla de estado |
| `-w` | Perfil de carga de trabajo (1-4) |
| `--force` | Ignorar las advertencias |
| `-O` | Kernels optimizados |

### Inicio rápido
```bash
# Ataque básico de diccionario
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Mostrar los hashes recuperados
hashcat -m 0 hash.txt --show

# Consultar hashes de ejemplo
hashcat -m 0 --example-hashes
```

---

## 🎮 Modos de ataque

### Descripción general

| Modo | Nombre | Descripción |
|------|------|-------------|
| `-a 0` | Diccionario | Ataque con lista de palabras |
| `-a 1` | Combinación | Combinar dos listas de palabras |
| `-a 3` | Fuerza bruta | Ataque basado en máscaras |
| `-a 6` | Híbrido diccionario+máscara | Lista de palabras + máscara |
| `-a 7` | Híbrido máscara+diccionario | Máscara + lista de palabras |
| `-a 9` | Asociación | Asociaciones de palabras |

### Ataque de diccionario (-a 0)

```bash
# Ataque básico de diccionario
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Con reglas
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Varias listas de palabras
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Ataque de combinación (-a 1)

Combina palabras de dos listas de palabras.

```bash
# Combinar dos listas de palabras
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Combinaciones word1word2
```

### Ataque de fuerza bruta o máscara (-a 3)

```bash
# Solo minúsculas, 6 caracteres
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Solo dígitos, de 4 a 8 caracteres según el original
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Conjunto de caracteres personalizado
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

> **Corrección del incremento:** el ejemplo anterior utiliza solo cuatro posiciones `?d`. `--increment-max=8` no amplía esa máscara a ocho posiciones; para cubrir de 4 a 8 deben existir ocho posiciones en la máscara. Se conserva el comando original. Consulta la [documentación de máscaras](https://hashcat.net/wiki/doku.php?id=mask_attack).

### Ataques híbridos (-a 6, -a 7)

```bash
# Diccionario + máscara (añadir al final)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Máscara + diccionario (añadir al principio)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Modos de hash

### Modos de hash más habituales

| Modo | Tipo de hash | Ejemplo |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Formato complejo |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

> **Formatos y versiones:** los hashes abreviados con `...` son ilustraciones y no entradas completas. La lista conserva modos históricos como WPA/WPA2 `2500`; verifica su disponibilidad en tu versión y utiliza el formato compatible con el modo elegido. Las rutas de sesiones y los paquetes de controladores también dependen de la instalación.

### Aplicaciones web

| Modo | Tipo de hash |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Bases de datos

| Modo | Tipo de hash |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Documentos y archivos comprimidos

| Modo | Tipo de hash |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Criptomonedas

| Modo | Tipo de hash |
|------|-----------|
| 11300 | Monedero Bitcoin |
| 12700 | Monedero Blockchain |
| 16600 | Monedero Electrum |
| 15200 | Ethereum Keystore |

### Listar y buscar modos
```bash
# Listar todos los modos de hash
hashcat --help | grep -i "hash modes"

# Buscar un tipo de hash concreto
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Mostrar un formato de hash de ejemplo
hashcat -m 1000 --example-hashes
```

---

## 🎭 Ataque de máscara

### Marcadores de conjuntos de caracteres

| Marcador | Caracteres |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Conjuntos de caracteres personalizados

```bash
# Definir un conjunto de caracteres personalizado
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Varios conjuntos de caracteres personalizados
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Caracteres especiales
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Modo incremental

```bash
# Probar longitudes de 1 a 8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Especificar el mínimo y el máximo
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Patrones de máscara habituales

```bash
# 8 letras minúsculas
?l?l?l?l?l?l?l?l

# Contraseña + 4 dígitos
password?d?d?d?d

# Mayúscula + minúsculas + dígitos
?u?l?l?l?l?l?d?d

# Patrón Winter2024
?u?l?l?l?l?l?d?d?d?d

# Número de teléfono
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Reglas

### Archivos de reglas incluidos

```bash
# Ubicación
ls /usr/share/hashcat/rules/

# Reglas habituales
best64.rule           # Las 64 mejores reglas
rockyou-30000.rule    # Las 30000 reglas más destacadas
d3ad0ne.rule          # Reglas D3ad0ne
dive.rule             # Reglas Dive
generated.rule        # Reglas generadas
```

### Uso de reglas

```bash
# Un solo archivo de reglas
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Varios archivos de reglas
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Generar reglas
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Operaciones habituales de reglas

| Regla | Descripción | Ejemplo |
|------|-------------|---------|
| `:` | No realizar cambios | password |
| `l` | Convertir todo a minúsculas | PASSWORD → password |
| `u` | Convertir todo a mayúsculas | password → PASSWORD |
| `c` | Convertir la inicial a mayúscula | password → Password |
| `C` | Inicial en minúscula y resto en mayúsculas | password → pASSWORD |
| `t` | Invertir mayúsculas y minúsculas | password → PASSWORD |
| `r` | Invertir el orden | password → drowssap |
| `d` | Duplicar | pass → passpass |
| `$X` | Añadir X al final | pass → passX |
| `^X` | Añadir X al principio | pass → Xpass |
| `sXY` | Sustituir X por Y | password → pa$$word |

---

## 💾 Gestión de sesiones

### Crear una sesión
```bash
# Sesión con nombre
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Archivos de sesión almacenados en ~/.hashcat/sessions/
```

### Pausar y reanudar
```bash
# Pausar: pulsar 'p' durante el cracking
# Reanudar: pulsar 'r' durante el cracking

# O restaurar desde la línea de comandos
hashcat --session=mysession --restore
```

### Comandos de estado

Durante el cracking:
- `s` - Mostrar el estado
- `p` - Pausar
- `r` - Reanudar
- `b` - Omitir el ataque actual
- `c` - Punto de restauración
- `q` - Salir

### Opciones de salida
```bash
# Guardar la salida en un archivo
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Formato de salida
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Mostrar las contraseñas recuperadas
hashcat -m 0 hash.txt --show

# Mostrar los hashes pendientes de recuperar
hashcat -m 0 hash.txt --left
```

---

> **Corrección de formato:** `--outfile-format=2` selecciona texto de la contraseña, no `hash:plain`, como indica el comentario original. Consulta los formatos en la [ayuda oficial](https://github.com/hashcat/hashcat/blob/master/docs/hashcat-help.md).

## 🚀 Optimización de GPU

### Perfiles de carga de trabajo

| Perfil | Descripción |
|---------|-------------|
| `-w 1` | Bajo (predeterminado e interactivo, según el original) |
| `-w 2` | Medio |
| `-w 3` | Alto (puede bloquear el escritorio) |
| `-w 4` | Nightmare (equipo dedicado al cracking) |

> **Corrección de perfiles:** la ayuda oficial identifica `-w 2` como perfil predeterminado, no `-w 1`. La opción `--force` ignora advertencias y no aporta rendimiento por sí misma. `--benchmark-all` amplía los modos evaluados; no desactiva la optimización de OpenCL. Véase la [ayuda oficial](https://github.com/hashcat/hashcat/blob/master/docs/hashcat-help.md).

### Comandos de optimización
```bash
# Rendimiento alto
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Kernels optimizados (más rápidos, con longitud de contraseña limitada)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Seleccionar el dispositivo
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Desactivar la CPU y utilizar solo GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Opciones de dispositivos
```bash
# Listar los dispositivos
hashcat -I

# Seleccionar un dispositivo concreto
hashcat -d 1 ...

# Tipos de dispositivos
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Benchmark
```bash
# Benchmark completo
hashcat -b

# Tipo de hash concreto
hashcat -b -m 0

# Benchmark sin optimización OpenCL, según el original
hashcat -b --benchmark-all
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: ataque de diccionario contra MD5
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Ejemplo 2: NTLM con reglas
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Ejemplo 3: handshake (intercambio de autenticación) WPA/WPA2
```bash
# Convertir primero al formato de Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Recuperar la contraseña
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Ejemplo 4: fuerza bruta contra SHA256 (6 caracteres)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Ejemplo 5: documento de Office 2013
```bash
# Extraer el hash con office2john
office2john document.docx > office.hash

# Recuperar la contraseña
hashcat -m 9600 office.hash wordlist.txt
```

### Ejemplo 6: contraseña de shadow de Linux
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Ejemplo 7: hash de WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Ejemplo 8: ataque híbrido
```bash
# Nombre de empresa + 4 dígitos
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Ejemplo 9: hashes de un dominio Windows
```bash
# Hashes NTLM de secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

> **Formato de entrada:** `--username` permite ignorar un prefijo de usuario, pero no transforma automáticamente una línea completa `usuario:RID:LM:NT:...` de secretsdump en un hash NTLM. Prepara el formato de entrada que espera el modo elegido. Lo mismo se aplica a prefijos de nombre de archivo añadidos por herramientas de extracción.

### Ejemplo 10: monedero Bitcoin
```bash
# Extraer con bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Diccionario contra MD5 | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| Fuerza bruta contra NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 con reglas | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Mostrar las contraseñas recuperadas | `hashcat -m 0 hash.txt --show` |
| Benchmark | `hashcat -b` |
| Listar los dispositivos | `hashcat -I` |
| Restaurar una sesión | `hashcat --session=name --restore` |

### Modos de hash habituales

| Modo | Tipo |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Modos de ataque

| Modo | Tipo |
|------|------|
| -a 0 | Diccionario |
| -a 1 | Combinación |
| -a 3 | Fuerza bruta |
| -a 6 | Híbrido (diccionario+máscara) |
| -a 7 | Híbrido (máscara+diccionario) |

---

## 💡 Consejos y buenas prácticas

### Consejos de eficiencia

1. **Comenzar con diccionario y reglas**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Utilizar kernels optimizados**
   ```bash
   hashcat -O ...
   ```

3. **Centrarse en patrones habituales**
   ```bash
   # Estación+Año: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Consejos de rendimiento

```bash
# Rendimiento máximo (sistema dedicado)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Varias GPU
hashcat -d 1,2 ...
```

### Flujo de trabajo

```
1. Identificar el tipo de hash
2. Probar un ataque de diccionario con reglas
3. Probar máscaras dirigidas (patrones)
4. Probar fuerza bruta completa (si es viable)
5. Probar ataques híbridos
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Hashcat es una herramienta potente para recuperar contraseñas que debe utilizarse únicamente con **fines autorizados**.
> 
> - ✅ Auditar sistemas propios
> - ✅ Evaluaciones de seguridad con autorización
> - ✅ Recuperación de contraseñas propias
> - ❌ No intentar recuperar contraseñas sin autorización
> - ❌ El acceso no autorizado es ilegal
> 
> **El cracking de contraseñas sin autorización es ilegal y puede dar lugar a cargos penales.**

---

## 📚 Recursos

### Recursos oficiales
- [Sitio oficial de Hashcat](https://hashcat.net/hashcat/)
- [Wiki de Hashcat](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Foro de Hashcat](https://hashcat.net/forum/)

### Listas de palabras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Guías rápidas relacionadas
- [John the Ripper](../../John-The-Ripper/translations/README.es.md)
- [Hydra](../../Hydra/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)

---

<p align="center">
  <b>⚡ ¡Domina el cracking de contraseñas con GPU!</b><br>
  <i>La recuperación de contraseñas más rápida del mundo</i>
</p>
