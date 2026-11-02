# 🔍 Arjun: descubrimiento de parámetros ocultos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)


> **Precisiones de versión:** Arjun descubre parámetros, no nuevos endpoints por sí mismo. Se conservan los comandos y el JSON del original; varios ejemplos requieren revisión frente a la [guía oficial](https://github.com/s0md3v/Arjun/wiki/Usage). Esta documenta `--ratelimit`, `-d` para el intervalo, `-T` para el tiempo de espera, `-i` para importar y `-oJ`/`-oT` para exportar. Las cabeceras múltiples se separan con saltos de línea, no con punto y coma entre nombres de cabecera. `--passive` recopila nombres de fuentes como CommonCrawl, OTX y Wayback Machine; no significa simplemente analizar JavaScript. Deben comprobarse las opciones `--stdin`, `--import`, `--burp`, `-H` y los métodos separados por comas según la versión. El esquema JSON real puede diferir del ejemplo, lo que también afecta al filtro `jq`.


```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         Conjunto de herramientas de descubrimiento de parámetros HTTP
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Qué es Arjun](#qué-es-arjun)
- [Instalación](#instalación)
- [Uso básico](#uso-básico)
- [Modos de descubrimiento](#modos-de-descubrimiento)
- [Opciones avanzadas](#opciones-avanzadas)
- [Opciones de salida](#opciones-de-salida)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-arjun"></a>

## 🎯 Qué es Arjun

**Arjun** descubre parámetros HTTP ocultos en aplicaciones web:

- 🔍 **Descubrimiento de parámetros**: encontrar parámetros GET/POST ocultos
- 🎯 **Compatibilidad con JSON**: descubrir parámetros JSON
- 📡 **Varios métodos**: modos GET, POST y JSON
- ⚡ **Rápido**: solicitudes con alta concurrencia

### ¿Por qué Arjun?

| Función | Ventaja |
|---------|---------|
| Parámetros ocultos | Encontrar endpoints no referenciados (descripción del original) |
| Varios formatos | GET, POST, JSON |
| Modo pasivo | Comprobar archivos JavaScript |
| Importar desde Burp | Integrarse con el flujo de trabajo existente |

---

<a id="instalación"></a>

## 🚀 Instalación

```bash
# Instalación con pip (recomendada)
pip3 install arjun

# Desde el código fuente
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Verificar
arjun --help
```

---

<a id="uso-básico"></a>

## 📖 Uso básico

### Una URL

```bash
# Descubrimiento básico de parámetros
arjun -u https://example.com/endpoint

# Con un archivo de salida
arjun -u https://example.com/endpoint -o results.txt
```

### Varias URL

```bash
# Desde un archivo
arjun -i urls.txt

# Desde la entrada estándar
cat urls.txt | arjun --stdin
```

### Especificar el método HTTP

```bash
# Parámetros GET (predeterminado)
arjun -u https://example.com/search -m GET

# Parámetros POST
arjun -u https://example.com/api -m POST

# Cuerpo JSON
arjun -u https://example.com/api -m JSON
```

---

<a id="modos-de-descubrimiento"></a>

## 🔎 Modos de descubrimiento

### Parámetros GET

```bash
# Descubrir parámetros GET
arjun -u https://example.com/search?known=value -m GET

# Salida de ejemplo:
# [param] id
# [param] user
# [param] debug
```

### Parámetros POST

```bash
# Descubrir parámetros de formularios POST
arjun -u https://example.com/login -m POST

# Con datos existentes
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### Parámetros JSON

```bash
# Descubrir parámetros del cuerpo JSON
arjun -u https://example.com/api/v1/users -m JSON

# Con un JSON base
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Descubrimiento combinado

```bash
# Todos los métodos
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="opciones-avanzadas"></a>

## ⚙️ Opciones avanzadas

### Lista de palabras personalizada

```bash
# Usar una lista de palabras personalizada
arjun -u https://example.com -w /path/to/wordlist.txt

# Usar los parámetros de SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Hilos y limitación de la tasa de solicitudes

```bash
# Establecer los hilos (valor predeterminado: 2)
arjun -u https://example.com -t 10

# Límite de tasa (solicitudes por segundo)
arjun -u https://example.com --rate-limit 10

# Intervalo entre solicitudes (segundos)
arjun -u https://example.com --delay 1
```

### Cabeceras personalizadas

```bash
# Añadir una cabecera
arjun -u https://example.com --headers "Authorization: Bearer token"

# Varias cabeceras
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Desde un archivo
arjun -u https://example.com -H headers.txt
```

### Incluir parámetros conocidos

```bash
# Incluir parámetros conocidos
arjun -u https://example.com/search --include 'q=test&page=1'

# Para POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Modo pasivo

```bash
# Extraer de archivos JavaScript
arjun -u https://example.com --passive

# Esto comprueba los archivos JavaScript para encontrar posibles nombres de parámetros
```

### Desactivar las redirecciones

```bash
# No seguir las redirecciones
arjun -u https://example.com --disable-redirects
```

### Tiempo de espera

```bash
# Establecer el tiempo de espera (segundos)
arjun -u https://example.com --timeout 15
```

### Modo estable

```bash
# Más fiable (más lento)
arjun -u https://example.com --stable
```

---

<a id="opciones-de-salida"></a>

## 📤 Opciones de salida

### Formatos de salida

```bash
# Salida de texto (predeterminada)
arjun -u https://example.com -oT results.txt

# Salida JSON
arjun -u https://example.com -oJ results.json

# Ambos
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Ejemplo de salida JSON

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Importar desde Burp

```bash
# Importar solicitudes de Burp
arjun --import burp_export.xml

# Procesar un archivo de Burp
arjun -i burp_requests.txt --burp
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos básicos

| Comando | Descripción |
|---------|-------------|
| `arjun -u URL` | Descubrir parámetros |
| `arjun -u URL -m POST` | Método POST |
| `arjun -u URL -m JSON` | Cuerpo JSON |
| `arjun -i file.txt` | Varias URL |
| `arjun -u URL -oJ out.json` | Salida JSON |

### Opciones de método

| Opción | Descripción |
|------|-------------|
| `-m GET` | Parámetros GET |
| `-m POST` | Datos de formulario POST |
| `-m JSON` | Cuerpo JSON |
| `-m GET,POST` | Varios métodos |

### Opciones de rendimiento

| Opción | Descripción |
|------|-------------|
| `-t 10` | Hilos |
| `--rate-limit 10` | Solicitudes por segundo |
| `--delay 1` | Intervalo entre solicitudes |
| `--timeout 15` | Tiempo de espera de la solicitud |

### Opciones de descubrimiento

| Opción | Descripción |
|------|-------------|
| `-w wordlist.txt` | Lista de palabras personalizada |
| `--include 'a=b'` | Incluir parámetros |
| `--passive` | Extraer de JavaScript |
| `--stable` | Modo fiable |

---

## 🎯 Flujos de trabajo para bug bounty

### Descubrimiento básico

```bash
# Descubrimiento rápido de parámetros
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Con una lista de palabras personalizada

```bash
# Descubrimiento exhaustivo
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Pruebas autenticadas

```bash
# Con una sesión
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Descubrimiento masivo

```bash
# Varios endpoints
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# O mediante un archivo
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Integración en pipelines

```bash
# Encontrar endpoints y después descubrir parámetros
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Probar los parámetros descubiertos

```bash
# Después del descubrimiento, probar vulnerabilidades
# 1. Descubrir parámetros
arjun -u https://target.com/search -oJ params.json

# 2. Extraer nombres de parámetros
cat params.json | jq -r '.[].params[].name'

# 3. Probar con ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Consejos prácticos

1. **Empezar por el modo pasivo**: comprobar primero los archivos JavaScript
2. **Usar buenas listas de palabras**: se recomiendan los nombres de parámetros de Burp
3. **Limitar la tasa**: evitar bloqueos
4. **Probar todos los métodos**: GET, POST y JSON
5. **Comprobar cabeceras**: X-Custom-Param, X-Debug, etc.
6. **Pruebas autenticadas**: probar las áreas con sesión iniciada

### Parámetros ocultos habituales

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Recursos

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp Parameter Names](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Guías rápidas relacionadas
- [Metodología de bug bounty](../../Bug-Bounty-Methodology/translations/README.es.md)
- [ffuf](../../ffuf/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Descubre parámetros ocultos!</b><br>
  <i>Arjun: el cazador de parámetros</i>
</p>
