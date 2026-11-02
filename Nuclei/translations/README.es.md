# 🎯 Nuclei - Guía de referencia completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _            _      _ 
 | \ | |_   _  ___| | ___(_)
 |  \| | | | |/ __| |/ _ \ |
 | |\  | |_| | (__| |  __/ |
 |_| \_|\__,_|\___|_|\___|_|
                            
    Escáner rápido basado en plantillas
```

<p align="center">
  <img src="https://img.shields.io/badge/Nuclei-Vulnerability_Scanner-red?style=for-the-badge" alt="Nuclei">
  <img src="https://img.shields.io/badge/9000+-Templates-orange?style=for-the-badge" alt="Plantillas">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>🔥 El escáner de vulnerabilidades basado en plantillas n.º 1 para investigadores de bug bounty</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Categorías de plantillas](#-categorías-de-plantillas)
- [Escaneo de objetivos](#-escaneo-de-objetivos)
- [Selección de plantillas](#-selección-de-plantillas)
- [Opciones de salida](#-opciones-de-salida)
- [Funciones avanzadas](#-funciones-avanzadas)
- [Plantillas personalizadas](#-plantillas-personalizadas)
- [Flujo de trabajo de bug bounty](#-flujo-de-trabajo-de-bug-bounty)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Nuclei** es un escáner rápido de vulnerabilidades basado en plantillas, desarrollado por ProjectDiscovery. Es una herramienta de referencia para investigadores de seguridad y participantes en programas de bug bounty.

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Más de 9000 plantillas** | CVE, paneles, configuraciones incorrectas y secuestros de subdominios |
| **Rápido y eficiente** | Escaneo en paralelo con limitación de solicitudes |
| **Basado en plantillas** | Plantillas YAML fáciles de personalizar |
| **Impulsado por la comunidad** | Plantillas actualizadas constantemente |
| **Varios protocolos** | HTTP, DNS, TCP, SSL, Headless |
| **Integración sencilla** | Se integra con otras herramientas (httpx y subfinder) |

### Tipos de plantillas

> **Cifras y rutas:** los recuentos son los del original y cambian con el repositorio. Los nombres `v3.x.x`, los CVE con `XXXXX` y varias rutas son marcadores o ejemplos; comprueba las versiones, etiquetas y rutas reales en el [repositorio de plantillas](https://github.com/projectdiscovery/nuclei-templates).

| Tipo | Descripción | Cantidad |
|------|-------------|-------|
| **CVE** | Vulnerabilidades conocidas | 2000+ |
| **Paneles expuestos** | Paneles de administración y paneles de control | 500+ |
| **Configuraciones incorrectas** | Configuraciones de seguridad incorrectas | 300+ |
| **Credenciales de acceso predeterminadas** | Credenciales predeterminadas | 200+ |
| **Secuestros de subdominios** | Secuestro de subdominios | 100+ |
| **Tecnologías** | Detección de tecnologías | 1000+ |
| **Exposiciones de información** | Exposición de archivos sensibles | 500+ |

---

## 📥 Instalación

### Mediante Go (recomendado)
```bash
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest
```

### Descarga del binario
```bash
# Descargar desde las versiones publicadas
https://github.com/projectdiscovery/nuclei/releases

# Linux
wget https://github.com/projectdiscovery/nuclei/releases/download/v3.x.x/nuclei_3.x.x_linux_amd64.zip
unzip nuclei_3.x.x_linux_amd64.zip
sudo mv nuclei /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/nuclei:latest
docker run -it projectdiscovery/nuclei -h
```

### Kali Linux
```bash
sudo apt install nuclei
```

### Actualizar plantillas

> **Corrección:** `-ut` y `-update-templates` actualizan las plantillas; el motor se actualiza con `-update`. Se conserva el comentario original. Véase la [guía oficial](https://docs.projectdiscovery.io/opensource/nuclei/running).
```bash
# Actualizar Nuclei y las plantillas
nuclei -ut

# Actualizar solo las plantillas
nuclei -update-templates
```

### Verificar la instalación
```bash
nuclei -version
nuclei -tl              # Listar plantillas
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
nuclei [options] -target <url>
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-u` | URL de un objetivo |
| `-l` | Archivo con una lista de URL |
| `-t` | Plantillas que se van a ejecutar |
| `-tags` | Plantillas con etiquetas concretas |
| `-severity` | Filtrar por severidad |
| `-o` | Archivo de salida |
| `-silent` | Modo silencioso |
| `-v` | Salida detallada |

### Inicio rápido
```bash
# Escanear un objetivo
nuclei -u https://example.com

# Escanear una lista de objetivos
nuclei -l urls.txt

# Escanear con plantillas concretas
nuclei -u https://example.com -t cves/

# Escanear con etiquetas
nuclei -u https://example.com -tags cve,rce
```

---

## 📂 Categorías de plantillas

### Plantillas de CVE

```bash
# Todas las plantillas de CVE
nuclei -u target.com -t cves/

# Año concreto de CVE
nuclei -u target.com -t cves/2024/
nuclei -u target.com -t cves/2023/

# Solo CVE críticos
nuclei -u target.com -t cves/ -severity critical

# CVE concreto
nuclei -u target.com -t cves/2023/CVE-2023-XXXXX.yaml
```

### Paneles expuestos

```bash
# Toda la detección de paneles
nuclei -u target.com -t exposed-panels/

# Paneles concretos
nuclei -u target.com -tags phpmyadmin
nuclei -u target.com -tags jenkins
nuclei -u target.com -tags grafana
nuclei -u target.com -tags kibana
```

### Configuraciones incorrectas

```bash
# Todas las configuraciones incorrectas
nuclei -u target.com -t misconfiguration/

# Tipos concretos
nuclei -u target.com -tags cors
nuclei -u target.com -tags headers
nuclei -u target.com -tags http-missing-security-headers
```

### Credenciales de acceso predeterminadas

```bash
# Todas las credenciales predeterminadas
nuclei -u target.com -t default-logins/

# Fabricantes concretos
nuclei -u target.com -tags tomcat-default
nuclei -u target.com -tags apache-default
```

### Secuestro de subdominios

```bash
# Todas las comprobaciones de secuestro
nuclei -u target.com -t takeovers/

# Proveedores concretos
nuclei -u target.com -tags aws-takeover
nuclei -u target.com -tags azure-takeover
nuclei -u target.com -tags github-takeover
```

### Detección de tecnologías

```bash
# Detección de tecnologías
nuclei -u target.com -t technologies/

# Detección de CMS
nuclei -u target.com -tags tech,wordpress
nuclei -u target.com -tags tech,joomla
```

### Exposiciones de información

```bash
# Exposición de archivos sensibles
nuclei -u target.com -t exposures/

# Exposiciones concretas
nuclei -u target.com -tags config
nuclei -u target.com -tags backup
nuclei -u target.com -tags logs
nuclei -u target.com -tags git
```

---

## 🎯 Escaneo de objetivos

### Un objetivo

```bash
nuclei -u https://example.com
nuclei -u https://example.com:8080
```

### Varios objetivos

```bash
# Desde un archivo
nuclei -l targets.txt

# Desde la entrada estándar (mediante una cadena de herramientas)
cat targets.txt | nuclei
echo "https://example.com" | nuclei

# Varias URL en la línea de comandos
nuclei -u https://site1.com -u https://site2.com
```

### Con otras herramientas

```bash
# Con subfinder (subdominios)
subfinder -d example.com | nuclei

# Con httpx (equipos activos)
subfinder -d example.com | httpx | nuclei

# Con katana (URL rastreadas)
katana -u https://example.com | nuclei

# Cadena completa de reconocimiento
subfinder -d example.com -silent | httpx -silent | nuclei -silent
```

---

<a id="-selección-de-plantillas"></a>
## 🏷️ Selección de plantillas

### Por ruta de plantilla

```bash
# Una plantilla
nuclei -u target.com -t cves/2023/CVE-2023-1234.yaml

# Directorio de plantillas
nuclei -u target.com -t cves/2023/
nuclei -u target.com -t exposed-panels/

# Varios directorios
nuclei -u target.com -t cves/ -t exposed-panels/

# Ruta de plantillas personalizada
nuclei -u target.com -t /path/to/custom/templates/
```

### Por etiquetas

```bash
# Una etiqueta
nuclei -u target.com -tags cve

# Varias etiquetas (OR)
nuclei -u target.com -tags cve,rce,sqli

# Etiquetas habituales
nuclei -u target.com -tags xss
nuclei -u target.com -tags ssrf
nuclei -u target.com -tags lfi
nuclei -u target.com -tags rce
nuclei -u target.com -tags injection
```

### Por severidad

```bash
# Una severidad
nuclei -u target.com -severity critical
nuclei -u target.com -severity high
nuclei -u target.com -severity medium
nuclei -u target.com -severity low
nuclei -u target.com -severity info

# Varias severidades
nuclei -u target.com -severity critical,high

# Excluir una severidad
nuclei -u target.com -exclude-severity info,low
```

### Excluir plantillas

```bash
# Excluir plantillas concretas
nuclei -u target.com -exclude-templates cves/2020/

# Excluir por etiquetas
nuclei -u target.com -exclude-tags dos,fuzz

# Excluir por ID
nuclei -u target.com -exclude-id CVE-2021-1234
```

---

## 📤 Opciones de salida

### Formatos de salida

> **Versiones:** `-json` aparece en ejemplos antiguos. La documentación actual usa `-jsonl` para JSON Lines y `-json-export` para exportar JSON; comprueba las opciones de la versión instalada.

```bash
# Salida estándar
nuclei -u target.com -o results.txt

# Salida JSON
nuclei -u target.com -json -o results.json

# JSON Lines
nuclei -u target.com -jsonl -o results.jsonl

# Markdown
nuclei -u target.com -markdown-export report/
```

### Filtrado de salida

```bash
# Modo silencioso (solo resultados)
nuclei -u target.com -silent

# Sin colores
nuclei -u target.com -nc

# Detallado
nuclei -u target.com -v

# Depuración
nuclei -u target.com -debug
```

### Opciones de exportación

```bash
# SARIF (GitHub)
nuclei -u target.com -sarif-export results.sarif

# Informe en Markdown
nuclei -u target.com -me reports/
```

---

## ⚡ Funciones avanzadas

### Limitación de solicitudes

```bash
# Solicitudes por segundo
nuclei -u target.com -rate-limit 100

# Tamaño del lote
nuclei -u target.com -bulk-size 25

# Concurrencia
nuclei -u target.com -c 50

# Concurrencia de plantillas
nuclei -u target.com -rl 100 -c 50
```

### Compatibilidad con proxy

```bash
# Proxy HTTP
nuclei -u target.com -proxy http://127.0.0.1:8080

# Proxy SOCKS
nuclei -u target.com -proxy socks5://127.0.0.1:9050

# Proxy de Burp Suite
nuclei -u target.com -proxy http://127.0.0.1:8080
```

### Autenticación

```bash
# Autenticación mediante cabecera
nuclei -u target.com -H "Authorization: Bearer token123"

# Autenticación mediante cookie
nuclei -u target.com -H "Cookie: session=abc123"

# Varias cabeceras
nuclei -u target.com -H "Authorization: Bearer token" -H "X-Custom: value"
```

### Integración con Interactsh

```bash
# Usar Interactsh para pruebas fuera de banda (OOB)
nuclei -u target.com -interactsh-server https://interact.sh

# Servidor Interactsh personalizado
nuclei -u target.com -iserver https://my-interactsh.com
```

### Modo sin interfaz gráfica (headless)

> **Activación:** seleccionar el directorio `headless/` no sustituye a `-headless`; estas plantillas requieren habilitar expresamente ese modo.

```bash
# Habilitar el navegador sin interfaz gráfica
nuclei -u target.com -headless

# Solo plantillas headless
nuclei -u target.com -t headless/
```

### Flujos de trabajo

```bash
# Ejecutar un flujo de trabajo
nuclei -u target.com -w workflows/wordpress-workflow.yaml

# Listar flujos de trabajo
ls ~/.local/nuclei-templates/workflows/
```

---

## 📝 Plantillas personalizadas

### Estructura de una plantilla

```yaml
id: my-custom-template

info:
  name: My Custom Template
  author: your-name
  severity: medium
  description: Description of what this template detects
  tags: custom,web

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
    matchers:
      - type: word
        words:
          - "Admin Panel"
```

### Ejemplos de plantillas

#### Detectar una página concreta

> **Interpretación:** un estado HTTP 200 por sí solo no demuestra que exista un panel de administración; puede producir falsos positivos.
```yaml
id: admin-panel-detect

info:
  name: Admin Panel Detection
  author: hunter
  severity: info
  tags: panel

http:
  - method: GET
    path:
      - "{{BaseURL}}/admin"
      - "{{BaseURL}}/administrator"
      - "{{BaseURL}}/wp-admin"
    matchers-condition: or
    matchers:
      - type: status
        status:
          - 200
```

#### Comprobar una cabecera

> **Error del YAML original:** se repite la clave `matchers` dentro de la misma solicitud. Un analizador puede rechazarla o conservar solo una aparición. Ambos criterios deberían pertenecer a una única lista y combinarse según la lógica deseada; se conserva el YAML original. Véase la [referencia de matchers](https://docs.projectdiscovery.io/templates/reference/matchers).
```yaml
id: missing-security-header

info:
  name: Missing X-Frame-Options
  author: hunter
  severity: low
  tags: headers

http:
  - method: GET
    path:
      - "{{BaseURL}}"
    matchers:
      - type: word
        words:
          - "text/html"
        part: header
    matchers:
      - type: word
        negative: true
        words:
          - "X-Frame-Options"
        part: header
```

### Validar una plantilla

```bash
# Validar la sintaxis de la plantilla
nuclei -validate -t my-template.yaml

# Probar la plantilla
nuclei -u target.com -t my-template.yaml -debug
```

---

## 🔄 Flujo de trabajo de bug bounty

### Cadena completa de reconocimiento

```bash
# Paso 1: Enumerar subdominios
subfinder -d target.com -o subs.txt

# Paso 2: Identificar equipos activos
cat subs.txt | httpx -o live.txt

# Paso 3: Ejecutar Nuclei
nuclei -l live.txt -severity critical,high -o findings.txt

# Comando de una línea
subfinder -d target.com -silent | httpx -silent | nuclei -severity critical,high
```

### Escaneo por prioridad

```bash
# Fase 1: Solo severidad crítica (rápido)
nuclei -l targets.txt -severity critical -o critical.txt

# Fase 2: Severidad alta
nuclei -l targets.txt -severity high -o high.txt

# Fase 3: CVE
nuclei -l targets.txt -t cves/ -o cves.txt

# Fase 4: Escaneo completo
nuclei -l targets.txt -o all.txt
```

### Escaneo específico

```bash
# Objetivos WordPress
nuclei -l wp-sites.txt -tags wordpress

# Endpoints de API
nuclei -l apis.txt -tags api

# Páginas de inicio de sesión
nuclei -l logins.txt -tags login,default-login
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: Escaneo completo
```bash
nuclei -u https://target.com -o results.txt
```

### Ejemplo 2: Solo CVE críticos

> **Precisión del título:** el comando del original selecciona tanto `critical` como `high`.
```bash
nuclei -u https://target.com -t cves/ -severity critical,high
```

### Ejemplo 3: Escaneo rápido de bug bounty
```bash
nuclei -u https://target.com -tags cve,rce,sqli,xss,ssrf -severity critical,high
```

### Ejemplo 4: Comprobar secuestro de subdominios
```bash
cat subs.txt | nuclei -t takeovers/
```

### Ejemplo 5: Detección de tecnologías
```bash
nuclei -u https://target.com -t technologies/ -silent
```

### Ejemplo 6: Con proxy (Burp)
```bash
nuclei -u https://target.com -proxy http://127.0.0.1:8080
```

### Ejemplo 7: Escaneo autenticado
```bash
nuclei -u https://target.com -H "Authorization: Bearer eyJhbG..."
```

### Ejemplo 8: Escaneo con solicitudes limitadas
```bash
nuclei -l targets.txt -rate-limit 10 -c 5
```

### Ejemplo 9: Salida JSON para procesamiento
```bash
nuclei -u https://target.com -json -o results.json
```

### Ejemplo 10: Cadena completa de herramientas
```bash
subfinder -d target.com -silent | \
httpx -silent | \
nuclei -severity critical,high -json -o results.json
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Escaneo básico | `nuclei -u target.com` |
| Con plantillas | `nuclei -u target.com -t cves/` |
| Por etiquetas | `nuclei -u target.com -tags cve,rce` |
| Por severidad | `nuclei -u target.com -severity critical` |
| Desde una lista | `nuclei -l urls.txt` |
| Actualizar plantillas | `nuclei -ut` |
| Salida silenciosa | `nuclei -u target.com -silent` |
| Salida JSON | `nuclei -u target.com -json -o out.json` |

### Etiquetas habituales

| Etiqueta | Descripción |
|-----|-------------|
| `cve` | Vulnerabilidades CVE |
| `rce` | Ejecución remota de código |
| `sqli` | Inyección SQL |
| `xss` | Cross-site scripting (XSS) |
| `ssrf` | Falsificación de solicitudes del lado del servidor |
| `lfi` | Inclusión de archivos locales |
| `panel` | Paneles de administración |
| `takeover` | Secuestro de subdominios |

### Niveles de severidad

| Severidad | Color | Prioridad |
|----------|-------|----------|
| `critical` | 🔴 Rojo | Immediate |
| `high` | 🟠 Naranja | Alta |
| `medium` | 🟡 Amarillo | Media |
| `low` | 🟢 Verde | Baja |
| `info` | 🔵 Azul | Informativa |

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

```bash
# Configuración óptima para escaneos grandes
nuclei -l targets.txt -c 50 -rl 150 -bs 25

# Modo silencioso para cadenas de herramientas
nuclei -l targets.txt -silent -o results.txt
```

### Consejos para bug bounty

1. **Empezar por severidades crítica y alta**
   ```bash
   nuclei -l targets.txt -severity critical,high
   ```

2. **Buscar resultados rápidos**
   ```bash
   nuclei -l targets.txt -tags takeover,exposed-panels
   ```

3. **Usar plantillas actualizadas**
   ```bash
   nuclei -ut  # Actualizar siempre antes de buscar vulnerabilidades
   ```

4. **Limitar las solicitudes para mantener la estabilidad**
   ```bash
   nuclei -l targets.txt -rl 50
   ```

### Consejos de discreción

```bash
# Lento y silencioso
nuclei -l targets.txt -rl 10 -c 2

# Mediante un proxy
nuclei -l targets.txt -proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Nuclei es un escáner potente de vulnerabilidades que debe usarse únicamente para **pruebas autorizadas**.
> 
> - ✅ Usar en programas para los que se tenga permiso de pruebas
> - ✅ Usar en programas de bug bounty autorizados
> - ✅ Usar en sistemas propios
> - ❌ Nunca escanear sin autorización
> - ❌ Respetar los límites de solicitudes y el alcance
> 
> **El escaneo sin autorización es ilegal y contrario a la ética.**

---

## 📚 Recursos

### Recursos oficiales
- [Nuclei GitHub](https://github.com/projectdiscovery/nuclei)
- [Plantillas de Nuclei](https://github.com/projectdiscovery/nuclei-templates)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Documentación](https://docs.projectdiscovery.io/tools/nuclei/)

### Recursos de plantillas
- [Plantillas de la comunidad](https://github.com/projectdiscovery/nuclei-templates)
- [Guía de plantillas](https://docs.projectdiscovery.io/templates/)

### Guías de referencia relacionadas
- [Nmap](../../Nmap/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)

---

<p align="center">
  <b>🎯 ¡Domina el escaneo de vulnerabilidades!</b><br>
  <i>El mejor aliado del investigador de bug bounty</i>
</p>
