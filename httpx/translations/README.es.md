# 🌐 httpx - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Herramienta rápida de sondeo HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Rápido">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Herramienta HTTP rápida y polivalente de ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Sondeo y descubrimiento](#-sondeo-y-descubrimiento)
- [Extracción de información](#-extracción-de-información)
- [Filtrado y selección de coincidencias](#-filtrado-y-selección-de-coincidencias)
- [Opciones de salida](#-opciones-de-salida)
- [Funciones avanzadas](#-funciones-avanzadas)
- [Flujo de trabajo de bug bounty](#-flujo-de-trabajo-de-bug-bounty)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**httpx** es una herramienta HTTP rápida y polivalente que realiza varios tipos de sondeo mediante la biblioteca retryablehttp. Está diseñada para investigadores de seguridad y participantes en programas de bug bounty.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Rápido** | Herramienta escrita en Go con alta concurrencia |
| **Varios tipos de sondeo** | Código de estado, título, tecnologías y cabeceras |
| **Detección de tecnologías** | Detección basada en Wappalyzer |
| **Captura de pantalla** | Capturas de pantalla mediante un navegador sin interfaz gráfica |
| **Cadena de herramientas** | Se integra con subfinder y nuclei |
| **Salida JSON** | Compatibilidad con salida estructurada |

### httpx frente a otras herramientas

> **Comparación orientativa:** las estrellas no son resultados de un benchmark. curl sí admite transferencias en paralelo mediante `--parallel`; véase su [manual oficial](https://curl.se/docs/manpage.html).

| Función | httpx | curl | wget |
|---------|-------|------|------|
| **Velocidad** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Concurrencia** | ✅ Sí | ❌ No | ❌ No |
| **Detección de tecnologías** | ✅ Sí | ❌ No | ❌ No |
| **Capturas de pantalla** | ✅ Sí | ❌ No | ❌ No |
| **Cadena de herramientas** | ✅ Sí | ⚠️ Manual | ⚠️ Manual |

---

## 📥 Instalación

### Mediante Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Descarga del binario
```bash
# Descargar desde las versiones publicadas
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Verificar la instalación
```bash
httpx -version
httpx -h
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-u` | URL de un objetivo |
| `-l` | Archivo con una lista de URL |
| `-sc` | Mostrar el código de estado |
| `-title` | Mostrar el título de la página |
| `-td` | Detección de tecnologías |
| `-o` | Archivo de salida |
| `-silent` | Modo silencioso |
| `-json` | Salida JSON |

### Inicio rápido
```bash
# Sondear una URL
httpx -u https://example.com

# Sondear a partir de un archivo
httpx -l urls.txt

# Desde la entrada estándar (mediante una cadena de herramientas)
cat hosts.txt | httpx

# Con subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 Sondeo y descubrimiento

### Sondeo básico

```bash
# Sondear equipos mediante HTTP/HTTPS
cat hosts.txt | httpx

# Incluir el código de estado
cat hosts.txt | httpx -sc

# Sondear puertos concretos
cat hosts.txt | httpx -p 80,443,8080,8443

# Sondear los puertos habituales
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Escaneo de puertos

```bash
# Puertos personalizados
httpx -l hosts.txt -p 80,443,8080,8443

# Intervalo de puertos
httpx -l hosts.txt -p 80-100

# Puertos web habituales
httpx -l hosts.txt -p http:80,https:443
```

### Detección de protocolos

> **Fallback:** `-no-fallback` permite mostrar ambos protocolos sondeados, HTTP y HTTPS; el nombre no significa simplemente deshabilitar todo sondeo alternativo. Los nombres `v1.x.x` del bloque de descarga son marcadores de versión.

```bash
# Sondeo HTTP/HTTPS
cat hosts.txt | httpx

# Seguir redirecciones
cat hosts.txt | httpx -follow-redirects

# Máximo de redirecciones
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Sin fallback
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Extracción de información

### Códigos de estado y títulos

```bash
# Código de estado
httpx -l urls.txt -sc

# Título de la página
httpx -l urls.txt -title

# Ambos
httpx -l urls.txt -sc -title

# Longitud del contenido
httpx -l urls.txt -cl

# Servidor web
httpx -l urls.txt -server

# Toda la información básica
httpx -l urls.txt -sc -title -cl -server
```

### Detección de tecnologías

```bash
# Detectar tecnologías
httpx -l urls.txt -td

# Detección de tecnologías (detallada)
httpx -l urls.txt -tech-detect

# Con otra información
httpx -l urls.txt -sc -title -td
```

### Cabeceras y respuesta

> **Versiones:** la ayuda actual documenta `-include-response-header` como una opción booleana para JSON, no como selector de nombres de cabeceras. Verifica las opciones de estos ejemplos en la [referencia oficial de httpx](https://docs.projectdiscovery.io/opensource/httpx/usage).

```bash
# Cabeceras de respuesta
httpx -l urls.txt -response-header

# Cabecera concreta
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hash del cuerpo de la respuesta
httpx -l urls.txt -hash md5

# Vista previa del cuerpo
httpx -l urls.txt -bp
```

### Información IP y DNS

```bash
# Dirección IP
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# Detección de CDN
httpx -l urls.txt -cdn

# Información del ASN
httpx -l urls.txt -asn

# Toda la información DNS
httpx -l urls.txt -ip -cname -cdn -asn
```

### Información SSL/TLS

> **Alcance:** `-tls-probe` sondea los dominios extraídos de certificados TLS; `-tls-grab` recopila información TLS. Son acciones diferentes.

```bash
# Sondeo TLS
httpx -l urls.txt -tls-probe

# Recopilación de datos TLS
httpx -l urls.txt -tls-grab

# Información del certificado
httpx -l urls.txt -tlsgrab
```

### Hash del favicon

```bash
# Obtener el hash del favicon (útil para búsquedas en Shodan)
httpx -l urls.txt -favicon
```

### Captura de pantallas

```bash
# Tomar capturas de pantalla
httpx -l urls.txt -screenshot

# Captura de pantalla con directorio de salida
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Opciones del navegador sin interfaz gráfica
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtrado y selección de coincidencias

### Filtrar por código de estado

```bash
# Seleccionar códigos de estado
httpx -l urls.txt -mc 200,301,302

# Excluir códigos de estado
httpx -l urls.txt -fc 404,403,500

# Seleccionar todos y excluir algunos concretos
httpx -l urls.txt -mc all -fc 404
```

### Filtrar por tamaño

```bash
# Seleccionar por longitud del contenido
httpx -l urls.txt -ml 1234

# Excluir por longitud del contenido
httpx -l urls.txt -fl 0

# Seleccionar por número de líneas
httpx -l urls.txt -mlc 100

# Excluir por número de líneas
httpx -l urls.txt -flc 50
```

### Filtrar por contenido

```bash
# Seleccionar el cuerpo de respuesta mediante regex
httpx -l urls.txt -mr "admin|login"

# Excluir el cuerpo de respuesta mediante regex
httpx -l urls.txt -fr "error|not found"

# Seleccionar por una cadena
httpx -l urls.txt -ms "dashboard"
```

### Filtrar por tecnología

```bash
# Seleccionar por tecnología
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Excluir por tecnología
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Opciones de salida

### Formatos de salida

> **Formato:** `-json` produce JSON Lines. Algunas opciones del original, como `-jsonl`, `-mt`, `-ft`, `-screenshot-output` o `-resume-file`, pueden no existir en tu versión; contrástalas con la ayuda instalada. `-fr` aparece actualmente como seguimiento de redirecciones, no como exclusión por regex.

```bash
# Texto plano
httpx -l urls.txt -o results.txt

# Salida JSON
httpx -l urls.txt -json -o results.json

# JSON Lines
httpx -l urls.txt -jsonl -o results.jsonl

# Salida CSV
httpx -l urls.txt -csv -o results.csv
```

### Campos de salida

```bash
# Guardar la respuesta
httpx -l urls.txt -sr -srd ./responses/

# Guardar capturas de pantalla
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Salida personalizada
httpx -l urls.txt -o output.txt -sc -title -td
```

### Modo silencioso y detallado

```bash
# Silencioso (solo URL)
httpx -l urls.txt -silent

# Detallado
httpx -l urls.txt -v

# Depuración
httpx -l urls.txt -debug

# Sin colores
httpx -l urls.txt -nc
```

---

## ⚡ Funciones avanzadas

### Hilos y limitación de solicitudes

```bash
# Fijar los hilos (predeterminado: 50)
httpx -l urls.txt -threads 100

# Límite de solicitudes
httpx -l urls.txt -rate-limit 100

# Tiempo de espera
httpx -l urls.txt -timeout 10

# Reintentos
httpx -l urls.txt -retries 3
```

### Compatibilidad con proxy

```bash
# Proxy HTTP
httpx -l urls.txt -proxy http://127.0.0.1:8080

# Proxy SOCKS
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Cabeceras personalizadas

```bash
# Cabecera personalizada
httpx -l urls.txt -H "Authorization: Bearer token123"

# Varias cabeceras
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Métodos de solicitud

> **Corrección:** la documentación actual usa `-x` para elegir métodos de solicitud; `-method` muestra el método. Se conservan las líneas originales.

```bash
# Solicitud HEAD (más rápida)
httpx -l urls.txt -method HEAD

# Método personalizado
httpx -l urls.txt -method OPTIONS

# POST con cuerpo
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Reanudación y paginación

```bash
# Reanudar desde la última posición
httpx -l urls.txt -resume

# Guardar el progreso
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Flujo de trabajo de bug bounty

### Cadena completa de reconocimiento

> **Salida entre herramientas:** el archivo generado con estado, título y tecnologías incluye anotaciones. Para entregar solo URL a la siguiente herramienta, usa una salida de URL limpia o extrae el campo URL de la salida estructurada.

```bash
# Paso 1: Enumerar subdominios
subfinder -d target.com -o subs.txt

# Paso 2: Sondear equipos activos con httpx
cat subs.txt | httpx -sc -title -td -o live.txt

# Paso 3: Escanear vulnerabilidades
nuclei -l live.txt -severity critical,high

# Comando de una línea
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Reconocimiento detallado

```bash
# Recopilación completa de información
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Tomar capturas de todos los equipos activos

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Encontrar endpoints interesantes

```bash
# Encontrar paneles de administración
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# Encontrar endpoints de API
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Comprobar rutas concretas
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Búsqueda por tecnología

```bash
# Encontrar sitios WordPress
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Encontrar servidores Nginx
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Sondeo básico
```bash
echo "https://example.com" | httpx -sc -title
```

### Ejemplo 2: Reconocimiento completo a partir de subdominios
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Ejemplo 3: Tomar capturas de todos los equipos
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Ejemplo 4: Encontrar solo equipos activos
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Ejemplo 5: Detección de tecnologías
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Ejemplo 6: Con proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Ejemplo 7: Puertos personalizados
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Ejemplo 8: Filtrar solo el estado 200
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Ejemplo 9: Salida JSON para procesamiento
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Ejemplo 10: Cadena completa de herramientas
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Sondeo básico | `httpx -u url` |
| Desde un archivo | `httpx -l urls.txt` |
| Código de estado | `httpx -l urls.txt -sc` |
| Título de la página | `httpx -l urls.txt -title` |
| Detectar tecnologías | `httpx -l urls.txt -td` |
| Dirección IP | `httpx -l urls.txt -ip` |
| Captura de pantalla | `httpx -l urls.txt -screenshot` |
| Salida JSON | `httpx -l urls.txt -json -o out.json` |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-u` | Una URL |
| `-l` | Archivo con una lista de URL |
| `-sc` | Código de estado |
| `-title` | Título de la página |
| `-td` | Detección de tecnologías |
| `-ip` | Dirección IP |
| `-cdn` | Detección de CDN |
| `-server` | Servidor web |
| `-mc` | Seleccionar códigos |
| `-fc` | Excluir códigos |
| `-json` | Salida JSON |
| `-silent` | Modo silencioso |

### Cadenas de herramientas habituales

| Cadena de herramientas | Comando |
|----------|---------|
| Equipos activos | `subfinder -d target.com \| httpx -silent` |
| Con información | `subfinder -d target.com \| httpx -sc -title -td` |
| Hacia nuclei | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Consejos y buenas prácticas

### Consejos para bug bounty

1. **Usar con Subfinder**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Obtener una visión completa**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Capturas de pantalla para la evaluación inicial**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **JSON para procesamiento**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Consejos de rendimiento

```bash
# Alta velocidad
httpx -l hosts.txt -threads 100 -timeout 5

# Pocos hilos y velocidad reducida
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** httpx realiza solicitudes HTTP a los objetivos. Úsalo únicamente en sistemas autorizados.
> 
> - ✅ Usar en programas para los que se tenga permiso de pruebas
> - ✅ Objetivos de bug bounty dentro del alcance
> - ✅ Dominios propios
> - ❌ Nunca sondear objetivos sin autorización
> - ❌ Respetar los límites de solicitudes
> 
> **El sondeo sin autorización puede ser ilegal.**

---

## 📚 Recursos

### Recursos oficiales
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentación](https://docs.projectdiscovery.io/tools/httpx/)

### Guías de referencia relacionadas
- [Subfinder](../../Subfinder/translations/README.es.md)
- [Nuclei](../../Nuclei/translations/README.es.md)
- [ffuf](../../ffuf/translations/README.es.md)

---

<p align="center">
  <b>🌐 ¡Sondea todos tus objetivos!</b><br>
  <i>La herramienta HTTP esencial para bug bounty</i>
</p>
