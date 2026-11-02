# 🔗 GAU - Get All URLs

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
   ██████╗  █████╗ ██╗   ██╗
  ██╔════╝ ██╔══██╗██║   ██║
  ██║  ███╗███████║██║   ██║
  ██║   ██║██╔══██║██║   ██║
  ╚██████╔╝██║  ██║╚██████╔╝
   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ 
       Get All URLs - Recopilación rápida de URL
```

<p align="center">
  <img src="https://img.shields.io/badge/GAU-URL_Discovery-blue?style=for-the-badge" alt="GAU">
  <img src="https://img.shields.io/badge/Wayback-green?style=for-the-badge" alt="Wayback">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [¿Qué es GAU?](#-qué-es-gau)
- [Instalación](#-instalación)
- [Uso básico](#-uso-básico)
- [Proveedores](#-proveedores)
- [Filtrado](#-filtrado)
- [Integración](#-integración)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es GAU?

**GAU** (Get All URLs, obtener todas las URL) recopila URL conocidas de varias fuentes:

- 🔍 **Wayback Machine** - URL históricas
- 🌐 **Common Crawl** - Datos de rastreo web
- 📡 **AlienVault OTX** - URL de inteligencia de amenazas
- 🔒 **URLScan.io** - URL analizadas

### ¿Por qué utilizar GAU?

| Ventaja | Descripción |
|---------|-------------|
| **Reconocimiento pasivo** | Sin contacto directo con el objetivo |
| **Datos históricos** | Encontrar endpoints antiguos u olvidados |
| **Rápido** | Recopilación en paralelo |
| **Varias fuentes** | Resultados amplios |

---

## 🚀 Instalación

```bash
# Instalar con Go (recomendado)
go install github.com/lc/gau/v2/cmd/gau@latest

# Desde el código fuente
git clone https://github.com/lc/gau.git
cd gau/cmd/gau
go build

# Comprobar la instalación
gau --version
```

---

## 📖 Uso básico

### Un solo dominio

```bash
# Uso básico
gau example.com

# Con archivo de salida
gau example.com -o urls.txt

# Modo silencioso (sin banner), según el original
gau --subs example.com
```

> **Corrección:** `--subs` incluye subdominios; no activa un modo silencioso, aunque así lo describa el comentario anterior. Véase el [README oficial de GAU](https://github.com/lc/gau).

### Varios dominios

```bash
# Desde la entrada estándar
echo "example.com" | gau

# Varios dominios
cat domains.txt | gau

# Desde un archivo
gau --threads 5 < domains.txt
```

### Incluir subdominios

```bash
# Incluir subdominios en los resultados
gau --subs example.com
```

### Hilos de ejecución

```bash
# Establecer los hilos (predeterminado: 1)
gau --threads 5 example.com

# Ajustar para limitar la tasa de peticiones
gau --threads 2 example.com
```

---

## 🌐 Proveedores

### Proveedores disponibles

| Proveedor | Descripción | Predeterminado |
|----------|-------------|---------|
| `wayback` | Wayback Machine | ✅ |
| `commoncrawl` | Common Crawl | ✅ |
| `otx` | AlienVault OTX | ✅ |
| `urlscan` | URLScan.io | ✅ |

### Seleccionar proveedores

```bash
# Utilizar únicamente proveedores concretos
gau --providers wayback example.com
gau --providers wayback,otx example.com

# Excluir un proveedor
gau --blacklist commoncrawl example.com
```

> **Corrección:** `--blacklist` excluye extensiones de archivo, no proveedores. Para elegir fuentes, utiliza la lista admitida por `--providers`. El ejemplo original se conserva; consulta la [documentación oficial](https://github.com/lc/gau).

### Configuración de proveedores

```bash
# Ubicación del archivo de configuración
~/.gau.toml

# Configuración de ejemplo
cat > ~/.gau.toml << EOF
threads = 5
verbose = false
retries = 3
subdomains = false
providers = ["wayback","commoncrawl","otx","urlscan"]
blacklist = []
json = false
EOF
```

---

## 🔍 Filtrado

> **Datos históricos:** los códigos y tipos de contenido disponibles proceden de las fuentes consultadas; no prueban que la URL siga accesible actualmente. La comprobación con httpx es una etapa activa adicional.

### Filtrar por código de estado

```bash
# Solo las URL que devolvieron 200
gau --mc 200 example.com

# Varios códigos de estado
gau --mc 200,301,302 example.com

# Excluir códigos concretos
gau --fc 404,500 example.com
```

### Filtrar por extensión

```bash
# Seleccionar extensiones
gau --mt "\.js$" example.com
gau --mt "\.php$" example.com

# Excluir extensiones
gau --ft "\.jpg$|\.png$|\.gif$|\.css$" example.com
```

> **Corrección:** `--mt` y `--ft` seleccionan o excluyen tipos MIME, no expresiones regulares de extensiones. Las expresiones `.js` y `.php` del bloque anterior no cumplen la función que describe el original. La sección con grep muestra el filtrado por extensión. Véase el [README oficial](https://github.com/lc/gau).

### Filtrar por tipo MIME

```bash
# Filtrar por tipo de contenido
gau --mt "application/json" example.com
gau --mt "text/html" example.com
```

### Formato de salida

```bash
# Salida JSON
gau --json example.com

# Modo detallado
gau --verbose example.com
```

---

## 🔗 Integración

### Con httpx

```bash
# Comprobar las URL activas
gau example.com | httpx -silent

# Con códigos de estado
gau example.com | httpx -sc -title
```

### Con grep y extracción de parámetros

```bash
# Buscar URL con parámetros
gau example.com | grep "="

# Buscar patrones concretos
gau example.com | grep -E "\.php\?|\.asp\?"

# Patrones GF
gau example.com | gf xss
gau example.com | gf sqli
gau example.com | gf lfi
gau example.com | gf redirect
```

### Con urldedupe

```bash
# Eliminar duplicados
gau example.com | urldedupe

# Alternativa: sort -u
gau example.com | sort -u
```

### Con uro

```bash
# Eliminación inteligente de URL duplicadas
gau example.com | uro
```

### Cadena completa de herramientas

```bash
# Cadena completa de recopilación de URL
echo "example.com" | gau --subs --threads 5 | \
  uro | \
  httpx -silent -mc 200 | \
  grep "=" > params.txt
```

---

## 📊 Referencia rápida

### Comandos básicos

| Comando | Descripción |
|---------|-------------|
| `gau domain.com` | Obtener las URL de un dominio |
| `gau --subs domain.com` | Incluir subdominios |
| `gau -o urls.txt domain.com` | Guardar la salida en un archivo |
| `gau --threads 5 domain.com` | Con varios hilos |

### Opciones de filtrado

| Opción | Descripción |
|------|-------------|
| `--mc 200` | Seleccionar un código de estado |
| `--fc 404` | Excluir un código de estado |
| `--mt "regex"` | Seleccionar mediante una expresión regular, según el original |
| `--ft "regex"` | Excluir mediante una expresión regular, según el original |
| `--json` | Salida JSON |

### Opciones de proveedores

| Opción | Descripción |
|------|-------------|
| `--providers wayback,otx` | Utilizar proveedores concretos |
| `--blacklist commoncrawl` | Excluir un proveedor |

### Comandos útiles de una línea

```bash
# Obtener todos los archivos JS
gau example.com | grep -E "\.js$" | sort -u

# Buscar endpoints de API
gau example.com | grep -iE "/api/|/v[0-9]/"

# Buscar parámetros
gau example.com | grep "?" | grep "=" | sort -u

# Buscar posibles casos de SQLi
gau example.com | gf sqli

# Buscar posibles casos de XSS
gau example.com | gf xss

# URL activas con parámetros
gau example.com | grep "=" | httpx -silent
```

---

## 🎯 Flujo de trabajo de bug bounty

```bash
# 1. Recopilar todas las URL
gau --subs --threads 5 target.com > all_urls.txt

# 2. Filtrar las URL de interés
cat all_urls.txt | grep "=" | uro > params.txt

# 3. Comprobar cuáles están activas
cat params.txt | httpx -silent > live_params.txt

# 4. Buscar vulnerabilidades
cat live_params.txt | gf sqli > sqli.txt
cat live_params.txt | gf xss > xss.txt
cat live_params.txt | gf lfi > lfi.txt
cat live_params.txt | gf redirect > redirect.txt

# 5. Realizar pruebas
cat xss.txt | qsreplace '"><script>alert(1)</script>' | \
  httpx -silent -mc 200
```

---

> **Validación:** GF clasifica URL candidatas mediante patrones; no confirma una vulnerabilidad. El último ejemplo inserta un payload y comprueba HTTP 200, pero eso no demuestra reflexión ni ejecución de JavaScript: requiere validación adicional.

## 📚 Recursos

- [GAU GitHub](https://github.com/lc/gau)
- [Patrones GF](https://github.com/1ndianl33t/Gf-Patterns)

### Guías rápidas relacionadas
- [Metodología de bug bounty](../../Bug-Bounty-Methodology/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)
- [httpx](../../httpx/translations/README.es.md)

---

<p align="center">
  <b>🔗 ¡Recopila todas las URL!</b><br>
  <i>GAU - Descubrimiento pasivo de URL</i>
</p>
