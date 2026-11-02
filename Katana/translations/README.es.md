# ⚔️ Katana - Rastreador web de nueva generación

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ██╗  ██╗ █████╗ ████████╗ █████╗ ███╗   ██╗ █████╗ 
  ██║ ██╔╝██╔══██╗╚══██╔══╝██╔══██╗████╗  ██║██╔══██╗
  █████╔╝ ███████║   ██║   ███████║██╔██╗ ██║███████║
  ██╔═██╗ ██╔══██║   ██║   ██╔══██║██║╚██╗██║██╔══██║
  ██║  ██╗██║  ██║   ██║   ██║  ██║██║ ╚████║██║  ██║
  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝
         Framework de rastreo web de nueva generación
```

<p align="center">
  <img src="https://img.shields.io/badge/Katana-ProjectDiscovery-blue?style=for-the-badge" alt="Katana">
  <img src="https://img.shields.io/badge/Web_Crawler-green?style=for-the-badge" alt="Rastreador">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Qué es Katana](#-qué-es-katana)
- [Instalación](#-instalación)
- [Basic Uso](#-basic-uso)
- [Opciones de rastreo](#-opciones-de-rastreo)
- [Modo sin interfaz gráfica (headless)](#-modo-sin-interfaz-gráfica-headless)
- [Salida y filtrado](#-salida-y-filtrado)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 Qué es Katana

**Katana** es un framework de rastreo web de nueva generación creado por ProjectDiscovery:

- ⚡ **Rápido** - Escrito en Go, con alta concurrencia
- 🔍 **Renderizado de JavaScript** - Compatibilidad con navegadores sin interfaz gráfica
- 📡 **Web moderna** - Procesa aplicaciones de página única (SPA) y contenido dinámico
- 🎯 **Orientado a bug bounty** - Diseñado para pruebas de seguridad

### ¿Por qué Katana?

| Función | Ventaja |
|---------|---------|
| Headless Chrome | Rastrear aplicaciones con uso intensivo de JavaScript |
| Cabeceras personalizadas | Rastreo autenticado |
| Control del alcance | Mantenerse dentro del alcance |
| Cumplimentación de formularios | Descubrir más endpoints |

---

## 🚀 Instalación

```bash
# Instalación con Go (recomendado)
go install github.com/projectdiscovery/katana/cmd/katana@latest

# Docker
docker pull projectdiscovery/katana

# Verificar
katana -version
```

---

## 📖 Basic Uso

### Un objetivo

```bash
# Rastreo básico
katana -u https://example.com

# Guardar la salida
katana -u https://example.com -o urls.txt

# Modo silencioso
katana -u https://example.com -silent
```

### Varios objetivos

```bash
# Desde la entrada estándar
echo "https://example.com" | katana

# Varias URL
cat targets.txt | katana

# Desde un archivo
katana -list targets.txt
```

### Profundidad de rastreo

```bash
# Fijar la profundidad (predeterminada: 3)
katana -u https://example.com -d 5

# Profundidad 1 (solo la página objetivo)
katana -u https://example.com -d 1
```

---

<a id="-opciones-de-rastreo"></a>
## 🕷️ Opciones de rastreo

### Control del alcance

```bash
# Rastrear únicamente dentro del alcance (mismo dominio)
katana -u https://example.com -fs dn

# Opciones de alcance:
# dn - nombre de dominio
# rdn - dominio raíz
# fqdn - nombre de dominio completo

# Alcance por campo
katana -u https://example.com -fs rdn
```

### Estrategia de rastreo

> **Errata:** `-cs` define una regex de alcance; la estrategia se selecciona mediante `-s` o `-strategy`. Se conservan los comandos originales. Véase la [referencia oficial](https://docs.projectdiscovery.io/opensource/katana/usage).

```bash
# En profundidad (predeterminada)
katana -u https://example.com -cs depth-first

# En anchura
katana -u https://example.com -cs breadth-first
```

### Concurrencia

```bash
# Solicitudes simultáneas (predeterminado: 10)
katana -u https://example.com -c 50

# Límite de solicitudes por segundo
katana -u https://example.com -rl 100

# Paralelismo
katana -u https://example.com -p 10
```

### Duración del rastreo

```bash
# Fijar el tiempo de espera (segundos)
katana -u https://example.com -timeout 30

# Duración máxima del rastreo
katana -u https://example.com -ct 300
```

### Cumplimentación automática de formularios

> **Versiones:** la configuración personalizada de formularios se carga con `-form-config`; la opción `-fv` del original puede no existir.

```bash
# Habilitar la cumplimentación de formularios
katana -u https://example.com -aff

# Con valores personalizados
katana -u https://example.com -aff -fv "username:admin,password:test123"
```

---

## 🌐 Modo sin interfaz gráfica (headless)

### Habilitar el navegador sin interfaz gráfica

```bash
# Habilitar Chrome sin interfaz gráfica
katana -u https://example.com -headless

# Navegador sin interfaz gráfica con profundidad de rastreo
katana -u https://example.com -headless -d 3
```

### Opciones del navegador sin interfaz gráfica

> **Precisión:** `-system-chrome` usa el Chrome instalado; una ruta concreta requiere `-system-chrome-path`. Verifica también la disponibilidad de `-screenshot` en tu versión.

```bash
# Ruta personalizada de Chrome
katana -u https://example.com -headless -system-chrome

# Sin aislamiento de Chrome (para root)
katana -u https://example.com -headless -no-sandbox

# Tomar capturas de las páginas
katana -u https://example.com -headless -screenshot
```

### Rastreo de JavaScript

> **Unidades:** `-timeout` fija la espera por solicitud en segundos, no milisegundos ni una espera específica para JavaScript.

```bash
# Esperar a que se cargue JavaScript (ms)
katana -u https://example.com -headless -timeout 5

# Rastrear endpoints de JavaScript
katana -u https://example.com -js-crawl
```

---

## 📤 Salida y filtrado

### Formatos de salida

```bash
# Salida estándar a un archivo
katana -u https://example.com -o results.txt

# Salida JSON
katana -u https://example.com -jsonl -o results.json

# Sin colores
katana -u https://example.com -nc
```

### Campos mostrados

> **Campos:** no todos los nombres enumerados pertenecen a `-f`. Para detalles de solicitudes y respuestas, comprueba los campos de la salida JSONL.

```bash
# Mostrar campos concretos
katana -u https://example.com -f url,method,status

# Campos disponibles:
# url, path, fqdn, rdn, rurl, qurl, qpath
# file, kv, dir, ufile, udir
# method, status, body, response, header
```

### Filtrar por extensión

```bash
# Seleccionar extensiones
katana -u https://example.com -em js,php,asp

# Excluir extensiones
katana -u https://example.com -ef png,jpg,gif,css,svg,woff
```

### Filtrar por código de estado

> **Opciones:** la ayuda documenta condiciones de respuesta mediante DSL (`-mdc` y `-fdc`); no presupongas que `-mc` y `-fc` tienen el significado usado aquí por otras herramientas. En Katana, `-fc` es la configuración de formularios.

```bash
# Seleccionar un código de estado
katana -u https://example.com -mc 200,301,302

# Excluir un código de estado
katana -u https://example.com -fc 404,500
```

### Cabeceras personalizadas

```bash
# Añadir una cabecera
katana -u https://example.com -H "Authorization: Bearer TOKEN"

# Varias cabeceras
katana -u https://example.com -H "Cookie: session=abc" -H "User-Agent: Custom"
```

### Compatibilidad con proxy

```bash
# Proxy HTTP
katana -u https://example.com -proxy http://127.0.0.1:8080

# Para Burp Suite
katana -u https://example.com -proxy http://127.0.0.1:8080
```

---

## 📊 Referencia rápida

### Comandos básicos

| Comando | Descripción |
|---------|-------------|
| `katana -u URL` | Rastrear una URL |
| `katana -list file.txt` | Rastrear desde un archivo |
| `katana -u URL -d 5` | Fijar la profundidad en 5 |
| `katana -u URL -headless` | Habilitar el modo sin interfaz gráfica |
| `katana -u URL -o out.txt` | Guardar los resultados |

### Opciones de alcance

| Opción | Descripción |
|------|-------------|
| `-fs dn` | Mismo dominio |
| `-fs rdn` | Dominio raíz |
| `-fs fqdn` | Dominio completo |
| `-cs depth-first` | Rastreo en profundidad |
| `-cs breadth-first` | Rastreo en anchura |

### Opciones de rendimiento

| Opción | Descripción |
|------|-------------|
| `-c 50` | Concurrencia |
| `-p 10` | Paralelismo |
| `-rl 100` | Límite de solicitudes |
| `-timeout 30` | Tiempo de espera de la solicitud |

### Opciones de filtrado

| Opción | Descripción |
|------|-------------|
| `-em js,php` | Seleccionar extensiones |
| `-ef png,jpg` | Excluir extensiones |
| `-mc 200` | Seleccionar códigos de estado |
| `-fc 404` | Excluir códigos de estado |

---

## 🎯 Flujos de trabajo de bug bounty

### Rastreo básico

```bash
# Rastreo rápido
katana -u https://target.com -d 3 -o urls.txt
```

### Rastreo exhaustivo

```bash
# Rastreo profundo con JavaScript
katana -u https://target.com -d 5 -headless -c 50 \
  -ef png,jpg,gif,css,svg,woff,woff2,ttf,eot,ico \
  -o all_urls.txt
```

### Descubrimiento de parámetros

```bash
# Encontrar URL con parámetros
katana -u https://target.com -d 3 | grep "?" | sort -u

# Con grep para parámetros concretos
katana -u https://target.com | grep -E "id=|user=|admin=|file="
```

### Combinación con otras herramientas

```bash
# Katana + httpx
katana -u https://target.com -d 3 | httpx -silent -sc

# Katana + Nuclei
katana -u https://target.com -d 3 | nuclei -t nuclei-templates/

# Cadena completa de herramientas
subfinder -d target.com | httpx -silent | katana -d 3 | grep "=" | sort -u
```

### Rastreo autenticado

```bash
# Con una cookie de sesión
katana -u https://target.com \
  -H "Cookie: session=abc123" \
  -d 5 \
  -headless \
  -o authenticated_urls.txt
```

---

## 📚 Recursos

- [Katana GitHub](https://github.com/projectdiscovery/katana)
- [ProjectDiscovery](https://projectdiscovery.io/)

### Guías de referencia relacionadas
- [Metodología de bug bounty](../../Bug-Bounty-Methodology/translations/README.es.md)
- [httpx](../../httpx/translations/README.es.md)
- [Nuclei](../../Nuclei/translations/README.es.md)
- [GAU](../../GAU/translations/README.es.md)

---

<p align="center">
  <b>⚔️ ¡Rastrea todos tus objetivos!</b><br>
  <i>Katana - Rastreador web de nueva generación</i>
</p>
