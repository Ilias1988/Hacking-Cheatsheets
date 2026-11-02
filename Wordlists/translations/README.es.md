# 📚 Diccionarios (wordlists): guía de consulta completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗    ██╗ ██████╗ ██████╗ ██████╗ ██╗     ██╗███████╗████████╗███████╗
  ██║    ██║██╔═══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║   ██║██████╔╝██║  ██║██║     ██║███████╗   ██║   ███████╗
  ██║███╗██║██║   ██║██╔══██╗██║  ██║██║     ██║╚════██║   ██║   ╚════██║
  ╚███╔███╔╝╚██████╔╝██║  ██║██████╔╝███████╗██║███████║   ██║   ███████║
   ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Wordlists-Reference-red?style=for-the-badge" alt="Diccionarios">
  <img src="https://img.shields.io/badge/SecLists-orange?style=for-the-badge" alt="SecLists">
  <img src="https://img.shields.io/badge/Brute_Force-blue?style=for-the-badge" alt="Fuerza bruta">
  <img src="https://img.shields.io/badge/Fuzzing-green?style=for-the-badge" alt="Fuzzing">
</p>

<p align="center">
  <b>📖 Guía de consulta completa de diccionarios para pruebas de penetración</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Diccionarios de contraseñas](#-diccionarios-de-contraseñas)
- [Diccionarios de directorios y archivos](#-diccionarios-de-directorios-y-archivos)
- [Diccionarios de subdominios](#-diccionarios-de-subdominios)
- [Diccionarios de nombres de usuario](#-diccionarios-de-nombres-de-usuario)
- [Diccionarios para fuzzing web](#-diccionarios-para-fuzzing-web)
- [Colección SecLists](#-colección-seclists)
- [Diccionarios predeterminados de Kali](#-diccionarios-predeterminados-de-kali)
- [Herramientas para crear diccionarios personalizados](#-herramientas-para-crear-diccionarios-personalizados)
- [Descarga rápida](#-descarga-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)

---

## 🔐 Diccionarios de contraseñas

> **Vigencia de las referencias:** Los tamaños, recuentos, rutas y árboles de esta guía son los del original y pueden variar entre versiones y paquetes. Algunos archivos se distribuyen comprimidos: en SecLists, RockYou aparece como `rockyou.txt.tar.gz` dentro de Leaked-Databases. Consulta el [repositorio oficial de SecLists](https://github.com/danielmiessler/SecLists/tree/master/Passwords/Leaked-Databases) para localizar cada archivo; se conservan los nombres y ejemplos originales.

### Los más utilizados

| Diccionario | Tamaño | Líneas | Origen | Aplicación |
|----------|------|-------|--------|----------|
| **rockyou.txt** | 139 MB | 14.3M | Kali/SecLists | Recuperación de contraseñas |
| **darkweb2017-top10000.txt** | 83 KB | 10K | SecLists | Contraseñas habituales |
| **xato-net-10-million-passwords.txt** | 83 MB | 10M | SecLists | Diccionario amplio de contraseñas |
| **common-passwords-win.txt** | - | - | SecLists | Contraseñas de Windows |
| **10-million-password-list-top-1000000.txt** | 8.1 MB | 1M | SecLists | Un millón de las contraseñas más frecuentes |

### Diccionarios de contraseñas por tipo

```
SecLists/Passwords/
├── Common-Credentials/
│   ├── 10-million-password-list-top-100.txt
│   ├── 10-million-password-list-top-1000.txt
│   ├── 10-million-password-list-top-10000.txt
│   ├── 10-million-password-list-top-100000.txt
│   └── 10-million-password-list-top-1000000.txt
├── Leaked-Databases/
│   └── rockyou.txt
├── darkweb2017-top1000.txt
├── darkweb2017-top10000.txt
└── Default-Credentials/
    ├── default-passwords.txt
    └── tomcat-betterdefaultpasslist.txt
```

### Diccionarios de contraseñas especializados

| Diccionario | Finalidad | Ubicación |
|----------|---------|----------|
| **wifi-passwords.txt** | Recuperación de claves WiFi | SecLists/Passwords/ |
| **probable-v2-top12000.txt** | Análisis estadístico | SecLists/Passwords/ |
| **seasons-only.txt** | Patrones estacionales | SecLists/Passwords/Honeypot-Captures/ |
| **twitter-banned.txt** | Contraseñas prohibidas | SecLists/Passwords/ |
| **cirt-default-passwords.txt** | Credenciales predeterminadas | SecLists/Passwords/Default-Credentials/ |

### Ubicaciones en Kali

```bash
# RockYou (descomprimir primero)
/usr/share/wordlists/rockyou.txt.gz
gunzip /usr/share/wordlists/rockyou.txt.gz

# John the Ripper
/usr/share/john/password.lst

# Metasploit
/usr/share/metasploit-framework/data/wordlists/
```

---

## 📂 Diccionarios de directorios y archivos

### Descubrimiento de directorios por fuerza bruta

> **Aclaración:** `common.txt` suele corresponder a DIRB; el original lo atribuye a DirBuster. Son colecciones distintas y las rutas dependen del paquete instalado.

| Diccionario | Tamaño | Líneas | Origen | Aplicación |
|----------|------|-------|--------|----------|
| **common.txt** | 36 KB | 4.6K | DirBuster | Exploración rápida |
| **directory-list-2.3-small.txt** | 756 KB | 87K | DirBuster | Enumeración reducida |
| **directory-list-2.3-medium.txt** | 1.9 MB | 220K | DirBuster | Enumeración estándar |
| **directory-list-2.3-big.txt** | 14 MB | 1.2M | DirBuster | Enumeración completa |
| **big.txt** | 210 KB | 20K | SecLists | Directorios habituales |

### Serie RAFT (muy recomendable)

```
SecLists/Discovery/Web-Content/
├── raft-large-directories.txt      # 62K directorios
├── raft-large-files.txt            # 37K archivos
├── raft-large-words.txt            # 120K palabras
├── raft-medium-directories.txt     # 30K directorios
├── raft-medium-files.txt           # 17K archivos
├── raft-medium-words.txt           # 63K palabras
├── raft-small-directories.txt      # 20K directorios
├── raft-small-files.txt            # 11K archivos
└── raft-small-words.txt            # 43K palabras
```

### Listas de extensiones de archivo

| Diccionario | Finalidad |
|----------|---------|
| **web-extensions.txt** | Extensiones web habituales |
| **backup-extensions.txt** | Copias de seguridad (.bak, .old) |
| **raft-large-extensions.txt** | Lista amplia de extensiones |

### Descubrimiento especializado

| Diccionario | Finalidad | Líneas |
|----------|---------|-------|
| **apache.txt** | Específico de Apache | - |
| **nginx.txt** | Específico de Nginx | - |
| **iis.txt** | Específico de IIS | - |
| **api-endpoints.txt** | Descubrimiento de API | - |
| **graphql.txt** | Endpoints de GraphQL (puntos de acceso) | - |
| **swagger.txt** | Swagger/OpenAPI | - |

### Ubicaciones habituales

```bash
# DirBuster
/usr/share/dirbuster/wordlists/
/usr/share/dirb/wordlists/

# Descubrimiento con SecLists
/usr/share/seclists/Discovery/Web-Content/

# Descargas rápidas
common.txt
directory-list-2.3-medium.txt
raft-medium-directories.txt
```

---

## 🌐 Diccionarios de subdominios

### Diccionarios de subdominios habituales

| Diccionario | Líneas | Origen | Aplicación |
|----------|-------|--------|----------|
| **subdomains-top1million-5000.txt** | 5K | SecLists | Enumeración rápida |
| **subdomains-top1million-20000.txt** | 20K | SecLists | Enumeración estándar |
| **subdomains-top1million-110000.txt** | 110K | SecLists | Enumeración exhaustiva |
| **dns-Jhaddix.txt** | 2M+ | SecLists | Programas de recompensas por vulnerabilidades |
| **fierce-hostlist.txt** | - | SecLists | Compatible con Fierce |
| **combined_subdomains.txt** | 648K | SecLists | Lista combinada |

### Ubicación de los diccionarios de subdominios

```
SecLists/Discovery/DNS/
├── subdomains-top1million-5000.txt
├── subdomains-top1million-20000.txt
├── subdomains-top1million-110000.txt
├── dns-Jhaddix.txt                    # ¡Especialmente útil en programas de recompensas!
├── combined_subdomains.txt
├── deepmagic.com-prefixes-top500.txt
├── deepmagic.com-prefixes-top50000.txt
├── fierce-hostlist.txt
├── namelist.txt
├── shubs-subdomains.txt
└── bitquark-subdomains-top100000.txt
```

### Recomendados para programas de recompensas

```bash
# Metodología de Jason Haddix
dns-Jhaddix.txt              # Más de 2M de subdominios

# Combinar para obtener una cobertura más amplia
cat subdomains-top1million-110000.txt dns-Jhaddix.txt | sort -u > mega-subdomain.txt
```

---

## 👤 Diccionarios de nombres de usuario

### Diccionarios habituales de nombres de usuario

| Diccionario | Líneas | Finalidad |
|----------|-------|---------|
| **top-usernames-shortlist.txt** | 17 | Comprobación rápida |
| **xato-net-10-million-usernames.txt** | 10M | Lista amplia |
| **cirt-default-usernames.txt** | - | Usuarios predeterminados |
| **names.txt** | - | Nombres habituales |
| **familynames-usa-top1000.txt** | 1K | Apellidos |

### Ubicación de los diccionarios de nombres de usuario

```
SecLists/Usernames/
├── top-usernames-shortlist.txt
├── xato-net-10-million-usernames.txt
├── cirt-default-usernames.txt
├── Names/
│   ├── names.txt
│   ├── familynames-usa-top1000.txt
│   └── malenames-usa-top1000.txt
└── Honeypot-Captures/
    └── multiplesources-users-fabian-fingerle.de.txt
```

### Credenciales predeterminadas

| Servicio | Diccionario |
|---------|----------|
| **FTP** | ftp-betterdefaultpasslist.txt |
| **SSH** | ssh-betterdefaultpasslist.txt |
| **Tomcat** | tomcat-betterdefaultpasslist.txt |
| **MySQL** | mysql-betterdefaultpasslist.txt |
| **MSSQL** | mssql-betterdefaultpasslist.txt |
| **PostgreSQL** | postgres-betterdefaultpasslist.txt |

---

## 💉 Diccionarios para fuzzing web

### Inyección SQL

```
SecLists/Fuzzing/SQLi/
├── Generic-SQLi.txt
├── quick-SQLi.txt
├── MSSQL.fuzzdb.txt
├── MySQL.fuzzdb.txt
├── PostgreSQL.fuzzdb.txt
└── oracle.fuzzdb.txt
```

### XSS (inyección de scripts en páginas web)

```
SecLists/Fuzzing/XSS/
├── XSS-Bypass-Strings-BruteLogic.txt
├── XSS-Cheat-Sheet-PortSwigger.txt
├── XSS-Jhaddix.txt
├── XSS-RSNAKE.txt
├── XSS-Vectors-Mario.txt
└── xss-without-parentheses-semi-colons-portswigger.txt
```

### LFI/RFI (inclusión de archivos locales o remotos)

```
SecLists/Fuzzing/LFI/
├── LFI-Jhaddix.txt
├── LFI-LFISuite-pathtotest-huge.txt
├── LFI-LFISuite-pathtotest.txt
└── LFI-gracefulsecurity-linux.txt
```

### Inyección de comandos

```
SecLists/Fuzzing/command-injection-commix.txt
SecLists/Fuzzing/command-injection-testing-unix.txt
SecLists/Fuzzing/command-injection-testing-windows.txt
```

### SSTI (inyección de plantillas del lado del servidor)

```
SecLists/Fuzzing/SSTI/
├── SSTI.txt
└── tplmap-template-injection.txt
```

### Colecciones para fuzzing

| Diccionario | Finalidad |
|----------|---------|
| **special-chars.txt** | Caracteres especiales |
| **Unicode.txt** | Fuzzing con Unicode |
| **big-list-of-naughty-strings.txt** | Casos límite |
| **fuzz-Bo0oM.txt** | Fuzzing general |
| **burp-parameter-names.txt** | Nombres de parámetros |

---

## 📦 Colección SecLists

### Acerca de SecLists

**SecLists** es un recurso de apoyo para el pentester: reúne distintos tipos de listas utilizadas en las evaluaciones de seguridad. Una *wordlist* es un diccionario de palabras, nombres o entradas candidatas que una herramienta utiliza durante las pruebas; el *fuzzing* consiste en probar entradas variadas para observar el comportamiento de un sistema.

### Instalación

> **Compatibilidad:** Las órdenes apt y las ubicaciones mostradas corresponden a paquetes disponibles en Kali o a repositorios configurados para ello; `seclists` no debe suponerse preinstalado ni disponible en cualquier instalación estándar de Ubuntu/Debian. Véase la [ficha oficial del paquete en Kali](https://www.kali.org/tools/seclists/).

```bash
# Kali Linux (preinstalado)
ls /usr/share/seclists/

# Ubuntu/Debian
sudo apt install seclists

# Clonar con Git
git clone https://github.com/danielmiessler/SecLists.git

# Descarga rápida (diccionarios concretos)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt
```

### Estructura de SecLists

```
SecLists/
├── Discovery/
│   ├── DNS/                    # Diccionarios de subdominios
│   ├── Web-Content/            # Diccionarios de directorios y archivos
│   └── Infrastructure/         # Servicios y puertos
├── Fuzzing/
│   ├── SQLi/                   # Payloads de inyección SQL
│   ├── XSS/                    # Payloads XSS
│   ├── LFI/                    # Payloads LFI/RFI
│   └── command-injection*.txt  # Inyección de comandos
├── Passwords/
│   ├── Common-Credentials/     # Diccionarios de contraseñas
│   ├── Leaked-Databases/       # rockyou, etc.
│   └── Default-Credentials/    # Contraseñas predeterminadas
├── Usernames/
│   ├── Names/                  # Listas de nombres
│   └── top-usernames*.txt      # Listas de nombres de usuario
├── Payloads/
│   └── Anti-Antivirus/         # Evasión de antivirus (AV)
└── Pattern-Matching/
    └── Hunting/                # Patrones de expresiones regulares
```

### Repositorio de GitHub

🔗 https://github.com/danielmiessler/SecLists

---

## 🐧 Diccionarios predeterminados de Kali

> **Corrección del comentario original:** `nmap.lst` enlaza a una lista de contraseñas de Nmap, no a una lista de scripts. Los enlaces simbólicos y directorios mostrados son ejemplos y dependen de los paquetes instalados. Fuente: [documentación oficial de wordlists en Kali](https://www.kali.org/tools/wordlists/).

### Ubicación de los diccionarios

```bash
# Directorio principal de diccionarios
/usr/share/wordlists/

# Contenido
ls -la /usr/share/wordlists/
├── rockyou.txt.gz          # Descomprimir con gunzip
├── dirb/                   # Diccionarios de DIRB
├── dirbuster/              # Diccionarios de DirBuster
├── fasttrack.txt           # Diccionario de Fast-Track
├── fern-wifi/              # Diccionarios de WiFi
├── metasploit/             # Diccionarios de Metasploit
├── nmap.lst                # Lista de scripts de Nmap, según el comentario original
├── wfuzz/                  # Diccionarios de wFuzz
└── seclists -> /usr/share/seclists/
```

### Diccionarios de DirBuster

```bash
ls /usr/share/dirbuster/wordlists/
├── apache-user-enum-1.0.txt
├── apache-user-enum-2.0.txt
├── directories.jbrofuzz
├── directory-list-1.0.txt
├── directory-list-2.3-big.txt
├── directory-list-2.3-medium.txt
├── directory-list-2.3-small.txt
└── directory-list-lowercase-2.3-big.txt
```

### Diccionarios de DIRB

```bash
ls /usr/share/dirb/wordlists/
├── big.txt                  # 20K entradas
├── common.txt               # 4.6K entradas
├── small.txt                # 959 entradas
├── catala.txt               # Catalán
├── euskera.txt              # Euskera
├── extensions_common.txt    # Extensiones
├── indexes.txt              # Archivos de índice
├── mutations_common.txt     # Mutaciones
├── spanish.txt              # Español
├── stress/                  # Pruebas de estrés
└── vulns/                   # Comprobaciones de vulnerabilidades
```

---

<a id="-herramientas-para-crear-diccionarios-personalizados"></a>
## 🛠️ Herramientas para crear diccionarios personalizados

### CeWL (generador de diccionarios personalizados)

```bash
# Instalación
sudo apt install cewl

# Uso básico: recorrer un sitio web
cewl https://target.com -w wordlist.txt

# Especificar la profundidad y la longitud mínima de las palabras
cewl https://target.com -d 3 -m 6 -w wordlist.txt

# Incluir direcciones de correo electrónico
cewl https://target.com -e -w wordlist.txt

# Opciones
-d <depth>      # Profundidad del rastreo (predeterminada: 2)
-m <length>     # Longitud mínima de las palabras (predeterminada: 3)
-w <file>       # Archivo de salida
-e              # Incluir direcciones de correo electrónico
--lowercase     # Convertir a minúsculas
-c              # Contar las apariciones de cada palabra
```

### Crunch (generador de diccionarios)

> **Aclaración:** La cantidad de combinaciones crece con el tamaño del alfabeto y la longitud. El ejemplo de tubería usa el modo 0 de Hashcat, correspondiente a MD5; no es un modo genérico para todos los hashes.

```bash
# Instalación
sudo apt install crunch

# Sintaxis básica
crunch <min-len> <max-len> [charset] -o <output>

# Generar contraseñas de entre 4 y 6 caracteres
crunch 4 6 -o wordlist.txt

# Con caracteres concretos
crunch 8 8 abcdefghijklmnopqrstuvwxyz0123456789 -o wordlist.txt

# Con un patrón
crunch 6 6 -t @@%%^^ -o wordlist.txt
# @ = minúsculas, , = mayúsculas, % = dígito, ^ = carácter especial

# Conjunto de caracteres personalizado
crunch 4 4 -f /usr/share/crunch/charset.lst mixalpha-numeric -o wordlist.txt

# Enviar la salida a Hashcat mediante una tubería
crunch 8 8 0123456789 | hashcat -m 0 hash.txt
```

### CUPP (generador de perfiles de contraseñas de usuarios)

```bash
# Instalación
git clone https://github.com/Mebus/cupp.git
cd cupp

# Modo interactivo basado en perfiles
python3 cupp.py -i

# Datos que se solicitan:
# - Nombre, apellidos y apodo
# - Fecha de nacimiento
# - Datos de la pareja
# - Nombre de la mascota
# - Nombre de la empresa
# - Palabras clave

# Descargar contraseñas habituales
python3 cupp.py -l

# Utilizar un diccionario existente y ampliarlo mediante perfiles
python3 cupp.py -w wordlist.txt
```

### Mentalist (generador de diccionarios con interfaz gráfica)

```bash
# Herramienta con interfaz gráfica para generar diccionarios complejos
# Admite reglas, cambios entre mayúsculas y minúsculas y sustituciones
# Descargar desde: https://github.com/sc0tfree/mentalist
```

### Wordlister (Python)

```python
#!/usr/bin/env python3
# Generador sencillo de diccionarios personalizados
import itertools

# Generar combinaciones
chars = 'abc123'
length = 4

for combo in itertools.product(chars, repeat=length):
    print(''.join(combo))
```

### Reglas de Hashcat

> **Compatibilidad:** Los nombres de reglas dependen de la versión. El repositorio actual utiliza `best66.rule` y `toggles1.rule`, mientras que el original muestra `best64.rule` y `toggles1-5.rule`. Los ejemplos originales se conservan; consulta las [reglas oficiales de Hashcat](https://github.com/hashcat/hashcat/tree/master/rules).

```bash
# Utilizar reglas para transformar el diccionario
hashcat -r /usr/share/hashcat/rules/best64.rule wordlist.txt --stdout

# Reglas habituales
/usr/share/hashcat/rules/
├── best64.rule         # Las 64 mejores reglas
├── rockyou-30000.rule  # Basadas en RockYou
├── d3ad0ne.rule        # Reglas de d3ad0ne
├── dive.rule           # Reglas DIVE
└── toggles1-5.rule     # Alternar entre mayúsculas y minúsculas
```

---

<a id="-descarga-rápida"></a>
## ⬇️ Descarga rápida

### Descargas con comandos de una línea

```bash
# RockYou
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# SecLists completo
git clone --depth 1 https://github.com/danielmiessler/SecLists.git

# Contraseñas habituales (1M)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-1000000.txt

# Diccionario de directorios de tamaño medio
wget https://raw.githubusercontent.com/daviddias/node-dirbuster/master/lists/directory-list-2.3-medium.txt

# Diccionario de subdominios
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/subdomains-top1million-110000.txt

# DNS de Jhaddix (programas de recompensas)
wget https://raw.githubusercontent.com/danielmiessler/SecLists/master/Discovery/DNS/dns-Jhaddix.txt
```

### Clonar repositorios

```bash
# SecLists
git clone https://github.com/danielmiessler/SecLists.git

# FuzzDB
git clone https://github.com/fuzzdb-project/fuzzdb.git

# PayloadsAllTheThings
git clone https://github.com/swisskyrepo/PayloadsAllTheThings.git

# Diccionarios de Assetnote
git clone https://github.com/assetnote/wordlists.git
```

---

## 💡 Consejos y buenas prácticas

### Elegir el diccionario adecuado

| Tarea | Diccionario recomendado |
|------|---------------------|
| **Exploración rápida de directorios** | common.txt, raft-small-directories.txt |
| **Exploración exhaustiva de directorios** | directory-list-2.3-medium.txt |
| **Exploración completa de directorios** | directory-list-2.3-big.txt |
| **Enumeración de subdominios** | subdomains-top1million-20000.txt |
| **DNS para programas de recompensas** | dns-Jhaddix.txt |
| **Recuperación de contraseñas** | rockyou.txt |
| **Prueba rápida de contraseñas** | top-10000.txt |

### Combinar diccionarios

```bash
# Combinar y eliminar duplicados
cat list1.txt list2.txt | sort -u > combined.txt

# Añadir un prefijo a todas las líneas
sed 's/^/admin_/' wordlist.txt > admin_wordlist.txt

# Añadir un sufijo
sed 's/$/.php/' wordlist.txt > php_wordlist.txt

# Ambas operaciones
while read line; do echo "${line}.php"; echo "${line}.html"; done < wordlist.txt > extended.txt
```

### Optimización de diccionarios

```bash
# Ordenar y eliminar duplicados
sort -u wordlist.txt -o wordlist.txt

# Eliminar líneas vacías
sed -i '/^$/d' wordlist.txt

# Eliminar las líneas de menos de 4 caracteres
awk 'length >= 4' wordlist.txt > filtered.txt

# Convertir a minúsculas
tr '[:upper:]' '[:lower:]' < wordlist.txt > lowercase.txt

# Contar líneas
wc -l wordlist.txt
```

### Consejos de rendimiento

1. **Empieza con diccionarios pequeños** - Utiliza primero listas pequeñas y amplíalas después
2. **Utiliza patrones** - Si conoces el formato, utiliza patrones concretos
3. **Combina herramientas** - Genera listas personalizadas y utiliza también las estándar
4. **Listas específicas para el objetivo** - Crea listas adaptadas al objetivo con CeWL

---

## 📊 Referencia rápida

### Diccionarios destacados por categoría

| Categoría | Opción destacada | Ubicación |
|----------|-------------|----------|
| **Contraseñas** | rockyou.txt | SecLists/Passwords/ |
| **Directorios** | directory-list-2.3-medium.txt | DirBuster |
| **Archivos** | raft-large-files.txt | SecLists |
| **Subdominios** | dns-Jhaddix.txt | SecLists/Discovery/DNS/ |
| **Nombres de usuario** | xato-net-10-million-usernames.txt | SecLists/Usernames/ |
| **SQLi** | Generic-SQLi.txt | SecLists/Fuzzing/SQLi/ |
| **XSS** | XSS-Jhaddix.txt | SecLists/Fuzzing/XSS/ |
| **LFI** | LFI-Jhaddix.txt | SecLists/Fuzzing/LFI/ |

### Guía de tamaños

| Tamaño | Líneas | Aplicación |
|------|-------|----------|
| **Pequeño** | < 5K | Pruebas rápidas |
| **Mediano** | 5K - 100K | Enumeración estándar |
| **Grande** | 100K - 1M | Exploración exhaustiva |
| **Enorme** | > 1M | Cobertura completa |

---

## 📚 Recursos

### Repositorios de diccionarios
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Diccionarios de Assetnote](https://github.com/assetnote/wordlists)

### Herramientas
- [CeWL](https://github.com/digininja/CeWL)
- [CUPP](https://github.com/Mebus/cupp)
- [Crunch](https://sourceforge.net/projects/crunch-wordlist/)
- [Mentalist](https://github.com/sc0tfree/mentalist)

### Guías de consulta relacionadas
- [Hydra](../../Hydra/translations/README.es.md)
- [John the Ripper](../../John-The-Ripper/translations/README.es.md)
- [Hashcat](../../Hashcat/translations/README.es.md)
- [Gobuster](../../Gobuster/translations/README.es.md)
- [ffuf](../../ffuf/translations/README.es.md)

---

<p align="center">
  <b>📚 ¡Un buen diccionario marca la diferencia!</b><br>
  <i>Elige con criterio y combina de forma eficaz</i>
</p>
