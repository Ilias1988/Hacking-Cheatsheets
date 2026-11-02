# 🔍 Subfinder - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

```
  ____        _     __ _           _           
 / ___| _   _| |__ / _(_)_ __   __| | ___ _ __ 
 \___ \| | | | '_ \ |_| | '_ \ / _` |/ _ \ '__|
  ___) | |_| | |_) |  _| | | | | (_| |  __/ |   
 |____/ \__,_|_.__/|_| |_|_| |_|\__,_|\___|_|   
                                                
   Herramienta rápida de descubrimiento de subdominios
```

<p align="center">
  <img src="https://img.shields.io/badge/Subfinder-Subdomain_Discovery-red?style=for-the-badge" alt="Subfinder">
  <img src="https://img.shields.io/badge/Passive-Recon-orange?style=for-the-badge" alt="Pasivo">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🌐 Herramienta rápida de enumeración pasiva de subdominios de ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Descubrimiento de subdominios](#-descubrimiento-de-subdominios)
- [Fuentes de datos](#-fuentes-de-datos)
- [Configuración de API](#-configuración-de-api)
- [Opciones de salida](#-opciones-de-salida)
- [Funciones avanzadas](#-funciones-avanzadas)
- [Flujo de trabajo de bug bounty](#-flujo-de-trabajo-de-bug-bounty)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Subfinder** es una herramienta rápida de enumeración pasiva que descubre subdominios mediante distintas fuentes, como motores de búsqueda, sitios para compartir texto y plataformas de inteligencia.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Rápido** | Desarrollado en Go, con alta concurrencia |
| **Pasivo** | Sin interacción directa con el objetivo |
| **Varias fuentes** | Más de 40 fuentes de datos |
| **Integración de API** | Compatible con fuentes de pago |
| **Fácil de integrar en cadenas de herramientas** | Funciona con otras herramientas |
| **Salida JSON** | Compatible con salida estructurada |

### Subfinder frente a otras herramientas

| Función | Subfinder | Amass | Assetfinder |
|---------|-----------|-------|-------------|
| **Velocidad** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Fuentes** | 40+ | 50+ | 10+ |
| **Escaneo activo** | ❌ No | ✅ Sí | ❌ No |
| **Compatibilidad con API** | ✅ Sí | ✅ Sí | ❌ No |
| **Facilidad de uso** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ |

---

## 📥 Instalación

### Con Go
```bash
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
```

### Kali Linux
```bash
sudo apt install subfinder
```

> **Marcador de versión:** `v2.x.x` y `2.x.x` en la descarga siguiente son valores de ejemplo que deben sustituirse por una versión publicada; no identifican un archivo descargable concreto.

### Descarga del binario
```bash
# Descargar desde las versiones publicadas
https://github.com/projectdiscovery/subfinder/releases

# Linux
wget https://github.com/projectdiscovery/subfinder/releases/download/v2.x.x/subfinder_2.x.x_linux_amd64.zip
unzip subfinder_2.x.x_linux_amd64.zip
sudo mv subfinder /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/subfinder:latest
docker run projectdiscovery/subfinder -d example.com
```

### Comprobar la instalación
```bash
subfinder -version
subfinder -h
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
subfinder [options] -d <domain>
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-d` | Dominio objetivo |
| `-dL` | Archivo con una lista de dominios |
| `-o` | Archivo de salida |
| `-oJ` | Salida JSON |
| `-silent` | Modo silencioso (solo subdominios) |
| `-v` | Salida detallada |
| `-t` | Número de hilos |
| `-all` | Utilizar todas las fuentes |

### Inicio rápido
```bash
# Enumeración básica
subfinder -d example.com

# Salida silenciosa (lista limpia)
subfinder -d example.com -silent

# Guardar en un archivo
subfinder -d example.com -o subdomains.txt

# Varios dominios
subfinder -dL domains.txt -o all-subs.txt
```

---

## 🔍 Descubrimiento de subdominios

### Un solo dominio

```bash
# Enumeración básica
subfinder -d target.com

# Salida detallada
subfinder -d target.com -v

# Modo silencioso (solo subdominios)
subfinder -d target.com -silent

# Mostrar las fuentes
subfinder -d target.com -cs
```

### Varios dominios

```bash
# Desde un archivo
subfinder -dL domains.txt

# Guardar la salida en un archivo
subfinder -dL domains.txt -o all_subdomains.txt

# Modo silencioso para encadenar herramientas
subfinder -dL domains.txt -silent | httpx
```

### Enumeración recursiva

```bash
# Activar el modo recursivo (encontrar subdominios anidados, según el original)
subfinder -d target.com -recursive

# Modo recursivo con todas las fuentes
subfinder -d target.com -recursive -all
```

---

> **Precisión:** `-recursive` selecciona fuentes compatibles con subdominios anidados; no implementa por sí solo un recorrido recursivo exhaustivo. `-nW` activa la comprobación DNS y `-t` controla esa resolución, por lo que aumentar hilos no acelera necesariamente la consulta pasiva. Véase la [documentación oficial](https://docs.projectdiscovery.io/opensource/subfinder/usage).

## 📊 Fuentes de datos

### Fuentes gratuitas (predeterminadas)

Subfinder utiliza estas fuentes de forma predeterminada, según el original (sin necesidad de clave API):
- Alienvault
- AnubisDB
- Crtsh
- Hackertarget
- Rapiddns
- Sublist3r
- ThreatCrowd
- URLScan
- Wayback Machine

### Fuentes premium (requieren API)

Estas fuentes requieren claves API para obtener mejores resultados:
- **Shodan**
- **SecurityTrails**
- **VirusTotal**
- **Censys**
- **Chaos**
- **GitHub**
- **PassiveTotal**
- **Binaryedge**
- **WhoisXML**
- **ZoomEye**

### Gestión de fuentes

```bash
# Listar todas las fuentes
subfinder -ls

# Utilizar únicamente fuentes concretas
subfinder -d target.com -sources crtsh,hackertarget,alienvault

# Excluir fuentes concretas
subfinder -d target.com -es shodan,securitytrails

# Utilizar todas las fuentes (incluidas las lentas)
subfinder -d target.com -all

# Mostrar qué fuente ha encontrado cada subdominio
subfinder -d target.com -cs
```

---

## 🔑 Configuración de API

### Ubicación del archivo de configuración

```bash
# Ubicación predeterminada
~/.config/subfinder/provider-config.yaml

# O especificar una ubicación personalizada
subfinder -d target.com -pc /path/to/config.yaml
```

### Plantilla del archivo de configuración

```yaml
# ~/.config/subfinder/provider-config.yaml

binaryedge:
  - your_api_key_here
censys:
  - your_api_id:your_api_secret
certspotter: []
chaos:
  - your_chaos_key
chinaz: []
dnsdb: []
fofa:
  - your_email:your_key
fullhunt: []
github:
  - your_github_token
hunter:
  - your_hunter_key
intelx:
  - your_intelx_key
passivetotal:
  - your_username:your_key
securitytrails:
  - your_security_trails_key
shodan:
  - your_shodan_key
virustotal:
  - your_virustotal_key
whoisxmlapi:
  - your_whoisxml_key
zoomeye:
  - your_zoomeye_key
```

### Obtención de claves API

| Fuente | Plan gratuito | URL |
|--------|-----------|-----|
| **VirusTotal** | ✅ Sí | https://www.virustotal.com |
| **SecurityTrails** | ✅ Sí (limitado) | https://securitytrails.com |
| **Shodan** | ✅ Sí (limitado) | https://shodan.io |
| **Censys** | ✅ Sí | https://censys.io |
| **Chaos** | ✅ Gratuito (limitado) | https://chaos.projectdiscovery.io |
| **GitHub** | ✅ Gratuito | https://github.com/settings/tokens |

> **Nota de versión:** la lista de fuentes, los planes gratuitos y los formatos de claves son los del original. Pueden cambiar; utiliza la configuración generada por tu versión y la documentación del proveedor correspondiente. Los identificadores y marcadores de la plantilla se conservan íntegros.

### Configuración gratuita recomendada

```yaml
# Configuración mínima recomendada para bug bounty
virustotal:
  - your_free_key
github:
  - your_github_token
chaos:
  - your_chaos_key
```

---

## 📤 Opciones de salida

### Formatos de salida

```bash
# Texto sin formato (predeterminado)
subfinder -d target.com -o subs.txt

# Salida JSON
subfinder -d target.com -oJ -o subs.json

# JSON por líneas
subfinder -d target.com -oJL -o subs.jsonl

# Formato CSV
subfinder -d target.com -oD -o subs.csv
```

> **Corrección de opciones:** `-oJ` produce JSONL; `-oD` indica un directorio de salida para `-dL`, no CSV. `-oJL` y `-exclude-subdomains` no figuran en la ayuda oficial consultada. `-cs` incorpora fuentes a la salida JSON y requiere ese formato. Se conservan los ejemplos originales. Consulta la [ayuda oficial](https://docs.projectdiscovery.io/opensource/subfinder/usage).

### Filtrado de la salida

```bash
# Modo silencioso (salida limpia)
subfinder -d target.com -silent

# Mostrar solo los subdominios nuevos (excluir los conocidos)
subfinder -d target.com -exclude-subdomains known.txt

# Sin colores
subfinder -d target.com -nc
```

### Encadenamiento de la salida

```bash
# Enviar la salida a httpx
subfinder -d target.com -silent | httpx -silent

# Enviar la salida a nuclei
subfinder -d target.com -silent | httpx -silent | nuclei

# Guardar y procesar
subfinder -d target.com -silent | tee subs.txt | httpx
```

---

## ⚡ Funciones avanzadas

### Hilos y rendimiento

```bash
# Establecer los hilos (predeterminado: 10)
subfinder -d target.com -t 50

# Limitar la tasa de peticiones
subfinder -d target.com -rate-limit 100

# Tiempo de espera
subfinder -d target.com -timeout 30
```

### Coincidencias y filtrado

```bash
# Seleccionar patrones concretos
subfinder -d target.com -match "api,dev,stage"

# Excluir patrones
subfinder -d target.com -filter "www,mail"
```

### Opciones de resolutores DNS

```bash
# Utilizar resolutores DNS personalizados
subfinder -d target.com -rL resolvers.txt

# Resolver los subdominios
subfinder -d target.com -nW
```

> **Compatibilidad del proxy:** la ayuda oficial documenta un proxy HTTP; no presupongas compatibilidad con la URL SOCKS del ejemplo. Véase la [documentación de uso](https://docs.projectdiscovery.io/opensource/subfinder/usage).

### Compatibilidad con proxies

```bash
# Proxy HTTP
subfinder -d target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
subfinder -d target.com -proxy socks5://127.0.0.1:9050
```

---

## 🔄 Flujo de trabajo de bug bounty

### Cadena completa de reconocimiento

```bash
# Paso 1: enumeración de subdominios
subfinder -d target.com -all -o subs.txt

# Paso 2: comprobar los hosts activos
cat subs.txt | httpx -o live.txt

# Paso 3: escanear con nuclei
nuclei -l live.txt -severity critical,high -o vulns.txt

# Comando de una línea
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Flujo de trabajo para varios dominios

```bash
# Enumerar todos los dominios del programa
subfinder -dL scope.txt -all -o all-subs.txt

# Procesar los resultados
cat all-subs.txt | httpx -silent | nuclei
```

### Supervisión continua

```bash
# Guardar la referencia inicial
subfinder -d target.com -silent > baseline.txt

# Más adelante, buscar subdominios nuevos
subfinder -d target.com -silent > current.txt
diff baseline.txt current.txt | grep ">" | cut -d" " -f2 > new-subs.txt
```

> **Precisión de la supervisión:** el orden de los resultados puede variar entre ejecuciones. El `diff` del ejemplo puede mostrar líneas como nuevas por un cambio de orden; compara conjuntos ordenados y sin duplicados para identificar altas reales.

### Integración con otras herramientas

```bash
# Con httpx (hosts activos)
subfinder -d target.com -silent | httpx

# Con ffuf (fuzzing de directorios)
subfinder -d target.com -silent | httpx -silent | \
  xargs -I {} ffuf -u {}/FUZZ -w wordlist.txt

# Con nmap (escaneo de puertos)
subfinder -d target.com -silent | dnsx -a -silent | nmap -iL -

# Cadena completa de herramientas
subfinder -d target.com -silent | \
  httpx -title -status-code -tech-detect -silent | \
  tee recon-results.txt
```

---

## 🎬 Ejemplos prácticos

> **Formato entre herramientas:** revisa que cada salida sea válida para el siguiente consumidor. La salida de `dnsx -a` puede incluir el nombre y la IP entre corchetes, y la de httpx con títulos o códigos contiene metadatos; no son necesariamente listas limpias de IP o URL para Nmap o Nuclei.

### Ejemplo 1: enumeración básica
```bash
subfinder -d hackerone.com -silent
```

### Ejemplo 2: con todas las fuentes
```bash
subfinder -d target.com -all -v
```

### Ejemplo 3: guardar los resultados
```bash
subfinder -d target.com -o target-subs.txt
```

### Ejemplo 4: varios dominios
```bash
echo -e "target1.com\ntarget2.com" | subfinder -silent
```

### Ejemplo 5: mostrar las fuentes
```bash
subfinder -d target.com -cs
```

### Ejemplo 6: salida JSON
```bash
subfinder -d target.com -oJ -o results.json
```

### Ejemplo 7: con proxy
```bash
subfinder -d target.com -proxy http://127.0.0.1:8080
```

### Ejemplo 8: rendimiento alto
```bash
subfinder -d target.com -all -t 100 -o subs.txt
```

### Ejemplo 9: cadena completa de bug bounty
```bash
subfinder -d target.com -all -silent | \
  httpx -silent -status-code -title | \
  tee live-hosts.txt | \
  nuclei -severity critical,high
```

### Ejemplo 10: descubrimiento recursivo
```bash
subfinder -d target.com -recursive -all -silent
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Enumeración básica | `subfinder -d target.com` |
| Salida silenciosa | `subfinder -d target.com -silent` |
| Guardar en un archivo | `subfinder -d target.com -o subs.txt` |
| Todas las fuentes | `subfinder -d target.com -all` |
| Mostrar las fuentes | `subfinder -d target.com -cs` |
| Varios dominios | `subfinder -dL domains.txt` |
| Salida JSON | `subfinder -d target.com -oJ` |
| Listar las fuentes | `subfinder -ls` |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-d` | Dominio objetivo |
| `-dL` | Archivo con una lista de dominios |
| `-o` | Archivo de salida |
| `-oJ` | Salida JSON |
| `-silent` | Modo silencioso |
| `-all` | Utilizar todas las fuentes |
| `-cs` | Mostrar la fuente de cada subdominio |
| `-t` | Hilos |
| `-recursive` | Enumeración recursiva |

### Cadenas de herramientas habituales

| Cadena | Comando |
|----------|---------|
| Hosts activos | `subfinder -d target.com -silent \| httpx` |
| Reconocimiento completo | `subfinder -d target.com \| httpx \| nuclei` |
| Escaneo de puertos | `subfinder -d target.com -silent \| dnsx -a \| nmap -iL -` |

---

## 💡 Consejos y buenas prácticas

### Consejos de bug bounty

1. **Configurar las claves API**
   ```bash
# Mejores resultados mediante API
   vim ~/.config/subfinder/provider-config.yaml
   ```

2. **Utilizar todas las fuentes para ampliar la cobertura**
   ```bash
   subfinder -d target.com -all
   ```

3. **Combinar con otras herramientas**
   ```bash
   subfinder -d target.com -silent | httpx | nuclei
   ```

4. **Supervisar los subdominios nuevos**
   ```bash
# Comprobación diaria de activos nuevos
   subfinder -d target.com -silent > today.txt
   ```

### Consejos de rendimiento

```bash
# Número elevado de hilos
subfinder -d target.com -t 100 -all

# Tiempo de espera para fuentes lentas
subfinder -d target.com -timeout 60
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Subfinder realiza reconocimiento pasivo y no interactúa directamente con los objetivos en ese modo. Sin embargo, otras herramientas de la cadena pueden hacerlo.
> 
> - ✅ Utilizar en programas autorizados
> - ✅ Objetivos de bug bounty dentro del alcance
> - ✅ Dominios propios
> - ❌ No enumerar objetivos sin autorización
> - ❌ Respetar el alcance del programa
> 
> **Asegúrate siempre de contar con autorización.**

---

## 📚 Recursos

### Recursos oficiales
- [Subfinder GitHub](https://github.com/projectdiscovery/subfinder)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentación](https://docs.projectdiscovery.io/tools/subfinder/)

### Guías rápidas relacionadas
- [Nuclei](../../Nuclei/translations/README.es.md)
- [ffuf](../../ffuf/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)

---

<p align="center">
  <b>🔍 ¡Descubre todos los subdominios!</b><br>
  <i>El primer paso del reconocimiento en bug bounty</i>
</p>
