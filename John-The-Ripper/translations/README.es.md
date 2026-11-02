# 🔨 John the Ripper: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Terminología y precisión:** Cracking significa probar candidatos para recuperar una contraseña a partir de su hash; wordlist es una lista de palabras y mangling, su transformación mediante reglas. Las opciones y utilidades `2john` dependen de Core/Jumbo y de la distribución. Se conservan ejemplos de hashes abreviados; no son entradas completas válidas. `wc -l` cuenta líneas, no necesariamente formatos. Las reglas `$X` y `^X` añaden un solo carácter; las líneas `$123` y `^2023` del original no deben interpretarse como una forma general de añadir cadenas enteras ([sintaxis oficial de reglas](https://www.openwall.com/john/doc/RULES.shtml)). El formato de contraseña predeterminado de Linux depende de la distribución, y la comparación con Hashcat es orientativa.


```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  La herramienta de recuperación de contraseñas     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 La legendaria herramienta de auditoría de seguridad y recuperación de contraseñas</b>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Instalación](#instalación)
- [Sintaxis básica](#sintaxis-básica)
- [Modos de recuperación](#modos-de-recuperación)
- [Extracción de hashes](#extracción-de-hashes)
- [Tipos de hash habituales](#tipos-de-hash-habituales)
- [Recuperación de contraseñas de Linux](#recuperación-de-contraseñas-de-linux)
- [Recuperación de contraseñas de Windows](#recuperación-de-contraseñas-de-windows)
- [Recuperación de contraseñas de archivos](#recuperación-de-contraseñas-de-archivos)
- [Reglas y transformación de palabras](#reglas-y-transformación-de-palabras)
- [Gestión de sesiones](#gestión-de-sesiones)
- [Ajuste del rendimiento](#ajuste-del-rendimiento)
- [Ejemplos prácticos](#ejemplos-prácticos)
- [Referencia rápida](#referencia-rápida)
- [Consejos y buenas prácticas](#consejos-y-buenas-prácticas)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**John the Ripper** (John/JtR) es una herramienta gratuita y de código abierto para auditar la seguridad de las contraseñas y recuperarlas. Está diseñada para detectar contraseñas débiles y recuperar contraseñas a partir de sus hashes.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Varios formatos** | Admite cientos de tipos de hash |
| **Varios modos** | Single, Wordlist e Incremental |
| **Reglas personalizadas** | Potentes reglas de transformación de palabras |
| **Detección automática** | Detección automática del formato de hash |
| **Multiplataforma** | Linux, Windows, macOS |
| **Compatibilidad con GPU** | Aceleración OpenCL (Jumbo) |

### John frente a Hashcat

| Característica | John | Hashcat |
|---------|------|---------|
| **Más adecuado para** | Recuperación mediante CPU | Recuperación mediante GPU |
| **Facilidad de uso** | Más sencillo | Más complejo |
| **Detección automática** | ✅ Sí | ❌ No |
| **Reglas** | Potentes | Muy potentes |
| **Tipos de hash** | Muchos | Más |
| **Velocidad (GPU)** | Buena | Mejor |

### Versiones

| Versión | Descripción |
|---------|-------------|
| **John (Core)** | Versión básica |
| **John Jumbo** | Versión ampliada por la comunidad (recomendada) |

---

<a id="instalación"></a>

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Versión estándar
sudo apt install john

# Versión Jumbo (recomendada)
sudo apt install john-jumbo
```

### Desde el código fuente (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Descargar desde el sitio oficial
https://www.openwall.com/john/

# O usar Chocolatey
choco install john
```

### Verificar la instalación
```bash
john --version
john --list=formats | wc -l    # Contar los formatos admitidos
```

---

<a id="sintaxis-básica"></a>

## ⌨️ Sintaxis básica

### Sintaxis general
```bash
john [options] [password_file]
```

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `--wordlist=` | Ruta de la lista de palabras |
| `--format=` | Especificar el formato del hash |
| `--rules` | Activar las reglas de transformación de palabras |
| `--show` | Mostrar las contraseñas recuperadas |
| `--single` | Modo de recuperación Single |
| `--incremental` | Modo de fuerza bruta |
| `--session=` | Asignar un nombre a la sesión |
| `--restore` | Restaurar una sesión anterior |
| `--pot=` | Archivo pot personalizado |

### Inicio rápido
```bash
# Detectar automáticamente y recuperar
john hashes.txt

# Con una lista de palabras
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostrar las contraseñas recuperadas
john --show hashes.txt
```

---

<a id="modos-de-recuperación"></a>

## 🎮 Modos de recuperación

### Modo Single Crack

Utiliza nombres de usuario e información GECOS para generar contraseñas candidatas.

```bash
# Modo Single (el más rápido según el original; prueba variantes del nombre de usuario)
john --single hashes.txt

# Especificando el formato
john --single --format=sha512crypt hashes.txt
```

### Modo de lista de palabras

Utiliza palabras de un diccionario, con reglas opcionales.

```bash
# Ataque básico de diccionario
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Lista de palabras con reglas
john --wordlist=wordlist.txt --rules hashes.txt

# Conjunto de reglas específico
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Modo incremental (fuerza bruta)

Prueba todas las combinaciones posibles de caracteres.

```bash
# Modo incremental predeterminado (todos los caracteres)
john --incremental hashes.txt

# Conjunto de caracteres específico
john --incremental=Digits hashes.txt      # Solo números
john --incremental=Lower hashes.txt       # Solo minúsculas
john --incremental=Upper hashes.txt       # Solo mayúsculas
john --incremental=Alpha hashes.txt       # Solo letras
john --incremental=Alnum hashes.txt       # Alfanumérico
john --incremental=LowerNum hashes.txt    # Minúsculas y números
```

### Modo de máscara

Fuerza bruta basada en patrones.

```bash
# Ataque de máscara (? = marcadores de posición)
john --mask='?l?l?l?l?d?d' hashes.txt

# Marcadores de posición:
# ?l = minúsculas    ?u = mayúsculas
# ?d = dígitos       ?s = especiales
# ?a = todos        ?h = hexadecimal en minúsculas
```

### Modo externo

Algoritmos de recuperación personalizados.

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="extracción-de-hashes"></a>

## 🔍 Extracción de hashes

### Contraseñas de Linux

```bash
# Combinar los archivos passwd y shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Recuperar las contraseñas del archivo combinado
john unshadowed.txt
```

### Hashes de Windows

```bash
# Desde el archivo SAM (usar pwdump, secretsdump, etc.)
# Formato: user:id:LM_hash:NTLM_hash:::

# Usar hashdump de Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Guardar en un archivo y recuperar
john --format=nt hashes.txt
```

### Contraseñas de archivos (herramientas 2john)

```bash
# Archivos ZIP
zip2john protected.zip > zip_hash.txt

# Archivos RAR
rar2john protected.rar > rar_hash.txt

# Archivos PDF
pdf2john protected.pdf > pdf_hash.txt

# Documentos de Office
office2john document.docx > office_hash.txt

# Claves SSH
ssh2john id_rsa > ssh_hash.txt

# Archivos 7zip
7z2john archive.7z > 7z_hash.txt

# Bases de datos KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="tipos-de-hash-habituales"></a>

## 🔢 Tipos de hash habituales

### Tabla de formatos de hash

| Tipo de hash | Opción de formato | Ejemplo |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Listar todos los formatos
```bash
# Listar todos los formatos admitidos
john --list=formats

# Buscar un formato específico
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Contar los formatos
john --list=formats | wc -l
```

### Identificar el tipo de hash
```bash
# Usar hash-identifier
hash-identifier

# O usar hashid
hashid -m '$6$salt$hash...'
```

---

<a id="recuperación-de-contraseñas-de-linux"></a>

## 🐧 Recuperación de contraseñas de Linux

### Preparar los hashes
```bash
# Combinar passwd y shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# O extraer solo el hash
sudo cat /etc/shadow | grep username
```

### Ejemplos de formatos de hash
```
# Formato de /etc/shadow:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (el más habitual en Linux moderno, según el original)
# $y$ = yescrypt (más reciente, según el original)
```

### Recuperar contraseñas a partir de hashes de Linux
```bash
# Detección automática (recomendada)
john linux_hashes.txt

# Especificar el formato
john --format=sha512crypt linux_hashes.txt

# Con una lista de palabras
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Mostrar los resultados
john --show linux_hashes.txt
```

---

<a id="recuperación-de-contraseñas-de-windows"></a>

## 🪟 Recuperación de contraseñas de Windows

### Tipos de hash

| Tipo | Descripción | Formato |
|------|-------------|--------|
| **LM** | Heredado (débil) | `--format=lm` |
| **NTLM** | Windows moderno | `--format=nt` |
| **NTLMv2** | Autenticación de red | `--format=netntlmv2` |

### Recuperar contraseñas a partir de hashes NTLM
```bash
# Formato de hash NTLM:
# user:rid:lm_hash:ntlm_hash:::

# Recuperar NTLM
john --format=nt windows_hashes.txt

# Con una lista de palabras
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Con reglas
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Extraer hashes de Windows
```bash
# Usar Metasploit
meterpreter> hashdump

# Usar secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Usar pwdump
pwdump7.exe
```

---

<a id="recuperación-de-contraseñas-de-archivos"></a>

## 📄 Recuperación de contraseñas de archivos

### Archivos ZIP
```bash
# Extraer el hash
zip2john protected.zip > zip.hash

# Recuperar
john --wordlist=rockyou.txt zip.hash

# Mostrar la contraseña
john --show zip.hash
```

### Archivos RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### Archivos PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Documentos de Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Claves privadas SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Base de datos KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="reglas-y-transformación-de-palabras"></a>

## ⚙️ Reglas y transformación de palabras

### Activar reglas
```bash
# Reglas predeterminadas
john --wordlist=wordlist.txt --rules hashes.txt

# Conjunto de reglas específico
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Listar las reglas disponibles
```bash
john --list=rules
```

### Operaciones habituales de reglas

| Regla | Descripción | Ejemplo |
|------|-------------|---------|
| `:` | No hacer nada | password → password |
| `l` | Convertir todo a minúsculas | PassWord → password |
| `u` | Convertir todo a mayúsculas | password → PASSWORD |
| `c` | Poner la primera letra en mayúscula | password → Password |
| `r` | Invertir el orden | password → drowssap |
| `d` | Duplicar | pass → passpass |
| `$X` | Añadir el carácter X al final | pass → passX |
| `^X` | Añadir el carácter X al principio | pass → Xpass |
| `sXY` | Reemplazar X por Y | password → pa$$word |

### Reglas personalizadas
```bash
# Añadir a john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="gestión-de-sesiones"></a>

## 💾 Gestión de sesiones

### Crear una sesión con nombre
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Restaurar una sesión
```bash
# Restaurar una sesión interrumpida
john --restore=mycrack

# Restaurar la sesión predeterminada
john --restore
```

### Comprobar el estado
```bash
# Pulsar cualquier tecla durante la recuperación para ver el estado
# O consultar el archivo .rec
cat ~/.john/mycrack.rec
```

### Archivo pot
```bash
# Archivo pot predeterminado
~/.john/john.pot

# Archivo pot personalizado
john --pot=custom.pot hashes.txt

# Mostrar todas las contraseñas recuperadas
john --show --pot=custom.pot hashes.txt
```

---

<a id="ajuste-del-rendimiento"></a>

## 🚀 Ajuste del rendimiento

### Fork (varios procesos)
```bash
# Usar varios núcleos de CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# Listar los dispositivos OpenCL
john --list=opencl-devices

# Usar la GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Limitar los candidatos
```bash
# Limitar la longitud de la contraseña
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Uso de memoria
```bash
# Reducir el uso de memoria
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="ejemplos-prácticos"></a>

## 🎬 Ejemplos prácticos

### Ejemplo 1: archivo shadow de Linux
```bash
# Combinar los archivos
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Recuperar con rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostrar los resultados
john --show hashes.txt
```

### Ejemplo 2: NTLM de Windows
```bash
# A partir de la salida de hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Recuperar
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Ejemplo 3: contraseña de un archivo ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Ejemplo 4: frase de contraseña de una clave SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Ejemplo 5: hashes MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Ejemplo 6: hashes de aplicaciones web
```bash
# Hash bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Ejemplo 7: con reglas
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Ejemplo 8: ataque incremental
```bash
# Probar todas las combinaciones de 6 a 8 caracteres de minúsculas y dígitos
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Recuperación básica | `john hashes.txt` |
| Ataque de diccionario | `john --wordlist=rockyou.txt hashes.txt` |
| Con reglas | `john --wordlist=words.txt --rules hashes.txt` |
| Mostrar las contraseñas recuperadas | `john --show hashes.txt` |
| Formato específico | `john --format=raw-md5 hashes.txt` |
| Incremental | `john --incremental hashes.txt` |
| Restaurar una sesión | `john --restore=session` |
| Listar formatos | `john --list=formats` |

### Herramientas 2john

| Herramienta | Finalidad |
|------|---------|
| `unshadow` | Archivos passwd/shadow de Linux |
| `zip2john` | Archivos ZIP |
| `rar2john` | Archivos RAR |
| `pdf2john` | Archivos PDF |
| `office2john` | Documentos de Office |
| `ssh2john` | Claves SSH |
| `keepass2john` | Bases de datos KeePass |

### Formatos habituales

| Hash | Identificador de formato |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="consejos-y-buenas-prácticas"></a>

## 💡 Consejos y buenas prácticas

### Consejos de eficiencia

1. **Probar primero el modo Single**
   ```bash
   john --single hashes.txt
   ```

2. **Usar listas de palabras adaptadas al objetivo**
   ```bash
   # Nombre de la empresa, ciudad, etc.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Empezar con listas de palabras pequeñas**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Después, probar listas más grandes
   ```

4. **Combinar modos**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Consejos de rendimiento

```bash
# Usar varios núcleos
john --fork=4 hashes.txt

# Aceleración mediante GPU (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** John the Ripper es una herramienta potente de recuperación de contraseñas que solo debe utilizarse para **fines autorizados**.
> 
> - ✅ Auditar las contraseñas propias
> - ✅ Realizar pruebas con autorización expresa
> - ✅ Evaluaciones de seguridad autorizadas
> - ❌ No recuperar contraseñas sin permiso
> - ❌ El acceso sin autorización es ilegal
> 
> **La recuperación de contraseñas sin autorización es ilegal y puede dar lugar a cargos penales.**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [Sitio oficial de John the Ripper](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [John Wiki](https://openwall.info/wiki/john)

### Listas de palabras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Guías rápidas relacionadas
- [Hydra](../../Hydra/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)

---

<p align="center">
  <b>🔨 ¡Domina la recuperación de contraseñas!</b><br>
  <i>Recupera contraseñas siempre de forma responsable</i>
</p>
