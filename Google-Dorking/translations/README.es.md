# 🔍 Google Dorking: guía de consulta completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  ██████╗  ██████╗  ██████╗ ██╗     ███████╗
  ██╔════╝ ██╔═══██╗██╔═══██╗██╔════╝ ██║     ██╔════╝
  ██║  ███╗██║   ██║██║   ██║██║  ███╗██║     █████╗  
  ██║   ██║██║   ██║██║   ██║██║   ██║██║     ██╔══╝  
  ╚██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝███████╗███████╗
   ╚═════╝  ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚══════╝
  ██████╗  ██████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗ 
  ██╔══██╗██╔═══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝ 
  ██║  ██║██║   ██║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗
  ██║  ██║██║   ██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║
  ██████╔╝╚██████╔╝██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

<p align="center">
  <img src="https://img.shields.io/badge/Google-Dorking-red?style=for-the-badge" alt="Google Dorking">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Information-Gathering-green?style=for-the-badge" alt="Recopilación de información">
</p>

<p align="center">
  <b>🎯 Técnicas avanzadas de búsqueda en Google para investigadores de seguridad</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Operadores básicos](#-operadores-básicos)
- [Operadores avanzados](#-operadores-avanzados)
- [Combinación de operadores](#-combinación-de-operadores)
- [Descubrimiento de archivos](#-descubrimiento-de-archivos)
- [Listado de directorios](#-listado-de-directorios)
- [Paneles de administración](#-paneles-de-administración)
- [Bases de datos y SQL](#-bases-de-datos-y-sql)
- [Archivos de configuración](#-archivos-de-configuración)
- [Credenciales y secretos](#-credenciales-y-secretos)
- [Servidores vulnerables](#-servidores-vulnerables)
- [Dorks para programas de recompensas por vulnerabilidades](#-dorks-para-programas-de-recompensas-por-vulnerabilidades)
- [Búsquedas específicas por CMS](#-búsquedas-específicas-por-cms)
- [Referencia rápida](#-referencia-rápida)
- [Herramientas y automatización](#-herramientas-y-automatización)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Google Dorking** (también conocido como Google Hacking) es una técnica que utiliza operadores de búsqueda avanzados para encontrar información sensible expuesta en Internet. Un *dork* es una consulta de búsqueda preparada para localizar un tipo concreto de información.

### ¿Qué puedes encontrar?

| Categoría | Ejemplos |
|----------|----------|
| **Archivos sensibles** | Copias de seguridad, configuraciones, registros y bases de datos |
| **Paneles expuestos** | Paneles de administración, paneles de control y phpMyAdmin |
| **Credenciales** | Contraseñas, claves de API y tokens |
| **Vulnerabilidades** | Inyección SQL y directorios abiertos |
| **Datos personales** | Correos electrónicos, números de teléfono y documentos |

### URL de los motores de búsqueda

| Motor | URL |
|--------|-----|
| **Google** | https://www.google.com |
| **Bing** | https://www.bing.com |
| **DuckDuckGo** | https://duckduckgo.com |
| **Yandex** | https://yandex.com |

---

## 🔧 Operadores básicos

### Operador site

> **Aclaración:** Los resultados de `site:` no son un inventario exhaustivo. `-www` excluye un término de búsqueda; no garantiza excluir únicamente el subdominio `www`. Consulta la [documentación oficial del operador site](https://developers.google.com/search/docs/monitor-debug/search-operators/all-search-site).

Limita la búsqueda a un dominio concreto:

```
site:example.com                     # Solo example.com
site:*.example.com                   # Todos los subdominios
site:example.com -www                # Excluir www
site:.gov                            # Sitios gubernamentales
site:.edu                            # Sitios educativos
```

### Operador inurl

Busca en la URL:

```
inurl:admin                          # admin en la URL
inurl:login                          # Páginas de inicio de sesión
inurl:dashboard                      # Paneles de control
inurl:config                         # Archivos de configuración
inurl:backup                         # Copias de seguridad
```

### Operador intitle

Busca en el título de la página:

```
intitle:"index of"                   # Listados de directorios
intitle:admin                        # Páginas de administración
intitle:login                        # Páginas de inicio de sesión
intitle:"dashboard"                  # Paneles de control
intitle:"control panel"              # Paneles de control
```

### Operador intext

Busca en el contenido de la página:

```
intext:"password"                    # Contraseña en el texto
intext:"username"                    # Nombre de usuario en el texto
intext:"api_key"                     # Claves de API
intext:"secret"                      # Secretos
```

### Operador filetype

Busca tipos de archivo concretos:

```
filetype:pdf                         # Archivos PDF
filetype:doc                         # Documentos de Word
filetype:xls                         # Archivos de Excel
filetype:sql                         # Archivos SQL
filetype:log                         # Archivos de registro
filetype:bak                         # Copias de seguridad
filetype:env                         # Archivos de variables de entorno
```

---

## ⚡ Operadores avanzados

### Variantes con el prefijo all

```
allinurl:admin login                 # Todas las palabras en la URL
allintitle:admin panel               # Todas las palabras en el título
allintext:password username          # Todas las palabras en el texto
```

### Caché y enlaces

> **Nota de vigencia:** Google retiró el operador `cache:` en septiembre de 2024; las consultas que lo utilizan se conservan como ejemplos históricos. Consulta el [registro oficial de cambios de Google](https://developers.google.com/search/updates#september-2024). Los operadores históricos de esta sección pueden dejar de estar disponibles.

```
cache:example.com                    # Versión en caché
link:example.com                     # Páginas que enlazan al sitio
related:example.com                  # Sitios relacionados
```

### Intervalo de fechas

```
after:2023-01-01                     # Después de la fecha
before:2024-01-01                    # Antes de la fecha
daterange:2458850-2459215            # Intervalo de fechas julianas
```

### Comodines y OR

```
admin * panel                        # Comodín
admin OR login                       # Cualquiera de los términos
"admin panel"                        # Frase exacta
-inurl:https                         # Excluir HTTPS
```

### Intervalo numérico

> **Aclaración:** Un intervalo numérico filtra coincidencias de búsqueda; no enumera ni solicita todos los valores de un parámetro de la aplicación.

```
inurl:id= 1..1000                    # Intervalo numérico
price $100..$500                     # Intervalo de precios
```

---

## 🔗 Combinación de operadores

### Combinaciones básicas

```
site:example.com filetype:pdf
site:example.com inurl:admin
site:example.com intitle:login
site:example.com intext:password
```

### Combinaciones avanzadas

```
site:example.com filetype:sql intext:password
site:example.com inurl:admin intitle:login
site:example.com filetype:log intext:error
site:example.com ext:php inurl:config
```

### Combinaciones para programas de recompensas

```
site:target.com (filetype:pdf | filetype:doc | filetype:xls)
site:target.com (inurl:admin | inurl:login | inurl:dashboard)
site:target.com (ext:sql | ext:bak | ext:log)
```

---

## 📁 Descubrimiento de archivos

### Documentos sensibles

```
# Documentos de ofimática
site:target.com filetype:doc
site:target.com filetype:docx
site:target.com filetype:xls
site:target.com filetype:xlsx
site:target.com filetype:ppt
site:target.com filetype:pdf

# Archivos de texto
site:target.com filetype:txt
site:target.com filetype:rtf
site:target.com filetype:csv
```

### Copias de seguridad

```
site:target.com filetype:bak
site:target.com filetype:backup
site:target.com filetype:old
site:target.com filetype:orig
site:target.com ext:bak
site:target.com inurl:backup
site:target.com intitle:"backup"
```

### Archivos de bases de datos

```
site:target.com filetype:sql
site:target.com filetype:db
site:target.com filetype:mdb
site:target.com filetype:sqlite
site:target.com filetype:dump
site:target.com ext:sql intext:INSERT
```

### Archivos de registro

```
site:target.com filetype:log
site:target.com ext:log
site:target.com inurl:log
site:target.com intext:"error" filetype:log
site:target.com intext:"exception" filetype:log
site:target.com intext:"fatal" filetype:log
```

### Archivos de configuración

```
site:target.com filetype:conf
site:target.com filetype:config
site:target.com filetype:cfg
site:target.com filetype:ini
site:target.com filetype:env
site:target.com filetype:yml
site:target.com filetype:yaml
site:target.com filetype:xml
site:target.com filetype:json
```

### Código fuente

```
site:target.com filetype:php
site:target.com filetype:asp
site:target.com filetype:aspx
site:target.com filetype:jsp
site:target.com filetype:py
site:target.com filetype:rb
site:target.com filetype:js
```

---

## 📂 Listado de directorios

### Búsquedas con index of

```
intitle:"index of" site:target.com
intitle:"index of /" site:target.com
intitle:"directory listing" site:target.com
intitle:"parent directory" site:target.com
```

### Directorios concretos

```
intitle:"index of" "backup" site:target.com
intitle:"index of" "password" site:target.com
intitle:"index of" "admin" site:target.com
intitle:"index of" "config" site:target.com
intitle:"index of" "ftp" site:target.com
intitle:"index of" "logs" site:target.com
intitle:"index of" "uploads" site:target.com
intitle:"index of" ".git" site:target.com
intitle:"index of" ".svn" site:target.com
```

### Tipos de archivo en directorios

```
intitle:"index of" "*.sql" site:target.com
intitle:"index of" "*.bak" site:target.com
intitle:"index of" "*.log" site:target.com
intitle:"index of" "*.zip" site:target.com
intitle:"index of" "*.tar.gz" site:target.com
```

---

## 🔐 Paneles de administración

### Administración genérica

```
site:target.com inurl:admin
site:target.com inurl:administrator
site:target.com inurl:adminlogin
site:target.com inurl:admin/login
site:target.com inurl:admin.php
site:target.com inurl:admin.asp
site:target.com intitle:admin
site:target.com intitle:"admin login"
site:target.com intitle:"admin panel"
site:target.com intitle:"administrator"
```

### cPanel y alojamiento web

```
site:target.com inurl:cpanel
site:target.com inurl:webmail
site:target.com inurl:whm
site:target.com intitle:"cPanel"
site:target.com inurl:2082
site:target.com inurl:2083
site:target.com inurl:2086
site:target.com inurl:2087
```

### Administración de bases de datos

```
site:target.com inurl:phpmyadmin
site:target.com inurl:phpMyAdmin
site:target.com intitle:"phpMyAdmin"
site:target.com inurl:adminer
site:target.com inurl:pgadmin
site:target.com intitle:"pgAdmin"
site:target.com inurl:mysql
```

### Paneles de control

```
site:target.com intitle:"dashboard"
site:target.com intitle:"control panel"
site:target.com inurl:portal
site:target.com inurl:cms
site:target.com inurl:backend
```

---

## 💾 Bases de datos y SQL

### Archivos SQL

```
site:target.com filetype:sql
site:target.com filetype:sql intext:password
site:target.com filetype:sql intext:"INSERT INTO"
site:target.com filetype:sql intext:"CREATE TABLE"
site:target.com ext:sql "INSERT INTO `users`"
```

### Errores SQL

```
site:target.com "SQL syntax" error
site:target.com "mysql_fetch_array"
site:target.com "mysql_num_rows"
site:target.com "mysql_connect"
site:target.com "mysqli_"
site:target.com "ORA-" error
site:target.com "PostgreSQL" error
site:target.com "ODBC" error
```

### Indicadores de inyección SQL

> **Aclaración:** La presencia de parámetros en una URL indica dónde investigar; por sí sola no demuestra una inyección SQL.

```
site:target.com inurl:id=
site:target.com inurl:page=
site:target.com inurl:cat=
site:target.com inurl:item=
site:target.com inurl:pid=
site:target.com inurl:product_id=
site:target.com inurl:news_id=
```

### Volcados de bases de datos

```
site:target.com "dump" filetype:sql
site:target.com "database dump" filetype:sql
site:target.com filetype:sql "-- MySQL dump"
site:target.com filetype:sql "-- phpMyAdmin SQL Dump"
```

---

<a id="-archivos-de-configuración"></a>
## ⚙️ Archivos de configuración

### Configuración del servidor web

```
site:target.com filetype:conf
site:target.com filetype:conf intext:password
site:target.com "httpd.conf"
site:target.com "nginx.conf"
site:target.com ".htaccess"
site:target.com ".htpasswd"
site:target.com "web.config"
```

### Configuración de la aplicación

```
site:target.com filetype:env
site:target.com ".env" filetype:env
site:target.com "config.php" intext:password
site:target.com "settings.php" intext:password
site:target.com "database.php" intext:password
site:target.com "wp-config.php"
site:target.com "configuration.php"
site:target.com "config.yml"
site:target.com "config.json"
```

### Configuración de Git

```
site:target.com ".git/config"
site:target.com intitle:"index of" ".git"
site:target.com inurl:".git"
site:target.com filetype:git
```

### AWS y servicios en la nube

```
site:target.com filetype:pem
site:target.com filetype:ppk
site:target.com "aws_access_key_id"
site:target.com "aws_secret_access_key"
site:target.com "AKIA"                    # Prefijo de clave de AWS
site:target.com "credentials" filetype:json
```

---

## 🔑 Credenciales y secretos

### Contraseñas

```
site:target.com intext:password
site:target.com intext:"password is"
site:target.com intext:"default password"
site:target.com filetype:txt password
site:target.com filetype:log password
site:target.com "password" filetype:xls
site:target.com "password" filetype:csv
```

### Combinaciones de nombre de usuario y contraseña

```
site:target.com intext:username intext:password
site:target.com "username" "password" filetype:txt
site:target.com "login" "password" filetype:xls
site:target.com intext:"username:" intext:"password:"
```

### Claves de API y tokens

```
site:target.com intext:api_key
site:target.com intext:apikey
site:target.com intext:api-key
site:target.com intext:"api key"
site:target.com intext:access_token
site:target.com intext:auth_token
site:target.com intext:secret_key
site:target.com intext:bearer
```

### Direcciones de correo electrónico

```
site:target.com "@target.com"
site:target.com filetype:xls "@target.com"
site:target.com filetype:csv "email"
site:target.com intext:"@gmail.com"
```

### Claves privadas

```
site:target.com "BEGIN RSA PRIVATE KEY"
site:target.com "BEGIN DSA PRIVATE KEY"
site:target.com "BEGIN EC PRIVATE KEY"
site:target.com "BEGIN PRIVATE KEY"
site:target.com filetype:pem
site:target.com filetype:key
site:target.com "id_rsa"
site:target.com "id_dsa"
```

---

<a id="-servidores-vulnerables"></a>
## ⚠️ Servidores vulnerables

### Directorios abiertos

```
intitle:"index of" site:target.com
intitle:"directory listing" site:target.com
intitle:"Apache" "server at" site:target.com
```

### Páginas predeterminadas

```
site:target.com intitle:"Apache2 Ubuntu Default Page"
site:target.com intitle:"Welcome to nginx"
site:target.com intitle:"Test Page for Apache"
site:target.com intitle:"IIS Windows Server"
site:target.com intitle:"Welcome to CentOS"
```

### Páginas de error

```
site:target.com "404 Not Found" inurl:admin
site:target.com "500 Internal Server Error"
site:target.com "Error establishing database connection"
site:target.com "Parse error" "syntax error"
site:target.com "Warning:" "mysql_"
site:target.com "Fatal error:" "php"
```

### Servicios expuestos

```
site:target.com intitle:"Webmin"
site:target.com intitle:"Jenkins"
site:target.com intitle:"Grafana"
site:target.com intitle:"Kibana"
site:target.com intitle:"Elasticsearch"
site:target.com intitle:"Solr Admin"
site:target.com intitle:"RabbitMQ Management"
site:target.com intitle:"Redis Commander"
```

---

## 🐛 Dorks para programas de recompensas por vulnerabilidades

### Hallazgos rápidos

```
# Archivos sensibles
site:target.com (filetype:sql | filetype:bak | filetype:log | filetype:env)

# Paneles de administración
site:target.com (inurl:admin | inurl:login | inurl:dashboard)

# Archivos de configuración
site:target.com (filetype:conf | filetype:config | filetype:ini | filetype:env)

# Credenciales
site:target.com (intext:password | intext:api_key | intext:secret)
```

### Descubrimiento de subdominios

```
site:*.target.com -www
site:*.target.com -www -mail
site:*.*.target.com
```

### Endpoints de API (puntos de acceso)

```
site:target.com inurl:api
site:target.com inurl:api/v1
site:target.com inurl:api/v2
site:target.com inurl:/rest/
site:target.com inurl:graphql
site:target.com inurl:swagger
site:target.com inurl:swagger-ui
site:target.com filetype:json inurl:api
```

### Desarrollo y preproducción

```
site:dev.target.com
site:staging.target.com
site:test.target.com
site:uat.target.com
site:qa.target.com
site:demo.target.com
site:beta.target.com
site:sandbox.target.com
```

### Páginas de autenticación

```
site:target.com inurl:login
site:target.com inurl:signin
site:target.com inurl:signup
site:target.com inurl:register
site:target.com inurl:auth
site:target.com inurl:oauth
site:target.com inurl:sso
site:target.com inurl:forgot
site:target.com inurl:reset
```

### Subida de archivos

```
site:target.com inurl:upload
site:target.com intitle:upload
site:target.com inurl:file
site:target.com "choose file"
site:target.com "upload file"
```

---

## 📱 Búsquedas específicas por CMS

### WordPress

```
site:target.com inurl:wp-content
site:target.com inurl:wp-includes
site:target.com inurl:wp-admin
site:target.com inurl:wp-login
site:target.com "wp-config.php"
site:target.com filetype:sql "wp_users"
site:target.com intitle:"WordPress" inurl:readme
```

### Joomla

```
site:target.com inurl:administrator
site:target.com inurl:com_
site:target.com "configuration.php"
site:target.com "Joomla"
```

### Drupal

```
site:target.com inurl:node/
site:target.com inurl:sites/default/files
site:target.com "Drupal"
site:target.com "CHANGELOG.txt"
```

### Magento

```
site:target.com inurl:app/etc/local.xml
site:target.com inurl:downloader
site:target.com "Magento"
```

---

## 📊 Referencia rápida

### Operadores esenciales

| Operador | Función | Ejemplo |
|----------|----------|---------|
| `site:` | Restringe a un dominio | `site:target.com` |
| `inurl:` | Busca en la URL | `inurl:admin` |
| `intitle:` | Busca en el título | `intitle:login` |
| `intext:` | Busca en el contenido | `intext:password` |
| `filetype:` | Extensión de archivo | `filetype:sql` |
| `ext:` | Extensión de archivo | `ext:pdf` |
| `cache:` | Versión en caché | `cache:target.com` |
| `"..."` | Frase exacta | `"admin panel"` |
| `-` | Excluir | `-site:www` |
| `OR` / `|` | Cualquiera de los términos | `admin OR login` |
| `*` | Comodín | `admin * panel` |

### Diez dorks destacados para programas de recompensas

| # | Dork | Finalidad |
|---|------|---------|
| 1 | `site:target.com filetype:sql` | Archivos de bases de datos |
| 2 | `site:target.com inurl:admin` | Paneles de administración |
| 3 | `site:target.com filetype:log` | Archivos de registro |
| 4 | `site:target.com filetype:env` | Archivos de variables de entorno |
| 5 | `site:target.com intext:password filetype:txt` | Contraseñas |
| 6 | `intitle:"index of" site:target.com` | Listado de directorios |
| 7 | `site:target.com inurl:api` | Endpoints de API |
| 8 | `site:target.com filetype:bak` | Copias de seguridad |
| 9 | `site:*.target.com -www` | Subdominios |
| 10 | `site:target.com "api_key"` | Claves de API |

---

<a id="-herramientas-y-automatización"></a>
## 🛠️ Herramientas y automatización

### Herramientas de Google Dorking

| Herramienta | Descripción | URL |
|------|-------------|-----|
| **Google Hacking Database** | Colección de dorks de Exploit-DB | [GHDB](https://www.exploit-db.com/google-hacking-database) |
| **DorkSearch** | Generador de dorks | [DorkSearch](https://dorksearch.com) |
| **Pagodo** | Google Dorking pasivo | GitHub |
| **GooDork** | Herramienta de dorking en Python | GitHub |
| **GooFuzz** | Fuzzing (búsqueda con entradas variables) mediante Google | GitHub |

### Sitios web de dorking

```
# Google Hacking Database
https://www.exploit-db.com/google-hacking-database

# Pentest-Tools
https://pentest-tools.com/information-gathering/google-hacking

# DorkSearch
https://dorksearch.com
```

### Ejemplo de script de automatización

```bash
#!/bin/bash
# Automatización sencilla de dorks de Google

TARGET="target.com"

DORKS=(
    "site:$TARGET filetype:sql"
    "site:$TARGET filetype:log"
    "site:$TARGET filetype:env"
    "site:$TARGET inurl:admin"
    "site:$TARGET intext:password"
)

for dork in "${DORKS[@]}"; do
    echo "[*] Dork: $dork"
    echo "URL: https://www.google.com/search?q=$(echo $dork | sed 's/ /+/g')"
    echo ""
done
```

---

## 💡 Consejos y buenas prácticas

### Consejos para programas de recompensas

1. **Empieza con búsquedas amplias y concrétalas después**
   ```
   site:target.com                    # Primero
   site:target.com filetype:sql       # Después, una búsqueda concreta
   ```

2. **Utiliza varios motores de búsqueda**
   - Google, Bing, DuckDuckGo, Yandex
   
3. **Consulta las páginas en caché**
   ```
   cache:target.com/admin
   ```

4. **Busca subdominios**
   ```
   site:*.target.com -www
   ```

5. **Limita la frecuencia de las búsquedas**
   - Google puede bloquear un número excesivo de consultas

### Evitar la detección

```
# Utiliza distintas IP o VPN
# Añade pausas entre búsquedas
# Varía tus patrones de búsqueda
# Utiliza distintos navegadores
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** El uso de Google Dorking en sí es legal, pero **acceder** a datos expuestos sin autorización puede ser ilegal.
> 
> - ✅ Úsalo para pruebas de seguridad autorizadas
> - ✅ Úsalo en programas de recompensas por vulnerabilidades
> - ✅ Comunica las vulnerabilidades de forma responsable
> - ❌ Nunca accedas a datos sin permiso
> - ❌ Nunca descargues ni almacenes datos sensibles
> - ❌ No explotes las vulnerabilidades que encuentres
> 
> **¡Obtén siempre la autorización correspondiente antes de realizar pruebas!**

---

## 📚 Recursos

### Recursos oficiales
- [Operadores de búsqueda de Google](https://support.google.com/websearch/answer/2466433)
- [Google Hacking Database](https://www.exploit-db.com/google-hacking-database)

### Libros y guías
- "Google Hacking for Penetration Testers", de Johnny Long
- Guía de pruebas de OWASP (OWASP Testing Guide)

### Guías de consulta relacionadas
- [Shodan](../../Shodan/translations/README.es.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Encuentra lo que otros ocultan!</b><br>
  <i>Domina el arte del Google Dorking</i>
</p>
